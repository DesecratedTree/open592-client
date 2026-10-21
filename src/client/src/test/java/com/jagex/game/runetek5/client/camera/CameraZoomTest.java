package com.jagex.game.runetek5.client.camera;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("CameraZoom OSHD-style zoom")
class CameraZoomTest {

	@BeforeEach
	void resetZoomState() {
		CameraZoom.reset();
	}

	@ParameterizedTest(name = "clamp({0}) == {1}")
	@CsvSource({
		"0, 128",
		"-500, 128",
		"96, 128",
		"128, 128",
		"256, 256",
		"512, 512",
		"768, 512",
		"9999, 512"
	})
	@DisplayName("clamp pins zoom to ZOOM_MIN..ZOOM_MAX")
	void clamp_should_pinToRange_when_outOfBounds(int input, int expected) {
		assertEquals(expected, CameraZoom.clamp(input));
	}

	@ParameterizedTest(name = "adjustTarget({0}) from 256 -> {1}")
	@CsvSource({
		"-1, 232",
		"1, 280",
		"-2, 208",
		"8, 448"
	})
	@DisplayName("adjustTarget moves target 24 units per notch, scroll-up zooms in")
	void adjustTarget_should_stepByNotches_when_wheelMoves(int notches, int expectedTarget) {
		CameraZoom.adjustTarget(notches);

		assertAll(
			() -> assertEquals(expectedTarget, CameraZoom.getTargetZoom()),
			() -> assertEquals(CameraZoom.ZOOM_DEFAULT, CameraZoom.getCurrentZoom())
		);
	}

	@Test
	@DisplayName("adjustTarget clamps at the zoom limits")
	void adjustTarget_should_clamp_when_scrolledPastLimits() {
		CameraZoom.adjustTarget(100);
		assertEquals(512, CameraZoom.getTargetZoom());

		CameraZoom.reset();
		CameraZoom.adjustTarget(-100);
		assertEquals(128, CameraZoom.getTargetZoom());
	}

	@ParameterizedTest(name = "computeDistance({0}) == {1} at default zoom")
	@CsvSource({
		"1024, 984",
		"2048, 1368",
		"3072, 1752"
	})
	@DisplayName("computeDistance derives base distance from pitch at 100% zoom")
	void computeDistance_should_matchBaseFormula_when_zoomIsDefault(int pitch, int expected) {
		assertEquals(expected, CameraZoom.computeDistance(pitch));
		assertEquals(expected, CameraZoom.scaleDistance(expected));
	}

	@Test
	@DisplayName("computeDistance scales with the smoothed zoom level")
	void computeDistance_should_scaleWithZoom_when_zoomedToLimits() {
		CameraZoom.setForTest(128, 128);
		assertEquals(984 * 128 / 256, CameraZoom.computeDistance(1024));

		CameraZoom.setForTest(512, 512);
		assertEquals(984 * 512 / 256, CameraZoom.computeDistance(1024));
	}

	@ParameterizedTest(name = "tick({0}, ctrl={1}, shift={2}) consumes={3}")
	@CsvSource({
		"3, true, false, true",
		"-2, true, false, true",
		"3, true, true, false",
		"3, false, false, false",
		"3, false, true, false",
		"0, true, false, false"
	})
	@DisplayName("controller consumes only Ctrl-without-Shift wheel input")
	void tick_should_consumeOnlyCtrlWithoutShift(int notches, boolean ctrl, boolean shift, boolean expected) {
		assertEquals(expected, CameraZoomController.tick(notches, ctrl, shift));
	}

	@Test
	@DisplayName("Ctrl+Shift leaves the target alone for the legacy debug path")
	void tick_should_leaveTargetAlone_when_ctrlAndShiftHeld() {
		assertFalse(CameraZoomController.tick(3, true, true));
		assertEquals(CameraZoom.ZOOM_DEFAULT, CameraZoom.getTargetZoom());
	}

	@Test
	@DisplayName("Ctrl-only moves the target by step times notches")
	void tick_should_moveTarget_when_ctrlOnlyHeld() {
		assertTrue(CameraZoomController.tick(-2, true, false));
		assertEquals(CameraZoom.ZOOM_DEFAULT - 2 * CameraZoom.STEP_PER_NOTCH,
			CameraZoom.getTargetZoom());
	}
}
