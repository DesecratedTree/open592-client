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

@OriginalClass("client!s")
public final class s extends AbstractFontRenderer implements DrawableItem {

	@OriginalMember(owner = "client!s", name = "nativeid", descriptor = "J")
	private long nativeid;

	@OriginalMember(owner = "client!s", name = "<init>", descriptor = "(Lclient!qa;Lclient!na;Lclient!gt;[Lclient!bb;[Lclient!l;)V")
	public s(@OriginalArg(0) qa arg0, @OriginalArg(1) na arg1, @OriginalArg(2) com.jagex.game.runetek5.graphics.font.FontMetrics fontMetrics, @OriginalArg(3) SpriteType[] indexedImages, @OriginalArg(4) NativeSprite[] sprites) {
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
		this.NA(arg0, arg1, local7, local11, local15, local19, local23);
	}

	@OriginalMember(owner = "client!s", name = "OA", descriptor = "(CIIIZ)V")
	protected native void OA(@OriginalArg(0) char arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) boolean arg4);

	@OriginalMember(owner = "client!s", name = "EA", descriptor = "()V")
	public native void EA();

	@OriginalMember(owner = "client!s", name = "NA", descriptor = "(Lclient!qa;Lclient!na;[[B[I[I[I[I)V")
	private native void NA(@OriginalArg(0) qa arg0, @OriginalArg(1) na arg1, @OriginalArg(2) byte[][] arg2, @OriginalArg(3) int[] arg3, @OriginalArg(4) int[] arg4, @OriginalArg(5) int[] arg5, @OriginalArg(6) int[] arg6);

	@OriginalMember(owner = "client!s", name = "a", descriptor = "(CIIIZLclient!ma;II)V")
	@Override
	protected void renderSymbol(@OriginalArg(0) char arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) boolean arg4, @OriginalArg(5) ComponentSurface clippingMask, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7) {
		this.A(arg0, arg1, arg2, arg3, arg4, clippingMask, arg6, arg7);
	}

	@OriginalMember(owner = "client!s", name = "A", descriptor = "(CIIIZLclient!ma;II)V")
	private native void A(@OriginalArg(0) char arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) boolean arg4, @OriginalArg(5) ComponentSurface arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7);

	@OriginalMember(owner = "client!s", name = "finalize", descriptor = "()V")
	@Override
	public void finalize() {
		Static404.method5220(this);
	}
}
