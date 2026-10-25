package com.jagex.game.runetek5.client.hd.water;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jagex.game.runetek5.client.hd.ground.HdGroundWater;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("caustics term")
class HdCausticsTest {

	@Test
	@DisplayName("the row off is the identity, whatever the water and the clock say")
	void rowOff_shouldBeTheIdentity() {
		HdCaustics off = HdCaustics.of(false, HdGroundWater.of(200), 12.5F);

		assertAll(
			() -> assertEquals(HdCaustics.OFF, off),
			() -> assertEquals(0.0F, off.strength()),
			() -> assertEquals(0.0F, off.seconds(), "the clock is not even carried"),
			() -> assertFalse(off.active())
		);
	}

	@Test
	@DisplayName("a dry tile is the identity even with the row on")
	void dryTile_shouldBeTheIdentity() {
		assertAll(
			() -> assertEquals(HdCaustics.OFF, HdCaustics.of(true, HdGroundWater.DRY, 3.0F)),
			() -> assertEquals(HdCaustics.OFF, HdCaustics.of(true, HdGroundWater.of(0), 3.0F)),
			() -> assertEquals(HdCaustics.OFF, HdCaustics.of(true, null, 3.0F),
				"a caller with no water value must not get a pattern")
		);
	}

	@Test
	@DisplayName("strength is the stock depth byte over 255, reaching the maximum at full depth")
	void strength_shouldFollowTheStockDepthByte() {
		int[] depths = { 1, 32, 128, 255 };
		float previous = 0.0F;
		for (int depth : depths) {
			float strength = HdCaustics.of(true, HdGroundWater.of(depth), 0.0F).strength();
			float expected = HdCaustics.MAX_STRENGTH * depth / 255.0F;
			assertEquals(expected, strength, 1.0e-6F, "depth " + depth);
			assertTrue(strength > previous, "deeper water must focus more light, depth " + depth);
			previous = strength;
		}
		assertEquals(HdCaustics.MAX_STRENGTH, HdCaustics.of(true, HdGroundWater.of(255), 0.0F).strength(),
			1.0e-6F, "the stock byte's full scale is the row's full strength");
	}

	@Test
	@DisplayName("the clock rides the value, and a negative clock is no clock")
	void clock_shouldCarry() {
		assertAll(
			() -> assertEquals(2.5F, HdCaustics.of(true, HdGroundWater.of(200), 2.5F).seconds()),
			() -> assertEquals(0.0F, HdCaustics.of(true, HdGroundWater.of(200), -1.0F).seconds(),
				"a clock cannot run backwards")
		);
	}

	@Test
	@DisplayName("the identity is GL's initial uniform value, so an off draw uploads nothing")
	void off_shouldBeGlInitialValue() {
		assertAll(
			() -> assertEquals(0.0F, HdCaustics.OFF.strength(), "x is the strength"),
			() -> assertEquals(0.0F, HdCaustics.OFF.seconds(), "y is the clock"),
			() -> assertEquals(HdCaustics.OFF, HdCaustics.of(true, HdGroundWater.DRY, 8.0F),
				"every off path must land on the same value the uniform starts at"),
			() -> assertEquals(HdCaustics.OFF.hashCode(), HdCaustics.of(false, HdGroundWater.of(9), 1.0F).hashCode())
		);
	}

	@Test
	@DisplayName("the clock distinguishes two live terms, the strength alone does not")
	void equality_shouldIncludeTheClock() {
		HdCaustics first = HdCaustics.of(true, HdGroundWater.of(120), 1.0F);
		HdCaustics later = HdCaustics.of(true, HdGroundWater.of(120), 1.5F);

		assertAll(
			() -> assertEquals(HdCaustics.of(true, HdGroundWater.of(120), 1.0F), first),
			() -> assertNotEquals(first, later, "the pattern moved, so the upload must happen"),
			() -> assertTrue(first.active()),
			() -> assertTrue(first.toString().contains("caustics="))
		);
	}

	@Test
	@DisplayName("the tile's depth is clamped into the stock byte's range")
	void groundWater_shouldClamp() {
		assertAll(
			() -> assertEquals(0, HdGroundWater.of(-4).depth()),
			() -> assertTrue(HdGroundWater.of(-4) == HdGroundWater.DRY),
			() -> assertEquals(255, HdGroundWater.of(400).depth()),
			() -> assertTrue(HdGroundWater.of(0) == HdGroundWater.DRY),
			() -> assertEquals(1.0F, HdGroundWater.of(255).fraction(), 1.0e-6F),
			() -> assertFalse(HdGroundWater.DRY.submerged()),
			() -> assertTrue(HdGroundWater.of(1).submerged())
		);
	}
}
