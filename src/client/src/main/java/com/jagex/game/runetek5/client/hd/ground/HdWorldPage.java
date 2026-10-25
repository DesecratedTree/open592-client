package com.jagex.game.runetek5.client.hd.ground;

import com.jagex.game.runetek5.unpack_Sub1;

/**
 * Assembles the world-texture page from the client's own material pixels.
 *
 * <p>The page is the same art the stock ground draw binds one-at-a-time:
 * {@code HdMaterialArt} produces a material's texture from the cache's material
 * record and its texture operations, which is exactly what
 * {@code OpenGLTextureCache.method1549} does before handing a single texture to
 * the fixed-function pass. Copying each registered material into its
 * {@link HdWorldAtlas} cell therefore changes where a texture is read from, not
 * what it looks like.
 *
 * <p>A material whose texture has not streamed in yet contributes nothing: the
 * page keeps the previous frame's cell (or an empty one), and the caller only
 * registers materials the provider says are available. Rebuilding the whole
 * page each time a material joins costs one copy of 512x512 ints - once per new
 * material, not per frame. A cell recycled by {@link HdMaterialMap} is copied
 * again on that same rebuild, which is why the page reads its cells from the
 * map rather than caching them: after a recycling the map no longer points the
 * displaced material at the cell, so its art leaves the page with it.
 *
 * <p>The page has to be the texture the stock draw would have bound, alpha
 * included, and the format that decides it lives in one place:
 * {@link HdMaterialArt}. That type is also why this page cannot disagree with
 * the material map about whether a material can be held at all - both ask it.
 * The history is worth keeping: the page once carried its own copy of the
 * RGB-versus-alpha rule, kept every material's alpha, and so turned an opaque
 * ground texture translucent at its dark texels, which reads as a see-through
 * world with the scene's blending on.
 */
public final class HdWorldPage {

	private HdWorldPage() {
	}

	/**
	 * The page's texels, as the {@code 0xAARRGGBB} ints the texture constructor
	 * takes. Each assigned material's pixels are copied into its cell; a
	 * material with no cell or no texture contributes nothing.
	 */
	public static int[] pixels(HdMaterialMap map, unpack_Sub1 provider) {
		int[] page = new int[HdWorldAtlas.SIZE * HdWorldAtlas.SIZE];
		if (map == null || provider == null) {
			return page;
		}
		int cellSize = HdWorldAtlas.CELL_SIZE;
		for (int material = 0; material < HdMaterialMap.MATERIALS; material++) {
			int cell = map.cellOf(material);
			if (cell == HdMaterialMap.NO_CELL) {
				continue;
			}
			// The art in the format its stock upload uses, from the one owner of
			// that decision: an RGB upload arrives opaque, everything else keeps
			// its own alpha. Null is a material the page cannot hold, so its cell
			// stays empty - which is why the map never publishes such a cell.
			int[] art = HdMaterialArt.art(provider, material);
			if (art == null) {
				continue;
			}
			int left = HdWorldAtlas.x(cell);
			int bottom = HdWorldAtlas.y(cell);
			for (int row = 0; row < cellSize; row++) {
				System.arraycopy(art, row * cellSize, page, (bottom + row) * HdWorldAtlas.SIZE + left,
					cellSize);
			}
		}
		return page;
	}

	/** The page's side, so the caller does not repeat the layout constants. */
	public static int size() {
		return HdWorldAtlas.SIZE;
	}
}
