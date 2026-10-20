package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteType;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.FixedFunctionSpecularPass;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
@OriginalClass("client!bf")
public final class Class21 {

	@OriginalMember(owner = "client!bf", name = "h", descriptor = "[F")
	public static final float[] aFloatArray3 = new float[16384];

	@OriginalMember(owner = "client!bf", name = "g", descriptor = "[F")
	public static final float[] aFloatArray2 = new float[16384];

	static {
		@Pc(19) double local19 = 3.834951969714103E-4D;
		for (@Pc(21) int local21 = 0; local21 < 16384; local21++) {
			aFloatArray2[local21] = (float) Math.sin(local19 * (double) local21);
			aFloatArray3[local21] = (float) Math.cos(local19 * (double) local21);
		}
	}

	@OriginalMember(owner = "client!bf", name = "<init>", descriptor = "(II)V")
	public Class21(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
	}

	@OriginalMember(owner = "client!bf", name = "toString", descriptor = "()Ljava/lang/String;")
	@Override
	public String toString() {
		throw new IllegalStateException();
	}

@OriginalMember(owner = "client!bf", name = "j", descriptor = "I")
	public static int anInt460;
@OriginalMember(owner = "client!bf", name = "e", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_7 = new Class215(19, 2);
@OriginalMember(owner = "client!bf", name = "f", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_33 = new Class22(18, 1);
@OriginalMember(owner = "client!bf", name = "i", descriptor = "Lclient!pk;")
	public static final Class183 aClass183_2 = new Class183();
@OriginalMember(owner = "client!bf", name = "k", descriptor = "I")
	public static int anInt461 = 0;
@OriginalMember(owner = "client!bf", name = "a", descriptor = "(II)V")
	public static void method522(@OriginalArg(0) int arg0) {
		if (anInt461 == 0) {
			MapSpriteType.aClass4_Sub15_Sub1_1.method1428(arg0);
		} else {
			Class126.anInt3920 = arg0;
		}
	}
@OriginalMember(owner = "client!bf", name = "a", descriptor = "(IB)I")
	public static int method523(@OriginalArg(0) int arg0) {
		return arg0 & 0xFF;
	}
@OriginalMember(owner = "client!bf", name = "a", descriptor = "(IIIIIIIII)V")
	public static void method524(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) int arg7) {
		if (arg1 < 1 || arg6 < 1 || GLBillowTextureData.anInt5666 - 2 < arg1 || arg6 > Static283.anInt5187 - 2) {
			return;
		}
		@Pc(32) int local32 = arg0;
		if (arg0 < 3 && EnvironmentSampler.method3229(arg1, arg6)) {
			local32 = arg0 + 1;
		}
		if (!TextureOpBrightness.aClass50_Sub1_1.method2861(Packet.anInt2973) && !Class4_Sub37.method4164(arg1, FixedFunctionSpecularPass.anInt5523, local32, arg6)) {
			return;
		}
		if (Class70.aClass164ArrayArrayArray5 == null) {
			return;
		}
		Class4_Sub6_Sub4.aClass266_Sub1_1.method5645(MonoStereoData.aClass19_16, PlayerVarDomain.aClass213Array1[arg0], arg7, arg0, arg6, arg1);
		if (arg5 < 0) {
			return;
		}
		@Pc(73) boolean local73 = TextureOpBrightness.aClass50_Sub1_1.aBoolean305;
		TextureOpBrightness.aClass50_Sub1_1.aBoolean305 = true;
		Class4_Sub6_Sub4.aClass266_Sub1_1.method5649(arg1, arg0, arg4, arg3, arg5, MonoStereoData.aClass19_16, arg2, arg6, local32, PlayerVarDomain.aClass213Array1[arg0]);
		TextureOpBrightness.aClass50_Sub1_1.aBoolean305 = local73;
		return;
	}
}
