package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.sign.SignedResource;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.sequence.frameset.AnimBase;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.config.objtype.ObjTypeList;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.Class4_Sub41;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
@OriginalClass("client!kg")
public final class Class27_Sub3 extends TextureCombinerOp {

	@OriginalMember(owner = "client!kg", name = "i", descriptor = "I")
	private final int anInt3894;

	@OriginalMember(owner = "client!kg", name = "q", descriptor = "I")
	private final int anInt3901;

	@OriginalMember(owner = "client!kg", name = "o", descriptor = "I")
	private final int anInt3899;

	@OriginalMember(owner = "client!kg", name = "n", descriptor = "I")
	private final int anInt3898;

	@OriginalMember(owner = "client!kg", name = "<init>", descriptor = "(IIIIIII)V")
	public Class27_Sub3(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6) {
		super(arg4, arg5, arg6);
		this.anInt3894 = arg2;
		this.anInt3901 = arg1;
		this.anInt3899 = arg0;
		this.anInt3898 = arg3;
	}

	@OriginalMember(owner = "client!kg", name = "a", descriptor = "(III)V")
	@Override
	public void method3935(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		@Pc(10) int local10 = this.anInt3899 * arg1 >> 12;
		@Pc(22) int local22 = this.anInt3894 * arg1 >> 12;
		@Pc(29) int local29 = arg0 * this.anInt3901 >> 12;
		@Pc(36) int local36 = this.anInt3898 * arg0 >> 12;
		Static166.method2735(local22, super.anInt5152, super.anInt5154, local36, local10, local29);
	}

	@OriginalMember(owner = "client!kg", name = "c", descriptor = "(III)V")
	@Override
	public void method3939(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		@Pc(10) int local10 = this.anInt3899 * arg1 >> 12;
		@Pc(17) int local17 = arg1 * this.anInt3894 >> 12;
		@Pc(34) int local34 = arg0 * this.anInt3901 >> 12;
		@Pc(41) int local41 = arg0 * this.anInt3898 >> 12;
		ObjTypeList.method4709(local41, local10, super.anInt5155, local17, local34);
	}

	@OriginalMember(owner = "client!kg", name = "b", descriptor = "(III)V")
	@Override
	public void method3936(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		@Pc(10) int local10 = arg1 * this.anInt3899 >> 12;
		@Pc(17) int local17 = arg1 * this.anInt3894 >> 12;
		@Pc(24) int local24 = this.anInt3901 * arg0 >> 12;
		@Pc(31) int local31 = arg0 * this.anInt3898 >> 12;
		AnimBase.method3827(local24, super.anInt5152, local17, local10, super.anInt5154, local31, super.anInt5155);
	}

@OriginalMember(owner = "client!kg", name = "k", descriptor = "I")
	public static int anInt3896;
@OriginalMember(owner = "client!kg", name = "l", descriptor = "F")
	public static float aFloat127;
@OriginalMember(owner = "client!kg", name = "a", descriptor = "(Lclient!et;I)[Lclient!ic;")
	public static Class103[] method3184(@OriginalArg(0) SignLink arg0) {
		if (!arg0.method1754()) {
			return new Class103[0];
		}
		@Pc(16) SignedResource local16 = arg0.method1745();
		while (local16.anInt5760 == 0) {
			Class259.method5503(10L);
		}
		if (local16.anInt5760 == 2) {
			return new Class103[0];
		}
		@Pc(40) int[] local40 = (int[]) local16.anObject29;
		@Pc(46) Class103[] local46 = new Class103[local40.length >> 2];
		for (@Pc(48) int local48 = 0; local48 < local46.length; local48++) {
			@Pc(54) Class103 local54 = new Class103();
			local46[local48] = local54;
			local54.anInt3173 = local40[local48 << 2];
			local54.anInt3174 = local40[(local48 << 2) + 1];
			local54.anInt3176 = local40[(local48 << 2) + 2];
			local54.anInt3175 = local40[(local48 << 2) + 3];
		}
		return local46;
	}
@OriginalMember(owner = "client!kg", name = "a", descriptor = "(ZZIIB)I")
	public static int method3185(@OriginalArg(1) boolean arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		@Pc(10) Class4_Sub25 local10 = PlayerVarDomain.method2834(false, arg1);
		if (local10 == null) {
			return 0;
		}
		@Pc(16) int local16 = 0;
		for (@Pc(23) int local23 = 0; local23 < local10.anIntArray230.length; local23++) {
			if (local10.anIntArray230[local23] >= 0 && local10.anIntArray230[local23] < Terrain.aClass206_3.num) {
				@Pc(49) ItemType local49 = Terrain.aClass206_3.method4703(local10.anIntArray230[local23]);
				@Pc(59) int local59 = local49.method4781(arg2, Class4_Sub1_Sub11.aClass180_1.method4121(arg2).anInt4123);
				if (arg0) {
					local16 += local10.anIntArray229[local23] * local59;
				} else {
					local16 += local59;
				}
			}
		}
		return local16;
	}
@OriginalMember(owner = "client!kg", name = "a", descriptor = "(IIIIIII)Lclient!tb;")
	public static EnvironmentSampler method3186(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5) {
		@Pc(38) long local38 = (long) arg2 * 76724863L ^ (long) arg0 * 32147369L ^ (long) arg4 * 986053L ^ (long) arg3 * 67481L ^ (long) arg5 * 97549L ^ (long) arg1 * 475427L;
		@Pc(44) EnvironmentSampler local44 = (EnvironmentSampler) Class4_Sub41.aClass68_51.method1787(local38);
		if (local44 == null) {
			local44 = Static57.aClass19_7.createCubeTexture(arg3, arg5, arg1, arg4, arg0, arg2);
			Class4_Sub41.aClass68_51.method1779(local38, local44);
			return local44;
		} else {
			return local44;
		}
	}
}
