package com.jagex.game.runetek5.client.hd.shader;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.client.hd.material.HdMaterialSurface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The lighting-model row and its material, as the draw seam states them.
 *
 * <p>Two facts the shader depends on and cannot check itself: that the row is
 * read from the live setting (so turning it off is the stock draw), and that a
 * draw without a material carries the identity surface rather than a half-set
 * one - the shader would otherwise sample units nothing bound.
 */
@DisplayName("592HD lighting-model draw request")
class HdDrawRequestLightingModelTest {

	@BeforeEach
	void settings() throws Exception {
		HdSettings.setDirectoryForTest(Files.createTempDirectory("hd-request").toFile());
		HdSettings.setEnabled(true, null);
	}

	@AfterEach
	void reset() {
		HdSettings.resetForTest();
	}

	@Test
	@DisplayName("the row follows the setting, off by default")
	void row_shouldFollowTheSetting() {
		HdSettings.set(HdFeature.LIGHTING_MODEL, 0);
		HdDrawRequest off = request();
		HdSettings.set(HdFeature.LIGHTING_MODEL, 1);
		HdDrawRequest on = request();
		HdSettings.set(HdFeature.LIGHTING_MODEL, 0);
		HdDrawRequest restored = request();

		assertAll(
			() -> assertFalse(off.lightingModel(), "a settings file without the row is the stock draw"),
			() -> assertTrue(on.lightingModel()),
			() -> assertFalse(restored.lightingModel())
		);
	}

	@Test
	@DisplayName("a draw without a material carries the identity surface")
	void noMaterial_shouldBeIdentity() {
		HdSettings.set(HdFeature.LIGHTING_MODEL, 1);
		HdDrawRequest request = request();

		assertAll(
			() -> assertSame(HdMaterialSurface.OFF, request.material()),
			() -> assertTrue(request.material().isIdentity())
		);
	}

	@Test
	@DisplayName("the material travels with the draw through its setters")
	void material_shouldSurviveTheSetters() {
		HdSettings.set(HdFeature.LIGHTING_MODEL, 1);
		HdMaterialSurface surface = HdMaterialSurface.OFF;
		HdDrawRequest request = request().withMaterial(surface)
			.withWater(null);

		assertSame(surface, request.material(),
			"a setter that rebuilds the draw must carry the material with it");
	}

	private static HdDrawRequest request() {
		return HdDrawRequest.current(HdDrawRows.none(), 0, 8448);
	}
}
