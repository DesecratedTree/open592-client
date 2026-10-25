package com.jagex.game.runetek5.client.hd.sky;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The camera's axes and the ray through a pixel, as pure maths.
 *
 * <p>These are the numbers the sky shader is handed, so a mistake here tilts
 * the whole dome: the tests pin the identity case, a yaw, a pitch, an
 * off-centre projection, and the round trip between a direction and the pixel
 * it appears in.
 */
@DisplayName("592HD sky rays")
class SkyRaySpaceTest {

	private static final float EPSILON = 1.0e-5F;

	/** A centred perspective, 90 degrees either way: m00 = m11 = 1. */
	private static float[] squareProjection() {
		float[] projection = new float[16];
		projection[0] = 1.0F;
		projection[5] = 1.0F;
		projection[10] = -1.002F;
		projection[11] = -1.0F;
		projection[14] = -0.2F;
		return projection;
	}

	private static float[] identity() {
		float[] matrix = new float[16];
		matrix[0] = 1.0F;
		matrix[5] = 1.0F;
		matrix[10] = 1.0F;
		matrix[15] = 1.0F;
		return matrix;
	}

	/** A camera yawed by {@code degrees} about +y: rotating it right turns the view right. */
	private static float[] yaw(float degrees) {
		double radians = Math.toRadians(degrees);
		float cos = (float) Math.cos(radians);
		float sin = (float) Math.sin(radians);
		float[] matrix = identity();
		matrix[0] = cos;
		matrix[2] = -sin;
		matrix[8] = sin;
		matrix[10] = cos;
		return matrix;
	}

	/** A camera pitched by {@code degrees} about +x: positive looks up. */
	private static float[] pitch(float degrees) {
		double radians = Math.toRadians(degrees);
		float cos = (float) Math.cos(radians);
		float sin = (float) Math.sin(radians);
		float[] matrix = identity();
		matrix[5] = cos;
		matrix[6] = -sin;
		matrix[9] = sin;
		matrix[10] = cos;
		return matrix;
	}

	@Test
	@DisplayName("for an identity camera, up is -y and the centre is +z")
	void axes_shouldFoldInTheToolkitsFlip() {
		float[] axes = SkyRaySpace.axes(identity(), null);
		assertAll(
			() -> assertEquals(1.0F, axes[0], EPSILON, "right is the scene's +x"),
			() -> assertEquals(-1.0F, axes[4], EPSILON, "up is the scene's -y: this world's y points down"),
			() -> assertEquals(1.0F, axes[8], EPSILON, "the centre is the scene's +z"),
			() -> assertEquals(0.0F, axes[1], EPSILON),
			() -> assertEquals(0.0F, axes[2], EPSILON),
			() -> assertEquals(0.0F, axes[3], EPSILON),
			() -> assertEquals(0.0F, axes[5], EPSILON),
			() -> assertEquals(0.0F, axes[6], EPSILON)
		);
	}

	@Test
	@DisplayName("the centre of the screen is the scene's forward axis, +z")
	void direction_shouldLookAlongSceneForward() {
		float[] direction = SkyRaySpace.direction(identity(), squareProjection(), 0.0F, 0.0F, null);
		assertAll(
			() -> assertEquals(0.0F, direction[0], EPSILON),
			() -> assertEquals(0.0F, direction[1], EPSILON),
			() -> assertEquals(1.0F, direction[2], EPSILON)
		);
	}

	@Test
	@DisplayName("a 90 degree projection puts 45 degrees at the screen edge")
	void direction_shouldUndoTheProjection() {
		float[] right = SkyRaySpace.direction(identity(), squareProjection(), 1.0F, 0.0F, null);
		float[] up = SkyRaySpace.direction(identity(), squareProjection(), 0.0F, 1.0F, null);
		float diagonal = (float) Math.sin(Math.toRadians(45.0D));
		float forward = (float) Math.cos(Math.toRadians(45.0D));
		assertAll(
			() -> assertEquals(diagonal, right[0], EPSILON),
			() -> assertEquals(forward, right[2], EPSILON),
			() -> assertEquals(-diagonal, up[1], EPSILON,
				"the screen's up is the scene's -y"),
			() -> assertEquals(forward, up[2], EPSILON),
			() -> assertEquals(right[0], -up[1], EPSILON, "a square projection is square")
		);
	}

