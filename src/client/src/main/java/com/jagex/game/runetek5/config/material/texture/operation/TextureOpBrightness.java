package com.jagex.game.runetek5.config.material.texture.operation;

import java.util.Random;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.deob.Static211;
import com.jagex.game.runetek5.deob.Static277;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class50_Sub1;

import com.jagex.game.runetek5.config.material.texture.operation.*;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.graphics.model.normal.ModelNormalsGL;
import com.jagex.game.runetek5.config.enumtype.EnumTypeList;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.cache.node.lru.StructType;
import com.jagex.game.runetek5.deob.Class78_Sub1;
@OriginalClass("client!qf")
public final class TextureOpBrightness extends TextureOperation {

	@OriginalMember(owner = "client!qf", name = "C", descriptor = "I")
	private int anInt5613 = 0;

	@OriginalMember(owner = "client!qf", name = "D", descriptor = "I")
	private int anInt5614 = 16;

	@OriginalMember(owner = "client!qf", name = "G", descriptor = "I")
	private int anInt5617 = 0;

	@OriginalMember(owner = "client!qf", name = "E", descriptor = "I")
	private int anInt5615 = 2000;

	@OriginalMember(owner = "client!qf", name = "N", descriptor = "I")
	private int anInt5623 = 4096;

	@OriginalMember(owner = "client!qf", name = "<init>", descriptor = "()V")
	public TextureOpBrightness() {
		super(0, true);
	}

	@OriginalMember(owner = "client!qf", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(19) int[] local19 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			@Pc(28) int local28 = this.anInt5623 >> 1;
			@Pc(33) int[][] local33 = super.monochromeImageCache.method2263();
			@Pc(40) Random local40 = new Random((long) this.anInt5613);
			for (@Pc(42) int local42 = 0; local42 < this.anInt5615; local42++) {
				@Pc(65) int local65 = this.anInt5623 <= 0 ? this.anInt5617 : this.anInt5617 + ModelNormalsGL.method3443(local40, this.anInt5623) - local28;
				@Pc(71) int local71 = local65 >> 4 & 0xFF;
				@Pc(76) int local76 = ModelNormalsGL.method3443(local40, MidiPcmStream.anInt1616);
				@Pc(81) int local81 = ModelNormalsGL.method3443(local40, EnumTypeList.anInt4939);
				@Pc(92) int local92 = (this.anInt5614 * StructType.anIntArray416[local71] >> 12) + local76;
				@Pc(103) int local103 = local81 + (Static277.anIntArray336[local71] * this.anInt5614 >> 12);
				@Pc(108) int local108 = local103 - local81;
				@Pc(113) int local113 = local92 - local76;
				if (local113 != 0 || local108 != 0) {
					if (local108 < 0) {
						local108 = -local108;
					}
					if (local113 < 0) {
						local113 = -local113;
					}
					@Pc(144) boolean local144 = local113 < local108;
					@Pc(148) int local148;
					@Pc(152) int local152;
					if (local144) {
						local148 = local76;
						local76 = local81;
						local152 = local92;
						local81 = local148;
						local92 = local103;
						local103 = local152;
					}
					if (local76 > local92) {
						local148 = local76;
						local152 = local81;
						local76 = local92;
						local81 = local103;
						local92 = local148;
						local103 = local152;
					}
					local148 = local81;
					local152 = local92 - local76;
					@Pc(188) int local188 = local103 - local81;
					@Pc(193) int local193 = -local152 / 2;
					@Pc(197) int local197 = 2048 / local152;
					@Pc(207) int local207 = 1024 - (ModelNormalsGL.method3443(local40, 4096) >> 2);
					@Pc(214) int local214 = local103 > local81 ? 1 : -1;
					if (local188 < 0) {
						local188 = -local188;
					}
					for (@Pc(224) int local224 = local76; local224 < local92; local224++) {
						@Pc(237) int local237 = local207 + (local224 - local76) * local197 + 1024;
						@Pc(241) int local241 = Class78_Sub1.anInt3883 & local224;
						@Pc(245) int local245 = local148 & Static211.anInt3929;
						if (local144) {
							local33[local245][local241] = local237;
						} else {
							local33[local241][local245] = local237;
						}
						local193 += local188;
						if (local193 > 0) {
							local193 += -local152;
							local148 += local214;
						}
					}
				}
			}
		}
		return local19;
	}

	@OriginalMember(owner = "client!qf", name = "a", descriptor = "(Z)V")
	@Override
	public void postDecode() {
		LRUCache.method1784();
	}

	@OriginalMember(owner = "client!qf", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.anInt5613 = arg1.method2490();
		} else if (arg0 == 1) {
			this.anInt5615 = arg1.method2536();
		} else if (arg0 == 2) {
			this.anInt5614 = arg1.method2490();
		} else if (arg0 == 3) {
			this.anInt5617 = arg1.method2536();
		} else if (arg0 == 4) {
			this.anInt5623 = arg1.method2536();
		}
	}

@OriginalMember(owner = "client!qf", name = "O", descriptor = "Lclient!jb;")
	public static Class50_Sub1 aClass50_Sub1_1;
@OriginalMember(owner = "client!qf", name = "I", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_74 = new Class215(43, 2);
@OriginalMember(owner = "client!qf", name = "K", descriptor = "I")
	public static int anInt5620 = 0;
@OriginalMember(owner = "client!qf", name = "a", descriptor = "(IB)Z")
	public static boolean method4382(@OriginalArg(0) int arg0) {
		if (arg0 == 11 || arg0 == 15 || arg0 == 21 || arg0 == 3 || arg0 == 50) {
			return true;
		} else {
			return arg0 == 22 || arg0 == 1003;
		}
	}
@OriginalMember(owner = "client!qf", name = "a", descriptor = "(Ljava/lang/String;Z)Ljava/lang/Class;")
	public static Class method4383(@OriginalArg(0) String arg0) throws ClassNotFoundException {
		if (arg0.equals("B")) {
			return Byte.TYPE;
		} else if (arg0.equals("I")) {
			return Integer.TYPE;
		} else if (arg0.equals("S")) {
			return Short.TYPE;
		} else if (arg0.equals("J")) {
			return Long.TYPE;
		} else if (arg0.equals("Z")) {
			return Boolean.TYPE;
		} else if (arg0.equals("F")) {
			return Float.TYPE;
		} else if (arg0.equals("D")) {
			return Double.TYPE;
		} else if (arg0.equals("C")) {
			return Character.TYPE;
		} else {
			return Class.forName(arg0);
		}
	}
}
