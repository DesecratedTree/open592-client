package com.jagex.game.runetek5.client.cycle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TickBatch loop-local tick execution")
class TickBatchTest {

	@Test
	@DisplayName("every due tick runs even when a tick clears a shared counter")
	void run_shouldRunEveryDueTick_whenTickMutatesSharedState() {
		// The regression: the loop used Static411.anInt6741 as its bound and a
		// tick zeroed it, so a frame that owed five ticks ran one.
		int[] sharedCounter = { 5 };
		int[] runs = { 0 };

		int executed = TickBatch.run(5, () -> {
			runs[0]++;
			sharedCounter[0] = 0;
		});

		assertEquals(5, executed);
		assertEquals(5, runs[0]);
		assertEquals(0, sharedCounter[0], "the batch must not read the mutated counter");
	}

	@Test
	@DisplayName("no due ticks runs nothing")
	void run_shouldRunNothing_whenNothingIsDue() {
		int[] runs = { 0 };

		assertEquals(0, TickBatch.run(0, () -> runs[0]++));
		assertEquals(0, TickBatch.run(-3, () -> runs[0]++));
		assertEquals(0, runs[0]);
	}
}
