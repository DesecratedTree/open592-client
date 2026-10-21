package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Seam for the fixed-function light calls the LWJGL renderer needs.
 * The production implementation issues real {@code GL11} calls (and so
 * requires a current context); tests inject a recording fake, which keeps
 * light setup logic headless-safe.
 */
public interface LwjglLightOps {

    /** Sets a 4-float light parameter vector. */
    void lightfv(int light, int pname, float[] params);

    /** Sets a single-float light parameter. */
    void lightf(int light, int pname, float value);

    /** Sets a 4-float lighting-model parameter vector. */
    void lightModelfv(int pname, float[] params);
}
