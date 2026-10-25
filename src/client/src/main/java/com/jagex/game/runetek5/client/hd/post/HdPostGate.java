package com.jagex.game.runetek5.client.hd.post;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;

/**
 * Whether a screen-space effect may run this frame.
 *
 * <p>Same shape as the water and shadow gates: pure functions over stored
 * state, so the precedence is stated once and tested as a truth table. The
 * first clause is always 592HD itself - with the feature set off every row is
 * inert and the frame is the stock one, whatever the stored rows say.
 */
public final class HdPostGate {

	private HdPostGate() {
	}

	/** True when the row is stored as on and 592HD is enabled. */
	public static boolean requested(HdFeature row) {
		return requested(HdSettings.isEnabled(), HdSettings.value(row) != 0);
	}

	/** The one predicate; every caller delegates here. */
	public static boolean requested(boolean hdEnabled, boolean rowOn) {
		return hdEnabled && rowOn;
	}

	/** True when at least one screen-space row is on, so the pass has work to do. */
	public static boolean anyRequested() {
		return requested(HdFeature.TONEMAP) || requested(HdFeature.COLOUR_GRADING)
			|| requested(HdFeature.DITHER) || requested(HdFeature.FXAA);
	}
}
