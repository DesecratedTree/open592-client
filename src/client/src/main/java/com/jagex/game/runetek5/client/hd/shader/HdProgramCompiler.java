package com.jagex.game.runetek5.client.hd.shader;

/**
 * Compiles and links the 592HD programs, and reports exactly what went wrong
 * when it cannot.
 *
 * <p>GLSL failures are normal life on old drivers, so a failure must never be
 * silent and must never leak a GL object: the result carries the driver log,
 * every shader object is deleted on every path, and the program object is
 * deleted unless it linked. Callers treat an unsuccessful result as "592HD
 * lighting is off" rather than as an error - the stock fixed-function path is
 * still there and keeps drawing.
 */
public final class HdProgramCompiler {

	private HdProgramCompiler() {
	}

	/** The outcome of one compile: a usable program, or the reason there is none. */
	public static final class Result {

		private final HdProgram program;

		private final String failure;

		private Result(HdProgram program, String failure) {
			this.program = program;
			this.failure = failure;
		}

		public boolean succeeded() {
			return this.program != null;
		}

		/** The linked program, or null. */
		public HdProgram program() {
			return this.program;
		}

		/** A one-line report naming the stage and quoting the driver, or "". */
		public String failure() {
			return this.failure;
		}
	}

	/** Compiles {@link HdShaderSource}'s per-pixel lighting program. */
	public static Result compileLighting(HdShaderApi api) {
		return compile(api, "592HD per-pixel lighting", HdShaderSource.VERTEX, HdShaderSource.FRAGMENT);
	}

	/** Compiles the screen-space pass, which shares this compile-and-report path. */
	public static Result compilePost(HdShaderApi api) {
		return compile(api, "592HD post-processing",
			com.jagex.game.runetek5.client.hd.post.HdPostSource.VERTEX,
			com.jagex.game.runetek5.client.hd.post.HdPostSource.FRAGMENT);
	}

	/** Compiles the HD water program, which shares this compile-and-report path. */
	public static Result compileWater(HdShaderApi api) {
		return compile(api, "592HD water",
			com.jagex.game.runetek5.client.hd.water.HdWaterSource.VERTEX,
			com.jagex.game.runetek5.client.hd.water.HdWaterSource.FRAGMENT);
	}

	/**
	 * Compiles the reflective water program: the same vertex stage, a fragment
	 * stage that samples the environment cube instead of the fog band's colour.
	 */
	public static Result compileReflectiveWater(HdShaderApi api) {
		return compile(api, "592HD water reflections",
			com.jagex.game.runetek5.client.hd.water.HdWaterSource.VERTEX,
			com.jagex.game.runetek5.client.hd.water.HdWaterSource.REFLECTIVE_FRAGMENT);
	}

	/**
	 * Compiles the breaking water program: the same vertex stage again, a
	 * fragment stage whose band is the breaking material's own numbers.
	 */
	public static Result compileBreakingWater(HdShaderApi api) {
		return compile(api, "592HD breaking water",
			com.jagex.game.runetek5.client.hd.water.HdWaterSource.VERTEX,
			com.jagex.game.runetek5.client.hd.water.HdWaterSource.BREAKING_FRAGMENT);
	}

	/**
	 * Compiles the custom water surface: the same vertex stage as the other water
	 * programs, a fragment stage with the third look the row selects.
	 */
	public static Result compileCustomWater(HdShaderApi api) {
		return compile(api, "592HD custom water",
			com.jagex.game.runetek5.client.hd.water.HdWaterSource.VERTEX,
			com.jagex.game.runetek5.client.hd.water.HdWaterSource.CUSTOM_FRAGMENT);
	}

	/** Compiles the sky dome, which shares this compile-and-report path. */
	public static Result compileSky(HdShaderApi api) {
		return compile(api, "592HD sky",
			com.jagex.game.runetek5.client.hd.sky.HdSkySource.VERTEX,
			com.jagex.game.runetek5.client.hd.sky.HdSkySource.FRAGMENT);
	}

	public static Result compile(HdShaderApi api, String name, String vertexSource, String fragmentSource) {
		long vertexShader = 0L;
		long fragmentShader = 0L;
		long program = 0L;
		boolean linked = false;
		try {
			vertexShader = api.createShader(HdShaderApi.VERTEX_SHADER);
			if (vertexShader == 0L) {
				return failure(name, "vertex shader object was not created");
			}
			api.shaderSource(vertexShader, vertexSource);
			api.compileShader(vertexShader);
			if (!api.compileSucceeded(vertexShader)) {
				return failure(name, "vertex compile failed: " + describe(api.shaderInfoLog(vertexShader)));
			}

			fragmentShader = api.createShader(HdShaderApi.FRAGMENT_SHADER);
			if (fragmentShader == 0L) {
				return failure(name, "fragment shader object was not created");
			}
			api.shaderSource(fragmentShader, fragmentSource);
			api.compileShader(fragmentShader);
			if (!api.compileSucceeded(fragmentShader)) {
				return failure(name, "fragment compile failed: " + describe(api.shaderInfoLog(fragmentShader)));
			}

			program = api.createProgram();
			if (program == 0L) {
				return failure(name, "program object was not created");
			}
			api.attach(program, vertexShader);
			api.attach(program, fragmentShader);
			api.link(program);
			if (!api.linkSucceeded(program)) {
				return failure(name, "link failed: " + describe(api.programInfoLog(program)));
			}
			api.detach(program, vertexShader);
			api.detach(program, fragmentShader);
			linked = true;
			return new Result(new HdProgram(api, program), "");
		} catch (RuntimeException ex) {
			return failure(name, "GL error: " + ex);
		} finally {
			// Shaders are reference-counted by the program object; deleting our
			// references after linking (or on any failure) is always correct.
			api.delete(vertexShader);
			api.delete(fragmentShader);
			if (!linked && program != 0L) {
				api.delete(program);
			}
		}
	}

	private static Result failure(String name, String reason) {
		return new Result(null, name + ": " + reason);
	}

	private static String describe(String driverLog) {
		return driverLog == null || driverLog.length() == 0 ? "(no driver message)" : driverLog;
	}
}
