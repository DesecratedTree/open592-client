package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.cache.node.PointLight;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.TileMessage;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.config.rsinterface.HookRequest;
import com.jagex.game.runetek5.deob.Class269;
@OriginalClass("client!jj")
public class Class120 {

	@OriginalMember(owner = "client!jj", name = "d", descriptor = "I")
	public int anInt5351;

	@OriginalMember(owner = "client!jj", name = "m", descriptor = "Ljava/lang/Object;")
	public Object anObject27;

	@OriginalMember(owner = "client!jj", name = "a", descriptor = "[Lclient!pe;")
	public final Class16_Sub1[] aClass16_Sub1Array2 = new Class16_Sub1[100];

	@OriginalMember(owner = "client!jj", name = "e", descriptor = "[I")
	public final int[] anIntArray357 = new int[100];

	@OriginalMember(owner = "client!jj", name = "j", descriptor = "[I")
	public final int[] anIntArray358 = new int[3];

	@OriginalMember(owner = "client!jj", name = "g", descriptor = "[Lclient!vp;")
	public final PointLight[] aClass4_Sub21Array3 = new PointLight[8];

@OriginalMember(owner = "client!jj", name = "h", descriptor = "I")
	public static int anInt5352;
@OriginalMember(owner = "client!jj", name = "l", descriptor = "I")
	public static int anInt5355;
@OriginalMember(owner = "client!jj", name = "f", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_113 = new Class83(" is already on your ignore list.", " steht bereits auf deiner Ignorieren-Liste!", " est déjà dans votre liste noire.", " já está na sua lista de ignorados.");
@OriginalMember(owner = "client!jj", name = "i", descriptor = "I")
	public static int anInt5353 = 0;
@OriginalMember(owner = "client!jj", name = "a", descriptor = "(IIIZZ)V")
	public static void method4107(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) boolean arg3) {
		TileMessage.aLong223 = 0L;
		@Pc(13) int local13 = Class269.method5664();
		if (arg0 == 3 || local13 == 3) {
			arg3 = true;
		}
		if (!MonoStereoData.aClass19_16.method4245()) {
			arg3 = true;
		}
		HookRequest.method4020(arg0, local13, arg2, arg3, arg1);
	}
}
