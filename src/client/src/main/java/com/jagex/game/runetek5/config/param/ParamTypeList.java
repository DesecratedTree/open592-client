package com.jagex.game.runetek5.config.param;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.Class58;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import java.awt.Font;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.config.param.*;
import com.jagex.game.runetek5.deob.Class70;
@OriginalClass("client!tf")
public final class ParamTypeList {

	@OriginalMember(owner = "client!tf", name = "j", descriptor = "Lclient!fa;")
	private final LRUCache aClass68_46 = new LRUCache(16);

	@OriginalMember(owner = "client!tf", name = "e", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider paramResource;

	@OriginalMember(owner = "client!tf", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;)V")
	public ParamTypeList(@OriginalArg(0) Game modeGame, @OriginalArg(1) int languageId, @OriginalArg(2) Js5ResourceProvider paramResource) {
		this.paramResource = paramResource;
		this.paramResource.method2108(30);
	}

	@OriginalMember(owner = "client!tf", name = "a", descriptor = "(B)V")
	public void method5020() {
		@Pc(10) LRUCache local10 = this.aClass68_46;
		synchronized (this.aClass68_46) {
			this.aClass68_46.method1777();
		}
	}

	@OriginalMember(owner = "client!tf", name = "b", descriptor = "(I)V")
	public void method5022() {
		@Pc(11) LRUCache local11 = this.aClass68_46;
		synchronized (this.aClass68_46) {
			this.aClass68_46.method1791();
		}
	}

	@OriginalMember(owner = "client!tf", name = "a", descriptor = "(BI)V")
	public void method5024() {
		@Pc(2) LRUCache local2 = this.aClass68_46;
		synchronized (this.aClass68_46) {
			this.aClass68_46.method1788(5);
		}
	}

	@OriginalMember(owner = "client!tf", name = "a", descriptor = "(II)Lclient!eb;")
	public Class58 method5025(@OriginalArg(1) int arg0) {
		@Pc(6) LRUCache local6 = this.aClass68_46;
		@Pc(16) Class58 local16;
		synchronized (this.aClass68_46) {
			local16 = (Class58) this.aClass68_46.method1787((long) arg0);
		}
		if (local16 != null) {
			return local16;
		}
		@Pc(38) byte[] local38 = this.paramResource.method2104(arg0, 30);
		local16 = new Class58();
		if (local38 != null) {
			local16.method1509(new Packet(local38));
		}
		@Pc(54) LRUCache local54 = this.aClass68_46;
		synchronized (this.aClass68_46) {
			this.aClass68_46.method1779((long) arg0, local16);
			return local16;
		}
	}

@OriginalMember(owner = "client!tf", name = "a", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_87;
@OriginalMember(owner = "client!tf", name = "d", descriptor = "I")
	public static int anInt6417;
@OriginalMember(owner = "client!tf", name = "f", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_89;
@OriginalMember(owner = "client!tf", name = "h", descriptor = "Ljava/awt/Font;")
	public static Font aFont1;
@OriginalMember(owner = "client!tf", name = "b", descriptor = "[Ljava/lang/String;")
	public static final String[] aStringArray35 = new String[5];
@OriginalMember(owner = "client!tf", name = "g", descriptor = "I")
	public static int anInt6418 = 0;
@OriginalMember(owner = "client!tf", name = "i", descriptor = "I")
	public static final int anInt6419 = 2;
@OriginalMember(owner = "client!tf", name = "a", descriptor = "(III)Lclient!ii;")
	public static Class16_Sub2 method5023(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		@Pc(7) Class164 local7 = Class70.aClass164ArrayArrayArray5[arg0][arg1][arg2];
		return local7 == null ? null : local7.aClass16_Sub2_3;
	}
}
