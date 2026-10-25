package com.jagex.game.runetek5.client.hd.sky;

/**
 * The four colours a 592HD frame's sky is made of, derived from the lighting
 * the scene already holds.
 *
 * <p>There is no sky palette in this class, and no light level either. Every
 * colour is a function of two the stock renderer already uploaded, <b>as
 * {@code GlToolkit} has already dimmed them for this frame</b>:
 *
 * <ul>
 * <li>{@code sunColour} - the sun's diffuse colour, the same value
 * {@code GlToolkit.scaleHeight} hands to {@code GL_LIGHT0} and the per-pixel
 * lighting shader lights surfaces with, so the disc in the sky is literally the
 * colour the light is;</li>
 * <li>{@code fogColour} - the fog band's colour, which is the colour distant
 * geometry fades into, so a horizon made of it meets the world without a seam
 * whatever a level's palette says.</li>
 * </ul>
 *
 * <p><b>The level belongs to {@link HdDayNight}.</b> The toolkit multiplies the
 * sun and fog colours by its factors before this class sees them, so the dome
 * cannot drift from the world: it inherits whatever the world was lit with.
 * This class adds only the shape - a daylight-blue bias on the zenith, and a
 * mixing weight that leans the below-horizon fill toward the sun's colour by
 * day. With every row on and the clock at noon the horizon is the fog colour
 * <em>exactly</em>; at midnight it is the dimmed fog colour, exactly.
 *
 * <p>These formulas are the fragment shader's, restated in Java so a test can
 * predict a pixel instead of recording one. The render path uploads the four
 * colours and lets the shader interpolate between them; it does not evaluate
 * anything here.
 */
public final class HdSkyColours {

	/** Daylight's blue shift on the zenith: a shape, not a colour. */
	public static final float ZENITH_SHIFT_R = 0.0F;

	public static final float ZENITH_SHIFT_G = 0.05F;

	public static final float ZENITH_SHIFT_B = 0.13F;

	/** How far the below-horizon fill leans toward the sun's colour by day. */
	public static final float AMBIENT_SUN_MIX = 0.35F;

	/** Cosine of the sun's angular radius: the disc's edge, ~1.6 degrees. */
	public static final float SUN_COS_INNER = 0.99960F;

	/** Cosine where the disc's soft edge begins, ~2.2 degrees. */
	public static final float SUN_COS_OUTER = 0.99926F;

	/** Strength of the glow around the disc. */
	public static final float SUN_GLOW = 0.35F;

	/** Every row off: no sky at all, and the value GL's zeroed uniforms describe. */
	public static final HdSkyColours OFF = new HdSkyColours(false, false, 0.0F,
		new float[3], new float[3], new float[3], new float[3]);

	private final boolean sky;

	private final boolean dayNight;

	private final float daylight;

	private final float[] zenith;

	private final float[] horizon;

	private final float[] ambient;

	private final float[] sun;

	private HdSkyColours(boolean sky, boolean dayNight, float daylight, float[] zenith, float[] horizon,
			float[] ambient, float[] sun) {
		this.sky = sky;
		this.dayNight = dayNight;
		this.daylight = daylight;
		this.zenith = zenith;
		this.horizon = horizon;
		this.ambient = ambient;
		this.sun = sun;
	}

