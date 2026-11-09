package com.jagex.game.runetek5.client.hd;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pins the relationship between a row's support level and where its value
 * goes. A row is either backed by a stock flag (and then it must name a
 * target), or owned by the 592HD renderer (and then it must not), and the two
 * renderer-owned rows this milestone implemented must stop advertising
 * themselves as pending in the pane.
 */
@DisplayName("592HD row ownership")
class HdFeatureSupportTest {

	/** True when any row still offers this value label, whatever its key. */
	private static boolean hasAnyLabel(String label) {
		for (HdFeature feature : HdFeature.values()) {
			for (int value = 0; value < feature.valueCount(); value++) {
				if (label.equals(feature.valueLabel(value))) {
					return true;
				}
			}
		}
		return false;
	}

	private static final Set<HdFeature> RENDERER_OWNED = EnumSet.of(
		HdFeature.PER_PIXEL_LIGHTING, HdFeature.POINT_LIGHTS,
		HdFeature.SHADOW_MAP, HdFeature.LIGHTING_DETAIL, HdFeature.WATER,
		HdFeature.TONEMAP, HdFeature.COLOUR_GRADING, HdFeature.FXAA, HdFeature.DITHER,
		HdFeature.MIPMAPS, HdFeature.ANISOTROPIC,
		HdFeature.FOG, HdFeature.DISTANCE_FADE, HdFeature.AMBIENT_OCCLUSION,
		HdFeature.CARRIED_LIGHT, HdFeature.ROOM_LIGHT, HdFeature.ENVIRONMENT_LIGHTING,
		HdFeature.SKY, HdFeature.DAY_NIGHT, HdFeature.CUTOUT_SHADOWS,
		HdFeature.FOLIAGE_SWAY, HdFeature.GRASS_DISPLACEMENT, HdFeature.CAUSTICS, HdFeature.REFLECTIONS,
		HdFeature.WORLD_TEXTURES, HdFeature.MATERIAL_MAPS, HdFeature.TERRAIN_BLEND,
		HdFeature.LIGHTING_MODEL, HdFeature.TEXTURE_REPLACEMENTS);

	@Test
	@DisplayName("a READY row is either stock-backed or renderer-owned, never neither")
	void readyRows_shouldHaveAnOwner() {
		for (HdFeature feature : HdFeature.values()) {
			if (feature.support() != HdSupport.READY) {
				continue;
			}
			assertTrue(feature.appliesToStock() || RENDERER_OWNED.contains(feature),
				feature + " is READY but nothing owns it");
		}
	}

	@Test
	@DisplayName("the renderer-owned rows are live, so the pane stops marking them pending")
	void rendererRows_shouldBeReady() {
		assertAll(
			() -> assertEquals(HdSupport.READY, HdFeature.PER_PIXEL_LIGHTING.support()),
			() -> assertEquals(HdSupport.READY, HdFeature.POINT_LIGHTS.support()),
			() -> assertFalse(HdFeature.PER_PIXEL_LIGHTING.appliesToStock()),
			() -> assertFalse(HdFeature.POINT_LIGHTS.appliesToStock())
		);
	}

	@Test
	@DisplayName("the water row is upstream's key, with its three values")
	void water_shouldBeRendererOwned() {
		assertAll(
			() -> assertEquals(HdSupport.READY, HdFeature.WATER.support()),
			() -> assertTrue(RENDERER_OWNED.contains(HdFeature.WATER)),
			() -> assertFalse(HdFeature.WATER.appliesToStock(), "the renderer reads it, no stock flag"),
			// Off is the stock water draw, so index 0 must stay Off.
			() -> assertEquals("Off", HdFeature.WATER.valueLabel(0)),
			() -> assertEquals("On", HdFeature.WATER.valueLabel(1)),
			// The third value is the custom surface; the gate's indices name it.
			() -> assertEquals("Custom", HdFeature.WATER.valueLabel(2)),
			() -> assertEquals(3, HdFeature.WATER.valueCount(), "Off, On, Custom"),
			// The old presence row is gone: the water row is not one of the
			// unavailable placeholders, and no row still advertises the inert
			// "Always" value it used to show.
			() -> assertNotEquals(HdSupport.UNAVAILABLE, HdFeature.WATER.support()),
			() -> assertFalse(hasAnyLabel("Always"), "no row still shows the inert Always value")
		);
	}

