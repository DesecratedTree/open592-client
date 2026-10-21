package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Compiled GLSL shader owned by the LWJGL renderer, replacing the
 * {@code Class267} role. Compilation mirrors
 * {@code MusicPatch.method4397}: create, source, compile, then check the
 * compile status (35713) and print the info log on failure.
 *
 * <p>Deletion goes through {@link LwjglTextureOps#deleteObject}, exactly
 * like the legacy tick frees {@code Class267} handles with
 * {@code glDeleteObjectARB}.
 */
public final class LwjglShader {

    /** Fragment shader type, as used by every legacy GLSL caller. */
    public static final int TYPE_FRAGMENT_SHADER = 0x8B30;

    /** Compile status parameter. */
    public static final int COMPILE_STATUS = 0x8B81;

    private final LwjglTextureOps ops;
    private final long id;
    private final int type;
    private boolean deleted;

    private LwjglShader(LwjglTextureOps ops, long id, int type) {
        this.ops = ops;
        this.id = id;
        this.type = type;
    }

    /**
     * Compiles GLSL source, returning null (after printing the log, like
     * the legacy path) when compilation fails.
     */
    public static LwjglShader compile(LwjglTextureOps ops, int type, String source) {
        if (ops == null || source == null) {
            throw new RuntimeException();
        }
        long id = ops.createShader(type);
        if (id <= 0L) {
            return null;
        }
        ops.shaderSource(id, source);
        ops.compileShader(id);
        if (ops.getObjectParam(id, COMPILE_STATUS) == 0) {
            System.out.println("Shader compile failed:");
            String log = ops.getObjectInfoLog(id);
            if (log.length() > 0) {
                System.out.println(log);
            }
            ops.deleteObject(id);
            return null;
        }
        return new LwjglShader(ops, id, type);
    }

    /** @return GLSL shader object handle. */
    public long id() {
        return this.id;
    }

    /** @return shader type passed to {@link #compile}. */
    public int type() {
        return this.type;
    }

    /** @return true after {@link #delete()}. */
    public boolean isDeleted() {
        return this.deleted;
    }

    /** Deletes the GL object. Idempotent. */
    public void delete() {
        if (!this.deleted) {
            this.deleted = true;
            this.ops.deleteObject(this.id);
        }
    }
}
