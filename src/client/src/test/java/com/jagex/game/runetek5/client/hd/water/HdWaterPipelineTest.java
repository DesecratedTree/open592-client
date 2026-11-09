package com.jagex.game.runetek5.client.hd.water;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import com.jagex.game.runetek5.client.hd.shader.FakeShaderApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HD water pipeline")
class HdWaterPipelineTest {

	private FakeShaderApi api;

	private HdWaterPipeline pipeline;

	@BeforeEach
	void setUp() {
		this.api = new FakeShaderApi();
		for (String uniform : HdWaterSource.uniforms()) {
			this.api.declareUniform(uniform);
		}
		// The reflective program's extra sampler; declaring it after the calm
		// group keeps the calm uniforms' locations stable for the assertions.
		this.api.declareUniform(HdWaterSource.UNIFORM_ENVIRONMENT);
		this.api.declareUniform(HdWaterSource.UNIFORM_CAUSTICS);
		// The custom surface's palette, declared last for the same reason.
		this.api.declareUniform(HdWaterSource.UNIFORM_DEEP_COLOUR);
		this.api.declareUniform(HdWaterSource.UNIFORM_SHALLOW_COLOUR);
		// The breaking surface's six material numbers, declared after those.
		this.api.declareUniform(HdWaterSource.UNIFORM_BREAK_DEPTH);
		this.api.declareUniform(HdWaterSource.UNIFORM_BREAK_OFFSET);
		this.api.declareUniform(HdWaterSource.UNIFORM_WAVE_INTENSITY);
		this.api.declareUniform(HdWaterSource.UNIFORM_WAVE_EXPONENT);
		this.api.declareUniform(HdWaterSource.UNIFORM_BREAK_SCALE);
		this.api.declareUniform(HdWaterSource.UNIFORM_BREAK_RATE);
		// The two water maps and the flag/strength the pipeline hands with them.
		this.api.declareUniform(HdWaterSource.UNIFORM_WATER_NORMAL);
		this.api.declareUniform(HdWaterSource.UNIFORM_WATER_FOAM);
		this.api.declareUniform(HdWaterSource.UNIFORM_WATER_MAPS);
		this.api.declareUniform(HdWaterSource.UNIFORM_NORMAL_STRENGTH);
		// The area is static: a published one outlives its test class.
		HdWaterArea.resetForTest();
		this.pipeline = new HdWaterPipeline(this.api);
	}

	@Test
	@DisplayName("the row off never reaches GL, not even to compile")
	void rowOff_shouldBeInert() {
		assertAll(
			() -> assertFalse(this.pipeline.begin(HdWaterRequest.still(false, 0.0F))),
			() -> assertFalse(this.pipeline.available()),
			() -> assertTrue(this.api.calls.isEmpty(), this.api.calls.toString())
		);
	}

	@Test
	@DisplayName("the first water draw compiles on demand and binds the sampler")
	void begin_shouldCompileAndBindTheSampler() {
		assertTrue(this.pipeline.begin(HdWaterRequest.still(true, 1.25F)));

		assertAll(
			() -> assertTrue(this.pipeline.available()),
			() -> assertTrue(this.pipeline.bound()),
			() -> assertEquals(1, this.api.calls("link:")),
			() -> assertTrue(this.api.uploads.contains("uTexture=0"), "the sampler is bound to unit 0: " + this.api.uploads),
			// The material's water type - its gloss included - goes up with the
			// bind, ahead of the frame group.
			() -> assertEquals(Arrays.asList("uShininess=" + HdWaterPipeline.DEFAULT_SHININESS, "uTime=1.25",
				"uFlow=" + HdWaterPipeline.DEFAULT_FLOW), uploadsOf("uTime", "uFlow", "uShininess")),
			() -> assertEquals(1L, this.pipeline.binds())
		);
	}

