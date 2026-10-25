package com.jagex.game.runetek5.client.hd.reflect;

/**
 * One draw's environment reflection: how much of the client's own environment
 * cube reaches the surface, as the value the fragment stage reads.
 *
 * <p>The row alone is not enough to reflect a surface. The stock client already
 * decides which materials reflect: a material's texture-effect slot
 * ({@code MaterialType.aByte89}) selects an effect in {@code EffectManagerGL},
 * and slot {@link #SPECULAR_SLOT} is {@code FixedFunctionSpecularPass} - the
 * pre-filtered environment cube the stock renderer reads as a reflection, not a
 * per-material light response ({@code MaterialType} has no specular strength to
 * read). This class therefore treats that slot as the material's own claim: the
 * row says the settings want reflections, the slot says this surface is one the
 * client already considers reflective.
 *
 * <p>{@link #OFF} is the identity: strength exactly zero, which is also GL's
 * initial uniform value, so a row that is off - or a material the stock path
 * draws without the specular effect - resolves to the same value every frame
 * and the fragment stage takes no branch.
 *
 * <p>The honest limit is the texture environment: the lighting shader
 * reproduces {@code GL_MODULATE} and nothing else, so only materials whose
 * environment is {@link #MODULATE_ENVIRONMENT} may be taken from the stock
 * effect. Everything else keeps the stock pass even with the row on, rather
 * than being drawn with a different blend mode.
 */
public final class HdReflection {

	/**
	 * The stock effect slot {@code FixedFunctionSpecularPass} occupies. A
	 * material whose slot is this value is one the stock renderer already draws
	 * with the environment cube; the row replaces that ramp with a live sample of
	 * the same environment.
	 */
	public static final int SPECULAR_SLOT = 1;

	/**
	 * The material texture environment whose combine is {@code GL_MODULATE}, the
	 * only combine the lighting shader reproduces. Taking a draw from its stock
	 * effect is only safe when its environment is this one.
	 */
	public static final int MODULATE_ENVIRONMENT = 1;

	/** How much of the cube reaches the surface at full strength. */
	public static final float STRENGTH = 0.4F;

	/** The identity: no reflection, and the strength GL initialises the uniform to. */
	public static final HdReflection OFF = new HdReflection(0.0F);

	private final float strength;

	private HdReflection(float strength) {
		this.strength = strength;
	}

	/**
	 * This draw's reflection term.
	 *
	 * @param requested the Reflections row for this draw
	 * @param effectSlot the material's stock texture-effect slot
	 */
	public static HdReflection of(boolean requested, int effectSlot) {
		if (!requested || effectSlot != SPECULAR_SLOT) {
			return OFF;
		}
		return new HdReflection(STRENGTH);
	}

	/** 0 when the row is off or the material is not the reflective type. */
	public float strength() {
		return this.strength;
	}

	/** True when this draw samples the environment cube at all. */
	public boolean active() {
		return this.strength > 0.0F;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HdReflection)) {
			return false;
		}
		return ((HdReflection) other).strength == this.strength;
	}

	@Override
	public int hashCode() {
		return Float.floatToIntBits(this.strength);
	}

	@Override
	public String toString() {
		return "reflection=" + this.strength;
	}
}
