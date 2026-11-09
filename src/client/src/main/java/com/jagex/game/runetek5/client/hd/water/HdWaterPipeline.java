package com.jagex.game.runetek5.client.hd.water;

import com.jagex.game.runetek5.client.hd.shader.HdProgram;
import com.jagex.game.runetek5.client.hd.shader.HdProgramCompiler;
import com.jagex.game.runetek5.client.hd.shader.HdShaderApi;

/**
 * Owns 592HD's water programs for one GL context and the bind cycle around
 * them.
 *
 * <p>Lifetime is the toolkit's ({@code GlToolkit.stop()}), exactly like the
 * lighting pipeline: the programs cannot outlive the context they belong to.
 *
 * <ul>
 * <li>{@link #prepare()} compiles the calm program once; a driver that rejects
 * the shader is remembered and logged once, and the stock water effect keeps
 * drawing. {@link #prepareReflective()} does the same for the reflective
 * variant, and only when a draw actually asks for it - a session that never
 * meets a reflective water material never compiles that program.
 * {@link #prepareBreaking()} is the same again for the breaking water type, and
 * {@link #prepareCustom()} for the custom surface: a session whose water row is
 * Off or On never compiles these, and a driver that refuses one leaves the draw
 * on a surface that did link rather than without water.</li>
 * <li>{@link #begin} binds and uploads this frame's time; the flow amplitude
 * and shininess are uploaded with the time because they are cheap and change
 * at most when a setting does. A water region starts calm, because the region
 * itself has no material.</li>
 * <li>{@link #select} is the material bind's half: when the material's stock
 * effect slot is the reflective water type and an environment cube exists, it
 * switches to the reflective program (binding {@code uEnvironment} to unit 1
 * where the stock cube effects put theirs); when the slot is the breaking type
 * it switches to the breaking program and uploads that material's own numbers.
 * Switching back is the same call, so a region that mixes water types is one
 * program at a time, never two. Two breaking materials in a row leave the
 * program bound and only re-upload the numbers that changed, or nothing when
 * they are the same.</li>
 * <li>{@link #unbind()} returns to fixed function with {@code glUseProgram(0)}.
 * The stock water effect binds an ARB program through its own state; the
 * caller must not have enabled that while this program is bound, or GL will
 * have two programs enabled at once.</li>
 * </ul>
 *
 * <p>Which of the four programs a draw runs is {@link HdWaterGate#surface}'s
 * decision, not this class's: the row's style and the material's reflection and
 * breaking type arrive on the request, and the pipeline only binds what the
 * gate named.
 *
 * <p>None of the programs samples the sun's shadow map: the map covers a volume
 * around the camera and the water surface is inside it, but a shadow on a wave
 * crest reads as a smear. That is a deliberate omission, not a gap.
 */
public final class HdWaterPipeline {

	/** Wave amplitude in surface-normal units; the pane's Water row is on/off and its value picks the surface. */
	public static final float DEFAULT_FLOW = 0.06F;

	/**
	 * The glint's exponent: 634HD's WATER type {@code gloss}, 500
	 * ({@code 634hd/data/materials.schema.json}). It is a small, hard glint on
	 * the crests, not a broad sheen: at 96 the lobe spread over a wave face.
	 */
	public static final float DEFAULT_SHININESS = 500.0F;

	/**
	 * Ripple strength: 634HD's WATER type {@code normalStrength}, 0.09. The
	 * shipped normal map tilts the surface by this much in scene-normal units,
	 * which is a ripple, not a chop.
	 */
	public static final float DEFAULT_NORMAL_STRENGTH = 0.09F;

	/**
	 * The custom surface's colour looking straight down: 634HD's WATER type
	 * {@code depth} colour, {@code #00758e} (#00, #75, #8e) over 255. Upstream
	 * stores it as sRGB bytes ({@code 634hd/data/materials.schema.json}), and this
	 * row shades in the texture's own sRGB space, so the bytes divide straight
	 * through rather than being linearised first.
	 */
	public static final float[] DEFAULT_DEEP_COLOUR = { 0x00 / 255.0F, 0x75 / 255.0F, 0x8e / 255.0F };

	/**
	 * The custom surface's colour at a grazing angle, tinting the stock texture:
	 * the same type's {@code surface} colour, {@code #69809c}.
	 */
	public static final float[] DEFAULT_SHALLOW_COLOUR = {
		0x69 / 255.0F, 0x80 / 255.0F, 0x9c / 255.0F
	};

