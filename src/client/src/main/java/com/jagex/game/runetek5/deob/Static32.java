package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.client.loading.loader.Js5GroupLoader;
import com.jagex.game.runetek5.graphics.texture.water.NativeWaterTexture;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.deob.Class6_Sub2;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.deob.Class16_Sub3;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
public final class Static32 {

	@OriginalMember(owner = "client!bl", name = "c", descriptor = "Z")
	public static boolean aBoolean63 = false;

	@OriginalMember(owner = "client!bl", name = "a", descriptor = "(IIILclient!fs;IZ)V")
	public static void method625(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) Js5ResourceProvider arg2) {
		Class6_Sub2.aBoolean30 = false;
		Class21.anInt461 = 1;
		Class126.anInt3920 = arg0;
		Static47.anInt811 = 10000;
		Class16_Sub3.anInt742 = 0;
		Static14.anInt190 = arg1;
		NativeWaterTexture.aClass76_49 = arg2;
	}

	@OriginalMember(owner = "client!bl", name = "a", descriptor = "(IIIIII)V")
	public static void method626(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4) {
		@Pc(19) int local19 = Js5GroupLoader.method4519(GlCubeTexture.anInt3256, arg0, Model.anInt6287);
		@Pc(25) int local25 = Js5GroupLoader.method4519(GlCubeTexture.anInt3256, arg4, Model.anInt6287);
		@Pc(31) int local31 = Js5GroupLoader.method4519(Class36.anInt2656, arg3, ItemType.anInt6186);
		@Pc(37) int local37 = Js5GroupLoader.method4519(Class36.anInt2656, arg2, ItemType.anInt6186);
		for (@Pc(39) int local39 = local19; local39 <= local25; local39++) {
			BasType.method3640(local31, arg1, local37, Class4_Sub6_Sub7.anIntArrayArray21[local39]);
		}
	}
}
