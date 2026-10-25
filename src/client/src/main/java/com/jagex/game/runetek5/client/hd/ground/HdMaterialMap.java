package com.jagex.game.runetek5.client.hd.ground;

import com.jagex.game.runetek5.config.material.MaterialType;

/**
 * The material map: for every ground material the world-texture atlas holds,
 * which cell its texture lives in and the stock parameters the shader needs to
 * shade it.
 *
 * <p>This is the map the terrain rows were waiting on. The client's materials
 * are not textures with a few channels each - {@link MaterialType} is a record
 * of generation parameters, and the stock ground path bakes the two that matter
 * into every vertex ({@code GlGroundLayer.setVertexColour} reads
 * {@code aByte87} and {@code aByte90} and lightens the vertex colour with
 * them). A shader cannot see a vertex colour that has already been baked, and a
 * blended tile needs both materials' parameters at the same fragment, so the
 * pair is put where the fragment stage can reach it: one texel per material id,
 * {@code (cell + 1, brightness, blend, 255)}.
 *
 * <p>Cell {@code 0} means "not on the page": the arithmetic that turns it into
 * an atlas rectangle is only ever run for a material that registered
 * successfully, and a shader that sees zero takes the stock single-texture
 * path. A 592 region uses far fewer materials than a session visits, so cells
 * are recycled rather than owned: each {@link #beginFrame() frame} is a
 * generation, {@link #register} stamps the material it serves with the current
 * one, and when every cell is taken the material that has gone longest without
 * being drawn gives its cell to the newcomer. A material that was displaced
 * keeps its old texel value until it is drawn again - and by then its texel has
 * been cleared - so a tile still in view is never displaced for one that is
 * not, and nothing ever reads a cell that now belongs to another material.
 */
public final class HdMaterialMap {

	/** Material ids a 592 cache can address; the map is one texel each. */
	public static final int MATERIALS = 256;

	/** The value {@link #cellOf(int)} reports for a material that has no cell. */
	public static final int NO_CELL = -1;

	/**
	 * Whether a page build can fill a material's cell right now: the check a
	 * material has to pass before the map points any cell at it.
	 *
	 * <p>The map cannot see art; the client's sampler can. This is the shape the
	 * toolkit hands over so the map - which owns which cell each material reads -
	 * is the thing that decides whether a material gets one, instead of the page
	 * silently skipping a cell the map has already published.
	 */
	public interface Art {
		/** True when a page build would copy this material's art into its cell. */
		boolean available(int material);
	}

	/**
	 * The art check for a caller with no page to ask: every material passes.
	 *
	 * <p>Production never passes this - {@code GlToolkit} hands over the page's own
	 * test - so a caller that names it is saying "this map is not backing a page".
	 * It is public and deliberately spelled out at each call site rather than
	 * defaulted to, because a default would let a caller that does have a page
	 * publish a cell the page cannot fill.
	 */
	public static final Art ANY_ART = new Art() {
		@Override
		public boolean available(int material) {
			return true;
		}
	};

	/**
	 * The map's row width; one texel per material id. The shader samples it with
	 * {@code (id + 0.5) / MATERIALS} so each id lands in the middle of its texel.
	 */
	public static final int WIDTH = MATERIALS;

	private final int[] cells = new int[MATERIALS];

	private final int[] brightness = new int[MATERIALS];

	private final int[] blend = new int[MATERIALS];

	/** The generation each material was last drawn in; 0 before its first draw. */
	private final int[] lastUsed = new int[MATERIALS];

	/** The material occupying each cell, or -1 while the cell is spare. */
	private final int[] owners = new int[HdWorldAtlas.CAPACITY];

	private int assigned;

	/** The current generation; advanced once per world frame. */
	private int generation;

	/** Bumped whenever a material joins, leaves or is recycled, so the page can be rebuilt once. */
	private int version;

	/** Cells handed to a newer material, for the report and for the tests. */
	private int evictions;

	/**
	 * The map {@link #version} at which a material last failed the art check, so
	 * a material whose art has not arrived is probed once per page, not once per
	 * draw: the next probe waits for some other material to move the version,
	 * which is exactly when the page is rebuilt anyway.
	 */
	private final int[] refusedAt = new int[MATERIALS];

