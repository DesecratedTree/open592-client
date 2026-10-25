package com.jagex.game.runetek5.client.hd.sky;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;

/**
 * Whether the sky is drawn, and how fast its clock turns.
 *
 * <p>Two rows meet here and their precedence is deliberate: <b>Sky is the
 * draw</b> - with it off nothing is issued to GL at all, and the background is
 * the one the stock renderer produced. <b>Day and night is the clock</b> - it
 * recolours a sky that is being drawn <em>and</em> scales the stock light the
 * world is drawn with (see {@link HdDayNight}), so it is not confined to the
 * dome and changes the scene with Sky off. Sky on with the clock off is a
 * legitimate state: the dome is lit by the sun the scene's lighting already
 * holds, and it never changes with time.
 *
 * <p>The day length is a property of the row, not of the pass: the labels are
 * "Clock", "10 min", "30 min" and "60 min", where the numbers are how long one
 * full turn takes and "Clock" is a real 24-hour day. Off has no cycle at all,
 * so the phase freezes where it is rather than snapping to midnight.
 *
 * <p>Pure functions over stored state, like {@code HdWaterGate},
 * {@code HdPostGate} and {@code SunShadowGate}: the truth table is testable
 * without a GL context. {@link #dayNightRequested(boolean, boolean)} therefore
 * does not mention the Sky row: the clock has its own effect on the scene.
 */
public final class SkyGate {

	/** The "Clock" row: one full day per real day. */
	public static final int CLOCK_MINUTES = 24 * 60;

	private SkyGate() {
	}

	/** True when 592HD itself is on. */
	public static boolean enabled() {
		return HdSettings.isEnabled();
	}

	/** True when the Sky row may draw this frame. */
	public static boolean skyRequested() {
		return skyRequested(HdSettings.isEnabled(), HdSettings.value(HdFeature.SKY) != 0);
	}

	/** The one predicate; every caller delegates here. */
	public static boolean skyRequested(boolean hdEnabled, boolean skyRow) {
		return hdEnabled && skyRow;
	}

	/** True when the Day and night row is on, so the clock is allowed to turn. */
	public static boolean dayNightRequested() {
		return dayNightRequested(HdSettings.isEnabled(), HdSettings.value(HdFeature.DAY_NIGHT) != 0);
	}

	/**
	 * The one predicate. The clock may turn with the Sky row off - it paints
	 * nothing then, and a player who switches the dome on later sees a sky that
	 * has been keeping time rather than one that snaps to midnight.
	 */
	public static boolean dayNightRequested(boolean hdEnabled, boolean dayNightRow) {
		return hdEnabled && dayNightRow;
	}

	/**
	 * Length of one full day in real minutes, or 0 when the clock is stopped.
	 *
	 * <p>0 covers three cases that behave identically: 592HD off, the row off,
	 * and an out-of-range stored value. {@link #cycleMillis()} is the same
	 * number in the unit {@link HdSkyTime} advances in.
	 */
	public static int cycleMinutes() {
		if (!dayNightRequested()) {
			return 0;
		}
		return cycleMinutes(HdSettings.value(HdFeature.DAY_NIGHT));
	}

	/** The row's stored value to minutes; anything unknown is a stopped clock. */
	public static int cycleMinutes(int rowValue) {
		switch (rowValue) {
			case 1:
				return CLOCK_MINUTES;
			case 2:
				return 10;
			case 3:
				return 30;
			case 4:
				return 60;
			default:
				return 0;
		}
	}

	/** One full day in milliseconds, or 0 when the clock is stopped. */
	public static double cycleMillis() {
		return cycleMillis(cycleMinutes());
	}

	/** The same, for a day length already resolved to minutes. */
	public static double cycleMillis(int minutes) {
		return minutes <= 0 ? 0.0D : minutes * 60000.0D;
	}
}
