package com.jagex.game.runetek5.client.hd.post;

import com.jagex.game.runetek5.client.hd.HdFeature;

/**
 * The values one screen-space pass is run with: which rows are on, and the
 * constants that give the look.
 *
 * <p>Immutable and built from {@link HdPostGate}, so the pixel harness can run
 * the pass with any combination without touching settings files, and the game
 * path cannot drift from what the tests prove.
 *
 * <p>The look is deliberately conservative, because these are the rows a player
 * turns on expecting the frame to stay recognisable:
 *
 * <ul>
 * <li><b>Tonemap</b> is a shoulder, not a curve over the whole range: nothing
 * below {@link #KNEE} changes, so mid tones and the interface keep their
 * levels, and only highlights roll off toward white. Applying Reinhard to a
 * display-space frame would darken the entire game.</li>
 * <li><b>Colour grading</b> is a warm lift-and-gain with a little extra
 * saturation; at the constants below a mid grey stays within a dozen levels of
 * where it was, and the shift is in hue rather than brightness.</li>
 * <li><b>Dither</b> is one 8-bit step of ordered noise, which removes the
 * banding a tonemap's shoulder creates in skies without being visible as
 * grain.</li>
 * <li><b>FXAA</b> blends only where the luma range across a pixel is wide
 * enough to be an edge, so flat areas are untouched.</li>
 * </ul>
 */
public final class HdPostParams {

	/** Exposure applied before the tonemap shoulder; 1.0 leaves levels alone. */
	public static final float EXPOSURE = 1.0F;

	/** Below this, the tonemap is the identity. */
	public static final float KNEE = 0.6F;

	/** How far the shoulder can compress above the knee, as a fraction of the range. */
	public static final float SHOULDER = 0.4F;

	/** Per-channel gain: a warm grade that keeps green fixed. */
	public static final float[] GAIN = { 1.05F, 1.0F, 0.94F };

	/** Per-channel lift; zero keeps black at black. */
	public static final float[] LIFT = { 0.0F, 0.0F, 0.0F };

	/** Saturation multiplier applied after gain and lift. */
	public static final float SATURATION = 1.08F;

	/** Dither amplitude in colour units: exactly one 8-bit step. */
	public static final float DITHER_AMOUNT = 1.0F / 255.0F;

	/** Luma range below which FXAA leaves a pixel alone. */
	public static final float FXAA_THRESHOLD = 0.08F;

	/** The most of the neighbourhood average FXAA may blend in. */
	public static final float FXAA_STRENGTH = 0.75F;

	/** A pass with every row off; {@link #any()} is false. */
	public static final HdPostParams OFF = new HdPostParams(false, false, false, false,
		EXPOSURE, GAIN, LIFT, SATURATION, DITHER_AMOUNT);

	private final boolean tonemap;

	private final boolean grading;

	private final boolean dither;

	private final boolean fxaa;

	private final float exposure;

	private final float[] gain;

	private final float[] lift;

	private final float saturation;

	private final float ditherAmount;

	public HdPostParams(boolean tonemap, boolean grading, boolean dither, boolean fxaa,
			float exposure, float[] gain, float[] lift, float saturation, float ditherAmount) {
		this.tonemap = tonemap;
		this.grading = grading;
		this.dither = dither;
		this.fxaa = fxaa;
		this.exposure = exposure;
		this.gain = gain.clone();
		this.lift = lift.clone();
		this.saturation = saturation;
		this.ditherAmount = ditherAmount;
	}

	/** The pass the stored settings ask for, through {@link HdPostGate}. */
	public static HdPostParams fromSettings() {
		return new HdPostParams(
			HdPostGate.requested(HdFeature.TONEMAP),
			HdPostGate.requested(HdFeature.COLOUR_GRADING),
			HdPostGate.requested(HdFeature.DITHER),
			HdPostGate.requested(HdFeature.FXAA),
			EXPOSURE, GAIN, LIFT, SATURATION, DITHER_AMOUNT);
	}

	/** True when at least one row is on, so the pass is worth a frame copy. */
	public boolean any() {
		return this.tonemap || this.grading || this.dither || this.fxaa;
	}

	public boolean tonemap() {
		return this.tonemap;
	}

	public boolean grading() {
		return this.grading;
	}

	public boolean dither() {
		return this.dither;
	}

	public boolean fxaa() {
		return this.fxaa;
	}

	public float exposure() {
		return this.exposure;
	}

	public float[] gain() {
		return this.gain.clone();
	}

	public float[] lift() {
		return this.lift.clone();
	}

	public float saturation() {
		return this.saturation;
	}

	/** Where the tonemap shoulder begins; below it the curve is the identity. */
	public float knee() {
		return KNEE;
	}

	/** How far the shoulder compresses above the knee. */
	public float shoulder() {
		return SHOULDER;
	}

	/**
	 * The dither the shader applies: zero when the row is off, so the branch is
	 * a multiply rather than a texture read.
	 */
	public float ditherAmount() {
		return this.dither ? this.ditherAmount : 0.0F;
	}

	/** 1 when the tonemap is on, 0 otherwise; the shader's branch flag. */
	public float tonemapFlag() {
		return this.tonemap ? 1.0F : 0.0F;
	}

	/** 1 when grading is on, 0 otherwise. */
	public float gradingFlag() {
		return this.grading ? 1.0F : 0.0F;
	}

	/** 1 when FXAA is on, 0 otherwise. */
	public float fxaaFlag() {
		return this.fxaa ? 1.0F : 0.0F;
	}
}