	@Test
	@DisplayName("a failure is remembered, reported once, and the stock effect stays in charge")
	void failure_shouldBeRemembered() {
		this.api.failLink = true;

		assertFalse(this.pipeline.begin(HdWaterRequest.still(true, 0.0F)));
		int calls = this.api.calls.size();
		assertFalse(this.pipeline.begin(HdWaterRequest.still(true, 1.0F)));

		assertAll(
			() -> assertFalse(this.pipeline.available()),
			() -> assertTrue(this.pipeline.logLine().contains("link failed"), this.pipeline.logLine()),
			() -> assertEquals(calls, this.api.calls.size(), "a failure must not be retried per draw"),
			() -> assertEquals(0L, this.pipeline.binds()),
			() -> assertTrue(this.api.liveHandles().isEmpty())
		);
	}

	@Test
	@DisplayName("the time is uploaded once per frame, not once per water layer")
	void repeatedBegin_shouldSkipUnchangedTime() {
		this.pipeline.begin(HdWaterRequest.still(true, 3.0F));
		this.pipeline.unbind();
		int uploads = this.api.uploads.size();

		this.pipeline.begin(HdWaterRequest.still(true, 3.0F));

		assertAll(
			// The program still holds the material's water type, so only the
			// sampler, the reflection flag and the caustic strength are re-bound:
			// the palette is not re-sent for a type the program already has.
			() -> assertEquals(uploads + 3, this.api.uploads.size(),
				"the sampler, the reflection flag and the caustic strength are re-bound"),
			() -> assertEquals(2L, this.pipeline.binds()),
			() -> assertTrue(uploadsOf("uTime", "uFlow", "uShininess").size() == 3)
		);
	}

	@Test
	@DisplayName("a new frame re-uploads the animation time")
	void newFrame_shouldUploadTheTime() {
		this.pipeline.begin(HdWaterRequest.still(true, 3.0F));
		this.pipeline.begin(HdWaterRequest.still(true, 3.02F));

		assertAll(
			() -> assertTrue(uploadsOf("uTime").contains("uTime=3.02")),
			() -> assertEquals(2, this.api.calls("uniform1f:" + location(HdWaterSource.UNIFORM_TIME)),
				"one time upload per frame")
		);
	}

	@Test
	@DisplayName("a reflective material switches the open draw to the cube-sampling program")
	void reflectiveMaterial_shouldSwitchProgram() {
		assertTrue(this.pipeline.begin(HdWaterRequest.still(true, 2.0F)));
		int uses = this.api.calls("use:");

		HdWaterRequest reflective = HdWaterRequest.current(true, true, true, 2.0F);
		assertTrue(this.pipeline.select(reflective));

		assertAll(
			() -> assertTrue(this.pipeline.reflectiveAvailable(), "the second program compiled on demand"),
			() -> assertTrue(this.pipeline.reflectiveMode()),
			() -> assertEquals(uses + 1, this.api.calls("use:"), "one switch, not one program per draw"),
			() -> assertTrue(this.api.uploadedSource(HdWaterSource.REFLECTIVE_FRAGMENT),
				"the reflective fragment is the one that was compiled"),
			// GLSL 120 needs a unit for the sampler even though the cube is bound
			// by the toolkit; unit 1 is where the stock cube effects put theirs.
			() -> assertTrue(this.api.uploads.contains("uEnvironment=1"), this.api.uploads.toString()),
			// A program that has just been switched to has never seen this frame.
			() -> assertEquals("uTime=2.0", uploadsOf("uTime").get(uploadsOf("uTime").size() - 1)),
			() -> assertEquals(2L, this.pipeline.binds()),
			() -> assertEquals(1L, this.pipeline.reflectiveBinds())
		);
	}

	@Test
	@DisplayName("the same reflective material again is not a second switch")
	void reflectiveMaterial_shouldBeIdempotent() {
		this.pipeline.begin(HdWaterRequest.still(true, 1.0F));
		this.pipeline.select(HdWaterRequest.current(true, true, true, 1.0F));
		int uses = this.api.calls("use:");

		this.pipeline.select(HdWaterRequest.current(true, true, true, 1.0F));

		assertAll(
			() -> assertEquals(uses, this.api.calls("use:")),
			() -> assertEquals(1L, this.pipeline.reflectiveBinds())
		);
	}

