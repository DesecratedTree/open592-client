package com.jagex.game.runetek5.client.hd.material;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import com.jagex.game.runetek5.client.hd.content.HdContent;
import com.jagex.game.runetek5.client.hd.content.HdEnvironmentDefinition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The area's sun and ambient tint, from {@code environments.json}.
 *
 * <p>The stock client already uploads a level ambient and a sun colour, so this
 * class adds the one datum it has no field for: the <em>tint</em> the area's
 * environment asks for. Off must be the identity - a multiplier of exactly 1 -
 * or a frame with no area would quietly darken the whole scene.
 */
@DisplayName("592HD area light")
class HdAreaLightTest {

	@AfterEach
	void forgetArea() {
		HdAreaLight.resetForTest();
	}

	@Test
	@DisplayName("the environment the area was resolved from is published for the water")
	void environment_shouldBeTheCapturedAreas() {
		assertNull(HdAreaLight.environment(), "a harness with no player stands in no area");

		HdAreaLight.publishForTest(HdAreaLight.of(environment()), null);
		assertNull(HdAreaLight.environment(), "a published light states no environment");

		HdAreaLight.resetForTest();
		assertNull(HdAreaLight.environment(), "resetting must forget the area");
	}

	@Test
	@DisplayName("an environment's sun and ambient become the frame's tint")
	void environment_shouldBecomeTheTint() {
		HdAreaLight light = HdAreaLight.of(environment());

		assertAll(
			() -> assertTrue(light.active()),
			() -> assertFalse(light.isIdentity()),
			// #FF8000 at intensity 2: full red, half green, no blue.
			() -> assertEquals(2.0F, light.tintedSunRed(), 0.001F),
			() -> assertEquals(2.0F * 0x80 / 255.0F, light.tintedSunGreen(), 0.001F),
			() -> assertEquals(0.0F, light.tintedSunBlue(), 0.001F),
			// #204080 at the default intensity 1.
			() -> assertEquals(0x20 / 255.0F, light.tintedAmbientRed(), 0.001F),
			() -> assertEquals(0x40 / 255.0F, light.tintedAmbientGreen(), 0.001F),
			() -> assertEquals(0x80 / 255.0F, light.tintedAmbientBlue(), 0.001F)
		);
	}

	@Test
	@DisplayName("a colour the environment leaves out is full strength, not black")
	void absentColour_shouldBeFullStrength() {
		// Only a sun: the ambient has no colour, so it must not multiply the
		// frame's ambient toward zero.
		HdAreaLight light = HdAreaLight.of(environmentWithSunOnly());

		assertAll(
			() -> assertTrue(light.active()),
			() -> assertEquals(1.0F, light.tintedAmbientRed(), 0.0F),
			() -> assertEquals(1.0F, light.tintedAmbientGreen(), 0.0F),
			() -> assertEquals(1.0F, light.tintedAmbientBlue(), 0.0F),
			() -> assertEquals(0x80 / 255.0F, light.tintedSunRed(), 0.001F)
		);
	}

	@Test
	@DisplayName("no environment, or one with no tint, is exactly the identity")
	void noTint_shouldBeIdentity() {
		HdAreaLight off = HdAreaLight.of(null);
		HdAreaLight untinted = HdAreaLight.of(environmentWithNoColours());

		assertAll(
			() -> assertSame(HdAreaLight.OFF, off),
			() -> assertSame(HdAreaLight.OFF, untinted),
			// The shader's uAreaSun.w is 0 for OFF, so the stock sun stands; the
			// getters still answer 1 so a caller can multiply unconditionally.
			() -> assertEquals(1.0F, HdAreaLight.OFF.tintedSunRed(), 0.0F),
			() -> assertEquals(1.0F, HdAreaLight.OFF.tintedAmbientGreen(), 0.0F)
		);
	}

	@Test
	@DisplayName("the published area is what a frame without a player reads")
	void publishedArea_shouldBeWhatCaptureReturns() {
		HdAreaLight light = HdAreaLight.of(environment());

		assertSame(HdAreaLight.OFF, HdAreaLight.capture(),
			"a harness with no player stands in no area");

		HdAreaLight.publishForTest(light);
		assertSame(light, HdAreaLight.capture(),
			"the published light must survive a read with no player");

		HdAreaLight.resetForTest();
		assertSame(HdAreaLight.OFF, HdAreaLight.capture(),
			"resetting must return to the identity, not the last area");
	}

	@Test
	@DisplayName("equality is the whole tint, so the pipeline caches on it safely")
	void equality_shouldCoverEveryChannel() {
		HdAreaLight one = HdAreaLight.of(environment());
		HdAreaLight same = HdAreaLight.of(environment());
		HdAreaLight other = HdAreaLight.of(environmentWithSunOnly());

		assertAll(
			() -> assertEquals(one, same),
			() -> assertEquals(one.hashCode(), same.hashCode()),
			() -> assertFalse(one.equals(other)),
			() -> assertFalse(one.equals(HdAreaLight.OFF))
		);
	}

	// -- Content ---------------------------------------------------------

	private static HdEnvironmentDefinition environment() {
		return environment("{\"name\":\"desert\",\"area\":{\"bounds\":[[3200,3200,3200,3200]]},"
			+ "\"sun\":{\"colour\":\"#FF8000\",\"intensity\":2},"
			+ "\"ambient\":{\"colour\":\"#204080\"}}", "desert");
	}

	private static HdEnvironmentDefinition environmentWithSunOnly() {
		return environment("{\"name\":\"dusk\",\"area\":{\"bounds\":[[3200,3200,3200,3200]]},"
			+ "\"sun\":{\"colour\":\"#808080\"}}", "dusk");
	}

	private static HdEnvironmentDefinition environmentWithNoColours() {
		return environment("{\"name\":\"plain\",\"area\":{\"bounds\":[[3200,3200,3200,3200]]},"
			+ "\"fog\":{\"depth\":5}}", "plain");
	}

	/** The definition the selector returns for the tile the entry covers. */
	private static HdEnvironmentDefinition environment(String entry, String name) {
		Map<String, String> files = new HashMap<String, String>();
		files.put("environments.json", "{\"environments\":[" + entry + "]}");
		HdContent content = HdContent.parse(files);
		assertTrue(content.problems().isEmpty(), "the entry must parse cleanly: " + content.problems());
		HdEnvironmentDefinition environment = content.environmentFor(3200, 3200, 0);
		assertEquals(name, environment == null ? null : environment.name(), "the area entry must be selected");
		return environment;
	}
}
