package com.jagex.game.runetek5.cache.node;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.entity.PathingEntity;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.cache.node.*;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Class93;
@OriginalClass("client!hc")
public final class GpuPointLight extends PointLight {

	@OriginalMember(owner = "client!hc", name = "<init>", descriptor = "(IIIIIF)V")
	public GpuPointLight(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int z, @OriginalArg(3) int radius, @OriginalArg(4) int colour, @OriginalArg(5) float intensity) {
		super(x, y, z, radius, colour, intensity);
	}

	@OriginalMember(owner = "client!hc", name = "a", descriptor = "(IIIB)V")
	@Override
	public void setPosition(@OriginalArg(0) int x, @OriginalArg(1) int arg1, @OriginalArg(2) int z) {
		super.anInt3866 = arg1;
		super.anInt3856 = z;
		super.anInt3860 = x;
	}

	@OriginalMember(owner = "client!hc", name = "a", descriptor = "(IF)V")
	@Override
	public void setIntensity(@OriginalArg(1) float arg0) {
		super.intensity = arg0;
	}

@OriginalMember(owner = "client!hc", name = "C", descriptor = "I")
	public static int anInt2881;
@OriginalMember(owner = "client!hc", name = "D", descriptor = "[Ljava/lang/String;")
	public static final String[] aStringArray11 = new String[100];
@OriginalMember(owner = "client!hc", name = "a", descriptor = "(BLclient!r;)I")
	public static int method2424(@OriginalArg(1) PathingEntity arg0) {
		if (arg0.anInt6080 == 0) {
			return 0;
		}
		@Pc(49) int local49;
		@Pc(56) int local56;
		if (arg0.anInt6034 != -1) {
			@Pc(19) PathingEntity local19 = null;
			if (arg0.anInt6034 < 32768) {
				local19 = Class93.aClass16_Sub1_Sub5_Sub2Array1[arg0.anInt6034];
			} else if (arg0.anInt6034 >= 32768) {
				local19 = Static267.aClass16_Sub1_Sub5_Sub1Array1[arg0.anInt6034 - 32768];
			}
			if (local19 != null) {
				local49 = arg0.anInt6893 - local19.anInt6893;
				local56 = arg0.anInt6892 - local19.anInt6892;
				if (local49 != 0 || local56 != 0) {
					arg0.method4743((int) (Math.atan2((double) local49, (double) local56) * 2607.5945876176133D) & 0x3FFF);
				}
			}
		}
		if (arg0 instanceof PlayerEntity) {
			@Pc(152) PlayerEntity local152 = (PlayerEntity) arg0;
			if (local152.anInt4323 != -1 && (local152.pathPointer == 0 || local152.anInt6085 > 0)) {
				local152.method4743(local152.anInt4323);
				local152.anInt4323 = -1;
			}
		} else if (arg0 instanceof NPCEntity) {
			@Pc(82) NPCEntity local82 = (NPCEntity) arg0;
			if (local82.anInt6089 != -1 && (local82.pathPointer == 0 || local82.anInt6085 > 0)) {
				local49 = local82.anInt6893 - (local82.anInt6089 - Class50_Sub1.anInt3453 - Class50_Sub1.anInt3453) * 64;
				local56 = local82.anInt6892 - (local82.anInt6095 - OpenGLTextureCache.anInt1771 - OpenGLTextureCache.anInt1771) * 64;
				if (local49 != 0 || local56 != 0) {
					local82.method4743((int) (Math.atan2((double) local49, (double) local56) * 2607.5945876176133D) & 0x3FFF);
				}
				local82.anInt6089 = -1;
			}
		}
		return arg0.rotationTick();
	}
}
