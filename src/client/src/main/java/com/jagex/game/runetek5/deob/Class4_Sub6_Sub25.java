package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class4_Sub9;
import com.jagex.game.runetek5.config.material.texture.ColorImageCache;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.cache.node.lru.ClientScript;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class50;
import com.jagex.game.runetek5.deob.Class6_Sub2;
import com.jagex.game.runetek5.Js5TextureSource;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTile;
import com.jagex.game.runetek5.deob.Class48_Sub1_Sub1;
import com.jagex.game.runetek5.deob.Class267;
import com.jagex.game.runetek5.entity.pathingentity.EasedAngle;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Class189;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.EnvironmentContext;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpMonochromeEdgeDetector;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
@OriginalClass("client!nh")
public final class Class4_Sub6_Sub25 extends TextureOperation {

	@OriginalMember(owner = "client!nh", name = "<init>", descriptor = "()V")
	public Class4_Sub6_Sub25() {
		super(1, false);
	}

	@OriginalMember(owner = "client!nh", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			super.monochrome = arg1.method2490() == 1;
		}
	}

	@OriginalMember(owner = "client!nh", name = "a", descriptor = "(BI)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[][] local11 = super.colorImageCache.get(arg0);
		if (super.colorImageCache.dirty) {
			@Pc(26) int[][] local26 = this.getChildColorOutput(0, arg0);
			@Pc(30) int[] local30 = local26[0];
			@Pc(34) int[] local34 = local26[1];
			@Pc(38) int[] local38 = local26[2];
			@Pc(42) int[] local42 = local11[0];
			@Pc(46) int[] local46 = local11[1];
			@Pc(50) int[] local50 = local11[2];
			for (@Pc(52) int local52 = 0; local52 < MidiPcmStream.anInt1616; local52++) {
				local42[local52] = 4096 - local30[local52];
				local46[local52] = 4096 - local34[local52];
				local50[local52] = 4096 - local38[local52];
			}
		}
		return local11;
	}

	@OriginalMember(owner = "client!nh", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(19) int[] local19 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			@Pc(29) int[] local29 = this.getChildMonochromeOutput(arg0, 0);
			for (@Pc(31) int local31 = 0; local31 < MidiPcmStream.anInt1616; local31++) {
				local19[local31] = 4096 - local29[local31];
			}
		}
		return local19;
	}

@OriginalMember(owner = "client!nh", name = "F", descriptor = "I")
	public static int anInt4958;
@OriginalMember(owner = "client!nh", name = "M", descriptor = "I")
	public static int anInt4963;
@OriginalMember(owner = "client!nh", name = "D", descriptor = "[Z")
	public static final boolean[] aBooleanArray15 = new boolean[100];
@OriginalMember(owner = "client!nh", name = "G", descriptor = "Z")
	public static boolean aBoolean455 = false;
@OriginalMember(owner = "client!nh", name = "f", descriptor = "(I)V")
	public static void method3806() {
		if (!TextureOpMonochromeEdgeDetector.method5406()) {
			return;
		}
		if (Static102.aStringArray8 == null) {
			Class6_Sub2.method198();
		}
		Class50.aBoolean292 = true;
		Class48_Sub1_Sub1.anInt4882 = 0;
		try {
			Class4_Sub6_Sub31.aClipboard1 = ClientScript.aClient1.getToolkit().getSystemClipboard();
		} catch (@Pc(16) Exception local16) {
		}
	}
@OriginalMember(owner = "client!nh", name = "a", descriptor = "(BII)I")
	public static int method3807(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		@Pc(9) int local9 = arg0 * 57 + arg1;
		@Pc(20) int local20 = local9 << 13 ^ local9;
		@Pc(34) int local34 = (local20 * local20 * 15731 + 789221) * local20 + 1376312589 & Integer.MAX_VALUE;
		return local34 >> 19 & 0xFF;
	}
@OriginalMember(owner = "client!nh", name = "a", descriptor = "(ILclient!ta;)V")
	public static void method3808(@OriginalArg(0) int arg0, @OriginalArg(1) Class65 arg1) {
		Class16_Sub1_Sub6.aClass65Array4[arg0] = arg1;
	}
@OriginalMember(owner = "client!nh", name = "b", descriptor = "(BI)V")
	public static void method3809(@OriginalArg(1) int arg0) {
		if (arg0 == Js5TextureSource.anInt2845) {
			return;
		}
		GLBillowTextureData.anInt5666 = Static283.anInt5187 = Class50_Sub1.anIntArray226[arg0];
		EnvironmentContext.method4607();
		Class189.anIntArrayArrayArray12 = new int[4][GLBillowTextureData.anInt5666 >> 3][Static283.anInt5187 >> 3];
		EasedAngle.anIntArrayArray8 = new int[GLBillowTextureData.anInt5666][Static283.anInt5187];
		Class4_Sub9.anIntArrayArray7 = new int[GLBillowTextureData.anInt5666][Static283.anInt5187];
		for (@Pc(40) int local40 = 0; local40 < 4; local40++) {
			PlayerVarDomain.aClass213Array1[local40] = Class267.method5622(Static283.anInt5187, GLBillowTextureData.anInt5666);
		}
		Class10.aByteArrayArrayArray1 = new byte[4][GLBillowTextureData.anInt5666][Static283.anInt5187];
		TextureOpTile.method4977(Static283.anInt5187, GLBillowTextureData.anInt5666);
		ColorImageCache.method3682(Static283.anInt5187 >> 3, MonoStereoData.aClass19_16, GLBillowTextureData.anInt5666 >> 3);
		Js5TextureSource.anInt2845 = arg0;
	}
}
