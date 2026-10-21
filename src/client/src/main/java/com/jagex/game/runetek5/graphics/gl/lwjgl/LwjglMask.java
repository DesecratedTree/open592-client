package com.jagex.game.runetek5.graphics.gl.lwjgl;

import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;

/**
 * Clipping mask owned by the LWJGL renderer, replacing the
 * {@code Class243.method5256} role.
 *
 * <p>The span fill mirrors the legacy loop exactly: every row paints
 * {@code widths[row]} set texels starting at
 * {@code offsets[row] + row * width}. The result uploads as a
 * byte-per-texel 6406 image, mirroring the mask texture construction.
 */
public final class LwjglMask extends ComponentSurface {

    private final int width;
    private final int height;
    private final byte[] bits;
    private final LwjglTexture2D texture;

    /**
     * @param toolkit owning toolkit.
     * @param width mask width in texels.
     * @param height mask height in texels.
     * @param rowOffsets first set texel per row.
     * @param rowWidths set-texel run length per row.
     */
    public LwjglMask(LwjglToolkit toolkit, int width, int height, int[] rowOffsets, int[] rowWidths) {
        if (toolkit == null || width <= 0 || height <= 0 || rowOffsets == null || rowWidths == null
            || rowOffsets.length < height || rowWidths.length < height) {
            throw new RuntimeException();
        }
        this.width = width;
        this.height = height;
        this.bits = new byte[width * height];
        for (int row = 0; row < height; row++) {
            int at = rowOffsets[row] + row * width;
            for (int i = 0; i < rowWidths[row]; i++) {
                this.bits[at++] = -1;
            }
        }
        this.texture = new LwjglTexture2D(toolkit, LwjglSprite.TARGET_TEXTURE_2D, LwjglFont.FORMAT_ALPHA,
            width, height, false, this.bits, LwjglFont.FORMAT_ALPHA, false, toolkit.textureOps());
    }

    /** @return mask width in texels. */
    public int width() {
        return this.width;
    }

    /** @return mask height in texels. */
    public int height() {
        return this.height;
    }

    /** @return backing mask texture. */
    public LwjglTexture2D texture() {
        return this.texture;
    }

    /** @return copy of the packed span bits. */
    public byte[] bits() {
        byte[] copy = new byte[this.bits.length];
        System.arraycopy(this.bits, 0, copy, 0, this.bits.length);
        return copy;
    }
}
