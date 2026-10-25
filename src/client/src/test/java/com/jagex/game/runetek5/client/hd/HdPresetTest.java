package com.jagex.game.runetek5.client.hd;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayName("592HD presets")
class HdPresetTest {

	@Test
	@DisplayName("Off is the stock switch: no assignments and disabled")
	void off_shouldTouchNothing() {
		assertAll(
			() -> assertFalse(HdPreset.OFF.enabled()),
			() -> assertTrue(HdPreset.OFF.assignments().isEmpty())
		);
	}

	@Test
	@DisplayName("Custom is not a preset a player can select")
	void custom_shouldNotBeAButton() {
		for (HdPreset button : HdPreset.buttons()) {
			assertFalse(button == HdPreset.CUSTOM);
		}
	}

	@Test
	@DisplayName("the shipped default is Medium, enabled")
	void defaultPreset_shouldBeMedium() {
		assertAll(
			() -> assertEquals(HdPreset.MEDIUM, HdPreset.defaultPreset()),
			() -> assertTrue(HdPreset.defaultPreset().enabled())
		);
	}

	@Test
	@DisplayName("Medium carries the ported 634HD feature set")
	void medium_shouldMatchThePortedTable() {
		assertAll(
			() -> assertEquals(1, HdPreset.MEDIUM.value(HdFeature.FOG)),
			() -> assertEquals(1, HdPreset.MEDIUM.value(HdFeature.SKY)),
			() -> assertEquals(1, HdPreset.MEDIUM.value(HdFeature.TEXTURES)),
			() -> assertEquals(1, HdPreset.MEDIUM.value(HdFeature.GROUND_BLENDING)),
			() -> assertEquals(2, HdPreset.MEDIUM.value(HdFeature.SHADOWS)),
			() -> assertEquals(1, HdPreset.MEDIUM.value(HdFeature.PER_PIXEL_LIGHTING)),
			() -> assertEquals(1, HdPreset.MEDIUM.value(HdFeature.POINT_LIGHTS)),
			() -> assertEquals(1, HdPreset.MEDIUM.value(HdFeature.TONEMAP)),
			() -> assertEquals(2, HdPreset.MEDIUM.value(HdFeature.RENDER_DISTANCE))
		);
	}

	@Test
	@DisplayName("Ultra raises AA, draw distance, shadows and filtering over High")
	void ultra_shouldExceedHigh() {
		assertAll(
			() -> assertEquals(2, HdPreset.ULTRA.value(HdFeature.ANTI_ALIASING)),
			() -> assertEquals(1, HdPreset.HIGH.value(HdFeature.ANTI_ALIASING)),
			() -> assertEquals(3, HdPreset.ULTRA.value(HdFeature.RENDER_DISTANCE)),
			() -> assertEquals(3, HdPreset.ULTRA.value(HdFeature.SHADOW_MAP)),
			() -> assertEquals(4, HdPreset.ULTRA.value(HdFeature.ANISOTROPIC))
		);
	}

	@ParameterizedTest(name = "{0} enables everything Medium enables")
	@EnumSource(value = HdPreset.class, names = { "MEDIUM", "HIGH", "ULTRA" })
	@DisplayName("each step keeps the features the previous step turned on")
	void higherPresets_shouldKeepMediumFeatures(HdPreset preset) {
		for (Map.Entry<HdFeature, Integer> entry : HdPreset.MEDIUM.assignments().entrySet()) {
			if (entry.getValue().intValue() == 0) {
				continue;
			}
			assertTrue(preset.value(entry.getKey()) > 0,
				preset + " dropped " + entry.getKey());
		}
	}

	@ParameterizedTest(name = "{0} values are in range")
	@EnumSource(HdPreset.class)
	@DisplayName("every preset assignment is a legal value for its feature")
	void assignments_shouldBeInRange(HdPreset preset) {
		for (Map.Entry<HdFeature, Integer> entry : preset.assignments().entrySet()) {
			int value = entry.getValue().intValue();
			assertTrue(value >= 0 && value < entry.getKey().valueCount(),
				preset + " " + entry.getKey() + "=" + value);
		}
	}

	@Test
	@DisplayName("preset buttons keep the 634HD order")
	void buttons_shouldKeepOrder() {
		HdPreset[] buttons = HdPreset.buttons();
		assertAll(
			() -> assertEquals(HdPreset.OFF, buttons[0]),
			() -> assertEquals(HdPreset.LOW, buttons[1]),
			() -> assertEquals(HdPreset.MEDIUM, buttons[2]),
			() -> assertEquals(HdPreset.HIGH, buttons[3]),
			() -> assertEquals(HdPreset.ULTRA, buttons[4])
		);
	}

	@Test
	@DisplayName("preset keys are parsed case-insensitively")
	void forKey_shouldIgnoreCase() {
		assertAll(
			() -> assertEquals(HdPreset.HIGH, HdPreset.forKey("high")),
			() -> assertEquals(HdPreset.OFF, HdPreset.forKey("OFF")),
			() -> assertEquals(null, HdPreset.forKey("nonsense"))
		);
	}
}
