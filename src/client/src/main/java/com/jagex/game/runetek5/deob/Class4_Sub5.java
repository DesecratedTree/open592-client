package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.QuickChatPhrase;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.deob.Class27_Sub2;
import com.jagex.game.runetek5.deob.Class16_Sub5_Sub2;
import com.jagex.game.runetek5.deob.Class70_Sub1;
import com.jagex.game.runetek5.Js5TextureSource;
import com.jagex.game.runetek5.graphics.font.FontMetrics;
import com.jagex.game.runetek5.deob.Class111;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
@OriginalClass("client!os")
public abstract class Class4_Sub5 extends Country {

	@OriginalMember(owner = "client!os", name = "c", descriptor = "(B)I")
	public abstract int method515();

	@OriginalMember(owner = "client!os", name = "d", descriptor = "(B)J")
	public abstract long method516();

	@OriginalMember(owner = "client!os", name = "c", descriptor = "(I)I")
	public abstract int method518();

	@OriginalMember(owner = "client!os", name = "e", descriptor = "(B)I")
	public abstract int method519();

@OriginalMember(owner = "client!os", name = "n", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_17 = new Class83(" is already on your friend list.", " steht bereits auf deiner Freunde-Liste!", " est déjà dans votre liste d'amis.", " já está na sua lista de amigos.");
@OriginalMember(owner = "client!os", name = "o", descriptor = "Lclient!bf;")
	public static final Class21 aClass21_4 = new Class21(0, 15);
@OriginalMember(owner = "client!os", name = "p", descriptor = "I")
	public static int anInt446 = 0;
@OriginalMember(owner = "client!os", name = "a", descriptor = "(Z)V")
	public static void method514() {
		if (Js5TextureSource.anInt2836 == -1 || Class27_Sub2.anInt2797 == -1) {
			return;
		}
		@Pc(24) int local24 = (Class4_Sub7.anInt605 * (GpuFaceNormal.anInt6951 - TextureOpColorGradient.anInt1765) >> 16) + TextureOpColorGradient.anInt1765;
		Class4_Sub7.anInt605 += local24;
		if (Class4_Sub7.anInt605 >= 65535) {
			Class4_Sub7.anInt605 = 65535;
			if (Class164.aBoolean463) {
				Class16_Sub5_Sub2.aBoolean549 = false;
			} else {
				Class16_Sub5_Sub2.aBoolean549 = true;
			}
			Class164.aBoolean463 = true;
		} else {
			Class164.aBoolean463 = false;
			Class16_Sub5_Sub2.aBoolean549 = false;
		}
		@Pc(54) float local54 = (float) Class4_Sub7.anInt605 / 65535.0F;
		@Pc(57) float[] local57 = new float[3];
		@Pc(65) int local65 = Static145.anInt2908 * 2;
		@Pc(91) int local91;
		@Pc(124) int local124;
		@Pc(132) int local132;
		@Pc(137) int local137;
		@Pc(147) int local147;
		@Pc(164) int local164;
		for (@Pc(67) int local67 = 0; local67 < 3; local67++) {
			@Pc(79) int local79 = Class27_Sub2.anIntArrayArrayArray3[Js5TextureSource.anInt2836][local65][local67] * 3;
			local91 = Class27_Sub2.anIntArrayArrayArray3[Js5TextureSource.anInt2836][local65 + 1][local67] * 3;
			local124 = (Class27_Sub2.anIntArrayArrayArray3[Js5TextureSource.anInt2836][local65 + 2][local67] + Class27_Sub2.anIntArrayArrayArray3[Js5TextureSource.anInt2836][local65 + 2][local67] - Class27_Sub2.anIntArrayArrayArray3[Js5TextureSource.anInt2836][local65 + 3][local67]) * 3;
			local132 = Class27_Sub2.anIntArrayArrayArray3[Js5TextureSource.anInt2836][local65][local67];
			local137 = local91 - local79;
			local147 = local79 + local124 - local91 * 2;
			local164 = local91 + Class27_Sub2.anIntArrayArrayArray3[Js5TextureSource.anInt2836][local65 + 2][local67] - local132 - local124;
			local57[local67] = (float) local132 + local54 * ((float) local137 + (local54 * (float) local164 + (float) local147) * local54);
		}
		Class4_Sub23.anInt3197 = (int) local57[0] - Class50_Sub1.anInt3453 * 128;
		TextureOpIrregularBricks.anInt2024 = (int) local57[1] * -1;
		Static145.anInt2906 = (int) local57[2] - OpenGLTextureCache.anInt1771 * 128;
		@Pc(220) float[] local220 = new float[3];
		local91 = Class111.anInt3368 * 2;
		for (local124 = 0; local124 < 3; local124++) {
			local132 = Class27_Sub2.anIntArrayArrayArray3[Class27_Sub2.anInt2797][local91][local124] * 3;
			local137 = Class27_Sub2.anIntArrayArrayArray3[Class27_Sub2.anInt2797][local91 + 1][local124] * 3;
			local147 = (Class27_Sub2.anIntArrayArrayArray3[Class27_Sub2.anInt2797][local91 + 2][local124] + Class27_Sub2.anIntArrayArrayArray3[Class27_Sub2.anInt2797][local91 + 2][local124] - Class27_Sub2.anIntArrayArrayArray3[Class27_Sub2.anInt2797][local91 + 3][local124]) * 3;
			local164 = Class27_Sub2.anIntArrayArrayArray3[Class27_Sub2.anInt2797][local91][local124];
			@Pc(297) int local297 = local137 - local132;
			@Pc(306) int local306 = local132 + local147 - local137 * 2;
			@Pc(323) int local323 = local137 + Class27_Sub2.anIntArrayArrayArray3[Class27_Sub2.anInt2797][local91 + 2][local124] - local164 - local147;
			local220[local124] = (local54 * ((float) local306 + (float) local323 * local54) + (float) local297) * local54 + (float) local164;
		}
		@Pc(355) float local355 = local220[0] - local57[0];
		@Pc(365) float local365 = (local220[1] - local57[1]) * -1.0F;
		@Pc(374) float local374 = local220[2] - local57[2];
		@Pc(384) double local384 = Math.sqrt((double) (local355 * local355 + local374 * local374));
		QuickChatPhrase.anInt7074 = (int) (Math.atan2((double) local365, local384) * 2607.5945876176133D) & 0x3FFF;
		FontMetrics.anInt2790 = (int) (-Math.atan2((double) local355, (double) local374) * 2607.5945876176133D) & 0x3FFF;
		Class70_Sub1.anInt2070 = Class27_Sub2.anIntArrayArrayArray3[Js5TextureSource.anInt2836][local65][3] + ((Class27_Sub2.anIntArrayArrayArray3[Js5TextureSource.anInt2836][local65 + 2][3] - Class27_Sub2.anIntArrayArrayArray3[Js5TextureSource.anInt2836][local65][3]) * Class4_Sub7.anInt605 >> 16);
	}
}
