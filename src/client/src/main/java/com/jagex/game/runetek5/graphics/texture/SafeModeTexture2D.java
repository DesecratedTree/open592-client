package com.jagex.game.runetek5.graphics.texture;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.graphics.texture.*;
import com.jagex.game.runetek5.graphics.texture.SafeModeTexture2D;
@OriginalClass("client!si")
public final class SafeModeTexture2D extends Country {

	@OriginalMember(owner = "client!si", name = "o", descriptor = "I")
	public int cycle;

	@OriginalMember(owner = "client!si", name = "q", descriptor = "Z")
	public boolean isAnimated;

	@OriginalMember(owner = "client!si", name = "m", descriptor = "I")
	public final int materialId;

	@OriginalMember(owner = "client!si", name = "l", descriptor = "I")
	private final int totalColorCount;

	@OriginalMember(owner = "client!si", name = "p", descriptor = "[I")
	private int[] colorArgb;

	@OriginalMember(owner = "client!si", name = "<init>", descriptor = "(II[IZ)V")
	public SafeModeTexture2D(@OriginalArg(0) int materialId, @OriginalArg(1) int totalColorCount, @OriginalArg(2) int[] argbColor, @OriginalArg(3) boolean shouldBlur) {
		this.materialId = materialId;
		this.totalColorCount = totalColorCount;
		this.colorArgb = argbColor;
		if (shouldBlur) {
			@Pc(16) int[] local16 = new int[this.totalColorCount];
			@Pc(20) int[] local20 = new int[this.totalColorCount];
			@Pc(24) int[] local24 = new int[this.totalColorCount];
			if (SafeModeTexture2D.colorsArgb == null || SafeModeTexture2D.colorsArgb.length < this.colorArgb.length) {
				SafeModeTexture2D.colorsArgb = new int[this.colorArgb.length];
			}
			@Pc(40) int local40 = this.totalColorCount;
			@Pc(43) int local43 = this.totalColorCount;
			@Pc(47) int local47 = local40 - 1;
			@Pc(51) int local51 = local43 - 1;
			@Pc(55) int local55 = local40 * local43;
			@Pc(58) int local58 = local40;
			@Pc(59) int local59 = local40;
			@Pc(64) int local64;
			for (@Pc(61) int local61 = 2; local61 >= 0; local61--) {
				for (local64 = local47; local64 >= 0; local64--) {
					local58--;
					@Pc(71) int local71 = this.colorArgb[local58];
					local16[local64] += local71 >> 16 & 0xFF;
					local20[local64] += local71 >> 8 & 0xFF;
					local24[local64] += local71 & 0xFF;
				}
				if (local58 == 0) {
					local58 = local55;
				}
			}
			@Pc(114) int local114 = local55;
			for (@Pc(116) int local116 = local51; local116 >= 0; local116--) {
				@Pc(119) int local119 = 1;
				@Pc(121) int local121 = 1;
				@Pc(124) int local124 = 0;
				@Pc(126) int local126 = 0;
				@Pc(127) int local127 = 0;
				for (local64 = 2; local64 >= 0; local64--) {
					local121--;
					local127 += local16[local121];
					local124 += local20[local121];
					local126 += local24[local121];
					if (local121 == 0) {
						local121 = local40;
					}
				}
				for (local64 = local47; local64 >= 0; local64--) {
					local121--;
					local119--;
					@Pc(165) int local165 = local127 / 9;
					@Pc(169) int local169 = local124 / 9;
					@Pc(173) int local173 = local126 / 9;
					local114--;
					SafeModeTexture2D.colorsArgb[local114] = local165 << 16 | local169 << 8 | local173;
					local127 += local16[local121] - local16[local119];
					local126 += local24[local121] - local24[local119];
					local124 += local20[local121] - local20[local119];
					if (local121 == 0) {
						local121 = local40;
					}
					if (local119 == 0) {
						local119 = local40;
					}
				}
				for (local64 = local47; local64 >= 0; local64--) {
					local58--;
					@Pc(236) int local236 = this.colorArgb[local58];
					local59--;
					@Pc(242) int local242 = this.colorArgb[local59];
					local16[local64] += (local236 >> 16 & 0xFF) - (local242 >> 16 & 0xFF);
					local20[local64] += (local236 >> 8 & 0xFF) - (local242 >> 8 & 0xFF);
					local24[local64] += (local236 & 0xFF) - (local242 & 0xFF);
				}
				if (local58 == 0) {
					local58 = local55;
				}
				if (local59 == 0) {
					local59 = local55;
				}
			}
			@Pc(306) int[] local306 = this.colorArgb;
			this.colorArgb = SafeModeTexture2D.colorsArgb;
			SafeModeTexture2D.colorsArgb = local306;
		}
	}

	@OriginalMember(owner = "client!si", name = "a", descriptor = "(II)V")
	public void animate(@OriginalArg(0) int offsetX, @OriginalArg(1) int offsetY) {
		if (offsetX == 0 && offsetY == 0) {
			return;
		}
		if (SafeModeTexture2D.colorsArgb == null || SafeModeTexture2D.colorsArgb.length < this.colorArgb.length) {
			SafeModeTexture2D.colorsArgb = new int[this.colorArgb.length];
		}
		@Pc(21) int local21 = this.colorArgb.length;
		@Pc(28) int local28 = this.totalColorCount - 1;
		@Pc(33) int local33 = this.totalColorCount * offsetY;
		@Pc(37) int local37 = local21 - 1;
		for (@Pc(39) int local39 = 0; local39 < local21; local39 += this.totalColorCount) {
			@Pc(46) int local46 = local39 + local33 & local37;
			for (@Pc(48) int local48 = 0; local48 < this.totalColorCount; local48++) {
				@Pc(53) int local53 = local39 + local48;
				@Pc(61) int local61 = local46 + (local48 + offsetX & local28);
				SafeModeTexture2D.colorsArgb[local53] = this.colorArgb[local61];
			}
		}
		@Pc(84) int[] local84 = this.colorArgb;
		this.colorArgb = SafeModeTexture2D.colorsArgb;
		SafeModeTexture2D.colorsArgb = local84;
	}

	@OriginalMember(owner = "client!si", name = "b", descriptor = "()[I")
	public int[] getArgbColor() {
		return this.colorArgb;
	}

@OriginalMember(owner = "client!si", name = "n", descriptor = "[I")
	public static int[] colorsArgb;
}
