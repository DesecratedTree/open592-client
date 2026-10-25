package com.jagex.game.runetek5.client.hd.light;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

/**
 * The environment-light row, proven with pixels through the stock toolkit.
 *
 * <p>The harness is {@code HdLightRealGlTest}'s: a real {@link GlToolkit} on an
 * off-screen canvas, its own uploads and its own {@code hdBeginModelLighting}
 * seam. The environment is uploaded the way the game uploads it - {@code B} for
 * the ambient, {@code scaleHeight} for the sun, {@code UA} for the sky/fog
 * colour - so nothing here is a test-only source. The modelview is the scene's
 * own {@code rotX(180) * camera}, and the camera matrix the toolkit holds is
 * the identity, so the eye-space up axis the shader is given is the flipped
 * world up, exactly as it is in a frame.
 *
 * <p>Two quads: one in the model's z=0 plane facing up, one facing the camera.
 * The first sees the sky and must take its colour; the second sees none of it
 * and must keep the stock ambient. Both are predicted from
 * {@link HdEnvironmentLight}'s own arithmetic, not recorded.
 *	 * <p>Runs by default; {@link GlTestContext#open()} skips it with the reason
	 * when this machine cannot give a GL context.
 */
@DisplayName("592HD environment light through the stock toolkit")
class HdEnvironmentLightGlTest {

	private static final int SIZE = 64;

	private static final int TOLERANCE = 3;

	/** Far-plane distance; the harness' fog band sits outside the quad. */
	private static final int FAR = 3584;

	/** The quad sits this far from the eye under the harness' 0.1 frustum. */
	private static final float DEPTH = 100.0F;

	/**
	 * The scene's camera, chosen so {@code rotX(180) * camera} is the pure
	 * translation the harness loads: {@code diag(1,-1,-1)} with {@code tz = 100}.
	 * That is the client's own composition, which is why the shader's eye-space
	 * up axis is exactly the flipped world up for these frames.
	 */
	private static final JavaMatrix CAMERA = sceneCamera();

	private static final int RED_SKY = 0xFF0000;

	private static final int GREEN_SKY = 0x00FF00;

	private static final float AMBIENT = 0.2F;

	private static GlToolkit toolkit;

	private static Frame frame;

	private static Canvas canvas;

	private static int framebuffer;

	private static int targetTexture;

	private static int depthBuffer;

	private static ElementsBufferGL upBuffer;

	private static Class177 upColours;

	private static Class177 upNormals;

	private static Class177 upVertices;

	private static ArrayBufferGL upElements;

	private static ElementsBufferGL wallBuffer;

	private static Class177 wallColours;

	private static Class177 wallNormals;

	private static Class177 wallVertices;

	private static ArrayBufferGL wallElements;

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

		frame = new Frame("592HD environment test");
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
		System.out.println("[hd-gl] environment harness " + GL11.glGetString(GL11.GL_RENDERER) + " | GL "
			+ GL11.glGetString(GL11.GL_VERSION));
		createTarget();
		upBuffer = quadBuffer(0.0F, 1.0F, 0.0F);
		upColours = new Class177(upBuffer, GL11.GL_UNSIGNED_BYTE, 4, 12);
		upNormals = new Class177(upBuffer, GL11.GL_FLOAT, 3, 16);
		upVertices = new Class177(upBuffer, GL11.GL_FLOAT, 3, 0);
		upElements = quadElements();
		wallBuffer = quadBuffer(0.0F, 0.0F, 1.0F);
		wallColours = new Class177(wallBuffer, GL11.GL_UNSIGNED_BYTE, 4, 12);
		wallNormals = new Class177(wallBuffer, GL11.GL_FLOAT, 3, 16);
		wallVertices = new Class177(wallBuffer, GL11.GL_FLOAT, 3, 0);
		wallElements = quadElements();

