package com.jagex.game.runetek5.client.hd.post;

import com.jagex.game.runetek5.client.hd.shader.HdProgram;
import com.jagex.game.runetek5.client.hd.shader.HdProgramCompiler;
import com.jagex.game.runetek5.client.hd.shader.HdShaderApi;

import org.lwjgl.opengl.GL11;

import jaggl.OpenGL;

/**
 * Runs 592HD's screen-space pass over the composited frame.
 *
 * <p>The client already composites the scene into display targets; this pass
 * sits after that and after the interface, at the very end of presentation,
 * because the rows it carries (tonemap, grading, dither, FXAA) are display
 * effects: they must see the finished frame, not individual draws.
 *
 * <p>It copies the back buffer into one screen-sized texture and draws a
 * full-screen quad through the program. There is no render target and no second
 * copy: the copied texture is the input, the back buffer is the output, so the
 * cost is one blit and one textured quad per frame with any row on.
 *
 * <p>State discipline is the same as the water pass: the pass pushes the
 * enable and texture attribute groups, saves the program and the unit-0
 * binding, restores all of them, and never leaves the fixed-function pipeline
 * holding a stale binding. If the driver refuses the program, the pass reports
 * once and the frame is presented exactly as the stock compositor left it.
 */
public final class HdPostProcess {

	private static final int TEXTURE_2D = 3553;

	private static final int RGBA = 6408;

	private static final int UNSIGNED_BYTE = 5121;

	private static final int RGBA8 = 32856;

	private static final int LINEAR = 9729;

	private static final int CLAMP_TO_EDGE = 33071;

	private static final int TEXTURE_MIN_FILTER = 10241;

	private static final int TEXTURE_MAG_FILTER = 10240;

	private static final int TEXTURE_WRAP_S = 10242;

	private static final int TEXTURE_WRAP_T = 10243;

	private static final int TEXTURE_BINDING_2D = 32873;

	private static final int ACTIVE_TEXTURE = 34016;

	private static final int TEXTURE0 = 33984;

	/** {@code GL_CURRENT_PROGRAM}; LWJGL's binding does not name it. */
	private static final int CURRENT_PROGRAM = 0x8B8D;

	private static final int PROJECTION = 5889;

	private static final int MODELVIEW = 5888;

	private static final int QUADS = 7;

	/** {@code GL_ENABLE_BIT}: every {@code glEnable}/{@code glDisable} state. */
	private static final int ENABLE_BIT = 0x2000;

	/** {@code GL_TEXTURE_BIT}: the texture-unit bindings and environment. */
	private static final int TEXTURE_BIT = 0x40000;

	private static final int BLEND = 3042;

	private static final int DEPTH_TEST = 2929;

	private static final int ALPHA_TEST = 3008;

	private static final int CULL_FACE = 2884;

	private static final int LIGHTING = 2896;

	private static final int FOG = 2912;

	private static final int SCISSOR_TEST = 2960;

	private static final int TEXTURE_STATE = 3553;

	private final HdShaderApi api;

	private HdProgram program;

	private boolean attempted;

	private boolean reported;

	private String failure = "";

	private int texture;

	private int textureWidth;

	private int textureHeight;

	private long frames;

	public HdPostProcess(HdShaderApi api) {
		this.api = api;
	}

	/** True once the pass has linked. Never compiles. */
	public boolean available() {
		return this.program != null && this.program.usable();
	}

	/** How many frames the pass has processed; zero means the seam is dead. */
	public long frames() {
		return this.frames;
	}

	/** The one-line report of a failed compile, or "". */
	public String logLine() {
		return this.failure.isEmpty() ? "" : "[592HD] post-processing unavailable - " + this.failure;
	}

