package com.jagex.game.runetek5.client.hd.water;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HD water gate")
class HdWaterGateTest {

	/** A breaking material's numbers: depth 64, offset 0.25, exponent 2, scale 0.25. */
	private static final HdWaterBreaking BREAKING = HdWaterBreaking.of((byte) 24, 64 | 2 << 16 | 2 << 27);

	@Test
	@DisplayName("only the reflective water slot is reflective, and only slot 9 is breaking")
	void waterTypes_shouldBeTheStockSlots() {
		assertTrue(HdWaterGate.isReflectiveMaterial(HdWaterGate.REFLECTIVE_SLOT),
			"slot 8 is ReflectiveRipplingWaterEffect's");
		assertEquals(8, HdWaterGate.REFLECTIVE_SLOT);

		assertFalse(HdWaterGate.isReflectiveMaterial(3), "still water");
		assertFalse(HdWaterGate.isReflectiveMaterial(4), "the effect slot the stock manager substitutes for 8");
		assertFalse(HdWaterGate.isReflectiveMaterial(HdWaterGate.BREAKING_SLOT), "breaking water is a different effect");
		assertFalse(HdWaterGate.isReflectiveMaterial(0), "no effect");
		assertFalse(HdWaterGate.isReflectiveMaterial(-1));

		assertTrue(HdWaterGate.isBreakingMaterial(HdWaterGate.BREAKING_SLOT),
			"slot 9 is BreakingWaterEffect's");
		assertEquals(9, HdWaterGate.BREAKING_SLOT);
		assertFalse(HdWaterGate.isBreakingMaterial(HdWaterGate.REFLECTIVE_SLOT));
		assertFalse(HdWaterGate.isBreakingMaterial(3), "still water");
		assertFalse(HdWaterGate.isBreakingMaterial(-1));
	}

	@Test
	@DisplayName("both reflecting water types take the environment cube, the still slot does not")
	void samplesEnvironment_shouldCoverTheTwoReflectingTypes() {
		assertAll(
			() -> assertTrue(HdWaterGate.samplesEnvironment(HdWaterGate.REFLECTIVE_SLOT)),
			() -> assertTrue(HdWaterGate.samplesEnvironment(HdWaterGate.BREAKING_SLOT)),
			() -> assertFalse(HdWaterGate.samplesEnvironment(3), "still water samples the fog colour"),
			() -> assertFalse(HdWaterGate.samplesEnvironment(4)),
			() -> assertFalse(HdWaterGate.samplesEnvironment(0)),
			() -> assertFalse(HdWaterGate.samplesEnvironment(-1))
		);
	}

	@Test
	@DisplayName("a reflection needs the row, the type, the cube and both programs")
	void shouldReflect_shouldRequireAllFour() {
		HdWaterRequest reflective = HdWaterRequest.current(true, true, true, 0.0F);
		assertTrue(HdWaterGate.shouldReflect(reflective, true, true));

		assertFalse(HdWaterGate.shouldReflect(reflective.reflection(false, true), true, true), "a calm material");
		assertFalse(HdWaterGate.shouldReflect(reflective.reflection(true, false), true, true), "no environment cube");
		assertFalse(HdWaterGate.shouldReflect(reflective, false, true), "the calm program did not link");
		assertFalse(HdWaterGate.shouldReflect(reflective, true, false), "the reflective program did not link");
		assertFalse(HdWaterGate.shouldReflect(HdWaterRequest.current(false, true, true, 0.0F), true, true),
			"the row off is the stock draw");
		// Even the whole reflection half cannot outvote a draw that is not HD water.
		assertFalse(HdWaterGate.shouldReflect(reflective, false, false));
	}

	@Test
	@DisplayName("only the row's third value asks for the custom surface")
	void custom_shouldBeTheThirdValueOnly() {
		assertTrue(HdWaterGate.custom(true, HdWaterGate.STYLE_CUSTOM));

		assertFalse(HdWaterGate.custom(true, HdWaterGate.STYLE_STANDARD), "On is the standard surface");
		assertFalse(HdWaterGate.custom(true, HdWaterGate.STYLE_OFF), "Off compiles nothing");
		assertFalse(HdWaterGate.custom(false, HdWaterGate.STYLE_CUSTOM), "592HD off means no program at all");
		assertTrue(HdWaterGate.STYLE_OFF < HdWaterGate.STYLE_STANDARD
			&& HdWaterGate.STYLE_STANDARD < HdWaterGate.STYLE_CUSTOM, "the values are the row's order");
	}

