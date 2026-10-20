package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.scene.ground.Ground;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class38;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.deob.Class28;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.deob.Class4_Sub17;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.Class3_Sub4;
import com.jagex.game.runetek5.js5.request.FileWorker;
@OriginalClass("client!jp")
public final class Class16_Sub4_Sub3 extends Class16_Sub4 implements GameObject {

	@OriginalMember(owner = "client!jp", name = "x", descriptor = "Z")
	private boolean aBoolean338;

	@OriginalMember(owner = "client!jp", name = "J", descriptor = "S")
	private final short aShort40;

	@OriginalMember(owner = "client!jp", name = "F", descriptor = "B")
	private final byte aByte26;

	@OriginalMember(owner = "client!jp", name = "A", descriptor = "Z")
	private final boolean aBoolean339;

	@OriginalMember(owner = "client!jp", name = "w", descriptor = "B")
	private final byte aByte25;

	@OriginalMember(owner = "client!jp", name = "L", descriptor = "Z")
	private final boolean aBoolean341;

	@OriginalMember(owner = "client!jp", name = "H", descriptor = "Z")
	private final boolean aBoolean340;

	@OriginalMember(owner = "client!jp", name = "Q", descriptor = "Lclient!t;")
	private Model aClass110_4;

	@OriginalMember(owner = "client!jp", name = "C", descriptor = "Lclient!ba;")
	private Shadow aClass4_Sub1_Sub4_3;

	static {
		new Class83(null, "geschickt werden.", null, null);
	}

