package com.jagex.game.runetek5.client.hd.ground;

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

import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.PcmPlayerThread;
import com.jagex.game.runetek5.buffer.ElementsBufferGL;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.GlGroundLayer;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.client.hd.water.HdWaterGate;
import com.jagex.game.runetek5.client.hd.water.HdWaterSource;
import com.jagex.game.runetek5.client.hd.water.HdWaterTime;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.scene.ground.GlGround;

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
 * Caustics in pixels, through the production terrain draw: two adjacent tiles on
 * a real {@link GlGround}, one dry and one with the stock depth byte set, drawn
 * by {@link GlGroundLayer#method1462} - the method the scene calls every frame.
 *
 * <p>The depth is the client's own, not a test constant: a layer's
 * {@code waterDepth} (the byte {@code Terrain} reads per tile for an underwater
 * region) is what the seam turns into {@link HdGroundWater}, so a layer
 * constructed with 200 is what a submerged tile really submits.
 *
 * <p>What this proves:
 *
 * <ul>
 * <li>the row lights the submerged tile and leaves the dry neighbour pixel for
 * pixel alone;</li>
 * <li>the pattern advances with the water clock, and only on the submerged
 * tile;</li>
 * <li>the row off - and a dry tile with the row on - issues no caustics upload
 * at all, so the off state is the frame before the row existed rather than a
 * faint pattern.</li>
 * </ul>
 *	 * <p>Runs by default; {@link GlTestContext#open()} skips it with the reason
	 * when this machine cannot give a GL context.
 */
@DisplayName("592HD caustics on the live terrain draw")
class HdCausticsGlTest {

	private static final int SIZE = 64;

	private static final int TILE_UNITS = 128;

	/** The stock depth byte a submerged tile submits; 200 of 255. */
	private static final int WATER_DEPTH = 200;

	private static final int CAMERA_X = -64;

	private static final int CAMERA_Y = -32;

	private static final int CAMERA_Z = 200;

	private static final float FAR = 3584.0F;

	/** One channel step tolerated by the comparison, for rounding only. */
	private static final int CHANNEL_STEP = 2;

	/**
	 * The frame's sky cube's side. Fine enough that a ripple's bend - a few
	 * hundredths of the cube's width - moves the sampled texel: the proof is
	 * that the sky in the water moves, so a coarse gradient would hide it.
	 */
	private static final int SKY_SIZE = 64;

	private static GlToolkit toolkit;

	private static Frame frame;

	private static Canvas canvas;

	private static int framebuffer;

	private static int targetTexture;

	private static int depthBuffer;

	private static GlGround ground;

	/** Tile 0's layer: no water over it. */
	private static GlGroundLayer dryLayer;

	/** Tile 1's layer: the stock byte says 200/255 of water stands here. */
	private static GlGroundLayer wetLayer;

	private static ElementsBufferGL terrain;

	@BeforeAll
	static void openToolkit() throws Exception {
		try {
			GlTestContext warmup = GlTestContext.open();
			warmup.close();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}

		frame = new Frame("592HD caustics test");
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
		System.out.println("[hd-caustics] toolkit " + GL11.glGetString(GL11.GL_RENDERER) + " | GL "
			+ GL11.glGetString(GL11.GL_VERSION));

		createTarget();
		toolkit.setHeap(toolkit.createHeap(1 << 26));
		// A sky with structure: with one flat colour in the cube, bending the
		// reflection changes nothing and a still-looking surface could pass.
		toolkit.setSunCubeTexture(new Sky(gradientSky()));
		createTerrain();

		Path preferences = Files.createTempDirectory("hd-caustics");
		HdSettings.setDirectoryForTest(preferences.toFile());
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 1);
		HdSettings.set(HdFeature.POINT_LIGHTS, 0);
		HdSettings.set(HdFeature.SHADOW_MAP, 0);
		HdSettings.set(HdFeature.FOG, 0);
		HdSettings.set(HdFeature.DISTANCE_FADE, 0);
		HdSettings.set(HdFeature.AMBIENT_OCCLUSION, 0);
		HdSettings.set(HdFeature.CARRIED_LIGHT, 0);
		HdSettings.set(HdFeature.ROOM_LIGHT, 0);
		HdSettings.set(HdFeature.ENVIRONMENT_LIGHTING, 0);
		HdSettings.set(HdFeature.CAUSTICS, 0);
		// Every caustics proof below measures the terrain program's own draw, so
		// the Water row must be Off unless a test asks for it: a water layer's
		// surface would own the tile and the pattern would be a different pass.
		HdSettings.set(HdFeature.WATER, 0);
		HdWaterTime.reset();

		JavaMatrix camera = new JavaMatrix();
		camera.scaleWidth(CAMERA_X, CAMERA_Y, CAMERA_Z);
		toolkit.ea(camera);

		PcmPlayerThread.aClass4_Sub12_7 = new Packet(64);
	}

	@AfterAll
	static void closeToolkit() {
		HdSettings.resetForTest();
		HdWaterTime.reset();
		if (toolkit != null) {
			toolkit.hdEndModelLighting();
		}
		if (frame != null) {
			frame.dispose();
		}
	}

	@Test
	@DisplayName("caustics light the submerged tile and leave the dry neighbour alone")
	void caustics_shouldLightOnlyTheSubmergedTile() {
		HdSettings.set(HdFeature.CAUSTICS, 0);
		HdWaterTime.reset();
		int[] off = drawFrame(true, true);
		int[] dryOnly = drawFrame(true, false);
		int[] wetOnly = drawFrame(false, true);

		HdSettings.set(HdFeature.CAUSTICS, 1);
		int[] on = drawFrame(true, true);

		int[] dry = litMask(dryOnly);
		int[] wet = litMask(wetOnly);
		int dryChanged = changed(on, off, dry);
		int wetChanged = changed(on, off, wet);
		int darker = darker(on, off, wet);

		System.out.println("[hd-caustics] dry px=" + dry.length + " wet px=" + wet.length
			+ ", changed dry=" + dryChanged + " wet=" + wetChanged
			+ ", darker on the wet tile=" + darker
			+ ", uploads=" + toolkit.hdCausticsUploads());

		assertAll(
			() -> assertTrue(wet.length > 0, "the submerged tile must be visible"),
			() -> assertEquals(0, dryChanged,
				"a dry tile has no water to pattern, so it must not change: " + dryChanged),
			() -> assertTrue(wetChanged > 20,
				"the submerged tile must show the pattern: " + wetChanged + " px changed"),
			() -> assertEquals(0, darker,
				"caustics add light; no pixel on the wet tile may darken: " + darker),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("the pattern advances with the water clock, and only on the wet tile")
	void caustics_shouldAdvanceWithTheClock() {
		HdSettings.set(HdFeature.CAUSTICS, 1);
		HdWaterTime.reset();
		int[] first = drawFrame(true, true);

		advanceTo(500L);
		int[] later = drawFrame(true, true);

		int[] dry = litMask(drawOnlyDry());
		int[] wet = litMask(drawOnlyWet());
		int moved = changed(later, first, wet);
		int still = changed(later, first, dry);

		System.out.println("[hd-caustics] clock: wet moved " + moved + " px, dry moved " + still
			+ " px at " + HdWaterTime.seconds() + "s");

		assertAll(
			() -> assertTrue(moved > 20, "the pattern must move with the water clock: " + moved),
			() -> assertEquals(0, still, "the dry tile has nothing to animate: " + still)
		);
	}

	@Test
	@DisplayName("a ground water layer draws through the terrain-format surface inside the client's region")
	void waterRegion_shouldDrawTheGroundLayerThroughTheSurfaceProgram() {
		HdSettings.set(HdFeature.WATER, 1);
		HdWaterTime.reset();
		try {
			// Outside a region there is no water draw at all: the layer is stock
			// and the seam is never reached, which is what the row promises.
			long idle = toolkit.hdWaterBinds();
			long idleGround = toolkit.hdWaterGroundBinds();
			int[] stock = drawFrame(true, false);
			long outside = toolkit.hdWaterBinds() - idle;
			long outsideGround = toolkit.hdWaterGroundBinds() - idleGround;

			// The client's own water region, then the layer inside it.
			beginFrame();
			cameraMatrices();
			uploadScene();
			toolkit.method1906(true);
			HdSettings.set(HdFeature.MIPMAPS, 1);
			long before = toolkit.hdWaterBinds();
			long groundBefore = toolkit.hdWaterGroundBinds();
			toolkit.AA(-1, 1583160, 40, 127);
			HdSettings.set(HdFeature.MIPMAPS, 0);
			boolean opened = toolkit.hdWaterActive();
			long openedBinds = toolkit.hdWaterBinds() - before;
			boolean mapsOnTheirUnit = toolkit.hdWaterMapsAvailable()
				&& toolkit.hdBoundTexture(HdWaterSource.WATER_NORMAL_UNIT) != null;
			final int minFilter;
			if (toolkit.hdWaterMapsAvailable()) {
				toolkit.hdActiveTextureUnit(HdWaterSource.WATER_NORMAL_UNIT);
				minFilter = GL11.glGetTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER);
				toolkit.hdActiveTextureUnit(0);
			} else {
				minFilter = 0;
			}

			wetLayer.method1462(new int[] { 0, 1 }, 2);
			// The layer must hand the program back the moment it has drawn, or the
			// rest of the region - and every later water draw - runs a program
			// written for the ground pass and loses its texture.
			boolean released = !toolkit.hdWaterGroundBound();
			wetLayer.method1462(new int[] { 0, 1 }, 2);
			long groundBinds = toolkit.hdWaterGroundBinds() - groundBefore;
			boolean stillOwned = toolkit.hdWaterActive();
			int[] frame = capture();
			int lit = litMask(frame).length;
			toolkit.l();
			boolean closed = !toolkit.hdWaterActive();
			int differing = differing(frame, stock);

			System.out.println("[hd-water-ground] outside=" + outside + "/" + outsideGround
				+ " opened=" + opened + " regionBinds=" + openedBinds + " groundBinds=" + groundBinds
				+ " released=" + released
				+ " maps=" + mapsOnTheirUnit + " minFilter=" + minFilter + " lit=" + lit
				+ " stillOwned=" + stillOwned + " closed=" + closed + " pxVsStock=" + differing);

			assertAll(
				() -> assertEquals(0L, outside, "a layer outside a region must stay stock"),
				() -> assertEquals(0L, outsideGround, "no ground surface outside a region"),
				() -> assertTrue(opened, "the client's water region must open the HD seam"),
				() -> assertEquals(1L, openedBinds, "the region opens the region program once"),
				() -> assertEquals(2L, groundBinds,
					"each layer must draw with the terrain-format surface"),
				() -> assertTrue(released, "the layer must hand the program back"),
				() -> assertTrue(!toolkit.hdWaterMapsAvailable() || mapsOnTheirUnit,
					"the maps must be on their own units before the surface samples them"),
				() -> assertTrue(!toolkit.hdWaterMapsAvailable() || minFilter == 9987,
					"the water maps must carry a mip chain on their own unit: " + minFilter),
				() -> assertTrue(stillOwned, "the region stays open across the layer's own program"),
				() -> assertTrue(closed, "the region's end must close the seam"),
				() -> assertTrue(lit > 0, "the surface must draw pixels: " + lit),
				() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
			);
		} finally {
			HdSettings.set(HdFeature.WATER, 0);
			HdSettings.set(HdFeature.MIPMAPS, 0);
			toolkit.l();
		}
	}

	@Test
	@DisplayName("the row's custom value reaches a ground water layer too")
	void customRow_shouldDrawTheGroundLayerThroughTheCustomSurface() {
		HdSettings.set(HdFeature.WATER, 2);
		HdWaterTime.reset();
		try {
			beginFrame();
			cameraMatrices();
			uploadScene();
			toolkit.method1906(true);
			HdSettings.set(HdFeature.MIPMAPS, 1);
			long before = toolkit.hdWaterGroundBinds();
			toolkit.AA(-1, 1583160, 40, 127);
			HdSettings.set(HdFeature.MIPMAPS, 0);
			wetLayer.method1462(new int[] { 0, 1 }, 2);
			long binds = toolkit.hdWaterGroundBinds() - before;
			HdWaterGate.Surface surface = toolkit.hdWaterLastGroundSurface();
			boolean released = !toolkit.hdWaterGroundBound();
			boolean owned = toolkit.hdWaterActive();
			capture();
			toolkit.l();
			System.out.println("[hd-water-ground-custom] binds=" + binds + " surface=" + surface
				+ " released=" + released + " regionStillOwned=" + owned);
			assertAll(
				() -> assertEquals(1L, binds, "the layer must draw through a terrain-format surface"),
				() -> assertEquals(HdWaterGate.Surface.CUSTOM, surface,
					"the row's third value is the style on terrain as on a model"),
				() -> assertTrue(released, "the layer must hand the program back"),
				() -> assertTrue(owned, "the region keeps its own program"),
				() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
			);
		} finally {
			HdSettings.set(HdFeature.WATER, 0);
			HdSettings.set(HdFeature.MIPMAPS, 0);
			toolkit.l();
		}
	}

	@Test
	@DisplayName("the row off and a dry tile both make no caustics upload at all")
	void off_shouldIssueNoUpload() {
		int[] dryOnly = litMask(drawOnlyDry());
		assertTrue(dryOnly.length > 0, "the dry tile must be visible for this to mean anything");

		HdSettings.set(HdFeature.CAUSTICS, 0);
		long before = toolkit.hdCausticsUploads();
		int[] off = drawFrame(true, true);
		int[] offAgain = drawFrame(true, true);
		long offUploads = toolkit.hdCausticsUploads() - before;

		HdSettings.set(HdFeature.CAUSTICS, 1);
		long beforeDry = toolkit.hdCausticsUploads();
		drawOnlyDry();
		long dryUploads = toolkit.hdCausticsUploads() - beforeDry;

		long beforeWet = toolkit.hdCausticsUploads();
		drawOnlyWet();
		long wetUploads = toolkit.hdCausticsUploads() - beforeWet;

		System.out.println("[hd-caustics] uploads: row off=" + offUploads + ", dry tile=" + dryUploads
			+ ", wet tile=" + wetUploads + ", off frames differ=" + differing(off, offAgain));

		assertAll(
			() -> assertEquals(0L, offUploads, "the row off must make no GL call"),
			() -> assertEquals(0, differing(off, offAgain), "two off frames are the same frame"),
			() -> assertEquals(0L, dryUploads, "no water over the tile, nothing to upload"),
			() -> assertTrue(wetUploads > 0L, "the wet tile is where the upload happens"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("a lake water layer keeps its HD surface through the client's terrain pass")
	void lakeWater_shouldKeepItsSurfaceThroughTheTerrainPass() {
		HdWaterTime.reset();
		HdSettings.set(HdFeature.WATER, 0);
		int[] terrainOnly = twoGroundPasses(wetLayer);
		int terrainPixels = litMask(terrainOnly).length;

		HdSettings.set(HdFeature.WATER, 1);
		long before = toolkit.hdWaterGroundBinds();
		int[] surfaceFrame;
		long binds;
		HdWaterGate.Surface surface;
		boolean released;
		boolean closed;
		try {
			surfaceFrame = twoGroundPasses(wetLayer);
			binds = toolkit.hdWaterGroundBinds() - before;
			surface = toolkit.hdWaterLastGroundSurface();
			released = !toolkit.hdWaterGroundBound();
			closed = !toolkit.hdWaterActive();
		} finally {
			HdSettings.set(HdFeature.WATER, 0);
		}
		int surfacePixels = litMask(surfaceFrame).length;
		int replaced = differing(surfaceFrame, terrainOnly);

		System.out.println("[hd-water-ground] lake two passes: binds=" + binds + " surface=" + surface
			+ " released=" + released + " closed=" + closed + " px terrain=" + terrainPixels
			+ " surface=" + surfacePixels + " differing=" + replaced);

		assertAll(
			() -> assertEquals(2L, binds,
				"both of the client's passes over this tile must draw it as water"),
			() -> assertEquals(HdWaterGate.Surface.STILL, surface, "a lake layer is the calm surface"),
			() -> assertTrue(released, "the layer must hand the program back"),
			() -> assertTrue(closed, "a layer outside a region closes the seam it opened"),
			() -> assertTrue(surfacePixels > 0, "the water surface must paint the tile: " + surfacePixels),
			() -> assertEquals(terrainPixels, surfacePixels,
				"the surface must cover exactly the tile the terrain program covers"),
			() -> assertTrue(replaced > 20,
				"the surface must survive the terrain pass instead of being replaced: " + replaced),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("a dry tile never takes the water surface, so ground keeps its own draw")
	void dryTile_shouldKeepTheTerrainProgram() {
		HdWaterTime.reset();
		HdSettings.set(HdFeature.WATER, 0);
		// The terrain pass builds its ground atlas page on the first draw and
		// falls back to the stock single texture for that one frame, so the frame
		// this test compares is the second: same reason the row gets its own
		// discarded first frame below.
		twoGroundPasses(dryLayer);
		int[] stock = twoGroundPasses(dryLayer);

		HdSettings.set(HdFeature.WATER, 1);
		twoGroundPasses(dryLayer);
		long beforeGround = toolkit.hdWaterGroundBinds();
		long beforeWater = toolkit.hdWaterBinds();
		int[] rowOn;
		long groundBinds;
		long waterBinds;
		try {
			rowOn = twoGroundPasses(dryLayer);
			groundBinds = toolkit.hdWaterGroundBinds() - beforeGround;
			waterBinds = toolkit.hdWaterBinds() - beforeWater;
		} finally {
			HdSettings.set(HdFeature.WATER, 0);
		}

		System.out.println("[hd-water-ground] dry two passes: groundBinds=" + groundBinds
			+ " waterBinds=" + waterBinds + " px=" + litMask(rowOn).length
			+ " vsStock=" + differing(rowOn, stock));

		assertAll(
			() -> assertEquals(0L, groundBinds, "dry ground never runs the water surface"),
			() -> assertEquals(0L, waterBinds, "and the seam is not even opened for it"),
			() -> assertTrue(litMask(rowOn).length > 0, "the dry tile is still painted"),
			() -> assertEquals(0, differing(rowOn, stock),
				"the row must leave dry ground bit-identical: " + differing(rowOn, stock)),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@DisplayName("the lake reads as water in pixels: a palette body, moving ripples and the caustics net")
	void lake_shouldReadAsWaterNotWhite() {
		HdSettings.set(HdFeature.WATER, 1);
		HdSettings.set(HdFeature.CAUSTICS, 1);
		HdWaterTime.reset();
		int[] first;
		int[] later;
		int[] noCaustics;
		int[] terrainOnly;
		try {
			// The frame that compiles the surface program draws nothing, and the
			// rows were off for every proof before this one, so the first water
			// frame here is that frame. It is drawn and dropped, as the row does.
			twoGroundPasses(wetLayer);
			first = twoGroundPasses(wetLayer);
			advanceTo(500L);
			later = twoGroundPasses(wetLayer);
			HdSettings.set(HdFeature.CAUSTICS, 0);
			advanceTo(500L);
			noCaustics = twoGroundPasses(wetLayer);
			// The same tile and the same two passes with the Water row off: the
			// terrain texture the report was showing instead of water.
			HdSettings.set(HdFeature.WATER, 0);
			advanceTo(500L);
			terrainOnly = twoGroundPasses(wetLayer);
		} finally {
			HdSettings.set(HdFeature.WATER, 0);
			HdSettings.set(HdFeature.CAUSTICS, 0);
			HdWaterTime.reset();
		}

		int lit = drawnPixels(first);
		int white = whitePixels(first);
		int palette = palettePixels(first);
		int moved = differing(first, later);
		// Against `later`, not `first`: the row is toggled after the clock has
		// advanced, so comparing with the t=0 frame would count the water's own
		// movement as a caustics difference.
		int net = differing(later, noCaustics);
		int bodied = differing(first, terrainOnly);
		System.out.println("[hd-water-ground] lake body: lit=" + lit + " white=" + white
			+ " palette=" + palette + " moved=" + moved + " caustics=" + net
			+ " vsTerrain=" + bodied
			+ " mean=" + meanColour(first)
			+ " centre=" + Integer.toHexString(first[first.length / 2 + SIZE / 2]));

		assertAll(
			() -> assertTrue(lit > 0, "the lake must be drawn at all: " + lit),
			// The bug this pins: every surface took the layer's bound terrain
			// texture - white for a lake - as its body, so the water was a bright
			// scrolling sheet instead of water.
			() -> assertTrue(white < lit / 20,
				"the body must not be a white sheet: " + white + " of " + lit + " px"),
			// The body must be the surface's own, not the layer's terrain texture:
			// most of the tile has to differ from the row-off frame, which is the
			// texture the report saw instead of water. The palette itself is
			// proved at the seam (HdWaterSource/HdWaterPipeline) and this fixture's
			// calm sheen is the fog colour, so the per-texel palette count is
			// logged rather than asserted on.
			() -> assertTrue(bodied > lit / 2,
				"the surface must own the tile, not the terrain texture: " + bodied
					+ " of " + lit + " px differ from the row-off frame"),
			// Two frames of the same scene, so the body is moving water and not a
			// still image that happens to be the right colour.
			() -> assertTrue(moved > lit / 10,
				"the surface must move between frames: " + moved + " px"),
			// 634HD's net is the seabed's: the surface must not paint it, so the
			// row on and the row off are the same lake.
			() -> assertEquals(0, net,
				"the lake surface is water and sky, not the caustic net: " + net + " px"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	/** The frame's mean colour over drawn pixels, for the log. */
	private static String meanColour(int[] frame) {
		long red = 0;
		long green = 0;
		long blue = 0;
		int drawn = 0;
		for (int pixel : frame) {
			if ((pixel & 0xFFFFFF) == 0x0000FF) {
				continue;
			}
			red += pixel >> 16 & 0xFF;
			green += pixel >> 8 & 0xFF;
			blue += pixel & 0xFF;
			drawn++;
		}
		return "rgb(" + red / Math.max(drawn, 1) + "," + green / Math.max(drawn, 1) + ","
			+ blue / Math.max(drawn, 1) + ")";
	}

	/** Pixels the lake actually painted: everything but the frame's clear colour. */
	private static int drawnPixels(int[] frame) {
		int count = 0;
		for (int pixel : frame) {
			if ((pixel & 0xFFFFFF) != 0x0000FF) {
				count++;
			}
		}
		return count;
	}

	/** Pixels at or near white: what a white sheet would be, everywhere. */
	private static int whitePixels(int[] frame) {
		int count = 0;
		for (int pixel : frame) {
			int red = pixel >> 16 & 0xFF;
			int green = pixel >> 8 & 0xFF;
			int blue = pixel & 0xFF;
			if (red >= 235 && green >= 235 && blue >= 235) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Pixels carrying either end of the water palette: teal or blue-dominant.
	 * The deep end is 0x00758e and the shallow end tints the stock texel to a
	 * blue-grey, so both have blue above red; dry terrain in this fixture does
	 * not, which keeps the count about the water and not about the frame.
	 */
	private static int palettePixels(int[] frame) {
		int count = 0;
		for (int pixel : frame) {
			int red = pixel >> 16 & 0xFF;
			int green = pixel >> 8 & 0xFF;
			int blue = pixel & 0xFF;
			if (blue > red + 12 && green > red) {
				count++;
			}
		}
		return count;
	}

	/**
	 * The client's own order for a frame with no water region: the ground pass
	 * draws the layer, then the terrain pass draws it again. This is the pair of
	 * draws a lake in an ordinary region gets.
	 */
	private static int[] twoGroundPasses(GlGroundLayer layer) {
		beginFrame();
		cameraMatrices();
		uploadScene();
		toolkit.method1904();
		toolkit.method1906(true);
		layer.method1462(new int[] { 0, 1 }, 2);
		layer.method1462(new int[] { 0, 1 }, 2);
		return capture();
	}

	// -- Frames -----------------------------------------------------------

	private static int[] drawFrame(boolean dry, boolean wet) {
		beginFrame();
		cameraMatrices();
		uploadScene();
		toolkit.method1906(true);
		if (dry) {
			dryLayer.method1462(new int[] { 0, 1 }, 2);
		}
		if (wet) {
			wetLayer.method1462(new int[] { 0, 1 }, 2);
		}
		return capture();
	}

	private static int[] drawOnlyDry() {
		return drawFrame(true, false);
	}

	private static int[] drawOnlyWet() {
		HdSettings.set(HdFeature.CAUSTICS, 1);
		return drawFrame(false, true);
	}

	/** Advances the water clock by {@code millis}, one clamped frame at a time. */
	private static void advanceTo(long millis) {
		HdWaterTime.reset();
		long remaining = millis;
		while (remaining > 0L) {
			long step = Math.min(remaining, HdWaterTime.MAX_STEP_MILLIS);
			HdWaterTime.advance(step);
			remaining -= step;
		}
	}

	private static void cameraMatrices() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glFrustum(-0.4D, 0.4D, -0.4D, 0.4D, 1.0D, 400.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef((float) CAMERA_X, (float) CAMERA_Y, (float) CAMERA_Z);
	}

	/**
	 * The scene's uploads, with a deliberately dim sun.
	 *
	 * <p>The stock harness lights a white tile to 255, where an added term has no
	 * headroom to be seen. This frame sits at roughly 100 grey - ambient 0.2 plus
	 * a 0x404040 sun's N.L - so the caustics gain is measurable rather than
	 * clamped away.
	 */
	private static void uploadScene() {
		toolkit.B(0.2F);
		toolkit.scaleHeight(0x404040, 1.0F, 0.0F, 0.0F, 0.6F, 0.8F);
		toolkit.UA(0xFF0000, 100, (int) FAR - 300);
		toolkit.method1918(false);
		toolkit.copyLights(0, new PointLight[0]);
		toolkit.method1900(1);
		toolkit.method1938(-1, true);
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
		GL11.glClearColor(0.0F, 0.0F, 1.0F, 1.0F);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	private static int[] capture() {
		GL11.glFinish();
		assertEquals(GL11.GL_NO_ERROR, GL11.glGetError(), "GL error while drawing");
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

	/** The pixels one layer's tile covers: anything that is not the blue clear. */
	private static int[] litMask(int[] frame) {
		int count = 0;
		for (int pixel : frame) {
			if (lit(pixel)) {
				count++;
			}
		}
		int[] mask = new int[count];
		int at = 0;
		for (int index = 0; index < frame.length; index++) {
			if (lit(frame[index])) {
				mask[at++] = index;
			}
		}
		return mask;
	}

	private static boolean lit(int pixel) {
		int red = (pixel >> 16) & 0xFF;
		int green = (pixel >> 8) & 0xFF;
		int blue = pixel & 0xFF;
		return !(red < 16 && green < 16 && blue > 240);
	}

	private static int changed(int[] first, int[] second, int[] mask) {
		int count = 0;
		for (int index : mask) {
			if (difference(first[index], second[index]) > CHANNEL_STEP) {
				count++;
			}
		}
		return count;
	}

	private static int darker(int[] first, int[] second, int[] mask) {
		int count = 0;
		for (int index : mask) {
			if (brightness(first[index]) + CHANNEL_STEP < brightness(second[index])) {
				count++;
			}
		}
		return count;
	}

	private static int brightness(int pixel) {
		return ((pixel >> 16) & 0xFF) + ((pixel >> 8) & 0xFF) + (pixel & 0xFF);
	}

	private static int differing(int[] first, int[] second) {
		int count = 0;
		for (int index = 0; index < first.length; index++) {
			if (difference(first[index], second[index]) > CHANNEL_STEP) {
				count++;
			}
		}
		return count;
	}

	private static int difference(int first, int second) {
		int worst = 0;
		for (int shift = 0; shift <= 16; shift += 8) {
			worst = Math.max(worst, Math.abs(((first >> shift) & 0xFF) - ((second >> shift) & 0xFF)));
		}
		return worst;
	}

	/** The frame's sky: a gradient with structure, so the ripples have something to bend. */
	private static final class Sky extends Class99_Sub1 {

		private final GlCubeTexture cube;

		private Sky(GlCubeTexture cube) {
			this.cube = cube;
		}

		@Override
		public GlCubeTexture method3232() {
			return this.cube;
		}
	}

	private static GlCubeTexture gradientSky() {
		byte[][] faces = new byte[6][SKY_SIZE * SKY_SIZE * 3];
		for (int face = 0; face < 6; face++) {
			for (int y = 0; y < SKY_SIZE; y++) {
				float t = y / (float) (SKY_SIZE - 1);
				for (int x = 0; x < SKY_SIZE; x++) {
					int index = (y * SKY_SIZE + x) * 3;
					// A vertical gradient with two-texel bands over it: a bend of a
					// hundredth of the cube's width crosses most of a band.
					int band = (y >> 1 & 1) == 0 ? 55 : 0;
					faces[face][index] = (byte) (90 + 120 * t + band);
					faces[face][index + 1] = (byte) (120 + 100 * t + band);
					faces[face][index + 2] = (byte) (190 + 60 * t + band);
				}
			}
		}
		GlCubeTexture cube = new GlCubeTexture(toolkit, GL11.GL_RGB, SKY_SIZE, false, faces, GL11.GL_RGB);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		return cube;
	}

	// -- Geometry ---------------------------------------------------------

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

	/**
	 * Two tiles in one real layer pair: a dry quad on the left and a submerged
	 * quad on the right, each its own layer with its own stock depth byte.
	 */
	private static void createTerrain() {
		int[][] heights = new int[3][3];
		ground = new GlGround(toolkit, 0, 0x7, 2, 2, heights, heights, TILE_UNITS);

		int stride = 28;
		float[][] positions = {
			{ 0.0F, 0.0F, 0.0F }, { 60.0F, 0.0F, 0.0F },
			{ 60.0F, 64.0F, 0.0F }, { 0.0F, 64.0F, 0.0F },
			{ 68.0F, 0.0F, 0.0F }, { 128.0F, 0.0F, 0.0F },
			{ 128.0F, 64.0F, 0.0F }, { 68.0F, 64.0F, 0.0F }
		};
		ByteBuffer data = ByteBuffer.allocate(stride * positions.length).order(ByteOrder.LITTLE_ENDIAN);
		for (float[] position : positions) {
			data.putFloat(position[0]).putFloat(position[1]).putFloat(position[2]);
			data.put((byte) 255).put((byte) 255).put((byte) 255).put((byte) 255);
			data.putFloat(0.0F).putFloat(0.0F).putFloat(1.0F);
		}
		terrain = new GlVboElementsArrayBuffer(toolkit, stride, data.array(), data.array().length, false);
		ground.aClass177_15 = new Class177(terrain, GL11.GL_FLOAT, 3, 0);
		ground.aClass177_16 = new Class177(terrain, GL11.GL_FLOAT, 3, 16);
		ground.aClass177_14 = null;
		ground.tileVertexIndices[0] = new short[] { 0, 1, 2, 0, 2, 3 };
		ground.tileVertexIndices[1] = new short[] { 4, 5, 6, 4, 6, 7 };

		// arg5 is the stock waterDepth byte: the value Terrain reads per tile and
		// GA hands to the stock water effect as depth / 255.
		dryLayer = new GlGroundLayer(ground, -1, 0, 0, 0, 0);
		wetLayer = new GlGroundLayer(ground, -1, 0, 0, 0, WATER_DEPTH);
		colour(dryLayer, 0);
		colour(wetLayer, 1);
	}

	/** One layer's colour buffer, filled for every vertex in the shared buffer. */
	private static void colour(GlGroundLayer layer, int tile) {
		layer.initColours(8);
		for (int vertex = 0; vertex < 8; vertex++) {
			layer.setVertexColour(0, vertex, 0xFFFFFF, 1.0F);
			layer.setVertexOpaque(vertex);
		}
		layer.finishColours(8);
		layer.addTriangle(0, 0, tile);
		layer.addTriangle(0, 1, tile);
	}
}
