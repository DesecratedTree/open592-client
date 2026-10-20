package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.deob.Class145_Sub2;
import com.jagex.game.runetek5.deob.da;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.*;
import com.jagex.game.runetek5.SpriteSM;
@OriginalClass("client!sf")
public abstract class SpriteSM extends NativeSprite {

	@OriginalMember(owner = "client!sf", name = "i", descriptor = "I")
	protected int topMargin;

	@OriginalMember(owner = "client!sf", name = "p", descriptor = "I")
	protected int trimRight;

	@OriginalMember(owner = "client!sf", name = "r", descriptor = "I")
	protected int leftMargin;

	@OriginalMember(owner = "client!sf", name = "v", descriptor = "[I")
	private int[] cachedClipping;

	@OriginalMember(owner = "client!sf", name = "x", descriptor = "I")
	protected int trimBottom;

	@OriginalMember(owner = "client!sf", name = "H", descriptor = "Lclient!bc;")
	protected final JavaToolkit toolkit;

	@OriginalMember(owner = "client!sf", name = "D", descriptor = "I")
	protected final int imageWidth;

	@OriginalMember(owner = "client!sf", name = "e", descriptor = "I")
	protected final int imageHeight;

	@OriginalMember(owner = "client!sf", name = "<init>", descriptor = "(Lclient!bc;II)V")
	protected SpriteSM(@OriginalArg(0) JavaToolkit javaToolkit, @OriginalArg(1) int imageWidth, @OriginalArg(2) int imageHeight) {
		this.toolkit = javaToolkit;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}

	@OriginalMember(owner = "client!sf", name = "ma", descriptor = "()I")
	@Override
	public final int getHeight() {
		return this.imageHeight;
	}

	@OriginalMember(owner = "client!sf", name = "la", descriptor = "()I")
	@Override
	public final int getWidth() {
		return this.imageWidth;
	}

	@OriginalMember(owner = "client!sf", name = "b", descriptor = "(FFFFFFLclient!ma;II)V")
	@Override
	protected final void renderParallelogramImpl(@OriginalArg(0) float centerX, @OriginalArg(1) float centerY, @OriginalArg(2) float x1, @OriginalArg(3) float y1, @OriginalArg(4) float x2, @OriginalArg(5) float y2, @OriginalArg(6) ComponentSurface arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8) {
		if (this.toolkit.stopped()) {
			throw new IllegalStateException();
		}
		this.method5543(centerX, centerY, x1, y1, x2, y2);
		@Pc(18) Class145_Sub2 local18 = (Class145_Sub2) arg6;
		this.blitParallelogramMasked(local18.anIntArray554, local18.anIntArray553, SpriteSM.anInt7170 - arg7, -arg8 - (SpriteSM.anInt7141 - SpriteSM.anInt7149));
	}

