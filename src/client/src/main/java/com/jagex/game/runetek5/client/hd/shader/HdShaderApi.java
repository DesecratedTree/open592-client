package com.jagex.game.runetek5.client.hd.shader;

/**
 * The GL entry points 592HD's shader pipeline needs, isolated behind one
 * interface.
 *
 * <p>Everything above this is ordinary Java that can be driven by a fake: the
 * program compiler, the uniform cache and the bind/unbind state machine are
 * tested without a GL context. {@link JagglShaderApi} is the only class that
 * touches {@code jaggl.OpenGL}.
 *
 * <p>Handles are {@code long} because that is how this client's jaggl wrapper
 * carries GL object names; 0 always means "none".
 */
public interface HdShaderApi {

	/** {@code GL_VERTEX_SHADER_ARB}. */
	int VERTEX_SHADER = 0x8B31;

	/** {@code GL_FRAGMENT_SHADER_ARB}. */
	int FRAGMENT_SHADER = 0x8B30;

	/** {@code GL_PROGRAM_OBJECT_ARB}: the value {@link #use(long)} takes when unbinding. */
	long NO_PROGRAM = 0L;

	long createShader(int type);

	void shaderSource(long shader, String source);

	void compileShader(long shader);

	/** True when the last compile of {@code shader} succeeded. */
	boolean compileSucceeded(long shader);

	/** The driver's compile log, or an empty string when there is none. */
	String shaderInfoLog(long shader);

	long createProgram();

	void attach(long program, long shader);

	void link(long program);

	/** True when the last link of {@code program} succeeded. */
	boolean linkSucceeded(long program);

	/** The driver's link log, or an empty string when there is none. */
	String programInfoLog(long program);

	void detach(long program, long shader);

	/** Deletes a shader or program object; ignores 0. */
	void delete(long handle);

	/** Binds {@code program}, or {@link #NO_PROGRAM} to return to fixed function. */
	void use(long program);

	/** The uniform's location, or -1 when it is absent or optimised away. */
	int uniformLocation(long program, String name);

	void uniform1i(int location, int value);

	void uniform1f(int location, float value);

	void uniform2f(int location, float x, float y);

	/** Three floats in one call; the custom water surface's palette goes up whole. */
	void uniform3f(int location, float x, float y, float z);

	/** Four floats in one call; the shadow matrix goes up as four rows. */
	void uniform4f(int location, float x, float y, float z, float w);
}
