package com.jagex.game.runetek5.client.hd.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class HdContentTest {

	private static HdContent parse(String file, String json) {
		Map<String, String> files = new LinkedHashMap<String, String>();
		files.put(file, json);
		return HdContent.parse(files);
	}

	@Test
	void lightsConvertTilesAndFileUnitsToSceneUnits() {
		HdContent content = parse(HdContentFile.LIGHTS,
			"{\"lights\":[{\"name\":\"torch\",\"objects\":[10,11],\"colour\":\"#ff8000\",\"radius\":2,"
				+ "\"intensity\":0.5,\"offset\":[512,256,-512],\"flicker\":\"flicker\"}]}");

		assertTrue(content.problems().isEmpty(), content.problems().toString());
		HdLightDefinition light = content.lightForObject(11);
		assertNotNull(light);
		assertEquals(10, light.objects()[0]);
		assertEquals(0xFF8000, light.rgb());
		assertEquals(256.0F, light.radius());
		// 512 file units is one tile, and this client's tile is 128 units.
		assertEquals(128.0F, light.offsetX());
		assertEquals(64.0F, light.offsetY());
		assertEquals(-128.0F, light.offsetZ());
		assertEquals(HdLightDefinition.FLICKER, light.flicker());
	}

	@Test
	void theFirstLightNamingAnIdWins() {
		HdContent content = parse(HdContentFile.LIGHTS,
			"{\"lights\":[{\"objects\":[7],\"colour\":\"#ff0000\",\"radius\":1},"
				+ "{\"objects\":[7],\"colour\":\"#00ff00\",\"radius\":1}]}");

		assertEquals(0xFF0000, content.lightForObject(7).rgb());
		assertNull(content.lightForObject(8));
	}

	@Test
	void aMalformedLightEntryIsSkippedAndNamed() {
		HdContent content = parse(HdContentFile.LIGHTS,
			"{\"lights\":[{\"objects\":[1],\"colour\":\"#ffffff\",\"radius\":1},"
				+ "{\"objects\":[2],\"colour\":\"not-a-colour\",\"radius\":1},"
				+ "{\"objects\":[3],\"colour\":\"#ffffff\"},"
				+ "{\"objects\":[4],\"colour\":\"#ffffff\",\"radius\":0.01}]}");

		// Entries 1 and 2 have a required field missing or malformed; the
		// remaining two entries load, the second with its radius clamped.
		assertEquals(2, content.lights().size());
		assertNotNull(content.lightForObject(1));
		assertNotNull(content.lightForObject(4));
		assertEquals(2, content.problems().size(), content.problems().toString());
		assertTrue(content.problems().get(0).startsWith("lights.json[1]"), content.problems().get(0));
	}

	@Test
	void lightRadiusAndIntensityAreClampedToTheSchemaRange() {
		HdContent content = parse(HdContentFile.LIGHTS,
			"{\"lights\":[{\"objects\":[1],\"colour\":\"#ffffff\",\"radius\":0.01,\"intensity\":99}]}");

		HdLightDefinition light = content.lights().get(0);
		assertEquals(0.1F, light.radiusTiles());
		assertEquals(16.0F, light.intensity());
	}

	@Test
	void aDefaultEnvironmentIsUsedOnlyAfterAreaEntries() {
		HdContent content = parse(HdContentFile.ENVIRONMENTS,
			"{\"environments\":[{\"name\":\"default\",\"fog\":{\"depth\":25}},"
				+ "{\"name\":\"desert\",\"area\":{\"bounds\":[[3200,3200,3300,3300]]},\"fog\":{\"depth\":50}}]}");

		assertTrue(content.problems().isEmpty(), content.problems().toString());
		assertEquals("desert", content.environmentFor(3250, 3250, 0).name());
		assertEquals("default", content.environmentFor(100, 100, 0).name());
	}

	@Test
	void anEnvironmentLevelListFiltersItsArea() {
		int region = HdEnvironmentDefinition.regionOf(3222, 3222);
		HdContent content = parse(HdContentFile.ENVIRONMENTS,
			"{\"environments\":[{\"name\":\"cave\",\"area\":{\"regions\":[" + region + "],\"levels\":[0]}},"
				+ "{\"name\":\"under\",\"area\":{\"regions\":[" + region + "],\"levels\":[1]}}]}");

		assertEquals("cave", content.environmentFor(3222, 3222, 0).name());
		assertEquals("under", content.environmentFor(3222, 3222, 1).name());
	}

	@Test
	void anEnvironmentRegionAloneMatchesEvenWithNoBounds() {
		int region = HdEnvironmentDefinition.regionOf(3222, 3222);
		HdContent content = parse(HdContentFile.ENVIRONMENTS,
			"{\"environments\":[{\"name\":\"here\",\"area\":{\"regions\":[" + region + "]}}]}");

		assertEquals("here", content.environmentFor(3222, 3222, 0).name());
		assertNull(content.environmentFor(100, 100, 0));
	}

	@Test
	void absentEnvironmentFieldsStayUnsetRatherThanZero() {
		HdContent content = parse(HdContentFile.ENVIRONMENTS, "{\"environments\":[{\"name\":\"plain\"}]}");
		HdEnvironmentDefinition environment = content.environments().get(0);

		assertTrue(environment.isDefault());
		assertNull(environment.sunColour());
		assertNull(environment.fogColour());
		assertTrue(Float.isNaN(environment.fogDepth()));
		assertTrue(Float.isNaN(environment.fogEnd()));
		assertEquals(1.0F, environment.sunIntensity());
		assertEquals(1.0F, environment.waterWaves());
		assertTrue(Float.isNaN(environment.waterStrength()));
	}

	@Test
	void anEnvironmentColourMustBeHex() {
		HdContent content = parse(HdContentFile.ENVIRONMENTS,
			"{\"environments\":[{\"name\":\"bad\",\"sky\":{\"top\":\"blue\"}},{\"name\":\"good\"}]}");

		assertEquals(1, content.environments().size());
		assertEquals("good", content.environments().get(0).name());
		assertEquals(1, content.problems().size());
	}

	@Test
	void materialsCarryTheirWaterDefaults() {
		HdContent content = parse(HdContentFile.MATERIALS,
			"{\"materials\":[{\"texture\":11,\"albedo\":\"albedo.png\",\"water\":{}}]}");

		HdMaterialDefinition material = content.material(11);
		assertNotNull(material);
		assertTrue(material.hasAlbedo());
		assertFalse(material.isCustom());
		HdMaterialDefinition.Water water = material.water();
		assertNotNull(water);
		assertEquals(0x69809C, water.surface());
		assertEquals(0xB0A492, water.foam());
		assertEquals(0x00758E, water.depth());
		assertEquals(0.5F, water.opacity());
		assertEquals(500.0F, water.gloss());
		assertTrue(water.hasFoam());
		assertFalse(water.flat());
	}

	@Test
	void materialsFromTenThousandUpAreTheClientsOwn() {
		HdContent content = parse(HdContentFile.MATERIALS,
			"{\"materials\":[{\"texture\":10001,\"albedo\":\"albedo.png\"}]}");

		assertTrue(content.material(10001).isCustom());
		assertFalse(content.material(9999) != null);
	}

	@Test
	void aMalformedMaterialIsSkipped() {
		HdContent content = parse(HdContentFile.MATERIALS,
			"{\"materials\":[{\"name\":\"no texture\"},{\"texture\":-1},{\"texture\":5}]}");

		assertEquals(1, content.materials().size());
		assertNotNull(content.material(5));
		assertEquals(2, content.problems().size(), content.problems().toString());
	}

	@Test
	void aModelOverrideUsesItsAreaAndFallsBackToAreaOnlyEntries() {
		HdContent content = parse(HdContentFile.MODEL_OVERRIDES,
			"{\"overrides\":[{\"objects\":[10],\"area\":[3200,3200,3210,3210],\"material\":10001},"
				+ "{\"area\":[3300,3300,3310,3310],\"material\":10002,\"wind\":true}]}");

		assertTrue(content.problems().isEmpty(), content.problems().toString());
		assertEquals(10001, content.overrideForObject(10, 3205, 3205).material());
		// Outside its own area, object 10 still falls back to the area-only
		// entry, exactly as upstream's overrideFor does.
		assertEquals(10002, content.overrideForObject(10, 3305, 3305).material());
		assertEquals(10002, content.overrideForObject(99, 3305, 3305).material());
		assertTrue(content.overrideForObject(99, 3305, 3305).wind());
		assertNull(content.overrideForObject(10, 100, 100));
	}

	@Test
	void aModelOverrideDefaultsShadowsToOnAndUvToWorld() {
		HdContent content = parse(HdContentFile.MODEL_OVERRIDES,
			"{\"overrides\":[{\"models\":[7],\"material\":5}]}");
		HdModelOverride override = content.overrideForModel(7, 0, 0);

		assertTrue(override.castShadows());
		assertTrue(override.worldUv());
		assertFalse(override.wind());
		assertFalse(override.replace());
		assertEquals(1.0F, override.uvScale());
	}

	@Test
	void aModelOverrideRefusesAnUnknownUvMode() {
		HdContent content = parse(HdContentFile.MODEL_OVERRIDES,
			"{\"overrides\":[{\"models\":[7],\"uv\":\"sideways\"}]}");

		assertTrue(content.modelOverrides().isEmpty());
		assertEquals(1, content.problems().size());
	}

	@Test
	void tileOverridesUseFirstMatchAndRefuseAnEmptyPredicate() {
		HdContent content = parse(HdContentFile.TILE_OVERRIDES,
			"{\"tiles\":[{\"underlays\":[1],\"material\":10001},"
				+ "{\"underlays\":[1],\"material\":10002},"
				+ "{\"area\":[3200,3200,3200,3200],\"material\":10003},"
				+ "{\"material\":10004}]}");

		assertEquals(10001, content.tileOverrideFor(0, 0, 1, -1).material());
		assertNull(content.tileOverrideFor(0, 0, 2, -1));
		assertEquals(10003, content.tileOverrideFor(3200, 3200, 2, -1).material());
		assertEquals(1, content.problems().size(), content.problems().toString());
	}

	@Test
	void foliageDefaultsStrengthAndFrequencyToOne() {
		HdContent content = parse(HdContentFile.FOLIAGE, "{\"foliage\":[{\"objects\":[5,6]}]}");

		HdFoliageDefinition definition = content.foliageForObject(6);
		assertNotNull(definition);
		assertEquals(1.0F, definition.strength());
		assertEquals(1.0F, definition.frequency());
		assertNull(content.foliageForObject(7));
	}

	@Test
	void aFoliageEntryWithoutObjectsIsSkipped() {
		HdContent content = parse(HdContentFile.FOLIAGE, "{\"foliage\":[{\"name\":\"none\"},{\"objects\":[1]}]}");

		assertEquals(1, content.foliage().size());
		assertEquals(1, content.problems().size());
	}

	@Test
	void aMalformedFileNeverThrows() {
		HdContent content = HdContent.parse(single(HdContentFile.LIGHTS, "{\"lights\":["));

		assertTrue(content.lights().isEmpty());
		assertEquals(1, content.problems().size());
		assertTrue(content.problems().get(0).startsWith("lights.json:"));
	}

	@Test
	void aMalformedRootIsNamed() {
		HdContent content = HdContent.parse(single(HdContentFile.LIGHTS, "[]"));

		assertTrue(content.lights().isEmpty());
		assertEquals(1, content.problems().size());
	}

	@Test
	void materialFilesRefuseToEscapeTheirFolder() {
		HdContent content = HdContent.parse(new LinkedHashMap<String, String>());

		assertNull(content.materialFile(11, "../secret.png"));
		assertNull(content.materialFile("..", "secret.png"));
		assertNull(content.lutFile("a/b.png"));
	}

	private static Map<String, String> single(String file, String json) {
		Map<String, String> files = new LinkedHashMap<String, String>();
		files.put(file, json);
		return files;
	}

	/** The file names are package-private knowledge of the loader; restate them here. */
	private static final class HdContentFile {

		static final String LIGHTS = "lights.json";

		static final String ENVIRONMENTS = "environments.json";

		static final String MATERIALS = "materials.json";

		static final String MODEL_OVERRIDES = "model_overrides.json";

		static final String TILE_OVERRIDES = "tile_overrides.json";

		static final String FOLIAGE = "foliage.json";
	}
}
