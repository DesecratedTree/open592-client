package com.jagex.game.runetek5.client.hd.material;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jagex.game.runetek5.client.hd.content.HdContent;

/**
 * The map-coverage proof: every material {@code materials.json} gives maps to
 * must resolve to pixels this machine can sample, and the shipped sets' blocks
 * must be exactly how most of them do it.
 *
 * <p>The count is the claim. A set that resolves through its PNG is coverage
 * too, but a set the decoder bypasses is not: the printed line names blocks and
 * PNGs separately, and an unresolved set is printed with its reason, so a
 * failure here says which map and why rather than only that something is wrong.
 */
@DisplayName("592HD material map coverage")
class HdMaterialCoverageTest {

	@Test
	@DisplayName("every shipped map set resolves, blocks first, with a height")
	void everyMaterial_shouldResolve() {
		HdContent content = HdContent.get();
		HdMaterialTextures.Coverage coverage = HdMaterialTextures.coverage(content);
		System.out.println(coverage.summary());
		for (int i = 0; i < coverage.reasons.size(); i++) {
			System.out.println("[hd-maps] unresolved " + coverage.reasons.get(i));
		}

		assertAll(
			() -> assertEquals(0, coverage.unresolved,
				"a set whose files are present must decode, never be dropped: " + coverage.reasons),
			() -> assertTrue(coverage.materials >= 190, "the file's declared map sets: " + coverage.materials),
			() -> assertEquals(coverage.materials, coverage.resolved + coverage.notShipped,
				"every declared set is either bound or has no files shipped"),
			() -> assertTrue(coverage.resolved > 0, "some shipped material must bind map data"),
			() -> assertEquals(coverage.resolved, coverage.blockNormal,
				"every bound normal came from BC5 blocks, not a PNG: " + coverage.blockNormal),
			() -> assertEquals(0, coverage.pngNormal, "no bound normal fell back to its PNG"),
			() -> assertEquals(coverage.resolved, coverage.blockOrm,
				"every bound ORM came from BC7 blocks: " + coverage.blockOrm),
			() -> assertEquals(0, coverage.pngOrm, "no bound ORM fell back to its PNG"),
			() -> assertEquals(coverage.resolved, coverage.heightFromPacked + coverage.heightFromPng,
				"every bound set resolves a height")
		);
	}
}
