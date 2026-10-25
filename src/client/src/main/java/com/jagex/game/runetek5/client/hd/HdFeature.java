package com.jagex.game.runetek5.client.hd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Vocabulary for one 592HD setting row.
 *
 * <p>This enum is the single source of truth for the pane: its tab, label,
 * value labels, the stock state it drives (if any) and whether that state
 * exists in this client yet. Presets, persistence and the pane all iterate the
 * same list, so a row can never appear in one and be missing from another.
 *
 * <p>Values are stored as the index into {@link #values()}; the labels are only
 * for display, which keeps a saved file valid when a label is reworded.
 */
public enum HdFeature {

	// -- Lighting ---------------------------------------------------------

	/**
	 * The HD fog band in the per-pixel shader: the stock linear band with a
	 * smoothstep shoulder, so the band's ends are unchanged and only its interior
	 * is denser. Off is the stock band, pixel for pixel.
	 *
	 * <p>It exists only inside the per-pixel program. With Per-pixel lighting off
	 * there is no HD fragment stage to evaluate it, the stock {@code GL_LINEAR}
	 * band draws, and the row has nothing to act in - which is why no shipped
	 * preset lists it next to a disabled program.
	 */
	FOG("fog", HdTab.LIGHTING, "Fog", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * The 592HD sky dome: a per-pixel dome evaluated from the frame's own camera,
	 * sun and fog colour, drawn as the background before world geometry. Off is
	 * no draw at all, so the frame is the stock clear. A map that brought its own
	 * cubemap sky keeps it - the dome fills the flat fog slot rather than painting
	 * over a sky the world already has. See {@code HdSkyPipeline}.
	 */
	SKY("sky", HdTab.LIGHTING, "Sky", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * The stock 592 scenery-shadow level ({@code Class50.method2852}), not the
	 * 592HD sun map - that is {@link #SHADOW_MAP}. It is live and reversible
	 * because the stock setting is, and it stays in the pane because it is a real
	 * graphics option; it is not upstream 634HD's shadow row.
	 */
	SHADOWS("shadows", HdTab.LIGHTING, "Stock scenery shadows", HdStockTarget.SHADOWS, HdSupport.READY, false,
		"Off", "Static", "Dynamic"),

	/**
	 * Cut-out casters: the sun's depth pass alpha-tests each caster's own
	 * texture, so leaves, fences and grilles cast their shape instead of a
	 * solid block. Ported from 634HD's {@code cutoutShadows}; it only exists
	 * inside a sun shadow pass, and Off leaves the pass exactly as it was.
	 * See {@code ShadowCutout}.
	 */
	CUTOUT_SHADOWS("cutoutShadows", HdTab.LIGHTING, "Cut-out shadows", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * Resolution of the sun's depth map, rebuilt per frame. The renderer reads
	 * this live: Off means no pass, no target and no texture unit change.
	 */
	SHADOW_MAP("shadowMap", HdTab.LIGHTING, "Sun shadows", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "1024", "2048", "4096"),

	/**
	 * Port-only control of the sun-shadow PCF kernel: Low reads 3x3, High 5x5.
	 * It is not one of 634HD's rows - upstream fixes the kernel - and it has no
	 * effect without Sun shadows, because there is no map to filter.
	 */
	LIGHTING_DETAIL("lightingDetail", HdTab.LIGHTING, "Shadow detail", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Low", "High"),

	PER_PIXEL_LIGHTING("perPixelLighting", HdTab.LIGHTING, "Per-pixel lighting", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * The term-sum lit model - the mapped normal under the sun with shadows, a
	 * sky/ground hemisphere, the occlusion map and roughness highlights - after
	 * 634HD's {@code lightingModel}.
	 *
	 * <p>It is not honoured, and the blocker is content rather than shader code.
	 * 634HD takes the surface response from its material maps (normal,
	 * occlusion/roughness, emissive, height); this client has none, and
	 * {@code MaterialType} - the only material record the cache carries - holds
	 * texture-generation parameters, with no specular strength, exponent or
	 * roughness anywhere in it (see that class's documentation). The fixed
	 * "specular" this client does have is the stock environment cube map, a
	 * reflection selected by a texture effect, not a per-material response to
	 * the sun. A highlight built here today would have to invent both a strength
	 * and an exponent, so the row stays stored and marked instead; it is
	 * unblocked by {@link #MATERIAL_MAPS} and the content behind it, not by a
	 * renderer-only change.
	 */
	LIGHTING_MODEL("lightingModel", HdTab.LIGHTING, "Lighting model", HdStockTarget.RENDERER, HdSupport.PENDING_RENDERER, false,
		"Off", "On"),

	/**
	 * The scene's stock point lights evaluated per pixel instead of per vertex.
	 * The term lives in the per-pixel program, so with Per-pixel lighting off the
	 * stock vertex lighting stands and the row does nothing.
	 */
	POINT_LIGHTS("pointLights", HdTab.LIGHTING, "Point lights", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * The light the local player carries: the strongest map light already
	 * registered on the carrier's tile, re-anchored at the player. Off is no
	 * term at all. See {@code HdLightFrame}.
	 */
	CARRIED_LIGHT("carriedLight", HdTab.LIGHTING, "Carried light", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * The per-region room light: each eight-by-eight map chunk's own ambient,
	 * tinted by the map lights registered on its tiles and bounded to that chunk
	 * so it cannot reach its neighbour. Off is no term at all. See
	 * {@code HdLightFrame}.
	 */
	ROOM_LIGHT("roomLight", HdTab.LIGHTING, "Room light", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * Contact darkening of the ambient term, read from the sun's depth map with a
	 * coarse wide-biased kernel. This stands in for 634HD's ambient occlusion,
	 * which is <em>baked</em> into the material maps: this client has no occlusion
	 * map and no depth prepass, so the sun's map is the only occluder source there
	 * is. The row is named for what it draws, not for the technique it replaces.
	 * With Sun shadows off there is no map bound and it is a uniform-level no-op;
	 * with Per-pixel lighting off it has no program at all.
	 */
	AMBIENT_OCCLUSION("ambientOcclusion", HdTab.LIGHTING, "Contact occlusion", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * Fades the outer band of the projection's far plane into the fog colour,
	 * reaching it exactly at the plane and leaving nearer geometry untouched.
	 * Like the fog band it is evaluated in the per-pixel program, so it needs
	 * Per-pixel lighting; with that off the stock draw stands unchanged.
	 */
	DISTANCE_FADE("distanceFade", HdTab.LIGHTING, "Distance fade", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * The world's clock: how long one full day takes. It scales the stock light
	 * the scene is drawn with - the sun's colour, the fill light, the global
	 * ambient, the fog colour and how much of the sun's shadow survives - and it
	 * recolours the sky dome when Sky is on. It therefore changes the world with
	 * Sky off, which is why it is a row of its own. Off stops the clock where it
	 * is, and every factor is then exactly 1. See {@code HdDayNight}.
	 */
	DAY_NIGHT("dayNight", HdTab.LIGHTING, "Day and night", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "Clock", "10 min", "30 min", "60 min"),

	/**
	 * The environment's own light tinting the per-pixel ambient, after 634HD's
	 * {@code environmentLighting}. It reads the environment the client already
	 * uploaded - the sky/fog colour, the level ambient, and the camera's sky axis -
	 * so a surface facing the sky takes the ambient in the colour of the sky above
	 * it instead of the sun-tinted stock value, and the scene changes with the
	 * area it is in. The tint is <b>ambient only</b>: the sun keeps the
	 * environment's own upload, and this client's environment table has no ground
	 * colour, so downward faces keep the stock ambient. Off is the stock ambient,
	 * pixel for pixel. Needs Per-pixel lighting, where the term lives.
	 * See {@code HdEnvironmentLight}.
	 */
	ENVIRONMENT_LIGHTING("environmentLighting", HdTab.LIGHTING, "Environment light", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	// -- World ------------------------------------------------------------

	TEXTURES("textures", HdTab.WORLD, "Textures", HdStockTarget.TEXTURES, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * The stock per-tile material blend flag ({@code Class50.aBoolean289}), which
	 * pins each ground tile to one material. Upstream's {@code terrainBlend}
	 * blends several materials through a noise mask with triplanar mapping; that
	 * is {@link #TERRAIN_BLEND}, and it is pending. This row is the stock setting
	 * under a name that says so, not parity.
	 */
	GROUND_BLENDING("groundBlending", HdTab.WORLD, "Stock ground blending", HdStockTarget.GROUND_BLENDING, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * Tile materials blended through a noise mask, with triplanar mapping on
	 * steep ground, ported from 634HD's {@code terrainBlend}.
	 *
	 * <p>The stock ground mesh draws one material per layer and the layers meet
	 * at tile edges, so a tile built from two materials shows the change as a
	 * hard line. This row mixes the tile's own two materials through a noise
	 * field anchored to the scene, so the change is a soft band that crosses the
	 * tile instead of following its edge; the two ids and a weight are read from
	 * the tile's texel in the mesh's material map (see
	 * {@code HdTileMaterialMap}), which is copied out of the layer stack before
	 * the mesh releases it. Steep ground takes the triplanar weights and flat
	 * ground the planar mapping the stock texture matrix uses.
	 *
	 * <p>It needs {@link #WORLD_TEXTURES} and {@link #MATERIAL_MAPS}: without the
	 * page there is no second texture to mix and without the map there is no
	 * cell to read it from. Off is the identity - a strength of exactly zero - so
	 * every layer draws its own material alone, the frame before the row
	 * existed. A tile with one material blends nothing either way.
	 */
	TERRAIN_BLEND("terrainBlend", HdTab.WORLD, "Terrain blend", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * Ground materials drawn from one world-texture page instead of one bound
	 * texture per layer draw.
	 *
	 * <p>The page is the same art the stock draw binds - each material's pixels
	 * come from the client's own generator - packed into a 512x512 atlas of 64
	 * cells (see {@code HdWorldAtlas}). A layer draw selects its cell through the
	 * material map, so one draw can sample any material the page holds, which is
	 * what makes a per-tile material pair reachable at all. Ground the page
	 * cannot serve (a material that never streamed in, or a 65th material) keeps
	 * its stock texture.
	 *
	 * <p>Off is the identity: the shader samples the bound {@code uTexture} exactly
	 * as before and the page is never built.
	 */
	WORLD_TEXTURES("worldTextures", HdTab.WORLD, "World textures", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * A texture of per-material data the ground shader samples: one texel per
	 * material id, {@code (cell + 1, brightness, blend, 255)}, built from the
	 * client's {@code MaterialType} records (see {@code HdMaterialMap}).
	 *
	 * <p>The stock path bakes the two parameters into vertex colours before the
	 * draw, which a shader cannot recover and which a tile with two materials
	 * would need twice. Putting them in a map lets the fragment stage apply each
	 * material's own brightness and blend at the texel it shades, and the same
	 * red channel is what turns a material id into a world-texture cell.
	 *
	 * <p>Off is the identity: no map is built, the shader never samples it and the
	 * stock single-texture draw runs.
	 */
	MATERIAL_MAPS("materialMaps", HdTab.WORLD, "Material maps", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * Replacement albedo art, ported from 634HD's {@code textureReplacements}:
	 * a cached world texture drawn from a newer authored image. This client
	 * ships no replacement set and has nowhere to load one from, so the row is
	 * inert rather than half-wired.
	 */
	TEXTURE_REPLACEMENTS("textureReplacements", HdTab.WORLD, "Texture replacements", HdStockTarget.RENDERER, HdSupport.UNAVAILABLE, false,
		"Off", "On"),

	/**
	 * The client's water presence, kept only to mirror upstream's row list. This
	 * client has no switch that suppresses water and no second stock mode: it
	 * always draws its own water, and the working upgrade is {@link #WATER_HD}.
	 * Both values do nothing, so the row is marked n/a rather than pending - there
	 * is no renderer work that would make Off mean anything.
	 */
	WATER("water", HdTab.WORLD, "Water", HdStockTarget.RENDERER, HdSupport.UNAVAILABLE, false,
		"Always"),

	/**
	 * The 592HD water program in the stock water effect slot. Off is the stock
	 * water draw, pixel for pixel: the renderer reads this live, and the stock
	 * selection runs untouched while it is off.
	 *
	 * <p>Coverage is the calm-water slot ({@code StillWaterEffect}), the reflective
	 * water type ({@code ReflectiveRipplingWaterEffect}'s stock effect slot) and
	 * the breaking water type ({@code BreakingWaterEffect}'s slot). A reflective
	 * water material makes the draw sample the environment cube the stock cube
	 * effects already bind, so the surface reflects the sky it sits under and
	 * follows a repainted sky; a calm one keeps the program and the image it had
	 * before this row grew the second half. A breaking material runs its own
	 * surface program, whose band is driven by that material's two effect
	 * parameter fields - its ripple scale, its animation rate on this row's
	 * clock, and its break depth, shore offset, crest colour and sharpening
	 * exponent - decoded exactly as the stock effect decodes them (see
	 * {@code HdWaterBreaking}). The {@link #REFLECTIONS} row still owns
	 * reflections on models and the rest of the scene.
	 *
	 * <p>The breaking band is a function of the per-vertex water depth the stock
	 * path already supplies ({@code gl_MultiTexCoord0.z}), like the stock effect
	 * itself: a water surface drawn without that value - an open lake plane, as
	 * opposed to submerged ground - has no shore to break against and the
	 * program reduces to its reflective stage. That is a property of the data,
	 * not of the program; where the client supplies the depth the band appears,
	 * and where it does not the surface is honest about it.
	 *
	 * <p>The third value, Custom, is a second surface program for calm water: a
	 * third diagonal wave train, absorption between a deep and a shallow colour,
	 * a sharper Fresnel and sun sparkles on the crests. It is a look, not a new
	 * capability - the reflective and breaking water types keep their own
	 * programs whatever the value, because those belong to the material and not
	 * to the surface, and a driver that refuses the custom program falls back to
	 * the standard surface.
	 */
	WATER_HD("waterHd", HdTab.WORLD, "592HD water", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On", "Custom"),

	/**
	 * Environment reflections on surfaces the client already draws reflectively.
	 *
	 * <p>The fragment stage samples the client's own environment cube - the one
	 * the reflective water program already reads - with this fragment's view
	 * vector, so the surface tracks the sky as the camera moves. Which materials
	 * reflect is not a new decision: the material's stock texture-effect slot is
	 * the client's own answer, and only the slot {@code FixedFunctionSpecularPass}
	 * occupies ({@code HdReflection.SPECULAR_SLOT}) is a reflection of the
	 * environment rather than a light response. Those draws are taken from the
	 * stock specular effect and drawn by the lit program instead; every other
	 * material keeps the stock pass, row or no row.
	 *
	 * <p>Off is the identity: no stock selection changes and the shader's strength
	 * is exactly {@code 0.0F}, GL's initial uniform value, so the frame is the one
	 * before the row existed.
	 *
	 * <p>What it does not cover: the shader reproduces {@code GL_MODULATE}, so a
	 * reflective material whose texture environment is not MODULATE keeps its
	 * stock effect even with the row on - the row never changes a blend mode to
	 * get a reflection. Needs Per-pixel lighting, where the fragment stage lives.
	 */
	REFLECTIONS("reflections", HdTab.WORLD, "Reflections", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * Light patterns on submerged ground, ported from 634HD's {@code caustics}.
	 *
	 * <p>The term lives in the per-pixel fragment stage, in scene space, driven by
	 * two things the client already has: the water clock
	 * ({@code HdWaterTime}) and each tile's depth byte. The depth is the stock
	 * value - {@code Terrain} hands {@code GlGroundLayer} a 0..255 byte per tile
	 * for an underwater region, and the stock {@code StillWaterEffect} uses it as
	 * {@code depth / 255} to fade the submerged look in. {@code HdGroundWater}
	 * carries it; {@code HdCaustics} turns it into this draw's strength.
	 *
	 * <p>Off is the identity, not a faint pattern: no row, or a tile with no water
	 * over it, resolves to a strength of exactly {@code 0.0F} - GL's initial
	 * uniform value - so the pipeline uploads nothing and the fragment stage takes
	 * no branch. The row is read live at draw time, so switching it needs no
	 * restart.
	 *
	 * <p>What it does not cover: a lake or an ocean in an ordinary region draws its
	 * surface as a separate water object and leaves every tile's depth byte at
	 * zero, because the client only fills those arrays for regions flagged
	 * {@code underwater}. Caustics therefore appear on the ground the player is
	 * standing under water on, and not under a surface lake - an honest limit of
	 * the data rather than of the shader. Needs Per-pixel lighting, where the
	 * fragment stage lives.
	 */
	CAUSTICS("caustics", HdTab.WORLD, "Caustics", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * Plants leaning with the frame's wind, in the per-pixel program's vertex
	 * stage. The offset is a height-squared lever from the model's own base, so
	 * the roots stay planted and the crown travels; it is phased by the plant's
	 * world position, so a copse is not one rigid sheet. Off contributes an
	 * amplitude of exactly {@code 0.0F} and the shader takes the stock
	 * {@code ftransform} path, so the row is pixel-identical to its absence.
	 * Only draws the scene marked as vegetation move (see
	 * {@code HdVegetation}); needs Per-pixel lighting, where the stage lives.
	 *
	 * <p>One thing the row does <em>not</em> do: the sun's depth pass keeps every
	 * caster at its rest position. The shadow map is rendered by a separate pass
	 * with its own program, and displacing only the colour pass would make a
	 * leaning plant cast a shadow of the plant it used to be. Rest-position
	 * casters are the honest choice until both passes share the displacement.
	 *
	 * <p>The row moves the vertex, not the model: picking, click boxes and
	 * occlusion stay where the model was submitted, which is within one lean of
	 * the plant's base. The normal is left at its rest direction too, so the
	 * shading does not follow the lean - a sheared crown still lights as if it
	 * stood upright. Rotating it would need the offset's derivative in eye space
	 * for every vertex; at these amplitudes the error is a few degrees and the
	 * row is worth having without it.
	 */
	FOLIAGE_SWAY("foliageSway", HdTab.WORLD, "Foliage sway", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * Ground plants parting around the viewer and the actors near them, on the
	 * same vertex mechanism as {@link #FOLIAGE_SWAY} and summed with it when both
	 * are on. The push is away from the viewer's eye - the one position the
	 * renderer can read from the frame's own camera without inventing actor
	 * state - with compact support, so it reaches exactly zero at its radius and
	 * nothing outside it moves. Off contributes an amplitude of exactly
	 * {@code 0.0F}. Needs Per-pixel lighting, where the stage lives.
	 */
	GRASS_DISPLACEMENT("grassDisplacement", HdTab.WORLD, "Grass bends", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	// -- Effects ----------------------------------------------------------

	/**
	 * The stock 592 bloom compositor's level ({@code HashTable.method2673}). It is
	 * live and reversible with the stock setting; 634HD's own bloom is a separate
	 * post-process that has not landed, so this is the stock effect under a name
	 * that says so.
	 */
	BLOOM("bloom", HdTab.EFFECTS, "Stock bloom", HdStockTarget.BLOOM, HdSupport.READY, false,
		"Off", "Low", "High"),

	/**
	 * The screen-space pass's filmic curve, ACES only. The frame it reads is the
	 * toolkit's 8-bit colour, not a linear half-float target, so it shapes the
	 * existing frame rather than recovering highlights: upstream's HDR path and
	 * its AgX choice have not landed. See {@code HdPostSource}.
	 */
	TONEMAP("tonemap", HdTab.EFFECTS, "Tonemapping", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "ACES"),

	COLOUR_GRADING("colourGrading", HdTab.EFFECTS, "Colour grading", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * FXAA on the finished frame. The pass is hooked at presentation, so it sees
	 * everything the window holds - the world, the interface and any composition
	 * over them - and filters text along with the scene. That is a consequence of
	 * where this renderer can still reach, not a claim about upstream.
	 */
	FXAA("fxaa", HdTab.EFFECTS, "FXAA", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * A mip chain for world textures. Read live where a texture is handed out;
	 * Off drops the chain the row generated and reinstates the texture's stock
	 * filter, so the row is reversible like the rest.
	 */
	MIPMAPS("mipmaps", HdTab.EFFECTS, "Mipmaps", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	/**
	 * Anisotropic sampling of world textures; only meaningful with Mipmaps on and
	 * a driver that advertises the extension. Off returns the parameter to GL's
	 * 1x on the next hand-out, so the row is reversible like the rest.
	 */
	ANISOTROPIC("anisotropic", HdTab.EFFECTS, "Anisotropic filtering", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "2x", "4x", "8x", "16x"),

	DITHER("dither", HdTab.EFFECTS, "Dither", HdStockTarget.RENDERER, HdSupport.READY, false,
		"Off", "On"),

	SOFT_PARTICLES("softParticles", HdTab.EFFECTS, "Soft particles", HdStockTarget.RENDERER, HdSupport.PENDING_RENDERER, false,
		"Off", "On"),

	/**
	 * Inert: nothing in this client's particle data names an ember, a spark or a
	 * flame, so the row has no system to bind to. It is marked unsupported
	 * rather than guessing at one.
	 *
	 * <p>The evidence is the particle model this client already decodes. An
	 * emitter is an {@code EmissiveTriangle}: a producer id and a priority byte,
	 * with the producer looked up by that id. {@code ParticleProducerType} is
	 * numbers only - a colour ramp packed into ints, spawn colours, sizes,
	 * speeds, angles, lifetimes, a material id and a handful of flags - so no
	 * producer carries a kind. {@code ParticleType} classifies only how a
	 * particle is <em>handled</em> ({@code particleHandlingType}: attached to a
	 * model's emitter face, or driven by an effector), never what it looks like.
	 * Systems themselves are made per entity - a loc, a spot animation on a
	 * player or NPC, a projectile - so their spawn origin and clock are known,
	 * but the difference between the embers of a fire and the smoke above them
	 * lives in the artwork a producer id points at, not in any field.
	 *
	 * <p>A renderer that picked particles by warm colour ramp or upward speed
	 * would be classifying art, which is why this row does not: it stays off,
	 * never reaches a draw decision, and the pane shows it as unsupported. It
	 * becomes implementable when the data names one - a producer-level kind in
	 * the cache - which is what a cache-backed session would have to establish.
	 */
	EMBERS("embers", HdTab.EFFECTS, "Embers and sparks", HdStockTarget.RENDERER, HdSupport.UNAVAILABLE, false,
		"Off", "On"),

	// -- Display ----------------------------------------------------------

	/**
	 * Percent of the stock view range. The values are indices into
	 * {@link HdRenderDistance#PERCENT}, and every percent is a legal
	 * {@code FogDistance} level today.
	 */
	RENDER_DISTANCE("renderDistance", HdTab.DISPLAY, "Draw distance", HdStockTarget.RENDER_DISTANCE, HdSupport.READY, false,
		"100%", "125%", "140%", "165%"),

	ANTI_ALIASING("antiAliasing", HdTab.DISPLAY, "Anti-aliasing", HdStockTarget.ANTI_ALIASING, HdSupport.READY, true,
		"Off", "2x", "4x"),

	INTERFACE_SCALE("interfaceScale", HdTab.DISPLAY, "Interface scale", HdStockTarget.RENDERER, HdSupport.UNAVAILABLE, true,
		"Auto", "2x", "3x"),

	/**
	 * Edge-directed upscaling of the interface's sprites and text, ported from
	 * 634HD's {@code smoothInterface}. It is a property of a scaled interface,
	 * and this client's interface scale is UNAVAILABLE, so the row is inert.
	 */
	SMOOTH_INTERFACE("smoothInterface", HdTab.DISPLAY, "Smooth interface", HdStockTarget.RENDERER, HdSupport.UNAVAILABLE, true,
		"Off", "On");

	private final String key;

	private final HdTab tab;

	private final String label;

	private final HdStockTarget target;

	private final HdSupport support;

	private final boolean restartRequired;

	private final String[] values;

	HdFeature(String key, HdTab tab, String label, HdStockTarget target, HdSupport support,
			boolean restartRequired, String... values) {
		this.key = key;
		this.tab = tab;
		this.label = label;
		this.target = target;
		this.support = support;
		this.restartRequired = restartRequired;
		this.values = values;
	}

	/** Stable key written to the properties file. Never change it casually. */
	public String key() {
		return this.key;
	}

	public HdTab tab() {
		return this.tab;
	}

	public String label() {
		return this.label;
	}

	public HdStockTarget target() {
		return this.target;
	}

	/**
	 * True when this row drives an existing stock graphics flag rather than the
	 * 592HD renderer. Rows owned by the renderer are read live at draw time, so
	 * they are never pushed through {@link HdGraphicsBackend}.
	 */
	public boolean appliesToStock() {
		return this.target != HdStockTarget.RENDERER;
	}

	public HdSupport support() {
		return this.support;
	}

	public boolean restartRequired() {
		return this.restartRequired;
	}

	public int valueCount() {
		return this.values.length;
	}

	public String valueLabel(int value) {
		if (value < 0 || value >= this.values.length) {
			return this.values[0];
		}
		return this.values[value];
	}

	/** The value shown as the feature's default when no preset sets it. */
	public int defaultValue() {
		return 0;
	}

	/** Every row of one tab, in pane order. */
	public static HdFeature[] forTab(HdTab tab) {
		List<HdFeature> rows = new ArrayList<HdFeature>();
		for (HdFeature feature : values()) {
			if (feature.tab == tab) {
				rows.add(feature);
			}
		}
		return rows.toArray(new HdFeature[rows.size()]);
	}

	public static HdFeature forKey(String key) {
		for (HdFeature feature : values()) {
			if (feature.key.equals(key)) {
				return feature;
			}
		}
		return null;
	}

	/** Unmodifiable order, used by the persistence layer and tests. */
	public static List<HdFeature> all() {
		return Collections.unmodifiableList(java.util.Arrays.asList(values()));
	}
}
