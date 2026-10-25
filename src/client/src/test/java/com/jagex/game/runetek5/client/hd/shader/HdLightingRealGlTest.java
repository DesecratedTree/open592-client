package com.jagex.game.runetek5.client.hd.shader;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import com.jagex.game.runetek5.client.hd.shader.HdDrawRows.Row;
import jaggl.GlTestContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

/**
 * The per-pixel lighting program, rendered by a real driver and read back.
 *
 * <p>Everything in the shader package up to here is asserted: the source parses
 * under glslang, the ARB call sequence is faked, the gate is a truth table. None
 * of that can say what a GPU does with {@code gl_LightSource}, {@code gl_Fog},
 * {@code gl_Color} or a GLSL 1.20 loop. This test gets a real compatibility
 * context the same way the client does (JAWT on an off-screen AWT canvas), then
 * draws one quad into an FBO through the shipped {@link JagglShaderApi},
 * {@link HdProgramCompiler} and {@link HdLightingPipeline} - no test doubles -
 * and asserts the pixels against the lighting equation by hand.
 *
 * <p>The scene is deliberately arithmetically plain: white albedo, a white
 * directional light, GL_LINEAR fog, a 64x64 target. Every expected value below
 * is computed, not observed, so a shader that drifts is caught rather than
 * recorded.
 *
 * <p>If no context is reachable the class is skipped with the native or display
 * reason in the message; the pixels are then unproven, which is what a skip
 * means.
 */
@DisplayName("592HD lighting shader on a real GL driver")
class HdLightingRealGlTest {

	/** {@code GL_MODULATE}. */
	private static final int GL_MODULATE = 7681;

	/** {@code GL_REPLACE}. */
	private static final int GL_REPLACE = 8448;

	/** {@code GL_CURRENT_PROGRAM}, GL 2.0. */
	private static final int CURRENT_PROGRAM = 35725;

	private static final int SIZE = 64;

	/** A single byte of error on either side of a predicted channel. */
	private static final int TOLERANCE = 3;

	/** Fog far enough away to change nothing: the clean lit colour. */
	private static final float NO_FOG_START = 0.0F;

	private static final float NO_FOG_END = 100000.0F;

	/** z = -20 sits 4/7 of the way through this band. */
	private static final float BAND_START = 5.0F;

	private static final float BAND_END = 40.0F;

	private static GlTestContext context;

	private static HdLightingPipeline pipeline;

	private static int framebuffer;

	private static int targetTexture;

	private static int depthBuffer;

	/** A 1x1 pure-green material texture, so sampling is visible at a glance. */
	private static int greenTexture;

	@BeforeAll
	static void openContext() {
		try {
			context = GlTestContext.open();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}
		System.out.println("[hd-gl] " + context.renderer() + " | GL " + context.version()
			+ " | GLSL " + context.glslVersion());
		pipeline = new HdLightingPipeline(new JagglShaderApi());
		if (!pipeline.prepare()) {
			System.out.println("[hd-gl] " + pipeline.logLine());
		}
		createTargets();
	}

	@AfterAll
	static void closeContext() {
		if (context == null) {
			return;
		}
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		if (framebuffer != 0) {
			EXTFramebufferObject.glDeleteFramebuffersEXT(framebuffer);
		}
		if (targetTexture != 0) {
			GL11.glDeleteTextures(targetTexture);
		}
		if (depthBuffer != 0) {
			EXTFramebufferObject.glDeleteRenderbuffersEXT(depthBuffer);
		}
		if (greenTexture != 0) {
			GL11.glDeleteTextures(greenTexture);
		}
		context.close();
	}

	@Test
	@DisplayName("the shipped source compiles and links on this driver")
	void program_shouldCompileOnTheDriver() {
		assertAll(
			() -> assertTrue(context.supportsGlsl120(), "context GLSL " + context.glslVersion()),
			() -> assertTrue(pipeline.available(), pipeline.logLine())
		);
	}

	@Test
	@DisplayName("a sun facing the surface lights it; turning it around leaves ambient only")
	void sunDirection_shouldDriveBrightness() {
		Pixel lit = render(new Scene());
		Pixel unlit = render(new Scene().sunPosition(0.0F, 0.0F, -1.0F, 0.0F));

		// ambient 0.2 + diffuse 1.0 -> clamped 1.0; ambient alone is 0.2.
		assertAll(
			() -> assertPixel("sun toward the viewer", lit, 255, 255, 255),
			() -> assertPixel("sun behind the surface", unlit, 51, 51, 51),
			() -> assertTrue(lit.green - unlit.green > 190,
				"sun flip moved green by only " + (lit.green - unlit.green))
		);
	}

