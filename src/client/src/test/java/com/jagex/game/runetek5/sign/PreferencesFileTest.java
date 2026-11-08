package com.jagex.game.runetek5.sign;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.nio.file.Path;

import com.jagex.game.runetek5.cache.JFS32;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("Client preferences file location and persistence")
class PreferencesFileTest {

	@TempDir
	Path tempDir;

	@BeforeEach
	void redirectRoot() {
		PreferencesFile.setRootForTest(tempDir.toFile());
	}

	@AfterEach
	void restoreRoot() {
		PreferencesFile.setRootForTest(null);
	}

	@Test
	@DisplayName("the file name follows the stock cache name and preference suffix")
	void fileName_should_matchStockNaming() {
		assertEquals("jagex_runescape_preferences.dat",
			PreferencesFile.fileName("", "runescape", 32));
		assertEquals("jagex_runescape_preferences2.dat",
			PreferencesFile.fileName("2", "runescape", 32));
		assertEquals("jagex_runescape_preferences_rc.dat",
			PreferencesFile.fileName("", "runescape", 33));
		assertEquals("jagex_runescape_preferences_wip.dat",
			PreferencesFile.fileName("", "runescape", 34));
	}

	@Test
	@DisplayName("open creates the preferences file in the first usable location")
	void open_should_createFile() {
		JFS32 file = PreferencesFile.open("", "runescape", 32);
		assertNotNull(file, "the preferences file should open in the temporary directory");
		assertEquals(new File(tempDir.toFile(), "jagex_runescape_preferences.dat"),
			file.method4716());
		close(file);
	}

	@Test
	@DisplayName("a second open reads back exactly what the first one saved")
	void savedPreferences_should_surviveReopen() throws Exception {
		byte[] saved = new byte[] { 17, 1, 1, 1, 2, 0, 127, 42 };
		JFS32 file = PreferencesFile.open("", "runescape", 32);
		assertNotNull(file);
		file.method4714(0, saved.length, saved);
		close(file);

		JFS32 reopened = PreferencesFile.open("", "runescape", 32);
		assertNotNull(reopened, "the file written by the first open should be found again");
		assertEquals(saved.length, reopened.method4717());
		byte[] loaded = new byte[saved.length];
		assertEquals(saved.length, reopened.method4715(0, loaded.length, loaded));
		assertArrayEquals(saved, loaded);
		close(reopened);
	}

	@Test
	@DisplayName("no usable location reports failure instead of a half-open file")
	void open_should_returnNull_whenNoRootExists() {
		PreferencesFile.setRootForTest(new File(tempDir.toFile(), "missing"));
		assertNull(PreferencesFile.open("", "runescape", 32));
	}

	@Test
	@DisplayName("SignLink operation 12 hands the game an open preferences file")
	void signLink_should_openPreferencesThroughItsWorker() throws Exception {
		SignLink link = new SignLink(null, 32, "runescape", 31);
		try {
			SignedResource resource = link.method1738("");
			long deadline = System.currentTimeMillis() + 5000L;
			while (resource.anInt5760 == 0 && System.currentTimeMillis() < deadline) {
				Thread.sleep(5L);
			}
			assertEquals(1, resource.anInt5760,
				"the worker should complete the preferences request, not fail it");
			assertNotNull(resource.anObject29, "the request should carry a JFS32");
			close((JFS32) resource.anObject29);
		} finally {
			link.method1732();
		}
	}

	private static void close(JFS32 file) {
		try {
			file.method4713();
		} catch (Exception exception) {
			throw new AssertionError("closing the preferences file should not fail", exception);
		}
	}
}
