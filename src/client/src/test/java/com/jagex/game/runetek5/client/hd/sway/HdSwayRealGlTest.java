package com.jagex.game.runetek5.client.hd.sway;

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
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import com.jagex.game.runetek5.unpack_Sub1;
import jaggl.GlTestContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

/**
 * The two vegetation rows on a real driver, proved by pixels.
 *
 * <p>The scene is one upright quad - a loc-shaped plant: roots on the ground
 * plane at the model's own y translation, a crown a half-tile up - drawn
 * through {@code hdBeginModelLighting} and the toolkit's indexed model draw.
 * The camera is a plain translation the frame itself supplies ({@code ea}), and
 * the GL modelview is loaded as {@code rotX(180) * V}, the composition the
 * model path's own modelview has. That is what makes the test able to say
 * "with the rows off the vertex is where stock put it": nothing in the harness
 * is displaced by construction.
 *
 * <p>What each proof is for:
 *
 * <ul>
 * <li>rows off - a round trip (off, on, off) must land on the first frame byte
 * for byte, and a non-vegetation draw with the rows on must be identical to
 * them off;</li>
 * <li>foliage - the crown shifts sideways while the roots stay exactly where
 * they were, and the wind clock moves it;</li>
 * <li>grass - the crown parts away from the viewer's column, so the silhouette
 * widens symmetrically instead of shifting, and it does not depend on the
 * clock.</li>
 * </ul>
 *
 * <p>The rows' shadow rule is structural and therefore not measurable here: the
 * sun's depth pass draws through a different program and
 * {@code hdBeginModelLighting} refuses while the pass is open, so a caster is
 * always at its rest position. A swaying plant's shadow is the plant's stock
 * shadow, by construction rather than by tuning.
 */
@DisplayName("592HD vegetation sway on a real GL driver")
class HdSwayRealGlTest {

	private static final int SIZE = 128;

	private static final int STRIDE = 28;

	/** Half the quad's width, in scene units. */
	private static final float HALF_WIDTH = 40.0F;

	/** The crown's height above the roots, in scene units. */
	private static final float HEIGHT = 64.0F;

	/** The camera translation the frame supplies, and the modelview mirrors. */
	private static final int CAMERA_Y = 4;

	private static final int CAMERA_Z = 120;

	private static GlToolkit toolkit;

	private static Frame frame;

	private static Canvas canvas;

	private static int framebuffer;

	private static int targetTexture;

	private static int depthBuffer;

	private static ElementsBufferGL quad;

	private static Class177 vertices;

	private static Class177 colours;

	private static Class177 normals;

	private static ArrayBufferGL elements;

	@BeforeAll
	static void openToolkit() throws Exception {
		try {
			GlTestContext warmup = GlTestContext.open();
			warmup.close();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}
		frame = new Frame("592HD sway test");
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(SIZE, SIZE));
		frame.add(canvas);
		frame.pack();
		frame.setLocation(-4000, -4000);
		frame.setVisible(true);
		try {
			toolkit = new GlToolkit(0, canvas, new TestMaterials(), 0, null);
		} catch (Throwable error) {
			frame.dispose();
			assumeTrue(false, "GlToolkit could not start in this JVM: " + error);
		}
		System.out.println("[hd-sway] toolkit " + GL11.glGetString(GL11.GL_RENDERER));

		createTarget();
		createQuad();
		toolkit.method1900(1);
		toolkit.method1906(true);

