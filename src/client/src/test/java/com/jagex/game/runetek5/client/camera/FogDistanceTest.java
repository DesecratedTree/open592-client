package com.jagex.game.runetek5.client.camera;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("FogDistance OSHD-style Alt+scroll fog")
class FogDistanceTest {

	@BeforeEach
	void resetFogState() {
		FogDistance.reset();
	}

	@ParameterizedTest(name = "clamp({0}) == {1}")
	@CsvSource({
		"0, 64",
		"-500, 64",
		"64, 64",
		"256, 256",
		"1024, 1024",
		"9999, 1024"
	})
	@DisplayName("clamp pins fog to FOG_MIN..FOG_MAX")
	void clamp_should_pinToRange_when_outOfBounds(int input, int expected) {
		assertEquals(expected, FogDistance.clamp(input));
	}

	@ParameterizedTest(name = "adjustTarget({0}) from 256 -> {1}")
	@CsvSource({
		"-1, 240",
		"1, 272",
		"-2, 224",
		"8, 384"
	})
	@DisplayName("adjustTarget moves target 16 units per notch, scroll-up closes fog in")
	void adjustTarget_should_stepByNotches_when_wheelMoves(int notches, int expectedTarget) {
		FogDistance.adjustTarget(notches);

		assertEquals(expectedTarget, FogDistance.getTargetFog());
		assertEquals(FogDistance.FOG_DEFAULT, FogDistance.getCurrentFog());
	}

	@Test
	@DisplayName("adjustTarget clamps at the fog limits")
	void adjustTarget_should_clamp_when_scrolledPastLimits() {
		FogDistance.adjustTarget(100);
		assertEquals(1024, FogDistance.getTargetFog());

		FogDistance.reset();
		FogDistance.adjustTarget(-100);
		assertEquals(64, FogDistance.getTargetFog());
	}

	@Test
	@DisplayName("smoothStep converges to the target without overshoot")
	void smoothStep_should_convergeWithoutOvershoot_when_targetAbove() {
		FogDistance.setTarget(512);

		int ticks = 0;
		int previous = FogDistance.getCurrentFog();
		while (FogDistance.getCurrentFog() != FogDistance.getTargetFog() && ticks < 1000) {
			int next = FogDistance.smoothStep();
			assertTrue(next >= previous && next <= 512,
				"step must move monotonically toward the target, got " + next);
			previous = next;
			ticks++;
		}

		assertEquals(512, FogDistance.getCurrentFog());
		assertTrue(ticks > 1, "smoothing must take several ticks, took " + ticks);
	}

	@Test
	@DisplayName("smoothStep converges downward without undershoot and snaps the last unit")
	void smoothStep_should_convergeAndSnap_when_targetBelow() {
		FogDistance.setForTest(300, 256);

		int ticks = 0;
		int previous = FogDistance.getCurrentFog();
		while (FogDistance.getCurrentFog() != FogDistance.getTargetFog() && ticks < 1000) {
			int next = FogDistance.smoothStep();
			assertTrue(next <= previous && next >= 256,
				"step must move monotonically toward the target, got " + next);
			previous = next;
			ticks++;
		}

		assertEquals(256, FogDistance.getCurrentFog());
		assertTrue(ticks > 1, "smoothing must take several ticks, took " + ticks);
	}

	@Test
	@DisplayName("getScale is 1.0 at default, 0.25 at min, 4.0 at max")
	void getScale_should_matchLevel_when_converged() {
		assertEquals(1.0f, FogDistance.getScale(), 0.0001f);

		convergeFog(64);
		assertEquals(0.25f, FogDistance.getScale(), 0.0001f);

		convergeFog(1024);
		assertEquals(4.0f, FogDistance.getScale(), 0.0001f);
	}

	@ParameterizedTest(name = "tickFog({0}, alt={1}, ctrl={2}) consumes={3}")
	@CsvSource({
		"3, true, false, true",
		"-2, true, false, true",
		"3, true, true, false",
		"3, false, false, false",
		"3, false, true, false",
		"0, true, false, false"
	})
	@DisplayName("fog consumes only Alt-without-Ctrl wheel input")
	void tickFog_should_consumeOnlyAltWithoutCtrl(int notches, boolean alt, boolean ctrl, boolean expected) {
		assertEquals(expected, CameraZoomController.tickFog(notches, alt, ctrl));
	}

	@Test
	@DisplayName("Alt+Ctrl leaves the fog target alone so zoom wins")
	void tickFog_should_leaveTargetAlone_when_ctrlAlsoHeld() {
		assertFalse(CameraZoomController.tickFog(3, true, true));
		assertEquals(FogDistance.FOG_DEFAULT, FogDistance.getTargetFog());
	}

	@Test
	@DisplayName("Alt-only moves the fog target by step times notches")
	void tickFog_should_moveTarget_when_altOnlyHeld() {
		assertTrue(CameraZoomController.tickFog(-2, true, false));
		assertEquals(FogDistance.FOG_DEFAULT - 2 * FogDistance.STEP_PER_NOTCH,
			FogDistance.getTargetFog());
	}

	@Test
	@DisplayName("scaleDistance tracks the smoothed fog level")
	void scaleDistance_should_scaleBase_when_converged() {
		assertEquals(984, FogDistance.scaleDistance(984));

		convergeFog(64);
		assertEquals(246, FogDistance.scaleDistance(984));

		convergeFog(1024);
		assertEquals(3936, FogDistance.scaleDistance(984));
	}

	private static void convergeFog(int target) {
		FogDistance.setTarget(target);
		for (int i = 0; i < 1000 && FogDistance.getCurrentFog() != target; i++) {
			FogDistance.smoothStep();
		}
	}
}
