package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class269;
@OriginalClass("client!fp")
public final class Class75 {

	@OriginalMember(owner = "client!fp", name = "b", descriptor = "[I")
	public static final int[] anIntArray163 = new int[4096];

	@OriginalMember(owner = "client!fp", name = "f", descriptor = "Lclient!fa;")
	private final LRUCache aClass68_11 = new LRUCache(64);

	@OriginalMember(owner = "client!fp", name = "a", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider aClass76_28;

	static {
		for (@Pc(4) int local4 = 0; local4 < 4096; local4++) {
			anIntArray163[local4] = Class269.method5662(local4);
		}
	}

	@OriginalMember(owner = "client!fp", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;)V")
	public Class75(@OriginalArg(0) Game arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Js5ResourceProvider arg2) {
		this.aClass76_28 = arg2;
		this.aClass76_28.method2108(5);
	}

	@OriginalMember(owner = "client!fp", name = "a", descriptor = "(II)Lclient!an;")
	public Class4_Sub1_Sub2 method2079(@OriginalArg(1) int arg0) {
		@Pc(6) LRUCache local6 = this.aClass68_11;
		@Pc(16) Class4_Sub1_Sub2 local16;
		synchronized (this.aClass68_11) {
			local16 = (Class4_Sub1_Sub2) this.aClass68_11.method1787((long) arg0);
		}
		if (local16 != null) {
			return local16;
		}
		@Pc(33) byte[] local33 = this.aClass76_28.method2104(arg0, 5);
		local16 = new Class4_Sub1_Sub2();
		if (local33 != null) {
			local16.method154(new Packet(local33));
		}
		@Pc(51) LRUCache local51 = this.aClass68_11;
		synchronized (this.aClass68_11) {
			this.aClass68_11.method1779((long) arg0, local16);
			return local16;
		}
	}

@OriginalMember(owner = "client!fp", name = "c", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_31 = new Class215(29, 7);
@OriginalMember(owner = "client!fp", name = "e", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_102 = new Class22(32, -2);
@OriginalMember(owner = "client!fp", name = "g", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_42 = new Class83("glow2:", "leuchten2:", "brillant2:", "brilho2:");
@OriginalMember(owner = "client!fp", name = "h", descriptor = "[I")
	public static final int[] anIntArray164 = new int[13];
@OriginalMember(owner = "client!fp", name = "i", descriptor = "Lclient!pk;")
	public static Class183 aClass183_15 = new Class183();
}
