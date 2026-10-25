package com.jagex.game.runetek5.client.hd.shader;

import com.jagex.game.runetek5.client.hd.atmosphere.HdAtmosphere;
import com.jagex.game.runetek5.client.hd.light.HdEnvironmentLight;
import com.jagex.game.runetek5.client.hd.light.HdLightFrame;
import com.jagex.game.runetek5.client.hd.light.HdLightGate;
import com.jagex.game.runetek5.client.hd.light.HdLightSource;
import com.jagex.game.runetek5.client.hd.light.HdRoomLight;
import com.jagex.game.runetek5.client.hd.ground.HdGroundTextures;
import com.jagex.game.runetek5.client.hd.ground.HdTileMaterialMap;
import com.jagex.game.runetek5.client.hd.reflect.HdReflection;
import com.jagex.game.runetek5.client.hd.shadow.ShadowBinding;
import com.jagex.game.runetek5.client.hd.sway.HdSway;
import com.jagex.game.runetek5.client.hd.water.HdCaustics;

/**
 * Owns the per-pixel lighting program for one GL context and the bind/unbind
 * cycle around it.
 *
 * <p>Lifetime: the pipeline is created and destroyed by its toolkit
 * ({@code GlToolkit.stop()}), so it cannot outlive the context its program
 * belongs to. Everything in this class is mechanical - link, upload, bind,
 * unbind; the decision of whether a draw may use it belongs to
 * {@link HdLightingGate}.
 *
 * <ul>
 * <li>{@link #prepare()} compiles once. A failure is remembered and logged
 * once, not retried: a driver that rejects the shader must not be asked again
 * every frame.</li>
 * <li>{@link #begin(HdDrawRequest, HdDrawSpace, HdFrameState)} evaluates the
 * gate, compiles on first demand, binds, and uploads each value only when it
 * changed. The sampler unit is uploaded once per program, not per bind.</li>
 * <li>{@link #unbind()} returns the context to fixed function
 * ({@code glUseProgram(0)}). The stock path draws with no program bound, so this
 * happens after a single draw rather than around the whole model.</li>
 * <li>{@link #shutdown()} unbinds and deletes the program; the toolkit calls it
 * before releasing the context.</li>
 * </ul>
 *
 * <p>Uniform values live in the program object, so skipping an upload is safe:
 * nothing else in this client binds this program.
 *
 * <p>The draw is factored into three values rather than a parameter list: the
 * request (what may bind), {@link HdDrawSpace} (where its vertices are) and
 * {@link HdFrameState} (the frame's values). Only {@code request} varies per
 * primitive in the ordinary case; the frame's half is assembled once, where the
 * frame's state lives.
 */
public final class HdLightingPipeline {

	private final HdShaderApi api;

	private HdProgram program;

	private boolean attempted;

	private boolean reported;

	private String failure = "";

	private HdDrawState uploaded;

	private boolean samplerBound;

	private boolean shadowSamplerBound;

	private int uploadedShadowEnabled = -1;

	private int uploadedShadowRadius = -1;

	/** NaN until the first bind: GL initialises the uniform to 0, which is no shadow. */
	private float uploadedShadowStrength = Float.NaN;

	private int uploadedShadowFrame = -1;

	private HdAtmosphere uploadedAtmosphere = HdAtmosphere.OFF;

	/** Null until the first bind: identity is not what GL's zeroed uniforms describe. */
	private float[] uploadedModel;

	private float[] uploadedEye;

	private int uploadedSceneSpace = -1;

	/** 0 is GL's initial value and means "apply the band", so a fogged draw uploads nothing. */
	private int uploadedFogOff;

	/** Carried light: the flag, then the values it gates. */
	private boolean uploadedCarriedEnabled;

	private HdLightSource uploadedCarried;

	/** Room light: the flag, then all nine region slots (unused ones zeroed). */
	private boolean uploadedRoomEnabled;

	private HdRoomLight[] uploadedRegions;

	/**
	 * Environment light: -1 until the first draw decides, then 0 or 1. The
	 * values are only cached while the flag is on, so a row that was never used
	 * leaves GL's zeroed uniforms alone.
	 */
	private int uploadedEnvironmentEnabled = -1;

	private HdEnvironmentLight uploadedEnvironment = HdEnvironmentLight.OFF;

