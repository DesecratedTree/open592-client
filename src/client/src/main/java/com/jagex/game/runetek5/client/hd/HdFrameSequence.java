package com.jagex.game.runetek5.client.hd;

import com.jagex.game.runetek5.client.hd.light.HdLightFrame;
import com.jagex.game.runetek5.client.hd.shadow.SunShadowSubmission;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

/**
 * Where 592HD's frame stages hang off the stock world pass.
 *
 * <p>The frame is drawn by {@code Skybox.method5247}: it clears the viewport,
 * places the camera, builds the frustum and only then submits the world. The
 * sky is a background, so its slot is that one moment - camera and projection
 * current, nothing drawn over the clear colour yet - and the world geometry
 * afterwards covers it. The sun's depth pass belongs to the same moment, after
 * the background and before the world: the receivers it shadows are drawn by
 * that world pass, and a map built after them would be a frame late. This
 * class is the policy for both calls so the deob method keeps one line each.
 *
 * <p>Every entry point is false when 592HD is off, the stage's row is off, or
 * the drawing toolkit is not OpenGL; {@link GlToolkit#hdSkyFrame()} makes no GL
 * call in any of those cases, so the stock frame is untouched rather than
 * nearly untouched.
 */
public final class HdFrameSequence {

	private HdFrameSequence() {
	}

	/**
	 * Draws the 592HD sky dome into the frame's background.
	 *
	 * <p>A map that brought its own cubemap sky keeps it: the dome fills the
	 * flat fog slot the stock renderer leaves empty, it does not paint over a
	 * sky the world already has.
	 *
	 * @param stockSkyboxDrawn true when this frame already drew the stock
	 *        skybox, in which case the dome stands aside
	 * @return true when the dome was drawn
	 */
	public static boolean drawSky(boolean stockSkyboxDrawn) {
		if (stockSkyboxDrawn) {
			return false;
		}
		GlToolkit toolkit = GlHdGraphicsBackend.activeToolkit();
		return toolkit != null && toolkit.hdSkyFrame();
	}

	/**
	 * Runs this frame's sun depth pass, from the camera's scene position.
	 *
	 * <p>Called at the same seam, after the background and before the world is
	 * submitted, so the map the receivers sample is this frame's geometry and
	 * nothing in the world pass has to know a depth target exists.
	 *
	 * <p>A scene that collects casters calls
	 * {@code hdShadowSubmission().beginFrame(...)} and its {@code add} calls from
	 * this same seam before this method; those casters are submitted as they
	 * stand. With no collection in progress the pass runs with an empty set,
	 * which clears the map rather than leaving last frame's shadow behind.
	 *
	 * <p>No GL call is issued when 592HD, per-pixel lighting or the Sun shadows
	 * row is off: the submission declines the frame before the pass opens and
	 * the stock frame is bit-identical to one from before this stage existed.
	 *
	 * @param cameraX camera's scene x, the unit the shadow volume uses
	 * @param cameraY camera's scene y; the volume is vertical too
	 * @param cameraZ camera's scene z
	 * @return true when the map was rebuilt for this frame
	 */
	public static boolean drawSunShadows(float cameraX, float cameraY, float cameraZ) {
		GlToolkit toolkit = GlHdGraphicsBackend.activeToolkit();
		if (toolkit == null) {
			return false;
		}
		SunShadowSubmission submission = toolkit.hdShadowSubmission();
		// A nested world draw - the stock reflective water effect re-enters the
		// scene to fill its reflection - must not run the pass again. This frame's
		// pass has already run; a second one would rebuild the same map from
		// inside the draw that is using it, with the framebuffer, matrix and
		// texture state half applied, and would submit a caster set the outer
		// draw has not finished filling.
		if (submission.sceneOpen()) {
			return false;
		}
		if (!submission.collecting()) {
			submission.beginFrame(cameraX, cameraY, cameraZ);
		}
		return submission.submit(toolkit);
	}

	/**
	 * Opens the world draw's caster collection: every model the draw submits is
	 * recorded for the next frame's sun map.
	 *
	 * <p>This is the only window in which models are recorded, which is what
	 * keeps inventory icons, model previews and the map bake out of the map. It
	 * is a no-op with 592HD or the Sun shadows row off, and the draw then runs
	 * exactly as the stock one did.
	 */
	public static void beginWorldDraw() {
		// The carried and room light terms, captured once per frame from the light
		// state the stock environment update has just chosen. Both rows off means
		// capture() returns OFF before reading anything.
		HdLightFrame.capture();
		GlToolkit toolkit = GlHdGraphicsBackend.activeToolkit();
		if (toolkit == null) {
			return;
		}
		SunShadowSubmission submission = toolkit.hdShadowSubmission();
		boolean outermost = !submission.sceneOpen();
		submission.enterScene();
		if (outermost) {
			toolkit.hdShadowSceneBegin();
			// The atlas' staleness clock: one generation per outermost world
			// draw, so the cells recycled when the page fills belong to the
			// materials this frame is not drawing.
			toolkit.hdGroundFrameBegin();
		}
	}

	/** Closes the collection {@link #beginWorldDraw()} opened. */
	public static void endWorldDraw() {
		GlToolkit toolkit = GlHdGraphicsBackend.activeToolkit();
		if (toolkit == null) {
			return;
		}
		SunShadowSubmission submission = toolkit.hdShadowSubmission();
		submission.exitScene();
		if (!submission.sceneOpen()) {
			toolkit.hdShadowSceneEnd();
		}
	}
}
