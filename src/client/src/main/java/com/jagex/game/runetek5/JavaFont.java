package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.deob.Class145_Sub2;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;

@OriginalClass("client!qe")
public final class JavaFont extends AbstractFontRenderer {

	@OriginalMember(owner = "client!qe", name = "A", descriptor = "Lclient!bc;")
	private JavaToolkit toolkit;

	@OriginalMember(owner = "client!qe", name = "w", descriptor = "[I")
	private final int[] glyphWidth;

	@OriginalMember(owner = "client!qe", name = "C", descriptor = "[I")
	private final int[] glyphHeight;

	@OriginalMember(owner = "client!qe", name = "x", descriptor = "[[B")
	private final byte[][] glyphRaster;

	@OriginalMember(owner = "client!qe", name = "y", descriptor = "[I")
	private final int[] glyphOffsetY;

	@OriginalMember(owner = "client!qe", name = "z", descriptor = "[I")
	private final int[] glyphOffsetX;

	@OriginalMember(owner = "client!qe", name = "B", descriptor = "[I")
	private final int[] palette;

	@OriginalMember(owner = "client!qe", name = "<init>", descriptor = "(Lclient!bc;Lclient!gt;[Lclient!bb;[I[I)V")
	public JavaFont(@OriginalArg(0) JavaToolkit javaToolkit, @OriginalArg(1) com.jagex.game.runetek5.graphics.font.FontMetrics fontMetrics, @OriginalArg(2) SpriteType[] indexedImages, @OriginalArg(3) int[] glyphWidth, @OriginalArg(4) int[] glyphHeight) {
		super(javaToolkit, fontMetrics);
		this.toolkit = javaToolkit;
		this.toolkit = javaToolkit;
		this.glyphWidth = glyphWidth;
		this.glyphHeight = glyphHeight;
		this.glyphRaster = new byte[indexedImages.length][];
		this.glyphOffsetY = new int[indexedImages.length];
		this.glyphOffsetX = new int[indexedImages.length];
		for (@Pc(32) int local32 = 0; local32 < indexedImages.length; local32++) {
			this.glyphRaster[local32] = indexedImages[local32].pixels;
			this.glyphOffsetY[local32] = indexedImages[local32].minY;
			this.glyphOffsetX[local32] = indexedImages[local32].minX;
		}
		this.palette = indexedImages[0].pallete;
	}

	@OriginalMember(owner = "client!qe", name = "a", descriptor = "([B[I[IIIIIII)V")
	private void blitPalette(@OriginalArg(0) byte[] src, @OriginalArg(1) int[] dst, @OriginalArg(2) int[] palette, @OriginalArg(3) int srcIndex, @OriginalArg(4) int dstIndex, @OriginalArg(5) int width, @OriginalArg(6) int height, @OriginalArg(7) int dstStep, @OriginalArg(8) int srcStep) {
		@Pc(4) int local4 = -(width >> 2);
		@Pc(9) int local9 = -(width & 0x3);
		for (@Pc(14) int local14 = -height; local14 < 0; local14++) {
			for (@Pc(17) int local17 = local4; local17 < 0; local17++) {
				@Pc(24) byte local24;
				if ((local24 = src[srcIndex++]) == 0) {
					dstIndex++;
				} else {
					dst[dstIndex++] = palette[local24 & 0xFF];
				}
				@Pc(42) byte local42;
				if ((local42 = src[srcIndex++]) == 0) {
					dstIndex++;
				} else {
					dst[dstIndex++] = palette[local42 & 0xFF];
				}
				@Pc(60) byte local60;
				if ((local60 = src[srcIndex++]) == 0) {
					dstIndex++;
				} else {
					dst[dstIndex++] = palette[local60 & 0xFF];
				}
				@Pc(78) byte local78;
				if ((local78 = src[srcIndex++]) == 0) {
					dstIndex++;
				} else {
					dst[dstIndex++] = palette[local78 & 0xFF];
				}
			}
			for (@Pc(95) int local95 = local9; local95 < 0; local95++) {
				@Pc(102) byte local102;
				if ((local102 = src[srcIndex++]) == 0) {
					dstIndex++;
				} else {
					dst[dstIndex++] = palette[local102 & 0xFF];
				}
			}
			dstIndex += dstStep;
			srcIndex += srcStep;
		}
	}

