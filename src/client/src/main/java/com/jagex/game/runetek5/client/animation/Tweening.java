package com.jagex.game.runetek5.client.animation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.jagex.game.runetek5.deob.OutputStream_Sub1;

/**
 * Whether animations interpolate between keyframes.
 *
 * <p>The renderers ask {@code OutputStream_Sub1.aBoolean244} on every animation
 * lookup, and stock only sets that flag from the dev console's {@code tween}
 * command, so a fresh client plays every sequence frame-by-frame until someone
 * types it. This class owns the value instead: tweening is on by default, the
 * console toggles through here so its messages and the flag cannot drift, and
 * the choice is remembered in a local properties file so a player who prefers
 * stepped animation keeps it across restarts.
 *
 * <p>Sequences that already ask to tween are unaffected — this only forces the
 * ones that would otherwise step.
 */
public final class Tweening {

	/** Subdirectory of the user home holding this client's local settings. */
	private static final String DIRECTORY_NAME = ".open592";

	private static final String FILE_NAME = "tween.properties";

	private static final String KEY_ENABLED = "tweening";

	/** On by default: the smoother animation is what the renderers are built for. */
	public static final boolean DEFAULT_ENABLED = true;

	private static boolean enabled = DEFAULT_ENABLED;

	/** Test hook: redirects the properties file (null restores the default). */
	private static File directoryOverride;

	private Tweening() {
	}

	public static boolean isEnabled() {
		return enabled;
	}

	/** Sets the flag and pushes it to the renderers' shared field. */
	public static void setEnabled(boolean value) {
		enabled = value;
		OutputStream_Sub1.aBoolean244 = value;
	}

	/**
	 * Flips the flag.
	 *
	 * @return the value now in effect
	 */
	public static boolean toggle() {
		setEnabled(!enabled);
		return enabled;
	}

	/**
	 * Loads the remembered choice and pushes it to the renderers. A missing
	 * file keeps the default, and so does an unrecognised value:
	 * {@code Boolean.parseBoolean} would quietly read garbage as {@code false}
	 * and switch tweening off.
	 */
	public static void load() {
		Properties properties = readProperties();
		String value = properties.getProperty(KEY_ENABLED);
		if (value != null) {
			String trimmed = value.trim();
			if (trimmed.equalsIgnoreCase("true")) {
				enabled = true;
			} else if (trimmed.equalsIgnoreCase("false")) {
				enabled = false;
			}
		}
		OutputStream_Sub1.aBoolean244 = enabled;
	}

	/** Persists the choice. Safe to call on every change. */
	public static void save() {
		Properties properties = new Properties();
		properties.setProperty(KEY_ENABLED, Boolean.toString(enabled));
		File file = propertiesFile();
		try {
			File parent = file.getParentFile();
			if (parent != null) {
				parent.mkdirs();
			}
			OutputStream out = new FileOutputStream(file);
			try {
				properties.store(out, "open592 animation tweening");
			} finally {
				out.close();
			}
		} catch (IOException ignored) {
			// Persistence is best-effort; the session keeps running.
		} catch (SecurityException ignored) {
			// Sandboxed runtimes may forbid local file writes.
		}
	}

	/** Restores the shipped default and pushes it. */
	public static void reset() {
		setEnabled(DEFAULT_ENABLED);
	}

	/** Test hook: redirects the properties file under the given directory. */
	static void setDirectoryForTest(File directory) {
		directoryOverride = directory;
	}

	/** Test hook: sets the flag without touching the file. */
	static void setForTest(boolean value) {
		setEnabled(value);
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
			// Corrupt or unreadable file means the default.
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
