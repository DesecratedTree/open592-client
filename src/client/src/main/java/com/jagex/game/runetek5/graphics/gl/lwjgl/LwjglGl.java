package com.jagex.game.runetek5.graphics.gl.lwjgl;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;

/**
 * Production {@link LwjglGlOps}: real {@code GL15} buffer calls.
 * Every method requires the calling thread's GL context to be current.
 */
public final class LwjglGl implements LwjglGlOps {

    private static final LwjglGl INSTANCE = new LwjglGl();

    private LwjglGl() {
    }

    /** @return the shared production implementation. */
    public static LwjglGl get() {
        return INSTANCE;
    }

    @Override
    public int genBuffer() {
        return GL15.glGenBuffers();
    }

    @Override
    public void bindBuffer(int target, int id) {
        GL15.glBindBuffer(target, id);
    }

    @Override
    public void upload(int target, byte[] data, int size, int usage) {
        ByteBuffer direct = MemoryUtil.memAlloc(size);
        try {
            direct.put(data, 0, size);
            direct.flip();
            GL15.glBufferData(target, direct, usage);
        } finally {
            MemoryUtil.memFree(direct);
        }
    }

    @Override
    public void subUpload(int target, long offset, byte[] data, int size) {
        ByteBuffer direct = MemoryUtil.memAlloc(size);
        try {
            direct.put(data, 0, size);
            direct.flip();
            GL15.glBufferSubData(target, offset, direct);
        } finally {
            MemoryUtil.memFree(direct);
        }
    }

    @Override
    public void deleteBuffer(int id) {
        GL15.glDeleteBuffers(id);
    }
}
