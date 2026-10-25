package com.jagex.game.runetek5.client.hd.shader;

/**
 * The GLSL 1.20 source for 592HD's per-pixel model lighting, and the names of
 * the uniforms the pipeline sets.
 *
 * <p>Why 1.20 and not 3.30: this client's renderer is the 592 fixed-function
 * toolkit on {@code jaggl}, and the shader only has to sit in front of an
 * existing submission (client arrays, {@code glBegin} batches) without a core
 * profile, VAOs or an attribute translator. GLSL 1.20 is the version those
 * drivers already compile the water and volume effects with.
 *
 * <p>Why the light and fog state comes from {@code gl_LightSource},
 * {@code gl_LightModel} and {@code gl_Fog} instead of new uniforms: the
 * fixed-function path already uploads the sun, the four point lights and the
 * fog band into that state, so reading it means the shader cannot drift from
 * what the stock renderer would have drawn. The only uniforms left are the
 * ones GL state cannot express: which texture unit to sample, whether the
 * primitive is textured at all, how many point lights are active, the sun's
 * shadow payload, and the three atmosphere rows.
 *
 * <p>The lighting itself is the fixed-function sum, per pixel: global ambient
 * plus {@code max(N.L, 0)} for the sun and anti-sun lights, plus the point
 * lights attenuated exactly as the toolkit configures them. That is
 * {@code 1 / (quadratic * d^2)}, not {@code 1 / (1 + quadratic * d^2)}:
 * {@code method1896} zeroes the constant and linear terms for all eight lights
 * and {@code method1903} sets only the quadratic one to {@code 1/r^2}. Alpha is
 * not lit, and fog is the stock {@code GL_LINEAR} band evaluated on
 * {@code |z_eye|} - or, for a draw the stock path renders fogless (the
 * terrain), no fog at all via {@code uFogOff}.
 *
 * <p>Three details are load-bearing on a real driver, all found by the real-GL
 * test in this package rather than by inspection. The vertex stage copies
 * {@code gl_Color} into {@code gl_FrontColor} and {@code gl_BackColor}, because
 * a vertex shader that writes neither leaves the fragment stage's
 * {@code gl_Color} undefined - NVIDIA supplies zero, which blackens every lit
 * surface. And the lit colour is clamped before the fog mix, because the
 * fixed-function pipeline clamps before fog; mixing unclamped lighting (which
 * routinely exceeds 1 with ambient plus a sun) faded saturated surfaces toward
 * the fog colour at the wrong rate. Third, the far plane the distance fade needs
 * is derived from {@code gl_ProjectionMatrix} in the vertex stage
 * ({@code m23 / (1 + m22)}), so the fade tracks the scene's own far plane as the
 * draw distance changes instead of a constant.
 *	 * <p>Every atmosphere row is a {@code if} on an int uniform whose off value is
	 * zero, and GL initialises uniforms to zero: with the rows off the pipeline
	 * uploads nothing and the fragment stage executes exactly the arithmetic it
	 * executed before the rows existed. Off is the identity, not a different look.
	 * The environment-light row follows the same rule: it swaps the ambient's
	 * colour for the sky's on surfaces that face it, and with its flag zero the
	 * stock {@code gl_LightModel.ambient} expression is the one evaluated.
	 *
	 * <p>The carried and room lights follow the same rule and are the only terms
	 * that read {@code vWorldPosition} for lighting rather than for the shadow
	 * map: both are defined in scene space, so the terrain's tile-local draw and a
	 * model's placement matrix report the same position for the same world point.
	 * The carried light has compact support - it reaches zero at its radius - and
	 * the room regions are disjoint chunk rectangles, so neither can brighten
	 * geometry outside its own source.
	 *
	 * <p>The two vegetation rows are the only place the vertex stage moves a
	 * vertex. They read the draw's own kind ({@code uVegetation}, carried from the
	 * loc's shape like the light rows), the frame's wind clock and the viewer's
	 * scene position, and they displace a point above the model's base by a
	 * lever on its height. With both amplitudes zero the branch is not taken and
	 * the stock {@code ftransform} result stands, so Off is the frame before the
	 * rows existed rather than a near miss.
	 */
public final class HdShaderSource {

	/** {@code #version} the sources declare; see the class comment. */
	public static final int GLSL_VERSION = 120;

	/** The only texture unit the material samples. */
	public static final int SAMPLER_UNIT = 0;

	/** Unit the sun shadow map is bound to; unit 0 is the material. */
	public static final int SAMPLER_UNIT_SHADOW = 1;

	/** Unit the environment cube is bound to; unit 0 is the material, 1 the shadow map. */
	public static final int SAMPLER_UNIT_REFLECTION = 2;

	/** Unit the world-texture atlas is bound to; the ground pass's own textures. */
	public static final int SAMPLER_UNIT_WORLD_TEXTURES = 3;

	/** Unit the material map (material id to atlas cell) is bound to. */
	public static final int SAMPLER_UNIT_MATERIAL_MAPS = 4;

	/** Unit the ground mesh's per-tile material map is bound to. */
	public static final int SAMPLER_UNIT_TILE_MATERIALS = 5;

	/** {@code sampler2D}: the primitive's material texture. */
	public static final String UNIFORM_TEXTURE = "uTexture";

	/** {@code int}: 1 when the primitive has a bound material texture. */
	public static final String UNIFORM_TEXTURED = "uTextured";

	/** {@code int}: how many of {@code GL_LIGHT2..5} are live this frame. */
	public static final String UNIFORM_POINT_LIGHTS = "uPointLightCount";

	/** {@code sampler2D}: the sun's depth map, or a bound unit with nothing to say. */
	public static final String UNIFORM_SHADOW_MAP = "uShadowMap";

	/**
	 * {@code vec4}s: the world-to-shadow-texture matrix, one row each. Rows
	 * rather than a {@code mat4} because this project's jaggl wrapper has no
	 * matrix uniform, and four {@code glUniform4f} calls are all it needs.
	 */
	public static final String[] UNIFORM_SHADOW_ROWS = {
		"uShadowRow0", "uShadowRow1", "uShadowRow2", "uShadowRow3"
	};

	/** {@code vec2}: one texel, for the PCF offsets. */
	public static final String UNIFORM_SHADOW_TEXEL = "uShadowTexel";

	/** {@code float}: depth bias in texture-space depth units. */
	public static final String UNIFORM_SHADOW_BIAS = "uShadowBias";

	/** {@code int}: PCF kernel radius in texels; 0 is a single tap. */
	public static final String UNIFORM_SHADOW_RADIUS = "uShadowRadius";

	/** {@code int}: 1 when this draw samples a real shadow map. */
	public static final String UNIFORM_SHADOW_ENABLED = "uShadowEnabled";

	/**
	 * {@code float}: how much of the sun's shadow survives this frame.
	 *
	 * <p>1 is a full shadow and multiplies the sample through unchanged. The
	 * day/night clock lowers it toward 0 at night, because a shadow that is as
	 * dark as the night is a second darkness rather than a shadow: the shadowed
	 * surface fades toward the lit one instead. GL's initial value is 0, which
	 * would mean "no shadow at all", so the pipeline uploads it alongside the
	 * enable flag - on the same draws that sample the map, and no others.
	 */
	public static final String UNIFORM_SHADOW_STRENGTH = "uShadowStrength";

