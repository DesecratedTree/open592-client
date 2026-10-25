package com.jagex.game.runetek5.client.hd.shader;

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
 * Structural checks the Java side can make without a driver. Numeric
 * equivalence still needs a real GL context; these catch the mistakes that
 * would otherwise fail on the player's machine only - a version the 592
 * context rejects, a varying that does not match across the two stages, a
 * uniform the pipeline sets that the shader never declares, or a light source
 * the stock path does not provide.
 */
@DisplayName("592HD lighting GLSL")
class HdShaderSourceTest {

	private static final Pattern VARYING = Pattern.compile("varying\\s+\\w+\\s+(\\w+)\\s*;");

	@Test
	@DisplayName("both stages declare GLSL 120, the version this toolkit's drivers accept")
	void sources_shouldBeGlsl120() {
		assertAll(
			() -> assertTrue(HdShaderSource.VERTEX.startsWith("#version 120\n"), firstLine(HdShaderSource.VERTEX)),
			() -> assertTrue(HdShaderSource.FRAGMENT.startsWith("#version 120\n"), firstLine(HdShaderSource.FRAGMENT)),
			() -> assertFalse(HdShaderSource.VERTEX.contains("layout(")),
			() -> assertFalse(HdShaderSource.FRAGMENT.contains("layout(")),
			() -> assertFalse(HdShaderSource.FRAGMENT.contains("#version 330")),
			() -> assertFalse(HdShaderSource.VERTEX.contains("in vec"))
		);
	}

	@Test
	@DisplayName("the vertex stage writes exactly the varyings the fragment stage reads")
	void varyings_shouldMatch() {
		Set<String> vertex = varyings(HdShaderSource.VERTEX);
		Set<String> fragment = varyings(HdShaderSource.FRAGMENT);

		assertAll(
			() -> assertFalse(vertex.isEmpty()),
			() -> assertTrue(vertex.equals(fragment), "vertex " + vertex + " vs fragment " + fragment),
			() -> assertTrue(vertex.contains("vEyeNormal")),
			() -> assertTrue(vertex.contains("vEyePosition")),
			() -> assertTrue(vertex.contains("vTexCoord"))
		);
	}

	@Test
	@DisplayName("every uniform the pipeline sets is declared by the stage that owns it")
	void uniforms_shouldBeDeclared() {
		for (String uniform : HdShaderSource.uniforms()) {
			assertTrue(HdShaderSource.FRAGMENT.contains("uniform ") && HdShaderSource.FRAGMENT.contains(" " + uniform + ";"),
				uniform + " is missing from the fragment source");
		}
		// The model-to-scene rows belong to the vertex stage, which is the only
		// place gl_Vertex is available.
		for (String uniform : HdShaderSource.vertexUniforms()) {
			assertTrue(HdShaderSource.VERTEX.contains(" " + uniform + ";"),
				uniform + " is missing from the vertex source");
		}
		assertFalse(HdShaderSource.FRAGMENT.contains("uModelRow"),
			"the fragment stage reads the varying, not the matrix");
	}

	@Test
	@DisplayName("the vertex stage feeds eye-space normals, scene-space positions and the stock clip transform")
	void vertex_shouldLightInEyeSpace() {
		assertAll(
			() -> assertTrue(HdShaderSource.VERTEX.contains("ftransform()")),
			() -> assertTrue(HdShaderSource.VERTEX.contains("gl_NormalMatrix * gl_Normal")),
			() -> assertTrue(HdShaderSource.VERTEX.contains("gl_ModelViewMatrix * gl_Vertex")),
			() -> assertTrue(HdShaderSource.VERTEX.contains("gl_MultiTexCoord0")),
			// The vertex buffer is model-local; the scene position the shadow map
			// and the occlusion kernel need is the model matrix applied to it.
			() -> assertTrue(HdShaderSource.VERTEX.contains("dot(uModelRow0, model)")),
			() -> assertTrue(HdShaderSource.VERTEX.contains("vWorldPosition = vec3("))
		);
	}

