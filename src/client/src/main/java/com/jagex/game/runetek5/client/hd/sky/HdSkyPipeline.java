package com.jagex.game.runetek5.client.hd.sky;

import com.jagex.game.runetek5.client.hd.shader.HdProgram;
import com.jagex.game.runetek5.client.hd.shader.HdProgramCompiler;
import com.jagex.game.runetek5.client.hd.shader.HdShaderApi;

import jaggl.OpenGL;

import org.lwjgl.opengl.GL11;

/**
 * Owns 592HD's sky program for one GL context, and the state sandwich around
 * the one quad it draws.
 *
 * <p>The pass is a single full-screen quad per frame: the shader turns each
 * pixel into a direction and evaluates four colours. Everything it needs comes
 * from the frame it is drawn into - the toolkit's camera, projection, sun and
 * fog colour - so there is no target, no copy and no per-frame allocation.
 *
 * <p>State discipline is the water and post passes': the pass is a guest in
 * the scene's context. It saves the enable group, the colour and depth groups
 * and the current program, draws with depth test and depth writes off (a
 * background must not occlude the world), then restores every one of them and
 * returns to whatever program was bound before - which is normally the fixed
 * function pipeline. Should the driver refuse the shader, the failure is
 * reported once and the frame keeps the colour the stock clear left, which is
 * exactly the frame a client with the row off gets.
 */
public final class HdSkyPipeline {

	/** {@code GL_CURRENT_PROGRAM}; LWJGL's binding does not name it. */
	private static final int CURRENT_PROGRAM = 0x8B8D;

	/** {@code GL_ENABLE_BIT}: every {@code glEnable}/{@code glDisable} state. */
	private static final int ENABLE_BIT = 0x2000;

	/** {@code GL_DEPTH_BUFFER_BIT}: depth mask, func and range. */
	private static final int DEPTH_BUFFER_BIT = 0x0100;

	/** {@code GL_COLOR_BUFFER_BIT}: the colour mask and the clear colour. */
	private static final int COLOR_BUFFER_BIT = 0x4000;

	/** {@code GL_POLYGON_BIT}: polygon mode and culling. */
	private static final int POLYGON_BIT = 0x0008;

	private static final int DEPTH_TEST = 2929;

	private static final int BLEND = 3042;

	private static final int ALPHA_TEST = 3008;

	private static final int CULL_FACE = 2884;

	private static final int LIGHTING = 2896;

	private static final int FOG = 2912;

	private static final int PROJECTION = 5889;

	private static final int MODELVIEW = 5888;

	private static final int QUADS = 7;

	private final HdShaderApi api;

	private HdProgram program;

	private boolean attempted;

	private boolean reported;

	private String failure = "";

	private long draws;

	public HdSkyPipeline(HdShaderApi api) {
		this.api = api;
	}

	/** True once the pass has linked. Never compiles. */
	public boolean available() {
		return this.program != null && this.program.usable();
	}

	/** How many skies have been drawn; zero means the seam is dead. */
	public long draws() {
		return this.draws;
	}

	/** The one-line report of a failed compile, or "". */
	public String logLine() {
		return this.failure.isEmpty() ? "" : "[592HD] sky unavailable - " + this.failure;
	}

