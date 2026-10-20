package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.TileLighting;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.core.compress.BzipDecompressor;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.graphics.font.FontMetrics;
import com.jagex.game.runetek5.QuickChatPhrase;
import com.jagex.game.runetek5.deob.Class70_Sub1;
import com.jagex.game.runetek5.deob.Class78_Sub1;
import com.jagex.game.runetek5.deob.JavaFallbackSurface;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
@OriginalClass("client!jr")
public final class Class4_Sub6_Sub16 extends TextureOperation {

	@OriginalMember(owner = "client!jr", name = "N", descriptor = "I")
	private int anInt3715 = 3216;

	@OriginalMember(owner = "client!jr", name = "J", descriptor = "I")
	private int anInt3712 = 4096;

	@OriginalMember(owner = "client!jr", name = "O", descriptor = "I")
	private int anInt3716 = 3216;

	@OriginalMember(owner = "client!jr", name = "T", descriptor = "[I")
	private final int[] anIntArray253 = new int[3];

	@OriginalMember(owner = "client!jr", name = "<init>", descriptor = "()V")
	public Class4_Sub6_Sub16() {
		super(1, true);
	}

	@OriginalMember(owner = "client!jr", name = "a", descriptor = "(Z)V")
	@Override
	public void postDecode() {
		this.method3073();
	}

	@OriginalMember(owner = "client!jr", name = "f", descriptor = "(I)V")
	private void method3073() {
		@Pc(11) double local11 = Math.cos((double) ((float) this.anInt3716 / 4096.0F));
		this.anIntArray253[0] = (int) (local11 * Math.sin((double) ((float) this.anInt3715 / 4096.0F)) * 4096.0D);
		this.anIntArray253[1] = (int) (local11 * 4096.0D * Math.cos((double) ((float) this.anInt3715 / 4096.0F)));
		this.anIntArray253[2] = (int) (Math.sin((double) ((float) this.anInt3716 / 4096.0F)) * 4096.0D);
		@Pc(75) int local75 = this.anIntArray253[0] * this.anIntArray253[0] >> 12;
		@Pc(87) int local87 = this.anIntArray253[1] * this.anIntArray253[1] >> 12;
		@Pc(99) int local99 = this.anIntArray253[2] * this.anIntArray253[2] >> 12;
		@Pc(112) int local112 = (int) (Math.sqrt((double) (local99 + local87 + local75 >> 12)) * 4096.0D);
		if (local112 != 0) {
			this.anIntArray253[2] = (this.anIntArray253[2] << 12) / local112;
			this.anIntArray253[1] = (this.anIntArray253[1] << 12) / local112;
			this.anIntArray253[0] = (this.anIntArray253[0] << 12) / local112;
		}
	}

	@OriginalMember(owner = "client!jr", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.anInt3712 = arg1.method2536();
		} else if (arg0 == 1) {
			this.anInt3715 = arg1.method2536();
		} else if (arg0 == 2) {
			this.anInt3716 = arg1.method2536();
		}
	}

	@OriginalMember(owner = "client!jr", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(19) int[] local19 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			@Pc(30) int local30 = TileLighting.anInt4776 * this.anInt3712 >> 12;
			@Pc(40) int[] local40 = this.getChildMonochromeOutput(arg0 - 1 & Static211.anInt3929, 0);
			@Pc(46) int[] local46 = this.getChildMonochromeOutput(arg0, 0);
			@Pc(56) int[] local56 = this.getChildMonochromeOutput(Static211.anInt3929 & arg0 + 1, 0);
			for (@Pc(58) int local58 = 0; local58 < MidiPcmStream.anInt1616; local58++) {
				@Pc(73) int local73 = (local56[local58] - local40[local58]) * local30 >> 12;
				@Pc(93) int local93 = local30 * (local46[local58 - 1 & Class78_Sub1.anInt3883] - local46[Class78_Sub1.anInt3883 & local58 + 1]) >> 12;
				@Pc(97) int local97 = local93 >> 4;
				@Pc(101) int local101 = local73 >> 4;
				if (local97 < 0) {
					local97 = -local97;
				}
				if (local101 < 0) {
					local101 = -local101;
				}
				if (local97 > 255) {
					local97 = 255;
				}
				if (local101 > 255) {
					local101 = 255;
				}
				@Pc(141) int local141 = Class9.aByteArray98[local97 + (local101 * (local101 + 1) >> 1)] & 0xFF;
				@Pc(147) int local147 = local93 * local141 >> 8;
				@Pc(153) int local153 = local141 * 4096 >> 8;
				@Pc(159) int local159 = local141 * local73 >> 8;
				@Pc(168) int local168 = local147 * this.anIntArray253[0] >> 12;
				@Pc(177) int local177 = local159 * this.anIntArray253[1] >> 12;
				@Pc(186) int local186 = this.anIntArray253[2] * local153 >> 12;
				local19[local58] = local186 + local168 + local177;
			}
		}
		return local19;
	}

