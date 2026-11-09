package com.jagex.game.runetek5.client.hd.material;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jagex.game.runetek5.client.hd.content.HdContent;
import com.jagex.game.runetek5.client.hd.content.HdMaterialDefinition;

/**
 * The material-map decode: what the shader can and cannot be given.
 *
 * <p>Every map is resampled to one square so a material costs four small
 * uploads; the alpha rule is the one that matters, because a PNG without an
 * alpha channel decodes to transparent black through {@code getRGB}, and a
 * transparent occlusion map would make every such material fully unoccluded by
 * accident rather than by content.
 */
@DisplayName("592HD material map decode")
class HdMaterialTexturesTest {

	@Test
	@DisplayName("a PNG is resampled to the square, opaque where it has no alpha")
	void png_shouldResampleOpaque() throws Exception {
		int[] pixels = HdMaterialTextures.decode(png(8, false), 4);

		assertNotNull(pixels);
		assertEquals(16, pixels.length);
		assertAll(
			// The source is mid grey with no alpha channel, so every texel keeps
			// its colour and gains an opaque alpha.
			() -> assertEquals(0x80, pixels[0] >> 16 & 0xFF),
			() -> assertEquals(0xFF, pixels[0] >> 24 & 0xFF, "an alpha-less PNG must decode opaque"),
			() -> assertEquals(0xFF, pixels[15] >> 24 & 0xFF)
		);
	}

	@Test
	@DisplayName("the source's alpha survives when it has one")
	void png_shouldKeepAlpha() throws Exception {
		int[] pixels = HdMaterialTextures.decode(png(8, true), 4);

		assertNotNull(pixels);
		assertEquals(0x40, pixels[0] >> 24 & 0xFF, "a transparent source texel must stay transparent");
	}

	@Test
	@DisplayName("a downscale averages the texels it covers instead of keeping one per block")
	void downscale_shouldAverage() throws Exception {
		// 4x4: one white texel and three black in every 2x2 block. Point sampling
		// keeps the white one - aliased detail the shader reads as grain - while
		// averaging makes it a quarter.
		BufferedImage image = new BufferedImage(4, 4, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				boolean bright = (x & 1) == 0 && (y & 1) == 0;
				image.setRGB(x, y, 0xFF000000 | (bright ? 0xFFFFFF : 0x000000));
			}
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "png", out);
		int[] pixels = HdMaterialTextures.decode(out.toByteArray(), 2);

