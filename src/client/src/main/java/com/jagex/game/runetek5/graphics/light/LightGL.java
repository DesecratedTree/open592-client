package com.jagex.game.runetek5.graphics.light;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.buffer.ArrayBufferGL;
import com.jagex.game.runetek5.buffer.ElementsBufferGL;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.Class4_Sub31;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlBitBuffer;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;

import com.jagex.game.runetek5.graphics.light.*;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.graphics.sprite.SpriteDrawQueue;
@OriginalClass("client!iu")
public final class LightGL extends Country {

	@OriginalMember(owner = "client!iu", name = "t", descriptor = "I")
	private int anInt3408;

	@OriginalMember(owner = "client!iu", name = "x", descriptor = "I")
	private int anInt3410;

	@OriginalMember(owner = "client!iu", name = "y", descriptor = "Lclient!fd;")
	private final GlToolkit toolkit;

	@OriginalMember(owner = "client!iu", name = "I", descriptor = "Lclient!oq;")
	private final GlGround ground;

	@OriginalMember(owner = "client!iu", name = "J", descriptor = "Lclient!vp;")
	private final PointLight pointLight;

	@OriginalMember(owner = "client!iu", name = "p", descriptor = "I")
	private final int minTileX;

	@OriginalMember(owner = "client!iu", name = "A", descriptor = "I")
	private final int totalIndexCount;

	@OriginalMember(owner = "client!iu", name = "H", descriptor = "I")
	private final int minTileZ;

	@OriginalMember(owner = "client!iu", name = "w", descriptor = "I")
	private final int maxTileZ;

	@OriginalMember(owner = "client!iu", name = "s", descriptor = "[[F")
	private float[][] aFloatArrayArray3;

	@OriginalMember(owner = "client!iu", name = "v", descriptor = "[[F")
	private float[][] aFloatArrayArray4;

	@OriginalMember(owner = "client!iu", name = "m", descriptor = "[[F")
	private float[][] aFloatArrayArray2;

	@OriginalMember(owner = "client!iu", name = "r", descriptor = "Lclient!iv;")
	private Packet aClass4_Sub12_3;

	@OriginalMember(owner = "client!iu", name = "C", descriptor = "Lclient!hk;")
	private GlBitBuffer aClass4_Sub12_Sub2_2;

	@OriginalMember(owner = "client!iu", name = "E", descriptor = "Lclient!ib;")
	private CubeMapSpecularEffect aClass102_17;

	@OriginalMember(owner = "client!iu", name = "u", descriptor = "Lclient!bk;")
	private final ArrayBufferGL anInterface1_3;

	@OriginalMember(owner = "client!iu", name = "l", descriptor = "Lclient!vd;")
	private final ElementsBufferGL anInterface11_5;

	@OriginalMember(owner = "client!iu", name = "F", descriptor = "Lclient!pc;")
	private final Class177 positionDrawBuffer;

	@OriginalMember(owner = "client!iu", name = "D", descriptor = "Lclient!pc;")
	private final Class177 colorDrawBuffer;

	static {
		new Class83(" days.", " Tage.", " jours.", " dias.");
	}

