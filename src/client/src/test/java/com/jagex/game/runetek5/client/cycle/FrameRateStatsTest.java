package com.jagex.game.runetek5.client.cycle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("FrameRateStats debug overlay rates")
class FrameRateStatsTest {

	@BeforeEach
	void resetStats() {
		FrameRateStats.reset();
	}

	@ParameterizedTest(name = "perSecond({0} in {1} ms) == {2}")
	@CsvSource({
		"180, 1000, 180",
		"5, 100, 50",
		"1, 3, 333",
		"181, 1005, 180",
		"0, 1000, 0",
		"-3, 1000, 0",
		"60, 0, 0",
		"60, -1, 0"
	})
	@DisplayName("perSecond rounds a count over an interval")
	void perSecond_shouldScaleAndRound(int count, long elapsedMillis, int expected) {
		assertEquals(expected, FrameRateStats.perSecond(count, elapsedMillis));
	}

	@Test
	@DisplayName("nothing is published before a full window has elapsed")
	void record_shouldPublishNothing_whenWindowIsIncomplete() {
		for (int frame = 0; frame < 100; frame++) {
			FrameRateStats.record(0, frame * 5L);
		}

		assertEquals("Fps:0 Tps:0", FrameRateStats.getOverlayText());
	}

	@Test
	@DisplayName("200 frames carrying 50 ticks over one second read as 200 fps / 50 tps")
	void record_shouldReportDecoupledRates() {
		long start = 1_700_000_000_000L;
		FrameRateStats.record(0, start);
		for (int frame = 0; frame < 200; frame++) {
			FrameRateStats.record(frame % 4 == 0 ? 1 : 0, start + (frame + 1L) * 5L);
		}

		assertEquals("Fps:200 Tps:50", FrameRateStats.getOverlayText());
	}

	@Test
	@DisplayName("rates roll over each window instead of accumulating")
	void record_shouldResetCounters_eachWindow() {
		long start = 1_700_000_000_000L;
		FrameRateStats.record(1, start);
		for (int frame = 0; frame < 60; frame++) {
			FrameRateStats.record(1, start + (frame + 1L) * 1000L / 60L);
		}
		assertEquals("Fps:60 Tps:60", FrameRateStats.getOverlayText());

		for (int frame = 0; frame < 30; frame++) {
			FrameRateStats.record(1, start + 1000L + (frame + 1L) * 1000L / 30L);
		}

		assertEquals("Fps:30 Tps:30", FrameRateStats.getOverlayText());
	}

	@Test
	@DisplayName("a stall is reported as the low rate it was, not extrapolated away")
	void record_shouldReportStallAsLowRate() {
		long start = 1_700_000_000_000L;
		FrameRateStats.record(10, start);
		FrameRateStats.record(10, start + 1500L);

		assertEquals("Fps:1 Tps:7", FrameRateStats.getOverlayText());
	}

	@Test
	@DisplayName("a reset requested off-thread is applied by the loop's next frame")
	void requestReset_shouldClearRates_onTheNextRecord() {
		long start = 1_700_000_000_000L;
		FrameRateStats.record(1, start);
		for (int frame = 0; frame < 60; frame++) {
			FrameRateStats.record(1, start + (frame + 1L) * 1000L / 60L);
		}
		assertEquals("Fps:60 Tps:60", FrameRateStats.getOverlayText());

		FrameRateStats.requestReset();

		assertEquals("Fps:60 Tps:60", FrameRateStats.getOverlayText(),
			"the window is only cleared by the loop thread");
		FrameRateStats.record(1, start + 1000L);
		assertEquals("Fps:0 Tps:0", FrameRateStats.getOverlayText());
	}
}
