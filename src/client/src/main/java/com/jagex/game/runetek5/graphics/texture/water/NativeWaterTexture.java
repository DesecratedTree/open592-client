package com.jagex.game.runetek5.graphics.texture.water;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.GlVolumeTexture;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static366;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.graphics.shader.Texture3DShaderGL;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.graphics.texture.water.*;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpPerlinNoise;
import com.jagex.game.runetek5.config.cursor.CursorType;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
@OriginalClass("client!m")
public final class NativeWaterTexture {

	@OriginalMember(owner = "client!m", name = "a", descriptor = "Lclient!cf;")
	public GlVolumeTexture aClass6_Sub3_1 = null;

	@OriginalMember(owner = "client!m", name = "f", descriptor = "Lclient!cf;")
	public GlVolumeTexture aClass6_Sub3_2 = null;

	@OriginalMember(owner = "client!m", name = "e", descriptor = "[Lclient!af;")
	public OpenGl2DTexture[] waterTextureSamplers = null;

	@OriginalMember(owner = "client!m", name = "g", descriptor = "Lclient!cf;")
	public GlVolumeTexture aClass6_Sub3_3 = null;

	@OriginalMember(owner = "client!m", name = "d", descriptor = "[Lclient!af;")
	public OpenGl2DTexture[] textureSamplers = null;

	@OriginalMember(owner = "client!m", name = "b", descriptor = "Z")
	public final boolean hasDepth;

	@OriginalMember(owner = "client!m", name = "<init>", descriptor = "(Lclient!fd;)V")
	public NativeWaterTexture(@OriginalArg(0) GlToolkit nativeToolkit) {
		this.hasDepth = nativeToolkit.aBoolean211;
		Static267.method3829(nativeToolkit);
		if (this.hasDepth) {
			@Pc(112) byte[] local112 = Static366.method4930(false, Class16.anObject33);
			this.aClass6_Sub3_3 = new GlVolumeTexture(nativeToolkit, 6410, 128, 128, 16, local112, 6410);
			@Pc(129) byte[] local129 = Static366.method4930(false, CursorType.anObject13);
			this.aClass6_Sub3_1 = new GlVolumeTexture(nativeToolkit, 6410, 128, 128, 16, local129, 6410);
			@Pc(144) Texture3DShaderGL local144 = nativeToolkit.texture3dShader;
			if (local144.createShader()) {
				local112 = Static366.method4930(false, Class4_Sub6_Sub15.anObject19);
				this.aClass6_Sub3_2 = new GlVolumeTexture(nativeToolkit, 6408, 128, 128, 16);
				@Pc(174) GlVolumeTexture local174 = new GlVolumeTexture(nativeToolkit, 6409, 128, 128, 16, local112, 6409);
				if (local144.method1250(2.0F, local174, this.aClass6_Sub3_2)) {
					this.aClass6_Sub3_2.method4189();
				} else {
					this.aClass6_Sub3_2.method4183();
					this.aClass6_Sub3_2 = null;
				}
				local174.method4183();
			}
		} else {
			this.waterTextureSamplers = new OpenGl2DTexture[16];
			for (@Pc(34) int local34 = 0; local34 < 16; local34++) {
				@Pc(46) byte[] local46 = TextureOpPerlinNoise.method2285(Class16.anObject33, local34 * 128 * 256);
				this.waterTextureSamplers[local34] = new OpenGl2DTexture(nativeToolkit, 3553, 6410, 128, 128, true, local46, 6410, false);
			}
			this.textureSamplers = new OpenGl2DTexture[16];
			for (@Pc(74) int local74 = 0; local74 < 16; local74++) {
				@Pc(84) byte[] local84 = TextureOpPerlinNoise.method2285(CursorType.anObject13, local74 * 32768);
				this.textureSamplers[local74] = new OpenGl2DTexture(nativeToolkit, 3553, 6410, 128, 128, true, local84, 6410, false);
			}
		}
	}

@OriginalMember(owner = "client!m", name = "i", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_49;
@OriginalMember(owner = "client!m", name = "c", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_200 = new Class22(97, -2);
@OriginalMember(owner = "client!m", name = "j", descriptor = "I")
	public static int anInt4491 = 0;
@OriginalMember(owner = "client!m", name = "a", descriptor = "(II)I")
	public static int method3528(@OriginalArg(1) int arg0) {
		return arg0 >>> 8;
	}
}
