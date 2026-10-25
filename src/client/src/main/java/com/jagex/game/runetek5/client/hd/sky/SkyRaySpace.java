package com.jagex.game.runetek5.client.hd.sky;

/**
 * The camera's three ray directions for the sky: the screen's right axis, its
 * up axis and its centre, in scene coordinates.
 *
 * <p>The sky is the one surface in the client with no geometry to place: every
 * pixel is a direction from the eye, and the colours are functions of that
 * direction. The dimensions come from the frame itself - the scene-to-eye matrix
 * the toolkit builds the modelview from ({@code method1952}) and the projection
 * matrix it uploads ({@code GlToolkit.hdProjection()}) - so the dome cannot
 * disagree with the world drawn in front of it. That is the rule
 * {@code GroundTileSpace} follows for the terrain.
 *
 * <p><b>The flip.</b> The toolkit draws the scene with {@code rotX(180) * V},
 * not with {@code V} alone: this client's camera looks along +z and its world's
 * y axis points down, while GL's eye space looks along -z with y up. A pass that
 * builds its own rays has to fold that rotation in or it renders a dome mirrored
 * against the world - which is exactly the mistake the pixel test's geometric
 * cross-check exists to catch. So the axes below are the <em>scene</em>
 * directions of GL's screen axes:
 *
 * <ul>
 * <li>right = row 0 of the camera, unchanged;</li>
 * <li>up = <b>minus</b> row 1, because the flip turns GL's +y into the scene's
 * -y (a smaller scene y is higher up);</li>
 * <li>centre = row 2, because GL's forward is -z and the flip turns it back into
 * the scene's +z.</li>
 * </ul>
 *
 * <p>Layouts are OpenGL's: a {@code glLoadMatrixf} array is column-major, so the
 * element in row {@code r} of column {@code c} is {@code m[c * 4 + r]}.
 *
 * <p>Pure maths, no GL calls: the pipeline uploads {@link #axes} and the tests
 * use {@link #direction} / {@link #projectsTo} to predict where the sun's disc
 * lands.
 */
public final class SkyRaySpace {

	/** Floats per axis set: three floats each for right, up and centre. */
	public static final int AXES = 9;

	private SkyRaySpace() {
	}

	/**
	 * Writes the screen's right, up and centre rays into {@code out}, as
	 * unnormalised scene-space vectors.
	 *
	 * <p>A pixel's direction is
	 * {@code right * ndcX + up * ndcY + centre}, normalised - the shader does
	 * exactly that with three {@code vec3} uniforms.
	 *
	 * @param camera the scene-to-eye matrix, as {@code glLoadMatrixf} takes it
	 * @param out    nine floats, or null for a fresh array
	 * @return {@code out}
	 */
	public static float[] axes(float[] camera, float[] out) {
		float[] axes = out == null ? new float[AXES] : out;
		axes[0] = camera[0];
		axes[1] = camera[4];
		axes[2] = camera[8];
		// Up is -row1: the toolkit's rotX(180) turns GL's +y into the scene's -y.
		axes[3] = -camera[1];
		axes[4] = -camera[5];
		axes[5] = -camera[9];
		// The centre is +row2: GL's forward is -z, and the flip maps that back
		// onto the camera's own +z, which is the world's forward.
		axes[6] = camera[2];
		axes[7] = camera[6];
		axes[8] = camera[10];
		return axes;
	}

	/**
	 * Writes the projection's two scale terms and two offsets.
	 *
	 * <p>{@code out = {m00, m11, m08, m09}} - the diagonal that turns an eye
	 * position into a normalised device coordinate, and the off-centre terms the
	 * toolkit's frustum carries when the viewport is not centred. Both are needed:
	 * with the offsets assumed zero, an off-centre projection would tilt the whole
	 * sky.
	 */
	public static float[] projection(float[] projectionMatrix, float[] out) {
		float[] values = out == null ? new float[4] : out;
		values[0] = projectionMatrix[0];
		values[1] = projectionMatrix[5];
		values[2] = projectionMatrix[8];
		values[3] = projectionMatrix[9];
		return values;
	}

	/**
	 * The scene-space direction through a normalised device coordinate.
	 *
	 * <p>Undoes the projection on the near plane, then interpolates the camera's
	 * three rays and normalises.
	 *
	 * @param camera     the scene-to-eye matrix
	 * @param projection the projection matrix, as {@code glLoadMatrixf} takes it
	 * @param ndcX       -1 (left) to 1 (right)
	 * @param ndcY       -1 (bottom) to 1 (top)
	 * @param out        three floats, or null for a fresh array
	 */
	public static float[] direction(float[] camera, float[] projection, float ndcX, float ndcY, float[] out) {
		float[] values = projection(projection, null);
		float eyeX = (ndcX + values[2]) / values[0];
		float eyeY = (ndcY + values[3]) / values[1];
		return direction(camera, eyeX, eyeY, out);
	}

	/**
	 * The scene-space direction for an eye-space offset on the near plane.
	 *
	 * @param camera the scene-to-eye matrix
	 * @param eyeX   how far along the screen's right axis
	 * @param eyeY   how far along the screen's up axis
	 * @param out    three floats, or null for a fresh array
	 */
	public static float[] direction(float[] camera, float eyeX, float eyeY, float[] out) {
		float[] axes = axes(camera, null);
		float[] result = out == null ? new float[3] : out;
		for (int axis = 0; axis < 3; axis++) {
			result[axis] = axes[axis] * eyeX + axes[3 + axis] * eyeY + axes[6 + axis];
		}
		float length = (float) Math.sqrt(result[0] * result[0] + result[1] * result[1]
			+ result[2] * result[2]);
		if (length > 0.0F) {
			result[0] /= length;
			result[1] /= length;
			result[2] /= length;
		}
		return result;
	}

	/**
	 * Whether a direction is visible, and the NDC it appears at.
	 *
	 * <p>Projects onto the camera's three rays: the centre ray's sign decides
	 * whether the direction is in front, and the other two divided by it give the
	 * normalised device coordinate the projection maps it to. Used by the tests to
	 * decide which pixel a sun direction should appear in; the pipeline never
	 * needs it.
	 */
	public static boolean projectsTo(float[] camera, float[] projection, float[] direction,
			float[] outNdc) {
		float[] axes = axes(camera, null);
		float x = axes[0] * direction[0] + axes[1] * direction[1] + axes[2] * direction[2];
		float y = axes[3] * direction[0] + axes[4] * direction[1] + axes[5] * direction[2];
		float z = axes[6] * direction[0] + axes[7] * direction[1] + axes[8] * direction[2];
		if (z <= 0.0F) {
			// Behind the camera: no pixel.
			return false;
		}
		float[] values = projection(projection, null);
		outNdc[0] = values[0] * (x / z) - values[2];
		outNdc[1] = values[1] * (y / z) - values[3];
		return outNdc[0] >= -1.0F && outNdc[0] <= 1.0F && outNdc[1] >= -1.0F && outNdc[1] <= 1.0F;
	}
}
