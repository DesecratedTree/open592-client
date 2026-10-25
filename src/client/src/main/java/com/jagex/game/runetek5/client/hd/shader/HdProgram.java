package com.jagex.game.runetek5.client.hd.shader;

import java.util.HashMap;
import java.util.Map;

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
}
