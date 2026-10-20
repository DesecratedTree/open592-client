package com.jagex.game.runetek5.graphics.sprite;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Static459;

import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.graphics.sprite.*;
@OriginalClass("client!bb")
public final class SpriteType {

	@OriginalMember(owner = "client!bb", name = "a", descriptor = "I")
	public int height;

	@OriginalMember(owner = "client!bb", name = "b", descriptor = "I")
	public int width;

	@OriginalMember(owner = "client!bb", name = "c", descriptor = "[B")
	public byte[] pixels;

	@OriginalMember(owner = "client!bb", name = "d", descriptor = "I")
	public int minX;

	@OriginalMember(owner = "client!bb", name = "e", descriptor = "I")
	public int borderX;

	@OriginalMember(owner = "client!bb", name = "f", descriptor = "[I")
	public int[] pallete;

	@OriginalMember(owner = "client!bb", name = "g", descriptor = "[B")
	public byte[] alpha;

	@OriginalMember(owner = "client!bb", name = "h", descriptor = "I")
	public int minY;

	@OriginalMember(owner = "client!bb", name = "i", descriptor = "I")
	public int borderY;

	@OriginalMember(owner = "client!bb", name = "a", descriptor = "(I)V")
	public void outline(@OriginalArg(0) int outlineColor) {
		@Pc(1) int local1 = -1;
		@Pc(8) int local8;
		@Pc(69) int local69;
		@Pc(63) int local63;
		if (this.pallete.length < 255) {
			for (local8 = 0; local8 < this.pallete.length; local8++) {
				if (this.pallete[local8] == outlineColor) {
					local1 = local8;
					break;
				}
			}
			if (local1 == -1) {
				local1 = this.pallete.length;
				@Pc(38) int[] local38 = new int[this.pallete.length + 1];
				Static459.copy(this.pallete, 0, local38, 0, this.pallete.length);
				this.pallete = local38;
				local38[local1] = outlineColor;
			}
		} else {
			local8 = Integer.MAX_VALUE;
			local63 = outlineColor >> 16 & 0xFF;
			local69 = outlineColor >> 8 & 0xFF;
			@Pc(73) int local73 = outlineColor & 0xFF;
			for (@Pc(75) int local75 = 0; local75 < this.pallete.length; local75++) {
				@Pc(81) int local81 = this.pallete[local75];
				@Pc(87) int local87 = local81 >> 16 & 0xFF;
				@Pc(93) int local93 = local81 >> 8 & 0xFF;
				@Pc(97) int local97 = local81 & 0xFF;
				@Pc(101) int local101 = local63 - local87;
				if (local101 < 0) {
					local101 = -local101;
				}
				@Pc(110) int local110 = local69 - local93;
				if (local110 < 0) {
					local110 = -local110;
				}
				@Pc(119) int local119 = local73 - local97;
				if (local119 < 0) {
					local119 = -local119;
				}
				@Pc(130) int local130 = local101 + local110 + local119;
				if (local130 < local8) {
					local8 = local130;
					local1 = local75;
				}
			}
		}
		for (local8 = this.height - 1; local8 > 0; local8--) {
			local63 = local8 * this.width;
			for (local69 = this.width - 1; local69 > 0; local69--) {
				if (this.pallete[this.pixels[local69 + local63] & 0xFF] == 0 && this.pallete[this.pixels[local69 + local63 - this.width - 1] & 0xFF] != 0) {
					this.pixels[local69 + local63] = (byte) local1;
				}
			}
		}
	}

	@OriginalMember(owner = "client!bb", name = "a", descriptor = "()V")
	public void flipHorizontal() {
		@Pc(2) byte[] local2 = this.pixels;
		@Pc(29) byte local29;
		@Pc(10) int local10;
		@Pc(16) int local16;
		@Pc(23) int local23;
		if (this.alpha == null) {
			for (local10 = this.height - 1; local10 >= 0; local10--) {
				local16 = local10 * this.width;
				local23 = (local10 + 1) * this.width;
				while (local16 < local23) {
					local23--;
					local29 = local2[local16];
					local2[local16] = local2[local23];
					local2[local23] = local29;
					local16++;
				}
			}
		} else {
			@Pc(50) byte[] local50 = this.alpha;
			for (local16 = this.height - 1; local16 >= 0; local16--) {
				local23 = local16 * this.width;
				@Pc(68) int local68 = (local16 + 1) * this.width;
				while (local23 < local68) {
					local68--;
					local29 = local2[local23];
					local2[local23] = local2[local68];
					local2[local68] = local29;
					local29 = local50[local23];
					local50[local23] = local50[local68];
					local50[local68] = local29;
					local23++;
				}
			}
		}
		local10 = this.minX;
		this.minX = this.borderX;
		this.borderX = local10;
	}

