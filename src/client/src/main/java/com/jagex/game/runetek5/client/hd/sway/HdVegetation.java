package com.jagex.game.runetek5.client.hd.sway;

/**
 * Which draws the two vegetation rows are allowed to touch.
 *
 * <p>A sway shader cannot tell a fern from a fence post: both are the same
 * interleaved position/colour/normal buffer drawn through the same program. The
 * one place the scene still knows the difference is the loc's shape - the
 * {@code LocType} field the client already uses to pick the geometry - so the
 * kind travels with the draw, exactly as the light rows do, and the shader's
 * branch is keyed on it rather than on a height or a material guess.
 *
 * <p>Exactly two shapes are marked: {@value #GROUND_DECOR_SHAPE} and its
 * diagonal variant {@value #GROUND_DECOR_DIAGONAL_SHAPE}, the ground decoration
 * layer the terrain draws flat on the ground (flowers, ferns, the low plants).
 * The diagonal one is included because the client already draws it with the
 * same model - {@code LocEntity.method578} maps shape 11 to shape 10 - so it is
 * the same plant at a different angle, and leaving it unmarked would make one
 * of every two tufts still. Everything else, including every model the scene
 * did not tag at all, is {@link #NONE} and keeps the stock vertex transform. That is what makes "both rows off is byte-identical" hold by
 * construction rather than by tuning an amplitude to zero.
 *
 * <p>Strictly a subset on purpose. Whether shape 10 is honest vegetation in a
 * given cache is a question for a cache-backed session, not for arithmetic
 * here: mis-tagging a wall would displace stone, while missing a plant only
 * leaves it still. The conservative set keeps the failure boring.
 */
public final class HdVegetation {

	/** Not vegetation: every model the scene did not mark, and all of stock. */
	public static final int NONE = 0;

	/** Ground-layer scenery: the loc shape that stands flat on the ground. */
	public static final int GROUND_DECOR = 1;

	/** {@code LocType} shape 10, the ground-decoration layer. */
	public static final int GROUND_DECOR_SHAPE = 10;

	/** {@code LocType} shape 11, the diagonal ground decoration. */
	public static final int GROUND_DECOR_DIAGONAL_SHAPE = 11;

	private HdVegetation() {
	}

	/**
	 * Maps a loc shape to the kind the draw request carries.
	 *
	 * <p>The diagonal decoration is the same geometry as the flat one - the
	 * loc's own draw already substitutes shape 10 for 11 - so both mark.
	 */
	public static int ofLocShape(int shape) {
		return shape == GROUND_DECOR_SHAPE || shape == GROUND_DECOR_DIAGONAL_SHAPE ? GROUND_DECOR : NONE;
	}

	/** True when this kind may be displaced by either row. */
	public static boolean isVegetation(int kind) {
		return kind == GROUND_DECOR;
	}
}
