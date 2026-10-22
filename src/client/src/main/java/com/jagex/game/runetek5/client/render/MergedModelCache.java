package com.jagex.game.runetek5.client.render;

import com.jagex.game.runetek5.JavaModel;
import com.jagex.game.runetek5.JavaToolkit;

/**
 * Remembers merged multi-part models so a player or NPC does not rebuild one
 * every frame.
 *
 * <p>{@code JavaToolkit.method4307}/{@code method4299} copy every part of an
 * entity's model into a brand new {@link JavaModel} on each draw: a dozen
 * arrays sized to the combined mesh, the vertices, the faces and the baked
 * face colours. For an equipped player that is tens of kilobytes allocated and
 * copied per frame, and the result is identical frame after frame.
 *
 * <p>Reuse is decided by what can actually change the answer:
 * <ul>
 * <li>the part objects themselves — an equipment or sequence change swaps
 * them, so identity is the signal;</li>
 * <li>each part's lighting state — the deob's own definition of "this model
 * needs a relight" after a recolour, scale or rotation, so a part that is not
 * {@link JavaModel#lightingCurrent()} is never served from the cache;</li>
 * <li>{@link JavaModel#lightRevision} — the toolkit's light inputs, which the
 * merge bakes in;</li>
 * <li>the toolkit — switching graphics detail rebuilds every model.</li>
 * </ul>
 *
 * <p>One table per thread: {@code method4299} runs both on the client thread
 * and on raster workers, and a shared table would serialise them on a lock.
 */
public final class MergedModelCache {

	/**
	 * Enough for the entities visible at once on a crowded screen. This is also
	 * the memory bound: a merged player model is tens of kilobytes, so a full
	 * table is under a megabyte per thread that draws entities.
	 */
	private static final int CAPACITY = 32;

	private static final ThreadLocal<MergeCache<JavaModel>> CACHES = new ThreadLocal<MergeCache<JavaModel>>() {
		@Override
		protected MergeCache<JavaModel> initialValue() {
			return new MergeCache<JavaModel>(CAPACITY);
		}
	};

	private MergedModelCache() {
	}

	/**
	 * The model to draw for these parts: the part itself when there is only
	 * one, the remembered merge when nothing it depends on has changed, and a
	 * freshly merged model otherwise.
	 */
	public static JavaModel merged(JavaToolkit toolkit, JavaModel[] parts) {
		int count = 0;
		for (int i = 0; i < parts.length; i++) {
			JavaModel part = parts[i];
			if (part != null) {
				if (!part.lightingCurrent()) {
					// A dirty part would change the merge, and letting the
					// factory bake it keeps this frame correct; the next frame
					// can cache again.
					return JavaModel.method4912(toolkit, parts);
				}
				count++;
			}
		}
		if (count < 2) {
			return JavaModel.method4912(toolkit, parts);
		}
		final JavaToolkit owner = toolkit;
		final JavaModel[] inputs = parts;
		return CACHES.get().resolve(owner, inputs, JavaModel.lightRevision, new MergeCache.Builder<JavaModel>() {
			@Override
			public JavaModel build() {
				return JavaModel.method4912(owner, inputs);
			}
		});
	}

}
