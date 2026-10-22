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
		"0, 100",
		"-500, 100",
		"64, 100",
		"100, 100",
		"140, 140",
		"155, 155",
		"165, 165",
		"9999, 165"
	})
	@DisplayName("clamp pins fog to FOG_MIN..FOG_MAX")
	void clamp_should_pinToRange_when_outOfBounds(int input, int expected) {
		assertEquals(expected, FogDistance.clamp(input));
	}

	@ParameterizedTest(name = "adjustTarget({0}) from 140 -> {1}")
	@CsvSource({
		"-1, 135",
		"1, 145",
		"-2, 130",
		"8, 165"
	})
	@DisplayName("adjustTarget moves target 5 points per notch, scroll-up closes fog in")
	void adjustTarget_should_stepByNotches_when_wheelMoves(int notches, int expectedTarget) {
		FogDistance.adjustTarget(notches);

		assertEquals(expectedTarget, FogDistance.getTargetFog());
		assertEquals(FogDistance.FOG_DEFAULT, FogDistance.getCurrentFog());
	}

	@ParameterizedTest(name = "setTarget({0}) == {1}")
	@CsvSource({
		"141, 140",
		"138, 140",
		"158, 160",
		"167, 165",
		"97, 100",
		"160, 160"
	})
	@DisplayName("setTarget snaps to the 5-point wheel grid, then clamps")
	void setTarget_should_snapToStepGrid(int requested, int expected) {
		FogDistance.setTarget(requested);

		assertEquals(expected, FogDistance.getTargetFog());
	}

	@Test
	@DisplayName("adjustTarget clamps at the fog limits")
	void adjustTarget_should_clamp_when_scrolledPastLimits() {
		FogDistance.adjustTarget(100);
		assertEquals(165, FogDistance.getTargetFog());

		FogDistance.reset();
		FogDistance.adjustTarget(-100);
		assertEquals(100, FogDistance.getTargetFog());
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
	void scaleDistance_should_scaleBase_atEachLimit() {
		FogDistance.setForTest(FogDistance.FOG_STOCK, FogDistance.FOG_STOCK);
		assertEquals(984, FogDistance.scaleDistance(984));

		FogDistance.setForTest(FogDistance.FOG_MIN, FogDistance.FOG_MIN);
		assertEquals(984, FogDistance.scaleDistance(984));

		FogDistance.setForTest(FogDistance.FOG_DEFAULT, FogDistance.FOG_DEFAULT);
		assertEquals(1377, FogDistance.scaleDistance(984));

		FogDistance.setForTest(FogDistance.FOG_MAX, FogDistance.FOG_MAX);
		assertEquals(1623, FogDistance.scaleDistance(984));
	}

	@Test
	@DisplayName("scaleDistance saturates instead of wrapping at every fog notch")
	void scaleDistance_should_saturate_when_farPlaneWouldOverflow() {
		for (int fog = FogDistance.FOG_MIN; fog <= FogDistance.FOG_MAX; fog += FogDistance.STEP_PER_NOTCH) {
			FogDistance.setForTest(fog, fog);

			assertTrue(FogDistance.scaleDistance(Integer.MAX_VALUE) > 0,
				"no-far-clip far plane must stay positive at fog=" + fog);
			assertTrue(FogDistance.scaleDistance(984) > 0, "far plane must stay positive at fog=" + fog);
			assertTrue(FogDistance.scaleDistance(3584) > 0, "scene far plane must stay positive at fog=" + fog);

			// Scaling the no-far-clip value can only grow it, so it pins at the
			// int limit instead of wrapping negative.
			if (fog >= FogDistance.FOG_STOCK) {
				assertEquals(Integer.MAX_VALUE, FogDistance.scaleDistance(Integer.MAX_VALUE),
					"no-far-clip far plane must saturate at fog=" + fog);
			}
		}
	}

	@Test
	@DisplayName("scaleDistance keeps stock-fog distances bit-identical")
	void scaleDistance_should_beIdentity_when_fogIsStock() {
		FogDistance.setForTest(FogDistance.FOG_STOCK, FogDistance.FOG_STOCK);

		assertEquals(984, FogDistance.scaleDistance(984));
		assertEquals(3584, FogDistance.scaleDistance(3584));
		assertEquals(Integer.MAX_VALUE, FogDistance.scaleDistance(Integer.MAX_VALUE));
	}

	@Test
	@DisplayName("scaledTileRadius follows the smoothed fog level")
	void scaledTileRadius_should_scaleBase_atEachLimit() {
		FogDistance.setBaseTileRadius(32);
		assertEquals(44, FogDistance.scaledTileRadius(), "32 tiles at the 140% default");

		FogDistance.setForTest(FogDistance.FOG_STOCK, FogDistance.FOG_STOCK);
		assertEquals(32, FogDistance.scaledTileRadius(), "stock distance at 100%");

		FogDistance.setForTest(FogDistance.FOG_MIN, FogDistance.FOG_MIN);
		assertEquals(32, FogDistance.scaledTileRadius(), "100% is the closest fog comes");

		FogDistance.setForTest(FogDistance.FOG_MAX, FogDistance.FOG_MAX);
		assertEquals(52, FogDistance.scaledTileRadius());
	}

	@Test
	@DisplayName("maxTileRadius covers the widest fog setting")
	void maxTileRadius_should_fitWidestFog() {
		assertEquals(52, FogDistance.maxTileRadius(32));
		assertEquals(46, FogDistance.maxTileRadius(28));
	}
}
