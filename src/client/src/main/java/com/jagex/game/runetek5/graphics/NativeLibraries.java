package com.jagex.game.runetek5.graphics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.GZIPInputStream;

import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.deob.Static366;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

/**
 * Loads the Jagex natives (jaclib and jaggl) from JS5 index 30.
 *
 * <p>The original client shipped one set of natives per platform, but the
 * 592 update server publishes every set in index 30: Windows x86/x86-64,
 * Linux x86/x86-64 and macOS universal/i386/x86-64/ppc. Extracting the set
 * that matches the running JVM means the same client build works everywhere
 * instead of shipping platform-specific DLLs/dylibs with it.</p>
 */
public final class NativeLibraries {

	private static final int CACHE_ID = 32;
	private static final String CACHE_SUBDIRECTORY = "runescape";
	private static final String VERSION_FILE = "natives.version";

	private static final int PLATFORM_WINDOWS = 0;
	private static final int PLATFORM_MAC = 1;
	private static final int PLATFORM_LINUX = 2;

	private static final String[] LIBRARIES = { "jaclib", "jaggl" };
	private static final byte[][] LIBRARY_MARKERS = {
		"Java_jaclib_".getBytes(),
		"Java_jaggl_".getBytes()
	};

	private static File cacheDirectory;
	private static boolean extracted;
	private static boolean extractAttempted;

	private NativeLibraries() {
	}

