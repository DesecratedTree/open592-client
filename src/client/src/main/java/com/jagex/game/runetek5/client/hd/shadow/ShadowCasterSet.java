package com.jagex.game.runetek5.client.hd.shadow;

/**
 * The casters one frame submits to the sun's depth pass, in submission order.
 *
 * <p>Reused across frames rather than rebuilt: the renderer clears it as it
 * culls this frame's objects and the pass walks it once. The backing array only
 * grows when a frame has more casters than any frame before it, so a steady
 * scene allocates nothing after the first few frames - which matters because
 * the list is touched on every frame the Sun shadows row is on, and never
 * touched at all when it is off.
 *
 * <p>Order is the renderer's order, not sorted: the depth pass is a depth test,
 * so the cheapest thing the shadow map can do is submit in the order the scene
 * already had.
 */
public final class ShadowCasterSet {

	private static final ShadowCaster[] NONE = new ShadowCaster[0];

	private ShadowCaster[] casters = NONE;

	private int size;

	/** Empties the set without releasing the storage the last frame needed. */
	public void clear() {
		for (int index = 0; index < this.size; index++) {
			this.casters[index] = null;
		}
		this.size = 0;
	}

	/** Appends one caster; null is ignored so callers need no branch. */
	public void add(ShadowCaster caster) {
		if (caster == null) {
			return;
		}
		if (this.size == this.casters.length) {
			ShadowCaster[] grown = new ShadowCaster[this.size == 0 ? 16 : this.size << 1];
			System.arraycopy(this.casters, 0, grown, 0, this.size);
			this.casters = grown;
		}
		this.casters[this.size++] = caster;
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	/** The caster at {@code index}, in the order it was added. */
	public ShadowCaster get(int index) {
		if (index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException(index + " of " + this.size);
		}
		return this.casters[index];
	}
}
