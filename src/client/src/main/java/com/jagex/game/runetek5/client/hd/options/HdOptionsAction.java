package com.jagex.game.runetek5.client.hd.options;

/**
 * One thing a click on the 592HD pane can mean.
 *
 * <p>Layout produces these; the screen and model consume them. Keeping the
 * click meaning as data (rather than a callback per rectangle) is what lets
 * the layout be unit-tested without a client.
 */
public final class HdOptionsAction {

	public enum Kind {
		/** The stock-page button that opens the pane. */
		OPEN,
		/** The page's Back button. */
		BACK,
		/** A tab-strip button. */
		TAB,
		/** A settings row. */
		ROW,
		/** A preset button. */
		PRESET
	}

	private final Kind kind;

	private final int index;

	private HdOptionsAction(Kind kind, int index) {
		this.kind = kind;
		this.index = index;
	}

	public static HdOptionsAction open() {
		return new HdOptionsAction(Kind.OPEN, 0);
	}

	public static HdOptionsAction back() {
		return new HdOptionsAction(Kind.BACK, 0);
	}

	public static HdOptionsAction tab(int index) {
		return new HdOptionsAction(Kind.TAB, index);
	}

	public static HdOptionsAction row(int index) {
		return new HdOptionsAction(Kind.ROW, index);
	}

	public static HdOptionsAction preset(int index) {
		return new HdOptionsAction(Kind.PRESET, index);
	}

	public Kind kind() {
		return this.kind;
	}

	public int index() {
		return this.index;
	}

	@Override
	public String toString() {
		return this.kind + (this.kind == Kind.OPEN || this.kind == Kind.BACK ? "" : "(" + this.index + ")");
	}
}
