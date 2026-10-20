package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.cache.node.impl.SpriteRenderNode;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.entity.ParticleSystem;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.entity.DynamicWall;
import com.jagex.game.runetek5.deob.Class58;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTexture;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationType;
import com.jagex.game.runetek5.deob.Class95_Sub3_Sub1;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
@OriginalClass("client!ne")
public final class Class4_Sub1_Sub8_Sub1 extends SpriteRenderNode {

	@OriginalMember(owner = "client!ne", name = "M", descriptor = "Ljava/lang/Object;")
	private final Object anObject26;

	@OriginalMember(owner = "client!ne", name = "<init>", descriptor = "(Ljava/lang/Object;I)V")
	public Class4_Sub1_Sub8_Sub1(@OriginalArg(0) Object arg0, @OriginalArg(1) int arg1) {
		super(arg1);
		this.anObject26 = arg0;
	}

	@OriginalMember(owner = "client!ne", name = "e", descriptor = "(B)Z")
	@Override
	public boolean isSoft() {
		return false;
	}

	@OriginalMember(owner = "client!ne", name = "d", descriptor = "(I)Ljava/lang/Object;")
	@Override
	public Object get() {
		return this.anObject26;
	}

@OriginalMember(owner = "client!ne", name = "G", descriptor = "I")
	public static int anInt4897;
@OriginalMember(owner = "client!ne", name = "E", descriptor = "I")
	public static int anInt4896 = -1;
@OriginalMember(owner = "client!ne", name = "F", descriptor = "[[I")
	public static final int[][] anIntArrayArray41 = new int[][] { { 0, 1, 2, 3 }, { 1, -1, -1, 0 }, { -1, 2, -1, 0 }, { -1, 0, -1, 2 }, { 0, 1, -1, 2 }, { 1, 2, -1, 0 }, { -1, 4, -1, 1 }, { -1, 3, 4, -1 }, { -1, 0, 2, -1 }, { -1, -1, 2, 0 }, { 0, 2, 5, 3 }, { 0, -1, 6, -1 }, { 0, 1, 2, 3 } };
@OriginalMember(owner = "client!ne", name = "N", descriptor = "[S")
	public static final short[] aShortArray161 = new short[] { 76, 8, 137, 4, 0, 1, 38, 2, 19 };
@OriginalMember(owner = "client!ne", name = "a", descriptor = "(IB)C")
	public static char method3771(@OriginalArg(1) byte arg0) {
		@Pc(7) int local7 = arg0 & 0xFF;
		if (local7 == 0) {
			throw new IllegalArgumentException("Non cp1252 character 0x" + Integer.toString(local7, 16) + " provided");
		}
		if (local7 >= 128 && local7 < 160) {
			@Pc(41) char local41 = StillWaterEffect.aCharArray7[local7 - 128];
			if (local41 == '\u0000') {
				local41 = '?';
			}
			local7 = local41;
		}
		return (char) local7;
	}
@OriginalMember(owner = "client!ne", name = "e", descriptor = "(I)V")
	public static void method3772() {
		for (@Pc(8) ParticleSystem local8 = (ParticleSystem) Static384.aClass84_8.method2312(); local8 != null; local8 = (ParticleSystem) Static384.aClass84_8.method2309()) {
			local8.method170();
		}
	}
@OriginalMember(owner = "client!ne", name = "a", descriptor = "(IBZIILclient!la;Lclient!ya;)V")
	public static void method3773(@OriginalArg(0) int arg0, @OriginalArg(2) boolean arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) AbstractFontRenderer arg4, @OriginalArg(6) com.jagex.game.runetek5.graphics.Toolkit arg5) {
		if (arg1) {
			GlVboElementsArrayBuffer.aClass57_23.render((GpuPointLight.anInt2881 - GlVboElementsArrayBuffer.aClass57_23.scaleWidth()) / 2, (EffectiveVertex.anInt5346 - GlVboElementsArrayBuffer.aClass57_23.scaleHeight()) / 2);
			Class58.aClass57_10.render((GpuPointLight.anInt2881 - Class58.aClass57_10.scaleWidth()) / 2, 18);
		}
		arg4.method5056(GpuPointLight.anInt2881 / 2, arg3, EffectiveVertex.anInt5346 / 2 - 26, -1, (TextureOpTexture.aClass121_1 == DynamicWall.aClass121_3 ? SpotAnimationType.aClass83_104 : Class95_Sub3_Sub1.aClass83_129).method2267(NativeBillboardFace.anInt3423));
		@Pc(64) int local64 = EffectiveVertex.anInt5346 / 2 - 18;
		arg5.outlineRect(GpuPointLight.anInt2881 / 2 - 152, local64, 304, 34, arg0, 0);
		arg5.outlineRect(GpuPointLight.anInt2881 / 2 - 151, local64 - -1, 302, 32, 0, 0);
		arg5.O(GpuPointLight.anInt2881 / 2 - 150, local64 + 2, GlFramebuffer.anInt4590 * 3, 30, arg2, 0);
		arg5.O(GpuPointLight.anInt2881 / 2 + GlFramebuffer.anInt4590 * 3 - 150, local64 + 2, 300 - GlFramebuffer.anInt4590 * 3, 30, 0, 0);
		arg4.method5056(GpuPointLight.anInt2881 / 2, arg3, EffectiveVertex.anInt5346 / 2 + 4, -1, Shadow.aString53);
	}
}
