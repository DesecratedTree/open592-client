package com.jagex.game.runetek5.client.hd;

/**
 * Where a {@link HdFeature} writes its value.
 *
 * <p>Keeping this as a separate vocabulary means the pane, the presets and the
 * persistence layer never contain renderer calls; only
 * {@link LegacyHdGraphicsBackend} interprets the stock targets. A row that
 * names {@link #RENDERER} writes nothing here at all.
 */
public enum HdStockTarget {

	/**
	 * No stock state: the 592HD renderer owns the row and reads its value live
	 * at draw time. Whether the renderer honours it yet is
	 * {@link HdSupport} - a RENDERER row is READY when the pipeline implements
	 * it and PENDING_RENDERER while the value is only stored. This enum never
	 * says which of the two it is.
	 */
	RENDERER,

	/** {@code Class50.aBoolean307}, re-applied through the model caches. */
	TEXTURES,

	/** {@code Class50.aBoolean289}, re-applied through a scene rebuild. */
	GROUND_BLENDING,

	/** {@code Class50.method2852}, the stock scenery-shadow level. */
	SHADOWS,

	/** The stock bloom level, {@code HashTable.method2673}. */
	BLOOM,

	/** {@code Class50.anInt3440}, the multisample count held at toolkit init. */
	ANTI_ALIASING,

	/** {@code FogDistance} and the camera preference file. */
	RENDER_DISTANCE
}
