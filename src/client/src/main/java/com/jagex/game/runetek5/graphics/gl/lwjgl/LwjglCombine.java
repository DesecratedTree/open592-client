package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Single owner of the fixed-function combiner tables for the LWJGL
 * renderer, replacing the {@code GlToolkit} combine path
 * ({@code method1900}, {@code method1958}, {@code method1962},
 * {@code method5295}) with pure, headless-testable mappings.
 *
 * <p>Combine presets 0-4 install the RGB/alpha pair from the legacy
 * {@code method1900} table; the same RGB column doubles as the
 * {@code method1962} enum used by the sprite channel-mask path. Blend
 * modes are the full legacy {@code method1958} range 0/1/2/128 with the
 * alpha-test rule folded in. Channel colours mirror the
 * {@code method5295} {@code method1913} values. Unknown presets and blend
 * modes throw, matching the LWJGL toolkit contract (legacy
 * {@code method1900} silently ignores them, but every in-repo caller only
 * passes 0-4 and the tests pin the rejection).
 */
public final class LwjglCombine {

    /** Texture environment target (legacy literal 8960). */
    public static final int TARGET_TEXTURE_ENV = 0x2300;

    /** RGB combine pname base (legacy literal 34161). */
    public static final int COMBINE_RGB = 0x8571;

    /** Alpha combine pname base (legacy literal 34162). */
    public static final int COMBINE_ALPHA = 0x8572;

    /** Texture combine unit offset base (legacy literal 34176). */
    public static final int COMBINE_UNIT_BASE = 0x8580;

    /** Texture source operand offset base (legacy literal 34192). */
    public static final int SOURCE_UNIT_BASE = 0x8590;

    /** Texture coordinate combine offset base (legacy literal 34184). */
    public static final int TEXGEN_UNIT_BASE = 0x8588;

    /** Texture coordinate source offset base (legacy literal 34200). */
    public static final int TEXSRC_UNIT_BASE = 0x8598;

    /** Alpha-test capability (legacy literal 3008). */
    public static final int CAP_ALPHA_TEST = 0x0BC0;

    /** RGB/alpha combine op for preset 1 (legacy literal 7681). */
    public static final int COMBINE_REPLACE = 0x1E01;

    /** RGB/alpha combine op for preset 0 (legacy literal 8448). */
    public static final int COMBINE_MODULATE = 0x2100;

    /** Dot-product combine op for mask textures (legacy literal 34479). */
    public static final int COMBINE_DOT3_RGBA = 0x86AF;

    /** Texture matrix mode (legacy literal 5890). */
    public static final int MATRIX_TEXTURE = 0x1702;

    /** Modelview matrix mode (legacy literal 5888). */
    public static final int MATRIX_MODELVIEW = 0x1700;

    /** Blend off: {@code method1958} mode 0 disables blending. */
    public static final int BLEND_NONE = 0;

    /** Standard alpha blending: {@code method1958} mode 1. */
    public static final int BLEND_ALPHA = 1;

    /** Additive blending: {@code method1958} mode 2. */
    public static final int BLEND_ADD = 2;

    /** Modulate blending: {@code method1958} mode 128. */
    public static final int BLEND_MULTIPLY = 128;

    private LwjglCombine() {
    }

    /**
     * @return the RGB combine op for a {@code method1900} preset.
     */
    public static int rgbOp(int combineMode) {
        if (combineMode == 1) {
            return 7681;
        } else if (combineMode == 0) {
            return 8448;
        } else if (combineMode == 2) {
            return 34165;
        } else if (combineMode == 3) {
            return 260;
        } else if (combineMode == 4) {
            return 34023;
        }
        throw new RuntimeException();
    }

    /**
     * @return the alpha combine op for a {@code method1900} preset.
     */
    public static int alphaOp(int combineMode) {
        if (combineMode == 1) {
            return 7681;
        } else if (combineMode == 0) {
            return 8448;
        } else if (combineMode == 2) {
            return 7681;
        } else if (combineMode == 3) {
            return 8448;
        } else if (combineMode == 4) {
            return 34023;
        }
        throw new RuntimeException();
    }

    /**
     * @return the source factors for a {@code method1958} blend mode:
     *     {@code {src, dst}}.
     */
    public static int[] blendFactors(int blendMode) {
        if (blendMode == BLEND_ALPHA) {
            return new int[] { 770, 771 };
        } else if (blendMode == BLEND_ADD) {
            return new int[] { 1, 1 };
        } else if (blendMode == BLEND_MULTIPLY) {
            return new int[] { 774, 1 };
        } else if (blendMode == BLEND_NONE) {
            return new int[] { 770, 771 };
        }
        throw new RuntimeException();
    }

    /** @return true when the blend mode enables the alpha test. */
    public static boolean alphaTestEnabled(int blendMode) {
        if (blendMode == BLEND_ALPHA || blendMode == BLEND_MULTIPLY) {
            return true;
        } else if (blendMode == BLEND_NONE || blendMode == BLEND_ADD) {
            return false;
        }
        throw new RuntimeException();
    }

    /**
     * @return the {@code method1913} texenv colour for a channel-mask
     *     index: {@code {r, g, b, a}} in {@code method1913} argument order.
     */
    public static float[] channelEnvColor(int channel) {
        if (channel == 0) {
            return new float[] { 0.5F, 0.0F, 0.5F, 1.0F };
        } else if (channel == 1) {
            return new float[] { 1.0F, 0.0F, 0.5F, 0.5F };
        } else if (channel == 2) {
            return new float[] { 0.5F, 0.0F, 1.0F, 0.5F };
        } else if (channel == 3) {
            return new float[] { 128.5F, 0.0F, 128.5F, 128.5F };
        }
        throw new RuntimeException();
    }
}
