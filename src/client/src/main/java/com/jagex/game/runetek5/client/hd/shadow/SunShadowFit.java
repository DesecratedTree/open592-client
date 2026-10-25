package com.jagex.game.runetek5.client.hd.shadow;

/**
 * The sun's orthographic shadow matrix, as pure maths.
 *
 * <p>One directional light, one square orthographic volume sized by a radius
 * around a centre: the light sits {@code radius} back along the sun direction
 * and the volume spans {@code +-radius} across and {@code 2 * radius} deep, so
 * every point of the scene inside the radius is inside the map. The result is
 * already biased into texture space ({@code 0..1} on all three axes), because
 * the fragment stage multiplies it straight into {@code texture2D}.
 *
 * <p>Matrices are column-major, the order {@code glLoadMatrixf} and
 * {@code glUniformMatrix4fv} take, so {@code m[column * 4 + row]}.
 *
 * <p>Nothing here touches GL: the fit is the part that can be wrong in ways a
 * screenshot cannot show (a flipped axis, a rolled basis, a volume that misses
 * the geometry), so it is tested directly in {@code SunShadowFitTest}.
 */
public final class SunShadowFit {

	/** Below this the sun direction is treated as straight up. */
	private static final float MIN_LENGTH = 1.0E-6F;

	/** Near plane: keeps the projection finite without losing the centre. */
	public static final float NEAR = 0.001F;

	private SunShadowFit() {
	}

	/**
	 * The world-to-shadow-texture matrix: {@link #clipMatrix} biased into
	 * {@code 0..1}, which is what the fragment stage samples with.
	 */
	public static float[] lightMatrix(float sunX, float sunY, float sunZ, float centreX, float centreY,
			float centreZ, float radius) {
		return bias(clipMatrix(sunX, sunY, sunZ, centreX, centreY, centreZ, radius));
	}

	/**
	 * The world-to-light-clip matrix the depth pass rasterises with.
	 *
	 * @param sunX   sun direction, pointing from the scene toward the sun
	 * @param radius half-extent of the square volume, in scene units
	 */
	public static float[] clipMatrix(float sunX, float sunY, float sunZ, float centreX, float centreY,
			float centreZ, float radius) {
		float[] sun = normalise(sunX, sunY, sunZ);
		// The light looks from the sun back toward the scene.
		float forwardX = -sun[0];
		float forwardY = -sun[1];
		float forwardZ = -sun[2];

		// A stable up hint: straight up unless the light is vertical, in which
		// case +x, so the basis never degenerates.
		float upX = 0.0F;
		float upY = 1.0F;
		float upZ = 0.0F;
		if (Math.abs(forwardY) > 0.99F) {
			upX = 1.0F;
			upY = 0.0F;
		}

		float[] right = normalise(
			upY * forwardZ - upZ * forwardY,
			upZ * forwardX - upX * forwardZ,
			upX * forwardY - upY * forwardX);
		float[] up = new float[] {
			forwardY * right[2] - forwardZ * right[1],
			forwardZ * right[0] - forwardX * right[2],
			forwardX * right[1] - forwardY * right[0]
		};

		// Eye sits toward the sun, radius back from the centre.
		float eyeX = centreX + sun[0] * radius;
		float eyeY = centreY + sun[1] * radius;
		float eyeZ = centreZ + sun[2] * radius;

		float[] view = new float[16];
		view[0] = right[0];
		view[4] = right[1];
		view[8] = right[2];
		view[12] = -(right[0] * eyeX + right[1] * eyeY + right[2] * eyeZ);
		view[1] = up[0];
		view[5] = up[1];
		view[9] = up[2];
		view[13] = -(up[0] * eyeX + up[1] * eyeY + up[2] * eyeZ);
		view[2] = -forwardX;
		view[6] = -forwardY;
		view[10] = -forwardZ;
		view[14] = forwardX * eyeX + forwardY * eyeY + forwardZ * eyeZ;
		view[15] = 1.0F;

		float far = radius * 2.0F;
		float[] projection = orthographic(-radius, radius, -radius, radius, NEAR, far);
		return multiply(projection, view);
	}

	/** {@code glOrtho}, column-major. */
	public static float[] orthographic(float left, float right, float bottom, float top, float near, float far) {
		float[] matrix = new float[16];
		matrix[0] = 2.0F / (right - left);
		matrix[5] = 2.0F / (top - bottom);
		matrix[10] = -2.0F / (far - near);
		matrix[12] = -(right + left) / (right - left);
		matrix[13] = -(top + bottom) / (top - bottom);
		matrix[14] = -(far + near) / (far - near);
		matrix[15] = 1.0F;
		return matrix;
	}

	/**
	 * Maps clip space {@code -1..1} onto texture space {@code 0..1}, so the
	 * fragment stage can sample without a per-fragment remap.
	 */
	public static float[] bias(float[] matrix) {
		return multiply(new float[] {
			0.5F, 0.0F, 0.0F, 0.0F,
			0.0F, 0.5F, 0.0F, 0.0F,
			0.0F, 0.0F, 0.5F, 0.0F,
			0.5F, 0.5F, 0.5F, 1.0F
		}, matrix);
	}

	/** Column-major {@code a * b}: apply {@code b} first. */
	public static float[] multiply(float[] a, float[] b) {
		float[] result = new float[16];
		for (int column = 0; column < 4; column++) {
			for (int row = 0; row < 4; row++) {
				float sum = 0.0F;
				for (int k = 0; k < 4; k++) {
					sum += a[k * 4 + row] * b[column * 4 + k];
				}
				result[column * 4 + row] = sum;
			}
		}
		return result;
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

	/** Normalises a direction; a zero length becomes straight up. */
	public static float[] normalise(float x, float y, float z) {
		float length = (float) Math.sqrt(x * x + y * y + z * z);
		if (length < MIN_LENGTH) {
			return new float[] { 0.0F, 1.0F, 0.0F };
		}
		return new float[] { x / length, y / length, z / length };
	}

	/**
	 * The depth bias, in texture-space depth units, used so a surface does not
	 * shadow itself. Scaled by the volume's depth range so it means the same
	 * thing at every resolution and radius.
	 */
	public static float depthBias(float radius) {
		return Math.max(1.0E-4F, 0.005F / Math.max(radius, 1.0F));
	}
}
