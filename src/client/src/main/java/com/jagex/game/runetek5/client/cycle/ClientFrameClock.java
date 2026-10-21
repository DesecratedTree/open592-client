package com.jagex.game.runetek5.client.cycle;

import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;

/**
 * The single owner of the client's published frame clock.
 *
 * <p>{@code Class4_Sub12_Sub1.aClass36_1} backs
 * {@code Class4_Sub1_Sub11.method2607()}, so once the loop starts it must hold
 * the {@link FramePacer}; anything else stops the frame clock and freezes
 * every per-frame presentation value. Both writers live here, so the rule is
 * enforced rather than left to call order.
 */
public final class ClientFrameClock {

	private ClientFrameClock() {
	}

	/**
	 * Resets the pacer's clock and publishes it as the client's frame clock.
	 */
	public static void install(FramePacer pacer) {
		pacer.reset();
		Class4_Sub12_Sub1.aClass36_1 = pacer;
	}

	/**
	 * Installs the stock startup cycle manager for the boot window, and refuses
	 * once the pacer owns the clock.
	 *
	 * <p>{@code GameShell.method877()} is also called from the login handler,
	 * so a startup retry after a failed first attempt would otherwise replace a
	 * clock the loop never advances. The write is simply skipped then.
	 */
	public static void installDuringStartup(Class36 clock) {
		if (!(Class4_Sub12_Sub1.aClass36_1 instanceof FramePacer)) {
			Class4_Sub12_Sub1.aClass36_1 = clock;
		}
	}
}
