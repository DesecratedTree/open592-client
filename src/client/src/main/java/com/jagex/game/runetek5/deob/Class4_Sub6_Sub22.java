package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.config.flu.FluTypeList;
import com.jagex.game.runetek5.scene.CollisionMap;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpPerlinNoise;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Class48_Sub1_Sub1;
import com.jagex.game.runetek5.deob.Class101;
import com.jagex.game.runetek5.deob.Class38;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.cache.node.lru.StructType;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpVoronoiNoise;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.deob.Class78_Sub1;
@OriginalClass("client!lu")
public final class Class4_Sub6_Sub22 extends TextureOperation {

	@OriginalMember(owner = "client!lu", name = "J", descriptor = "I")
	private int anInt4463 = 32768;

	static {
		new Class83(" left the channel.", " hat den Chatraum verlassen.", " a quitté le canal.", " deixou o canal.");
		new Class83("Your friendlist is full, max of 100 for free users, and 200 for members.", "Ihre Freunde-Liste ist voll!", "Votre liste d'amis est pleine (100 noms maximum pour la version gratuite et 200 pour les abonnés).", "Sua lista de amigos está cheia. O limite é 100 para usuários não pagantes, e 200 para membros.");
	}

	@OriginalMember(owner = "client!lu", name = "<init>", descriptor = "()V")
	public Class4_Sub6_Sub22() {
		super(3, false);
	}

	@OriginalMember(owner = "client!lu", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[] local11 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			@Pc(29) int[] local29 = this.getChildMonochromeOutput(arg0, 1);
			@Pc(35) int[] local35 = this.getChildMonochromeOutput(arg0, 2);
			for (@Pc(37) int local37 = 0; local37 < MidiPcmStream.anInt1616; local37++) {
				@Pc(47) int local47 = local29[local37] >> 4 & 0xFF;
				@Pc(56) int local56 = local35[local37] * this.anInt4463 >> 12;
				@Pc(64) int local64 = StructType.anIntArray416[local47] * local56 >> 12;
				@Pc(72) int local72 = local56 * Static277.anIntArray336[local47] >> 12;
				@Pc(80) int local80 = local37 + (local64 >> 12) & Class78_Sub1.anInt3883;
				@Pc(88) int local88 = Static211.anInt3929 & (local72 >> 12) + arg0;
				@Pc(94) int[] local94 = this.getChildMonochromeOutput(local88, 0);
				local11[local37] = local94[local80];
			}
		}
		return local11;
	}

	@OriginalMember(owner = "client!lu", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.anInt4463 = arg1.method2536() << 4;
		} else if (arg0 == 1) {
			super.monochrome = arg1.method2490() == 1;
		}
	}

	@OriginalMember(owner = "client!lu", name = "a", descriptor = "(Z)V")
	@Override
	public void postDecode() {
		LRUCache.method1784();
	}

	@OriginalMember(owner = "client!lu", name = "a", descriptor = "(BI)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[][] local11 = super.colorImageCache.get(arg0);
		if (super.colorImageCache.dirty) {
			@Pc(21) int[] local21 = this.getChildMonochromeOutput(arg0, 1);
			@Pc(27) int[] local27 = this.getChildMonochromeOutput(arg0, 2);
			@Pc(31) int[] local31 = local11[0];
			@Pc(35) int[] local35 = local11[1];
			@Pc(39) int[] local39 = local11[2];
			for (@Pc(41) int local41 = 0; local41 < MidiPcmStream.anInt1616; local41++) {
				@Pc(53) int local53 = local21[local41] * 255 >> 12 & 0xFF;
				@Pc(62) int local62 = this.anInt4463 * local27[local41] >> 12;
				@Pc(70) int local70 = StructType.anIntArray416[local53] * local62 >> 12;
				@Pc(78) int local78 = Static277.anIntArray336[local53] * local62 >> 12;
				@Pc(86) int local86 = Class78_Sub1.anInt3883 & local41 + (local70 >> 12);
				@Pc(94) int local94 = Static211.anInt3929 & arg0 + (local78 >> 12);
				@Pc(100) int[][] local100 = this.getChildColorOutput(0, local94);
				local31[local41] = local100[0][local86];
				local35[local41] = local100[1][local86];
				local39[local41] = local100[2][local86];
			}
		}
		return local11;
	}

