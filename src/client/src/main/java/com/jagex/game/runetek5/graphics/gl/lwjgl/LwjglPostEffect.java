package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Fullscreen post effect owned by the LWJGL renderer, replacing the
 * {@code Class4_Sub10} role for the new compositor. The compositor drives
 * effects through this interface exactly like
 * {@code FramebufferCompositor} drives the legacy effects: register calls
 * {@link #isAvailable()} or {@link #setup()}, every composite runs
 * {@link #present} then {@link #finish} per pass, and the final quad is
 * drawn by the compositor with the effect's program left active.
 */
public interface LwjglPostEffect {

    /** @return true when the effect can run without setup. */
    boolean isAvailable();

    /**
     * Creates the effect's GL resources.
     *
     * @return true when every resource (including shader programs) built.
     */
    boolean setup();

    /**
     * @return passes per composite. The stock effects use one.
     */
    default int passes() {
        return 1;
    }

    /**
     * @return float-format need driving the compositor's colour format:
     * 0 is byte RGBA, 1 is 32-bit float, 2 is 16-bit float.
     */
    default int formatNeed() {
        return 0;
    }

    /** @return true when the effect needs the depth texture. Always false in stock. */
    default boolean needsDepthTexture() {
        return false;
    }

    /**
     * Runs one pass, leaving the composite program active with the scene
     * on unit 0 and the effect result on unit 1 for the compositor's quad.
     */
    void present(int pass, LwjglTexture2D scene, LwjglTexture2D depth);

    /** Unbinds the program and texture units after a pass. */
    void finish(int pass);

    /** Rebuilds size-dependent resources. */
    void resize(int width, int height);

    /** Deletes every GL resource. */
    void release();

    /** Marks the effect registered (true) or retired (false). */
    void setActive(boolean active);

    /** @return true while registered with the compositor. */
    boolean isActive();
}
