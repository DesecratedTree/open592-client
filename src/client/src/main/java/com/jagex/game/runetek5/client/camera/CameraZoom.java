package com.jagex.game.runetek5.client.camera;

/**
 * OSHD-style camera zoom state.
 *
 * <p>Zoom is expressed as a percentage of the default camera distance, where
 * {@code 256} is 100%. The renderer derives its base distance from the camera
 * pitch ({@code (pitch >> 3) * 3 + 600}) and this class scales it:
 * {@code effectiveDistance = baseDistance * currentZoom / 256}.
 *
 * <p>Scrolling adjusts the <em>target</em> in fixed steps; the presentation
 * layer eases {@code currentZoom} toward it once per frame (see
 * {@link CameraEasing}) so motion is smooth instead of jumping a full notch at
 * a time.
 */
public final class CameraZoom {

	public static final int ZOOM_DEFAULT = 256;

	public static final int ZOOM_MIN = 128;

	public static final int ZOOM_MAX = 512;

	/** Zoom units added per wheel notch. Positive notches zoom out. */
	public static final int STEP_PER_NOTCH = 24;

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
	 * Restores a persisted level without smoothing, setting both ends so
	 * the camera starts exactly there instead of gliding on launch.
	 */
	static void snapTo(int zoom) {
		currentZoom = targetZoom = clamp(zoom);
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
