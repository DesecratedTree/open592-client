package com.jagex.game.runetek5.client.render;

/**
 * Names the renderer the client's frames are actually painted with, for the
 * displayfps overlay.
 *
 * <p>{@code Packet.anInt2973} already holds the resolved graphics mode: the
 * toolkit setup stores the mode it ended up building with, and its fallback
 * path rewrites the mode to 0 when the OpenGL toolkit cannot start. So mode 1
 * means the GL toolkit is drawing, and every other value — low detail, standard
 * detail (which this port builds with the Java renderer) or the pre-init -1 —
 * means the Java software renderer. The overlay reads that value rather than
 * keeping a second copy that could drift.</p>
 */
public final class RendererLabel {

	/** The LWJGL OpenGL toolkit ({@code GlToolkit}). */
	public static final String OPENGL = "OpenGL";

	/** The pure-Java rasteriser ({@code JavaToolkit}). */
	public static final String SOFTWARE = "Software";

	private RendererLabel() {
	}

	/**
	 * @param graphicsMode the resolved mode from {@code Packet.anInt2973}
	 * @return {@link #OPENGL} for the GL toolkit, {@link #SOFTWARE} otherwise
	 */
	public static String of(int graphicsMode) {
		return graphicsMode == 1 ? OPENGL : SOFTWARE;
	}
}
