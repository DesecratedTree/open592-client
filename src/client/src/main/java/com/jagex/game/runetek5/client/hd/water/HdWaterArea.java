package com.jagex.game.runetek5.client.hd.water;

import com.jagex.game.runetek5.client.hd.content.HdEnvironmentDefinition;
import com.jagex.game.runetek5.client.hd.material.HdAreaLight;

/**
 * The area's water, resolved from {@code environments.json} for the local
 * player's tile: 634HD's {@code hdWaterParams}, {@code hdWaterTint} and the
 * sizing half of its {@code hdWaterClock}.
 *
 * <p>634HD reads the area's water block once per frame and hands the shader
 * four values: {@code waves} scales the ripples' height on top of the type's
 * own {@code normalStrength}, {@code glint} scales the sun's glint on top of
 * the type's {@code specular}, {@code size} scales the ripples' tiling, and
 * {@code speed} is the rate of the water's clock - so the caustics and the
 * ripples advance on one clock. The tint pulls the type's surface colour
 * towards the area's colour at the type's brightness, and gives the gradient
 * the surface reflects its hue.
 *
 * <p>{@code size} is the one value a change is animated rather than switched:
 * the pattern is laid in world space, so a pond at size 4 replacing one at
 * size 1 would sweep hundreds of repeats across the surface in one frame.
 * Upstream blends the layer sums at the old size into the new one over three
 * seconds ({@code hdWaterClock.y} the old size, {@code z} the blend), and this
 * holder carries the same pair.
 *
 * <p>Same shape as {@link HdAreaLight}: a static holder resolved for the
 * player's tile through the captured environment, {@link #DEFAULT} for a frame
 * that stands in no area (the login screen, a harness), and a test seam that
 * publishes an area without a player. Every default is the identity - scales 1,
 * WATER's {@code surface} colour, and the documented tint strength - so a frame
 * that resolves no area shades exactly as the surface did before this existed.
 */
public final class HdWaterArea {

	/** 634HD's {@code water.strength} when an environment states none. */
	public static final float DEFAULT_STRENGTH = 0.45F;

	/**
	 * The strength of the area a frame has not resolved: no area states a tint,
	 * so the surface keeps 592HD's own body - the type's surface colour pulled
	 * half-way to its depth colour - and the type's surface colour for the
	 * reflected gradient. Outside the schema's [0, 1], so a reported area can
	 * never be mistaken for the absent one.
	 */
	public static final float NO_TINT = -1.0F;

	/** The WATER type's {@code surface} colour, {@code #69809c}: the tint with no area. */
	public static final int DEFAULT_TINT = 0x69809C;

	/** How long the ripples' tiling takes to move to a new area's size. */
	public static final float TRANSITION_MILLIS = 3000.0F;

	/** No area: every scale 1, the type's own colour, settled. */
	public static final HdWaterArea DEFAULT = new HdWaterArea(DEFAULT_TINT, NO_TINT,
		1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);

	private static volatile HdWaterArea current = DEFAULT;

	/** Set by {@link #publishForTest}: the published area wins over the player. */
	private static boolean overridden;

	private final int tint;

	private final float strength;

	private final float waves;

	private final float glint;

	private final float speed;

	/** The size the area asks for. */
	private final float size;

	/** The size on show before this area arrived; {@code size} once {@code blend} reaches 1. */
	private final float shownSize;

	/** How far the crossfade to {@code size} has run; 1 when settled. */
	private final float blend;

	private HdWaterArea(int tint, float strength, float waves, float glint, float speed, float size,
			float shownSize, float blend) {
		this.tint = tint & 0xFFFFFF;
		// Negative is the absent area sentinel, outside the schema's range but
		// not a value it can report; every reported strength stays in [0, 1].
		this.strength = strength < 0.0F ? NO_TINT : clamp(strength, 0.0F, 1.0F);
		this.waves = clamp(waves, 0.0F, 4.0F);
		this.glint = clamp(glint, 0.0F, 4.0F);
		this.speed = clamp(speed, 0.0F, 4.0F);
		this.size = clamp(size, 0.25F, 4.0F);
		this.shownSize = clamp(shownSize, 0.25F, 4.0F);
		this.blend = clamp(blend, 0.0F, 1.0F);
	}

	/** The area one environment's water block describes; {@link #DEFAULT} for none. */
	public static HdWaterArea of(HdEnvironmentDefinition environment) {
		if (environment == null) {
			return DEFAULT;
		}
		Integer tint = environment.waterTint();
		float strength = environment.waterStrength();
		if (Float.isNaN(strength)) {
			strength = DEFAULT_STRENGTH;
		}
		float size = environment.waterSize();
		return new HdWaterArea(tint == null ? DEFAULT_TINT : tint.intValue(), strength,
			environment.waterWaves(), environment.waterGlint(), environment.waterSpeed(), size,
			size, 1.0F);
	}

