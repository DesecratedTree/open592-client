package com.jagex.game.runetek5.client.hd;

import com.jagex.game.runetek5.client.camera.FogDistance;

/**
 * Maps a 592HD draw-distance choice onto the camera's fog level.
 *
 * <p>{@link FogDistance} stays the only owner of "level to distance" math; this
 * class only names the four levels the pane offers. Every percent here is a
 * legal level today, so choosing a distance can never push fog outside its
 * clamp. A future 2x option would extend {@code FogDistance}'s ceiling instead
 * of duplicating the scale here.
 */
public final class HdRenderDistance {

	/** Percent of the stock view range, indexed by the feature value. */
	public static final int[] PERCENT = { 100, 125, 140, 165 };

	/** The level a fresh Level-140 install starts on. */
	public static final int DEFAULT_INDEX = 2;

	private HdRenderDistance() {
	}

	public static int percent(int index) {
		if (index < 0 || index >= PERCENT.length) {
			return PERCENT[DEFAULT_INDEX];
		}
		return PERCENT[index];
	}

	/** The index whose percent equals {@code percent}, or the default. */
	public static int indexOfPercent(int percent) {
		for (int i = 0; i < PERCENT.length; i++) {
			if (PERCENT[i] == percent) {
				return i;
			}
		}
		return DEFAULT_INDEX;
	}

	/** Clamps a level to the same range the camera accepts. */
	public static int clampPercent(int percent) {
		return FogDistance.clamp(percent);
	}
}