		Path preferences = Files.createTempDirectory("hd-environment-gl");
		HdSettings.setDirectoryForTest(preferences.toFile());
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 1);
		HdSettings.set(HdFeature.POINT_LIGHTS, 0);
		// The stock band, geometry and seam are pinned by the other real-GL
		// tests; here the environment row is the only variable.
		HdSettings.set(HdFeature.FOG, 0);
		HdSettings.set(HdFeature.DISTANCE_FADE, 0);
		HdSettings.set(HdFeature.AMBIENT_OCCLUSION, 0);
		HdSettings.set(HdFeature.CARRIED_LIGHT, 0);
		HdSettings.set(HdFeature.ROOM_LIGHT, 0);
		HdSettings.set(HdFeature.ENVIRONMENT_LIGHTING, 0);
		// What method1928 leaves behind before model geometry: GL_MODULATE and
		// normals present, so GL_LIGHTING is on.
		toolkit.method1900(1);
		toolkit.method1906(true);
	}

	@AfterAll
	static void closeToolkit() {
		HdLightFrame.resetForTest();
		HdSettings.resetForTest();
		if (toolkit != null) {
			toolkit.hdEndModelLighting();
		}
		if (frame != null) {
			frame.dispose();
		}
	}

	@BeforeEach
	void resetRow() {
		HdSettings.set(HdFeature.ENVIRONMENT_LIGHTING, 0);
		HdLightFrame.publishForTest(HdLightFrame.OFF);
	}

	@Test
	@DisplayName("with the row off the ambient is the stock one, whatever sky the map set")
	void rowOff_shouldBeTheStockFrame() {
		int[] red = draw(false, RED_SKY, AMBIENT, 0.0F, true, 32, 32);
		int[] green = draw(false, GREEN_SKY, AMBIENT, 0.0F, true, 32, 32);

		System.out.println("[hd-gl] environment row off: red sky " + describe(red) + ", green sky "
			+ describe(green));
		assertAll(
			() -> assertPixel("row off, red sky", red, expectedStock(AMBIENT, 0.0F, true)),
			() -> assertPixel("row off, green sky", green, expectedStock(AMBIENT, 0.0F, true)),
			() -> assertPixel("row off is independent of the environment", green, red)
		);
	}

	@Test
	@DisplayName("with the row on a surface facing the sky takes the environment's colour at its level")
	void rowOn_shouldTakeTheSkysColour() {
		HdSettings.set(HdFeature.ENVIRONMENT_LIGHTING, 1);
		int[] red = draw(true, RED_SKY, AMBIENT, 0.0F, true, 32, 32);
		int[] green = draw(true, GREEN_SKY, AMBIENT, 0.0F, true, 32, 32);

		System.out.println("[hd-gl] environment row on: red sky " + describe(red) + ", green sky "
			+ describe(green));
		assertAll(
			() -> assertPixel("red sky", red, expectedSky(AMBIENT, RED_SKY, 0.0F, true)),
			() -> assertPixel("green sky", green, expectedSky(AMBIENT, GREEN_SKY, 0.0F, true)),
			() -> assertTrue(red[0] > red[1], "a red sky must not brighten the green channel: " + describe(red)),
			() -> assertTrue(green[1] > green[0], "nor a green sky the red one: " + describe(green))
		);
	}

	@Test
	@DisplayName("the level ambient is the row's intensity, so the same sky brightens when the map does")
	void rowOn_shouldFollowTheLevelAmbient() {
		HdSettings.set(HdFeature.ENVIRONMENT_LIGHTING, 1);
		int[] dim = draw(true, RED_SKY, AMBIENT, 0.0F, true, 32, 32);
		int[] bright = draw(true, RED_SKY, 0.4F, 0.0F, true, 32, 32);

		System.out.println("[hd-gl] environment level: ambient " + AMBIENT + " " + describe(dim)
			+ ", ambient 0.4 " + describe(bright));
		assertAll(
			() -> assertPixel("dim", dim, expectedSky(AMBIENT, RED_SKY, 0.0F, true)),
			() -> assertPixel("bright", bright, expectedSky(0.4F, RED_SKY, 0.0F, true)),
			() -> assertTrue(bright[0] > dim[0], "twice the level ambient must be brighter: " + describe(bright))
		);
	}

	@Test
	@DisplayName("the sun's term is untouched: the row only recolours the ambient it replaces")
	void rowOn_shouldLeaveTheSunAlone() {
		HdSettings.set(HdFeature.ENVIRONMENT_LIGHTING, 0);
		int[] stock = draw(false, RED_SKY, AMBIENT, 0.5F, true, 32, 32);
		HdSettings.set(HdFeature.ENVIRONMENT_LIGHTING, 1);
		int[] tinted = draw(true, RED_SKY, AMBIENT, 0.5F, true, 32, 32);

		System.out.println("[hd-gl] environment sun: row off " + describe(stock) + ", row on "
			+ describe(tinted));
		assertAll(
			() -> assertPixel("row off with a sun", stock, expectedStock(AMBIENT, 0.5F, true)),
			() -> assertPixel("row on with a sun", tinted, expectedSky(AMBIENT, RED_SKY, 0.5F, true)),
			// The only green the row removed is the ambient's: the two frames are
			// exactly the level ambient apart in green and blue, and the sun's own
			// 0.5 is still in both.
			() -> assertTrue(Math.abs(stock[1] - tinted[1] - channel(AMBIENT)) <= TOLERANCE,
				"the sun's green must survive the row: " + describe(stock) + " vs " + describe(tinted)),
			() -> assertTrue(Math.abs(stock[2] - tinted[2] - channel(AMBIENT)) <= TOLERANCE,
				"and so must its blue: " + describe(stock) + " vs " + describe(tinted))
		);
	}

	@Test
	@DisplayName("a surface facing away from the sky keeps the stock ambient, because there is no ground colour")
	void wall_shouldKeepTheStockAmbient() {
		int[] stock = draw(false, RED_SKY, AMBIENT, 0.0F, false, 32, 32);
		HdSettings.set(HdFeature.ENVIRONMENT_LIGHTING, 1);
		int[] tinted = draw(true, RED_SKY, AMBIENT, 0.0F, false, 32, 32);

		System.out.println("[hd-gl] environment wall: row off " + describe(stock) + ", row on "
			+ describe(tinted));
		assertAll(
			() -> assertPixel("wall, row off", stock, expectedStock(AMBIENT, 0.0F, false)),
			() -> assertPixel("wall, row on", tinted, expectedSky(AMBIENT, RED_SKY, 0.0F, false)),
			() -> assertPixel("the wall is not recoloured", tinted, stock)
		);
	}

	/**
	 * The stock ambient the shader starts from: {@code gl_LightModel.ambient},
	 * which {@code B} filled with the level ambient in the sun's (white) colour,
	 * plus the sun's own term.
	 */
	private static int[] expectedStock(float level, float sunIntensity, boolean facingSky) {
		float sun = sunIntensity * (facingSky ? 1.0F : 0.0F);
		return new int[] { channel(level + sun), channel(level + sun), channel(level + sun) };
	}

	/**
	 * The row's pixel, from the shader's own equation: the ambient mixed toward
	 * {@code sky * level} by the sky weight, plus the untouched sun term.
	 */
	private static int[] expectedSky(float level, int sky, float sunIntensity, boolean facingSky) {
		// The up quad's normal is (0,1,0): the flipped world up is its own eye-space
		// normal, so the weight is 1. The wall's normal is perpendicular to it.
		float weight = facingSky ? 1.0F : 0.0F;
		float sun = sunIntensity * (facingSky ? 1.0F : 0.0F);
		return new int[] {
			channel(HdEnvironmentLight.ambientChannel(level, (sky >> 16 & 0xFF) / 255.0F * level, weight) + sun),
			channel(HdEnvironmentLight.ambientChannel(level, (sky >> 8 & 0xFF) / 255.0F * level, weight) + sun),
			channel(HdEnvironmentLight.ambientChannel(level, (sky & 0xFF) / 255.0F * level, weight) + sun)
		};
	}

	private static int channel(float linear) {
		return Math.round(clamp01(linear) * 255.0F);
	}

	private static float clamp01(float value) {
		return value < 0.0F ? 0.0F : (value > 1.0F ? 1.0F : value);
	}

	// -- Harness -----------------------------------------------------------

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

	/** One interleaved quad, laid out exactly as {@code GlModel} lays it out. */
	private static ElementsBufferGL quadBuffer(float normalX, float normalY, float normalZ) {
		// vertex (12) | colour (4) | normal (12) = 28.
		int stride = 28;
		float[][] positions = { { -8, -8, 0 }, { 8, -8, 0 }, { 8, 8, 0 }, { -8, 8, 0 } };
		ByteBuffer data = ByteBuffer.allocate(stride * positions.length).order(ByteOrder.LITTLE_ENDIAN);
		for (float[] position : positions) {
			data.putFloat(position[0]).putFloat(position[1]).putFloat(position[2]);
			data.put((byte) 255).put((byte) 255).put((byte) 255).put((byte) 255);
			data.putFloat(normalX).putFloat(normalY).putFloat(normalZ);
		}
		return new GlVboElementsArrayBuffer(toolkit, stride, data.array(), data.array().length, false);
	}

	private static ArrayBufferGL quadElements() {
		byte[] indices = { 0, 0, 1, 0, 2, 0, 0, 0, 2, 0, 3, 0 };
		return new GlVboArrayBuffer(toolkit, GL11.GL_UNSIGNED_SHORT, indices, indices.length, false);
	}

	/**
	 * One frame: the scene's matrices, the environment uploaded through the
	 * toolkit's public API, then the quad through the live seam.
	 *
	 * <p>The camera matrix the toolkit holds is reset to the identity, and the
	 * modelview is the one {@code method1952} builds - {@code rotX(180)} then the
	 * camera - so the eye-space up axis the shader receives is this frame's.
	 */
	private static int[] draw(boolean shader, int skyColour, float levelAmbient, float sunIntensity,
			boolean facingSky, int pixelX, int pixelY) {
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

		toolkit.ea(CAMERA);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glFrustum(-0.1D, 0.1D, -0.1D, 0.1D, 1.0D, 400.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -DEPTH);
		GL11.glEnable(GL11.GL_LIGHTING);

		// The stock uploads, under the modelview that is live now.
		toolkit.B(levelAmbient);
		toolkit.scaleHeight(0xFFFFFF, sunIntensity, 0.0F, 0.0F, 1.0F, 0.0F);
		toolkit.UA(skyColour, 100, FAR - 300);
		toolkit.method1918(true);
		toolkit.copyLights(0, new PointLight[0]);
		toolkit.method1938(-1, true);

		boolean bound = false;
		if (shader) {
			bound = toolkit.hdBeginModelLighting(false);
			assertTrue(bound, "the toolkit seam refused the quad with the environment row in play");
		}
		if (facingSky) {
			toolkit.method1934(upColours, upNormals, null, upVertices);
			toolkit.method1967(0, 6, upElements);
		} else {
			toolkit.method1934(wallColours, wallNormals, null, wallVertices);
			toolkit.method1967(0, 6, wallElements);
		}
		if (bound) {
			toolkit.hdEndModelLighting();
		}

		GL11.glFinish();
		assertEquals(GL11.GL_NO_ERROR, GL11.glGetError(), "GL error while drawing");
		ByteBuffer pixel = BufferUtils.createByteBuffer(4);
		GL11.glReadPixels(pixelX, pixelY, 1, 1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixel);
		int[] result = { pixel.get(0) & 0xFF, pixel.get(1) & 0xFF, pixel.get(2) & 0xFF };
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		return result;
	}

	/** {@code rotX(180) * camera == translate(0, 0, -DEPTH)}, so the quad stays in front. */
	private static JavaMatrix sceneCamera() {
		JavaMatrix camera = new JavaMatrix();
		camera.aFloat204 = -1.0F;
		camera.aFloat199 = -1.0F;
		camera.aFloat198 = DEPTH;
		return camera;
	}

	private static String describe(int[] pixel) {
		return "rgb(" + pixel[0] + "," + pixel[1] + "," + pixel[2] + ")";
	}

	private static void assertPixel(String what, int[] pixel, int[] expected) {
		System.out.println("[hd-gl] " + what + " = " + describe(pixel) + ", expected " + describe(expected));
		assertAll(
			() -> assertTrue(Math.abs(pixel[0] - expected[0]) <= TOLERANCE, what + ": red " + describe(pixel)),
			() -> assertTrue(Math.abs(pixel[1] - expected[1]) <= TOLERANCE, what + ": green " + describe(pixel)),
			() -> assertTrue(Math.abs(pixel[2] - expected[2]) <= TOLERANCE, what + ": blue " + describe(pixel))
		);
	}
}