	@Test
	@DisplayName("without an environment cube the reflective type stays calm water, with no second compile")
	void reflectiveWithoutEnvironment_shouldStayCalm() {
		this.pipeline.begin(HdWaterRequest.still(true, 0.5F));
		int uses = this.api.calls("use:");
		int links = this.api.calls("link:");

		assertTrue(this.pipeline.select(HdWaterRequest.current(true, true, false, 0.5F)));

		assertAll(
			() -> assertFalse(this.pipeline.reflectiveMode()),
			() -> assertEquals(uses, this.api.calls("use:"), "a reflection of nothing is not drawn"),
			() -> assertEquals(links, this.api.calls("link:"), "the program is not compiled for a draw it cannot serve"),
			() -> assertEquals(0L, this.pipeline.reflectiveBinds())
		);
	}

	@Test
	@DisplayName("a reflective draw with the row off still never reaches GL")
	void reflectiveRowOff_shouldBeInert() {
		assertFalse(this.pipeline.begin(HdWaterRequest.current(false, true, true, 0.0F)));

		assertAll(
			() -> assertFalse(this.pipeline.available()),
			() -> assertFalse(this.pipeline.reflectiveAvailable()),
			() -> assertTrue(this.api.calls.isEmpty(), this.api.calls.toString())
		);
	}

	@Test
	@DisplayName("a reflective compile failure is reported once and the calm program keeps drawing")
	void reflectiveFailure_shouldFallBackToCalm() {
		this.pipeline.begin(HdWaterRequest.still(true, 0.0F));
		this.api.failLink = true;

		assertTrue(this.pipeline.select(HdWaterRequest.current(true, true, true, 0.0F)));
		int calls = this.api.calls.size();
		this.pipeline.select(HdWaterRequest.current(true, true, true, 0.5F));

		assertAll(
			() -> assertFalse(this.pipeline.reflectiveAvailable()),
			() -> assertFalse(this.pipeline.reflectiveMode(), "the calm program is still the one bound"),
			() -> assertTrue(this.pipeline.logLine().contains("HD water reflections unavailable"), this.pipeline.logLine()),
			() -> assertEquals(calls, this.api.calls.size(), "a failure must not be retried per material"),
			() -> assertEquals(0L, this.pipeline.reflectiveBinds())
		);
	}

	@Test
	@DisplayName("select outside an open water draw binds nothing")
	void selectWithoutDraw_shouldBeInert() {
		assertFalse(this.pipeline.select(HdWaterRequest.current(true, true, true, 0.0F)));

		assertAll(
			() -> assertFalse(this.pipeline.bound()),
			() -> assertTrue(this.api.calls.isEmpty(), this.api.calls.toString())
		);
	}

	@Test
	@DisplayName("unbind returns to fixed function exactly once")
	void unbind_shouldUseProgramZeroOnce() {
		this.pipeline.begin(HdWaterRequest.still(true, 0.0F));
		this.pipeline.unbind();
		this.pipeline.unbind();

		assertAll(
			() -> assertFalse(this.pipeline.bound()),
			() -> assertEquals(1, this.api.calls("use:0"))
		);
	}

	@Test
	@DisplayName("the standard surface never compiles the custom program")
	void onRow_shouldNeverCompileTheCustomSurface() {
		assertTrue(this.pipeline.begin(HdWaterRequest.still(true, 0.0F)));
		this.pipeline.unbind();
		assertTrue(this.pipeline.begin(HdWaterRequest.still(true, 1.0F)));

		assertAll(
			() -> assertFalse(this.pipeline.customAvailable()),
			() -> assertEquals(1, this.api.calls("link:"), "one program, the one the row asked for"),
			() -> assertEquals(HdWaterGate.Surface.STILL, this.pipeline.surface()),
			() -> assertEquals(0L, this.pipeline.customBinds())
		);
	}

