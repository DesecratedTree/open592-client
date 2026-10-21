package com.jagex.game.runetek5.graphics.gl.lwjgl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexProgram;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

/**
 * Production {@link LwjglTextureOps}: real LWJGL texture and program calls.
 * Every method requires the calling thread's GL context to be current.
 *
 * <p>Framebuffer attachments and mipmap generation use core {@code GL30}
 * entry points: the legacy code went through {@code EXT} variants because
 * it targeted GL 2.x-era contexts, while this renderer always creates a
 * modern context. Program deletion goes through
 * {@code ARBShaderObjects#glDeleteObjectARB} exactly like the legacy tick,
 * which frees GLSL and ARB program objects alike.
 */
public final class LwjglTextureGl implements LwjglTextureOps {

    private static final LwjglTextureGl INSTANCE = new LwjglTextureGl();

    private LwjglTextureGl() {
    }

    /** @return the shared production implementation. */
    public static LwjglTextureGl get() {
        return INSTANCE;
    }

    @Override
    public int genTexture() {
        return GL11.glGenTextures();
    }

    @Override
    public void deleteTexture(int id) {
        GL11.glDeleteTextures(id);
    }

    @Override
    public void bindTexture(int target, int id) {
        GL11.glBindTexture(target, id);
    }

    @Override
    public void enableTarget(int target) {
        GL11.glEnable(target);
    }

    @Override
    public void disableTarget(int target) {
        GL11.glDisable(target);
    }

