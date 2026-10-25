package com.jagex.game.runetek5.client.hd.ground;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jagex.game.runetek5.client.hd.shadow.SunShadowFit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The terrain's two transforms as pure maths: no GL, no caches, no ground mesh.
 *
 * <p>The terrain pass cannot be given a camera - it loads an identity modelview
 * and draws tile-space vertices under its own tile-window ortho - so the two
 * matrices it hands the lighting program are the only thing standing between a
 * lit world and a lit world with a seam. A wrong scale or a missing window
 * origin still lights something; it just lights the wrong place, and a shadow
 * lands a tile off. The driver here cannot run the pixel proof (its native GL
 * faults during context work), so the composition is pinned instead: the same
 * world point, expressed once as a model vertex and once as a tile vertex, must
 * reach the same scene position, the same eye position and the same shadow
 * texel. Those three are exactly what the fragment stage reads.
 */
@DisplayName("592HD ground tile space")
class GroundTileSpaceTest {

	private static final float EPSILON = 0.001F;

	/** A camera matrix in the shape {@code JavaMatrix.method5161} produces. */
	private static float[] view(float yaw) {
		float cos = (float) Math.cos(yaw);
		float sin = (float) Math.sin(yaw);
		// rotate about y, then translate the eye out of the world.
		return new float[] {
			cos, 0.0F, -sin, 0.0F,
			0.0F, 1.0F, 0.0F, 0.0F,
			sin, 0.0F, cos, 0.0F,
			-64.0F * cos - 64.0F * sin, -320.0F, 64.0F * sin - 64.0F * cos, 1.0F
		};
	}

	@Test
	@DisplayName("a tile vertex lands where a model at the same place lands")
	void tileToScene_shouldScaleTilesIntoSceneUnits() {
		float[] matrix = GroundTileSpace.tileToScene(new float[16], 8, 10, 20);

		float[] origin = GroundTileSpace.transform(matrix, 0.0F, 40.0F, 0.0F);
		float[] middle = GroundTileSpace.transform(matrix, 8.0F, 41.0F, 8.0F);
		float[] corner = GroundTileSpace.transform(matrix, 4.0F, 42.0F, 4.0F);

		assertAll(
			// The window's own first tile is the scene origin of this pass, so a
			// vertex at the window origin is at -origin * 128, not at 0.
			() -> assertPoint("window origin", origin, -1280.0F, 40.0F, -2560.0F),
			() -> assertPoint("one tile across", middle, -1152.0F, 41.0F, -2432.0F),
			() -> assertPoint("half a tile across", corner, -1216.0F, 42.0F, -2496.0F),
			// Height is already a scene value: 128 units per tile is horizontal.
			() -> assertEquals(42.0F, corner[1], EPSILON)
		);
	}

	@Test
	@DisplayName("at the live pass's resolution tile-to-scene is the identity")
	void tileToScene_shouldBeTheIdentityForTheLiveGround() {
		// GlToolkit.createGround builds the pass with 128 vertices per tile, and
		// the live draw (GlGroundLayer.method1462) submits the whole scene from
		// tile 0. Its buffer therefore already holds the coordinates a model at
		// that spot reports, so the seam must hand the shader those very numbers
		// rather than a scaled copy of them.
		float[] matrix = GroundTileSpace.tileToScene(new float[16], 128, 0, 0);
		for (int index = 0; index < 16; index++) {
			final int at = index;
			assertEquals(at % 5 == 0 ? 1.0F : 0.0F, matrix[at], EPSILON, "element " + at);
		}
	}

	@Test
	@DisplayName("every ground quality scales the same way: one tile is 128 scene units")
	void tileToScene_shouldHoldForEveryGroundQuality() {
		for (int units = 1; units <= 16; units <<= 1) {
			float[] matrix = GroundTileSpace.tileToScene(new float[16], units, 0, 0);
			float[] across = GroundTileSpace.transform(matrix, (float) units, 0.0F, 0.0F);
			float[] down = GroundTileSpace.transform(matrix, 0.0F, 0.0F, (float) units);
			final int tileUnits = units;
			assertAll(
				() -> assertEquals(128.0F, across[0], EPSILON, "x at tileUnits " + tileUnits),
				() -> assertEquals(128.0F, down[2], EPSILON, "z at tileUnits " + tileUnits)
			);
		}
	}

	@Test
	@DisplayName("scene-to-eye is rotX(180) times the camera, not the camera alone")
	void sceneToEye_shouldApplyTheGroundsFlip() {
		float[] camera = view(0.0F);
		float[] flipped = GroundTileSpace.sceneToEye(camera, new float[16]);

		// The toolkit's method1952 loads identity, rotX(180), then the camera.
		// The flip negates the second and third rows of the product.
		assertAll(
			() -> assertEquals(camera[0], flipped[0], EPSILON),
			() -> assertEquals(-camera[1], flipped[1], EPSILON, "row 1 is negated"),
			() -> assertEquals(-camera[2], flipped[2], EPSILON, "row 2 is negated"),
			() -> assertEquals(camera[3], flipped[3], EPSILON),
			() -> assertEquals(-camera[13], flipped[13], EPSILON, "the eye's y flips"),
			() -> assertEquals(-camera[14], flipped[14], EPSILON, "and its z")
		);
	}

