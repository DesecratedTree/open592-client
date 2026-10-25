package com.jagex.game.runetek5.client.hd.water;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The decode that makes a breaking material's numbers mean what the stock
 * effect means by them.
 *
 * <p>{@code BreakingWaterEffect.setEffectParams(int, int)} is the specification:
 * speed from bits 0-1, texture scale from bits 3-5 over 32, depth from the low
 * sixteen bits, shore offset from bits 16-17 over 8, the crest colour from bits
 * 19-22 and 23-26 over 16, and the sharpening exponent from bits 27-30. These
 * tests pin one hand-computed packed value and the two ends of the byte, so a
 * later refactor cannot quietly change what a saved material looks like.
 */
@DisplayName("HD breaking water parameters")
class HdWaterBreakingTest {

	/**
	 * A hand-packed parameter word: depth 64, offset 2 (0.25), intensity y 4
	 * (0.25), intensity x 8 (0.5), exponent 3.
	 *
	 * <pre>
	 * 64 | 2 &lt;&lt; 16 | 4 &lt;&lt; 19 | 8 &lt;&lt; 23 | 3 &lt;&lt; 27 = 471990336
	 * </pre>
	 */
	private static final int PACKED = 471990336;

	@Test
	@DisplayName("a material's two fields decode exactly as the stock effect decodes them")
	void decode_shouldMirrorTheStockEffect() {
		HdWaterBreaking breaking = HdWaterBreaking.of((byte) 24, PACKED);

		assertAll(
			() -> assertEquals(1, breaking.speed(), "bits 0-1 of 24 are zero: 1 << 0"),
			() -> assertEquals(0.25F, breaking.textureScale(), "bits 3-5 of 24 are 3: 1 << 3 over 32"),
			() -> assertEquals(64.0F, breaking.depth(), "the low sixteen bits"),
			() -> assertEquals(0.25F, breaking.offset(), "bits 16-17 are 2: 2 over 8"),
			() -> assertEquals(8.0F / 16.0F, breaking.intensityX(), "bits 23-26 are 8"),
			() -> assertEquals(4.0F / 16.0F, breaking.intensityY(), "bits 19-22 are 4"),
			() -> assertEquals(3.0F, breaking.exponent(), "bits 27-30 are 3")
		);
	}

	@Test
	@DisplayName("a negative byte field decodes by its low bits, as the stock path's masks read it")
	void negativeByte_shouldDecodeTheSameBits() {
		// The toolkit widens the byte field to an int, so 0xFF reaches the stock
		// effect as -1. The masks only read bits 0-5, which are the same in the
		// sign-extended value, so the decode must be 8 and 4.0 rather than a
		// negative shift.
		HdWaterBreaking breaking = HdWaterBreaking.of((byte) 0xFF, 0);

		assertAll(
			() -> assertEquals(8, breaking.speed(), "bits 0-1 are both set: 1 << 3"),
			() -> assertEquals(4.0F, breaking.textureScale(), "bits 3-5 are all set: 128 over 32")
		);
	}

	@Test
	@DisplayName("the extreme word stays inside the stock ranges")
	void extremes_shouldStayInRange() {
		HdWaterBreaking breaking = HdWaterBreaking.of((byte) 0xFF, 0xFFFFFFFF);

		assertAll(
			() -> assertEquals(65535.0F, breaking.depth(), "sixteen bits"),
			() -> assertEquals(3.0F / 8.0F, breaking.offset(), "two bits over 8"),
			() -> assertEquals(15.0F / 16.0F, breaking.intensityX()),
			() -> assertEquals(15.0F / 16.0F, breaking.intensityY()),
			() -> assertEquals(15.0F, breaking.exponent(), "four bits"),
			() -> assertTrue(breaking.speed() >= 1 && breaking.speed() <= 8, "speed is 1..8"),
			() -> assertTrue(breaking.textureScale() > 0.0F, "a scale of zero would collapse the texture")
		);
	}

	@Test
	@DisplayName("NONE is the absent decode, and a real breaking material is never absent")
	void none_shouldBeInert() {
		assertAll(
			() -> assertTrue(HdWaterBreaking.NONE.absent()),
			() -> assertFalse(HdWaterBreaking.of((byte) 24, PACKED).absent()),
			() -> assertEquals(1, HdWaterBreaking.NONE.speed(), "the decode of a zero byte"),
			() -> assertEquals(1.0F / 32.0F, HdWaterBreaking.NONE.textureScale()),
			() -> assertEquals(0.0F, HdWaterBreaking.NONE.depth()),
			// A material whose two fields are both zero is still a breaking
			// material: absent is the marker this row puts on non-breaking draws,
			// not an inference from the numbers.
			() -> assertFalse(HdWaterBreaking.of((byte) 0, 0).absent()),
			() -> assertNotSame(HdWaterBreaking.NONE, HdWaterBreaking.of((byte) 24, PACKED))
		);
	}

	@Test
	@DisplayName("sameAs compares the numbers, which is what skips a repeated upload")
	void sameAs_shouldCompareEveryField() {
		HdWaterBreaking first = HdWaterBreaking.of((byte) 24, PACKED);
		HdWaterBreaking again = HdWaterBreaking.of((byte) 24, PACKED);
		HdWaterBreaking otherDepth = HdWaterBreaking.of((byte) 24, PACKED + 1);
		HdWaterBreaking otherScale = HdWaterBreaking.of((byte) 32, PACKED);

		assertAll(
			() -> assertTrue(first.sameAs(again)),
			() -> assertFalse(first.sameAs(otherDepth), "one bit of depth is a different material"),
			() -> assertFalse(first.sameAs(otherScale), "a different scale is a different material"),
			() -> assertFalse(first.sameAs(null), "nothing has been uploaded yet"),
			() -> assertFalse(first.sameAs(HdWaterBreaking.NONE))
		);
	}
}