	@OriginalMember(owner = "client!qe", name = "a", descriptor = "([B[IIIIIIII)V")
	private void blitSolid(@OriginalArg(0) byte[] src, @OriginalArg(1) int[] dst, @OriginalArg(2) int colour, @OriginalArg(3) int srcIndex, @OriginalArg(4) int dstIndex, @OriginalArg(5) int width, @OriginalArg(6) int height, @OriginalArg(7) int dstStep, @OriginalArg(8) int srcStep) {
		@Pc(4) int local4 = -(width >> 2);
		@Pc(9) int local9 = -(width & 0x3);
		for (@Pc(12) int local12 = -height; local12 < 0; local12++) {
			for (@Pc(15) int local15 = local4; local15 < 0; local15++) {
				if (src[srcIndex++] == 0) {
					dstIndex++;
				} else {
					dst[dstIndex++] = colour;
				}
				if (src[srcIndex++] == 0) {
					dstIndex++;
				} else {
					dst[dstIndex++] = colour;
				}
				if (src[srcIndex++] == 0) {
					dstIndex++;
				} else {
					dst[dstIndex++] = colour;
				}
				if (src[srcIndex++] == 0) {
					dstIndex++;
				} else {
					dst[dstIndex++] = colour;
				}
			}
			for (@Pc(69) int local69 = local9; local69 < 0; local69++) {
				if (src[srcIndex++] == 0) {
					dstIndex++;
				} else {
					dst[dstIndex++] = colour;
				}
			}
			dstIndex += dstStep;
			srcIndex += srcStep;
		}
	}

	@OriginalMember(owner = "client!qe", name = "OA", descriptor = "(CIIIZ)V")
	@Override
	protected void OA(@OriginalArg(0) char c, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int colour, @OriginalArg(4) boolean shadow) {
		x += this.glyphOffsetX[c];
		y += this.glyphOffsetY[c];
		@Pc(18) int local18 = this.glyphWidth[c];
		@Pc(23) int local23 = this.glyphHeight[c];
		@Pc(27) int local27 = this.toolkit.anInt430;
		@Pc(33) int local33 = x + y * local27;
		@Pc(37) int local37 = local27 - local18;
		@Pc(39) int local39 = 0;
		@Pc(41) int local41 = 0;
		@Pc(52) int local52;
		if (y < this.toolkit.clipY1) {
			local52 = this.toolkit.clipY1 - y;
			local23 -= local52;
			y = this.toolkit.clipY1;
			local41 += local52 * local18;
			local33 += local52 * local27;
		}
		if (y + local23 > this.toolkit.clipY2) {
			local23 -= y + local23 - this.toolkit.clipY2;
		}
		if (x < this.toolkit.clipX1) {
			local52 = this.toolkit.clipX1 - x;
			local18 -= local52;
			x = this.toolkit.clipX1;
			local41 += local52;
			local33 += local52;
			local39 += local52;
			local37 += local52;
		}
		if (x + local18 > this.toolkit.clipX2) {
			local52 = x + local18 - this.toolkit.clipX2;
			local18 -= local52;
			local39 += local52;
			local37 += local52;
		}
		if (local18 <= 0 || local23 <= 0) {
			return;
		}
		if (shadow) {
			this.blitSolid(this.glyphRaster[c], this.toolkit.surfaceRaster, colour, local41, local33, local18, local23, local37, local39);
		} else {
			this.blitPalette(this.glyphRaster[c], this.toolkit.surfaceRaster, this.palette, local41, local33, local18, local23, local37, local39);
		}
	}

