package com.jagex.game.runetek5.client.hd.water;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jagex.game.runetek5.client.hd.content.HdMaterialDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The type a material's water block resolves to: 634HD's WATER defaults, the
 * same fields carried through, the clamps the content parser applies, and the
 * plain-water rule the surface's quarter darkening depends on.
 */
@DisplayName("HD water types")
class HdWaterTypeTest {

	@Test
	@DisplayName("the default is 634HD's WATER type")
	void default_shouldBeTheWaterType() {
		HdWaterType type = HdWaterType.DEFAULT;
		assertAll(
			() -> assertEquals(0x69809C, type.surface()),
			() -> assertEquals(0x00758E, type.depth()),
			() -> assertEquals(0xB0A492, type.foam()),
			() -> assertEquals(0.5F, type.opacity()),
			() -> assertEquals(0.5F, type.specular()),
			() -> assertEquals(500.0F, type.gloss()),
			() -> assertEquals(0.09F, type.normalStrength()),
			() -> assertEquals(0.85F, type.fresnel()),
			() -> assertEquals(1.0F, type.duration()),
			() -> assertTrue(type.hasFoam()),
			() -> assertFalse(type.flat()),
			() -> assertTrue(type.plain(), "WATER's Fresnel amount is upstream's plain-water rule")
		);
	}

	@Test
	@DisplayName("a material's own block is its own type")
	void of_shouldCarryTheMaterialsBlock() {
		// The swamp water in the shipped materials.json: dark green, opaque, a soft
		// glint, a slower period, and not plain water.
		HdWaterType swamp = HdWaterType.of(0x172114, 0x737865, 0x29521A, 0.8F, 0.1F, 100.0F, 0.05F,
			0.3F, 1.2F, true, true);
		assertAll(
			() -> assertEquals(0x172114, swamp.surface()),
			() -> assertEquals(0x29521A, swamp.depth()),
			() -> assertEquals(0x737865, swamp.foam()),
			() -> assertEquals(0.8F, swamp.opacity()),
			() -> assertEquals(0.1F, swamp.specular()),
			() -> assertEquals(100.0F, swamp.gloss()),
			() -> assertEquals(0.05F, swamp.normalStrength()),
			() -> assertEquals(0.3F, swamp.fresnel()),
			() -> assertEquals(1.2F, swamp.duration()),
			() -> assertTrue(swamp.flat()),
			() -> assertFalse(swamp.plain(), "0.3 is not WATER's Fresnel amount"),
			() -> assertFalse(swamp.sameAs(HdWaterType.DEFAULT))
		);
	}

	@Test
	@DisplayName("a material with no water block is the default type, not a blank one")
	void of_shouldFallBackToTheDefault() {
		assertAll(
			() -> assertSame(HdWaterType.DEFAULT,
				HdWaterType.of((HdMaterialDefinition) null)),
			() -> assertSame(HdWaterType.DEFAULT,
				HdWaterType.of((HdMaterialDefinition.Water) null))
		);
	}

	@Test
	@DisplayName("the fields clamp the way the content parser clamps them")
	void of_shouldClamp() {
		HdWaterType type = HdWaterType.of(0xFFFFFF, 0, 0, 4.0F, -1.0F, 5000.0F, 2.0F, 2.0F, 0.0F,
			false, true);
		assertAll(
			() -> assertEquals(1.0F, type.opacity()),
			() -> assertEquals(0.0F, type.specular()),
			() -> assertEquals(2000.0F, type.gloss()),
			() -> assertEquals(1.0F, type.normalStrength()),
			() -> assertEquals(1.0F, type.fresnel()),
			() -> assertTrue(type.duration() > 0.0F,
				"a zero period would divide by zero in the ripple layers")
		);
	}

	@Test
	@DisplayName("the colours divide straight through to sRGB components")
	void colours_shouldBeSrgbComponents() {
		float[] surface = HdWaterType.DEFAULT.surfaceColour();
		float[] depth = HdWaterType.DEFAULT.depthColour();
		float[] foam = HdWaterType.DEFAULT.foamColour();
		assertAll(
			() -> assertEquals(0x69 / 255.0F, surface[0], 1.0e-6F),
			() -> assertEquals(0x80 / 255.0F, surface[1], 1.0e-6F),
			() -> assertEquals(0x9C / 255.0F, surface[2], 1.0e-6F),
			() -> assertEquals(0x00 / 255.0F, depth[0], 1.0e-6F),
			() -> assertEquals(0x75 / 255.0F, depth[1], 1.0e-6F),
			() -> assertEquals(0x8E / 255.0F, depth[2], 1.0e-6F),
			() -> assertEquals(0xB0 / 255.0F, foam[0], 1.0e-6F),
			() -> assertEquals(0xA4 / 255.0F, foam[1], 1.0e-6F),
			() -> assertEquals(0x92 / 255.0F, foam[2], 1.0e-6F)
		);
	}

	@Test
	@DisplayName("two types with the same fields compare equal, so the upload can be skipped")
	void sameAs_shouldCompareEveryField() {
		HdWaterType one = HdWaterType.of(0x102030, 0x405060, 0x708090, 0.4F, 1.5F, 300.0F, 0.2F,
			0.6F, 2.0F, false, true);
		HdWaterType same = HdWaterType.of(0x102030, 0x405060, 0x708090, 0.4F, 1.5F, 300.0F, 0.2F,
			0.6F, 2.0F, false, true);
		HdWaterType other = HdWaterType.of(0x102031, 0x405060, 0x708090, 0.4F, 1.5F, 300.0F, 0.2F,
			0.6F, 2.0F, false, true);
		assertAll(
			() -> assertTrue(one.sameAs(same)),
			() -> assertFalse(one.sameAs(other)),
			() -> assertFalse(one.sameAs(null))
		);
	}
}
