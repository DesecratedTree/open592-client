package com.jagex.game.runetek5.client.hd;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.file.Path;

import com.jagex.game.runetek5.buffer.Packet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("592HD pane status")
class HdCapabilitiesTest {

	@TempDir
	Path home;

	private int rendererKind;

	@BeforeEach
	void resetState() {
		HdSettings.setDirectoryForTest(this.home.toFile());
		HdSettings.resetForTest();
		HdSettings.setDirectoryForTest(this.home.toFile());
		this.rendererKind = Packet.anInt2973;
	}

	@AfterEach
	void restore() {
		Packet.anInt2973 = this.rendererKind;
		HdSettings.resetForTest();
	}

	private static void withOpenGl() {
		Packet.anInt2973 = 1;
	}

	@Test
	@DisplayName("the status names the setting that is stopping lighting")
	void statusLine_shouldNameTheBlockingSetting() {
		HdSettings.setEnabled(false, null);
		String off = HdCapabilities.statusLine();

		HdSettings.setEnabled(true, null);
		Packet.anInt2973 = 0;
		String noOpenGl = HdCapabilities.statusLine();

		withOpenGl();
		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 0);
		String rowOff = HdCapabilities.statusLine();

		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 1);
		String pending = HdCapabilities.statusLine();

		assertAll(
			() -> assertEquals("592HD off - stock graphics", off),
			() -> assertEquals("592HD on - needs OpenGL", noOpenGl),
			() -> assertEquals("592HD on - per-pixel off", rowOff),
			() -> assertEquals("592HD on - lighting pending", pending)
		);
	}

	@Test
	@DisplayName("cycling the row changes the status immediately, with no compile involved")
	void statusLine_shouldTrackTheSettings() {
		withOpenGl();
		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 1);
		String lit = HdCapabilities.statusLine();

		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 0);

		assertAll(
			() -> assertEquals("592HD on - per-pixel off", HdCapabilities.statusLine()),
			() -> assertNotEquals(lit, HdCapabilities.statusLine())
		);
	}

	@Test
	@DisplayName("without a toolkit there is no program, so the status never claims one")
	void lightingAvailable_shouldNeedTheOwningToolkit() {
		assertFalse(HdCapabilities.lightingAvailable());
	}
}
