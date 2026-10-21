package com.jagex.game.runetek5.client.cycle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FrameSleeper frame-ceiling deadline")
class FrameSleeperTest {

	private static final long MS = 1_000_000L;

	private static final long SECOND = 1_000_000_000L;

	/** Records sleep requests instead of letting real time pass. */
	private static final class RecordingSleep implements FrameSleeper.Sleep {

		private final List<Long> requests = new ArrayList<Long>();

		@Override
		public void sleep(long nanos) {
			this.requests.add(nanos);
		}

		int count() {
			return this.requests.size();
		}

		long last() {
			return this.requests.get(this.requests.size() - 1);
		}
	}

	private RecordingSleep sleep;

	private FrameSleeper sleeper;

	@BeforeEach
	void setUp() {
		this.sleep = new RecordingSleep();
		this.sleeper = new FrameSleeper(this.sleep);
	}

	@Test
	@DisplayName("an uncapped frame rate never waits")
	void sleepToDeadline_shouldNotWait_whenUncapped() {
		this.sleeper.sleepToDeadline(FrameRateLimit.MIN_FPS, 1000 * MS);
		this.sleeper.sleepToDeadline(FrameRateLimit.MIN_FPS, 2000 * MS);

		assertEquals(0, this.sleep.count());
	}

	@Test
	@DisplayName("the first frame arms the deadline, the next waits one frame budget")
	void sleepToDeadline_shouldWaitOneBudget_onSecondFrame() {
		this.sleeper.sleepToDeadline(180, 0L);
		assertEquals(0, this.sleep.count(), "the first frame only arms the deadline");

		this.sleeper.sleepToDeadline(180, 1 * MS);

		assertEquals(1, this.sleep.count());
		assertTrue(this.sleep.last() <= SECOND / 180L, "waited " + this.sleep.last() + " ns");
	}

	@Test
	@DisplayName("the deadline is advanced by one budget per call, not by the wait itself")
	void sleepToDeadline_shouldAdvanceByOneBudgetPerCall() {
		long start = 5 * SECOND;
		this.sleeper.sleepToDeadline(100, start);

		this.sleeper.sleepToDeadline(100, start + MS);
		long first = this.sleep.last();
		this.sleeper.sleepToDeadline(100, start + MS + first);

		assertTrue(first < SECOND / 100L, "the first wait is one budget short: " + first);
		assertEquals(SECOND / 100L, this.sleep.last(), "an on-time frame waits the full budget");
	}

	@Test
	@DisplayName("a late frame resyncs instead of accumulating debt")
	void sleepToDeadline_shouldResync_whenBehind() {
		this.sleeper.sleepToDeadline(60, 0L);

		// The renderer is 500 ms late: it must not now sprint to catch up.
		this.sleeper.sleepToDeadline(60, 500 * MS);
		assertEquals(0, this.sleep.count());

		this.sleeper.sleepToDeadline(60, 500 * MS);
		assertEquals(1, this.sleep.count(), "after resyncing, the frame waits normally again");
	}

	@Test
	@DisplayName("reset moves the deadline to now so a pause never causes a burst")
	void reset_shouldResyncTheDeadline() {
		this.sleeper.sleepToDeadline(60, 0L);
		this.sleeper.reset(10 * SECOND);

		this.sleeper.sleepToDeadline(60, 10 * SECOND);

		assertEquals(0, this.sleep.count());
	}
}
