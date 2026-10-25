package com.jagex.game.runetek5.client.hd.light;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;

/**
 * The one owner of "does this row contribute a term to the per-pixel shader?".
 *
 * <p>Both rows share this gate because they share everything else: one program,
 * one frame, one uniform upload. A pure function over the settings and the
 * frame's own contents, so the precedence is stated once and tested as a truth
 * table rather than spread across the renderer. In order:
 *
 * <ol>
 * <li>the program must be in use at all - these rows add terms to the
 * per-pixel lighting program and cannot draw on their own, so 592HD off or
 * Per-pixel lighting off means neither row exists;</li>
 * <li>the row itself must be on;</li>
 * <li>the frame must carry a usable source. A carried row with no light on the
 * carrier's tile, and a room row with no lit region in the window, contribute
 * nothing: there is no fallback colour and no borrowed light.</li>
 * </ol>
 *
 * <p>Off is the identity. When a row is gated out its uniform is zero - GL's
 * initial value - and the shader's branch is not taken, so the fragment
 * arithmetic is exactly what it was before the rows existed. That is what makes
 * the rows' Off state byte-identical rather than merely similar.
 *
 * <p>The gate is asked once per draw, by {@code HdDrawRequest.current}, and the
 * answer travels with the request. The pipeline therefore never reads
 * {@link HdSettings}: a draw carries the rows it was granted, so one draw's
 * settings cannot leak into the next, and a test can drive either row without
 * touching process-wide state.
 */
public final class HdLightGate {

	private HdLightGate() {
	}

	/** True when the carried row is on and per-pixel lighting can use it. */
	public static boolean carriedRequested() {
		return carriedRequested(HdSettings.isEnabled(),
			HdSettings.value(HdFeature.CARRIED_LIGHT) != 0,
			HdSettings.value(HdFeature.PER_PIXEL_LIGHTING) != 0);
	}

	/** The settings predicate; the source check is separate so it can be tested alone. */
	public static boolean carriedRequested(boolean hdEnabled, boolean carriedRow, boolean perPixelLighting) {
		return hdEnabled && perPixelLighting && carriedRow;
	}

	/** True when the room row is on and per-pixel lighting can use it. */
	public static boolean roomRequested() {
		return roomRequested(HdSettings.isEnabled(),
			HdSettings.value(HdFeature.ROOM_LIGHT) != 0,
			HdSettings.value(HdFeature.PER_PIXEL_LIGHTING) != 0);
	}

	/** The settings predicate for the room row. */
	public static boolean roomRequested(boolean hdEnabled, boolean roomRow, boolean perPixelLighting) {
		return hdEnabled && perPixelLighting && roomRow;
	}

	/**
	 * True when the carried row has something to upload. The source is part of
	 * the question: a row that is on over an unlit tile uploads nothing, so its
	 * pixels stay the stock ones instead of gaining a made-up light.
	 */
	public static boolean carriedHasSource(HdLightSource source) {
		return source != null && source.intensity() > 0.0F && source.radius() >= 1.0F;
	}

	/** True when the room row has at least one region that would contribute. */
	public static boolean roomHasSource(HdRoomLight[] regions) {
		if (regions == null) {
			return false;
		}
		for (int i = 0; i < regions.length; i++) {
			if (regions[i] != null && !regions[i].isDark()) {
				return true;
			}
		}
		return false;
	}

	/** True when either row is asking for a term this frame. */
	public static boolean lightRequested() {
		return carriedRequested() || roomRequested();
	}
}
