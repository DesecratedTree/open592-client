package com.jagex.game.runetek5.client.hd.shader;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.client.hd.shader.HdDrawRows.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("592HD lighting gate")
class HdLightingGateTest {

	/** {@code GL_MODULATE}. */
	private static final int MODULATE = 7681;

	/** {@code GL_REPLACE}. */
	private static final int REPLACE = 8448;

	/** The draw the shader was built for: HD on, row on, fogged, textured, lit. */
	private static HdDrawRequest pass() {
		return new HdDrawRequest(HdDrawRows.of(Row.HD, Row.PER_PIXEL_LIGHTING, Row.POINT_LIGHTS,
			Row.NORMALS, Row.TEXTURE, Row.FOG), 0, MODULATE);
	}

	@Test
	@DisplayName("a draw that agrees with every precondition binds")
	void shouldBind_shouldAcceptAFullyLitDraw() {
		assertTrue(HdLightingGate.shouldBind(pass(), true));
	}

	@Test
	@DisplayName("each veto alone keeps the draw on fixed function")
	void shouldBind_shouldRejectEachVeto() {
		assertAll(
			() -> assertFalse(HdLightingGate.shouldBind(pass(), false), "program not linked"),
			() -> assertFalse(HdLightingGate.shouldBind(pass().without(Row.HD), true), "592HD off"),
			() -> assertFalse(HdLightingGate.shouldBind(pass().without(Row.PER_PIXEL_LIGHTING), true),
				"row off"),
			() -> assertFalse(HdLightingGate.shouldBind(pass().withTextureCombine(REPLACE), true),
				"GL_REPLACE"),
			() -> assertFalse(HdLightingGate.shouldBind(pass().withTextureUnit(1), true), "unit 1"),
			() -> assertFalse(HdLightingGate.shouldBind(pass().without(Row.NORMALS), true), "no normals"),
			() -> assertFalse(HdLightingGate.shouldBind(pass().with(Row.MATERIAL_EFFECT), true),
				"effect active"),
			() -> assertFalse(HdLightingGate.shouldBind(null, true), "no request")
		);
	}

	@Test
	@DisplayName("a fogless draw binds: the shader drops the band instead of the draw")
	void shouldBind_shouldAcceptAFoglessDraw() {
		// The terrain pass turns GL_FOG off and lights its tiles itself. Vetoing
		// it here would leave the ground as the one surface in the scene with no
		// per-pixel light and no sun shadow; uFogOff exists so it does not have
		// to be a veto.
		HdDrawRequest fogless = pass().without(Row.FOG);
		HdDrawRequest fogged = pass();

		assertAll(
			() -> assertTrue(HdLightingGate.wantsLighting(fogless), "the settings still want lighting"),
			() -> assertTrue(HdLightingGate.shouldBind(fogless, true), "and this draw may be lit"),
			() -> assertTrue(HdLightingGate.shouldBind(fogged, true),
				"fog no longer changes the answer"),
			() -> assertFalse(fogless.fogEnabled(), "the request still reports the real fog state")
		);
	}

	@Test
	@DisplayName("wantsLighting is only HD plus the row, so the rest cannot cost a compile")
	void wantsLighting_shouldIgnoreTheDrawState() {
		HdDrawRequest worstCase = pass().without(Row.POINT_LIGHTS, Row.NORMALS, Row.TEXTURE, Row.FOG)
			.with(Row.MATERIAL_EFFECT).withTextureUnit(1).withTextureCombine(REPLACE);

		assertAll(
			() -> assertTrue(HdLightingGate.wantsLighting(worstCase)),
			() -> assertFalse(HdLightingGate.wantsLighting(pass().without(Row.HD))),
			() -> assertFalse(HdLightingGate.wantsLighting(pass().without(Row.PER_PIXEL_LIGHTING))),
			() -> assertFalse(HdLightingGate.wantsLighting(null))
		);
	}

