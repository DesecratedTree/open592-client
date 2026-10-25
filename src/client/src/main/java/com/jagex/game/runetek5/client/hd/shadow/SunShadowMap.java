package com.jagex.game.runetek5.client.hd.shadow;

import jaggl.OpenGL;
import org.lwjgl.opengl.GL11;

/**
 * The sun's depth map: one target, rebuilt every frame, and the GL state
 * sandwich that lets the scene draw casters into it without changing anything
 * the stock path sees afterwards.
 *
 * <p>Why a depth texture rather than the toolkit's {@code GlFramebuffer} +
 * {@code OpenGlRenderBuffer} pair: a renderbuffer cannot be sampled, and the
 * shader has to read this map to decide whether each fragment sees the sun.
 * {@code GlFramebuffer} only models 2D/cube colour attachments and
 * renderbuffers, so this class owns the one target the toolkit does not already
 * have, using the same {@code jaggl} entry points and constants
 * {@code GlFramebuffer} uses.
 *
 * <p>State discipline: {@code beginPass} pushes {@code GL_ENABLE_BIT} and both
 * matrices and saves everything else it touches (framebuffer binding, viewport,
 * draw/read buffer, depth function, colour and depth write masks, clear depth).
 * {@code endPass} restores them in reverse. That matters twice over: the toolkit
 * caches its own copy of the enable flags, so leaving one flipped would make it
 * skip a needed GL call later, and a fixed-function draw after the pass must be
 * bit-identical to one before it. The pixel harness checks exactly that.
 *
 * <p>The pass deliberately does not enable polygon offset: the toolkit's jaggl
 * wrapper has no {@code glPolygonOffset}, so the depth bias is applied in the
 * shader instead, where it also scales with the volume size.
 */
public final class SunShadowMap implements SunShadowFrame.Surface {

	/** Texture unit the lighting shader samples; unit 0 is the material. */
	public static final int TEXTURE_UNIT = 1;

	private static final int GL_TEXTURE_2D = 3553;

	private static final int GL_TEXTURE_MIN_FILTER = 10241;

	private static final int GL_TEXTURE_MAG_FILTER = 10240;

	private static final int GL_TEXTURE_WRAP_S = 10242;

	private static final int GL_TEXTURE_WRAP_T = 10243;

	private static final int GL_LINEAR = 9729;

	private static final int GL_CLAMP_TO_EDGE = 33071;

	private static final int GL_DEPTH_COMPONENT = 6402;

	private static final int GL_UNSIGNED_INT = 5125;

	private static final int GL_FRAMEBUFFER_EXT = 36160;

	private static final int GL_FRAMEBUFFER_BINDING_EXT = 36006;

	private static final int GL_DEPTH_ATTACHMENT_EXT = 36096;

	private static final int GL_FRAMEBUFFER_COMPLETE_EXT = 36053;

	private static final int GL_NONE = 0;

	private static final int GL_DRAW_BUFFER = 3073;

	private static final int GL_READ_BUFFER = 3074;

	private static final int GL_VIEWPORT = 2978;

	private static final int GL_DEPTH_FUNC = 2932;

	private static final int GL_DEPTH_WRITEMASK = 2930;

	private static final int GL_COLOR_WRITEMASK = 3106;

	private static final int GL_DEPTH_CLEAR_VALUE = 2931;

	private static final int GL_ENABLE_BIT = 2048;

	private static final int GL_DEPTH_TEST = 2929;

	private static final int GL_SCISSOR_TEST = 3089;

	private static final int GL_SCISSOR_BOX = 3088;

	private static final int GL_CULL_FACE = 2884;

	/** The function and reference, the other two thirds of the alpha test. */
	private static final int GL_ALPHA_TEST_FUNC = 3009;

	private static final int GL_ALPHA_TEST_REF = 3010;

	private static final int GL_LEQUAL = 515;

	private static final int GL_PROJECTION = 5889;

	private static final int GL_MODELVIEW = 5888;

	private static final int GL_DEPTH_BUFFER_BIT = 256;

	private static final int GL_TEXTURE0 = 33984;

	private static final int GL_ACTIVE_TEXTURE = 34016;

	private final int[] name = new int[1];

	private final int[] savedInts = new int[9];

	private final int[] savedViewport = new int[4];

	private final int[] savedScissor = new int[4];

	/** Saved before {@code beginPass} so the pass can draw the whole target. */
	private boolean savedScissorTest;

