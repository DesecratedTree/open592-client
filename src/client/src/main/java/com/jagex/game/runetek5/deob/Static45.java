package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.enumtype.EnumType;
import com.jagex.game.runetek5.graphics.texture.water.BillowNoiseGenerator;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.toolkit.opengl.effect.BreakingWaterEffect;
import com.jagex.game.runetek5.cache.node.HashTable;
import com.jagex.game.runetek5.entity.ParticleEmitter;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.graphics.gl.GlShadowMap;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.graphics.light.LightGL;
import com.jagex.game.runetek5.graphics.shader.Texture3DShaderGL;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.deob.Class145_Sub1;
import com.jagex.game.runetek5.deob.Class15;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.Class3_Sub4;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
public final class Static45 {

	@OriginalMember(owner = "client!cb", name = "b", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_49 = new Class22(108, 8);

	@OriginalMember(owner = "client!cb", name = "a", descriptor = "(JI)V")
	public static void method764(@OriginalArg(0) long arg0) {
		if (Class70.aClass164ArrayArrayArray5 != null) {
			if (Class3_Sub3.anInt2386 == 1 || Class3_Sub3.anInt2386 == 5) {
				Class3_Sub4.method3266(arg0);
			} else if (Class3_Sub3.anInt2386 == 4) {
				Mesh.method3052(arg0);
			}
		}
		BreakingWaterEffect.method1340((long) Shadow.anInt5323, MonoStereoData.aClass19_16);
		if (EnumType.anInt5766 != -1) {
			Class14.method208(EnumType.anInt5766);
		}
		for (@Pc(40) int local40 = 0; local40 < SeqType.p12FullIndex; local40++) {
			if (PingRequest.aBooleanArray21[local40]) {
				Class4_Sub6_Sub25.aBooleanArray15[local40] = true;
			}
			Texture3DShaderGL.aBooleanArray9[local40] = PingRequest.aBooleanArray21[local40];
			PingRequest.aBooleanArray21[local40] = false;
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
		Class4_Sub6_Sub31.method4696(MonoStereoData.aClass19_16);
		@Pc(94) int local94 = BillowNoiseGenerator.method4409();
		if (local94 == -1) {
			local94 = HashTable.anInt3147;
		}
		if (local94 == -1) {
			local94 = Class30.anInt770;
		}
		Static368.method4938(local94);
		Static258.method3728(Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892, LightGL.npcCount, Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893, Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82);
		LightGL.npcCount = 0;
	}

	@OriginalMember(owner = "client!cb", name = "b", descriptor = "(JI)Ljava/lang/String;")
	public static String method766(@OriginalArg(0) long arg0) {
		if (arg0 <= 0L || arg0 >= 6582952005840035281L) {
			return null;
		} else if (arg0 % 37L == 0L) {
			return null;
		} else {
			@Pc(29) int local29 = 0;
			for (@Pc(36) long local36 = arg0; local36 != 0L; local36 /= 37L) {
				local29++;
			}
			@Pc(52) StringBuffer local52 = new StringBuffer(local29);
			while (arg0 != 0L) {
				@Pc(56) long local56 = arg0;
				arg0 /= 37L;
				@Pc(70) char local70 = Class15.aCharArray1[(int) (local56 - arg0 * 37L)];
				if (local70 == '_') {
					@Pc(80) int local80 = local52.length() - 1;
					local70 = ' ';
					local52.setCharAt(local80, Character.toUpperCase(local52.charAt(local80)));
				}
				local52.append(local70);
			}
			local52.reverse();
			local52.setCharAt(0, Character.toUpperCase(local52.charAt(0)));
			return local52.toString();
		}
	}
}
