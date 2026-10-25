package com.jagex.game.runetek5.client.hd.water;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;

import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.buffer.ArrayBufferGL;
import com.jagex.game.runetek5.buffer.ElementsBufferGL;
import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.unpack_Sub1;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import jaggl.GlTestContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**
 * HD water on a real driver, proved by pixels.
 *
 * <p>The scene is one camera-facing water quad drawn through
 * {@code method1934}/{@code method1967} - the same calls the ground's water
 * layers make - with a flat-coloured texture on unit 0 so that nothing but the
 * surface normal can change the image. That is the whole point: with a uniform
 * texture and an identity texture matrix, a frame that moves when only the
 * clock advances is proof that the normal perturbation is running and driving
 * the shading, not the stock texture scroll. The quad fills the frustum, and
 * the sun is swept in small steps so the specular lobe lands inside the frame;
 * at a near-head-on sun the mirror direction falls on the quad's edge, where a
 * lobe would be indistinguishable from a frame-edge ramp.
 *
 * <p>What each proof is for:
 *
 * <ul>
 * <li>still vs rippled - amplitude zero must be identical across time, and the
 * shipped amplitude must not be;</li>
 * <li>the sun - tilting the sun moves the specular lobe to the other side of
 * the surface;</li>
 * <li>the row - turning it off must reproduce the stock frame byte for byte,
 * with the HD seam never reaching GL;</li>
 * <li>state - a water frame must leave every enable flag, the program binding,
 * the texture binding and the active unit exactly as it found them.</li>
 * </ul>
 */
@DisplayName("HD water on a real GL driver")
class HdWaterRealGlTest {

	private static final int SIZE = 256;

	private static final int STRIDE = 36;

	/** The water plane's half-extent in world units. */
	private static final float HALF = 5.0F;

	/** Environment cube size; sixteen is plenty for a solid colour. */
	private static final int CUBE_SIZE = 16;

	/**
	 * Material indices the stub provider answers for. The reflective one reports
	 * {@code aByte89 == 8}, which is the stock effect slot
	 * {@code ReflectiveRipplingWaterEffect} occupies; the breaking one reports
	 * slot 9 with the stock effect's own two parameter fields; the calm one
	 * reports the still-water slot.
	 */
	private static final int REFLECTIVE_MATERIAL = 900;

	private static final int CALM_MATERIAL = 901;

	private static final int BREAKING_MATERIAL = 902;

	/**
	 * The same breaking type on a shallower band: depth 32 instead of 64, a
	 * brighter crest pair (12/16, 3/16) and a sharper exponent (6 instead of 2),
	 * with the ripple scale and rate unchanged.
	 */
	private static final int BREAKING_SHALLOW_MATERIAL = 903;

	/**
	 * The breaking material's fields: scale 8/32 = 0.25 at rate 1, depth 64,
	 * shore offset 2/8, crest colour (8/16, 4/16) and exponent 2 - one layer
	 * over the stock {@code BreakingWaterEffect.setEffectParams} layout.
	 */
	private static final byte BREAKING_FIELDS = 24;

	private static final int BREAKING_PACKED = 64 | 2 << 16 | 4 << 19 | 8 << 23 | 2 << 27;

	private static final int BREAKING_SHALLOW_PACKED = 32 | 2 << 16 | 3 << 19 | 12 << 23 | 6 << 27;

	/** Depth range across the quad: the stock per-vertex water depth is 0..N. */
	private static final float DEPTH_MAX = 96.0F;

	private static final int DEPTH_STRIDE = 40;

	/**
	 * A surface normal tilted toward the eye: Fresnel needs a grazing-ish angle
	 * for the reflection to be visible at all, which is also what makes the
	 * reflection a reflection and not a tint.
	 */
	private static final float TILTED_Y = 0.94F;

	private static final float TILTED_Z = 0.34F;

	private static GlToolkit toolkit;

	private static Frame frame;

	private static Canvas canvas;

	private static int framebuffer;

	private static int targetTexture;

	private static int depthBuffer;

	private static ElementsBufferGL planeBuffer;

	private static ArrayBufferGL planeIndices;

	private static Class177 planeVertices;

	private static Class177 planeColours;

	private static Class177 planeNormals;

	private static Class177 planeTexCoords;

	private static ElementsBufferGL tiltedBuffer;

	private static ArrayBufferGL tiltedIndices;

	private static Class177 tiltedVertices;

	private static Class177 tiltedColours;

	private static Class177 tiltedNormals;

	private static Class177 tiltedTexCoords;

	private static int waterTexture;

	/** A two-colour checker, so the material's ripple scale is visible at all. */
	private static int checkerTexture;

	private static ElementsBufferGL depthPlaneBuffer;

	private static ArrayBufferGL depthIndices;

	private static Class177 depthVertices;

	private static Class177 depthColours;

	private static Class177 depthNormals;

	private static Class177 depthTexCoords;

	/**
	 * The two environments the reflective proof swaps between. Neither is red:
	 * this harness sets the fog colour to red, and the calm program's sheen is
	 * the fog colour, so a red cube would make calm and reflective water agree
	 * by construction instead of proving anything.
	 */
	private static GlCubeTexture blueEnvironment;

	private static GlCubeTexture greenEnvironment;

	/** Stands in for the client's sun sampler; {@code method1946()} reads it. */
	private static TestEnvironment environment;

	@BeforeAll
	static void openToolkit() throws Exception {
		try {
			GlTestContext warmup = GlTestContext.open();
			warmup.close();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}
		frame = new Frame("592HD water test");
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(SIZE, SIZE));
		frame.add(canvas);
		frame.pack();
		frame.setLocation(-4000, -4000);
		frame.setVisible(true);
		try {
			toolkit = new GlToolkit(0, canvas, new TestMaterials(), 0, null);
		} catch (Throwable error) {
			frame.dispose();
			assumeTrue(false, "GlToolkit could not start in this JVM: " + error);
		}
		System.out.println("[hd-water] toolkit " + GL11.glGetString(GL11.GL_RENDERER));

