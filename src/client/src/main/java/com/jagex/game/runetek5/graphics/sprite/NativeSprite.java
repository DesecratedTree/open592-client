package com.jagex.game.runetek5.graphics.sprite;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;

@OriginalClass("client!l")
public abstract class NativeSprite {

	@OriginalMember(owner = "client!l", name = "<init>", descriptor = "()V")
	protected NativeSprite() {
	}

	@OriginalMember(owner = "client!l", name = "JA", descriptor = "(IIIIIII)V")
	public abstract void drawScaledColored(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int combineMode, @OriginalArg(5) int colorArgb, @OriginalArg(6) int blendMode);

	@OriginalMember(owner = "client!l", name = "a", descriptor = "(FFIILclient!ma;II)V")
	public final void method5533(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1, @OriginalArg(3) int arg2, @OriginalArg(4) ComponentSurface arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5) {
		this.rotate(arg0, arg1, (float) this.scaleWidth() / 2.0F, (float) this.scaleHeight() / 2.0F, 4096, arg2, arg3, arg4, arg5);
	}

	@OriginalMember(owner = "client!l", name = "la", descriptor = "()I")
	public abstract int getWidth();

	@OriginalMember(owner = "client!l", name = "a", descriptor = "(II)V")
	public final void render(@OriginalArg(0) int x, @OriginalArg(1) int y) {
		this.render(x, y, 1, 0, 1);
	}

	@OriginalMember(owner = "client!l", name = "BA", descriptor = "(III)V")
	public abstract void copyAlpha(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int channel);

	@OriginalMember(owner = "client!l", name = "MA", descriptor = "(IIII)V")
	public abstract void setOffsets(@OriginalArg(0) int translateX, @OriginalArg(1) int translateY, @OriginalArg(2) int trimRight, @OriginalArg(3) int trimBottom);

	@OriginalMember(owner = "client!l", name = "r", descriptor = "(IIIII)V")
	public abstract void render(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int combineMode, @OriginalArg(3) int colorArgb, @OriginalArg(4) int blendMode);

	@OriginalMember(owner = "client!l", name = "a", descriptor = "(FFFFFFIII)V")
	protected abstract void a(@OriginalArg(0) float topLeftX, @OriginalArg(1) float topLeftY, @OriginalArg(2) float topRightX, @OriginalArg(3) float topRightY, @OriginalArg(4) float bottomLeftX, @OriginalArg(5) float bottomLeftY, @OriginalArg(6) int combineMode, @OriginalArg(7) int colorArgb, @OriginalArg(8) int arg8);

	@OriginalMember(owner = "client!l", name = "b", descriptor = "(FFFFFFLclient!ma;II)V")
	protected abstract void renderParallelogramImpl(@OriginalArg(0) float topLeftX, @OriginalArg(1) float topLeftY, @OriginalArg(2) float topRightX, @OriginalArg(3) float topRightY, @OriginalArg(4) float bottomLeftX, @OriginalArg(5) float bottomLeftY, @OriginalArg(6) ComponentSurface arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int surfaceOffsetX);

	@OriginalMember(owner = "client!l", name = "T", descriptor = "()I")
	public abstract int scaleHeight();

	@OriginalMember(owner = "client!l", name = "a", descriptor = "(FFIIIII)V")
	public final void drawRotatedCenterColored(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(5) int arg4) {
		this.drawRotatedWithPivot(arg0, arg1, (float) this.scaleWidth() / 2.0F, (float) this.scaleHeight() / 2.0F, arg2, arg3, 0, arg4);
	}

	@OriginalMember(owner = "client!l", name = "t", descriptor = "(IIIIII)V")
	public abstract void captureScreenRegion(@OriginalArg(0) int dstX, @OriginalArg(1) int dstY, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int srcX, @OriginalArg(5) int srcY);

	@OriginalMember(owner = "client!l", name = "a", descriptor = "(IIII)V")
	public final void drawTintedScaled(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height) {
		this.renderImpl(x, y, width, height, 1, 0, 1);
	}

	@OriginalMember(owner = "client!l", name = "s", descriptor = "(IIIIIII)V")
	public abstract void renderImpl(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int combineMode, @OriginalArg(5) int colorArgb, @OriginalArg(6) int blendMode);

