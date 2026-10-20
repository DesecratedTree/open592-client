package com.jagex.game.runetek5.client.loading.loader;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.QuickChatValueType;
import com.jagex.game.runetek5.client.loading.loader.*;
import com.jagex.game.runetek5.deob.SceneLocEntity;
@OriginalClass("client!qq")
public final class Js5GroupLoader {

	@OriginalMember(owner = "client!qq", name = "e", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider aClass76_74;

	@OriginalMember(owner = "client!qq", name = "g", descriptor = "I")
	public final int anInt5738;

	@OriginalMember(owner = "client!qq", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;)V")
	public Js5GroupLoader(@OriginalArg(0) Game arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Js5ResourceProvider arg2) {
		new LRUCache(64);
		this.aClass76_74 = arg2;
		this.anInt5738 = this.aClass76_74.method2108(15);
	}

@OriginalMember(owner = "client!qq", name = "b", descriptor = "F")
	public static float aFloat180;
@OriginalMember(owner = "client!qq", name = "c", descriptor = "Lclient!ho;")
	public static final QuickChatValueType aClass98_14 = new QuickChatValueType(14, 0, 4, 1);
@OriginalMember(owner = "client!qq", name = "d", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_269 = new Class22(49, 4);
@OriginalMember(owner = "client!qq", name = "a", descriptor = "(IIII)I")
	public static int method4519(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		if (arg2 > arg1) {
			return arg2;
		} else if (arg0 >= arg1) {
			return arg1;
		} else {
			return arg0;
		}
	}
@OriginalMember(owner = "client!qq", name = "a", descriptor = "(B)V")
	public static void method4520() {
		SceneLocEntity.aClass68_19.method1791();
	}
}
