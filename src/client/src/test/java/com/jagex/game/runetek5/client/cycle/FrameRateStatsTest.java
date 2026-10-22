package com.jagex.game.runetek5.client.cycle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jagex.game.runetek5.client.camera.CameraEasing;
import com.jagex.game.runetek5.client.camera.CameraZoom;
import com.jagex.game.runetek5.client.camera.FogDistance;

import java.util.function.IntUnaryOperator;

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
		// The overlay reports the presentation levels, so the readout is only
		// deterministic when they start from their defaults.
		CameraZoom.reset();
		FogDistance.reset();
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

		assertEquals("Fps:0 Tps:0 Zoom:165% Fog:165%", FrameRateStats.getOverlayText());
	}

	@Test
	@DisplayName("200 frames carrying 50 ticks over one second read as 200 fps / 50 tps")
	void record_shouldReportDecoupledRates() {
		long start = 1_700_000_000_000L;
		FrameRateStats.record(0, start);
		for (int frame = 0; frame < 200; frame++) {
			FrameRateStats.record(frame % 4 == 0 ? 1 : 0, start + (frame + 1L) * 5L);
		}

		assertEquals("Fps:200 Tps:50 Zoom:165% Fog:165%", FrameRateStats.getOverlayText());
	}

	@Test
	@DisplayName("rates roll over each window instead of accumulating")
	void record_shouldResetCounters_eachWindow() {
		long start = 1_700_000_000_000L;
		FrameRateStats.record(1, start);
		for (int frame = 0; frame < 60; frame++) {
			FrameRateStats.record(1, start + (frame + 1L) * 1000L / 60L);
		}
		assertEquals("Fps:60 Tps:60 Zoom:165% Fog:165%", FrameRateStats.getOverlayText());

		for (int frame = 0; frame < 30; frame++) {
			FrameRateStats.record(1, start + 1000L + (frame + 1L) * 1000L / 30L);
		}

		assertEquals("Fps:30 Tps:30 Zoom:165% Fog:165%", FrameRateStats.getOverlayText());
	}

	@Test
	@DisplayName("a stall is reported as the low rate it was, not extrapolated away")
	void record_shouldReportStallAsLowRate() {
		long start = 1_700_000_000_000L;
		FrameRateStats.record(10, start);
		FrameRateStats.record(10, start + 1500L);

		assertEquals("Fps:1 Tps:7 Zoom:165% Fog:165%", FrameRateStats.getOverlayText());
	}

	@Test
	@DisplayName("the overlay reports the levels on screen, not the ones being eased toward")
	void getOverlayText_shouldReportCurrentLevels_whileEasing() {
		CameraZoom.setTarget(CameraZoom.ZOOM_MAX);
		FogDistance.setTarget(FogDistance.FOG_MIN);
		CameraZoom.smoothStep(CameraEasing.SMOOTH_TICK_MILLIS);
		FogDistance.smoothStep(CameraEasing.SMOOTH_TICK_MILLIS);

		int zoomLevel = CameraZoom.getCurrentZoom();
		int fogLevel = FogDistance.getCurrentFog();

		assertEquals("Fps:0 Tps:0 Zoom:" + zoomLevel + "% Fog:" + fogLevel + "%",
			FrameRateStats.getOverlayText());
		assertTrue(zoomLevel > CameraZoom.ZOOM_DEFAULT && zoomLevel < CameraZoom.ZOOM_MAX,
			"one easing step should sit between the default and the target, was " + zoomLevel);
		assertTrue(fogLevel < FogDistance.FOG_DEFAULT && fogLevel > FogDistance.FOG_MIN,
			"one easing step should sit between the default and the target, was " + fogLevel);
	}

	@Test
	@DisplayName("the overlay reports settled zoom and fog as percentages of stock")
	void getOverlayText_shouldReportSettledLevels() {
		CameraZoom.setTarget(CameraZoom.ZOOM_MAX);
		FogDistance.setTarget(190);
		settle(CameraZoom::smoothStep, CameraZoom.getCurrentZoom(), CameraZoom.ZOOM_MAX);
		settle(FogDistance::smoothStep, FogDistance.getCurrentFog(), 190);

		assertEquals("Fps:0 Tps:0 Zoom:200% Fog:190%", FrameRateStats.getOverlayText());
	}

	@Test
	@DisplayName("a reset requested off-thread is applied by the loop's next frame")
	void requestReset_shouldClearRates_onTheNextRecord() {
		long start = 1_700_000_000_000L;
		FrameRateStats.record(1, start);
		for (int frame = 0; frame < 60; frame++) {
			FrameRateStats.record(1, start + (frame + 1L) * 1000L / 60L);
		}
		assertEquals("Fps:60 Tps:60 Zoom:165% Fog:165%", FrameRateStats.getOverlayText());

		FrameRateStats.requestReset();

		assertEquals("Fps:60 Tps:60 Zoom:165% Fog:165%", FrameRateStats.getOverlayText(),
			"the window is only cleared by the loop thread");
		FrameRateStats.record(1, start + 1000L);
		assertEquals("Fps:0 Tps:0 Zoom:165% Fog:165%", FrameRateStats.getOverlayText());
	}

	/**
	 * Steps the easing until it reaches {@code target}, failing rather than
	 * hanging if it never converges.
	 */
	private static void settle(IntUnaryOperator step, int before, int target) {
		assertNotEquals(target, before, "the level should start away from its target");
		int current = before;
		for (int frame = 0; frame < 500 && current != target; frame++) {
			current = step.applyAsInt(CameraEasing.SMOOTH_TICK_MILLIS);
		}
		assertEquals(target, current, "easing should converge on the target");
	}
}
