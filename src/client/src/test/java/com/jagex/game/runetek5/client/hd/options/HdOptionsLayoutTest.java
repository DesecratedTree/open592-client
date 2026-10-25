package com.jagex.game.runetek5.client.hd.options;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdTab;

@DisplayName("592HD pane layout")
class HdOptionsLayoutTest {

	private static final HdInterfaceTarget TARGET = new HdInterfaceTarget(742, (742 << 16) | 2, 100, 50, 512, 338);

	@Test
	@DisplayName("the page fits inside the stock window")
	void page_shouldFitTheWindow() {
		HdOptionsLayout layout = layout(765, 503, TARGET, HdTab.LIGHTING);

		HdRect page = layout.page();
		assertAll(
			() -> assertTrue(page.x() >= TARGET.x()),
			() -> assertTrue(page.right() <= TARGET.x() + TARGET.width()),
			() -> assertTrue(page.y() >= TARGET.y()),
			() -> assertTrue(page.bottom() <= TARGET.y() + TARGET.height())
		);
	}

	@Test
	@DisplayName("every row is inside the page and above the footer")
	void rows_shouldSitInsideThePage() {
		HdOptionsLayout layout = layout(765, 503, TARGET, HdTab.LIGHTING);

		HdRect page = layout.page();
		assertEquals(HdFeature.forTab(HdTab.LIGHTING).length, layout.rowCount());
		for (int i = 0; i < layout.rowCount(); i++) {
			HdRect row = layout.row(i);
			assertAll(
				() -> assertTrue(row.x() >= page.x()),
				() -> assertTrue(row.right() <= page.right()),
				() -> assertTrue(row.y() >= page.y()),
				() -> assertTrue(row.bottom() <= layout.footer().y())
			);
		}
	}

	@Test
	@DisplayName("the open button sits on the stock window's top right")
	void openButton_shouldSitOnTheWindow() {
		HdOptionsLayout layout = layout(765, 503, TARGET, HdTab.LIGHTING);

		HdRect button = layout.openButton();
		assertAll(
			() -> assertNotNull(button),
			() -> assertTrue(button.contains(TARGET.x() + TARGET.width() - 20, TARGET.y() + 10)),
			() -> assertFalse(button.contains(TARGET.x() + 10, TARGET.y() + 10))
		);
	}

	@Test
	@DisplayName("hit testing maps control centres to their actions")
	void hitTest_shouldMapControls() {
		HdOptionsLayout layout = layout(765, 503, TARGET, HdTab.WORLD);

		assertAll(
			() -> assertEquals(HdOptionsAction.Kind.BACK, layout.hitTest(centreX(layout.backButton()), centreY(layout.backButton())).kind()),
			() -> assertEquals(HdOptionsAction.Kind.OPEN, layout.hitTest(centreX(layout.openButton()), centreY(layout.openButton())).kind()),
			() -> assertEquals(HdOptionsAction.Kind.TAB, layout.hitTest(centreX(layout.tabButton(2)), centreY(layout.tabButton(2))).kind()),
			() -> assertEquals(2, layout.hitTest(centreX(layout.tabButton(2)), centreY(layout.tabButton(2))).index()),
			() -> assertEquals(HdOptionsAction.Kind.PRESET, layout.hitTest(centreX(layout.presetButton(1)), centreY(layout.presetButton(1))).kind()),
			() -> assertEquals(HdOptionsAction.Kind.ROW, layout.hitTest(centreX(layout.row(3)), centreY(layout.row(3))).kind()),
			() -> assertEquals(3, layout.hitTest(centreX(layout.row(3)), centreY(layout.row(3))).index())
		);
	}

	@Test
	@DisplayName("clicks outside the page and button are not consumed by the layout")
	void hitTest_shouldIgnoreOutsideClicks() {
		HdOptionsLayout layout = layout(765, 503, TARGET, HdTab.LIGHTING);

		assertAll(
			() -> assertNull(layout.hitTest(5, 5)),
			() -> assertNull(layout.hitTest(TARGET.x() + TARGET.width() - 2, TARGET.y() + TARGET.height() - 2))
		);
	}

	@Test
	@DisplayName("a standalone pane centres itself and has no stock button")
	void standalone_shouldCentre() {
		HdOptionsLayout layout = layout(765, 503, null, HdTab.EFFECTS);

		HdRect page = layout.page();
		assertAll(
			() -> assertNull(layout.openButton()),
			() -> assertTrue(Math.abs((page.x() + page.width() / 2) - 765 / 2) <= 2),
			() -> assertEquals(HdFeature.forTab(HdTab.EFFECTS).length, layout.rowCount())
		);
	}

	@Test
	@DisplayName("a small canvas clamps the page inside the screen")
	void smallCanvas_shouldClampThePage() {
		HdOptionsLayout layout = layout(320, 240, null, HdTab.LIGHTING);

		HdRect page = layout.page();
		assertAll(
			() -> assertTrue(page.x() >= 2),
			() -> assertTrue(page.right() <= 320),
			() -> assertTrue(page.y() >= 2)
		);
	}

	@Test
	@DisplayName("row count follows the tab")
	void rows_shouldFollowTheTab() {
		assertAll(
			() -> assertEquals(HdFeature.forTab(HdTab.LIGHTING).length,
				layout(765, 503, TARGET, HdTab.LIGHTING).rowCount()),
			() -> assertEquals(HdFeature.forTab(HdTab.WORLD).length,
				layout(765, 503, TARGET, HdTab.WORLD).rowCount()),
			() -> assertEquals(HdFeature.forTab(HdTab.EFFECTS).length,
				layout(765, 503, TARGET, HdTab.EFFECTS).rowCount()),
			() -> assertEquals(HdFeature.forTab(HdTab.DISPLAY).length,
				layout(765, 503, TARGET, HdTab.DISPLAY).rowCount())
		);
	}

	private static HdOptionsLayout layout(int width, int height, HdInterfaceTarget target, HdTab tab) {
		return new HdOptionsLayout(width, height, target, tab, HdFeature.forTab(tab).length);
	}

	private static int centreX(HdRect rect) {
		return rect.x() + rect.width() / 2;
	}

	private static int centreY(HdRect rect) {
		return rect.y() + rect.height() / 2;
	}
}
