package com.jagex.game.runetek5.client.camera;

/**
 * OSHD-style fog and render distance state.
 *
 * <p>Fog distance is a percentage of the scene's stock view range:
 * {@link #FOG_STOCK} (100%) is exactly what the client shipped with and higher
 * levels see farther. The smoothed level scales the scene far plane and the
 * camera-centered tile window, so render distance (projection, culling, tile
 * submission) and the derived fog start/end move together and the fog band
 * keeps its shape.
 *
 * <p>Scrolling with Alt held adjusts the <em>target</em> in
 * {@link #STEP_PER_NOTCH}-point steps between {@link #FOG_MIN} and
 * {@link #FOG_MAX}; a fresh install starts at {@link #FOG_DEFAULT}. The
 * presentation layer eases {@code currentFog} to the target once per frame (see
 * {@link CameraEasing}) so motion is smooth instead of jumping a full notch at
 * a time.
 */
public final class FogDistance {

	/**
	 * Fog level that renders the stock view distance. Levels are percentages
	 * of that distance, so 100 is unmodified and 165 is 65% farther out.
	 */
	public static final int FOG_STOCK = 100;

	public static final int FOG_DEFAULT = 165;

	/** Nearest level; the stock view distance is as close as fog comes. */
	public static final int FOG_MIN = 100;

	public static final int FOG_MAX = 200;

	/** Fog percentage points added per wheel notch. Positive notches push fog farther out. */
	public static final int STEP_PER_NOTCH = 5;

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

	/**
	 * Sets the target, snapped to a whole wheel notch. Every notch then lands on
	 * the same 5-point grid the level starts on, so a persisted or migrated level
	 * cannot leave the control permanently between two steps.
	 */
	public static void setTarget(int fog) {
		targetFog = clamp(snapToStep(fog));
	}

	/** Rounds a level to the nearest multiple of {@link #STEP_PER_NOTCH} from stock. */
	private static int snapToStep(int fog) {
		int offset = fog - FOG_STOCK;
		return FOG_STOCK + Math.round((float) offset / (float) STEP_PER_NOTCH) * STEP_PER_NOTCH;
	}

	/**
	 * Restores a persisted level without smoothing, setting both ends so
	 * the view starts exactly there instead of gliding on launch.
	 */
	static void snapTo(int fog) {
		currentFog = targetFog = clamp(snapToStep(fog));
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
	 * <p>Saturates instead of wrapping. {@code Integer.MAX_VALUE} is the
	 * documented "no far clip" value (item icons render with
	 * {@code ia(50, Integer.MAX_VALUE)}), and at the upper fog levels the
	 * scaled result does not fit in an {@code int}. Wrapping produced a
	 * negative far plane, which inverted the depth test and blanked every
	 * triangle drawn in that projection.
	 */
	public static int scaleDistance(int baseDistance) {
		return saturate((long) baseDistance * (long) currentFog / (long) FOG_STOCK);
	}

	/** Clamps a scaled distance to the {@code int} range without wrapping. */
	private static int saturate(long scaledDistance) {
		if (scaledDistance > (long) Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		}
		if (scaledDistance < (long) Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		}
		return (int) scaledDistance;
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
		return saturate((long) baseRadius * (long) FOG_MAX / (long) FOG_STOCK);
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
