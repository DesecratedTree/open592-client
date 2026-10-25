package com.jagex.game.runetek5.client.hd;

/**
 * The seam between 592HD's settings and the client's stock graphics state.
 *
 * <p>Only READY features reach this interface, and only through their
 * {@link HdStockTarget}. Keeping the renderer calls behind one interface means
 * the pane and the presets never import toolkit classes, and tests can drive
 * them with a fake that records writes instead of touching GL.
 */
public interface HdGraphicsBackend {

	/** True when the stock graphics state exists and can be written. */
	boolean canApply();

	/**
	 * Applies one feature's value to the stock state. Features without a
	 * stock target are ignored.
	 */
	void apply(HdFeature feature, int value);

	/** Persists whatever stock state was changed. */
	void flush();
}
