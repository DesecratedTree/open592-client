package com.jagex.game.runetek5.client.hd.ground;

/**
 * One ground draw's texture state: which material the layer is drawing, how
 * large a tile is, how long one texture repeat is, and how much of the tile's
 * second material the noise mask may reach.
 *
 * <p>Four numbers, all of them things the stock draw already knows. The layer
 * knows its material ({@code GlGroundLayer.anInt1647}) and its texture scale;
 * the mesh knows how many vertices a tile has; the terrain-blend rows decide the
 * strength. Nothing here is measured or guessed, which is what lets the shader's
 * first sample be the same texel the stock texture matrix would have shown.
 *
 * <p>{@link #OFF} draws no atlas texture and no blend: it is the state the seam
 * returns when the rows are off or the material never reached the atlas page,
 * and the shader then takes the stock {@code uTexture} path unchanged.
 */
public final class HdGroundTextures {

	/** The identity: no material, no repeat, no blend. */
	public static final HdGroundTextures OFF = new HdGroundTextures(-1, 0.0F, 0.0F, 0.0F);

	private final int material;

	private final float tileSize;

	private final float repeat;

	private final float blendStrength;

	private HdGroundTextures(int material, float tileSize, float repeat, float blendStrength) {
		this.material = material;
		this.tileSize = tileSize;
		this.repeat = repeat;
		this.blendStrength = blendStrength;
	}

	/**
	 * This draw's texture state.
	 *
	 * @param requested     the world-textures and material-maps rows for this draw
	 * @param material      the layer's stock material id, or -1 for an untextured layer
	 * @param textured      whether the layer actually binds a texture
	 * @param tileUnits     vertices per tile ({@code GlGround.anInt5261}); the
	 *                      mesh's vertices scale to a fixed scene size, so this
	 *                      gates the draw but does not size the tile
	 * @param textureScale  the layer's stock texture scale, in scene units per
	 *                      repeat: {@code GlGroundLayer} divides the texture
	 *                      matrix by it, and a 592 {@code FluType} defaults to
	 *                      128, one repeat per tile. The atlas must use the
	 *                      same number or its art is magnified by the tile size
	 *                      and a whole tile reads one texel - a flat colour.
	 * @param blend         the frame's terrain blend mask, {@link HdTerrainBlend#OFF} off
	 */
	public static HdGroundTextures of(boolean requested, int material, boolean textured, int tileUnits,
			float textureScale, HdTerrainBlend blend) {
		if (!requested || !textured || material < 0 || tileUnits <= 0 || textureScale <= 0.0F) {
			return OFF;
		}
		// A tile is always 128 scene units: GroundTileSpace scales the buffer by
		// 128 / tileUnits, so a mesh with four vertices per tile still covers the
		// same world distance as one with 128.
		float tileSize = GroundTileSpace.UNITS_PER_TILE;
		HdTerrainBlend mix = blend == null ? HdTerrainBlend.OFF : blend;
		// The stock draw scales texture coordinates by 1 / textureScale in the
		// texture matrix. Those coordinates are scene units (GlGround writes the
		// vertex's own x and z into the texcoord stream), so one repeat is
		// textureScale scene units - not a tile times that scale, which made the
		// page 128 repeats too wide and every tile a single texel.
		return new HdGroundTextures(material, tileSize, textureScale, mix.strength());
	}

	/** The material this layer draws, or -1 off. */
	public int material() {
		return this.material;
	}

	/** Scene units per tile. */
	public float tileSize() {
		return this.tileSize;
	}

	/** Scene units per texture repeat; the stock texture matrix's inverse scale. */
	public float repeat() {
		return this.repeat;
	}

	/** How much of a second material the mask may reach, 0..1. */
	public float blendStrength() {
		return this.blendStrength;
	}

	/** True when the shader should sample the atlas instead of the bound texture. */
	public boolean active() {
		return this.material >= 0 && this.tileSize > 0.0F && this.repeat > 0.0F;
	}

	@Override
	public String toString() {
		return "groundTextures=" + this.material + "@" + this.repeat + " blend=" + this.blendStrength;
	}
}
