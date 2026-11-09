package com.jagex.game.runetek5.client.hd.material;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;

import com.jagex.game.runetek5.buffer.ArrayBufferGL;
import com.jagex.game.runetek5.buffer.ElementsBufferGL;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.client.hd.content.HdContent;
import com.jagex.game.runetek5.client.hd.content.HdEnvironmentDefinition;
import com.jagex.game.runetek5.client.hd.content.HdMaterialDefinition;
import com.jagex.game.runetek5.client.hd.sway.HdVegetation;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import jaggl.GlTestContext;
import jaggl.OpenGL;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

/**
 * The lighting model in pixels, through the production model draw: a quad
 * submitted the way {@code GlModel} submits one, with its material id passed to
 * {@code hdBeginModelLighting} and the maps decoded from the shipped
 * {@code materials.json}.
 *
 * <p>The shipped maps are subtle - an ORM map is mostly unoccluded and a normal
 * map is mostly flat - so the proofs do not demand a property of a whole map.
 * Instead they find the map's most extreme texel at runtime, require its 3x3
 * neighbourhood to share the property (so a parallax nudge or a driver's texel
 * rounding cannot step out of it), and point the quad's uv window at that texel,
 * which is where the centre fragment samples.
 *
 * <p>Each claim fails if the lit path is bypassed:
 *
 * <ul>
 * <li><b>The occlusion map darkens the surface.</b> With the row off the same
 * draw is pixel-identical to a draw with no material at all, which is what makes
 * the darkening the map's and not a different program.</li>
 * <li><b>The normal map turns the lit surface.</b> Against an oblique sun the
 * mapped normal must give a different pixel from the same geometry with no
 * maps, and give the flat pixel again with the row off.</li>
 * <li><b>The area's tint reaches the sun and the ambient.</b> A red-only area
 * sun and ambient drive the green and blue channels of an otherwise grey lit
 * quad to zero, and turning the environment row off restores the grey.</li>
 * </ul>
 *
 * <p>Runs by default; {@link GlTestContext#open()} skips it with its reason when
 * this machine cannot give a GL context.
 */
