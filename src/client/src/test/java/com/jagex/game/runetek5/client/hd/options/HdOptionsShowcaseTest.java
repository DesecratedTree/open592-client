package com.jagex.game.runetek5.client.hd.options;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.client.hd.HdTab;

/**
 * Renders the pane at the stock canvas size and writes the PNGs to
 * {@code build/hd-showcase}, so the layout can be reviewed as an image as well
 * as asserted. It is also a smoke test for the draw path: every screenshot is a
 * real pass through {@link HdOptionsOverlay}.
 */
@DisplayName("592HD pane showcase")
class HdOptionsShowcaseTest {

	private static final int CANVAS_WIDTH = 765;

	private static final int CANVAS_HEIGHT = 503;

	private static final HdInterfaceTarget STOCK_WINDOW =
		new HdInterfaceTarget(742, (742 << 16) | 2, 100, 50, 512, 338);

	private static final Path OUTPUT = java.nio.file.Paths.get("build", "hd-showcase");

	private Path home;

	@BeforeEach
	void resetState() throws IOException {
		this.home = Files.createTempDirectory("hd-showcase");
		HdSettings.setDirectoryForTest(this.home.toFile());
		HdSettings.resetForTest();
		HdSettings.setDirectoryForTest(this.home.toFile());
		HdOptionsScreen.resetForTest();
		Files.createDirectories(OUTPUT);
	}

	@AfterEach
	void clearStorageHook() {
		HdOptionsScreen.resetForTest();
		HdSettings.resetForTest();
	}

	@Test
	@DisplayName("stock page, page, World tab and a row toggle render to PNG")
	void showcase_shouldRenderEveryState() throws Exception {
		ImageHdCanvas canvas = new ImageHdCanvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		HdOptionsScreen.setTargetForTest(STOCK_WINDOW);
		HdOptionsScreen.setCanvasForTest(CANVAS_WIDTH, CANVAS_HEIGHT);

		Path button = frame(canvas, "1-stock-button.png");

		HdOptionsScreen.open();
		Path lighting = frame(canvas, "2-pane-lighting.png");

		HdOptionsModel model = HdOptionsScreen.model();
		HdRect worldTab = currentLayout().tabButton(HdTab.WORLD.ordinal());
		assertTrue(HdOptionsScreen.consumeClick(
			worldTab.x() + worldTab.width() / 2, worldTab.y() + worldTab.height() / 2, 0));
		Path world = frame(canvas, "3-pane-world.png");

		HdRect textures = currentLayout().row(0);
		assertTrue(HdOptionsScreen.consumeClick(
			textures.x() + 40, textures.y() + textures.height() / 2, 0));
		Path toggled = frame(canvas, "4-pane-world-textures-off.png");

		File preferences = new File(this.home.toFile(), "hd.properties");
		String saved = new String(Files.readAllBytes(preferences.toPath()), StandardCharsets.ISO_8859_1);
		Path copy = OUTPUT.resolve("5-saved-preferences.txt");
		Files.copy(preferences.toPath(), copy, StandardCopyOption.REPLACE_EXISTING);

		assertAll(
			() -> assertTrue(Files.exists(button)),
			() -> assertTrue(Files.exists(lighting)),
			() -> assertTrue(Files.exists(world)),
			() -> assertTrue(Files.exists(toggled)),
			() -> assertTrue(Files.exists(copy)),
			() -> assertEquals(HdTab.WORLD, model.tab()),
			() -> assertEquals(0, HdSettings.value(HdFeature.TEXTURES)),
			() -> assertTrue(saved.contains(HdFeature.TEXTURES.key() + "=0")),
			() -> assertTrue(HdOptionsScreen.isOpen())
		);
		System.out.println("hd showcase -> " + OUTPUT.toAbsolutePath());
	}

	/** Repaints the stand-in stock page, draws the pane over it, saves the frame. */
	private Path frame(ImageHdCanvas canvas, String name) throws IOException {
		drawStockBackdrop(canvas);
		HdOptionsScreen.draw(canvas);
		BufferedImage image = canvas.snapshot();
		File file = OUTPUT.resolve(name).toFile();
		ImageIO.write(image, "png", file);
		return file.toPath();
	}

	private HdOptionsLayout currentLayout() {
		return new HdOptionsLayout(CANVAS_WIDTH, CANVAS_HEIGHT, STOCK_WINDOW,
			HdOptionsScreen.model().tab(), HdOptionsScreen.model().rowCount());
	}

	private static void drawStockBackdrop(ImageHdCanvas canvas) {
		Graphics2D graphics = canvas.graphics();
		graphics.setColor(new Color(0x2B1F17));
		graphics.fillRect(0, 0, canvas.width(), canvas.height());
		graphics.setColor(new Color(0x3E2C21));
		graphics.fillRect(0, 0, canvas.width(), 22);
		graphics.setColor(new Color(0xC0B091));
		graphics.drawString("592 client - Graphics Options (stock interface)", 8, 15);

		HdInterfaceTarget stock = STOCK_WINDOW;
		graphics.setColor(new Color(0x6B5135));
		graphics.fillRect(stock.x(), stock.y(), stock.width(), stock.height());
		graphics.setColor(new Color(0x8E6D45));
		graphics.fillRect(stock.x() + 4, stock.y() + 4, stock.width() - 8, 20);
		graphics.setColor(new Color(0xD8C6A8));
		graphics.drawString("Graphics Options", stock.x() + 10, stock.y() + 18);
		graphics.setColor(new Color(0xB29A72));
		for (int i = 0; i < 6; i++) {
			int y = stock.y() + 60 + i * 40;
			graphics.drawString("[stock row " + (i + 1) + "]", stock.x() + 12, y);
			graphics.drawRect(stock.x() + 190, y - 12, 150, 16);
		}
	}
}
