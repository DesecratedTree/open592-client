package com.jagex.game.runetek5.client.hd.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class HdContentResourcesTest {

	private static HdContent content;

	@BeforeAll
	static void load() {
		content = HdContent.loadDefault();
	}

	@Test
	void everyShippedEntryParses() {
		List<String> problems = content.problems();
		assertEquals(0, problems.size(), problems.toString());
	}

	@Test
	void everySectionIsPresent() {
		assertTrue(content.lights().size() >= 5, "lights: " + content.lights().size());
		assertTrue(content.environments().size() >= 50, "environments: " + content.environments().size());
		assertTrue(content.materials().size() >= 100, "materials: " + content.materials().size());
		assertTrue(content.foliage().size() >= 1, "foliage: " + content.foliage().size());
	}

	@Test
	void everyLightIsUsable() {
		for (int i = 0; i < content.lights().size(); i++) {
			HdLightDefinition light = content.lights().get(i);
			assertFalse(light.isEmpty(), light.name());
			assertTrue(light.radiusTiles() >= 0.1F && light.radiusTiles() <= 64.0F, light.name());
			assertTrue(light.intensity() >= 0.0F && light.intensity() <= 16.0F, light.name());
		}
	}

	@Test
	void everyEnvironmentAreaHoldsATileItNames() {
		for (int i = 0; i < content.environments().size(); i++) {
			HdEnvironmentDefinition environment = content.environments().get(i);
			int[] regions = environment.regions();
			if (regions == null || regions.length == 0) {
				continue;
			}
			int region = regions[0];
			int x = (region >> 8) << 6;
			int z = (region & 0xFF) << 6;
			int level = environment.levels() == null ? 0 : environment.levels()[0];
			assertTrue(environment.contains(x, z, level), environment.name());
		}
	}

	@Test
	void aKnownCustomMaterialHasItsAlbedo() {
		HdMaterialDefinition material = content.material(10001);
		assertNotNull(material);
		assertTrue(material.isCustom());
		assertTrue(material.hasAlbedo());
	}

	@Test
	void aKnownMaterialFileIsAClasspathPng() {
		HdMaterialDefinition material = content.material(10001);
		byte[] albedo = content.materialFile(material.texture(), material.albedo());

		assertNotNull(albedo);
		assertTrue(albedo.length > 8);
		assertEquals((byte) 0x89, albedo[0]);
		assertEquals((byte) 'P', albedo[1]);
		assertEquals((byte) 'N', albedo[2]);
		assertEquals((byte) 'G', albedo[3]);
	}

	@Test
	void atLeastOneEnvironmentCarriesItsOwnFogDepth() {
		HdEnvironmentDefinition fogged = null;
		for (int i = 0; i < content.environments().size(); i++) {
			HdEnvironmentDefinition candidate = content.environments().get(i);
			if (!Float.isNaN(candidate.fogDepth())) {
				fogged = candidate;
				break;
			}
		}
		assertNotNull(fogged);
		assertTrue(fogged.fogDepth() > 0.0F);
	}
}
