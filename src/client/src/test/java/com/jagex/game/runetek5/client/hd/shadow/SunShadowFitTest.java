package com.jagex.game.runetek5.client.hd.shadow;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * The shadow fit is where a flip or a roll silently ruins a whole frame, so it
 * is checked point by point: the centre of the volume lands in the middle of
 * the texture, "toward the sun" is nearer the light (smaller depth), the cross
 * axes are perpendicular and correctly scaled, and the volume is square.
 */
@DisplayName("sun shadow matrix fit")
class SunShadowFitTest {

	private static final float TOLERANCE = 1.0E-5F;

	/** Depth spans a 2 * radius volume, so the near plane costs ~1/20000. */
	private static final float DEPTH_TOLERANCE = 1.0E-3F;

	@Test
	@DisplayName("the volume centre lands in the middle of the texture")
	void centre_shouldProjectToHalf() {
		float[] matrix = SunShadowFit.lightMatrix(0.0F, 0.0F, 1.0F, 3.0F, 4.0F, 5.0F, 10.0F);
		float[] projected = project(matrix, 3.0F, 4.0F, 5.0F);

		assertAll(
			() -> assertEquals(0.5F, projected[0], TOLERANCE, "u"),
			() -> assertEquals(0.5F, projected[1], TOLERANCE, "v"),
			() -> assertEquals(0.5F, projected[2], DEPTH_TOLERANCE, "depth")
		);
	}

	@Test
	@DisplayName("toward the sun is nearer the light, so smaller depth")
	void towardTheSun_shouldBeShallower() {
		float[] matrix = SunShadowFit.lightMatrix(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 10.0F);
		float centre = project(matrix, 0.0F, 0.0F, 0.0F)[2];
		float towardSun = project(matrix, 0.0F, 0.0F, 5.0F)[2];
		float awayFromSun = project(matrix, 0.0F, 0.0F, -5.0F)[2];

		assertAll(
			() -> assertEquals(0.5F, centre, DEPTH_TOLERANCE),
			() -> assertEquals(0.25F, towardSun, DEPTH_TOLERANCE),
			() -> assertEquals(0.75F, awayFromSun, DEPTH_TOLERANCE),
			() -> assertTrue(towardSun < centre && centre < awayFromSun, "depth must grow away from the sun")
		);
	}

	@Test
	@DisplayName("the across axis is square, centred and correctly scaled")
	void acrossAxis_shouldSpanMinusOneToPlusOne() {
		float[] matrix = SunShadowFit.lightMatrix(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 10.0F);
		float[] positiveX = project(matrix, 10.0F, 0.0F, 0.0F);
		float[] negativeX = project(matrix, -10.0F, 0.0F, 0.0F);
		float[] positiveY = project(matrix, 0.0F, 10.0F, 0.0F);
		float[] negativeY = project(matrix, 0.0F, -10.0F, 0.0F);

		assertAll(
			() -> assertEquals(1.0F, positiveX[0] + negativeX[0], TOLERANCE, "x is centred"),
			() -> assertEquals(1.0F, positiveY[1] + negativeY[1], TOLERANCE, "y is centred"),
			() -> assertEquals(1.0F, Math.abs(positiveX[0] - negativeX[0]), TOLERANCE, "radius maps to the full width"),
			() -> assertEquals(1.0F, positiveY[1] - negativeY[1], TOLERANCE, "radius maps to the full height"),
			() -> assertTrue(Math.abs(positiveY[0] - 0.5F) < TOLERANCE, "y does not leak into u"),
			() -> assertTrue(Math.abs(positiveX[1] - 0.5F) < TOLERANCE, "x does not leak into v")
		);
	}

