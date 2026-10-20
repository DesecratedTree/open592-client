package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.deob.Class145_Sub2;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;

@OriginalClass("client!dc")
public final class JavaMonoAlphaFont extends AbstractFontRenderer {

	@OriginalMember(owner = "client!dc", name = "x", descriptor = "Lclient!bc;")
	private JavaToolkit toolkit;

	@OriginalMember(owner = "client!dc", name = "y", descriptor = "[I")
	private final int[] glyphWidth;

	@OriginalMember(owner = "client!dc", name = "z", descriptor = "[I")
	private final int[] glyphHeight;

	@OriginalMember(owner = "client!dc", name = "w", descriptor = "[[B")
	private final byte[][] glyphAlpha;

	@OriginalMember(owner = "client!dc", name = "B", descriptor = "[I")
	private final int[] glyphOffsetY;

	@OriginalMember(owner = "client!dc", name = "A", descriptor = "[I")
	private final int[] glyphOffsetX;

	@OriginalMember(owner = "client!dc", name = "<init>", descriptor = "(Lclient!bc;Lclient!gt;[Lclient!bb;[I[I)V")
	public JavaMonoAlphaFont(@OriginalArg(0) JavaToolkit javaToolkit, @OriginalArg(1) com.jagex.game.runetek5.graphics.font.FontMetrics fontMetrics, @OriginalArg(2) SpriteType[] indexedImages, @OriginalArg(3) int[] glyphWidth, @OriginalArg(4) int[] glyphHeight) {
		super(javaToolkit, fontMetrics);
		this.toolkit = javaToolkit;
		this.toolkit = javaToolkit;
		this.glyphWidth = glyphWidth;
		this.glyphHeight = glyphHeight;
		this.glyphAlpha = new byte[indexedImages.length][];
		this.glyphOffsetY = new int[indexedImages.length];
		this.glyphOffsetX = new int[indexedImages.length];
		for (@Pc(32) int local32 = 0; local32 < indexedImages.length; local32++) {
			this.glyphAlpha[local32] = indexedImages[local32].alpha;
			this.glyphOffsetY[local32] = indexedImages[local32].minY;
			this.glyphOffsetX[local32] = indexedImages[local32].minX;
		}
	}

	@OriginalMember(owner = "client!dc", name = "a", descriptor = "([B[IIIIIIIIIIILclient!ma;II)V")
	private void blitMasked(@OriginalArg(0) byte[] src, @OriginalArg(1) int[] dst, @OriginalArg(2) int colour, @OriginalArg(3) int srcIndex, @OriginalArg(4) int dstIndex, @OriginalArg(5) int width, @OriginalArg(6) int height, @OriginalArg(7) int dstStep, @OriginalArg(8) int srcStep, @OriginalArg(9) int x, @OriginalArg(10) int y, @OriginalArg(11) int srcStride, @OriginalArg(12) ComponentSurface mask, @OriginalArg(13) int maskX, @OriginalArg(14) int maskY) {
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
				@Pc(158) int local158 = src[srcIndex++] & 0xFF;
				if (local158 == 0) {
					dstIndex++;
				} else {
					@Pc(178) int local178 = ((colour & 0xFF00FF) * local158 & 0xFF00FF00) + ((colour & 0xFF00) * local158 & 0xFF0000) >> 8;
					local158 = 256 - local158;
					@Pc(186) int local186 = dst[dstIndex];
					dst[dstIndex++] = (((local186 & 0xFF00FF) * local158 & 0xFF00FF00) + ((local186 & 0xFF00) * local158 & 0xFF0000) >> 8) + local178;
				}
			}
			srcIndex += local82 + srcStep;
			dstIndex += local82 + dstStep;
		}
	}

	@OriginalMember(owner = "client!dc", name = "a", descriptor = "(CIIIZLclient!ma;II)V")
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
		if (local28 > 0 && local33 > 0) {
			this.blitMasked(this.glyphAlpha[c], this.toolkit.surfaceRaster, colour, local51, local43, local28, local33, local47, local49, x, y, this.glyphWidth[c], mask, offsetX, offsetY);
		}
	}

	@OriginalMember(owner = "client!dc", name = "OA", descriptor = "(CIIIZ)V")
	@Override
	protected void OA(@OriginalArg(0) char c, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int color, @OriginalArg(4) boolean shadow) {
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
		if (local18 > 0 && local23 > 0) {
			this.blendGlyphPixels(this.glyphAlpha[c], this.toolkit.surfaceRaster, color, local41, local33, local18, local23, local37, local39);
		}
	}

	@OriginalMember(owner = "client!dc", name = "a", descriptor = "([B[IIIIIIII)V")
	private void blendGlyphPixels(@OriginalArg(0) byte[] glyphData, @OriginalArg(1) int[] pixels, @OriginalArg(2) int color, @OriginalArg(3) int glyphOffset, @OriginalArg(4) int pixelOffset, @OriginalArg(5) int clippedWidth, @OriginalArg(6) int clippedHeight, @OriginalArg(7) int dstRowPadding, @OriginalArg(8) int srcRowPadding) {
		for (@Pc(2) int local2 = -clippedHeight; local2 < 0; local2++) {
			for (@Pc(6) int local6 = -clippedWidth; local6 < 0; local6++) {
				@Pc(14) int local14 = glyphData[glyphOffset++] & 0xFF;
				if (local14 == 0) {
					pixelOffset++;
				} else {
					@Pc(34) int local34 = ((color & 0xFF00FF) * local14 & 0xFF00FF00) + ((color & 0xFF00) * local14 & 0xFF0000) >> 8;
					local14 = 256 - local14;
					@Pc(42) int local42 = pixels[pixelOffset];
					pixels[pixelOffset++] = (((local42 & 0xFF00FF) * local14 & 0xFF00FF00) + ((local42 & 0xFF00) * local14 & 0xFF0000) >> 8) + local34;
				}
			}
			pixelOffset += dstRowPadding;
			glyphOffset += srcRowPadding;
		}
	}
}
