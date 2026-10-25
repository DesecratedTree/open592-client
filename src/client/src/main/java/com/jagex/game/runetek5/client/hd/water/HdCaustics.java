package com.jagex.game.runetek5.client.hd.water;

import com.jagex.game.runetek5.client.hd.ground.HdGroundWater;

/**
 * One draw's caustics term: how strong the light pattern is and where the water
 * clock stands, as the pair the fragment stage reads.
 *
 * <p>Three inputs decide it, and each is a value the caller already has: the
 * Caustics row (a {@code HdDrawRows} row, resolved from the saved settings at
 * the seam), the tile's water depth ({@link HdGroundWater}, the client's own 0..255
 * byte), and the frame's water clock ({@link HdWaterTime}). The pattern is the
 * shader's; this class only answers "how much, and when".
 *
 * <p>{@link #OFF} is the identity: strength exactly zero and clock zero, which is
 * also GL's initial uniform value. A row that is off, or a tile with no water
 * over it, therefore resolves to the same value every frame, so the pipeline
 * uploads nothing and the fragment stage takes no branch - the off state is the
 * frame before the row existed rather than a very faint pattern.
 */
public final class HdCaustics {

	/** How much of the pattern reaches the surface at full depth. */
	public static final float MAX_STRENGTH = 1.0F;

	/** The identity: no pattern, and the clock value GL starts at. */
	public static final HdCaustics OFF = new HdCaustics(0.0F, 0.0F);

	private final float strength;

	private final float seconds;

	private HdCaustics(float strength, float seconds) {
		this.strength = strength;
		this.seconds = seconds;
	}

	/**
	 * This draw's term.
	 *
	 * @param requested the Caustics row for this draw
	 * @param water     the water standing over the tile being drawn
	 * @param seconds   the frame's water clock
	 */
	public static HdCaustics of(boolean requested, HdGroundWater water, float seconds) {
		if (!requested || water == null || !water.submerged()) {
			return OFF;
		}
		return new HdCaustics(MAX_STRENGTH * water.fraction(), Math.max(0.0F, seconds));
	}

	/** 0 when the row is off or the tile is dry; depth-scaled otherwise. */
	public float strength() {
		return this.strength;
	}

	/** The water clock this draw was built at. */
	public float seconds() {
		return this.seconds;
	}

	/** True when this draw actually draws a pattern. */
	public boolean active() {
		return this.strength > 0.0F;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HdCaustics)) {
			return false;
		}
		HdCaustics caustics = (HdCaustics) other;
		return caustics.strength == this.strength && caustics.seconds == this.seconds;
	}

	@Override
	public int hashCode() {
		return Float.floatToIntBits(this.strength) * 31 + Float.floatToIntBits(this.seconds);
	}

	@Override
	public String toString() {
		return "caustics=" + this.strength + "@" + this.seconds + "s";
	}
}
