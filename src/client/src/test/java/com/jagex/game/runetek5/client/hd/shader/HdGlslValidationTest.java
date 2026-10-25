package com.jagex.game.runetek5.client.hd.shader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Runs the real GLSL compiler over both stages, when one is available.
 *
 * <p>No flag to remember: the validator is found on {@code PATH} when it is
 * installed, and the check only skips on a machine that does not have it. An
 * unusual install can still be pointed at with
 * {@code -Dhd.glslValidator=<path>} (or {@code HD_GLSL_VALIDATOR}). Everything
 * else in {@link HdShaderSourceTest} is structural; this is the only check that
 * proves the sources parse and link against a real front end.
 */
@DisplayName("592HD GLSL validation")
class HdGlslValidationTest {

	@Test
	@DisplayName("glslangValidator accepts the vertex and fragment stages")
	void shaders_shouldValidate() throws Exception {
		String validator = locate();
		assumeTrue(validator != null,
			"glslangValidator is not on PATH; install it or pass -Dhd.glslValidator=<path>");

		Path directory = Files.createTempDirectory("hd-glsl");
		Path vertex = directory.resolve("lighting.vert");
		Path fragment = directory.resolve("lighting.frag");
		Files.write(vertex, HdShaderSource.VERTEX.getBytes(StandardCharsets.ISO_8859_1));
		Files.write(fragment, HdShaderSource.FRAGMENT.getBytes(StandardCharsets.ISO_8859_1));

		Result vertexResult = run(validator, vertex);
		Result fragmentResult = run(validator, fragment);

		assertEquals(0, vertexResult.exitCode, "vertex stage: " + vertexResult.output);
		assertEquals(0, fragmentResult.exitCode, "fragment stage: " + fragmentResult.output);
	}

	/**
	 * The validator to run, or null where this machine has none: the configured
	 * path first, then {@code PATH}. Installing glslang is all a developer has to
	 * do for this check to start running -- there is no property to discover.
	 */
	private static String locate() {
		String configured = System.getProperty("hd.glslValidator", System.getenv("HD_GLSL_VALIDATOR"));
		if (configured != null) {
			return new File(configured).isFile() ? configured : null;
		}
		String path = System.getenv("PATH");
		if (path == null) {
			return null;
		}
		boolean windows = System.getProperty("os.name", "").toLowerCase().contains("win");
		String name = windows ? "glslangValidator.exe" : "glslangValidator";
		for (String entry : path.split(File.pathSeparator)) {
			if (entry.isEmpty()) {
				continue;
			}
			File candidate = new File(entry, name);
			if (candidate.isFile()) {
				return candidate.getAbsolutePath();
			}
		}
		return null;
	}

	private static Result run(String validator, Path source) throws Exception {
		Process process = new ProcessBuilder(validator, source.toString())
			.redirectErrorStream(true)
			.start();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		InputStream in = process.getInputStream();
		try {
			byte[] chunk = new byte[4096];
			int read;
			while ((read = in.read(chunk)) > 0) {
				buffer.write(chunk, 0, read);
			}
		} finally {
			in.close();
		}
		int exitCode = process.waitFor();
		return new Result(exitCode, new String(buffer.toByteArray(), StandardCharsets.ISO_8859_1));
	}

	private static final class Result {

		final int exitCode;

		final String output;

		Result(int exitCode, String output) {
			this.exitCode = exitCode;
			this.output = output;
		}
	}
}