@OriginalMember(owner = "client!jr", name = "Q", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_43;
@OriginalMember(owner = "client!jr", name = "S", descriptor = "I")
	public static int anInt3719;
@OriginalMember(owner = "client!jr", name = "I", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_167 = new Class22(13, -2);
@OriginalMember(owner = "client!jr", name = "M", descriptor = "I")
	public static int anInt3714 = 2;
@OriginalMember(owner = "client!jr", name = "a", descriptor = "(IIIIIIII)V")
	public static void method3072(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6) {
		@Pc(7) int local7 = arg5 - 334;
		if (local7 < 0) {
			local7 = 0;
		} else if (local7 > 100) {
			local7 = 100;
		}
		@Pc(33) int local33 = (BzipDecompressor.aShort13 - Static14.aShort1) * local7 / 100 + Static14.aShort1;
		@Pc(39) int local39 = local33 * arg1 >> 8;
		@Pc(46) int local46 = 16384 - arg6 & 0x3FFF;
		@Pc(53) int local53 = 16384 - arg3 & 0x3FFF;
		@Pc(55) int local55 = 0;
		@Pc(57) int local57 = 0;
		@Pc(59) int local59 = local39;
		if (local46 != 0) {
			local57 = -local39 * Class50_Sub1.anIntArray225[local46] >> 15;
			local59 = Class50_Sub1.anIntArray224[local46] * local39 >> 15;
		}
		if (local53 != 0) {
			local55 = local59 * Class50_Sub1.anIntArray225[local53] >> 15;
			local59 = Class50_Sub1.anIntArray224[local53] * local59 >> 15;
		}
		QuickChatPhrase.anInt7074 = arg6;
		Class4_Sub23.anInt3197 = arg0 - local55;
		Class70_Sub1.anInt2070 = 0;
		Static145.anInt2906 = arg2 - local59;
		FontMetrics.anInt2790 = arg3;
		TextureOpIrregularBricks.anInt2024 = arg4 - local57;
	}
@OriginalMember(owner = "client!jr", name = "a", descriptor = "(BLclient!ji;)V")
	public static void method3074(@OriginalArg(1) Huffman arg0) {
		JavaFallbackSurface.aClass119_1 = arg0;
	}
@OriginalMember(owner = "client!jr", name = "a", descriptor = "(I[J[Ljava/lang/Object;II)V")
	public static void method3075(@OriginalArg(0) int arg0, @OriginalArg(1) long[] arg1, @OriginalArg(2) Object[] arg2, @OriginalArg(3) int arg3) {
		if (arg0 >= arg3) {
			return;
		}
		@Pc(14) int local14 = (arg0 + arg3) / 2;
		@Pc(16) int local16 = arg0;
		@Pc(20) long local20 = arg1[local14];
		arg1[local14] = arg1[arg3];
		arg1[arg3] = local20;
		@Pc(34) Object local34 = arg2[local14];
		arg2[local14] = arg2[arg3];
		arg2[arg3] = local34;
		@Pc(52) int local52 = local20 == Long.MAX_VALUE ? 0 : 1;
		for (@Pc(54) int local54 = arg0; local54 < arg3; local54++) {
			if (arg1[local54] < local20 + (long) (local54 & local52)) {
				@Pc(72) long local72 = arg1[local54];
				arg1[local54] = arg1[local16];
				arg1[local16] = local72;
				@Pc(86) Object local86 = arg2[local54];
				arg2[local54] = arg2[local16];
				arg2[local16++] = local86;
			}
		}
		arg1[arg3] = arg1[local16];
		arg1[local16] = local20;
		arg2[arg3] = arg2[local16];
		arg2[local16] = local34;
		method3075(arg0, arg1, arg2, local16 - 1);
		method3075(local16 + 1, arg1, arg2, arg3);
	}
}
