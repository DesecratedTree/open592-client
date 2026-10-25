package com.jagex.game.runetek5.client.hd.light;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The environment-light value and its gate, as arithmetic a test can state.
 *
 * <p>Three properties matter. The gate is a three-way and for the same reason
 * the other lighting rows are: the row only exists inside the per-pixel
 * program. The snapshot is the client's own environment values, and it is
 * {@link HdEnvironmentLight#OFF} rather than a made-up environment when there
 * is none. And the shader's mix is restated here so a pixel test can predict a
 * channel instead of recording one.
 */
@DisplayName("592HD environment light")
class HdEnvironmentLightTest {

	private static final float EPSILON = 1.0E-5F;

	@BeforeAll
	static void redirectPreferences() throws Exception {
		HdSettings.setDirectoryForTest(Files.createTempDirectory("hd-environment").toFile());
	}

	@AfterAll
	static void restoreSettings() {
		HdSettings.resetForTest();
	}

	@Test
	@DisplayName("the row needs 592HD, its own switch and per-pixel lighting")
	void requested_shouldBeAThreeWayAnd() {
		boolean[] values = { false, true };
		for (int i = 0; i < 8; i++) {
			boolean hd = values[i & 1];
			boolean row = values[i >> 1 & 1];
			boolean perPixel = values[i >> 2 & 1];
			boolean expected = hd && row && perPixel;
			assertEquals(expected, HdEnvironmentLight.requested(hd, row, perPixel),
				"environment(" + hd + "," + row + "," + perPixel + ")");
		}
	}

	@Test
	@DisplayName("the live gate follows the saved rows")
	void requested_shouldFollowTheSettings() {
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 1);
		HdSettings.set(HdFeature.ENVIRONMENT_LIGHTING, 0);
		boolean off = HdEnvironmentLight.requested();

		HdSettings.set(HdFeature.ENVIRONMENT_LIGHTING, 1);
		boolean on = HdEnvironmentLight.requested();

		HdSettings.set(HdFeature.PER_PIXEL_LIGHTING, 0);
		boolean withoutProgram = HdEnvironmentLight.requested();

		assertAll(
			() -> assertFalse(off, "the row's own switch is off"),
			() -> assertTrue(on, "all three conditions hold"),
			() -> assertFalse(withoutProgram, "no per-pixel program, no term")
		);
	}

	@Test
	@DisplayName("there is no environment when the map has not set one")
	void of_shouldBeOffWithoutAnEnvironment() {
		assertAll(
			() -> assertSame(HdEnvironmentLight.OFF, HdEnvironmentLight.of(0xFFFFFF, 0.0F, 0.0F, 1.0F, 0.0F),
				"zero ambient is no light"),
			() -> assertSame(HdEnvironmentLight.OFF, HdEnvironmentLight.of(0, 0.2F, 0.0F, 1.0F, 0.0F),
				"a black sky is the toolkit's unset value, not black light"),
			() -> assertTrue(HdEnvironmentLight.OFF.isIdentity()),
			() -> assertFalse(HdEnvironmentLight.OFF.sky())
		);
	}

	@Test
	@DisplayName("the snapshot carries the sky colour at the level ambient, and a unit up axis")
	void of_shouldNormaliseItsInputs() {
		HdEnvironmentLight light = HdEnvironmentLight.of(0xFF8000, 0.25F, 0.0F, 2.0F, 0.0F);

		assertAll(
			() -> assertTrue(light.sky()),
			() -> assertFalse(light.isIdentity()),
			() -> assertEquals(1.0F, light.red(), EPSILON),
			() -> assertEquals(128.0F / 255.0F, light.green(), EPSILON),
			() -> assertEquals(0.0F, light.blue(), EPSILON),
			() -> assertEquals(0.25F, light.ambient(), EPSILON),
			() -> assertEquals(0.0F, light.upX(), EPSILON),
			() -> assertEquals(1.0F, light.upY(), EPSILON),
			() -> assertEquals(0.0F, light.upZ(), EPSILON),
			() -> assertEquals(0.25F, light.ambientRed(), EPSILON)
		);
	}

	@Test
	@DisplayName("a degenerate camera basis falls back to world up instead of a zero vector")
	void of_shouldKeepAUsableUpAxis() {
		HdEnvironmentLight light = HdEnvironmentLight.of(0xFFFFFF, 0.2F, 0.0F, 0.0F, 0.0F);

		assertAll(
			() -> assertTrue(light.sky(), "the colour and level are still an environment"),
			() -> assertEquals(0.0F, light.upX(), EPSILON),
			() -> assertEquals(1.0F, light.upY(), EPSILON),
			() -> assertEquals(0.0F, light.upZ(), EPSILON)
		);
	}

	@Test
	@DisplayName("the sky weight is the clamped dot with up: 1 overhead, 0 at and below the horizon")
	void skyWeight_shouldBeTheClampedDot() {
		HdEnvironmentLight light = HdEnvironmentLight.of(0xFFFFFF, 0.2F, 0.0F, 1.0F, 0.0F);
		float diagonal = (float) (1.0D / Math.sqrt(2.0D));

		assertAll(
			() -> assertEquals(1.0F, light.skyWeight(0.0F, 1.0F, 0.0F), EPSILON, "straight up"),
			() -> assertEquals(1.0F, light.skyWeight(0.0F, 4.0F, 0.0F), EPSILON, "the normal is normalised"),
			() -> assertEquals(diagonal, light.skyWeight(1.0F, 1.0F, 0.0F), EPSILON, "45 degrees"),
			() -> assertEquals(0.0F, light.skyWeight(0.0F, 0.0F, 1.0F), EPSILON, "a wall sees no sky"),
			() -> assertEquals(0.0F, light.skyWeight(0.0F, -1.0F, 0.0F), EPSILON, "nor does a floor"),
			() -> assertEquals(0.0F, light.skyWeight(0.0F, 0.0F, 0.0F), EPSILON, "no normal, no weight"),
			() -> assertEquals(0.0F, HdEnvironmentLight.OFF.skyWeight(0.0F, 1.0F, 0.0F), EPSILON,
				"the identity never contributes")
		);
	}

	@Test
	@DisplayName("the mix is the identity at 0 and the sky at 1, which is what makes Off stock")
	void ambientChannel_shouldMatchTheShadersMix() {
		assertAll(
			() -> assertEquals(0.2F, HdEnvironmentLight.ambientChannel(0.2F, 0.9F, 0.0F), EPSILON,
				"weight 0 keeps the stock ambient"),
			() -> assertEquals(0.9F, HdEnvironmentLight.ambientChannel(0.2F, 0.9F, 1.0F), EPSILON,
				"weight 1 is the sky's ambient"),
			() -> assertEquals(0.55F, HdEnvironmentLight.ambientChannel(0.2F, 0.9F, 0.5F), EPSILON,
				"and the interior is linear")
		);
	}

	@Test
	@DisplayName("equal snapshots compare equal, so an unchanged frame re-uploads nothing")
	void equals_shouldBeByValue() {
		HdEnvironmentLight first = HdEnvironmentLight.of(0xFF8000, 0.25F, 0.0F, 1.0F, 0.0F);
		HdEnvironmentLight same = HdEnvironmentLight.of(0xFF8000, 0.25F, 0.0F, 1.0F, 0.0F);
		HdEnvironmentLight recoloured = HdEnvironmentLight.of(0x00FF00, 0.25F, 0.0F, 1.0F, 0.0F);

		assertAll(
			() -> assertEquals(first, same),
			() -> assertEquals(first.hashCode(), same.hashCode()),
			() -> assertNotEquals(first, recoloured)
		);
	}
}
