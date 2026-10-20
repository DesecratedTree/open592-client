package com.jagex.game.runetek5.config.material.texture.operation;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class83;

import com.jagex.game.runetek5.config.material.texture.operation.*;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.config.sequence.SeqTypeList;
import com.jagex.game.runetek5.config.enumtype.EnumTypeList;
@OriginalClass("client!ss")
public final class TextureOpTile extends TextureOperation {

	@OriginalMember(owner = "client!ss", name = "D", descriptor = "I")
	private int tileCountY = 4;

	@OriginalMember(owner = "client!ss", name = "H", descriptor = "I")
	private int tileCountX = 4;

	@OriginalMember(owner = "client!ss", name = "<init>", descriptor = "()V")
	public TextureOpTile() {
		super(1, false);
	}

	@OriginalMember(owner = "client!ss", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.tileCountX = arg1.method2490();
		} else if (arg0 == 1) {
			this.tileCountY = arg1.method2490();
		}
	}

	@OriginalMember(owner = "client!ss", name = "a", descriptor = "(BI)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		@Pc(17) int[][] local17 = super.colorImageCache.get(arg0);
		if (super.colorImageCache.dirty) {
			@Pc(26) int local26 = MidiPcmStream.anInt1616 / this.tileCountX;
			@Pc(31) int local31 = EnumTypeList.anInt4939 / this.tileCountY;
			@Pc(50) int[][] local50;
			if (local31 > 0) {
				@Pc(40) int local40 = arg0 % local31;
				local50 = this.getChildColorOutput(0, local40 * EnumTypeList.anInt4939 / local31);
			} else {
				local50 = this.getChildColorOutput(0, 0);
			}
			@Pc(62) int[] local62 = local50[0];
			@Pc(66) int[] local66 = local50[1];
			@Pc(70) int[] local70 = local50[2];
			@Pc(74) int[] local74 = local17[0];
			@Pc(78) int[] local78 = local17[1];
			@Pc(82) int[] local82 = local17[2];
			for (@Pc(84) int local84 = 0; local84 < MidiPcmStream.anInt1616; local84++) {
				@Pc(90) int local90;
				if (local26 <= 0) {
					local90 = 0;
				} else {
					@Pc(96) int local96 = local84 % local26;
					local90 = MidiPcmStream.anInt1616 * local96 / local26;
				}
				local74[local84] = local62[local90];
				local78[local84] = local66[local90];
				local82[local84] = local70[local90];
			}
		}
		return local17;
	}

	@OriginalMember(owner = "client!ss", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(19) int[] local19 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			@Pc(28) int local28 = MidiPcmStream.anInt1616 / this.tileCountX;
			@Pc(33) int local33 = EnumTypeList.anInt4939 / this.tileCountY;
			@Pc(52) int[] local52;
			@Pc(42) int local42;
			if (local33 > 0) {
				local42 = arg0 % local33;
				local52 = this.getChildMonochromeOutput(local42 * EnumTypeList.anInt4939 / local33, 0);
			} else {
				local52 = this.getChildMonochromeOutput(0, 0);
			}
			for (local42 = 0; local42 < MidiPcmStream.anInt1616; local42++) {
				if (local28 <= 0) {
					local19[local42] = local52[0];
				} else {
					@Pc(81) int local81 = local42 % local28;
					local19[local42] = local52[MidiPcmStream.anInt1616 * local81 / local28];
				}
			}
		}
		return local19;
	}

@OriginalMember(owner = "client!ss", name = "G", descriptor = "I")
	public static int anInt6373;
@OriginalMember(owner = "client!ss", name = "I", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_290 = new Class22(6, 11);
@OriginalMember(owner = "client!ss", name = "J", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_126 = new Class83("Started 3d Library", "3D-Softwarebibliothek gestartet.", "Librairie 3D démarrée", "Biblioteca 3D iniciada");
@OriginalMember(owner = "client!ss", name = "K", descriptor = "[I")
	public static final int[] anIntArray484 = new int[] { 16, 32, 64, 128 };
@OriginalMember(owner = "client!ss", name = "M", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_84 = new Class215(57, 4);
@OriginalMember(owner = "client!ss", name = "a", descriptor = "(IIII)V")
	public static void method4977(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		SeqTypeList.aByteArrayArrayArray2 = new byte[4][arg1][arg0];
	}
}
