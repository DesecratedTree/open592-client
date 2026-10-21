package com.jagex.game.runetek5.client.cycle;

/**
 * The frame-ceiling deadline, and the only place the loop waits.
 *
 * <p>One frame budget per call is added to the deadline, resyncing to "now"
 * whenever the renderer falls behind, so it never builds debt that later
 * produces unslept frames. The sleep is injected, so the arithmetic is
 * testable without real time passing.
 */
public final class FrameSleeper {

	/** Sleep sink; production uses {@link #SYSTEM}, tests record the request. */
	public interface Sleep {
		void sleep(long nanos);
	}

	private static final long NANOS_PER_MILLI = 1_000_000L;

	private static final long NANOS_PER_SECOND = 1_000_000_000L;

	/** Production sleep sink. */
	private static final Sleep SYSTEM = new Sleep() {
		@Override
		public void sleep(long nanos) {
			long millis = nanos / NANOS_PER_MILLI;
			int remainder = (int) (nanos % NANOS_PER_MILLI);
			try {
				Thread.sleep(millis, remainder);
			} catch (InterruptedException ignored) {
				// The loop is never interrupted: shutdown is signalled through
				// Static173.aLong118. Restoring the flag would make every later
				// sleep return immediately and spin the renderer.
			}
		}
	};

	private final Sleep sleep;

	private long deadlineNanos;

	public FrameSleeper() {
		this(SYSTEM);
	}

	public FrameSleeper(Sleep sleep) {
		this.sleep = sleep;
	}

	/**
	 * Waits until the next frame is due.
	 *
	 * @param fps frames per second to allow; {@link FrameRateLimit#MIN_FPS}
	 *            (zero) means uncapped and never waits
	 * @param nowNanos current time from the loop's clock
	 */
	public void sleepToDeadline(int fps, long nowNanos) {
		if (fps <= FrameRateLimit.MIN_FPS) {
			this.deadlineNanos = nowNanos;
			return;
		}
		long frameNanos = NANOS_PER_SECOND / (long) fps;
		if (this.deadlineNanos < nowNanos) {
			this.deadlineNanos = nowNanos;
		}
		long wait = this.deadlineNanos - nowNanos;
		this.deadlineNanos += frameNanos;
		if (wait > 0L) {
			this.sleep.sleep(wait);
		}
	}

	/** Resyncs the deadline to now, e.g. after a pause, so no burst follows. */
	public void reset(long nowNanos) {
		this.deadlineNanos = nowNanos;
	}
}