	@OriginalMember(owner = "client!bb", name = "b", descriptor = "(I)V")
	public void drawThickBorder(@OriginalArg(0) int color) {
		@Pc(1) int local1 = -1;
		@Pc(8) int local8;
		@Pc(69) int local69;
		@Pc(73) int local73;
		@Pc(75) int local75;
		if (this.pallete.length < 255) {
			for (local8 = 0; local8 < this.pallete.length; local8++) {
				if (this.pallete[local8] == color) {
					local1 = local8;
					break;
				}
			}
			if (local1 == -1) {
				local1 = this.pallete.length;
				@Pc(38) int[] local38 = new int[this.pallete.length + 1];
				Static459.copy(this.pallete, 0, local38, 0, this.pallete.length);
				this.pallete = local38;
				local38[local1] = color;
			}
		} else {
			local8 = Integer.MAX_VALUE;
			@Pc(63) int local63 = color >> 16 & 0xFF;
			local69 = color >> 8 & 0xFF;
			local73 = color & 0xFF;
			for (local75 = 0; local75 < this.pallete.length; local75++) {
				@Pc(81) int local81 = this.pallete[local75];
				@Pc(87) int local87 = local81 >> 16 & 0xFF;
				@Pc(93) int local93 = local81 >> 8 & 0xFF;
				@Pc(97) int local97 = local81 & 0xFF;
				@Pc(101) int local101 = local63 - local87;
				if (local101 < 0) {
					local101 = -local101;
				}
				@Pc(110) int local110 = local69 - local93;
				if (local110 < 0) {
					local110 = -local110;
				}
				@Pc(119) int local119 = local73 - local97;
				if (local119 < 0) {
					local119 = -local119;
				}
				@Pc(130) int local130 = local101 + local110 + local119;
				if (local130 < local8) {
					local8 = local130;
					local1 = local75;
				}
			}
		}
		local8 = 0;
		@Pc(152) byte[] local152 = new byte[this.width * this.height];
		for (local69 = 0; local69 < this.height; local69++) {
			for (local73 = 0; local73 < this.width; local73++) {
				local75 = this.pixels[local8] & 0xFF;
				if (this.pallete[local75] == 0) {
					if (local73 > 0 && this.pallete[this.pixels[local8 - 1] & 0xFF] != 0) {
						local75 = local1;
					} else if (local69 > 0 && this.pallete[this.pixels[local8 - this.width] & 0xFF] != 0) {
						local75 = local1;
					} else if (local73 < this.width - 1 && this.pallete[this.pixels[local8 + 1] & 0xFF] != 0) {
						local75 = local1;
					} else if (local69 < this.height - 1 && this.pallete[this.pixels[local8 + this.width] & 0xFF] != 0) {
						local75 = local1;
					}
				}
				local152[local8++] = (byte) local75;
			}
		}
		this.pixels = local152;
	}

	@OriginalMember(owner = "client!bb", name = "b", descriptor = "()[I")
	public int[] getPixels() {
		@Pc(2) int local2 = this.widthSum();
		@Pc(8) int[] local8 = new int[local2 * this.heightSum()];
		@Pc(13) int local13;
		@Pc(19) int local19;
		@Pc(29) int local29;
		@Pc(31) int local31;
		if (this.alpha == null) {
			for (local13 = 0; local13 < this.height; local13++) {
				local19 = local13 * this.width;
				local29 = this.minX + (local13 + this.minY) * local2;
				for (local31 = 0; local31 < this.width; local31++) {
					@Pc(96) int local96 = this.pallete[this.pixels[local19++] & 0xFF];
					if (local96 == 0) {
						local8[local29++] = 0;
					} else {
						local8[local29++] = local96 | 0xFF000000;
					}
				}
			}
		} else {
			for (local13 = 0; local13 < this.height; local13++) {
				local19 = local13 * this.width;
				local29 = this.minX + (local13 + this.minY) * local2;
				for (local31 = 0; local31 < this.width; local31++) {
					local8[local29++] = this.alpha[local19] << 24 | this.pallete[this.pixels[local19] & 0xFF];
					local19++;
				}
			}
		}
		return local8;
	}

