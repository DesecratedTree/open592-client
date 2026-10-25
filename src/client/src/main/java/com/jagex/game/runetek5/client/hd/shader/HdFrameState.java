package com.jagex.game.runetek5.client.hd.shader;

import com.jagex.game.runetek5.client.hd.atmosphere.HdAtmosphere;
import com.jagex.game.runetek5.client.hd.light.HdEnvironmentLight;
import com.jagex.game.runetek5.client.hd.light.HdLightFrame;
import com.jagex.game.runetek5.client.hd.shadow.ShadowBinding;
import com.jagex.game.runetek5.client.hd.sway.HdSway;

/**
 * Everything the lighting program needs that is constant across a frame, as one
 * value: the scene's point light count, this frame's shadow map and how much of
 * the sun's shadow survives the hour, and the atmosphere, carried/room light,
 * environment light and displacement rows.
 *
 * <p>It exists so the draw seam has one frame input instead of nine optional
 * parameters. The per-frame half is this class, assembled by the toolkit where
 * the frame's own state lives; the per-draw half is {@link HdDrawRequest} (what
 * may bind) and {@link HdDrawState} (what the uniforms resolve to). The
 * geometry half - which space the vertices are in - is {@link HdDrawSpace}.
 *
 * <p>Immutable: the toolkit builds one per draw from its frame fields, and a
 * test drives any row by building one too. A null row is normalised to that
 * row's own off value here, so neither the pipeline nor a caller has to
 * re-state the "null means stock" rule.
 */
public final class HdFrameState {

	/** A frame with nothing on: no map, no rows, a full-strength (unused) shadow. */
	public static final HdFrameState OFF = new HdFrameState(0, null, 1.0F, HdAtmosphere.OFF,
		HdLightFrame.OFF, HdEnvironmentLight.OFF, HdSway.OFF, 0.0F);

	private final int pointLights;

	private final ShadowBinding shadow;

	private final float shadowStrength;

	private final HdAtmosphere atmosphere;

	private final HdLightFrame lights;

	private final HdEnvironmentLight environment;

	private final HdSway sway;

	/**
	 * The frame's water clock, for the rows that animate on it. Zero is both GL's
	 * initial uniform value and the state a harness that never advances the clock
	 * draws at, so an unanimated frame is still a well-defined one.
	 */
	private final float waterSeconds;

	public HdFrameState(int scenePointLights, ShadowBinding shadow, float shadowStrength,
			HdAtmosphere atmosphere, HdLightFrame lights, HdEnvironmentLight environment, HdSway sway,
			float waterSeconds) {
		this.pointLights = HdDrawState.clampPointLights(scenePointLights);
		this.shadow = shadow;
		this.shadowStrength = shadowStrength;
		this.atmosphere = atmosphere == null ? HdAtmosphere.OFF : atmosphere;
		this.lights = lights == null ? HdLightFrame.OFF : lights;
		this.environment = environment == null ? HdEnvironmentLight.OFF : environment;
		this.sway = sway == null ? HdSway.OFF : sway;
		this.waterSeconds = waterSeconds;
	}

	/**
	 * The stock frame with {@code scenePointLights} live point lights: no shadow
	 * map and every row off, which is exactly the frame before any of the rows
	 * existed.
	 */
	public static HdFrameState of(int scenePointLights) {
		return new HdFrameState(scenePointLights, null, 1.0F, HdAtmosphere.OFF, HdLightFrame.OFF,
			HdEnvironmentLight.OFF, HdSway.OFF, 0.0F);
	}

	public HdFrameState withPointLights(int scenePointLights) {
		return new HdFrameState(scenePointLights, this.shadow, this.shadowStrength, this.atmosphere,
			this.lights, this.environment, this.sway, this.waterSeconds);
	}

	public HdFrameState withShadow(ShadowBinding shadow) {
		return new HdFrameState(this.pointLights, shadow, this.shadowStrength, this.atmosphere,
			this.lights, this.environment, this.sway, this.waterSeconds);
	}

	/** The fraction of the sun's shadow that survives the hour; 1 is a full shadow. */
	public HdFrameState withStrength(float shadowStrength) {
		return new HdFrameState(this.pointLights, this.shadow, shadowStrength, this.atmosphere,
			this.lights, this.environment, this.sway, this.waterSeconds);
	}

	public HdFrameState withAtmosphere(HdAtmosphere atmosphere) {
		return new HdFrameState(this.pointLights, this.shadow, this.shadowStrength, atmosphere,
			this.lights, this.environment, this.sway, this.waterSeconds);
	}

	public HdFrameState withLights(HdLightFrame lights) {
		return new HdFrameState(this.pointLights, this.shadow, this.shadowStrength, this.atmosphere,
			lights, this.environment, this.sway, this.waterSeconds);
	}

	public HdFrameState withEnvironment(HdEnvironmentLight environment) {
		return new HdFrameState(this.pointLights, this.shadow, this.shadowStrength, this.atmosphere,
			this.lights, environment, this.sway, this.waterSeconds);
	}

	public HdFrameState withSway(HdSway sway) {
		return new HdFrameState(this.pointLights, this.shadow, this.shadowStrength, this.atmosphere,
			this.lights, this.environment, sway, this.waterSeconds);
	}

	/** The frame's water clock, which the water-driven rows animate on. */
	public HdFrameState withWaterSeconds(float waterSeconds) {
		return new HdFrameState(this.pointLights, this.shadow, this.shadowStrength, this.atmosphere,
			this.lights, this.environment, this.sway, waterSeconds);
	}

	/** The scene's live point lights, already clamped to the shader's slots. */
	public int pointLights() {
		return this.pointLights;
	}

	/** This frame's shadow map, or null when the row is off or nothing was built. */
	public ShadowBinding shadow() {
		return this.shadow;
	}

	public float shadowStrength() {
		return this.shadowStrength;
	}

	public HdAtmosphere atmosphere() {
		return this.atmosphere;
	}

	public HdLightFrame lights() {
		return this.lights;
	}

	public HdEnvironmentLight environment() {
		return this.environment;
	}

	public HdSway sway() {
		return this.sway;
	}

	public float waterSeconds() {
		return this.waterSeconds;
	}
}
