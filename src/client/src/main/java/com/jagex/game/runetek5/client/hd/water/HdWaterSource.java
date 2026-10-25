package com.jagex.game.runetek5.client.hd.water;

/**
 * The GLSL 1.20 source for 592HD's water, and the names of the uniforms the
 * pipeline sets.
 *
 * <p>Same constraints as the lighting shader: GLSL 120 on the 592 fixed-function
 * toolkit, reading the light and fog state the stock path already uploads
 * ({@code gl_LightSource}, {@code gl_Fog}) so the water cannot drift from the
 * frame's sun and fog band. What is new is per-pixel: the surface normal is
 * perturbed by two crossing wave trains driven by {@code uTime}, the Fresnel
 * term is evaluated per fragment against the perturbed normal, and the sun's
 * highlight is a Blinn-Phong lobe instead of a flat vertex-lit sheen.
 *
 * <p>Three details carry the look, and each is checked by a pixel test rather
 * than by eye:
 *
 * <ul>
 * <li>the perturbation happens in scene space (where {@code gl_Vertex} lives in
 * this client, because the modelview carries the camera) and is carried to eye
 * space through the three eye-space basis vectors the vertex stage supplies,
 * so the waves are attached to the world and not to the camera - and so the
 * fragment stage never names {@code gl_NormalMatrix}, which GLSL 120 defines
 * only for vertex shaders (some drivers accept it there anyway; the spec does
 * not);</li>
 * <li>{@code uFlow} is the amplitude, so amplitude zero is a perfectly flat
 * surface - the still frame the pixel test compares against;</li>
 * <li>UVs are {@code gl_TextureMatrix[0] * gl_MultiTexCoord0}, because stock
 * water scrolls its texture with the texture matrix; sampling raw
 * {@code gl_MultiTexCoord0} would freeze the ripples.</li>
 * </ul>
 *
 * <p>There are four fragment programs, and the still one is unchanged from the
 * pass that shipped. {@link #CUSTOM_FRAGMENT} is the row's third value: the
 * same vertex stage, a surface with a third, diagonal wave train, a sharper
 * Fresnel, absorption from a deep to a shallow colour instead of a flat
 * darkening, and sun sparkles that blink on the crests. Its palette is uniforms
 * rather than literals, so the look is data the pipeline owns; it declares no
 * cube sampler, so like the calm program its image cannot depend on one.
 *
 * <p>{@link #FRAGMENT} reflects the fog band's colour - the
 * cheapest image of the sky there is - and is what every calm water draw keeps
 * using. {@link #REFLECTIVE_FRAGMENT} is the water type the stock client called
 * reflective: it samples the environment cube map the toolkit already binds for
 * the stock cube effects ({@code GlToolkit.method1946()}), along the reflection
 * of the view about the perturbed normal. That cube is the sky the water sits
 * under, so a repainted sky repaints the reflection. Splitting the program
 * rather than branching inside one keeps the calm path free of a cube sampler
 * it never fills, which is what makes its output provably the old output.
 *
 * <p>{@link #BREAKING_FRAGMENT} is the third water type the stock client drew
 * with a program of its own ({@code BreakingWaterEffect}): the same surface,
 * cube, sun and fog as the reflective stage, plus the breaking band that
 * material's own parameters describe - its ripple scale on the coordinates, its
 * rate on the clock, and its depth, shore offset, crest colour and sharpening
 * exponent on the band. The stock stage reads those from the same two fields
 * ({@link HdWaterBreaking} is that decode) but samples a 3D normal volume the
 * HD path does not own; here the row's own wave field supplies the crest mask,
 * which is why this stage's numbers mean the same thing without a volume.
 */
public final class HdWaterSource {

	/** {@code #version} both stages declare; see the class comment. */
	public static final int GLSL_VERSION = 120;

	/** {@code sampler2D}: the water texture, scrolled by the stock texture matrix. */
	public static final String UNIFORM_TEXTURE = "uTexture";

