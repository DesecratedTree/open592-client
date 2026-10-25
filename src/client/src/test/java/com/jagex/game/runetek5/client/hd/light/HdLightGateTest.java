package com.jagex.game.runetek5.client.hd.light;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The shared gate for the carried and room rows, as a truth table.
 *
 * <p>The precedence is the point: these rows add terms to the per-pixel
 * program, so they cannot exist without it, they must be on, and they must have
 * a source. Any one of those failing leaves the stock draw untouched - not
 * "nearly", and not with a fallback colour.
 */
@DisplayName("592HD carried and room light gate")
class HdLightGateTest {

	@Test
	@DisplayName("a row needs 592HD, its own switch and per-pixel lighting")
	void rowGate_shouldBeAThreeWayAnd() {
		boolean[] values = { false, true };
		for (int i = 0; i < 8; i++) {
			boolean hd = values[i & 1];
			boolean row = values[i >> 1 & 1];
			boolean perPixel = values[i >> 2 & 1];
			boolean expected = hd && row && perPixel;
			assertAll(
				() -> assertTrue(expected == HdLightGate.carriedRequested(hd, row, perPixel),
					"carried(" + hd + "," + row + "," + perPixel + ")"),
				() -> assertTrue(expected == HdLightGate.roomRequested(hd, row, perPixel),
					"room(" + hd + "," + row + "," + perPixel + ")")
			);
		}
	}

	@Test
	@DisplayName("a carried row without a source contributes nothing")
	void carriedSource_shouldBeRequired() {
		assertAll(
			() -> assertFalse(HdLightGate.carriedHasSource(null)),
			() -> assertFalse(HdLightGate.carriedHasSource(new HdLightSource(0, 0, 0, 512, 0xFFFFFF, 0.0F))),
			() -> assertTrue(HdLightGate.carriedHasSource(new HdLightSource(0, 0, 0, 512, 0xFFFFFF, 1.0F)))
		);
	}

	@Test
	@DisplayName("a room row with no lit region contributes nothing")
	void roomSource_shouldBeRequired() {
		HdRoomLight lit = new HdRoomLight(0, 0, 1024, 1024, 0xFFFFFF, 0.35F);
		HdRoomLight dark = new HdRoomLight(0, 0, 1024, 1024, 0xFFFFFF, 0.0F);
		assertAll(
			() -> assertFalse(HdLightGate.roomHasSource(null)),
			() -> assertFalse(HdLightGate.roomHasSource(new HdRoomLight[0])),
			() -> assertFalse(HdLightGate.roomHasSource(new HdRoomLight[] { null, dark })),
			() -> assertTrue(HdLightGate.roomHasSource(new HdRoomLight[] { dark, lit }))
		);
	}

	@Test
	@DisplayName("a zero-strength region is not uploaded, so no empty rectangle reaches the shader")
	void darkRegion_shouldNotCount() {
		HdRoomLight dark = new HdRoomLight(0, 0, 1024, 1024, 0xFFFFFF, 0.0F);
		assertTrue(dark.isDark());
		assertFalse(new HdRoomLight(0, 0, 1024, 1024, 0xFFFFFF, 0.01F).isDark());
	}
}
