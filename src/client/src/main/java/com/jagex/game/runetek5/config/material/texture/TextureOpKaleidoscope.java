package com.jagex.game.runetek5.config.material.texture;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.Static211;
import com.jagex.game.runetek5.deob.Static411;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.config.material.texture.*;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub35;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub29;
import com.jagex.game.runetek5.config.enumtype.EnumTypeList;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.deob.WaterfallEffect;
import com.jagex.game.runetek5.Js5TextureSource;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.deob.Class78_Sub1;
@OriginalClass("client!gs")
public final class TextureOpKaleidoscope extends TextureOperation {

	@OriginalMember(owner = "client!gs", name = "<init>", descriptor = "()V")
	public TextureOpKaleidoscope() {
		super(1, false);
	}

	@OriginalMember(owner = "client!gs", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(19) int[] local19 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			for (@Pc(25) int local25 = 0; local25 < MidiPcmStream.anInt1616; local25++) {
				this.method2338(local25, arg0);
				@Pc(38) int[] local38 = this.getChildMonochromeOutput(Class4_Sub6_Sub29.anInt5794, 0);
				local19[local25] = local38[Static411.anInt6739];
			}
		}
		return local19;
	}

	@OriginalMember(owner = "client!gs", name = "a", descriptor = "(BI)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		@Pc(16) int[][] local16 = super.colorImageCache.get(arg0);
		if (super.colorImageCache.dirty) {
			@Pc(24) int[] local24 = local16[0];
			@Pc(28) int[] local28 = local16[1];
			@Pc(32) int[] local32 = local16[2];
			for (@Pc(34) int local34 = 0; local34 < MidiPcmStream.anInt1616; local34++) {
				this.method2338(local34, arg0);
				@Pc(47) int[][] local47 = this.getChildColorOutput(0, Class4_Sub6_Sub29.anInt5794);
				local24[local34] = local47[0][Static411.anInt6739];
				local28[local34] = local47[1][Static411.anInt6739];
				local32[local34] = local47[2][Static411.anInt6739];
			}
		}
		return local16;
	}

	@OriginalMember(owner = "client!gs", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			super.monochrome = arg1.method2490() == 1;
		}
	}

	@OriginalMember(owner = "client!gs", name = "a", descriptor = "(IIB)V")
	private void method2338(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		@Pc(9) int local9 = AbstractArbBufferGL.anIntArray504[arg0];
		@Pc(13) int local13 = ParticleEffector.anIntArray209[arg1];
		@Pc(28) float local28 = (float) Math.atan2((double) (local9 - 2048), (double) (local13 - 2048));
		if ((double) local28 >= -3.141592653589793D && (double) local28 <= -2.356194490192345D) {
			Static411.anInt6739 = arg0;
			Class4_Sub6_Sub29.anInt5794 = arg1;
		} else if ((double) local28 <= -1.5707963267948966D && (double) local28 >= -2.356194490192345D) {
			Class4_Sub6_Sub29.anInt5794 = arg0;
			Static411.anInt6739 = arg1;
		} else if ((double) local28 <= -0.7853981633974483D && (double) local28 >= -1.5707963267948966D) {
			Static411.anInt6739 = MidiPcmStream.anInt1616 - arg1;
			Class4_Sub6_Sub29.anInt5794 = arg0;
		} else if (local28 <= 0.0F && (double) local28 >= -0.7853981633974483D) {
			Class4_Sub6_Sub29.anInt5794 = EnumTypeList.anInt4939 - arg1;
			Static411.anInt6739 = arg0;
		} else if (local28 >= 0.0F && (double) local28 <= 0.7853981633974483D) {
			Class4_Sub6_Sub29.anInt5794 = EnumTypeList.anInt4939 - arg1;
			Static411.anInt6739 = MidiPcmStream.anInt1616 - arg0;
		} else if ((double) local28 >= 0.7853981633974483D && (double) local28 <= 1.5707963267948966D) {
			Static411.anInt6739 = MidiPcmStream.anInt1616 - arg1;
			Class4_Sub6_Sub29.anInt5794 = EnumTypeList.anInt4939 - arg0;
		} else if ((double) local28 >= 1.5707963267948966D && (double) local28 <= 2.356194490192345D) {
			Static411.anInt6739 = arg1;
			Class4_Sub6_Sub29.anInt5794 = EnumTypeList.anInt4939 - arg0;
		} else if ((double) local28 >= 2.356194490192345D && (double) local28 <= 3.141592653589793D) {
			Class4_Sub6_Sub29.anInt5794 = arg1;
			Static411.anInt6739 = MidiPcmStream.anInt1616 - arg0;
		}
		Static411.anInt6739 &= Class78_Sub1.anInt3883;
		Class4_Sub6_Sub29.anInt5794 &= Static211.anInt3929;
	}

@OriginalMember(owner = "client!gs", name = "G", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_120 = new Class22(24, 6);
@OriginalMember(owner = "client!gs", name = "I", descriptor = "Z")
	public static boolean aBoolean256 = false;
@OriginalMember(owner = "client!gs", name = "b", descriptor = "(II)V")
	public static void method2336(@OriginalArg(1) int arg0) {
		Class4_Sub6_Sub35.anInt6662 = arg0;
		TextureOpColorGradient.anInt1763 = -1;
		Js5TextureSource.anInt2835 = 3;
		WaterfallEffect.anInt5136 = 100;
	}
}
