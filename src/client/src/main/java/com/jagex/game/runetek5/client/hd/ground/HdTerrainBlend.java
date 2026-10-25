package com.jagex.game.runetek5.client.hd.ground;

/**
 * The terrain blend's mask: how strongly a tile's second material may reach
 * through, and how large the noise cells that carry it are.
 *
 * <p>Which two materials a tile has is the tile's own business and lives in
 * {@link HdTileMaterialMap}; this class is only the term that mixes them. It is
 * one value for the whole frame, built from the Terrain blend row, so the seam
 * resolves the row once instead of per tile.
 *
 * <p>{@link #OFF} is the identity: strength exactly zero, which is also the
 * shader's off state, so a frame with the row off mixes nothing and the ground
 * draws each layer's own material alone - the frame before the row existed.
 */
public final class HdTerrainBlend {

	/** How much of the second material the mask can reach at full strength. */
	public static final float STRENGTH = 0.6F;

	/**
	 * Noise cells per scene unit. Small enough that the mask varies over a few
	 * tiles rather than every tile, and large enough that one tile still crosses
	 * a band: the boundary reads as a soft edge, not a per-tile dye.
	 */
	public static final float MASK_SCALE = 0.075F;

	/** The identity: no blend. */
	public static final HdTerrainBlend OFF = new HdTerrainBlend(0.0F, 0.0F);

	private final float strength;

	private final float maskScale;

	private HdTerrainBlend(float strength, float maskScale) {
		this.strength = strength;
		this.maskScale = maskScale;
	}

	/** The mask term for a frame, from the Terrain blend row. */
	public static HdTerrainBlend of(boolean requested) {
		return requested ? new HdTerrainBlend(STRENGTH, MASK_SCALE) : OFF;
	}

	/** The mask's strongest reach, 0..1. */
	public float strength() {
		return this.strength;
	}

	/** Noise cells per scene unit; zero off. */
	public float maskScale() {
		return this.maskScale;
	}

	/** True when tiles may mix their two materials. */
	public boolean active() {
		return this.strength > 0.0F;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HdTerrainBlend)) {
			return false;
		}
		HdTerrainBlend blend = (HdTerrainBlend) other;
		return blend.strength == this.strength && blend.maskScale == this.maskScale;
	}

	@Override
	public int hashCode() {
		return Float.floatToIntBits(this.strength) * 31 + Float.floatToIntBits(this.maskScale);
	}

	@Override
	public String toString() {
		return "terrainBlend@" + this.strength + "/" + this.maskScale;
	}
}