	/**
	 * Vegetation: the draw's kind, then the displacement block. Both are zero at
	 * GL's initial value, so a frame with the rows off issues no call at all.
	 */
	private int uploadedVegetation;

	private HdSway uploadedSway = HdSway.OFF;

	/**
	 * The caustics term: strength and water clock. OFF is also GL's initial value,
	 * so a row that was never on uploads nothing and a row that goes off uploads
	 * exactly one clearing value.
	 */
	private HdCaustics uploadedCaustics = HdCaustics.OFF;

	/**
	 * The environment reflection: a strength and the cube sampler that rides it.
	 * OFF is GL's initial value, so a draw that does not reflect uploads nothing.
	 */
	private HdReflection uploadedReflection = HdReflection.OFF;

	private long lightUploads;

	private long swayUploads;

	private long causticsUploads;

	private long reflectionUploads;

	/** The ground-texture row state and the three values that describe a ground draw. */
	private int uploadedWorldTextures = -1;

	private int uploadedMaterialMaps = -1;

	private float[] uploadedTerrainBlend;

	private float uploadedWorldRepeat = Float.NaN;

	private long groundTextureUploads;

	private final float[] identity = { 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F,
		0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F };

	private boolean bound;

	private long binds;

	public HdLightingPipeline(HdShaderApi api) {
		this.api = api;
	}

	/** True once the lighting program has linked. Never compiles. */
	public boolean available() {
		return this.program != null && this.program.usable();
	}

	/**
	 * The GL program name this pass has bound, or 0 in fixed function.
	 *
	 * <p>This is what the toolkit's state cache reports for the bound program:
	 * the pass is the only thing that binds it, so the pass is the only thing
	 * that can say which one GL has. Zero while unbound, which is the fixed
	 * function every stock draw runs in.
	 */
	public int programName() {
		return this.bound && this.program != null ? (int) this.program.handle() : 0;
	}

	/**
	 * The one-line report of a failed compile that was written to the client
	 * log, or "" when there is nothing to report.
	 */
	public String logLine() {
		return this.failure.isEmpty() ? "" : "[592HD] per-pixel model lighting unavailable - " + this.failure;
	}

	/** Compiles the program if that has not been attempted yet. */
	public boolean prepare() {
		if (this.attempted) {
			return available();
		}
		this.attempted = true;
		HdProgramCompiler.Result result = HdProgramCompiler.compileLighting(this.api);
		if (!result.succeeded()) {
			this.failure = result.failure();
			this.program = null;
			report();
			return false;
		}
		this.program = result.program();
		return true;
	}

