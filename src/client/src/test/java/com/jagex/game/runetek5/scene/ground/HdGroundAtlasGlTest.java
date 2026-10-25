package com.jagex.game.runetek5.scene.ground;

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
import com.jagex.game.runetek5.buffer.ArrayBufferGL;
import com.jagex.game.runetek5.buffer.ElementsBufferGL;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.GlGroundLayer;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.client.hd.ground.HdTileMaterialMap;
import com.jagex.game.runetek5.client.hd.ground.HdWorldAtlas;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import com.jagex.game.runetek5.unpack_Sub1;

import jaggl.GlTestContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

/**
 * The world-texture atlas in pixels: a live {@link GlGroundLayer} draw whose
 * layer carries a real material id, through the same seam the scene calls
 * ({@code method1462} -> {@code hdBeginGroundLighting}).
 *
 * <p>The earlier proof of the atlas was unit-only: the runtime harness builds
 * its layer with material {@code -1}, so {@code hdBindGroundTextures} declines
 * before it registers a material and the atlas branch never ran on a GPU. This
 * class gives the layer the two things that branch reads - a material id and a
 * mesh whose per-tile map is attached - and pins the facts this harness can
 * state exactly:
 *
 * <ul>
 * <li><b>The atlas cell is what the tile draws.</b> A material with red art
 * draws its stock frame's red, texel for texel (0 px different), so the page
 * cell reproduces the material texture rather than a plausible colour.</li>
 * <li><b>A tile without a material is unchanged.</b> Material {@code -1} with
 * every atlas row on is the same frame as with them off, and binds no atlas.</li>
 * <li><b>A row off is the stock draw.</b> World textures off, material maps
 * off, or both, leave the seamed draw byte-identical to the all-rows-off tile,
 * and none of them binds the atlas.</li>
 * <li><b>Terrain with no normal array keeps its tile.</b> Flat ground builds no
 * normals, so {@code gl_Normal} is the current normal - zero after a pass that
 * set none - and {@code normalize(0)} is NaN. NaN in the scene normal reached
 * the triplanar weights and the tile drew no material at all (0 red px, mean
 * {@code rgb(0,0,0)}); the up-axis fallback draws it (1352 px).</li>
 * </ul>
 *
 * <p>It also pins the ordering rule the atlas depends on: a material that joins
 * the page mid-frame is drawn with its stock texture on that frame (the page it
 * would need does not exist yet), and the page is built before the next draw.
 * No frame ever samples a page it built, which is what keeps the first
 * appearance of a material from being a black or empty tile.
 *
 * <p>The page outlives one region but not one session, so the last test
 * replaces it twice over: two waves of {@link HdWorldAtlas#CAPACITY} materials
 * arrive one per frame, and the cells the first wave holds are recycled into
 * the second. A material that loses its cell falls back to its own stock
 * texture rather than sampling the cell that took its number, and pages again
 * when it is drawn.
 *	 * <p>Runs by default; {@link GlTestContext#open()} skips it with the reason
	 * when this machine cannot give a GL context.
 */