	/**
	 * Saved before {@code beginPass} because the pass draws casters two-sided.
	 *
	 * <p>A depth map has to be solid: whether a caster's triangle winding faces
	 * the light or away from it says nothing about whether the object blocks the
	 * sun. The stock renderer leaves {@code GL_CULL_FACE} enabled for its model
	 * draws, and this pass runs before the world with whatever the previous
	 * frame left, so inheriting the flag silently dropped half of every closed
	 * caster. A ground test that ran after a frame whose setup disabled culling
	 * masked it; the single-test filter did not.
	 */
	private boolean savedCull;

	/** Whether this pass alpha-tested casters, so the end restores the flag. */
	private boolean cutout;

	private int framebuffer;

	private int texture;

	private int size;

	private boolean ready;

	private boolean failed;

	private boolean inPass;

	private boolean savedDepthTest;

	/** Saved before the pass so a cut-out pass leaves the scene's flag alone. */
	private boolean savedAlphaTest;

	/** Saved with the flag: enabling the test alone does not restore it. */
	private int savedAlphaFunc;

	private float savedAlphaRef;

	private final float[] savedAlphaRefs = new float[1];

	private int frameId;

	private float radius = 1.0F;

	private float[] clipMatrix = new float[16];

	private float[] textureMatrix = new float[16];

	public SunShadowMap() {
	}

