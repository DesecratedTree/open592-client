package com.jagex.game.runetek5.client.animation;

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

import com.jagex.game.runetek5.deob.OutputStream_Sub1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("Tweening default, renderer flag and saved choice")
class TweeningTest {

	@TempDir
	Path tempDir;

	@BeforeEach
	void redirectSettings() {
		Tweening.reset();
		Tweening.setDirectoryForTest(tempDir.toFile());
	}

	@AfterEach
	void restoreSettings() {
		Tweening.setDirectoryForTest(null);
		Tweening.reset();
	}

	@Test
	@DisplayName("tweening is on before anyone touches the console")
	void should_beEnabled_by_default() {
		assertTrue(Tweening.DEFAULT_ENABLED);
		assertTrue(Tweening.isEnabled());
	}

	@Test
	@DisplayName("the startup push turns the renderers' flag on")
	void reset_should_enableRendererFlag() {
		Tweening.setForTest(false);
		assertFalse(OutputStream_Sub1.aBoolean244);

		Tweening.reset();

		assertTrue(Tweening.isEnabled());
		assertTrue(OutputStream_Sub1.aBoolean244);
	}

	@Test
	@DisplayName("toggle drives the renderer flag and reports the new state")
	void toggle_should_driveRendererFlag() {
		assertFalse(Tweening.toggle());
		assertFalse(OutputStream_Sub1.aBoolean244);

		assertTrue(Tweening.toggle());
		assertTrue(OutputStream_Sub1.aBoolean244);
	}

	@Test
	@DisplayName("save then load restores a disabled choice and pushes it")
	void saveLoad_should_restoreDisabled() {
		Tweening.setForTest(false);
		Tweening.save();

		Tweening.reset();
		assertTrue(OutputStream_Sub1.aBoolean244);

		Tweening.load();

		assertFalse(Tweening.isEnabled());
		assertFalse(OutputStream_Sub1.aBoolean244);
	}

	@Test
	@DisplayName("save writes the choice under its own key")
	void save_should_writeFlag() throws Exception {
		Tweening.setForTest(false);
		Tweening.save();

		Properties properties = new Properties();
		InputStream in = new FileInputStream(new File(tempDir.toFile(), "tween.properties"));
		try {
			properties.load(in);
		} finally {
			in.close();
		}
		assertEquals("false", properties.getProperty("tweening"));
	}

	@Test
	@DisplayName("load keeps the default when no file was saved")
	void load_should_keepDefault_when_fileMissing() {
		assertDoesNotThrow(Tweening::load);
		assertTrue(Tweening.isEnabled());
		assertTrue(OutputStream_Sub1.aBoolean244);
	}

	@Test
	@DisplayName("load ignores a corrupt file instead of switching tweening off")
	void load_should_keepDefault_when_fileCorrupt() throws Exception {
		writeRaw("tweening=\n");
		assertDoesNotThrow(Tweening::load);
		assertTrue(Tweening.isEnabled());

		writeRaw("tweening=not-a-boolean\n");
		assertDoesNotThrow(Tweening::load);
		assertTrue(Tweening.isEnabled());
		assertTrue(OutputStream_Sub1.aBoolean244);
	}

	private void writeRaw(String content) throws Exception {
		Writer writer = new FileWriter(new File(tempDir.toFile(), "tween.properties"));
		try {
			writer.write(content);
		} finally {
			writer.close();
		}
	}
}
