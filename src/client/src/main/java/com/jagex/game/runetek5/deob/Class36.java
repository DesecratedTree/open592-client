package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.entity.PathingEntity;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.deob.Class186;
@OriginalClass("client!kk")
public abstract class Class36 {

	static {
		new Class83("Unable to send message - player unavailable.", "Deine Nachricht konnte nicht verschickt werden,", "Impossible d'envoyer un message - joueur indisponible.", "Não foi possível enviar a mensagem. O jogador não está disponível.");
	}

	@OriginalMember(owner = "client!kk", name = "a", descriptor = "(B)V")
	public abstract void method2248();

	@OriginalMember(owner = "client!kk", name = "c", descriptor = "(B)J")
	public abstract long method2252();

	@OriginalMember(owner = "client!kk", name = "a", descriptor = "(ZI)I")
	public abstract int method2253(@OriginalArg(1) int arg0);

@OriginalMember(owner = "client!kk", name = "e", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_29;
@OriginalMember(owner = "client!kk", name = "c", descriptor = "Lclient!ib;")
	public static final CubeMapSpecularEffect aClass102_11 = new CubeMapSpecularEffect(32);
@OriginalMember(owner = "client!kk", name = "d", descriptor = "I")
	public static int anInt2656 = 100;
@OriginalMember(owner = "client!kk", name = "a", descriptor = "(IIIIIIIIIIIIII)V")
	public static void method2249(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) int arg7, @OriginalArg(9) int arg8, @OriginalArg(10) int arg9, @OriginalArg(11) int arg10, @OriginalArg(12) int arg11, @OriginalArg(13) int arg12) {
		if (Class16_Sub1_Sub6.aClass65Array4 == null) {
			return;
		}
		@Pc(25) PathingEntity local25;
		@Pc(18) int local18;
		if (arg5 < 0) {
			local18 = -arg5 - 1;
			if (local18 == Class6.anInt5452) {
				local25 = Static1.aClass16_Sub1_Sub5_Sub1_1;
			} else {
				local25 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local18];
			}
		} else {
			local18 = arg5 - 1;
			local25 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local18];
		}
		if (local25 == null) {
			return;
		}
		@Pc(47) LocType local47 = Static267.aClass262_2.method5560(arg10);
		@Pc(56) int local56;
		@Pc(59) int local59;
		if (arg2 == 1 || arg2 == 3) {
			local56 = local47.anInt5801;
			local59 = local47.anInt5816;
		} else {
			local59 = local47.anInt5801;
			local56 = local47.anInt5816;
		}
		@Pc(73) int local73 = (local56 >> 1) + arg0;
		@Pc(82) int local82 = arg0 + (local56 + 1 >> 1);
		@Pc(88) int local88 = arg9 + (local59 >> 1);
		@Pc(104) int local104 = arg9 + (local59 + 1 >> 1);
		@Pc(108) Class65 local108 = Class16_Sub1_Sub6.aClass65Array4[arg7];
		@Pc(132) int local132 = local108.l(local73, local88) + local108.l(local82, local88) + local108.l(local73, local104) + local108.l(local82, local104) >> 2;
		@Pc(136) Class4_Sub23 local136 = new Class4_Sub23();
		local136.anInt3191 = Shadow.anInt5323 + arg11;
		local136.anInt3195 = arg12;
		local136.anInt3202 = Shadow.anInt5323 + arg1;
		local136.anInt3196 = local25.aByte82;
		local136.anInt3190 = arg2;
		@Pc(167) int local167;
		if (arg3 > arg8) {
			local167 = arg3;
			arg3 = arg8;
			arg8 = local167;
		}
		local136.anInt3189 = arg0;
		local136.anInt3194 = arg10;
		local136.anInt3193 = arg9;
		if (arg4 < arg6) {
			local167 = arg6;
			arg6 = arg4;
			arg4 = local167;
		}
		local136.anInt3204 = arg0 + arg8;
		local136.anInt3200 = arg0 + arg3;
		local136.anInt3203 = (local59 << 6) + (local136.anInt3193 << 7);
		local136.anInt3192 = arg6 + arg9;
		local136.anInt3199 = arg4 + arg9;
		local136.anInt3188 = local132;
		local136.anInt3198 = (local136.anInt3189 << 7) + (local56 << 6);
		Static298.aClass183_38.method4137(local136);
		local25.aClass4_Sub23_3 = local136;
	}
@OriginalMember(owner = "client!kk", name = "b", descriptor = "(B)V")
	public static void method2250() {
		if (SignLink.aString23.toLowerCase().indexOf("microsoft") != -1) {
			Class186.anIntArray373[221] = 43;
			Class186.anIntArray373[186] = 57;
			Class186.anIntArray373[189] = 26;
			Class186.anIntArray373[220] = 74;
			Class186.anIntArray373[187] = 27;
			Class186.anIntArray373[222] = 59;
			Class186.anIntArray373[192] = 58;
			Class186.anIntArray373[219] = 42;
			Class186.anIntArray373[190] = 72;
			Class186.anIntArray373[191] = 73;
			Class186.anIntArray373[188] = 71;
			Class186.anIntArray373[223] = 28;
			return;
		}
		if (SignLink.aMethod1 == null) {
			Class186.anIntArray373[222] = 59;
			Class186.anIntArray373[192] = 58;
		} else {
			Class186.anIntArray373[192] = 28;
			Class186.anIntArray373[520] = 59;
			Class186.anIntArray373[222] = 58;
		}
		Class186.anIntArray373[91] = 42;
		Class186.anIntArray373[46] = 72;
		Class186.anIntArray373[61] = 27;
		Class186.anIntArray373[93] = 43;
		Class186.anIntArray373[47] = 73;
		Class186.anIntArray373[59] = 57;
		Class186.anIntArray373[92] = 74;
		Class186.anIntArray373[45] = 26;
		Class186.anIntArray373[44] = 71;
	}
}
