package com.jagex.game.runetek5.client.hd.sky;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

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
 * The sky in pixels: the quad the toolkit's seam draws, on a real driver.
 *
 * <p>What is being proved, in order of how easily it could be wrong:
 *
 * <ul>
 * <li><b>The shader's arithmetic is the model's.</b> Every pixel is compared to
 * {@link HdSkySource#shade} evaluated on the ray {@link SkyRaySpace} builds from
 * the same camera and projection the toolkit handed the pass. A wrong axis, a
 * transposed matrix, a missing offset, a mis-uploaded uniform or a different
 * clamp all show up as a frame that disagrees with the prediction.</li>
 * <li><b>The disc follows the scene's sun.</b> The brightest pixel is where
 * {@link SkyRaySpace#projectsTo} says the lighting sun is, and moving the sun
 * moves the pixel - the proof that the sky reads the light rather than a
 * palette of its own.</li>
 * <li><b>Day and night recolours the dome and dims the world.</b> With the row
 * on, midnight is measurably darker than noon - and the lit world behind the
 * dome dims with it, which is why this class also pins the world's pixels; with
 * the row off, the two phases are the same frame, pixel for pixel.</li>
 * <li><b>Off is the stock frame, and on is a good guest.</b> With the row off
 * nothing is issued: the frame is the clear colour and every piece of GL state
 * is what it was. With it on, the frame matches the prediction, GL reports no
 * error, the state sandwich restores what it borrowed, the dome writes no
 * depth, and a stock quad drawn afterwards is still exactly its own colour -
 * which it would not be if the sky's program, matrices or enables had leaked.
 * </li>
 * </ul>
 *	 * <p>Runs by default; {@link GlTestContext#open()} skips it with the reason
	 * when this machine cannot give a GL context.
 */
@DisplayName("592HD sky")
class HdSkyGlTest {

	private static final int SIZE = 64;

	/** Sentinel clear: magenta cannot occur in this sky, so a miss is visible. */
	private static final int CLEAR = 0xFF00FF;

	/** The fog colour the world fades into, and so the horizon. */
	private static final int FOG = 0x203040;

	/** The sun's diffuse colour, warm like the 592 sun. */
	private static final int SUN = 0xFFF0D0;

	/** A channel step's worth of rounding, and no more. */
	private static final int PIXEL_TOLERANCE = 2;

	/** The lit surface's depth, near enough that the fog band leaves it alone. */
	private static final float LIT_DEPTH = 200.0F;

	/** Beyond the band's end, so the fog owns it completely. */
	private static final float FOGGED_DEPTH = 1000.0F;

	/** The sun's diffuse scale for the world test: a lit but unsaturated surface. */
	private static final float WORLD_SUN_INTENSITY = 0.5F;

	/** The ambient level the world test lights to. */
	private static final float WORLD_AMBIENT = 0.10F;

	private static GlToolkit toolkit;

	private static Frame frame;

	private static Canvas canvas;

	private static int framebuffer;

	private static int targetTexture;

	private static int depthBuffer;

	@BeforeAll
	static void openToolkit() throws Exception {
		try {
			GlTestContext warmup = GlTestContext.open();
			warmup.close();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}

		frame = new Frame("592HD sky test");
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(SIZE, SIZE));
		frame.add(canvas);
		frame.pack();
		frame.setLocation(-4000, -4000);
		frame.setVisible(true);
		try {
			toolkit = new GlToolkit(0, canvas, null, 0, null);
		} catch (Throwable error) {
			frame.dispose();
			assumeTrue(false, "GlToolkit could not start in this JVM: " + error);
		}
		System.out.println("[hd-sky] toolkit " + GL11.glGetString(GL11.GL_RENDERER) + " | GL "
			+ GL11.glGetString(GL11.GL_VERSION));

		createTarget();

		Path preferences = Files.createTempDirectory("hd-sky");
		HdSettings.setDirectoryForTest(preferences.toFile());
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.SKY, 1);
		HdSettings.set(HdFeature.DAY_NIGHT, 0);

		// The scene's view: a 64 unit high window over the whole canvas, so the
		// projection is square and the frame covers 53 degrees.
		toolkit.H(SIZE / 2, SIZE / 2, SIZE, SIZE);
		// The camera: at the origin looking down -z, which is what
		// JavaMatrix's identity means here.
		toolkit.ea(new JavaMatrix());
		toolkit.UA(FOG, 1000, 3000);
		HdSkyTime.reset();
	}

	@AfterAll
	static void closeToolkit() {
		HdSettings.resetForTest();
		HdSkyTime.reset();
		if (frame != null) {
			frame.dispose();
		}
	}

	@Test
	@DisplayName("every pixel is the frame the shader's source predicts")
	void frame_shouldMatchThePredictedRays() {
		setDayNight(0);
		aimSun(20.0F, 0.0F);
		int[] drawn = drawSky();
		int[] predicted = predict();

		int worst = 0;
		int worstX = 0;
		int worstY = 0;
		for (int index = 0; index < drawn.length; index++) {
			int delta = difference(drawn[index], predicted[index]);
			if (delta > worst) {
				worst = delta;
				worstX = index % SIZE;
				worstY = index / SIZE;
			}
		}
		final int worstChannel = worst;
		final int atX = worstX;
		final int atY = worstY;
		System.out.println("[hd-sky] prediction: worst channel difference " + worstChannel
			+ " at (" + atX + ", " + atY + "), sky drawn " + toolkit.hdSkyDraws() + " time(s)");
		assertAll(
			() -> assertTrue(worstChannel <= PIXEL_TOLERANCE,
				"the drawn frame disagrees with the model by " + worstChannel + " at (" + atX + ", " + atY + ")"),
			() -> assertNotEquals(CLEAR, drawn[SIZE * SIZE / 2 + SIZE / 2], "the sky must be drawn")
		);
	}

	@Test
	@DisplayName("the disc sits on the sun the lighting was given, and moves with it")
	void disc_shouldFollowTheLightsSun() {
		setDayNight(0);
		aimSun(18.0F, 15.0F);
		int right = brightest(drawSky());
		int rightExpected = projectSun();
		aimSun(18.0F, -15.0F);
		int left = brightest(drawSky());
		int leftExpected = projectSun();

		System.out.println("[hd-sky] disc: sun right at " + describe(right) + " expected "
			+ describe(rightExpected) + ", sun left at " + describe(left) + " expected "
			+ describe(leftExpected));
		assertAll(
			() -> assertTrue(Math.abs(right % SIZE - rightExpected % SIZE) <= 2,
				"the disc is " + right % SIZE + " on x, the sun projects to " + rightExpected % SIZE),
			() -> assertTrue(Math.abs(right / SIZE - rightExpected / SIZE) <= 2,
				"the disc is " + right / SIZE + " on y, the sun projects to " + rightExpected / SIZE),
			() -> assertTrue(Math.abs(left % SIZE - leftExpected % SIZE) <= 2),
			() -> assertTrue(Math.abs(left / SIZE - leftExpected / SIZE) <= 2),
			// The frame's centre is not the camera's axis (the toolkit's surface is
			// wider than the canvas), so the proof is the movement, not the side.
			() -> assertTrue(right % SIZE > left % SIZE + 5,
				"moving the sun right must move the disc right: " + (right % SIZE) + " vs " + (left % SIZE)),
			() -> assertEquals(rightExpected % SIZE - leftExpected % SIZE, right % SIZE - left % SIZE, 2,
				"and by the distance the projection predicts")
		);
	}

	@Test
	@DisplayName("day and night darkens the sky, and only when its row is on")
	void clock_shouldDarkenOnlyWhenOn() {
		setDayNight(2);
		HdSkyTime.setForTest(0.5F);
		int[] noon = drawSky();
		HdSkyTime.setForTest(0.0F);
		int[] midnight = drawSky();
		int noonBrightness = meanBrightness(noon);
		int midnightBrightness = meanBrightness(midnight);

		setDayNight(0);
		HdSkyTime.setForTest(0.5F);
		int[] noClock = drawSky();
		HdSkyTime.setForTest(0.0F);
		int[] noClockNight = drawSky();

		System.out.println("[hd-sky] clock: noon " + noonBrightness + ", midnight " + midnightBrightness
			+ "; with the row off " + meanBrightness(noClock) + " at both phases");
		assertAll(
			() -> assertTrue(noonBrightness > midnightBrightness + 15,
				"midnight must be clearly darker: " + noonBrightness + " vs " + midnightBrightness),
			() -> assertArrayEquals(noClock, noClockNight,
				"with Day and night off the phase must not change a single pixel")
		);
	}

	@Test
	@DisplayName("the world's light follows the clock too, not only the dome")
	void worldLight_shouldFollowTheClock() {
		setDayNight(2);

		// The fill first, at the level GlGround and GlModel bake from: the pixel
		// proof below disables the second light, so this field is its witness.
		HdSkyTime.setForTest(0.5F);
		aimSun(20.0F, 0.0F);
		float noonFill = toolkit.aFloat65;
		float noonFillExpected = 0.6F * HdDayNight.factors(1.0F).fill();
		HdSkyTime.setForTest(0.0F);
		aimSun(20.0F, 0.0F);
		float midnightFill = toolkit.aFloat65;
		float midnightFillExpected = 0.6F * HdDayNight.factors(0.0F).fill();

		HdSkyTime.setForTest(0.5F);
		int[] noon = drawLitWorld(false);
		int noonPredicted = predictedWorldLight();
		HdSkyColours noonColours = toolkit.hdSkyColours();
		// Dawn's own soft edge: half way up the ramp, so a factor that only
		// switches at the ends is caught rather than interpolated.
		HdSkyTime.setForTest(0.28F);
		int[] dawn = drawLitWorld(false);
		int dawnPredicted = predictedWorldLight();
		HdSkyTime.setForTest(0.0F);
		int[] midnight = drawLitWorld(false);
		int midnightPredicted = predictedWorldLight();
		HdSkyColours midnightColours = toolkit.hdSkyColours();

		int centre = SIZE * SIZE / 2 + SIZE / 2;
		int noonPixel = noon[centre];
		int dawnPixel = dawn[centre];
		int midnightPixel = midnight[centre];
		System.out.println("[hd-sky] world light: noon " + hex(noonPixel) + " (predicted "
			+ hex(noonPredicted) + "), dawn " + hex(dawnPixel) + " (predicted " + hex(dawnPredicted)
			+ "), midnight " + hex(midnightPixel) + " (predicted " + hex(midnightPredicted)
			+ "), fill " + noonFill + " -> " + midnightFill);
		assertAll(
			() -> assertNotEquals(CLEAR, noonPixel, "the lit quad must cover the frame"),
			() -> assertPixel(noonPredicted, noonPixel, PIXEL_TOLERANCE,
				"the lit surface must be the ambient plus the sun the factors predicted"),
			() -> assertPixel(dawnPredicted, dawnPixel, PIXEL_TOLERANCE,
				"and part way down the ramp it must be the interpolated pair"),
			() -> assertEquals(0xFF / 255.0F, noonColours.sun()[0], 1.0e-6F,
				"at noon the sun's colour is the caller's, exactly"),
			() -> assertTrue(brightness(noonPixel) > brightness(dawnPixel),
				"dawn is darker than noon: " + brightness(dawnPixel) + " vs " + brightness(noonPixel)),
			() -> assertTrue(brightness(dawnPixel) > brightness(midnightPixel),
				"and midnight darker still: " + brightness(midnightPixel)),
			() -> assertPixel(midnightPredicted, midnightPixel, PIXEL_TOLERANCE,
				"and at midnight it must be the dimmed ambient plus the dimmed sun"),
			() -> assertTrue(brightness(noonPixel) > brightness(midnightPixel) + 40,
				"the world must darken, not just the dome: " + brightness(noonPixel) + " vs "
					+ brightness(midnightPixel)),
			() -> assertTrue(brightness(midnightPixel) > 10,
				"night is dim, not black: " + brightness(midnightPixel)),
			() -> assertEquals(noonFillExpected, noonFill, 1.0e-6F, "noon keeps the whole fill"),
			() -> assertEquals(midnightFillExpected, midnightFill, 1.0e-6F, "night keeps a share of it"),
			() -> assertTrue(midnightColours.sun()[0] < noonColours.sun()[0], "the sun's own colour dims"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);

		// And the row off is the stock frame, world included: not one pixel moves.
		setDayNight(0);
		HdSkyTime.setForTest(0.5F);
		int[] rowOffNoon = drawLitWorld(false);
		HdSkyTime.setForTest(0.0F);
		int[] rowOffMidnight = drawLitWorld(false);
		assertArrayEquals(rowOffNoon, rowOffMidnight,
			"with Day and night off the hour must not change the lit world by a byte");
	}

	@Test
	@DisplayName("the world fades into the same colour the dome's horizon is made of")
	void worldFog_shouldBeTheHorizonsColour() {
		setDayNight(2);
		int centre = SIZE * SIZE / 2 + SIZE / 2;

		HdSkyTime.setForTest(0.5F);
		int[] noonWorld = drawLitWorld(true);
		int noonHorizon = pack(toolkit.hdSkyColours().horizon());
		float[] noonFog = fogColour();
		int noonDome = drawSky()[centre];

		HdSkyTime.setForTest(0.0F);
		int[] midnightWorld = drawLitWorld(true);
		int midnightHorizon = pack(toolkit.hdSkyColours().horizon());
		float[] midnightFog = fogColour();
		int midnightDome = drawSky()[centre];

		System.out.println("[hd-sky] fog: noon fogged " + hex(noonWorld[centre]) + " horizon "
			+ hex(noonHorizon) + ", midnight fogged " + hex(midnightWorld[centre]) + " horizon "
			+ hex(midnightHorizon));
		assertAll(
			() -> assertPixel(noonHorizon, noonWorld[centre], PIXEL_TOLERANCE,
				"a fully fogged surface must be the fog colour the dome's horizon uses"),
			() -> assertPixel(midnightHorizon, midnightWorld[centre], PIXEL_TOLERANCE,
				"and at midnight it must be the dimmed one"),
			() -> assertPixel(noonHorizon, noonDome, PIXEL_TOLERANCE,
				"the dome's horizon is that same colour"),
			() -> assertPixel(midnightHorizon, midnightDome, PIXEL_TOLERANCE, "at midnight too"),
			() -> assertEquals(channel(noonHorizon, 0) / 255.0F, noonFog[0], 2.0F / 255.0F,
				"GL's own fog colour is what the dome was handed"),
			() -> assertEquals(channel(midnightHorizon, 0) / 255.0F, midnightFog[0], 2.0F / 255.0F),
			() -> assertTrue(brightness(midnightHorizon) < brightness(noonHorizon) - 10,
				"the fog fades into a darker colour at night"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("with the row off nothing is drawn and the frame is untouched")
	void skyOff_shouldLeaveTheStockFrame() {
		setDayNight(0);
		aimSun(20.0F, 0.0F);
		HdSettings.set(HdFeature.SKY, 0);
		long drawsBefore = toolkit.hdSkyDraws();
		String[] before = fingerprint();
		int[] off = drawSky();
		String[] after = fingerprint();

		System.out.println("[hd-sky] off: " + litPixels(off) + " pixels changed, draws "
			+ toolkit.hdSkyDraws());
		assertAll(
			() -> assertEquals(drawsBefore, toolkit.hdSkyDraws(), "no sky may be drawn"),
			() -> assertEquals(0, litPixels(off), "the frame must be the clear colour alone"),
			() -> assertArrayEquals(before, after, "an off row must not touch GL state"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("on is a good guest: state, program and pixels are restored")
	void skyOn_shouldRestoreEverything() {
		setDayNight(0);
		aimSun(20.0F, 0.0F);
		beginFrame();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glColor4f(0.25F, 0.5F, 0.75F, 1.0F);
		String[] before = fingerprint();
		assertTrue(toolkit.hdSkyFrame(), "the seam must draw with the row on");
		String[] after = fingerprint();
		assertEquals(GL11.GL_NO_ERROR, GL11.glGetError(), "GL error while drawing the sky");

		// A stock draw afterwards: if the sky's program, matrix or enables had
		// leaked, this quad would not be exactly red.
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(-1.0F, -1.0F);
		GL11.glVertex2f(1.0F, -1.0F);
		GL11.glVertex2f(1.0F, 1.0F);
		GL11.glVertex2f(-1.0F, 1.0F);
		GL11.glEnd();
		GL11.glPopMatrix();
		int[] afterStockDraw = capture();

		assertAll(
			() -> assertArrayEquals(before, after, "the sky left GL state behind"),
			() -> assertEquals(0xFF0000, afterStockDraw[SIZE * SIZE / 2 + SIZE / 2],
				"the stock quad must still draw in its own colour")
		);
	}

	@Test
	@DisplayName("the dome writes no depth, so the world still draws over it")
	void dome_shouldBeABackground() {
		setDayNight(0);
		aimSun(20.0F, 0.0F);
		beginFrame();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glDepthMask(true);
		GL11.glClearDepth(1.0D);
		assertTrue(toolkit.hdSkyFrame());

		// Halfway between the eye and the far plane: it must beat the dome's
		// depth. If the dome had written the near plane, this would be rejected.
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex3f(-1.0F, -1.0F, -0.5F);
		GL11.glVertex3f(1.0F, -1.0F, -0.5F);
		GL11.glVertex3f(1.0F, 1.0F, -0.5F);
		GL11.glVertex3f(-1.0F, 1.0F, -0.5F);
		GL11.glEnd();
		GL11.glPopMatrix();
		int[] frame = capture();
		System.out.println("[hd-sky] depth: centre pixel " + hex(frame[SIZE * SIZE / 2 + SIZE / 2]));
		assertAll(
			() -> assertEquals(0x00FF00, frame[SIZE * SIZE / 2 + SIZE / 2],
				"geometry drawn after the dome must not be depth-rejected"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("the dome and the geometry put the sun in the same pixel")
	void domeAndGeometry_shouldAgreeOnWhereTheSunIs() {
		setDayNight(0);
		aimSun(18.0F, 10.0F);

		// The sky alone, so its disc can be found without the marker on top.
		int disc = brightest(drawSky());

		// Then the same frame with a marker drawn down the scene's own pipeline:
		// the toolkit's rotX(180) * camera modelview, the toolkit's projection,
		// and the sun direction as a point in scene space. Where GL puts that
		// marker is the geometry's answer to "where is the sun".
		beginFrame();
		assertTrue(toolkit.hdSkyFrame());
		float[] sunDirection = sunDirection();
		loadSceneMatrices();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glPointSize(5.0F);
		GL11.glColor3f(1.0F, 0.0F, 1.0F);
		GL11.glBegin(GL11.GL_POINTS);
		GL11.glVertex3f(sunDirection[0] * 100.0F, sunDirection[1] * 100.0F, sunDirection[2] * 100.0F);
		GL11.glEnd();
		int[] marked = capture();
		int marker = markerCentroid(marked);

		System.out.println("[hd-sky] geometry: sky disc at " + describe(disc) + ", marker at "
			+ describe(marker));
		assertAll(
			() -> assertTrue(marker >= 0, "the marker must be visible"),
			() -> assertTrue(Math.abs(disc % SIZE - marker % SIZE) <= 2,
				"x: sky " + disc % SIZE + ", geometry " + marker % SIZE),
			() -> assertTrue(Math.abs(disc / SIZE - marker / SIZE) <= 2,
				"y: sky " + disc / SIZE + ", geometry " + marker / SIZE)
		);
	}

	@Test
	@DisplayName("pitching the camera moves the sun down the frame by exactly the pitch")
	void camera_shouldMoveTheDome() {
		setDayNight(0);
		aimSun(20.0F, 0.0F);
		int levelRow = brightest(drawSky()) / SIZE;

		// Pitch the camera up by the sun's own elevation: what was 20 degrees
		// above the centre must now sit on it. The camera matrix is the
		// scene-to-eye rotation, so a pitch up is rotX with the eye looking
		// further up the scene's y axis.
		float cos = (float) Math.cos(Math.toRadians(20.0D));
		float sin = (float) Math.sin(Math.toRadians(20.0D));
		JavaMatrix camera = new JavaMatrix();
		camera.aFloat204 = cos;
		camera.aFloat203 = -sin;
		camera.aFloat201 = sin;
		camera.aFloat199 = cos;
		toolkit.ea(camera);
		int upRow = brightest(drawSky()) / SIZE;
		toolkit.ea(new JavaMatrix());

		System.out.println("[hd-sky] camera: sun row level " + levelRow + ", pitched " + upRow);
		assertAll(
			() -> assertTrue(levelRow > SIZE / 2 + 8, "the sun starts well above the centre: " + levelRow),
			() -> assertTrue(upRow < levelRow - 15,
				"a 20 degree pitch must bring the sun most of the way to the centre: " + upRow)
		);
	}

	// -- Frames -----------------------------------------------------------

	private static void setDayNight(int value) {
		HdSettings.set(HdFeature.DAY_NIGHT, value);
		HdSettings.set(HdFeature.SKY, 1);
	}

	/**
	 * Points the scene's light where the sky should read the sun from.
	 *
	 * <p>Scene angles, not GL ones: this world's y axis points down and its z axis
	 * is forward, so a sun {@code elevation} degrees above the horizon is at
	 * {@code y = -sin(elevation)} and {@code z = +cos(azimuth) * cos(elevation)}.
	 * Getting that wrong is exactly the mirrored dome the geometric cross-check
	 * below is here to catch.
	 */
	private static void aimSun(float elevationDegrees, float azimuthDegrees) {
		double elevation = Math.toRadians(elevationDegrees);
		double azimuth = Math.toRadians(azimuthDegrees);
		float x = (float) (Math.sin(azimuth) * Math.cos(elevation));
		float y = (float) -Math.sin(elevation);
		float z = (float) (Math.cos(azimuth) * Math.cos(elevation));
		// The stock upload: colour, sun and anti-sun brightness, direction.
		toolkit.scaleHeight(SUN, 1.0F, 0.6F, x, y, z);
	}

	/**
	 * One frame of the world: the dome, then a lit surface drawn with the toolkit's
	 * own light state, read back.
	 *
	 * <p>The surface is one quad facing the camera at {@code z = -200}, or
	 * {@code z = -1000} for the fog variant, under the scene's projection and an
	 * identity modelview. The modelview is identity on purpose: the light's
	 * direction is what the toolkit uploaded, so the pixel is a function of the
	 * colours and strengths under test and not of a camera.
	 *
	 * <p>The materials are set explicitly - white ambient and diffuse, no specular,
	 * no colour material - so the fixed-function sum is exactly {@code ambient +
	 * diffuse * max(N.L, 0)} and the prediction can be arithmetic rather than
	 * empirical.
	 */
	private static int[] drawLitWorld(boolean fogged) {
		beginFrame();
		toolkit.ea(new JavaMatrix());
		loadProjection();
		// The fog colour first: UA is the per-frame setter the scene uses, and the
		// dimmed colour has to reach GL through it as the hour moves.
		toolkit.UA(FOG, 1000, 3000);
		toolkit.B(WORLD_AMBIENT);
		// Under the identity modelview, so the light lands in the frame the quad is
		// drawn in. The first call forces the upload, the second aims it.
		toolkit.scaleHeight(SUN, WORLD_SUN_INTENSITY, 0.0F, 0.0F, 1.0F, 0.0F);
		toolkit.scaleHeight(SUN, WORLD_SUN_INTENSITY, 0.0F, 0.0F, -0.342F, 0.9397F);
		assertTrue(toolkit.hdSkyFrame(), "the dome must draw under the world too");

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glDisable(GL11.GL_COLOR_MATERIAL);
		GL11.glMaterialfv(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE, floats(1.0F, 1.0F, 1.0F, 1.0F));
		GL11.glMaterialfv(GL11.GL_FRONT_AND_BACK, GL11.GL_SPECULAR, floats(0.0F, 0.0F, 0.0F, 1.0F));
		GL11.glMaterialf(GL11.GL_FRONT_AND_BACK, GL11.GL_SHININESS, 0.0F);
		GL11.glLightfv(GL11.GL_LIGHT0, GL11.GL_AMBIENT, floats(0.0F, 0.0F, 0.0F, 1.0F));
		GL11.glLightfv(GL11.GL_LIGHT0, GL11.GL_SPECULAR, floats(0.0F, 0.0F, 0.0F, 1.0F));
		GL11.glLightModeli(GL11.GL_LIGHT_MODEL_TWO_SIDE, GL11.GL_FALSE);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_LIGHT0);
		for (int light = GL11.GL_LIGHT1; light <= GL11.GL_LIGHT7; light++) {
			GL11.glDisable(light);
		}
		if (fogged) {
			GL11.glEnable(GL11.GL_FOG);
		} else {
			GL11.glDisable(GL11.GL_FOG);
		}
		float depth = fogged ? FOGGED_DEPTH : LIT_DEPTH;
		float extent = depth * 2.0F;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glNormal3f(0.0F, 0.0F, 1.0F);
		GL11.glVertex3f(-extent, -extent, -depth);
		GL11.glVertex3f(extent, -extent, -depth);
		GL11.glVertex3f(extent, extent, -depth);
		GL11.glVertex3f(-extent, extent, -depth);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glPopMatrix();
		return capture();
	}

	/**
	 * The lit surface's pixel as the toolkit's own factors predict it: the global
	 * ambient plus the sun, both dimmed, on a surface facing the light.
	 */
	private static int predictedWorldLight() {
		HdDayNight.Factors factors = HdDayNight.factors(HdSettings.isEnabled(),
			SkyGate.dayNightRequested(), -toolkit.sunDirection[1], HdSkyTime.phase());
		int colour = HdDayNight.scale(SUN, factors.sun());
		float facing = Math.max(toolkit.sunDirection[2], 0.0F);
		float level = WORLD_SUN_INTENSITY * facing + WORLD_AMBIENT * factors.ambient();
		int red = Math.round(HdSkyColours.clamp01(((colour >> 16) & 0xFF) / 255.0F * level) * 255.0F);
		int green = Math.round(HdSkyColours.clamp01(((colour >> 8) & 0xFF) / 255.0F * level) * 255.0F);
		int blue = Math.round(HdSkyColours.clamp01((colour & 0xFF) / 255.0F * level) * 255.0F);
		return red << 16 | green << 8 | blue;
	}

	/** GL's fog colour, which is what a fully fogged surface must read as. */
	private static float[] fogColour() {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
		GL11.glGetFloatv(GL11.GL_FOG_COLOR, buffer);
		return new float[] { buffer.get(0), buffer.get(1), buffer.get(2), buffer.get(3) };
	}

	private static void loadProjection() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		projection.put(toolkit.hdProjection()).flip();
		GL11.glLoadMatrixf(projection);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	private static FloatBuffer floats(float... values) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
		buffer.put(values).flip();
		return buffer;
	}

	/** One frame: clear the sentinel, draw the sky, read the pixels. */
	private static int[] drawSky() {
		beginFrame();
		toolkit.hdSkyFrame();
		return capture();
	}

	private static void beginFrame() {
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, framebuffer);
		GL11.glViewport(0, 0, SIZE, SIZE);
		GL11.glDisable(GL11.GL_DITHER);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glDisable(GL11.GL_LIGHTING);
		// A known transform for every frame: a test that leaves the scene's
		// matrices loaded must not change what the next frame's quads mean.
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glClearColor(1.0F, 0.0F, 1.0F, 1.0F);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		while (GL11.glGetError() != GL11.GL_NO_ERROR) {
			// Drain anything the previous frame left, so this frame's error is
			// its own.
		}
	}

	private static float[] sunDirection() {
		return new float[] { toolkit.sunDirection[0], toolkit.sunDirection[1], toolkit.sunDirection[2] };
	}

	/**
	 * Loads the scene's own matrices: the toolkit's projection, and the modelview
	 * {@code method1952} builds - {@code rotX(180) * camera}.
	 */
	private static void loadSceneMatrices() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		FloatBuffer projection = BufferUtils.createFloatBuffer(16);
		projection.put(toolkit.hdProjection()).flip();
		GL11.glLoadMatrixf(projection);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		FloatBuffer camera = BufferUtils.createFloatBuffer(16);
		camera.put(toolkit.aClass31_Sub1_4.method5161()).flip();
		GL11.glMultMatrixf(camera);
	}

	/** The centre of the magenta marker, or -1 when it is not in frame. */
	private static int markerCentroid(int[] frame) {
		long sumX = 0L;
		long sumY = 0L;
		int count = 0;
		for (int index = 0; index < frame.length; index++) {
			int pixel = frame[index];
			if (((pixel >> 16) & 0xFF) > 240 && (pixel & 0xFF) > 240 && ((pixel >> 8) & 0xFF) < 40) {
				sumX += index % SIZE;
				sumY += index / SIZE;
				count++;
			}
		}
		if (count == 0) {
			return -1;
		}
		return pack((int) Math.round((double) sumX / count), (int) Math.round((double) sumY / count));
	}

	/** The frame as packed {@code 0xRRGGBB}. */
	private static int[] capture() {
		GL11.glFinish();
		ByteBuffer pixels = BufferUtils.createByteBuffer(SIZE * SIZE * 4);
		GL11.glReadPixels(0, 0, SIZE, SIZE, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
		int[] frame = new int[SIZE * SIZE];
		for (int index = 0; index < frame.length; index++) {
			frame[index] = (pixels.get(index * 4) & 0xFF) << 16
				| (pixels.get(index * 4 + 1) & 0xFF) << 8
				| (pixels.get(index * 4 + 2) & 0xFF);
		}
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		return frame;
	}

	/**
	 * The frame the model predicts, from the same camera, projection and colours
	 * the toolkit hands the pass.
	 */
	private static int[] predict() {
		HdSkyColours colours = toolkit.hdSkyColours();
		assertTrue(colours != null && colours.sky(), "the rows must leave a sky to predict");
		float[] camera = toolkit.aClass31_Sub1_4.method5161().clone();
		float[] projection = toolkit.hdProjection();
		float[] sunDirection = { toolkit.sunDirection[0], toolkit.sunDirection[1], toolkit.sunDirection[2] };
		int[] predicted = new int[SIZE * SIZE];
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				float ndcX = (x + 0.5F) / SIZE * 2.0F - 1.0F;
				float ndcY = (y + 0.5F) / SIZE * 2.0F - 1.0F;
				float[] direction = SkyRaySpace.direction(camera, projection, ndcX, ndcY, null);
				float[] pixel = HdSkySource.shade(colours, sunDirection, direction, null);
				predicted[y * SIZE + x] = pack(pixel);
			}
		}
		return predicted;
	}

	/** The pixel the lighting sun projects to, in the frame's own coordinates. */
	private static int projectSun() {
		float[] camera = toolkit.aClass31_Sub1_4.method5161().clone();
		float[] projection = toolkit.hdProjection();
		float[] sunDirection = { toolkit.sunDirection[0], toolkit.sunDirection[1], toolkit.sunDirection[2] };
		float[] ndc = new float[2];
		assertTrue(SkyRaySpace.projectsTo(camera, projection, sunDirection, ndc),
			"the test's sun must be in frame");
		int x = Math.round((ndc[0] + 1.0F) / 2.0F * SIZE - 0.5F);
		int y = Math.round((ndc[1] + 1.0F) / 2.0F * SIZE - 0.5F);
		return pack(x, y);
	}

	/**
	 * The enables, depth state, program and matrices the pass shares with the
	 * scene, as strings so a mismatch prints both sides.
	 */
	private static String[] fingerprint() {
		return new String[] {
			"depthTest=" + GL11.glIsEnabled(GL11.GL_DEPTH_TEST),
			"blend=" + GL11.glIsEnabled(GL11.GL_BLEND),
			"cull=" + GL11.glIsEnabled(GL11.GL_CULL_FACE),
			"lighting=" + GL11.glIsEnabled(GL11.GL_LIGHTING),
			"fog=" + GL11.glIsEnabled(GL11.GL_FOG),
			"alphaTest=" + GL11.glIsEnabled(GL11.GL_ALPHA_TEST),
			"depthMask=" + GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK),
			"depthFunc=" + GL11.glGetInteger(GL11.GL_DEPTH_FUNC),
			"program=" + GL11.glGetInteger(0x8B8D),
			"projection=" + java.util.Arrays.toString(matrix(GL11.GL_PROJECTION_MATRIX)),
			"modelview=" + java.util.Arrays.toString(matrix(GL11.GL_MODELVIEW_MATRIX)),
			"attribDepth=" + GL11.glGetInteger(GL11.GL_ATTRIB_STACK_DEPTH)
		};
	}

	private static float[] matrix(int name) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		GL11.glGetFloatv(name, buffer);
		float[] values = new float[16];
		for (int index = 0; index < 16; index++) {
			values[index] = buffer.get(index);
		}
		return values;
	}

	// -- Reading frames ---------------------------------------------------

	private static int brightest(int[] frame) {
		int best = 0;
		int bestBrightness = -1;
		for (int index = 0; index < frame.length; index++) {
			int value = brightness(frame[index]);
			if (value > bestBrightness) {
				bestBrightness = value;
				best = index;
			}
		}
		return best;
	}

	private static int brightness(int pixel) {
		return (channel(pixel, 0) + channel(pixel, 1) + channel(pixel, 2)) / 3;
	}

	/** The frame's mean channel value: how bright the sky is overall. */
	private static int meanBrightness(int[] frame) {
		long total = 0L;
		for (int pixel : frame) {
			total += brightness(pixel);
		}
		return (int) (total / frame.length);
	}

	private static int channel(int pixel, int channel) {
		return (pixel >> (16 - channel * 8)) & 0xFF;
	}

	private static int litPixels(int[] frame) {
		int count = 0;
		for (int pixel : frame) {
			if (difference(pixel, CLEAR) > 0) {
				count++;
			}
		}
		return count;
	}

	/** A packed pixel compared channel by channel, so the report names both sides. */
	private static void assertPixel(int expected, int actual, int tolerance, String message) {
		assertTrue(difference(expected, actual) <= tolerance,
			message + ": expected " + hex(expected) + ", got " + hex(actual));
	}

	private static int difference(int first, int second) {
		int worst = 0;
		for (int shift = 0; shift <= 16; shift += 8) {
			worst = Math.max(worst, Math.abs(((first >> shift) & 0xFF) - ((second >> shift) & 0xFF)));
		}
		return worst;
	}

	private static int pack(float[] rgb) {
		int red = Math.round(HdSkyColours.clamp01(rgb[0]) * 255.0F);
		int green = Math.round(HdSkyColours.clamp01(rgb[1]) * 255.0F);
		int blue = Math.round(HdSkyColours.clamp01(rgb[2]) * 255.0F);
		return red << 16 | green << 8 | blue;
	}

	private static int pack(int x, int y) {
		return y * SIZE + x;
	}

	private static String describe(int index) {
		return "(" + index % SIZE + ", " + index / SIZE + ")";
	}

	private static String hex(int pixel) {
		return String.format("#%06X", Integer.valueOf(pixel));
	}

	// -- Target -----------------------------------------------------------

	private static void createTarget() {
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
		assertEquals(EXTFramebufferObject.GL_FRAMEBUFFER_COMPLETE_EXT, status, "offscreen target incomplete");
	}
}
