package com.jagex.game.runetek5.client.hd.options;

import com.jagex.game.runetek5.client.hd.HdPreset;
import com.jagex.game.runetek5.client.hd.HdTab;

/**
 * Where every 592HD control sits for one frame.
 *
 * <p>Pure geometry: it only knows a canvas size, an optional anchor window,
 * the active tab and how many rows that tab has. The screen uses it to draw
 * and to hit-test clicks, and tests use it without a client, a cache or a GL
 * context - which is the only way layout regressions can be caught here.
 *
 * <p>The page is centred on the stock window when one is open and on the
 * canvas otherwise, so {@code ::hdsettings} still works before any interface
 * is up.
 */
public final class HdOptionsLayout {

	private static final int INSET = 6;

	private static final int TITLE_HEIGHT = 22;

	private static final int PRESET_HEIGHT = 16;

	private static final int TAB_HEIGHT = 18;

	private static final int ROW_HEIGHT = 16;

	/** Smallest row that still reads; a tab with this many rows just fits. */
	private static final int MIN_ROW_HEIGHT = 12;

	private static final int FOOTER_HEIGHT = 14;

	private static final int BACK_HEIGHT = 18;

	private static final int BACK_WIDTH = 56;

	private static final int MIN_PAGE_WIDTH = 360;

	private static final int MAX_PAGE_WIDTH = 520;

	private static final int STANDALONE_WIDTH = 480;

	/** The stock-page button that opens the pane. */
	private static final int OPEN_BUTTON_WIDTH = 62;

	private static final int OPEN_BUTTON_HEIGHT = 16;

	private final HdRect page;

	private final HdRect openButton;

	private final HdRect backButton;

	private final HdRect[] presetButtons;

	private final HdRect[] tabButtons;

	private final HdRect[] rowRects;

	public HdOptionsLayout(int canvasWidth, int canvasHeight, HdInterfaceTarget target, HdTab tab, int rowCount) {
		int rows = rowCount < 0 ? 0 : rowCount;
		int availableWidth = Math.max(1, canvasWidth);
		int pageWidth;
		if (target != null) {
			pageWidth = clamp(target.width() - 24, MIN_PAGE_WIDTH, MAX_PAGE_WIDTH);
		} else {
			pageWidth = Math.min(STANDALONE_WIDTH, availableWidth - 40);
		}
		pageWidth = Math.max(200, Math.min(pageWidth, availableWidth - 8));
		// The stock window is a fixed 338 pixels tall, so a tab grows rows into
		// it rather than past it: the row height comes down to MIN_ROW_HEIGHT
		// before the page is allowed to leave the window. That keeps every row
		// inside the page and the footer below the last one as tabs grow.
		int fixedHeight = TITLE_HEIGHT + PRESET_HEIGHT + TAB_HEIGHT + FOOTER_HEIGHT + BACK_HEIGHT
			+ INSET * 2;
		int maxHeight = target != null ? target.height() - 8 : Math.max(1, canvasHeight - 8);
		int rowHeight = ROW_HEIGHT;
		if (rows > 0 && fixedHeight + rows * rowHeight > maxHeight) {
			rowHeight = Math.max(MIN_ROW_HEIGHT, (maxHeight - fixedHeight) / rows);
		}
		int pageHeight = fixedHeight + rows * rowHeight;
		int pageX = target != null
			? target.x() + (target.width() - pageWidth) / 2
			: (availableWidth - pageWidth) / 2;
		int pageY = target != null
			? target.y() + (target.height() - pageHeight) / 2
			: (Math.max(1, canvasHeight) - pageHeight) / 2;
		pageX = Math.max(2, Math.min(pageX, availableWidth - pageWidth - 2));
		pageY = Math.max(2, pageY);
		this.page = new HdRect(pageX, pageY, pageWidth, pageHeight);

		this.openButton = target == null ? null : new HdRect(
			target.x() + target.width() - OPEN_BUTTON_WIDTH - 8, target.y() + 4,
			OPEN_BUTTON_WIDTH, OPEN_BUTTON_HEIGHT);

		HdPreset[] presets = HdPreset.buttons();
		int presetWidth = (pageWidth - INSET * 2) / presets.length;
		this.presetButtons = new HdRect[presets.length];
		int presetY = pageY + TITLE_HEIGHT;
		for (int i = 0; i < presets.length; i++) {
			this.presetButtons[i] = new HdRect(pageX + INSET + i * presetWidth, presetY, presetWidth - 2, PRESET_HEIGHT);
		}

		HdTab[] tabs = HdTab.values();
		int tabWidth = (pageWidth - INSET * 2) / tabs.length;
		this.tabButtons = new HdRect[tabs.length];
		int tabY = presetY + PRESET_HEIGHT + 3;
		for (int i = 0; i < tabs.length; i++) {
			this.tabButtons[i] = new HdRect(pageX + INSET + i * tabWidth, tabY, tabWidth - 2, TAB_HEIGHT);
		}

		this.rowRects = new HdRect[rows];
		int rowY = tabY + TAB_HEIGHT + 4;
		for (int i = 0; i < rows; i++) {
			this.rowRects[i] = new HdRect(pageX + INSET, rowY + i * rowHeight, pageWidth - INSET * 2, rowHeight);
		}

		int footerY = rowY + rows * rowHeight + 2;
		int backY = footerY + FOOTER_HEIGHT + 2;
		this.backButton = new HdRect(pageX + pageWidth - INSET - BACK_WIDTH, backY, BACK_WIDTH, BACK_HEIGHT);
	}

