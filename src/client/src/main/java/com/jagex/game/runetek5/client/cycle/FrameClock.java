package com.jagex.game.runetek5.client.cycle;

/**
 * Time source for the game loop: monotonic nanoseconds, never behind the last
 * reading.
 *
 * <p>Production reads {@link #SYSTEM}; tests inject their own so every
 * scheduled nanosecond is theirs.
 */
public interface FrameClock {

	/** Production clock: {@link System#nanoTime()}. */
	FrameClock SYSTEM = System::nanoTime;

	long nanos();
}