		createTarget();
		createTexture();
		planeBuffer = plane(0.0F, 0.0F, 1.0F);
		planeVertices = new Class177(planeBuffer, GL11.GL_FLOAT, 3, 0);
		planeColours = new Class177(planeBuffer, GL11.GL_UNSIGNED_BYTE, 4, 12);
		planeNormals = new Class177(planeBuffer, GL11.GL_FLOAT, 3, 16);
		planeTexCoords = new Class177(planeBuffer, GL11.GL_FLOAT, 2, 28);
		planeIndices = indices();
		tiltedBuffer = plane(0.0F, TILTED_Y, TILTED_Z);
		tiltedVertices = new Class177(tiltedBuffer, GL11.GL_FLOAT, 3, 0);
		tiltedColours = new Class177(tiltedBuffer, GL11.GL_UNSIGNED_BYTE, 4, 12);
		tiltedNormals = new Class177(tiltedBuffer, GL11.GL_FLOAT, 3, 16);
		tiltedTexCoords = new Class177(tiltedBuffer, GL11.GL_FLOAT, 2, 28);
		tiltedIndices = indices();
		depthPlaneBuffer = depthPlane();
		depthVertices = new Class177(depthPlaneBuffer, GL11.GL_FLOAT, 3, 0);
		depthColours = new Class177(depthPlaneBuffer, GL11.GL_UNSIGNED_BYTE, 4, 12);
		depthNormals = new Class177(depthPlaneBuffer, GL11.GL_FLOAT, 3, 16);
		depthTexCoords = new Class177(depthPlaneBuffer, GL11.GL_FLOAT, 3, 28);
		depthIndices = indices();

		checkerTexture = checker();

		blueEnvironment = cube(0, 0, 255);
		greenEnvironment = cube(0, 255, 0);
		environment = new TestEnvironment(blueEnvironment);
		toolkit.setSunCubeTexture(environment);

