package com.jagex.game.runetek5.client.hd.water;

/**
 * One breaking-water material's own effect parameters, decoded the way the
 * stock effect decodes them.
 *
 * <p>A {@code MaterialType} carries an effect slot ({@code aByte89}) and two
 * parameter fields. When the slot is the breaking type
 * ({@link HdWaterGate#BREAKING_SLOT}), {@code BreakingWaterEffect.setEffectParams}
 * unpacks those two numbers into the animation rate, the texture scale, the
 * depth the breaking band forms at, its shore offset, the crest's two colour
 * values and the exponent that sharpens the band. This class is that unpacking
 * and nothing else: the same masks, the same divisors, so a material's numbers
 * mean here exactly what they mean on the stock path.
 *
 * <p>The HD breaking surface reads these as uniforms. It uses the same clock as
 * every other surface in the row ({@link HdWaterTime#seconds()}, advanced by
 * {@code FramePresentation}); the stock effect instead scrolls a 3D normal
 * volume with a 40000-step counter, and this client's HD programs do not sample
 * that volume - the row's own wave field is the surface. So the parameters are
 * carried as the facts they are:
 *
 * <ul>
 * <li>{@code speed} - the stock {@code time} multiplier, {@code 1 << (bits 0-1)};
 * it scales the row's clock, so the default 1 animates at the row's rate and a
 * larger value is faster;</li>
 * <li>{@code textureScale} - {@code (1 << (bits 3-5)) / 32}, multiplied into the
 * texture coordinates exactly as the stock vertex stage does;</li>
 * <li>{@code depth} - the bottom sixteen bits, the depth the breaking band forms
 * at, divided into the per-vertex water depth;</li>
 * <li>{@code offset} - bits 16-17 over 8, the crest mask's bias on that
 * ratio;</li>
 * <li>{@code intensityX} / {@code intensityY} - bits 23-26 and 19-22 over 16,
 * the two values the stock stage uses as the crest's colour;</li>
 * <li>{@code exponent} - bits 27-30, the power the band is sharpened by.</li>
 * </ul>
 *
 * <p>{@link #NONE} is the decode of an absent pair and is what a draw that is
 * not the breaking type carries; it is never uploaded because nothing selects
 * the breaking surface without real parameters.
 */
public final class HdWaterBreaking {

	/** No breaking material: the decode of two zero fields, used by other draws. */
	public static final HdWaterBreaking NONE = new HdWaterBreaking(0, 0);

	private final int speed;

	private final float textureScale;

	private final float depth;

	private final float offset;

	private final float intensityX;

	private final float intensityY;

	private final float exponent;

	private HdWaterBreaking(int speedAndScale, int packed) {
		this.speed = 1 << (speedAndScale & 0x3);
		this.textureScale = (float) (1 << (speedAndScale >> 3 & 0x7)) / 32.0F;
		this.depth = (float) (packed & 0xFFFF);
		this.offset = (float) (packed >> 16 & 0x3) / 8.0F;
		this.intensityY = (float) (packed >> 19 & 0xF) / 16.0F;
		this.intensityX = (float) (packed >> 23 & 0xF) / 16.0F;
		this.exponent = (float) (packed >> 27 & 0xF);
	}

	/**
	 * The parameters a breaking-water material carries.
	 *
	 * <p>The stock call is {@code setEffectParams(aByte88, anInt6933)} - the
	 * toolkit passes the two fields in that order - so the first argument here
	 * is the speed/scale byte and the second the packed depth, offset,
	 * intensity and exponent.
	 */
	public static HdWaterBreaking of(byte speedAndScale, int packed) {
		return new HdWaterBreaking(speedAndScale, packed);
	}

	/** True when this is {@link #NONE}: no breaking material's numbers. */
	public boolean absent() {
		return this == NONE;
	}

	/** The stock {@code time} multiplier: 1 is the row's own animation rate. */
	public int speed() {
		return this.speed;
	}

	/** The texture-coordinate multiplier the stock vertex stage applies. */
	public float textureScale() {
		return this.textureScale;
	}

	/** The depth the breaking band forms at, in the water depth's own units. */
	public float depth() {
		return this.depth;
	}

	/** The crest mask's bias on the depth ratio; 0 disables it. */
	public float offset() {
		return this.offset;
	}

	/** The crest colour's first value: the stock {@code waveIntensity.x}. */
	public float intensityX() {
		return this.intensityX;
	}

	/** The crest colour's second value: the stock {@code waveIntensity.y}. */
	public float intensityY() {
		return this.intensityY;
	}

	/** The power the breaking band is sharpened by; 0 leaves no band. */
	public float exponent() {
		return this.exponent;
	}

	/**
	 * True when both decode to the same numbers. The pipeline re-uploads a
	 * breaking draw's uniforms only when a material actually changes them, so
	 * two tiles with the same water cost one upload.
	 */
	public boolean sameAs(HdWaterBreaking other) {
		return other != null
			&& this.speed == other.speed
			&& this.textureScale == other.textureScale
			&& this.depth == other.depth
			&& this.offset == other.offset
			&& this.intensityX == other.intensityX
			&& this.intensityY == other.intensityY
			&& this.exponent == other.exponent;
	}

	@Override
	public String toString() {
		return "breaking[speed=" + this.speed + ", scale=" + this.textureScale
			+ ", depth=" + this.depth + ", offset=" + this.offset
			+ ", intensity=" + this.intensityX + "/" + this.intensityY
			+ ", exponent=" + this.exponent + "]";
	}
}
