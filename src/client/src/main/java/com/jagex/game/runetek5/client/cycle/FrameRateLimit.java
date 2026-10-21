package com.jagex.game.runetek5.client.cycle;

/**
 * Frame-rate ceiling policy for the render loop.
 *
 * <p>{@link #DEFAULT_FPS} is a ceiling rather than vsync: it keeps an idle
 * machine from rendering thousands of frames per second while staying well
 * above the tick rate. {@link #MIN_FPS} means uncapped; a background window is
 * throttled to the tick rate because nobody sees those frames.
 */
public final class FrameRateLimit {

	/** Out-of-box ceiling for the renderer. */
	public static final int DEFAULT_FPS = 180;

	/** Uncapped: render as fast as the machine allows. */
	public static final int MIN_FPS = 0;

	/** Upper bound accepted from the {@code maxfps} console command. */
	public static final int MAX_FPS = 1000;

	/** Session ceiling; the {@code maxfps} console command writes this. */
	private static int configuredFps = DEFAULT_FPS;

	private FrameRateLimit() {
	}

	public static int clamp(int fps) {
		if (fps < MIN_FPS) {
			return MIN_FPS;
		}
		if (fps > MAX_FPS) {
			return MAX_FPS;
		}
		return fps;
	}

	public static int getConfiguredFps() {
		return configuredFps;
	}

	/** Applies a new session ceiling and returns the clamped value. */
	public static int setConfiguredFps(int fps) {
		configuredFps = clamp(fps);
		return configuredFps;
	}

	/**
	 * Effective ceiling for the current frame.
	 *
	 * <p>An unfocused window is capped at the tick rate (or at the configured
	 * ceiling when that is lower), so alt-tabbing parks the renderer without
	 * touching game pace. {@code tickMs} is the live tick interval from the
	 * {@code fps} console command, so a non-standard tick rate still yields a
	 * sensible idle ceiling.
	 *
	 * @return frames per second to allow; {@link #MIN_FPS} means no ceiling.
	 */
	public static int targetFps(int configuredFps, boolean focused, int tickMs) {
		int capped = clamp(configuredFps);
		int safeTickMs = tickMs < 1 ? 1 : tickMs;
		int tickFps = 1000 / safeTickMs;
		if (!focused) {
			return capped == MIN_FPS ? tickFps : Math.min(capped, tickFps);
		}
		return capped;
	}

	static void reset() {
		configuredFps = DEFAULT_FPS;
	}
}
