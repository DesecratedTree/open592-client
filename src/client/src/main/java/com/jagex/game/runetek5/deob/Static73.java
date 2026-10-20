package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class145_Sub2;
import com.jagex.game.runetek5.deob.Class204;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
public final class Static73 {

	@OriginalMember(owner = "client!dn", name = "a", descriptor = "I")
	public static int anInt1520 = 0;

	@OriginalMember(owner = "client!dn", name = "a", descriptor = "(IIILclient!ii;Lclient!ii;)V")
	public static void method1356(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) Class16_Sub2 arg3, @OriginalArg(4) Class16_Sub2 arg4) {
		@Pc(4) Class164 local4 = AbstractArbBufferGL.method5175(arg0, arg1, arg2);
		if (local4 != null) {
			local4.aClass16_Sub2_2 = arg3;
			local4.aClass16_Sub2_3 = arg4;
		}
	}

	@OriginalMember(owner = "client!dn", name = "a", descriptor = "(Ljava/lang/String;I)I")
	public static int method1357(@OriginalArg(0) String arg0) {
		for (@Pc(7) int local7 = 0; local7 < Static258.aStringArray18.length; local7++) {
			if (Static258.aStringArray18[local7].equalsIgnoreCase(arg0)) {
				return local7;
			}
		}
		return -1;
	}

	@OriginalMember(owner = "client!dn", name = "a", descriptor = "(IIIII)V")
	public static void method1358(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4) {
		if (Class145_Sub2.anIntArrayArray64 != null) {
			Class145_Sub2.anIntArrayArray64[arg0][arg1] = arg2 | 0xFF000000;
		}
		if (Class204.aByteArrayArray26 != null) {
			Class204.aByteArrayArray26[arg0][arg1] = (byte) (arg3 >> 3);
		}
		if (AbstractArbBufferGL.aByteArrayArray29 != null) {
			AbstractArbBufferGL.aByteArrayArray29[arg0][arg1] = (byte) arg4;
		}
	}
}
