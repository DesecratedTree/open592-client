package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.scene.CollisionMap;
import com.jagex.game.runetek5.config.bas.BasType;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpPerlinNoise;
import com.jagex.game.runetek5.deob.Class4_Sub19;
import com.jagex.game.runetek5.deob.OutputStream_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub16;
import com.jagex.game.runetek5.deob.Class50;
import com.jagex.game.runetek5.client.input.keyboard.KeyRecordEntry;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.cache.node.GlGroundLayer;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.cache.node.MonochromeImageCacheSlot;
import com.jagex.game.runetek5.config.enumtype.EnumTypeList;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Class222;
import com.jagex.game.runetek5.graphics.texture.RectangularTextureGL;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpVoronoiNoise;
@OriginalClass("client!bm")
public final class Class4_Sub7 extends Country {

	@OriginalMember(owner = "client!bm", name = "o", descriptor = "Ljava/lang/String;")
	public String aString12;

	@OriginalMember(owner = "client!bm", name = "<init>", descriptor = "()V")
	private Class4_Sub7() {
	}

	@OriginalMember(owner = "client!bm", name = "<init>", descriptor = "(Ljava/lang/String;)V")
	public Class4_Sub7(@OriginalArg(0) String arg0) {
		this.aString12 = arg0;
	}

@OriginalMember(owner = "client!bm", name = "m", descriptor = "Lclient!ag;")
	public static Class7 aClass7_2;
@OriginalMember(owner = "client!bm", name = "p", descriptor = "I")
	public static int anInt602;
@OriginalMember(owner = "client!bm", name = "q", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_18 = new Class83("Connecting to update server", "Verbindung mit Update-Server...", "Connexion au serveur de mise à jour en cours", "Conectando ao servidor de atualização");
@OriginalMember(owner = "client!bm", name = "r", descriptor = "[I")
	public static final int[] anIntArray36 = new int[50];
@OriginalMember(owner = "client!bm", name = "u", descriptor = "I")
	public static int anInt605 = 0;
@OriginalMember(owner = "client!bm", name = "v", descriptor = "I")
	public static int anInt606 = 0;
@OriginalMember(owner = "client!bm", name = "a", descriptor = "(BLclient!lh;)I")
	public static int method631(@OriginalArg(1) PlayerEntity arg0) {
		@Pc(14) int local14 = arg0.anInt4333;
		@Pc(18) BasType local18 = arg0.method4757();
		if (arg0.aBoolean562) {
			local14 = arg0.walkingSoundEffectId;
		} else if (local18.anInt4669 == arg0.anInt6076 || arg0.anInt6076 == local18.anInt4656 || local18.anInt4665 == arg0.anInt6076 || local18.anInt4686 == arg0.anInt6076) {
			local14 = arg0.anInt4322;
		} else if (arg0.anInt6076 == local18.anInt4667 || local18.anInt4681 == arg0.anInt6076 || arg0.anInt6076 == local18.anInt4688 || arg0.anInt6076 == local18.anInt4675) {
			local14 = arg0.anInt4319;
		}
		return local14;
	}
@OriginalMember(owner = "client!bm", name = "a", descriptor = "(IIILclient!rs;)V")
	public static void method632(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) NPCEntity arg2) {
		if (arg0 == arg2.anInt6021 && arg0 != -1) {
			@Pc(25) SeqType local25 = SceneLocEntity.aClass55_1.method1397(arg0);
			@Pc(28) int local28 = local25.loopType;
			if (local28 == 1) {
				arg2.anInt6046 = arg1;
				arg2.anInt6060 = 0;
				arg2.anInt6058 = 1;
				arg2.anInt6012 = 0;
				arg2.anInt6073 = 0;
				Class4_Sub1_Sub2.method156(arg2.anInt6893, arg2.anInt6060, arg2.aByte82, arg2.anInt6892, local25, false);
			}
			if (local28 == 2) {
				arg2.anInt6012 = 0;
				return;
			}
		} else if (arg0 == -1 || arg2.anInt6021 == -1 || SceneLocEntity.aClass55_1.method1397(arg0).priority >= SceneLocEntity.aClass55_1.method1397(arg2.anInt6021).priority) {
			arg2.anInt6012 = 0;
			arg2.anInt6058 = 1;
			arg2.anInt6073 = 0;
			arg2.anInt6087 = arg2.pathPointer;
			arg2.anInt6046 = arg1;
			arg2.anInt6021 = arg0;
			arg2.anInt6060 = 0;
			if (arg2.anInt6021 == -1) {
				return;
			}
			Class4_Sub1_Sub2.method156(arg2.anInt6893, arg2.anInt6060, arg2.aByte82, arg2.anInt6892, SceneLocEntity.aClass55_1.method1397(arg2.anInt6021), false);
		}
	}
@OriginalMember(owner = "client!bm", name = "a", descriptor = "(IZ)V")
	public static void method633(@OriginalArg(1) boolean arg0) {
		if (arg0 && Static148.area != null) {
			Class4_Sub6_Sub16.anInt3719 = Static148.area.anInt1012;
		} else {
			Class4_Sub6_Sub16.anInt3719 = -1;
		}
		Class50.anInt3429 = 0;
		Static148.area = null;
		GlGroundLayer.aClass247_2 = null;
		EnvironmentSampler.aClass183_29 = null;
		Static148.freeBuffers();
		Static148.worldMapCategories.method4138();
		EnumTypeList.aClass212_7 = null;
		Class222.aClass212_8 = null;
		Class9.anInt6920 = -1;
		MonochromeImageCacheSlot.aClass212_1 = null;
		Static258.aClass212_6 = null;
		ParticleList.aClass212_5 = null;
		RectangularTextureGL.aClass212_3 = null;
		Static148.staticElements = null;
		SeqType.aClass57_18 = null;
		OutputStream_Sub1.anInt2674 = -1;
		GlGroundLayer.aClass212_2 = null;
		Class4_Sub19.aClass212_4 = null;
		Static148.MECTYPE_LIST.method4127();
		Static148.MAP_SPRITE_TYPE_LIST.method5591(64, 64);
		Static148.MECTYPE_LIST.method4131(64, 128);
		Static148.LOCTYPE_LIST.method5565(64);
		KeyRecordEntry.aClass26_1.method646(64);
	}
@OriginalMember(owner = "client!bm", name = "a", descriptor = "(BILclient!sd;IIIIIIII)Z")
	public static boolean method634(@OriginalArg(1) int arg0, @OriginalArg(2) CollisionMap arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) int arg7, @OriginalArg(9) int arg8, @OriginalArg(10) int arg9) {
		@Pc(7) int local7 = arg5;
		@Pc(9) int local9 = arg7;
		@Pc(23) int local23 = arg5 - 64;
		CubeMapSpecularEffect.anIntArrayArray30[64][64] = 99;
		@Pc(33) int local33 = arg7 - 64;
		Static14.anIntArrayArray1[64][64] = 0;
		@Pc(41) byte local41 = 0;
		@Pc(43) int local43 = 0;
		TextureOpPerlinNoise.anIntArray182[0] = arg5;
		@Pc(50) int local50 = local41 + 1;
		PingRequest.recentVarcUpdates[0] = arg7;
		@Pc(55) int[][] local55 = arg1.collisionFlags;
		while (local50 != local43) {
			local7 = TextureOpPerlinNoise.anIntArray182[local43];
			local9 = PingRequest.recentVarcUpdates[local43];
			@Pc(70) int local70 = local9 - local33;
			local43 = local43 + 1 & 0xFFF;
			@Pc(81) int local81 = local7 - local23;
			@Pc(87) int local87 = local7 - arg1.anInt6230;
			@Pc(92) int local92 = local9 - arg1.anInt6235;
			if (arg0 == -4) {
				if (local7 == arg8 && local9 == arg6) {
					Static102.anInt2045 = local9;
					TextureOpVoronoiNoise.anInt6521 = local7;
					return true;
				}
			} else if (arg0 == -3) {
				if (Toolkit.method4305(2, arg6, 2, arg4, arg3, local9, local7, arg8)) {
					Static102.anInt2045 = local9;
					TextureOpVoronoiNoise.anInt6521 = local7;
					return true;
				}
			} else if (arg0 == -2) {
				if (arg1.method4844(arg8, 2, arg4, local7, arg2, arg6, local9, arg3, 2)) {
					Static102.anInt2045 = local9;
					TextureOpVoronoiNoise.anInt6521 = local7;
					return true;
				}
			} else if (arg0 == -1) {
				if (arg1.method4840(arg3, 2, arg8, arg2, arg4, arg6, local7, local9)) {
					Static102.anInt2045 = local9;
					TextureOpVoronoiNoise.anInt6521 = local7;
					return true;
				}
			} else if (arg0 == 0 || arg0 == 1 || arg0 == 2 || arg0 == 3 || arg0 == 9) {
				if (arg1.method4842(local9, arg9, arg6, 2, local7, arg8, arg0)) {
					TextureOpVoronoiNoise.anInt6521 = local7;
					Static102.anInt2045 = local9;
					return true;
				}
			} else if (arg1.method4849(arg9, local7, arg0, arg8, local9, arg6, 2)) {
				TextureOpVoronoiNoise.anInt6521 = local7;
				Static102.anInt2045 = local9;
				return true;
			}
			@Pc(251) int local251 = Static14.anIntArrayArray1[local81][local70] + 1;
			if (local81 > 0 && CubeMapSpecularEffect.anIntArrayArray30[local81 - 1][local70] == 0 && (local55[local87 - 1][local92] & 0x43A40000) == 0 && (local55[local87 - 1][local92 + 1] & 0x4E240000) == 0) {
				TextureOpPerlinNoise.anIntArray182[local50] = local7 - 1;
				PingRequest.recentVarcUpdates[local50] = local9;
				local50 = local50 + 1 & 0xFFF;
				CubeMapSpecularEffect.anIntArrayArray30[local81 - 1][local70] = 2;
				Static14.anIntArrayArray1[local81 - 1][local70] = local251;
			}
			if (local81 < 126 && CubeMapSpecularEffect.anIntArrayArray30[local81 + 1][local70] == 0 && (local55[local87 + 2][local92] & 0x60E40000) == 0 && (local55[local87 + 2][local92 + 1] & 0x78240000) == 0) {
				TextureOpPerlinNoise.anIntArray182[local50] = local7 + 1;
				PingRequest.recentVarcUpdates[local50] = local9;
				CubeMapSpecularEffect.anIntArrayArray30[local81 + 1][local70] = 8;
				local50 = local50 + 1 & 0xFFF;
				Static14.anIntArrayArray1[local81 + 1][local70] = local251;
			}
			if (local70 > 0 && CubeMapSpecularEffect.anIntArrayArray30[local81][local70 - 1] == 0 && (local55[local87][local92 - 1] & 0x43A40000) == 0 && (local55[local87 + 1][local92 - 1] & 0x60E40000) == 0) {
				TextureOpPerlinNoise.anIntArray182[local50] = local7;
				PingRequest.recentVarcUpdates[local50] = local9 - 1;
				CubeMapSpecularEffect.anIntArrayArray30[local81][local70 - 1] = 1;
				local50 = local50 + 1 & 0xFFF;
				Static14.anIntArrayArray1[local81][local70 - 1] = local251;
			}
			if (local70 < 126 && CubeMapSpecularEffect.anIntArrayArray30[local81][local70 + 1] == 0 && (local55[local87][local92 + 2] & 0x4E240000) == 0 && (local55[local87 + 1][local92 + 2] & 0x78240000) == 0) {
				TextureOpPerlinNoise.anIntArray182[local50] = local7;
				PingRequest.recentVarcUpdates[local50] = local9 + 1;
				local50 = local50 + 1 & 0xFFF;
				CubeMapSpecularEffect.anIntArrayArray30[local81][local70 + 1] = 4;
				Static14.anIntArrayArray1[local81][local70 + 1] = local251;
			}
			if (local81 > 0 && local70 > 0 && CubeMapSpecularEffect.anIntArrayArray30[local81 - 1][local70 - 1] == 0 && (local55[local87 - 1][local92] & 0x4FA40000) == 0 && (local55[local87 - 1][local92 - 1] & 0x43A40000) == 0 && (local55[local87][local92 - 1] & 0x63E40000) == 0) {
				TextureOpPerlinNoise.anIntArray182[local50] = local7 - 1;
				PingRequest.recentVarcUpdates[local50] = local9 - 1;
				CubeMapSpecularEffect.anIntArrayArray30[local81 - 1][local70 - 1] = 3;
				local50 = local50 + 1 & 0xFFF;
				Static14.anIntArrayArray1[local81 - 1][local70 - 1] = local251;
			}
			if (local81 < 126 && local70 > 0 && CubeMapSpecularEffect.anIntArrayArray30[local81 + 1][local70 - 1] == 0 && (local55[local87 + 1][local92 - 1] & 0x63E40000) == 0 && (local55[local87 + 2][local92 - 1] & 0x60E40000) == 0 && (local55[local87 + 2][local92] & 0x78E40000) == 0) {
				TextureOpPerlinNoise.anIntArray182[local50] = local7 + 1;
				PingRequest.recentVarcUpdates[local50] = local9 - 1;
				local50 = local50 + 1 & 0xFFF;
				CubeMapSpecularEffect.anIntArrayArray30[local81 + 1][local70 - 1] = 9;
				Static14.anIntArrayArray1[local81 + 1][local70 - 1] = local251;
			}
			if (local81 > 0 && local70 < 126 && CubeMapSpecularEffect.anIntArrayArray30[local81 - 1][local70 + 1] == 0 && (local55[local87 - 1][local92 + 1] & 0x4FA40000) == 0 && (local55[local87 - 1][local92 + 2] & 0x4E240000) == 0 && (local55[local87][local92 + 2] & 0x7E240000) == 0) {
				TextureOpPerlinNoise.anIntArray182[local50] = local7 - 1;
				PingRequest.recentVarcUpdates[local50] = local9 + 1;
				CubeMapSpecularEffect.anIntArrayArray30[local81 - 1][local70 + 1] = 6;
				local50 = local50 + 1 & 0xFFF;
				Static14.anIntArrayArray1[local81 - 1][local70 + 1] = local251;
			}
			if (local81 < 126 && local70 < 126 && CubeMapSpecularEffect.anIntArrayArray30[local81 + 1][local70 + 1] == 0 && (local55[local87 + 1][local92 + 2] & 0x7E240000) == 0 && (local55[local87 + 2][local92 + 2] & 0x78240000) == 0 && (local55[local87 + 2][local92 + 1] & 0x78E40000) == 0) {
				TextureOpPerlinNoise.anIntArray182[local50] = local7 + 1;
				PingRequest.recentVarcUpdates[local50] = local9 + 1;
				local50 = local50 + 1 & 0xFFF;
				CubeMapSpecularEffect.anIntArrayArray30[local81 + 1][local70 + 1] = 12;
				Static14.anIntArrayArray1[local81 + 1][local70 + 1] = local251;
			}
		}
		Static102.anInt2045 = local9;
		TextureOpVoronoiNoise.anInt6521 = local7;
		return false;
	}
}
