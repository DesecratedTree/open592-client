package com.jagex.game.runetek5.client.hd.options;

/** An immutable screen rectangle in canvas pixels. */
public final class HdRect {

	private final int x;

	private final int y;

	private final int width;

	private final int height;

	public HdRect(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int x() {
		return this.x;
	}

	public int y() {
		return this.y;
	}

	public int width() {
		return this.width;
	}

	public int height() {
		return this.height;
	}

	public int right() {
		return this.x + this.width;
	}

	public int bottom() {
		return this.y + this.height;
	}

	public boolean contains(int pointX, int pointY) {
		return pointX >= this.x && pointX < right() && pointY >= this.y && pointY < bottom();
	}

	@Override
	public String toString() {
		return "[" + this.x + "," + this.y + " " + this.width + "x" + this.height + "]";
	}
}