	@Test
	@DisplayName("the terrain takes its normal into eye space through the same view as its position")
	void sceneSpace_shouldTransformTheNormal() {
		// The terrain's normal arrives in scene axes while the fixed-function
		// lights are uploaded as eye-space positions under the camera modelview.
		// Dotting the two without this transform lights every tile from behind:
		// the real-GL ground test saw ambient only (0x333333) until it was fixed.
		assertAll(
			() -> assertTrue(HdShaderSource.VERTEX.contains("dot(uEyeRow0.xyz, gl_Normal)")),
			() -> assertTrue(HdShaderSource.VERTEX.contains("dot(uEyeRow1.xyz, gl_Normal)")),
			() -> assertTrue(HdShaderSource.VERTEX.contains("dot(uEyeRow2.xyz, gl_Normal)")),
			() -> assertTrue(HdShaderSource.VERTEX.contains("dot(uEyeRow0, world)"),
				"the same view must place the eye position"),
			() -> assertFalse(HdShaderSource.VERTEX.contains("vEyeNormal = normalize(gl_Normal);"),
				"a scene-space normal dotted with eye-space lights is a tile lit from behind")
		);
	}

	@Test
	@DisplayName("the fragment stage reads the same light, material and fog state fixed function does")
	void fragment_shouldReadSharedGlState() {
		assertAll(
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("gl_LightModel.ambient")),
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("gl_LightSource[0].diffuse")),
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("gl_LightSource[0].position")),
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("gl_LightSource[1].diffuse")),
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("quadraticAttenuation")),
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("gl_Color")),
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("texture2D(uTexture, vTexCoord)")),
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("gl_Fog.scale")),
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("gl_Fog.color")),
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("gl_FragColor"))
		);
	}

	@Test
	@DisplayName("point lights loop over the four slots the toolkit enables")
	void fragment_shouldLoopOverFourPointLights() {
		assertAll(
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("for (int i = 2; i < 6; i++)")),
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("i - 2 >= uPointLightCount")),
			() -> assertTrue(HdDrawState.MAX_POINT_LIGHTS == 4)
		);
	}

	@Test
	@DisplayName("the environment row recolours only the ambient, and its off path is the stock expression")
	void fragment_shouldRecolourOnlyTheAmbient() {
		String fragment = HdShaderSource.FRAGMENT;
		assertAll(
			() -> assertTrue(fragment.contains("uniform int uEnvLighting;")),
			() -> assertTrue(fragment.contains("vec3 ambientLight = gl_LightModel.ambient.rgb;")),
			() -> assertTrue(fragment.contains("if (uEnvLighting != 0) {")),
			() -> assertTrue(fragment.contains("mix(ambientLight, uEnvSky.rgb * uEnvSky.a, skyWeight)")),
			() -> assertTrue(fragment.contains("vec3 lighting = ambientLight * occlusion;")),
			// The sun, anti-sun and point-light terms must keep reading GL state:
			// the row has no business moving the environment's own sun upload.
			() -> assertTrue(fragment.contains("lighting += gl_LightSource[0].diffuse.rgb")),
			() -> assertFalse(fragment.contains("gl_LightSource[0].diffuse.rgb * uEnv"),
				"the row must not scale the sun's colour or intensity a second time")
		);
	}

	@Test
	@DisplayName("the fog-off row keeps the band out of a draw the stock path does not fog")
	void fragment_shouldLetADrawDropTheBand() {
		assertAll(
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("uniform int uFogOff;")),
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("if (uFogOff == 0) {")),
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("float fogFactor = 1.0;")),
			// Zero is GL's initial value and must mean "apply the band", so every
			// draw that does not ask for the row renders as it did before it.
			() -> assertTrue(java.util.Arrays.asList(HdShaderSource.uniforms())
				.contains(HdShaderSource.UNIFORM_FOG_OFF))
		);
	}

	@Test
	@DisplayName("a degenerate scene normal takes the planar ground sample, not NaN")
	void fragment_shouldFallBackToThePlanarSample() {
		// The triplanar weights divide by the scene normal's components. A zero
		// normal is NaN after normalize(), NaN would travel through those
		// weights into the albedo, and the ground pass draws a black tile that no
		// longer shows its material - the transparent-grass frame. Flat terrain
		// has no normal array at all, so the planar branch is the ordinary case.
		String fragment = HdShaderSource.FRAGMENT;
		assertAll(
			() -> assertTrue(fragment.contains("if (!(steep > 0.0)) {")),
			() -> assertTrue(fragment.contains("return planar;")),
			() -> assertFalse(fragment.contains("if (steep > 0.0) {"),
				"the guard has to be written so NaN takes the planar branch"),
			// A material with no cell is reported, not multiplied in: neutral
			// white would erase the layer's still-bound stock texture and leave
			// only the vertex colour.
			() -> assertTrue(fragment.contains("return vec4(-1.0);"),
				"a material with no cell must report itself to the caller"),
			() -> assertTrue(fragment.contains("if (ground.a < 0.0) {"),
				"and the caller must keep the stock texture for that draw")
		);
	}

	@Test
	@DisplayName("a vertex format with no normal array still carries a usable normal")
	void vertex_shouldReplaceADegenerateNormal() {
		// GL reports the current normal when the format has no normal array, and
		// a pass that never set one leaves it at zero. normalize(0) is NaN, and
		// the lighting terms and the triplanar weights are both built from it.
		String vertex = HdShaderSource.VERTEX;
		assertAll(
			() -> assertTrue(vertex.contains("vec3 rawNormal = gl_Normal;")),
			() -> assertTrue(vertex.contains("if (!(dot(rawNormal, rawNormal) > 1.0e-8)) {")),
			() -> assertTrue(vertex.contains("rawNormal = vec3(0.0, 1.0, 0.0);")),
			() -> assertTrue(vertex.contains("vSceneNormal = normalize(rawNormal);")),
			() -> assertTrue(vertex.contains("if (!(dot(vEyeNormal, vEyeNormal) > 0.5)) {")),
			() -> assertTrue(vertex.contains(
				"vEyeNormal = normalize(vec3(uEyeRow0.y, uEyeRow1.y, uEyeRow2.y));")),
			// The negated comparison is the point: NaN fails every ordered test.
			() -> assertFalse(vertex.contains("if (dot(rawNormal, rawNormal) > 1.0e-8) {"),
				"the guard has to be written so NaN takes it")
		);
	}

	@Test
	@DisplayName("alpha is never lit and fog only touches rgb")
	void fragment_shouldKeepAlphaOutOfLighting() {
		assertAll(
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("clamp(albedo.rgb * lighting, 0.0, 1.0)"),
				"fog must see the same clamped colour fixed function fogs"),
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("mix(gl_Fog.color.rgb, lit, fogFactor)")),
			() -> assertTrue(HdShaderSource.FRAGMENT.contains(", albedo.a)")),
			() -> assertFalse(HdShaderSource.FRAGMENT.contains("lit.a"),
				"alpha must come from the albedo, never from the lit colour")
		);
	}

	@Test
	@DisplayName("the sun's shadow scales only the sun's term, and only when a map is bound")
	void fragment_shouldShadowOnlyTheSun() {
		String fragment = HdShaderSource.FRAGMENT;
		assertAll(
			() -> assertTrue(fragment.contains("if (uShadowEnabled == 0) {"),
				"the no-map path must return before any texture lookup"),
			() -> assertTrue(fragment.contains("float sunShadow() {")),
			// The sun term is the only diffuse term multiplied by the visibility.
			() -> assertTrue(fragment.contains("gl_LightSource[0].diffuse.rgb * max(dot(normal, "
				+ "normalize(gl_LightSource[0].position.xyz)), 0.0) * shadow;")),
			() -> assertFalse(fragment.contains("gl_LightSource[1].diffuse.rgb * max(dot(normal, "
				+ "normalize(gl_LightSource[1].position.xyz)), 0.0) * shadow"),
				"the anti-sun is a fill light: it must not be shadowed"),
			() -> assertTrue(fragment.contains("lighting += gl_LightSource[i].diffuse.rgb"))
		);
	}

	@Test
	@DisplayName("the map is projected per pixel with a PCF kernel that can widen to 5x5")
	void fragment_shouldProjectAndFilter() {
		String fragment = HdShaderSource.FRAGMENT;
		assertAll(
			() -> assertTrue(fragment.contains("dot(uShadowRow0, world)")),
			() -> assertTrue(fragment.contains("projected.xyz / projected.w")),
			() -> assertTrue(fragment.contains("coord.z - uShadowBias > depth")),
			() -> assertTrue(fragment.contains("for (int y = -2; y <= 2; y++)")),
			() -> assertTrue(fragment.contains("for (int x = -2; x <= 2; x++)")),
			() -> assertTrue(fragment.contains("x < -uShadowRadius || x > uShadowRadius")),
			() -> assertTrue(fragment.contains("visible / samples"), "PCF averages the taps"),
			// Outside the light's volume nothing is known to occlude, so stay lit
			// rather than reading clamped edge texels as shadow.
			() -> assertTrue(fragment.contains("coord.x > 1.0")),
			() -> assertEquals(HdDrawState.clampRadius(9), 2, "the loop's ceiling is the clamp")
		);
	}

	@Test
	@DisplayName("the shadow sampler has its own unit so it cannot displace a material")
	void shadowSampler_shouldLiveOnItsOwnUnit() {
		assertAll(
			() -> assertEquals(0, HdShaderSource.SAMPLER_UNIT, "materials sample unit 0"),
			() -> assertEquals(1, HdShaderSource.SAMPLER_UNIT_SHADOW),
			() -> assertTrue(HdShaderSource.SAMPLER_UNIT_SHADOW
				== com.jagex.game.runetek5.client.hd.shadow.SunShadowMap.TEXTURE_UNIT,
				"the pass and the shader must agree on the unit"),
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("texture2D(uShadowMap, coord.xy")),
			() -> assertTrue(HdShaderSource.FRAGMENT.contains("vec2(float(x), float(y)) * uShadowTexel"))
		);
	}

	@Test
	@DisplayName("the vegetation rows branch in the vertex stage, and both-off never takes it")
	void vertex_shouldOnlyDisplaceMarkedPlantsWithARowOn() {
		String vertex = HdShaderSource.VERTEX;
		assertAll(
			() -> assertTrue(vertex.contains("uniform int uVegetation;")),
			() -> assertTrue(vertex.contains("uniform vec4 uSway;")),
			() -> assertTrue(vertex.contains("uniform vec4 uSwayPhase;")),
			() -> assertTrue(vertex.contains("uniform vec4 uSwayOrigin;")),
			// The kind and both amplitudes must be in the condition: a marked plant
			// with both rows off, or a wall with them on, is the same image.
			() -> assertTrue(vertex.contains("if (uVegetation != 0 && (uSway.x != 0.0 || uSway.y != 0.0)) {")),
			() -> assertTrue(vertex.contains("vec3 swayOffset(vec3 world) {")),
			() -> assertTrue(vertex.contains("vWorldPosition + swayOffset(vWorldPosition)")),
			// The stock transform is still computed first and used when the branch
			// is not taken, which is what makes Off the stock position.
			() -> assertTrue(vertex.contains("vec4 clip = ftransform();")),
			() -> assertTrue(vertex.contains("gl_Position = clip;")),
			() -> assertTrue(vertex.contains("clip = gl_ProjectionMatrix * eye;")),
			// The lever is a height above the model's own base, so roots stay put.
			() -> assertTrue(vertex.contains("world.y - uModelRow1.w")),
			// Compact support: nothing outside the grass radius is touched.
			() -> assertTrue(vertex.contains("max(uSway.z, 1.0e-4)")),
			// A vertex uniform belongs to the vertex stage, never the fragment one.
			() -> assertFalse(HdShaderSource.FRAGMENT.contains("uSway")),
			() -> assertFalse(HdShaderSource.FRAGMENT.contains("uVegetation")),
			() -> assertTrue(java.util.Arrays.asList(HdShaderSource.vertexUniforms())
				.contains(HdShaderSource.UNIFORM_VEGETATION)),
			() -> assertTrue(java.util.Arrays.asList(HdShaderSource.vertexUniforms())
				.contains(HdShaderSource.UNIFORM_SWAY)),
			() -> assertFalse(java.util.Arrays.asList(HdShaderSource.uniforms())
				.contains(HdShaderSource.UNIFORM_SWAY), "the fragment list is fragment uniforms only")
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

	private static String firstLine(String source) {
		int newline = source.indexOf('\n');
		return newline < 0 ? source : source.substring(0, newline);
	}
}
