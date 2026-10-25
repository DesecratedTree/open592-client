package com.jagex.game.runetek5.client.hd;

/**
 * How far a 592HD row is backed today.
 *
 * <p>{@link #READY} rows are live: either they drive a stock 592 graphics flag
 * ({@link HdFeature#appliesToStock()}) or the 592HD renderer owns them and
 * reads the value at draw time. Turning a READY row changes the game now.
 * {@link #PENDING_RENDERER} rows are not honoured yet: the choice is stored (so
 * a preset survives) but the row is marked in the pane and nothing reads it.
 * The blocker is not always renderer code - some of these rows wait on material
 * content this client does not ship - which is why the pane says "not honoured"
 * rather than naming a reason it cannot know. {@link #UNAVAILABLE} rows have no
 * data or mechanism in this client at all and cannot be selected.
 */
public enum HdSupport {

	/** Live now: a stock flag or the 592HD renderer honours the value. */
	READY(null),

	/** Stored now; honoured when the renderer and the data it needs land. */
	PENDING_RENDERER("*"),

	/** Not supported by this client or cache; the row is inert. */
	UNAVAILABLE("n/a");

	private final String marker;

	HdSupport(String marker) {
		this.marker = marker;
	}

	/** Suffix drawn next to an unavailable or not-yet-live row, or null. */
	public String marker() {
		return this.marker;
	}

	public boolean selectable() {
		return this != UNAVAILABLE;
	}

	public boolean appliesToday() {
		return this == READY;
	}
}
