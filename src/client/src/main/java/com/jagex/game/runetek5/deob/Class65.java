package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.cache.node.lru.Shadow;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.entity.PathingEntity;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.config.objtype.ObjTypeList;
import com.jagex.game.runetek5.deob.Class70_Sub2;
import com.jagex.game.runetek5.deob.Class79_Sub1;
import com.jagex.game.runetek5.graphics.texture.water.BillowNoiseGenerator;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTiledSprite;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.config.material.texture.MonochromeImageCache;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpInterpolate;
import com.jagex.game.runetek5.graphics.model.billboard.BillboardType;
import com.jagex.game.runetek5.graphics.texture.sampler.GlRectangleTexture;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.deob.Class180;
import com.jagex.game.runetek5.FixedFunctionSpecularPass;
import com.jagex.game.runetek5.toolkit.opengl.EffectManagerGL;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.TextureOpBoxBlur;
import com.jagex.game.runetek5.entity.DynamicWall;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.toolkit.vertexdeclaration.VertexDeclarationComponent;
import com.jagex.game.runetek5.deob.Class136;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.cache.node.GlGroundLayer;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTexture;
import com.jagex.game.runetek5.deob.PositionedGfx;
import com.jagex.game.runetek5.deob.Class3_Sub4;
import com.jagex.game.runetek5.deob.Class4_Sub21_Sub2;
@OriginalClass("client!ta")
public abstract class Class65 {

	@OriginalMember(owner = "client!ta", name = "g", descriptor = "I")
	public final int anInt5262;

	@OriginalMember(owner = "client!ta", name = "a", descriptor = "I")
	public int anInt5256;

	@OriginalMember(owner = "client!ta", name = "c", descriptor = "I")
	public final int anInt5258;

	@OriginalMember(owner = "client!ta", name = "f", descriptor = "I")
	public final int anInt5261;

	@OriginalMember(owner = "client!ta", name = "<init>", descriptor = "(III)V")
	protected Class65(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		this.anInt5262 = arg0;
		while (arg2 > 1) {
			arg2 >>= 0x1;
			this.anInt5256++;
		}
		this.anInt5258 = arg1;
		this.anInt5261 = 0x1 << this.anInt5256;
	}

	@OriginalMember(owner = "client!ta", name = "l", descriptor = "(II)I")
	public abstract int l(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1);

	@OriginalMember(owner = "client!ta", name = "k", descriptor = "(IILclient!ba;)Lclient!ba;")
	public abstract Shadow k(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Shadow arg2);

	@OriginalMember(owner = "client!ta", name = "ca", descriptor = "()V")
	public abstract void ca();

	@OriginalMember(owner = "client!ta", name = "a", descriptor = "(Lclient!ba;IIIIZ)Z")
	public abstract boolean method4022(@OriginalArg(0) Shadow arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3);

	@OriginalMember(owner = "client!ta", name = "a", descriptor = "(II[I[I[I[I[I[I[I[I[I[I[IIIIZ)V")
	public abstract void method4023(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int[] arg2, @OriginalArg(3) int[] arg3, @OriginalArg(4) int[] arg4, @OriginalArg(5) int[] arg5, @OriginalArg(6) int[] arg6, @OriginalArg(7) int[] arg7, @OriginalArg(8) int[] arg8, @OriginalArg(9) int[] arg9, @OriginalArg(10) int[] arg10, @OriginalArg(11) int[] arg11, @OriginalArg(12) int[] arg12, @OriginalArg(13) int arg13, @OriginalArg(14) int arg14, @OriginalArg(15) int arg15);

	@OriginalMember(owner = "client!ta", name = "a", descriptor = "(II)I")
	public abstract int a(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1);

	@OriginalMember(owner = "client!ta", name = "a", descriptor = "(Lclient!vp;[I)V")
	public abstract void method4024(@OriginalArg(0) PointLight arg0, @OriginalArg(1) int[] arg1);

	@OriginalMember(owner = "client!ta", name = "i", descriptor = "(III)V")
	public abstract void i(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2);

