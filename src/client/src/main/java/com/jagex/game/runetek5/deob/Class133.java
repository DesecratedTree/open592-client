package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.cache.node.lru.CacheableNode;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class48_Sub1_Sub1;
import com.jagex.game.runetek5.deob.Class101;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub8_Sub1;
import com.jagex.game.runetek5.deob.Class38;
import com.jagex.game.runetek5.deob.Class16_Sub5;
@OriginalClass("client!lg")
public final class Class133 {

	@OriginalMember(owner = "client!lg", name = "a", descriptor = "Ljava/lang/String;")
	public String aString40;

	@OriginalMember(owner = "client!lg", name = "f", descriptor = "I")
	public int anInt4123;

	@OriginalMember(owner = "client!lg", name = "l", descriptor = "C")
	private char aChar2;

	@OriginalMember(owner = "client!lg", name = "e", descriptor = "Z")
	public boolean aBoolean385 = true;

	@OriginalMember(owner = "client!lg", name = "b", descriptor = "(I)Z")
	public boolean method3350() {
		return this.aChar2 == 's';
	}

	@OriginalMember(owner = "client!lg", name = "a", descriptor = "(ILclient!iv;I)V")
	private void method3352(@OriginalArg(1) Packet arg0, @OriginalArg(2) int arg1) {
		if (arg1 == 1) {
			this.aChar2 = Class4_Sub1_Sub8_Sub1.method3771(arg0.method2542());
		} else if (arg1 == 2) {
			this.anInt4123 = arg0.method2529();
		} else if (arg1 == 4) {
			this.aBoolean385 = false;
		} else if (arg1 == 5) {
			this.aString40 = arg0.method2537();
		}
	}

	@OriginalMember(owner = "client!lg", name = "a", descriptor = "(Lclient!iv;B)V")
	public void method3354(@OriginalArg(0) Packet arg0) {
		while (true) {
			@Pc(15) int local15 = arg0.method2490();
			if (local15 == 0) {
				return;
			}
			this.method3352(arg0, local15);
		}
	}

@OriginalMember(owner = "client!lg", name = "h", descriptor = "I")
	public static int anInt4125;
@OriginalMember(owner = "client!lg", name = "m", descriptor = "Lclient!v;")
	public static Class249 aClass249_1;
@OriginalMember(owner = "client!lg", name = "a", descriptor = "(II)Z")
	public static boolean method3349(@OriginalArg(0) int arg0) {
		return arg0 != 1 && arg0 != 7;
	}
@OriginalMember(owner = "client!lg", name = "a", descriptor = "(IB)V")
	public static void method3351() {
		Class48_Sub1_Sub1.aClass68_26.method1788(50);
		Static232.aClass68_25.method1788(50);
		Static384.aClass68_47.method1788(50);
		Class38.aClass68_4.method1788(50);
		Class101.aClass68_16.method1788(50);
	}
@OriginalMember(owner = "client!lg", name = "a", descriptor = "(ZLclient!ac;Lclient!ac;)V")
	public static void method3353(@OriginalArg(1) CacheableNode arg0, @OriginalArg(2) CacheableNode arg1) {
		if (arg1.aClass4_Sub1_56 != null) {
			arg1.method4662();
		}
		arg1.aClass4_Sub1_56 = arg0;
		arg1.aClass4_Sub1_55 = arg0.aClass4_Sub1_55;
		arg1.aClass4_Sub1_56.aClass4_Sub1_55 = arg1;
		arg1.aClass4_Sub1_55.aClass4_Sub1_56 = arg1;
	}
@OriginalMember(owner = "client!lg", name = "a", descriptor = "(B)V")
	public static void method3355() {
		Class16_Sub5.aBoolean596 = true;
	}
}
