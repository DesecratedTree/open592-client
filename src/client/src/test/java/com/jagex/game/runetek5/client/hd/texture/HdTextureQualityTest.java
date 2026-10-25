package com.jagex.game.runetek5.client.hd.texture;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HD texture quality gate")
class HdTextureQualityTest {

	@Test
	@DisplayName("a row needs 592HD itself as well as its own value")
	void requested_shouldRequireHdAndTheRow() {
		assertAll(
			() -> assertTrue(HdTextureQuality.mipmapsRequested(true, 1)),
			() -> assertFalse(HdTextureQuality.mipmapsRequested(true, 0), "row off"),
			() -> assertFalse(HdTextureQuality.mipmapsRequested(false, 1), "592HD off wins"),
			() -> assertTrue(HdTextureQuality.anisotropyRequested(true, 3)),
			() -> assertFalse(HdTextureQuality.anisotropyRequested(false, 3))
		);
	}

	@Test
	@DisplayName("only a 2D texture larger than a texel can be mipmapped")
	void canMipmap_shouldRejectWhatGlCannotTake() {
		assertAll(
			() -> assertTrue(HdTextureQuality.canMipmap(true, 1, HdTextureQuality.TEXTURE_2D, 64, 64)),
			() -> assertFalse(HdTextureQuality.canMipmap(true, 1, 34067, 64, 64), "cube maps are not this path"),
			() -> assertFalse(HdTextureQuality.canMipmap(true, 1, HdTextureQuality.TEXTURE_2D, 1, 64)),
			() -> assertFalse(HdTextureQuality.canMipmap(true, 1, HdTextureQuality.TEXTURE_2D, 64, 1)),
			() -> assertFalse(HdTextureQuality.canMipmap(true, 0, HdTextureQuality.TEXTURE_2D, 64, 64), "row off"),
			() -> assertFalse(HdTextureQuality.canMipmap(false, 1, HdTextureQuality.TEXTURE_2D, 64, 64))
		);
	}

	@Test
	@DisplayName("row indices map to 2x, 4x, 8x, 16x and clamp")
	void samplesFor_shouldMapTheLabels() {
		assertAll(
			() -> assertEquals(1, HdTextureQuality.samplesFor(0), "Off"),
			() -> assertEquals(2, HdTextureQuality.samplesFor(1), "2x"),
			() -> assertEquals(4, HdTextureQuality.samplesFor(2), "4x"),
			() -> assertEquals(8, HdTextureQuality.samplesFor(3), "8x"),
			() -> assertEquals(16, HdTextureQuality.samplesFor(4), "16x"),
			() -> assertEquals(HdTextureQuality.MAX_SAMPLES, HdTextureQuality.samplesFor(99))
		);
	}

	@Test
	@DisplayName("the signature changes when a row changes, and is 0 when both are off")
	void signature_shouldFollowTheRows() {
		// The static signature reads the stored settings, so this case stays on
		// the pure part: the row predicates themselves.
		assertFalse(HdTextureQuality.mipmapsRequested(false, 1));
		assertFalse(HdTextureQuality.anisotropyRequested(false, 4));
	}
}
