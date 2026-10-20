package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;


import java.lang.reflect.Method;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.ChatFilter;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class145_Sub2;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub38;
import com.jagex.game.runetek5.deob.Class204;
import com.jagex.game.runetek5.toolkit.opengl.effect.BreakingWaterEffect;
import com.jagex.game.runetek5.deob.Class58;
import com.jagex.game.runetek5.deob.Class27_Sub4;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.deob.Class70_Sub2;
import com.jagex.game.runetek5.js5.request.FileWorker;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.graphics.model.GlModel;
@OriginalClass("client!qg")
public final class Class193 {

	static {
		new Class83("You appear to be telling someone your password - please don't!", "Willst du jemandem dein Passwort verraten? Das darfst du nicht! Falls das", "Il semble que vous révéliez votre mot de passe à quelqu'un - ne faites jamais ça !", "Parece que você está revelando sua senha a alguém. Não faça isso!");
	}

@OriginalMember(owner = "client!qg", name = "d", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_69;
@OriginalMember(owner = "client!qg", name = "a", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_261 = new Class22(7, 0);
@OriginalMember(owner = "client!qg", name = "e", descriptor = "Lclient!id;")
	public static final ChatFilter aClass104_3 = new ChatFilter(2);
@OriginalMember(owner = "client!qg", name = "f", descriptor = "I")
	public static int anInt5626 = 0;
@OriginalMember(owner = "client!qg", name = "a", descriptor = "(B)V")
	public static void method4384() {
		if (SignLink.anInt1987 == 2) {
			BreakingWaterEffect.anInt1503 = 96;
			return;
		}
		try {
			@Pc(26) Method local26 = Runtime.class.getMethod("maxMemory");
			if (local26 != null) {
				try {
					@Pc(30) Runtime local30 = Runtime.getRuntime();
					@Pc(36) Long local36 = (Long) local26.invoke(local30, (Object[]) null);
					BreakingWaterEffect.anInt1503 = (int) (local36 / 1048576L) + 1;
					return;
				} catch (@Pc(46) Throwable local46) {
					return;
				}
			}
		} catch (@Pc(48) Exception local48) {
			return;
		}
	}
@OriginalMember(owner = "client!qg", name = "a", descriptor = "(Lclient!pe;Z)Z")
	public static boolean method4385(@OriginalArg(0) Class16_Sub1 arg0, @OriginalArg(1) boolean arg1) {
		@Pc(6) boolean local6 = Class16_Sub1_Sub6.aClass65Array4 == FileWorker.aClass65Array3;
		@Pc(8) int local8 = 0;
		@Pc(10) byte local10 = 0;
		@Pc(12) byte local12 = 0;
		arg0.method5346();
		if (arg0.aShort94 < 0 || arg0.aShort96 < 0 || arg0.aShort97 >= GpuFaceNormal.menuOpenWidth || arg0.aShort95 >= GlModel.anInt3361) {
			return false;
		}
		@Pc(38) int local38;
		for (@Pc(34) int local34 = arg0.aShort94; local34 <= arg0.aShort97; local34++) {
			for (local38 = arg0.aShort96; local38 <= arg0.aShort95; local38++) {
				@Pc(45) Class164 local45 = AbstractArbBufferGL.method5175(arg0.aByte82, local34, local38);
				if (local45 != null) {
					@Pc(49) int local49 = 0;
					if (local34 > arg0.aShort94) {
						local49++;
					}
					if (local34 < arg0.aShort97) {
						local49 += 4;
					}
					if (local38 > arg0.aShort96) {
						local49 += 8;
					}
					if (local38 < arg0.aShort95) {
						local49 += 2;
					}
					@Pc(74) Class7 local74 = Class58.method1513(local49, arg0);
					@Pc(77) Class7 local77 = local45.aClass7_3;
					if (local77 == null) {
						local45.aClass7_3 = local74;
					} else {
						while (local77.aClass7_1 != null) {
							local77 = local77.aClass7_1;
						}
						local77.aClass7_1 = local74;
					}
					local45.aByte50 = (byte) (local45.aByte50 | local49);
					if (local6 && (Class145_Sub2.anIntArrayArray64[local34][local38] & 0xFF000000) != 0) {
						local8 = Class145_Sub2.anIntArrayArray64[local34][local38];
						local10 = Class204.aByteArrayArray26[local34][local38];
						local12 = AbstractArbBufferGL.aByteArrayArray29[local34][local38];
					}
				}
			}
		}
		if (local6 && (local8 & 0xFF000000) != 0) {
			for (local38 = arg0.aShort94; local38 <= arg0.aShort97; local38++) {
				for (@Pc(150) int local150 = arg0.aShort96; local150 <= arg0.aShort95; local150++) {
					if ((Class145_Sub2.anIntArrayArray64[local38][local150] & 0xFF000000) == 0) {
						Class145_Sub2.anIntArrayArray64[local38][local150] = local8;
						Class204.aByteArrayArray26[local38][local150] = local10;
						AbstractArbBufferGL.aByteArrayArray29[local38][local150] = local12;
					}
				}
			}
		}
		if (arg1) {
			Class4_Sub6_Sub38.aClass16_Sub1Array3[Static1.anInt6++] = arg0;
		}
		return true;
	}
@OriginalMember(owner = "client!qg", name = "a", descriptor = "([Lclient!l;I)V")
	public static void method4387(@OriginalArg(0) NativeSprite[] arg0) {
		Static277.anInt5150 = arg0.length;
		Class27_Sub4.anIntArray338 = new int[Static277.anInt5150 + 10];
		Class70_Sub2.aClass57Array16 = new NativeSprite[Static277.anInt5150 + 10];
		Static459.copy(arg0, 0, Class70_Sub2.aClass57Array16, 0, Static277.anInt5150);
		for (@Pc(26) int local26 = 0; local26 < Static277.anInt5150; local26++) {
			Class27_Sub4.anIntArray338[local26] = Class70_Sub2.aClass57Array16[local26].scaleHeight();
		}
		for (@Pc(41) int local41 = Static277.anInt5150; local41 < Class70_Sub2.aClass57Array16.length; local41++) {
			Class27_Sub4.anIntArray338[local41] = 12;
		}
	}
}
