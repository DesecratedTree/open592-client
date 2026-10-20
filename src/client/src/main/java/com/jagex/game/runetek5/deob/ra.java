package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.graphics.loginscreen.DrawableItem;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;

@OriginalClass("client!ra")
public final class ra extends NativeSprite implements DrawableItem {

	@OriginalMember(owner = "client!ra", name = "nativeid", descriptor = "J")
	private long nativeid;

	@OriginalMember(owner = "client!ra", name = "<init>", descriptor = "(Lclient!qa;Lclient!na;[IIIII)V")
	public ra(@OriginalArg(0) qa arg0, @OriginalArg(1) na arg1, @OriginalArg(2) int[] arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6) {
		this.p(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	}

	@OriginalMember(owner = "client!ra", name = "<init>", descriptor = "(Lclient!qa;Lclient!na;IIII)V")
	public ra(@OriginalArg(0) qa arg0, @OriginalArg(1) na arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5) {
		this.X(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@OriginalMember(owner = "client!ra", name = "j", descriptor = "()I")
	public native int scaleWidth();

	@OriginalMember(owner = "client!ra", name = "a", descriptor = "(IILclient!ma;II)V")
	@Override
	public void drawWithSurface(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) ComponentSurface arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4) {
		this.f(arg0, arg1, arg2, arg3, arg4);
	}

	@OriginalMember(owner = "client!ra", name = "ma", descriptor = "()I")
	public native int getHeight();

	@OriginalMember(owner = "client!ra", name = "g", descriptor = "(FFFFFFLclient!ma;II)V")
	private native void g(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2, @OriginalArg(3) float arg3, @OriginalArg(4) float arg4, @OriginalArg(5) float arg5, @OriginalArg(6) ComponentSurface arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8);

	@OriginalMember(owner = "client!ra", name = "s", descriptor = "(IIIIIII)V")
	public native void renderImpl(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6);

	@OriginalMember(owner = "client!ra", name = "BA", descriptor = "(III)V")
	public native void copyAlpha(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2);

	@OriginalMember(owner = "client!ra", name = "t", descriptor = "(IIIIII)V")
	public native void captureScreenRegion(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5);

	@OriginalMember(owner = "client!ra", name = "EA", descriptor = "()V")
	public native void EA();

	@OriginalMember(owner = "client!ra", name = "a", descriptor = "(FFFFFFIII)V")
	protected native void a(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2, @OriginalArg(3) float arg3, @OriginalArg(4) float arg4, @OriginalArg(5) float arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8);

	@OriginalMember(owner = "client!ra", name = "T", descriptor = "()I")
	public native int scaleHeight();

	@OriginalMember(owner = "client!ra", name = "la", descriptor = "()I")
	public native int getWidth();

	@OriginalMember(owner = "client!ra", name = "MA", descriptor = "(IIII)V")
	public native void setOffsets(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3);

	@OriginalMember(owner = "client!ra", name = "finalize", descriptor = "()V")
	@Override
	public void finalize() {
		Static404.method5220(this);
	}

	@OriginalMember(owner = "client!ra", name = "r", descriptor = "(IIIII)V")
	public native void render(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4);

	@OriginalMember(owner = "client!ra", name = "b", descriptor = "(FFFFFFLclient!ma;II)V")
	@Override
	protected void renderParallelogramImpl(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2, @OriginalArg(3) float arg3, @OriginalArg(4) float arg4, @OriginalArg(5) float arg5, @OriginalArg(6) ComponentSurface arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8) {
		this.g(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
	}

	@OriginalMember(owner = "client!ra", name = "f", descriptor = "(IILclient!ma;II)V")
	private native void f(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) ComponentSurface arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4);

	@OriginalMember(owner = "client!ra", name = "JA", descriptor = "(IIIIIII)V")
	public native void drawScaledColored(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6);

	@OriginalMember(owner = "client!ra", name = "p", descriptor = "(Lclient!qa;Lclient!na;[IIIII)V")
	private native void p(@OriginalArg(0) qa arg0, @OriginalArg(1) na arg1, @OriginalArg(2) int[] arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6);

	@OriginalMember(owner = "client!ra", name = "X", descriptor = "(Lclient!qa;Lclient!na;IIII)V")
	private native void X(@OriginalArg(0) qa arg0, @OriginalArg(1) na arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5);
}