	public HdMaterialMap() {
		java.util.Arrays.fill(this.owners, -1);
		java.util.Arrays.fill(this.refusedAt, -1);
	}

	/**
	 * Starts a new generation. A material not drawn since the previous call is
	 * cold, and only cold materials give up their cells, so a frame can never
	 * recycle the material it is drawing.
	 */
	public void beginFrame() {
		this.generation++;
	}

	/**
	 * Reserves a cell for {@code material} only when the page can fill it, or
	 * returns -1 when the id cannot be mapped, the page cannot fill it, or every
	 * cell was drawn in this generation - the cases where the material keeps the
	 * stock single-texture draw for this frame.
	 *
	 * <p>Drawing a material that already holds a cell returns that cell and only
	 * refreshes its stamp, so a frame that draws the same material many times
	 * costs one comparison after the first. Drawing a material that does not
	 * hold one takes a spare cell, or the cell of the material that has gone
	 * longest without being drawn. The displaced material's texel goes back to
	 * "no cell" in the same call, so the map never points two ids at one cell.
	 *
	 * <p>The cell is what the shader reads to decide whether to sample the page
	 * at all: a published cell means "sample this cell", and a cell nothing was
	 * copied into holds zeroes - black, with alpha zero. With the ground pass'
	 * blending and its {@code GREATER} alpha test, that is not a dark tile but no
	 * tile at all: the fragment is discarded, no colour and no depth is written,
	 * and the water or sky behind it shows through the terrain. So a material
	 * whose art the page could not produce must keep {@link #NO_CELL}, which the
	 * shader reads as "this material is not on the page" and multiplies by
	 * nothing - the stock single-texture draw, the state before the atlas rows
	 * existed.
	 *
	 * <p>The check runs only when a material would take a cell, so a material the
	 * map already holds costs no sampler call; a refusal is remembered against
	 * {@link #version()}, so art that has not streamed in is asked for once per
	 * page rather than once per draw. The sweep drops a material that never
	 * joins to the stock draw, which is where a material the page does not hold
	 * belongs; a later page rebuild (any other material joining) asks again.
	 *
	 * <p>{@code art} is required. There is no overload that skips it: the one that
	 * existed was the shorter call and let a caller publish a cell the page could
	 * not fill, which is the empty-cell hole this check exists to prevent. A
	 * caller with no page says so by name, with {@link #ANY_ART}.
	 *
	 * @param art the page's own test; never null
	 * @throws NullPointerException when {@code art} is null
	 */
	public int register(int material, MaterialType type, Art art) {
		java.util.Objects.requireNonNull(art, "art");
		if (material < 0 || material >= MATERIALS) {
			return -1;
		}
		if (this.cells[material] != 0) {
			this.lastUsed[material] = this.generation;
			return this.cells[material] - 1;
		}
		if (this.refusedAt[material] == this.version) {
			return -1;
		}
		if (!art.available(material)) {
			this.refusedAt[material] = this.version;
			return -1;
		}
		int cell;
		if (this.assigned < HdWorldAtlas.CAPACITY) {
			cell = this.assigned++;
		} else {
			cell = this.coldestCell();
			if (cell < 0) {
				return -1;
			}
			int displaced = this.owners[cell];
			this.cells[displaced] = 0;
			this.brightness[displaced] = 0;
			this.blend[displaced] = 0;
			this.evictions++;
		}
		this.owners[cell] = material;
		this.cells[material] = cell + 1;
		this.brightness[material] = type == null ? 0 : type.aByte87 & 0xFF;
		this.blend[material] = type == null ? 0 : type.aByte90 & 0xFF;
		this.lastUsed[material] = this.generation;
		this.version++;
		return cell;
	}

