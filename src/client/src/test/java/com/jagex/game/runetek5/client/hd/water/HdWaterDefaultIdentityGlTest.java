package com.jagex.game.runetek5.client.hd.water;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.jagex.game.runetek5.client.hd.shader.HdProgramCompiler;
import com.jagex.game.runetek5.client.hd.shader.HdShaderApi;
import com.jagex.game.runetek5.client.hd.shader.JagglShaderApi;
import jaggl.GlTestContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**
* The default frame, rendered twice on the same driver: once through the shipped
* water program and once through the fragment stage this port had before the
* area's water existed.
*
* <p>The second program is compiled from a literal copy of that stage
* ({@code hd/water/water-fragment-before-area.glsl}, extracted from the source
* as it stood before this change), so the comparison is exact pixels: both
* programs run on this GPU, in this process, over the same textures, the same
* fixed-function state and the same uniforms. The only difference between them
* is the source text the change touched.
*
* <p>With no area resolved ({@link HdWaterArea#DEFAULT}) every pixel of the
* surface, its ripples, its caustics and the seabed it shades must be identical.
* A frame drawn with a reported area is compared too, because a comparison that
* cannot tell two frames apart proves nothing about which one was computed.
*
* <p>If no context is reachable the class is skipped with the driver's reason;
* the pixels are then unproven, which is what a skip means.
*/
@DisplayName("592HD water's default frame against the frame before the area")
class HdWaterDefaultIdentityGlTest {

	private static final int SIZE = 64;

	/** World span of the quad: wide enough for several map repeats. */
	private static final float SPAN = 2000.0F;

	/** Below the camera, so the surface is face up and lit, not edge-on. */
	private static final float PLANE_Y = -1000.0F;

	private static final float FOG_START = 500.0F;

	private static final float FOG_END = 9000.0F;

	private static final int TEX_ALBEDO = 0;

	private static final int TEX_NORMAL = 1;

	private static final int TEX_FOAM = 2;

	private static final int TEX_FLOW = 3;

	private static GlTestContext context;

	private static HdShaderApi api;

	/** The shipped calm surface, compiled by the shipped compiler. */
	private static long current;

	/** The same vertex stage with the pre-area fragment stage. */
	private static long reference;

	@BeforeAll
	static void openContext() {
		try {
			context = GlTestContext.open();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}
		System.out.println("[hd-water-default] " + context.renderer() + " | GL " + context.version()
			+ " | GLSL " + context.glslVersion());
		api = new JagglShaderApi();
		HdProgramCompiler.Result shipped = HdProgramCompiler.compileWater(api);
		assumeTrue(shipped.succeeded(), "the shipped water fragment must compile: " + shipped.failure());
		current = shipped.program().handle();
		reference = compile(HdWaterSource.VERTEX, preAreaFragment());
		createTextures();
	}

	@AfterAll
	static void closeContext() {
		if (context != null) {
			context.close();
		}
	}

	@Test
	@DisplayName("no area: surface, ripples, caustics and seabed are the pre-area frame, byte for byte")
	void defaultFrame_shouldBeThePreAreaFrame() {
		HdWaterType type = HdWaterType.DEFAULT;
		HdWaterArea area = HdWaterArea.DEFAULT;
		// The shared context's very first draw after this class attaches it is
		// not stable: whichever program draws first differs from its own second
		// draw by 4038 px (measured in both orders), and from then on every draw
		// is exact. Warm both programs once; the claim below is about the
		// steady-state frame, which is what the client draws.
		render(reference, type, area, 0);
		render(current, type, area, 0);

		int[] settled = render(current, type, area, 0);
		int[] settledBefore = render(reference, type, area, 0);
		int[] moving = render(current, type, area, 1);
		int[] movingBefore = render(reference, type, area, 1);
		int[] bare = render(current, type, area, 2);
		int[] bareBefore = render(reference, type, area, 2);
		int tintedDiff = differences(settled,
			render(current, type, HdWaterArea.of(0x204060, 0.45F, 1.0F, 1.0F, 1.0F, 1.0F), 0));
		// Determinism first: the same program twice, and the reference twice,
		// so a difference below cannot be the harness flickering.
		int repeatCurrent = differences(settled, render(current, type, area, 0));
		int repeatReference = differences(settledBefore, render(reference, type, area, 0));

		System.out.println("[hd-water-default] distinct=" + distinct(settled)
			+ " settledDiff=" + differences(settled, settledBefore)
			+ " movedDiff=" + differences(moving, movingBefore)
			+ " bareDiff=" + differences(bare, bareBefore)
			+ " moved=" + differences(settled, moving)
			+ " repeatCurrent=" + repeatCurrent + " repeatReference=" + repeatReference
			+ " tintedDiff=" + tintedDiff);

		assertAll(
			() -> assertArrayEquals(settledBefore, settled,
				"the settled default frame must be the frame before the area, pixel for pixel"),
			() -> assertArrayEquals(movingBefore, moving,
				"and so must the frame with the waves advanced"),
			() -> assertArrayEquals(bareBefore, bare,
				"and the frame with the maps and the caustics row off"),
			// Not a flat or empty capture: the comparison has to have something
			// to compare.
			() -> assertTrue(distinct(settled) >= 8,
				"the frame must shade the surface: " + distinct(settled)),
			() -> assertTrue(differences(settled, moving) > SIZE * SIZE / 100,
				"the ripples must move, or the clock is not reaching the shader: "
					+ differences(settled, moving))
		);
	}

	@Test
	@DisplayName("a reported area is not the default frame, so the comparison can see a difference")
	void areaFrame_shouldDifferFromTheDefault() {
		HdWaterType type = HdWaterType.DEFAULT;
		int[] settled = render(current, type, HdWaterArea.DEFAULT, 0);
		int[] tinted = render(current, type, HdWaterArea.of(0x204060, 0.45F, 1.0F, 1.0F, 1.0F, 1.0F), 0);
		int[] tiled = render(current, type, HdWaterArea.of(0x69809C, -1.0F, 1.0F, 1.0F, 1.0F, 2.0F), 1);

		assertAll(
			() -> assertFalse(Arrays.equals(settled, tinted),
				"the area's tint must reach the body: " + differences(settled, tinted) + " px"),
			() -> assertFalse(Arrays.equals(settled, tiled),
				"the area's tiling must reach the ripples: " + differences(settled, tiled) + " px")
		);
	}

	/** Draws the quad with one program and one scene, and reads it back. */
	private static int[] render(long program, HdWaterType type, HdWaterArea area, int scene) {
		GL11.glViewport(0, 0, SIZE, SIZE);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glFogf(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
		GL11.glFogf(GL11.GL_FOG_START, FOG_START);
		GL11.glFogf(GL11.GL_FOG_END, FOG_END);
		GL11.glFogfv(GL11.GL_FOG_COLOR, floats(0.55F, 0.65F, 0.8F, 1.0F));
		GL11.glEnable(GL11.GL_LIGHT0);
		GL11.glLightfv(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, floats(1.0F, 1.0F, 1.0F, 1.0F));
		GL11.glLightfv(GL11.GL_LIGHT0, GL11.GL_AMBIENT, floats(0.0F, 0.0F, 0.0F, 1.0F));
		GL11.glLightfv(GL11.GL_LIGHT0, GL11.GL_POSITION, floats(0.35F, 1.0F, 0.25F, 0.0F));
		GL11.glLightModelfv(GL11.GL_LIGHT_MODEL_AMBIENT, floats(0.1F, 0.1F, 0.1F, 1.0F));
		// The client keeps the camera in the modelview and the world in the
		// vertex, so the plane is rotated into view and stays the world xz it
		// is in the shader; its y is depth and has to sit in front.
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(-SPAN, SPAN, -SPAN, SPAN, 100.0, 10000.0);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
		GL11.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		api.use(program);
		upload(program, type, area, scene);

		GL11.glBegin(GL11.GL_QUADS);
		corner(-SPAN, -SPAN, 0.0F, 0.0F);
		corner(SPAN, -SPAN, 1.0F, 0.0F);
		corner(SPAN, SPAN, 1.0F, 1.0F);
		corner(-SPAN, SPAN, 0.0F, 1.0F);
		GL11.glEnd();
		GL11.glFinish();

		assertEquals(GL11.GL_NO_ERROR, GL11.glGetError(), "GL error while drawing");
		return pixels();
	}

	private static void corner(float x, float z, float u, float v) {
		GL11.glTexCoord3f(u, v, 0.4F);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glVertex3f(x, PLANE_Y, z);
	}

	/**
	* The same uniforms the pipeline hands the program, in the same order: the
	* type's palette, then the frame, then the area. Scene 0 is the settled
	* default, scene 1 the same surface half a second of water time later, scene
	* 2 the frame with the maps and the caustics row off.
	*/
	private static void upload(long program, HdWaterType type, HdWaterArea area, int scene) {
		float[] surface = type.surfaceColour();
		float[] depth = type.depthColour();
		float[] foam = type.foamColour();
		api.uniform1f(location(program, HdWaterSource.UNIFORM_SHININESS), type.gloss());
		api.uniform1f(location(program, HdWaterSource.UNIFORM_NORMAL_STRENGTH), type.normalStrength());
		api.uniform3f(location(program, HdWaterSource.UNIFORM_DEEP_COLOUR), depth[0], depth[1], depth[2]);
		api.uniform3f(location(program, HdWaterSource.UNIFORM_SHALLOW_COLOUR),
			surface[0], surface[1], surface[2]);
		api.uniform3f(location(program, HdWaterSource.UNIFORM_FOAM_COLOUR), foam[0], foam[1], foam[2]);
		api.uniform3f(location(program, HdWaterSource.UNIFORM_WATER_SPEC),
			type.specular(), type.fresnel(), type.opacity());
		api.uniform3f(location(program, HdWaterSource.UNIFORM_WATER_SHAPE),
			type.hasFoam() ? 1.0F : 0.0F, type.duration(), type.plain() ? 1.0F : 0.0F);
		api.uniform1i(location(program, HdWaterSource.UNIFORM_POINT_LIGHTS), 0);
		api.uniform1f(location(program, HdWaterSource.UNIFORM_TIME), scene == 0 ? 0.0F : 1.37F);
		api.uniform1f(location(program, HdWaterSource.UNIFORM_FLOW), 0.4F);
		api.uniform1f(location(program, HdWaterSource.UNIFORM_REFLECTION), 0.0F);
		api.uniform1i(location(program, HdWaterSource.UNIFORM_TEXTURE), TEX_ALBEDO);
		api.uniform1i(location(program, HdWaterSource.UNIFORM_WATER_NORMAL), TEX_NORMAL);
		api.uniform1i(location(program, HdWaterSource.UNIFORM_WATER_FOAM), TEX_FOAM);
		api.uniform1i(location(program, HdWaterSource.UNIFORM_WATER_FLOW), TEX_FLOW);
		api.uniform1f(location(program, HdWaterSource.UNIFORM_WATER_MAPS), scene == 2 ? 0.0F : 1.0F);
		api.uniform1f(location(program, HdWaterSource.UNIFORM_CAUSTICS), scene == 2 ? 0.0F : 1.0F);
		api.uniform3f(location(program, HdWaterSource.UNIFORM_WATER_PARAMS),
			area.waves(), area.glint(), area.size());
		api.uniform2f(location(program, HdWaterSource.UNIFORM_WATER_BLEND),
			area.shownSize(), area.blend());
		api.uniform4f(location(program, HdWaterSource.UNIFORM_WATER_TINT),
			area.tintRed(), area.tintGreen(), area.tintBlue(), area.tintStrength());
	}

	private static int location(long program, String name) {
		return api.uniformLocation(program, name);
	}

	/** Compiles the pre-area fragment stage against the shipped vertex stage. */
	private static long compile(String vertex, String fragment) {
		long vertexStage = shader(HdShaderApi.VERTEX_SHADER, vertex, "vertex");
		long fragmentStage = shader(HdShaderApi.FRAGMENT_SHADER, fragment, "pre-area fragment");
		long program = api.createProgram();
		api.attach(program, vertexStage);
		api.attach(program, fragmentStage);
		api.link(program);
		boolean linked = api.linkSucceeded(program);
		String log = api.programInfoLog(program);
		api.detach(program, vertexStage);
		api.detach(program, fragmentStage);
		api.delete(vertexStage);
		api.delete(fragmentStage);
		assumeTrue(linked, "the pre-area fragment must link: " + log);
		return program;
	}

	private static long shader(int type, String source, String stage) {
		long shader = api.createShader(type);
		api.shaderSource(shader, source);
		api.compileShader(shader);
		boolean compiled = api.compileSucceeded(shader);
		String log = api.shaderInfoLog(shader);
		assumeTrue(compiled, "the " + stage + " stage must compile: " + log);
		return shader;
	}

	/**
	* The pre-area fragment stage, copied out of the source as it stood before
	* this change. It is read from the test tree rather than the classpath
	* because the project keeps its resources in the main source set.
	*/
	private static String preAreaFragment() {
		Path path = Paths.get("src/client/src/test/resources/hd/water/water-fragment-before-area.glsl");
		try {
			return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
		} catch (IOException error) {
			throw new IllegalStateException("cannot read " + path.toAbsolutePath(), error);
		}
	}

	private static void createTextures() {
		int[] textures = new int[4];
		GL11.glGenTextures(textures);
		for (int unit = 0; unit < textures.length; unit++) {
			ByteBuffer data = BufferUtils.createByteBuffer(SIZE * SIZE * 4);
			for (int y = 0; y < SIZE; y++) {
				for (int x = 0; x < SIZE; x++) {
					data.put((byte) channel(unit, x, y, 0));
					data.put((byte) channel(unit, x, y, 1));
					data.put((byte) channel(unit, x, y, 2));
					data.put((byte) 255);
				}
			}
			data.flip();
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures[unit]);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, SIZE, SIZE, 0,
				GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		}
	}

	/** Deterministic art per unit: albedo, ripple normals, foam mask, flow. */
	private static int channel(int unit, int x, int y, int channel) {
		switch (unit) {
			case TEX_NORMAL:
				if (channel == 0) {
					return 128 + (int) (110.0 * Math.sin(x * 0.5) * Math.cos(y * 0.35));
				}
				return channel == 1 ? 128 + (int) (110.0 * Math.cos(x * 0.28 + y * 0.41)) : 255;
			case TEX_FOAM:
				return (x / 4 + y / 4) % 2 == 0 ? 255 : 32;
			case TEX_FLOW:
				return channel == 0 ? (x / 8) * 32 : channel == 1 ? (y / 8) * 32 : 128;
			default:
				return channel == 0 ? (x * 4) % 256 : channel == 1 ? (y * 4) % 256 : 255;
		}
	}

	private static int[] pixels() {
		ByteBuffer buffer = BufferUtils.createByteBuffer(SIZE * SIZE * 4);
		GL11.glReadPixels(0, 0, SIZE, SIZE, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		int[] out = new int[SIZE * SIZE];
		for (int i = 0; i < out.length; i++) {
			out[i] = (buffer.get(i * 4) & 0xFF) << 16
				| (buffer.get(i * 4 + 1) & 0xFF) << 8
				| (buffer.get(i * 4 + 2) & 0xFF);
		}
		return out;
	}

	private static int distinct(int[] frame) {
		Set<Integer> colours = new HashSet<Integer>();
		for (int pixel : frame) {
			colours.add(pixel);
		}
		return colours.size();
	}

	private static int differences(int[] first, int[] second) {
		int count = 0;
		for (int i = 0; i < first.length; i++) {
			if (first[i] != second[i]) {
				count++;
			}
		}
		return count;
	}

	private static java.nio.FloatBuffer floats(float... values) {
		java.nio.FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
		buffer.put(values).flip();
		return buffer;
	}
}
