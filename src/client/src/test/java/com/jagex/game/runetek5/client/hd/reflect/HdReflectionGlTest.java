package com.jagex.game.runetek5.client.hd.reflect;

import static org.junit.jupiter.api.Assertions.assertAll;
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

import com.jagex.game.runetek5.buffer.ArrayBufferGL;
import com.jagex.game.runetek5.buffer.ElementsBufferGL;
import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.client.hd.sway.HdVegetation;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
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
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;

/**
 * Reflections in pixels, through the production model draw: a quad submitted the
 * way {@code GlModel} submits one, with its material bound by
 * {@code GlToolkit.method1938} and the lit program asked for through
 * {@code hdBeginModelLighting}.
 *
 * <p>The two materials are the client's own vocabulary: one carries the stock
 * specular effect's slot ({@link HdReflection#SPECULAR_SLOT}), the other carries
 * no effect at all. That is what makes "only the reflective surface reflects"
 * a property of the material rather than of the test.
 *
 * <p>What this proves:
 *
 * <ul>
 * <li>the reflective surface's pixels follow a repainted environment cube, so
 * the draw really samples the client's cube and not a constant;</li>
 * <li>the plain material is pixel-identical across the same repaint, with the
 * row on and with it off;</li>
 * <li>with the row off, the reflective material keeps the stock specular pass -
 * the program is not bound - and two off frames are the same frame.</li>
 * </ul>
 *	 * <p>Runs by default; {@link GlTestContext#open()} skips it with the reason
	 * when this machine cannot give a GL context.
 */
@DisplayName("592HD reflections on the live model draw")
class HdReflectionGlTest {

	private static final int SIZE = 64;

	private static final int CUBE_SIZE = 16;

	/** The material whose effect slot is the stock specular one. */
	private static final int REFLECTIVE_MATERIAL = 1;

	/** A material with no texture effect: nothing about it says "reflect". */
	private static final int PLAIN_MATERIAL = 2;

	private static final float DEPTH = 100.0F;

	private static final int CHANNEL_STEP = 2;

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

	private static GlCubeTexture redCube;

	private static GlCubeTexture greenCube;

	private static TestEnvironment environment;

