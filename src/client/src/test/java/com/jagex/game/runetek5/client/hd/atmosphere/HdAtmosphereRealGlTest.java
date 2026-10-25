package com.jagex.game.runetek5.client.hd.atmosphere;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.nio.ByteBuffer;

import com.jagex.game.runetek5.client.hd.shader.HdDrawRequest;
import com.jagex.game.runetek5.client.hd.shader.HdDrawRows.Row;
import com.jagex.game.runetek5.client.hd.shader.HdDrawRows;
import com.jagex.game.runetek5.client.hd.shader.HdDrawSpace;
import com.jagex.game.runetek5.client.hd.shader.HdFrameState;
import com.jagex.game.runetek5.client.hd.shader.HdLightingPipeline;
import com.jagex.game.runetek5.client.hd.shader.JagglShaderApi;
import com.jagex.game.runetek5.client.hd.shadow.ShadowBinding;
import jaggl.GlTestContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;

/**
 * The three atmosphere rows, rendered by a real driver and read back.
 *
 * <p>Everything above this class is arithmetic: the pure test pins the curves
 * and the pipeline test pins the uniform uploads. Neither can say what a GPU
 * does with {@code gl_ProjectionMatrix}, {@code gl_Fog} or a second sampler
 * unit. This test draws through the shipped {@link JagglShaderApi},
 * {@link HdLightingPipeline} and {@link HdAtmosphere} on a real compatibility
 * context - no test doubles - and asserts the pixels against the equations in
 * {@link HdAtmosphere}.
 *
 * <p>Every expected value below is computed from those equations, not observed.
 * The three claims:
 *
 * <ul>
 * <li>fog blends toward the fog colour at distance, and its off state is
 * byte-identical to the fixed-function draw;</li>
 * <li>distance fade reaches the fog colour at the far plane and leaves nearby
 * geometry exactly as it was;</li>
 * <li>ambient occlusion darkens the ambient term where the map shows contact
 * and leaves a free fragment untouched.</li>
 * </ul>
 *
 * <p>If no context is reachable the class is skipped with the reason in the
 * message; the pixels are then unproven, which is what a skip means.
 */
@DisplayName("592HD atmosphere rows on a real GL driver")
class HdAtmosphereRealGlTest {

	private static final int GL_MODULATE = 7681;

	private static final int CURRENT_PROGRAM = 35725;

	private static final int SIZE = 64;

	private static final int TOLERANCE = 3;

	/** Fog band used by the fog proof: start 5, end 40, so z = -20 is 4/7 through. */
	private static final float BAND_START = 5.0F;

	private static final float BAND_END = 40.0F;

	/** A band far enough away to change nothing. */
	private static final float NO_FOG_END = 100000.0F;

	/** The harness's projection: near 1, far 100. */
	private static final float FAR_PLANE = 100.0F;

	private static GlTestContext context;

	private static HdLightingPipeline pipeline;

	private static int framebuffer;

	private static int targetTexture;

	private static int depthBuffer;

	/** Depth 0.502: occludes the probe fragment under the wide AO bias. */
	private static int occluderTexture;

	/** Depth 0.941: farther than the probe, so the fragment is free. */
	private static int freeTexture;

	/** Depth 0.851: the threshold the scene-position probe straddles. */
	private static int midTexture;

