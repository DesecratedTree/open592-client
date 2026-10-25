package com.jagex.game.runetek5.client.hd.options;

/**
 * The screen rectangle of the open window 592HD draws itself into.
 *
 * <p>Recorded from the live open-interface entry rather than hard-coded: the
 * stock Graphics Options window can be moved or resized by the interface
 * definition, and the pane has to follow it. The component key is kept so the
 * pane can tell one window from another.
 */
public final class HdInterfaceTarget {

	private final int interfaceId;

	private final int containerKey;

	private final int x;

	private final int y;

	private final int width;

	private final int height;

	public HdInterfaceTarget(int interfaceId, int containerKey, int x, int y, int width, int height) {
		this.interfaceId = interfaceId;
		this.containerKey = containerKey;
		this.x = x;
		this.y = y;
		this.width = width <= 0 ? 512 : width;
		this.height = height <= 0 ? 338 : height;
	}

	public int interfaceId() {
		return this.interfaceId;
	}

	public int containerKey() {
		return this.containerKey;
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

	public boolean contains(int pointX, int pointY) {
		return pointX >= this.x && pointX < this.x + this.width
			&& pointY >= this.y && pointY < this.y + this.height;
	}

	@Override
	public String toString() {
		return "interface " + this.interfaceId + " at " + this.x + "," + this.y
			+ " " + this.width + "x" + this.height;
	}
}
