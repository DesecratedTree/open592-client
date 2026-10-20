package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.graphics.gl.GlToolkit;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.cache.node.MonochromeImageCacheSlot;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.config.quickchat.QuickChatCategoryType;
@OriginalClass("client!dl")
public abstract class Class9 {

	@OriginalMember(owner = "client!dl", name = "c", descriptor = "[B")
	public static final byte[] aByteArray98 = new byte[32896];

	@OriginalMember(owner = "client!dl", name = "d", descriptor = "Lclient!fd;")
	protected final GlToolkit aClass19_Sub2_41;

	static {
		@Pc(13) int local13 = 0;
		for (@Pc(15) int local15 = 0; local15 < 256; local15++) {
			for (@Pc(18) int local18 = 0; local18 <= local15; local18++) {
				aByteArray98[local13++] = (byte) (255.0D / Math.sqrt((double) ((float) (local15 * local15 + local18 * local18 + 65535) / 65535.0F)));
			}
		}
	}

	@OriginalMember(owner = "client!dl", name = "<init>", descriptor = "(Lclient!fd;)V")
	public Class9(@OriginalArg(0) GlToolkit arg0) {
		this.aClass19_Sub2_41 = arg0;
	}

	@OriginalMember(owner = "client!dl", name = "a", descriptor = "(III)V")
	public abstract void setEffectParams(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1);

	@OriginalMember(owner = "client!dl", name = "a", descriptor = "(IZ)V")
	public abstract void enable(@OriginalArg(1) boolean arg0);

	@OriginalMember(owner = "client!dl", name = "a", descriptor = "(I)V")
	public abstract void disable();

	@OriginalMember(owner = "client!dl", name = "a", descriptor = "(Z)Z")
	public abstract boolean isSupported();

	@OriginalMember(owner = "client!dl", name = "a", descriptor = "(ILclient!kj;Z)V")
	public abstract void method5376(@OriginalArg(0) int arg0, @OriginalArg(1) Class6 arg1);

	@OriginalMember(owner = "client!dl", name = "a", descriptor = "(ZB)V")
	public abstract void applyTextureCombine(@OriginalArg(0) boolean arg0);

@OriginalMember(owner = "client!dl", name = "g", descriptor = "I")
	public static int anInt6919;
@OriginalMember(owner = "client!dl", name = "a", descriptor = "Lclient!cr;")
	public static final MonochromeImageCacheSlot aClass4_Sub11_10 = new MonochromeImageCacheSlot(0, 0);
@OriginalMember(owner = "client!dl", name = "b", descriptor = "[Lclient!pt;")
	public static final Class4_Sub1_Sub17[] aClass4_Sub1_Sub17Array14 = new Class4_Sub1_Sub17[14];
@OriginalMember(owner = "client!dl", name = "f", descriptor = "[I")
	public static final int[] anIntArray536 = new int[200];
@OriginalMember(owner = "client!dl", name = "h", descriptor = "I")
	public static int anInt6920 = -1;
@OriginalMember(owner = "client!dl", name = "a", descriptor = "(ZLclient!ya;)V")
	public static void method5372(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		if (Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82 != Class99_Sub1.anInt3967 && (Class70.aClass164ArrayArrayArray5 != null && QuickChatCategoryType.method3868(Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82, arg0))) {
			Class99_Sub1.anInt3967 = Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82;
		}
	}
}