		Path preferences = Files.createTempDirectory("hd-water");
		HdSettings.setDirectoryForTest(preferences.toFile());
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.WATER_HD, 1);
		toolkit.method1900(1);
		toolkit.method1906(true);
	}

	/** Each proof starts from the shipped settings, whatever the previous one toggled. */
	@BeforeEach
	void resetWater() {
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.WATER_HD, 1);
		toolkit.hdWaterFlowForTest(HdWaterPipeline.DEFAULT_FLOW);
		HdWaterTime.setForTest(0.0F);
		environment.cube = blueEnvironment;
	}

	@AfterAll
	static void closeToolkit() {
		HdSettings.resetForTest();
		HdWaterTime.reset();
		if (frame != null) {
			frame.dispose();
		}
	}

	@Test
	@DisplayName("the surface is still at zero amplitude and moves at the shipped one")
	void flow_shouldBeTheOnlyThingThatMoves() {
		HdWaterTime.setForTest(0.0F);
		toolkit.hdWaterFlowForTest(0.0F);
		int[] stillFirst = waterFrame(0.0F, 0.0F);

		toolkit.hdWaterFlowForTest(0.0F);
		int[] stillLater = waterFrame(0.0F, 0.0F);

		toolkit.hdWaterFlowForTest(HdWaterPipeline.DEFAULT_FLOW);
		int[] rippledFirst = waterFrame(0.0F, 0.0F);
		int[] rippledLater = waterFrame(1.5F, 0.0F);

		int stillDiff = differing(stillFirst, stillLater);
		int rippleDiff = differing(rippledFirst, rippledLater);
		System.out.println("[hd-water] amplitude 0 across time differs in " + stillDiff
			+ " px; shipped amplitude differs in " + rippleDiff + " px");
		assertAll(
			() -> assertEquals(0, stillDiff, "a flat surface must not animate"),
			() -> assertTrue(rippleDiff > SIZE / 2, "the shipped surface must move: " + rippleDiff)
		);
	}

	@Test	@DisplayName("the specular lobe is a peak on the surface and it tracks the sun")
	void specular_shouldFollowTheSun() {
		// Amplitude zero: the lobe must move because of the sun, not a wave.
		toolkit.hdWaterFlowForTest(0.0F);

		int[] peaks = new int[SUN_SWEEP.length];
		for (int i = 0; i < SUN_SWEEP.length; i++) {
			peaks[i] = brightestColumn(waterFrame(0.0F, SUN_SWEEP[i]));
		}
		System.out.println("[hd-water] highlight column per sun " + java.util.Arrays.toString(peaks));

		// The lobe is a local maximum on the surface, not a ramp that happens to
		// end at the brightest frame edge: it is interior and falls away on both
		// sides within the frame.
		int[] leftFrame = waterFrame(0.0F, SUN_SWEEP[0]);
		int[] rightFrame = waterFrame(0.0F, SUN_SWEEP[SUN_SWEEP.length - 1]);
		int left = brightestColumn(leftFrame);
		int right = brightestColumn(rightFrame);
		assertAll(
			() -> assertTrue(left > SIZE / 8 && left < SIZE / 2,
				"a sun on the left puts an interior peak on the left: " + left),
			() -> assertTrue(right > SIZE / 2 && right < SIZE * 7 / 8,
				"and a sun on the right puts one on the right: " + right),
			() -> assertTrue(isLobe(leftFrame, left), "the left highlight falls off on both sides"),
			() -> assertTrue(isLobe(rightFrame, right), "the right highlight falls off on both sides"),
			// Every step of the sweep moves the lobe the same way, so the highlight
			// responds to the sun's direction and not just to its presence.
			() -> assertTrue(isIncreasing(peaks), "the highlight follows the sun across the sweep: "
				+ java.util.Arrays.toString(peaks)),
			// Equal sun steps move the lobe equally far: the highlight is the
			// mirror direction, not a brightness ramp that happens to peak late.
			// The peak is a whole pixel column, so an 8-bit lobe about 21 px wide
			// can tie between neighbouring columns and jitter the step by two.
			() -> assertTrue(spreadOfSteps(peaks) <= 4, "equal sun steps move the lobe equally: "
				+ java.util.Arrays.toString(peaks))
		);
	}

	@Test
	@DisplayName("water on, then off again, lands back on the stock frame byte for byte")
	void rowOff_shouldMatchTheStockFrame() {
		// The reference is taken before the program has ever been bound, so this is
		// a round trip - pristine stock, HD draw, stock again - and not just two
		// runs of the same code path.
		HdSettings.set(HdFeature.WATER_HD, 0);
		long bindsBefore = toolkit.hdWaterBinds();
		int[] stock = stockWaterFrame(0.0F);
		long bindsWhileOff = toolkit.hdWaterBinds();

		HdSettings.set(HdFeature.WATER_HD, 1);
		toolkit.hdWaterFlowForTest(HdWaterPipeline.DEFAULT_FLOW);
		int[] hd = waterFrame(0.7F, 0.0F);
		long bindsWhileOn = toolkit.hdWaterBinds();

		HdSettings.set(HdFeature.WATER_HD, 0);
		int[] restored = stockWaterFrame(0.0F);

		int diff = differing(stock, restored);
		int changedByHd = differing(stock, hd);
		int brightest = brightestChannel(stock);
		System.out.println("[hd-water] stock vs restored differ in " + diff
			+ " px; HD draws while off: " + (bindsWhileOff - bindsBefore)
			+ "; HD draws while on: " + (bindsWhileOn - bindsWhileOff)
			+ "; HD changed " + changedByHd + " px; stock frame peak " + brightest
			+ ", centre " + pixel(stock, SIZE / 2, SIZE / 2));
		assertAll(
			() -> assertEquals(0, diff, "turning the row off must return the exact stock frame"),
			() -> assertEquals(0L, bindsWhileOff - bindsBefore, "the HD seam must not reach GL while off"),
			() -> assertTrue(bindsWhileOn > bindsWhileOff, "the row on must reach the HD program"),
			() -> assertTrue(changedByHd > SIZE, "and it must change the image in between: " + changedByHd),
			// Anti-vacuity guard, not a look claim: two black frames would compare
			// equal too, and that would prove nothing about the stock path.
			() -> assertTrue(brightest > 20, "the stock frame drew something, so the comparison means something: "
				+ brightest)
		);
	}

	@Test
	@DisplayName("a water frame leaves no GL state behind")
	void water_shouldNotLeakState() {
		int[] before = captureState();
		waterFrame(0.6F, 0.0F);
		int[] after = captureState();

		// GL_CURRENT_PROGRAM (0x8B8D) is not exposed by this LWJGL binding.
		int program = GL11.glGetInteger(0x8B8D);
		System.out.println("[hd-water] caps before " + before[0] + " after " + after[0]
			+ ", program after " + program);
		assertAll(
			() -> assertEquals(before[0], after[0], "enable flags"),
			() -> assertEquals(before[1], after[1], "active texture unit"),
			() -> assertEquals(before[2], after[2], "texture binding on unit 0"),
			() -> assertEquals(before[3], after[3], "framebuffer binding"),
			() -> assertEquals(0, program, "returns to fixed function"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("the scene's own water pass binds the HD program")
	void sceneWaterPass_shouldReachTheHdProgram() {
		HdSettings.set(HdFeature.WATER_HD, 1);
		long before = toolkit.hdWaterBinds();
		int[] frame = sceneWaterFrame(0.7F, -1);
		long drawn = toolkit.hdWaterBinds() - before;

		int[] stock = sceneWaterFrameStock(0.0F, -1);
		int changed = differing(stock, frame);
		System.out.println("[hd-water] scene pass: HD draws " + drawn + "; changed " + changed
			+ " px; centre " + pixel(frame, SIZE / 2, SIZE / 2)
			+ "; distinct colours " + distinctColours(frame));
		assertAll(
			() -> assertTrue(drawn > 0L, "the scene's AA pass must bind the HD water program, drew " + drawn),
			() -> assertTrue(changed > SIZE, "and the scene frame must change with the row on: " + changed),
			() -> assertTrue(distinctColours(frame) > 64,
				"the surface must not be one flat fog colour: " + distinctColours(frame))
		);
	}

	@Test
	@DisplayName("the HD draw is a different image from the stock one")
	void hd_shouldDifferFromStock() {
		int[] stock = stockWaterFrame(0.0F);
		int[] hd = waterFrame(0.0F, 0.0F);

		int diff = differing(stock, hd);
		System.out.println("[hd-water] HD vs stock differ in " + diff + " px; centre stock "
			+ pixel(stock, SIZE / 2, SIZE / 2) + ", HD " + pixel(hd, SIZE / 2, SIZE / 2)
			+ "; edge stock " + pixel(stock, 20, SIZE / 2) + ", HD " + pixel(hd, 20, SIZE / 2));
		assertAll(
			() -> assertTrue(brightestChannel(stock) > 20, "the stock water slot drew something"),
			() -> assertTrue(brightestChannel(hd) > 20, "the HD program drew something"),
			() -> assertTrue(diff > SIZE, "the HD program must change the water, not reproduce it: " + diff)
		);
	}

	@Test
	@DisplayName("the custom value is a third image, and switching back is the standard one again")
	void custom_shouldBeAThirdSurface() {
		HdSettings.set(HdFeature.WATER_HD, 1);
		long customsBefore = toolkit.hdWaterCustomBinds();
		int[] standard = waterFrame(0.5F, 0.0F);

		HdSettings.set(HdFeature.WATER_HD, 2);
		int[] custom = waterFrame(0.5F, 0.0F);
		long customsAfter = toolkit.hdWaterCustomBinds();

		HdSettings.set(HdFeature.WATER_HD, 1);
		int[] restored = waterFrame(0.5F, 0.0F);

		int changed = differing(standard, custom);
		int roundTrip = differing(standard, restored);
		System.out.println("[hd-water] custom vs standard differ in " + changed + " px; standard again differs in "
			+ roundTrip + " px; custom draws " + (customsAfter - customsBefore)
			+ "; centre standard " + pixel(standard, SIZE / 2, SIZE / 2)
			+ ", custom " + pixel(custom, SIZE / 2, SIZE / 2));
		assertAll(
			// Anti-vacuity: both frames drew water, so the comparison means something.
			() -> assertTrue(brightestChannel(standard) > 20, "the standard surface drew: "
				+ brightestChannel(standard)),
			() -> assertTrue(brightestChannel(custom) > 20, "the custom surface drew: "
				+ brightestChannel(custom)),
			() -> assertTrue(changed > SIZE, "the custom surface must change the water: " + changed),
			// The style is a program selection, not a one-way switch: On is exactly
			// the image it was before the custom frame.
			() -> assertEquals(0, roundTrip, "switching back to On restores the standard image byte for byte"),
			() -> assertTrue(customsAfter - customsBefore >= 1L,
				"the custom program drew the frame: " + (customsAfter - customsBefore))
		);
	}

	@Test
	@DisplayName("reflective water samples the environment cube, and follows it when the sky changes")
	void reflective_shouldFollowTheEnvironment() {
		// A flat surface: the only term that can differ between these frames is
		// where the Fresnel sheen comes from.
		toolkit.hdWaterFlowForTest(0.0F);

		environment.cube = blueEnvironment;
		int[] calmBlue = waterFrameWithMaterial(0.0F, 0.0F, -1);
		int[] reflectiveBlue = waterFrameWithMaterial(0.0F, 0.0F, REFLECTIVE_MATERIAL);

		environment.cube = greenEnvironment;
		int[] calmGreen = waterFrameWithMaterial(0.0F, 0.0F, -1);
		int[] reflectiveGreen = waterFrameWithMaterial(0.0F, 0.0F, REFLECTIVE_MATERIAL);

		int calmDiff = differing(calmBlue, calmGreen);
		int reflectionDiff = differing(calmBlue, reflectiveBlue);
		System.out.println("[hd-water] reflective vs calm differ in " + reflectionDiff
			+ " px; calm frames across a sky change differ in " + calmDiff
			+ " px; calm avg rgb(" + Math.round(meanChannel(calmBlue, 0)) + ","
			+ Math.round(meanChannel(calmBlue, 1)) + "," + Math.round(meanChannel(calmBlue, 2))
			+ "); blue sky reflect avg rgb(" + Math.round(meanChannel(reflectiveBlue, 0)) + ","
			+ Math.round(meanChannel(reflectiveBlue, 1)) + "," + Math.round(meanChannel(reflectiveBlue, 2))
			+ "); green sky reflect avg rgb(" + Math.round(meanChannel(reflectiveGreen, 0)) + ","
			+ Math.round(meanChannel(reflectiveGreen, 1)) + "," + Math.round(meanChannel(reflectiveGreen, 2))
			+ "); reflective draws " + toolkit.hdWaterReflectiveBinds());
		assertAll(
			// The calm program has no cube sampler at all, so a repainted sky
			// cannot touch it: this is the still-water guarantee, measured.
			() -> assertEquals(0, calmDiff, "the calm image must not depend on the environment cube"),
			() -> assertTrue(reflectionDiff > SIZE, "the reflection changes the water: " + reflectionDiff),
			() -> assertTrue(meanChannel(reflectiveBlue, 2) > meanChannel(reflectiveGreen, 2) + 5.0,
				"a blue sky paints the blue channel: " + meanChannel(reflectiveBlue, 2)
					+ " vs " + meanChannel(reflectiveGreen, 2)),
			() -> assertTrue(meanChannel(reflectiveGreen, 1) > meanChannel(reflectiveBlue, 1) + 5.0,
				"a green sky paints the green channel: " + meanChannel(reflectiveGreen, 1)
					+ " vs " + meanChannel(reflectiveBlue, 1)),
			() -> assertTrue(toolkit.hdWaterReflectiveBinds() >= 2L,
				"both reflective draws took the cube path: " + toolkit.hdWaterReflectiveBinds())
		);
	}

	@Test
	@DisplayName("a calm water material keeps the calm image exactly")
	void calmMaterial_shouldStayOnTheCalmProgram() {
		int[] noMaterial = waterFrameWithMaterial(0.4F, 0.0F, -1);
		int[] calmMaterial = waterFrameWithMaterial(0.4F, 0.0F, CALM_MATERIAL);

		assertEquals(0, differing(noMaterial, calmMaterial),
			"a material with the still-water slot must not move a single pixel");
	}

	@Test
	@DisplayName("a reflective material with the row off is the stock frame, byte for byte, and never reaches GL")
	void reflectiveRowOff_shouldBeTheStockFrame() {
		HdSettings.set(HdFeature.WATER_HD, 0);
		long bindsBefore = toolkit.hdWaterBinds();
		int[] stock = stockWaterFrame(0.0F);
		int[] withReflectiveMaterial = stockWaterFrameWithMaterial(0.0F, REFLECTIVE_MATERIAL);

		int diff = differing(stock, withReflectiveMaterial);
		System.out.println("[hd-water] row off, reflective material bound: " + diff
			+ " px from stock; HD draws " + (toolkit.hdWaterBinds() - bindsBefore));
		assertAll(
			() -> assertEquals(0, diff, "Off is the stock draw even when the material is reflective"),
			() -> assertEquals(0L, toolkit.hdWaterBinds() - bindsBefore, "the seam must not reach GL while off")
		);
	}

	@Test
	@DisplayName("a reflective draw leaves the environment unit and the program as it found them")
	void reflective_shouldNotLeakState() {
		int programBefore = GL11.glGetInteger(0x8B8D);
		boolean cubeEnabledBefore = cubeEnabledOnUnit1();
		int activeBefore = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE);

		int[] frame = waterFrameWithMaterial(0.0F, 0.0F, REFLECTIVE_MATERIAL);
		assertTrue(brightestChannel(frame) > 0, "the reflective draw drew something");

		assertAll(
			() -> assertEquals(cubeEnabledBefore, cubeEnabledOnUnit1(), "unit 1's cube target"),
			() -> assertEquals(activeBefore, GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE), "active texture unit"),
			() -> assertEquals(programBefore, GL11.glGetInteger(0x8B8D), "returns to fixed function"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("breaking water draws a band the calm and reflective surfaces do not have")
	void breaking_shouldDifferFromCalmAndReflective() {
		long breakingBefore = toolkit.hdWaterBreakingBinds();
		int[] calm = waterFrameAtDepth(0.0F, -1);
		int[] reflective = waterFrameAtDepth(0.0F, REFLECTIVE_MATERIAL);
		int[] breaking = waterFrameAtDepth(0.0F, BREAKING_MATERIAL);
		long breakingDrawn = toolkit.hdWaterBreakingBinds() - breakingBefore;

		int fromCalm = differing(calm, breaking);
		int fromReflective = differing(reflective, breaking);
		int calmFromReflective = differing(calm, reflective);
		System.out.println("[hd-water] breaking vs calm " + fromCalm + " px, vs reflective " + fromReflective
			+ " px; calm vs reflective " + calmFromReflective + " px; breaking draws " + breakingDrawn
			+ "; centre breaking " + pixel(breaking, SIZE / 2, SIZE / 2)
			+ ", reflective " + pixel(reflective, SIZE / 2, SIZE / 2));
		assertAll(
			// Anti-vacuity: every one of the three frames drew water.
			() -> assertTrue(brightestChannel(calm) > 20, "calm drew: " + brightestChannel(calm)),
			() -> assertTrue(brightestChannel(reflective) > 20, "reflective drew: " + brightestChannel(reflective)),
			() -> assertTrue(brightestChannel(breaking) > 20, "breaking drew: " + brightestChannel(breaking)),
			// The breaking band is the material's, not the cube's or the fog's.
			() -> assertTrue(fromCalm > SIZE, "breaking differs from calm: " + fromCalm),
			() -> assertTrue(fromReflective > SIZE, "breaking differs from reflective: " + fromReflective),
			() -> assertTrue(calmFromReflective > SIZE, "and the reflective frame is a cube image: "
				+ calmFromReflective),
			() -> assertTrue(breakingDrawn >= 1L, "the breaking program drew the frame: " + breakingDrawn)
		);
	}

	@Test
	@DisplayName("the breaking band follows the clock and the material's own numbers")
	void breaking_shouldFollowTheClockAndTheMaterial() {
		int[] first = waterFrameAtDepth(0.0F, BREAKING_MATERIAL);
		int[] later = waterFrameAtDepth(1.2F, BREAKING_MATERIAL);
		int[] shallow = waterFrameAtDepth(0.0F, BREAKING_SHALLOW_MATERIAL);

		int moved = differing(first, later);
		int rescaled = differing(first, shallow);
		System.out.println("[hd-water] breaking across 1.2 s differs in " + moved
			+ " px; the shallower band on the same geometry differs in " + rescaled + " px");
		assertAll(
			() -> assertTrue(moved > SIZE / 2, "the band and the waves move with the clock: " + moved),
			() -> assertTrue(rescaled > SIZE / 2,
				"a different band from the same material type is a different image: " + rescaled)
		);
	}

	@Test
	@DisplayName("with the row off a breaking material is the stock frame and never reaches the breaking program")
	void breakingRowOff_shouldBeTheStockFrame() {
		HdSettings.set(HdFeature.WATER_HD, 0);
		long bindsBefore = toolkit.hdWaterBinds();
		long breakingBefore = toolkit.hdWaterBreakingBinds();
		int[] noMaterial = waterFrameAtDepth(0.0F, -1);
		int[] breaking = waterFrameAtDepth(0.0F, BREAKING_MATERIAL);

		int diff = differing(noMaterial, breaking);
		System.out.println("[hd-water] row off, breaking material bound: " + diff
			+ " px from the stock frame; HD draws " + (toolkit.hdWaterBinds() - bindsBefore));
		assertAll(
			() -> assertEquals(0, diff, "Off leaves the frame exactly as the stock material state drew it"),
			() -> assertEquals(0L, toolkit.hdWaterBinds() - bindsBefore, "the seam must not reach GL while off"),
			() -> assertEquals(0L, toolkit.hdWaterBreakingBinds() - breakingBefore,
				"and never reaches the breaking program")
		);
	}

	@Test
	@DisplayName("a breaking draw leaves the environment unit and the program as it found them")
	void breaking_shouldNotLeakState() {
		int programBefore = GL11.glGetInteger(0x8B8D);
		boolean cubeEnabledBefore = cubeEnabledOnUnit1();
		int activeBefore = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE);

		int[] frame = waterFrameAtDepth(0.0F, BREAKING_MATERIAL);
		assertTrue(brightestChannel(frame) > 0, "the breaking draw drew something");

		assertAll(
			() -> assertEquals(cubeEnabledBefore, cubeEnabledOnUnit1(), "unit 1's cube target"),
			() -> assertEquals(activeBefore, GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE), "active texture unit"),
			() -> assertEquals(programBefore, GL11.glGetInteger(0x8B8D), "returns to fixed function"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	// -- Scene helpers ----------------------------------------------------

	/**
	 * One frame: the water plane via the HD seam, timed at {@code seconds}.
	 * Returns the RGBA frame so callers can compare pixels.
	 */
	private static int[] waterFrame(float seconds, float sunX) {
		HdWaterTime.setForTest(seconds);
		beginFrame(sunX);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, waterTexture);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		boolean bound = toolkit.hdBeginWater();
		if (!bound) {
			// The stock material state a model/ground draw sets before submitting:
			// without it fixed function has no material and draws black, which
			// would make every stock comparison vacuous.
			toolkit.method1938(-1, true);
		}
		drawPlane();
		if (bound) {
			toolkit.hdEndWater();
		}
		finishFrame();
		return readFrame();
	}

	/**
	 * One frame through the stock water effect slot - {@code AA} selects it,
	 * {@code l()} ends it - exactly the calls the scene makes for a water region.
	 */
	private static int[] stockWaterFrame(float sunX) {
		return stockWaterFrameWithMaterial(sunX, -1);
	}

	/**
	 * One frame through the scene's own water seam: {@code AA} opens the pass with
	 * the stock water-mode fog state, the row decides whether the HD program
	 * takes it, and {@code l()} closes it - exactly the calls {@code Class62}
	 * makes for a water region.
	 */
	private static int[] sceneWaterFrame(float seconds, int material) {
		HdWaterTime.setForTest(seconds);
		beginFrame(0.0F);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, waterTexture);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		toolkit.AA(-1, 1583160, 40, 127);
		toolkit.method1938(-1, true);
		if (material >= 0) {
			toolkit.hdWaterMaterial(material);
		}
		drawPlane();
		toolkit.l();
		finishFrame();
		return readFrame();
	}

	/** The same scene pass with the row off, which is the stock image. */
	private static int[] sceneWaterFrameStock(float seconds, int material) {
		HdSettings.set(HdFeature.WATER_HD, 0);
		try {
			return sceneWaterFrame(seconds, material);
		} finally {
			HdSettings.set(HdFeature.WATER_HD, 1);
		}
	}

	/** Pixels of distinct RGB triples; a flat fill has one. */
	private static int distinctColours(int[] frame) {
		java.util.HashSet<Integer> colours = new java.util.HashSet<Integer>();
		for (int i = 0; i < frame.length; i += 4) {
			colours.add(Integer.valueOf(frame[i] << 16 | frame[i + 1] << 8 | frame[i + 2]));
		}
		return colours.size();
	}

	/**
	 * The stock water slot with a material's effect type applied, as the scene
	 * would. The row is turned off for the frame and restored afterwards: this
	 * is the reference the HD image is measured against, so it must not itself
	 * bind the HD program.
	 */
	private static int[] stockWaterFrameWithMaterial(float sunX, int material) {
		int row = HdSettings.value(HdFeature.WATER_HD);
		HdSettings.set(HdFeature.WATER_HD, 0);
		try {
			beginFrame(sunX);
			toolkit.AA(-1, 1583160, 40, 127);
			toolkit.method1938(-1, true);
			if (material >= 0) {
				toolkit.hdWaterMaterial(material);
			}
			drawPlane();
			toolkit.l();
			finishFrame();
			return readFrame();
		} finally {
			HdSettings.set(HdFeature.WATER_HD, row);
		}
	}

	/**
	 * One HD water frame whose material is the given stock effect type; -1 means
	 * the region's own calm state, which is what a water region starts with.
	 */
	private static int[] waterFrameWithMaterial(float seconds, float sunX, int material) {
		HdWaterTime.setForTest(seconds);
		beginFrame(sunX);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, waterTexture);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		boolean bound = toolkit.hdBeginWater();
		assertTrue(bound, "HD water must be available for these frames");
		if (material >= 0) {
			toolkit.hdWaterMaterial(material);
		}
		drawTiltedPlane();
		toolkit.hdEndWater();
		finishFrame();
		return readFrame();
	}

	/**
	 * One HD water frame of the depth-ramped quad, with the stock style's own
	 * per-vertex depth (the z texture coordinate) varying across it, and a
	 * checker on unit 0 so a material's ripple scale is visible. A row that is
	 * off draws the same quad under the stock material state, so the caller can
	 * compare the two paths on one geometry.
	 */
	private static int[] waterFrameAtDepth(float seconds, int material) {
		HdWaterTime.setForTest(seconds);
		beginFrame(0.0F);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, checkerTexture);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		boolean bound = toolkit.hdBeginWater();
		if (bound) {
			if (material >= 0) {
				toolkit.hdWaterMaterial(material);
			}
			drawDepthPlane();
			toolkit.hdEndWater();
		} else {
			toolkit.method1938(-1, true);
			drawDepthPlane();
		}
		finishFrame();
		return readFrame();
	}

	private static void drawDepthPlane() {
		toolkit.method1934(depthColours, depthNormals, depthTexCoords, depthVertices);
		toolkit.method1967(0, 6, depthIndices);
	}

	private static void drawPlane() {
		toolkit.method1934(planeColours, planeNormals, planeTexCoords, planeVertices);
		toolkit.method1967(0, 6, planeIndices);
	}

	/** Same quad, with the tilted normal the reflection proof needs. */
	private static void drawTiltedPlane() {
		toolkit.method1934(tiltedColours, tiltedNormals, tiltedTexCoords, tiltedVertices);
		toolkit.method1967(0, 6, tiltedIndices);
	}

	/** Mean of one channel over the frame; the background is black in all frames. */
	private static double meanChannel(int[] frame, int channel) {
		long total = 0L;
		for (int i = channel; i < frame.length; i += 4) {
			total += frame[i];
		}
		return (double) total / (double) (frame.length / 4);
	}

	/** True while the cube target is enabled on unit 1, which is the state HD water owns. */
	private static boolean cubeEnabledOnUnit1() {
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		boolean enabled = GL11.glIsEnabled(GL13.GL_TEXTURE_CUBE_MAP);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		return enabled;
	}

	private static void beginFrame(float sunX) {
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, framebuffer);
		GL11.glViewport(0, 0, SIZE, SIZE);
		GL11.glDisable(GL11.GL_DITHER);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		// +-5 world units across the viewport at the plane, so the whole quad fits.
		GL11.glFrustum(-0.25D, 0.25D, -0.25D, 0.25D, 1.0D, 100.0D);
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -20.0F);

		GL11.glEnable(GL11.GL_LIGHTING);
		// A mid-grey sun keeps the surface short of clipping, so a shading change
		// is a pixel change rather than a saturating highlight.
		toolkit.B(0.1F);
		toolkit.scaleHeight(0x808080, 1.0F, 0.0F, sunX, 0.0F, 0.97F);
		toolkit.UA(0xFF0000, 1, 0);
		toolkit.method1918(true);
		toolkit.copyLights(0, new com.jagex.game.runetek5.cache.node.PointLight[0]);
	}

	private static void finishFrame() {
		GL11.glFinish();
		assertEquals(GL11.GL_NO_ERROR, GL11.glGetError(), "GL error while drawing");
	}

	private static int[] readFrame() {
		ByteBuffer pixels = BufferUtils.createByteBuffer(SIZE * SIZE * 4);
		GL11.glReadPixels(0, 0, SIZE, SIZE, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
		int[] values = new int[SIZE * SIZE * 4];
		for (int i = 0; i < values.length; i++) {
			values[i] = pixels.get(i) & 0xFF;
		}
		return values;
	}

	/** Pixels (not channels) whose red changed by more than one step. */
	private static int differing(int[] first, int[] second) {
		int count = 0;
		for (int i = 0; i < first.length; i += 4) {
			if (Math.abs(first[i] - second[i]) > 1) {
				count++;
			}
		}
		return count;
	}

	/** The brightest channel anywhere in the frame. */
	private static int brightestChannel(int[] frame) {
		int best = 0;
		for (int i = 0; i < frame.length; i += 4) {
			for (int channel = 0; channel < 3; channel++) {
				if (frame[i + channel] > best) {
					best = frame[i + channel];
				}
			}
		}
		return best;
	}

	private static String pixel(int[] frame, int x, int y) {
		int index = (y * SIZE + x) * 4;
		return "rgb(" + frame[index] + "," + frame[index + 1] + "," + frame[index + 2] + ")";
	}

	/** Sun x offsets swept by the specular test; they keep the lobe inside the frame. */
	private static final float[] SUN_SWEEP = { -0.16F, -0.12F, -0.08F, -0.04F, 0.0F, 0.04F, 0.08F, 0.12F, 0.16F };

	/** True when every entry is strictly greater than the one before it. */
	private static boolean isIncreasing(int[] values) {
		for (int i = 1; i < values.length; i++) {
			if (values[i] <= values[i - 1]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * True when the highlight at {@code column} is a peak: the middle row 48 px
	 * either side of it is darker. The margin on the sunward side is small
	 * because the diffuse term rises there; a ramp would still fail one side.
	 */
	private static boolean isLobe(int[] frame, int column) {
		int row = SIZE / 2;
		int peak = frame[(row * SIZE + column) * 4];
		int left = frame[(row * SIZE + Math.max(0, column - 48)) * 4];
		int right = frame[(row * SIZE + Math.min(SIZE - 1, column + 48)) * 4];
		return peak - left > 5 && peak - right > 5;
	}

	/** Difference between the largest and smallest step in a monotonically rising series. */
	private static int spreadOfSteps(int[] values) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i = 1; i < values.length; i++) {
			int step = values[i] - values[i - 1];
			min = Math.min(min, step);
			max = Math.max(max, step);
		}
		return max - min;
	}

	/** The column of the brightest pixel, which is where the sun's lobe sits. */
	private static int brightestColumn(int[] frame) {
		int best = -1;
		int column = -1;
		for (int i = 0; i < frame.length; i += 4) {
			int value = frame[i] + frame[i + 1] + frame[i + 2];
			if (value > best) {
				best = value;
				column = (i / 4) % SIZE;
			}
		}
		return column;
	}

	private static int[] captureState() {
		int[] caps = { GL11.GL_LIGHTING, GL11.GL_TEXTURE_2D, GL11.GL_DEPTH_TEST, GL11.GL_BLEND,
			GL11.GL_ALPHA_TEST, GL11.GL_CULL_FACE, GL11.GL_FOG };
		int mask = 0;
		for (int i = 0; i < caps.length; i++) {
			if (GL11.glIsEnabled(caps[i])) {
				mask |= 1 << i;
			}
		}
		int active = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE);
		int texture = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
		int binding = GL11.glGetInteger(EXTFramebufferObject.GL_FRAMEBUFFER_BINDING_EXT);
		return new int[] { mask, active, texture, binding };
	}

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
			org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT24, SIZE, SIZE);

		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, framebuffer);
		EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
			EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, targetTexture, 0);
		EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
			EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, depthBuffer);
		int status = EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT);
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		assertEquals(EXTFramebufferObject.GL_FRAMEBUFFER_COMPLETE_EXT, status, "offscreen target incomplete");
	}

	/** A flat, uniform water texture: only the surface normal can shade it. */
	private static void createTexture() {
		waterTexture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, waterTexture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		// Direct memory: LWJGL passes a buffer's native address straight to the
		// driver, and a heap buffer's address is not a real pointer (on HotSpot
		// it is the array base offset, 16), which faults inside the GL driver.
		ByteBuffer texels = BufferUtils.createByteBuffer(4 * 4 * 4).order(ByteOrder.LITTLE_ENDIAN);
		for (int i = 0; i < 16; i++) {
			texels.put((byte) 120).put((byte) 160).put((byte) 200).put((byte) 255);
		}
		texels.flip();
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, 4, 4, 0, GL11.GL_RGBA,
			GL11.GL_UNSIGNED_BYTE, texels);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	/** Six solid faces of one colour: the sampled direction cannot be ambiguous. */
	private static GlCubeTexture cube(int red, int green, int blue) {
		byte[][] faces = new byte[6][CUBE_SIZE * CUBE_SIZE * 3];
		for (int face = 0; face < 6; face++) {
			for (int texel = 0; texel < CUBE_SIZE * CUBE_SIZE; texel++) {
				faces[face][texel * 3] = (byte) red;
				faces[face][texel * 3 + 1] = (byte) green;
				faces[face][texel * 3 + 2] = (byte) blue;
			}
		}
		GlCubeTexture cube = new GlCubeTexture(toolkit, GL11.GL_RGB, CUBE_SIZE, false, faces, GL11.GL_RGB);
		// The constructor leaves the cube bound on whichever unit is current.
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return cube;
	}

	/** One quad in the z = 0 plane, with white vertex colour and the given normal. */
	private static ElementsBufferGL plane(float normalX, float normalY, float normalZ) {
		float[][] corners = {
			{ -HALF, -HALF, 0.0F }, { HALF, -HALF, 0.0F }, { HALF, HALF, 0.0F }, { -HALF, HALF, 0.0F }
		};
		float[][] uvs = { { 0.0F, 0.0F }, { 1.0F, 0.0F }, { 1.0F, 1.0F }, { 0.0F, 1.0F } };
		ByteBuffer data = ByteBuffer.allocate(STRIDE * corners.length).order(ByteOrder.LITTLE_ENDIAN);
		for (int i = 0; i < corners.length; i++) {
			data.putFloat(corners[i][0]).putFloat(corners[i][1]).putFloat(corners[i][2]);
			data.put((byte) 255).put((byte) 255).put((byte) 255).put((byte) 255);
			// Facing the camera: a reflection lobe is a place on this surface, and
			// where it sits depends only on the sun's direction.
			data.putFloat(normalX).putFloat(normalY).putFloat(normalZ);
			data.putFloat(uvs[i][0]).putFloat(uvs[i][1]);
		}
		return new GlVboElementsArrayBuffer(toolkit, STRIDE, data.array(), data.array().length, false);
	}

	/**
	 * One quad in the z = 0 plane whose third texture coordinate is a depth ramp:
	 * 0 at the left edge to {@link #DEPTH_MAX} at the right, which is the stock
	 * water path's own per-vertex water depth that the breaking band reads.
	 */
	private static ElementsBufferGL depthPlane() {
		float[][] corners = {
			{ -HALF, -HALF, 0.0F }, { HALF, -HALF, 0.0F }, { HALF, HALF, 0.0F }, { -HALF, HALF, 0.0F }
		};
		float[][] uvs = { { 0.0F, 0.0F }, { 1.0F, 0.0F }, { 1.0F, 1.0F }, { 0.0F, 1.0F } };
		ByteBuffer data = ByteBuffer.allocate(DEPTH_STRIDE * corners.length).order(ByteOrder.LITTLE_ENDIAN);
		for (int i = 0; i < corners.length; i++) {
			data.putFloat(corners[i][0]).putFloat(corners[i][1]).putFloat(corners[i][2]);
			data.put((byte) 255).put((byte) 255).put((byte) 255).put((byte) 255);
			data.putFloat(0.0F).putFloat(1.0F).putFloat(0.0F);
			data.putFloat(uvs[i][0]).putFloat(uvs[i][1]);
			data.putFloat(corners[i][0] < 0.0F ? 0.0F : DEPTH_MAX);
		}
		return new GlVboElementsArrayBuffer(toolkit, DEPTH_STRIDE, data.array(), data.array().length, false);
	}

	/** A 4x4 two-colour checker, repeated, so a scaled lookup cannot be guessed. */
	private static int checker() {
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		ByteBuffer texels = BufferUtils.createByteBuffer(4 * 4 * 4).order(ByteOrder.LITTLE_ENDIAN);
		for (int row = 0; row < 4; row++) {
			for (int column = 0; column < 4; column++) {
				boolean light = ((row + column) & 1) == 0;
				texels.put((byte) (light ? 240 : 40)).put((byte) (light ? 200 : 60))
					.put((byte) (light ? 120 : 80)).put((byte) 255);
			}
		}
		texels.flip();
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, 4, 4, 0, GL11.GL_RGBA,
			GL11.GL_UNSIGNED_BYTE, texels);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return texture;
	}

	private static ArrayBufferGL indices() {
		byte[] indices = { 0, 0, 1, 0, 2, 0, 0, 0, 2, 0, 3, 0 };
		return new GlVboArrayBuffer(toolkit, GL11.GL_UNSIGNED_SHORT, indices, indices.length, false);
	}

	/**
	 * The client's sun sampler reduced to the one thing the water seam reads:
	 * the environment cube {@code GlToolkit.method1946()} hands out. Swapping
	 * {@link #cube} is exactly what a repainted sky does to a real session.
	 */
	private static final class TestEnvironment extends Class99_Sub1 {

		private GlCubeTexture cube;

		private TestEnvironment(GlCubeTexture cube) {
			this.cube = cube;
		}

		@Override
		public GlCubeTexture method3232() {
			return this.cube;
		}
	}

	/**
	 * Two materials with the stock effect slots the seam decides on: one
	 * reflective (slot 8), one calm (slot 3). Every other material is absent,
	 * which is the toolkit's "no material" answer.
	 */
	private static final class TestMaterials implements unpack_Sub1 {

		private final MaterialType reflective = new MaterialType();

		private final MaterialType calm = new MaterialType();

		private final MaterialType breaking = new MaterialType();

		private final MaterialType breakingShallow = new MaterialType();

		private TestMaterials() {
			this.reflective.aByte89 = (byte) HdWaterGate.REFLECTIVE_SLOT;
			this.calm.aByte89 = 3;
			this.breaking.aByte89 = (byte) HdWaterGate.BREAKING_SLOT;
			this.breaking.aByte88 = BREAKING_FIELDS;
			this.breaking.anInt6933 = BREAKING_PACKED;
			this.breakingShallow.aByte89 = (byte) HdWaterGate.BREAKING_SLOT;
			this.breakingShallow.aByte88 = BREAKING_FIELDS;
			this.breakingShallow.anInt6933 = BREAKING_SHALLOW_PACKED;
		}

		@Override
		public MaterialType getMetrics(int material) {
			if (material == REFLECTIVE_MATERIAL) {
				return this.reflective;
			}
			if (material == BREAKING_MATERIAL) {
				return this.breaking;
			}
			if (material == BREAKING_SHALLOW_MATERIAL) {
				return this.breakingShallow;
			}
			return material == CALM_MATERIAL ? this.calm : null;
		}

		@Override
		public boolean textureAvailable(int material) {
			return false;
		}

		@Override
		public int[] getPixels(boolean readReversed, int arg1, float arg2, int arg3, int height) {
			return null;
		}

		@Override
		public float[] method2394(float arg0, int arg1, int arg2, int arg3) {
			return null;
		}

		@Override
		public int[] getPixelsArgb(int arg0, float arg1, int arg2, int arg3) {
			return null;
		}
	}
}
