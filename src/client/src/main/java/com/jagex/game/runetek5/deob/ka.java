package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.graphics.loginscreen.DrawableItem;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;

@OriginalClass("client!ka")
public final class ka extends AbstractFontRenderer implements DrawableItem {

	@OriginalMember(owner = "client!ka", name = "nativeid", descriptor = "J")
	private long nativeid;

	@OriginalMember(owner = "client!ka", name = "<init>", descriptor = "(Lclient!qa;Lclient!na;Lclient!gt;[Lclient!bb;[Lclient!l;)V")
	public ka(@OriginalArg(0) qa arg0, @OriginalArg(1) na arg1, @OriginalArg(2) com.jagex.game.runetek5.graphics.font.FontMetrics fontMetrics, @OriginalArg(3) SpriteType[] indexedImages, @OriginalArg(4) NativeSprite[] sprites) {
		super(arg0, fontMetrics);
		@Pc(7) byte[][] local7 = new byte[indexedImages.length][];
		@Pc(11) int[] local11 = new int[indexedImages.length];
		@Pc(15) int[] local15 = new int[indexedImages.length];
		@Pc(19) int[] local19 = new int[indexedImages.length];
		@Pc(23) int[] local23 = new int[indexedImages.length];
		for (@Pc(25) int local25 = 0; local25 < indexedImages.length; local25++) {
			local7[local25] = indexedImages[local25].pixels;
			local11[local25] = indexedImages[local25].width;
			local15[local25] = indexedImages[local25].height;
			local19[local25] = indexedImages[local25].minX;
			local23[local25] = indexedImages[local25].minY;
		}
		this.ta(arg0, arg1, local7, indexedImages[0].pallete, local11, local15, local19, local23);
	}

	@OriginalMember(owner = "client!ka", name = "finalize", descriptor = "()V")
	@Override
	public void finalize() {
		Static404.method5220(this);
	}

	@OriginalMember(owner = "client!ka", name = "ya", descriptor = "(CIIIZLclient!ma;II)V")
	private native void ya(@OriginalArg(0) char arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) boolean arg4, @OriginalArg(5) ComponentSurface arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7);

	@OriginalMember(owner = "client!ka", name = "EA", descriptor = "()V")
	public native void EA();

	@OriginalMember(owner = "client!ka", name = "a", descriptor = "(CIIIZLclient!ma;II)V")
	@Override
	protected void renderSymbol(@OriginalArg(0) char arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) boolean arg4, @OriginalArg(5) ComponentSurface clippingMask, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7) {
		this.ya(arg0, arg1, arg2, arg3, arg4, clippingMask, arg6, arg7);
	}

	@OriginalMember(owner = "client!ka", name = "ta", descriptor = "(Lclient!qa;Lclient!na;[[B[I[I[I[I[I)V")
	private native void ta(@OriginalArg(0) qa arg0, @OriginalArg(1) na arg1, @OriginalArg(2) byte[][] arg2, @OriginalArg(3) int[] arg3, @OriginalArg(4) int[] arg4, @OriginalArg(5) int[] arg5, @OriginalArg(6) int[] arg6, @OriginalArg(7) int[] arg7);

	@OriginalMember(owner = "client!ka", name = "OA", descriptor = "(CIIIZ)V")
	protected native void OA(@OriginalArg(0) char arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) boolean arg4);
}
