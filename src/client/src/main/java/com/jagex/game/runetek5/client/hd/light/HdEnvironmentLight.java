package com.jagex.game.runetek5.client.hd.light;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;

/**
 * The environment's own ambient light for one draw: the colour the world fades
 * into (the sky/fog colour the stock renderer uploads with
 * {@code GlToolkit.UA}), the level ambient the map asks for, and the eye-space
 * direction the sky is in for this frame's camera.
 *
 * <p>The source is the client's, not a new one. A map region's environment
 * arrives as a per-chunk {@code TileLighting}: {@code OpenGl2DTexture.method4194}
 * publishes its sun colour, intensity, fill, direction and sky colour, then
 * {@code Class10.method127} and {@code FixedFunctionSpecularPass.method4231}
 * push them into the toolkit - {@code scaleHeight} for the sun, {@code B} for
 * the ambient and {@code UA} for the sky/fog colour. Those three fields are what
 * this snapshot reads, so moving between two areas with different environments
 * changes it with no extra plumbing, and an area that has not set an
 * environment yet (the toolkit's fields still zero) yields {@link #OFF}.
 *
 * <p>The row's missing datum is the ambient's <em>colour</em>. The stock light
 * model uploads the level ambient tinted by the <b>sun's</b> colour
 * ({@code GlToolkit.method1961}), so the sky colour never reaches the lighting
 * at all - it is only the fog band's colour. With this row on, a surface facing
 * the sky takes the level ambient in the sky's colour instead; surfaces facing
 * away keep the stock value, because this client's environment table has no
 * ground colour to offer them and inventing one would be a second source.
 *
 * <p>The sun's colour, intensity and direction are deliberately not duplicated
 * here. They are already the environment's own upload ({@code scaleHeight} sets
 * {@code GL_LIGHT0/1}'s colour, intensity and direction), and the per-pixel
 * shader reads them from that GL state, so a scene lit by this row and a scene
 * lit by fixed function cannot disagree about the sun. Carrying them again
 * would only create a second place for them to be wrong.
 *
 * <p>Off is the identity: the pipeline uploads nothing, the shader keeps
 * {@code gl_LightModel.ambient} untouched, and the frame is the stock one.
 *
 * <p>Two boundaries are deliberate. The colour read is the scene's own sky,
 * not the underwater band's: the stock light model does not change when the
 * camera goes under water, so neither does this row - the water veil stays a
 * fog rather than becoming an ambient source. And a black sky is the toolkit's
 * unset value (the field's initial state, and the veil the stock light-splat
 * pass writes), so it is no environment at all rather than an environment
 * asking for black light.
 */
public final class HdEnvironmentLight {

	/** No sky to light with: the identity, and the state GL's zeroed uniforms describe. */
	public static final HdEnvironmentLight OFF = new HdEnvironmentLight(false, 0.0F, 0.0F, 0.0F, 0.0F,
		0.0F, 1.0F, 0.0F);

	private final boolean sky;

	private final float red;

	private final float green;

	private final float blue;

	private final float ambient;

	private final float upX;

	private final float upY;

	private final float upZ;

	private HdEnvironmentLight(boolean sky, float red, float green, float blue, float ambient,
			float upX, float upY, float upZ) {
		this.sky = sky;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.ambient = ambient;
		this.upX = upX;
		this.upY = upY;
		this.upZ = upZ;
	}

	/**
	 * True when the saved settings let this row act at all.
	 *
	 * <p>Same precedence as the carried and room rows: the term only exists
	 * inside the per-pixel program, so 592HD or Per-pixel lighting off means the
	 * row cannot contribute regardless of its own value.
	 */
	public static boolean requested() {
		return requested(HdSettings.isEnabled(),
			HdSettings.value(HdFeature.ENVIRONMENT_LIGHTING) != 0,
			HdSettings.value(HdFeature.PER_PIXEL_LIGHTING) != 0);
	}

	/** The pure settings predicate, for the truth table. */
	public static boolean requested(boolean hdEnabled, boolean environmentRow, boolean perPixelLighting) {
		return hdEnabled && perPixelLighting && environmentRow;
	}

