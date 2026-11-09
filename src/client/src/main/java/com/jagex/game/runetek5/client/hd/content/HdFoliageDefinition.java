package com.jagex.game.runetek5.client.hd.content;

/**
 * One {@code foliage.json} entry: objects that sway, and how hard.
 *
 * <p>{@code strength} scales the sway amplitude and {@code frequency} its
 * phase rate; both default to 1, so an entry that only names its objects
 * sways exactly like the base row. This is the data the scene tags a draw
 * with; it does not replace {@code HdVegetation}'s shape rule, which still
 * decides that the geometry is a plant rather than a wall.
 */
public final class HdFoliageDefinition {

	final String name;

	final int[] objects;

	final float strength;

	final float frequency;

	HdFoliageDefinition(String name, int[] objects, float strength, float frequency) {
		this.name = name == null ? "" : name;
		this.objects = objects;
		this.strength = strength;
		this.frequency = frequency;
	}

	public String name() {
		return this.name;
	}

	public int[] objects() {
		return this.objects;
	}

	public float strength() {
		return this.strength;
	}

	public float frequency() {
		return this.frequency;
	}

	public boolean contains(int object) {
		for (int i = 0; i < this.objects.length; i++) {
			if (this.objects[i] == object) {
				return true;
			}
		}
		return false;
	}
}
