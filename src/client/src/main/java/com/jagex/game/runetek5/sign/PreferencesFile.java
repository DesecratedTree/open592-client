package com.jagex.game.runetek5.sign;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jagex.game.runetek5.cache.JFS32;

/**
 * Locates and opens the client's own preferences file.
 *
 * <p>Graphics options ({@code Class50_Sub1}) and the sound settings keep their
 * state in a {@code jagex_<name>_preferences*.dat} file, read and written
 * through {@link SignLink}'s worker as operation 12. That operation had no
 * implementation — and {@link JFS32} no file constructor — so every load and
 * save failed and the client came back with stock settings on each launch.
 *
 * <p>The name and the search order are the stock client's: the release-
 * candidate and work-in-progress cache ids get their {@code _rc}/{@code _wip}
 * file names, and the first existing location from
 * {@link #DEFAULT_ROOT_NAMES} wins, with the user's home directory preferred
 * over the Windows and temporary fallbacks. A file that cannot be opened in
 * any of them yields {@code null}; {@link SignLink} reports that to its caller
 * as a failed request rather than handing back a half-open file.
 */
public final class PreferencesFile {

	/** Written into every save; generous because the payload grows. */
	private static final long MAX_LENGTH = 10000L;

	/**
	 * Where the stock client looks for its preferences file, in order. The
	 * home directory is filled in at call time; an empty entry means the
	 * current working directory and always exists.
	 */
	private static final String[] DEFAULT_ROOT_NAMES = {
		"c:/rscache/", "/rscache/", null, "c:/windows/", "c:/winnt/", "c:/", "/tmp/", ""
	};

	/** Test hook: searches only this directory (null restores the defaults). */
	private static File rootOverride;

	private PreferencesFile() {
	}

	/**
	 * Opens the preferences file for the given cache name and id.
	 *
	 * @param name      the preference set's suffix, e.g. {@code ""} for
	 *                  graphics options or {@code "2"} for the sound settings
	 * @param cacheName the client's cache/name field, {@code "runescape"} for
	 *                  a stock launch
	 * @param cacheId   the client's cache version; 33 and 34 select the
	 *                  release-candidate and work-in-progress file names
	 * @return the opened file, or null when no location could provide it
	 */
	public static JFS32 open(String name, String cacheName, int cacheId) {
		String fileName = fileName(name, cacheName, cacheId);
		for (String root : roots()) {
			if (root.length() > 0 && !new File(root).exists()) {
				continue;
			}
			try {
				return new JFS32(new File(root, fileName), "rw", MAX_LENGTH);
			} catch (IOException exception) {
				// Try the next location, exactly as the stock client does.
			} catch (SecurityException exception) {
				// A sandboxed runtime may forbid this location entirely.
			}
		}
		return null;
	}

	/** The stock file name for one preference set. */
	static String fileName(String name, String cacheName, int cacheId) {
		String suffix;
		if (cacheId == 33) {
			suffix = "_rc.dat";
		} else if (cacheId == 34) {
			suffix = "_wip.dat";
		} else {
			suffix = ".dat";
		}
		return "jagex_" + cacheName + "_preferences" + (name == null ? "" : name) + suffix;
	}

	/** The directories to try, in order. */
	private static String[] roots() {
		File override = rootOverride;
		if (override != null) {
			return override.isDirectory() ? new String[] { override.getPath() + File.separator } : new String[0];
		}
		List<String> roots = new ArrayList<String>(DEFAULT_ROOT_NAMES.length);
		for (String root : DEFAULT_ROOT_NAMES) {
			if (root == null) {
				root = SignLink.preferencesParentFile;
			}
			roots.add(root == null ? "" : root);
		}
		return roots.toArray(new String[roots.size()]);
	}

	/** Test hook: searches only the given directory (null restores stock). */
	static void setRootForTest(File directory) {
		rootOverride = directory;
	}
}
