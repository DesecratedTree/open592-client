package com.jagex.game.runetek5.client.hd.ground;

import static com.jagex.game.runetek5.client.hd.ground.HdMaterialMap.ANY_ART;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jagex.game.runetek5.config.material.MaterialType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("material map")
class HdMaterialMapTest {

	@Test
	@DisplayName("materials take cells in first-seen order and keep them")
	void register_shouldAssignStableCells() {
		HdMaterialMap map = new HdMaterialMap();
		int first = map.register(17, material(40, 90), ANY_ART);
		int second = map.register(3, material(0, 0), ANY_ART);
		int again = map.register(17, material(255, 255), ANY_ART);

		assertAll(
			() -> assertEquals(0, first),
			() -> assertEquals(1, second, "the next plain cell, not the material id"),
			() -> assertEquals(first, again, "a material keeps its cell"),
			() -> assertEquals(0, map.cellOf(17)),
			() -> assertEquals(1, map.cellOf(3)),
			() -> assertEquals(HdMaterialMap.NO_CELL, map.cellOf(99)),
			() -> assertEquals(2, map.assigned()),
			() -> assertEquals(2, map.version(), "one bump per material, none for a repeat")
		);
	}

	@Test
	@DisplayName("a material the page cannot fill is never given a cell")
	void register_shouldRefuseAMaterialWithoutArt() {
		HdMaterialMap map = new HdMaterialMap();
		int[] probes = { 0 };
		HdMaterialMap.Art noArt = material -> {
			probes[0]++;
			return false;
		};

		int refused = map.register(7, material(1, 2), noArt);
		int again = map.register(7, material(1, 2), noArt);

		assertAll(
			() -> assertEquals(HdMaterialMap.NO_CELL, refused,
				"a cell nothing can be copied into is a transparent tile, so it is not handed out"),
			() -> assertEquals(HdMaterialMap.NO_CELL, map.cellOf(7)),
			() -> assertEquals(0, map.assigned(), "a refused material takes no capacity"),
			() -> assertEquals(0, map.version(), "and does not force a page rebuild"),
			() -> assertEquals(1, probes[0],
				"the same page asks once, not once per draw of the material")
		);

		// Some other material joining moves the version, which is the page rebuild
		// the refused one gets a fresh answer at.
		map.register(8, material(3, 4), material -> true);
		int retried = map.register(7, material(1, 2), material -> true);
		assertAll(
			() -> assertTrue(retried >= 0, "art that arrives later joins on the next page"),
			() -> assertEquals(retried, map.cellOf(7)),
			() -> assertEquals(2, map.assigned())
		);
	}

	@Test
	@DisplayName("the map carries the stock brightness and blend bytes, not guesses")
	void register_shouldCarryMaterialParameters() {
		HdMaterialMap map = new HdMaterialMap();
		map.register(5, material(64, 128), ANY_ART);

		assertAll(
			() -> assertEquals(64, map.brightnessOf(5)),
			() -> assertEquals(128, map.blendOf(5)),
			() -> assertEquals(0, map.blendOf(6), "a material with no texel blends nothing"),
			() -> assertEquals(128 / 255.0F, HdMaterialMap.blendFraction(map.blendOf(5)))
		);
	}

	@Test
	@DisplayName("a texel packs the cell and both parameters into its channels")
	void pixel_shouldPackCellAndParameters() {
		HdMaterialMap map = new HdMaterialMap();
		map.register(9, material(0x12, 0x34), ANY_ART);

		int pixel = map.pixel(9);
		int absent = map.pixel(10);
		assertAll(
			() -> assertEquals(1, pixel >> 16 & 0xFF, "red is cell + 1"),
			() -> assertEquals(0x12, pixel >> 8 & 0xFF, "green is the brightness byte"),
			() -> assertEquals(0x34, pixel & 0xFF, "blue is the blend byte"),
			() -> assertEquals(0xFF, pixel >>> 24, "alpha is opaque"),
			() -> assertEquals(0, absent >> 16 & 0xFF, "an unmapped material has no cell"),
			() -> assertNotEquals(pixel, absent)
		);
		assertEquals(HdMaterialMap.MATERIALS, map.pixels().length);
	}

	@Test
	@DisplayName("a full page refuses a material while every cell was drawn this frame")
	void register_shouldRefuseWhenNothingIsCold() {
		HdMaterialMap map = new HdMaterialMap();
		for (int material = 0; material < HdWorldAtlas.CAPACITY; material++) {
			assertEquals(material, map.register(material, material(1, 2), ANY_ART));
		}

		assertAll(
			() -> assertTrue(map.full()),
			() -> assertEquals(-1, map.register(HdWorldAtlas.CAPACITY, material(1, 2), ANY_ART),
				"nothing in view may be displaced, so the newcomer waits"),
			() -> assertEquals(0, map.evictions()),
			() -> assertEquals(0, map.cellOf(0), "the first material did not move"),
			() -> assertEquals(HdWorldAtlas.CAPACITY - 1, map.cellOf(HdWorldAtlas.CAPACITY - 1))
		);
	}

