package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import java.awt.Color;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.sign.SignedResource;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.cache.node.lru.ClientScript;
import com.jagex.game.runetek5.client.GameShell;
import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.toolkit.opengl.effect.BreakingWaterEffect;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.graphics.font.FontMetrics;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.Js5TextureSource;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.cache.node.MemoryPool;
import com.jagex.game.runetek5.deob.JavaFallbackSurface;
import com.jagex.game.runetek5.config.quickchat.QuickChatCategoryType;
@OriginalClass("client!hd")
public final class Class92 {

	@OriginalMember(owner = "client!hd", name = "a", descriptor = "I")
	public int anInt2882;

	@OriginalMember(owner = "client!hd", name = "b", descriptor = "I")
	public int anInt2883;

	@OriginalMember(owner = "client!hd", name = "d", descriptor = "I")
	public int anInt2885;

	static {
		new Class83("This mute will remain for a further ", "Diese Stummschaltung gilt für weitere ", "Votre accès restera bloqué encore ", "Este veto permanecerá por mais ");
	}

@OriginalMember(owner = "client!hd", name = "i", descriptor = "Lclient!qt;")
	public static SignedResource aClass199_6;
@OriginalMember(owner = "client!hd", name = "g", descriptor = "[J")
	public static final long[] aLongArray6 = new long[32];
@OriginalMember(owner = "client!hd", name = "a", descriptor = "(Lclient!iv;B)V")
	public static void method2425(@OriginalArg(0) Packet arg0) {
		@Pc(9) int local9 = arg0.method2506();
		FontMetrics.aClass10Array1 = new Class10[local9];
		for (@Pc(14) int local14 = 0; local14 < local9; local14++) {
			FontMetrics.aClass10Array1[local14] = new Class10();
			FontMetrics.aClass10Array1[local14].anInt169 = arg0.method2506();
			FontMetrics.aClass10Array1[local14].aString3 = arg0.method2497();
		}
		MemoryPool.anInt4816 = arg0.method2506();
		JavaFallbackSurface.anInt5874 = arg0.method2506();
		Class4_Sub7.anInt602 = arg0.method2506();
		Skybox.aClass78_Sub1Array2 = new Class78_Sub1[JavaFallbackSurface.anInt5874 + 1 - MemoryPool.anInt4816];
		for (@Pc(67) int local67 = 0; local67 < Class4_Sub7.anInt602; local67++) {
			@Pc(73) int local73 = arg0.method2506();
			@Pc(81) Class78_Sub1 local81 = Skybox.aClass78_Sub1Array2[local73] = new Class78_Sub1();
			local81.anInt3877 = arg0.method2490();
			local81.anInt3874 = arg0.method2529();
			local81.anInt3881 = MemoryPool.anInt4816 + local73;
			local81.aString37 = arg0.method2497();
			local81.aString38 = arg0.method2497();
		}
		BreakingWaterEffect.anInt1504 = arg0.method2529();
		Class64_Sub1.aBoolean176 = true;
	}
@OriginalMember(owner = "client!hd", name = "a", descriptor = "(B)V")
	public static void method2426() {
		Static384.aClass244_1.method5486();
		MaterialType.aClass80_1.validate();
		if (PlayerEntity.aClass14_1 != null) {
			PlayerEntity.aClass14_1.method213(Class164.aCanvas5);
		}
		ClientScript.aClient1.method874();
		Class164.aCanvas5.setBackground(Color.black);
		ClientScript.cameraPitch = -1;
		Static384.aClass244_1 = Js5TextureSource.method2398(Class164.aCanvas5);
		MaterialType.aClass80_1 = Model.method4882(Class164.aCanvas5);
		if (PlayerEntity.aClass14_1 != null) {
			PlayerEntity.aClass14_1.method209(Class164.aCanvas5);
		}
	}
@OriginalMember(owner = "client!hd", name = "a", descriptor = "(BIC)I")
	public static int method2428(@OriginalArg(1) int arg0, @OriginalArg(2) char arg1) {
		@Pc(7) int local7 = arg1 << 4;
		if (Character.isUpperCase(arg1) || Character.isTitleCase(arg1)) {
			arg1 = Character.toLowerCase(arg1);
			local7 = (arg1 << 4) + 1;
		}
		if (arg1 == 'ñ' && arg0 == 0) {
			local7 = 1762;
		}
		return local7;
	}
@OriginalMember(owner = "client!hd", name = "a", descriptor = "(IIIIIII)V")
	public static void method2429(@OriginalArg(0) int arg0, @OriginalArg(4) int arg1, @OriginalArg(5) int arg2, @OriginalArg(6) int arg3) {
		for (@Pc(6) TileMessage local6 = (TileMessage) GameShell.aClass84_4.method2312(); local6 != null; local6 = (TileMessage) GameShell.aClass84_4.method2309()) {
			if (Shadow.anInt5323 >= local6.anInt7367) {
				local6.method5676();
			} else {
				QuickChatCategoryType.method3871((local6.anInt7366 << 7) + 64, (local6.anInt7374 << 7) + 64, local6.anInt7372, local6.anInt7375 * 2, arg0 >> 1, arg2 >> 1);
				Static331.aClass46_10.method5056(arg3 + Static449.anIntArray557[0], local6.anInt7376 | 0xFF000000, arg1 + Static449.anIntArray557[1], 0, local6.aString71);
			}
		}
	}
}
