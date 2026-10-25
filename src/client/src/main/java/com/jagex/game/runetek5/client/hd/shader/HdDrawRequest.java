package com.jagex.game.runetek5.client.hd.shader;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.client.hd.ground.HdGroundTextures;
import com.jagex.game.runetek5.client.hd.ground.HdGroundWater;
import com.jagex.game.runetek5.client.hd.light.HdEnvironmentLight;
import com.jagex.game.runetek5.client.hd.light.HdLightGate;
import com.jagex.game.runetek5.client.hd.sway.HdSwayGate;

/**
 * Everything the 592HD lighting gate needs for one model primitive.
 *
 * <p>Gathered once at the draw seam and evaluated once by
 * {@link HdLightingGate}: settings, the primitive's own state, and the GL state
 * the shader would have to reproduce. {@link #current} is the single place that
 * reads {@link HdSettings}, so the renderer never has to know which file or
 * which row a value came from.
 *
 * <p>The flags are not constructor arguments. They are named rows in
 * {@link HdDrawRows}, so a caller states which row it means and cannot put one
 * flag in another's slot: the scene's rows are resolved by {@link #current} from
 * the saved settings and the four gates, and the primitive's own rows are built
 * by the seam. What is left as arguments is genuinely positional-free data - the
 * texture unit, the combine mode and the scene's classification of the model.
 */
public final class HdDrawRequest {

	private final HdDrawRows rows;

	private final int textureUnit;

	private final int textureCombine;

	/**
	 * The scene's own classification of this model: {@code HdVegetation.NONE}
	 * for everything it did not mark, which is every stock draw. Carried rather
	 * than inferred, because the shader cannot tell a plant from a wall.
	 */
	private final int vegetation;

	/**
	 * The water standing over this draw's tile. {@link HdGroundWater#DRY} for
	 * everything that is not terrain, which is every model and every dry tile, so
	 * the caustics term is zero for them without a second flag.
	 */
	private final HdGroundWater water;

	/**
	 * The material's stock texture-effect slot, or -1 for a draw with no
	 * material. The reflections row reads it to decide whether this surface is
	 * one the client already considers reflective; it is data about the draw,
	 * not a flag (see {@code HdReflection}).
	 */
	private final int materialEffectSlot;

	/** The ground draw's atlas state; {@link HdGroundTextures#OFF} for a model draw. */
	private final HdGroundTextures groundTextures;

	/**
	 * One draw.
	 *
	 * @param rows           the rows this draw asks for, by name
	 * @param textureUnit    the unit the material texture is bound to
	 * @param textureCombine the combine mode the material set for it
	 * @param vegetation     the scene's classification of the model
	 */
	public HdDrawRequest(HdDrawRows rows, int textureUnit, int textureCombine, int vegetation) {
		this(rows, textureUnit, textureCombine, vegetation, HdGroundWater.DRY);
	}

	/**
	 * The same draw with the water standing over its tile.
	 *
	 * <p>Terrain is the only draw with water over it, and its depth is a per-tile
	 * value rather than a row: the caustics row says the settings want the
	 * pattern, this says whether there is water here to pattern. A model leaves
	 * it {@link HdGroundWater#DRY}.
	 */
	public HdDrawRequest(HdDrawRows rows, int textureUnit, int textureCombine, int vegetation,
			HdGroundWater water) {
		this(rows, textureUnit, textureCombine, vegetation, water, -1, HdGroundTextures.OFF);
	}

	private HdDrawRequest(HdDrawRows rows, int textureUnit, int textureCombine, int vegetation,
			HdGroundWater water, int materialEffectSlot, HdGroundTextures groundTextures) {
		this.rows = rows == null ? HdDrawRows.none() : rows;
		this.textureUnit = textureUnit;
		this.textureCombine = textureCombine;
		this.vegetation = vegetation;
		this.water = water == null ? HdGroundWater.DRY : water;
		this.materialEffectSlot = materialEffectSlot;
		this.groundTextures = groundTextures == null ? HdGroundTextures.OFF : groundTextures;
	}

