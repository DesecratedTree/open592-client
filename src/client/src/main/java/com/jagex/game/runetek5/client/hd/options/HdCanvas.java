package com.jagex.game.runetek5.client.hd.options;

/**
 * The small drawing surface the 592HD pane needs.
 *
 * <p>The pane is laid out and drawn through this interface so its rendering is
 * not welded to {@code Toolkit}: the client uses {@link ToolkitHdCanvas}, and
 * tests can draw the real pane into an image without a GL context. Only four
 * primitives are needed, which also keeps the production adapter trivial.
 */
public interface HdCanvas {

	int width();

	int height();

	/** Fills a rectangle with a 0xRRGGBB colour. */
	void fillRect(int x, int y, int width, int height, int rgb);

	/** Draws a one-pixel rectangle outline. */
	void outlineRect(int x, int y, int width, int height, int rgb);

	/**
	 * Draws one line of text.
	 *
	 * @param height box height used for vertical placement and wrapping
	 * @param align  0 left, 1 centre, 2 right
	 */
	void text(String text, int x, int y, int width, int height, int rgb, int align);
}
