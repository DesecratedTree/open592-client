package com.jagex.game.runetek5.client.hd.shader;

import java.nio.charset.StandardCharsets;

import jaggl.OpenGL;

/**
 * {@link HdShaderApi} over this client's {@code jaggl} ARB shader-object
 * binding.
 *
 * <p>The exact call sequence is the one the stock shader paths already use
 * ({@code MusicPatch.method4397} compiles, {@code TileLighting.method3694}
 * links): create, source, compile, then read {@code COMPILE_STATUS}. Link
 * status is {@code LINK_STATUS}; both logs are fetched by first reading
 * {@code INFO_LOG_LENGTH}. jaggl writes the log's length back into the array it
 * is handed (including the NUL), so the log is trimmed at the first zero byte -
 * the raw buffer would otherwise carry trailing NULs into the failure report.
 *
 * <p>Nothing here is called unless 592HD is enabled and its program is being
 * prepared, which is what keeps the stock path byte-identical.
 */
public final class JagglShaderApi implements HdShaderApi {

	/** {@code GL_OBJECT_COMPILE_STATUS_ARB}. */
	private static final int COMPILE_STATUS = 35713;

	/** {@code GL_OBJECT_LINK_STATUS_ARB}. */
	private static final int LINK_STATUS = 35714;

	/** {@code GL_OBJECT_INFO_LOG_LENGTH_ARB}. */
	private static final int INFO_LOG_LENGTH = 35716;

	/** Shared scratch for the status/length queries; the pipeline is single-threaded. */
	private final int[] status = new int[1];

	private final int[] written = new int[1];

	@Override
	public long createShader(int type) {
		return OpenGL.glCreateShaderObjectARB(type);
	}

	@Override
	public void shaderSource(long shader, String source) {
		OpenGL.glShaderSourceARB(shader, source);
	}

	@Override
	public void compileShader(long shader) {
		OpenGL.glCompileShaderARB(shader);
	}

	@Override
	public boolean compileSucceeded(long shader) {
		OpenGL.glGetObjectParameterivARB(shader, COMPILE_STATUS, this.status, 0);
		return this.status[0] != 0;
	}

	@Override
	public String shaderInfoLog(long shader) {
		return log(shader);
	}

	@Override
	public long createProgram() {
		return OpenGL.glCreateProgramObjectARB();
	}

	@Override
	public void attach(long program, long shader) {
		OpenGL.glAttachObjectARB(program, shader);
	}

	@Override
	public void link(long program) {
		OpenGL.glLinkProgramARB(program);
	}

	@Override
	public boolean linkSucceeded(long program) {
		OpenGL.glGetObjectParameterivARB(program, LINK_STATUS, this.status, 0);
		return this.status[0] != 0;
	}

	@Override
	public String programInfoLog(long program) {
		return log(program);
	}

	@Override
	public void detach(long program, long shader) {
		OpenGL.glDetachObjectARB(program, shader);
	}

	@Override
	public void delete(long handle) {
		if (handle != 0L) {
			OpenGL.glDeleteObjectARB(handle);
		}
	}

	@Override
	public void use(long program) {
		OpenGL.glUseProgramObjectARB(program);
	}

	@Override
	public int uniformLocation(long program, String name) {
		return OpenGL.glGetUniformLocationARB(program, name);
	}

	@Override
	public void uniform1i(int location, int value) {
		if (location >= 0) {
			OpenGL.glUniform1iARB(location, value);
		}
	}

	@Override
	public void uniform1f(int location, float value) {
		if (location >= 0) {
			OpenGL.glUniform1fARB(location, value);
		}
	}

	@Override
	public void uniform2f(int location, float x, float y) {
		if (location >= 0) {
			OpenGL.glUniform2fARB(location, x, y);
		}
	}

	@Override
	public void uniform3f(int location, float x, float y, float z) {
		if (location >= 0) {
			OpenGL.glUniform3fARB(location, x, y, z);
		}
	}

	@Override
	public void uniform4f(int location, float x, float y, float z, float w) {
		if (location >= 0) {
			OpenGL.glUniform4fARB(location, x, y, z, w);
		}
	}

	/** Reads an object's info log, or "" when the driver reports none. */
	private String log(long handle) {
		OpenGL.glGetObjectParameterivARB(handle, INFO_LOG_LENGTH, this.status, 0);
		int length = this.status[0];
		if (length <= 1) {
			return "";
		}
		byte[] bytes = new byte[length];
		this.written[0] = 0;
		OpenGL.glGetInfoLogARB(handle, length, this.written, 0, bytes, 0);
		int used = this.written[0];
		if (used <= 0 || used > length) {
			used = length;
		}
		String text = new String(bytes, 0, used, StandardCharsets.ISO_8859_1);
		int end = text.indexOf('\0');
		if (end >= 0) {
			text = text.substring(0, end);
		}
		return text.trim();
	}
}