	/**
	 * The colours for the current settings and lighting state.
	 *
	 * @param hdEnabled      whether 592HD is on at all
	 * @param skyRow         the Sky row
	 * @param dayNightRow    the Day and night row
	 * @param sunElevation   the lighting sun's height, 1 straight up to -1 straight
	 *                       down; the caller negates the scene's downward y
	 * @param phase          the day/night clock, in {@code [0, 1)}
	 * @param sunColourRgb   the sun's diffuse colour after this frame's dimming,
	 *                       {@code 0xRRGGBB}
	 * @param fogColourRgb   the fog band's colour after this frame's dimming,
	 *                       {@code 0xRRGGBB}
	 */
	public static HdSkyColours resolve(boolean hdEnabled, boolean skyRow, boolean dayNightRow,
			float sunElevation, float phase, int sunColourRgb, int fogColourRgb) {
		if (!SkyGate.skyRequested(hdEnabled, skyRow)) {
			return OFF;
		}
		float daylight = HdDayNight.daylight(sunElevation, phase, dayNightRow);
		float[] horizon = rgb(fogColourRgb);
		float[] sun = rgb(sunColourRgb);
		float[] zenith = {
			clamp01(horizon[0] + ZENITH_SHIFT_R * daylight),
			clamp01(horizon[1] + ZENITH_SHIFT_G * daylight),
			clamp01(horizon[2] + ZENITH_SHIFT_B * daylight)
		};
		float mix = AMBIENT_SUN_MIX * daylight;
		float[] ambient = {
			clamp01(horizon[0] + (sun[0] - horizon[0]) * mix),
			clamp01(horizon[1] + (sun[1] - horizon[1]) * mix),
			clamp01(horizon[2] + (sun[2] - horizon[2]) * mix)
		};
		return new HdSkyColours(true, dayNightRow, daylight, zenith, horizon, ambient, sun);
	}

	private static float[] rgb(int colour) {
		return new float[] {
			((colour >> 16) & 0xFF) / 255.0F,
			((colour >> 8) & 0xFF) / 255.0F,
			(colour & 0xFF) / 255.0F
		};
	}

	/** True when the rows allowed a sky: false means no draw at all. */
	public boolean sky() {
		return this.sky;
	}

	public boolean dayNight() {
		return this.dayNight;
	}

	public float daylight() {
		return this.daylight;
	}

	/** Zenith colour, {@code {r, g, b}} in {@code [0, 1]}; never modified after construction. */
	public float[] zenith() {
		return this.zenith;
	}

	/** Horizon colour: the fog band's colour, at the level the world already uses. */
	public float[] horizon() {
		return this.horizon;
	}

	/** Below-horizon fill, between the horizon and the sun's colour. */
	public float[] ambient() {
		return this.ambient;
	}

	/** The disc and glow colour: the sun's own light colour, at the world's level. */
	public float[] sun() {
		return this.sun;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HdSkyColours)) {
			return false;
		}
		HdSkyColours colours = (HdSkyColours) other;
		return colours.sky == this.sky && colours.dayNight == this.dayNight
			&& Float.compare(colours.daylight, this.daylight) == 0
			&& java.util.Arrays.equals(colours.zenith, this.zenith)
			&& java.util.Arrays.equals(colours.horizon, this.horizon)
			&& java.util.Arrays.equals(colours.ambient, this.ambient)
			&& java.util.Arrays.equals(colours.sun, this.sun);
	}

	@Override
	public int hashCode() {
		int hash = (this.sky ? 1 : 0) * 31 + (this.dayNight ? 1 : 0);
		hash = hash * 31 + Float.floatToIntBits(this.daylight);
		hash = hash * 31 + java.util.Arrays.hashCode(this.zenith);
		hash = hash * 31 + java.util.Arrays.hashCode(this.horizon);
		hash = hash * 31 + java.util.Arrays.hashCode(this.ambient);
		return hash * 31 + java.util.Arrays.hashCode(this.sun);
	}

	@Override
	public String toString() {
		return "sky=" + this.sky + " dayNight=" + this.dayNight + " daylight=" + this.daylight
			+ " zenith=" + java.util.Arrays.toString(this.zenith)
			+ " horizon=" + java.util.Arrays.toString(this.horizon)
			+ " ambient=" + java.util.Arrays.toString(this.ambient)
			+ " sun=" + java.util.Arrays.toString(this.sun);
	}

	/** GLSL's {@code smoothstep}; owned by {@link HdDayNight}, forwarded for the shader mirror. */
	public static float smoothstep(float edge0, float edge1, float value) {
		return HdDayNight.smoothstep(edge0, edge1, value);
	}

	public static float clamp01(float value) {
		return HdDayNight.clamp01(value);
	}
}
