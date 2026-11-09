package com.jagex.game.runetek5.client.hd.water;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.image.BufferedImage;

import com.jagex.game.runetek5.client.hd.content.HdContent;
import com.jagex.game.runetek5.client.hd.shader.HdShaderSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The water maps' decode: a set that cannot be read must be absent rather than
 * a flat stand-in, and a downscale must average instead of point sampling -
 * aliasing baked into level 0 is the grain no later filtering removes.
 */
@DisplayName("HD water maps")
class HdWaterMapsTest {

	@Test
	@DisplayName("the shipped size and file names are 634HD's own")
	void constants_shouldMatchUpstream() {
		assertAll(
			() -> assertEquals(128, HdWaterMaps.SIZE),
			() -> assertEquals("flow", HdWaterMaps.FOLDER),
			() -> assertEquals("water_normal_map_1.png", HdWaterMaps.NORMAL_FILE),
			() -> assertEquals("water_foam.png", HdWaterMaps.FOAM_FILE),
			() -> assertEquals("water_flow_map.png", HdWaterMaps.FLOW_FILE)
		);
	}

	@Test
	@DisplayName("the water map units are not a unit another pass binds")
	void units_shouldNotCollideWithAnotherPass() {
		int[] taken = { 0, 1, HdShaderSource.SAMPLER_UNIT_WORLD_TEXTURES,
			HdShaderSource.SAMPLER_UNIT_MATERIAL_MAPS, HdShaderSource.SAMPLER_UNIT_TILE_MATERIALS,
			HdShaderSource.SAMPLER_UNIT_MATERIAL_NORMAL, HdShaderSource.SAMPLER_UNIT_MATERIAL_ORM,
			HdShaderSource.SAMPLER_UNIT_MATERIAL_EMISSIVE, HdShaderSource.SAMPLER_UNIT_MATERIAL_HEIGHT };
		assertAll(
			() -> assertNotEquals(HdWaterSource.WATER_NORMAL_UNIT, HdWaterSource.WATER_FOAM_UNIT,
				"the maps need their own units"),
			() -> assertNotEquals(HdWaterSource.WATER_FLOW_UNIT, HdWaterSource.WATER_NORMAL_UNIT,
				"the flow map needs its own unit"),
			() -> assertNotEquals(HdWaterSource.WATER_FLOW_UNIT, HdWaterSource.WATER_FOAM_UNIT,
				"the flow map needs its own unit"),
			() -> assertTrue(HdWaterSource.WATER_FLOW_UNIT > HdShaderSource.SAMPLER_UNIT_MATERIAL_HEIGHT,
				"the flow map lives above the material range as well: " + HdWaterSource.WATER_FLOW_UNIT),
			() -> assertTrue(HdWaterSource.WATER_NORMAL_UNIT > HdShaderSource.SAMPLER_UNIT_MATERIAL_HEIGHT,
				"a water layer also binds its material, so the maps live above that range: "
					+ HdWaterSource.WATER_NORMAL_UNIT),
			() -> {
				for (int unit : taken) {
					assertNotEquals(unit, HdWaterSource.WATER_NORMAL_UNIT,
						"the ripple map must not share unit " + unit + " with another pass");
					assertNotEquals(unit, HdWaterSource.WATER_FOAM_UNIT,
						"the foam mask must not share unit " + unit + " with another pass");
					assertNotEquals(unit, HdWaterSource.WATER_FLOW_UNIT,
						"the flow map must not share unit " + unit + " with another pass");
				}
			}
		);
	}

	@Test
	@DisplayName("the shipped flow map carries a current, not a blank sheet")
	void shippedFlowMap_shouldCarryACurrent() {
		HdWaterMaps maps = HdWaterMaps.load(HdContent.loadDefault());

		assertNotNull(maps, "the build must ship 634hd/materials/flow's three maps");
		int[] flow = maps.flow();
		int deviating = 0;
		int strongest = 0;
		java.util.Set<Integer> velocities = new java.util.HashSet<Integer>();
		for (int texel : flow) {
			int red = texel >> 16 & 0xFF;
			int green = texel >> 8 & 0xFF;
			int deviation = Math.abs(red - 128) + Math.abs(green - 128);
			if (deviation > 8) {
				deviating++;
			}
			strongest = Math.max(strongest, deviation);
			velocities.add(Integer.valueOf(texel & 0xFFFFFF));
		}
		int texels = flow.length;
		int moving = deviating;
		int peak = strongest;
		int distinct = velocities.size();

		assertAll(
			() -> assertEquals(HdWaterMaps.SIZE * HdWaterMaps.SIZE, texels, "one texel a world position"),
			// A map that failed to decode, or an all-mid-grey stand-in, would
			// advect nothing and the ripples would stay still on the current.
			() -> assertTrue(moving > texels * 3 / 4,
				"nearly every texel must carry a velocity: " + moving + " of " + texels),
			() -> assertTrue(peak > 64, "and with a real magnitude: " + peak),
			() -> assertTrue(distinct > 1000, "a current is not one value: " + distinct + " velocities")
		);
	}

	@Test
	@DisplayName("absent art is null, never a blank map")
	void decode_shouldRejectAbsentArt() {
		assertAll(
			() -> assertNull(HdWaterMaps.decode(null)),
			() -> assertNull(HdWaterMaps.decode(new byte[0])),
			() -> assertNull(HdWaterMaps.decode(new byte[] { 1, 2, 3, 4 }))
		);
	}

	@Test
	@DisplayName("a downscale averages the texels it covers instead of keeping one")
	void resample_shouldAverage() {
		// A 4x4 with one bright and three dark texels per 2x2 target block: point
		// sampling keeps the bright one, averaging turns it into a quarter.
		BufferedImage image = new BufferedImage(4, 4, BufferedImage.TYPE_INT_ARGB);
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				boolean bright = (x & 1) == 0 && (y & 1) == 0;
				image.setRGB(x, y, (0xFF << 24) | (bright ? 0xFFFFFF : 0));
			}
		}
		int[] texels = HdWaterMaps.resample(image, 2);

		assertAll(
			() -> assertEquals(4, texels.length),
			() -> assertEquals(0xFF << 24 | 0x404040, texels[0], "#404040: one of four texels is white"),
			() -> assertEquals(texels[0], texels[3], "every block averages the same way")
		);
	}

	@Test
	@DisplayName("an opaque map stays opaque, and a 1:1 map is copied")
	void resample_shouldKeepAlpha() {
		BufferedImage image = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
		image.setRGB(0, 0, 0x123456);
		image.setRGB(1, 0, 0x123456);
		image.setRGB(0, 1, 0x123456);
		image.setRGB(1, 1, 0x123456);

		int[] texels = HdWaterMaps.resample(image, 2);

		assertAll(
			() -> assertArrayEquals(new int[] { 0xFF123456, 0xFF123456, 0xFF123456, 0xFF123456 }, texels),
			() -> assertTrue((texels[0] >>> 24) == 0xFF)
		);
	}
}
