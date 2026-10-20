package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.datatransfer.Clipboard;
import com.jagex.game.runetek5.config.mec.MECTypeList;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlBitBuffer;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.entity.pathingentity.EasedAngle;
import com.jagex.game.runetek5.deob.Class120_Sub1;
import com.jagex.game.runetek5.graphics.model.normal.ModelNormalsGL;
import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub37;
import com.jagex.game.runetek5.graphics.texture.water.BillowNoiseGenerator;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
@OriginalClass("client!rn")
public final class Class4_Sub6_Sub31 extends TextureOperation {

	@OriginalMember(owner = "client!rn", name = "J", descriptor = "I")
	private int anInt5971 = 1024;

	@OriginalMember(owner = "client!rn", name = "M", descriptor = "I")
	private int anInt5974 = 3072;

	@OriginalMember(owner = "client!rn", name = "O", descriptor = "I")
	private int anInt5975 = 2048;

	static {
		new Class83("Now talking in clan channel ", "Chatraum: ", "Vous participez actuellement au canal de clan ", "Falando no canal do clã agora ");
	}

	@OriginalMember(owner = "client!rn", name = "<init>", descriptor = "()V")
	public Class4_Sub6_Sub31() {
		super(1, false);
	}

	@OriginalMember(owner = "client!rn", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.anInt5971 = arg1.method2536();
		} else if (arg0 == 1) {
			this.anInt5974 = arg1.method2536();
		} else if (arg0 == 2) {
			super.monochrome = arg1.method2490() == 1;
		}
	}

	@OriginalMember(owner = "client!rn", name = "a", descriptor = "(BI)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		@Pc(19) int[][] local19 = super.colorImageCache.get(arg0);
		if (super.colorImageCache.dirty) {
			@Pc(29) int[][] local29 = this.getChildColorOutput(0, arg0);
			@Pc(33) int[] local33 = local29[0];
			@Pc(37) int[] local37 = local29[1];
			@Pc(41) int[] local41 = local29[2];
			@Pc(45) int[] local45 = local19[0];
			@Pc(49) int[] local49 = local19[1];
			@Pc(53) int[] local53 = local19[2];
			for (@Pc(55) int local55 = 0; local55 < MidiPcmStream.anInt1616; local55++) {
				local45[local55] = this.anInt5971 + (local33[local55] * this.anInt5975 >> 12);
				local49[local55] = (this.anInt5975 * local37[local55] >> 12) + this.anInt5971;
				local53[local55] = this.anInt5971 + (this.anInt5975 * local41[local55] >> 12);
			}
		}
		return local19;
	}

	@OriginalMember(owner = "client!rn", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[] local11 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			@Pc(29) int[] local29 = this.getChildMonochromeOutput(arg0, 0);
			for (@Pc(31) int local31 = 0; local31 < MidiPcmStream.anInt1616; local31++) {
				local11[local31] = (this.anInt5975 * local29[local31] >> 12) + this.anInt5971;
			}
		}
		return local11;
	}

	@OriginalMember(owner = "client!rn", name = "a", descriptor = "(Z)V")
	@Override
	public void postDecode() {
		this.anInt5975 = this.anInt5974 - this.anInt5971;
	}

@OriginalMember(owner = "client!rn", name = "N", descriptor = "Lclient!pj;")
	public static MECTypeList aClass182_4;
@OriginalMember(owner = "client!rn", name = "Q", descriptor = "Ljava/awt/datatransfer/Clipboard;")
	public static Clipboard aClipboard1;
@OriginalMember(owner = "client!rn", name = "E", descriptor = "Lclient!pk;")
	public static final Class183 aClass183_42 = new Class183();
@OriginalMember(owner = "client!rn", name = "P", descriptor = "I")
	public static int anInt5976 = 0;
@OriginalMember(owner = "client!rn", name = "a", descriptor = "(IIB)I")
	public static int method4695(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		return arg0 == 4 || arg0 == 5 ? EasedAngle.anIntArray50[arg1 & 0x3] : 256;
	}
@OriginalMember(owner = "client!rn", name = "a", descriptor = "(BLclient!ya;)V")
	public static void method4696(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		if (Class4_Sub6_Sub22.aBoolean411) {
			GlBitBuffer.method2554(arg0);
		} else {
			Static17.method177(arg0);
		}
	}
@OriginalMember(owner = "client!rn", name = "c", descriptor = "(B)V")
	public static void method4697() {
		if (BillowNoiseGenerator.aFrame3 != null) {
			return;
		}
		@Pc(16) int local16 = FileOnDisk.anInt4424;
		@Pc(18) int local18 = Class120_Sub1.anInt5363;
		@Pc(25) int local25 = Class4_Sub6_Sub37.anInt7000 - GpuPointLight.anInt2881 - local16;
		@Pc(33) int local33 = Static17.anInt222 - local18 - EffectiveVertex.anInt5346;
		if (local16 <= 0 && local25 <= 0 && local18 <= 0 && local33 <= 0) {
			return;
		}
		try {
			@Pc(49) Container local49;
			if (ModelNormalsGL.aFrame1 == null) {
				local49 = Class126.aClass66_4.anApplet1;
			} else {
				local49 = ModelNormalsGL.aFrame1;
			}
			@Pc(55) int local55 = 0;
			@Pc(57) int local57 = 0;
			if (ModelNormalsGL.aFrame1 == local49) {
				@Pc(63) Insets local63 = ModelNormalsGL.aFrame1.getInsets();
				local55 = local63.left;
				local57 = local63.top;
			}
			@Pc(72) Graphics local72 = local49.getGraphics();
			local72.setColor(Color.black);
			if (local16 > 0) {
				local72.fillRect(local55, local57, local16, Static17.anInt222);
			}
			if (local18 > 0) {
				local72.fillRect(local55, local57, Class4_Sub6_Sub37.anInt7000, local18);
			}
			if (local25 > 0) {
				local72.fillRect(Class4_Sub6_Sub37.anInt7000 + local55 - local25, local57, local25, Static17.anInt222);
			}
			if (local33 > 0) {
				local72.fillRect(local55, local57 + Static17.anInt222 - local33, Class4_Sub6_Sub37.anInt7000, local33);
				return;
			}
		} catch (@Pc(125) Exception local125) {
			return;
		}
	}
}
