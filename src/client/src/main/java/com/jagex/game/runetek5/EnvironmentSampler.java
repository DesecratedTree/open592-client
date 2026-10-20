package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Static283;
import com.jagex.game.runetek5.deob.Static384;
import com.jagex.game.runetek5.deob.Static449;
import com.jagex.game.runetek5.entity.EntityNodeList;
import com.jagex.game.runetek5.graphics.model.particle.Particle;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.*;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.deob.Class27_Sub1;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.deob.Class4_Sub45;
import com.jagex.game.runetek5.config.sequence.SeqTypeList;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.graphics.shader.Texture3DShaderGL;
import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.scene.CollisionMap;
import com.jagex.game.runetek5.config.mec.MECTypeList;
@OriginalClass("client!tb")
public abstract class EnvironmentSampler {

@OriginalMember(owner = "client!tb", name = "e", descriptor = "Lclient!uu;")
	public static com.jagex.game.runetek5.config.rsinterface.Component aClass247_8;
@OriginalMember(owner = "client!tb", name = "f", descriptor = "Lclient!pk;")
	public static Class183 aClass183_29;
@OriginalMember(owner = "client!tb", name = "a", descriptor = "(IIIIIIIIII)V")
	public static void method3226(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(4) int arg2, @OriginalArg(5) int arg3, @OriginalArg(6) int arg4, @OriginalArg(8) int arg5, @OriginalArg(9) int arg6) {
		if (arg0 < 128 || arg1 < 128 || (GLBillowTextureData.anInt5666 - 2) * 128 < arg0 || (Static283.anInt5187 - 2) * 128 < arg1) {
			Static449.anIntArray557[0] = Static449.anIntArray557[1] = -1;
			return;
		}
		@Pc(43) int local43 = BufferedSocket.method135(arg0, arg5, arg1) - arg4;
		Class3_Sub3.aClass31_1.o(arg2, 0, 0);
		MonoStereoData.aClass19_16.ea(Class3_Sub3.aClass31_1);
		MonoStereoData.aClass19_16.X(arg0, local43, arg1, Static449.anIntArray557);
		Class3_Sub3.aClass31_1.o(-arg2, 0, 0);
		MonoStereoData.aClass19_16.ea(Class3_Sub3.aClass31_1);
	}
@OriginalMember(owner = "client!tb", name = "a", descriptor = "(ILclient!fs;)V")
	public static void method3227(@OriginalArg(1) Js5ResourceProvider arg0) {
		CollisionMap.anInt6240 = 0;
		MECTypeList.anInt5385 = 0;
		Static384.aClass84_8 = new EntityNodeList();
		Class4_Sub45.aClass3_Sub1_Sub1_Sub1Array2 = new Particle[1024];
		Texture3DShaderGL.method1249(arg0);
		Class27_Sub1.method664(arg0);
	}
@OriginalMember(owner = "client!tb", name = "a", descriptor = "(III)Z")
	public static boolean method3229(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		return (SeqTypeList.aByteArrayArrayArray2[1][arg0][arg1] & 0x2) != 0;
	}
@OriginalMember(owner = "client!tb", name = "a", descriptor = "(IIB)Lclient!uu;")
	public static com.jagex.game.runetek5.config.rsinterface.Component method3230(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		@Pc(15) com.jagex.game.runetek5.config.rsinterface.Component local15 = ParticleBinaryTree.method5121(arg0);
		if (arg1 == -1) {
			return local15;
		} else if (local15 == null || local15.slotChildren == null || arg1 >= local15.slotChildren.length) {
			return null;
		} else {
			return local15.slotChildren[arg1];
		}
	}
@OriginalMember(owner = "client!tb", name = "a", descriptor = "(I[BI[BIIIBI)V")
	public static void method3231(@OriginalArg(0) int arg0, @OriginalArg(1) byte[] arg1, @OriginalArg(2) int arg2, @OriginalArg(3) byte[] arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(8) int arg7) {
		@Pc(18) int local18 = -(arg5 >> 2);
		@Pc(23) int local23 = -(arg5 & 0x3);
		for (@Pc(26) int local26 = -arg7; local26 < 0; local26++) {
			@Pc(34) int local34;
			for (@Pc(30) int local30 = local18; local30 < 0; local30++) {
				local34 = arg0++;
				arg3[local34] = (byte) (arg3[local34] - arg1[arg4++]);
				@Pc(47) int local47 = arg0++;
				arg3[local47] = (byte) (arg3[local47] - arg1[arg4++]);
				@Pc(60) int local60 = arg0++;
				arg3[local60] = (byte) (arg3[local60] - arg1[arg4++]);
				@Pc(73) int local73 = arg0++;
				arg3[local73] = (byte) (arg3[local73] - arg1[arg4++]);
			}
			for (@Pc(89) int local89 = local23; local89 < 0; local89++) {
				local34 = arg0++;
				arg3[local34] = (byte) (arg3[local34] - arg1[arg4++]);
			}
			arg4 += arg6;
			arg0 += arg2;
		}
	}
}
