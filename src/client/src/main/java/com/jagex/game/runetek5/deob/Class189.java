package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;


import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.graphics.font.FontMetrics;
import com.jagex.game.runetek5.QuickChatPhrase;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.deob.Class70_Sub1;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.config.param.ParamTypeList;
import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
@OriginalClass("client!pr")
public final class Class189 {

	static {
		new Class83(null, "bevor du die Option 'Regelverstoß melden' benutzt.", null, null);
	}

@OriginalMember(owner = "client!pr", name = "e", descriptor = "[[[I")
	public static int[][][] anIntArrayArrayArray12;
@OriginalMember(owner = "client!pr", name = "f", descriptor = "I")
	public static int anInt5435;
@OriginalMember(owner = "client!pr", name = "g", descriptor = "I")
	public static int anInt5436;
@OriginalMember(owner = "client!pr", name = "c", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_70 = new Class215(6, 8);
@OriginalMember(owner = "client!pr", name = "d", descriptor = "[I")
	public static final int[] anIntArray374 = new int[2048];
@OriginalMember(owner = "client!pr", name = "a", descriptor = "(IIIIII)V")
	public static void method4175(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(5) int arg4) {
		Class133.anInt4125 = arg3;
		Class3_Sub3.anInt2379 = arg4;
		ParamTypeList.anInt6417 = arg0;
		StillWaterEffect.anInt6924 = arg2;
		CacheableNodeByteArray.anInt4509 = arg1;
		if (ParamTypeList.anInt6417 >= 100) {
			@Pc(30) int local30 = Class3_Sub3.anInt2379 * 128 + 64;
			@Pc(36) int local36 = StillWaterEffect.anInt6924 * 128 + 64;
			@Pc(45) int local45 = BufferedSocket.method135(local30, Class4_Sub6_Sub25.anInt4963, local36) - CacheableNodeByteArray.anInt4509;
			@Pc(50) int local50 = local30 - Class4_Sub23.anInt3197;
			@Pc(55) int local55 = local45 - TextureOpIrregularBricks.anInt2024;
			@Pc(59) int local59 = local36 - Static145.anInt2906;
			@Pc(70) int local70 = (int) Math.sqrt((double) (local59 * local59 + local50 * local50));
			QuickChatPhrase.anInt7074 = (int) (Math.atan2((double) local55, (double) local70) * 2607.5945876176133D) & 0x3FFF;
			FontMetrics.anInt2790 = (int) (Math.atan2((double) local50, (double) local59) * -2607.5945876176133D) & 0x3FFF;
			if (QuickChatPhrase.anInt7074 < 1024) {
				QuickChatPhrase.anInt7074 = 1024;
			}
			Class70_Sub1.anInt2070 = 0;
			if (QuickChatPhrase.anInt7074 > 3072) {
				QuickChatPhrase.anInt7074 = 3072;
			}
		}
		Class3_Sub3.anInt2386 = 2;
	}
@OriginalMember(owner = "client!pr", name = "a", descriptor = "(BI)V")
	public static void method4176(@OriginalArg(1) int arg0) {
		@Pc(13) Class4_Sub1_Sub11 local13 = NPCTypeList.method5222(arg0, 12);
		local13.method2608();
	}
}
