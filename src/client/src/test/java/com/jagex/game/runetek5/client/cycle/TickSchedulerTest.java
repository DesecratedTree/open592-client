package com.jagex.game.runetek5.client.cycle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TickScheduler fixed-timestep accumulator")
class TickSchedulerTest {

	private static final long MS = 1_000_000L;

	private TickScheduler scheduler;

	@BeforeEach
	void setUp() {
		this.scheduler = new TickScheduler();
	}

	@Test
	@DisplayName("frames as long as a tick produce exactly one tick each")
	void advance_shouldRunOneTick_whenFrameMatchesTickInterval() {
		for (int frame = 0; frame < 100; frame++) {
			assertEquals(1, this.scheduler.advance(20 * MS, 20), "frame " + frame);
		}
	}

	@Test
	@DisplayName("quarter-tick frames tick every fourth frame")
	void advance_shouldTickEveryFourthFrame_whenFramesAreQuarterTick() {
		int[] expected = { 0, 0, 0, 1 };
		for (int frame = 0; frame < 400; frame++) {
			assertEquals(expected[frame % 4], this.scheduler.advance(5 * MS, 20), "frame " + frame);
		}
	}

	@Test
	@DisplayName("unaligned frames do not drift: 7000 ms of 7 ms frames is 350 ticks")
	void advance_shouldNotDrift_whenFramesAreUnaligned() {
		int ticks = 0;
		for (int frame = 0; frame < 1000; frame++) {
			ticks += this.scheduler.advance(7 * MS, 20);
		}

		assertEquals(350, ticks);
	}

	@Test
	@DisplayName("jitter averages out over a second of frames")
	void advance_shouldAverageOutJitter() {
		int ticks = 0;
		for (int pair = 0; pair < 50; pair++) {
			ticks += this.scheduler.advance(18 * MS, 20);
			ticks += this.scheduler.advance(22 * MS, 20);
		}

		assertEquals(100, ticks);
	}

	@Test
	@DisplayName("the tick interval is passed per call, so a live change is honoured")
	void advance_shouldHonourLiveTickInterval() {
		assertEquals(0, this.scheduler.advance(10 * MS, 20));
		assertEquals(1, this.scheduler.advance(10 * MS, 20));
		assertEquals(1, this.scheduler.advance(10 * MS, 10), "a 10 ms interval doubles the rate");
	}

	@Test
	@DisplayName("an unusable tick interval falls back to the default instead of dividing by zero")
	void advance_shouldFallBack_whenTickIntervalIsUnusable() {
		assertEquals(1, this.scheduler.advance(20 * MS, 0));
		assertEquals(1, this.scheduler.advance(20 * MS, -5));
	}

	@Test
	@DisplayName("a long stall is capped and its backlog is dropped")
	void advance_shouldCapCatchUp_whenFrameStalls() {
		assertEquals(TickScheduler.MAX_TICKS_PER_FRAME, this.scheduler.advance(5000 * MS, 20));
		assertEquals(1, this.scheduler.advance(20 * MS, 20), "the stall must not replay as a burst");
	}

	@Test
	@DisplayName("negative elapsed time counts as no time at all")
	void advance_shouldTreatNegativeElapsedAsZero() {
		assertEquals(0, this.scheduler.advance(-100 * MS, 20));
		assertEquals(1, this.scheduler.advance(20 * MS, 20));
	}

	@Test
	@DisplayName("reset drops the remainder so a pause never bursts ticks")
	void reset_shouldDropRemainder() {
		assertEquals(2, this.scheduler.advance(45 * MS, 20));

		this.scheduler.reset();

		assertEquals(0, this.scheduler.advance(1 * MS, 20));
	}
}
