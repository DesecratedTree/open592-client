package com.jagex.game.runetek5.client.hd.sway;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdPreset;
import com.jagex.game.runetek5.client.hd.HdSettings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * The two vegetation rows' gate, as a truth table.
 *
 * <p>Two independent rows and one kind of draw: a row needs 592HD, its own
 * switch, and a model the scene marked as vegetation. The precedence is the
 * contract - every row of the table is asserted, not just the interesting ones,
 * because "off" has to mean untouched rather than nearly untouched.
 */
@DisplayName("592HD vegetation gate")
class HdSwayGateTest {

	@TempDir
	Path preferences;

	@BeforeEach
	void reset() {
		HdSettings.setDirectoryForTest(this.preferences.toFile());
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.FOLIAGE_SWAY, 0);
		HdSettings.set(HdFeature.GRASS_DISPLACEMENT, 0);
	}

	@AfterEach
	void restore() {
		HdSettings.resetForTest();
	}

	@Test
	@DisplayName("either row, with 592HD on, asks for displacement - and nothing else does")
	void requested_shouldBeHdAndAtLeastOneRow() {
		boolean[] values = { false, true };
		for (int i = 0; i < 8; i++) {
			boolean hd = values[i & 1];
			boolean foliage = values[i >> 1 & 1];
			boolean grass = values[i >> 2 & 1];
			boolean expected = hd && (foliage || grass);
			boolean actual = HdSwayGate.requested(hd, foliage, grass);
			assertEquals(expected, actual, "requested(" + hd + "," + foliage + "," + grass + ")");
		}
	}

	@Test
	@DisplayName("only a marked plant is displaced, and only with a row on")
	void displaces_shouldNeedBothTheKindAndARow() {
		boolean[] values = { false, true };
		for (int i = 0; i < 8; i++) {
			boolean foliage = values[i & 1];
			boolean grass = values[i >> 1 & 1];
			boolean rows = foliage || grass;
			HdSway block = HdSway.of(foliage, grass, 0.0F, 0.0F, 0.0F, 0.0F);
			boolean plant = HdSwayGate.displaces(HdVegetation.GROUND_DECOR, block);
			boolean other = HdSwayGate.displaces(HdVegetation.NONE, block);
			assertAll(
				() -> assertEquals(rows, plant, "plant(" + foliage + "," + grass + ")"),
				() -> assertFalse(other, "a model the scene did not mark never moves: ("
					+ foliage + "," + grass + ")")
			);
		}
	}

	@Test
	@DisplayName("the rows follow the saved settings, and 592HD off silences both")
	void settings_shouldDriveTheRows() {
		assertFalse(HdSwayGate.requested());
		assertFalse(HdSwayGate.foliageRequested());
		assertFalse(HdSwayGate.grassRequested());

		HdSettings.set(HdFeature.FOLIAGE_SWAY, 1);
		assertAll(
			() -> assertTrue(HdSwayGate.foliageRequested()),
			() -> assertFalse(HdSwayGate.grassRequested(), "the rows are independent"),
			() -> assertTrue(HdSwayGate.requested())
		);

		HdSettings.setEnabled(false, null);
		assertAll(
			() -> assertFalse(HdSwayGate.foliageRequested(), "a row without 592HD does nothing"),
			() -> assertFalse(HdSwayGate.requested()),
			// The stored value survives: turning 592HD back on restores the row.
			() -> assertEquals(1, HdSettings.value(HdFeature.FOLIAGE_SWAY))
		);

		HdSettings.setEnabled(true, null);
		assertTrue(HdSwayGate.foliageRequested());
	}

	@Test
	@DisplayName("the high tiers carry both rows, the low ones neither")
	void presets_shouldBeCoherent() {
		assertAll(
			() -> assertEquals(1, HdPreset.HIGH.value(HdFeature.FOLIAGE_SWAY)),
			() -> assertEquals(1, HdPreset.HIGH.value(HdFeature.GRASS_DISPLACEMENT)),
			() -> assertEquals(1, HdPreset.ULTRA.value(HdFeature.FOLIAGE_SWAY)),
			() -> assertEquals(1, HdPreset.ULTRA.value(HdFeature.GRASS_DISPLACEMENT)),
			// Both rows live in the per-pixel program's vertex stage, so a tier
			// that leaves that program off cannot claim either.
			() -> assertEquals(0, HdPreset.LOW.value(HdFeature.PER_PIXEL_LIGHTING)),
			() -> assertEquals(0, HdPreset.LOW.value(HdFeature.FOLIAGE_SWAY)),
			() -> assertEquals(0, HdPreset.LOW.value(HdFeature.GRASS_DISPLACEMENT)),
			() -> assertEquals(1, HdPreset.MEDIUM.value(HdFeature.PER_PIXEL_LIGHTING)),
			() -> assertEquals(0, HdPreset.MEDIUM.value(HdFeature.FOLIAGE_SWAY)),
			() -> assertEquals(0, HdPreset.MEDIUM.value(HdFeature.GRASS_DISPLACEMENT))
		);
	}

	@Test
	@DisplayName("both rows ship off, so a fresh profile never displaces stock geometry")
	void shippedDefault_shouldBeOff() {
		assertAll(
			() -> assertEquals("Off", HdFeature.FOLIAGE_SWAY.valueLabel(0)),
			() -> assertEquals("On", HdFeature.FOLIAGE_SWAY.valueLabel(1)),
			() -> assertEquals(2, HdFeature.FOLIAGE_SWAY.valueCount()),
			() -> assertEquals(0, HdFeature.FOLIAGE_SWAY.defaultValue()),
			() -> assertEquals(0, HdFeature.GRASS_DISPLACEMENT.defaultValue())
		);
	}

	@Test
	@DisplayName("a fresh preferences directory loads with both rows off")
	void freshSettings_shouldLoadOff() throws Exception {
		Path directory = Files.createTempDirectory("hd-sway");
		HdSettings.setDirectoryForTest(directory.toFile());
		HdSettings.load();
		assertAll(
			() -> assertFalse(HdSwayGate.foliageRequested()),
			() -> assertFalse(HdSwayGate.grassRequested())
		);
	}
}
