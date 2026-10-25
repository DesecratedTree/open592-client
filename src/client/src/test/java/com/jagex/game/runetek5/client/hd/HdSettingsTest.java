package com.jagex.game.runetek5.client.hd;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.jagex.game.runetek5.client.config.LaunchConfig;

@DisplayName("592HD settings state")
class HdSettingsTest {

	@TempDir
	Path home;

	private RecordingHdGraphicsBackend backend;

	@BeforeEach
	void resetState() {
		HdSettings.setDirectoryForTest(home.toFile());
		HdSettings.resetForTest();
		HdSettings.setDirectoryForTest(home.toFile());
		this.backend = new RecordingHdGraphicsBackend();
	}

	@AfterEach
	void clearStorageHook() {
		HdSettings.resetForTest();
	}

	@Test
	@DisplayName("a fresh install starts on Medium with the pipeline's values stored")
	void defaults_shouldBeMedium() {
		assertAll(
			() -> assertTrue(HdSettings.isEnabled()),
			() -> assertEquals(HdPreset.MEDIUM, HdSettings.preset()),
			() -> assertEquals(HdPreset.MEDIUM.value(HdFeature.PER_PIXEL_LIGHTING),
				HdSettings.value(HdFeature.PER_PIXEL_LIGHTING)),
			() -> assertEquals(HdPreset.MEDIUM.value(HdFeature.RENDER_DISTANCE),
				HdSettings.value(HdFeature.RENDER_DISTANCE))
		);
	}

	@Test
	@DisplayName("Off disables 592HD and writes no stock state")
	void off_shouldWriteNothing() {
		HdSettings.applyPreset(HdPreset.HIGH, this.backend);
		this.backend.clear();

		HdSettings.applyPreset(HdPreset.OFF, this.backend);

		assertAll(
			() -> assertFalse(HdSettings.isEnabled()),
			() -> assertTrue(this.backend.writes().isEmpty()),
			() -> assertEquals(0, this.backend.flushes())
		);
	}

	@Test
	@DisplayName("a preset writes only the rows this client can apply today")
	void preset_shouldApplyOnlyReadyRows() {
		HdSettings.applyPreset(HdPreset.HIGH, this.backend);

		assertAll(
			() -> assertTrue(this.backend.wrote(HdFeature.TEXTURES)),
			() -> assertTrue(this.backend.wrote(HdFeature.GROUND_BLENDING)),
			() -> assertTrue(this.backend.wrote(HdFeature.SHADOWS)),
			() -> assertTrue(this.backend.wrote(HdFeature.BLOOM)),
			() -> assertTrue(this.backend.wrote(HdFeature.ANTI_ALIASING)),
			() -> assertTrue(this.backend.wrote(HdFeature.RENDER_DISTANCE)),
			() -> assertFalse(this.backend.wrote(HdFeature.FXAA)),
			() -> assertFalse(this.backend.wrote(HdFeature.PER_PIXEL_LIGHTING)),
			() -> assertEquals(1, this.backend.flushes())
		);
	}

	@Test
	@DisplayName("cycling a ready row stores it, marks the preset custom and applies it")
	void cycle_shouldStoreAndApply() {
		HdSettings.set(HdFeature.TEXTURES, 1);
		this.backend.clear();

		int value = HdSettings.cycle(HdFeature.TEXTURES, this.backend);

		assertAll(
			() -> assertEquals(0, value),
			() -> assertEquals(0, HdSettings.value(HdFeature.TEXTURES)),
			() -> assertEquals(HdPreset.CUSTOM, HdSettings.preset()),
			() -> assertEquals(0, this.backend.valueOf(HdFeature.TEXTURES))
		);
	}

