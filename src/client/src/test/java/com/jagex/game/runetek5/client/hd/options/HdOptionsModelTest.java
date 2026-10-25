package com.jagex.game.runetek5.client.hd.options;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdPreset;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.client.hd.HdSupport;
import com.jagex.game.runetek5.client.hd.HdTab;
import com.jagex.game.runetek5.client.hd.RecordingHdGraphicsBackend;

@DisplayName("592HD pane model")
class HdOptionsModelTest {

	@TempDir
	Path home;

	private HdOptionsModel model;

	private RecordingHdGraphicsBackend backend;

	@BeforeEach
	void resetState() {
		HdSettings.setDirectoryForTest(this.home.toFile());
		HdSettings.resetForTest();
		HdSettings.setDirectoryForTest(this.home.toFile());
		this.model = new HdOptionsModel();
		this.backend = new RecordingHdGraphicsBackend();
	}

	@AfterEach
	void clearStorageHook() {
		HdSettings.resetForTest();
	}

	@Test
	@DisplayName("the pane opens on the Lighting tab with that tab's rows")
	void defaults_shouldShowLighting() {
		assertAll(
			() -> assertEquals(HdTab.LIGHTING, this.model.tab()),
			() -> assertEquals(HdFeature.forTab(HdTab.LIGHTING).length, this.model.rowCount()),
			() -> assertEquals(HdFeature.FOG.label(), this.model.rowLabel(0))
		);
	}

	@Test
	@DisplayName("selecting a tab swaps the row set")
	void tabAction_shouldSwapRows() {
		this.model.apply(HdOptionsAction.tab(HdTab.WORLD.ordinal()), this.backend);

		assertAll(
			() -> assertEquals(HdTab.WORLD, this.model.tab()),
			() -> assertEquals(HdFeature.TEXTURES, this.model.rows()[0]),
			() -> assertEquals(HdFeature.TEXTURES.label(), this.model.rowLabel(0))
		);
	}

	@Test
	@DisplayName("a row click cycles the value and applies it")
	void rowAction_shouldCycle() {
		int shadowsRow = indexOf(HdTab.LIGHTING, HdFeature.SHADOWS);
		int before = HdSettings.value(HdFeature.SHADOWS);

		this.model.apply(HdOptionsAction.row(shadowsRow), this.backend);

		assertAll(
			() -> assertEquals((before + 1) % HdFeature.SHADOWS.valueCount(), HdSettings.value(HdFeature.SHADOWS)),
			() -> assertEquals(HdSettings.value(HdFeature.SHADOWS), this.backend.valueOf(HdFeature.SHADOWS))
		);
	}

	@Test
	@DisplayName("a preset click applies and saves it")
	void presetAction_shouldApplyPreset() {
		this.model.apply(HdOptionsAction.preset(3), this.backend);

		assertAll(
			() -> assertEquals(HdPreset.HIGH, HdSettings.preset()),
			() -> assertTrue(HdSettings.isEnabled()),
			() -> assertEquals(HdPreset.HIGH.value(HdFeature.FXAA), HdSettings.value(HdFeature.FXAA))
		);
	}

	@Test
	@DisplayName("a preset click on Off disables HD and writes nothing")
	void presetAction_offShouldWriteNothing() {
		this.backend.clear();
		this.model.apply(HdOptionsAction.preset(0), this.backend);

		assertAll(
			() -> assertFalse(HdSettings.isEnabled()),
			() -> assertEquals(HdPreset.OFF, HdSettings.preset()),
			() -> assertTrue(this.backend.writes().isEmpty())
		);
	}

	@Test
	@DisplayName("Open and Back are the screen's to handle, not the model's")
	void openAndBack_shouldNotBeModelActions() {
		assertAll(
			() -> assertFalse(this.model.apply(HdOptionsAction.open(), this.backend)),
			() -> assertFalse(this.model.apply(HdOptionsAction.back(), this.backend)),
			() -> assertFalse(this.model.apply(null, this.backend))
		);
	}

