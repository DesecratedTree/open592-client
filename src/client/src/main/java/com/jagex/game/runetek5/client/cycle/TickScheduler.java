package com.jagex.game.runetek5.client.cycle;

/**
 * Fixed-timestep tick accumulator.
 *
 * <p>Owns the nanosecond remainder that decides when a tick is due. It has no
 * clock and never sleeps (elapsed time is an argument), so cadence is testable
 * to the nanosecond. {@link #MAX_TICKS_PER_FRAME} bounds one catch-up and any
 * backlog beyond it is dropped, so a stall can never replay as a burst.
 */
public final class TickScheduler {

	/** Tick interval used when the caller passes something unusable. */
	private static final int DEFAULT_TICK_MS = 20;

	/** Legacy catch-up cap per frame, matching the cycle managers' limit. */
	public static final int MAX_TICKS_PER_FRAME = 10;

	private static final long NANOS_PER_MILLI = 1_000_000L;

	private long accumulatorNanos;

	/**
	 * Accounts for elapsed time and returns how many ticks are due.
	 *
	 * @param elapsedNanos time since the previous call; negative values are
	 *                     treated as zero
	 * @param tickMs live tick interval, never cached
	 * @return ticks due now, {@code 0..MAX_TICKS_PER_FRAME}
	 */
	public int advance(long elapsedNanos, int tickMs) {
		if (elapsedNanos < 0L) {
			elapsedNanos = 0L;
		}
		long tickNanos = nanosPerTick(tickMs);
		this.accumulatorNanos += elapsedNanos;
		int ticks = (int) (this.accumulatorNanos / tickNanos);
		if (ticks > MAX_TICKS_PER_FRAME) {
			ticks = MAX_TICKS_PER_FRAME;
		}
		this.accumulatorNanos -= (long) ticks * tickNanos;
		if (this.accumulatorNanos >= tickNanos) {
			// Backlog beyond the catch-up cap is dropped, not replayed later.
			this.accumulatorNanos = 0L;
		}
		return ticks;
	}

	/** Discards the sub-tick remainder so the next frame starts a fresh tick. */
	public void reset() {
		this.accumulatorNanos = 0L;
	}

	private static long nanosPerTick(int tickMs) {
		return (long) (tickMs < 1 ? DEFAULT_TICK_MS : tickMs) * NANOS_PER_MILLI;
	}
}
