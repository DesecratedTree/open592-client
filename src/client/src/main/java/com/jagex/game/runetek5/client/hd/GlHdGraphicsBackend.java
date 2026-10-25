package com.jagex.game.runetek5.client.hd;

import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

/**
 * The 592HD backend: stock graphics flags for the rows that have one, and the
 * shader pipeline for the rows that are the renderer's own.
 *
 * <p>{@link LegacyHdGraphicsBackend} still owns every stock target, so turning
 * 592HD off leaves exactly the flags the player had. The rows with
 * {@link HdStockTarget#RENDERER} are read live at draw time instead of being
 * pushed here, so this backend contains no renderer state and no GL handles.
 *
 * <p>This backend never compiles or deletes a program: the GL call happens on
 * the draw thread when the pipeline first binds, and the program is owned by
 * the toolkit that created it and deleted in that toolkit's teardown. Settings
 * can therefore be changed from the pane without touching a GL context from
 * the wrong thread.
 */
public final class GlHdGraphicsBackend implements HdGraphicsBackend {

	private final LegacyHdGraphicsBackend stock = new LegacyHdGraphicsBackend();

	@Override
	public boolean canApply() {
		return this.stock.canApply();
	}

	@Override
	public void apply(HdFeature feature, int value) {
		if (feature == null || !feature.appliesToStock()) {
			return;
		}
		this.stock.apply(feature, value);
	}

	@Override
	public void flush() {
		this.stock.flush();
	}

	/** The OpenGL toolkit drawing frames, or null when another one is active. */
	public static GlToolkit activeToolkit() {
		Toolkit toolkit = MonoStereoData.aClass19_16;
		return toolkit instanceof GlToolkit ? (GlToolkit) toolkit : null;
	}
}
