package com.jagex.game.runetek5.client.camera;

/**
 * OSHD-style camera zoom state.
 *
 * <p>Zoom is a percentage of the stock camera distance. The renderer derives
 * its base distance from the camera pitch ({@code (pitch >> 3) * 3 + 600}) and
 * this class scales it: {@code effectiveDistance = baseDistance * currentZoom / 100}.
 * {@link #ZOOM_STOCK} (100%) is therefore exactly the view the client shipped
 * with, and higher levels pull the camera back.
 *
 * <p>The wheel moves the <em>target</em> in {@link #STEP_PER_NOTCH}-point steps
 * between {@link #ZOOM_MIN} and {@link #ZOOM_MAX}; a fresh install starts at
 * {@link #ZOOM_DEFAULT}. The presentation layer eases {@code currentZoom} to
 * the target once per frame (see {@link CameraEasing}) so motion is smooth
 * instead of jumping a full notch at a time.
 */
public final class CameraZoom {

	/**
	 * Zoom level that renders the stock camera distance. Levels are percentages
	 * of that distance, so 100 is unmodified and 165 is 65% farther back.
	 */
	public static final int ZOOM_STOCK = 100;

	public static final int ZOOM_DEFAULT = 165;

	/** Closest level; the stock view is as far in as the camera goes. */
	public static final int ZOOM_MIN = 100;

	public static final int ZOOM_MAX = 200;

	/** Zoom percentage points added per wheel notch. Positive notches zoom out. */
	public static final int STEP_PER_NOTCH = 5;

	private static int currentZoom = ZOOM_DEFAULT;

	private static int targetZoom = ZOOM_DEFAULT;

	private CameraZoom() {
	}

	public static int getCurrentZoom() {
		return currentZoom;
	}

	public static int getTargetZoom() {
		return targetZoom;
	}

	public static int clamp(int zoom) {
		if (zoom < ZOOM_MIN) {
			return ZOOM_MIN;
		}
		if (zoom > ZOOM_MAX) {
			return ZOOM_MAX;
		}
		return zoom;
	}

	/**
	 * Sets the target, snapped to a whole wheel notch. Every notch then lands on
	 * the same 5-point grid the level starts on, so a persisted or migrated level
	 * cannot leave the control permanently between two steps.
	 */
	public static void setTarget(int zoom) {
		targetZoom = clamp(snapToStep(zoom));
	}

	/** Rounds a level to the nearest multiple of {@link #STEP_PER_NOTCH} from stock. */
	private static int snapToStep(int zoom) {
		int offset = zoom - ZOOM_STOCK;
		return ZOOM_STOCK + Math.round((float) offset / (float) STEP_PER_NOTCH) * STEP_PER_NOTCH;
	}

	/**
	 * Restores a persisted level without smoothing, setting both ends so
	 * the camera starts exactly there instead of gliding on launch.
	 */
	static void snapTo(int zoom) {
		currentZoom = targetZoom = clamp(snapToStep(zoom));
	}

	/**
	 * Moves the target by a wheel delta. Scroll up reports a negative
	 * rotation and zooms in (shorter distance); scroll down zooms out.
	 */
	public static void adjustTarget(int wheelNotches) {
		setTarget(targetZoom + wheelNotches * STEP_PER_NOTCH);
	}

	/** Eases the current zoom for a frame duration; see {@link CameraEasing}. */
	public static int smoothStep(int deltaMillis) {
		currentZoom = CameraEasing.stepTowards(currentZoom, targetZoom, deltaMillis);
		return currentZoom;
	}

	/** Scales a pitch-derived base distance by the smoothed zoom level. */
	public static int scaleDistance(int baseDistance) {
		return (int) ((long) baseDistance * (long) currentZoom / (long) ZOOM_STOCK);
	}

	/**
	 * Single owner of the pitch-to-distance formula. Derives the base camera
	 * distance from pitch ({@code (pitch >> 3) * 3 + 600}) and scales it by
	 * the smoothed zoom level, so render call sites never duplicate the math.
	 */
	public static int computeDistance(int pitch) {
		return scaleDistance((pitch >> 3) * 3 + 600);
	}

	public static void reset() {
		currentZoom = ZOOM_DEFAULT;
		targetZoom = ZOOM_DEFAULT;
	}

	/** Test hook: sets both ends of the zoom without smoothing. */
	static void setForTest(int current, int target) {
		currentZoom = clamp(current);
		targetZoom = clamp(target);
	}
}
