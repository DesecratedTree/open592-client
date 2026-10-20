package com.jagex.game.runetek5.config.flu;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.FluType;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.deob.Static231;

import com.jagex.game.runetek5.config.flu.*;
import com.jagex.game.runetek5.deob.Class70;
@OriginalClass("client!qd")
public final class FluTypeList {

	@OriginalMember(owner = "client!qd", name = "a", descriptor = "Lclient!fa;")
	private final LRUCache aClass68_35 = new LRUCache(128);

	@OriginalMember(owner = "client!qd", name = "b", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider underlayResource;

	static {
		new Class83("To prevent further mutes please read the rules.", "Um eine erneute Stummschaltung zu verhindern, lies bitte die Regeln.", "Pour éviter un nouveau blocage, lisez le règlement.", "Para evitar outros vetos, leia as regras.");
	}

	@OriginalMember(owner = "client!qd", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;)V")
	public FluTypeList(@OriginalArg(0) Game modeGame, @OriginalArg(1) int languageId, @OriginalArg(2) Js5ResourceProvider underlayResource) {
		this.underlayResource = underlayResource;
		this.underlayResource.method2108(1);
	}

	@OriginalMember(owner = "client!qd", name = "a", descriptor = "(BI)V")
	public void method4339() {
		@Pc(14) LRUCache local14 = this.aClass68_35;
		synchronized (this.aClass68_35) {
			this.aClass68_35.method1788(5);
		}
	}

	@OriginalMember(owner = "client!qd", name = "a", descriptor = "(II)Lclient!sr;")
	public FluType method4340(@OriginalArg(1) int arg0) {
		@Pc(6) LRUCache local6 = this.aClass68_35;
		@Pc(16) FluType local16;
		synchronized (this.aClass68_35) {
			local16 = (FluType) this.aClass68_35.method1787((long) arg0);
		}
		if (local16 != null) {
			return local16;
		}
		@Pc(33) byte[] local33 = this.underlayResource.method2104(arg0, 1);
		local16 = new FluType();
		if (local33 != null) {
			local16.method4960(new Packet(local33));
		}
		@Pc(55) LRUCache local55 = this.aClass68_35;
		synchronized (this.aClass68_35) {
			this.aClass68_35.method1779((long) arg0, local16);
			return local16;
		}
	}

	@OriginalMember(owner = "client!qd", name = "a", descriptor = "(I)V")
	public void method4342() {
		@Pc(2) LRUCache local2 = this.aClass68_35;
		synchronized (this.aClass68_35) {
			this.aClass68_35.method1777();
		}
	}

	@OriginalMember(owner = "client!qd", name = "b", descriptor = "(I)V")
	public void method4343() {
		@Pc(2) LRUCache local2 = this.aClass68_35;
		synchronized (this.aClass68_35) {
			this.aClass68_35.method1791();
		}
	}

@OriginalMember(owner = "client!qd", name = "g", descriptor = "I")
	public static int anInt5579;
@OriginalMember(owner = "client!qd", name = "a", descriptor = "(IIII)V")
	public static void method4341(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		@Pc(7) Class164 local7 = Class70.aClass164ArrayArrayArray5[arg0][arg1][arg2];
		if (local7 == null) {
			return;
		}
		@Pc(13) Class16_Sub2 local13 = local7.aClass16_Sub2_2;
		@Pc(16) Class16_Sub2 local16 = local7.aClass16_Sub2_3;
		if (local13 != null) {
			local13.anInt4550 = local13.anInt4550 * arg3 / (0x10 << Static231.anInt4434 - 7);
			local13.anInt4551 = local13.anInt4551 * arg3 / (0x10 << Static231.anInt4434 - 7);
		}
		if (local16 != null) {
			local16.anInt4550 = local16.anInt4550 * arg3 / (0x10 << Static231.anInt4434 - 7);
			local16.anInt4551 = local16.anInt4551 * arg3 / (0x10 << Static231.anInt4434 - 7);
		}
	}
}
