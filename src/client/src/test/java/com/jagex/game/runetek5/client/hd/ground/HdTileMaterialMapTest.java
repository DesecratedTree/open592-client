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
	@DisplayName("texels are row-major, two 16-bit ids per tile")
	void texels_shouldBeRowMajor() {
		HdTileMaterialMap map = new HdTileMaterialMap();
		map.set(2, 1, 7, 8);

		int at = (1 * HdTileMaterialMap.SIZE + 2) * HdTileMaterialMap.STRIDE;
		byte[] bytes = map.bytes();
		assertAll(
			() -> assertEquals(7, bytes[at] & 0xFF, "the primary's low byte"),
			() -> assertEquals(0, bytes[at + 1] & 0xFF, "the primary's high byte"),
			() -> assertEquals(8, bytes[at + 2] & 0xFF, "the mate's low byte"),
			() -> assertEquals(0, bytes[at + 3] & 0xFF, "the mate's high byte"),
			() -> assertEquals(HdTileMaterialMap.TILES * HdTileMaterialMap.STRIDE, bytes.length)
		);
	}

	@Test
	@DisplayName("a material id past a byte keeps its own value, not a truncated one")
	void idsPastAByte_shouldSurviveThePacking() {
		HdTileMaterialMap map = new HdTileMaterialMap();
		// A live 592 region draws these three: 408 is the material whose tile lost
		// its texture when the map stored ids in one byte each - 408 became 152,
		// and the fragment sampled material 152's cell instead.
		map.set(4, 6, 408, 181);

		assertAll(
			() -> assertEquals(408, map.primary(4, 6)),
			() -> assertEquals(181, map.mate(4, 6)),
			() -> assertEquals(HdTileMaterialMap.FULL_WEIGHT, map.weight(4, 6)),
			() -> assertTrue(map.blended()),
			() -> assertEquals(65536, HdTileMaterialMap.MATERIAL_LIMIT, "the packing is 16-bit")
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

	@Test
	@DisplayName("an id the packing cannot hold reads as no material, not as another id")
	void outOfRangeId_shouldReadAsNone() {
		HdTileMaterialMap map = new HdTileMaterialMap();
		map.set(0, 1, 40000, -1);

		assertAll(
			() -> assertEquals(40000, map.primary(0, 1), "a 16-bit id is stored whole"),
			() -> assertEquals(-1, map.mate(0, 1))
		);
	}
}