    @Override
    public void activeTexture(int unit) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
    }

    @Override
    public void texParameteri(int target, int pname, int param) {
        GL11.glTexParameteri(target, pname, param);
    }

    @Override
    public void texEnvi(int pname, int param) {
        GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, pname, param);
    }

    @Override
    public void texEnvColor(float r, float g, float b, float a) {
        FloatBuffer direct = MemoryUtil.memAllocFloat(4);
        try {
            direct.put(r);
            direct.put(g);
            direct.put(b);
            direct.put(a);
            direct.flip();
            GL11.glTexEnvfv(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_COLOR, direct);
        } finally {
            MemoryUtil.memFree(direct);
        }
    }

    @Override
    public void pixelStorei(int pname, int param) {
        GL11.glPixelStorei(pname, param);
    }

    @Override
    public void pixelTransferf(int pname, float param) {
        GL11.glPixelTransferf(pname, param);
    }

    @Override
    public void texImage2D(int target, int level, int internalFormat, int width, int height, int format, int type, byte[] pixels, int offset) {
        ByteBuffer direct = MemoryUtil.memAlloc(pixels.length - offset);
        try {
            direct.put(pixels, offset, pixels.length - offset);
            direct.flip();
            GL11.glTexImage2D(target, level, internalFormat, width, height, 0, format, type, direct);
        } finally {
            MemoryUtil.memFree(direct);
        }
    }

    @Override
    public void texImage2D(int target, int level, int internalFormat, int width, int height, int format, int type, int[] pixels, int offset) {
        IntBuffer direct = MemoryUtil.memAllocInt(pixels.length - offset);
        try {
            direct.put(pixels, offset, pixels.length - offset);
            direct.flip();
            GL11.glTexImage2D(target, level, internalFormat, width, height, 0, format, type, direct);
        } finally {
            MemoryUtil.memFree(direct);
        }
    }

    @Override
    public void texImage2D(int target, int level, int internalFormat, int width, int height, int format, int type, float[] pixels, int offset) {
        FloatBuffer direct = MemoryUtil.memAllocFloat(pixels.length - offset);
        try {
            direct.put(pixels, offset, pixels.length - offset);
            direct.flip();
            GL11.glTexImage2D(target, level, internalFormat, width, height, 0, format, type, direct);
        } finally {
            MemoryUtil.memFree(direct);
        }
    }

    @Override
    public void allocateImage2D(int target, int level, int internalFormat, int width, int height, int format, int type) {
        GL11.glTexImage2D(target, level, internalFormat, width, height, 0, format, type, (ByteBuffer) null);
    }

    @Override
    public void texSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, byte[] pixels, int offset) {
        ByteBuffer direct = MemoryUtil.memAlloc(pixels.length - offset);
        try {
            direct.put(pixels, offset, pixels.length - offset);
            direct.flip();
            GL11.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, type, direct);
        } finally {
            MemoryUtil.memFree(direct);
        }
    }

    @Override
    public void texSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, int[] pixels, int offset) {
        IntBuffer direct = MemoryUtil.memAllocInt(pixels.length - offset);
        try {
            direct.put(pixels, offset, pixels.length - offset);
            direct.flip();
            GL11.glTexSubImage2D(target, level, xOffset, yOffset, width, height, format, type, direct);
        } finally {
            MemoryUtil.memFree(direct);
        }
    }

    @Override
    public void copyImage2D(int target, int level, int internalFormat, int x, int y, int width, int height) {
        GL11.glCopyTexImage2D(target, level, internalFormat, x, y, width, height, 0);
    }

    @Override
    public void copySubImage2D(int target, int level, int xOffset, int yOffset, int x, int y, int width, int height) {
        GL11.glCopyTexSubImage2D(target, level, xOffset, yOffset, x, y, width, height);
    }

    @Override
    public void generateMipmap(int target) {
        GL30.glGenerateMipmap(target);
    }

    @Override
    public void framebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
        GL30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
    }

    @Override
    public void flush() {
        GL11.glFlush();
    }

    @Override
    public long createShader(int type) {
        return ARBShaderObjects.glCreateShaderObjectARB(type);
    }

    @Override
    public void shaderSource(long shader, String source) {
        ARBShaderObjects.glShaderSourceARB((int) shader, source);
    }

    @Override
    public void compileShader(long shader) {
        ARBShaderObjects.glCompileShaderARB((int) shader);
    }

    @Override
    public int getObjectParam(long object, int pname) {
        return ARBShaderObjects.glGetObjectParameteriARB((int) object, pname);
    }

    @Override
    public String getObjectInfoLog(long object) {
        if (getObjectParam(object, GL20.GL_INFO_LOG_LENGTH) <= 1) {
            return "";
        }
        return ARBShaderObjects.glGetInfoLogARB((int) object);
    }

    @Override
    public void deleteObject(long object) {
        ARBShaderObjects.glDeleteObjectARB((int) object);
    }

    @Override
    public long createProgram() {
        return ARBShaderObjects.glCreateProgramObjectARB();
    }

    @Override
    public void attachShader(long program, long shader) {
        ARBShaderObjects.glAttachObjectARB((int) program, (int) shader);
    }

    @Override
    public void detachShader(long program, long shader) {
        ARBShaderObjects.glDetachObjectARB((int) program, (int) shader);
    }

    @Override
    public void linkProgram(long program) {
        ARBShaderObjects.glLinkProgramARB((int) program);
    }

    @Override
    public void useProgram(long program) {
        ARBShaderObjects.glUseProgramObjectARB((int) program);
    }

    @Override
    public int getUniformLocation(long program, String name) {
        return ARBShaderObjects.glGetUniformLocationARB((int) program, name);
    }

    @Override
    public void uniform1i(int location, int value) {
        ARBShaderObjects.glUniform1iARB(location, value);
    }

    @Override
    public void uniform1f(int location, float value) {
        ARBShaderObjects.glUniform1fARB(location, value);
    }

    @Override
    public void uniform2f(int location, float x, float y) {
        ARBShaderObjects.glUniform2fARB(location, x, y);
    }

    @Override
    public void uniform3f(int location, float x, float y, float z) {
        ARBShaderObjects.glUniform3fARB(location, x, y, z);
    }

    @Override
    public void uniform4f(int location, float x, float y, float z, float w) {
        ARBShaderObjects.glUniform4fARB(location, x, y, z, w);
    }

    @Override
    public void programLocalParameter4f(int target, int index, float x, float y, float z, float w) {
        ARBVertexProgram.glProgramLocalParameter4fARB(target, index, x, y, z, w);
    }

    @Override
    public int genArbProgram() {
        return ARBVertexProgram.glGenProgramsARB();
    }

    @Override
    public void bindArbProgram(int target, int id) {
        ARBVertexProgram.glBindProgramARB(target, id);
    }

    @Override
    public void programString(int target, int format, String source) {
        byte[] ascii = source.getBytes(StandardCharsets.US_ASCII);
        ByteBuffer direct = MemoryUtil.memAlloc(ascii.length);
        try {
            direct.put(ascii);
            direct.flip();
            ARBVertexProgram.glProgramStringARB(target, format, direct);
        } finally {
            MemoryUtil.memFree(direct);
        }
    }

    @Override
    public int programErrorPosition() {
        return GL11.glGetInteger(ARBVertexProgram.GL_PROGRAM_ERROR_POSITION_ARB);
    }
}
