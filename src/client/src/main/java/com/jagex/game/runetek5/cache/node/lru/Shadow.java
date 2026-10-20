package com.jagex.game.runetek5.cache.node.lru;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalArg;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.cache.node.lru.*;
@OriginalClass("client!ba")
public abstract class Shadow extends CacheableNode {

@OriginalMember(owner = "client!ba", name = "A", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_61;
@OriginalMember(owner = "client!ba", name = "y", descriptor = "Ljava/lang/String;")
	public static String aString53 = "";
@OriginalMember(owner = "client!ba", name = "z", descriptor = "Lclient!bg;")
	public static final Class22 categoryIterator = new Class22(40, 3);
@OriginalMember(owner = "client!ba", name = "C", descriptor = "I")
	public static int anInt5323 = 0;
@OriginalMember(owner = "client!ba", name = "D", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_111 = new Class83("Allocated memory", "Zugewiesener Speicher.", "Mémoire attribuée", "Memória alocada");
@OriginalMember(owner = "client!ba", name = "b", descriptor = "(II)I")
	public static int method4082(@OriginalArg(1) int arg0) {
		return arg0 & 0xFF;
	}
}