@DisplayName("592HD ground atlas textures")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HdGroundAtlasGlTest {

	/**
	 * The stock texture-effect enum whose combine is {@code GL_MODULATE} -
	 * {@code method1900(1)}. It is not the GL enum itself: the material records an
	 * effect index, and the toolkit maps it.
	 */
	private static final int MODULATE_EFFECT = 1;

	private static final int SIZE = 64;

	/** The scaling {@code GlToolkit.createGround} builds the pass with. */
	private static final int TILE_UNITS = 128;

	private static final float SUN_X = 0.0F;

	private static final float SUN_Y = 0.6F;

	private static final float SUN_Z = 0.8F;

	private static final int CAMERA_X = -64;

	private static final int CAMERA_Y = -32;

	private static final int CAMERA_Z = 200;

	private static final float FAR = 3584.0F;

	/** The layer's own material: the atlas must draw this material's cell. */
	private static final int PRIMARY_MATERIAL = 2;

	/** The tile's second material, recorded in the mesh's per-tile map. */
	private static final int MATE_MATERIAL = 3;

	/**
	 * A material whose art varies across the texture, and a layer that draws it
	 * at the stock one-repeat-per-tile scale. Solid-colour art cannot see the
	 * repeat length: any scale looks the same when every texel is equal, which
	 * is how a 128x-too-large repeat went unnoticed.
	 */
	private static final int GRADIENT_MATERIAL = 4;

	private static GlToolkit toolkit;

	private static Frame frame;

	private static Canvas canvas;

	private static int framebuffer;

	private static int targetTexture;

	private static int depthBuffer;

	private static GlGround ground;

	private static GlGroundLayer primary;

	private static GlGroundLayer mate;

	private static GlGroundLayer untextured;

	private static ElementsBufferGL terrain;

	private static Class177 terrainColours;

	private static ArrayBufferGL terrainIndices;

	private static HdTileMaterialMap tileMaterials;

	private static AtlasMaterials materials;

	private static final java.util.Map<Integer, GlGroundLayer> LAYERS =
		new java.util.HashMap<Integer, GlGroundLayer>();

	@BeforeAll
	static void openToolkit() throws Exception {
		try {
			GlTestContext warmup = GlTestContext.open();
			warmup.close();
		} catch (GlTestContext.Unavailable unavailable) {
			assumeTrue(false, unavailable.getMessage());
		}

		frame = new Frame("592HD ground atlas test");
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(SIZE, SIZE));
		frame.add(canvas);
		frame.pack();
		frame.setLocation(-4000, -4000);
		frame.setVisible(true);
		try {
			materials = new AtlasMaterials();
			toolkit = new GlToolkit(0, canvas, materials, 0, null);
		} catch (Throwable error) {
			frame.dispose();
			assumeTrue(false, "GlToolkit could not start in this JVM: " + error);
		}
		System.out.println("[hd-atlas] toolkit " + GL11.glGetString(GL11.GL_RENDERER) + " | GL "
			+ GL11.glGetString(GL11.GL_VERSION));

		createTarget();
		toolkit.setHeap(toolkit.createHeap(1 << 26));
		createTerrain();

		Path preferences = Files.createTempDirectory("hd-atlas");
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
		HdSettings.set(HdFeature.REFLECTIONS, 0);
		HdSettings.set(HdFeature.FOLIAGE_SWAY, 0);
		HdSettings.set(HdFeature.GRASS_DISPLACEMENT, 0);
		HdSettings.set(HdFeature.WATER_HD, 0);
		HdSettings.set(HdFeature.WORLD_TEXTURES, 0);
		HdSettings.set(HdFeature.MATERIAL_MAPS, 0);
		HdSettings.set(HdFeature.TERRAIN_BLEND, 0);

		// The camera the ground seam reads for scene-to-eye, and the same one the
		// toolkit loads as the modelview before the pass.
		JavaMatrix camera = new JavaMatrix();
		camera.scaleWidth(CAMERA_X, CAMERA_Y, CAMERA_Z);
		toolkit.ea(camera);

		// method1462 packs the layer's visible indices through this packet.
		PcmPlayerThread.aClass4_Sub12_7 = new Packet(64);
	}

	@AfterAll
	static void closeToolkit() {
		HdSettings.resetForTest();
		if (toolkit != null) {
			toolkit.hdEndModelLighting();
		}
		if (frame != null) {
			frame.dispose();
		}
	}

	@Test
	@Order(1)
	@DisplayName("each material's tile draws its own atlas cell, on the frame after the page is built")
	void materialTile_shouldDrawItsOwnAtlasCell() {
		// Each material's first atlas request registers it; the page that exists
		// cannot hold it yet, so that frame must draw the stock texture and bind
		// no atlas. The next frame must draw the same pixels from the atlas.
		Shot mateStock = draw(mate, false, false, false);
		Shot mateBuild = draw(mate, true, true, false);
		Shot mateAtlas = draw(mate, true, true, false);
		Shot primaryStock = draw(primary, false, false, false);
		Shot primaryBuild = draw(primary, true, true, false);
		Shot primaryAtlas = draw(primary, true, true, false);

		int redPixels = count(primaryAtlas.pixels, 40, 40, 40, false);
		int redYellow = count(primaryAtlas.pixels, 40, 40, 40, true);
		int yellowPixels = count(mateAtlas.pixels, 40, 40, 40, true);

		System.out.println("[hd-atlas] cells: build frames bind " + mateBuild.atlasBinds + "/"
			+ primaryBuild.atlasBinds + " and differ from stock "
			+ differing(mateBuild.pixels, mateStock.pixels) + "/"
			+ differing(primaryBuild.pixels, primaryStock.pixels) + " px; atlas frames bind "
			+ mateAtlas.atlasBinds + "/" + primaryAtlas.atlasBinds + " and differ from stock "
			+ differing(mateAtlas.pixels, mateStock.pixels) + "/"
			+ differing(primaryAtlas.pixels, primaryStock.pixels) + " px; red=" + redPixels
			+ " yellow=" + redYellow + "/" + yellowPixels + "; means atlas "
			+ mean(primaryAtlas.pixels) + " / " + mean(mateAtlas.pixels) + " stock "
			+ mean(primaryStock.pixels) + " / " + mean(mateStock.pixels) + "; art asked "
			+ materials.asked + "; units " + boundTexture(0) + "; " + boundTexture(3) + "; "
			+ boundTexture(4) + "; " + boundTexture(5));

		assertAll(
			// No frame samples a page it built.
			() -> assertEquals(0L, mateBuild.atlasBinds, "the mate's page-building frame binds no atlas"),
			() -> assertEquals(0L, primaryBuild.atlasBinds, "and neither does the primary's"),
			() -> assertEquals(0, differing(mateBuild.pixels, mateStock.pixels),
				"the building frame is the stock material draw"),
			() -> assertEquals(0, differing(primaryBuild.pixels, primaryStock.pixels),
				"the building frame is the stock material draw"),
			// The page is current, so the atlas owns the draw and reproduces the art.
			() -> assertEquals(1L, mateAtlas.atlasBinds, "one layer draw with both rows on is one atlas bind"),
			() -> assertEquals(1L, primaryAtlas.atlasBinds, "and the primary's own draw is one"),
			() -> assertEquals(0, differing(primaryAtlas.pixels, primaryStock.pixels),
				"the primary's cell must draw its stock texture, texel for texel: "
					+ differing(primaryAtlas.pixels, primaryStock.pixels) + " px differ"),
			() -> assertEquals(0, differing(mateAtlas.pixels, mateStock.pixels),
				"the mate's cell must draw its stock texture, texel for texel: "
					+ differing(mateAtlas.pixels, mateStock.pixels) + " px differ"),
			() -> assertTrue(redPixels > 400,
				"the primary material's red cell must cover the tile: " + redPixels),
			() -> assertEquals(0, redYellow,
				"and nothing of the mate's cell may show in it: " + redYellow),
			() -> assertTrue(yellowPixels > 400,
				"the mate's yellow cell must cover its own tile: " + yellowPixels),
			() -> assertTrue(differing(primaryAtlas.pixels, mateAtlas.pixels) > 400,
				"two materials on different cells cannot draw the same frame"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@Order(2)
	@DisplayName("a two-material tile blends its own pair through the tile map entry")
	void twoMaterialTile_shouldBlendPerItsMapEntry() {
		int[] plain = draw(primary, true, true, false).pixels;
		int[] blended = draw(primary, true, true, true).pixels;
		int[] blendedAgain = draw(primary, true, true, true).pixels;

		int plainYellow = count(plain, 80, 80, 60, true);
		int blendedYellow = count(blended, 80, 80, 60, true);

		System.out.println("[hd-atlas] blend: differing=" + differing(plain, blended)
			+ ", mate colour " + plainYellow + " -> " + blendedYellow
			+ ", means " + mean(plain) + " / " + mean(blended)
			+ ", repeat differs=" + differing(blended, blendedAgain));

		assertAll(
			() -> assertTrue(blendedYellow > plainYellow + 50,
				"the tile map's mate must reach the drawn tile: " + plainYellow + " -> " + blendedYellow),
			() -> assertTrue(differing(plain, blended) > 100,
				"the blend must change the tile, not just the counter"),
			() -> assertEquals(0, differing(blended, blendedAgain),
				"the same blend state must be the same frame"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@Order(3)
	@DisplayName("a tile without a material is unchanged by the atlas rows")
	void untexturedTile_shouldBeUnchanged() {
		Shot off = draw(untextured, false, false, false);
		Shot on = draw(untextured, true, true, true);

		System.out.println("[hd-atlas] untextured: binds=" + on.atlasBinds
			+ ", differing=" + differing(off.pixels, on.pixels));

		assertAll(
			() -> assertEquals(0L, off.atlasBinds, "no material, no atlas"),
			() -> assertEquals(0L, on.atlasBinds, "an untextured layer has no material to register"),
			() -> assertEquals(0, differing(off.pixels, on.pixels),
				"the rows must not touch a layer the stock path draws untextured"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@Order(4)
	@DisplayName("either atlas row off leaves the stock tile draw byte for byte")
	void rowsOff_shouldBeTheStockTile() {
		Shot stock = draw(primary, false, false, false);
		Shot worldOff = draw(primary, false, true, true);
		Shot mapsOff = draw(primary, true, false, true);
		Shot bothOff = draw(primary, false, false, true);

		System.out.println("[hd-atlas] rows off: world " + differing(stock.pixels, worldOff.pixels)
			+ " px, maps " + differing(stock.pixels, mapsOff.pixels)
			+ " px, both " + differing(stock.pixels, bothOff.pixels)
			+ " px; binds " + stock.atlasBinds + "/" + worldOff.atlasBinds + "/"
			+ mapsOff.atlasBinds + "/" + bothOff.atlasBinds);

		assertAll(
			() -> assertEquals(0L, stock.atlasBinds, "all rows off binds nothing"),
			() -> assertEquals(0L, worldOff.atlasBinds, "world textures off binds nothing"),
			() -> assertEquals(0L, mapsOff.atlasBinds, "material maps off binds nothing"),
			() -> assertEquals(0L, bothOff.atlasBinds, "both off binds nothing"),
			() -> assertEquals(0, differing(stock.pixels, worldOff.pixels),
				"the world-textures row off must be the stock texture draw"),
			() -> assertEquals(0, differing(stock.pixels, mapsOff.pixels),
				"the material-maps row off must be the stock texture draw"),
			() -> assertEquals(0, differing(stock.pixels, bothOff.pixels),
				"both texture rows off must be the stock draw"),
			() -> assertEquals(0, differing(worldOff.pixels, bothOff.pixels),
				"the row that is off cannot matter whose neighbour is on"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	/**
	 * A session walks through many regions, each with its own ground materials,
	 * so the page has to hand its cells on rather than fill once and stay full.
	 * This drives the seam through two waves of {@link HdWorldAtlas#CAPACITY}
	 * materials, one arriving per frame: what a region change looks like, since
	 * the old region's materials stop being drawn while the new region's arrive.
	 *
	 * <p>Facts it pins: the second wave pages exactly as the first did - each
	 * material draws its own stock art texel for texel from its cell on the frame
	 * after it arrives; 20 further frames of a paged tile build nothing; the
	 * atlas holds two textures throughout and every retired page reaches the
	 * toolkit's deallocation queue; a material whose cell was recycled draws its
	 * own stock texture on the frame it returns in, never the cell that took its
	 * number, and pages again the frame after; and a tile whose second material
	 * has no cell still draws only its own material.
	 */
	@Test
	@Order(5)
	@DisplayName("a second wave recycles the page: new materials page, displaced ones keep the stock tile")
	void secondWave_shouldRecycleCellsAndFallBackToStock() {
		long buildsBefore = toolkit.hdGroundPageBuilds();
		long disposalsBefore = toolkit.hdGroundPageDisposals();
		int evictionsBefore = toolkit.hdGroundAtlasEvictions();
		int texturesBefore = toolkit.hdGroundAtlasTextures();
		// The page already holds the primary and the mate from the tests above.
		int takenBefore = 2;

		int pagedFirst = 0;
		for (int index = 0; index < WAVE_SIZE; index++) {
			int material = FIRST_WAVE + index;
			GlGroundLayer layer = layer(material);
			// Arrival frame, then the frame after it, then the stock reference.
			Shot build = draw(layer, true, true, false);
			Shot atlas = draw(layer, true, true, false);
			Shot stock = draw(layer, false, false, false);
			assertEquals(0L, build.atlasBinds,
				"material " + material + " must not sample a page it built");
			assertEquals(1L, atlas.atlasBinds,
				"the first wave's material " + material + " must page");
			assertEquals(0, differing(atlas.pixels, stock.pixels),
				"material " + material + "'s cell must draw its own art, texel for texel");
			pagedFirst++;
		}

		int pagedSecond = 0;
		for (int index = 0; index < WAVE_SIZE; index++) {
			int material = SECOND_WAVE + index;
			GlGroundLayer layer = layer(material);
			Shot build = draw(layer, true, true, false);
			Shot atlas = draw(layer, true, true, false);
			Shot stock = draw(layer, false, false, false);
			assertEquals(0L, build.atlasBinds,
				"wave two's material " + material + " must not sample a page it built");
			assertEquals(1L, atlas.atlasBinds,
				"wave two's material " + material + " must page once the old wave is displaced");
			assertEquals(0, differing(atlas.pixels, stock.pixels),
				"a recycled cell must draw its own material's art: " + material);
			pagedSecond++;
		}

		int paged = pagedFirst + pagedSecond;
		int evictions = toolkit.hdGroundAtlasEvictions() - evictionsBefore;
		long builds = toolkit.hdGroundPageBuilds() - buildsBefore;
		long disposals = toolkit.hdGroundPageDisposals() - disposalsBefore;
		int texturesAfter = toolkit.hdGroundAtlasTextures();

		// A paged tile on frames that change nothing: the page is already current.
		int steadyMaterial = SECOND_WAVE + 10;
		GlGroundLayer steadyLayer = layer(steadyMaterial);
		Shot steadyStock = draw(steadyLayer, false, false, false);
		long steadyBuilds = toolkit.hdGroundPageBuilds();
		long steadyBinds = 0L;
		int[] steadyPixels = null;
		for (int frame = 0; frame < 20; frame++) {
			Shot steady = draw(steadyLayer, true, true, false);
			steadyBinds += steady.atlasBinds;
			assertEquals(0, differing(steady.pixels, steadyStock.pixels),
				"a paged tile keeps drawing its cell on every frame");
			if (steadyPixels == null) {
				steadyPixels = steady.pixels;
			}
		}
		long steadyRepeats = toolkit.hdGroundPageBuilds() - steadyBuilds;

		// The same id, new art: the page keeps what it was built with.
		long beforeRepaint = toolkit.hdGroundPageBuilds();
		materials.repaint(steadyMaterial, 0xFF00FF00);
		Shot repainted = draw(steadyLayer, true, true, false);
		materials.unpaint(steadyMaterial);
		Shot restored = draw(steadyLayer, true, true, false);
		long repaintBuilds = toolkit.hdGroundPageBuilds() - beforeRepaint;

		// One frame that draws the whole page and then one material more, the way
		// a region's ground pass submits every layer before the frame ends. The
		// materials this frame draws are in view, so the newcomer waits rather
		// than taking a cell from a tile being drawn.
		GlGroundLayer[] inView = new GlGroundLayer[HdWorldAtlas.CAPACITY];
		for (int index = 0; index < inView.length; index++) {
			inView[index] = layer(SECOND_WAVE + index);
		}
		int newcomerMaterial = SECOND_WAVE + WAVE_SIZE;
		GlGroundLayer newcomer = layer(newcomerMaterial);
		long buildsBeforeCrowd = toolkit.hdGroundPageBuilds();
		int evictionsBeforeCrowd = toolkit.hdGroundAtlasEvictions();
		GlGroundLayer[] crowded = java.util.Arrays.copyOf(inView, inView.length + 1);
		crowded[inView.length] = newcomer;
		long crowdBinds = drawFrame(true, true, false, inView);
		long crowdBindsWithNewcomer = drawFrame(true, true, false, crowded);
		long crowdBuilds = toolkit.hdGroundPageBuilds() - buildsBeforeCrowd;
		int crowdEvictions = toolkit.hdGroundAtlasEvictions() - evictionsBeforeCrowd;

		// A tile whose own material is paged and whose second material no longer
		// holds a cell: the blend can only reach one of the pair.
		int displacedMate = FIRST_WAVE + 7;
		HdTileMaterialMap partial = new HdTileMaterialMap();
		partial.set(0, 0, steadyMaterial, displacedMate);
		ground.hdAttachTileMaterialsForTest(partial);
		draw(steadyLayer, true, true, true);
		Shot unpagedMate = draw(steadyLayer, true, true, true);
		Shot ownOnly = draw(steadyLayer, true, true, false);
		ground.hdAttachTileMaterialsForTest(tileMaterials);
		int mateBlend = differing(unpagedMate.pixels, ownOnly.pixels);

		// A material the second wave displaced, drawn again as a returning
		// region would: its old cell belongs to a newer material, so the frame it
		// registers on must be its own stock texture, and the frame after its new
		// cell.
		int returningMaterial = FIRST_WAVE + 5;
		GlGroundLayer returningLayer = layer(returningMaterial);
		Shot returningStock = draw(returningLayer, false, false, false);
		long buildsBeforeReturn = toolkit.hdGroundPageBuilds();
		int evictionsBeforeReturn = toolkit.hdGroundAtlasEvictions();
		Shot returning = draw(returningLayer, true, true, false);
		Shot repaged = draw(returningLayer, true, true, false);
		long returningBuilds = toolkit.hdGroundPageBuilds() - buildsBeforeReturn;
		int returningEvictions = toolkit.hdGroundAtlasEvictions() - evictionsBeforeReturn;

		// The retired pages are queued for the toolkit's tick, which is where the
		// client hands them to the driver. Flush it and watch the accounted bytes
		// fall: a queue that never drains would be the leak this test looks for.
		int bytesBeforeTick = toolkit.textureBuffersSize;
		toolkit.tick(0);
		int releasedBytes = bytesBeforeTick - toolkit.textureBuffersSize;

		System.out.println("[hd-atlas] waves: " + paged + " materials paged (" + pagedFirst
			+ " first, " + pagedSecond + " second) over " + (WAVE_SIZE * 2) + " frames; builds "
			+ builds + " (+" + returningBuilds + " for a returning material), disposals "
			+ disposals + ", evictions " + evictions + " (+" + returningEvictions
			+ "), atlas textures " + texturesBefore + " -> " + texturesAfter + ", tick released "
			+ releasedBytes + " B; steady 20 frames: " + steadyRepeats + " builds, " + steadyBinds
			+ " binds; repaint: " + repaintBuilds + " builds, "
			+ differing(repainted.pixels, steadyPixels) + " px vs before, restored "
			+ differing(restored.pixels, steadyPixels) + " px; tile with a displaced mate "
			+ mean(unpagedMate.pixels) + " vs its own " + mean(ownOnly.pixels) + ", blend differs "
			+ mateBlend + " px; returning material binds " + returning.atlasBinds + "/"
			+ repaged.atlasBinds + ", " + differing(returning.pixels, returningStock.pixels) + "/"
			+ differing(repaged.pixels, returningStock.pixels) + " px vs its stock, "
			+ differing(repaged.pixels, steadyPixels) + " px vs its neighbour's cell; crowd frame: "
			+ crowdBinds + " binds for " + HdWorldAtlas.CAPACITY + " layers, "
			+ crowdBindsWithNewcomer + " for " + (HdWorldAtlas.CAPACITY + 1)
			+ " (one newcomer), " + crowdBuilds + " builds, " + crowdEvictions + " evictions");

		final int pagedCount = paged;
		final int firstWavePaged = pagedFirst;
		final int secondWavePaged = pagedSecond;
		final int evictionCount = evictions;
		final long buildCount = builds;
		final long disposalCount = disposals;
		final long steadyRepeatCount = steadyRepeats;
		final long steadyBindCount = steadyBinds;
		final long repaintBuildCount = repaintBuilds;
		final int repaintDifference = differing(repainted.pixels, steadyPixels);
		final int restoredDifference = differing(restored.pixels, steadyPixels);
		final int unpagedMateBlend = mateBlend;
		final long returningBuildCount = returningBuilds;
		final int returningEvictionCount = returningEvictions;
		final long returningBind = returning.atlasBinds;
		final long repagedBind = repaged.atlasBinds;
		final int returningDifference = differing(returning.pixels, returningStock.pixels);
		final int repagedDifference = differing(repaged.pixels, returningStock.pixels);
		final int neighbourDifference = differing(repaged.pixels, steadyPixels);
		final int tickReleased = releasedBytes;
		final int freeBefore = HdWorldAtlas.CAPACITY - takenBefore;
		final long crowdBindCount = crowdBinds;
		final long crowdNewcomerBinds = crowdBindsWithNewcomer;
		final long crowdBuildCount = crowdBuilds;
		final int crowdEvictionCount = crowdEvictions;

		assertAll(
			() -> assertEquals(WAVE_SIZE, firstWavePaged,
				"every material of the first wave must page"),
			() -> assertEquals(WAVE_SIZE, secondWavePaged,
				"and every material of the second wave must page after the first is displaced"),
			() -> assertEquals(pagedCount, evictionCount + freeBefore,
				"each arrival past the free cells recycles exactly one cell"),
			// One build per arrival, none for the frames that change nothing.
			() -> assertEquals((long) pagedCount, buildCount,
				"one page build per arrival, no rebuild for the ones already held"),
			() -> assertEquals((long) pagedCount, disposalCount,
				"each build must release the page it replaces"),
			() -> assertEquals(texturesBefore, texturesAfter,
				"the atlas must hold a constant two textures however many waves arrive"),
			() -> assertEquals(0L, steadyRepeatCount, "a paged tile must not rebuild the page"),
			() -> assertEquals(20L, steadyBindCount,
				"and must bind the atlas on every one of those frames"),
			() -> assertEquals(0L, repaintBuildCount,
				"changing a material's art must not rebuild the page"),
			() -> assertEquals(0, repaintDifference,
				"the page serves the art it was built with, as the stock cache does"),
			() -> assertEquals(0, restoredDifference,
				"and the same art once the source is restored"),
			() -> assertEquals(0, unpagedMateBlend,
				"a tile must not blend toward anything when its mate has no cell: "
					+ unpagedMateBlend + " px"),
			() -> assertEquals(0L, returningBind,
				"a returning material must not sample the cell that took its number"),
			() -> assertEquals(0, returningDifference,
				"it must draw its own stock texture on that frame"),
			() -> assertEquals(1L, repagedBind,
				"and its own new cell the frame after"),
			() -> assertEquals(0, repagedDifference,
				"which must be its own art, texel for texel"),
			() -> assertTrue(neighbourDifference > 100,
				"a recycled cell must not be left showing another material's art: "
					+ neighbourDifference + " px"),
			() -> assertEquals(1L, returningBuildCount,
				"a returning material changes the working set once"),
			() -> assertEquals(1, returningEvictionCount,
				"and takes exactly one cell from the material that was coldest"),
			() -> assertEquals((long) HdWorldAtlas.CAPACITY, crowdBindCount,
				"a frame that draws the whole page binds the atlas once per layer"),
			() -> assertEquals((long) HdWorldAtlas.CAPACITY, crowdNewcomerBinds,
				"a newcomer in that frame draws its stock texture, not a cell in view"),
			() -> assertEquals(0L, crowdBuildCount,
				"and changes nothing about the page"),
			() -> assertEquals(0, crowdEvictionCount,
				"no tile in view gives up its cell mid-frame"),
			() -> assertTrue(tickReleased > 0,
				"the tick must release the retired pages to the driver: " + tickReleased + " B"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@Order(6)
	@DisplayName("a terrain draw with no normal array still draws its tile")
	void missingNormal_shouldNotLoseTheTile() {
		// Flat terrain builds no normal array (GlGround.aClass177_16 is null), so
		// gl_Normal is whatever the previous pass left - zero if it set none.
		// normalize(0) is NaN, and NaN in the scene normal travels through the
		// triplanar weights and the lighting into the albedo: the atlas tile
		// turns black and stops drawing its material at all, which is the
		// transparent-grass frame the world-texture rows produced.
		// The wave test's second wave took cells from the coldest materials;
		// drawing the primary once re-registers it and rebuilds the page, so the
		// second frame is the steady atlas frame this test compares against.
		draw(primary, true, true, false);
		Shot normals = draw(primary, true, true, false);
		Class177 saved = ground.aClass177_16;
		ground.aClass177_16 = null;
		GL11.glNormal3f(0.0F, 0.0F, 0.0F);
		Shot zeroed = draw(primary, true, true, false);
		ground.aClass177_16 = saved;
		Shot restored = draw(primary, true, true, false);

		int zeroedRed = count(zeroed.pixels, 40, 40, 40, false);

		System.out.println("[hd-atlas] no normal array: zeroed red=" + zeroedRed + ", binds "
			+ zeroed.atlasBinds + ", mean " + mean(zeroed.pixels) + "; restored differs "
			+ differing(restored.pixels, normals.pixels) + " px");

		assertAll(
			() -> assertEquals(1L, normals.atlasBinds, "the bound-normal frame samples the page"),
			() -> assertEquals(1L, zeroed.atlasBinds, "and so does the normal-less frame"),
			() -> assertTrue(zeroedRed > 400,
				"a zero normal must not reach the plane weights as NaN: " + zeroedRed + " px"),
			() -> assertEquals(0, differing(restored.pixels, normals.pixels),
				"and binding the array again must draw exactly what it drew before"),
			() -> assertEquals(GL11.GL_NO_ERROR, GL11.glGetError())
		);
	}

	@Test
	@Order(7)
	@DisplayName("the atlas repeats at the stock texture scale: a tile shows its material's whole texture, not one texel")
	void repeat_shouldBeTheStockTextureScale() {
		GlGroundLayer gradient = gradientLayer();
		// The first frame fills the page and stays on the stock texture; the
		// second samples the cell now that it holds the art.
		Shot build = draw(gradient, true, true, false);
		Shot atlas = draw(gradient, true, true, false);

		int atlasColours = distinctColours(atlas.pixels);
		int spread = redSpread(atlas.pixels);
		System.out.println("[hd-atlas] repeat: " + atlasColours + " colours, red spread " + spread
			+ "; build binds " + build.atlasBinds + ", atlas binds " + atlas.atlasBinds);

		assertAll(
			() -> assertEquals(0L, build.atlasBinds,
				"the page-building frame stays on the stock material texture"),
			() -> assertEquals(1L, atlas.atlasBinds, "the filled page serves the next frame"),
			// The bug this pins: a page repeat of tileSize * textureScale is 128
			// repeats too wide, so one tile samples a fraction of a texel and the
			// whole tile is one colour. The stock scale spans the whole cell.
			() -> assertTrue(atlasColours > 16,
				"the atlas must span its cell across the tile, not read one texel: " + atlasColours),
			() -> assertTrue(spread > 100,
				"and the tile must show the gradient's whole range, not a flat fill: " + spread)
		);
	}

	// -- Frames -----------------------------------------------------------

	/** One frame down the live path, with the three atlas rows set as asked. */
	private static Shot draw(GlGroundLayer layer, boolean worldTextures, boolean materialMaps,
			boolean terrainBlend) {
		HdSettings.set(HdFeature.WORLD_TEXTURES, worldTextures ? 1 : 0);
		HdSettings.set(HdFeature.MATERIAL_MAPS, materialMaps ? 1 : 0);
		HdSettings.set(HdFeature.TERRAIN_BLEND, terrainBlend ? 1 : 0);
		// The frame seam's atlas clock, as beginWorldDraw advances it: a frame
		// starts here, so materials this frame draws are the ones it cannot
		// recycle.
		toolkit.hdGroundFrameBegin();
		long before = toolkit.hdGroundAtlasBinds();
		beginFrame();
		cameraMatrices();
		uploadScene();
		// method4034's lit pass: the ground shader is enabled and its normals are
		// the array the layer binds.
		toolkit.method1906(true);
		layer.method1462(new int[] { 0 }, 1);
		return new Shot(capture(), toolkit.hdGroundAtlasBinds() - before);
	}

	/**
	 * One frame that draws several layers, as a region's ground pass does: every
	 * layer is submitted inside a single frame, so the atlas' staleness clock is
	 * advanced once for all of them.
	 */
	private static long drawFrame(boolean worldTextures, boolean materialMaps,
			boolean terrainBlend, GlGroundLayer... layers) {
		HdSettings.set(HdFeature.WORLD_TEXTURES, worldTextures ? 1 : 0);
		HdSettings.set(HdFeature.MATERIAL_MAPS, materialMaps ? 1 : 0);
		HdSettings.set(HdFeature.TERRAIN_BLEND, terrainBlend ? 1 : 0);
		toolkit.hdGroundFrameBegin();
		long before = toolkit.hdGroundAtlasBinds();
		beginFrame();
		cameraMatrices();
		uploadScene();
		toolkit.method1906(true);
		for (GlGroundLayer layer : layers) {
			layer.method1462(new int[] { 0 }, 1);
		}
		capture();
		return toolkit.hdGroundAtlasBinds() - before;
	}

	/** {@code method1952}: identity, rotX(180), then the camera, under the scene's frustum. */
	private static void cameraMatrices() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glFrustum(-0.4D, 0.4D, -0.4D, 0.4D, 1.0D, 400.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		GL11.glTranslatef((float) CAMERA_X, (float) CAMERA_Y, (float) CAMERA_Z);
	}

	/** The stock uploads, under whatever modelview the frame currently holds. */
	private static void uploadScene() {
		toolkit.B(0.2F);
		toolkit.scaleHeight(0xFFFFFF, 1.0F, 0.0F, SUN_X, SUN_Y, SUN_Z);
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

	/** The frame as packed {@code 0xRRGGBB}. */
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

	/**
	 * What is bound on a unit and what a few of its texels are, for the log only.
	 *
	 * <p>These queries change GL's active unit directly, so the caller's last
	 * probe leaves unit 0 selected and clears the error flag: the toolkit's own
	 * unit cache is not told about the detour and the next frame would bind its
	 * material texture on whatever unit the probe left behind.
	 */
	private static String boundTexture(int unit) {
		org.lwjgl.opengl.GL13.glActiveTexture(org.lwjgl.opengl.GL13.GL_TEXTURE0 + unit);
		int id = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
		int width = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
		int height = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);
		ByteBuffer pixels = BufferUtils.createByteBuffer(Math.max(width * height * 4, 4));
		GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
		// Cell 1's first texel on a 512-wide page: x = 64, y = 0.
		// For the one-row id map, texel 3; for the page, cell 1's first texel.
		int far = height == 1 ? Math.min(3 * 4, pixels.capacity() - 4) : Math.min(64 * 4, pixels.capacity() - 4);
		org.lwjgl.opengl.GL13.glActiveTexture(org.lwjgl.opengl.GL13.GL_TEXTURE0);
		GL11.glGetError();
		return id + " " + width + "x" + height + " [" + (pixels.get(0) & 0xFF) + ","
			+ (pixels.get(1) & 0xFF) + "," + (pixels.get(2) & 0xFF) + " / "
			+ (pixels.get(far) & 0xFF) + "," + (pixels.get(far + 1) & 0xFF) + ","
			+ (pixels.get(far + 2) & 0xFF) + "]";
	}

	/** The frame's mean colour over drawn pixels (not the blue clear), for the log only. */
	private static String mean(int[] frame) {
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
		return "rgb(" + red / Math.max(drawn, 1) + "," + green / Math.max(drawn, 1)
			+ "," + blue / Math.max(drawn, 1) + ") n=" + drawn;
	}

	/** Pixels differing by more than a channel step. */
	private static int differing(int[] first, int[] second) {
		int count = 0;
		for (int index = 0; index < first.length; index++) {
			if (difference(first[index], second[index]) > 2) {
				count++;
			}
		}
		return count;
	}

	private static int difference(int first, int second) {
		int worst = 0;
		for (int shift = 0; shift <= 16; shift += 8) {
			int delta = Math.abs((first >> shift & 0xFF) - (second >> shift & 0xFF));
			if (delta > worst) {
				worst = delta;
			}
		}
		return worst;
	}

	/**
	 * Pixels of one hue: red-dominant when {@code yellow} is false, else both
	 * red and green above the floor. Blue stays under the ceiling so the clear
	 * colour cannot be counted.
	 */
	private static int count(int[] frame, int red, int green, int blue, boolean yellow) {
		int count = 0;
		for (int pixel : frame) {
			int r = pixel >> 16 & 0xFF;
			int g = pixel >> 8 & 0xFF;
			int b = pixel & 0xFF;
			if (b >= blue) {
				continue;
			}
			if (yellow ? r > red && g > green : r > red && g < green) {
				count++;
			}
		}
		return count;
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
	 * The mesh and three layers: material 2, the tile's mate 3, and an
	 * untextured layer. Every layer draws the same tile, so each frame is one
	 * layer's answer to the same geometry.
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
		terrain = new GlVboElementsArrayBuffer(toolkit, stride, data.array(), data.array().length, false);
		terrainColours = new Class177(terrain, GL11.GL_UNSIGNED_BYTE, 4, 12);
		ground.aClass177_15 = new Class177(terrain, GL11.GL_FLOAT, 3, 0);
		ground.aClass177_16 = new Class177(terrain, GL11.GL_FLOAT, 3, 16);
		ground.aClass177_14 = null;
		ground.tileVertexIndices[0] = new short[] { 0, 1, 2, 0, 2, 3 };
		terrainIndices = triangles();

		primary = layer(PRIMARY_MATERIAL);
		mate = layer(MATE_MATERIAL);
		untextured = layer(-1);

		// The mesh's per-tile map, attached the way constructTileLayers attaches
		// it: tile (0, 0) is built from the primary material and its mate.
		tileMaterials = new HdTileMaterialMap();
		tileMaterials.set(0, 0, PRIMARY_MATERIAL, MATE_MATERIAL);
		ground.hdAttachTileMaterialsForTest(tileMaterials);
	}

	/** The gradient material's layer, at the stock 128 scene units per repeat. */
	private static GlGroundLayer gradientLayer() {
		GlGroundLayer layer = new GlGroundLayer(ground, GRADIENT_MATERIAL, 128, 0, 0, 0);
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

	/** Distinct non-clear RGB triples; a flat fill has one. */
	private static int distinctColours(int[] frame) {
		java.util.HashSet<Integer> colours = new java.util.HashSet<Integer>();
		for (int pixel : frame) {
			if ((pixel & 0xFFFFFF) != 0x0000FF) {
				colours.add(Integer.valueOf(pixel & 0xFFFFFF));
			}
		}
		return colours.size();
	}

	/** Max minus min red over the drawn pixels: a one-texel fill has 0. */
	private static int redSpread(int[] frame) {
		int low = 255;
		int high = 0;
		for (int pixel : frame) {
			if ((pixel & 0xFFFFFF) == 0x0000FF) {
				continue;
			}
			int red = pixel >> 16 & 0xFF;
			low = Math.min(low, red);
			high = Math.max(high, red);
		}
		return high - low;
	}

	private static GlGroundLayer layer(int material) {
		GlGroundLayer cached = LAYERS.get(Integer.valueOf(material));
		if (cached != null) {
			return cached;
		}
		GlGroundLayer layer = new GlGroundLayer(ground, material, 1, 0, 0, 0);
		layer.initColours(4);
		for (int vertex = 0; vertex < 4; vertex++) {
			layer.setVertexColour(0, vertex, 0xFFFFFF, 1.0F);
			layer.setVertexOpaque(vertex);
		}
		layer.finishColours(4);
		layer.addTriangle(0, 0, 0);
		layer.addTriangle(0, 1, 0);
		LAYERS.put(Integer.valueOf(material), layer);
		return layer;
	}

	private static ArrayBufferGL triangles() {
		byte[] indices = { 0, 0, 1, 0, 2, 0, 0, 0, 2, 0, 3, 0 };
		return new GlVboArrayBuffer(toolkit, GL11.GL_UNSIGNED_SHORT, indices, indices.length, false);
	}

	/** One frame: its pixels and how many layer draws bound the atlas. */
	private static final class Shot {

		private final int[] pixels;

		private final long atlasBinds;

		private Shot(int[] pixels, long atlasBinds) {
			this.pixels = pixels;
			this.atlasBinds = atlasBinds;
		}
	}

	/**
	 * The client's material sampler reduced to what the atlas reads: which
	 * materials have art, and the art itself. Both are 64x64, the page's cell
	 * size and what {@code OpenGLTextureCache} asks a small material for. The
	 * primary is red, the mate yellow - colours no lighting term in this frame
	 * can turn into the other.
	 */
	private static final class AtlasMaterials implements unpack_Sub1 {

		private final MaterialType primaryType = new MaterialType();

		private final MaterialType mateType = new MaterialType();

		private final java.util.Map<Integer, MaterialType> types =
			new java.util.HashMap<Integer, MaterialType>();

		/** Art overrides, so a test can repaint a material that is already paged. */
		private final java.util.Map<Integer, Integer> overrides = new java.util.HashMap<Integer, Integer>();

		private void repaint(int material, int colour) {
			this.overrides.put(Integer.valueOf(material), Integer.valueOf(colour));
		}

		private void unpaint(int material) {
			this.overrides.remove(Integer.valueOf(material));
		}

		private AtlasMaterials() {
			// A small material is 64x64: the stock cache and the atlas cell then
			// ask for exactly the art built below. The effect is the MODULATE one
			// because that is the only combine the lit program reproduces - the
			// gate refuses any other, and a fixture that leaves it at the default
			// measures the fixed-function path while claiming to test the shader.
			this.primaryType.isSmall = true;
			this.primaryType.anInt6934 = MODULATE_EFFECT;
			this.mateType.isSmall = true;
			this.mateType.anInt6934 = MODULATE_EFFECT;
		}

		@Override
		public MaterialType getMetrics(int material) {
			if (material == PRIMARY_MATERIAL) {
				return this.primaryType;
			}
			if (material == MATE_MATERIAL) {
				return this.mateType;
			}
			if (material < 0) {
				return null;
			}
			MaterialType type = this.types.get(Integer.valueOf(material));
			if (type == null) {
				type = new MaterialType();
				type.isSmall = true;
				type.anInt6934 = MODULATE_EFFECT;
				this.types.put(Integer.valueOf(material), type);
			}
			return type;
		}

		@Override
		public boolean textureAvailable(int material) {
			return material >= 0 && material <= LAST_MATERIAL;
		}

		@Override
		public int[] getPixels(boolean readReversed, int arg1, float arg2, int arg3, int height) {
			return null;
		}

		@Override
		public float[] method2394(float arg0, int arg1, int arg2, int arg3) {
			return null;
		}

		private final StringBuffer asked = new StringBuffer();

		@Override
		public int[] getPixelsArgb(int material, float brightness, int width, int height) {
			this.asked.append(material).append('@').append(width).append(' ');
			int colour = artColour(material);
			int[] pixels = new int[width * height];
			if (material == GRADIENT_MATERIAL) {
				// A horizontal ramp: the only art here a wrong repeat can flatten.
				for (int row = 0; row < height; row++) {
					for (int column = 0; column < width; column++) {
						int red = 20 + column * 200 / Math.max(width - 1, 1);
						pixels[row * width + column] = 0xFF000000 | red << 16 | 20 << 8 | 20;
					}
				}
				return pixels;
			}
			for (int index = 0; index < pixels.length; index++) {
				pixels[index] = colour;
			}
			return pixels;
		}
	}

	/**
	 * The id ranges the wave test streams; every id maps to its own colour. The
	 * second wave's ids are disjoint from the first's, so a recycled cell that
	 * still held the old material's art would be a different colour.
	 */
	private static final int FIRST_WAVE = 10;

	private static final int SECOND_WAVE = 100;

	/** Materials one wave asks for: exactly one page. */
	private static final int WAVE_SIZE = HdWorldAtlas.CAPACITY;

	private static final int LAST_MATERIAL = SECOND_WAVE + WAVE_SIZE + 40;

	/**
	 * One material's art: the two named materials keep their red and yellow, and
	 * every other id gets a colour derived from the id so a wrong cell is a wrong
	 * colour rather than a plausible one.
	 */
	private static int artColour(int material) {
		Integer override = materials == null ? null : materials.overrides.get(Integer.valueOf(material));
		if (override != null) {
			return override.intValue();
		}
		if (material == PRIMARY_MATERIAL) {
			return 0xFFFF0000;
		}
		if (material == MATE_MATERIAL) {
			return 0xFFFFFF00;
		}
		int red = material * 37 & 0xFF;
		int green = material * 71 & 0xFF;
		int blue = material * 113 & 0xFF;
		if (red == 0 && green == 0) {
			// Never confuse a material with the blue clear or with black.
			red = 1;
		}
		return 0xFF000000 | red << 16 | green << 8 | blue;
	}
}
