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
		"0, 128",
		"-500, 128",
		"64, 128",
		"256, 256",
		"1024, 405",
		"9999, 405"
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
		assertEquals(405, FogDistance.getTargetFog());

		FogDistance.reset();
		FogDistance.adjustTarget(-100);
		assertEquals(128, FogDistance.getTargetFog());
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
		assertEquals(984, FogDistance.scaleDistance(984));

		FogDistance.setForTest(128, 128);
		assertEquals(492, FogDistance.scaleDistance(984));

		FogDistance.setForTest(405, 405);
		assertEquals(1556, FogDistance.scaleDistance(984));
	}

	@Test
	@DisplayName("scaleDistance saturates at the int limit instead of wrapping negative")
	void scaleDistance_shouldSaturate_whenScaledDistanceOverflows() {
		// Item icon generation renders into a 36x32 buffer with
		// ia(50, Integer.MAX_VALUE) to mean "no fog". Scaling that far plane
		// used to overflow for any fog level above the default, producing a
		// negative far plane that culled every triangle, so item sprites
		// came out blank in the inventory.
		int[] fogLevels = { FogDistance.FOG_MIN, FogDistance.FOG_DEFAULT, 272, FogDistance.FOG_MAX };
		for (int level : fogLevels) {
			FogDistance.setForTest(level, level);

			int far = FogDistance.scaleDistance(Integer.MAX_VALUE);
			assertTrue(far > 0, "far plane must stay positive for fog " + level);
			if (level >= FogDistance.FOG_DEFAULT) {
				assertEquals(Integer.MAX_VALUE, far, "far plane saturates for fog " + level);
			}
		}
	}

	@Test
	@DisplayName("scaleDistance keeps ordinary distances positive at every fog level")
	void scaleDistance_shouldStayPositive_when_sceneDistancesAreScaled() {
		int[] fogLevels = { FogDistance.FOG_MIN, FogDistance.FOG_DEFAULT, FogDistance.FOG_MAX };
		for (int level : fogLevels) {
			FogDistance.setForTest(level, level);

			assertTrue(FogDistance.scaleDistance(3584) > 0, "scene far plane for fog " + level);
			// Scaling must be monotonic in the fog level: farther fog never
			// draws the far plane closer than nearer fog does.
			assertTrue(FogDistance.scaleDistance(3584) >= 3584 * FogDistance.FOG_MIN / FogDistance.FOG_DEFAULT,
				"monotonic far plane for fog " + level);
		}
	}

	@Test
	@DisplayName("scaledTileRadius follows the smoothed fog level")
	void scaledTileRadius_should_scaleBase_atEachLimit() {
		FogDistance.setBaseTileRadius(32);
		assertEquals(32, FogDistance.scaledTileRadius());

		FogDistance.setForTest(128, 128);
		assertEquals(16, FogDistance.scaledTileRadius());

		FogDistance.setForTest(405, 405);
		assertEquals(50, FogDistance.scaledTileRadius());
	}

	@Test
	@DisplayName("maxTileRadius covers the widest fog setting")
	void maxTileRadius_should_fitWidestFog() {
		assertEquals(50, FogDistance.maxTileRadius(32));
		assertEquals(44, FogDistance.maxTileRadius(28));
	}
}
