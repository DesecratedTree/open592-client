package com.jagex.game.runetek5.client.hd.ground;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.unpack_Sub1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The one owner of the "which upload, and is there a cell's worth of art"
 * decision: the format follows {@code OpenGLTextureCache.method1549}, and a
 * material the page cannot hold answers null rather than zeroes.
 *
 * <p>Both callers - the page build and the material map's gate - read this
 * answer, so a divergence here is the see-through ground: an RGB material whose
 * alpha was kept turns an opaque texture translucent at its dark texels, and a
 * cell published for art that never arrived is a discarded fragment with no
 * colour and no depth.
 */
@DisplayName("material art, in its stock upload's format")
class HdMaterialArtTest {

	/** The alpha sampler's art: half transparent, so a kept alpha is visible. */
	private static final int TRANSLUCENT = 0x7F00FF00;

	/** The RGB sampler's art: black at alpha zero, the texel that made holes. */
	private static final int BLACK = 0x00000000;

	@Test
	@DisplayName("an RGB upload reaches the page opaque, from the RGB sampler")
	void art_shouldStateTheAlphaAnRgbUploadHas() {
		StubMaterials materials = new StubMaterials();
		materials.rgbUpload = true;

		int[] art = HdMaterialArt.art(materials, 2);

		assertAll(
			() -> assertEquals(0xFF000000, art[0],
				"an RGB upload has no alpha channel, so its black texel is opaque black: #"
					+ Integer.toHexString(art[0])),
			() -> assertEquals(1, materials.rgbCalls, "the RGB sampler is the one that answered"),
			() -> assertEquals(0, materials.argbCalls, "an RGB material never asks the alpha sampler")
		);
	}

	@Test
	@DisplayName("a material stock keeps alpha for keeps its own alpha, from the ARGB sampler")
	void art_shouldKeepAlpha_whenTheEffectSlotNeedsIt() {
		StubMaterials materials = new StubMaterials();
		materials.rgbUpload = true;
		materials.effectSlot = 1;

		int[] art = HdMaterialArt.art(materials, 2);

		assertAll(
			() -> assertEquals(TRANSLUCENT, art[0], "slot 1 is uploaded with its alpha"),
			() -> assertEquals(1, materials.argbCalls),
			() -> assertEquals(0, materials.rgbCalls, "and never from the RGB sampler")
		);
	}

	@Test
	@DisplayName("the sampler's own array is never written through")
	void art_shouldNotMutateTheSamplersArray() {
		StubMaterials materials = new StubMaterials();
		materials.rgbUpload = true;

		int[] art = HdMaterialArt.art(materials, 2);

		assertAll(
			() -> assertTrue(art != materials.rgbArt, "an RGB result is a stated copy, not the sampler's array"),
			() -> assertEquals(BLACK, materials.rgbArt[0],
				"the sampler's texel still has the alpha it produced")
		);
	}

	@Test
	@DisplayName("a material the page cannot hold answers null, never an empty cell")
	void art_shouldRefuseWhatThePageCannotFill() {
		StubMaterials missing = new StubMaterials();
		missing.available = false;
		StubMaterials undescribed = new StubMaterials();
		undescribed.describe = false;
		StubMaterials nullArt = new StubMaterials();
		nullArt.nullArt = true;
		StubMaterials shortArt = new StubMaterials();
		shortArt.shortBy = 1;

		assertAll(
			() -> assertNull(HdMaterialArt.art(null, 2), "no provider, no art"),
			() -> assertNull(HdMaterialArt.art(missing, 2), "art the provider does not have"),
			() -> assertNull(HdMaterialArt.art(undescribed, 2),
				"a material with no metric row has no upload to follow, and the stock samplers index it"),
			() -> assertNull(HdMaterialArt.art(nullArt, 2), "a sampler that answers null"),
			() -> assertNull(HdMaterialArt.art(shortArt, 2), "less than a cell is not a cell"),
			() -> assertNull(HdMaterialArt.art(new StubMaterials(), -1), "an id a 592 cache cannot address")
		);
	}

	@Test
	@DisplayName("the upload rule is the stock one: aBoolean640 and the two alpha slots")
	void uploadsRgb_shouldFollowTheStockUpload() {
		assertAll(
			() -> assertFalse(HdMaterialArt.uploadsRgb(null), "no record, no RGB claim"),
			() -> assertTrue(HdMaterialArt.uploadsRgb(type(true, 2)), "aBoolean640 at an ordinary slot"),
			() -> assertFalse(HdMaterialArt.uploadsRgb(type(false, 2)), "no aBoolean640, alpha kept"),
			() -> assertFalse(HdMaterialArt.uploadsRgb(type(true, 1)), "slot 1 keeps its alpha"),
			() -> assertFalse(HdMaterialArt.uploadsRgb(type(true, 7)), "slot 7 keeps its alpha"),
			() -> assertTrue(HdMaterialArt.uploadsRgb(type(true, 0)), "slot 0 does not"),
			() -> assertTrue(HdMaterialArt.uploadsRgb(type(true, 8)), "nor does slot 8")
		);
	}

	private static MaterialType type(boolean rgbUpload, int effectSlot) {
		MaterialType type = new MaterialType();
		type.aBoolean640 = rgbUpload;
		type.aByte89 = (byte) effectSlot;
		return type;
	}

	/** A provider whose two samplers answer with art of their own. */
	private static final class StubMaterials implements unpack_Sub1 {

		private boolean rgbUpload;

		private int effectSlot = 2;

		private boolean available = true;

		private boolean describe = true;

		private boolean nullArt;

		private int shortBy;

		private int argbCalls;

		private int rgbCalls;

		private int[] rgbArt;

		@Override
		public MaterialType getMetrics(int material) {
			return this.describe ? type(this.rgbUpload, this.effectSlot) : null;
		}

		@Override
		public boolean textureAvailable(int material) {
			return this.available;
		}

		@Override
		public int[] getPixels(boolean readReversed, int material, float brightness, int width,
				int height) {
			this.rgbCalls++;
			this.rgbArt = filled(BLACK, width * height);
			return this.nullArt ? null : this.rgbArt;
		}

		@Override
		public float[] method2394(float arg0, int arg1, int arg2, int arg3) {
			return null;
		}

		@Override
		public int[] getPixelsArgb(int material, float brightness, int width, int height) {
			this.argbCalls++;
			return this.nullArt ? null : filled(TRANSLUCENT, width * height - this.shortBy);
		}

		private static int[] filled(int colour, int texels) {
			int[] pixels = new int[Math.max(0, texels)];
			Arrays.fill(pixels, colour);
			return pixels;
		}
	}
}
