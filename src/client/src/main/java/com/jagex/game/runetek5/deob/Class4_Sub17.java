package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.cache.node.impl.VariableRateSoundPacket;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.config.midi.SoundEffect;
import com.jagex.game.runetek5.sound.jagtheora.AreaSoundStream;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.config.sequence.PathingEntitySequence;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.deob.Class186;
import com.jagex.game.runetek5.deob.GameCanvas;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.js5.request.FileWorker;
@OriginalClass("client!fj")
public final class Class4_Sub17 extends Country {

	@OriginalMember(owner = "client!fj", name = "m", descriptor = "I")
	public final int anInt2367;

	@OriginalMember(owner = "client!fj", name = "n", descriptor = "I")
	public final int anInt2368;

	@OriginalMember(owner = "client!fj", name = "l", descriptor = "I")
	public final int anInt2366;

	@OriginalMember(owner = "client!fj", name = "r", descriptor = "I")
	public final int anInt2372;

	@OriginalMember(owner = "client!fj", name = "t", descriptor = "Z")
	public final boolean aBoolean222;

	@OriginalMember(owner = "client!fj", name = "s", descriptor = "I")
	public final int anInt2373;

	static {
		new Class83(null, "Mitglieder können 200 Freunde hinzufügen, freie Spieler nur 100.", null, null);
	}

	@OriginalMember(owner = "client!fj", name = "<init>", descriptor = "(IIIIIZ)V")
	public Class4_Sub17(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) boolean arg5) {
		this.anInt2367 = arg4;
		this.anInt2368 = arg1;
		this.anInt2366 = arg3;
		this.anInt2372 = arg0;
		this.aBoolean222 = arg5;
		this.anInt2373 = arg2;
	}

@OriginalMember(owner = "client!fj", name = "o", descriptor = "I")
	public static int anInt2369 = 1;
@OriginalMember(owner = "client!fj", name = "u", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_27 = new Class215(73, 2);
@OriginalMember(owner = "client!fj", name = "v", descriptor = "S")
	public static short aShort18 = 32767;
@OriginalMember(owner = "client!fj", name = "w", descriptor = "I")
	public static int anInt2374 = 0;
@OriginalMember(owner = "client!fj", name = "a", descriptor = "(Lclient!ba;III[Z)V")
	public static void method2015(@OriginalArg(0) Shadow arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) boolean[] arg4) {
		if (Class16_Sub1_Sub6.aClass65Array4 == FileWorker.aClass65Array3) {
			return;
		}
		@Pc(9) int local9 = Class4_Sub6_Sub4.aClass65Array1[arg1].a(arg2, arg3);
		for (@Pc(11) int local11 = 0; local11 <= arg1; local11++) {
			if (arg4 == null || arg4[local11]) {
				@Pc(22) Class65 local22 = Class4_Sub6_Sub4.aClass65Array1[local11];
				if (local22 != null) {
					local22.p(arg0, arg2, local9 - local22.a(arg2, arg3), arg3, 0, false);
				}
			}
		}
	}
@OriginalMember(owner = "client!fj", name = "a", descriptor = "(Lclient!uu;I)V")
	public static void method2016(@OriginalArg(0) com.jagex.game.runetek5.config.rsinterface.Component arg0) {
		@Pc(11) com.jagex.game.runetek5.config.rsinterface.Component local11 = Class10.method125(arg0);
		@Pc(17) int local17;
		@Pc(15) int local15;
		if (local11 == null) {
			local15 = EffectiveVertex.anInt5346;
			local17 = GpuPointLight.anInt2881;
		} else {
			local17 = local11.anInt6833;
			local15 = local11.anInt6805;
		}
		Ground.method5658(false, local17, arg0, local15);
		Class186.method4160(arg0, local17, local15);
	}
