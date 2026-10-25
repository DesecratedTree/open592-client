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

	/** Wave amplitude in surface-normal units; the pane's "592HD water" row is on/off. */
	public static final float DEFAULT_FLOW = 0.06F;

	/** Blinn-Phong exponent: tight enough to read as a sun glint. */
	public static final float DEFAULT_SHININESS = 96.0F;

	/** The custom surface's colour looking straight down: deep, almost black-blue. */
	public static final float[] DEFAULT_DEEP_COLOUR = { 0.02F, 0.10F, 0.14F };

	/** The custom surface's colour at a grazing angle, tinting the stock texture. */
	public static final float[] DEFAULT_SHALLOW_COLOUR = { 0.75F, 0.95F, 1.0F };

	private final HdShaderApi api;

	/** The calm-water program; every water draw starts here. */
	private HdProgram still;

	/** The reflective program; only compiled when a reflective material asks. */
	private HdProgram reflective;

	/** The breaking program; only compiled when a breaking material asks. */
	private HdProgram breaking;

	/** The custom surface; only compiled when the row's Custom value asks. */
	private HdProgram custom;

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
		bind(wanted, request.seconds(), request.breakingParams());
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
			return true;
		}
		bind(wanted, request.seconds(), request.breakingParams());
		return true;
	}

	/** Returns the context to fixed function; no-op when nothing is bound. */
	public void unbind() {
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
		this.attempted = true;
		this.reflectiveAttempted = true;
		this.breakingAttempted = true;
		this.customAttempted = true;
		this.stillTime = Float.NaN;
		this.reflectiveTime = Float.NaN;
		this.breakingTime = Float.NaN;
		this.customTime = Float.NaN;
		this.breakingParams = null;
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
	private void bind(HdWaterGate.Surface surface, float seconds, HdWaterBreaking breakingParams) {
		HdProgram program = program(surface);
		this.api.use(program.handle());
		program.bindSampler();
		if (surface == HdWaterGate.Surface.REFLECTIVE || surface == HdWaterGate.Surface.BREAKING) {
			this.api.uniform1i(program.uniform(HdWaterSource.UNIFORM_ENVIRONMENT), HdWaterSource.ENVIRONMENT_UNIT);
		}
		float last = lastTime(surface);
		if (Float.isNaN(last) || seconds != last) {
			this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_TIME), seconds);
			this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_FLOW), this.flow);
			this.api.uniform1f(program.uniform(HdWaterSource.UNIFORM_SHININESS), DEFAULT_SHININESS);
			if (surface == HdWaterGate.Surface.CUSTOM) {
				this.api.uniform3f(program.uniform(HdWaterSource.UNIFORM_DEEP_COLOUR),
					DEFAULT_DEEP_COLOUR[0], DEFAULT_DEEP_COLOUR[1], DEFAULT_DEEP_COLOUR[2]);
				this.api.uniform3f(program.uniform(HdWaterSource.UNIFORM_SHALLOW_COLOUR),
					DEFAULT_SHALLOW_COLOUR[0], DEFAULT_SHALLOW_COLOUR[1], DEFAULT_SHALLOW_COLOUR[2]);
			}
			rememberTime(surface, seconds);
		}
		if (surface == HdWaterGate.Surface.BREAKING) {
			uploadBreaking(program, breakingParams);
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
