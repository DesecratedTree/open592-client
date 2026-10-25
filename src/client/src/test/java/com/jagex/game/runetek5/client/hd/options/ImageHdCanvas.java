package com.jagex.game.runetek5.client.hd.options;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Renders the real pane into an image so its layout can be inspected without a
 * GL context or a cache.
 *
 * <p>This is the same {@link HdCanvas} contract the client adapter implements,
 * so the picture exercises the shipped overlay, model and layout code; only the
 * pixel backend differs. Tests use it to produce reviewable artifacts.
 */
final class ImageHdCanvas implements HdCanvas {

	private static final Font FONT = new Font(Font.DIALOG, Font.PLAIN, 11);

	private final BufferedImage image;

	private final Graphics2D graphics;

	ImageHdCanvas(int width, int height) {
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.graphics = this.image.createGraphics();
		this.graphics.setFont(FONT);
		this.graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		this.graphics.setColor(new Color(0x1B2A1B));
		this.graphics.fillRect(0, 0, width, height);
	}

	/** Paints a stand-in game frame so the pane is seen in context. */
	void drawBackdrop(int x, int y, int width, int height, String label) {
		this.graphics.setColor(new Color(0x2B3A2B));
		this.graphics.fillRect(x, y, width, height);
		this.graphics.setColor(new Color(0x4A5A4A));
		this.graphics.drawRect(x, y, width, height);
		this.graphics.setColor(new Color(0x8FA08F));
		this.graphics.drawString(label, x + 8, y + height / 2);
	}

	BufferedImage image() {
		return this.image;
	}

	/** The live graphics context, for painting a backdrop under the pane. */
	Graphics2D graphics() {
		return this.graphics;
	}

	/** An immutable copy of the current frame, for writing to disk. */
	BufferedImage snapshot() {
		BufferedImage copy = new BufferedImage(this.image.getWidth(), this.image.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D target = copy.createGraphics();
		target.drawImage(this.image, 0, 0, null);
		target.dispose();
		return copy;
	}

	@Override
	public int width() {
		return this.image.getWidth();
	}

	@Override
	public int height() {
		return this.image.getHeight();
	}

	@Override
	public void fillRect(int x, int y, int width, int height, int rgb) {
		if (width <= 0 || height <= 0) {
			return;
		}
		this.graphics.setColor(new Color(rgb));
		this.graphics.fillRect(x, y, width, height);
	}

	@Override
	public void outlineRect(int x, int y, int width, int height, int rgb) {
		if (width <= 0 || height <= 0) {
			return;
		}
		this.graphics.setColor(new Color(rgb));
		this.graphics.drawRect(x, y, width - 1, height - 1);
	}

	@Override
	public void text(String text, int x, int y, int width, int height, int rgb, int align) {
		if (text == null || text.length() == 0) {
			return;
		}
		FontMetrics metrics = this.graphics.getFontMetrics();
		int drawX = x;
		if (align == 1) {
			drawX = x + (width - metrics.stringWidth(text)) / 2;
		} else if (align == 2) {
			drawX = x + width - metrics.stringWidth(text);
		}
		this.graphics.setColor(new Color(rgb));
		this.graphics.drawString(text, drawX, y + metrics.getAscent());
	}

	void dispose() {
		this.graphics.dispose();
	}
}
