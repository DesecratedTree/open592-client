package com.jagex.game.runetek5.client.hd.sky;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The two rows that decide whether a sky exists at all, and how fast its clock
 * turns.
 *
 * <p>The precedence being pinned: 592HD off beats everything, and Sky off
 * means no draw whatever Day and night says. The clock is deliberately not
 * gated by Sky: it also scales the stock light the world is drawn with, so it
 * would still change the scene with the dome off.
 */
@DisplayName("592HD sky gating")
class SkyGateTest {

	@BeforeEach
	void useTemporaryPreferences() throws IOException {
		HdSettings.setDirectoryForTest(Files.createTempDirectory("hd-sky-gate").toFile());
		HdSettings.setEnabled(true, null);
		HdSettings.set(HdFeature.SKY, 1);
		HdSettings.set(HdFeature.DAY_NIGHT, 2);
	}

	@AfterEach
	void resetSettings() {
		HdSettings.resetForTest();
	}

	@Test
	@DisplayName("the Sky row needs 592HD and its own value")
	void skyRequested_shouldBeATruthTable() {
		assertAll(
			() -> assertTrue(SkyGate.skyRequested(true, true)),
			() -> assertFalse(SkyGate.skyRequested(true, false)),
			() -> assertFalse(SkyGate.skyRequested(false, true)),
			() -> assertFalse(SkyGate.skyRequested(false, false))
		);
	}

	@Test
	@DisplayName("the Day and night row needs 592HD and its own value")
	void dayNightRequested_shouldBeATruthTable() {
		assertAll(
			() -> assertTrue(SkyGate.dayNightRequested(true, true)),
			() -> assertFalse(SkyGate.dayNightRequested(true, false)),
			() -> assertFalse(SkyGate.dayNightRequested(false, true)),
			() -> assertFalse(SkyGate.dayNightRequested(false, false))
		);
	}

	@Test
	@DisplayName("Sky off is no draw, even with the clock on")
	void skyOff_shouldVetoTheDraw() {
		HdSettings.set(HdFeature.SKY, 0);
		assertAll(
			() -> assertFalse(SkyGate.skyRequested(), "nothing may be issued to GL"),
			() -> assertTrue(SkyGate.dayNightRequested(), "the clock may still turn; it paints nothing"),
			() -> assertEquals(10, SkyGate.cycleMinutes())
		);
	}

	@Test
	@DisplayName("592HD off stops the clock whatever the rows say")
	void hdOff_shouldStopTheClock() {
		HdSettings.setEnabled(false, null);
		assertAll(
			() -> assertFalse(SkyGate.skyRequested()),
			() -> assertFalse(SkyGate.dayNightRequested()),
			() -> assertEquals(0, SkyGate.cycleMinutes()),
			() -> assertEquals(0.0D, SkyGate.cycleMillis())
		);
	}

	@Test
	@DisplayName("the row's labels are the day lengths")
	void cycleMinutes_shouldMatchTheRowLabels() {
		assertAll(
			() -> assertEquals(0, SkyGate.cycleMinutes(0), "Off"),
			() -> assertEquals(1440, SkyGate.cycleMinutes(1), "Clock"),
			() -> assertEquals(10, SkyGate.cycleMinutes(2), "10 min"),
			() -> assertEquals(30, SkyGate.cycleMinutes(3), "30 min"),
			() -> assertEquals(60, SkyGate.cycleMinutes(4), "60 min"),
			() -> assertEquals(SkyGate.CLOCK_MINUTES, SkyGate.cycleMinutes(1)),
			// An unknown value must not be read as a very short day.
			() -> assertEquals(0, SkyGate.cycleMinutes(5)),
			() -> assertEquals(0, SkyGate.cycleMinutes(-1))
		);
	}

	@Test
	@DisplayName("a 10 minute day is 600000 milliseconds")
	void cycleMillis_shouldBeInMilliseconds() {
		assertAll(
			() -> assertEquals(600000.0D, SkyGate.cycleMillis(), 1.0e-9D),
			// The overload takes minutes, not the row's index.
			() -> assertEquals(1800000.0D, SkyGate.cycleMillis(30), 1.0e-9D),
			() -> assertEquals(3600000.0D, SkyGate.cycleMillis(60), 1.0e-9D),
			() -> assertEquals(0.0D, SkyGate.cycleMillis(0), 1.0e-9D),
			() -> assertEquals(86400000.0D, SkyGate.cycleMinutes(1) * 60000.0D, 1.0e-9D)
		);
	}

	@Test
	@DisplayName("Off stops the clock rather than resetting it")
	void dayNightOff_shouldStopTheClock() {
		HdSettings.set(HdFeature.DAY_NIGHT, 0);
		assertAll(
			() -> assertFalse(SkyGate.dayNightRequested()),
			() -> assertEquals(0, SkyGate.cycleMinutes()),
			() -> assertTrue(SkyGate.skyRequested(), "the dome is still drawn, lit by the light's sun")
		);
	}
}