	/** Compiles the pass if that has not been attempted yet. */
	public boolean prepare() {
		if (this.attempted) {
			return available();
		}
		this.attempted = true;
		HdProgramCompiler.Result result = HdProgramCompiler.compileSky(this.api);
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
	 * Draws one frame's sky over the whole viewport.
	 *
	 * @param projection the projection matrix the scene is using, as
	 *                   {@code glLoadMatrixf} takes it
	 * @param camera     the scene-to-eye matrix the modelview is built from
	 * @param colours    the four colours, from {@link HdSkyColours#resolve}
	 * @param sunDir     the lighting sun's direction, normalised
	 * @return true when the quad was drawn; false leaves the context untouched
	 */
	public boolean draw(float[] projection, float[] camera, HdSkyColours colours, float[] sunDir) {
		if (colours == null || !colours.sky()) {
			return false;
		}
		if (!this.attempted) {
			prepare();
		}
		if (!available()) {
			return false;
		}
		float[] projectionTerms = SkyRaySpace.projection(projection, null);
		if (projectionTerms[0] == 0.0F || projectionTerms[1] == 0.0F) {
			// A degenerate projection has no rays to cast; draw nothing rather
			// than divide by zero into a full-screen smudge.
			return false;
		}
		float[] axes = SkyRaySpace.axes(camera, null);

		// LWJGL's glGetInteger is used for the program query: the jaggl
		// wrapper's glGetIntegerv copy-back returns zeroes for it.
		int previousProgram = GL11.glGetInteger(CURRENT_PROGRAM);

		OpenGL.glPushAttrib(ENABLE_BIT | DEPTH_BUFFER_BIT | COLOR_BUFFER_BIT | POLYGON_BIT);
		OpenGL.glDisable(DEPTH_TEST);
		OpenGL.glDepthMask(false);
		OpenGL.glDisable(BLEND);
		OpenGL.glDisable(ALPHA_TEST);
		OpenGL.glDisable(CULL_FACE);
		OpenGL.glDisable(LIGHTING);
		OpenGL.glDisable(FOG);

		// Identity in both slots: the quad is submitted in clip space, and the
		// camera reaches the shader as three axes instead of a matrix.
		OpenGL.glMatrixMode(PROJECTION);
		OpenGL.glPushMatrix();
		OpenGL.glLoadIdentity();
		OpenGL.glMatrixMode(MODELVIEW);
		OpenGL.glPushMatrix();
		OpenGL.glLoadIdentity();

		this.api.use(this.program.handle());
		upload(colours, sunDir, axes, projectionTerms);
		OpenGL.glBegin(QUADS);
		OpenGL.glVertex2f(-1.0F, -1.0F);
		OpenGL.glVertex2f(1.0F, -1.0F);
		OpenGL.glVertex2f(1.0F, 1.0F);
		OpenGL.glVertex2f(-1.0F, 1.0F);
		OpenGL.glEnd();
		this.api.use(previousProgram);

		OpenGL.glMatrixMode(MODELVIEW);
		OpenGL.glPopMatrix();
		OpenGL.glMatrixMode(PROJECTION);
		OpenGL.glPopMatrix();
		OpenGL.glMatrixMode(MODELVIEW);
		OpenGL.glPopAttrib();

		this.draws++;
		return true;
	}

	private void upload(HdSkyColours colours, float[] sunDir, float[] axes, float[] projection) {
		HdProgram bound = this.program;
		this.api.uniform2f(bound.uniform(HdSkySource.UNIFORM_PROJ_SCALE), projection[0], projection[1]);
		this.api.uniform2f(bound.uniform(HdSkySource.UNIFORM_PROJ_OFFSET), projection[2], projection[3]);
		this.api.uniform4f(bound.uniform(HdSkySource.UNIFORM_EYE_X), axes[0], axes[1], axes[2], 0.0F);
		this.api.uniform4f(bound.uniform(HdSkySource.UNIFORM_EYE_Y), axes[3], axes[4], axes[5], 0.0F);
		this.api.uniform4f(bound.uniform(HdSkySource.UNIFORM_EYE_Z), axes[6], axes[7], axes[8], 0.0F);
		this.api.uniform4f(bound.uniform(HdSkySource.UNIFORM_SUN_DIR), sunDir[0], sunDir[1], sunDir[2], 0.0F);
		float[] sun = colours.sun();
		this.api.uniform4f(bound.uniform(HdSkySource.UNIFORM_SUN_COLOUR), sun[0], sun[1], sun[2], 1.0F);
		float[] horizon = colours.horizon();
		this.api.uniform4f(bound.uniform(HdSkySource.UNIFORM_HORIZON), horizon[0], horizon[1], horizon[2], 1.0F);
		float[] zenith = colours.zenith();
		this.api.uniform4f(bound.uniform(HdSkySource.UNIFORM_ZENITH), zenith[0], zenith[1], zenith[2], 1.0F);
		float[] ambient = colours.ambient();
		this.api.uniform4f(bound.uniform(HdSkySource.UNIFORM_AMBIENT), ambient[0], ambient[1], ambient[2], 1.0F);
		this.api.uniform4f(bound.uniform(HdSkySource.UNIFORM_SUN_SHAPE), HdSkyColours.SUN_COS_INNER,
			HdSkyColours.SUN_COS_OUTER, HdSkyColours.SUN_GLOW, 0.0F);
	}

	/** Releases the program; called from the toolkit's teardown. */
	public void shutdown() {
		if (this.program != null) {
			this.api.delete(this.program.handle());
			this.program = null;
		}
		this.attempted = true;
		this.draws = 0L;
	}

	private void report() {
		if (this.reported) {
			return;
		}
		this.reported = true;
		System.out.println(logLine());
	}
}