	@Test
	@DisplayName("the custom value compiles the third surface on first use and uploads its palette")
	void customRow_shouldCompileAndUploadThePalette() {
		assertTrue(this.pipeline.begin(HdWaterRequest.still(true, true, 1.5F)));

		assertAll(
			() -> assertTrue(this.pipeline.customAvailable()),
			() -> assertTrue(this.pipeline.customMode()),
			() -> assertFalse(this.pipeline.reflectiveMode()),
			() -> assertEquals(2, this.api.calls("link:"), "the standard surface and the custom one"),
			() -> assertTrue(this.api.uploadedSource(HdWaterSource.CUSTOM_FRAGMENT),
				"the custom fragment is the one that was compiled"),
			() -> assertTrue(uploadsOf("uDeepColour").contains(colourUpload("uDeepColour",
				HdWaterPipeline.DEFAULT_DEEP_COLOUR)), this.api.uploads.toString()),
			() -> assertTrue(uploadsOf("uShallowColour").contains(colourUpload("uShallowColour",
				HdWaterPipeline.DEFAULT_SHALLOW_COLOUR)), this.api.uploads.toString()),
			() -> assertEquals("uTime=1.5", uploadsOf("uTime").get(uploadsOf("uTime").size() - 1)),
			() -> assertEquals(1L, this.pipeline.customBinds()),
			() -> assertEquals(0L, this.pipeline.reflectiveBinds())
		);
	}

	@Test
	@DisplayName("the custom palette is upstream's WATER type: depth #00758e under surface #69809c")
	void customPalette_shouldBeTheUpstreamWaterType() {
		// 634hd/data/materials.schema.json: the WATER type's depth and surface
		// colours, whose sRGB bytes this row uses directly (it shades in the
		// texture's own sRGB space).
		float[] deep = HdWaterPipeline.DEFAULT_DEEP_COLOUR;
		float[] shallow = HdWaterPipeline.DEFAULT_SHALLOW_COLOUR;
		assertAll(
			() -> assertEquals(3, deep.length),
			() -> assertEquals(0x00 / 255.0F, deep[0], 0.0F, "depth #00758e: red"),
			() -> assertEquals(0x75 / 255.0F, deep[1], 0.0F, "depth #00758e: green"),
			() -> assertEquals(0x8e / 255.0F, deep[2], 0.0F, "depth #00758e: blue"),
			() -> assertEquals(3, shallow.length),
			() -> assertEquals(0x69 / 255.0F, shallow[0], 0.0F, "surface #69809c: red"),
			() -> assertEquals(0x80 / 255.0F, shallow[1], 0.0F, "surface #69809c: green"),
			() -> assertEquals(0x9c / 255.0F, shallow[2], 0.0F, "surface #69809c: blue")
		);
	}

	@Test
	@DisplayName("the standard surface is told whether the frame has a cube to sample")
	void stillSurface_shouldUploadTheReflectionFlag() {
		assertTrue(this.pipeline.begin(HdWaterRequest.still(true, 1.0F).withEnvironment(true)));

		assertAll(
			() -> assertEquals(Arrays.asList("uEnvironment=1"), uploadsOf("uEnvironment")),
			() -> assertEquals(Arrays.asList("uReflection=1.0"), uploadsOf("uReflection")),
			// The net is every surface's now: a lake on the plain rows showed no
			// caustics at all while only the custom style ran them.
			() -> assertEquals(Arrays.asList("uCaustics=0.0"), uploadsOf("uCaustics"),
				"the standard surface runs the row's net, at the strength it was handed")
		);
	}

	@Test
	@DisplayName("the custom surface is handed the Caustics row's strength")
	void customSurface_shouldUploadTheCausticStrength() {
		assertTrue(this.pipeline.begin(HdWaterRequest.still(true, true, 1.0F).withCaustics(1.0F)));

		assertAll(
			() -> assertTrue(this.pipeline.customMode()),
			() -> assertEquals(Arrays.asList("uCaustics=1.0"), uploadsOf("uCaustics")),
			() -> assertEquals(Arrays.asList("uReflection=0.0"), uploadsOf("uReflection"),
				"no cube in this draw, so the sheen falls back to the fog band")
		);
	}