	/** {@code float}: animation seconds; the only per-frame uniform. */
	public static final String UNIFORM_TIME = "uTime";

	/** {@code float}: wave amplitude. Zero is a flat, still surface. */
	public static final String UNIFORM_FLOW = "uFlow";

	/** {@code float}: Blinn-Phong exponent for the sun's highlight. */
	public static final String UNIFORM_SHININESS = "uShininess";

	/** {@code vec3}: the custom surface's colour looking straight down. */
	public static final String UNIFORM_DEEP_COLOUR = "uDeepColour";

	/** {@code vec3}: the custom surface's colour at a grazing angle, tinting the texture. */
	public static final String UNIFORM_SHALLOW_COLOUR = "uShallowColour";

	/**
	 * {@code samplerCube}: the environment the surface reflects. The reflective
	 * and breaking fragments declare it; the still program never has a sampler it
	 * would have to fill.
	 */
	public static final String UNIFORM_ENVIRONMENT = "uEnvironment";

	/** {@code float}: the depth the breaking type's band forms at. */
	public static final String UNIFORM_BREAK_DEPTH = "uBreakDepth";

	/** {@code float}: the crest mask's bias on the depth ratio. */
	public static final String UNIFORM_BREAK_OFFSET = "uBreakOffset";

	/** {@code vec2}: the breaking type's two crest colour values. */
	public static final String UNIFORM_WAVE_INTENSITY = "uWaveIntensity";

	/** {@code float}: the power the breaking band is sharpened by. */
	public static final String UNIFORM_WAVE_EXPONENT = "uWaveExponent";

	/** {@code float}: the breaking material's ripple scale on the texture. */
	public static final String UNIFORM_BREAK_SCALE = "uBreakScale";

	/** {@code float}: the breaking material's animation rate on the row's clock. */
	public static final String UNIFORM_BREAK_RATE = "uBreakRate";

	/**
	 * The unit the environment cube is bound to. Unit 1 is where the stock cube
	 * effects put it ({@code ReflectiveRipplingWaterEffect.enable}), so the HD
	 * reflection cannot fight the effect manager for a unit.
	 */
	public static final int ENVIRONMENT_UNIT = 1;

	private static final String[] UNIFORMS = {
		UNIFORM_TEXTURE, UNIFORM_TIME, UNIFORM_FLOW, UNIFORM_SHININESS
	};

	private static final String[] REFLECTIVE_UNIFORMS = {
		UNIFORM_TEXTURE, UNIFORM_ENVIRONMENT, UNIFORM_TIME, UNIFORM_FLOW, UNIFORM_SHININESS
	};

	private static final String[] CUSTOM_UNIFORMS = {
		UNIFORM_TEXTURE, UNIFORM_TIME, UNIFORM_FLOW, UNIFORM_SHININESS, UNIFORM_DEEP_COLOUR,
		UNIFORM_SHALLOW_COLOUR
	};

	private static final String[] BREAKING_UNIFORMS = {
		UNIFORM_TEXTURE, UNIFORM_ENVIRONMENT, UNIFORM_TIME, UNIFORM_FLOW, UNIFORM_SHININESS,
		UNIFORM_BREAK_DEPTH, UNIFORM_BREAK_OFFSET, UNIFORM_WAVE_INTENSITY, UNIFORM_WAVE_EXPONENT,
		UNIFORM_BREAK_SCALE, UNIFORM_BREAK_RATE
	};

