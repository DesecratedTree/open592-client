package com.jagex.game.runetek5.deob;

import java.awt.Component;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.deob.Class4_Sub31;
import com.jagex.game.runetek5.deob.Class4_Sub36;
import com.jagex.game.runetek5.deob.Class4_Sub41;
import com.jagex.game.runetek5.deob.Class4_Sub18;
@OriginalClass("client!hh")
public abstract class Class14 {

	@OriginalMember(owner = "client!hh", name = "a", descriptor = "(BLjava/awt/Component;)V")
	public abstract void attachTo(@OriginalArg(1) Component canvas);

	@OriginalMember(owner = "client!hh", name = "b", descriptor = "(B)I")
	public abstract int consumeWheelRotation();

	@OriginalMember(owner = "client!hh", name = "a", descriptor = "(Ljava/awt/Component;I)V")
	public abstract void detachFrom(@OriginalArg(0) Component canvas);

@OriginalMember(owner = "client!hh", name = "c", descriptor = "I")
	public static int anInt266;
@OriginalMember(owner = "client!hh", name = "i", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_4;
@OriginalMember(owner = "client!hh", name = "b", descriptor = "Lclient!bf;")
	public static final Class21 aClass21_2 = new Class21(12, 4);
@OriginalMember(owner = "client!hh", name = "e", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_10 = new Class83(" ", ": ", " ", " ");
@OriginalMember(owner = "client!hh", name = "g", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_23 = new Class22(47, 20);
@OriginalMember(owner = "client!hh", name = "h", descriptor = "Lclient!cw;")
	public static final Class4_Sub12_Sub1 aClass4_Sub12_Sub1_3 = new Class4_Sub12_Sub1(5000);
@OriginalMember(owner = "client!hh", name = "a", descriptor = "(BI)V")
	public static void method208(@OriginalArg(1) int arg0) {
		if (Class4_Sub18.method2034(arg0)) {
			MonoStereoData.method2212(-1, Class4_Sub36.aClass247ArrayArray2[arg0]);
		}
	}
@OriginalMember(owner = "client!hh", name = "a", descriptor = "(I)V")
	public static void method210() {
		Class4_Sub41.aClass68_51.method1777();
	}
@OriginalMember(owner = "client!hh", name = "a", descriptor = "([Ljava/lang/Object;[IZ)V")
	public static void method214(@OriginalArg(0) Object[] arg0, @OriginalArg(1) int[] arg1) {
		Class4_Sub31.method3813(arg1, arg1.length - 1, 0, arg0);
	}
}
