package com.jagex.game.runetek5.cache.node.lru;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class186;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub17;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Static102;
import com.jagex.game.runetek5.deob.Static14;
import com.jagex.game.runetek5.deob.Static232;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.scene.CollisionMap;
import com.jagex.game.runetek5.cache.node.lru.*;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpPerlinNoise;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub6_Sub2;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.graphics.sprite.SpriteGL;
import com.jagex.game.runetek5.deob.Class48_Sub1_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpVoronoiNoise;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
@OriginalClass("client!ae")
public final class ClientScript extends CacheableNode {

	@OriginalMember(owner = "client!ae", name = "y", descriptor = "[I")
	public int[] anIntArray5;

	@OriginalMember(owner = "client!ae", name = "A", descriptor = "I")
	public int anInt54;

	@OriginalMember(owner = "client!ae", name = "B", descriptor = "[I")
	public int[] anIntArray6;

	@OriginalMember(owner = "client!ae", name = "C", descriptor = "I")
	public int anInt55;

	@OriginalMember(owner = "client!ae", name = "D", descriptor = "[Ljava/lang/String;")
	public String[] aStringArray1;

	@OriginalMember(owner = "client!ae", name = "E", descriptor = "I")
	public int anInt56;

	@OriginalMember(owner = "client!ae", name = "G", descriptor = "I")
	public int anInt58;

	@OriginalMember(owner = "client!ae", name = "I", descriptor = "Ljava/lang/String;")
	public String aString2;

	@OriginalMember(owner = "client!ae", name = "K", descriptor = "[Lclient!ib;")
	public CubeMapSpecularEffect[] aClass102Array1;

	@OriginalMember(owner = "client!ae", name = "N", descriptor = "Lclient!pn;")
	public Class186 aClass186_1;