	@Test
	@DisplayName("the allocation-free settings check cannot disagree with the per-draw check")
	void lightingRequested_shouldMatchTheDrawRequest(@TempDir Path home) {
		HdSettings.setDirectoryForTest(home.toFile());
		try {
			HdSettings.setEnabled(true, null);
			HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 1);
			boolean rowOn = HdLightingGate.lightingRequested();
			boolean rowOnDraw = HdLightingGate.wantsLighting(
				HdDrawRequest.current(HdDrawRows.of(Row.NORMALS, Row.FOG), 0, MODULATE));

			HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 0);
			boolean rowOff = HdLightingGate.lightingRequested();
			boolean rowOffDraw = HdLightingGate.wantsLighting(
				HdDrawRequest.current(HdDrawRows.of(Row.NORMALS, Row.FOG), 0, MODULATE));

			HdSettings.setEnabled(false, null);

			assertAll(
				() -> assertTrue(rowOn),
				() -> assertEquals(rowOn, rowOnDraw),
				() -> assertFalse(rowOff),
				() -> assertEquals(rowOff, rowOffDraw),
				() -> assertFalse(HdLightingGate.lightingRequested(), "592HD off")
			);
		} finally {
			HdSettings.resetForTest();
		}
	}

	@Test
	@DisplayName("a material effect outranks every other reason to bind")
	void shouldBind_shouldLetTheEffectWin() {
		assertAll(
			() -> assertFalse(HdLightingGate.shouldBind(pass().with(Row.MATERIAL_EFFECT), true)),
			() -> assertTrue(HdLightingGate.shouldBind(pass(), true))
		);
	}

	@Test
	@DisplayName("point lights are clamped into the shader's four slots, and zeroed when off")
	void drawState_shouldClampAndGate() {
		HdDrawRequest lightsOn = pass().without(Row.TEXTURE);
		HdDrawRequest lightsOff = pass().without(Row.TEXTURE, Row.POINT_LIGHTS);

		assertAll(
			() -> assertEquals(0, HdLightingGate.drawState(lightsOn, 0).pointLights()),
			() -> assertEquals(2, HdLightingGate.drawState(lightsOn, 2).pointLights()),
			() -> assertEquals(HdDrawState.MAX_POINT_LIGHTS,
				HdLightingGate.drawState(lightsOn, 9).pointLights()),
			() -> assertEquals(0, HdLightingGate.drawState(lightsOn, -3).pointLights()),
			() -> assertEquals(0, HdLightingGate.drawState(lightsOff, 3).pointLights())
		);
	}

	@Test
	@DisplayName("only GL_MODULATE on the base unit is reproduced, other combines stay stock")
	void textureCombine_shouldOnlyAllowModulateOnUnitZero() {
		assertAll(
			() -> assertTrue(HdLightingGate.supportsTextureCombine(false, 1, REPLACE),
				"an untextured primitive has no combine to disagree with"),
			() -> assertTrue(HdLightingGate.supportsTextureCombine(true, 0, MODULATE)),
			() -> assertFalse(HdLightingGate.supportsTextureCombine(true, 0, REPLACE), "GL_REPLACE"),
			() -> assertFalse(HdLightingGate.supportsTextureCombine(true, 0, 34165), "GL_ADD"),
			() -> assertFalse(HdLightingGate.supportsTextureCombine(true, 1, MODULATE),
				"multi-texturing is out of scope")
		);
	}

	@Test
	@DisplayName("the draw state compares by value so repeated uploads can be skipped")
	void drawState_shouldCompareByValue() {
		assertAll(
			() -> assertEquals(new HdDrawState(true, 2), new HdDrawState(true, 2)),
			() -> assertEquals(new HdDrawState(true, 2).hashCode(), new HdDrawState(true, 2).hashCode()),
			() -> assertFalse(new HdDrawState(true, 2).equals(new HdDrawState(false, 2))),
			() -> assertFalse(new HdDrawState(true, 2).equals(new HdDrawState(true, 1))),
			() -> assertFalse(new HdDrawState(true, 2).equals(null))
		);
	}
}