	public static final String VERTEX = "#version " + GLSL_VERSION + "\n"
		+ "\n"
		+ "varying vec3 vEyePosition;\n"
		+ "varying vec3 vWorldPosition;\n"
		+ "varying vec3 vWorldNormal;\n"
		+ "varying vec3 vNormalX;\n"
		+ "varying vec3 vNormalY;\n"
		+ "varying vec3 vNormalZ;\n"
		+ "varying vec2 vTexCoord;\n"
		+ "varying float vFoam;\n"
		+ "\n"
		+ "void main() {\n"
		+ "\tgl_Position = ftransform();\n"
		+ "\tvec4 eye = gl_ModelViewMatrix * gl_Vertex;\n"
		+ "\tvEyePosition = eye.xyz;\n"
		+ "\t// Scene space: this client's modelview carries the camera, not the model.\n"
		+ "\tvWorldPosition = gl_Vertex.xyz;\n"
		+ "\tvWorldNormal = gl_Normal;\n"
		+ "\t// Eye-space images of the world axes: the fragment stage needs a normal\n"
		+ "\t// matrix, and GLSL 120 only gives gl_NormalMatrix to the vertex stage.\n"
		+ "\tvNormalX = gl_NormalMatrix * vec3(1.0, 0.0, 0.0);\n"
		+ "\tvNormalY = gl_NormalMatrix * vec3(0.0, 1.0, 0.0);\n"
		+ "\tvNormalZ = gl_NormalMatrix * vec3(0.0, 0.0, 1.0);\n"
		+ "\t// The stock water path scrolls with the texture matrix; keep that scroll.\n"
		+ "\tvTexCoord = (gl_TextureMatrix[0] * gl_MultiTexCoord0).xy;\n"
		+ "\t// z is the stock per-vertex depth/foam value, applied per pixel here.\n"
		+ "\tvFoam = gl_MultiTexCoord0.z;\n"
		+ "\tgl_FrontColor = gl_Color;\n"
		+ "\tgl_BackColor = gl_Color;\n"
		+ "}\n";

	public static final String FRAGMENT = "#version " + GLSL_VERSION + "\n"
		+ "\n"
		+ "uniform sampler2D uTexture;\n"
		+ "uniform float uTime;\n"
		+ "uniform float uFlow;\n"
		+ "uniform float uShininess;\n"
		+ "\n"
		+ "varying vec3 vEyePosition;\n"
		+ "varying vec3 vWorldPosition;\n"
		+ "varying vec3 vWorldNormal;\n"
		+ "varying vec3 vNormalX;\n"
		+ "varying vec3 vNormalY;\n"
		+ "varying vec3 vNormalZ;\n"
		+ "varying vec2 vTexCoord;\n"
		+ "varying float vFoam;\n"
		+ "\n"
		+ "void main() {\n"
		+ "\tvec3 surface = vWorldNormal;\n"
		+ "\t// Water geometry without normals is still a flat surface facing up.\n"
		+ "\tif (dot(surface, surface) < 1.0e-6) {\n"
		+ "\t\tsurface = vec3(0.0, 1.0, 0.0);\n"
		+ "\t}\n"
		+ "\t// Two crossing wave trains in scene space: the surface is never flat,\n"
		+ "\t// and the waves stay put in the world as the camera moves.\n"
		+ "\tfloat phase = uTime;\n"
		+ "\tsurface.x += sin(vWorldPosition.x * 0.31 + phase * 1.9) * uFlow\n"
		+ "\t\t+ sin(vWorldPosition.z * 0.17 - phase * 1.3) * uFlow * 0.6;\n"
		+ "\tsurface.z += cos(vWorldPosition.z * 0.27 - phase * 1.6) * uFlow\n"
		+ "\t\t+ cos(vWorldPosition.x * 0.21 + phase * 1.1) * uFlow * 0.6;\n"
		+ "\tsurface = normalize(surface);\n"
		+ "\tvec3 normal = normalize(vNormalX * surface.x\n"
		+ "\t\t+ vNormalY * surface.y + vNormalZ * surface.z);\n"
		+ "\tvec3 view = normalize(-vEyePosition);\n"
		+ "\tvec3 light = normalize(gl_LightSource[0].position.xyz);\n"
		+ "\t// `half` is a reserved word in GLSL 1.20; the Blinn-Phong vector needs a\n"
		+ "\t// name of its own.\n"
		+ "\tvec3 halfway = normalize(light + view);\n"
		+ "\t// Per-pixel Fresnel: grazing angles see the sky, steep ones see the body.\n"
		+ "\tfloat fresnel = pow(1.0 - clamp(dot(normal, view), 0.0, 1.0), 3.0);\n"
		+ "\tvec3 texel = texture2D(uTexture, vTexCoord).rgb;\n"
		+ "\tvec3 water = texel * gl_Color.rgb;\n"
		+ "\tvec3 body = water * 0.55;\n"
		+ "\t// gl_Fog.color is the frame's sky/fog colour: the cheapest reflection\n"
		+ "\t// there is, and it cannot disagree with the fog band around the water.\n"
		+ "\tvec3 sheen = gl_Fog.color.rgb;\n"
		+ "\tvec3 base = mix(body, sheen, fresnel * 0.7);\n"
		+ "\tfloat diffuse = max(dot(normal, light), 0.0);\n"
		+ "\tfloat specular = pow(max(dot(normal, halfway), 0.0), uShininess);\n"
		+ "\tvec3 lit = base + water * gl_LightSource[0].diffuse.rgb * diffuse * 0.35\n"
		+ "\t\t+ gl_LightSource[0].diffuse.rgb * specular;\n"
		+ "\t// The stock path fades foam in from the per-vertex depth value.\n"
		+ "\tlit = mix(lit, vec3(1.0), clamp(vFoam, 0.0, 1.0) * 0.2);\n"
		+ "\tvec3 colour = clamp(lit, 0.0, 1.0);\n"
		+ "\tfloat fogFactor = clamp((gl_Fog.end - abs(vEyePosition.z)) * gl_Fog.scale, 0.0, 1.0);\n"
		+ "\tgl_FragColor = vec4(mix(gl_Fog.color.rgb, colour, fogFactor), gl_Color.a);\n"
		+ "}\n";

