package com.jagex.game.runetek5.client.hd.sky;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * What the hour is allowed to do to the world's light, and what it must leave
 * alone.
 *
 * <p>The rule being pinned: the clock owns intensity and colour - sun, fill,
 * ambient, fog, shadow strength - and dims all five together, so the sky and the
 * world cannot disagree about what time it is. Off is exactly 1.0 for every
 * factor, which is what keeps the stock frame bit-identical.
 */
@DisplayName("592HD day and night")
class HdDayNightTest {

	/** The stock sun sits well above the horizon. */
	private static final float UP = 0.6F;

	@Test
	@DisplayName("off is the identity, exactly - not nearly")
	void factors_shouldBeExactlyOneWhenOff() {
		HdDayNight.Factors off = HdDayNight.factors(false, true, -1.0F, 0.0F);
		HdDayNight.Factors rowOff = HdDayNight.factors(true, false, -1.0F, 0.0F);
		assertAll(
			() -> assertTrue(off.isIdentity()),
			() -> assertTrue(rowOff.isIdentity()),
			() -> assertEquals(HdDayNight.IDENTITY, off),
			() -> assertEquals(1.0F, off.sun()),
			() -> assertEquals(1.0F, off.ambient()),
			() -> assertEquals(1.0F, off.fog()),
			() -> assertEquals(1.0F, off.shadow())
		);
	}

	@Test
	@DisplayName("scaling by 1.0 returns the colour unchanged, channel for channel")
	void scale_shouldBeExactAtOne() {
		int[] colours = { 0x000000, 0xFFFFFF, 0x336699, 0xFFCC88, 0x010203 };
		for (int colour : colours) {
			assertEquals(colour, HdDayNight.scale(colour, 1.0F), Integer.toHexString(colour));
			assertEquals(colour, HdDayNight.IDENTITY.sunColour(colour));
			assertEquals(colour, HdDayNight.IDENTITY.fogColour(colour));
		}
	}

	@Test
	@DisplayName("noon is the identity, so a full day is today's frame")
	void noon_shouldBeTheIdentity() {
		HdDayNight.Factors noon = HdDayNight.factors(true, true, UP, 0.5F);
		assertAll(
			() -> assertEquals(1.0F, noon.daylight(), 1.0e-6F),
			() -> assertTrue(noon.isIdentity()),
			() -> assertEquals(0x336699, noon.fogColour(0x336699))
		);
	}

	@Test
	@DisplayName("midnight is dark in every factor, and never darker than its level")
	void midnight_shouldDimEveryFactorToItsLevel() {
		HdDayNight.Factors night = HdDayNight.factors(false, false, 0.0F, 0.0F);
		HdDayNight.Factors midnight = HdDayNight.factors(true, true, UP, 0.0F);
		assertAll(
			() -> assertEquals(0.0F, midnight.daylight(), 1.0e-6F),
			() -> assertEquals(HdDayNight.SUN_NIGHT_LEVEL, midnight.sun(), 1.0e-6F),
			() -> assertEquals(HdDayNight.FILL_NIGHT_LEVEL, midnight.fill(), 1.0e-6F),
			() -> assertEquals(HdDayNight.AMBIENT_NIGHT_LEVEL, midnight.ambient(), 1.0e-6F),
			() -> assertEquals(HdDayNight.FOG_NIGHT_LEVEL, midnight.fog(), 1.0e-6F),
			() -> assertEquals(HdDayNight.SHADOW_NIGHT_LEVEL, midnight.shadow(), 1.0e-6F),
			() -> assertFalse(midnight.isIdentity())
		);
		// Every factor is a lerp from its night level up to 1, so nothing ever
		// leaves [level, 1] and nothing ever brightens above the stock light.
		HdDayNight.Factors dusk = HdDayNight.factors(0.4F);
		for (float value : new float[] { dusk.sun(), dusk.fill(), dusk.ambient(), dusk.fog(), dusk.shadow() }) {
			assertTrue(value >= HdDayNight.SUN_NIGHT_LEVEL && value <= 1.0F, "factor " + value + " out of band");
		}
	}

	@Test
	@DisplayName("factors rise monotonically with daylight and reach 1 at full day")
	void factors_shouldRiseWithDaylight() {
		HdDayNight.Factors previous = HdDayNight.factors(0.0F);
		for (int step = 1; step <= 20; step++) {
			float daylight = step / 20.0F;
			HdDayNight.Factors current = HdDayNight.factors(daylight);
			assertTrue(current.sun() >= previous.sun(), "sun lapsed at " + daylight);
			assertTrue(current.ambient() >= previous.ambient(), "ambient lapsed at " + daylight);
			assertTrue(current.fog() >= previous.fog(), "fog lapsed at " + daylight);
			assertTrue(current.shadow() >= previous.shadow(), "shadow lapsed at " + daylight);
			previous = current;
		}
		assertTrue(previous.isIdentity());
	}

	@Test
	@DisplayName("the clock height is noon 1, midnight 0, dawn and dusk 0")
	void clockDaylight_shouldBeAnArc() {
		assertAll(
			() -> assertEquals(1.0F, HdDayNight.clockDaylight(0.5F), 1.0e-6F, "noon"),
			() -> assertEquals(0.0F, HdDayNight.clockDaylight(0.0F), 1.0e-6F, "midnight"),
			() -> assertEquals(0.0F, HdDayNight.clockDaylight(0.25F), 1.0e-6F, "sunrise"),
			() -> assertEquals(0.0F, HdDayNight.clockDaylight(0.75F), 1.0e-6F, "sunset"),
			() -> assertTrue(HdDayNight.clockDaylight(0.28F) > HdDayNight.clockDaylight(0.26F),
				"morning gets brighter"),
			() -> assertEquals(1.0F, HdDayNight.clockDaylight(0.30F), 1.0e-6F, "full day soon after dawn")
		);
	}

