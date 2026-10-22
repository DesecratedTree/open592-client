package com.jagex.game.runetek5.client.cursor;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Custom cursor image assembly.
 *
 * <p>The game ships cursor sprites as packed {@code 0xAARRGGBB} pixels
 * (see {@code SpriteType.getPixels()}). AWT cursors must be built from an
 * alpha-capable image; building from a grayscale or alpha-less image (or
 * dropping the high byte) renders every custom cursor as a flat grey
 * silhouette. This helper is the single owner of that conversion so render
 * code never duplicates the pixel math.
 */
public final class CustomCursor {

	private CustomCursor() {
	}

	/**
	 * True for the reset request ({@code width < 0} or {@code pixels == null}),
	 * which restores the default cursor instead of installing a custom one.
	 */
	public static boolean isResetRequest(int width, int[] pixels) {
		return width < 0 || pixels == null;
	}

	/**
	 * Recovers the cursor height when only the width travels with the request.
	 * The client signlink drops the height int, but {@code pixels.length ==
	 * width * height} so it can be derived. Falls back to
	 * {@code fallbackHeight} when it cannot be derived.
	 */
	public static int heightFor(int width, int[] pixels, int fallbackHeight) {
		if (pixels == null || width <= 0) {
			return fallbackHeight;
		}
		if (pixels.length % width != 0) {
			return fallbackHeight;
		}
		return pixels.length / width;
	}

	/**
	 * Packs sprite pixels into an alpha-capable image without touching the
	 * color bytes. Transparent pixels stay transparent (alpha 0); opaque and
	 * translucent pixels keep their exact ARGB value.
	 */
	public static BufferedImage toImage(int width, int[] pixels, int height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("width and height must be positive");
		}
		if (pixels == null) {
			throw new IllegalArgumentException("pixels must not be null");
		}
		if (pixels.length != width * height) {
			throw new IllegalArgumentException("pixels.length must equal width * height");
		}
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		image.setRGB(0, 0, width, height, pixels, 0, width);
		return image;
	}

	/**
	 * Clamps the hotspot inside the cursor bounds. An out-of-bounds hotspot
	 * makes {@code Toolkit.createCustomCursor} throw, which previously left
	 * the canvas without a custom cursor.
	 */
	public static Point clampHotspot(Point hotspot, int width, int height) {
		if (hotspot == null) {
			return new Point(0, 0);
		}
		int x = hotspot.x;
		int y = hotspot.y;
		if (x < 0) {
			x = 0;
		} else if (x >= width) {
			x = Math.max(0, width - 1);
		}
		if (y < 0) {
			y = 0;
		} else if (y >= height) {
			y = Math.max(0, height - 1);
		}
		return new Point(x, y);
	}
}
