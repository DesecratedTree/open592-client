package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.entity.ComparableGraphNode;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub6_Sub2;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
@OriginalClass("client!hl")
public final class Class4_Sub1_Sub9_Sub2 extends Class4_Sub1_Sub9 {

	@OriginalMember(owner = "client!hl", name = "M", descriptor = "Ljava/lang/Object;")
	private final Object anObject16;

	@OriginalMember(owner = "client!hl", name = "<init>", descriptor = "(Lclient!bq;Ljava/lang/Object;I)V")
	public Class4_Sub1_Sub9_Sub2(@OriginalArg(0) ComparableGraphNode arg0, @OriginalArg(1) Object arg1, @OriginalArg(2) int arg2) {
		super(arg0, arg2);
		this.anObject16 = arg1;
	}

	@OriginalMember(owner = "client!hl", name = "e", descriptor = "(B)Z")
	@Override
	public boolean isSoft() {
		return false;
	}

	@OriginalMember(owner = "client!hl", name = "f", descriptor = "(B)Ljava/lang/Object;")
	@Override
	public Object get() {
		return this.anObject16;
	}

@OriginalMember(owner = "client!hl", name = "L", descriptor = "F")
	public static float aFloat103;
@OriginalMember(owner = "client!hl", name = "a", descriptor = "(IIZ)V")
	public static void method2573(@OriginalArg(1) int arg0, @OriginalArg(2) boolean arg1) {
		@Pc(8) Class4_Sub25 local8 = PlayerVarDomain.method2834(arg1, arg0);
		if (local8 != null) {
			local8.method5684();
		}
	}
@OriginalMember(owner = "client!hl", name = "a", descriptor = "(BI)V")
	public static void method2574(@OriginalArg(1) int arg0) {
		Class6.anInt5454 = arg0;
		@Pc(7) LRUCache local7 = Class4_Sub1_Sub6_Sub2.aClass68_24;
		synchronized (Class4_Sub1_Sub6_Sub2.aClass68_24) {
			Class4_Sub1_Sub6_Sub2.aClass68_24.method1777();
		}
	}
@OriginalMember(owner = "client!hl", name = "g", descriptor = "(B)Lclient!ie;")
	public static Class105 method2575() {
		try {
			return (Class105) Class.forName("com.jagex.game.runetek5.core.datastruct.ref.SoftReferenceFactory").getDeclaredConstructor().newInstance();
		} catch (@Pc(14) Throwable local14) {
			return null;
		}
	}
}
