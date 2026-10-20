package com.jagex.game.runetek5.entity;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.LocEntity;
import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.deob.Class16_Sub5;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.buffer.Packet;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub17;
import com.jagex.game.runetek5.deob.Class28;
@OriginalClass("client!tt")
public final class DynamicWall extends Class16_Sub5 implements GameObject {

	@OriginalMember(owner = "client!tt", name = "E", descriptor = "Lclient!bi;")
	public final LocEntity aClass23_4;

	@OriginalMember(owner = "client!tt", name = "t", descriptor = "Z")
	private final boolean aBoolean597;

	@OriginalMember(owner = "client!tt", name = "<init>", descriptor = "(Lclient!ya;Lclient!rd;IIIIIZIII)V")
	public DynamicWall(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) LocType arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) boolean arg7, @OriginalArg(8) int arg8, @OriginalArg(9) int arg9, @OriginalArg(10) int arg10) {
		super(arg4, arg5, arg6, Class4_Sub1_Sub17.method4212(arg8, arg9));
		this.aClass23_4 = new LocEntity(arg0, arg1, arg8, arg9, arg2, arg3, super.anInt6550, super.anInt6552, arg7, arg10);
		this.aBoolean597 = arg1.anInt5813 != 0 && !arg7;
	}

	@OriginalMember(owner = "client!tt", name = "c", descriptor = "(Lclient!ya;I)Lclient!fk;")
	@Override
	public Class3_Sub3 method5339(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(16) Model local16 = this.aClass23_4.method578(super.anInt6552, true, arg0, super.anInt6550, 2048, false);
		if (local16 == null) {
			return null;
		}
		@Pc(23) Ground local23 = arg0.method4289();
		local23.scaleWidth(super.anInt6550, super.anInt6554, super.anInt6552);
		@Pc(38) Class3_Sub3 local38 = null;
		if (this.aBoolean597) {
			local38 = Class28.method672(1);
		}
		if (this.aClass23_4.aClass3_Sub2_1 == null) {
			local16.render(local23, local38 == null ? null : local38.aClass3_Sub5Array1[0], 0);
		} else {
			@Pc(54) ParticleList local54 = this.aClass23_4.aClass3_Sub2_1.getList();
			arg0.method4262(local16, local54, local23, local38 == null ? null : local38.aClass3_Sub5Array1[0]);
		}
		@Pc(87) int local87 = super.anInt6550 >> 7;
		@Pc(92) int local92 = super.anInt6552 >> 7;
		this.aClass23_4.method586(true, local92, local16, local87, arg0, local92, local87);
		return local38;
	}

	@OriginalMember(owner = "client!tt", name = "a", descriptor = "(IILclient!fi;BZLclient!ya;I)V")
	@Override
	public void method5337(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Class16 arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg4, @OriginalArg(6) int arg5) {
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!tt", name = "c", descriptor = "(I)I")
	@Override
	public int method5354() {
		return this.aClass23_4.anInt536;
	}

	@OriginalMember(owner = "client!tt", name = "a", descriptor = "(Lclient!ya;I)V")
	@Override
	public void removeShadow(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		this.aClass23_4.method582(arg0);
	}

	@OriginalMember(owner = "client!tt", name = "a", descriptor = "(IILclient!ya;I)Z")
	@Override
	public boolean method5341(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) int arg2) {
		@Pc(20) Model local20 = this.aClass23_4.method578(super.anInt6552, false, arg1, super.anInt6550, 131072, false);
		if (local20 == null) {
			return false;
		} else {
			@Pc(27) Ground local27 = arg1.method4289();
			local27.scaleWidth(super.anInt6550, super.anInt6554, super.anInt6552);
			return local20.picked(arg2, arg0, local27, false);
		}
	}

	@OriginalMember(owner = "client!tt", name = "a", descriptor = "(ZLclient!ya;)V")
	@Override
	public void method5349(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		this.aClass23_4.method588(arg0);
	}

	@OriginalMember(owner = "client!tt", name = "a", descriptor = "(Z)I")
	@Override
	public int method5350() {
		return this.aClass23_4.anInt548;
	}

	@OriginalMember(owner = "client!tt", name = "a", descriptor = "(Lclient!ya;II)Lclient!t;")
	@Override
	public Model method5352(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) int arg1) {
		return this.aClass23_4.method578(0, false, arg0, 0, arg1, false);
	}

	@OriginalMember(owner = "client!tt", name = "d", descriptor = "(I)V")
	@Override
	public void removeTileShadow() {
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!tt", name = "b", descriptor = "(Lclient!ya;I)V")
	@Override
	public void method5335(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(22) Model local22 = this.aClass23_4.method578(super.anInt6552, true, arg0, super.anInt6550, 262144, true);
		if (local22 != null) {
			@Pc(29) int local29 = super.anInt6550 >> 7;
			@Pc(34) int local34 = super.anInt6552 >> 7;
			this.aClass23_4.method586(false, local34, local22, local29, arg0, local34, local29);
		}
	}

	@OriginalMember(owner = "client!tt", name = "b", descriptor = "(I)V")
	@Override
	public void upload() {
	}

	@OriginalMember(owner = "client!tt", name = "a", descriptor = "(B)I")
	@Override
	public int method5347() {
		return this.aClass23_4.anInt551;
	}

	@OriginalMember(owner = "client!tt", name = "a", descriptor = "(I)Z")
	@Override
	public boolean hardShadow() {
		return this.aClass23_4.method584();
	}

	@OriginalMember(owner = "client!tt", name = "d", descriptor = "(B)Z")
	@Override
	public boolean hasTileShadow() {
		return false;
	}

@OriginalMember(owner = "client!tt", name = "J", descriptor = "[I")
	public static int[] anIntArray501;
@OriginalMember(owner = "client!tt", name = "v", descriptor = "Lclient!jk;")
	public static Game aClass121_3 = null;
@OriginalMember(owner = "client!tt", name = "A", descriptor = "[I")
	public static final int[] anIntArray500 = new int[32];
@OriginalMember(owner = "client!tt", name = "a", descriptor = "(Lclient!iv;B)Lclient!br;")
	public static Class27_Sub1 method5149(@OriginalArg(0) Packet arg0) {
		return new Class27_Sub1(arg0.method2512(), arg0.method2512(), arg0.method2512(), arg0.method2512(), arg0.method2512(), arg0.method2512(), arg0.method2512(), arg0.method2512(), arg0.method2493(), arg0.method2490());
	}
}
