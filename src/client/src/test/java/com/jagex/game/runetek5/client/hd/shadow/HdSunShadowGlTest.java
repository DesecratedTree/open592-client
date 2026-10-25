package com.jagex.game.runetek5.client.hd.shadow;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;

import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.buffer.ArrayBufferGL;
import com.jagex.game.runetek5.buffer.ElementsBufferGL;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.client.hd.HdFrameSequence;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.client.hd.shader.HdDrawRequest;
import com.jagex.game.runetek5.client.hd.shader.HdDrawRows.Row;
import com.jagex.game.runetek5.client.hd.shader.HdDrawRows;
import com.jagex.game.runetek5.client.hd.shader.HdDrawSpace;
import com.jagex.game.runetek5.client.hd.shader.HdFrameState;
import com.jagex.game.runetek5.client.hd.shader.HdLightingPipeline;
import com.jagex.game.runetek5.client.hd.shader.JagglShaderApi;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import com.jagex.game.runetek5.unpack_Sub1;
import jaggl.GlTestContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

/**
 * Sun shadows on a real driver, proved by pixels rather than by asserting that
 * a uniform was uploaded.
 *
 * <p>The scene is two quads: a receiver lying across the sun (z = 0, +-4) and an
 * occluder one unit nearer the sun (z = 1, +-0.5). Both are ordinary interleaved
 * buffers drawn through {@code method1934}/{@code method1967}, the same calls
 * {@code GlModel} makes, and both are lit by the toolkit's own sun uploads. The
 * shadow map is rebuilt from the sun with {@code hdSunShadowBegin/End}, so
 * every frame here runs the real path: depth pass, then per-pixel lighting with
 * the map projected in.
 *
 * <p>What the proofs are for:
 *
 * <ul>
 * <li>a shadow exists, and only where the sun is blocked - a pixel under the
 * occluder is ambient-only while one beside it is lit;</li>
 * <li>the sun's direction drives it - tilt the sun and the dark region moves to
 * the geometric place it must move to, not just "somewhere else";</li>
 * <li>PCF softens the edge - the pixel run across a shadow boundary holds
 * partial values, and a wider kernel widens that run;</li>
 * <li>the depth pass is invisible to everything else - a fixed-function frame
 * drawn after a pass is byte-identical to one drawn before it, and the GL
 * target, viewport, draw buffer, masks and the scene's alpha-test function and
 * reference are all back;</li>
 * <li>the cut-out row is a texture's shape, not its quad - a textured caster
 * casts a hole where its alpha is transparent, the same caster with the row
 * off or with an opaque texture casts solid, and the pass owns its alpha test
 * while it draws rather than inheriting the scene's;</li>
 * <li>a real model recorded through the world draw's seam casts the shape of
 * its own materials and texcoords - each face group binds the texture the
 * screen draw gives it, a solid caster draws once with no texture at all, and
 * a half-opaque texture leaves part of the quad's interior lit, which a
 * constant texcoord could not.</li>
 * </ul>
 *
 * <p>It also times the pass, because a per-frame shadow map that costs the
 * frame its budget is not a feature.
 */
@DisplayName("sun shadows on a real GL driver")
class HdSunShadowGlTest {

	private static final int SIZE = 256;

	/** Camera distance; the frustum below maps +-5 world units to the viewport. */
	private static final float EYE = 20.0F;

	private static final float VISIBLE_HALF = 5.0F;

	/** Shared volume: big enough that its texels are visible on screen. */
	private static final float VOLUME_RADIUS = 32.0F;

	private static final byte[] WHITE = { (byte) 255, (byte) 255, (byte) 255, (byte) 255 };

	/** Fake material ids: one opaque, one transparent, one without an alpha channel. */
	private static final int OPAQUE_MATERIAL = 7001;

	private static final int CLEAR_MATERIAL = 7002;

	private static final int SOLID_MATERIAL = 7003;

	/** Opaque on one half of its texture, transparent on the other. */
	private static final int HALF_MATERIAL = 7004;

	/**
	 * A second fully opaque alpha material: two ids make the model builder emit
	 * two face groups over the same full silhouette, which is the shape the
	 * per-group depth draw iterates.
	 */
	private static final int SECOND_OPAQUE_MATERIAL = 7006;

	/**
	 * An id the provider has never had a texture for, which is what
	 * {@code Js5TextureSource.textureAvailable} reports for a material that has
	 * not streamed in yet.
	 */
	private static final int MISSING_MATERIAL = 7005;

	/**
	 * The mask bits {@code GlModel.method2807} tests before it releases the
	 * face-group and material tables. Both must be absent for the release, and
	 * clearing them is exactly what the client's own {@code UA(mask)} path does
	 * when the model's lighting preference changes underneath it.
	 */
	private static final int FACE_GROUP_BIT = 0x800;

	private static final int GROUPED_FACES_BIT = 0x40000;

	private static GlToolkit toolkit;

	private static Frame frame;

	private static Canvas canvas;

	private static int framebuffer;

	private static int targetTexture;

	private static int depthBuffer;

	private static ElementsBufferGL receiverBuffer;

	private static ArrayBufferGL receiverIndices;

	private static Class177 receiverVertices;

	private static Class177 receiverColours;

	private static Class177 receiverNormals;

	private static ElementsBufferGL occluderBuffer;

	private static ElementsBufferGL wideOccluderBuffer;

	private static Class177 wideOccluderVertices;

	private static Class177 wideOccluderColours;

	private static Class177 wideOccluderNormals;

	/**
	 * The cut-out caster: the same footprint as the wide occluder, but made of a
	 * texture whose +x half is transparent. The depth pass must reject those
	 * texels only when the cut-out row is on.
	 */
	private static ElementsBufferGL texturedCasterBuffer;

	private static Class177 texturedCasterVertices;

	private static Class177 texturedCasterColours;

	private static Class177 texturedCasterNormals;

	private static Class177 texturedCasterTexCoords;

	/** White with an alpha cliff at u = 0.5. */
	private static int cutoutTexture;

	/** The same white texture, fully opaque: the control for the hole. */
	private static int opaqueTexture;

	private static ArrayBufferGL occluderIndices;

	private static Class177 occluderVertices;

	private static Class177 occluderColours;

	private static Class177 occluderNormals;

	private static HdLightingPipeline pipeline;

	@BeforeAll
	static void openToolkit() throws Exception {
		try {
			GlTestContext warmup = GlTestContext.open();
			warmup.close();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}
		frame = new Frame("592HD sun shadow test");
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(SIZE, SIZE));
		frame.add(canvas);
		frame.pack();
		frame.setLocation(-4000, -4000);
		frame.setVisible(true);
		try {
			toolkit = new GlToolkit(0, canvas, new ShadowMaterials(), 0, null);
		} catch (Throwable error) {
			frame.dispose();
			assumeTrue(false, "GlToolkit could not start in this JVM: " + error);
		}
		System.out.println("[hd-shadow] toolkit " + GL11.glGetString(GL11.GL_RENDERER));

		createTarget();
		receiverBuffer = quad(4.0F, 0.0F);
		receiverVertices = new Class177(receiverBuffer, GL11.GL_FLOAT, 3, 0);
		receiverColours = new Class177(receiverBuffer, GL11.GL_UNSIGNED_BYTE, 4, 12);
		receiverNormals = new Class177(receiverBuffer, GL11.GL_FLOAT, 3, 16);
		receiverIndices = indices();

		occluderBuffer = quad(0.3F, 1.0F);
		occluderVertices = new Class177(occluderBuffer, GL11.GL_FLOAT, 3, 0);
		occluderColours = new Class177(occluderBuffer, GL11.GL_UNSIGNED_BYTE, 4, 12);
		occluderNormals = new Class177(occluderBuffer, GL11.GL_FLOAT, 3, 16);
		occluderIndices = indices();

		// The submission names its own volume, and ShadowVolume's floor is 256
		// units, so at 1024 texels a 0.6-unit caster is barely one texel. This
		// quad is wide enough that its footprint cannot fall between texels.
		wideOccluderBuffer = quad(1.5F, 1.0F);
		wideOccluderVertices = new Class177(wideOccluderBuffer, GL11.GL_FLOAT, 3, 0);
		wideOccluderColours = new Class177(wideOccluderBuffer, GL11.GL_UNSIGNED_BYTE, 4, 12);
		wideOccluderNormals = new Class177(wideOccluderBuffer, GL11.GL_FLOAT, 3, 16);

		texturedCasterBuffer = texturedQuad(1.5F, 1.0F);
		texturedCasterVertices = new Class177(texturedCasterBuffer, GL11.GL_FLOAT, 3, 0);
		texturedCasterColours = new Class177(texturedCasterBuffer, GL11.GL_UNSIGNED_BYTE, 4, 12);
		texturedCasterNormals = new Class177(texturedCasterBuffer, GL11.GL_FLOAT, 3, 16);
		texturedCasterTexCoords = new Class177(texturedCasterBuffer, GL11.GL_FLOAT, 2, 28);
		cutoutTexture = rgbaTexture(true);
		opaqueTexture = rgbaTexture(false);

