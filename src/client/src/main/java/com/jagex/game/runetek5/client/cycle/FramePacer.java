package com.jagex.game.runetek5.client.cycle;

import com.jagex.game.runetek5.deob.Class36;

/**
 * The loop's pacing facade and the client's clock.
 *
 * <p>Stock asked a {@link Class36} cycle manager for the ticks owed and that
 * call blocked until the next tick boundary, welding frames to the 20 ms tick.
 * The pacer keeps the cadence from a monotonic clock but never waits for it, so
 * frames can outrun ticks; only the ceiling in {@link FrameRateLimit} may
 * sleep. The arithmetic lives in {@link TickScheduler} and
 * {@link FrameSleeper}; what remains here is the {@link Class36} adapter and
 * the published clock that {@link ClientFrameClock} installs.
 */
public final class FramePacer extends Class36 {

	/** Elapsed time beyond this is a stall, not frames to catch up. */
	private static final long MAX_FRAME_NANOS = 250_000_000L;

	private final FrameClock clock;

	private final TickScheduler tickScheduler = new TickScheduler();

	private final FrameSleeper sleeper = new FrameSleeper();

	private long lastFrameNanos;

	/**
	 * Raised by off-thread lifecycle callbacks and consumed by the loop on its
	 * next frame: the pacer's fields are unsynchronised, so another thread
	 * must not reset them directly.
	 */
	private volatile boolean resetRequested;

	public FramePacer() {
		this(FrameClock.SYSTEM);
	}

	public FramePacer(FrameClock clock) {
		this.clock = clock;
		this.lastFrameNanos = clock.nanos();
		this.sleeper.reset(this.lastFrameNanos);
	}

	/**
	 * Restarts the clock without crediting ticks: the next frame reports
	 * nothing due and the sub-tick remainder is discarded, so a pause never
	 * bursts ticks. Loop-thread only; other threads use
	 * {@link #requestReset()}.
	 */
	public void reset() {
		this.resetRequested = false;
		long now = this.clock.nanos();
		this.lastFrameNanos = now;
		this.sleeper.reset(now);
		this.tickScheduler.reset();
	}

	/**
	 * Advances the tick clock and returns how many ticks are due,
	 * {@code 0..TickScheduler.MAX_TICKS_PER_FRAME}.
	 *
	 * @param tickMs live tick interval; the {@code fps} console command may
	 *               change it between frames, so it is never cached
	 */
	public int ticksDue(int tickMs) {
		if (this.resetRequested) {
			this.resetRequested = false;
			this.reset();
		}
		long now = this.clock.nanos();
		long elapsed = now - this.lastFrameNanos;
		this.lastFrameNanos = now;
		if (elapsed > MAX_FRAME_NANOS) {
			elapsed = MAX_FRAME_NANOS;
		}
		return this.tickScheduler.advance(elapsed, tickMs);
	}

	/**
	 * Waits only as long as the frame ceiling requires.
	 *
	 * @param focused a background window is throttled to the tick rate
	 */
	public void sleepToDeadline(int tickMs, boolean focused) {
		int fps = FrameRateLimit.targetFps(FrameRateLimit.getConfiguredFps(), focused, tickMs);
		this.sleeper.sleepToDeadline(fps, this.clock.nanos());
	}

	/**
	 * Asks the loop to restart the clock on its next frame. Safe from any
	 * thread: it only raises a flag.
	 */
	public void requestReset() {
		this.resetRequested = true;
	}

	@Override
	public long method2252() {
		return this.clock.nanos();
	}

	/** Non-blocking ticks due, unlike the stock cycle managers. */
	@Override
	public int method2253(int tickMs) {
		return this.ticksDue(tickMs);
	}

	/**
	 * Legacy clock-reset hook, intentionally a no-op: the title screen calls
	 * it every frame and resetting would starve ticks.
	 */
	@Override
	public void method2248() {
	}
}