	@OriginalMember(owner = "client!sf", name = "a", descriptor = "(FFFFFF)V")
	private void method5543(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2, @OriginalArg(3) float arg3, @OriginalArg(4) float arg4, @OriginalArg(5) float arg5) {
		@Pc(8) int local8 = this.leftMargin + this.imageWidth + this.trimRight;
		@Pc(17) int local17 = this.topMargin + this.imageHeight + this.trimBottom;
		@Pc(32) float local32;
		@Pc(39) float local39;
		@Pc(46) float local46;
		@Pc(53) float local53;
		@Pc(59) float local59;
		@Pc(65) float local65;
		@Pc(71) float local71;
		@Pc(77) float local77;
		if (local8 != this.imageWidth || local17 != this.imageHeight) {
			local32 = (arg2 - arg0) / (float) local8;
			local39 = (arg3 - arg1) / (float) local8;
			local46 = (arg4 - arg0) / (float) local17;
			local53 = (arg5 - arg1) / (float) local17;
			local59 = local46 * (float) this.topMargin;
			local65 = local53 * (float) this.topMargin;
			local71 = local32 * (float) this.leftMargin;
			local77 = local39 * (float) this.leftMargin;
			@Pc(84) float local84 = -local32 * (float) this.trimRight;
			@Pc(91) float local91 = -local39 * (float) this.trimRight;
			@Pc(98) float local98 = -local46 * (float) this.trimBottom;
			@Pc(105) float local105 = -local53 * (float) this.trimBottom;
			arg0 += local71 + local59;
			arg1 += local77 + local65;
			arg2 += local84 + local59;
			arg3 += local91 + local65;
			arg4 += local71 + local98;
			arg5 += local77 + local105;
		}
		local32 = arg4 + arg2 - arg0;
		local39 = arg3 + arg5 - arg1;
		if (arg0 < arg2) {
			local46 = arg0;
			local53 = arg2;
		} else {
			local46 = arg2;
			local53 = arg0;
		}
		if (arg4 < local46) {
			local46 = arg4;
		}
		if (local32 < local46) {
			local46 = local32;
		}
		if (arg4 > local53) {
			local53 = arg4;
		}
		if (local32 > local53) {
			local53 = local32;
		}
		if (arg1 < arg3) {
			local59 = arg1;
			local65 = arg3;
		} else {
			local59 = arg3;
			local65 = arg1;
		}
		if (arg5 < local59) {
			local59 = arg5;
		}
		if (local39 < local59) {
			local59 = local39;
		}
		if (arg5 > local65) {
			local65 = arg5;
		}
		if (local39 > local65) {
			local65 = local39;
		}
		if (local46 < (float) this.toolkit.clipX1) {
			local46 = this.toolkit.clipX1;
		}
		if (local53 > (float) this.toolkit.clipX2) {
			local53 = this.toolkit.clipX2;
		}
		if (local59 < (float) this.toolkit.clipY1) {
			local59 = this.toolkit.clipY1;
		}
		if (local65 > (float) this.toolkit.clipY2) {
			local65 = this.toolkit.clipY2;
		}
		local53 = local46 - local53;
		if (local53 >= 0.0F) {
			return;
		}
		local65 = local59 - local65;
		if (local65 >= 0.0F) {
			return;
		}
		SpriteSM.viewportStride = this.toolkit.anInt430;
		SpriteSM.pixelOffset = (int) ((float) ((int) local59 * SpriteSM.viewportStride) + local46);
		local71 = (arg2 - arg0) * (arg5 - arg1) - (arg3 - arg1) * (arg4 - arg0);
		local77 = (arg4 - arg0) * (arg3 - arg1) - (arg5 - arg1) * (arg2 - arg0);
		SpriteSM.anInt7153 = (int) ((arg5 - arg1) * 4096.0F * (float) this.imageWidth / local71);
		SpriteSM.anInt7164 = (int) ((arg3 - arg1) * 4096.0F * (float) this.imageHeight / local77);
		SpriteSM.anInt7169 = (int) ((arg4 - arg0) * 4096.0F * (float) this.imageWidth / local77);
		SpriteSM.dvdy = (int) ((arg2 - arg0) * 4096.0F * (float) this.imageHeight / local71);
		SpriteSM.anInt7140 = (int) (local46 * 16.0F + 8.0F - (arg0 + arg2 + arg4 + local32) / 4.0F * 16.0F);
		SpriteSM.anInt7163 = (int) (local59 * 16.0F + 8.0F - (arg1 + arg3 + arg5 + local39) / 4.0F * 16.0F);
		SpriteSM.uBase = (this.imageWidth >> 1 << 12) + (SpriteSM.anInt7163 * SpriteSM.anInt7169 >> 4);
		SpriteSM.vBase = (this.imageHeight >> 1 << 12) + (SpriteSM.anInt7163 * SpriteSM.dvdy >> 4);
		SpriteSM.anInt7143 = SpriteSM.anInt7140 * SpriteSM.anInt7153 >> 4;
		SpriteSM.anInt7142 = SpriteSM.anInt7140 * SpriteSM.anInt7164 >> 4;
		SpriteSM.anInt7170 = (int) local46;
		SpriteSM.anInt7151 = (int) local53;
		SpriteSM.anInt7149 = (int) local59;
		SpriteSM.anInt7141 = (int) local65;
	}

