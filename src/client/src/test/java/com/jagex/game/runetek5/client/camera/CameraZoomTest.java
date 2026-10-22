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
		"0, 100",
		"-500, 100",
		"64, 100",
		"100, 100",
		"165, 165",
		"180, 180",
		"200, 200",
		"9999, 200"
	})
	@DisplayName("clamp pins zoom to ZOOM_MIN..ZOOM_MAX")
	void clamp_should_pinToRange_when_outOfBounds(int input, int expected) {
		assertEquals(expected, CameraZoom.clamp(input));
	}

	@ParameterizedTest(name = "adjustTarget({0}) from 165 -> {1}")
	@CsvSource({
		"-1, 160",
		"1, 170",
		"-2, 155",
		"6, 195",
		"7, 200",
		"8, 200"
	})
	@DisplayName("adjustTarget moves target 5 points per notch, scroll-up zooms in")
	void adjustTarget_should_stepByNotches_when_wheelMoves(int notches, int expectedTarget) {
		CameraZoom.adjustTarget(notches);

		assertAll(
			() -> assertEquals(expectedTarget, CameraZoom.getTargetZoom()),
			() -> assertEquals(CameraZoom.ZOOM_DEFAULT, CameraZoom.getCurrentZoom())
		);
	}

	@ParameterizedTest(name = "setTarget({0}) == {1}")
	@CsvSource({
		"166, 165",
		"163, 165",
		"183, 185",
		"202, 200",
		"97, 100",
		"190, 190"
	})
	@DisplayName("setTarget snaps to the 5-point wheel grid, then clamps")
	void setTarget_should_snapToStepGrid(int requested, int expected) {
		CameraZoom.setTarget(requested);

		assertEquals(expected, CameraZoom.getTargetZoom());
	}

	@Test
	@DisplayName("adjustTarget clamps at the zoom limits")
	void adjustTarget_should_clamp_when_scrolledPastLimits() {
		CameraZoom.adjustTarget(100);
		assertEquals(200, CameraZoom.getTargetZoom());

		CameraZoom.reset();
		CameraZoom.adjustTarget(-100);
		assertEquals(100, CameraZoom.getTargetZoom());
	}

	@ParameterizedTest(name = "computeDistance({0}) == {1} at stock zoom")
	@CsvSource({
		"1024, 984",
		"2048, 1368",
		"3072, 1752"
	})
	@DisplayName("computeDistance derives base distance from pitch at 100% (stock) zoom")
	void computeDistance_should_matchBaseFormula_when_zoomIsStock(int pitch, int expected) {
		CameraZoom.setForTest(CameraZoom.ZOOM_STOCK, CameraZoom.ZOOM_STOCK);

		assertEquals(expected, CameraZoom.computeDistance(pitch));
		assertEquals(expected, CameraZoom.scaleDistance(expected));
	}

	@Test
	@DisplayName("the default zoom pulls the camera 165% of the stock distance back")
	void computeDistance_should_scaleFromStock_when_zoomIsDefault() {
		assertEquals(984 * CameraZoom.ZOOM_DEFAULT / 100, CameraZoom.computeDistance(1024));
	}

	@Test
	@DisplayName("computeDistance scales with the smoothed zoom level")
	void computeDistance_should_scaleWithZoom_atEachLimit() {
		CameraZoom.setForTest(CameraZoom.ZOOM_MIN, CameraZoom.ZOOM_MIN);
		assertEquals(984 * 100 / 100, CameraZoom.computeDistance(1024));

		CameraZoom.setForTest(CameraZoom.ZOOM_DEFAULT, CameraZoom.ZOOM_DEFAULT);
		assertEquals(984 * 165 / 100, CameraZoom.computeDistance(1024));

		CameraZoom.setForTest(CameraZoom.ZOOM_MAX, CameraZoom.ZOOM_MAX);
		assertEquals(984 * 200 / 100, CameraZoom.computeDistance(1024));
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
