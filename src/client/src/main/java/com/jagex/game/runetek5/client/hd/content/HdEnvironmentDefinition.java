package com.jagex.game.runetek5.client.hd.content;

/**
 * One {@code environments.json} entry: per-area sun, ambient, fog, sky, colour
 * grading and water tint.
 *
 * <p>Absent fields are null / NaN rather than zero, because zero is a real
 * value here: "no fog colour" means keep the game's own zone fog, while black
 * is a colour. The accessors therefore return boxed values for the colours and
 * {@link Float#NaN} for the unset numbers, and the consumer substitutes its
 * own default exactly where upstream does.
 *
 * <p>{@link #contains(int, int, int)} is upstream's area rule: a level list
 * must include the level, then a region list <em>or</em> a bounds list must
 * hold the tile; an entry with neither list is the default. Note the "or" - a
 * region match is enough even when bounds exist.
 */
public final class HdEnvironmentDefinition {

	/** The region a tile belongs to, in the client's {@code (x >> 6) << 8 | z >> 6} form. */
	public static final int regionOf(int tileX, int tileZ) {
		return ((tileX >> 6) << 8) | (tileZ >> 6);
	}

	String name;

	String source;

	int[] regions;

	int[][] bounds;

	int[] levels;

	/** Null when unset; degrees, 0 level and 90 straight down. */
	Float sunPitch;

	Float sunYaw;

	Integer sunColour;

	float sunIntensity = 1.0F;

	Integer ambientColour;

	float ambientIntensity = 1.0F;

	Integer fogColour;

	/** Exponential fog depth; NaN keeps the default environment's, else 6.5. */
	float fogDepth = Float.NaN;

	/** Fog end in tiles from the camera; NaN keeps the game's own. */
	float fogEnd = Float.NaN;

	Integer skyTop;

	Integer skyHorizon;

	float groundFogStart;

	float groundFogEnd;

	float groundFogOpacity;

	String lut;

	Integer waterTint;

	/** How much of the game's water colour the tint replaces; NaN means 0.45. */
	float waterStrength = Float.NaN;

	float waterWaves = 1.0F;

	float waterSpeed = 1.0F;

	float waterSize = 1.0F;

	float waterGlint = 1.0F;

	HdEnvironmentDefinition() {
	}

	public String name() {
		return this.name;
	}

	/** The generated area's name, or null for a hand-written entry. */
	public String source() {
		return this.source;
	}

	public int[] regions() {
		return this.regions;
	}

	public int[][] bounds() {
		return this.bounds;
	}

	public int[] levels() {
		return this.levels;
	}

	public Float sunPitch() {
		return this.sunPitch;
	}

	public Float sunYaw() {
		return this.sunYaw;
	}

	public Integer sunColour() {
		return this.sunColour;
	}

	public float sunIntensity() {
		return this.sunIntensity;
	}

	public Integer ambientColour() {
		return this.ambientColour;
	}

	public float ambientIntensity() {
		return this.ambientIntensity;
	}

	public Integer fogColour() {
		return this.fogColour;
	}

	public float fogDepth() {
		return this.fogDepth;
	}

	public float fogEnd() {
		return this.fogEnd;
	}

	public Integer skyTop() {
		return this.skyTop;
	}

	public Integer skyHorizon() {
		return this.skyHorizon;
	}

	public float groundFogStart() {
		return this.groundFogStart;
	}

	public float groundFogEnd() {
		return this.groundFogEnd;
	}

	public float groundFogOpacity() {
		return this.groundFogOpacity;
	}

	public String lut() {
		return this.lut;
	}

	public Integer waterTint() {
		return this.waterTint;
	}

	public float waterStrength() {
		return this.waterStrength;
	}

	public float waterWaves() {
		return this.waterWaves;
	}

	public float waterSpeed() {
		return this.waterSpeed;
	}

	public float waterSize() {
		return this.waterSize;
	}

	public float waterGlint() {
		return this.waterGlint;
	}

	/** True when this entry has no area and is the file's default. */
	public boolean isDefault() {
		return this.regions == null && this.bounds == null;
	}

	/** Upstream's {@code contains}: level list, then region or bounds. */
	public boolean contains(int tileX, int tileZ, int level) {
		if (this.levels != null && !contains(this.levels, level)) {
			return false;
		}
		if (this.regions == null && this.bounds == null) {
			return true;
		}
		if (this.regions != null && contains(this.regions, regionOf(tileX, tileZ))) {
			return true;
		}
		if (this.bounds != null) {
			for (int i = 0; i < this.bounds.length; i++) {
				int[] bound = this.bounds[i];
				int minX = Math.min(bound[0], bound[2]);
				int maxX = Math.max(bound[0], bound[2]);
				int minZ = Math.min(bound[1], bound[3]);
				int maxZ = Math.max(bound[1], bound[3]);
				if (tileX >= minX && tileX <= maxX && tileZ >= minZ && tileZ <= maxZ) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean contains(int[] values, int value) {
		for (int i = 0; i < values.length; i++) {
			if (values[i] == value) {
				return true;
			}
		}
		return false;
	}
}
