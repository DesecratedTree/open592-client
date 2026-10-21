package com.jagex.game.runetek5.client.cycle;

/**
 * Runs the ticks a frame owes, exactly once each.
 *
 * <p>The loop used to bound this on a shared deob counter
 * ({@code Static411.anInt6741}). The tick path clears that counter part-way
 * through a tick ({@code GlVboArrayBuffer.method3660()}), so on the title,
 * login and loading screens the bound dropped to zero after the first tick and
 * every further owed tick was silently lost. Holding the count in a parameter
 * means nothing a tick does can shorten its own batch.
 *
 * <p>The returned count is what actually ran: it drives the AWT pump and the
 * {@code Tps} readout, so both report work done rather than work owed.
 */
public final class TickBatch {

	private TickBatch() {
	}

	/**
	 * Runs {@code dueTicks} ticks.
	 *
	 * @param dueTicks ticks the pacer says are due; zero or negative runs none
	 * @param tick one game tick
	 * @return ticks actually executed, always {@code max(dueTicks, 0)}
	 */
	public static int run(int dueTicks, Runnable tick) {
		int executed = 0;
		while (executed < dueTicks) {
			tick.run();
			executed++;
		}
		return executed;
	}
}
