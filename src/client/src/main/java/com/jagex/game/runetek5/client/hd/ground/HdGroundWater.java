package com.jagex.game.runetek5.client.hd.ground;

/**
 * How much water stands over one terrain tile, as the client already stores it.
 *
 * <p>The source is the stock ground draw's own per-tile value. {@code Terrain}
 * reads it for every tile it submits - {@code Terrain.method...} lines
 * {@code local1261/1266/1289} - from three arrays that are only touched when the
 * region is {@code underwater}:
 *
 * <ul>
 * <li>{@code Class16_Sub5_Sub1.method3012(x, z)} - a plain byte, 0..255, handed
 * to {@code GlGround.B} as the third value and kept by {@code GlGroundLayer} as
 * {@code waterDepth};</li>
 * <li>{@code Class149.method3563(x, z)} - a byte scaled by 8, the stock water
 * effect's ripple period denominator;</li>
 * <li>{@code PathingEntity.method4753(x, z)} - a 24-bit value, the water's fog
 * tint.</li>
 * </ul>
 *
 * <p>{@code GlGroundLayer.method1462} then hands all three to
 * {@code GlToolkit.GA}, and {@code StillWaterEffect} uses the plain byte as
 * {@code depth / 255} - the factor that fades the submerged look in. That is the
 * number this class carries: the client's own measure of how deep the water over
 * that tile is, not a value invented for caustics.
 *
 * <p>The honest limit: the arrays are populated only for an {@code underwater}
 * region, so this is the depth of terrain the player is standing under water on.
 * A lake or an ocean in an ordinary region draws its surface as a separate water
 * object and leaves every tile's value at zero; such a tile is {@link #DRY} here
 * even though a water plane covers it. Caustics therefore appear on submerged
 * ground and nowhere else, which is the claim the row makes.
 */
public final class HdGroundWater {

	/** No water over the tile: the stock value, and the identity for the row. */
	public static final HdGroundWater DRY = new HdGroundWater(0);

	/** The stock byte's full scale; {@code StillWaterEffect} divides by it too. */
	public static final int FULL_DEPTH = 255;

	private final int depth;

	private HdGroundWater(int depth) {
		this.depth = depth;
	}

	/**
	 * The water over one tile, clamped into the stock byte's range.
	 *
	 * <p>Zero returns {@link #DRY} itself, so a dry tile's value compares equal to
	 * the identity and the shader's off path stays a bit-for-bit copy of the
	 * frame before the row existed.
	 */
	public static HdGroundWater of(int depth) {
		if (depth <= 0) {
			return DRY;
		}
		return new HdGroundWater(depth > FULL_DEPTH ? FULL_DEPTH : depth);
	}

	/** The stock 0..255 depth byte for this tile. */
	public int depth() {
		return this.depth;
	}

	/** True when any water stands over this tile. */
	public boolean submerged() {
		return this.depth > 0;
	}

	/** {@code depth / 255}: the same fraction the stock water effect applies. */
	public float fraction() {
		return this.depth / (float) FULL_DEPTH;
	}

	@Override
	public String toString() {
		return "water=" + this.depth;
	}
}
