package com.jagex.game.runetek5.client.hd.content;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import com.jagex.game.runetek5.client.hd.content.HdMaterialDefinition;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.unpack_Sub1;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import jaggl.GlTestContext;
import jaggl.OpenGL;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

/**
 * The shipped replacement art in pixels, through the seam the stock texture
 * cache and the world page both call.
 *
 * <p>A texture replacement is a stock upload whose texels are the authored
 * albedo's colour over the game texture's own alpha
 * ({@code HdTextureReplacements.combine}). The proof draws the texels
 * {@code applied(material, size, stock)} returns and reads the centre pixel in
 * {@code GL_REPLACE}, so the fragment colour is the sampled texel exactly - no
 * light, no fog and no shader arithmetic between the upload and the reading.
 *
 * <p>Three claims, each of which fails if a piece is missing:
 *
 * <ul>
 * <li><b>Off is the game's texture.</b> A draw of the stock array alone and a
 * draw of {@code applied} with the row off are the same pixel, and the unit
 * test proves the latter is the same array by reference.</li>
 * <li><b>On is the authored art.</b> With the row on the pixel is the art's own
 * texel colour at that spot, taken from the decode, not a blend of it.</li>
 * <li><b>The masks survive.</b> The pixel's alpha is the game texture's, so a
 * cut-out is still a cut-out after the replacement.</li>
 * </ul>
 */
@DisplayName("592HD texture replacements on a real GL driver")
class HdTextureReplacementGlTest {

	private static final int SIZE = 64;

	/** The stock texture's size, and the art's decode size. */
	private static final int TEXELS = 64;

	private static final float DEPTH = 300.0F;

	private static final int GL_FRAMEBUFFER_EXT = 0x8D40;

	/** The shipped material whose art this proof draws; chosen by {@link #chooseTexel()}. */
	private static int material = -1;

	/** The game texture's colour: a flat grey the art is not. */
	private static final int STOCK = 0x803C3C3C;

	private static GlToolkit toolkit;

	private static Frame frame;

	private static Canvas canvas;

	private static int framebuffer;

	private static int targetTexture;

	private static int depthBuffer;

	private static HdContent content;

