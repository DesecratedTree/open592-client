package com.jagex.game.runetek5.client.hd.sway;

/**
 * The wind clock: animation seconds for the sway phase, advanced once per frame.
 *
 * <p>Same shape and same reasons as {@code HdWaterTime} - a delta accumulator,
 * not a wall clock, because the pacer's nanos are only readable while the frame
 * loop is installed and a paused client must not make the copse jump when it
 * resumes. One clock serves both rows: the grass push uses the same phase to
 * add its own small flutter, so a tuft bends away from the viewer and shivers
 * in step with the plants around it instead of moving on a second, unrelated
 * rhythm.
 *
 * <p>Its only production caller is {@code FramePresentation.beginFrame}, so a
 * harness that draws without the game loop gets a wind that never changes - a
 * still HD frame, not an invented one. The pixel test drives the clock through
 * the test hook for exactly that reason.
 */
public final class HdWindTime {

	/** A single frame longer than this is a stall, not animation: clamp it. */
	public static final long MAX_STEP_MILLIS = 100L;

	private static long millis;

	private HdWindTime() {
	}

	/**
	 * Advances the wind clock by one frame.
	 *
	 * @param deltaMillis time since the previous frame; negative and overlong
	 *                    steps are clamped, so a stall cannot skip a gust
	 */
	public static void advance(long deltaMillis) {
		if (deltaMillis <= 0L) {
			return;
		}
		millis += Math.min(deltaMillis, MAX_STEP_MILLIS);
	}

	/** Wind seconds since the last reset. */
	public static float seconds() {
		return millis / 1000.0F;
	}

	/** Restarts the wind window; the caller is a lifecycle event, not a row toggle. */
	public static void reset() {
		millis = 0L;
	}

	/** Test hook: sets the clock to an exact second count. */
	static void setForTest(float seconds) {
		millis = (long) (Math.max(0.0F, seconds) * 1000.0F);
	}
}
