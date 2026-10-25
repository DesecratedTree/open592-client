package com.jagex.game.runetek5.client.frame;

import com.jagex.game.runetek5.client.camera.CameraFrameUpdate;
import com.jagex.game.runetek5.client.hd.sky.HdSkyTime;
import com.jagex.game.runetek5.client.hd.sway.HdWindTime;
import com.jagex.game.runetek5.client.hd.water.HdWaterTime;
import com.jagex.game.runetek5.client.input.mouse.MiddleMouseCamera;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.preference.Preference;

/**
 * The frame boundary: everything presentation advances once per frame, before
 * any drawing.
 *
 * <p>Stock did this inline at the top of {@code client.method894()}, an
 * ordering held together only by line order. Here the order is structural:
 * sample the freshest pointer into mouse-look, then ease zoom and fog (and
 * push a moved fog level). Only presentation belongs here; game state still
 * advances solely in the tick.
 */
public final class FramePresentation {

	private final MiddleMouseCamera mouseLook;

	public FramePresentation(MiddleMouseCamera mouseLook) {
		this.mouseLook = mouseLook;
	}

	/**
	 * Advances presentation for one frame.
	 *
	 * @param deltaMillis milliseconds since the previous frame; the camera
	 *                    layer clamps it, so long frames cannot overshoot.
	 */
	public void beginFrame(long deltaMillis) {
		this.sampleMouseLook();
		CameraFrameUpdate.step(deltaMillis);
		// Water waves are presentation too: the same delta that eases the camera
		// drives them, so they move at the same speed at any frame rate.
		HdWaterTime.advance(deltaMillis);
		// Vegetation leans on the same delta and for the same reason: a copse
		// that swayed per tick would speed up with the tick rate, and one that read
		// a wall clock would jump when a paused client resumed.
		HdWindTime.advance(deltaMillis);
		// The day/night clock turns on the same delta, so a day length is real
		// minutes of rendering rather than frames, and a paused client's sun
		// stands still instead of jumping when it resumes.
		HdSkyTime.advance(deltaMillis);
	}

	/**
	 * Feeds the newest pointer sample into mouse-look.
	 *
	 * <p>The recorder reports negative coordinates once the pointer has left
	 * the canvas. Those are not a position, so the accumulator is reset
	 * instead: orbiting by the sentinel would spin the camera. This guard
	 * lives on the frame path only — the tick's own latch and click queue are
	 * untouched.
	 */
	private void sampleMouseLook() {
		Preference recorder = MaterialType.aClass80_1;
		if (recorder == null) {
			return;
		}
		int latestX = recorder.getLatestX();
		int latestY = recorder.getLatestY();
		if (latestX >= 0 && latestY >= 0) {
			this.mouseLook.update(latestX, latestY, recorder.method2237());
		} else {
			this.mouseLook.reset();
		}
	}
}
