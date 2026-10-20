package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.JavaToolkit;
import com.jagex.game.runetek5.SpriteSM;
import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;

@OriginalClass("client!ea")
public final class Class57_Sub1_Sub1 extends SpriteSM {

	@OriginalMember(owner = "client!ea", name = "E", descriptor = "[I")
	private final int[] pixels;

	@OriginalMember(owner = "client!ea", name = "<init>", descriptor = "(Lclient!bc;[IIIII)V")
	public Class57_Sub1_Sub1(@OriginalArg(0) JavaToolkit javaToolkit, @OriginalArg(1) int[] srcPixels, @OriginalArg(2) int srcOffset, @OriginalArg(3) int srcStride, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5) {
		super(javaToolkit, arg4, arg5);
		this.pixels = new int[arg4 * arg5];
		@Pc(15) int local15 = srcStride - super.imageWidth;
		@Pc(17) int local17 = 0;
		for (@Pc(19) int local19 = 0; local19 < arg5; local19++) {
			for (@Pc(22) int local22 = 0; local22 < arg4; local22++) {
				@Pc(28) int local28 = srcPixels[srcOffset++];
				if (local28 >>> 24 == 255) {
					@Pc(37) int local37 = local28 & 0xFFFFFF;
					if (local37 == 0) {
						this.pixels[local17++] = 1;
					} else {
						this.pixels[local17++] = local37;
					}
				} else {
					this.pixels[local17++] = 0;
				}
			}
			srcOffset += local15;
		}
	}

	@OriginalMember(owner = "client!ea", name = "<init>", descriptor = "(Lclient!bc;[III)V")
	public Class57_Sub1_Sub1(@OriginalArg(0) JavaToolkit javaToolkit, @OriginalArg(1) int[] srcPixels, @OriginalArg(2) int width, @OriginalArg(3) int height) {
		super(javaToolkit, width, height);
		this.pixels = srcPixels;
	}

	@OriginalMember(owner = "client!ea", name = "s", descriptor = "(IIIIIII)V")
	@Override
	public void renderImpl(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int op, @OriginalArg(5) int colour, @OriginalArg(6) int mode) {
		if (super.toolkit.stopped()) {
			throw new IllegalStateException();
		} else if (width > 0 && height > 0) {
			@Pc(14) int local14 = 0;
			@Pc(16) int local16 = 0;
			@Pc(20) int local20 = super.toolkit.anInt430;
			@Pc(29) int local29 = super.leftMargin + super.imageWidth + super.trimRight;
			@Pc(38) int local38 = super.topMargin + super.imageHeight + super.trimBottom;
			@Pc(44) int local44 = (local29 << 16) / width;
			@Pc(50) int local50 = (local38 << 16) / height;
			@Pc(64) int local64;
			if (super.leftMargin > 0) {
				local64 = ((super.leftMargin << 16) + local44 - 1) / local44;
				x += local64;
				local14 += local64 * local44 - (super.leftMargin << 16);
			}
			if (super.topMargin > 0) {
				local64 = ((super.topMargin << 16) + local50 - 1) / local50;
				y += local64;
				local16 += local64 * local50 - (super.topMargin << 16);
			}
			if (super.imageWidth < local29) {
				width = ((super.imageWidth << 16) + local44 - local14 - 1) / local44;
			}
			if (super.imageHeight < local38) {
				height = ((super.imageHeight << 16) + local50 - local16 - 1) / local50;
			}
			local64 = x + y * local20;
			@Pc(152) int local152 = local20 - width;
			if (y + height > super.toolkit.clipY2) {
				height -= y + height - super.toolkit.clipY2;
			}
			@Pc(180) int local180;
			if (y < super.toolkit.clipY1) {
				local180 = super.toolkit.clipY1 - y;
				height -= local180;
				local64 += local180 * local20;
				local16 += local50 * local180;
			}
			if (x + width > super.toolkit.clipX2) {
				local180 = x + width - super.toolkit.clipX2;
				width -= local180;
				local152 += local180;
			}
			if (x < super.toolkit.clipX1) {
				local180 = super.toolkit.clipX1 - x;
				width -= local180;
				local64 += local180;
				local14 += local44 * local180;
				local152 += local180;
			}
			@Pc(252) int[] local252 = super.toolkit.surfaceRaster;
			@Pc(259) int local259;
			@Pc(262) int local262;
			@Pc(270) int local270;
			@Pc(273) int local273;
			@Pc(330) int local330;
			@Pc(338) int local338;
			@Pc(341) int local341;
			@Pc(351) int local351;
			@Pc(359) int local359;
			@Pc(367) int local367;
			@Pc(375) int local375;
			@Pc(525) int local525;
			if (mode != 0) {
				@Pc(837) int local837;
				@Pc(845) int local845;
				@Pc(857) int local857;
				if (mode == 1) {
					if (op == 1) {
						local259 = local14;
						for (local262 = -height; local262 < 0; local262++) {
							local270 = (local16 >> 16) * super.imageWidth;
							for (local273 = -width; local273 < 0; local273++) {
								local330 = this.pixels[(local14 >> 16) + local270];
								if (local330 == 0) {
									local64++;
								} else {
									local252[local64++] = local330;
								}
								local14 += local44;
							}
							local16 += local50;
							local14 = local259;
							local64 += local152;
						}
					} else if (op == 0) {
						local259 = local14;
						if ((colour & 0xFFFFFF) == 16777215) {
							local262 = colour >>> 24;
							local270 = 256 - local262;
							for (local273 = -height; local273 < 0; local273++) {
								local330 = (local16 >> 16) * super.imageWidth;
								for (local338 = -width; local338 < 0; local338++) {
									local341 = this.pixels[(local14 >> 16) + local330];
									if (local341 == 0) {
										local64++;
									} else {
										local351 = local252[local64];
										local252[local64++] = ((local341 & 0xFF00FF) * local262 + (local351 & 0xFF00FF) * local270 & 0xFF00FF00) + ((local341 & 0xFF00) * local262 + (local351 & 0xFF00) * local270 & 0xFF0000) >> 8;
									}
									local14 += local44;
								}
								local16 += local50;
								local14 = local259;
								local64 += local152;
							}
						} else {
							local262 = colour >> 16 & 0xFF;
							local270 = colour >> 8 & 0xFF;
							local273 = colour & 0xFF;
							local330 = colour >>> 24;
							local338 = 256 - local330;
							for (local341 = -height; local341 < 0; local341++) {
								local351 = (local16 >> 16) * super.imageWidth;
								for (local359 = -width; local359 < 0; local359++) {
									local367 = this.pixels[(local14 >> 16) + local351];
									if (local367 == 0) {
										local64++;
									} else if (local330 == 255) {
										local375 = (local367 & 0xFF0000) * local262 & 0xFF000000;
										local837 = (local367 & 0xFF00) * local270 & 0xFF0000;
										local845 = (local367 & 0xFF) * local273 & 0xFF00;
										local252[local64++] = (local375 | local837 | local845) >>> 8;
									} else {
										local375 = (local367 & 0xFF0000) * local262 & 0xFF000000;
										local837 = (local367 & 0xFF00) * local270 & 0xFF0000;
										local845 = (local367 & 0xFF) * local273 & 0xFF00;
										local367 = (local375 | local837 | local845) >>> 8;
										local857 = local252[local64];
										local252[local64++] = ((local367 & 0xFF00FF) * local330 + (local857 & 0xFF00FF) * local338 & 0xFF00FF00) + ((local367 & 0xFF00) * local330 + (local857 & 0xFF00) * local338 & 0xFF0000) >> 8;
									}
									local14 += local44;
								}
								local16 += local50;
								local14 = local259;
								local64 += local152;
							}
						}
					} else if (op == 3) {
						local259 = local14;
						local262 = colour >>> 24;
						local270 = 256 - local262;
						for (local273 = -height; local273 < 0; local273++) {
							local330 = (local16 >> 16) * super.imageWidth;
							for (local338 = -width; local338 < 0; local338++) {
								local341 = this.pixels[(local14 >> 16) + local330];
								local351 = local341 + colour;
								local359 = (local341 & 0xFF00FF) + (colour & 0xFF00FF);
								local367 = (local359 & 0x1000100) + (local351 - local359 & 0x10000);
								local367 = local351 - local367 | local367 - (local367 >>> 8);
								if (local341 == 0 && local262 != 255) {
									local341 = local367;
									local367 = local252[local64];
									local367 = ((local341 & 0xFF00FF) * local262 + (local367 & 0xFF00FF) * local270 & 0xFF00FF00) + ((local341 & 0xFF00) * local262 + (local367 & 0xFF00) * local270 & 0xFF0000) >> 8;
								}
								local252[local64++] = local367;
								local14 += local44;
							}
							local16 += local50;
							local14 = local259;
							local64 += local152;
						}
					} else if (op == 2) {
						local259 = colour >>> 24;
						local262 = 256 - local259;
						local270 = (colour & 0xFF00FF) * local262 & 0xFF00FF00;
						local273 = (colour & 0xFF00) * local262 & 0xFF0000;
						local525 = (local270 | local273) >>> 8;
						local330 = local14;
						for (local338 = -height; local338 < 0; local338++) {
							local341 = (local16 >> 16) * super.imageWidth;
							for (local351 = -width; local351 < 0; local351++) {
								local359 = this.pixels[(local14 >> 16) + local341];
								if (local359 == 0) {
									local64++;
								} else {
									local270 = (local359 & 0xFF00FF) * local259 & 0xFF00FF00;
									local273 = (local359 & 0xFF00) * local259 & 0xFF0000;
									local252[local64++] = ((local270 | local273) >>> 8) + local525;
								}
								local14 += local44;
							}
							local16 += local50;
							local14 = local330;
							local64 += local152;
						}
					} else {
						throw new IllegalArgumentException();
					}
				} else if (mode != 2) {
					throw new IllegalArgumentException();
				} else if (op == 1) {
					local259 = local14;
					for (local262 = -height; local262 < 0; local262++) {
						local270 = (local16 >> 16) * super.imageWidth;
						for (local273 = -width; local273 < 0; local273++) {
							local330 = this.pixels[(local14 >> 16) + local270];
							if (local330 == 0) {
								local64++;
							} else {
								local338 = local252[local64];
								local341 = local330 + local338;
								local351 = (local330 & 0xFF00FF) + (local338 & 0xFF00FF);
								local338 = (local351 & 0x1000100) + (local341 - local351 & 0x10000);
								local252[local64++] = local341 - local338 | local338 - (local338 >>> 8);
							}
							local14 += local44;
						}
						local16 += local50;
						local14 = local259;
						local64 += local152;
					}
				} else if (op == 0) {
					local259 = local14;
					local262 = colour >> 16 & 0xFF;
					local270 = colour >> 8 & 0xFF;
					local273 = colour & 0xFF;
					for (local330 = -height; local330 < 0; local330++) {
						local338 = (local16 >> 16) * super.imageWidth;
						for (local341 = -width; local341 < 0; local341++) {
							local351 = this.pixels[(local14 >> 16) + local338];
							if (local351 == 0) {
								local64++;
							} else {
								local359 = (local351 & 0xFF0000) * local262 & 0xFF000000;
								local367 = (local351 & 0xFF00) * local270 & 0xFF0000;
								local375 = (local351 & 0xFF) * local273 & 0xFF00;
								local351 = (local359 | local367 | local375) >>> 8;
								local837 = local252[local64];
								local845 = local351 + local837;
								local857 = (local351 & 0xFF00FF) + (local837 & 0xFF00FF);
								local837 = (local857 & 0x1000100) + (local845 - local857 & 0x10000);
								local252[local64++] = local845 - local837 | local837 - (local837 >>> 8);
							}
							local14 += local44;
						}
						local16 += local50;
						local14 = local259;
						local64 += local152;
					}
				} else if (op == 3) {
					local259 = local14;
					for (local262 = -height; local262 < 0; local262++) {
						local270 = (local16 >> 16) * super.imageWidth;
						for (local273 = -width; local273 < 0; local273++) {
							local330 = this.pixels[(local14 >> 16) + local270];
							local338 = local330 + colour;
							local341 = (local330 & 0xFF00FF) + (colour & 0xFF00FF);
							local351 = (local341 & 0x1000100) + (local338 - local341 & 0x10000);
							local330 = local338 - local351 | local351 - (local351 >>> 8);
							local351 = local252[local64];
							local338 = local330 + local351;
							local341 = (local330 & 0xFF00FF) + (local351 & 0xFF00FF);
							local351 = (local341 & 0x1000100) + (local338 - local341 & 0x10000);
							local252[local64++] = local338 - local351 | local351 - (local351 >>> 8);
							local14 += local44;
						}
						local16 += local50;
						local14 = local259;
						local64 += local152;
					}
				} else if (op == 2) {
					local259 = colour >>> 24;
					local262 = 256 - local259;
					local270 = (colour & 0xFF00FF) * local262 & 0xFF00FF00;
					local273 = (colour & 0xFF00) * local262 & 0xFF0000;
					local525 = (local270 | local273) >>> 8;
					local330 = local14;
					for (local338 = -height; local338 < 0; local338++) {
						local341 = (local16 >> 16) * super.imageWidth;
						for (local351 = -width; local351 < 0; local351++) {
							local359 = this.pixels[(local14 >> 16) + local341];
							if (local359 == 0) {
								local64++;
							} else {
								local270 = (local359 & 0xFF00FF) * local259 & 0xFF00FF00;
								local273 = (local359 & 0xFF00) * local259 & 0xFF0000;
								local359 = ((local270 | local273) >>> 8) + local525;
								local367 = local252[local64];
								local375 = local359 + local367;
								local837 = (local359 & 0xFF00FF) + (local367 & 0xFF00FF);
								@Pc(1668) int local1668 = (local837 & 0x1000100) + (local375 - local837 & 0x10000);
								local252[local64++] = local375 - local1668 | local1668 - (local1668 >>> 8);
							}
							local14 += local44;
						}
						local16 += local50;
						local14 = local330;
						local64 += local152;
					}
				} else {
					throw new IllegalArgumentException();
				}
			} else if (op == 1) {
				local259 = local14;
				for (local262 = -height; local262 < 0; local262++) {
					local270 = (local16 >> 16) * super.imageWidth;
					for (local273 = -width; local273 < 0; local273++) {
						local252[local64++] = this.pixels[(local14 >> 16) + local270];
						local14 += local44;
					}
					local16 += local50;
					local14 = local259;
					local64 += local152;
				}
			} else if (op == 0) {
				local259 = colour >> 16 & 0xFF;
				local262 = colour >> 8 & 0xFF;
				local270 = colour & 0xFF;
				local273 = local14;
				for (local330 = -height; local330 < 0; local330++) {
					local338 = (local16 >> 16) * super.imageWidth;
					for (local341 = -width; local341 < 0; local341++) {
						local351 = this.pixels[(local14 >> 16) + local338];
						local359 = (local351 & 0xFF0000) * local259 & 0xFF000000;
						local367 = (local351 & 0xFF00) * local262 & 0xFF0000;
						local375 = (local351 & 0xFF) * local270 & 0xFF00;
						local252[local64++] = (local359 | local367 | local375) >>> 8;
						local14 += local44;
					}
					local16 += local50;
					local14 = local273;
					local64 += local152;
				}
			} else if (op == 3) {
				local259 = local14;
				for (local262 = -height; local262 < 0; local262++) {
					local270 = (local16 >> 16) * super.imageWidth;
					for (local273 = -width; local273 < 0; local273++) {
						local330 = this.pixels[(local14 >> 16) + local270];
						local338 = local330 + colour;
						local341 = (local330 & 0xFF00FF) + (colour & 0xFF00FF);
						local351 = (local341 & 0x1000100) + (local338 - local341 & 0x10000);
						local252[local64++] = local338 - local351 | local351 - (local351 >>> 8);
						local14 += local44;
					}
					local16 += local50;
					local14 = local259;
					local64 += local152;
				}
			} else if (op == 2) {
				local259 = colour >>> 24;
				local262 = 256 - local259;
				local270 = (colour & 0xFF00FF) * local262 & 0xFF00FF00;
				local273 = (colour & 0xFF00) * local262 & 0xFF0000;
				local525 = (local270 | local273) >>> 8;
				local330 = local14;
				for (local338 = -height; local338 < 0; local338++) {
					local341 = (local16 >> 16) * super.imageWidth;
					for (local351 = -width; local351 < 0; local351++) {
						local359 = this.pixels[(local14 >> 16) + local341];
						local270 = (local359 & 0xFF00FF) * local259 & 0xFF00FF00;
						local273 = (local359 & 0xFF00) * local259 & 0xFF0000;
						local252[local64++] = ((local270 | local273) >>> 8) + local525;
						local14 += local44;
					}
					local16 += local50;
					local14 = local330;
					local64 += local152;
				}
			} else {
				throw new IllegalArgumentException();
			}
		}
	}

