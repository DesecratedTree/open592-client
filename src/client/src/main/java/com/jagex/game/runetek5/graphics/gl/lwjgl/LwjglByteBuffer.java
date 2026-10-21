package com.jagex.game.runetek5.graphics.gl.lwjgl;

import java.nio.ByteBuffer;

import org.lwjgl.system.MemoryUtil;

/**
 * Direct byte buffer replacing the {@code jaclib.memory.NativeBuffer} role
 * for the LWJGL renderer. The legacy heap is native-only (its methods are
 * {@code native} with no pure-Java backing), so new code allocates off-heap
 * through {@link MemoryUtil} instead. Bounds violations throw
 * {@link RuntimeException}, mirroring the legacy buffer contract.
 *
 * <p>Headless-safe: allocation, reads and writes touch no GL context.
 * Ownership is explicit: every successful {@link #allocate(int)} must be
 * paired with exactly one {@link #free()}.
 */
public final class LwjglByteBuffer {

    private ByteBuffer buffer;
    private final int capacity;

    private LwjglByteBuffer(ByteBuffer buffer, int capacity) {
        this.buffer = buffer;
        this.capacity = capacity;
    }

    /**
     * @param capacity bytes to allocate off-heap, must be non-negative.
     */
    public static LwjglByteBuffer allocate(int capacity) {
        if (capacity < 0) {
            throw new RuntimeException();
        }
        return new LwjglByteBuffer(MemoryUtil.memAlloc(capacity), capacity);
    }

    /** @return usable capacity in bytes. */
    public int capacity() {
        return this.capacity;
    }

    /** @return true after {@link #free()} has run. */
    public boolean isFreed() {
        return this.buffer == null;
    }

    /**
     * Copies bytes into the buffer at an absolute offset.
     *
     * @throws RuntimeException when freed, or when the range is out of bounds.
     */
    public void put(byte[] src, int srcOffset, int dstOffset, int length) {
        if (this.buffer == null || src == null || srcOffset < 0 || dstOffset < 0 || length < 0
                || src.length < srcOffset + length || this.capacity < dstOffset + length) {
            throw new RuntimeException();
        }
        ByteBuffer dst = this.buffer.duplicate();
        dst.position(dstOffset);
        dst.put(src, srcOffset, length);
    }

    /**
     * Copies bytes out of the buffer from an absolute offset.
     *
     * @throws RuntimeException when freed, or when the range is out of bounds.
     */
    public void get(byte[] dst, int dstOffset, int srcOffset, int length) {
        if (this.buffer == null || dst == null || dstOffset < 0 || srcOffset < 0 || length < 0
                || dst.length < dstOffset + length || this.capacity < srcOffset + length) {
            throw new RuntimeException();
        }
        ByteBuffer src = this.buffer.duplicate();
        src.position(srcOffset);
        src.get(dst, dstOffset, length);
    }

    /**
     * @return a duplicate positioned for a full read, for GL upload calls.
     * @throws RuntimeException when freed.
     */
    public ByteBuffer slice() {
        if (this.buffer == null) {
            throw new RuntimeException();
        }
        ByteBuffer view = this.buffer.duplicate();
        view.position(0);
        view.limit(this.capacity);
        return view;
    }

    /** @return native address of the first byte. */
    public long address() {
        if (this.buffer == null) {
            throw new RuntimeException();
        }
        return MemoryUtil.memAddress(this.buffer);
    }

    /** Releases the allocation. Idempotent. */
    public void free() {
        if (this.buffer != null) {
            MemoryUtil.memFree(this.buffer);
            this.buffer = null;
        }
    }
}
