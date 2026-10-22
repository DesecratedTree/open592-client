package com.jagex.game.runetek5.client.animation;

import com.jagex.game.runetek5.deob.OutputStream_Sub1;

/**
 * Whether animations interpolate between keyframes.
 *
 * <p>The renderers ask {@code OutputStream_Sub1.aBoolean244} on every animation
 * lookup, and stock only sets that flag from the dev console's {@code tween}
 * command, so a fresh client plays every sequence frame-by-frame until someone
 * types it. This class owns the value instead: tweening is on by default, and
 * the console toggles through here so its messages and the flag cannot drift.
 *
 * <p>Sequences that already ask to tween are unaffected — this only forces the
 * ones that would otherwise step.
 */
public final class Tweening {

	/** On by default: the smoother animation is what the renderers are built for. */
	public static final boolean DEFAULT_ENABLED = true;

	private static boolean enabled = DEFAULT_ENABLED;

	private Tweening() {
	}

	public static boolean isEnabled() {
		return enabled;
	}

	/** Sets the flag and pushes it to the renderers' shared field. */
	public static void setEnabled(boolean value) {
		enabled = value;
		OutputStream_Sub1.aBoolean244 = value;
	}

	/**
	 * Flips the flag.
	 *
	 * @return the value now in effect
	 */
	public static boolean toggle() {
		setEnabled(!enabled);
		return enabled;
	}

	/** Restores the shipped default and pushes it, for the startup path. */
	public static void reset() {
		setEnabled(DEFAULT_ENABLED);
	}

	/** Test hook: sets the flag without adding any UI path. */
	static void setForTest(boolean value) {
		setEnabled(value);
	}
}