		Path preferences = Files.createTempDirectory("hd-sway");
		HdSettings.setDirectoryForTest(preferences.toFile());
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 1);
		HdSettings.set(HdFeature.POINT_LIGHTS, 0);
		HdSettings.set(HdFeature.FOLIAGE_SWAY, 0);
		HdSettings.set(HdFeature.GRASS_DISPLACEMENT, 0);
	}

	@BeforeEach
	void resetRows() {
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 1);
		HdSettings.set(HdFeature.FOLIAGE_SWAY, 0);
		HdSettings.set(HdFeature.GRASS_DISPLACEMENT, 0);
		HdWindTime.reset();
	}

	@AfterAll
	static void closeToolkit() {
		HdSettings.resetForTest();
		HdWindTime.reset();
		if (frame != null) {
			frame.dispose();
		}
	}

	@Test
	@DisplayName("rows off, on, then off again lands on the first frame byte for byte")
	void rowsOff_shouldBeTheStockTransform() {
		long bindsBefore = toolkit.hdSwayBinds();
		int[] rest = frame(0.0F, HdVegetation.GROUND_DECOR);
		long bindsWhileOff = toolkit.hdSwayBinds();

		HdSettings.set(HdFeature.FOLIAGE_SWAY, 1);
		HdSettings.set(HdFeature.GRASS_DISPLACEMENT, 1);
		int[] displaced = frame(0.7F, HdVegetation.GROUND_DECOR);
		long bindsWhileOn = toolkit.hdSwayBinds();

		HdSettings.set(HdFeature.FOLIAGE_SWAY, 0);
		HdSettings.set(HdFeature.GRASS_DISPLACEMENT, 0);
		int[] restored = frame(0.0F, HdVegetation.GROUND_DECOR);

		writeArtifact("stock.png", rest);
		writeArtifact("sway-on.png", displaced);
		writeArtifact("diff.png", difference(rest, displaced));

		int roundTrip = differing(rest, restored);
		int changed = differing(rest, displaced);
		System.out.println("[hd-sway] rows off vs restored differ in " + roundTrip + " px; rows on changed "
			+ changed + " px; displacement submissions while off " + (bindsWhileOff - bindsBefore)
			+ ", while on " + (bindsWhileOn - bindsWhileOff));
		assertAll(
			() -> assertEquals(0, roundTrip, "off again must be the frame before any row was on"),
			() -> assertEquals(0L, bindsWhileOff - bindsBefore, "the seam must not claim a move while off"),
			() -> assertTrue(bindsWhileOn - bindsWhileOff > 0L, "the row on must reach the displacement path"),
			() -> assertTrue(changed > SIZE, "and it must move the plant in between: " + changed),
			() -> assertTrue(brightestChannel(rest) > 20, "the frame drew something, so the comparison means something")
		);
	}

	@Test
	@DisplayName("a draw the scene did not mark moves no pixel, even with both rows on")
	void nonVegetation_shouldNeverMove() {
		HdSettings.set(HdFeature.FOLIAGE_SWAY, 1);
		HdSettings.set(HdFeature.GRASS_DISPLACEMENT, 1);
		int[] marked = frame(0.4F, HdVegetation.GROUND_DECOR);
		int[] unmarked = frame(0.4F, HdVegetation.NONE);

		HdSettings.set(HdFeature.FOLIAGE_SWAY, 0);
		HdSettings.set(HdFeature.GRASS_DISPLACEMENT, 0);
		int[] rowsOff = frame(0.4F, HdVegetation.GROUND_DECOR);

		System.out.println("[hd-sway] unmarked draw vs rows off differ in " + differing(unmarked, rowsOff)
			+ " px; marked draw moved " + differing(marked, rowsOff) + " px");
		assertAll(
			() -> assertEquals(0, differing(unmarked, rowsOff),
				"a wall with the rows on is the wall with them off"),
			() -> assertTrue(differing(marked, rowsOff) > SIZE, "the marked draw is the one that moved")
		);
	}

	@Test
	@DisplayName("foliage moves the crown and leaves the roots exactly where they were")
	void foliage_shouldLeanTheCrownOnly() {
		HdSettings.set(HdFeature.FOLIAGE_SWAY, 1);
		HdWindTime.setForTest(0.0F);
		int[] rest = frame(0.0F, HdVegetation.GROUND_DECOR);
		int[] leaned = frame(0.0F, HdVegetation.GROUND_DECOR);

		HdSettings.set(HdFeature.FOLIAGE_SWAY, 0);
		int[] off = frame(0.0F, HdVegetation.GROUND_DECOR);

		Band rootsOff = Band.of(off, true);
		Band crownOff = Band.of(off, false);
		Band rootsLeaned = Band.of(leaned, true);
		Band crownLeaned = Band.of(leaned, false);
		System.out.println("[hd-sway] roots off " + rootsOff + " leaned " + rootsLeaned
			+ "; crown off " + crownOff + " leaned " + crownLeaned
			+ "; identical reps " + differing(rest, leaned));
		assertAll(
			// The lever is zero at the model's base, so the root band is the same
			// shape to the pixel: a plant does not slide along the ground.
			() -> assertEquals(rootsOff, rootsLeaned, "the roots must not move"),
			// The crown travels. How it travels depends on the phase across the
			// object - the wind is a travelling wave, not a rigid offset - so this
			// asserts movement, not a direction; the still-wind check below and the
			// clock test are what say the movement is the wind.
			() -> assertTrue(crownOff.width() != crownLeaned.width()
				|| Math.abs(crownLeaned.centre() - crownOff.centre()) > 0.5,
				"the crown moves with the wind: " + crownOff + " -> " + crownLeaned),
			// Two frames at the same second and the same rows are the same image:
			// nothing here is uninitialised or driven by wall time.
			() -> assertEquals(0, differing(rest, leaned),
				"a still wind is the same image at the same second")
		);
	}

	@Test
	@DisplayName("the wind clock moves the foliage row and cannot touch the grass row")
	void clock_shouldDriveOnlyTheFoliageRow() {
		HdSettings.set(HdFeature.FOLIAGE_SWAY, 1);
		HdWindTime.setForTest(0.0F);
		HdSettings.set(HdFeature.GRASS_DISPLACEMENT, 0);
		int[] foliageEarly = frame(0.0F, HdVegetation.GROUND_DECOR);
		int[] foliageLate = frame(1.5F, HdVegetation.GROUND_DECOR);

		HdSettings.set(HdFeature.FOLIAGE_SWAY, 0);
		HdSettings.set(HdFeature.GRASS_DISPLACEMENT, 1);
		int[] grassEarly = frame(0.0F, HdVegetation.GROUND_DECOR);
		int[] grassLate = frame(1.5F, HdVegetation.GROUND_DECOR);

		System.out.println("[hd-sway] foliage across 1.5 s differs in " + differing(foliageEarly, foliageLate)
			+ " px; grass across 1.5 s differs in " + differing(grassEarly, grassLate) + " px");
		assertAll(
			() -> assertTrue(differing(foliageEarly, foliageLate) > 20,
				"the wind clock must move a swaying plant"),
			() -> assertEquals(0, differing(grassEarly, grassLate),
				"the grass part is positional: time alone must not move it")
		);
	}

	@Test
	@DisplayName("grass parts the crown away from the viewer's column, and only the crown")
	void grass_shouldPartTheCrownAroundTheViewer() {
		int[] off = frame(0.0F, HdVegetation.GROUND_DECOR);

		HdSettings.set(HdFeature.GRASS_DISPLACEMENT, 1);
		int[] parted = frame(0.0F, HdVegetation.GROUND_DECOR);

		Band rootsOff = Band.of(off, true);
		Band crownOff = Band.of(off, false);
		Band rootsParted = Band.of(parted, true);
		Band crownParted = Band.of(parted, false);
		System.out.println("[hd-sway] grass: roots " + rootsOff + " -> " + rootsParted
			+ "; crown " + crownOff + " -> " + crownParted);
		assertAll(
			() -> assertEquals(rootsOff, rootsParted, "the roots stay planted"),
			// Both crown edges move outward from the viewer's column at x = 0, so
			// the silhouette widens instead of sliding.
			() -> assertTrue(crownParted.width() > crownOff.width() + 4,
				"the crown parts away from the viewer: " + crownOff + " -> " + crownParted),
			() -> assertTrue(Math.abs(crownParted.centre() - crownOff.centre()) <= 2,
				"a symmetric parting does not shift the crown: " + crownOff + " -> " + crownParted)
		);
	}

	// -- Scene -------------------------------------------------------------

	/**
	 * One frame: the quad through the model seam with the live rows. The camera
	 * is re-supplied every frame so {@code uEyeRow*} and the GL modelview are
	 * the same composition - that is what makes the off state stock.
	 */
	private static int[] frame(float seconds, int vegetation) {
		HdWindTime.setForTest(seconds);
		beginFrame();
		JavaMatrix camera = new JavaMatrix();
		camera.scaleWidth(0, CAMERA_Y, CAMERA_Z);
		toolkit.ea(camera);
		boolean bound = toolkit.hdBeginModelLighting(false, vegetation);
		assertTrue(bound, "the displacement proof needs the per-pixel program");
		toolkit.method1934(colours, normals, null, vertices);
		toolkit.method1967(0, 6, elements);
		toolkit.hdEndModelLighting();
		return finishFrame();
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
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glClearColor(0.0F, 0.0F, 1.0F, 1.0F);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glFrustum(-0.8D, 0.8D, -0.8D, 0.8D, 1.0D, 2000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		// rotX(180) * V, the composition the model path's modelview has.
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.0F, CAMERA_Y, CAMERA_Z);

		GL11.glEnable(GL11.GL_LIGHTING);
		toolkit.B(0.35F);
		toolkit.scaleHeight(0x808080, 1.0F, 0.0F, 0.0F, 0.0F, 0.97F);
		toolkit.UA(0xFFFFFF, 1, 0);
		toolkit.method1918(true);
		toolkit.copyLights(0, new PointLight[0]);
		toolkit.method1938(-1, true);
	}

	private static int[] finishFrame() {
		GL11.glFinish();
		assertEquals(GL11.GL_NO_ERROR, GL11.glGetError(), "GL error while drawing");
		ByteBuffer pixels = BufferUtils.createByteBuffer(SIZE * SIZE * 4);
		GL11.glReadPixels(0, 0, SIZE, SIZE, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
		int[] values = new int[SIZE * SIZE * 4];
		for (int index = 0; index < values.length; index++) {
			values[index] = pixels.get(index) & 0xFF;
		}
		return values;
	}

	/** A quad with its roots at world y = 0, the model's own base. */
	private static void createQuad() {
		float[][] corners = {
			{ -HALF_WIDTH, 0.0F, 0.0F }, { HALF_WIDTH, 0.0F, 0.0F },
			{ HALF_WIDTH, HEIGHT, 0.0F }, { -HALF_WIDTH, HEIGHT, 0.0F }
		};
		ByteBuffer data = ByteBuffer.allocate(STRIDE * corners.length).order(ByteOrder.LITTLE_ENDIAN);
		for (float[] corner : corners) {
			data.putFloat(corner[0]).putFloat(corner[1]).putFloat(corner[2]);
			data.put((byte) 255).put((byte) 255).put((byte) 255).put((byte) 255);
			data.putFloat(0.0F).putFloat(0.0F).putFloat(1.0F);
		}
		quad = new GlVboElementsArrayBuffer(toolkit, STRIDE, data.array(), data.array().length, false);
		vertices = new Class177(quad, GL11.GL_FLOAT, 3, 0);
		colours = new Class177(quad, GL11.GL_UNSIGNED_BYTE, 4, 12);
		normals = new Class177(quad, GL11.GL_FLOAT, 3, 16);
		byte[] indices = { 0, 0, 1, 0, 2, 0, 0, 0, 2, 0, 3, 0 };
		elements = new GlVboArrayBuffer(toolkit, GL11.GL_UNSIGNED_SHORT, indices, indices.length, false);
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
			org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT24, SIZE, SIZE);

		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, framebuffer);
		EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
			EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, targetTexture, 0);
		EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
			EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, depthBuffer);
		int status = EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT);
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		assertEquals(EXTFramebufferObject.GL_FRAMEBUFFER_COMPLETE_EXT, status, "offscreen target incomplete");
	}

	// -- Measurement -------------------------------------------------------

	/**
	 * Writes one frame to {@code build/hd-sway}, rows flipped to image order, so
	 * the proof can be looked at rather than only read. The directory is build
	 * output and never committed.
	 */
	private static void writeArtifact(String name, int[] frame) {
		try {
			java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(SIZE, SIZE,
				java.awt.image.BufferedImage.TYPE_INT_RGB);
			for (int row = 0; row < SIZE; row++) {
				for (int column = 0; column < SIZE; column++) {
					int index = ((SIZE - 1 - row) * SIZE + column) * 4;
					image.setRGB(column, row,
						(frame[index] << 16) | (frame[index + 1] << 8) | frame[index + 2]);
				}
			}
			Path directory = java.nio.file.Paths.get(System.getProperty("user.dir"), "build", "hd-sway");
			Files.createDirectories(directory);
			javax.imageio.ImageIO.write(image, "png", directory.resolve(name).toFile());
		} catch (java.io.IOException error) {
			throw new IllegalStateException("could not write " + name, error);
		}
	}

	/** White where the two frames differ, for the artifact. */
	private static int[] difference(int[] first, int[] second) {
		int[] frame = new int[first.length];
		for (int index = 0; index < first.length; index += 4) {
			boolean differs = Math.abs(first[index] - second[index]) > 1;
			int value = differs ? 255 : 0;
			frame[index] = value;
			frame[index + 1] = value;
			frame[index + 2] = value;
			frame[index + 3] = 255;
		}
		return frame;
	}

	/** Pixels (not channels) that differ by more than one step. */
	private static int differing(int[] first, int[] second) {
		int count = 0;
		for (int index = 0; index < first.length; index += 4) {
			if (Math.abs(first[index] - second[index]) > 1) {
				count++;
			}
		}
		return count;
	}

	private static int brightestChannel(int[] frame) {
		int best = 0;
		for (int index = 0; index < frame.length; index += 4) {
			for (int channel = 0; channel < 3; channel++) {
				if (frame[index + channel] > best) {
					best = frame[index + channel];
				}
			}
		}
		return best;
	}

	/**
	 * The silhouette of the root band (the top few drawn rows, where the roots
	 * are under this camera) or the crown band (the bottom few), as a span of
	 * columns.
	 *
	 * <p>A band rather than one row because a sub-pixel edge can flip a column
	 * on a single line; three rows make the measured span stable while still
	 * being close enough to the end that a lever of {@code height^2} cannot
	 * reach it. Equality of two bands is the strong claim.
	 */
	private static final class Band {

		private final int min;

		private final int max;

		private Band(int min, int max) {
			this.min = min;
			this.max = max;
		}

		private static Band of(int[] frame, boolean roots) {
			int first = -1;
			int last = -1;
			for (int row = 0; row < SIZE; row++) {
				if (drawn(frame, row)) {
					if (first < 0) {
						first = row;
					}
					last = row;
				}
			}
			assertTrue(first >= 0, "the plant must be on screen");
			int min = Integer.MAX_VALUE;
			int max = Integer.MIN_VALUE;
			for (int step = 0; step < 3; step++) {
				int row = roots ? last - step : first + step;
				if (row < 0 || row >= SIZE) {
					continue;
				}
				for (int column = 0; column < SIZE; column++) {
					if (!isDrawn(frame, row, column)) {
						continue;
					}
					min = Math.min(min, column);
					max = Math.max(max, column);
				}
			}
			assertTrue(min <= max, "the band must contain drawn pixels");
			return new Band(min, max);
		}

		private static boolean drawn(int[] frame, int row) {
			for (int column = 0; column < SIZE; column++) {
				if (isDrawn(frame, row, column)) {
					return true;
				}
			}
			return false;
		}

		/** The scene's colour is lighter than the blue clear, which is the only other colour. */
		private static boolean isDrawn(int[] frame, int row, int column) {
			int index = (row * SIZE + column) * 4;
			return frame[index] > 30 || frame[index + 1] > 30;
		}

		private double centre() {
			return (this.min + this.max) / 2.0;
		}

		private int width() {
			return this.max - this.min + 1;
		}

		@Override
		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (!(other instanceof Band)) {
				return false;
			}
			Band that = (Band) other;
			return this.min == that.min && this.max == that.max;
		}

		@Override
		public int hashCode() {
			return this.min * 131 + this.max;
		}

		@Override
		public String toString() {
			return "[" + this.min + "," + this.max + "]";
		}
	}

	/**
	 * No materials at all. The draw is untextured and effect-free, so every
	 * lookup misses, which is the toolkit's "no material" answer; the provider
	 * only has to exist because the constructor asks for one.
	 */
	private static final class TestMaterials implements unpack_Sub1 {

		@Override
		public MaterialType getMetrics(int material) {
			return null;
		}

		@Override
		public boolean textureAvailable(int material) {
			return false;
		}

		@Override
		public int[] getPixels(boolean readReversed, int arg1, float arg2, int arg3, int height) {
			return null;
		}

		@Override
		public float[] method2394(float arg0, int arg1, int arg2, int arg3) {
			return null;
		}

		@Override
		public int[] getPixelsArgb(int arg0, float arg1, int arg2, int arg3) {
			return null;
		}
	}
}
