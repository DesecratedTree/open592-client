package com.jagex.game.runetek5;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Hashtable;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


@OriginalClass("client!hs")
public final class JavaDefaultSurface extends EnvironmentContext {

	@OriginalMember(owner = "client!hs", name = "u", descriptor = "Ljava/awt/Shape;")
	private Shape clip;

	@OriginalMember(owner = "client!hs", name = "v", descriptor = "Ljava/awt/Image;")
	private Image image;

	@OriginalMember(owner = "client!hs", name = "w", descriptor = "Ljava/awt/Rectangle;")
	private Rectangle clipBox;

	@OriginalMember(owner = "client!hs", name = "x", descriptor = "Ljava/awt/Canvas;")
	private Canvas aCanvas4;

	@OriginalMember(owner = "client!hs", name = "a", descriptor = "(IIILjava/awt/Graphics;BI)V")
	@Override
	public void method4601(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) Graphics arg3, @OriginalArg(5) int arg4) {
		this.clip = arg3.getClip();
		this.clipBox.x = arg2;
		this.clipBox.y = arg0;
		this.clipBox.height = arg4;
		this.clipBox.width = arg1;
		arg3.setClip(this.clipBox);
		arg3.drawImage(this.image, 0, 0, this.aCanvas4);
		arg3.setClip(this.clip);
	}

	@OriginalMember(owner = "client!hs", name = "a", descriptor = "(BLjava/awt/Canvas;)V")
	@Override
	public void method4606(@OriginalArg(1) Canvas arg0) {
		this.aCanvas4 = arg0;
		this.clipBox = new Rectangle();
		this.anInt5857 = this.aCanvas4.getSize().width;
		this.anInt5856 = this.aCanvas4.getSize().height;
		this.anIntArray409 = new int[this.anInt5857 * this.anInt5856];
		@Pc(40) DataBufferInt local40 = new DataBufferInt(this.anIntArray409, this.anIntArray409.length);
		@Pc(48) DirectColorModel local48 = new DirectColorModel(32, 16711680, 65280, 255);
		@Pc(58) WritableRaster local58 = Raster.createWritableRaster(local48.createCompatibleSampleModel(this.anInt5857, this.anInt5856), local40, null);
		this.image = new BufferedImage(local48, local58, false, new Hashtable());
	}

	@OriginalMember(owner = "client!hs", name = "a", descriptor = "(IIILjava/awt/Graphics;)V")
	@Override
	public void method4608(@OriginalArg(3) Graphics arg0) {
		arg0.drawImage(this.image, 0, 0, this.aCanvas4);
	}
}
