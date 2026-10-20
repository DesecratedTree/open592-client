package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.sign.SignedResource;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.entity.DynamicWall;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class4_Sub19;
import com.jagex.game.runetek5.deob.Class190;
import com.jagex.game.runetek5.deob.Class26;
import com.jagex.game.runetek5.config.identitykit.IdkType;
import com.jagex.game.runetek5.deob.Class120;
import com.jagex.game.runetek5.toolkit.opengl.GlParticleRenderer;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchNode;
import com.jagex.game.runetek5.graphics.font.FontMetrics;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.graphics.model.particle.PointEntity;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.config.flo.FloTypeList;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.Huffman;
import com.jagex.game.runetek5.deob.Class70_Sub1;
import com.jagex.game.runetek5.deob.Class25;
import com.jagex.game.runetek5.deob.Class48;
@OriginalClass("client!ov")
public final class Class4_Sub36 extends Country {

	@OriginalMember(owner = "client!ov", name = "m", descriptor = "[[[B")
	public byte[][][] aByteArrayArrayArray4;

	@OriginalMember(owner = "client!ov", name = "n", descriptor = "[Lclient!qt;")
	public SignedResource[] aClass199Array1;

	@OriginalMember(owner = "client!ov", name = "p", descriptor = "I")
	public int anInt5309;

	@OriginalMember(owner = "client!ov", name = "q", descriptor = "[I")
	public int[] anIntArray349;

	@OriginalMember(owner = "client!ov", name = "r", descriptor = "[I")
	public int[] anIntArray350;

	@OriginalMember(owner = "client!ov", name = "s", descriptor = "I")
	public int anInt5310;

	@OriginalMember(owner = "client!ov", name = "t", descriptor = "[Lclient!qt;")
	public SignedResource[] aClass199Array2;

	@OriginalMember(owner = "client!ov", name = "w", descriptor = "[I")
	public int[] anIntArray351;

@OriginalMember(owner = "client!ov", name = "v", descriptor = "[[Lclient!uu;")
	public static com.jagex.game.runetek5.config.rsinterface.Component[][] aClass247ArrayArray2;
@OriginalMember(owner = "client!ov", name = "o", descriptor = "Ljava/lang/String;")
	public static String aString52 = "";
@OriginalMember(owner = "client!ov", name = "a", descriptor = "(I)V")
	public static void method4069() {
		ParticleNode.aClass4_Sub12_Sub1_5.currentPosition = 0;
		Static410.anInt6735 = 0;
		Static380.aClass22_294 = null;
		Class190.aClass22_257 = null;
		Class120.anInt5353 = 0;
		IdkType.aClass22_246 = null;
		Class25.aClass22_42 = null;
		Class14.aClass4_Sub12_Sub1_3.currentPosition = 0;
		PointEntity.anInt4075 = 0;
		Class70_Sub1.method1816();
		FloTypeList.method3148();
		for (@Pc(33) int local33 = 0; local33 < 2048; local33++) {
			Static267.aClass16_Sub1_Sub5_Sub1Array1[local33] = null;
		}
		Static1.aClass16_Sub1_Sub5_Sub1_1 = null;
		for (@Pc(47) int local47 = 0; local47 < Class93.aClass16_Sub1_Sub5_Sub2Array1.length; local47++) {
			@Pc(53) NPCEntity local53 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local47];
			if (local53 != null) {
				local53.anInt6034 = -1;
			}
		}
		Class48.method4403();
		Class3_Sub3.anInt2386 = 1;
		Huffman.method2932(30);
		for (@Pc(72) int local72 = 0; local72 < 100; local72++) {
			PingRequest.aBooleanArray21[local72] = true;
		}
		Static1.method5();
		StillWaterEffect.aLong212 = 0L;
		NativeBillboardFace.aClass4_Sub42_2 = null;
	}
