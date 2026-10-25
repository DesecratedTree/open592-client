package com.jagex.game.runetek5.client.hd;

import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

/**
 * What the running client can currently honour for 592HD, and the one line the
 * pane shows above its rows.
 *
 * <p>The status line is derived from the settings, not from a cached compile
 * result: it changes the moment a row is cycled, and it can never claim a
 * pipeline that a previous toolkit compiled. The program's own state is asked
 * of the toolkit that owns it, and only to say that lighting is not usable yet.
 */
public final class HdCapabilities {

	private HdCapabilities() {
	}

	/**
	 * True when the active OpenGL toolkit has a linked lighting program. Asked
	 * of the toolkit that owns the program, so a destroyed toolkit cannot leave
	 * this reporting a shader that no longer exists.
	 */
	public static boolean lightingAvailable() {
		GlToolkit toolkit = GlHdGraphicsBackend.activeToolkit();
		return toolkit != null && toolkit.hdLightingAvailable();
	}

	/** True when the OpenGL toolkit is the one drawing frames. */
	public static boolean openGlActive() {
		return Packet.anInt2973 == 1;
	}

	/**
	 * One-line status shown above the rows. Every branch names a setting (off,
	 * no OpenGL, per-pixel lighting off) so the line cannot disagree with the
	 * rows beneath it; only the last one is about the program itself.
	 */
	public static String statusLine() {
		if (!HdSettings.isEnabled()) {
			return "592HD off - stock graphics";
		}
		if (!openGlActive()) {
			return "592HD on - needs OpenGL";
		}
		if (HdSettings.value(HdFeature.PER_PIXEL_LIGHTING) == 0) {
			return "592HD on - per-pixel off";
		}
		if (!lightingAvailable()) {
			return "592HD on - lighting pending";
		}
		return "592HD on - per-pixel lighting";
	}
}
