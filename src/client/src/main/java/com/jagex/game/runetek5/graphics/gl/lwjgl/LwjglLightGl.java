package com.jagex.game.runetek5.graphics.gl.lwjgl;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

/**
 * Production {@link LwjglLightOps}: real {@code GL11} fixed-function
 * light calls. Every method requires the calling thread's GL context to
 * be current.
 */
public final class LwjglLightGl implements LwjglLightOps {

    private static final LwjglLightGl INSTANCE = new LwjglLightGl();

    private LwjglLightGl() {
    }

    /** @return the shared production implementation. */
    public static LwjglLightGl get() {
        return INSTANCE;
    }

    @Override
    public void lightfv(int light, int pname, float[] params) {
        FloatBuffer direct = MemoryUtil.memAllocFloat(params.length);
        try {
            direct.put(params);
            direct.flip();
            GL11.glLightfv(light, pname, direct);
        } finally {
            MemoryUtil.memFree(direct);
        }
    }

    @Override
    public void lightf(int light, int pname, float value) {
        GL11.glLightf(light, pname, value);
    }

    @Override
    public void lightModelfv(int pname, float[] params) {
        FloatBuffer direct = MemoryUtil.memAllocFloat(params.length);
        try {
            direct.put(params);
            direct.flip();
            GL11.glLightModelfv(pname, direct);
        } finally {
            MemoryUtil.memFree(direct);
        }
    }
}