	/**
	 * The reflective water type's fragment stage. Same surface, sun, foam and fog
	 * terms as {@link #FRAGMENT}; the difference is where the Fresnel sheen comes
	 * from - the environment cube instead of the fog band's flat colour.
	 */
	public static final String REFLECTIVE_FRAGMENT = "#version " + GLSL_VERSION + "\n"
		+ "\n"
		+ "uniform sampler2D uTexture;\n"
		+ "uniform samplerCube uEnvironment;\n"
		+ "uniform float uTime;\n"
		+ "uniform float uFlow;\n"
		+ "uniform float uShininess;\n"
		+ "\n"
		+ "varying vec3 vEyePosition;\n"
		+ "varying vec3 vWorldPosition;\n"
		+ "varying vec3 vWorldNormal;\n"
		+ "varying vec3 vNormalX;\n"
		+ "varying vec3 vNormalY;\n"
		+ "varying vec3 vNormalZ;\n"
		+ "varying vec2 vTexCoord;\n"
		+ "varying float vFoam;\n"
		+ "\n"
		+ "void main() {\n"
		+ "\tvec3 surface = vWorldNormal;\n"
		+ "\t// Water geometry without normals is still a flat surface facing up.\n"
		+ "\tif (dot(surface, surface) < 1.0e-6) {\n"
		+ "\t\tsurface = vec3(0.0, 1.0, 0.0);\n"
		+ "\t}\n"
		+ "\t// Two crossing wave trains in scene space: the surface is never flat,\n"
		+ "\t// and the waves stay put in the world as the camera moves.\n"
		+ "\tfloat phase = uTime;\n"
		+ "\tsurface.x += sin(vWorldPosition.x * 0.31 + phase * 1.9) * uFlow\n"
		+ "\t\t+ sin(vWorldPosition.z * 0.17 - phase * 1.3) * uFlow * 0.6;\n"
		+ "\tsurface.z += cos(vWorldPosition.z * 0.27 - phase * 1.6) * uFlow\n"
		+ "\t\t+ cos(vWorldPosition.x * 0.21 + phase * 1.1) * uFlow * 0.6;\n"
		+ "\tsurface = normalize(surface);\n"
		+ "\tvec3 normal = normalize(vNormalX * surface.x\n"
		+ "\t\t+ vNormalY * surface.y + vNormalZ * surface.z);\n"
		+ "\tvec3 view = normalize(-vEyePosition);\n"
		+ "\tvec3 light = normalize(gl_LightSource[0].position.xyz);\n"
		+ "\tvec3 halfway = normalize(light + view);\n"
		+ "\t// Per-pixel Fresnel: grazing angles see the sky, steep ones see the body.\n"
		+ "\tfloat fresnel = pow(1.0 - clamp(dot(normal, view), 0.0, 1.0), 3.0);\n"
		+ "\tvec3 texel = texture2D(uTexture, vTexCoord).rgb;\n"
		+ "\tvec3 water = texel * gl_Color.rgb;\n"
		+ "\tvec3 body = water * 0.55;\n"
		+ "\t// The view reflected about the perturbed normal, sampled from the\n"
		+ "\t// environment cube the stock cube effects already bind: the surface\n"
		+ "\t// shows the sky it sits under, and follows when that sky is repainted.\n"
		+ "\tvec3 reflected = reflect(view, normal);\n"
		+ "\tvec3 environment = textureCube(uEnvironment, reflected).rgb;\n"
		+ "\tvec3 base = mix(body, environment, fresnel * 0.7);\n"
		+ "\tfloat diffuse = max(dot(normal, light), 0.0);\n"
		+ "\tfloat specular = pow(max(dot(normal, halfway), 0.0), uShininess);\n"
		+ "\tvec3 lit = base + water * gl_LightSource[0].diffuse.rgb * diffuse * 0.35\n"
		+ "\t\t+ gl_LightSource[0].diffuse.rgb * specular;\n"
		+ "\t// The stock path fades foam in from the per-vertex depth value.\n"
		+ "\tlit = mix(lit, vec3(1.0), clamp(vFoam, 0.0, 1.0) * 0.2);\n"
		+ "\tvec3 colour = clamp(lit, 0.0, 1.0);\n"
		+ "\tfloat fogFactor = clamp((gl_Fog.end - abs(vEyePosition.z)) * gl_Fog.scale, 0.0, 1.0);\n"
		+ "\tgl_FragColor = vec4(mix(gl_Fog.color.rgb, colour, fogFactor), gl_Color.a);\n"
		+ "}\n";