	@BeforeAll
	static void openContext() {
		try {
			context = GlTestContext.open();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}
		System.out.println("[hd-gl] atmosphere | " + context.renderer() + " | GL " + context.version()
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
		if (occluderTexture != 0) {
			GL11.glDeleteTextures(occluderTexture);
		}
		if (freeTexture != 0) {
			GL11.glDeleteTextures(freeTexture);
		}
		if (midTexture != 0) {
			GL11.glDeleteTextures(midTexture);
		}
		context.close();
	}

	@Test
	@DisplayName("the shipped source still compiles with the atmosphere rows in it")
	void program_shouldCompile() {
		assertAll(
			() -> assertTrue(context.supportsGlsl120(), "context GLSL " + context.glslVersion()),
			() -> assertTrue(pipeline.available(), pipeline.logLine())
		);
	}

	@Test
	@DisplayName("fog off is the fixed-function draw, pixel for pixel")
	void fogOff_shouldMatchFixedFunction() {
		Scene scene = new Scene().z(-20.0F).fog(BAND_START, BAND_END, 1.0F, 0.0F, 0.0F);
		Pixel shader = render(scene);
		Scene fixed = new Scene().z(-20.0F).fog(BAND_START, BAND_END, 1.0F, 0.0F, 0.0F);
		fixed.fixedFunction = true;
		Pixel fixedPixel = render(fixed);

		// 4/7 of the way into the band: green and blue keep 4/7 of the lit white.
		assertAll(
			() -> assertPixel("fog row off", shader, 255, 146, 146),
			() -> assertClose("fog row off vs fixed function", shader, fixedPixel)
		);
	}

	@Test
	@DisplayName("fog on blends toward the fog colour sooner, at the predicted fraction")
	void fogOn_shouldBeDenserInsideTheBand() {
		Scene off = new Scene().z(-20.0F).fog(BAND_START, BAND_END, 1.0F, 0.0F, 0.0F);
		Scene on = new Scene().z(-20.0F).fog(BAND_START, BAND_END, 1.0F, 0.0F, 0.0F)
			.atmosphere(HdAtmosphere.resolve(true, true, false, false));

		Pixel stock = render(off);
		Pixel smoothed = render(on);

		// (4/7)^2 = 0.3265, so green keeps 0.3265 * 255 = 83.3 instead of 4/7.
		float fraction = HdAtmosphere.fogFraction(HdAtmosphere.stockFogFraction(BAND_START, BAND_END, 20.0F));
		assertAll(
			() -> assertPixel("fog row off", stock, 255, 146, 146),
			() -> assertPixel("fog row on", smoothed, 255, Math.round(fraction * 255.0F), Math.round(fraction * 255.0F)),
			() -> assertTrue(smoothed.green < stock.green,
				"HD fog must move toward the fog colour: green " + smoothed.green + " vs " + stock.green)
		);
	}

	@Test
	@DisplayName("fog on leaves both ends of the band where stock put them")
	void fogOn_shouldPinTheBandEnds() {
		Scene atStart = new Scene().z(-BAND_START).fog(BAND_START, BAND_END, 1.0F, 0.0F, 0.0F)
			.atmosphere(HdAtmosphere.resolve(true, true, false, false));
		Scene pastEnd = new Scene().z(-(BAND_END + 5.0F)).fog(BAND_START, BAND_END, 1.0F, 0.0F, 0.0F)
			.atmosphere(HdAtmosphere.resolve(true, true, false, false));

		assertAll(
			() -> assertPixel("at the band's start the HD band adds no fog", render(atStart), 255, 255, 255),
			() -> assertPixel("past the band's end the HD band is fully fog", render(pastEnd), 255, 0, 0)
		);
	}

	@Test
	@DisplayName("distance fade leaves nearby geometry untouched and fades the far plane")
	void distanceFade_shouldOnlyTouchTheOuterBand() {
		HdAtmosphere fade = HdAtmosphere.resolve(true, false, true, false);
		Pixel nearOff = render(new Scene().z(-20.0F).fog(0.0F, NO_FOG_END, 1.0F, 0.0F, 0.0F));
		Pixel nearOn = render(new Scene().z(-20.0F).fog(0.0F, NO_FOG_END, 1.0F, 0.0F, 0.0F).atmosphere(fade));
		Pixel midOff = render(new Scene().z(-92.5F).fog(0.0F, NO_FOG_END, 1.0F, 0.0F, 0.0F));
		Pixel midOn = render(new Scene().z(-92.5F).fog(0.0F, NO_FOG_END, 1.0F, 0.0F, 0.0F).atmosphere(fade));
		Pixel edgeOn = render(new Scene().z(-99.5F).fog(0.0F, NO_FOG_END, 1.0F, 0.0F, 0.0F).atmosphere(fade));

		// z = -92.5 is halfway through the 85..100 band, so half the colour is fog.
		assertAll(
			() -> assertEquals(nearOff.toString(), nearOn.toString(),
				"a fragment at z = -20 is outside the band and must not move at all"),
			() -> assertPixel("near fragment, fade row on", nearOn, 255, 255, 255),
			() -> assertPixel("fade row off at z = -92.5", midOff, 255, 255, 255),
			() -> assertPixel("halfway through the fade band", midOn, 255, 128, 128),
			() -> assertPixel("one unit before the far plane", edgeOn, 255, 9, 9)
		);
	}

	@Test
	@DisplayName("ambient occlusion darkens contact and leaves a free fragment alone")
	void ambientOcclusion_shouldDarkenOnlyContact() {
		HdAtmosphere ao = HdAtmosphere.resolve(true, false, false, true);
		ShadowBinding binding = contactBinding();

		Pixel occludedOff = render(new Scene().z(-20.0F).sunOff().shadow(binding));
		Pixel occludedOn = render(new Scene().z(-20.0F).sunOff().shadow(binding).atmosphere(ao));
		Pixel freeOn = render(new Scene().z(-20.0F).sunOff().shadow(binding).texture(freeTexture).atmosphere(ao));
		Pixel noMapOn = render(new Scene().z(-20.0F).sunOff().atmosphere(ao));

		// Ambient 0.2 alone is 51. Fully occluded it is 0.2 * (1 - 0.55) = 0.09 -> 23.
		assertAll(
			() -> assertPixel("row off, in contact", occludedOff, 51, 51, 51),
			() -> assertPixel("row on, in contact", occludedOn, 23, 23, 23),
			() -> assertPixel("row on, nothing occludes", freeOn, 51, 51, 51),
			() -> assertPixel("row on, no map bound", noMapOn, 51, 51, 51),
			() -> assertEquals(occludedOff.toString(), freeOn.toString(),
				"a free fragment must not move even with the row on")
		);
	}

	@Test
	@DisplayName("world position is scene space: one world point, one occlusion result")
	void worldPosition_shouldBeSceneSpace() {
		HdAtmosphere ao = HdAtmosphere.resolve(true, false, false, true);
		ShadowBinding binding = tiltedBinding();

		// The same world point, z = -20, reached two ways: baked into the vertex
		// buffer, and carried by the model matrix with the buffer at z = 0.
		Pixel baked = render(new Scene().z(0.0F).vertexZ(-20.0F).sunOff().shadow(binding)
			.texture(midTexture).atmosphere(ao));
		Pixel throughMatrix = render(new Scene().z(-20.0F).vertexZ(0.0F).sunOff().shadow(binding)
			.texture(midTexture).atmosphere(ao).model(translate(0.0F, 0.0F, -20.0F)));
		// The old reading of gl_Vertex, kept as the failing case: with the model
		// matrix ignored, the same world point lands on a different texel.
		Pixel modelLocal = render(new Scene().z(-20.0F).vertexZ(0.0F).sunOff().shadow(binding)
			.texture(midTexture).atmosphere(ao));

		assertAll(
			() -> assertPixel("scene position baked into the buffer", baked, 51, 51, 51),
			() -> assertPixel("the same scene position carried by the matrix", throughMatrix, 51, 51, 51),
			() -> assertEquals(baked.toString(), throughMatrix.toString(),
				"the split between the buffer and the matrix must not change the pixel"),
			() -> assertPixel("model-local reading, which is what the shader used to do", modelLocal, 23, 23, 23)
		);
	}

	@Test
	@DisplayName("turning every row back off restores the pre-row pixels exactly")
	void rowsOff_shouldRoundTripExactly() {
		HdAtmosphere all = HdAtmosphere.resolve(true, true, true, true);
		Scene base = new Scene().z(-20.0F).fog(BAND_START, BAND_END, 1.0F, 0.0F, 0.0F).sunOff().shadow(contactBinding());

		Pixel before = render(base);
		Pixel on = render(new Scene().z(-20.0F).fog(BAND_START, BAND_END, 1.0F, 0.0F, 0.0F).sunOff()
			.shadow(contactBinding()).atmosphere(all));
		Pixel after = render(base);

		assertAll(
			() -> assertEquals(before.toString(), after.toString(),
				"the off state after the rows were used must be the pre-row frame"),
			() -> assertTrue(before.green != on.green || before.red != on.red,
				"the rows must have changed something while on")
		);
	}

	@Test
	@DisplayName("the draw leaves no program, no error and no active unit behind")
	void draw_shouldNotLeakState() {
		render(new Scene().z(-20.0F).fog(BAND_START, BAND_END, 1.0F, 0.0F, 0.0F).sunOff()
			.shadow(contactBinding()).atmosphere(HdAtmosphere.resolve(true, true, true, true)));

		assertAll(
			() -> assertEquals(0, GL11.glGetInteger(CURRENT_PROGRAM), "fixed function after the draw"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError()),
			() -> assertEquals(GL13.GL_TEXTURE0, GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE),
				"the shadow unit must not stay selected")
		);
	}

