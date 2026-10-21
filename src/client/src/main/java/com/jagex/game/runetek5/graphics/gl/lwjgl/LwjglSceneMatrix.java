package com.jagex.game.runetek5.graphics.gl.lwjgl;

import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.scene.ground.Ground;

/**
 * Single owner of the scene-matrix math for the LWJGL renderer, replacing
 * the {@code GlToolkit} view/projection path ({@code ea}, {@code method1923},
 * {@code method1953}, {@code method1948}/{@code method1916}) with pure,
 * headless-testable functions. {@link LwjglToolkit} owns the resulting
 * state; this class owns the derivations so render code never duplicates
 * them.
 *
 * <p>Column-major 16-float layout everywhere, matching
 * {@code JavaMatrix.method5161}. That method returns a shared static array,
 * so every entry point here returns a fresh copy.
 */
public final class LwjglSceneMatrix {

    /** Modelview matrix mode (legacy literal 5888). */
    public static final int MODELVIEW = 0x1700;

    /** Projection matrix mode (legacy literal 5889). */
    public static final int PROJECTION = 0x1701;

    private LwjglSceneMatrix() {
    }

    /** @return identity matrix. */
    public static float[] identity() {
        return new float[] {
            1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F,
            0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F };
    }

    /**
     * Unwraps a model placement into a matrix, mirroring how
     * {@code GlModel} reads the {@code JavaMatrix} passed to
     * {@code render}. Non-matrix grounds have no placement, so they read
     * as identity instead of throwing.
     */
    public static float[] modelMatrix(Ground ground) {
        if (ground instanceof JavaMatrix) {
            float[] shared = ((JavaMatrix) ground).method5161();
            float[] copy = new float[16];
            System.arraycopy(shared, 0, copy, 0, 16);
            return copy;
        }
        return identity();
    }

    /**
     * Unwraps the scene view installed by {@code ea}, with the same
     * copy-out rule as {@link #modelMatrix}.
     */
    public static float[] viewMatrix(Ground sceneGround) {
        return modelMatrix(sceneGround);
    }

    /**
     * Rebuilds the perspective projection from the {@code H} centers and
     * scales, the viewport extent and the near/effective-far planes,
     * mirroring {@code GlToolkit.method1923} exactly: a degenerate
     * left/right or top/bottom span falls back to the near-identity matrix
     * instead of dividing by zero.
     *
     * @param out 16-float column-major destination.
     */
    public static void projection(float[] out, int centerX, int centerY, int scaleX, int scaleY,
        int width, int height, int zNear, int effectiveFar) {
        if (out == null || out.length != 16) {
            throw new RuntimeException();
        }
        if (scaleX == 0 || scaleY == 0 || zNear == 0) {
            identityInto(out);
            return;
        }
        float left = (float) (-centerX * zNear) / (float) scaleX;
        float right = (float) ((width - centerX) * zNear) / (float) scaleX;
        float top = (float) (centerY * zNear) / (float) scaleY;
        float bottom = (float) (zNear * (centerY - height)) / (float) scaleY;
        if (left == right || bottom == top) {
            identityInto(out);
            return;
        }
        float span = (float) zNear * 2.0F;
        out[6] = 0.0F;
        out[3] = 0.0F;
        out[13] = 0.0F;
        out[5] = span / (top - bottom);
        out[4] = 0.0F;
        out[7] = 0.0F;
        out[8] = (right + left) / (right - left);
        out[12] = 0.0F;
        out[9] = (top + bottom) / (top - bottom);
        out[14] = -((span * (float) effectiveFar) / (float) (effectiveFar - zNear));
        out[2] = 0.0F;
        out[15] = 0.0F;
        out[10] = (float) -(effectiveFar + zNear) / (float) (effectiveFar - zNear);
        out[1] = 0.0F;
        out[11] = -1.0F;
        out[0] = span / (right - left);
    }

    private static void identityInto(float[] out) {
        out[7] = 0.0F;
        out[13] = 0.0F;
        out[3] = 0.0F;
        out[5] = 1.0F;
        out[2] = 0.0F;
        out[9] = 0.0F;
        out[14] = 0.0F;
        out[4] = 0.0F;
        out[10] = 1.0F;
        out[15] = 1.0F;
        out[8] = 0.0F;
        out[1] = 0.0F;
        out[12] = 0.0F;
        out[0] = 1.0F;
        out[11] = 0.0F;
        out[6] = 0.0F;
    }

    /**
     * Derives the pixel clip bounds from the {@code OA} rectangle and the
     * {@code H} centers, mirroring {@code GlToolkit.method1953}:
     * {@code {minX, maxX, minY, maxY}}.
     */
    public static float[] clipBounds(int clipLeft, int clipTop, int clipRight, int clipBottom,
        int centerX, int centerY) {
        return new float[] {
            (float) (clipLeft - centerX), (float) (clipRight - centerX),
            (float) (clipTop - centerY), (float) (clipBottom - centerY) };
    }

    /**
     * Maps pixel clip bounds into the depth-normalized units
     * {@code LwjglToolkit.visible} tests ({@code nx = x / z}), by dividing
     * by the {@code H} projection scales. This mirrors the legacy
     * {@code GlModel} structure, which compares {@code eye * scale / zoom}
     * against the clip bounds at zoom 1.
     */
    public static float[] frustumFromClip(float[] clip, int scaleX, int scaleY) {
        if (clip == null || clip.length != 4) {
            throw new RuntimeException();
        }
        if (scaleX == 0 || scaleY == 0) {
            throw new RuntimeException();
        }
        return new float[] { clip[0] / (float) scaleX, clip[1] / (float) scaleX,
            clip[2] / (float) scaleY, clip[3] / (float) scaleY };
    }
}
