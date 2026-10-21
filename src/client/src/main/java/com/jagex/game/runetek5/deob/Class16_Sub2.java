package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import java.awt.Canvas;
import java.awt.Color;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.EnvironmentContext;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTiledSprite;
import com.jagex.game.runetek5.cache.node.GlGroundLayer;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.config.enumtype.EnumType;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.config.idktype.IDKTypeList;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class4_Sub21_Sub2;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.config.rsinterface.Component;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!ii")
public abstract class Class16_Sub2 extends Class16 {

	@OriginalMember(owner = "client!ii", name = "k", descriptor = "I")
	public int anInt4551;

	@OriginalMember(owner = "client!ii", name = "G", descriptor = "I")
	public int anInt4566;

	@OriginalMember(owner = "client!ii", name = "z", descriptor = "I")
	public final int anInt4562;

	@OriginalMember(owner = "client!ii", name = "j", descriptor = "I")
	public int anInt4550;

	@OriginalMember(owner = "client!ii", name = "l", descriptor = "I")
	public int anInt4552;

	@OriginalMember(owner = "client!ii", name = "v", descriptor = "I")
	protected final int anInt4559;

	@OriginalMember(owner = "client!ii", name = "s", descriptor = "I")
	public final int anInt4556;

	@OriginalMember(owner = "client!ii", name = "<init>", descriptor = "(IIIIIII)V")
	protected Class16_Sub2(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6) {
		this.anInt4551 = arg5;
		this.anInt4566 = arg0;
		this.anInt4562 = arg3;
		this.anInt4550 = arg4;
		this.anInt4552 = arg2;
		this.anInt4559 = arg1;
		this.anInt4556 = arg6;
	}

	@OriginalMember(owner = "client!ii", name = "d", descriptor = "(B)Z")
	@Override
	public final boolean hasTileShadow() {
		return false;
	}

	@OriginalMember(owner = "client!ii", name = "b", descriptor = "(Z)I")
	public abstract int method3583();