	@OriginalMember(owner = "client!l", name = "a", descriptor = "(FFFFIILclient!ma;II)V")
	public final void rotate(@OriginalArg(0) float x, @OriginalArg(1) float y, @OriginalArg(2) float pivotX, @OriginalArg(3) float pivotY, @OriginalArg(4) int zoom, @OriginalArg(5) int rotation, @OriginalArg(6) ComponentSurface surface, @OriginalArg(7) int surfaceOffsetX, @OriginalArg(8) int surfaceOffsetY) {
		if (zoom == 0) {
			return;
		}
		@Pc(9) double local9 = (double) (rotation & 0xFFFF) * 9.587379924285257E-5D;
		@Pc(16) float local16 = (float) Math.sin(local9) * (float) zoom;
		@Pc(23) float local23 = (float) Math.cos(local9) * (float) zoom;
		@Pc(37) float local37 = (-pivotX * local23 + -pivotY * local16) / 4096.0F + x;
		@Pc(50) float local50 = (pivotX * local16 + -pivotY * local23) / 4096.0F + y;
		@Pc(67) float local67 = (((float) this.scaleWidth() - pivotX) * local23 + -pivotY * local16) / 4096.0F + x;
		@Pc(85) float local85 = (-((float) this.scaleWidth() - pivotX) * local16 + -pivotY * local23) / 4096.0F + y;
		@Pc(102) float local102 = (-pivotX * local23 + ((float) this.scaleHeight() - pivotY) * local16) / 4096.0F + x;
		@Pc(118) float local118 = (pivotX * local16 + ((float) this.scaleHeight() - pivotY) * local23) / 4096.0F + y;
		this.renderParallelogramImpl(local37, local50, local67, local85, local102, local118, surface, surfaceOffsetX, surfaceOffsetY);
	}

	@OriginalMember(owner = "client!l", name = "b", descriptor = "(IIII)V")
	public final void drawTiled(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height) {
		this.drawScaledColored(x, y, width, height, 1, 0, 1);
	}

	@OriginalMember(owner = "client!l", name = "a", descriptor = "(FFFFIIIII)V")
	private void drawRotatedWithPivot(@OriginalArg(0) float x, @OriginalArg(1) float y, @OriginalArg(2) float pivotX, @OriginalArg(3) float pivotY, @OriginalArg(4) int zoom, @OriginalArg(5) int rotation, @OriginalArg(6) int mode, @OriginalArg(7) int filter) {
		if (zoom == 0) {
			return;
		}
		@Pc(9) double local9 = (double) (rotation & 0xFFFF) * 9.587379924285257E-5D;
		@Pc(16) float local16 = (float) Math.sin(local9) * (float) zoom;
		@Pc(23) float local23 = (float) Math.cos(local9) * (float) zoom;
		@Pc(37) float local37 = (-pivotX * local23 + -pivotY * local16) / 4096.0F + x;
		@Pc(50) float local50 = (pivotX * local16 + -pivotY * local23) / 4096.0F + y;
		@Pc(67) float local67 = (((float) this.scaleWidth() - pivotX) * local23 + -pivotY * local16) / 4096.0F + x;
		@Pc(85) float local85 = (-((float) this.scaleWidth() - pivotX) * local16 + -pivotY * local23) / 4096.0F + y;
		@Pc(102) float local102 = (-pivotX * local23 + ((float) this.scaleHeight() - pivotY) * local16) / 4096.0F + x;
		@Pc(118) float local118 = (pivotX * local16 + ((float) this.scaleHeight() - pivotY) * local23) / 4096.0F + y;
		this.a(local37, local50, local67, local85, local102, local118, mode, filter, 1);
	}

	@OriginalMember(owner = "client!l", name = "j", descriptor = "()I")
	public abstract int scaleWidth();

	@OriginalMember(owner = "client!l", name = "ma", descriptor = "()I")
	public abstract int getHeight();

	@OriginalMember(owner = "client!l", name = "a", descriptor = "(FFII)V")
	public final void drawRotatedCenter(@OriginalArg(0) float x, @OriginalArg(1) float y, @OriginalArg(2) int zoom, @OriginalArg(3) int rotation) {
		this.drawRotatedWithPivot(x, y, (float) this.scaleWidth() / 2.0F, (float) this.scaleHeight() / 2.0F, zoom, rotation, 1, 0);
	}

	@OriginalMember(owner = "client!l", name = "a", descriptor = "(IILclient!ma;II)V")
	public abstract void drawWithSurface(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) ComponentSurface surface, @OriginalArg(3) int surfaceOffsetX, @OriginalArg(4) int surfaceOffsetY);
}
