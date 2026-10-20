package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.deob.Class145_Sub2;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;

@OriginalClass("client!th")
public final class JavaMonoFont extends AbstractFontRenderer {

	@OriginalMember(owner = "client!th", name = "B", descriptor = "Lclient!bc;")
	private final JavaToolkit toolkit;

	@OriginalMember(owner = "client!th", name = "y", descriptor = "[I")
	private final int[] glyphWidth;

	@OriginalMember(owner = "client!th", name = "z", descriptor = "[I")
	private final int[] glyphHeight;

	@OriginalMember(owner = "client!th", name = "x", descriptor = "[[B")
	private final byte[][] glyphRaster;

	@OriginalMember(owner = "client!th", name = "w", descriptor = "[I")
	private final int[] glyphOffsetY;

	@OriginalMember(owner = "client!th", name = "A", descriptor = "[I")
	private final int[] glyphOffsetX;

	@OriginalMember(owner = "client!th", name = "<init>", descriptor = "(Lclient!bc;Lclient!gt;[Lclient!bb;[I[I)V")
	public JavaMonoFont(@OriginalArg(0) JavaToolkit javaToolkit, @OriginalArg(1) com.jagex.game.runetek5.graphics.font.FontMetrics fontMetrics, @OriginalArg(2) SpriteType[] indexedImages, @OriginalArg(3) int[] glyphWidth, @OriginalArg(4) int[] glyphHeight) {
		super(javaToolkit, fontMetrics);
		this.toolkit = javaToolkit;
		this.glyphWidth = glyphWidth;
		this.glyphHeight = glyphHeight;
		this.glyphRaster = new byte[indexedImages.length][];
		this.glyphOffsetY = new int[indexedImages.length];
		this.glyphOffsetX = new int[indexedImages.length];
		for (@Pc(29) int local29 = 0; local29 < indexedImages.length; local29++) {
			this.glyphRaster[local29] = indexedImages[local29].pixels;
			this.glyphOffsetY[local29] = indexedImages[local29].minY;
			this.glyphOffsetX[local29] = indexedImages[local29].minX;
		}
	}

	@OriginalMember(owner = "client!th", name = "a", descriptor = "([B[IIIIIIII)V")
	private void blitOpaque(@OriginalArg(0) byte[] src, @OriginalArg(1) int[] dst, @OriginalArg(2) int colour, @OriginalArg(3) int srcIndex, @OriginalArg(4) int dstIndex, @OriginalArg(5) int width, @OriginalArg(6) int height, @OriginalArg(7) int dstStep, @OriginalArg(8) int srcStep) {
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

	@OriginalMember(owner = "client!th", name = "OA", descriptor = "(CIIIZ)V")
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
		if ((colour & -16777216) == -16777216) {
			this.blitOpaque(this.glyphRaster[c], this.toolkit.surfaceRaster, colour, local41, local33, local18, local23, local37, local39);
		} else if ((colour & 0xFF000000) != 0) {
			this.blendImage(this.glyphRaster[c], this.toolkit.surfaceRaster, colour, local41, local33, local18, local23, local37, local39);
		}
	}

