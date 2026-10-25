package com.jagex.game.runetek5.client.hd.water;

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
 * The mistakes a driver would only find on the player's machine: a version the
 * 592 context rejects, varyings that disagree across stages, a uniform the
 * pipeline sets that no stage declares, UVs that ignore the stock texture
 * matrix (which would freeze the scroll), or an animation term that does not
 * actually read the clock.
 */
@DisplayName("HD water GLSL")
class HdWaterSourceTest {

	private static final Pattern VARYING = Pattern.compile("varying\\s+\\w+\\s+(\\w+)\\s*;");

	@Test
	@DisplayName("both stages are GLSL 120, like the lighting program")
	void sources_shouldBeGlsl120() {
		assertAll(
			() -> assertTrue(HdWaterSource.VERTEX.startsWith("#version 120\n")),
			() -> assertTrue(HdWaterSource.FRAGMENT.startsWith("#version 120\n")),
			() -> assertFalse(HdWaterSource.VERTEX.contains("layout(")),
			() -> assertFalse(HdWaterSource.FRAGMENT.contains("layout(")),
			() -> assertFalse(HdWaterSource.VERTEX.contains("in vec"))
		);
	}

	@Test
	@DisplayName("the stages agree on every varying")
	void varyings_shouldMatch() {
		assertEquals(varyings(HdWaterSource.VERTEX), varyings(HdWaterSource.FRAGMENT),
			"vertex " + varyings(HdWaterSource.VERTEX) + " vs fragment " + varyings(HdWaterSource.FRAGMENT));
	}

	@Test
	@DisplayName("every uniform the pipeline sets is declared by the fragment stage")
	void uniforms_shouldBeDeclared() {
		for (String uniform : HdWaterSource.uniforms()) {
			assertTrue(HdWaterSource.FRAGMENT.contains("uniform ") && HdWaterSource.FRAGMENT.contains(" " + uniform + ";"),
				uniform + " is missing from the fragment source");
		}
	}

	@Test
	@DisplayName("the surface is animated by the clock, not by the texture scroll")
	void fragment_shouldAnimateTheSurface() {
		assertAll(
			() -> assertTrue(HdWaterSource.FRAGMENT.contains("sin(vWorldPosition.x * 0.31 + phase * 1.9)")),
			() -> assertTrue(HdWaterSource.FRAGMENT.contains("float phase = uTime;")),
			() -> assertTrue(HdWaterSource.FRAGMENT.contains("* uFlow")),
			() -> assertTrue(HdWaterSource.FRAGMENT.contains("surface = normalize(surface);")),
			// Scene space, then eye space: waves stay attached to the world, and the
			// fragment stage never names gl_NormalMatrix (GLSL 120 vertex-only).
			() -> assertTrue(HdWaterSource.VERTEX.contains("vNormalX = gl_NormalMatrix * vec3(1.0, 0.0, 0.0);")),
			() -> assertTrue(HdWaterSource.FRAGMENT.contains("vNormalX * surface.x")),
			() -> assertFalse(HdWaterSource.FRAGMENT.contains("gl_NormalMatrix")),
			() -> assertTrue(HdWaterSource.VERTEX.contains("vWorldNormal = gl_Normal;"))
		);
	}

	@Test
	@DisplayName("fresnel is per pixel and the highlight is the sun's")
	void fragment_shouldFresnelAndSpecular() {
		assertAll(
			() -> assertTrue(HdWaterSource.FRAGMENT.contains("pow(1.0 - clamp(dot(normal, view), 0.0, 1.0), 3.0)")),
			() -> assertTrue(HdWaterSource.FRAGMENT.contains("mix(body, sheen, fresnel * 0.7)")),
			() -> assertTrue(HdWaterSource.FRAGMENT.contains("normalize(gl_LightSource[0].position.xyz)")),
			() -> assertTrue(HdWaterSource.FRAGMENT.contains("pow(max(dot(normal, halfway), 0.0), uShininess)")),
			() -> assertFalse(HdWaterSource.FRAGMENT.contains("vec3 half ="),
				"half is reserved in GLSL 1.20"),
			() -> assertTrue(HdWaterSource.FRAGMENT.contains("gl_LightSource[0].diffuse.rgb * specular"))
		);
	}

