package com.jagex.game.runetek5.client.camera;

/**
 * The one time-normalised easing curve shared by zoom and fog.
 *
 * <p>Both are levels a wheel notch moves as a target while presentation eases
 * the current value; they must move on exactly the same curve. Callers keep
 * their own state and hand a step in here.
 *
 * <p>Exponential, with a retention of {@code (divisor - 1) / divisor} per
 * reference tick, normalised on 20 ms so a 20 ms frame is bit-identical to the
 * legacy tick step. The body of the curve therefore runs on wall-clock time at
 * any frame rate; only the last units move one per frame, so a faster
 * presentation settles slightly sooner (256 to 448 in ~380 ms at the legacy
 * 20 ms tick, ~264 ms at 180 fps).
 */
public final class CameraEasing {

	/** Exponential smoothing divisor applied per reference tick. */
	private static final int SMOOTH_DIVISOR = 4;

	/** Reference frame for time-normalised smoothing: one game tick. */
	public static final int SMOOTH_TICK_MILLIS = 20;

	/** Longest frame the smoothing accounts for, so a stall never jumps a value. */
	public static final int SMOOTH_MAX_STEP_MILLIS = 250;

	/** Fraction of the remaining distance kept per reference tick. */
	private static final double SMOOTH_RETENTION =
		(double) (SMOOTH_DIVISOR - 1) / (double) SMOOTH_DIVISOR;

	private CameraEasing() {
	}

	/**
	 * Moves one value toward its target for an arbitrary frame duration.
	 *
	 * <p>A non-positive duration is a no-op and a long frame is clamped, so a
	 * hitch can neither stall the easing nor overshoot the target.
	 */
	public static int stepTowards(int current, int target, int deltaMillis) {
		if (current == target || deltaMillis <= 0) {
			return current;
		}
		if (deltaMillis >= SMOOTH_MAX_STEP_MILLIS) {
			deltaMillis = SMOOTH_MAX_STEP_MILLIS;
		}
		if (deltaMillis == SMOOTH_TICK_MILLIS) {
			return stepTowards(current, target);
		}
		int remaining = target - current;
		double progress = 1.0D - Math.pow(SMOOTH_RETENTION,
			(double) deltaMillis / (double) SMOOTH_TICK_MILLIS);
		int step = (int) Math.round((double) remaining * progress);
		if (step == 0) {
			step = remaining > 0 ? 1 : -1;
		}
		return snap(current, target, step);
	}

	/**
	 * The legacy per-tick step: {@code 1 / SMOOTH_DIVISOR} of the remaining
	 * distance, with a one-unit floor so the value always moves and always
	 * reaches its target instead of asymptotically crawling.
	 */
	private static int stepTowards(int current, int target) {
		if (current == target) {
			return current;
		}
		int remaining = target - current;
		int step = remaining / SMOOTH_DIVISOR;
		if (step == 0) {
			step = remaining > 0 ? 1 : -1;
		}
		return snap(current, target, step);
	}

	/** Applies a step, stopping on the target instead of passing it. */
	private static int snap(int current, int target, int step) {
		int remaining = target - current;
		int next = current + step;
		if (remaining > 0 && next > target) {
			return target;
		}
		if (remaining < 0 && next < target) {
			return target;
		}
		return next;
	}

	/**
	 * Clamps a frame duration to the range the easing accepts: no negative
	 * frames, and no step longer than {@link #SMOOTH_MAX_STEP_MILLIS}.
	 */
	public static int clampDelta(long deltaMillis) {
		if (deltaMillis < 0L) {
			return 0;
		}
		if (deltaMillis > SMOOTH_MAX_STEP_MILLIS) {
			return SMOOTH_MAX_STEP_MILLIS;
		}
		return (int) deltaMillis;
	}
}
