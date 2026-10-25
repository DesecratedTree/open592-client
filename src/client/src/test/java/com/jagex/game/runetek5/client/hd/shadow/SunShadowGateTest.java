package com.jagex.game.runetek5.client.hd.shadow;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The gate's whole job is precedence, so every combination is checked: the
 * shader and the row must both be on and the target must exist before a depth
 * pass runs, and a missing target must fall back to unshadowed lighting rather
 * than to a black frame.
 */
@DisplayName("sun shadow gate")
class SunShadowGateTest {

	@Test
	@DisplayName("shadows need 592HD, per-pixel lighting and the row")
	void requested_shouldRequireAllThree() {
		assertAll(
			() -> assertTrue(SunShadowGate.requested(true, true, 2)),
			() -> assertFalse(SunShadowGate.requested(false, true, 2), "592HD off"),
			() -> assertFalse(SunShadowGate.requested(true, false, 2), "no shader to project into"),
			() -> assertFalse(SunShadowGate.requested(true, true, 0), "row off"),
			() -> assertFalse(SunShadowGate.requested(false, false, 0))
		);
	}

	@Test
	@DisplayName("a requested pass still needs a created target")
	void shouldRender_shouldRequireAReadyMap() {
		assertAll(
			() -> assertTrue(SunShadowGate.shouldRender(true, true, 1, true)),
			() -> assertFalse(SunShadowGate.shouldRender(true, true, 1, false), "no target: no pass"),
			() -> assertFalse(SunShadowGate.shouldRender(true, true, 0, true), "row off beats a ready map"),
			() -> assertFalse(SunShadowGate.shouldRender(false, true, 1, true))
		);
	}

	@Test
	@DisplayName("the row labels are the map sizes")
	void resolution_shouldMatchTheRowLabels() {
		assertAll(
			() -> assertEquals(0, SunShadowGate.resolution(0), "Off"),
			() -> assertEquals(1024, SunShadowGate.resolution(1)),
			() -> assertEquals(2048, SunShadowGate.resolution(2)),
			() -> assertEquals(4096, SunShadowGate.resolution(3)),
			() -> assertEquals(0, SunShadowGate.resolution(-1), "out of range is Off"),
			() -> assertEquals(0, SunShadowGate.resolution(9), "out of range is Off")
		);
	}

	@Test
	@DisplayName("requested sizes clamp into what the target can allocate")
	void clampResolution_shouldStayInRange() {
		assertAll(
			() -> assertEquals(SunShadowGate.MIN_SIZE, SunShadowGate.clampResolution(1)),
			() -> assertEquals(SunShadowGate.MIN_SIZE, SunShadowGate.clampResolution(-8)),
			() -> assertEquals(1024, SunShadowGate.clampResolution(1024)),
			() -> assertEquals(SunShadowGate.MAX_SIZE, SunShadowGate.clampResolution(8192))
		);
	}

	@Test
	@DisplayName("lighting detail selects a 3x3 or 5x5 PCF kernel")
	void pcfRadius_shouldFollowDetail() {
		assertAll(
			() -> assertEquals(1, SunShadowGate.pcfRadius(0), "Low"),
			() -> assertEquals(2, SunShadowGate.pcfRadius(1), "High"),
			() -> assertTrue(SunShadowGate.pcfRadius(1) > SunShadowGate.pcfRadius(0), "High is softer")
		);
	}
}
