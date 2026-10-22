package com.jagex.game.runetek5.client.render;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.jagex.game.runetek5.config.material.texture.TextureType;

/**
 * Covers the two jobs {@link RenderCores} has: clamping a count the queue can
 * actually use, and remembering it without letting a stale file widen the
 * range.
 */
class RenderCoresTest {

	@TempDir
	File directory;

	private int originalTextureCores;

	@BeforeEach
	void redirectSettingsFile() {
		this.originalTextureCores = TextureType.anInt4043;
		RenderCores.resetForTest();
		RenderCores.setDirectoryForTest(this.directory);
	}

	@AfterEach
	void restoreGlobals() {
		RenderCores.resetForTest();
		TextureType.anInt4043 = this.originalTextureCores;
	}

	@Test
	void clampsToTheRangeTheQueueCanBind() {
		assertEquals(RenderCores.MIN, RenderCores.clamp(-5));
		assertEquals(RenderCores.MIN, RenderCores.clamp(0));
		assertEquals(2, RenderCores.clamp(2));
		assertEquals(RenderCores.MAX, RenderCores.clamp(4));
		assertEquals(RenderCores.MAX, RenderCores.clamp(99));
	}

	@Test
	void defaultLeavesACoreForTheClientThread() {
		assertEquals(1, RenderCores.defaultFor(1));
		assertEquals(1, RenderCores.defaultFor(2));
		assertEquals(2, RenderCores.defaultFor(3));
		assertEquals(3, RenderCores.defaultFor(4));
		assertEquals(RenderCores.MAX, RenderCores.defaultFor(8));
		assertEquals(RenderCores.MAX, RenderCores.defaultFor(64));
	}

	@Test
	void resolveUsesTheMachineWhenNothingUsableWasSaved() {
		assertEquals(RenderCores.defaultFor(8), RenderCores.resolve(0, 8));
		assertEquals(RenderCores.defaultFor(2), RenderCores.resolve(-3, 2));
	}

	@Test
	void resolveClampsASavedValueInsteadOfTrustingIt() {
		assertEquals(3, RenderCores.resolve(3, 8));
		assertEquals(RenderCores.MAX, RenderCores.resolve(12, 8));
	}

	@Test
	void firstUseResolvesTheMachineDefaultAndPushesIt() {
		int expected = RenderCores.defaultFor(Runtime.getRuntime().availableProcessors());
		assertEquals(expected, RenderCores.get());
		assertEquals(expected, TextureType.anInt4043);
	}

	@Test
	void setPushesTheCountToTheRendererAndSavesIt() throws Exception {
		assertEquals(2, RenderCores.set(2));
		assertEquals(2, TextureType.anInt4043);
		assertEquals(2, savedCores());
	}

	@Test
	void setClampsBeforeSaving() throws Exception {
		assertEquals(RenderCores.MAX, RenderCores.set(99));
		assertEquals(RenderCores.MAX, savedCores());
	}

	@Test
	void loadRestoresASavedChoice() throws Exception {
		writeSettings(3);
		RenderCores.load();
		assertEquals(3, RenderCores.get());
		assertEquals(3, TextureType.anInt4043);
	}

	@Test
	void loadWithoutAFileKeepsTheMachineDefault() {
		RenderCores.load();
		assertEquals(RenderCores.defaultFor(Runtime.getRuntime().availableProcessors()), RenderCores.get());
	}

	@Test
	void loadIgnoresAnUnreadableValue() throws Exception {
		writeSettings("two");
		RenderCores.load();
		assertEquals(RenderCores.defaultFor(Runtime.getRuntime().availableProcessors()), RenderCores.get());
	}

	@Test
	void loadClampsAStaleFileFromABiggerMachine() throws Exception {
		writeSettings(12);
		RenderCores.load();
		assertEquals(RenderCores.MAX, RenderCores.get());
	}

	private int savedCores() throws Exception {
		Properties properties = new Properties();
		File file = new File(this.directory, "render.properties");
		assertTrue(file.isFile(), "expected render.properties to be written");
		InputStream in = new FileInputStream(file);
		try {
			properties.load(in);
		} finally {
			in.close();
		}
		return Integer.parseInt(properties.getProperty("renderCores").trim());
	}

	private void writeSettings(int cores) throws Exception {
		writeSettings(Integer.toString(cores));
	}

	private void writeSettings(String value) throws Exception {
		Properties properties = new Properties();
		properties.setProperty("renderCores", value);
		OutputStream out = new FileOutputStream(new File(this.directory, "render.properties"));
		try {
			properties.store(out, "test");
		} finally {
			out.close();
		}
	}
}