	static {
		new Class83("You have left the channel.", "Du hast den Chatraum verlassen.", "Vous avez quitté le canal.", "Você saiu do canal.");
	}

@OriginalMember(owner = "client!ae", name = "z", descriptor = "Lclient!client;")
	public static client aClient1;
@OriginalMember(owner = "client!ae", name = "O", descriptor = "I")
	public static int anInt62;
@OriginalMember(owner = "client!ae", name = "F", descriptor = "I")
	public static int cameraPitch = -1;
@OriginalMember(owner = "client!ae", name = "a", descriptor = "(IILclient!t;IIILclient!ya;IIIIIZLclient!lp;I)Lclient!t;")
	public static Model method48(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Model arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) com.jagex.game.runetek5.graphics.Toolkit arg6, @OriginalArg(7) int arg7, @OriginalArg(9) int arg8, @OriginalArg(10) int arg9, @OriginalArg(11) int arg10, @OriginalArg(12) boolean arg11, @OriginalArg(13) SeqType arg12, @OriginalArg(14) int arg13) {
		if (arg2 == null) {
			return null;
		}
		@Pc(11) int local11 = 2055;
		if (arg12 != null) {
			local11 = arg12.getFunctionMask(arg10, -1, false) | 0x807;
			local11 &= 0xFFFFFDFF;
		}
		@Pc(48) long local48 = ((long) arg5 << 48) + (long) ((arg13 << 24) + (arg8 + (arg7 << 16))) + ((long) arg3 << 32);
		@Pc(50) LRUCache local50 = Class4_Sub1_Sub6_Sub2.aClass68_24;
		@Pc(58) Model local58;
		synchronized (Class4_Sub1_Sub6_Sub2.aClass68_24) {
			local58 = (Model) Class4_Sub1_Sub6_Sub2.aClass68_24.method1787(local48);
		}
		@Pc(163) int local163;
		@Pc(169) int local169;
		@Pc(175) int local175;
		@Pc(179) int local179;
		if (local58 == null || arg6.compareFunctionMasks(local58.P(), local11) != 0) {
			if (local58 != null) {
				local11 = arg6.combineFunctionMasks(local11, local58.P());
			}
			@Pc(98) byte local98;
			if (arg8 == 1) {
				local98 = 9;
			} else if (arg8 == 2) {
				local98 = 12;
			} else if (arg8 == 3) {
				local98 = 15;
			} else if (arg8 == 4) {
				local98 = 18;
			} else {
				local98 = 21;
			}
			@Pc(137) int[] local137 = new int[] { 64, 96, 128 };
			@Pc(156) Mesh local156 = new Mesh(local98 * 3 + 1, -local98 + local98 * 2 * 3, 0);
			local163 = local156.method3040(0, 0, 0);
			@Pc(167) int[][] local167 = new int[3][local98];
			@Pc(181) int local181;
			@Pc(205) int local205;
			for (local169 = 0; local169 < 3; local169++) {
				local175 = local137[local169];
				local179 = local137[local169];
				for (local181 = 0; local181 < local98; local181++) {
					@Pc(189) int local189 = (local181 << 14) / local98;
					@Pc(197) int local197 = local175 * Class50_Sub1.anIntArray225[local189] >> 15;
					local205 = Class50_Sub1.anIntArray224[local189] * local179 >> 15;
					local167[local169][local181] = local156.method3040(local205, 0, local197);
				}
			}
			for (local175 = 0; local175 < 3; local175++) {
				local179 = (local175 * 256 + 128) / 3;
				local181 = 256 - local179;
				@Pc(256) byte local256 = (byte) (arg13 * local179 + local181 * arg7 >> 8);
				@Pc(301) short local301 = (short) ((local179 * (arg5 & 0x7F) + local181 * (arg3 & 0x7F) & 0x7F00) + (local179 * (arg5 & 0xFC00) + (arg3 & 0xFC00) * local181 & 0xFC0000) + ((arg3 & 0x380) * local181 + local179 * (arg5 & 0x380) & 0x38000) >> 8);
				for (local205 = 0; local205 < local98; local205++) {
					if (local175 == 0) {
						local156.method3047(local167[0][local205], (short) -1, (byte) -1, local163, local167[0][(local205 + 1) % local98], local301, (byte) 1, local256);
					} else {
						local156.method3047(local167[local175][(local205 + 1) % local98], (short) -1, (byte) -1, local167[local175 - 1][local205], local167[local175 - 1][(local205 + 1) % local98], local301, (byte) 1, local256);
						local156.method3047(local167[local175][local205], (short) -1, (byte) -1, local167[local175 - 1][local205], local167[local175][(local205 + 1) % local98], local301, (byte) 1, local256);
					}
				}
			}
			local58 = arg6.createModel(local156, local11, Class6.anInt5454, 64, 768);
			@Pc(424) LRUCache local424 = Class4_Sub1_Sub6_Sub2.aClass68_24;
			synchronized (Class4_Sub1_Sub6_Sub2.aClass68_24) {
				Class4_Sub1_Sub6_Sub2.aClass68_24.method1779(local48, local58);
			}
		}
		@Pc(443) int local443 = (arg8 << 6) - 1;
		@Pc(446) int local446 = -local443;
		@Pc(449) int local449 = -local443;
		@Pc(451) int local451 = local443;
		local163 = local443;
		if (arg11) {
			if (arg9 > 1024 && arg9 < 7168) {
				local446 -= 128;
			}
			if (arg9 > 13312 || arg9 < 3072) {
				local449 -= 128;
			}
			if (arg9 > 9216 && arg9 < 15360) {
				local451 = local443 + 128;
			}
			if (arg9 > 5120 && arg9 < 11264) {
				local163 = local443 + 128;
			}
		}
		@Pc(496) int local496 = arg2.HA();
		local169 = arg2.NA();
		local175 = arg2.PA();
		if (local449 > local175) {
			local175 = local449;
		}
		if (local169 > local451) {
			local169 = local451;
		}
		if (local446 > local496) {
			local496 = local446;
		}
		local179 = arg2.addLight();
		if (local179 > local163) {
			local179 = local163;
		}
		@Pc(539) Class4_Sub1_Sub17 local539 = null;
		if (arg12 != null) {
			@Pc(546) int local546 = arg12.durations[arg10];
			local539 = SceneLocEntity.aClass55_1.method1391(local546 >> 16);
			arg10 = local546 & 0xFFFF;
		}
		if (local539 == null) {
			local58 = local58.copy((byte) 3, local11, true);
			local58.H(local169 - local496 >> 1, 128, local179 - local175 >> 1);
			local58.drawScaledColored(local169 + local496 >> 1, 0, local179 + local175 >> 1);
		} else {
			local58 = local58.copy((byte) 3, local11, true);
			local58.H(local169 - local496 >> 1, 128, local179 - local175 >> 1);
			local58.drawScaledColored(local496 + local169 >> 1, 0, local179 + local175 >> 1);
			local58.method4892(arg10, local539);
		}
		if (arg1 != 0) {
			local58.Z(arg1);
		}
		if (arg0 != 0) {
			local58.R(arg0);
		}
		if (arg4 != 0) {
			local58.drawScaledColored(0, arg4, 0);
		}
		return local58;
	}
