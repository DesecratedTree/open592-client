package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Seam for the handful of GL buffer calls the LWJGL buffer classes need.
 * The production implementation issues real {@code GL15} calls (and so
 * requires a current context); tests inject a recording fake, which keeps
 * buffer lifecycle logic headless-safe.
 */
public interface LwjglGlOps {

    /** @return a fresh buffer object name. */
    int genBuffer();

    /** Binds a buffer to its target. */
    void bindBuffer(int target, int id);

    /** Orphaning upload: (re)allocates immutable-by-size storage and fills it. */
    void upload(int target, byte[] data, int size, int usage);

    /** In-place update of existing storage. */
    void subUpload(int target, long offset, byte[] data, int size);

    /** Deletes a buffer object. Idempotent per id. */
    void deleteBuffer(int id);
}
