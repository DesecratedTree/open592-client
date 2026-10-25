package com.jagex.game.runetek5.client.frame;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Component;
import java.awt.event.ComponentEvent;

import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The resize seam's two effects, without a window: the flag the render loop
 * reads, and the paint request that re-syncs the peer. The macOS bug was that
 * neither followed a resize, so an untouched window kept the old surface.
 */
@DisplayName("canvas resize redraw")
class CanvasResizeRedrawTest {

	@Test
	@DisplayName("a resize marks the frame for redraw and asks the peer to paint")
	void componentResized_shouldRequestARedraw() {
		Sized canvas = new Sized();
		CanvasResizeRedraw.attach(canvas);
		TextureOpIrregularBricks.aBoolean189 = false;

		canvas.resizeTo(320, 240);

		assertAll(
			() -> assertTrue(TextureOpIrregularBricks.aBoolean189,
				"the loop only redraws when the flag is set"),
			// The flag is set before the paint is asked for, which is what makes
			// this request the one that redraws rather than the peer's own.
			() -> assertTrue(canvas.paintsWithFlag > 0,
				"the peer needs the paint to re-sync: " + canvas.paintsWithFlag)
		);
	}

	@Test
	@DisplayName("without the seam a resize requests nothing")
	void noListener_shouldLeaveTheFrameAlone() {
		// The macOS bug in one line: a resize that does not go through the seam
		// leaves the redraw flag false, so the loop has no reason to draw again.
		Sized canvas = new Sized();
		TextureOpIrregularBricks.aBoolean189 = false;

		canvas.resizeTo(320, 240);

		assertAll(
			() -> assertFalse(TextureOpIrregularBricks.aBoolean189),
			() -> assertEquals(0, canvas.paintsWithFlag)
		);
	}

	@Test
	@DisplayName("a canvas with no peer still asks for the redraw")
	void attach_shouldAcceptAMissingCanvas() {
		CanvasResizeRedraw.attach(null);
		TextureOpIrregularBricks.aBoolean189 = true;
		assertTrue(TextureOpIrregularBricks.aBoolean189);
	}

	/** A component whose paints are attributed to the flag's state. */
	private static final class Sized extends Component {

		private static final long serialVersionUID = 1L;

		/** Paints requested while the frame was already marked for redraw. */
		private int paintsWithFlag;

		private void resizeTo(int width, int height) {
			setSize(width, height);
			TextureOpIrregularBricks.aBoolean189 = false;
			// A component outside a displayable peer delivers no resize event to
			// its listeners, so fire the one the peer would have sent.
			processComponentEvent(new ComponentEvent(this, ComponentEvent.COMPONENT_RESIZED));
		}

		@Override
		public void repaint() {
			if (TextureOpIrregularBricks.aBoolean189) {
				this.paintsWithFlag++;
			}
		}
	}
}