	/**
	 * The breaking water type's fragment stage: the reflective stage's surface,
	 * cube, sun and fog, with the material's own breaking band laid over it.
	 *
	 * <p>Everything the band is made of comes from the material
	 * ({@link HdWaterBreaking}): {@code uBreakScale} on the texture coordinates,
	 * {@code uBreakRate} on the row's clock, and {@code uBreakDepth},
	 * {@code uBreakOffset}, {@code uWaveIntensity} and {@code uWaveExponent} on
	 * the band itself. The one substitution is the mask the offset is applied to:
	 * the stock stage takes it from a 3D normal volume, and this stage takes the
	 * row's own crest measure from the perturbed surface - a term the custom
	 * surface already ships - so the band's shape still follows the water rather
	 * than a texture the HD path would have to own.
	 */
	public static final String BREAKING_FRAGMENT = "#version " + GLSL_VERSION + "\n"
		+ "\n"
		+ "uniform sampler2D uTexture;\n"
		+ "uniform samplerCube uEnvironment;\n"
		+ "uniform float uTime;\n"
		+ "uniform float uFlow;\n"
		+ "uniform float uShininess;\n"
		+ "uniform float uBreakDepth;\n"
		+ "uniform float uBreakOffset;\n"
		+ "uniform vec2 uWaveIntensity;\n"
		+ "uniform float uWaveExponent;\n"
		+ "uniform float uBreakScale;\n"
		+ "uniform float uBreakRate;\n"
		+ "\n"
		+ "varying vec3 vEyePosition;\n"
		+ "varying vec3 vWorldPosition;\n"
		+ "varying vec3 vWorldNormal;\n"
		+ "varying vec3 vNormalX;\n"
		+ "varying vec3 vNormalY;\n"
		+ "varying vec3 vNormalZ;\n"
		+ "varying vec2 vTexCoord;\n"
		+ "varying float vFoam;\n"
		+ "\n"
		+ "void main() {\n"
		+ "\tvec3 surface = vWorldNormal;\n"
		+ "\t// Water geometry without normals is still a flat surface facing up.\n"
		+ "\tif (dot(surface, surface) < 1.0e-6) {\n"
		+ "\t\tsurface = vec3(0.0, 1.0, 0.0);\n"
		+ "\t}\n"
		+ "\t// The material's own animated rate on the row's clock, and the same two\n"
		+ "\t// crossing wave trains every water surface in this row runs.\n"
		+ "\tfloat phase = uTime * uBreakRate;\n"
		+ "\tsurface.x += sin(vWorldPosition.x * 0.31 + phase * 1.9) * uFlow\n"
		+ "\t\t+ sin(vWorldPosition.z * 0.17 - phase * 1.3) * uFlow * 0.6;\n"
		+ "\tsurface.z += cos(vWorldPosition.z * 0.27 - phase * 1.6) * uFlow\n"
		+ "\t\t+ cos(vWorldPosition.x * 0.21 + phase * 1.1) * uFlow * 0.6;\n"
		+ "\tsurface = normalize(surface);\n"
		+ "\tvec3 normal = normalize(vNormalX * surface.x\n"
		+ "\t\t+ vNormalY * surface.y + vNormalZ * surface.z);\n"
		+ "\tvec3 up = normalize(vNormalY);\n"
		+ "\tvec3 view = normalize(-vEyePosition);\n"
		+ "\tvec3 light = normalize(gl_LightSource[0].position.xyz);\n"
		+ "\tvec3 halfway = normalize(light + view);\n"
		+ "\tfloat fresnel = pow(1.0 - clamp(dot(normal, view), 0.0, 1.0), 3.0);\n"
		+ "\t// The material's ripple scale on the stock texture coordinates, the same\n"
		+ "\t// multiplication the stock breaking vertex stage performs.\n"
		+ "\tvec3 texel = texture2D(uTexture, vTexCoord * uBreakScale).rgb;\n"
		+ "\tvec3 water = texel * gl_Color.rgb;\n"
		+ "\tvec3 body = water * 0.55;\n"
		+ "\t// The crest measure the custom surface already uses: how far the wave\n"
		+ "\t// field is tilted away from world up.\n"
		+ "\tfloat crest = clamp((1.0 - dot(normal, up)) * 6.0, 0.0, 1.0);\n"
		+ "\t// The stock breaking type's shore factor and band, from this material's\n"
		+ "\t// own depth, shore offset and sharpening exponent.\n"
		+ "\tfloat shore = clamp(vFoam / max(uBreakDepth, 1.0) - uBreakOffset * crest, 0.0, 1.0);\n"
		+ "\tfloat band = pow(1.0 - shore, uWaveExponent) - 0.5;\n"
		+ "\tband = 1.0 - 4.0 * band * band;\n"
		+ "\t// The material's two values are the crest's colour, exactly as the stock\n"
		+ "\t// stage mixes them: intensity.x * mask + intensity.y.\n"
		+ "\tvec3 crestColour = vec3(uWaveIntensity.y + uWaveIntensity.x * crest);\n"
		+ "\tvec3 environment = textureCube(uEnvironment, reflect(view, normal)).rgb;\n"
		+ "\tvec3 sheen = mix(environment, crestColour, clamp(band, -1.0, 1.0));\n"
		+ "\tvec3 base = mix(body, sheen, fresnel * 0.7);\n"
		+ "\tfloat diffuse = max(dot(normal, light), 0.0);\n"
		+ "\tfloat specular = pow(max(dot(normal, halfway), 0.0), uShininess);\n"
		+ "\tvec3 lit = base + water * gl_LightSource[0].diffuse.rgb * diffuse * 0.35\n"
		+ "\t\t+ gl_LightSource[0].diffuse.rgb * specular;\n"
		+ "\t// The stock path fades foam in from the per-vertex depth value.\n"
		+ "\tlit = mix(lit, vec3(1.0), clamp(vFoam, 0.0, 1.0) * 0.2);\n"
		+ "\tvec3 colour = clamp(lit, 0.0, 1.0);\n"
		+ "\tfloat fogFactor = clamp((gl_Fog.end - abs(vEyePosition.z)) * gl_Fog.scale, 0.0, 1.0);\n"
		+ "\tgl_FragColor = vec4(mix(gl_Fog.color.rgb, colour, fogFactor), gl_Color.a);\n"
		+ "}\n";

