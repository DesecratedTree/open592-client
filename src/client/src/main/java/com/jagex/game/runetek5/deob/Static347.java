package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class16_Sub1_Sub1;
import com.jagex.game.runetek5.entity.pathingentity.EasedAngle;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.deob.Class4_Sub25;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
public final class Static347 {

	@OriginalMember(owner = "client!rm", name = "a", descriptor = "[J")
	public static final long[] aLongArray8 = new long[32];

	@OriginalMember(owner = "client!rm", name = "a", descriptor = "(ZZZ)V")
	public static void method4678(@OriginalArg(0) boolean arg0, @OriginalArg(2) boolean arg1) {
		if (arg0) {
			Class4_Sub25.anInt3550++;
			CacheableNodeByteArray.method3543();
		}
		if (arg1) {
			EasedAngle.anInt855++;
			Class16_Sub1_Sub1.method293();
		}
	}

	@OriginalMember(owner = "client!rm", name = "a", descriptor = "(ZLjava/lang/String;)Ljava/lang/String;")
	public static String method4680(@OriginalArg(1) String arg0) {
		@Pc(16) String local16 = Static45.method766(Class4_Sub6_Sub7.method1684(arg0));
		if (local16 == null) {
			local16 = "";
		}
		return local16;
	}
}
