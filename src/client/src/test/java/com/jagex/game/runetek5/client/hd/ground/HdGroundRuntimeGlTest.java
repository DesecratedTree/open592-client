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
import com.jagex.game.runetek5.buffer.ArrayBufferGL;
import com.jagex.game.runetek5.buffer.ElementsBufferGL;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.GlGroundLayer;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.client.hd.shadow.ShadowCaster;
import com.jagex.game.runetek5.client.hd.shadow.SunShadowFrame;
import com.jagex.game.runetek5.client.hd.shadow.SunShadowSubmission;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.scene.ground.GlGround;
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
 * The live terrain draw in pixels: a real {@link GlGroundLayer} submitted
 * through {@code method1462}, the method the scene calls every frame.
 *
 * <p>The earlier proof lit an imitation of the pass: a tile-space quad under the
 * bake's identity modelview and tile-window ortho. That is a shape the live
 * frame never draws. This one builds the objects the live frame builds - a
 * {@link GlGround} whose vertex buffer is already in scene units (128 per tile,
 * what {@code GlToolkit.createGround} asks for), its layer's own colour buffer -
 * and calls the production method with the pass's matrices: {@code method1952}'s
 * {@code rotX(180) * V} under the camera's projection. Only the seam's bind is
 * inside that method; the test supplies nothing the scene does not.
 *
 * <p>Four facts, each of which was false before the hook:
 *
 * <ul>
 * <li><b>The live draw binds the program</b> - exactly once per layer draw, with
 * GL left clean.</li>
 * <li><b>Its pixels are a model's pixels</b> at the same world place: the same
 * buffers drawn through {@code hdBeginModelLighting} with an identity model
 * matrix produce the same frame, so the terrain's shade is the reference path's
 * shade rather than a plausible colour.</li>
 * <li><b>A caster's shadow lands on it</b>, and only when the seam binds: with
 * the row off the same caster leaves the stock fixed-function tile untouched,
 * because the stock path never samples the map.</li>
 * <li><b>The map bake still stands aside</b>: with the row on and
 * {@code hdMapBakeBegin} held around the draw, the tile is the stock tile pixel
 * for pixel and the counter does not move.</li>
 * </ul>
 *	 * <p>Runs by default; {@link GlTestContext#open()} skips it with the reason
	 * when this machine cannot give a GL context.
 */
@DisplayName("592HD live terrain lighting")
class HdGroundRuntimeGlTest {

	private static final int SIZE = 64;

	/** The scaling {@code GlToolkit.createGround} builds the pass with. */
	private static final int TILE_UNITS = 128;

	private static final float SUN_X = 0.0F;

	private static final float SUN_Y = 0.6F;

	private static final float SUN_Z = 0.8F;

	/** The eye the toolkit's camera matrix is built for; see the ground test. */
	private static final int CAMERA_X = -64;

	private static final int CAMERA_Y = -32;

	/** Positive because JavaMatrix looks along +z; the toolkit's flip makes it -z. */
	private static final int CAMERA_Z = 200;

	private static final float FAR = 3584.0F;

	/** Pixels allowed to differ: one shadow edge decided by float association. */
	private static final int EDGE_PIXELS = 3;

	private static GlToolkit toolkit;

	private static Frame frame;

	private static Canvas canvas;

	private static int framebuffer;

	private static int targetTexture;

	private static int depthBuffer;

	/** The pass's own object: scene-unit vertices, one layer, one tile. */
	private static GlGround ground;

	private static GlGroundLayer layer;

	private static ElementsBufferGL terrain;

	private static Class177 terrainColours;

	private static ArrayBufferGL terrainIndices;

	/** A wall in front of the receiver, in scene units, for the depth pass. */
	private static ElementsBufferGL occluders;

	private static Class177 occluderColours;

	private static Class177 occluderNormals;

	private static Class177 occluderVertices;

	private static ArrayBufferGL occluderIndices;

	@BeforeAll
	static void openToolkit() throws Exception {
		try {
			GlTestContext warmup = GlTestContext.open();
			warmup.close();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}

		frame = new Frame("592HD live terrain test");
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
		System.out.println("[hd-live] toolkit " + GL11.glGetString(GL11.GL_RENDERER) + " | GL "
			+ GL11.glGetString(GL11.GL_VERSION));

		createTarget();
		// A heap large enough for the toolkit's own start-up buffers as well as
		// the layer's colour stream.
		toolkit.setHeap(toolkit.createHeap(1 << 26));
		createTerrain();
		createOccluders();

		Path preferences = Files.createTempDirectory("hd-live");
		HdSettings.setDirectoryForTest(preferences.toFile());
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 1);
		HdSettings.set(HdFeature.POINT_LIGHTS, 0);
		HdSettings.set(HdFeature.SHADOW_MAP, 1);
		HdSettings.set(HdFeature.FOG, 0);
		HdSettings.set(HdFeature.DISTANCE_FADE, 0);
		HdSettings.set(HdFeature.AMBIENT_OCCLUSION, 0);
		// This class pins the ground's shade; a row left on by another class
		// would add a light term to the pixels it compares.
		HdSettings.set(HdFeature.CARRIED_LIGHT, 0);
		HdSettings.set(HdFeature.ROOM_LIGHT, 0);

		// The camera the ground seam reads for scene-to-eye, and the same one the
		// toolkit loads as the modelview before the pass.
		JavaMatrix camera = new JavaMatrix();
		camera.scaleWidth(CAMERA_X, CAMERA_Y, CAMERA_Z);
		toolkit.ea(camera);

		// method1462 packs the layer's visible indices through this packet.
		PcmPlayerThread.aClass4_Sub12_7 = new Packet(64);
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
	@DisplayName("the live layer draw binds the terrain seam, once")
	void liveDraw_shouldBindTheSeam() {
		long before = toolkit.hdLightingBinds();
		int[] live = drawLive(false);
		long binds = toolkit.hdLightingBinds() - before;

		System.out.println("[hd-live] layer draw: binds=" + binds + ", lit pixels=" + lit(live));
		assertAll(
			() -> assertEquals(1L, binds, "one layer draw must be one bind"),
			() -> assertTrue(lit(live) > 0, "the tile must actually be drawn"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("the live terrain is lit like a model at the same place")
	void liveDraw_shouldBeLitLikeAModel() {
		int[] live = drawLive(false);
		int[] model = drawModel(false);

		int differing = differing(live, model);
		System.out.println("[hd-live] live vs model: differing pixels=" + differing
			+ ", lit=" + lit(live) + "/" + lit(model));
		assertAll(
			() -> assertTrue(lit(live) > 0, "the tile must be visible"),
			() -> assertTrue(shadowed(live) == 0, "no caster: the whole tile is lit"),
			() -> assertEquals(0, differing,
				"the live path must shade a world point exactly as the model path does: "
					+ differing + " pixel(s) differ")
		);
	}

	@Test
	@DisplayName("a caster's shadow lands on the live terrain draw, not only on the bake")
	void shadow_shouldLandOnTheLiveDraw() {
		int[] live = drawLive(true);
		int[] model = drawModel(true);
		int[] stock = drawStock(true);

		int liveShadow = shadowed(live);
		int stockShadow = shadowed(stock);
		System.out.println("[hd-live] with a caster: live shadowed=" + liveShadow
			+ ", model shadowed=" + shadowed(model) + ", stock shadowed=" + stockShadow
			+ ", lit=" + lit(live));
		assertAll(
			() -> assertTrue(liveShadow > 0, "the caster must darken the live tile: " + liveShadow),
			() -> assertTrue(lit(live) > 0, "and must not cover it"),
			() -> assertTrue(stockShadow == 0,
				"the stock path has no sun-shadow sampling; it must not go dark: " + stockShadow),
			() -> assertTrue(Math.abs(liveShadow - shadowed(model)) <= EDGE_PIXELS,
				"the live tile must shadow where the model path does: " + liveShadow
					+ " vs " + shadowed(model)),
			() -> assertTrue(differing(live, stock) >= liveShadow - EDGE_PIXELS,
				"the lit frame must differ from stock by at least its shadowed area")
		);
	}

	@Test
	@DisplayName("the bake guard declines both seams and lifts when the bake ends")
	void mapBake_shouldDeclineBothSeams() {
		toolkit.hdMapBakeBegin();
		boolean groundLit;
		boolean modelLit;
		try {
			groundLit = toolkit.hdBeginGroundLighting(false, TILE_UNITS, 0, 0, HdGroundWater.DRY);
			modelLit = toolkit.hdBeginModelLighting(false);
		} finally {
			toolkit.hdMapBakeEnd();
		}
		boolean live = toolkit.hdBeginGroundLighting(false, TILE_UNITS, 0, 0, HdGroundWater.DRY);
		toolkit.hdEndModelLighting();

		System.out.println("[hd-live] bake: ground seam=" + groundLit + " model seam=" + modelLit
			+ ", after the bake=" + live);
		assertAll(
			() -> assertTrue(!groundLit, "the map bake must keep the stock ground draw"),
			() -> assertTrue(!modelLit, "and the stock model draw"),
			() -> assertTrue(live, "the live seam must be back once the bake ends"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("the map bake draws the stock live tile, pixel for pixel")
	void bake_shouldDrawTheStockTile() {
		long before = toolkit.hdLightingBinds();
		int[] baked = drawLive(true, true);
		long binds = toolkit.hdLightingBinds() - before;
		int[] stock = drawStock(true);

		System.out.println("[hd-live] bake: binds=" + binds + ", differing pixels="
			+ differing(baked, stock));
		assertAll(
			() -> assertEquals(0L, binds, "the bake may not bind the scene's program"),
			() -> assertTrue(lit(baked) > 0, "the tile must actually be drawn"),
			() -> assertEquals(0, differing(baked, stock),
				"the bake must be the stock draw, not the scene's"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("the scene's caster submission darkens the live terrain, and only with a caster")
	void submission_shouldShadowTheLiveTerrain() {
		// This test submits without a draw first, so it must load the camera and
		// the sun it expects instead of relying on a previous test's frame.
		cameraMatrices();
		uploadScene();
		// The production frame reaches this seam with the scene's culling state,
		// and model draws leave it on. A caster must still land in the map: a
		// depth pass is two-sided whichever way the triangles wind.
		GL11.glEnable(GL11.GL_CULL_FACE);
		SunShadowSubmission submission = toolkit.hdShadowSubmission();
		submission.reset();
		ShadowCaster.Draw wall = new ShadowCaster.Draw() {
			@Override
			public void draw() {
				toolkit.method1934(occluderColours, occluderNormals, null, occluderVertices);
				toolkit.method1967(0, 6, occluderIndices);
			}
		};

		// The caster is already in scene units, so its own transform is identity
		// and the submission's job is the volume and the pass, not the placement.
		submission.beginFrame(32.0F, 32.0F, 10.0F, 200.0F);
		submission.addAt(0.0F, 0.0F, 0.0F, wall);
		assertTrue(submission.submit(toolkit), "the submission must run the pass: "
			+ submission.stats());
		boolean cullAfterPass = GL11.glIsEnabled(GL11.GL_CULL_FACE);
		int[] shadowedFrame = drawLive(false);

		// Same sun, same camera, no caster: only a map built this frame can lose
		// the shadow, which is what makes the dark pixels the caster's.
		submission.beginFrame(32.0F, 32.0F, 10.0F, 200.0F);
		assertTrue(submission.submit(toolkit), "the empty frame must rebuild too");
		int[] litFrame = drawLive(false);

		System.out.println("[hd-live] submission: shadowed " + shadowed(shadowedFrame)
			+ ", empty map " + shadowed(litFrame) + ", lit " + lit(shadowedFrame)
			+ ", differing " + differing(shadowedFrame, litFrame));
		assertAll(
			() -> assertTrue(shadowed(shadowedFrame) > 0,
				"the caster must darken the live tile: " + shadowed(shadowedFrame)),
			() -> assertTrue(lit(shadowedFrame) > 0, "and must not cover it"),
			// The pass disables culling for its own draw and puts it back, so the
			// stock draw after it sees exactly the state it left.
			() -> assertTrue(cullAfterPass, "the pass must restore the scene's culling state"),
			() -> assertEquals(0, shadowed(litFrame), "no caster, no shadow: " + shadowed(litFrame)),
			() -> assertTrue(differing(shadowedFrame, litFrame) >= shadowed(shadowedFrame) - EDGE_PIXELS,
				"only the shadowed area may change"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	// -- Frames -----------------------------------------------------------

	/**
	 * One frame down the live path: the camera's projection, {@code method1952}'s
	 * modelview, the pass's lit state, then the production draw.
	 */
	private static int[] drawLive(boolean withShadow) {
		return drawLive(withShadow, false);
	}

	/**
	 * {@link #drawLive(boolean)} with {@code bake} holding
	 * {@code hdMapBakeBegin/End} around the draw, exactly where
	 * {@code QuickChatCategoryType} holds it.
	 */
	private static int[] drawLive(boolean withShadow, boolean bake) {
		if (withShadow) {
			rebuildShadow();
		}
		beginFrame();
		cameraMatrices();
		uploadScene();
		// method4034's lit pass: the ground shader is enabled and its normals
		// are the array the layer binds.
		toolkit.method1906(true);
		if (bake) {
			toolkit.hdMapBakeBegin();
		}
		try {
			layer.method1462(new int[] { 0 }, 1);
		} finally {
			if (bake) {
				toolkit.hdMapBakeEnd();
			}
		}
		return capture();
	}

	/**
	 * The reference frame: the same scene-unit buffer drawn as a model, with the
	 * model seam and an identity model matrix.
	 */
	private static int[] drawModel(boolean withShadow) {
		if (withShadow) {
			rebuildShadow();
		}
		beginFrame();
		cameraMatrices();
		uploadScene();
		toolkit.method1948(new JavaMatrix());
		boolean bound = toolkit.hdBeginModelLighting(false);
		assertTrue(bound, "the model seam must bind for the reference frame to exist");
		toolkit.method1934(terrainColours, ground.aClass177_16, null, ground.aClass177_15);
		toolkit.method1967(0, 6, terrainIndices);
		GL11.glPopMatrix();
		toolkit.hdEndModelLighting();
		return capture();
	}

	/**
	 * The stock frame: the same live draw with the row off, so no seam binds.
	 *
	 * <p>The map is still built first, with the row on - otherwise "the stock
	 * tile has no shadow" would only prove the map was never rendered. The point
	 * is that the stock draw ignores a map that exists.
	 */
	private static int[] drawStock(boolean withShadow) {
		if (withShadow) {
			rebuildShadow();
		}
		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 0);
		try {
			return drawLive(false);
		} finally {
			HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 1);
		}
	}

	/** {@code method1952}: identity, rotX(180), then the camera, under the scene's frustum. */
	private static void cameraMatrices() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glFrustum(-0.4D, 0.4D, -0.4D, 0.4D, 1.0D, 400.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef((float) CAMERA_X, (float) CAMERA_Y, (float) CAMERA_Z);
	}

	/** The stock uploads, under whatever modelview the frame currently holds. */
	private static void uploadScene() {
		toolkit.B(0.2F);
		toolkit.scaleHeight(0xFFFFFF, 1.0F, 0.0F, SUN_X, SUN_Y, SUN_Z);
		toolkit.UA(0xFF0000, 100, (int) FAR - 300);
		toolkit.method1918(false);
		toolkit.copyLights(0, new PointLight[0]);
		toolkit.method1900(1);
		toolkit.method1938(-1, true);
	}

	/**
	 * Rebuilds the map from this test's own scene state.
	 *
	 * <p>Self-contained on purpose: the pass reads the camera matrices and the
	 * sun's direction, and neither can be inherited from whatever frame happened
	 * to run last. Without this, running the single submission test (e.g. the
	 * {@code --tests "*Shadow*"} filter) left the pass with default matrices and
	 * an empty map.
	 */
	private static void rebuildShadow() {
		cameraMatrices();
		uploadScene();
		boolean ran = toolkit.hdSunShadowFrame(32.0F, 32.0F, 10.0F, 200.0F, new SunShadowFrame.Casters() {
			@Override
			public void drawCasters() {
				toolkit.method1934(occluderColours, occluderNormals, null, occluderVertices);
				toolkit.method1967(0, 6, occluderIndices);
			}
		});
		assertTrue(ran, "the shadow pass must run for this frame to be comparable: "
			+ toolkit.hdSunShadowDriver().lastSkip().label() + " (hd=" + HdSettings.isEnabled()
			+ " lighting=" + HdSettings.value(HdFeature.PER_PIXEL_LIGHTING)
			+ " shadows=" + HdSettings.value(HdFeature.SHADOW_MAP) + ")");
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

	/** The frame as packed {@code 0xRRGGBB}. */
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

	/** Pixels differing by more than a channel step. */
	private static int differing(int[] first, int[] second) {
		int count = 0;
		for (int index = 0; index < first.length; index++) {
			if (difference(first[index], second[index]) > 2) {
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

	/** Receiver pixels the sun reaches: the lit albedo, not the blue clear colour. */
	private static int lit(int[] frame) {
		int count = 0;
		for (int pixel : frame) {
			if (((pixel >> 16) & 0xFF) > 200 && ((pixel >> 8) & 0xFF) > 200 && (pixel & 0xFF) > 200) {
				count++;
			}
		}
		return count;
	}

	/** Receiver pixels in shadow: ambient only, around 51 grey. */
	private static int shadowed(int[] frame) {
		int count = 0;
		for (int pixel : frame) {
			int red = (pixel >> 16) & 0xFF;
			if (red > 20 && red < 120 && (pixel & 0xFF) < 120) {
				count++;
			}
		}
		return count;
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
	 * A real {@link GlGround} and one real layer, populated the way the bake would
	 * fill them - except the vertex buffer is written directly in scene units, so
	 * the tiles land in the camera's frame without a model matrix.
	 */
	private static void createTerrain() {
		int[][] heights = new int[3][3];
		ground = new GlGround(toolkit, 0, 0x7, 2, 2, heights, heights, TILE_UNITS);

		// vertex (12) | colour (4) | normal (12) = 28, the layout GlModel uses.
		int stride = 28;
		float[][] positions = {
			{ 0.0F, 0.0F, 0.0F }, { 128.0F, 0.0F, 0.0F },
			{ 128.0F, 64.0F, 0.0F }, { 0.0F, 64.0F, 0.0F }
		};
		ByteBuffer data = ByteBuffer.allocate(stride * positions.length).order(ByteOrder.LITTLE_ENDIAN);
		for (float[] position : positions) {
			data.putFloat(position[0]).putFloat(position[1]).putFloat(position[2]);
			data.put((byte) 255).put((byte) 255).put((byte) 255).put((byte) 255);
			data.putFloat(0.0F).putFloat(0.0F).putFloat(1.0F);
		}
		terrain = new GlVboElementsArrayBuffer(toolkit, stride, data.array(), data.array().length, false);
		terrainColours = new Class177(terrain, GL11.GL_UNSIGNED_BYTE, 4, 12);
		ground.aClass177_15 = new Class177(terrain, GL11.GL_FLOAT, 3, 0);
		ground.aClass177_16 = new Class177(terrain, GL11.GL_FLOAT, 3, 16);
		ground.aClass177_14 = null;
		ground.tileVertexIndices[0] = new short[] { 0, 1, 2, 0, 2, 3 };
		terrainIndices = triangles();

		layer = new GlGroundLayer(ground, -1, 0, 0, 0, 0);
		layer.initColours(4);
		for (int vertex = 0; vertex < 4; vertex++) {
			layer.setVertexColour(0, vertex, 0xFFFFFF, 1.0F);
			layer.setVertexOpaque(vertex);
		}
		layer.finishColours(4);
		layer.addTriangle(0, 0, 0);
		layer.addTriangle(0, 1, 0);
	}

	/** Scene units, in front of the receiver, so its shadow lands on it. */
	private static void createOccluders() {
		int stride = 28;
		float[][] positions = {
			{ 0.0F, 0.0F, 20.0F }, { 20.0F, 0.0F, 20.0F },
			{ 20.0F, 64.0F, 20.0F }, { 0.0F, 64.0F, 20.0F }
		};
		ByteBuffer data = ByteBuffer.allocate(stride * positions.length).order(ByteOrder.LITTLE_ENDIAN);
		for (float[] position : positions) {
			data.putFloat(position[0]).putFloat(position[1]).putFloat(position[2]);
			data.put((byte) 255).put((byte) 255).put((byte) 255).put((byte) 255);
			data.putFloat(0.0F).putFloat(0.0F).putFloat(-1.0F);
		}
		occluders = new GlVboElementsArrayBuffer(toolkit, stride, data.array(), data.array().length, false);
		occluderColours = new Class177(occluders, GL11.GL_UNSIGNED_BYTE, 4, 12);
		occluderNormals = new Class177(occluders, GL11.GL_FLOAT, 3, 16);
		occluderVertices = new Class177(occluders, GL11.GL_FLOAT, 3, 0);
		occluderIndices = triangles();
	}

	private static ArrayBufferGL triangles() {
		byte[] indices = { 0, 0, 1, 0, 2, 0, 0, 0, 2, 0, 3, 0 };
		return new GlVboArrayBuffer(toolkit, GL11.GL_UNSIGNED_SHORT, indices, indices.length, false);
	}
}
