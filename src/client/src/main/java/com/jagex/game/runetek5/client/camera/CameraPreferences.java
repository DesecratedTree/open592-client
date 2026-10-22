package com.jagex.game.runetek5.client.camera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Persists the OSHD-style camera zoom and fog distance targets across
 * sessions in a local properties file.
 *
 * <p>Saved values are re-clamped on load, so a hand-edited or stale file
 * can never push the camera outside its legal range. All I/O failures are
 * swallowed: a missing or corrupt file simply means stock defaults, and a
 * failed save leaves the running session untouched.
 *
 * <p>Levels are written in the current percent-of-stock units and stamped with
 * {@link #KEY_VERSION}. A file without that stamp predates the switch to
 * percent levels and is read on the old 256-per-100% scale, then converted, so
 * a level the player set before the change is not silently reinterpreted.
 */
public final class CameraPreferences {

	/** Subdirectory of the user home holding this client's local settings. */
	private static final String DIRECTORY_NAME = ".open592";

	private static final String FILE_NAME = "camera.properties";

	private static final String KEY_ZOOM = "cameraZoom";

	private static final String KEY_FOG = "fogDistance";

	private static final String KEY_VERSION = "version";

	/** Written into every save; files without it hold pre-percent levels. */
	private static final int VERSION = 2;

	/** Level that meant 100% before levels became percentages of stock. */
	private static final int LEGACY_STOCK = 256;

	/** Test hook: redirects the properties file (null restores the default). */
	private static File directoryOverride;

	private CameraPreferences() {
	}

	/**
	 * Loads persisted zoom and fog targets, snapping both ends so the view
	 * starts exactly there instead of gliding on launch. Falls back to
	 * stock defaults when nothing valid was saved.
	 */
	public static void load() {
		Properties properties = readProperties();
		boolean legacy = parseVersion(properties) < VERSION;
		CameraZoom.snapTo(parseLevel(properties, KEY_ZOOM, CameraZoom.ZOOM_DEFAULT, legacy));
		FogDistance.snapTo(parseLevel(properties, KEY_FOG, FogDistance.FOG_DEFAULT, legacy));
	}

	/** Persists the current zoom and fog targets. Safe to call every change. */
	public static void save() {
		Properties properties = new Properties();
		properties.setProperty(KEY_VERSION, Integer.toString(VERSION));
		properties.setProperty(KEY_ZOOM, Integer.toString(CameraZoom.getTargetZoom()));
		properties.setProperty(KEY_FOG, Integer.toString(FogDistance.getTargetFog()));
		File file = propertiesFile();
		try {
			File parent = file.getParentFile();
			if (parent != null) {
				parent.mkdirs();
			}
			OutputStream out = new FileOutputStream(file);
			try {
				properties.store(out, "open592 camera zoom and fog distance");
			} finally {
				out.close();
			}
		} catch (IOException ignored) {
			// Persistence is best-effort; the session keeps running.
		} catch (SecurityException ignored) {
			// Sandboxed runtimes may forbid local file writes.
		}
	}

	/** Test hook: redirects the properties file under the given directory. */
	static void setDirectoryForTest(File directory) {
		directoryOverride = directory;
	}

	/**
	 * Reads one level, converting pre-percent values when the file is legacy.
	 * A missing key or unparsable value falls back to the current default,
	 * which is already in percent units and must not be converted.
	 */
	private static int parseLevel(Properties properties, String key, int fallback, boolean legacy) {
		String value = properties.getProperty(key);
		if (value == null) {
			return fallback;
		}
		int level;
		try {
			level = Integer.parseInt(value.trim());
		} catch (NumberFormatException ex) {
			return fallback;
		}
		return legacy ? legacyToPercent(level) : level;
	}

	/** Rescales a level recorded on the old 256-per-100% scale. */
	private static int legacyToPercent(int legacyLevel) {
		return (int) Math.round((double) legacyLevel * 100.0D / (double) LEGACY_STOCK);
	}

	private static int parseVersion(Properties properties) {
		String value = properties.getProperty(KEY_VERSION);
		if (value == null) {
			return 1;
		}
		try {
			return Integer.parseInt(value.trim());
		} catch (NumberFormatException ex) {
			return 1;
		}
	}

	private static Properties readProperties() {
		Properties properties = new Properties();
		File file = propertiesFile();
		if (!file.isFile()) {
			return properties;
		}
		try {
			InputStream in = new FileInputStream(file);
			try {
				properties.load(in);
			} finally {
				in.close();
			}
		} catch (IOException ignored) {
			// Corrupt or unreadable file means stock defaults.
		} catch (SecurityException ignored) {
			// Sandboxed runtimes may forbid local file reads.
		}
		return properties;
	}

	private static File propertiesFile() {
		File directory = directoryOverride;
		if (directory == null) {
			directory = new File(System.getProperty("user.home"), DIRECTORY_NAME);
		}
		return new File(directory, FILE_NAME);
	}
}