	/** {@code int}: 0 is the stock linear fog band, 1 the HD squared band. */
	public static final String UNIFORM_FOG_MODE = "uFogMode";

	/**
	 * {@code int}: 1 when this draw has no fog band at all.
	 *
	 * <p>The fragment stage always applies the band, so a draw whose frame has
	 * {@code GL_FOG} off cannot use it - until this row exists. It is the
	 * terrain's row: the ground pass turns fog off and lights its tiles itself,
	 * so without it the ground would be the one surface in the scene that gets
	 * no per-pixel light and no sun shadow. Zero - GL's initial value - means
	 * the band applies, which is every other draw in the client.
	 */
	public static final String UNIFORM_FOG_OFF = "uFogOff";

	/** {@code int}: 1 when the far plane's outer band fades into the fog colour. */
	public static final String UNIFORM_DISTANCE_FADE = "uDistanceFade";

	/** {@code float}: fraction of the far plane the distance fade covers. */
	public static final String UNIFORM_FADE_BAND = "uFadeBand";

	/** {@code int}: 1 when the ambient term is attenuated by map contact. */
	public static final String UNIFORM_AO_ENABLED = "uAoEnabled";

	/** {@code float}: how much ambient a fully occluded fragment loses. */
	public static final String UNIFORM_AO_STRENGTH = "uAoStrength";

	/** {@code int}: 1 when this draw's ambient takes the environment's sky colour. */
	public static final String UNIFORM_ENV_LIGHTING = "uEnvLighting";

	/**
	 * {@code vec4}: the environment's sky colour and its level ambient.
	 *
	 * <p>{@code rgb} is the colour the world fades into (the sky/fog colour the
	 * scene uploaded), {@code a} the level ambient the map asked for. A surface
	 * facing the sky takes the ambient in this colour at that level; the stock
	 * value it replaces is the same level tinted by the sun.
	 */
	public static final String UNIFORM_ENV_SKY = "uEnvSky";

	/**
	 * {@code vec4}: the world's up axis in eye space, {@code xyz} used.
	 *
	 * <p>The normals this stage dots against are eye-space, so the sky's
	 * direction has to be too. The scene's modelview is {@code rotX(180) * view}
	 * for models and terrain alike, so the toolkit composes the same transform
	 * and uploads one direction rather than a matrix per draw.
	 */
	public static final String UNIFORM_ENV_UP = "uEnvUp";

	/**
	 * {@code vec4}s: this model draw's model-to-scene matrix, one row each.
	 *
	 * <p>The vertex buffer holds model-local positions and the model's place in
	 * the world lives in the matrix the toolkit multiplies into the modelview,
	 * so {@code gl_Vertex} is <em>not</em> a scene position. The shadow map and
	 * the occlusion kernel are both defined in scene space, so the vertex stage
	 * composes this matrix and carries the result as {@code vWorldPosition}.
	 *
	 * <p>Identity when nothing sets it (the pipeline uploads identity for a draw
	 * that has no model transform), which is what a caller drawing in scene
	 * coordinates already means.
	 */
	public static final String[] UNIFORM_MODEL_ROWS = {
		"uModelRow0", "uModelRow1", "uModelRow2", "uModelRow3"
	};

	/**
	 * {@code vec4}s: this draw's scene-to-eye matrix, used only when the draw's
	 * own modelview cannot supply it.
	 *
	 * <p>The terrain pass has to load an identity modelview so its tile-window
	 * ortho projection maps tile-local vertices to the screen. That leaves
	 * {@code gl_ModelViewMatrix * gl_Vertex} meaningless as an eye position, so
	 * the pass supplies the same view transform the scene uses (the ground
	 * matrix, with the 180-degree X flip the toolkit applies) and the shader
	 * composes it with the tile-to-scene matrix instead.
	 */
	public static final String[] UNIFORM_EYE_ROWS = {
		"uEyeRow0", "uEyeRow1", "uEyeRow2", "uEyeRow3"
	};

	/**
	 * {@code int}: 1 when the draw is the tile-local terrain pass and its light
	 * state is in scene space, 0 for the ordinary scene-space model path.
	 */
	public static final String UNIFORM_SCENE_SPACE = "uSceneSpace";

	/** {@code int}: 1 when this frame has a carried light to add. */
	public static final String UNIFORM_CARRIED_ENABLED = "uCarriedEnabled";

	/** {@code vec4}: the carried light's scene position and its radius. */
	public static final String UNIFORM_CARRIED_POSITION = "uCarriedPosition";

	/** {@code vec4}: the carried light's colour and its 0..1 intensity. */
	public static final String UNIFORM_CARRIED_COLOUR = "uCarriedColour";

	/** {@code int}: 1 when this frame has room regions to add. */
	public static final String UNIFORM_ROOM_ENABLED = "uRoomEnabled";

	/**
	 * Room regions one frame can carry, matching
	 * {@code HdLightFrame.MAX_ROOM_REGIONS}. Each region is two uniforms - its
	 * scene-space bounds and its colour - so the count is a fixed "nearly free"
	 * grid rather than a loop whose bound a driver would have to unroll.
	 */
	public static final int ROOM_REGIONS = 9;

	/**
	 * {@code vec4}s: one room region's scene-space bounds each,
	 * {@code (minX, minZ, maxX, maxZ)}. Disjoint chunk rectangles: a fragment is
	 * inside at most one, which is what keeps a region from leaking into its
	 * neighbour. An unused slot is uploaded as an empty rectangle - GL's zeroed
	 * value already is one.
	 */
	public static final String[] UNIFORM_ROOM_BOUNDS = {
		"uRoomBounds0", "uRoomBounds1", "uRoomBounds2", "uRoomBounds3", "uRoomBounds4",
		"uRoomBounds5", "uRoomBounds6", "uRoomBounds7", "uRoomBounds8"
	};

	/** {@code vec4}s: one room region's colour and additive strength each. */
	public static final String[] UNIFORM_ROOM_COLOUR = {
		"uRoomColour0", "uRoomColour1", "uRoomColour2", "uRoomColour3", "uRoomColour4",
		"uRoomColour5", "uRoomColour6", "uRoomColour7", "uRoomColour8"
	};

	/**
	 * {@code int}: 1 when this draw's geometry is vegetation and may be
	 * displaced. Carried with the draw from the loc's own shape, exactly as the
	 * light rows are: a sway shader cannot tell a fern from a fence post, and
	 * the scene is the only place that knows which it submitted.
	 *
	 * <p>Zero is not vegetation and is GL's initial value, so a draw the scene
	 * did not mark - every draw in stock - takes the untouched branch.
	 */
	public static final String UNIFORM_VEGETATION = "uVegetation";

	/**
	 * {@code vec4}: {@code (foliageAmplitude, grassAmplitude, grassRadius,
	 * heightScale)}, in scene units.
	 *
	 * <p>Both amplitudes are exactly zero when their row is off, so the vertex
	 * stage's branch is false and the geometry takes the stock transform. The
	 * two rows share this one block on purpose: a plant that is both swaying and
	 * being parted gets both offsets summed once, instead of one row overwriting
	 * the other's displacement.
	 */
	public static final String UNIFORM_SWAY = "uSway";