	/** The most point lights a stock frame uploads: {@code gl_LightSource[2 .. 5]}. */
	public static final int MAX_POINT_LIGHTS = 4;

	private final HdShaderApi api;

	/** The calm-water program; every water draw starts here. */
	private HdProgram still;

	/** The reflective program; only compiled when a reflective material asks. */
	private HdProgram reflective;

	/** The breaking program; only compiled when a breaking material asks. */
	private HdProgram breaking;

	/** The custom surface; only compiled when the row's Custom value asks. */
	private HdProgram custom;

	/**
	 * The terrain-format surfaces, one per {@link HdWaterGate.Surface}: the ground
	 * pass' vertex arrays over the same fragment stages the region runs, so a
	 * ground layer gets the region's look rather than a fixed calm one.
	 */
	private final HdProgram[] ground = new HdProgram[HdWaterGate.Surface.values().length];

	private final boolean[] groundAttempted = new boolean[HdWaterGate.Surface.values().length];

	/** The surface the open ground layer runs; {@code NONE} while none is bound. */
	private HdWaterGate.Surface groundSurface = HdWaterGate.Surface.NONE;

	/** The last surface a ground layer ran, which survives the hand-back. */
	private HdWaterGate.Surface lastGroundSurface = HdWaterGate.Surface.NONE;

	/** How many terrain-format binds ran, so a harness can see the variant ran. */
	private long groundBinds;

	/** Whether the program currently bound is the terrain-format one. */
	private boolean groundBound;

	/** The draw the open region last asked for, so the ground variant can hand back. */
	private HdWaterRequest regionRequest;

	private boolean attempted;

	private boolean reported;

	private String failure = "";

	private boolean reflectiveAttempted;

	private boolean reflectiveReported;

	private String reflectiveFailure = "";

	private boolean breakingAttempted;

	private boolean breakingReported;

	private String breakingFailure = "";

	private boolean customAttempted;

	private boolean customReported;

	private String customFailure = "";

	private boolean bound;

	/** Which program the open draw is running; {@code NONE} while unbound. */
	private HdWaterGate.Surface surface = HdWaterGate.Surface.NONE;

	private long binds;

	private long reflectiveBinds;

	private long breakingBinds;

	private long customBinds;

	private float flow = DEFAULT_FLOW;

	/** The breaking numbers last handed to the breaking program; null until it has any. */
	private HdWaterBreaking breakingParams;

	/** Whether the open draw's program holds a cube on the environment unit. */
	private boolean boundEnvironment;

	/** The area water the bound program holds, and the program it went to. */
	private HdWaterArea boundArea;

	private float boundAreaBlend = Float.NaN;

	private HdProgram boundAreaProgram;

	/** Whether the two water maps are bound on their units for the open draw. */
	private boolean waterMaps;

	/** The caustic strength last handed to the custom program. */
	private float boundCaustics = Float.NaN;

	/** The water type last handed to the bound program; null forces an upload. */
	private HdWaterType boundType;

	/**
	 * Which program object the two rows above went to: uniform state is per
	 * program, and a surface has more than one - the model-format program and the
	 * terrain-format one. Keying on the surface let a ground layer reuse the model
	 * program's type and draw with every water uniform still zero.
	 */
	private HdProgram boundTypeProgram;

	/** The frame's live point lights, 0 to {@link #MAX_POINT_LIGHTS}. */
	private int pointLights;

	/** The count last uploaded to the bound program; -1 forces an upload. */
	private int boundPointLights = -1;

	/** Per program: a new program has never been handed this frame's uniforms. */
	private float stillTime = Float.NaN;

	private float reflectiveTime = Float.NaN;

	private float breakingTime = Float.NaN;

	private float customTime = Float.NaN;

	public HdWaterPipeline(HdShaderApi api) {
		this.api = api;
	}

	/** True once the calm program has linked. Never compiles. */
	public boolean available() {
		return this.still != null && this.still.usable();
	}

	/** True once the reflective program has linked. Never compiles. */
	public boolean reflectiveAvailable() {
		return this.reflective != null && this.reflective.usable();
	}

	/** True once the breaking program has linked. Never compiles. */
	public boolean breakingAvailable() {
		return this.breaking != null && this.breaking.usable();
	}

	/** True once the custom surface has linked. Never compiles. */
	public boolean customAvailable() {
		return this.custom != null && this.custom.usable();
	}

	/** True between {@link #begin} and {@link #unbind}. */
	public boolean bound() {
		return this.bound;
	}

