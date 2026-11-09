package com.jagex.game.runetek5.client.hd.ground;

/**
 * A ground mesh's material map: one texel per terrain tile, saying which two
 * materials the tile is built from.
 *
 * <p>This is the map the terrain blend was waiting on. The stock ground mesh
 * keeps its tile layers while it is built - {@code GlGround.tileLayers[x][z]}
 * is an array of {@code GlGroundLayer}s, each with its own material - and then
 * throws them away: at draw time a layer pass covers a window of tiles with one
 * bound texture, so nothing on the GPU knows that a tile in the middle of it
 * carries a second material. Copying the two ids into a texture before the
 * layers are released leaves exactly that knowledge where the fragment stage can
 * reach it, addressed by the tile the fragment stands on.
 *
 * <p>Channels are the two ids, <b>two bytes each</b>, little endian:
 * {@code (primaryLo, primaryHi, mateLo, mateHi)}. Ids are the client's own
 * material ids, and they are 16-bit - {@code aShortArray107} is read as
 * {@code & 0xFFFF}, and a live region logs materials 408, 181 and 66. They were
 * once packed into one byte per id, which silently rewrote 408 to 152: the
 * fragment then looked the truncated id up in the material map, found another
 * material's cell (or none) and blended it in, so tiles showed the wrong
 * texture or lost theirs. There is no spare channel for a weight any more; a
 * tile has a second material exactly when the two stored ids differ, which is
 * the same rule {@link #weight} states.
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

	/** Bytes per tile: the two ids, two bytes each. */
	public static final int STRIDE = 4;

	/** Full blend strength, for a tile whose layer stack really holds two materials. */
	public static final int FULL_WEIGHT = 255;

	/** The stored id of "no material": both bytes set. */
	public static final int NO_MATERIAL = 0xFFFF;

	/** How many ids one texel can hold; the client's material ids are 16-bit. */
	public static final int MATERIAL_LIMIT = 0x10000;

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
		int primaryId = stored(primary);
		// The mate is kept even when it repeats the primary: the fragment's rule
		// is "two different materials blend", so a tile the game built from one
		// texture twice stays that texture rather than losing its own look.
		int mateId = mate < 0 ? NO_MATERIAL : stored(mate);
		this.texels[at] = (byte) (primaryId & 0xFF);
		this.texels[at + 1] = (byte) (primaryId >>> 8);
		this.texels[at + 2] = (byte) (mateId & 0xFF);
		this.texels[at + 3] = (byte) (mateId >>> 8);
	}

	/** The tile's dominant material, or -1 when the tile is not on the map. */
	public int primary(int tileX, int tileZ) {
		return idAt(tileX, tileZ, 0);
	}

	/** The tile's second material, or -1. */
	public int mate(int tileX, int tileZ) {
		return idAt(tileX, tileZ, 2);
	}

	/** The tile's blend weight byte, 0 or 255: derived, not stored. */
	public int weight(int tileX, int tileZ) {
		int primary = primary(tileX, tileZ);
		int mate = mate(tileX, tileZ);
		return mate >= 0 && mate != primary ? FULL_WEIGHT : 0;
	}

	private int idAt(int tileX, int tileZ, int at) {
		if (tileX < 0 || tileX >= SIZE || tileZ < 0 || tileZ >= SIZE) {
			return -1;
		}
		int index = (tileZ * SIZE + tileX) * STRIDE + at;
		int id = (this.texels[index] & 0xFF) | (this.texels[index + 1] & 0xFF) << 8;
		return id == NO_MATERIAL ? -1 : id;
	}

	/** One id as it is stored: the id itself, or {@link #NO_MATERIAL}. */
	private static int stored(int material) {
		if (material < 0) {
			return NO_MATERIAL;
		}
		return material < MATERIAL_LIMIT ? material : NO_MATERIAL;
	}

	/** The map's bytes, row by row: what the texture is uploaded from. */
	public byte[] bytes() {
		return this.texels;
	}

	/** True when any tile carries a second material, so the map has something to say. */
	public boolean blended() {
		for (int at = 0; at < this.texels.length; at += STRIDE) {
			int primary = (this.texels[at] & 0xFF) | (this.texels[at + 1] & 0xFF) << 8;
			int mate = (this.texels[at + 2] & 0xFF) | (this.texels[at + 3] & 0xFF) << 8;
			if (mate != NO_MATERIAL && mate != primary) {
				return true;
			}
		}
		return false;
	}
}
