package com.jagex.game.runetek5.client.hd.atmosphere;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;

/**
 * The three atmosphere rows the per-pixel lighting shader evaluates: fog,
 * distance fade and ambient occlusion.
 *
 * <p>All three live in the fragment stage, all three are pure functions of the
 * values below, and all three share one rule: <b>off is the identity</b>. When
 * a row is off its uniform is zero, its branch is not taken, and the shader
 * emits exactly the arithmetic it emitted before the row existed - the fog band
 * stays the stock {@code GL_LINEAR} band, the far fade is absent, and the
 * ambient term is multiplied by nothing. That is why {@link #OFF} is a real
 * value and not just a flag: the pipeline compares against it and uploads
 * nothing at all while every row is off, because GL initialises the uniforms to
 * zero.
 *
 * <p>The equations below are the shader's, restated in Java so a test can
 * compute what a pixel <em>should</em> be instead of recording what it was.
 * They are not used by the render path.
 *
 * <ul>
 * <li><b>Fog</b> - the stock band is
 * {@code clamp((end - d) / (end - start))}, linear in eye distance. The HD
 * band squares that same fraction, so both ends are unchanged (no fog at the
 * band's start, full fog at its end) and the interior is strictly thicker:
 * the band builds slowly where it starts and climbs toward the far end. Off is
 * the linear band, i.e. the stock draw.</li>
 * <li><b>Distance fade</b> - the outer {@code FADE_BAND} of the projection's
 * far plane is blended the rest of the way into the fog colour, reaching
 * exactly the fog colour at the far plane. Geometry nearer than
 * {@code far * (1 - FADE_BAND)} is untouched, which is what makes the row safe
 * to leave on: nothing the player is looking at changes.</li>
 * <li><b>Ambient occlusion</b> - the sun's depth map is sampled again with a
 * coarse 3x3 kernel at triple the texel step and four times the bias. Geometry
 * in contact with an occluder is shadowed across that whole neighbourhood, so
 * the term reads as contact darkening instead of as a second sun shadow, and it
 * multiplies the <em>ambient</em> term only. There is no other occluder source
 * in this forward renderer: no depth prepass, no normal buffer, no depth
 * texture on the scene target. A draw with no shadow map therefore has
 * {@code uShadowEnabled == 0} and the occlusion branch is skipped
 * entirely - the row is a no-op without Sun shadows on, which is stated in the
 * row's own documentation rather than faked.</li>
 * </ul>
 */
public final class HdAtmosphere {

	/** Fraction of the far plane over which distance fade runs. */
	public static final float FADE_BAND = 0.15F;

	/** How much of the ambient term a fully occluded fragment loses. */
	public static final float AO_STRENGTH = 0.55F;

	/** Every row off: the identity, and the state GL's zeroed uniforms describe. */
	public static final HdAtmosphere OFF = new HdAtmosphere(false, false, false, 0.0F, 0.0F);

	private final boolean enhancedFog;

	private final boolean distanceFade;

	private final boolean ambientOcclusion;

	private final float aoStrength;

	private final float fadeBand;

	public HdAtmosphere(boolean enhancedFog, boolean distanceFade, boolean ambientOcclusion,
			float aoStrength, float fadeBand) {
		this.enhancedFog = enhancedFog;
		this.distanceFade = distanceFade;
		this.ambientOcclusion = ambientOcclusion;
		this.aoStrength = clamp01(aoStrength);
		this.fadeBand = clamp01(fadeBand);
	}

	/** The values for the current settings, read once per draw like the draw request. */
	public static HdAtmosphere current() {
		return resolve(HdSettings.isEnabled(),
			HdSettings.value(HdFeature.FOG) != 0,
			HdSettings.value(HdFeature.DISTANCE_FADE) != 0,
			HdSettings.value(HdFeature.AMBIENT_OCCLUSION) != 0);
	}

	/**
	 * The one gate: which of the three rows may act at all. 592HD off means
	 * every row is off - the shader is not bound in that case anyway, and this
	 * keeps the settings predicate honest on its own.
	 */
	public static HdAtmosphere resolve(boolean hdEnabled, boolean fogRow, boolean fadeRow, boolean aoRow) {
		if (!hdEnabled) {
			return OFF;
		}
		if (!fogRow && !fadeRow && !aoRow) {
			return OFF;
		}
		return new HdAtmosphere(fogRow, fadeRow, aoRow, AO_STRENGTH, FADE_BAND);
	}

	// -- The shader's equations, for prediction ---------------------------

	/** The stock {@code GL_LINEAR} fog fraction at {@code distance}. */
	public static float stockFogFraction(float start, float end, float distance) {
		if (end <= start) {
			return 0.0F;
		}
		return clamp01((end - distance) / (end - start));
	}

	/** The HD fog fraction: the stock fraction squared, so the band is thicker. */
	public static float fogFraction(float stockFraction) {
		float t = clamp01(stockFraction);
		return t * t;
	}

	/**
	 * 1.0 outside the fade band, 0.0 at the far plane. The far plane comes from
	 * the projection in the shader ({@code m23 / (1 + m22)}); this takes it as an
	 * argument so a test can predict the same number.
	 */
	public static float fadeFraction(float eyeDistance, float farPlane) {
		if (farPlane <= 0.0F) {
			return 1.0F;
		}
		float start = farPlane * (1.0F - FADE_BAND);
		float span = farPlane - start;
		if (span <= 0.0F) {
			return 1.0F;
		}
		return clamp01((farPlane - eyeDistance) / span);
	}

	/** The ambient multiplier for a shadow-map coverage of 0 (free) to 1 (occluded). */
	public static float ambientScale(float coverage) {
		return 1.0F - AO_STRENGTH * clamp01(coverage);
	}

	/** True when no row acts: the pipeline uploads nothing and the shader is stock. */
	public boolean isIdentity() {
		return !this.enhancedFog && !this.distanceFade && !this.ambientOcclusion;
	}

	public boolean enhancedFog() {
		return this.enhancedFog;
	}

	public boolean distanceFade() {
		return this.distanceFade;
	}

	public boolean ambientOcclusion() {
		return this.ambientOcclusion;
	}

	public float aoStrength() {
		return this.aoStrength;
	}

	public float fadeBand() {
		return this.fadeBand;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HdAtmosphere)) {
			return false;
		}
		HdAtmosphere atmosphere = (HdAtmosphere) other;
		return atmosphere.enhancedFog == this.enhancedFog
			&& atmosphere.distanceFade == this.distanceFade
			&& atmosphere.ambientOcclusion == this.ambientOcclusion
			&& Float.compare(atmosphere.aoStrength, this.aoStrength) == 0
			&& Float.compare(atmosphere.fadeBand, this.fadeBand) == 0;
	}

	@Override
	public int hashCode() {
		int hash = (this.enhancedFog ? 1 : 0) * 31 + (this.distanceFade ? 1 : 0);
		hash = hash * 31 + (this.ambientOcclusion ? 1 : 0);
		hash = hash * 31 + Float.floatToIntBits(this.aoStrength);
		return hash * 31 + Float.floatToIntBits(this.fadeBand);
	}

	@Override
	public String toString() {
		return "fog=" + this.enhancedFog + " fade=" + this.distanceFade
			+ " ao=" + this.ambientOcclusion + "/" + this.aoStrength + " band=" + this.fadeBand;
	}

	private static float clamp01(float value) {
		if (value < 0.0F) {
			return 0.0F;
		}
		return value > 1.0F ? 1.0F : value;
	}
}
