package com.jagex.game.runetek5.client.hd.water;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jagex.game.runetek5.client.hd.ground.HdGroundWater;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("which ground layers the HD water surface may own")
class HdWaterGroundGateTest {

	/** An underwater tile: the stock depth byte is filled in for these regions. */
	private static final HdGroundWater WET = HdGroundWater.of(200);

	@Test
	@DisplayName("the row must be on before any layer is taken")
	void off_shouldOwnNothing() {
		assertAll(
			() -> assertFalse(HdWaterGroundGate.owns(false, true, WET),
				"the row off is the stock draw, even for a water material"),
			() -> assertFalse(HdWaterGroundGate.owns(false, false, WET),
				"the row off is the stock draw for a submerged tile too"),
			() -> assertFalse(HdWaterGroundGate.owns(false, true, HdGroundWater.DRY))
		);
	}

	@Test
	@DisplayName("a lake tile is water by its material, because its depth byte is zero")
	void lakeTile_shouldBeOwnedByItsMaterial() {
		assertAll(
			() -> assertEquals(0, HdGroundWater.DRY.depth(),
				"an ordinary region leaves every tile's depth byte at zero"),
			() -> assertTrue(HdWaterGroundGate.owns(true, true, HdGroundWater.DRY),
				"the material is the only fact a lake tile has"),
			() -> assertTrue(HdWaterGroundGate.owns(true, false, WET),
				"an underwater tile is water by its depth byte alone")
		);
	}

	@Test
	@DisplayName("dry ground keeps the client's own draw")
	void dryGround_shouldNeverBeTaken() {
		assertAll(
			() -> assertFalse(HdWaterGroundGate.owns(true, false, HdGroundWater.DRY),
				"owning a dry tile paints it with the water maps instead of its atlas"),
			() -> assertFalse(HdWaterGroundGate.owns(true, false, null),
				"no measure is dry, not a reason to take the layer")
		);
	}

	@Test
	@DisplayName("the stock slots that name water are the four water types, and only those")
	void waterSlots_shouldBeTheWaterTypes() {
		assertAll(
			() -> assertTrue(HdWaterGate.isWaterMaterial(HdWaterGate.STILL_SLOT), "still water"),
			() -> assertTrue(HdWaterGate.isWaterMaterial(HdWaterGate.STILL_FALLBACK_SLOT),
				"the manager's downgrade of the reflective type"),
			() -> assertTrue(HdWaterGate.isWaterMaterial(HdWaterGate.REFLECTIVE_SLOT), "reflective water"),
			() -> assertTrue(HdWaterGate.isWaterMaterial(HdWaterGate.BREAKING_SLOT), "breaking water"),
			() -> assertFalse(HdWaterGate.isWaterMaterial(0), "no effect is not water"),
			() -> assertFalse(HdWaterGate.isWaterMaterial(1), "the specular effect is not water"),
			() -> assertFalse(HdWaterGate.isWaterMaterial(2), "the plain texture effect is not water"),
			() -> assertFalse(HdWaterGate.isWaterMaterial(5), "the waterfall effect is not water"),
			() -> assertFalse(HdWaterGate.isWaterMaterial(6)),
			() -> assertFalse(HdWaterGate.isWaterMaterial(7)),
			() -> assertFalse(HdWaterGate.isWaterMaterial(-1))
		);
	}
}
