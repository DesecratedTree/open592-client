package com.jagex.game.runetek5.client.hd.sway;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The displacement block: what the two rows contribute, what masking does, how
 * the viewer is recovered, and how the wind clock advances.
 *
 * <p>All of it is arithmetic a pixel test cannot isolate - a wrong viewer only
 * looks like "the grass moved the wrong way", and a clock that does not clamp
 * only shows up as a jump after a stalled frame.
 */
@DisplayName("592HD vegetation displacement block")
class HdSwayTest {

	@AfterEach
	void reset() {
		HdWindTime.reset();
	}

	@Test
	@DisplayName("Off is zero on both rows and the pipeline's initial state")
	void off_shouldBeZero() {
		assertAll(
			() -> assertEquals(0.0F, HdSway.OFF.foliage()),
			() -> assertEquals(0.0F, HdSway.OFF.grass()),
			() -> assertFalse(HdSway.OFF.active()),
			() -> assertSame(HdSway.OFF, HdSway.of(false, false, 4.0F, 1.0F, 2.0F, 3.0F),
				"neither row is the same block, whatever the clock says")
		);
	}

	@Test
	@DisplayName("each row contributes exactly its own amplitude")
	void amplitudes_shouldBeIndependent() {
		HdSway foliage = HdSway.of(true, false, 0.0F, 0.0F, 0.0F, 0.0F);
		HdSway grass = HdSway.of(false, true, 0.0F, 0.0F, 0.0F, 0.0F);
		HdSway both = HdSway.of(true, true, 0.0F, 0.0F, 0.0F, 0.0F);
		assertAll(
			() -> assertEquals(HdSway.FOLIAGE_AMPLITUDE, foliage.foliage()),
			() -> assertEquals(0.0F, foliage.grass()),
			() -> assertEquals(HdSway.GRASS_AMPLITUDE, grass.grass()),
			() -> assertEquals(0.0F, grass.foliage()),
			() -> assertEquals(HdSway.FOLIAGE_AMPLITUDE, both.foliage()),
			() -> assertEquals(HdSway.GRASS_AMPLITUDE, both.grass()),
			() -> assertTrue(both.active())
		);
	}

	@Test
	@DisplayName("masking removes an amplitude the draw's own rows do not claim")
	void masked_shouldFollowTheDraw() {
		HdSway both = HdSway.of(true, true, 2.0F, 5.0F, 6.0F, 7.0F);
		assertAll(
			() -> assertSame(both, both.masked(true, true), "nothing to remove"),
			() -> assertEquals(0.0F, both.masked(false, true).foliage(),
				"the foliage row is off for this draw, whatever the block carried"),
			() -> assertEquals(HdSway.GRASS_AMPLITUDE, both.masked(false, true).grass()),
			() -> assertEquals(0.0F, both.masked(true, false).grass()),
			() -> assertFalse(both.masked(false, false).active(),
				"both rows off is exactly zero, never a small lean"),
			// The phase and viewer survive masking: only the amplitudes are the
			// rows' business.
			() -> assertEquals(2.0F, both.masked(false, false).seconds()),
			() -> assertEquals(7.0F, both.masked(false, false).viewerZ())
		);
	}

	@Test
	@DisplayName("the viewer is recovered by inverting the scene-to-eye map")
	void ofScene_shouldRecoverTheViewer() {
		// flip * translate(0, -40, 120), the composition a frame's modelview has:
		// world (0, 40, -120) is the point that lands at the eye's origin.
		float[] sceneToEye = {
			1.0F, 0.0F, 0.0F, 0.0F,
			0.0F, -1.0F, 0.0F, 0.0F,
			0.0F, 0.0F, -1.0F, 0.0F,
			0.0F, 40.0F, -120.0F, 1.0F
		};
		HdSway sway = HdSway.ofScene(true, true, 3.0F, sceneToEye);
		assertAll(
			() -> assertEquals(0.0F, sway.viewerX(), 1.0E-5F),
			() -> assertEquals(40.0F, sway.viewerY(), 1.0E-5F),
			() -> assertEquals(-120.0F, sway.viewerZ(), 1.0E-5F),
			() -> assertEquals(3.0F, sway.seconds()),
			() -> assertTrue(sway.active())
		);
	}

	@Test
	@DisplayName("a missing matrix falls back to the origin without turning a row on")
	void ofScene_shouldTolerateAMissingMatrix() {
		HdSway sway = HdSway.ofScene(false, false, 1.0F, null);
		assertAll(
			() -> assertFalse(sway.active()),
			() -> assertEquals(0.0F, sway.viewerX())
		);
	}

	@Test
	@DisplayName("the block is a value: equal contents compare equal, and hash equal")
	void block_shouldCompareByValue() {
		HdSway first = HdSway.of(true, false, 1.0F, 2.0F, 3.0F, 4.0F);
		HdSway same = HdSway.of(true, false, 1.0F, 2.0F, 3.0F, 4.0F);
		HdSway moved = HdSway.of(true, false, 1.5F, 2.0F, 3.0F, 4.0F);
		assertAll(
			() -> assertEquals(first, same),
			() -> assertEquals(first.hashCode(), same.hashCode()),
			() -> assertNotEquals(first, moved, "a different phase is a different upload"),
			() -> assertNotEquals(first, HdSway.of(true, true, 1.0F, 2.0F, 3.0F, 4.0F))
		);
	}

	@Test
	@DisplayName("the wind clock accumulates frame time, clamps stalls and ignores non-steps")
	void clock_shouldAccumulateAndClamp() {
		HdWindTime.advance(16L);
		HdWindTime.advance(16L);
		assertEquals(0.032F, HdWindTime.seconds(), 1.0E-5F);

		// A stall is one clamped step, not a skipped gust.
		HdWindTime.advance(5_000L);
		assertEquals(0.132F, HdWindTime.seconds(), 1.0E-5F);

		HdWindTime.advance(0L);
		HdWindTime.advance(-40L);
		assertEquals(0.132F, HdWindTime.seconds(), 1.0E-5F);

		HdWindTime.reset();
		assertEquals(0.0F, HdWindTime.seconds());
	}

	@Test
	@DisplayName("the shipped constants describe a lean and a parting, not a nudge")
	void constants_shouldBeUsable() {
		assertAll(
			() -> assertTrue(HdSway.FOLIAGE_AMPLITUDE > 4.0F, "a lean has to be visible"),
			() -> assertTrue(HdSway.FOLIAGE_AMPLITUDE
				< com.jagex.game.runetek5.client.hd.ground.GroundTileSpace.UNITS_PER_TILE,
				"and smaller than a tile, or the plant leaves its place"),
			() -> assertTrue(HdSway.GRASS_AMPLITUDE > 0.0F),
			() -> assertTrue(HdSway.GRASS_RADIUS > HdSway.GRASS_AMPLITUDE * 4.0F,
				"the parting reaches well beyond its own amplitude"),
			() -> assertTrue(HdSway.HEIGHT_SCALE > 0.0F),
			() -> assertTrue(HdSway.WIND_SPEED > 0.0F)
		);
	}
}