	@OriginalMember(owner = "client!bb", name = "c", descriptor = "()V")
	public void rotate90DegLeft() {
		@Pc(6) byte[] local6 = new byte[this.width * this.height];
		@Pc(8) int local8 = 0;
		@Pc(13) int local13;
		@Pc(19) int local19;
		if (this.alpha == null) {
			for (local13 = 0; local13 < this.width; local13++) {
				for (local19 = this.height - 1; local19 >= 0; local19--) {
					local6[local8++] = this.pixels[local13 + local19 * this.width];
				}
			}
			this.pixels = local6;
		} else {
			@Pc(52) byte[] local52 = new byte[this.width * this.height];
			for (local19 = 0; local19 < this.width; local19++) {
				for (@Pc(60) int local60 = this.height - 1; local60 >= 0; local60--) {
					local6[local8] = this.pixels[local19 + local60 * this.width];
					local52[local8++] = this.alpha[local19 + local60 * this.width];
				}
			}
			this.pixels = local6;
			this.alpha = local52;
		}
		local13 = this.minY;
		this.minY = this.minX;
		this.minX = this.borderY;
		this.borderY = this.borderX;
		this.borderX = this.minY;
		local13 = this.height;
		this.height = this.width;
		this.width = local13;
	}

	@OriginalMember(owner = "client!bb", name = "d", descriptor = "()V")
	public void flipVertical() {
		@Pc(2) byte[] local2 = this.pixels;
		@Pc(37) byte local37;
		@Pc(12) int local12;
		@Pc(18) int local18;
		@Pc(28) int local28;
		@Pc(32) int local32;
		if (this.alpha == null) {
			for (local12 = (this.height >> 1) - 1; local12 >= 0; local12--) {
				local18 = local12 * this.width;
				local28 = (this.height - local12 - 1) * this.width;
				for (local32 = -this.width; local32 < 0; local32++) {
					local37 = local2[local18];
					local2[local18] = local2[local28];
					local2[local28] = local37;
					local18++;
					local28++;
				}
			}
		} else {
			@Pc(59) byte[] local59 = this.alpha;
			for (local18 = (this.height >> 1) - 1; local18 >= 0; local18--) {
				local28 = local18 * this.width;
				local32 = (this.height - local18 - 1) * this.width;
				for (@Pc(86) int local86 = -this.width; local86 < 0; local86++) {
					local37 = local2[local28];
					local2[local28] = local2[local32];
					local2[local32] = local37;
					local37 = local59[local28];
					local59[local28] = local59[local32];
					local59[local32] = local37;
					local28++;
					local32++;
				}
			}
		}
		local12 = this.minY;
		this.minY = this.borderY;
		this.borderY = local12;
	}

	@OriginalMember(owner = "client!bb", name = "e", descriptor = "()V")
	public void expandToFullSize() {
		@Pc(2) int local2 = this.widthSum();
		@Pc(5) int local5 = this.heightSum();
		if (this.width == local2 && this.height == local5) {
			return;
		}
		@Pc(19) byte[] local19 = new byte[local2 * local5];
		@Pc(29) int local29;
		@Pc(35) int local35;
		@Pc(45) int local45;
		if (this.alpha == null) {
			for (@Pc(80) int local80 = 0; local80 < this.height; local80++) {
				local29 = local80 * this.width;
				local35 = (local80 + this.minY) * local2 + this.minX;
				for (local45 = 0; local45 < this.width; local45++) {
					local19[local35++] = this.pixels[local29++];
				}
			}
		} else {
			@Pc(27) byte[] local27 = new byte[local2 * local5];
			for (local29 = 0; local29 < this.height; local29++) {
				local35 = local29 * this.width;
				local45 = (local29 + this.minY) * local2 + this.minX;
				for (@Pc(47) int local47 = 0; local47 < this.width; local47++) {
					local19[local45] = this.pixels[local35];
					local27[local45++] = this.alpha[local35++];
				}
			}
			this.alpha = local27;
		}
		this.minX = this.borderX = this.minY = this.borderY = 0;
		this.width = local2;
		this.height = local5;
		this.pixels = local19;
	}

