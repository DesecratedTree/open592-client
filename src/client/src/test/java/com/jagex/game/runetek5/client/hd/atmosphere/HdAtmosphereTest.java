package com.jagex.game.runetek5.client.hd.atmosphere;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The three atmosphere rows as pure functions: the gate's truth table and the
 * curves the shader restates.
 *
 * <p>Every number here is the prediction a real-GL pixel is checked against in
 * {@code HdAtmosphereRealGlTest}, so a drift in either the Java prediction or
 * the GLSL shows up as a disagreement between the two.
 */
@DisplayName("592HD atmosphere rows")
class HdAtmosphereTest {

	@AfterEach
	void restoreSettings() {
		HdSettings.resetForTest();
	}

	@Test
	@DisplayName("with 592HD off every row is off, whatever the stored values say")
	void resolve_shouldBeIdentityWhenHdIsOff() {
		assertAll(
			() -> assertSame(HdAtmosphere.OFF, HdAtmosphere.resolve(false, true, true, true)),
			() -> assertTrue(HdAtmosphere.resolve(false, true, true, true).isIdentity())
		);
	}

	@Test
	@DisplayName("all three rows off is the identity object, so no uniform is ever uploaded")
	void resolve_shouldReturnTheSharedIdentity() {
		HdAtmosphere off = HdAtmosphere.resolve(true, false, false, false);

		assertAll(
			() -> assertSame(HdAtmosphere.OFF, off),
			() -> assertTrue(off.isIdentity()),
			() -> assertEquals(0.0F, off.aoStrength(), "a zeroed uniform is the off state"),
			() -> assertEquals(0.0F, off.fadeBand(), "a zeroed uniform is the off state")
		);
	}

	@Test
	@DisplayName("each row switches only itself on")
	void resolve_shouldCarryOnlyTheChosenRows() {
		HdAtmosphere fog = HdAtmosphere.resolve(true, true, false, false);
		HdAtmosphere fade = HdAtmosphere.resolve(true, false, true, false);
		HdAtmosphere ao = HdAtmosphere.resolve(true, false, false, true);

		assertAll(
			() -> assertTrue(fog.enhancedFog()),
			() -> assertFalse(fog.distanceFade()),
			() -> assertFalse(fog.ambientOcclusion()),
			() -> assertFalse(fade.enhancedFog()),
			() -> assertTrue(fade.distanceFade()),
			() -> assertTrue(ao.ambientOcclusion()),
			() -> assertEquals(HdAtmosphere.AO_STRENGTH, ao.aoStrength()),
			() -> assertEquals(HdAtmosphere.FADE_BAND, fade.fadeBand())
		);
	}

	@Test
	@DisplayName("the HD fog curve leaves both band ends alone")
	void fogCurve_shouldPinTheBandEnds() {
		// Start of the band: no fog. End of the band: full fog. Those are the two
		// places the player can compare against the stock draw.
		assertAll(
			() -> assertEquals(0.0F, HdAtmosphere.fogFraction(0.0F), 1.0e-6F),
			() -> assertEquals(1.0F, HdAtmosphere.fogFraction(1.0F), 1.0e-6F),
			() -> assertEquals(0.0F, HdAtmosphere.stockFogFraction(5.0F, 40.0F, 40.0F), 1.0e-6F),
			() -> assertEquals(1.0F, HdAtmosphere.stockFogFraction(5.0F, 40.0F, 5.0F), 1.0e-6F),
			() -> assertEquals(0.0F, HdAtmosphere.stockFogFraction(5.0F, 40.0F, 60.0F), 1.0e-6F)
		);
	}

	@Test
	@DisplayName("the HD fog curve is denser than stock everywhere inside the band")
	void fogCurve_shouldBeDenserInsideTheBand() {
		float[] fractions = { 0.05F, 0.25F, 0.5F, 0.5714F, 0.75F, 0.95F };
		for (int i = 0; i < fractions.length; i++) {
			float stock = fractions[i];
			float hd = HdAtmosphere.fogFraction(stock);
			assertTrue(hd < stock && hd > 0.0F,
				"at stock fraction " + stock + " the HD fraction is " + hd);
		}
		// The exact pixel the real-GL test measures: the stock band at z = -20 in
		// a 5..40 band is 4/7, whose square is 0.3265.
		assertEquals(0.32653F, HdAtmosphere.fogFraction(4.0F / 7.0F), 0.0002F);
	}

	@Test
	@DisplayName("the fog curve is monotonic in distance, in both forms")
	void fogCurve_shouldBeMonotonic() {
		float previousStock = 2.0F;
		float previousHd = 2.0F;
		for (float distance = 0.0F; distance <= 45.0F; distance += 0.5F) {
			float stock = HdAtmosphere.stockFogFraction(5.0F, 40.0F, distance);
			float hd = HdAtmosphere.fogFraction(stock);
			assertTrue(stock <= previousStock + 1.0e-6F, "stock fog rose at " + distance);
			assertTrue(hd <= previousHd + 1.0e-6F, "HD fog rose at " + distance);
			previousStock = stock;
			previousHd = hd;
		}
	}