	@Test
	@DisplayName("a custom program that fails to link leaves the standard surface drawing")
	void customFailure_shouldFallBackToTheStandardSurface() {
		// The standard program links first, as it does in a session; only the
		// third program is refused.
		assertTrue(this.pipeline.begin(HdWaterRequest.still(true, 0.0F)));
		this.api.failLink = true;
		assertTrue(this.pipeline.begin(HdWaterRequest.still(true, true, 0.0F)),
			"the water is still drawn, on the standard surface");

		int links = this.api.calls("link:");
		this.pipeline.begin(HdWaterRequest.still(true, true, 0.5F));

		assertAll(
			() -> assertFalse(this.pipeline.customAvailable()),
			() -> assertEquals(HdWaterGate.Surface.STILL, this.pipeline.surface()),
			() -> assertTrue(this.pipeline.logLine().contains("custom water surface unavailable"),
				this.pipeline.logLine()),
			() -> assertEquals(links, this.api.calls("link:"), "a failure is not retried per draw"),
			() -> assertEquals(0L, this.pipeline.customBinds())
		);
	}

	@Test
	@DisplayName("the row off compiles no custom program, even when the value asks for one")
	void customRowOff_shouldBeInert() {
		assertFalse(this.pipeline.begin(HdWaterRequest.still(false, true, 0.0F)));

		assertAll(
			() -> assertFalse(this.pipeline.available()),
			() -> assertFalse(this.pipeline.customAvailable()),
			() -> assertTrue(this.api.calls.isEmpty(), this.api.calls.toString())
		);
	}

	@Test
	@DisplayName("a reflective material on the custom row still reflects, and the style returns after it")
	void customRow_shouldKeepTheReflectionAndReturn() {
		assertTrue(this.pipeline.begin(HdWaterRequest.still(true, true, 0.0F)));
		assertTrue(this.pipeline.select(HdWaterRequest.current(true, true, true, true, 0.0F)));

		assertAll(
			() -> assertTrue(this.pipeline.reflectiveMode(), "the material's reflection outranks the style"),
			() -> assertEquals(HdWaterGate.Surface.REFLECTIVE, this.pipeline.surface()),
			() -> assertEquals(1L, this.pipeline.reflectiveBinds())
		);

		assertTrue(this.pipeline.select(HdWaterRequest.current(true, true, false, false, 0.0F)));
		assertAll(
			() -> assertTrue(this.pipeline.customMode(), "a calm material returns to the chosen surface"),
			() -> assertEquals(2L, this.pipeline.customBinds())
		);
	}

	@Test
	@DisplayName("a breaking material compiles its own surface and uploads the material's numbers")
	void breakingMaterial_shouldCompileAndUploadItsParameters() {
		HdWaterBreaking params = HdWaterBreaking.of((byte) 24, 64 | 2 << 16 | 4 << 19 | 8 << 23 | 2 << 27);
		assertTrue(this.pipeline.begin(HdWaterRequest.still(true, 1.5F)));
		int uses = this.api.calls("use:");

		assertTrue(this.pipeline.select(HdWaterRequest.current(true, false, true, 1.5F).withBreaking(params)));

		assertAll(
			() -> assertTrue(this.pipeline.breakingAvailable(), "the breaking program compiled on demand"),
			() -> assertTrue(this.pipeline.breakingMode()),
			() -> assertFalse(this.pipeline.reflectiveMode()),
			() -> assertEquals(uses + 1, this.api.calls("use:"), "one switch to the breaking program"),
			() -> assertTrue(this.api.uploadedSource(HdWaterSource.BREAKING_FRAGMENT),
				"the breaking fragment is the one that was compiled"),
			() -> assertTrue(this.api.uploads.contains("uEnvironment=1"), "it reflects the same cube"),
			() -> assertTrue(this.api.uploads.contains("uBreakDepth=64.0"), this.api.uploads.toString()),
			() -> assertTrue(this.api.uploads.contains("uBreakOffset=0.25")),
			() -> assertTrue(this.api.uploads.contains("uWaveIntensity=0.5,0.25"),
				"the two crest values are unpacked from the material"),
			() -> assertTrue(this.api.uploads.contains("uWaveExponent=2.0")),
			() -> assertTrue(this.api.uploads.contains("uBreakScale=0.25")),
			() -> assertTrue(this.api.uploads.contains("uBreakRate=1.0")),
			() -> assertEquals("uTime=1.5", uploadsOf("uTime").get(uploadsOf("uTime").size() - 1)),
			() -> assertEquals(2L, this.pipeline.binds()),
			() -> assertEquals(1L, this.pipeline.breakingBinds()),
			() -> assertEquals(0L, this.pipeline.reflectiveBinds())
		);
	}

