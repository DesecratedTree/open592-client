package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
@OriginalClass("client!ul")
public final class Class4_Sub41 extends Country {

	@OriginalMember(owner = "client!ul", name = "n", descriptor = "I")
	public final int anInt6690;

	@OriginalMember(owner = "client!ul", name = "l", descriptor = "I")
	public final int anInt6688;

	@OriginalMember(owner = "client!ul", name = "r", descriptor = "I")
	public final int anInt6693;

	@OriginalMember(owner = "client!ul", name = "z", descriptor = "I")
	public final int anInt6701;

	@OriginalMember(owner = "client!ul", name = "x", descriptor = "I")
	private final int anInt6699;

	@OriginalMember(owner = "client!ul", name = "u", descriptor = "I")
	private final int anInt6696;

	@OriginalMember(owner = "client!ul", name = "q", descriptor = "I")
	private final int anInt6692;

	@OriginalMember(owner = "client!ul", name = "w", descriptor = "I")
	private final int anInt6698;

	@OriginalMember(owner = "client!ul", name = "t", descriptor = "I")
	private final int anInt6695;

	@OriginalMember(owner = "client!ul", name = "<init>", descriptor = "(IIIIIIIII)V")
	public Class4_Sub41(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8) {
		this.anInt6690 = arg6;
		this.anInt6688 = arg7;
		this.anInt6693 = arg8;
		this.anInt6701 = arg5;
		this.anInt6699 = arg3;
		this.anInt6696 = arg1;
		this.anInt6692 = arg0;
		this.anInt6698 = arg2;
		this.anInt6695 = arg4;
	}

	@OriginalMember(owner = "client!ul", name = "a", descriptor = "([IIII)V")
	public void method5236(@OriginalArg(0) int[] arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		arg0[2] = arg2 + this.anInt6690 - this.anInt6698;
		arg0[1] = this.anInt6701 + arg1 - this.anInt6696;
		arg0[0] = 0;
	}

	@OriginalMember(owner = "client!ul", name = "a", descriptor = "(III[I)V")
	public void method5237(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int[] arg2) {
		arg2[2] = this.anInt6698 + arg0 - this.anInt6690;
		arg2[0] = this.anInt6692;
		arg2[1] = this.anInt6696 + arg1 - this.anInt6701;
	}

	@OriginalMember(owner = "client!ul", name = "a", descriptor = "(BII)Z")
	public boolean method5238(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		return this.anInt6696 <= arg1 && arg1 <= this.anInt6699 && arg0 >= this.anInt6698 && this.anInt6695 >= arg0;
	}

	@OriginalMember(owner = "client!ul", name = "a", descriptor = "(IIB)Z")
	public boolean method5239(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		return this.anInt6701 <= arg1 && this.anInt6688 >= arg1 && arg0 >= this.anInt6690 && arg0 <= this.anInt6693;
	}

	@OriginalMember(owner = "client!ul", name = "a", descriptor = "(IIII)Z")
	public boolean method5240(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		return arg2 == this.anInt6692 && arg1 >= this.anInt6696 && arg1 <= this.anInt6699 && this.anInt6698 <= arg0 && this.anInt6695 >= arg0;
	}

@OriginalMember(owner = "client!ul", name = "B", descriptor = "I")
	public static int anInt6702;
@OriginalMember(owner = "client!ul", name = "H", descriptor = "I")
	public static int anInt6705;
@OriginalMember(owner = "client!ul", name = "p", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_306 = new Class22(96, 2);
@OriginalMember(owner = "client!ul", name = "A", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_307 = new Class22(94, 0);
@OriginalMember(owner = "client!ul", name = "D", descriptor = "[F")
	public static final float[] aFloatArray30 = new float[4];
@OriginalMember(owner = "client!ul", name = "F", descriptor = "Lclient!fa;")
	public static final LRUCache aClass68_51 = new LRUCache(8);
@OriginalMember(owner = "client!ul", name = "G", descriptor = "I")
	public static int anInt6704 = 0;
@OriginalMember(owner = "client!ul", name = "a", descriptor = "(IIIBII)V")
	public static void method5242(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4) {
		for (@Pc(7) int local7 = arg4; local7 <= arg1; local7++) {
			BasType.method3640(arg0, arg2, arg3, Class4_Sub6_Sub7.anIntArrayArray21[local7]);
		}
	}
}
