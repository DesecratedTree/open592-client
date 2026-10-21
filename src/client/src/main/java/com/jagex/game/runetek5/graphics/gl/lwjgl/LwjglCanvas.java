package com.jagex.game.runetek5.graphics.gl.lwjgl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.awt.AWTGLCanvas;
import org.lwjgl.opengl.awt.GLData;

/**
 * AWT-embedded OpenGL canvas for the LWJGL high-detail renderer.
 *
 * <p>This is the bridge between the client's existing AWT hierarchy
 * ({@code GameCanvas}/{@code GameShell}) and LWJGL: unlike stock GLFW, which
 * owns a separate native window, {@link AWTGLCanvas} renders into a regular
 * AWT {@code Canvas} so the game view stays where the Applet put it.
 *
 * <p>Threading mirrors the legacy native path: the game thread owns GL.
 * {@link #attachGameThread()} makes the context current on the game thread
 * and installs capabilities once, and nothing ever releases it (AWT paint
 * events never issue GL), so uploads and draws work from any toolkit
 * method; {@link #presentFrame()} then only swaps the finished frame.
 */
public final class LwjglCanvas extends AWTGLCanvas {

    private static final long serialVersionUID = 1L;

    /** True once {@link #initGL()} ran for the current native context. */
    private boolean gameThreadAttached;

    /**
     * @param data context description, see
     * {@link LwjglConfig#newGLData(int)}.
     */
    public LwjglCanvas(GLData data) {
        super(data);
    }

    /**
     * Creates a canvas with the standard HD context for the given
     * multisample preference.
     */
    public static LwjglCanvas createHD(int multisamplePreference) {
        return new LwjglCanvas(LwjglConfig.newGLData(multisamplePreference));
    }

    @Override
    public void initGL() {
        // Installs thread-local LWJGL capabilities for this context. Must
        // run on the thread that will issue GL calls for the canvas.
        GL.createCapabilities();
        GL11.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void paintGL() {
        int width = Math.max(1, getFramebufferWidth());
        int height = Math.max(1, getFramebufferHeight());
        GL11.glViewport(0, 0, width, height);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        swapBuffers();
    }

    /**
     * Makes this canvas' context current on the calling thread and installs
     * capabilities on first use, then leaves the context current: the game
     * thread issues GL from many call sites outside any frame callback, so
     * releasing (as {@code render()} does) would break every upload after
     * the first present. Idempotent; safe to call once per frame to heal a
     * context lost to canvas re-homing. Call only on the game thread, and
     * only once the canvas is displayable (added under a showing parent).
     */
    public void attachGameThread() {
        this.beforeRender();
        try {
            if (!this.gameThreadAttached) {
                this.initGL();
                this.gameThreadAttached = true;
            }
        } catch (RuntimeException failure) {
            this.afterRender();
            throw failure;
        }
    }

    /**
     * Presents the frame drawn since the last call: viewport sync plus
     * buffer swap. The context must already be current on the calling
     * thread (see {@link #attachGameThread()}); unlike {@link #paintGL()}
     * this never clears, because the scene was drawn by the toolkit before
     * the present step.
     */
    public void presentFrame() {
        int width = Math.max(1, getFramebufferWidth());
        int height = Math.max(1, getFramebufferHeight());
        GL11.glViewport(0, 0, width, height);
        this.swapBuffers();
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        // The peer (and with it the context) is gone; the next attach
        // recreates both instead of issuing GL against a dead context.
        this.gameThreadAttached = false;
    }
}
