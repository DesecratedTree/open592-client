package com.jagex.game.runetek5.client.render;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.jagex.game.runetek5.config.material.texture.TextureType;

/**
 * How many worker threads the software rasteriser may use.
 *
 * <p>{@code TextureType.anInt4043} is both the number of workers the threaded
 * command queue creates ({@code Class70_Sub1.method5075}) and the flag that
 * makes {@code Class62} choose that queue over the single-threaded one. Stock
 * leaves it at 1, so the CPU renderer paints every tile and every plane on one
 * thread even though it is built for more; only the {@code mc2}..{@code mc4}
 * console commands ever raised it, and they forget it on exit.
 *
 * <p>This class owns the value: it derives a default from the machine, keeps
 * the console command and the queue's thread count in step, and remembers the
 * choice across restarts. The rendered image is identical at any setting —
 * workers only decide who paints a given tile — so the only cost of the
 * default is memory: each worker holds its own {@code JavaThreadResource}
 * scratch buffers.
 */
public final class RenderCores {

	/** Subdirectory of the user home holding this client's local settings. */
	private static final String DIRECTORY_NAME = ".open592";

	private static final String FILE_NAME = "render.properties";

	private static final String KEY_CORES = "renderCores";

	/**
	 * One worker is the floor: the toolkit needs at least the main thread's
	 * resource, and 0 would divide the scene across nothing.
	 */
	public static final int MIN = 1;

	/**
	 * Four is the ceiling the queue can express: {@code Class70_Sub1.method5075}
	 * enqueues one bind byte per worker ({@code 0..n-1}) and the worker loop only
	 * recognises bytes {@code 0..3}, so a fifth worker would never be bound to a
	 * resource. It also matches the four scene planes the queue splits work by.
	 */
	public static final int MAX = 4;

	/** Cores held back from the rasteriser for the client thread's own work. */
	private static final int RESERVED_FOR_CLIENT = 1;

	/** Test hook: redirects the properties file (null restores the default). */
	private static File directoryOverride;

	/** -1 until the saved choice or the machine default has been resolved. */
	private static int cores = -1;

	private RenderCores() {
	}

	/** The count now in effect, resolving the machine default on first use. */
	public static int get() {
		if (cores < 0) {
			cores = defaultFor(Runtime.getRuntime().availableProcessors());
			TextureType.anInt4043 = cores;
		}
		return cores;
	}

	/**
	 * Sets, clamps and persists the count, and pushes it to the field the
	 * scene renderer reads.
	 *
	 * @return the count now in effect
	 */
	public static int set(int value) {
		cores = clamp(value);
		TextureType.anInt4043 = cores;
		save();
		return cores;
	}

	public static int clamp(int value) {
		if (value < MIN) {
			return MIN;
		}
		if (value > MAX) {
			return MAX;
		}
		return value;
	}

	/**
	 * Default for a machine with the given processor count: everything but the
	 * core the client thread runs on, capped at what the queue can use. A
	 * single-core machine still gets one worker rather than none.
	 */
	public static int defaultFor(int processors) {
		return clamp(processors - RESERVED_FOR_CLIENT);
	}

	/**
	 * Resolves a remembered value against the machine. {@code saved <= 0}
	 * means nothing usable was recorded, so the machine default applies; any
	 * other value is clamped, which is what keeps a hand-edited file or a save
	 * from a bigger machine inside the queue's range.
	 */
	public static int resolve(int saved, int processors) {
		if (saved <= 0) {
			return defaultFor(processors);
		}
		return clamp(saved);
	}

	/**
	 * Loads the remembered count and pushes it to the renderer. A missing file
	 * keeps the machine default; an unparsable or out-of-range value is
	 * resolved through {@link #resolve}, so it can never widen the range.
	 */
	public static void load() {
		Properties properties = readProperties();
		int saved = parseCores(properties);
		cores = resolve(saved, Runtime.getRuntime().availableProcessors());
		TextureType.anInt4043 = cores;
	}

	/** Persists the count. Safe to call on every change. */
	public static void save() {
		Properties properties = new Properties();
		properties.setProperty(KEY_CORES, Integer.toString(get()));
		File file = propertiesFile();
		try {
			File parent = file.getParentFile();
			if (parent != null) {
				parent.mkdirs();
			}
			OutputStream out = new FileOutputStream(file);
			try {
				properties.store(out, "open592 software renderer cores");
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

	/** Test hook: forgets the resolved count, as a fresh process would. */
	static void resetForTest() {
		cores = -1;
		directoryOverride = null;
	}

	/** Saved value, or 0 when the file is absent or unreadable as a number. */
	private static int parseCores(Properties properties) {
		String value = properties.getProperty(KEY_CORES);
		if (value == null) {
			return 0;
		}
		try {
			return Integer.parseInt(value.trim());
		} catch (NumberFormatException ex) {
			return 0;
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
			// Corrupt or unreadable file means the machine default.
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