	/**
	 * @return whether jaclib and jaggl for this platform are available either
	 * from index 30 or from an already installed copy.
	 */
	public static synchronized boolean isAvailable() {
		if (ensureExtracted()) {
			return true;
		}
		int platform = platform();
		boolean is64Bit = is64Bit();
		if (platform < 0) {
			return false;
		}
		File directory = cacheDirectory();
		if (directory != null && validCopy(directory, platform, is64Bit)) {
			return true;
		}
		String[] libraryPath = System.getProperty("java.library.path", "").split(File.pathSeparator);
		for (int i = 0; i < libraryPath.length; i++) {
			if (libraryPath[i].length() == 0) {
				continue;
			}
			if (validCopy(new File(libraryPath[i]), platform, is64Bit)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Loads a Jagex native, preferring the copy extracted from index 30 and
	 * falling back to {@link System#loadLibrary(String)}.
	 */
	public static synchronized void load(String name) {
		// An earlier run may already have extracted this library; load() is
		// also called before the JS5 provider has its master index ready, so
		// the copy on disk must be tried regardless of ensureExtracted().
		if (loadCached(name)) {
			return;
		}
		if (ensureExtracted() && loadCached(name)) {
			return;
		}
		System.loadLibrary(name);
	}

	private static boolean loadCached(String name) {
		File directory = cacheDirectory();
		if (directory == null) {
			return false;
		}
		File file = new File(directory, fileName(name));
		if (!file.isFile()) {
			return false;
		}
		try {
			System.load(file.getAbsolutePath());
			return true;
		} catch (Throwable ignored) {
			// The library on java.library.path may still work.
			return false;
		}
	}

	/**
	 * Extracts jaclib and jaggl for the running platform from index 30.
	 *
	 * <p>This client does not keep a JS5 disk cache, so index 30's groups are
	 * normally only reachable over the network. When the Jagex cache has
	 * already been populated (by the loader or an earlier install) the index
	 * can be read straight from main_file_cache.idx30/dat2 instead, which is
	 * synchronous, cheap and works offline. Both paths use the same container
	 * format, so they extract byte-identical libraries.</p>
	 *
	 * @return true when both libraries are present in the cache.
	 */
	private static boolean ensureExtracted() {
		if (extracted) {
			return true;
		}
		if (extractAttempted) {
			return false;
		}
		int platform = platform();
		if (platform < 0) {
			return false;
		}
		File directory = cacheDirectory();
		if (directory == null) {
			return false;
		}
		boolean is64Bit = is64Bit();
		String cacheVersion = cacheVersion(directory);
		if (cacheVersion != null && cacheVersion.equals(readVersion(directory))
				&& validCopy(directory, platform, is64Bit)) {
			extracted = true;
			return true;
		}
		extractAttempted = true;
		if (extractFromCache(directory, platform, is64Bit)) {
			if (cacheVersion != null) {
				writeVersion(directory, cacheVersion);
			}
			extracted = true;
			return true;
		}
		Js5ResourceProvider provider = Static366.aClass76_100;
		int groupCount = waitForIndex();
		if (groupCount <= 0) {
			return false;
		}
		String version = versionKey(platform, is64Bit, provider);
		boolean[] found = new boolean[LIBRARIES.length];
		boolean[] scanned = new boolean[groupCount];
		long deadline = System.currentTimeMillis() + 5000L;
		while (System.currentTimeMillis() < deadline) {
			boolean progressed = false;
			for (int group = 0; group < groupCount; group++) {
				if (scanned[group] || allFound(found)) {
					continue;
				}
				byte[] data;
				try {
					data = provider.method2122(group);
				} catch (Throwable ignored) {
					data = null;
				}
				if (data == null) {
					continue;
				}
				scanned[group] = true;
				progressed = true;
				int library = libraryFor(data);
				if (library < 0 || found[library] || !matchesPlatform(data, platform, is64Bit)) {
					continue;
				}
				File file = new File(directory, fileName(LIBRARIES[library]));
				if (write(file, data) || validFile(file, library, platform, is64Bit)) {
					found[library] = true;
				}
			}
			if (allFound(found)) {
				if (version != null) {
					writeVersion(directory, version);
				}
				extracted = true;
				return true;
			}
			if (!progressed) {
				// Index 30 is prefetched through the request manager, which
				// is normally pumped once per game-loop frame. This check runs
				// on that same thread while the loop is waiting, so drive it
				// here or the prefetch never completes and the scan sees no
				// data at all.
				pumpRequests();
				try {
					Thread.sleep(10L);
				} catch (InterruptedException ignored) {
				}
			}
		}
		return false;
	}

	/**
	 * @return a version key derived from the on-disk index 30, or null when
	 * the cache has not been populated.
	 */
	private static String cacheVersion(File directory) {
		File index = new File(directory, "main_file_cache.idx30");
		if (!index.isFile()) {
			return null;
		}
		return "disk-" + index.length() + "-" + index.lastModified();
	}

	/**
	 * Reads jaclib and jaggl out of main_file_cache.idx30/dat2.
	 */
	private static boolean extractFromCache(File directory, int platform, boolean is64Bit) {
		File indexFile = new File(directory, "main_file_cache.idx30");
		File dataFile = new File(directory, "main_file_cache.dat2");
		if (!indexFile.isFile() || !dataFile.isFile()) {
			return false;
		}
		RandomAccessFile index = null;
		RandomAccessFile data = null;
		try {
			index = new RandomAccessFile(indexFile, "r");
			data = new RandomAccessFile(dataFile, "r");
			int groupCount = (int) (index.length() / 6L);
			boolean[] found = new boolean[LIBRARIES.length];
			byte[] header = new byte[6];
			for (int group = 0; group < groupCount && !allFound(found); group++) {
				index.seek((long) group * 6L);
				if (index.read(header) != header.length) {
					break;
				}
				int size = (header[0] & 0xff) << 16 | (header[1] & 0xff) << 8 | (header[2] & 0xff);
				int sector = (header[3] & 0xff) << 16 | (header[4] & 0xff) << 8 | (header[5] & 0xff);
				if (size <= 0) {
					continue;
				}
				byte[] container = readGroup(data, group, size, sector);
				if (container == null) {
					continue;
				}
				byte[] payload = decompress(container);
				if (payload == null) {
					continue;
				}
				int library = libraryFor(payload);
				if (library < 0 || found[library] || !matchesPlatform(payload, platform, is64Bit)) {
					continue;
				}
				File file = new File(directory, fileName(LIBRARIES[library]));
				if (write(file, payload) || validFile(file, library, platform, is64Bit)) {
					found[library] = true;
				}
			}
			return allFound(found);
		} catch (Throwable ignored) {
			return false;
		} finally {
			close(index);
			close(data);
		}
	}

	/**
	 * Reads one group from a cache data file. Blocks are 520 bytes: an
	 * 8-byte header (group, part, next sector, cache id) followed by up to
	 * 512 bytes of container data.
	 */
	private static byte[] readGroup(RandomAccessFile data, int group, int size, int sector) throws IOException {
		byte[] result = new byte[size];
		byte[] block = new byte[520];
		int position = 0;
		int current = sector;
		int part = 0;
		while (position < size) {
			if (current <= 0) {
				return null;
			}
			data.seek((long) current * 520L);
			int read = data.read(block);
			if (read < 8) {
				return null;
			}
			int blockGroup = (block[0] & 0xff) << 8 | (block[1] & 0xff);
			int blockPart = (block[2] & 0xff) << 8 | (block[3] & 0xff);
			int next = (block[4] & 0xff) << 16 | (block[5] & 0xff) << 8 | (block[6] & 0xff);
			if (blockGroup != group || blockPart != part) {
				return null;
			}
			int count = size - position;
			if (count > 512) {
				count = 512;
			}
			if (count > read - 8) {
				count = read - 8;
			}
			System.arraycopy(block, 8, result, position, count);
			position += count;
			current = next;
			part++;
		}
		return result;
	}

	/**
	 * Unpacks a cache group container: compression type, compressed length,
	 * the original length when compressed, then the payload.
	 */
	private static byte[] decompress(byte[] container) {
		if (container.length < 5) {
			return null;
		}
		int compression = container[0] & 0xff;
		int length = intBE(container, 1);
		if (length < 0) {
			return null;
		}
		if (compression == 0) {
			if (length > container.length - 5) {
				return null;
			}
			byte[] result = new byte[length];
			System.arraycopy(container, 5, result, 0, length);
			return result;
		}
		if (compression != 2 || container.length < 9 || length > container.length - 9) {
			// Only gzip appears in index 30; bzip2 would need a third-party
			// stream, and the JS5 path handles those containers anyway.
			return null;
		}
		GZIPInputStream in = null;
		try {
			in = new GZIPInputStream(new ByteArrayInputStream(container, 9, length));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[8192];
			int count;
			while ((count = in.read(buffer)) != -1) {
				out.write(buffer, 0, count);
			}
			return out.toByteArray();
		} catch (Throwable ignored) {
			return null;
		} finally {
			close(in);
		}
	}

	private static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException ignored) {
			}
		}
	}

	private static void pumpRequests() {
		if (Class64_Sub1.aClass159_1 != null) {
			try {
				Class64_Sub1.aClass159_1.method3819();
			} catch (Throwable ignored) {
			}
		}
	}

	/**
	 * Waits for index 30's master index to arrive. The client requests the
	 * natives index while it is still loading, and the provider returns -1
	 * from {@link Js5ResourceProvider#method2100()} until the master index
	 * has been decoded; checking once at that point made every platform look
	 * like it had no natives at all.
	 *
	 * @return the index's group count, or -1 on timeout.
	 */
	private static int waitForIndex() {
		long deadline = System.currentTimeMillis() + 5000L;
		while (true) {
			Js5ResourceProvider provider = Static366.aClass76_100;
			if (provider == null) {
				// Nothing has requested the natives index yet; waiting here
				// would not make the main thread create the provider.
				return -1;
			}
			int groupCount;
			try {
				groupCount = provider.method2100();
			} catch (Throwable ignored) {
				groupCount = -1;
			}
			if (groupCount > 0) {
				return groupCount;
			}
			if (System.currentTimeMillis() >= deadline) {
				return -1;
			}
			try {
				Thread.sleep(25L);
			} catch (InterruptedException ignored) {
				return -1;
			}
		}
	}

	private static boolean allFound(boolean[] found) {
		for (int i = 0; i < found.length; i++) {
			if (!found[i]) {
				return false;
			}
		}
		return true;
	}

	private static int libraryFor(byte[] data) {
		for (int i = 0; i < LIBRARY_MARKERS.length; i++) {
			if (indexOf(data, LIBRARY_MARKERS[i]) != -1) {
				return i;
			}
		}
		return -1;
	}

	private static int platform() {
		String os = System.getProperty("os.name", "").toLowerCase();
		if (os.startsWith("win")) {
			return PLATFORM_WINDOWS;
		}
		if (os.startsWith("mac") || os.startsWith("darwin")) {
			return PLATFORM_MAC;
		}
		if (os.startsWith("linux") || os.startsWith("sunos")) {
			return PLATFORM_LINUX;
		}
		return -1;
	}

	private static String architecture() {
		return System.getProperty("os.arch", "").toLowerCase();
	}

	private static boolean is64Bit() {
		String arch = architecture();
		return arch.contains("64") || arch.equals("amd64");
	}

	private static boolean architectureSupported() {
		String arch = architecture();
		if (arch.contains("x86") || arch.contains("i386") || arch.contains("i486")
				|| arch.contains("i586") || arch.contains("i686") || arch.equals("x64")
				|| arch.equals("amd64") || arch.startsWith("ppc") || arch.startsWith("powerpc")) {
			return true;
		}
		return false;
	}

	private static String fileName(String name) {
		int platform = platform();
		if (platform == PLATFORM_WINDOWS) {
			return name + ".dll";
		}
		if (platform == PLATFORM_MAC) {
			return "lib" + name + ".jnilib";
		}
		return "lib" + name + ".so";
	}

	private static File cacheDirectory() {
		if (cacheDirectory != null) {
			return cacheDirectory;
		}
		String home = System.getProperty("user.home");
		File base = home == null || home.length() == 0
				? new File(System.getProperty("java.io.tmpdir", "."))
				: new File(home);
		File directory = new File(base, ".jagex_cache_" + CACHE_ID + File.separator + CACHE_SUBDIRECTORY);
		if (directory.isDirectory() || directory.mkdirs()) {
			cacheDirectory = directory;
			return directory;
		}
		File fallback = new File(new File(System.getProperty("java.io.tmpdir", ".")),
				".jagex_cache_" + CACHE_ID + File.separator + CACHE_SUBDIRECTORY);
		if (fallback.isDirectory() || fallback.mkdirs()) {
			cacheDirectory = fallback;
			return fallback;
		}
		return null;
	}

	private static boolean write(File file, byte[] data) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(data);
			return true;
		} catch (Throwable ignored) {
			return false;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	private static String versionKey(int platform, boolean is64Bit, Js5ResourceProvider provider) {
		try {
			return platform + "-" + (is64Bit ? "64" : "32") + "-" + Integer.toHexString(provider.method2111());
		} catch (Throwable ignored) {
			return null;
		}
	}

	private static String readVersion(File directory) {
		File file = new File(directory, VERSION_FILE);
		if (!file.isFile() || file.length() > 128L) {
			return null;
		}
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			int read = 0;
			while (read < buffer.length) {
				int count = in.read(buffer, read, buffer.length - read);
				if (count < 0) {
					break;
				}
				read += count;
			}
			return new String(buffer, 0, read, "UTF-8").trim();
		} catch (Throwable ignored) {
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	private static void writeVersion(File directory, String version) {
		try {
			write(new File(directory, VERSION_FILE), version.getBytes("UTF-8"));
		} catch (Throwable ignored) {
		}
	}

	private static boolean validCopy(File directory, int platform, boolean is64Bit) {
		for (int i = 0; i < LIBRARIES.length; i++) {
			if (!validFile(new File(directory, fileName(LIBRARIES[i])), i, platform, is64Bit)) {
				return false;
			}
		}
		return true;
	}

	private static boolean validFile(File file, int library, int platform, boolean is64Bit) {
		if (!file.isFile() || file.length() == 0L) {
			return false;
		}
		byte[] data = new byte[(int) file.length()];
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			int read = 0;
			while (read < data.length) {
				int count = in.read(data, read, data.length - read);
				if (count < 0) {
					break;
				}
				read += count;
			}
			if (read != data.length) {
				return false;
			}
		} catch (Throwable ignored) {
			return false;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ignored) {
				}
			}
		}
		return libraryFor(data) == library && matchesPlatform(data, platform, is64Bit);
	}

	static boolean matchesPlatform(byte[] data, int platform, boolean is64Bit) {
		if (!architectureSupported()) {
			return false;
		}
		if (platform == PLATFORM_WINDOWS) {
			return isWindowsBinary(data, is64Bit);
		}
		if (platform == PLATFORM_LINUX) {
			return isElfBinary(data, is64Bit);
		}
		return isMacBinary(data, is64Bit);
	}

	private static boolean isWindowsBinary(byte[] data, boolean is64Bit) {
		if (data.length < 64 || data[0] != 'M' || data[1] != 'Z') {
			return false;
		}
		int pe = intLE(data, 0x3c);
		if (pe < 0 || pe + 24 > data.length || data[pe] != 'P' || data[pe + 1] != 'E'
				|| data[pe + 2] != 0 || data[pe + 3] != 0) {
			return false;
		}
		int machine = shortLE(data, pe + 4);
		return is64Bit ? machine == 0x8664 : machine == 0x14c;
	}

	private static boolean isElfBinary(byte[] data, boolean is64Bit) {
		if (data.length < 20 || data[0] != 0x7f || data[1] != 'E' || data[2] != 'L' || data[3] != 'F') {
			return false;
		}
		int machine = shortLE(data, 18);
		return is64Bit ? data[4] == 2 && machine == 62 : data[4] == 1 && machine == 3;
	}

	private static boolean isMacBinary(byte[] data, boolean is64Bit) {
		if (data.length < 8) {
			return false;
		}
		int magic = intBE(data, 0);
		if (magic == 0xcafebabe || magic == 0xbebafeca) {
			// A universal binary carries an i386, x86-64 and (in the 2010
			// build) a ppc slice, so it serves every supported Mac JVM.
			return true;
		}
		if (magic == 0xfeedface) {
			return !is64Bit && intBE(data, 4) == 7;
		}
		if (magic == 0xfeedfacf) {
			return is64Bit && intBE(data, 4) == 0x01000007;
		}
		if (magic == 0xcefaedfe) {
			return !is64Bit && intLE(data, 4) == 7;
		}
		if (magic == 0xcffaedfe) {
			return is64Bit && intLE(data, 4) == 0x01000007;
		}
		return false;
	}

	private static int shortLE(byte[] data, int offset) {
		if (offset < 0 || offset + 2 > data.length) {
			return -1;
		}
		return (data[offset] & 0xff) | (data[offset + 1] & 0xff) << 8;
	}

	private static int intLE(byte[] data, int offset) {
		if (offset < 0 || offset + 4 > data.length) {
			return -1;
		}
		return (data[offset] & 0xff) | (data[offset + 1] & 0xff) << 8
				| (data[offset + 2] & 0xff) << 16 | (data[offset + 3] & 0xff) << 24;
	}

	private static int intBE(byte[] data, int offset) {
		if (offset < 0 || offset + 4 > data.length) {
			return -1;
		}
		return (data[offset] & 0xff) << 24 | (data[offset + 1] & 0xff) << 16
				| (data[offset + 2] & 0xff) << 8 | (data[offset + 3] & 0xff);
	}

	private static int indexOf(byte[] haystack, byte[] needle) {
		if (needle.length == 0) {
			return 0;
		}
		outer: for (int i = 0; i <= haystack.length - needle.length; i++) {
			for (int j = 0; j < needle.length; j++) {
				if (haystack[i + j] != needle[j]) {
					continue outer;
				}
			}
			return i;
		}
		return -1;
	}
}
