package com.jagex.game.runetek5.graphics.gl.lwjgl;

import com.jagex.game.runetek5.buffer.ElementsBufferGL;

/**
 * LWJGL index buffer, replacing the {@code GlElementsArrayBuffer} VBO role.
 * Implements the same {@link ElementsBufferGL} contract; the VBO path
 * reports a zero device address exactly like the legacy version.
 */
public final class LwjglElementArrayBuffer extends LwjglGlBuffer implements ElementsBufferGL {

    private int indexType;

    /**
     * @param toolkit owning toolkit.
     * @param indexType index element type.
     * @param data initial contents, exactly {@code size} bytes.
     * @param size storage size in bytes.
     * @param streamDraw true for streaming uploads, false for static.
     * @param ops GL seam; {@link LwjglGl#get()} in game code.
     */
    public LwjglElementArrayBuffer(LwjglToolkit toolkit, int indexType, byte[] data, int size, boolean streamDraw, LwjglGlOps ops) {
        super(toolkit, TARGET_ELEMENT_ARRAY_BUFFER, data, size, streamDraw, ops);
        this.indexType = indexType;
    }

    @Override
    public int method5181() {
        return 0;
    }

    @Override
    public int method5182() {
        return this.indexType;
    }

    @Override
    public long getAddress() {
        return 0L;
    }

    @Override
    public void method5184(int arg0, int arg1, byte[] arg2) {
        super.upload(arg2, arg0);
        this.indexType = arg1;
    }
}
