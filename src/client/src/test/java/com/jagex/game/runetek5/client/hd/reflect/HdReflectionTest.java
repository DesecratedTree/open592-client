package com.jagex.game.runetek5.client.hd.reflect;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("reflection term")
class HdReflectionTest {

	@Test
	@DisplayName("the row off is the identity, whatever the material's slot says")
	void rowOff_shouldBeTheIdentity() {
		HdReflection off = HdReflection.of(false, HdReflection.SPECULAR_SLOT);

		assertAll(
			() -> assertEquals(HdReflection.OFF, off),
			() -> assertEquals(0.0F, off.strength()),
			() -> assertFalse(off.active())
		);
	}

	@Test
	@DisplayName("only the stock specular slot reflects; every other slot is the identity")
	void onlyTheSpecularSlot_shouldReflect() {
		HdReflection reflecting = HdReflection.of(true, HdReflection.SPECULAR_SLOT);

		assertAll(
			() -> assertTrue(reflecting.active()),
			() -> assertEquals(HdReflection.STRENGTH, reflecting.strength()),
			() -> assertEquals(HdReflection.OFF, HdReflection.of(true, 0),
				"a plain material has no reflection to draw"),
			() -> assertEquals(HdReflection.OFF, HdReflection.of(true, 2)),
			() -> assertEquals(HdReflection.OFF, HdReflection.of(true, -1),
				"a draw with no material cannot reflect")
		);
	}

	@Test
	@DisplayName("the identity is GL's initial uniform value, so an off draw uploads nothing")
	void off_shouldBeGlInitialValue() {
		assertAll(
			() -> assertEquals(0.0F, HdReflection.OFF.strength()),
			() -> assertEquals(HdReflection.OFF.hashCode(), HdReflection.of(false, 1).hashCode()),
			() -> assertEquals(HdReflection.OFF, HdReflection.of(false, 8))
		);
	}

	@Test
	@DisplayName("the slot distinguishes a reflective material from a plain one")
	void equality_shouldFollowTheSlot() {
		HdReflection reflecting = HdReflection.of(true, HdReflection.SPECULAR_SLOT);

		assertAll(
			() -> assertEquals(reflecting, HdReflection.of(true, HdReflection.SPECULAR_SLOT)),
			() -> assertNotEquals(reflecting, HdReflection.of(true, 0)),
			() -> assertTrue(reflecting.toString().contains("reflection="))
		);
	}

	@Test
	@DisplayName("a specular alpha is a strength, not coverage: the surface stays opaque")
	void alphaCoverage_shouldRefuseStrengthMasks() {
		assertAll(
			() -> assertFalse(HdReflection.alphaIsCoverage(HdReflection.SPECULAR_SLOT, true),
				"slot 1 keeps its strength in alpha"),
			() -> assertFalse(HdReflection.alphaIsCoverage(7, true), "slot 7 keeps its strength in alpha"),
			() -> assertTrue(HdReflection.alphaIsCoverage(0, true), "an ordinary cut-out keeps its shape"),
			() -> assertTrue(HdReflection.alphaIsCoverage(0, false), "no alpha channel"),
			() -> assertTrue(HdReflection.isStrengthMask(HdReflection.SPECULAR_SLOT)),
			() -> assertFalse(HdReflection.isStrengthMask(0))
		);
	}
}