	/**
	 * The GL program name this pass has bound, or 0 in fixed function.
	 *
	 * <p>The water pass switches programs per material, so the bound one is the
	 * surface's, not "the" water program; the toolkit's state cache reports it so
	 * a draw that leaves a water shader bound over the interface is named.
	 */
	public int programName() {
		HdProgram bound = this.bound ? program(this.surface) : null;
		return bound == null ? 0 : (int) bound.handle();
	}

	/** True while the reflective program is bound, for the frame's other seams. */
	public boolean reflectiveMode() {
		return this.surface == HdWaterGate.Surface.REFLECTIVE;
	}

	/** True while the breaking program is bound. */
	public boolean breakingMode() {
		return this.surface == HdWaterGate.Surface.BREAKING;
	}

	/** True while the custom surface is the bound program. */
	public boolean customMode() {
		return this.surface == HdWaterGate.Surface.CUSTOM;
	}

	/** Which program the open draw runs; {@code NONE} when nothing is bound. */
	public HdWaterGate.Surface surface() {
		return this.surface;
	}

	/** The one-line report of a failed compile, or "" when there is nothing to say. */
	public String logLine() {
		StringBuilder line = new StringBuilder();
		if (!this.failure.isEmpty()) {
			line.append("[592HD] HD water unavailable - ").append(this.failure);
		}
		if (!this.reflectiveFailure.isEmpty()) {
			if (line.length() > 0) {
				line.append("; ");
			}
			line.append("[592HD] HD water reflections unavailable - ").append(this.reflectiveFailure);
		}
		if (!this.breakingFailure.isEmpty()) {
			if (line.length() > 0) {
				line.append("; ");
			}
			line.append("[592HD] breaking water unavailable - ").append(this.breakingFailure);
		}
		if (!this.customFailure.isEmpty()) {
			if (line.length() > 0) {
				line.append("; ");
			}
			line.append("[592HD] custom water surface unavailable - ").append(this.customFailure);
		}
		return line.toString();
	}

	/** How many water draws have bound a program; zero means the seam is dead. */
	public long binds() {
		return this.binds;
	}

	/** How many of those draws sampled the environment cube. */
	public long reflectiveBinds() {
		return this.reflectiveBinds;
	}

	/** How many of those draws ran the breaking type's surface. */
	public long breakingBinds() {
		return this.breakingBinds;
	}

	/** How many of those draws ran the custom surface. */
	public long customBinds() {
		return this.customBinds;
	}

	/** Compiles the calm program if that has not been attempted yet. */
	public boolean prepare() {
		if (this.attempted) {
			return available();
		}
		this.attempted = true;
		HdProgramCompiler.Result result = HdProgramCompiler.compileWater(this.api);
		if (!result.succeeded()) {
			this.failure = result.failure();
			this.still = null;
			report();
			return false;
		}
		this.still = result.program();
		return true;
	}

	/** Compiles the custom surface if the row asked for it; never retried. */
	public boolean prepareCustom() {
		if (this.customAttempted) {
			return customAvailable();
		}
		this.customAttempted = true;
		HdProgramCompiler.Result result = HdProgramCompiler.compileCustomWater(this.api);
		if (!result.succeeded()) {
			this.customFailure = result.failure();
			this.custom = null;
			reportCustom();
			return false;
		}
		this.custom = result.program();
		return true;
	}

	/** Compiles the reflective program if a draw asked for it; never retried. */
	public boolean prepareReflective() {
		if (this.reflectiveAttempted) {
			return reflectiveAvailable();
		}
		this.reflectiveAttempted = true;
		HdProgramCompiler.Result result = HdProgramCompiler.compileReflectiveWater(this.api);
		if (!result.succeeded()) {
			this.reflectiveFailure = result.failure();
			this.reflective = null;
			reportReflective();
			return false;
		}
		this.reflective = result.program();
		return true;
	}

	/** Compiles the breaking program if a draw asked for it; never retried. */
	public boolean prepareBreaking() {
		if (this.breakingAttempted) {
			return breakingAvailable();
		}
		this.breakingAttempted = true;
		HdProgramCompiler.Result result = HdProgramCompiler.compileBreakingWater(this.api);
		if (!result.succeeded()) {
			this.breakingFailure = result.failure();
			this.breaking = null;
			reportBreaking();
			return false;
		}
		this.breaking = result.program();
		return true;
	}

