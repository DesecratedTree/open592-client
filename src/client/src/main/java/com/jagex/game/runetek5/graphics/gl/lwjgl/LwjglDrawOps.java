package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Seam for the draw calls backing LWJGL scene rendering.
 * The production implementation issues real {@code GL11} calls (and so
 * requires a current context); tests inject a recording fake, which keeps
 * draw lifecycle logic headless-safe.
 */
public interface LwjglDrawOps {

    /** Draws indexed geometry from the bound element buffer. */
    void drawElements(int mode, int count, int type, long offset);

    /** Draws non-indexed geometry from the bound array buffer. */
    void drawArrays(int mode, int first, int count);
}
