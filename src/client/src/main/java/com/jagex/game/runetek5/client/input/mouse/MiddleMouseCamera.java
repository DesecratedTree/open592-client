package com.jagex.game.runetek5.client.input.mouse;

import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.deob.Static368;
import com.jagex.game.runetek5.preference.Preference;

public final class MiddleMouseCamera {

	private static final float SENSITIVITY = 6.0F;
	private static final float MIN_PITCH = 1024.0F;
	private static final float MAX_PITCH = 3072.0F;
	private static final float YAW_PERIOD = 16384.0F;

	private int lastX = -1;
	private int lastY = -1;

	public void update(Preference mouse) {
		if (!mouse.method2237()) {
			this.lastX = -1;
			this.lastY = -1;
			return;
		}
		int x = mouse.method2234();
		int y = mouse.getDefaultValue();
		if (this.lastX != -1) {
			int deltaX = x - this.lastX;
			int deltaY = y - this.lastY;
			if (deltaX != 0) {
				Static368.aFloat192 -= (float) deltaX * SENSITIVITY;
			}
			if (deltaY != 0) {
				Class16_Sub2.aFloat142 += (float) deltaY * SENSITIVITY;
			}
			if (deltaX != 0 || deltaY != 0) {
				this.clampAngles();
			}
		}
		this.lastX = x;
		this.lastY = y;
	}

	private void clampAngles() {
		if (Class16_Sub2.aFloat142 < MIN_PITCH) {
			Class16_Sub2.aFloat142 = MIN_PITCH;
		} else if (Class16_Sub2.aFloat142 > MAX_PITCH) {
			Class16_Sub2.aFloat142 = MAX_PITCH;
		}
		while (Static368.aFloat192 >= YAW_PERIOD) {
			Static368.aFloat192 -= YAW_PERIOD;
		}
		while (Static368.aFloat192 < 0.0F) {
			Static368.aFloat192 += YAW_PERIOD;
		}
	}
}
