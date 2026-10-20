package com.jagex.game.runetek5.graphics.model.particle;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.config.particle.ParticleType;
import com.jagex.game.runetek5.scene.ground.Ground;

import java.awt.Rectangle;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.deob.Class120;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class16_Sub1;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.deob.Class16_Sub3;
import com.jagex.game.runetek5.deob.Class16_Sub4;
import com.jagex.game.runetek5.deob.Class16_Sub5;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.deob.Class4_Sub14;
import com.jagex.game.runetek5.deob.Class4_Sub3;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub23;
import com.jagex.game.runetek5.deob.Class7;
import com.jagex.game.runetek5.deob.Static105;
import com.jagex.game.runetek5.deob.Static148;
import com.jagex.game.runetek5.deob.Static209;
import com.jagex.game.runetek5.deob.Static231;
import com.jagex.game.runetek5.deob.Static433;
import com.jagex.game.runetek5.deob.Static458;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.graphics.model.particle.*;
import com.jagex.game.runetek5.deob.RemoteFileWorker;
import com.jagex.game.runetek5.cache.node.GlMemoryPool;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub8_Sub1;
import com.jagex.game.runetek5.deob.Class70_Sub2;
import com.jagex.game.runetek5.QuickChatPhrase;
import com.jagex.game.runetek5.deob.Class9_Sub3;
import com.jagex.game.runetek5.deob.Class95_Sub3;
import com.jagex.game.runetek5.js5.request.FileWorker;
import com.jagex.game.runetek5.deob.Class208;
import com.jagex.game.runetek5.deob.Class229;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchNode;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.deob.MouseManager;
import com.jagex.game.runetek5.deob.Class180;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.deob.Class4_Sub18;
import com.jagex.game.runetek5.config.param.ParamTypeList;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.deob.TextureOpBoxBlur;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub1;
import com.jagex.game.runetek5.deob.Class149;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.Class4_Sub36;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub26;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.graphics.texture.OpenGl2DImageTexture;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.deob.Class16_Sub5_Sub1;
import com.jagex.game.runetek5.OpenGlArbElementArrayBuffer;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.config.rsinterface.HookRequest;
import com.jagex.game.runetek5.deob.QuickChatValueType;
import com.jagex.game.runetek5.deob.ReflectingTextureEffect;
import com.jagex.game.runetek5.entity.PathingEntity;
@OriginalClass("client!pf")
public final class EffectiveVertex {

	@OriginalMember(owner = "client!pf", name = "c", descriptor = "Lclient!c;")
	public Ground aClass31_4;

	@OriginalMember(owner = "client!pf", name = "d", descriptor = "I")
	public int anInt5340;

	@OriginalMember(owner = "client!pf", name = "f", descriptor = "I")
	public int anInt5342;

	@OriginalMember(owner = "client!pf", name = "h", descriptor = "Lclient!pf;")
	public EffectiveVertex aClass179_2;

	@OriginalMember(owner = "client!pf", name = "m", descriptor = "I")
	public int anInt5347;

	@OriginalMember(owner = "client!pf", name = "a", descriptor = "I")
	public final int appliesOnVertex;

	@OriginalMember(owner = "client!pf", name = "b", descriptor = "I")
	public final int effectId;

	@OriginalMember(owner = "client!pf", name = "<init>", descriptor = "(II)V")
	public EffectiveVertex(@OriginalArg(0) int effectId, @OriginalArg(1) int appliesOnVertex) {
		this.appliesOnVertex = appliesOnVertex;
		this.effectId = effectId;
	}

	@OriginalMember(owner = "client!pf", name = "<init>", descriptor = "(Lclient!pf;I)V")
	public EffectiveVertex(@OriginalArg(0) EffectiveVertex arg0, @OriginalArg(1) int arg1) {
		this.aClass179_2 = arg0;
		this.effectId = this.aClass179_2.effectId;
		this.aClass31_4 = this.aClass179_2.aClass31_4;
		this.appliesOnVertex = this.aClass179_2.appliesOnVertex + arg1;
	}

	@OriginalMember(owner = "client!pf", name = "a", descriptor = "(IZ)Lclient!pf;")
	public EffectiveVertex method4104(@OriginalArg(0) int arg0) {
		return new EffectiveVertex(this.effectId, arg0);
	}