	@Test
	@DisplayName("the light basis is orthonormal for every sun direction")
	void basis_shouldBeOrthonormal() {
		float[][] directions = {
			{ 0.0F, 0.0F, 1.0F },
			{ 0.0F, 1.0F, 0.0F },
			{ 0.6F, 0.8F, 0.0F },
			{ -0.5F, 0.5F, -0.7F },
			{ 0.0F, -1.0F, 0.0F }
		};
		for (float[] direction : directions) {
			// The basis lives in the clip matrix; the light matrix carries the
			// 0..1 texture bias on top and would scale it by half.
			float[] matrix = SunShadowFit.clipMatrix(direction[0], direction[1], direction[2],
				0.0F, 0.0F, 0.0F, 8.0F);
			// The orthographic scale is folded into these rows, so normalise
			// them first: what must hold is that they are mutually perpendicular,
			// i.e. the light's basis has no roll or shear.
			float[] across = unit(matrix[0], matrix[4], matrix[8]);
			float[] up = unit(matrix[1], matrix[5], matrix[9]);
			float[] depth = unit(matrix[2], matrix[6], matrix[10]);
			assertAll(
				() -> assertEquals(1.0F, length(across), 1.0E-4F, "across is unit length"),
				() -> assertEquals(1.0F, length(up), 1.0E-4F, "up is unit length"),
				() -> assertEquals(1.0F, length(depth), 1.0E-4F, "depth is unit length"),
				() -> assertEquals(0.0F, dot(across, up), 1.0E-4F, "across perp up"),
				() -> assertEquals(0.0F, dot(across, depth), 1.0E-4F, "across perp depth"),
				() -> assertEquals(0.0F, dot(up, depth), 1.0E-4F, "up perp depth")
			);
		}
	}

	@ParameterizedTest
	@ValueSource(floats = { 1.0F, 16.0F, 400.0F })
	@DisplayName("a point one radius across sits on the map edge")
	void radius_shouldDefineTheEdge(float radius) {
		float[] matrix = SunShadowFit.lightMatrix(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, radius);
		float[] edge = project(matrix, radius, 0.0F, 0.0F);
		float[] outside = project(matrix, radius * 1.1F, 0.0F, 0.0F);

		assertAll(
			() -> assertTrue(edge[0] >= -TOLERANCE && edge[0] <= 1.0F + TOLERANCE, "edge inside 0..1"),
			() -> assertTrue(outside[0] < 0.0F || outside[0] > 1.0F, "beyond the radius leaves the map")
		);
	}

	@Test
	@DisplayName("an upside-down sun flips the depth ordering")
	void sunBehind_shouldFlipDepth() {
		float[] fromAbove = SunShadowFit.lightMatrix(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 10.0F);
		float[] fromBelow = SunShadowFit.lightMatrix(0.0F, 0.0F, -1.0F, 0.0F, 0.0F, 0.0F, 10.0F);

		assertAll(
			() -> assertTrue(project(fromAbove, 0.0F, 0.0F, 5.0F)[2] < 0.5F, "sun above: +z is shallow"),
			() -> assertTrue(project(fromBelow, 0.0F, 0.0F, 5.0F)[2] > 0.5F, "sun below: +z is deep")
		);
	}

	@Test
	@DisplayName("a zero sun direction falls back to straight up instead of a NaN matrix")
	void zeroSun_shouldNotProduceNaN() {
		float[] matrix = SunShadowFit.lightMatrix(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 10.0F);
		for (float value : matrix) {
			assertTrue(!Float.isNaN(value) && !Float.isInfinite(value), "matrix holds a non-finite value");
		}
	}

	@Test
	@DisplayName("the depth bias is positive and shrinks as the volume grows")
	void bias_shouldBePositiveAndScaleWithRadius() {
		assertAll(
			() -> assertTrue(SunShadowFit.depthBias(1.0F) > 0.0F),
			() -> assertTrue(SunShadowFit.depthBias(400.0F) < SunShadowFit.depthBias(16.0F)),
			() -> assertTrue(SunShadowFit.depthBias(1.0E9F) >= 1.0E-4F, "never reaches zero")
		);
	}

	private static float[] project(float[] matrix, float x, float y, float z) {
		float[] clip = SunShadowFit.transform(matrix, x, y, z);
		float w = clip[3] == 0.0F ? 1.0F : clip[3];
		return new float[] { clip[0] / w, clip[1] / w, clip[2] / w };
	}

	private static float[] unit(float x, float y, float z) {
		float length = (float) Math.sqrt(x * x + y * y + z * z);
		return new float[] { x / length, y / length, z / length };
	}

	private static float length(float[] vector) {
		return (float) Math.sqrt(dot(vector, vector));
	}

	private static float dot(float[] a, float[] b) {
		return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
	}
}