	@OriginalMember(owner = "client!ta", name = "a", descriptor = "(IIIIIII[[Z)V")
	public abstract void method4025(@OriginalArg(3) int arg0, @OriginalArg(4) int arg1, @OriginalArg(5) int arg2, @OriginalArg(6) int arg3, @OriginalArg(7) boolean[][] arg4);

	@OriginalMember(owner = "client!ta", name = "B", descriptor = "(II[I[I[I[I[I[I[I[IIIIZ)V")
	public abstract void B(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int[] arg2, @OriginalArg(3) int[] arg3, @OriginalArg(4) int[] arg4, @OriginalArg(5) int[] arg5, @OriginalArg(6) int[] arg6, @OriginalArg(7) int[] arg7, @OriginalArg(8) int[] arg8, @OriginalArg(9) int[] arg9, @OriginalArg(10) int arg10, @OriginalArg(11) int arg11, @OriginalArg(12) int arg12, @OriginalArg(13) boolean arg13);

	@OriginalMember(owner = "client!ta", name = "b", descriptor = "(II)V")
	public abstract void method4028(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1);

	@OriginalMember(owner = "client!ta", name = "a", descriptor = "(III[[ZZ)V")
	public abstract void method4029(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) boolean[][] arg3, @OriginalArg(4) boolean arg4);

	@OriginalMember(owner = "client!ta", name = "b", descriptor = "(Lclient!ba;IIIIZ)V")
	public abstract void b(@OriginalArg(0) Shadow arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) boolean arg5);

	@OriginalMember(owner = "client!ta", name = "p", descriptor = "(Lclient!ba;IIIIZ)V")
	public abstract void p(@OriginalArg(0) Shadow arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) boolean arg5);