	@OriginalMember(owner = "client!qe", name = "a", descriptor = "([B[IIIIIIIIIIILclient!ma;II)V")
	private void blitSolidMasked(@OriginalArg(0) byte[] src, @OriginalArg(1) int[] dst, @OriginalArg(2) int colour, @OriginalArg(3) int srcIndex, @OriginalArg(4) int dstIndex, @OriginalArg(5) int width, @OriginalArg(6) int height, @OriginalArg(7) int dstStep, @OriginalArg(8) int srcStep, @OriginalArg(9) int x, @OriginalArg(10) int y, @OriginalArg(11) int srcStride, @OriginalArg(12) ComponentSurface mask, @OriginalArg(13) int maskX, @OriginalArg(14) int maskY) {
		@Pc(2) Class145_Sub2 local2 = (Class145_Sub2) mask;
		@Pc(5) int[] local5 = local2.anIntArray554;
		@Pc(8) int[] local8 = local2.anIntArray553;
		@Pc(14) int local14 = x - this.toolkit.clipX1;
		@Pc(16) int local16 = y;
		if (maskY > y) {
			local16 = maskY;
			dstIndex += (maskY - y) * this.toolkit.anInt430;
			srcIndex += (maskY - y) * srcStride;
		}
		@Pc(56) int local56 = maskY + local5.length < y + height ? maskY + local5.length : y + height;
		for (@Pc(58) int local58 = local16; local58 < local56; local58++) {
			@Pc(67) int local67 = local5[local58 - maskY] + maskX;
			@Pc(73) int local73 = local8[local58 - maskY];
			@Pc(75) int local75 = width;
			@Pc(82) int local82;
			if (local14 > local67) {
				local82 = local14 - local67;
				if (local82 >= local73) {
					srcIndex += width + srcStep;
					dstIndex += width + dstStep;
					continue;
				}
				local73 -= local82;
			} else {
				local82 = local67 - local14;
				if (local82 >= width) {
					srcIndex += width + srcStep;
					dstIndex += width + dstStep;
					continue;
				}
				srcIndex += local82;
				local75 = width - local82;
				dstIndex += local82;
			}
			local82 = 0;
			if (local75 < local73) {
				local73 = local75;
			} else {
				local82 = local75 - local73;
			}
			for (@Pc(150) int local150 = -local73; local150 < 0; local150++) {
				if (src[srcIndex++] == 0) {
					dstIndex++;
				} else {
					dst[dstIndex++] = colour;
				}
			}
			srcIndex += local82 + srcIndex;
			dstIndex += local82 + dstStep;
		}
	}