		Path preferences = Files.createTempDirectory("hd-shadow");
		HdSettings.setDirectoryForTest(preferences.toFile());
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 1);
		HdSettings.set(HdFeature.POINT_LIGHTS, 1);
		HdSettings.set(HdFeature.SHADOW_MAP, 1); // 1024
		HdSettings.set(HdFeature.LIGHTING_DETAIL, 1); // 5x5
		toolkit.method1900(1);
		toolkit.method1906(true);

		pipeline = new HdLightingPipeline(new JagglShaderApi());
	}

	@AfterAll
	static void closeToolkit() {
		HdSettings.resetForTest();
		if (pipeline != null) {
			pipeline.shutdown();
		}
		if (toolkit != null) {
			SunShadowMap map = toolkit.hdSunShadow();
			if (map != null) {
				map.release();
			}
			toolkit.hdEndModelLighting();
			GL11.glDeleteTextures(cutoutTexture);
			GL11.glDeleteTextures(opaqueTexture);
		}
		if (frame != null) {
			frame.dispose();
		}
	}

	@Test
	@DisplayName("the occluder darkens the receiver only where it blocks the sun")
	void occluder_shouldShadowOnlyUnderTheSun() {
		renderShadowed(0.0F, 0.0F, 1.0F, 2);

		Pixel centre = pixel(128, 128);
		Pixel beside = pixel(151, 128); // x = +0.9, outside the +-0.3 occluder
		Pixel high = pixel(128, 152);   // y = +0.9

		System.out.println("[hd-shadow] centre " + centre + ", beside " + beside + ", high " + high);
		assertAll(
			() -> assertTrue(centre.red <= 60, "the shadowed receiver must fall to ambient, got " + centre),
			() -> assertTrue(beside.red >= 240, "the sunlit receiver must be full, got " + beside),
			() -> assertTrue(high.red >= 240, "the shadow is a footprint, not a wash, got " + high)
		);
	}

	@Test
	@DisplayName("turning the sun moves the shadow to the place geometry says it must be")
	void movingSun_shouldMoveTheShadow() {
		renderShadowed(0.0F, 0.0F, 1.0F, 2);
		int centreBefore = pixel(128, 128).red;
		int expectedAfter = pixel(143, 128).red; // x = +0.58, still lit by an overhead sun

		// 30 degrees off vertical, toward +x: the shadow of a caster one unit up
		// lands tan(30) = 0.577 units the other way, at x = -0.577.
		renderShadowed(0.5F, 0.0F, 0.866F, 2);
		int centreAfter = pixel(128, 128).red;
		int shifted = pixel(113, 128).red; // x = -0.58
		int opposite = pixel(143, 128).red; // x = +0.58

		System.out.println("[hd-shadow] sun overhead: centre " + centreBefore + ", x=+0.58 " + expectedAfter
			+ " | sun tilted: centre " + centreAfter + ", x=-0.58 " + shifted
			+ ", x=+0.58 " + opposite);
		assertAll(
			() -> assertTrue(centreBefore <= 60, "overhead sun shadows the centre"),
			() -> assertTrue(expectedAfter >= 240, "and leaves x=+0.58 lit"),
			() -> assertTrue(centreAfter >= 240, "a tilted sun uncovers the centre"),
			() -> assertTrue(shifted <= 60, "and shadows the point the caster now covers"),
			() -> assertTrue(opposite >= 240, "on the down-sun side only, not both sides")
		);
	}

	@Test
	@DisplayName("PCF turns the shadow edge into a gradient, and a wider kernel widens it")
	void pcf_shouldSoftenTheEdge() {
		renderShadowed(0.0F, 0.0F, 1.0F, 0);

		int hardPartial = partialPixels();
		renderShadowed(0.0F, 0.0F, 1.0F, 1);
		int lowPartial = partialPixels();
		renderShadowed(0.0F, 0.0F, 1.0F, 2);
		int highPartial = partialPixels();

		System.out.println("[hd-shadow] edge pixels: single tap " + hardPartial
			+ ", 3x3 " + lowPartial + ", 5x5 " + highPartial);
		assertAll(
			() -> assertTrue(hardPartial <= 2, "one tap is a hard edge, got " + hardPartial),
			() -> assertTrue(lowPartial > hardPartial, "3x3 must soften the edge"),
			() -> assertTrue(highPartial > lowPartial, "5x5 must soften it further")
		);
	}

	@Test
	@DisplayName("the depth pass leaves no state behind for the stock draw")
	void depthPass_shouldNotDisturbStockDraw() {
		// A plain fixed-function frame: no shader, no shadows.
		Pixel before = drawFixed();
		int[] bindingBefore = new int[1];
		GL11.glGetIntegerv(EXTFramebufferObject.GL_FRAMEBUFFER_BINDING_EXT, bindingBefore);
		int capsBefore = enabledCaps();

		HdSettings.set(HdFeature.SHADOW_MAP, 1);
		assertTrue(toolkit.hdSunShadowBegin(0.0F, 0.0F, 0.0F, VOLUME_RADIUS), "the pass should start");
		toolkit.method1934(occluderColours, occluderNormals, null, occluderVertices);
		toolkit.method1967(0, 6, occluderIndices);
		int capsDuringPass = enabledCaps();
		toolkit.hdSunShadowEnd();
		int capsAfterPass = enabledCaps();

		Pixel after = drawFixed();

		int[] binding = new int[1];
		GL11.glGetIntegerv(EXTFramebufferObject.GL_FRAMEBUFFER_BINDING_EXT, binding);
		int[] viewport = new int[4];
		GL11.glGetIntegerv(GL11.GL_VIEWPORT, viewport);
		int[] drawBuffer = new int[1];
		GL11.glGetIntegerv(GL11.GL_DRAW_BUFFER, drawBuffer);
		boolean colourMask = GL11.glGetBoolean(GL11.GL_COLOR_WRITEMASK);
		boolean depthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);

		System.out.println("[hd-shadow] stock frame before " + before + ", after " + after
			+ ", caps " + Integer.toBinaryString(capsBefore) + " -> " + Integer.toBinaryString(capsAfterPass));
		assertAll(
			() -> assertEquals(before.toString(), after.toString(), "the stock frame must be unchanged"),
			() -> assertEquals(capsBefore, capsAfterPass, "every enable flag the pass touched is back"),
			() -> assertEquals(capsBefore | 1 << 2, capsDuringPass,
				"during the pass only the depth test is added, which is what makes it a depth pass"),
			() -> assertEquals(bindingBefore[0], binding[0], "framebuffer binding restored"),
			() -> assertEquals(0, viewport[0], "viewport restored"),
			() -> assertEquals(SIZE, viewport[2], "viewport size restored"),
			() -> assertEquals(EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, drawBuffer[0], "draw buffer restored"),
			() -> assertTrue(colourMask, "colour writes restored"),
			() -> assertTrue(depthMask, "depth writes restored"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError(), "no GL error")
		);
	}

	@Test
	@DisplayName("with the row off the frame is the unshadowed one, and no pass runs")
	void rowOff_shouldLeaveTheFrameUntouched() {
		HdSettings.set(HdFeature.SHADOW_MAP, 0);
		assertFalse(toolkit.hdSunShadowBegin(0.0F, 0.0F, 0.0F, VOLUME_RADIUS), "no pass when the row is off");

		Pixel unshadowed = drawToolkitLighting();
		HdSettings.set(HdFeature.SHADOW_MAP, 1);
		Pixel shadowed = drawToolkitLighting();

		System.out.println("[hd-shadow] row off " + unshadowed + ", row on " + shadowed);
		assertAll(
			() -> assertTrue(unshadowed.red >= 240, "row off is the plain lit receiver"),
			() -> assertTrue(shadowed.red <= 60, "row on brings the shadow back")
		);
	}

	@Test
	@DisplayName("a cut-out caster casts the shape of its texture, not its quad")
	void cutoutCaster_shouldCastTheTextureShape() {
		HdSettings.set(HdFeature.CUTOUT_SHADOWS, 1);
		try {
			renderTexturedShadow(cutoutTexture);

			// The texture is opaque below u = 0.5 (x < 0) and transparent above
			// it. Every sample is well inside the quad, so only the alpha can
			// explain a difference between the two halves.
			Pixel solidSide = pixel(109, 128); // x = -0.75
			Pixel holeSide = pixel(147, 128);  // x = +0.75
			Pixel solidEdge = pixel(97, 128);  // x = -1.21
			Pixel holeEdge = pixel(159, 128);  // x = +1.21

			System.out.println("[hd-shadow] cut-out row on: opaque side " + solidSide + ", " + solidEdge
				+ " | transparent side " + holeSide + ", " + holeEdge);
			assertAll(
				() -> assertTrue(solidSide.red <= 60, "the opaque half must shadow: " + solidSide),
				() -> assertTrue(solidEdge.red <= 60, "across the whole opaque half: " + solidEdge),
				() -> assertTrue(holeSide.red >= 240,
					"the transparent half must let the sun through: " + holeSide),
				() -> assertTrue(holeEdge.red >= 240,
					"across the whole transparent half: " + holeEdge)
			);
		} finally {
			HdSettings.set(HdFeature.CUTOUT_SHADOWS, 0);
		}
	}

	@Test
	@DisplayName("the same caster with an opaque texture casts solid under the same row")
	void cutoutCaster_opaqueTextureShouldCastSolid() {
		HdSettings.set(HdFeature.CUTOUT_SHADOWS, 1);
		try {
			renderTexturedShadow(opaqueTexture);
			Pixel left = pixel(109, 128);
			Pixel right = pixel(147, 128);

			System.out.println("[hd-shadow] cut-out row on, opaque texture: " + left + ", " + right);
			assertAll(
				() -> assertTrue(left.red <= 60, "the opaque texture must shadow its whole quad: " + left),
				() -> assertTrue(right.red <= 60, "both halves, so the hole is the alpha: " + right)
			);
		} finally {
			HdSettings.set(HdFeature.CUTOUT_SHADOWS, 0);
		}
	}

	@Test
	@DisplayName("with the row off the same cut-out caster casts its full silhouette")
	void cutoutCaster_offShouldCastTheFullSilhouette() {
		HdSettings.set(HdFeature.CUTOUT_SHADOWS, 0);
		renderTexturedShadow(cutoutTexture);
		Pixel left = pixel(109, 128);
		Pixel right = pixel(147, 128);
		Pixel farRight = pixel(159, 128);

		System.out.println("[hd-shadow] cut-out row off: " + left + ", " + right + ", " + farRight);
		assertAll(
			() -> assertTrue(left.red <= 60, "the row-off pass is solid: " + left),
			() -> assertTrue(right.red <= 60, "including under the transparent texels: " + right),
			() -> assertTrue(farRight.red <= 60, "across the whole quad: " + farRight)
		);
	}

	@Test
	@DisplayName("a recorded model carries its per-group material texture into the depth pass")
	void recordedModel_shouldCarryMaterialAlpha() {
		HdSettings.set(HdFeature.CUTOUT_SHADOWS, 1);
		try {
			GlModel model = shadowModel(OPAQUE_MATERIAL, CLEAR_MATERIAL);
			Pixel[] pixels = renderRecordedModel(model);
			long casters = toolkit.hdShadowSubmission().castersSubmitted();
			long binds = toolkit.hdShadowMaterialBinds();

			System.out.println("[hd-shadow] recorded model: casters " + casters + ", material binds "
				+ binds + ", opaque quad " + pixels[0] + ", transparent quad " + pixels[1]);
			assertAll(
				() -> assertEquals(1L, casters, "the recorded model reached the pass"),
				() -> assertEquals(2L, binds, "one bind for each group's material texture"),
				() -> assertTrue(pixels[0].red <= 60,
					"the opaque material's quad must shadow: " + pixels[0]),
				() -> assertTrue(pixels[1].red >= 240,
					"the transparent material's quad must not: " + pixels[1])
			);
		} finally {
			HdSettings.set(HdFeature.CUTOUT_SHADOWS, 0);
		}
	}

	@Test
	@DisplayName("a recorded cut-out model with the row off casts solid and binds no texture")
	void recordedModel_offShouldCastSolid() {
		HdSettings.set(HdFeature.CUTOUT_SHADOWS, 0);
		GlModel model = shadowModel(OPAQUE_MATERIAL, CLEAR_MATERIAL);
		Pixel[] pixels = renderRecordedModel(model);
		long binds = toolkit.hdShadowMaterialBinds();

		System.out.println("[hd-shadow] recorded model row off: material binds " + binds
			+ ", opaque quad " + pixels[0] + ", transparent quad " + pixels[1]);
		assertAll(
			() -> assertEquals(0L, binds, "the row-off draw path must not bind a texture"),
			() -> assertTrue(pixels[0].red <= 60, "the whole silhouette casts: " + pixels[0]),
			() -> assertTrue(pixels[1].red <= 60, "including the transparent material: " + pixels[1])
		);
	}

	@Test
	@DisplayName("a recorded solid model binds nothing even with the cut-out row on")
	void recordedModel_solidShouldBindNothing() {
		HdSettings.set(HdFeature.CUTOUT_SHADOWS, 1);
		try {
			GlModel model = shadowModel(SOLID_MATERIAL, SOLID_MATERIAL);
			Pixel[] pixels = renderRecordedModel(model);
			long binds = toolkit.hdShadowMaterialBinds();

			System.out.println("[hd-shadow] recorded solid model: material binds " + binds
				+ ", left " + pixels[0] + ", right " + pixels[1]);
			assertAll(
				() -> assertEquals(0L, binds, "a material with no alpha channel is not bound"),
				() -> assertTrue(pixels[0].red <= 60, "the solid model still casts: " + pixels[0]),
				() -> assertTrue(pixels[1].red <= 60, "across its whole quad: " + pixels[1])
			);
		} finally {
			HdSettings.set(HdFeature.CUTOUT_SHADOWS, 0);
		}
	}

	@Test
	@DisplayName("a caster whose group table the client released still casts its whole silhouette")
	void releasedCaster_shouldStillCastItsSilhouette() {
		HdSettings.set(HdFeature.CUTOUT_SHADOWS, 1);
		try {
			// A real two-group caster: with the table in place it binds one material
			// texture per group, so the counter is evidence the table was there.
			GlModel caster = shadowModel(OPAQUE_MATERIAL, SECOND_OPAQUE_MATERIAL);
			boolean[] grouped = darkMask(caster);
			long bindsWithGroups = toolkit.hdShadowMaterialBinds();

			// The client's own release path: UA(mask) sets the model's new
			// preference mask and runs method2807, which drops the face-group
			// table, the material table and the texcoords while the geometry and
			// the uploaded vertex buffer stay. This is the state the live frame was
			// in when the depth pass faulted.
			int mask = caster.P();
			int releasedMask = mask & ~FACE_GROUP_BIT & ~GROUPED_FACES_BIT;
			caster.UA(releasedMask);

			boolean[] released = darkMask(caster);
			long bindsReleased = toolkit.hdShadowMaterialBinds();
			int error = GL11.glGetError();

			System.out.println("[hd-shadow] released caster: mask " + mask + " -> " + releasedMask
				+ ", cast px " + count(grouped) + " -> " + count(released) + ", material binds "
				+ bindsWithGroups + " -> " + bindsReleased + ", gl error " + error);
			assertAll(
				() -> assertTrue(count(grouped) > 0, "the grouped caster cast something to compare against"),
				() -> assertEquals(2L, bindsWithGroups, "two groups, one texture each"),
				// No group table, so no per-group draw: the whole mesh goes out once,
				// with no material bound, and still lands where the screen draw puts it.
				() -> assertEquals(0L, bindsReleased, "the released caster takes the position-only path"),
				() -> assertEquals(count(grouped), count(released),
					"the released caster casts its whole silhouette, not nothing: " + count(released)),
				() -> assertTrue(java.util.Arrays.equals(grouped, released),
					"and casts exactly the same pixels as before the release"),
				() -> assertEquals(GL11.GL_NO_ERROR, error, "the depth pass must leave no GL error")
			);
		} finally {
			HdSettings.set(HdFeature.CUTOUT_SHADOWS, 0);
		}
	}

	@Test
	@DisplayName("a copy of a released caster inherits no group table and still casts its silhouette")
	void copiedReleasedCaster_shouldStillCast() {
		HdSettings.set(HdFeature.CUTOUT_SHADOWS, 1);
		try {
			GlModel source = shadowModel(OPAQUE_MATERIAL, SECOND_OPAQUE_MATERIAL);
			int mask = source.P();
			// Release the source first, so the copy has nothing to inherit.
			source.UA(mask & ~FACE_GROUP_BIT & ~GROUPED_FACES_BIT);
			boolean[] releasedSource = darkMask(source);

			// The client's own copy entry point. The copy's mask still asks for
			// face groups (0x800), so nothing in its own update will drop the
			// table - and nothing rebuilds a table either, so the null it inherits
			// at method2802's {@code arg3.anIntArray215 = this.anIntArray215} stays
			// null however the copy is later drawn.
			GlModel copy = (GlModel) source.copy((byte) 0, mask, false);
			copy.uploadToGpu();

			boolean[] copied = darkMask(copy);
			long binds = toolkit.hdShadowMaterialBinds();
			int error = GL11.glGetError();

			System.out.println("[hd-shadow] copied released caster: mask " + copy.P() + ", cast px "
				+ count(releasedSource) + " -> " + count(copied) + ", material binds " + binds
				+ ", gl error " + error);
			assertAll(
				() -> assertTrue(count(releasedSource) > 0, "the released source cast something"),
				() -> assertTrue(count(copied) > 0, "the copy casts its silhouette, not nothing"),
				() -> assertEquals(0L, binds, "with no group table the copy takes the position-only path"),
				() -> assertTrue(java.util.Arrays.equals(releasedSource, copied),
					"the copy casts exactly the source's silhouette"),
				() -> assertEquals(GL11.GL_NO_ERROR, error, "the depth pass must leave no GL error")
			);
		} finally {
			HdSettings.set(HdFeature.CUTOUT_SHADOWS, 0);
		}
	}

	@Test
	@DisplayName("a copy with no index target is skipped by the pass rather than drawn from nothing")
	void copyWithoutIndexTarget_shouldBeSkipped() {
		HdSettings.set(HdFeature.CUTOUT_SHADOWS, 1);
		try {
			// Copying without the 0x800 bit gives the copy no aClass230_1 and no
			// texcoords: both of method2802's target branches hang off that one
			// bit, so a caster with texcoords released but an index target kept
			// cannot be built this way. drawShadowDepth returns on the missing
			// target before it touches any geometry, which is why this route can
			// never reach the group loop.
			GlModel source = shadowModel(OPAQUE_MATERIAL, SECOND_OPAQUE_MATERIAL);
			GlModel copy = (GlModel) source.copy((byte) 0, source.P() & ~FACE_GROUP_BIT, false);

			Pixel[] pixels = renderRecordedModel(copy);
			int error = GL11.glGetError();

			System.out.println("[hd-shadow] copy without an index target: left " + pixels[0]
					+ ", right " + pixels[1] + ", gl error " + error);
			assertAll(
				// Nothing was drawn from the copy, so the receiver keeps its lit
				// colour instead of being shadowed by geometry the pass never drew.
				() -> assertTrue(pixels[0].red >= 240 && pixels[1].red >= 240,
					"a caster with no target casts nothing: " + pixels[0] + ", " + pixels[1]),
				() -> assertEquals(GL11.GL_NO_ERROR, error, "the pass must leave no GL error")
			);
		} finally {
			HdSettings.set(HdFeature.CUTOUT_SHADOWS, 0);
		}
	}

	@Test
	@DisplayName("a caster whose material never streamed in casts its silhouette, not a hole")
	void casterWithMissingMaterial_shouldCastTheFullSilhouette() {
		HdSettings.set(HdFeature.CUTOUT_SHADOWS, 1);
		try {
			// Same geometry, an id the provider has no texture for: the pass cannot
			// alpha-test what it cannot sample, so it must fall back to the solid
			// silhouette rather than binding nothing and drawing a hole.
			GlModel missing = shadowModel(MISSING_MATERIAL, MISSING_MATERIAL);
			boolean[] withMissing = darkMask(missing);
			long binds = toolkit.hdShadowMaterialBinds();

			int error = GL11.glGetError();

			// The reference silhouette: the same model with a material that has no
			// alpha channel at all, which takes the same position-only path.
			GlModel solid = shadowModel(SOLID_MATERIAL, SOLID_MATERIAL);
			boolean[] solidMask = darkMask(solid);

			System.out.println("[hd-shadow] missing material caster: cast px " + count(withMissing)
				+ " vs solid " + count(solidMask) + ", material binds " + binds
				+ ", gl error " + error);
			assertAll(
				() -> assertTrue(count(withMissing) > 0, "an unstaged material must not silence its caster"),
				() -> assertEquals(0L, binds, "nothing is bound for a texture that does not exist"),
				() -> assertTrue(java.util.Arrays.equals(solidMask, withMissing),
					"the missing material casts the same solid silhouette as a solid one"),
				() -> assertEquals(GL11.GL_NO_ERROR, error, "the depth pass must leave no GL error")
			);
		} finally {
			HdSettings.set(HdFeature.CUTOUT_SHADOWS, 0);
		}
	}

	@Test
	@DisplayName("a recorded model's texcoords, not just its material, shape the shadow")
	void recordedModel_texCoordsShouldShapeTheShadow() {
		HdSettings.set(HdFeature.CUTOUT_SHADOWS, 1);
		try {
			// Same quads and texcoords, two materials: one fully opaque, one
			// opaque on half its texture. A constant texcoord would make the
			// half texture either cast the whole quad or miss it entirely;
			// varying texcoords leave lit pixels well inside the footprint.
			boolean[] opaque = darkMask(shadowModel(OPAQUE_MATERIAL, OPAQUE_MATERIAL));
			boolean[] half = darkMask(shadowModel(HALF_MATERIAL, HALF_MATERIAL));
			int area = SCAN_WIDTH * SCAN_HEIGHT;
			int opaqueDark = count(opaque);
			int halfDark = count(half);
			int halfLit = area - halfDark;

			System.out.println("[hd-shadow] texcoords shape the shadow: opaque " + opaqueDark
				+ " of " + area + ", half-texture " + halfDark + " dark / " + halfLit + " lit");
			assertAll(
				() -> assertTrue(opaqueDark > area * 9 / 10,
					"the opaque material must shadow the whole inner quad: " + opaqueDark +
						" of " + area),
				() -> assertTrue(halfDark > area / 10,
					"the half-opaque texture must still cast part of it: " + halfDark),
				() -> assertTrue(halfLit > area / 10,
					"and leave the rest lit, which only varying texcoords can do: " + halfLit)
			);
		} finally {
			HdSettings.set(HdFeature.CUTOUT_SHADOWS, 0);
		}
	}

	@Test
	@DisplayName("the pass owns the alpha test while it runs and restores all of it")
	void alphaTest_shouldBeOwnedThenRestored() {
		HdSettings.set(HdFeature.CUTOUT_SHADOWS, 1);
		try {
			beginFrame();
			applySun(0.0F, 0.0F, 1.0F);
			// What the scene may have left behind: the stock sprite path turns
			// the test on, and its reference is not always the toolkit's zero.
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.25F);
			int capsBefore = enabledCaps();
			int funcBefore = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC);
			float refBefore = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF);

			assertTrue(toolkit.hdSunShadowBegin(0.0F, 0.0F, 0.0F, VOLUME_RADIUS), "the pass should start");
			boolean cutoutEnabled = GL11.glIsEnabled(GL11.GL_ALPHA_TEST);
			int cutoutFunc = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC);
			float cutoutRef = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF);
			drawTexturedCaster(cutoutTexture);
			toolkit.hdSunShadowEnd();
			boolean enabledAfterCutout = GL11.glIsEnabled(GL11.GL_ALPHA_TEST);
			int funcAfterCutout = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC);
			float refAfterCutout = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF);
			int capsAfterCutout = enabledCaps();

			// Row off: the scene's test is still on and its reference would
			// reject the transparent texels, so the pass must switch the test
			// off itself rather than inherit it.
			HdSettings.set(HdFeature.CUTOUT_SHADOWS, 0);
			assertTrue(toolkit.hdSunShadowBegin(0.0F, 0.0F, 0.0F, VOLUME_RADIUS), "the off pass should start");
			boolean solidEnabled = GL11.glIsEnabled(GL11.GL_ALPHA_TEST);
			drawTexturedCaster(cutoutTexture);
			toolkit.hdSunShadowEnd();
			boolean enabledAfterSolid = GL11.glIsEnabled(GL11.GL_ALPHA_TEST);
			int funcAfterSolid = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC);
			float refAfterSolid = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF);
			int capsAfterSolid = enabledCaps();
			finishFrame();

			System.out.println("[hd-shadow] alpha test: scene " + funcBefore + "/" + refBefore
				+ " -> cut-out " + cutoutFunc + "/" + cutoutRef + " -> after " + funcAfterCutout
				+ "/" + refAfterCutout + "; row off during pass " + solidEnabled
				+ " -> after " + funcAfterSolid + "/" + refAfterSolid);
			assertAll(
				() -> assertEquals(GL11.GL_GEQUAL, funcBefore, "the fixture must start from a non-default function"),
				() -> assertTrue(cutoutEnabled, "the cut-out pass enables the test"),
				() -> assertEquals(GL11.GL_GREATER, cutoutFunc, "with its own function"),
				() -> assertEquals(ShadowCutout.THRESHOLD, cutoutRef, 1.0E-6F, "and its own reference"),
				() -> assertTrue(enabledAfterCutout, "and puts the scene's enable back"),
				() -> assertEquals(funcBefore, funcAfterCutout, "the scene's function is back"),
				() -> assertEquals(refBefore, refAfterCutout, 1.0E-6F, "the scene's reference is back"),
				() -> assertEquals(capsBefore, capsAfterCutout, "the enable mask is exactly as it was"),
				() -> assertFalse(solidEnabled,
					"the row-off pass switches the test off so the silhouette is solid"),
				() -> assertTrue(enabledAfterSolid, "and restores the scene's enable afterwards"),
				() -> assertEquals(funcBefore, funcAfterSolid, "with the scene's function"),
				() -> assertEquals(refBefore, refAfterSolid, 1.0E-6F, "and the scene's reference"),
				() -> assertEquals(capsBefore, capsAfterSolid, "the enable mask is untouched"),
				() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError(), "no GL error")
			);
		} finally {
			HdSettings.set(HdFeature.CUTOUT_SHADOWS, 0);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
		}
	}

	@Test
	@DisplayName("the map is rebuilt from the scene each frame, not baked")
	void map_shouldBeRebuiltEveryFrame() {
		int firstFrame = rebuild(0.0F, 0.0F, 1.0F, 0.0F);
		SunShadowMap map = toolkit.hdSunShadow();
		int frameIdAfterFirst = map.frameId();

		// Same sun, same caster: the next build must reproduce the shadow.
		rebuild(0.0F, 0.0F, 1.0F, 0.0F);
		int frameIdAfterSecond = map.frameId();

		// Same sun, caster moved a whole unit sideways: only a map built from
		// this frame's geometry can put the shadow there.
		rebuild(0.0F, 0.0F, 1.0F, 1.0F);
		Pixel movedCentre = pixel(128, 128);
		Pixel movedUnder = pixel(154, 128); // x = +1.02, where the caster now is

		System.out.println("[hd-shadow] rebuilt: frame " + frameIdAfterFirst + " -> " + frameIdAfterSecond
			+ " -> " + map.frameId() + ", caster at +1 gives centre " + movedCentre
			+ ", x=+1.02 " + movedUnder);
		assertAll(
			() -> assertEquals(frameIdAfterFirst + 1, frameIdAfterSecond, "one pass per frame"),
			() -> assertEquals(frameIdAfterSecond + 1, map.frameId(), "and it keeps counting"),
			() -> assertTrue(firstFrame <= 60, "frame one: the caster at the origin shadows the centre"),
			() -> assertTrue(movedCentre.red >= 240, "frame three: the old footprint is gone"),
			() -> assertTrue(movedUnder.red <= 60, "frame three: the shadow followed the caster")
		);
	}

	@Test
	@DisplayName("the frame seam runs one pass per frame, and the counters agree")
	void seam_shouldRunOnePassPerFrame() {
		HdSettings.set(HdFeature.SHADOW_MAP, 1);
		SunShadowFrame driver = toolkit.hdSunShadowDriver();
		long passesBefore = driver.passes();
		long framesBefore = driver.frames();
		final int[] casts = new int[1];

		SunShadowFrame.Casters occluder = new SunShadowFrame.Casters() {
			@Override
			public void drawCasters() {
				casts[0]++;
				GL11.glPushMatrix();
				toolkit.method1934(occluderColours, occluderNormals, null, occluderVertices);
				toolkit.method1967(0, 6, occluderIndices);
				GL11.glPopMatrix();
			}
		};

		long bindsBefore = toolkit.hdLightingBinds();
		beginFrame();
		applySun(0.0F, 0.0F, 1.0F);
		assertTrue(toolkit.hdSunShadowFrame(0.0F, 0.0F, 0.0F, VOLUME_RADIUS, occluder), "the seam should run");
		// The receiver goes through the toolkit's own model seam, the way the
		// game's model draw does: gate, shadow binding, draw, unbind.
		boolean bound = toolkit.hdBeginModelLighting(false);
		toolkit.method1934(receiverColours, receiverNormals, null, receiverVertices);
		toolkit.method1967(0, 6, receiverIndices);
		if (bound) {
			toolkit.hdEndModelLighting();
		}
		int centre = pixel(128, 128).red;
		long bindsAfter = toolkit.hdLightingBinds();
		finishFrame();

		long afterOne = driver.passes();
		for (int i = 0; i < 2; i++) {
			toolkit.hdSunShadowFrame(0.0F, 0.0F, 0.0F, VOLUME_RADIUS, occluder);
		}

		String stats = toolkit.hdLightingStats();
		System.out.println("[hd-shadow] seam: centre " + centre + ", " + stats);
		assertAll(
			() -> assertEquals(passesBefore + 1, afterOne, "one call, one pass"),
			() -> assertEquals(passesBefore + 3, driver.passes(), "and it keeps counting"),
			() -> assertEquals(framesBefore + 3, driver.frames()),
			() -> assertEquals(3, casts[0], "the casters ran once per pass"),
			() -> assertTrue(bound, "the toolkit's model seam bound the lighting program"),
			() -> assertEquals(bindsBefore + 1, bindsAfter, "and counted exactly one primitive"),
			() -> assertTrue(centre <= 60, "the pass filled the map the receiver samples: " + centre),
			() -> assertTrue(stats.contains("passes="), stats),
			() -> assertTrue(stats.contains("model binds="), stats)
		);
	}

	@Test
	@DisplayName("with the row off the seam refuses the frame before any caster is drawn")
	void seam_offShouldSkipTheFrame() {
		HdSettings.set(HdFeature.SHADOW_MAP, 0);
		SunShadowFrame driver = toolkit.hdSunShadowDriver();
		long passes = driver.passes();
		final int[] casts = new int[1];
		SunShadowFrame.Casters occluder = new SunShadowFrame.Casters() {
			@Override
			public void drawCasters() {
				casts[0]++;
				toolkit.method1934(occluderColours, occluderNormals, null, occluderVertices);
				toolkit.method1967(0, 6, occluderIndices);
			}
		};

		boolean rowOff = toolkit.hdSunShadowFrame(0.0F, 0.0F, 0.0F, VOLUME_RADIUS, occluder);
		HdSettings.setEnabled(false, null);
		HdSettings.set(HdFeature.SHADOW_MAP, 1);
		boolean hdOff = toolkit.hdSunShadowFrame(0.0F, 0.0F, 0.0F, VOLUME_RADIUS, occluder);
		HdSettings.setEnabled(true, null);

		assertAll(
			() -> assertFalse(rowOff, "row off must not start a pass"),
			() -> assertFalse(hdOff, "592HD off must not start a pass"),
			() -> assertEquals(passes, driver.passes(), "no pass either way"),
			() -> assertEquals(0, casts[0], "the stock frame's geometry was not submitted"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("a caster drawn inside the depth pass stays fixed function")
	void insidePass_shouldNotBindLighting() {
		HdSettings.set(HdFeature.SHADOW_MAP, 1);
		beginFrame();
		applySun(0.0F, 0.0F, 1.0F);
		assertTrue(toolkit.hdSunShadowBegin(0.0F, 0.0F, 0.0F, VOLUME_RADIUS));

		long binds = toolkit.hdLightingBinds();
		boolean bound = toolkit.hdBeginModelLighting(false);
		toolkit.method1934(occluderColours, occluderNormals, null, occluderVertices);
		toolkit.method1967(0, 6, occluderIndices);
		if (bound) {
			toolkit.hdEndModelLighting();
		}
		long after = toolkit.hdLightingBinds();
		toolkit.hdSunShadowEnd();
		finishFrame();

		assertAll(
			() -> assertFalse(bound, "depth only: the lighting program must not be bound"),
			() -> assertEquals(binds, after, "and the bind counter must not move")
		);
	}

	@Test
	@DisplayName("a frame's shadow work fits in a frame")
	void cost_shouldBeMeasured() {
		HdSettings.set(HdFeature.SHADOW_MAP, 2); // 2048
		assertTrue(toolkit.hdSunShadowBegin(0.0F, 0.0F, 0.0F, VOLUME_RADIUS));
		toolkit.hdSunShadowEnd();

		int warmup = 10;
		int passes = 60;
		for (int i = 0; i < warmup; i++) {
			emptyFrame();
			passOnly();
			shadedFrame(2);
			unshadowedFrame();
		}

		// Absolute per-frame times at one quad are dominated by JIT and driver
		// drift - measured separately, an empty frame came out slower than one
		// with a 2048 depth pass. So the two bodies are interleaved and only the
		// paired difference is reported, which is what the feature actually costs.
		double passCost = pairedMillis(passes, () -> emptyFrame(), () -> passOnly());
		double frameCost = pairedMillis(passes, () -> unshadowedFrame(), () -> shadedFrame(2));
		System.out.println("[hd-shadow] cost (2048 map, 1 caster): depth pass adds " + ms(passCost)
			+ ", shadowing a lit frame adds " + ms(frameCost) + " (paired, 60 frames)");

		assertAll(
			() -> assertTrue(passCost < 4.0D, "a 2048 depth pass with one caster must cost a few ms at most: " + passCost),
			() -> assertTrue(frameCost < 8.0D, "and the whole stage must fit in a 60 Hz frame: " + frameCost)
		);
	}

	@Test
	@DisplayName("the scene's submission places each caster and rebuilds the map every frame")
	void submission_shouldPlaceEachCasterAndRebuild() {
		HdSettings.set(HdFeature.SHADOW_MAP, 1);
		HdSettings.set(HdFeature.LIGHTING_DETAIL, 0); // hard edge, so the pixel is unambiguous
		SunShadowSubmission submission = toolkit.hdShadowSubmission();
		submission.reset();
		ShadowCaster.Draw broad = new ShadowCaster.Draw() {
			@Override
			public void draw() {
				toolkit.method1934(wideOccluderColours, wideOccluderNormals, null, wideOccluderVertices);
				toolkit.method1967(0, 6, occluderIndices);
			}
		};

		// Frame one: two casters, one to either side of the centre. The per-object
		// translation is the whole point - one draw, two places.
		beginFrame();
		applySun(0.0F, 0.0F, 1.0F);
		submission.beginFrame(0.0F, 0.0F, 0.0F, VOLUME_RADIUS);
		submission.addAt(-2.0F, 0.0F, 0.0F, broad);
		submission.addAt(2.0F, 0.0F, 0.0F, broad);
		assertTrue(submission.submit(toolkit), "the submission should run the pass");
		long drawnFirst = submission.castersSubmitted();
		int firstFrame = toolkit.hdSunShadow().frameId();
		Pixel left = drawModelReceiver(77, 128);
		Pixel centre = drawModelReceiver(128, 128);
		Pixel right = drawModelReceiver(179, 128);
		finishFrame();

		// Frame two: one caster at the centre. Only a map built from this frame
		// can lose the two shadows frame one had.
		beginFrame();
		applySun(0.0F, 0.0F, 1.0F);
		submission.beginFrame(0.0F, 0.0F, 0.0F, VOLUME_RADIUS);
		submission.addAt(0.0F, 0.0F, 0.0F, broad);
		assertTrue(submission.submit(toolkit), "the next frame should run its own pass");
		long drawnSecond = submission.castersSubmitted();
		int secondFrame = toolkit.hdSunShadow().frameId();
		Pixel secondCentre = drawModelReceiver(128, 128);
		Pixel secondLeft = drawModelReceiver(77, 128);
		finishFrame();

		System.out.println("[hd-shadow] submission: frame " + firstFrame + " -> " + secondFrame
			+ ", casters " + drawnFirst + " -> " + drawnSecond + ", left " + left
			+ ", centre " + centre + ", right " + right + " | next frame centre "
			+ secondCentre + ", left " + secondLeft);
		assertAll(
			() -> assertEquals(2L, drawnFirst, "both casters were drawn into the map"),
			() -> assertEquals(1L, drawnSecond, "the next frame submits only its own caster"),
			() -> assertEquals(firstFrame + 1, secondFrame, "one pass per frame"),
			() -> assertTrue(left.red <= 60, "the caster at -2 shadows x = -2: " + left),
			() -> assertTrue(right.red <= 60, "the caster at +2 shadows x = +2: " + right),
			() -> assertTrue(centre.red >= 240, "between them the receiver sees the sun: " + centre),
			() -> assertTrue(secondCentre.red <= 60, "the centre caster now shadows the centre: " + secondCentre),
			() -> assertTrue(secondLeft.red >= 240, "frame one's left shadow is gone: " + secondLeft)
		);
	}

	@Test
	@DisplayName("the frame seam runs the pass before the world, and the row off declines it with no GL")
	void frameSeam_shouldRunOnceAndDeclineWhenOff() {
		HdSettings.set(HdFeature.SHADOW_MAP, 1);
		SunShadowSubmission submission = toolkit.hdShadowSubmission();
		submission.reset();
		Toolkit previous = MonoStereoData.aClass19_16;
		MonoStereoData.aClass19_16 = toolkit;
		try {
			beginFrame();
			applySun(0.0F, 0.0F, 1.0F);
			boolean ran = HdFrameSequence.drawSunShadows(0.0F, 0.0F, 0.0F);
			int afterRun = toolkit.hdSunShadow().frameId();
			long passesOn = submission.passes();

			HdSettings.set(HdFeature.SHADOW_MAP, 0);
			boolean declined = HdFrameSequence.drawSunShadows(0.0F, 0.0F, 0.0F);
			int afterDecline = toolkit.hdSunShadow().frameId();

			// A caster offered while the row is off must not be drawn at all.
			final int[] draws = new int[1];
			submission.beginFrame(0.0F, 0.0F, 0.0F);
			submission.addAt(0.0F, 0.0F, 0.0F, new ShadowCaster.Draw() {
				@Override
				public void draw() {
					draws[0]++;
				}
			});
			boolean direct = submission.submit(toolkit);
			long drawnOff = submission.castersSubmitted();

			// The receiver still draws through the live seam: with the row off
			// there is no binding, so it is the stock, fully lit surface.
			Pixel lit = drawModelReceiver(128, 128);
			finishFrame();

			System.out.println("[hd-shadow] seam: ran=" + ran + " passes=" + passesOn
				+ " declined=" + declined + " caster draws=" + draws[0] + " row-off pixel " + lit);
			assertAll(
				() -> assertTrue(ran, "row on: the seam runs the scene's pass"),
				() -> assertEquals(1L, passesOn, "one seam call, one pass"),
				() -> assertFalse(declined, "row off: the seam declines"),
				() -> assertEquals(afterRun, afterDecline, "and the map is not rebuilt"),
				() -> assertFalse(direct, "a direct submission is declined too"),
				() -> assertEquals(0, draws[0], "no caster is drawn with the row off"),
				() -> assertEquals(0L, drawnOff, "and the submission counts no casters"),
				() -> assertTrue(lit.red >= 240, "the row-off receiver is the plain lit surface: " + lit),
				() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
			);
		} finally {
			MonoStereoData.aClass19_16 = previous;
			HdSettings.set(HdFeature.SHADOW_MAP, 1);
		}
	}

	@Test
	@DisplayName("the seam submits the casters the scene collected at that same point")
	void seam_shouldKeepCollectedCasters() {
		HdSettings.set(HdFeature.SHADOW_MAP, 1);
		SunShadowSubmission submission = toolkit.hdShadowSubmission();
		submission.reset();
		Toolkit previous = MonoStereoData.aClass19_16;
		MonoStereoData.aClass19_16 = toolkit;
		try {
			beginFrame();
			applySun(0.0F, 0.0F, 1.0F);
			submission.beginFrame(0.0F, 0.0F, 0.0F, VOLUME_RADIUS);
			submission.addAt(0.0F, 0.0F, 0.0F, new ShadowCaster.Draw() {
				@Override
				public void draw() {
					toolkit.method1934(wideOccluderColours, wideOccluderNormals, null, wideOccluderVertices);
					toolkit.method1967(0, 6, occluderIndices);
				}
			});
			boolean ran = HdFrameSequence.drawSunShadows(0.0F, 0.0F, 0.0F);
			long drawn = submission.castersSubmitted();
			long frames = submission.frames();
			finishFrame();

			System.out.println("[hd-shadow] seam with collected casters: ran=" + ran
				+ ", drawn=" + drawn + ", frames=" + frames);
			assertAll(
				() -> assertTrue(ran, "the seam should run the pass"),
				() -> assertEquals(1L, drawn, "the scene's caster must not be discarded by the seam"),
				() -> assertEquals(1L, frames, "one seam call is one frame")
			);
		} finally {
			MonoStereoData.aClass19_16 = previous;
		}
	}

	@Test
	@DisplayName("the pass owns its whole target: the scene's scissor is ignored, then restored")
	void pass_shouldIgnoreAndRestoreSceneScissor() {
		HdSettings.set(HdFeature.SHADOW_MAP, 1);
		beginFrame();
		applySun(0.0F, 0.0F, 1.0F);

		// What an interface or a viewport corner does to the scene draw. Left on
		// inside the pass it would clip the map to an 8x8 corner of the target.
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glScissor(0, 0, 8, 8);
		assertTrue(toolkit.hdSunShadowBegin(0.0F, 0.0F, 0.0F, VOLUME_RADIUS), "the pass should start");
		int[] scissorDuringPass = new int[1];
		GL11.glGetIntegerv(GL11.GL_SCISSOR_TEST, scissorDuringPass);
		toolkit.method1934(wideOccluderColours, wideOccluderNormals, null, wideOccluderVertices);
		toolkit.method1967(0, 6, occluderIndices);
		toolkit.hdSunShadowEnd();

		boolean scissorAfterPass = GL11.glIsEnabled(GL11.GL_SCISSOR_TEST);
		int[] boxAfterPass = new int[4];
		GL11.glGetIntegerv(GL11.GL_SCISSOR_BOX, boxAfterPass);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		finishFrame();

		System.out.println("[hd-shadow] scissor during pass=" + scissorDuringPass[0]
			+ " after=" + scissorAfterPass + " box=" + boxAfterPass[2] + "x" + boxAfterPass[3]);
		assertAll(
			() -> assertEquals(0, scissorDuringPass[0], "the pass must switch the scene's scissor off"),
			() -> assertTrue(scissorAfterPass, "and put the scene's scissor back"),
			() -> assertArrayEquals(new int[] { 0, 0, 8, 8 }, boxAfterPass, "with its rectangle")
		);
	}

	@Test
	@DisplayName("a model the world draw submits casts on the next frame's map")
	void worldDrawModel_shouldCastNextFrame() {
		HdSettings.set(HdFeature.SHADOW_MAP, 1);
		HdSettings.set(HdFeature.LIGHTING_DETAIL, 0);
		GlModel caster = triangleModel();
		// A second model, so the counters have to track a list rather than one.
		GlModel second = triangleModel();
		SunShadowSubmission submission = toolkit.hdShadowSubmission();
		submission.reset();
		Toolkit previous = MonoStereoData.aClass19_16;
		MonoStereoData.aClass19_16 = toolkit;
		try {
			// Frame N: the world draw's collection window, and the model draw's own
			// submission point. Nothing is in the map yet.
			toolkit.hdShadowSceneBegin();
			toolkit.hdRecordShadowCaster(caster, new JavaMatrix());
			toolkit.hdRecordShadowCaster(second, new JavaMatrix());
			toolkit.hdShadowSceneEnd();
			int recordedNow = submission.casters().size();

			// Frame N+1: the scene names the volume it needs, then the seam drains
			// what the draw recorded and fills the map. The submission's floor is
			// the smallest volume it will honour, which at 1024 texels is half a
			// scene unit per texel - fine for an object the size of a loc.
			beginFrame();
			applySun(0.0F, 0.0F, 1.0F);
			submission.beginFrame(0.0F, 0.0F, 0.0F, ShadowVolume.MIN_RADIUS);
			boolean ran = HdFrameSequence.drawSunShadows(0.0F, 0.0F, 0.0F);
			long recorded = submission.recorded();
			long drawn = submission.castersSubmitted();
			Pixel under = drawModelReceiver(56, 128); // x = -2.8, under the model
			Pixel beside = drawModelReceiver(200, 128); // x = +2.8, clear of it
			String stats = toolkit.hdLightingStats();
			finishFrame();

			System.out.println("[hd-shadow] model caster: recorded at draw=" + recordedNow
				+ ", seam ran=" + ran + ", recorded=" + recorded + ", drawn=" + drawn
				+ ", under " + under + ", beside " + beside + ", " + stats);
			assertAll(
				() -> assertEquals(0, recordedNow, "the draw records for the next seam, not the current map"),
				() -> assertTrue(ran, "the seam should run the pass"),
				() -> assertEquals(2L, recorded, "the two models the world draw recorded reached the seam"),
				() -> assertEquals(2L, drawn, "and the count follows what was drawn into the map"),
				() -> assertTrue(stats.contains("recorded=2"),
					"the session's stats must report what the draw fed: " + stats),
				() -> assertTrue(under.red <= 60, "the model must shadow the ground it covers: " + under),
				() -> assertTrue(beside.red >= 240, "and only what it covers: " + beside)
			);

			// Row off: the same world draw records nothing and the seam makes no GL.
			HdSettings.set(HdFeature.SHADOW_MAP, 0);
			int frameIdBefore = toolkit.hdSunShadow().frameId();
			toolkit.hdShadowSceneBegin();
			toolkit.hdRecordShadowCaster(caster, new JavaMatrix());
			toolkit.hdShadowSceneEnd();
			boolean off = HdFrameSequence.drawSunShadows(0.0F, 0.0F, 0.0F);
			int frameIdAfter = toolkit.hdSunShadow().frameId();
			finishFrame();

			System.out.println("[hd-shadow] model caster row off: ran=" + off + ", map " + frameIdBefore
				+ " -> " + frameIdAfter + ", recorded=" + submission.recorded());
			assertAll(
				() -> assertFalse(off, "row off must not run the pass"),
				() -> assertEquals(frameIdBefore, frameIdAfter, "and must not rebuild the map"),
				() -> assertEquals(0L, submission.recorded(), "the draw recorded no caster with the row off")
			);

			// 592HD off: the same draw records nothing and the seam makes no GL.
			HdSettings.setEnabled(false, null);
			try {
				int hdOffFrame = toolkit.hdSunShadow().frameId();
				toolkit.hdShadowSceneBegin();
				toolkit.hdRecordShadowCaster(caster, new JavaMatrix());
				toolkit.hdShadowSceneEnd();
				boolean hdOff = HdFrameSequence.drawSunShadows(0.0F, 0.0F, 0.0F);
				int hdOffAfter = toolkit.hdSunShadow().frameId();
				finishFrame();

				System.out.println("[hd-shadow] model caster hd off: ran=" + hdOff + ", map " + hdOffFrame
					+ " -> " + hdOffAfter + ", recorded=" + submission.recorded());
				assertAll(
					() -> assertFalse(hdOff, "592HD off must not run the pass"),
					() -> assertEquals(hdOffFrame, hdOffAfter, "and must not rebuild the map"),
					() -> assertEquals(0L, submission.recorded(),
						"the draw recorded no caster with 592HD off")
				);
			} finally {
				HdSettings.setEnabled(true, null);
			}
		} finally {
			MonoStereoData.aClass19_16 = previous;
			HdSettings.set(HdFeature.SHADOW_MAP, 1);
		}
	}

	// -- Scene helpers ----------------------------------------------------

	/**
	 * Rebuilds the map, draws the occluder into it, then draws the receiver with
	 * the lighting shader sampling it at the requested PCF radius.
	 */
	private static void renderShadowed(float sunX, float sunY, float sunZ, int radius) {
		beginFrame();
		applySun(sunX, sunY, sunZ);

		if (!toolkit.hdSunShadowBegin(0.0F, 0.0F, 0.0F, VOLUME_RADIUS)) {
			throw new AssertionError("the shadow pass refused to start");
		}
		toolkit.method1934(occluderColours, occluderNormals, null, occluderVertices);
		toolkit.method1967(0, 6, occluderIndices);
		toolkit.hdSunShadowEnd();

		SunShadowMap map = toolkit.hdSunShadow();
		assertTrue(map.hasFrame(), "the pass should have filled the map");
		toolkit.hdBindShadowTexture(map.textureName());
		boolean bound = pipeline.begin(HdDrawRequest.current(HdDrawRows.of(Row.NORMALS, Row.FOG), 0, 7681),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withShadow(map.binding(radius)));
		assertTrue(bound, "the gate refused the shadowed draw");
		toolkit.method1934(receiverColours, receiverNormals, null, receiverVertices);
		toolkit.method1967(0, 6, receiverIndices);
		pipeline.unbind();

		finishFrame();
	}

	/** The toolkit's own seam: row on means a shadowed receiver, no local pipeline. */
	private static Pixel drawToolkitLighting() {
		beginFrame();
		applySun(0.0F, 0.0F, 1.0F);
		if (HdSettings.value(HdFeature.SHADOW_MAP) != 0) {
			assertTrue(toolkit.hdSunShadowBegin(0.0F, 0.0F, 0.0F, VOLUME_RADIUS));
			toolkit.method1934(occluderColours, occluderNormals, null, occluderVertices);
			toolkit.method1967(0, 6, occluderIndices);
			toolkit.hdSunShadowEnd();
		}
		boolean bound = toolkit.hdBeginModelLighting(false);
		assertTrue(bound);
		toolkit.method1934(receiverColours, receiverNormals, null, receiverVertices);
		toolkit.method1967(0, 6, receiverIndices);
		toolkit.hdEndModelLighting();
		finishFrame();
		return pixel(128, 128);
	}

	private static Pixel drawFixed() {
		beginFrame();
		applySun(0.0F, 0.0F, 1.0F);
		toolkit.method1938(-1, true);
		toolkit.method1934(receiverColours, receiverNormals, null, receiverVertices);
		toolkit.method1967(0, 6, receiverIndices);
		finishFrame();
		return pixel(128, 128);
	}

	/**
	 * One frame whose caster is textured: the depth pass draws it exactly as a
	 * model draw would (texcoord pointer + bound material texture), then the
	 * receiver is lit with the map just built.
	 */
	private static void renderTexturedShadow(int texture) {
		beginFrame();
		applySun(0.0F, 0.0F, 1.0F);
		assertTrue(toolkit.hdSunShadowBegin(0.0F, 0.0F, 0.0F, VOLUME_RADIUS), "the pass refused to start");
		drawTexturedCaster(texture);
		toolkit.hdSunShadowEnd();

		SunShadowMap map = toolkit.hdSunShadow();
		assertTrue(map.hasFrame(), "the pass should have filled the map");
		toolkit.hdBindShadowTexture(map.textureName());
		assertTrue(pipeline.begin(HdDrawRequest.current(HdDrawRows.of(Row.NORMALS, Row.FOG), 0, 7681),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withShadow(map.binding(2))), "the gate refused the shadowed draw");
		toolkit.method1934(receiverColours, receiverNormals, null, receiverVertices);
		toolkit.method1967(0, 6, receiverIndices);
		pipeline.unbind();
		finishFrame();
	}

	/**
	 * A caster drawn the way a material model draws: its own texture on unit 0,
	 * texture coordinates from the interleaved vertex buffer. Nothing here is
	 * shadow-specific except that the pass is open, which is the point.
	 */
	private static void drawTexturedCaster(int texture) {
		toolkit.method1936(0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		toolkit.method1934(texturedCasterColours, texturedCasterNormals, texturedCasterTexCoords,
			texturedCasterVertices);
		toolkit.method1967(0, 6, occluderIndices);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	/** A white RGBA texture; when {@code cutout}, the +u half is transparent. */
	private static int rgbaTexture(boolean cutout) {
		toolkit.method1936(0);
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		ByteBuffer data = BufferUtils.createByteBuffer(64 * 64 * 4);
		for (int y = 0; y < 64; y++) {
			for (int x = 0; x < 64; x++) {
				data.put((byte) 255).put((byte) 255).put((byte) 255)
					.put((byte) (cutout && x >= 32 ? 0 : 255));
			}
		}
		data.flip();
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, 64, 64, 0, GL11.GL_RGBA,
			GL11.GL_UNSIGNED_BYTE, data);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return texture;
	}

	/**
	 * The recorded-model proof: the scene records a real GlModel through the
	 * world draw's seam, the seam drains it into the pass, and the receiver is
	 * sampled under its two quads' centres.
	 */
	private static Pixel[] renderRecordedModel(GlModel model) {
		return renderRecordedModel(model, null);
	}

	/** As above, also filling in the dark pixels of the left quad's footprint. */
	private static Pixel[] renderRecordedModel(GlModel model, boolean[] darkMask) {
		Toolkit previous = MonoStereoData.aClass19_16;
		MonoStereoData.aClass19_16 = toolkit;
		SunShadowSubmission submission = toolkit.hdShadowSubmission();
		submission.reset();
		try {
			HdSettings.set(HdFeature.SHADOW_MAP, 1);
			HdSettings.set(HdFeature.LIGHTING_DETAIL, 0); // hard edge, unambiguous pixels
			beginFrame();
			applySun(0.0F, 0.0F, 1.0F);
			toolkit.hdShadowSceneBegin();
			toolkit.hdRecordShadowCaster(model, new JavaMatrix());
			toolkit.hdShadowSceneEnd();
			submission.beginFrame(0.0F, 0.0F, 0.0F, VOLUME_RADIUS);
			assertTrue(HdFrameSequence.drawSunShadows(0.0F, 0.0F, 0.0F), "the seam should run");

			// The two quads sit at x = -4..-1 and 1..4, y = -4..4; each sample is
			// at its quad's centre, several shadow texels from any edge.
			Pixel left = drawModelReceiver(64, 128);
			Pixel right = drawModelReceiver(192, 128);
			if (darkMask != null) {
				readDarkMask(darkMask);
			}
			finishFrame();
			return new Pixel[] { left, right };
		} finally {
			MonoStereoData.aClass19_16 = previous;
			HdSettings.set(HdFeature.LIGHTING_DETAIL, 1);
		}
	}

	/**
	 * The sampled part of the left quad's footprint: world x -2.97..-1.95,
	 * y -1.5..1.5. One world unit from every quad edge, so penumbra from the
	 * silhouette cannot reach it and only the material's alpha can.
	 */
	private static final int SCAN_MIN_X = 52;

	private static final int SCAN_MAX_X = 78;

	private static final int SCAN_MIN_Y = 90;

	private static final int SCAN_MAX_Y = 166;

	private static final int SCAN_WIDTH = SCAN_MAX_X - SCAN_MIN_X + 1;

	private static final int SCAN_HEIGHT = SCAN_MAX_Y - SCAN_MIN_Y + 1;

	/** The receiver's shadowed colour is ~51, its lit colour 255. */
	private static void readDarkMask(boolean[] mask) {
		ByteBuffer row = BufferUtils.createByteBuffer(SIZE * 4);
		for (int y = SCAN_MIN_Y; y <= SCAN_MAX_Y; y++) {
			GL11.glReadPixels(0, y, SIZE, 1, GL11.GL_RED, GL11.GL_UNSIGNED_BYTE, row);
			for (int x = SCAN_MIN_X; x <= SCAN_MAX_X; x++) {
				mask[(y - SCAN_MIN_Y) * SCAN_WIDTH + (x - SCAN_MIN_X)] = (row.get(x) & 0xFF) <= 100;
			}
		}
	}

	private static boolean[] darkMask(GlModel model) {
		boolean[] mask = new boolean[SCAN_WIDTH * SCAN_HEIGHT];
		renderRecordedModel(model, mask);
		return mask;
	}

	private static int count(boolean[] mask) {
		int count = 0;
		for (boolean dark : mask) {
			if (dark) {
				count++;
			}
		}
		return count;
	}

	/**
	 * A real two-group model at z = 1: each quad names its own material, so the
	 * caster's draw has to bind the two textures separately to reproduce the
	 * screen shape.
	 */
	private static GlModel shadowModel(int leftMaterial, int rightMaterial) {
		Mesh mesh = new Mesh(8, 4, 1);
		mesh.method3042();
		// Two flat quads at z = 1, one each side of the screen centre, tall
		// enough that a centre-row sample sits well inside each footprint.
		int leftA = mesh.method3040(1, -4, -4);
		int leftB = mesh.method3040(1, -4, -1);
		int leftC = mesh.method3040(1, 4, -1);
		int leftD = mesh.method3040(1, 4, -4);
		// A planar texture basis across the left quad: u = 0 at its left edge,
		// 1 at its right, so the model's texcoords vary with the geometry.
		mesh.aByteArray47[0] = 0;
		mesh.aShortArray118[0] = (short) leftA;
		mesh.aShortArray116[0] = (short) leftB;
		mesh.aShortArray124[0] = (short) leftC;
		mesh.method3047(leftA, (short) leftMaterial, (byte) 0, leftB, leftC, (short) 127, (byte) 0, (byte) 0);
		mesh.method3047(leftA, (short) leftMaterial, (byte) 0, leftC, leftD, (short) 127, (byte) 0, (byte) 0);
		int rightA = mesh.method3040(1, -4, 1);
		int rightB = mesh.method3040(1, -4, 4);
		int rightC = mesh.method3040(1, 4, 4);
		int rightD = mesh.method3040(1, 4, 1);
		mesh.method3047(rightA, (short) rightMaterial, (byte) 0, rightB, rightC, (short) 127, (byte) 0, (byte) 0);
		mesh.method3047(rightA, (short) rightMaterial, (byte) 0, rightC, rightD, (short) 127, (byte) 0, (byte) 0);
		mesh.maxDepth = mesh.anInt3668;
		mesh.anIntArray245 = null;
		mesh.anIntArray246 = null;
		mesh.aByteArray42 = null;
		Model model = toolkit.createModel(mesh, 51200, 33, 64, 768);
		model.uploadToGpu();
		return (GlModel) model;
	}

	/**
	 * A real model, built without a cache: one triangle in the x/y plane at
	 * z = 1, the unit `applySun` puts between the receiver and the sun.
	 */
	private static GlModel triangleModel() {
		// One texture slot and one face group, exactly what stock model builders
		// allocate: without the group the per-vertex face list is never built and
		// the model packs no positions into its buffer.
		Mesh mesh = new Mesh(3, 1, 1);
		mesh.method3042();
		// The mesh's first argument is the buffer's z, so a constant first
		// argument is a flat triangle at that z - face-on to the sun applySun
		// puts on +z, one unit above the receiver at z = 0. It is tile-sized and
		// sits to the left of the screen centre, so a fragment under it and one
		// beside it are both meaningful samples of an ordinary object.
		int first = mesh.method3040(1, -128, -128);
		int second = mesh.method3040(1, -128, -2);
		int third = mesh.method3040(1, 128, -2);
		mesh.method3047(first, (short) -1, (byte) -1, second, third, (short) 127, (byte) 0, (byte) 0);
		mesh.maxDepth = mesh.anInt3668;
		mesh.anIntArray245 = null;
		mesh.anIntArray246 = null;
		mesh.aByteArray42 = null;
		Model model = toolkit.createModel(mesh, 51200, 33, 64, 768);
		model.uploadToGpu();
		return (GlModel) model;
	}

	/**
	 * Draws the receiver through the toolkit's own model seam - gate, shadow
	 * binding, draw, unbind - and samples one pixel. The frame is left open for
	 * further draws; the caller finishes it.
	 */
	private static Pixel drawModelReceiver(int sampleX, int sampleY) {
		boolean bound = toolkit.hdBeginModelLighting(false);
		assertTrue(bound, "the toolkit's model seam should bind for a lit receiver");
		toolkit.method1934(receiverColours, receiverNormals, null, receiverVertices);
		toolkit.method1967(0, 6, receiverIndices);
		toolkit.hdEndModelLighting();
		return pixel(sampleX, sampleY);
	}

	/**
	 * The mean extra milliseconds {@code withWork} costs over {@code baseline},
	 * measured in pairs inside one loop so drift hits both equally. Both bodies
	 * end in {@code glFinish}, so this is wall time per frame, GPU included.
	 */
	private static double pairedMillis(int frames, Runnable baseline, Runnable withWork) {
		double total = 0.0D;
		for (int i = 0; i < frames; i++) {
			long start = System.nanoTime();
			baseline.run();
			long middle = System.nanoTime();
			withWork.run();
			long end = System.nanoTime();
			total += (end - middle) - (middle - start);
		}
		return total / 1.0E6D / frames;
	}

	private static String ms(double millis) {
		return String.format("%.3f", millis) + " ms";
	}

	/** The frame floor: bind the target, clear it, finish. */
	private static void emptyFrame() {
		beginFrame();
		finishFrame();
	}

	private static void passOnly() {
		beginFrame();
		applySun(0.0F, 0.0F, 1.0F);
		toolkit.hdSunShadowBegin(0.0F, 0.0F, 0.0F, VOLUME_RADIUS);
		toolkit.method1934(occluderColours, occluderNormals, null, occluderVertices);
		toolkit.method1967(0, 6, occluderIndices);
		toolkit.hdSunShadowEnd();
		// Every timed body ends in a finish, or the figures measure command
		// submission while only the empty frame measures the GPU.
		finishFrame();
	}

	private static void shadedFrame(int radius) {
		renderShadowed(0.0F, 0.0F, 1.0F, radius);
	}

	private static void unshadowedFrame() {
		beginFrame();
		applySun(0.0F, 0.0F, 1.0F);
		boolean bound = pipeline.begin(HdDrawRequest.current(HdDrawRows.of(Row.NORMALS, Row.FOG), 0, 7681),
			HdDrawSpace.MODEL,
			HdFrameState.of(0));
		toolkit.method1934(receiverColours, receiverNormals, null, receiverVertices);
		toolkit.method1967(0, 6, receiverIndices);
		if (bound) {
			pipeline.unbind();
		}
		finishFrame();
	}

	/**
	 * One frame built from scratch: the caster at {@code casterX}, the receiver
	 * sampled with the map just built. Returns the centre pixel.
	 */
	private static int rebuild(float sunX, float sunY, float sunZ, float casterX) {
		beginFrame();
		applySun(sunX, sunY, sunZ);
		assertTrue(toolkit.hdSunShadowBegin(0.0F, 0.0F, 0.0F, VOLUME_RADIUS), "the pass refused to start");
		GL11.glPushMatrix();
		GL11.glTranslatef(casterX, 0.0F, 0.0F);
		toolkit.method1934(occluderColours, occluderNormals, null, occluderVertices);
		toolkit.method1967(0, 6, occluderIndices);
		GL11.glPopMatrix();
		toolkit.hdSunShadowEnd();

		SunShadowMap map = toolkit.hdSunShadow();
		toolkit.hdBindShadowTexture(map.textureName());
		assertTrue(pipeline.begin(HdDrawRequest.current(HdDrawRows.of(Row.NORMALS, Row.FOG), 0, 7681),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withShadow(map.binding(2))), "the gate refused the shadowed draw");
		toolkit.method1934(receiverColours, receiverNormals, null, receiverVertices);
		toolkit.method1967(0, 6, receiverIndices);
		pipeline.unbind();

		finishFrame();
		return pixel(128, 128).red;
	}

	/** Whatever the scene hands the toolkit: ambient, sun, no anti-sun, fog far away. */
	private static void applySun(float x, float y, float z) {
		toolkit.B(0.2F);
		toolkit.scaleHeight(0xFFFFFF, 1.0F, 0.0F, x, y, z);
		toolkit.UA(0xFF0000, 1, 0);
		toolkit.method1918(true);
		toolkit.copyLights(0, new com.jagex.game.runetek5.cache.node.PointLight[0]);
	}

	private static void beginFrame() {
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, framebuffer);
		GL11.glViewport(0, 0, SIZE, SIZE);
		GL11.glDisable(GL11.GL_DITHER);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glClearColor(0.0F, 0.0F, 1.0F, 1.0F);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glFrustum(-0.25D, 0.25D, -0.25D, 0.25D, 1.0D, 100.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -EYE);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	private static void finishFrame() {
		GL11.glFinish();
		assertEquals(GL11.GL_NO_ERROR, GL11.glGetError(), "GL error while drawing");
	}

	/** The enable flags a fixed-function model draw depends on, as a bitmask. */
	private static int enabledCaps() {
		int[] caps = { GL11.GL_LIGHTING, GL11.GL_TEXTURE_2D, GL11.GL_DEPTH_TEST, GL11.GL_BLEND,
			GL11.GL_ALPHA_TEST, GL11.GL_CULL_FACE, GL11.GL_COLOR_MATERIAL, GL11.GL_FOG,
			GL11.GL_NORMALIZE, GL11.GL_SCISSOR_TEST };
		int mask = 0;
		for (int i = 0; i < caps.length; i++) {
			if (GL11.glIsEnabled(caps[i])) {
				mask |= 1 << i;
			}
		}
		return mask;
	}

	private static int partialPixels() {
		// Scan the row through the shadow's x edge (world y = 0) and count the
		// pixels that are neither ambient-only nor fully lit.
		ByteBuffer row = BufferUtils.createByteBuffer(SIZE * 4);
		GL11.glReadPixels(0, SIZE / 2, SIZE, 1, GL11.GL_RED, GL11.GL_UNSIGNED_BYTE, row);
		int partial = 0;
		for (int i = 0; i < SIZE; i++) {
			int value = row.get(i) & 0xFF;
			if (value > 70 && value < 240) {
				partial++;
			}
		}
		return partial;
	}

	private static Pixel pixel(int x, int y) {
		ByteBuffer pixel = BufferUtils.createByteBuffer(4);
		GL11.glReadPixels(x, y, 1, 1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixel);
		return new Pixel(pixel.get(0) & 0xFF, pixel.get(1) & 0xFF, pixel.get(2) & 0xFF);
	}

	private static void createTarget() {
		framebuffer = EXTFramebufferObject.glGenFramebuffersEXT();
		targetTexture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, targetTexture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, SIZE, SIZE, 0,
			GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		depthBuffer = EXTFramebufferObject.glGenRenderbuffersEXT();
		EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, depthBuffer);
		EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT,
			GL14.GL_DEPTH_COMPONENT24, SIZE, SIZE);

		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, framebuffer);
		EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
			EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, targetTexture, 0);
		EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
			EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, depthBuffer);
		int status = EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT);
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		assertEquals(EXTFramebufferObject.GL_FRAMEBUFFER_COMPLETE_EXT, status, "offscreen target incomplete");
	}

	/** One interleaved quad at {@code z}, half-extent {@code half}. */
	private static ElementsBufferGL quad(float half, float z) {
		int stride = 28;
		float[][] corners = {
			{ -half, -half, z }, { half, -half, z }, { half, half, z }, { -half, half, z }
		};
		ByteBuffer data = ByteBuffer.allocate(stride * corners.length).order(ByteOrder.LITTLE_ENDIAN);
		for (float[] corner : corners) {
			data.putFloat(corner[0]).putFloat(corner[1]).putFloat(corner[2]);
			data.put(WHITE);
			data.putFloat(0.0F).putFloat(0.0F).putFloat(1.0F);
		}
		return new GlVboElementsArrayBuffer(toolkit, stride, data.array(), data.array().length, false);
	}

	/**
	 * One interleaved textured quad: position, colour, normal, texcoord. The
	 * texcoord pointer is what lets the depth pass see the material's alpha.
	 */
	private static ElementsBufferGL texturedQuad(float half, float z) {
		int stride = 36;
		float[][] corners = {
			{ -half, -half, z }, { half, -half, z }, { half, half, z }, { -half, half, z }
		};
		float[][] uvs = { { 0.0F, 0.0F }, { 1.0F, 0.0F }, { 1.0F, 1.0F }, { 0.0F, 1.0F } };
		ByteBuffer data = ByteBuffer.allocate(stride * corners.length).order(ByteOrder.LITTLE_ENDIAN);
		for (int i = 0; i < corners.length; i++) {
			data.putFloat(corners[i][0]).putFloat(corners[i][1]).putFloat(corners[i][2]);
			data.put(WHITE);
			data.putFloat(0.0F).putFloat(0.0F).putFloat(1.0F);
			data.putFloat(uvs[i][0]).putFloat(uvs[i][1]);
		}
		return new GlVboElementsArrayBuffer(toolkit, stride, data.array(), data.array().length, false);
	}

	private static ArrayBufferGL indices() {
		byte[] indices = { 0, 0, 1, 0, 2, 0, 0, 0, 2, 0, 3, 0 };
		return new GlVboArrayBuffer(toolkit, GL11.GL_UNSIGNED_SHORT, indices, indices.length, false);
	}

	/**
	 * The three materials the recorded-model proofs need, without a cache: one
	 * opaque with an alpha channel, one fully transparent, and one with no alpha
	 * channel at all (the solid caster that must not bind a texture).
	 */
	private static final class ShadowMaterials implements unpack_Sub1 {

		private final MaterialType opaque = metrics(true);

		private final MaterialType clear = metrics(true);

		private final MaterialType solid = metrics(false);

		private static MaterialType metrics(boolean alpha) {
			MaterialType type = new MaterialType();
			type.isSmall = true;
			type.aByte92 = (byte) (alpha ? 1 : 0);
			return type;
		}			@Override
		public boolean textureAvailable(int id) {
			return id == OPAQUE_MATERIAL || id == CLEAR_MATERIAL || id == SOLID_MATERIAL
					|| id == HALF_MATERIAL || id == SECOND_OPAQUE_MATERIAL;
		}

		@Override
		public int[] getPixels(boolean readReversed, int id, float scale, int width, int height) {
			return getPixelsArgb(id, scale, width, height);
		}

		@Override
		public float[] method2394(float scale, int id, int width, int height) {
			return new float[width * height * 4];
		}			@Override
		public int[] getPixelsArgb(int id, float scale, int width, int height) {
			int[] pixels = new int[width * height];
			if (id == HALF_MATERIAL) {
				// The left half of the texture opaque, the right half clear.
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						pixels[y * width + x] = x < width / 2 ? 0xFFFFFFFF : 0x00000000;
					}
				}
				return pixels;
			}
			java.util.Arrays.fill(pixels, id == CLEAR_MATERIAL ? 0x00000000 : 0xFFFFFFFF);
			return pixels;
		}

		@Override
		public MaterialType getMetrics(int id) {
			if (id == CLEAR_MATERIAL) {
				return this.clear;
			}
			// A material whose texture has not streamed in still has a type: only
			// the image is missing, which is the state textureAvailable reports.
			return id == SOLID_MATERIAL ? this.solid : this.opaque;
		}
	}

	/** The centre texel, 0..255 per channel. */
	private static final class Pixel {

		final int red;

		final int green;

		final int blue;

		Pixel(int red, int green, int blue) {
			this.red = red;
			this.green = green;
			this.blue = blue;
		}

		@Override
		public String toString() {
			return "rgb(" + this.red + "," + this.green + "," + this.blue + ")";
		}
	}
}
