package com.jagex.game.runetek5.client.hd.shadow;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jagex.game.runetek5.client.hd.HdFeature;

@DisplayName("cut-out shadow policy")
class ShadowCutoutTest {

	/** The Shadow row's indices: Off, 1024, 2048, 4096. */
	private static final int SHADOWS_OFF = 0;

	private static final int SHADOWS_1024 = 1;

	private static final int CUTOUT_OFF = 0;

	private static final int CUTOUT_ON = 1;

	@Test
	@DisplayName("cut-out casters need HD, per-pixel lighting, a map and the row")
	void requested_shouldRequireEveryTerm() {
		assertAll(
			() -> assertFalse(ShadowCutout.requested(false, true, SHADOWS_1024, CUTOUT_ON), "HD off"),
			() -> assertFalse(ShadowCutout.requested(true, false, SHADOWS_1024, CUTOUT_ON), "per-pixel off"),
			() -> assertFalse(ShadowCutout.requested(true, true, SHADOWS_OFF, CUTOUT_ON), "no shadow map"),
			() -> assertFalse(ShadowCutout.requested(true, true, SHADOWS_1024, CUTOUT_OFF), "row off"),
			() -> assertTrue(ShadowCutout.requested(true, true, SHADOWS_1024, CUTOUT_ON), "all on")
		);
	}

	@Test
	@DisplayName("a caster without a group table falls back to one position-only draw")
	void canDrawGroups_shouldRefuseAReleasedTable() {
		int[] groups = { 0, 3 };
		short[] materials = { 0, 1, 2 };
		assertAll(
			() -> assertFalse(ShadowCutout.canDrawGroups(false, true, groups, materials), "row off"),
			// method2807 releases the table for a mesh whose faces all lack an
			// alpha group while the geometry stays uploaded; dereferencing it
			// threw the live NPE this predicate exists to prevent.
			() -> assertFalse(ShadowCutout.canDrawGroups(true, true, null, materials), "released table"),
			() -> assertFalse(ShadowCutout.canDrawGroups(true, true, new int[0], materials), "no groups"),
			() -> assertFalse(ShadowCutout.canDrawGroups(true, true, new int[] { 0 }, materials),
				"a table with no end index is not a group list"),
			() -> assertFalse(ShadowCutout.canDrawGroups(true, true, groups, null), "no materials"),
			() -> assertFalse(ShadowCutout.canDrawGroups(true, false, groups, materials),
				"without texcoords the alpha test samples one texel"),
			() -> assertTrue(ShadowCutout.canDrawGroups(true, true, groups, materials))
		);
	}

	@Test
	@DisplayName("the per-group draw needs the row, the texcoords and both face tables")
	void canDrawGroups_shouldRequireEveryTerm() {
		int[] groups = { 0, 3 };
		short[] materials = { (short) 1, (short) 1, (short) 1 };

		assertAll(
			() -> assertTrue(ShadowCutout.canDrawGroups(true, true, groups, materials), "all present"),
			// The row off is the solid depth pass: one draw, no groups.
			() -> assertFalse(ShadowCutout.canDrawGroups(false, true, groups, materials), "row off"),
			// method2807 releases the texcoords with the face tables for a mesh whose
			// mask no longer asks for them, while the geometry stays.
			() -> assertFalse(ShadowCutout.canDrawGroups(true, false, groups, materials), "texcoords released"),
			() -> assertFalse(ShadowCutout.canDrawGroups(true, true, null, materials), "group table released"),
			() -> assertFalse(ShadowCutout.canDrawGroups(true, true, groups, null), "material table released"),
			() -> assertFalse(ShadowCutout.canDrawGroups(true, true, new int[0], materials), "no groups at all"),
			// The loop reads group + 1 as the end of the last group's span.
			() -> assertFalse(ShadowCutout.canDrawGroups(true, true, new int[] { 0 }, materials),
				"one entry names no span")
		);
	}

	@Test
	@DisplayName("the threshold is half-opaque, inside the GL reference range")
	void threshold_shouldBeHalfOpaque() {
		assertTrue(ShadowCutout.THRESHOLD > 0.0F && ShadowCutout.THRESHOLD < 1.0F,
			"a threshold outside (0,1) either rejects every texel or none");
	}

	@Test
	@DisplayName("the row is stored under 634HD's key and is selectable")
	void feature_shouldBeThe634Row() {
		assertAll(
			() -> assertTrue(HdFeature.forKey("cutoutShadows") == HdFeature.CUTOUT_SHADOWS,
				"the saved key must match 634HD's"),
			() -> assertTrue(HdFeature.CUTOUT_SHADOWS.support().appliesToday(),
				"the pass reads the row live"),
			() -> assertTrue(HdFeature.CUTOUT_SHADOWS.valueCount() == 2, "Off and On")
		);
	}
}
