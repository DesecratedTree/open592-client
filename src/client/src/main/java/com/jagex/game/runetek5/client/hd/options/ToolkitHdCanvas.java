package com.jagex.game.runetek5.client.hd.options;

import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;

/**
 * Draws the 592HD pane with the game's own toolkit and small font, so the
 * overlay matches the surrounding interface instead of looking pasted on.
 *
 * <p>Coordinates are canvas pixels: the frame hook runs after the interface
 * pass and before the client's hook queue drains, which is the top of the 2D
 * layer.
 */
public final class ToolkitHdCanvas implements HdCanvas {

	private final Toolkit toolkit;

	public ToolkitHdCanvas(Toolkit toolkit) {
		this.toolkit = toolkit;
	}

	@Override
	public int width() {
		return Class164.aCanvas5 == null ? 765 : Class164.aCanvas5.getWidth();
	}

	@Override
	public int height() {
		return Class164.aCanvas5 == null ? 503 : Class164.aCanvas5.getHeight();
	}

	@Override
	public void fillRect(int x, int y, int width, int height, int rgb) {
		if (this.toolkit == null || width <= 0 || height <= 0) {
			return;
		}
		this.toolkit.O(x, y, width, height, rgb & 0xFFFFFF, 0);
	}

	@Override
	public void outlineRect(int x, int y, int width, int height, int rgb) {
		if (this.toolkit == null || width <= 0 || height <= 0) {
			return;
		}
		this.toolkit.outlineRect(x, y, width, height, rgb & 0xFFFFFF, 0);
	}

	@Override
	public void text(String text, int x, int y, int width, int height, int rgb, int align) {
		AbstractFontRenderer font = Class6.aClass46_9;
		if (font == null || text == null || text.length() == 0) {
			return;
		}
		int safeHeight = height <= 0 ? 16 : height;
		// The renderer takes ARGB, not RGB: the alpha byte is the text's own
		// opacity, and the stock interface passes 255 there. Without it every
		// string is fully transparent and the pane draws only its rectangles.
		int argb = textColour(rgb);
		font.method5048(x, safeHeight, argb, null, null, -1, 0, 0, 0, 0,
			text, y, 0, null, align, width);
	}

	/**
	 * The renderer's colour argument: ARGB with a fully opaque alpha. The pane
	 * speaks 0xRRGGBB, and {@code method5048} treats a zero alpha byte as a fully
	 * transparent string - which is how this adapter once drew nothing at all.
	 */
	static int textColour(int rgb) {
		return 0xFF000000 | (rgb & 0xFFFFFF);
	}
}
