package com.jagex.game.runetek5.client.hd.shadow;

import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.graphics.model.GlModel;

/**
 * The scene renderer's per-frame submission to the sun's depth pass: this
 * frame's volume, this frame's casters, and the counters that say whether the
 * pass ran.
 *
 * <p>The split is the point. The scene owns <em>what casts</em> - it walks the
 * objects it is about to draw and calls {@link #add} with each one's placement
 * and draw - while {@link SunShadowMap} owns the target and {@link SunShadowFrame}
 * owns the order. Nothing here draws the world: the ground is a receiver, not a
 * caster, and is never submitted, which is what keeps the depth map a picture of
 * the objects that block the sun rather than a second copy of the frame.
 *
 * <p>The volume is the camera's, from {@link ShadowVolume#aroundCamera}: a map
 * that follows the eye is the only one whose texels stay small enough for an
 * object's shadow to have an edge. Every frame starts from
 * {@link #beginFrame}, so a caster that left the scene last frame cannot cast
 * this one - the set is cleared and refilled, never appended to across frames.
 *
 * <p>Pure bookkeeping over the toolkit's seam: {@link #submit} makes no GL call
 * itself and {@code hdSunShadowFrame} declines before touching state when 592HD,
 * per-pixel lighting or the Sun shadows row is off. A submission that never runs
 * is still counted, because "no shadow" and "the pass never ran" are different
 * bugs.
 */
public final class SunShadowSubmission {

	/** How many casters a frame may record; beyond this the scene is not mapped. */
	private static final int MAX_PENDING = 8192;

	private final ShadowCasterSet casters = new ShadowCasterSet();

	/**
	 * Casters the world draw recorded, waiting for the next seam. Guarded by
	 * itself: the draw may run on renderer threads, the seam on the client's.
	 */
	private final ShadowCaster[] pending = new ShadowCaster[MAX_PENDING];

	private int pendingCount;

	private long recorded;

	/** World draws sharing this frame's collection; see {@link #enterScene()}. */
	private int sceneDepth;

	private final float[] centre = new float[3];

	private float radius = ShadowVolume.DEFAULT_RADIUS;

	private boolean collecting;

	private long frames;

	private long passes;

	private long skipped;

	private long submitted;

	/**
	 * Starts this frame's submission: clears last frame's casters and centres
	 * the volume on the camera, in scene units.
	 *
	 * <p>Call once per frame, before any {@link #add}, and never while a pass is
	 * open. No GL call: this is safe with the row off.
	 */
	public void beginFrame(float cameraX, float cameraY, float cameraZ) {
		this.beginFrame(cameraX, cameraY, cameraZ, ShadowVolume.DEFAULT_RADIUS);
	}

	/**
	 * Starts this frame's submission with an explicit volume radius.
	 *
	 * <p>The scene names the volume when the camera's default is the wrong size
	 * for what it is about to submit - a map covering 2048 units has 4-unit
	 * texels at 1024, and an object narrower than that never reaches a texel
	 * centre. Clamped by {@link ShadowVolume#around}.
	 */
	public void beginFrame(float centreX, float centreY, float centreZ, float radius) {
		ShadowVolume volume = ShadowVolume.around(centreX, centreY, centreZ, radius);
		this.centre[0] = volume.centreX();
		this.centre[1] = volume.centreY();
		this.centre[2] = volume.centreZ();
		this.radius = volume.radius();
		this.casters.clear();
		this.submitted = 0L;
		this.collecting = true;
		// The world draw before this seam recorded the objects it drew. They are
		// this frame's casters: one frame of lag is the cost of a pass that runs
		// before the draw it shadows, and a stationary object's shadow is exact.
		this.recorded = this.drainPending();
	}

	/**
	 * Records a model the world draw is about to submit: its placement and the
	 * depth draw that reproduces it in scene space.
	 *
	 * <p>Called from the renderer threads as objects are drawn, so it takes the
	 * pending lock and never touches GL. A frame with no seam (592HD or the row
	 * off) never calls this, and one that does is capped at {@link #MAX_PENDING}
	 * so a stream of objects cannot grow the list without bound.
	 */
	public void recordModel(GlModel model, float[] modelToScene) {
		if (model == null || modelToScene == null) {
			return;
		}
		this.record(modelToScene, new ModelDraw(model));
	}

	/** Records one caster's placement and draw for the next seam. */
	public void record(float[] modelToScene, ShadowCaster.Draw draw) {
		if (draw == null) {
			return;
		}
		ShadowCaster caster = ShadowCaster.of(modelToScene, draw);
		synchronized (this.pending) {
			if (this.pendingCount < this.pending.length) {
				this.pending[this.pendingCount++] = caster;
			}
		}
	}

