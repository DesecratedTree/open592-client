package com.jagex.game.runetek5.client.hd.content;

/**
 * One {@code tile_overrides.json} entry: a material for the ground tiles it
 * matches.
 *
 * <p>A tile matches when every constraint the entry states agrees: its area
 * holds the tile, its underlay list names the floor's underlay, and its
 * overlay list names its overlay. An entry that states no constraint matches
 * nothing - unlike a model override, a tile entry with no predicate would
 * repaint the whole world, so the loader refuses it.
 */
public final class HdTileOverride {

	final String name;

	final int[] underlays;

	final int[] overlays;

	final int[] area;

	final int material;

	final float scale;

	final boolean replace;

	HdTileOverride(String name, int[] underlays, int[] overlays, int[] area, int material, float scale,
			boolean replace) {
		this.name = name == null ? "" : name;
		this.underlays = underlays;
		this.overlays = overlays;
		this.area = area;
		this.material = material;
		this.scale = scale;
		this.replace = replace;
	}

	public String name() {
		return this.name;
	}

	public int[] underlays() {
		return this.underlays;
	}

	public int[] overlays() {
		return this.overlays;
	}

	public int[] area() {
		return this.area;
	}

	public int material() {
		return this.material;
	}

	public float scale() {
		return this.scale;
	}

	public boolean replace() {
		return this.replace;
	}

	/** True when this entry states at least one tile predicate. */
	public boolean hasPredicate() {
		return this.area != null || this.underlays != null || this.overlays != null;
	}

	public boolean matches(int x, int z, int underlayId, int overlayId) {
		if (this.area != null && !(x >= this.area[0] && z >= this.area[1] && x <= this.area[2] && z <= this.area[3])) {
			return false;
		}
		if (this.underlays != null && !contains(this.underlays, underlayId)) {
			return false;
		}
		if (this.overlays != null && !contains(this.overlays, overlayId)) {
			return false;
		}
		return hasPredicate();
	}

	private static boolean contains(int[] ids, int id) {
		for (int i = 0; i < ids.length; i++) {
			if (ids[i] == id) {
				return true;
			}
		}
		return false;
	}
}
