package com.jagex.game.runetek5.client.hd.sway;

/**
 * The displacement block one vegetation draw is given: two amplitudes, the wind
 * phase, and where the viewer stands.
 *
 * <p>Immutable and compared by value, because the pipeline only re-uploads a
 * uniform it has not sent before: a frame where nothing moved costs no GL call,
 * and two draws in the same frame with the same block cost one.
 *
 * <p>Amplitudes are scene units - the unit the camera, the entity positions and
 * the loc positions all use ({@code 128} per tile) - so a lean is the same
 * physical distance at any zoom. A row that is off contributes exactly
 * {@code 0.0F}, not a small value, which is what lets the shader skip the
 * branch and reproduce the stock transform bit for bit.
 *
 * <p>The viewer is recovered here rather than passed in, so the seam supplies a
 * matrix and the maths stays in one testable place. Inverting an affine
 * scene-to-eye map is just the negative rotated translation; the matrix's
 * rotation is orthonormal by construction (it is a camera), so its transpose is
 * its inverse and no general inversion is needed.
 */
public final class HdSway {

	/** Units a fully-grown plant's crown leans at the wind's peak. */
	public static final float FOLIAGE_AMPLITUDE = 18.0F;

	/** Units a tuft is bent away at the very centre of the grass part. */
	public static final float GRASS_AMPLITUDE = 40.0F;

	/**
	 * Scene radius of the grass part, measured in the ground plane from the
	 * viewer's column: four tiles. Beyond it the push is exactly zero.
	 *
	 * <p>Measured in {@code xz} only, never in three dimensions: the eye sits
	 * hundreds of units above the ground, so a 3D radius would either have to be
	 * enormous - and then reach half the room - or exclude the tuft at the
	 * player's feet. The column under the eye is the viewer's own ground
	 * position, which is the thing the row is named after.
	 */
	public static final float GRASS_RADIUS = 512.0F;

	/** Radians per second of wind phase; one full gust is about six seconds. */
	public static final float WIND_SPEED = 1.1F;

	/**
	 * Scene units of height over which the lever goes from root to crown.
	 *
	 * <p>Not a measurement of any particular model: the lever is normalised by
	 * it, so a plant shorter than this leans with only part of the amplitude and
	 * a tall one is capped rather than ripping out of the ground. Half a tile,
	 * so a tuft bends from just above its roots.
	 */
	public static final float HEIGHT_SCALE = 64.0F;

	/** Neither row: the stock transform, and the pipeline's initial state. */
	public static final HdSway OFF = new HdSway(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

	private final float foliage;

	private final float grass;

	private final float seconds;

	private final float viewerX;

	private final float viewerY;

	private final float viewerZ;

	private HdSway(float foliage, float grass, float seconds, float viewerX, float viewerY, float viewerZ) {
		this.foliage = foliage;
		this.grass = grass;
		this.seconds = seconds;
		this.viewerX = viewerX;
		this.viewerY = viewerY;
		this.viewerZ = viewerZ;
	}

	/**
	 * The block for this frame, with the amplitudes the two rows ask for.
	 *
	 * @param foliage whether the foliage-sway row is on
	 * @param grass   whether the grass-displacement row is on
	 * @param seconds the wind clock, from {@link HdWindTime#seconds()}
	 */
	public static HdSway of(boolean foliage, boolean grass, float seconds, float viewerX, float viewerY,
			float viewerZ) {
		if (!foliage && !grass) {
			// Both rows off is the initial state exactly, not a block of zeroes
			// that merely looks like it: the pipeline's cache compares identity.
			return OFF;
		}
		return new HdSway(foliage ? FOLIAGE_AMPLITUDE : 0.0F, grass ? GRASS_AMPLITUDE : 0.0F,
			seconds, viewerX, viewerY, viewerZ);
	}

	/**
	 * The same, taking the scene-to-eye matrix and recovering the viewer itself.
	 *
	 * <p>{@code sceneToEye} is column-major ({@code m[column * 4 + row]}) and
	 * affine, so the eye is {@code -R^T * t}: the negation of the translation
	 * dotted with each of the rotation's columns.
	 */
	public static HdSway ofScene(boolean foliage, boolean grass, float seconds, float[] sceneToEye) {
		if (!foliage && !grass) {
			return OFF;
		}
		if (sceneToEye == null) {
			return of(foliage, grass, seconds, 0.0F, 0.0F, 0.0F);
		}
		float x = -(sceneToEye[0] * sceneToEye[12] + sceneToEye[1] * sceneToEye[13]
			+ sceneToEye[2] * sceneToEye[14]);
		float y = -(sceneToEye[4] * sceneToEye[12] + sceneToEye[5] * sceneToEye[13]
			+ sceneToEye[6] * sceneToEye[14]);
		float z = -(sceneToEye[8] * sceneToEye[12] + sceneToEye[9] * sceneToEye[13]
			+ sceneToEye[10] * sceneToEye[14]);
		return of(foliage, grass, seconds, x, y, z);
	}

	/**
	 * This block with any amplitude the draw's own rows do not claim removed.
	 *
	 * <p>The pipeline masks with the request rather than trusting its caller: a
	 * draw whose request says both rows are off gets exactly zero, so the
	 * "off is the stock transform" promise cannot be broken by a stale block
	 * arriving at the seam.
	 */
	public HdSway masked(boolean foliage, boolean grass) {
		if ((foliage || this.foliage == 0.0F) && (grass || this.grass == 0.0F)) {
			return this;
		}
		return new HdSway(foliage ? this.foliage : 0.0F, grass ? this.grass : 0.0F,
			this.seconds, this.viewerX, this.viewerY, this.viewerZ);
	}

	/** True when at least one row gives this block a non-zero displacement. */
	public boolean active() {
		return this.foliage != 0.0F || this.grass != 0.0F;
	}

	public float foliage() {
		return this.foliage;
	}

	public float grass() {
		return this.grass;
	}

	public float seconds() {
		return this.seconds;
	}

	public float viewerX() {
		return this.viewerX;
	}

	public float viewerY() {
		return this.viewerY;
	}

	public float viewerZ() {
		return this.viewerZ;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HdSway)) {
			return false;
		}
		HdSway that = (HdSway) other;
		return Float.compare(this.foliage, that.foliage) == 0
			&& Float.compare(this.grass, that.grass) == 0
			&& Float.compare(this.seconds, that.seconds) == 0
			&& Float.compare(this.viewerX, that.viewerX) == 0
			&& Float.compare(this.viewerY, that.viewerY) == 0
			&& Float.compare(this.viewerZ, that.viewerZ) == 0;
	}

	@Override
	public int hashCode() {
		int hash = Float.floatToIntBits(this.foliage);
		hash = 31 * hash + Float.floatToIntBits(this.grass);
		hash = 31 * hash + Float.floatToIntBits(this.seconds);
		hash = 31 * hash + Float.floatToIntBits(this.viewerX);
		hash = 31 * hash + Float.floatToIntBits(this.viewerY);
		hash = 31 * hash + Float.floatToIntBits(this.viewerZ);
		return hash;
	}
}
