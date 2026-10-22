package com.jagex.game.runetek5.client.camera;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CameraPreferencesTest {

	@TempDir
	Path tempDir;

	@BeforeEach
	void redirectPreferences() {
		CameraZoom.reset();
		FogDistance.reset();
		CameraPreferences.setDirectoryForTest(tempDir.toFile());
	}

	@AfterEach
	void restorePreferences() {
		CameraPreferences.setDirectoryForTest(null);
		CameraZoom.reset();
		FogDistance.reset();
	}

	@Test
	@DisplayName("save then load restores zoom and fog without smoothing sweep")
	void saveLoad_should_restoreTargets_when_roundTripped() {
		CameraZoom.setTarget(155);
		FogDistance.setTarget(105);
		CameraPreferences.save();

		CameraZoom.reset();
		FogDistance.reset();
		CameraPreferences.load();

		assertEquals(155, CameraZoom.getTargetZoom());
		assertEquals(155, CameraZoom.getCurrentZoom());
		assertEquals(105, FogDistance.getTargetFog());
		assertEquals(105, FogDistance.getCurrentFog());
	}

	@Test
	@DisplayName("load falls back to defaults when no file was saved")
	void load_should_useDefaults_when_fileMissing() {
		assertDoesNotThrow(CameraPreferences::load);
		assertEquals(CameraZoom.ZOOM_DEFAULT, CameraZoom.getTargetZoom());
		assertEquals(FogDistance.FOG_DEFAULT, FogDistance.getTargetFog());
	}

	@Test
	@DisplayName("load clamps out-of-range and corrupt values")
	void load_should_clamp_when_fileHasBadValues() throws Exception {
		writeRaw("cameraZoom=99999\nfogDistance=not-a-number\n");
		CameraPreferences.load();
		assertEquals(CameraZoom.ZOOM_MAX, CameraZoom.getTargetZoom());
		assertEquals(FogDistance.FOG_DEFAULT, FogDistance.getTargetFog());

		writeRaw("cameraZoom=-5\nfogDistance=99999\n");
		CameraPreferences.load();
		assertEquals(CameraZoom.ZOOM_MIN, CameraZoom.getTargetZoom());
		assertEquals(FogDistance.FOG_MAX, FogDistance.getTargetFog());
	}

	@Test
	@DisplayName("a file saved before levels became percentages is converted, not reread")
	void load_should_convertLegacyLevels_whenFileHasNoVersion() throws Exception {
		// 512 and 256 were 200% and 100% on the old 256-per-100% scale.
		writeRaw("cameraZoom=512\nfogDistance=256\n");
		CameraPreferences.load();

		assertEquals(CameraZoom.ZOOM_MAX, CameraZoom.getTargetZoom());
		assertEquals(FogDistance.FOG_STOCK, FogDistance.getTargetFog());
	}

	@Test
	@DisplayName("a versioned file is read as-is even when a level looks legacy-sized")
	void load_should_notConvert_whenFileIsCurrent() throws Exception {
		writeRaw("version=2\ncameraZoom=160\nfogDistance=165\n");
		CameraPreferences.load();

		assertEquals(160, CameraZoom.getTargetZoom());
		assertEquals(165, FogDistance.getTargetFog());
	}

	@Test
	@DisplayName("save writes both zoom and fog keys")
	void save_should_writeBothKeys() throws Exception {
		CameraZoom.setTarget(155);
		FogDistance.setTarget(160);
		CameraPreferences.save();

		Properties properties = new Properties();
		InputStream in = new FileInputStream(new File(tempDir.toFile(), "camera.properties"));
		try {
			properties.load(in);
		} finally {
			in.close();
		}
		assertEquals("2", properties.getProperty("version"));
		assertEquals("155", properties.getProperty("cameraZoom"));
		assertEquals("160", properties.getProperty("fogDistance"));
	}

	private void writeRaw(String content) throws Exception {
		Writer writer = new FileWriter(new File(tempDir.toFile(), "camera.properties"));
		try {
			writer.write(content);
		} finally {
			writer.close();
		}
	}
}