	@OriginalMember(owner = "client!bb", name = "f", descriptor = "()I")
	public int widthSum() {
		return this.width + this.minX + this.borderX;
	}

	@OriginalMember(owner = "client!bb", name = "g", descriptor = "()I")
	public int heightSum() {
		return this.height + this.minY + this.borderY;
	}

	@OriginalMember(owner = "client!bb", name = "c", descriptor = "(I)V")
	public void drawSmallBorder(@OriginalArg(0) int borderThickness) {
		@Pc(2) int local2 = this.widthSum();
		@Pc(5) int local5 = this.heightSum();
		if (this.width == local2 && this.height == local5) {
			return;
		}
		@Pc(16) int local16 = borderThickness;
		if (borderThickness > this.minX) {
			local16 = this.minX;
		}
		@Pc(25) int local25 = borderThickness;
		if (borderThickness + this.minX + this.width > local2) {
			local25 = local2 - this.minX - this.width;
		}
		@Pc(44) int local44 = borderThickness;
		if (borderThickness > this.minY) {
			local44 = this.minY;
		}
		@Pc(53) int local53 = borderThickness;
		if (borderThickness + this.minY + this.height > local5) {
			local53 = local5 - this.minY - this.height;
		}
		@Pc(77) int local77 = this.width + local16 + local25;
		@Pc(84) int local84 = this.height + local44 + local53;
		@Pc(89) byte[] local89 = new byte[local77 * local84];
		@Pc(100) int local100;
		@Pc(108) int local108;
		@Pc(110) int local110;
		if (this.alpha == null) {
			for (@Pc(94) int local94 = 0; local94 < this.height; local94++) {
				local100 = local94 * this.width;
				local108 = (local94 + local44) * local77 + local16;
				for (local110 = 0; local110 < this.width; local110++) {
					local89[local108++] = this.pixels[local100++];
				}
			}
		} else {
			@Pc(136) byte[] local136 = new byte[local77 * local84];
			for (local100 = 0; local100 < this.height; local100++) {
				local108 = local100 * this.width;
				local110 = (local100 + local44) * local77 + local16;
				for (@Pc(154) int local154 = 0; local154 < this.width; local154++) {
					local136[local110] = this.alpha[local108];
					local89[local110++] = this.pixels[local108++];
				}
			}
			this.alpha = local136;
		}
		this.minX -= local16;
		this.minY -= local44;
		this.borderX -= local25;
		this.borderY -= local53;
		this.width = local77;
		this.height = local84;
		this.pixels = local89;
	}

@OriginalMember(owner = "client!bb", name = "a", descriptor = "([B)[Lclient!bb;")
	private static SpriteType[] load(@OriginalArg(0) byte[] data) {
		@Pc(4) Packet local4 = new Packet(data);
		local4.currentPosition = data.length - 2;
		@Pc(14) int local14 = local4.method2536();
		@Pc(17) SpriteType[] local17 = new SpriteType[local14];
		for (@Pc(19) int local19 = 0; local19 < local14; local19++) {
			local17[local19] = new SpriteType();
		}
		local4.currentPosition = data.length - local14 * 8 - 7;
		@Pc(44) int local44 = local4.method2536();
		@Pc(48) int local48 = local4.method2536();
		@Pc(56) int local56 = (local4.method2490() & 0xFF) + 1;
		for (@Pc(58) int local58 = 0; local58 < local14; local58++) {
			local17[local58].minX = local4.method2536();
		}
		for (@Pc(72) int local72 = 0; local72 < local14; local72++) {
			local17[local72].minY = local4.method2536();
		}
		for (@Pc(86) int local86 = 0; local86 < local14; local86++) {
			local17[local86].width = local4.method2536();
		}
		for (@Pc(100) int local100 = 0; local100 < local14; local100++) {
			local17[local100].height = local4.method2536();
		}
		for (@Pc(114) int local114 = 0; local114 < local14; local114++) {
			@Pc(119) SpriteType local119 = local17[local114];
			local119.borderX = local44 - local119.width - local119.minX;
			local119.borderY = local48 - local119.height - local119.minY;
		}
		local4.currentPosition = data.length - local14 * 8 - (local56 - 1) * 3 - 7;
		@Pc(160) int[] local160 = new int[local56];
		for (@Pc(162) int local162 = 1; local162 < local56; local162++) {
			local160[local162] = local4.method2493();
			if (local160[local162] == 0) {
				local160[local162] = 1;
			}
		}
		for (@Pc(183) int local183 = 0; local183 < local14; local183++) {
			local17[local183].pallete = local160;
		}
		local4.currentPosition = 0;
		for (@Pc(198) int local198 = 0; local198 < local14; local198++) {
			@Pc(203) SpriteType local203 = local17[local198];
			@Pc(209) int local209 = local203.width * local203.height;
			local203.pixels = new byte[local209];
			@Pc(217) int local217 = local4.method2490();
			@Pc(245) int local245;
			if ((local217 & 0x2) == 0) {
				@Pc(227) int local227;
				if ((local217 & 0x1) == 0) {
					for (local227 = 0; local227 < local209; local227++) {
						local203.pixels[local227] = local4.method2542();
					}
				} else {
					for (local227 = 0; local227 < local203.width; local227++) {
						for (local245 = 0; local245 < local203.height; local245++) {
							local203.pixels[local227 + local245 * local203.width] = local4.method2542();
						}
					}
				}
			} else {
				@Pc(271) boolean local271 = false;
				local203.alpha = new byte[local209];
				@Pc(295) int local295;
				if ((local217 & 0x1) == 0) {
					for (local245 = 0; local245 < local209; local245++) {
						local203.pixels[local245] = local4.method2542();
					}
					for (local295 = 0; local295 < local209; local295++) {
						@Pc(305) byte local305 = local203.alpha[local295] = local4.method2542();
						local271 |= local305 != -1;
					}
				} else {
					for (local245 = 0; local245 < local203.width; local245++) {
						for (local295 = 0; local295 < local203.height; local295++) {
							local203.pixels[local245 + local295 * local203.width] = local4.method2542();
						}
					}
					for (local295 = 0; local295 < local203.width; local295++) {
						for (@Pc(352) int local352 = 0; local352 < local203.height; local352++) {
							@Pc(367) byte local367 = local203.alpha[local295 + local352 * local203.width] = local4.method2542();
							local271 |= local367 != -1;
						}
					}
				}
				if (!local271) {
					local203.alpha = null;
				}
			}
		}
		return local17;
	}
@OriginalMember(owner = "client!bb", name = "a", descriptor = "(Lclient!fs;II)Lclient!bb;")
	public static SpriteType listSingle(@OriginalArg(0) Js5ResourceProvider resource, @OriginalArg(1) int archiveId, @OriginalArg(2) int fileId) {
		@Pc(5) byte[] local5 = resource.method2104(fileId, archiveId);
		return local5 == null ? null : load(local5)[0];
	}
@OriginalMember(owner = "client!bb", name = "a", descriptor = "(Lclient!fs;I)[Lclient!bb;")
	public static SpriteType[] method323(@OriginalArg(0) Js5ResourceProvider arg0, @OriginalArg(1) int arg1) {
		@Pc(4) byte[] local4 = arg0.method2122(arg1);
		return local4 == null ? null : load(local4);
	}
@OriginalMember(owner = "client!bb", name = "b", descriptor = "(Lclient!fs;II)[Lclient!bb;")
	public static SpriteType[] decodeGroup(@OriginalArg(0) Js5ResourceProvider resource, @OriginalArg(1) int archive) {
		@Pc(5) byte[] local5 = resource.method2104(0, archive);
		return local5 == null ? null : load(local5);
	}
@OriginalMember(owner = "client!bb", name = "b", descriptor = "(Lclient!fs;I)Lclient!bb;")
	public static SpriteType method328(@OriginalArg(0) Js5ResourceProvider arg0, @OriginalArg(1) int arg1) {
		@Pc(4) byte[] local4 = arg0.method2122(arg1);
		return local4 == null ? null : load(local4)[0];
	}
}
