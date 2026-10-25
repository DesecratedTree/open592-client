package com.jagex.game.runetek5.client.hd;

import java.util.ArrayList;
import java.util.List;

/**
 * A backend that records writes instead of touching the toolkit.
 *
 * <p>Shared by the settings, pane and preset tests so they can assert exactly
 * which stock flags a preset or click would have changed, and - just as
 * important - that {@code Off} changes none of them.
 */
public final class RecordingHdGraphicsBackend implements HdGraphicsBackend {

	private final List<HdFeature> writes = new ArrayList<HdFeature>();

	private final List<Integer> values = new ArrayList<Integer>();

	private boolean canApply = true;

	private int flushes;

	public void setCanApply(boolean value) {
		this.canApply = value;
	}

	public List<HdFeature> writes() {
		return this.writes;
	}

	public int valueOf(HdFeature feature) {
		int index = this.writes.indexOf(feature);
		return index < 0 ? -1 : this.values.get(index).intValue();
	}

	public boolean wrote(HdFeature feature) {
		return this.writes.contains(feature);
	}

	public int flushes() {
		return this.flushes;
	}

	public void clear() {
		this.writes.clear();
		this.values.clear();
		this.flushes = 0;
	}

	@Override
	public boolean canApply() {
		return this.canApply;
	}

	@Override
	public void apply(HdFeature feature, int value) {
		if (!this.canApply) {
			return;
		}
		this.writes.add(feature);
		this.values.add(Integer.valueOf(value));
	}

	@Override
	public void flush() {
		if (this.canApply) {
			this.flushes++;
		}
	}
}
