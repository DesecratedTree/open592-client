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
		CameraZoom.setTarget(400);
		FogDistance.setTarget(128);
		CameraPreferences.save();

		CameraZoom.reset();
		FogDistance.reset();
		CameraPreferences.load();

		assertEquals(400, CameraZoom.getTargetZoom());
		assertEquals(400, CameraZoom.getCurrentZoom());
		assertEquals(128, FogDistance.getTargetFog());
		assertEquals(128, FogDistance.getCurrentFog());
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
	@DisplayName("save writes both zoom and fog keys")
	void save_should_writeBothKeys() throws Exception {
		CameraZoom.setTarget(320);
		FogDistance.setTarget(400);
		CameraPreferences.save();

		Properties properties = new Properties();
		InputStream in = new FileInputStream(new File(tempDir.toFile(), "camera.properties"));
		try {
			properties.load(in);
		} finally {
			in.close();
		}
		assertEquals("320", properties.getProperty("cameraZoom"));
		assertEquals("400", properties.getProperty("fogDistance"));
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