	@Test
	@DisplayName("the evening darkens without ever brightening again, all the way to midnight")
	void clockDaylight_shouldFallMonotonicallyIntoTheNight() {
		float previous = HdDayNight.clockDaylight(0.5F);
		for (int step = 1; step <= 50; step++) {
			float phase = 0.5F + step * 0.01F;
			float current = HdDayNight.clockDaylight(phase);
			assertTrue(current <= previous + 1.0e-6F, "the light brightened at phase " + phase);
			previous = current;
		}
		assertEquals(0.0F, previous, 1.0e-6F, "and the evening reaches midnight");
	}

	@Test
	@DisplayName("scaling is per channel: no channel bleeds into its neighbour")
	void scale_shouldBePerChannel() {
		assertAll(
			() -> assertEquals(0x804000, HdDayNight.scale(0xFF8000, 0.5F)),
			() -> assertEquals(0x000080, HdDayNight.scale(0x0000FF, 0.5F), "half of 255 rounds up"),
			() -> assertEquals(0xFF0000, HdDayNight.scale(0xFF0000, 1.0F)),
			() -> assertEquals(0x008000, HdDayNight.scale(0x008000, 1.0F))
		);
	}

	@Test
	@DisplayName("the lighting's sun is daylight on its own, the clock only multiplies")
	void daylight_shouldRespectBothTheSunAndTheClock() {
		assertAll(
			() -> assertEquals(1.0F, HdDayNight.daylight(UP, 0.5F, true), 1.0e-6F),
			() -> assertEquals(0.0F, HdDayNight.daylight(UP, 0.0F, true), 1.0e-6F),
			() -> assertEquals(1.0F, HdDayNight.daylight(UP, 0.5F, false), 1.0e-6F),
			() -> assertEquals(1.0F, HdDayNight.daylight(UP, 0.0F, false), 1.0e-6F,
				"with the row off the clock is not consulted at all"),
			() -> assertEquals(0.0F, HdDayNight.daylight(-0.5F, 0.5F, false), 1.0e-6F,
				"a sun below the horizon is never daylight")
		);
	}

	@Test
	@DisplayName("the phase is invisible while the row is off")
	void rowOff_shouldIgnoreThePhase() {
		HdDayNight.Factors reference = HdDayNight.factors(true, false, UP, 0.5F);
		for (float phase = 0.0F; phase < 1.0F; phase += 0.1F) {
			assertEquals(reference, HdDayNight.factors(true, false, UP, phase), "phase " + phase);
		}
	}

	@Test
	@DisplayName("every colour stays in range and never brightens the stock light")
	void colours_shouldStayInRange() {
		for (float phase = 0.0F; phase < 1.0F; phase += 0.05F) {
			for (float elevation = -1.0F; elevation <= 1.0F; elevation += 0.1F) {
				HdDayNight.Factors factors = HdDayNight.factors(true, true, elevation, phase);
				assertTrue(factors.sun() >= 0.0F && factors.sun() <= 1.0F);
				assertTrue(factors.fog() >= 0.0F && factors.fog() <= 1.0F);
				assertTrue(factors.shadow() >= 0.0F && factors.shadow() <= 1.0F);
				int fog = factors.fogColour(0xFFFFFF);
				assertTrue(fog >= 0 && fog <= 0xFFFFFF);
				assertTrue(((fog >> 16) & 0xFF) <= 0xFF);
			}
		}
	}

	@Test
	@DisplayName("a black colour stays black and a full-scale one keeps its levels")
	void scale_shouldNotOverflow() {
		assertAll(
			() -> assertEquals(0x000000, HdDayNight.scale(0x000000, 0.5F)),
			() -> assertEquals(0x010101, HdDayNight.scale(0x020202, 0.5F)),
			() -> assertEquals(0x808080, HdDayNight.scale(0xFFFFFF, 0.5F)),
			() -> assertEquals(0x000000, HdDayNight.scale(0xFFFFFF, -1.0F)),
			() -> assertEquals(0xFFFFFF, HdDayNight.scale(0xFFFFFF, 2.0F))
		);
	}

	@Test
	@DisplayName("the shadow fades toward lit, never toward black")
	void shadow_shouldFadeTowardLit() {
		// The shader mixes 1 - strength * (1 - sample): a full-strength sun keeps
		// the sample, and a zero-strength one erases the shadow entirely.
		assertEquals(1.0F, HdDayNight.factors(1.0F).shadow());
		assertTrue(HdDayNight.factors(0.0F).shadow() > 0.0F, "night keeps some shadow");
		assertNotEquals(0.0F, HdDayNight.factors(0.0F).shadow());
	}

	@Test
	@DisplayName("smoothstep is GLSL's, edges included")
	void smoothstep_shouldMatchGlsl() {
		assertAll(
			() -> assertEquals(0.0F, HdDayNight.smoothstep(0.0F, 1.0F, -0.5F)),
			() -> assertEquals(0.0F, HdDayNight.smoothstep(0.0F, 1.0F, 0.0F)),
			() -> assertEquals(0.5F, HdDayNight.smoothstep(0.0F, 1.0F, 0.5F), 1.0e-6F),
			() -> assertEquals(1.0F, HdDayNight.smoothstep(0.0F, 1.0F, 1.0F)),
			() -> assertEquals(1.0F, HdDayNight.smoothstep(0.0F, 1.0F, 2.0F)),
			() -> assertEquals(0.0F, HdDayNight.smoothstep(0.0F, 1.0F, -2.0F))
		);
	}
}
