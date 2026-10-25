package com.jagex.game.runetek5.client.hd.shader;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import com.jagex.game.runetek5.buffer.ArrayBufferGL;
import com.jagex.game.runetek5.buffer.ElementsBufferGL;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
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
 * The HD lighting path driven by the stock toolkit, not by hand-set state.
 *
 * <p>Three things were assumptions before this class. First, that
 * {@code GlToolkit}'s own uploads ({@code B}, {@code scaleHeight}, {@code UA},
 * {@code copyLights}) put the same values into {@code gl_LightSource} and
 * {@code gl_Fog} that the earlier harness set by hand. Second, that the real
 * interleaved vertex path ({@code method1934} + {@code method1967}, the calls
 * {@code GlModel} makes) delivers {@code gl_Normal} and {@code gl_Color} the way
 * an immediate-mode quad does. Third, what the stock path's negative anti-sun
 * diffuse actually does once a driver has stored it.
 *
 * <p>So this test builds a real {@code GlToolkit} on an off-screen canvas, lets
 * it upload the sun, the anti-sun, the ambient, the fog band and the point
 * lights through its public API, draws through the toolkit's own
 * {@code hdBeginModelLighting} seam and indexed draw, and compares the pixels
 * with the same geometry drawn immediately. It never sets a light or a fog value
 * with a raw GL call.
 *
 * <p>What it settled, on the driver it ran on:
 *
 * <ul>
 * <li>the toolkit's uploads are the ones the shader expects - sun and anti-sun
 * directions, a negative anti-sun diffuse the driver keeps at -1, ambient 0.2,
 * and a fog band measured back from the far plane;</li>
 * <li>the interleaved indexed path delivers the same normals and colour as an
 * immediate-mode quad, so {@code method1934}/{@code method1967} need no
 * attribute work for 592HD;</li>
 * <li>point lights fall off as {@code 1/(quadratic * d^2)}, because
 * {@code method1896} zeroes the constant and linear terms - not the
 * {@code 1/(1 + q*d^2)} the first hand-set harness assumed;</li>
 * <li>fixed function lights per vertex and the shader lights per pixel, so the
 * two agree exactly only where the lighting is constant across the primitive,
 * and a point-lit quad is where they legitimately differ.</li>
 * </ul>
 *
 * <p>If the toolkit cannot be constructed (no desktop, no jaclib), the class is
 * skipped with the reason rather than asserting anything.
 */
@DisplayName("592HD lighting through the stock toolkit")
class HdToolkitLightingGlTest {

	private static final int SIZE = 64;

	private static final int TOLERANCE = 3;

	/** {@code GL_MODULATE}. */
	private static final int GL_MODULATE = 7681;

	/** Far-plane distance; {@code UA} measures the fog band back from here. */
	private static final int FAR = 3584;

	/**
	 * How far the quad sits from the eye. The toolkit's own near plane is 50
	 * and it clamps the fog start to it, so the geometry has to be beyond that
	 * for the stock fog band to mean anything.
	 */
	private static final float DEPTH = 100.0F;

	/** {@code GL_QUADRATIC_ATTENUATION}, only used for the point-light read-back. */
	private static final int GL_QUADRATIC_ATTENUATION = 4617;

	private static GlToolkit toolkit;

	private static Frame frame;

	private static Canvas canvas;

	private static int framebuffer;

	private static int targetTexture;

	private static int depthBuffer;

	private static ElementsBufferGL interleaved;

	private static Class177 colours;

	private static Class177 normals;

	private static Class177 vertices;

	private static ArrayBufferGL elements;

