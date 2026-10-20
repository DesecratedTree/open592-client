package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class16_Sub2_Sub3;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.graphics.texture.water.NativeWaterTexture;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.deob.Class6_Sub2;
@OriginalClass("client!ua")
public abstract class Class16_Sub3 extends Class16 {

	@OriginalMember(owner = "client!ua", name = "j", descriptor = "I")
	public int anInt737;

	@OriginalMember(owner = "client!ua", name = "l", descriptor = "I")
	public int anInt739;

	@OriginalMember(owner = "client!ua", name = "s", descriptor = "Z")
	public boolean aBoolean73;

	@OriginalMember(owner = "client!ua", name = "t", descriptor = "I")
	public int anInt744;

	@OriginalMember(owner = "client!ua", name = "a", descriptor = "(IILclient!fi;BZLclient!ya;I)V")
	@Override
	public final void method5337(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Class16 arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg4, @OriginalArg(6) int arg5) {
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!ua", name = "d", descriptor = "(I)V")
	@Override
	public final void removeTileShadow() {
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!ua", name = "d", descriptor = "(B)Z")
	@Override
	public final boolean hasTileShadow() {
		return false;
	}

@OriginalMember(owner = "client!ua", name = "o", descriptor = "I")
	public static int anInt742;
@OriginalMember(owner = "client!ua", name = "q", descriptor = "[I")
	public static final int[] anIntArray45 = new int[256];
@OriginalMember(owner = "client!ua", name = "r", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_21 = new Class83("Members object", "Gegenstand für Mitglieder", "Objet d'abonnés", "Objeto para membros");
@OriginalMember(owner = "client!ua", name = "v", descriptor = "[Lclient!pt;")
	public static final Class4_Sub1_Sub17[] aClass4_Sub1_Sub17Array6 = new Class4_Sub1_Sub17[14];
@OriginalMember(owner = "client!ua", name = "a", descriptor = "(III)Z")
	public static boolean method723(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		return (arg1 & 0x70000) != 0 | Class16_Sub2_Sub3.method3594(arg0, arg1) || Class164.method3914(arg0, arg1);
	}
@OriginalMember(owner = "client!ua", name = "a", descriptor = "(IB)V")
	public static void method724() {
		Class21.anInt461 = 1;
		Static47.anInt811 = 2;
		Static14.anInt190 = -1;
		Class126.anInt3920 = 0;
		NativeWaterTexture.aClass76_49 = null;
		anInt742 = -1;
		Class6_Sub2.aBoolean30 = false;
	}
@OriginalMember(owner = "client!ua", name = "a", descriptor = "(BII)Z")
	public static boolean method726(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		return (arg1 & 0x180) != 0;
	}
}
