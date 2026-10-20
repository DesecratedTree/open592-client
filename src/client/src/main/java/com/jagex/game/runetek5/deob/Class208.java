package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.config.struct.StructTypeList;
@OriginalClass("client!rq")
public final class Class208 {

	@OriginalMember(owner = "client!rq", name = "e", descriptor = "Lclient!fa;")
	private final LRUCache aClass68_42 = new LRUCache(64);

	@OriginalMember(owner = "client!rq", name = "m", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider aClass76_80;

	@OriginalMember(owner = "client!rq", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;)V")
	public Class208(@OriginalArg(0) Game arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Js5ResourceProvider arg2) {
		this.aClass76_80 = arg2;
		if (this.aClass76_80 != null) {
			this.aClass76_80.method2108(35);
		}
	}

	@OriginalMember(owner = "client!rq", name = "a", descriptor = "(B)V")
	public void method4721() {
		@Pc(6) LRUCache local6 = this.aClass68_42;
		synchronized (this.aClass68_42) {
			this.aClass68_42.method1777();
		}
	}

	@OriginalMember(owner = "client!rq", name = "a", descriptor = "(Z)V")
	public void method4722() {
		@Pc(2) LRUCache local2 = this.aClass68_42;
		synchronized (this.aClass68_42) {
			this.aClass68_42.method1791();
		}
	}

	@OriginalMember(owner = "client!rq", name = "a", descriptor = "(II)V")
	public void method4723() {
		@Pc(10) LRUCache local10 = this.aClass68_42;
		synchronized (this.aClass68_42) {
			this.aClass68_42.method1788(5);
		}
	}

	@OriginalMember(owner = "client!rq", name = "a", descriptor = "(BI)Lclient!hv;")
	public Class101 method4725(@OriginalArg(1) int arg0) {
		@Pc(6) LRUCache local6 = this.aClass68_42;
		@Pc(16) Class101 local16;
		synchronized (this.aClass68_42) {
			local16 = (Class101) this.aClass68_42.method1787((long) arg0);
		}
		if (local16 != null) {
			return local16;
		}
		@Pc(33) byte[] local33 = this.aClass76_80.method2104(arg0, 35);
		local16 = new Class101();
		if (local33 != null) {
			local16.method2679(new Packet(local33));
		}
		local16.method2676();
		@Pc(57) LRUCache local57 = this.aClass68_42;
		synchronized (this.aClass68_42) {
			this.aClass68_42.method1779((long) arg0, local16);
			return local16;
		}
	}

@OriginalMember(owner = "client!rq", name = "h", descriptor = "F")
	public static float aFloat187;
@OriginalMember(owner = "client!rq", name = "j", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_79;
@OriginalMember(owner = "client!rq", name = "b", descriptor = "Z")
	public static boolean aBoolean556 = true;
@OriginalMember(owner = "client!rq", name = "c", descriptor = "[I")
	public static final int[] anIntArray419 = new int[14];
@OriginalMember(owner = "client!rq", name = "d", descriptor = "[B")
	public static final byte[] aByteArray83 = new byte[] { 0, 0, 2, 0, 0, 2, 1, 1, 0 };
@OriginalMember(owner = "client!rq", name = "g", descriptor = "[I")
	public static final int[] anIntArray420 = new int[6];
@OriginalMember(owner = "client!rq", name = "a", descriptor = "(Lclient!jj;IIIII)V")
	public static void method4720(@OriginalArg(0) Class120 arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5) {
		arg0.anInt5351 = 0;
		for (@Pc(4) int local4 = 0; local4 < 4; local4++) {
			if (arg0.aClass4_Sub21Array3[local4] != null) {
				arg0.anInt5351++;
			}
		}
		label51: for (@Pc(22) int local22 = 0; local22 < arg0.anInt5351; local22++) {
			@Pc(31) long local31 = Terrain.aLongArrayArrayArray2[arg1][arg2][arg3];
			@Pc(41) Class191 local41;
			while (local31 != 0L) {
				local41 = StructTypeList.aClass191Array1[(int) ((local31 & 0xFFFFL) - 1L)];
				local31 >>>= 0x10;
				if (local41.aClass4_Sub21_2 == arg0.aClass4_Sub21Array3[local22]) {
					continue label51;
				}
			}
			local31 = Terrain.aLongArrayArrayArray2[arg1][arg4][arg5];
			while (local31 != 0L) {
				local41 = StructTypeList.aClass191Array1[(int) ((local31 & 0xFFFFL) - 1L)];
				local31 >>>= 0x10;
				if (local41.aClass4_Sub21_2 == arg0.aClass4_Sub21Array3[local22]) {
					continue label51;
				}
			}
			for (@Pc(93) int local93 = local22; local93 < arg0.anInt5351 - 1; local93++) {
				arg0.aClass4_Sub21Array3[local93] = arg0.aClass4_Sub21Array3[local93 + 1];
			}
			arg0.anInt5351--;
		}
	}
@OriginalMember(owner = "client!rq", name = "a", descriptor = "(Ljava/lang/Throwable;Ljava/lang/String;)Lclient!mn;")
	public static RuntimeException_Sub1 method4724(@OriginalArg(0) Throwable arg0, @OriginalArg(1) String arg1) {
		@Pc(9) RuntimeException_Sub1 local9;
		if (arg0 instanceof RuntimeException_Sub1) {
			local9 = (RuntimeException_Sub1) arg0;
			local9.aString46 = local9.aString46 + ' ' + arg1;
		} else {
			local9 = new RuntimeException_Sub1(arg0, arg1);
		}
		return local9;
	}
}