@OriginalMember(owner = "client!ov", name = "a", descriptor = "(Lclient!jj;IIIII)V")
	public static void method4070(@OriginalArg(0) Class120 arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5) {
		arg0.anInt5351 = 0;
		@Pc(7) int local7;
		label56: for (@Pc(4) int local4 = arg2; local4 <= arg4; local4++) {
			label54: for (local7 = arg3; local7 <= arg5; local7++) {
				@Pc(16) long local16 = Terrain.aLongArrayArrayArray2[arg1][local4][local7];
				@Pc(18) long local18 = 0L;
				while (true) {
					label49: while (true) {
						if (local18 > 48L) {
							continue label54;
						}
						@Pc(27) int local27 = (int) (local16 >>> (int) local18 & 0xFFFFL);
						if (local27 <= 0) {
							continue label54;
						}
						@Pc(35) Class191 local35 = StructTypeList.aClass191Array1[local27 - 1];
						for (@Pc(37) int local37 = 0; local37 < arg0.anInt5351; local37++) {
							if (arg0.aClass4_Sub21Array3[local37] == local35.aClass4_Sub21_2) {
								local18 += 16L;
								continue label49;
							}
						}
						arg0.aClass4_Sub21Array3[arg0.anInt5351++] = local35.aClass4_Sub21_2;
						if (arg0.anInt5351 == 4) {
							break label56;
						}
						local18 += 16L;
					}
				}
			}
		}
		for (local7 = arg0.anInt5351; local7 < 4; local7++) {
			arg0.aClass4_Sub21Array3[local7] = null;
		}
	}
@OriginalMember(owner = "client!ov", name = "a", descriptor = "(IIBIIIII)V")
	public static void method4071(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6) {
		if (arg5 < 0 || arg6 < 0 || GLBillowTextureData.anInt5666 - 1 <= arg5 || arg6 >= Static283.anInt5187 - 1) {
			return;
		}
		if (Class70.aClass164ArrayArrayArray5 == null) {
			return;
		}
		@Pc(67) GameObject local67;
		if (arg3 != 0) {
			if (arg3 != 1) {
				if (arg3 == 2) {
					local67 = (GameObject) Class26.method648(arg0, arg5, arg6, GameObject.class);
					if (local67 != null) {
						if (arg4 == 11) {
							arg4 = 10;
						}
						if (!(local67 instanceof SceneLocEntity)) {
							Class21.method524(arg0, arg5, arg4, arg2, arg1, local67.method5350(), arg6, arg3);
							return;
						}
						((SceneLocEntity) local67).aClass23_2.method587(arg2);
						return;
					}
				} else if (arg3 == 3) {
					local67 = (GameObject) Class4_Sub19.method2096(arg0, arg5, arg6);
					if (local67 == null) {
						return;
					}
					if (!(local67 instanceof Class16_Sub4_Sub2)) {
						Class21.method524(arg0, arg5, arg4, arg2, arg1, local67.method5350(), arg6, arg3);
						return;
					}
					((Class16_Sub4_Sub2) local67).aClass23_1.method587(arg2);
				} else {
					return;
				}
				return;
			}
			local67 = (GameObject) GlParticleRenderer.method918(arg0, arg5, arg6);
			if (local67 != null) {
				if (!(local67 instanceof Class16_Sub2_Sub3)) {
					@Pc(148) int local148 = local67.method5350();
					if (arg4 != 4 && arg4 != 5) {
						if (arg4 == 6) {
							Class21.method524(arg0, arg5, 4, arg2, arg1 + 4, local148, arg6, arg3);
							return;
						}
						if (arg4 == 7) {
							Class21.method524(arg0, arg5, 4, arg2, (arg1 + 2 & 0x3) + 4, local148, arg6, arg3);
						} else if (arg4 == 8) {
							Class21.method524(arg0, arg5, 4, arg2, arg1 + 4, local148, arg6, arg3);
							Class21.method524(arg0, arg5, 4, arg2, (arg1 + 2 & 0x3) + 4, local148, arg6, arg3);
							return;
						}
						return;
					}
					Class21.method524(arg0, arg5, 4, arg2, arg1, local148, arg6, arg3);
					return;
				}
				((Class16_Sub2_Sub3) local67).aClass23_3.method587(arg2);
				return;
			}
			return;
		}
		local67 = (GameObject) MusicPatchNode.method3449(arg0, arg5, arg6);
		@Pc(256) GameObject local256 = (GameObject) FontMetrics.method2344(arg0, arg5, arg6);
		if (local67 != null && arg4 != 2) {
			if (local67 instanceof DynamicWall) {
				((DynamicWall) local67).aClass23_4.method587(arg2);
			} else {
				Class21.method524(arg0, arg5, arg4, arg2, arg1, local67.method5350(), arg6, arg3);
			}
		}
		if (local256 == null) {
			return;
		}
		if (local256 instanceof DynamicWall) {
			((DynamicWall) local256).aClass23_4.method587(arg2);
			return;
		}
		Class21.method524(arg0, arg5, arg4, arg2, arg1, local256.method5350(), arg6, arg3);
		return;
	}
}
