package com.jagex.game.runetek5.client.hd.ground;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("tile material map")
class HdTileMaterialMapTest {

	@Test
	@DisplayName("a two-material tile records both ids and a full weight")
	void twoMaterials_shouldCarryThePair() {
		HdTileMaterialMap map = new HdTileMaterialMap();
		map.set(3, 5, 17, 42);

		assertAll(
			() -> assertEquals(17, map.primary(3, 5)),
			() -> assertEquals(42, map.mate(3, 5)),
			() -> assertEquals(HdTileMaterialMap.FULL_WEIGHT, map.weight(3, 5)),
			() -> assertTrue(map.blended())
		);
	}

	@Test
	@DisplayName("a one-material tile records no mate and no weight")
	void oneMaterial_shouldNotBlend() {
		HdTileMaterialMap map = new HdTileMaterialMap();
		map.set(1, 2, 9, -1);

		assertAll(
			() -> assertEquals(9, map.primary(1, 2)),
			() -> assertEquals(-1, map.mate(1, 2)),
			() -> assertEquals(0, map.weight(1, 2)),
			() -> assertFalse(map.blended())
		);
	}

	@Test
	@DisplayName("a tile whose layers share a material has nothing to mix")
	void repeatedMaterial_shouldNotBlend() {
		HdTileMaterialMap map = new HdTileMaterialMap();
		map.set(0, 0, 4, 4);

		assertAll(
			() -> assertEquals(4, map.primary(0, 0)),
			() -> assertEquals(4, map.mate(0, 0)),
			() -> assertEquals(0, map.weight(0, 0), "the same texture twice is no blend")
		);
	}

	@Test
	@DisplayName("texels are row-major, four bytes per tile")
	void texels_shouldBeRowMajor() {
		HdTileMaterialMap map = new HdTileMaterialMap();
		map.set(2, 1, 7, 8);

		int at = (1 * HdTileMaterialMap.SIZE + 2) * HdTileMaterialMap.STRIDE;
		byte[] bytes = map.bytes();
		assertAll(
			() -> assertEquals(7, bytes[at] & 0xFF),
			() -> assertEquals(8, bytes[at + 1] & 0xFF),
			() -> assertEquals(HdTileMaterialMap.FULL_WEIGHT, bytes[at + 2] & 0xFF),
			() -> assertEquals(0, bytes[at + 3] & 0xFF, "the flags byte is reserved"),
			() -> assertEquals(HdTileMaterialMap.TILES * HdTileMaterialMap.STRIDE, bytes.length)
		);
	}

	@Test
	@DisplayName("a tile off the map is written nowhere and reads as empty")
	void outside_shouldBeIgnored() {
		HdTileMaterialMap map = new HdTileMaterialMap();
		map.set(-1, 0, 1, 2);
		map.set(HdTileMaterialMap.SIZE, 0, 1, 2);

		assertAll(
			() -> assertEquals(-1, map.primary(-1, 0)),
			() -> assertEquals(-1, map.primary(HdTileMaterialMap.SIZE, 0)),
			() -> assertEquals(0, map.weight(HdTileMaterialMap.SIZE, 0)),
			() -> assertFalse(map.blended(), "nothing was written")
		);
	}
}
