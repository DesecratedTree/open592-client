package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class4_Sub9;
import com.jagex.game.runetek5.config.objtype.ObjTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.config.enumtype.EnumType;
import com.jagex.game.runetek5.deob.Class186;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTiledSprite;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.config.material.texture.MonochromeImageCache;
import com.jagex.game.runetek5.toolkit.opengl.effect.BreakingWaterEffect;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpInterpolate;
import com.jagex.game.runetek5.graphics.model.billboard.BillboardType;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.entity.ParticleEmitter;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.deob.TextureOpBoxBlur;
import com.jagex.game.runetek5.entity.pathingentity.EasedAngle;
import com.jagex.game.runetek5.graphics.gl.GlShadowMap;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.graphics.light.LightGL;
import com.jagex.game.runetek5.graphics.shader.Texture3DShaderGL;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.deob.Class145_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub5;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.ReflectingTextureEffect;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpVoronoiNoise;
public final class Static298 {

	@OriginalMember(owner = "client!pb", name = "d", descriptor = "Lclient!pk;")
	public static final Class183 aClass183_38 = new Class183();

	@OriginalMember(owner = "client!pb", name = "e", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_112 = new Class83("slide:", "gleiten:", "glissement:", "deslizamento:");

	@OriginalMember(owner = "client!pb", name = "f", descriptor = "S")
	public static short aShort67 = 256;

	@OriginalMember(owner = "client!pb", name = "a", descriptor = "(IIIIII)Z")
	public static boolean method4083(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(5) int arg4) {
		for (@Pc(7) int local7 = arg4; local7 <= arg2; local7++) {
			for (@Pc(11) int local11 = arg1; local11 <= arg0; local11++) {
				if (EasedAngle.anIntArrayArray8[local7][local11] == arg3 && Class4_Sub9.anIntArrayArray7[local7][local11] <= 1) {
					return true;
				}
			}
		}
		return false;
	}

	@OriginalMember(owner = "client!pb", name = "a", descriptor = "(Z)V")
	public static void method4085() {
		BreakingWaterEffect.method1340((long) Shadow.anInt5323, MonoStereoData.aClass19_16);
		if (EnumType.anInt5766 != -1) {
			Class14.method208(EnumType.anInt5766);
		}
		for (@Pc(24) int local24 = 0; local24 < SeqType.p12FullIndex; local24++) {
			if (PingRequest.aBooleanArray21[local24]) {
				Class4_Sub6_Sub25.aBooleanArray15[local24] = true;
			}
			Texture3DShaderGL.aBooleanArray9[local24] = PingRequest.aBooleanArray21[local24];
			PingRequest.aBooleanArray21[local24] = false;
		}
		ParticleEmitter.anInt4639 = Shadow.anInt5323;
		if (MonoStereoData.aClass19_16.method4243()) {
			GlShadowMap.aBoolean600 = true;
		}
		if (EnumType.anInt5766 != -1) {
			SeqType.p12FullIndex = 0;
			Class145_Sub1.method5615();
		}
		MonoStereoData.aClass19_16.e();
		Static368.method4938(Class30.anInt770);
		LightGL.npcCount = 0;
	}

	@OriginalMember(owner = "client!pb", name = "b", descriptor = "(Z)V")
	public static void method4086() {
		TextureOpBoxBlur.aClass57Array13 = null;
		TextureOpInterpolate.aClass57Array8 = null;
		Class186.aClass57Array10 = null;
		Static410.aClass57Array17 = null;
		Class6.aClass46_9 = null;
		Ground.aClass46_12 = null;
		TextureOpVoronoiNoise.aClass57_22 = null;
		Static107.aClass57Array6 = null;
		ReflectingTextureEffect.aClass57Array12 = null;
		TextureOpTiledSprite.aClass57Array3 = null;
		Toolkit.aClass57Array11 = null;
		MonochromeImageCache.aClass57Array7 = null;
		PingWorker.aClass57Array18 = null;
		BillboardType.aClass57Array9 = null;
		Class4_Sub6_Sub5.aClass57Array4 = null;
		Static331.aClass46_10 = null;
		Class4_Sub23.aClass57_16 = null;
		ObjTypeList.aClass57Array14 = null;
	}
}
