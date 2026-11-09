package com.jagex.game.runetek5.client.hd.content;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * The authored replacement albedo for a texture, decoded to the ARGB texels
 * this client's uploads use.
 *
 * <p>Upstream ships replacement art as PNG (and BC5/BC7 for the material maps
 * that the GPU path samples directly). This client has no BC decoder and its
 * stock texture cache samples RGBA, so only the PNG is read here: a material
 * whose art exists only as a compressed block gets no replacement and keeps
 * the game's texture, which is the honest fallback rather than uploading
 * bytes the sampler cannot interpret. That difference is recorded in
 * {@code docs/634hd-parity-ledger.md}.
 *
 * <p>Replacement art is scaled to the size the caller asks for. Downscaling is
 * a box filter - every source texel a target texel covers, averaged - because
 * authored albedo is far larger than the 64x64 stock texture (or 128x128 fresh
 * set) it stands in for, and point-sampling it baked aliasing into level 0 that
 * no mip chain can remove: the reason authored grass, stone and fur read as
 * grain rather than detail. The average is weighted by alpha so a transparent
 * neighbour cannot darken a texel. Upscaling, which no shipped art needs, picks
 * the nearest texel and fills its block.
 *
 * <p>{@link #combine(int[], int[])} keeps the game texture's alpha channel and
 * takes only the replacement's colour, matching upstream's fresh-texture path
 * for art without an alpha mask: cut-outs, transparency and masks stay exactly
 * where the cache put them.
 */
public final class HdTextureReplacements {

	private static final Map<Long, int[]> CACHE = new HashMap<Long, int[]>();

	private HdTextureReplacements() {
	}

	/** The replacement art for {@code material} at {@code size} square, or null. */
	public static int[] art(int material, int size) {
		return art(HdContent.get(), material, size);
	}

	/** The replacement art, read through a specific content set. */
	public static synchronized int[] art(HdContent content, int material, int size) {
		if (content == null || material < 0 || size <= 0) {
			return null;
		}
		long key = ((long) material << 32) | (size & 0xFFFFFFFFL);
		if (CACHE.containsKey(Long.valueOf(key))) {
			return CACHE.get(Long.valueOf(key));
		}
		HdMaterialDefinition definition = content.material(material);
		if (definition == null || !definition.hasAlbedo()) {
			return null;
		}
		byte[] bytes = content.materialFile(material, definition.albedo());
		int[] art = decode(bytes, size);
		if (art != null) {
			CACHE.put(Long.valueOf(key), art);
		}
		return art;
	}

	/**
	 * The stock texels with the replacement's colour where the row is on and the
	 * content names art; the stock array itself otherwise.
	 *
	 * <p>The gate lives here rather than at each caller so the stock texture
	 * cache and the world page cannot disagree about whether the row is live,
	 * and so "off" is provably the game's own array - the same reference, not a
	 * copy that happens to look the same.
	 */
	public static int[] applied(int material, int size, int[] stock) {
		if (stock == null || !com.jagex.game.runetek5.client.hd.HdSettings.isEnabled()
			|| com.jagex.game.runetek5.client.hd.HdSettings.value(
				com.jagex.game.runetek5.client.hd.HdFeature.TEXTURE_REPLACEMENTS) == 0) {
			return stock;
		}
		int[] art = art(material, size);
		return art == null ? stock : combine(stock, art);
	}

	/** True when this content names replacement art for the material. */
	public static boolean has(HdContent content, int material) {
		HdMaterialDefinition definition = content == null ? null : content.material(material);
		return definition != null && definition.hasAlbedo();
	}

	/** Forgets every decoded image; the test hook, and a future reload seam. */
	public static synchronized void clear() {
		CACHE.clear();
	}

	/**
	 * The replacement's colour with the original's alpha, texel for texel.
	 * A null replacement returns the original untouched.
	 */
	public static int[] combine(int[] original, int[] replacement) {
		if (original == null || replacement == null) {
			return original;
		}
		int length = Math.min(original.length, replacement.length);
		int[] combined = new int[length];
		for (int i = 0; i < length; i++) {
			combined[i] = (original[i] & 0xFF000000) | (replacement[i] & 0xFFFFFF);
		}
		return combined;
	}

	/** Decodes PNG bytes to {@code size} square ARGB texels, or null. */
	static int[] decode(byte[] png, int size) {
		if (png == null || png.length == 0 || size <= 0) {
			return null;
		}
		BufferedImage image;
		try {
			image = ImageIO.read(new ByteArrayInputStream(png));
		} catch (IOException ex) {
			return null;
		} catch (RuntimeException ex) {
			return null;
		}
		if (image == null || image.getWidth() <= 0 || image.getHeight() <= 0) {
			return null;
		}
		int width = image.getWidth();
		int height = image.getHeight();
		int[] source = image.getRGB(0, 0, width, height, null, 0, width);
		boolean alpha = image.getColorModel().hasAlpha();
		int[] out = new int[size * size];
		if (size >= width || size >= height) {
			// Nothing to average: each target texel is the nearest source one.
			for (int y = 0; y < size; y++) {
				int sourceY = y * height / size;
				for (int x = 0; x < size; x++) {
					int texel = source[sourceY * width + x * width / size];
					out[y * size + x] = alpha ? texel : texel | 0xFF000000;
				}
			}
			return out;
		}
		// Downscale: average the source texels each target texel covers, weighted
		// by alpha, so fine authored detail becomes the texture's own resolution
		// instead of the aliased noise point sampling leaves behind.
		for (int y = 0; y < size; y++) {
			int top = y * height / size;
			int bottom = Math.max(top + 1, (y + 1) * height / size);
			for (int x = 0; x < size; x++) {
				int left = x * width / size;
				int right = Math.max(left + 1, (x + 1) * width / size);
				long red = 0L;
				long green = 0L;
				long blue = 0L;
				long weight = 0L;
				int count = 0;
				for (int sourceY = top; sourceY < bottom; sourceY++) {
					for (int sourceX = left; sourceX < right; sourceX++) {
						int texel = source[sourceY * width + sourceX];
						int texelAlpha = alpha ? texel >>> 24 : 0xFF;
						red += (long) ((texel >> 16) & 0xFF) * texelAlpha;
						green += (long) ((texel >> 8) & 0xFF) * texelAlpha;
						blue += (long) (texel & 0xFF) * texelAlpha;
						weight += texelAlpha;
						count++;
					}
				}
				int outAlpha = alpha ? (int) ((weight + count / 2) / count) : 0xFF;
				if (weight == 0L) {
					out[y * size + x] = 0;
					continue;
				}
				int averageRed = (int) ((red + weight / 2) / weight);
				int averageGreen = (int) ((green + weight / 2) / weight);
				int averageBlue = (int) ((blue + weight / 2) / weight);
				out[y * size + x] = outAlpha << 24 | averageRed << 16 | averageGreen << 8 | averageBlue;
			}
		}
		return out;
	}
}
