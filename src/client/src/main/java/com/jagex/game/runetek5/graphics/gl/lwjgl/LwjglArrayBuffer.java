package com.jagex.game.runetek5.graphics.gl.lwjgl;

import com.jagex.game.runetek5.buffer.ArrayBufferGL;

/**
 * LWJGL vertex-attribute buffer, replacing the {@code GlVboArrayBuffer}
 * role. Implements the same {@link ArrayBufferGL} contract so future render
 * code can consume either implementation; the VBO path reports a zero
 * device address exactly like the legacy version.
 */
public final class LwjglArrayBuffer extends LwjglGlBuffer implements ArrayBufferGL {

    private int stride;

    /**
     * @param toolkit owning toolkit.
     * @param stride vertex stride in bytes.
     * @param data initial contents, exactly {@code size} bytes.
     * @param size storage size in bytes.
     * @param streamDraw true for streaming uploads, false for static.
     * @param ops GL seam; {@link LwjglGl#get()} in game code.
     */
    public LwjglArrayBuffer(LwjglToolkit toolkit, int stride, byte[] data, int size, boolean streamDraw, LwjglGlOps ops) {
        super(toolkit, TARGET_ARRAY_BUFFER, data, size, streamDraw, ops);
        this.stride = stride;
    }

    @Override
    public int getId() {
        return super.id();
    }

    @Override
    public void method3657(byte[] arg0, int arg1) {
        super.upload(arg0, arg1);
        this.stride = 5123;
    }

    @Override
    public long getAddress() {
        return 0L;
    }

    @Override
    public int getStride() {
        return this.stride;
    }
}
