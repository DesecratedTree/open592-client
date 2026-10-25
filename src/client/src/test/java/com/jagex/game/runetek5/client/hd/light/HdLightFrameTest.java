package com.jagex.game.runetek5.client.hd.light;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.client.hd.HdSupport;
import com.jagex.game.runetek5.client.hd.shader.HdShaderSource;

/**
 * The frame the two lighting rows upload, tested without a GL context.
 *
 * <p>What is pinned here is the part that must not be invented: the carried
 * light is a map light the scene already registered, re-anchored at the
 * carrier, and the room light is a chunk's own ambient and its own map lights.
 * The shader-side rectangle test is proven with pixels in
 * {@code HdLightRealGlTest}; this class pins the values those pixels are made
 * from.
 */
@DisplayName("592HD carried and room light frame")
class HdLightFrameTest {

	@AfterAll
	static void reset() {
		HdLightFrame.resetForTest();
		HdSettings.resetForTest();
	}

	@Test
	@DisplayName("the carried light is the strongest map light on the tile, at the carrier")
	void carriedLight_shouldBeTheStrongestTileLight() {
		HdLightSource whiteTorch = HdLightSource.of(light(0, 0, 0, 512, 0xFFFFFF, 0.8F));
		HdLightSource redCandle = HdLightSource.of(light(0, 0, 0, 256, 0xFF0000, 0.5F));
		HdLightSource result = HdLightFrame.strongest(new HdLightSource[] { redCandle, null, whiteTorch });

		assertAll(
			() -> assertEquals(whiteTorch, result, "intensity times colour brightness picks the torch"),
			() -> assertTrue(result.weight() > redCandle.weight()),
			// The light keeps everything it came with; only its position moves.
			() -> assertEquals(512, result.radius()),
			() -> assertEquals(0xFFFFFF, result.rgb()),
			() -> assertEquals(0.8F, result.intensity(), 0.0001F)
		);
	}

	@Test
	@DisplayName("a tile with no registered light carries no light at all")
	void carriedLight_shouldBeAbsentOnAnUnlitTile() {
		assertAll(
			() -> assertNull(HdLightFrame.strongest(null)),
			() -> assertNull(HdLightFrame.strongest(new HdLightSource[0])),
			() -> assertNull(HdLightFrame.strongest(new HdLightSource[] { null, null })),
			// A zero-intensity light contributes nothing, so it is not a source.
			() -> assertFalse(HdLightGate.carriedHasSource(
				HdLightSource.of(light(0, 0, 0, 512, 0xFFFFFF, 0.0F))))
		);
	}

	@Test
	@DisplayName("re-anchoring moves the light and leaves colour, radius and intensity alone")
	void carriedLight_shouldOnlyMove() {
		HdLightSource source = HdLightSource.of(light(10, 20, 30, 384, 0x336699, 0.75F));
		HdLightSource carried = source.at(1024, 640, 2048);

		assertAll(
			() -> assertEquals(1024.0F, carried.x(), 0.0001F),
			() -> assertEquals(640.0F, carried.y(), 0.0001F),
			() -> assertEquals(2048.0F, carried.z(), 0.0001F),
			() -> assertEquals(source.radius(), carried.radius()),
			() -> assertEquals(source.rgb(), carried.rgb()),
			() -> assertEquals(source.intensity(), carried.intensity(), 0.0001F)
		);
	}

	@Test
	@DisplayName("a region is its chunk's own ambient, gained to the shader's additive share")
	void region_shouldUseTheChunkAmbient() {
		// Default ambient: the neutral strength, which is the row's gain.
		HdRoomLight defaultRegion = HdLightFrame.region(3, 4, HdRoomLight.DEFAULT_AMBIENT, null);
		HdRoomLight darkerRegion = HdLightFrame.region(3, 4, HdRoomLight.DEFAULT_AMBIENT * 0.5F, null);

		assertAll(
			() -> assertNotNull(defaultRegion),
			() -> assertEquals(0.35F, defaultRegion.strength(), 0.0001F),
			() -> assertEquals(0.175F, darkerRegion.strength(), 0.0001F),
			// The map made the region darker, so its room light is dimmer.
			() -> assertTrue(darkerRegion.strength() < defaultRegion.strength()),
			// No light override at all is not a source; the capture skips it.
			() -> assertNull(HdLightFrame.region(3, 4, 0.0F, null))
		);
	}

	@Test
	@DisplayName("a region's tint is the average of the map lights registered on its tiles")
	void region_shouldTintFromItsOwnLights() {
		PointLight red = light(0, 0, 0, 512, 0xFF0000, 1.0F);
		PointLight blue = light(0, 0, 0, 512, 0x0000FF, 1.0F);
		HdRoomLight region = HdLightFrame.region(0, 0, HdRoomLight.DEFAULT_AMBIENT, new PointLight[] { red, blue });

		assertAll(
			// (255 + 0) / 2 truncates to 127 per channel: the share is integer
			// arithmetic, so the average is 0x7F007F, not a rounded 0x800080.
			() -> assertEquals(0x7F007F, region.rgb(), "equal weights average to half red, half blue"),
			// An unlit chunk keeps the neutral white: an ordinary room is not tinted.
			() -> assertEquals(0xFFFFFF, HdLightFrame.region(0, 0, HdRoomLight.DEFAULT_AMBIENT, null).rgb())
		);
	}

