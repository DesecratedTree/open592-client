package com.jagex.game.runetek5.deob;

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

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class120;
import com.jagex.game.runetek5.config.staticelements.StaticElements;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.deob.Class28;
import com.jagex.game.runetek5.config.midi.SoundBankCache;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
@OriginalClass("client!mj")
public final class Class16_Sub2_Sub3 extends Class16_Sub2 implements GameObject {

	@OriginalMember(owner = "client!mj", name = "V", descriptor = "Lclient!bi;")
	public final LocEntity aClass23_3;

	@OriginalMember(owner = "client!mj", name = "J", descriptor = "Z")
	private final boolean aBoolean421;

	@OriginalMember(owner = "client!mj", name = "<init>", descriptor = "(Lclient!ya;Lclient!rd;IIIIIZIIIIII)V")
	public Class16_Sub2_Sub3(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) LocType arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) boolean arg7, @OriginalArg(8) int arg8, @OriginalArg(9) int arg9, @OriginalArg(10) int arg10, @OriginalArg(11) int arg11, @OriginalArg(12) int arg12, @OriginalArg(13) int arg13) {
		super(arg4, arg5, arg6, arg8, arg9, arg10, StaticElements.method4425(arg12, arg11));
		this.aClass23_3 = new LocEntity(arg0, arg1, arg11, arg12, arg2, arg3, arg4, arg6, arg7, arg13);
		this.aBoolean421 = arg1.anInt5813 != 0 && !arg7;
	}

	@OriginalMember(owner = "client!mj", name = "b", descriptor = "(I)V")
	@Override
	public void upload() {
	}

	@OriginalMember(owner = "client!mj", name = "c", descriptor = "(Lclient!ya;I)Lclient!fk;")
	@Override
	public Class3_Sub3 method5339(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(16) Model local16 = this.aClass23_3.method578(super.anInt4552, true, arg0, super.anInt4566, 2048, false);
		if (local16 == null) {
			return null;
		}
		@Pc(23) Ground local23 = arg0.method4289();
		local23.scaleWidth(super.anInt4566 + super.anInt4550, super.anInt4559, super.anInt4552 + super.anInt4551);
		@Pc(41) Class3_Sub3 local41 = null;
		if (this.aBoolean421) {
			local41 = Class28.method672(1);
		}
		if (this.aClass23_3.aClass3_Sub2_1 == null) {
			local16.render(local23, local41 == null ? null : local41.aClass3_Sub5Array1[0], 0);
		} else {
			@Pc(77) ParticleList local77 = this.aClass23_3.aClass3_Sub2_1.getList();
			arg0.method4262(local16, local77, local23, local41 == null ? null : local41.aClass3_Sub5Array1[0]);
		}
		@Pc(96) int local96 = super.anInt4566 >> 7;
		@Pc(101) int local101 = super.anInt4552 >> 7;
		this.aClass23_3.method586(true, local101, local16, local96, arg0, local101, local96);
		return local41;
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(B)I")
	@Override
	public int method5347() {
		return this.aClass23_3.anInt551;
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(ZLclient!ya;)V")
	@Override
	public void method5349(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		this.aClass23_3.method588(arg0);
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(I)Z")
	@Override
	public boolean hardShadow() {
		return this.aClass23_3.method584();
	}

	@OriginalMember(owner = "client!mj", name = "b", descriptor = "(Z)I")
	@Override
	public int method3583() {
		return this.aClass23_3.method580();
	}

	@OriginalMember(owner = "client!mj", name = "c", descriptor = "(I)I")
	@Override
	public int method5354() {
		return this.aClass23_3.anInt536;
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(Lclient!ya;I)V")
	@Override
	public void removeShadow(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		this.aClass23_3.method582(arg0);
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(Lclient!ya;II)Lclient!t;")
	@Override
	public Model method5352(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) int arg1) {
		return this.aClass23_3.method578(0, false, arg0, 0, arg1, false);
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(IILclient!ya;I)Z")
	@Override
	public boolean method5341(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) int arg2) {
		@Pc(16) Model local16 = this.aClass23_3.method578(super.anInt4552, false, arg1, super.anInt4566, 131072, false);
		if (local16 == null) {
			return false;
		} else {
			@Pc(28) Ground local28 = arg1.method4289();
			local28.scaleWidth(super.anInt4566 + super.anInt4550, super.anInt4559, super.anInt4552 + super.anInt4551);
			return local16.picked(arg2, arg0, local28, false);
		}
	}

	@OriginalMember(owner = "client!mj", name = "b", descriptor = "(Lclient!ya;I)V")
	@Override
	public void method5335(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(22) Model local22 = this.aClass23_3.method578(super.anInt4552, true, arg0, super.anInt4566, 262144, false);
		if (local22 != null) {
			@Pc(29) int local29 = super.anInt4566 >> 7;
			@Pc(34) int local34 = super.anInt4552 >> 7;
			this.aClass23_3.method586(false, local34, local22, local29, arg0, local34, local29);
		}
	}

	@OriginalMember(owner = "client!mj", name = "a", descriptor = "(Z)I")
	@Override
	public int method5350() {
		return this.aClass23_3.anInt548;
	}

@OriginalMember(owner = "client!mj", name = "P", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_205 = new Class22(72, 6);
@OriginalMember(owner = "client!mj", name = "a", descriptor = "(III)Z")
	public static boolean method3594(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		return (arg1 & 0x21) != 0;
	}
@OriginalMember(owner = "client!mj", name = "a", descriptor = "(IZIIZII)V")
	public static void method3596(@OriginalArg(0) int arg0, @OriginalArg(1) boolean arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5) {
		Class120.anInt5355 = arg3;
		Class215.anInt6311 = arg0;
		SoundBankCache.hslToRgbUsers = arg2;
		Class4_Sub6_Sub4.anInt1408 = arg5;
		Class14.anInt266 = arg4;
		if (arg1 && SoundBankCache.hslToRgbUsers >= 100) {
			Class4_Sub23.anInt3197 = Class14.anInt266 * 128 + 64;
			Static145.anInt2906 = Class120.anInt5355 * 128 + 64;
			TextureOpIrregularBricks.anInt2024 = BufferedSocket.method135(Class4_Sub23.anInt3197, Class4_Sub6_Sub25.anInt4963, Static145.anInt2906) - Class215.anInt6311;
		}
		Class3_Sub3.anInt2386 = 2;
	}
@OriginalMember(owner = "client!mj", name = "a", descriptor = "(Lclient!vu;Lclient!vu;I)V")
	public static void method3597(@OriginalArg(0) Country arg0, @OriginalArg(1) Country arg1) {
		if (arg1.aClass4_261 != null) {
			arg1.method5684();
		}
		arg1.aClass4_261 = arg0.aClass4_261;
		arg1.aClass4_262 = arg0;
		arg1.aClass4_261.aClass4_262 = arg1;
		arg1.aClass4_262.aClass4_261 = arg1;
	}
}
