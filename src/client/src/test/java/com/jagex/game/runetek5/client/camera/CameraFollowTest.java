package com.jagex.game.runetek5.client.camera;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("CameraFollow snappy follow-camera step")
class CameraFollowTest {

	@Test
	@DisplayName("a camera already on the player stays there")
	void smoothStep_shouldHold_whenAlreadyOnTarget() {
		assertEquals(150, CameraFollow.smoothStep(150, 150, 5L));
	}

	@ParameterizedTest(name = "smoothStep({0}, {1}, {2}) == {3}")
	@CsvSource({
		// one full response time covers the whole gap, without overshooting
		"0, 100, 24, 100",
		"100, 0, 24, 0",
		"0, -100, 24, -100",
		// half a response time covers about half the gap
		"0, 100, 12, 50",
		"100, 0, 12, 50",
		// longer than one response time still stops exactly on the target
		"0, 100, 240, 100",
		"-100, 0, 240, 0"
	})
	@DisplayName("the step scales with elapsed time and never overshoots")
	void smoothStep_shouldScaleWithDelta(int current, int target, long deltaMillis, int expected) {
		assertEquals(expected, CameraFollow.smoothStep(current, target, deltaMillis));
	}

	@Test
	@DisplayName("no elapsed time holds the camera where it is")
	void smoothStep_shouldHold_whenDeltaIsNotPositive() {
		assertEquals(40, CameraFollow.smoothStep(40, 100, 0L));
		assertEquals(40, CameraFollow.smoothStep(40, 100, -16L));
	}

	@Test
	@DisplayName("a very short frame still advances a unit instead of stalling")
	void smoothStep_shouldAdvanceAtLeastOneUnit_onTinyDelta() {
		// 1 ms * 10 units / 24 ms rounds to zero without the floor.
		assertEquals(1, CameraFollow.smoothStep(0, 10, 1L));
		assertEquals(-1, CameraFollow.smoothStep(0, -10, 1L));
	}

	@Test
	@DisplayName("the last unit snaps instead of crawling")
	void smoothStep_shouldSnap_withinOneUnit() {
		assertEquals(100, CameraFollow.smoothStep(99, 100, 0L));
		assertEquals(100, CameraFollow.smoothStep(101, 100, 0L));
	}

	@Test
	@DisplayName("walking at two tiles per 600 ms keeps the camera within a fraction of a tile")
	void smoothStep_shouldTrailOnlySlightly_whilePlayerWalks() {
		// 2 tiles / 600 ms in scene units. Stock's 320 ms response left roughly
		// half a tile (~64 units) of trailing; the snappy follow must not.
		int target = 0;
		int camera = 0;
		int worstGap = 0;
		long delta = 5L;
		long walkedMillis = 0L;
		for (int frame = 0; frame < 600; frame++) {
			target = (int) (256L * walkedMillis / 600L);
			camera = CameraFollow.smoothStep(camera, target, delta);
			worstGap = Math.max(worstGap, Math.abs(target - camera));
			walkedMillis += delta;
		}

		assertTrue(worstGap < 16, "camera trailed the player by " + worstGap + " units");
	}

	@Test
	@DisplayName("either axis over the jump limit counts as a teleport")
	void shouldJump_shouldUseTheStrictStockThreshold() {
		assertFalse(CameraFollow.shouldJump(0, 0));
		assertFalse(CameraFollow.shouldJump(CameraFollow.JUMP_DISTANCE, 0));
		assertFalse(CameraFollow.shouldJump(0, -CameraFollow.JUMP_DISTANCE));
		assertTrue(CameraFollow.shouldJump(CameraFollow.JUMP_DISTANCE + 1, 0));
		assertTrue(CameraFollow.shouldJump(0, -CameraFollow.JUMP_DISTANCE - 1));
		// A scene reload moves both axes; the small one must snap too.
		assertTrue(CameraFollow.shouldJump(512, 12));
	}
}
