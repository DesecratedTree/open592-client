package com.jagex.game.runetek5.client.cycle;

import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.client.camera.CameraZoom;
import com.jagex.game.runetek5.client.camera.FogDistance;
import com.jagex.game.runetek5.client.render.RendererInfo;
import com.jagex.game.runetek5.client.render.RendererLabel;

/**
 * Frames and ticks the loop actually ran, counted over a one-second window and
 * held between windows so the overlay is steady rather than flickering.
 */
public final class FrameRateStats {

	/** Long enough to be stable, short enough to react to a cap change. */
	private static final int WINDOW_MILLIS = 1000;

	private static boolean started;

	private static long windowStartMillis;

	private static int frames;

	private static int ticks;

	private static int fps;

	private static int ticksPerSecond;

	/**
	 * Raised by lifecycle callbacks that may run off the loop thread; applied
	 * at the next {@link #record(int, long)} so the loop never sees the window
	 * reset underneath it.
	 */
	private static volatile boolean resetRequested;

	private FrameRateStats() {
	}

	/**
	 * Counts one rendered frame and the ticks that ran in it.
	 *
	 * @param tickCount ticks the loop actually executed for this frame, not the
	 *                  number the pacer considered due
	 * @param nowMillis the loop's wall clock, in milliseconds
	 */
	public static void record(int tickCount, long nowMillis) {
		if (resetRequested) {
			resetRequested = false;
			reset();
		}
		if (!started) {
			// The first frame only opens the window: counting it as well would
			// credit one frame for zero elapsed time and bias the rate up.
			started = true;
			windowStartMillis = nowMillis;
			return;
		}
		frames++;
		ticks += tickCount;
		long elapsed = nowMillis - windowStartMillis;
		if (elapsed >= WINDOW_MILLIS) {
			fps = perSecond(frames, elapsed);
			ticksPerSecond = perSecond(ticks, elapsed);
			windowStartMillis = nowMillis;
			frames = 0;
			ticks = 0;
		}
	}

	/**
	 * The debug overlay line, shared by both display sites so the readout cannot
	 * drift apart: real rendered frames per second, real game ticks per second,
	 * then the presentation levels that decide how much of the world is drawn.
	 *
	 * <p>Zoom and fog are already percentages of the stock view, so they are
	 * printed as they are: 100% is the distance the client shipped with, 140% is
	 * the default level and 165% is the maximum. They belong in this readout
	 * because both scale render distance: when something only misbehaves at
	 * range, the level that produced it sits next to the frame rate that showed
	 * it.
	 *
	 * <p>The renderer comes last: the GL toolkit and the Java rasteriser produce
	 * the same scene by very different routes, so which one is running is the
	 * first thing to check when the frame rate is not what it should be.
	 */
	public static String getOverlayText() {
		return "Fps:" + fps + " Tps:" + ticksPerSecond
			+ " Zoom:" + CameraZoom.getCurrentZoom() + "%"
			+ " Fog:" + FogDistance.getCurrentFog() + "%"
			+ " Renderer:" + RendererLabel.of(Packet.anInt2973);
	}

	/**
	 * The second overlay line: the GL version, vendor and renderer strings the
	 * driver reported, or null when the Java renderer is drawing (there is no GL
	 * context to ask), so the overlay can skip the line instead of leaving a
	 * gap.
	 */
	public static String getRendererInfoText() {
		if (!RendererLabel.OPENGL.equals(RendererLabel.of(Packet.anInt2973))) {
			return null;
		}
		String text = RendererInfo.describe();
		return text.length() == 0 ? null : text;
	}

	/** Rounds a count over an interval to a per-second rate; 0 when either is unusable. */
	static int perSecond(int count, long elapsedMillis) {
		if (count <= 0 || elapsedMillis <= 0L) {
			return 0;
		}
		return (int) (((long) count * 1000L + elapsedMillis / 2L) / elapsedMillis);
	}

	/**
	 * Asks the loop thread to start a fresh counting window on its next frame.
	 *
	 * <p>Safe from any thread: it only raises a flag, so an applet lifecycle
	 * callback cannot clear the window while the loop is reading it.
	 */
	public static void requestReset() {
		resetRequested = true;
	}

	static void reset() {
		resetRequested = false;
		started = false;
		windowStartMillis = 0L;
		frames = 0;
		ticks = 0;
		fps = 0;
		ticksPerSecond = 0;
	}
}