	@Test
	@DisplayName("yawing the camera turns the view with it")
	void direction_shouldFollowYaw() {
		float[] camera = yaw(90.0F);
		float[] direction = SkyRaySpace.direction(camera, squareProjection(), 0.0F, 0.0F, null);
		assertAll(
			// This camera maps the scene's +x axis onto its own -z, so it faces
			// -x; the centre ray must agree with that.
			() -> assertEquals(-1.0F, direction[0], EPSILON),
			() -> assertEquals(0.0F, direction[2], EPSILON),
			() -> assertEquals(0.0F, direction[1], EPSILON)
		);
	}

	@Test
	@DisplayName("pitching the camera up moves the view up (-y)")
	void direction_shouldFollowPitch() {
		float[] centre = SkyRaySpace.direction(pitch(20.0F), squareProjection(), 0.0F, 0.0F, null);
		float[] same = SkyRaySpace.direction(identity(), squareProjection(), 0.0F,
			(float) Math.tan(Math.toRadians(20.0D)), null);
		float[] level = SkyRaySpace.direction(identity(), squareProjection(), 0.0F, 0.0F, null);
		assertAll(
			() -> assertTrue(centre[1] < -0.3F, "a 20 degree pitch looks up: " + centre[1]),
			() -> assertEquals(same[1], centre[1], 1.0e-4F, "the same direction, reached two ways"),
			() -> assertEquals(level[1], 0.0F, EPSILON),
			() -> assertEquals(1.0F, level[2], EPSILON)
		);
	}

	@Test
	@DisplayName("an off-centre projection's offsets are honoured")
	void direction_shouldUseTheProjectionOffsets() {
		float[] projection = squareProjection();
		projection[8] = 0.25F;
		projection[9] = -0.5F;
		float[] direction = SkyRaySpace.direction(identity(), projection, 0.0F, 0.0F, null);
		assertAll(
			() -> assertTrue(direction[0] > 0.2F, "the frustum is off to the left: " + direction[0]),
			() -> assertTrue(direction[1] > 0.4F, "and down, which is +y here: " + direction[1])
		);
	}

	@Test
	@DisplayName("the projection terms are the matrix's own")
	void projection_shouldReadTheDiagonalAndOffsets() {
		float[] projection = squareProjection();
		projection[0] = 1.75F;
		projection[5] = 2.5F;
		projection[8] = 0.125F;
		projection[9] = -0.25F;
		float[] terms = SkyRaySpace.projection(projection, null);
		assertAll(
			() -> assertEquals(1.75F, terms[0], EPSILON),
			() -> assertEquals(2.5F, terms[1], EPSILON),
			() -> assertEquals(0.125F, terms[2], EPSILON),
			() -> assertEquals(-0.25F, terms[3], EPSILON)
		);
	}

	@Test
	@DisplayName("a direction lands back in the pixel it came from")
	void projectsTo_shouldInvertDirection() {
		float[] camera = yaw(12.0F);
		float[] projection = squareProjection();
		float[] ndc = new float[2];
		for (float x = -0.8F; x <= 0.8F; x += 0.4F) {
			for (float y = -0.8F; y <= 0.8F; y += 0.4F) {
				float[] direction = SkyRaySpace.direction(camera, projection, x, y, null);
				assertTrue(SkyRaySpace.projectsTo(camera, projection, direction, ndc),
					"(" + x + ", " + y + ") must be inside the frustum");
				assertEquals(x, ndc[0], 1.0e-4F);
				assertEquals(y, ndc[1], 1.0e-4F);
			}
		}
	}

	@Test
	@DisplayName("behind the camera and outside the frustum are not visible")
	void projectsTo_shouldRejectWhatIsOffScreen() {
		float[] camera = identity();
		float[] projection = squareProjection();
		float[] ndc = new float[2];
		float[] behind = { 0.0F, 0.0F, -1.0F };
		float[] outLeft = SkyRaySpace.direction(camera, projection, 1.5F, 0.0F, null);
		assertAll(
			() -> assertFalse(SkyRaySpace.projectsTo(camera, projection, behind, ndc)),
			() -> assertFalse(SkyRaySpace.projectsTo(camera, projection, outLeft, ndc))
		);
	}
}
