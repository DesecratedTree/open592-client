package com.jagex.game.runetek5.client.hd.sky;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The source the driver is handed, and the arithmetic it is claimed to
 * perform.
 *
 * <p>The pixel proof needs a prediction to compare against; this pins that the
 * prediction is the shader's own arithmetic and that every uniform the pipeline
 * uploads exists in the source (a renamed uniform would silently fall back to
 * GL's zero, which would be a black sky and no error).
 */
@DisplayName("592HD sky source")
class HdSkySourceTest {

	@Test
	@DisplayName("both stages declare the legacy GLSL the other passes use")
	void sources_shouldDeclareGlsl120() {
		assertAll(
			() -> assertTrue(HdSkySource.VERTEX.startsWith("#version " + HdSkySource.GLSL_VERSION)),
			() -> assertTrue(HdSkySource.FRAGMENT.startsWith("#version " + HdSkySource.GLSL_VERSION)),
			() -> assertEquals(120, HdSkySource.GLSL_VERSION),
			() -> assertFalse(HdSkySource.FRAGMENT.contains("#version 330"), "no core profile"),
			() -> assertFalse(HdSkySource.VERTEX.contains("layout("), "no attribute layout")
		);
	}

	@Test
	@DisplayName("every uniform the pipeline uploads is declared, and nothing else is")
	void uniforms_shouldMatchTheSource() {
		String both = HdSkySource.VERTEX + HdSkySource.FRAGMENT;
		for (String uniform : HdSkySource.uniforms()) {
			assertTrue(HdSkySource.FRAGMENT.contains("uniform ") && both.contains(uniform),
				uniform + " is uploaded but never declared");
		}
		// The other direction: a declared uniform nobody uploads would keep GL's
		// zero, so the pass would draw with a missing input.
		int declared = 0;
		for (String line : HdSkySource.FRAGMENT.split("\n")) {
			if (line.startsWith("uniform ")) {
				declared++;
			}
		}
		assertEquals(HdSkySource.uniforms().length, declared, "a declared uniform has no uploader");
	}

	@Test
	@DisplayName("the dome samples no textures and needs no depth")
	void fragment_shouldOnlyUseItsUniforms() {
		assertAll(
			() -> assertFalse(HdSkySource.FRAGMENT.contains("texture2D"), "the sky is analytic"),
			() -> assertFalse(HdSkySource.FRAGMENT.contains("gl_FragDepth"), "the dome writes no depth"),
			() -> assertFalse(HdSkySource.VERTEX.contains("gl_Normal"), "there is no geometry"),
			() -> assertTrue(HdSkySource.FRAGMENT.contains(HdSkySource.UNIFORM_SUN_DIR),
				"the disc has to follow the scene's sun"),
			() -> assertTrue(HdSkySource.FRAGMENT.contains("smoothstep(uSunShape.y, uSunShape.x"),
				"smoothstep needs outer < inner or it is undefined")
		);
	}

	@Test
	@DisplayName("the sky's sun is the lighting's sun, not a second one")
	void shade_shouldPutTheDiscOnTheLightsSun() {
		HdSkyColours colours = HdSkyColours.resolve(true, true, false, 0.6F, 0.5F, 0xFFFFFF, 0x000000);
		float[] sunDirection = { 0.0F, 0.6F, 0.8F };
		float length = (float) Math.sqrt(0.6F * 0.6F + 0.8F * 0.8F);
		float[] normalised = { 0.0F, 0.6F / length, 0.8F / length };
		float[] atSun = HdSkySource.shade(colours, normalised, normalised, null);
		// Square to the sun, so neither the disc nor its glow reaches this pixel.
		float[] away = HdSkySource.shade(colours, normalised, new float[] { 1.0F, 0.0F, 0.0F }, null);
		assertAll(
			() -> assertTrue(atSun[0] > away[0], "the disc is brighter than the sky beside it"),
			() -> assertEquals(1.0F, atSun[2], 1.0e-6F, "black fog plus a white disc is white"),
			() -> assertEquals(0.0F, away[0], 1.0e-6F, "no fog, no sun there: black"),
			() -> assertTrue(sunDirection[1] > 0.0F)
		);
	}

	@Test
	@DisplayName("the Java prediction saturates exactly where the shader clamps")
	void shade_shouldClamp() {
		// A white fog, a white sun and the sun straight ahead: the raw sum is 2.
		HdSkyColours colours = HdSkyColours.resolve(true, true, false, 1.0F, 0.5F, 0xFFFFFF, 0xFFFFFF);
		float[] forward = { 0.0F, 0.0F, -1.0F };
		float[] pixel = HdSkySource.shade(colours, forward, forward, null);
		assertAll(
			() -> assertEquals(1.0F, pixel[0], 1.0e-6F),
			() -> assertEquals(1.0F, pixel[1], 1.0e-6F),
			() -> assertEquals(1.0F, pixel[2], 1.0e-6F)
		);
	}

	@Test
	@DisplayName("the prediction's horizon is the horizon colour when the sun is behind")
	void shade_shouldGiveTheHorizonAtTheHorizon() {
		HdSkyColours colours = HdSkyColours.resolve(true, true, false, 1.0F, 0.5F, 0xFFFFFF, 0x204060);
		// The sun behind the viewer (-z), so the glow contributes nothing.
		float[] sunBehind = { 0.0F, 0.0F, -1.0F };
		float[] sideways = { 1.0F, 0.0F, 0.0F };
		float[] pixel = HdSkySource.shade(colours, sunBehind, sideways, null);
		for (int channel = 0; channel < 3; channel++) {
			assertEquals(colours.horizon()[channel], pixel[channel], 1.0e-6F, "channel " + channel);
		}
	}

	@Test
	@DisplayName("below the horizon the dome fades to the fill colour")
	void shade_shouldFadeBelowTheHorizon() {
		HdSkyColours colours = HdSkyColours.resolve(true, true, false, 1.0F, 0.5F, 0xFFFFFF, 0x204060);
		// This world's y axis points down, so "straight down" is +y.
		float[] down = { 0.0F, 1.0F, 0.0F };
		float[] sunBehind = { 0.0F, 0.0F, -1.0F };
		float[] pixel = HdSkySource.shade(colours, sunBehind, down, null);
		for (int channel = 0; channel < 3; channel++) {
			assertEquals(colours.ambient()[channel], pixel[channel], 1.0e-6F, "channel " + channel);
		}
	}
}
