package com.jagex.game.runetek5.client.hd.ground;

/**
 * The terrain pass's two missing transforms, as pure maths.
 *
 * <p>The ground is drawn exactly like a model: one interleaved buffer of
 * positions, colours and normals, submitted through {@code method1934} +
 * {@code method1967}. What differs is the spaces. A model's buffer is
 * model-local and its modelview is {@code R * V * M}, so {@code gl_Vertex}
 * reaches both scene and eye space through matrices GL already holds. The
 * terrain's buffer holds <em>scene units</em> at GL quality ({@code 128} per
 * tile, {@code GlToolkit.createGround}), so the live pass can be given the
 * camera's own modelview and the mapping is the identity - which is what
 * {@code GlGroundLayer.method1462} passes (window origin 0). The top-down map
 * bake is the one that draws a window of the buffer under an identity
 * modelview and its own ortho, and it names the window it is drawing; the seam
 * is guarded during that bake and never binds. Neither the scene position nor
 * the eye position is recoverable from GL state in the window case, which is
 * why the pass has its own pair of matrices:
 *
 * <ul>
 * <li>{@link #tileToScene} maps a tile-space vertex to the scene coordinates a
 * model at the same place would use: {@code 128} units per tile (the unit the
 * camera, the entity positions and the sun's shadow volume all live in), with
 * the window origin subtracted so a vertex is not offset twice.</li>
 * <li>{@link #sceneToEye} is the ground pass's view transform. The toolkit's
 * {@code method1952} loads exactly {@code rotX(180) * V} before the terrain,
 * where {@code V} is the frame's camera matrix, so scene-to-eye here is
 * {@code rotX(180) * V} and nothing else. The 180-degree flip is the ground's:
 * dropping it lights the terrain from under the world.</li>
 * </ul>
 *
 * <p>With those two in place the terrain's {@code vWorldPosition} is the same
 * function of the same world point as a model's, so the sun's shadow map, the
 * contact-occlusion kernel and the point lights all agree across the
 * model/ground seam. That is the property {@code GroundTileSpaceTest} pins: one
 * world point expressed as a model vertex and as a tile vertex must reach the
 * same scene position, the same eye position and the same shadow texel.
 *
 * <p>Column-major throughout, the order {@code glLoadMatrixf} and the lighting
 * program's {@code uModelRow}/{@code uEyeRow} uniforms use:
 * {@code m[column * 4 + row]}.
 */
public final class GroundTileSpace {

	/** Scene units per tile: the unit entity positions and the camera use. */
	public static final int UNITS_PER_TILE = 128;

	/** {@code rotX(180)}, as the toolkit applies it: y and z negate. */
	private static final float[] FLIP_X = {
		1.0F, 0.0F, 0.0F, 0.0F,
		0.0F, -1.0F, 0.0F, 0.0F,
		0.0F, 0.0F, -1.0F, 0.0F,
		0.0F, 0.0F, 0.0F, 1.0F
	};

	private GroundTileSpace() {
	}

	/**
	 * Fills {@code target} with this window's tile-to-scene matrix.
	 *
	 * <p>{@code x} and {@code z} scale by {@code 128 / tileUnits} and lose the
	 * window's origin, {@code y} is already a scene height and passes through.
	 *
	 * @param tileUnits {@code GlGround.anInt5261}: vertices per tile
	 * @param originX   the window's first tile, the coordinate the pass's ortho
	 *                  subtracts
	 * @param originZ   the window's first tile on the other axis
	 */
	public static float[] tileToScene(float[] target, int tileUnits, int originX, int originZ) {
		float scale = (float) UNITS_PER_TILE / (float) tileUnits;
		for (int index = 0; index < 16; index++) {
			target[index] = 0.0F;
		}
		target[0] = scale;
		target[5] = 1.0F;
		target[10] = scale;
		target[12] = (float) -originX * UNITS_PER_TILE;
		target[14] = (float) -originZ * UNITS_PER_TILE;
		target[15] = 1.0F;
		return target;
	}

	/**
	 * Fills {@code target} with {@code rotX(180) * view}, this pass's
	 * scene-to-eye matrix.
	 *
	 * <p>{@code view} is the frame's camera matrix - {@code
	 * GlToolkit.aClass31_Sub1_4.method5161()} - which is what the model path's
	 * modelview starts from. The copy is deliberate: {@code method5161} returns
	 * a shared scratch array that the next matrix call overwrites.
	 */
	public static float[] sceneToEye(float[] view, float[] target) {
		return multiply(FLIP_X, view, target);
	}

	/** {@code a * b}, column-major: apply {@code b} first. */
	public static float[] multiply(float[] a, float[] b, float[] target) {
		for (int column = 0; column < 4; column++) {
			for (int row = 0; row < 4; row++) {
				float sum = 0.0F;
				for (int k = 0; k < 4; k++) {
					sum += a[k * 4 + row] * b[column * 4 + k];
				}
				target[column * 4 + row] = sum;
			}
		}
		return target;
	}

	/** Transforms one point, returning {@code (x, y, z, w)} after the matrix. */
	public static float[] transform(float[] matrix, float x, float y, float z) {
		return new float[] {
			matrix[0] * x + matrix[4] * y + matrix[8] * z + matrix[12],
			matrix[1] * x + matrix[5] * y + matrix[9] * z + matrix[13],
			matrix[2] * x + matrix[6] * y + matrix[10] * z + matrix[14],
			matrix[3] * x + matrix[7] * y + matrix[11] * z + matrix[15]
		};
	}
}
