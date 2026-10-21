package com.jagex.game.runetek5.client.camera;

/**
 * OSHD-style fog and render distance state.
 *
 * <p>Fog distance is expressed as a percentage of the scene's base view
 * range, where {@code 256} is 100%. The smoothed level scales the scene far
 * plane and the camera-centered tile window, so render distance
 * (projection, culling, tile submission) and the derived fog start/end move
 * together and the fog band keeps its shape.
 *
 * <p>Scrolling with Alt held adjusts the <em>target</em> in fixed steps; the
 * presentation layer eases {@code currentFog} toward it once per frame (see
 * {@link CameraEasing}) so motion is smooth instead of jumping a full notch at
 * a time.
 */
public final class FogDistance {

	public static final int FOG_DEFAULT = 256;

	public static final int FOG_MIN = 128;

	public static final int FOG_MAX = 405;

	/** Fog units added per wheel notch. Positive notches push fog farther out. */
	public static final int STEP_PER_NOTCH = 16;

	private static int currentFog = FOG_DEFAULT;

	private static int targetFog = FOG_DEFAULT;

	/**
	 * Base scene tile radius (camera-centered tile window) chosen at scene
	 * init from the graphics detail level. Set once per scene init, before
	 * the first frame, via {@link #setBaseTileRadius}.
	 */
	private static int baseTileRadius;

	private FogDistance() {
	}

	public static int getCurrentFog() {
		return currentFog;
	}

	public static int getTargetFog() {
		return targetFog;
	}

	public static int clamp(int fog) {
		if (fog < FOG_MIN) {
			return FOG_MIN;
		}
		if (fog > FOG_MAX) {
			return FOG_MAX;
		}
		return fog;
	}

	public static void setTarget(int fog) {
		targetFog = clamp(fog);
	}

	/**
	 * Restores a persisted level without smoothing, setting both ends so
	 * the view starts exactly there instead of gliding on launch.
	 */
	static void snapTo(int fog) {
		currentFog = targetFog = clamp(fog);
	}

	/**
	 * Moves the target by a wheel delta. Scroll up reports a negative
	 * rotation and closes fog in (shorter view distance); scroll down pushes
	 * fog farther out, matching the zoom sign convention.
	 */
	public static void adjustTarget(int wheelNotches) {
		setTarget(targetFog + wheelNotches * STEP_PER_NOTCH);
	}

	/** Eases the current fog level for a frame duration; see {@link CameraEasing}. */
	public static int smoothStep(int deltaMillis) {
		currentFog = CameraEasing.stepTowards(currentFog, targetFog, deltaMillis);
		return currentFog;
	}

	/**
	 * Scales a base distance (far plane or fog range) by the smoothed fog
	 * level. Render distance and fog share this scale so one control moves
	 * both together.
	 *
	 * <p>The result saturates at {@link Integer#MAX_VALUE} instead of
	 * wrapping. Offscreen passes use {@code Integer.MAX_VALUE} as "no fog"
	 * far plane (item icon generation calls {@code ia(50, Integer.MAX_VALUE)}),
	 * and a wrapped value turns the far plane negative, which culls every
	 * triangle and leaves the icon blank. Saturating keeps a huge far plane
	 * huge at every fog level.
	 */
	public static int scaleDistance(int baseDistance) {
		long scaled = (long) baseDistance * (long) currentFog / (long) FOG_DEFAULT;
		if (scaled > Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		}
		if (scaled < Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		}
		return (int) scaled;
	}

	/**
	 * Records the scene tile radius from the detail level. The per-tick
	 * radius is this base scaled by the smoothed fog level.
	 */
	public static void setBaseTileRadius(int radius) {
		baseTileRadius = radius;
	}

	/**
	 * Current scene tile radius: base radius scaled by the smoothed fog
	 * level, clamped to at least 1 so the scene never collapses.
	 */
	public static int scaledTileRadius() {
		return Math.max(1, scaleDistance(baseTileRadius));
	}

	/**
	 * Radius at maximum fog, for one-time allocation of the per-tile
	 * visibility arrays so they never need resizing as fog moves.
	 */
	public static int maxTileRadius(int baseRadius) {
		return (int) ((long) baseRadius * (long) FOG_MAX / (long) FOG_DEFAULT);
	}

	public static void reset() {
		currentFog = FOG_DEFAULT;
		targetFog = FOG_DEFAULT;
		baseTileRadius = 0;
	}

	/** Test hook: sets both ends of the fog level without smoothing. */
	static void setForTest(int current, int target) {
		currentFog = clamp(current);
		targetFog = clamp(target);
	}
}
