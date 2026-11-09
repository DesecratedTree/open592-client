package com.jagex.game.runetek5.client.hd.shadow;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("which draws may cast")
class ShadowCasterGateTest {

	@Test
	@DisplayName("a water draw casts nothing, whatever it is blended by")
	void casts_shouldRefuseAWaterDraw() {
		assertAll(
			() -> assertFalse(ShadowCasterGate.casts(true, ShadowCasterGate.BLEND_OFF), "water, opaque"),
			() -> assertFalse(ShadowCasterGate.casts(true, ShadowCasterGate.BLEND_ALPHA), "water, alpha"),
			() -> assertFalse(ShadowCasterGate.casts(true, ShadowCasterGate.BLEND_ADDITIVE), "water, additive")
		);
	}

	@Test
	@DisplayName("only an additive or modulating effect is no occluder")
	void casts_shouldKeepOpaqueAndAlphaDraws() {
		assertAll(
			() -> assertTrue(ShadowCasterGate.casts(false, ShadowCasterGate.BLEND_OFF), "blending off"),
			() -> assertTrue(ShadowCasterGate.casts(false, ShadowCasterGate.BLEND_ALPHA), "ordinary alpha"),
			() -> assertFalse(ShadowCasterGate.casts(false, ShadowCasterGate.BLEND_ADDITIVE), "additive"),
			() -> assertFalse(ShadowCasterGate.casts(false, ShadowCasterGate.BLEND_MODULATING), "modulating")
		);
	}

	@Test
	@DisplayName("a refusal names which of upstream's tests refused the draw")
	void refusal_shouldNameTheRule() {
		assertAll(
			() -> assertEquals(ShadowCasterGate.Refusal.WATER,
				ShadowCasterGate.refusal(true, ShadowCasterGate.BLEND_MODULATING),
				"water owns the draw before any blend test does"),
			() -> assertEquals(ShadowCasterGate.Refusal.BLEND,
				ShadowCasterGate.refusal(false, ShadowCasterGate.BLEND_ADDITIVE)),
			() -> assertEquals(ShadowCasterGate.Refusal.BLEND,
				ShadowCasterGate.refusal(false, ShadowCasterGate.BLEND_MODULATING)),
			() -> assertEquals(ShadowCasterGate.Refusal.NONE,
				ShadowCasterGate.refusal(false, ShadowCasterGate.BLEND_OFF)),
			() -> assertEquals(ShadowCasterGate.Refusal.NONE,
				ShadowCasterGate.refusal(false, ShadowCasterGate.BLEND_ALPHA))
		);
	}
}
