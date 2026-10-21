package com.jagex.game.runetek5.client.camera;

/**
 * OSHD-style camera zoom state.
 *
 * <p>Zoom is expressed as a percentage of the default camera distance, where
 * {@code 256} is 100%. The renderer derives its base distance from the camera
 * pitch ({@code (pitch >> 3) * 3 + 600}) and this class scales it:
 * {@code effectiveDistance = baseDistance * currentZoom / 256}.
 *
 * <p>Scrolling adjusts the <em>target</em> in fixed steps; the render loop
 * eases {@code currentZoom} toward it once per tick so motion is smooth
 * instead of jumping a full notch at a time.
 */
public final class CameraZoom {

	public static final int ZOOM_DEFAULT = 256;

	public static final int ZOOM_MIN = 128;

	public static final int ZOOM_MAX = 512;

	/** Zoom units added per wheel notch. Positive notches zoom out. */
	public static final int STEP_PER_NOTCH = 24;

	/** Exponential smoothing divisor applied per tick. */
	public static final int SMOOTH_DIVISOR = 4;

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

	public static void setTarget(int zoom) {
		targetZoom = clamp(zoom);
	}

	/**
	 * Moves the target by a wheel delta. Scroll up reports a negative
	 * rotation and zooms in (shorter distance); scroll down zooms out.
	 */
	public static void adjustTarget(int wheelNotches) {
		setTarget(targetZoom + wheelNotches * STEP_PER_NOTCH);
	}

	/** Eases the current zoom one tick toward the target. */
	public static int smoothStep() {
		currentZoom = stepTowards(currentZoom, targetZoom);
		return currentZoom;
	}

	static int stepTowards(int current, int target) {
		if (current == target) {
			return current;
		}
		int remaining = target - current;
		int step = remaining / SMOOTH_DIVISOR;
		if (step == 0) {
			step = remaining > 0 ? 1 : -1;
		}
		int next = current + step;
		if (remaining > 0 && next > target) {
			return target;
		}
		if (remaining < 0 && next < target) {
			return target;
		}
		return next;
	}

	/** Scales a pitch-derived base distance by the smoothed zoom level. */
	public static int scaleDistance(int baseDistance) {
		return (int) ((long) baseDistance * (long) currentZoom / (long) ZOOM_DEFAULT);
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