	@Test
	@DisplayName("row presentation carries the value, support and restart marker")
	void rows_shouldDescribeTheirState() {
		this.model.apply(HdOptionsAction.tab(HdTab.DISPLAY.ordinal()), this.backend);
		int aaRow = indexOf(HdTab.DISPLAY, HdFeature.ANTI_ALIASING);

		assertAll(
			() -> assertEquals(HdSupport.READY, this.model.rowSupport(0)),
			() -> assertTrue(this.model.rowLabel(aaRow).contains("(restart)")),
			() -> assertEquals(HdSettings.valueLabel(HdFeature.ANTI_ALIASING), this.model.rowValueLabel(aaRow))
		);
	}

	@Test
	@DisplayName("the value cell carries the marker the legend names")
	void rowValueText_shouldCarryTheSupportMarker() {
		int fogRow = indexOf(HdTab.LIGHTING, HdFeature.FOG);
		int skyRow = indexOf(HdTab.LIGHTING, HdFeature.SKY);
		int carriedRow = indexOf(HdTab.LIGHTING, HdFeature.CARRIED_LIGHT);

		assertAll(
			() -> assertEquals(HdSettings.valueLabel(HdFeature.FOG), this.model.rowValueText(fogRow),
				"a live row must not carry a marker"),
			() -> assertEquals(HdSettings.valueLabel(HdFeature.SKY), this.model.rowValueText(skyRow),
				"the sky is live now, so it carries no marker either"),
			// The lighting tab has no pending rows left: both scene-light rows are
			// live, so the value cell is the plain value.
			() -> assertEquals(HdSettings.valueLabel(HdFeature.CARRIED_LIGHT), this.model.rowValueText(carriedRow))
		);

		this.model.apply(HdOptionsAction.tab(HdTab.WORLD.ordinal()), this.backend);
		int texturesRow = indexOf(HdTab.WORLD, HdFeature.WORLD_TEXTURES);
		int reflectionsRow = indexOf(HdTab.WORLD, HdFeature.REFLECTIONS);
		int causticsRow = indexOf(HdTab.WORLD, HdFeature.CAUSTICS);
		int waterRow = indexOf(HdTab.WORLD, HdFeature.WATER);
		assertAll(
			() -> assertEquals(HdSettings.valueLabel(HdFeature.WORLD_TEXTURES), this.model.rowValueText(texturesRow),
				"the world-texture page is live now, so it carries no marker"),
			// Pending and unavailable are still different claims and must not share
			// a marker: the stock water row cannot ever do anything in this client,
			// while the rows above are live and carry no marker at all.
			() -> assertEquals(HdSettings.valueLabel(HdFeature.REFLECTIONS), this.model.rowValueText(reflectionsRow),
				"a live row carries no marker"),
			() -> assertEquals(HdSettings.valueLabel(HdFeature.CAUSTICS), this.model.rowValueText(causticsRow),
				"a live row carries no marker"),
			() -> assertEquals(HdSettings.valueLabel(HdFeature.WATER) + " "
				+ HdSupport.UNAVAILABLE.marker(), this.model.rowValueText(waterRow))
		);
	}

	@Test
	@DisplayName("the legend flags follow the tab's rows")
	void legend_shouldFollowTheTab() {
		// The parity rows are on this tab before their renderer is: the legend
		// must say so rather than let a stored choice look live.
		assertAll(
			() -> assertTrue(this.model.hasPendingRows()),
			() -> assertFalse(this.model.hasUnavailableRows()),
			() -> assertEquals(HdSupport.READY, this.model.rowSupport(
				indexOf(HdTab.LIGHTING, HdFeature.CUTOUT_SHADOWS)))
		);
		this.model.apply(HdOptionsAction.tab(HdTab.WORLD.ordinal()), this.backend);
		assertTrue(this.model.hasUnavailableRows());
	}

	private static int indexOf(HdTab tab, HdFeature feature) {
		HdFeature[] rows = HdFeature.forTab(tab);
		for (int i = 0; i < rows.length; i++) {
			if (rows[i] == feature) {
				return i;
			}
		}
		return -1;
	}
}
