package com.jagex.game.runetek5.client.camera;

import com.jagex.game.runetek5.deob.Static384;

/**
 * Routes mouse-wheel input between OSHD-style camera zoom, OSHD-style fog
 * and render distance, and the legacy chat/interface scroll path.
 *
 * <p>A consumed delta must not reach the legacy scroll accumulator; an
 * unconsumed one is returned untouched.
 */
public final class CameraZoomController {

	/** Jagex keycodes: Ctrl, Shift, Alt (AWT VK_CONTROL, VK_SHIFT, VK_ALT). */
	private static final int JAGEX_KEY_CTRL = 82;

	private static final int JAGEX_KEY_SHIFT = 81;

	private static final int JAGEX_KEY_ALT = 86;

	private CameraZoomController() {
	}

	private static boolean isKeyHeld(int jagexKeycode) {
		return Static384.aClass244_1 != null && Static384.aClass244_1.isKeyHeld(jagexKeycode);
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
		if (tickFog(wheelNotches, isKeyHeld(JAGEX_KEY_ALT), isKeyHeld(JAGEX_KEY_CTRL))) {
			CameraPreferences.save();
			return 0;
		}
		if (tick(wheelNotches, isKeyHeld(JAGEX_KEY_CTRL), isKeyHeld(JAGEX_KEY_SHIFT))) {
			CameraPreferences.save();
			return 0;
		}
		return wheelNotches;
	}
}
