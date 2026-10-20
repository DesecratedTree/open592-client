package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

import com.jagex.game.runetek5.graphics.model.particle.Particle;

import com.jagex.game.runetek5.deob.*;
@OriginalClass("client!wt")
public final class Class4_Sub45 extends Country {

	@OriginalMember(owner = "client!wt", name = "m", descriptor = "I")
	public int anInt7356;

	@OriginalMember(owner = "client!wt", name = "l", descriptor = "I")
	public final int anInt7355;

	@OriginalMember(owner = "client!wt", name = "<init>", descriptor = "(II)V")
	public Class4_Sub45(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		this.anInt7356 = arg1;
		this.anInt7355 = arg0;
	}

@OriginalMember(owner = "client!wt", name = "p", descriptor = "[Lclient!lb;")
	public static Particle[] aClass3_Sub1_Sub1_Sub1Array2;
@OriginalMember(owner = "client!wt", name = "s", descriptor = "F")
	public static float aFloat227;
@OriginalMember(owner = "client!wt", name = "n", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_152 = new Class83("M", "M", "M", "M");
@OriginalMember(owner = "client!wt", name = "o", descriptor = "I")
	public static int anInt7357 = 500;
@OriginalMember(owner = "client!wt", name = "q", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_335 = new Class22(29, 1);
@OriginalMember(owner = "client!wt", name = "r", descriptor = "[[I")
	public static final int[][] anIntArrayArray66 = new int[][] { { 12, 12, 12, 12 }, { 12, 12, 12, 12 }, { 5, 5, 5 }, { 5, 5, 5 }, { 5, 5, 5 }, { 5, 5, 5 }, { 12, 12, 12, 12, 12, 12 }, { 1, 1, 1, 7 }, { 1, 1, 7, 1 }, { 8, 9, 9, 8, 8, 9 }, { 8, 8, 9, 8, 9, 9 }, { 10, 10, 11, 11, 11, 10 }, { 12, 12, 12, 12 } };
@OriginalMember(owner = "client!wt", name = "t", descriptor = "I")
	public static int anInt7358 = -1;
@OriginalMember(owner = "client!wt", name = "u", descriptor = "I")
	public static int anInt7359 = 0;
@OriginalMember(owner = "client!wt", name = "v", descriptor = "I")
	public static int anInt7360 = 0;
}