	@Test
	@DisplayName("the stock texture scroll survives: UVs go through the texture matrix")
	void vertex_shouldHonourTheStockTextureMatrix() {
		assertAll(
			() -> assertTrue(HdWaterSource.VERTEX.contains("gl_TextureMatrix[0] * gl_MultiTexCoord0")),
			() -> assertFalse(HdWaterSource.VERTEX.contains("vTexCoord = gl_MultiTexCoord0.xy;")),
			() -> assertTrue(HdWaterSource.FRAGMENT.contains("texture2D(uTexture, vTexCoord)"))
		);
	}

	@Test
	@DisplayName("fog is applied like the stock path, and alpha comes from the vertex colour")
	void fragment_shouldFogAndKeepAlpha() {
		assertAll(
			() -> assertTrue(HdWaterSource.FRAGMENT.contains("gl_Fog.scale")),
			() -> assertTrue(HdWaterSource.FRAGMENT.contains("mix(gl_Fog.color.rgb, colour, fogFactor)")),
			() -> assertTrue(HdWaterSource.FRAGMENT.contains(", gl_Color.a)")),
			() -> assertTrue(HdWaterSource.FRAGMENT.contains("clamp(lit, 0.0, 1.0)"))
		);
	}

	@Test
	@DisplayName("the reflective stage is GLSL 120, agrees on the varyings, and reads the cube")
	void reflectiveFragment_shouldSampleTheEnvironment() {
		assertAll(
			() -> assertTrue(HdWaterSource.REFLECTIVE_FRAGMENT.startsWith("#version 120\n")),
			() -> assertEquals(varyings(HdWaterSource.VERTEX), varyings(HdWaterSource.REFLECTIVE_FRAGMENT),
				"both fragments consume the one vertex stage"),
			() -> assertTrue(HdWaterSource.REFLECTIVE_FRAGMENT.contains("uniform samplerCube uEnvironment;")),
			() -> assertTrue(HdWaterSource.REFLECTIVE_FRAGMENT.contains("reflect(view, normal)")),
			() -> assertTrue(HdWaterSource.REFLECTIVE_FRAGMENT.contains("textureCube(uEnvironment, reflected)")),
			() -> assertTrue(HdWaterSource.REFLECTIVE_FRAGMENT.contains("mix(body, environment, fresnel * 0.7)")),
			// Same surface, sun, foam and fog as the calm program: only the sheen's
			// source is different, so a repainted sky cannot move the water body.
			() -> assertTrue(HdWaterSource.REFLECTIVE_FRAGMENT.contains("pow(1.0 - clamp(dot(normal, view), 0.0, 1.0), 3.0)")),
			() -> assertTrue(HdWaterSource.REFLECTIVE_FRAGMENT.contains("pow(max(dot(normal, halfway), 0.0), uShininess)")),
			() -> assertTrue(HdWaterSource.REFLECTIVE_FRAGMENT.contains("mix(gl_Fog.color.rgb, colour, fogFactor)")),
			() -> assertTrue(HdWaterSource.REFLECTIVE_FRAGMENT.contains(", gl_Color.a)"))
		);
	}

	@Test
	@DisplayName("the calm fragment has no cube sampler, so its image cannot depend on one")
	void calmFragment_shouldStayCubeFree() {
		assertAll(
			() -> assertFalse(HdWaterSource.FRAGMENT.contains("samplerCube")),
			() -> assertFalse(HdWaterSource.FRAGMENT.contains("textureCube")),
			() -> assertTrue(HdWaterSource.FRAGMENT.contains("vec3 sheen = gl_Fog.color.rgb;"))
		);
	}

	@Test
	@DisplayName("the reflective uniforms include the cube, and the calm list does not")
	void reflectiveUniforms_shouldAddTheEnvironment() {
		assertAll(
			() -> assertEquals(4, HdWaterSource.uniforms().length, "the calm program is the one that shipped"),
			() -> assertEquals(5, HdWaterSource.reflectiveUniforms().length),
			() -> assertTrue(HdWaterSource.reflectiveUniforms()[1].equals(HdWaterSource.UNIFORM_ENVIRONMENT),
				"the cube sits beside the material sampler"),
			() -> assertTrue(HdWaterSource.ENVIRONMENT_UNIT == 1, "unit 1, where the stock cube effects bind")
		);
		for (String uniform : HdWaterSource.reflectiveUniforms()) {
			assertTrue(HdWaterSource.REFLECTIVE_FRAGMENT.contains(" " + uniform + ";"),
				uniform + " is missing from the reflective fragment source");
		}
	}

