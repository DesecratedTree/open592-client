package com.jagex.game.runetek5.client.hd.water;

/**
 * Animation time for the water waves, advanced once per frame.
 *
 * <p>Deliberately a delta accumulator rather than a wall clock: the client's
 * frame clock is the pacer's monotonic nanoseconds, read through
 * {@code Class4_Sub1_Sub11.method2607()} and only non-null once
 * {@code GameShell.run()} has installed the {@code FramePacer}, and a paused or
 * throttled client must not make the water jump when it resumes. Consumers see
 * only {@link #seconds()}, which is frame-rate independent because the same
 * total milliseconds produce the same second count at 30 fps and 144 fps.
 *
 * <p>The only production caller of {@link #advance(long)} is
 * {@code FramePresentation.beginFrame} inside {@code client.method894()}, so a
 * harness or tool that draws water without the game loop gets no advance at
 * all: the shader still gets a time, it simply never changes, and the surface
 * is a still HD surface rather than animated water. The pixel test drives the
 * clock through the test hook for exactly that reason.
 *
 * <p>Same shape as the camera state holders: a static holder with
 * {@link #advance(long)}, {@link #reset()} and a package-private test hook, so
 * the frame path holds no object of its own. {@link #reset()} is lifecycle (a
 * fresh measurement window), not a row toggle: the clock keeps ticking while
 * the row is off, so switching it on never snaps back to a stale phase.
 */
public final class HdWaterTime {

	/** A single frame longer than this is a stall, not animation: clamp it. */
	public static final long MAX_STEP_MILLIS = 100L;

	private static long millis;

	private HdWaterTime() {
	}

	/**
	 * Advances the animation clock by one frame.
	 *
	 * @param deltaMillis time since the previous frame; negative and overlong
	 *                    steps are clamped, so a stall cannot skip the waves
	 */
	public static void advance(long deltaMillis) {
		if (deltaMillis <= 0L) {
			return;
		}
		millis += Math.min(deltaMillis, MAX_STEP_MILLIS);
	}

	/** Animation seconds since the last reset. */
	public static float seconds() {
		return millis / 1000.0F;
	}

	/** Restarts the animation window; the caller is a lifecycle event, not a row toggle. */
	public static void reset() {
		millis = 0L;
	}

	/** Test hook: sets the clock to an exact second count. */
	static void setForTest(float seconds) {
		millis = (long) (Math.max(0.0F, seconds) * 1000.0F);
	}
}