	/** {@code vec4}: {@code (seconds, radiansPerSecond, 0, 0)} - the wind clock. */
	public static final String UNIFORM_SWAY_PHASE = "uSwayPhase";

	/**
	 * {@code vec4}: {@code (strength, seconds, 0, 0)} - the caustics term.
	 *
	 * <p>{@code strength} is the Caustics row's amplitude for this tile, scaled by
	 * the tile's own water depth ({@code HdGroundWater}: the stock 0..255 byte the
	 * client keeps for underwater ground). It is exactly zero when the row is off
	 * or nothing stands over the tile, and zero is GL's initial value, so the
	 * pipeline uploads nothing and the fragment stage's branch is not taken - the
	 * row's Off state is the frame before it existed. {@code seconds} is the water
	 * clock ({@code HdWaterTime}), so the pattern drifts with the surface that
	 * casts it rather than with a clock of its own.
	 */
	public static final String UNIFORM_CAUSTICS = "uCaustics";

	/**
	 * {@code vec4}: {@code (strength, 0, 0, 0)} - the environment reflection.
	 *
	 * <p>{@code strength} is the Reflections row's amplitude for a draw the
	 * client already considers reflective (the material's stock effect slot is
	 * {@code HdReflection.SPECULAR_SLOT}). It is exactly zero for every other
	 * material and when the row is off, and zero is GL's initial value, so the
	 * pipeline uploads nothing and the fragment stage takes no branch.
	 */
	public static final String UNIFORM_REFLECTION = "uReflection";

	/**
	 * {@code samplerCube}: the client's environment cube, the same one the
	 * reflective water program samples ({@code GlToolkit.method1946()}).
	 */
	public static final String UNIFORM_REFLECTION_CUBE = "uReflectionCube";

	/** {@code sampler2D}: the atlas of ground material textures, one cell each. */
	public static final String UNIFORM_WORLD_ATLAS = "uWorldAtlas";

	/** {@code sampler2D}: the material map, one texel per material id. */
	public static final String UNIFORM_MATERIAL_ID_MAP = "uMaterialIdMap";

	/** {@code sampler2D}: the ground mesh's per-tile material map. */
	public static final String UNIFORM_TILE_MATERIALS = "uTileMaterials";

	/** {@code int}: 1 when this draw samples the world-texture atlas. */
	public static final String UNIFORM_WORLD_TEXTURES = "uWorldTextures";

	/** {@code int}: 1 when the material map resolves a ground material to its cell. */
	public static final String UNIFORM_MATERIAL_MAPS = "uMaterialMaps";

	/**
	 * {@code vec4}: {@code (material, tileSize, tileMapTexel, blendStrength)} -
	 * the ground draw's material and the three constants that put a fragment on
	 * its tile.
	 */
	public static final String UNIFORM_TERRAIN_BLEND = "uTerrainBlend";

	/** {@code float}: scene units one world-texture repeat covers. */
	public static final String UNIFORM_WORLD_TEXTURE_REPEAT = "uWorldTextureRepeat";

	/**
	 * {@code vec4}: the viewer's scene position ({@code xyz} used) - the eye the
	 * grass is pushed away from.
	 *
	 * <p>Read from the frame's own camera rather than from an actor: the
	 * renderer can recover it by inverting the scene-to-eye map it already
	 * supplies, and inventing an actor list here would put game state on the
	 * presentation path. The viewer's own body parts the grass around them, which
	 * is the case a player notices.
	 */
	public static final String UNIFORM_SWAY_ORIGIN = "uSwayOrigin";

	private static final String[] UNIFORMS = {
		UNIFORM_TEXTURE, UNIFORM_TEXTURED, UNIFORM_POINT_LIGHTS,
		UNIFORM_SHADOW_MAP, UNIFORM_SHADOW_ROWS[0], UNIFORM_SHADOW_ROWS[1],
		UNIFORM_SHADOW_ROWS[2], UNIFORM_SHADOW_ROWS[3], UNIFORM_SHADOW_TEXEL,
		UNIFORM_SHADOW_BIAS, UNIFORM_SHADOW_RADIUS, UNIFORM_SHADOW_ENABLED,
		UNIFORM_SHADOW_STRENGTH,
		UNIFORM_FOG_MODE, UNIFORM_FOG_OFF, UNIFORM_DISTANCE_FADE, UNIFORM_FADE_BAND,
		UNIFORM_AO_ENABLED, UNIFORM_AO_STRENGTH,
		UNIFORM_ENV_LIGHTING, UNIFORM_ENV_SKY, UNIFORM_ENV_UP,
		UNIFORM_CARRIED_ENABLED, UNIFORM_CARRIED_POSITION, UNIFORM_CARRIED_COLOUR,
		UNIFORM_ROOM_ENABLED,
		UNIFORM_ROOM_BOUNDS[0], UNIFORM_ROOM_BOUNDS[1], UNIFORM_ROOM_BOUNDS[2],
		UNIFORM_ROOM_BOUNDS[3], UNIFORM_ROOM_BOUNDS[4], UNIFORM_ROOM_BOUNDS[5],
		UNIFORM_ROOM_BOUNDS[6], UNIFORM_ROOM_BOUNDS[7], UNIFORM_ROOM_BOUNDS[8],
		UNIFORM_ROOM_COLOUR[0], UNIFORM_ROOM_COLOUR[1], UNIFORM_ROOM_COLOUR[2],
		UNIFORM_ROOM_COLOUR[3], UNIFORM_ROOM_COLOUR[4], UNIFORM_ROOM_COLOUR[5],
		UNIFORM_ROOM_COLOUR[6], UNIFORM_ROOM_COLOUR[7], UNIFORM_ROOM_COLOUR[8],
		UNIFORM_CAUSTICS, UNIFORM_REFLECTION, UNIFORM_REFLECTION_CUBE,
		UNIFORM_WORLD_ATLAS, UNIFORM_MATERIAL_ID_MAP, UNIFORM_TILE_MATERIALS,
		UNIFORM_WORLD_TEXTURES, UNIFORM_MATERIAL_MAPS, UNIFORM_TERRAIN_BLEND,
		UNIFORM_WORLD_TEXTURE_REPEAT
	};

	/** The uniforms the vertex stage owns; the pipeline locates them there. */
	private static final String[] VERTEX_UNIFORMS = {
		UNIFORM_MODEL_ROWS[0], UNIFORM_MODEL_ROWS[1], UNIFORM_MODEL_ROWS[2], UNIFORM_MODEL_ROWS[3],
		UNIFORM_EYE_ROWS[0], UNIFORM_EYE_ROWS[1], UNIFORM_EYE_ROWS[2], UNIFORM_EYE_ROWS[3],
		UNIFORM_SCENE_SPACE,
		UNIFORM_VEGETATION, UNIFORM_SWAY, UNIFORM_SWAY_PHASE, UNIFORM_SWAY_ORIGIN
	};

