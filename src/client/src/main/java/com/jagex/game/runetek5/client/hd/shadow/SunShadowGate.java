package com.jagex.game.runetek5.client.hd.shadow;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;

/**
 * Whether sun shadows may run this frame, and at what size and softness.
 *
 * <p>Pure functions over the settings and the map's own readiness, in the same
 * shape as {@code HdLightingGate}: the precedence is stated once and tested as
 * a truth table rather than spread across the renderer.
 *
 * <p>Order of precedence:
 *
 * <ol>
 * <li>592HD and per-pixel lighting must both be on - without the shader there
 * is nothing to project the map into, and the stock draw must stay untouched;</li>
 * <li>the Sun shadows row must be on - Off means no pass, no target and no
 * texture unit change;</li>
 * <li>the map must have been created successfully - a driver that refuses the
 * target falls back to unshadowed per-pixel lighting, never to a black frame.</li>
 * </ol>
 *
 * <p>Size comes from the row's label ({@code 1024/2048/4096}) and softness from
 * the pane's existing Lighting detail row: Low is a 3x3 PCF kernel, High 5x5.
 * Kernel radius 0 is the single-tap path the pixel tests use as the aliasing
 * baseline.
 */
public final class SunShadowGate {

	/** Row values of {@link HdFeature#SHADOW_MAP}: Off, 1024, 2048, 4096. */
	private static final int[] SIZES = { 0, 1024, 2048, 4096 };

	/** Smallest map that can still show a shadow edge; also the clamp floor. */
	public static final int MIN_SIZE = 256;

	/** Largest map the pane offers; also the clamp ceiling. */
	public static final int MAX_SIZE = 4096;

	private SunShadowGate() {
	}

	/** True when the stored settings ask for sun shadows at all. */
	public static boolean requested() {
		return requested(HdSettings.isEnabled(),
			HdSettings.value(HdFeature.PER_PIXEL_LIGHTING) != 0,
			HdSettings.value(HdFeature.SHADOW_MAP));
	}

	/** The one settings predicate; the pass and the shader both delegate here. */
	public static boolean requested(boolean hdEnabled, boolean perPixelLighting, int shadowRow) {
		return hdEnabled && perPixelLighting && resolution(shadowRow) > 0;
	}

	/** True when this frame can render the depth pass into a usable map. */
	public static boolean shouldRender(boolean hdEnabled, boolean perPixelLighting, int shadowRow, boolean mapReady) {
		return requested(hdEnabled, perPixelLighting, shadowRow) && mapReady;
	}

	/** The map edge in texels for a row value; 0 means Off. */
	public static int resolution(int shadowRow) {
		if (shadowRow < 0 || shadowRow >= SIZES.length) {
			return SIZES[0];
		}
		return SIZES[shadowRow];
	}

	/** Clamps a requested edge into the sizes the target class can allocate. */
	public static int clampResolution(int size) {
		if (size < MIN_SIZE) {
			return MIN_SIZE;
		}
		return size > MAX_SIZE ? MAX_SIZE : size;
	}

	/**
	 * PCF kernel radius in texels: 0 is one tap, 1 is 3x3, 2 is 5x5. The pane's
	 * Lighting detail row selects 1 (Low) or 2 (High).
	 */
	public static int pcfRadius(int lightingDetailRow) {
		return lightingDetailRow <= 0 ? 1 : 2;
	}
}
