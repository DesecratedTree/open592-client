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
 * The carried and room light rows, proven with pixels through the stock toolkit.
 *
 * <p>Same harness as {@code HdToolkitLightingGlTest}: a real {@link GlToolkit}
 * on an off-screen canvas, its own uploads, its own
 * {@code hdBeginModelLighting} seam and its own indexed draw. What is measured
 * here is only the two new terms, so the scene has no sun and no point lights -
 * the pixel is {@code ambient + term} and nothing else.
 *
 * <p>The geometry is one quad in the model's own z=0 plane. The model seam
 * passes no model matrix (only {@code GlModel.render} sets one), so
 * {@code vWorldPosition} is exactly {@code gl_Vertex}: world and model agree,
 * which is what lets a test place a light at a known scene point and predict the
 * pixel from the shader's own equation. The quad spans the visible frustum at
 * its depth, so a pixel's scene x/y is known in closed form.
 *	 * <p>Runs by default; {@link GlTestContext#open()} skips it with the reason
	 * when this machine cannot give a GL context.
 */
@DisplayName("592HD carried and room light through the stock toolkit")
class HdLightRealGlTest {

	private static final int SIZE = 64;

	private static final int TOLERANCE = 3;

	/** Far-plane distance; the harness' fog band sits outside the quad. */
	private static final int FAR = 3584;

	/** Half-extent of the visible scene at {@link #DEPTH}, from the harness frustum. */
	private static final float HALF_EXTENT = 10.0F;

	/** The quad sits this far from the eye under the harness' 0.1 frustum. */
	private static final float DEPTH = 100.0F;

	/** The scene's ambient; every expected pixel starts here. */
	private static final float AMBIENT = 0.2F;

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

		frame = new Frame("592HD light test");
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
		System.out.println("[hd-gl] light harness " + GL11.glGetString(GL11.GL_RENDERER) + " | GL "
			+ GL11.glGetString(GL11.GL_VERSION));
		createTarget();
		createVertexData();

		Path preferences = Files.createTempDirectory("hd-light");
		HdSettings.setDirectoryForTest(preferences.toFile());
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 1);
		HdSettings.set(HdFeature.POINT_LIGHTS, 0);
		// The stock fog band, geometry and seam are pinned by
		// HdToolkitLightingGlTest; here the two new rows are the only variable.
		HdSettings.set(HdFeature.FOG, 0);
		HdSettings.set(HdFeature.DISTANCE_FADE, 0);
		HdSettings.set(HdFeature.AMBIENT_OCCLUSION, 0);
		HdSettings.set(HdFeature.CARRIED_LIGHT, 0);
		HdSettings.set(HdFeature.ROOM_LIGHT, 0);
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

	/**
	 * Each test starts with both rows off and no published frame: these tests
	 * run in one toolkit and one process-wide settings store, so a row left on
	 * by an earlier test would colour the next test's "stock" frame.
	 */
	@BeforeEach
	void resetRows() {
		HdSettings.set(HdFeature.CARRIED_LIGHT, 0);
		HdSettings.set(HdFeature.ROOM_LIGHT, 0);
		HdLightFrame.publishForTest(HdLightFrame.OFF);
	}

	@Test
	@DisplayName("a carried light brightens near the carrier and falls to zero at its radius")
	void carriedLight_shouldFallOffWithDistance() {
		HdSettings.set(HdFeature.CARRIED_LIGHT, 1);
		HdLightFrame.publishForTest(HdLightFrame.of(
			new HdLightSource(0.0F, 0.0F, 1.0F, 8.0F, 0x808080, 1.0F), new HdRoomLight[0]));
		int[] near = draw(true, 32, 32);
		int[] middle = draw(true, 44, 32);
		int[] far = draw(true, 56, 32);

		int expectedNear = expectedCarried(32, 32, 8.0F, 0x808080, 1.0F);
		int expectedMiddle = expectedCarried(44, 32, 8.0F, 0x808080, 1.0F);
		int expectedFar = expectedCarried(56, 32, 8.0F, 0x808080, 1.0F);
		System.out.println("[hd-gl] carried: near " + describe(near) + " (expected " + expectedNear
			+ "), middle " + describe(middle) + " (expected " + expectedMiddle
			+ "), far " + describe(far) + " (expected " + expectedFar + ")");

		assertAll(
			() -> assertPixel("carried, near", near, expectedNear, expectedNear, expectedNear),
			() -> assertPixel("carried, middle", middle, expectedMiddle, expectedMiddle, expectedMiddle),
			() -> assertPixel("carried, far", far, expectedFar, expectedFar, expectedFar),
			// The three samples are the row's shape, not one tinted frame.
			() -> assertTrue(near[0] > middle[0], "near " + near[0] + " vs middle " + middle[0]),
			() -> assertTrue(middle[0] > far[0], "middle " + middle[0] + " vs far " + far[0]),
			// Ambient only: past the radius the term is exactly zero.
			() -> assertEquals(51, far[0], "beyond the radius the term must be gone")
		);
	}

	@Test
	@DisplayName("the carried light keeps the colour the map light carries")
	void carriedLight_shouldKeepItsColour() {
		HdSettings.set(HdFeature.CARRIED_LIGHT, 1);
		HdLightFrame.publishForTest(HdLightFrame.of(
			new HdLightSource(0.0F, 0.0F, 1.0F, 8.0F, 0xFF0000, 0.5F), new HdRoomLight[0]));
		int[] centre = draw(true, 32, 32);
		int expected = expectedCarried(32, 32, 8.0F, 0xFF0000, 0.5F);
		System.out.println("[hd-gl] carried red: " + describe(centre) + " (expected red " + expected + ")");
		assertAll(
			() -> assertPixel("carried red", centre, expected, 51, 51),
			() -> assertTrue(centre[0] > centre[1], "the red channel must be the light's own")
		);
	}

	@Test
	@DisplayName("a room light tints its own region and leaves the next one alone")
	void roomLight_shouldBeBoundedToItsRegion() {
		HdSettings.set(HdFeature.ROOM_LIGHT, 1);
		HdLightFrame.publishForTest(HdLightFrame.of(null, new HdRoomLight[] {
			new HdRoomLight(-10, -10, 0, 10, 0xFF0000, 0.3F),
			new HdRoomLight(0, -10, 10, 10, 0x00FF00, 0.3F)
		}));
		int[] left = draw(true, 16, 32);
		int[] right = draw(true, 48, 32);
		System.out.println("[hd-gl] room: left " + describe(left) + ", right " + describe(right));

		assertAll(
			// ambient 0.2 + (1,0,0) * 0.3 = (0.5, 0.2, 0.2)
			() -> assertPixel("room, first region", left, 128, 51, 51),
			// ambient 0.2 + (0,1,0) * 0.3 = (0.2, 0.5, 0.2)
			() -> assertPixel("room, second region", right, 51, 128, 51)
		);
	}

	@Test
	@DisplayName("a room light cannot leak into the region beside it")
	void roomLight_shouldNotLeakAcrossTheSeam() {
		HdSettings.set(HdFeature.ROOM_LIGHT, 1);
		HdLightFrame.publishForTest(HdLightFrame.of(null, new HdRoomLight[] {
			new HdRoomLight(-10, -10, 0, 10, 0xFF0000, 0.3F)
		}));
		int[] inside = draw(true, 16, 32);
		int[] beside = draw(true, 48, 32);
		System.out.println("[hd-gl] room seam: inside " + describe(inside) + ", beside " + describe(beside));

		assertAll(
			() -> assertPixel("inside the only region", inside, 128, 51, 51),
			// Exactly the ambient floor: the fragment is one region over, and the
			// rectangle test has no falloff to borrow.
			() -> assertPixel("one region over", beside, 51, 51, 51)
		);
	}

	@Test
	@DisplayName("with both rows off, a published frame changes no pixel at all")
	void rowsOff_shouldLeaveTheStockDrawUntouched() {
		HdLightFrame.publishForTest(HdLightFrame.of(
			new HdLightSource(0.0F, 0.0F, 1.0F, 8.0F, 0xFFFFFF, 1.0F),
			new HdRoomLight[] { new HdRoomLight(-10, -10, 10, 10, 0xFF00FF, 0.5F) }));
		int[] stock = draw(false, 32, 32);
		int[] shaderRowsOff = draw(true, 32, 32);
		HdLightFrame.publishForTest(HdLightFrame.OFF);
		int[] shaderNoFrame = draw(true, 32, 32);

		System.out.println("[hd-gl] rows off: fixed " + describe(stock) + ", shader " + describe(shaderRowsOff)
			+ ", no frame " + describe(shaderNoFrame));
		assertAll(
			() -> assertPixel("stock draw", stock, 51, 51, 51),
			() -> assertEquals(describe(shaderRowsOff), describe(shaderNoFrame),
				"a frame with both rows off must not move a pixel"),
			() -> assertClose("shader vs fixed function", shaderRowsOff, stock)
		);
	}

	@Test
	@DisplayName("turning a row on and off again restores the exact frame")
	void rowsToggle_shouldRoundTrip() {
		HdLightFrame.publishForTest(HdLightFrame.of(
			new HdLightSource(0.0F, 0.0F, 1.0F, 8.0F, 0x808080, 1.0F), new HdRoomLight[0]));
		int[] off = draw(true, 32, 32);
		HdSettings.set(HdFeature.CARRIED_LIGHT, 1);
		int[] on = draw(true, 32, 32);
		HdSettings.set(HdFeature.CARRIED_LIGHT, 0);
		int[] backOff = draw(true, 32, 32);

		System.out.println("[hd-gl] carried toggle: off " + describe(off) + ", on " + describe(on)
			+ ", off again " + describe(backOff));
		assertAll(
			() -> assertEquals(describe(off), describe(backOff), "off must be the identity in both directions"),
			() -> assertTrue(on[0] > off[0], "the row must actually add the term")
		);
	}

	// -- Expected pixels ---------------------------------------------------

	/**
	 * The shader's carried term, restated: {@code ambient + colour * intensity *
	 * (1 - d/r)^2}, where {@code d} is the distance from the fragment's scene
	 * position to the light. The light sits one unit in front of the quad's
	 * z=0 plane, and the fragment's scene position is its {@code gl_Vertex}
	 * because the model matrix is identity.
	 */
	private static int expectedCarried(int pixelX, int pixelY, float radius, int rgb, float intensity) {
		float worldX = sceneX(pixelX);
		float worldY = sceneY(pixelY);
		float distance = (float) Math.sqrt(worldX * worldX + worldY * worldY + 1.0);
		float window = clamp01(1.0F - distance / radius);
		float red = (float) (rgb >> 16 & 0xFF) / 255.0F;
		return channel(AMBIENT + red * intensity * window * window);
	}

	private static float sceneX(int pixelX) {
		return ((pixelX + 0.5F) / (SIZE / 2.0F) - 1.0F) * HALF_EXTENT;
	}

	private static float sceneY(int pixelY) {
		return ((pixelY + 0.5F) / (SIZE / 2.0F) - 1.0F) * HALF_EXTENT;
	}

	private static float clamp01(float value) {
		return value < 0.0F ? 0.0F : (value > 1.0F ? 1.0F : value);
	}

	private static int channel(float linear) {
		return Math.round(clamp01(linear) * 255.0F);
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

	/** One interleaved buffer, laid out exactly as {@code GlModel} lays it out. */
	private static void createVertexData() {
		// vertex (12) | colour (4) | normal (12) = 28.
		int stride = 28;
		float[][] positions = { { -8, -8, 0 }, { 8, -8, 0 }, { 8, 8, 0 }, { -8, 8, 0 } };
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

	/**
	 * Draws the quad with or without the HD program and returns one pixel.
	 *
	 * <p>The scene is ambient 0.2, no sun (diffuse and anti-sun zero), no point
	 * lights and a fog band whose far edge is outside the quad, so the pixel is
	 * {@code ambient + carried + room} with no other term.
	 */
	private static int[] draw(boolean shader, int pixelX, int pixelY) {
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

		toolkit.B(AMBIENT);
		toolkit.scaleHeight(0xFFFFFF, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F);
		toolkit.UA(0xFF0000, 100, FAR - 300);
		toolkit.method1918(true);
		toolkit.copyLights(0, new PointLight[0]);
		toolkit.method1938(-1, true);

		boolean bound = false;
		if (shader) {
			bound = toolkit.hdBeginModelLighting(false);
			assertTrue(bound, "the toolkit seam refused the quad with the light rows in play");
		}
		toolkit.method1934(colours, normals, null, vertices);
		toolkit.method1967(0, 6, elements);
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

	private static String describe(int[] pixel) {
		return "rgb(" + pixel[0] + "," + pixel[1] + "," + pixel[2] + ")";
	}

	private static void assertPixel(String what, int[] pixel, int red, int green, int blue) {
		System.out.println("[hd-gl] " + what + " = " + describe(pixel) + ", expected rgb(" + red + ","
			+ green + "," + blue + ")");
		assertAll(
			() -> assertTrue(Math.abs(pixel[0] - red) <= TOLERANCE, what + ": red " + pixel[0]),
			() -> assertTrue(Math.abs(pixel[1] - green) <= TOLERANCE, what + ": green " + pixel[1]),
			() -> assertTrue(Math.abs(pixel[2] - blue) <= TOLERANCE, what + ": blue " + pixel[2])
		);
	}

	private static void assertClose(String what, int[] actual, int[] expected) {
		assertAll(
			() -> assertTrue(Math.abs(actual[0] - expected[0]) <= TOLERANCE,
				what + ": red " + actual[0] + " vs " + expected[0]),
			() -> assertTrue(Math.abs(actual[1] - expected[1]) <= TOLERANCE,
				what + ": green " + actual[1] + " vs " + expected[1]),
			() -> assertTrue(Math.abs(actual[2] - expected[2]) <= TOLERANCE,
				what + ": blue " + actual[2] + " vs " + expected[2])
		);
	}
}
