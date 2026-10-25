package com.jagex.game.runetek5.client.hd.shader;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("592HD program compiler")
class HdProgramCompilerTest {

	private FakeShaderApi api;

	@BeforeEach
	void setUp() {
		this.api = new FakeShaderApi();
		for (String uniform : HdShaderSource.uniforms()) {
			this.api.declareUniform(uniform);
		}
	}

	@Test
	@DisplayName("a good pair links, uploads both sources and leaks no shader object")
	void compile_shouldLinkAndCleanUp() {
		HdProgramCompiler.Result result = HdProgramCompiler.compileLighting(this.api);

		assertAll(
			() -> assertTrue(result.succeeded()),
			() -> assertEquals("", result.failure()),
			() -> assertNotNull(result.program()),
			() -> assertTrue(result.program().usable()),
			() -> assertTrue(this.api.uploadedSource(HdShaderSource.VERTEX)),
			() -> assertTrue(this.api.uploadedSource(HdShaderSource.FRAGMENT)),
			() -> assertEquals(2, this.api.calls("delete:")),
			() -> assertEquals(1, this.api.liveHandles().size(), "only the program may stay alive"),
			() -> assertTrue(this.api.liveHandles().contains(
				Long.valueOf(result.program().handle()))),
			() -> assertEquals(0, this.api.calls("use:"), "compiling must not bind")
		);
	}

	@Test
	@DisplayName("a vertex compile failure names the stage, quotes the driver and deletes the shader")
	void compile_shouldReportVertexFailure() {
		this.api.failVertexCompile = true;

		HdProgramCompiler.Result result = HdProgramCompiler.compileLighting(this.api);

		assertAll(
			() -> assertFalse(result.succeeded()),
			() -> assertNull(result.program()),
			() -> assertTrue(result.failure().contains("vertex compile failed: vertex boom"),
				result.failure()),
			() -> assertEquals(0, this.api.calls("createProgram")),
			() -> assertTrue(this.api.liveHandles().isEmpty())
		);
	}

	@Test
	@DisplayName("a fragment compile failure also deletes the vertex shader")
	void compile_shouldReportFragmentFailure() {
		this.api.failFragmentCompile = true;

		HdProgramCompiler.Result result = HdProgramCompiler.compileLighting(this.api);

		assertAll(
			() -> assertFalse(result.succeeded()),
			() -> assertTrue(result.failure().contains("fragment compile failed: fragment boom"),
				result.failure()),
			() -> assertTrue(this.api.liveHandles().isEmpty())
		);
	}

	@Test
	@DisplayName("a link failure deletes the program as well as both shaders")
	void compile_shouldReportLinkFailure() {
		this.api.failLink = true;

		HdProgramCompiler.Result result = HdProgramCompiler.compileLighting(this.api);

		assertAll(
			() -> assertFalse(result.succeeded()),
			() -> assertTrue(result.failure().contains("link failed: link boom"), result.failure()),
			() -> assertTrue(this.api.liveHandles().isEmpty()),
			() -> assertEquals(0, this.api.calls("use:"))
		);
	}

	@Test
	@DisplayName("a driver that cannot create objects is reported, not retried forever")
	void compile_shouldReportMissingObjects() {
		this.api.failCreation = true;

		HdProgramCompiler.Result result = HdProgramCompiler.compileLighting(this.api);

		assertAll(
			() -> assertFalse(result.succeeded()),
			() -> assertTrue(result.failure().contains("vertex shader object was not created"),
				result.failure()),
			() -> assertTrue(this.api.liveHandles().isEmpty())
		);
	}

	@Test
	@DisplayName("uniform locations are looked up once and cached, including -1")
	void program_shouldCacheUniformLocations() {
		HdProgram program = HdProgramCompiler.compileLighting(this.api).program();
		int before = this.api.calls("uniformLocation:");

		int textured = program.uniform(HdShaderSource.UNIFORM_TEXTURED);
		int texturedAgain = program.uniform(HdShaderSource.UNIFORM_TEXTURED);
		int missing = program.uniform("uNotThere");
		int missingAgain = program.uniform("uNotThere");

		assertAll(
			() -> assertEquals(textured, texturedAgain),
			() -> assertEquals(-1, missing),
			() -> assertEquals(-1, missingAgain),
			() -> assertEquals(before + 2, this.api.calls("uniformLocation:"))
		);
	}
}