	/**
	 * Binds the program for one primitive, if the gate allows it.
	 *
	 * <p>Three inputs, one per question a draw asks. {@code request} says what
	 * this primitive is and whether the rows are on for it; {@code space} says
	 * where its vertices are; {@code frame} is this frame's own half - the live
	 * point lights, the shadow map and the hour's strength, and the atmosphere,
	 * light, environment and displacement rows. The gate is evaluated once here,
	 * and nothing is compiled and no GL call is made when the row is off, which
	 * is what keeps a 592HD-off or rows-off frame identical to the stock frame.
	 *
	 * <p>Every upload is cached against the value last sent, so a repeated bind
	 * costs no GL call. The shadow map's matrix and texel are per frame, so they
	 * go up once per frame id; only the enable flag and the PCF radius ride the
	 * per-draw state. An unshadowed draw touches no shadow state at all - not the
	 * sampler unit, not the matrix, not the flag - which is what keeps the Sun
	 * shadows row's Off state identical to the frame before the stage existed,
	 * and keeps unit 1 (which the toolkit otherwise never selects) out of the
	 * draw.
	 *
	 * <p>{@code space}'s eye rows are consulted for every draw that supplies
	 * them, not only the terrain: a displaced vegetation vertex re-derives its
	 * eye position from those rows after the model path has taken the modelview
	 * route. They are the same {@code rotX(180) * V} the model modelview is built
	 * from, so the moved point lands exactly where the stock transform would have
	 * put it.
	 *
	 * @param request this draw's state and settings
	 * @param space   where this draw's vertices are; {@link HdDrawSpace#MODEL} for
	 *                the ordinary model path
	 * @param frame   this frame's values; {@link HdFrameState#OFF} for a frame
	 *                with every row off
	 * @return true when the program is bound and the caller must call {@link #unbind()}
	 */
	public boolean begin(HdDrawRequest request, HdDrawSpace space, HdFrameState frame) {
		if (HdLightingGate.wantsLighting(request)) {
			// Compile on first use; never reach GL at all when the row is off.
			prepare();
		}
		if (!HdLightingGate.shouldBind(request, available())) {
			return false;
		}
		HdDrawSpace vertices = space == null ? HdDrawSpace.MODEL : space;
		HdFrameState values = frame == null ? HdFrameState.OFF : frame;
		this.api.use(this.program.handle());
		if (!this.samplerBound) {
			this.program.bindSampler();
			// The reflection cube's unit is selected here, with the others, and
			// never moved again. GL forbids two sampler types from pointing at
			// one unit, so leaving a cube sampler at its initial unit 0 would
			// make every draw of this program an invalid operation, reflecting
			// or not.
			this.program.bindReflectionSampler();
			this.program.bindGroundSamplers();
			this.samplerBound = true;
		}
		HdDrawState state = HdLightingGate.drawState(request, values.pointLights(), values.shadow());
		if (this.uploaded == null || !this.uploaded.equals(state)) {
			this.api.uniform1i(this.program.uniform(HdShaderSource.UNIFORM_TEXTURED), state.textured() ? 1 : 0);
			this.api.uniform1i(this.program.uniform(HdShaderSource.UNIFORM_POINT_LIGHTS), state.pointLights());
			this.uploaded = state;
		}
		// A fogless draw keeps the whole band (uFogOff = 1); GL initialises the
		// uniform to 0, so the ordinary fogged draw issues no call here.
		int fogOff = request.fogEnabled() ? 0 : 1;
		if (this.uploadedFogOff != fogOff) {
			this.api.uniform1i(this.program.uniform(HdShaderSource.UNIFORM_FOG_OFF), fogOff);
			this.uploadedFogOff = fogOff;
		}
		int sceneFlag = vertices.sceneSpace() ? 1 : 0;
		if (this.uploadedSceneSpace != sceneFlag) {
			this.api.uniform1i(this.program.uniform(HdShaderSource.UNIFORM_SCENE_SPACE), sceneFlag);
			this.uploadedSceneSpace = sceneFlag;
		}
		float[] sceneToEye = vertices.sceneToEye();
		if (sceneToEye != null
			&& (this.uploadedEye == null || !java.util.Arrays.equals(sceneToEye, this.uploadedEye))) {
			for (int row = 0; row < 4; row++) {
				this.api.uniform4f(this.program.uniform(HdShaderSource.UNIFORM_EYE_ROWS[row]),
					sceneToEye[row], sceneToEye[4 + row], sceneToEye[8 + row], sceneToEye[12 + row]);
			}
			this.uploadedEye = sceneToEye.clone();
		}
		float[] model = vertices.modelToScene() == null ? this.identity : vertices.modelToScene();
		if (this.uploadedModel == null || !java.util.Arrays.equals(model, this.uploadedModel)) {
			for (int row = 0; row < 4; row++) {
				this.api.uniform4f(this.program.uniform(HdShaderSource.UNIFORM_MODEL_ROWS[row]),
					model[row], model[4 + row], model[8 + row], model[12 + row]);
			}
			this.uploadedModel = model.clone();
		}
		HdAtmosphere atmosphere = values.atmosphere();
		if (!atmosphere.equals(this.uploadedAtmosphere)) {
			uploadAtmosphere(atmosphere);
			this.uploadedAtmosphere = atmosphere;
		}
		uploadLights(request, values.lights());
		uploadEnvironment(request, values.environment());
		uploadSway(request, values.sway());
		uploadCaustics(request, values.waterSeconds());
		uploadReflection(request);
		uploadGroundTextures(request);
		if (state.shadowEnabled()) {
			uploadShadowEnabled(state.shadowRadius(), values.shadowStrength());
			ShadowBinding shadow = values.shadow();
			if (shadow != null && shadow.frameId() != this.uploadedShadowFrame) {
				uploadShadow(shadow);
				this.uploadedShadowFrame = shadow.frameId();
			}
		} else if (this.uploadedShadowEnabled == 1) {
			// One transition back to "lit": the flag must not stay 1 behind us.
			// A draw that never saw a map has nothing to clear, so the very first
			// unshadowed draw after the program linked issues nothing here.
			this.api.uniform1i(this.program.uniform(HdShaderSource.UNIFORM_SHADOW_ENABLED), 0);
			this.uploadedShadowEnabled = 0;
		}
		this.bound = true;
		this.binds++;
		return true;
	}

