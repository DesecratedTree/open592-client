package com.jagex.game.runetek5.graphics.gl.lwjgl;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

/**
 * Production {@link LwjglFrameOps}: real LWJGL framebuffer and
 * immediate-mode calls. Every method requires the calling thread's GL
 * context to be current.
 *
 * <p>Framebuffer objects use core {@code GL30} entry points: the legacy
 * code went through {@code EXT} variants because it targeted GL 2.x-era
 * contexts, while this renderer always creates a modern context.
 */
public final class LwjglFrameGl implements LwjglFrameOps {

    /** Texture coordinate S for texgen (legacy literal 8192). */
    public static final int COORDINATE_S = 0x2000;

    /** Eye-plane texgen mode (legacy literal 9474). */
    public static final int PLANE_EYE = 0x2502;

    private static final LwjglFrameGl INSTANCE = new LwjglFrameGl();

    private LwjglFrameGl() {
    }

    /** @return the shared production implementation. */
    public static LwjglFrameGl get() {
        return INSTANCE;
    }

    @Override
    public boolean supportsFramebufferObjects() {
        return true;
    }

    @Override
    public int genFramebuffer() {
        return GL30.glGenFramebuffers();
    }

    @Override
    public void deleteFramebuffer(int id) {
        GL30.glDeleteFramebuffers(id);
    }

    @Override
    public void bindFramebuffer(int target, int id) {
        GL30.glBindFramebuffer(target, id);
    }

    @Override
    public int checkFramebufferStatus(int target) {
        return GL30.glCheckFramebufferStatus(target);
    }

    @Override
    public void drawBuffer(int attachment) {
        GL11.glDrawBuffer(attachment);
    }

    @Override
    public void readBuffer(int attachment) {
        GL11.glReadBuffer(attachment);
    }

    @Override
    public void blitFramebuffer(int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter) {
        GL30.glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
    }

    @Override
    public int genRenderbuffer() {
        return GL30.glGenRenderbuffers();
    }

    @Override
    public void deleteRenderbuffer(int id) {
        GL30.glDeleteRenderbuffers(id);
    }

    @Override
    public void bindRenderbuffer(int id) {
        GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, id);
    }

    @Override
    public void renderbufferStorage(int format, int width, int height) {
        GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, format, width, height);
    }

    @Override
    public void renderbufferStorageMultisample(int samples, int format, int width, int height) {
        GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, samples, format, width, height);
    }

    @Override
    public void framebufferRenderbuffer(int target, int attachment, int renderbuffer) {
        GL30.glFramebufferRenderbuffer(target, attachment, GL30.GL_RENDERBUFFER, renderbuffer);
    }

    @Override
    public void viewport(int x, int y, int width, int height) {
        GL11.glViewport(x, y, width, height);
    }

    @Override
    public void pushAttrib(int mask) {
        GL11.glPushAttrib(mask);
    }

    @Override
    public void popAttrib() {
        GL11.glPopAttrib();
    }

    @Override
    public void matrixMode(int mode) {
        GL11.glMatrixMode(mode);
    }

    @Override
    public void pushMatrix() {
        GL11.glPushMatrix();
    }

    @Override
    public void popMatrix() {
        GL11.glPopMatrix();
    }

    @Override
    public void loadIdentity() {
        GL11.glLoadIdentity();
    }

    @Override
    public void ortho(double left, double right, double bottom, double top, double near, double far) {
        GL11.glOrtho(left, right, bottom, top, near, far);
    }

    @Override
    public void beginQuads() {
        GL11.glBegin(GL11.GL_QUADS);
    }

    @Override
    public void end() {
        GL11.glEnd();
    }

    @Override
    public void texCoord2f(float s, float t) {
        GL11.glTexCoord2f(s, t);
    }

    @Override
    public void multiTexCoord2f(int unit, float s, float t) {
        GL13.glMultiTexCoord2f(unit, s, t);
    }

    @Override
    public void vertex2i(int x, int y) {
        GL11.glVertex2i(x, y);
    }

    @Override
    public void vertex2f(float x, float y) {
        GL11.glVertex2f(x, y);
    }

    @Override
    public void enableCap(int cap) {
        GL11.glEnable(cap);
    }

    @Override
    public void disableCap(int cap) {
        GL11.glDisable(cap);
    }

    @Override
    public void color4f(float r, float g, float b, float a) {
        GL11.glColor4f(r, g, b, a);
    }

    @Override
    public void translatef(float x, float y, float z) {
        GL11.glTranslatef(x, y, z);
    }

    @Override
    public void blendFunc(int src, int dst) {
        GL11.glBlendFunc(src, dst);
    }

    @Override
    public void multMatrixf(float[] matrix) {
        FloatBuffer direct = MemoryUtil.memAllocFloat(16);
        try {
            direct.put(matrix, 0, 16);
            direct.flip();
            GL11.glMultMatrixf(direct);
        } finally {
            MemoryUtil.memFree(direct);
        }
    }

    @Override
    public void texGen(int coordinate, int pname, float[] params) {
        FloatBuffer direct = MemoryUtil.memAllocFloat(params.length);
        try {
            direct.put(params);
            direct.flip();
            GL11.glTexGenfv(coordinate, pname, direct);
        } finally {
            MemoryUtil.memFree(direct);
        }
    }
}