@OriginalMember(owner = "client!ta", name = "a", descriptor = "(IZLclient!lh;)V")
	public static void method4026(@OriginalArg(1) boolean arg0, @OriginalArg(2) PlayerEntity arg1) {
		if (Skybox.anInt6710 >= 400) {
			return;
		}
		if (Static1.aClass16_Sub1_Sub5_Sub1_1 != arg1) {
			@Pc(85) String local85;
			@Pc(139) int local139;
			if (arg1.anInt4336 == 0) {
				@Pc(89) boolean local89 = true;
				if (Static1.aClass16_Sub1_Sub5_Sub1_1.anInt4330 != -1 && arg1.anInt4330 != -1) {
					@Pc(114) int local114 = Static1.aClass16_Sub1_Sub5_Sub1_1.anInt4345 > arg1.anInt4345 ? Static1.aClass16_Sub1_Sub5_Sub1_1.anInt4345 : arg1.anInt4345;
					@Pc(129) int local129 = arg1.anInt4330 > Static1.aClass16_Sub1_Sub5_Sub1_1.anInt4330 ? Static1.aClass16_Sub1_Sub5_Sub1_1.anInt4330 : arg1.anInt4330;
					local139 = local114 * 10 / 100 + local129 + 5;
					@Pc(146) int local146 = Static1.aClass16_Sub1_Sub5_Sub1_1.anInt4345 - arg1.anInt4345;
					if (local146 < 0) {
						local146 = -local146;
					}
					if (local146 > local139) {
						local89 = false;
					}
				}
				@Pc(176) String local176 = FixedFunctionSpecularPass.WRITE_PING == DynamicWall.aClass121_3 ? CacheableNode.aClass83_121.method2267(NativeBillboardFace.anInt3423) : BillowNoiseGenerator.aClass83_118.method2267(NativeBillboardFace.anInt3423);
				if (arg1.anInt4345 >= arg1.anInt4332) {
					local85 = arg1.method3428() + (local89 ? GlRectangleTexture.getCombatLevelColour(Static1.aClass16_Sub1_Sub5_Sub1_1.anInt4345, arg1.anInt4345) : "<col=ffffff>") + " (" + local176 + arg1.anInt4345 + ")";
				} else {
					local85 = arg1.method3428() + (local89 ? GlRectangleTexture.getCombatLevelColour(Static1.aClass16_Sub1_Sub5_Sub1_1.anInt4345, arg1.anInt4345) : "<col=ffffff>") + " (" + local176 + arg1.anInt4345 + "+" + (arg1.anInt4332 - arg1.anInt4345) + ")";
				}
			} else {
				local85 = arg1.method3428() + " (" + MonochromeImageCache.aClass83_49.method2267(NativeBillboardFace.anInt3423) + arg1.anInt4336 + ")";
			}
			if (AbstractArbBufferGL.aBoolean599) {
				if (!arg0 && (Country.anInt7378 & 0x8) != 0) {
					Static380.method5017(46, false, (long) arg1.anInt6037, 0, Class4_Sub10.aString28 + " -> <col=ffffff>" + local85, true, Class230.aString62, 0, -1, Class164.anInt5120);
				}
			} else if (arg0) {
				Static380.method5017(-1, true, 0L, 0, "", false, "<col=cccccc>" + local85, 0, 0, -1);
			} else {
				for (@Pc(282) int local282 = 7; local282 >= 0; local282--) {
					if (ObjTypeList.aStringArray29[local282] != null) {
						@Pc(290) short local290 = 0;
						if (DynamicWall.aClass121_3 == TextureOpTexture.aClass121_1 && ObjTypeList.aStringArray29[local282].equalsIgnoreCase(GlGroundLayer.aClass83_33.method2267(NativeBillboardFace.anInt3423))) {
							if (arg1.anInt4345 > Static1.aClass16_Sub1_Sub5_Sub1_1.anInt4345) {
								local290 = 2000;
							}
							if (Static1.aClass16_Sub1_Sub5_Sub1_1.anInt4328 != 0 && arg1.anInt4328 != 0) {
								if (Static1.aClass16_Sub1_Sub5_Sub1_1.anInt4328 == arg1.anInt4328) {
									local290 = 2000;
								} else {
									local290 = 0;
								}
							}
						} else if (Class3_Sub4.aBooleanArray13[local282]) {
							local290 = 2000;
						}
						@Pc(351) short local351 = (short) (local290 + Class70_Sub2.aShortArray232[local282]);
						local139 = GlRectangleTexture.anIntArray333[local282] == -1 ? Class6.anInt5453 : GlRectangleTexture.anIntArray333[local282];
						Static380.method5017(local351, false, (long) arg1.anInt6037, 0, "<col=ffffff>" + local85, true, ObjTypeList.aStringArray29[local282], 0, -1, local139);
					}
				}
			}
			if (!arg0) {
				for (@Pc(440) OggOtherStream local440 = (OggOtherStream) Class126.aClass183_28.method4140(); local440 != null; local440 = (OggOtherStream) Class126.aClass183_28.method4144()) {
					if (local440.anInt5699 == 44) {
						local440.aString56 = "<col=ffffff>" + local85;
						return;
					}
				}
			}
		} else if (AbstractArbBufferGL.aBoolean599 && (Country.anInt7378 & 0x10) != 0) {
			Static380.method5017(8, false, 0L, 0, Class4_Sub10.aString28 + " -> <col=ffffff>" + PlayerEntity.aClass83_91.method2267(NativeBillboardFace.anInt3423), true, Class230.aString62, 0, -1, Class164.anInt5120);
		}
	}