	@Test
	@DisplayName("a full page recycles its coldest cell into the newest material")
	void register_shouldRecycleTheColdestCell() {
		HdMaterialMap map = new HdMaterialMap();
		for (int material = 0; material < HdWorldAtlas.CAPACITY; material++) {
			map.register(material, material(1, 2), ANY_ART);
		}
		// A new frame: nothing has been drawn in it, so all 64 are cold and the
		// lowest cell wins the tie.
		map.beginFrame();
		int recycled = map.register(200, material(3, 4), ANY_ART);

		assertAll(
			() -> assertEquals(0, recycled, "the cell that has gone longest without a draw"),
			() -> assertEquals(0, map.cellOf(200)),
			() -> assertEquals(HdMaterialMap.NO_CELL, map.cellOf(0),
				"the displaced material lost its cell"),
			() -> assertEquals(0, map.pixel(0) >> 16 & 0xFF,
				"and its texel no longer points at one"),
			() -> assertEquals(3, map.brightnessOf(200), "the new owner's own parameters"),
			() -> assertEquals(1, map.evictions()),
			() -> assertEquals(HdWorldAtlas.CAPACITY, map.assigned(), "the page stays full"),
			() -> assertEquals(HdWorldAtlas.CAPACITY + 1, map.version(),
				"one bump for the material that joined")
		);
	}

	@Test
	@DisplayName("a material drawn in the current frame is never displaced")
	void register_shouldSpareMaterialsDrawnThisFrame() {
		HdMaterialMap map = new HdMaterialMap();
		for (int material = 0; material < HdWorldAtlas.CAPACITY; material++) {
			map.register(material, material(1, 2), ANY_ART);
		}
		map.beginFrame();
		// This frame draws every material the page holds, then one more arrives.
		for (int material = 0; material < HdWorldAtlas.CAPACITY; material++) {
			map.register(material, material(1, 2), ANY_ART);
		}
		int refused = map.register(200, material(1, 2), ANY_ART);

		assertAll(
			() -> assertEquals(HdMaterialMap.NO_CELL, refused,
				"a newcomer waits rather than displacing a tile in view"),
			() -> assertEquals(0, map.evictions()),
			() -> assertEquals(0, map.cellOf(0), "the coldest material keeps its cell"),
			() -> assertEquals(HdMaterialMap.NO_CELL, map.cellOf(200))
		);
	}

	@Test
	@DisplayName("the frame's most recently drawn material survives a recycling")
	void register_shouldSpareTheMostRecentlyDrawn() {
		HdMaterialMap map = new HdMaterialMap();
		for (int material = 0; material < HdWorldAtlas.CAPACITY; material++) {
			map.register(material, material(1, 2), ANY_ART);
		}
		map.beginFrame();
		map.register(5, material(1, 2), ANY_ART);
		int recycled = map.register(200, material(1, 2), ANY_ART);

		assertAll(
			() -> assertEquals(0, recycled, "a cold cell, not the one just drawn"),
			() -> assertEquals(5, map.cellOf(5), "the material drawn this frame keeps its cell"),
			() -> assertEquals(HdMaterialMap.NO_CELL, map.cellOf(0)),
			() -> assertEquals(1, map.evictions())
		);
	}

	@Test
	@DisplayName("a displaced material takes a cell back when it is drawn again")
	void register_shouldReAdmitADisplacedMaterial() {
		HdMaterialMap map = new HdMaterialMap();
		for (int material = 0; material < HdWorldAtlas.CAPACITY; material++) {
			map.register(material, material(1, 2), ANY_ART);
		}
		map.beginFrame();
		map.register(200, material(1, 2), ANY_ART);
		assertEquals(HdMaterialMap.NO_CELL, map.cellOf(0));

		map.beginFrame();
		int readmitted = map.register(0, material(9, 9), ANY_ART);

		assertAll(
			() -> assertTrue(readmitted >= 0, "a returning material pages again"),
			() -> assertEquals(readmitted, map.cellOf(0)),
			() -> assertEquals(9, map.brightnessOf(0), "with its own parameters"),
			() -> assertEquals(2, map.evictions(), "and takes exactly one cell"),
			() -> assertEquals(HdWorldAtlas.CAPACITY, map.assigned())
		);
	}

	@Test
	@DisplayName("ids a 592 cache cannot address are refused, not wrapped")
	void register_shouldRefuseIdsOutsideTheMap() {
		HdMaterialMap map = new HdMaterialMap();
		assertAll(
			() -> assertEquals(-1, map.register(-1, material(0, 0), ANY_ART)),
			() -> assertEquals(-1, map.register(HdMaterialMap.MATERIALS, material(0, 0), ANY_ART)),
			() -> assertEquals(HdMaterialMap.NO_CELL, map.cellOf(-1)),
			() -> assertEquals(HdMaterialMap.NO_CELL, map.cellOf(HdMaterialMap.MATERIALS)),
			() -> assertFalse(map.full(), "a refused id does not consume a cell")
		);
	}

	@Test
	@DisplayName("the atlas coordinate lands in the middle of the material's texel")
	void atlasCoordinate_shouldHitTheTexelCentre() {
		assertAll(
			() -> assertEquals(0.5F / HdMaterialMap.MATERIALS, HdMaterialMap.atlasCoordinate(0)),
			() -> assertEquals(17.5F / HdMaterialMap.MATERIALS, HdMaterialMap.atlasCoordinate(17)),
			() -> assertEquals((HdMaterialMap.MATERIALS - 0.5F) / HdMaterialMap.MATERIALS,
				HdMaterialMap.atlasCoordinate(HdMaterialMap.MATERIALS - 1))
		);
	}

	private static MaterialType material(int brightness, int blend) {
		MaterialType type = new MaterialType();
		type.aByte87 = (byte) brightness;
		type.aByte90 = (byte) blend;
		return type;
	}
}