	@Test
	@DisplayName("the atmosphere rows are renderer-owned and live in the fragment stage")
	void atmosphereRows_shouldBeRendererOwned() {
		assertAll(
			() -> assertEquals(HdSupport.READY, HdFeature.FOG.support()),
			() -> assertEquals(HdSupport.READY, HdFeature.DISTANCE_FADE.support()),
			() -> assertEquals(HdSupport.READY, HdFeature.AMBIENT_OCCLUSION.support()),
			// Off is the stock draw for all three, so index 0 must stay Off.
			() -> assertEquals("Off", HdFeature.FOG.valueLabel(0)),
			() -> assertEquals("Off", HdFeature.DISTANCE_FADE.valueLabel(0)),
			() -> assertEquals("Off", HdFeature.AMBIENT_OCCLUSION.valueLabel(0)),
			// The water row is upstream's key and the renderer's own row, not a
			// second inert presence row.
			() -> assertEquals(HdSupport.READY, HdFeature.WATER.support())
		);
	}

	@Test
	@DisplayName("the carried and room rows are live, sourced from the scene's own lights")
	void sceneLightRows_shouldBeRendererOwned() {
		assertAll(
			() -> assertEquals(HdSupport.READY, HdFeature.CARRIED_LIGHT.support()),
			() -> assertEquals(HdSupport.READY, HdFeature.ROOM_LIGHT.support()),
			() -> assertTrue(RENDERER_OWNED.contains(HdFeature.CARRIED_LIGHT)),
			() -> assertTrue(RENDERER_OWNED.contains(HdFeature.ROOM_LIGHT)),
			// Neither names a stock flag: the terms live only in the shader.
			() -> assertFalse(HdFeature.CARRIED_LIGHT.appliesToStock()),
			() -> assertFalse(HdFeature.ROOM_LIGHT.appliesToStock()),
			// Off is the identity for both, so index 0 must stay Off.
			() -> assertEquals("Off", HdFeature.CARRIED_LIGHT.valueLabel(0)),
			() -> assertEquals("Off", HdFeature.ROOM_LIGHT.valueLabel(0)),
			() -> assertEquals(2, HdFeature.CARRIED_LIGHT.valueCount()),
			() -> assertEquals(2, HdFeature.ROOM_LIGHT.valueCount())
		);
	}

	@Test
	@DisplayName("sun shadows are a renderer row, read live at draw time")
	void sunShadowRows_shouldBeRendererOwned() {
		assertAll(
			() -> assertEquals(HdSupport.READY, HdFeature.SHADOW_MAP.support()),
			() -> assertTrue(RENDERER_OWNED.contains(HdFeature.SHADOW_MAP)),
			() -> assertEquals(4, HdFeature.SHADOW_MAP.valueCount(), "Off, 1024, 2048, 4096"),
			() -> assertEquals("Off", HdFeature.SHADOW_MAP.valueLabel(0),
				"index 0 must stay Off or a fresh profile would shadow by default"),
			// The PCF quality rides the existing detail row rather than adding one.
			() -> assertEquals(HdSupport.READY, HdFeature.LIGHTING_DETAIL.support()),
			() -> assertEquals(2, HdFeature.LIGHTING_DETAIL.valueCount())
		);
	}

