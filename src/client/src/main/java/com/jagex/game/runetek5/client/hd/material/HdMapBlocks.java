package com.jagex.game.runetek5.client.hd.material;

/**
 * The two block formats 634HD precompresses its material maps to, decoded to
 * RGBA on the CPU because jaggl can only sample RGBA.
 *
 * <p>Upstream uploads the blocks as they are where the driver can sample them
 * ({@code HdMaterialMaps}: {@code GL_COMPRESSED_RG_RGTC2} for normals,
 * {@code COMPRESSED_RGBA_BPTC} for the packed and emissive maps) and decodes
 * them here when it cannot - this client is always in the second case, so this
 * class is the translation of upstream's {@code decodeBc5}/{@code decodeBc7}
 * fallback rather than a new format reader.
 *
 * <p>BC5 stores the normal's two tangent-space channels (z is rebuilt by the
 * shader), so its blue channel is 255 and its alpha 255. BC7 is decoded for
 * mode 6 only, the one mode {@code tools/compress_material_maps.py} writes: one
 * subset, RGBA end points with a p-bit each and 4-bit indices (the first index
 * three bits, its high bit implied zero, as the 128-bit block leaves room for
 * only 63). The packed map's alpha is the height, exactly as upstream packs it.
 */
public final class HdMapBlocks {

	/** BC7 mode 6's interpolation table, 0 to 64. */
	private static final int[] BC7_WEIGHTS = { 0, 4, 9, 13, 17, 21, 26, 30, 34, 38, 43, 47, 51, 55, 60, 64 };

	private HdMapBlocks() {
	}

	/**
	 * The square side of a block file's level 0, inferred from its whole mip
	 * chain (the shipped sets are 256 square; a 128-square set still reads).
	 */
	public static int sideOf(byte[] data) {
		if (data == null) {
			return 0;
		}
		for (int side = 512; side >= 32; side >>= 1) {
			if (mipBytes(side) <= data.length) {
				return side;
			}
		}
		return 0;
	}

	/** Every mip level of a square block image, in bytes. */
	private static int mipBytes(int side) {
		int total = 0;
		for (int level = side; level >= 1; level >>= 1) {
			int blocks = (level + 3) >> 2;
			total += 16 * blocks * blocks;
		}
		return total;
	}

	/**
	 * One BC5 image (two BC4 blocks per 4x4 texel block: x then y) to ARGB
	 * texels, blue and alpha 255. Null when the data is absent or too short.
	 */
	public static int[] decodeBc5(byte[] data, int size) {
		if (data == null || size <= 0 || (size & 3) != 0) {
			return null;
		}
		int across = size / 4;
		if (data.length < across * across * 16) {
			return null;
		}
		int[] out = new int[size * size];
		int[] palette = new int[8];
		int[] red = new int[16];
		int[] green = new int[16];
		for (int by = 0; by < across; by++) {
			for (int bx = 0; bx < across; bx++) {
				int at = (by * across + bx) * 16;
				for (int channel = 0; channel < 2; channel++) {
					int base = at + channel * 8;
					int first = data[base] & 0xFF;
					int second = data[base + 1] & 0xFF;
					palette[0] = first;
					palette[1] = second;
					if (first > second) {
						for (int k = 2; k < 8; k++) {
							palette[k] = ((8 - k) * first + (k - 1) * second + 3) / 7;
						}
					} else {
						for (int k = 2; k < 6; k++) {
							palette[k] = ((6 - k) * first + (k - 1) * second + 2) / 5;
						}
						palette[6] = 0;
						palette[7] = 255;
					}
					long bits = 0L;
					for (int k = 0; k < 6; k++) {
						bits |= (long) (data[base + 2 + k] & 0xFF) << (8 * k);
					}
					for (int i = 0; i < 16; i++) {
						int value = palette[(int) (bits >>> (3 * i)) & 7];
						if (channel == 0) {
							red[i] = value;
						} else {
							green[i] = value;
						}
					}
				}
				for (int i = 0; i < 16; i++) {
					int x = bx * 4 + (i & 3);
					int y = by * 4 + (i >> 2);
					out[y * size + x] = 0xFF000000 | red[i] << 16 | green[i] << 8 | 0xFF;
				}
			}
		}
		return out;
	}

	/**
	 * One BC7 image (mode 6 blocks) to ARGB texels; the alpha channel is the
	 * packed map's height. Null when any block is another mode, or when the
	 * data is absent or too short - a caller's signal to fall back to the PNG.
	 */
	public static int[] decodeBc7(byte[] data, int size) {
		if (data == null || size <= 0 || (size & 3) != 0) {
			return null;
		}
		int across = size / 4;
		if (data.length < across * across * 16) {
			return null;
		}
		int[] out = new int[size * size];
		int[] first = new int[4];
		int[] second = new int[4];
		for (int by = 0; by < across; by++) {
			for (int bx = 0; bx < across; bx++) {
				int at = (by * across + bx) * 16;
				long low = 0L;
				long high = 0L;
				for (int k = 0; k < 8; k++) {
					low |= (long) (data[at + k] & 0xFF) << (8 * k);
					high |= (long) (data[at + 8 + k] & 0xFF) << (8 * k);
				}
				// The mode is the low bits of the block: 6 is 0x40 in seven bits.
				if ((low & 0x7FL) != 0x40L) {
					return null;
				}
				int p0 = (int) (low >>> 63) & 1;
				int p1 = (int) high & 1;
				for (int c = 0; c < 4; c++) {
					first[c] = ((int) (low >>> (7 + c * 14)) & 0x7F) << 1 | p0;
					second[c] = ((int) (low >>> (14 + c * 14)) & 0x7F) << 1 | p1;
				}
				// Bits 65 onwards: sixteen indices, the first three bits wide (its top
				// bit implied zero), the rest four.
				int pos = 1;
				for (int i = 0; i < 16; i++) {
					int width = i == 0 ? 3 : 4;
					int weight = BC7_WEIGHTS[(int) (high >>> pos) & ((1 << width) - 1)];
					pos += width;
					int x = bx * 4 + (i & 3);
					int y = by * 4 + (i >> 2);
					int red = (64 - weight) * first[0] + weight * second[0] + 32 >> 6;
					int green = (64 - weight) * first[1] + weight * second[1] + 32 >> 6;
					int blue = (64 - weight) * first[2] + weight * second[2] + 32 >> 6;
					int alpha = (64 - weight) * first[3] + weight * second[3] + 32 >> 6;
					out[y * size + x] = alpha << 24 | red << 16 | green << 8 | blue;
				}
			}
		}
		return out;
	}
}
