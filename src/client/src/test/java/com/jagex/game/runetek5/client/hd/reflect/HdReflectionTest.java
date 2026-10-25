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
}
