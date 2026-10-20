package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.js5.request.Js5WorkerRequestMessage;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.deob.Class217;
@OriginalClass("client!lf")
public final class Class4_Sub1_Sub6_Sub2 extends Js5WorkerRequestMessage {

	@OriginalMember(owner = "client!lf", name = "G", descriptor = "Lclient!st;")
	public Class222 aClass222_1;

	@OriginalMember(owner = "client!lf", name = "H", descriptor = "[B")
	public byte[] aByteArray50;

	@OriginalMember(owner = "client!lf", name = "K", descriptor = "I")
	public int anInt4117;

	static {
		new Class83("You have been permanently muted due to breaking a rule.", "Du wurdest permanent stumm geschaltet, da du gegen eine Regel verstoßen hast.", "L'accès à la messagerie instantanée vous a définitivement été retiré suite à une infraction.", "Você foi permanentemente vetado por ter violado uma regra.");
	}

	@OriginalMember(owner = "client!lf", name = "b", descriptor = "(Z)[B")
	@Override
	public byte[] getData() {
		if (super.waiting) {
			throw new RuntimeException();
		}
		return this.aByteArray50;
	}

	@OriginalMember(owner = "client!lf", name = "a", descriptor = "(I)I")
	@Override
	public int completePercentage() {
		return super.waiting ? 0 : 100;
	}

@OriginalMember(owner = "client!lf", name = "J", descriptor = "Lclient!fa;")
	public static final LRUCache aClass68_24 = new LRUCache(32);
@OriginalMember(owner = "client!lf", name = "L", descriptor = "Ljava/lang/String;")
	public static String aString39 = null;
@OriginalMember(owner = "client!lf", name = "a", descriptor = "(Lclient!fs;I)V")
	public static void method3346(@OriginalArg(0) Js5ResourceProvider arg0) {
		LocType.anInt5846 = arg0.method2099("titlebg");
		Class217.anInt6320 = arg0.method2099("logo");
	}
@OriginalMember(owner = "client!lf", name = "a", descriptor = "(I[SI)[S")
	public static short[] method3347(@OriginalArg(1) short[] arg0, @OriginalArg(2) int arg1) {
		@Pc(6) short[] local6 = new short[arg1];
		Static459.copy(arg0, 0, local6, 0, arg1);
		return local6;
	}
}
