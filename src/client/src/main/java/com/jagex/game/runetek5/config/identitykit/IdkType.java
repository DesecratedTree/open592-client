package com.jagex.game.runetek5.config.identitykit;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.idktype.IDKTypeList;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteType;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.deob.Static214;
import com.jagex.game.runetek5.deob.Static271;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.config.identitykit.*;
import com.jagex.game.runetek5.js5.request.local.LocalRequester;
import com.jagex.game.runetek5.deob.Class4_Sub14;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.config.enumtype.EnumType;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.graphics.sprite.SpriteDrawQueue;
import com.jagex.game.runetek5.deob.Class95_Sub3_Sub1;
import com.jagex.game.runetek5.deob.Class180;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
@OriginalClass("client!pd")
public final class IdkType {

	@OriginalMember(owner = "client!pd", name = "c", descriptor = "[S")
	private short[] aShortArray180;

	@OriginalMember(owner = "client!pd", name = "e", descriptor = "Lclient!in;")
	public IDKTypeList identityTypeList;

	@OriginalMember(owner = "client!pd", name = "i", descriptor = "[S")
	private short[] aShortArray181;

	@OriginalMember(owner = "client!pd", name = "l", descriptor = "[S")
	private short[] aShortArray182;

	@OriginalMember(owner = "client!pd", name = "m", descriptor = "[S")
	private short[] aShortArray183;

	@OriginalMember(owner = "client!pd", name = "o", descriptor = "[I")
	private int[] bodyModels;

	@OriginalMember(owner = "client!pd", name = "h", descriptor = "[I")
	private final int[] anIntArray355 = new int[] { -1, -1, -1, -1, -1 };

	@OriginalMember(owner = "client!pd", name = "a", descriptor = "(ILclient!iv;)V")
	public void decode(@OriginalArg(1) Packet arg0) {
		while (true) {
			@Pc(11) int local11 = arg0.method2490();
			if (local11 == 0) {
				return;
			}
			this.method4098(local11, arg0);
		}
	}

	@OriginalMember(owner = "client!pd", name = "a", descriptor = "(B)Z")
	public boolean method4093() {
		@Pc(15) boolean local15 = true;
		@Pc(19) Js5ResourceProvider local19 = this.identityTypeList.aClass76_41;
		synchronized (this.identityTypeList.aClass76_41) {
			for (@Pc(23) int local23 = 0; local23 < 5; local23++) {
				if (this.anIntArray355[local23] != -1 && !this.identityTypeList.aClass76_41.method2098(0, this.anIntArray355[local23])) {
					local15 = false;
				}
			}
			return local15;
		}
	}

	@OriginalMember(owner = "client!pd", name = "b", descriptor = "(B)Z")
	public boolean method4095() {
		if (this.bodyModels == null) {
			return true;
		}
		@Pc(19) boolean local19 = true;
		@Pc(23) Js5ResourceProvider local23 = this.identityTypeList.aClass76_41;
		synchronized (this.identityTypeList.aClass76_41) {
			for (@Pc(27) int local27 = 0; local27 < this.bodyModels.length; local27++) {
				if (!this.identityTypeList.aClass76_41.method2098(0, this.bodyModels[local27])) {
					local19 = false;
				}
			}
			return local19;
		}
	}

	@OriginalMember(owner = "client!pd", name = "c", descriptor = "(B)Lclient!jq;")
	public Mesh method4096() {
		if (this.bodyModels == null) {
			return null;
		}
		@Pc(15) Mesh[] local15 = new Mesh[this.bodyModels.length];
		@Pc(19) Js5ResourceProvider local19 = this.identityTypeList.aClass76_41;
		@Pc(23) int local23;
		synchronized (this.identityTypeList.aClass76_41) {
			local23 = 0;
			while (true) {
				if (local23 >= this.bodyModels.length) {
					break;
				}
				local15[local23] = SpriteDrawQueue.method4948(this.identityTypeList.aClass76_41, this.bodyModels[local23]);
				local23++;
			}
		}
		for (@Pc(56) int local56 = 0; local56 < this.bodyModels.length; local56++) {
			if (local15[local56].version < 13) {
				local15[local56].method3039();
			}
		}
		@Pc(92) Mesh local92;
		if (local15.length == 1) {
			local92 = local15[0];
		} else {
			local92 = new Mesh(local15, local15.length);
		}
		if (local92 == null) {
			return null;
		}
		if (this.aShortArray181 != null) {
			for (local23 = 0; local23 < this.aShortArray181.length; local23++) {
				local92.method3055(this.aShortArray181[local23], this.aShortArray183[local23]);
			}
		}
		if (this.aShortArray182 != null) {
			for (local23 = 0; local23 < this.aShortArray182.length; local23++) {
				local92.method3038(this.aShortArray180[local23], this.aShortArray182[local23]);
			}
		}
		return local92;
	}

