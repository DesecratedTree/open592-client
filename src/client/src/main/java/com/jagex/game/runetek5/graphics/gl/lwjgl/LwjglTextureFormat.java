package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Pure pixel-format tables for the LWJGL renderer, ported from the legacy
 * toolkit helpers so upload code never duplicates the math.
 *
 * <p>{@link #bytesPerPixel} mirrors {@code GlToolkit.method1920} exactly,
 * including the {@link IllegalArgumentException} on unknown formats, and
 * {@link #formatForInternalFormat} mirrors {@code Class180.method4122}.
 */
public final class LwjglTextureFormat {

    /** Default client pixel type, matching the little-endian stock path. */
    public static final int PIXEL_TYPE_UNSIGNED_BYTE = 0x1401;

    private LwjglTextureFormat() {
    }

    /**
     * Bytes per pixel for an uncompressed internal format or pixel format.
     *
     * @throws IllegalArgumentException for unknown formats, like the legacy
     * table.
     */
    public static int bytesPerPixel(int format) {
        if (format == 6406 || format == 6409) {
            return 1;
        } else if (format == 6410 || format == 34846 || format == 34844) {
            return 2;
        } else if (format == 6407) {
            return 3;
        } else if (format == 6408 || format == 34847) {
            return 4;
        } else if (format == 34843) {
            return 6;
        } else if (format == 34842) {
            return 8;
        } else if (format == 6402) {
            return 3;
        } else if (format == 6401) {
            return 1;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Pixel format matching an internal format for typeless allocation.
     *
     * @throws IllegalArgumentException for unknown formats, like the legacy
     * table.
     */
    public static int formatForInternalFormat(int internalFormat) {
        if (internalFormat == 6407 || internalFormat == 34843 || internalFormat == 34837) {
            return 6407;
        } else if (internalFormat == 6408 || internalFormat == 34842 || internalFormat == 34836) {
            return 6408;
        } else if (internalFormat == 6406 || internalFormat == 34844) {
            return 6406;
        } else if (internalFormat == 6409 || internalFormat == 34846) {
            return 6409;
        } else if (internalFormat == 6410 || internalFormat == 34847) {
            return 6410;
        } else if (internalFormat == 6402) {
            return 6402;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Whether an RGB (not ARGB) material upload is usable directly, mirroring
     * {@code Class133.method3349}: upload kinds 1 and 7 need the alpha path.
     */
    public static boolean isDirectRgbUpload(int kind) {
        return kind != 1 && kind != 7;
    }
}