	/**
	 * How many primitives have been drawn with this program since it was
	 * created. This is the counter that answers "is the game path reaching this
	 * code at all": zero on a shadowed frame means the seam is never called.
	 */
	public long binds() {
		return this.binds;
	}

	/**
	 * Binds the shadow sampler and the enable flag, and the radius when it moved.
	 * The sampler is bound on first use rather than at every program bind, so a
	 * context that never renders a shadow never selects the shadow unit.
	 */
	private void uploadShadowEnabled(int radius, float strength) {
		if (!this.shadowSamplerBound) {
			this.program.bindShadowSampler();
			this.shadowSamplerBound = true;
		}
		if (this.uploadedShadowEnabled != 1) {
			this.api.uniform1i(this.program.uniform(HdShaderSource.UNIFORM_SHADOW_ENABLED), 1);
			this.uploadedShadowEnabled = 1;
		}
		if (this.uploadedShadowRadius != radius) {
			this.api.uniform1i(this.program.uniform(HdShaderSource.UNIFORM_SHADOW_RADIUS), radius);
			this.uploadedShadowRadius = radius;
		}
		// Only a shadowed draw needs it: with the map off the shader's sample is
		// already 1.0, so GL's zero leaves the arithmetic alone, and an unshadowed
		// primitive keeps touching no shadow state at all.
		if (this.uploadedShadowStrength != strength) {
			this.api.uniform1f(this.program.uniform(HdShaderSource.UNIFORM_SHADOW_STRENGTH), strength);
			this.uploadedShadowStrength = strength;
		}
	}

	/**
	 * Pushes the three atmosphere rows. All five uniforms go up together even
	 * when only one row moved: they are one small group and a partial upload
	 * would need five caches to save four driver calls.
	 */
	private void uploadAtmosphere(HdAtmosphere atmosphere) {
		this.api.uniform1i(this.program.uniform(HdShaderSource.UNIFORM_FOG_MODE), atmosphere.enhancedFog() ? 1 : 0);
		this.api.uniform1i(this.program.uniform(HdShaderSource.UNIFORM_DISTANCE_FADE), atmosphere.distanceFade() ? 1 : 0);
		this.api.uniform1f(this.program.uniform(HdShaderSource.UNIFORM_FADE_BAND), atmosphere.fadeBand());
		this.api.uniform1i(this.program.uniform(HdShaderSource.UNIFORM_AO_ENABLED), atmosphere.ambientOcclusion() ? 1 : 0);
		this.api.uniform1f(this.program.uniform(HdShaderSource.UNIFORM_AO_STRENGTH), atmosphere.aoStrength());
	}

