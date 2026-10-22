package com.jagex.game.runetek5.client.render;

/**
 * The GL driver's own names for itself, captured once while the context is
 * current and shown by the displayfps overlay.
 *
 * <p>{@code glGetString} can only be called on the thread the context is
 * current on, so the toolkit records the strings during initialisation and the
 * overlay formats them later from here. The parts are the driver's, not ours:
 * the GL version (which on most drivers also carries the driver build), the
 * vendor and the renderer name, e.g.
 * {@code 4.6.0 NVIDIA 617.42 | NVIDIA Corporation | NVIDIA GeForce RTX 4070}.</p>
 */
public final class RendererInfo {

	/**
	 * Long enough for a full driver string at the overlay's small font, short
	 * enough that the right-aligned line cannot run off the left of a 765-pixel
	 * canvas.
	 */
	public static final int MAX_LENGTH = 110;

	private static final String SEPARATOR = " | ";

	private static final String PREFIX = "GL:";

	private static final String ELLIPSIS = "...";

	private static volatile String version = "";

	private static volatile String vendor = "";

	private static volatile String renderer = "";

	private RendererInfo() {
	}

	/** Records what the driver reported; empty and null parts are dropped. */
	public static void record(String version, String vendor, String renderer) {
		RendererInfo.version = clean(version);
		RendererInfo.vendor = clean(vendor);
		RendererInfo.renderer = clean(renderer);
	}

	/**
	 * @return the overlay line, or an empty string when no context has reported
	 * itself (the Java renderer, or before initialisation)
	 */
	public static String describe() {
		StringBuilder line = new StringBuilder();
		append(line, version);
		append(line, vendor);
		append(line, renderer);
		if (line.length() == 0) {
			return "";
		}
		String text = PREFIX + line;
		if (text.length() > MAX_LENGTH) {
			text = text.substring(0, MAX_LENGTH - ELLIPSIS.length()) + ELLIPSIS;
		}
		return text;
	}

	public static String getVersion() {
		return version;
	}

	public static String getVendor() {
		return vendor;
	}

	public static String getRenderer() {
		return renderer;
	}

	/** Clears the capture, as the next toolkit (or a test) starts from nothing. */
	public static void reset() {
		version = "";
		vendor = "";
		renderer = "";
	}

	private static void append(StringBuilder line, String part) {
		if (part.length() == 0) {
			return;
		}
		if (line.length() > 0) {
			line.append(SEPARATOR);
		}
		line.append(part);
	}

	private static String clean(String value) {
		return value == null ? "" : value.trim();
	}
}
