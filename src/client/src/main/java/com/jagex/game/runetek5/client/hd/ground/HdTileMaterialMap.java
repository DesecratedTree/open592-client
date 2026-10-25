package com.jagex.game.runetek5.client.hd.ground;

/**
 * A ground mesh's material map: one texel per terrain tile, saying which two
 * materials the tile is built from and how strongly the second one blends in.
 *
 * <p>This is the map the terrain blend was waiting on. The stock ground mesh
 * keeps its tile layers while it is built - {@code GlGround.tileLayers[x][z]}
 * is an array of {@code GlGroundLayer}s, each with its own material - and then
 * throws them away: at draw time a layer pass covers a window of tiles with one
 * bound texture, so nothing on the GPU knows that a tile in the middle of it
 * carries a second material. Copying the two ids and a weight into a texture
 * before the layers are released leaves exactly that knowledge where the
 * fragment stage can reach it, addressed by the tile the fragment stands on.
 *
 * <p>Channels are {@code (primary, mate, weight, flags)}. Ids are the client's
 * own material ids, which a 592 cache keeps under 256; the weight is 255 when
 * the tile really has two materials and 0 when it has one, so a single-material
 * tile costs the shader one branch and draws its own texture alone. The flags
 * byte is reserved and always zero today.
 *
 * <p>The texture is a fixed square because its addressing is a dividing sum:
 * a tile is {@code floor(sceneXZ / tileSize)} and a texel is that divided by
 * the map's side. {@link #SIZE} of 256 matches the largest region window the
 * client builds, and a tile outside the mesh reads texel 255 - a row no mesh
 * writes - so an out-of-range fragment falls back to the stock draw.
 */
public final class HdTileMaterialMap {

	/** Tiles per side; covers every region window the client builds. */
	public static final int SIZE = 256;

	/** Tiles one map holds. */
	public static final int TILES = SIZE * SIZE;

	/** Bytes per tile: primary, mate, weight, flags. */
	public static final int STRIDE = 4;

	/** Full blend strength, for a tile whose layer stack really holds two materials. */
	public static final int FULL_WEIGHT = 255;

	private final byte[] texels = new byte[TILES * STRIDE];

	/**
	 * Records one tile's materials.
	 *
	 * @param tileX   the tile's column in the mesh, 0-based
	 * @param tileZ   the tile's row in the mesh, 0-based
	 * @param primary the material the tile's dominant layer uses, or -1 for none
	 * @param mate    the tile's second material, or -1 when it has one
	 */
	public void set(int tileX, int tileZ, int primary, int mate) {
		if (tileX < 0 || tileX >= SIZE || tileZ < 0 || tileZ >= SIZE) {
			return;
		}
		int at = (tileZ * SIZE + tileX) * STRIDE;
		this.texels[at] = (byte) primary;
		this.texels[at + 1] = (byte) mate;
		this.texels[at + 2] = (byte) (mate >= 0 && mate != primary ? FULL_WEIGHT : 0);
		this.texels[at + 3] = 0;
	}

	/** The tile's dominant material, or -1 when the tile is not on the map. */
	public int primary(int tileX, int tileZ) {
		return texel(tileX, tileZ, 0);
	}

	/** The tile's second material, or -1. */
	public int mate(int tileX, int tileZ) {
		return texel(tileX, tileZ, 1);
	}

	/** The tile's blend weight byte, 0 or 255. */
	public int weight(int tileX, int tileZ) {
		return texel(tileX, tileZ, 2);
	}

	private int texel(int tileX, int tileZ, int channel) {
		if (tileX < 0 || tileX >= SIZE || tileZ < 0 || tileZ >= SIZE) {
			return channel == 2 ? 0 : -1;
		}
		int value = this.texels[(tileZ * SIZE + tileX) * STRIDE + channel] & 0xFF;
		// An id byte of 255 is "no material": a 592 cache's ids stop short of it,
		// and the shader's mate test uses the same sentinel.
		return channel != 2 && value == 255 ? -1 : value;
	}

	/** The map's bytes, row by row: what the texture is uploaded from. */
	public byte[] bytes() {
		return this.texels;
	}

	/** True when any tile carries a second material, so the map has something to say. */
	public boolean blended() {
		for (int at = 2; at < this.texels.length; at += STRIDE) {
			if (this.texels[at] != 0) {
				return true;
			}
		}
		return false;
	}
}