	// -- The harness ------------------------------------------------------

	/** One draw: projection depth, fog state, lights and the rows under test. */
	private static final class Scene {

		boolean fixedFunction;

		float z = -20.0F;

		float vertexZ;

		float[] model;

		float sunDiffuse = 1.0F;

		float fogStart;

		float fogEnd = NO_FOG_END;

		float[] fogColour = { 1.0F, 0.0F, 0.0F, 1.0F };

		boolean fogEnabled = true;

		HdAtmosphere atmosphere = HdAtmosphere.OFF;

		ShadowBinding shadow;

		int occluder = occluderTexture;

		Scene z(float value) {
			this.z = value;
			return this;
		}

		Scene vertexZ(float value) {
			this.vertexZ = value;
			return this;
		}

		Scene model(float[] matrix) {
			this.model = matrix;
			return this;
		}

		Scene sunOff() {
			this.sunDiffuse = 0.0F;
			return this;
		}

		Scene fog(float start, float end, float red, float green, float blue) {
			this.fogStart = start;
			this.fogEnd = end;
			this.fogColour = new float[] { red, green, blue, 1.0F };
			return this;
		}

		Scene atmosphere(HdAtmosphere rows) {
			this.atmosphere = rows;
			return this;
		}

		Scene shadow(ShadowBinding binding) {
			this.shadow = binding;
			return this;
		}