@OriginalMember(owner = "client!ta", name = "a", descriptor = "(IIIIIII)V")
	public static void method4027(@OriginalArg(0) int arg0, @OriginalArg(3) int arg1, @OriginalArg(5) int arg2, @OriginalArg(6) int arg3) {
		@Pc(7) int local7 = Class4_Sub7.anInt606;
		Class136.anInt4371 = 0;
		@Pc(11) int[] local11 = Static99.anIntArray157;
		@Pc(187) int local187;
		@Pc(216) int local216;
		@Pc(263) int local263;
		@Pc(338) int local338;
		@Pc(427) int local427;
		@Pc(867) int local867;
		@Pc(560) int local560;
		for (@Pc(13) int local13 = 0; local13 < Class10.anInt163 + local7; local13++) {
			@Pc(17) NPCType local17 = null;
			@Pc(30) PathingEntity local30;
			if (local7 > local13) {
				local30 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local11[local13]];
			} else {
				local30 = Class93.aClass16_Sub1_Sub5_Sub2Array1[TextureOpBoxBlur.anIntArray412[local13 - local7]];
				local17 = ((NPCEntity) local30).npcType;
				if (local17.anIntArray544 != null) {
					local17 = local17.method5574(Class4_Sub6_Sub24.aClass114_1);
					if (local17 == null) {
						continue;
					}
				}
			}
			if (local30.worldPriority >= 0 && (local30.drawCycle == Class79_Sub1.anInt6264 || local30.aByte82 == Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82)) {
				ModeWhere.method4939(arg0 >> 1, arg3 >> 1, local30.getOverheadHeight(), local30);
				if (Static449.anIntArray557[0] >= 0) {
					if (local30.aString60 != null && (local13 >= local7 || Static14.anInt189 == 0 || Static14.anInt189 == 3 || Static14.anInt189 == 1 && PositionedGfx.method2458(((PlayerEntity) local30).aString44)) && VertexDeclarationComponent.anInt6670 > Class136.anInt4371) {
						VertexDeclarationComponent.anIntArray508[Class136.anInt4371] = Js5ResourceProvider.aClass89_5.getWidthNoSprites(local30.aString60) / 2;
						VertexDeclarationComponent.anIntArray511[Class136.anInt4371] = Static449.anIntArray557[0];
						VertexDeclarationComponent.anIntArray509[Class136.anInt4371] = Static449.anIntArray557[1];
						VertexDeclarationComponent.anIntArray507[Class136.anInt4371] = local30.anInt6084;
						VertexDeclarationComponent.anIntArray510[Class136.anInt4371] = local30.anInt6064;
						VertexDeclarationComponent.anIntArray512[Class136.anInt4371] = local30.anInt6077;
						VertexDeclarationComponent.aStringArray36[Class136.anInt4371] = local30.aString60;
						Class136.anInt4371++;
					}
					local187 = Static449.anIntArray557[1] + arg1;
					@Pc(248) NativeSprite[] local248;
					@Pc(255) SpriteType[] local255;
					@Pc(311) NativeSprite local311;
					if (local30.isHidden || local30.anInt6072 <= Shadow.anInt5323) {
						local187 -= Math.max(Js5ResourceProvider.aClass89_5.anInt2783, TextureOpInterpolate.aClass57Array8[0].getHeight());
					} else {
						@Pc(202) byte local202 = 1;
						if (local7 > local13) {
							@Pc(211) PlayerEntity local211 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local11[local13]];
							local216 = local30.method4757().anInt4692;
							if (local211.pMapdot) {
								local202 = 2;
							}
						} else {
							local216 = local17.anInt7204;
							if (local216 == -1) {
								local216 = local30.method4757().anInt4692;
							}
						}
						@Pc(238) NativeSprite[] local238 = TextureOpInterpolate.aClass57Array8;
						if (local216 != -1) {
							local248 = (NativeSprite[]) MECType.aClass68_27.method1787((long) local216);
							if (local248 == null) {
								local255 = SpriteType.decodeGroup(GlGround.aClass76_60, local216);
								if (local255 != null) {
									local248 = new NativeSprite[local255.length];
									for (local263 = 0; local263 < local255.length; local263++) {
										local248[local263] = MonoStereoData.aClass19_16.generateSprite(local255[local263]);
									}
									MECType.aClass68_27.method1779((long) local216, local248);
								}
							}
							if (local248 != null && local248.length >= 2) {
								local238 = local248;
							}
						}
						if (local202 >= local238.length) {
							local202 = 1;
						}
						@Pc(307) NativeSprite local307 = local238[0];
						local311 = local238[local202];
						local187 -= Math.max(Js5ResourceProvider.aClass89_5.anInt2783, local307.getHeight());
						local263 = Static449.anIntArray557[0] + arg2 - (local307.getWidth() >> 1);
						local338 = local307.getWidth() * local30.anInt6026 / 255;
						if (local30.anInt6026 > 0 && local338 < 2) {
							local338 = 2;
						}
						local307.render(local263, local187);
						MonoStereoData.aClass19_16.fa(local263, local187, local263 + local338, local307.getHeight() + local187);
						local311.render(local263, local187);
						MonoStereoData.aClass19_16.OA(arg2, arg1, arg0 + arg2, arg1 + arg3);
					}
					local187 -= 2;
					if (!local30.isHidden) {
						@Pc(408) NativeSprite local408;
						if (Shadow.anInt5323 < local30.anInt6035) {
							local408 = Static410.aClass57Array17[local30.aBoolean559 ? 2 : 0];
							@Pc(417) NativeSprite local417 = Static410.aClass57Array17[local30.aBoolean559 ? 3 : 1];
							if (local30 instanceof NPCEntity) {
								local427 = local17.anInt7218;
								if (local427 == -1) {
									local427 = local30.method4757().anInt4673;
								}
							} else {
								local427 = local30.method4757().anInt4673;
							}
							if (local427 != -1) {
								local248 = (NativeSprite[]) Class4_Sub21_Sub2.aClass68_22.method1787((long) local427);
								if (local248 == null) {
									local255 = SpriteType.decodeGroup(GlGround.aClass76_60, local427);
									if (local255 != null) {
										local248 = new NativeSprite[local255.length];
										for (local263 = 0; local263 < local255.length; local263++) {
											local248[local263] = MonoStereoData.aClass19_16.generateSprite(local255[local263]);
										}
										Class4_Sub21_Sub2.aClass68_22.method1779((long) local427, local248);
									}
								}
								if (local248 != null && local248.length == 4) {
									local408 = local248[local30.aBoolean559 ? 2 : 0];
									local417 = local248[local30.aBoolean559 ? 3 : 1];
								}
							}
							@Pc(526) int local526 = local30.anInt6035 - Shadow.anInt5323;
							if (local30.anInt6051 < local526) {
								local526 -= local30.anInt6051;
								local263 = local30.anInt6054 == 0 ? 0 : local30.anInt6054 * ((local30.anInt6041 - local526) / local30.anInt6054);
								local560 = local408.getWidth() * local263 / local30.anInt6041;
							} else {
								local560 = local408.getWidth();
							}
							local263 = local408.getHeight();
							local187 -= local263;
							local338 = Static449.anIntArray557[0] + arg2 - (local408.getWidth() >> 1);
							local408.render(local338, local187);
							MonoStereoData.aClass19_16.fa(local338, local187, local338 + local560, local263 + local187);
							local417.render(local338, local187);
							local187 -= 2;
							MonoStereoData.aClass19_16.OA(arg2, arg1, arg2 + arg0, arg1 - -arg3);
						}
						if (local13 < local7) {
							@Pc(620) PlayerEntity local620 = (PlayerEntity) local30;
							if (local620.anInt4339 != -1) {
								local187 -= 25;
								BillboardType.aClass57Array9[local620.anInt4339].render(Static449.anIntArray557[0] + arg2 - 12, local187);
								local187 -= 2;
							}
							if (local620.anInt4346 != -1) {
								local187 -= 25;
								Static107.aClass57Array6[local620.anInt4346].render(arg2 + Static449.anIntArray557[0] - 12, local187);
								local187 -= 2;
							}
						} else if (local17.anInt7205 >= 0 && local17.anInt7205 < Static107.aClass57Array6.length) {
							local408 = Static107.aClass57Array6[local17.anInt7205];
							local187 -= 25;
							local408.render(Static449.anIntArray557[0] + arg2 - (local408.getWidth() >> 1), local187);
							local187 -= 2;
						}
					}
					@Pc(697) Class38[] local697;
					@Pc(705) Class38 local705;
					if (!(local30 instanceof PlayerEntity)) {
						local216 = 0;
						local697 = Class183.aClass38Array1;
						for (local427 = 0; local427 < local697.length; local427++) {
							local705 = local697[local427];
							if (local705 != null && local705.anInt891 == 1 && TextureOpBoxBlur.anIntArray412[local13 - local7] == local705.anInt885) {
								local311 = TextureOpTiledSprite.aClass57Array3[local705.anInt886];
								if (local311.getHeight() > local216) {
									local216 = local311.getHeight();
								}
								if (Shadow.anInt5323 % 20 < 10) {
									local311.render(arg2 + Static449.anIntArray557[0] - 12, local187 + -local311.getHeight());
								}
							}
						}
						if (local216 > 0) {
						}
					} else if (local13 >= 0) {
						local216 = 0;
						local697 = Class183.aClass38Array1;
						for (local427 = 0; local427 < local697.length; local427++) {
							local705 = local697[local427];
							if (local705 != null && local705.anInt891 == 10 && local11[local13] == local705.anInt885) {
								local311 = TextureOpTiledSprite.aClass57Array3[local705.anInt886];
								if (local216 < local311.getHeight()) {
									local216 = local311.getHeight();
								}
								local311.render(Static449.anIntArray557[0] + arg2 - 12, local187 - local311.getHeight());
							}
						}
						if (local216 > 0) {
						}
					}
					for (local216 = 0; local216 < 4; local216++) {
						if (Shadow.anInt5323 < local30.anIntArray424[local216]) {
							local867 = local30.getOverheadHeight() / 2;
							ModeWhere.method4939(arg0 >> 1, arg3 >> 1, local867, local30);
							if (Static449.anIntArray557[0] > -1) {
								if (local216 == 1) {
									Static449.anIntArray557[1] -= 20;
								}
								if (local216 == 2) {
									Static449.anIntArray557[1] -= 10;
									Static449.anIntArray557[0] -= 15;
								}
								if (local216 == 3) {
									Static449.anIntArray557[1] -= 10;
									Static449.anIntArray557[0] += 15;
								}
								ObjTypeList.aClass57Array14[local30.anIntArray425[local216]].render(arg2 + Static449.anIntArray557[0] - 12, Static449.anIntArray557[1] + -12 + arg1);
								Ground.aClass46_12.method5056(arg2 + Static449.anIntArray557[0] - 1, -1, arg1 + 3 - -Static449.anIntArray557[1], 0, Integer.toString(local30.anIntArray422[local216]));
							}
						}
					}
				}
			}
		}
		@Pc(1004) int local1004;
		for (@Pc(998) int local998 = 0; local998 < Class180.anInt5369; local998++) {
			local1004 = Class136.anIntArray290[local998];
			@Pc(1011) PathingEntity local1011;
			if (local1004 < 2048) {
				local1011 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local1004];
			} else {
				local1011 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local1004 - 2048];
			}
			local216 = EffectManagerGL.anIntArray525[local998];
			@Pc(1034) PathingEntity local1034;
			if (local216 >= 2048) {
				local1034 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local216 - 2048];
			} else {
				local1034 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local216];
			}
			method4030(arg1, arg2, local1034, local1011, --local1011.overheadStackCount, arg0, arg3);
		}
		local1004 = Js5ResourceProvider.aClass89_5.anInt2778 + Js5ResourceProvider.aClass89_5.anInt2783 + 2;
		for (local187 = 0; local187 < Class136.anInt4371; local187++) {
			local216 = VertexDeclarationComponent.anIntArray511[local187];
			local867 = VertexDeclarationComponent.anIntArray509[local187];
			local427 = VertexDeclarationComponent.anIntArray508[local187];
			@Pc(1087) boolean local1087 = true;
			while (local1087) {
				local1087 = false;
				for (local560 = 0; local560 < local187; local560++) {
					if (VertexDeclarationComponent.anIntArray509[local560] - local1004 < local867 + 2 && VertexDeclarationComponent.anIntArray509[local560] + 2 > local867 - local1004 && VertexDeclarationComponent.anIntArray508[local560] + VertexDeclarationComponent.anIntArray511[local560] > local216 - local427 && VertexDeclarationComponent.anIntArray511[local560] - VertexDeclarationComponent.anIntArray508[local560] < local427 + local216 && local867 > VertexDeclarationComponent.anIntArray509[local560] - local1004) {
						local867 = VertexDeclarationComponent.anIntArray509[local560] - local1004;
						local1087 = true;
					}
				}
			}
			VertexDeclarationComponent.anIntArray509[local187] = local867;
			@Pc(1184) String local1184 = VertexDeclarationComponent.aStringArray36[local187];
			if (Game.anInt3572 == 0) {
				local263 = 16776960;
				if (VertexDeclarationComponent.anIntArray507[local187] < 6) {
					local263 = PlayerEntity.visibleBelowDrawheight[VertexDeclarationComponent.anIntArray507[local187]];
				}
				if (VertexDeclarationComponent.anIntArray507[local187] == 6) {
					local263 = Class79_Sub1.anInt6264 % 20 >= 10 ? 16776960 : 16711680;
				}
				if (VertexDeclarationComponent.anIntArray507[local187] == 7) {
					local263 = Class79_Sub1.anInt6264 % 20 >= 10 ? 65535 : 255;
				}
				if (VertexDeclarationComponent.anIntArray507[local187] == 8) {
					local263 = Class79_Sub1.anInt6264 % 20 >= 10 ? 8454016 : 45056;
				}
				if (VertexDeclarationComponent.anIntArray507[local187] == 9) {
					local338 = 150 - VertexDeclarationComponent.anIntArray512[local187];
					if (local338 < 50) {
						local263 = local338 * 1280 + 16711680;
					} else if (local338 < 100) {
						local263 = 16776960 - (local338 - 50) * 327680;
					} else if (local338 < 150) {
						local263 = (local338 - 100) * 5 + 65280;
					}
				}
				if (VertexDeclarationComponent.anIntArray507[local187] == 10) {
					local338 = 150 - VertexDeclarationComponent.anIntArray512[local187];
					if (local338 < 50) {
						local263 = local338 * 5 + 16711680;
					} else if (local338 < 100) {
						local263 = 16711935 - (local338 - 50) * 327680;
					} else if (local338 < 150) {
						local263 = (local338 - 100) * 327680 + 255 + 500 - local338 * 5;
					}
				}
				if (VertexDeclarationComponent.anIntArray507[local187] == 11) {
					local338 = 150 - VertexDeclarationComponent.anIntArray512[local187];
					if (local338 < 50) {
						local263 = 16777215 - local338 * 327685;
					} else if (local338 < 100) {
						local263 = local338 * 327685 + 65280 - 16384250;
					} else if (local338 < 150) {
						local263 = 32768000 + 16777215 - local338 * 327680;
					}
				}
				local338 = local263 | 0xFF000000;
				if (VertexDeclarationComponent.anIntArray510[local187] == 0) {
					Static331.aClass46_10.method5056(arg2 + local216, local338, local867 + arg1, -16777216, local1184);
				}
				if (VertexDeclarationComponent.anIntArray510[local187] == 1) {
					Static331.aClass46_10.method5050(local338, local1184, local867 + arg1, local216 + arg2, Class79_Sub1.anInt6264);
				}
				if (VertexDeclarationComponent.anIntArray510[local187] == 2) {
					Static331.aClass46_10.method5046(arg1 + local867, local216 + arg2, local1184, Class79_Sub1.anInt6264, local338);
				}
				if (VertexDeclarationComponent.anIntArray510[local187] == 3) {
					Static331.aClass46_10.method5064(local338, arg1 + local867, local1184, arg2 + local216, Class79_Sub1.anInt6264, 150 - VertexDeclarationComponent.anIntArray512[local187]);
				}
				@Pc(1537) int local1537;
				if (VertexDeclarationComponent.anIntArray510[local187] == 4) {
					local1537 = (150 - VertexDeclarationComponent.anIntArray512[local187]) * (Js5ResourceProvider.aClass89_5.getWidthNoSprites(local1184) + 100) / 150;
					MonoStereoData.aClass19_16.fa(arg2 + local216 - 50, arg1, arg2 + local216 + 50, arg1 - -arg3);
					Static331.aClass46_10.method5058(-16777216, local338, local1184, arg1 + local867, arg2 + 50 + local216 - local1537);
					MonoStereoData.aClass19_16.OA(arg2, arg1, arg0 + arg2, arg3 + arg1);
				}
				if (VertexDeclarationComponent.anIntArray510[local187] == 5) {
					local1537 = 150 - VertexDeclarationComponent.anIntArray512[local187];
					@Pc(1595) int local1595 = 0;
					if (local1537 < 25) {
						local1595 = local1537 - 25;
					} else if (local1537 > 125) {
						local1595 = local1537 - 125;
					}
					@Pc(1621) int local1621 = Js5ResourceProvider.aClass89_5.anInt2783 + Js5ResourceProvider.aClass89_5.anInt2778;
					MonoStereoData.aClass19_16.fa(arg2, local867 + arg1 - local1621 - 1, arg2 - -arg0, local867 + arg1 + 5);
					Static331.aClass46_10.method5056(local216 + arg2, local338, local867 + arg1 + local1595, -16777216, local1184);
					MonoStereoData.aClass19_16.OA(arg2, arg1, arg0 + arg2, arg3 + arg1);
				}
			} else {
				Static331.aClass46_10.method5056(arg2 + local216, -256, local867 + arg1, -16777216, local1184);
			}
		}
	}
