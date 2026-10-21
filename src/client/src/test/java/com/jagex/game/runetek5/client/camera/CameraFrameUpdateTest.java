package com.jagex.game.runetek5.client.camera;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jagex.game.runetek5.cache.FileOnDisk;

/**
 * The easing curve itself is covered by {@link CameraEasingTest}; these tests
 * cover what only the frame step does: move both levels together and push a
 * moved fog level to the renderer.
 */
@DisplayName("CameraFrameUpdate per-frame camera easing")
class CameraFrameUpdateTest {

	@BeforeEach
	void resetCameraState() {
		CameraZoom.reset();
		FogDistance.reset();
		CameraFrameUpdate.resetForTest();
	}

	@Test
	@DisplayName("zoom and fog converge together, without overshoot")
	void step_shouldConvergeBothLevels() {
		CameraZoom.setTarget(512);
		FogDistance.setTarget(405);

		for (int frame = 0; frame < 120; frame++) {
			CameraFrameUpdate.step(20L);
			assertTrue(CameraZoom.getCurrentZoom() <= 512, "frame " + frame);
			assertTrue(FogDistance.getCurrentFog() <= 405, "frame " + frame);
		}

		assertEquals(512, CameraZoom.getCurrentZoom());
		assertEquals(405, FogDistance.getCurrentFog());
	}

	@Test
	@DisplayName("a moved fog level is pushed to the renderer as a tile radius")
	void step_shouldPushMovedFog() {
		FogDistance.setBaseTileRadius(32);
		FogDistance.setTarget(405);

		for (int frame = 0; frame < 120; frame++) {
			CameraFrameUpdate.step(20L);
		}

		assertEquals(50, FogDistance.scaledTileRadius());
		assertEquals(FogDistance.scaledTileRadius(), FileOnDisk.anInt4431);
	}

	@Test
	@DisplayName("an unchanged fog level is not re-pushed every frame")
	void step_shouldNotPush_whenFogDidNotMove() {
		FogDistance.setBaseTileRadius(32);
		FogDistance.setTarget(405);
		for (int frame = 0; frame < 120; frame++) {
			CameraFrameUpdate.step(20L);
		}
		FileOnDisk.anInt4431 = -1;

		CameraFrameUpdate.step(20L);

		assertEquals(-1, FileOnDisk.anInt4431);
	}
}