	@OriginalMember(owner = "client!ii", name = "a", descriptor = "(IILclient!fi;BZLclient!ya;I)V")
	@Override
	public final void method5337(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Class16 arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg4, @OriginalArg(6) int arg5) {
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!ii", name = "d", descriptor = "(I)V")
	@Override
	public final void removeTileShadow() {
		throw new IllegalStateException();
	}

	// DEOB-RECOVERY: the concrete subclasses (Class16_Sub2_Sub1/Sub3 and
	// OpenGlArbElementArrayBuffer) all implement Interface6's
	// hardShadow/removeShadow, but this abstract parent declares neither, so
	// statically-typed uses (e.g. in MapRegion) cannot see them. These abstract
	// declarations restore visibility without changing behaviour; every concrete
	// subclass already provides an implementation.
	public abstract boolean hardShadow();

	public abstract void removeShadow(com.jagex.game.runetek5.graphics.Toolkit arg0);

@OriginalMember(owner = "client!ii", name = "p", descriptor = "Ljava/lang/String;")
	public static String aString45;
@OriginalMember(owner = "client!ii", name = "E", descriptor = "F")
	public static float aFloat143;
@OriginalMember(owner = "client!ii", name = "i", descriptor = "[Ljava/awt/Color;")
	public static final Color[] aColorArray6 = new Color[] { new Color(9179409), new Color(16777215) };
@OriginalMember(owner = "client!ii", name = "n", descriptor = "[Lclient!ri;")
	public static final Class30[] aClass30Array4 = new Class30[128];
@OriginalMember(owner = "client!ii", name = "o", descriptor = "[[I")
	public static final int[][] anIntArrayArray37 = new int[][] { { 2, 4, 6, 0 }, { 0, 2, 3, 5, 6, 4 }, { 0, 1, 4, 5 }, { 4, 6, 0, 2 }, { 2, 4, 0 }, { 0, 2, 4 }, { 6, 0, 1, 2, 4, 5 }, { 0, 1, 2, 4, 6, 7 }, { 4, 7, 6, 0 }, { 0, 8, 6, 1, 9, 2, 9, 4 }, { 2, 9, 4, 0, 8, 6 }, { 2, 11, 3, 7, 10, 10, 6, 6 }, { 2, 4, 6, 0 } };
@OriginalMember(owner = "client!ii", name = "w", descriptor = "F")
	public static float aFloat142 = 1024.0F;
	/**
	 * Camera zoom now lives in {@link com.jagex.game.runetek5.client.camera.CameraZoom}.
	 * These stubs were unused and are removed so the smoothed zoom state has
	 * a single owner. See CameraZoom for ZOOM_DEFAULT / ZOOM_MIN / ZOOM_MAX.
	 */
@OriginalMember(owner = "client!ii", name = "B", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_204 = new Class22(57, 15);
@OriginalMember(owner = "client!ii", name = "C", descriptor = "[I")
	public static final int[] anIntArray302 = new int[] { 28, 35, 40, 44 };
@OriginalMember(owner = "client!ii", name = "a", descriptor = "(IIIIIIBIII)V")
	public static void method3579(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) int arg7, @OriginalArg(9) int arg8) {
		if (arg4 == arg2 && arg8 == arg5 && arg7 == arg0 && arg6 == arg3) {
			Class4_Sub21_Sub2.method3167(arg1, arg6, arg8, arg0, arg4);
			return;
		}
		@Pc(36) int local36 = arg4;
		@Pc(38) int local38 = arg8;
		@Pc(42) int local42 = arg4 * 3;
		@Pc(46) int local46 = arg8 * 3;
		@Pc(50) int local50 = arg2 * 3;
		@Pc(54) int local54 = arg5 * 3;
		@Pc(58) int local58 = arg7 * 3;
		@Pc(62) int local62 = arg3 * 3;
		@Pc(71) int local71 = local50 + arg0 - local58 - arg4;
		@Pc(81) int local81 = arg6 + local54 - local62 - arg8;
		@Pc(91) int local91 = local42 + local58 - local50 - local50;
		@Pc(101) int local101 = local46 + local62 - local54 - local54;
		@Pc(106) int local106 = local50 - local42;
		@Pc(111) int local111 = local54 - local46;
		for (@Pc(113) int local113 = 128; local113 <= 4096; local113 += 128) {
			@Pc(121) int local121 = local113 * local113 >> 12;
			@Pc(127) int local127 = local121 * local113 >> 12;
			@Pc(131) int local131 = local71 * local127;
			@Pc(135) int local135 = local81 * local127;
			@Pc(139) int local139 = local91 * local121;
			@Pc(143) int local143 = local101 * local121;
			@Pc(147) int local147 = local113 * local106;
			@Pc(151) int local151 = local113 * local111;
			@Pc(162) int local162 = arg4 + (local139 + local131 + local147 >> 12);
			@Pc(173) int local173 = (local143 + local135 + local151 >> 12) + arg8;
			Class4_Sub21_Sub2.method3167(arg1, local173, local38, local162, local36);
			local36 = local162;
			local38 = local173;
		}
	}
@OriginalMember(owner = "client!ii", name = "a", descriptor = "(IIIII)V")
	public static void method3580(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		if (ItemType.anInt6186 <= arg3 - arg2 && arg2 + arg3 <= Class36.anInt2656 && Model.anInt6287 <= arg0 - arg2 && arg0 + arg2 <= GlCubeTexture.anInt3256) {
			IDKTypeList.method2755(arg3, arg2, arg0, arg1);
		} else {
			Static441.method5570(arg0, arg1, arg2, arg3);
		}
	}
@OriginalMember(owner = "client!ii", name = "f", descriptor = "(I)V")
	public static void method3582() {
		if (EnumType.anInt5766 == -1) {
			return;
		}
		@Pc(17) int local17 = MaterialType.aClass80_1.method2234();
		@Pc(23) int local23 = MaterialType.aClass80_1.getDefaultValue();
		@Pc(28) Class4_Sub5 local28 = (Class4_Sub5) Component.aClass183_47.method4140();
		if (local28 != null) {
			local17 = local28.method518();
			local23 = local28.method519();
		}
		TextureOpTiledSprite.method988(0, 0, GpuPointLight.anInt2881, EnumType.anInt5766, 0, 0, EffectiveVertex.anInt5346, local23, local17);
		if (GlGroundLayer.aClass247_2 != null) {
			Static308.method4154(local17, local23);
		}
	}
@OriginalMember(owner = "client!ii", name = "a", descriptor = "(Ljava/awt/Canvas;I)Lclient!kd;")
	public static EnvironmentContext method3584(@OriginalArg(0) Canvas arg0) {
		try {
			@Pc(6) Class local6 = Class.forName("com.jagex.game.runetek5.JavaDefaultSurface");
			@Pc(10) EnvironmentContext local10 = (EnvironmentContext) local6.getDeclaredConstructor().newInstance();
			local10.method4606(arg0);
			return local10;
		} catch (@Pc(22) Throwable local22) {
			@Pc(26) JavaFallbackSurface local26 = new JavaFallbackSurface();
			local26.method4606(arg0);
			return local26;
		}
	}
@OriginalMember(owner = "client!ii", name = "a", descriptor = "(ZII)V")
	public static void method3585(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		@Pc(8) Class4_Sub1_Sub11 local8 = NPCTypeList.method5222(arg1, 14);
		local8.method2604();
		local8.anInt3083 = arg0;
	}
}
