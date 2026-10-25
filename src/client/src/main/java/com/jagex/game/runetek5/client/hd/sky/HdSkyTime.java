package com.jagex.game.runetek5.client.hd.sky;

/**
 * The day and night clock: one phase in {@code [0, 1)} that the sky colours are
 * read from.
 *
 * <p>Phase 0 is midnight, 0.25 sunrise, 0.5 noon and 0.75 sunset, so the sun's
 * height is {@code sin(2 * PI * (phase - 0.25))} and the same number drives the
 * day length the row names. A full turn takes {@link SkyGate#cycleMinutes()}
 * real minutes, or never happens while the row is off.
 *
 * <p>Deliberately a delta accumulator, exactly like {@code HdWaterTime}: the
 * only production caller of {@link #advance(long)} is
 * {@code FramePresentation.beginFrame}, so a paused, throttled or off-screen
 * client does not come back to a sky that jumped hours, and a harness that
 * draws frames without the game loop keeps whatever phase it set. Nothing here
 * reads a wall clock - {@link #START_PHASE} is where a fresh session begins and
 * where {@link #reset()} returns to - which is what makes "nothing advances
 * time" mean a still sky rather than a sky sampled from the system time.
 *
 * <p>Speed is applied to the phase, not to an elapsed-millisecond counter, so
 * changing the day length mid-cycle changes how fast the sky turns from then
 * on instead of jumping the sun to a different hour.
 */
public final class HdSkyTime {

	/** A single frame longer than this is a stall, not time passing: clamp it. */
	public static final long MAX_STEP_MILLIS = 100L;

	/** Noon: a fresh session and a reset both start in full daylight. */
	public static final float START_PHASE = 0.5F;

	private static double phase = START_PHASE;

	private HdSkyTime() {
	}

	/**
	 * Advances the clock by one frame.
	 *
	 * @param deltaMillis time since the previous frame; negative and overlong
	 *                    steps are clamped, and a stopped clock ignores the call
	 *                    entirely
	 */
	public static void advance(long deltaMillis) {
		if (deltaMillis <= 0L) {
			return;
		}
		double cycle = SkyGate.cycleMillis();
		if (cycle <= 0.0D) {
			// The row is off (or 592HD is): the sun holds its place.
			return;
		}
		long step = Math.min(deltaMillis, MAX_STEP_MILLIS);
		double advanced = phase + (double) step / cycle;
		phase = advanced - Math.floor(advanced);
	}

	/** The current phase in {@code [0, 1)}. */
	public static float phase() {
		return (float) phase;
	}

	/** Restarts at noon; the caller is a lifecycle event, not a row toggle. */
	public static void reset() {
		phase = START_PHASE;
	}

	/** Test hook: pins the clock at an exact phase, wrapping into range. */
	static void setForTest(float value) {
		double wrapped = value - Math.floor(value);
		phase = wrapped < 0.0D ? wrapped + 1.0D : wrapped;
	}
}