	@Test
	@DisplayName("a second breaking material re-uploads only the numbers that changed, and no program switch")
	void secondBreakingMaterial_shouldOnlyUploadTheDifference() {
		HdWaterBreaking first = HdWaterBreaking.of((byte) 24, 64 | 2 << 16 | 2 << 27);
		HdWaterBreaking same = HdWaterBreaking.of((byte) 24, 64 | 2 << 16 | 2 << 27);
		HdWaterBreaking deeper = HdWaterBreaking.of((byte) 24, 128 | 2 << 16 | 2 << 27);

		this.pipeline.begin(HdWaterRequest.still(true, 0.0F));
		this.pipeline.select(HdWaterRequest.current(true, false, true, 0.0F).withBreaking(first));
		int uses = this.api.calls("use:");
		int uploads = this.api.uploads.size();

		this.pipeline.select(HdWaterRequest.current(true, false, true, 0.0F).withBreaking(same));

		assertAll(
			() -> assertEquals(uses, this.api.calls("use:"), "the program is already bound"),
			() -> assertEquals(uploads, this.api.uploads.size(), "the same numbers cost nothing"),
			() -> assertEquals(1L, this.pipeline.breakingBinds())
		);

		this.pipeline.select(HdWaterRequest.current(true, false, true, 0.0F).withBreaking(deeper));

		assertAll(
			() -> assertEquals(uses, this.api.calls("use:"), "a different material is not a different program"),
			() -> assertEquals(uploads + 6, this.api.uploads.size(), "the six numbers go up again"),
			() -> assertEquals("uBreakDepth=128.0",
				uploadsOf("uBreakDepth").get(uploadsOf("uBreakDepth").size() - 1)),
			() -> assertEquals(1L, this.pipeline.breakingBinds())
		);
	}

	@Test
	@DisplayName("a breaking compile failure is reported once and the calm program keeps drawing")
	void breakingFailure_shouldFallBackToCalm() {
		HdWaterBreaking params = HdWaterBreaking.of((byte) 24, 64 | 2 << 16 | 2 << 27);
		this.pipeline.begin(HdWaterRequest.still(true, 0.0F));
		this.api.failLink = true;

		assertTrue(this.pipeline.select(HdWaterRequest.current(true, false, true, 0.0F).withBreaking(params)));
		int calls = this.api.calls.size();
		this.pipeline.select(HdWaterRequest.current(true, false, true, 0.5F).withBreaking(params));

		assertAll(
			() -> assertFalse(this.pipeline.breakingAvailable()),
			() -> assertEquals(HdWaterGate.Surface.STILL, this.pipeline.surface(),
				"the calm program is still the one bound"),
			() -> assertTrue(this.pipeline.logLine().contains("breaking water unavailable"), this.pipeline.logLine()),
			() -> assertEquals(calls, this.api.calls.size(), "a failure must not be retried per material"),
			() -> assertEquals(0L, this.pipeline.breakingBinds())
		);
	}

	@Test
	@DisplayName("the row off compiles no breaking program, even for a breaking material")
	void breakingRowOff_shouldBeInert() {
		HdWaterBreaking params = HdWaterBreaking.of((byte) 24, 64 | 2 << 16 | 2 << 27);
		assertFalse(this.pipeline.begin(HdWaterRequest.current(false, false, true, 0.0F).withBreaking(params)));

		assertAll(
			() -> assertFalse(this.pipeline.available()),
			() -> assertFalse(this.pipeline.breakingAvailable()),
			() -> assertTrue(this.api.calls.isEmpty(), this.api.calls.toString())
		);
	}

