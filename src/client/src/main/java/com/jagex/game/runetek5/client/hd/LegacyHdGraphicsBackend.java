package com.jagex.game.runetek5.client.hd;

import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.HashTable;
import com.jagex.game.runetek5.client.camera.CameraPreferences;
import com.jagex.game.runetek5.client.camera.FogDistance;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub2;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.entity.PathingEntity;

/**
 * Writes 592HD's READY rows into the stock 592 graphics settings.
 *
 * <p>Every target here is an existing field or console command, so 592HD and
 * the stock Graphics Options script can never drift apart: both end up driving
 * the same {@code Class50_Sub1} flags. The re-application calls after each
 * write are the same ones the stock console commands make.
 *
 * <p>{@link #canApply()} is false until the client has created its settings
 * object, which is also true in unit tests; that lets the pane run headless
 * without touching GL state.
 */
public final class LegacyHdGraphicsBackend implements HdGraphicsBackend {

	@Override
	public boolean canApply() {
		return TextureOpBrightness.aClass50_Sub1_1 != null;
	}

	@Override
	public void apply(HdFeature feature, int value) {
		if (feature == null || !canApply()) {
			return;
		}
		Class50_Sub1 settings = TextureOpBrightness.aClass50_Sub1_1;
		switch (feature.target()) {
		case TEXTURES:
			settings.aBoolean307 = value != 0;
			Class16_Sub4_Sub2.method2149();
			PathingEntity.method4744();
			break;
		case GROUND_BLENDING:
			settings.aBoolean289 = value != 0;
			PathingEntity.method4744();
			break;
		case SHADOWS:
			settings.method2852(Packet.anInt2973, value);
			PathingEntity.method4744();
			break;
		case BLOOM:
			HashTable.method2673(value);
			break;
		case ANTI_ALIASING:
			// Read when the toolkit is created; the pane labels it restart-only.
			settings.anInt3440 = value;
			break;
		case RENDER_DISTANCE:
			FogDistance.setTarget(HdRenderDistance.percent(value));
			CameraPreferences.save();
			break;
		default:
			// RENDERER: read live by the HD pipeline, not by stock state.
			break;
		}
	}

	@Override
	public void flush() {
		if (!canApply()) {
			return;
		}
		Class50_Sub1 settings = TextureOpBrightness.aClass50_Sub1_1;
		if (Class126.aClass66_4 != null) {
			settings.method2856(Class126.aClass66_4);
		}
	}
}
