package com.jagex.game.runetek5.client.hd.post;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Checks a driver would only catch on a player's machine: the version, stage
 * agreement, every uniform the pass sets being declared, and the stage order -
 * dither last, FXAA first - which is what makes the rows compose instead of
 * fighting.
 */
@DisplayName("HD post-processing GLSL")
class HdPostSourceTest {

	private static final Pattern VARYING = Pattern.compile("varying\\s+\\w+\\s+(\\w+)\\s*;");

	@Test
	@DisplayName("both stages are GLSL 120")
	void sources_shouldBeGlsl120() {
		assertAll(
			() -> assertTrue(HdPostSource.VERTEX.startsWith("#version 120\n")),
			() -> assertTrue(HdPostSource.FRAGMENT.startsWith("#version 120\n")),
			() -> assertFalse(HdPostSource.FRAGMENT.contains("layout(")),
			() -> assertFalse(HdPostSource.FRAGMENT.contains("texture("), "texture() is GLSL 1.30")
		);
	}

	@Test
	@DisplayName("the stages agree on every varying")
	void varyings_shouldMatch() {
		assertEquals(varyings(HdPostSource.VERTEX), varyings(HdPostSource.FRAGMENT));
	}

	@Test
	@DisplayName("every uniform the pass sets is declared")
	void uniforms_shouldBeDeclared() {
		for (String uniform : HdPostSource.uniforms()) {
			assertTrue(HdPostSource.FRAGMENT.contains("uniform ") && HdPostSource.FRAGMENT.contains(" " + uniform + ";"),
				uniform + " is missing from the fragment source");
		}
		// The sampler is set by the pass itself, not by HdProgram's helper.
		assertTrue(HdPostSource.FRAGMENT.contains("uniform sampler2D " + HdPostSource.UNIFORM_SCREEN + ";"));
	}

	@Test
	@DisplayName("the pass reads the copied frame at the vertex's own texcoord")
	void vertex_shouldFeedTheScreenCoord() {
		assertAll(
			() -> assertTrue(HdPostSource.VERTEX.contains("gl_Position = ftransform();")),
			() -> assertTrue(HdPostSource.VERTEX.contains("vScreenCoord = gl_MultiTexCoord0.xy;")),
			() -> assertTrue(HdPostSource.FRAGMENT.contains("texture2D(uScreen, uv)")),
			// A program does not apply the fixed-function texture matrix.
			() -> assertFalse(HdPostSource.VERTEX.contains("gl_TextureMatrix"))
		);
	}

	@Test
	@DisplayName("the tonemap is a shoulder: below the knee it is the identity")
	void tonemap_shouldBeAHighlightShoulder() {
		assertAll(
			() -> assertTrue(HdPostSource.FRAGMENT.contains("vec3 knee = max(colour - vec3(uKnee), 0.0);")),
			() -> assertTrue(HdPostSource.FRAGMENT.contains("+ uShoulder * knee / (knee + vec3(uShoulder))")),
			// Nothing multiplies the whole frame by a curve term.
			() -> assertFalse(HdPostSource.FRAGMENT.contains("colour / (colour + vec3(1.0))"))
		);
	}

	@Test
	@DisplayName("grading is gain, lift and saturation, in that order")
	void grading_shouldBeGainLiftSaturation() {
		int gain = HdPostSource.FRAGMENT.indexOf("colour * uGain.rgb + uLift.rgb");
		int saturation = HdPostSource.FRAGMENT.indexOf("mix(vec3(luma(colour)), colour, uSaturation)");
		assertTrue(gain >= 0 && saturation > gain,
			"saturation must follow gain and lift, or it would scale the lift too");
	}

	@Test
	@DisplayName("the stage order is FXAA, tonemap, grading, dither")
	void stages_shouldBeOrdered() {
		String source = HdPostSource.FRAGMENT;
		int fxaa = source.indexOf("if (uFxaa > 0.5)");
		int tonemap = source.indexOf("if (uTonemap > 0.5)");
		int grading = source.indexOf("if (uGrade > 0.5)");
		int dither = source.indexOf("if (uDither > 0.0)");
		assertAll(
			() -> assertTrue(fxaa >= 0 && tonemap > fxaa, "FXAA sees the rendered edge, not a graded one"),
			() -> assertTrue(grading > tonemap, "grading applies to the tonemapped image"),
			() -> assertTrue(dither > grading, "dither must be the last thing that touches a level")
		);
	}

	@Test
	@DisplayName("dither is ordered and one 8-bit step, so it cannot look like grain")
	void dither_shouldBeOrderedAndTiny() {
		assertAll(
			() -> assertTrue(HdPostSource.FRAGMENT.contains("bayer4(gl_FragCoord.xy)")),
			() -> assertTrue(HdPostSource.FRAGMENT.contains("* uDither")),
			() -> assertTrue(HdPostParams.DITHER_AMOUNT <= 1.0F / 255.0F + 1.0E-6F)
		);
	}

	@Test
	@DisplayName("FXAA only blends where the luma range is an edge")
	void fxaa_shouldBeEdgeGated() {
		assertAll(
			() -> assertTrue(HdPostSource.FRAGMENT.contains("if (range > 0.08)")),
			() -> assertTrue(HdPostSource.FRAGMENT.contains("mix(colour, average, clamp(range * 2.0, 0.0, 0.75))")),
			() -> assertFalse(HdPostSource.FRAGMENT.contains("if (uFxaa > 0.5) {\n\t\tcolour = average;"))
		);
	}

	private static Set<String> varyings(String source) {
		Set<String> names = new HashSet<String>();
		Matcher matcher = VARYING.matcher(source);
		while (matcher.find()) {
			names.add(matcher.group(1));
		}
		return names;
	}
}
