package com.jagex.game.runetek5.client.hd.light;

import java.util.Arrays;

import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.deob.Class191;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.TileLighting;
import com.jagex.game.runetek5.entity.PlayerEntity;

/**
 * The carried and room light terms for one frame, taken from the light state
 * the stock client already keeps.
 *
 * <p>There are two sources in this client, and this class reads both without
 * adding a third:
 *
 * <ul>
 * <li><b>Carried light</b> - the map lights registered on the tile the local
 * player stands on ({@code Terrain.aLongArrayArrayArray2}, the same per-tile
 * light set the scene resolves for every model). The strongest of them is
 * re-anchored at the carrier: its colour, radius and intensity are the map
 * light's own, only its position moves with the player. A tile with no light
 * registered has no carried light, which is why walking into an unlit room does
 * not switch on a made-up lamp.</li>
 * <li><b>Room light</b> - the per-region environment {@code TileLighting} at the
 * same eight-by-eight-chunk granularity the stock renderer applies it in
 * ({@code DummyInputStream.method3623} reads the chunk under the player and
 * feeds it to {@code GlToolkit.B}). The row keeps that per-region structure
 * instead of applying one chunk's ambient to the whole scene, and takes the
 * region's colour from the map lights registered on its tiles. A chunk with no
 * {@code TileLighting} has no room light.</li>
 * </ul>
 *
 * <p>Built once per frame from {@link #capture()} at the world draw's own seam,
 * and published through {@link #current()} like {@code HdAtmosphere}: the
 * pipeline is a function of what it is handed, so a test can drive every value
 * without touching the client's globals. With both rows off {@link #capture()}
 * returns {@link #OFF} before reading anything, so the frame path costs two
 * field reads and issues no GL call at all.
 */
public final class HdLightFrame {

	/** Both rows off; the frame GL's zeroed uniforms already describe. */
	public static final HdLightFrame OFF = new HdLightFrame(null, new HdRoomLight[0]);

	/** Room regions one frame may carry: the three-by-three chunks around the carrier. */
	public static final int MAX_ROOM_REGIONS = 9;

	/** Tiles per region chunk; the unit {@code TileLighting} is stored in. */
	public static final int CHUNK_TILES = 8;

	/** Scene units per tile: the client's 1/128-tile position unit. */
	public static final int TILE_UNITS = 128;

	/** Scene units per region chunk. */
	public static final int CHUNK_UNITS = CHUNK_TILES * TILE_UNITS;

	/** How far above the carrier's feet the carried light sits, in scene units. */
	public static final int CARRIED_HEIGHT = 64;

	private static final HdRoomLight[] NO_REGIONS = new HdRoomLight[0];

	private static HdLightFrame current = OFF;

	private final HdLightSource carried;

	private final HdRoomLight[] regions;

	private HdLightFrame(HdLightSource carried, HdRoomLight[] regions) {
		this.carried = carried;
		this.regions = regions;
	}

	/** The frame the current draw should use; {@link #OFF} until a capture runs. */
	public static HdLightFrame current() {
		return current;
	}

	/**
	 * Rebuilds this frame's terms from the live client state and publishes them.
	 *
	 * <p>Called once per frame from the world draw's seam, after the stock
	 * environment update has chosen the region's {@code TileLighting}, so the
	 * two are looking at the same chunk. Returns {@link #OFF} - and reads
	 * nothing - when both rows are off.
	 */
	public static HdLightFrame capture() {
		boolean carriedRow = HdLightGate.carriedRequested();
		boolean roomRow = HdLightGate.roomRequested();
		if (!carriedRow && !roomRow) {
			current = OFF;
			return OFF;
		}
		PlayerEntity player = Static1.aClass16_Sub1_Sub5_Sub1_1;
		if (player == null) {
			current = OFF;
			return OFF;
		}
		int plane = player.aByte82;
		int tileX = player.anInt6893 >> 7;
		int tileZ = player.anInt6892 >> 7;
		HdLightSource carried = carriedRow ? carried(player.anInt6893, player.anInt6889 + CARRIED_HEIGHT,
			player.anInt6892, tileLights(plane, tileX, tileZ)) : null;
		HdRoomLight[] regions = roomRow ? regions(player.anInt6893, player.anInt6892, plane) : NO_REGIONS;
		HdLightFrame frame = new HdLightFrame(carried, regions);
		// Value equality, not identity: a stationary player under a steady light
		// produces the same frame, and the pipeline can then keep the uniforms it
		// already uploaded.
		current = frame.equals(current) ? current : frame;
		return current;
	}

