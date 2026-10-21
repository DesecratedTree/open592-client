package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * ARB assembly program owned by the LWJGL renderer, replacing the
 * {@code Class225} role. Loading mirrors
 * {@code ParticleBinaryTree.method5125}: generate, bind, upload the ASCII
 * source, then read the error position (34379). A position of -1 means the
 * program loaded; anything else unbinds and returns null.
 *
 * <p>Deletion goes through {@link LwjglTextureOps#deleteObject}, exactly
 * like the legacy tick frees ARB program handles with
 * {@code glDeleteObjectARB}.
 */
public final class LwjglArbProgram {

    /** Vertex program target, used by every legacy ARB caller. */
    public static final int TARGET_VERTEX_PROGRAM = 0x8620;

    /** ASCII program format. */
    public static final int FORMAT_ASCII = 0x8875;

    private final LwjglTextureOps ops;
    private final int target;
    private final int id;
    private boolean deleted;

    private LwjglArbProgram(LwjglTextureOps ops, int target, int id) {
        this.ops = ops;
        this.target = target;
        this.id = id;
    }

    /**
     * Loads ARB assembly source, returning null when the program fails to
     * load.
     */
    public static LwjglArbProgram create(LwjglTextureOps ops, int target, String source) {
        if (ops == null || source == null) {
            throw new RuntimeException();
        }
        int id = ops.genArbProgram();
        ops.bindArbProgram(target, id);
        ops.programString(target, FORMAT_ASCII, source);
        if (ops.programErrorPosition() != -1) {
            ops.bindArbProgram(target, 0);
            return null;
        }
        ops.bindArbProgram(target, 0);
        return new LwjglArbProgram(ops, target, id);
    }

    /** @return ARB program target. */
    public int target() {
        return this.target;
    }

    /** @return ARB program object name. */
    public int id() {
        return this.id;
    }

    /** @return true after {@link #delete()}. */
    public boolean isDeleted() {
        return this.deleted;
    }

    /** Binds this program. */
    public void bind() {
        if (this.deleted) {
            throw new RuntimeException();
        }
        this.ops.bindArbProgram(this.target, this.id);
    }

    /** Unbinds any program on this target. */
    public void unbind() {
        this.ops.bindArbProgram(this.target, 0);
    }

    /** Deletes the GL object. Idempotent. */
    public void delete() {
        if (!this.deleted) {
            this.deleted = true;
            this.ops.deleteObject(this.id);
        }
    }
}