	@Test
	@DisplayName("the custom surface is GLSL 120, agrees on the varyings, and declares its palette")
	void customFragment_shouldBeAThirdProgram() {
		assertAll(
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.startsWith("#version 120\n")),
			() -> assertEquals(varyings(HdWaterSource.VERTEX), varyings(HdWaterSource.CUSTOM_FRAGMENT),
				"all three fragments consume the one vertex stage"),
			() -> assertFalse(HdWaterSource.CUSTOM_FRAGMENT.equals(HdWaterSource.FRAGMENT),
				"a third program, not an alias for the standard one"),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("uniform vec3 uDeepColour;")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("uniform vec3 uShallowColour;"))
		);
		for (String uniform : HdWaterSource.customUniforms()) {
			assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains(" " + uniform + ";"),
				uniform + " is missing from the custom fragment source");
		}
	}

	@Test
	@DisplayName("the custom look is a third wave train, absorption and crest sparkles")
	void customFragment_shouldCarryTheLook() {
		assertAll(
			// The third train runs on the diagonal, which neither shipped program does.
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains(
				"sin((vWorldPosition.x + vWorldPosition.z) * 0.12 + phase * 0.8)")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("* uFlow * 0.45")),
			// Absorption: straight down is the deep colour, grazing is the shallow one.
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains(
				"mix(uDeepColour, texel * uShallowColour, smoothstep(0.0, 0.65, facing))")),
			// A sharper Fresnel than the standard surface's cube of one minus facing.
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("pow(1.0 - facing, 4.0)")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("mix(body, sheen, fresnel * 0.85)")),
			// Sparkles: a hashed cell, stepped by the clock, gated by the sun's glint.
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("float crestHash(vec2 cell, float tick)")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("fract(sin(dot(cell + tick")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("float tick = floor(phase * 4.0);")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("step(0.996, crestHash(cell, tick)) * tight")),
			// Two lobes: a tight glint and a wide sheen, both the sun's colour.
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("uShininess * 0.08")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("sun * (tight * 1.15 + wide * 0.22")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("vec3 sun = gl_LightSource[0].diffuse.rgb;")),
			() -> assertFalse(HdWaterSource.CUSTOM_FRAGMENT.contains("gl_NormalMatrix")),
			() -> assertFalse(HdWaterSource.CUSTOM_FRAGMENT.contains("vec3 half ="))
		);
	}

	@Test
	@DisplayName("the custom surface keeps the stock frame terms and never reads a cube")
	void customFragment_shouldKeepTheFrameTerms() {
		assertAll(
			() -> assertTrue(HdWaterSource.VERTEX.contains("gl_TextureMatrix[0] * gl_MultiTexCoord0"),
				"the shared vertex stage keeps the stock scroll"),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("texture2D(uTexture, vTexCoord)")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("clamp(vFoam, 0.0, 1.0) * 0.24")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("gl_Fog.scale")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("mix(gl_Fog.color.rgb, colour, fogFactor)")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains(", gl_Color.a)")),
			() -> assertFalse(HdWaterSource.CUSTOM_FRAGMENT.contains("samplerCube")),
			() -> assertFalse(HdWaterSource.CUSTOM_FRAGMENT.contains("textureCube"))
		);
	}

	@Test
	@DisplayName("the breaking stage is GLSL 120, agrees on the varyings, and reads the material's numbers")
	void breakingFragment_shouldCarryTheMaterialParameters() {
		assertAll(
			() -> assertTrue(HdWaterSource.BREAKING_FRAGMENT.startsWith("#version 120\n")),
			() -> assertEquals(varyings(HdWaterSource.VERTEX), varyings(HdWaterSource.BREAKING_FRAGMENT),
				"all four fragments consume the one vertex stage"),
			() -> assertFalse(HdWaterSource.BREAKING_FRAGMENT.equals(HdWaterSource.REFLECTIVE_FRAGMENT),
				"the breaking type is its own stage, not an alias for the reflective one"),
			() -> assertTrue(HdWaterSource.BREAKING_FRAGMENT.contains("uniform samplerCube uEnvironment;")),
			() -> assertTrue(HdWaterSource.BREAKING_FRAGMENT.contains("textureCube(uEnvironment, reflect(view, normal))")),
			// The material's own numbers: scale on the coordinates, rate on the clock,
			// and the depth, offset, crest colour and exponent on the band.
			() -> assertTrue(HdWaterSource.BREAKING_FRAGMENT.contains("float phase = uTime * uBreakRate;")),
			() -> assertTrue(HdWaterSource.BREAKING_FRAGMENT.contains("texture2D(uTexture, vTexCoord * uBreakScale)")),
			() -> assertTrue(HdWaterSource.BREAKING_FRAGMENT.contains("vFoam / max(uBreakDepth, 1.0) - uBreakOffset * crest")),
			() -> assertTrue(HdWaterSource.BREAKING_FRAGMENT.contains("pow(1.0 - shore, uWaveExponent) - 0.5")),
			() -> assertTrue(HdWaterSource.BREAKING_FRAGMENT.contains("1.0 - 4.0 * band * band")),
			() -> assertTrue(HdWaterSource.BREAKING_FRAGMENT.contains("vec3 crestColour = vec3(uWaveIntensity.y + uWaveIntensity.x * crest);")),
			// The same surface, sun and fog as the other water stages.
			() -> assertTrue(HdWaterSource.BREAKING_FRAGMENT.contains("sin(vWorldPosition.x * 0.31 + phase * 1.9)")),
			() -> assertTrue(HdWaterSource.BREAKING_FRAGMENT.contains("pow(max(dot(normal, halfway), 0.0), uShininess)")),
			() -> assertTrue(HdWaterSource.BREAKING_FRAGMENT.contains("mix(gl_Fog.color.rgb, colour, fogFactor)")),
			() -> assertTrue(HdWaterSource.BREAKING_FRAGMENT.contains(", gl_Color.a)")),
			// The same two GLSL 120 hazards the other stages avoid.
			() -> assertFalse(HdWaterSource.BREAKING_FRAGMENT.contains("gl_NormalMatrix")),
			() -> assertFalse(HdWaterSource.BREAKING_FRAGMENT.contains("vec3 half ="))
		);
		for (String uniform : HdWaterSource.breakingUniforms()) {
			assertTrue(HdWaterSource.BREAKING_FRAGMENT.contains(" " + uniform + ";"),
				uniform + " is missing from the breaking fragment source");
		}
	}

	@Test
	@DisplayName("the breaking uniforms are the shared group plus the cube and the six material numbers")
	void breakingUniforms_shouldBeTheSharedGroupPlusTheMaterialNumbers() {
		assertAll(
			() -> assertEquals(11, HdWaterSource.breakingUniforms().length),
			() -> assertEquals(HdWaterSource.UNIFORM_ENVIRONMENT, HdWaterSource.breakingUniforms()[1]),
			() -> assertEquals(HdWaterSource.UNIFORM_BREAK_DEPTH, HdWaterSource.breakingUniforms()[5]),
			() -> assertEquals(HdWaterSource.UNIFORM_BREAK_RATE, HdWaterSource.breakingUniforms()[10]),
			() -> assertTrue(java.util.Arrays.asList(HdWaterSource.breakingUniforms()).containsAll(
				java.util.Arrays.asList(HdWaterSource.uniforms())), "the breaking surface keeps the shared four")
		);
	}

	@Test
	@DisplayName("the three programs declare the uniform groups they are handed")
	void uniformGroups_shouldMatchTheirPrograms() {
		assertAll(
			() -> assertEquals(4, HdWaterSource.uniforms().length, "the standard surface: sampler, time, flow, shininess"),
			() -> assertEquals(5, HdWaterSource.reflectiveUniforms().length, "plus the cube"),
			() -> assertEquals(6, HdWaterSource.customUniforms().length, "plus the two palette colours"),
			() -> assertEquals(11, HdWaterSource.breakingUniforms().length, "plus the cube and six numbers"),
			() -> assertTrue(HdWaterSource.customUniforms()[4].equals(HdWaterSource.UNIFORM_DEEP_COLOUR)),
			() -> assertTrue(HdWaterSource.customUniforms()[5].equals(HdWaterSource.UNIFORM_SHALLOW_COLOUR)),
			() -> assertTrue(java.util.Arrays.asList(HdWaterSource.customUniforms()).containsAll(
				java.util.Arrays.asList(HdWaterSource.uniforms())), "the custom surface keeps the shared four")
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
