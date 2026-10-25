package com.jagex.game.runetek5.client.hd.ground;

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
import com.jagex.game.runetek5.PcmPlayerThread;
import com.jagex.game.runetek5.buffer.ElementsBufferGL;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.GlGroundLayer;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.client.hd.water.HdWaterTime;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.scene.ground.GlGround;

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
 * Caustics in pixels, through the production terrain draw: two adjacent tiles on
 * a real {@link GlGround}, one dry and one with the stock depth byte set, drawn
 * by {@link GlGroundLayer#method1462} - the method the scene calls every frame.
 *
 * <p>The depth is the client's own, not a test constant: a layer's
 * {@code waterDepth} (the byte {@code Terrain} reads per tile for an underwater
 * region) is what the seam turns into {@link HdGroundWater}, so a layer
 * constructed with 200 is what a submerged tile really submits.
 *
 * <p>What this proves:
 *
 * <ul>
 * <li>the row lights the submerged tile and leaves the dry neighbour pixel for
 * pixel alone;</li>
 * <li>the pattern advances with the water clock, and only on the submerged
 * tile;</li>
 * <li>the row off - and a dry tile with the row on - issues no caustics upload
 * at all, so the off state is the frame before the row existed rather than a
 * faint pattern.</li>
 * </ul>
 *	 * <p>Runs by default; {@link GlTestContext#open()} skips it with the reason
	 * when this machine cannot give a GL context.
 */
@DisplayName("592HD caustics on the live terrain draw")
class HdCausticsGlTest {

	private static final int SIZE = 64;

	private static final int TILE_UNITS = 128;

	/** The stock depth byte a submerged tile submits; 200 of 255. */
	private static final int WATER_DEPTH = 200;

	private static final int CAMERA_X = -64;

	private static final int CAMERA_Y = -32;

	private static final int CAMERA_Z = 200;

	private static final float FAR = 3584.0F;

	/** One channel step tolerated by the comparison, for rounding only. */
	private static final int CHANNEL_STEP = 2;

	private static GlToolkit toolkit;

	private static Frame frame;

	private static Canvas canvas;

	private static int framebuffer;

	private static int targetTexture;

	private static int depthBuffer;

	private static GlGround ground;

	/** Tile 0's layer: no water over it. */
	private static GlGroundLayer dryLayer;

	/** Tile 1's layer: the stock byte says 200/255 of water stands here. */
	private static GlGroundLayer wetLayer;

	private static ElementsBufferGL terrain;

	@BeforeAll
	static void openToolkit() throws Exception {
		try {
			GlTestContext warmup = GlTestContext.open();
			warmup.close();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}

		frame = new Frame("592HD caustics test");
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
		System.out.println("[hd-caustics] toolkit " + GL11.glGetString(GL11.GL_RENDERER) + " | GL "
			+ GL11.glGetString(GL11.GL_VERSION));

		createTarget();
		toolkit.setHeap(toolkit.createHeap(1 << 26));
		createTerrain();

		Path preferences = Files.createTempDirectory("hd-caustics");
		HdSettings.setDirectoryForTest(preferences.toFile());
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 1);
		HdSettings.set(HdFeature.POINT_LIGHTS, 0);
		HdSettings.set(HdFeature.SHADOW_MAP, 0);
		HdSettings.set(HdFeature.FOG, 0);
		HdSettings.set(HdFeature.DISTANCE_FADE, 0);
		HdSettings.set(HdFeature.AMBIENT_OCCLUSION, 0);
		HdSettings.set(HdFeature.CARRIED_LIGHT, 0);
		HdSettings.set(HdFeature.ROOM_LIGHT, 0);
		HdSettings.set(HdFeature.ENVIRONMENT_LIGHTING, 0);
		HdSettings.set(HdFeature.CAUSTICS, 0);
		HdWaterTime.reset();

		JavaMatrix camera = new JavaMatrix();
		camera.scaleWidth(CAMERA_X, CAMERA_Y, CAMERA_Z);
		toolkit.ea(camera);

		PcmPlayerThread.aClass4_Sub12_7 = new Packet(64);
	}

	@AfterAll
	static void closeToolkit() {
		HdSettings.resetForTest();
		HdWaterTime.reset();
		if (toolkit != null) {
			toolkit.hdEndModelLighting();
		}
		if (frame != null) {
			frame.dispose();
		}
	}

	@Test
	@DisplayName("caustics light the submerged tile and leave the dry neighbour alone")
	void caustics_shouldLightOnlyTheSubmergedTile() {
		HdSettings.set(HdFeature.CAUSTICS, 0);
		HdWaterTime.reset();
		int[] off = drawFrame(true, true);
		int[] dryOnly = drawFrame(true, false);
		int[] wetOnly = drawFrame(false, true);

		HdSettings.set(HdFeature.CAUSTICS, 1);
		int[] on = drawFrame(true, true);

		int[] dry = litMask(dryOnly);
		int[] wet = litMask(wetOnly);
		int dryChanged = changed(on, off, dry);
		int wetChanged = changed(on, off, wet);
		int darker = darker(on, off, wet);

		System.out.println("[hd-caustics] dry px=" + dry.length + " wet px=" + wet.length
			+ ", changed dry=" + dryChanged + " wet=" + wetChanged
			+ ", darker on the wet tile=" + darker
			+ ", uploads=" + toolkit.hdCausticsUploads());

		assertAll(
			() -> assertTrue(wet.length > 0, "the submerged tile must be visible"),
			() -> assertEquals(0, dryChanged,
				"a dry tile has no water to pattern, so it must not change: " + dryChanged),
			() -> assertTrue(wetChanged > 20,
				"the submerged tile must show the pattern: " + wetChanged + " px changed"),
			() -> assertEquals(0, darker,
				"caustics add light; no pixel on the wet tile may darken: " + darker),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("the pattern advances with the water clock, and only on the wet tile")
	void caustics_shouldAdvanceWithTheClock() {
		HdSettings.set(HdFeature.CAUSTICS, 1);
		HdWaterTime.reset();
		int[] first = drawFrame(true, true);

		advanceTo(500L);
		int[] later = drawFrame(true, true);

		int[] dry = litMask(drawOnlyDry());
		int[] wet = litMask(drawOnlyWet());
		int moved = changed(later, first, wet);
		int still = changed(later, first, dry);

		System.out.println("[hd-caustics] clock: wet moved " + moved + " px, dry moved " + still
			+ " px at " + HdWaterTime.seconds() + "s");

		assertAll(
			() -> assertTrue(moved > 20, "the pattern must move with the water clock: " + moved),
			() -> assertEquals(0, still, "the dry tile has nothing to animate: " + still)
		);
	}

	@Test
	@DisplayName("the row off and a dry tile both make no caustics upload at all")
	void off_shouldIssueNoUpload() {
		int[] dryOnly = litMask(drawOnlyDry());
		assertTrue(dryOnly.length > 0, "the dry tile must be visible for this to mean anything");

		HdSettings.set(HdFeature.CAUSTICS, 0);
		long before = toolkit.hdCausticsUploads();
		int[] off = drawFrame(true, true);
		int[] offAgain = drawFrame(true, true);
		long offUploads = toolkit.hdCausticsUploads() - before;

		HdSettings.set(HdFeature.CAUSTICS, 1);
		long beforeDry = toolkit.hdCausticsUploads();
		drawOnlyDry();
		long dryUploads = toolkit.hdCausticsUploads() - beforeDry;

		long beforeWet = toolkit.hdCausticsUploads();
		drawOnlyWet();
		long wetUploads = toolkit.hdCausticsUploads() - beforeWet;

		System.out.println("[hd-caustics] uploads: row off=" + offUploads + ", dry tile=" + dryUploads
			+ ", wet tile=" + wetUploads + ", off frames differ=" + differing(off, offAgain));

		assertAll(
			() -> assertEquals(0L, offUploads, "the row off must make no GL call"),
			() -> assertEquals(0, differing(off, offAgain), "two off frames are the same frame"),
			() -> assertEquals(0L, dryUploads, "no water over the tile, nothing to upload"),
			() -> assertTrue(wetUploads > 0L, "the wet tile is where the upload happens"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	// -- Frames -----------------------------------------------------------

	private static int[] drawFrame(boolean dry, boolean wet) {
		beginFrame();
		cameraMatrices();
		uploadScene();
		toolkit.method1906(true);
		if (dry) {
			dryLayer.method1462(new int[] { 0, 1 }, 2);
		}
		if (wet) {
			wetLayer.method1462(new int[] { 0, 1 }, 2);
		}
		return capture();
	}

	private static int[] drawOnlyDry() {
		return drawFrame(true, false);
	}

	private static int[] drawOnlyWet() {
		HdSettings.set(HdFeature.CAUSTICS, 1);
		return drawFrame(false, true);
	}

	/** Advances the water clock by {@code millis}, one clamped frame at a time. */
	private static void advanceTo(long millis) {
		HdWaterTime.reset();
		long remaining = millis;
		while (remaining > 0L) {
			long step = Math.min(remaining, HdWaterTime.MAX_STEP_MILLIS);
			HdWaterTime.advance(step);
			remaining -= step;
		}
	}

	private static void cameraMatrices() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glFrustum(-0.4D, 0.4D, -0.4D, 0.4D, 1.0D, 400.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef((float) CAMERA_X, (float) CAMERA_Y, (float) CAMERA_Z);
	}

	/**
	 * The scene's uploads, with a deliberately dim sun.
	 *
	 * <p>The stock harness lights a white tile to 255, where an added term has no
	 * headroom to be seen. This frame sits at roughly 100 grey - ambient 0.2 plus
	 * a 0x404040 sun's N.L - so the caustics gain is measurable rather than
	 * clamped away.
	 */
	private static void uploadScene() {
		toolkit.B(0.2F);
		toolkit.scaleHeight(0x404040, 1.0F, 0.0F, 0.0F, 0.6F, 0.8F);
		toolkit.UA(0xFF0000, 100, (int) FAR - 300);
		toolkit.method1918(false);
		toolkit.copyLights(0, new PointLight[0]);
		toolkit.method1900(1);
		toolkit.method1938(-1, true);
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
	}

	private static int[] capture() {
		GL11.glFinish();
		assertEquals(GL11.GL_NO_ERROR, GL11.glGetError(), "GL error while drawing");
		ByteBuffer pixels = BufferUtils.createByteBuffer(SIZE * SIZE * 4);
		GL11.glReadPixels(0, 0, SIZE, SIZE, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
		int[] frame = new int[SIZE * SIZE];
		for (int index = 0; index < frame.length; index++) {
			frame[index] = (pixels.get(index * 4) & 0xFF) << 16
				| (pixels.get(index * 4 + 1) & 0xFF) << 8
				| (pixels.get(index * 4 + 2) & 0xFF);
		}
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		return frame;
	}

	/** The pixels one layer's tile covers: anything that is not the blue clear. */
	private static int[] litMask(int[] frame) {
		int count = 0;
		for (int pixel : frame) {
			if (lit(pixel)) {
				count++;
			}
		}
		int[] mask = new int[count];
		int at = 0;
		for (int index = 0; index < frame.length; index++) {
			if (lit(frame[index])) {
				mask[at++] = index;
			}
		}
		return mask;
	}

	private static boolean lit(int pixel) {
		int red = (pixel >> 16) & 0xFF;
		int green = (pixel >> 8) & 0xFF;
		int blue = pixel & 0xFF;
		return !(red < 16 && green < 16 && blue > 240);
	}

	private static int changed(int[] first, int[] second, int[] mask) {
		int count = 0;
		for (int index : mask) {
			if (difference(first[index], second[index]) > CHANNEL_STEP) {
				count++;
			}
		}
		return count;
	}

	private static int darker(int[] first, int[] second, int[] mask) {
		int count = 0;
		for (int index : mask) {
			if (brightness(first[index]) + CHANNEL_STEP < brightness(second[index])) {
				count++;
			}
		}
		return count;
	}

	private static int brightness(int pixel) {
		return ((pixel >> 16) & 0xFF) + ((pixel >> 8) & 0xFF) + (pixel & 0xFF);
	}

	private static int differing(int[] first, int[] second) {
		int count = 0;
		for (int index = 0; index < first.length; index++) {
			if (difference(first[index], second[index]) > CHANNEL_STEP) {
				count++;
			}
		}
		return count;
	}

	private static int difference(int first, int second) {
		int worst = 0;
		for (int shift = 0; shift <= 16; shift += 8) {
			worst = Math.max(worst, Math.abs(((first >> shift) & 0xFF) - ((second >> shift) & 0xFF)));
		}
		return worst;
	}

	// -- Geometry ---------------------------------------------------------

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
	 * Two tiles in one real layer pair: a dry quad on the left and a submerged
	 * quad on the right, each its own layer with its own stock depth byte.
	 */
	private static void createTerrain() {
		int[][] heights = new int[3][3];
		ground = new GlGround(toolkit, 0, 0x7, 2, 2, heights, heights, TILE_UNITS);

		int stride = 28;
		float[][] positions = {
			{ 0.0F, 0.0F, 0.0F }, { 60.0F, 0.0F, 0.0F },
			{ 60.0F, 64.0F, 0.0F }, { 0.0F, 64.0F, 0.0F },
			{ 68.0F, 0.0F, 0.0F }, { 128.0F, 0.0F, 0.0F },
			{ 128.0F, 64.0F, 0.0F }, { 68.0F, 64.0F, 0.0F }
		};
		ByteBuffer data = ByteBuffer.allocate(stride * positions.length).order(ByteOrder.LITTLE_ENDIAN);
		for (float[] position : positions) {
			data.putFloat(position[0]).putFloat(position[1]).putFloat(position[2]);
			data.put((byte) 255).put((byte) 255).put((byte) 255).put((byte) 255);
			data.putFloat(0.0F).putFloat(0.0F).putFloat(1.0F);
		}
		terrain = new GlVboElementsArrayBuffer(toolkit, stride, data.array(), data.array().length, false);
		ground.aClass177_15 = new Class177(terrain, GL11.GL_FLOAT, 3, 0);
		ground.aClass177_16 = new Class177(terrain, GL11.GL_FLOAT, 3, 16);
		ground.aClass177_14 = null;
		ground.tileVertexIndices[0] = new short[] { 0, 1, 2, 0, 2, 3 };
		ground.tileVertexIndices[1] = new short[] { 4, 5, 6, 4, 6, 7 };

		// arg5 is the stock waterDepth byte: the value Terrain reads per tile and
		// GA hands to the stock water effect as depth / 255.
		dryLayer = new GlGroundLayer(ground, -1, 0, 0, 0, 0);
		wetLayer = new GlGroundLayer(ground, -1, 0, 0, 0, WATER_DEPTH);
		colour(dryLayer, 0);
		colour(wetLayer, 1);
	}

	/** One layer's colour buffer, filled for every vertex in the shared buffer. */
	private static void colour(GlGroundLayer layer, int tile) {
		layer.initColours(8);
		for (int vertex = 0; vertex < 8; vertex++) {
			layer.setVertexColour(0, vertex, 0xFFFFFF, 1.0F);
			layer.setVertexOpaque(vertex);
		}
		layer.finishColours(8);
		layer.addTriangle(0, 0, tile);
		layer.addTriangle(0, 1, tile);
	}
}
