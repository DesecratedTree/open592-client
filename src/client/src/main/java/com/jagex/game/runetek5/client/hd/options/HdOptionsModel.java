package com.jagex.game.runetek5.client.hd.options;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdGraphicsBackend;
import com.jagex.game.runetek5.client.hd.HdPreset;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.client.hd.HdSupport;
import com.jagex.game.runetek5.client.hd.HdTab;

/**
 * The pane's navigation and value state, independent of drawing and input.
 *
 * <p>The screen owns "is the pane open"; this owns "which tab is showing" and
 * applies row/preset actions to {@link HdSettings}. Splitting them keeps every
 * navigation rule testable without a canvas.
 */
public final class HdOptionsModel {

	private HdTab tab = HdTab.initial();

	public HdTab tab() {
		return this.tab;
	}

	public HdFeature[] rows() {
		return HdFeature.forTab(this.tab);
	}

	public int rowCount() {
		return rows().length;
	}

	public void selectTab(int index) {
		this.tab = HdTab.at(index);
	}

	/** Back to the first tab, as a freshly opened pane shows. */
	public void reset() {
		this.tab = HdTab.initial();
	}

	/**
	 * Applies a click action that changes state.
	 *
	 * @return true when the action was consumed; OPEN and BACK belong to the
	 *         screen and are reported as false
	 */
	public boolean apply(HdOptionsAction action, HdGraphicsBackend backend) {
		if (action == null) {
			return false;
		}
		switch (action.kind()) {
		case TAB:
			selectTab(action.index());
			return true;
		case ROW:
			HdFeature[] features = rows();
			if (action.index() >= 0 && action.index() < features.length) {
				HdSettings.cycle(features[action.index()], backend);
			}
			return true;
		case PRESET:
			HdPreset preset = HdPreset.buttonAt(action.index());
			if (preset != null) {
				HdSettings.applyPreset(preset, backend);
			}
			return true;
		default:
			return false;
		}
	}

	public String rowLabel(int index) {
		HdFeature[] features = rows();
		if (index < 0 || index >= features.length) {
			return "";
		}
		HdFeature feature = features[index];
		String label = feature.label();
		if (feature.restartRequired()) {
			label += " (restart)";
		}
		return label;
	}

	public String rowValueLabel(int index) {
		HdFeature[] features = rows();
		if (index < 0 || index >= features.length) {
			return "";
		}
		return HdSettings.valueLabel(features[index]);
	}

	/**
	 * The value cell's text: the value plus the support marker, so a pending
	 * "On" cannot read as a live one. The footer's legend names the same
	 * markers, which is why they come from {@link HdSupport} and not from a
	 * string literal here.
	 */
	public String rowValueText(int index) {
		HdFeature[] features = rows();
		if (index < 0 || index >= features.length) {
			return "";
		}
		HdFeature feature = features[index];
		String value = HdSettings.valueLabel(feature);
		String marker = feature.support().marker();
		return marker == null ? value : value + " " + marker;
	}

	public HdSupport rowSupport(int index) {
		HdFeature[] features = rows();
		if (index < 0 || index >= features.length) {
			return HdSupport.UNAVAILABLE;
		}
		return features[index].support();
	}

	/** True when the tab holds at least one row that is waiting on the pipeline. */
	public boolean hasPendingRows() {
		for (HdFeature feature : rows()) {
			if (feature.support() == HdSupport.PENDING_RENDERER) {
				return true;
			}
		}
		return false;
	}

	/** True when the tab holds at least one row this client cannot support. */
	public boolean hasUnavailableRows() {
		for (HdFeature feature : rows()) {
			if (feature.support() == HdSupport.UNAVAILABLE) {
				return true;
			}
		}
		return false;
	}
}
