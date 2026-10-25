package com.jagex.game.runetek5.client.hd.shader;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import com.jagex.game.runetek5.client.hd.atmosphere.HdAtmosphere;
import com.jagex.game.runetek5.client.hd.ground.HdGroundWater;

import com.jagex.game.runetek5.client.hd.light.HdEnvironmentLight;
import com.jagex.game.runetek5.client.hd.light.HdLightFrame;
import com.jagex.game.runetek5.client.hd.light.HdLightSource;
import com.jagex.game.runetek5.client.hd.light.HdRoomLight;
import com.jagex.game.runetek5.client.hd.reflect.HdReflection;
import com.jagex.game.runetek5.client.hd.shadow.ShadowBinding;
import com.jagex.game.runetek5.client.hd.shader.HdDrawRows.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("592HD lighting pipeline")
class HdLightingPipelineTest {

	private static final int MODULATE = 7681;

	private static final int REPLACE = 8448;

	private FakeShaderApi api;

	private HdLightingPipeline pipeline;

	private java.util.Map<String, Integer> locations;

	@BeforeEach
	void setUp() {
		this.api = new FakeShaderApi();
		this.locations = new java.util.HashMap<String, Integer>();
		for (String uniform : HdShaderSource.uniforms()) {
			this.locations.put(uniform, Integer.valueOf(this.api.declareUniform(uniform)));
		}
		for (String uniform : HdShaderSource.vertexUniforms()) {
			this.locations.put(uniform, Integer.valueOf(this.api.declareUniform(uniform)));
		}
		this.pipeline = new HdLightingPipeline(this.api);
	}

	/** A draw the shader should light, with the given settings overrides. */
	private static HdDrawRequest draw(boolean hd, boolean row, boolean fog, boolean textured, int combine) {
		HdDrawRows rows = HdDrawRows.none()
			.with(Row.HD, hd)
			.with(Row.PER_PIXEL_LIGHTING, row)
			.with(Row.POINT_LIGHTS)
			.with(Row.NORMALS)
			.with(Row.TEXTURE, textured)
			.with(Row.FOG, fog);
		return new HdDrawRequest(rows, 0, combine);
	}

	private static HdDrawRequest draw(boolean hd, boolean row) {
		return draw(hd, row, true, true, MODULATE);
	}

	@Test
	@DisplayName("a draw with the row off never reaches GL, not even to compile")
	void beginWithRowOff_shouldBeInert() {
		assertAll(
			() -> assertFalse(this.pipeline.begin(draw(true, false),
				HdDrawSpace.MODEL,
				HdFrameState.of(2))),
			() -> assertFalse(this.pipeline.available()),
			() -> assertTrue(this.api.calls.isEmpty(), this.api.calls.toString())
		);
	}

	@Test
	@DisplayName("the first allowed draw compiles the program on demand")
	void beginShouldCompileOnFirstUse() {
		assertTrue(this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(1)));

