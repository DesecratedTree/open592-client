package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Renderbuffer owned by the LWJGL renderer, replacing the
 * {@code OpenGlRenderBuffer} role: depth or colour storage for
 * framebuffer objects, single-sample or multisample. Byte accounting
 * mirrors the legacy class (width by height at the format's byte depth)
 * and disposal releases the tracked bytes.
 *
 * <p>Construction and disposal require a current GL context on the
 * calling thread; pass fakes to exercise the lifecycle without one.
 * Unlike the legacy class, which queued deletes on the toolkit tick,
 * disposal deletes immediately: the LWJGL renderer has no cross-thread
 * delete queue, so deletes run on the GL thread instead.
 */
public class LwjglRenderbuffer {

    protected final LwjglToolkit toolkit;
    protected final LwjglFrameOps ops;

    private final int format;
    private final int width;
    private final int height;
    private final int samples;
    private final int id;
    private final int byteSize;

    private int framebufferTarget = -1;
    private int framebufferAttachment = -1;
    private boolean disposed;

    /**
     * @param toolkit owning toolkit, adjusted for the storage bytes.
     * @param format sized internal format.
     * @param width storage width in pixels.
     * @param height storage height in pixels.
     * @param samples multisample count, 1 for single-sample storage.
     * @param ops frame seam; {@link LwjglFrameGl#get()} in game code.
     */
    public LwjglRenderbuffer(LwjglToolkit toolkit, int format, int width, int height, int samples, LwjglFrameOps ops) {
        if (toolkit == null || ops == null || width <= 0 || height <= 0 || samples < 1) {
            throw new RuntimeException();
        }
        this.toolkit = toolkit;
        this.ops = ops;
        this.format = format;
        this.width = width;
        this.height = height;
        this.samples = samples;
        this.id = ops.genRenderbuffer();
        ops.bindRenderbuffer(this.id);
        if (samples > 1) {
            ops.renderbufferStorageMultisample(samples, format, width, height);
        } else {
            ops.renderbufferStorage(format, width, height);
        }
        this.byteSize = width * height * LwjglTextureFormat.bytesPerPixel(format);
        toolkit.trackRenderbuffer(this.byteSize);
    }

    /**
     * Single-sample storage, mirroring the legacy 4-argument constructor.
     */
    public LwjglRenderbuffer(LwjglToolkit toolkit, int format, int width, int height, LwjglFrameOps ops) {
        this(toolkit, format, width, height, 1, ops);
    }

    /** @return GL object name, 0 after {@link #dispose()}. */
    public int id() {
        return this.disposed ? 0 : this.id;
    }

    /** @return storage width in pixels. */
    public int width() {
        return this.width;
    }

    /** @return storage height in pixels. */
    public int height() {
        return this.height;
    }

    /** @return multisample count, 1 for single-sample storage. */
    public int samples() {
        return this.samples;
    }

    /** @return tracked storage bytes. */
    public int byteSize() {
        return this.byteSize;
    }

    /** @return true after {@link #dispose()}. */
    public boolean isDisposed() {
        return this.disposed;
    }

    /**
     * Attaches to a bound framebuffer object, mirroring
     * {@code OpenGlRenderBuffer.attach}.
     */
    public void attachToFramebuffer(int target, int attachment, LwjglFrameOps frameOps) {
        if (this.disposed || frameOps == null) {
            throw new RuntimeException();
        }
        frameOps.framebufferRenderbuffer(target, attachment, this.id);
        this.framebufferAttachment = attachment;
        this.framebufferTarget = target;
    }

    /**
     * Detaches from the framebuffer, mirroring
     * {@code OpenGlRenderBuffer.detach}.
     */
    public void detachFromFramebuffer(LwjglFrameOps frameOps) {
        if (frameOps == null) {
            throw new RuntimeException();
        }
        frameOps.framebufferRenderbuffer(this.framebufferTarget, this.framebufferAttachment, 0);
        this.framebufferAttachment = -1;
        this.framebufferTarget = -1;
    }

    /** Deletes the GL object and releases the tracked bytes. Idempotent. */
    public void dispose() {
        if (!this.disposed) {
            this.disposed = true;
            this.ops.deleteRenderbuffer(this.id);
            this.toolkit.trackRenderbuffer(-this.byteSize);
        }
    }
}
