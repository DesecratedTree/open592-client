package com.jagex.game.runetek5.config.struct;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.cache.node.lru.StructType;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.deob.Game;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteType;
import com.jagex.game.runetek5.graphics.texture.water.NativeWaterTexture;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.config.item.ItemType;
@OriginalClass("client!ff")
public final class StructTypeList {

	@OriginalMember(owner = "client!ff", name = "g", descriptor = "Lclient!fa;")
	private final LRUCache aClass68_10 = new LRUCache(256);

	@OriginalMember(owner = "client!ff", name = "e", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider aClass76_26;

	@OriginalMember(owner = "client!ff", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;)V")
	public StructTypeList(@OriginalArg(0) Game arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Js5ResourceProvider arg2) {
		this.aClass76_26 = arg2;
		this.aClass76_26.method2108(26);
	}

	@OriginalMember(owner = "client!ff", name = "b", descriptor = "(B)V")
	public void method1984() {
		@Pc(6) LRUCache local6 = this.aClass68_10;
		synchronized (this.aClass68_10) {
			this.aClass68_10.method1777();
		}
	}

	@OriginalMember(owner = "client!ff", name = "a", descriptor = "(ZI)V")
	public void method1985() {
		@Pc(10) LRUCache local10 = this.aClass68_10;
		synchronized (this.aClass68_10) {
			this.aClass68_10.method1788(5);
		}
	}

	@OriginalMember(owner = "client!ff", name = "a", descriptor = "(I)V")
	public void method1987() {
		@Pc(10) LRUCache local10 = this.aClass68_10;
		synchronized (this.aClass68_10) {
			this.aClass68_10.method1791();
		}
	}

	@OriginalMember(owner = "client!ff", name = "b", descriptor = "(II)Lclient!rk;")
	public StructType method1988(@OriginalArg(0) int arg0) {
		@Pc(6) LRUCache local6 = this.aClass68_10;
		@Pc(16) StructType local16;
		synchronized (this.aClass68_10) {
			local16 = (StructType) this.aClass68_10.method1787((long) arg0);
		}
		if (local16 != null) {
			return local16;
		}
		@Pc(35) byte[] local35 = this.aClass76_26.method2104(arg0, 26);
		local16 = new StructType();
		if (local35 != null) {
			local16.method4668(new Packet(local35));
		}
		@Pc(51) LRUCache local51 = this.aClass68_10;
		synchronized (this.aClass68_10) {
			this.aClass68_10.method1779((long) arg0, local16);
			return local16;
		}
	}

@OriginalMember(owner = "client!ff", name = "m", descriptor = "[Lclient!qc;")
	public static Class191[] aClass191Array1;
@OriginalMember(owner = "client!ff", name = "c", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_95 = new Class22(39, 8);
@OriginalMember(owner = "client!ff", name = "k", descriptor = "Lclient!bf;")
	public static final Class21 aClass21_7 = new Class21(3, 7);
@OriginalMember(owner = "client!ff", name = "l", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_26 = new Class215(78, -1);
@OriginalMember(owner = "client!ff", name = "a", descriptor = "(II)Z")
	public static boolean method1982(@OriginalArg(1) int arg0) {
		if (arg0 == 18 || arg0 == 45 || arg0 == 23 || arg0 == 25 || arg0 == 1007) {
			return true;
		} else {
			return arg0 == 30 || arg0 == 1010;
		}
	}
@OriginalMember(owner = "client!ff", name = "a", descriptor = "(B)V")
	public static void method1983() {
		Class4_Sub7.anInt606 = 0;
		for (@Pc(13) int local13 = 0; local13 < 2048; local13++) {
			ItemType.aClass4_Sub12Array1[local13] = null;
			Static411.aByteArray95[local13] = 1;
			Class183.aClass253Array1[local13] = null;
		}
	}
@OriginalMember(owner = "client!ff", name = "c", descriptor = "(B)V")
	public static void method1986() {
		MapSpriteType.aClass4_Sub15_Sub1_1.method1440();
		Class21.anInt461 = 1;
		NativeWaterTexture.aClass76_49 = null;
	}
}
