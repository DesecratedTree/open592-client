package com.jagex.game.runetek5.client.hd.shadow;

/**
 * One frame's sun shadow work: decide, prepare, run the depth pass with the
 * caller's casters, close it, and count what happened.
 *
 * <p>This is the single call a scene renderer makes per frame, before it draws
 * the world. {@link SunShadowMap} owns the target and the GL state; this class
 * owns the order and the gating, and nothing else. The caller passes the sun
 * direction and the volume the map should cover, and a {@link Casters}
 * callback that submits the scene's geometry: the same model and ground draw
 * calls the frame uses afterwards, which is what makes the map a picture of
 * this frame rather than a baked tile.
 *
 * <p>Every exit path is counted, because "the shadow never appears" is
 * indistinguishable from "the row is off" without a number: {@link #frames},
 * {@link #passes}, {@link #skipped} and {@link #lastSkip()}. When the row or
 * 592HD is off, no surface is prepared, no pass is opened and the casters are
 * never drawn - the stock frame before this stage existed.
 *
 * <p>Pure Java over {@link Surface}, like {@code HdLightingPipeline} over
 * {@code HdShaderApi}, so the order and the skip rules are tested without a GL
 * context.
 */
public final class SunShadowFrame {

	/** The scene's geometry submission, run inside the depth pass. */
	public interface Casters {

		/** Draws every shadow caster with the toolkit's ordinary draw calls. */
		void drawCasters();
	}

	/** The target surface the pass renders into; {@link SunShadowMap} implements it. */
	public interface Surface {

		/** Creates or resizes the target; false when the driver refuses it. */
		boolean prepare(int size);

		/**
		 * Starts the depth pass.
		 *
		 * @param sunX sun direction, pointing from the scene toward the sun
		 * @param radius half-extent of the volume around the centre
		 */
		boolean beginPass(float sunX, float sunY, float sunZ, float centreX, float centreY,
				float centreZ, float radius);

		void endPass();

		/** Increments once per completed pass. */
		int frameId();
	}

	/** Why the last frame ran no pass. */
	public enum Skip {

		/** The pass ran. */
		NONE,

		/** 592HD, per-pixel lighting or the Sun shadows row is off. */
		SETTINGS,

		/** The driver refused the target (no framebuffer objects, bad status). */
		TARGET,

		/** A pass was already open, or the map refused to start one. */
		PASS;

		public String label() {
			switch (this) {
			case SETTINGS:
				return "settings off";
			case TARGET:
				return "target unavailable";
			case PASS:
				return "pass refused";
			default:
				return "ok";
			}
		}
	}

	private long frames;

	private long passes;

	private long skipped;

	private Skip lastSkip = Skip.NONE;

	/**
	 * Runs this frame's sun shadow pass, or explains why it did not.
	 *
	 * @param surface          the map to fill, or null before one exists
	 * @param casters          the scene's geometry submission
	 * @param hdEnabled        592HD on
	 * @param perPixelLighting the per-pixel lighting row on
	 * @param shadowRow        the Sun shadows row value (0 = Off)
	 * @param radius           half-extent of the volume around the centre
	 * @return true when a pass ran and the map now holds this frame
	 */
	public boolean render(Surface surface, Casters casters, boolean hdEnabled, boolean perPixelLighting,
			int shadowRow, float sunX, float sunY, float sunZ, float centreX, float centreY, float centreZ,
			float radius) {
		this.frames++;
		if (!SunShadowGate.requested(hdEnabled, perPixelLighting, shadowRow)) {
			return skip(Skip.SETTINGS);
		}
		if (surface == null || casters == null) {
			return skip(Skip.TARGET);
		}
		int size = SunShadowGate.resolution(shadowRow);
		if (!surface.prepare(size)) {
			return skip(Skip.TARGET);
		}
		if (!surface.beginPass(sunX, sunY, sunZ, centreX, centreY, centreZ, radius)) {
			return skip(Skip.PASS);
		}
		casters.drawCasters();
		surface.endPass();
		this.passes++;
		this.lastSkip = Skip.NONE;
		return true;
	}

	private boolean skip(Skip reason) {
		this.skipped++;
		this.lastSkip = reason;
		return false;
	}

	/** Frames this driver was asked to render, pass or not. */
	public long frames() {
		return this.frames;
	}

	/** Passes that completed, which is one per shadowed frame. */
	public long passes() {
		return this.passes;
	}

	/** Frames that ran no pass, for any reason. */
	public long skipped() {
		return this.skipped;
	}

	/** Why the most recent frame did or did not run a pass. */
	public Skip lastSkip() {
		return this.lastSkip;
	}

	/** One line for the client log or the console, never per frame. */
	public String stats() {
		return "sun shadows: frames=" + this.frames + " passes=" + this.passes
			+ " skipped=" + this.skipped + " (" + this.lastSkip.label() + ")";
	}

	/** Forgets the counters; the map is the surface's business, not this class's. */
	public void reset() {
		this.frames = 0L;
		this.passes = 0L;
		this.skipped = 0L;
		this.lastSkip = Skip.NONE;
	}
}
