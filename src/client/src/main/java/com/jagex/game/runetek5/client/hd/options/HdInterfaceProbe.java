package com.jagex.game.runetek5.client.hd.options;

import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.config.rsinterface.Component;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;

/**
 * Finds the open window the 592HD pane should attach to.
 *
 * <p>The stock Graphics Options interface is 742 (the same id the 634HD port
 * injects into, and one the client already preloads during startup). This port
 * cannot ship a cache editor, so the window is located from the live
 * open-interface list instead of assumed: interface 742 wins outright, and a
 * window whose name mentions graphics or options is the fallback for a cache
 * that numbers it differently. Hard-coding component ids would leave a blank
 * window on any other cache; this cannot.
 */
public final class HdInterfaceProbe {

	/** The stock Graphics Options interface, as in 634HD. */
	public static final int GRAPHICS_OPTIONS_INTERFACE = 742;

	private static final String[] NAME_HINTS = { "graphic", "option", "display", "settings" };

	private HdInterfaceProbe() {
	}

	/** The window to draw into, or null when none is open. */
	public static HdInterfaceTarget find() {
		if (MusicPatch.aClass102_29 == null) {
			return null;
		}
		HdInterfaceTarget named = null;
		for (Country node = MusicPatch.aClass102_29.method2705(); node != null;
				node = MusicPatch.aClass102_29.method2704()) {
			Class4_Sub43 entry = (Class4_Sub43) node;
			if (entry.anInt6979 <= 0) {
				continue;
			}
			Component window = ParticleBinaryTree.method5121((int) entry.aLong224);
			if (window == null || window.anInt6840 != 0) {
				continue;
			}
			HdInterfaceTarget target = new HdInterfaceTarget(entry.anInt6979, (int) entry.aLong224,
				window.anInt6836, window.anInt6859, window.anInt6833, window.anInt6805);
			if (entry.anInt6979 == GRAPHICS_OPTIONS_INTERFACE) {
				return target;
			}
			if (named == null && mentionsOptions(window)) {
				named = target;
			}
		}
		return named;
	}

	/**
	 * True when the window or one of its direct children carries a name that
	 * suggests the graphics options page. Component names come from the cache,
	 * so this is a hint rather than a contract.
	 */
	static boolean mentionsOptions(Component window) {
		if (matches(window.name)) {
			return true;
		}
		if (window.slotChildren != null) {
			for (Component child : window.slotChildren) {
				if (child != null && matches(child.name)) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean matches(String name) {
		if (name == null) {
			return false;
		}
		String lower = name.toLowerCase();
		for (String hint : NAME_HINTS) {
			if (lower.contains(hint)) {
				return true;
			}
		}
		return false;
	}
}