	@Test
	@DisplayName("shutdown deletes every program the context owns")
	void shutdown_shouldReleaseTheProgram() {
		this.pipeline.begin(HdWaterRequest.still(true, true, 0.0F));
		this.pipeline.select(HdWaterRequest.current(true, true, true, true, 0.0F));
		this.pipeline.select(HdWaterRequest.current(true, false, true, 0.0F)
			.withBreaking(HdWaterBreaking.of((byte) 24, 64)));
		assertTrue(this.pipeline.customAvailable() && this.pipeline.reflectiveAvailable()
			&& this.pipeline.breakingAvailable(), "all four programs exist before teardown");
		this.pipeline.shutdown();

		assertAll(
			() -> assertFalse(this.pipeline.available()),
			() -> assertFalse(this.pipeline.reflectiveAvailable()),
			() -> assertFalse(this.pipeline.breakingAvailable()),
			() -> assertFalse(this.pipeline.customAvailable()),
			() -> assertEquals(HdWaterGate.Surface.NONE, this.pipeline.surface()),
			() -> assertEquals(0L, this.pipeline.customBinds()),
			() -> assertEquals(1, this.api.calls("use:0")),
			() -> assertTrue(this.api.liveHandles().isEmpty(), this.api.liveHandles().toString()),
			() -> assertEquals(0L, this.pipeline.binds()),
			() -> assertEquals(0L, this.pipeline.reflectiveBinds()),
			() -> assertEquals(0L, this.pipeline.breakingBinds())
		);
	}

	@Test
	@DisplayName("the map units are selected once per frame group, and their absence is uploaded as 0")
	void waterMaps_shouldBeBoundAndFlagged() {
		this.pipeline.setWaterMaps(true);
		assertTrue(this.pipeline.begin(HdWaterRequest.still(true, 1.0F)));

		assertAll(
			() -> assertTrue(this.api.uploads.contains("uWaterNormal=" + HdWaterSource.WATER_NORMAL_UNIT),
				this.api.uploads.toString()),
			() -> assertTrue(this.api.uploads.contains("uWaterFoam=" + HdWaterSource.WATER_FOAM_UNIT),
				this.api.uploads.toString()),
			() -> assertEquals(Arrays.asList("uWaterMaps=1.0"), uploadsOf("uWaterMaps")),
			() -> assertEquals(Arrays.asList("uNormalStrength=" + HdWaterPipeline.DEFAULT_NORMAL_STRENGTH),
				uploadsOf("uNormalStrength")),
			() -> assertTrue(this.pipeline.waterMaps())
		);

		this.pipeline.setWaterMaps(false);
		this.pipeline.begin(HdWaterRequest.still(true, 2.0F));
		assertAll(
			() -> assertEquals("uWaterMaps=0.0", uploadsOf("uWaterMaps").get(uploadsOf("uWaterMaps").size() - 1)),
			() -> assertFalse(this.pipeline.waterMaps())
		);
	}

	@Test
	@DisplayName("a map flag that changes within one clock step is uploaded, not cached")
	void waterMaps_changeAtTheSameTime_shouldBeUploaded() {
		// The flag rides in the frame group, and that group is skipped while the
		// clock stands still; the first draw must not leave a stale 0 for every
		// draw at that instant.
		this.pipeline.setWaterMaps(false);
		assertTrue(this.pipeline.begin(HdWaterRequest.still(true, 1.0F)));
		this.pipeline.setWaterMaps(true);
		assertTrue(this.pipeline.begin(HdWaterRequest.still(true, 1.0F)));

		assertAll(
			() -> assertEquals(Arrays.asList("uWaterMaps=0.0", "uWaterMaps=1.0"), uploadsOf("uWaterMaps")),
			() -> assertEquals(2, uploadsOf("uTime").size(), "the frame group moves with the flag")
		);
	}