@OriginalMember(owner = "client!lu", name = "N", descriptor = "F")
	public static float aFloat140;
@OriginalMember(owner = "client!lu", name = "O", descriptor = "Lclient!qd;")
	public static FluTypeList aClass192_2;
@OriginalMember(owner = "client!lu", name = "D", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_93 = new Class83("glow3:", "leuchten3:", "brillant3:", "brilho3:");
@OriginalMember(owner = "client!lu", name = "H", descriptor = "Z")
	public static boolean aBoolean411 = false;
@OriginalMember(owner = "client!lu", name = "L", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_199 = new Class22(19, 6);
@OriginalMember(owner = "client!lu", name = "a", descriptor = "(IIIIILclient!sd;IIIIII)Z")
	public static boolean method3499(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) CollisionMap arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) int arg7, @OriginalArg(9) int arg8, @OriginalArg(10) int arg9, @OriginalArg(11) int arg10) {
		@Pc(7) int local7 = arg3;
		@Pc(9) int local9 = arg6;
		@Pc(22) int local22 = arg3 - 64;
		@Pc(27) int local27 = arg6 - 64;
		CubeMapSpecularEffect.anIntArrayArray30[64][64] = 99;
		Static14.anIntArrayArray1[64][64] = 0;
		@Pc(41) byte local41 = 0;
		@Pc(43) int local43 = 0;
		TextureOpPerlinNoise.anIntArray182[0] = arg3;
		@Pc(50) int local50 = local41 + 1;
		PingRequest.recentVarcUpdates[0] = arg6;
		@Pc(55) int[][] local55 = arg4.collisionFlags;
		while (true) {
			label282: while (true) {
				@Pc(74) int local74;
				@Pc(69) int local69;
				@Pc(86) int local86;
				@Pc(92) int local92;
				@Pc(247) int local247;
				@Pc(289) int local289;
				do {
					do {
						do {
							label259: do {
								if (local50 == local43) {
									TextureOpVoronoiNoise.anInt6521 = local7;
									Static102.anInt2045 = local9;
									return false;
								}
								local7 = TextureOpPerlinNoise.anIntArray182[local43];
								local9 = PingRequest.recentVarcUpdates[local43];
								local69 = local9 - local27;
								local74 = local7 - local22;
								local43 = local43 + 1 & 0xFFF;
								local86 = local7 - arg4.anInt6230;
								local92 = local9 - arg4.anInt6235;
								if (arg10 == -4) {
									if (local7 == arg0 && local9 == arg5) {
										Static102.anInt2045 = local9;
										TextureOpVoronoiNoise.anInt6521 = local7;
										return true;
									}
								} else if (arg10 == -3) {
									if (Toolkit.method4305(arg8, arg5, arg8, arg1, arg7, local9, local7, arg0)) {
										TextureOpVoronoiNoise.anInt6521 = local7;
										Static102.anInt2045 = local9;
										return true;
									}
								} else if (arg10 == -2) {
									if (arg4.method4844(arg0, arg8, arg1, local7, arg2, arg5, local9, arg7, arg8)) {
										TextureOpVoronoiNoise.anInt6521 = local7;
										Static102.anInt2045 = local9;
										return true;
									}
								} else if (arg10 == -1) {
									if (arg4.method4840(arg7, arg8, arg0, arg2, arg1, arg5, local7, local9)) {
										Static102.anInt2045 = local9;
										TextureOpVoronoiNoise.anInt6521 = local7;
										return true;
									}
								} else if (arg10 == 0 || arg10 == 1 || arg10 == 2 || arg10 == 3 || arg10 == 9) {
									if (arg4.method4842(local9, arg9, arg5, arg8, local7, arg0, arg10)) {
										Static102.anInt2045 = local9;
										TextureOpVoronoiNoise.anInt6521 = local7;
										return true;
									}
								} else if (arg4.method4849(arg9, local7, arg10, arg0, local9, arg5, arg8)) {
									TextureOpVoronoiNoise.anInt6521 = local7;
									Static102.anInt2045 = local9;
									return true;
								}
								local247 = Static14.anIntArrayArray1[local74][local69] + 1;
								if (local74 > 0 && CubeMapSpecularEffect.anIntArrayArray30[local74 - 1][local69] == 0 && (local55[local86 - 1][local92] & 0x43A40000) == 0 && (local55[local86 - 1][local92 + arg8 - 1] & 0x4E240000) == 0) {
									local289 = 1;
									while (true) {
										if (local289 >= arg8 - 1) {
											TextureOpPerlinNoise.anIntArray182[local50] = local7 - 1;
											PingRequest.recentVarcUpdates[local50] = local9;
											CubeMapSpecularEffect.anIntArrayArray30[local74 - 1][local69] = 2;
											local50 = local50 + 1 & 0xFFF;
											Static14.anIntArrayArray1[local74 - 1][local69] = local247;
											break;
										}
										if ((local55[local86 - 1][local92 + local289] & 0x4FA40000) != 0) {
											break;
										}
										local289++;
									}
								}
								if (local74 < 128 - arg8 && CubeMapSpecularEffect.anIntArrayArray30[local74 + 1][local69] == 0 && (local55[local86 + arg8][local92] & 0x60E40000) == 0 && (local55[arg8 + local86][local92 + arg8 - 1] & 0x78240000) == 0) {
									local289 = 1;
									while (true) {
										if (local289 >= arg8 - 1) {
											TextureOpPerlinNoise.anIntArray182[local50] = local7 + 1;
											PingRequest.recentVarcUpdates[local50] = local9;
											CubeMapSpecularEffect.anIntArrayArray30[local74 + 1][local69] = 8;
											local50 = local50 + 1 & 0xFFF;
											Static14.anIntArrayArray1[local74 + 1][local69] = local247;
											break;
										}
										if ((local55[local86 + arg8][local289 + local92] & 0x78E40000) != 0) {
											break;
										}
										local289++;
									}
								}
								if (local69 > 0 && CubeMapSpecularEffect.anIntArrayArray30[local74][local69 - 1] == 0 && (local55[local86][local92 - 1] & 0x43A40000) == 0 && (local55[arg8 + local86 - 1][local92 - 1] & 0x60E40000) == 0) {
									local289 = 1;
									while (true) {
										if (arg8 - 1 <= local289) {
											TextureOpPerlinNoise.anIntArray182[local50] = local7;
											PingRequest.recentVarcUpdates[local50] = local9 - 1;
											CubeMapSpecularEffect.anIntArrayArray30[local74][local69 - 1] = 1;
											local50 = local50 + 1 & 0xFFF;
											Static14.anIntArrayArray1[local74][local69 - 1] = local247;
											break;
										}
										if ((local55[local289 + local86][local92 - 1] & 0x63E40000) != 0) {
											break;
										}
										local289++;
									}
								}
								if (local69 < 128 - arg8 && CubeMapSpecularEffect.anIntArrayArray30[local74][local69 + 1] == 0 && (local55[local86][local92 + arg8] & 0x4E240000) == 0 && (local55[local86 + arg8 - 1][local92 + arg8] & 0x78240000) == 0) {
									local289 = 1;
									while (true) {
										if (arg8 - 1 <= local289) {
											TextureOpPerlinNoise.anIntArray182[local50] = local7;
											PingRequest.recentVarcUpdates[local50] = local9 + 1;
											CubeMapSpecularEffect.anIntArrayArray30[local74][local69 + 1] = 4;
											local50 = local50 + 1 & 0xFFF;
											Static14.anIntArrayArray1[local74][local69 + 1] = local247;
											break;
										}
										if ((local55[local289 + local86][arg8 + local92] & 0x7E240000) != 0) {
											break;
										}
										local289++;
									}
								}
								if (local74 > 0 && local69 > 0 && CubeMapSpecularEffect.anIntArrayArray30[local74 - 1][local69 - 1] == 0 && (local55[local86 - 1][local92 - 1] & 0x43A40000) == 0) {
									local289 = 1;
									while (true) {
										if (arg8 <= local289) {
											TextureOpPerlinNoise.anIntArray182[local50] = local7 - 1;
											PingRequest.recentVarcUpdates[local50] = local9 - 1;
											CubeMapSpecularEffect.anIntArrayArray30[local74 - 1][local69 - 1] = 3;
											local50 = local50 + 1 & 0xFFF;
											Static14.anIntArrayArray1[local74 - 1][local69 - 1] = local247;
											break;
										}
										if ((local55[local86 - 1][local92 + local289 - 1] & 0x4FA40000) != 0 || (local55[local86 + local289 - 1][local92 - 1] & 0x63E40000) != 0) {
											break;
										}
										local289++;
									}
								}
								if (local74 < 128 - arg8 && local69 > 0 && CubeMapSpecularEffect.anIntArrayArray30[local74 + 1][local69 - 1] == 0 && (local55[local86 + arg8][local92 - 1] & 0x60E40000) == 0) {
									local289 = 1;
									while (true) {
										if (arg8 <= local289) {
											TextureOpPerlinNoise.anIntArray182[local50] = local7 + 1;
											PingRequest.recentVarcUpdates[local50] = local9 - 1;
											local50 = local50 + 1 & 0xFFF;
											CubeMapSpecularEffect.anIntArrayArray30[local74 + 1][local69 - 1] = 9;
											Static14.anIntArrayArray1[local74 + 1][local69 - 1] = local247;
											break;
										}
										if ((local55[local86 + arg8][local289 + local92 - 1] & 0x78E40000) != 0 || (local55[local86 + local289][local92 - 1] & 0x63E40000) != 0) {
											break;
										}
										local289++;
									}
								}
								if (local74 > 0 && 128 - arg8 > local69 && CubeMapSpecularEffect.anIntArrayArray30[local74 - 1][local69 + 1] == 0 && (local55[local86 - 1][local92 + arg8] & 0x4E240000) == 0) {
									for (local289 = 1; local289 < arg8; local289++) {
										if ((local55[local86 - 1][local92 + local289] & 0x4FA40000) != 0 || (local55[local86 + local289 - 1][arg8 + local92] & 0x7E240000) != 0) {
											continue label259;
										}
									}
									TextureOpPerlinNoise.anIntArray182[local50] = local7 - 1;
									PingRequest.recentVarcUpdates[local50] = local9 + 1;
									local50 = local50 + 1 & 0xFFF;
									CubeMapSpecularEffect.anIntArrayArray30[local74 - 1][local69 + 1] = 6;
									Static14.anIntArrayArray1[local74 - 1][local69 + 1] = local247;
								}
							} while (128 - arg8 <= local74);
						} while (128 - arg8 <= local69);
					} while (CubeMapSpecularEffect.anIntArrayArray30[local74 + 1][local69 + 1] != 0);
				} while ((local55[arg8 + local86][arg8 + local92] & 0x78240000) != 0);
				for (local289 = 1; local289 < arg8; local289++) {
					if ((local55[local289 + local86][arg8 + local92] & 0x7E240000) != 0 || (local55[arg8 + local86][local92 + local289] & 0x78E40000) != 0) {
						continue label282;
					}
				}
				TextureOpPerlinNoise.anIntArray182[local50] = local7 + 1;
				PingRequest.recentVarcUpdates[local50] = local9 + 1;
				CubeMapSpecularEffect.anIntArrayArray30[local74 + 1][local69 + 1] = 12;
				local50 = local50 + 1 & 0xFFF;
				Static14.anIntArrayArray1[local74 + 1][local69 + 1] = local247;
			}
		}
	}
@OriginalMember(owner = "client!lu", name = "c", descriptor = "(B)V")
	public static void method3501() {
		Class48_Sub1_Sub1.aClass68_26.method1791();
		Static232.aClass68_25.method1791();
		Static384.aClass68_47.method1791();
		Class38.aClass68_4.method1791();
		Class101.aClass68_16.method1791();
	}
@OriginalMember(owner = "client!lu", name = "a", descriptor = "(IB)V")
	public static void method3502(@OriginalArg(0) int arg0) {
		@Pc(8) Class4_Sub1_Sub11 local8 = NPCTypeList.method5222(arg0, 14);
		local8.method2608();
	}
}
