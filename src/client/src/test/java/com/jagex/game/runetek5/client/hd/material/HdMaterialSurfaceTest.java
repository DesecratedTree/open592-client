package com.jagex.game.runetek5.client.hd.material;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import com.jagex.game.runetek5.client.hd.content.HdContent;
import com.jagex.game.runetek5.client.hd.content.HdMaterialDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * What the shader's {@code uMaterialFlags}/{@code uMaterialSurface} say for one
 * material.
 *
 * <p>The point of the class under test is that "the file says this material has
 * a normal map" and "this draw uploaded one" are different facts: a PNG that
 * fails to decode must not leave the shader sampling an empty unit. These tests
 * pin that difference, and the identity that makes a row-off draw the stock
 * draw.
 */
@DisplayName("592HD material surface")
class HdMaterialSurfaceTest {

	@Test
	@DisplayName("no decoded map is the identity, whatever the file says")
	void noMaps_shouldBeIdentity() {
		HdMaterialDefinition definition = material();

		assertAll(
			() -> assertSame(HdMaterialSurface.OFF,
				HdMaterialSurface.of(definition, false, false, false, false),
				"a material whose maps all failed to decode must be OFF, not a half-bound surface"),
			() -> assertSame(HdMaterialSurface.OFF, HdMaterialSurface.of(null, true, true, true, true),
				"a draw without a definition binds nothing"),
			() -> assertTrue(HdMaterialSurface.OFF.isIdentity()),
			() -> assertFalse(HdMaterialSurface.OFF.normal()),
			() -> assertFalse(HdMaterialSurface.OFF.orm()),
			() -> assertFalse(HdMaterialSurface.OFF.emissive()),
			() -> assertFalse(HdMaterialSurface.OFF.height())
		);
	}

	@Test
	@DisplayName("the surface carries only the maps that decoded, plus the material's response")
	void decodedMaps_shouldCarryTheMaterialsResponse() {
		HdMaterialDefinition definition = material();
		HdMaterialSurface surface = HdMaterialSurface.of(definition, true, true, false, false);

		assertAll(
			() -> assertTrue(surface.normal()),
			() -> assertTrue(surface.orm()),
			() -> assertFalse(surface.emissive()),
			() -> assertFalse(surface.height()),
			() -> assertFalse(surface.isIdentity()),
			() -> assertEquals(definition.specular(), surface.specular()),
			() -> assertEquals(definition.emissiveStrength(), surface.emissiveStrength()),
			() -> assertEquals(definition.parallax(), surface.parallax()),
			() -> assertFalse(surface.unlit())
		);
	}

	@Test
	@DisplayName("an unlit material takes no parallax, because it is not lit through its maps")
	void unlit_shouldDropParallax() {
		HdMaterialSurface lit = HdMaterialSurface.of(material(), true, false, false, true);
		HdMaterialSurface unlit = HdMaterialSurface.of(material(true), true, false, false, true);

		assertAll(
			() -> assertTrue(unlit.unlit()),
			() -> assertEquals(0.0F, unlit.parallax(), "an unlit surface never offsets its lookup"),
			() -> assertTrue(lit.parallax() > 0.0F, "a lit surface keeps the file's depth: " + lit.parallax())
		);
	}

	@Test
	@DisplayName("equality is the whole surface, so the pipeline caches on it safely")
	void equality_shouldCoverEveryField() {
		HdMaterialDefinition definition = material();
		HdMaterialSurface one = HdMaterialSurface.of(definition, true, true, false, false);
		HdMaterialSurface same = HdMaterialSurface.of(definition, true, true, false, false);
		HdMaterialSurface other = HdMaterialSurface.of(definition, true, false, false, false);

		assertAll(
			() -> assertEquals(one, same),
			() -> assertEquals(one.hashCode(), same.hashCode()),
			() -> assertFalse(one.equals(other)),
			() -> assertFalse(one.equals(HdMaterialSurface.OFF))
		);
	}

	private static HdMaterialDefinition material() {
		return material(false);
	}

	/** One parsed entry, so the defaults are the loader's and not a test's. */
	private static HdMaterialDefinition material(boolean unlit) {
		Map<String, String> files = new HashMap<String, String>();
		files.put("materials.json", "{\"materials\":[{"
			+ "\"texture\":105,\"normal\":\"normal.png\",\"orm\":\"orm.png\","
			+ "\"emissive\":\"emissive.png\",\"height\":\"height.png\","
			+ "\"specular\":1.5,\"emissiveStrength\":3.0,\"parallax\":0.1,"
			+ "\"unlit\":" + unlit + "}]}");
		HdContent content = HdContent.parse(files);
		assertTrue(content.problems().isEmpty(), "the entry must parse cleanly: " + content.problems());
		return content.material(105);
	}
}
