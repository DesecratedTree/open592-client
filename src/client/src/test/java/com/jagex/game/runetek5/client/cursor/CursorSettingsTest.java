package com.jagex.game.runetek5.client.cursor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("CursorSettings custom cursor switch")
class CursorSettingsTest {

	@TempDir
	Path tempDir;

	@BeforeEach
	void redirectSettings() {
		CursorSettings.reset();
		CursorSettings.setDirectoryForTest(tempDir.toFile());
	}

	@AfterEach
	void restoreSettings() {
		CursorSettings.setDirectoryForTest(null);
		CursorSettings.reset();
	}

	@Test
	@DisplayName("cursors are on until the player turns them off")
	void isEnabled_should_defaultToOn() {
		assertTrue(CursorSettings.isEnabled());
		assertTrue(CursorSettings.DEFAULT_ENABLED);
	}

	@Test
	@DisplayName("toggle flips the switch and reports the new value")
	void toggle_should_flipAndReport() {
		assertFalse(CursorSettings.toggle());
		assertFalse(CursorSettings.isEnabled());
		assertTrue(CursorSettings.toggle());
		assertTrue(CursorSettings.isEnabled());
	}

	@Test
	@DisplayName("save then load restores a disabled switch")
	void saveLoad_should_restoreDisabled() {
		CursorSettings.setEnabled(false);
		CursorSettings.save();

		CursorSettings.reset();
		CursorSettings.load();

		assertFalse(CursorSettings.isEnabled());
	}

	@Test
	@DisplayName("save writes the flag under its own key")
	void save_should_writeFlag() throws Exception {
		CursorSettings.setEnabled(true);
		CursorSettings.save();

		Properties properties = new Properties();
		InputStream in = new FileInputStream(new File(tempDir.toFile(), "cursor.properties"));
		try {
			properties.load(in);
		} finally {
			in.close();
		}
		assertEquals("true", properties.getProperty("customCursor"));
	}

	@Test
	@DisplayName("load leaves the default alone when no file was saved")
	void load_should_keepDefault_when_fileMissing() {
		assertDoesNotThrow(CursorSettings::load);
		assertTrue(CursorSettings.isEnabled());
	}

	@Test
	@DisplayName("load ignores a corrupt file instead of failing")
	void load_should_keepDefault_when_fileCorrupt() throws Exception {
		writeRaw("customCursor=\n");
		assertDoesNotThrow(CursorSettings::load);
		assertTrue(CursorSettings.isEnabled());

		writeRaw("customCursor=not-a-boolean\n");
		assertDoesNotThrow(CursorSettings::load);
		assertTrue(CursorSettings.isEnabled());
	}

	private void writeRaw(String content) throws Exception {
		Writer writer = new FileWriter(new File(tempDir.toFile(), "cursor.properties"));
		try {
			writer.write(content);
		} finally {
			writer.close();
		}
	}
}
