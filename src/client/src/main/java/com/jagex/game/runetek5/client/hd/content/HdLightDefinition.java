package com.jagex.game.runetek5.client.hd.content;

/**
 * One {@code lights.json} entry: a coloured light attached to a 634 object,
 * NPC, spot animation or projectile id.
 *
 * <p>The file is in 634HD's units - radius in tiles and offset in scene units
 * with 512 per tile - while this client's renderer works in 128 units per tile
 * (the unit camera, entity and loc positions already use). The conversion is
 * stated here, once, in {@link #radius()} and the offset getters, so no caller
 * can mix the two.
 *
 * <p>'First match wins' is the file's rule: an id listed by two entries lights
 * with the earlier one, which is what makes a hand-written override above a
 * generated block effective.
 */
public final class HdLightDefinition {

	public static final int FLICKER_NONE = 0;

	/** A fast, irregular flame flicker. */
	public static final int FLICKER = 1;

	/** A slow breathe, for magic and lava. */
	public static final int PULSE = 2;

	/** This client's scene units per tile; 634HD's files use 512. */
	public static final float CLIENT_UNITS_PER_TILE = 128.0F;

	/** The unit the file's offset is written in. */
	public static final float FILE_UNITS_PER_TILE = 512.0F;

	final String name;

	final int[] objects;

	final int[] npcs;

	final int[] spotAnimations;

	final int[] projectiles;

	final int rgb;

	final float radiusTiles;

	final float intensity;

	final float offsetX;

	final float offsetY;

	final float offsetZ;

	final int flicker;

	HdLightDefinition(String name, int[] objects, int[] npcs, int[] spotAnimations, int[] projectiles,
			int rgb, float radiusTiles, float intensity, float offsetX, float offsetY, float offsetZ,
			int flicker) {
		this.name = name == null ? "" : name;
		this.objects = objects;
		this.npcs = npcs;
		this.spotAnimations = spotAnimations;
		this.projectiles = projectiles;
		this.rgb = rgb & 0xFFFFFF;
		this.radiusTiles = radiusTiles;
		this.intensity = intensity;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.flicker = flicker;
	}

	public String name() {
		return this.name;
	}

	public int[] objects() {
		return this.objects;
	}

	public int[] npcs() {
		return this.npcs;
	}

	public int[] spotAnimations() {
		return this.spotAnimations;
	}

	public int[] projectiles() {
		return this.projectiles;
	}

	public int rgb() {
		return this.rgb;
	}

	public float red() {
		return (this.rgb >> 16 & 0xFF) / 255.0F;
	}

	public float green() {
		return (this.rgb >> 8 & 0xFF) / 255.0F;
	}

	public float blue() {
		return (this.rgb & 0xFF) / 255.0F;
	}

	/** Reach in scene units: the file's tiles times this client's 128 per tile. */
	public float radius() {
		return this.radiusTiles * CLIENT_UNITS_PER_TILE;
	}

	public float radiusTiles() {
		return this.radiusTiles;
	}

	public float intensity() {
		return this.intensity;
	}

	/** The light's x offset in this client's scene units. */
	public float offsetX() {
		return this.offsetX * CLIENT_UNITS_PER_TILE / FILE_UNITS_PER_TILE;
	}

	/** The light's height offset in this client's scene units; negative is up. */
	public float offsetY() {
		return this.offsetY * CLIENT_UNITS_PER_TILE / FILE_UNITS_PER_TILE;
	}

	public float offsetZ() {
		return this.offsetZ * CLIENT_UNITS_PER_TILE / FILE_UNITS_PER_TILE;
	}

	public int flicker() {
		return this.flicker;
	}

	public boolean lightsObject(int id) {
		return contains(this.objects, id);
	}

	public boolean lightsNpc(int id) {
		return contains(this.npcs, id);
	}

	public boolean lightsSpotAnimation(int id) {
		return contains(this.spotAnimations, id);
	}

	public boolean lightsProjectile(int id) {
		return contains(this.projectiles, id);
	}

	/** True when this entry names no id at all and can never light anything. */
	public boolean isEmpty() {
		return this.objects.length == 0 && this.npcs.length == 0 && this.spotAnimations.length == 0
			&& this.projectiles.length == 0;
	}

	private static boolean contains(int[] ids, int id) {
		if (ids == null || id < 0) {
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
