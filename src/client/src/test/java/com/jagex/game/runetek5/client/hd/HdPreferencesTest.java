package com.jagex.game.runetek5.client.hd;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("592HD properties file")
class HdPreferencesTest {

	@TempDir
	Path home;

	@BeforeEach
	void redirectStorage() {
		HdSettings.setDirectoryForTest(home.toFile());
		HdSettings.resetForTest();
		HdSettings.setDirectoryForTest(home.toFile());
	}

	@AfterEach
	void clearStorageHook() {
		HdSettings.resetForTest();
	}

	@Test
	@DisplayName("a missing file leaves the shipped defaults")
	void load_shouldKeepDefaults_whenFileMissing() {
		assertFalse(HdPreferences.load());
		assertEquals(HdPreset.MEDIUM, HdSettings.preset());
	}

	@Test
	@DisplayName("an out-of-range value falls back instead of widening the pane")
	void load_shouldIgnoreOutOfRangeValues() throws Exception {
		write(new String[] { "shadows=99", "fxaa=-4", "water=7" });

		HdPreferences.load();

		assertAll(
			() -> assertEquals(HdFeature.SHADOWS.defaultValue(), HdSettings.value(HdFeature.SHADOWS)),
			() -> assertEquals(0, HdSettings.value(HdFeature.FXAA)),
			() -> assertEquals(0, HdSettings.value(HdFeature.WATER))
		);
	}

	@Test
	@DisplayName("an unparsable value falls back")
	void load_shouldIgnoreGarbage() throws Exception {
		write(new String[] { "fxaa=lots", "shadows=" });

		HdPreferences.load();

		assertAll(
			() -> assertEquals(0, HdSettings.value(HdFeature.FXAA)),
			() -> assertEquals(0, HdSettings.value(HdFeature.SHADOWS))
		);
	}

	@Test
	@DisplayName("enabled and preset survive the round trip")
	void save_shouldRoundTripTheSwitch() {
		HdSettings.applyPreset(HdPreset.HIGH, new RecordingHdGraphicsBackend());
		HdSettings.save();

		HdSettings.resetForTest();
		HdSettings.setDirectoryForTest(home.toFile());
		HdSettings.load();

		assertAll(
			() -> assertTrue(HdSettings.isEnabled()),
			() -> assertEquals(HdPreset.HIGH, HdSettings.preset()),
			() -> assertEquals(HdPreset.HIGH.value(HdFeature.FXAA), HdSettings.value(HdFeature.FXAA))
		);
	}

	@Test
	@DisplayName("Off is remembered as disabled")
	void save_shouldRememberOff() {
		HdSettings.applyPreset(HdPreset.OFF, new RecordingHdGraphicsBackend());
		HdSettings.save();

		HdSettings.resetForTest();
		HdSettings.setDirectoryForTest(home.toFile());
		HdSettings.load();

		assertFalse(HdSettings.isEnabled());
	}

	@Test
	@DisplayName("the file is stamped with a version and the first-run flag")
	void save_shouldStampVersionAndApplied() throws Exception {
		HdSettings.save();

		Properties properties = new Properties();
		java.io.InputStream in = new java.io.FileInputStream(new File(home.toFile(), "hd.properties"));
		try {
			properties.load(in);
		} finally {
			in.close();
		}
		assertAll(
			() -> assertTrue(properties.containsKey("version")),
			() -> assertTrue(properties.containsKey("applied")),
			() -> assertTrue(properties.containsKey("preset"))
		);
	}

	private void write(String[] lines) throws Exception {
		File file = new File(home.toFile(), "hd.properties");
		OutputStream out = new FileOutputStream(file);
		try {
			for (String line : lines) {
				out.write((line + System.lineSeparator()).getBytes("ISO-8859-1"));
			}
		} finally {
			out.close();
		}
	}
}