	/** Moves the recorded casters into this frame's set; the count is returned. */
	private long drainPending() {
		synchronized (this.pending) {
			int count = this.pendingCount;
			for (int index = 0; index < count; index++) {
				this.casters.add(this.pending[index]);
				this.pending[index] = null;
			}
			this.pendingCount = 0;
			return count;
		}
	}

	/** Adds one caster with its model-to-scene matrix, column-major. */
	public void add(float[] modelToScene, ShadowCaster.Draw draw) {
		this.checkCollecting();
		this.casters.add(ShadowCaster.of(modelToScene, draw));
	}

	/** Adds one caster translated to a scene position, with no rotation. */
	public void addAt(float x, float y, float z, ShadowCaster.Draw draw) {
		this.checkCollecting();
		this.casters.add(ShadowCaster.at(x, y, z, draw));
	}

	/** The set as it stands; the frame driver reads it inside the pass only. */
	public ShadowCasterSet casters() {
		return this.casters;
	}

	/** True between {@link #beginFrame} and {@link #submit}. */
	public boolean collecting() {
		return this.collecting;
	}

	/**
	 * Runs the pass with the casters collected since {@link #beginFrame()}.
	 *
	 * @return true when the map now holds this frame; false when the row, 592HD
	 *         or the target refused it, in which case no caster was drawn
	 */
	public boolean submit(GlToolkit toolkit) {
		if (toolkit == null) {
			throw new IllegalArgumentException("a submission needs the toolkit drawing the frame");
		}
		this.frames++;
		this.collecting = false;
		boolean ran = toolkit.hdSunShadowFrame(this.centre[0], this.centre[1], this.centre[2],
			this.radius, this.casters);
		if (ran) {
			this.passes++;
			this.submitted = toolkit.hdShadowCastersSubmitted();
		} else {
			this.skipped++;
			this.submitted = 0L;
		}
		return ran;
	}

	private void checkCollecting() {
		if (!this.collecting) {
			throw new IllegalStateException("add casters between beginFrame and submit");
		}
	}

	/**
	 * Opens one world draw. Nested draws share the outermost frame's collection
	 * and must not close it when they finish.
	 *
	 * <p>This exists because the stock reflective water effect re-enters the
	 * scene draw to fill its reflection target. Without the count, that nested
	 * draw's end would stop the outer draw from recording the geometry it has
	 * not reached yet, and its scene seam would rebuild the sun map from inside
	 * the draw that is already using it.
	 */
	public void enterScene() {
		this.sceneDepth++;
	}

	/** Closes one world draw; an unmatched exit cannot take the count below zero. */
	public void exitScene() {
		if (this.sceneDepth > 0) {
			this.sceneDepth--;
		}
	}

	/** True while a world draw is open, so the frame's pass must not run inside it. */
	public boolean sceneOpen() {
		return this.sceneDepth > 0;
	}

	/** Half-extent of the volume this frame's map covers, in scene units. */
	public float radius() {
		return this.radius;
	}

	/** Frames submitted, pass or not. */
	public long frames() {
		return this.frames;
	}

	/** Frames whose pass completed. */
	public long passes() {
		return this.passes;
	}

	/** Frames the toolkit declined, for any reason. */
	public long skipped() {
		return this.skipped;
	}

	/** Casters the last completed pass actually drew; zero when it was skipped. */
	public long castersSubmitted() {
		return this.submitted;
	}

	/** Casters the last world draw recorded for this frame's pass. */
	public long recorded() {
		return this.recorded;
	}

	/** One line for the console, never per frame. */
	public String stats() {
		return "sun casters: frames=" + this.frames + " passes=" + this.passes
			+ " skipped=" + this.skipped + " recorded=" + this.recorded
			+ " submitted=" + this.submitted;
	}

	/** Forgets the counters and the casters; the map itself is not touched. */
	public void reset() {
		this.casters.clear();
		this.collecting = false;
		this.sceneDepth = 0;
		this.frames = 0L;
		this.passes = 0L;
		this.skipped = 0L;
		this.submitted = 0L;
		this.recorded = 0L;
		synchronized (this.pending) {
			for (int index = 0; index < this.pendingCount; index++) {
				this.pending[index] = null;
			}
			this.pendingCount = 0;
		}
	}

	/** A model's scene-space depth draw, for the recorded caster list. */
	private static final class ModelDraw implements ShadowCaster.Draw {

		private final GlModel model;

		ModelDraw(GlModel model) {
			this.model = model;
		}

		@Override
		public void draw() {
			this.model.drawShadowDepth();
		}
	}
}
