package com.jagex.game.runetek5.client.hd.post;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.nio.ByteBuffer;

import com.jagex.game.runetek5.client.hd.shader.JagglShaderApi;
import jaggl.GlTestContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**
 * The screen-space rows on a real driver, proved by pixels.
 *
 * <p>Every case draws a known frame, runs the pass over the back buffer and
 * reads it back, so each assertion names the number it expects rather than
 * "something changed":
 *
 * <ul>
 * <li>tonemap - a level below the knee is untouched and white lands on 0.8,
 * which is the shoulder and not a curve over the whole frame;</li>
 * <li>grading - a mid grey turns warm by a bounded, directional amount;</li>
 * <li>dither - at most one 8-bit step and an unchanged mean, so it breaks
 * banding without shifting the image;</li>
 * <li>FXAA - a hard diagonal edge gains intermediate pixels it did not
 * have;</li>
 * <li>off - byte-identical, and no GL state left behind.</li>
 * </ul>
 */
@DisplayName("HD post-processing on a real GL driver")
class HdPostRealGlTest {

	private static final int SIZE = 64;

	private static GlTestContext context;

	private static HdPostProcess pass;

	@BeforeAll
	static void openContext() {
		try {
			context = GlTestContext.open();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}
		assumeTrue(context.supportsGlsl120(), "driver reports GLSL " + context.glslVersion());
		pass = new HdPostProcess(new JagglShaderApi());
		assumeTrue(pass.prepare(), pass.logLine());
		System.out.println("[hd-post] " + context.renderer() + " / GLSL " + context.glslVersion());
	}

	@AfterAll
	static void closeContext() {
		if (pass != null) {
			pass.shutdown();
		}
		if (context != null) {
			context.close();
		}
	}

	@Test
	@DisplayName("every row off leaves the frame byte for byte")
	void off_shouldNotTouchTheFrame() {
		drawBands(0.2F, 1.0F);
		int[] before = read();
		boolean ran = pass.apply(0, 0, SIZE, SIZE, HdPostParams.OFF);
		int[] after = read();

		System.out.println("[hd-post] off ran=" + ran + " diff=" + differing(before, after));
		assertAll(
			() -> assertTrue(!ran, "an inert parameter set must not draw"),
			() -> assertEquals(0, differing(before, after))
		);
	}

	@Test
	@DisplayName("the tonemap is a shoulder: blacks and mids keep their levels")
	void tonemap_shouldOnlyCompressHighlights() {
		drawBands(0.2F, 1.0F);
		int[] before = read();
		pass.apply(0, 0, SIZE, SIZE, params(true, false, false, false));
		int[] after = read();

		int dark = pixel(after, 8, SIZE / 2)[0];
		int white = pixel(after, SIZE - 8, SIZE / 2)[0];
		System.out.println("[hd-post] tonemap dark " + pixel(before, 8, SIZE / 2)[0] + "->" + dark
			+ ", white " + pixel(before, SIZE - 8, SIZE / 2)[0] + "->" + white);
		assertAll(
			() -> assertEquals(51, pixel(before, 8, SIZE / 2)[0], "the dark band starts at 0.2"),
			() -> assertTrue(Math.abs(dark - 51) <= 1, "below the knee the curve is the identity: " + dark),
			// 1.0 above a 0.6 knee: 0.6 + 0.4 * 0.4 / 0.8 = 0.8 -> 204.
			() -> assertTrue(Math.abs(white - 204) <= 2, "white rolls off to 0.8: " + white)
		);
	}

	@Test
	@DisplayName("grading warms a mid grey without moving its brightness")
	void grading_shouldBeWarmAndBounded() {
		drawBands(128.0F / 255.0F, 128.0F / 255.0F);
		pass.apply(0, 0, SIZE, SIZE, params(false, true, false, false));
		int[] colour = pixel(read(), SIZE / 2, SIZE / 2);

		System.out.println("[hd-post] graded grey rgb(" + colour[0] + "," + colour[1] + "," + colour[2] + ")");
		assertAll(
			() -> assertTrue(colour[0] > colour[1] && colour[1] > colour[2],
				"the grade is warm: red over green over blue"),
			() -> assertTrue(colour[0] - colour[2] >= 8, "and the shift is visible: " + (colour[0] - colour[2])),
			() -> assertTrue(Math.abs(colour[1] - 128) <= 3, "green stays put: " + colour[1]),
			() -> assertTrue(colour[0] <= 140 && colour[2] >= 112,
				"a mid tone must stay a mid tone: rgb(" + colour[0] + "," + colour[1] + "," + colour[2] + ")")
		);
	}

	@Test
	@DisplayName("dither moves a level by at most one step and keeps the mean")
	void dither_shouldBeOneStepNoise() {
		drawBands(128.0F / 255.0F, 128.0F / 255.0F);
		int[] before = read();
		pass.apply(0, 0, SIZE, SIZE, params(false, false, true, false));
		int[] after = read();

		int worst = 0;
		long sum = 0L;
		for (int i = 0; i < after.length; i += 4) {
			int delta = Math.abs(after[i] - before[i]);
			if (delta > worst) {
				worst = delta;
			}
			sum += after[i];
		}
		double mean = sum / (double) (after.length / 4);
		final int worstStep = worst;
		final double meanLevel = mean;
		System.out.println("[hd-post] dither worst step " + worstStep + ", mean " + meanLevel);
		assertAll(
			() -> assertTrue(worstStep <= 1, "one 8-bit step: " + worstStep),
			() -> assertTrue(worstStep >= 1, "something actually moved"),
			() -> assertTrue(Math.abs(meanLevel - 128.0) <= 0.6, "the mean is unchanged: " + meanLevel)
		);
	}

