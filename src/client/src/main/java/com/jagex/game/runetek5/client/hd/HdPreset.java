package com.jagex.game.runetek5.client.hd;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * The 592HD quality presets, ported from 634HD's {@code HdSettings.applyPreset}.
 *
 * <p>Each preset is an explicit table of feature values rather than a formula:
 * a preset is a product decision, and a table is the only form that can be
 * reviewed and tested line by line. Presets never touch stock state directly;
 * {@link HdSettings#applyPreset} applies the READY rows through
 * {@link HdGraphicsBackend} and stores the rest for the pipeline.
 *
 * <p>Rows a preset does not list fall back to {@link HdFeature#defaultValue()}.
 */
public enum HdPreset {

	/** 592HD off; the stock client renders exactly as it does today. */
	OFF("Off", false),

	LOW("Low", true),

	MEDIUM("Medium", true),

	HIGH("High", true),

	ULTRA("Ultra", true),

	/** Any hand-tweaked row; not reachable from the preset buttons. */
	CUSTOM("Custom", true);

	private final String label;

	private final boolean enabled;

	private final Map<HdFeature, Integer> values;

	private HdPreset(String label, boolean enabled) {
		this.label = label;
		this.enabled = enabled;
		this.values = new EnumMap<HdFeature, Integer>(HdFeature.class);
	}

	static {
		// No fog row here: it lives in the per-pixel program, which Low leaves off,
		// so listing it would only claim an effect the tier cannot show.
		LOW.set(HdFeature.TEXTURES, 1)
			.set(HdFeature.GROUND_BLENDING, 0)
			.set(HdFeature.SHADOWS, 1)
			.set(HdFeature.LIGHTING_DETAIL, 0)
			.set(HdFeature.BLOOM, 0)
			.set(HdFeature.ANTI_ALIASING, 0)
			.set(HdFeature.MIPMAPS, 1)
			.set(HdFeature.RENDER_DISTANCE, 1);

		MEDIUM.set(HdFeature.FOG, 1)
			.set(HdFeature.SKY, 1)
			.set(HdFeature.TEXTURES, 1)
			.set(HdFeature.GROUND_BLENDING, 1)
			.set(HdFeature.SHADOWS, 2)
			.set(HdFeature.LIGHTING_DETAIL, 1)
			.set(HdFeature.PER_PIXEL_LIGHTING, 1)
			.set(HdFeature.POINT_LIGHTS, 1)
			.set(HdFeature.ENVIRONMENT_LIGHTING, 1)
			.set(HdFeature.DISTANCE_FADE, 1)
			.set(HdFeature.TONEMAP, 1)
			.set(HdFeature.MIPMAPS, 1)
			.set(HdFeature.BLOOM, 0)
			.set(HdFeature.ANTI_ALIASING, 0)
			.set(HdFeature.RENDER_DISTANCE, 2);

		HIGH.set(HdFeature.FOG, 1)
			.set(HdFeature.SKY, 1)
			.set(HdFeature.TEXTURES, 1)
			.set(HdFeature.GROUND_BLENDING, 1)
			.set(HdFeature.SHADOWS, 2)
			.set(HdFeature.SHADOW_MAP, 2)
			.set(HdFeature.LIGHTING_DETAIL, 1)
			.set(HdFeature.PER_PIXEL_LIGHTING, 1)
			.set(HdFeature.POINT_LIGHTS, 1)
			.set(HdFeature.CARRIED_LIGHT, 1)
			.set(HdFeature.ROOM_LIGHT, 1)
			.set(HdFeature.AMBIENT_OCCLUSION, 1)
			.set(HdFeature.CUTOUT_SHADOWS, 1)
			.set(HdFeature.ENVIRONMENT_LIGHTING, 1)
			.set(HdFeature.DISTANCE_FADE, 1)
			.set(HdFeature.WORLD_TEXTURES, 1)
			.set(HdFeature.MATERIAL_MAPS, 1)
			.set(HdFeature.WATER_HD, 1)
			.set(HdFeature.REFLECTIONS, 1)
			.set(HdFeature.CAUSTICS, 1)
			.set(HdFeature.FOLIAGE_SWAY, 1)
			.set(HdFeature.GRASS_DISPLACEMENT, 1)
			.set(HdFeature.BLOOM, 2)
			.set(HdFeature.TONEMAP, 1)
			.set(HdFeature.COLOUR_GRADING, 1)
			.set(HdFeature.FXAA, 1)
			.set(HdFeature.MIPMAPS, 1)
			.set(HdFeature.ANISOTROPIC, 2)
			.set(HdFeature.DITHER, 1)
			.set(HdFeature.SOFT_PARTICLES, 1)
			.set(HdFeature.ANTI_ALIASING, 1)
			.set(HdFeature.RENDER_DISTANCE, 2);

		ULTRA.set(HdFeature.FOG, 1)
			.set(HdFeature.SKY, 1)
			.set(HdFeature.TEXTURES, 1)
			.set(HdFeature.GROUND_BLENDING, 1)
			.set(HdFeature.SHADOWS, 2)
			.set(HdFeature.SHADOW_MAP, 3)
			.set(HdFeature.LIGHTING_DETAIL, 1)
			.set(HdFeature.PER_PIXEL_LIGHTING, 1)
			.set(HdFeature.POINT_LIGHTS, 1)
			.set(HdFeature.CARRIED_LIGHT, 1)
			.set(HdFeature.ROOM_LIGHT, 1)
			.set(HdFeature.AMBIENT_OCCLUSION, 1)
			.set(HdFeature.CUTOUT_SHADOWS, 1)
			.set(HdFeature.ENVIRONMENT_LIGHTING, 1)
			.set(HdFeature.LIGHTING_MODEL, 1)
			.set(HdFeature.TERRAIN_BLEND, 1)
			.set(HdFeature.DISTANCE_FADE, 1)
			.set(HdFeature.WORLD_TEXTURES, 1)
			.set(HdFeature.MATERIAL_MAPS, 1)
			.set(HdFeature.WATER_HD, 1)
			.set(HdFeature.REFLECTIONS, 1)
			.set(HdFeature.CAUSTICS, 1)
			.set(HdFeature.FOLIAGE_SWAY, 1)
			.set(HdFeature.GRASS_DISPLACEMENT, 1)
			.set(HdFeature.BLOOM, 2)
			.set(HdFeature.TONEMAP, 1)
			.set(HdFeature.COLOUR_GRADING, 1)
			.set(HdFeature.FXAA, 1)
			.set(HdFeature.MIPMAPS, 1)
			.set(HdFeature.ANISOTROPIC, 4)
			.set(HdFeature.DITHER, 1)
			.set(HdFeature.SOFT_PARTICLES, 1)
			.set(HdFeature.ANTI_ALIASING, 2)
			.set(HdFeature.RENDER_DISTANCE, 3);
	}

	private HdPreset set(HdFeature feature, int value) {
		this.values.put(feature, value);
		return this;
	}

	public String label() {
		return this.label;
	}

	/** False only for {@link #OFF}: the HD pipeline itself is disabled. */
	public boolean enabled() {
		return this.enabled;
	}

	/** The preset row value, or the feature's default when not listed. */
	public int value(HdFeature feature) {
		Integer value = this.values.get(feature);
		return value == null ? feature.defaultValue() : value.intValue();
	}

	/** The preset's explicit assignments; rows absent here use the default. */
	public Map<HdFeature, Integer> assignments() {
		return Collections.unmodifiableMap(this.values);
	}

	/** The presets offered as buttons, in order. */
	public static HdPreset[] buttons() {
		return new HdPreset[] { OFF, LOW, MEDIUM, HIGH, ULTRA };
	}

	public static HdPreset buttonAt(int index) {
		HdPreset[] buttons = buttons();
		if (index < 0 || index >= buttons.length) {
			return null;
		}
		return buttons[index];
	}

	public static HdPreset forKey(String key) {
		for (HdPreset preset : values()) {
			if (preset.name().equalsIgnoreCase(key)) {
				return preset;
			}
		}
		return null;
	}

	/** The shipped default: HD on at Medium, as approved for this port. */
	public static HdPreset defaultPreset() {
		return MEDIUM;
	}
}
