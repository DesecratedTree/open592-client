package com.jagex.game.runetek5.client.camera;

/**
 * OSHD-style fog distance state.
 *
 * <p>Fog distance is expressed as a percentage of the scene's base fog range,
 * where {@code 256} is 100%. The renderer derives its base fog start/end from
 * the camera far plane and per-scene offsets, and this class scales both by
 * {@code currentFog / 256} so the fog band keeps its shape.
 *
 * <p>Scrolling with Alt held adjusts the <em>target</em> in fixed steps; the
 * render loop eases {@code currentFog} toward it once per tick so motion is
 * smooth instead of jumping a full notch at a time.
 */
public final class FogDistance {

	public static final int FOG_DEFAULT = 256;

	public static final int FOG_MIN = 64;

	public static final int FOG_MAX = 1024;

	/** Fog units added per wheel notch. Positive notches push fog farther out. */
	public static final int STEP_PER_NOTCH = 16;

	/** Exponential smoothing divisor applied per tick. */
	public static final int SMOOTH_DIVISOR = 4;

	private static int currentFog = FOG_DEFAULT;

	private static int targetFog = FOG_DEFAULT;

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
	 * Moves the target by a wheel delta. Scroll up reports a negative
	 * rotation and closes fog in (shorter view distance); scroll down pushes
	 * fog farther out, matching the zoom sign convention.
	 */
	public static void adjustTarget(int wheelNotches) {
		setTarget(targetFog + wheelNotches * STEP_PER_NOTCH);
	}

	/** Eases the current fog level one tick toward the target. */
	public static int smoothStep() {
		currentFog = stepTowards(currentFog, targetFog);
		return currentFog;
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

	/** Returns the smoothed fog level as a scale factor (1.0 at default). */
	public static float getScale() {
		return (float) currentFog / (float) FOG_DEFAULT;
	}

	public static void reset() {
		currentFog = FOG_DEFAULT;
		targetFog = FOG_DEFAULT;
	}

	/** Test hook: sets both ends of the fog level without smoothing. */
	static void setForTest(int current, int target) {
		currentFog = clamp(current);
		targetFog = clamp(target);
	}
}
