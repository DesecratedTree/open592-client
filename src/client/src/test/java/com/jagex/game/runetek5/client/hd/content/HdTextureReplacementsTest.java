package com.jagex.game.runetek5.client.hd.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class HdTextureReplacementsTest {

	private static HdContent content;

	@BeforeAll
	static void load() {
		content = HdContent.loadDefault();
		HdTextureReplacements.clear();
	}

	@Test
	void upscalesSmallArtWithNearestSampling() throws IOException {
		int[] texels = HdTextureReplacements.decode(png(2, 2, new int[] {
			0xFFFF0000, 0xFF00FF00, 0xFF0000FF, 0xFFFFFFFF }), 4);

		assertNotNull(texels);
		assertEquals(16, texels.length);
		// Each source texel fills a 2x2 block; alpha survives the decode.
		assertEquals(0xFFFF0000, texels[0]);
		assertEquals(0xFFFF0000, texels[1]);
		assertEquals(0xFFFF0000, texels[4]);
		assertEquals(0xFF00FF00, texels[2]);
		assertEquals(0xFF0000FF, texels[8]);
		assertEquals(0xFFFFFFFF, texels[15]);
	}

	/**
	 * The grain fix at its own seam: authored art is larger than the stock
	 * texture, so a target texel covers many source texels and takes their
	 * average. Point sampling would return one of the two tones - the aliased
	 * noise that made authored grass, stone and fur read as grain.
	 */
	@Test
	void downscalesAuthoredArtWithABoxFilter() throws IOException {
		int[] checker = new int[16];
		for (int texel = 0; texel < checker.length; texel++) {
			boolean black = ((texel % 4) + (texel / 4)) % 2 == 0;
			checker[texel] = black ? 0xFF000000 : 0xFFFFFFFF;
		}

		int[] texels = HdTextureReplacements.decode(png(4, 4, checker), 2);

		assertNotNull(texels);
		assertEquals(4, texels.length);
		for (int texel = 0; texel < texels.length; texel++) {
			assertEquals(0xFF808080, texels[texel],
				"a 2x2 block of the checker must average to mid grey, not pick a tone");
		}
	}

	/**
	 * The average is weighted by alpha: a transparent texel contributes no
	 * colour, so a cut-out's edge cannot darken the texels beside it.
	 */
	@Test
	void aTransparentNeighbourDoesNotDarkenTheAverage() throws IOException {
		int[] art = { 0xFFFF0000, 0x00000000, 0x00000000, 0x00000000 };

		int[] texels = HdTextureReplacements.decode(png(2, 2, art), 1);

		assertNotNull(texels);
		assertEquals(1, texels.length);
		assertEquals(0xFF, (texels[0] >> 16) & 0xFF, "the opaque red is the colour");
		assertEquals(0, (texels[0] >> 8) & 0xFF);
		assertEquals(64, texels[0] >>> 24, "one opaque texel of four is a quarter alpha");
	}

	@Test
	void aPngWithoutAnAlphaChannelDecodesOpaque() throws IOException {
		BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		image.setRGB(0, 0, 0x00123456);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "png", out);

		int[] texels = HdTextureReplacements.decode(out.toByteArray(), 1);
		assertNotNull(texels);
		assertEquals(0xFF123456, texels[0]);
	}

	@Test
	void garbageBytesDecodeToNull() {
		assertNull(HdTextureReplacements.decode(new byte[] { 1, 2, 3, 4 }, 8));
		assertNull(HdTextureReplacements.decode(null, 8));
		assertNull(HdTextureReplacements.decode(new byte[0], 8));
	}

	@Test
	void combineKeepsTheStockAlphaAndTakesTheFreshColour() {
		int[] original = { 0x00112233, 0x80AABBCC };
		int[] replacement = { 0xFF445566, 0xFF778899 };
		int[] combined = HdTextureReplacements.combine(original, replacement);

		assertEquals(0x00445566, combined[0]);
		assertEquals(0x80778899, combined[1]);
	}

	@Test
	void combineReturnsTheOriginalWhenThereIsNoReplacement() {
		int[] original = { 0x80112233 };
		assertSame(original, HdTextureReplacements.combine(original, null));
		assertNull(HdTextureReplacements.combine(null, original));
	}

	/**
	 * The row's gate, proven at its own seam: off is the game's array by
	 * reference, so no upload can be changed by a feature the player turned off.
	 */
	@Test
	void theRowOffKeepsTheGameArrayItself() {
		HdSettings.resetForTest();
		setSettings(0);
		int[] stock = { 0x803C3C3C };
		assertSame(stock, HdTextureReplacements.applied(10001, 64, stock));

		// Even with the row stored on, a disabled 592HD keeps the game's art.
		HdSettings.set(HdFeature.TEXTURE_REPLACEMENTS, 1);
		HdSettings.setEnabled(false, null);
		assertSame(stock, HdTextureReplacements.applied(10001, 64, stock));
		HdSettings.resetForTest();
	}

	@Test
	void theRowOnTakesTheReplacementColourAndKeepsTheAlpha() {
		setSettings(1);
		int size = 8;
		int[] stock = new int[size * size];
		java.util.Arrays.fill(stock, 0x803C3C3C);
		int[] art = HdTextureReplacements.art(content, 10001, size);
		int[] applied = HdTextureReplacements.applied(10001, size, stock);

		assertNotNull(art);
		assertNotSame(stock, applied);
		for (int i = 0; i < applied.length; i++) {
			assertEquals(0x80, applied[i] >>> 24, "the game texture's alpha at " + i);
			assertEquals(art[i] & 0xFFFFFF, applied[i] & 0xFFFFFF, "the art's colour at " + i);
		}
		HdSettings.resetForTest();
	}

	@Test
	void aMaterialWithoutArtKeepsTheGameArrayEvenWithTheRowOn() {
		setSettings(1);
		int[] stock = { 0x803C3C3C };
		assertSame(stock, HdTextureReplacements.applied(1000000, 64, stock));
		HdSettings.resetForTest();
	}

	/** Settings the gate reads, in a throwaway directory. */
	private static void setSettings(int row) {
		try {
			Path preferences = Files.createTempDirectory("hd-replacements");
			HdSettings.setDirectoryForTest(preferences.toFile());
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.TEXTURE_REPLACEMENTS, row);
	}

	@Test
	void aShippedMaterialDecodesToACell() {
		int[] art = HdTextureReplacements.art(content, 10001, HdWorldAtlasCell.SIZE);

		assertNotNull(art);
		assertEquals(HdWorldAtlasCell.SIZE * HdWorldAtlasCell.SIZE, art.length);
		for (int i = 0; i < art.length; i++) {
			assertEquals(0xFF, art[i] >>> 24, "texel " + i);
		}
	}

	@Test
	void aMaterialWithoutAnAlbedoHasNoReplacement() {
		assertNull(HdTextureReplacements.art(content, 1000000, 64));
		assertNull(HdTextureReplacements.art(content, -1, 64));
		assertEquals(false, HdTextureReplacements.has(content, 1000000));
	}

	/** Avoid depending on the renderer's atlas constant in a content test. */
	private static final class HdWorldAtlasCell {

		static final int SIZE = 64;
	}

	private static byte[] png(int width, int height, int[] argb) throws IOException {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		image.setRGB(0, 0, width, height, argb, 0, width);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "png", out);
		return out.toByteArray();
	}
}