	@OriginalMember(owner = "client!th", name = "a", descriptor = "(CIIIZLclient!ma;II)V")
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
		if ((colour & -16777216) == -16777216) {
			this.drawGlyphOpaqueMasked(this.glyphRaster[c], this.toolkit.surfaceRaster, colour, local51, local43, local28, local33, local47, local49, x, y, this.glyphWidth[c], mask, offsetX, offsetY);
		} else {
			this.blitBlendedMasked(this.glyphRaster[c], this.toolkit.surfaceRaster, colour, local51, local43, local28, local33, local47, local49, x, y, this.glyphWidth[c], mask, offsetX, offsetY);
		}
	}

	@OriginalMember(owner = "client!th", name = "a", descriptor = "([B[IIIIIIIIIIILclient!ma;II)V")
	private void drawGlyphOpaqueMasked(@OriginalArg(0) byte[] glyphData, @OriginalArg(1) int[] pixels, @OriginalArg(2) int color, @OriginalArg(3) int glyphOffset, @OriginalArg(4) int pixelOffset, @OriginalArg(5) int clippedWidth, @OriginalArg(6) int clippedHeight, @OriginalArg(7) int dstRowPadding, @OriginalArg(8) int srcRowPadding, @OriginalArg(9) int drawX, @OriginalArg(10) int drawY, @OriginalArg(11) int fullGlyphWidth, @OriginalArg(12) ComponentSurface surface, @OriginalArg(13) int surfaceOffsetX, @OriginalArg(14) int surfaceOffsetY) {
		@Pc(2) Class145_Sub2 local2 = (Class145_Sub2) surface;
		@Pc(5) int[] local5 = local2.anIntArray554;
		@Pc(8) int[] local8 = local2.anIntArray553;
		@Pc(10) int local10 = drawY;
		if (surfaceOffsetY > drawY) {
			local10 = surfaceOffsetY;
			pixelOffset += (surfaceOffsetY - drawY) * this.toolkit.anInt430;
			glyphOffset += (surfaceOffsetY - drawY) * fullGlyphWidth;
		}
		@Pc(50) int local50 = surfaceOffsetY + local5.length < drawY + clippedHeight ? surfaceOffsetY + local5.length : drawY + clippedHeight;
		for (@Pc(52) int local52 = local10; local52 < local50; local52++) {
			@Pc(61) int local61 = surfaceOffsetX + local5[local52 - surfaceOffsetY];
			@Pc(67) int local67 = local8[local52 - surfaceOffsetY];
			@Pc(69) int local69 = clippedWidth;
			@Pc(76) int local76;
			if (drawX > local61) {
				local76 = drawX - local61;
				if (local76 >= local67) {
					glyphOffset += clippedWidth + srcRowPadding;
					pixelOffset += clippedWidth + dstRowPadding;
					continue;
				}
				local67 -= local76;
			} else {
				local76 = local61 - drawX;
				if (local76 >= clippedWidth) {
					glyphOffset += clippedWidth + srcRowPadding;
					pixelOffset += clippedWidth + dstRowPadding;
					continue;
				}
				glyphOffset += local76;
				local69 = clippedWidth - local76;
				pixelOffset += local76;
			}
			local76 = 0;
			if (local69 < local67) {
				local67 = local69;
			} else {
				local76 = local69 - local67;
			}
			for (@Pc(143) int local143 = 0; local143 < local67; local143++) {
				if (glyphData[glyphOffset++] == 0) {
					pixelOffset++;
				} else {
					pixels[pixelOffset++] = color;
				}
			}
			glyphOffset += local76 + srcRowPadding;
			pixelOffset += local76 + dstRowPadding;
		}
	}

	@OriginalMember(owner = "client!th", name = "b", descriptor = "([B[IIIIIIIIIIILclient!ma;II)V")
	private void blitBlendedMasked(@OriginalArg(0) byte[] src, @OriginalArg(1) int[] dst, @OriginalArg(2) int colour, @OriginalArg(3) int srcIndex, @OriginalArg(4) int dstIndex, @OriginalArg(5) int width, @OriginalArg(6) int height, @OriginalArg(7) int dstStep, @OriginalArg(8) int srcStep, @OriginalArg(9) int x, @OriginalArg(10) int y, @OriginalArg(11) int srcStride, @OriginalArg(12) ComponentSurface mask, @OriginalArg(13) int maskX, @OriginalArg(14) int maskY) {
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
		@Pc(60) int local60 = colour >>> 24;
		@Pc(64) int local64 = 255 - local60;
		for (@Pc(66) int local66 = local16; local66 < local56; local66++) {
			@Pc(75) int local75 = local5[local66 - maskY] + maskX;
			@Pc(81) int local81 = local8[local66 - maskY];
			@Pc(83) int local83 = width;
			@Pc(90) int local90;
			if (local14 > local75) {
				local90 = local14 - local75;
				if (local90 >= local81) {
					srcIndex += width + srcStep;
					dstIndex += width + dstStep;
					continue;
				}
				local81 -= local90;
			} else {
				local90 = local75 - local14;
				if (local90 >= width) {
					srcIndex += width + srcStep;
					dstIndex += width + dstStep;
					continue;
				}
				srcIndex += local90;
				local83 = width - local90;
				dstIndex += local90;
			}
			local90 = 0;
			if (local83 < local81) {
				local81 = local83;
			} else {
				local90 = local83 - local81;
			}
			for (@Pc(158) int local158 = -local81; local158 < 0; local158++) {
				if (src[srcIndex++] == 0) {
					dstIndex++;
				} else {
					@Pc(182) int local182 = ((colour & 0xFF00FF) * local60 & 0xFF00FF00) + ((colour & 0xFF00) * local60 & 0xFF0000) >> 8;
					@Pc(186) int local186 = dst[dstIndex];
					dst[dstIndex++] = (((local186 & 0xFF00FF) * local64 & 0xFF00FF00) + ((local186 & 0xFF00) * local64 & 0xFF0000) >> 8) + local182;
				}
			}
			srcIndex += local90 + srcStep;
			dstIndex += local90 + dstStep;
		}
	}

	@OriginalMember(owner = "client!th", name = "b", descriptor = "([B[IIIIIIII)V")
	private void blendImage(@OriginalArg(0) byte[] glyphData, @OriginalArg(1) int[] pixels, @OriginalArg(2) int color, @OriginalArg(3) int glyphOffset, @OriginalArg(4) int pixelOffset, @OriginalArg(5) int width, @OriginalArg(6) int height, @OriginalArg(7) int dstRowPadding, @OriginalArg(8) int srcRowPadding) {
		@Pc(3) int local3 = color >>> 24;
		@Pc(7) int local7 = 255 - local3;
		for (@Pc(10) int local10 = -height; local10 < 0; local10++) {
			for (@Pc(14) int local14 = -width; local14 < 0; local14++) {
				if (glyphData[glyphOffset++] == 0) {
					pixelOffset++;
				} else {
					@Pc(38) int local38 = ((color & 0xFF00FF) * local3 & 0xFF00FF00) + ((color & 0xFF00) * local3 & 0xFF0000) >> 8;
					@Pc(42) int local42 = pixels[pixelOffset];
					pixels[pixelOffset++] = (((local42 & 0xFF00FF) * local7 & 0xFF00FF00) + ((local42 & 0xFF00) * local7 & 0xFF0000) >> 8) + local38;
				}
			}
			pixelOffset += dstRowPadding;
			glyphOffset += srcRowPadding;
		}
	}
}
