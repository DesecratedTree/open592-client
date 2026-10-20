package com.jagex.game.runetek5.cache.node.lru;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.cache.node.lru.*;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.deob.OutputStream_Sub1;
import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.graphics.texture.OpenGl2DImageTexture;
@OriginalClass("client!ac")
public class CacheableNode extends Country {

	@OriginalMember(owner = "client!ac", name = "o", descriptor = "J")
	public long indexReference;

	@OriginalMember(owner = "client!ac", name = "r", descriptor = "Lclient!ac;")
	public CacheableNode aClass4_Sub1_55;

	@OriginalMember(owner = "client!ac", name = "s", descriptor = "Lclient!ac;")
	public CacheableNode aClass4_Sub1_56;

	@OriginalMember(owner = "client!ac", name = "c", descriptor = "(B)V")
	public final void method4662() {
		if (this.aClass4_Sub1_56 != null) {
			this.aClass4_Sub1_56.aClass4_Sub1_55 = this.aClass4_Sub1_55;
			this.aClass4_Sub1_55.aClass4_Sub1_56 = this.aClass4_Sub1_56;
			this.aClass4_Sub1_55 = null;
			this.aClass4_Sub1_56 = null;
		}
	}

	@OriginalMember(owner = "client!ac", name = "a", descriptor = "(Z)Z")
	public final boolean method4666() {
		return this.aClass4_Sub1_56 != null;
	}

@OriginalMember(owner = "client!ac", name = "q", descriptor = "Lclient!ya;")
	public static com.jagex.game.runetek5.graphics.Toolkit aClass19_34;
@OriginalMember(owner = "client!ac", name = "t", descriptor = "I")
	public static int anInt5935;
@OriginalMember(owner = "client!ac", name = "n", descriptor = "[I")
	public static final int[] anIntArray415 = new int[] { 4, 2, 1, 1, 2, 2, 3, 1, 3, 3, 3, 2, 0 };
@OriginalMember(owner = "client!ac", name = "w", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_277 = new Class22(81, -2);
@OriginalMember(owner = "client!ac", name = "x", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_121 = new Class83("rating: ", "Kampfstufe: ", "classement ", "qualificação: ");
@OriginalMember(owner = "client!ac", name = "a", descriptor = "(II)V")
	public static void method4663(@OriginalArg(0) int arg0) {
		Class9.anInt6920 = -1;
		OutputStream_Sub1.anInt2674 = -1;
		OpenGl2DImageTexture.anInt5471 = arg0;
		JavaMatrix.method5160();
	}
@OriginalMember(owner = "client!ac", name = "d", descriptor = "(B)V")
	public static void method4664() {
		@Pc(5) LRUCache local5 = NPCType.aClass68_58;
		synchronized (NPCType.aClass68_58) {
			NPCType.aClass68_58.method1791();
		}
		local5 = Class4_Sub6_Sub15.aClass68_20;
		synchronized (Class4_Sub6_Sub15.aClass68_20) {
			Class4_Sub6_Sub15.aClass68_20.method1791();
		}
	}
}