	/**
	 * Eye-space varyings, because both the lighting and the fog band are
	 * evaluated there in fixed function. {@code ftransform()} keeps the clip
	 * transform identical to the stock path, including the scaled interface.
	 */
	public static final String VERTEX = "#version " + GLSL_VERSION + "\n"
		+ "\n"
		+ "varying vec3 vEyePosition;\n"
		+ "varying vec3 vEyeNormal;\n"
		+ "varying vec3 vSceneNormal;\n"
		+ "varying vec3 vWorldPosition;\n"
		+ "varying vec2 vTexCoord;\n"
		+ "varying float vFarPlane;\n"
		+ "\n"
		+ "uniform vec4 uModelRow0;\n"
		+ "uniform vec4 uModelRow1;\n"
		+ "uniform vec4 uModelRow2;\n"
		+ "uniform vec4 uModelRow3;\n"
		+ "uniform vec4 uEyeRow0;\n"
		+ "uniform vec4 uEyeRow1;\n"
		+ "uniform vec4 uEyeRow2;\n"
		+ "uniform vec4 uEyeRow3;\n"
		+ "uniform int uSceneSpace;\n"
		+ "uniform int uVegetation;\n"
		+ "uniform vec4 uSway;\n"
		+ "uniform vec4 uSwayPhase;\n"
		+ "uniform vec4 uSwayOrigin;\n"
		+ "\n"
		+ "// The two vegetation rows' shared offset, in scene units. Both terms are\n"
		+ "// exactly zero when their row is off, and main() skips this call when both\n"
		+ "// are, so Off is the stock transform rather than a small lean.\n"
		+ "vec3 swayOffset(vec3 world) {\n"
		+ "\t// A plant pivots at its own base, so the travel is a lever on height\n"
		+ "\t// above that base: nothing at the root, most at the crown.\n"
		+ "\t// uModelRow1.w is the model matrix's y translation, the base the\n"
		+ "\t// model was placed at - the same base a loc's model is drawn from.\n"
		+ "\tfloat height = clamp((world.y - uModelRow1.w) / max(uSway.w, 1.0), 0.0, 1.0);\n"
		+ "\tfloat lever = height * height;\n"
		+ "\t// Wind: a travelling wave phased by the plant's own place in the\n"
		+ "\t// world, so neighbours lean together but not in lockstep. The two\n"
		+ "\t// components are in quadrature, which makes the lean a slow circle\n"
		+ "\t// rather than a line - pushed and released, not swinging on a rail.\n"
		+ "\tfloat phase = uSwayPhase.x * uSwayPhase.y + dot(world.xz, vec2(0.017, 0.011));\n"
		+ "\tvec2 wind = vec2(sin(phase), cos(phase * 0.83)) * (uSway.x * lever);\n"
		+ "\t// Grass: parted away from the viewer, with compact support, so the\n"
		+ "\t// push is exactly zero at the radius and nothing outside it moves.\n"
		+ "\tvec2 delta = world.xz - uSwayOrigin.xz;\n"
		+ "\tfloat reach = length(delta);\n"
		+ "\tfloat window = clamp(1.0 - reach / max(uSway.z, 1.0e-4), 0.0, 1.0);\n"
		+ "\tvec2 away = vec2(0.0, 1.0);\n"
		+ "\tif (reach > 1.0e-4) {\n"
		+ "\t\taway = delta / reach;\n"
		+ "\t}\n"
		+ "\tvec2 push = away * (uSway.y * window * lever);\n"
		+ "\treturn vec3(wind + push, 0.0);\n"
		+ "}\n"
		+ "\n"
		+ "void main() {\n"
		+ "\t// Scene space: the vertex buffer is model-local and the model's\n"
		+ "\t// placement lives in the matrix the toolkit composes into the\n"
		+ "\t// modelview, so gl_Vertex alone is not a world position.\n"
		+ "\tvec4 model = vec4(gl_Vertex.xyz, 1.0);\n"
		+ "\tvWorldPosition = vec3(dot(uModelRow0, model), dot(uModelRow1, model),\n"
		+ "\t\tdot(uModelRow2, model));\n"
		+ "\tvec4 world = vec4(vWorldPosition, 1.0);\n"
		+ "\tvec4 eye;\n"
		+ "\tif (uSceneSpace != 0) {\n"
		+ "\t\t// Terrain: the pass supplies its view matrix explicitly, because\n"
		+ "\t\t// the vertex buffer is scene units and this is the transform the\n"
		+ "\t\t// camera applies to it. The normal lives in the same scene axes\n"
		+ "\t\t// and the fixed-function lights were uploaded as eye-space\n"
		+ "\t\t// positions under that modelview, so both must be moved to eye\n"
		+ "\t\t// space before they are dotted together.\n"
		+ "\t\teye = vec4(dot(uEyeRow0, world), dot(uEyeRow1, world),\n"
		+ "\t\t\tdot(uEyeRow2, world), 1.0);\n"
		+ "\t\tvEyeNormal = normalize(vec3(dot(uEyeRow0.xyz, gl_Normal),\n"
		+ "\t\t\tdot(uEyeRow1.xyz, gl_Normal), dot(uEyeRow2.xyz, gl_Normal)));\n"
		+ "\t\t// A terrain vertex format without a normal array leaves gl_Normal\n"
		+ "\t\t// at the current normal, which is zero after a pass that never set\n"
		+ "\t\t// one. normalize(0) is NaN, and every lighting term is a dot with\n"
		+ "\t\t// this vector, so a degenerate normal blacks the surface out. The\n"
		+ "\t\t// terrain's own rows carry the scene's up axis in eye space, which\n"
		+ "\t\t// is the one direction a missing ground normal can mean.\n"
		+ "\t\tif (!(dot(vEyeNormal, vEyeNormal) > 0.5)) {\n"
		+ "\t\t\tvEyeNormal = normalize(vec3(uEyeRow0.y, uEyeRow1.y, uEyeRow2.y));\n"
		+ "\t\t}\n"
		+ "\t} else {\n"
		+ "\t\teye = gl_ModelViewMatrix * gl_Vertex;\n"
		+ "\t\tvEyeNormal = normalize(gl_NormalMatrix * gl_Normal);\n"
		+ "\t}\n"
		+ "\tvEyePosition = eye.xyz;\n"
		+ "\t// The scene-space normal, for the terrain's triplanar mapping: its\n"
		+ "\t// weights must not turn with the camera, so they cannot use the\n"
		+ "\t// eye-space normal the lighting does. Terrain normals already are\n"
		+ "\t// scene axes; a model's are moved by its own placement rows.\n"
		+ "\tif (uSceneSpace != 0) {\n"
		+ "\t\t// Same degenerate case in scene axes, and here it is the triplanar\n"
		+ "\t\t// weights: NaN in them would reach the albedo, so a missing terrain\n"
		+ "\t\t// normal takes the up axis and maps as flat ground. The comparison\n"
		+ "\t\t// is written negated on purpose - NaN fails every ordered test,\n"
		+ "\t\t// and !(NaN > 0) is the one form that catches it in GLSL 120.\n"
		+ "\t\tvec3 rawNormal = gl_Normal;\n"
		+ "\t\tif (!(dot(rawNormal, rawNormal) > 1.0e-8)) {\n"
		+ "\t\t\trawNormal = vec3(0.0, 1.0, 0.0);\n"
		+ "\t\t}\n"
		+ "\t\tvSceneNormal = normalize(rawNormal);\n"
		+ "\t} else {\n"
		+ "\t\tvSceneNormal = normalize(vec3(dot(uModelRow0.xyz, gl_Normal),\n"
		+ "\t\t\tdot(uModelRow1.xyz, gl_Normal), dot(uModelRow2.xyz, gl_Normal)));\n"
		+ "\t}\n"
		+ "\tvec4 clip = ftransform();\n"
		+ "\t// The two vegetation rows: only draws the scene marked as plants, and\n"
		+ "\t// only while at least one row gives a non-zero amplitude. Both off is\n"
		+ "\t// this branch not taken, so the stock transform above stands bit for\n"
		+ "\t// bit. The moved point re-derives its eye position from the same\n"
		+ "\t// scene-to-eye rows an explicit scene-space draw would use, so the\n"
		+ "\t// displaced vertex is exactly where a model submitted there would be.\n"
		+ "\tif (uVegetation != 0 && (uSway.x != 0.0 || uSway.y != 0.0)) {\n"
		+ "\t\tvec3 moved = vWorldPosition + swayOffset(vWorldPosition);\n"
		+ "\t\tvec4 scene = vec4(moved, 1.0);\n"
		+ "\t\tvWorldPosition = moved;\n"
		+ "\t\teye = vec4(dot(uEyeRow0, scene), dot(uEyeRow1, scene),\n"
		+ "\t\t\tdot(uEyeRow2, scene), 1.0);\n"
		+ "\t\tvEyePosition = eye.xyz;\n"
		+ "\t\tclip = gl_ProjectionMatrix * eye;\n"
		+ "\t}\n"
		+ "\tgl_Position = clip;\n"
		+ "\tvTexCoord = gl_MultiTexCoord0.xy;\n"
		+ "\t// The projection's far plane, for distance fade. For the perspective\n"
		+ "\t// matrix the toolkit loads, m23 / (1 + m22) is exactly anInt2312; a\n"
		+ "\t// projection where that is not positive disables the fade in the\n"
		+ "\t// fragment stage rather than fading the whole scene.\n"
		+ "\tvec4 farColumn = gl_ProjectionMatrix[3];\n"
		+ "\tvec4 depthColumn = gl_ProjectionMatrix[2];\n"
		+ "\tvFarPlane = farColumn.z / (1.0 + depthColumn.z);\n"
		+ "\tgl_FrontColor = gl_Color;\n"
		+ "\tgl_BackColor = gl_Color;\n"
		+ "}\n";

