package com.jagex.game.runetek5.client.hd.light;

import com.jagex.game.runetek5.cache.node.PointLight;

/**
 * One light the per-pixel shader adds on top of the sun and the point lights:
 * a scene position, a radius, a colour and a strength.
 *
 * <p>The class exists so the carried-light row never invents its own values.
 * Every field comes from a {@link PointLight} the scene already registered
 * ({@link #of}), and the {@code capture} path in {@link HdLightFrame} only
 * moves the light's position to the carrier; colour, radius and intensity are
 * the map light's own.
 *
 * <p>Immutable and value-comparable, because the pipeline skips the uniform
 * upload when the frame it is about to draw equals the one already in the
 * program - a stationary player under a steady light issues no GL call at all.
 */
public final class HdLightSource {

	/** The light's scene position, in the client's 1/128-tile units. */
	private final float x;

	private final float y;

	private final float z;

	/** Distance at which the light reaches exactly zero, in scene units. */
	private final float radius;

	private final int rgb;

	/** The animated 0..1 fraction a {@code Class191} writes through {@code setIntensity}. */
	private final float intensity;

	public HdLightSource(float x, float y, float z, float radius, int rgb, float intensity) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius < 1.0F ? 1.0F : radius;
		this.rgb = rgb & 0xFFFFFF;
		this.intensity = intensity < 0.0F ? 0.0F : (intensity > 1.0F ? 1.0F : intensity);
	}

	/** The light as the scene defines it: same colour, same radius, same intensity. */
	public static HdLightSource of(PointLight light) {
		if (light == null) {
			return null;
		}
		return new HdLightSource(light.method3160(), light.method3161(), light.method3164(),
			light.method3165(), light.method3162(), light.getIntensity());
	}

	/** The same light moved to a carrier: only the position changes. */
	public HdLightSource at(float newX, float newY, float newZ) {
		return new HdLightSource(newX, newY, newZ, this.radius, this.rgb, this.intensity);
	}

	/**
	 * How strongly this light competes: its intensity times the brightness of
	 * its colour. A dim red torch loses to a white one of the same intensity,
	 * which is what "the strongest light on the tile" has to mean.
	 */
	public float weight() {
		return this.intensity * (float) (this.rgb >> 16 & 0xFF) * 0.299F
			+ this.intensity * (float) (this.rgb >> 8 & 0xFF) * 0.587F
			+ this.intensity * (float) (this.rgb & 0xFF) * 0.114F;
	}

	public float x() {
		return this.x;
	}

	public float y() {
		return this.y;
	}

	public float z() {
		return this.z;
	}

	public float radius() {
		return this.radius;
	}

	public int rgb() {
		return this.rgb;
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

	public float intensity() {
		return this.intensity;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HdLightSource)) {
			return false;
		}
		HdLightSource light = (HdLightSource) other;
		return Float.floatToIntBits(this.x) == Float.floatToIntBits(light.x)
			&& Float.floatToIntBits(this.y) == Float.floatToIntBits(light.y)
			&& Float.floatToIntBits(this.z) == Float.floatToIntBits(light.z)
			&& Float.floatToIntBits(this.radius) == Float.floatToIntBits(light.radius)
			&& Float.floatToIntBits(this.intensity) == Float.floatToIntBits(light.intensity)
			&& this.rgb == light.rgb;
	}

	@Override
	public int hashCode() {
		int hash = this.rgb;
		hash = hash * 31 + Float.floatToIntBits(this.x);
		hash = hash * 31 + Float.floatToIntBits(this.y);
		hash = hash * 31 + Float.floatToIntBits(this.z);
		hash = hash * 31 + Float.floatToIntBits(this.radius);
		return hash * 31 + Float.floatToIntBits(this.intensity);
	}

	@Override
	public String toString() {
		return "light(" + this.x + "," + this.y + "," + this.z + " r=" + this.radius
			+ " rgb=" + Integer.toHexString(this.rgb) + " i=" + this.intensity + ")";
	}
}