	@BeforeAll
	static void openToolkit() throws Exception {
		try {
			GlTestContext warmup = GlTestContext.open();
			warmup.close();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}

		frame = new Frame("592HD reflection test");
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
		System.out.println("[hd-reflect] toolkit " + GL11.glGetString(GL11.GL_RENDERER) + " | GL "
			+ GL11.glGetString(GL11.GL_VERSION));

		createTarget();
		createGeometry();
		redCube = cube(255, 0, 0);
		greenCube = cube(0, 255, 0);
		environment = new TestEnvironment(redCube);
		toolkit.setSunCubeTexture(environment);

		Path preferences = Files.createTempDirectory("hd-reflect");
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
		HdSettings.set(HdFeature.REFLECTIONS, 0);

		// What method1928 leaves behind before model geometry: GL_MODULATE and
		// normals present, so the lit program's preconditions hold.
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
	@DisplayName("the reflective surface follows the environment cube")
	void reflection_shouldFollowTheEnvironment() {
		environment.cube = redCube;
		Shot red = draw(REFLECTIVE_MATERIAL, true);
		environment.cube = greenCube;
		Shot green = draw(REFLECTIVE_MATERIAL, true);

		int[] lit = litMask(red.pixels);
		int changed = changed(red.pixels, green.pixels, lit);
		int[] redMean = mean(red.pixels, lit);
		int[] greenMean = mean(green.pixels, lit);

		System.out.println("[hd-reflect] red cube rgb(" + redMean[0] + "," + redMean[1] + "," + redMean[2]
			+ "), green cube rgb(" + greenMean[0] + "," + greenMean[1] + "," + greenMean[2]
			+ "), changed " + changed + " px, bound " + red.bound + "/" + green.bound
			+ ", uploads=" + toolkit.hdReflectionUploads());

		assertAll(
			() -> assertTrue(red.bound, "the lit program must own the reflective draw"),
			() -> assertTrue(green.bound, "the lit program must own the reflective draw"),
			() -> assertTrue(lit.length > 0, "the surface must be visible"),
			() -> assertTrue(changed > 100, "the repaint must reach the surface: " + changed),
			() -> assertTrue(redMean[0] > redMean[1] + 20,
				"a red cube must dominate the reflected surface: " + redMean[0] + " vs " + redMean[1]),
			() -> assertTrue(greenMean[1] > greenMean[0] + 20,
				"a green cube must dominate the reflected surface: " + greenMean[1] + " vs " + greenMean[0]),
			() -> assertTrue(toolkit.hdReflectionUploads() > 0L, "the seam must have uploaded a strength"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("a material with no reflective effect is unchanged, row on or off")
	void plainMaterial_shouldNeverReflect() {
		environment.cube = redCube;
		Shot redOn = draw(PLAIN_MATERIAL, true);
		Shot redOff = draw(PLAIN_MATERIAL, false);
		environment.cube = greenCube;
		Shot greenOn = draw(PLAIN_MATERIAL, true);

		System.out.println("[hd-reflect] plain: cube swap changes " + differing(redOn.pixels, greenOn.pixels)
			+ " px, row toggle changes " + differing(redOn.pixels, redOff.pixels) + " px");

		assertAll(
			() -> assertTrue(redOn.bound, "a plain material is a normal lit draw"),
			() -> assertEquals(0, differing(redOn.pixels, greenOn.pixels),
				"no effect slot, nothing to reflect"),
			() -> assertEquals(0, differing(redOn.pixels, redOff.pixels),
				"the row must be a no-op for this material")
		);
	}

	@Test
	@DisplayName("the row off keeps the stock specular pass and makes no upload")
	void rowOff_shouldLeaveTheStockPass() {
		environment.cube = redCube;
		long before = toolkit.hdReflectionUploads();
		Shot off = draw(REFLECTIVE_MATERIAL, false);
		Shot offAgain = draw(REFLECTIVE_MATERIAL, false);
		environment.cube = greenCube;
		Shot offGreen = draw(REFLECTIVE_MATERIAL, false);
		long uploads = toolkit.hdReflectionUploads() - before;

		Shot on = draw(REFLECTIVE_MATERIAL, true);

		System.out.println("[hd-reflect] row off: bound=" + off.bound + ", off frames differ "
			+ differing(off.pixels, offAgain.pixels) + " px, cube swap " + differing(off.pixels, offGreen.pixels)
			+ " px, uploads=" + uploads + "; row on bound=" + on.bound);

		assertAll(
			() -> assertFalse(off.bound, "with the row off the stock specular effect owns the draw"),
			() -> assertEquals(0, differing(off.pixels, offAgain.pixels), "two off frames are the same frame"),
			() -> assertEquals(0, differing(off.pixels, offGreen.pixels),
				"the stock pass reads its own ramp, not the environment cube"),
			() -> assertEquals(0L, uploads, "the row off must make no reflection upload"),
			() -> assertTrue(on.bound, "with the row on the lit program takes the draw"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	// -- Frames -----------------------------------------------------------

	private static Shot draw(int material, boolean reflections) {
		HdSettings.set(HdFeature.REFLECTIONS, reflections ? 1 : 0);
		beginFrame();
		uploadScene();
		// What GlModel calls first: the material bind, then the lit program.
		toolkit.method1938(material, true);
		boolean bound = toolkit.hdBeginModelLighting(true, HdVegetation.NONE);
		toolkit.method1934(colours, normals, null, vertices);
		toolkit.method1967(0, 6, elements);
		if (bound) {
			toolkit.hdEndModelLighting();
		}
		return new Shot(capture(), bound);
	}

	/**
	 * The scene's uploads, with a deliberately dim sun so the added reflection has
	 * headroom rather than clipping at white.
	 */
	private static void uploadScene() {
		toolkit.B(0.2F);
		toolkit.scaleHeight(0x404040, 1.0F, 0.0F, 0.0F, 0.6F, 0.8F);
		toolkit.UA(0xFF0000, 100, 3300);
		toolkit.method1918(false);
		toolkit.copyLights(0, new com.jagex.game.runetek5.cache.node.PointLight[0]);
		toolkit.method1900(1);
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

	private static int[] capture() {
		GL11.glFinish();
		assertEquals(GL11.GL_NO_ERROR, GL11.glGetError(), "GL error while drawing");
		ByteBuffer pixels = BufferUtils.createByteBuffer(SIZE * SIZE * 4);
		GL11.glReadPixels(0, 0, SIZE, SIZE, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
		int[] shot = new int[SIZE * SIZE];
		for (int index = 0; index < shot.length; index++) {
			shot[index] = (pixels.get(index * 4) & 0xFF) << 16
				| (pixels.get(index * 4 + 1) & 0xFF) << 8
				| (pixels.get(index * 4 + 2) & 0xFF);
		}
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		return shot;
	}

	/** The pixels the quad covers: anything that is not the blue clear. */
	private static int[] litMask(int[] shot) {
		int count = 0;
		for (int pixel : shot) {
			if (lit(pixel)) {
				count++;
			}
		}
		int[] mask = new int[count];
		int at = 0;
		for (int index = 0; index < shot.length; index++) {
			if (lit(shot[index])) {
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

	private static int[] mean(int[] shot, int[] mask) {
		long red = 0L;
		long green = 0L;
		long blue = 0L;
		for (int index : mask) {
			red += (shot[index] >> 16) & 0xFF;
			green += (shot[index] >> 8) & 0xFF;
			blue += shot[index] & 0xFF;
		}
		return new int[] { (int) (red / mask.length), (int) (green / mask.length), (int) (blue / mask.length) };
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

	/** One interleaved quad, laid out exactly as {@code GlModel} lays it out. */
	private static void createGeometry() {
		int stride = 28;
		float[][] positions = { { -8, -8, 0 }, { 8, -8, 0 }, { 8, 8, 0 }, { -8, 8, 0 } };
		ByteBuffer data = ByteBuffer.allocate(stride * positions.length).order(ByteOrder.LITTLE_ENDIAN);
		for (float[] position : positions) {
			data.putFloat(position[0]).putFloat(position[1]).putFloat(position[2]);
			data.put((byte) 255).put((byte) 255).put((byte) 255).put((byte) 255);
			// Facing the camera, so the reflection vector points back at the sky.
			data.putFloat(0.0F).putFloat(0.0F).putFloat(1.0F);
		}
		interleaved = new GlVboElementsArrayBuffer(toolkit, stride, data.array(), data.array().length, false);
		colours = new Class177(interleaved, GL11.GL_UNSIGNED_BYTE, 4, 12);
		normals = new Class177(interleaved, GL11.GL_FLOAT, 3, 16);
		vertices = new Class177(interleaved, GL11.GL_FLOAT, 3, 0);

		byte[] indices = { 0, 0, 1, 0, 2, 0, 0, 0, 2, 0, 3, 0 };
		elements = new GlVboArrayBuffer(toolkit, GL11.GL_UNSIGNED_SHORT, indices, indices.length, false);
	}

	/** Six solid faces of one colour: the sampled direction cannot be ambiguous. */
	private static GlCubeTexture cube(int red, int green, int blue) {
		byte[][] faces = new byte[6][CUBE_SIZE * CUBE_SIZE * 3];
		for (int face = 0; face < 6; face++) {
			for (int texel = 0; texel < CUBE_SIZE * CUBE_SIZE; texel++) {
				faces[face][texel * 3] = (byte) red;
				faces[face][texel * 3 + 1] = (byte) green;
				faces[face][texel * 3 + 2] = (byte) blue;
			}
		}
		GlCubeTexture cube = new GlCubeTexture(toolkit, GL11.GL_RGB, CUBE_SIZE, false, faces, GL11.GL_RGB);
		// The constructor leaves the cube bound on whichever unit is current.
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return cube;
	}

	/** One frame: its pixels and whether the lit program owned the draw. */
	private static final class Shot {

		private final int[] pixels;

		private final boolean bound;

		private Shot(int[] pixels, boolean bound) {
			this.pixels = pixels;
			this.bound = bound;
		}
	}

	/**
	 * The client's sun sampler reduced to the one thing the seam reads: the
	 * environment cube {@code GlToolkit.method1946()} hands out. Swapping
	 * {@link #cube} is exactly what a repainted sky does to a real session.
	 */
	private static final class TestEnvironment extends Class99_Sub1 {

		private GlCubeTexture cube;

		private TestEnvironment(GlCubeTexture cube) {
			this.cube = cube;
		}

		@Override
		public GlCubeTexture method3232() {
			return this.cube;
		}
	}

	/**
	 * Two materials with the stock effect slots the seam decides on: one
	 * reflective (slot 1, the specular pass), one with no effect at all. Both
	 * carry the MODULATE environment the lit program reproduces.
	 */
	private static final class TestMaterials implements unpack_Sub1 {

		private final MaterialType reflective = new MaterialType();

		private final MaterialType plain = new MaterialType();

		private TestMaterials() {
			this.reflective.aByte89 = (byte) HdReflection.SPECULAR_SLOT;
			// The specular pass selects its ramp by this byte; 1 is the first.
			this.reflective.aByte88 = 1;
			this.reflective.anInt6934 = HdReflection.MODULATE_ENVIRONMENT;
			this.plain.aByte89 = 0;
			this.plain.anInt6934 = HdReflection.MODULATE_ENVIRONMENT;
		}

		@Override
		public MaterialType getMetrics(int material) {
			if (material == REFLECTIVE_MATERIAL) {
				return this.reflective;
			}
			return material == PLAIN_MATERIAL ? this.plain : null;
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
