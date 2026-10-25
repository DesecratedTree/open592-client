package com.jagex.game.runetek5.client.hd.sway;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Which loc shapes the two sway rows may displace.
 *
 * <p>This is the whole "is it a plant" answer the renderer has, so it is pinned
 * shape by shape rather than sampled: the ground decoration and its diagonal
 * twin are plants, everything else the loc system can submit is not, and no
 * shape outside the set can ever be displaced by a settings change.
 */
@DisplayName("592HD vegetation classification")
class HdVegetationTest {

	@Test
	@DisplayName("the ground decoration and its diagonal twin are the marked shapes")
	void groundDecoration_shouldBeVegetation() {
		assertAll(
			() -> assertEquals(10, HdVegetation.GROUND_DECOR_SHAPE),
			() -> assertEquals(11, HdVegetation.GROUND_DECOR_DIAGONAL_SHAPE),
			() -> assertEquals(HdVegetation.GROUND_DECOR, HdVegetation.ofLocShape(10)),
			() -> assertEquals(HdVegetation.GROUND_DECOR, HdVegetation.ofLocShape(11),
				"the client draws shape 11 with shape 10's model, so it is the same plant"),
			() -> assertTrue(HdVegetation.isVegetation(HdVegetation.GROUND_DECOR))
		);
	}

	@Test
	@DisplayName("every other loc shape is untouched, including the ones that are nearly plants")
	void otherShapes_shouldNotBeVegetation() {
		for (int shape = 0; shape <= 22; shape++) {
			if (shape == HdVegetation.GROUND_DECOR_SHAPE
				|| shape == HdVegetation.GROUND_DECOR_DIAGONAL_SHAPE) {
				continue;
			}
			assertEquals(HdVegetation.NONE, HdVegetation.ofLocShape(shape),
				"shape " + shape + " is scenery, not ground decoration");
		}
		assertAll(
			() -> assertEquals(HdVegetation.NONE, HdVegetation.ofLocShape(-1)),
			() -> assertEquals(HdVegetation.NONE, HdVegetation.ofLocShape(90)),
			() -> assertFalse(HdVegetation.isVegetation(HdVegetation.NONE))
		);
	}
}
