package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Seam for the GL texture and program calls the LWJGL renderer needs.
 * The production implementation issues real {@code GL11}/{@code GL13}/
 * {@code GL20}/{@code GL30} calls (and so requires a current context);
 * tests inject a recording fake, which keeps texture and shader lifecycle
 * logic headless-safe.
 *
 * <p>Method shapes mirror the legacy {@code jaggl} call sites: texture
 * creation/binding/filtering/upload in {@code Class6} and
 * {@code OpenGl2DTexture}, cube uploads in {@code GlCubeTexture}, GLSL
 * compile/link in {@code MusicPatch}/{@code TileLighting}, and ARB vertex
 * program setup in {@code ParticleBinaryTree}.
 */
public interface LwjglTextureOps {

    /** @return a fresh texture object name. */
    int genTexture();

    /** Deletes a texture object. Idempotent per id. */
    void deleteTexture(int id);

    /** Binds a texture to its target on the active unit. */
    void bindTexture(int target, int id);

    /** Enables a texture target on the active unit. */
    void enableTarget(int target);

    /** Disables a texture target on the active unit. */
    void disableTarget(int target);

    /** Selects the active texture unit (0-based). */
    void activeTexture(int unit);

    /** Sets an integer texture parameter. */
    void texParameteri(int target, int pname, int param);

    /**
     * Sets an integer texture-environment parameter, mirroring the
     * legacy {@code glTexEnvi} combiner and texgen call sites
     * ({@code method1912}, {@code method1945}, {@code method1954}).
     */
    void texEnvi(int pname, int param);

    /**
     * Sets the float texture-environment colour, mirroring the legacy
     * {@code glTexEnvfv(8960, 8705, ...)} call sites ({@code method1901},
     * {@code method1913}).
     */
    void texEnvColor(float r, float g, float b, float a);

    /** Sets a pixel-store parameter (unpack alignment). */
    void pixelStorei(int pname, int param);

    /**
     * Sets a pixel-transfer parameter, mirroring the legacy
     * {@code glPixelTransferf} scale/bias pairs in
     * {@code SpriteGL.copyAlpha} (3348-3355).
     */
    void pixelTransferf(int pname, float param);

    /** Full 2D upload from bytes. */
    void texImage2D(int target, int level, int internalFormat, int width, int height, int format, int type, byte[] pixels, int offset);

    /** Full 2D upload from ints (BGRA client data). */
    void texImage2D(int target, int level, int internalFormat, int width, int height, int format, int type, int[] pixels, int offset);

    /** Full 2D upload from floats. */
    void texImage2D(int target, int level, int internalFormat, int width, int height, int format, int type, float[] pixels, int offset);

    /** Allocates immutable-by-content storage with no client data. */
    void allocateImage2D(int target, int level, int internalFormat, int width, int height, int format, int type);

    /** In-place 2D update from bytes. */
    void texSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, byte[] pixels, int offset);

    /** In-place 2D update from ints (BGRA client data). */
    void texSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, int[] pixels, int offset);

    /** Copies framebuffer pixels into a texture image. */
    void copyImage2D(int target, int level, int internalFormat, int x, int y, int width, int height);

    /** Copies framebuffer pixels into part of a texture image. */
    void copySubImage2D(int target, int level, int xOffset, int yOffset, int x, int y, int width, int height);

    /** Generates the mipmap pyramid for the bound texture. */
    void generateMipmap(int target);

    /** Attaches a texture image to a framebuffer object. */
    void framebufferTexture2D(int target, int attachment, int textarget, int texture, int level);

    /** Flushes pending GL commands (after copy uploads). */
    void flush();

    /** @return a fresh GLSL shader object handle. */
    long createShader(int type);

    /** Sets shader source. */
    void shaderSource(long shader, String source);

    /** Compiles a shader. */
    void compileShader(long shader);

    /**
     * Reads a shader/program object parameter: compile status (35713) for
     * shaders, link status (35714) for programs.
     */
    int getObjectParam(long object, int pname);

    /** @return the info log, or an empty string when there is none. */
    String getObjectInfoLog(long object);

    /**
     * Deletes a GLSL shader/program object or an ARB program: the legacy
     * tick frees both kinds with {@code glDeleteObjectARB}.
     */
    void deleteObject(long object);

    /** @return a fresh GLSL program object handle. */
    long createProgram();

    /** Attaches a shader to a program. */
    void attachShader(long program, long shader);

    /** Detaches a shader from a program. */
    void detachShader(long program, long shader);

    /** Links a program. */
    void linkProgram(long program);

    /** Installs a program (0 unbinds). */
    void useProgram(long program);

    /** @return a uniform location, opaque to callers. */
    int getUniformLocation(long program, String name);

    /** Sets an int uniform. */
    void uniform1i(int location, int value);

    /** Sets a float uniform. */
    void uniform1f(int location, float value);

    /** Sets a vec2 uniform. */
    void uniform2f(int location, float x, float y);

    /** Sets a vec3 uniform. */
    void uniform3f(int location, float x, float y, float z);

    /** Sets a vec4 uniform. */
    void uniform4f(int location, float x, float y, float z, float w);

    /** @return a fresh ARB program object name. */
    /** Sets an ARB program local parameter (still-water fog plane). */
    void programLocalParameter4f(int target, int index, float x, float y, float z, float w);

    int genArbProgram();

    /** Binds an ARB vertex/fragment program. */
    void bindArbProgram(int target, int id);

    /** Loads ARB assembly source into the bound program. */
    void programString(int target, int format, String source);

    /** @return the ARB program error position, -1 when loading succeeded. */
    int programErrorPosition();
}
