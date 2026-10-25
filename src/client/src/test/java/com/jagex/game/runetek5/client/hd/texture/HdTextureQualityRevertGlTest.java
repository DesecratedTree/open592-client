package com.jagex.game.runetek5.client.hd.texture;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.nio.ByteBuffer;
import java.nio.file.Files;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
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
 * The two texture rows, proved to put the texture back when they are turned
 * off - through {@code Class6.hdApplyTextureQuality}, the method the material
 * cache calls, on a real driver.
 *
 * <p>GL keeps the min filter, the mip chain and the anisotropy maximum on the
 * texture object, so "stop writing them" is not "restore them". Each case here
 * is an on/off/on cycle whose evidence is the driver's own answer: the filter
 * parameter it reports, the anisotropy it reports, and what the texture looks
 * like when minified - the chain is being sampled or it is not. A texture the
 * stock path created with a chain is included as the control: the rows must
 * leave it alone, because that chain was never theirs.
 *	 * <p>Runs by default; {@link GlTestContext#open()} skips it with the reason
	 * when this machine cannot give a GL context.
 */
@DisplayName("592HD texture rows revert to stock")
class HdTextureQualityRevertGlTest {

	private static final int SIZE = 64;

	/** Minified draw size: 64 texels across 20 pixels, a 3.2:1 footprint. */
	private static final int QUAD = 20;

	/** {@code GL_TEXTURE_MIN_FILTER}. */
	private static final int MIN_FILTER = 10241;

	/** {@code GL_LINEAR}. */
	private static final int LINEAR = 9729;

	/** {@code GL_NEAREST}. */
	private static final int NEAREST = 9728;

	/** {@code GL_LINEAR_MIPMAP_LINEAR}. */
	private static final int LINEAR_MIPMAP_LINEAR = 9987;

	/** {@code GL_NEAREST_MIPMAP_NEAREST}. */
	private static final int NEAREST_MIPMAP_NEAREST = 9984;

	private static Frame frame;

	private static Canvas canvas;

	private static GlToolkit toolkit;

	private static int framebuffer;

	private static int targetTexture;

	@BeforeAll
	static void openToolkit() throws Exception {
		try {
			GlTestContext warmup = GlTestContext.open();
			warmup.close();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}

		frame = new Frame("592HD texture revert test");
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
		System.out.println("[hd-texture-revert] " + GL11.glGetString(GL11.GL_RENDERER) + " | GL "
			+ GL11.glGetString(GL11.GL_VERSION));

		createTarget();
		HdSettings.setDirectoryForTest(Files.createTempDirectory("hd-texture-revert").toFile());
		HdSettings.setEnabled(true, null);
		// The probe caches the extension answer per context, so forget it now
		// that this context exists.
		HdTextureQuality.resetForTest();
	}

	@AfterAll
	static void closeToolkit() {
		HdSettings.resetForTest();
		HdTextureQuality.resetForTest();
		if (frame != null) {
			frame.dispose();
		}
	}

	@Test
	@DisplayName("min filter and sampling return to stock when Mipmaps goes off")
	void mipmapsOff_shouldDropTheChain() {
		OpenGl2DTexture rowTexture = nearestTexture(false);
		OpenGl2DTexture stockTexture = nearestTexture(false);

		setRows(1, 0);
		rowTexture.hdApplyTextureQuality(SIZE, SIZE);
		int whileOn = minFilter(rowTexture);
		int[] mipmapped = drawMinified(rowTexture);

		setRows(0, 0);
		rowTexture.hdApplyTextureQuality(SIZE, SIZE);
		int afterOff = minFilter(rowTexture);
		int[] reverted = drawMinified(rowTexture);
		int[] stock = drawMinified(stockTexture);

		double mipmappedSpread = standardDeviation(mipmapped);
		double revertedSpread = standardDeviation(reverted);
		System.out.println("[hd-texture-revert] min filter " + whileOn + " -> " + afterOff
			+ ", alias spread " + round(mipmappedSpread) + " -> " + round(revertedSpread));

		assertAll(
			() -> assertEquals(NEAREST_MIPMAP_NEAREST, whileOn,
				"an on texture samples its chain with this texture's own filter"),
			() -> assertEquals(NEAREST, afterOff,
				"off restores the nearest filter this texture had before the row"),
			() -> assertTrue(mipmappedSpread < 8.0,
				"a mipmapped checkerboard is the average: " + round(mipmappedSpread)),
			() -> assertTrue(revertedSpread > 80.0,
				"without the chain the checkerboard aliases again: " + round(revertedSpread)),
			() -> assertArrayEquals(stock, reverted,
				"off is the pristine texture's pixels, byte for byte"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("turning Mipmaps back on builds the chain again")
	void mipmapsOnAgain_shouldRebuildTheChain() {
		OpenGl2DTexture texture = nearestTexture(false);

		setRows(1, 0);
		texture.hdApplyTextureQuality(SIZE, SIZE);
		int first = minFilter(texture);

		setRows(0, 0);
		texture.hdApplyTextureQuality(SIZE, SIZE);
		int off = minFilter(texture);

		setRows(1, 0);
		texture.hdApplyTextureQuality(SIZE, SIZE);
		int second = minFilter(texture);
		System.out.println("[hd-texture-revert] on/off/on min filter " + first + " / " + off + " / " + second);

		assertAll(
			() -> assertEquals(NEAREST_MIPMAP_NEAREST, first),
			() -> assertEquals(NEAREST, off),
			() -> assertEquals(NEAREST_MIPMAP_NEAREST, second, "the row is not one-shot"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("off with anisotropy still on drops the chain and keeps the samples")
	void mipmapsOffWithAnisotropyOn_shouldStillDropTheChain() {
		assumeTrue(HdTextureQuality.supportedSamples() > 1,
			"driver does not advertise GL_EXT_texture_filter_anisotropic");
		OpenGl2DTexture texture = nearestTexture(false);

		setRows(1, 4);
		texture.hdApplyTextureQuality(SIZE, SIZE);
		int withChain = minFilter(texture);

		setRows(0, 4);
		texture.hdApplyTextureQuality(SIZE, SIZE);
		int withoutChain = minFilter(texture);
		float samples = anisotropy(texture);
		System.out.println("[hd-texture-revert] chain " + withChain + " -> " + withoutChain
			+ " with " + samples + " samples");

		assertAll(
			() -> assertEquals(NEAREST_MIPMAP_NEAREST, withChain),
			() -> assertEquals(NEAREST, withoutChain,
				"the mipmap half of the row is reverted even while the other half is on"),
			() -> assertEquals(16.0F, samples,
				"the anisotropy half is untouched by the other row going off"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("anisotropy goes back to 1x when its row goes off, and back up again")
	void anisotropyOff_shouldReturnToOne() {
		assumeTrue(HdTextureQuality.supportedSamples() > 1,
			"driver does not advertise GL_EXT_texture_filter_anisotropic");
		OpenGl2DTexture texture = nearestTexture(false);

		setRows(1, 4);
		texture.hdApplyTextureQuality(SIZE, SIZE);
		float on = anisotropy(texture);

		setRows(1, 0);
		texture.hdApplyTextureQuality(SIZE, SIZE);
		float off = anisotropy(texture);

		setRows(1, 2);
		texture.hdApplyTextureQuality(SIZE, SIZE);
		float onAgain = anisotropy(texture);
		System.out.println("[hd-texture-revert] anisotropy " + on + " -> " + off + " -> " + onAgain);

		assertAll(
			() -> assertEquals(16.0F, on, "16x asked for"),
			() -> assertEquals(1.0F, off, "GL's default restored"),
			() -> assertEquals(4.0F, onAgain, "4x asked for the second time"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("a chain the stock path built is not the row's to drop")
	void stockChain_shouldSurviveTheRowGoingOff() {
		OpenGl2DTexture stockMipmapped = nearestTexture(true);
		int stockFilter = minFilter(stockMipmapped);

		setRows(1, 0);
		stockMipmapped.hdApplyTextureQuality(SIZE, SIZE);
		int whileOn = minFilter(stockMipmapped);

		setRows(0, 0);
		stockMipmapped.hdApplyTextureQuality(SIZE, SIZE);
		int afterOff = minFilter(stockMipmapped);
		System.out.println("[hd-texture-revert] stock chain " + stockFilter + " / " + whileOn + " / " + afterOff);

		assertAll(
			() -> assertEquals(NEAREST_MIPMAP_NEAREST, stockFilter, "the fixture starts mipmapped"),
			() -> assertEquals(NEAREST_MIPMAP_NEAREST, whileOn),
			() -> assertEquals(NEAREST_MIPMAP_NEAREST, afterOff,
				"off must not strip a chain the material already had"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	// -- Fixture -----------------------------------------------------------

	private static void setRows(int mipmaps, int anisotropic) {
		HdSettings.set(HdFeature.MIPMAPS, mipmaps);
		HdSettings.set(HdFeature.ANISOTROPIC, anisotropic);
	}

	/**
	 * A 64x64 one-texel checkerboard built the way the material cache builds
	 * one, filtered nearest so the chain's absence is visible as aliasing.
	 */
	private static OpenGl2DTexture nearestTexture(boolean stockMipmapped) {
		int[] pixels = new int[SIZE * SIZE];
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				boolean light = (x + y) % 2 == 0;
				pixels[y * SIZE + x] = light ? 0xFFFFFFFF : 0xFF000000;
			}
		}
		// Format 6408 is what the material cache passes; the toolkit's size table
		// only knows the stock internal formats.
		OpenGl2DTexture texture = new OpenGl2DTexture(toolkit, GL11.GL_TEXTURE_2D, 6408,
			SIZE, SIZE, stockMipmapped, pixels, false);
		// The stock filter toggle the material path uses; nearest makes a
		// sampled chain and an unsampled one look different.
		texture.method4181(false);
		return texture;
	}

	private static int minFilter(OpenGl2DTexture texture) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.method4186());
		return GL11.glGetTexParameteri(GL11.GL_TEXTURE_2D, MIN_FILTER);
	}

	private static float anisotropy(OpenGl2DTexture texture) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.method4186());
		return GL11.glGetTexParameterf(GL11.GL_TEXTURE_2D, HdTextureQuality.TEXTURE_MAX_ANISOTROPY);
	}

	private static int[] drawMinified(OpenGl2DTexture texture) {
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, framebuffer);
		GL11.glViewport(0, 0, QUAD, QUAD);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glClearColor(0.5F, 0.0F, 0.0F, 1.0F);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, QUAD, 0.0D, QUAD, -1.0D, 1.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.method4186());
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0.0F, 0.0F);
		GL11.glVertex2i(0, 0);
		GL11.glTexCoord2f(1.0F, 0.0F);
		GL11.glVertex2i(QUAD, 0);
		GL11.glTexCoord2f(1.0F, 1.0F);
		GL11.glVertex2i(QUAD, QUAD);
		GL11.glTexCoord2f(0.0F, 1.0F);
		GL11.glVertex2i(0, QUAD);
		GL11.glEnd();

		return read(QUAD, QUAD);
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

	/** Standard deviation of the red channel over the whole frame. */
	private static double standardDeviation(int[] frame) {
		double sum = 0.0;
		double squared = 0.0;
		int count = frame.length / 4;
		for (int i = 0; i < count; i++) {
			double value = frame[i * 4];
			sum += value;
			squared += value * value;
		}
		double mean = sum / count;
		return Math.sqrt(Math.max(0.0, squared / count - mean * mean));
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

	private static String round(double value) {
		return String.format("%.2f", Double.valueOf(value));
	}
}
