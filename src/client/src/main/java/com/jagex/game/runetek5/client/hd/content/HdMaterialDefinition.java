package com.jagex.game.runetek5.client.hd.content;

/**
 * One {@code materials.json} entry: the extra art a cache texture draws with,
 * keyed by its texture id.
 *
 * <p>Ids below {@link #CUSTOM_MATERIALS} are the game's own textures and the
 * entry supplies maps beside the cache's albedo. Ids from
 * {@link #CUSTOM_MATERIALS} up are 634HD's own materials: their {@code albedo}
 * is the colour map and model/tile overrides put them on faces and tiles the
 * game leaves untextured.
 *
 * <p>Every numeric field carries the upstream default rather than zero, so a
 * terse entry - the common case in the shipped file - behaves exactly like the
 * full one. Colours are sRGB 0xRRGGBB.
 */
public final class HdMaterialDefinition {

	/** The first id that is 634HD's own material rather than a cache texture. */
	public static final int CUSTOM_MATERIALS = 10000;

	final int texture;

	final String name;

	final String albedo;

	final String normal;

	final String orm;

	final String height;

	final String emissive;

	final String flowMap;

	final float uvScrollU;

	final float uvScrollV;

	final float specular;

	final float emissiveStrength;

	final float parallax;

	final float flowStrength;

	final float flowDurationU;

	final float flowDurationV;

	final boolean unlit;

	final float lightRed;

	final float lightGreen;

	final float lightBlue;

	final Water water;

	HdMaterialDefinition(int texture, String name, String albedo, String normal, String orm, String height,
			String emissive, String flowMap, float uvScrollU, float uvScrollV, float specular,
			float emissiveStrength, float parallax, float flowStrength, float flowDurationU,
			float flowDurationV, boolean unlit, float lightRed, float lightGreen, float lightBlue, Water water) {
		this.texture = texture;
		this.name = name == null ? "" : name;
		this.albedo = albedo;
		this.normal = normal;
		this.orm = orm;
		this.height = height;
		this.emissive = emissive;
		this.flowMap = flowMap;
		this.uvScrollU = uvScrollU;
		this.uvScrollV = uvScrollV;
		this.specular = specular;
		this.emissiveStrength = emissiveStrength;
		this.parallax = parallax;
		this.flowStrength = flowStrength;
		this.flowDurationU = flowDurationU;
		this.flowDurationV = flowDurationV;
		this.unlit = unlit;
		this.lightRed = lightRed;
		this.lightGreen = lightGreen;
		this.lightBlue = lightBlue;
		this.water = water;
	}

	public int texture() {
		return this.texture;
	}

	public String name() {
		return this.name;
	}

	public String albedo() {
		return this.albedo;
	}

	public String normal() {
		return this.normal;
	}

	/** Red: ambient occlusion, green: roughness, blue: emissive mask. */
	public String orm() {
		return this.orm;
	}

	public String height() {
		return this.height;
	}

	public String emissive() {
		return this.emissive;
	}

	public String flowMap() {
		return this.flowMap;
	}

	public float uvScrollU() {
		return this.uvScrollU;
	}

	public float uvScrollV() {
		return this.uvScrollV;
	}

	public float specular() {
		return this.specular;
	}

	public float emissiveStrength() {
		return this.emissiveStrength;
	}

	public float parallax() {
		return this.parallax;
	}

	public float flowStrength() {
		return this.flowStrength;
	}

	public float flowDurationU() {
		return this.flowDurationU;
	}

	public float flowDurationV() {
		return this.flowDurationV;
	}

	public boolean unlit() {
		return this.unlit;
	}

	public float lightRed() {
		return this.lightRed;
	}

	public float lightGreen() {
		return this.lightGreen;
	}

	public float lightBlue() {
		return this.lightBlue;
	}

	/** True when this entry has a replacement albedo to draw. */
	public boolean hasAlbedo() {
		return this.albedo != null;
	}

	public boolean isCustom() {
		return this.texture >= CUSTOM_MATERIALS;
	}

	/** The water block, or null when this texture is not water. */
	public Water water() {
		return this.water;
	}

	/** A water texture's surface parameters; the defaults are the plain WATER type's. */
	public static final class Water {

		final int surface;

		final int foam;

		final int depth;

		final float opacity;

		final float specular;

		final float gloss;

		final float normalStrength;

		final float fresnel;

		final float duration;

		final boolean hasFoam;

		final boolean flat;

		Water(int surface, int foam, int depth, float opacity, float specular, float gloss,
				float normalStrength, float fresnel, float duration, boolean hasFoam, boolean flat) {
			this.surface = surface;
			this.foam = foam;
			this.depth = depth;
			this.opacity = opacity;
			this.specular = specular;
			this.gloss = gloss;
			this.normalStrength = normalStrength;
			this.fresnel = fresnel;
			this.duration = duration;
			this.hasFoam = hasFoam;
			this.flat = flat;
		}

		public int surface() {
			return this.surface;
		}

		public int foam() {
			return this.foam;
		}

		public int depth() {
			return this.depth;
		}

		public float opacity() {
			return this.opacity;
		}

		public float specular() {
			return this.specular;
		}

		public float gloss() {
			return this.gloss;
		}

		public float normalStrength() {
			return this.normalStrength;
		}

		public float fresnel() {
			return this.fresnel;
		}

		public float duration() {
			return this.duration;
		}

		public boolean hasFoam() {
			return this.hasFoam;
		}

		public boolean flat() {
			return this.flat;
		}
	}
}
