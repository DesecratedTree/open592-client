package com.jagex.game.runetek5.client.hd.sway;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;

/**
 * The two vegetation rows' single decision point, in the shape of
 * {@code HdLightGate} and {@code HdWaterGate}: pure functions over the settings
 * and the draw's kind, tested as a truth table.
 *
 * <p>The two rows are independent and never replace each other:
 *
 * <ul>
 * <li><b>Foliage sway</b> moves a plant's geometry with the frame's wind - the
 * whole crown leans and comes back, phase-shifted by where the plant stands so
 * a copse is not one rigid sheet.</li>
 * <li><b>Grass displacement</b> parts the ground plants around the viewer -
 * the characters walk through a tuft and it bends away from them.</li>
 * </ul>
 *
 * <p>They share one vertex mechanism and one uniform block, so a draw that both
 * rows claim gets both offsets summed once, and a plant that is both swaying
 * and being pushed leans and parts rather than picking a winner. Turning either
 * row off only zeroes its amplitude; turning both off zeroes both and the
 * shader skips the branch, which is why the off state is the stock transform
 * byte for byte, not a quiet approximation of it.
 */
public final class HdSwayGate {

	private HdSwayGate() {
	}

	/** True when the stored settings ask for displacement of any kind. */
	public static boolean requested() {
		return requested(HdSettings.isEnabled(), foliageRequested(), grassRequested());
	}

	/** The one settings predicate; the seam's allocation-free early-out. */
	public static boolean requested(boolean hdEnabled, boolean foliage, boolean grass) {
		return hdEnabled && (foliage || grass);
	}

	/** True when the foliage-sway row is on. */
	public static boolean foliageRequested() {
		return HdSettings.isEnabled() && HdSettings.value(HdFeature.FOLIAGE_SWAY) != 0;
	}

	/** True when the grass-displacement row is on. */
	public static boolean grassRequested() {
		return HdSettings.isEnabled() && HdSettings.value(HdFeature.GRASS_DISPLACEMENT) != 0;
	}

	/**
	 * True when this exact draw's vertices move.
	 *
	 * <p>Both facts are required and neither is inferred from the other: the
	 * scene must have marked the model as vegetation, and at least one row must
	 * carry a non-zero amplitude this frame. A plant with both rows off, or a wall
	 * with them on, is untouched.
	 *
	 * <p>The amplitudes travel as the frame's block rather than as two flags, so
	 * a caller cannot hand the foliage amplitude in the grass slot: the block
	 * already names them.
	 */
	public static boolean displaces(int vegetation, HdSway sway) {
		return HdVegetation.isVegetation(vegetation)
			&& (sway.foliage() > 0.0F || sway.grass() > 0.0F);
	}
}
