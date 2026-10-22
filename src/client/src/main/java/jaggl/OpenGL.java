package jaggl;

import java.awt.Canvas;
import java.util.Hashtable;
import org.lwjgl.opengl.ARBDrawBuffers;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.ARBVertexProgram;
import org.lwjgl.opengl.EXTFramebufferBlit;
import org.lwjgl.opengl.EXTFramebufferMultisample;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;

/**
 * Not part of the original client: libjaggl's JNI is replaced by LWJGL here. Every function
 * keeps the original's name and signature and does what libjaggl's did, through lwjgl-opengl;
 * the context and its surfaces are a {@link GlContext} (lwjgl3-awt) and the array-and-offset
 * calls copy through {@link GlScratch}. {@code peer} holds the GL context handle, as it held
 * libjaggl's.
 */
@OriginalClass("client!jaggl/OpenGL")
public final class OpenGL {

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "c", descriptor = "Ljava/util/Hashtable;")
	private static final Hashtable c = new Hashtable();

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "peer", descriptor = "J")
	private long peer;

	// Not part of the original client: the LWJGL context (see the class comment).
	private GlContext context;

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "a", descriptor = "Ljava/util/Hashtable;")
	private Hashtable a;

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "b", descriptor = "Ljava/lang/Thread;")
	private Thread b;

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexCoord2i", descriptor = "(II)V")
	private static void glTexCoord2i(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		GL11.glTexCoord2i(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glBindFramebufferEXT", descriptor = "(II)V")
	public static void glBindFramebufferEXT(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		EXTFramebufferObject.glBindFramebufferEXT(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glEnd", descriptor = "()V")
	public static void glEnd() {
		GL11.glEnd();
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glBufferDataARBub", descriptor = "(II[BII)V")
	public static void glBufferDataARBub(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) byte[] arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4) {
		ARBVertexBufferObject.nglBufferDataARB(arg0, arg1, GlScratch.bytes(arg2, arg3, arg1), arg4);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glGetObjectParameterivARB", descriptor = "(JI[II)V")
	public static void glGetObjectParameterivARB(@OriginalArg(0) long arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int[] arg2, @OriginalArg(3) int arg3) {
		arg2[arg3] = ARBShaderObjects.glGetObjectParameteriARB((int) arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glLineWidth", descriptor = "(F)V")
	public static void glLineWidth(@OriginalArg(0) float arg0) {
		GL11.glLineWidth(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glDisable", descriptor = "(I)V")
	public static void glDisable(@OriginalArg(0) int arg0) {
		GL11.glDisable(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glReadPixelsi", descriptor = "(IIIIII[II)V")
	public static void glReadPixelsi(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int[] arg6, @OriginalArg(7) int arg7) {
		GL11.nglReadPixels(arg0, arg1, arg2, arg3, arg4, arg5, GlScratch.space(arg2 * arg3 * 4));
		GlScratch.readInts(arg6, arg7, arg2 * arg3);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glGenBuffersARB", descriptor = "(I[II)V")
	public static void glGenBuffersARB(@OriginalArg(0) int arg0, @OriginalArg(1) int[] arg1, @OriginalArg(2) int arg2) {
		ARBVertexBufferObject.nglGenBuffersARB(arg0, GlScratch.ints(arg1, arg2, arg0));
		GlScratch.readInts(arg1, arg2, arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glFogi", descriptor = "(II)V")
	public static void glFogi(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		GL11.glFogi(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glDepthMask", descriptor = "(Z)V")
	public static void glDepthMask(@OriginalArg(0) boolean arg0) {
		GL11.glDepthMask(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glRotatef", descriptor = "(FFFF)V")
	public static void glRotatef(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2, @OriginalArg(3) float arg3) {
		GL11.glRotatef(arg0, arg1, arg2, arg3);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glLoadMatrixf", descriptor = "([FI)V")
	public static void glLoadMatrixf(@OriginalArg(0) float[] arg0, @OriginalArg(1) int arg1) {
		GL11.nglLoadMatrixf(GlScratch.floats(arg0, arg1, 16));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glGetProgramivARB", descriptor = "(II[II)V")
	private static void glGetProgramivARB(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int[] arg2, @OriginalArg(3) int arg3) {
		arg2[arg3] = ARBVertexProgram.glGetProgramiARB(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glLoadIdentity", descriptor = "()V")
	public static void glLoadIdentity() {
		GL11.glLoadIdentity();
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glPixelStorei", descriptor = "(II)V")
	public static void glPixelStorei(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		GL11.glPixelStorei(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexCoordPointer", descriptor = "(IIIJ)V")
	public static void glTexCoordPointer(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) long arg3) {
		GL11.nglTexCoordPointer(arg0, arg1, arg2, arg3);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glGetString", descriptor = "(I)Ljava/lang/String;")
	public static String glGetString(@OriginalArg(0) int arg0) {
		return GL11.glGetString(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glBufferSubDataARB", descriptor = "(III[BI)V")
	public static void glBufferSubDataARB(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) byte[] arg3, @OriginalArg(4) int arg4) {
		ARBVertexBufferObject.nglBufferSubDataARB(arg0, arg1, arg2, GlScratch.bytes(arg3, arg4, arg2));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glBufferSubDataARBub", descriptor = "(III[BI)V")
	public static void glBufferSubDataARBub(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) byte[] arg3, @OriginalArg(4) int arg4) {
		ARBVertexBufferObject.nglBufferSubDataARB(arg0, arg1, arg2, GlScratch.bytes(arg3, arg4, arg2));
	}

	/**
	 * Kept for the call sites written against libjaggl's two spellings of the byte[] buffer
	 * update: jaggl's glBufferSubDataARB and glBufferSubDataARBub now both map to the same
	 * LWJGL call, so there is no entry point left to resolve.
	 */
	public static void glBufferSubDataARBCompat(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) byte[] arg3, @OriginalArg(4) int arg4) {
		glBufferSubDataARB(arg0, arg1, arg2, arg3, arg4);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexEnvi", descriptor = "(III)V")
	public static void glTexEnvi(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		GL11.glTexEnvi(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glScalef", descriptor = "(FFF)V")
	public static void glScalef(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2) {
		GL11.glScalef(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexGenfv", descriptor = "(II[FI)V")
	public static void glTexGenfv(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) float[] arg2, @OriginalArg(3) int arg3) {
		GL11.nglTexGenfv(arg0, arg1, GlScratch.floats(arg2, arg3, 4));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glActiveTexture", descriptor = "(I)V")
	public static void glActiveTexture(@OriginalArg(0) int arg0) {
		GL13.glActiveTexture(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glCopyPixels", descriptor = "(IIIII)V")
	public static void glCopyPixels(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4) {
		GL11.glCopyPixels(arg0, arg1, arg2, arg3, arg4);
	}

	// Not part of the original client: GLhandleARB is a jlong in jaggl and an unsigned int in
	// LWJGL. Handles are only ever compared and handed back, so the zero extension round-trips.
	private static long handle(int handle) {
		return handle & 0xFFFFFFFFL;
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "getCurrent", descriptor = "()Lclient!jaggl/OpenGL;")
	private static OpenGL getCurrent() {
		return (OpenGL) c.get(Thread.currentThread());
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glVertexPointer", descriptor = "(IIIJ)V")
	public static void glVertexPointer(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) long arg3) {
		GL11.nglVertexPointer(arg0, arg1, arg2, arg3);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glBindProgramARB", descriptor = "(II)V")
	public static void glBindProgramARB(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		ARBVertexProgram.glBindProgramARB(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glCopyTexSubImage3D", descriptor = "(IIIIIIIII)V")
	public static void glCopyTexSubImage3D(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8) {
		GL12.glCopyTexSubImage3D(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glDrawElements", descriptor = "(IIIJ)V")
	public static void glDrawElements(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) long arg3) {
		GL11.nglDrawElements(arg0, arg1, arg2, arg3);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexImage2Dub", descriptor = "(IIIIIIII[BI)V")
	public static void glTexImage2Dub(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) byte[] arg8, @OriginalArg(9) int arg9) {
		GL11.nglTexImage2D(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, GlScratch.bytes(arg8, arg9));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glAttachObjectARB", descriptor = "(JJ)V")
	public static void glAttachObjectARB(@OriginalArg(0) long arg0, @OriginalArg(1) long arg1) {
		ARBShaderObjects.glAttachObjectARB((int) arg0, (int) arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glReadBuffer", descriptor = "(I)V")
	public static void glReadBuffer(@OriginalArg(0) int arg0) {
		GL11.glReadBuffer(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexImage2Df", descriptor = "(IIIIIIII[FI)V")
	public static void glTexImage2Df(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) float[] arg8, @OriginalArg(9) int arg9) {
		GL11.nglTexImage2D(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, GlScratch.floats(arg8, arg9));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glDisableClientState", descriptor = "(I)V")
	public static void glDisableClientState(@OriginalArg(0) int arg0) {
		GL11.glDisableClientState(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glColorPointer", descriptor = "(IIIJ)V")
	public static void glColorPointer(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) long arg3) {
		GL11.nglColorPointer(arg0, arg1, arg2, arg3);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexImage3Dub", descriptor = "(IIIIIIIII[BI)V")
	public static void glTexImage3Dub(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8, @OriginalArg(9) byte[] arg9, @OriginalArg(10) int arg10) {
		GL12.nglTexImage3D(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, GlScratch.bytes(arg9, arg10));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexSubImage2Dub", descriptor = "(IIIIIIII[BI)V")
	public static void glTexSubImage2Dub(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) byte[] arg8, @OriginalArg(9) int arg9) {
		GL11.nglTexSubImage2D(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, GlScratch.bytes(arg8, arg9));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glGenProgramARB", descriptor = "()I")
	public static int glGenProgramARB() {
		return ARBVertexProgram.glGenProgramsARB();
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glBindTexture", descriptor = "(II)V")
	public static void glBindTexture(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		GL11.glBindTexture(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glClear", descriptor = "(I)V")
	public static void glClear(@OriginalArg(0) int arg0) {
		GL11.glClear(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glDeleteLists", descriptor = "(II)V")
	public static void glDeleteLists(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		GL11.glDeleteLists(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glFogf", descriptor = "(IF)V")
	public static void glFogf(@OriginalArg(0) int arg0, @OriginalArg(1) float arg1) {
		GL11.glFogf(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glPolygonMode", descriptor = "(II)V")
	public static void glPolygonMode(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		GL11.glPolygonMode(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glScissor", descriptor = "(IIII)V")
	public static void glScissor(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		GL11.glScissor(arg0, arg1, arg2, arg3);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glProgramLocalParameter4fvARB", descriptor = "(II[FI)V")
	public static void glProgramLocalParameter4fvARB(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) float[] arg2, @OriginalArg(3) int arg3) {
		ARBVertexProgram.nglProgramLocalParameter4fvARB(arg0, arg1, GlScratch.floats(arg2, arg3, 4));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glBindRenderbufferEXT", descriptor = "(II)V")
	public static void glBindRenderbufferEXT(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		EXTFramebufferObject.glBindRenderbufferEXT(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glDetachObjectARB", descriptor = "(JJ)V")
	public static void glDetachObjectARB(@OriginalArg(0) long arg0, @OriginalArg(1) long arg1) {
		ARBShaderObjects.glDetachObjectARB((int) arg0, (int) arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glCompileShaderARB", descriptor = "(J)V")
	public static void glCompileShaderARB(@OriginalArg(0) long arg0) {
		ARBShaderObjects.glCompileShaderARB((int) arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glFramebufferTexture2DEXT", descriptor = "(IIIII)V")
	public static void glFramebufferTexture2DEXT(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4) {
		EXTFramebufferObject.glFramebufferTexture2DEXT(arg0, arg1, arg2, arg3, arg4);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glBlendFunc", descriptor = "(II)V")
	public static void glBlendFunc(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		GL11.glBlendFunc(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glPointSize", descriptor = "(F)V")
	private static void glPointSize(@OriginalArg(0) float arg0) {
		GL11.glPointSize(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexImage2Di", descriptor = "(IIIIIIII[II)V")
	public static void glTexImage2Di(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int[] arg8, @OriginalArg(9) int arg9) {
		GL11.nglTexImage2D(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, GlScratch.ints(arg8, arg9));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexImage1Dub", descriptor = "(IIIIIII[BI)V")
	public static void glTexImage1Dub(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) byte[] arg7, @OriginalArg(8) int arg8) {
		GL11.nglTexImage1D(arg0, arg1, arg2, arg3, arg4, arg5, arg6, GlScratch.bytes(arg7, arg8));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glDeleteObjectARB", descriptor = "(J)V")
	public static void glDeleteObjectARB(@OriginalArg(0) long arg0) {
		ARBShaderObjects.glDeleteObjectARB((int) arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glGetFloatv", descriptor = "(I[FI)V")
	public static void glGetFloatv(@OriginalArg(0) int arg0, @OriginalArg(1) float[] arg1, @OriginalArg(2) int arg2) {
		GL11.nglGetFloatv(arg0, GlScratch.space(64));
		GlScratch.readFloats(arg1, arg2, 16);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexCoord3f", descriptor = "(FFF)V")
	public static void glTexCoord3f(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2) {
		GL11.glTexCoord3f(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glFinish", descriptor = "()V")
	private static void glFinish() {
		GL11.glFinish();
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glBegin", descriptor = "(I)V")
	public static void glBegin(@OriginalArg(0) int arg0) {
		GL11.glBegin(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glColor4f", descriptor = "(FFFF)V")
	private static void glColor4f(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2, @OriginalArg(3) float arg3) {
		GL11.glColor4f(arg0, arg1, arg2, arg3);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glLightModelfv", descriptor = "(I[FI)V")
	public static void glLightModelfv(@OriginalArg(0) int arg0, @OriginalArg(1) float[] arg1, @OriginalArg(2) int arg2) {
		GL11.nglLightModelfv(arg0, GlScratch.floats(arg1, arg2, 4));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glRenderbufferStorageEXT", descriptor = "(IIII)V")
	public static void glRenderbufferStorageEXT(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		EXTFramebufferObject.glRenderbufferStorageEXT(arg0, arg1, arg2, arg3);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexGeni", descriptor = "(III)V")
	public static void glTexGeni(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		GL11.glTexGeni(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glLightf", descriptor = "(IIF)V")
	public static void glLightf(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) float arg2) {
		GL11.glLightf(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glShadeModel", descriptor = "(I)V")
	public static void glShadeModel(@OriginalArg(0) int arg0) {
		GL11.glShadeModel(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glUniform2fARB", descriptor = "(IFF)V")
	public static void glUniform2fARB(@OriginalArg(0) int arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2) {
		ARBShaderObjects.glUniform2fARB(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glMaterialfv", descriptor = "(II[FI)V")
	private static void glMaterialfv(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) float[] arg2, @OriginalArg(3) int arg3) {
		GL11.nglMaterialfv(arg0, arg1, GlScratch.floats(arg2, arg3, 4));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glDrawBuffersARB", descriptor = "(I[II)V")
	private static void glDrawBuffersARB(@OriginalArg(0) int arg0, @OriginalArg(1) int[] arg1, @OriginalArg(2) int arg2) {
		ARBDrawBuffers.nglDrawBuffersARB(arg0, GlScratch.ints(arg1, arg2, arg0));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glFramebufferTexture3DEXT", descriptor = "(IIIIII)V")
	public static void glFramebufferTexture3DEXT(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5) {
		EXTFramebufferObject.glFramebufferTexture3DEXT(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glUniform1iARB", descriptor = "(II)V")
	public static void glUniform1iARB(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		ARBShaderObjects.glUniform1iARB(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexParameteri", descriptor = "(III)V")
	public static void glTexParameteri(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		GL11.glTexParameteri(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glProgramLocalParameter4fARB", descriptor = "(IIFFFF)V")
	public static void glProgramLocalParameter4fARB(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) float arg2, @OriginalArg(3) float arg3, @OriginalArg(4) float arg4, @OriginalArg(5) float arg5) {
		ARBVertexProgram.glProgramLocalParameter4fARB(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glGetInfoLogARB", descriptor = "(JI[II[BI)V")
	public static void glGetInfoLogARB(@OriginalArg(0) long arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int[] arg2, @OriginalArg(3) int arg3, @OriginalArg(4) byte[] arg4, @OriginalArg(5) int arg5) {
		if (arg1 <= 0) {
			arg2[arg3] = 0;
			return;
		}
		byte[] log = ARBShaderObjects.glGetInfoLogARB((int) arg0).getBytes(java.nio.charset.StandardCharsets.ISO_8859_1);
		int limit = Math.max(0, Math.min(arg1 - 1, arg4.length - arg5));
		int copied = Math.min(log.length, limit);
		System.arraycopy(log, 0, arg4, arg5, copied);
		if (arg5 + copied < arg4.length) {
			arg4[arg5 + copied] = 0;
		}
		arg2[arg3] = copied + 1;
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glClientActiveTexture", descriptor = "(I)V")
	private static void glClientActiveTexture(@OriginalArg(0) int arg0) {
		GL13.glClientActiveTexture(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glUniform3fARB", descriptor = "(IFFF)V")
	public static void glUniform3fARB(@OriginalArg(0) int arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2, @OriginalArg(3) float arg3) {
		ARBShaderObjects.glUniform3fARB(arg0, arg1, arg2, arg3);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glColor3ub", descriptor = "(BBB)V")
	private static void glColor3ub(@OriginalArg(0) byte arg0, @OriginalArg(1) byte arg1, @OriginalArg(2) byte arg2) {
		GL11.glColor3ub(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glDeleteTextures", descriptor = "(I[II)V")
	public static void glDeleteTextures(@OriginalArg(0) int arg0, @OriginalArg(1) int[] arg1, @OriginalArg(2) int arg2) {
		GL11.nglDeleteTextures(arg0, GlScratch.ints(arg1, arg2, arg0));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glDeleteBuffersARB", descriptor = "(I[II)V")
	public static void glDeleteBuffersARB(@OriginalArg(0) int arg0, @OriginalArg(1) int[] arg1, @OriginalArg(2) int arg2) {
		ARBVertexBufferObject.nglDeleteBuffersARB(arg0, GlScratch.ints(arg1, arg2, arg0));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glDeleteRenderbuffersEXT", descriptor = "(I[II)V")
	public static void glDeleteRenderbuffersEXT(@OriginalArg(0) int arg0, @OriginalArg(1) int[] arg1, @OriginalArg(2) int arg2) {
		EXTFramebufferObject.nglDeleteRenderbuffersEXT(arg0, GlScratch.ints(arg1, arg2, arg0));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glCreateProgramObjectARB", descriptor = "()J")
	public static long glCreateProgramObjectARB() {
		return handle(ARBShaderObjects.glCreateProgramObjectARB());
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glColor3f", descriptor = "(FFF)V")
	public static void glColor3f(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2) {
		GL11.glColor3f(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glPushAttrib", descriptor = "(I)V")
	public static void glPushAttrib(@OriginalArg(0) int arg0) {
		GL11.glPushAttrib(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glLightfv", descriptor = "(II[FI)V")
	public static void glLightfv(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) float[] arg2, @OriginalArg(3) int arg3) {
		GL11.nglLightfv(arg0, arg1, GlScratch.floats(arg2, arg3, 4));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexSubImage2Df", descriptor = "(IIIIIIII[FI)V")
	private static void glTexSubImage2Df(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) float[] arg8, @OriginalArg(9) int arg9) {
		GL11.nglTexSubImage2D(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, GlScratch.floats(arg8, arg9));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glFrustum", descriptor = "(DDDDDD)V")
	private static void glFrustum(@OriginalArg(0) double arg0, @OriginalArg(1) double arg1, @OriginalArg(2) double arg2, @OriginalArg(3) double arg3, @OriginalArg(4) double arg4, @OriginalArg(5) double arg5) {
		GL11.glFrustum(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glUniform1fARB", descriptor = "(IF)V")
	public static void glUniform1fARB(@OriginalArg(0) int arg0, @OriginalArg(1) float arg1) {
		ARBShaderObjects.glUniform1fARB(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexEnvfv", descriptor = "(II[FI)V")
	public static void glTexEnvfv(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) float[] arg2, @OriginalArg(3) int arg3) {
		GL11.nglTexEnvfv(arg0, arg1, GlScratch.floats(arg2, arg3, 4));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glReadPixelsub", descriptor = "(IIIIII[BI)V")
	private static void glReadPixelsub(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) byte[] arg6, @OriginalArg(7) int arg7) {
		GL11.nglReadPixels(arg0, arg1, arg2, arg3, arg4, arg5, GlScratch.space(arg2 * arg3 * 4));
		GlScratch.readBytes(arg6, arg7, arg2 * arg3 * 4);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glBlitFramebufferEXT", descriptor = "(IIIIIIIIII)V")
	public static void glBlitFramebufferEXT(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8, @OriginalArg(9) int arg9) {
		EXTFramebufferBlit.glBlitFramebufferEXT(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glNormalPointer", descriptor = "(IIJ)V")
	public static void glNormalPointer(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) long arg2) {
		GL11.nglNormalPointer(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glColor4ub", descriptor = "(BBBB)V")
	public static void glColor4ub(@OriginalArg(0) byte arg0, @OriginalArg(1) byte arg1, @OriginalArg(2) byte arg2, @OriginalArg(3) byte arg3) {
		GL11.glColor4ub(arg0, arg1, arg2, arg3);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glGetIntegerv", descriptor = "(I[II)V")
	public static void glGetIntegerv(@OriginalArg(0) int arg0, @OriginalArg(1) int[] arg1, @OriginalArg(2) int arg2) {
		GL11.nglGetIntegerv(arg0, GlScratch.space(64));
		GlScratch.readInts(arg1, arg2, 16);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glGenRenderbuffersEXT", descriptor = "(I[II)V")
	public static void glGenRenderbuffersEXT(@OriginalArg(0) int arg0, @OriginalArg(1) int[] arg1, @OriginalArg(2) int arg2) {
		EXTFramebufferObject.nglGenRenderbuffersEXT(arg0, GlScratch.ints(arg1, arg2, arg0));
		GlScratch.readInts(arg1, arg2, arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glCullFace", descriptor = "(I)V")
	public static void glCullFace(@OriginalArg(0) int arg0) {
		GL11.glCullFace(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glOrtho", descriptor = "(DDDDDD)V")
	public static void glOrtho(@OriginalArg(0) double arg0, @OriginalArg(1) double arg1, @OriginalArg(2) double arg2, @OriginalArg(3) double arg3, @OriginalArg(4) double arg4, @OriginalArg(5) double arg5) {
		GL11.glOrtho(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glCopyTexSubImage2D", descriptor = "(IIIIIIII)V")
	public static void glCopyTexSubImage2D(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7) {
		GL11.glCopyTexSubImage2D(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glNewList", descriptor = "(II)V")
	public static void glNewList(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		GL11.glNewList(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glGetUniformLocationARB", descriptor = "(JLjava/lang/String;)I")
	public static int glGetUniformLocationARB(@OriginalArg(0) long arg0, @OriginalArg(1) String arg1) {
		return ARBShaderObjects.glGetUniformLocationARB((int) arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glDeleteFramebuffersEXT", descriptor = "(I[II)V")
	public static void glDeleteFramebuffersEXT(@OriginalArg(0) int arg0, @OriginalArg(1) int[] arg1, @OriginalArg(2) int arg2) {
		EXTFramebufferObject.nglDeleteFramebuffersEXT(arg0, GlScratch.ints(arg1, arg2, arg0));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glCreateShaderObjectARB", descriptor = "(I)J")
	public static long glCreateShaderObjectARB(@OriginalArg(0) int arg0) {
		return handle(ARBShaderObjects.glCreateShaderObjectARB(arg0));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glVertex2f", descriptor = "(FF)V")
	public static void glVertex2f(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1) {
		GL11.glVertex2f(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glAlphaFunc", descriptor = "(IF)V")
	public static void glAlphaFunc(@OriginalArg(0) int arg0, @OriginalArg(1) float arg1) {
		GL11.glAlphaFunc(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glGenLists", descriptor = "(I)I")
	public static int glGenLists(@OriginalArg(0) int arg0) {
		return GL11.glGenLists(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glUniform4fARB", descriptor = "(IFFFF)V")
	public static void glUniform4fARB(@OriginalArg(0) int arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2, @OriginalArg(3) float arg3, @OriginalArg(4) float arg4) {
		ARBShaderObjects.glUniform4fARB(arg0, arg1, arg2, arg3, arg4);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glEndList", descriptor = "()V")
	public static void glEndList() {
		GL11.glEndList();
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glGenerateMipmapEXT", descriptor = "(I)V")
	public static void glGenerateMipmapEXT(@OriginalArg(0) int arg0) {
		EXTFramebufferObject.glGenerateMipmapEXT(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glLinkProgramARB", descriptor = "(J)V")
	public static void glLinkProgramARB(@OriginalArg(0) long arg0) {
		ARBShaderObjects.glLinkProgramARB((int) arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glRenderbufferStorageMultisampleEXT", descriptor = "(IIIII)V")
	public static void glRenderbufferStorageMultisampleEXT(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4) {
		EXTFramebufferMultisample.glRenderbufferStorageMultisampleEXT(arg0, arg1, arg2, arg3, arg4);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glClearColor", descriptor = "(FFFF)V")
	public static void glClearColor(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2, @OriginalArg(3) float arg3) {
		GL11.glClearColor(arg0, arg1, arg2, arg3);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glVertex3f", descriptor = "(FFF)V")
	private static void glVertex3f(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2) {
		GL11.glVertex3f(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glFogfv", descriptor = "(I[FI)V")
	public static void glFogfv(@OriginalArg(0) int arg0, @OriginalArg(1) float[] arg1, @OriginalArg(2) int arg2) {
		GL11.nglFogfv(arg0, GlScratch.floats(arg1, arg2, 4));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glGenFramebuffersEXT", descriptor = "(I[II)V")
	public static void glGenFramebuffersEXT(@OriginalArg(0) int arg0, @OriginalArg(1) int[] arg1, @OriginalArg(2) int arg2) {
		EXTFramebufferObject.nglGenFramebuffersEXT(arg0, GlScratch.ints(arg1, arg2, arg0));
		GlScratch.readInts(arg1, arg2, arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glDeleteProgramARB", descriptor = "(I)V")
	public static void glDeleteProgramARB(@OriginalArg(0) int arg0) {
		ARBVertexProgram.nglDeleteProgramsARB(1, GlScratch.ints(new int[] { arg0 }, 0, 1));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glVertex2i", descriptor = "(II)V")
	public static void glVertex2i(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		GL11.glVertex2i(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glShaderSourceARB", descriptor = "(JLjava/lang/String;)V")
	public static void glShaderSourceARB(@OriginalArg(0) long arg0, @OriginalArg(1) String arg1) {
		ARBShaderObjects.glShaderSourceARB((int) arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glUseProgramObjectARB", descriptor = "(J)V")
	public static void glUseProgramObjectARB(@OriginalArg(0) long arg0) {
		ARBShaderObjects.glUseProgramObjectARB((int) arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glHint", descriptor = "(II)V")
	public static void glHint(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		GL11.glHint(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glColorMaterial", descriptor = "(II)V")
	public static void glColorMaterial(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		GL11.glColorMaterial(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glCallList", descriptor = "(I)V")
	public static void glCallList(@OriginalArg(0) int arg0) {
		GL11.glCallList(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glDrawArrays", descriptor = "(III)V")
	public static void glDrawArrays(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		GL11.glDrawArrays(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glCopyTexImage2D", descriptor = "(IIIIIIII)V")
	public static void glCopyTexImage2D(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7) {
		GL11.glCopyTexImage2D(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glPixelZoom", descriptor = "(FF)V")
	private static void glPixelZoom(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1) {
		GL11.glPixelZoom(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glPopAttrib", descriptor = "()V")
	public static void glPopAttrib() {
		GL11.glPopAttrib();
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glMultiTexCoord2f", descriptor = "(IFF)V")
	public static void glMultiTexCoord2f(@OriginalArg(0) int arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2) {
		GL13.glMultiTexCoord2f(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glPixelTransferf", descriptor = "(IF)V")
	public static void glPixelTransferf(@OriginalArg(0) int arg0, @OriginalArg(1) float arg1) {
		GL11.glPixelTransferf(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexCoord2f", descriptor = "(FF)V")
	public static void glTexCoord2f(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1) {
		GL11.glTexCoord2f(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexParameterf", descriptor = "(IIF)V")
	private static void glTexParameterf(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) float arg2) {
		GL11.glTexParameterf(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glRasterPos2i", descriptor = "(II)V")
	public static void glRasterPos2i(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		GL11.glRasterPos2i(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glMultiTexCoord2i", descriptor = "(III)V")
	private static void glMultiTexCoord2i(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		GL13.glMultiTexCoord2i(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glFramebufferRenderbufferEXT", descriptor = "(IIII)V")
	public static void glFramebufferRenderbufferEXT(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		EXTFramebufferObject.glFramebufferRenderbufferEXT(arg0, arg1, arg2, arg3);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glEnableClientState", descriptor = "(I)V")
	public static void glEnableClientState(@OriginalArg(0) int arg0) {
		GL11.glEnableClientState(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glEnable", descriptor = "(I)V")
	public static void glEnable(@OriginalArg(0) int arg0) {
		GL11.glEnable(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glClearDepth", descriptor = "(F)V")
	public static void glClearDepth(@OriginalArg(0) float arg0) {
		GL11.glClearDepth(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTranslatef", descriptor = "(FFF)V")
	public static void glTranslatef(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2) {
		GL11.glTranslatef(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glBufferDataARBa", descriptor = "(IIJI)V")
	public static void glBufferDataARBa(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) long arg2, @OriginalArg(3) int arg3) {
		ARBVertexBufferObject.nglBufferDataARB(arg0, arg1, arg2, arg3);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glDrawPixelsi", descriptor = "(IIII[II)V")
	private static void glDrawPixelsi(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int[] arg4, @OriginalArg(5) int arg5) {
		GL11.nglDrawPixels(arg0, arg1, arg2, arg3, GlScratch.ints(arg4, arg5));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glNormal3f", descriptor = "(FFF)V")
	public static void glNormal3f(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2) {
		GL11.glNormal3f(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexSubImage2Di", descriptor = "(IIIIIIII[II)V")
	public static void glTexSubImage2Di(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int[] arg8, @OriginalArg(9) int arg9) {
		GL11.nglTexSubImage2D(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, GlScratch.ints(arg8, arg9));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glGetError", descriptor = "()I")
	private static int glGetError() {
		return GL11.glGetError();
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glBindBufferARB", descriptor = "(II)V")
	public static void glBindBufferARB(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		ARBVertexBufferObject.glBindBufferARB(arg0, arg1);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glMatrixMode", descriptor = "(I)V")
	public static void glMatrixMode(@OriginalArg(0) int arg0) {
		GL11.glMatrixMode(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glDrawBuffer", descriptor = "(I)V")
	public static void glDrawBuffer(@OriginalArg(0) int arg0) {
		GL11.glDrawBuffer(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glCheckFramebufferStatusEXT", descriptor = "(I)I")
	public static int glCheckFramebufferStatusEXT(@OriginalArg(0) int arg0) {
		return EXTFramebufferObject.glCheckFramebufferStatusEXT(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glColorMask", descriptor = "(ZZZZ)V")
	public static void glColorMask(@OriginalArg(0) boolean arg0, @OriginalArg(1) boolean arg1, @OriginalArg(2) boolean arg2, @OriginalArg(3) boolean arg3) {
		GL11.glColorMask(arg0, arg1, arg2, arg3);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glPushMatrix", descriptor = "()V")
	public static void glPushMatrix() {
		GL11.glPushMatrix();
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glDrawPixelsub", descriptor = "(IIII[BI)V")
	private static void glDrawPixelsub(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) byte[] arg4, @OriginalArg(5) int arg5) {
		GL11.nglDrawPixels(arg0, arg1, arg2, arg3, GlScratch.bytes(arg4, arg5));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glMultMatrixf", descriptor = "([FI)V")
	public static void glMultMatrixf(@OriginalArg(0) float[] arg0, @OriginalArg(1) int arg1) {
		GL11.nglMultMatrixf(GlScratch.floats(arg0, arg1, 16));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glProgramStringARB", descriptor = "(IILjava/lang/String;)V")
	public static void glProgramStringARB(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) String arg2) {
		byte[] program = arg2.getBytes(java.nio.charset.StandardCharsets.ISO_8859_1);
		ARBVertexProgram.nglProgramStringARB(arg0, arg1, program.length, GlScratch.bytes(program, 0));
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glPopMatrix", descriptor = "()V")
	public static void glPopMatrix() {
		GL11.glPopMatrix();
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glTexEnvf", descriptor = "(IIF)V")
	public static void glTexEnvf(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) float arg2) {
		GL11.glTexEnvf(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glGenTextures", descriptor = "(I[II)V")
	public static void glGenTextures(@OriginalArg(0) int arg0, @OriginalArg(1) int[] arg1, @OriginalArg(2) int arg2) {
		GL11.nglGenTextures(arg0, GlScratch.ints(arg1, arg2, arg0));
		GlScratch.readInts(arg1, arg2, arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glViewport", descriptor = "(IIII)V")
	public static void glViewport(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		GL11.glViewport(arg0, arg1, arg2, arg3);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glFlush", descriptor = "()V")
	public static void glFlush() {
		GL11.glFlush();
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "glDepthFunc", descriptor = "(I)V")
	public static void glDepthFunc(@OriginalArg(0) int arg0) {
		GL11.glDepthFunc(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "createPbuffer", descriptor = "(II)J")
	private long createPbuffer(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		return 0L;
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "prepareSurface", descriptor = "(Ljava/awt/Canvas;)J")
	public long prepareSurface(@OriginalArg(0) Canvas arg0) {
		return this.context == null ? -1L : this.context.prepareSurface(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "setSurface", descriptor = "(J)Z")
	public boolean setSurface(@OriginalArg(0) long arg0) {
		return this.context != null && this.context.setSurface(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "setPbuffer", descriptor = "(J)V")
	private void setPbuffer(@OriginalArg(0) long arg0) {
		throw new UnsupportedOperationException("no pbuffers");
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "isExtensionAvailable", descriptor = "(Ljava/lang/String;)Z")
	public boolean isExtensionAvailable(@OriginalArg(0) String arg0) {
		if (this.a == null) {
			this.a = new Hashtable();
			@Pc(10) String local10 = glGetString(7939);
			@Pc(12) int local12 = 0;
			while (true) {
				@Pc(17) int local17 = local10.indexOf(32, local12);
				if (local17 == -1) {
					@Pc(48) String local48 = local10.substring(local12).trim();
					if (local48.length() != 0) {
						this.a.put(local48, local48);
					}
					break;
				}
				@Pc(26) String local26 = local10.substring(local12, local17).trim();
				if (local26.length() != 0) {
					this.a.put(local26, local26);
				}
				local12 = local17 + 1;
			}
		}
		return this.a.containsKey(arg0);
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "releaseSurface", descriptor = "(Ljava/awt/Canvas;J)V")
	public void releaseSurface(@OriginalArg(0) Canvas arg0, @OriginalArg(1) long arg1) {
		if (this.context != null) {
			this.context.releaseSurface(arg1);
		}
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "attachPeer", descriptor = "()Z")
	private boolean attachPeer() {
		return this.context != null && this.context.attach();
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "arePbuffersAvailable", descriptor = "()Z")
	public boolean arePbuffersAvailable() {
		return false;
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "detach", descriptor = "()Z")
	public synchronized boolean detach() {
		if (this.b == Thread.currentThread()) {
			this.detachPeer();
			c.remove(this.b);
			this.b = null;
			return true;
		} else {
			return false;
		}
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "detachPeer", descriptor = "()V")
	private void detachPeer() {
		if (this.context != null) {
			this.context.detach();
		}
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "releasePbuffer", descriptor = "(J)V")
	private void releasePbuffer(@OriginalArg(0) long arg0) {
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "setSwapInterval", descriptor = "(I)V")
	public void setSwapInterval(@OriginalArg(0) int arg0) {
		if (this.context != null) {
			this.context.setSwapInterval(arg0);
		}
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "swapBuffers", descriptor = "()V")
	public void swapBuffers() {
		if (this.context != null) {
			this.context.swapBuffers();
		}
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "attach", descriptor = "()Z")
	public synchronized boolean attach() {
		@Pc(1) Thread local1 = Thread.currentThread();
		if (!this.attachPeer()) {
			return false;
		}
		@Pc(10) OpenGL local10 = (OpenGL) c.put(local1, this);
		if (local10 != null) {
			local10.b = null;
		}
		this.b = local1;
		return true;
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "init", descriptor = "(Ljava/awt/Canvas;IIIIII)J")
	public long init(@OriginalArg(0) Canvas arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6) {
		if (this.context != null) {
			throw new IllegalStateException("already initialised");
		}
		try {
			GlContext created = GlContext.create(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
			this.context = created;
			this.peer = created.context();
			return created.mainHandle();
		} catch (Exception exception) {
			// The toolkit factory falls back to the software renderer on a RuntimeException, as it
			// did when libjaggl failed to load.
			throw new RuntimeException("OpenGL context: " + exception, exception);
		} catch (LinkageError error) {
			// LWJGL's natives missing for this platform, or a class failing to initialise.
			throw new RuntimeException("OpenGL context: " + error, error);
		} catch (java.awt.AWTError error) {
			throw new RuntimeException("OpenGL context: " + error, error);
		}
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "surfaceResized", descriptor = "(J)V")
	public void surfaceResized(@OriginalArg(0) long arg0) {
	}

	@OriginalMember(owner = "client!jaggl/OpenGL", name = "release", descriptor = "()V")
	public void release() {
		GlContext released = this.context;
		this.context = null;
		this.peer = 0L;
		if (released != null) {
			released.release();
		}
	}
}