	@Test
	@DisplayName("cycling a pending row stores it without touching stock state")
	void cycle_shouldStorePendingWithoutApplying() {
		HdSettings.set(HdFeature.FXAA, 0);
		this.backend.clear();

		HdSettings.cycle(HdFeature.FXAA, this.backend);

		assertAll(
			() -> assertEquals(1, HdSettings.value(HdFeature.FXAA)),
			() -> assertTrue(this.backend.writes().isEmpty())
		);
	}

	@Test
	@DisplayName("cycling a renderer-owned row stores it without touching stock state")
	void cycle_shouldStoreRendererRowsWithoutStockWrites() {
		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 1);
		this.backend.clear();

		HdSettings.cycle(HdFeature.PER_PIXEL_LIGHTING, this.backend);

		assertAll(
			() -> assertEquals(0, HdSettings.value(HdFeature.PER_PIXEL_LIGHTING)),
			() -> assertTrue(this.backend.writes().isEmpty()),
			() -> assertEquals(0, this.backend.flushes())
		);
	}

	@Test
	@DisplayName("an unsupported row cannot be changed")
	void cycle_shouldIgnoreUnavailableRows() {
		// Interface scale is the honest example: this client has no whole-client
		// scale at all. Grass displacement is pending, not unavailable, so it
		// cycles like the other stored rows.
		HdSettings.set(HdFeature.INTERFACE_SCALE, 0);

		HdSettings.cycle(HdFeature.INTERFACE_SCALE, this.backend);

		assertEquals(0, HdSettings.value(HdFeature.INTERFACE_SCALE));
	}

	@Test
	@DisplayName("a stock install re-applies nothing at startup")
	void savedDeviations_shouldWriteNothingAtStock() {
		HdSettings.load();
		this.backend.clear();

		HdSettings.applySavedDeviations(this.backend);

		assertAll(
			() -> assertTrue(this.backend.writes().isEmpty()),
			() -> assertEquals(0, this.backend.flushes())
		);
	}

	@Test
	@DisplayName("a saved non-default row is re-applied once, and only that row")
	void savedDeviations_shouldRestoreChangedRows() {
		HdSettings.set(HdFeature.TEXTURES, 0);
		this.backend.clear();

		HdSettings.applySavedDeviations(this.backend);

		assertAll(
			() -> assertTrue(this.backend.wrote(HdFeature.TEXTURES)),
			() -> assertEquals(0, this.backend.valueOf(HdFeature.TEXTURES)),
			() -> assertEquals(1, this.backend.writes().size())
		);

		this.backend.clear();
		HdSettings.applySavedDeviations(this.backend);
		assertTrue(this.backend.writes().isEmpty(), "re-applied twice in one session");
	}

	@Test
	@DisplayName("a saved pending row is never pushed into stock state")
	void savedDeviations_shouldSkipPendingRows() {
		HdSettings.set(HdFeature.FXAA, 1);
		this.backend.clear();

		HdSettings.applySavedDeviations(this.backend);

		assertAll(
			() -> assertFalse(this.backend.wrote(HdFeature.FXAA)),
			() -> assertTrue(this.backend.writes().isEmpty())
		);
	}

	@Test
	@DisplayName("turning 592HD back on restores the chosen rows")
	void enable_shouldReapplyTheChosenRows() {
		HdSettings.set(HdFeature.TEXTURES, 0);
		HdSettings.setEnabled(false, this.backend);
		this.backend.clear();

		HdSettings.setEnabled(true, this.backend);

		assertAll(
			() -> assertTrue(HdSettings.isEnabled()),
			() -> assertEquals(0, this.backend.valueOf(HdFeature.TEXTURES)),
			() -> assertEquals(1, this.backend.flushes())
		);
	}

	@Test
	@DisplayName("first-run defaults wait for the renderer and are then applied once")
	void pendingDefaults_shouldWaitForThePipeline() {
		HdSettings.load();
		this.backend.clear();

		HdSettings.applyPendingDefaults(this.backend);

		assertAll(
			() -> assertFalse(HdSettings.isFirstRunApplied()),
			() -> assertTrue(this.backend.writes().isEmpty())
		);
	}

	@Test
	@DisplayName("settings survive a reload")
	void saveAndLoad_shouldRoundTrip() throws Exception {
		HdSettings.set(HdFeature.FXAA, 1);
		HdSettings.set(HdFeature.SHADOWS, 0);
		HdSettings.save();

		HdSettings.resetForTest();
		HdSettings.setDirectoryForTest(this.home.toFile());
		HdSettings.load();

		assertAll(
			() -> assertEquals(1, HdSettings.value(HdFeature.FXAA)),
			() -> assertEquals(0, HdSettings.value(HdFeature.SHADOWS)),
			() -> assertEquals(HdPreset.CUSTOM, HdSettings.preset())
		);
		File file = new File(this.home.toFile(), "hd.properties");
		assertTrue(file.isFile());
		String saved = new String(Files.readAllBytes(file.toPath()), StandardCharsets.ISO_8859_1);
		assertTrue(saved.contains("fxaa=1"));
	}

	@Test
	@DisplayName("a first load creates the properties file at the shipped default")
	void load_shouldCreateTheFile() {
		HdSettings.load();

		assertTrue(new File(this.home.toFile(), "hd.properties").isFile());
	}

	@Test
	@DisplayName("a backend that cannot apply is never written to")
	void apply_shouldSkipAnUnavailableBackend() {
		this.backend.setCanApply(false);

		HdSettings.applyPreset(HdPreset.HIGH, this.backend);

		assertTrue(this.backend.writes().isEmpty());
	}

	@Test
	@DisplayName("a launcher preset overrides the saved choice and leaves the file alone")
	void launchPreset_shouldOverrideWithoutRewritingTheFile() throws Exception {
		HdSettings.applyPreset(HdPreset.LOW, this.backend);
		File file = new File(this.home.toFile(), "hd.properties");
		byte[] before = Files.readAllBytes(file.toPath());

		System.setProperty("open592.hd.preset", "high");
		LaunchConfig.resetForTest();
		HdSettings.resetForTest();
		HdSettings.setDirectoryForTest(this.home.toFile());
		try {
			HdSettings.load();
			assertAll(
				() -> assertEquals(HdPreset.HIGH, HdSettings.preset()),
				() -> assertTrue(HdSettings.isEnabled()),
				() -> assertTrue(HdSettings.isFirstRunApplied(),
					"the pending-defaults pass must not save over the player's file"),
				() -> assertEquals(HdPreset.HIGH.value(HdFeature.TEXTURES),
					HdSettings.value(HdFeature.TEXTURES))
			);

			// The rows the launch preset changed are pushed without a save, and
			// the pending-defaults pass has nothing left to write.
			this.backend.clear();
			HdSettings.applyPendingDefaults(this.backend);
			assertTrue(this.backend.writes().isEmpty());
			HdSettings.applySavedDeviations(this.backend);
			assertAll(
				() -> assertTrue(this.backend.wrote(HdFeature.BLOOM)),
				() -> assertArrayEquals(before, Files.readAllBytes(file.toPath()))
			);
		} finally {
			System.clearProperty("open592.hd.preset");
			LaunchConfig.resetForTest();
		}
	}

	@Test
	@DisplayName("ingame leaves the saved choice in charge")
	void launchPresetIngame_shouldKeepTheSavedChoice() {
		HdSettings.applyPreset(HdPreset.LOW, this.backend);

		System.setProperty("open592.hd.preset", "ingame");
		LaunchConfig.resetForTest();
		try {
			HdSettings.load();
			assertAll(
				() -> assertEquals(HdPreset.LOW, HdSettings.preset()),
				() -> assertEquals(HdPreset.LOW.value(HdFeature.TEXTURES),
					HdSettings.value(HdFeature.TEXTURES))
			);
		} finally {
			System.clearProperty("open592.hd.preset");
			LaunchConfig.resetForTest();
		}
	}
}