	/** A settled area, for tests and callers that state the values directly. */
	static HdWaterArea of(int tint, float strength, float waves, float glint, float speed, float size) {
		return new HdWaterArea(tint, strength, waves, glint, speed, size, size, 1.0F);
	}

	/** A crossfading area, for the tests that check the blend itself. */
	static HdWaterArea midTransition(HdWaterArea area, float shownSize, float blend) {
		return new HdWaterArea(area.tint, area.strength, area.waves, area.glint, area.speed,
			area.size, shownSize, blend);
	}

	/**
	 * Resolves the local player's area and publishes its water for this frame.
	 *
	 * <p>The environment is the one {@link HdAreaLight} resolved, so the frame
	 * looks its area up once: the light is what that block is read for, and
	 * reading it again here would be a second source for the same question. An
	 * area whose water values differ from the published ones starts a three
	 * second crossfade from the size on show, so a teleport never snaps the
	 * pattern.
	 */
	public static void capture() {
		if (!overridden) {
			current = retarget(current, HdAreaLight.environment());
		}
	}

	/**
	 * The area [shown] becomes when the frame stands in [environment]: itself
	 * when the water it asks for is the water already on show, or the new area
	 * crossfading from the tiling that was displayed. Pure, so the retarget is
	 * provable without a player.
	 */
	static HdWaterArea retarget(HdWaterArea shown, HdEnvironmentDefinition environment) {
		HdWaterArea resolved = of(environment);
		if (resolved.sameAs(shown)) {
			return shown;
		}
		return new HdWaterArea(resolved.tint, resolved.strength, resolved.waves, resolved.glint,
			resolved.speed, resolved.size, shown.displayedSize(), 0.0F);
	}

	/**
	 * Advances the crossfade to the area's size by one frame.
	 *
	 * @param deltaMillis time since the previous frame; negative and overlong
	 *                    steps are clamped, so a stall cannot finish the blend
	 *                    in one frame
	 */
	public static void smoothStep(long deltaMillis) {
		HdWaterArea area = current;
		if (area.blend >= 1.0F) {
			return;
		}
		long step = deltaMillis <= 0L ? 0L : Math.min(deltaMillis, HdWaterTime.MAX_STEP_MILLIS);
		current = new HdWaterArea(area.tint, area.strength, area.waves, area.glint, area.speed,
			area.size, area.shownSize, area.blend + step / TRANSITION_MILLIS);
	}

	/** The frame's area; {@link #DEFAULT} until a player stands in one. */
	public static HdWaterArea current() {
		return current;
	}

	/** Lifecycle: forget the area, as a fresh frame window would. */
	public static void reset() {
		current = DEFAULT;
		overridden = false;
	}

	/** Test seam: publish a resolved area without a live player. */
	static void publishForTest(HdWaterArea area) {
		current = area == null ? DEFAULT : area;
		overridden = true;
	}

	/** Test seam: forget the published area and return to the player. */
	static void resetForTest() {
		reset();
	}

	public int tint() {
		return this.tint;
	}

	public float tintRed() {
		return (this.tint >> 16 & 0xFF) / 255.0F;
	}

	public float tintGreen() {
		return (this.tint >> 8 & 0xFF) / 255.0F;
	}

	public float tintBlue() {
		return (this.tint & 0xFF) / 255.0F;
	}

	/** How much of the type's colour the tint replaces. */
	public float tintStrength() {
		return this.strength;
	}

	/** The ripples' height, on top of the type's own strength. */
	public float waves() {
		return this.waves;
	}

	/** The sun's glint, on top of the type's own specular strength. */
	public float glint() {
		return this.glint;
	}

	/** The rate the water's clock advances at. */
	public float speed() {
		return this.speed;
	}

	/** The ripples' tiling this area asks for. */
	public float size() {
		return this.size;
	}

	/** The tiling on show before the crossfade; {@link #size()} once settled. */
	public float shownSize() {
		return this.shownSize;
	}

	/** How far the crossfade has run; 1 when settled. */
	public float blend() {
		return this.blend;
	}

	/** The tiling actually shown this frame. */
	public float displayedSize() {
		return this.blend >= 1.0F ? this.size
			: this.shownSize + (this.size - this.shownSize) * this.blend;
	}

	/** True when every value the area states matches, so no crossfade is owed. */
	public boolean sameAs(HdWaterArea other) {
		return other != null && this.tint == other.tint
			&& Float.compare(this.strength, other.strength) == 0
			&& Float.compare(this.waves, other.waves) == 0
			&& Float.compare(this.glint, other.glint) == 0
			&& Float.compare(this.speed, other.speed) == 0
			&& Float.compare(this.size, other.size) == 0;
	}

	@Override
	public String toString() {
		return "waterArea[tint=#" + Integer.toHexString(this.tint) + " at " + this.strength
			+ ", waves=" + this.waves + ", glint=" + this.glint + ", speed=" + this.speed
			+ ", size=" + this.size + " from " + this.shownSize + " at " + this.blend + "]";
	}

	private static float clamp(float value, float min, float max) {
		return value < min ? min : (value > max ? max : value);
	}
}