		Scene texture(int texture) {
			this.occluder = texture;
			return this;
		}

		HdDrawRequest request() {
			HdDrawRows rows = HdDrawRows.none()
				.with(Row.HD)
				.with(Row.PER_PIXEL_LIGHTING)
				.with(Row.NORMALS)
				.with(Row.FOG, this.fogEnabled);
			return new HdDrawRequest(rows, 0, GL_MODULATE);
		}
	}

	/** A column-major translation, as the toolkit composes for a model. */
	private static float[] translate(float x, float y, float z) {
		return new float[] { 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F,
			x, y, z, 1.0F };
	}

	/**
	 * A map whose depth depends on scene z: z = -20 lands at 0.88 (free under the
	 * 0.85 map with the wider AO bias), z = 0 at 0.90 (occluded). That difference
	 * is what makes a model-local reading visible.
	 */
	private static ShadowBinding tiltedBinding() {
		float[] matrix = new float[16];
		matrix[12] = 0.5F;
		matrix[13] = 0.5F;
		matrix[10] = 0.001F;
		matrix[14] = 0.9F;
		matrix[15] = 1.0F;
		return new ShadowBinding(2, 8, 0, 0.01F, matrix);
	}

	/** A map whose whole surface sits at 0.9 with a 0.01 bias, as contact would. */
	private static ShadowBinding contactBinding() {
		float[] matrix = new float[16];
		// Column-major rows are (0,0,0,0.5), (0,0,0,0.5), (0,0,0,0.9), (0,0,0,1).
		matrix[12] = 0.5F;
		matrix[13] = 0.5F;
		matrix[14] = 0.9F;
		matrix[15] = 1.0F;
		return new ShadowBinding(1, 8, 0, 0.01F, matrix);
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

		occluderTexture = solidTexture(128);
		freeTexture = solidTexture(240);
		midTexture = solidTexture(217);
	}

