package com.jagex.game.runetek5.client.hd.sky;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Where every sky colour comes from, and what the clock may and may not do to
 * them.
 *
 * <p>The rule being pinned: the horizon is the fog colour and the disc is the
 * sun's colour - the two colours the scene already lit itself with - moved by
 * one daylight level; the clock moves that level only when the Day and night
 * row is on, and never brightens a sun the lighting has put below the horizon.
 */
@DisplayName("592HD sky colours")
class HdSkyColoursTest {

	private static final int SUN = 0xFFFFFF;

	private static final int FOG = 0x336699;

	/** The stock sun sits well above the horizon. */
	private static final float UP = 0.6F;

	@Test
	@DisplayName("no row, no sky")
	void resolve_shouldBeOffWhenTheRowIsOff() {
		assertAll(
			() -> assertFalse(HdSkyColours.resolve(true, false, true, UP, 0.5F, SUN, FOG).sky()),
			() -> assertFalse(HdSkyColours.resolve(false, true, true, UP, 0.5F, SUN, FOG).sky()),
			() -> assertEquals(HdSkyColours.OFF, HdSkyColours.resolve(true, false, true, UP, 0.5F, SUN, FOG)),
			() -> assertEquals(0.0F, HdSkyColours.resolve(false, true, true, UP, 0.5F, SUN, FOG).daylight())
		);
	}

	@Test
	@DisplayName("in full day the horizon is the fog colour, channel for channel")
	void horizon_shouldBeTheFogColourAtFullDay() {
		HdSkyColours colours = HdSkyColours.resolve(true, true, false, UP, 0.5F, SUN, FOG);
		assertAll(
			() -> assertEquals(1.0F, colours.daylight(), 1.0e-6F),
			() -> assertEquals(0x33 / 255.0F, colours.horizon()[0], 1.0e-6F),
			() -> assertEquals(0x66 / 255.0F, colours.horizon()[1], 1.0e-6F),
			() -> assertEquals(0x99 / 255.0F, colours.horizon()[2], 1.0e-6F)
		);
	}

	@Test
	@DisplayName("the horizon is whatever fog colour the world was handed, dim or not")
	void horizon_shouldBeExactlyTheFogColourItWasGiven() {
		// The level is HdDayNight's and the toolkit applies it before this call,
		// so the dome must not compute a second one: a dimmed fog colour has to
		// arrive dimmed and leave untouched.
		int midnightFog = HdDayNight.factors(0.0F).fogColour(FOG);
		HdSkyColours night = HdSkyColours.resolve(true, true, true, UP, 0.0F, SUN, midnightFog);
		HdSkyColours day = HdSkyColours.resolve(true, true, true, UP, 0.5F, SUN, FOG);
		assertAll(
			() -> assertEquals(0.0F, night.daylight(), 1.0e-6F),
			() -> assertEquals(((midnightFog >> 16) & 0xFF) / 255.0F, night.horizon()[0], 1.0e-6F),
			() -> assertEquals(((midnightFog >> 8) & 0xFF) / 255.0F, night.horizon()[1], 1.0e-6F),
			() -> assertEquals((midnightFog & 0xFF) / 255.0F, night.horizon()[2], 1.0e-6F),
			() -> assertEquals(((FOG >> 16) & 0xFF) / 255.0F, day.horizon()[0], 1.0e-6F)
		);
	}

	@Test
	@DisplayName("the zenith is the horizon plus daylight's blue shift")
	void zenith_shouldBeTheHorizonShiftedBlue() {
		int darkFog = 0x101010;
		HdSkyColours day = HdSkyColours.resolve(true, true, false, UP, 0.5F, SUN, darkFog);
		HdSkyColours night = HdSkyColours.resolve(true, true, true, UP, 0.0F, SUN, darkFog);
		assertAll(
			() -> assertEquals(day.horizon()[0] + HdSkyColours.ZENITH_SHIFT_R, day.zenith()[0], 1.0e-6F),
			() -> assertEquals(day.horizon()[1] + HdSkyColours.ZENITH_SHIFT_G, day.zenith()[1], 1.0e-6F),
			() -> assertEquals(day.horizon()[2] + HdSkyColours.ZENITH_SHIFT_B, day.zenith()[2], 1.0e-6F),
			() -> assertTrue(day.zenith()[2] > day.horizon()[2], "the day's zenith is bluer than its horizon"),
			() -> assertEquals(night.horizon()[0], night.zenith()[0], 1.0e-6F,
				"with no daylight there is no blue shift"),
			() -> assertEquals(night.horizon()[2], night.zenith()[2], 1.0e-6F)
		);
	}

	@Test
	@DisplayName("the disc is the sun's own colour, dimmed at night")
	void sunColour_shouldComeFromTheLightsSun() {
		int warmSun = 0xFFCC88;
		HdSkyColours day = HdSkyColours.resolve(true, true, false, UP, 0.5F, warmSun, FOG);
		int midnightSun = HdDayNight.factors(0.0F).sunColour(warmSun);
		HdSkyColours night = HdSkyColours.resolve(true, true, true, UP, 0.0F, midnightSun, FOG);
		assertAll(
			() -> assertEquals(0xFF / 255.0F, day.sun()[0], 1.0e-6F),
			() -> assertEquals(0xCC / 255.0F, day.sun()[1], 1.0e-6F),
			() -> assertEquals(0x88 / 255.0F, day.sun()[2], 1.0e-6F),
			() -> assertEquals(((midnightSun >> 16) & 0xFF) / 255.0F, night.sun()[0], 1.0e-6F,
				"the disc is the light's own colour, which the toolkit already dimmed")
		);
	}

