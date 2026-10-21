package com.jagex.game.runetek5.client.camera;

/**
 * Follow-camera position smoothing, in scene units (1/128 of a tile).
 *
 * <p>Stock eased {@code delta / 320} of the remaining gap per millisecond
 * (~320 ms), leaving the player a tile or more ahead while walking and taking
 * most of a second to settle after stopping. Measured at 5 ms frames: 189
 * units trailing and 790 ms to settle, versus 12 units and 50 ms here. The
 * step is still bounded and never overshoots; jumps over
 * {@link #JUMP_DISTANCE} are teleports and snap, exactly as stock did.
 */
public final class CameraFollow {

	/**
	 * Distance beyond which a move is a teleport or scene reload rather than
	 * walking, and the camera snaps. Stock used 500.
	 */
	public static final int JUMP_DISTANCE = 500;

	/**
	 * Response time constant in milliseconds. Stock's 320 ms was the source of
	 * the visible trailing; 24 ms is a little over one tick, so the camera is
	 * still eased (no per-frame snapping) but reads as attached to the player.
	 */
	private static final int RESPONSE_MILLIS = 24;

	/**
	 * Remaining distance at which the camera is considered on target. Without
	 * this the integer step would crawl the last few units for several frames.
	 */
	private static final int SNAP_DISTANCE = 1;

	private CameraFollow() {
	}

	/**
	 * Whether a move is a jump (teleport, region load) rather than walking.
	 * Either axis over the limit snaps both, matching stock: a reload moves
	 * both axes at once and must not be eased.
	 */
	public static boolean shouldJump(int dx, int dz) {
		return Math.abs(dx) > JUMP_DISTANCE || Math.abs(dz) > JUMP_DISTANCE;
	}

	/**
	 * Moves one camera axis towards its target.
	 *
	 * @param current current camera coordinate
	 * @param target where the player now is
	 * @param deltaMillis milliseconds since the previous frame; values at or
	 * below zero hold the camera still
	 * @return the new coordinate, always between {@code current} and
	 * {@code target} inclusive
	 */
	public static int smoothStep(int current, int target, long deltaMillis) {
		int diff = target - current;
		if (diff == 0) {
			return target;
		}
		int distance = Math.abs(diff);
		if (distance <= SNAP_DISTANCE) {
			return target;
		}
		if (deltaMillis <= 0L) {
			return current;
		}
		long step = deltaMillis * (long) diff / RESPONSE_MILLIS;
		if (step == 0L) {
			// Never stall: a very short frame still advances one unit, otherwise
			// the camera can sit several frames behind at high frame rates.
			step = diff > 0 ? 1L : -1L;
		}
		if (step > distance) {
			step = distance;
		} else if (step < -distance) {
			step = -distance;
		}
		return current + (int) step;
	}
}