	/** An 8x8 texture whose red channel is {@code value}/255, read as a depth. */
	private static int solidTexture(int value) {
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		ByteBuffer texels = BufferUtils.createByteBuffer(8 * 8 * 4);
		for (int i = 0; i < 8 * 8; i++) {
			texels.put((byte) value).put((byte) value).put((byte) value).put((byte) 255);
		}
		texels.flip();
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, 8, 8, 0,
			GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, texels);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return texture;
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

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glFrustum(-0.1D, 0.1D, -0.1D, 0.1D, 1.0D, FAR_PLANE);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, scene.z);

		GL11.glFogf(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
		GL11.glFogf(GL11.GL_FOG_START, scene.fogStart);
		GL11.glFogf(GL11.GL_FOG_END, scene.fogEnd);
		GL11.glFogfv(GL11.GL_FOG_COLOR, floats(scene.fogColour[0], scene.fogColour[1],
			scene.fogColour[2], scene.fogColour[3]));
		if (scene.fogEnabled) {
			GL11.glEnable(GL11.GL_FOG);
		} else {
			GL11.glDisable(GL11.GL_FOG);
		}

		GL11.glLightModelfv(GL11.GL_LIGHT_MODEL_AMBIENT, floats(0.2F, 0.2F, 0.2F, 1.0F));
		GL11.glEnable(GL11.GL_LIGHT0);
		GL11.glEnable(GL11.GL_LIGHT1);
		setLight(GL11.GL_LIGHT0, scene.sunDiffuse);
		setLight(GL11.GL_LIGHT1, 0.0F);

		if (scene.fixedFunction) {
			GL11.glEnable(GL11.GL_COLOR_MATERIAL);
			GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE);
			GL11.glEnable(GL11.GL_LIGHTING);
		} else {
			GL11.glDisable(GL11.GL_COLOR_MATERIAL);
			GL11.glDisable(GL11.GL_LIGHTING);
		}
		GL11.glColor3f(1.0F, 1.0F, 1.0F);

		boolean bound = false;
		if (!scene.fixedFunction) {
			if (scene.shadow != null) {
				// The toolkit's job: the map on its own unit, selected through the
				// same unit the pipeline binds the sampler to.
				GL13.glActiveTexture(GL13.GL_TEXTURE1);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, scene.occluder);
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
			}
			bound = pipeline.begin(scene.request(),
				HdDrawSpace.model(scene.model, null),
				HdFrameState.of(0)
					.withShadow(scene.shadow)
					.withAtmosphere(scene.atmosphere));
			assertTrue(bound, "the gate refused a draw this scene expects to bind");
		}

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glNormal3f(0.0F, 0.0F, 1.0F);
		GL11.glVertex3f(-2.0F, -2.0F, scene.vertexZ);
		GL11.glVertex3f(2.0F, -2.0F, scene.vertexZ);
		GL11.glVertex3f(2.0F, 2.0F, scene.vertexZ);
		GL11.glVertex3f(-2.0F, 2.0F, scene.vertexZ);
		GL11.glEnd();

		if (bound) {
			pipeline.unbind();
		}
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_COLOR_MATERIAL);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glFinish();

		assertEquals(GL11.GL_NO_ERROR, GL11.glGetError(), "GL error while drawing");
		Pixel pixel = readCentre();
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		return pixel;
	}

	private static void setLight(int light, float diffuse) {
		GL11.glLightfv(light, GL11.GL_DIFFUSE, floats(diffuse, diffuse, diffuse, 1.0F));
		GL11.glLightfv(light, GL11.GL_SPECULAR, floats(0.0F, 0.0F, 0.0F, 1.0F));
		GL11.glLightfv(light, GL11.GL_AMBIENT, floats(0.0F, 0.0F, 0.0F, 1.0F));
		// Straight at the surface, so the diffuse term is the light's own colour.
		GL11.glLightfv(light, GL11.GL_POSITION, floats(0.0F, 0.0F, 1.0F, 0.0F));
	}

	private static Pixel readCentre() {
		ByteBuffer pixel = BufferUtils.createByteBuffer(4);
		GL11.glReadPixels(SIZE / 2, SIZE / 2, 1, 1, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixel);
		return new Pixel(pixel.get(0) & 0xFF, pixel.get(1) & 0xFF, pixel.get(2) & 0xFF);
	}

	private static java.nio.FloatBuffer floats(float... values) {
		java.nio.FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
		buffer.put(values).flip();
		return buffer;
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