	/** Compiles the pass if that has not been attempted yet. */
	public boolean prepare() {
		if (this.attempted) {
			return available();
		}
		this.attempted = true;
		HdProgramCompiler.Result result = HdProgramCompiler.compilePost(this.api);
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
	 * Processes one frame.
	 *
	 * @param x      left edge of the region to process, in window coordinates
	 * @param y      bottom edge, in window coordinates
	 * @param width  region width in pixels
	 * @param height region height in pixels
	 * @param params what to apply; {@link HdPostParams#OFF} or null is a no-op
	 * @return true when the pass actually drew
	 */
	public boolean apply(int x, int y, int width, int height, HdPostParams params) {
		if (params == null || !params.any() || width <= 0 || height <= 0) {
			return false;
		}
		if (!this.attempted) {
			prepare();
		}
		if (!available()) {
			return false;
		}

		// LWJGL's glGetInteger is used for the three state queries: the jaggl
		// wrapper's glGetIntegerv copy-back returns zeroes for them.
		int previousProgram = GL11.glGetInteger(CURRENT_PROGRAM);
		int previousUnit = GL11.glGetInteger(ACTIVE_TEXTURE);
		// The sampler reads unit 0, so bind the copy there and put the unit back
		// afterwards; the compositor leaves another unit active.
		OpenGL.glActiveTexture(TEXTURE0);
		int previousTexture = GL11.glGetInteger(TEXTURE_BINDING_2D);
		ensureTexture(width, height);

		OpenGL.glPushAttrib(ENABLE_BIT | TEXTURE_BIT);
		OpenGL.glDisable(BLEND);
		OpenGL.glDisable(DEPTH_TEST);
		OpenGL.glDisable(ALPHA_TEST);
		OpenGL.glDisable(CULL_FACE);
		OpenGL.glDisable(LIGHTING);
		OpenGL.glDisable(FOG);
		OpenGL.glDisable(SCISSOR_TEST);
		OpenGL.glDisable(TEXTURE_STATE);

		// The back buffer is the only input; the copy keeps the pixel centres
		// aligned with the quad, so a 1:1 blit is exact.
		OpenGL.glBindTexture(TEXTURE_2D, this.texture);
		OpenGL.glCopyTexSubImage2D(TEXTURE_2D, 0, 0, 0, x, y, width, height);

		OpenGL.glMatrixMode(PROJECTION);
		OpenGL.glPushMatrix();
		OpenGL.glLoadIdentity();
		OpenGL.glOrtho(x, x + width, y, y + height, -1.0D, 1.0D);
		OpenGL.glMatrixMode(MODELVIEW);
		OpenGL.glPushMatrix();
		OpenGL.glLoadIdentity();

		this.api.use(this.program.handle());
		upload(params, width, height);
		OpenGL.glBegin(QUADS);
		OpenGL.glTexCoord2f(0.0F, 0.0F);
		OpenGL.glVertex2i(x, y);
		OpenGL.glTexCoord2f(1.0F, 0.0F);
		OpenGL.glVertex2i(x + width, y);
		OpenGL.glTexCoord2f(1.0F, 1.0F);
		OpenGL.glVertex2i(x + width, y + height);
		OpenGL.glTexCoord2f(0.0F, 1.0F);
		OpenGL.glVertex2i(x, y + height);
		OpenGL.glEnd();
		this.api.use(HdShaderApi.NO_PROGRAM);

		OpenGL.glMatrixMode(MODELVIEW);
		OpenGL.glPopMatrix();
		OpenGL.glMatrixMode(PROJECTION);
		OpenGL.glPopMatrix();
		OpenGL.glMatrixMode(MODELVIEW);
		OpenGL.glPopAttrib();

		OpenGL.glBindTexture(TEXTURE_2D, previousTexture);
		OpenGL.glActiveTexture(previousUnit);
		if (previousProgram != 0) {
			this.api.use(previousProgram);
		}
		this.frames++;
		return true;
	}

	private void upload(HdPostParams params, int width, int height) {
		HdProgram bound = this.program;
		this.api.uniform1i(bound.uniform(HdPostSource.UNIFORM_SCREEN), 0);
		this.api.uniform2f(bound.uniform(HdPostSource.UNIFORM_TEXEL), 1.0F / width, 1.0F / height);
		this.api.uniform1f(bound.uniform(HdPostSource.UNIFORM_EXPOSURE), params.exposure());
		this.api.uniform1f(bound.uniform(HdPostSource.UNIFORM_TONEMAP), params.tonemapFlag());
		this.api.uniform1f(bound.uniform(HdPostSource.UNIFORM_GRADE), params.gradingFlag());
		float[] gain = params.gain();
		float[] lift = params.lift();
		this.api.uniform4f(bound.uniform(HdPostSource.UNIFORM_GAIN), gain[0], gain[1], gain[2], 0.0F);
		this.api.uniform4f(bound.uniform(HdPostSource.UNIFORM_LIFT), lift[0], lift[1], lift[2], 0.0F);
		this.api.uniform1f(bound.uniform(HdPostSource.UNIFORM_SATURATION), params.saturation());
		this.api.uniform1f(bound.uniform(HdPostSource.UNIFORM_DITHER), params.ditherAmount());
		this.api.uniform1f(bound.uniform(HdPostSource.UNIFORM_FXAA), params.fxaaFlag());
		this.api.uniform1f(bound.uniform(HdPostSource.UNIFORM_KNEE), params.knee());
		this.api.uniform1f(bound.uniform(HdPostSource.UNIFORM_SHOULDER), params.shoulder());
	}

	/** (Re)allocates the frame copy when the region changes size. */
	private void ensureTexture(int width, int height) {
		if (this.texture == 0) {
			int[] names = new int[1];
			OpenGL.glGenTextures(1, names, 0);
			this.texture = names[0];
		}
		if (this.textureWidth == width && this.textureHeight == height) {
			return;
		}
		OpenGL.glBindTexture(TEXTURE_2D, this.texture);
		OpenGL.glTexParameteri(TEXTURE_2D, TEXTURE_MIN_FILTER, LINEAR);
		OpenGL.glTexParameteri(TEXTURE_2D, TEXTURE_MAG_FILTER, LINEAR);
		OpenGL.glTexParameteri(TEXTURE_2D, TEXTURE_WRAP_S, CLAMP_TO_EDGE);
		OpenGL.glTexParameteri(TEXTURE_2D, TEXTURE_WRAP_T, CLAMP_TO_EDGE);
		// The jaggl wrapper has no null-data upload: the zeroed array is only
		// touched on a resize, and glCopyTexSubImage2D overwrites it every frame.
		OpenGL.glTexImage2Dub(TEXTURE_2D, 0, RGBA8, width, height, 0, RGBA, UNSIGNED_BYTE,
			new byte[width * height * 4], 0);
		this.textureWidth = width;
		this.textureHeight = height;
	}

	/** Releases the program and the frame copy; called from the toolkit's teardown. */
	public void shutdown() {
		if (this.program != null) {
			this.api.delete(this.program.handle());
			this.program = null;
		}
		if (this.texture != 0) {
			int[] names = { this.texture };
			OpenGL.glDeleteTextures(1, names, 0);
			this.texture = 0;
		}
		this.textureWidth = 0;
		this.textureHeight = 0;
		this.attempted = true;
		this.frames = 0L;
	}

	private void report() {
		if (this.reported) {
			return;
		}
		this.reported = true;
		System.out.println(logLine());
	}
}
