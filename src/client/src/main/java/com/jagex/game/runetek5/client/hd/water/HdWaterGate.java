package com.jagex.game.runetek5.client.hd.water;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;

/**
 * Whether 592HD's water may own this draw.
 *
 * <p>Pure functions over the settings and the program's availability, in the
 * shape of {@code HdLightingGate}: the precedence is stated once and tested as
 * a truth table. In order:
 *
 * <ol>
 * <li>592HD must be enabled and the "592HD water" row on - Off means the stock
 * water effect is selected exactly as before, with no GL call from this
 * package;</li>
 * <li>the program must have linked - a driver that refuses the shader falls
 * back to the stock water effect, never to an untextured surface;</li>
 * <li>a water draw must actually be in progress - the seam is reached from the
 * stock selector, but a stray call outside it must not bind.</li>
 * </ol>
 *
 * <p>Unlike the lighting gate there is no fog requirement: the shader applies
 * the fog band itself, and stock water draws with fog off in the same frame.
 *
 * <p>The reflective half is a second, independent decision, because it needs
 * something the calm program does not: a material of the reflective water type
 * and an environment cube to sample. Its precedence is: the row must still own
 * the draw, the material must ask for the reflective type, the environment cube
 * must exist, and both programs must have linked. Any missing half is calm
 * water - never a reflection of nothing.
 *
 * <p>The breaking half is a third decision with the same shape: the breaking
 * water type, an environment cube (the stock breaking program samples the same
 * cube the reflective one does), and the breaking program linked. It sits
 * beside the reflective decision rather than under the style, because like a
 * reflection the breaking look belongs to the water type and not to the
 * surface's shading.
 *
 * <p>The row's third value selects the custom surface ({@link #STYLE_CUSTOM}).
 * It is a surface style, not a second reflection: a reflective material keeps
 * the reflective program whatever the style, because the reflection belongs to
 * the water type and not to the surface's shading, and a breaking material
 * keeps (or loses to calm) the breaking program for the same reason. The custom
 * program is the answer for calm water only, and when it fails to link the
 * standard surface takes the draw back - a driver that rejects the new program
 * costs the style, never the water.
 */
public final class HdWaterGate {

	/** The row's Off value: the stock water effect, untouched. */
	public static final int STYLE_OFF = 0;

	/** The row's On value: the surface that shipped with the first water pass. */
	public static final int STYLE_STANDARD = 1;

	/** The row's Custom value: the third surface program. */
	public static final int STYLE_CUSTOM = 2;

	/** Which program a water draw runs, resolved once at the seam. */
	public enum Surface {

		/** No HD water: the stock effect keeps the draw. */
		NONE,

		/** The surface that shipped with the first water pass. */
		STILL,

		/** The reflective water type, sampling the environment cube. */
		REFLECTIVE,

		/** The custom surface, selected by the row's third value. */
		CUSTOM,

		/** The breaking water type, driven by that material's own parameters. */
		BREAKING
	}

	/**
	 * The stock effect slot {@code ReflectiveRipplingWaterEffect} occupies. A
	 * water material whose {@code MaterialType.aByte89} is this value is the
	 * reflective water type; the stock manager substitutes slot 4 because that
	 * effect reports itself unsupported, which is exactly the fallback this row
	 * replaces.
	 */
	public static final int REFLECTIVE_SLOT = 8;

	/**
	 * The stock effect slot {@code BreakingWaterEffect} occupies. Its own
	 * {@code setEffectParams} calls are the source of
	 * {@link HdWaterBreaking}'s numbers, and its program samples the same
	 * environment cube as the reflective type.
	 */
	public static final int BREAKING_SLOT = 9;

	private HdWaterGate() {
	}

	/**
	 * True when the stored settings ask for HD water at all.
	 *
	 * <p>The gate is the pane's "592HD water" row, not its "Water" row: the
	 * latter says whether the client draws water at all (Off/Game) and belongs
	 * to the stock effect, while this row is the upgrade. Off here is the stock
	 * draw, which is exactly what the row promises.
	 */
	public static boolean requested() {
		return requested(HdSettings.isEnabled(), HdSettings.value(HdFeature.WATER_HD) != 0);
	}

	/** The one settings predicate; every caller delegates here. */
	public static boolean requested(boolean hdEnabled, boolean waterRow) {
		return hdEnabled && waterRow;
	}

	/** True when the stored row selects the custom surface. */
	public static boolean custom() {
		return custom(HdSettings.isEnabled(), HdSettings.value(HdFeature.WATER_HD));
	}

	/**
	 * The row's third value, and only that value: On is the standard surface,
	 * and Off never compiles anything. 592HD itself must still be enabled.
	 */
	public static boolean custom(boolean hdEnabled, int rowValue) {
		return hdEnabled && rowValue == STYLE_CUSTOM;
	}

	/**
	 * The whole program decision for one draw, in precedence order: the row must
	 * own the draw and the base program must have linked; a reflective material
	 * with a cube outranks the style, because that is the water type's own
	 * reflection; then a breaking material, whose look is its type's too; then
	 * the custom style, if it linked; then the standard surface.
	 *
	 * <p>Pure, so the ladder is a testable truth table rather than an if-chain
	 * buried in the pipeline. {@link Surface#NONE} means the stock effect draws.
	 */
	public static Surface surface(HdWaterRequest request, boolean stillAvailable, boolean customAvailable,
			boolean reflectiveAvailable, boolean breakingAvailable) {
		if (!request.requested() || !stillAvailable) {
			return Surface.NONE;
		}
		if (shouldReflect(request, stillAvailable, reflectiveAvailable)) {
			return Surface.REFLECTIVE;
		}
		if (request.breaking()) {
			// The type decides, not the style: a breaking tile is breaking water
			// whatever the row's third value asks for, and a program that did not
			// link costs the breaking look, never the water.
			return shouldBreak(request, stillAvailable, breakingAvailable) ? Surface.BREAKING : Surface.STILL;
		}
		return request.custom() && customAvailable ? Surface.CUSTOM : Surface.STILL;
	}

	/** True when a material's stock effect slot names the reflective water type. */
	public static boolean isReflectiveMaterial(int effectSlot) {
		return effectSlot == REFLECTIVE_SLOT;
	}

	/** True when a material's stock effect slot names the breaking water type. */
	public static boolean isBreakingMaterial(int effectSlot) {
		return effectSlot == BREAKING_SLOT;
	}

	/**
	 * True when that effect slot's stock program samples the environment cube.
	 * Both water types that are not the still surface bind it on unit 1, so the
	 * toolkit binds the cube for either and the gate decides which program.
	 */
	public static boolean samplesEnvironment(int effectSlot) {
		return isReflectiveMaterial(effectSlot) || isBreakingMaterial(effectSlot);
	}

	/**
	 * True when this draw should sample the environment cube instead of the fog
	 * band's colour. Pure, so the truth table is the specification: every one of
	 * the four facts is required, and the calm program is the answer otherwise.
	 */
	public static boolean shouldReflect(HdWaterRequest request, boolean stillAvailable, boolean reflectiveAvailable) {
		return request.requested() && request.reflective() && request.environment()
			&& stillAvailable && reflectiveAvailable;
	}

	/**
	 * True when the breaking type should draw with the material's own numbers:
	 * the row owns the draw, the material is the breaking type, its parameters
	 * are present, the cube it reflects exists and the base program linked.
	 */
	public static boolean shouldBreak(HdWaterRequest request, boolean stillAvailable, boolean breakingAvailable) {
		return request.requested() && request.breaking() && !request.breakingParams().absent()
			&& request.environment() && stillAvailable && breakingAvailable;
	}
}
