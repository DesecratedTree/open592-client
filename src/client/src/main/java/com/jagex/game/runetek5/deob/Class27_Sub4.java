package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import java.awt.Canvas;
import com.jagex.game.runetek5.unpack_Sub1;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.RemoteFileWorker;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub2;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.deob.Class60;
import com.jagex.game.runetek5.deob.RuntimeException_Sub1;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchPcmStream;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
@OriginalClass("client!ob")
public final class Class27_Sub4 extends TextureCombinerOp {

	@OriginalMember(owner = "client!ob", name = "h", descriptor = "I")
	private final int anInt5157;

	@OriginalMember(owner = "client!ob", name = "n", descriptor = "I")
	private final int anInt5161;

	@OriginalMember(owner = "client!ob", name = "l", descriptor = "I")
	private final int anInt5160;

	@OriginalMember(owner = "client!ob", name = "p", descriptor = "I")
	private final int anInt5163;

	@OriginalMember(owner = "client!ob", name = "<init>", descriptor = "(IIIIIII)V")
	public Class27_Sub4(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6) {
		super(arg4, arg5, arg6);
		this.anInt5157 = arg0;
		this.anInt5161 = arg2;
		this.anInt5160 = arg3;
		this.anInt5163 = arg1;
	}

	@OriginalMember(owner = "client!ob", name = "c", descriptor = "(III)V")
	@Override
	public void method3939(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		@Pc(10) int local10 = this.anInt5157 * arg1 >> 12;
		@Pc(17) int local17 = arg1 * this.anInt5161 >> 12;
		@Pc(24) int local24 = arg0 * this.anInt5163 >> 12;
		@Pc(31) int local31 = this.anInt5160 * arg0 >> 12;
		Class60.method1588(local24, local17, super.anInt5155, local10, local31);
	}

	@OriginalMember(owner = "client!ob", name = "b", descriptor = "(III)V")
	@Override
	public void method3936(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		@Pc(10) int local10 = this.anInt5157 * arg1 >> 12;
		@Pc(22) int local22 = this.anInt5161 * arg1 >> 12;
		@Pc(29) int local29 = this.anInt5163 * arg0 >> 12;
		@Pc(36) int local36 = this.anInt5160 * arg0 >> 12;
		ComponentSurface.method5623(super.anInt5152, super.anInt5154, local10, super.anInt5155, local29, local22, local36);
	}

	@OriginalMember(owner = "client!ob", name = "a", descriptor = "(III)V")
	@Override
	public void method3935(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
	}

@OriginalMember(owner = "client!ob", name = "m", descriptor = "[I")
	public static int[] anIntArray338;
@OriginalMember(owner = "client!ob", name = "j", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_232 = new Class22(52, 8);
@OriginalMember(owner = "client!ob", name = "s", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_63 = new Class215(41, 3);
@OriginalMember(owner = "client!ob", name = "a", descriptor = "(ILjava/awt/Canvas;ILclient!et;ILclient!ga;)Lclient!ya;")
	public static com.jagex.game.runetek5.graphics.Toolkit method3941(@OriginalArg(0) int arg0, @OriginalArg(1) Canvas arg1, @OriginalArg(3) SignLink arg2, @OriginalArg(4) int arg3, @OriginalArg(5) unpack_Sub1 arg4) {
		return new GlToolkit(arg0, arg1, arg4, arg3, arg2);
	}
@OriginalMember(owner = "client!ob", name = "a", descriptor = "(JB)V")
	public static void method3942(@OriginalArg(0) long arg0) {
		ParticleNode.aClass4_Sub12_Sub1_5.currentPosition = 0;
		ParticleNode.aClass4_Sub12_Sub1_5.method2551(Class4_Sub1_Sub11.aClass60_7.anInt1812);
		ParticleNode.aClass4_Sub12_Sub1_5.method2504(arg0);
		Class4_Sub6_Sub31.anInt5976 = 0;
		Class16_Sub4_Sub2.anInt2524 = 0;
		ItemType.anInt6183 = 1;
		RuntimeException_Sub1.anInt4623 = -3;
	}
@OriginalMember(owner = "client!ob", name = "a", descriptor = "(Lclient!ya;)V")
	public static void method3943(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		for (@Pc(1) int local1 = MusicPatchPcmStream.anInt6251; local1 < RemoteFileWorker.anInt4487; local1++) {
			for (@Pc(4) int local4 = 0; local4 < GpuFaceNormal.menuOpenWidth; local4++) {
				for (@Pc(7) int local7 = 0; local7 < GlModel.anInt3361; local7++) {
					@Pc(16) Class164 local16 = Class70.aClass164ArrayArrayArray5[local1][local4][local7];
					if (local16 != null) {
						@Pc(21) Class16_Sub5 local21 = local16.aClass16_Sub5_3;
						@Pc(24) Class16_Sub5 local24 = local16.aClass16_Sub5_2;
						if (local21 != null && local21.hasTileShadow()) {
							TextureOpIrregularBricks.method1774(arg0, local21, local1, local4, local7, 1, 1);
							if (local24 != null && local24.hasTileShadow()) {
								TextureOpIrregularBricks.method1774(arg0, local24, local1, local4, local7, 1, 1);
								local24.method5337(0, 0, local21, false, arg0, 0);
								local24.removeTileShadow();
							}
							local21.removeTileShadow();
						}
						for (@Pc(70) Class7 local70 = local16.aClass7_3; local70 != null; local70 = local70.aClass7_1) {
							@Pc(74) Class16_Sub1 local74 = local70.aClass16_Sub1_1;
							if (local74 != null && local74.hasTileShadow()) {
								TextureOpIrregularBricks.method1774(arg0, local74, local1, local4, local7, local74.aShort97 + 1 - local74.aShort94, local74.aShort95 - local74.aShort96 + 1);
								local74.removeTileShadow();
							}
						}
						@Pc(111) Class16_Sub4 local111 = local16.aClass16_Sub4_2;
						if (local111 != null && local111.hasTileShadow()) {
							MECType.method3787(arg0, local111, local1, local4, local7);
							local111.removeTileShadow();
						}
					}
				}
			}
		}
	}
}
