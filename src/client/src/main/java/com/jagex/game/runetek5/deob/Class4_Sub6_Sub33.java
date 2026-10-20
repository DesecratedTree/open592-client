package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class120_Sub1;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
import com.jagex.game.runetek5.client.GameShell;
import com.jagex.game.runetek5.graphics.JavaBillboardFace;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.graphics.texture.sampler.GlRectangleTexture;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.entity.pathingentity.EasedAngle;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.js5.Js5WorkerThread;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
@OriginalClass("client!tg")
public final class Class4_Sub6_Sub33 extends TextureOperation {

	@OriginalMember(owner = "client!tg", name = "F", descriptor = "I")
	private int anInt6441 = 0;

	@OriginalMember(owner = "client!tg", name = "K", descriptor = "I")
	private int anInt6444 = 1;

	@OriginalMember(owner = "client!tg", name = "D", descriptor = "I")
	private int anInt6439 = 0;

	@OriginalMember(owner = "client!tg", name = "<init>", descriptor = "()V")
	public Class4_Sub6_Sub33() {
		super(0, true);
	}

	@OriginalMember(owner = "client!tg", name = "a", descriptor = "(Z)V")
	@Override
	public void postDecode() {
		LRUCache.method1784();
	}

	@OriginalMember(owner = "client!tg", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.anInt6439 = arg1.method2490();
		} else if (arg0 == 1) {
			this.anInt6441 = arg1.method2490();
		} else if (arg0 == 3) {
			this.anInt6444 = arg1.method2490();
		}
	}

	@OriginalMember(owner = "client!tg", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(19) int[] local19 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			@Pc(27) int local27 = ParticleEffector.anIntArray209[arg0];
			@Pc(33) int local33 = local27 - 2048 >> 1;
			for (@Pc(35) int local35 = 0; local35 < MidiPcmStream.anInt1616; local35++) {
				@Pc(41) int local41 = AbstractArbBufferGL.anIntArray504[local35];
				@Pc(47) int local47 = local41 - 2048 >> 1;
				@Pc(70) int local70;
				if (this.anInt6439 == 0) {
					local70 = (local41 - local27) * this.anInt6444;
				} else {
					@Pc(60) int local60 = local33 * local33 + local47 * local47 >> 12;
					local70 = (int) (Math.sqrt((double) ((float) local60 / 4096.0F)) * 4096.0D);
					local70 = (int) ((double) (local70 * this.anInt6444) * 3.141592653589793D);
				}
				local70 -= local70 & 0xFFFFF000;
				if (this.anInt6441 == 0) {
					local70 = Static277.anIntArray336[local70 >> 4 & 0xFF] + 4096 >> 1;
				} else if (this.anInt6441 == 2) {
					local70 -= 2048;
					if (local70 < 0) {
						local70 = -local70;
					}
					local70 = 2048 - local70 << 1;
				}
				local19[local35] = local70;
			}
		}
		return local19;
	}

@OriginalMember(owner = "client!tg", name = "G", descriptor = "[I")
	public static int[] anIntArray491;
@OriginalMember(owner = "client!tg", name = "O", descriptor = "I")
	public static int anInt6447 = 0;
@OriginalMember(owner = "client!tg", name = "b", descriptor = "(Z)[Lclient!eh;")
	public static Class60[] method5040() {
		return new Class60[] { Class4_Sub1_Sub11.aClass60_1, Class4_Sub1_Sub11.aClass60_2, Class4_Sub1_Sub11.aClass60_3, Class4_Sub1_Sub11.aClass60_4, Class4_Sub1_Sub11.aClass60_5, Class4_Sub1_Sub11.aClass60_6, Class4_Sub1_Sub11.aClass60_7, Class4_Sub1_Sub11.aClass60_8, Class4_Sub1_Sub11.aClass60_9, Class4_Sub1_Sub11.aClass60_10 };
	}
@OriginalMember(owner = "client!tg", name = "f", descriptor = "(I)V")
	public static void method5041() {
		if (Static32.aBoolean63) {
			return;
		}
		BasType.aBoolean429 = true;
		if (TextureOpBrightness.aClass50_Sub1_1.aBoolean303) {
			Class16_Sub2.aFloat142 = (int) Class16_Sub2.aFloat142 - 17 & 0xFFFFFFF0;
		} else {
			EasedAngle.aFloat28 += (-12.0F - EasedAngle.aFloat28) / 2.0F;
		}
		Static32.aBoolean63 = true;
	}
@OriginalMember(owner = "client!tg", name = "a", descriptor = "(Lclient!pk;ILclient!ya;II)V")
	public static void method5042(@OriginalArg(0) Class183 arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1) {
		EnvironmentSampler.aClass183_29.method4138();
		if (Class120_Sub1.aBoolean482) {
			return;
		}
		for (@Pc(21) Class4_Sub3 local21 = (Class4_Sub3) arg0.method4140(); local21 != null; local21 = (Class4_Sub3) arg0.method4144()) {
			@Pc(29) MECType local29 = Static148.MECTYPE_LIST.method4124(local21.anInt234);
			if (Js5WorkerThread.method5473(local29)) {
				@Pc(41) boolean local41 = GlRectangleTexture.method3908(arg1, local21, local29);
				if (local41) {
					EffectiveVertex.method4102(local29, local21, arg1);
				}
			}
		}
	}
@OriginalMember(owner = "client!tg", name = "a", descriptor = "(IZII)V")
	public static void method5043(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		if (arg0 == 1004) {
			Static271.method3899(Static258.aClass186_12, arg2, arg1);
		} else if (arg0 == 1012) {
			Static271.method3899(client.aClass186_4, arg2, arg1);
		} else if (arg0 == 1008) {
			Static271.method3899(Class4_Sub6_Sub7.aClass186_8, arg2, arg1);
		} else if (arg0 == 1001) {
			Static271.method3899(JavaBillboardFace.aClass186_11, arg2, arg1);
		} else if (arg0 == 1006) {
			Static271.method3899(GameShell.aClass186_3, arg2, arg1);
		}
	}
@OriginalMember(owner = "client!tg", name = "b", descriptor = "(II)V")
	public static void method5045(@OriginalArg(0) int arg0) {
		@Pc(17) Class4_Sub1_Sub11 local17 = NPCTypeList.method5222(arg0, 5);
		local17.method2608();
	}
}
