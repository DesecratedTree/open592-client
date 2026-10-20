package com.jagex.game.runetek5.deob;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.graphics.texture.water.NativeWaterTexture;
import com.jagex.game.runetek5.toolkit.opengl.GlRenderList;

import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.sound.jagtheora.PcmStreamMixer;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.toolkit.opengl.EffectManagerGL;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.config.sequence.PathingEntitySequence;
import com.jagex.game.runetek5.deob.TextureCombinerOp;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpMonochromeEdgeDetector;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpMandelbrot;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.GameCanvas;
import com.jagex.game.runetek5.deob.Resampler;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
import com.jagex.game.runetek5.deob.Class208;
@OriginalClass("client!dm")
public final class Class9_Sub3 extends Class9 {

	@OriginalMember(owner = "client!dm", name = "t", descriptor = "Lclient!ct;")
	private GlRenderList aClass43_1;

	@OriginalMember(owner = "client!dm", name = "i", descriptor = "Lclient!m;")
	private final NativeWaterTexture aClass144_3;

	@OriginalMember(owner = "client!dm", name = "s", descriptor = "Lclient!ar;")
	private final Class6_Sub2 aClass6_Sub2_1;

	@OriginalMember(owner = "client!dm", name = "<init>", descriptor = "(Lclient!fd;Lclient!m;)V")
	public Class9_Sub3(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) NativeWaterTexture arg1) {
		super(arg0);
		this.aClass144_3 = arg1;
		this.method1351();
		this.aClass6_Sub2_1 = new Class6_Sub2(super.aClass19_Sub2_41, 6406, 2, new byte[] { 0, -1 }, 6406);
		this.aClass6_Sub2_1.method199();
	}

	@OriginalMember(owner = "client!dm", name = "b", descriptor = "(I)V")
	private void method1351() {
		this.aClass43_1 = new GlRenderList(super.aClass19_Sub2_41, 2);
		this.aClass43_1.method990(0);
		super.aClass19_Sub2_41.method1936(1);
		super.aClass19_Sub2_41.method1912(7681, 260);
		super.aClass19_Sub2_41.method1945(0, 768, 34168);
		OpenGL.glTexGeni(8192, 9472, 9216);
		OpenGL.glEnable(3168);
		super.aClass19_Sub2_41.method1936(0);
		OpenGL.glTexEnvf(8960, 34163, 2.0F);
		if (this.aClass144_3.hasDepth) {
			OpenGL.glTexGeni(8194, 9472, 9217);
			OpenGL.glTexGeni(8195, 9472, 9217);
			OpenGL.glTexGenfv(8195, 9473, new float[] { 0.0F, 0.0F, 0.0F, 1.0F }, 0);
			OpenGL.glEnable(3170);
			OpenGL.glEnable(3171);
		}
		this.aClass43_1.method991();
		this.aClass43_1.method990(1);
		super.aClass19_Sub2_41.method1936(1);
		super.aClass19_Sub2_41.method1912(8448, 8448);
		super.aClass19_Sub2_41.method1945(0, 768, 5890);
		OpenGL.glDisable(3168);
		super.aClass19_Sub2_41.method1936(0);
		OpenGL.glTexEnvf(8960, 34163, 1.0F);
		if (this.aClass144_3.hasDepth) {
			OpenGL.glDisable(3170);
			OpenGL.glDisable(3171);
		}
		this.aClass43_1.method991();
	}

	@OriginalMember(owner = "client!dm", name = "a", descriptor = "(ZB)V")
	@Override
	public void applyTextureCombine(@OriginalArg(0) boolean arg0) {
		super.aClass19_Sub2_41.method1912(260, 8448);
	}

	@OriginalMember(owner = "client!dm", name = "a", descriptor = "(Z)Z")
	@Override
	public boolean isSupported() {
		return true;
	}

	@OriginalMember(owner = "client!dm", name = "a", descriptor = "(IZ)V")
	@Override
	public void enable(@OriginalArg(1) boolean arg0) {
		if (super.aClass19_Sub2_41.anInt2315 > 0) {
			@Pc(13) float local13 = -0.5F / (float) super.aClass19_Sub2_41.anInt2315;
			super.aClass19_Sub2_41.method1936(1);
			TextureOpMonochromeEdgeDetector.aFloatArray32[1] = 0.0F;
			TextureOpMonochromeEdgeDetector.aFloatArray32[0] = 0.0F;
			TextureOpMonochromeEdgeDetector.aFloatArray32[2] = local13;
			TextureOpMonochromeEdgeDetector.aFloatArray32[3] = super.aClass19_Sub2_41.aFloat59 * local13 + 0.25F;
			OpenGL.glPushMatrix();
			OpenGL.glLoadIdentity();
			OpenGL.glTexGenfv(8192, 9474, TextureOpMonochromeEdgeDetector.aFloatArray32, 0);
			OpenGL.glPopMatrix();
			super.aClass19_Sub2_41.method1943(0.5F, (float) super.aClass19_Sub2_41.anInt2315);
			super.aClass19_Sub2_41.setActiveTexture(this.aClass6_Sub2_1);
			super.aClass19_Sub2_41.method1936(0);
		}
		this.aClass43_1.method989('\u0000');
		OpenGL.glMatrixMode(5890);
		OpenGL.glPushMatrix();
		OpenGL.glScalef(0.25F, 0.25F, 1.0F);
		OpenGL.glMatrixMode(5888);
	}

	@OriginalMember(owner = "client!dm", name = "a", descriptor = "(ILclient!kj;Z)V")
	@Override
	public void method5376(@OriginalArg(0) int arg0, @OriginalArg(1) Class6 arg1) {
	}

	@OriginalMember(owner = "client!dm", name = "a", descriptor = "(III)V")
	@Override
	public void setEffectParams(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		if ((arg0 & 0x1) == 1) {
			if (this.aClass144_3.hasDepth) {
				super.aClass19_Sub2_41.setActiveTexture(this.aClass144_3.aClass6_Sub3_3);
				TextureOpMonochromeEdgeDetector.aFloatArray32[3] = (float) (super.aClass19_Sub2_41.anInt2274 % 4000) / 4000.0F;
				TextureOpMonochromeEdgeDetector.aFloatArray32[1] = 0.0F;
				TextureOpMonochromeEdgeDetector.aFloatArray32[2] = 0.0F;
				TextureOpMonochromeEdgeDetector.aFloatArray32[0] = 0.0F;
				OpenGL.glTexGenfv(8194, 9473, TextureOpMonochromeEdgeDetector.aFloatArray32, 0);
			} else {
				@Pc(24) int local24 = super.aClass19_Sub2_41.anInt2274 % 4000 * 16 / 4000;
				super.aClass19_Sub2_41.setActiveTexture(this.aClass144_3.waterTextureSamplers[local24]);
			}
		} else if (this.aClass144_3.hasDepth) {
			super.aClass19_Sub2_41.setActiveTexture(this.aClass144_3.aClass6_Sub3_3);
			TextureOpMonochromeEdgeDetector.aFloatArray32[0] = 0.0F;
			TextureOpMonochromeEdgeDetector.aFloatArray32[3] = 0.0F;
			TextureOpMonochromeEdgeDetector.aFloatArray32[2] = 0.0F;
			TextureOpMonochromeEdgeDetector.aFloatArray32[1] = 0.0F;
			OpenGL.glTexGenfv(8194, 9473, TextureOpMonochromeEdgeDetector.aFloatArray32, 0);
		} else {
			super.aClass19_Sub2_41.setActiveTexture(this.aClass144_3.waterTextureSamplers[0]);
		}
	}

	@OriginalMember(owner = "client!dm", name = "a", descriptor = "(I)V")
	@Override
	public void disable() {
		this.aClass43_1.method989('\u0001');
		if (super.aClass19_Sub2_41.anInt2315 > 0) {
			super.aClass19_Sub2_41.method1936(1);
			super.aClass19_Sub2_41.setActiveTexture(null);
			super.aClass19_Sub2_41.method1943(1.0F, 0.0F);
			super.aClass19_Sub2_41.method1936(0);
		}
		super.aClass19_Sub2_41.method1912(8448, 8448);
		OpenGL.glMatrixMode(5890);
		OpenGL.glPopMatrix();
		OpenGL.glMatrixMode(5888);
	}

