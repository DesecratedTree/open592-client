package com.jagex.game.runetek5.client.hd.shader;

import java.util.HashMap;
import java.util.Map;

import com.jagex.game.runetek5.client.hd.water.HdWaterSource;

/**
 * A linked GLSL program plus its uniform locations.
 *
 * <p>Locations are looked up once per name and cached: {@code glGetUniformLocation}
 * is a driver call, and the model path binds this program for every primitive.
 * A location of -1 (the uniform was optimised out, or the name is wrong) is
 * cached too, so a typo costs one lookup rather than one per draw.
 */
public final class HdProgram {

	private final HdShaderApi api;

	private final long handle;

	private final Map<String, Integer> locations = new HashMap<String, Integer>();

	private final int samplerLocation;

	private final int shadowSamplerLocation;

	private final int reflectionSamplerLocation;

	private final int worldAtlasLocation;

	private final int materialIdMapLocation;

	private final int tileMaterialsLocation;

	private final int materialNormalLocation;

	private final int materialOrmLocation;

	private final int materialEmissiveLocation;

	private final int materialHeightLocation;

	private final int waterNormalLocation;

	private final int waterFoamLocation;

	private final int waterFlowLocation;

	HdProgram(HdShaderApi api, long handle) {
		this.api = api;
		this.handle = handle;
		this.samplerLocation = handle == 0L ? -1 : api.uniformLocation(handle, HdShaderSource.UNIFORM_TEXTURE);
		this.shadowSamplerLocation = handle == 0L ? -1
			: api.uniformLocation(handle, HdShaderSource.UNIFORM_SHADOW_MAP);
		this.reflectionSamplerLocation = handle == 0L ? -1
			: api.uniformLocation(handle, HdShaderSource.UNIFORM_REFLECTION_CUBE);
		this.worldAtlasLocation = handle == 0L ? -1
			: api.uniformLocation(handle, HdShaderSource.UNIFORM_WORLD_ATLAS);
		this.materialIdMapLocation = handle == 0L ? -1
			: api.uniformLocation(handle, HdShaderSource.UNIFORM_MATERIAL_ID_MAP);
		this.tileMaterialsLocation = handle == 0L ? -1
			: api.uniformLocation(handle, HdShaderSource.UNIFORM_TILE_MATERIALS);
		this.materialNormalLocation = handle == 0L ? -1
			: api.uniformLocation(handle, HdShaderSource.UNIFORM_MATERIAL_NORMAL);
		this.materialOrmLocation = handle == 0L ? -1
			: api.uniformLocation(handle, HdShaderSource.UNIFORM_MATERIAL_ORM);
		this.materialEmissiveLocation = handle == 0L ? -1
			: api.uniformLocation(handle, HdShaderSource.UNIFORM_MATERIAL_EMISSIVE);
		this.materialHeightLocation = handle == 0L ? -1
			: api.uniformLocation(handle, HdShaderSource.UNIFORM_MATERIAL_HEIGHT);
		this.waterNormalLocation = handle == 0L ? -1
			: api.uniformLocation(handle, HdWaterSource.UNIFORM_WATER_NORMAL);
		this.waterFoamLocation = handle == 0L ? -1
			: api.uniformLocation(handle, HdWaterSource.UNIFORM_WATER_FOAM);
		this.waterFlowLocation = handle == 0L ? -1
			: api.uniformLocation(handle, HdWaterSource.UNIFORM_WATER_FLOW);
	}

	public long handle() {
		return this.handle;
	}

	public boolean usable() {
		return this.handle != 0L;
	}

	/** The cached location of {@code uniform}, or -1 when the program lacks it. */
	public int uniform(String uniform) {
		if (!usable()) {
			return -1;
		}
		Integer cached = this.locations.get(uniform);
		if (cached != null) {
			return cached.intValue();
		}
		int location = this.api.uniformLocation(this.handle, uniform);
		this.locations.put(uniform, Integer.valueOf(location));
		return location;
	}

	/**
	 * Binds this program's sampler to the shader's texture unit. The
	 * {@code uTexture} location is resolved once when the program is created,
	 * because a sampler unit never changes.
	 */
	public void bindSampler() {
		if (this.samplerLocation >= 0) {
			this.api.uniform1i(this.samplerLocation, HdShaderSource.SAMPLER_UNIT);
		}
	}

	/** Binds the shadow map to its own unit, once per program like the material sampler. */
	public void bindShadowSampler() {
		if (this.shadowSamplerLocation >= 0) {
			this.api.uniform1i(this.shadowSamplerLocation, HdShaderSource.SAMPLER_UNIT_SHADOW);
		}
	}

	/** Binds the environment cube to its own unit; only a reflecting draw asks. */
	public void bindReflectionSampler() {
		if (this.reflectionSamplerLocation >= 0) {
			this.api.uniform1i(this.reflectionSamplerLocation, HdShaderSource.SAMPLER_UNIT_REFLECTION);
		}
	}

	/**
	 * Binds 634HD's three water maps to their own units, once per program like
	 * every other sampler here. A water program owns the frame while it is
	 * bound, so the units cannot collide with the ground or material passes.
	 */
	public void bindWaterSamplers() {
		if (this.waterNormalLocation >= 0) {
			this.api.uniform1i(this.waterNormalLocation, HdWaterSource.WATER_NORMAL_UNIT);
		}
		if (this.waterFoamLocation >= 0) {
			this.api.uniform1i(this.waterFoamLocation, HdWaterSource.WATER_FOAM_UNIT);
		}
		if (this.waterFlowLocation >= 0) {
			this.api.uniform1i(this.waterFlowLocation, HdWaterSource.WATER_FLOW_UNIT);
		}
	}

	/**
	 * Binds the ground pass' three textures to their own units. Like the other
	 * samplers they are selected once per program: the units never change, only
	 * which texture is on them, and a unit left unpicked would collide with the
	 * material sampler's.
	 */
	public void bindGroundSamplers() {
		if (this.worldAtlasLocation >= 0) {
			this.api.uniform1i(this.worldAtlasLocation, HdShaderSource.SAMPLER_UNIT_WORLD_TEXTURES);
		}
		if (this.materialIdMapLocation >= 0) {
			this.api.uniform1i(this.materialIdMapLocation, HdShaderSource.SAMPLER_UNIT_MATERIAL_MAPS);
		}
		if (this.tileMaterialsLocation >= 0) {
			this.api.uniform1i(this.tileMaterialsLocation, HdShaderSource.SAMPLER_UNIT_TILE_MATERIALS);
		}
	}

	/**
	 * Binds the four material-map samplers to their own units, once per program
	 * like every other sampler here. The units never change; only which texture
	 * sits on them does, and the seam binds those per draw.
	 */
	public void bindMaterialSamplers() {
		if (this.materialNormalLocation >= 0) {
			this.api.uniform1i(this.materialNormalLocation, HdShaderSource.SAMPLER_UNIT_MATERIAL_NORMAL);
		}
		if (this.materialOrmLocation >= 0) {
			this.api.uniform1i(this.materialOrmLocation, HdShaderSource.SAMPLER_UNIT_MATERIAL_ORM);
		}
		if (this.materialEmissiveLocation >= 0) {
			this.api.uniform1i(this.materialEmissiveLocation, HdShaderSource.SAMPLER_UNIT_MATERIAL_EMISSIVE);
		}
		if (this.materialHeightLocation >= 0) {
			this.api.uniform1i(this.materialHeightLocation, HdShaderSource.SAMPLER_UNIT_MATERIAL_HEIGHT);
		}
	}
}