	@OriginalMember(owner = "client!pd", name = "a", descriptor = "(IBLclient!iv;)V")
	private void method4098(@OriginalArg(0) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 1) {
			arg1.method2490();
			return;
		}
		@Pc(15) int local15;
		@Pc(21) int local21;
		if (arg0 == 2) {
			local15 = arg1.method2490();
			this.bodyModels = new int[local15];
			for (local21 = 0; local21 < local15; local21++) {
				this.bodyModels[local21] = arg1.method2536();
			}
		} else if (arg0 != 3) {
			if (arg0 == 40) {
				local15 = arg1.method2490();
				this.aShortArray181 = new short[local15];
				this.aShortArray183 = new short[local15];
				for (local21 = 0; local21 < local15; local21++) {
					this.aShortArray181[local21] = (short) arg1.method2536();
					this.aShortArray183[local21] = (short) arg1.method2536();
				}
			} else if (arg0 == 41) {
				local15 = arg1.method2490();
				this.aShortArray180 = new short[local15];
				this.aShortArray182 = new short[local15];
				for (local21 = 0; local21 < local15; local21++) {
					this.aShortArray182[local21] = (short) arg1.method2536();
					this.aShortArray180[local21] = (short) arg1.method2536();
				}
			} else if (arg0 >= 60 && arg0 < 70) {
				this.anIntArray355[arg0 - 60] = arg1.method2536();
			}
		}
	}

	@OriginalMember(owner = "client!pd", name = "b", descriptor = "(I)Lclient!jq;")
	public Mesh method4099() {
		@Pc(8) Mesh[] local8 = new Mesh[5];
		@Pc(10) int local10 = 0;
		@Pc(14) Js5ResourceProvider local14 = this.identityTypeList.aClass76_41;
		@Pc(18) int local18;
		synchronized (this.identityTypeList.aClass76_41) {
			local18 = 0;
			while (true) {
				if (local18 >= 5) {
					break;
				}
				if (this.anIntArray355[local18] != -1) {
					local8[local10++] = SpriteDrawQueue.method4948(this.identityTypeList.aClass76_41, this.anIntArray355[local18]);
				}
				local18++;
			}
		}
		for (@Pc(53) int local53 = 0; local53 < 5; local53++) {
			if (local8[local53] != null && local8[local53].version < 13) {
				local8[local53].method3039();
			}
		}
		@Pc(83) Mesh local83 = new Mesh(local8, local10);
		if (this.aShortArray181 != null) {
			for (local18 = 0; local18 < this.aShortArray181.length; local18++) {
				local83.method3055(this.aShortArray181[local18], this.aShortArray183[local18]);
			}
		}
		if (this.aShortArray182 != null) {
			for (local18 = 0; local18 < this.aShortArray182.length; local18++) {
				local83.method3038(this.aShortArray180[local18], this.aShortArray182[local18]);
			}
		}
		return local83;
	}

@OriginalMember(owner = "client!pd", name = "j", descriptor = "Lclient!bg;")
	public static Class22 aClass22_246 = null;
@OriginalMember(owner = "client!pd", name = "a", descriptor = "(IIZBI)Lclient!vj;")
	public static Class4_Sub43 method4092(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) boolean arg2, @OriginalArg(4) int arg3) {
		@Pc(7) Class4_Sub43 local7 = new Class4_Sub43();
		local7.anInt6981 = arg1;
		local7.anInt6979 = arg0;
		MusicPatch.aClass102_29.method2703((long) arg3, local7);
		LocalRequester.method5439(arg0);
		@Pc(26) com.jagex.game.runetek5.config.rsinterface.Component local26 = ParticleBinaryTree.method5121(arg3);
		if (local26 != null) {
			Class4_Sub12_Sub1.method1142(local26);
		}
		if (Class180.aClass247_16 != null) {
			Class4_Sub12_Sub1.method1142(Class180.aClass247_16);
			Class180.aClass247_16 = null;
		}
		Static214.method3224();
		if (local26 != null) {
			Class95_Sub3_Sub1.method4998(local26, !arg2);
		}
		if (!arg2) {
			Static271.runInterfaceOnLoadScripts(arg0);
		}
		if (!arg2 && EnumType.anInt5766 != -1) {
			Class4_Sub37.method4165(EnumType.anInt5766, 1);
		}
		return local7;
	}
@OriginalMember(owner = "client!pd", name = "a", descriptor = "(Lclient!ya;IILclient!rd;II)V")
	public static void method4094(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(3) LocType arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4) {
		@Pc(9) MapSpriteType local9 = Class4_Sub14.aClass265_2.method5588(arg2.anInt5803);
		if (local9.fileId == -1) {
			return;
		}
		if (arg2.aBoolean533) {
			@Pc(37) int local37 = arg1 + arg2.anInt5817;
			arg1 = local37 & 0x3;
		} else {
			arg1 = 0;
		}
		@Pc(49) NativeSprite local49 = local9.method937(arg2.aBoolean530, arg0, arg1);
		if (local49 == null) {
			return;
		}
		@Pc(55) int local55 = arg2.anInt5816;
		@Pc(58) int local58 = arg2.anInt5801;
		if ((arg1 & 0x1) == 1) {
			local55 = arg2.anInt5801;
			local58 = arg2.anInt5816;
		}
		@Pc(74) int local74 = local49.scaleWidth();
		@Pc(77) int local77 = local49.scaleHeight();
		if (local9.requiresUpscaling) {
			local74 = local55 * 4;
			local77 = local58 * 4;
		}
		if (local9.backgroundColor == 0) {
			local49.drawTintedScaled(arg4, arg3 + 4 - local58 * 4, local74, local77);
		} else {
			local49.renderImpl(arg4, arg3 + 4 - local58 * 4, local74, local77, 0, local9.backgroundColor | 0xFF000000, 1);
		}
	}
}
