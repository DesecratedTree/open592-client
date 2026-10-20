package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.network.packet.ServerProt;

import com.jagex.game.runetek5.deob.*;
@OriginalClass("client!rl")
public final class Class204 {

	@OriginalMember(owner = "client!rl", name = "a", descriptor = "Lclient!fa;")
	private final LRUCache aClass68_39 = new LRUCache(64);

	@OriginalMember(owner = "client!rl", name = "j", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider aClass76_76;

	@OriginalMember(owner = "client!rl", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;)V")
	public Class204(@OriginalArg(0) Game arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Js5ResourceProvider arg2) {
		this.aClass76_76 = arg2;
		this.aClass76_76.method2108(31);
	}

	@OriginalMember(owner = "client!rl", name = "a", descriptor = "(I)V")
	public void method4672() {
		@Pc(6) LRUCache local6 = this.aClass68_39;
		synchronized (this.aClass68_39) {
			this.aClass68_39.method1777();
		}
	}

	@OriginalMember(owner = "client!rl", name = "a", descriptor = "(Z)V")
	public void method4673() {
		@Pc(2) LRUCache local2 = this.aClass68_39;
		synchronized (this.aClass68_39) {
			this.aClass68_39.method1791();
		}
	}

	@OriginalMember(owner = "client!rl", name = "a", descriptor = "(BI)V")
	public void method4676() {
		@Pc(2) LRUCache local2 = this.aClass68_39;
		synchronized (this.aClass68_39) {
			this.aClass68_39.method1788(5);
		}
	}

	@OriginalMember(owner = "client!rl", name = "a", descriptor = "(II)Lclient!pu;")
	public Class190 method4677(@OriginalArg(0) int arg0) {
		@Pc(15) LRUCache local15 = this.aClass68_39;
		@Pc(25) Class190 local25;
		synchronized (this.aClass68_39) {
			local25 = (Class190) this.aClass68_39.method1787((long) arg0);
		}
		if (local25 != null) {
			return local25;
		}
		@Pc(42) byte[] local42 = this.aClass76_76.method2104(arg0, 31);
		local25 = new Class190();
		if (local42 != null) {
			local25.method4218(new Packet(local42));
		}
		@Pc(58) LRUCache local58 = this.aClass68_39;
		synchronized (this.aClass68_39) {
			this.aClass68_39.method1779((long) arg0, local25);
			return local25;
		}
	}

@OriginalMember(owner = "client!rl", name = "g", descriptor = "[[B")
	public static byte[][] aByteArrayArray26;
@OriginalMember(owner = "client!rl", name = "l", descriptor = "[I")
	public static int[] anIntArray418;
@OriginalMember(owner = "client!rl", name = "d", descriptor = "Lclient!jc;")
	public static final ServerProt aClass117_6 = new ServerProt("RC", 1);
@OriginalMember(owner = "client!rl", name = "i", descriptor = "I")
	public static int anInt5948 = 0;
@OriginalMember(owner = "client!rl", name = "k", descriptor = "[I")
	public static final int[] anIntArray417 = new int[] { 0, -1, 0, 1 };
@OriginalMember(owner = "client!rl", name = "a", descriptor = "(BILclient!f;I)J")
	public static long method4675(@OriginalArg(1) int arg0, @OriginalArg(2) GameObject arg1, @OriginalArg(3) int arg2) {
		@Pc(5) long local5 = 4194304L;
		@Pc(7) long local7 = 2147483648L;
		@Pc(9) long local9 = Long.MIN_VALUE;
		@Pc(16) LocType local16 = Static267.aClass262_2.method5560(arg1.method5350());
		@Pc(37) long local37 = (long) ((arg2 | 0x800000) << 7 | arg0 | arg1.method5354() << 14 | arg1.method5347() << 20);
		if (local16.anInt5813 == 0) {
			local37 |= local9;
		}
		if (local16.anInt5818 == 1) {
			local37 |= local5;
		}
		if (local16.aBoolean529) {
			local37 |= local7;
		}
		return local37 | (long) arg1.method5350() << 32;
	}
}