	@OriginalMember(owner = "client!pf", name = "b", descriptor = "(B)Lclient!ah;")
	public ParticleType method4106() {
		return Class16_Sub4_Sub1.method1587(this.effectId);
	}

@OriginalMember(owner = "client!pf", name = "l", descriptor = "I")
	public static int anInt5346;
@OriginalMember(owner = "client!pf", name = "i", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_247 = new Class22(22, 9);
@OriginalMember(owner = "client!pf", name = "n", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_248 = new Class22(10, 12);
@OriginalMember(owner = "client!pf", name = "a", descriptor = "(Lclient!jj;Lclient!nt;)V")
	public static void method4101(@OriginalArg(0) Class120 arg0, @OriginalArg(1) Class164 arg1) {
		if (!arg1.aBoolean466) {
			return;
		}
		@Pc(6) short local6 = arg1.aShort64;
		@Pc(9) short local9 = arg1.aShort63;
		@Pc(12) byte local12 = arg1.aByte54;
		@Pc(15) byte local15 = arg1.aByte53;
		@Pc(21) int local21 = (local6 << Static231.anInt4434) + ReflectingTextureEffect.anInt5747;
		@Pc(27) int local27 = (local9 << Static231.anInt4434) + ReflectingTextureEffect.anInt5747;
		@Pc(31) Class164[][] local31 = Class70.aClass164ArrayArrayArray5[local12];
		@Pc(53) float local53;
		if (FileWorker.aClass65Array3 == Class16_Sub1_Sub6.aClass65Array4) {
			Class95_Sub3.aClass19_37.GA(Class4_Sub6_Sub4.aClass65Array1[0].a(local21, local27), PathingEntity.method4753(local6, local9), Class149.method3563(local6, local9), Class16_Sub5_Sub1.method3012(local6, local9));
			local53 = 251.5F;
		} else {
			local53 = 201.5F - (float) (local15 + 1) * 50.0F;
		}
		Class95_Sub3.aClass19_37.g(3000.0F, local53 * 1.5F);
		@Pc(86) Class164 local86;
		@Pc(616) Class3_Sub3 local616;
		@Pc(711) int local711;
		@Pc(714) int local714;
		@Pc(723) int local723;
		@Pc(735) int local735;
		@Pc(406) int local406;
		@Pc(330) boolean local330;
		@Pc(377) Class3_Sub3 local377;
		@Pc(408) int local408;
		@Pc(411) Class16_Sub5 local411;
		@Pc(706) int local706;
		if (arg1.aBoolean464) {
			if (QuickChatValueType.aBoolean269) {
				if (local12 > 0) {
					local86 = Class70.aClass164ArrayArrayArray5[local12 - 1][local6][local9];
					if (local86 != null && local86.aBoolean466) {
						return;
					}
				}
				if (local6 <= QuickChatPhrase.anInt7073 && local6 > Static105.anInt2325) {
					local86 = local31[local6 - 1][local9];
					if (local86 != null && local86.aBoolean466 && (local86.aBoolean464 || (arg1.aByte50 & 0x1) == 0)) {
						return;
					}
				}
				if (local6 >= QuickChatPhrase.anInt7073 && local6 < Class30.anInt775 - 1) {
					local86 = local31[local6 + 1][local9];
					if (local86 != null && local86.aBoolean466 && (local86.aBoolean464 || (arg1.aByte50 & 0x4) == 0)) {
						return;
					}
				}
				if (local9 <= OpenGlArbElementArrayBuffer.anInt1887 && local9 > Class149.anInt4530) {
					local86 = local31[local6][local9 - 1];
					if (local86 != null && local86.aBoolean466 && (local86.aBoolean464 || (arg1.aByte50 & 0x8) == 0)) {
						return;
					}
				}
				if (local9 >= OpenGlArbElementArrayBuffer.anInt1887 && local9 < Class4_Sub1_Sub11.anInt3087 - 1) {
					local86 = local31[local6][local9 + 1];
					if (local86 != null && local86.aBoolean466 && (local86.aBoolean464 || (arg1.aByte50 & 0x2) == 0)) {
						return;
					}
				}
			} else {
				QuickChatValueType.aBoolean269 = true;
			}
			arg1.aBoolean464 = false;
			if (arg1.aClass164_1 != null) {
				local86 = arg1.aClass164_1;
				Class95_Sub3.aClass19_37.g(3000.0F, (201.5F - (float) (local86.aByte53 + 1) * 50.0F) * 1.5F);
				if (!Class4_Sub18.method2030(local86.aByte53, local6, local9)) {
					Class16_Sub1_Sub6.aClass65Array4[local86.aByte53].method4028(local6, local9);
				}
				@Pc(249) Class16_Sub5 local249 = local86.aClass16_Sub5_3;
				if (local249 != null) {
					if (Class4_Sub6_Sub26.aBoolean470) {
						if ((local249.anInt6551 & arg1.aShort65) == 0) {
							MusicPatchNode.method3448(arg0, local12, local6, local9);
						} else {
							Class180.method4115(arg0, local249.anInt6551, local15, local6, local9);
						}
						Class95_Sub3.aClass19_37.copyLights(arg0.anInt5351, arg0.aClass4_Sub21Array3);
					}
					local249.method5339(Class95_Sub3.aClass19_37);
				}
				for (@Pc(286) Class7 local286 = local86.aClass7_3; local286 != null; local286 = local286.aClass7_1) {
					@Pc(290) Class16_Sub1 local290 = local286.aClass16_Sub1_1;
					if (local290 != null) {
						if (Class4_Sub6_Sub26.aBoolean470) {
							MusicPatchNode.method3448(arg0, local12, local6, local9);
							Class95_Sub3.aClass19_37.copyLights(arg0.anInt5351, arg0.aClass4_Sub21Array3);
						}
						local290.method5339(Class95_Sub3.aClass19_37);
					}
				}
				Class95_Sub3.aClass19_37.g(3000.0F, local53 * 1.5F);
			}
			local330 = !Class4_Sub18.method2030(local15, local6, local9);
			if (local330) {
				Class16_Sub1_Sub6.aClass65Array4[local15].method4028(local6, local9);
				@Pc(341) Class16_Sub4 local341 = arg1.aClass16_Sub4_2;
				if (local341 != null && local341.aBoolean336) {
					if (local341.aBoolean337) {
						Class95_Sub3.aClass19_37.g(3000.0F, (local53 + 50.0F - 1.5F) * 1.5F);
					}
					if (Class4_Sub6_Sub26.aBoolean470) {
						MusicPatchNode.method3448(arg0, local12, local6, local9);
						Class95_Sub3.aClass19_37.copyLights(arg0.anInt5351, arg0.aClass4_Sub21Array3);
					}
					local377 = local341.method5339(Class95_Sub3.aClass19_37);
					if (local377 != null) {
						local377.aClass16_1 = local341;
						local377.anInt2381 = local12;
						local377.anInt2384 = local6;
						local377.anInt2383 = local9;
						Class4_Sub3.aClass84_2.offer(local377);
					}
					if (local341.aBoolean337) {
						Class95_Sub3.aClass19_37.g(3000.0F, local53 * 1.5F);
					}
				}
			}
			local406 = 0;
			local408 = 0;
			local411 = arg1.aClass16_Sub5_3;
			@Pc(414) Class16_Sub2 local414 = arg1.aClass16_Sub2_2;
			if (local411 != null || local414 != null) {
				if (QuickChatPhrase.anInt7073 == local6) {
					local406++;
				} else if (QuickChatPhrase.anInt7073 < local6) {
					local406 += 2;
				}
				if (OpenGlArbElementArrayBuffer.anInt1887 == local9) {
					local406 += 3;
				} else if (OpenGlArbElementArrayBuffer.anInt1887 > local9) {
					local406 += 6;
				}
				local408 = HookRequest.anIntArray344[local406];
				arg1.aShort65 = Class4_Sub1_Sub8_Sub1.aShortArray161[local406];
			}
			if (local411 != null) {
				if ((local411.anInt6551 & MonoStereoData.anIntArray176[local406]) == 0) {
					arg1.aByte49 = 0;
				} else if (local411.anInt6551 == 16) {
					arg1.aByte49 = 3;
					arg1.aByte52 = Class208.aByteArray83[local406];
					arg1.aByte51 = (byte) (3 - arg1.aByte52);
				} else if (local411.anInt6551 == 32) {
					arg1.aByte49 = 6;
					arg1.aByte52 = StillWaterEffect.aByteArray99[local406];
					arg1.aByte51 = (byte) (6 - arg1.aByte52);
				} else if (local411.anInt6551 == 64) {
					arg1.aByte49 = 12;
					arg1.aByte52 = Class9_Sub3.aByteArray19[local406];
					arg1.aByte51 = (byte) (12 - arg1.aByte52);
				} else {
					arg1.aByte49 = 9;
					arg1.aByte52 = Class259.aByteArray103[local406];
					arg1.aByte51 = (byte) (9 - arg1.aByte52);
				}
				if ((local411.anInt6551 & local408) != 0 && !MouseManager.method2242(local15, local6, local9, local411.anInt6551)) {
					if (Class4_Sub6_Sub26.aBoolean470) {
						MusicPatchNode.method3448(arg0, local12, local6, local9);
						Class95_Sub3.aClass19_37.copyLights(arg0.anInt5351, arg0.aClass4_Sub21Array3);
					}
					@Pc(563) Class3_Sub3 local563 = local411.method5339(Class95_Sub3.aClass19_37);
					if (local563 != null) {
						local563.aClass16_1 = local411;
						local563.anInt2381 = local12;
						local563.anInt2384 = local6;
						local563.anInt2383 = local9;
						Class4_Sub3.aClass84_2.offer(local563);
					}
				}
				@Pc(584) Class16_Sub5 local584 = arg1.aClass16_Sub5_2;
				if (local584 != null && (local584.anInt6551 & local408) != 0 && !MouseManager.method2242(local15, local6, local9, local584.anInt6551)) {
					if (Class4_Sub6_Sub26.aBoolean470) {
						MusicPatchNode.method3448(arg0, local12, local6, local9);
						Class95_Sub3.aClass19_37.copyLights(arg0.anInt5351, arg0.aClass4_Sub21Array3);
					}
					local616 = local584.method5339(Class95_Sub3.aClass19_37);
					if (local616 != null) {
						local616.aClass16_1 = local584;
						local616.anInt2381 = local12;
						local616.anInt2384 = local6;
						local616.anInt2383 = local9;
						Class4_Sub3.aClass84_2.offer(local616);
					}
				}
			}
			if (local414 != null && !TextureOpBoxBlur.method4630(local15, local6, local9, local414.method3583())) {
				@Pc(647) Class16_Sub2 local647 = arg1.aClass16_Sub2_3;
				Class95_Sub3.aClass19_37.g(3000.0F, (local53 - 0.5F) * 1.5F);
				if ((local414.anInt4556 & local408) != 0) {
					if (Class4_Sub6_Sub26.aBoolean470) {
						MusicPatchNode.method3448(arg0, local12, local6, local9);
						Class95_Sub3.aClass19_37.copyLights(arg0.anInt5351, arg0.aClass4_Sub21Array3);
					}
					local616 = local414.method5339(Class95_Sub3.aClass19_37);
					if (local616 != null) {
						local616.aClass16_1 = local414;
						local616.anInt2381 = local12;
						local616.anInt2384 = local6;
						local616.anInt2383 = local9;
						Class4_Sub3.aClass84_2.offer(local616);
					}
				} else if (local414.anInt4556 == 256) {
					local706 = local414.anInt4566 - Class4_Sub1_Sub11.anInt3073;
					local711 = local414.anInt4552 - Static209.anInt3921;
					local714 = local414.anInt4562;
					if (local714 == 1 || local714 == 2) {
						local723 = -local706;
					} else {
						local723 = local706;
					}
					if (local714 == 2 || local714 == 3) {
						local735 = -local711;
					} else {
						local735 = local711;
					}
					if (Class4_Sub6_Sub26.aBoolean470) {
						MusicPatchNode.method3448(arg0, local12, local6, local9);
						Class95_Sub3.aClass19_37.copyLights(arg0.anInt5351, arg0.aClass4_Sub21Array3);
					}
					@Pc(759) Class3_Sub3 local759;
					if (local735 < local723) {
						local759 = local414.method5339(Class95_Sub3.aClass19_37);
						if (local759 != null) {
							local759.aClass16_1 = local414;
							local759.anInt2381 = local12;
							local759.anInt2384 = local6;
							local759.anInt2383 = local9;
							Class4_Sub3.aClass84_2.offer(local759);
						}
					} else if (local647 != null) {
						local759 = local647.method5339(Class95_Sub3.aClass19_37);
						if (local759 != null) {
							local759.aClass16_1 = local647;
							local759.anInt2381 = local12;
							local759.anInt2384 = local6;
							local759.anInt2383 = local9;
							Class4_Sub3.aClass84_2.offer(local759);
						}
					}
				}
				Class95_Sub3.aClass19_37.g(3000.0F, local53 * 1.5F);
			}
			if (local330) {
				@Pc(814) Class16_Sub4 local814 = arg1.aClass16_Sub4_2;
				if (local814 != null && !local814.aBoolean336) {
					if (local814.aBoolean337) {
						Class95_Sub3.aClass19_37.g(3000.0F, (local53 + 50.0F - 1.5F) * 1.5F);
					}
					if (Class4_Sub6_Sub26.aBoolean470) {
						MusicPatchNode.method3448(arg0, local12, local6, local9);
						Class95_Sub3.aClass19_37.copyLights(arg0.anInt5351, arg0.aClass4_Sub21Array3);
					}
					local616 = local814.method5339(Class95_Sub3.aClass19_37);
					if (local616 != null) {
						local616.aClass16_1 = local814;
						local616.anInt2381 = local12;
						local616.anInt2384 = local6;
						local616.anInt2383 = local9;
						Class4_Sub3.aClass84_2.offer(local616);
					}
					if (local814.aBoolean337) {
						Class95_Sub3.aClass19_37.g(3000.0F, local53 * 1.5F);
					}
				}
				@Pc(880) Class16_Sub3 local880 = arg1.aClass16_Sub3_1;
				if (local880 != null && !local880.aBoolean73) {
					if (Class4_Sub6_Sub26.aBoolean470) {
						MusicPatchNode.method3448(arg0, local12, local6, local9);
						Class95_Sub3.aClass19_37.copyLights(arg0.anInt5351, arg0.aClass4_Sub21Array3);
					}
					@Pc(903) Class3_Sub3 local903 = local880.method5339(Class95_Sub3.aClass19_37);
					if (local903 != null) {
						local903.aClass16_1 = local880;
						local903.anInt2381 = local12;
						local903.anInt2384 = local6;
						local903.anInt2383 = local9;
						Class4_Sub3.aClass84_2.offer(local903);
					}
				}
			}
			@Pc(924) byte local924 = arg1.aByte50;
			if (local924 != 0) {
				@Pc(941) Class164 local941;
				if (local6 < QuickChatPhrase.anInt7073 && (local924 & 0x4) != 0) {
					local941 = local31[local6 + 1][local9];
					if (local941 != null && local941.aBoolean466) {
						Static433.aClass70_1.method5080(local941);
					}
				}
				if (local9 < OpenGlArbElementArrayBuffer.anInt1887 && (local924 & 0x2) != 0) {
					local941 = local31[local6][local9 + 1];
					if (local941 != null && local941.aBoolean466) {
						Static433.aClass70_1.method5080(local941);
					}
				}
				if (local6 > QuickChatPhrase.anInt7073 && (local924 & 0x1) != 0) {
					local941 = local31[local6 - 1][local9];
					if (local941 != null && local941.aBoolean466) {
						Static433.aClass70_1.method5080(local941);
					}
				}
				if (local9 > OpenGlArbElementArrayBuffer.anInt1887 && (local924 & 0x8) != 0) {
					local941 = local31[local6][local9 - 1];
					if (local941 != null && local941.aBoolean466) {
						Static433.aClass70_1.method5080(local941);
					}
				}
			}
		}
		@Pc(1074) int local1074;
		@Pc(1057) Class16_Sub5 local1057;
		@Pc(1078) int local1078;
		@Pc(1030) Class7 local1030;
		if (arg1.aByte49 != 0) {
			local330 = true;
			for (local1030 = arg1.aClass7_3; local1030 != null; local1030 = local1030.aClass7_1) {
				if (local1030.aClass16_Sub1_1.anInt6890 != Class93.anInt2891 && (local1030.anInt118 & arg1.aByte49) == arg1.aByte52) {
					local330 = false;
					break;
				}
			}
			if (local330) {
				local1057 = arg1.aClass16_Sub5_3;
				if (!MouseManager.method2242(local15, local6, local9, local1057.anInt6551)) {
					if (Class4_Sub6_Sub26.aBoolean470) {
						label682: {
							if (local1057.anInt6551 >= 16) {
								local1074 = local6 - QuickChatPhrase.anInt7073;
								local1078 = local9 - OpenGlArbElementArrayBuffer.anInt1887;
								if (local1057.anInt6551 == 16) {
									local1074 -= ReflectingTextureEffect.anInt5747;
									local1078 += ReflectingTextureEffect.anInt5747;
									if (local1078 < local1074 && local6 > 0 && local9 <= GlModel.anInt3361) {
										MusicPatchNode.method3448(arg0, local12, local6 - 1, local9 + 1);
										break label682;
									}
								} else if (local1057.anInt6551 == 32) {
									local1074 += ReflectingTextureEffect.anInt5747;
									local1078 += ReflectingTextureEffect.anInt5747;
									if (local1078 < -local1074 && local6 < GpuFaceNormal.menuOpenWidth && local9 < GlModel.anInt3361) {
										MusicPatchNode.method3448(arg0, local12, local6 + 1, local9 + 1);
										break label682;
									}
								} else if (local1057.anInt6551 == 64) {
									local1074 += ReflectingTextureEffect.anInt5747;
									local1078 -= ReflectingTextureEffect.anInt5747;
									if (local1078 > local1074 && local6 < GpuFaceNormal.menuOpenWidth && local9 > 0) {
										MusicPatchNode.method3448(arg0, local12, local6 + 1, local9 - 1);
										break label682;
									}
								} else if (local1057.anInt6551 == 128) {
									local1074 -= ReflectingTextureEffect.anInt5747;
									local1078 -= ReflectingTextureEffect.anInt5747;
									if (local1078 > -local1074 && local6 > 0 && local9 > 0) {
										MusicPatchNode.method3448(arg0, local12, local6 - 1, local9 - 1);
										break label682;
									}
								}
							}
							MusicPatchNode.method3448(arg0, local12, local6, local9);
						}
						Class95_Sub3.aClass19_37.copyLights(arg0.anInt5351, arg0.aClass4_Sub21Array3);
					}
					@Pc(1216) Class3_Sub3 local1216 = local1057.method5339(Class95_Sub3.aClass19_37);
					if (local1216 != null) {
						local1216.aClass16_1 = local1057;
						local1216.anInt2381 = local12;
						local1216.anInt2384 = local6;
						local1216.anInt2383 = local9;
						Class4_Sub3.aClass84_2.offer(local1216);
					}
				}
				arg1.aByte49 = 0;
			}
		}
		@Pc(1333) int local1333;
		if (arg1.aBoolean465) {
			try {
				arg1.aBoolean465 = false;
				@Pc(1245) int local1245 = 0;
				label625: for (local1030 = arg1.aClass7_3; local1030 != null; local1030 = local1030.aClass7_1) {
					@Pc(1252) Class16_Sub1 local1252 = local1030.aClass16_Sub1_1;
					if (local1252.anInt6890 != Class93.anInt2891) {
						for (local1074 = local1252.aShort94; local1074 <= local1252.aShort97; local1074++) {
							for (local1078 = local1252.aShort96; local1078 <= local1252.aShort95; local1078++) {
								@Pc(1270) Class164 local1270 = local31[local1074][local1078];
								if (local1270.aBoolean464) {
									arg1.aBoolean465 = true;
									continue label625;
								}
								if (local1270.aByte49 != 0) {
									local706 = 0;
									if (local1074 > local1252.aShort94) {
										local706++;
									}
									if (local1074 < local1252.aShort97) {
										local706 += 4;
									}
									if (local1078 > local1252.aShort96) {
										local706 += 8;
									}
									if (local1078 < local1252.aShort95) {
										local706 += 2;
									}
									if ((local706 & local1270.aByte49) == arg1.aByte51) {
										arg1.aBoolean465 = true;
										continue label625;
									}
								}
							}
						}
						local1078 = QuickChatPhrase.anInt7073 - local1252.aShort94;
						local1333 = local1252.aShort97 - QuickChatPhrase.anInt7073;
						if (local1333 > local1078) {
							local1078 = local1333;
						}
						local706 = OpenGlArbElementArrayBuffer.anInt1887 - local1252.aShort96;
						local711 = local1252.aShort95 - OpenGlArbElementArrayBuffer.anInt1887;
						if (local711 > local706) {
							local706 = local711;
						}
						arg0.aClass16_Sub1Array2[local1245] = local1252;
						arg0.anIntArray357[local1245++] = local1078 + local706;
					}
				}
				while (local1245 > 0) {
					local408 = -50;
					local1074 = -1;
					@Pc(1384) Class16_Sub1 local1384;
					for (local1078 = 0; local1078 < local1245; local1078++) {
						local1384 = arg0.aClass16_Sub1Array2[local1078];
						if (local1384.anInt6890 != Class93.anInt2891) {
							local706 = arg0.anIntArray357[local1078];
							if (local706 > local408) {
								local408 = local706;
								local1074 = local1078;
							} else if (local706 == local408) {
								local711 = local1384.anInt6893 - Class4_Sub1_Sub11.anInt3073;
								local714 = local1384.anInt6892 - Static209.anInt3921;
								local723 = arg0.aClass16_Sub1Array2[local1074].anInt6893 - Class4_Sub1_Sub11.anInt3073;
								local735 = arg0.aClass16_Sub1Array2[local1074].anInt6892 - Static209.anInt3921;
								if (local711 * local711 + local714 * local714 > local723 * local723 + local735 * local735) {
									local1074 = local1078;
								}
							}
						}
					}
					if (local1074 == -1) {
						break;
					}
					local1384 = arg0.aClass16_Sub1Array2[local1074];
					local1384.anInt6890 = Class93.anInt2891;
					if (!GlMemoryPool.method102(local15, local1384.aShort94, local1384.aShort97, local1384.aShort96, local1384.aShort95, local1384.method5342())) {
						if (Class4_Sub6_Sub26.aBoolean470) {
							if (local1384.aByte83 == 0) {
								Class4_Sub36.method4070(arg0, local12, local1384.aShort94, local1384.aShort96, local1384.aShort97, local1384.aShort95);
							} else {
								MusicPatchNode.method3448(arg0, local12, local6, local9);
								local706 = local6 - QuickChatPhrase.anInt7073;
								local711 = local9 - OpenGlArbElementArrayBuffer.anInt1887;
								if (local1384.aByte83 == 2) {
									if (local711 > -local706) {
										Class208.method4720(arg0, local12, local6, local9 - 1, local6 - 1, local9);
									} else {
										Class208.method4720(arg0, local12, local6, local9 + 1, local6 + 1, local9);
									}
								} else if (local711 > local706) {
									Class208.method4720(arg0, local12, local6, local9 - 1, local6 + 1, local9);
								} else {
									Class208.method4720(arg0, local12, local6, local9 + 1, local6 - 1, local9);
								}
							}
							Class95_Sub3.aClass19_37.copyLights(arg0.anInt5351, arg0.aClass4_Sub21Array3);
						}
						local616 = local1384.method5339(Class95_Sub3.aClass19_37);
						if (local616 != null) {
							local616.aClass16_1 = local1384;
							local616.anInt2381 = local12;
							local616.anInt2384 = local1384.aShort94;
							local616.anInt2383 = local1384.aShort96;
							Class4_Sub3.aClass84_2.offer(local616);
						}
					}
					for (local706 = local1384.aShort94; local706 <= local1384.aShort97; local706++) {
						for (local711 = local1384.aShort96; local711 <= local1384.aShort95; local711++) {
							@Pc(1609) Class164 local1609 = local31[local706][local711];
							if (local1609.aByte49 != 0) {
								Static433.aClass70_1.method5080(local1609);
							} else if ((local706 != local6 || local711 != local9) && local1609.aBoolean466) {
								Static433.aClass70_1.method5080(local1609);
							}
						}
					}
				}
				if (arg1.aBoolean465) {
					return;
				}
			} catch (@Pc(1647) Exception local1647) {
				arg1.aBoolean465 = false;
			}
		}
		if (arg1.aClass146_2 != null && arg1.aByte48 == (byte) (GLBillowTextureData.anInt5665 & 0xFF)) {
			@Pc(1664) ParticleList local1664 = arg1.aClass146_2;
			local406 = Class16_Sub1_Sub6.aClass65Array4[local12].l(local6, local9);
			if (local12 < RemoteFileWorker.anInt4487 - 1) {
				local408 = Class16_Sub1_Sub6.aClass65Array4[local12].l(local6, local9) - Class16_Sub1_Sub6.aClass65Array4[local12 + 1].l(local6, local9);
			} else {
				local408 = 0x8 << Static231.anInt4434;
			}
			MECType.aClass31_3.method5660(local21, local406, local27, arg0.anIntArray358);
			local1074 = arg0.anIntArray358[2];
			MECType.aClass31_3.method5660(local21, local406 - local408, local27, arg0.anIntArray358);
			local1078 = arg0.anIntArray358[2];
			local1333 = local1074;
			local706 = local1078;
			if (local1074 > local1078) {
				local1333 = local1078;
				local706 = local1074;
			}
			local1333 -= OpenGlArbElementArrayBuffer.anInt1873;
			local706 += OpenGlArbElementArrayBuffer.anInt1873;
			local1664.anInt4520 = local1333;
			local1664.anInt4515 = local706;
			local1664.aBoolean417 = true;
			Class95_Sub3.aClass19_37.render(local1664);
		}
		if (!arg1.aBoolean466) {
			return;
		}
		if (arg1.aByte49 != 0) {
			return;
		}
		if (local6 <= QuickChatPhrase.anInt7073 && local6 > Static105.anInt2325) {
			local86 = local31[local6 - 1][local9];
			if (local86 != null && local86.aBoolean466) {
				return;
			}
		}
		if (local6 >= QuickChatPhrase.anInt7073 && local6 < Class30.anInt775 - 1) {
			local86 = local31[local6 + 1][local9];
			if (local86 != null && local86.aBoolean466) {
				return;
			}
		}
		if (local9 <= OpenGlArbElementArrayBuffer.anInt1887 && local9 > Class149.anInt4530) {
			local86 = local31[local6][local9 - 1];
			if (local86 != null && local86.aBoolean466) {
				return;
			}
		}
		if (local9 >= OpenGlArbElementArrayBuffer.anInt1887 && local9 < Class4_Sub1_Sub11.anInt3087 - 1) {
			local86 = local31[local6][local9 + 1];
			if (local86 != null && local86.aBoolean466) {
				return;
			}
		}
		arg1.aBoolean466 = false;
		OpenGl2DImageTexture.anInt5476--;
		@Pc(1855) Class16_Sub3 local1855 = arg1.aClass16_Sub3_1;
		if (local1855 != null && local1855.aBoolean73) {
			if (Class4_Sub6_Sub26.aBoolean470) {
				MusicPatchNode.method3448(arg0, local12, local6, local9);
				Class95_Sub3.aClass19_37.copyLights(arg0.anInt5351, arg0.aClass4_Sub21Array3);
			}
			@Pc(1878) Class3_Sub3 local1878 = local1855.method5339(Class95_Sub3.aClass19_37);
			if (local1878 != null) {
				local1878.aClass16_1 = local1855;
				local1878.anInt2381 = local12;
				local1878.anInt2384 = local6;
				local1878.anInt2383 = local9;
				Class4_Sub3.aClass84_2.offer(local1878);
			}
		}
		if (arg1.aShort65 != 0) {
			@Pc(1902) Class16_Sub2 local1902 = arg1.aClass16_Sub2_2;
			if (local1902 != null && !TextureOpBoxBlur.method4630(local15, local6, local9, local1902.method3583())) {
				if ((local1902.anInt4556 & arg1.aShort65) != 0) {
					if (Class4_Sub6_Sub26.aBoolean470) {
						MusicPatchNode.method3448(arg0, local12, local6, local9);
						Class95_Sub3.aClass19_37.copyLights(arg0.anInt5351, arg0.aClass4_Sub21Array3);
					}
					local377 = local1902.method5339(Class95_Sub3.aClass19_37);
					if (local377 != null) {
						local377.aClass16_1 = local1902;
						local377.anInt2381 = local12;
						local377.anInt2384 = local6;
						local377.anInt2383 = local9;
						Class4_Sub3.aClass84_2.offer(local377);
					}
				} else if (local1902.anInt4556 == 256) {
					local408 = local1902.anInt4566 - Class4_Sub1_Sub11.anInt3073;
					local1074 = local1902.anInt4552 - Static209.anInt3921;
					local1078 = local1902.anInt4562;
					if (local1078 == 1 || local1078 == 2) {
						local1333 = -local408;
					} else {
						local1333 = local408;
					}
					if (local1078 == 2 || local1078 == 3) {
						local706 = -local1074;
					} else {
						local706 = local1074;
					}
					if (Class4_Sub6_Sub26.aBoolean470) {
						MusicPatchNode.method3448(arg0, local12, local6, local9);
						Class95_Sub3.aClass19_37.copyLights(arg0.anInt5351, arg0.aClass4_Sub21Array3);
					}
					@Pc(2012) Class16_Sub2 local2012 = arg1.aClass16_Sub2_3;
					@Pc(2020) Class3_Sub3 local2020;
					if (local706 > local1333) {
						local2020 = local1902.method5339(Class95_Sub3.aClass19_37);
						if (local2020 != null) {
							local2020.aClass16_1 = local1902;
							local2020.anInt2381 = local12;
							local2020.anInt2384 = local6;
							local2020.anInt2383 = local9;
							Class4_Sub3.aClass84_2.offer(local2020);
						}
					} else if (local2012 != null) {
						local2020 = local2012.method5339(Class95_Sub3.aClass19_37);
						if (local2020 != null) {
							local2020.aClass16_1 = local2012;
							local2020.anInt2381 = local12;
							local2020.anInt2384 = local6;
							local2020.anInt2383 = local9;
							Class4_Sub3.aClass84_2.offer(local2020);
						}
					}
				}
			}
			local1057 = arg1.aClass16_Sub5_3;
			local411 = arg1.aClass16_Sub5_2;
			@Pc(2105) Class3_Sub3 local2105;
			if (local411 != null && (local411.anInt6551 & arg1.aShort65) != 0 && !MouseManager.method2242(local15, local6, local9, local411.anInt6551)) {
				if (Class4_Sub6_Sub26.aBoolean470) {
					Class180.method4115(arg0, local411.anInt6551, local12, local6, local9);
					Class95_Sub3.aClass19_37.copyLights(arg0.anInt5351, arg0.aClass4_Sub21Array3);
				}
				local2105 = local411.method5339(Class95_Sub3.aClass19_37);
				if (local2105 != null) {
					local2105.aClass16_1 = local411;
					local2105.anInt2381 = local12;
					local2105.anInt2384 = local6;
					local2105.anInt2383 = local9;
					Class4_Sub3.aClass84_2.offer(local2105);
				}
			}
			if (local1057 != null && (local1057.anInt6551 & arg1.aShort65) != 0 && !MouseManager.method2242(local15, local6, local9, local1057.anInt6551)) {
				if (Class4_Sub6_Sub26.aBoolean470) {
					Class180.method4115(arg0, local1057.anInt6551, local12, local6, local9);
					Class95_Sub3.aClass19_37.copyLights(arg0.anInt5351, arg0.aClass4_Sub21Array3);
				}
				local2105 = local1057.method5339(Class95_Sub3.aClass19_37);
				if (local2105 != null) {
					local2105.aClass16_1 = local1057;
					local2105.anInt2381 = local12;
					local2105.anInt2384 = local6;
					local2105.anInt2383 = local9;
					Class4_Sub3.aClass84_2.offer(local2105);
				}
			}
		}
		@Pc(2191) Class164 local2191;
		if (local12 < RemoteFileWorker.anInt4487 - 1) {
			local2191 = Class70.aClass164ArrayArrayArray5[local12 + 1][local6][local9];
			if (local2191 != null && local2191.aBoolean466) {
				Static433.aClass70_1.method5078(local2191);
			}
		}
		if (local6 < QuickChatPhrase.anInt7073) {
			local2191 = local31[local6 + 1][local9];
			if (local2191 != null && local2191.aBoolean466) {
				Static433.aClass70_1.method5080(local2191);
			}
		}
		if (local9 < OpenGlArbElementArrayBuffer.anInt1887) {
			local2191 = local31[local6][local9 + 1];
			if (local2191 != null && local2191.aBoolean466) {
				Static433.aClass70_1.method5080(local2191);
			}
		}
		if (local6 > QuickChatPhrase.anInt7073) {
			local2191 = local31[local6 - 1][local9];
			if (local2191 != null && local2191.aBoolean466) {
				Static433.aClass70_1.method5080(local2191);
			}
		}
		if (local9 > OpenGlArbElementArrayBuffer.anInt1887) {
			local2191 = local31[local6][local9 - 1];
			if (local2191 != null && local2191.aBoolean466) {
				Static433.aClass70_1.method5080(local2191);
			}
		}
	}
@OriginalMember(owner = "client!pf", name = "a", descriptor = "(Lclient!nf;Lclient!aq;BLclient!ya;)V")
	public static void method4102(@OriginalArg(0) MECType arg0, @OriginalArg(1) Class4_Sub3 arg1, @OriginalArg(3) com.jagex.game.runetek5.graphics.Toolkit arg2) {
		@Pc(10) NativeSprite local10 = arg0.method3780(arg2);
		if (local10 == null) {
			return;
		}
		@Pc(16) int local16 = local10.getWidth();
		if (local16 < local10.getHeight()) {
			local16 = local10.getHeight();
		}
		@Pc(28) int local28 = arg1.anInt235;
		@Pc(31) int local31 = arg1.anInt231;
		@Pc(33) int local33 = 0;
		@Pc(35) int local35 = 0;
		@Pc(37) int local37 = 0;
		@Pc(51) int local51;
		@Pc(74) int local74;
		if (arg0.aString47 != null) {
			local33 = Class229.aClass89_10.method2339(null, null, ParamTypeList.aStringArray35, arg0.aString47);
			for (local51 = 0; local51 < local33; local51++) {
				@Pc(57) String local57 = ParamTypeList.aStringArray35[local51];
				if (local33 - 1 > local51) {
					local57 = local57.substring(0, local57.length() - 4);
				}
				local74 = ParticleList.aClass212_5.getTextWidth(local57);
				if (local35 < local74) {
					local35 = local74;
				}
			}
			local37 = local33 * ParticleList.aClass212_5.getLineHeight() + ParticleList.aClass212_5.getHeight() / 2;
		}
		local51 = arg1.anInt235 + local16 / 2;
		@Pc(107) int local107 = arg1.anInt231;
		if (local16 + Static148.pixelViewStartX > local28) {
			local51 = Static148.pixelViewStartX + local16 / 2 + local35 / 2 + 15;
			local28 = Static148.pixelViewStartX;
		} else if (Static148.anInt5731 - local16 < local28) {
			local51 = Static148.anInt5731 - local16 / 2 - local35 / 2 - 10 - 5;
			local28 = Static148.anInt5731 - local16;
		}
		if (local16 + Static148.anInt5727 > local31) {
			local31 = Static148.anInt5727;
			local107 = local16 / 2 + Static148.anInt5727 + 10;
		} else if (local31 > Static148.pixelViewEndY - local16) {
			local107 = Static148.pixelViewEndY - local37 - local16 / 2 - 10;
			local31 = Static148.pixelViewEndY - local16;
		}
		local74 = (int) (Math.atan2((double) (local28 - arg1.anInt235), (double) (local31 - arg1.anInt231)) / 3.141592653589793D * 32767.0D) & 0xFFFF;
		local10.drawRotatedCenter((float) local28 + (float) local16 / 2.0F, (float) local16 / 2.0F + (float) local31, 4096, local74);
		@Pc(251) int local251 = -2;
		@Pc(253) int local253 = -2;
		@Pc(255) int local255 = -2;
		@Pc(257) int local257 = -2;
		if (arg0.aString47 != null) {
			local251 = local51 - local35 / 2 - 5;
			local253 = local107;
			local257 = local107 + ParticleList.aClass212_5.getLineHeight() * local33 + 3;
			local255 = local35 + local251 + 10;
			if (arg0.anInt4918 != 0) {
				arg2.method4293(local251, local255 - local251, local107, arg0.anInt4918, local257 - local107);
			}
			if (arg0.anInt4906 != 0) {
				arg2.method4295(local251, arg0.anInt4906, local257 - local107, -local251 + local255, local107);
			}
			for (@Pc(322) int local322 = 0; local322 < local33; local322++) {
				@Pc(328) String local328 = ParamTypeList.aStringArray35[local322];
				if (local322 < local33 - 1) {
					local328 = local328.substring(0, local328.length() - 4);
				}
				ParticleList.aClass212_5.renderCenter(arg2, local328, local51, local107, arg0.anInt4926);
				local107 += ParticleList.aClass212_5.getLineHeight();
			}
		}
		if (arg0.anInt4930 == -1 && arg0.aString47 == null) {
			return;
		}
		@Pc(379) Class4_Sub14 local379 = new Class4_Sub14(arg1);
		local16 >>= 0x1;
		local379.anInt1539 = local28 + local16;
		local379.anInt1544 = local31 - local16;
		local379.anInt1540 = local257;
		local379.anInt1546 = local255;
		local379.anInt1538 = local28 - local16;
		local379.anInt1542 = local31 + local16;
		local379.anInt1541 = local251;
		local379.anInt1543 = local253;
		EnvironmentSampler.aClass183_29.method4137(local379);
	}
@OriginalMember(owner = "client!pf", name = "a", descriptor = "(IZIII)V")
	public static void method4103(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		for (@Pc(1) int local1 = 0; local1 < SeqType.p12FullIndex; local1++) {
			@Pc(9) Rectangle local9 = Class4_Sub6_Sub23.aRectangleArray1[local1];
			if (arg0 < local9.width + local9.x && local9.x < arg2 + arg0 && arg3 < local9.height + local9.y && local9.y < arg1 + arg3) {
				PingRequest.aBooleanArray21[local1] = true;
			}
		}
	}
@OriginalMember(owner = "client!pf", name = "a", descriptor = "(I)Z")
	public static boolean method4105() {
		if (Class70_Sub2.aBoolean591) {
			try {
				if ((Boolean) Static458.method2593(Class126.aClass66_4.anApplet1, "showingVideoAd")) {
					return false;
				}
				return true;
			} catch (@Pc(23) Throwable local23) {
			}
		}
		return true;
	}
}