	@Test
	@DisplayName("FXAA turns a hard diagonal edge into intermediate pixels")
	void fxaa_shouldSoftenAnEdge() {
		drawDiagonal();
		int before = intermediatePixels(read());
		pass.apply(0, 0, SIZE, SIZE, params(false, false, false, true));
		int after = intermediatePixels(read());

		System.out.println("[hd-post] edge intermediates " + before + " -> " + after);
		assertAll(
			() -> assertEquals(0, before, "the rendered edge is binary to start with"),
			// One blended pixel per edge row at the least; the diagonal is 64 rows.
			() -> assertTrue(after >= 32, "FXAA produced blended pixels along the edge: " + after)
		);
	}

	@Test
	@DisplayName("the pass leaves no GL state behind")
	void post_shouldNotLeakState() {
		// Drain anything an earlier case left queued, so this measures the pass.
		int earlier = GL11.glGetError();
		int[] before = captureState();
		pass.apply(0, 0, SIZE, SIZE, params(true, true, true, true));
		int[] after = captureState();
		int afterError = GL11.glGetError();

		System.out.println("[hd-post] caps before " + before[0] + " after " + after[0]
			+ "; program before " + before[3] + " after " + after[3]
			+ "; error earlier " + earlier + " after " + afterError);
		assertAll(
			() -> assertEquals(before[0], after[0], "enable flags"),
			() -> assertEquals(before[1], after[1], "active texture unit"),
			() -> assertEquals(before[2], after[2], "texture binding on the active unit"),
			() -> assertEquals(0, after[3], "returns to fixed function"),
			() -> assertEquals(GL11.GL_NO_ERROR, afterError, "the pass raised no GL error (earlier " + earlier + ")")
		);
	}

	// -- Scene helpers ----------------------------------------------------

	/** Two vertical bands of the given levels, so a level change has a reference. */
	private static void drawBands(float left, float right) {
		beginScene();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3f(left, left, left);
		GL11.glVertex2i(0, 0);
		GL11.glVertex2i(SIZE / 2, 0);
		GL11.glVertex2i(SIZE / 2, SIZE);
		GL11.glVertex2i(0, SIZE);
		GL11.glColor3f(right, right, right);
		GL11.glVertex2i(SIZE / 2, 0);
		GL11.glVertex2i(SIZE, 0);
		GL11.glVertex2i(SIZE, SIZE);
		GL11.glVertex2i(SIZE / 2, SIZE);
		GL11.glEnd();
	}

	/** A white triangle on black: one diagonal edge for FXAA to find. */
	private static void drawDiagonal() {
		beginScene();
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		GL11.glVertex2i(0, 0);
		GL11.glVertex2i(SIZE, 0);
		GL11.glVertex2i(0, SIZE);
		GL11.glEnd();
	}

	private static void beginScene() {
		GL11.glViewport(0, 0, SIZE, SIZE);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, SIZE, 0.0D, SIZE, -1.0D, 1.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	private static HdPostParams params(boolean tonemap, boolean grading, boolean dither, boolean fxaa) {
		return new HdPostParams(tonemap, grading, dither, fxaa, HdPostParams.EXPOSURE,
			HdPostParams.GAIN, HdPostParams.LIFT, HdPostParams.SATURATION, HdPostParams.DITHER_AMOUNT);
	}

	private static int[] read() {
		GL11.glFinish();
		ByteBuffer pixels = BufferUtils.createByteBuffer(SIZE * SIZE * 4);
		GL11.glReadPixels(0, 0, SIZE, SIZE, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
		int[] values = new int[SIZE * SIZE * 4];
		for (int i = 0; i < values.length; i++) {
			values[i] = pixels.get(i) & 0xFF;
		}
		return values;
	}

	private static int[] pixel(int[] frame, int x, int y) {
		int index = (y * SIZE + x) * 4;
		return new int[] { frame[index], frame[index + 1], frame[index + 2] };
	}

	private static int differing(int[] first, int[] second) {
		int count = 0;
		for (int i = 0; i < first.length; i++) {
			if (first[i] != second[i]) {
				count++;
			}
		}
		return count / 4;
	}

	/** Pixels anywhere in the frame that are neither of the two source levels. */
	private static int intermediatePixels(int[] frame) {
		int count = 0;
		for (int i = 0; i < frame.length; i += 4) {
			int value = frame[i];
			if (value > 20 && value < 235) {
				count++;
			}
		}
		return count;
	}

	private static int[] captureState() {
		int[] caps = { GL11.GL_LIGHTING, GL11.GL_TEXTURE_2D, GL11.GL_DEPTH_TEST, GL11.GL_BLEND,
			GL11.GL_ALPHA_TEST, GL11.GL_CULL_FACE, GL11.GL_FOG, GL11.GL_SCISSOR_TEST };
		int mask = 0;
		for (int i = 0; i < caps.length; i++) {
			if (GL11.glIsEnabled(caps[i])) {
				mask |= 1 << i;
			}
		}
		int active = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE);
		int texture = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
		int program = GL11.glGetInteger(0x8B8D);
		return new int[] { mask, active, texture, program };
	}
}