@OriginalMember(owner = "client!dm", name = "u", descriptor = "[I")
	public static int[] anIntArray115;
@OriginalMember(owner = "client!dm", name = "k", descriptor = "D")
	public static double aDouble1 = -1.0D;
@OriginalMember(owner = "client!dm", name = "o", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_71 = new Class22(77, -2);
@OriginalMember(owner = "client!dm", name = "A", descriptor = "[B")
	public static final byte[] aByteArray19 = new byte[] { 0, 4, 4, 8, 0, 0, 8, 0, 0 };
@OriginalMember(owner = "client!dm", name = "B", descriptor = "[I")
	public static final int[] anIntArray116 = new int[] { 1, 4 };
@OriginalMember(owner = "client!dm", name = "a", descriptor = "(ILclient!uu;I)I")
	public static int method1350(@OriginalArg(0) int arg0, @OriginalArg(1) com.jagex.game.runetek5.config.rsinterface.Component arg1) {
		if (!client.method900(arg1).method3905(arg0) && arg1.anObjectArray15 == null) {
			return -1;
		} else if (arg1.opCursors == null || arg1.opCursors.length <= arg0) {
			return -1;
		} else {
			return arg1.opCursors[arg0];
		}
	}
@OriginalMember(owner = "client!dm", name = "b", descriptor = "(III)Z")
	public static boolean method1353(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		return (arg1 & 0x220) == 544 | (arg1 & 0x18) != 0;
	}
@OriginalMember(owner = "client!dm", name = "b", descriptor = "(B)V")
	public static void method1354() {
		MaterialType.method5385(TextureOpBrightness.aClass50_Sub1_1.aBoolean291);
		Static363.aClass4_Sub15_Sub1_3 = new MidiPcmStream();
		Static363.aClass4_Sub15_Sub1_3.method1453();
		Class4_Sub43.aClass49_2 = Static380.method5016(0, Class126.aClass66_4, 22050, Class164.aCanvas5);
		Class4_Sub43.aClass49_2.method2070(Static363.aClass4_Sub15_Sub1_3);
		Resampler.method5554(Class4_Sub6_Sub4.aClass76_19, Static363.aClass4_Sub15_Sub1_3, Static312.aClass76_66, Class208.aClass76_79);
		TextureOpMandelbrot.aClass49_1 = Static380.method5016(1, Class126.aClass66_4, 2048, Class164.aCanvas5);
		GameCanvas.aClass4_Sub15_Sub2_2 = new PcmStreamMixer();
		TextureOpMandelbrot.aClass49_1.method2070(GameCanvas.aClass4_Sub15_Sub2_2);
		PathingEntitySequence.keyRecorder = new Resampler(22050, MECType.anInt4928);
		EffectManagerGL.anInt6866 = TextureCombinerOp.aClass76_54.method2099("scape main");
	}
@OriginalMember(owner = "client!dm", name = "a", descriptor = "(IIIIII)V")
	public static void method1355(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4) {
		@Pc(7) int local7 = 0;
		@Pc(9) int local9 = arg3;
		@Pc(13) int local13 = arg1 * arg1;
		@Pc(17) int local17 = arg3 * arg3;
		@Pc(21) int local21 = local17 << 1;
		@Pc(25) int local25 = local13 << 1;
		@Pc(29) int local29 = arg3 << 1;
		@Pc(39) int local39 = local13 * (1 - local29) + local21;
		@Pc(48) int local48 = local17 - (local29 - 1) * local25;
		@Pc(52) int local52 = local13 << 2;
		@Pc(56) int local56 = local17 << 2;
		@Pc(64) int local64 = local21 * 3;
		@Pc(72) int local72 = local25 * ((arg3 << 1) - 3);
		@Pc(78) int local78 = local56;
		BasType.method3640(arg4 - arg1, arg2, arg4 + arg1, Class4_Sub6_Sub7.anIntArrayArray21[arg0]);
		@Pc(98) int local98 = local52 * (arg3 - 1);
		while (local9 > 0) {
			if (local39 < 0) {
				while (local39 < 0) {
					local39 += local64;
					local48 += local78;
					local64 += local56;
					local78 += local56;
					local7++;
				}
			}
			if (local48 < 0) {
				local39 += local64;
				local48 += local78;
				local78 += local56;
				local64 += local56;
				local7++;
			}
			local39 += -local98;
			local48 += -local72;
			local9--;
			local98 -= local52;
			local72 -= local52;
			@Pc(174) int local174 = arg0 - local9;
			@Pc(178) int local178 = local9 + arg0;
			@Pc(182) int local182 = local7 + arg4;
			@Pc(187) int local187 = arg4 - local7;
			BasType.method3640(local187, arg2, local182, Class4_Sub6_Sub7.anIntArrayArray21[local174]);
			BasType.method3640(local187, arg2, local182, Class4_Sub6_Sub7.anIntArrayArray21[local178]);
		}
	}
}
