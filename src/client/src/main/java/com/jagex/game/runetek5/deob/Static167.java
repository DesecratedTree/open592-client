package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.config.objtype.ObjTypeList;
import com.jagex.game.runetek5.deob.SpotAnimation;
import com.jagex.game.runetek5.tools.Isaac;
import com.jagex.game.runetek5.config.enumtype.EnumType;
import com.jagex.game.runetek5.config.midi.SoundBankCache;
import com.jagex.game.runetek5.deob.Class190;
import com.jagex.game.runetek5.deob.Class4_Sub17;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.deob.Class75;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
import com.jagex.game.runetek5.config.identitykit.IdkType;
import com.jagex.game.runetek5.cache.node.lru.ClientScript;
import com.jagex.game.runetek5.client.GameShell;
import com.jagex.game.runetek5.deob.Class120;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.deob.Class95_Sub2;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.graphics.texture.sampler.GlRectangleTexture;
import com.jagex.game.runetek5.config.cursor.CursorTypeList;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.entity.pathingentity.player.PlayerAppearance;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.deob.Class180;
import com.jagex.game.runetek5.deob.Resampler;
import com.jagex.game.runetek5.config.param.ParamTypeList;
import com.jagex.game.runetek5.deob.GameCanvas;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub6_Sub2;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.config.particle.ParticleProducerType;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub1;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class243;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.js5.request.Js5ResourceRequest;
import com.jagex.game.runetek5.graphics.gl.GlShadowMap;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.cache.node.lru.ProjectileAnimationNode;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpMandelbrot;
import com.jagex.game.runetek5.deob.ObjStackEntity;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.deob.Class103;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.config.rsinterface.HookRequest;
import com.jagex.game.runetek5.js5.request.RequestManager;
import com.jagex.game.runetek5.EnvironmentContext;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpMonochromeEdgeDetector;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class62;
import com.jagex.game.runetek5.deob.Class70_Sub1;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.deob.Class25;
import com.jagex.game.runetek5.deob.PositionedGfx;
import com.jagex.game.runetek5.deob.Class3_Sub4;
import com.jagex.game.runetek5.deob.Class48;
public final class Static167 {

	@OriginalMember(owner = "client!il", name = "e", descriptor = "Ljava/lang/String;")
	public static String aString35;

