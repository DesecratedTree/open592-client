package com.jagex.game.runetek5.client.hd;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.PcmPlayerThread;
import com.jagex.game.runetek5.buffer.ArrayBufferGL;
import com.jagex.game.runetek5.buffer.ElementsBufferGL;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.GlGroundLayer;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.client.hd.ground.HdMaterialMap;
import com.jagex.game.runetek5.client.hd.ground.HdTileMaterialMap;
import com.jagex.game.runetek5.client.hd.shadow.ShadowCutout;
import com.jagex.game.runetek5.client.hd.shadow.SunShadowMap;
import com.jagex.game.runetek5.client.hd.sky.HdSkyTime;
import com.jagex.game.runetek5.client.hd.state.HdGlState;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.graphics.sprite.SpriteGL;
import com.jagex.game.runetek5.graphics.texture.OpenGl2DImageTexture;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import com.jagex.game.runetek5.unpack_Sub1;

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
 * The frame contract on a real driver: at every pass boundary of one frame, GL
 * is in the state the toolkit's caches claim, and in the state a stock frame
 * would be in.
 *
 * <p>This is the measurement the visibility bugs needed. {@code method1958}
 * returns without a GL call when the blend mode it is asked for is already the
 * cached one, and every state setter in the toolkit does the same; one pass that
 * moves GL behind the cache's back therefore turns into a frame where a blend
 * function, a depth mask, an alpha-test function or a texture binding is the
 * pass's rather than the draw's - washed colours, a translucent interface, water
 * over the world. Which bit it is cannot be read off the symptoms, so it is
 * measured: one frame is run in client order (clear, sky, sun shadows, world,
 * water, interface, screen-space pass, frame end), and at each boundary the
 * actual GL state is compared with the cache's claim and with the same boundary
 * of the 592HD-off frame.
 *
 * <p>The frame under test draws the three surfaces the reports name, not stand-ins
 * for them:
 *
 * <ul>
 * <li><b>The world is real terrain</b> - a {@link GlGroundLayer} with a material
 * the atlas pages, drawn by the scene's own call
 * ({@code method1462 -> hdBeginGroundLighting}) under the ground pass' state
 * ({@code method1904}: depth test and depth writes on). The measured frame runs
 * after a warm-up frame, so the page exists and the terrain's draw samples its
 * atlas cell rather than the stock material texture.</li>
 * <li><b>The water contends with that depth</b> - the same footprint, drawn
 * behind the land, must change no land pixel at all; the same water pass drawn
 * in front paints the ring the land does not cover, so the zero is a depth
 * rejection and not a dead pass.</li>
 * <li><b>The interface is a textured, alpha-masked sprite</b> - a real
 * {@link SpriteGL} from the client's own sprite constructor, half-transparent in
 * its core and clear at its border, drawn by {@code NativeSprite.render} with
 * the interface's blend mode. Its core must land exactly on the source-over
 * blend of its own colour at its alpha, and its border must write nothing.</li>
 * </ul>
 *
 * <p>The three comparisons are different claims. Cache against actual says no
 * pass has lied to the toolkit about the blend, the masks, the alpha-test
 * function, the depth function or the bound program; HD against stock says no
 * pass has left a bit behind that the stock frame would not have; and the pixels
 * say what the draws actually sampled and wrote. A failure names the boundary,
 * the field or the surface rather than a washed screen.
 *	 * <p>Runs by default; {@link GlTestContext#open()} skips it with the reason
	 * when this machine cannot give a GL context.
 */
@DisplayName("592HD frame contract on a real GL driver")
class HdFrameContractGlTest {

	private static final int SIZE = 64;

	/** The scaling {@code GlToolkit.createGround} builds the ground pass with. */
	private static final int TILE_UNITS = 128;

	private static final float SUN_X = 0.0F;

	private static final float SUN_Y = 0.6F;

	private static final float SUN_Z = 0.8F;

	private static final int CAMERA_X = -64;

	private static final int CAMERA_Y = -32;

	private static final int CAMERA_Z = 200;

	private static final float FAR = 3584.0F;

	/** The terrain tile's material; the atlas pages this one id. */
	private static final int TERRAIN_MATERIAL = 2;

	/** The stock texture-effect enum whose combine is {@code GL_MODULATE}. */
	private static final int MODULATE_EFFECT = 1;

	/** The material's art: pure red, so no lighting term can turn it grey. */
	private static final int TERRAIN_ART = 0xFF0000;

	/**
	 * A material the stock upload can texture but the page cannot: its sampler
	 * answers the stock call, then null. The cell the map publishes for it would
	 * be copied from that null and stay empty.
	 */
	private static final int TERRAIN_NO_ART = 3;

	/** The same, with art shorter than one cell instead of nothing at all. */
	private static final int TERRAIN_SHORT_ART = 4;

	/** The art the stock upload of {@link #TERRAIN_NO_ART} holds: pure green. */
	private static final int NO_ART_ART = 0x00FF00;

	/** The art the stock upload of {@link #TERRAIN_SHORT_ART} holds: pure blue. */
	private static final int SHORT_ART_ART = 0x0000FF;

	/** The interface panel: a half-transparent green core, clear border. */
	private static final int PANEL_ART = 0x00FF00;

	/** The panel's alpha in its core: half, so the blend is measurable. */
	private static final int PANEL_OPACITY = 0x80;

	/**
	 * The panel is a power-of-two sprite (64x64): the toolkit uploads a non-power
	 * -of-two sprite as a rectangle texture, which is a different target and a
	 * different sampling path from the one interface art normally takes.
	 */
	private static final int PANEL_RECT = 0;

	private static final int PANEL_SIZE = 64;

	private static final int PANEL_CORE_FROM = 20;

	private static final int PANEL_CORE_TO = 44;

	/** The water the HD pass draws: a blue no terrain or sky colour can imitate. */
	private static final int WATER_ART = 0x2060C0;

	private static final int WATER_TEXTURE_SIZE = 8;

	/**
	 * The depth below which a pixel is land: anything the world pass wrote.
	 *
	 * <p>Not 0.99. This frame's projection is the client's own
	 * ({@code ia(1, FAR)}), whose depth is non-linear over a 1..3584 range, so
	 * terrain 200 units away lands near 0.995 - well under the clear value, and
	 * well over 0.99. A threshold at 0.99 called that unwritten and made the whole
	 * terrain measurement fall back to a pixel no draw had touched.
	 */
	private static final float LAND_DEPTH = 0.999F;

	/** The boundaries, in the order one frame reaches them. */
	private static final String[] BOUNDARIES = {
		"start", "clear", "sky", "shadows", "world", "water", "interface", "post", "end"
	};

	private static GlToolkit toolkit;

	private static Frame frame;

	private static Canvas canvas;

	private static int framebuffer;

	private static int targetTexture;

	private static int depthBuffer;

	/** The model quad: the sky-shadow caster and the stock model draw. */
	private static ElementsBufferGL quadBuffer;

	private static ArrayBufferGL quadIndices;

	private static Class177 quadColours;

	private static Class177 quadNormals;

	private static Class177 quadVertices;

	/** The scene's terrain: one layer whose tile carries {@link #TERRAIN_MATERIAL}. */
	private static GlGround ground;

	private static GlGroundLayer terrain;

	/** The same mesh drawn by a layer whose material's sampler answers null to the page. */
	private static GlGroundLayer terrainNoArt;

	/** The same mesh drawn by a layer whose material's sampler answers short art. */
	private static GlGroundLayer terrainShortArt;

	private static GlVboElementsArrayBuffer terrainBuffer;

	/** The water surface, drawn twice: behind the land and in front of it. */
	private static ElementsBufferGL waterBuffer;

	private static Class177 waterVertices;

	private static Class177 waterColours;

	private static ArrayBufferGL waterIndices;

	private static OpenGl2DImageTexture waterTexture;

	/** The interface's panel: the client's own sprite, with a real alpha mask. */
	private static SpriteGL panel;

	/**
	 * Interface art shaped like a real panel: an opaque body, a half-transparent
	 * edge ring and a clear border. Uploaded twice - once with the 592HD rows on
	 * and once with them off - so what reaches GL can be compared, not assumed.
	 */
	private static int[] interfaceArt;

	/** The texels the 592HD-mode upload left on the sprite's texture, or null. */
	private static int[] hdUpload;

	/** The texels the stock-mode upload left, or null. */
	private static int[] stockUpload;

	/** The panel's drawn pixels over an opaque backdrop, 592HD rows on. */
	private static int[] hdPanelPixels;

	/** The same draw with the rows off. */
	private static int[] stockPanelPixels;

	private static FrameMaterials materials;

	/** The toolkit the client would have current; restored for the next class. */
	private static Toolkit previousToolkit;

	@BeforeAll
	static void openToolkit() throws Exception {
		try {
			GlTestContext warmup = GlTestContext.open();
			warmup.close();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}
		frame = new Frame("592HD frame contract test");
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(SIZE, SIZE));
		frame.add(canvas);
		frame.pack();
		frame.setLocation(-4000, -4000);
		// The toolkit builds its projection from the canvas' reported size, and
		// its viewport from the same numbers. A packed frame that settled on a
		// different size than the offscreen target would project the whole frame
		// through a window that is not the one being read back - measured as a
		// 120x64 surface against a 64x64 target, which halved every x and made
		// both the terrain and the panel cover half the columns they should.
		frame.setSize(SIZE, SIZE);
		frame.setVisible(true);
		frame.validate();
		canvas.setSize(SIZE, SIZE);
		System.out.println("[hd-frame] canvas " + canvas.getSize());
		try {
			materials = new FrameMaterials();
			toolkit = new GlToolkit(0, canvas, materials, 0, null);
		} catch (Throwable error) {
			frame.dispose();
			assumeTrue(false, "GlToolkit could not start in this JVM: " + error);
		}
		System.out.println("[hd-frame] toolkit " + GL11.glGetString(GL11.GL_RENDERER) + " | GL "
			+ GL11.glGetString(GL11.GL_VERSION));
		// HdFrameSequence reaches the drawing toolkit through the client's own
		// static, exactly as it does in a live frame.
		previousToolkit = MonoStereoData.aClass19_16;
		MonoStereoData.aClass19_16 = toolkit;

		Path preferences = Files.createTempDirectory("hd-frame");
		HdSettings.setDirectoryForTest(preferences.toFile());
		HdSettings.setEnabled(true, null);
		for (HdFeature feature : new HdFeature[] { HdFeature.SKY, HdFeature.DAY_NIGHT,
				HdFeature.TONEMAP, HdFeature.WORLD_TEXTURES, HdFeature.MATERIAL_MAPS,
				// The water's surface reads the fog band's colour and the fog band's
				// far plane; a frame with the row off hands the shader a zero fog
				// and paints the water in the fog colour, black. A live frame has
				// the band, so this one does too.
				HdFeature.FOG }) {
			HdSettings.set(feature, 1);
		}
		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 1);
		HdSettings.set(HdFeature.SHADOW_MAP, 2);
		HdSettings.set(HdFeature.WATER_HD, 1);
		for (HdFeature feature : new HdFeature[] { HdFeature.POINT_LIGHTS,
				HdFeature.DISTANCE_FADE, HdFeature.AMBIENT_OCCLUSION, HdFeature.CARRIED_LIGHT,
				HdFeature.ROOM_LIGHT, HdFeature.ENVIRONMENT_LIGHTING, HdFeature.CAUSTICS,
				HdFeature.REFLECTIONS, HdFeature.FOLIAGE_SWAY, HdFeature.GRASS_DISPLACEMENT,
				HdFeature.TERRAIN_BLEND }) {
			HdSettings.set(feature, 0);
		}
		createTarget();
		toolkit.setHeap(toolkit.createHeap(1 << 26));
		createGeometry();
		createTerrain();
		createWater();
		createPanel();
		// method1462 packs the layer's visible indices through this packet.
		PcmPlayerThread.aClass4_Sub12_7 = new Packet(64);
		// The scene's view, camera and sun, set the way the client sets them
		// before a frame: the 2D window over the canvas, the camera the ground
		// seam reads for scene-to-eye, and the projection the pass loads.
		toolkit.H(SIZE / 2, SIZE / 2, SIZE, SIZE);
		JavaMatrix camera = new JavaMatrix();
		camera.scaleWidth(CAMERA_X, CAMERA_Y, CAMERA_Z);
		toolkit.ea(camera);
		toolkit.ia(1, (int) FAR);
		toolkit.scaleHeight(0xFFFFFF, 1.0F, SUN_Y, 0.0F, -SUN_Y, SUN_Z);
		toolkit.B(0.2F);
		HdSkyTime.reset();
		// One frame whose only job is the page: a material's first atlas request
		// builds the page and draws the stock texture, and the frame after it
		// samples the cell. The measured frames are all frames after it.
		runFrame(true);
		measureInterfaceUploads();
	}

	@AfterAll
	static void closeToolkit() {
		HdSettings.resetForTest();
		HdSkyTime.reset();
		MonoStereoData.aClass19_16 = previousToolkit;
		if (frame != null) {
			frame.dispose();
		}
	}

	/**
	 * One frame, in the order the client draws it, with a snapshot at every
	 * boundary: what GL has, and what the toolkit's caches claim it has.
	 */
	@Test
	@DisplayName("every pass boundary leaves GL in the state the caches claim")
	void frame_shouldKeepTheCachesTruthful() {
		FrameRun run = runFrame(true);
		assertPassesRan(run);

		StringBuilder report = new StringBuilder();
		for (String boundary : BOUNDARIES) {
			print(boundary, run.states.get(boundary));
		}
		for (String boundary : BOUNDARIES) {
			List<String> differences = run.states.get(boundary).differences(run.caches.get(boundary));
			if (!differences.isEmpty()) {
				report.append(boundary).append(": ").append(differences).append('\n')
					.append("  actual ").append(run.states.get(boundary)).append('\n')
					.append("  cache  ").append(run.caches.get(boundary)).append('\n');
			}
		}
		assertTrue(report.length() == 0,
			"the toolkit's caches disagree with GL at these boundaries:\n" + report);
	}

	/**
	 * The same frame with every 592HD row on, against the same frame with 592HD
	 * off: a boundary that differs is a pass that did not put GL back.
	 */
	@Test
	@DisplayName("a 592HD frame leaves the same GL state at every boundary as a stock frame")
	void frame_shouldMatchTheStockPathAtEveryBoundary() {
		FrameRun stock = runFrame(false);
		assertPassesDeclined(stock);
		FrameRun hd = runFrame(true);
		assertPassesRan(hd);

		StringBuilder report = new StringBuilder();
		for (String boundary : BOUNDARIES) {
			print(boundary, stock.states.get(boundary));
			List<String> differences = hd.states.get(boundary).differences(stock.states.get(boundary));
			if (!differences.isEmpty()) {
				report.append(boundary).append(": ").append(differences).append('\n')
					.append("  592HD ").append(hd.states.get(boundary)).append('\n')
					.append("  stock ").append(stock.states.get(boundary)).append('\n');
			}
		}
		assertTrue(report.length() == 0,
			"592HD changed GL state that the stock frame leaves behind:\n" + report);
	}

	/**
	 * The three surfaces the reports name, measured in the frame that draws them.
	 *
	 * <p>Each measurement is a property of the pixels, not of the code path: the
	 * terrain's colour is its material's red (a colourless or black tile fails),
	 * the depth it wrote is below the clear value, water drawn behind it changes
	 * none of its pixels while the same pass in front changes the empty ring
	 * around it, and the interface's half-transparent core lands on the
	 * source-over blend of its own colour at its own alpha while its transparent
	 * border writes nothing.
	 */
	@Test
	@DisplayName("the terrain, the water and the interface draw what they sample")
	void reportedSurfaces_shouldDrawWhatTheySample() {
		FrameRun run = runFrame(true);
		assertPassesRan(run);

		// The terrain's own pixel: the land fragment nearest the colour target's
		// centre, found from the depth the terrain wrote rather than assumed, so
		// the measurement is about what the draw produced and not about where a
		// fixture put it.
		int terrain = nearestLand(run.worldDepth, SIZE / 2, SIZE / 2);
		int terrainRed = channel(run.worldPixels[terrain], 16);
		int terrainGreen = channel(run.worldPixels[terrain], 8);
		int terrainBlue = channel(run.worldPixels[terrain], 0);
		int terrainDepth = Math.round(run.worldDepth[terrain] * 1000.0F);
		int waterOverLand = changedWhereDepth(run.worldDepth, run.worldPixels, run.waterBehindPixels, true);
		int waterOverClear = changedWhereDepth(run.worldDepth, run.worldPixels, run.waterPixels, false);
		int[] panelBox = changedBox(run.waterPixels, run.interfacePixels);
		int panelX = panelBox == null ? 0 : (panelBox[0] + panelBox[2]) / 2;
		int panelY = panelBox == null ? 0 : (panelBox[1] + panelBox[3]) / 2;
		int panelBackground = pixel(run.waterPixels, panelX, panelY);
		int panelCore = pixel(run.interfacePixels, panelX, panelY);
		int borderX = panelBox == null ? 2 : Math.max(0, panelBox[0] - 2);
		int panelBorder = pixel(run.interfacePixels, borderX, panelY);
		int panelBorderBackground = pixel(run.waterPixels, borderX, panelY);
		int panelExpected = blend(panelBackground, PANEL_ART, PANEL_OPACITY);

		int panelPixels = changedFrom(run.waterPixels, run.interfacePixels);
		int[] landBox = landBox(run.worldDepth);

		System.out.println("[hd-frame] terrain: atlasBinds=" + run.atlasBinds + " pageBuilds="
			+ run.pageBuilds + " at=" + (terrain % SIZE) + "," + (SIZE - 1 - terrain / SIZE) + " rgb="
			+ terrainRed + "/" + terrainGreen + "/" + terrainBlue + " depth=" + terrainDepth + "/1000"
			+ " landBox=" + Arrays.toString(landBox));
		System.out.println("[hd-frame] water sample: world="
			+ Integer.toHexString(pixel(run.worldPixels, 32, 32)) + " behind="
			+ Integer.toHexString(pixel(run.waterBehindPixels, 32, 32)) + " front="
			+ Integer.toHexString(pixel(run.waterPixels, 32, 32)) + " | corner world="
			+ Integer.toHexString(pixel(run.worldPixels, 2, 2)) + " behind="
			+ Integer.toHexString(pixel(run.waterBehindPixels, 2, 2)) + " front="
			+ Integer.toHexString(pixel(run.waterPixels, 2, 2)));
		System.out.println("[hd-frame] water depth: world=" + landPixels(run.worldDepth)
			+ " afterWater=" + landPixels(run.waterDepth) + " waterOnly="
			+ depthAdded(run.worldDepth, run.waterDepth));
		System.out.println("[hd-frame] water: landPixels=" + landPixels(run.worldDepth)
			+ " changedOverLand=" + waterOverLand + " changedOverClear=" + waterOverClear
			+ " face0=" + changedFrom(run.worldPixels, run.waterBehindPixels) + " face1="
			+ changedFrom(run.waterBehindPixels, run.waterPixels) + " colour="
			+ Integer.toHexString(pixel(run.waterPixels, 2, 2)));
		System.out.println("[hd-frame] interface: panelPixels=" + panelPixels + " box="
			+ Arrays.toString(panelBox) + " at=" + panelX + "," + panelY + " core "
			+ Integer.toHexString(panelCore) + " expected " + Integer.toHexString(panelExpected)
			+ " over " + Integer.toHexString(panelBackground) + " at alpha " + PANEL_OPACITY
			+ "/255, border " + Integer.toHexString(panelBorder) + " over "
			+ Integer.toHexString(panelBorderBackground));

		assertAll(
			// The terrain: its own material, through the page, with depth written.
			() -> assertEquals(1L, run.atlasBinds,
				"the terrain's draw must bind the world-texture atlas: binds=" + run.atlasBinds),
			() -> assertTrue(terrainRed > 60,
				"the terrain must draw its material's red, not a colourless tile: rgb=" + terrainRed
					+ "/" + terrainGreen + "/" + terrainBlue),
			() -> assertTrue(terrainRed > 2 * terrainGreen && terrainRed > 2 * terrainBlue,
				"the terrain must keep its material's hue: rgb=" + terrainRed + "/" + terrainGreen
					+ "/" + terrainBlue),
			() -> assertTrue(run.worldDepth[terrain] < LAND_DEPTH,
				"the terrain must write depth for the water behind it to test against: depth="
					+ terrainDepth + "/1000"),
			// The water: rejected where the land wrote depth, drawn where it did not.
			() -> assertEquals(0, waterOverLand,
				"water drawn behind the land must change no land pixel - it is depth-rejected, "
					+ "not painted over: " + waterOverLand + " of " + landPixels(run.worldDepth)
					+ " changed"),
			() -> assertTrue(waterOverClear > 100,
				"the same water pass must paint where no land occludes it, or the zero above is a "
					+ "dead pass: " + waterOverClear + " px changed"),
			// The interface: a textured, alpha-masked, blended panel.
			() -> assertTrue(panelPixels > 200,
				"the interface's panel must draw: " + panelPixels + " px changed"),
			() -> assertBlend(panelExpected, panelCore,
				"the panel's alpha-masked core must draw its own colour at its alpha over what is "
					+ "behind it: " + Integer.toHexString(panelBackground) + " under "
					+ Integer.toHexString(PANEL_ART) + " at " + PANEL_OPACITY + "/255"),
			() -> assertEquals(panelBorderBackground, panelBorder,
				"the panel's transparent border must write nothing: "
					+ Integer.toHexString(panelBorderBackground) + " -> "
					+ Integer.toHexString(panelBorder)),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	/**
	 * The gap the faithful frame left open: a material the map publishes but the
	 * page cannot fill.
	 *
	 * <p>The stock path and the page read the same sampler, but not at the same
	 * time: a material's stock texture is built on its first draw, and the page
	 * copies the material when it next rebuilds. A sampler that has stopped
	 * answering - null, or art shorter than one cell - leaves the cell the map
	 * already published empty, and an empty cell is zeroes: black, with alpha
	 * zero. The ground pass blends and alpha-tests ({@code GREATER} at 0), so that
	 * fragment is discarded outright - no colour and no depth written - and the
	 * water and sky behind the tile draw through it. That is the reported pair,
	 * not a dark texture.
	 *
	 * <p>Both cases are drawn by the same mesh and the same layer call as the
	 * reference terrain, so the pixel and the depth are read at the reference's
	 * own land pixel: whatever the material does, the geometry is at that spot.
	 * A material the page cannot fill must keep the stock texture, which is what
	 * {@code HdMaterialMap.register} enforces by refusing a cell whose art the
	 * page's own test does not find.
	 */
	@Test
	@DisplayName("a material the page cannot fill draws the stock texture, not an empty cell")
	void unfilledAtlasCells_shouldDrawTheStockTexture() {
		FrameRun reference = runFrame(true);
		int at = nearestLand(reference.worldDepth, SIZE / 2, SIZE / 2);
		assertAll(
			() -> assertTrue(reference.worldDepth[at] < LAND_DEPTH,
				"the reference terrain must land on screen, or the two below are measured off its "
					+ "geometry"),
			() -> assertTrue(channel(reference.worldPixels[at], 16) > 60,
				"the reference terrain must draw its material's red"));
		FrameRun noArt = afterPageBuild(terrainNoArt);
		FrameRun shortArt = afterPageBuild(terrainShortArt);
		int noArtDepth = Math.round(noArt.worldDepth[at] * 1000.0F);
		int shortDepth = Math.round(shortArt.worldDepth[at] * 1000.0F);
		System.out.println("[hd-frame] unfilled cell at " + (at % SIZE) + ","
			+ (SIZE - 1 - at / SIZE) + ": null art rgb=" + channels(noArt.worldPixels[at])
			+ " depth=" + noArtDepth + "/1000 land=" + landPixels(noArt.worldDepth)
			+ "; short art rgb=" + channels(shortArt.worldPixels[at]) + " depth=" + shortDepth
			+ "/1000 land=" + landPixels(shortArt.worldDepth));
		assertAll(
			() -> assertTrue(noArt.worldDepth[at] < LAND_DEPTH,
				"a material whose sampler returns null must still draw its stock texture, not a "
					+ "discarded fragment: depth=" + noArtDepth + "/1000"),
			() -> assertHue(noArt.worldPixels[at], NO_ART_ART, "the null-art material"),
			() -> assertTrue(shortArt.worldDepth[at] < LAND_DEPTH,
				"a material whose sampler returns short art must still draw its stock texture, not a "
					+ "discarded fragment: depth=" + shortDepth + "/1000"),
			() -> assertHue(shortArt.worldPixels[at], SHORT_ART_ART, "the short-art material"));
	}

	/**
	 * The frame's alpha channel, which nothing has measured yet.
	 *
	 * <p>The live client clears the frame with a fog colour whose alpha byte is
	 * zero ({@code Js5MasterIndex.anInt6110}), so every pixel starts transparent
	 * and alpha is only ever what the draws leave: an opaque draw sets it to 1, and
	 * a blend toward a transparent backdrop leaves a fraction. That is the one
	 * channel a window compositor can turn into a see-through interface, and it is
	 * also the channel the screen-space pass decides, because the pass copies the
	 * frame and draws it back with blending off - its fragment stage's alpha is
	 * the presented alpha for the whole window.
	 *
	 * <p>Read at the reference terrain's own pixel, at a panel pixel whose backdrop
	 * is that terrain, and at a panel pixel whose backdrop is the sky, in three
	 * frames: 592HD with the screen-space pass, 592HD with its rows off, and
	 * stock. The window's own alpha bits are read too: a surface with no alpha
	 * channel cannot show any of this however translucent the frame is.
	 */
	@Test
	@DisplayName("the presented frame is as opaque with 592HD as without it")
	void presentedAlpha_shouldNotBeTranslucent() {
		FrameRun hd = runFrame(true, terrain, LIVE_CLEAR);
		FrameRun stock = runFrame(false, terrain, LIVE_CLEAR);
		// The screen-space pass is measured off, then every row that turns it on
		// is put back to the value it had: a row left off here is a row the next
		// test draws without, and which test ran first would decide whether the
		// suite noticed.
		HdFeature[] postRows = { HdFeature.TONEMAP, HdFeature.COLOUR_GRADING, HdFeature.DITHER,
				HdFeature.FXAA };
		int[] postValues = new int[postRows.length];
		FrameRun noPost;
		try {
			for (int row = 0; row < postRows.length; row++) {
				postValues[row] = HdSettings.value(postRows[row]);
				HdSettings.set(postRows[row], 0);
			}
			noPost = runFrame(true, terrain, LIVE_CLEAR);
		} finally {
			for (int row = 0; row < postRows.length; row++) {
				HdSettings.set(postRows[row], postValues[row]);
			}
		}
		// The measurement is scoped: a row left off here would be a row the rest
		// of the suite draws without, and which test ran first would then decide
		// what the frame tests measured.
		for (int row = 0; row < postRows.length; row++) {
			int expected = postValues[row];
			HdFeature feature = postRows[row];
			assertEquals(expected, HdSettings.value(feature),
				feature + " must be back where the measurement found it: " + expected + " vs "
					+ HdSettings.value(feature));
		}

		int world = nearestLand(hd.worldDepth, SIZE / 2, SIZE / 2);
		int panelOverWorld = panelPixel(hd, world, true);
		int panelOverSky = panelPixel(hd, world, false);
		System.out.println("[hd-frame] presented alpha (592HD post, 592HD no post, stock): world="
			+ hd.alpha[world] + "," + noPost.alpha[world] + ","
			+ stock.alpha[world] + " panelOverWorld=" + hd.alpha[panelOverWorld]
			+ "," + noPost.alpha[panelOverWorld] + ","
			+ stock.alpha[panelOverWorld] + " panelOverSky="
			+ hd.alpha[panelOverSky] + "," + noPost.alpha[panelOverSky] + ","
			+ stock.alpha[panelOverSky] + " | at the interface boundary: world "
			+ hd.interfaceAlpha[world] + "/" + stock.interfaceAlpha[world]
			+ ", panelOverWorld " + hd.interfaceAlpha[panelOverWorld] + "/"
			+ stock.interfaceAlpha[panelOverWorld] + ", panelOverSky "
			+ hd.interfaceAlpha[panelOverSky] + "/"
			+ stock.interfaceAlpha[panelOverSky] + "; window alpha bits="
			+ windowAlphaBits());

		assertAll(
			() -> assertEquals(255, hd.alpha[world],
				"the screen-space pass must present an opaque world: alpha=" + hd.alpha[world]),
			() -> assertEquals(255, hd.alpha[panelOverWorld],
				"and an opaque panel over it: alpha=" + hd.alpha[panelOverWorld]),
			() -> assertEquals(255, hd.alpha[panelOverSky],
				"and an opaque panel over the sky: alpha=" + hd.alpha[panelOverSky]),
			() -> assertEquals(stock.alpha[world], noPost.alpha[world],
				"without the screen-space pass 592HD must leave the world's alpha where stock leaves it"),
			() -> assertEquals(stock.alpha[panelOverWorld], noPost.alpha[panelOverWorld],
				"and must not leave a panel over the world more translucent than stock does"),
			() -> assertEquals(stock.alpha[panelOverSky], noPost.alpha[panelOverSky],
				"and must not leave a panel over the sky more translucent than stock does"));
	}

	/**
	 * The upload and the blend of an interface panel, in both paths.
	 *
	 * <p>Interface art is uploaded by the client's own sprite constructor, and
	 * that constructor is not a 592HD seam: it has no row test in it. So the two
	 * uploads must be the same texels in the same format, and the same draw must
	 * put the same pixels on the backdrop. If they are, a panel that reads see
	 * through on screen is not the upload and not the blend - the same conclusion
	 * the presented-frame alpha reached from the other side.
	 */
	@Test
	@DisplayName("an interface panel's upload and blend are the same in both paths")
	void interfaceSpriteUpload_shouldMatchStock() {
		assumeTrue(hdUpload != null && stockUpload != null,
			"this driver did not leave the sprite's texture bound for a read-back");
		// The upload is measured in the art's own coordinates: an opaque body, the
		// masked ring around it, the clear border.
		int artRow = PANEL_SIZE / 2;
		int hdBody = texel(hdUpload, PANEL_SIZE / 2, artRow);
		int stockBody = texel(stockUpload, PANEL_SIZE / 2, artRow);
		int hdEdge = texel(hdUpload, 9, artRow);
		int stockEdge = texel(stockUpload, 9, artRow);
		int hdBorder = texel(hdUpload, 2, artRow);
		int stockBorder = texel(stockUpload, 2, artRow);

		// The draw is measured where it landed: the panel is projected through
		// the 2D window, so the screen footprint is not the art's coordinates.
		int row = PANEL_SIZE / 2;
		int backdrop = pixel(stockPanelPixels, 0, 0);
		int bodyX = firstOnRow(stockPanelPixels, row, 0x204060);
		int edgeX = firstNotOnRow(stockPanelPixels, row, backdrop, 0x204060);
		int borderX = firstLeftOf(stockPanelPixels, row, backdrop, bodyX);
		int expectedEdge = blend(backdrop, 0xC0C0C0, PANEL_OPACITY);
		System.out.println("[hd-frame] interface upload 592HD/stock: body "
			+ Integer.toHexString(hdBody) + "/" + Integer.toHexString(stockBody) + ", edge "
			+ Integer.toHexString(hdEdge) + "/" + Integer.toHexString(stockEdge) + ", border "
			+ Integer.toHexString(hdBorder) + "/" + Integer.toHexString(stockBorder)
			+ " | drawn over " + Integer.toHexString(backdrop) + " at x=" + bodyX + "/" + edgeX
			+ "/" + borderX + ": body " + Integer.toHexString(pixel(hdPanelPixels, bodyX, row))
			+ "/" + Integer.toHexString(pixel(stockPanelPixels, bodyX, row)) + ", edge "
			+ Integer.toHexString(pixel(hdPanelPixels, edgeX, row)) + "/"
			+ Integer.toHexString(pixel(stockPanelPixels, edgeX, row)) + " (expected "
			+ Integer.toHexString(expectedEdge) + "), border "
			+ Integer.toHexString(pixel(hdPanelPixels, borderX, row)) + "/"
			+ Integer.toHexString(pixel(stockPanelPixels, borderX, row)));

		assertAll(
			() -> assertEquals(stockBody, hdBody, "the body's texel must be the same in both paths"),
			() -> assertEquals(stockEdge, hdEdge, "the masked edge's texel must be the same"),
			() -> assertEquals(stockBorder, hdBorder, "the clear border's texel must be the same"),
			() -> assertEquals(255, stockBody >>> 24,
				"the body must upload opaque: alpha=" + (stockBody >>> 24)),
			() -> assertEquals(PANEL_OPACITY, stockEdge >>> 24,
				"the masked edge keeps its own alpha: alpha=" + (stockEdge >>> 24)),
			() -> assertEquals(0, stockBorder >>> 24,
				"the border uploads clear: alpha=" + (stockBorder >>> 24)),
			() -> assertTrue(bodyX > edgeX && edgeX > borderX,
				"the drawn panel must show its body, its edge and its border in that order: "
					+ bodyX + "/" + edgeX + "/" + borderX),
			() -> assertEquals(0x204060, pixel(stockPanelPixels, bodyX, row),
				"an opaque body must draw its own colour over the world: "
					+ Integer.toHexString(pixel(stockPanelPixels, bodyX, row))),
			() -> assertNear(expectedEdge, pixel(stockPanelPixels, edgeX, row), 2,
				"a masked edge must reach halfway between its colour and the backdrop"),
			() -> assertEquals(backdrop, pixel(stockPanelPixels, borderX, row),
				"a clear border must write nothing: "
					+ Integer.toHexString(pixel(stockPanelPixels, borderX, row))),
			() -> assertArrayEquals(stockUpload, hdUpload,
				"the two uploads must be the same texture, texel for texel"),
			() -> assertArrayEquals(stockPanelPixels, hdPanelPixels,
				"the two frames must draw the panel the same, pixel for pixel"));
	}

	/**
	 * The alpha test the sun's depth pass borrows, measured where it would matter:
	 * on a panel drawn after it, in the alpha-test state the interface runs in.
	 *
	 * <p>The cut-out row is the only code in a frame that changes
	 * {@code glAlphaFunc}: {@code SunShadowMap.beginPass} sets {@code GREATER 0.5}
	 * so a leaf's texel shape casts, and {@code endPass} restores the enable, the
	 * function and the reference. That reference is the question, because it is
	 * exactly what decides whether a panel's half-transparent texels survive: at
	 * {@code 0.0} they blend, at {@code 0.5} the same texels fail and the panel
	 * reads as glass with the world showing through it.
	 *
	 * <p>So two measurements, the same panel over the same opaque backdrop. The
	 * pass with the row off and with it on must leave the function, the reference
	 * and every panel pixel identical. And a deliberately stale reference - the
	 * panel drawn with {@code 0.5} where the frame leaves {@code 0.0} - must
	 * change pixels, or this harness could not see the leak it is looking for.
	 */
	@Test
	@DisplayName("the shadow pass' alpha test does not leak into a panel drawn after it")
	void cutoutAlphaTest_shouldNotLeakIntoTheInterface() {
		int cutoutRow = HdSettings.value(HdFeature.CUTOUT_SHADOWS);
		ShadowPanel off;
		ShadowPanel on;
		try {
			HdSettings.set(HdFeature.CUTOUT_SHADOWS, 0);
			off = panelAfterShadowPass();
			HdSettings.set(HdFeature.CUTOUT_SHADOWS, 1);
			on = panelAfterShadowPass();
		} finally {
			HdSettings.set(HdFeature.CUTOUT_SHADOWS, cutoutRow);
		}
		// The positive control: the same panel drawn with a reference above its
		// own alpha, which is what a stale reference does to the texels it
		// covers. The cut-out's own 0.5 is measured too - this panel's mask sits
		// at 128/255, just above it, so 0.5 alone would be invisible here and
		// proving the instrument bites needs a reference that actually rejects
		// one of the panel's texels.
		int atCutoutRef = changedFrom(drawPanelWithAlphaTest(0.0F), drawPanelWithAlphaTest(ShadowCutout.THRESHOLD));
		int stale = changedFrom(drawPanelWithAlphaTest(0.0F), drawPanelWithAlphaTest(0.9F));
		int leaked = changedFrom(off.pixels, on.pixels);
		System.out.println("[hd-frame] shadow alpha test: row off func=" + off.func + " ref=" + off.ref
			+ ", row on func=" + on.func + " ref=" + on.ref + " (cut-out " + on.cutout
			+ "), panel pixels changed " + leaked + "; a reference at 0.5 changes " + atCutoutRef
			+ ", at 0.9 changes " + stale);

		assertAll(
			() -> assertTrue(on.pass && on.cutout,
				"the pass must have run with the cut-out row on, or nothing leaked into it"),
			() -> assertEquals(off.func, on.func,
				"the pass must restore the alpha-test function: " + off.func + " vs " + on.func),
			() -> assertEquals(off.ref, on.ref, 0.0001F,
				"the pass must restore the alpha-test reference: " + off.ref + " vs " + on.ref),
			() -> assertEquals(0, leaked,
				"a panel drawn after the pass must be pixel-identical: " + leaked + " changed"),
			() -> assertTrue(stale > 0,
				"a stale reference must be visible in the panel's own pixels, or this is vacuous"));
	}

	/**
	 * One panel drawn after the sun's depth pass, with the interface's alpha-test
	 * state: the function and reference the pass leaves, and the panel's pixels.
	 */
	private static ShadowPanel panelAfterShadowPass() {
		// The pass only exists while HD is on; the rest of the class reaches this
		// through runFrame, which sets it, and this measurement does not.
		HdSettings.setEnabled(true, null);
		// The live frame's alpha test, as prepareForRendering leaves it: enabled,
		// GREATER at 0.0. Without it a panel is not tested at all and a leaked
		// reference could not show in its pixels.
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(ShadowCutout.GL_GREATER, 0.0F);
		ShadowPanel measured = new ShadowPanel();
		measured.cutout = ShadowCutout.enabled();
		measured.pass = toolkit.hdSunShadowBegin(0.0F, 0.0F, 0.0F, 32.0F);
		if (measured.pass) {
			drawQuad();
			toolkit.hdSunShadowEnd();
		}
		measured.func = GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC);
		measured.ref = GL11.glGetFloat(GL11.GL_ALPHA_TEST_REF);
		measured.pixels = drawPanelOverOpaque(panel);
		// The harness' frame baseline: no alpha test, so the frame tests' cached
		// flags stay true.
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		return measured;
	}

	/** The panel drawn with an alpha test at {@code ref}, as a leaked frame would. */
	private static int[] drawPanelWithAlphaTest(float ref) {
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(ShadowCutout.GL_GREATER, ref);
		int[] pixels = drawPanelOverOpaque(panel);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(ShadowCutout.GL_GREATER, 0.0F);
		return pixels;
	}

	/** What one post-pass panel measurement saw. */
	private static final class ShadowPanel {

		private int[] pixels;

		private int func;

		private float ref;

		private boolean pass;

		private boolean cutout;
	}

	/**
	 * A pixel the panel actually changed, on the backdrop the caller asked for: a
	 * land pixel from the world pass, or one the world left alone.
	 */
	private static int panelPixel(FrameRun run, int fallback, boolean overLand) {
		int anyPanel = fallback;
		for (int index = 0; index < run.interfacePixels.length; index++) {
			if (run.interfacePixels[index] == run.waterPixels[index]) {
				continue;
			}
			anyPanel = index;
			if ((run.worldDepth[index] < LAND_DEPTH) == overLand) {
				return index;
			}
		}
		return anyPanel;
	}

	/** The window's own alpha bits: 0 means the surface cannot show translucency at all. */
	private static int windowAlphaBits() {
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		int bits = GL11.glGetInteger(GL11.GL_ALPHA_BITS);
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, framebuffer);
		return bits;
	}

	/** The frame after the one that first draws the layer, so a page build cannot hide the draw. */
	private static FrameRun afterPageBuild(GlGroundLayer layer) {
		runFrame(true, layer);
		return runFrame(true, layer);
	}

	/** The pixel must be dominated by the channel the material's own art has. */
	private static void assertHue(int pixel, int art, String what) {
		int dominant = channel(art, 8) != 0 ? 8 : 0;
		int value = channel(pixel, dominant);
		int other = channel(pixel, dominant == 8 ? 16 : 8);
		int third = channel(pixel, dominant == 8 ? 0 : 16);
		assertTrue(value > 40 && value > 2 * other && value > 2 * third,
			what + " must draw its own colour, not a colourless tile: rgb=" + channels(pixel));
	}

	private static String channels(int pixel) {
		return channel(pixel, 16) + "/" + channel(pixel, 8) + "/" + channel(pixel, 0);
	}

	/**
	 * A frame whose HD passes must all have drawn: a comparison of two frames
	 * that both declined would be stock against stock, which is the failure this
	 * harness exists to avoid making itself.
	 */
	private static void assertPassesRan(FrameRun run) {
		System.out.println("[hd-frame] passes sky=" + run.skyDraws + " shadowFrames=" + run.shadowFrames
			+ " worldBinds=" + run.worldBinds + " waterBinds=" + run.waterBinds
			+ " postFrames=" + run.postFrames + " atlasBinds=" + run.atlasBinds);
		assertTrue(run.skyDraws > 0, "the sky dome must draw: draws=" + run.skyDraws);
		assertTrue(run.shadows && run.shadowFrames > 0,
			"the sun depth pass must fill the map: passes=" + run.shadowFrames);
		assertTrue(run.litWorld && run.worldBinds > 0,
			"the lit world draw must bind: binds=" + run.worldBinds);
		assertTrue(run.atlasBinds > 0,
			"the terrain must draw through the world-texture atlas: binds=" + run.atlasBinds);
		assertTrue(run.water && run.waterBinds > 0, "the HD water draw must bind: binds=" + run.waterBinds);
		assertTrue(run.post && run.postFrames > 0,
			"the screen-space pass must draw: frames=" + run.postFrames);
	}

	/** With 592HD off every seam must decline, or the stock run is not stock. */
	private static void assertPassesDeclined(FrameRun run) {
		assertTrue(run.skyDraws == 0 && run.shadowFrames == 0 && run.worldBinds == 0
				&& run.atlasBinds == 0 && run.waterBinds == 0 && run.postFrames == 0,
			"a 592HD pass ran with the rows off: sky=" + run.skyDraws + " shadowFrames=" + run.shadowFrames
				+ " worldBinds=" + run.worldBinds + " atlasBinds=" + run.atlasBinds
				+ " waterBinds=" + run.waterBinds + " postFrames=" + run.postFrames);
	}

	/** What one frame measured: the state at each boundary, and which passes drew. */
	private static final class FrameRun {

		private final Map<String, HdGlState> states = new LinkedHashMap<String, HdGlState>();

		private final Map<String, HdGlState> caches = new LinkedHashMap<String, HdGlState>();

		private boolean sky;

		private boolean shadows;

		private boolean litWorld;

		private boolean water;

		private boolean post;

		/** How much each pass actually drew, so a green frame cannot be a dead one. */
		private long skyDraws;

		private long shadowFrames;

		private long worldBinds;

		private long waterBinds;

		private long postFrames;

		private long atlasBinds;

		private long pageBuilds;

		/** The frame at the world, just-behind-water, water and interface boundaries. */
		private int[] worldPixels;

		private int[] waterBehindPixels;

		private int[] waterPixels;

		private int[] interfacePixels;

		/** The depth the world pass wrote, so the water's rejection can be located. */
		private float[] worldDepth;

		/** The depth after the water pass: nonzero here is water geometry that drew. */
		private float[] waterDepth;

		/** The presented frame's alpha, 0..255, after every pass. */
		private int[] alpha;

		/** The frame's alpha at the interface boundary, before the screen-space pass. */
		private int[] interfaceAlpha;
	}

	/**
	 * Draws one frame in client order and records every boundary.
	 *
	 * <p>Every draw is the client's own: the ground layer's {@code method1462},
	 * the water seam's {@code hdBeginWater}/{@code hdEndWater}, and
	 * {@code NativeSprite.render} for the panel. The only thing this method
	 * chooses is the order, which is the order a frame uses.
	 */
	/** The colour the live client clears the frame with: a fog colour, alpha byte 0. */
	private static final int LIVE_CLEAR = 0x000000;

	private static FrameRun runFrame(boolean hd) {
		return runFrame(hd, terrain);
	}

	/** The same frame with a chosen terrain layer, so one material at a time is measured. */
	private static FrameRun runFrame(boolean hd, GlGroundLayer layer) {
		return runFrame(hd, layer, 0xFF000000);
	}

	/**
	 * The same frame with a chosen clear colour. The alpha of the presented frame
	 * depends on what the clear left behind, so the measurements that read it use
	 * the live client's clear - a fog colour, whose alpha byte is 0 - while the
	 * colour measurements keep an opaque clear so their maths stay about the draw.
	 */
	private static FrameRun runFrame(boolean hd, GlGroundLayer layer, int clear) {
		HdSettings.setEnabled(hd, null);
		FrameRun run = new FrameRun();

		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, framebuffer);
		GL11.glViewport(0, 0, SIZE, SIZE);
		// A known, truthful baseline through the toolkit's own setters: nothing
		// here touches GL except to reach the state a 2D frame starts from.
		toolkit.method1958(0);
		toolkit.method1951(false);
		toolkit.method1911(false);
		toolkit.method1918(false);
		toolkit.method1906(false);
		toolkit.setActiveTexture(null);
		toolkit.method1900(1);
		toolkit.method1930();
		snapshot(run, "start");

		toolkit.p(clear);
		// The client's own start-of-frame depth clear: it enables depth writes
		// first (method4266), because glClear honours the write mask and a 2D
		// pass has just turned it off.
		toolkit.method4266();
		snapshot(run, "clear");

		long skyBefore = toolkit.hdSkyDraws();
		HdFrameSequence.drawSky(false);
		run.skyDraws = toolkit.hdSkyDraws() - skyBefore;
		run.sky = run.skyDraws > 0;
		snapshot(run, "sky");

		// The sun's depth pass, through the toolkit seams HdFrameSequence drives
		// from the world draw's opening. It opens before the world so the map the
		// receivers sample is this frame's geometry.
		SunShadowMap mapBefore = toolkit.hdSunShadow();
		int framesBefore = mapBefore == null ? 0 : mapBefore.frameId();
		boolean beganShadow = toolkit.hdSunShadowBegin(0.0F, 0.0F, 0.0F, 32.0F);
		SunShadowMap map = toolkit.hdSunShadow();
		if (beganShadow) {
			drawQuad();
			toolkit.hdSunShadowEnd();
		}
		run.shadowFrames = (map == null ? 0 : map.frameId()) - framesBefore;
		run.shadows = beganShadow && map != null && map.hasFrame() && run.shadowFrames > 0;
		// The pass draws into a target of its own; the frame it interrupted must
		// still be the one GL is rendering into when it ends.
		assertEquals(framebuffer, GL11.glGetInteger(EXTFramebufferObject.GL_FRAMEBUFFER_BINDING_EXT),
			"the sun's depth pass must restore the framebuffer it found");
		snapshot(run, "shadows");

		// The world: the scene's ground pass. method1904 is that pass' own state
		// call - depth test and depth writes on, blend on, GL_MODULATE - and
		// method1906 asks for the normals the lit program reads. The layer's
		// method1462 is the same call the region makes for every layer.
		HdFrameSequence.beginWorldDraw();
		toolkit.method1904();
		toolkit.method1906(true);
		long bindsBefore = toolkit.hdLightingBinds();
		long atlasBefore = toolkit.hdGroundAtlasBinds();
		long pagesBefore = toolkit.hdGroundPageBuilds();
		layer.method1462(new int[] { 0 }, 1);
		HdFrameSequence.endWorldDraw();
		run.worldBinds = toolkit.hdLightingBinds() - bindsBefore;
		run.atlasBinds = toolkit.hdGroundAtlasBinds() - atlasBefore;
		run.pageBuilds = toolkit.hdGroundPageBuilds() - pagesBefore;
		run.litWorld = run.worldBinds > 0;
		run.worldPixels = captureColour();
		run.worldDepth = captureDepth();
		snapshot(run, "world");

		// The water: the same footprint drawn behind the land (it must be
		// depth-rejected) and then in front of it (it must paint the ring the
		// land does not cover). Both go through the water seam, and the stock run
		// binds the water texture and draws the same faces in fixed function, so
		// the two runs reach the species of state a stock water draw leaves.
		long waterBefore = toolkit.hdWaterBinds();
		run.water = toolkit.hdBeginWater();
		// The material bind that follows the seam in the live path: the water's
		// own texture on unit 0, where the stock effect also puts it.
		toolkit.setActiveTexture(waterTexture);
		drawWater(0);
		run.waterBehindPixels = captureColour();
		drawWater(1);
		run.waterPixels = captureColour();
		run.waterDepth = captureDepth();
		if (run.water) {
			toolkit.hdEndWater();
		}
		run.waterBinds = toolkit.hdWaterBinds() - waterBefore;
		run.water &= run.waterBinds > 0;
		snapshot(run, "water");

		// The interface: the client's own sprite draw. It prepares 2D state
		// itself, binds its texture on unit 0 and blends its masked texels; a
		// leaked blend function, alpha-test function, program or depth mask shows
		// up as this draw's, not the pass's.
		panel.render(PANEL_RECT, PANEL_RECT, 1, 0xFFFFFF, 1);
		run.interfacePixels = captureColour();
		run.interfaceAlpha = captureAlpha();
		snapshot(run, "interface");

		long postBefore = toolkit.hdPostDraws();
		toolkit.hdPostFrame();
		run.postFrames = toolkit.hdPostDraws() - postBefore;
		run.post = run.postFrames > 0;
		snapshot(run, "post");

		GL11.glFinish();
		assertEquals(GL11.GL_NO_ERROR, GL11.glGetError(), "GL error while drawing the frame");
		// The frame as it would be presented: after every pass, including the
		// screen-space one that copies the frame and draws it back.
		run.alpha = captureAlpha();
		snapshot(run, "end");
		return run;
	}

	private static void drawQuad() {
		toolkit.method1934(quadColours, quadNormals, null, quadVertices);
		toolkit.method1967(0, 6, quadIndices);
	}

	/** One of the water's two faces: 0 behind the terrain's depth, 1 in front of it. */
	private static void drawWater(int face) {
		toolkit.method1934(waterColours, null, null, waterVertices);
		toolkit.method1967(face * 6, 6, waterIndices);
	}

	private static void snapshot(FrameRun run, String boundary) {
		run.states.put(boundary, HdGlState.actual());
		run.caches.put(boundary, toolkit.hdGlState());
		System.out.println("[hd-frame] probe " + boundary + " fbo="
			+ GL11.glGetInteger(EXTFramebufferObject.GL_FRAMEBUFFER_BINDING_EXT) + " clearDepth="
			+ GL11.glGetFloat(GL11.GL_DEPTH_CLEAR_VALUE) + " depthBits="
			+ GL11.glGetInteger(GL11.GL_DEPTH_BITS) + " target=" + targetTexture);
	}

	private static void print(String boundary, HdGlState state) {
		System.out.println("[hd-frame] " + boundary + " " + state);
	}

	/** The frame as packed {@code 0xRRGGBB}; row 0 is the bottom, as GL reads it. */
	private static int[] captureColour() {
		ByteBuffer pixels = BufferUtils.createByteBuffer(SIZE * SIZE * 4);
		GL11.glReadPixels(0, 0, SIZE, SIZE, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
		int[] frame = new int[SIZE * SIZE];
		for (int index = 0; index < frame.length; index++) {
			frame[index] = (pixels.get(index * 4) & 0xFF) << 16
				| (pixels.get(index * 4 + 1) & 0xFF) << 8
				| (pixels.get(index * 4 + 2) & 0xFF);
		}
		return frame;
	}

	/** The frame's alpha channel, in the same layout as {@link #captureColour()}. */
	private static int[] captureAlpha() {
		ByteBuffer pixels = BufferUtils.createByteBuffer(SIZE * SIZE * 4);
		GL11.glReadPixels(0, 0, SIZE, SIZE, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
		int[] alpha = new int[SIZE * SIZE];
		for (int index = 0; index < alpha.length; index++) {
			alpha[index] = pixels.get(index * 4 + 3) & 0xFF;
		}
		return alpha;
	}

	/** The depth the frame has, in the same layout as {@link #captureColour()}. */
	private static float[] captureDepth() {
		ByteBuffer depth = BufferUtils.createByteBuffer(SIZE * SIZE * 4);
		GL11.glReadPixels(0, 0, SIZE, SIZE, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, depth);
		float[] values = new float[SIZE * SIZE];
		for (int index = 0; index < values.length; index++) {
			values[index] = depth.getFloat(index * 4);
		}
		return values;
	}

	/** Row-major from the bottom, the order {@link #captureColour()} returns. */
	private static int index(int x, int yFromTop) {
		return (SIZE - 1 - yFromTop) * SIZE + x;
	}

	private static int pixel(int[] frame, int x, int yFromTop) {
		return frame[index(x, yFromTop)];
	}

	private static int changedFrom(int[] from, int[] to) {
		int changed = 0;
		for (int index = 0; index < from.length; index++) {
			if (from[index] != to[index]) {
				changed++;
			}
		}
		return changed;
	}

	/** Pixels that changed on the side of the depth value the caller asked for. */
	private static int changedWhereDepth(float[] depth, int[] from, int[] to, boolean land) {
		int changed = 0;
		for (int index = 0; index < from.length; index++) {
			boolean onLand = depth[index] < LAND_DEPTH;
			if (onLand == land && from[index] != to[index]) {
				changed++;
			}
		}
		return changed;
	}

	/** The land pixel nearest a screen point, from the depth the terrain wrote. */
	private static int nearestLand(float[] depth, int x, int y) {
		int best = -1;
		int bestDistance = Integer.MAX_VALUE;
		for (int index = 0; index < depth.length; index++) {
			if (depth[index] >= LAND_DEPTH) {
				continue;
			}
			int dx = index % SIZE - x;
			int dy = index / SIZE - (SIZE - 1 - y);
			int distance = dx * dx + dy * dy;
			if (distance < bestDistance) {
				bestDistance = distance;
				best = index;
			}
		}
		return best < 0 ? index(x, y) : best;
	}

	/** The land's bounds as {@code x0, y0, x1, y1} from the top, or null. */
	private static int[] landBox(float[] depth) {
		return box(depth, LAND_DEPTH);
	}

	/** The bounds of the pixels two frames differ in, or null when they match. */
	private static int[] changedBox(int[] from, int[] to) {
		int x0 = SIZE;
		int y0 = SIZE;
		int x1 = -1;
		int y1 = -1;
		for (int index = 0; index < from.length; index++) {
			if (from[index] == to[index]) {
				continue;
			}
			int x = index % SIZE;
			int y = SIZE - 1 - index / SIZE;
			x0 = Math.min(x0, x);
			y0 = Math.min(y0, y);
			x1 = Math.max(x1, x);
			y1 = Math.max(y1, y);
		}
		return x1 < 0 ? null : new int[] { x0, y0, x1, y1 };
	}

	private static int[] box(float[] depth, float limit) {
		int x0 = SIZE;
		int y0 = SIZE;
		int x1 = -1;
		int y1 = -1;
		for (int index = 0; index < depth.length; index++) {
			if (depth[index] >= limit) {
				continue;
			}
			int x = index % SIZE;
			int y = SIZE - 1 - index / SIZE;
			x0 = Math.min(x0, x);
			y0 = Math.min(y0, y);
			x1 = Math.max(x1, x);
			y1 = Math.max(y1, y);
		}
		return x1 < 0 ? null : new int[] { x0, y0, x1, y1 };
	}

	/** Pixels the water pass gave depth to that had none: its own coverage. */
	private static int depthAdded(float[] before, float[] after) {
		int added = 0;
		for (int index = 0; index < before.length; index++) {
			if (before[index] >= LAND_DEPTH && after[index] < LAND_DEPTH) {
				added++;
			}
		}
		return added;
	}

	private static int landPixels(float[] depth) {
		int land = 0;
		for (int value = 0; value < depth.length; value++) {
			if (depth[value] < LAND_DEPTH) {
				land++;
			}
		}
		return land;
	}

	private static int channel(int colour, int shift) {
		return colour >> shift & 0xFF;
	}

	/**
	 * The pixel a source-over blend of {@code colour} at {@code alpha} over
	 * {@code background} must produce. The panel's mask makes its core exactly
	 * this, so a see-through panel (alpha lost) or an untextured one (colour
	 * lost) lands somewhere else.
	 */
	private static int blend(int background, int colour, int alpha) {
		int result = 0;
		for (int shift = 0; shift <= 16; shift += 8) {
			int mixed = (channel(background, shift) * (255 - alpha) + channel(colour, shift) * alpha + 127) / 255;
			result |= mixed << shift;
		}
		return result;
	}

	/** Asserts the drawn pixel is the blend, channel by channel, within rounding. */
	private static void assertBlend(int expected, int actual, String message) {
		for (int shift = 0; shift <= 16; shift += 8) {
			int difference = Math.abs(channel(expected, shift) - channel(actual, shift));
			assertTrue(difference <= 3, message + ": expected " + Integer.toHexString(expected)
				+ ", drew " + Integer.toHexString(actual));
		}
	}

	private static void createTarget() {
		framebuffer = EXTFramebufferObject.glGenFramebuffersEXT();
		targetTexture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, targetTexture);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, SIZE, SIZE, 0, GL11.GL_RGBA,
			GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		depthBuffer = EXTFramebufferObject.glGenRenderbuffersEXT();
		EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, depthBuffer);
		EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT,
			GL14.GL_DEPTH_COMPONENT24, SIZE, SIZE);

		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, framebuffer);
		EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
			EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, targetTexture, 0);
		EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
			EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT,
			depthBuffer);
		int status = EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT);
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
		assertEquals(EXTFramebufferObject.GL_FRAMEBUFFER_COMPLETE_EXT, status, "offscreen target incomplete");
	}

	/** One interleaved quad: position, colour, normal, the shape a model draw uses. */
	private static void createGeometry() {
		int stride = 28;
		float[][] corners = {
			{ -2.0F, -2.0F, -80.0F }, { 2.0F, -2.0F, -80.0F }, { 2.0F, 2.0F, -80.0F },
			{ -2.0F, 2.0F, -80.0F }
		};
		ByteBuffer data = ByteBuffer.allocate(stride * corners.length).order(ByteOrder.LITTLE_ENDIAN);
		for (float[] corner : corners) {
			data.putFloat(corner[0]).putFloat(corner[1]).putFloat(corner[2]);
			data.put(new byte[] { (byte) 255, (byte) 255, (byte) 255, (byte) 255 });
			data.putFloat(0.0F).putFloat(0.0F).putFloat(1.0F);
		}
		quadBuffer = new GlVboElementsArrayBuffer(toolkit, stride, data.array(), data.array().length, false);
		quadVertices = new Class177(quadBuffer, GL11.GL_FLOAT, 3, 0);
		quadColours = new Class177(quadBuffer, GL11.GL_UNSIGNED_BYTE, 4, 12);
		quadNormals = new Class177(quadBuffer, GL11.GL_FLOAT, 3, 16);
		byte[] indices = { 0, 0, 1, 0, 2, 0, 0, 0, 2, 0, 3, 0 };
		quadIndices = new GlVboArrayBuffer(toolkit, GL11.GL_UNSIGNED_SHORT, indices, indices.length, false);
	}

	/**
	 * The scene's terrain: a two-by-two mesh whose one visible tile is drawn by a
	 * layer carrying {@link #TERRAIN_MATERIAL}, with the mesh's per-tile material
	 * map attached the way {@code constructTileLayers} attaches it. The tile is in
	 * scene units under the camera's own matrices, which is what the ground seam's
	 * identity mapping expects of the live pass.
	 */
	private static void createTerrain() {
		int[][] heights = new int[3][3];
		ground = new GlGround(toolkit, 0, 0x7, 2, 2, heights, heights, TILE_UNITS);
		// vertex (12) | colour (4) | normal (12) = 28, the layout GlModel uses.
		int stride = 28;
		float[][] positions = {
			{ 0.0F, 0.0F, 0.0F }, { 128.0F, 0.0F, 0.0F },
			{ 128.0F, 64.0F, 0.0F }, { 0.0F, 64.0F, 0.0F }
		};
		ByteBuffer data = ByteBuffer.allocate(stride * positions.length).order(ByteOrder.LITTLE_ENDIAN);
		for (float[] position : positions) {
			data.putFloat(position[0]).putFloat(position[1]).putFloat(position[2]);
			data.put((byte) 255).put((byte) 255).put((byte) 255).put((byte) 255);
			data.putFloat(0.0F).putFloat(0.0F).putFloat(1.0F);
		}
		terrainBuffer = new GlVboElementsArrayBuffer(toolkit, stride, data.array(), data.array().length, false);
		ground.aClass177_15 = new Class177(terrainBuffer, GL11.GL_FLOAT, 3, 0);
		ground.aClass177_16 = new Class177(terrainBuffer, GL11.GL_FLOAT, 3, 16);
		ground.aClass177_14 = null;
		// Wound for the camera's modelview: rotX(180) mirrors the scene, so a
		// scene-space quad has to wind the other way round to face the viewer.
		// The stock pass leaves GL_CULL_FACE on and the real mesh is wound for
		// this same transform, so a fixture wound the naive way is culled away.
		ground.tileVertexIndices[0] = new short[] { 0, 2, 1, 0, 3, 2 };

		terrain = terrainLayer(TERRAIN_MATERIAL);
		terrainNoArt = terrainLayer(TERRAIN_NO_ART);
		terrainShortArt = terrainLayer(TERRAIN_SHORT_ART);

		HdTileMaterialMap tileMaterials = new HdTileMaterialMap();
		tileMaterials.set(0, 0, TERRAIN_MATERIAL, TERRAIN_MATERIAL);
		ground.hdAttachTileMaterialsForTest(tileMaterials);
	}

	/**
	 * One layer of the terrain mesh, carrying its own material. The two extra
	 * layers exist so the same tile can be drawn by a material the page can fill
	 * and by one it cannot, under exactly the same geometry and state.
	 */
	private static GlGroundLayer terrainLayer(int material) {
		GlGroundLayer layer = new GlGroundLayer(ground, material, 1, 0, 0, 0);
		layer.initColours(4);
		for (int vertex = 0; vertex < 4; vertex++) {
			layer.setVertexColour(0, vertex, 0xFFFFFF, 1.0F);
			layer.setVertexOpaque(vertex);
		}
		layer.finishColours(4);
		layer.addTriangle(0, 0, 0);
		layer.addTriangle(0, 1, 0);
		return layer;
	}

	/**
	 * The water's two faces: the same footprint as the terrain, once behind it
	 * (z = +40, further from the camera) and once in front (z = -40). In this
	 * camera larger world z is further away, so the first face is the one the
	 * terrain's depth must reject.
	 */
	private static void createWater() {
		int stride = 16;
		float[][] corners = {
			// Behind the land, exactly its footprint: every pixel it covers is a
			// land pixel, so the depth test alone decides whether it paints.
			{ 0.0F, 0.0F, 40.0F }, { 128.0F, 0.0F, 40.0F }, { 128.0F, 64.0F, 40.0F }, { 0.0F, 64.0F, 40.0F },
			// In front of it, and wider than it, so the same pass also paints the
			// ring the land does not reach - the control that says the zero above
			// is a depth rejection and not a dead pass.
			{ -256.0F, -128.0F, -40.0F }, { 384.0F, -128.0F, -40.0F },
			{ 384.0F, 192.0F, -40.0F }, { -256.0F, 192.0F, -40.0F }
		};
		ByteBuffer data = ByteBuffer.allocate(stride * corners.length).order(ByteOrder.LITTLE_ENDIAN);
		for (float[] corner : corners) {
			data.putFloat(corner[0]).putFloat(corner[1]).putFloat(corner[2]);
			data.put((byte) 255).put((byte) 255).put((byte) 255).put((byte) 255);
		}
		waterBuffer = new GlVboElementsArrayBuffer(toolkit, stride, data.array(), data.array().length, false);
		waterVertices = new Class177(waterBuffer, GL11.GL_FLOAT, 3, 0);
		waterColours = new Class177(waterBuffer, GL11.GL_UNSIGNED_BYTE, 4, 12);
		// Same winding as the terrain: the modelview mirrors the scene. The two
		// faces are two vertex ranges, not the same four vertices drawn twice -
		// the first attempt indexed 0..3 for both, so the "front" face was the
		// behind face again and the pass could only ever be depth-rejected.
		byte[] indices = {
			0, 0, 2, 0, 1, 0, 0, 0, 3, 0, 2, 0,
			4, 0, 6, 0, 5, 0, 4, 0, 7, 0, 6, 0
		};
		waterIndices = new GlVboArrayBuffer(toolkit, GL11.GL_UNSIGNED_SHORT, indices, indices.length, false);
		int[] pixels = new int[WATER_TEXTURE_SIZE * WATER_TEXTURE_SIZE];
		Arrays.fill(pixels, 0xFF000000 | WATER_ART);
		waterTexture = new OpenGl2DImageTexture(toolkit, GL11.GL_TEXTURE_2D,
			WATER_TEXTURE_SIZE, WATER_TEXTURE_SIZE, false, pixels);
	}

	/**
	 * The interface's panel: a real {@link SpriteGL} of a real alpha mask - half
	 * transparent across its core, clear at its border - drawn by the sprite's own
	 * {@code render}, which is what every interface sprite call reaches.
	 */
	private static void createPanel() {
		int[] pixels = new int[PANEL_SIZE * PANEL_SIZE];
		for (int y = 0; y < PANEL_SIZE; y++) {
			for (int x = 0; x < PANEL_SIZE; x++) {
				boolean core = x >= PANEL_CORE_FROM && x < PANEL_CORE_TO
					&& y >= PANEL_CORE_FROM && y < PANEL_CORE_TO;
				pixels[y * PANEL_SIZE + x] = core ? PANEL_OPACITY << 24 | PANEL_ART : 0;
			}
		}
		panel = new SpriteGL(toolkit, PANEL_SIZE, PANEL_SIZE, pixels, 0, 0);
	}

	/**
	 * Builds one interface panel twice - once with the 592HD rows on, once with
	 * them off - and records what reaches GL and what it draws.
	 *
	 * <p>The upload is the question. Both modes reach the same constructor
	 * ({@code Class230.method5095} -> {@code OpenGl2DImageTexture}), so a panel
	 * that is see-through in one mode and not the other can only come from the
	 * texels or the format that constructor chose. Reading the texture back is the
	 * only way to see which; drawing it over an opaque colour is the only way to
	 * see the blend those texels produce.
	 */
	private static void measureInterfaceUploads() {
		interfaceArt = interfaceArt();
		HdSettings.setEnabled(true, null);
		SpriteGL hd = new SpriteGL(toolkit, PANEL_SIZE, PANEL_SIZE, interfaceArt, 0, 0);
		hdUpload = spriteTexels();
		hdPanelPixels = drawPanelOverOpaque(hd);
		HdSettings.setEnabled(false, null);
		SpriteGL stock = new SpriteGL(toolkit, PANEL_SIZE, PANEL_SIZE, interfaceArt, 0, 0);
		stockUpload = spriteTexels();
		stockPanelPixels = drawPanelOverOpaque(stock);
		HdSettings.setEnabled(true, null);
	}

	/**
	 * Real interface art: an opaque body, a half-transparent edge ring and a clear
	 * border - the three regions a panel's format decision is visible in.
	 */
	private static int[] interfaceArt() {
		int[] art = new int[PANEL_SIZE * PANEL_SIZE];
		for (int y = 0; y < PANEL_SIZE; y++) {
			for (int x = 0; x < PANEL_SIZE; x++) {
				boolean border = x < 8 || y < 8 || x >= PANEL_SIZE - 8 || y >= PANEL_SIZE - 8;
				boolean edge = !border && (x < 10 || y < 10 || x >= PANEL_SIZE - 10
					|| y >= PANEL_SIZE - 10);
				art[y * PANEL_SIZE + x] = border ? 0x00FFFFFF : edge ? 0x80C0C0C0 : 0xFF204060;
			}
		}
		return art;
	}

	/** The texels of whatever 2D texture the sprite's constructor left bound, or null. */
	private static int[] spriteTexels() {
		int name = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
		if (name == 0) {
			return null;
		}
		ByteBuffer texels = BufferUtils.createByteBuffer(PANEL_SIZE * PANEL_SIZE * 4);
		GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, texels);
		int[] pixels = new int[PANEL_SIZE * PANEL_SIZE];
		for (int index = 0; index < pixels.length; index++) {
			pixels[index] = (texels.get(index * 4 + 3) & 0xFF) << 24
				| (texels.get(index * 4) & 0xFF) << 16
				| (texels.get(index * 4 + 1) & 0xFF) << 8
				| texels.get(index * 4 + 2) & 0xFF;
		}
		return pixels;
	}

	/** One panel drawn 1:1 over an opaque grey, the way the interface draws over the world. */
	private static int[] drawPanelOverOpaque(SpriteGL sprite) {
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, framebuffer);
		GL11.glViewport(0, 0, SIZE, SIZE);
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glColorMask(true, true, true, true);
		GL11.glClearColor(0.5F, 0.5F, 0.5F, 1.0F);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		sprite.render(PANEL_RECT, PANEL_RECT, 1, 0xFFFFFF, 1);
		return captureColour();
	}

	/** The first x on a row whose pixel is {@code colour}, or 0. */
	private static int firstOnRow(int[] frame, int row, int colour) {
		for (int x = 0; x < SIZE; x++) {
			if (pixel(frame, x, row) == colour) {
				return x;
			}
		}
		return 0;
	}

	/** The first x on a row whose pixel is neither {@code first} nor {@code second}. */
	private static int firstNotOnRow(int[] frame, int row, int first, int second) {
		for (int x = 0; x < SIZE; x++) {
			int value = pixel(frame, x, row);
			if (value != first && value != second) {
				return x;
			}
		}
		return 0;
	}

	/**
	 * The x nearest {@code limit} on a row whose pixel is the backdrop - the
	 * border of the drawn panel, as opposed to the backdrop outside it.
	 */
	private static int firstLeftOf(int[] frame, int row, int backdrop, int limit) {
		for (int x = Math.max(0, limit - 1); x >= 0; x--) {
			if (pixel(frame, x, row) == backdrop) {
				return x;
			}
		}
		return 0;
	}

	/** Every channel within {@code slack} of the expected pixel. */
	private static void assertNear(int expected, int actual, int slack, String message) {
		for (int shift = 0; shift <= 16; shift += 8) {
			int difference = Math.abs(channel(expected, shift) - channel(actual, shift));
			assertTrue(difference <= slack, message + ": expected " + Integer.toHexString(expected)
				+ ", drew " + Integer.toHexString(actual));
		}
	}

	/** A texel of a read-back texture; GL rows run bottom-up. */
	private static int texel(int[] upload, int x, int y) {
		return upload[(PANEL_SIZE - 1 - y) * PANEL_SIZE + x];
	}

	/**
	 * The client's material sampler reduced to what the page and the stock texture
	 * read: one material with art, uploaded the way this one's stock upload is.
	 *
	 * <p>The material is opaque by construction ({@code aBoolean640}, an effect
	 * slot that keeps its alpha) so the page states the alpha the stock RGB upload
	 * implies - an RGBA page copied from an alpha-0 samplers' black texels would
	 * be translucent and read as a see-through world.
	 */
	private static final class FrameMaterials implements unpack_Sub1 {

		private final MaterialType terrainType = new MaterialType();

		private FrameMaterials() {
			this.terrainType.isSmall = true;
			this.terrainType.aBoolean640 = true;
			this.terrainType.anInt6934 = MODULATE_EFFECT;
		}

		/**
		 * How many times the sampler has been asked for each material. The stock
		 * upload is built once, on the material's first draw, and the page's copy
		 * is asked for afterwards - which is exactly the order a streaming client
		 * sees when a material's art stops being reproducible after its stock
		 * texture exists.
		 */
		private final int[] samplerCalls = new int[HdMaterialMap.MATERIALS];

		@Override
		public boolean textureAvailable(int material) {
			return material == TERRAIN_MATERIAL || material == TERRAIN_NO_ART
				|| material == TERRAIN_SHORT_ART;
		}

		@Override
		public int[] getPixels(boolean readReversed, int material, float brightness, int width, int height) {
			int call = this.samplerCalls[material]++;
			if (material == TERRAIN_MATERIAL) {
				return filled(width * height, TERRAIN_ART);
			}
			if (material == TERRAIN_NO_ART) {
				return call == 0 ? filled(width * height, NO_ART_ART) : null;
			}
			if (material == TERRAIN_SHORT_ART) {
				return call == 0 ? filled(width * height, SHORT_ART_ART) : new int[16];
			}
			return null;
		}

		private static int[] filled(int length, int colour) {
			int[] pixels = new int[length];
			Arrays.fill(pixels, colour);
			return pixels;
		}

		@Override
		public float[] method2394(float arg0, int arg1, int arg2, int arg3) {
			return null;
		}

		@Override
		public int[] getPixelsArgb(int material, float brightness, int width, int height) {
			int call = this.samplerCalls[material]++;
			if (material == TERRAIN_MATERIAL) {
				return filled(width * height, 0xFF000000 | TERRAIN_ART);
			}
			if (material == TERRAIN_NO_ART) {
				return call == 0 ? filled(width * height, 0xFF000000 | NO_ART_ART) : null;
			}
			if (material == TERRAIN_SHORT_ART) {
				return call == 0 ? filled(width * height, 0xFF000000 | SHORT_ART_ART) : new int[16];
			}
			return null;
		}

		@Override
		public MaterialType getMetrics(int material) {
			return textureAvailable(material) ? this.terrainType : null;
		}
	}
}
