package com.jagex.game.runetek5.client.hd.options;

import com.jagex.game.runetek5.client.hd.HdGraphicsBackend;
import com.jagex.game.runetek5.client.hd.GlHdGraphicsBackend;
import com.jagex.game.runetek5.graphics.Toolkit;

/**
 * The 592HD page: open state, input routing and the draw entry point.
 *
 * <p>The client calls {@link #draw(HdCanvas)} once a frame and
 * {@link #consumeClick(int, int, int)} from the click drain. Everything else
 * lives in the model, layout and overlay, so this class stays a state machine:
 * closed shows the stock-page button, open shows the page, and a click inside
 * the page never reaches the stock interface underneath.
 *
 * <p>A click lands on the pane only when the stock Graphics Options window is
 * open, or when the pane itself is open. Closing the pane and clicking
 * anywhere else are pass-through, so the stock interface keeps working
 * exactly as before.
 */
public final class HdOptionsScreen {

	private static final int DEFAULT_CANVAS_WIDTH = 765;

	private static final int DEFAULT_CANVAS_HEIGHT = 503;

	private static final HdOptionsModel MODEL = new HdOptionsModel();

	private static boolean open;

	private static int canvasWidth = DEFAULT_CANVAS_WIDTH;

	private static int canvasHeight = DEFAULT_CANVAS_HEIGHT;

	private static HdInterfaceTarget targetOverride;

	private static HdGraphicsBackend backend = new GlHdGraphicsBackend();

	/** Cached adapter so the frame path allocates nothing. */
	private static Toolkit canvasToolkit;

	private static HdCanvas toolkitCanvas;

	private HdOptionsScreen() {
	}

	public static boolean isOpen() {
		return open;
	}

	public static HdOptionsModel model() {
		return MODEL;
	}

	public static void toggle() {
		if (open) {
			close();
		} else {
			open();
		}
	}

	public static void open() {
		open = true;
		MODEL.reset();
	}

	public static void close() {
		open = false;
		MODEL.reset();
	}

	/** The window the pane attaches to, or null when none is open. */
	public static HdInterfaceTarget target() {
		return targetOverride != null ? targetOverride : HdInterfaceProbe.find();
	}

	/**
	 * Handles one queued click.
	 *
	 * @param type the stock click type; only a left press (0) is considered
	 * @return true when the pane consumed the click
	 */
	public static boolean consumeClick(int x, int y, int type) {
		if (type != 0) {
			return false;
		}
		HdOptionsLayout layout = layout();
		if (open) {
			if (!layout.page().contains(x, y)) {
				close();
				return true;
			}
			HdOptionsAction action = layout.hitTest(x, y);
			if (action == null) {
				// Swallow clicks on the page background so the stock
				// interface underneath never reacts to them.
				return true;
			}
			if (action.kind() == HdOptionsAction.Kind.BACK) {
				close();
				return true;
			}
			MODEL.apply(action, backend);
			return true;
		}
		HdOptionsAction action = layout.hitTest(x, y);
		if (action != null && action.kind() == HdOptionsAction.Kind.OPEN) {
			open();
			return true;
		}
		return false;
	}

	/**
	 * Draws the button or the page for this frame.
	 *
	 * <p>Called once per frame after the interface pass, so the queued
	 * rectangles flush with the rest of the 2D layer and land above the HUD.
	 */
	public static void draw(Toolkit toolkit) {
		if (toolkit == null) {
			return;
		}
		if (toolkitCanvas == null || canvasToolkit != toolkit) {
			// Toolkit instances survive resizes; rebuild only when it changes.
			canvasToolkit = toolkit;
			toolkitCanvas = new ToolkitHdCanvas(toolkit);
		}
		draw(toolkitCanvas);
	}

	/** Draws the button or the page for this frame. */
	public static void draw(HdCanvas canvas) {
		if (canvas == null) {
			return;
		}
		canvasWidth = canvas.width();
		canvasHeight = canvas.height();
		HdInterfaceTarget target = target();
		HdOptionsLayout layout = new HdOptionsLayout(canvasWidth, canvasHeight, target,
			MODEL.tab(), MODEL.rowCount());
		if (open) {
			HdOptionsOverlay.drawPage(canvas, layout, MODEL);
		} else if (target != null) {
			HdOptionsOverlay.drawOpenButton(canvas, layout);
		}
	}

	public static HdGraphicsBackend backend() {
		return backend;
	}

	private static HdOptionsLayout layout() {
		return new HdOptionsLayout(canvasWidth, canvasHeight, target(), MODEL.tab(), MODEL.rowCount());
	}

	// -- Test hooks -------------------------------------------------------

	static void setBackendForTest(HdGraphicsBackend value) {
		backend = value == null ? new GlHdGraphicsBackend() : value;
	}

	static void setTargetForTest(HdInterfaceTarget value) {
		targetOverride = value;
	}

	static void setCanvasForTest(int width, int height) {
		canvasWidth = width;
		canvasHeight = height;
	}

	static void resetForTest() {
		open = false;
		canvasWidth = DEFAULT_CANVAS_WIDTH;
		canvasHeight = DEFAULT_CANVAS_HEIGHT;
		targetOverride = null;
		backend = new GlHdGraphicsBackend();
		canvasToolkit = null;
		toolkitCanvas = null;
		MODEL.reset();
	}
}
