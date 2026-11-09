package com.jagex.game.runetek5.client.hd.content;

/**
 * One {@code model_overrides.json} entry: 634HD behaviour for particular
 * models, objects, face colours or areas.
 *
 * <p>The nullable arrays mean "no constraint": an entry listing only an area
 * applies to every object in it, and one listing only a model applies wherever
 * that model is drawn. {@link #material} is -1 when the entry only changes
 * wind or shadow behaviour.
 *
 * <p>{@link #wind} and {@link #castShadows} carry upstream's defaults (false
 * and true) rather than {@code null}, because those are the values the
 * renderer would use in the entry's absence; an entry that says nothing about
 * shadows keeps them.
 */
public final class HdModelOverride {

	final String name;

	final int[] objects;

	final int[] models;

	final int[] faceColours;

	final int[] area;

	final int material;

	final boolean wind;

	final boolean castShadows;

	final boolean worldUv;

	final boolean replace;

	final float uvScale;

	HdModelOverride(String name, int[] objects, int[] models, int[] faceColours, int[] area, int material,
			boolean wind, boolean castShadows, boolean worldUv, boolean replace, float uvScale) {
		this.name = name == null ? "" : name;
		this.objects = objects;
		this.models = models;
		this.faceColours = faceColours;
		this.area = area;
		this.material = material;
		this.wind = wind;
		this.castShadows = castShadows;
		this.worldUv = worldUv;
		this.replace = replace;
		this.uvScale = uvScale;
	}

	public String name() {
		return this.name;
	}

	public int[] objects() {
		return this.objects;
	}

	public int[] models() {
		return this.models;
	}

	public int[] faceColours() {
		return this.faceColours;
	}

	/** Absolute tile bounds {@code [x1, z1, x2, z2]}, or null for no area. */
	public int[] area() {
		return this.area;
	}

	public int material() {
		return this.material;
	}

	public boolean hasMaterial() {
		return this.material >= 0;
	}

	public boolean wind() {
		return this.wind;
	}

	public boolean castShadows() {
		return this.castShadows;
	}

	/** True for box mapping in world space, false for the model's own space. */
	public boolean worldUv() {
		return this.worldUv;
	}

	public boolean replace() {
		return this.replace;
	}

	public float uvScale() {
		return this.uvScale;
	}

	public boolean namesObject(int object) {
		return contains(this.objects, object);
	}

	public boolean namesModel(int model) {
		return contains(this.models, model);
	}

	public boolean namesFaceColour(int colour) {
		return contains(this.faceColours, colour);
	}

	/** True when this entry's area holds the absolute tile, or it has no area. */
	public boolean covers(int x, int z) {
		if (this.area == null) {
			return true;
		}
		return x >= this.area[0] && z >= this.area[1] && x <= this.area[2] && z <= this.area[3];
	}

	/** True when this entry constrains something other than its area. */
	public boolean namesAnything() {
		return this.objects != null || this.models != null || this.faceColours != null;
	}

	private static boolean contains(int[] ids, int id) {
		if (ids == null) {
			return false;
		}
		for (int i = 0; i < ids.length; i++) {
			if (ids[i] == id) {
				return true;
			}
		}
		return false;
	}
}