	@Test
	@DisplayName("a ground layer is handed the type its own program has not seen")
	void groundLayer_shouldGetTheTypeItsProgramHasNotSeen() {
		// The model-format program and the terrain-format one are two program
		// objects of the same surface, and uniforms live on the program: keying
		// the type on the surface left the ground program at all zeros, which
		// drew a flat black lake whose ripples and net never moved.
		HdWaterRequest request = HdWaterRequest.still(true, 1.0F);
		assertTrue(this.pipeline.begin(request));
		int spec = uploadsOf("uWaterSpec").size();
		int lights = uploadsOf("uPointLightCount").size();
		int params = uploadsOf("uWaterParams").size();

		assertTrue(this.pipeline.beginGround(request, 0.5F));

		assertAll(
			() -> assertEquals(1, spec, "the model program was handed the type once"),
			() -> assertEquals(spec + 1, uploadsOf("uWaterSpec").size(),
				"the ground program is handed the type as well"),
			() -> assertEquals(lights + 1, uploadsOf("uPointLightCount").size(),
				"and the frame's point-light count with it"),
			() -> assertEquals(HdWaterGate.Surface.STILL, this.pipeline.groundSurface()),
			// The terrain program had no area group at all, which left the
			// strength at zero and its ripples flat: the lake proof caught it.
			() -> assertEquals(params + 1, uploadsOf("uWaterParams").size(),
				"and the area's water, which the terrain program has never seen")
		);
	}

	@Test
	@DisplayName("the area's water is uploaded once per program, not once per layer")
	void waterArea_shouldUploadOnceAndFollowTheArea() {
		// A lake draws many layers of one program, and the area group belongs to
		// the program: re-sending it per layer is per-draw work for one teleport.
		HdWaterArea.publishForTest(HdWaterArea.of(0x204060, 0.5F, 2.0F, 3.0F, 0.5F, 2.0F));
		try {
			assertTrue(this.pipeline.begin(HdWaterRequest.still(true, 1.0F)));
			assertAll(
				() -> assertEquals(Arrays.asList("uWaterParams=2.0,3.0,2.0"), uploadsOf("uWaterParams")),
				() -> assertEquals(1, uploadsOf("uWaterTint").size(), "the tint rides with the scales"),
				() -> assertEquals(Arrays.asList("uWaterBlend=2.0,1.0"), uploadsOf("uWaterBlend"),
					"a settled area shows its own tiling")
			);

			assertTrue(this.pipeline.begin(HdWaterRequest.still(true, 1.0F)));
			assertAll(
				() -> assertEquals(1, uploadsOf("uWaterParams").size(), "a settled area is not re-sent"),
				() -> assertEquals(1, uploadsOf("uWaterTint").size()),
				() -> assertEquals(1, uploadsOf("uWaterBlend").size())
			);

			// A teleport crossfades from the tiling that was on show, so the shown
			// size and the blend go up with the new area's own size.
			HdWaterArea.publishForTest(HdWaterArea.midTransition(
				HdWaterArea.of(0x204060, 0.5F, 2.0F, 3.0F, 0.5F, 4.0F), 2.0F, 0.0F));
			assertTrue(this.pipeline.begin(HdWaterRequest.still(true, 2.0F)));
			List<String> params = uploadsOf("uWaterParams");
			List<String> blend = uploadsOf("uWaterBlend");
			assertAll(
				() -> assertEquals("uWaterParams=2.0,3.0,4.0", params.get(params.size() - 1)),
				() -> assertEquals("uWaterBlend=2.0,0.0", blend.get(blend.size() - 1))
			);
		} finally {
			HdWaterArea.resetForTest();
		}
	}

	/** The upload string the fake records for a vec3: {@code name=x,y,z}. */
	private static String colourUpload(String name, float[] colour) {
		return name + "=" + colour[0] + "," + colour[1] + "," + colour[2];
	}

	private List<String> uploadsOf(String... names) {
		List<String> matches = new java.util.ArrayList<String>();
		for (String upload : this.api.uploads) {
			for (String name : names) {
				if (upload.startsWith(name + "=")) {
					matches.add(upload);
				}
			}
		}
		return matches;
	}

	/** The location the fake handed out: declarations start at 10, in order. */
	private static int location(String uniform) {
		return 10 + indexOf(uniform);
	}

	private static int indexOf(String uniform) {
		String[] uniforms = HdWaterSource.uniforms();
		for (int i = 0; i < uniforms.length; i++) {
			if (uniforms[i].equals(uniform)) {
				return i;
			}
		}
		return -1;
	}
}