	@OriginalMember(owner = "client!sf", name = "j", descriptor = "()I")
	@Override
	public final int scaleWidth() {
		return this.leftMargin + this.imageWidth + this.trimRight;
	}

	@OriginalMember(owner = "client!sf", name = "MA", descriptor = "(IIII)V")
	@Override
	public final void setOffsets(@OriginalArg(0) int leftMargin, @OriginalArg(1) int topMargin, @OriginalArg(2) int trimRight, @OriginalArg(3) int trimBottom) {
		this.leftMargin = leftMargin;
		this.topMargin = topMargin;
		this.trimRight = trimRight;
		this.trimBottom = trimBottom;
	}

	@OriginalMember(owner = "client!sf", name = "b", descriptor = "(II)V")
	protected abstract void blitParallelogram(@OriginalArg(0) int op);

	@OriginalMember(owner = "client!sf", name = "r", descriptor = "(IIIII)V")
	public abstract void render(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int combineMode, @OriginalArg(3) int colorArgb, @OriginalArg(4) int blendMode);

	@OriginalMember(owner = "client!sf", name = "a", descriptor = "(IILclient!ma;II)V")
	public abstract void drawWithSurface(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) ComponentSurface surface, @OriginalArg(3) int surfaceOffsetX, @OriginalArg(4) int surfaceOffsetY);

	@OriginalMember(owner = "client!sf", name = "s", descriptor = "(IIIIIII)V")
	public abstract void renderImpl(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int combineMode, @OriginalArg(5) int colorArgb, @OriginalArg(6) int blendMode);

	@OriginalMember(owner = "client!sf", name = "T", descriptor = "()I")
	@Override
	public final int scaleHeight() {
		return this.topMargin + this.imageHeight + this.trimBottom;
	}

	@OriginalMember(owner = "client!sf", name = "a", descriptor = "(FFFFFFIII)V")
	@Override
	protected final void a(@OriginalArg(0) float topLeftX, @OriginalArg(1) float topLeftY, @OriginalArg(2) float topRightX, @OriginalArg(3) float topRightY, @OriginalArg(4) float bottomLeftX, @OriginalArg(5) float bottomLeftY, @OriginalArg(6) int combineMode, @OriginalArg(7) int colour, @OriginalArg(8) int arg8) {
		if (this.toolkit.stopped()) {
			throw new IllegalStateException();
		}
		this.method5543(topLeftX, topLeftY, topRightX, topRightY, bottomLeftX, bottomLeftY);
		SpriteSM.colour = colour;
		if (combineMode != 1) {
			SpriteSM.alpha = colour >>> 24;
			SpriteSM.invAlpha = 256 - SpriteSM.alpha;
			if (combineMode == 0) {
				SpriteSM.red = colour >> 16 & 0xFF;
				SpriteSM.green = colour >> 8 & 0xFF;
				SpriteSM.blue = colour & 0xFF;
			} else if (combineMode == 2) {
				SpriteSM.lerpAlpha = colour >>> 24;
				SpriteSM.lerpInvAlpha = 256 - SpriteSM.lerpAlpha;
				@Pc(66) int local66 = (colour & 0xFF00FF) * SpriteSM.lerpInvAlpha & 0xFF00FF00;
				@Pc(74) int local74 = (colour & 0xFF00) * SpriteSM.lerpInvAlpha & 0xFF0000;
				SpriteSM.lerpColour = (local66 | local74) >>> 8;
			}
		}
		if (combineMode == 1) {
			this.blitParallelogram(1);
		} else if (combineMode == 0) {
			this.blitParallelogram(0);
		} else if (combineMode == 3) {
			this.blitParallelogram(3);
		} else if (combineMode == 2) {
			this.blitParallelogram(2);
		}
	}

	@OriginalMember(owner = "client!sf", name = "JA", descriptor = "(IIIIIII)V")
	@Override
	public final void drawScaledColored(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int combineMode, @OriginalArg(5) int colorArgb, @OriginalArg(6) int blendMode) {
		if (this.toolkit.stopped()) {
			throw new IllegalStateException();
		}
		if (this.cachedClipping == null) {
			this.cachedClipping = new int[4];
		}
		this.toolkit.da(this.cachedClipping);
		this.toolkit.fa(this.toolkit.clipX1, this.toolkit.clipY1, x + width, y + height);
		@Pc(37) int local37 = this.scaleWidth();
		@Pc(40) int local40 = this.scaleHeight();
		@Pc(48) int local48 = (width + local37 - 1) / local37;
		@Pc(56) int local56 = (height + local40 - 1) / local40;
		for (@Pc(58) int local58 = 0; local58 < local56; local58++) {
			@Pc(63) int local63 = local58 * local40;
			for (@Pc(65) int local65 = 0; local65 < local48; local65++) {
				this.render(x + local65 * local37, y + local63, combineMode, colorArgb, blendMode);
			}
		}
		this.toolkit.OA(this.cachedClipping[0], this.cachedClipping[1], this.cachedClipping[2], this.cachedClipping[3]);
	}

	@OriginalMember(owner = "client!sf", name = "a", descriptor = "([I[III)V")
	protected abstract void blitParallelogramMasked(@OriginalArg(0) int[] rowStartX, @OriginalArg(1) int[] rowWidth, @OriginalArg(2) int maskOffsetX, @OriginalArg(3) int maskOffsetY);

