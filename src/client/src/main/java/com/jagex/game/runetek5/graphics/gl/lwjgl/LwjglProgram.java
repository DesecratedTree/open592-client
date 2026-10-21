package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Linked GLSL program owned by the LWJGL renderer, replacing the
 * {@code Class210} role. Linking mirrors
 * {@code TileLighting.method3694}: every shader must be non-null with a
 * live handle, then create, attach all, link, and check the link status
 * (35714). On failure the log is printed, every shader is detached, the
 * program is deleted, and null is returned.
 *
 * <p>Call sites set uniforms through the typed setters, which resolve the
 * location per call exactly like the legacy
 * {@code glGetUniformLocationARB}/{@code glUniform} sequences in
 * {@code RectangularTextureGL} and the water effects.
 */
public final class LwjglProgram {

    /** Link status parameter. */
    public static final int LINK_STATUS = 0x8B82;

    private final LwjglTextureOps ops;
    private final long id;
    private boolean deleted;

    private LwjglProgram(LwjglTextureOps ops, long id) {
        this.ops = ops;
        this.id = id;
    }

    /**
     * Links shaders into a program, returning null (after printing the
     * log, like the legacy path) when any shader is unusable or linking
     * fails.
     */
    public static LwjglProgram link(LwjglTextureOps ops, LwjglShader[] shaders) {
        if (ops == null || shaders == null) {
            throw new RuntimeException();
        }
        for (int i = 0; i < shaders.length; i++) {
            if (shaders[i] == null || shaders[i].isDeleted() || shaders[i].id() <= 0L) {
                return null;
            }
        }
        long id = ops.createProgram();
        for (int i = 0; i < shaders.length; i++) {
            ops.attachShader(id, shaders[i].id());
        }
        ops.linkProgram(id);
        if (ops.getObjectParam(id, LINK_STATUS) == 0) {
            System.out.println("Shader linking failed:");
            String log = ops.getObjectInfoLog(id);
            if (log.length() > 0) {
                System.out.println(log);
            }
            for (int i = 0; i < shaders.length; i++) {
                ops.detachShader(id, shaders[i].id());
            }
            ops.deleteObject(id);
            return null;
        }
        return new LwjglProgram(ops, id);
    }

    /** @return GLSL program object handle. */
    public long id() {
        return this.id;
    }

    /** @return true after {@link #delete()}. */
    public boolean isDeleted() {
        return this.deleted;
    }

    /** Installs this program. */
    public void use() {
        if (this.deleted) {
            throw new RuntimeException();
        }
        this.ops.useProgram(this.id);
    }

    /** Unbinds any program. */
    public static void unbind(LwjglTextureOps ops) {
        if (ops == null) {
            throw new RuntimeException();
        }
        ops.useProgram(0L);
    }

    /** Sets an int uniform by name. */
    public void uniform1i(String name, int value) {
        this.ops.uniform1i(this.ops.getUniformLocation(this.id, name), value);
    }

    /** Sets a float uniform by name. */
    public void uniform1f(String name, float value) {
        this.ops.uniform1f(this.ops.getUniformLocation(this.id, name), value);
    }

    /** Sets a vec2 uniform by name. */
    public void uniform2f(String name, float x, float y) {
        this.ops.uniform2f(this.ops.getUniformLocation(this.id, name), x, y);
    }

    /** Sets a vec3 uniform by name. */
    public void uniform3f(String name, float x, float y, float z) {
        this.ops.uniform3f(this.ops.getUniformLocation(this.id, name), x, y, z);
    }

    /** Sets a vec4 uniform by name. */
    public void uniform4f(String name, float x, float y, float z, float w) {
        this.ops.uniform4f(this.ops.getUniformLocation(this.id, name), x, y, z, w);
    }

    /** Deletes the GL object. Idempotent. */
    public void delete() {
        if (!this.deleted) {
            this.deleted = true;
            this.ops.deleteObject(this.id);
        }
    }
}