	@OriginalMember(owner = "client!qe", name = "a", descriptor = "([B[I[IIIIIIIIIIILclient!ma;II)V")
	private void blitPaletteMasked(@OriginalArg(0) byte[] src, @OriginalArg(1) int[] dst, @OriginalArg(2) int[] palette, @OriginalArg(3) int colour, @OriginalArg(4) int srcIndex, @OriginalArg(5) int dstIndex, @OriginalArg(6) int width, @OriginalArg(7) int height, @OriginalArg(8) int dstStep, @OriginalArg(9) int srcStep, @OriginalArg(10) int x, @OriginalArg(11) int y, @OriginalArg(12) int srcStride, @OriginalArg(13) ComponentSurface mask, @OriginalArg(14) int maskX, @OriginalArg(15) int maskY) {
		@Pc(2) Class145_Sub2 local2 = (Class145_Sub2) mask;
		@Pc(5) int[] local5 = local2.anIntArray554;
		@Pc(8) int[] local8 = local2.anIntArray553;
		@Pc(14) int local14 = x - this.toolkit.clipX1;
		@Pc(16) int local16 = y;
		if (maskY > y) {
			local16 = maskY;
			dstIndex += (maskY - y) * this.toolkit.anInt430;
			srcIndex += (maskY - y) * srcStride;
		}
		@Pc(56) int local56 = maskY + local5.length < y + height ? maskY + local5.length : y + height;
		for (@Pc(60) int local60 = local16; local60 < local56; local60++) {
			@Pc(69) int local69 = local5[local60 - maskY] + maskX;
			@Pc(75) int local75 = local8[local60 - maskY];
			@Pc(77) int local77 = width;
			@Pc(84) int local84;
			if (local14 > local69) {
				local84 = local14 - local69;
				if (local84 >= local75) {
					srcIndex += width + srcStep;
					dstIndex += width + dstStep;
					continue;
				}
				local75 -= local84;
			} else {
				local84 = local69 - local14;
				if (local84 >= width) {
					srcIndex += width + srcStep;
					dstIndex += width + dstStep;
					continue;
				}
				srcIndex += local84;
				local77 = width - local84;
				dstIndex += local84;
			}
			local84 = 0;
			if (local77 < local75) {
				local75 = local77;
			} else {
				local84 = local77 - local75;
			}
			for (@Pc(152) int local152 = -local75; local152 < 0; local152++) {
				@Pc(159) byte local159;
				if ((local159 = src[srcIndex++]) == 0) {
					dstIndex++;
				} else {
					dst[dstIndex++] = palette[local159 & 0xFF];
				}
			}
			srcIndex += local84 + srcStep;
			dstIndex += local84 + dstStep;
		}
	}

	@OriginalMember(owner = "client!qe", name = "a", descriptor = "(CIIIZLclient!ma;II)V")
	@Override
	protected void renderSymbol(@OriginalArg(0) char c, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int colour, @OriginalArg(4) boolean shadow, @OriginalArg(5) ComponentSurface mask, @OriginalArg(6) int offsetX, @OriginalArg(7) int offsetY) {
		if (mask == null) {
			this.OA(c, x, y, colour, shadow);
			return;
		}
		x += this.glyphOffsetX[c];
		y += this.glyphOffsetY[c];
		@Pc(28) int local28 = this.glyphWidth[c];
		@Pc(33) int local33 = this.glyphHeight[c];
		@Pc(37) int local37 = this.toolkit.anInt430;
		@Pc(43) int local43 = x + y * local37;
		@Pc(47) int local47 = local37 - local28;
		@Pc(49) int local49 = 0;
		@Pc(51) int local51 = 0;
		@Pc(62) int local62;
		if (y < this.toolkit.clipY1) {
			local62 = this.toolkit.clipY1 - y;
			local33 -= local62;
			y = this.toolkit.clipY1;
			local51 = local62 * local28;
			local43 += local62 * local37;
		}
		if (y + local33 > this.toolkit.clipY2) {
			local33 -= y + local33 - this.toolkit.clipY2;
		}
		if (x < this.toolkit.clipX1) {
			local62 = this.toolkit.clipX1 - x;
			local28 -= local62;
			x = this.toolkit.clipX1;
			local51 += local62;
			local43 += local62;
			local49 = local62;
			local47 += local62;
		}
		if (x + local28 > this.toolkit.clipX2) {
			local62 = x + local28 - this.toolkit.clipX2;
			local28 -= local62;
			local49 += local62;
			local47 += local62;
		}
		if (local28 <= 0 || local33 <= 0) {
			return;
		}
		if (shadow) {
			this.blitSolidMasked(this.glyphRaster[c], this.toolkit.surfaceRaster, colour, local51, local43, local28, local33, local47, local49, x, y, this.glyphWidth[c], mask, offsetX, offsetY);
		} else {
			this.blitPaletteMasked(this.glyphRaster[c], this.toolkit.surfaceRaster, this.palette, colour, local51, local43, local28, local33, local47, local49, x, y, this.glyphWidth[c], mask, offsetX, offsetY);
		}
	}
}