	/** One draw of a model the scene did not classify: {@code HdVegetation.NONE}. */
	public HdDrawRequest(HdDrawRows rows, int textureUnit, int textureCombine) {
		this(rows, textureUnit, textureCombine, 0);
	}

	/** Builds a request from the live settings and the toolkit's draw state. */
	public static HdDrawRequest current(HdDrawRows primitive, int textureUnit, int textureCombine) {
		return current(primitive, textureUnit, textureCombine, 0);
	}

	/**
	 * The same, with the scene's classification of the model being drawn.
	 *
	 * <p>The scene's rows are resolved here and merged under the primitive's, so
	 * both seams state only what they actually know: this model's normals,
	 * material and fog, and which kind of model it is.
	 */
	public static HdDrawRequest current(HdDrawRows primitive, int textureUnit, int textureCombine,
			int vegetation) {
		HdDrawRows rows = HdDrawRows.none()
			.with(HdDrawRows.Row.HD, HdSettings.isEnabled())
			.with(HdDrawRows.Row.PER_PIXEL_LIGHTING, HdSettings.value(HdFeature.PER_PIXEL_LIGHTING) != 0)
			.with(HdDrawRows.Row.POINT_LIGHTS, HdSettings.value(HdFeature.POINT_LIGHTS) != 0)
			.with(HdDrawRows.Row.CARRIED_LIGHT, HdLightGate.carriedRequested())
			.with(HdDrawRows.Row.ROOM_LIGHT, HdLightGate.roomRequested())
			.with(HdDrawRows.Row.ENVIRONMENT_LIGHT, HdEnvironmentLight.requested())
			.with(HdDrawRows.Row.FOLIAGE_SWAY, HdSwayGate.foliageRequested())
			.with(HdDrawRows.Row.GRASS_DISPLACEMENT, HdSwayGate.grassRequested())
			.with(HdDrawRows.Row.CAUSTICS, HdSettings.value(HdFeature.CAUSTICS) != 0)
			.with(HdDrawRows.Row.REFLECTIONS, HdSettings.value(HdFeature.REFLECTIONS) != 0)
			.with(HdDrawRows.Row.WORLD_TEXTURES, HdSettings.value(HdFeature.WORLD_TEXTURES) != 0)
			.with(HdDrawRows.Row.MATERIAL_MAPS, HdSettings.value(HdFeature.MATERIAL_MAPS) != 0)
			.with(HdDrawRows.Row.TERRAIN_BLEND, HdSettings.value(HdFeature.TERRAIN_BLEND) != 0)
			.with(primitive);
		return new HdDrawRequest(rows, textureUnit, textureCombine, vegetation);
	}

	/** The same draw with rows turned on, by name. */
	public HdDrawRequest with(HdDrawRows.Row... rows) {
		return new HdDrawRequest(this.rows.with(rows), this.textureUnit, this.textureCombine, this.vegetation,
			this.water, this.materialEffectSlot, this.groundTextures);
	}

	/** The same draw with rows turned off, by name. */
	public HdDrawRequest without(HdDrawRows.Row... rows) {
		return new HdDrawRequest(this.rows.without(rows), this.textureUnit, this.textureCombine, this.vegetation,
			this.water, this.materialEffectSlot, this.groundTextures);
	}

	public HdDrawRequest withTextureUnit(int textureUnit) {
		return new HdDrawRequest(this.rows, textureUnit, this.textureCombine, this.vegetation, this.water,
			this.materialEffectSlot, this.groundTextures);
	}

	public HdDrawRequest withTextureCombine(int textureCombine) {
		return new HdDrawRequest(this.rows, this.textureUnit, textureCombine, this.vegetation, this.water,
			this.materialEffectSlot, this.groundTextures);
	}

	/** The same draw with the water standing over its tile; terrain's only setter. */
	public HdDrawRequest withWater(HdGroundWater water) {
		return new HdDrawRequest(this.rows, this.textureUnit, this.textureCombine, this.vegetation, water,
			this.materialEffectSlot, this.groundTextures);
	}