@OriginalMember(owner = "client!sf", name = "a", descriptor = "I")
	public static int anInt7140;
@OriginalMember(owner = "client!sf", name = "b", descriptor = "I")
	public static int anInt7141;
@OriginalMember(owner = "client!sf", name = "c", descriptor = "I")
	public static int anInt7142;
@OriginalMember(owner = "client!sf", name = "d", descriptor = "I")
	public static int anInt7143;
@OriginalMember(owner = "client!sf", name = "f", descriptor = "I")
	public static int uBase;
@OriginalMember(owner = "client!sf", name = "g", descriptor = "I")
	public static int vBase;
@OriginalMember(owner = "client!sf", name = "j", descriptor = "I")
	public static int anInt7149;
@OriginalMember(owner = "client!sf", name = "k", descriptor = "I")
	public static int dvdy;
@OriginalMember(owner = "client!sf", name = "l", descriptor = "I")
	public static int anInt7151;
@OriginalMember(owner = "client!sf", name = "n", descriptor = "I")
	public static int anInt7153;
@OriginalMember(owner = "client!sf", name = "o", descriptor = "I")
	public static int viewportStride;
@OriginalMember(owner = "client!sf", name = "q", descriptor = "I")
	public static int pixelOffset;
@OriginalMember(owner = "client!sf", name = "u", descriptor = "I")
	public static int colour;
@OriginalMember(owner = "client!sf", name = "y", descriptor = "I")
	public static int anInt7163;
@OriginalMember(owner = "client!sf", name = "z", descriptor = "I")
	public static int anInt7164;
@OriginalMember(owner = "client!sf", name = "F", descriptor = "I")
	public static int anInt7169;
@OriginalMember(owner = "client!sf", name = "I", descriptor = "I")
	public static int anInt7170;
@OriginalMember(owner = "client!sf", name = "h", descriptor = "I")
	public static int lerpAlpha = 0;
@OriginalMember(owner = "client!sf", name = "m", descriptor = "I")
	public static int lerpColour = 0;
@OriginalMember(owner = "client!sf", name = "s", descriptor = "I")
	public static int blue = 0;
@OriginalMember(owner = "client!sf", name = "t", descriptor = "I")
	public static int invAlpha = 0;
@OriginalMember(owner = "client!sf", name = "w", descriptor = "I")
	public static int alpha = 0;
@OriginalMember(owner = "client!sf", name = "A", descriptor = "I")
	public static int red = 0;
@OriginalMember(owner = "client!sf", name = "B", descriptor = "I")
	public static int lerpInvAlpha = 0;
@OriginalMember(owner = "client!sf", name = "C", descriptor = "I")
	public static int green = 0;
}