@DisplayName("592HD lighting model in pixels")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HdLightingModelGlTest {

	private static final int SIZE = 64;

	/** Far-plane distance; {@code UA} measures the fog band back from here. */
	private static final int FAR = 3584;

	private static final float DEPTH = 100.0F;

	/**
	 * The uv window's half-width, in texels. Narrow enough that the whole quad
	 * samples one texel under nearest sampling, wide enough that dFdx/dFdy of
	 * the uv are not zero - a constant uv collapses the tangent frame onto the
	 * face normal and no normal map could turn anything.
	 */
	private static final float UV_HALF_TEXEL = 0.4F;

	/** The map square, which is not the canvas square. */
	private static final int MAP = HdMaterialTextures.SIZE;

	/**
	 * How far the quad is pushed to one side of the camera axis, in scene
	 * units. The height march's step is the view direction's lateral lean, so
	 * a fragment on the axis would offset by nothing however steep its height.
	 */
	private static final float QUAD_OFFSET_X = 9.0F;

	// The EXT_framebuffer_object enums, spelled here rather than imported: the
	// test builds under --release 8, where javac cannot see the LWJGL jar's
	// EXTFramebufferObject class even though jaggl.OpenGL wraps it for the
	// production tree.
	private static final int GL_FRAMEBUFFER_EXT = 0x8D40;

	private static final int GL_RENDERBUFFER_EXT = 0x8D41;

	private static final int GL_COLOR_ATTACHMENT0_EXT = 0x8CE0;

	private static final int GL_DEPTH_ATTACHMENT_EXT = 0x8D00;

	private static final int GL_FRAMEBUFFER_COMPLETE_EXT = 0x8CD5;

	private static GlToolkit toolkit;

	private static Frame frame;

	private static Canvas canvas;

	private static int framebuffer;

	private static int targetTexture;

	private static int depthBuffer;

	/** A shipped material whose most occluded texel is occluded; -1 is "not chosen". */
	private static int occluded = -1;

	/** The 3x3 maximum red around the chosen occlusion texel; 255 means "none found". */
	private static int occludedRed = 255;

	private static float occludedU;

	private static float occludedV;

	/** A shipped material with the most tilted normal texel; -1 is "not chosen". */
	private static int relief = -1;

	/** The 3x3 minimum tilt around the chosen normal texel; 0 means "none found". */
	private static int reliefTilt;

	private static float reliefU;

	private static float reliefV;

	/** A shipped material whose ORM map resolves only from BC7 blocks. */
	private static int blockMaterial = -1;

	private static int blockRed = 255;

	private static float blockU;

	private static float blockV;

	/** A shipped material with a parallax depth and a usable height edge. */
	private static int heightMaterial = -1;

	private static float heightEdgeU;

	private static float heightV;

	private static float heightParallax;

	/** A material with maps that no earlier proof draws, so its upload is a first use. */
	private static int stateMaterial = -1;

	@BeforeAll
	static void openToolkit() throws Exception {
		try {
			GlTestContext warmup = GlTestContext.open();
			warmup.close();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}

		frame = new Frame("592HD lighting model test");
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(SIZE, SIZE));
		frame.add(canvas);
		frame.pack();
		frame.setLocation(-4000, -4000);
		frame.setVisible(true);
		try {
			toolkit = new GlToolkit(0, canvas, null, 0, null);
		} catch (Throwable error) {
			frame.dispose();
			assumeTrue(false, "GlToolkit could not start in this JVM: " + error);
		}
		System.out.println("[hd-model] toolkit " + GL11.glGetString(GL11.GL_RENDERER) + " | GL "
			+ GL11.glGetString(GL11.GL_VERSION));
		createTarget();

		Path preferences = Files.createTempDirectory("hd-model");
		HdSettings.setDirectoryForTest(preferences.toFile());
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 1);
		// Every other row off: the lighting model is the only variable here, and
		// the atmosphere rows in particular would grade the pixels it compares.
		HdSettings.set(HdFeature.FOG, 0);
		HdSettings.set(HdFeature.DISTANCE_FADE, 0);
		HdSettings.set(HdFeature.AMBIENT_OCCLUSION, 0);
		HdSettings.set(HdFeature.CARRIED_LIGHT, 0);
		HdSettings.set(HdFeature.ROOM_LIGHT, 0);
		HdSettings.set(HdFeature.POINT_LIGHTS, 0);
		HdSettings.set(HdFeature.CAUSTICS, 0);
		HdSettings.set(HdFeature.REFLECTIONS, 0);
		HdSettings.set(HdFeature.MATERIAL_MAPS, 0);
		HdSettings.set(HdFeature.WORLD_TEXTURES, 0);
		HdSettings.set(HdFeature.ENVIRONMENT_LIGHTING, 0);
		toolkit.method1900(1);
		toolkit.method1906(true);

		chooseMaterials();
	}

	@AfterAll
	static void closeToolkit() {
		HdAreaLight.resetForTest();
		HdMaterialTextures.resetParallaxForTest();
		HdSettings.resetForTest();
		if (toolkit != null) {
			toolkit.hdEndModelLighting();
		}
		if (frame != null) {
			frame.dispose();
		}
	}

	@Test
	@Order(1)
	@DisplayName("a material's occlusion map darkens the ambient, and only while the row is on")
	void occlusionMap_shouldDarken() {
		assumeTrue(occluded >= 0 && occludedRed <= 220,
			"no shipped ORM texel is occluded enough: darkest 3x3 maximum red = " + occludedRed);
		HdSettings.set(HdFeature.LIGHTING_MODEL, 1);
		Pixel mapped = draw(occluded, false, occludedU, occludedV);
		Pixel plain = draw(-1, false, occludedU, occludedV);
		HdSettings.set(HdFeature.LIGHTING_MODEL, 0);
		Pixel rowOff = draw(occluded, false, occludedU, occludedV);
		Pixel rowOffPlain = draw(-1, false, occludedU, occludedV);
		HdSettings.set(HdFeature.LIGHTING_MODEL, 1);

		// Ambient 0.2 alone is 51; the lit model keeps three quarters of it, so an
		// unoccluded surface is 0.15 -> 38 and the map can only take light away.
		System.out.println("[hd-model] occlusion material " + occluded + " red " + occludedRed + ": mapped " + mapped
			+ ", plain " + plain + ", row off " + rowOff);
		assertAll(
			() -> assertTrue(mapped.red < plain.red,
				"an occluded material must be darker than the same draw with no maps: " + mapped + " vs " + plain),
			() -> assertEquals(rowOffPlain.toString(), rowOff.toString(),
				"with the row off the material's maps must not be sampled at all")
		);
	}

	@Test
	@Order(2)
	@DisplayName("a material's normal map turns the lit surface, and only while the row is on")
	void normalMap_shouldTurnTheSurface() {
		assumeTrue(relief >= 0 && reliefTilt >= 40,
			"no shipped normal texel is tilted enough: steepest 3x3 minimum tilt = " + reliefTilt);
		HdSettings.set(HdFeature.LIGHTING_MODEL, 1);
		Pixel mapped = draw(relief, true, reliefU, reliefV);
		Pixel plain = draw(-1, true, reliefU, reliefV);
		HdSettings.set(HdFeature.LIGHTING_MODEL, 0);
		Pixel rowOff = draw(relief, true, reliefU, reliefV);
		Pixel rowOffPlain = draw(-1, true, reliefU, reliefV);
		HdSettings.set(HdFeature.LIGHTING_MODEL, 1);

		System.out.println("[hd-model] relief material " + relief + " tilt " + reliefTilt + ": mapped " + mapped
			+ ", plain " + plain + ", row off " + rowOff);
		assertAll(
			() -> assertNotEquals(plain.toString(), mapped.toString(),
				"a mapped normal under an oblique sun must not shade like the face normal"),
			() -> assertEquals(rowOffPlain.toString(), rowOff.toString(),
				"with the row off the normal map must not be sampled at all")
		);
	}

	@Test
	@Order(3)
	@DisplayName("the area's sun and ambient tint reach the lit pixel")
	void areaTint_shouldColourTheSurface() {
		HdSettings.set(HdFeature.LIGHTING_MODEL, 1);
		HdSettings.set(HdFeature.ENVIRONMENT_LIGHTING, 1);
		HdAreaLight light = HdAreaLight.of(redEnvironment());
		assumeTrue(light.active(), "the environment under test must state a tint");

		HdAreaLight.publishForTest(HdAreaLight.OFF);
		Pixel untilted = draw(-1, true, 0.5F, 0.5F);
		HdAreaLight.publishForTest(light);
		Pixel tinted = draw(-1, true, 0.5F, 0.5F);
		HdSettings.set(HdFeature.ENVIRONMENT_LIGHTING, 0);
		Pixel rowOff = draw(-1, true, 0.5F, 0.5F);
		HdSettings.set(HdFeature.LIGHTING_MODEL, 1);
		HdAreaLight.resetForTest();

		// A red-only sun and ambient: the grey surface's green and blue go to zero
		// and its red stays where the same light put it.
		System.out.println("[hd-model] area: untilted " + untilted + ", tinted " + tinted + ", row off " + rowOff);
		assertAll(
			() -> assertTrue(tinted.green <= 2, "a red-only ambient must leave no green: " + tinted.green),
			() -> assertTrue(tinted.blue <= 2, "a red-only ambient must leave no blue: " + tinted.blue),
			() -> assertTrue(tinted.red >= untilted.red,
				"the sun and ambient keep their red, so the red channel cannot fall: " + tinted + " vs " + untilted),
			() -> assertNotEquals(untilted.toString(), tinted.toString()),
			() -> assertEquals(untilted.toString(), rowOff.toString(),
				"with the environment row off the area's tint must not be applied")
		);
	}

	@Test
	@Order(4)
	@DisplayName("the Ambient occlusion row invents no occlusion without a material map")
	void ambientOcclusion_shouldNeedAMap() {
		HdSettings.set(HdFeature.LIGHTING_MODEL, 1);
		Pixel aoOff = draw(-1, false, 0.5F, 0.5F);
		HdSettings.set(HdFeature.AMBIENT_OCCLUSION, 1);
		Pixel aoOn = draw(-1, false, 0.5F, 0.5F);
		HdSettings.set(HdFeature.AMBIENT_OCCLUSION, 0);

		assertEquals(aoOff.toString(), aoOn.toString(),
			"with no occlusion map the row has nothing to attenuate: " + aoOn);
	}

	@Test
	@Order(5)
	@DisplayName("a material whose maps exist only as BC5/BC7 blocks still shades by them")
	void blockOnlyMaterial_shouldShade() {
		assumeTrue(blockMaterial >= 0 && blockRed <= 220,
			"no shipped BC7 map is occluded enough: darkest 3x3 maximum red = " + blockRed);
		HdSettings.set(HdFeature.LIGHTING_MODEL, 1);
		Pixel lit = draw(blockMaterial, false, blockU, blockV);
		Pixel plain = draw(-1, false, blockU, blockV);
		HdSettings.set(HdFeature.LIGHTING_MODEL, 0);
		Pixel lightingOff = draw(blockMaterial, false, blockU, blockV);
		Pixel lightingOffPlain = draw(-1, false, blockU, blockV);
		HdSettings.set(HdFeature.LIGHTING_MODEL, 1);

		System.out.println("[hd-model] blocks: material " + blockMaterial + " red " + blockRed + ", lit " + lit
			+ ", plain " + plain + ", lighting off " + lightingOff);
		assertAll(
			() -> assertTrue(lit.red < plain.red,
				"the decoded block occlusion must darken the surface: " + lit + " vs " + plain),
			() -> assertNotEquals(lightingOff.toString(), lit.toString(),
				"the lit term sum must differ from the same draw with lighting off: " + lit + " vs " + lightingOff),
			() -> assertEquals(lightingOffPlain.toString(), lightingOff.toString(),
				"lighting off must not sample the material at all")
		);
	}

	@Test
	@Order(6)
	@DisplayName("the height map marches the uv, and only while a height is bound")
	void heightMap_shouldMarchTheUv() {
		assumeTrue(heightMaterial >= 0,
			"no shipped material combines a parallax depth with a usable height edge");
		HdSettings.set(HdFeature.LIGHTING_MODEL, 1);
		// The authored depth is the material's own; NaN means "no override".
		HdMaterialTextures.overrideParallaxForTest(heightMaterial, Float.NaN);
		Pixel authored = draw(heightMaterial, true, heightEdgeU, heightV, QUAD_OFFSET_X, 0.15F);
		HdMaterialTextures.overrideParallaxForTest(heightMaterial, 0.0F);
		Pixel flat = draw(heightMaterial, true, heightEdgeU, heightV, QUAD_OFFSET_X, 0.15F);
		HdMaterialTextures.resetParallaxForTest();

		System.out.println("[hd-model] height: material " + heightMaterial + " parallax " + heightParallax
			+ ", authored " + authored + ", flat " + flat);
		assertAll(
			() -> assertTrue(heightParallax > 0.0F),
			() -> assertNotEquals(flat.toString(), authored.toString(),
				"the height at this texel edge must move the sampled uv: " + authored + " vs " + flat)
		);
	}

	@Test
	@Order(8)
	@DisplayName("a material's map upload leaves the draw's own texture on unit 0")
	void materialUpload_shouldLeaveTheDrawsTexture() {
		assumeTrue(stateMaterial >= 0, "no unused shipped material with maps");
		com.jagex.game.runetek5.deob.OpenGl2DTexture magenta = toolkit.hdCreateTexture(1, 1,
			new int[] { 0xFFFF00FF });
		HdSettings.set(HdFeature.LIGHTING_MODEL, 1);
		toolkit.hdActiveTextureUnit(0);
		toolkit.hdSetActiveTexture(magenta);
		Pixel lit = draw(stateMaterial, false, 0.5F, 0.5F, 0.0F, UV_HALF_TEXEL, true);
		Object bound = toolkit.hdBoundTexture(0);

		System.out.println("[hd-model] upload state: material " + stateMaterial + " unit 0 "
			+ (bound == magenta ? "kept" : "replaced") + ", textured draw " + lit);
		assertAll(
			() -> assertTrue(bound == magenta,
				"the map upload must not replace the texture this draw bound on unit 0"),
			() -> assertTrue(lit.green <= 8,
				"the primitive must still sample its own magenta texture, not a map: " + lit),
			() -> assertTrue(lit.red > lit.green && lit.blue > lit.green,
				"magenta keeps its red and blue, unlike a BC5/BC7 map: " + lit)
		);
	}

	// -- Content selection -------------------------------------------------

	/**
	 * Finds the shipped map texels the two proofs need, by scanning every decoded
	 * map rather than by trusting an id, so the test states the property it
	 * relies on instead of depending on the content staying the same.
	 */
	private static void chooseMaterials() {
		HdContent content = HdContent.get();
		int darkest = 256;
		int steepest = -1;
		for (HdMaterialDefinition material : content.materials()) {
			if (material.orm() != null) {
				int[] orm = HdMaterialTextures.decode(content.materialFile(material.texture(), material.orm()),
					HdMaterialTextures.SIZE);
				if (orm != null) {
					for (int y = 0; y < MAP; y++) {
						for (int x = 0; x < MAP; x++) {
							int red = maxRedAround(orm, x, y);
							if (red < darkest) {
								darkest = red;
								occluded = material.texture();
								occludedU = (x + 0.5F) / MAP;
								occludedV = (y + 0.5F) / MAP;
							}
						}
					}
				}
			}
			if (material.normal() != null) {
				int[] normal = HdMaterialTextures.decode(content.materialFile(material.texture(), material.normal()),
					HdMaterialTextures.SIZE);
				if (normal != null) {
					for (int y = 0; y < MAP; y++) {
						for (int x = 0; x < MAP; x++) {
							int tilt = minTiltAround(normal, x, y);
							if (tilt > steepest) {
								steepest = tilt;
								relief = material.texture();
								reliefU = (x + 0.5F) / MAP;
								reliefV = (y + 0.5F) / MAP;
							}
						}
					}
				}
			}
		}
		occludedRed = darkest;
		reliefTilt = steepest < 0 ? 0 : steepest;
		System.out.println("[hd-model] material maps: " + content.materials().size() + " materials, occlusion material "
			+ occluded + " (3x3 max red " + occludedRed + "), relief material " + relief + " (3x3 min tilt "
			+ reliefTilt + ")");
		chooseBlockMaterial(content);
		chooseHeightMaterial(content);
		chooseStateMaterial(content);
	}

	/**
	 * A material with a shipped map set that no earlier proof draws, so the upload
	 * inside its draw is the first use - the case that used to bind the maps over
	 * the draw's own texture on unit 0.
	 */
	private static void chooseStateMaterial(HdContent content) {
		for (int i = 0; i < content.materials().size(); i++) {
			HdMaterialDefinition material = content.materials().get(i);
			if (material.texture() == 63 || material.texture() == 66) {
				continue;
			}
			if (HdMaterialTextures.resolve(content, material).hasAny()) {
				stateMaterial = material.texture();
				System.out.println("[hd-model] upload state: unused map material " + stateMaterial);
				return;
			}
		}
	}

	/**
	 * A material whose ORM map exists only as BC7 blocks: the decoded blocks
	 * are the only reason the draw is not flat, so the proof fails if the
	 * decoder is bypassed.
	 */
	private static void chooseBlockMaterial(HdContent content) {
		int darkest = 256;
		for (int i = 0; i < content.materials().size(); i++) {
			HdMaterialDefinition material = content.materials().get(i);
			if (material.orm() == null) {
				continue;
			}
			HdMaterialTextures.Resolution resolution = HdMaterialTextures.resolve(content, material);
			if (resolution.orm == null || resolution.ormSource == null
				|| !resolution.ormSource.endsWith(".bc7")) {
				continue;
			}
			for (int y = 0; y < MAP; y += 4) {
				for (int x = 0; x < MAP; x += 4) {
					int red = maxRedAround(resolution.orm, x, y);
					if (red < darkest) {
						darkest = red;
						blockMaterial = material.texture();
						blockRed = red;
						blockU = (x + 0.5F) / MAP;
						blockV = (y + 0.5F) / MAP;
					}
				}
			}
		}
		System.out.println("[hd-model] blocks: material " + blockMaterial + " most occluded 3x3 max red "
			+ blockRed);
	}

	/**
	 * A material that states a parallax depth and ships a height whose alpha is
	 * near zero, at a texel the normal map varies sharply at, so the height's
	 * own uv shift changes which texel the draw samples.
	 */
	private static void chooseHeightMaterial(HdContent content) {
		for (int i = 0; i < content.materials().size(); i++) {
			HdMaterialDefinition material = content.materials().get(i);
			if (material.parallax() < 0.03F || material.height() == null) {
				continue;
			}
			HdMaterialTextures.Resolution resolution = HdMaterialTextures.resolve(content, material);
			if (resolution.height == null || resolution.normal == null) {
				continue;
			}
			for (int y = 0; y < MAP; y += 4) {
				for (int x = 0; x + 1 < MAP; x += 4) {
					if ((resolution.height[y * MAP + x] >>> 24) > 40) {
						continue;
					}
					int here = tiltAt(resolution.normal, x, y);
					int next = tiltAt(resolution.normal, x + 1, y);
					if (Math.abs(here - next) >= 80) {
						heightMaterial = material.texture();
						heightEdgeU = (x + 0.45F) / MAP;
						heightV = (y + 0.5F) / MAP;
						heightParallax = material.parallax();
						System.out.println("[hd-model] height: material " + heightMaterial + " parallax "
							+ heightParallax + " at " + x + "," + y + " tilts " + here + "/" + next);
						return;
					}
				}
			}
		}
		System.out.println("[hd-model] height: no shipped material has a height at a usable texel edge");
	}

	/** The normal map's lean at one texel, 0 flat. */
	private static int tiltAt(int[] normal, int x, int y) {
		int texel = normal[y * MAP + clamp(x)];
		return Math.max(Math.abs((texel >> 16 & 0xFF) - 128), Math.abs((texel >> 8 & 0xFF) - 128));
	}

	/** The largest red in the 3x3 neighbourhood of a texel, edges clamped. */
	private static int maxRedAround(int[] orm, int x, int y) {
		int max = 0;
		for (int dy = -1; dy <= 1; dy++) {
			int at = clamp(y + dy);
			for (int dx = -1; dx <= 1; dx++) {
				int red = orm[at * MAP + clamp(x + dx)] >> 16 & 0xFF;
				if (red > max) {
					max = red;
				}
			}
		}
		return max;
	}

	/** The smallest xy tilt from the flat normal in the 3x3 neighbourhood of a texel. */
	private static int minTiltAround(int[] normal, int x, int y) {
		int min = 255;
		for (int dy = -1; dy <= 1; dy++) {
			int at = clamp(y + dy);
			for (int dx = -1; dx <= 1; dx++) {
				int texel = normal[at * MAP + clamp(x + dx)];
				int tilt = Math.max(Math.abs((texel >> 16 & 0xFF) - 128), Math.abs((texel >> 8 & 0xFF) - 128));
				if (tilt < min) {
					min = tilt;
				}
			}
		}
		return min;
	}

	private static int clamp(int texel) {
		return texel < 0 ? 0 : texel >= MAP ? MAP - 1 : texel;
	}

	/** One environment: a red sun and a red ambient, so the tint is unambiguous. */
	private static HdEnvironmentDefinition redEnvironment() {
		java.util.Map<String, String> files = new java.util.HashMap<String, String>();
		files.put("environments.json", "{\"environments\":[{\"name\":\"red\","
			+ "\"sun\":{\"colour\":\"#FF0000\"},\"ambient\":{\"colour\":\"#FF0000\"}}]}");
		return HdContent.parse(files).environmentFor(3200, 3200, 0);
	}

	// -- Harness ----------------------------------------------------------

	private static void createTarget() {
		framebuffer = genFramebuffer();
		targetTexture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, targetTexture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, SIZE, SIZE, 0,
			GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		depthBuffer = genRenderbuffer();
		OpenGL.glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, depthBuffer);
		OpenGL.glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL14.GL_DEPTH_COMPONENT24, SIZE, SIZE);

		OpenGL.glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebuffer);
		OpenGL.glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D,
			targetTexture, 0);
		OpenGL.glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT_EXT, GL_RENDERBUFFER_EXT,
			depthBuffer);
		int status = OpenGL.glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT);
		OpenGL.glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		assertEquals(GL_FRAMEBUFFER_COMPLETE_EXT, status, "offscreen target incomplete");
	}

	private static int genFramebuffer() {
		int[] names = new int[1];
		OpenGL.glGenFramebuffersEXT(1, names, 0);
		return names[0];
	}

	private static int genRenderbuffer() {
		int[] names = new int[1];
		OpenGL.glGenRenderbuffersEXT(1, names, 0);
		return names[0];
	}

	/**
	 * One draw through the production seam: vertex (12) | colour (4) | normal
	 * (12) | uv (8), laid out as {@code GlModel} does, with the uv window
	 * centred on the texel the proof asserts on.
	 */
	private static Pixel draw(int material, boolean oblique, float centreU, float centreV) {
		return draw(material, oblique, centreU, centreV, 0.0F, UV_HALF_TEXEL);
	}

	private static Pixel draw(int material, boolean oblique, float centreU, float centreV, float translateX) {
		return draw(material, oblique, centreU, centreV, translateX, UV_HALF_TEXEL);
	}

	private static Pixel draw(int material, boolean oblique, float centreU, float centreV, float translateX,
			float halfTexel) {
		return draw(material, oblique, centreU, centreV, translateX, halfTexel, false);
	}

	private static Pixel draw(int material, boolean oblique, float centreU, float centreV, float translateX,
			float halfTexel, boolean textured) {
		int stride = 36;
		float half = halfTexel / MAP;
		// The quad may be pushed off the camera axis, so the texel to read is
		// where its centre projects, not the framebuffer's centre. The frustum
		// half-width at DEPTH is 10 scene units.
		int readX = SIZE / 2 + Math.round(translateX / 10.0F * (SIZE / 2.0F));
		int pixelX = readX < 0 ? 0 : readX >= SIZE ? SIZE - 1 : readX;
		float[][] positions = { { -2, -2, 0 }, { 2, -2, 0 }, { 2, 2, 0 }, { -2, 2, 0 } };
		float[][] uvsAt = { { centreU - half, centreV - half }, { centreU + half, centreV - half },
			{ centreU + half, centreV + half }, { centreU - half, centreV + half } };
		ByteBuffer data = ByteBuffer.allocate(stride * positions.length).order(ByteOrder.LITTLE_ENDIAN);
		for (int i = 0; i < positions.length; i++) {
			float[] position = positions[i];
			data.putFloat(position[0]).putFloat(position[1]).putFloat(position[2]);
			data.put((byte) 255).put((byte) 255).put((byte) 255).put((byte) 255);
			data.putFloat(0.0F).putFloat(0.0F).putFloat(1.0F);
			data.putFloat(uvsAt[i][0]).putFloat(uvsAt[i][1]);
		}
		ElementsBufferGL interleaved = new GlVboElementsArrayBuffer(toolkit, stride, data.array(),
			data.array().length, false);
		Class177 colours = new Class177(interleaved, GL11.GL_UNSIGNED_BYTE, 4, 12);
		Class177 normals = new Class177(interleaved, GL11.GL_FLOAT, 3, 16);
		Class177 texCoords = new Class177(interleaved, GL11.GL_FLOAT, 2, 28);
		Class177 vertices = new Class177(interleaved, GL11.GL_FLOAT, 3, 0);
		byte[] indices = { 0, 0, 1, 0, 2, 0, 0, 0, 2, 0, 3, 0 };
		ArrayBufferGL elements = new GlVboArrayBuffer(toolkit, GL11.GL_UNSIGNED_SHORT, indices, indices.length,
			false);

		OpenGL.glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebuffer);
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
		GL11.glFrustum(-0.1D, 0.1D, -0.1D, 0.1D, 1.0D, 400.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(translateX, 0.0F, -DEPTH);
		GL11.glEnable(GL11.GL_LIGHTING);

		toolkit.B(0.2F);
		toolkit.scaleHeight(0xFFFFFF, oblique ? 0.3F : 0.0F, oblique ? 1.0F : 0.0F, oblique ? 0.866F : 0.0F,
			0.0F, 0.5F);
		toolkit.UA(0x000000, 1, FAR - 300);
		toolkit.method1918(true);
		toolkit.copyLights(0, new PointLight[] { new GpuPointLight(0, 0, 0, 1, 0, 1.0F) });

		toolkit.method1938(-1, true);
		boolean bound = toolkit.hdBeginModelLighting(textured, HdVegetation.NONE, material);
		toolkit.method1934(colours, normals, texCoords, vertices);
		toolkit.method1967(0, 6, elements);
		if (bound) {
			toolkit.hdEndModelLighting();
		}
		assertTrue(bound, "the toolkit seam refused an untextured model draw with the lighting-model row on");

		GL11.glFinish();
		assertEquals(GL11.GL_NO_ERROR, GL11.glGetError(), "GL error while drawing");
		ByteBuffer pixel = BufferUtils.createByteBuffer(4);
		GL11.glReadPixels(pixelX, SIZE / 2, 1, 1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixel);
		Pixel result = new Pixel(pixel.get(0) & 0xFF, pixel.get(1) & 0xFF, pixel.get(2) & 0xFF);
		OpenGL.glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		return result;
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
