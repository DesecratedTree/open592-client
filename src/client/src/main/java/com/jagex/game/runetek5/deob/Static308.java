package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub35;
import com.jagex.game.runetek5.deob.TextureCombinerOp;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.deob.OutputStream_Sub1;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpMonochromeEdgeDetector;
import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.Js5TextureSource;
import com.jagex.game.runetek5.deob.WaterfallEffect;
import com.jagex.game.runetek5.deob.DequeIterator;
import com.jagex.game.runetek5.graphics.texture.OpenGl2DImageTexture;
public final class Static308 {

	@OriginalMember(owner = "client!pm", name = "c", descriptor = "Z")
	public static boolean aBoolean485;

	@OriginalMember(owner = "client!pm", name = "g", descriptor = "[I")
	public static int[] anIntArray372;

	@OriginalMember(owner = "client!pm", name = "a", descriptor = "I")
	public static int anInt5410 = 0;

	@OriginalMember(owner = "client!pm", name = "b", descriptor = "Lclient!em;")
	public static final DequeIterator aClass63_1 = new DequeIterator();

	@OriginalMember(owner = "client!pm", name = "d", descriptor = "Z")
	public static boolean aBoolean486 = false;

	@OriginalMember(owner = "client!pm", name = "h", descriptor = "I")
	public static int anInt5413 = 0;

	@OriginalMember(owner = "client!pm", name = "i", descriptor = "Z")
	public static boolean aBoolean487 = false;

	@OriginalMember(owner = "client!pm", name = "a", descriptor = "(IIB)V")
	public static void method4154(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		if (Static148.mapZoom < Static148.requestedMapZoom) {
			Static148.mapZoom = (float) ((double) Static148.mapZoom + (double) Static148.mapZoom / 30.0D);
			if (Static148.requestedMapZoom < Static148.mapZoom) {
				Static148.mapZoom = Static148.requestedMapZoom;
			}
			JavaMatrix.method5160();
			Static148.tileSize = (int) Static148.mapZoom >> 1;
			Static148.tileShapePatterns = Static449.method5655(Static148.tileSize);
		} else if (Static148.mapZoom > Static148.requestedMapZoom) {
			Static148.mapZoom = (float) ((double) Static148.mapZoom - (double) Static148.mapZoom / 30.0D);
			if (Static148.requestedMapZoom > Static148.mapZoom) {
				Static148.mapZoom = Static148.requestedMapZoom;
			}
			JavaMatrix.method5160();
			Static148.tileSize = (int) Static148.mapZoom >> 1;
			Static148.tileShapePatterns = Static449.method5655(Static148.tileSize);
		}
		if (OutputStream_Sub1.anInt2674 != -1 && Class9.anInt6920 != -1) {
			@Pc(85) int local85 = OutputStream_Sub1.anInt2674 - OpenGl2DImageTexture.anInt5471;
			if (local85 < 2 || local85 > 2) {
				local85 /= 8;
			}
			@Pc(104) int local104 = Class9.anInt6920 - TextureCombinerOp.anInt5156;
			OpenGl2DImageTexture.anInt5471 += local85;
			if (local104 < 2 || local104 > 2) {
				local104 /= 8;
			}
			TextureCombinerOp.anInt5156 += local104;
			if (local85 == 0 && local104 == 0) {
				OutputStream_Sub1.anInt2674 = -1;
				Class9.anInt6920 = -1;
			}
			JavaMatrix.method5160();
		}
		if (Js5TextureSource.anInt2835 > 0) {
			WaterfallEffect.anInt5136--;
			if (WaterfallEffect.anInt5136 == 0) {
				Js5TextureSource.anInt2835--;
				WaterfallEffect.anInt5136 = 100;
			}
		} else {
			Class4_Sub6_Sub35.anInt6662 = -1;
			TextureOpColorGradient.anInt1763 = -1;
		}
		if (!TextureOpMonochromeEdgeDetector.aBoolean653 || EnvironmentSampler.aClass183_29 == null) {
			return;
		}
		for (@Pc(177) Class4_Sub14 local177 = (Class4_Sub14) EnvironmentSampler.aClass183_29.method4140(); local177 != null; local177 = (Class4_Sub14) EnvironmentSampler.aClass183_29.method4144()) {
			@Pc(186) MECType local186 = Static148.MECTYPE_LIST.method4124(local177.aClass4_Sub3_1.anInt234);
			if (local177.method1370(arg1, arg0)) {
				if (local186.minimenuActions != null) {
					if (local186.minimenuActions[4] != null) {
						Static380.method5017(1006, false, (long) local177.aClass4_Sub3_1.anInt234, local186.anInt4919, local186.aString48, true, local186.minimenuActions[4], 0, -1, -1);
					}
					if (local186.minimenuActions[3] != null) {
						Static380.method5017(1001, false, (long) local177.aClass4_Sub3_1.anInt234, local186.anInt4919, local186.aString48, true, local186.minimenuActions[3], 0, -1, -1);
					}
					if (local186.minimenuActions[2] != null) {
						Static380.method5017(1008, false, (long) local177.aClass4_Sub3_1.anInt234, local186.anInt4919, local186.aString48, true, local186.minimenuActions[2], 0, -1, -1);
					}
					if (local186.minimenuActions[1] != null) {
						Static380.method5017(1012, false, (long) local177.aClass4_Sub3_1.anInt234, local186.anInt4919, local186.aString48, true, local186.minimenuActions[1], 0, -1, -1);
					}
					if (local186.minimenuActions[0] != null) {
						Static380.method5017(1004, false, (long) local177.aClass4_Sub3_1.anInt234, local186.anInt4919, local186.aString48, true, local186.minimenuActions[0], 0, -1, -1);
					}
				}
				if (!local177.aClass4_Sub3_1.aBoolean27) {
					local177.aClass4_Sub3_1.aBoolean27 = true;
					Static271.method3899(Static312.aClass186_13, local177.aClass4_Sub3_1.anInt234, local186.anInt4919);
				}
				if (local177.aClass4_Sub3_1.aBoolean27) {
					Static271.method3899(DequeIterator.aClass186_7, local177.aClass4_Sub3_1.anInt234, local186.anInt4919);
				}
			} else if (local177.aClass4_Sub3_1.aBoolean27) {
				local177.aClass4_Sub3_1.aBoolean27 = false;
				Static271.method3899(AbstractFontRenderer.aClass186_16, local177.aClass4_Sub3_1.anInt234, local186.anInt4919);
			}
		}
	}

	@OriginalMember(owner = "client!pm", name = "a", descriptor = "(ILclient!kf;ZLclient!kf;IZB)I")
	public static int method4155(@OriginalArg(0) int arg0, @OriginalArg(1) Class78_Sub1 arg1, @OriginalArg(2) boolean arg2, @OriginalArg(3) Class78_Sub1 arg3, @OriginalArg(4) int arg4, @OriginalArg(5) boolean arg5) {
		@Pc(18) int local18 = Static283.method3963(arg5, arg0, arg1, arg3);
		if (local18 != 0) {
			return arg5 ? -local18 : local18;
		} else if (arg4 == -1) {
			return 0;
		} else {
			@Pc(42) int local42 = Static283.method3963(arg2, arg4, arg1, arg3);
			return arg2 ? -local42 : local42;
		}
	}
}
