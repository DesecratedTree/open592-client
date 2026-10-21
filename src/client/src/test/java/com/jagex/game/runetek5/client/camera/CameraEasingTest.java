package com.jagex.game.runetek5.client.camera;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("CameraEasing shared time-normalised curve")
class CameraEasingTest {

	private static final int ONE_TICK = CameraEasing.SMOOTH_TICK_MILLIS;

	@Test
	@DisplayName("one reference tick takes a quarter of the remaining distance")
	void stepTowards_shouldTakeAQuarterPerTick() {
		// Also the legacy identity: a 20 ms frame is the stock per-tick step.
		assertEquals(304, CameraEasing.stepTowards(256, 448, ONE_TICK));
		assertEquals(289, CameraEasing.stepTowards(300, 256, ONE_TICK));
	}

	@Test
	@DisplayName("the last unit snaps instead of crawling")
	void stepTowards_shouldSnapTheLastUnit() {
		assertEquals(256, CameraEasing.stepTowards(255, 256, ONE_TICK));
		assertEquals(256, CameraEasing.stepTowards(257, 256, ONE_TICK));
	}

	@ParameterizedTest(name = "stepTowards(256, 448, {0}) == 256 (unchanged)")
	@ValueSource(ints = { 0, -1, -250 })
	@DisplayName("a zero-length or negative frame is a no-op")
	void stepTowards_shouldNotMove_whenDeltaIsNotPositive(int deltaMillis) {
		assertEquals(256, CameraEasing.stepTowards(256, 448, deltaMillis));
	}

	@ParameterizedTest(name = "delta={0} ms")
	@ValueSource(ints = { 1, 2, 3, 5, 7, 10, 16, 33, 100, 249, 250 })
	@DisplayName("every frame length converges monotonically without overshoot")
	void stepTowards_shouldConvergeMonotonically(int deltaMillis) {
		int previous = 256;
		int steps = 0;
		while (previous != 448 && steps < 100000) {
			int next = CameraEasing.stepTowards(previous, 448, deltaMillis);
			assertTrue(next >= previous && next <= 448,
				"delta " + deltaMillis + " produced " + next);
			previous = next;
			steps++;
		}

		assertEquals(448, previous);
	}

	@ParameterizedTest(name = "stepTowards(448, 256, {0})")
	@ValueSource(ints = { 1, 5, 20, 33, 250 })
	@DisplayName("downward easing mirrors upward easing")
	void stepTowards_shouldConvergeDownward(int deltaMillis) {
		int previous = 448;
		int steps = 0;
		while (previous != 256 && steps < 100000) {
			int next = CameraEasing.stepTowards(previous, 256, deltaMillis);
			assertTrue(next <= previous && next >= 256,
				"delta " + deltaMillis + " produced " + next);
			previous = next;
			steps++;
		}

		assertEquals(256, previous);
	}

	@Test
	@DisplayName("a long frame is clamped, so a hitch cannot jump the value")
	void stepTowards_shouldClampLongFrames() {
		assertEquals(CameraEasing.stepTowards(256, 4096, CameraEasing.SMOOTH_MAX_STEP_MILLIS),
			CameraEasing.stepTowards(256, 4096, 60000));
	}

	@Test
	@DisplayName("easing speed does not depend on the frame rate")
	void stepTowards_shouldEaseAtSameRate_whenFrameRateDiffers() {
		int fast = 256;
		for (int frame = 0; frame < 20; frame++) {
			fast = CameraEasing.stepTowards(fast, 448, 5);
		}

		int slow = 256;
		for (int frame = 0; frame < 5; frame++) {
			slow = CameraEasing.stepTowards(slow, 448, 20);
		}

		assertTrue(Math.abs(fast - slow) <= 2, "100 ms at 200 fps reached " + fast
			+ ", at 50 fps reached " + slow);
	}

	@ParameterizedTest(name = "clampDelta({0}) == {1}")
	@CsvSource({
		"-1, 0",
		"0, 0",
		"5, 5",
		"249, 249",
		"250, 250",
		"251, 250",
		"60000, 250"
	})
	@DisplayName("clampDelta bounds a frame to the easing range")
	void clampDelta_shouldBound(long deltaMillis, int expected) {
		assertEquals(expected, CameraEasing.clampDelta(deltaMillis));
	}
}
