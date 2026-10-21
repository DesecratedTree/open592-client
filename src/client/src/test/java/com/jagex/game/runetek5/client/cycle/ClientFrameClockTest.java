package com.jagex.game.runetek5.client.cycle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;

@DisplayName("ClientFrameClock published frame clock")
class ClientFrameClockTest {

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

	/** A stock cycle manager stand-in for the boot window. */
	private static final class FakeCycleManager extends Class36 {

		private final long nanos;

		FakeCycleManager(long nanos) {
			this.nanos = nanos;
		}

		@Override
		public void method2248() {
		}

		@Override
		public long method2252() {
			return this.nanos;
		}

		@Override
		public int method2253(int tickMs) {
			return 1;
		}
	}

	private FakeClock clock;

	private FramePacer pacer;

	@BeforeEach
	void setUp() {
		this.clock = new FakeClock();
		this.pacer = new FramePacer(this.clock);
		// Each test starts with no published clock, as on a fresh boot.
		Class4_Sub12_Sub1.aClass36_1 = null;
	}

	@Test
	@DisplayName("install publishes the pacer as the client clock")
	void install_shouldPublishThePacer() {
		ClientFrameClock.install(this.pacer);

		assertSame(this.pacer, Class4_Sub12_Sub1.aClass36_1);
		assertEquals(this.clock.nanos(), Class4_Sub12_Sub1.aClass36_1.method2252());
	}

	@Test
	@DisplayName("install starts the pacer clock, so a stale deadline is never published")
	void install_shouldResetThePacer() {
		this.clock.advanceMillis(5000);

		ClientFrameClock.install(this.pacer);

		assertEquals(0, this.pacer.ticksDue(20), "the first frame after install owes no ticks");
	}

	@Test
	@DisplayName("the startup window installs a stock cycle manager, not the pacer")
	void installDuringStartup_shouldPublishTheSuppliedClock() {
		Class36 startupClock = new FakeCycleManager(42L);

		ClientFrameClock.installDuringStartup(startupClock);

		assertSame(startupClock, Class4_Sub12_Sub1.aClass36_1);
		assertEquals(42L, Class4_Sub12_Sub1.aClass36_1.method2252());
	}

	@Test
	@DisplayName("a late startup install cannot replace the pacer")
	void installDuringStartup_shouldRefuse_whenThePacerOwnsTheClock() {
		ClientFrameClock.install(this.pacer);
		Class36 lateStartupClock = new FakeCycleManager(-1L);

		ClientFrameClock.installDuringStartup(lateStartupClock);

		// A login-time retry must not swap a clock the loop never advances.
		assertSame(this.pacer, Class4_Sub12_Sub1.aClass36_1);
	}
}