	@OriginalMember(owner = "client!il", name = "a", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_144 = new Class22(92, 2);

	@OriginalMember(owner = "client!il", name = "f", descriptor = "I")
	public static int anInt3231 = 0;

	@OriginalMember(owner = "client!il", name = "a", descriptor = "(I)V")
	public static void method2736() {
		Isaac.aClass76_20.loadType = 1;
		ClientScript.aClient1.method877();
		RequestManager.aBoolean457 = true;
		ObjStackEntity.aBoolean74 = true;
		Class3_Sub3.method2023();
		ParticleNode.aClass4_Sub12_Sub1_5.currentPosition = 0;
		Class190.aClass22_257 = null;
		Static410.anInt6735 = 0;
		IdkType.aClass22_246 = null;
		Class14.aClass4_Sub12_Sub1_3.currentPosition = 0;
		ProjectileAnimationNode.anInt1910 = 0;
		Class25.aClass22_42 = null;
		Static380.aClass22_294 = null;
		Class120.anInt5353 = 0;
		for (@Pc(5586) int local5586 = 0; local5586 < Class183.aClass38Array1.length; local5586++) {
			Class183.aClass38Array1[local5586] = null;
		}
		for (@Pc(5608) int local5608 = 0; local5608 < 100; local5608++) {
			Class4_Sub1_Sub2.aStringArray2[local5608] = null;
		}
		AbstractArbBufferGL.aBoolean599 = false;
		GlShadowMap.anInt6639 = 0;
		PositionedGfx.method2462();
		Class164.anInt5119 = 0;
		Static368.aFloat192 = 0.0F;
		Class3_Sub4.anInt4024 = 0;
		PlayerVarDomain.anInt3399 = 0;
		Class4_Sub17.anInt2374 = 0;
		TextureOpBrightness.anInt5620 = 0;
		PlayerAppearance.method836();
		for (@Pc(5675) int local5675 = 0; local5675 < 2048; local5675++) {
			Static267.aClass16_Sub1_Sub5_Sub1Array1[local5675] = null;
		}
		Class10.anInt163 = 0;
		for (@Pc(5689) int local5689 = 0; local5689 < 32768; local5689++) {
			Class93.aClass16_Sub1_Sub5_Sub2Array1[local5689] = null;
		}
		Class21.aClass183_2.method4138();
		HookRequest.aClass183_36.method4138();
		GameShell.aClass84_4.method2310();
		LocTypeList.aClass102_40.method2699();
		Class75.aClass183_15 = new Class183();
		Class4_Sub10.anInt2346 = 0;
		Model.anInt6277 = 0;
		Class4_Sub6_Sub24.aClass114_1.reset();
		TextureOpMonochromeEdgeDetector.method5405();
		ParamTypeList.anInt6417 = 0;
		Class133.anInt4125 = 0;
		Class14.anInt266 = 0;
		Class4_Sub6_Sub4.anInt1408 = 0;
		Class215.anInt6311 = 0;
		Class3_Sub3.anInt2379 = 0;
		StillWaterEffect.anInt6924 = 0;
		Class120.anInt5355 = 0;
		CacheableNodeByteArray.anInt4509 = 0;
		SoundBankCache.hslToRgbUsers = 0;
		for (@Pc(5748) int local5748 = 0; local5748 < ParticleEffector.WALL_ROTATION_FLAGS.length; local5748++) {
			if (!Class16_Sub1_Sub1.aBooleanArray3[local5748]) {
				ParticleEffector.WALL_ROTATION_FLAGS[local5748] = -1;
			}
		}
		if (EnumType.anInt5766 != -1) {
			EnvironmentContext.method4602(EnumType.anInt5766);
		}
		for (@Pc(5778) Class4_Sub43 local5778 = (Class4_Sub43) MusicPatch.aClass102_29.method2705(); local5778 != null; local5778 = (Class4_Sub43) MusicPatch.aClass102_29.method2704()) {
			if (!local5778.method5683()) {
				local5778 = (Class4_Sub43) MusicPatch.aClass102_29.method2705();
				if (local5778 == null) {
					break;
				}
			}
			Class62.method1606(true, local5778, false);
		}
		EnumType.anInt5766 = -1;
		MusicPatch.aClass102_29 = new CubeMapSpecularEffect(8);
		Class22.method529();
		Class180.aClass247_16 = null;
		for (@Pc(5818) int local5818 = 0; local5818 < 8; local5818++) {
			ObjTypeList.aStringArray29[local5818] = null;
			Class3_Sub4.aBooleanArray13[local5818] = false;
			GlRectangleTexture.anIntArray333[local5818] = -1;
		}
		Class48.method4403();
		Resampler.aBoolean676 = true;
		for (@Pc(5844) int local5844 = 0; local5844 < 100; local5844++) {
			PingRequest.aBooleanArray21[local5844] = true;
		}
		Class4_Sub1_Sub6_Sub2.aString39 = null;
		Class95_Sub2.anInt6410 = 0;
		GameShell.aClass15Array2 = null;
		for (@Pc(5862) int local5862 = 0; local5862 < 6; local5862++) {
			Class243.aClass28Array1[local5862] = new Class28();
		}
		for (@Pc(5878) int local5878 = 0; local5878 < 25; local5878++) {
			Static395.anIntArray502[local5878] = 0;
			ParticleProducerType.anIntArray258[local5878] = 0;
			Class103.anIntArray207[local5878] = 0;
		}
		Class133.method3355();
		BasType.aBoolean429 = true;
		GlVboArrayBuffer.aShortArray151 = OggOtherStream.aShortArray196 = Static78.aShortArray46 = Static166.aShortArray97 = new short[256];
		aString35 = Class4_Sub6_Sub15.aClass83_76.method2267(NativeBillboardFace.anInt3423);
		Js5ResourceRequest.mediumPos = 0;
		TextureOpBrightness.aClass50_Sub1_1.aBoolean308 = false;
		TextureOpBrightness.aClass50_Sub1_1.aBoolean309 = false;
		Class70_Sub1.method1816();
		Static1.method5();
		Isaac.aClass76_20.loadType = 2;
		NativeBillboardFace.aClass4_Sub42_2 = null;
		StillWaterEffect.aLong212 = 0L;
	}

	@OriginalMember(owner = "client!il", name = "a", descriptor = "(IIIIII)V")
	public static void method2738(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4) {
		@Pc(18) int local18 = arg3 - arg0;
		@Pc(23) int local23 = arg4 - arg1;
		if (local23 == 0) {
			if (local18 != 0) {
				CursorTypeList.method3980(arg3, arg0, arg1, arg2);
			}
		} else if (local18 == 0) {
			Static340.method4613(arg4, arg0, arg2, arg1);
		} else {
			if (local23 < 0) {
				local23 = -local23;
			}
			if (local18 < 0) {
				local18 = -local18;
			}
			@Pc(75) boolean local75 = local23 < local18;
			@Pc(79) int local79;
			@Pc(83) int local83;
			if (local75) {
				local79 = arg1;
				arg1 = arg0;
				local83 = arg4;
				arg4 = arg3;
				arg0 = local79;
				arg3 = local83;
			}
			if (arg4 < arg1) {
				local79 = arg1;
				arg1 = arg4;
				local83 = arg0;
				arg4 = local79;
				arg0 = arg3;
				arg3 = local83;
			}
			local79 = arg0;
			local83 = arg4 - arg1;
			@Pc(116) int local116 = arg3 - arg0;
			@Pc(121) int local121 = -(local83 >> 1);
			if (local116 < 0) {
				local116 = -local116;
			}
			@Pc(140) int local140 = arg3 > arg0 ? 1 : -1;
			@Pc(144) int local144;
			if (local75) {
				for (local144 = arg1; local144 <= arg4; local144++) {
					Class4_Sub6_Sub7.anIntArrayArray21[local144][local79] = arg2;
					local121 += local116;
					if (local121 > 0) {
						local79 += local140;
						local121 -= local83;
					}
				}
			} else {
				for (local144 = arg1; local144 <= arg4; local144++) {
					local121 += local116;
					Class4_Sub6_Sub7.anIntArrayArray21[local79][local144] = arg2;
					if (local121 > 0) {
						local79 += local140;
						local121 -= local83;
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!il", name = "a", descriptor = "(IZ)I")
	public static int method2739(@OriginalArg(0) int arg0) {
		return arg0 & 0xFF;
	}

	@OriginalMember(owner = "client!il", name = "a", descriptor = "(Z)V")
	public static void method2740() {
		SpotAnimation.method1241();
		MaterialType.method5385(TextureOpBrightness.aClass50_Sub1_1.aBoolean291);
		Class4_Sub43.aClass49_2 = Static380.method5016(0, Class126.aClass66_4, 22050, Class164.aCanvas5);
		Class4_Sub43.aClass49_2.method2070(Static363.aClass4_Sub15_Sub1_3);
		TextureOpMandelbrot.aClass49_1 = Static380.method5016(1, Class126.aClass66_4, 2048, Class164.aCanvas5);
		TextureOpMandelbrot.aClass49_1.method2070(GameCanvas.aClass4_Sub15_Sub2_2);
	}
}
