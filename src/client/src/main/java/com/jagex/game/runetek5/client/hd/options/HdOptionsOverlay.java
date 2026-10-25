package com.jagex.game.runetek5.client.hd.options;

import com.jagex.game.runetek5.client.hd.HdCapabilities;
import com.jagex.game.runetek5.client.hd.HdPreset;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.client.hd.HdSupport;
import com.jagex.game.runetek5.client.hd.HdTab;

/**
 * Draws the 592HD button and page.
 *
 * <p>All geometry comes from {@link HdOptionsLayout} and all text from the
 * model, so the overlay is a pure projection of state onto a canvas. That is
 * what lets the real pane be rendered into an image in a test, and it keeps
 * the in-game adapter (which only knows the Toolkit primitives) trivial.
 */
public final class HdOptionsOverlay {

	private static final int PANEL = 0x3E2E1E;

	private static final int PANEL_EDGE = 0x8B7355;

	private static final int HEADER = 0x5A4632;

	private static final int ROW_DARK = 0x46351F;

	private static final int ROW_LIGHT = 0x3E2E1E;

	private static final int BUTTON = 0x4C3A25;

	private static final int BUTTON_ACTIVE = 0x7A5E3A;

	private static final int TEXT = 0xFFE8C0;

	private static final int TEXT_DIM = 0xA8977F;

	private static final int VALUE_READY = 0x8CFF8C;

	private static final int VALUE_PENDING = 0xFFD070;

	private static final int VALUE_NA = 0x8A7A6A;

	private static final int ACCENT = 0xFFD700;

	private HdOptionsOverlay() {
	}

	/** The small "592HD" button on the stock Graphics Options window. */
	public static void drawOpenButton(HdCanvas canvas, HdOptionsLayout layout) {
		HdRect button = layout.openButton();
		if (button == null) {
			return;
		}
		canvas.fillRect(button.x() + 1, button.y() + 1, button.width(), button.height(), 0x1A1208);
		canvas.fillRect(button.x(), button.y(), button.width(), button.height(), HdSettings.isEnabled() ? BUTTON_ACTIVE : BUTTON);
		canvas.outlineRect(button.x(), button.y(), button.width(), button.height(), PANEL_EDGE);
		canvas.text("592HD", button.x(), button.y() + 3, button.width(), 12, ACCENT, 1);
	}

	/** The full page. */
	public static void drawPage(HdCanvas canvas, HdOptionsLayout layout, HdOptionsModel model) {
		HdRect page = layout.page();
		canvas.fillRect(page.x() + 3, page.y() + 3, page.width(), page.height(), 0x140E07);
		canvas.fillRect(page.x(), page.y(), page.width(), page.height(), PANEL);
		canvas.outlineRect(page.x(), page.y(), page.width(), page.height(), PANEL_EDGE);

		canvas.fillRect(page.x() + 1, page.y() + 1, page.width() - 2, 20, HEADER);
		canvas.text("592HD", page.x() + 8, page.y() + 4, 120, 14, ACCENT, 0);
		String status = HdCapabilities.statusLine();
		canvas.text(status, page.x() + page.width() - 8 - 200, page.y() + 5, 200, 12, TEXT_DIM, 2);

		drawPresets(canvas, layout);
		drawTabs(canvas, layout, model);
		drawRows(canvas, layout, model);
		drawFooter(canvas, layout, model);
		drawBack(canvas, layout);
	}

	private static void drawPresets(HdCanvas canvas, HdOptionsLayout layout) {
		HdPreset[] presets = HdPreset.buttons();
		HdPreset active = HdSettings.preset();
		for (int i = 0; i < layout.presetCount() && i < presets.length; i++) {
			HdRect rect = layout.presetButton(i);
			boolean selected = active == presets[i];
			canvas.fillRect(rect.x(), rect.y(), rect.width(), rect.height(), selected ? BUTTON_ACTIVE : BUTTON);
			canvas.outlineRect(rect.x(), rect.y(), rect.width(), rect.height(), selected ? ACCENT : PANEL_EDGE);
			canvas.text(presets[i].label(), rect.x(), rect.y() + 2, rect.width(), 12,
				selected ? ACCENT : TEXT, 1);
		}
	}

	private static void drawTabs(HdCanvas canvas, HdOptionsLayout layout, HdOptionsModel model) {
		HdTab[] tabs = HdTab.values();
		for (int i = 0; i < layout.tabCount() && i < tabs.length; i++) {
			HdRect rect = layout.tabButton(i);
			boolean selected = tabs[i] == model.tab();
			canvas.fillRect(rect.x(), rect.y(), rect.width(), rect.height(), selected ? BUTTON_ACTIVE : BUTTON);
			canvas.outlineRect(rect.x(), rect.y(), rect.width(), rect.height(), PANEL_EDGE);
			canvas.text(tabs[i].label(), rect.x(), rect.y() + 3, rect.width(), 12,
				selected ? ACCENT : TEXT, 1);
		}
	}

	private static void drawRows(HdCanvas canvas, HdOptionsLayout layout, HdOptionsModel model) {
		for (int i = 0; i < model.rowCount(); i++) {
			HdRect rect = layout.row(i);
			HdSupport support = model.rowSupport(i);
			canvas.fillRect(rect.x(), rect.y(), rect.width(), rect.height(), i % 2 == 0 ? ROW_DARK : ROW_LIGHT);
			canvas.text(model.rowLabel(i), rect.x() + 4, rect.y() + 2, rect.width() / 2, 12,
				support == HdSupport.UNAVAILABLE ? TEXT_DIM : TEXT, 0);
			int valueColour = support == HdSupport.READY ? VALUE_READY
				: (support == HdSupport.PENDING_RENDERER ? VALUE_PENDING : VALUE_NA);
			// rowValueText carries the support marker the legend names, so an
			// amber "On" is never mistaken for a row the renderer honours.
			canvas.text(model.rowValueText(i), rect.x() + rect.width() / 2, rect.y() + 2,
				rect.width() / 2 - 4, 12, valueColour, 2);
		}
	}

	private static void drawFooter(HdCanvas canvas, HdOptionsLayout layout, HdOptionsModel model) {
		HdRect footer = layout.footer();
		// The markers come from HdSupport, the same source the value cells use,
		// so the legend cannot name a symbol no row draws.
		String pending = HdSupport.PENDING_RENDERER.marker() + " stored, not honoured yet";
		String unavailable = HdSupport.UNAVAILABLE.marker() + " not supported by this client";
		String legend;
		if (model.hasPendingRows() && model.hasUnavailableRows()) {
			legend = pending + "    " + unavailable;
		} else if (model.hasPendingRows()) {
			legend = pending;
		} else if (model.hasUnavailableRows()) {
			legend = unavailable;
		} else {
			legend = "click a value to change it";
		}
		canvas.text(legend, footer.x() + 2, footer.y(), footer.width(), 12, TEXT_DIM, 0);
	}

	private static void drawBack(HdCanvas canvas, HdOptionsLayout layout) {
		HdRect back = layout.backButton();
		canvas.fillRect(back.x(), back.y(), back.width(), back.height(), BUTTON);
		canvas.outlineRect(back.x(), back.y(), back.width(), back.height(), PANEL_EDGE);
		canvas.text("Back", back.x(), back.y() + 3, back.width(), 12, TEXT, 1);
	}
}