	@Test
	@DisplayName("the below-horizon fill sits between the horizon and the sun")
	void ambient_shouldSitBetweenHorizonAndSun() {
		HdSkyColours day = HdSkyColours.resolve(true, true, false, UP, 0.5F, SUN, FOG);
		float mix = HdSkyColours.AMBIENT_SUN_MIX;
		for (int channel = 0; channel < 3; channel++) {
			float expected = day.horizon()[channel] + (day.sun()[channel] - day.horizon()[channel]) * mix;
			assertEquals(HdSkyColours.clamp01(expected), day.ambient()[channel], 1.0e-6F);
		}
		HdSkyColours night = HdSkyColours.resolve(true, true, true, UP, 0.0F, SUN, FOG);
		assertEquals(night.horizon()[2], night.ambient()[2], 1.0e-6F,
			"no daylight means no lean toward the sun");
	}

	@Test
	@DisplayName("with the clock off the phase is invisible")
	void dayNightOff_shouldIgnoreThePhase() {
		for (float phase : new float[] { 0.0F, 0.25F, 0.5F, 0.75F, 0.99F }) {
			assertEquals(HdSkyColours.resolve(true, true, false, UP, 0.5F, SUN, FOG),
				HdSkyColours.resolve(true, true, false, UP, phase, SUN, FOG),
				"phase " + phase + " changed a sky with no clock");
		}
	}

	@Test
	@DisplayName("with the clock on the phase is the day")
	void dayNightOn_shouldFollowTheClock() {
		HdSkyColours noon = HdSkyColours.resolve(true, true, true, UP, 0.5F, SUN, FOG);
		// The toolkit dims the hand-off colours; the dome never computes its own
		// level, so the test dims them the same way the toolkit would.
		int nightFog = HdDayNight.factors(0.0F).fogColour(FOG);
		int nightSun = HdDayNight.factors(0.0F).sunColour(SUN);
		HdSkyColours midnight = HdSkyColours.resolve(true, true, true, UP, 0.0F, nightSun, nightFog);
		assertAll(
			() -> assertEquals(1.0F, noon.daylight(), 1.0e-6F),
			() -> assertEquals(0.0F, midnight.daylight(), 1.0e-6F),
			() -> assertNotEquals(noon, midnight),
			() -> assertTrue(noon.zenith()[2] > midnight.zenith()[2], "night is darker"),
			() -> assertTrue(noon.sun()[0] > midnight.sun()[0], "and so is the disc")
		);
	}

	@Test
	@DisplayName("a sun the lighting put below the horizon is not daylight, at any hour")
	void sunBelowTheHorizon_shouldOverrideTheClock() {
		assertAll(
			() -> assertEquals(0.0F, HdSkyColours.resolve(true, true, true, -0.5F, 0.5F, SUN, FOG).daylight(),
				1.0e-6F),
			() -> assertEquals(0.0F, HdDayNight.sunElevationDaylight(-0.5F), 1.0e-6F),
			() -> assertEquals(1.0F, HdDayNight.sunElevationDaylight(0.6F), 1.0e-6F)
		);
	}

	@Test
	@DisplayName("every colour stays in range for every phase and every elevation")
	void colours_shouldStayInRange() {
		for (float phase = 0.0F; phase < 1.0F; phase += 0.05F) {
			for (float elevation = -1.0F; elevation <= 1.0F; elevation += 0.1F) {
				HdSkyColours colours = HdSkyColours.resolve(true, true, true, elevation, phase, 0xFFFFFF, 0x808080);
				float[][] terms = { colours.zenith(), colours.horizon(), colours.ambient(), colours.sun() };
				for (float[] term : terms) {
					for (float channel : term) {
						assertTrue(channel >= 0.0F && channel <= 1.0F, "channel " + channel
							+ " at phase " + phase + " elevation " + elevation);
					}
				}
				assertTrue(colours.daylight() >= 0.0F && colours.daylight() <= 1.0F);
			}
		}
	}

	@Test
	@DisplayName("a white fog at full day saturates instead of overflowing")
	void brightFog_shouldClamp() {
		HdSkyColours colours = HdSkyColours.resolve(true, true, false, UP, 0.5F, SUN, 0xFFFFFF);
		assertAll(
			() -> assertEquals(1.0F, colours.horizon()[0], 1.0e-6F),
			() -> assertEquals(1.0F, colours.zenith()[2], 1.0e-6F)
		);
	}

	@Test
	@DisplayName("the dome's smoothstep is GLSL's, edges included")
	void smoothstep_shouldMatchGlsl() {
		assertAll(
			() -> assertEquals(0.0F, HdSkyColours.smoothstep(0.0F, 1.0F, -0.5F)),
			() -> assertEquals(0.0F, HdSkyColours.smoothstep(0.0F, 1.0F, 0.0F)),
			() -> assertEquals(0.5F, HdSkyColours.smoothstep(0.0F, 1.0F, 0.5F), 1.0e-6F),
			() -> assertEquals(1.0F, HdSkyColours.smoothstep(0.0F, 1.0F, 1.0F)),
			() -> assertEquals(1.0F, HdSkyColours.smoothstep(0.0F, 1.0F, 2.0F)),
			() -> assertEquals(0.0F, HdSkyColours.smoothstep(0.0F, 1.0F, -2.0F))
		);
	}
}
