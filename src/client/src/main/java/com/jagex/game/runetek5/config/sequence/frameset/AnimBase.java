package com.jagex.game.runetek5.config.sequence.frameset;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.buffer.Packet;

import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.config.sequence.frameset.*;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!nk")
public final class AnimBase extends Country {

	@OriginalMember(owner = "client!nk", name = "u", descriptor = "I")
	public final int id;

	@OriginalMember(owner = "client!nk", name = "l", descriptor = "I")
	public final int numTransformation;

	@OriginalMember(owner = "client!nk", name = "n", descriptor = "[I")
	public final int[] transformationType;

	@OriginalMember(owner = "client!nk", name = "t", descriptor = "[[I")
	public final int[][] labels;

	@OriginalMember(owner = "client!nk", name = "p", descriptor = "[Z")
	public final boolean[] shadowed;

	@OriginalMember(owner = "client!nk", name = "r", descriptor = "[I")
	public final int[] submeshes;

	@OriginalMember(owner = "client!nk", name = "<init>", descriptor = "(I[B)V")
	public AnimBase(@OriginalArg(0) int id, @OriginalArg(1) byte[] bytes) {
		this.id = id;
		@Pc(11) Packet local11 = new Packet(bytes);
		this.numTransformation = local11.method2490();
		this.transformationType = new int[this.numTransformation];
		this.labels = new int[this.numTransformation][];
		this.shadowed = new boolean[this.numTransformation];
		this.submeshes = new int[this.numTransformation];
		for (@Pc(38) int local38 = 0; local38 < this.numTransformation; local38++) {
			this.transformationType[local38] = local11.method2490();
		}
		for (@Pc(54) int local54 = 0; local54 < this.numTransformation; local54++) {
			this.shadowed[local54] = local11.method2490() == 1;
		}
		for (@Pc(81) int local81 = 0; local81 < this.numTransformation; local81++) {
			this.submeshes[local81] = local11.method2536();
		}
		for (@Pc(101) int local101 = 0; local101 < this.numTransformation; local101++) {
			this.labels[local101] = new int[local11.method2490()];
		}
		for (@Pc(118) int local118 = 0; local118 < this.numTransformation; local118++) {
			for (@Pc(122) int local122 = 0; local122 < this.labels[local118].length; local122++) {
				this.labels[local118][local122] = local11.method2490();
			}
		}
	}

@OriginalMember(owner = "client!nk", name = "m", descriptor = "I")
	public static final int anInt4982 = 1406;
@OriginalMember(owner = "client!nk", name = "o", descriptor = "[I")
	public static final int[] anIntArray319 = new int[4];
@OriginalMember(owner = "client!nk", name = "q", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_61 = new Class215(30, 3);
@OriginalMember(owner = "client!nk", name = "v", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_62 = new Class215(42, -1);
@OriginalMember(owner = "client!nk", name = "a", descriptor = "(II)I")
	public static int method3825(@OriginalArg(1) int arg0) {
		@Pc(14) double local14 = (double) (arg0 >> 16 & 0xFF) / 256.0D;
		@Pc(23) double local23 = (double) (arg0 >> 8 & 0xFF) / 256.0D;
		@Pc(30) double local30 = (double) (arg0 & 0xFF) / 256.0D;
		@Pc(32) double local32 = local14;
		if (local14 > local23) {
			local32 = local23;
		}
		if (local32 > local30) {
			local32 = local30;
		}
		@Pc(46) double local46 = local14;
		if (local14 < local23) {
			local46 = local23;
		}
		if (local30 > local46) {
			local46 = local30;
		}
		@Pc(60) double local60 = 0.0D;
		@Pc(63) double local63 = (double) 0;
		@Pc(69) double local69 = (local32 + local46) / 2.0D;
		if (local32 != local46) {
			if (local69 < 0.5D) {
				local63 = (local46 - local32) / (local46 + local32);
			}
			if (local14 == local46) {
				local60 = (local23 - local30) / (local46 - local32);
			} else if (local23 == local46) {
				local60 = (local30 - local14) / (-local32 + local46) + 2.0D;
			} else if (local30 == local46) {
				local60 = (local14 - local23) / (-local32 + local46) + 4.0D;
			}
			if (local69 >= 0.5D) {
				local63 = (local46 - local32) / (2.0D - local46 - local32);
			}
		}
		local60 /= 6.0D;
		@Pc(157) int local157 = (int) (local60 * 256.0D);
		@Pc(162) int local162 = (int) (local63 * 256.0D);
		if (local162 < 0) {
			local162 = 0;
		} else if (local162 > 255) {
			local162 = 255;
		}
		@Pc(180) int local180 = (int) (local69 * 256.0D);
		if (local180 < 0) {
			local180 = 0;
		} else if (local180 > 255) {
			local180 = 255;
		}
		if (local180 > 243) {
			local162 >>= 0x4;
		} else if (local180 > 217) {
			local162 >>= 0x3;
		} else if (local180 > 192) {
			local162 >>= 0x2;
		} else if (local180 > 179) {
			local162 >>= 0x1;
		}
		return (local162 >> 5 << 7) + ((local157 & 0xFF) >> 2 << 10) + (local180 >> 1);
	}
@OriginalMember(owner = "client!nk", name = "a", descriptor = "(IIIIIIII)V")
	public static void method3827(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6) {
		if (arg3 >= ItemType.anInt6186 && Class36.anInt2656 >= arg2 && arg0 >= Model.anInt6287 && arg5 <= GlCubeTexture.anInt3256) {
			Class10.method130(arg0, arg2, arg6, arg3, arg1, arg5, arg4);
		} else {
			Class99_Sub1.method3236(arg3, arg1, arg4, arg0, arg6, arg2, arg5);
		}
	}
}
