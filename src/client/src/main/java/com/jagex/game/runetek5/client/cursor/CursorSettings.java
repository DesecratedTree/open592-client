package com.jagex.game.runetek5.client.cursor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Whether the client installs the game's cursor set on the canvas.
 *
 * <p>The stock client asks for a custom cursor whenever the pointer hovers
 * something with a cursor type, but the request is gated by a preference byte
 * carried in the server's saved-player-settings packet. When that byte is off
 * — which is the usual state on a private server whose options interface has
 * no custom-cursor toggle — there is no way for the player to turn the cursors
 * back on. This class owns the switch instead: it is on by default, reachable
 * from the dev console ({@code cursor}, {@code cursoron}, {@code cursoroff}),
 * and remembered in a local properties file, so the option survives a restart
 * without depending on the server.
 *
 * <p>The in-game toggle still works: the cs2 opcodes write and read through
 * this class, so the interface and the console always agree.
 */
public final class CursorSettings {

	/** Subdirectory of the user home holding this client's local settings. */
	private static final String DIRECTORY_NAME = ".open592";

	private static final String FILE_NAME = "cursor.properties";

	private static final String KEY_ENABLED = "customCursor";

	/** The game's own cursors are the better default: they point at what they hover. */
	public static final boolean DEFAULT_ENABLED = true;

	private static boolean enabled = DEFAULT_ENABLED;

	/** Test hook: redirects the properties file (null restores the default). */
	private static File directoryOverride;

	private CursorSettings() {
	}

	public static boolean isEnabled() {
		return enabled;
	}

	public static void setEnabled(boolean value) {
		enabled = value;
	}

	/**
	 * Flips the setting.
	 *
	 * @return the value now in effect
	 */
	public static boolean toggle() {
		enabled = !enabled;
		return enabled;
	}

	/**
	 * Loads the remembered setting. A missing file keeps the default, and so
	 * does an unrecognised value: {@code Boolean.parseBoolean} would quietly
	 * read garbage as {@code false} and turn the cursors off.
	 */
	public static void load() {
		Properties properties = readProperties();
		String value = properties.getProperty(KEY_ENABLED);
		if (value == null) {
			return;
		}
		String trimmed = value.trim();
		if (trimmed.equalsIgnoreCase("true")) {
			enabled = true;
		} else if (trimmed.equalsIgnoreCase("false")) {
			enabled = false;
		}
	}

	/** Persists the setting. Safe to call on every change. */
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
				properties.store(out, "open592 custom cursors");
			} finally {
				out.close();
			}
		} catch (IOException ignored) {
			// Persistence is best-effort; the session keeps running.
		} catch (SecurityException ignored) {
			// Sandboxed runtimes may forbid local file writes.
		}
	}

	public static void reset() {
		enabled = DEFAULT_ENABLED;
	}

	/** Test hook: redirects the properties file under the given directory. */
	static void setDirectoryForTest(File directory) {
		directoryOverride = directory;
	}

	/** Test hook: sets the flag without touching the file. */
	static void setForTest(boolean value) {
		enabled = value;
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