	/**
	 * Binds a water program for the draw that is about to happen.
	 *
	 * @param request the draw's facts, gathered at the seam so this class never
	 *                reads the settings or the material cache itself
	 * @return true when a program is bound and the caller must call {@link #unbind}
	 */
	public boolean begin(HdWaterRequest request) {
		if (request.requested()) {
			// Compile on first use; never reach GL at all when the row is off.
			prepare();
			if (request.reflective() && request.environment()) {
				prepareReflective();
			}
			if (request.breaking() && request.environment()) {
				prepareBreaking();
			}
			// The custom surface only matters to a draw the reflection or the
			// breaking band does not take, so neither material never costs its
			// compile.
			if (request.custom() && !request.breaking()
					&& !HdWaterGate.shouldReflect(request, available(), reflectiveAvailable())) {
				prepareCustom();
			}
		}
		HdWaterGate.Surface wanted = HdWaterGate.surface(request, available(), customAvailable(),
			reflectiveAvailable(), breakingAvailable());
		if (wanted == HdWaterGate.Surface.NONE) {
			return false;
		}
		bind(wanted, request);
		return true;
	}

	/**
	 * Re-points an open water draw at the program its material asks for.
	 *
	 * <p>Called from the material bind, once per water material. Returns false
	 * when no water draw is open, so a stray call cannot bind a program outside
	 * the seam. When the material does not change the answer, nothing reaches
	 * GL - which is what keeps a calm draw's calls exactly what they were. The
	 * exception is a breaking material: its numbers are per material, so a
	 * second breaking material on the same program still uploads the ones that
	 * changed.
	 */
	public boolean select(HdWaterRequest request) {
		if (!this.bound) {
			return false;
		}
		if (this.groundBound) {
			// The terrain-format program owns this layer's draw. Rebinding a
			// model-format program here would drop the layer's own vertex arrays
			// for a program written against the model path.
			return true;
		}
		if (request.requested() && request.reflective() && request.environment()) {
			prepareReflective();
		}
		if (request.requested() && request.breaking() && request.environment()) {
			prepareBreaking();
		}
		if (request.requested() && request.custom() && !request.breaking()
				&& !HdWaterGate.shouldReflect(request, available(), reflectiveAvailable())) {
			prepareCustom();
		}
		HdWaterGate.Surface wanted = HdWaterGate.surface(request, available(), customAvailable(),
			reflectiveAvailable(), breakingAvailable());
		if (wanted == HdWaterGate.Surface.NONE) {
			return true;
		}
		if (wanted == HdWaterGate.Surface.BREAKING && wanted == this.surface) {
			// Same program, different material: the surface stays, the material's
			// numbers move.
			uploadBreaking(program(wanted), request.breakingParams());
			return true;
		}
		if (wanted == this.surface) {
			refresh(wanted, request);
			return true;
		}
		bind(wanted, request);
		return true;
	}

	/** Compiles the calm terrain-format surface; kept for the harness and teardown. */
	public boolean prepareGround() {
		return prepareGround(HdWaterGate.Surface.STILL);
	}

	/**
	 * Compiles one terrain-format surface if a ground water layer asked for it.
	 * One program per surface, compiled lazily: a region that never meets a
	 * material type never pays for its terrain-format program.
	 */
	private boolean prepareGround(HdWaterGate.Surface surface) {
		int index = surface.ordinal();
		if (this.groundAttempted[index]) {
			return this.ground[index] != null && this.ground[index].usable();
		}
		this.groundAttempted[index] = true;
		HdProgramCompiler.Result result;
		switch (surface) {
		case CUSTOM:
			result = HdProgramCompiler.compileGroundWater(this.api, HdWaterSource.CUSTOM_FRAGMENT,
				"592HD custom ground water");
			break;
		case REFLECTIVE:
			result = HdProgramCompiler.compileGroundWater(this.api, HdWaterSource.REFLECTIVE_FRAGMENT,
				"592HD reflective ground water");
			break;
		case BREAKING:
			result = HdProgramCompiler.compileGroundWater(this.api, HdWaterSource.BREAKING_FRAGMENT,
				"592HD breaking ground water");
			break;
		default:
			result = HdProgramCompiler.compileGroundWater(this.api);
			break;
		}
		if (!result.succeeded()) {
			this.ground[index] = null;
			return false;
		}
		this.ground[index] = result.program();
		return this.ground[index].usable();
	}

	/** True when the calm terrain-format surface linked on this context. */
	public boolean groundAvailable() {
		return ground(HdWaterGate.Surface.STILL) != null && ground(HdWaterGate.Surface.STILL).usable();
	}

	/** The terrain-format program for one surface, or null when it has none. */
	private HdProgram ground(HdWaterGate.Surface surface) {
		HdProgram program = this.ground[surface.ordinal()];
		return program != null && program.usable() ? program : null;
	}

