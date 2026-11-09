package com.jagex.game.runetek5.client.hd.material;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The block decode jaggl needs: BC5's two channels and BC7 mode 6's RGBA.
 *
 * <p>A wrong bit layout would still produce a plausible picture, so the proofs
 * build the blocks themselves - end points and indices chosen so the expected
 * texel is arithmetic, not a stored image.
 */
@DisplayName("592HD material block decode")
class HdMapBlocksTest {

	@Test
	@DisplayName("a BC5 block decodes its red and green halves independently")
	void bc5_shouldDecodeBothChannels() {
		byte[] block = new byte[16];
		// Red: 200 down to 40, every index zero, so every texel is the first end point.
		block[0] = (byte) 200;
		block[1] = (byte) 40;
		// Green: 30 up to 90, every index seven, so every texel is the second end
		// point (an r0 < r1 block puts 255 at index 7, not an interpolation).
		block[8] = (byte) 30;
		block[9] = (byte) 90;
		writeIndices(block, 2, 0);
		writeIndices(block, 10, 7);

		int[] pixels = HdMapBlocks.decodeBc5(block, 4);

		assertNotNull(pixels);
		assertEquals(16, pixels.length);
		assertAll(
			() -> assertEquals(200, pixels[0] >> 16 & 0xFF),
			() -> assertEquals(255, pixels[0] >> 8 & 0xFF, "an r0 < r1 block's index 7 is 255"),
			() -> assertEquals(255, pixels[0] & 0xFF, "BC5 carries two channels; blue is filled"),
			() -> assertEquals(0xFF, pixels[0] >>> 24),
			() -> assertEquals(pixels[0], pixels[15], "every texel shares the block's palette")
		);
	}

	@Test
	@DisplayName("a torn or absent block is no map, never a half picture")
	void bc5_shouldRejectShortData() {
		assertAll(
			() -> assertNull(HdMapBlocks.decodeBc5(null, 4)),
			() -> assertNull(HdMapBlocks.decodeBc5(new byte[15], 4)),
			() -> assertNull(HdMapBlocks.decodeBc5(new byte[16], 0)),
			() -> assertNull(HdMapBlocks.decodeBc5(new byte[16], 6), "a size that is not 4x4 blocks")
		);
	}

	@Test
	@DisplayName("a BC7 mode 6 block decodes its end points and its indices")
	void bc7_shouldDecodeModeSix() {
		// One end point black, the other white: index 0 is black, index 8 the
		// mid weight, and every channel moves together.
		int[] pixels = HdMapBlocks.decodeBc7(bc7(0, 0, 0, 0, 255, 255, 255, 255, 8), 4);

		assertNotNull(pixels);
		assertAll(
			() -> assertEquals(0, pixels[0] >> 16 & 0xFF, "the first index has three bits, so it is 0 here"),
			() -> assertEquals(135, pixels[1] >> 16 & 0xFF, "weight 34 of 64 between 0 and 255, truncated like the real decoder"),
			() -> assertEquals(135, pixels[1] >> 8 & 0xFF),
			() -> assertEquals(135, pixels[1] & 0xFF),
			() -> assertEquals(135, pixels[1] >>> 24, "BC7 mode 6 carries the alpha end points")
		);

		int[] white = HdMapBlocks.decodeBc7(bc7(255, 255, 255, 255, 255, 255, 255, 255, 0), 4);
		assertNotNull(white);
		assertEquals(0xFFFFFFFF, white[0]);
	}

	@Test
	@DisplayName("a block in another BC7 mode is refused, so the caller can fall back")
	void bc7_shouldRefuseOtherModes() {
		byte[] block = new byte[16];
		// Mode 0: the low seven bits are zero. The library only compresses mode 6.
		assertNull(HdMapBlocks.decodeBc7(block, 4));
		assertNull(HdMapBlocks.decodeBc7(null, 4));
		assertNull(HdMapBlocks.decodeBc7(new byte[15], 4));
	}

	/** Every 3-bit index of one BC4 half set to the same value. */
	private static void writeIndices(byte[] block, int at, int index) {
		long bits = 0L;
		for (int i = 0; i < 16; i++) {
			bits |= (long) index << (3 * i);
		}
		for (int k = 0; k < 6; k++) {
			block[at + k] = (byte) (bits >>> (8 * k));
		}
	}

	/** One BC7 mode 6 block: the two RGBA end points and one index for every texel. */
	private static byte[] bc7(int r0, int g0, int b0, int a0, int r1, int g1, int b1, int a1, int index) {
		long[] words = new long[2];
		// Mode 6 in the low seven bits, then R0, G0, B0, A0 and R1, G1, B1, A1
		// in 14-bit pairs, each end point keeping its p-bit in its R channel.
		put(words, 0, 7, 0x40);
		put(words, 7, 7, r0 >> 1);
		put(words, 21, 7, g0 >> 1);
		put(words, 35, 7, b0 >> 1);
		put(words, 49, 7, a0 >> 1);
		put(words, 14, 7, r1 >> 1);
		put(words, 28, 7, g1 >> 1);
		put(words, 42, 7, b1 >> 1);
		put(words, 56, 7, a1 >> 1);
		put(words, 63, 1, r0 & 1);
		put(words, 64, 1, r1 & 1);
		int pos = 65;
		for (int i = 0; i < 16; i++) {
			int width = i == 0 ? 3 : 4;
			put(words, pos, width, index & ((1 << width) - 1));
			pos += width;
		}
		byte[] out = new byte[16];
		for (int k = 0; k < 8; k++) {
			out[k] = (byte) (words[0] >>> (8 * k));
			out[8 + k] = (byte) (words[1] >>> (8 * k));
		}
		return out;
	}

	private static void put(long[] words, int bit, int width, int value) {
		for (int i = 0; i < width; i++) {
			if ((value >>> i & 1) != 0) {
				int at = bit + i;
				words[at >> 6] |= 1L << (at & 63);
			}
		}
	}
}