	/**
	 * Creates or resizes the target.
	 *
	 * @param requested edge in texels; clamped into
	 *        {@link SunShadowGate#MIN_SIZE}..{@link SunShadowGate#MAX_SIZE}
	 * @return true when a complete target exists at that size
	 */
	@Override
	public boolean prepare(int requested) {
		int wanted = SunShadowGate.clampResolution(requested);
		if (this.ready && this.size == wanted) {
			return true;
		}
		if (this.failed && this.size == wanted) {
			return false;
		}
		release();
		this.size = wanted;

		// The target this build must not disturb: creating the map binds a
		// framebuffer of its own, and the caller is already drawing into one
		// (the toolkit's framebuffer for a multisampled frame, or the screen).
		// Restoring "0" instead put every later draw of that frame into the
		// window - the world, the water and the interface all vanished from the
		// target being composed. Read through LWJGL, because the jaggl wrapper's
		// copy-back answers 0 for this enum.
		int previousFramebuffer = GL11.glGetInteger(GL_FRAMEBUFFER_BINDING_EXT);
		// The toolkit caches which unit is active, so put it back afterwards.
		int active = queryActiveTexture();
		OpenGL.glActiveTexture(GL_TEXTURE0 + TEXTURE_UNIT);
		OpenGL.glGenTextures(1, this.name, 0);
		this.texture = this.name[0];
		OpenGL.glBindTexture(GL_TEXTURE_2D, this.texture);
		OpenGL.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		OpenGL.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		OpenGL.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		OpenGL.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		OpenGL.glTexImage2Di(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, this.size, this.size, 0,
			GL_DEPTH_COMPONENT, GL_UNSIGNED_INT, null, 0);

		OpenGL.glGenFramebuffersEXT(1, this.name, 0);
		this.framebuffer = this.name[0];
		OpenGL.glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, this.framebuffer);
		OpenGL.glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT, GL_DEPTH_ATTACHMENT_EXT, GL_TEXTURE_2D,
			this.texture, 0);
		// No colour attachment: without this the target is incomplete.
		OpenGL.glDrawBuffer(GL_NONE);
		OpenGL.glReadBuffer(GL_NONE);
		int status = OpenGL.glCheckFramebufferStatusEXT(GL_FRAMEBUFFER_EXT);
		OpenGL.glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, previousFramebuffer);
		OpenGL.glBindTexture(GL_TEXTURE_2D, 0);
		OpenGL.glActiveTexture(active);

		if (status != GL_FRAMEBUFFER_COMPLETE_EXT) {
			release();
			this.failed = true;
			this.size = wanted;
			System.out.println("[592HD] sun shadow map unavailable - depth target status 0x"
				+ Integer.toHexString(status));
			return false;
		}
		this.ready = true;
		this.failed = false;
		return true;
	}

	/** True when a complete target exists and a pass may be started. */
	public boolean ready() {
		return this.ready && !this.inPass;
	}

	public int size() {
		return this.size;
	}

	/**
	 * Starts the depth-only pass: binds the map, sets the sun's clip matrix,
	 * clears depth and disables colour writes.
	 *
	 * @param radius half-extent of the volume around the centre
	 * @return false when the map is missing or a pass is already open
	 */
	@Override
	public boolean beginPass(float sunX, float sunY, float sunZ, float centreX, float centreY,
			float centreZ, float radius) {
		if (!ready()) {
			return false;
		}
		this.radius = radius;
		this.clipMatrix = SunShadowFit.clipMatrix(sunX, sunY, sunZ, centreX, centreY, centreZ, radius);
		this.textureMatrix = SunShadowFit.bias(this.clipMatrix);
		this.frameId++;

		this.savedInts[0] = GL11.glGetInteger(GL_FRAMEBUFFER_BINDING_EXT);
		OpenGL.glGetIntegerv(GL_DRAW_BUFFER, this.savedInts, 1);
		OpenGL.glGetIntegerv(GL_READ_BUFFER, this.savedInts, 2);
		OpenGL.glGetIntegerv(GL_DEPTH_FUNC, this.savedInts, 3);
		OpenGL.glGetIntegerv(GL_VIEWPORT, this.savedViewport, 0);
		// Saved by hand rather than trusting the attribute stack: on the driver
		// the pixel harness runs on, glPopAttrib does not bring GL_DEPTH_TEST
		// back, and a leftover enable would make the toolkit's cached flags lie.
		// glGetIntegerv on a capability enum answers the enable state directly;
		// jaggl has no glIsEnabled.
		OpenGL.glGetIntegerv(GL_DEPTH_TEST, this.savedInts, 4);
		this.savedDepthTest = this.savedInts[4] != 0;
		// The target is smaller than the canvas the world is drawn into, and the
		// scene may have a scissor rectangle set for an interface or a viewport
		// corner. The pass owns the whole target, so the scissor is saved and
		// switched off; leaving it on would clip the map to a rectangle of the
		// previous frame's screen and casters outside it would not cast.
		OpenGL.glGetIntegerv(GL_SCISSOR_TEST, this.savedInts, 5);
		this.savedScissorTest = this.savedInts[5] != 0;
		OpenGL.glGetIntegerv(GL_SCISSOR_BOX, this.savedScissor, 0);
		OpenGL.glGetIntegerv(GL_CULL_FACE, this.savedInts, 6);
		this.savedCull = this.savedInts[6] != 0;
		OpenGL.glGetIntegerv(ShadowCutout.GL_ALPHA_TEST, this.savedInts, 7);
		this.savedAlphaTest = this.savedInts[7] != 0;
		OpenGL.glGetIntegerv(GL_ALPHA_TEST_FUNC, this.savedInts, 8);
		this.savedAlphaFunc = this.savedInts[8];
		OpenGL.glGetFloatv(GL_ALPHA_TEST_REF, this.savedAlphaRefs, 0);
		this.savedAlphaRef = this.savedAlphaRefs[0];
		// A caster's texture alpha is what its shape is made of. Testing it
		// while the pass draws is the whole cut-out row; with the row off the
		// pass still switches the test off, so a caster always casts its full
		// silhouette rather than inheriting a leftover reference from the
		// scene's last sprite draw.
		this.cutout = ShadowCutout.enabled();

		OpenGL.glPushAttrib(GL_ENABLE_BIT);
		OpenGL.glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, this.framebuffer);
		OpenGL.glDrawBuffer(GL_NONE);
		OpenGL.glReadBuffer(GL_NONE);
		OpenGL.glViewport(0, 0, this.size, this.size);
		OpenGL.glColorMask(false, false, false, false);
		OpenGL.glDepthMask(true);
		OpenGL.glEnable(GL_DEPTH_TEST);
		OpenGL.glDepthFunc(GL_LEQUAL);
		OpenGL.glDisable(GL_SCISSOR_TEST);
		// Two-sided: a caster's silhouette is the same whichever way its
		// triangles wind, so the depth map must not inherit the scene's culling.
		OpenGL.glDisable(GL_CULL_FACE);
		if (this.cutout) {
			OpenGL.glEnable(ShadowCutout.GL_ALPHA_TEST);
			OpenGL.glAlphaFunc(ShadowCutout.GL_GREATER, ShadowCutout.THRESHOLD);
		} else {
			OpenGL.glDisable(ShadowCutout.GL_ALPHA_TEST);
		}
		OpenGL.glClearDepth(1.0F);
		OpenGL.glClear(GL_DEPTH_BUFFER_BIT);

		OpenGL.glMatrixMode(GL_PROJECTION);
		OpenGL.glPushMatrix();
		OpenGL.glLoadMatrixf(this.clipMatrix, 0);
		OpenGL.glMatrixMode(GL_MODELVIEW);
		OpenGL.glPushMatrix();
		OpenGL.glLoadIdentity();

		this.inPass = true;
		return true;
	}

	/** Ends the pass and puts every saved piece of state back. */
	@Override
	public void endPass() {
		if (!this.inPass) {
			return;
		}
		OpenGL.glPopMatrix();
		OpenGL.glMatrixMode(GL_PROJECTION);
		OpenGL.glPopMatrix();
		OpenGL.glMatrixMode(GL_MODELVIEW);
		OpenGL.glPopAttrib();

		// The masks and the clear depth are put back to the state the toolkit
		// keeps at a model draw rather than saved: this jaggl wrapper has no
		// working read-back for them, and every caller of a model draw has
		// colour and depth writes on by construction. The pixel harness proves
		// a fixed-function frame after a pass is identical to one before it.
		OpenGL.glColorMask(true, true, true, true);
		OpenGL.glDepthMask(true);
		OpenGL.glClearDepth(1.0F);
		if (this.savedDepthTest) {
			OpenGL.glEnable(GL_DEPTH_TEST);
		} else {
			OpenGL.glDisable(GL_DEPTH_TEST);
		}
		OpenGL.glDepthFunc(this.savedInts[3]);
		OpenGL.glViewport(this.savedViewport[0], this.savedViewport[1], this.savedViewport[2],
			this.savedViewport[3]);
		OpenGL.glScissor(this.savedScissor[0], this.savedScissor[1], this.savedScissor[2],
			this.savedScissor[3]);
		if (this.savedScissorTest) {
			OpenGL.glEnable(GL_SCISSOR_TEST);
		} else {
			OpenGL.glDisable(GL_SCISSOR_TEST);
		}
		if (this.savedCull) {
			OpenGL.glEnable(GL_CULL_FACE);
		} else {
			OpenGL.glDisable(GL_CULL_FACE);
		}
		// All three parts of the scene's alpha test go back together: the pass
		// changed the function and reference as well as the flag, and restoring
		// only the flag would leave our half-opaque threshold behind for the
		// next sprite. This runs whether or not the row was on, because the
		// row-off pass disables the test while it draws.
		OpenGL.glAlphaFunc(this.savedAlphaFunc, this.savedAlphaRef);
		if (this.savedAlphaTest) {
			OpenGL.glEnable(ShadowCutout.GL_ALPHA_TEST);
		} else {
			OpenGL.glDisable(ShadowCutout.GL_ALPHA_TEST);
		}
		this.cutout = false;
		OpenGL.glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, this.savedInts[0]);
		OpenGL.glDrawBuffer(this.savedInts[1]);
		OpenGL.glReadBuffer(this.savedInts[2]);
		this.inPass = false;
	}

	/** True between {@link #beginPass} and {@link #endPass}. */
	public boolean inPass() {
		return this.inPass;
	}

	/**
	 * True while the open pass is alpha-testing casters, which is the only time
	 * their material textures matter to the depth result.
	 */
	public boolean cutoutActive() {
		return this.inPass && this.cutout;
	}

	/** True once at least one depth pass has filled the map. */
	public boolean hasFrame() {
		return this.ready && this.frameId > 0;
	}

	/** How many passes have run; the pipeline uploads a frame's uniforms once. */
	@Override
	public int frameId() {
		return this.frameId;
	}

	/** This frame's uniforms; only meaningful after a pass has run. */
	public ShadowBinding binding(int radius) {
		return new ShadowBinding(this.frameId, this.size, radius, SunShadowFit.depthBias(this.radius), this.textureMatrix);
	}

	/** The world-to-light-clip matrix the pass rasterised with. */
	public float[] clipMatrix() {
		return this.clipMatrix.clone();
	}

	/**
	 * The map's texture name, or 0 before a target exists.
	 *
	 * <p>The map is not a {@code Class6}, so it cannot be bound through
	 * {@code GlToolkit.setActiveTexture} and its unit is not one the toolkit's
	 * cache describes. The toolkit therefore owns the bind: it takes this name
	 * through {@code hdBindShadowTexture} and records it, so the unit's cache
	 * stays a truthful description of the unit instead of claiming it is empty
	 * while a depth map is sampled from it.
	 */
	public int textureName() {
		return this.ready ? this.texture : 0;
	}

	/** {@code GL_ACTIVE_TEXTURE}, so the toolkit's cached unit stays true. */
	private int queryActiveTexture() {
		int[] active = new int[1];
		OpenGL.glGetIntegerv(GL_ACTIVE_TEXTURE, active, 0);
		return active[0];
	}

	/** Deletes the target; safe to call more than once. */
	public void release() {
		if (this.framebuffer != 0) {
			this.name[0] = this.framebuffer;
			OpenGL.glDeleteFramebuffersEXT(1, this.name, 0);
			this.framebuffer = 0;
		}
		if (this.texture != 0) {
			this.name[0] = this.texture;
			OpenGL.glDeleteTextures(1, this.name, 0);
			this.texture = 0;
		}
		this.ready = false;
		this.inPass = false;
	}
}