	/** True while a ground layer owns the program. */
	public boolean groundBound() {
		return this.groundBound;
	}

	/** The surface the open ground layer runs; {@code NONE} when none is open. */
	public HdWaterGate.Surface groundSurface() {
		return this.groundSurface;
	}

	/** The surface the last ground layer ran, for the pixel proof. */
	public HdWaterGate.Surface lastGroundSurface() {
		return this.lastGroundSurface;
	}

	/**
	 * Binds the terrain-format surface for a ground water layer.
	 *
	 * <p>Only meaningful while a region is open; the region owns the cube, the
	 * maps and the clock, and this call only swaps the program the layer is drawn
	 * with. The layer's depth byte becomes the foam term {@link HdWaterSource#VERTEX}
	 * takes from a model's texcoord z, and the calm fragment stage shades it.
	 *
	 * @param request the region's draw, built by the seam
	 * @param foam    the stock per-tile depth over 255
	 */
	public boolean beginGround(HdWaterRequest request, float foam) {
		if (!this.bound || !request.requested()) {
			return false;
		}
		this.regionRequest = request;
		HdWaterGate.Surface wanted = HdWaterGate.surface(request, available(), customAvailable(),
			reflectiveAvailable(), breakingAvailable());
		if (wanted == HdWaterGate.Surface.NONE || !prepareGround(wanted)) {
			return false;
		}
		HdProgram program = ground(wanted);
		if (program == null) {
			return false;
		}
		this.api.use(program.handle());
		program.bindSampler();
		boolean environment = request.environment();
		this.api.uniform1i(program.uniform(HdWaterSource.UNIFORM_ENVIRONMENT), HdWaterSource.ENVIRONMENT_UNIT);
		this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_REFLECTION), environment ? 1.0F : 0.0F);
		this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_TIME), request.seconds());
		this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_FLOW), this.flow);
		program.bindWaterSamplers();
		this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_WATER_MAPS), this.waterMaps ? 1.0F : 0.0F);
		this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_GROUND_DEPTH), foam);
		// The row's net is every surface's now - on terrain as on a model - and the
		// material's water type is the body the stock texel only tints, which is what
		// stops a white material texture from drawing a white lake.
		this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_CAUSTICS), request.caustics());
		uploadWaterType(program, request.type());
		uploadWaterArea(program);
		if (wanted == HdWaterGate.Surface.BREAKING) {
			uploadBreaking(program, request.breakingParams());
		}
		this.groundSurface = wanted;
		this.lastGroundSurface = wanted;
		this.groundBound = true;
		this.groundBinds++;
		return true;
	}

	/**
	 * Hands the draw back to the region's own program.
	 *
	 * <p>The region's program keeps its uniform state in the program object, so
	 * this is a bind rather than a second full upload; the clock is refreshed on
	 * the next material, which is the same path every other draw in the region
	 * takes.
	 */
	public void endGround() {
		if (!this.groundBound) {
			return;
		}
		this.groundBound = false;
		this.groundSurface = HdWaterGate.Surface.NONE;
		if (this.regionRequest == null) {
			this.api.use(HdShaderApi.NO_PROGRAM);
			this.bound = false;
			this.surface = HdWaterGate.Surface.NONE;
			return;
		}
		HdWaterGate.Surface wanted = HdWaterGate.surface(this.regionRequest, available(),
			customAvailable(), reflectiveAvailable(), breakingAvailable());
		if (wanted == HdWaterGate.Surface.NONE) {
			this.api.use(HdShaderApi.NO_PROGRAM);
			this.bound = false;
			this.surface = HdWaterGate.Surface.NONE;
			return;
		}
		this.bound = false;
		bind(wanted, this.regionRequest);
	}

	/** How many terrain-format binds ran; zero means a water layer never reached one. */
	public long groundBinds() {
		return this.groundBinds;
	}

	/** Returns the context to fixed function; no-op when nothing is bound. */
	public void unbind() {
		// A ground layer that never closed would otherwise leave the terrain-format
		// program selected for the next region, which is exactly the leak that
		// made water lose its texture after the first layer.
		this.groundBound = false;
		this.groundSurface = HdWaterGate.Surface.NONE;
		if (this.bound) {
			this.api.use(HdShaderApi.NO_PROGRAM);
			this.bound = false;
			this.surface = HdWaterGate.Surface.NONE;
		}
	}

	/**
	 * Test hook: changes the wave amplitude, and forces the next upload. The
	 * pixel harness uses zero to mean a perfectly flat surface, which is the
	 * only way to prove the movement comes from the perturbation.
	 */
	public void setFlowForTest(float amplitude) {
		this.flow = amplitude;
		this.stillTime = Float.NaN;
		this.reflectiveTime = Float.NaN;
		this.breakingTime = Float.NaN;
		this.customTime = Float.NaN;
	}

	/**
	 * Tells the pipeline whether the toolkit managed to bind 634HD's water maps.
	 * The flag rides in the per-frame uniform group, so a draw whose maps are
	 * missing keeps the wave-field surface instead of sampling empty units.
	 */
	public void setWaterMaps(boolean available) {
		if (this.waterMaps != available) {
			// The flag rides in the per-program frame group, and that group is
			// skipped while the clock stands still. A change of art must not
			// wait for the next clock step: invalidate the groups so the next
			// bind uploads the flag with them.
			this.stillTime = Float.NaN;
			this.reflectiveTime = Float.NaN;
			this.breakingTime = Float.NaN;
			this.customTime = Float.NaN;
		}
		this.waterMaps = available;
	}

	/** True when the last draw was told the water maps are bound. */
	public boolean waterMaps() {
		return this.waterMaps;
	}

	/** Unbinds and deletes the programs; called from the toolkit's teardown. */
	public void shutdown() {
		unbind();
		if (this.still != null) {
			this.api.delete(this.still.handle());
			this.still = null;
		}
		if (this.reflective != null) {
			this.api.delete(this.reflective.handle());
			this.reflective = null;
		}
		if (this.breaking != null) {
			this.api.delete(this.breaking.handle());
			this.breaking = null;
		}
		if (this.custom != null) {
			this.api.delete(this.custom.handle());
			this.custom = null;
		}
		for (int index = 0; index < this.ground.length; index++) {
			if (this.ground[index] != null) {
				this.api.delete(this.ground[index].handle());
				this.ground[index] = null;
			}
			this.groundAttempted[index] = true;
		}
		this.groundBound = false;
		this.groundSurface = HdWaterGate.Surface.NONE;
		this.lastGroundSurface = HdWaterGate.Surface.NONE;
		this.groundBinds = 0L;
		this.attempted = true;
		this.reflectiveAttempted = true;
		this.breakingAttempted = true;
		this.customAttempted = true;
		this.stillTime = Float.NaN;
		this.reflectiveTime = Float.NaN;
		this.breakingTime = Float.NaN;
		this.customTime = Float.NaN;
		this.breakingParams = null;
		this.boundEnvironment = false;
		this.boundCaustics = Float.NaN;
		this.boundType = null;
		this.boundTypeProgram = null;
		this.boundPointLights = -1;
		this.pointLights = 0;
		this.waterMaps = false;
		this.binds = 0L;
		this.reflectiveBinds = 0L;
		this.breakingBinds = 0L;
		this.customBinds = 0L;
		this.surface = HdWaterGate.Surface.NONE;
	}

	/**
	 * Binds the program the gate named and hands it the state it needs.
	 *
	 * <p>The sampler and the animation group are uploaded per program, because
	 * uniform state belongs to the program object: a program that has just been
	 * switched to has never seen this frame's time, even when another one has.
	 * The custom surface's palette goes up with that group because it is
	 * constant, and the breaking material's numbers because they come with the
	 * draw that asked for the program.
	 */
	private void bind(HdWaterGate.Surface surface, HdWaterRequest request) {
		HdProgram program = program(surface);
		this.api.use(program.handle());
		program.bindSampler();
		boolean environment = request.environment();
		if (environment) {
			this.api.uniform1i(program.uniform(HdWaterSource.UNIFORM_ENVIRONMENT), HdWaterSource.ENVIRONMENT_UNIT);
		}
		if (surface == HdWaterGate.Surface.STILL || surface == HdWaterGate.Surface.CUSTOM) {
			// The standard and custom surfaces blend the sky themselves; zero keeps
			// the fog band's colour when the frame has no cube to sample.
			this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_REFLECTION), environment ? 1.0F : 0.0F);
		}
		// Every surface shows the row's net and reads the material's water type and
		// the frame's point lights, so all three go up with the bind rather than
		// only for one style.
		float caustics = request.caustics();
		this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_CAUSTICS), caustics);
		uploadWaterType(program, request.type());
		uploadWaterArea(program);
		this.boundEnvironment = environment;
		this.boundCaustics = caustics;
		float seconds = request.seconds();
		float last = lastTime(surface);
		if (Float.isNaN(last) || seconds != last) {
			this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_TIME), seconds);
			this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_FLOW), this.flow);
			// The map units never change, so they are selected with the frame
			// group rather than per bind; the flag says whether they hold art.
			program.bindWaterSamplers();
			this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_WATER_MAPS), this.waterMaps ? 1.0F : 0.0F);
			rememberTime(surface, seconds);
		}
		if (surface == HdWaterGate.Surface.BREAKING) {
			uploadBreaking(program, request.breakingParams());
		}
		this.surface = surface;
		this.bound = true;
		this.binds++;
		if (surface == HdWaterGate.Surface.REFLECTIVE) {
			this.reflectiveBinds++;
		} else if (surface == HdWaterGate.Surface.BREAKING) {
			this.breakingBinds++;
		} else if (surface == HdWaterGate.Surface.CUSTOM) {
			this.customBinds++;
		}
	}

	/**
	 * Re-uploads the two uniform groups a material can change without changing
	 * the program: the reflection flag (the cube is bound per draw, so it can
	 * appear between two materials of the same type) and the custom surface's
	 * caustic strength (the Caustics row is live). A draw that changes neither
	 * reaches no GL call at all.
	 */
	private void refresh(HdWaterGate.Surface surface, HdWaterRequest request) {
		if (surface == HdWaterGate.Surface.NONE) {
			return;
		}
		HdProgram program = program(surface);
		if (program == null) {
			return;
		}
		if (program != this.boundTypeProgram) {
			// Uniform state belongs to the program object: a program that has just
			// been switched to has never seen this frame's water type or lights.
			this.boundType = null;
			this.boundPointLights = -1;
		}
		if (!request.type().sameAs(this.boundType) || this.boundPointLights != this.pointLights) {
			uploadWaterType(program, request.type());
		}
		if ((surface == HdWaterGate.Surface.STILL || surface == HdWaterGate.Surface.CUSTOM)
				&& request.environment() != this.boundEnvironment) {
			if (request.environment()) {
				this.api.uniform1i(program.uniform(HdWaterSource.UNIFORM_ENVIRONMENT), HdWaterSource.ENVIRONMENT_UNIT);
			}
			this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_REFLECTION),
				request.environment() ? 1.0F : 0.0F);
			this.boundEnvironment = request.environment();
		}
		if (request.caustics() != this.boundCaustics) {
			this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_CAUSTICS), request.caustics());
			this.boundCaustics = request.caustics();
		}
	}

	/**
	 * The frame's live point lights, counted as the lighting program counts them.
	 * Their position, colour and attenuation are fixed function's
	 * {@code gl_LightSource[2 .. 5]} rows, which the stock light copy has already
	 * uploaded; this seam only tells the water surface how many of them to read.
	 */
	public void setPointLights(int count) {
		int clamped = count < 0 ? 0 : (count > MAX_POINT_LIGHTS ? MAX_POINT_LIGHTS : count);
		if (this.pointLights != clamped) {
			this.pointLights = clamped;
			this.boundPointLights = -1;
		}
	}

	/** The point-light count last handed to the water surface. */
	public int pointLights() {
		return this.pointLights;
	}

	/**
	 * Hands one water type and the frame's point-light count to the bound
	 * program: the type's palette, foam colour, gloss, ripple strength, specular
	 * strength, Fresnel share, opacity and animation period. A material's water
	 * block is the whole difference between a swamp's water and a lake's, so the
	 * program never changes for it. Skipped when the program already holds them.
	 */
	private void uploadWaterType(HdProgram program, HdWaterType type) {
		HdWaterType resolved = type == null ? HdWaterType.DEFAULT : type;
		boolean sameProgram = program == this.boundTypeProgram;
		if (!sameProgram || !resolved.sameAs(this.boundType)) {
			float[] surfaceColour = resolved.surfaceColour();
			float[] depthColour = resolved.depthColour();
			float[] foamColour = resolved.foamColour();
			this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_SHININESS), resolved.gloss());
			this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_NORMAL_STRENGTH),
				resolved.normalStrength());
			this.api.uniform3f(program.uniform(HdWaterSource.UNIFORM_DEEP_COLOUR),
				depthColour[0], depthColour[1], depthColour[2]);
			this.api.uniform3f(program.uniform(HdWaterSource.UNIFORM_SHALLOW_COLOUR),
				surfaceColour[0], surfaceColour[1], surfaceColour[2]);
			this.api.uniform3f(program.uniform(HdWaterSource.UNIFORM_FOAM_COLOUR),
				foamColour[0], foamColour[1], foamColour[2]);
			this.api.uniform3f(program.uniform(HdWaterSource.UNIFORM_WATER_SPEC),
				resolved.specular(), resolved.fresnel(), resolved.opacity());
			this.api.uniform3f(program.uniform(HdWaterSource.UNIFORM_WATER_SHAPE),
				resolved.hasFoam() ? 1.0F : 0.0F, resolved.duration(),
				resolved.plain() ? 1.0F : 0.0F);
			this.boundType = resolved;
		}
		if (!sameProgram || this.boundPointLights != this.pointLights) {
			this.api.uniform1i(program.uniform(HdWaterSource.UNIFORM_POINT_LIGHTS), this.pointLights);
			this.boundPointLights = this.pointLights;
		}
		this.boundTypeProgram = program;
	}

	/**
	 * Hands the bound program the area's water: the ripples' height, the
	 * sun's glint, the tiling (and the tiling on show before it, with the
	 * blend between them) and the tint - 634HD's {@code hdWaterParams},
	 * {@code hdWaterClock.y}/{@code z} and {@code hdWaterTint}. Skipped when
	 * the program already holds this frame's values, so a settled area costs
	 * no GL call between two draws of the same program.
	 */
	private void uploadWaterArea(HdProgram program) {
		HdWaterArea area = HdWaterArea.current();
		if (program != this.boundAreaProgram || !area.sameAs(this.boundArea)) {
			this.api.uniform3f(program.uniform(HdWaterSource.UNIFORM_WATER_PARAMS), area.waves(),
				area.glint(), area.size());
			this.api.uniform4f(program.uniform(HdWaterSource.UNIFORM_WATER_TINT), area.tintRed(),
				area.tintGreen(), area.tintBlue(), area.tintStrength());
			this.boundArea = area;
			this.boundAreaBlend = Float.NaN;
		}
		if (Float.isNaN(this.boundAreaBlend) || area.blend() != this.boundAreaBlend) {
			this.api.uniform2f(program.uniform(HdWaterSource.UNIFORM_WATER_BLEND),
				area.shownSize(), area.blend());
			this.boundAreaBlend = area.blend();
		}
		this.boundAreaProgram = program;
	}

	/**
	 * Hands the breaking program one material's numbers, skipping the upload
	 * when the program already holds them.
	 */
	private void uploadBreaking(HdProgram program, HdWaterBreaking params) {
		if (params.sameAs(this.breakingParams)) {
			return;
		}
		this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_BREAK_DEPTH), params.depth());
		this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_BREAK_OFFSET), params.offset());
		this.api.uniform2f(program.uniform(HdWaterSource.UNIFORM_WAVE_INTENSITY), params.intensityX(),
			params.intensityY());
		this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_WAVE_EXPONENT), params.exponent());
		this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_BREAK_SCALE), params.textureScale());
		this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_BREAK_RATE), params.speed());
		this.breakingParams = params;
	}

	/** The program one surface runs, or null for {@code NONE}. */
	private HdProgram program(HdWaterGate.Surface surface) {
		switch (surface) {
		case REFLECTIVE:
			return this.reflective;
		case BREAKING:
			return this.breaking;
		case CUSTOM:
			return this.custom;
		case STILL:
			return this.still;
		default:
			return null;
		}
	}

	/** The time last uploaded to that surface's program. */
	private float lastTime(HdWaterGate.Surface surface) {
		switch (surface) {
		case REFLECTIVE:
			return this.reflectiveTime;
		case BREAKING:
			return this.breakingTime;
		case CUSTOM:
			return this.customTime;
		default:
			return this.stillTime;
		}
	}

	private void rememberTime(HdWaterGate.Surface surface, float seconds) {
		switch (surface) {
		case REFLECTIVE:
			this.reflectiveTime = seconds;
			break;
		case BREAKING:
			this.breakingTime = seconds;
			break;
		case CUSTOM:
			this.customTime = seconds;
			break;
		default:
			this.stillTime = seconds;
			break;
		}
	}

	private void report() {
		if (this.reported) {
			return;
		}
		this.reported = true;
		System.out.println(logLine());
	}

	private void reportReflective() {
		if (this.reflectiveReported) {
			return;
		}
		this.reflectiveReported = true;
		System.out.println(logLine());
	}

	private void reportBreaking() {
		if (this.breakingReported) {
			return;
		}
		this.breakingReported = true;
		System.out.println(logLine());
	}

	private void reportCustom() {
		if (this.customReported) {
			return;
		}
		this.customReported = true;
		System.out.println(logLine());
	}
}
