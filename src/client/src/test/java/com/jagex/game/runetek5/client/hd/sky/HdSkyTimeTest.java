package com.jagex.game.runetek5.client.hd.sky;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The day/night clock: a delta accumulator, so equal elapsed time is equal sun
 * at any frame rate, and no elapsed time is no motion.
 */
@DisplayName("592HD day and night clock")
class HdSkyTimeTest {

	private static final float EPSILON = 1.0e-6F;

	@BeforeEach
	void useTemporaryPreferences() throws IOException {
		HdSettings.setDirectoryForTest(Files.createTempDirectory("hd-sky-time").toFile());
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.SKY, 1);
		HdSettings.set(HdFeature.DAY_NIGHT, 2);
		HdSkyTime.reset();
	}

	@AfterEach
	void resetSettings() {
		HdSettings.resetForTest();
		HdSkyTime.reset();
	}

	@Test
	@DisplayName("a fresh clock is at noon and stays there while nothing advances it")
	void phase_shouldNotMoveOnItsOwn() {
		float phase = HdSkyTime.phase();
		HdSkyTime.advance(0L);
		HdSkyTime.advance(-16L);
		assertAll(
			() -> assertEquals(HdSkyTime.START_PHASE, phase, EPSILON),
			() -> assertEquals(0.5F, phase, EPSILON, "noon"),
			() -> assertEquals(phase, HdSkyTime.phase(), 0.0F, "no deltas, no motion")
		);
	}

	@Test
	@DisplayName("one day of frames is one turn, whatever the frame time")
	void advance_shouldTurnOncePerDay() {
		// 600000 ms of 10-minute days, at the 100 ms the clock clamps to, is
		// exactly one turn: 6000 full steps.
		for (int step = 0; step < 6000; step++) {
			HdSkyTime.advance(100L);
		}
		assertEquals(HdSkyTime.START_PHASE, HdSkyTime.phase(), 1.0e-4F);

		// The same elapsed time in 30 ms frames must land in the same place:
		// a frame-rate independent clock, like the water.
		HdSkyTime.reset();
		for (int step = 0; step < 2000; step++) {
			HdSkyTime.advance(100L);
		}
		float afterThird = HdSkyTime.phase();
		HdSkyTime.reset();
		for (int step = 0; step < 6666; step++) {
			HdSkyTime.advance(30L);
		}
		HdSkyTime.advance(20L);
		assertEquals(afterThird, HdSkyTime.phase(), 1.0e-3F);
	}

	@Test
	@DisplayName("a stalled frame advances no more than the clamp allows")
	void advance_shouldClampLongFrames() {
		HdSkyTime.setForTest(0.0F);
		// A ten second stall in a ten minute day would be 1/60 of a turn if it
		// were trusted; the clock takes the 100 ms clamp instead.
		HdSkyTime.advance(10000L);
		float expected = (float) (HdSkyTime.MAX_STEP_MILLIS / 600000.0D);
		assertEquals(expected, HdSkyTime.phase(), 1.0e-6F);
		assertTrue(HdSkyTime.phase() < 0.001F, "a stall must not skip a sunset");
	}

	@Test
	@DisplayName("the phase wraps instead of growing without bound")
	void phase_shouldWrap() {
		HdSkyTime.setForTest(0.99F);
		HdSkyTime.advance(10000L);
		float phase = HdSkyTime.phase();
		assertAll(
			() -> assertTrue(phase >= 0.0F && phase < 1.0F, "phase must stay in [0, 1): " + phase),
			() -> assertEquals(0.99F + (float) (100.0D / 600000.0D), phase, 1.0e-6F)
		);
	}

	@Test
	@DisplayName("turning the clock off freezes it, and back on continues from there")
	void stoppedClock_shouldHoldItsPhase() {
		HdSkyTime.setForTest(0.3F);
		HdSettings.set(HdFeature.DAY_NIGHT, 0);
		for (int step = 0; step < 100; step++) {
			HdSkyTime.advance(100L);
		}
		assertEquals(0.3F, HdSkyTime.phase(), EPSILON, "Off is a stopped clock, not a reset");

		HdSettings.set(HdFeature.DAY_NIGHT, 2);
		HdSkyTime.advance(100L);
		assertEquals(0.3F + (float) (100.0D / 600000.0D), HdSkyTime.phase(), 1.0e-6F);
	}

	@Test
	@DisplayName("changing the day length changes the speed, not the hour")
	void changingTheDayLength_shouldNotJumpTheSun() {
		HdSkyTime.setForTest(0.4F);
		HdSettings.set(HdFeature.DAY_NIGHT, 2);
		HdSkyTime.advance(100L);
		float afterShortDay = HdSkyTime.phase();

		HdSettings.set(HdFeature.DAY_NIGHT, 4);
		assertEquals(afterShortDay, HdSkyTime.phase(), EPSILON, "the row itself moves nothing");
		HdSkyTime.advance(100L);
		float slowStep = HdSkyTime.phase() - afterShortDay;
		assertEquals((float) (100.0D / 3600000.0D), slowStep, 1.0e-7F, "an hour-long day is six times slower");
	}

	@Test
	@DisplayName("592HD off stops the clock too")
	void hdOff_shouldFreezeTheClock() {
		HdSkyTime.setForTest(0.7F);
		HdSettings.setEnabled(false, null);
		HdSkyTime.advance(100L);
		assertEquals(0.7F, HdSkyTime.phase(), EPSILON);
	}

	@Test
	@DisplayName("the test hook wraps like the clock does")
	void testHook_shouldWrap() {
		HdSkyTime.setForTest(1.25F);
		assertEquals(0.25F, HdSkyTime.phase(), EPSILON);
		HdSkyTime.setForTest(-0.25F);
		assertEquals(0.75F, HdSkyTime.phase(), EPSILON);
	}
}