	@Test
	@DisplayName("the sky rows are live: the dome draws, the clock only recolours")
	void skyRows_shouldBeRendererOwned() {
		assertAll(
			() -> assertEquals(HdSupport.READY, HdFeature.SKY.support()),
			() -> assertEquals(HdSupport.READY, HdFeature.DAY_NIGHT.support()),
			() -> assertTrue(RENDERER_OWNED.contains(HdFeature.SKY)),
			() -> assertTrue(RENDERER_OWNED.contains(HdFeature.DAY_NIGHT)),
			() -> assertFalse(HdFeature.SKY.appliesToStock(), "the pass is the row's only owner"),
			// Off is no draw, so index 0 must stay Off.
			() -> assertEquals("Off", HdFeature.SKY.valueLabel(0)),
			() -> assertEquals("On", HdFeature.SKY.valueLabel(1)),
			// Off is a stopped clock, and the four lengths are the row's labels.
			() -> assertEquals("Off", HdFeature.DAY_NIGHT.valueLabel(0)),
			() -> assertEquals(5, HdFeature.DAY_NIGHT.valueCount(), "Off, Clock, 10, 30, 60")
		);
	}

	@Test
	@DisplayName("cut-out shadows are live and ride the sun shadow pass")
	void cutoutShadows_shouldBeRendererOwned() {
		assertAll(
			() -> assertEquals(HdSupport.READY, HdFeature.CUTOUT_SHADOWS.support()),
			() -> assertFalse(HdFeature.CUTOUT_SHADOWS.appliesToStock()),
			() -> assertEquals("cutoutShadows", HdFeature.CUTOUT_SHADOWS.key(),
				"634HD's saved key, so a ported file reads the same row"),
			() -> assertEquals("Off", HdFeature.CUTOUT_SHADOWS.valueLabel(0))
		);
	}

	@Test
	@DisplayName("caustics are live, driven by the tile's own water depth")
	void caustics_shouldBeRendererOwned() {
		assertAll(
			() -> assertEquals(HdSupport.READY, HdFeature.CAUSTICS.support()),
			() -> assertTrue(RENDERER_OWNED.contains(HdFeature.CAUSTICS)),
			() -> assertFalse(HdFeature.CAUSTICS.appliesToStock(), "the fragment stage is the row's only owner"),
			// Off is the identity, so index 0 must stay Off.
			() -> assertEquals("Off", HdFeature.CAUSTICS.valueLabel(0)),
			() -> assertEquals("On", HdFeature.CAUSTICS.valueLabel(1)),
			() -> assertEquals("caustics", HdFeature.CAUSTICS.key(),
				"634HD's saved key, so a ported file reads the same row")
		);
	}

	@Test
	@DisplayName("reflections are live, driven by the material's own effect slot")
	void reflections_shouldBeRendererOwned() {
		assertAll(
			() -> assertEquals(HdSupport.READY, HdFeature.REFLECTIONS.support()),
			() -> assertTrue(RENDERER_OWNED.contains(HdFeature.REFLECTIONS)),
			() -> assertFalse(HdFeature.REFLECTIONS.appliesToStock(), "the fragment stage is the row's only owner"),
			// Off is the identity, so index 0 must stay Off.
			() -> assertEquals("Off", HdFeature.REFLECTIONS.valueLabel(0)),
			() -> assertEquals("On", HdFeature.REFLECTIONS.valueLabel(1)),
			() -> assertEquals("reflections", HdFeature.REFLECTIONS.key(),
				"634HD's saved key, so a ported file reads the same row")
		);
	}

	@Test
	@DisplayName("the lighting model is live, switchable in the pane")
	void lightingModel_shouldBeRendererOwned() {
		assertAll(
			() -> assertEquals(HdSupport.READY, HdFeature.LIGHTING_MODEL.support()),
			() -> assertTrue(RENDERER_OWNED.contains(HdFeature.LIGHTING_MODEL)),
			() -> assertFalse(HdFeature.LIGHTING_MODEL.appliesToStock(), "the fragment stage is the row's only owner"),
			// Off is the stock fixed-function draw, so index 0 must stay Off.
			() -> assertEquals("Off", HdFeature.LIGHTING_MODEL.valueLabel(0)),
			() -> assertEquals("lightingModel", HdFeature.LIGHTING_MODEL.key(),
				"634HD's saved key, so a ported file reads the same row")
		);
	}