	/** The same draw with the material's stock texture-effect slot; the model seam's setter. */
	public HdDrawRequest withMaterialEffectSlot(int materialEffectSlot) {
		return new HdDrawRequest(this.rows, this.textureUnit, this.textureCombine, this.vegetation, this.water,
			materialEffectSlot, this.groundTextures);
	}

	/** The named rows this draw asks for. */
	public HdDrawRows rows() {
		return this.rows;
	}

	public boolean hdEnabled() {
		return this.rows.has(HdDrawRows.Row.HD);
	}

	public boolean perPixelLighting() {
		return this.rows.has(HdDrawRows.Row.PER_PIXEL_LIGHTING);
	}

	public boolean pointLights() {
		return this.rows.has(HdDrawRows.Row.POINT_LIGHTS);
	}

	public boolean hasNormals() {
		return this.rows.has(HdDrawRows.Row.NORMALS);
	}

	public boolean textured() {
		return this.rows.has(HdDrawRows.Row.TEXTURE);
	}

	public boolean materialEffectActive() {
		return this.rows.has(HdDrawRows.Row.MATERIAL_EFFECT);
	}

	public boolean fogEnabled() {
		return this.rows.has(HdDrawRows.Row.FOG);
	}

	public int textureUnit() {
		return this.textureUnit;
	}

	public int textureCombine() {
		return this.textureCombine;
	}

	/** True when this draw includes the carried light's term. */
	public boolean carriedLight() {
		return this.rows.has(HdDrawRows.Row.CARRIED_LIGHT);
	}

	/** True when this draw includes the room light's term. */
	public boolean roomLight() {
		return this.rows.has(HdDrawRows.Row.ROOM_LIGHT);
	}

	/** True when this draw's ambient takes the environment's sky colour. */
	public boolean environmentLighting() {
		return this.rows.has(HdDrawRows.Row.ENVIRONMENT_LIGHT);
	}

	/** True when this draw includes the foliage-sway row's displacement. */
	public boolean foliageSway() {
		return this.rows.has(HdDrawRows.Row.FOLIAGE_SWAY);
	}

	/** True when this draw includes the grass-displacement row's push. */
	public boolean grassDisplacement() {
		return this.rows.has(HdDrawRows.Row.GRASS_DISPLACEMENT);
	}

	/** The scene's classification of this draw's model; {@code HdVegetation.NONE} by default. */
	public int vegetation() {
		return this.vegetation;
	}

	/** True when this draw's settings ask for the caustics pattern. */
	public boolean caustics() {
		return this.rows.has(HdDrawRows.Row.CAUSTICS);
	}

	/** The water standing over this draw's tile; {@link HdGroundWater#DRY} off terrain. */
	public HdGroundWater water() {
		return this.water;
	}

	/** True when this draw's settings ask for the environment reflection. */
	public boolean reflections() {
		return this.rows.has(HdDrawRows.Row.REFLECTIONS);
	}

	/** The material's stock texture-effect slot, or -1 when this draw has no material. */
	public int materialEffectSlot() {
		return this.materialEffectSlot;
	}

	/** True when this draw samples the world-texture page. */
	public boolean worldTextures() {
		return this.rows.has(HdDrawRows.Row.WORLD_TEXTURES);
	}

	/** True when this draw resolves material ids through the material map. */
	public boolean materialMaps() {
		return this.rows.has(HdDrawRows.Row.MATERIAL_MAPS);
	}

	/** True when this draw is told it may mix a tile's two materials. */
	public boolean terrainBlend() {
		return this.rows.has(HdDrawRows.Row.TERRAIN_BLEND);
	}

	/** The ground draw's atlas state; {@link HdGroundTextures#OFF} for a model. */
	public HdGroundTextures groundTextures() {
		return this.groundTextures;
	}

	/** The same draw with its ground-texture state; the terrain seam's setter. */
	public HdDrawRequest withGroundTextures(HdGroundTextures textures) {
		return new HdDrawRequest(this.rows, this.textureUnit, this.textureCombine, this.vegetation, this.water,
			this.materialEffectSlot, textures);
	}
}