	public static final String FRAGMENT = "#version " + GLSL_VERSION + "\n"
		+ "\n"
		+ "uniform sampler2D uTexture;\n"
		+ "uniform int uTextured;\n"
		+ "uniform int uPointLightCount;\n"
		+ "uniform sampler2D uShadowMap;\n"
		+ "uniform vec4 uShadowRow0;\n"
		+ "uniform vec4 uShadowRow1;\n"
		+ "uniform vec4 uShadowRow2;\n"
		+ "uniform vec4 uShadowRow3;\n"
		+ "uniform vec2 uShadowTexel;\n"
		+ "uniform float uShadowBias;\n"
		+ "uniform int uShadowRadius;\n"
		+ "uniform int uShadowEnabled;\n"
		+ "uniform float uShadowStrength;\n"
		+ "uniform int uFogMode;\n"
		+ "uniform int uFogOff;\n"
		+ "uniform int uDistanceFade;\n"
		+ "uniform float uFadeBand;\n"
		+ "uniform int uAoEnabled;\n"
		+ "uniform int uSceneSpace;\n"
		+ "uniform float uAoStrength;\n"
		+ "uniform int uEnvLighting;\n"
		+ "uniform vec4 uEnvSky;\n"
		+ "uniform vec4 uEnvUp;\n"
		+ "uniform int uCarriedEnabled;\n"
		+ "uniform vec4 uCarriedPosition;\n"
		+ "uniform vec4 uCarriedColour;\n"
		+ "uniform int uRoomEnabled;\n"
		+ "uniform vec4 uRoomBounds0;\n"
		+ "uniform vec4 uRoomBounds1;\n"
		+ "uniform vec4 uRoomBounds2;\n"
		+ "uniform vec4 uRoomBounds3;\n"
		+ "uniform vec4 uRoomBounds4;\n"
		+ "uniform vec4 uRoomBounds5;\n"
		+ "uniform vec4 uRoomBounds6;\n"
		+ "uniform vec4 uRoomBounds7;\n"
		+ "uniform vec4 uRoomBounds8;\n"
		+ "uniform vec4 uRoomColour0;\n"
		+ "uniform vec4 uRoomColour1;\n"
		+ "uniform vec4 uRoomColour2;\n"
		+ "uniform vec4 uRoomColour3;\n"
		+ "uniform vec4 uRoomColour4;\n"
		+ "uniform vec4 uRoomColour5;\n"
		+ "uniform vec4 uRoomColour6;\n"
		+ "uniform vec4 uRoomColour7;\n"
		+ "uniform vec4 uRoomColour8;\n"
		+ "uniform vec4 uCaustics;\n"
		+ "uniform vec4 uReflection;\n"
		+ "uniform samplerCube uReflectionCube;\n"
		+ "uniform sampler2D uWorldAtlas;\n"
		+ "uniform sampler2D uMaterialIdMap;\n"
		+ "uniform sampler2D uTileMaterials;\n"
		+ "uniform int uWorldTextures;\n"
		+ "uniform int uMaterialMaps;\n"
		+ "uniform vec4 uTerrainBlend;\n"
		+ "uniform float uWorldTextureRepeat;\n"
		+ "\n"
		+ "varying vec3 vEyePosition;\n"
		+ "varying vec3 vEyeNormal;\n"
		+ "varying vec3 vSceneNormal;\n"
		+ "varying vec3 vWorldPosition;\n"
		+ "varying vec2 vTexCoord;\n"
		+ "varying float vFarPlane;\n"
		+ "\n"
		+ "vec4 shadowProjection() {\n"
		+ "\tvec4 world = vec4(vWorldPosition, 1.0);\n"
		+ "\treturn vec4(dot(uShadowRow0, world), dot(uShadowRow1, world), dot(uShadowRow2, world), dot(uShadowRow3, world));\n"
		+ "}\n"
		+ "\n"
		+ "// 1.0 lit, 0.0 shadowed, or the PCF average between them.\n"
		+ "float sunShadow() {\n"
		+ "\tif (uShadowEnabled == 0) {\n"
		+ "\t\treturn 1.0;\n"
		+ "\t}\n"
		+ "\tvec4 projected = shadowProjection();\n"
		+ "\tvec3 coord = projected.xyz / projected.w;\n"
		+ "\t// Outside the light's volume nothing is known to occlude: stay lit.\n"
		+ "\tif (coord.x < 0.0 || coord.x > 1.0 || coord.y < 0.0 || coord.y > 1.0 || coord.z > 1.0) {\n"
		+ "\t\treturn 1.0;\n"
		+ "\t}\n"
		+ "\tfloat visible = 0.0;\n"
		+ "\tfloat samples = 0.0;\n"
		+ "\tfor (int y = -2; y <= 2; y++) {\n"
		+ "\t\tfor (int x = -2; x <= 2; x++) {\n"
		+ "\t\t\tif (x < -uShadowRadius || x > uShadowRadius || y < -uShadowRadius || y > uShadowRadius) {\n"
		+ "\t\t\t\tcontinue;\n"
		+ "\t\t\t}\n"
		+ "\t\t\tfloat depth = texture2D(uShadowMap, coord.xy + vec2(float(x), float(y)) * uShadowTexel).r;\n"
		+ "\t\t\tvisible += (coord.z - uShadowBias > depth) ? 0.0 : 1.0;\n"
		+ "\t\t\tsamples += 1.0;\n"
		+ "\t\t}\n"
		+ "\t}\n"
		+ "\treturn visible / samples;\n"
		+ "}\n"
		+ "\n"
		+ "// Contact occlusion from the same map: a coarse 3x3 grid at three texels\n"
		+ "// and four times the bias. A fragment in contact with an occluder is\n"
		+ "// shadowed across that whole neighbourhood, so this reads as darkening in\n"
		+ "// creases rather than as a blurrier sun shadow.\n"
		+ "float sunOcclusion(vec3 coord) {\n"
		+ "\tfloat bias = uShadowBias * 4.0;\n"
		+ "\tfloat visible = 0.0;\n"
		+ "\tfor (int y = -1; y <= 1; y++) {\n"
		+ "\t\tfor (int x = -1; x <= 1; x++) {\n"
		+ "\t\t\tfloat depth = texture2D(uShadowMap, coord.xy + vec2(float(x), float(y)) * uShadowTexel * 3.0).r;\n"
		+ "\t\t\tvisible += (coord.z - bias > depth) ? 0.0 : 1.0;\n"
		+ "\t\t}\n"
		+ "\t}\n"
		+ "\treturn visible / 9.0;\n"
		+ "}\n"
		+ "\n"
		+ "// The carrier's own lamp. Compact support, so the term is exactly zero at\n"
		+ "// the radius and nothing beyond it is touched. No N.L term: this is the\n"
		+ "// light the carrier themselves holds, a soft omni term, while the map's\n"
		+ "// point lights keep their directional one.\n"
		+ "vec3 carriedLight() {\n"
		+ "\tif (uCarriedEnabled == 0) {\n"
		+ "\t\treturn vec3(0.0);\n"
		+ "\t}\n"
		+ "\tvec3 delta = uCarriedPosition.xyz - vWorldPosition;\n"
		+ "\tfloat window = clamp(1.0 - length(delta) / max(uCarriedPosition.w, 1.0e-4), 0.0, 1.0);\n"
		+ "\treturn uCarriedColour.rgb * (uCarriedColour.a * window * window);\n"
		+ "}\n"
		+ "\n"
		+ "// One region's contribution, or zero when the fragment is not inside it.\n"
		+ "// The bounds are disjoint chunk rectangles, so the nine terms can be summed\n"
		+ "// without double counting: at most one of them is ever non-zero.\n"
		+ "vec3 roomTerm(vec4 bounds, vec4 colour) {\n"
		+ "\tvec2 at = vWorldPosition.xz;\n"
		+ "\tif (at.x < bounds.x || at.x >= bounds.z || at.y < bounds.y || at.y >= bounds.w) {\n"
		+ "\t\treturn vec3(0.0);\n"
		+ "\t}\n"
		+ "\treturn colour.rgb * colour.a;\n"
		+ "}\n"
		+ "\n"
		+ "vec3 roomLight() {\n"
		+ "\tif (uRoomEnabled == 0) {\n"
		+ "\t\treturn vec3(0.0);\n"
		+ "\t}\n"
		+ "\tvec3 sum = vec3(0.0);\n"
		+ "\tsum += roomTerm(uRoomBounds0, uRoomColour0);\n"
		+ "\tsum += roomTerm(uRoomBounds1, uRoomColour1);\n"
		+ "\tsum += roomTerm(uRoomBounds2, uRoomColour2);\n"
		+ "\tsum += roomTerm(uRoomBounds3, uRoomColour3);\n"
		+ "\tsum += roomTerm(uRoomBounds4, uRoomColour4);\n"
		+ "\tsum += roomTerm(uRoomBounds5, uRoomColour5);\n"
		+ "\tsum += roomTerm(uRoomBounds6, uRoomColour6);\n"
		+ "\tsum += roomTerm(uRoomBounds7, uRoomColour7);\n"
		+ "\tsum += roomTerm(uRoomBounds8, uRoomColour8);\n"
		+ "\treturn sum;\n"
		+ "}\n"
		+ "\n"
		+ "// Caustics: the light a rippling surface focuses onto the ground under it.\n"
		+ "// Two counter-moving wave fields, each turned into a thin bright filament\n"
		+ "// where it crosses zero - the contour line of the wave - so the pattern is a\n"
		+ "// drifting net rather than a smooth wash. Evaluated in scene space, so the\n"
		+ "// net is anchored to the world and slides as the clock advances; the strength\n"
		+ "// is exactly zero when the row is off or the tile has no water over it, and\n"
		+ "// main() then adds nothing at all.\n"
		+ "vec3 causticsTerm() {\n"
		+ "\tif (uCaustics.x <= 0.0) {\n"
		+ "\t\treturn vec3(0.0);\n"
		+ "\t}\n"
		+ "\tvec2 at = vWorldPosition.xz * 0.055;\n"
		+ "\tfloat t = uCaustics.y;\n"
		+ "\tfloat first = sin(at.x * 1.7 + t * 0.9) + sin(at.y * 2.1 - t * 0.7);\n"
		+ "\tfloat second = sin((at.x + at.y) * 1.3 - t * 0.55) + sin((at.x - at.y) * 0.9 + t * 0.8);\n"
		+ "\tfloat lines = pow(clamp(1.0 - abs(first) * 0.5, 0.0, 1.0), 6.0)\n"
		+ "\t\t+ pow(clamp(1.0 - abs(second) * 0.5, 0.0, 1.0), 6.0);\n"
		+ "\treturn vec3(0.5 * uCaustics.x * lines);\n"
		+ "}\n"
		+ "\n"
		+ "// Environment reflection: the cube the stock specular effect reads, sampled\n"
		+ "// with this fragment's own view vector so the surface tracks the sky as the\n"
		+ "// camera moves. Only a material the client already draws reflectively gets\n"
		+ "// a non-zero strength; every other draw returns exactly zero here.\n"
		+ "vec3 reflectionTerm() {\n"
		+ "\tif (uReflection.x <= 0.0) {\n"
		+ "\t\treturn vec3(0.0);\n"
		+ "\t}\n"
		+ "\tvec3 direction = reflect(normalize(vEyePosition), normalize(vEyeNormal));\n"
		+ "\treturn textureCube(uReflectionCube, direction).rgb * uReflection.x;\n"
		+ "}\n"
		+ "\n"
		+ "// One ground material's albedo, from its cell on the world-texture page.\n"
		+ "// The cell comes from the material map, so no texture has to be bound per\n"
		+ "// material: one page holds them all and an id is enough to reach any cell.\n"
		+ "// Flat ground uses the planar mapping the stock texture matrix gives; steep\n"
		+ "// ground takes the three axis projections weighted by the scene normal, so a\n"
		+ "// cliff shows its texture instead of smeared edge texels.\n"
		+ "vec4 groundMaterial() {\n"
		+ "\tfloat material = uTerrainBlend.x;\n"
		+ "\tfloat cell = texture2D(uMaterialIdMap, vec2((material + 0.5) / 256.0, 0.5)).r * 255.0 - 1.0;\n"
		+ "\t// A material the page does not hold is reported to the caller, not\n"
		+ "\t// multiplied in: the stock draw of that material is its bound texture,\n"
		+ "\t// which is still on the base unit. White here would erase the texture\n"
		+ "\t// and leave the vertex colour - the flat ground this sentinel fixes.\n"
		+ "\tif (cell < -0.5) {\n"
		+ "\t\treturn vec4(-1.0);\n"
		+ "\t}\n"
		+ "\tvec2 origin = vec2(mod(cell, 8.0), floor(cell / 8.0)) * 0.125 + vec2(0.001953125, 0.001953125);\n"
		+ "\tvec2 span = vec2(0.12109375, 0.12109375);\n"
		+ "\tvec3 scene = abs(normalize(vSceneNormal));\n"
		+ "\tfloat steep = clamp(1.0 - scene.y, 0.0, 1.0);\n"
		+ "\tfloat repeat = max(uWorldTextureRepeat, 1.0);\n"
		+ "\tvec2 planarUv = fract(vWorldPosition.xz / repeat) * span + origin;\n"
		+ "\tvec4 planar = texture2D(uWorldAtlas, planarUv);\n"
		+ "\t// Flat ground takes the planar sample - and so does a fragment whose\n"
		+ "\t// scene normal is degenerate, because normalize() of a zero normal is\n"
		+ "\t// NaN and NaN would travel through the triplanar weights into the\n"
		+ "\t// albedo. Terrain without a height map has no normal array at all,\n"
		+ "\t// so that is the ordinary flat case, not a corner.\n"
		+ "\tif (!(steep > 0.0)) {\n"
		+ "\t\treturn planar;\n"
		+ "\t}\n"
		+ "\tvec3 weights = scene / max(scene.x + scene.y + scene.z, 1.0e-4);\n"
		+ "\tvec4 sides = texture2D(uWorldAtlas, fract(vWorldPosition.zy / repeat) * span + origin) * weights.x\n"
		+ "\t\t+ texture2D(uWorldAtlas, fract(vWorldPosition.xz / repeat) * span + origin) * weights.y\n"
		+ "\t\t+ texture2D(uWorldAtlas, fract(vWorldPosition.xy / repeat) * span + origin) * weights.z;\n"
		+ "\treturn mix(planar, sides, steep);\n"
		+ "}\n"
		+ "\n"
		+ "// A chosen ground material, resolved against the tile's own pair rather than\n"
		+ "// the draw's uniform: the drawn layer is one of the tile's two materials,\n"
		+ "// and the blend must reach the other one whichever side it is.\n"
		+ "vec4 groundMaterialFor(float material) {\n"
		+ "\tfloat cell = texture2D(uMaterialIdMap, vec2((material + 0.5) / 256.0, 0.5)).r * 255.0 - 1.0;\n"
		+ "\t// Negative alpha means \"no cell on the page\": the caller leaves the\n"
		+ "\t// tile drawing the material it does have. White would blend the tile\n"
		+ "\t// toward the lightening the stock path does not apply for a material\n"
		+ "\t// it cannot reach, and would wash the whole tile, not tint it.\n"
		+ "\tif (cell < -0.5) {\n"
		+ "\t\treturn vec4(-1.0);\n"
		+ "\t}\n"
		+ "\tvec2 origin = vec2(mod(cell, 8.0), floor(cell / 8.0)) * 0.125 + vec2(0.001953125, 0.001953125);\n"
		+ "\tvec2 span = vec2(0.12109375, 0.12109375);\n"
		+ "\tfloat repeat = max(uWorldTextureRepeat, 1.0);\n"
		+ "\treturn texture2D(uWorldAtlas, fract(vWorldPosition.xz / repeat) * span + origin);\n"
		+ "}\n"
		+ "\n"
		+ "// This ground draw's albedo: its own material, and the tile's second\n"
		+ "// material reaching through a noise mask. The pair is the tile's own - the\n"
		+ "// material map holds its two ids and a weight - so a fragment can only show\n"
		+ "// materials the tile is built from. A row off, a material missing from the\n"
		+ "// page, or a single-material tile leaves the stock texture path untouched.\n"
		+ "vec4 groundAlbedo() {\n"
		+ "\tvec2 at = vWorldPosition.xz / max(uTerrainBlend.y, 1.0);\n"
		+ "\tvec4 tile = texture2D(uTileMaterials, (floor(at) + 0.5) * uTerrainBlend.z);\n"
		+ "\tvec4 own = groundMaterial();\n"
		+ "\t// The page cannot serve this draw's own material, so there is no\n"
		+ "\t// atlas albedo to blend the tile's mate into: pass the sentinel on\n"
		+ "\t// and let main() take the stock texture, exactly as it would have\n"
		+ "\t// before the material joined the page.\n"
		+ "\tif (own.a < 0.0) {\n"
		+ "\t\treturn own;\n"
		+ "\t}\n"
		+ "\tfloat strength = uTerrainBlend.w * tile.b;\n"
		+ "\tif (strength <= 0.0) {\n"
		+ "\t\treturn own;\n"
		+ "\t}\n"
		+ "\tfloat primary = tile.r * 255.0;\n"
		+ "\tfloat mate = tile.g * 255.0;\n"
		+ "\tif (mate > 254.5) {\n"
		+ "\t\treturn own;\n"
		+ "\t}\n"
		+ "\tvec4 other = groundMaterialFor(uTerrainBlend.x == primary ? mate : primary);\n"
		+ "\tif (other.a < 0.0) {\n"
		+ "\t\treturn own;\n"
		+ "\t}\n"
		+ "\tvec2 noiseAt = vWorldPosition.xz * 0.075;\n"
		+ "\tfloat mask = 0.5 + 0.5 * sin(noiseAt.x * 2.3 + sin(noiseAt.y * 1.7))\n"
		+ "\t\t* sin(noiseAt.y * 2.9 + sin(noiseAt.x * 1.3));\n"
		+ "\treturn mix(own, other, clamp(mask * strength, 0.0, 1.0));\n"
		+ "}\n"
		+ "\n"
		+ "void main() {\n"
		+ "\tvec3 normal = normalize(vEyeNormal);\n"
		+ "\tfloat occlusion = 1.0;\n"
		+ "\tif (uAoEnabled != 0 && uShadowEnabled != 0) {\n"
		+ "\t\tvec4 projected = shadowProjection();\n"
		+ "\t\tvec3 coord = projected.xyz / projected.w;\n"
		+ "\t\tif (coord.x >= 0.0 && coord.x <= 1.0 && coord.y >= 0.0 && coord.y <= 1.0 && coord.z <= 1.0) {\n"
		+ "\t\t\tocclusion = 1.0 - uAoStrength * (1.0 - sunOcclusion(coord));\n"
		+ "\t\t}\n"
		+ "\t}\n"
		+ "\t// The stock ambient is the level light tinted by the sun's colour. A\n"
		+ "\t// surface facing the sky takes the same level in the sky's colour, which\n"
		+ "\t// is the environment datum fixed function never lights with; a surface\n"
		+ "\t// facing away keeps the stock value, because this client's environment\n"
		+ "\t// table has no ground colour to give it. Off is zero - GL's initial\n"
		+ "\t// value - so the stock expression stands unchanged.\n"
		+ "\tvec3 ambientLight = gl_LightModel.ambient.rgb;\n"
		+ "\tif (uEnvLighting != 0) {\n"
		+ "\t\tfloat skyWeight = clamp(dot(normal, normalize(uEnvUp.xyz)), 0.0, 1.0);\n"
		+ "\t\tambientLight = mix(ambientLight, uEnvSky.rgb * uEnvSky.a, skyWeight);\n"
		+ "\t}\n"
		+ "\tvec3 lighting = ambientLight * occlusion;\n"
		+ "\tfloat shadow = sunShadow();\n"
		+ "\t// Night keeps only a fraction of it: 1.0 is the sample untouched, so\n"
		+ "\t// the branch is skipped for a full-strength sun.\n"
		+ "\tif (uShadowStrength < 1.0) {\n"
		+ "\t\tshadow = 1.0 - uShadowStrength * (1.0 - shadow);\n"
		+ "\t}\n"
		+ "\t// Only the sun casts; the anti-sun is a fill light and point lights are local.\n"
		+ "\tlighting += gl_LightSource[0].diffuse.rgb * max(dot(normal, normalize(gl_LightSource[0].position.xyz)), 0.0) * shadow;\n"
		+ "\tlighting += gl_LightSource[1].diffuse.rgb * max(dot(normal, normalize(gl_LightSource[1].position.xyz)), 0.0);\n"
		+ "\tfor (int i = 2; i < 6; i++) {\n"
		+ "\t\tif (i - 2 >= uPointLightCount) {\n"
		+ "\t\t\tbreak;\n"
		+ "\t\t}\n"
		+ "\t\tvec3 lightAt = uSceneSpace != 0 ? vWorldPosition : vEyePosition;\n"
		+ "\t\tvec3 toLight = gl_LightSource[i].position.xyz - lightAt;\n"
		+ "\t\tfloat distanceSquared = max(dot(toLight, toLight), 1.0e-8);\n"
		+ "\t\tfloat distance = sqrt(distanceSquared);\n"
		+ "\t\tvec3 direction = toLight / distance;\n"
		+ "\t\tfloat attenuation = 1.0 / (gl_LightSource[i].constantAttenuation\n"
		+ "\t\t\t+ gl_LightSource[i].linearAttenuation * distance\n"
		+ "\t\t\t+ gl_LightSource[i].quadraticAttenuation * distanceSquared);\n"
		+ "\t\tlighting += gl_LightSource[i].diffuse.rgb * (max(dot(normal, direction), 0.0) * attenuation);\n"
		+ "\t}\n"
		+ "\tlighting += carriedLight();\n"
		+ "\tlighting += roomLight();\n"
		+ "\tlighting += causticsTerm();\n"
		+ "\tvec4 albedo = gl_Color;\n"
		+ "\t// The ground atlas path: the rows and a registered material. Anything\n"
		+ "\t// else - every model draw - keeps the bound material texture below.\n"
		+ "\tif (uWorldTextures != 0 && uMaterialMaps != 0 && uTerrainBlend.x >= 0.0) {\n"
		+ "\t\t// The whole texel, alpha included: the page holds the art the stock\n"
		+ "\t\t// upload would have bound - HdMaterialArt keeps an alpha channel\n"
		+ "\t\t// only where that upload does - so modulating RGBA here is the\n"
		+ "\t\t// draw the fixed function makes. A cell is never empty either: the\n"
		+ "\t\t// material map only publishes a cell the page filled, because a\n"
		+ "\t\t// zeroed one's alpha would fall to this pass' GREATER alpha test\n"
		+ "\t\t// and leave a hole in the terrain where a dark tile belongs.\n"
		+ "\t\tvec4 ground = groundAlbedo();\n"
		+ "\t\t// A material the page does not hold - a frame that just filled the\n"
		+ "\t\t// page, or a cell recycled for a newer material - keeps the stock\n"
		+ "\t\t// draw. The layer's own texture is still bound on the base unit, so\n"
		+ "\t\t// this is the same texel fixed function would have sampled.\n"
		+ "\t\tif (ground.a < 0.0) {\n"
		+ "\t\t\tif (uTextured != 0) {\n"
		+ "\t\t\t\talbedo *= texture2D(uTexture, vTexCoord);\n"
		+ "\t\t\t}\n"
		+ "\t\t} else {\n"
		+ "\t\t\talbedo *= ground;\n"
		+ "\t\t}\n"
		+ "\t} else if (uTextured != 0) {\n"
		+ "\t\talbedo *= texture2D(uTexture, vTexCoord);\n"
		+ "\t}\n"
		+ "\tvec3 lit = clamp(albedo.rgb * lighting, 0.0, 1.0);\n"
		+ "\t// The stock specular effect adds its cube over the textured colour rather\n"
		+ "\t// than shading it, so a reflection is added here, past the lit texel.\n"
		+ "\tlit += reflectionTerm();\n"
		+ "\t// A fogless draw keeps the whole band: GL's fog state still holds the\n"
		+ "\t// last band the scene set, and applying it here would fog a draw the\n"
		+ "\t// stock path draws without fog.\n"
		+ "\tfloat fogFactor = 1.0;\n"
		+ "\tif (uFogOff == 0) {\n"
		+ "\t\tfogFactor = clamp((gl_Fog.end - abs(vEyePosition.z)) * gl_Fog.scale, 0.0, 1.0);\n"
		+ "\t}\n"
		+ "\tif (uFogMode != 0) {\n"
		+ "\t\t// Same ends, thicker interior: the stock linear fraction squared.\n"
		+ "\t\tfogFactor = fogFactor * fogFactor;\n"
		+ "\t}\n"
		+ "\tvec3 colour = mix(gl_Fog.color.rgb, lit, fogFactor);\n"
		+ "\tif (uDistanceFade != 0) {\n"
		+ "\t\t// The outer band of the projection's far plane, reaching the fog\n"
		+ "\t\t// colour exactly at the plane and leaving anything nearer alone.\n"
		+ "\t\tfloat farPlane = max(vFarPlane, 1.0);\n"
		+ "\t\tfloat fadeStart = farPlane * (1.0 - uFadeBand);\n"
		+ "\t\tfloat span = max(farPlane - fadeStart, 1.0e-4);\n"
		+ "\t\tcolour = mix(gl_Fog.color.rgb, colour, clamp((farPlane - abs(vEyePosition.z)) / span, 0.0, 1.0));\n"
		+ "\t}\n"
		+ "\tgl_FragColor = vec4(colour, albedo.a);\n"
		+ "}\n";

	private HdShaderSource() {
	}

	/**
	 * Every fragment uniform the pipeline must be able to locate, in declaration
	 * order. The vegetation block is not here: it belongs to the vertex stage,
	 * which is the only place a vertex can be moved, and is listed in
	 * {@link #vertexUniforms()} instead.
	 */
	public static String[] uniforms() {
		return UNIFORMS.clone();
	}

	/** Every vertex uniform, which the pipeline also has to locate. */
	public static String[] vertexUniforms() {
		return VERTEX_UNIFORMS.clone();
	}
}