	/**
	 * Pushes the carried and room light terms, and nothing when neither row has a
	 * source.
	 *
	 * <p>Both decisions come from the draw request, like every other row here:
	 * the request carries whether each row is on for this draw, so the pipeline
	 * never reads the saved settings and two draws are never coupled by global
	 * state. The frame only has to supply the values.
	 *
	 * <p>Three caches, because the two rows move independently: the carried
	 * light's flag and values, and the whole nine-slot region block. A region
	 * block that shrank zeroes its freed slots, so a region cannot linger after
	 * the player leaves its coverage - that is the "does not leak" guarantee on
	 * the upload side, matching the shader's rectangle test on the other side.
	 */
	private void uploadLights(HdDrawRequest request, HdLightFrame lights) {
		boolean carriedOn = request.carriedLight() && HdLightGate.carriedHasSource(lights.carried());
		if (carriedOn != this.uploadedCarriedEnabled) {
			this.api.uniform1i(this.program.uniform(HdShaderSource.UNIFORM_CARRIED_ENABLED), carriedOn ? 1 : 0);
			this.uploadedCarriedEnabled = carriedOn;
			this.lightUploads++;
		}
		if (carriedOn) {
			HdLightSource source = lights.carried();
			if (!source.equals(this.uploadedCarried)) {
				this.api.uniform4f(this.program.uniform(HdShaderSource.UNIFORM_CARRIED_POSITION),
					source.x(), source.y(), source.z(), source.radius());
				this.api.uniform4f(this.program.uniform(HdShaderSource.UNIFORM_CARRIED_COLOUR),
					source.red(), source.green(), source.blue(), source.intensity());
				this.uploadedCarried = source;
				this.lightUploads++;
			}
		}
		boolean roomOn = request.roomLight() && HdLightGate.roomHasSource(lights.regions());
		if (roomOn != this.uploadedRoomEnabled) {
			this.api.uniform1i(this.program.uniform(HdShaderSource.UNIFORM_ROOM_ENABLED), roomOn ? 1 : 0);
			this.uploadedRoomEnabled = roomOn;
			this.lightUploads++;
		}
		if (roomOn) {
			HdRoomLight[] regions = lights.regions();
			if (!java.util.Arrays.equals(regions, this.uploadedRegions)) {
				for (int i = 0; i < HdShaderSource.ROOM_REGIONS; i++) {
					HdRoomLight region = i < regions.length ? regions[i] : null;
					if (region == null) {
						// An empty rectangle: min == max contains no fragment.
						this.api.uniform4f(this.program.uniform(HdShaderSource.UNIFORM_ROOM_BOUNDS[i]),
							0.0F, 0.0F, 0.0F, 0.0F);
						this.api.uniform4f(this.program.uniform(HdShaderSource.UNIFORM_ROOM_COLOUR[i]),
							0.0F, 0.0F, 0.0F, 0.0F);
					} else {
						this.api.uniform4f(this.program.uniform(HdShaderSource.UNIFORM_ROOM_BOUNDS[i]),
							region.minX(), region.minZ(), region.maxX(), region.maxZ());
						this.api.uniform4f(this.program.uniform(HdShaderSource.UNIFORM_ROOM_COLOUR[i]),
							region.red(), region.green(), region.blue(), region.strength());
					}
				}
				this.uploadedRegions = regions.clone();
				this.lightUploads++;
			}
		}
	}

	/**
	 * Pushes the environment's sky, and the flag that turns it on, for one draw.
	 *
	 * <p>Off sends one flag transition and nothing else. That is the whole point
	 * of caching the flag rather than the values: a frame with the row off is the
	 * stock frame, so the only GL call it can ever make here is the one that
	 * takes the row back out after a frame that had it on.
	 */
	private void uploadEnvironment(HdDrawRequest request, HdEnvironmentLight environment) {
		boolean on = request.environmentLighting() && !environment.isIdentity();
		if (!on) {
			if (this.uploadedEnvironmentEnabled == 1) {
				this.api.uniform1i(this.program.uniform(HdShaderSource.UNIFORM_ENV_LIGHTING), 0);
				this.uploadedEnvironmentEnabled = 0;
			}
			return;
		}
		if (this.uploadedEnvironmentEnabled != 1) {
			this.api.uniform1i(this.program.uniform(HdShaderSource.UNIFORM_ENV_LIGHTING), 1);
			this.uploadedEnvironmentEnabled = 1;
		}
		if (!environment.equals(this.uploadedEnvironment)) {
			this.api.uniform4f(this.program.uniform(HdShaderSource.UNIFORM_ENV_SKY),
				environment.red(), environment.green(), environment.blue(), environment.ambient());
			this.api.uniform4f(this.program.uniform(HdShaderSource.UNIFORM_ENV_UP),
				environment.upX(), environment.upY(), environment.upZ(), 0.0F);
			this.uploadedEnvironment = environment;
		}
	}

	/** How many light uploads this program has issued; zero means the rows never ran. */
	public long lightUploads() {
		return this.lightUploads;
	}

