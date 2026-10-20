package com.jagex.game.runetek5.config.sequence;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class83;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Resampler;
import com.jagex.game.runetek5.deob.Static368;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.sign.SignedResource;
import com.jagex.game.runetek5.config.sequence.*;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.deob.TextureOpBlend;
import com.jagex.game.runetek5.deob.Class92;
import com.jagex.game.runetek5.deob.ReflectingTextureEffect;
import com.jagex.game.runetek5.deob.Class4_Sub17;
import com.jagex.game.runetek5.core.io.DummyInputStream;
@OriginalClass("client!nv")
public final class PathingEntitySequence {

	@OriginalMember(owner = "client!nv", name = "b", descriptor = "I")
	public int anInt5138;

	@OriginalMember(owner = "client!nv", name = "d", descriptor = "I")
	public int anInt5139;

	@OriginalMember(owner = "client!nv", name = "e", descriptor = "I")
	public int anInt5140;

	@OriginalMember(owner = "client!nv", name = "f", descriptor = "I")
	public int anInt5141;

	@OriginalMember(owner = "client!nv", name = "g", descriptor = "I")
	public int anInt5142;

	@OriginalMember(owner = "client!nv", name = "h", descriptor = "I")
	public int anInt5143;

	static {
		new Class83(" joined the channel.", " hat den Chatraum betreten.", " a rejoint le canal.", " entrou no canal.");
	}

@OriginalMember(owner = "client!nv", name = "c", descriptor = "Lclient!wd;")
	public static Resampler keyRecorder;
@OriginalMember(owner = "client!nv", name = "a", descriptor = "I")
	public static int anInt5137 = 0;
@OriginalMember(owner = "client!nv", name = "i", descriptor = "I")
	public static int anInt5144 = 0;
@OriginalMember(owner = "client!nv", name = "a", descriptor = "(Lclient!et;Ljava/lang/String;ZZI)V")
	public static void method3929(@OriginalArg(0) SignLink arg0, @OriginalArg(1) String arg1, @OriginalArg(2) boolean arg2, @OriginalArg(3) boolean arg3) {
		if (!arg3) {
			TextureOpBlend.method3110(arg0, 3, arg1);
			return;
		}
		@Pc(29) String local29;
		if (SignLink.aString25.startsWith("win") && SignLink.anInt1987 != 3) {
			local29 = null;
			if (arg0.anApplet1 != null) {
				local29 = arg0.anApplet1.getParameter("haveie6");
			}
			if (local29 == null || !local29.equals("1")) {
				@Pc(49) SignedResource local49 = TextureOpBlend.method3110(arg0, 0, arg1);
				ReflectingTextureEffect.aString57 = arg1;
				Class92.aClass199_6 = local49;
				DummyInputStream.aClass66_6 = arg0;
				return;
			}
		}
		if (SignLink.aString25.startsWith("mac")) {
			local29 = null;
			if (arg0.anApplet1 != null) {
				local29 = arg0.anApplet1.getParameter("havefirefox");
			}
			if (local29 != null && local29.equals("1") && arg2) {
				TextureOpBlend.method3110(arg0, 1, arg1);
				return;
			}
		}
		TextureOpBlend.method3110(arg0, 2, arg1);
	}
@OriginalMember(owner = "client!nv", name = "a", descriptor = "(ILjava/lang/String;IIILclient!la;ILclient!uu;IILclient!gt;Lclient!ma;)V")
	public static void method3930(@OriginalArg(0) int arg0, @OriginalArg(1) String arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) AbstractFontRenderer arg4, @OriginalArg(6) int arg5, @OriginalArg(7) com.jagex.game.runetek5.config.rsinterface.Component arg6, @OriginalArg(8) int arg7, @OriginalArg(9) int arg8, @OriginalArg(10) com.jagex.game.runetek5.graphics.font.FontMetrics arg9, @OriginalArg(11) ComponentSurface arg10) {
		@Pc(11) int local11;
		if (Class3_Sub3.anInt2386 == 4) {
			local11 = (int) Static368.aFloat192 & 0x3FFF;
		} else {
			local11 = (int) Static368.aFloat192 + PlayerVarDomain.anInt3399 & 0x3FFF;
		}
		@Pc(33) int local33 = Math.max(arg6.anInt6833 / 2, arg6.anInt6805 / 2) + 10;
		@Pc(41) int local41 = arg2 * arg2 + arg7 * arg7;
		if (local41 > local33 * local33) {
			return;
		}
		@Pc(55) int local55 = Class50_Sub1.anIntArray225[local11];
		@Pc(59) int local59 = Class50_Sub1.anIntArray224[local11];
		if (Class3_Sub3.anInt2386 != 4) {
			local59 = local59 * 256 / (Class4_Sub17.anInt2374 + 256);
			local55 = local55 * 256 / (Class4_Sub17.anInt2374 + 256);
		}
		@Pc(89) int local89 = arg7 * local55 + arg2 * local59 >> 15;
		@Pc(99) int local99 = local59 * arg7 - local55 * arg2 >> 15;
		@Pc(106) int local106 = arg9.method2349(arg1, 100, null);
		@Pc(114) int local114 = arg9.method2345(arg1, null);
		@Pc(120) int local120 = local89 - local106 / 2;
		if (-arg6.anInt6833 <= local120 && local120 <= arg6.anInt6833 && local99 >= -arg6.anInt6805 && local99 <= arg6.anInt6805) {
			arg4.method5063(0, arg5, null, arg0, arg10, arg6.anInt6805 / 2 + arg5 - local114 - arg3 - local99, local120 - -arg0 - -(arg6.anInt6833 / 2), 50, local106, arg8, 0, arg1, null);
		}
	}
}
