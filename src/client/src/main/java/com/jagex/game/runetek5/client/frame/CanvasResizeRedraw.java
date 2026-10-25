package com.jagex.game.runetek5.client.frame;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;

/**
 * The canvas' resize seam: a resized game canvas asks for a redraw.
 *
 * <p>On macOS a resized {@code Canvas} is not repainted by AWT: the native
 * surface is reallocated and the window server leaves whatever the peer holds,
 * which is the previous frame stretched to the new size or the frame's
 * background. The client only redraws when something sets
 * {@code TextureOpIrregularBricks.aBoolean189} - a paint, a toolkit resize, the
 * once-a-second canvas sync - and none of those follows a resize on that
 * platform, so the window can sit on a stale or empty surface indefinitely.
 *
 * <p>The listener states the same request an AWT paint would: set the redraw
 * flag the render loop consumes, and ask the peer for a paint so the resize
 * reaches the loop even when it arrives outside a frame. Both are needed; the
 * flag alone waits for the next frame, and the paint alone is discarded by the
 * loop's own redraw gate. Every platform gets the same behaviour, which costs
 * one boolean store and a repaint on a resize - an event that happens when the
 * player drags a window edge, not per frame.
 */
public final class CanvasResizeRedraw extends ComponentAdapter {

	private CanvasResizeRedraw() {
	}

	/** Listens for this canvas' resizes; one listener per canvas. */
	public static void attach(Component canvas) {
		if (canvas != null) {
			canvas.addComponentListener(new CanvasResizeRedraw());
		}
	}

	/**
	 * Marks the frame for a full redraw and asks AWT to deliver the paint that
	 * would have done it.
	 *
	 * <p>The flag is the volatile the render loop already reads for every other
	 * redraw request, so this method adds no state of its own and cannot be
	 * consumed a frame late. The repaint goes through the canvas' peer, which is
	 * also what re-syncs the native surface to the new size.
	 */
	@Override
	public void componentResized(ComponentEvent event) {
		TextureOpIrregularBricks.aBoolean189 = true;
		Component canvas = event.getComponent();
		if (canvas != null) {
			canvas.repaint();
		}
	}
}