	@Test
	@DisplayName("the surface ladder: reflection outranks the style, the style outranks standard")
	void surface_shouldFollowTheLadder() {
		HdWaterRequest custom = HdWaterRequest.still(true, true, 0.0F);
		assertEquals(HdWaterGate.Surface.CUSTOM, HdWaterGate.surface(custom, true, true, false, false),
			"the row's Custom value with its program linked");
		assertEquals(HdWaterGate.Surface.STILL, HdWaterGate.surface(custom, true, false, false, false),
			"a custom program that did not link is the standard surface, not no water");
		assertEquals(HdWaterGate.Surface.STILL,
			HdWaterGate.surface(HdWaterRequest.still(true, 0.0F), true, true, true, true),
			"On is the standard surface, even with every program linked");

		HdWaterRequest reflective = HdWaterRequest.current(true, true, true, true, 0.0F);
		assertEquals(HdWaterGate.Surface.REFLECTIVE, HdWaterGate.surface(reflective, true, true, true, true),
			"the water type's own reflection outranks the surface style");
		assertEquals(HdWaterGate.Surface.CUSTOM,
			HdWaterGate.surface(reflective.reflection(true, false), true, true, true, true),
			"a reflective material without a cube falls back to the chosen surface");
		assertEquals(HdWaterGate.Surface.CUSTOM, HdWaterGate.surface(reflective, true, true, false, true),
			"and so does one whose reflective program did not link");
	}

	@Test
	@DisplayName("a breaking material runs its own surface, and the style never takes it away")
	void breaking_shouldOutrankTheStyle() {
		// A breaking material is that type and not the reflective one, so the
		// reflection half is off and only the breaking band is asking for a cube.
		HdWaterRequest breaking = HdWaterRequest.current(true, true, false, true, 0.0F).withBreaking(BREAKING);
		assertTrue(breaking.breaking());
		assertFalse(breaking.reflective());

		assertAll(
			() -> assertEquals(HdWaterGate.Surface.BREAKING, HdWaterGate.surface(breaking, true, true, false, true)),
			() -> assertEquals(HdWaterGate.Surface.BREAKING,
				HdWaterGate.surface(breaking.style(true), true, true, false, true),
				"the type's band belongs to the material, not to the row's third value"),
			// Without the cube it reflects, the type has nothing to sample.
			() -> assertEquals(HdWaterGate.Surface.STILL,
				HdWaterGate.surface(breaking.reflection(false, false), true, true, false, true)),
			// A driver that refuses the breaking program costs the band, not the
			// water - and it is calm water, not the style's surface.
			() -> assertEquals(HdWaterGate.Surface.STILL, HdWaterGate.surface(breaking, true, true, false, false)),
			// Nor does the row being off leave a breaking material drawing anything.
			() -> assertEquals(HdWaterGate.Surface.NONE,
				HdWaterGate.surface(HdWaterRequest.current(false, true, true, true, 0.0F).withBreaking(BREAKING),
					true, true, true, true))
		);
	}

	@Test
	@DisplayName("a breaking draw needs the row, the type, its numbers, the cube and the program")
	void shouldBreak_shouldRequireEveryFact() {
		HdWaterRequest breaking = HdWaterRequest.current(true, true, true, true, 0.0F).withBreaking(BREAKING);

		assertTrue(HdWaterGate.shouldBreak(breaking, true, true));
		assertFalse(HdWaterGate.shouldBreak(breaking.withBreaking(HdWaterBreaking.NONE), true, true),
			"a calm material's draw is not breaking");
		assertFalse(HdWaterGate.shouldBreak(breaking.reflection(false, false), true, true), "no cube");
		assertFalse(HdWaterGate.shouldBreak(breaking, false, true), "the base program did not link");
		assertFalse(HdWaterGate.shouldBreak(breaking, true, false), "the breaking program did not link");
		assertFalse(HdWaterGate.shouldBreak(HdWaterRequest.current(false, true, true, true, 0.0F)
			.withBreaking(BREAKING), true, true), "the row off is the stock draw");
	}

	@Test
	@DisplayName("without the base program, or with the row off, nothing is drawn")
	void surface_shouldBeNoneWithoutTheBaseProgram() {
		assertAll(
			() -> assertEquals(HdWaterGate.Surface.NONE,
				HdWaterGate.surface(HdWaterRequest.still(true, true, 0.0F), false, true, true, true),
				"the standard program is the fallback for the others, so it must link first"),
			() -> assertEquals(HdWaterGate.Surface.NONE,
				HdWaterGate.surface(HdWaterRequest.still(false, true, 0.0F), true, true, true, true),
				"the row off is the stock draw, whatever the style"),
			() -> assertEquals(HdWaterGate.Surface.NONE,
				HdWaterGate.surface(HdWaterRequest.current(false, true, true, true, 0.0F), true, true, true, true),
				"a reflective material cannot outvote the row")
		);
	}

	@Test
	@DisplayName("the row is stored, not the program state: Off is off with or without 592HD")
	void requested_shouldBeTheRowAndHd() {
		assertTrue(HdWaterGate.requested(true, true));
		assertFalse(HdWaterGate.requested(true, false));
		assertFalse(HdWaterGate.requested(false, true), "a stock client cannot run the program");
		assertFalse(HdWaterGate.requested(false, false));
	}
}
