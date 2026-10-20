package com.jagex.game.runetek5.config.enumtype;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.deob.Static167;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.HelveticaFont;
import com.jagex.game.runetek5.deob.Class22;

import com.jagex.game.runetek5.config.enumtype.*;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
@OriginalClass("client!ng")
public final class EnumTypeList {

	@OriginalMember(owner = "client!ng", name = "g", descriptor = "Lclient!fa;")
	private final LRUCache cache = new LRUCache(128);

	@OriginalMember(owner = "client!ng", name = "e", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider enumTypeResource;

	@OriginalMember(owner = "client!ng", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;)V")
	public EnumTypeList(@OriginalArg(0) Game modeGame, @OriginalArg(1) int languageId, @OriginalArg(2) Js5ResourceProvider enumTypeResource) {
		this.enumTypeResource = enumTypeResource;
		if (this.enumTypeResource != null) {
			@Pc(20) int local20 = this.enumTypeResource.method2100() - 1;
			this.enumTypeResource.method2108(local20);
		}
	}

	@OriginalMember(owner = "client!ng", name = "a", descriptor = "(II)Lclient!qu;")
	public EnumType method3790(@OriginalArg(0) int arg0) {
		@Pc(14) LRUCache local14 = this.cache;
		@Pc(24) EnumType local24;
		synchronized (this.cache) {
			local24 = (EnumType) this.cache.method1787((long) arg0);
		}
		if (local24 != null) {
			return local24;
		}
		@Pc(45) byte[] local45 = this.enumTypeResource.method2104(Static167.method2739(arg0), OpenGl2DTexture.method4190(arg0));
		local24 = new EnumType();
		if (local45 != null) {
			local24.method4534(new Packet(local45));
		}
		@Pc(61) LRUCache local61 = this.cache;
		synchronized (this.cache) {
			this.cache.method1779((long) arg0, local24);
			return local24;
		}
	}

@OriginalMember(owner = "client!ng", name = "c", descriptor = "Lclient!sb;")
	public static HelveticaFont aClass212_7;
@OriginalMember(owner = "client!ng", name = "d", descriptor = "I")
	public static int anInt4939;
@OriginalMember(owner = "client!ng", name = "b", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_219 = new Class22(38, 1);
@OriginalMember(owner = "client!ng", name = "f", descriptor = "I")
	public static final int anInt4940 = 205;
}