	/**
	 * The frame's environment light.
	 *
	 * @param skyColourRgb  the sky/fog colour the environment set, {@code 0xRRGGBB}
	 * @param levelAmbient  the level ambient after the hour's dimming
	 * @param upX           the eye-space direction of the world's up axis
	 * @param upY           its y
	 * @param upZ           its z
	 * @return {@link #OFF} when there is no environment to light with
	 */
	public static HdEnvironmentLight of(int skyColourRgb, float levelAmbient, float upX, float upY, float upZ) {
		int rgb = skyColourRgb & 0xFFFFFF;
		// A black sky is the toolkit's "no colour set" value: the field's initial
		// state, and what the stock light-splat pass writes while it veils the
		// scene. It is not an environment asking for black ambient light.
		if (levelAmbient <= 0.0F || rgb == 0) {
			return OFF;
		}
		float length = (float) Math.sqrt((double) (upX * upX + upY * upY + upZ * upZ));
		if (!(length > 1.0E-6F)) {
			// A degenerate camera basis cannot say where the sky is; world up is
			// the honest fallback rather than a zero vector the shader would
			// normalise into NaNs.
			return new HdEnvironmentLight(true, channel(rgb, 16), channel(rgb, 8), channel(rgb, 0),
				levelAmbient, 0.0F, 1.0F, 0.0F);
		}
		return new HdEnvironmentLight(true, channel(rgb, 16), channel(rgb, 8), channel(rgb, 0),
			levelAmbient, upX / length, upY / length, upZ / length);
	}

	private static float channel(int rgb, int shift) {
		return (float) (rgb >> shift & 0xFF) / 255.0F;
	}

	/** True when this frame has a sky to light with. */
	public boolean sky() {
		return this.sky;
	}

	/** True when the row would change nothing: no sky, no upload. */
	public boolean isIdentity() {
		return !this.sky;
	}

	public float red() {
		return this.red;
	}

	public float green() {
		return this.green;
	}

	public float blue() {
		return this.blue;
	}

	/** The level ambient the map asked for, after the hour's dimming. */
	public float ambient() {
		return this.ambient;
	}

	public float upX() {
		return this.upX;
	}

	public float upY() {
		return this.upY;
	}

	public float upZ() {
		return this.upZ;
	}

	/** The sky's ambient red: the shader's {@code uEnvSky.r * uEnvSky.a}. */
	public float ambientRed() {
		return this.sky ? this.red * this.ambient : 0.0F;
	}

	public float ambientGreen() {
		return this.sky ? this.green * this.ambient : 0.0F;
	}

	public float ambientBlue() {
		return this.sky ? this.blue * this.ambient : 0.0F;
	}

	/**
	 * How much of the sky a surface sees: {@code max(dot(normal, up), 0)}.
	 *
	 * <p>0 at the horizon and below (the stock ambient stands, because the
	 * environment table has no ground colour), 1 for a surface looking straight
	 * up. The normal is normalised here exactly as the shader normalises
	 * {@code vEyeNormal}, so a prediction and a pixel are the same function.
	 */
	public float skyWeight(float normalX, float normalY, float normalZ) {
		if (!this.sky) {
			return 0.0F;
		}
		float length = (float) Math.sqrt((double) (normalX * normalX + normalY * normalY + normalZ * normalZ));
		if (!(length > 1.0E-6F)) {
			return 0.0F;
		}
		float dot = (normalX * this.upX + normalY * this.upY + normalZ * this.upZ) / length;
		if (dot <= 0.0F) {
			return 0.0F;
		}
		return dot >= 1.0F ? 1.0F : dot;
	}

	/** The shader's {@code mix(stock, sky, weight)}, restated for prediction. */
	public static float ambientChannel(float stockChannel, float skyChannel, float weight) {
		return stockChannel + (skyChannel - stockChannel) * weight;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HdEnvironmentLight)) {
			return false;
		}
		HdEnvironmentLight light = (HdEnvironmentLight) other;
		return light.sky == this.sky && Float.compare(light.red, this.red) == 0
			&& Float.compare(light.green, this.green) == 0 && Float.compare(light.blue, this.blue) == 0
			&& Float.compare(light.ambient, this.ambient) == 0 && Float.compare(light.upX, this.upX) == 0
			&& Float.compare(light.upY, this.upY) == 0 && Float.compare(light.upZ, this.upZ) == 0;
	}

	@Override
	public int hashCode() {
		int hash = this.sky ? 1 : 0;
		hash = hash * 31 + Float.floatToIntBits(this.red);
		hash = hash * 31 + Float.floatToIntBits(this.green);
		hash = hash * 31 + Float.floatToIntBits(this.blue);
		hash = hash * 31 + Float.floatToIntBits(this.ambient);
		hash = hash * 31 + Float.floatToIntBits(this.upX);
		hash = hash * 31 + Float.floatToIntBits(this.upY);
		return hash * 31 + Float.floatToIntBits(this.upZ);
	}

	@Override
	public String toString() {
		return "sky=" + this.sky + " colour=" + this.red + "/" + this.green + "/" + this.blue
			+ " ambient=" + this.ambient + " up=" + this.upX + "/" + this.upY + "/" + this.upZ;
	}
}
