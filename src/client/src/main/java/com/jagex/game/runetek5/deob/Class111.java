package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;

import com.jagex.game.runetek5.cache.node.lru.LRUCache;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class70;
@OriginalClass("client!iq")
public final class Class111 {

	@OriginalMember(owner = "client!iq", name = "d", descriptor = "I")
	public int anInt3365 = 0;

	@OriginalMember(owner = "client!iq", name = "a", descriptor = "(Lclient!iv;Z)V")
	public void method2810(@OriginalArg(0) Packet arg0) {
		while (true) {
			@Pc(13) int local13 = arg0.method2490();
			if (local13 == 0) {
				return;
			}
			this.method2811(arg0, local13);
		}
	}

	@OriginalMember(owner = "client!iq", name = "a", descriptor = "(Lclient!iv;II)V")
	private void method2811(@OriginalArg(0) Packet arg0, @OriginalArg(1) int arg1) {
		if (arg1 == 5) {
			this.anInt3365 = arg0.method2536();
		}
	}

@OriginalMember(owner = "client!iq", name = "g", descriptor = "I")
	public static int anInt3367;
@OriginalMember(owner = "client!iq", name = "b", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_150 = new Class22(46, -1);
@OriginalMember(owner = "client!iq", name = "e", descriptor = "Z")
	public static boolean aBoolean282 = false;
@OriginalMember(owner = "client!iq", name = "f", descriptor = "I")
	public static final int anInt3366 = 12;
@OriginalMember(owner = "client!iq", name = "h", descriptor = "[I")
	public static final int[] anIntArray218 = new int[] { 7, 8, 9, 10, 11, 12, 13, 15 };
@OriginalMember(owner = "client!iq", name = "i", descriptor = "I")
	public static int anInt3368 = 0;
@OriginalMember(owner = "client!iq", name = "j", descriptor = "Lclient!fa;")
	public static final LRUCache aClass68_18 = new LRUCache(10);
@OriginalMember(owner = "client!iq", name = "a", descriptor = "(III)V")
	public static void method2809(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		@Pc(20) boolean local20 = Class70.aClass164ArrayArrayArray5[0][arg1][arg2] != null && Class70.aClass164ArrayArrayArray5[0][arg1][arg2].aClass164_1 != null;
		for (@Pc(22) int local22 = arg0; local22 >= 0; local22--) {
			if (Class70.aClass164ArrayArrayArray5[local22][arg1][arg2] == null) {
				@Pc(46) Class164 local46 = Class70.aClass164ArrayArrayArray5[local22][arg1][arg2] = new Class164(local22, arg1, arg2);
				if (local20) {
					local46.aByte53++;
				}
			}
		}
	}
}
