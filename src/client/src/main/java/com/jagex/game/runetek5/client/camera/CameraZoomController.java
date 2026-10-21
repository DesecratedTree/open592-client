package com.jagex.game.runetek5.client.camera;

import com.jagex.game.runetek5.deob.Static384;

/**
 * Routes mouse-wheel input between OSHD-style camera zoom, OSHD-style fog
 * and render distance, and the legacy chat/interface scroll path.
 *
 * <p>When Ctrl is held the wheel delta is consumed into
 * {@link CameraZoom#adjustTarget(int)}, and when Alt is held without Ctrl it
 * is consumed into {@link FogDistance#adjustTarget(int)}; the caller must
 * <em>not</em> forward a consumed delta to the legacy scroll accumulator.
 * Otherwise the delta is returned untouched for plain-scroll handling.
 */
public final class CameraZoomController {

	/** Jagex keycode for Ctrl (AWT VK_CONTROL maps to 82). */
	public static final int JAGEX_KEY_CTRL = 82;

	/** Jagex keycode for Shift (AWT VK_SHIFT maps to 81). */
	public static final int JAGEX_KEY_SHIFT = 81;

	/** Jagex keycode for Alt (AWT VK_ALT maps to 86). */
	public static final int JAGEX_KEY_ALT = 86;

	private CameraZoomController() {
	}

	public static boolean isControlHeld() {
		try {
			return Static384.aClass244_1 != null && Static384.aClass244_1.isKeyHeld(JAGEX_KEY_CTRL);
		} catch (RuntimeException ex) {
			return false;
		}
	}

	public static boolean isShiftHeld() {
		try {
			return Static384.aClass244_1 != null && Static384.aClass244_1.isKeyHeld(JAGEX_KEY_SHIFT);
		} catch (RuntimeException ex) {
			return false;
		}
	}

	public static boolean isAltHeld() {
		try {
			return Static384.aClass244_1 != null && Static384.aClass244_1.isKeyHeld(JAGEX_KEY_ALT);
		} catch (RuntimeException ex) {
			return false;
		}
	}

	/**
	 * Pure gating decision used by the client tick and unit checks.
	 *
	 * <p>Zoom consumes the wheel only when Ctrl is held <em>without</em>
	 * Shift, so the pre-existing Ctrl+Shift+wheel debug/interface path keeps
	 * receiving its delta via the legacy scroll accumulator.
	 *
	 * @return true when the wheel was consumed as zoom and must be suppressed
	 *         from the legacy scroll path.
	 */
	public static boolean tick(int wheelNotches, boolean controlHeld, boolean shiftHeld) {
		if (wheelNotches != 0 && controlHeld && !shiftHeld) {
			CameraZoom.adjustTarget(wheelNotches);
			return true;
		}
		return false;
	}

	/**
	 * Pure gating decision for OSHD-style fog distance, used by the client
	 * tick and unit checks.
	 *
	 * <p>Fog consumes the wheel only when Alt is held <em>without</em> Ctrl,
	 * so Ctrl keeps its zoom meaning when both modifiers are held and every
	 * pre-existing Ctrl path is untouched.
	 *
	 * @return true when the wheel was consumed as fog distance and must be
	 *         suppressed from the legacy scroll path.
	 */
	public static boolean tickFog(int wheelNotches, boolean altHeld, boolean controlHeld) {
		if (wheelNotches != 0 && altHeld && !controlHeld) {
			FogDistance.adjustTarget(wheelNotches);
			return true;
		}
		return false;
	}

	/**
	 * Reads live keyboard state, applying fog and render distance when Alt is
	 * held without Ctrl and zoom when Ctrl is held without Shift, and returns the
	 * residual wheel delta for the legacy path (0 when consumed). Alt+Ctrl
	 * resolves to zoom; Ctrl+Shift and plain wheel pass through untouched.
	 */
	public static int routeWheel(int wheelNotches) {
		if (tickFog(wheelNotches, isAltHeld(), isControlHeld())) {
			CameraPreferences.save();
			return 0;
		}
		if (tick(wheelNotches, isControlHeld(), isShiftHeld())) {
			CameraPreferences.save();
			return 0;
		}
		return wheelNotches;
	}
}
