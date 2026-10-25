package com.jagex.game.runetek5.client.hd.post;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HD post-processing gate")
class HdPostGateTest {

	@Test
	@DisplayName("a row needs 592HD itself as well as its own value")
	void requested_shouldRequireHdAndTheRow() {
		assertTrue(HdPostGate.requested(true, true));
		assertFalse(HdPostGate.requested(true, false), "row off");
		assertFalse(HdPostGate.requested(false, true), "592HD off wins over a stored row");
		assertFalse(HdPostGate.requested(false, false));
	}

	@Test
	@DisplayName("an off parameter set is the identity, and any() says so")
	void paramsOff_shouldBeInert() {
		assertFalse(HdPostParams.OFF.any());
		assertFalse(HdPostParams.OFF.tonemap());
		assertFalse(HdPostParams.OFF.grading());
		assertFalse(HdPostParams.OFF.dither());
		assertFalse(HdPostParams.OFF.fxaa());
	}

	@Test
	@DisplayName("a single row is enough to make the pass run")
	void any_shouldFollowTheRows() {
		assertTrue(params(true, false, false, false).any());
		assertTrue(params(false, true, false, false).any());
		assertTrue(params(false, false, true, false).any());
		assertTrue(params(false, false, false, true).any());
		assertTrue(params(true, true, true, true).any());
	}

	@Test
	@DisplayName("dither is amplitude zero unless its row is on")
	void ditherAmount_shouldFollowTheRow() {
		assertTrue(params(false, false, false, false).ditherAmount() == 0.0F);
		assertTrue(params(false, false, true, false).ditherAmount() > 0.0F);
	}

	private static HdPostParams params(boolean tonemap, boolean grading, boolean dither, boolean fxaa) {
		return new HdPostParams(tonemap, grading, dither, fxaa, HdPostParams.EXPOSURE,
			HdPostParams.GAIN, HdPostParams.LIFT, HdPostParams.SATURATION, HdPostParams.DITHER_AMOUNT);
	}
}
