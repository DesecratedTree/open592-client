package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Lazy model vertex/index uploads owned by the LWJGL renderer, replacing
 * the two buffer sites in {@code GlModel}.
 *
 * <p>{@link #uploadVertex} mirrors the vertex site: a missing buffer is
 * created through the {@code method1955} VBO branch (stride 5123,
 * caller-chosen usage) and an existing one is refreshed through
 * {@code method3657} (which re-pins the stride to 5123).
 * {@link #uploadIndex} mirrors the index site the same way through the
 * {@code method1960} VBO branch and {@code method5184}. Like the legacy
 * path the LWJGL renderer always takes the VBO branch, so there is no
 * client-memory fallback.
 *
 * <p>Deleting the holder deletes both buffers; a deleted holder must be
 * discarded, re-uploading to it throws exactly like uploading to a deleted
 * {@link LwjglGlBuffer}.
 */
public final class LwjglModelBuffers {

    /** Vertex stride from the legacy {@code method1955} branch. */
    public static final int VERTEX_STRIDE = 5123;

    private final LwjglToolkit toolkit;
    private final LwjglGlOps ops;
    private LwjglArrayBuffer vertexBuffer;
    private LwjglElementArrayBuffer indexBuffer;

    /**
     * @param toolkit owning toolkit, adjusted for every size change.
     * @param ops GL seam; {@link LwjglGl#get()} in game code.
     */
    public LwjglModelBuffers(LwjglToolkit toolkit, LwjglGlOps ops) {
        if (toolkit == null || ops == null) {
            throw new RuntimeException();
        }
        this.toolkit = toolkit;
        this.ops = ops;
    }

    /**
     * Creates or refreshes the vertex buffer.
     *
     * @param data vertex bytes, at least {@code size} bytes.
     * @param size storage size in bytes.
     * @param streamDraw true for streaming uploads, false for static.
     * @return the live vertex buffer.
     */
    public LwjglArrayBuffer uploadVertex(byte[] data, int size, boolean stream) {
        if (data == null) {
            throw new RuntimeException();
        }
        if (this.vertexBuffer == null) {
            this.vertexBuffer = new LwjglArrayBuffer(this.toolkit, VERTEX_STRIDE, data, size, stream, this.ops);
        } else {
            this.vertexBuffer.method3657(data, size);
        }
        return this.vertexBuffer;
    }

    /**
     * Creates or refreshes the index buffer.
     *
     * @param data index bytes, at least {@code size} bytes.
     * @param size storage size in bytes.
     * @param indexType index element type, preserved across re-uploads.
     * @param streamDraw true for streaming uploads, false for static.
     * @return the live index buffer.
     */
    public LwjglElementArrayBuffer uploadIndex(byte[] data, int size, int indexType, boolean stream) {
        if (data == null) {
            throw new RuntimeException();
        }
        if (this.indexBuffer == null) {
            this.indexBuffer = new LwjglElementArrayBuffer(this.toolkit, indexType, data, size, stream, this.ops);
        } else {
            this.indexBuffer.method5184(size, indexType, data);
        }
        return this.indexBuffer;
    }

    /** @return the vertex buffer, null before the first upload. */
    public LwjglArrayBuffer vertexBuffer() {
        return this.vertexBuffer;
    }

    /** @return the index buffer, null before the first upload. */
    public LwjglElementArrayBuffer indexBuffer() {
        return this.indexBuffer;
    }

    /** Deletes both buffers. Idempotent. */
    public void delete() {
        if (this.vertexBuffer != null) {
            this.vertexBuffer.delete();
        }
        if (this.indexBuffer != null) {
            this.indexBuffer.delete();
        }
    }
}
