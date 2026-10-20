package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.deob.ReflectingTextureEffect;
@OriginalClass("client!ic")
public final class Class103 {

	@OriginalMember(owner = "client!ic", name = "b", descriptor = "I")
	public int anInt3173;

	@OriginalMember(owner = "client!ic", name = "c", descriptor = "I")
	public int anInt3174;

	@OriginalMember(owner = "client!ic", name = "e", descriptor = "I")
	public int anInt3175;

	@OriginalMember(owner = "client!ic", name = "f", descriptor = "I")
	public int anInt3176;

@OriginalMember(owner = "client!ic", name = "a", descriptor = "B")
	public static byte aByte18;
@OriginalMember(owner = "client!ic", name = "d", descriptor = "[I")
	public static final int[] anIntArray207 = new int[25];
@OriginalMember(owner = "client!ic", name = "a", descriptor = "(IIIILclient!ua;)V")
	public static void method2710(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) Class16_Sub3 arg4) {
		@Pc(4) Class164 local4 = AbstractArbBufferGL.method5175(arg0, arg1, arg2);
		if (local4 == null) {
			return;
		}
		@Pc(8) int local8 = 0;
		arg4.anInt744 = (arg1 << Static231.anInt4434) + ReflectingTextureEffect.anInt5747;
		arg4.anInt739 = arg3;
		arg4.anInt737 = (arg2 << Static231.anInt4434) + ReflectingTextureEffect.anInt5747;
		for (@Pc(28) Class7 local28 = local4.aClass7_3; local28 != null; local28 = local28.aClass7_1) {
			if (local28.aClass16_Sub1_1.aBoolean629) {
				@Pc(38) int local38 = local28.aClass16_Sub1_1.method5342();
				if (local38 != -32768 && local38 < local8) {
					local8 = local38;
				}
			}
		}
		if (local8 < 0) {
			arg4.anInt739 += local8;
			arg4.aBoolean73 = true;
		}
		local4.aClass16_Sub3_1 = arg4;
	}
}
