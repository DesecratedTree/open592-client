package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class9_Sub3;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.entity.pathingentity.player.PlayerAppearance;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!eh")
public final class Class60 {

	@OriginalMember(owner = "client!eh", name = "a", descriptor = "I")
	public final int anInt1812;

	@OriginalMember(owner = "client!eh", name = "<init>", descriptor = "(II)V")
	public Class60(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		this.anInt1812 = arg0;
	}

	@OriginalMember(owner = "client!eh", name = "toString", descriptor = "()Ljava/lang/String;")
	@Override
	public String toString() {
		throw new IllegalStateException();
	}

@OriginalMember(owner = "client!eh", name = "e", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_23;
@OriginalMember(owner = "client!eh", name = "h", descriptor = "Lclient!lq;")
	public static FileOnDisk aClass139_1;
@OriginalMember(owner = "client!eh", name = "c", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_20 = new Class215(59, 18);
@OriginalMember(owner = "client!eh", name = "g", descriptor = "I")
	public static int anInt1816 = 0;
@OriginalMember(owner = "client!eh", name = "a", descriptor = "(BIIIII)V")
	public static void method1588(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4) {
		if (arg4 == arg1) {
			Class16_Sub2.method3580(arg0, arg2, arg1, arg3);
		} else if (ItemType.anInt6186 <= arg3 - arg1 && Class36.anInt2656 >= arg1 + arg3 && Model.anInt6287 <= arg0 - arg4 && GlCubeTexture.anInt3256 >= arg4 + arg0) {
			Class9_Sub3.method1355(arg0, arg1, arg2, arg4, arg3);
		} else {
			Class10.method124(arg4, arg3, arg2, arg1, arg0);
		}
	}
@OriginalMember(owner = "client!eh", name = "a", descriptor = "(Ljava/lang/String;I)Z")
	public static boolean method1590(@OriginalArg(0) String arg0) {
		return PlayerAppearance.method839(arg0);
	}
}
