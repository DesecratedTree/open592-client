package com.jagex.game.runetek5.config.material.texture;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.deob.Static211;
import com.jagex.game.runetek5.deob.Static459;

import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.entity.EntityNodeList;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class204;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub8_Sub1;
import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.graphics.JavaBillboardFace;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.StackFrame;
import com.jagex.game.runetek5.graphics.model.billboard.BillboardType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.deob.Class95_Sub2;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.FixedFunctionSpecularPass;
import com.jagex.game.runetek5.deob.Class4_Sub3;
import com.jagex.game.runetek5.deob.Class48_Sub1_Sub1;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.deob.TextureOpBoxBlur;
import com.jagex.game.runetek5.entity.DynamicWall;
import com.jagex.game.runetek5.deob.Class243;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.config.sequence.SeqTypeList;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.cache.node.lru.ProjectileAnimationNode;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.deob.Class16_Sub5;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpConstant;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.deob.Class70_Sub1;
import com.jagex.game.runetek5.config.material.texture.TextureType;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageType;
import com.jagex.game.runetek5.deob.Class78_Sub1;
@OriginalClass("client!jf")
public final class TextureOpFlip extends TextureOperation {

	@OriginalMember(owner = "client!jf", name = "C", descriptor = "Z")
	private boolean aBoolean321 = true;

	@OriginalMember(owner = "client!jf", name = "D", descriptor = "Z")
	private boolean aBoolean322 = true;

	@OriginalMember(owner = "client!jf", name = "<init>", descriptor = "()V")
	public TextureOpFlip() {
		super(1, false);
	}

	@OriginalMember(owner = "client!jf", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(19) int[] local19 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			@Pc(37) int[] local37 = this.getChildMonochromeOutput(this.aBoolean321 ? Static211.anInt3929 - arg0 : arg0, 0);
			if (this.aBoolean322) {
				for (@Pc(42) int local42 = 0; local42 < MidiPcmStream.anInt1616; local42++) {
					local19[local42] = local37[Class78_Sub1.anInt3883 - local42];
				}
			} else {
				Static459.copy(local37, 0, local19, 0, MidiPcmStream.anInt1616);
			}
		}
		return local19;
	}

	@OriginalMember(owner = "client!jf", name = "a", descriptor = "(BI)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		@Pc(16) int[][] local16 = super.colorImageCache.get(arg0);
		if (super.colorImageCache.dirty) {
			@Pc(34) int[][] local34 = this.getChildColorOutput(0, this.aBoolean321 ? Static211.anInt3929 - arg0 : arg0);
			@Pc(38) int[] local38 = local34[0];
			@Pc(42) int[] local42 = local34[1];
			@Pc(46) int[] local46 = local34[2];
			@Pc(50) int[] local50 = local16[0];
			@Pc(54) int[] local54 = local16[1];
			@Pc(58) int[] local58 = local16[2];
			@Pc(63) int local63;
			if (this.aBoolean322) {
				for (local63 = 0; local63 < MidiPcmStream.anInt1616; local63++) {
					local50[local63] = local38[Class78_Sub1.anInt3883 - local63];
					local54[local63] = local42[Class78_Sub1.anInt3883 - local63];
					local58[local63] = local46[Class78_Sub1.anInt3883 - local63];
				}
			} else {
				for (local63 = 0; local63 < MidiPcmStream.anInt1616; local63++) {
					local50[local63] = local38[local63];
					local54[local63] = local42[local63];
					local58[local63] = local46[local63];
				}
			}
		}
		return local16;
	}

	@OriginalMember(owner = "client!jf", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.aBoolean322 = arg1.method2490() == 1;
		} else if (arg0 == 1) {
			this.aBoolean321 = arg1.method2490() == 1;
		} else if (arg0 == 2) {
			super.monochrome = arg1.method2490() == 1;
		}
	}