	/**
	 * The custom surface's fragment stage: the water section's third look.
	 *
	 * <p>Same surface basis, sun and fog as {@link #FRAGMENT}, so it sits in the
	 * same frame; what it adds is the look the row's Custom value promises - a
	 * third wave train along the diagonal, absorption between two colours, a
	 * tighter Fresnel, crest light and a hashed sun sparkle. No cube sampler, so
	 * its image cannot depend on an environment the draw did not ask for.
	 */
	public static final String CUSTOM_FRAGMENT = "#version " + GLSL_VERSION + "\n"
		+ "\n"
		+ "uniform sampler2D uTexture;\n"
		+ "uniform float uTime;\n"
		+ "uniform float uFlow;\n"
		+ "uniform float uShininess;\n"
		+ "uniform vec3 uDeepColour;\n"
		+ "uniform vec3 uShallowColour;\n"
		+ "\n"
		+ "varying vec3 vEyePosition;\n"
		+ "varying vec3 vWorldPosition;\n"
		+ "varying vec3 vWorldNormal;\n"
		+ "varying vec3 vNormalX;\n"
		+ "varying vec3 vNormalY;\n"
		+ "varying vec3 vNormalZ;\n"
		+ "varying vec2 vTexCoord;\n"
		+ "varying float vFoam;\n"
		+ "\n"
		+ "// A sparkle that lives on the surface: a hash of the scene cell and the\n"
		+ "// step of the clock, so a cell blinks for a few frames and goes dark.\n"
		+ "float crestHash(vec2 cell, float tick) {\n"
		+ "\treturn fract(sin(dot(cell + tick, vec2(127.1, 311.7))) * 43758.5453);\n"
		+ "}\n"
		+ "\n"
		+ "void main() {\n"
		+ "\tvec3 surface = vWorldNormal;\n"
		+ "\t// Water geometry without normals is still a flat surface facing up.\n"
		+ "\tif (dot(surface, surface) < 1.0e-6) {\n"
		+ "\t\tsurface = vec3(0.0, 1.0, 0.0);\n"
		+ "\t}\n"
		+ "\tfloat phase = uTime;\n"
		+ "\t// The standard surface's two trains, plus a third along the diagonal.\n"
		+ "\tsurface.x += sin(vWorldPosition.x * 0.31 + phase * 1.9) * uFlow\n"
		+ "\t\t+ sin(vWorldPosition.z * 0.17 - phase * 1.3) * uFlow * 0.6;\n"
		+ "\tsurface.z += cos(vWorldPosition.z * 0.27 - phase * 1.6) * uFlow\n"
		+ "\t\t+ cos(vWorldPosition.x * 0.21 + phase * 1.1) * uFlow * 0.6;\n"
		+ "\tsurface.x += sin((vWorldPosition.x + vWorldPosition.z) * 0.12 + phase * 0.8) * uFlow * 0.45;\n"
		+ "\tsurface.z += cos((vWorldPosition.x - vWorldPosition.z) * 0.14 - phase * 0.9) * uFlow * 0.45;\n"
		+ "\tsurface = normalize(surface);\n"
		+ "\tvec3 normal = normalize(vNormalX * surface.x\n"
		+ "\t\t+ vNormalY * surface.y + vNormalZ * surface.z);\n"
		+ "\tvec3 up = normalize(vNormalY);\n"
		+ "\tvec3 view = normalize(-vEyePosition);\n"
		+ "\tvec3 light = normalize(gl_LightSource[0].position.xyz);\n"
		+ "\t// `half` is reserved in GLSL 1.20; the Blinn-Phong vector needs a name.\n"
		+ "\tvec3 halfway = normalize(light + view);\n"
		+ "\tfloat facing = clamp(dot(normal, view), 0.0, 1.0);\n"
		+ "\t// A sharper Fresnel than the standard surface: the horizon turns to\n"
		+ "\t// sky sooner, which is what makes the water read as deep.\n"
		+ "\tfloat fresnel = pow(1.0 - facing, 4.0);\n"
		+ "\tvec3 texel = texture2D(uTexture, vTexCoord).rgb;\n"
		+ "\t// Absorption: straight down is the deep colour, a grazing look is the\n"
		+ "\t// shallow colour tinting the stock texture. That gradient is the look.\n"
		+ "\tvec3 body = mix(uDeepColour, texel * uShallowColour, smoothstep(0.0, 0.65, facing));\n"
		+ "\tvec3 sheen = gl_Fog.color.rgb;\n"
		+ "\tvec3 base = mix(body, sheen, fresnel * 0.85);\n"
		+ "\tfloat tight = pow(max(dot(normal, halfway), 0.0), uShininess);\n"
		+ "\tfloat wide = pow(max(dot(normal, halfway), 0.0), uShininess * 0.08);\n"
		+ "\t// Tilted away from world-up: the light catches the crests.\n"
		+ "\tfloat crest = clamp((1.0 - dot(normal, up)) * 6.0, 0.0, 1.0);\n"
		+ "\t// Sparkles: one hashed cell per quarter scene unit, stepped four times\n"
		+ "\t// a second, only where the sun already glints.\n"
		+ "\tvec2 cell = floor(vWorldPosition.xz * 4.0);\n"
		+ "\tfloat tick = floor(phase * 4.0);\n"
		+ "\tfloat sparkle = step(0.996, crestHash(cell, tick)) * tight;\n"
		+ "\tvec3 sun = gl_LightSource[0].diffuse.rgb;\n"
		+ "\tvec3 lit = base + body * sun * max(dot(normal, light), 0.0) * 0.30\n"
		+ "\t\t+ sun * (tight * 1.15 + wide * 0.22 + sparkle * 2.5)\n"
		+ "\t\t+ sun * crest * 0.05;\n"
		+ "\t// The stock path fades foam in from the per-vertex depth value.\n"
		+ "\tlit = mix(lit, vec3(1.0), clamp(vFoam, 0.0, 1.0) * 0.24);\n"
		+ "\tvec3 colour = clamp(lit, 0.0, 1.0);\n"
		+ "\tfloat fogFactor = clamp((gl_Fog.end - abs(vEyePosition.z)) * gl_Fog.scale, 0.0, 1.0);\n"
		+ "\tgl_FragColor = vec4(mix(gl_Fog.color.rgb, colour, fogFactor), gl_Color.a);\n"
		+ "}\n";

	private HdWaterSource() {
	}

	/** Every uniform the pipeline must be able to locate, in declaration order. */
	public static String[] uniforms() {
		return UNIFORMS.clone();
	}

	/** The reflective program's uniforms; the still program declares none of the extra ones. */
	public static String[] reflectiveUniforms() {
		return REFLECTIVE_UNIFORMS.clone();
	}

	/** The custom surface's uniforms: the calm set plus the two palette colours. */
	public static String[] customUniforms() {
		return CUSTOM_UNIFORMS.clone();
	}

	/** The breaking surface's uniforms: the cube plus the material's six numbers. */
	public static String[] breakingUniforms() {
		return BREAKING_UNIFORMS.clone();
	}
}
