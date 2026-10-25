package com.jagex.game.runetek5.client.hd.ground;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("terrain blend")
class HdTerrainBlendTest {

	@Test
	@DisplayName("the row on gives the mask its one strength and noise scale")
	void onRow_shouldGiveTheTerm() {
		HdTerrainBlend blend = HdTerrainBlend.of(true);

		assertAll(
			() -> assertTrue(blend.active()),
			() -> assertEquals(HdTerrainBlend.STRENGTH, blend.strength()),
			() -> assertEquals(HdTerrainBlend.MASK_SCALE, blend.maskScale())
		);
	}

	@Test
	@DisplayName("the row off is the identity, not a weaker blend")
	void offRow_shouldBeTheIdentity() {
		assertAll(
			() -> assertSame(HdTerrainBlend.OFF, HdTerrainBlend.of(false)),
			() -> assertFalse(HdTerrainBlend.OFF.active()),
			() -> assertEquals(0.0F, HdTerrainBlend.OFF.strength()),
			() -> assertEquals(0.0F, HdTerrainBlend.OFF.maskScale())
		);
	}

	@Test
	@DisplayName("two frames with the row on hold the same term")
	void term_shouldCompareByValue() {
		assertAll(
			() -> assertEquals(HdTerrainBlend.of(true), HdTerrainBlend.of(true)),
			() -> assertEquals(HdTerrainBlend.of(true).hashCode(), HdTerrainBlend.of(true).hashCode()),
			() -> assertFalse(HdTerrainBlend.of(true).equals(HdTerrainBlend.OFF))
		);
	}
}