	@BeforeAll
	static void openToolkit() throws Exception {
		try {
			GlTestContext warmup = GlTestContext.open();
			warmup.close();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}

		frame = new Frame("592HD texture replacements test");
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
		System.out.println("[hd-replace] toolkit " + GL11.glGetString(GL11.GL_RENDERER) + " | GL "
			+ GL11.glGetString(GL11.GL_VERSION));
		createTarget();

		Path preferences = Files.createTempDirectory("hd-replace");
		HdSettings.setDirectoryForTest(preferences.toFile());
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.TEXTURE_REPLACEMENTS, 0);
		content = HdContent.loadDefault();
	}

	@AfterAll
	static void closeToolkit() {
		HdSettings.resetForTest();
		if (frame != null) {
			frame.dispose();
		}
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

	/** The art at the stock size, and the texel this proof reads. */
	private static int[] art;

	private static int artX = -1;

	private static int artY = -1;

	private static void chooseTexel() {
		int bestDelta = 0;
		for (int i = 0; i < content.materials().size(); i++) {
			HdMaterialDefinition definition = content.materials().get(i);
			if (!definition.hasAlbedo()) {
				continue;
			}
			int[] candidate = HdTextureReplacements.art(content, definition.texture(), TEXELS);
			if (candidate == null) {
				continue;
			}
			for (int y = 0; y < TEXELS; y++) {
				for (int x = 0; x < TEXELS; x++) {
					int delta = delta(candidate[y * TEXELS + x], STOCK);
					if (delta > bestDelta) {
						bestDelta = delta;
						material = definition.texture();
						art = candidate;
						artX = x;
						artY = y;
					}
				}
			}
		}
		assumeTrue(art != null && bestDelta >= 16, "no shipped art differs from the stock grey");
		System.out.println("[hd-replace] material " + material + " texel " + artX + "," + artY + " art "
			+ hex(art[artY * TEXELS + artX]) + " vs stock " + hex(STOCK) + " (delta " + bestDelta + ")");
	}

	/** The largest channel difference between an art texel and the stock colour. */
	private static int delta(int texel, int stock) {
		int worst = 0;
		for (int shift = 0; shift < 24; shift += 8) {
			int difference = Math.abs(((texel >>> shift) & 0xFF) - ((stock >>> shift) & 0xFF));
			if (difference > worst) {
				worst = difference;
			}
		}
		return worst;
	}

	private static String hex(int argb) {
		return String.format("0x%08X", Integer.valueOf(argb));
	}

	private static int[] stock() {
		int[] texels = new int[TEXELS * TEXELS];
		java.util.Arrays.fill(texels, STOCK);
		return texels;
	}

	private static void ensureTexel() {
		if (artX < 0) {
			chooseTexel();
		}
	}

	/**
	 * Draws one textured quad that fills the viewport, its uv window inside a
	 * single texel, and reads the centre pixel.
	 *
	 * <p>The texture is built through the production upload seam and bound the way
	 * {@code GlModel} binds a group's stock texture - after the no-material stock
	 * bind, on unit 0. {@code GL_REPLACE} makes the fragment colour the sampled
	 * texel, so an exact comparison compares texels, not shading.
	 */
	private static Pixel draw(int[] texels) {
		return drawTexture(toolkit.hdCreateTexture(TEXELS, TEXELS, texels));
	}

	/**
	 * The same draw for a texture the stock texture cache built, so the proof can
	 * measure the cache's own upload instead of this harness's.
	 */
	private static Pixel drawTexture(OpenGl2DTexture texture) {
		float half = 0.3F / TEXELS;
		float centreU = (artX + 0.5F) / TEXELS;
		float centreV = (artY + 0.5F) / TEXELS;
		int stride = 36;
		float[][] positions = { { -30, -30, 0 }, { 30, -30, 0 }, { 30, 30, 0 }, { -30, 30, 0 } };
		float[][] uvs = { { centreU - half, centreV - half }, { centreU + half, centreV - half },
			{ centreU + half, centreV + half }, { centreU - half, centreV + half } };
		ByteBuffer data = ByteBuffer.allocate(stride * positions.length).order(ByteOrder.LITTLE_ENDIAN);
		for (int i = 0; i < positions.length; i++) {
			data.putFloat(positions[i][0]).putFloat(positions[i][1]).putFloat(positions[i][2]);
			data.put((byte) 255).put((byte) 255).put((byte) 255).put((byte) 255);
			data.putFloat(0.0F).putFloat(0.0F).putFloat(1.0F);
			data.putFloat(uvs[i][0]).putFloat(uvs[i][1]);
		}
		ElementsBufferGL interleaved = new GlVboElementsArrayBuffer(toolkit, stride, data.array(),
			data.array().length, false);
		Class177 colours = new Class177(interleaved, GL11.GL_UNSIGNED_BYTE, 4, 12);
		Class177 normals = new Class177(interleaved, GL11.GL_FLOAT, 3, 16);
		Class177 texCoords = new Class177(interleaved, GL11.GL_FLOAT, 2, 28);
		Class177 vertices = new Class177(interleaved, GL11.GL_FLOAT, 3, 0);
		byte[] indices = { 0, 0, 1, 0, 2, 0, 0, 0, 2, 0, 3, 0 };
		ArrayBufferGL elements = new GlVboArrayBuffer(toolkit, GL11.GL_UNSIGNED_SHORT, indices, indices.length,
			false);

		OpenGL.glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, framebuffer);
		GL11.glViewport(0, 0, SIZE, SIZE);
		GL11.glDisable(GL11.GL_DITHER);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glClearColor(0.0F, 0.0F, 1.0F, 1.0F);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glFrustum(-0.1D, 0.1D, -0.1D, 0.1D, 1.0D, 400.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -DEPTH);

		toolkit.method1900(1);
		toolkit.method1938(-1, true);
		toolkit.setActiveTexture(texture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		toolkit.method1934(colours, normals, texCoords, vertices);
		toolkit.method1967(0, 6, elements);
		GL11.glFinish();
		assertEquals(GL11.GL_NO_ERROR, GL11.glGetError(), "GL error while drawing");

		ByteBuffer pixel = BufferUtils.createByteBuffer(4);
		GL11.glReadPixels(SIZE / 2, SIZE / 2, 1, 1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixel);
		Pixel result = new Pixel(pixel.get(0) & 0xFF, pixel.get(1) & 0xFF, pixel.get(2) & 0xFF,
			pixel.get(3) & 0xFF);
		OpenGL.glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		return result;
	}

	@Test
	@DisplayName("the row off draws the game's own texels")
	void rowOff_drawsTheStockTexture() {
		ensureTexel();
		HdSettings.set(HdFeature.TEXTURE_REPLACEMENTS, 0);
		Pixel plain = draw(stock());
		Pixel viaSeam = draw(HdTextureReplacements.applied(material, TEXELS, stock()));

		System.out.println("[hd-replace] row off: plain " + plain + ", applied " + viaSeam);
		assertAll(
			() -> assertEquals("rgb(60,60,60)", plain.rgb(), "the stock texel is what the draw shows"),
			() -> assertEquals(plain, viaSeam, "the row off must draw exactly the stock texels"),
			() -> assertEquals(128, viaSeam.alpha, "the game texture's alpha survives")
		);
	}

	@Test
	@DisplayName("the row on draws the authored art over the game's mask")
	void rowOn_drawsTheReplacementArt() {
		ensureTexel();
		HdSettings.set(HdFeature.TEXTURE_REPLACEMENTS, 0);
		Pixel plain = draw(stock());
		HdSettings.set(HdFeature.TEXTURE_REPLACEMENTS, 1);
		Pixel replaced = draw(HdTextureReplacements.applied(material, TEXELS, stock()));

		int expected = art[artY * TEXELS + artX];
		Pixel painted = new Pixel((expected >> 16) & 0xFF, (expected >> 8) & 0xFF, expected & 0xFF, 128);
		System.out.println("[hd-replace] row on: stock " + plain + ", replaced " + replaced + ", art " + painted);
		assertAll(
			() -> assertNotEquals(plain, replaced, "the row must change the pixel"),
			() -> assertEquals(painted, replaced, "the replacement art is what the draw samples"),
			() -> assertEquals(128, replaced.alpha, "the replacement keeps the game texture's alpha")
		);
	}

	@Test
	@DisplayName("an RGB material shows the authored art through the cache's own upload")
	void rgbMaterial_shouldShowTheReplacementArt() {
		ensureTexel();

		// Row off, then row on, each through a fresh cache: the cache keys on the
		// material, so a second call on the same instance would hand back the
		// first upload instead of uploading again.
		HdSettings.set(HdFeature.TEXTURE_REPLACEMENTS, 0);
		RgbMaterialSource stockSource = new RgbMaterialSource();
		Pixel plain = drawTexture(new OpenGLTextureCache(toolkit, stockSource).method1549(material));

		HdSettings.set(HdFeature.TEXTURE_REPLACEMENTS, 1);
		RgbMaterialSource artSource = new RgbMaterialSource();
		Pixel replaced = drawTexture(new OpenGLTextureCache(toolkit, artSource).method1549(material));

		int expected = art[artY * TEXELS + artX];
		Pixel painted = new Pixel((expected >> 16) & 0xFF, (expected >> 8) & 0xFF, expected & 0xFF, 255);
		System.out.println("[hd-replace] RGB upload: stock " + plain + ", replaced " + replaced + ", art "
			+ painted + "; source rgb calls " + artSource.rgbCalls + ", argb calls " + artSource.argbCalls);
		assertAll(
			// The path is the proof's subject: a colour-only (6407) upload, not the
			// ARGB one the older proofs already covered.
			() -> assertEquals(1, artSource.rgbCalls, "the cache asked for colour-only pixels"),
			() -> assertEquals(0, artSource.argbCalls, "and never for an ARGB array"),
			() -> assertEquals("rgb(60,60,60)", plain.rgb(), "row off is the game's own texels"),
			// An RGB texture has no alpha channel to keep, so the drawn alpha is the
			// sampler's 1.0 - the point is the colour, which is the authored art.
			() -> assertEquals(255, replaced.alpha, "a colour-only upload draws opaque"),
			() -> assertEquals(painted, replaced, "the authored art is what the RGB upload shows")
		);
	}

	/**
	 * The stock cache's material source for one small RGB material.
	 *
	 * <p>{@code aBoolean640} asks for a colour-only upload and slot 0 is a slot
	 * {@code Class133.method3349} keeps no alpha for, which is exactly the branch
	 * in {@code OpenGLTextureCache.method1549} this proof is about. The pixels are
	 * the same flat grey the older proofs use, so the art is the only thing that
	 * can move the drawn pixel.
	 */
	private static final class RgbMaterialSource implements unpack_Sub1 {

		int rgbCalls;

		int argbCalls;

		@Override
		public boolean textureAvailable(int material) {
			return material >= 0;
		}

		@Override
		public int[] getPixels(boolean readReversed, int material, float brightness, int width, int height) {
			this.rgbCalls++;
			return stock();
		}

		@Override
		public float[] method2394(float brightness, int material, int width, int height) {
			return new float[width * height * 4];
		}

		@Override
		public int[] getPixelsArgb(int material, float brightness, int width, int height) {
			this.argbCalls++;
			return stock();
		}

		@Override
		public MaterialType getMetrics(int material) {
			MaterialType type = new MaterialType();
			type.isSmall = true;
			type.aBoolean640 = true;
			type.aByte89 = 0;
			type.aByte92 = 0;
			type.aBoolean638 = false;
			return type;
		}
	}

	/** The centre texel, 0..255 per channel, with its read alpha. */
	private static final class Pixel {

		final int red;

		final int green;

		final int blue;

		final int alpha;

		Pixel(int red, int green, int blue, int alpha) {
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.alpha = alpha;
		}

		String rgb() {
			return "rgb(" + this.red + "," + this.green + "," + this.blue + ")";
		}

		@Override
		public boolean equals(Object other) {
			if (!(other instanceof Pixel)) {
				return false;
			}
			Pixel that = (Pixel) other;
			return this.red == that.red && this.green == that.green && this.blue == that.blue
				&& this.alpha == that.alpha;
		}

		@Override
		public int hashCode() {
			return (this.alpha << 24) | (this.red << 16) | (this.green << 8) | this.blue;
		}

		@Override
		public String toString() {
			return this.rgb() + " alpha " + this.alpha;
		}
	}
}