		assertNotNull(pixels);
		assertAll(
			() -> assertEquals(4, pixels.length),
			() -> assertEquals(0x40, pixels[0] >> 16 & 0xFF, "one of four texels is white"),
			() -> assertEquals(0xFF, pixels[0] >> 24 & 0xFF),
			() -> assertEquals(pixels[0], pixels[3], "every block averages the same way")
		);
	}

	@Test
	@DisplayName("a block of transparent texels keeps its colour, not black")
	void transparentBlock_shouldKeepItsColour() throws Exception {
		// 634HD's normal PNGs often carry an unused all-zero alpha channel.
		// An alpha-weighted average would emit opaque black for every block
		// and the shader would read a hole in the surface.
		BufferedImage image = new BufferedImage(4, 4, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				image.setRGB(x, y, 0x5A5A5A);
			}
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "png", out);
		int[] pixels = HdMaterialTextures.decode(out.toByteArray(), 2);

		assertNotNull(pixels);
		assertAll(
			() -> assertEquals(0x5A, pixels[0] >> 16 & 0xFF,
				"the block's colour survives"),
			() -> assertEquals(0x00, pixels[0] >> 24 & 0xFF,
				"its alpha is still the block's")
		);
	}

	@Test
	@DisplayName("absent or unreadable art is no map, never an empty unit")
	void unreadable_shouldBeAbsent() {
		assertAll(
			() -> assertNull(HdMaterialTextures.decode(null, 4)),
			() -> assertNull(HdMaterialTextures.decode(new byte[0], 4)),
			() -> assertNull(HdMaterialTextures.decode("not a png".getBytes("UTF-8"), 4)),
			() -> assertNull(HdMaterialTextures.decode(png(4, false), 0))
		);
	}

	@Test
	@DisplayName("the map size is the shader's square and is a power of two")
	void size_shouldBeASquarePowerOfTwo() {
		int size = HdMaterialTextures.SIZE;
		assertTrue(size > 0 && (size & (size - 1)) == 0, "map size " + size + " is not a power of two");
	}

	@Test
	@DisplayName("a shipped set resolves its blocks, and its height from the packed map")
	void shippedSet_shouldResolveBlocks() {
		HdContent content = HdContent.get();
		HdMaterialDefinition definition = content.material(63);
		assertNotNull(definition, "material 63 must ship");
		HdMaterialTextures.Resolution resolution = HdMaterialTextures.resolve(content, definition);
		System.out.println("[hd-maps] 63: normal " + resolution.normalSource + ", orm " + resolution.ormSource
			+ ", height " + resolution.heightSource);
		assertAll(
			() -> assertNull(resolution.reason),
			() -> assertTrue(resolution.normalSource != null && resolution.normalSource.endsWith(".bc5"),
				"the normal is the precompressed BC5 set: " + resolution.normalSource),
			() -> assertTrue(resolution.ormSource != null && resolution.ormSource.endsWith(".bc7"),
				"the packed map is BC7: " + resolution.ormSource),
			() -> assertNotNull(resolution.height, "a shipped set must carry a height"),
			() -> assertNotNull(resolution.heightSource)
		);
	}

	@Test
	@DisplayName("the block decode matches the PNG the blocks were made from")
	void blocks_shouldMatchTheirPng() {
		HdContent content = HdContent.get();
		HdMaterialDefinition definition = content.material(63);
		HdMaterialTextures.Resolution resolution = HdMaterialTextures.resolve(content, definition);
		int[] pngNormal = HdMaterialTextures.decode(content.materialFile(63, definition.normal()),
			HdMaterialTextures.SIZE);
		int[] pngOrm = HdMaterialTextures.decode(content.materialFile(63, definition.orm()), HdMaterialTextures.SIZE);
		double normalError = meanError(resolution.normal, pngNormal, 2);
		double ormError = meanError(resolution.orm, pngOrm, 3);
		System.out.println("[hd-maps] 63 decode vs png: normal " + normalError + ", orm " + ormError);
		assertAll(
			() -> assertTrue(normalError < 6.0, "BC5's two channels must match the PNG's: " + normalError),
			() -> assertTrue(ormError < 6.0, "BC7's three channels must match the PNG's: " + ormError)
		);
	}

	@Test
	@DisplayName("the height is the packed map's alpha, the field 634HD packs it in")
	void height_shouldComeFromThePackedAlpha() {
		HdContent content = HdContent.get();
		HdMaterialDefinition definition = content.material(63);
		HdMaterialTextures.Resolution resolution = HdMaterialTextures.resolve(content, definition);
		assertNotNull(resolution.height);
		assertNotNull(resolution.orm);
		int varied = 0;
		int worst = 0;
		for (int i = 0; i < resolution.height.length; i++) {
			int height = resolution.height[i] >>> 24;
			worst = Math.max(worst, Math.abs(height - (resolution.orm[i] >>> 24)));
			if (height != 255) {
				varied++;
			}
		}
		final int nonOpaque = varied;
		final int worstError = worst;
		assertAll(
			() -> assertEquals(resolution.ormSource, resolution.heightSource,
				"the height is read from the packed map, as upstream reads it"),
			() -> assertEquals(0, worstError, "the height texture is exactly that map's alpha"),
			() -> assertTrue(nonOpaque > resolution.height.length / 4,
				"the height must vary, not be a flat opaque map: " + nonOpaque + " non-opaque texels")
		);
	}

	/** Mean absolute channel error between two decoded maps. */
	private static double meanError(int[] decoded, int[] png, int channels) {
		double total = 0.0;
		for (int i = 0; i < decoded.length; i++) {
			for (int c = 0; c < channels; c++) {
				int shift = 16 - 8 * c;
				total += Math.abs((decoded[i] >> shift & 0xFF) - (png[i] >> shift & 0xFF));
			}
		}
		return total / (decoded.length * channels);
	}

	/** One flat PNG, 0x808080 at 0x40 alpha for the transparent case. */
	private static byte[] png(int side, boolean alpha) throws Exception {
		BufferedImage image = new BufferedImage(side, side,
			alpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
		int texel = alpha ? 0x40808080 : 0x808080;
		for (int y = 0; y < side; y++) {
			for (int x = 0; x < side; x++) {
				image.setRGB(x, y, texel);
			}
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "png", out);
		return out.toByteArray();
	}
}