		assertAll(
			() -> assertTrue(this.pipeline.available()),
			() -> assertEquals(2, this.api.calls("createShader:")),
			() -> assertEquals(1, this.api.calls("link:"))
		);
	}

	@Test
	@DisplayName("prepare compiles once and never again")
	void prepare_shouldCompileOnce() {
		assertTrue(this.pipeline.prepare());
		int creations = this.api.calls("createShader:");
		int links = this.api.calls("link:");

		assertTrue(this.pipeline.prepare());
		this.pipeline.prepare();

		assertAll(
			() -> assertTrue(this.pipeline.available()),
			() -> assertEquals(creations, this.api.calls("createShader:")),
			() -> assertEquals(links, this.api.calls("link:"))
		);
	}

	@Test
	@DisplayName("a failed compile is remembered, reported once and never retried")
	void prepare_shouldRememberFailure() {
		this.api.failLink = true;

		assertFalse(this.pipeline.prepare());
		int callsAfterFailure = this.api.calls.size();
		assertFalse(this.pipeline.prepare());

		assertAll(
			() -> assertFalse(this.pipeline.available()),
			() -> assertTrue(this.pipeline.logLine().contains("link failed"), this.pipeline.logLine()),
			() -> assertEquals(callsAfterFailure, this.api.calls.size(), "a failure must not be retried"),
			() -> assertFalse(this.pipeline.begin(draw(true, true),
				HdDrawSpace.MODEL,
				HdFrameState.of(0))),
			() -> assertTrue(this.api.liveHandles().isEmpty(), this.api.liveHandles().toString())
		);
	}

	@Test
	@DisplayName("nothing is reported when the program linked")
	void logLine_shouldBeEmptyOnSuccess() {
		this.pipeline.prepare();

		assertEquals("", this.pipeline.logLine());
	}

	@Test
	@DisplayName("the first bind uploads the sampler and both state uniforms")
	void firstBind_shouldUploadEverything() {
		this.pipeline.prepare();
		int before = this.api.calls.size();

		assertTrue(this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(2)));

		assertAll(
			() -> assertEquals(Arrays.asList("uTexture=0", "uReflectionCube=2", "uWorldAtlas=3",
				"uMaterialIdMap=4", "uTileMaterials=5", "uTextured=1",
				"uPointLightCount=2", "uSceneSpace=0",
				"uModelRow0=1.0,0.0,0.0,0.0", "uModelRow1=0.0,1.0,0.0,0.0",
				"uModelRow2=0.0,0.0,1.0,0.0", "uModelRow3=0.0,0.0,0.0,1.0",
				"uWorldTextures=0", "uMaterialMaps=0",
				"uTerrainBlend=-1.0,0.0," + (1.0F / 256.0F) + ",0.0", "uWorldTextureRepeat=0.0"),
				this.api.uploads),
			() -> assertEquals("use:" + programHandle(), this.api.calls.get(before)),
			() -> assertEquals("uniform1i:10,0", this.api.calls.get(before + 1), "sampler unit is uploaded first")
		);
	}

	@Test
	@DisplayName("rebinding the same state only binds the program again")
	void repeatBind_shouldSkipUnchangedUniforms() {
		this.pipeline.prepare();
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(2));
		this.pipeline.unbind();
		int uploads = this.api.uploads.size();

		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(2));

		assertAll(
			() -> assertEquals(uploads, this.api.uploads.size(), "same state must not re-upload"),
			() -> assertEquals(2, this.api.calls("use:" + programHandle()), "bind, unbind, bind again")
		);
	}

	@Test
	@DisplayName("a changed draw state re-uploads the group, without re-looking-up locations")
	void changedState_shouldUploadTheGroup() {
		this.pipeline.prepare();
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(2));
		int lookups = this.api.calls("uniformLocation:");

		this.pipeline.begin(draw(true, true, true, false, MODULATE),
			HdDrawSpace.MODEL,
			HdFrameState.of(1));

		assertAll(
			() -> assertEquals(Arrays.asList("uTexture=0", "uReflectionCube=2", "uWorldAtlas=3",
				"uMaterialIdMap=4", "uTileMaterials=5", "uTextured=1",
				"uPointLightCount=2", "uSceneSpace=0",
				"uModelRow0=1.0,0.0,0.0,0.0", "uModelRow1=0.0,1.0,0.0,0.0",
				"uModelRow2=0.0,0.0,1.0,0.0", "uModelRow3=0.0,0.0,0.0,1.0",
				"uWorldTextures=0", "uMaterialMaps=0",
				"uTerrainBlend=-1.0,0.0," + (1.0F / 256.0F) + ",0.0", "uWorldTextureRepeat=0.0",
				"uTextured=0", "uPointLightCount=1"), this.api.uploads),
			() -> assertEquals(lookups, this.api.calls("uniformLocation:"), "locations are cached")
		);
	}

	@Test
	@DisplayName("the model matrix goes up once per model, not once per primitive sharing it")
	void modelMatrix_shouldUploadWhenItChanges() {
		this.pipeline.prepare();
		float[] first = translation(3.0F, 0.0F, -7.0F);
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.model(first, null),
			HdFrameState.of(0));
		int afterFirst = this.api.uploads.size();

		this.pipeline.begin(draw(true, true),
			HdDrawSpace.model(first.clone(), null),
			HdFrameState.of(0));
		int afterSame = this.api.uploads.size();

		this.pipeline.begin(draw(true, true),
			HdDrawSpace.model(translation(3.0F, 0.0F, -8.0F), null),
			HdFrameState.of(0));
		List<String> delta = this.api.uploads.subList(afterSame, this.api.uploads.size());

		assertAll(
			() -> assertEquals(16, afterFirst,
				"five sampler units, three state uniforms, four matrix rows, four ground scalars"),
			() -> assertEquals(afterFirst, afterSame, "the same matrix must not be re-uploaded"),
			() -> assertEquals(4, delta.size(), "a moved model re-uploads its four rows"),
			() -> assertTrue(delta.get(0).startsWith("uModelRow0="), delta.toString())
		);
	}

	@Test
	@DisplayName("a draw with no model matrix uploads the identity, so it is scene space")
	void nullModelMatrix_shouldBeIdentity() {
		this.pipeline.prepare();
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0));

		List<String> matrix = new java.util.ArrayList<String>();
		for (String upload : this.api.uploads) {
			if (upload.startsWith("uModelRow")) {
				matrix.add(upload);
			}
		}
		assertEquals(Arrays.asList("uModelRow0=1.0,0.0,0.0,0.0", "uModelRow1=0.0,1.0,0.0,0.0",
			"uModelRow2=0.0,0.0,1.0,0.0", "uModelRow3=0.0,0.0,0.0,1.0"), matrix);
	}

	private static float[] translation(float x, float y, float z) {
		return new float[] { 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F,
			x, y, z, 1.0F };
	}

	@Test
	@DisplayName("a fogless draw binds and turns the band off in the shader")
	void foglessDraw_shouldBindWithoutTheBand() {
		// The terrain's case: its pass has GL_FOG off. Binding with uFogOff = 1
		// keeps it lit without a band, instead of leaving the ground as the only
		// surface in the scene with no per-pixel light.
		assertTrue(this.pipeline.begin(draw(true, true, false, true, MODULATE),
			HdDrawSpace.MODEL,
			HdFrameState.of(2)));

		assertAll(
			() -> assertTrue(this.pipeline.available(), "the row is on, so the program is ready"),
			() -> assertEquals(1, this.api.calls("use:"), "the draw may bind"),
			() -> assertTrue(this.api.uploads.contains("uFogOff=1"), this.api.uploads.toString())
		);
		this.pipeline.unbind();
	}

	@Test
	@DisplayName("a GL_REPLACE material keeps its fixed-function colour")
	void replaceCombine_shouldNotBind() {
		this.pipeline.prepare();
		int before = this.api.calls.size();

		assertFalse(this.pipeline.begin(draw(true, true, true, true, REPLACE),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)));
		assertEquals(before, this.api.calls.size());
	}

	@Test
	@DisplayName("unbind returns the context to fixed function exactly once")
	void unbind_shouldUseProgramZeroOnce() {
		this.pipeline.prepare();
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0));

		this.pipeline.unbind();
		int unbinds = this.api.calls("use:0");
		this.pipeline.unbind();

		assertAll(
			() -> assertEquals(1, unbinds),
			() -> assertEquals(1, this.api.calls("use:0"), "a second unbind must be a no-op")
		);
	}

	@Test
	@DisplayName("begin accepts a null request without touching GL")
	void beginNullRequest_shouldDoNothing() {
		this.pipeline.prepare();
		int before = this.api.calls.size();

		assertAll(
			() -> assertFalse(this.pipeline.begin(null,
				HdDrawSpace.MODEL,
				HdFrameState.of(0))),
			() -> assertEquals(before, this.api.calls.size())
		);
	}

	@Test
	@DisplayName("shutdown unbinds and deletes the program the context owns")
	void shutdown_shouldReleaseTheProgram() {
		this.pipeline.prepare();
		long program = programHandle();
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0));

		this.pipeline.shutdown();

		assertAll(
			() -> assertFalse(this.pipeline.available()),
			() -> assertEquals(1, this.api.calls("use:0")),
			() -> assertTrue(this.api.liveHandles().isEmpty()),
			() -> assertTrue(this.api.deleted().contains(Long.toString(program)), this.api.deleted().toString())
		);
	}

	@Test
	@DisplayName("an unshadowed draw touches no shadow state, not even the sampler")
	void unshadowedDraw_shouldNotTouchShadowState() {
		this.pipeline.prepare();
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(2));

		assertAll(
			() -> assertTrue(shadowUploads().isEmpty(), shadowUploads().toString()),
			() -> assertEquals(0, this.api.calls("uniform1i:" + location(HdShaderSource.UNIFORM_SHADOW_MAP)),
				"unit 1 must not be selected when no map exists")
		);
	}

	@Test
	@DisplayName("a shadowed draw enables the map, its radius and one frame's matrix")
	void shadowedDraw_shouldUploadTheFrame() {
		this.pipeline.prepare();
		ShadowBinding binding = binding(7, 1024, 2);

		assertTrue(this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withShadow(binding)));

		List<String> shadow = shadowUploads();
		assertAll(
			() -> assertEquals("uShadowMap=1", shadow.get(0), "the sampler is bound before the flag"),
			() -> assertEquals("uShadowEnabled=1", shadow.get(1)),
			() -> assertEquals("uShadowRadius=2", shadow.get(2)),
			() -> assertEquals("uShadowStrength=1.0", shadow.get(3),
				"a full shadow is the default and the identity in the shader"),
			() -> assertEquals(1, this.api.calls("uniform1i:" + location(HdShaderSource.UNIFORM_SHADOW_MAP)),
				"the sampler is uploaded once per program"),
			() -> assertEquals(4, shadowRows(shadow), "four matrix rows"),
			() -> assertEquals(1, countStarting(shadow, "uShadowTexel=")),
			() -> assertEquals(1, countStarting(shadow, "uShadowBias="))
		);
	}

	@Test
	@DisplayName("a second draw of the same frame re-uploads nothing shadow-related")
	void sameFrame_shouldUploadShadowOnce() {
		this.pipeline.prepare();
		ShadowBinding binding = binding(7, 1024, 2);
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withShadow(binding));
		this.pipeline.unbind();
		int before = shadowUploads().size();

		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withShadow(binding));

		assertAll(
			() -> assertEquals(before, shadowUploads().size(), "the frame's matrix is shared"),
			() -> assertEquals(2, this.api.calls("use:" + programHandle()), "the program is still bound again")
		);
	}

	@Test
	@DisplayName("a new frame re-uploads the matrix, and only the matrix")
	void nextFrame_shouldUploadTheMatrixAgain() {
		this.pipeline.prepare();
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withShadow(binding(7, 1024, 2)));
		int before = shadowUploads().size();

		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withShadow(binding(8, 1024, 2)));

		List<String> delta = shadowUploads().subList(before, shadowUploads().size());
		assertAll(
			() -> assertEquals(4, shadowRows(delta), "one matrix per frame, not per draw"),
			() -> assertEquals(6, delta.size(), "the frame's whole payload: 4 rows, texel, bias"),
			() -> assertTrue(delta.get(0).startsWith("uShadowRow0="), delta.toString()),
			() -> assertEquals(0, countStarting(delta, "uShadowEnabled"),
				"enable and radius are per draw, not per frame")
		);
	}

	@Test
	@DisplayName("turning shadows off sends the flag back to zero exactly once")
	void shadowOff_shouldClearTheFlagOnce() {
		this.pipeline.prepare();
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withShadow(binding(7, 1024, 2)));

		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0));
		int cleared = this.api.calls("uniform1i:" + location(HdShaderSource.UNIFORM_SHADOW_ENABLED) + ",0");
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0));

		assertAll(
			() -> assertEquals(1, cleared, "the disable must go up"),
			() -> assertEquals(1, this.api.calls("uniform1i:" + location(HdShaderSource.UNIFORM_SHADOW_ENABLED) + ",0"),
				"and not every unshadowed draw after it")
		);
	}

	@Test
	@DisplayName("a radius change while shadowed re-uploads just the radius")
	void radiusChange_shouldUploadOnlyTheRadius() {
		this.pipeline.prepare();
		ShadowBinding binding = binding(7, 1024, 1);
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withShadow(binding));
		int before = shadowUploads().size();

		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withShadow(binding(7, 1024, 2)));

		List<String> delta = shadowUploads().subList(before, shadowUploads().size());
		assertEquals(Arrays.asList("uShadowRadius=2"), delta);
	}

	@Test
	@DisplayName("the shadow keeps only the fraction this frame asks for")
	void shadowStrength_shouldFollowTheDraw() {
		this.pipeline.prepare();
		ShadowBinding binding = binding(7, 1024, 2);

		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withShadow(binding)
				.withStrength(0.35F));
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withShadow(binding));
		int before = shadowUploads().size();
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withShadow(binding));

		List<String> uploads = shadowUploads();
		assertAll(
			() -> assertEquals(1, countStarting(uploads, "uShadowStrength=0.35"),
				"the hour's fraction must reach the program"),
			() -> assertEquals(1, countStarting(uploads, "uShadowStrength=1.0"),
				"and go back to a full shadow without repeating itself"),
			() -> assertEquals(before, shadowUploads().size(), "an unchanged strength is not re-sent")
		);
	}

	@Test
	@DisplayName("an unshadowed draw never sends a shadow strength")
	void unshadowedDraw_shouldNotSendStrength() {
		this.pipeline.prepare();
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withStrength(0.35F));
		assertEquals(0, countStarting(shadowUploads(), "uShadowStrength"),
			"with the map off the sample is already 1.0, so the value is dead weight");
	}

	@Test
	@DisplayName("the carried and room rows travel with the draw, not with the saved settings")
	void lightRows_shouldComeFromTheRequest() {
		HdLightFrame frame = HdLightFrame.of(
			new HdLightSource(0.0F, 0.0F, 1.0F, 512.0F, 0xFFFFFF, 1.0F),
			new HdRoomLight[] { new HdRoomLight(0, 0, 1024, 1024, 0xFFFFFF, 0.35F) });
		this.pipeline.prepare();

		// The saved settings are whatever an earlier class left behind; this draw
		// says both rows are off, so nothing about the frame may reach GL.
		int start = this.api.uploads.size();
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withLights(frame));
		List<String> off = new java.util.ArrayList<String>(this.api.uploads.subList(start, this.api.uploads.size()));

		int onStart = this.api.uploads.size();
		this.pipeline.begin(draw(true, true).with(Row.CARRIED_LIGHT, Row.ROOM_LIGHT),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withLights(frame));
		List<String> on = new java.util.ArrayList<String>(this.api.uploads.subList(onStart, this.api.uploads.size()));

		int backStart = this.api.uploads.size();
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withLights(frame));
		List<String> backOff = new java.util.ArrayList<String>(this.api.uploads.subList(backStart, this.api.uploads.size()));

		assertAll(
			() -> assertEquals(0, countStarting(off, "uCarried"), "a row off must upload nothing: " + off),
			() -> assertEquals(0, countStarting(off, "uRoom"), "a row off must upload nothing: " + off),
			() -> assertTrue(on.contains("uCarriedEnabled=1"), on.toString()),
			() -> assertTrue(on.contains("uCarriedPosition=0.0,0.0,1.0,512.0"), on.toString()),
			() -> assertTrue(on.contains("uCarriedColour=1.0,1.0,1.0,1.0"), on.toString()),
			() -> assertTrue(on.contains("uRoomEnabled=1"), on.toString()),
			() -> assertTrue(on.contains("uRoomBounds0=0.0,0.0,1024.0,1024.0"), on.toString()),
			() -> assertTrue(on.contains("uRoomColour0=1.0,1.0,1.0,0.35"), on.toString()),
			// Turning both rows off again clears the flags and sends no values.
			() -> assertEquals(1, countStarting(backOff, "uCarriedEnabled=0"), backOff.toString()),
			() -> assertEquals(1, countStarting(backOff, "uRoomEnabled=0"), backOff.toString()),
			() -> assertEquals(2, backOff.size(), "only the two flags move: " + backOff)
		);
	}

	@Test
	@DisplayName("the environment row travels with the draw, and off uploads nothing at all")
	void environmentRow_shouldComeFromTheRequest() {
		HdEnvironmentLight light = HdEnvironmentLight.of(0xFF0000, 0.2F, 0.0F, 1.0F, 0.0F);
		this.pipeline.prepare();

		int start = this.api.uploads.size();
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withEnvironment(light));
		List<String> off = new java.util.ArrayList<String>(this.api.uploads.subList(start, this.api.uploads.size()));

		int onStart = this.api.uploads.size();
		this.pipeline.begin(draw(true, true).with(Row.ENVIRONMENT_LIGHT),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withEnvironment(light));
		List<String> on = new java.util.ArrayList<String>(this.api.uploads.subList(onStart, this.api.uploads.size()));

		int repeatStart = this.api.uploads.size();
		this.pipeline.begin(draw(true, true).with(Row.ENVIRONMENT_LIGHT),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withEnvironment(light));
		List<String> repeat = new java.util.ArrayList<String>(this.api.uploads.subList(repeatStart, this.api.uploads.size()));

		int backStart = this.api.uploads.size();
		this.pipeline.begin(draw(true, true),
			HdDrawSpace.MODEL,
			HdFrameState.of(0)
				.withEnvironment(light));
		List<String> backOff = new java.util.ArrayList<String>(this.api.uploads.subList(backStart, this.api.uploads.size()));

		assertAll(
			() -> assertEquals(0, countStarting(off, "uEnv"), "a draw the row was refused for uploads nothing: " + off),
			() -> assertTrue(on.contains("uEnvLighting=1"), on.toString()),
			() -> assertTrue(on.contains("uEnvSky=1.0,0.0,0.0,0.2"), on.toString()),
			() -> assertTrue(on.contains("uEnvUp=0.0,1.0,0.0,0.0"), on.toString()),
			() -> assertEquals(0, countStarting(repeat, "uEnv"), "an unchanged environment is not re-sent: " + repeat),
			() -> assertEquals(1, countStarting(backOff, "uEnvLighting=0"), backOff.toString()),
			() -> assertEquals(1, backOff.size(), "and the return to stock is one flag: " + backOff)
		);
	}

	@Test
	@DisplayName("an environment row with no sky is a no-op, not a black frame")
	void identityEnvironment_shouldUploadNothing() {
		this.pipeline.prepare();
		int start = this.api.uploads.size();

		this.pipeline.begin(draw(true, true).with(Row.ENVIRONMENT_LIGHT),
			HdDrawSpace.MODEL,
			HdFrameState.of(0));

		List<String> uploads = this.api.uploads.subList(start, this.api.uploads.size());
		assertEquals(0, countStarting(uploads, "uEnv"), uploads.toString());
	}

	@Test
	@DisplayName("the caustics term travels with the draw, and a dry or off draw uploads nothing")
	void causticsRow_shouldComeFromTheRequestAndTheTile() {
		HdGroundWater wet = HdGroundWater.of(200);
		this.pipeline.prepare();

		int offStart = this.api.uploads.size();
		this.pipeline.begin(draw(true, true).withWater(wet),
			HdDrawSpace.MODEL,
			HdFrameState.of(0).withWaterSeconds(1.5F));
		List<String> off = new java.util.ArrayList<String>(this.api.uploads.subList(offStart, this.api.uploads.size()));

		int dryStart = this.api.uploads.size();
		this.pipeline.begin(draw(true, true).with(Row.CAUSTICS).withWater(HdGroundWater.DRY),
			HdDrawSpace.MODEL,
			HdFrameState.of(0).withWaterSeconds(1.5F));
		List<String> dry = new java.util.ArrayList<String>(this.api.uploads.subList(dryStart, this.api.uploads.size()));

		int wetStart = this.api.uploads.size();
		this.pipeline.begin(draw(true, true).with(Row.CAUSTICS).withWater(wet),
			HdDrawSpace.MODEL,
			HdFrameState.of(0).withWaterSeconds(1.5F));
		List<String> on = new java.util.ArrayList<String>(this.api.uploads.subList(wetStart, this.api.uploads.size()));

		int repeatStart = this.api.uploads.size();
		this.pipeline.begin(draw(true, true).with(Row.CAUSTICS).withWater(wet),
			HdDrawSpace.MODEL,
			HdFrameState.of(0).withWaterSeconds(1.5F));
		List<String> repeat = new java.util.ArrayList<String>(this.api.uploads.subList(repeatStart, this.api.uploads.size()));

		int clockStart = this.api.uploads.size();
		this.pipeline.begin(draw(true, true).with(Row.CAUSTICS).withWater(wet),
			HdDrawSpace.MODEL,
			HdFrameState.of(0).withWaterSeconds(2.0F));
		List<String> clock = new java.util.ArrayList<String>(this.api.uploads.subList(clockStart, this.api.uploads.size()));

		int backStart = this.api.uploads.size();
		this.pipeline.begin(draw(true, true).withWater(wet),
			HdDrawSpace.MODEL,
			HdFrameState.of(0).withWaterSeconds(2.0F));
		List<String> backOff = new java.util.ArrayList<String>(this.api.uploads.subList(backStart, this.api.uploads.size()));

		assertAll(
			() -> assertEquals(0, countStarting(off, "uCaustics"), "the row off uploads nothing: " + off),
			() -> assertEquals(0, countStarting(dry, "uCaustics"),
				"no water over the tile is the same identity: " + dry),
			() -> assertTrue(on.contains("uCaustics=" + (200 / 255.0F) + ",1.5,0.0,0.0"), on.toString()),
			() -> assertEquals(1, countStarting(on, "uCaustics"), "one value carries strength and clock: " + on),
			() -> assertEquals(0, countStarting(repeat, "uCaustics"),
				"the same tile at the same clock is not re-sent: " + repeat),
			() -> assertEquals(1, countStarting(clock, "uCaustics"), clock.toString()),
			() -> assertTrue(clock.contains("uCaustics=" + (200 / 255.0F) + ",2.0,0.0,0.0"),
				"the clock rides the same upload: " + clock),
			// Turning the row off must clear the uniform, or the previous draw's
			// pattern would still be in the program for the next one.
			() -> assertEquals(1, countStarting(backOff, "uCaustics"), backOff.toString()),
			() -> assertTrue(backOff.contains("uCaustics=0.0,0.0,0.0,0.0"), backOff.toString()),
			() -> assertEquals(3L, this.pipeline.causticsUploads())
		);
	}

	@Test
	@DisplayName("the reflection term travels with the material's slot, and off uploads nothing")
	void reflectionRow_shouldComeFromTheRequest() {
		this.pipeline.prepare();

		int plainStart = this.api.uploads.size();
		this.pipeline.begin(draw(true, true).with(Row.REFLECTIONS).withMaterialEffectSlot(-1),
			HdDrawSpace.MODEL,
			HdFrameState.of(0));
		List<String> plain = new java.util.ArrayList<String>(this.api.uploads.subList(plainStart, this.api.uploads.size()));

		int offStart = this.api.uploads.size();
		this.pipeline.begin(draw(true, true).withMaterialEffectSlot(1),
			HdDrawSpace.MODEL,
			HdFrameState.of(0));
		List<String> off = new java.util.ArrayList<String>(this.api.uploads.subList(offStart, this.api.uploads.size()));

		int onStart = this.api.uploads.size();
		this.pipeline.begin(draw(true, true).with(Row.REFLECTIONS).withMaterialEffectSlot(1),
			HdDrawSpace.MODEL,
			HdFrameState.of(0));
		List<String> on = new java.util.ArrayList<String>(this.api.uploads.subList(onStart, this.api.uploads.size()));

		int repeatStart = this.api.uploads.size();
		this.pipeline.begin(draw(true, true).with(Row.REFLECTIONS).withMaterialEffectSlot(1),
			HdDrawSpace.MODEL,
			HdFrameState.of(0));
		List<String> repeat = new java.util.ArrayList<String>(this.api.uploads.subList(repeatStart, this.api.uploads.size()));

		int clearStart = this.api.uploads.size();
		this.pipeline.begin(draw(true, true).with(Row.REFLECTIONS).withMaterialEffectSlot(0),
			HdDrawSpace.MODEL,
			HdFrameState.of(0));
		List<String> clear = new java.util.ArrayList<String>(this.api.uploads.subList(clearStart, this.api.uploads.size()));

		assertAll(
			() -> assertEquals(0, countStarting(plain, "uReflection="),
				"no material, no reflection: " + plain),
			() -> assertEquals(0, countStarting(off, "uReflection="),
				"the row off uploads nothing: " + off),
			() -> assertTrue(on.contains("uReflection=" + HdReflection.STRENGTH + ",0.0,0.0,0.0"), on.toString()),
			// GL rejects two sampler types on one unit, so the cube's unit is
			// selected at the first bind with the material's, not left at zero.
			() -> assertTrue(plain.contains("uReflectionCube=" + HdShaderSource.SAMPLER_UNIT_REFLECTION),
				"the cube sampler's unit is selected at the first bind: " + plain),
			() -> assertEquals(0, countStarting(on, "uReflectionCube="),
				"a reflecting draw does not move the sampler's unit: " + on),
			() -> assertEquals(1, countStarting(on, "uReflection="), "one value carries the strength: " + on),
			() -> assertEquals(0, countStarting(repeat, "uReflection="),
				"the same material is not re-sent: " + repeat),
			// A plain material drawn after a reflective one must clear the uniform,
			// or the previous surface's reflection would stay in the program.
			() -> assertTrue(clear.contains("uReflection=0.0,0.0,0.0,0.0"), clear.toString()),
			() -> assertEquals(2L, this.pipeline.reflectionUploads())
		);
	}

	/** Every uniform upload whose name starts with {@code uShadow}. */
	private List<String> shadowUploads() {
		List<String> shadow = new java.util.ArrayList<String>();
		for (String upload : this.api.uploads) {
			if (upload.startsWith("uShadow")) {
				shadow.add(upload);
			}
		}
		return shadow;
	}

	private static int shadowRows(List<String> uploads) {
		int rows = 0;
		for (String upload : uploads) {
			if (upload.startsWith("uShadowRow")) {
				rows++;
			}
		}
		return rows;
	}

	private int location(String uniform) {
		return this.locations.get(uniform).intValue();
	}

	private static int countStarting(List<String> uploads, String prefix) {
		int count = 0;
		for (String upload : uploads) {
			if (upload.startsWith(prefix)) {
				count++;
			}
		}
		return count;
	}

	/** A frame's map payload with a distinctive matrix so uploads are identifiable. */
	private static ShadowBinding binding(int frameId, int size, int radius) {
		float[] matrix = new float[16];
		for (int i = 0; i < matrix.length; i++) {
			matrix[i] = frameId + i * 0.001F;
		}
		return new ShadowBinding(frameId, size, radius, 0.002F, matrix);
	}

	/** The single program handle the compile handed out and kept alive. */
	private long programHandle() {
		return this.api.liveHandles().iterator().next().longValue();
	}
}