	@OriginalMember(owner = "client!jp", name = "<init>", descriptor = "(Lclient!ya;Lclient!rd;IIIIZIZ)V")
	public Class16_Sub4_Sub3(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) LocType arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) boolean arg6, @OriginalArg(7) int arg7, @OriginalArg(8) boolean arg8) {
		super(arg3, arg4, arg5, arg1.aBoolean527, arg1.aBoolean529);
		this.aBoolean338 = arg8;
		this.aShort40 = (short) arg1.id;
		this.aByte26 = (byte) arg7;
		this.aBoolean339 = arg1.anInt5813 != 0 && !arg6;
		this.aByte25 = (byte) arg2;
		this.aBoolean341 = arg6;
		super.anInt3646 = arg3;
		super.anInt3647 = arg5;
		this.aBoolean340 = arg0.hardShadow() && arg1.aBoolean526 && !this.aBoolean341 && TextureOpBrightness.aClass50_Sub1_1.method2850(Packet.anInt2973) != 0;
		@Pc(67) int local67 = 2048;
		if (this.aBoolean338) {
			local67 |= 0x10000;
		}
		@Pc(82) Class149 local82 = this.method3035(local67, this.aBoolean340, arg0);
		if (local82 != null) {
			this.aClass110_4 = local82.aClass110_5;
			this.aClass4_Sub1_Sub4_3 = local82.aClass4_Sub1_Sub4_4;
			if (this.aBoolean338) {
				this.aClass110_4 = this.aClass110_4.copy((byte) 0, local67, false);
				return;
			}
		}
	}

	@OriginalMember(owner = "client!jp", name = "a", descriptor = "(Lclient!ya;ZI)Lclient!t;")
	private Model method3033(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(2) int arg1) {
		if (this.aClass110_4 != null && arg0.compareFunctionMasks(this.aClass110_4.P(), arg1) == 0) {
			return this.aClass110_4;
		} else {
			@Pc(28) Class149 local28 = this.method3035(arg1, false, arg0);
			return local28 == null ? null : local28.aClass110_5;
		}
	}

	@OriginalMember(owner = "client!jp", name = "a", descriptor = "(B)I")
	@Override
	public int method5347() {
		return this.aByte26;
	}

	@OriginalMember(owner = "client!jp", name = "a", descriptor = "(IZILclient!ya;)Lclient!mg;")
	private Class149 method3035(@OriginalArg(0) int arg0, @OriginalArg(1) boolean arg1, @OriginalArg(3) com.jagex.game.runetek5.graphics.Toolkit arg2) {
		@Pc(19) LocType local19 = Static267.aClass262_2.method5560(this.aShort40 & 0xFFFF);
		@Pc(42) Class65 local42;
		@Pc(33) Class65 local33;
		if (this.aBoolean341) {
			local33 = Class4_Sub6_Sub4.aClass65Array1[0];
			local42 = FileWorker.aClass65Array3[this.aByte25];
		} else {
			if (this.aByte25 < 3) {
				local33 = Class4_Sub6_Sub4.aClass65Array1[this.aByte25 + 1];
			} else {
				local33 = null;
			}
			local42 = Class4_Sub6_Sub4.aClass65Array1[this.aByte25];
		}
		return local19.method4589(arg2, super.anInt3646, arg0, super.anInt3648, arg1, this.aByte26, local33, super.anInt3647, 22, local42);
	}

	@OriginalMember(owner = "client!jp", name = "a", descriptor = "(IILclient!fi;BZLclient!ya;I)V")
	@Override
	public void method5337(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Class16 arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg4, @OriginalArg(6) int arg5) {
		if (!(arg2 instanceof Class16_Sub4_Sub3)) {
			return;
		}
		@Pc(16) Class16_Sub4_Sub3 local16 = (Class16_Sub4_Sub3) arg2;
		if (this.aClass110_4 != null && local16.aClass110_4 != null) {
			this.aClass110_4.shareLight(local16.aClass110_4, arg1, arg5, arg0, arg3);
			return;
		}
	}

	@OriginalMember(owner = "client!jp", name = "b", descriptor = "(Lclient!ya;I)V")
	@Override
	public void method5335(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
	}

	@OriginalMember(owner = "client!jp", name = "a", descriptor = "(Lclient!ya;II)Lclient!t;")
	@Override
	public Model method5352(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) int arg1) {
		return this.method3033(arg0, arg1);
	}

	@OriginalMember(owner = "client!jp", name = "b", descriptor = "(I)V")
	@Override
	public void upload() {
		if (this.aClass110_4 != null) {
			this.aClass110_4.uploadToGpu();
		}
	}

	@OriginalMember(owner = "client!jp", name = "a", descriptor = "(Lclient!ya;I)V")
	@Override
	public void removeShadow(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(20) Shadow local20;
		if (this.aClass4_Sub1_Sub4_3 == null && this.aBoolean340) {
			@Pc(33) Class149 local33 = this.method3035(262144, true, arg0);
			local20 = local33 == null ? null : local33.aClass4_Sub1_Sub4_4;
		} else {
			local20 = this.aClass4_Sub1_Sub4_3;
			this.aClass4_Sub1_Sub4_3 = null;
		}
		if (local20 != null) {
			Class38.method844(local20, this.aByte25, super.anInt3646, super.anInt3647, null);
		}
	}

	@OriginalMember(owner = "client!jp", name = "a", descriptor = "(I)Z")
	@Override
	public boolean hardShadow() {
		return this.aBoolean340;
	}

	@OriginalMember(owner = "client!jp", name = "a", descriptor = "(Z)I")
	@Override
	public int method5350() {
		return this.aShort40 & 0xFFFF;
	}

	@OriginalMember(owner = "client!jp", name = "d", descriptor = "(I)V")
	@Override
	public void removeTileShadow() {
		this.aBoolean338 = false;
		if (this.aClass110_4 != null) {
			this.aClass110_4.UA(this.aClass110_4.P() & 0xFFFEFFFF);
		}
	}

	@OriginalMember(owner = "client!jp", name = "a", descriptor = "(IILclient!ya;I)Z")
	@Override
	public boolean method5341(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) int arg2) {
		@Pc(15) Model local15 = this.method3033(arg1, 131072);
		if (local15 == null) {
			return false;
		} else {
			@Pc(20) Ground local20 = arg1.method4289();
			local20.scaleWidth(super.anInt3646, super.anInt3648, super.anInt3647);
			return local15.picked(arg2, arg0, local20, false);
		}
	}

	@OriginalMember(owner = "client!jp", name = "c", descriptor = "(I)I")
	@Override
	public int method5354() {
		return 22;
	}

	@OriginalMember(owner = "client!jp", name = "c", descriptor = "(Lclient!ya;I)Lclient!fk;")
	@Override
	public Class3_Sub3 method5339(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		if (this.aClass110_4 == null) {
			return null;
		}
		@Pc(18) Ground local18 = arg0.method4289();
		local18.scaleWidth(super.anInt3646, super.anInt3648, super.anInt3647);
		@Pc(28) Class3_Sub3 local28 = null;
		if (this.aBoolean339) {
			local28 = Class28.method672(1);
		}
		this.aClass110_4.render(local18, local28 == null ? null : local28.aClass3_Sub5Array1[0], 0);
		return local28;
	}

	@OriginalMember(owner = "client!jp", name = "d", descriptor = "(B)Z")
	@Override
	public boolean hasTileShadow() {
		return this.aBoolean338;
	}

	@OriginalMember(owner = "client!jp", name = "a", descriptor = "(ZLclient!ya;)V")
	@Override
	public void method5349(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(14) Shadow local14;
		if (this.aClass4_Sub1_Sub4_3 == null && this.aBoolean340) {
			@Pc(25) Class149 local25 = this.method3035(262144, true, arg0);
			local14 = local25 == null ? null : local25.aClass4_Sub1_Sub4_4;
		} else {
			local14 = this.aClass4_Sub1_Sub4_3;
			this.aClass4_Sub1_Sub4_3 = null;
		}
		if (local14 != null) {
			Class4_Sub17.method2015(local14, this.aByte25, super.anInt3646, super.anInt3647, null);
		}
	}

@OriginalMember(owner = "client!jp", name = "I", descriptor = "[[B")
	public static byte[][] aByteArrayArray13;
@OriginalMember(owner = "client!jp", name = "a", descriptor = "(IIZ)Z")
	public static boolean method3032(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		return (arg1 & 0x580) != 0;
	}
@OriginalMember(owner = "client!jp", name = "a", descriptor = "(Lclient!sp;B)Z")
	public static boolean method3034(@OriginalArg(0) ModeWhere arg0) {
		return client.aClass219_1 == arg0 || arg0 == Class3_Sub4.aClass219_3 || arg0 == MaterialType.aClass219_7 || arg0 == SeqType.aClass219_4;
	}
}