	@Test
	@DisplayName("a region's bounds are its chunk and nothing more")
	void region_shouldBeBoundedToItsChunk() {
		HdRoomLight first = HdLightFrame.region(0, 0, HdRoomLight.DEFAULT_AMBIENT, null);
		HdRoomLight second = HdLightFrame.region(1, 0, HdRoomLight.DEFAULT_AMBIENT, null);

		assertAll(
			() -> assertEquals(0, first.minX()),
			() -> assertEquals(0, first.minZ()),
			() -> assertEquals(1024, first.maxX()),
			() -> assertEquals(1024, first.maxZ()),
			() -> assertEquals(1024, second.minX()),
			// The seam: the last fragment of one chunk and the first of the next.
			() -> assertTrue(first.contains(1023.99F, 10.0F)),
			() -> assertFalse(first.contains(1024.0F, 10.0F), "the next region starts exactly here"),
			() -> assertTrue(second.contains(1024.0F, 10.0F)),
			// No overlap anywhere: at most one region can light a fragment.
			() -> assertFalse(second.contains(1023.99F, 10.0F))
		);
	}

	@Test
	@DisplayName("the frame is a value: equal inputs skip the upload, a change does not")
	void frame_shouldCompareByValue() {
		HdLightSource first = HdLightSource.of(light(0, 0, 0, 512, 0xFFFFFF, 1.0F)).at(10, 20, 30);
		HdLightSource moved = first.at(11, 20, 30);
		HdRoomLight region = HdLightFrame.region(0, 0, HdRoomLight.DEFAULT_AMBIENT, null);

		assertAll(
			() -> assertEquals(HdLightFrame.of(first, new HdRoomLight[] { region }),
				HdLightFrame.of(first, new HdRoomLight[] { region })),
			() -> assertFalse(HdLightFrame.of(first, new HdRoomLight[] { region })
				.equals(HdLightFrame.of(moved, new HdRoomLight[] { region }))),
			() -> assertFalse(HdLightFrame.of(first, new HdRoomLight[] { region })
				.equals(HdLightFrame.of(first, new HdRoomLight[0]))),
			() -> assertTrue(HdLightFrame.OFF.isEmpty()),
			() -> assertFalse(HdLightFrame.of(first, new HdRoomLight[0]).isEmpty())
		);
	}

	@Test
	@DisplayName("a frame with nothing to add is OFF, so the shader takes neither branch")
	void emptyFrame_shouldPublishAsOff() {
		HdLightFrame empty = HdLightFrame.of(null, new HdRoomLight[0]);
		HdLightFrame.publishForTest(empty);
		assertSame(empty, HdLightFrame.current());
		HdLightFrame.publishForTest(null);
		assertSame(HdLightFrame.OFF, HdLightFrame.current());
	}

	@Test
	@DisplayName("both rows off costs no read: capture returns OFF before touching the client")
	void capture_shouldShortCircuitWhenBothRowsAreOff() {
		// The shipped preset leaves both rows off, and there is no player or
		// terrain in a unit test: if capture() read the client state first it
		// would throw rather than return the identity frame.
		HdSettings.resetForTest();
		assertSame(HdLightFrame.OFF, HdLightFrame.capture());
	}

	@Test
	@DisplayName("the shader's nine region slots are the frame's nine regions")
	void shaderRegionCount_shouldMatchTheFrame() {
		assertAll(
			() -> assertEquals(HdLightFrame.MAX_ROOM_REGIONS, HdShaderSource.ROOM_REGIONS),
			() -> assertEquals(HdShaderSource.ROOM_REGIONS, HdShaderSource.UNIFORM_ROOM_BOUNDS.length),
			() -> assertEquals(HdShaderSource.ROOM_REGIONS, HdShaderSource.UNIFORM_ROOM_COLOUR.length),
			() -> assertArrayEquals(new String[] { "uRoomBounds0", "uRoomBounds8", "uRoomColour0", "uRoomColour8" },
				new String[] { HdShaderSource.UNIFORM_ROOM_BOUNDS[0], HdShaderSource.UNIFORM_ROOM_BOUNDS[8],
					HdShaderSource.UNIFORM_ROOM_COLOUR[0], HdShaderSource.UNIFORM_ROOM_COLOUR[8] })
		);
	}

	@Test
	@DisplayName("the two rows are live renderer rows, not pending ones")
	void rows_shouldBeReady() {
		assertAll(
			() -> assertEquals(HdSupport.READY, HdFeature.CARRIED_LIGHT.support()),
			() -> assertEquals(HdSupport.READY, HdFeature.ROOM_LIGHT.support()),
			() -> assertFalse(HdFeature.CARRIED_LIGHT.appliesToStock()),
			() -> assertFalse(HdFeature.ROOM_LIGHT.appliesToStock()),
			// Off must stay index 0 or a fresh profile would light by default.
			() -> assertEquals("Off", HdFeature.CARRIED_LIGHT.valueLabel(0)),
			() -> assertEquals("Off", HdFeature.ROOM_LIGHT.valueLabel(0))
		);
	}

	private static PointLight light(int x, int y, int z, int radius, int rgb, float intensity) {
		return new GpuPointLight(x, y, z, radius, rgb, intensity);
	}
}
