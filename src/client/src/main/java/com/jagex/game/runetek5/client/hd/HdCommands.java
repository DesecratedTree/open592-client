package com.jagex.game.runetek5.client.hd;

import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.client.hd.options.HdOptionsScreen;
import com.jagex.game.runetek5.deob.Static441;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

/**
 * The 592HD dev-console commands.
 *
 * <p>One entry point, called from the client's existing command handler, so
 * the deob method grows a single delegation line instead of a new block of
 * command parsing. Every command reports through the same chat helper the
 * stock commands use, and every change is persisted.
 */
public final class HdCommands {

	private static final HdGraphicsBackend BACKEND = new GlHdGraphicsBackend();

	private HdCommands() {
	}

	/**
	 * Handles one lower-case console line.
	 *
	 * @return true when the line was a 592HD command and must not fall through
	 *         to the stock handler
	 */
	public static boolean handle(String command) {
		if (command == null) {
			return false;
		}
		String trimmed = command.trim();
		if (trimmed.equalsIgnoreCase("hd")) {
			HdSettings.toggleEnabled(BACKEND);
			message(HdSettings.isEnabled()
				? "592HD enabled (" + HdSettings.preset().label() + ")"
				: "592HD disabled");
			return true;
		}
		if (trimmed.equalsIgnoreCase("hdstats")) {
			// The counters that answer "is this stage running at all": frames,
			// passes, skips and how many primitives the lighting program drew.
			Toolkit toolkit = MonoStereoData.aClass19_16;
			if (toolkit instanceof GlToolkit) {
				// Two lines: the world's stage counters, then the sky's. A stage
				// whose numbers stay at zero in a live frame is not wired, which
				// is exactly what these are for.
				message(((GlToolkit) toolkit).hdLightingStats());
				message(((GlToolkit) toolkit).hdSkyStats());
				message(((GlToolkit) toolkit).hdWaterStats());
				message(((GlToolkit) toolkit).hdPostStats());
			} else {
				message("592HD: no GL toolkit is running");
			}
			return true;
		}
		if (trimmed.equalsIgnoreCase("hdsettings") || trimmed.equalsIgnoreCase("592hd")) {
			HdOptionsScreen.toggle();
			message(HdOptionsScreen.isOpen() ? "592HD settings opened" : "592HD settings closed");
			return true;
		}
		if (trimmed.equalsIgnoreCase("hdreset")) {
			HdSettings.reset();
			message("592HD settings reset to " + HdSettings.preset().label());
			return true;
		}
		if (trimmed.regionMatches(true, 0, "hdpreset", 0, 8)) {
			String name = trimmed.substring(8).trim();
			HdPreset preset = HdPreset.forKey(name);
			if (preset == null || preset == HdPreset.CUSTOM) {
				message("Usage: ::hdpreset off|low|medium|high|ultra");
				return true;
			}
			HdSettings.applyPreset(preset, BACKEND);
			message("592HD preset: " + preset.label());
			return true;
		}
		return false;
	}

	/** The backend the console and the pane share. */
	public static HdGraphicsBackend backend() {
		return BACKEND;
	}

	private static void message(String text) {
		Static441.method5568(text);
	}
}
