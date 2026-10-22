package jaggl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Canvas;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * The jaggl port replaces 167 JNI entry points with LWJGL bodies. Two things must not drift while
 * that happens: the client's call sites still resolve (same names and descriptors, including the
 * array-and-offset and long-address variants), and no method is left native, which would mean a
 * libjaggl reference survived.
 */
class OpenGLPortTest {

	/**
	 * All 167 entry points libjaggl declared as native. Every one of them has to survive the port:
	 * the claim "the whole surface moved, and only the bodies changed" is what this list checks.
	 */
	private static final String[] PORTED = {
			"glTexCoord2i", "glBindFramebufferEXT", "glEnd", "glBufferDataARBub", "glGetObjectParameterivARB", "glLineWidth",
			"glDisable", "glReadPixelsi", "glGenBuffersARB", "glFogi", "glDepthMask", "glRotatef",
			"glLoadMatrixf", "glGetProgramivARB", "glLoadIdentity", "glPixelStorei", "glTexCoordPointer", "glGetString",
			"glBufferSubDataARB", "glBufferSubDataARBub", "glTexEnvi", "glScalef", "glTexGenfv", "glActiveTexture",
			"glCopyPixels", "glVertexPointer", "glBindProgramARB", "glCopyTexSubImage3D", "glDrawElements", "glTexImage2Dub",
			"glAttachObjectARB", "glReadBuffer", "glTexImage2Df", "glDisableClientState", "glColorPointer", "glTexImage3Dub",
			"glTexSubImage2Dub", "glGenProgramARB", "glBindTexture", "glClear", "glDeleteLists", "glFogf",
			"glPolygonMode", "glScissor", "glProgramLocalParameter4fvARB", "glBindRenderbufferEXT", "glDetachObjectARB", "glCompileShaderARB",
			"glFramebufferTexture2DEXT", "glBlendFunc", "glPointSize", "glTexImage2Di", "glTexImage1Dub", "glDeleteObjectARB",
			"glGetFloatv", "glTexCoord3f", "glFinish", "glBegin", "glColor4f", "glLightModelfv",
			"glRenderbufferStorageEXT", "glTexGeni", "glLightf", "glShadeModel", "glUniform2fARB", "glMaterialfv",
			"glDrawBuffersARB", "glFramebufferTexture3DEXT", "glUniform1iARB", "glTexParameteri", "glProgramLocalParameter4fARB", "glGetInfoLogARB",
			"glClientActiveTexture", "glUniform3fARB", "glColor3ub", "glDeleteTextures", "glDeleteBuffersARB", "glDeleteRenderbuffersEXT",
			"glCreateProgramObjectARB", "glColor3f", "glPushAttrib", "glLightfv", "glTexSubImage2Df", "glFrustum",
			"glUniform1fARB", "glTexEnvfv", "glReadPixelsub", "glBlitFramebufferEXT", "glNormalPointer", "glColor4ub",
			"glGetIntegerv", "glGenRenderbuffersEXT", "glCullFace", "glOrtho", "glCopyTexSubImage2D", "glNewList",
			"glGetUniformLocationARB", "glDeleteFramebuffersEXT", "glCreateShaderObjectARB", "glVertex2f", "glAlphaFunc", "glGenLists",
			"glUniform4fARB", "glEndList", "glGenerateMipmapEXT", "glLinkProgramARB", "glRenderbufferStorageMultisampleEXT", "glClearColor",
			"glVertex3f", "glFogfv", "glGenFramebuffersEXT", "glDeleteProgramARB", "glVertex2i", "glShaderSourceARB",
			"glUseProgramObjectARB", "glHint", "glColorMaterial", "glCallList", "glDrawArrays", "glCopyTexImage2D",
			"glPixelZoom", "glPopAttrib", "glMultiTexCoord2f", "glPixelTransferf", "glTexCoord2f", "glTexParameterf",
			"glRasterPos2i", "glMultiTexCoord2i", "glFramebufferRenderbufferEXT", "glEnableClientState", "glEnable", "glClearDepth",
			"glTranslatef", "glBufferDataARBa", "glDrawPixelsi", "glNormal3f", "glTexSubImage2Di", "glGetError",
			"glBindBufferARB", "glMatrixMode", "glDrawBuffer", "glCheckFramebufferStatusEXT", "glColorMask", "glPushMatrix",
			"glDrawPixelsub", "glMultMatrixf", "glProgramStringARB", "glPopMatrix", "glTexEnvf", "glGenTextures",
			"glViewport", "glFlush", "glDepthFunc", "createPbuffer", "prepareSurface", "setSurface",
			"setPbuffer", "releaseSurface", "attachPeer", "arePbuffersAvailable", "detachPeer", "releasePbuffer",
			"setSwapInterval", "swapBuffers", "init", "surfaceResized", "release",
	};

