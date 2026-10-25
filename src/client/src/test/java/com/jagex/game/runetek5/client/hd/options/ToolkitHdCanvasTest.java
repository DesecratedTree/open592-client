package com.jagex.game.runetek5.client.hd.options;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The one contract the in-game text path gets wrong silently.
 *
 * <p>{@code AbstractFontRenderer.method5048} takes ARGB, and the stock
 * interface supplies {@code 255 - alpha << 24 | colour}. The pane's own colours
 * are 0xRRGGBB, so passing one straight through means alpha 0 and a string that
 * draws nothing while every rectangle around it appears - the exact report this
 * test pins.
 */
@DisplayName("592HD toolkithd canvas")
class ToolkitHdCanvasTest {

	@Test
	@DisplayName("text colours are composed with an opaque alpha")
	void textColour_shouldBeOpaque() {
		assertAll(
			() -> assertEquals(0xFFFFE8C0, ToolkitHdCanvas.textColour(0xFFE8C0)),
			() -> assertEquals(0xFF000000, ToolkitHdCanvas.textColour(0x000000)),
			() -> assertEquals(0xFFFFFFFF, ToolkitHdCanvas.textColour(0xFFFFFFFF),
				"an already-opaque colour keeps its alpha"),
			() -> assertEquals(0xFF12AB34, ToolkitHdCanvas.textColour(0x0012AB34),
				"a transparent input is made opaque rather than left invisible")
		);
	}
}