@OriginalMember(owner = "client!fj", name = "a", descriptor = "(ZIIILclient!bn;I)V")
	public static void method2017(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) ReflectionCheck arg3, @OriginalArg(5) int arg4) {
		if (arg3.anInt616 == -1 && arg3.anIntArray37 == null) {
			return;
		}
		@Pc(15) int local15 = 0;
		@Pc(29) int local29 = arg3.anInt620 * TextureOpBrightness.aClass50_Sub1_1.anInt3449 >> 8;
		if (arg3.anInt615 < arg2) {
			local15 = arg2 - arg3.anInt615;
		} else if (arg3.anInt614 > arg2) {
			local15 = arg3.anInt614 - arg2;
		}
		if (arg1 > arg3.anInt612) {
			local15 += arg1 - arg3.anInt612;
		} else if (arg3.anInt613 > arg1) {
			local15 += arg3.anInt613 - arg1;
		}
		if (arg3.anInt611 == 0 || local15 - 64 > arg3.anInt611 || TextureOpBrightness.aClass50_Sub1_1.anInt3449 == 0 || arg3.anInt619 != arg0) {
			if (arg3.aClass4_Sub15_Sub3_2 != null) {
				GameCanvas.aClass4_Sub15_Sub2_2.method2955(arg3.aClass4_Sub15_Sub3_2);
				arg3.aClass4_Sub15_Sub3_2 = null;
			}
			if (arg3.aClass4_Sub15_Sub3_1 != null) {
				GameCanvas.aClass4_Sub15_Sub2_2.method2955(arg3.aClass4_Sub15_Sub3_1);
				arg3.aClass4_Sub15_Sub3_1 = null;
			}
			return;
		}
		local15 -= 64;
		if (local15 < 0) {
			local15 = 0;
		}
		@Pc(149) int local149 = (arg3.anInt611 - local15) * local29 / arg3.anInt611;
		if (arg3.aClass4_Sub15_Sub3_2 != null) {
			arg3.aClass4_Sub15_Sub3_2.setVolume(local149);
		} else if (arg3.anInt616 >= 0) {
			@Pc(161) SoundEffect local161 = SoundEffect.getSoundEffect(Class4_Sub6_Sub4.aClass76_19, arg3.anInt616, 0);
			if (local161 != null) {
				@Pc(168) VariableRateSoundPacket local168 = local161.getMixedAudio().resample(PathingEntitySequence.keyRecorder);
				@Pc(173) AreaSoundStream local173 = AreaSoundStream.method4489(local168, local149);
				local173.setNumLoops(-1);
				GameCanvas.aClass4_Sub15_Sub2_2.addSubStream(local173);
				arg3.aClass4_Sub15_Sub3_2 = local173;
			}
		}
		if (arg3.aClass4_Sub15_Sub3_1 != null) {
			arg3.aClass4_Sub15_Sub3_1.setVolume(local149);
			if (arg3.aClass4_Sub15_Sub3_1.method5683()) {
				return;
			}
			arg3.aClass4_Sub15_Sub3_1 = null;
		} else if (arg3.anIntArray37 != null && (arg3.anInt618 -= arg4) <= 0) {
			@Pc(210) int local210 = (int) (Math.random() * (double) arg3.anIntArray37.length);
			@Pc(218) SoundEffect local218 = SoundEffect.getSoundEffect(Class4_Sub6_Sub4.aClass76_19, arg3.anIntArray37[local210], 0);
			if (local218 != null) {
				@Pc(225) VariableRateSoundPacket local225 = local218.getMixedAudio().resample(PathingEntitySequence.keyRecorder);
				@Pc(230) AreaSoundStream local230 = AreaSoundStream.method4489(local225, local149);
				local230.setNumLoops(0);
				GameCanvas.aClass4_Sub15_Sub2_2.addSubStream(local230);
				arg3.anInt618 = (int) ((double) (arg3.anInt622 - arg3.anInt623) * Math.random()) + arg3.anInt623;
				arg3.aClass4_Sub15_Sub3_1 = local230;
				return;
			}
		}
	}
}
