package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.*;
import com.jagex.game.runetek5.deob.Class48_Sub1_Sub1;
import com.jagex.game.runetek5.graphics.model.billboard.BillboardType;
import com.jagex.game.runetek5.config.sequence.SeqTypeList;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class70_Sub2;
import com.jagex.game.runetek5.deob.Class79_Sub1;
import com.jagex.game.runetek5.deob.ReflectingTextureEffect;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.deob.Class10;
@OriginalClass("client!kr")
public final class OpenGLArbTextureCubeMapGenerator extends Class99_Sub1 {

	@OriginalMember(owner = "client!kr", name = "B", descriptor = "Lclient!im;")
	private GlCubeTexture cubeTexture;

	@OriginalMember(owner = "client!kr", name = "y", descriptor = "I")
	private final int material1;

	@OriginalMember(owner = "client!kr", name = "A", descriptor = "I")
	private final int material6;

	@OriginalMember(owner = "client!kr", name = "v", descriptor = "I")
	private final int material5;

	@OriginalMember(owner = "client!kr", name = "z", descriptor = "I")
	private final int material3;

	@OriginalMember(owner = "client!kr", name = "w", descriptor = "I")
	private final int material4;

	@OriginalMember(owner = "client!kr", name = "E", descriptor = "Lclient!fd;")
	private final GlToolkit glToolkit;

	@OriginalMember(owner = "client!kr", name = "t", descriptor = "I")
	private final int material2;

	@OriginalMember(owner = "client!kr", name = "<init>", descriptor = "(Lclient!fd;IIIIII)V")
	public OpenGLArbTextureCubeMapGenerator(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) int material1, @OriginalArg(2) int material2, @OriginalArg(3) int material3, @OriginalArg(4) int material4, @OriginalArg(5) int material5, @OriginalArg(6) int material6) {
		this.material1 = material4;
		this.material6 = material5;
		this.material5 = material6;
		this.material3 = material1;
		this.material4 = material3;
		this.glToolkit = glToolkit;
		this.material2 = material2;
	}

	@OriginalMember(owner = "client!kr", name = "a", descriptor = "(I)Lclient!im;")
	@Override
	public GlCubeTexture method3232() {
		if (this.cubeTexture == null) {
			@Pc(17) unpack_Sub1 local17 = this.glToolkit.materialProvider;
			Class48_Sub1_Sub1.anIntArray314[2] = this.material4;
			Class48_Sub1_Sub1.anIntArray314[5] = this.material5;
			Class48_Sub1_Sub1.anIntArray314[0] = this.material3;
			Class48_Sub1_Sub1.anIntArray314[4] = this.material6;
			Class48_Sub1_Sub1.anIntArray314[1] = this.material2;
			Class48_Sub1_Sub1.anIntArray314[3] = this.material1;
			@Pc(49) boolean local49 = false;
			@Pc(51) int local51 = 0;
			for (@Pc(53) int local53 = 0; local53 < 6; local53++) {
				if (!local17.textureAvailable(Class48_Sub1_Sub1.anIntArray314[local53])) {
					return null;
				}
				@Pc(73) MaterialType local73 = local17.getMetrics(Class48_Sub1_Sub1.anIntArray314[local53]);
				@Pc(80) int local80 = local73.isSmall ? 64 : 128;
				if (local73.aByte92 > 0) {
					local49 = true;
				}
				if (local51 < local80) {
					local51 = local80;
				}
			}
			for (@Pc(105) int local105 = 0; local105 < 6; local105++) {
				Class215.anIntArrayArray57[local105] = local17.getPixels(false, Class48_Sub1_Sub1.anIntArray314[local105], 1.0F, local51, local51);
			}
			this.cubeTexture = new GlCubeTexture(this.glToolkit, 6407, local51, local49, Class215.anIntArrayArray57);
		}
		return this.cubeTexture;
	}

@OriginalMember(owner = "client!kr", name = "C", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_42 = new Class215(52, -1);
@OriginalMember(owner = "client!kr", name = "F", descriptor = "[[F")
	public static final float[][] aFloatArrayArray5 = new float[][] { { -0.333333F, -0.333333F, -0.333333F }, { 0.333333F, -0.333333F, -0.333333F }, { -0.333333F, 0.333333F, -0.333333F }, { 0.333333F, 0.333333F, -0.333333F }, { -0.333333F, -0.333333F, 0.333333F }, { 0.333333F, -0.333333F, 0.333333F }, { -0.333333F, 0.333333F, 0.333333F }, { 0.333333F, 0.333333F, 0.333333F } };
@OriginalMember(owner = "client!kr", name = "G", descriptor = "I")
	public static int anInt3980 = 0;
@OriginalMember(owner = "client!kr", name = "b", descriptor = "(I)V")
	public static void method3238() {
		@Pc(10) int local10 = TextureOpBrightness.aClass50_Sub1_1.method2858(Packet.anInt2973);
		if (local10 == 0) {
			Class10.aByteArrayArrayArray1 = null;
			BillboardType.method2924(0);
		} else if (local10 == 1) {
			Class50_Sub1.method2860((byte) 0);
			BillboardType.method2924(512);
			if (SeqTypeList.aByteArrayArrayArray2 != null) {
				Class70_Sub2.method5084();
			}
		} else {
			Class50_Sub1.method2860((byte) (Class79_Sub1.anInt6264 - 4 & 0xFF));
			BillboardType.method2924(2);
		}
		ReflectingTextureEffect.anInt5744 = Class4_Sub6_Sub25.anInt4963;
	}
}