@OriginalMember(owner = "client!ta", name = "a", descriptor = "(ZIIILclient!r;Lclient!r;IIII)V")
	private static void method4030(@OriginalArg(2) int arg0, @OriginalArg(3) int arg1, @OriginalArg(4) PathingEntity arg2, @OriginalArg(5) PathingEntity arg3, @OriginalArg(6) int arg4, @OriginalArg(7) int arg5, @OriginalArg(8) int arg6) {
		@Pc(7) int local7 = arg2.getOverheadSprite();
		if (local7 == -1) {
			return;
		}
		@Pc(20) NativeSprite local20 = (NativeSprite) Class99_Sub1.aClass68_23.method1787((long) local7);
		if (local20 == null) {
			@Pc(27) SpriteType[] local27 = SpriteType.decodeGroup(GlGround.aClass76_60, local7);
			if (local27 == null) {
				return;
			}
			local20 = MonoStereoData.aClass19_16.generateSprite(local27[0]);
			Class99_Sub1.aClass68_23.method1779((long) local7, local20);
		}
		EnvironmentSampler.method3226(arg3.anInt6893, arg3.anInt6892, arg3.getSize() * 64, arg5 >> 1, 0, arg3.aByte82, arg6 >> 1);
		@Pc(73) int local73 = Static449.anIntArray557[0] + arg1 - 18;
		@Pc(81) int local81 = local73 + arg4 / 4 * 18;
		@Pc(91) int local91 = Static449.anIntArray557[1] + arg0 - 16 - 54;
		@Pc(99) int local99 = local91 + arg4 % 4 * 18;
		local20.render(local81, local99);
		if (arg3 == arg2) {
			MonoStereoData.aClass19_16.method4295(local81 - 1, -256, 18, 18, local99 - 1);
		}
		@Pc(121) Class3_Sub4 local121 = Static411.method5273();
		local121.anInt4019 = local81;
		local121.anInt4023 = local99;
		local121.anInt4022 = local81 + 16;
		local121.aClass16_Sub1_Sub5_1 = arg2;
		local121.anInt4021 = local99 + 16;
		LRUCache.aClass84_5.offer(local121);
	}
}
