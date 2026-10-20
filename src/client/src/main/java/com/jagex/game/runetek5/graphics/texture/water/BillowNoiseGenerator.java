package com.jagex.game.runetek5.graphics.texture.water;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class48;

import java.awt.Frame;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.DequeIterator;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Static173;
import com.jagex.game.runetek5.deob.Static281;
import com.jagex.game.runetek5.deob.Static384;
import com.jagex.game.runetek5.deob.Static459;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;

import com.jagex.game.runetek5.graphics.texture.water.*;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.deob.Class111;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
@OriginalClass("client!qb")
public class BillowNoiseGenerator extends Class48 {

	@OriginalMember(owner = "client!qb", name = "s", descriptor = "I")
	private int writePosition;

	@OriginalMember(owner = "client!qb", name = "w", descriptor = "I")
	private int accumulator;

	@OriginalMember(owner = "client!qb", name = "x", descriptor = "[B")
	private byte[] outputBuffer;

	@OriginalMember(owner = "client!qb", name = "C", descriptor = "[I")
	private final int[] amplitudeWeights = new int[this.anInt5645];

	@OriginalMember(owner = "client!qb", name = "<init>", descriptor = "(IIIIIF)V")
	protected BillowNoiseGenerator(@OriginalArg(0) int seed, @OriginalArg(1) int factorsOfTwo, @OriginalArg(2) int xFactor, @OriginalArg(3) int yFactor, @OriginalArg(4) int zFactor, @OriginalArg(5) float frequency) {
		super(seed, factorsOfTwo, xFactor, yFactor, zFactor);
		for (@Pc(15) int local15 = 0; super.anInt5645 > local15; local15++) {
			this.amplitudeWeights[local15] = (short) (Math.pow((double) frequency, (double) local15) * 4096.0D);
		}
	}

	@OriginalMember(owner = "client!qb", name = "a", descriptor = "(B)V")
	@Override
	protected final void emitTexel() {
		this.accumulator = Math.abs(this.accumulator);
		if (this.accumulator >= 4096) {
			this.accumulator = 4095;
		}
		this.method4408((byte) (this.accumulator >> 4), this.writePosition++);
		this.accumulator = 0;
	}

	@OriginalMember(owner = "client!qb", name = "a", descriptor = "(BBI)V")
	protected void method4408(@OriginalArg(1) byte arg0, @OriginalArg(2) int arg1) {
		this.outputBuffer[this.writePosition++] = (byte) ((arg0 >> 1 & 0x7F) + 127);
	}

	@OriginalMember(owner = "client!qb", name = "a", descriptor = "(IIZ)V")
	@Override
	protected final void accumulateOctave(@OriginalArg(0) int noise, @OriginalArg(1) int octave) {
		this.accumulator += octave * this.amplitudeWeights[noise] >> 12;
	}

	@OriginalMember(owner = "client!qb", name = "d", descriptor = "(I)V")
	@Override
	protected final void reset() {
		this.accumulator = 0;
		this.writePosition = 0;
	}

@OriginalMember(owner = "client!qb", name = "z", descriptor = "Ljava/awt/Frame;")
	public static Frame aFrame3;
@OriginalMember(owner = "client!qb", name = "q", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_118 = new Class83("level: ", "Stufe: ", "niveau ", "nível: ");
@OriginalMember(owner = "client!qb", name = "A", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_263 = new Class22(41, 7);
@OriginalMember(owner = "client!qb", name = "B", descriptor = "I")
	public static int anInt5660 = -1;
@OriginalMember(owner = "client!qb", name = "e", descriptor = "(I)I")
	public static int method4409() {
		if (Static281.aClass247_12 == null) {
			if (!Class4_Sub6_Sub22.aBoolean411 && Skybox.anInt6710 > 0) {
				if (Country.aBoolean692 && Static384.aClass244_1.method5489(81) && Skybox.anInt6710 > 2) {
					return ((OggOtherStream) Class126.aClass183_28.aClass4_207.aClass4_261.aClass4_261).anInt5698;
				}
				return ((OggOtherStream) Class126.aClass183_28.aClass4_207.aClass4_261).anInt5698;
			}
			@Pc(20) int local20 = MaterialType.aClass80_1.method2234();
			@Pc(24) int local24 = MaterialType.aClass80_1.getDefaultValue();
			@Pc(26) int local26 = Class4_Sub37.anInt5424;
			@Pc(28) int local28 = Class111.anInt3367;
			@Pc(30) int local30 = Static173.anInt3370;
			if (local20 > local26 && local30 + local26 > local20) {
				@Pc(44) int local44 = -1;
				@Pc(64) int local64;
				for (@Pc(46) int local46 = 0; local46 < Skybox.anInt6710; local46++) {
					if (Class177.aBoolean481) {
						local64 = local28 + (-local46 + (Skybox.anInt6710 - 1)) * 16 + 33;
						if (local64 - 13 < local24 && local64 + 3 >= local24) {
							local44 = local46;
						}
					} else {
						local64 = (Skybox.anInt6710 - local46 - 1) * 16 + local28 + 31;
						if (local24 > local64 - 13 && local24 <= local64 + 3) {
							local44 = local46;
						}
					}
				}
				if (local44 != -1) {
					local64 = 0;
					@Pc(133) DequeIterator local133 = new DequeIterator(Class126.aClass183_28);
					for (@Pc(138) OggOtherStream local138 = (OggOtherStream) local133.method1611(); local138 != null; local138 = (OggOtherStream) local133.method1612()) {
						if (local64++ == local44) {
							return local138.anInt5698;
						}
					}
				}
			}
		}
		return -1;
	}
@OriginalMember(owner = "client!qb", name = "a", descriptor = "(I[FI)[F")
	public static float[] method4410(@OriginalArg(0) int arg0, @OriginalArg(1) float[] arg1) {
		@Pc(6) float[] local6 = new float[arg0];
		Static459.copy(arg1, 0, local6, 0, arg0);
		return local6;
	}
}
