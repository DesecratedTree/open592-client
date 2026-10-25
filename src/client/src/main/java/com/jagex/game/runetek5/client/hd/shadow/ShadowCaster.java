package com.jagex.game.runetek5.client.hd.shadow;

/**
 * One object the sun's depth pass may draw: where it sits in the scene, and the
 * submission that draws it.
 *
 * <p>The scene already knows both halves at the moment it submits an object -
 * the object's placement and the call that draws its geometry - but the two
 * live in different places: the placement is a matrix the renderer composes and
 * the geometry is drawn by the object's own model. Carrying them together is
 * what lets the depth pass replay the frame's casters <em>without</em> the
 * world pass having to know a depth target exists.
 *
 * <p>The matrix is column-major, the order {@code glLoadMatrixf} and
 * {@code glMultMatrixf} take, and is applied by the pass before {@link #draw()}
 * runs. A submission that places itself (a model whose draw composes its own
 * matrix) uses {@link #of(Draw)}: identity here, placement inside the draw.
 */
public final class ShadowCaster {

	/** Draws this object's geometry in whatever space the caster names. */
	public interface Draw {

		void draw();
	}

	private static final float[] IDENTITY = { 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F,
		0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F };

	private final float[] modelToScene;

	private final Draw draw;

	private ShadowCaster(float[] modelToScene, Draw draw) {
		this.modelToScene = modelToScene;
		this.draw = draw;
	}

	/** A caster whose geometry the pass places with {@code modelToScene}. */
	public static ShadowCaster of(float[] modelToScene, Draw draw) {
		if (draw == null) {
			throw new IllegalArgumentException("a caster must draw something");
		}
		return new ShadowCaster(modelToScene == null ? IDENTITY.clone() : modelToScene.clone(), draw);
	}

	/** A caster that places itself; the pass applies the identity. */
	public static ShadowCaster of(Draw draw) {
		return of(null, draw);
	}

	/** A caster translated to a scene position, with no rotation. */
	public static ShadowCaster at(float x, float y, float z, Draw draw) {
		float[] matrix = IDENTITY.clone();
		matrix[12] = x;
		matrix[13] = y;
		matrix[14] = z;
		return of(matrix, draw);
	}

	/** The pass's model matrix for this caster, column-major; a copy. */
	public float[] modelToScene() {
		return this.modelToScene.clone();
	}

	/** Copies the matrix into a caller-owned array, for the per-frame draw path. */
	public void modelToScene(float[] target) {
		System.arraycopy(this.modelToScene, 0, target, 0, 16);
	}

	/** The geometry submission, run with the caster's matrix in place. */
	public Draw draw() {
		return this.draw;
	}
}