	/** Publishes a frame without touching the client's state; the frame test's hook. */
	public static void publishForTest(HdLightFrame frame) {
		current = frame == null ? OFF : frame;
	}

	/** Forgets the published frame. */
	public static void resetForTest() {
		current = OFF;
	}

	/** Builds a frame from explicit values; the entry point every test uses. */
	public static HdLightFrame of(HdLightSource carried, HdRoomLight[] regions) {
		HdRoomLight[] copy = regions == null ? NO_REGIONS : regions;
		return new HdLightFrame(carried, copy);
	}

	/**
	 * The strongest of {@code candidates}, or null when the tile has none.
	 *
	 * <p>Strength is intensity times colour luminance, so a bright white torch
	 * beats a dim red one rather than the other way round. The winner keeps every
	 * value it came with; only {@link #capture()} moves its position.
	 */
	public static HdLightSource strongest(HdLightSource[] candidates) {
		if (candidates == null) {
			return null;
		}
		HdLightSource strongest = null;
		float best = 0.0F;
		for (int i = 0; i < candidates.length; i++) {
			HdLightSource candidate = candidates[i];
			if (candidate == null) {
				continue;
			}
			float weight = candidate.weight();
			if (weight > best) {
				best = weight;
				strongest = candidate;
			}
		}
		return strongest;
	}

	/**
	 * One region's room light from its own ambient and the colours of the map
	 * lights registered on its tiles. Null when the region has no
	 * {@code TileLighting} or its ambient is zero, i.e. when the region is not a
	 * light source at all.
	 */
	public static HdRoomLight region(int chunkX, int chunkZ, float ambient, PointLight[] lights) {
		if (ambient <= 0.0F) {
			return null;
		}
		int weight = 0;
		int red = 0;
		int green = 0;
		int blue = 0;
		if (lights != null) {
			for (int i = 0; i < lights.length; i++) {
				PointLight light = lights[i];
				if (light == null || light.getIntensity() <= 0.0F) {
					continue;
				}
				int share = Math.max(1, Math.round(light.getIntensity() * 255.0F));
				int rgb = light.method3162();
				red += (rgb >> 16 & 0xFF) * share;
				green += (rgb >> 8 & 0xFF) * share;
				blue += (rgb & 0xFF) * share;
				weight += share;
			}
		}
		int rgb = 0xFFFFFF;
		if (weight > 0) {
			rgb = clamp(red / weight) << 16 | clamp(green / weight) << 8 | clamp(blue / weight);
		}
		float strength = ambient / HdRoomLight.DEFAULT_AMBIENT * HdRoomLight.GAIN;
		return new HdRoomLight(chunkX * CHUNK_UNITS, chunkZ * CHUNK_UNITS,
			(chunkX + 1) * CHUNK_UNITS, (chunkZ + 1) * CHUNK_UNITS, rgb, strength);
	}

	/** The carried light: the strongest source, moved to the carrier's position. */
	private static HdLightSource carried(int carrierX, int carrierY, int carrierZ, HdLightSource[] candidates) {
		HdLightSource strongest = strongest(candidates);
		return strongest == null ? null : strongest.at(carrierX, carrierY, carrierZ);
	}

	/** The map lights registered on one tile, nulls trimmed; a fresh array per call. */
	private static HdLightSource[] tileLights(int plane, int tileX, int tileZ) {
		long[][][] volume = Terrain.aLongArrayArrayArray2;
		long[][] tiles = volume != null && plane >= 0 && plane < volume.length ? volume[plane] : null;
		if (tiles == null || tileX < 0 || tileZ < 0 || tileX >= tiles.length || tileZ >= tiles[tileX].length) {
			return null;
		}
		long packed = tiles[tileX][tileZ];
		if (packed == 0L) {
			return null;
		}
		Class191[] table = StructTypeList.aClass191Array1;
		if (table == null) {
			return null;
		}
		HdLightSource[] sources = new HdLightSource[4];
		int count = 0;
		for (int shift = 0; shift <= 48; shift += 16) {
			int id = (int) (packed >>> shift & 0xFFFFL);
			if (id <= 0) {
				break;
			}
			if (id > table.length || table[id - 1] == null) {
				continue;
			}
			sources[count++] = HdLightSource.of(table[id - 1].aClass4_Sub21_2);
			if (count == sources.length) {
				break;
			}
		}
		return sources;
	}

