package com.jagex.game.runetek5.graphics.gl.lwjgl;

import org.lwjgl.opengl.GL15;

/**
 * GPU buffer object owned by the LWJGL renderer, replacing the
 * {@code AbstractArbBufferGL} VBO role. Uploads mirror the legacy
 * orphan-or-subdata policy: growing storage reallocates, shrinking updates
 * in place. Binding precedes every upload, mirroring
 * {@code setAsActiveBuffer}.
 *
 * <p>Construction and mutation require a current GL context on the calling
 * thread; pass a fake {@link LwjglGlOps} to exercise the lifecycle without
 * one. Deletion must likewise run on the GL thread and is idempotent. The
 * owning toolkit's VBO byte counter is adjusted on every size change, which
 * keeps {@code IA()}-style accounting honest.
 */
public class LwjglGlBuffer {

    /** Array (vertex attribute) target. */
    public static final int TARGET_ARRAY_BUFFER = GL15.GL_ARRAY_BUFFER;

    /** Element (index) target. */
    public static final int TARGET_ELEMENT_ARRAY_BUFFER = GL15.GL_ELEMENT_ARRAY_BUFFER;

    /** Static storage hint. */
    public static final int USAGE_STATIC_DRAW = GL15.GL_STATIC_DRAW;

    /** Streaming storage hint. */
    public static final int USAGE_STREAM_DRAW = GL15.GL_STREAM_DRAW;

    protected final LwjglToolkit toolkit;
    private final int target;
    private final int usage;
    private final LwjglGlOps ops;
    private final int id;
    private int size;
    private boolean deleted;

    /**
     * @param toolkit owning toolkit, adjusted for every size change.
     * @param target {@link #TARGET_ARRAY_BUFFER} or
     * {@link #TARGET_ELEMENT_ARRAY_BUFFER}.
     * @param data initial contents, exactly {@code size} bytes.
     * @param size storage size in bytes.
     * @param streamDraw true for streaming uploads, false for static.
     * @param ops GL seam; {@link LwjglGl#get()} in game code.
     */
    public LwjglGlBuffer(LwjglToolkit toolkit, int target, byte[] data, int size, boolean streamDraw, LwjglGlOps ops) {
        if (toolkit == null || data == null || ops == null || data.length < size) {
            throw new RuntimeException();
        }
        this.toolkit = toolkit;
        this.target = target;
        this.usage = streamDraw ? USAGE_STREAM_DRAW : USAGE_STATIC_DRAW;
        this.ops = ops;
        this.size = size;
        this.id = ops.genBuffer();
        this.bind();
        ops.upload(target, data, size, this.usage);
        toolkit.trackVbo(size);
    }

    /** Binds this buffer to its target. */
    public final void bind() {
        this.ops.bindBuffer(this.target, this.id);
    }

    /** @return GL object name. */
    public final int id() {
        return this.id;
    }

    /** @return current storage size in bytes. */
    public final int size() {
        return this.size;
    }

    /** @return true after {@link #delete()}. */
    public final boolean isDeleted() {
        return this.deleted;
    }

    /**
     * Replaces the buffer contents, reallocating when growing.
     *
     * @param data new contents, at least {@code size} bytes.
     */
    public final void upload(byte[] data, int size) {
        if (this.deleted || data == null || data.length < size) {
            throw new RuntimeException();
        }
        this.bind();
        if (this.size < size) {
            this.ops.upload(this.target, data, size, this.usage);
            this.toolkit.trackVbo(size - this.size);
            this.size = size;
        } else {
            this.ops.subUpload(this.target, 0L, data, size);
        }
    }

    /** Deletes the GL object and releases the tracked bytes. Idempotent. */
    public final void delete() {
        if (!this.deleted) {
            this.deleted = true;
            this.ops.deleteBuffer(this.id);
            this.toolkit.trackVbo(-this.size);
            this.size = 0;
        }
    }
}
