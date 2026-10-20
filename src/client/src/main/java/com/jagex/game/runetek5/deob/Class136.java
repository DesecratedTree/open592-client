package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.client.loading.loader.Js5GroupLoader;
import com.jagex.game.runetek5.deob.WaterfallEffect;
import com.jagex.game.runetek5.config.cursor.CursorTypeList;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!lk")
public final class Class136 {

	@OriginalMember(owner = "client!lk", name = "b", descriptor = "I")
	public int anInt4352;

	@OriginalMember(owner = "client!lk", name = "c", descriptor = "I")
	public int anInt4353;

	@OriginalMember(owner = "client!lk", name = "d", descriptor = "I")
	public int anInt4354;

	@OriginalMember(owner = "client!lk", name = "e", descriptor = "I")
	public int anInt4355;

	@OriginalMember(owner = "client!lk", name = "f", descriptor = "I")
	public int anInt4356;

	@OriginalMember(owner = "client!lk", name = "g", descriptor = "I")
	public int anInt4357;

	@OriginalMember(owner = "client!lk", name = "h", descriptor = "I")
	public int anInt4358;

	@OriginalMember(owner = "client!lk", name = "i", descriptor = "I")
	public int anInt4359;

	@OriginalMember(owner = "client!lk", name = "j", descriptor = "I")
	public int anInt4360;

	@OriginalMember(owner = "client!lk", name = "l", descriptor = "I")
	public int anInt4361;

	@OriginalMember(owner = "client!lk", name = "m", descriptor = "I")
	public int anInt4362;

	@OriginalMember(owner = "client!lk", name = "n", descriptor = "I")
	public int anInt4363;

	@OriginalMember(owner = "client!lk", name = "p", descriptor = "I")
	public int anInt4365;

	@OriginalMember(owner = "client!lk", name = "q", descriptor = "I")
	public int anInt4366;

	@OriginalMember(owner = "client!lk", name = "r", descriptor = "I")
	public int anInt4367;

	@OriginalMember(owner = "client!lk", name = "s", descriptor = "I")
	public int anInt4368;

	@OriginalMember(owner = "client!lk", name = "t", descriptor = "I")
	public int anInt4369;

	@OriginalMember(owner = "client!lk", name = "v", descriptor = "I")
	public int anInt4370;

@OriginalMember(owner = "client!lk", name = "u", descriptor = "[I")
	public static int[] anIntArray291;
@OriginalMember(owner = "client!lk", name = "z", descriptor = "Lclient!st;")
	public static Class222 aClass222_2;
@OriginalMember(owner = "client!lk", name = "k", descriptor = "[I")
	public static final int[] anIntArray290 = new int[500];
@OriginalMember(owner = "client!lk", name = "w", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_46 = new Class215(28, 8);
@OriginalMember(owner = "client!lk", name = "x", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_192 = new Class22(44, 6);
@OriginalMember(owner = "client!lk", name = "y", descriptor = "I")
	public static int anInt4371 = 0;
@OriginalMember(owner = "client!lk", name = "a", descriptor = "(B)V")
	public static void method3438() {
		WaterfallEffect.method3926();
		CubeMapSpecularEffect.method2701();
	}
@OriginalMember(owner = "client!lk", name = "a", descriptor = "(IBIII)V")
	public static void method3440(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		if (arg0 >= ItemType.anInt6186 && arg0 <= Class36.anInt2656) {
			@Pc(24) int local24 = Js5GroupLoader.method4519(GlCubeTexture.anInt3256, arg1, Model.anInt6287);
			@Pc(30) int local30 = Js5GroupLoader.method4519(GlCubeTexture.anInt3256, arg2, Model.anInt6287);
			CursorTypeList.method3980(local30, local24, arg0, arg3);
		}
	}
}
