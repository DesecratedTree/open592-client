package com.jagex.game.runetek5.client.cycle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The pacer is an adapter: tick cadence is covered by {@link TickSchedulerTest}
 * and the ceiling arithmetic by {@link FrameSleeperTest}. These tests pin the
 * wiring between them and the {@code Class36} surface the deob code uses.
 */
@DisplayName("FramePacer Class36 adapter")
class FramePacerTest {

	private static final long MS = 1_000_000L;

	private static final class FakeClock implements FrameClock {

		private long nanos;

		@Override
		public long nanos() {
			return this.nanos;
		}

		void advanceMillis(int millis) {
			this.nanos += (long) millis * MS;
		}
	}

	private FakeClock clock;

	private FramePacer pacer;

	@BeforeEach
	void setUp() {
		this.clock = new FakeClock();
		this.pacer = new FramePacer(this.clock);
		this.pacer.reset();
	}

	@Test
	@DisplayName("the pacer is the client clock, so the frame delta keeps advancing")
	void method2252_shouldAdvanceWithTheClock() {
		long before = this.pacer.method2252();
		this.clock.advanceMillis(25);

		assertEquals(before + 25L * MS, this.pacer.method2252());
		assertEquals(1, this.pacer.method2253(20));
	}

	@Test
	@DisplayName("the title screen's legacy clock reset does not starve ticks")
	void method2248_shouldBeNoOp() {
		for (int frame = 0; frame < 10; frame++) {
			this.pacer.method2248();
			this.clock.advanceMillis(20);
			assertEquals(1, this.pacer.ticksDue(20), "frame " + frame);
		}
	}

	@Test
	@DisplayName("a reset requested from another thread is applied on the loop's next frame")
	void requestReset_shouldDropTheRemainder_onTheNextFrame() {
		this.clock.advanceMillis(45);
		assertEquals(2, this.pacer.ticksDue(20));

		this.pacer.requestReset();
		this.clock.advanceMillis(1);

		assertEquals(0, this.pacer.ticksDue(20), "the requested reset must discard the remainder");
		this.clock.advanceMillis(20);
		assertEquals(1, this.pacer.ticksDue(20), "the clock runs normally afterwards");
	}
}
