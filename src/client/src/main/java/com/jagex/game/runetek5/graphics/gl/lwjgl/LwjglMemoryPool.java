package com.jagex.game.runetek5.graphics.gl.lwjgl;

import com.jagex.game.runetek5.cache.node.MemoryPool;

/**
 * {@link MemoryPool} for the LWJGL renderer, replacing the
 * {@code GlMemoryPool}/native-heap pairing. Allocations are
 * {@link LwjglByteBuffer} instances owned by their callers; the pool only
 * records its configured capacity and release state.
 */
public final class LwjglMemoryPool extends MemoryPool {

    private final int capacity;
    private boolean released;

    /**
     * @param capacity configured capacity in bytes.
     */
    public LwjglMemoryPool(int capacity) {
        this.capacity = capacity;
    }

    /** @return configured capacity in bytes. */
    public int capacity() {
        return this.capacity;
    }

    /** @return true after {@link #deallocate()} has run. */
    public boolean isReleased() {
        return this.released;
    }

    /**
     * @param size bytes to allocate, must be non-negative.
     */
    public LwjglByteBuffer allocate(int size) {
        if (this.released || size < 0) {
            throw new RuntimeException();
        }
        return LwjglByteBuffer.allocate(size);
    }

    /** Marks the pool released. Idempotent; caller-owned buffers are untouched. */
    public void deallocate() {
        this.released = true;
    }
}