	@Test
	@DisplayName("the anti-sun slot is clamped at zero instead of subtracting light")
	void antiSun_shouldNotSubtract() {
		// GL_LIGHT1 is the anti-sun: brightest when pointing away, so a missing
		// max(dot, 0) would darken or blacken the surface instead.
		Pixel facingAway = render(new Scene()
			.sunDiffuse(new float[] { 0.0F, 0.0F, 0.0F, 1.0F })
			.antiSun(new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, new float[] { 0.0F, 0.0F, -1.0F, 0.0F }));
		Pixel facing = render(new Scene()
			.sunDiffuse(new float[] { 0.0F, 0.0F, 0.0F, 1.0F })
			.antiSun(new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, new float[] { 0.0F, 0.0F, 1.0F, 0.0F }));

		assertAll(
			() -> assertPixel("anti-sun behind the surface", facingAway, 51, 51, 51),
			() -> assertPixel("anti-sun facing the surface", facing, 255, 255, 255)
		);
	}

	@Test
	@DisplayName("the fog band blends into the lit colour, at the predicted fraction")
	void fog_shouldBlendTheBand() {
		Pixel fogged = render(new Scene().fog(BAND_START, BAND_END, 1.0F, 0.0F, 0.0F));
		Pixel clear = render(new Scene().fog(NO_FOG_START, NO_FOG_END, 1.0F, 0.0F, 0.0F));
		Scene fixedScene = new Scene().fog(BAND_START, BAND_END, 1.0F, 0.0F, 0.0F);
		fixedScene.fixedFunction = true;
		Pixel fixedFogged = render(fixedScene);

		// factor = (40 - 20) / 35 = 4/7. Red is 1.0 both as fog and as lit
		// albedo, so it stays 255; green and blue keep only the lit 4/7. The
		// lit value saturates at 1.0 before fog, exactly as fixed function
		// clamps it - comparing the two is what caught the shader fogging
		// unclamped 1.2 lighting (which read 175 instead of 146).
		assertAll(
			() -> assertPixel("no fog", clear, 255, 255, 255),
			() -> assertPixel("red fog at 4/7", fogged, 255, 146, 146),
			() -> assertClose("fogged per-pixel vs fixed function", fogged, fixedFogged)
		);
	}

	@Test
	@DisplayName("a fogless draw is lit with no band, where fixed function draws it unfogged")
	void fogOff_shouldBindAndKeepTheBandOut() {
		Scene fogged = new Scene().fog(BAND_START, BAND_END, 1.0F, 0.0F, 0.0F);
		Scene fogless = new Scene().fog(BAND_START, BAND_END, 1.0F, 0.0F, 0.0F);
		fogless.fogEnabled = false;
		Scene stock = new Scene().fog(BAND_START, BAND_END, 1.0F, 0.0F, 0.0F);
		stock.fogEnabled = false;
		stock.fixedFunction = true;

		Pixel perPixel = render(fogless);
		Pixel fixed = render(stock);
		Pixel banded = render(fogged);

		// GL still holds the band from the previous frame, so a shader that
		// applied it here would read 146 instead of the unfogged 255. This is
		// the terrain's case: the ground is fogless and lit per pixel.
		assertAll(
			() -> assertPixel("fogless per-pixel draw", perPixel, 255, 255, 255),
			() -> assertClose("fogless per-pixel vs fixed function", perPixel, fixed),
			() -> assertPixel("the same draw with the band", banded, 255, 146, 146)
		);
	}