	@BeforeAll
	static void openToolkit() throws Exception {
		try {
			// Loading jawt into the process is what lets jaclib.dll resolve its
			// own jawt dependency; a throwaway context does it.
			GlTestContext warmup = GlTestContext.open();
			warmup.close();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}

		frame = new Frame("592HD toolkit test");
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
		System.out.println("[hd-gl] toolkit " + GL11.glGetString(GL11.GL_RENDERER) + " | GL "
			+ GL11.glGetString(GL11.GL_VERSION));
		// The stock startup step before any buffer exists: a native heap for
		// client-side arrays when the driver is not on the ARB-VBO fast path.
		createTarget();
		createVertexData();

		Path preferences = Files.createTempDirectory("hd-toolkit");
		HdSettings.setDirectoryForTest(preferences.toFile());
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 1);
		HdSettings.set(HdFeature.POINT_LIGHTS, 1);
		// The atmosphere rows are pinned off here: these tests pin the *stock*
		// fog band, the stock geometry and the seam. The rows' own pixel proofs
		// live in HdAtmosphereRealGlTest, and the one seam test below turns Fog
		// back on to show the toolkit reads the live setting.
		HdSettings.set(HdFeature.FOG, 0);
		HdSettings.set(HdFeature.DISTANCE_FADE, 0);
		HdSettings.set(HdFeature.AMBIENT_OCCLUSION, 0);
		// The scene-light rows add terms to the same shader, so they are pinned
		// off here too: this class compares exact lighting values, and the rows'
		// own proofs live in HdLightRealGlTest.
		HdSettings.set(HdFeature.CARRIED_LIGHT, 0);
		HdSettings.set(HdFeature.ROOM_LIGHT, 0);
		// What method1928 leaves behind before model geometry: GL_MODULATE and
		// normals present, so GL_LIGHTING is on.
		toolkit.method1900(1);
		toolkit.method1906(true);
	}

	@AfterAll
	static void closeToolkit() {
		HdSettings.resetForTest();
		if (toolkit != null) {
			toolkit.hdEndModelLighting();
		}
		if (frame != null) {
			frame.dispose();
		}
	}

	@Test
	@DisplayName("the toolkit uploads the sun, anti-sun and ambient the shader reads")
	void toolkit_shouldUploadSunState() {
		beginFrame();
		apply(new Scene());
		float[] sunDiffuse = light(GL11.GL_LIGHT0, GL11.GL_DIFFUSE);
		float[] sunPosition = light(GL11.GL_LIGHT0, GL11.GL_POSITION);
		float[] antiDiffuse = light(GL11.GL_LIGHT1, GL11.GL_DIFFUSE);
		float[] antiPosition = light(GL11.GL_LIGHT1, GL11.GL_POSITION);
		float[] ambient = floats(GL11.GL_LIGHT_MODEL_AMBIENT, 4);

		System.out.println("[hd-gl] sun " + describe(sunDiffuse) + " @" + describe(sunPosition)
			+ ", anti-sun " + describe(antiDiffuse) + " @" + describe(antiPosition)
			+ ", ambient " + describe(ambient));
		assertAll(
			() -> assertVector("sun diffuse", sunDiffuse, 1.0F, 1.0F, 1.0F, 1.0F),
			() -> assertVector("sun direction", sunPosition, 0.0F, 0.0F, 1.0F, 0.0F),
			() -> assertVector("anti-sun direction", antiPosition, 0.0F, 0.0F, -1.0F, 0.0F),
			() -> assertTrue(antiDiffuse[0] < 0.0F,
				"the stock anti-sun is a negative diffuse; the driver stored " + antiDiffuse[0]),
			() -> assertVector("ambient", ambient, 0.2F, 0.2F, 0.2F, 1.0F)
		);
	}

	@Test
	@DisplayName("the toolkit uploads the fog band and colour the shader reads")
	void toolkit_shouldUploadFogState() {
		beginFrame();
		apply(new Scene().fog(60, 140, 0xFF0000));
		float start = floats(GL11.GL_FOG_START, 1)[0];
		float end = floats(GL11.GL_FOG_END, 1)[0];
		float[] colour = floats(GL11.GL_FOG_COLOR, 4);
		// gl_Fog.scale is derived by GL from start/end, not queried state; the
		// pixel test below is what proves the band's width reached the shader.
		System.out.println("[hd-gl] fog " + start + ".." + end + " colour " + describe(colour));
		assertAll(
			() -> assertTrue(Math.abs(start - 60.0F) < 0.01F, "fog start " + start),
			() -> assertTrue(Math.abs(end - 140.0F) < 0.01F, "fog end " + end),
			() -> assertVector("fog colour", colour, 1.0F, 0.0F, 0.0F, 1.0F)
		);
	}

	@Test
	@DisplayName("the negative anti-sun darkens the unlit side, exactly as fixed function does")
	void negativeAntiSun_shouldMatchFixedFunction() {
		Scene facingAway = new Scene().sun(0.0F, 0.0F, -1.0F);
		Pixel shader = drawInterleaved(facingAway, true);
		Pixel fixed = drawInterleaved(facingAway, false);
		float anti = light(GL11.GL_LIGHT1, GL11.GL_DIFFUSE)[0];

		// ambient 0.2 + anti-sun (-1) * dot(N, -sun) = 0.2 - 1.0 -> clamped at
		// write to 0. Had the driver clamped the negative diffuse at upload,
		// this would instead be 0.2 -> 51.
		int expected = anti < 0.0F ? 0 : 51;
		System.out.println("[hd-gl] anti-sun diffuse " + anti + " -> unlit " + expected);
		assertAll(
			() -> assertPixel("shader, sun behind", shader, expected, expected, expected),
			() -> assertPixel("fixed function, sun behind", fixed, expected, expected, expected),
			() -> assertClose("shader vs fixed function", shader, fixed)
		);
	}

	@Test
	@DisplayName("a sun facing the surface lights the interleaved draw through the toolkit seam")
	void toolkitSeam_shouldLightTheInterleavedDraw() {
		Scene scene = new Scene();
		Pixel shader = drawInterleaved(scene, true);
		Pixel fixed = drawInterleaved(scene, false);

		// ambient 0.2 + sun 1.0 * dot((0,0,1),(0,0,1)) = 1.2, clamped at write.
		assertAll(
			() -> assertPixel("shader, sun facing", shader, 255, 255, 255),
			() -> assertClose("shader vs fixed function", shader, fixed)
		);
	}

	@Test
	@DisplayName("the interleaved path delivers the same normals and colour as immediate mode")
	void interleavedPath_shouldMatchImmediateMode() {
		// dot((0,0,1) normal, (0.866,0,0.5) sun) = 0.5, so the tone is mid-range:
		// a wrong normal, or a colour attribute read from the wrong offset, moves
		// the pixel instead of pinning it at 255.
		Scene scene = new Scene().sun(0.866F, 0.0F, 0.5F);
		Pixel interleavedPixel = drawInterleaved(scene, true);
		Pixel immediatePixel = drawImmediate(scene, true);
		Pixel fixedInterleaved = drawInterleaved(scene, false);
		Pixel fixedImmediate = drawImmediate(scene, false);

		// ambient 0.2 + 1.0 * 0.5 = 0.7 -> 178.
		assertAll(
			() -> assertPixel("interleaved", interleavedPixel, 178, 178, 178),
			() -> assertClose("interleaved vs immediate (shader)", interleavedPixel, immediatePixel),
			() -> assertClose("interleaved vs immediate (fixed)", fixedInterleaved, fixedImmediate),
			() -> assertClose("shader vs fixed function (interleaved)", interleavedPixel, fixedInterleaved)
		);
	}

	@Test
	@DisplayName("the toolkit's fog band fogs the interleaved draw where fixed function does")
	void toolkitFog_shouldBlendTheBand() {
		Scene scene = new Scene().fog(60, 140, 0xFF0000);
		Pixel shader = drawInterleaved(scene, true);
		Pixel fixed = drawInterleaved(scene, false);

		// factor = (140-100)/(140-60) = 0.5 -> white mixed halfway to red.
		assertAll(
			() -> assertPixel("shader fog", shader, 255, 128, 128),
			() -> assertClose("fogged shader vs fixed function", shader, fixed)
		);
	}

	@Test
	@DisplayName("the Fog row reaches the toolkit seam from the settings, and back off again")
	void toolkitFogRow_shouldApplyTheHdBandFromSettings() {
		Scene scene = new Scene().fog(60, 140, 0xFF0000);
		Pixel stock = drawInterleaved(scene, true);

		HdSettings.set(HdFeature.FOG, 1);
		Pixel hd = drawInterleaved(scene, true);
		HdSettings.set(HdFeature.FOG, 0);
		Pixel restored = drawInterleaved(scene, true);

		// Stock: (140-100)/80 = 0.5 of the lit colour survives. HD: 0.25.
		assertAll(
			() -> assertPixel("stock band through the seam", stock, 255, 128, 128),
			() -> assertPixel("HD band through the seam", hd, 255, 64, 64),
			() -> assertEquals(stock.toString(), restored.toString(),
				"turning the row off must restore the stock frame exactly")
		);
	}

	@Test
	@DisplayName("a fogless toolkit frame is lit with the band left out, as fixed function draws it")
	void toolkitFogOff_shouldLightWithoutTheBand() {
		Pixel shader = drawFogless(true);
		Pixel fixed = drawFogless(false);

		// The terrain's case: GL_FOG off, so the stock lit colour is unfogged.
		// GL still holds the previous band, and applying it would read 128.
		assertAll(
			() -> assertPixel("fogless per-pixel", shader, 255, 255, 255),
			() -> assertClose("fogless per-pixel vs fixed function", shader, fixed)
		);
	}

	@Test
	@DisplayName("the toolkit's point light reaches the shader at stock attenuation")
	void toolkitPointLights_shouldAddWhenEnabled() {
		Scene scene = new Scene().noSun().pointLight(0x00FF00, 0, 0, 2, 1);
		Pixel shader = drawInterleaved(scene, true);

		float constant = light(GL11.GL_LIGHT2, GL11.GL_CONSTANT_ATTENUATION)[0];
		float linear = light(GL11.GL_LIGHT2, GL11.GL_LINEAR_ATTENUATION)[0];
		float quadratic = light(GL11.GL_LIGHT2, GL11.GL_QUADRATIC_ATTENUATION)[0];
		float[] diffuse = light(GL11.GL_LIGHT2, GL11.GL_DIFFUSE);
		float[] position = light(GL11.GL_LIGHT2, GL11.GL_POSITION);
		// method1896 zeroes constant and linear for every light and method1903 sets
		// only the quadratic term to 1/r^2, so a light 2 units away lands at a
		// quarter strength: ambient 0.2 + 0.25 = 0.45.
		System.out.println("[hd-gl] light2 " + describe(diffuse) + " @" + describe(position)
			+ " attenuation " + constant + "/" + linear + "/" + quadratic);
		assertAll(
			() -> assertVector("point light diffuse", diffuse, 0.0F, 1.0F, 0.0F, 1.0F),
			() -> assertVector("point light eye position", position, 0.0F, 0.0F, -98.0F, 1.0F),
			() -> assertTrue(Math.abs(constant) < 0.001F, "constant " + constant),
			() -> assertTrue(Math.abs(linear) < 0.001F, "linear " + linear),
			() -> assertTrue(Math.abs(quadratic - 1.0F) < 0.001F, "quadratic " + quadratic),
			() -> assertPixel("per-pixel point light", shader, 51, 114, 51)
		);
	}

	@Test
	@DisplayName("fixed function lights per vertex, so the point light diverges by design")
	void fixedFunctionPointLight_shouldBePerVertex() {
		Scene scene = new Scene().noSun().pointLight(0x00FF00, 0, 0, 2, 1);
		Pixel shader = drawInterleaved(scene, true);
		Pixel fixed = drawInterleaved(scene, false);

		// The quad's corner vertices sit at (+-2, +-2, -100) and the light at
		// (0,0,-98): 12 units squared away, N.L = 2/sqrt(12). Fixed function
		// evaluates that once per vertex and interpolates, so its centre pixel is
		// 0.2 + (2/sqrt(12)) * (1/12) = 0.248, not the 0.45 the surface actually
		// has there. The shader evaluates the pixel.
		assertAll(
			() -> assertPixel("per-pixel at the centre", shader, 51, 114, 51),
			() -> assertPixel("per-vertex interpolation", fixed, 51, 63, 51)
		);
	}

	@Test
	@DisplayName("on a primitive small enough for the two to agree, they do")
	void smallPrimitive_shouldMatchFixedFunction() {
		Scene scene = new Scene().noSun().pointLight(0x00FF00, 0, 0, 2, 1);
		Pixel shader = drawScaled(scene, true, 0.1F);
		Pixel fixed = drawScaled(scene, false, 0.1F);

		// Corners at +-0.2, so every vertex is within a tenth of a unit of the
		// centre and both evaluators land on the same 0.2 + 0.243 = 0.443.
		assertAll(
			() -> assertPixel("shader, small quad", shader, 51, 113, 51),
			() -> assertPixel("fixed function, small quad", fixed, 51, 113, 51),
			() -> assertClose("shader vs fixed function", shader, fixed)
		);
	}

	// -- Scene ------------------------------------------------------------

	/** The values a scene hands the toolkit, plus what the lighting should become. */
	private static final class Scene {

		float ambient = 0.2F;

		int sunColour = 0xFFFFFF;

		float sunDiffuse = 1.0F;

		float antiSunContrast = 1.0F;

		float sunX;

		float sunY;

		float sunZ = 1.0F;

		int fogColour = 0xFF0000;

		/**
		 * Fog band in toolkit units, in front of the eye: no fog by default, so a
		 * lighting assertion is not silently graded through the fog colour.
		 */
		int fogEnd = FAR - 300;

		int fogBand = 100;

		PointLight[] lights = new PointLight[0];

		Scene sun(float x, float y, float z) {
			this.sunX = x;
			this.sunY = y;
			this.sunZ = z;
			return this;
		}

		/** Keeps the sun's direction but removes its diffuse and the anti-sun. */
		Scene noSun() {
			this.sunDiffuse = 0.0F;
			this.antiSunContrast = 0.0F;
			return this;
		}

		Scene fog(int start, int end, int colour) {
			this.fogColour = colour;
			this.fogEnd = FAR - end;
			this.fogBand = end - start;
			return this;
		}

		Scene pointLight(int colour, int x, int y, int z, int radius) {
			// Intensity is the 0..1 fraction Class191 computes, so method1903's
			// /255 normalises the colour channel, not the intensity.
			this.lights = new PointLight[] { new GpuPointLight(x, y, z, radius, colour, 1.0F) };
			return this;
		}
	}

	/**
	 * The stock uploads, through the toolkit's public API only. Must run after
	 * the modelview exists: {@code method1903} lets GL transform the light
	 * positions at upload time, exactly as the scene does.
	 */
	private static void apply(Scene scene) {
		toolkit.B(scene.ambient);
		toolkit.scaleHeight(scene.sunColour, scene.sunDiffuse, scene.antiSunContrast,
			scene.sunX, scene.sunY, scene.sunZ);
		toolkit.UA(scene.fogColour, scene.fogBand, scene.fogEnd);
		toolkit.method1918(true);
		toolkit.copyLights(scene.lights.length, scene.lights);
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

	/**
	 * One interleaved buffer, laid out exactly as {@code GlModel} lays it out.
	 *
	 * <p>The buffers are created as ARB buffer objects directly. That is the
	 * same {@code AbstractArbBufferGL} storage {@code method1960}/{@code method1955}
	 * hand back when the toolkit is on its VBO path; the alternative, the
	 * client-side array path, needs {@code setHeap}, which allocates through
	 * jaclib's native heap and crashes this driver's startup. Only the storage
	 * differs - {@code method1934} and {@code method1967} drive both.
	 */
	private static void createVertexData() {
		// vertex (12) | colour (4) | normal (12) = 28.
		int stride = 28;
		float[][] positions = { { -2, -2, 0 }, { 2, -2, 0 }, { 2, 2, 0 }, { -2, 2, 0 } };
		ByteBuffer data = ByteBuffer.allocate(stride * positions.length).order(ByteOrder.LITTLE_ENDIAN);
		for (float[] position : positions) {
			data.putFloat(position[0]).putFloat(position[1]).putFloat(position[2]);
			data.put((byte) 255).put((byte) 255).put((byte) 255).put((byte) 255);
			data.putFloat(0.0F).putFloat(0.0F).putFloat(1.0F);
		}
		interleaved = new GlVboElementsArrayBuffer(toolkit, stride, data.array(), data.array().length, false);
		colours = new Class177(interleaved, GL11.GL_UNSIGNED_BYTE, 4, 12);
		normals = new Class177(interleaved, GL11.GL_FLOAT, 3, 16);
		vertices = new Class177(interleaved, GL11.GL_FLOAT, 3, 0);

		byte[] indices = { 0, 0, 1, 0, 2, 0, 0, 0, 2, 0, 3, 0 };
		elements = new GlVboArrayBuffer(toolkit, GL11.GL_UNSIGNED_SHORT, indices, indices.length, false);
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
		GL11.glFrustum(-0.1D, 0.1D, -0.1D, 0.1D, 1.0D, 400.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -DEPTH);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	private static Pixel drawInterleaved(Scene scene, boolean shader) {
		return drawScaled(scene, shader, 1.0F);
	}

	private static Pixel drawScaled(Scene scene, boolean shader, float scale) {
		beginFrame();
		if (scale != 1.0F) {
			GL11.glScalef(scale, scale, 1.0F);
		}
		apply(scene);
		// What GlModel calls first: material -1 leaves the combine at
		// GL_REPLACE and no texture bound, which the gate must still accept for
		// an untextured primitive.
		toolkit.method1938(-1, true);
		boolean bound = false;
		if (shader) {
			bound = toolkit.hdBeginModelLighting(false);
			assertTrue(bound, "the toolkit seam refused an untextured, fogged model draw");
		}
		toolkit.method1934(colours, normals, null, vertices);
		toolkit.method1967(0, 6, elements);
		if (bound) {
			toolkit.hdEndModelLighting();
		}
		return finishFrame();
	}

	/**
	 * The terrain's fog state: the stock fog band uploaded, then {@code GL_FOG}
	 * turned off exactly as the ground pass does, so the only fog source left is
	 * the shader's own band.
	 */
	private static Pixel drawFogless(boolean shader) {
		beginFrame();
		apply(new Scene().fog(60, 140, 0xFF0000));
		toolkit.method1918(false);
		assertTrue(!GL11.glIsEnabled(GL11.GL_FOG), "the frame under test must be fogless");
		toolkit.method1938(-1, true);
		boolean bound = false;
		if (shader) {
			bound = toolkit.hdBeginModelLighting(false);
			assertTrue(bound, "the gate refused a fogless draw");
		}
		toolkit.method1934(colours, normals, null, vertices);
		toolkit.method1967(0, 6, elements);
		if (bound) {
			toolkit.hdEndModelLighting();
		}
		Pixel result = finishFrame();
		toolkit.method1918(true);
		return result;
	}

	private static Pixel drawImmediate(Scene scene, boolean shader) {
		beginFrame();
		apply(scene);
		toolkit.method1938(-1, true);
		boolean bound = false;
		if (shader) {
			bound = toolkit.hdBeginModelLighting(false);
			assertTrue(bound);
		}
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glNormal3f(0.0F, 0.0F, 1.0F);
		GL11.glColor4ub((byte) 255, (byte) 255, (byte) 255, (byte) 255);
		GL11.glVertex3f(-2.0F, -2.0F, 0.0F);
		GL11.glVertex3f(2.0F, -2.0F, 0.0F);
		GL11.glVertex3f(2.0F, 2.0F, 0.0F);
		GL11.glVertex3f(-2.0F, 2.0F, 0.0F);
		GL11.glEnd();
		if (bound) {
			toolkit.hdEndModelLighting();
		}
		return finishFrame();
	}

	private static Pixel finishFrame() {
		GL11.glFinish();
		assertEquals(GL11.GL_NO_ERROR, GL11.glGetError(), "GL error while drawing");
		ByteBuffer pixel = BufferUtils.createByteBuffer(4);
		GL11.glReadPixels(SIZE / 2, SIZE / 2, 1, 1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixel);
		Pixel result = new Pixel(pixel.get(0) & 0xFF, pixel.get(1) & 0xFF, pixel.get(2) & 0xFF);
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		return result;
	}

	// -- Read-back helpers ------------------------------------------------

	private static float[] light(int light, int parameter) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
		GL11.glGetLightfv(light, parameter, buffer);
		return new float[] { buffer.get(0), buffer.get(1), buffer.get(2), buffer.get(3) };
	}

	private static float[] floats(int parameter, int count) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(count);
		GL11.glGetFloatv(parameter, buffer);
		float[] values = new float[count];
		for (int i = 0; i < count; i++) {
			values[i] = buffer.get(i);
		}
		return values;
	}

	private static String describe(float[] values) {
		return "(" + values[0] + "," + values[1] + "," + values[2] + "," + values[3] + ")";
	}

	private static void assertVector(String what, float[] actual, float x, float y, float z, float w) {
		assertAll(
			() -> assertTrue(Math.abs(actual[0] - x) < 0.001F, what + " x " + actual[0]),
			() -> assertTrue(Math.abs(actual[1] - y) < 0.001F, what + " y " + actual[1]),
			() -> assertTrue(Math.abs(actual[2] - z) < 0.001F, what + " z " + actual[2]),
			() -> assertTrue(Math.abs(actual[3] - w) < 0.001F, what + " w " + actual[3])
		);
	}

	private static void assertPixel(String what, Pixel pixel, int red, int green, int blue) {
		System.out.println("[hd-gl] " + what + " = " + pixel + ", expected rgb(" + red + "," + green + "," + blue + ")");
		assertAll(
			() -> assertTrue(Math.abs(pixel.red - red) <= TOLERANCE, what + ": red " + pixel.red),
			() -> assertTrue(Math.abs(pixel.green - green) <= TOLERANCE, what + ": green " + pixel.green),
			() -> assertTrue(Math.abs(pixel.blue - blue) <= TOLERANCE, what + ": blue " + pixel.blue)
		);
	}

	private static void assertClose(String what, Pixel actual, Pixel expected) {
		assertAll(
			() -> assertTrue(Math.abs(actual.red - expected.red) <= TOLERANCE,
				what + ": red " + actual.red + " vs " + expected.red),
			() -> assertTrue(Math.abs(actual.green - expected.green) <= TOLERANCE,
				what + ": green " + actual.green + " vs " + expected.green),
			() -> assertTrue(Math.abs(actual.blue - expected.blue) <= TOLERANCE,
				what + ": blue " + actual.blue + " vs " + expected.blue)
		);
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
