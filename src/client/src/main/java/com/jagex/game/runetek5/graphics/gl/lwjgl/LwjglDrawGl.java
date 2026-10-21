package com.jagex.game.runetek5.graphics.gl.lwjgl;

import org.lwjgl.opengl.GL11;

/**
 * Production {@link LwjglDrawOps}: real {@code GL11} draw calls.
 * Every method requires the calling thread's GL context to be current.
 */
public final class LwjglDrawGl implements LwjglDrawOps {

    private static final LwjglDrawGl INSTANCE = new LwjglDrawGl();

    private LwjglDrawGl() {
    }

    /** @return the shared production implementation. */
    public static LwjglDrawGl get() {
        return INSTANCE;
    }

    @Override
    public void drawElements(int mode, int count, int type, long offset) {
        GL11.glDrawElements(mode, count, type, offset);
    }

    @Override
    public void drawArrays(int mode, int first, int count) {
        GL11.glDrawArrays(mode, first, count);
    }
}
