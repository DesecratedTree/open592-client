package com.jagex.game.runetek5.client.hd.sky;

/**
 * What the hour does to the light the world is drawn with.
 *
 * <p>The clock is {@link HdSkyTime}'s; this class is the policy. It turns one
 * {@code daylight} number into five scale factors, and every lighting path in
 * the toolkit multiplies by one of them:
 *
 * <ul>
 * <li><b>sun</b> - the sun's colour, so {@code GL_LIGHT0}'s diffuse and the
 * dome's disc dim together;</li>
 * <li><b>fill</b> - the anti-sun light's brightness, the shadowed side's only
 * other light;</li>
 * <li><b>ambient</b> - the global ambient brightness, which is what stops a
 * night scene from being black;</li>
 * <li><b>fog</b> - the colour distant geometry fades into, which is also the
 * dome's horizon, so the two cannot disagree;</li>
 * <li><b>shadow</b> - how much of the sun's shadow survives: at night a
 * shadowed surface looks much like a lit one, so the strength falls toward
 * no-shadow rather than toward black.</li>
 * </ul>
 *
 * <p><b>The sun's direction is never touched.</b> It comes from the scene's
 * lighting ({@code GlToolkit.scaleHeight}), because that is the direction the
 * shadow map, the point lights and the sky's disc all have to agree on. The
 * clock owns intensity and colour, not geometry.
 *
 * <p><b>Off is 1.0, exactly.</b> With 592HD off, the Day and night row off, or
 * no day/night at all, every factor is exactly {@code 1.0F}: multiplying a
 * colour by 1.0F returns the same int (see {@link #scale}), so the stock frame
 * is bit-identical rather than merely close.
 *
 * <p>Deliberately pure: no GL, no settings reads. The caller resolves the gate
 * once and hands in the daylight inputs.
 */
public final class HdDayNight {

	/** Sun elevation at which daylight starts to appear. */
	public static final float ELEVATION_LOW = -0.02F;

	/** Sun elevation at which the light's sun counts as fully up. */
	public static final float ELEVATION_HIGH = 0.18F;

	/** Clock height, {@code sin(2*PI*(phase - 0.25))}, where dawn begins. */
	public static final float CLOCK_LOW = 0.0F;

	/** Clock height that counts as full day: a fifth of the way to noon. */
	public static final float CLOCK_HIGH = 0.30F;

	/** How much of the fog colour survives at midnight; the dome's horizon too. */
	public static final float FOG_NIGHT_LEVEL = 0.22F;

	/** How much of the sun's colour survives at midnight. */
	public static final float SUN_NIGHT_LEVEL = 0.35F;

	/** How much of the anti-sun fill survives at midnight. */
	public static final float FILL_NIGHT_LEVEL = 0.45F;

	/** How much of the global ambient survives at midnight. */
	public static final float AMBIENT_NIGHT_LEVEL = 0.30F;

	/**
	 * The fraction of the sun's shadow that survives at midnight: shadows fade
	 * toward "lit", never toward a second darkness.
	 */
	public static final float SHADOW_NIGHT_LEVEL = 0.35F;