	@Test
	@DisplayName("GL_REPLACE materials are refused, GL_MODULATE ones are drawn")
	void textureCombine_shouldGateTheBind() {
		Scene replace = new Scene().textured(true).combine(GL_REPLACE);
		Scene modulate = new Scene().textured(true).combine(GL_MODULATE);

		assertAll(
			() -> assertFalse(pipeline.begin(replace.request(),
				HdDrawSpace.MODEL,
				HdFrameState.of(0)), "replace-mode material must stay stock"),
			() -> assertEquals(0, currentProgram())
		);
		assertTrue(pipeline.begin(modulate.request(),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)));
		assertTrue(currentProgram() != 0, "the lighting program is bound");
		pipeline.unbind();
		assertEquals(0, currentProgram(), "unbind returns to fixed function");
	}

	@Test
	@DisplayName("point lights add only when the row is on, through the uniform loop")
	void pointLights_shouldAddOnlyWhenEnabled() {
		Scene scene = new Scene()
			.sunDiffuse(new float[] { 0.0F, 0.0F, 0.0F, 1.0F })
			.pointLight(new float[] { 0.0F, 1.0F, 0.0F, 1.0F }, new float[] { 0.0F, 0.0F, 2.0F, 1.0F }, 1);
		Pixel withPoint = render(scene);

		scene.pointLights = false;
		Pixel withoutPoint = render(scene);

		// Stock attenuation: method1896 zeroes constant and linear for every
		// light, so it is 1/(1 * d^2). The light is 2 units from the surface, so
		// 1/4 on top of the 0.2 ambient.
		assertAll(
			() -> assertPixel("point light on", withPoint, 51, 115, 51),
			() -> assertPixel("point lights row off", withoutPoint, 51, 51, 51)
		);
	}

	@Test
	@DisplayName("uTextured samples the material texture, and only when set")
	void textured_shouldSampleOnlyWhenFlagged() {
		Pixel textured = render(new Scene().textured(true));
		Pixel untextured = render(new Scene().textured(false));

		assertAll(
			() -> assertPixel("green material texture", textured, 0, 255, 0),
			() -> assertPixel("same draw, texture unit ignored", untextured, 255, 255, 255)
		);
	}

	@Test
	@DisplayName("per-pixel lighting matches fixed function on the same state")
	void shader_shouldMatchFixedFunction() {
		Scene shader = new Scene()
			.sunDiffuse(new float[] { 0.5F, 0.5F, 0.5F, 1.0F })
			.sunPosition(0.3F, 0.0F, 0.9539F, 0.0F);
		Scene fixed = new Scene()
			.sunDiffuse(new float[] { 0.5F, 0.5F, 0.5F, 1.0F })
			.sunPosition(0.3F, 0.0F, 0.9539F, 0.0F);
		fixed.fixedFunction = true;

		Pixel shaderPixel = render(shader);
		Pixel fixedPixel = render(fixed);

		// ambient 0.2 + 0.5 * dot((0,0,1), (0.3,0,0.9539)) = 0.2 + 0.477 = 0.677.
		assertAll(
			() -> assertPixel("fixed function", fixedPixel, 173, 173, 173),
			() -> assertClose("per-pixel vs fixed function", shaderPixel, fixedPixel)
		);
	}

	// -- The harness ------------------------------------------------------

	/** One scene: the light state, the fog state and the gate's inputs. */
	private static final class Scene {

		boolean fixedFunction;

		float[] sunDiffuse = { 1.0F, 1.0F, 1.0F, 1.0F };

		float[] sunPosition = { 0.0F, 0.0F, 1.0F, 0.0F };

		float[] antiSunDiffuse = { 0.0F, 0.0F, 0.0F, 1.0F };

		float[] antiSunPosition = { 0.0F, 0.0F, -1.0F, 0.0F };

		float[] pointDiffuse = { 0.0F, 0.0F, 0.0F, 1.0F };

		float[] pointPosition = { 0.0F, 0.0F, 1.0F, 1.0F };

		float fogStart = NO_FOG_START;

		float fogEnd = NO_FOG_END;

		float[] fogColour = { 1.0F, 0.0F, 0.0F, 1.0F };

		boolean fogEnabled = true;

		boolean textured;

		int combine = GL_MODULATE;

		boolean pointLights;

		int sceneLights;

		Scene sunPosition(float x, float y, float z, float w) {
			this.sunPosition = new float[] { x, y, z, w };
			return this;
		}

		Scene sunDiffuse(float[] diffuse) {
			this.sunDiffuse = diffuse;
			return this;
		}

		Scene antiSun(float[] diffuse, float[] position) {
			this.antiSunDiffuse = diffuse;
			this.antiSunPosition = position;
			return this;
		}

		Scene pointLight(float[] diffuse, float[] position, int count) {
			this.pointDiffuse = diffuse;
			this.pointPosition = position;
			this.sceneLights = count;
			this.pointLights = true;
			return this;
		}

		Scene fog(float start, float end, float red, float green, float blue) {
			this.fogStart = start;
			this.fogEnd = end;
			this.fogColour = new float[] { red, green, blue, 1.0F };
			return this;
		}

		Scene textured(boolean value) {
			this.textured = value;
			return this;
		}

		Scene combine(int mode) {
			this.combine = mode;
			return this;
		}

		/** The request the real gate is given: every row is a deliberate input. */
		HdDrawRequest request() {
			HdDrawRows rows = HdDrawRows.none()
				.with(Row.HD)
				.with(Row.PER_PIXEL_LIGHTING)
				.with(Row.POINT_LIGHTS, this.pointLights)
				.with(Row.NORMALS)
				.with(Row.TEXTURE, this.textured)
				.with(Row.FOG, this.fogEnabled);
			return new HdDrawRequest(rows, 0, this.combine);
		}
	}

	private static void createTargets() {
		framebuffer = EXTFramebufferObject.glGenFramebuffersEXT();
		targetTexture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, targetTexture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, SIZE, SIZE, 0,
			GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		depthBuffer = EXTFramebufferObject.glGenRenderbuffersEXT();
		EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, depthBuffer);
		EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT,
			GL14.GL_DEPTH_COMPONENT24, SIZE, SIZE);

		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, framebuffer);
		EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
			EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, targetTexture, 0);
		EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
			EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, depthBuffer);
		int status = EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT);
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		assertEquals(EXTFramebufferObject.GL_FRAMEBUFFER_COMPLETE_EXT, status, "offscreen target is incomplete");

		greenTexture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, greenTexture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		ByteBuffer green = BufferUtils.createByteBuffer(4);
		green.put((byte) 0).put((byte) 255).put((byte) 0).put((byte) 255).flip();
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, 1, 1, 0,
			GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, green);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	/** Draws one quad through the shipped pipeline and reads the centre texel. */
	private static Pixel render(Scene scene) {
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, framebuffer);
		GL11.glViewport(0, 0, SIZE, SIZE);
		GL11.glDisable(GL11.GL_DITHER);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0.0F, 0.0F, 1.0F, 1.0F);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		// Perspective at z = -20, exactly as a fixed-function model draw would
		// see it; the eye-space z is what the fog band is evaluated on.
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glFrustum(-0.1D, 0.1D, -0.1D, 0.1D, 1.0D, 100.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -20.0F);

		GL11.glFogf(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
		GL11.glFogf(GL11.GL_FOG_START, scene.fogStart);
		GL11.glFogf(GL11.GL_FOG_END, scene.fogEnd);
		GL11.glFogfv(GL11.GL_FOG_COLOR, vector(scene.fogColour));
		if (scene.fogEnabled) {
			GL11.glEnable(GL11.GL_FOG);
		} else {
			GL11.glDisable(GL11.GL_FOG);
		}

		GL11.glLightModelfv(GL11.GL_LIGHT_MODEL_AMBIENT,
			floats(0.2F, 0.2F, 0.2F, 1.0F));
		// Enabling is only meaningful to fixed function, but some drivers gate
		// gl_LightSource on the same bit, so both paths get the same lights.
		GL11.glEnable(GL11.GL_LIGHT0);
		GL11.glEnable(GL11.GL_LIGHT1);
		GL11.glEnable(GL11.GL_LIGHT2);
		setLight(GL11.GL_LIGHT0, scene.sunDiffuse, scene.sunPosition, null);
		setLight(GL11.GL_LIGHT1, scene.antiSunDiffuse, scene.antiSunPosition, null);
		// The exact attenuation GlToolkit.method1896 leaves behind: constant and
		// linear zeroed, only method1903's quadratic term set.
		setLight(GL11.GL_LIGHT2, scene.pointDiffuse, scene.pointPosition,
			new float[] { 0.0F, 0.0F, 1.0F });

		if (scene.fixedFunction) {
			// The shader multiplies lighting into gl_Color, so the fixed-function
			// reference has to take its material from the same colour.
			GL11.glEnable(GL11.GL_COLOR_MATERIAL);
			GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE);
			GL11.glEnable(GL11.GL_LIGHTING);
		} else {
			GL11.glDisable(GL11.GL_COLOR_MATERIAL);
			GL11.glDisable(GL11.GL_LIGHTING);
		}
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		if (scene.textured) {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, greenTexture);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		}

		boolean bound = false;
		if (!scene.fixedFunction) {
			bound = pipeline.begin(scene.request(),
				HdDrawSpace.MODEL,
				HdFrameState.of(scene.sceneLights));
			assertTrue(bound, "the gate refused a draw this scene expects to bind");
		}

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glNormal3f(0.0F, 0.0F, 1.0F);
		GL11.glTexCoord2f(0.0F, 0.0F);
		GL11.glVertex3f(-2.0F, -2.0F, 0.0F);
		GL11.glTexCoord2f(1.0F, 0.0F);
		GL11.glVertex3f(2.0F, -2.0F, 0.0F);
		GL11.glTexCoord2f(1.0F, 1.0F);
		GL11.glVertex3f(2.0F, 2.0F, 0.0F);
		GL11.glTexCoord2f(0.0F, 1.0F);
		GL11.glVertex3f(-2.0F, 2.0F, 0.0F);
		GL11.glEnd();

		if (bound) {
			pipeline.unbind();
		}
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_COLOR_MATERIAL);
		GL11.glFinish();

		assertEquals(GL11.GL_NO_ERROR, GL11.glGetError(), "GL error while drawing");
		Pixel pixel = readCentre();
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		return pixel;
	}

	private static void setLight(int light, float[] diffuse, float[] position, float[] attenuation) {
		GL11.glLightfv(light, GL11.GL_DIFFUSE, vector(diffuse));
		GL11.glLightfv(light, GL11.GL_SPECULAR, floats(0.0F, 0.0F, 0.0F, 1.0F));
		GL11.glLightfv(light, GL11.GL_AMBIENT, floats(0.0F, 0.0F, 0.0F, 1.0F));
		GL11.glLightfv(light, GL11.GL_POSITION, vector(position));
		if (attenuation != null) {
			GL11.glLightf(light, GL11.GL_CONSTANT_ATTENUATION, attenuation[0]);
			GL11.glLightf(light, GL11.GL_LINEAR_ATTENUATION, attenuation[1]);
			GL11.glLightf(light, GL11.GL_QUADRATIC_ATTENUATION, attenuation[2]);
		}
	}

	private static Pixel readCentre() {
		ByteBuffer pixel = BufferUtils.createByteBuffer(4);
		GL11.glReadPixels(SIZE / 2, SIZE / 2, 1, 1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixel);
		return new Pixel(pixel.get(0) & 0xFF, pixel.get(1) & 0xFF, pixel.get(2) & 0xFF);
	}

	private static int currentProgram() {
		return GL11.glGetInteger(CURRENT_PROGRAM);
	}

	private static FloatBuffer floats(float... values) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
		buffer.put(values).flip();
		return buffer;
	}

	/** A four-component vector from the scene's fixed-size arrays. */
	private static FloatBuffer vector(float[] values) {
		return floats(values[0], values.length > 1 ? values[1] : 0.0F,
			values.length > 2 ? values[2] : 0.0F, values.length > 3 ? values[3] : 1.0F);
	}

	private static void assertPixel(String what, Pixel pixel, int red, int green, int blue) {
		System.out.println("[hd-gl] " + what + " = " + pixel + ", expected rgb(" + red + "," + green + "," + blue + ")");
		assertAll(
			() -> assertTrue(Math.abs(pixel.red - red) <= TOLERANCE,
				what + ": red " + pixel.red + ", expected " + red),
			() -> assertTrue(Math.abs(pixel.green - green) <= TOLERANCE,
				what + ": green " + pixel.green + ", expected " + green),
			() -> assertTrue(Math.abs(pixel.blue - blue) <= TOLERANCE,
				what + ": blue " + pixel.blue + ", expected " + blue)
		);
	}

	private static void assertClose(String what, Pixel actual, Pixel expected) {
		assertAll(
			() -> assertTrue(Math.abs(actual.red - expected.red) <= TOLERANCE,
				what + ": red " + actual.red + " vs " + expected.red),
			() -> assertTrue(Math.abs(actual.green - expected.green) <= TOLERANCE,
				what + ": green " + actual.green + " vs " + expected.green),
			() -> assertTrue(Math.abs(actual.blue - expected.blue) <= TOLERANCE,
				what + ": blue " + actual.blue + " vs " + expected.blue)
		);
	}

	/** The centre texel, 0..255 per channel. */
	private static final class Pixel {

		final int red;

		final int green;

		final int blue;

		Pixel(int red, int green, int blue) {
			this.red = red;
			this.green = green;
			this.blue = blue;
		}

		@Override
		public String toString() {
			return "rgb(" + this.red + "," + this.green + "," + this.blue + ")";
		}
	}
}
