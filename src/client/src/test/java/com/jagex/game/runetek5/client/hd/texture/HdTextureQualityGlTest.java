package com.jagex.game.runetek5.client.hd.texture;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.nio.ByteBuffer;
import java.nio.file.Files;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import jaggl.GlTestContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**
 * Mipmaps and Anisotropic filtering, proved by pixels on the real driver.
 *
 * <p>The textures are raw GL objects and the configuration is
 * {@link HdTextureQuality}'s own helpers - the exact code the material cache
 * calls through {@code Class6} - so this measures the row's effect rather than
 * a test-local re-implementation:
 *
 * <ul>
 * <li>a one-texel 64x64 checkerboard drawn at 4:1 minification aliases into
 * black and white without the mip chain and collapses to a flat grey with it;
 * the standard deviation, not the mean, is the proof;</li>
 * <li>the same checker seen at a grazing angle keeps measurably more contrast
 * in the receding half with 16x anisotropy than with none.</li>
 * </ul>
 *
 * <p>That {@code Class6.hdApplyTextureQuality} routes the same two helpers is
 * covered by the gate test and by the read-back of the filter and anisotropy
 * parameters it produces.
 */
@DisplayName("HD texture quality on a real GL driver")
class HdTextureQualityGlTest {

	private static final int SIZE = 64;

	private static GlTestContext context;

	private static int framebuffer;

	private static int targetTexture;