	@Test
	@DisplayName("no two rows write the same stock flag")
	void stockTargets_shouldBeUnique() {
		Set<HdStockTarget> used = new HashSet<HdStockTarget>();
		for (HdFeature feature : HdFeature.values()) {
			if (!feature.appliesToStock()) {
				continue;
			}
			assertTrue(used.add(feature.target()), feature + " shares " + feature.target());
		}
	}

	@Test
	@DisplayName("RENDERER means only 'no stock flag'; liveness comes from the support level")
	void rendererTarget_shouldNotEncodeLiveness() {
		for (HdFeature feature : HdFeature.values()) {
			assertEquals(feature.target() != HdStockTarget.RENDERER, feature.appliesToStock(),
				feature + " disagrees with its target about stock state");
		}
		// The same target carries live, pending and unavailable rows, so the
		// enum can never be read as "this row is implemented".
		assertAll(
			() -> assertEquals(HdStockTarget.RENDERER, HdFeature.PER_PIXEL_LIGHTING.target()),
			() -> assertEquals(HdSupport.READY, HdFeature.PER_PIXEL_LIGHTING.support()),
			() -> assertEquals(HdStockTarget.RENDERER, HdFeature.WATER.target()),
			() -> assertEquals(HdSupport.READY, HdFeature.WATER.support()),
			() -> assertEquals(HdStockTarget.RENDERER, HdFeature.TONEMAP.target()),
			() -> assertEquals(HdSupport.READY, HdFeature.TONEMAP.support()),
			() -> assertEquals(HdStockTarget.RENDERER, HdFeature.SOFT_PARTICLES.target()),
			() -> assertEquals(HdSupport.PENDING_RENDERER, HdFeature.SOFT_PARTICLES.support()),
			() -> assertEquals(HdStockTarget.RENDERER, HdFeature.GRASS_DISPLACEMENT.target()),
			// Both vegetation rows are live now: they share the per-pixel program's
			// vertex stage, so neither names a stock flag and both are READY.
			() -> assertEquals(HdSupport.READY, HdFeature.GRASS_DISPLACEMENT.support()),
			() -> assertEquals(HdSupport.READY, HdFeature.FOLIAGE_SWAY.support()),
			() -> assertFalse(HdFeature.GRASS_DISPLACEMENT.appliesToStock()),
			() -> assertFalse(HdFeature.FOLIAGE_SWAY.appliesToStock())
		);
	}

	@Test
	@DisplayName("the environment row is live and reads the scene's own environment")
	void environmentLight_shouldBeRendererOwned() {
		assertAll(
			() -> assertEquals(HdSupport.READY, HdFeature.ENVIRONMENT_LIGHTING.support()),
			() -> assertTrue(RENDERER_OWNED.contains(HdFeature.ENVIRONMENT_LIGHTING)),
			// Off is the stock ambient, so index 0 must stay Off.
			() -> assertEquals("Off", HdFeature.ENVIRONMENT_LIGHTING.valueLabel(0)),
			() -> assertEquals("On", HdFeature.ENVIRONMENT_LIGHTING.valueLabel(1)),
			() -> assertFalse(HdFeature.ENVIRONMENT_LIGHTING.appliesToStock(), "the renderer reads it, no stock flag")
		);
	}

	@Test
	@DisplayName("pending rows stay stored, so a preset cannot silently drive them")
	void pendingRows_shouldNotApply() {
		for (HdFeature feature : HdFeature.values()) {
			if (feature.support() == HdSupport.PENDING_RENDERER) {
				assertFalse(feature.appliesToStock() && feature.support().appliesToday(),
					feature + " is pending but claims to apply today");
			}
		}
	}
}
