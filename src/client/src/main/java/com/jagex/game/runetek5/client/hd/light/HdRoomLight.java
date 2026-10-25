package com.jagex.game.runetek5.client.hd.light;

/**
 * One region's room light: an axis-aligned block of scene space, a colour and a
 * strength.
 *
 * <p>A region is a map chunk - eight by eight tiles, the same unit
 * {@code TileLighting} is stored and applied in - so the bounds tile the scene
 * exactly and no region overlaps its neighbour. The shader tests a fragment's
 * scene x/z against these bounds, which is what makes "does not leak into the
 * next region" structural rather than a tuning exercise: a fragment either is
 * inside this block or it is not, and only one block can contain it.
 *
 * <p>Both values are the region's own light state, not a new source: the
 * strength is the region's stock ambient ({@code TileLighting.aFloat150})
 * relative to the default every map without a light override uses, and the
 * colour is the intensity-weighted average of the map lights registered on the
 * region's tiles. A region with no map lights keeps the default white, so an
 * ordinary lit room is a neutral raise rather than a coloured cast.
 */
public final class HdRoomLight {

	/** The ambient {@code TileLighting} defaults to, i.e. the neutral strength 1.0. */
	public static final float DEFAULT_AMBIENT = 1.1523438F;

	/**
	 * Converts the region's ambient multiplier into the shader's additive share.
	 *
	 * <p>The stock ambient is a multiplier on the whole light model (its default
	 * 1.1523438 is what {@code Class10.method127} hands {@code GlToolkit.B}), so
	 * using it directly as an additive term would double the scene's ambient at
	 * the first lit region. This is the row's one presentation constant: the
	 * region adds at most a third of the ambient term it already has, scaled by
	 * how much darker or brighter the map made that region.
	 */
	public static final float GAIN = 0.35F;

	/** The final additive share is clamped to this, so no region can dominate the sun. */
	public static final float MAX_STRENGTH = 1.0F;

	private final int minX;

	private final int minZ;

	private final int maxX;

	private final int maxZ;

	private final int rgb;

	private final float strength;

	public HdRoomLight(int minX, int minZ, int maxX, int maxZ, int rgb, float strength) {
		this.minX = minX;
		this.minZ = minZ;
		this.maxX = maxX;
		this.maxZ = maxZ;
		this.rgb = rgb & 0xFFFFFF;
		this.strength = strength < 0.0F ? 0.0F : (strength > MAX_STRENGTH ? MAX_STRENGTH : strength);
	}

	/** True when {@code x/z} is inside this region; the shader uses the same test. */
	public boolean contains(float x, float z) {
		return x >= this.minX && x < this.maxX && z >= this.minZ && z < this.maxZ;
	}

	public int minX() {
		return this.minX;
	}

	public int minZ() {
		return this.minZ;
	}

	public int maxX() {
		return this.maxX;
	}

	public int maxZ() {
		return this.maxZ;
	}

	public int rgb() {
		return this.rgb;
	}

	public float strength() {
		return this.strength;
	}

	public float red() {
		return (float) (this.rgb >> 16 & 0xFF) / 255.0F;
	}

	public float green() {
		return (float) (this.rgb >> 8 & 0xFF) / 255.0F;
	}

	public float blue() {
		return (float) (this.rgb & 0xFF) / 255.0F;
	}

	/** True when the region would contribute nothing, so it never reaches the shader. */
	public boolean isDark() {
		return this.strength <= 0.0F;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HdRoomLight)) {
			return false;
		}
		HdRoomLight light = (HdRoomLight) other;
		return this.minX == light.minX && this.minZ == light.minZ
			&& this.maxX == light.maxX && this.maxZ == light.maxZ
			&& this.rgb == light.rgb
			&& Float.floatToIntBits(this.strength) == Float.floatToIntBits(light.strength);
	}

	@Override
	public int hashCode() {
		int hash = this.minX;
		hash = hash * 31 + this.minZ;
		hash = hash * 31 + this.maxX;
		hash = hash * 31 + this.maxZ;
		hash = hash * 31 + this.rgb;
		return hash * 31 + Float.floatToIntBits(this.strength);
	}

	@Override
	public String toString() {
		return "room[" + this.minX + ".." + this.maxX + " x " + this.minZ + ".." + this.maxZ
			+ " rgb=" + Integer.toHexString(this.rgb) + " s=" + this.strength + "]";
	}
}