	/**
	 * The cell whose material has gone longest without being drawn, or -1 when
	 * every cell belongs to a material drawn in this generation. Ties go to the
	 * lower cell number, so the same wave of arrivals recycles the same cells.
	 */
	private int coldestCell() {
		int coldest = -1;
		int stamp = Integer.MAX_VALUE;
		for (int cell = 0; cell < HdWorldAtlas.CAPACITY; cell++) {
			int owner = this.owners[cell];
			if (owner < 0) {
				return cell;
			}
			if (this.lastUsed[owner] >= this.generation) {
				continue;
			}
			if (this.lastUsed[owner] < stamp) {
				stamp = this.lastUsed[owner];
				coldest = cell;
			}
		}
		return coldest;
	}

	/** The cell this material holds, or {@link #NO_CELL}. */
	public int cellOf(int material) {
		if (material < 0 || material >= MATERIALS) {
			return NO_CELL;
		}
		return this.cells[material] == 0 ? NO_CELL : this.cells[material] - 1;
	}

	/** The map's texel for this material's brightness ({@code aByte87}), 0..255. */
	public int brightnessOf(int material) {
		return material < 0 || material >= MATERIALS ? 0 : this.brightness[material];
	}

	/** The map's texel for this material's blend toward white ({@code aByte90}), 0..255. */
	public int blendOf(int material) {
		return material < 0 || material >= MATERIALS ? 0 : this.blend[material];
	}

	/** How many materials hold a cell; the page's occupancy, never above capacity. */
	public int assigned() {
		return this.assigned;
	}

	/** Increments once per cell handed out or recycled; the texture is rebuilt on a change. */
	public int version() {
		return this.version;
	}

	/** The current generation; see {@link #beginFrame()}. */
	public int generation() {
		return this.generation;
	}

	/** The generation this material was last drawn in; 0 before its first draw. */
	public int lastUsedOf(int material) {
		return material < 0 || material >= MATERIALS ? 0 : this.lastUsed[material];
	}

	/** How many cells this map has recycled for a newer material. */
	public int evictions() {
		return this.evictions;
	}

	/** True when the page has no spare cell. */
	public boolean full() {
		return this.assigned >= HdWorldAtlas.CAPACITY;
	}

	/**
	 * The map's texel for one material id, as {@code 0xAARRGGBB}:
	 * red is {@code cell + 1} (0 for a material with no cell), green is
	 * {@code aByte87}, blue is {@code aByte90}, alpha is 255.
	 */
	public int pixel(int material) {
		int cell = this.cellOf(material);
		int red = cell == NO_CELL ? 0 : cell + 1;
		return 0xFF000000 | red << 16 | this.brightnessOf(material) << 8 | this.blendOf(material);
	}

	/** Every texel, in material-id order: the texture's row. */
	public int[] pixels() {
		int[] texels = new int[MATERIALS];
		for (int material = 0; material < MATERIALS; material++) {
			texels[material] = pixel(material);
		}
		return texels;
	}

	/**
	 * The same row as RGBA bytes, the order {@code GL_RGBA} uploads in. The
	 * shader reads red for the cell and green/blue for the parameters, so the
	 * channel order is part of the contract rather than an upload detail.
	 */
	public byte[] texels() {
		byte[] bytes = new byte[MATERIALS * 4];
		for (int material = 0; material < MATERIALS; material++) {
			int pixel = pixel(material);
			int at = material * 4;
			bytes[at] = (byte) (pixel >> 16 & 0xFF);
			bytes[at + 1] = (byte) (pixel >> 8 & 0xFF);
			bytes[at + 2] = (byte) (pixel & 0xFF);
			bytes[at + 3] = (byte) (pixel >>> 24);
		}
		return bytes;
	}

	/**
	 * The atlas coordinate the shader samples for one material id: the middle of
	 * the map's texel, so filtering cannot slide onto a neighbouring id.
	 */
	public static float atlasCoordinate(int material) {
		return (Math.max(0, material) + 0.5F) / MATERIALS;
	}

	/**
	 * How much of the brighter material a fragment of {@code material} shows in
	 * the stock path: {@code aByte90 / 255}, the same fraction
	 * {@code GlGroundLayer.setVertexColour} mixes toward white.
	 */
	public static float blendFraction(int blendByte) {
		return (blendByte & 0xFF) / 255.0F;
	}
}
