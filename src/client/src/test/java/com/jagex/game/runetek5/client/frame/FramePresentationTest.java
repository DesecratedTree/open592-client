package com.jagex.game.runetek5.client.frame;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jagex.game.runetek5.client.camera.CameraZoom;
import com.jagex.game.runetek5.client.camera.FogDistance;
import com.jagex.game.runetek5.client.input.mouse.MiddleMouseCamera;

@DisplayName("FramePresentation per-frame boundary")
class FramePresentationTest {

	private FramePresentation presentation;

	@BeforeEach
	void setUp() {
		CameraZoom.reset();
		FogDistance.reset();
		this.presentation = new FramePresentation(new MiddleMouseCamera());
	}

	@Test
	@DisplayName("a frame eases the camera for exactly that frame's delta")
	void beginFrame_shouldEaseForTheFrameDelta() {
		CameraZoom.setTarget(448);

		this.presentation.beginFrame(20L);

		assertEquals(304, CameraZoom.getCurrentZoom());
	}

	@Test
	@DisplayName("a zero-length frame advances nothing")
	void beginFrame_shouldNotMove_whenDeltaIsNotPositive() {
		CameraZoom.setTarget(448);

		this.presentation.beginFrame(0L);
		this.presentation.beginFrame(-16L);

		assertEquals(CameraZoom.ZOOM_DEFAULT, CameraZoom.getCurrentZoom());
	}
}
