package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.client.loading.loader.Js5GroupLoader;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteType;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!fi")
public abstract class Class16 {

	@OriginalMember(owner = "client!fi", name = "d", descriptor = "(I)V")
	public abstract void removeTileShadow();

	@OriginalMember(owner = "client!fi", name = "b", descriptor = "(Lclient!ya;I)V")
	public abstract void method5335(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0);

	@OriginalMember(owner = "client!fi", name = "a", descriptor = "(IILclient!fi;BZLclient!ya;I)V")
	public abstract void method5337(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Class16 arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg4, @OriginalArg(6) int arg5);

	@OriginalMember(owner = "client!fi", name = "d", descriptor = "(B)Z")
	public abstract boolean hasTileShadow();

	@OriginalMember(owner = "client!fi", name = "c", descriptor = "(Lclient!ya;I)Lclient!fk;")
	public abstract Class3_Sub3 method5339(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0);

	@OriginalMember(owner = "client!fi", name = "a", descriptor = "(IILclient!ya;I)Z")
	public abstract boolean method5341(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) int arg2);

@OriginalMember(owner = "client!fi", name = "e", descriptor = "Ljava/lang/Object;")
	public static Object anObject33;
@OriginalMember(owner = "client!fi", name = "c", descriptor = "I")
	public static int anInt6886 = 0;
@OriginalMember(owner = "client!fi", name = "d", descriptor = "Z")
	public static boolean aBoolean628 = false;
@OriginalMember(owner = "client!fi", name = "f", descriptor = "[I")
	public static final int[] anIntArray527 = new int[] { -1, -1, 1, 1 };
@OriginalMember(owner = "client!fi", name = "g", descriptor = "I")
	public static int anInt6887 = 0;
@OriginalMember(owner = "client!fi", name = "a", descriptor = "(CI)Z")
	public static boolean method5334(@OriginalArg(0) char arg0) {
		if (arg0 >= ' ' && arg0 <= '~') {
			return true;
		} else if (arg0 >= ' ' && arg0 <= 'ÿ') {
			return true;
		} else {
			return arg0 == '€' || arg0 == 'Œ' || arg0 == '—' || arg0 == 'œ' || arg0 == 'Ÿ';
		}
	}
@OriginalMember(owner = "client!fi", name = "c", descriptor = "(B)Z")
	public static boolean method5336() {
		return Class21.anInt461 == 0 ? MapSpriteType.aClass4_Sub15_Sub1_1.method1434() : true;
	}
@OriginalMember(owner = "client!fi", name = "a", descriptor = "(IIIBI)V")
	public static void method5340(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(4) int arg3) {
		if (Model.anInt6287 <= arg0 && arg0 <= GlCubeTexture.anInt3256) {
			@Pc(15) int local15 = Js5GroupLoader.method4519(Class36.anInt2656, arg1, ItemType.anInt6186);
			@Pc(21) int local21 = Js5GroupLoader.method4519(Class36.anInt2656, arg3, ItemType.anInt6186);
			Static340.method4613(local21, arg0, arg2, local15);
		}
	}
}
