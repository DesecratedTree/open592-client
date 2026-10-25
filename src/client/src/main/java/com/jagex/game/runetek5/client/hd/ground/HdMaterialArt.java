package com.jagex.game.runetek5.client.hd.ground;

import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.unpack_Sub1;

/**
 * One ground material's art, in the format the stock upload of it uses: the
 * single owner of the RGB-versus-alpha decision, and of the "can the page hold
 * this material at all" answer.
 *
 * <p>{@code OpenGLTextureCache.method1549} builds a material's stock texture two
 * ways. When {@code MaterialType.aBoolean640} is set and the texture-effect slot
 * is not one of the two {@code Class133.method3349} keeps an alpha channel for,
 * it uploads <b>RGB</b>: the texture has no alpha channel, every texel is
 * opaque, and the art comes from the sampler that path feeds. Everything else is
 * uploaded with its alpha, from the ARGB sampler. {@link #uploadsRgb} restates
 * that condition once, and {@link #art} is the only code that acts on it.
 *
 * <p>This type exists because the rule was once restated in two more places - the
 * world-texture page and, separately, the material map's gate - and the copies
 * drifted: the page kept the alpha of an RGB material, so its dark texels became
 * translucent holes in an opaque texture. A page and a map that both call this
 * method cannot disagree about a material, which is the point; a caller that
 * re-derives the condition instead re-opens that bug.
 *
 * <p>A cell the page cannot fill is reported as {@code null} rather than as
 * zeroes: a zeroed cell samples as black with alpha zero, which the ground pass'
 * alpha test discards, and a discarded fragment writes neither colour nor depth.
 * That is a hole in the terrain, not a dark tile, so the material has to be
 * refused before the map points a cell at it.
 */
public final class HdMaterialArt {

	/**
	 * The per-texel brightness both stock sampler calls are made at. The stock
	 * texture cache passes this value and so does the page, so the art is the
	 * same either way.
	 */
	private static final float BRIGHTNESS = 0.7F;

	/** The alpha an RGB upload has by construction: no channel, so always 1. */
	private static final int OPAQUE = 0xFF000000;

	private HdMaterialArt() {
	}

	/**
	 * This material's art, or {@code null} when the page cannot hold it.
	 *
	 * <p>The returned texels are the page's: the same sampler call
	 * {@code OpenGLTextureCache} makes for this material, with the alpha the
	 * stock upload implies - 1 for every texel of an RGB upload, the sampler's own
	 * alpha otherwise. The array is the caller's to keep; a sampler's result is
	 * never written through.
	 *
	 * <p>{@code null} means one of: no provider, a material the provider says is
	 * not available, a material the provider cannot describe (the stock samplers
	 * index the metric row, so asking one is not an option), or art shorter than
	 * one cell.
	 */
	public static int[] art(unpack_Sub1 provider, int material) {
		int cellSize = HdWorldAtlas.CELL_SIZE;
		if (provider == null || material < 0 || !provider.textureAvailable(material)) {
			return null;
		}
		MaterialType type = provider.getMetrics(material);
		if (type == null) {
			return null;
		}
		boolean opaque = uploadsRgb(type);
		int[] art = opaque
			? provider.getPixels(false, material, BRIGHTNESS, cellSize, cellSize)
			: provider.getPixelsArgb(material, BRIGHTNESS, cellSize, cellSize);
		if (art == null || art.length < cellSize * cellSize) {
			return null;
		}
		return opaque ? opaque(art) : art;
	}

	/**
	 * True when {@code OpenGLTextureCache} would upload this material as RGB.
	 *
	 * <p>The condition is not deduced: {@code aBoolean640} asks for the opaque
	 * path, and the slots {@code method3349} excludes are the ones whose art is
	 * only meaningful with an alpha channel. It is stated once, here, so every
	 * material the page holds follows the upload it stands in for.
	 */
	public static boolean uploadsRgb(MaterialType type) {
		return type != null && type.aBoolean640 && Class133.method3349(type.aByte89);
	}

	/** An RGB upload's texels with the alpha it has by construction stated. */
	private static int[] opaque(int[] art) {
		int[] stated = new int[art.length];
		for (int texel = 0; texel < art.length; texel++) {
			stated[texel] = art[texel] | OPAQUE;
		}
		return stated;
	}
}
