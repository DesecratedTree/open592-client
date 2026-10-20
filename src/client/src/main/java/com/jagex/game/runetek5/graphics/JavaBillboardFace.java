package com.jagex.game.runetek5.graphics;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.deob.Class186;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static441;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;

import com.jagex.game.runetek5.graphics.*;
import com.jagex.game.runetek5.deob.Class4_Sub21_Sub2;
import com.jagex.game.runetek5.graphics.map.worldmap.WorldMapElement;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class70_Sub2;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.deob.Class174;
import com.jagex.game.runetek5.config.material.texture.TextureType;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpVoronoiNoise;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
@OriginalClass("client!km")
public final class JavaBillboardFace {

	@OriginalMember(owner = "client!km", name = "h", descriptor = "B")
	public final byte aByte28;

	@OriginalMember(owner = "client!km", name = "c", descriptor = "S")
	public final short aShort45;

	@OriginalMember(owner = "client!km", name = "n", descriptor = "B")
	public final byte aByte29;

	@OriginalMember(owner = "client!km", name = "j", descriptor = "I")
	public final int anInt3925;

	@OriginalMember(owner = "client!km", name = "d", descriptor = "S")
	public final short aShort46;

	@OriginalMember(owner = "client!km", name = "b", descriptor = "Z")
	public final boolean aBoolean365;

	@OriginalMember(owner = "client!km", name = "g", descriptor = "S")
	public final short aShort47;

	@OriginalMember(owner = "client!km", name = "<init>", descriptor = "(IIIIIIIIIZ)V")
	public JavaBillboardFace(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8, @OriginalArg(9) boolean arg9) {
		this.aByte28 = (byte) arg8;
		this.aShort45 = (short) arg5;
		this.aByte29 = (byte) arg7;
		this.anInt3925 = arg0;
		this.aShort46 = (short) arg4;
		this.aBoolean365 = arg9;
		this.aShort47 = (short) arg6;
	}

	@OriginalMember(owner = "client!km", name = "a", descriptor = "(IIIII)Lclient!km;")
	public JavaBillboardFace method3196(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		return new JavaBillboardFace(arg1, arg3, arg2, arg0, this.aShort46, this.aShort45, this.aShort47, this.aByte29, this.aByte28, this.aBoolean365);
	}

@OriginalMember(owner = "client!km", name = "l", descriptor = "I")
	public static int anInt3926;
@OriginalMember(owner = "client!km", name = "e", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_175 = new Class22(43, 3);
@OriginalMember(owner = "client!km", name = "k", descriptor = "Lclient!pn;")
	public static final Class186 aClass186_11 = new Class186("", 13);
@OriginalMember(owner = "client!km", name = "m", descriptor = "[I")
	public static final int[] anIntArray261 = new int[13];
@OriginalMember(owner = "client!km", name = "a", descriptor = "(Lclient!qm;I)I")
	public static int method3197(@OriginalArg(0) OggOtherStream arg0) {
		@Pc(14) String local14 = Class4_Sub21_Sub2.method3166(arg0);
		@Pc(16) int[] local16 = null;
		if (Static441.method5569(arg0.anInt5699)) {
			local16 = Terrain.aClass206_3.method4703((int) arg0.aLong192).quests;
		} else if (arg0.anInt5695 != -1) {
			local16 = Terrain.aClass206_3.method4703(arg0.anInt5695).quests;
		} else if (TextureOpBrightness.method4382(arg0.anInt5699)) {
			@Pc(90) NPCEntity local90 = Class93.aClass16_Sub1_Sub5_Sub2Array1[(int) arg0.aLong192];
			if (local90 != null) {
				@Pc(95) NPCType local95 = local90.npcType;
				if (local95.anIntArray544 != null) {
					local95 = local95.method5574(Class4_Sub6_Sub24.aClass114_1);
				}
				if (local95 != null) {
					local16 = local95.anIntArray547;
				}
			}
		} else if (StructTypeList.method1982(arg0.anInt5699)) {
			@Pc(58) LocType local58;
			if (arg0.anInt5699 == 1010) {
				local58 = Static267.aClass262_2.method5560((int) arg0.aLong192);
			} else {
				local58 = Static267.aClass262_2.method5560((int) (arg0.aLong192 >>> 32 & 0x7FFFFFFFL));
			}
			if (local58.alternativeIds != null) {
				local58 = local58.method4584(Class4_Sub6_Sub24.aClass114_1);
			}
			if (local58 != null) {
				local16 = local58.quests;
			}
		}
		if (local16 != null) {
			local14 = local14 + Class259.method5504(local16);
		}
		@Pc(137) int local137 = Js5ResourceProvider.aClass89_5.method2342(local14, Class70_Sub2.aClass57Array16);
		if (arg0.aBoolean511) {
			local137 += TextureOpVoronoiNoise.aClass57_22.getWidth() + 4;
		}
		return local137;
	}
@OriginalMember(owner = "client!km", name = "a", descriptor = "(CZ)Z")
	public static boolean method3198(@OriginalArg(0) char arg0) {
		if (Character.isISOControl(arg0)) {
			return false;
		} else if (TextureType.method3280(arg0)) {
			return true;
		} else {
			@Pc(18) char[] local18 = WorldMapElement.aCharArray2;
			for (@Pc(20) int local20 = 0; local20 < local18.length; local20++) {
				@Pc(26) char local26 = local18[local20];
				if (local26 == arg0) {
					return true;
				}
			}
			@Pc(46) char[] local46 = Class174.aCharArray6;
			for (@Pc(48) int local48 = 0; local48 < local46.length; local48++) {
				@Pc(54) char local54 = local46[local48];
				if (local54 == arg0) {
					return true;
				}
			}
			return false;
		}
	}
}