	@BeforeAll
	static void openContext() throws Exception {
		try {
			context = GlTestContext.open();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}
		assumeTrue(context.supportsGlsl120(), "driver reports GLSL " + context.glslVersion());
		HdSettings.setDirectoryForTest(Files.createTempDirectory("hd-texture").toFile());
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.MIPMAPS, 1);
		HdSettings.set(HdFeature.ANISOTROPIC, 0);
		HdTextureQuality.resetForTest();
		createTarget();
		System.out.println("[hd-texture] " + context.renderer());
	}

	@AfterAll
	static void closeContext() {
		HdSettings.resetForTest();
		HdTextureQuality.resetForTest();
		if (context != null) {
			context.close();
		}
	}

	@Test
	@DisplayName("a minified checkerboard aliases without mipmaps and flattens with them")
	void mipmaps_shouldReplaceAliasingWithTheAverage() {
		int aliased = checkerboard(64, 1);
		int mipmapped = checkerboard(64, 1);

		// Nearest minification is the aliasing case: bilinear would average the
		// checker to grey even without a mip chain.
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, aliased);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mipmapped);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		assertTrue(HdTextureQuality.generateMipmaps(GL11.GL_TEXTURE_2D, 64, 64),
			"the row is on, so the chain is generated");

		int[] before = drawMinified(aliased, 20);
		int[] after = drawMinified(mipmapped, 20);

		double sdBefore = standardDeviation(before, 20, 20);
		double sdAfter = standardDeviation(after, 20, 20);
		double meanBefore = mean(before, 20, 20);
		double meanAfter = mean(after, 20, 20);
		System.out.println("[hd-texture] mipmaps sd " + round(sdBefore) + " -> " + round(sdAfter)
			+ ", mean " + round(meanBefore) + " -> " + round(meanAfter));
		assertAll(
			() -> assertTrue(sdBefore > 80.0, "without a mip chain the checker aliases: " + round(sdBefore)),
			() -> assertTrue(sdAfter < 10.0, "with the chain the minified texel is the average: " + round(sdAfter)),
			() -> assertTrue(Math.abs(meanBefore - meanAfter) < 12.0, "mipmaps change the detail, not the tone")
		);
	}

	@Test
	@DisplayName("16x anisotropy keeps more contrast at a grazing angle than none")
	void anisotropy_shouldSharpenTheRecedingHalf() {
		assumeTrue(HdTextureQuality.supportedSamples() > 1,
			"driver does not advertise GL_EXT_texture_filter_anisotropic");

		int texture = checkerboard(128, 8);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		HdSettings.set(HdFeature.ANISOTROPIC, 0);
		assertTrue(HdTextureQuality.generateMipmaps(GL11.GL_TEXTURE_2D, 128, 128));
		assertEquals(1, HdTextureQuality.applyAnisotropy(GL11.GL_TEXTURE_2D), "row off");
		int[] plain = drawGrazing(texture);

		HdSettings.set(HdFeature.ANISOTROPIC, 4);
		assertEquals(16, HdTextureQuality.applyAnisotropy(GL11.GL_TEXTURE_2D), "16x asked for");
		int[] filtered = drawGrazing(texture);

		double sdPlain = standardDeviationBand(plain, SIZE, SIZE / 2, SIZE - 1);
		double sdFiltered = standardDeviationBand(filtered, SIZE, SIZE / 2, SIZE - 1);
		System.out.println("[hd-texture] anisotropy contrast " + round(sdPlain) + " -> " + round(sdFiltered)
			+ " (samples " + HdTextureQuality.supportedSamples() + ")");
		assertAll(
			() -> assertTrue(sdPlain > 2.0, "the reduced plane still shows some detail: " + round(sdPlain)),
			() -> assertTrue(sdFiltered > sdPlain * 1.1,
				"anisotropy keeps the receding half sharper: " + round(sdFiltered) + " vs " + round(sdPlain))
		);
	}

	@Test
	@DisplayName("with both rows off the helpers set nothing")
	void rowsOff_shouldBeInert() {
		HdSettings.set(HdFeature.MIPMAPS, 0);
		HdSettings.set(HdFeature.ANISOTROPIC, 0);
		int texture = checkerboard(32, 1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

		boolean generated = HdTextureQuality.generateMipmaps(GL11.GL_TEXTURE_2D, 32, 32);
		int samples = HdTextureQuality.applyAnisotropy(GL11.GL_TEXTURE_2D);
		int minFilter = GL11.glGetTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER);
		assertAll(
			() -> assertFalse(generated, "no chain while the row is off"),
			() -> assertEquals(1, samples),
			() -> assertEquals(GL11.GL_LINEAR, minFilter, "the stock filter is untouched"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
		HdSettings.set(HdFeature.MIPMAPS, 1);
	}

	// -- Scene and texture helpers -----------------------------------------

	private static int checkerboard(int size, int cell) {
		byte[] pixels = new byte[size * size * 4];
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				boolean light = ((x / cell) + (y / cell)) % 2 == 0;
				int index = (y * size + x) * 4;
				pixels[index] = (byte) (light ? 255 : 0);
				pixels[index + 1] = (byte) (light ? 255 : 0);
				pixels[index + 2] = (byte) (light ? 255 : 0);
				pixels[index + 3] = (byte) 255;
			}
		}
		int[] names = new int[1];
		GL11.glGenTextures(names);
		int texture = names[0];
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		ByteBuffer data = BufferUtils.createByteBuffer(pixels.length);
		data.put(pixels).flip();
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, size, size, 0,
			GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);
		return texture;
	}

	private static int[] drawMinified(int texture, int pixels) {
		beginScene(pixels);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0.0F, 0.0F);
		GL11.glVertex2i(0, 0);
		GL11.glTexCoord2f(1.0F, 0.0F);
		GL11.glVertex2i(pixels, 0);
		GL11.glTexCoord2f(1.0F, 1.0F);
		GL11.glVertex2i(pixels, pixels);
		GL11.glTexCoord2f(0.0F, 1.0F);
		GL11.glVertex2i(0, pixels);
		GL11.glEnd();
		return read(pixels, pixels);
	}

	private static int[] drawGrazing(int texture) {
		beginScene(SIZE);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glFrustum(-0.5D, 0.5D, -0.5D, 0.5D, 1.0D, 100.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2.0F);
		GL11.glRotatef(70.0F, 1.0F, 0.0F, 0.0F);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		// Eight tile repeats along the receding axis: a strong anisotropic footprint.
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0.0F, 0.0F);
		GL11.glVertex3f(-2.0F, 0.0F, 0.0F);
		GL11.glTexCoord2f(8.0F, 0.0F);
		GL11.glVertex3f(2.0F, 0.0F, 0.0F);
		GL11.glTexCoord2f(8.0F, 8.0F);
		GL11.glVertex3f(2.0F, 0.0F, -40.0F);
		GL11.glTexCoord2f(0.0F, 8.0F);
		GL11.glVertex3f(-2.0F, 0.0F, -40.0F);
		GL11.glEnd();
		return read(SIZE, SIZE);
	}

	/** Clears the off-screen target and sets the matrices/state the quad needs. */
	private static void beginScene(int pixels) {
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, framebuffer);
		GL11.glViewport(0, 0, pixels, pixels);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glClearColor(0.5F, 0.0F, 0.0F, 1.0F);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, pixels, 0.0D, pixels, -1.0D, 1.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
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
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, framebuffer);
		EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
			EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, targetTexture, 0);
		int status = EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT);
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		assertEquals(EXTFramebufferObject.GL_FRAMEBUFFER_COMPLETE_EXT, status, "offscreen target incomplete");
	}

	private static int[] read(int width, int height) {
		GL11.glFinish();
		ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
		GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
		int[] values = new int[width * height * 4];
		for (int i = 0; i < values.length; i++) {
			values[i] = pixels.get(i) & 0xFF;
		}
		return values;
	}

	/** Standard deviation of the red channel over rows {@code from}..{@code to}. */
	private static double standardDeviationBand(int[] frame, int width, int from, int to) {
		double sum = 0.0;
		double squared = 0.0;
		int count = 0;
		for (int y = from; y <= to; y++) {
			for (int x = 0; x < width; x++) {
				double value = frame[(y * width + x) * 4];
				sum += value;
				squared += value * value;
				count++;
			}
		}
		double mean = sum / count;
		return Math.sqrt(Math.max(0.0, squared / count - mean * mean));
	}

	/** Standard deviation of the red channel over the first {@code rows} rows. */
	private static double standardDeviation(int[] frame, int width, int rows) {
		double mean = mean(frame, width, rows);
		double sum = 0.0;
		int count = width * rows;
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < width; x++) {
				double delta = frame[(y * width + x) * 4] - mean;
				sum += delta * delta;
			}
		}
		return Math.sqrt(sum / count);
	}

	private static double mean(int[] frame, int width, int rows) {
		double sum = 0.0;
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < width; x++) {
				sum += frame[(y * width + x) * 4];
			}
		}
		return sum / (width * rows);
	}

	private static String round(double value) {
		return String.format("%.2f", Double.valueOf(value));
	}
}