	/**
	 * The three-by-three chunks around the carrier, each with its own room light.
	 *
	 * <p>The window is centred on the carrier's chunk, so the block the player
	 * is standing in is always present and the eight around it are too. Regions
	 * outside the window contribute nothing; that is the row's coverage, stated
	 * in the feature's documentation rather than left to look like a bug.
	 */
	private static HdRoomLight[] regions(int carrierX, int carrierZ, int plane) {
		TileLighting[][] chunks = Static1.aClass155ArrayArray1;
		if (chunks == null) {
			return NO_REGIONS;
		}
		int centreX = Math.max(0, carrierX) / CHUNK_UNITS;
		int centreZ = Math.max(0, carrierZ) / CHUNK_UNITS;
		HdRoomLight[] regions = new HdRoomLight[MAX_ROOM_REGIONS];
		int count = 0;
		for (int offsetX = -1; offsetX <= 1; offsetX++) {
			for (int offsetZ = -1; offsetZ <= 1; offsetZ++) {
				int chunkX = centreX + offsetX;
				int chunkZ = centreZ + offsetZ;
				if (chunkX < 0 || chunkZ < 0 || chunkX >= chunks.length || chunks[chunkX] == null
					|| chunkZ >= chunks[chunkX].length) {
					continue;
				}
				TileLighting tile = chunks[chunkX][chunkZ];
				if (tile == null) {
					continue;
				}
				HdRoomLight region = region(chunkX, chunkZ, tile.aFloat150,
					chunkLights(plane, chunkX, chunkZ));
				if (region != null && !region.isDark()) {
					regions[count++] = region;
				}
			}
		}
		if (count == 0) {
			return NO_REGIONS;
		}
		return Arrays.copyOf(regions, count);
	}

	/** Every map light registered on a chunk's sixty-four tiles, duplicates kept. */
	private static PointLight[] chunkLights(int plane, int chunkX, int chunkZ) {
		long[][][] volume = Terrain.aLongArrayArrayArray2;
		long[][] tiles = volume != null && plane >= 0 && plane < volume.length ? volume[plane] : null;
		Class191[] table = StructTypeList.aClass191Array1;
		if (tiles == null || table == null) {
			return null;
		}
		PointLight[] lights = new PointLight[CHUNK_TILES * CHUNK_TILES * 4];
		int count = 0;
		int startX = chunkX * CHUNK_TILES;
		int startZ = chunkZ * CHUNK_TILES;
		for (int tileX = startX; tileX < startX + CHUNK_TILES && tileX < tiles.length; tileX++) {
			if (tileX < 0) {
				continue;
			}
			for (int tileZ = startZ; tileZ < startZ + CHUNK_TILES && tileZ < tiles[tileX].length; tileZ++) {
				if (tileZ < 0) {
					continue;
				}
				long packed = tiles[tileX][tileZ];
				if (packed == 0L) {
					continue;
				}
				for (int shift = 0; shift <= 48; shift += 16) {
					int id = (int) (packed >>> shift & 0xFFFFL);
					if (id <= 0) {
						break;
					}
					if (id <= table.length && table[id - 1] != null) {
						lights[count++] = table[id - 1].aClass4_Sub21_2;
					}
					if (count == lights.length) {
						return lights;
					}
				}
			}
		}
		return count == 0 ? null : lights;
	}

	private static int clamp(int channel) {
		return channel < 0 ? 0 : (channel > 255 ? 255 : channel);
	}

	public HdLightSource carried() {
		return this.carried;
	}

	public HdRoomLight[] regions() {
		return this.regions;
	}

	public int regionCount() {
		return this.regions.length;
	}

	/** True when this frame would add nothing at all. */
	public boolean isEmpty() {
		return this == OFF || (this.carried == null && this.regions.length == 0);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HdLightFrame)) {
			return false;
		}
		HdLightFrame frame = (HdLightFrame) other;
		return (this.carried == null ? frame.carried == null : this.carried.equals(frame.carried))
			&& Arrays.equals(this.regions, frame.regions);
	}

	@Override
	public int hashCode() {
		return (this.carried == null ? 0 : this.carried.hashCode()) * 31 + Arrays.hashCode(this.regions);
	}

	@Override
	public String toString() {
		return "HdLightFrame[" + (this.carried == null ? "no carried light" : this.carried.toString())
			+ ", regions=" + Arrays.toString(this.regions) + "]";
	}
}