	@Test
	@DisplayName("distance fade is exactly 1 outside the band and 0 at the far plane")
	void fade_shouldOnlyTouchTheOuterBand() {
		float far = 100.0F;
		float bandStart = far * (1.0F - HdAtmosphere.FADE_BAND);

		assertAll(
			() -> assertEquals(1.0F, HdAtmosphere.fadeFraction(0.0F, far), 1.0e-6F),
			() -> assertEquals(1.0F, HdAtmosphere.fadeFraction(20.0F, far), 1.0e-6F),
			() -> assertEquals(1.0F, HdAtmosphere.fadeFraction(bandStart, far), 1.0e-6F),
			() -> assertEquals(0.0F, HdAtmosphere.fadeFraction(far, far), 1.0e-6F),
			() -> assertEquals(0.0F, HdAtmosphere.fadeFraction(far + 10.0F, far), 1.0e-6F),
			// Halfway into the band is half faded, which is the number the pixel
			// proof reads at z = -92.5 in a 100-unit projection.
			() -> assertEquals(0.5F, HdAtmosphere.fadeFraction(92.5F, far), 1.0e-5F)
		);
	}

	@Test
	@DisplayName("distance fade is monotonic and never leaves 0..1")
	void fade_shouldBeClampedAndMonotonic() {
		float previous = 2.0F;
		for (float distance = 0.0F; distance <= 120.0F; distance += 0.25F) {
			float fade = HdAtmosphere.fadeFraction(distance, 100.0F);
			assertTrue(fade >= 0.0F && fade <= 1.0F, "fade left the unit range at " + distance);
			assertTrue(fade <= previous + 1.0e-6F, "fade rose at " + distance);
			previous = fade;
		}
	}

	@Test
	@DisplayName("a projection with no usable far plane disables the fade instead of fading everything")
	void fade_shouldBeInertWithoutAFarPlane() {
		assertAll(
			() -> assertEquals(1.0F, HdAtmosphere.fadeFraction(10.0F, 0.0F), 1.0e-6F),
			() -> assertEquals(1.0F, HdAtmosphere.fadeFraction(10.0F, -3584.0F), 1.0e-6F)
		);
	}

	@Test
	@DisplayName("occlusion takes at most AO_STRENGTH of the ambient term, and none when free")
	void occlusion_shouldScaleAmbientOnly() {
		assertAll(
			() -> assertEquals(1.0F, HdAtmosphere.ambientScale(0.0F), 1.0e-6F),
			() -> assertEquals(1.0F - HdAtmosphere.AO_STRENGTH, HdAtmosphere.ambientScale(1.0F), 1.0e-6F),
			() -> assertEquals(0.725F, HdAtmosphere.ambientScale(0.5F), 1.0e-6F),
			// Out-of-range coverage clamps rather than over-darkening.
			() -> assertEquals(1.0F, HdAtmosphere.ambientScale(-1.0F), 1.0e-6F),
			() -> assertEquals(1.0F - HdAtmosphere.AO_STRENGTH, HdAtmosphere.ambientScale(4.0F), 1.0e-6F)
		);
	}

	@Test
	@DisplayName("the value objects compare by content, so the pipeline can cache them")
	void equality_shouldBeByContent() {
		HdAtmosphere a = HdAtmosphere.resolve(true, true, true, false);
		HdAtmosphere b = HdAtmosphere.resolve(true, true, true, false);
		HdAtmosphere c = HdAtmosphere.resolve(true, true, false, false);

		assertAll(
			() -> assertEquals(a, b),
			() -> assertEquals(a.hashCode(), b.hashCode()),
			() -> assertNotEquals(a, c),
			() -> assertEquals(a, new HdAtmosphere(true, true, false,
				HdAtmosphere.AO_STRENGTH, HdAtmosphere.FADE_BAND))
		);
	}

	@Test
	@DisplayName("the live values come from the pane's rows, and 592HD off overrides them")
	void current_shouldFollowTheSettings() {
		// A fresh reset is the shipped preset (HD on at Medium), which has Fog on
		// and Distance fade on but leaves ambient occlusion off.
		HdSettings.set(HdFeature.FOG, 0);
		HdSettings.set(HdFeature.DISTANCE_FADE, 0);
		HdSettings.set(HdFeature.AMBIENT_OCCLUSION, 0);
		assertSame(HdAtmosphere.OFF, HdAtmosphere.current(), "all rows off must be the identity");

		HdSettings.set(HdFeature.AMBIENT_OCCLUSION, 1);
		assertAll(
			() -> assertTrue(HdAtmosphere.current().ambientOcclusion()),
			() -> assertFalse(HdAtmosphere.current().enhancedFog())
		);
	}
}
