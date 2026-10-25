package com.jagex.game.runetek5.client.hd.shadow;

/**
 * One frame's shadow-map uniform payload: which upload of the map the draw
 * should sample, and the matrix that turns a world position into its texel.
 *
 * <p>Immutable, so the lighting pipeline can compare frame ids and skip the
 * matrix upload for every primitive after the first of a frame.
 */
public final class ShadowBinding {

	private final int frameId;

	private final int size;

	private final int radius;

	private final float bias;

	private final float[] matrix;

	public ShadowBinding(int frameId, int size, int radius, float bias, float[] matrix) {
		this.frameId = frameId;
		this.size = size;
		this.radius = radius;
		this.bias = bias;
		this.matrix = matrix == null ? new float[16] : matrix.clone();
	}

	/** Increments once per depth pass, so a frame's uploads are shared. */
	public int frameId() {
		return this.frameId;
	}

	/** The map edge in texels; the shader's texel size is its reciprocal. */
	public int size() {
		return this.size;
	}

	/** PCF kernel radius in texels: 0 is a single tap. */
	public int radius() {
		return this.radius;
	}

	/** Depth bias in texture-space depth units. */
	public float bias() {
		return this.bias;
	}

	/** Column-major world-to-shadow-texture matrix. */
	public float[] matrix() {
		return this.matrix.clone();
	}
}
