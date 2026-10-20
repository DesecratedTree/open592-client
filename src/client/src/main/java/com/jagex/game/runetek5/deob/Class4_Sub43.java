package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.sound.player.PcmPlayer;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.deob.Class244;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
@OriginalClass("client!vj")
public final class Class4_Sub43 extends Country {

	@OriginalMember(owner = "client!vj", name = "o", descriptor = "I")
	public int anInt6979;

	@OriginalMember(owner = "client!vj", name = "q", descriptor = "I")
	public int anInt6981;

@OriginalMember(owner = "client!vj", name = "s", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_99;
@OriginalMember(owner = "client!vj", name = "t", descriptor = "Lclient!ln;")
	public static PcmPlayer aClass49_2;
@OriginalMember(owner = "client!vj", name = "m", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_319 = new Class22(73, 12);
@OriginalMember(owner = "client!vj", name = "r", descriptor = "[Z")
	public static final boolean[] aBooleanArray23 = new boolean[200];
@OriginalMember(owner = "client!vj", name = "a", descriptor = "(Lclient!et;II)I")
	public static int method5410(@OriginalArg(0) SignLink arg0) {
		return -1;
	}
@OriginalMember(owner = "client!vj", name = "a", descriptor = "(Z)V")
	public static void method5412() {
		TextureOpColorGradient.method1541();
		for (@Pc(8) int local8 = 0; local8 < 4; local8++) {
			PlayerVarDomain.aClass213Array1[local8].method4851();
		}
		Static277.method3932();
		Class244.method5485();
		System.gc();
	}
@OriginalMember(owner = "client!vj", name = "a", descriptor = "(II)Z")
	public static boolean method5413(@OriginalArg(1) int arg0) {
		return arg0 == (arg0 & -arg0);
	}
}