	/**
	 * Pushes this draw's kind and, when either row is on, the displacement
	 * block. Nothing is sent for a draw with the rows off.
	 *
	 * <p>The kind is per draw and the block is per frame, so they are cached
	 * apart: a scene of ten thousand plants uploads the block once and the kind
	 * only when it changes between vegetation and non-vegetation draws. The
	 * viewer position is part of the block, so a moving camera re-uploads once
	 * per frame rather than once per primitive.
	 */
	private void uploadSway(HdDrawRequest request, HdSway sway) {
		HdSway rows = sway.masked(request.foliageSway(), request.grassDisplacement());
		if (!rows.equals(this.uploadedSway)) {
			this.api.uniform4f(this.program.uniform(HdShaderSource.UNIFORM_SWAY),
				rows.foliage(), rows.grass(), HdSway.GRASS_RADIUS, HdSway.HEIGHT_SCALE);
			if (rows.active()) {
				this.api.uniform4f(this.program.uniform(HdShaderSource.UNIFORM_SWAY_PHASE),
					rows.seconds(), HdSway.WIND_SPEED, 0.0F, 0.0F);
				this.api.uniform4f(this.program.uniform(HdShaderSource.UNIFORM_SWAY_ORIGIN),
					rows.viewerX(), rows.viewerY(), rows.viewerZ(), 0.0F);
			}
			this.uploadedSway = rows;
			this.swayUploads++;
		}
		int vegetation = rows.active() ? request.vegetation() : 0;
		if (this.uploadedVegetation != vegetation) {
			this.api.uniform1i(this.program.uniform(HdShaderSource.UNIFORM_VEGETATION), vegetation);
			this.uploadedVegetation = vegetation;
			this.swayUploads++;
		}
	}

	/** How many displacement uploads this program has issued; zero means the rows never ran. */
	public long swayUploads() {
		return this.swayUploads;
	}

	/**
	 * Pushes the caustics strength and the water clock, and nothing when there is
	 * no pattern to draw.
	 *
	 * <p>The strength is the row's answer for this tile: zero when the row is off
	 * or the tile has no water over it, and that zero is also the uniform GL
	 * already holds, so the off state costs no call and no branch. The clock
	 * rides the same upload because the two are one value: a pattern is (how
	 * strong, when), and a pattern whose strength is zero has no "when" worth
	 * sending.
	 */
	private void uploadCaustics(HdDrawRequest request, float waterSeconds) {
		HdCaustics caustics = HdCaustics.of(request.caustics(), request.water(), waterSeconds);
		if (caustics.equals(this.uploadedCaustics)) {
			return;
		}
		this.api.uniform4f(this.program.uniform(HdShaderSource.UNIFORM_CAUSTICS),
			caustics.strength(), caustics.seconds(), 0.0F, 0.0F);
		this.uploadedCaustics = caustics;
		this.causticsUploads++;
	}

	/** How many caustics uploads this program has issued; zero means the row never drew. */
	public long causticsUploads() {
		return this.causticsUploads;
	}

	/**
	 * Pushes the reflection strength. The sampler's unit is selected once with
	 * the program's other samplers; only the strength moves per draw.
	 *
	 * <p>The strength is the row's answer for this material: zero unless the draw
	 * is one the client already considers reflective, and that zero is also GL's
	 * initial value, so a frame with the row off makes no call and no branch.
	 */
	private void uploadReflection(HdDrawRequest request) {
		HdReflection reflection = HdReflection.of(request.reflections(), request.materialEffectSlot());
		if (reflection.equals(this.uploadedReflection)) {
			return;
		}
		this.api.uniform4f(this.program.uniform(HdShaderSource.UNIFORM_REFLECTION),
			reflection.strength(), 0.0F, 0.0F, 0.0F);
		this.uploadedReflection = reflection;
		this.reflectionUploads++;
	}

	/** How many reflection uploads this program has issued; zero means the row never drew. */
	public long reflectionUploads() {
		return this.reflectionUploads;
	}

