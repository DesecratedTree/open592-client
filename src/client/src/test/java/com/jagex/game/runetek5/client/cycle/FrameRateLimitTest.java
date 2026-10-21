package com.jagex.game.runetek5.client.cycle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("FrameRateLimit frame-rate ceiling policy")
class FrameRateLimitTest {

	@BeforeEach
	void resetLimit() {
		FrameRateLimit.reset();
	}

	@Test
	@DisplayName("the out-of-box ceiling is 180 fps")
	void default_shouldBe180() {
		assertEquals(180, FrameRateLimit.DEFAULT_FPS);
		assertEquals(FrameRateLimit.DEFAULT_FPS, FrameRateLimit.getConfiguredFps());
	}

	@ParameterizedTest(name = "clamp({0}) == {1}")
	@CsvSource({
		"-500, 0",
		"-1, 0",
		"0, 0",
		"60, 60",
		"180, 180",
		"1000, 1000",
		"5000, 1000"
	})
	@DisplayName("clamp pins the ceiling to MIN_FPS..MAX_FPS")
	void clamp_shouldPinToRange(int input, int expected) {
		assertEquals(expected, FrameRateLimit.clamp(input));
	}

	@Test
	@DisplayName("setConfiguredFps clamps and reports the stored ceiling")
	void setConfiguredFps_shouldClamp() {
		assertEquals(240, FrameRateLimit.setConfiguredFps(240));
		assertEquals(240, FrameRateLimit.getConfiguredFps());

		assertEquals(FrameRateLimit.MAX_FPS, FrameRateLimit.setConfiguredFps(100000));
		assertEquals(FrameRateLimit.MIN_FPS, FrameRateLimit.setConfiguredFps(-1));

		FrameRateLimit.reset();
		assertEquals(FrameRateLimit.DEFAULT_FPS, FrameRateLimit.getConfiguredFps());
	}

	@ParameterizedTest(name = "targetFps(configured={0}, focused={1}, tickMs={2}) == {3}")
	@CsvSource({
		"180, true, 20, 180",
		"0, true, 20, 0",
		"5000, true, 20, 1000",
		"180, false, 20, 50",
		"0, false, 20, 50",
		"30, false, 20, 30",
		"180, false, 10, 100",
		"180, false, 0, 180"
	})
	@DisplayName("a focused window keeps its ceiling, a background one drops to the tick rate")
	void targetFps_shouldResolveCeiling_whenFocusChanges(int configured, boolean focused, int tickMs, int expected) {
		assertEquals(expected, FrameRateLimit.targetFps(configured, focused, tickMs));
	}
}
