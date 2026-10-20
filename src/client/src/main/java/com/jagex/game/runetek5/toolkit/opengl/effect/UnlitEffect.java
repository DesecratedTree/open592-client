package com.jagex.game.runetek5.toolkit.opengl.effect;

import java.awt.Canvas;
import java.awt.Dimension;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.deob.ka;
import com.jagex.game.runetek5.deob.qa;

@OriginalClass("client!za")
public final class UnlitEffect extends Country {

	@OriginalMember(owner = "client!za", name = "nativeid", descriptor = "J")
	private long nativeid;

	@OriginalMember(owner = "client!za", name = "l", descriptor = "Ljava/awt/Canvas;")
	private Canvas aCanvas8;

	@OriginalMember(owner = "client!za", name = "<init>", descriptor = "(Lclient!qa;Ljava/awt/Canvas;)V")
	public UnlitEffect(@OriginalArg(0) qa arg0, @OriginalArg(1) Canvas arg1) {
		this.aCanvas8 = arg1;
		@Pc(7) Dimension local7 = arg1.getSize();
		this.render(arg0, this.aCanvas8, local7.width, local7.height);
	}

	@OriginalMember(owner = "client!za", name = "d", descriptor = "(IIII)V")
	public native void d(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3);

	@OriginalMember(owner = "client!za", name = "a", descriptor = "()V")
	public void method5685() {
		this.ca();
		this.nativeid = 0L;
		this.aCanvas8 = null;
	}

	@OriginalMember(owner = "client!za", name = "finalize", descriptor = "()V")
	@Override
	public void finalize() {
		if (this.nativeid != 0L) {
			this.ca();
		}
	}

	@OriginalMember(owner = "client!za", name = "H", descriptor = "(Ljava/awt/Canvas;II)V")
	public native void H(@OriginalArg(0) Canvas arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2);

	@OriginalMember(owner = "client!za", name = "ka", descriptor = "()V")
	public native void ka();

	@OriginalMember(owner = "client!za", name = "ca", descriptor = "()V")
	private native void ca();

	@OriginalMember(owner = "client!za", name = "r", descriptor = "(Lclient!qa;Ljava/awt/Canvas;II)V")
	private native void render(@OriginalArg(0) qa arg0, @OriginalArg(1) Canvas arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3);
}