	private static int clamp(int value, int min, int max) {
		if (value < min) {
			return min;
		}
		if (value > max) {
			return max;
		}
		return value;
	}

	public HdRect page() {
		return this.page;
	}

	public HdRect openButton() {
		return this.openButton;
	}

	public HdRect backButton() {
		return this.backButton;
	}

	public HdRect presetButton(int index) {
		return this.presetButtons[index];
	}

	public HdRect tabButton(int index) {
		return this.tabButtons[index];
	}

	public HdRect row(int index) {
		return this.rowRects[index];
	}

	public int rowCount() {
		return this.rowRects.length;
	}

	public int presetCount() {
		return this.presetButtons.length;
	}

	public int tabCount() {
		return this.tabButtons.length;
	}

	/** The footer strip, used for the pending/unsupported legend. */
	public HdRect footer() {
		HdRect last = this.rowRects.length == 0
			? new HdRect(this.page.x(), this.page.y() + TITLE_HEIGHT + PRESET_HEIGHT + TAB_HEIGHT + 4, 1, 1)
			: this.rowRects[this.rowRects.length - 1];
		return new HdRect(this.page.x() + INSET, last.bottom() + 2, this.page.width() - INSET * 2, FOOTER_HEIGHT);
	}

	/**
	 * Hit-tests a click.
	 *
	 * @return the action, or null when the point is not on a control
	 */
	public HdOptionsAction hitTest(int x, int y) {
		if (this.openButton != null && this.openButton.contains(x, y)) {
			return HdOptionsAction.open();
		}
		if (!this.page.contains(x, y)) {
			return null;
		}
		if (this.backButton.contains(x, y)) {
			return HdOptionsAction.back();
		}
		for (int i = 0; i < this.tabButtons.length; i++) {
			if (this.tabButtons[i].contains(x, y)) {
				return HdOptionsAction.tab(i);
			}
		}
		for (int i = 0; i < this.presetButtons.length; i++) {
			if (this.presetButtons[i].contains(x, y)) {
				return HdOptionsAction.preset(i);
			}
		}
		for (int i = 0; i < this.rowRects.length; i++) {
			if (this.rowRects[i].contains(x, y)) {
				return HdOptionsAction.row(i);
			}
		}
		return null;
	}
}
