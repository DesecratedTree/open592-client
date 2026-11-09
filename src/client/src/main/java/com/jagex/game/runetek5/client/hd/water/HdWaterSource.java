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
 * <p>There are four fragment programs, and every one of them writes an opaque
 * alpha: 634HD's water is a composed surface - body, sky, sun - not a
 * translucent overlay, and letting the vertex colour's alpha through is what
 * made the stock HD water read as see-through. {@link #CUSTOM_FRAGMENT} is the
 * row's third value: the same vertex stage, a surface with a third, diagonal
 * wave train, a sharper Fresnel, absorption from a deep to a shallow colour
 * instead of a flat darkening, sun sparkles that blink on the crests, and the
 * Caustics row's net moving over the surface - upstream's own {@code hdCaustics}
 * from {@code 634hd/shaders/hd_material.glsl}, transcribed number for number.
 * Its palette is uniforms rather than literals, so the look is data the
 * pipeline owns, and its default values are that data's WATER type
 * ({@code 634hd/data/materials.schema.json}: {@code depth} #00758e under
 * {@code surface} #69809c).
 *
 * <p>{@link #FRAGMENT} samples the environment cube map the toolkit already
 * binds for the stock cube effects ({@code GlToolkit.method1946()}) along the
 * reflection of the view about the perturbed normal, and falls back to the fog
 * band's colour when the frame has no cube ({@code uReflection} is 0). That
 * cube is the sky the water sits under, so a repainted sky repaints every water
 * surface, which is what 634HD's water does. {@link #REFLECTIVE_FRAGMENT} is the
 * water type the stock client called reflective: the same cube, with the
 * breaking band or the plain surface on top.
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

	/**
	 * {@code vec3}: the custom surface's colour looking straight down - 634HD's
	 * WATER type {@code depth} colour (#00758e), so deep water is that teal.
	 */
	public static final String UNIFORM_DEEP_COLOUR = "uDeepColour";

	/**
	 * {@code vec3}: the custom surface's colour at a grazing angle, tinting the
	 * texture - the same type's {@code surface} colour (#69809c).
	 */
	public static final String UNIFORM_SHALLOW_COLOUR = "uShallowColour";

	/**
	 * {@code samplerCube}: the environment the surface reflects. Every water
	 * fragment declares it: 634HD's water reflects the sky it sits under at every
	 * angle it is drawn, not only on the material type whose stock effect was
	 * called reflective.
	 */
	public static final String UNIFORM_ENVIRONMENT = "uEnvironment";

	/**
	 * {@code float}: 1 when a cube is bound for this draw, 0 otherwise. A
	 * samplerCube with nothing on its unit samples black, so the surface asks for
	 * the flag instead of guessing: with no cube it keeps the fog band's colour.
	 */
	public static final String UNIFORM_REFLECTION = "uReflection";

	/**
	 * {@code float}: the Caustics row as this surface's pattern strength, 0 when
	 * the row is off. The custom surface multiplies the upstream net into its
	 * lighting, the way 634HD lights the seabed with it.
	 */
	public static final String UNIFORM_CAUSTICS = "uCaustics";

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

	/** {@code sampler2D}: 634HD's ripple normal map, scrolled in three layers. */
	public static final String UNIFORM_WATER_NORMAL = "uWaterNormal";

	/** {@code sampler2D}: 634HD's shore foam mask. */
	public static final String UNIFORM_WATER_FOAM = "uWaterFoam";

	/**
	 * {@code float}: 1 when the two water maps are bound, 0 otherwise. Like
	 * {@code uReflection}, the surfaces ask for the flag instead of guessing: a
	 * sampler with nothing on its unit samples black, which would flatten the
	 * ripples instead of leaving the wave field alone.
	 */
	public static final String UNIFORM_WATER_MAPS = "uWaterMaps";

	/** {@code float}: the ripple strength, 634HD's WATER type {@code normalStrength}. */
	public static final String UNIFORM_NORMAL_STRENGTH = "uNormalStrength";

	/**
	 * {@code sampler2D}: 634HD's flow map, which bends the ripples and drifts
	 * the shore foam along the current. Bound with the other two maps, on
	 * {@link #WATER_FLOW_UNIT}.
	 */
	public static final String UNIFORM_WATER_FLOW = "uWaterFlow";

	/**
	 * {@code vec3}: the water type's specular strength, Fresnel share and least
	 * opacity, in that order - 634HD's {@code hdWaterSpec[type].x}, its {@code w}
	 * (the Fresnel) and the surface colour's alpha, handed over per material.
	 */
	public static final String UNIFORM_WATER_SPEC = "uWaterSpec";

	/**
	 * {@code vec3}: the type's {@code hasFoam}, animation period and whether it is
	 * plain water (WATER's Fresnel amount, which upstream darkens by a quarter).
	 */
	public static final String UNIFORM_WATER_SHAPE = "uWaterShape";

	/**
	 * {@code vec3}: the area's water - 634HD's {@code hdWaterParams}: x the
	 * ripples' height, y the sun's glint, z the ripples' tiling (1 each by
	 * default), each scaling a field of the type.
	 */
	public static final String UNIFORM_WATER_PARAMS = "uWaterParams";

	/**
	 * {@code vec2}: the tiling on show before the area changed, and the blend
	 * to the current one (1 when settled) - 634HD's {@code hdWaterClock.y} and
	 * {@code z}.
	 */
	public static final String UNIFORM_WATER_BLEND = "uWaterBlend";

	/**
	 * {@code vec4}: the area's water tint (sRGB) and how much of the type's
	 * colour it replaces - 634HD's {@code hdWaterTint}.
	 */
	public static final String UNIFORM_WATER_TINT = "uWaterTint";

	/** {@code vec3}: the type's shore foam colour ({@code foam} in the content). */
	public static final String UNIFORM_FOAM_COLOUR = "uFoamColour";

	/**
	 * {@code int}: the frame's live point lights, counted the way the lighting
	 * program counts them. Their state is fixed function's {@code gl_LightSource}
	 * rows, so no light data rides with this uniform.
	 */
	public static final String UNIFORM_POINT_LIGHTS = "uPointLightCount";

	/**
	 * {@code float}: the stock per-tile water depth (0..1) on the terrain-format
	 * vertex stage. A ground layer has no texture coordinate z to carry it: its
	 * texcoords are scene units for the texture matrix, and the depth arrives as
	 * the draw's own byte.
	 */
	public static final String UNIFORM_GROUND_DEPTH = "uGroundDepth";

	/**
	 * The unit the ripple normal map is bound to. Above every unit another
	 * pass names: the world pass owns 3 to 5 and the material maps 6 to 9.
	 *
	 * <p>Water used to sit on 6 and 7, which are the material normal and ORM
	 * units. A water layer also has a material, so the layer's own map bind
	 * landed on the units the surface was about to sample: the ripples read
	 * the terrain's normal map, and the terrain's maps were replaced by the
	 * water art on the next region. Both are visible as grain and as art that
	 * stops appearing, which is why these live above the material range.
	 */
	public static final int WATER_NORMAL_UNIT = 10;

	/** The unit the shore foam mask is bound to, beside the normal map. */
	public static final int WATER_FOAM_UNIT = 11;

	/**
	 * The unit the flow map is bound to, beside the other two. The current's
	 * velocity is a third sampler, not a uniform: 634HD fades it with the
	 * atmosphere's transition and this port bakes the map's art instead.
	 */
	public static final int WATER_FLOW_UNIT = 12;

	/**
	 * The unit the environment cube is bound to. Unit 1 is where the stock cube
	 * effects put it ({@code ReflectiveRipplingWaterEffect.enable}), so the HD
	 * reflection cannot fight the effect manager for a unit.
	 */
	public static final int ENVIRONMENT_UNIT = 1;

	private static final String[] UNIFORMS = {
		UNIFORM_TEXTURE, UNIFORM_ENVIRONMENT, UNIFORM_REFLECTION, UNIFORM_TIME, UNIFORM_FLOW,
		UNIFORM_SHININESS, UNIFORM_WATER_NORMAL, UNIFORM_WATER_FOAM, UNIFORM_WATER_MAPS,
		UNIFORM_NORMAL_STRENGTH, UNIFORM_DEEP_COLOUR, UNIFORM_SHALLOW_COLOUR, UNIFORM_CAUSTICS,
		UNIFORM_WATER_SPEC, UNIFORM_WATER_SHAPE, UNIFORM_FOAM_COLOUR, UNIFORM_POINT_LIGHTS,
		UNIFORM_WATER_FLOW, UNIFORM_WATER_PARAMS, UNIFORM_WATER_BLEND, UNIFORM_WATER_TINT
	};

	private static final String[] REFLECTIVE_UNIFORMS = {
		UNIFORM_TEXTURE, UNIFORM_ENVIRONMENT, UNIFORM_TIME, UNIFORM_FLOW, UNIFORM_SHININESS,
		UNIFORM_WATER_NORMAL, UNIFORM_WATER_FOAM, UNIFORM_WATER_MAPS, UNIFORM_NORMAL_STRENGTH,
		UNIFORM_DEEP_COLOUR, UNIFORM_SHALLOW_COLOUR, UNIFORM_CAUSTICS, UNIFORM_WATER_SPEC,
		UNIFORM_WATER_SHAPE, UNIFORM_FOAM_COLOUR, UNIFORM_POINT_LIGHTS, UNIFORM_WATER_FLOW, UNIFORM_WATER_PARAMS, UNIFORM_WATER_BLEND, UNIFORM_WATER_TINT
	};

	private static final String[] CUSTOM_UNIFORMS = {
		UNIFORM_TEXTURE, UNIFORM_ENVIRONMENT, UNIFORM_REFLECTION, UNIFORM_CAUSTICS, UNIFORM_TIME,
		UNIFORM_FLOW, UNIFORM_SHININESS, UNIFORM_DEEP_COLOUR, UNIFORM_SHALLOW_COLOUR,
		UNIFORM_WATER_NORMAL, UNIFORM_WATER_FOAM, UNIFORM_WATER_MAPS, UNIFORM_NORMAL_STRENGTH,
		UNIFORM_WATER_SPEC, UNIFORM_WATER_SHAPE, UNIFORM_FOAM_COLOUR, UNIFORM_POINT_LIGHTS,
		UNIFORM_WATER_FLOW, UNIFORM_WATER_PARAMS, UNIFORM_WATER_BLEND, UNIFORM_WATER_TINT
	};

	private static final String[] BREAKING_UNIFORMS = {
		UNIFORM_TEXTURE, UNIFORM_ENVIRONMENT, UNIFORM_TIME, UNIFORM_FLOW, UNIFORM_SHININESS,
		UNIFORM_BREAK_DEPTH, UNIFORM_BREAK_OFFSET, UNIFORM_WAVE_INTENSITY, UNIFORM_WAVE_EXPONENT,
		UNIFORM_BREAK_SCALE, UNIFORM_BREAK_RATE, UNIFORM_WATER_NORMAL, UNIFORM_WATER_FOAM,
		UNIFORM_WATER_MAPS, UNIFORM_NORMAL_STRENGTH, UNIFORM_DEEP_COLOUR, UNIFORM_SHALLOW_COLOUR,
		UNIFORM_CAUSTICS, UNIFORM_WATER_SPEC, UNIFORM_WATER_SHAPE, UNIFORM_FOAM_COLOUR,
		UNIFORM_POINT_LIGHTS, UNIFORM_WATER_FLOW, UNIFORM_WATER_PARAMS, UNIFORM_WATER_BLEND, UNIFORM_WATER_TINT
	};

	/**
	 * The declarations and functions 634HD's water texture needs, shared by all
	 * four fragments so the maps cannot mean different things per surface.
	 *
	 * <p>{@code hdWaterLayers} upstream scrolls the normal map in three layers at
	 * 3, 5 and 7 tiles a repeat, each through a 30, 24 and 20 second period, and
	 * this is that rule on a 2D map: the volume's third channel is the up axis
	 * here, so the two the map has are the tangent tilt.
	 *
	 * <p>{@code hdShoreFoam} is 634HD's foam map faded in over the stock
	 * per-vertex depth; upstream's foam depth is 192 scene units.
	 */
	private static final String WATER_TEXTURE_GLSL =
		"uniform sampler2D uWaterNormal;\n"
		+ "uniform sampler2D uWaterFoam;\n"
		+ "uniform sampler2D uWaterFlow;\n"
		+ "uniform float uWaterMaps;\n"
		+ "uniform float uNormalStrength;\n"
		+ "uniform vec3 uDeepColour;\n"
		+ "uniform vec3 uShallowColour;\n"
		+ "uniform float uCaustics;\n"
		+ "\n"
		+ "// ---- 634HD's water types (hd_material.glsl, 634hd/data/materials.schema.json):\r\n"
		+ "// a material's water block is the type, and the type's own fields ride in\r\n"
		+ "// the uniforms below, so a swamp's water and a lake's are one program with\r\n"
		+ "// different numbers. The depth, foam, mirror and bend constants are\r\n"
		+ "// upstream's and the same for every type. ----\n"
		+ "#define HD_WATER_DEPTH_RANGE 1536.0\n"
		+ "#define HD_WATER_FOAM_DEPTH 192.0\n"
		+ "#define HD_WATER_DEPTH_OPACITY 0.9\n"
		+ "#define HD_WATER_REFLECTION_BEND 0.3\n"
		+ "#define HD_WATER_MIRROR_SHARE 0.5\n"
		+ "// specular strength, Fresnel share, least opacity - hdWaterSpec.xw and the\n"
		+ "// surface colour's alpha in upstream's tables.\n"
		+ "uniform vec3 uWaterSpec;\n"
		+ "// hasFoam, animation period, plain (WATER's Fresnel amount, the quarter\n"
		+ "// darkening).\n"
		+ "uniform vec3 uWaterShape;\n"
		+ "uniform vec3 uFoamColour;\n"
		+ "// The frame's live point lights, counted the way the lighting program counts\r\n"
		+ "// them: gl_LightSource[2 .. 5], uploaded by the stock light copy.\n"
		+ "uniform int uPointLightCount;\n"
		+ "// The area's water (hdWaterParams): the ripples' height, the sun's\n"
		+ "// glint and the ripples' tiling, each scaling a field of the type.\n"
		+ "uniform vec3 uWaterParams;\n"
		+ "// The tiling on show before the area changed, and the blend to the\n"
		+ "// new one (1 when settled): hdWaterClock.y and z.\n"
		+ "uniform vec2 uWaterBlend;\n"
		+ "// The area's water tint (sRGB) and how much of the type's colour it\n"
		+ "// replaces: hdWaterTint.\n"
		+ "uniform vec4 uWaterTint;\n"
		+ "\n"
		+ "// 634HD's world uv (hdWaterWorldUv): a repeat every [tiles] tiles (512\n"
		+ "// units) of the world position, negative so growing x or z walks the map\n"
		+ "// backwards, as upstream's does.\n"
		+ "vec2 hdWaterWorldUv634(vec2 xz, float tiles, float size) {\n"
		+ "\treturn -xz / (512.0 * tiles * max(size, 0.25));\n"
		+ "}\n"
		+ "\n"
		+ "// The animation frame (hdWaterFrame): the phase of a slide that takes\n"
		+ "// [period] seconds of water time; 0 for a period of none.\n"
		+ "float hdWaterFrame634(float seconds, float period) {\n"
		+ "\treturn period > 0.0 ? fract(seconds / period) : 0.0;\n"
		+ "}\n"
		+ "\n"
		+ "// 634HD's flow map (hdWaterLayers): the current's velocity (rg) at world\n"
		+ "// [xz], sampled every 15 tiles through a frame 50 seconds long, both\n"
		+ "// scaled by the type's animation period. The frame is one float added to\n"
		+ "// both uv axes, so the current itself drifts diagonally, and the ripples\n"
		+ "// and the foam are bent by it and travel with it. Zero without the maps,\n"
		+ "// which leaves the shipped wave field's own motion.\n"
		+ "vec2 hdWaterFlow634(vec2 xz, float seconds, float size) {\n"
		+ "\tif (uWaterMaps < 0.5) {\n"
		+ "\t\treturn vec2(0.0);\n"
		+ "\t}\n"
		+ "\tvec2 uv = hdWaterWorldUv634(xz, 15.0, size) + hdWaterFrame634(seconds, 50.0 * uWaterShape.y);\n"
		+ "\treturn texture2D(uWaterFlow, uv).xy;\n"
		+ "}\n"
		+ "\n"
		+ "// 634HD's water normal map (hdWaterLayers): three scrolled layers at 3, 5\n"
		+ "// and 7 tiles a repeat, each its own frame of a 30, 24 and 20 second\n"
		+ "// period. They are not three copies of one slide: upstream builds the\n"
		+ "// 3-tile layer's uv with its axes swapped and scrolls it *against* the\n"
		+ "// clock, and the 5 and 7 tile layers with it, so neighbouring layers\n"
		+ "// travel in different directions and their sum drifts rather than\n"
		+ "// sliding as one sheet. Sampled in world space, so the ripples stay put\n"
		+ "// as the camera moves.\n"
		+ "vec2 hdWaterLayers634(vec2 xz, float seconds, float size) {\n"
		+ "	float duration = uWaterShape.y;\n"
		+ "	// The type's strength scaled by the area's waves (hdWaterSpec.z * hdWaterParams.x).\n"
		+ "	float strength = uNormalStrength * uWaterParams.x;\n"
		+ "	// The current's own bend, upstream's 0.025 of the flow map's velocity.\n"
		+ "	vec2 flow = hdWaterFlow634(xz, seconds, size) * 0.025;\n"
		+ "	vec2 uv1 = hdWaterWorldUv634(xz, 3.0, size).yx - hdWaterFrame634(seconds, 30.0 * duration) + flow;\n"
		+ "	vec2 uv2 = hdWaterWorldUv634(xz, 5.0, size) + hdWaterFrame634(seconds, 24.0 * duration) + flow;\n"
		+ "	vec2 uv3 = hdWaterWorldUv634(xz, 7.0, size) + hdWaterFrame634(seconds, 20.0 * duration) + flow;\n"
		+ "	vec2 sum = (texture2D(uWaterNormal, uv1).rg * 2.0 - 1.0)\n"
		+ "		+ (texture2D(uWaterNormal, uv2).rg * 2.0 - 1.0)\n"
		+ "		+ (texture2D(uWaterNormal, uv3).rg * 2.0 - 1.0);\n"
		+ "	// Upstream's n per layer is -((r) * strength, b, (g) * strength): the map's\n"
		+ "	// red and green are the two tilts, negated.\n"
		+ "	return -sum * strength;\n"
		+ "}\n"
		+ "\n"
		+ "// The surface's tilt (hdWaterNormal): the layers at the area's tiling,\n"
		+ "// crossfaded from the tiling on show before the area changed while that\n"
		+ "// transition runs. Blending the two layer sums rather than rescaling the\n"
		+ "// world-space pattern keeps a new tiling from sweeping hundreds of repeats\n"
		+ "// across the surface in the frame it arrives.\n"
		+ "vec2 hdWaterRipples(vec2 xz, float seconds) {\n"
		+ "	if (uWaterMaps < 0.5) {\n"
		+ "		return vec2(0.0);\n"
		+ "	}\n"
		+ "	vec2 tilt = hdWaterLayers634(xz, seconds, uWaterParams.z);\n"
		+ "	if (uWaterBlend.y < 1.0) {\n"
		+ "		tilt = mix(hdWaterLayers634(xz, seconds, uWaterBlend.x), tilt, uWaterBlend.y);\n"
		+ "	}\n"
		+ "	return tilt;\n"
		+ "}\n"
		+ "\n"
		+ "// The foam map's texel at world [xz], drifting with the current the way\n"
		+ "// the ripples do (upstream: fract(world / 512) + flow * 0.025), 1 when\n"
		+ "// the maps are absent.\n"
		+ "float hdWaterFoamTexel(vec2 xz, float seconds) {\n"
		+ "\tif (uWaterMaps < 0.5) {\n"
		+ "\t\treturn 1.0;\n"
		+ "\t}\n"
		+ "\tvec2 flow = hdWaterFlow634(xz, seconds, uWaterParams.z) * 0.025;\n"
		+ "\treturn texture2D(uWaterFoam, fract(xz / 512.0) + flow).r;\n"
		+ "}\n"
		+ "\n"
		+ "// The shore foam mask, faded in over the stock per-vertex depth; 634HD's\n"
		+ "// foam depth is 192 scene units.\n"
		+ "float hdShoreFoam(vec2 xz, float depth, float seconds) {\n"
		+ "\tfloat mask = hdWaterFoamTexel(xz, seconds);\n"
		+ "\treturn clamp(mask * (1.0 - clamp(depth / 192.0, 0.0, 1.0)) * 1.6, 0.0, 1.0);\n"
		+ "}\n"
		+ "\n"
		+ "// 634HD's hdCaustics: the bright web a rippling surface focuses, two\n"
		+ "// rotated warped wave layers sharpened into thin lines, 0 to about 1.\n"
		+ "// Transcribed number for number from hd_material.glsl, and shared by every\n"
		+ "// surface so the Caustics row means one thing on water and seabed alike.\n"
		+ "float hdCaustics(vec2 p, float t) {\n"
		+ "\tvec2 q = p * 0.0042;\n"
		+ "\tfloat total = 0.0;\n"
		+ "\tfor (int i = 0; i < 2; i++) {\n"
		+ "\t\tfloat s = float(i) * 1.37 + 1.0;\n"
		+ "\t\tfloat angle = float(i) * 0.65;\n"
		+ "\t\tvec2 r = mat2(cos(angle), -sin(angle), sin(angle), cos(angle)) * q * s;\n"
		+ "\t\tvec2 w = r + vec2(sin(r.y * 1.3 + t * 0.7 * s), cos(r.x * 1.1 - t * 0.6 * s)) * 1.1;\n"
		+ "\t\tw += vec2(sin(w.y * 2.7 + t * 0.4), cos(w.x * 2.3 - t * 0.5)) * 0.35;\n"
		+ "\t\tfloat a = sin(w.x * 2.1 + t * 0.9) * sin(w.y * 2.4 - t * 0.8);\n"
		+ "\t\ttotal += pow(1.0 - abs(a), 7.0);\n"
		+ "\t}\n"
		+ "\treturn total * 0.5;\n"
		+ "}\n"
		+ "\n"

		+ "// 634HD's Fresnel mapping: not a power of the cosine but a straight mix\n"
		+ "// from 0.4 to 1.0 over the sine of the angle, so the reflection arrives\n"
		+ "// sooner at a grazing look than a cubic does.\n"
		+ "float hdWaterFresnel634(float facing) {\n"
		+ "\treturn clamp(mix(0.4, 1.0, (1.0 - clamp(facing, 0.0, 1.0)) * 1.2), 0.0, 1.0);\n"
		+ "}\n"
		+ "\n"
		+ "// What the surface reflects on its own: a gradient of the area's water\n"
		+ "// tint, normalised to its own brightest channel, at 0.05, 0.45 and 0.8 by\n"
		+ "// the Fresnel - upstream's three stops in hdWaterReflection.\n"
		+ "vec3 hdWaterTint634(vec3 tint, float finalFresnel) {\n"
		+ "\tvec3 t = tint / max(max(max(tint.r, tint.g), max(tint.b, 0.001)), 0.001);\n"
		+ "\treturn finalFresnel < 0.5\n"
		+ "\t\t? mix(t * 0.05, t * 0.45, finalFresnel * 2.0)\n"
		+ "\t\t: mix(t * 0.45, t * 0.8, (finalFresnel - 0.5) * 2.0);\n"
		+ "}\n"
		+ "\n"
		+ "// The sun's glint at the type's strength scaled by the area's glint:\n"
		+ "// upstream's hdWaterSpec.x * hdWaterParams.z.\n"
		+ "float hdWaterSpecular634() {\n"
		+ "	return uWaterSpec.x * uWaterParams.y;\n"
		+ "}\n"
		+ "\n"
		+ "// The area's tint for the reflected gradient (hdWaterTint.rgb): with no\n"
		+ "// area stating one, 592HD's own type colour keeps the gradient it had.\n"
		+ "vec3 hdWaterAreaTint634() {\n"
		+ "\treturn uWaterTint.w < 0.0 ? uShallowColour : uWaterTint.rgb;\n"
		+ "}\n"
		+ "\n"
		+ "// The body's tint (hdWaterTint): the type's colour pulled towards the\n"
		+ "// area's tint by its strength. The absent area (strength < 0) keeps the\n"
		+ "// type's own body, half-way to its depth colour, bit for bit.\n"
		+ "vec3 hdWaterAreaBody634(vec3 typeColour, float typeLuma) {\n"
		+ "\tif (uWaterTint.w < 0.0) {\n"
		+ "\t\tfloat deepLuma = max(dot(uDeepColour, vec3(0.299, 0.587, 0.114)), 0.001);\n"
		+ "\t\treturn mix(typeColour, uDeepColour * (typeLuma / deepLuma), 0.5);\n"
		+ "\t}\n"
		+ "\tfloat tintLuma = max(dot(uWaterTint.rgb, vec3(0.299, 0.587, 0.114)), 0.001);\n"
		+ "\treturn mix(typeColour, uWaterTint.rgb * (typeLuma / tintLuma), uWaterTint.w);\n"
		+ "}\n"
		+ "\n"
		+ "// The surface's own light, before anything it reflects: the frame's\n"
		+ "// ambient, the sun's diffuse on the ripples, the view-based glint at the\n"
		+ "// type's specular strength, and the sky's light under the fog band on\n"
		+ "// [up], the world's up in eye space. 634HD reads its ambient and sun from\n"
		+ "// the environment block; this frame's are gl_LightModel.ambient and the\n"
		+ "// stock sun row, which is the same light the scene is drawn with.\n"
		+ "vec3 hdWaterLight634(vec3 normal, vec3 view, vec3 up) {\n"
		+ "\tvec3 light = normalize(gl_LightSource[0].position.xyz);\n"
		+ "\tvec3 sun = gl_LightSource[0].diffuse.rgb;\n"
		+ "\tvec3 glint = sun * pow(clamp(dot(view, reflect(-light, normal)), 1.0e-10, 1.0),\n"
		+ "\t\tuShininess) * hdWaterSpecular634();\n"
		+ "\treturn gl_LightModel.ambient.rgb + sun * max(dot(normal, light), 0.0) + glint\n"
		+ "\t\t+ gl_Fog.color.rgb * max(dot(normal, up), 0.0) * 0.5;\n"
		+ "}\n"
		+ "\n"
		+ "// 634HD's point lights on the water: each live light's diffuse, and its\n"
		+ "// glint mirrored about the surface at the type's gloss and strength. The\n"
		+ "// frame's lights are gl_LightSource[2 .. 5] with the stock attenuation,\n"
		+ "// exactly where the lighting program reads the same lights; [eye] is the\n"
		+ "// fragment in eye space, the space this surface's normal and view live in.\n"
		+ "vec3 hdWaterPoints634(vec3 eye, vec3 normal, vec3 view) {\n"
		+ "\tvec3 sum = vec3(0.0);\n"
		+ "\tfor (int i = 2; i < 6; i++) {\n"
		+ "\t\tif (i - 2 >= uPointLightCount) {\n"
		+ "\t\t\tbreak;\n"
		+ "\t\t}\n"
		+ "\t\tvec3 toLight = gl_LightSource[i].position.xyz - eye;\n"
		+ "\t\tfloat distanceSquared = max(dot(toLight, toLight), 1.0e-8);\n"
		+ "\t\tfloat distance = sqrt(distanceSquared);\n"
		+ "\t\tvec3 direction = toLight / distance;\n"
		+ "\t\tfloat attenuation = 1.0 / (gl_LightSource[i].constantAttenuation\n"
		+ "\t\t\t+ gl_LightSource[i].linearAttenuation * distance\n"
		+ "\t\t\t+ gl_LightSource[i].quadraticAttenuation * distanceSquared);\n"
		+ "\t\tvec3 colour = gl_LightSource[i].diffuse.rgb * attenuation;\n"
		+ "\t\tsum += colour * max(dot(normal, direction), 0.0);\n"
		+ "\t\tsum += colour * pow(max(dot(view, reflect(-direction, normal)), 0.0),\n"
		+ "\t\t\tuShininess) * hdWaterSpecular634();\n"
		+ "\t}\n"
		+ "\treturn sum;\n"
		+ "}\n"
		+ "\n"
		+ "// 634HD's surface: the type's colour pulled towards the area's tint, lit\n"
		+ "// by hdWaterLight634, mixed with what it [reflection] reflects at the\n"
		+ "// type's Fresnel share, the plain water darkened by a quarter, the shore\n"
		+ "// foam in the type's colour, and the depth's absorption and opacity.\n"
		+ "// [depth] is the stock per-vertex water depth in scene units, as\n"
		+ "// upstream's vertex depth is; [foamMask] is the shore foam map's texel,\n"
		+ "// 1 without the maps.\n"
		+ "vec4 hdWaterBody634(vec3 eye, vec3 normal, vec3 view, vec3 up, float depth,\n"
		+ "\t\tfloat foamMask, vec3 reflection) {\n"
		+ "\tfloat facing = clamp(dot(normal, view), 0.0, 1.0);\n"
		+ "\tfloat finalFresnel = hdWaterFresnel634(facing);\n"
		+ "\t// The sun, the sky and the frame's point lights: a torch beside a lake\n"
		+ "\t// has to glint on it, as it does upstream.\n"
		+ "\tvec3 composite = hdWaterLight634(normal, view, up)\n"
		+ "\t\t+ hdWaterPoints634(eye, normal, view)\n"
		+ "\t\t// what the surface reflects is light on it too: upstream's\n"
		+ "\t\t// reflection * max(specStrength, 0.2) term\n"
		+ "\t\t+ reflection * max(hdWaterSpecular634(), 0.2);\n"
		+ "\t// The type's colour, its hue pulled towards the area's tint at the\n"
		+ "\t// type's own brightness: upstream's hdWaterTint share.\n"
		+ "\tvec3 typeColour = uShallowColour;\n"
		+ "\tfloat typeLuma = dot(typeColour, vec3(0.299, 0.587, 0.114));\n"
		+ "\tvec3 surfaceSrgb = hdWaterAreaBody634(typeColour, typeLuma);\n"
		+ "\tvec3 base = mix(surfaceSrgb * composite, reflection, uWaterSpec.y);\n"
		+ "\t// plain water - the types sharing WATER's Fresnel amount - is a\n"
		+ "\t// quarter darker, which is what keeps the shallows off a pale sheet.\n"
		+ "\tif (uWaterShape.z > 0.5) {\n"
		+ "\t\tbase *= 0.75;\n"
		+ "\t}\n"
		+ "\tfloat shore = 1.0 - clamp(depth / HD_WATER_FOAM_DEPTH, 0.0, 1.0);\n"
		+ "\tvec3 foamColour = uFoamColour * foamMask * composite;\n"
		+ "\tfloat foamAmount = clamp(pow(max(1.0 - (1.0 - min(shore, 0.8)) / 0.7, 0.0), 3.0),\n"
		+ "\t\t0.0, 1.0) * clamp(foamColour.r, 0.0, 1.0) * uWaterShape.x;\n"
		+ "\tbase = mix(base, foamColour, foamAmount);\n"
		+ "\t// 634HD raises the Fresnel by how flat the view is on the surface: a\n"
		+ "\t// surface seen edge-on mirrors the sky whatever its ripples do.\n"
		+ "\tfinalFresnel = max(finalFresnel, 1.0 - clamp(dot(view, up), 0.0, 1.0));\n"
		+ "\tfloat alpha = max(uWaterSpec.z, max(foamAmount, finalFresnel));\n"
		+ "\talpha = max(alpha, HD_WATER_DEPTH_OPACITY\n"
		+ "\t\t* smoothstep(0.3, 1.0, clamp(depth / HD_WATER_DEPTH_RANGE, 0.0, 1.0)));\n"
		+ "\t// the type's depth colour laid under the surface by that opacity: the\n"
		+ "\t// depth's absorption instead of a flat darkening of the same water.\n"
		+ "\treturn vec4(clamp(mix(uDeepColour, base, alpha), 0.0, 1.0), alpha);\n"
		+ "}\n"
		+ "\n";

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

	/**
	 * The terrain-format vertex stage: the same surface, reading the ground pass'
	 * fixed-function arrays.
	 *
	 * <p>{@link #VERTEX} is already written against {@code gl_Vertex},
	 * {@code gl_Normal}, {@code gl_MultiTexCoord0} and {@code gl_Color} - the
	 * arrays {@code GlGroundLayer} submits through {@code method1934} - so the
	 * ripples, the scroll and the scene-space perturbation carry over unchanged.
	 * The one difference is the foam/depth term: a model water surface carries it
	 * in the texture coordinate's z, while a terrain layer carries it as the stock
	 * depth byte the layer was built with, which becomes {@link #UNIFORM_GROUND_DEPTH}.
	 */
	public static final String GROUND_VERTEX = "#version " + GLSL_VERSION + "\n"
		+ "\n"
		+ "uniform float uGroundDepth;\n"
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
		+ "\tvWorldPosition = gl_Vertex.xyz;\n"
		+ "\tvWorldNormal = gl_Normal;\n"
		+ "\tvNormalX = gl_NormalMatrix * vec3(1.0, 0.0, 0.0);\n"
		+ "\tvNormalY = gl_NormalMatrix * vec3(0.0, 1.0, 0.0);\n"
		+ "\tvNormalZ = gl_NormalMatrix * vec3(0.0, 0.0, 1.0);\n"
		+ "\t// The stock water path scrolls with the texture matrix; keep that scroll.\n"
		+ "\tvTexCoord = (gl_TextureMatrix[0] * gl_MultiTexCoord0).xy;\n"
		+ "\t// The ground path hands the stock depth byte over 255; the surface\n"
		+ "\t// reads scene units (634HD's 1536-unit range), so a tile's water is\n"
		+ "\t// scaled back to them here rather than every fragment rescaling it.\n"
		+ "\tvFoam = uGroundDepth * 1536.0;\n"
		+ "\tgl_FrontColor = gl_Color;\n"
		+ "\tgl_BackColor = gl_Color;\n"
		+ "}\n";

	public static final String FRAGMENT = "#version " + GLSL_VERSION + "\n"
		+ "\n"
		+ "uniform sampler2D uTexture;\n"
		+ "uniform samplerCube uEnvironment;\n"
		+ "uniform float uReflection;\n"
		+ "uniform float uTime;\n"
		+ "uniform float uFlow;\n"
		+ "uniform float uShininess;\n"
		+ WATER_TEXTURE_GLSL
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
		+ "\t// 634HD water texture: the shipped normal map, three scrolled layers,\n"
		+ "\t// scaled by the row ripple strength. With no maps bound the surface\n"
		+ "\t// keeps the wave field above, which is the draw this port shipped.\n"
		+ "\tif (uWaterMaps > 0.5) {\n"
		+ "\t\tsurface.xz += hdWaterRipples(vWorldPosition.xz, phase);\n"
		+ "\t\tsurface = normalize(surface);\n"
		+ "\t}\n"
		+ "\tvec3 normal = normalize(vNormalX * surface.x\n"
		+ "\t\t+ vNormalY * surface.y + vNormalZ * surface.z);\n"
		+ "\tvec3 view = normalize(-vEyePosition);\n"
		+ "\tfloat facing = clamp(dot(normal, view), 0.0, 1.0);\n"
		+ "\tfloat finalFresnel = hdWaterFresnel634(facing);\n"
		+ "\t// 634HD's reflection: the area's tint as a gradient by the Fresnel, with\n"
		+ "\t// the sky the cube holds mixed over it at the mirror share - a pane of\n"
		+ "\t// glass with the trees in it is what a full mirror reads as - and bent\n"
		+ "\t// by the normal's lean, as upstream bends the mirrored scene map.\n"
		+ "\tvec3 bent = reflect(view, normal);\n"
		+ "\tbent.xz += normal.xz * HD_WATER_REFLECTION_BEND;\n"
		+ "\tvec3 reflected = textureCube(uEnvironment, bent).rgb;\n"
		+ "\tvec3 mirror = mix(gl_Fog.color.rgb, reflected, clamp(uReflection, 0.0, 1.0));\n"
		+ "\tvec3 reflection = mix(hdWaterTint634(hdWaterAreaTint634(), finalFresnel),\n"
		+ "\t\tmirror * finalFresnel,\n"
		+ "\t\tHD_WATER_MIRROR_SHARE * clamp(uReflection, 0.0, 1.0));\n"
		+ "\t// The shore foam mask, on the world coordinates upstream samples.\n"
		+ "\tfloat foamMask = hdWaterFoamTexel(vWorldPosition.xz, phase);\n"
		+ "\t// 634HD's surface: the type's colour, tint and depth absorption, which\n"
		+ "\t// is the body the stock material's white texture used to stand in for.\n"
		+ "\tvec3 lit = hdWaterBody634(vEyePosition, normal, view, normalize(vNormalY), vFoam, foamMask, reflection).rgb;\n"
		+ "\t// The Caustics row's net over the surface, the same field the seabed reads:\n"
		+ "\t// a share of the light already there where the web is bright.\n"
		+ "\t// 634HD's surface paints no caustic net: the net is the seabed's, lit\n"
		+ "\t// by the terrain pass under this water. On the surface it read as a net\n"
		+ "\t// over a flat sheet, which is what the stock water texture did.\n"
		+ "\tvec3 colour = clamp(lit, 0.0, 1.0);\n"
		+ "\tfloat fogFactor = clamp((gl_Fog.end - abs(vEyePosition.z)) * gl_Fog.scale, 0.0, 1.0);\n"
		+ "\t// 634HD's water is a composed surface, not a translucent overlay, so\n"
		+ "\t// the vertex colour's alpha must not thin it: terrain showing through\n"
		+ "\t// the waves is the see-through water this row exists to fix.\n"
		+ "\tgl_FragColor = vec4(mix(gl_Fog.color.rgb, colour, fogFactor), 1.0);\n"
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
		+ WATER_TEXTURE_GLSL
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
		+ "\t// 634HD water texture: the shipped normal map, three scrolled layers,\n"
		+ "\t// scaled by the row ripple strength. With no maps bound the surface\n"
		+ "\t// keeps the wave field above, which is the draw this port shipped.\n"
		+ "\tif (uWaterMaps > 0.5) {\n"
		+ "\t\tsurface.xz += hdWaterRipples(vWorldPosition.xz, phase);\n"
		+ "\t\tsurface = normalize(surface);\n"
		+ "\t}\n"
		+ "\tvec3 normal = normalize(vNormalX * surface.x\n"
		+ "\t\t+ vNormalY * surface.y + vNormalZ * surface.z);\n"
		+ "\tvec3 view = normalize(-vEyePosition);\n"
		+ "\tfloat facing = clamp(dot(normal, view), 0.0, 1.0);\n"
		+ "\tfloat finalFresnel = hdWaterFresnel634(facing);\n"
		+ "\t// The reflective type is the one whose sky is the cube outright: the\n"
		+ "\t// whole reflection is the scene's own, bent by the normal's lean.\n"
		+ "\tvec3 bent = reflect(view, normal);\n"
		+ "\tbent.xz += normal.xz * HD_WATER_REFLECTION_BEND;\n"
		+ "\tvec3 environment = textureCube(uEnvironment, bent).rgb;\n"
		+ "\tvec3 reflection = environment * finalFresnel;\n"
		+ "\tfloat foamMask = hdWaterFoamTexel(vWorldPosition.xz, phase);\n"
		+ "\tvec3 lit = hdWaterBody634(vEyePosition, normal, view, normalize(vNormalY), vFoam, foamMask, reflection).rgb;\n"
		+ "\t// 634HD's surface paints no caustic net: the net is the seabed's, lit\n"
		+ "\t// by the terrain pass under this water. On the surface it read as a net\n"
		+ "\t// over a flat sheet, which is what the stock water texture did.\n"
		+ "\tvec3 colour = clamp(lit, 0.0, 1.0);\n"
		+ "\tfloat fogFactor = clamp((gl_Fog.end - abs(vEyePosition.z)) * gl_Fog.scale, 0.0, 1.0);\n"
		+ "\t// 634HD's water is a composed surface, not a translucent overlay, so\n"
		+ "\t// the vertex colour's alpha must not thin it: terrain showing through\n"
		+ "\t// the waves is the see-through water this row exists to fix.\n"
		+ "\tgl_FragColor = vec4(mix(gl_Fog.color.rgb, colour, fogFactor), 1.0);\n"
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
		+ WATER_TEXTURE_GLSL
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
		+ "\t// 634HD water texture: the shipped normal map, three scrolled layers,\n"
		+ "\t// scaled by the row ripple strength. With no maps bound the surface\n"
		+ "\t// keeps the wave field above, which is the draw this port shipped.\n"
		+ "\tif (uWaterMaps > 0.5) {\n"
		+ "\t\tsurface.xz += hdWaterRipples(vWorldPosition.xz, phase);\n"
		+ "\t\tsurface = normalize(surface);\n"
		+ "\t}\n"
		+ "\tvec3 normal = normalize(vNormalX * surface.x\n"
		+ "\t\t+ vNormalY * surface.y + vNormalZ * surface.z);\n"
		+ "\tvec3 up = normalize(vNormalY);\n"
		+ "\tvec3 view = normalize(-vEyePosition);\n"
		+ "\tvec3 light = normalize(gl_LightSource[0].position.xyz);\n"
		+ "\tvec3 halfway = normalize(light + view);\n"
		+ "\tfloat fresnel = pow(1.0 - clamp(dot(normal, view), 0.0, 1.0), 3.0);\n"
		+ "\t// The material's ripple scale on the stock texture coordinates, the same\n"
		+ "\t// multiplication the stock breaking vertex stage performs.\n"
		+ "\tfloat facing = clamp(dot(normal, view), 0.0, 1.0);\n"
		+ "\tvec3 texel = texture2D(uTexture, vTexCoord * uBreakScale).rgb;\n"
		+ "\t// 634HD's water body, as on every other surface: the palette, with the\n"
		+ "\t// stock texel tinting the shallow end, so the breaking type cannot read as\n"
		+ "\t// the flat white sheet a white material texture left behind either.\n"
		+ "\tvec3 water = mix(uDeepColour, texel * uShallowColour, smoothstep(0.0, 0.65, facing));\n"
		+ "\t// The net belongs to the seabed, not the surface: see FRAGMENT.\n"
		+ "\tvec3 body = water;\n"
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
		+ "\tvec3 bent = reflect(view, normal);\n"
		+ "\tvec3 environment = textureCube(uEnvironment, bent).rgb;\n"
		+ "\t// The band is what this type reflects at the shore, in the material's\n"
		+ "\t// own two-value crest colour.\n"
		+ "\tvec3 reflection = mix(environment, crestColour, clamp(band, -1.0, 1.0));\n"
		+ "\tfloat foamMask = hdWaterFoamTexel(vWorldPosition.xz, phase);\n"
		+ "\tvec3 lit = hdWaterBody634(vEyePosition, normal, view, normalize(vNormalY), vFoam, foamMask, reflection).rgb;\n"
		+ "\t// 634HD's surface paints no caustic net: the net is the seabed's, lit\n"
		+ "\t// by the terrain pass under this water. On the surface it read as a net\n"
		+ "\t// over a flat sheet, which is what the stock water texture did.\n"
		+ "\tvec3 colour = clamp(lit, 0.0, 1.0);\n"
		+ "\tfloat fogFactor = clamp((gl_Fog.end - abs(vEyePosition.z)) * gl_Fog.scale, 0.0, 1.0);\n"
		+ "\t// 634HD's water is a composed surface, not a translucent overlay, so\n"
		+ "\t// the vertex colour's alpha must not thin it: terrain showing through\n"
		+ "\t// the waves is the see-through water this row exists to fix.\n"
		+ "\tgl_FragColor = vec4(mix(gl_Fog.color.rgb, colour, fogFactor), 1.0);\n"
		+ "}\n";

	/**
	 * The custom surface's fragment stage: the water section's third look.
	 *
	 * <p>Same surface basis, sun and fog as {@link #FRAGMENT}, so it sits in the
	 * same frame; what it adds is the look the row's Custom value promises - a
	 * third wave train along the diagonal, absorption between two colours, a
	 * tighter Fresnel, crest light, a hashed sun sparkle, and the Caustics row's
	 * net over the surface. It reflects the same environment cube as every other
	 * water surface, so the custom look is a style, not a different sky.
	 */
	public static final String CUSTOM_FRAGMENT = "#version " + GLSL_VERSION + "\n"
		+ "\n"
		+ "uniform sampler2D uTexture;\n"
		+ "uniform float uTime;\n"
		+ "uniform float uFlow;\n"
		+ "uniform float uShininess;\n"
		+ "uniform samplerCube uEnvironment;\n"
		+ "uniform float uReflection;\n"
		+ WATER_TEXTURE_GLSL
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
		// hdCaustics now lives in WATER_TEXTURE_GLSL: every surface reads it.

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
		+ "\t// 634HD water texture: the shipped normal map, three scrolled layers,\n"
		+ "\t// scaled by the row ripple strength. With no maps bound the surface\n"
		+ "\t// keeps the wave field above, which is the draw this port shipped.\n"
		+ "\tif (uWaterMaps > 0.5) {\n"
		+ "\t\tsurface.xz += hdWaterRipples(vWorldPosition.xz, phase);\n"
		+ "\t\tsurface = normalize(surface);\n"
		+ "\t}\n"
		+ "\tvec3 normal = normalize(vNormalX * surface.x\n"
		+ "\t\t+ vNormalY * surface.y + vNormalZ * surface.z);\n"
		+ "\tvec3 up = normalize(vNormalY);\n"
		+ "\tvec3 view = normalize(-vEyePosition);\n"
		+ "\tfloat facing = clamp(dot(normal, view), 0.0, 1.0);\n"
		+ "\tfloat finalFresnel = hdWaterFresnel634(facing);\n"
		+ "\tvec3 bent = reflect(view, normal);\n"
		+ "\tbent.xz += normal.xz * HD_WATER_REFLECTION_BEND;\n"
		+ "\tvec3 reflected = textureCube(uEnvironment, bent).rgb;\n"
		+ "\tvec3 mirror = mix(gl_Fog.color.rgb, reflected, clamp(uReflection, 0.0, 1.0));\n"
		+ "\tvec3 reflection = mix(hdWaterTint634(hdWaterAreaTint634(), finalFresnel),\n"
		+ "\t\tmirror * finalFresnel,\n"
		+ "\t\tHD_WATER_MIRROR_SHARE * clamp(uReflection, 0.0, 1.0));\n"
		+ "\tfloat foamMask = hdWaterFoamTexel(vWorldPosition.xz, phase);\n"
		+ "\tvec3 lit = hdWaterBody634(vEyePosition, normal, view, normalize(vNormalY), vFoam, foamMask, reflection).rgb;\n"
		+ "\t// The third style's own spar: a hashed cell per quarter scene unit,\n"
		+ "\t// stepped four times a second, only where the sun already glints.\t\n"
		+ "\tvec3 halfDir = normalize(normalize(gl_LightSource[0].position.xyz) + view);\n"
		+ "\tfloat tight = pow(max(dot(normal, halfDir), 0.0), uShininess);\n"
		+ "\tfloat crest = clamp((1.0 - dot(normal, up)) * 6.0, 0.0, 1.0);\n"
		+ "\tvec2 cell = floor(vWorldPosition.xz * 4.0);\n"
		+ "\tfloat tick = floor(phase * 4.0);\n"
		+ "\tfloat sparkle = step(0.996, crestHash(cell, tick)) * tight;\n"
		+ "\tvec3 sun = gl_LightSource[0].diffuse.rgb;\n"
		+ "\tlit += sun * (sparkle * 2.5 + crest * 0.05);\n"
		+ "\t// The net belongs to the seabed, not the surface: see FRAGMENT.\n"
		+ "\tvec3 colour = clamp(lit, 0.0, 1.0);\n"
		+ "\tfloat fogFactor = clamp((gl_Fog.end - abs(vEyePosition.z)) * gl_Fog.scale, 0.0, 1.0);\n"
		+ "\t// 634HD's water is a composed surface, not a translucent overlay, so\n"
		+ "\t// the vertex colour's alpha must not thin it: terrain showing through\n"
		+ "\t// the waves is the see-through water this row exists to fix.\n"
		+ "\tgl_FragColor = vec4(mix(gl_Fog.color.rgb, colour, fogFactor), 1.0);\n"
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