	@Test
	@DisplayName("the same world point takes the same eye position down both paths")
	void eyePosition_shouldAgreeAcrossTheSeam() {
		int tileUnits = 8;
		int originX = 12;
		int originZ = 30;
		float[] camera = view(0.7F);
		float[] tileToScene = GroundTileSpace.tileToScene(new float[16], tileUnits, originX, originZ);
		float[] sceneToEye = GroundTileSpace.sceneToEye(camera, new float[16]);

		// A point 2.5 tiles into the window, one tile up. As a model vertex the
		// model matrix is the tile-to-scene mapping; as a tile vertex it is the
		// raw buffer coordinate.
		float localX = 2.5F * tileUnits;
		float localY = 128.0F;
		float localZ = 1.0F * tileUnits;
		float[] world = GroundTileSpace.transform(tileToScene, localX, localY, localZ);
		float[] groundEye = GroundTileSpace.transform(sceneToEye, world[0], world[1], world[2]);
		// The model path: modelview * model vertex, where the modelview is the
		// camera under the same flip and the model matrix is tileToScene.
		float[] flippedCamera = GroundTileSpace.multiply(flip180(), camera, new float[16]);
		float[] modelEye = GroundTileSpace.transform(flippedCamera, world[0], world[1], world[2]);

		assertAll(
			() -> assertEquals(world[0], 12.0F * 128.0F * -1.0F + 2.5F * 128.0F, EPSILON,
				"scene x is the window origin plus the tile offset"),
			() -> assertPoint("eye position", groundEye, modelEye[0], modelEye[1], modelEye[2])
		);
	}

	@Test
	@DisplayName("a shadow crossing the model/ground boundary is continuous")
	void shadowProjection_shouldNotJumpAcrossTheSeam() {
		int tileUnits = 8;
		float[] tileToScene = GroundTileSpace.tileToScene(new float[16], tileUnits, 0, 0);
		float[] light = SunShadowFit.lightMatrix(0.0F, 0.6F, 0.8F, 64.0F, 0.0F, 64.0F, 400.0F);

		// The last sub-tile of the tile before the boundary and the first of the
		// tile after it are one unit apart on the ground. Nothing between them
		// may be skipped, so the shadow texel may only move by the projection of
		// one scene unit.
		float[] before = shadowOf(light, tileToScene, tileUnits - 1.0F, 0.0F);
		float[] across = shadowOf(light, tileToScene, tileUnits, 0.0F);
		float[] after = shadowOf(light, tileToScene, tileUnits + 1.0F, 0.0F);
		float stepBefore = distance(before, across);
		float stepAfter = distance(across, after);

		// One scene unit on the ground, independently: tile 1 begins at x = 128.
		float[] expected = unit(GroundTileSpace.transform(light, 128.0F, 0.0F, 0.0F));

		assertAll(
			() -> assertEquals(stepBefore, stepAfter, 0.0005F, "the seam is not a step"),
			() -> assertTrue(stepBefore > 0.0F, "the samples must move, or this proves nothing"),
			// A model matrix that places a model at that spot produces the same
			// texel, because both paths hand the light matrix the same world point.
			() -> assertPoint("shadow texel at the boundary", across,
				expected[0], expected[1], expected[2])
		);
	}

	private static float[] flip180() {
		return new float[] {
			1.0F, 0.0F, 0.0F, 0.0F,
			0.0F, -1.0F, 0.0F, 0.0F,
			0.0F, 0.0F, -1.0F, 0.0F,
			0.0F, 0.0F, 0.0F, 1.0F
		};
	}

	/** A tile vertex through tile-to-scene and then the sun's light matrix. */
	private static float[] shadowOf(float[] light, float[] tileToScene, float tileX, float tileZ) {
		float[] world = GroundTileSpace.transform(tileToScene, tileX, 0.0F, tileZ);
		return unit(GroundTileSpace.transform(light, world[0], world[1], world[2]));
	}

	private static float[] unit(float[] homogeneous) {
		float w = homogeneous[3] == 0.0F ? 1.0F : homogeneous[3];
		return new float[] { homogeneous[0] / w, homogeneous[1] / w, homogeneous[2] / w };
	}

	private static float distance(float[] a, float[] b) {
		float dx = a[0] - b[0];
		float dy = a[1] - b[1];
		float dz = a[2] - b[2];
		return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	private static void assertPoint(String what, float[] actual, float x, float y, float z) {
		assertAll(
			() -> assertEquals(x, actual[0], EPSILON, what + " x"),
			() -> assertEquals(y, actual[1], EPSILON, what + " y"),
			() -> assertEquals(z, actual[2], EPSILON, what + " z")
		);
	}
}
