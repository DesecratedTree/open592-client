package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Seam for the frame-level GL calls the LWJGL renderer needs: framebuffer
 * and renderbuffer objects, read/draw binding, blits, the viewport, and
 * the immediate-mode matrix/quad calls the post pipeline issues.
 * The production implementation issues real {@code GL11}/{@code GL13}/
 * {@code GL30} calls (and so requires a current context); tests inject a
 * recording fake, which keeps framebuffer lifecycle logic headless-safe.
 *
 * <p>Method shapes mirror the legacy {@code jaggl} call sites:
 * framebuffer handling in {@code GlFramebuffer} and
 * {@code FramebufferCompositor}, blits in {@code compositeEffects}, and
 * the {@code glBegin(7)} quad passes in {@code RectangularTextureGL},
 * the water effects and the final present quad.
 */
public interface LwjglFrameOps {

    /** @return true when framebuffer objects are usable. Always true for GL30. */
    boolean supportsFramebufferObjects();

    /** @return a fresh framebuffer object name. */
    int genFramebuffer();

    /** Deletes a framebuffer object. Idempotent per id. */
    void deleteFramebuffer(int id);

    /**
     * Binds a framebuffer object. Target is framebuffer (both), read, or
     * draw; id 0 restores the default framebuffer.
     */
    void bindFramebuffer(int target, int id);

    /** Checks framebuffer completeness on a bound target. */
    int checkFramebufferStatus(int target);

    /** Selects the draw buffer of the draw-bound framebuffer. */
    void drawBuffer(int attachment);

    /** Selects the read buffer of the read-bound framebuffer. */
    void readBuffer(int attachment);

    /** Copies a rectangle between the read and draw framebuffers. */
    void blitFramebuffer(int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter);

    /** @return a fresh renderbuffer object name. */
    int genRenderbuffer();

    /** Deletes a renderbuffer object. Idempotent per id. */
    void deleteRenderbuffer(int id);

    /** Binds a renderbuffer for storage allocation. */
    void bindRenderbuffer(int id);

    /** Allocates single-sample renderbuffer storage. */
    void renderbufferStorage(int format, int width, int height);

    /** Allocates multisample renderbuffer storage. */
    void renderbufferStorageMultisample(int samples, int format, int width, int height);

    /** Attaches a renderbuffer to a bound framebuffer object. */
    void framebufferRenderbuffer(int target, int attachment, int renderbuffer);

    /** Sets the viewport rectangle. */
    void viewport(int x, int y, int width, int height);

    /** Pushes an attribute group bitmask. */
    void pushAttrib(int mask);

    /** Pops the attribute group. */
    void popAttrib();

    /** Selects a matrix mode. */
    void matrixMode(int mode);

    /** Pushes the current matrix. */
    void pushMatrix();

    /** Pops the matrix stack. */
    void popMatrix();

    /** Loads the identity matrix. */
    void loadIdentity();

    /** Installs an orthographic projection. */
    void ortho(double left, double right, double bottom, double top, double near, double far);

    /** Begins a quad batch. Every legacy pass draws quads. */
    void beginQuads();

    /** Ends the current batch. */
    void end();

    /** Emits a 2D texture coordinate. */
    void texCoord2f(float s, float t);

    /** Emits a 2D texture coordinate on a non-zero unit. */
    void multiTexCoord2f(int unit, float s, float t);

    /** Emits an integer 2D vertex. */
    void vertex2i(int x, int y);

    /** Emits a float 2D vertex. */
    void vertex2f(float x, float y);

    /** Enables a capability (vertex/fragment programs, scissor, ...). */
    void enableCap(int cap);

    /** Disables a capability. */
    void disableCap(int cap);

    /** Sets texture-coordinate generation parameters. */
    void texGen(int coordinate, int pname, float[] params);

    /** Sets the current RGBA colour for untextured and tinted draws. */
    void color4f(float r, float g, float b, float a);

    /** Sets the blend factors (mirrors the state-cache blend pair). */
    void blendFunc(int src, int dst);

    /** Multiplies the current matrix (model placement). */
    void multMatrixf(float[] matrix);

    /** Translates the current matrix. */
    void translatef(float x, float y, float z);
}