	@OriginalMember(owner = "client!ea", name = "t", descriptor = "(IIIIII)V")
	@Override
	public void captureScreenRegion(@OriginalArg(0) int dstX, @OriginalArg(1) int dstY, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int srcX, @OriginalArg(5) int srcY) {
		@Pc(3) int[] local3 = super.toolkit.surfaceRaster;
		for (@Pc(5) int local5 = 0; local5 < arg3; local5++) {
			@Pc(15) int local15 = (dstY + local5) * super.imageWidth + dstX;
			@Pc(25) int local25 = (srcY + local5) * super.toolkit.anInt430 + srcX;
			for (@Pc(27) int local27 = 0; local27 < arg2; local27++) {
				this.pixels[local15 + local27] = local3[local25 + local27];
			}
		}
	}

	@OriginalMember(owner = "client!ea", name = "a", descriptor = "(IILclient!ma;II)V")
	@Override
	public void drawWithSurface(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) ComponentSurface clippingMask, @OriginalArg(3) int surfaceOffsetX, @OriginalArg(4) int surfaceOffsetY) {
		if (super.toolkit.stopped()) {
			throw new IllegalStateException();
		}
		x += super.leftMargin;
		y += super.topMargin;
		@Pc(19) int local19 = 0;
		@Pc(23) int local23 = super.toolkit.anInt430;
		@Pc(26) int local26 = super.imageWidth;
		@Pc(29) int local29 = super.imageHeight;
		@Pc(33) int local33 = local23 - local26;
		@Pc(35) int local35 = 0;
		@Pc(41) int local41 = x + y * local23;
		@Pc(52) int local52;
		if (y < super.toolkit.clipY1) {
			local52 = super.toolkit.clipY1 - y;
			local29 -= local52;
			y = super.toolkit.clipY1;
			local19 = local52 * local26;
			local41 += local52 * local23;
		}
		if (y + local29 > super.toolkit.clipY2) {
			local29 -= y + local29 - super.toolkit.clipY2;
		}
		if (x < super.toolkit.clipX1) {
			local52 = super.toolkit.clipX1 - x;
			local26 -= local52;
			x = super.toolkit.clipX1;
			local19 += local52;
			local41 += local52;
			local35 = local52;
			local33 += local52;
		}
		if (x + local26 > super.toolkit.clipX2) {
			local52 = x + local26 - super.toolkit.clipX2;
			local26 -= local52;
			local35 += local52;
			local33 += local52;
		}
		if (local26 <= 0 || local29 <= 0) {
			return;
		}
		@Pc(159) Class145_Sub2 local159 = (Class145_Sub2) clippingMask;
		@Pc(162) int[] local162 = local159.anIntArray554;
		@Pc(165) int[] local165 = local159.anIntArray553;
		@Pc(169) int[] local169 = super.toolkit.surfaceRaster;
		@Pc(171) int local171 = y;
		if (surfaceOffsetY > y) {
			local171 = surfaceOffsetY;
			local41 += (surfaceOffsetY - y) * local23;
			local19 += (surfaceOffsetY - y) * super.imageWidth;
		}
		@Pc(210) int local210 = surfaceOffsetY + local162.length < y + local29 ? surfaceOffsetY + local162.length : y + local29;
		for (@Pc(212) int local212 = local171; local212 < local210; local212++) {
			@Pc(221) int local221 = local162[local212 - surfaceOffsetY] + surfaceOffsetX;
			@Pc(227) int local227 = local165[local212 - surfaceOffsetY];
			@Pc(229) int local229 = local26;
			@Pc(236) int local236;
			if (x > local221) {
				local236 = x - local221;
				if (local236 >= local227) {
					local19 += local26 + local35;
					local41 += local26 + local33;
					continue;
				}
				local227 -= local236;
			} else {
				local236 = local221 - x;
				if (local236 >= local26) {
					local19 += local26 + local35;
					local41 += local26 + local33;
					continue;
				}
				local19 += local236;
				local229 = local26 - local236;
				local41 += local236;
			}
			local236 = 0;
			if (local229 < local227) {
				local227 = local229;
			} else {
				local236 = local229 - local227;
			}
			for (@Pc(304) int local304 = -local227; local304 < 0; local304++) {
				@Pc(311) int local311 = this.pixels[local19++];
				if (local311 == 0) {
					local41++;
				} else {
					local169[local41++] = local311;
				}
			}
			local19 += local236 + local35;
			local41 += local236 + local33;
		}
	}

	@OriginalMember(owner = "client!ea", name = "r", descriptor = "(IIIII)V")
	@Override
	public void render(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int op, @OriginalArg(3) int color, @OriginalArg(4) int mode) {
		if (super.toolkit.stopped()) {
			throw new IllegalStateException();
		}
		@Pc(11) int local11 = super.toolkit.anInt430;
		x += super.leftMargin;
		y += super.topMargin;
		@Pc(27) int local27 = y * local11 + x;
		@Pc(29) int local29 = 0;
		@Pc(32) int local32 = super.imageHeight;
		@Pc(35) int local35 = super.imageWidth;
		@Pc(39) int local39 = local11 - local35;
		@Pc(41) int local41 = 0;
		@Pc(52) int local52;
		if (y < super.toolkit.clipY1) {
			local52 = super.toolkit.clipY1 - y;
			local32 -= local52;
			y = super.toolkit.clipY1;
			local29 += local52 * local35;
			local27 += local52 * local11;
		}
		if (y + local32 > super.toolkit.clipY2) {
			local32 -= y + local32 - super.toolkit.clipY2;
		}
		if (x < super.toolkit.clipX1) {
			local52 = super.toolkit.clipX1 - x;
			local35 -= local52;
			x = super.toolkit.clipX1;
			local29 += local52;
			local27 += local52;
			local41 += local52;
			local39 += local52;
		}
		if (x + local35 > super.toolkit.clipX2) {
			local52 = x + local35 - super.toolkit.clipX2;
			local35 -= local52;
			local41 += local52;
			local39 += local52;
		}
		if (local35 <= 0 || local32 <= 0) {
			return;
		}
		@Pc(160) int[] local160 = super.toolkit.surfaceRaster;
		@Pc(492) int local492;
		@Pc(499) int local499;
		@Pc(506) int local506;
		if (op == 1) {
			for (local492 = -local32; local492 < 0; local492++) {
				local499 = local27 + local35 - 3;
				while (local27 < local499) {
					local506 = this.pixels[local29++];
					if (local506 == 0) {
						local27++;
					} else {
						local160[local27++] = local506;
					}
					local506 = this.pixels[local29++];
					if (local506 == 0) {
						local27++;
					} else {
						local160[local27++] = local506;
					}
					local506 = this.pixels[local29++];
					if (local506 == 0) {
						local27++;
					} else {
						local160[local27++] = local506;
					}
					local506 = this.pixels[local29++];
					if (local506 == 0) {
						local27++;
					} else {
						local160[local27++] = local506;
					}
				}
				local499 += 3;
				while (local27 < local499) {
					local506 = this.pixels[local29++];
					if (local506 == 0) {
						local27++;
					} else {
						local160[local27++] = local506;
					}
				}
				local27 += local39;
				local29 += local41;
			}
			return;
		}
		@Pc(617) int local617;
		@Pc(624) int local624;
		@Pc(630) int local630;
		@Pc(711) int local711;
		@Pc(718) int local718;
		if (op == 0) {
			if ((color & 0xFFFFFF) == 16777215) {
				local492 = color >>> 24;
				local499 = 256 - local492;
				for (local506 = -local32; local506 < 0; local506++) {
					for (local617 = -local35; local617 < 0; local617++) {
						local624 = this.pixels[local29++];
						if (local624 == 0) {
							local27++;
						} else {
							local630 = local160[local27];
							local160[local27++] = ((local624 & 0xFF00FF) * local492 + (local630 & 0xFF00FF) * local499 & 0xFF00FF00) + ((local624 & 0xFF00) * local492 + (local630 & 0xFF00) * local499 & 0xFF0000) >> 8;
						}
					}
					local27 += local39;
					local29 += local41;
				}
			} else {
				local492 = color >> 16 & 0xFF;
				local499 = color >> 8 & 0xFF;
				local506 = color & 0xFF;
				local617 = color >>> 24;
				local624 = 256 - local617;
				for (local630 = -local32; local630 < 0; local630++) {
					for (local711 = -local35; local711 < 0; local711++) {
						local718 = this.pixels[local29++];
						if (local718 == 0) {
							local27++;
						} else {
							@Pc(731) int local731;
							@Pc(739) int local739;
							@Pc(747) int local747;
							if (local617 == 255) {
								local731 = (local718 & 0xFF0000) * local492 & 0xFF000000;
								local739 = (local718 & 0xFF00) * local499 & 0xFF0000;
								local747 = (local718 & 0xFF) * local506 & 0xFF00;
								local160[local27++] = (local731 | local739 | local747) >>> 8;
							} else {
								local731 = (local718 & 0xFF0000) * local492 & 0xFF000000;
								local739 = (local718 & 0xFF00) * local499 & 0xFF0000;
								local747 = (local718 & 0xFF) * local506 & 0xFF00;
								local718 = (local731 | local739 | local747) >>> 8;
								@Pc(759) int local759 = local160[local27];
								local160[local27++] = ((local718 & 0xFF00FF) * local617 + (local759 & 0xFF00FF) * local624 & 0xFF00FF00) + ((local718 & 0xFF00) * local617 + (local759 & 0xFF00) * local624 & 0xFF0000) >> 8;
							}
						}
					}
					local27 += local39;
					local29 += local41;
				}
			}
		} else if (op == 3) {
			local492 = color >>> 24;
			local499 = 256 - local492;
			for (local506 = -local32; local506 < 0; local506++) {
				for (local617 = -local35; local617 < 0; local617++) {
					local624 = this.pixels[local29++];
					local630 = local624 + color;
					local711 = (local624 & 0xFF00FF) + (color & 0xFF00FF);
					local718 = (local711 & 0x1000100) + (local630 - local711 & 0x10000);
					local718 = local630 - local718 | local718 - (local718 >>> 8);
					if (local624 == 0 && local492 != 255) {
						local624 = local718;
						local718 = local160[local27];
						local718 = ((local624 & 0xFF00FF) * local492 + (local718 & 0xFF00FF) * local499 & 0xFF00FF00) + ((local624 & 0xFF00) * local492 + (local718 & 0xFF00) * local499 & 0xFF0000) >> 8;
					}
					local160[local27++] = local718;
				}
				local27 += local39;
				local29 += local41;
			}
		} else if (op == 2) {
			local492 = color >>> 24;
			local499 = 256 - local492;
			local506 = (color & 0xFF00FF) * local499 & 0xFF00FF00;
			local617 = (color & 0xFF00) * local499 & 0xFF0000;
			@Pc(996) int local996 = (local506 | local617) >>> 8;
			for (local624 = -local32; local624 < 0; local624++) {
				for (local630 = -local35; local630 < 0; local630++) {
					local711 = this.pixels[local29++];
					if (local711 == 0) {
						local27++;
					} else {
						local506 = (local711 & 0xFF00FF) * local492 & 0xFF00FF00;
						local617 = (local711 & 0xFF00) * local492 & 0xFF0000;
						local160[local27++] = ((local506 | local617) >>> 8) + local996;
					}
				}
				local27 += local39;
				local29 += local41;
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	@OriginalMember(owner = "client!ea", name = "BA", descriptor = "(III)V")
	@Override
	public void copyAlpha(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int channel) {
		throw new IllegalStateException("Can't capture alpha into a java_sprite_24");
	}

	@OriginalMember(owner = "client!ea", name = "b", descriptor = "(II)V")
	@Override
	protected void blitParallelogram(@OriginalArg(0) int op) {
		@Pc(3) int[] local3 = super.toolkit.surfaceRaster;
		@Pc(852) int local852;
		@Pc(9) int local9;
		@Pc(12) int local12;
		@Pc(14) int local14;
		@Pc(16) int local16;
		@Pc(18) int local18;
		@Pc(48) int local48;
		@Pc(51) int local51;
		@Pc(209) int local209;
		@Pc(234) int local234;
		@Pc(238) int local238;
		@Pc(242) int local242;
		@Pc(314) int local314;
		if (SpriteSM.anInt7153 == 0) {
			if (SpriteSM.anInt7164 == 0) {
				local9 = SpriteSM.anInt7141;
				while (local9 < 0) {
					local12 = SpriteSM.pixelOffset;
					local14 = SpriteSM.uBase;
					local16 = SpriteSM.vBase;
					local18 = SpriteSM.anInt7151;
					if (local14 >= 0 && local16 >= 0 && local14 - (super.imageWidth << 12) < 0 && local16 - (super.imageHeight << 12) < 0) {
						while (local18 < 0) {
							local48 = (local16 >> 12) * super.imageWidth + (local14 >> 12);
							local51 = local12++;
							if (op == 1) {
								local209 = this.pixels[local48];
								if (local209 != 0) {
									local3[local51] = local209;
								}
							} else if (op == 0) {
								local209 = this.pixels[local48];
								if (local209 != 0) {
									if ((SpriteSM.colour & 0xFFFFFF) == 16777215) {
										local234 = SpriteSM.colour >>> 24;
										local238 = 256 - local234;
										local242 = local3[local51];
										local3[local51] = ((local209 & 0xFF00FF) * local234 + (local242 & 0xFF00FF) * local238 & 0xFF00FF00) + ((local209 & 0xFF00) * local234 + (local242 & 0xFF00) * local238 & 0xFF0000) >> 8;
									} else if (SpriteSM.alpha == 255) {
										local234 = (local209 & 0xFF0000) * SpriteSM.red & 0xFF000000;
										local238 = (local209 & 0xFF00) * SpriteSM.green & 0xFF0000;
										local242 = (local209 & 0xFF) * SpriteSM.blue & 0xFF00;
										local3[local51] = (local234 | local238 | local242) >>> 8;
									} else {
										local234 = (local209 & 0xFF0000) * SpriteSM.red & 0xFF000000;
										local238 = (local209 & 0xFF00) * SpriteSM.green & 0xFF0000;
										local242 = (local209 & 0xFF) * SpriteSM.blue & 0xFF00;
										local209 = (local234 | local238 | local242) >>> 8;
										local314 = local3[local51];
										local3[local51] = ((local209 & 0xFF00FF) * SpriteSM.alpha + (local314 & 0xFF00FF) * SpriteSM.invAlpha & 0xFF00FF00) + ((local209 & 0xFF00) * SpriteSM.alpha + (local314 & 0xFF00) * SpriteSM.invAlpha & 0xFF0000) >> 8;
									}
								}
							} else if (op == 3) {
								local209 = this.pixels[local48];
								local234 = SpriteSM.colour;
								local238 = local209 + local234;
								local242 = (local209 & 0xFF00FF) + (local234 & 0xFF00FF);
								local314 = (local242 & 0x1000100) + (local238 - local242 & 0x10000);
								local314 = local238 - local314 | local314 - (local314 >>> 8);
								if (local209 == 0 && SpriteSM.alpha != 255) {
									local209 = local314;
									local314 = local3[local51];
									local314 = ((local209 & 0xFF00FF) * SpriteSM.alpha + (local314 & 0xFF00FF) * SpriteSM.invAlpha & 0xFF00FF00) + ((local209 & 0xFF00) * SpriteSM.alpha + (local314 & 0xFF00) * SpriteSM.invAlpha & 0xFF0000) >> 8;
								}
								local3[local51] = local314;
							} else if (op == 2) {
								local209 = this.pixels[local48];
								if (local209 != 0) {
									local234 = (local209 & 0xFF00FF) * SpriteSM.alpha & 0xFF00FF00;
									local238 = (local209 & 0xFF00) * SpriteSM.alpha & 0xFF0000;
									local3[local51++] = ((local234 | local238) >>> 8) + SpriteSM.lerpColour;
								}
							} else {
								throw new IllegalArgumentException();
							}
							local18++;
						}
					}
					local9++;
					SpriteSM.pixelOffset += SpriteSM.viewportStride;
				}
			} else if (SpriteSM.anInt7164 < 0) {
				local9 = SpriteSM.anInt7141;
				while (local9 < 0) {
					local12 = SpriteSM.pixelOffset;
					local14 = SpriteSM.uBase;
					local16 = SpriteSM.vBase + SpriteSM.anInt7142;
					local18 = SpriteSM.anInt7151;
					if (local14 >= 0 && local14 - (super.imageWidth << 12) < 0) {
						@Pc(845) int local845;
						if ((local845 = local16 - (super.imageHeight << 12)) >= 0) {
							local852 = (SpriteSM.anInt7164 - local845) / SpriteSM.anInt7164;
							local18 += local852;
							local16 += SpriteSM.anInt7164 * local852;
							local12 += local852;
						}
						@Pc(873) int local873;
						if ((local873 = (local16 - SpriteSM.anInt7164) / SpriteSM.anInt7164) > local18) {
							local18 = local873;
						}
						while (local18 < 0) {
							local48 = (local16 >> 12) * super.imageWidth + (local14 >> 12);
							local51 = local12++;
							if (op == 1) {
								local209 = this.pixels[local48];
								if (local209 != 0) {
									local3[local51] = local209;
								}
							} else if (op == 0) {
								local209 = this.pixels[local48];
								if (local209 != 0) {
									if ((SpriteSM.colour & 0xFFFFFF) == 16777215) {
										local234 = SpriteSM.colour >>> 24;
										local238 = 256 - local234;
										local242 = local3[local51];
										local3[local51] = ((local209 & 0xFF00FF) * local234 + (local242 & 0xFF00FF) * local238 & 0xFF00FF00) + ((local209 & 0xFF00) * local234 + (local242 & 0xFF00) * local238 & 0xFF0000) >> 8;
									} else if (SpriteSM.alpha == 255) {
										local234 = (local209 & 0xFF0000) * SpriteSM.red & 0xFF000000;
										local238 = (local209 & 0xFF00) * SpriteSM.green & 0xFF0000;
										local242 = (local209 & 0xFF) * SpriteSM.blue & 0xFF00;
										local3[local51] = (local234 | local238 | local242) >>> 8;
									} else {
										local234 = (local209 & 0xFF0000) * SpriteSM.red & 0xFF000000;
										local238 = (local209 & 0xFF00) * SpriteSM.green & 0xFF0000;
										local242 = (local209 & 0xFF) * SpriteSM.blue & 0xFF00;
										local209 = (local234 | local238 | local242) >>> 8;
										local314 = local3[local51];
										local3[local51] = ((local209 & 0xFF00FF) * SpriteSM.alpha + (local314 & 0xFF00FF) * SpriteSM.invAlpha & 0xFF00FF00) + ((local209 & 0xFF00) * SpriteSM.alpha + (local314 & 0xFF00) * SpriteSM.invAlpha & 0xFF0000) >> 8;
									}
								}
							} else if (op == 3) {
								local209 = this.pixels[local48];
								local234 = SpriteSM.colour;
								local238 = local209 + local234;
								local242 = (local209 & 0xFF00FF) + (local234 & 0xFF00FF);
								local314 = (local242 & 0x1000100) + (local238 - local242 & 0x10000);
								local314 = local238 - local314 | local314 - (local314 >>> 8);
								if (local209 == 0 && SpriteSM.alpha != 255) {
									local209 = local314;
									local314 = local3[local51];
									local314 = ((local209 & 0xFF00FF) * SpriteSM.alpha + (local314 & 0xFF00FF) * SpriteSM.invAlpha & 0xFF00FF00) + ((local209 & 0xFF00) * SpriteSM.alpha + (local314 & 0xFF00) * SpriteSM.invAlpha & 0xFF0000) >> 8;
								}
								local3[local51] = local314;
							} else if (op == 2) {
								local209 = this.pixels[local48];
								if (local209 != 0) {
									local234 = (local209 & 0xFF00FF) * SpriteSM.alpha & 0xFF00FF00;
									local238 = (local209 & 0xFF00) * SpriteSM.alpha & 0xFF0000;
									local3[local51++] = ((local234 | local238) >>> 8) + SpriteSM.lerpColour;
								}
							} else {
								throw new IllegalArgumentException();
							}
							local16 += SpriteSM.anInt7164;
							local18++;
						}
					}
					local9++;
					SpriteSM.uBase += SpriteSM.anInt7169;
					SpriteSM.pixelOffset += SpriteSM.viewportStride;
				}
			} else {
				local9 = SpriteSM.anInt7141;
				while (local9 < 0) {
					local12 = SpriteSM.pixelOffset;
					local14 = SpriteSM.uBase;
					local16 = SpriteSM.vBase + SpriteSM.anInt7142;
					local18 = SpriteSM.anInt7151;
					if (local14 >= 0 && local14 - (super.imageWidth << 12) < 0) {
						if (local16 < 0) {
							local852 = (SpriteSM.anInt7164 - local16 - 1) / SpriteSM.anInt7164;
							local18 += local852;
							local16 += SpriteSM.anInt7164 * local852;
							local12 += local852;
						}
						@Pc(1722) int local1722;
						if ((local1722 = (local16 + 1 - (super.imageHeight << 12) - SpriteSM.anInt7164) / SpriteSM.anInt7164) > local18) {
							local18 = local1722;
						}
						while (local18 < 0) {
							local48 = (local16 >> 12) * super.imageWidth + (local14 >> 12);
							local51 = local12++;
							if (op == 1) {
								local209 = this.pixels[local48];
								if (local209 != 0) {
									local3[local51] = local209;
								}
							} else if (op == 0) {
								local209 = this.pixels[local48];
								if (local209 != 0) {
									if ((SpriteSM.colour & 0xFFFFFF) == 16777215) {
										local234 = SpriteSM.colour >>> 24;
										local238 = 256 - local234;
										local242 = local3[local51];
										local3[local51] = ((local209 & 0xFF00FF) * local234 + (local242 & 0xFF00FF) * local238 & 0xFF00FF00) + ((local209 & 0xFF00) * local234 + (local242 & 0xFF00) * local238 & 0xFF0000) >> 8;
									} else if (SpriteSM.alpha == 255) {
										local234 = (local209 & 0xFF0000) * SpriteSM.red & 0xFF000000;
										local238 = (local209 & 0xFF00) * SpriteSM.green & 0xFF0000;
										local242 = (local209 & 0xFF) * SpriteSM.blue & 0xFF00;
										local3[local51] = (local234 | local238 | local242) >>> 8;
									} else {
										local234 = (local209 & 0xFF0000) * SpriteSM.red & 0xFF000000;
										local238 = (local209 & 0xFF00) * SpriteSM.green & 0xFF0000;
										local242 = (local209 & 0xFF) * SpriteSM.blue & 0xFF00;
										local209 = (local234 | local238 | local242) >>> 8;
										local314 = local3[local51];
										local3[local51] = ((local209 & 0xFF00FF) * SpriteSM.alpha + (local314 & 0xFF00FF) * SpriteSM.invAlpha & 0xFF00FF00) + ((local209 & 0xFF00) * SpriteSM.alpha + (local314 & 0xFF00) * SpriteSM.invAlpha & 0xFF0000) >> 8;
									}
								}
							} else if (op == 3) {
								local209 = this.pixels[local48];
								local234 = SpriteSM.colour;
								local238 = local209 + local234;
								local242 = (local209 & 0xFF00FF) + (local234 & 0xFF00FF);
								local314 = (local242 & 0x1000100) + (local238 - local242 & 0x10000);
								local314 = local238 - local314 | local314 - (local314 >>> 8);
								if (local209 == 0 && SpriteSM.alpha != 255) {
									local209 = local314;
									local314 = local3[local51];
									local314 = ((local209 & 0xFF00FF) * SpriteSM.alpha + (local314 & 0xFF00FF) * SpriteSM.invAlpha & 0xFF00FF00) + ((local209 & 0xFF00) * SpriteSM.alpha + (local314 & 0xFF00) * SpriteSM.invAlpha & 0xFF0000) >> 8;
								}
								local3[local51] = local314;
							} else if (op == 2) {
								local209 = this.pixels[local48];
								if (local209 != 0) {
									local234 = (local209 & 0xFF00FF) * SpriteSM.alpha & 0xFF00FF00;
									local238 = (local209 & 0xFF00) * SpriteSM.alpha & 0xFF0000;
									local3[local51++] = ((local234 | local238) >>> 8) + SpriteSM.lerpColour;
								}
							} else {
								throw new IllegalArgumentException();
							}
							local16 += SpriteSM.anInt7164;
							local18++;
						}
					}
					local9++;
					SpriteSM.uBase += SpriteSM.anInt7169;
					SpriteSM.pixelOffset += SpriteSM.viewportStride;
				}
			}
		} else if (SpriteSM.anInt7153 < 0) {
			if (SpriteSM.anInt7164 == 0) {
				local9 = SpriteSM.anInt7141;
				while (local9 < 0) {
					local12 = SpriteSM.pixelOffset;
					local14 = SpriteSM.uBase + SpriteSM.anInt7143;
					local16 = SpriteSM.vBase;
					local18 = SpriteSM.anInt7151;
					if (local16 >= 0 && local16 - (super.imageHeight << 12) < 0) {
						@Pc(2545) int local2545;
						if ((local2545 = local14 - (super.imageWidth << 12)) >= 0) {
							local852 = (SpriteSM.anInt7153 - local2545) / SpriteSM.anInt7153;
							local18 += local852;
							local14 += SpriteSM.anInt7153 * local852;
							local12 += local852;
						}
						@Pc(2573) int local2573;
						if ((local2573 = (local14 - SpriteSM.anInt7153) / SpriteSM.anInt7153) > local18) {
							local18 = local2573;
						}
						while (local18 < 0) {
							local48 = (local16 >> 12) * super.imageWidth + (local14 >> 12);
							local51 = local12++;
							if (op == 1) {
								local209 = this.pixels[local48];
								if (local209 != 0) {
									local3[local51] = local209;
								}
							} else if (op == 0) {
								local209 = this.pixels[local48];
								if (local209 != 0) {
									if ((SpriteSM.colour & 0xFFFFFF) == 16777215) {
										local234 = SpriteSM.colour >>> 24;
										local238 = 256 - local234;
										local242 = local3[local51];
										local3[local51] = ((local209 & 0xFF00FF) * local234 + (local242 & 0xFF00FF) * local238 & 0xFF00FF00) + ((local209 & 0xFF00) * local234 + (local242 & 0xFF00) * local238 & 0xFF0000) >> 8;
									} else if (SpriteSM.alpha == 255) {
										local234 = (local209 & 0xFF0000) * SpriteSM.red & 0xFF000000;
										local238 = (local209 & 0xFF00) * SpriteSM.green & 0xFF0000;
										local242 = (local209 & 0xFF) * SpriteSM.blue & 0xFF00;
										local3[local51] = (local234 | local238 | local242) >>> 8;
									} else {
										local234 = (local209 & 0xFF0000) * SpriteSM.red & 0xFF000000;
										local238 = (local209 & 0xFF00) * SpriteSM.green & 0xFF0000;
										local242 = (local209 & 0xFF) * SpriteSM.blue & 0xFF00;
										local209 = (local234 | local238 | local242) >>> 8;
										local314 = local3[local51];
										local3[local51] = ((local209 & 0xFF00FF) * SpriteSM.alpha + (local314 & 0xFF00FF) * SpriteSM.invAlpha & 0xFF00FF00) + ((local209 & 0xFF00) * SpriteSM.alpha + (local314 & 0xFF00) * SpriteSM.invAlpha & 0xFF0000) >> 8;
									}
								}
							} else if (op == 3) {
								local209 = this.pixels[local48];
								local234 = SpriteSM.colour;
								local238 = local209 + local234;
								local242 = (local209 & 0xFF00FF) + (local234 & 0xFF00FF);
								local314 = (local242 & 0x1000100) + (local238 - local242 & 0x10000);
								local314 = local238 - local314 | local314 - (local314 >>> 8);
								if (local209 == 0 && SpriteSM.alpha != 255) {
									local209 = local314;
									local314 = local3[local51];
									local314 = ((local209 & 0xFF00FF) * SpriteSM.alpha + (local314 & 0xFF00FF) * SpriteSM.invAlpha & 0xFF00FF00) + ((local209 & 0xFF00) * SpriteSM.alpha + (local314 & 0xFF00) * SpriteSM.invAlpha & 0xFF0000) >> 8;
								}
								local3[local51] = local314;
							} else if (op == 2) {
								local209 = this.pixels[local48];
								if (local209 != 0) {
									local234 = (local209 & 0xFF00FF) * SpriteSM.alpha & 0xFF00FF00;
									local238 = (local209 & 0xFF00) * SpriteSM.alpha & 0xFF0000;
									local3[local51++] = ((local234 | local238) >>> 8) + SpriteSM.lerpColour;
								}
							} else {
								throw new IllegalArgumentException();
							}
							local14 += SpriteSM.anInt7153;
							local18++;
						}
					}
					local9++;
					SpriteSM.vBase += SpriteSM.dvdy;
					SpriteSM.pixelOffset += SpriteSM.viewportStride;
				}
			} else if (SpriteSM.anInt7164 < 0) {
				local9 = SpriteSM.anInt7141;
				while (local9 < 0) {
					local12 = SpriteSM.pixelOffset;
					local14 = SpriteSM.uBase + SpriteSM.anInt7143;
					local16 = SpriteSM.vBase + SpriteSM.anInt7142;
					local18 = SpriteSM.anInt7151;
					@Pc(3387) int local3387;
					if ((local3387 = local14 - (super.imageWidth << 12)) >= 0) {
						local852 = (SpriteSM.anInt7153 - local3387) / SpriteSM.anInt7153;
						local18 += local852;
						local14 += SpriteSM.anInt7153 * local852;
						local16 += SpriteSM.anInt7164 * local852;
						local12 += local852;
					}
					@Pc(3421) int local3421;
					if ((local3421 = (local14 - SpriteSM.anInt7153) / SpriteSM.anInt7153) > local18) {
						local18 = local3421;
					}
					@Pc(3433) int local3433;
					if ((local3433 = local16 - (super.imageHeight << 12)) >= 0) {
						local852 = (SpriteSM.anInt7164 - local3433) / SpriteSM.anInt7164;
						local18 += local852;
						local14 += SpriteSM.anInt7153 * local852;
						local16 += SpriteSM.anInt7164 * local852;
						local12 += local852;
					}
					@Pc(3467) int local3467;
					if ((local3467 = (local16 - SpriteSM.anInt7164) / SpriteSM.anInt7164) > local18) {
						local18 = local3467;
					}
					while (local18 < 0) {
						local48 = (local16 >> 12) * super.imageWidth + (local14 >> 12);
						local51 = local12++;
						if (op == 1) {
							local209 = this.pixels[local48];
							if (local209 != 0) {
								local3[local51] = local209;
							}
						} else if (op == 0) {
							local209 = this.pixels[local48];
							if (local209 != 0) {
								if ((SpriteSM.colour & 0xFFFFFF) == 16777215) {
									local234 = SpriteSM.colour >>> 24;
									local238 = 256 - local234;
									local242 = local3[local51];
									local3[local51] = ((local209 & 0xFF00FF) * local234 + (local242 & 0xFF00FF) * local238 & 0xFF00FF00) + ((local209 & 0xFF00) * local234 + (local242 & 0xFF00) * local238 & 0xFF0000) >> 8;
								} else if (SpriteSM.alpha == 255) {
									local234 = (local209 & 0xFF0000) * SpriteSM.red & 0xFF000000;
									local238 = (local209 & 0xFF00) * SpriteSM.green & 0xFF0000;
									local242 = (local209 & 0xFF) * SpriteSM.blue & 0xFF00;
									local3[local51] = (local234 | local238 | local242) >>> 8;
								} else {
									local234 = (local209 & 0xFF0000) * SpriteSM.red & 0xFF000000;
									local238 = (local209 & 0xFF00) * SpriteSM.green & 0xFF0000;
									local242 = (local209 & 0xFF) * SpriteSM.blue & 0xFF00;
									local209 = (local234 | local238 | local242) >>> 8;
									local314 = local3[local51];
									local3[local51] = ((local209 & 0xFF00FF) * SpriteSM.alpha + (local314 & 0xFF00FF) * SpriteSM.invAlpha & 0xFF00FF00) + ((local209 & 0xFF00) * SpriteSM.alpha + (local314 & 0xFF00) * SpriteSM.invAlpha & 0xFF0000) >> 8;
								}
							}
						} else if (op == 3) {
							local209 = this.pixels[local48];
							local234 = SpriteSM.colour;
							local238 = local209 + local234;
							local242 = (local209 & 0xFF00FF) + (local234 & 0xFF00FF);
							local314 = (local242 & 0x1000100) + (local238 - local242 & 0x10000);
							local314 = local238 - local314 | local314 - (local314 >>> 8);
							if (local209 == 0 && SpriteSM.alpha != 255) {
								local209 = local314;
								local314 = local3[local51];
								local314 = ((local209 & 0xFF00FF) * SpriteSM.alpha + (local314 & 0xFF00FF) * SpriteSM.invAlpha & 0xFF00FF00) + ((local209 & 0xFF00) * SpriteSM.alpha + (local314 & 0xFF00) * SpriteSM.invAlpha & 0xFF0000) >> 8;
							}
							local3[local51] = local314;
						} else if (op == 2) {
							local209 = this.pixels[local48];
							if (local209 != 0) {
								local234 = (local209 & 0xFF00FF) * SpriteSM.alpha & 0xFF00FF00;
								local238 = (local209 & 0xFF00) * SpriteSM.alpha & 0xFF0000;
								local3[local51++] = ((local234 | local238) >>> 8) + SpriteSM.lerpColour;
							}
						} else {
							throw new IllegalArgumentException();
						}
						local14 += SpriteSM.anInt7153;
						local16 += SpriteSM.anInt7164;
						local18++;
					}
					local9++;
					SpriteSM.uBase += SpriteSM.anInt7169;
					SpriteSM.vBase += SpriteSM.dvdy;
					SpriteSM.pixelOffset += SpriteSM.viewportStride;
				}
			} else {
				local9 = SpriteSM.anInt7141;
				while (local9 < 0) {
					local12 = SpriteSM.pixelOffset;
					local14 = SpriteSM.uBase + SpriteSM.anInt7143;
					local16 = SpriteSM.vBase + SpriteSM.anInt7142;
					local18 = SpriteSM.anInt7151;
					@Pc(4287) int local4287;
					if ((local4287 = local14 - (super.imageWidth << 12)) >= 0) {
						local852 = (SpriteSM.anInt7153 - local4287) / SpriteSM.anInt7153;
						local18 += local852;
						local14 += SpriteSM.anInt7153 * local852;
						local16 += SpriteSM.anInt7164 * local852;
						local12 += local852;
					}
					@Pc(4321) int local4321;
					if ((local4321 = (local14 - SpriteSM.anInt7153) / SpriteSM.anInt7153) > local18) {
						local18 = local4321;
					}
					if (local16 < 0) {
						local852 = (SpriteSM.anInt7164 - local16 - 1) / SpriteSM.anInt7164;
						local18 += local852;
						local14 += SpriteSM.anInt7153 * local852;
						local16 += SpriteSM.anInt7164 * local852;
						local12 += local852;
					}
					@Pc(4369) int local4369;
					if ((local4369 = (local16 + 1 - (super.imageHeight << 12) - SpriteSM.anInt7164) / SpriteSM.anInt7164) > local18) {
						local18 = local4369;
					}
					while (local18 < 0) {
						local48 = (local16 >> 12) * super.imageWidth + (local14 >> 12);
						local51 = local12++;
						if (op == 1) {
							local209 = this.pixels[local48];
							if (local209 != 0) {
								local3[local51] = local209;
							}
						} else if (op == 0) {
							local209 = this.pixels[local48];
							if (local209 != 0) {
								if ((SpriteSM.colour & 0xFFFFFF) == 16777215) {
									local234 = SpriteSM.colour >>> 24;
									local238 = 256 - local234;
									local242 = local3[local51];
									local3[local51] = ((local209 & 0xFF00FF) * local234 + (local242 & 0xFF00FF) * local238 & 0xFF00FF00) + ((local209 & 0xFF00) * local234 + (local242 & 0xFF00) * local238 & 0xFF0000) >> 8;
								} else if (SpriteSM.alpha == 255) {
									local234 = (local209 & 0xFF0000) * SpriteSM.red & 0xFF000000;
									local238 = (local209 & 0xFF00) * SpriteSM.green & 0xFF0000;
									local242 = (local209 & 0xFF) * SpriteSM.blue & 0xFF00;
									local3[local51] = (local234 | local238 | local242) >>> 8;
								} else {
									local234 = (local209 & 0xFF0000) * SpriteSM.red & 0xFF000000;
									local238 = (local209 & 0xFF00) * SpriteSM.green & 0xFF0000;
									local242 = (local209 & 0xFF) * SpriteSM.blue & 0xFF00;
									local209 = (local234 | local238 | local242) >>> 8;
									local314 = local3[local51];
									local3[local51] = ((local209 & 0xFF00FF) * SpriteSM.alpha + (local314 & 0xFF00FF) * SpriteSM.invAlpha & 0xFF00FF00) + ((local209 & 0xFF00) * SpriteSM.alpha + (local314 & 0xFF00) * SpriteSM.invAlpha & 0xFF0000) >> 8;
								}
							}
						} else if (op == 3) {
							local209 = this.pixels[local48];
							local234 = SpriteSM.colour;
							local238 = local209 + local234;
							local242 = (local209 & 0xFF00FF) + (local234 & 0xFF00FF);
							local314 = (local242 & 0x1000100) + (local238 - local242 & 0x10000);
							local314 = local238 - local314 | local314 - (local314 >>> 8);
							if (local209 == 0 && SpriteSM.alpha != 255) {
								local209 = local314;
								local314 = local3[local51];
								local314 = ((local209 & 0xFF00FF) * SpriteSM.alpha + (local314 & 0xFF00FF) * SpriteSM.invAlpha & 0xFF00FF00) + ((local209 & 0xFF00) * SpriteSM.alpha + (local314 & 0xFF00) * SpriteSM.invAlpha & 0xFF0000) >> 8;
							}
							local3[local51] = local314;
						} else if (op == 2) {
							local209 = this.pixels[local48];
							if (local209 != 0) {
								local234 = (local209 & 0xFF00FF) * SpriteSM.alpha & 0xFF00FF00;
								local238 = (local209 & 0xFF00) * SpriteSM.alpha & 0xFF0000;
								local3[local51++] = ((local234 | local238) >>> 8) + SpriteSM.lerpColour;
							}
						} else {
							throw new IllegalArgumentException();
						}
						local14 += SpriteSM.anInt7153;
						local16 += SpriteSM.anInt7164;
						local18++;
					}
					local9++;
					SpriteSM.uBase += SpriteSM.anInt7169;
					SpriteSM.vBase += SpriteSM.dvdy;
					SpriteSM.pixelOffset += SpriteSM.viewportStride;
				}
			}
		} else if (SpriteSM.anInt7164 == 0) {
			local9 = SpriteSM.anInt7141;
			while (local9 < 0) {
				local12 = SpriteSM.pixelOffset;
				local14 = SpriteSM.uBase + SpriteSM.anInt7143;
				local16 = SpriteSM.vBase;
				local18 = SpriteSM.anInt7151;
				if (local16 >= 0 && local16 - (super.imageHeight << 12) < 0) {
					if (local14 < 0) {
						local852 = (SpriteSM.anInt7153 - local14 - 1) / SpriteSM.anInt7153;
						local18 += local852;
						local14 += SpriteSM.anInt7153 * local852;
						local12 += local852;
					}
					@Pc(5228) int local5228;
					if ((local5228 = (local14 + 1 - (super.imageWidth << 12) - SpriteSM.anInt7153) / SpriteSM.anInt7153) > local18) {
						local18 = local5228;
					}
					while (local18 < 0) {
						local48 = (local16 >> 12) * super.imageWidth + (local14 >> 12);
						local51 = local12++;
						if (op == 1) {
							local209 = this.pixels[local48];
							if (local209 != 0) {
								local3[local51] = local209;
							}
						} else if (op == 0) {
							local209 = this.pixels[local48];
							if (local209 != 0) {
								if ((SpriteSM.colour & 0xFFFFFF) == 16777215) {
									local234 = SpriteSM.colour >>> 24;
									local238 = 256 - local234;
									local242 = local3[local51];
									local3[local51] = ((local209 & 0xFF00FF) * local234 + (local242 & 0xFF00FF) * local238 & 0xFF00FF00) + ((local209 & 0xFF00) * local234 + (local242 & 0xFF00) * local238 & 0xFF0000) >> 8;
								} else if (SpriteSM.alpha == 255) {
									local234 = (local209 & 0xFF0000) * SpriteSM.red & 0xFF000000;
									local238 = (local209 & 0xFF00) * SpriteSM.green & 0xFF0000;
									local242 = (local209 & 0xFF) * SpriteSM.blue & 0xFF00;
									local3[local51] = (local234 | local238 | local242) >>> 8;
								} else {
									local234 = (local209 & 0xFF0000) * SpriteSM.red & 0xFF000000;
									local238 = (local209 & 0xFF00) * SpriteSM.green & 0xFF0000;
									local242 = (local209 & 0xFF) * SpriteSM.blue & 0xFF00;
									local209 = (local234 | local238 | local242) >>> 8;
									local314 = local3[local51];
									local3[local51] = ((local209 & 0xFF00FF) * SpriteSM.alpha + (local314 & 0xFF00FF) * SpriteSM.invAlpha & 0xFF00FF00) + ((local209 & 0xFF00) * SpriteSM.alpha + (local314 & 0xFF00) * SpriteSM.invAlpha & 0xFF0000) >> 8;
								}
							}
						} else if (op == 3) {
							local209 = this.pixels[local48];
							local234 = SpriteSM.colour;
							local238 = local209 + local234;
							local242 = (local209 & 0xFF00FF) + (local234 & 0xFF00FF);
							local314 = (local242 & 0x1000100) + (local238 - local242 & 0x10000);
							local314 = local238 - local314 | local314 - (local314 >>> 8);
							if (local209 == 0 && SpriteSM.alpha != 255) {
								local209 = local314;
								local314 = local3[local51];
								local314 = ((local209 & 0xFF00FF) * SpriteSM.alpha + (local314 & 0xFF00FF) * SpriteSM.invAlpha & 0xFF00FF00) + ((local209 & 0xFF00) * SpriteSM.alpha + (local314 & 0xFF00) * SpriteSM.invAlpha & 0xFF0000) >> 8;
							}
							local3[local51] = local314;
						} else if (op == 2) {
							local209 = this.pixels[local48];
							if (local209 != 0) {
								local234 = (local209 & 0xFF00FF) * SpriteSM.alpha & 0xFF00FF00;
								local238 = (local209 & 0xFF00) * SpriteSM.alpha & 0xFF0000;
								local3[local51++] = ((local234 | local238) >>> 8) + SpriteSM.lerpColour;
							}
						} else {
							throw new IllegalArgumentException();
						}
						local14 += SpriteSM.anInt7153;
						local18++;
					}
				}
				local9++;
				SpriteSM.uBase += SpriteSM.anInt7169;
				SpriteSM.vBase += SpriteSM.dvdy;
				SpriteSM.pixelOffset += SpriteSM.viewportStride;
			}
		} else if (SpriteSM.anInt7164 < 0) {
			for (local9 = SpriteSM.anInt7141; local9 < 0; local9++) {
				local12 = SpriteSM.pixelOffset;
				local14 = SpriteSM.uBase + SpriteSM.anInt7143;
				local16 = SpriteSM.vBase + SpriteSM.anInt7142;
				local18 = SpriteSM.anInt7151;
				if (local14 < 0) {
					local852 = (SpriteSM.anInt7153 - local14 - 1) / SpriteSM.anInt7153;
					local18 += local852;
					local14 += SpriteSM.anInt7153 * local852;
					local16 += SpriteSM.anInt7164 * local852;
					local12 += local852;
				}
				@Pc(6082) int local6082;
				if ((local6082 = (local14 + 1 - (super.imageWidth << 12) - SpriteSM.anInt7153) / SpriteSM.anInt7153) > local18) {
					local18 = local6082;
				}
				@Pc(6094) int local6094;
				if ((local6094 = local16 - (super.imageHeight << 12)) >= 0) {
					local852 = (SpriteSM.anInt7164 - local6094) / SpriteSM.anInt7164;
					local18 += local852;
					local14 += SpriteSM.anInt7153 * local852;
					local16 += SpriteSM.anInt7164 * local852;
					local12 += local852;
				}
				@Pc(6128) int local6128;
				if ((local6128 = (local16 - SpriteSM.anInt7164) / SpriteSM.anInt7164) > local18) {
					local18 = local6128;
				}
				while (local18 < 0) {
					local48 = (local16 >> 12) * super.imageWidth + (local14 >> 12);
					local51 = local12++;
					if (op == 1) {
						local209 = this.pixels[local48];
						if (local209 != 0) {
							local3[local51] = local209;
						}
					} else if (op == 0) {
						local209 = this.pixels[local48];
						if (local209 != 0) {
							if ((SpriteSM.colour & 0xFFFFFF) == 16777215) {
								local234 = SpriteSM.colour >>> 24;
								local238 = 256 - local234;
								local242 = local3[local51];
								local3[local51] = ((local209 & 0xFF00FF) * local234 + (local242 & 0xFF00FF) * local238 & 0xFF00FF00) + ((local209 & 0xFF00) * local234 + (local242 & 0xFF00) * local238 & 0xFF0000) >> 8;
							} else if (SpriteSM.alpha == 255) {
								local234 = (local209 & 0xFF0000) * SpriteSM.red & 0xFF000000;
								local238 = (local209 & 0xFF00) * SpriteSM.green & 0xFF0000;
								local242 = (local209 & 0xFF) * SpriteSM.blue & 0xFF00;
								local3[local51] = (local234 | local238 | local242) >>> 8;
							} else {
								local234 = (local209 & 0xFF0000) * SpriteSM.red & 0xFF000000;
								local238 = (local209 & 0xFF00) * SpriteSM.green & 0xFF0000;
								local242 = (local209 & 0xFF) * SpriteSM.blue & 0xFF00;
								local209 = (local234 | local238 | local242) >>> 8;
								local314 = local3[local51];
								local3[local51] = ((local209 & 0xFF00FF) * SpriteSM.alpha + (local314 & 0xFF00FF) * SpriteSM.invAlpha & 0xFF00FF00) + ((local209 & 0xFF00) * SpriteSM.alpha + (local314 & 0xFF00) * SpriteSM.invAlpha & 0xFF0000) >> 8;
							}
						}
					} else if (op == 3) {
						local209 = this.pixels[local48];
						local234 = SpriteSM.colour;
						local238 = local209 + local234;
						local242 = (local209 & 0xFF00FF) + (local234 & 0xFF00FF);
						local314 = (local242 & 0x1000100) + (local238 - local242 & 0x10000);
						local314 = local238 - local314 | local314 - (local314 >>> 8);
						if (local209 == 0 && SpriteSM.alpha != 255) {
							local209 = local314;
							local314 = local3[local51];
							local314 = ((local209 & 0xFF00FF) * SpriteSM.alpha + (local314 & 0xFF00FF) * SpriteSM.invAlpha & 0xFF00FF00) + ((local209 & 0xFF00) * SpriteSM.alpha + (local314 & 0xFF00) * SpriteSM.invAlpha & 0xFF0000) >> 8;
						}
						local3[local51] = local314;
					} else if (op == 2) {
						local209 = this.pixels[local48];
						if (local209 != 0) {
							local234 = (local209 & 0xFF00FF) * SpriteSM.alpha & 0xFF00FF00;
							local238 = (local209 & 0xFF00) * SpriteSM.alpha & 0xFF0000;
							local3[local51++] = ((local234 | local238) >>> 8) + SpriteSM.lerpColour;
						}
					} else {
						throw new IllegalArgumentException();
					}
					local14 += SpriteSM.anInt7153;
					local16 += SpriteSM.anInt7164;
					local18++;
				}
				SpriteSM.uBase += SpriteSM.anInt7169;
				SpriteSM.vBase += SpriteSM.dvdy;
				SpriteSM.pixelOffset += SpriteSM.viewportStride;
			}
		} else {
			for (local9 = SpriteSM.anInt7141; local9 < 0; local9++) {
				local12 = SpriteSM.pixelOffset;
				local14 = SpriteSM.uBase + SpriteSM.anInt7143;
				local16 = SpriteSM.vBase + SpriteSM.anInt7142;
				local18 = SpriteSM.anInt7151;
				if (local14 < 0) {
					local852 = (SpriteSM.anInt7153 - local14 - 1) / SpriteSM.anInt7153;
					local18 += local852;
					local14 += SpriteSM.anInt7153 * local852;
					local16 += SpriteSM.anInt7164 * local852;
					local12 += local852;
				}
				@Pc(6984) int local6984;
				if ((local6984 = (local14 + 1 - (super.imageWidth << 12) - SpriteSM.anInt7153) / SpriteSM.anInt7153) > local18) {
					local18 = local6984;
				}
				if (local16 < 0) {
					local852 = (SpriteSM.anInt7164 - local16 - 1) / SpriteSM.anInt7164;
					local18 += local852;
					local14 += SpriteSM.anInt7153 * local852;
					local16 += SpriteSM.anInt7164 * local852;
					local12 += local852;
				}
				@Pc(7032) int local7032;
				if ((local7032 = (local16 + 1 - (super.imageHeight << 12) - SpriteSM.anInt7164) / SpriteSM.anInt7164) > local18) {
					local18 = local7032;
				}
				while (local18 < 0) {
					local48 = (local16 >> 12) * super.imageWidth + (local14 >> 12);
					local51 = local12++;
					if (op == 1) {
						local209 = this.pixels[local48];
						if (local209 != 0) {
							local3[local51] = local209;
						}
					} else if (op == 0) {
						local209 = this.pixels[local48];
						if (local209 != 0) {
							if ((SpriteSM.colour & 0xFFFFFF) == 16777215) {
								local234 = SpriteSM.colour >>> 24;
								local238 = 256 - local234;
								local242 = local3[local51];
								local3[local51] = ((local209 & 0xFF00FF) * local234 + (local242 & 0xFF00FF) * local238 & 0xFF00FF00) + ((local209 & 0xFF00) * local234 + (local242 & 0xFF00) * local238 & 0xFF0000) >> 8;
							} else if (SpriteSM.alpha == 255) {
								local234 = (local209 & 0xFF0000) * SpriteSM.red & 0xFF000000;
								local238 = (local209 & 0xFF00) * SpriteSM.green & 0xFF0000;
								local242 = (local209 & 0xFF) * SpriteSM.blue & 0xFF00;
								local3[local51] = (local234 | local238 | local242) >>> 8;
							} else {
								local234 = (local209 & 0xFF0000) * SpriteSM.red & 0xFF000000;
								local238 = (local209 & 0xFF00) * SpriteSM.green & 0xFF0000;
								local242 = (local209 & 0xFF) * SpriteSM.blue & 0xFF00;
								local209 = (local234 | local238 | local242) >>> 8;
								local314 = local3[local51];
								local3[local51] = ((local209 & 0xFF00FF) * SpriteSM.alpha + (local314 & 0xFF00FF) * SpriteSM.invAlpha & 0xFF00FF00) + ((local209 & 0xFF00) * SpriteSM.alpha + (local314 & 0xFF00) * SpriteSM.invAlpha & 0xFF0000) >> 8;
							}
						}
					} else if (op == 3) {
						local209 = this.pixels[local48];
						local234 = SpriteSM.colour;
						local238 = local209 + local234;
						local242 = (local209 & 0xFF00FF) + (local234 & 0xFF00FF);
						local314 = (local242 & 0x1000100) + (local238 - local242 & 0x10000);
						local314 = local238 - local314 | local314 - (local314 >>> 8);
						if (local209 == 0 && SpriteSM.alpha != 255) {
							local209 = local314;
							local314 = local3[local51];
							local314 = ((local209 & 0xFF00FF) * SpriteSM.alpha + (local314 & 0xFF00FF) * SpriteSM.invAlpha & 0xFF00FF00) + ((local209 & 0xFF00) * SpriteSM.alpha + (local314 & 0xFF00) * SpriteSM.invAlpha & 0xFF0000) >> 8;
						}
						local3[local51] = local314;
					} else if (op == 2) {
						local209 = this.pixels[local48];
						if (local209 != 0) {
							local234 = (local209 & 0xFF00FF) * SpriteSM.alpha & 0xFF00FF00;
							local238 = (local209 & 0xFF00) * SpriteSM.alpha & 0xFF0000;
							local3[local51++] = ((local234 | local238) >>> 8) + SpriteSM.lerpColour;
						}
					} else {
						throw new IllegalArgumentException();
					}
					local14 += SpriteSM.anInt7153;
					local16 += SpriteSM.anInt7164;
					local18++;
				}
				SpriteSM.uBase += SpriteSM.anInt7169;
				SpriteSM.vBase += SpriteSM.dvdy;
				SpriteSM.pixelOffset += SpriteSM.viewportStride;
			}
		}
	}

	@OriginalMember(owner = "client!ea", name = "a", descriptor = "([I[III)V")
	@Override
	protected void blitParallelogramMasked(@OriginalArg(0) int[] lineOffsets, @OriginalArg(1) int[] lineWidths, @OriginalArg(2) int maskOffsetX, @OriginalArg(3) int maskOffsetY) {
		@Pc(3) int[] local3 = super.toolkit.surfaceRaster;
		@Pc(181) int local181;
		@Pc(9) int local9;
		@Pc(14) int local14;
		@Pc(23) int local23;
		@Pc(25) int local25;
		@Pc(27) int local27;
		@Pc(29) int local29;
		@Pc(53) int local53;
		@Pc(58) int local58;
		@Pc(64) int local64;
		@Pc(111) int local111;
		if (SpriteSM.anInt7153 == 0) {
			if (SpriteSM.anInt7164 == 0) {
				local9 = SpriteSM.anInt7141;
				while (local9 < 0) {
					local14 = local9 + maskOffsetY;
					if (local14 >= 0) {
						if (local14 >= lineOffsets.length) {
							return;
						}
						local23 = SpriteSM.pixelOffset;
						local25 = SpriteSM.uBase;
						local27 = SpriteSM.vBase;
						local29 = SpriteSM.anInt7151;
						if (local25 >= 0 && local27 >= 0 && local25 - (super.imageWidth << 12) < 0 && local27 - (super.imageHeight << 12) < 0) {
							local53 = lineOffsets[local14] - maskOffsetX;
							local58 = -lineWidths[local14];
							local64 = local53 + SpriteSM.pixelOffset - local23;
							if (local64 > 0) {
								local23 += local64;
								local29 += local64;
								local25 += SpriteSM.anInt7153 * local64;
								local27 += SpriteSM.anInt7164 * local64;
							} else {
								local58 -= local64;
							}
							if (local29 < local58) {
								local29 = local58;
							}
							while (local29 < 0) {
								local111 = this.pixels[(local27 >> 12) * super.imageWidth + (local25 >> 12)];
								if (local111 == 0) {
									local23++;
								} else {
									local3[local23++] = local111;
								}
								local29++;
							}
						}
					}
					local9++;
					SpriteSM.pixelOffset += SpriteSM.viewportStride;
				}
			} else if (SpriteSM.anInt7164 < 0) {
				local9 = SpriteSM.anInt7141;
				while (local9 < 0) {
					local14 = local9 + maskOffsetY;
					if (local14 >= 0) {
						if (local14 >= lineOffsets.length) {
							return;
						}
						local23 = SpriteSM.pixelOffset;
						local25 = SpriteSM.uBase;
						local27 = SpriteSM.vBase + SpriteSM.anInt7142;
						local29 = SpriteSM.anInt7151;
						if (local25 >= 0 && local25 - (super.imageWidth << 12) < 0) {
							@Pc(174) int local174;
							if ((local174 = local27 - (super.imageHeight << 12)) >= 0) {
								local181 = (SpriteSM.anInt7164 - local174) / SpriteSM.anInt7164;
								local29 += local181;
								local27 += SpriteSM.anInt7164 * local181;
								local23 += local181;
							}
							@Pc(202) int local202;
							if ((local202 = (local27 - SpriteSM.anInt7164) / SpriteSM.anInt7164) > local29) {
								local29 = local202;
							}
							local53 = lineOffsets[local14] - maskOffsetX;
							local58 = -lineWidths[local14];
							local64 = local53 + SpriteSM.pixelOffset - local23;
							if (local64 > 0) {
								local23 += local64;
								local29 += local64;
								local25 += SpriteSM.anInt7153 * local64;
								local27 += SpriteSM.anInt7164 * local64;
							} else {
								local58 -= local64;
							}
							if (local29 < local58) {
								local29 = local58;
							}
							while (local29 < 0) {
								local111 = this.pixels[(local27 >> 12) * super.imageWidth + (local25 >> 12)];
								if (local111 == 0) {
									local23++;
								} else {
									local3[local23++] = local111;
								}
								local27 += SpriteSM.anInt7164;
								local29++;
							}
						}
					}
					local9++;
					SpriteSM.uBase += SpriteSM.anInt7169;
					SpriteSM.pixelOffset += SpriteSM.viewportStride;
				}
			} else {
				local9 = SpriteSM.anInt7141;
				while (local9 < 0) {
					local14 = local9 + maskOffsetY;
					if (local14 >= 0) {
						if (local14 >= lineOffsets.length) {
							return;
						}
						local23 = SpriteSM.pixelOffset;
						local25 = SpriteSM.uBase;
						local27 = SpriteSM.vBase + SpriteSM.anInt7142;
						local29 = SpriteSM.anInt7151;
						if (local25 >= 0 && local25 - (super.imageWidth << 12) < 0) {
							if (local27 < 0) {
								local181 = (SpriteSM.anInt7164 - local27 - 1) / SpriteSM.anInt7164;
								local29 += local181;
								local27 += SpriteSM.anInt7164 * local181;
								local23 += local181;
							}
							@Pc(369) int local369;
							if ((local369 = (local27 + 1 - (super.imageHeight << 12) - SpriteSM.anInt7164) / SpriteSM.anInt7164) > local29) {
								local29 = local369;
							}
							local53 = lineOffsets[local14] - maskOffsetX;
							local58 = -lineWidths[local14];
							local64 = local53 + SpriteSM.pixelOffset - local23;
							if (local64 > 0) {
								local23 += local64;
								local29 += local64;
								local25 += SpriteSM.anInt7153 * local64;
								local27 += SpriteSM.anInt7164 * local64;
							} else {
								local58 -= local64;
							}
							if (local29 < local58) {
								local29 = local58;
							}
							while (local29 < 0) {
								local111 = this.pixels[(local27 >> 12) * super.imageWidth + (local25 >> 12)];
								if (local111 == 0) {
									local23++;
								} else {
									local3[local23++] = local111;
								}
								local27 += SpriteSM.anInt7164;
								local29++;
							}
						}
					}
					local9++;
					SpriteSM.uBase += SpriteSM.anInt7169;
					SpriteSM.pixelOffset += SpriteSM.viewportStride;
				}
			}
		} else if (SpriteSM.anInt7153 < 0) {
			if (SpriteSM.anInt7164 == 0) {
				local9 = SpriteSM.anInt7141;
				while (local9 < 0) {
					local14 = local9 + maskOffsetY;
					if (local14 >= 0) {
						if (local14 >= lineOffsets.length) {
							return;
						}
						local23 = SpriteSM.pixelOffset;
						local25 = SpriteSM.uBase + SpriteSM.anInt7143;
						local27 = SpriteSM.vBase;
						local29 = SpriteSM.anInt7151;
						if (local27 >= 0 && local27 - (super.imageHeight << 12) < 0) {
							@Pc(510) int local510;
							if ((local510 = local25 - (super.imageWidth << 12)) >= 0) {
								local181 = (SpriteSM.anInt7153 - local510) / SpriteSM.anInt7153;
								local29 += local181;
								local25 += SpriteSM.anInt7153 * local181;
								local23 += local181;
							}
							@Pc(538) int local538;
							if ((local538 = (local25 - SpriteSM.anInt7153) / SpriteSM.anInt7153) > local29) {
								local29 = local538;
							}
							local53 = lineOffsets[local14] - maskOffsetX;
							local58 = -lineWidths[local14];
							local64 = local53 + SpriteSM.pixelOffset - local23;
							if (local64 > 0) {
								local23 += local64;
								local29 += local64;
								local25 += SpriteSM.anInt7153 * local64;
								local27 += SpriteSM.anInt7164 * local64;
							} else {
								local58 -= local64;
							}
							if (local29 < local58) {
								local29 = local58;
							}
							while (local29 < 0) {
								local111 = this.pixels[(local27 >> 12) * super.imageWidth + (local25 >> 12)];
								if (local111 == 0) {
									local23++;
								} else {
									local3[local23++] = local111;
								}
								local25 += SpriteSM.anInt7153;
								local29++;
							}
						}
					}
					local9++;
					SpriteSM.vBase += SpriteSM.dvdy;
					SpriteSM.pixelOffset += SpriteSM.viewportStride;
				}
			} else if (SpriteSM.anInt7164 < 0) {
				local9 = SpriteSM.anInt7141;
				while (local9 < 0) {
					local14 = local9 + maskOffsetY;
					if (local14 >= 0) {
						if (local14 >= lineOffsets.length) {
							return;
						}
						local23 = SpriteSM.pixelOffset;
						local25 = SpriteSM.uBase + SpriteSM.anInt7143;
						local27 = SpriteSM.vBase + SpriteSM.anInt7142;
						local29 = SpriteSM.anInt7151;
						@Pc(670) int local670;
						if ((local670 = local25 - (super.imageWidth << 12)) >= 0) {
							local181 = (SpriteSM.anInt7153 - local670) / SpriteSM.anInt7153;
							local29 += local181;
							local25 += SpriteSM.anInt7153 * local181;
							local27 += SpriteSM.anInt7164 * local181;
							local23 += local181;
						}
						@Pc(704) int local704;
						if ((local704 = (local25 - SpriteSM.anInt7153) / SpriteSM.anInt7153) > local29) {
							local29 = local704;
						}
						@Pc(716) int local716;
						if ((local716 = local27 - (super.imageHeight << 12)) >= 0) {
							local181 = (SpriteSM.anInt7164 - local716) / SpriteSM.anInt7164;
							local29 += local181;
							local25 += SpriteSM.anInt7153 * local181;
							local27 += SpriteSM.anInt7164 * local181;
							local23 += local181;
						}
						@Pc(750) int local750;
						if ((local750 = (local27 - SpriteSM.anInt7164) / SpriteSM.anInt7164) > local29) {
							local29 = local750;
						}
						local53 = lineOffsets[local14] - maskOffsetX;
						local58 = -lineWidths[local14];
						local64 = local53 + SpriteSM.pixelOffset - local23;
						if (local64 > 0) {
							local23 += local64;
							local29 += local64;
							local25 += SpriteSM.anInt7153 * local64;
							local27 += SpriteSM.anInt7164 * local64;
						} else {
							local58 -= local64;
						}
						if (local29 < local58) {
							local29 = local58;
						}
						while (local29 < 0) {
							local111 = this.pixels[(local27 >> 12) * super.imageWidth + (local25 >> 12)];
							if (local111 == 0) {
								local23++;
							} else {
								local3[local23++] = local111;
							}
							local25 += SpriteSM.anInt7153;
							local27 += SpriteSM.anInt7164;
							local29++;
						}
					}
					local9++;
					SpriteSM.uBase += SpriteSM.anInt7169;
					SpriteSM.vBase += SpriteSM.dvdy;
					SpriteSM.pixelOffset += SpriteSM.viewportStride;
				}
			} else {
				local9 = SpriteSM.anInt7141;
				while (local9 < 0) {
					local14 = local9 + maskOffsetY;
					if (local14 >= 0) {
						if (local14 >= lineOffsets.length) {
							return;
						}
						local23 = SpriteSM.pixelOffset;
						local25 = SpriteSM.uBase + SpriteSM.anInt7143;
						local27 = SpriteSM.vBase + SpriteSM.anInt7142;
						local29 = SpriteSM.anInt7151;
						@Pc(888) int local888;
						if ((local888 = local25 - (super.imageWidth << 12)) >= 0) {
							local181 = (SpriteSM.anInt7153 - local888) / SpriteSM.anInt7153;
							local29 += local181;
							local25 += SpriteSM.anInt7153 * local181;
							local27 += SpriteSM.anInt7164 * local181;
							local23 += local181;
						}
						@Pc(922) int local922;
						if ((local922 = (local25 - SpriteSM.anInt7153) / SpriteSM.anInt7153) > local29) {
							local29 = local922;
						}
						if (local27 < 0) {
							local181 = (SpriteSM.anInt7164 - local27 - 1) / SpriteSM.anInt7164;
							local29 += local181;
							local25 += SpriteSM.anInt7153 * local181;
							local27 += SpriteSM.anInt7164 * local181;
							local23 += local181;
						}
						@Pc(970) int local970;
						if ((local970 = (local27 + 1 - (super.imageHeight << 12) - SpriteSM.anInt7164) / SpriteSM.anInt7164) > local29) {
							local29 = local970;
						}
						local53 = lineOffsets[local14] - maskOffsetX;
						local58 = -lineWidths[local14];
						local64 = local53 + SpriteSM.pixelOffset - local23;
						if (local64 > 0) {
							local23 += local64;
							local29 += local64;
							local25 += SpriteSM.anInt7153 * local64;
							local27 += SpriteSM.anInt7164 * local64;
						} else {
							local58 -= local64;
						}
						if (local29 < local58) {
							local29 = local58;
						}
						while (local29 < 0) {
							local111 = this.pixels[(local27 >> 12) * super.imageWidth + (local25 >> 12)];
							if (local111 == 0) {
								local23++;
							} else {
								local3[local23++] = local111;
							}
							local25 += SpriteSM.anInt7153;
							local27 += SpriteSM.anInt7164;
							local29++;
						}
					}
					local9++;
					SpriteSM.uBase += SpriteSM.anInt7169;
					SpriteSM.vBase += SpriteSM.dvdy;
					SpriteSM.pixelOffset += SpriteSM.viewportStride;
				}
			}
		} else if (SpriteSM.anInt7164 == 0) {
			local9 = SpriteSM.anInt7141;
			while (local9 < 0) {
				local14 = local9 + maskOffsetY;
				if (local14 >= 0) {
					if (local14 >= lineOffsets.length) {
						return;
					}
					local23 = SpriteSM.pixelOffset;
					local25 = SpriteSM.uBase + SpriteSM.anInt7143;
					local27 = SpriteSM.vBase;
					local29 = SpriteSM.anInt7151;
					if (local27 >= 0 && local27 - (super.imageHeight << 12) < 0) {
						if (local25 < 0) {
							local181 = (SpriteSM.anInt7153 - local25 - 1) / SpriteSM.anInt7153;
							local29 += local181;
							local25 += SpriteSM.anInt7153 * local181;
							local23 += local181;
						}
						@Pc(1147) int local1147;
						if ((local1147 = (local25 + 1 - (super.imageWidth << 12) - SpriteSM.anInt7153) / SpriteSM.anInt7153) > local29) {
							local29 = local1147;
						}
						local53 = lineOffsets[local14] - maskOffsetX;
						local58 = -lineWidths[local14];
						local64 = local53 + SpriteSM.pixelOffset - local23;
						if (local64 > 0) {
							local23 += local64;
							local29 += local64;
							local25 += SpriteSM.anInt7153 * local64;
							local27 += SpriteSM.anInt7164 * local64;
						} else {
							local58 -= local64;
						}
						if (local29 < local58) {
							local29 = local58;
						}
						while (local29 < 0) {
							local111 = this.pixels[(local27 >> 12) * super.imageWidth + (local25 >> 12)];
							if (local111 == 0) {
								local23++;
							} else {
								local3[local23++] = local111;
							}
							local25 += SpriteSM.anInt7153;
							local29++;
						}
					}
				}
				local9++;
				SpriteSM.uBase += SpriteSM.anInt7169;
				SpriteSM.vBase += SpriteSM.dvdy;
				SpriteSM.pixelOffset += SpriteSM.viewportStride;
			}
		} else if (SpriteSM.anInt7164 < 0) {
			local9 = SpriteSM.anInt7141;
			while (local9 < 0) {
				local14 = local9 + maskOffsetY;
				if (local14 >= 0) {
					if (local14 >= lineOffsets.length) {
						return;
					}
					local23 = SpriteSM.pixelOffset;
					local25 = SpriteSM.uBase + SpriteSM.anInt7143;
					local27 = SpriteSM.vBase + SpriteSM.anInt7142;
					local29 = SpriteSM.anInt7151;
					if (local25 < 0) {
						local181 = (SpriteSM.anInt7153 - local25 - 1) / SpriteSM.anInt7153;
						local29 += local181;
						local25 += SpriteSM.anInt7153 * local181;
						local27 += SpriteSM.anInt7164 * local181;
						local23 += local181;
					}
					@Pc(1319) int local1319;
					if ((local1319 = (local25 + 1 - (super.imageWidth << 12) - SpriteSM.anInt7153) / SpriteSM.anInt7153) > local29) {
						local29 = local1319;
					}
					@Pc(1331) int local1331;
					if ((local1331 = local27 - (super.imageHeight << 12)) >= 0) {
						local181 = (SpriteSM.anInt7164 - local1331) / SpriteSM.anInt7164;
						local29 += local181;
						local25 += SpriteSM.anInt7153 * local181;
						local27 += SpriteSM.anInt7164 * local181;
						local23 += local181;
					}
					@Pc(1365) int local1365;
					if ((local1365 = (local27 - SpriteSM.anInt7164) / SpriteSM.anInt7164) > local29) {
						local29 = local1365;
					}
					local53 = lineOffsets[local14] - maskOffsetX;
					local58 = -lineWidths[local14];
					local64 = local53 + SpriteSM.pixelOffset - local23;
					if (local64 > 0) {
						local23 += local64;
						local29 += local64;
						local25 += SpriteSM.anInt7153 * local64;
						local27 += SpriteSM.anInt7164 * local64;
					} else {
						local58 -= local64;
					}
					if (local29 < local58) {
						local29 = local58;
					}
					while (local29 < 0) {
						local111 = this.pixels[(local27 >> 12) * super.imageWidth + (local25 >> 12)];
						if (local111 == 0) {
							local23++;
						} else {
							local3[local23++] = local111;
						}
						local25 += SpriteSM.anInt7153;
						local27 += SpriteSM.anInt7164;
						local29++;
					}
				}
				local9++;
				SpriteSM.uBase += SpriteSM.anInt7169;
				SpriteSM.vBase += SpriteSM.dvdy;
				SpriteSM.pixelOffset += SpriteSM.viewportStride;
			}
		} else {
			local9 = SpriteSM.anInt7141;
			while (local9 < 0) {
				local14 = local9 + maskOffsetY;
				if (local14 >= 0) {
					if (local14 >= lineOffsets.length) {
						return;
					}
					local23 = SpriteSM.pixelOffset;
					local25 = SpriteSM.uBase + SpriteSM.anInt7143;
					local27 = SpriteSM.vBase + SpriteSM.anInt7142;
					local29 = SpriteSM.anInt7151;
					if (local25 < 0) {
						local181 = (SpriteSM.anInt7153 - local25 - 1) / SpriteSM.anInt7153;
						local29 += local181;
						local25 += SpriteSM.anInt7153 * local181;
						local27 += SpriteSM.anInt7164 * local181;
						local23 += local181;
					}
					@Pc(1539) int local1539;
					if ((local1539 = (local25 + 1 - (super.imageWidth << 12) - SpriteSM.anInt7153) / SpriteSM.anInt7153) > local29) {
						local29 = local1539;
					}
					if (local27 < 0) {
						local181 = (SpriteSM.anInt7164 - local27 - 1) / SpriteSM.anInt7164;
						local29 += local181;
						local25 += SpriteSM.anInt7153 * local181;
						local27 += SpriteSM.anInt7164 * local181;
						local23 += local181;
					}
					@Pc(1587) int local1587;
					if ((local1587 = (local27 + 1 - (super.imageHeight << 12) - SpriteSM.anInt7164) / SpriteSM.anInt7164) > local29) {
						local29 = local1587;
					}
					local53 = lineOffsets[local14] - maskOffsetX;
					local58 = -lineWidths[local14];
					local64 = local53 + SpriteSM.pixelOffset - local23;
					if (local64 > 0) {
						local23 += local64;
						local29 += local64;
						local25 += SpriteSM.anInt7153 * local64;
						local27 += SpriteSM.anInt7164 * local64;
					} else {
						local58 -= local64;
					}
					if (local29 < local58) {
						local29 = local58;
					}
					while (local29 < 0) {
						local111 = this.pixels[(local27 >> 12) * super.imageWidth + (local25 >> 12)];
						if (local111 == 0) {
							local23++;
						} else {
							local3[local23++] = local111;
						}
						local25 += SpriteSM.anInt7153;
						local27 += SpriteSM.anInt7164;
						local29++;
					}
				}
				local9++;
				SpriteSM.uBase += SpriteSM.anInt7169;
				SpriteSM.vBase += SpriteSM.dvdy;
				SpriteSM.pixelOffset += SpriteSM.viewportStride;
			}
		}
	}
}
