package com.jagex.game.runetek5.client.hd.water;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
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
	@DisplayName("the surface is 634HD's water type, number for number")
	void surface_shouldBeTheUpstreamWaterType() {
		String body = HdWaterSource.FRAGMENT;
		assertAll(
			// The type's fields, from materials.schema.json's WATER defaults.
			() -> assertTrue(body.contains("#define HD_WATER_DEPTH_RANGE 1536.0")),
			() -> assertTrue(body.contains("#define HD_WATER_FOAM_DEPTH 192.0")),
			() -> assertTrue(body.contains("#define HD_WATER_DEPTH_OPACITY 0.9")),
			() -> assertTrue(body.contains("#define HD_WATER_REFLECTION_BEND 0.3")),
			() -> assertTrue(body.contains("#define HD_WATER_MIRROR_SHARE 0.5")),
			// The type's own fields are uniforms: a material's water block selects
			// them, and these are the WATER type's values.
			() -> assertTrue(body.contains("uniform vec3 uWaterSpec;")),
			() -> assertTrue(body.contains("uniform vec3 uWaterShape;")),
			() -> assertTrue(body.contains("uniform vec3 uFoamColour;")),
			() -> assertEquals(0.5F, HdWaterType.DEFAULT.opacity()),
			() -> assertEquals(0.5F, HdWaterType.DEFAULT.specular()),
			() -> assertEquals(0.85F, HdWaterType.DEFAULT.fresnel()),
			() -> assertEquals(1.0F, HdWaterType.DEFAULT.duration()),
			() -> assertTrue(body.contains("* uWaterShape.y"),
				"the type's period scales the three ripple layers"),
			// Upstream's Fresnel: a mix from 0.4 to 1.0, not a power of the cosine.
			() -> assertTrue(body.contains(
				"clamp(mix(0.4, 1.0, (1.0 - clamp(facing, 0.0, 1.0)) * 1.2), 0.0, 1.0)")),
			() -> assertFalse(body.contains("pow(1.0 - clamp(dot(normal, view), 0.0, 1.0), 3.0)")),
			// The reflection's gradient: the tint at 0.05, 0.45 and 0.8.
			() -> assertTrue(body.contains("mix(t * 0.05, t * 0.45, finalFresnel * 2.0)")),
			() -> assertTrue(body.contains("mix(t * 0.45, t * 0.8, (finalFresnel - 0.5) * 2.0)")),
			// Plain water is darkened by a quarter, the type's own rule.
			() -> assertTrue(body.contains("base *= 0.75;")),
			// The foam is the type's colour, #b0a492.
			() -> assertEquals(0xB0A492, HdWaterType.DEFAULT.foam()),
			// The sky's light: half the fog band on the up-facing share.
			() -> assertTrue(body.contains("gl_Fog.color.rgb * max(dot(normal, up), 0.0) * 0.5")),
			// The glint is the view's reflection of the sun, at the type's strength.
			() -> assertTrue(body.contains("dot(view, reflect(-light, normal))")),
			() -> assertTrue(body.contains("* hdWaterSpecular634()")),
			// The type's foam only exists when it says so.
			() -> assertTrue(body.contains("* uWaterShape.x")),
			// The depth's opacity, and the depth colour laid under the surface.
			() -> assertTrue(body.contains("HD_WATER_DEPTH_OPACITY")),
			() -> assertTrue(body.contains("mix(uDeepColour, base, alpha)")),
			// Upstream's gloss, 500, not the broad 96 this port shipped.
			() -> assertEquals(500.0F, HdWaterPipeline.DEFAULT_SHININESS)
		);
	}

	@Test
	@DisplayName("the stock texture scroll survives: UVs go through the texture matrix")
	void vertex_shouldHonourTheStockTextureMatrix() {
		assertAll(
			() -> assertTrue(HdWaterSource.VERTEX.contains("gl_TextureMatrix[0] * gl_MultiTexCoord0")),
			() -> assertFalse(HdWaterSource.VERTEX.contains("vTexCoord = gl_MultiTexCoord0.xy;")),
			// 634HD's surface is the type's colour, not the water material's own
			// texture (often white), so the calm surface does not sample it; the
			// breaking type still scales the stock coordinates by its ripple scale.
			() -> assertFalse(HdWaterSource.FRAGMENT.contains("texture2D(uTexture")),
			() -> assertTrue(HdWaterSource.BREAKING_FRAGMENT.contains(
				"texture2D(uTexture, vTexCoord * uBreakScale)"))
		);
	}

	@Test
	@DisplayName("fog is applied like the stock path, and the surface is written opaque")
	void fragment_shouldFogAndWriteOpaque() {
		assertAll(
			() -> assertTrue(HdWaterSource.FRAGMENT.contains("gl_Fog.scale")),
			() -> assertTrue(HdWaterSource.FRAGMENT.contains("mix(gl_Fog.color.rgb, colour, fogFactor)")),
			// 634HD's water is not an overlay: letting the vertex colour's alpha
			// through is what made the stock HD surface see-through.
			() -> assertTrue(HdWaterSource.FRAGMENT.contains(", 1.0);")),
			() -> assertFalse(HdWaterSource.FRAGMENT.contains("gl_Color.a)")),
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
			() -> assertTrue(HdWaterSource.REFLECTIVE_FRAGMENT.contains("vec3 bent = reflect(view, normal);")),
			() -> assertTrue(HdWaterSource.REFLECTIVE_FRAGMENT.contains("textureCube(uEnvironment, bent)")),
			// The reflective type reflects the cube outright; the calm surface
			// mixes it with the tint gradient at the mirror share.
			() -> assertTrue(HdWaterSource.REFLECTIVE_FRAGMENT.contains("vec3 reflection = environment * finalFresnel;")),
			// Same body, foam, sun and fog as the calm program: only the sheen's
			// source is different, so a repainted sky cannot move the water body.
			() -> assertTrue(HdWaterSource.REFLECTIVE_FRAGMENT.contains(
				"hdWaterBody634(vEyePosition, normal, view, normalize(vNormalY), vFoam, foamMask, reflection)")),
			() -> assertTrue(HdWaterSource.REFLECTIVE_FRAGMENT.contains("mix(gl_Fog.color.rgb, colour, fogFactor)")),
			() -> assertTrue(HdWaterSource.REFLECTIVE_FRAGMENT.contains(", 1.0);")),
			() -> assertFalse(HdWaterSource.REFLECTIVE_FRAGMENT.contains("gl_Color.a)"))
		);
	}

	@Test
	@DisplayName("the calm fragment reflects the frame's cube, and falls back to the fog band")
	void calmFragment_shouldReflectTheEnvironment() {
		assertAll(
			() -> assertTrue(HdWaterSource.FRAGMENT.contains("uniform samplerCube uEnvironment;")),
			() -> assertTrue(HdWaterSource.FRAGMENT.contains(
				"vec3 reflected = textureCube(uEnvironment, bent).rgb;")),
			// The flag, not a guess: a cube with nothing on its unit samples black.
			() -> assertTrue(HdWaterSource.FRAGMENT.contains(
				"mix(gl_Fog.color.rgb, reflected, clamp(uReflection, 0.0, 1.0))"))
		);
	}

	@Test
	@DisplayName("the reflective uniforms include the cube, and the calm list does not")
	void reflectiveUniforms_shouldAddTheEnvironment() {
		assertAll(
			() -> assertEquals(21, HdWaterSource.uniforms().length,
				"the standard surface: sampler, cube, flag, time, flow, shininess, the three maps, the flag,\n"
					+ " the strength, the palette and the caustic strength"),
			() -> assertEquals(20, HdWaterSource.reflectiveUniforms().length),
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
			// Absorption is the shared 634HD body: the type's colour and depth
			// colour, not the stock material's texel.
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains(
				"hdWaterBody634(vEyePosition, normal, view, normalize(vNormalY), vFoam, foamMask, reflection)")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains(
				"hdWaterTint634(hdWaterAreaTint634(), finalFresnel)")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("HD_WATER_MIRROR_SHARE")),
			() -> assertFalse(HdWaterSource.CUSTOM_FRAGMENT.contains("pow(1.0 - facing, 4.0)")),
			// Sparkles: a hashed cell, stepped by the clock, gated by the sun's glint.
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("float crestHash(vec2 cell, float tick)")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("fract(sin(dot(cell + tick")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("float tick = floor(phase * 4.0);")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("step(0.996, crestHash(cell, tick)) * tight")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("sun * (sparkle * 2.5 + crest * 0.05)")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("vec3 sun = gl_LightSource[0].diffuse.rgb;")),
			() -> assertFalse(HdWaterSource.CUSTOM_FRAGMENT.contains("gl_NormalMatrix")),
			() -> assertFalse(HdWaterSource.CUSTOM_FRAGMENT.contains("vec3 half ="))
		);
	}

	@Test
	@DisplayName("the custom surface keeps the stock frame terms")
	void customFragment_shouldKeepTheFrameTerms() {
		assertAll(
			() -> assertTrue(HdWaterSource.VERTEX.contains("gl_TextureMatrix[0] * gl_MultiTexCoord0"),
				"the shared vertex stage keeps the stock scroll"),
			() -> assertFalse(HdWaterSource.CUSTOM_FRAGMENT.contains("texture2D(uTexture, vTexCoord)")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains(
				"hdWaterBody634(vEyePosition, normal, view, normalize(vNormalY), vFoam, foamMask, reflection)")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("gl_Fog.scale")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("mix(gl_Fog.color.rgb, colour, fogFactor)")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains(", 1.0);")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("samplerCube uEnvironment")),
			() -> assertTrue(HdWaterSource.CUSTOM_FRAGMENT.contains("textureCube(uEnvironment"))
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
			() -> assertTrue(HdWaterSource.BREAKING_FRAGMENT.contains("textureCube(uEnvironment, bent)")),
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
			() -> assertTrue(HdWaterSource.BREAKING_FRAGMENT.contains(
				"hdWaterBody634(vEyePosition, normal, view, normalize(vNormalY), vFoam, foamMask, reflection)")),
			() -> assertTrue(HdWaterSource.BREAKING_FRAGMENT.contains("mix(gl_Fog.color.rgb, colour, fogFactor)")),
			() -> assertTrue(HdWaterSource.BREAKING_FRAGMENT.contains(", 1.0);")),
			() -> assertFalse(HdWaterSource.BREAKING_FRAGMENT.contains("gl_Color.a)")),
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
			() -> assertEquals(26, HdWaterSource.breakingUniforms().length),
			() -> assertEquals(HdWaterSource.UNIFORM_ENVIRONMENT, HdWaterSource.breakingUniforms()[1]),
			() -> assertEquals(HdWaterSource.UNIFORM_BREAK_DEPTH, HdWaterSource.breakingUniforms()[5]),
			() -> assertEquals(HdWaterSource.UNIFORM_BREAK_RATE, HdWaterSource.breakingUniforms()[10]),
			() -> assertTrue(java.util.Arrays.asList(HdWaterSource.breakingUniforms()).containsAll(
				java.util.Arrays.asList(HdWaterSource.UNIFORM_TEXTURE, HdWaterSource.UNIFORM_ENVIRONMENT,
					HdWaterSource.UNIFORM_TIME, HdWaterSource.UNIFORM_FLOW,
					HdWaterSource.UNIFORM_SHININESS)), "the breaking surface keeps the shared group")
		);
	}

	@Test
	@DisplayName("the three programs declare the uniform groups they are handed")
	void uniformGroups_shouldMatchTheirPrograms() {
		assertAll(
			() -> assertEquals(21, HdWaterSource.uniforms().length,
				"the standard surface: sampler, cube, flag, time, flow, shininess, the three maps, the flag,\n"
					+ " the strength, the palette and the caustic strength"),
			() -> assertEquals(20, HdWaterSource.reflectiveUniforms().length, "the cube, no flag"),
			() -> assertEquals(21, HdWaterSource.customUniforms().length,
				"the cube, flag, caustics, the two palette colours and the map group"),
			() -> assertEquals(26, HdWaterSource.breakingUniforms().length,
				"plus the cube, six numbers and the map group"),
			() -> assertTrue(HdWaterSource.customUniforms()[7].equals(HdWaterSource.UNIFORM_DEEP_COLOUR)),
			() -> assertTrue(HdWaterSource.customUniforms()[8].equals(HdWaterSource.UNIFORM_SHALLOW_COLOUR)),
			() -> assertTrue(java.util.Arrays.asList(HdWaterSource.customUniforms()).containsAll(
				java.util.Arrays.asList(HdWaterSource.uniforms())), "the custom surface keeps the shared group")
		);
	}

	@Test
	@DisplayName("634HD's net is the seabed's: no water surface paints it")
	void waterSurfaces_shouldNotPaintTheCausticNet() {
		for (String fragment : new String[] { HdWaterSource.FRAGMENT, HdWaterSource.REFLECTIVE_FRAGMENT,
				HdWaterSource.CUSTOM_FRAGMENT, HdWaterSource.BREAKING_FRAGMENT }) {
			assertAll(
				// The uniform and upstream's function stay in the shared block:
				// the terrain pass lights the seabed with the same net.
				() -> assertTrue(fragment.contains("uniform float uCaustics;")),
				() -> assertTrue(fragment.contains("float hdCaustics(vec2 p, float t) {")),
				() -> assertTrue(fragment.contains("vec2 q = p * 0.0042;")),
				() -> assertTrue(fragment.contains("total += pow(1.0 - abs(a), 7.0);")),
				// hdWaterSurface has no caustics term at all: painted on the water
				// it was a net over a flat sheet, the stock water texture's look.
				() -> assertFalse(fragment.contains("hdCaustics(vWorldPosition")),
				() -> assertFalse(fragment.contains("lit += lit * hdCaustics"))
			);
		}
	}
	@Test
	@DisplayName("every water surface samples 634HD's water maps, and leaves the wave field when they are absent")
	void fragments_shouldSampleTheWaterMaps() {
		for (String fragment : new String[] { HdWaterSource.FRAGMENT, HdWaterSource.REFLECTIVE_FRAGMENT,
				HdWaterSource.CUSTOM_FRAGMENT, HdWaterSource.BREAKING_FRAGMENT }) {
			assertAll(
				() -> assertTrue(fragment.contains("uniform sampler2D uWaterNormal;")),
				() -> assertTrue(fragment.contains("uniform sampler2D uWaterFoam;")),
				() -> assertTrue(fragment.contains("uniform float uWaterMaps;")),
				// hdWaterLayers: 3, 5 and 7 tiles, 30, 24 and 20 second frames.
				() -> assertTrue(fragment.contains("vec2 hdWaterRipples(vec2 xz, float seconds)")),
				() -> assertTrue(fragment.contains("hdWaterWorldUv634(xz, 3.0, size)")),
				() -> assertTrue(fragment.contains("hdWaterWorldUv634(xz, 5.0, size)")),
				() -> assertTrue(fragment.contains("hdWaterWorldUv634(xz, 7.0, size)")),
				() -> assertTrue(fragment.contains("30.0 * duration")),
				() -> assertTrue(fragment.contains("24.0 * duration")),
				() -> assertTrue(fragment.contains("20.0 * duration")),
				() -> assertTrue(fragment.contains("texture2D(uWaterNormal, uv1)")),
				() -> assertTrue(fragment.contains("hdWaterRipples(vWorldPosition.xz, phase)")),
				// The stock per-vertex depth still decides where the foam is.
				() -> assertTrue(fragment.contains("texture2D(uWaterFoam")),
				() -> assertTrue(java.util.Arrays.asList(HdWaterSource.uniforms())
					.contains(HdWaterSource.UNIFORM_WATER_FLOW), "the flow map has its own sampler"),
				() -> assertTrue(fragment.contains("1.0 - clamp(depth / 192.0, 0.0, 1.0)")),
				// Absent maps leave the shipped draw untouched, not sampling black.
				() -> assertTrue(fragment.contains("if (uWaterMaps > 0.5) {"))
			);
		}
		assertAll(
			// Above every unit another pass names: a water layer also binds its
			// material, and 6/7 are that material's normal and ORM units.
			() -> assertTrue(HdWaterSource.WATER_NORMAL_UNIT > 9,
				"the ripple map sits above the material range: " + HdWaterSource.WATER_NORMAL_UNIT),
			() -> assertEquals(HdWaterSource.WATER_NORMAL_UNIT + 1, HdWaterSource.WATER_FOAM_UNIT),
			() -> assertEquals(HdWaterSource.WATER_FOAM_UNIT + 1, HdWaterSource.WATER_FLOW_UNIT),
			() -> assertTrue(java.util.Arrays.asList(HdWaterSource.uniforms())
				.contains(HdWaterSource.UNIFORM_WATER_MAPS)),
			() -> assertFalse(HdWaterSource.FRAGMENT.contains("textureCube(uWaterNormal"))
		);
	}

	@Test
	@DisplayName("the ripples and the foam drift with 634HD's flow map")
	void fragments_shouldDriftWithTheFlowMap() {
		for (String fragment : new String[] { HdWaterSource.FRAGMENT, HdWaterSource.REFLECTIVE_FRAGMENT,
				HdWaterSource.CUSTOM_FRAGMENT, HdWaterSource.BREAKING_FRAGMENT }) {
			assertAll(
				() -> assertTrue(fragment.contains("uniform sampler2D uWaterFlow;")),
				() -> assertTrue(fragment.contains("texture2D(uWaterFlow")),
				// hdWaterLayers: a repeat every 15 tiles, a frame 50 seconds long,
				// both scaled by the type's period (uWaterShape.y).
				() -> assertTrue(fragment.contains("hdWaterWorldUv634(xz, 15.0, size)")),
				() -> assertTrue(fragment.contains("return -xz / (512.0 * tiles * max(size, 0.25));")),
				() -> assertTrue(fragment.contains("50.0 * uWaterShape.y")),
				// Upstream's 0.025 of the velocity, on the ripple layers and on the
				// foam: one current, so the foam cannot slide against the water.
				() -> assertTrue(fragment.contains("hdWaterFlow634(xz, seconds, uWaterParams.z) * 0.025")),
				() -> assertTrue(fragment.contains("+ flow;")),
				() -> assertTrue(fragment.contains("hdWaterFoamTexel(vWorldPosition.xz, phase)")),
				() -> assertTrue(fragment.contains("fract(xz / 512.0) + flow")),
				// Absent maps: no current at all, rather than a sample of unit 0.
				() -> assertTrue(fragment.contains("if (uWaterMaps < 0.5) {")),
				() -> assertEquals(HdWaterSource.UNIFORM_WATER_FLOW, "uWaterFlow")
			);
		}
	}

	@Test
	@DisplayName("634HD's three ripple layers travel in different directions")
	void waterLayers_shouldScrollInDifferentDirections() {
		for (String fragment : new String[] { HdWaterSource.FRAGMENT, HdWaterSource.REFLECTIVE_FRAGMENT,
				HdWaterSource.CUSTOM_FRAGMENT, HdWaterSource.BREAKING_FRAGMENT }) {
			assertAll(
				// hdWaterLayers: the 3-tile layer's uv has its axes swapped and
				// scrolls against the clock, the 5 and 7 tile layers with it. Three
				// layers sharing one scroll direction is the sheet that slid as a
				// whole in this port, whichever way the current ran.
				() -> assertTrue(fragment.contains("hdWaterWorldUv634(xz, 3.0, size).yx - hdWaterFrame634")),
				() -> assertTrue(fragment.contains("hdWaterWorldUv634(xz, 5.0, size) + hdWaterFrame634")),
				() -> assertTrue(fragment.contains("hdWaterWorldUv634(xz, 7.0, size) + hdWaterFrame634")),
				// The per-layer offset that stood in for real decorrelation.
				() -> assertFalse(fragment.contains("float(i) * 0.37")),
				() -> assertTrue(fragment.contains("vec2 uv1 = hdWaterWorldUv634")),
				() -> assertTrue(fragment.contains("vec2 uv2 = hdWaterWorldUv634")),
				() -> assertTrue(fragment.contains("vec2 uv3 = hdWaterWorldUv634"))
			);
		}
	}

	@Test
	@DisplayName("the terrain-format stage takes the depth as a uniform, not from a texcoord")
	void groundVertex_shouldCarryTheDepthUniform() {
		assertAll(
			() -> assertTrue(HdWaterSource.GROUND_VERTEX.contains(
				"uniform float " + HdWaterSource.UNIFORM_GROUND_DEPTH + ";")),
			() -> assertTrue(HdWaterSource.GROUND_VERTEX.contains(
				"vFoam = " + HdWaterSource.UNIFORM_GROUND_DEPTH + " * 1536.0;")),
			() -> assertFalse(HdWaterSource.GROUND_VERTEX.contains("vFoam = gl_MultiTexCoord0.z;")),
			() -> assertTrue(HdWaterSource.GROUND_VERTEX.contains("gl_TextureMatrix[0] * gl_MultiTexCoord0"),
				"the stock scroll still drives the atlas")
		);
	}

	@Test
	@DisplayName("every surface carries the frame's point lights, counted as the lighting program counts them")
	void fragments_shouldCarryThePointLights() {
		for (String fragment : new String[] { HdWaterSource.FRAGMENT, HdWaterSource.REFLECTIVE_FRAGMENT,
				HdWaterSource.CUSTOM_FRAGMENT, HdWaterSource.BREAKING_FRAGMENT }) {
			assertAll(
				() -> assertTrue(fragment.contains("uniform int uPointLightCount;")),
				// The same rows the lighting program reads: gl_LightSource[2 .. 5].
				() -> assertTrue(fragment.contains("for (int i = 2; i < 6; i++) {")),
				() -> assertTrue(fragment.contains("if (i - 2 >= uPointLightCount) {")),
				() -> assertTrue(fragment.contains("gl_LightSource[i].constantAttenuation")),
				() -> assertTrue(fragment.contains("gl_LightSource[i].diffuse.rgb * attenuation")),
				() -> assertTrue(fragment.contains("hdWaterPoints634(eye, normal, view)")),
				() -> assertTrue(fragment.contains(
					"pow(max(dot(view, reflect(-direction, normal)), 0.0),")),
				// Eye space: the light rows are eye-space and this surface's normal
				// and view already are, so the glint needs no transform of its own.
				() -> assertTrue(fragment.contains("hdWaterBody634(vEyePosition, normal, view")),
				() -> assertTrue(fragment.contains("uShininess) * hdWaterSpecular634()")),
				() -> assertTrue(java.util.Arrays.asList(HdWaterSource.uniforms())
					.contains(HdWaterSource.UNIFORM_POINT_LIGHTS)),
				() -> assertTrue(java.util.Arrays.asList(HdWaterSource.breakingUniforms())
					.contains(HdWaterSource.UNIFORM_WATER_SPEC))
			);
		}
	}

	private static Set<String> varyings(String source) {
		Set<String> names = new HashSet<String>();
		Matcher matcher = VARYING.matcher(source);
		while (matcher.find()) {
			names.add(matcher.group(1));
		}
		return names;
	}

	@Test
	@DisplayName("the area's water reaches the surface as hdWaterParams, hdWaterTint and hdWaterClock")
	void waterAreaRules_shouldFollowUpstream() {
		String fragment = HdWaterSource.FRAGMENT;
		for (String[] list : new String[][] { HdWaterSource.uniforms(),
				HdWaterSource.reflectiveUniforms(), HdWaterSource.customUniforms(),
				HdWaterSource.breakingUniforms() }) {
			assertTrue(Arrays.asList(list).contains(HdWaterSource.UNIFORM_WATER_PARAMS));
			assertTrue(Arrays.asList(list).contains(HdWaterSource.UNIFORM_WATER_BLEND));
			assertTrue(Arrays.asList(list).contains(HdWaterSource.UNIFORM_WATER_TINT));
		}
		assertAll(
			() -> assertTrue(fragment.contains("vec2 hdWaterWorldUv634(vec2 xz, float tiles, float size)")),
			() -> assertTrue(fragment.contains("max(size, 0.25)")),
			() -> assertTrue(fragment.contains("uNormalStrength * uWaterParams.x")),
			() -> assertTrue(fragment.contains("uWaterSpec.x * uWaterParams.y")),
			() -> assertTrue(fragment.contains("hdWaterLayers634(xz, seconds, uWaterBlend.x)")),
			() -> assertTrue(fragment.contains("hdWaterWorldUv634(xz, 15.0, size)")),
			() -> assertTrue(fragment.contains("hdWaterFlow634(xz, seconds, uWaterParams.z)")),
			() -> assertTrue(fragment.contains("hdWaterTint634(hdWaterAreaTint634(), finalFresnel)")),
			() -> assertTrue(fragment.contains("surfaceSrgb = hdWaterAreaBody634(typeColour, typeLuma)")),
			() -> assertTrue(fragment.contains("uWaterSpec.x * uWaterParams.y")),
			() -> assertFalse(fragment.contains("hdWaterWorldUv634(vec2 xz, float tiles) {"))
		);
	}

}
