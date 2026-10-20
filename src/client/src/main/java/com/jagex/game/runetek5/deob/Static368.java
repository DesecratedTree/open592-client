package com.jagex.game.runetek5.deob;

import java.awt.Point;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.cursor.CursorType;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import com.jagex.game.runetek5.cache.node.lru.ClientScript;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
public final class Static368 {

	@OriginalMember(owner = "client!so", name = "a", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_124 = new Class83("Please remove ", "Bitte entferne ", "Veuillez commencer par supprimer ", "Remova ");

	@OriginalMember(owner = "client!so", name = "c", descriptor = "F")
	public static float aFloat192 = 0.0F;

	@OriginalMember(owner = "client!so", name = "a", descriptor = "(IB)V")
	public static void method4938(@OriginalArg(0) int arg0) {
		if (!TextureOpBrightness.aClass50_Sub1_1.aBoolean304) {
			arg0 = -1;
		}
		if (arg0 == ClientScript.cameraPitch) {
			return;
		}
		if (arg0 != -1) {
			@Pc(30) CursorType local30 = Js5ResourceProvider.aClass172_2.method3977(arg0);
			@Pc(34) SpriteType local34 = local30.method2061();
			if (local34 == null) {
				arg0 = -1;
			} else {
				Class126.aClass66_4.method1743(new Point(local30.anInt2420, local30.anInt2419), Class164.aCanvas5, local34.widthSum(), local34.getPixels(), local34.heightSum());
				ClientScript.cameraPitch = arg0;
			}
		}
		if (arg0 == -1 && ClientScript.cameraPitch != -1) {
			Class126.aClass66_4.method1743(new Point(), Class164.aCanvas5, -1, null, -1);
			ClientScript.cameraPitch = -1;
		}
	}
}
