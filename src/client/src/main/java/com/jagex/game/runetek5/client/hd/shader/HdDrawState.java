package com.jagex.game.runetek5.client.hd.shader;

/**
 * The per-primitive inputs the lighting shader cannot read from GL state.
 *
 * <p>What a draw samples (the material texture), how many point lights are live,
 * and how the sun's shadow map is being read this frame: whether a map is bound
 * at all and how wide its PCF kernel is. Immutable and {@code equals}-comparable
 * so the pipeline can skip redundant {@code glUniform} calls without a dirty
 * flag of its own. The shadow matrix itself is per frame, not per draw, so it
 * lives behind the frame id in the pipeline rather than here.
 */
public final class HdDrawState {

	/** {@code GL_LIGHT2..5}: the toolkit's {@code getMaxLights()} worth of slots. */
	public static final int MAX_POINT_LIGHTS = 4;

	private final boolean textured;

	private final int pointLights;

	private final boolean shadowEnabled;

	private final int shadowRadius;

	public HdDrawState(boolean textured, int pointLights, boolean shadowEnabled, int shadowRadius) {
		this.textured = textured;
		this.pointLights = clampPointLights(pointLights);
		this.shadowEnabled = shadowEnabled;
		this.shadowRadius = clampRadius(shadowRadius);
	}

	/** A draw with no shadow map bound, as every draw looked before the row existed. */
	public HdDrawState(boolean textured, int pointLights) {
		this(textured, pointLights, false, 0);
	}

	/** Clamps a scene light count into the four slots the shader loops over. */
	public static int clampPointLights(int count) {
		if (count < 0) {
			return 0;
		}
		if (count > MAX_POINT_LIGHTS) {
			return MAX_POINT_LIGHTS;
		}
		return count;
	}

	/** The largest PCF radius the shader's 5x5 loop can honour. */
	public static int clampRadius(int radius) {
		if (radius < 0) {
			return 0;
		}
		return radius > 2 ? 2 : radius;
	}

	public boolean textured() {
		return this.textured;
	}

	public int pointLights() {
		return this.pointLights;
	}

	public boolean shadowEnabled() {
		return this.shadowEnabled;
	}

	public int shadowRadius() {
		return this.shadowRadius;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HdDrawState)) {
			return false;
		}
		HdDrawState state = (HdDrawState) other;
		return state.textured == this.textured && state.pointLights == this.pointLights
			&& state.shadowEnabled == this.shadowEnabled && state.shadowRadius == this.shadowRadius;
	}

	@Override
	public int hashCode() {
		int hash = (this.textured ? 31 : 0) + this.pointLights;
		hash = hash * 31 + (this.shadowEnabled ? 1 : 0);
		return hash * 31 + this.shadowRadius;
	}

	@Override
	public String toString() {
		return "textured=" + this.textured + " pointLights=" + this.pointLights
			+ " shadow=" + this.shadowEnabled + "/" + this.shadowRadius;
	}
}
