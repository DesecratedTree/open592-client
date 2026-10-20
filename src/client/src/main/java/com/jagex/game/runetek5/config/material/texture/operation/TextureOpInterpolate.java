package com.jagex.game.runetek5.config.material.texture.operation;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.config.material.texture.operation.*;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
@OriginalClass("client!hm")
public final class TextureOpInterpolate extends TextureOperation {

	@OriginalMember(owner = "client!hm", name = "C", descriptor = "I")
	private int anInt3050 = 0;

	@OriginalMember(owner = "client!hm", name = "E", descriptor = "I")
	private int anInt3051 = 4096;

	@OriginalMember(owner = "client!hm", name = "<init>", descriptor = "()V")
	public TextureOpInterpolate() {
		super(1, false);
	}

	@OriginalMember(owner = "client!hm", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[] local11 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			@Pc(29) int[] local29 = this.getChildMonochromeOutput(arg0, 0);
			for (@Pc(31) int local31 = 0; local31 < MidiPcmStream.anInt1616; local31++) {
				@Pc(37) int local37 = local29[local31];
				if (this.anInt3050 > local37) {
					local11[local31] = this.anInt3050;
				} else if (this.anInt3051 < local37) {
					local11[local31] = this.anInt3051;
				} else {
					local11[local31] = local37;
				}
			}
		}
		return local11;
	}

	@OriginalMember(owner = "client!hm", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.anInt3050 = arg1.method2536();
		} else if (arg0 == 1) {
			this.anInt3051 = arg1.method2536();
		} else if (arg0 == 2) {
			super.monochrome = arg1.method2490() == 1;
		}
	}

	@OriginalMember(owner = "client!hm", name = "a", descriptor = "(BI)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[][] local11 = super.colorImageCache.get(arg0);
		if (super.colorImageCache.dirty) {
			@Pc(21) int[][] local21 = this.getChildColorOutput(0, arg0);
			@Pc(25) int[] local25 = local21[0];
			@Pc(29) int[] local29 = local21[1];
			@Pc(33) int[] local33 = local21[2];
			@Pc(37) int[] local37 = local11[0];
			@Pc(41) int[] local41 = local11[1];
			@Pc(45) int[] local45 = local11[2];
			for (@Pc(47) int local47 = 0; local47 < MidiPcmStream.anInt1616; local47++) {
				@Pc(53) int local53 = local25[local47];
				@Pc(57) int local57 = local29[local47];
				@Pc(61) int local61 = local33[local47];
				if (this.anInt3050 > local53) {
					local37[local47] = this.anInt3050;
				} else if (local53 > this.anInt3051) {
					local37[local47] = this.anInt3051;
				} else {
					local37[local47] = local53;
				}
				if (local57 < this.anInt3050) {
					local41[local47] = this.anInt3050;
				} else if (this.anInt3051 < local57) {
					local41[local47] = this.anInt3051;
				} else {
					local41[local47] = local57;
				}
				if (local61 < this.anInt3050) {
					local45[local47] = this.anInt3050;
				} else if (this.anInt3051 < local61) {
					local45[local47] = this.anInt3051;
				} else {
					local45[local47] = local61;
				}
			}
		}
		return local11;
	}

@OriginalMember(owner = "client!hm", name = "D", descriptor = "[Lclient!l;")
	public static NativeSprite[] aClass57Array8;
@OriginalMember(owner = "client!hm", name = "G", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_38;
@OriginalMember(owner = "client!hm", name = "I", descriptor = "I")
	public static int anInt3054;
}