	/** Every factor 1: the stock light, whatever the rows say. */
	public static final Factors IDENTITY = new Factors(1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

	private HdDayNight() {
	}

	/**
	 * The factors for this frame.
	 *
	 * @param hdEnabled    whether 592HD is on at all
	 * @param dayNightRow  the Day and night row
	 * @param sunElevation the lighting sun's height, 1 straight up; the caller
	 *                     negates the world's downward y
	 * @param phase        the clock, in {@code [0, 1)}
	 */
	public static Factors factors(boolean hdEnabled, boolean dayNightRow, float sunElevation, float phase) {
		if (!hdEnabled || !dayNightRow) {
			return IDENTITY;
		}
		return factors(daylight(sunElevation, phase, true));
	}

	/** The factors for an already-resolved daylight amount. */
	public static Factors factors(float daylight) {
		if (daylight >= 1.0F) {
			return IDENTITY;
		}
		float level = clamp01(daylight);
		return new Factors(level,
			scale(SUN_NIGHT_LEVEL, level),
			scale(FILL_NIGHT_LEVEL, level),
			scale(AMBIENT_NIGHT_LEVEL, level),
			scale(FOG_NIGHT_LEVEL, level),
			scale(SHADOW_NIGHT_LEVEL, level));
	}

	/**
	 * How much daylight this frame has: 0 at night, 1 in full day.
	 *
	 * <p>{@link #sunElevationDaylight} is the scene's own sun; when the clock is
	 * off it is the whole answer, which is why a world with no Day and night row
	 * is stable. When the clock is on, the two multiply, so the row can dim the
	 * scene but never argues with a sun the lighting has already set below the
	 * horizon.
	 */
	public static float daylight(float sunElevation, float phase, boolean dayNightRow) {
		float elevation = sunElevationDaylight(sunElevation);
		if (!dayNightRow) {
			return elevation;
		}
		return elevation * clockDaylight(phase);
	}

	/** Daylight implied by where the lighting has put the sun. */
	public static float sunElevationDaylight(float sunElevation) {
		return smoothstep(ELEVATION_LOW, ELEVATION_HIGH, sunElevation);
	}

	/** Daylight implied by the clock alone: dawn and dusk share a soft edge. */
	public static float clockDaylight(float phase) {
		double height = Math.sin(2.0D * Math.PI * (phase - 0.25D));
		return smoothstep(CLOCK_LOW, CLOCK_HIGH, (float) height);
	}

	/**
	 * Scales an {@code 0xRRGGBB} colour by a factor.
	 *
	 * <p>{@code 1.0F} is exact, not approximate: it returns the argument
	 * unchanged, which is what makes the row-off path byte-identical instead of
	 * one rounding step away from it.
	 *
	 * <p>The result is quantised to 8-bit channels, which is also what keeps a
	 * moving clock cheap: the toolkit's setters compare the value they are handed
	 * against the last one and re-upload only when it differs, so a sun that creeps
	 * by a fraction of a channel issues no GL call at all.
	 */
	public static int scale(int rgb, float factor) {
		if (factor >= 1.0F) {
			return rgb;
		}
		float level = factor <= 0.0F ? 0.0F : factor;
		int red = scaleChannel((rgb >> 16) & 0xFF, level);
		int green = scaleChannel((rgb >> 8) & 0xFF, level);
		int blue = scaleChannel(rgb & 0xFF, level);
		return red << 16 | green << 8 | blue;
	}

	private static int scaleChannel(int channel, float level) {
		int value = Math.round(channel * level);
		return value < 0 ? 0 : (value > 255 ? 255 : value);
	}

	/** {@code first + (second - first) * level}, the interpolation the levels use. */
	private static float scale(float nightLevel, float daylight) {
		return nightLevel + (1.0F - nightLevel) * daylight;
	}

	/** GLSL's {@code smoothstep}, so the Java model and the shader agree. */
	public static float smoothstep(float edge0, float edge1, float value) {
		if (edge1 <= edge0) {
			return value < edge0 ? 0.0F : 1.0F;
		}
		float t = clamp01((value - edge0) / (edge1 - edge0));
		return t * t * (3.0F - 2.0F * t);
	}

	public static float clamp01(float value) {
		if (value < 0.0F) {
			return 0.0F;
		}
		return value > 1.0F ? 1.0F : value;
	}

	/**
	 * The five multipliers for one frame. Immutable; {@link #IDENTITY} is the
	 * stock light.
	 */
	public static final class Factors {

		private final float daylight;

		private final float sun;

		private final float fill;

		private final float ambient;

		private final float fog;

		private final float shadow;

		private Factors(float daylight, float sun, float fill, float ambient, float fog, float shadow) {
			this.daylight = daylight;
			this.sun = sun;
			this.fill = fill;
			this.ambient = ambient;
			this.fog = fog;
			this.shadow = shadow;
		}

		/** 0 at night, 1 in full day. */
		public float daylight() {
			return this.daylight;
		}

		/** Multiplier for the sun's colour, and so the dome's disc. */
		public float sun() {
			return this.sun;
		}

		/** Multiplier for the anti-sun light's brightness. */
		public float fill() {
			return this.fill;
		}

		/** Multiplier for the global ambient brightness. */
		public float ambient() {
			return this.ambient;
		}

		/** Multiplier for the fog colour, and so the dome's horizon. */
		public float fog() {
			return this.fog;
		}

		/** Fraction of the sun's shadow that survives; 1 is full shadow. */
		public float shadow() {
			return this.shadow;
		}

		/** True when every factor is 1: the stock frame, bit for bit. */
		public boolean isIdentity() {
			return this.sun == 1.0F && this.fill == 1.0F && this.ambient == 1.0F
				&& this.fog == 1.0F && this.shadow == 1.0F;
		}

		/** The sun colour as this frame should light with. */
		public int sunColour(int rgb) {
			return scale(rgb, this.sun);
		}

		/** The fog colour as this frame should fade into. */
		public int fogColour(int rgb) {
			return scale(rgb, this.fog);
		}
	}
}
