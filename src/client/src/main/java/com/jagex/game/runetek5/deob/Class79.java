package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.graphics.JavaBillboardFace;
import com.jagex.game.runetek5.deob.PositionedGfx;
import com.jagex.game.runetek5.cache.node.CacheableNodeQueue;
@OriginalClass("client!ge")
public abstract class Class79 {

	@OriginalMember(owner = "client!ge", name = "<init>", descriptor = "()V")
	protected Class79() {
	}

	@OriginalMember(owner = "client!ge", name = "a", descriptor = "(IFIIFIFFII[F)V")
	public abstract void method4866(@OriginalArg(0) int arg0, @OriginalArg(1) float arg1, @OriginalArg(4) float arg2, @OriginalArg(6) float arg3, @OriginalArg(7) float arg4, @OriginalArg(9) int arg5, @OriginalArg(10) float[] arg6);

@OriginalMember(owner = "client!ge", name = "a", descriptor = "[I")
	public static final int[] anIntArray442 = new int[4096];
@OriginalMember(owner = "client!ge", name = "b", descriptor = "I")
	public static int anInt6260 = 0;
@OriginalMember(owner = "client!ge", name = "d", descriptor = "I")
	public static int anInt6262 = 0;
@OriginalMember(owner = "client!ge", name = "a", descriptor = "(Ljava/lang/String;B)Ljava/lang/String;")
	public static String method4868(@OriginalArg(0) String arg0) {
		if (arg0 == null) {
			return null;
		}
		@Pc(16) int local16 = 0;
		@Pc(19) int local19 = arg0.length();
		while (local19 > local16 && CacheableNodeQueue.method3481(arg0.charAt(local16))) {
			local16++;
		}
		while (local19 > local16 && CacheableNodeQueue.method3481(arg0.charAt(local19 - 1))) {
			local19--;
		}
		@Pc(54) int local54 = local19 - local16;
		if (local54 < 1 || local54 > 12) {
			return null;
		}
		@Pc(71) StringBuffer local71 = new StringBuffer(local54);
		for (@Pc(73) int local73 = local16; local73 < local19; local73++) {
			@Pc(79) char local79 = arg0.charAt(local73);
			if (JavaBillboardFace.method3198(local79)) {
				@Pc(87) char local87 = PositionedGfx.method2459(local79);
				if (local87 != '\u0000') {
					local71.append(local87);
				}
			}
		}
		if (local71.length() == 0) {
			return null;
		} else {
			return local71.toString();
		}
	}
}
