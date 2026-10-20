package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.config.bas.BasType;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class83;
@OriginalClass("client!v")
public final class Class249 {

	@OriginalMember(owner = "client!v", name = "h", descriptor = "Lclient!fa;")
	private final LRUCache aClass68_52 = new LRUCache(64);

	@OriginalMember(owner = "client!v", name = "g", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider aClass76_97;

	@OriginalMember(owner = "client!v", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;)V")
	public Class249(@OriginalArg(0) Game arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Js5ResourceProvider arg2) {
		this.aClass76_97 = arg2;
		this.aClass76_97.method2108(32);
	}

	@OriginalMember(owner = "client!v", name = "a", descriptor = "(BI)V")
	public void method5322() {
		@Pc(2) LRUCache local2 = this.aClass68_52;
		synchronized (this.aClass68_52) {
			this.aClass68_52.method1788(5);
		}
	}

	@OriginalMember(owner = "client!v", name = "a", descriptor = "(I)V")
	public void method5324() {
		@Pc(7) LRUCache local7 = this.aClass68_52;
		synchronized (this.aClass68_52) {
			this.aClass68_52.method1791();
		}
	}

	@OriginalMember(owner = "client!v", name = "a", descriptor = "(ZI)Lclient!mp;")
	public BasType method5325(@OriginalArg(1) int arg0) {
		@Pc(6) LRUCache local6 = this.aClass68_52;
		@Pc(16) BasType local16;
		synchronized (this.aClass68_52) {
			local16 = (BasType) this.aClass68_52.method1787((long) arg0);
		}
		if (local16 != null) {
			return local16;
		}
		@Pc(41) byte[] local41 = this.aClass76_97.method2104(arg0, 32);
		local16 = new BasType();
		if (local41 != null) {
			local16.method3638(new Packet(local41));
		}
		@Pc(57) LRUCache local57 = this.aClass68_52;
		synchronized (this.aClass68_52) {
			this.aClass68_52.method1779((long) arg0, local16);
			return local16;
		}
	}

	@OriginalMember(owner = "client!v", name = "c", descriptor = "(I)V")
	public void method5327() {
		@Pc(2) LRUCache local2 = this.aClass68_52;
		synchronized (this.aClass68_52) {
			this.aClass68_52.method1777();
		}
	}

@OriginalMember(owner = "client!v", name = "k", descriptor = "F")
	public static float aFloat213;
@OriginalMember(owner = "client!v", name = "e", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_143 = new Class83("white:", "weiss:", "blanc:", "branco:");
@OriginalMember(owner = "client!v", name = "i", descriptor = "Z")
	public static final boolean aBoolean625 = false;
@OriginalMember(owner = "client!v", name = "j", descriptor = "Z")
	public static boolean aBoolean626 = false;
@OriginalMember(owner = "client!v", name = "a", descriptor = "(BLjava/lang/String;)V")
	public static void method5323(@OriginalArg(1) String arg0) {
		Class83.method2268(0, 0, arg0, "", "");
	}
}
