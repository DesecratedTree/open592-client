package com.jagex.game.runetek5.client.hd;

/**
 * The four pages of the 592HD settings pane.
 *
 * <p>The grouping mirrors 634HD's Graphics Options page: lighting and shadow
 * controls first, world appearance second, post effects third, and the display
 * controls last. The order is the tab-strip order, so a tab's ordinal is also
 * its strip position.
 */
public enum HdTab {

	LIGHTING("Lighting"),
	WORLD("World"),
	EFFECTS("Effects"),
	DISPLAY("Display");

	private final String label;

	HdTab(String label) {
		this.label = label;
	}

	public String label() {
		return label;
	}

	/** The tab shown when the pane opens. */
	public static HdTab initial() {
		return LIGHTING;
	}

	public static HdTab at(int index) {
		HdTab[] tabs = values();
		if (index < 0 || index >= tabs.length) {
			return initial();
		}
		return tabs[index];
	}
}
