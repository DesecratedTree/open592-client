package com.jagex.game.runetek5.graphics.gl.lwjgl;

import org.lwjgl.opengl.awt.GLData;

/**
 * Pure, headless-safe configuration for the LWJGL high-detail renderer.
 *
 * <p>Holds the pinned LWJGL/lwjgl3-awt versions, the runtime-presence probe
 * used for toolkit selection, and the {@link GLData} describing the OpenGL
 * context hosted inside the AWT canvas hierarchy. None of this touches AWT
 * peers or creates a GL context, so it is safe to use from unit tests.
 *
 * <p>Context baseline is a compatibility profile at OpenGL 2.1: 2.1 is the
 * highest compatibility profile macOS offers, and pinning a single version
 * keeps behaviour identical on Windows/Linux too. This mirrors what the
 * legacy {@code GlToolkit} assumed (fixed-function pipeline, 8/8/8/8
 * colour, 24-bit depth).
 */
public final class LwjglConfig {

    /** LWJGL version pinned by the Gradle BOM (see build.gradle). */
    public static final String LWJGL_VERSION = "3.3.6";

    /**
     * lwjgl3-awt release pinned in Gradle. 0.2.3 was built against LWJGL
     * 3.3.5, the closest 3.3.x pairing for 3.3.6; both target Java 8.
     */
    public static final String LWJGL_AWT_VERSION = "0.2.3";

    /** Compatibility-profile major version every desktop target supports. */
    public static final int BASE_MAJOR_VERSION = 2;

    /** Compatibility-profile minor version every desktop target supports. */
    public static final int BASE_MINOR_VERSION = 1;

    private LwjglConfig() {
    }

    /**
     * @return true when the LWJGL core, OpenGL bindings and the AWT bridge
     * are all loadable. Creates no windows, peers or contexts.
     */
    public static boolean isRuntimePresent() {
        try {
            Class.forName("org.lwjgl.Version");
            Class.forName("org.lwjgl.opengl.GL");
            Class.forName("org.lwjgl.opengl.awt.AWTGLCanvas");
            Class.forName("org.lwjgl.opengl.awt.GLData");
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    /**
     * Maps the client's multisample preference (as passed to the legacy
     * {@code GlToolkit} constructor) onto MSAA samples for {@link GLData}.
     *
     * @param multisamplePreference requested samples, may be 0 or negative.
     * @return clamped sample count, never negative.
     */
    public static int samplesForPreference(int multisamplePreference) {
        return Math.max(0, multisamplePreference);
    }

    /**
     * Builds the context description for the embedded HD canvas.
     *
     * @param multisamplePreference requested MSAA samples (0 disables).
     * @return a compatibility-profile {@link GLData} with vsync enabled.
     */
    public static GLData newGLData(int multisamplePreference) {
        GLData data = new GLData();
        data.doubleBuffer = true;
        data.redSize = 8;
        data.greenSize = 8;
        data.blueSize = 8;
        data.alphaSize = 8;
        data.depthSize = 24;
        data.samples = samplesForPreference(multisamplePreference);
        data.profile = GLData.Profile.COMPATIBILITY;
        data.majorVersion = BASE_MAJOR_VERSION;
        data.minorVersion = BASE_MINOR_VERSION;
        data.swapInterval = 1;
        return data;
    }
}
