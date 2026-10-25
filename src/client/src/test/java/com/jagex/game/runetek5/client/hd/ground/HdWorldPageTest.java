package com.jagex.game.runetek5.client.hd.ground;

import static com.jagex.game.runetek5.client.hd.ground.HdMaterialMap.ANY_ART;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.unpack_Sub1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * The world-texture page's copy: two registered materials land in the cells the
 * material map names, with the pixels, and the alpha, of the texture stock would
 * have bound for them.
 *
 * <p>This is the half of the atlas that the GPU test cannot see directly: a
 * black tile on a real draw is either a page that was never copied or a cell
 * the shader read somewhere else, and this test separates them.
 *
 * <p>Each sampler answers with art of its own here, so a page that reads the
 * wrong one fails on the colour and not only on the alpha.
 */
@DisplayName("592HD world-texture page")
class HdWorldPageTest {

	/** Material 2's art from the alpha sampler: half transparent, so a kept alpha shows. */
	private static final int TRANSLUCENT = 0x7F00FF00;

	/** Material 3's art from the alpha sampler: opaque, and a different colour. */
	private static final int YELLOW = 0xFFFFFF00;

	/** Material 2's art from the opaque sampler: black, the one colour it leaves at alpha 0. */
	private static final int BLACK = 0x00000000;

	/** Material 3's art from the opaque sampler: opaque cyan. */
	private static final int CYAN = 0xFF00FFFF;

	@Test
	@DisplayName("each registered material's art lands in its own cell, alpha included")
	void pixels_shouldCopyEachRegisteredCell() {
		HdMaterialMap map = new HdMaterialMap();
		StubMaterials materials = new StubMaterials();
		assertEquals(0, map.register(2, new MaterialType(), ANY_ART), "the first material takes cell 0");
		assertEquals(1, map.register(3, new MaterialType(), ANY_ART), "the second takes cell 1");

		int[] page = HdWorldPage.pixels(map, materials);

		assertAll(
			() -> assertEquals(TRANSLUCENT, page[cellAt(0)],
				"cell 0 is material 2's own art, alpha as the sampler left it: " + hex(page, 0)),
			() -> assertEquals(YELLOW, page[cellAt(1)], "cell 1 is material 3's art: " + hex(page, 1)),
			() -> assertEquals(0, page[cellAt(2)], "a cell no material holds stays empty")
		);
	}

	@Test
	@DisplayName("a material stock uploads as RGB reaches the page opaque, from the RGB sampler")
	void pixels_shouldKeepRgbMaterialsOpaque() {
		// Stock binds an RGB texture for these materials, so their alpha is 1
		// whatever the art holds - and the page stands in for that texture.
		HdMaterialMap map = new HdMaterialMap();
		StubMaterials materials = new StubMaterials();
		materials.rgbUpload = true;
		assertEquals(0, map.register(2, new MaterialType(), ANY_ART), "the first material takes cell 0");
		assertEquals(1, map.register(3, new MaterialType(), ANY_ART), "the second takes cell 1");

		int[] page = HdWorldPage.pixels(map, materials);

		assertAll(
			// An RGB upload has no alpha channel at all, so its black texel is
			// opaque black. The alpha sampler's half-transparent green is what
			// the page held here before the upload's alpha was reproduced.
			() -> assertEquals(0xFF000000, page[cellAt(0)],
				"black art must reach the page opaque black: " + hex(page, 0)),
			// Not the alpha sampler's yellow: the art is the one the upload fed,
			// which is the half the alpha alone cannot show.
			() -> assertEquals(CYAN, page[cellAt(1)],
				"the art must come from the RGB sampler, not the alpha sampler: " + hex(page, 1))
		);
	}

	@ParameterizedTest(name = "effect slot {0}: opaque upload = {1}")
	@CsvSource({
		"0, true",
		"1, false",
		"2, true",
		"6, true",
		"7, false",
		"8, true"
	})
	@DisplayName("the two effect slots stock excludes keep their alpha channel")
	void pixels_shouldKeepAlpha_whenTheEffectSlotNeedsIt(int slot, boolean opaqueUpload) {
		HdMaterialMap map = new HdMaterialMap();
		StubMaterials materials = new StubMaterials();
		materials.rgbUpload = true;
		materials.effectSlot = slot;
		assertEquals(0, map.register(2, new MaterialType(), ANY_ART), "the material takes cell 0");

		int[] page = HdWorldPage.pixels(map, materials);

		// Slot 1 and slot 7 are the two the stock cache keeps an alpha channel for,
		// so those materials take the alpha sampler and its half-transparent art;
		// every other slot uploads RGB and reaches the page opaque.
		assertEquals(opaqueUpload ? 0xFF000000 : TRANSLUCENT, page[cellAt(0)],
			"slot " + slot + (opaqueUpload ? " uploads RGB, so the page is opaque"
				: " uploads with its alpha, so the page keeps it") + ": " + hex(page, 0));
	}

	/** The page texel at a cell, as the failure messages print it. */
	private static String hex(int[] page, int cell) {
		return String.format("#%08X", page[cellAt(cell)]);
	}

	/** The page texel at a cell's top-left inset, where the shader's origin lands. */
	private static int cellAt(int cell) {
		return HdWorldAtlas.y(cell) * HdWorldAtlas.SIZE + HdWorldAtlas.x(cell);
	}

	/** Two materials, each sampler answering with art of its own. */
	private static final class StubMaterials implements unpack_Sub1 {

		/** aBoolean640: when set, stock uploads the material as RGB and drops its alpha. */
		private boolean rgbUpload;

		/** aByte89, the texture-effect slot; 1 and 7 are the ones stock keeps alpha for. */
		private int effectSlot = 2;

		@Override
		public MaterialType getMetrics(int material) {
			MaterialType type = new MaterialType();
			// The two fields OpenGLTextureCache reads to choose an RGB upload.
			type.aBoolean640 = this.rgbUpload;
			type.aByte89 = (byte) this.effectSlot;
			return type;
		}

		@Override
		public boolean textureAvailable(int material) {
			return material == 2 || material == 3;
		}

		@Override
		public int[] getPixels(boolean readReversed, int material, float brightness, int width,
				int height) {
			return filled(material == 2 ? BLACK : CYAN, width * height);
		}

		@Override
		public float[] method2394(float arg0, int arg1, int arg2, int arg3) {
			return null;
		}

		@Override
		public int[] getPixelsArgb(int material, float brightness, int width, int height) {
			return filled(material == 2 ? TRANSLUCENT : YELLOW, width * height);
		}

		private static int[] filled(int colour, int texels) {
			int[] pixels = new int[texels];
			Arrays.fill(pixels, colour);
			return pixels;
		}
	}
}