@OriginalMember(owner = "client!jf", name = "I", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_157 = new Class22(90, 12);
@OriginalMember(owner = "client!jf", name = "J", descriptor = "I")
	public static int anInt3532 = 0;
@OriginalMember(owner = "client!jf", name = "K", descriptor = "[F")
	public static final float[] aFloatArray22 = new float[4];
@OriginalMember(owner = "client!jf", name = "a", descriptor = "(ILclient!ya;II)V")
	public static void method2920(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		if (arg1 < 0 || arg2 < 0 || GpuFaceNormal.anInt6948 == 0 || CacheableNodeByteArray.anInt4508 == 0) {
			return;
		}
		arg0.H(Game.anInt3569, Class9.anInt6919, GpuFaceNormal.anInt6948, CacheableNodeByteArray.anInt4508);
		arg0.OA(Class95_Sub2.anInt6409, Class4_Sub1_Sub8_Sub1.anInt4896, GpuFaceNormal.anInt6948, CacheableNodeByteArray.anInt4508);
		@Pc(43) Ground local43 = arg0.method4274();
		local43.s(Ground.anInt7321, JavaBillboardFace.anInt3926, ProjectileAnimationNode.anInt1909, BillboardType.anInt3534, Class21.anInt460, Class48_Sub1_Sub1.anInt4881);
		arg0.ea(local43);
		@Pc(72) int local72;
		@Pc(81) int local81;
		@Pc(84) int local84;
		@Pc(93) int local93;
		@Pc(101) int local101;
		@Pc(116) int[] local116;
		@Pc(147) int local147;
		@Pc(156) int local156;
		if (Class4_Sub6_Sub4.aClass65Array1 != null) {
			@Pc(58) int local58 = -1;
			@Pc(60) int local60 = -1;
			@Pc(63) int local63 = arg0.a();
			local72 = local63 * (arg1 - Game.anInt3569) / GpuFaceNormal.anInt6948;
			local81 = local63 * (arg2 - Class9.anInt6919) / CacheableNodeByteArray.anInt4508;
			local84 = arg0.YA();
			local93 = local84 * (arg1 - Game.anInt3569) / GpuFaceNormal.anInt6948;
			local101 = local84 * (arg2 - Class9.anInt6919) / CacheableNodeByteArray.anInt4508;
			local116 = new int[] { local72, local81, local63 };
			local43.addLight(local116);
			@Pc(134) int[] local134 = new int[] { local93, local101, local84 };
			local43.addLight(local134);
			@Pc(139) float local139 = 0.0F;
			local147 = local134[0] - local116[0];
			local156 = local134[1] - local116[1];
			@Pc(165) int local165 = local134[2] - local116[2];
			while (local139 < 1.0F) {
				@Pc(178) int local178 = (int) ((float) local116[0] + local139 * (float) local147);
				@Pc(182) int local182 = local178 >> 7;
				@Pc(193) int local193 = (int) (local139 * (float) local165 + (float) local116[2]);
				@Pc(197) int local197 = local193 >> 7;
				if (local182 > 0 && local197 > 0 && GLBillowTextureData.anInt5666 > local182 && Static283.anInt5187 > local197) {
					@Pc(221) int local221 = Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82;
					if (local221 < 3 && (SeqTypeList.aByteArrayArrayArray2[1][local182][local197] & 0x2) != 0) {
						local221++;
					}
					if ((float) Class4_Sub6_Sub4.aClass65Array1[local221].a(local178, local193) < local139 * (float) local156 + (float) local116[1]) {
						local58 = (Static1.aClass16_Sub1_Sub5_Sub1_1.getSize() - 1 << 6) + local178 >> 7;
						local60 = (Static1.aClass16_Sub1_Sub5_Sub1_1.getSize() - 1 << 6) + local193 >> 7;
						break;
					}
				}
				local139 = (float) ((double) local139 + 0.01D);
			}
			if (local58 != -1 && local60 != -1) {
				if (AbstractArbBufferGL.aBoolean599 && (Country.anInt7378 & 0x40) != 0) {
					@Pc(348) com.jagex.game.runetek5.config.rsinterface.Component local348 = EnvironmentSampler.method3230(Class243.anInt6726, StackFrame.anInt350);
					if (local348 == null) {
						Class16_Sub5.method5139();
					} else {
						Static380.method5017(19, false, 0L, local58, " ->", true, Class230.aString62, local60, -1, Class164.anInt5120);
					}
				} else {
					if (DynamicWall.aClass121_3 == FixedFunctionSpecularPass.WRITE_PING) {
						Static380.method5017(59, false, 0L, local58, "", true, TextureType.aClass83_83.method2267(NativeBillboardFace.anInt3423), local60, -1, -1);
					}
					Static380.method5017(44, false, 0L, local58, "", true, Static167.aString35, local60, -1, TextureOpConstant.anInt4097);
				}
			}
		}
		@Pc(368) EntityNodeList local368 = Class4_Sub3.aClass84_2;
		for (@Pc(373) Class3_Sub3 local373 = (Class3_Sub3) local368.method2312(); local373 != null; local373 = (Class3_Sub3) local368.method2309()) {
			if (local373.anInt2381 == Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82 && local373.method2024(arg1, arg2, arg0)) {
				@Pc(503) int local503;
				if (local373.aClass16_1 instanceof PlayerEntity) {
					@Pc(399) PlayerEntity local399 = (PlayerEntity) local373.aClass16_1;
					local72 = local399.getSize();
					if ((local72 & 0x1) == 0 && (local399.anInt6893 & 0x7F) == 0 && (local399.anInt6892 & 0x7F) == 0 || (local72 & 0x1) == 1 && (local399.anInt6893 & 0x7F) == 64 && (local399.anInt6892 & 0x7F) == 64) {
						local81 = local399.anInt6893 - (local399.getSize() - 1 << 6);
						local84 = local399.anInt6892 - (local399.getSize() - 1 << 6);
						@Pc(515) int local515;
						for (local93 = 0; local93 < Class10.anInt163; local93++) {
							@Pc(480) NPCEntity local480 = Class93.aClass16_Sub1_Sub5_Sub2Array1[TextureOpBoxBlur.anIntArray412[local93]];
							if (local480 != null && Shadow.anInt5323 != local480.anInt6048 && local480.isHidden) {
								local503 = local480.anInt6893 - (local480.npcType.anInt7201 - 1 << 6);
								local515 = local480.anInt6892 - (local480.npcType.anInt7201 - 1 << 6);
								if (local503 >= local81 && local480.npcType.anInt7201 <= local399.getSize() - (local503 - local81 >> 7) && local515 >= local84 && local480.npcType.anInt7201 <= local399.getSize() - (local515 - local84 >> 7)) {
									MusicPatch.method4396(local373.anInt2381 != Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82, local480);
									local480.anInt6048 = Shadow.anInt5323;
								}
							}
						}
						local101 = Class4_Sub7.anInt606;
						local116 = Static99.anIntArray157;
						for (local515 = 0; local515 < local101; local515++) {
							@Pc(587) PlayerEntity local587 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local116[local515]];
							if (local587 != null && Shadow.anInt5323 != local587.anInt6048 && local587 != local399 && local587.isHidden) {
								local147 = local587.anInt6893 - (local587.getSize() - 1 << 6);
								local156 = local587.anInt6892 - (local587.getSize() - 1 << 6);
								if (local147 >= local81 && local587.getSize() <= local399.getSize() - (local147 - local81 >> 7) && local84 <= local156 && local587.getSize() <= local399.getSize() - (local156 - local84 >> 7)) {
									Class65.method4026(local373.anInt2381 != Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82, local587);
									local587.anInt6048 = Shadow.anInt5323;
								}
							}
						}
					}
					if (Shadow.anInt5323 == local399.anInt6048) {
						continue;
					}
					Class65.method4026(local373.anInt2381 != Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82, local399);
					local399.anInt6048 = Shadow.anInt5323;
				}
				if (local373.aClass16_1 instanceof NPCEntity) {
					@Pc(721) NPCEntity local721 = (NPCEntity) local373.aClass16_1;
					if (local721.npcType != null) {
						if ((local721.npcType.anInt7201 & 0x1) == 0 && (local721.anInt6893 & 0x7F) == 0 && (local721.anInt6892 & 0x7F) == 0 || (local721.npcType.anInt7201 & 0x1) == 1 && (local721.anInt6893 & 0x7F) == 64 && (local721.anInt6892 & 0x7F) == 64) {
							local72 = local721.anInt6893 - (local721.npcType.anInt7201 - 1 << 6);
							local81 = local721.anInt6892 - (local721.npcType.anInt7201 - 1 << 6);
							for (local84 = 0; local84 < Class10.anInt163; local84++) {
								@Pc(798) NPCEntity local798 = Class93.aClass16_Sub1_Sub5_Sub2Array1[TextureOpBoxBlur.anIntArray412[local84]];
								if (local798 != null && Shadow.anInt5323 != local798.anInt6048 && local798 != local721 && local798.isHidden) {
									local101 = local798.anInt6893 - (local798.npcType.anInt7201 - 1 << 6);
									local503 = local798.anInt6892 - (local798.npcType.anInt7201 - 1 << 6);
									if (local101 >= local72 && local798.npcType.anInt7201 <= local721.npcType.anInt7201 - (local101 - local72 >> 7) && local81 <= local503 && local798.npcType.anInt7201 <= local721.npcType.anInt7201 - (local503 - local81 >> 7)) {
										MusicPatch.method4396(Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82 != local373.anInt2381, local798);
										local798.anInt6048 = Shadow.anInt5323;
									}
								}
							}
							local93 = Class4_Sub7.anInt606;
							@Pc(914) int[] local914 = Static99.anIntArray157;
							for (local503 = 0; local503 < local93; local503++) {
								@Pc(924) PlayerEntity local924 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local914[local503]];
								if (local924 != null && local924.anInt6048 != Shadow.anInt5323 && local924.isHidden) {
									@Pc(946) int local946 = local924.anInt6893 - (local924.getSize() - 1 << 6);
									local147 = local924.anInt6892 - (local924.getSize() - 1 << 6);
									if (local946 >= local72 && local924.getSize() <= local721.npcType.anInt7201 - (local946 - local72 >> 7) && local81 <= local147 && local924.getSize() <= local721.npcType.anInt7201 - (local147 - local81 >> 7)) {
										Class65.method4026(local373.anInt2381 != Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82, local924);
										local924.anInt6048 = Shadow.anInt5323;
									}
								}
							}
						}
						if (local721.anInt6048 == Shadow.anInt5323) {
							continue;
						}
						MusicPatch.method4396(local373.anInt2381 != Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82, local721);
						local721.anInt6048 = Shadow.anInt5323;
					}
				}
				if (local373.aClass16_1 instanceof ObjStackEntity) {
					@Pc(1063) Class4_Sub18 local1063 = (Class4_Sub18) LocTypeList.aClass102_40.method2700((long) (local373.anInt2384 | local373.anInt2381 << 28 | local373.anInt2383 << 14));
					if (local1063 != null) {
						for (@Pc(1071) Class4_Sub45 local1071 = (Class4_Sub45) local1063.aClass183_14.method4139(); local1071 != null; local1071 = (Class4_Sub45) local1063.aClass183_14.method4145()) {
							@Pc(1081) ItemType local1081 = Terrain.aClass206_3.method4703(local1071.anInt7355);
							if (!AbstractArbBufferGL.aBoolean599) {
								if (Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82 == local373.anInt2381) {
									@Pc(1153) String[] local1153 = local1081.aStringArray34;
									for (local93 = 4; local93 >= 0; local93--) {
										if (local1153 != null && local1153[local93] != null) {
											@Pc(1165) byte local1165 = 0;
											local503 = Class6.anInt5453;
											if (local93 == 0) {
												local1165 = 49;
											}
											if (local93 == 1) {
												local1165 = 51;
											}
											if (local93 == 2) {
												local1165 = 16;
											}
											if (local93 == 3) {
												local1165 = 4;
											}
											if (local1081.anInt6124 == local93) {
												local503 = local1081.anInt6155;
											}
											if (local93 == 4) {
												local1165 = 2;
											}
											if (local1081.anInt6153 == local93) {
												local503 = local1081.anInt6127;
											}
											Static380.method5017(local1165, false, (long) local1071.anInt7355, local373.anInt2384, "<col=ff9040>" + local1081.name, true, local1153[local93], local373.anInt2383, -1, local503);
										}
									}
								}
								Static380.method5017(1009, Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82 != local373.anInt2381, (long) local1071.anInt7355, local373.anInt2384, "<col=ff9040>" + local1081.name, true, QuickchatMessageType.aClass83_12.method2267(NativeBillboardFace.anInt3423), local373.anInt2383, -1, Class70_Sub1.anInt2066);
							} else if (Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82 == local373.anInt2381) {
								@Pc(1098) Class133 local1098 = OpenGl2DTexture.anInt5460 == -1 ? null : Class4_Sub1_Sub11.aClass180_1.method4121(OpenGl2DTexture.anInt5460);
								if ((Country.anInt7378 & 0x1) != 0 && (local1098 == null || local1081.method4781(OpenGl2DTexture.anInt5460, local1098.anInt4123) != local1098.anInt4123)) {
									Static380.method5017(6, false, (long) local1071.anInt7355, local373.anInt2384, Class4_Sub10.aString28 + " -> <col=ff9040>" + local1081.name, true, Class230.aString62, local373.anInt2383, -1, Class164.anInt5120);
								}
							}
						}
					}
				}
				if (local373.aClass16_1 instanceof GameObject) {
					@Pc(1297) GameObject local1297 = (GameObject) local373.aClass16_1;
					@Pc(1304) LocType local1304 = Static267.aClass262_2.method5560(local1297.method5350());
					if (local1304.alternativeIds != null) {
						local1304 = local1304.method4584(Class4_Sub6_Sub24.aClass114_1);
					}
					if (local1304 != null) {
						if (!AbstractArbBufferGL.aBoolean599) {
							if (local373.anInt2381 == Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82) {
								@Pc(1328) String[] local1328 = local1304.actions;
								if (local1328 != null) {
									for (local84 = 4; local84 >= 0; local84--) {
										if (local1328[local84] != null) {
											@Pc(1340) short local1340 = 0;
											local101 = Class6.anInt5453;
											if (local84 == 0) {
												local1340 = 18;
											}
											if (local84 == 1) {
												local1340 = 45;
											}
											if (local84 == 2) {
												local1340 = 23;
											}
											if (local84 == 3) {
												local1340 = 25;
											}
											if (local84 == 4) {
												local1340 = 1007;
											}
											if (local1304.anInt5820 == local84) {
												local101 = local1304.anInt5824;
											}
											if (local84 == local1304.anInt5837) {
												local101 = local1304.anInt5842;
											}
											Static380.method5017(local1340, false, Class204.method4675(local373.anInt2384, local1297, local373.anInt2383), local373.anInt2384, "<col=00ffff>" + local1304.name, true, local1328[local84], local373.anInt2383, -1, local101);
										}
									}
								}
							}
							Static380.method5017(1010, local373.anInt2381 != Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82, (long) local1304.id, local373.anInt2384, "<col=00ffff>" + local1304.name, true, QuickchatMessageType.aClass83_12.method2267(NativeBillboardFace.anInt3423), local373.anInt2383, -1, Class70_Sub1.anInt2066);
						} else if (Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82 == local373.anInt2381) {
							@Pc(1485) Class133 local1485 = OpenGl2DTexture.anInt5460 == -1 ? null : Class4_Sub1_Sub11.aClass180_1.method4121(OpenGl2DTexture.anInt5460);
							if ((Country.anInt7378 & 0x4) != 0 && (local1485 == null || local1304.method4585(OpenGl2DTexture.anInt5460, local1485.anInt4123) != local1485.anInt4123)) {
								Static380.method5017(30, false, Class204.method4675(local373.anInt2384, local1297, local373.anInt2383), local373.anInt2384, Class4_Sub10.aString28 + " -> <col=00ffff>" + local1304.name, true, Class230.aString62, local373.anInt2383, -1, Class164.anInt5120);
							}
						}
					}
				}
			}
		}
	}
}
