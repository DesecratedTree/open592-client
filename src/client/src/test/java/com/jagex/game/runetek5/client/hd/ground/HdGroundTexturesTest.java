package com.jagex.game.runetek5.client.hd.ground;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ground textures")
class HdGroundTexturesTest {

	@Test
	@DisplayName("a textured layer resolves its own material and repeat length")
	void layer_shouldCarryItsMaterial() {
		HdGroundTextures textures = HdGroundTextures.of(true, 17, true, 4, 2.0F, HdTerrainBlend.OFF);

		assertAll(
			() -> assertTrue(textures.active()),
			() -> assertEquals(17, textures.material()),
			// A tile is 128 scene units whatever the mesh's vertex count: the tile
			// buffer is scaled to that size, not sized by it.
			() -> assertEquals(128.0F, textures.tileSize(), "128 scene units per tile"),
			// The layer's texture scale is already in scene units per repeat: the
			// stock draw divides the texture matrix by it. Multiplying by the
			// tile size made one repeat 128 tiles wide, so every tile sampled a
			// fraction of a texel and the atlas drew a flat colour.
			() -> assertEquals(2.0F, textures.repeat(), "the stock texture scale, in scene units"),
			() -> assertEquals(0.0F, textures.blendStrength(), "the blend row is off")
		);
	}

	@Test
	@DisplayName("a stock texture scale of 128 is one repeat per tile")
	void stockScale_shouldBeOneRepeatPerTile() {
		HdGroundTextures textures = HdGroundTextures.of(true, 17, true, 128, 128.0F, HdTerrainBlend.OFF);

		assertAll(
			() -> assertEquals(128.0F, textures.repeat(),
				"FluType's default 128 is the stock one-repeat-per-tile scale"),
			() -> assertEquals(textures.tileSize(), textures.repeat(),
				"the tile and the repeat are the same distance at the stock scale")
		);
	}

	@Test
	@DisplayName("the blend row's strength travels with the draw")
	void blend_shouldCarryTheMaskStrength() {
		HdGroundTextures textures = HdGroundTextures.of(true, 3, true, 4, 1.0F, HdTerrainBlend.of(true));
		assertEquals(HdTerrainBlend.STRENGTH, textures.blendStrength());
	}

	@Test
	@DisplayName("the rows off, an untextured layer, or a bad tile size is the identity")
	void invalidInputs_shouldBeTheIdentity() {
		assertAll(
			() -> assertSame(HdGroundTextures.OFF, HdGroundTextures.of(false, 17, true, 4, 1.0F, HdTerrainBlend.OFF)),
			() -> assertSame(HdGroundTextures.OFF, HdGroundTextures.of(true, 17, false, 4, 1.0F, HdTerrainBlend.OFF)),
			() -> assertSame(HdGroundTextures.OFF, HdGroundTextures.of(true, -1, true, 4, 1.0F, HdTerrainBlend.OFF)),
			() -> assertSame(HdGroundTextures.OFF, HdGroundTextures.of(true, 17, true, 0, 1.0F, HdTerrainBlend.OFF)),
			() -> assertSame(HdGroundTextures.OFF, HdGroundTextures.of(true, 17, true, 4, 0.0F, HdTerrainBlend.OFF)),
			() -> assertFalse(HdGroundTextures.OFF.active())
		);
	}

	@Test
	@DisplayName("a null blend is the off blend, not an exception")
	void nullBlend_shouldBeOff() {
		HdGroundTextures textures = HdGroundTextures.of(true, 17, true, 4, 1.0F, null);
		assertEquals(0.0F, textures.blendStrength());
		assertTrue(textures.active(), "the atlas still draws its own material");
	}
}
