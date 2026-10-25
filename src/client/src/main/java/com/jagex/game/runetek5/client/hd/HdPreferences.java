package com.jagex.game.runetek5.client.hd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Persists the 592HD settings next to the other open592 preferences.
 *	 * <p>Each feature is written under its stable {@link HdFeature#key()} as the
	 * value's index, so rewording a label never invalidates a saved file. A value
	 * outside a feature's range falls back to that feature's default on load,
	 * which is what keeps a hand-edited or stale file inside the pane's
	 * vocabulary without widening its table.
 * I/O failures are swallowed: HD settings are a convenience, and a missing or
 * corrupt file falls back to the shipped preset rather than failing startup.
 */
final class HdPreferences {

	/** Subdirectory of the user home holding this client's local settings. */
	private static final String DIRECTORY_NAME = ".open592";

	private static final String FILE_NAME = "hd.properties";

	private static final String KEY_VERSION = "version";

	private static final String KEY_APPLIED = "applied";

	private static final String KEY_ENABLED = "enabled";

	private static final String KEY_PRESET = "preset";

	/** Written into every save so a future format change can migrate. */
	private static final int VERSION = 1;

	/** Test hook: redirects the properties file (null restores the default). */
	private static File directoryOverride;

	private HdPreferences() {
	}

	static File file() {
		File directory = directoryOverride;
		if (directory == null) {
			directory = new File(System.getProperty("user.home"), DIRECTORY_NAME);
		}
		return new File(directory, FILE_NAME);
	}

	/** Test hook: redirects the properties file under the given directory. */
	static void setDirectoryForTest(File directory) {
		directoryOverride = directory;
	}

	/** Forgets the redirect, as a fresh process would. */
	static void resetForTest() {
		directoryOverride = null;
	}

	static boolean exists() {
		return file().isFile();
	}

	/**
	 * Reads the stored settings into {@code settings}.
	 *
	 * @return false when no readable file exists, leaving {@code settings} at
	 *         its current (default) values
	 */
	static boolean load() {
		Properties properties = readProperties();
		if (properties.isEmpty()) {
			return false;
		}
		for (HdFeature feature : HdFeature.values()) {
			if (properties.getProperty(feature.key()) == null) {
				// Key absent: keep whatever the current preset chose.
				continue;
			}
			int value = parse(properties, feature.key(), -1);
			boolean inRange = value >= 0 && value < feature.valueCount();
			// A key that is present but stale or garbage means that row is
			// unusable; fall back to the row's own default so one bad line
			// cannot silently inherit a different preset's choice.
			HdSettings.setValueForLoad(feature, inRange ? value : feature.defaultValue());
		}
		HdSettings.setEnabledForLoad(parseBoolean(properties, KEY_ENABLED, true));
		HdSettings.setPresetForLoad(HdPreset.forKey(properties.getProperty(KEY_PRESET)));
		HdSettings.setFirstRunAppliedForLoad(parseBoolean(properties, KEY_APPLIED, true));
		return true;
	}

	static void save() {
		Properties properties = new Properties();
		properties.setProperty(KEY_VERSION, Integer.toString(VERSION));
		properties.setProperty(KEY_APPLIED, Boolean.toString(HdSettings.isFirstRunApplied()));
		properties.setProperty(KEY_ENABLED, Boolean.toString(HdSettings.isEnabled()));
		HdPreset preset = HdSettings.preset();
		properties.setProperty(KEY_PRESET, preset == null ? HdPreset.CUSTOM.name() : preset.name());
		for (HdFeature feature : HdFeature.values()) {
			properties.setProperty(feature.key(), Integer.toString(HdSettings.value(feature)));
		}
		File file = file();
		try {
			File parent = file.getParentFile();
			if (parent != null) {
				parent.mkdirs();
			}
			OutputStream out = new FileOutputStream(file);
			try {
				properties.store(out, "open592 HD settings");
			} finally {
				out.close();
			}
		} catch (IOException ignored) {
			// Persistence is best-effort; the session keeps running.
		} catch (SecurityException ignored) {
			// Sandboxed runtimes may forbid local file writes.
		}
	}

	private static int parse(Properties properties, String key, int fallback) {
		String value = properties.getProperty(key);
		if (value == null) {
			return fallback;
		}
		try {
			return Integer.parseInt(value.trim());
		} catch (NumberFormatException ex) {
			return fallback;
		}
	}

	private static boolean parseBoolean(Properties properties, String key, boolean fallback) {
		String value = properties.getProperty(key);
		if (value == null) {
			return fallback;
		}
		String trimmed = value.trim();
		if (trimmed.equalsIgnoreCase("true")) {
			return true;
		}
		if (trimmed.equalsIgnoreCase("false")) {
			return false;
		}
		return fallback;
	}

	private static Properties readProperties() {
		Properties properties = new Properties();
		File file = file();
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
			// Corrupt or unreadable file means the shipped default.
		} catch (SecurityException ignored) {
			// Sandboxed runtimes may forbid local file reads.
		}
		return properties;
	}
}
