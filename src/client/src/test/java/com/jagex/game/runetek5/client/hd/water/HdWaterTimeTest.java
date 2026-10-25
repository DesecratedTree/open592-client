package com.jagex.game.runetek5.client.hd.water;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HD water animation clock")
class HdWaterTimeTest {

	@BeforeEach
	void reset() {
		HdWaterTime.reset();
	}

	@Test
	@DisplayName("the same milliseconds give the same seconds at any frame rate")
	void seconds_shouldBeFrameRateIndependent() {
		for (int i = 0; i < 100; i++) {
			HdWaterTime.advance(10L);
		}
		float tenFpsStyle = HdWaterTime.seconds();

		HdWaterTime.reset();
		for (int i = 0; i < 200; i++) {
			HdWaterTime.advance(5L);
		}
		float otherRate = HdWaterTime.seconds();

		assertAll(
			() -> assertEquals(1.0F, tenFpsStyle, 1.0E-4F),
			() -> assertEquals(tenFpsStyle, otherRate, 1.0E-4F)
		);
	}

	@Test
	@DisplayName("a negative or zero step is a no-op")
	void nonPositiveStep_shouldNotMove() {
		HdWaterTime.advance(50L);
		HdWaterTime.advance(0L);
		HdWaterTime.advance(-500L);

		assertEquals(0.05F, HdWaterTime.seconds(), 1.0E-5F);
	}

	@Test
	@DisplayName("a long frame is clamped, so a stall cannot skip the waves")
	void longFrame_shouldBeClamped() {
		HdWaterTime.advance(5000L);

		assertAll(
			() -> assertEquals(HdWaterTime.MAX_STEP_MILLIS / 1000.0F, HdWaterTime.seconds(), 1.0E-5F),
			() -> assertTrue(HdWaterTime.seconds() <= 0.1F)
		);
	}

	@Test
	@DisplayName("reset returns to zero, and the test hook sets an exact time")
	void resetAndHook_shouldBeExact() {
		HdWaterTime.setForTest(2.5F);
		float set = HdWaterTime.seconds();
		HdWaterTime.reset();

		assertAll(
			() -> assertEquals(2.5F, set, 1.0E-5F),
			() -> assertEquals(0.0F, HdWaterTime.seconds(), 0.0F)
		);
	}
}
