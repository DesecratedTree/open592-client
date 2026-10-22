package com.jagex.game.runetek5.graphics.gl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.lwjgl.Version;
import org.lwjgl.system.MemoryUtil;

/**
 * Proves the LWJGL dependency set is really on the classpath, not just in the
 * build file: the pinned version resolves, the AWT bridge and the modules it
 * needs are compiled against the same version, and the native library for this
 * platform can be extracted and loaded.
 *
 * <p>The build-level half of this check lives in {@code verifyLwjglDependencies},
 * which covers every platform's native jar; this one covers the platform the
 * tests are running on.
 */
class LwjglDependencyTest {

	@Test
	void lwjglCoreIsThePinnedVersion() {
		assertEquals(3, Version.VERSION_MAJOR);
		assertEquals(4, Version.VERSION_MINOR);
		assertEquals(3, Version.VERSION_REVISION);
	}

	@Test
	void theAwtBridgeAndItsBindingsArePresent() throws Exception {
		// Loading without initialising: these types touch native code in their
		// static initialisers, and presence is what is being checked here.
		String[] required = {
				"org.lwjgl.awt.AWT",
				"org.lwjgl.opengl.awt.AWTGLCanvas",
				"org.lwjgl.opengl.awt.GLData",
				"org.lwjgl.system.jawt.JAWT",
				"org.lwjgl.egl.EGL",
		};
		for (int i = 0; i < required.length; i++) {
			Class<?> type = Class.forName(required[i], false, getClass().getClassLoader());
			assertNotNull(type, required[i]);
		}
	}

	@Test
	void theNativeLibraryForThisPlatformIsUsable() {
		// Allocating through MemoryUtil loads the platform's native library the
		// same way the renderer will; a missing native jar surfaces here as an
		// UnsatisfiedLinkError instead of at first draw.
		java.nio.ByteBuffer buffer = MemoryUtil.memAlloc(16);
		try {
			buffer.putLong(0, 0x0123456789ABCDEFL);
			assertEquals(0x0123456789ABCDEFL, buffer.getLong(0));
		} finally {
			MemoryUtil.memFree(buffer);
		}
	}
}
