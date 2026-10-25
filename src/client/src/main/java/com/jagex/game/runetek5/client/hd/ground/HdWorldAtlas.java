package com.jagex.game.runetek5.client.hd.ground;

/**
 * The world-texture atlas' layout: how many ground material textures fit on one
 * page, and where a cell sits on it.
 *
 * <p>The stock ground draw binds one material texture per layer, so a single
 * draw can only ever show one material. Blending several materials on one tile
 * means the fragment stage has to reach more than one texture, and the cheapest
 * way to do that on this client's fixed-function-era context is one page that
 * holds many textures: every layer draw samples the same page and picks its
 * cell with its own coordinates.
 *
 * <p>The layout is a pure function of the cell number, so the Java side and the
 * shader cannot disagree about where a texture lives: cell {@code n} occupies
 * the square whose top-left is {@code (n % COLUMNS, n / COLUMNS)} in cells, and
 * the shader is handed the same constants. A cell is inset by {@link #INSET}
 * texels when sampled, so a bilinear tap at a cell's edge cannot pull in its
 * neighbour's first row - the seam that shows up as a one-pixel line of the
 * wrong material along every tile boundary.
 *
 * <p>{@link #CAPACITY} is 64 materials, which covers every ground material a
 * 592 region can use at once - but not every material a session visits, since
 * walking to a new region brings a new set. The cells are therefore recycled by
 * staleness ({@link HdMaterialMap#beginFrame()}), so the page always holds the
 * materials being drawn now: one that has not been seen for longer gives its
 * cell up to a newcomer, and keeps the stock single-texture draw - the state
 * before this row existed - until it is drawn again.
 */
public final class HdWorldAtlas {

	/** Cells across the page. */
	public static final int COLUMNS = 8;

	/** Cells down the page. */
	public static final int ROWS = 8;

	/** Texels per cell, one material texture's side on the page. */
	public static final int CELL_SIZE = 64;

	/** The page's side in texels; a power of two for this context's filter state. */
	public static final int SIZE = COLUMNS * CELL_SIZE;

	/** Materials one page can hold. */
	public static final int CAPACITY = COLUMNS * ROWS;

	/**
	 * Texels a cell's sampling rectangle is inset by, in each direction. One
	 * texel is enough for a bilinear tap; the second is margin for a mip level
	 * the driver chooses.
	 */
	public static final int INSET = 1;

	private HdWorldAtlas() {
	}

	/** The cell a material's texture occupies, or -1 when the number cannot be a cell. */
	public static int cell(int cell) {
		return cell < 0 || cell >= CAPACITY ? -1 : cell;
	}

	/** The cell's left edge in texels. */
	public static int x(int cell) {
		return (cell % COLUMNS) * CELL_SIZE;
	}

	/** The cell's bottom edge in texels; the page's v axis grows upward. */
	public static int y(int cell) {
		return (cell / COLUMNS) * CELL_SIZE;
	}

	/** The sampled rectangle's left edge, u in 0..1. */
	public static float u0(int cell) {
		return (x(cell) + INSET) / (float) SIZE;
	}

	/** The sampled rectangle's bottom edge, v in 0..1. */
	public static float v0(int cell) {
		return (y(cell) + INSET) / (float) SIZE;
	}

	/** Width of the sampled rectangle, u in 0..1. */
	public static float span() {
		return (CELL_SIZE - 2 * INSET) / (float) SIZE;
	}

	/** How many texels of the page one cell's sampled rectangle covers. */
	public static int sampledSize() {
		return CELL_SIZE - 2 * INSET;
	}
}