	@OriginalMember(owner = "client!iu", name = "<init>", descriptor = "(Lclient!fd;Lclient!oq;Lclient!vp;[I)V")
	public LightGL(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) GlGround glGround, @OriginalArg(2) PointLight pointLight, @OriginalArg(3) int[] tileMask) {
		this.toolkit = glToolkit;
		this.ground = glGround;
		this.pointLight = pointLight;
		@Pc(22) int local22 = this.pointLight.method3165() - (glGround.anInt5261 >> 1);
		this.minTileX = this.pointLight.method3160() - local22 >> glGround.anInt5256;
		this.totalIndexCount = this.pointLight.method3160() + local22 >> glGround.anInt5256;
		this.minTileZ = this.pointLight.method3164() - local22 >> glGround.anInt5256;
		this.maxTileZ = this.pointLight.method3164() + local22 >> glGround.anInt5256;
		@Pc(78) int local78 = this.totalIndexCount + 1 - this.minTileX;
		@Pc(87) int local87 = this.maxTileZ + 1 - this.minTileZ;
		this.aFloatArrayArray3 = new float[local78 + 1][local87 + 1];
		this.aFloatArrayArray4 = new float[local78 + 1][local87 + 1];
		this.aFloatArrayArray2 = new float[local78 + 1][local87 + 1];
		@Pc(123) int local123;
		@Pc(141) int local141;
		@Pc(148) int local148;
		@Pc(178) int local178;
		@Pc(193) int local193;
		for (@Pc(116) int local116 = 0; local116 <= local87; local116++) {
			local123 = this.minTileZ + local116;
			if (local123 > 0 && this.ground.anInt5258 - 1 > local123) {
				for (local141 = 0; local141 <= local78; local141++) {
					local148 = this.minTileX + local141;
					if (local148 > 0 && local148 < this.ground.anInt5262 - 1) {
						local178 = glGround.l(local148 + 1, local123) - glGround.l(local148 - 1, local123);
						local193 = glGround.l(local148, local123 + 1) - glGround.l(local148, local123 - 1);
						@Pc(208) float local208 = (float) (1.0D / Math.sqrt((double) (local178 * local178 + local193 * local193 + 65536)));
						this.aFloatArrayArray2[local141][local116] = local208 * (float) local178;
						this.aFloatArrayArray3[local141][local116] = -256.0F * local208;
						this.aFloatArrayArray4[local141][local116] = (float) local193 * local208;
					}
				}
			}
		}
		local123 = 0;
		local141 = 0;
		for (local148 = this.minTileZ; local148 <= this.maxTileZ; local148++) {
			if (local148 >= 0 && glGround.anInt5258 > local148) {
				for (local178 = this.minTileX; local178 <= this.totalIndexCount; local178++) {
					if (local178 >= 0 && local178 < glGround.anInt5262) {
						local193 = tileMask[local141];
						@Pc(293) int[] local293 = glGround.anIntArrayArrayArray6[local178][local148];
						if (local293 != null && local193 != 0) {
							if (local193 == 1) {
								local123 += local293.length;
							} else {
								local123 += 3;
							}
						}
					}
					local141++;
				}
			} else {
				local141 += this.totalIndexCount - this.minTileX;
			}
		}
		if (local123 > 0) {
			this.aClass4_Sub12_3 = new Packet(local123 * 2);
			this.aClass4_Sub12_Sub2_2 = new GlBitBuffer(local123 * 16);
			this.aClass102_17 = new CubeMapSpecularEffect(SpriteDrawQueue.method4949(local123));
			local178 = 0;
			local141 = 0;
			for (local193 = this.minTileZ; local193 <= this.maxTileZ; local193++) {
				if (local193 >= 0 && local193 < glGround.anInt5258) {
					@Pc(402) int local402 = 0;
					for (@Pc(405) int local405 = this.minTileX; local405 <= this.totalIndexCount; local405++) {
						if (local405 >= 0 && glGround.anInt5262 > local405) {
							@Pc(421) int local421 = tileMask[local141];
							@Pc(428) int[] local428 = glGround.anIntArrayArrayArray6[local405][local193];
							@Pc(435) int[] local435 = glGround.anIntArrayArrayArray7[local405][local193];
							if (local428 != null && local421 != 0) {
								if (local421 == 1) {
									for (@Pc(591) int local591 = 0; local591 < local428.length; local591++) {
										this.method2839(local428[local591], local435[local591], local178, local402, local193, local405);
									}
								} else if (local421 == 3) {
									this.method2839(0, 0, local178, local402, local193, local405);
									this.method2839(glGround.anInt5261, 0, local178, local402, local193, local405);
									this.method2839(0, glGround.anInt5261, local178, local402, local193, local405);
								} else if (local421 == 2) {
									this.method2839(glGround.anInt5261, 0, local178, local402, local193, local405);
									this.method2839(glGround.anInt5261, glGround.anInt5261, local178, local402, local193, local405);
									this.method2839(0, 0, local178, local402, local193, local405);
								} else if (local421 == 5) {
									this.method2839(glGround.anInt5261, glGround.anInt5261, local178, local402, local193, local405);
									this.method2839(0, glGround.anInt5261, local178, local402, local193, local405);
									this.method2839(glGround.anInt5261, 0, local178, local402, local193, local405);
								} else if (local421 == 4) {
									this.method2839(0, glGround.anInt5261, local178, local402, local193, local405);
									this.method2839(0, 0, local178, local402, local193, local405);
									this.method2839(glGround.anInt5261, glGround.anInt5261, local178, local402, local193, local405);
								}
							}
						}
						local141++;
						local402++;
					}
				} else {
					local141 += this.totalIndexCount - this.minTileX;
				}
				local178++;
			}
			this.anInterface1_3 = this.toolkit.method1955(this.aClass4_Sub12_3.currentPosition, false, this.aClass4_Sub12_3.buffer);
			this.anInterface11_5 = this.toolkit.method1960(this.aClass4_Sub12_Sub2_2.currentPosition, this.aClass4_Sub12_Sub2_2.buffer, false, 16);
			this.positionDrawBuffer = new Class177(this.anInterface11_5, 5126, 3, 0);
			this.colorDrawBuffer = new Class177(this.anInterface11_5, 5121, 4, 12);
		} else {
			this.colorDrawBuffer = null;
			this.anInterface11_5 = null;
			this.anInterface1_3 = null;
			this.positionDrawBuffer = null;
		}
		this.aClass102_17 = null;
		this.aFloatArrayArray2 = this.aFloatArrayArray3 = this.aFloatArrayArray4 = null;
		this.aClass4_Sub12_3 = null;
		this.aClass4_Sub12_Sub2_2 = null;
	}

	@OriginalMember(owner = "client!iu", name = "a", descriptor = "(IIIIIII)V")
	private void method2839(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5) {
		@Pc(7) long local7 = -1L;
		@Pc(21) int local21 = (arg5 << this.ground.anInt5256) + arg0;
		@Pc(29) int local29 = (arg4 << this.ground.anInt5256) + arg1;
		@Pc(35) int local35 = this.ground.a(local21, local29);
		if ((arg0 & 0x7F) == 0 || (arg1 & 0x7F) == 0) {
			local7 = (long) local21 & 0xFFFFL | (long) local29 << 16 & 0xFFFFL << 16;
			@Pc(66) Country local66 = this.aClass102_17.method2700(local7);
			if (local66 != null) {
				this.method2840(((Class4_Sub31) local66).aShort62);
				return;
			}
		}
		@Pc(84) short local84 = (short) this.anInt3410++;
		if (local7 != -1L) {
			this.aClass102_17.method2703(local7, new Class4_Sub31(local84));
		}
		@Pc(313) float local313;
		@Pc(287) float local287;
		@Pc(322) float local322;
		@Pc(141) float local141;
		@Pc(149) float local149;
		@Pc(156) float local156;
		@Pc(163) float local163;
		@Pc(170) float local170;
		@Pc(179) float local179;
		@Pc(188) float local188;
		@Pc(230) float local230;
		if (arg0 == 0 && arg1 == 0) {
			local313 = this.aFloatArrayArray2[arg3][arg2];
			local287 = this.aFloatArrayArray3[arg3][arg2];
			local322 = this.aFloatArrayArray4[arg3][arg2];
		} else if (arg0 == this.ground.anInt5261 && arg1 == 0) {
			local322 = this.aFloatArrayArray4[arg3 + 1][arg2];
			local287 = this.aFloatArrayArray3[arg3 + 1][arg2];
			local313 = this.aFloatArrayArray2[arg3 + 1][arg2];
		} else if (arg0 == this.ground.anInt5261 && this.ground.anInt5261 == arg1) {
			local322 = this.aFloatArrayArray4[arg3 + 1][arg2 + 1];
			local287 = this.aFloatArrayArray3[arg3 + 1][arg2 + 1];
			local313 = this.aFloatArrayArray2[arg3 + 1][arg2 + 1];
		} else if (arg0 == 0 && this.ground.anInt5261 == arg1) {
			local313 = this.aFloatArrayArray2[arg3][arg2 + 1];
			local287 = this.aFloatArrayArray3[arg3][arg2 + 1];
			local322 = this.aFloatArrayArray4[arg3][arg2 + 1];
		} else {
			local141 = (float) arg0 / (float) this.ground.anInt5261;
			local149 = (float) arg1 / (float) this.ground.anInt5261;
			local156 = this.aFloatArrayArray2[arg3][arg2];
			local163 = this.aFloatArrayArray3[arg3][arg2];
			local170 = this.aFloatArrayArray4[arg3][arg2];
			local179 = this.aFloatArrayArray2[arg3 + 1][arg2];
			local188 = this.aFloatArrayArray3[arg3 + 1][arg2];
			@Pc(206) float local206 = local188 + (this.aFloatArrayArray3[arg3 + 1][arg2 + 1] - local188) * local141;
			@Pc(221) float local221 = local170 + (this.aFloatArrayArray4[arg3][arg2 + 1] - local170) * local141;
			local230 = this.aFloatArrayArray4[arg3 + 1][arg2];
			@Pc(248) float local248 = local179 + (this.aFloatArrayArray2[arg3 + 1][arg2 + 1] - local179) * local141;
			@Pc(263) float local263 = local156 + (this.aFloatArrayArray2[arg3][arg2 + 1] - local156) * local141;
			@Pc(279) float local279 = local163 + (this.aFloatArrayArray3[arg3][arg2 + 1] - local163) * local141;
			local287 = local149 * (local206 - local279) + local279;
			@Pc(305) float local305 = local230 + (this.aFloatArrayArray4[arg3 + 1][arg2 + 1] - local230) * local141;
			local313 = local149 * (local248 - local263) + local263;
			local322 = local221 + local149 * (local305 - local221);
		}
		local141 = this.pointLight.method3160() - local21;
		local149 = this.pointLight.method3161() - local35;
		local156 = this.pointLight.method3164() - local29;
		local163 = (float) Math.sqrt((double) (local156 * local156 + local149 * local149 + local141 * local141));
		local170 = 1.0F / local163;
		local156 *= local170;
		local149 *= local170;
		local141 *= local170;
		local179 = local163 / (float) this.pointLight.method3165();
		local188 = 1.0F - local179 * local179;
		if (local188 < 0.0F) {
			local188 = 0.0F;
		}
		local230 = local313 * local141 + local287 * local149 + local156 * local322;
		if (local230 < 0.0F) {
			local230 = 0.0F;
		}
		@Pc(543) float local543 = local230 * local188 * 2.0F;
		if (local543 > 1.0F) {
			local543 = 1.0F;
		}
		@Pc(554) int local554 = this.pointLight.method3162();
		@Pc(564) int local564 = (int) (local543 * (float) (local554 >> 16 & 0xFF));
		if (local564 > 255) {
			local564 = 255;
		}
		@Pc(581) int local581 = (int) ((float) (local554 >> 8 & 0xFF) * local543);
		if (local581 > 255) {
			local581 = 255;
		}
		@Pc(594) int local594 = (int) (local543 * (float) (local554 & 0xFF));
		if (this.toolkit.bigEndianess) {
			this.aClass4_Sub12_Sub2_2.method2556((float) local21);
			this.aClass4_Sub12_Sub2_2.method2556((float) local35);
			this.aClass4_Sub12_Sub2_2.method2556((float) local29);
		} else {
			this.aClass4_Sub12_Sub2_2.method2552((float) local21);
			this.aClass4_Sub12_Sub2_2.method2552((float) local35);
			this.aClass4_Sub12_Sub2_2.method2552((float) local29);
		}
		if (local594 > 255) {
			local594 = 255;
		}
		this.aClass4_Sub12_Sub2_2.method2551(local564);
		this.aClass4_Sub12_Sub2_2.method2551(local581);
		this.aClass4_Sub12_Sub2_2.method2551(local594);
		this.aClass4_Sub12_Sub2_2.method2551(255);
		this.method2840(local84);
	}

	@OriginalMember(owner = "client!iu", name = "a", descriptor = "(BS)V")
	private void method2840(@OriginalArg(1) short arg0) {
		if (this.toolkit.bigEndianess) {
			this.aClass4_Sub12_3.method2524(arg0);
		} else {
			this.aClass4_Sub12_3.method2541(arg0);
		}
		this.anInt3408++;
	}

	@OriginalMember(owner = "client!iu", name = "a", descriptor = "(IIII[[Z)V")
	public void method2842(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) boolean[][] arg3) {
		if (this.anInterface1_3 == null || (arg0 + arg1 < this.minTileX || (arg1 - arg0 > this.totalIndexCount || (this.minTileZ > arg2 + arg0 || arg2 - arg0 > this.maxTileZ)))) {
			return;
		}
		for (@Pc(53) int local53 = this.minTileZ; local53 <= this.maxTileZ; local53++) {
			for (@Pc(58) int local58 = this.minTileX; local58 <= this.totalIndexCount; local58++) {
				@Pc(65) int local65 = local58 - arg1;
				@Pc(69) int local69 = local53 - arg2;
				if (local65 > -arg0 && arg0 > local65 && local69 > -arg0 && arg0 > local69 && arg3[arg0 + local65][local69 + arg0]) {
					this.toolkit.method1901((int) (this.pointLight.getIntensity() * 255.0F) << 24);
					this.toolkit.method1934(this.colorDrawBuffer, null, null, this.positionDrawBuffer);
					this.toolkit.method1967(0, this.anInt3408, this.anInterface1_3);
					return;
				}
			}
		}
	}

@OriginalMember(owner = "client!iu", name = "G", descriptor = "I")
	public static int npcCount = 0;
@OriginalMember(owner = "client!iu", name = "a", descriptor = "(ILclient!qm;)V")
	public static void method2841(@OriginalArg(1) OggOtherStream arg0) {
		if (!Class4_Sub6_Sub22.aBoolean411) {
			arg0.method5684();
			Skybox.anInt6710--;
		}
	}
}
