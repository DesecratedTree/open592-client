package com.jagex.game.runetek5.client.hd;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

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
 * The frame seam the world pass actually calls, on a real driver: the slot, the
 * order, and the counter.
 *
 * <p>{@link HdFrameSequence} is the one call {@code Skybox.method5247} makes
 * between clearing the frame and submitting the world. This drives that call
 * with the same camera and frustum the world pass has at that point, and proves
 * the three things the wiring can get wrong:
 *
 * <ul>
 * <li><b>It draws.</b> With the Sky row on and no stock skybox, the call returns
 * true, the toolkit's draw counter advances by exactly one, and the frame stops
 * being the clear colour.</li>
 * <li><b>Off is the stock frame.</b> 592HD off, the Sky row off, or a stock
 * skybox already drawn: the call returns false, the counter does not move, and
 * the frame is the clear colour byte for byte - no dome, no partially issued
 * pass.</li>
 * <li><b>The order holds.</b> The dome is a background: it writes no depth, so
 * world geometry drawn after it at any depth still covers it. A dome that wrote
 * depth would hide that geometry.</li>
 * </ul>
 *
 * <p>{@code hdSkyDraws()} is the reachability counter: it counts the frames
 * that reached the seam, so a live client's {@code ::hdstats} shows whether
 * the call site is being executed rather than merely present.
 *	 * <p>Runs by default; {@link GlTestContext#open()} skips it with the reason
	 * when this machine cannot give a GL context.
 */
@DisplayName("592HD frame sequence")
class HdFrameSequenceTest {

	private static final int SIZE = 64;

	/** Sentinel clear: magenta cannot occur in this sky, so a miss is visible. */
	private static final int CLEAR = 0xFF00FF;

	private static final int FOG = 0x203040;

	private static final int SUN = 0xFFF0D0;

	private static GlToolkit toolkit;

	private static Frame frame;

	private static int framebuffer;

	private static int targetTexture;

	private static int depthBuffer;

	@BeforeAll
	static void openToolkit() throws Exception {
		try {
			GlTestContext warmup = GlTestContext.open();
			warmup.close();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}

		frame = new Frame("592HD frame sequence test");
		Canvas canvas = new Canvas();
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
		// The game publishes its drawing toolkit here; the seam resolves it the
		// same way, so the test proves the resolver, not a shortcut.
		MonoStereoData.aClass19_16 = toolkit;
		System.out.println("[hd-frame] toolkit " + GL11.glGetString(GL11.GL_RENDERER) + " | GL "
			+ GL11.glGetString(GL11.GL_VERSION));

		createTarget();

		Path preferences = Files.createTempDirectory("hd-frame");
		HdSettings.setDirectoryForTest(preferences.toFile());
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.SKY, 1);
		HdSettings.set(HdFeature.DAY_NIGHT, 0);

		// The scene's view and camera, as the world pass leaves them: a square
		// frustum and a camera at the origin looking down -z.
		toolkit.H(SIZE / 2, SIZE / 2, SIZE, SIZE);
		toolkit.ea(new JavaMatrix());
		toolkit.UA(FOG, 1000, 3000);
		// A sun above the horizon, or the sky rows have nothing daylight to
		// draw - the same reason the stock sun has to be up.
		toolkit.scaleHeight(SUN, 1.0F, 0.6F, 0.0F, -0.5F, 0.866F);
	}

	@AfterAll
	static void closeToolkit() {
		HdSettings.resetForTest();
		if (frame != null) {
			frame.dispose();
		}
	}

	@Test
	@DisplayName("the world pass's sky slot draws once per frame, and counts it")
	void skySlot_shouldDrawAndCount() {
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.SKY, 1);
		long before = toolkit.hdSkyDraws();
		beginFrame();
		boolean drawn = HdFrameSequence.drawSky(false);
		int[] frame = capture();
		long after = toolkit.hdSkyDraws();

		int centre = frame[SIZE * SIZE / 2 + SIZE / 2];
		System.out.println("[hd-frame] sky slot: drawn=" + drawn + " draws " + before + " -> " + after
			+ ", centre " + hex(centre));
		assertAll(
			() -> assertTrue(drawn, "the dome must draw with the row on and no stock skybox"),
			() -> assertEquals(before + 1, after, "the seam must count exactly this frame"),
			() -> assertNotEquals(CLEAR, centre, "the dome must have painted the background"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("off means no dome and the stock frame, byte for byte")
	void skySlot_shouldStandAsideWhenOff() {
		beginFrame();
		int[] cleared = capture();
		long before = toolkit.hdSkyDraws();

		// The row off.
		HdSettings.set(HdFeature.SKY, 0);
		beginFrame();
		boolean rowOff = HdFrameSequence.drawSky(false);
		int[] rowOffFrame = capture();

		// 592HD itself off, with the row back on: the row alone is not enough.
		HdSettings.set(HdFeature.SKY, 1);
		HdSettings.setEnabled(false, null);
		beginFrame();
		boolean hdOff = HdFrameSequence.drawSky(false);
		int[] hdOffFrame = capture();

		// The row on, but the map drew its own skybox.
		HdSettings.setEnabled(true, null);
		beginFrame();
		boolean stockSky = HdFrameSequence.drawSky(true);
		int[] stockSkyFrame = capture();

		long after = toolkit.hdSkyDraws();
		System.out.println("[hd-frame] off: row=" + rowOff + " hd=" + hdOff + " stockSky=" + stockSky
			+ ", draws " + before + " -> " + after);
		assertAll(
			() -> assertTrue(!rowOff, "the Sky row off must not draw"),
			() -> assertTrue(!hdOff, "592HD off must not draw, even with the row on"),
			() -> assertTrue(!stockSky, "a map's own skybox must keep its slot"),
			() -> assertEquals(before, after, "no attempt may reach the pass"),
			() -> assertArrayEquals(cleared, rowOffFrame, "row off must be the cleared frame"),
			() -> assertArrayEquals(cleared, hdOffFrame, "592HD off must be the cleared frame"),
			() -> assertArrayEquals(cleared, stockSkyFrame, "a stock skybox must be left alone"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("the dome is a background: geometry drawn after it covers it")
	void worldGeometry_shouldCoverTheDome() {
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.SKY, 1);
		beginFrame();
		boolean drawn = HdFrameSequence.drawSky(false);
		int[] dome = capture();
		// capture() leaves the default framebuffer bound; come back to the frame
		// the dome was drawn into before drawing over it.
		bindTarget();
		// Depth test on, a surface behind the dome's quad: if the dome had
		// written depth, this draw would be rejected where the dome covered it.
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor4ub((byte) 255, (byte) 0, (byte) 0, (byte) 255);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(-1.0F, -1.0F, 0.9F);
		GL11.glVertex3f(1.0F, -1.0F, 0.9F);
		GL11.glVertex3f(1.0F, 1.0F, 0.9F);
		GL11.glVertex3f(-1.0F, 1.0F, 0.9F);
		GL11.glEnd();
		int[] covered = capture();

		int centre = covered[SIZE * SIZE / 2 + SIZE / 2];
		System.out.println("[hd-frame] order: dome centre " + hex(dome[SIZE * SIZE / 2 + SIZE / 2])
			+ ", geometry over it " + hex(centre));
		assertAll(
			() -> assertTrue(drawn, "the dome must draw for the order to mean anything"),
			() -> assertEquals(0xFF0000, centre, "world geometry must cover the dome"),
			() -> assertNotEquals(0xFF0000, dome[SIZE * SIZE / 2 + SIZE / 2],
				"the dome must exist before the geometry"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("the frame-end slot runs the screen pass once, and only when a row is on")
	void postSlot_shouldRunBeforePresent() {
		HdSettings.setEnabled(true, null);
		// Every row of the pass, not just the one this test switches: a preset
		// may have left another on, and "off" must mean none of them.
		HdSettings.set(HdFeature.TONEMAP, 0);
		HdSettings.set(HdFeature.COLOUR_GRADING, 0);
		HdSettings.set(HdFeature.FXAA, 0);
		HdSettings.set(HdFeature.DITHER, 0);
		beginFrame();
		long before = toolkit.hdPostDraws();
		boolean off = toolkit.hdPostFrame();
		long afterOff = toolkit.hdPostDraws();

		HdSettings.set(HdFeature.DITHER, 1);
		beginFrame();
		boolean on = toolkit.hdPostFrame();
		long afterOn = toolkit.hdPostDraws();
		HdSettings.set(HdFeature.DITHER, 0);

		System.out.println("[hd-frame] post slot: off=" + off + " on=" + on + ", draws " + before
			+ " -> " + afterOff + " -> " + afterOn);
		assertAll(
			() -> assertTrue(!off, "no row on: the present must be the stock frame"),
			() -> assertEquals(before, afterOff, "no row on must apply nothing"),
			() -> assertTrue(on, "a row on must apply the pass to the finished frame"),
			() -> assertEquals(afterOff + 1, afterOn, "exactly one pass per presented frame"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	// -- Harness ----------------------------------------------------------

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
			EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT,
			depthBuffer);
		int status = EXTFramebufferObject.glCheckFramebufferStatusEXT(
			EXTFramebufferObject.GL_FRAMEBUFFER_EXT);
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		assertEquals(EXTFramebufferObject.GL_FRAMEBUFFER_COMPLETE_EXT, status,
			"offscreen target incomplete");
	}

	/** The world pass's own setup: the 3D viewport, bound and cleared magenta. */
	private static void bindTarget() {
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, framebuffer);
		GL11.glViewport(0, 0, SIZE, SIZE);
	}

	private static void beginFrame() {
		bindTarget();
		GL11.glDisable(GL11.GL_DITHER);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glClearColor(1.0F, 0.0F, 1.0F, 1.0F);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	/** The frame as packed {@code 0xRRGGBB}, and the default framebuffer restored. */
	private static int[] capture() {
		GL11.glFinish();
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

	private static String hex(int pixel) {
		return String.format("#%06X", pixel & 0xFFFFFF);
	}
}