	private static void assertNoNatives(Class<?> type) {
		Method[] methods = type.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			assertFalse(Modifier.isNative(methods[i].getModifiers()), type.getSimpleName() + "." + methods[i].getName() + " is still native");
		}
	}

	private static Method method(String name, Class<?>... parameters) throws Exception {
		Method method = OpenGL.class.getDeclaredMethod(name, parameters);
		assertNotNull(method, name);
		return method;
	}

	@Test
	void noJniIsLeftBehind() {
		assertNoNatives(OpenGL.class);
		assertNoNatives(GlContext.class);
		assertNoNatives(GlScratch.class);
	}

	@Test
	void everyPortedEntryPointIsStillDeclared() {
		Set<String> declared = new HashSet<String>();
		Method[] methods = OpenGL.class.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			declared.add(methods[i].getName());
		}
		for (int i = 0; i < PORTED.length; i++) {
			assertTrue(declared.contains(PORTED[i]), PORTED[i] + " is missing");
		}
	}

	@Test
	void theArrayAndOffsetEntryPointsKeepTheirDescriptors() throws Exception {
		method("glTexImage2Dub", int.class, int.class, int.class, int.class, int.class,
				int.class, int.class, int.class, byte[].class, int.class);
		method("glTexSubImage2Dub", int.class, int.class, int.class, int.class, int.class,
				int.class, int.class, int.class, byte[].class, int.class);
		method("glReadPixelsi", int.class, int.class, int.class, int.class, int.class,
				int.class, int[].class, int.class);
		method("glDrawPixelsi", int.class, int.class, int.class, int.class, int[].class, int.class);
		method("glBufferDataARBub", int.class, int.class, byte[].class, int.class, int.class);
		method("glBufferSubDataARB", int.class, int.class, int.class, byte[].class, int.class);
		method("glGenTextures", int.class, int[].class, int.class);
		method("glDeleteTextures", int.class, int[].class, int.class);
		method("glGetIntegerv", int.class, int[].class, int.class);
		method("glGetFloatv", int.class, float[].class, int.class);
		method("glMultMatrixf", float[].class, int.class);
	}

	@Test
	void thePointerAndHandleEntryPointsKeepTheirDescriptors() throws Exception {
		// A vertex buffer's data is a native address and a client array pointer is a long; a
		// GLhandleARB is a jlong, even though LWJGL stores it in an int.
		method("glBufferDataARBa", int.class, int.class, long.class, int.class);
		method("glDrawElements", int.class, int.class, int.class, long.class);
		method("glVertexPointer", int.class, int.class, int.class, long.class);
		method("glTexCoordPointer", int.class, int.class, int.class, long.class);
		method("glShaderSourceARB", long.class, String.class);
		method("glGetUniformLocationARB", long.class, String.class);
		method("glGetObjectParameterivARB", long.class, int.class, int[].class, int.class);
		method("glGetInfoLogARB", long.class, int.class, int[].class, int.class, byte[].class, int.class);
		method("glDeleteObjectARB", long.class);
		assertEquals(int.class, method("glGetUniformLocationARB", long.class, String.class).getReturnType());
		assertEquals(long.class, method("glCreateShaderObjectARB", int.class).getReturnType());
	}

	@Test
	void theContextLifecycleKeepsJagglsSignatures() throws Exception {
		Method init = method("init", Canvas.class, int.class, int.class, int.class, int.class, int.class, int.class);
		assertEquals(long.class, init.getReturnType());
		assertFalse(Modifier.isStatic(init.getModifiers()));
		assertEquals(long.class, method("prepareSurface", Canvas.class).getReturnType());
		assertEquals(boolean.class, method("setSurface", long.class).getReturnType());
		assertEquals(void.class, method("releaseSurface", Canvas.class, long.class).getReturnType());
		assertEquals(void.class, method("setSwapInterval", int.class).getReturnType());
		assertEquals(void.class, method("surfaceResized", long.class).getReturnType());
		assertEquals(boolean.class, method("arePbuffersAvailable").getReturnType());
		method("swapBuffers");
		method("release");
		method("attach");
		method("detach");
	}
}
