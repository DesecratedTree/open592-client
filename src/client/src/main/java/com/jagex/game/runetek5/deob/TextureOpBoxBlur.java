package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.Class4_Sub18;
@OriginalClass("client!rh")
public final class TextureOpBoxBlur extends TextureOperation {

	@OriginalMember(owner = "client!rh", name = "<init>", descriptor = "()V")
	public TextureOpBoxBlur() {
		super(3, false);
	}

	@OriginalMember(owner = "client!rh", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			super.monochrome = arg1.method2490() == 1;
		}
	}

	@OriginalMember(owner = "client!rh", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(19) int[] local19 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			@Pc(29) int[] local29 = this.getChildMonochromeOutput(arg0, 0);
			@Pc(35) int[] local35 = this.getChildMonochromeOutput(arg0, 1);
			@Pc(41) int[] local41 = this.getChildMonochromeOutput(arg0, 2);
			for (@Pc(43) int local43 = 0; local43 < MidiPcmStream.anInt1616; local43++) {
				@Pc(49) int local49 = local41[local43];
				if (local49 == 4096) {
					local19[local43] = local29[local43];
				} else if (local49 == 0) {
					local19[local43] = local35[local43];
				} else {
					local19[local43] = local49 * local29[local43] + local35[local43] * (4096 - local49) >> 12;
				}
			}
		}
		return local19;
	}

	@OriginalMember(owner = "client!rh", name = "a", descriptor = "(BI)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		@Pc(16) int[][] local16 = super.colorImageCache.get(arg0);
		if (super.colorImageCache.dirty) {
			@Pc(26) int[] local26 = this.getChildMonochromeOutput(arg0, 2);
			@Pc(32) int[][] local32 = this.getChildColorOutput(0, arg0);
			@Pc(38) int[][] local38 = this.getChildColorOutput(1, arg0);
			@Pc(42) int[] local42 = local16[0];
			@Pc(46) int[] local46 = local16[1];
			@Pc(50) int[] local50 = local16[2];
			@Pc(54) int[] local54 = local32[0];
			@Pc(58) int[] local58 = local32[1];
			@Pc(62) int[] local62 = local32[2];
			@Pc(66) int[] local66 = local38[0];
			@Pc(70) int[] local70 = local38[1];
			@Pc(74) int[] local74 = local38[2];
			for (@Pc(76) int local76 = 0; local76 < MidiPcmStream.anInt1616; local76++) {
				@Pc(82) int local82 = local26[local76];
				if (local82 == 4096) {
					local42[local76] = local54[local76];
					local46[local76] = local58[local76];
					local50[local76] = local62[local76];
				} else if (local82 == 0) {
					local42[local76] = local66[local76];
					local46[local76] = local70[local76];
					local50[local76] = local74[local76];
				} else {
					@Pc(114) int local114 = 4096 - local82;
					local42[local76] = local114 * local66[local76] + local82 * local54[local76] >> 12;
					local46[local76] = local58[local76] * local82 + local114 * local70[local76] >> 12;
					local50[local76] = local114 * local74[local76] + local62[local76] * local82 >> 12;
				}
			}
		}
		return local16;
	}

@OriginalMember(owner = "client!rh", name = "K", descriptor = "[Lclient!l;")
	public static NativeSprite[] aClass57Array13;
@OriginalMember(owner = "client!rh", name = "D", descriptor = "I")
	public static final int anInt5893 = 1407;
@OriginalMember(owner = "client!rh", name = "E", descriptor = "[I")
	public static final int[] anIntArray412 = new int[1024];
@OriginalMember(owner = "client!rh", name = "H", descriptor = "[Z")
	public static final boolean[] aBooleanArray17 = new boolean[5];
@OriginalMember(owner = "client!rh", name = "a", descriptor = "(IIII)Z")
	public static boolean method4630(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		if (Class4_Sub18.method2030(arg0, arg1, arg2)) {
			@Pc(10) int local10 = arg1 << Static231.anInt4434;
			@Pc(14) int local14 = arg2 << Static231.anInt4434;
			return Class177.method4088(local10 + 1, Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1, arg2) + arg3, local14 + 1) && Class177.method4088(local10 + CacheableNode.anInt5935 - 1, Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1 + 1, arg2) + arg3, local14 + 1) && Class177.method4088(local10 + CacheableNode.anInt5935 - 1, Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1 + 1, arg2 + 1) + arg3, local14 + CacheableNode.anInt5935 - 1) && Class177.method4088(local10 + 1, Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1, arg2 + 1) + arg3, local14 + CacheableNode.anInt5935 - 1);
		} else {
			return false;
		}
	}
@OriginalMember(owner = "client!rh", name = "a", descriptor = "(ZIII)I")
	public static int method4632(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		@Pc(26) int local26 = (arg1 * (arg0 & 0xFF00) & 0xFF0000 | (arg0 & 0xFF00FF) * arg1 & 0xFF00FF00) >>> 8;
		@Pc(31) int local31 = 255 - arg1;
		return ((local31 * (arg2 & 0xFF00) & 0xFF0000 | local31 * (arg2 & 0xFF00FF) & 0xFF00FF00) >>> 8) + local26;
	}
}
