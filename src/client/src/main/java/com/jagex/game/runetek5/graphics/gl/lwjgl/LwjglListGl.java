package com.jagex.game.runetek5.graphics.gl.lwjgl;

import org.lwjgl.opengl.GL11;

/**
 * Production {@link LwjglListOps}: real {@code GL11} display-list calls.
 * Every method requires the calling thread's GL context to be current.
 */
public final class LwjglListGl implements LwjglListOps {

    private static final LwjglListGl INSTANCE = new LwjglListGl();

    private LwjglListGl() {
    }

    /** @return the shared production implementation. */
    public static LwjglListGl get() {
        return INSTANCE;
    }

    @Override
    public int genLists(int range) {
        return GL11.glGenLists(range);
    }

    @Override
    public void callList(int id) {
        GL11.glCallList(id);
    }

    @Override
    public void newList(int id, int mode) {
        GL11.glNewList(id, mode);
    }

    @Override
    public void endList() {
        GL11.glEndList();
    }
}
