package com.jagex.game.runetek5.client.hd.shader;

/**
 * The preconditions and rows one lighting draw asks for, as a named set rather
 * than a run of booleans.
 *
 * <p>{@link HdDrawRequest} used to take them positionally - fifteen booleans and
 * three ints in a fixed order - so a flag in the wrong slot compiled and simply
 * answered a different question. Each row now has a name, and a set is built by
 * naming the rows that are on: {@code none().with(Row.NORMALS).with(Row.FOG)}.
 *
 * <p>Two sources fill a set. The scene's rows come from the saved settings and
 * the four feature gates ({@link HdDrawRequest#current}); the primitive's own
 * rows - normals, a material texture, a material effect owning the draw, fog -
 * come from the state of the draw itself. A bitmask keeps both cheap: a set is
 * one {@code long}, so building one per primitive allocates nothing but the
 * wrapper.
 */
public final class HdDrawRows {

	/**
	 * One named precondition or row. A row can be on for a draw without being on
	 * for the frame: the settings say these are wanted, the gate still decides
	 * for this primitive.
	 */
	public enum Row {

		/** 592HD itself is enabled. */
		HD,

		/** The per-pixel lighting row is on. */
		PER_PIXEL_LIGHTING,

		/** The point-light row is on. */
		POINT_LIGHTS,

		/** The primitive has vertex normals, so there is something to light. */
		NORMALS,

		/** The primitive has a material texture. */
		TEXTURE,

		/** A material effect owns this draw; the lit opaque program must stay off. */
		MATERIAL_EFFECT,

		/** The draw is fogged; false means the shader drops the band. */
		FOG,

		/** The carried-light row contributes to this draw. */
		CARRIED_LIGHT,

		/** The room-light row contributes to this draw. */
		ROOM_LIGHT,

		/** The environment-light row replaces this draw's ambient with the sky. */
		ENVIRONMENT_LIGHT,

		/** The foliage-sway row displaces this draw. */
		FOLIAGE_SWAY,

		/** The grass-displacement row pushes this draw. */
		GRASS_DISPLACEMENT,

		/**
		 * The caustics row draws a light pattern on submerged ground. On its own the
		 * row says only that the settings want it; the pattern also needs water
		 * over the tile, which travels separately as {@code HdGroundWater}.
		 */
		CAUSTICS,

		/**
		 * The reflections row samples the environment cube on this draw. On its own
		 * the row says only that the settings want it; the surface must also be one
		 * the client already considers reflective, which travels separately as the
		 * material's stock effect slot (see {@code HdReflection}).
		 */
		REFLECTIONS,

		/** The world-textures row draws the ground from the texture page. */
		WORLD_TEXTURES,

		/** The material-maps row resolves a material id to its page cell. */
		MATERIAL_MAPS,

		/**
		 * The terrain-blend row mixes a tile's two materials through a noise
		 * mask. On its own it says the settings want the blend; the tile's own
		 * pair and weight travel separately, in its material map.
		 */
		TERRAIN_BLEND;

		/** This row's bit in a set's mask. */
		long bit() {
			return 1L << ordinal();
		}
	}

	private final long mask;

	private HdDrawRows(long mask) {
		this.mask = mask;
	}

	/** No row on: the draw the stock path draws. */
	public static HdDrawRows none() {
		return new HdDrawRows(0L);
	}

	/** The set with exactly these rows on. */
	public static HdDrawRows of(Row... rows) {
		HdDrawRows set = none();
		for (Row row : rows) {
			set = set.with(row);
		}
		return set;
	}

	public HdDrawRows with(Row... rows) {
		long mask = this.mask;
		for (Row row : rows) {
			mask |= row.bit();
		}
		return new HdDrawRows(mask);
	}

	/** {@link #with(Row...)} for a row whose state is only known as a boolean. */
	public HdDrawRows with(Row row, boolean on) {
		return on ? with(row) : without(row);
	}

	public HdDrawRows without(Row... rows) {
		long mask = this.mask;
		for (Row row : rows) {
			mask &= ~row.bit();
		}
		return new HdDrawRows(mask);
	}

	/** This set joined with another: a primitive's rows over the scene's. */
	public HdDrawRows with(HdDrawRows other) {
		return new HdDrawRows(this.mask | other.mask);
	}

	public boolean has(Row row) {
		return (this.mask & row.bit()) != 0L;
	}

	@Override
	public String toString() {
		StringBuilder text = new StringBuilder("rows=");
		for (Row row : Row.values()) {
			if (has(row)) {
				text.append(row).append(' ');
			}
		}
		return text.toString().trim();
	}
}