@OriginalMember(owner = "client!ae", name = "a", descriptor = "(BLclient!sd;IIIIII[IIZ[IIII)I")
	public static int method49(@OriginalArg(1) CollisionMap arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) int[] arg7, @OriginalArg(9) int arg8, @OriginalArg(10) boolean arg9, @OriginalArg(11) int[] arg10, @OriginalArg(12) int arg11, @OriginalArg(13) int arg12, @OriginalArg(14) int arg13) {
		for (@Pc(7) int local7 = 0; local7 < 128; local7++) {
			for (@Pc(11) int local11 = 0; local11 < 128; local11++) {
				CubeMapSpecularEffect.anIntArrayArray30[local7][local11] = 0;
				Static14.anIntArrayArray1[local7][local11] = 99999999;
			}
		}
		@Pc(55) boolean local55;
		if (arg5 == 1) {
			local55 = SpriteGL.method5296(arg3, arg6, arg13, arg2, arg0, arg4, arg8, arg12, arg11, arg1);
		} else if (arg5 == 2) {
			local55 = Class4_Sub7.method634(arg13, arg0, arg6, arg3, arg12, arg1, arg4, arg11, arg2, arg8);
		} else {
			local55 = Class4_Sub6_Sub22.method3499(arg2, arg12, arg6, arg1, arg0, arg4, arg11, arg3, arg5, arg8, arg13);
		}
		@Pc(89) int local89 = arg1 - 64;
		@Pc(93) int local93 = arg11 - 64;
		@Pc(95) int local95 = TextureOpVoronoiNoise.anInt6521;
		@Pc(97) int local97 = Static102.anInt2045;
		@Pc(105) int local105;
		@Pc(107) int local107;
		@Pc(114) int local114;
		if (!local55) {
			if (!arg9) {
				return -1;
			}
			local105 = Integer.MAX_VALUE;
			local107 = Integer.MAX_VALUE;
			for (local114 = arg2 - 10; local114 <= arg2 + 10; local114++) {
				for (@Pc(120) int local120 = arg4 - 10; local120 <= arg4 + 10; local120++) {
					@Pc(127) int local127 = local114 - local89;
					@Pc(132) int local132 = local120 - local93;
					if (local127 >= 0 && local132 >= 0 && local127 < 128 && local132 < 128 && Static14.anIntArrayArray1[local127][local132] < 100) {
						@Pc(161) int local161 = 0;
						if (local114 < arg2) {
							local161 = arg2 - local114;
						} else if (local114 > arg3 + arg2 - 1) {
							local161 = local114 + 1 - arg2 - arg3;
						}
						@Pc(193) int local193 = 0;
						if (arg4 > local120) {
							local193 = arg4 - local120;
						} else if (local120 > arg4 + arg12 - 1) {
							local193 = local120 + 1 - arg4 - arg12;
						}
						@Pc(232) int local232 = local161 * local161 + local193 * local193;
						if (local105 > local232 || local232 == local105 && local107 > Static14.anIntArrayArray1[local127][local132]) {
							local107 = Static14.anIntArrayArray1[local127][local132];
							local95 = local114;
							local105 = local232;
							local97 = local120;
						}
					}
				}
			}
			if (~local105 == Integer.MIN_VALUE) {
				return -1;
			}
		}
		if (arg1 == local95 && local97 == arg11) {
			return 0;
		}
		@Pc(299) byte local299 = 0;
		TextureOpPerlinNoise.anIntArray182[0] = local95;
		local105 = local299 + 1;
		PingRequest.recentVarcUpdates[0] = local97;
		@Pc(326) int local326;
		local107 = local326 = CubeMapSpecularEffect.anIntArrayArray30[local95 - local89][local97 - local93];
		while (arg1 != local95 || arg11 != local97) {
			if (local107 != local326) {
				local326 = local107;
				TextureOpPerlinNoise.anIntArray182[local105] = local95;
				PingRequest.recentVarcUpdates[local105++] = local97;
			}
			if ((local107 & 0x1) != 0) {
				local97++;
			} else if ((local107 & 0x4) != 0) {
				local97--;
			}
			if ((local107 & 0x2) != 0) {
				local95++;
			} else if ((local107 & 0x8) != 0) {
				local95--;
			}
			local107 = CubeMapSpecularEffect.anIntArrayArray30[local95 - local89][local97 - local93];
		}
		local114 = 0;
		while (local105-- > 0) {
			arg10[local114] = TextureOpPerlinNoise.anIntArray182[local105];
			arg7[local114++] = PingRequest.recentVarcUpdates[local105];
			if (local114 >= arg10.length) {
				break;
			}
		}
		return local114;
	}
@OriginalMember(owner = "client!ae", name = "b", descriptor = "(II)V")
	public static void method51(@OriginalArg(0) int arg0) {
		Class48_Sub1_Sub1.anInt4879 = arg0;
		Static232.aClass68_25.method1777();
	}
}