	/**
	 * Pushes a ground draw's atlas state: the two rows, its material, the tile
	 * size and the mask strength. Everything else about the atlas (the page, the
	 * material map, the tile map) is a texture the toolkit binds on its own
	 * units, so only these scalars travel through the program.
	 */
	private void uploadGroundTextures(HdDrawRequest request) {
		int worldTextures = request.worldTextures() ? 1 : 0;
		int materialMaps = request.materialMaps() ? 1 : 0;
		if (worldTextures != this.uploadedWorldTextures) {
			this.api.uniform1i(this.program.uniform(HdShaderSource.UNIFORM_WORLD_TEXTURES), worldTextures);
			this.uploadedWorldTextures = worldTextures;
			this.groundTextureUploads++;
		}
		if (materialMaps != this.uploadedMaterialMaps) {
			this.api.uniform1i(this.program.uniform(HdShaderSource.UNIFORM_MATERIAL_MAPS), materialMaps);
			this.uploadedMaterialMaps = materialMaps;
			this.groundTextureUploads++;
		}
		HdGroundTextures textures = request.groundTextures();
		float[] blend = { textures.material(), textures.tileSize(), 1.0F / HdTileMaterialMap.SIZE,
			textures.blendStrength() };
		if (this.uploadedTerrainBlend == null || !java.util.Arrays.equals(blend, this.uploadedTerrainBlend)) {
			this.api.uniform4f(this.program.uniform(HdShaderSource.UNIFORM_TERRAIN_BLEND),
				blend[0], blend[1], blend[2], blend[3]);
			this.uploadedTerrainBlend = blend;
			this.groundTextureUploads++;
		}
		if (this.uploadedWorldRepeat != textures.repeat()) {
			this.api.uniform1f(this.program.uniform(HdShaderSource.UNIFORM_WORLD_TEXTURE_REPEAT), textures.repeat());
			this.uploadedWorldRepeat = textures.repeat();
			this.groundTextureUploads++;
		}
	}

	/** How many ground-texture uniforms this program has pushed; zero means the pass never ran. */
	public long groundTextureUploads() {
		return this.groundTextureUploads;
	}

	/** Pushes one frame's map parameters: four matrix rows, texel, bias. */
	private void uploadShadow(ShadowBinding shadow) {
		float[] matrix = shadow.matrix();
		for (int row = 0; row < 4; row++) {
			this.api.uniform4f(this.program.uniform(HdShaderSource.UNIFORM_SHADOW_ROWS[row]),
				matrix[row], matrix[4 + row], matrix[8 + row], matrix[12 + row]);
		}
		float texel = 1.0F / shadow.size();
		this.api.uniform2f(this.program.uniform(HdShaderSource.UNIFORM_SHADOW_TEXEL), texel, texel);
		this.api.uniform1f(this.program.uniform(HdShaderSource.UNIFORM_SHADOW_BIAS), shadow.bias());
	}

	/** Returns the context to fixed function; no-op when nothing is bound. */
	public void unbind() {
		if (this.bound) {
			this.api.use(HdShaderApi.NO_PROGRAM);
			this.bound = false;
		}
	}

	/** Unbinds and deletes the program; called from the toolkit's teardown. */
	public void shutdown() {
		unbind();
		if (this.program != null) {
			this.api.delete(this.program.handle());
			this.program = null;
		}
		this.attempted = true;
		this.samplerBound = false;
		this.shadowSamplerBound = false;
		this.uploadedShadowEnabled = -1;
		this.uploadedShadowRadius = -1;
		this.uploadedShadowStrength = Float.NaN;
		this.uploadedShadowFrame = -1;
		this.binds = 0L;
		this.uploaded = null;
		this.uploadedAtmosphere = HdAtmosphere.OFF;
		this.uploadedModel = null;
		this.uploadedEye = null;
		this.uploadedSceneSpace = -1;
		this.uploadedFogOff = 0;
		this.uploadedCarriedEnabled = false;
		this.uploadedCarried = null;
		this.uploadedRoomEnabled = false;
		this.uploadedRegions = null;
		this.uploadedEnvironmentEnabled = -1;
		this.uploadedEnvironment = HdEnvironmentLight.OFF;
		this.lightUploads = 0L;
		this.uploadedVegetation = 0;
		this.uploadedSway = HdSway.OFF;
		this.swayUploads = 0L;
		this.uploadedCaustics = HdCaustics.OFF;
		this.causticsUploads = 0L;
		this.uploadedReflection = HdReflection.OFF;
		this.reflectionUploads = 0L;
		this.uploadedWorldTextures = -1;
		this.uploadedMaterialMaps = -1;
		this.uploadedTerrainBlend = null;
		this.uploadedWorldRepeat = Float.NaN;
		this.groundTextureUploads = 0L;
	}

	/** Writes the compile failure to the client log exactly once. */
	private void report() {
		if (this.reported) {
			return;
		}
		this.reported = true;
		System.out.println(logLine());
	}
}
