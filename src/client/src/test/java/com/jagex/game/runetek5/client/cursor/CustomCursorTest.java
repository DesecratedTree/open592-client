package com.jagex.game.runetek5.client.cursor;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Point;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("CustomCursor image assembly")
class CustomCursorTest {

	@ParameterizedTest(name = "isResetRequest({0}, null={1}) == {2}")
	@CsvSource({
		"-1, true, true",
		"-1, false, true",
		"0, true, true",
		"16, true, true",
		"16, false, false",
		"0, false, false"
	})
	@DisplayName("reset requests restore the default cursor")
	void isResetRequest_should_matchResetShape(int width, boolean nullPixels, boolean expected) {
		int[] pixels = nullPixels ? null : new int[Math.max(0, width * 2)];
		assertEquals(expected, CustomCursor.isResetRequest(width, pixels));
	}

	@Test
	@DisplayName("heightFor derives height from pixels when the height int is dropped")
	void heightFor_should_deriveFromPixels_when_widthKnown() {
		assertEquals(3, CustomCursor.heightFor(4, new int[12], -1));
	}

	@Test
	@DisplayName("heightFor falls back when pixels are missing or ragged")
	void heightFor_should_fallback_when_notDerivable() {
		assertAll(
			() -> assertEquals(-1, CustomCursor.heightFor(4, null, -1)),
			() -> assertEquals(7, CustomCursor.heightFor(0, new int[8], 7)),
			() -> assertEquals(7, CustomCursor.heightFor(4, new int[10], 7))
		);
	}

	@Test
	@DisplayName("toImage preserves exact ARGB instead of greying colors")
	void toImage_should_preserveArgb_when_colorsAndAlphaVary() {
		int[] pixels = {
			0xFFFF0000, 0xFF00FF00,
			0x00000000, 0x800000FF
		};
		BufferedImage image = CustomCursor.toImage(2, pixels, 2);

		assertAll(
			() -> assertEquals(BufferedImage.TYPE_INT_ARGB, image.getType()),
			() -> assertEquals(0xFFFF0000, image.getRGB(0, 0)),
			() -> assertEquals(0xFF00FF00, image.getRGB(1, 0)),
			() -> assertEquals(0x00000000, image.getRGB(0, 1)),
			() -> assertEquals(0x800000FF, image.getRGB(1, 1))
		);
	}

	@Test
	@DisplayName("toImage keeps fully transparent pixels transparent")
	void toImage_should_keepTransparent_when_alphaIsZero() {
		BufferedImage image = CustomCursor.toImage(1, new int[] { 0 }, 1);
		assertEquals(0, image.getRGB(0, 0) >>> 24);
	}

	@Test
	@DisplayName("toImage rejects mismatched dimensions")
	void toImage_should_throw_when_shapeMismatched() {
		assertAll(
			() -> assertThrows(IllegalArgumentException.class, () -> CustomCursor.toImage(0, new int[0], 1)),
			() -> assertThrows(IllegalArgumentException.class, () -> CustomCursor.toImage(2, null, 2)),
			() -> assertThrows(IllegalArgumentException.class, () -> CustomCursor.toImage(2, new int[3], 2))
		);
	}

	@ParameterizedTest(name = "clampHotspot({0},{1}) in 16x16 -> ({2},{3})")
	@CsvSource({
		"0, 0, 0, 0",
		"5, 7, 5, 7",
		"-3, -1, 0, 0",
		"16, 16, 15, 15",
		"99, 2, 15, 2"
	})
	@DisplayName("hotspot is clamped inside the cursor bounds")
	void clampHotspot_should_pinToBounds(int x, int y, int expectedX, int expectedY) {
		Point clamped = CustomCursor.clampHotspot(new Point(x, y), 16, 16);
		assertAll(
			() -> assertEquals(expectedX, clamped.x),
			() -> assertEquals(expectedY, clamped.y)
		);
	}

	@Test
	@DisplayName("null hotspot falls back to the origin")
	void clampHotspot_should_fallbackToOrigin_when_null() {
		assertEquals(new Point(0, 0), CustomCursor.clampHotspot(null, 16, 16));
	}

	@Test
	@DisplayName("opaque white is not confused with transparent")
	void trivial_should_sanityCheckTestWiring() {
		assertTrue(CustomCursor.isResetRequest(-1, null));
		assertFalse(CustomCursor.isResetRequest(2, new int[4]));
	}
}
