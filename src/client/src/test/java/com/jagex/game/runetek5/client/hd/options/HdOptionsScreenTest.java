package com.jagex.game.runetek5.client.hd.options;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdPreset;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.client.hd.HdTab;
import com.jagex.game.runetek5.client.hd.RecordingHdGraphicsBackend;

@DisplayName("592HD pane input")
class HdOptionsScreenTest {

	private static final int CANVAS_WIDTH = 765;

	private static final int CANVAS_HEIGHT = 503;

	private static final HdInterfaceTarget TARGET = new HdInterfaceTarget(742, (742 << 16) | 2, 100, 50, 512, 338);

	@TempDir
	Path home;

	private RecordingHdGraphicsBackend backend;

	@BeforeEach
	void resetState() {
		HdSettings.setDirectoryForTest(this.home.toFile());
		HdSettings.resetForTest();
		HdSettings.setDirectoryForTest(this.home.toFile());
		HdOptionsScreen.resetForTest();
		this.backend = new RecordingHdGraphicsBackend();
		HdOptionsScreen.setBackendForTest(this.backend);
		HdOptionsScreen.setTargetForTest(TARGET);
		HdOptionsScreen.setCanvasForTest(CANVAS_WIDTH, CANVAS_HEIGHT);
	}

	@AfterEach
	void clearStorageHook() {
		HdOptionsScreen.resetForTest();
		HdSettings.resetForTest();
	}

	@Test
	@DisplayName("clicking the stock-page button opens the pane")
	void clickOnButton_shouldOpen() {
		HdRect button = layout().openButton();

		boolean consumed = HdOptionsScreen.consumeClick(centreX(button), centreY(button), 0);

		assertAll(
			() -> assertTrue(consumed),
			() -> assertTrue(HdOptionsScreen.isOpen())
		);
	}

	@Test
	@DisplayName("clicks away from the button pass through to the stock interface")
	void clickOffButton_shouldPassThrough() {
		assertAll(
			() -> assertFalse(HdOptionsScreen.consumeClick(5, 5, 0)),
			() -> assertFalse(HdOptionsScreen.isOpen())
		);
	}

	@Test
	@DisplayName("right and middle presses never reach the pane")
	void nonLeftPress_shouldPassThrough() {
		HdRect button = layout().openButton();

		assertFalse(HdOptionsScreen.consumeClick(centreX(button), centreY(button), 1));
		assertFalse(HdOptionsScreen.isOpen());
	}

	@Test
	@DisplayName("clicking a row changes the value and writes it to disk")
	void rowClick_shouldPersist() throws Exception {
		openPane();
		HdRect row = layout().row(indexOf(HdTab.LIGHTING, HdFeature.SHADOWS));
		int before = HdSettings.value(HdFeature.SHADOWS);

		boolean consumed = HdOptionsScreen.consumeClick(centreX(row), centreY(row), 0);

		assertAll(
			() -> assertTrue(consumed),
			() -> assertEquals((before + 1) % HdFeature.SHADOWS.valueCount(),
				HdSettings.value(HdFeature.SHADOWS)),
			() -> assertEquals(HdSettings.value(HdFeature.SHADOWS), this.backend.valueOf(HdFeature.SHADOWS)),
			() -> assertTrue(HdOptionsScreen.isOpen())
		);
		File file = new File(this.home.toFile(), "hd.properties");
		String saved = new String(Files.readAllBytes(file.toPath()), StandardCharsets.ISO_8859_1);
		assertTrue(saved.contains(HdFeature.SHADOWS.key() + "=" + HdSettings.value(HdFeature.SHADOWS)));
	}

	@Test
	@DisplayName("a tab click swaps the visible rows")
	void tabClick_shouldSwapRows() {
		openPane();
		HdRect tab = layout().tabButton(HdTab.WORLD.ordinal());

		HdOptionsScreen.consumeClick(centreX(tab), centreY(tab), 0);

		assertAll(
			() -> assertEquals(HdTab.WORLD, HdOptionsScreen.model().tab()),
			() -> assertEquals(HdFeature.TEXTURES, HdOptionsScreen.model().rows()[0])
		);
	}

	@Test
	@DisplayName("a preset click switches the whole table")
	void presetClick_shouldApplyPreset() {
		openPane();
		HdRect preset = layout().presetButton(4);

		HdOptionsScreen.consumeClick(centreX(preset), centreY(preset), 0);

		assertAll(
			() -> assertEquals(HdPreset.ULTRA, HdSettings.preset()),
			() -> assertEquals(HdPreset.ULTRA.value(HdFeature.SHADOW_MAP),
				HdSettings.value(HdFeature.SHADOW_MAP))
		);
	}

	@Test
	@DisplayName("Back closes the pane")
	void backClick_shouldClose() {
		openPane();

		HdOptionsScreen.consumeClick(centreX(layout().backButton()), centreY(layout().backButton()), 0);

		assertFalse(HdOptionsScreen.isOpen());
	}

	@Test
	@DisplayName("a click outside the page closes it and is consumed")
	void outsideClick_shouldCloseAndConsume() {
		openPane();

		boolean consumed = HdOptionsScreen.consumeClick(3, 3, 0);

		assertAll(
			() -> assertTrue(consumed),
			() -> assertFalse(HdOptionsScreen.isOpen())
		);
	}

	@Test
	@DisplayName("a click on the page background is swallowed without changing state")
	void pageBackgroundClick_shouldNotReachTheStockInterface() {
		openPane();
		HdRect page = layout().page();
		int x = page.x() + page.width() - 4;
		int y = page.y() + page.height() / 2;
		int before = HdSettings.value(HdFeature.SHADOWS);

		boolean consumed = HdOptionsScreen.consumeClick(x, y, 0);

		assertAll(
			() -> assertTrue(consumed),
			() -> assertEquals(before, HdSettings.value(HdFeature.SHADOWS)),
			() -> assertTrue(HdOptionsScreen.isOpen())
		);
	}

	@Test
	@DisplayName("without an open window the pane still opens and is clickable")
	void standalonePane_shouldWork() {
		HdOptionsScreen.setTargetForTest(null);
		HdOptionsScreen.open();
		HdOptionsLayout standalone = new HdOptionsLayout(CANVAS_WIDTH, CANVAS_HEIGHT, null,
			HdOptionsScreen.model().tab(), HdOptionsScreen.model().rowCount());
		HdRect row = standalone.row(indexOf(HdTab.LIGHTING, HdFeature.SHADOWS));

		assertAll(
			() -> assertTrue(HdOptionsScreen.isOpen()),
			() -> assertTrue(HdOptionsScreen.consumeClick(centreX(row), centreY(row), 0)),
			() -> assertEquals(HdSettings.value(HdFeature.SHADOWS), this.backend.valueOf(HdFeature.SHADOWS))
		);
	}

	private void openPane() {
		HdOptionsScreen.open();
	}

	private HdOptionsLayout layout() {
		return new HdOptionsLayout(CANVAS_WIDTH, CANVAS_HEIGHT, TARGET,
			HdOptionsScreen.model().tab(), HdOptionsScreen.model().rowCount());
	}

	private static int indexOf(HdTab tab, HdFeature feature) {
		HdFeature[] rows = HdFeature.forTab(tab);
		for (int i = 0; i < rows.length; i++) {
			if (rows[i] == feature) {
				return i;
			}
		}
		return -1;
	}

	private static int centreX(HdRect rect) {
		return rect.x() + rect.width() / 2;
	}

	private static int centreY(HdRect rect) {
		return rect.y() + rect.height() / 2;
	}
}
