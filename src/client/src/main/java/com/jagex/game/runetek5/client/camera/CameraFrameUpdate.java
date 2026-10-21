package com.jagex.game.runetek5.client.camera;

import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;

/**
 * Per-frame zoom/fog easing and the conditional fog push.
 *
 * <p>Both levels step through {@link CameraEasing}, so they share one curve
 * and reach their targets in the same wall-clock time at any frame rate. A
 * moved fog level is pushed to the renderer once; frame ordering belongs to
 * {@code client.frame.FramePresentation}.
 */
public final class CameraFrameUpdate {

	private static int lastAppliedFog = FogDistance.FOG_DEFAULT;

	private CameraFrameUpdate() {
	}

	/**
	 * Eases one frame and pushes a moved fog level.
	 *
	 * @param deltaMillis time since the previous frame; clamped, so a stall
	 *                    cannot jump the camera and a zero-length frame is a
	 *                    no-op.
	 */
	public static void step(long deltaMillis) {
		int delta = CameraEasing.clampDelta(deltaMillis);
		CameraZoom.smoothStep(delta);
		int fog = FogDistance.smoothStep(delta);
		if (fog != lastAppliedFog) {
			lastAppliedFog = fog;
			FileOnDisk.anInt4431 = FogDistance.scaledTileRadius();
			if (MonoStereoData.aClass19_16 != null) {
				MonoStereoData.aClass19_16.refreshFog();
			}
		}
	}

	/** Test hook: forgets the last pushed fog level. */
	static void resetForTest() {
		lastAppliedFog = FogDistance.FOG_DEFAULT;
	}
}
