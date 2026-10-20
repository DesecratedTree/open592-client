package com.jagex.game.runetek5.graphics.texture;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.deob.s;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

import com.jagex.game.runetek5.graphics.texture.*;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.config.particle.ParticleProducerType;
import com.jagex.game.runetek5.Skybox;
@OriginalClass("client!ps")
public final class OpenGl2DImageTexture extends OpenGl2DTexture {

	@OriginalMember(owner = "client!ps", name = "P", descriptor = "I")
	public final int singleWidth;

	@OriginalMember(owner = "client!ps", name = "U", descriptor = "I")
	public final int singleLength;

	@OriginalMember(owner = "client!ps", name = "V", descriptor = "F")
	public final float s;

	@OriginalMember(owner = "client!ps", name = "S", descriptor = "F")
	public final float captureScreenRegion;

	@OriginalMember(owner = "client!ps", name = "X", descriptor = "Z")
	public final boolean tiled;

	@OriginalMember(owner = "client!ps", name = "<init>", descriptor = "(Lclient!fd;IIIII[BI)V")
	public OpenGl2DImageTexture(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) int internalFormat, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) byte[] data, @OriginalArg(7) int format) {
		super(glToolkit, 3553, internalFormat, arg4, arg5);
		this.singleWidth = arg2;
		this.singleLength = arg3;
		this.method4191(arg2, data, arg3, format, true);
		this.s = (float) arg2 / (float) arg4;
		this.captureScreenRegion = (float) arg3 / (float) arg5;
		this.tiled = false;
		this.method4196(false, false);
	}

	@OriginalMember(owner = "client!ps", name = "<init>", descriptor = "(Lclient!fd;IIIIZ[BI)V")
	public OpenGl2DImageTexture(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) boolean arg5, @OriginalArg(6) byte[] arg6, @OriginalArg(7) int arg7) {
		super(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, true);
		this.singleWidth = arg3;
		this.singleLength = arg4;
		if (super.anInt5441 == 34037) {
			this.s = arg3;
			this.tiled = false;
			this.captureScreenRegion = arg4;
		} else {
			this.s = this.captureScreenRegion = 1.0F;
			this.tiled = true;
		}
	}

	@OriginalMember(owner = "client!ps", name = "<init>", descriptor = "(Lclient!fd;IIII[I)V")
	public OpenGl2DImageTexture(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) int target, @OriginalArg(2) int x, @OriginalArg(3) int totalWidth, @OriginalArg(4) int totalLength, @OriginalArg(5) int[] pixels) {
		super(glToolkit, 3553, 6408, totalWidth, totalLength);
		this.singleLength = x;
		this.singleWidth = target;
		this.method4197(x, pixels, target);
		this.tiled = false;
		this.s = (float) target / (float) totalWidth;
		this.captureScreenRegion = (float) x / (float) totalLength;
		this.method4196(false, false);
	}

	@OriginalMember(owner = "client!ps", name = "<init>", descriptor = "(Lclient!fd;IIIIIIZ)V")
	public OpenGl2DImageTexture(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int singleWidth, @OriginalArg(4) int singleLength, @OriginalArg(5) int width, @OriginalArg(6) int length, @OriginalArg(7) boolean copyFromFramebuffer) {
		super(glToolkit, 3553, x, y, width, length);
		this.singleWidth = singleWidth;
		this.singleLength = singleLength;
		this.tiled = false;
		this.s = (float) singleWidth / (float) width;
		this.captureScreenRegion = (float) singleLength / (float) length;
		this.method4196(false, false);
	}

	@OriginalMember(owner = "client!ps", name = "<init>", descriptor = "(Lclient!fd;IIIIIZ)V")
	public OpenGl2DImageTexture(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) boolean arg6) {
		super(arg0, arg1, arg2, arg3, arg4, arg5);
		this.singleLength = arg5;
		if (super.anInt5441 == 34037) {
			this.captureScreenRegion = arg5;
			this.tiled = false;
			this.s = arg4;
		} else {
			this.tiled = true;
			this.s = this.captureScreenRegion = 1.0F;
		}
		this.singleWidth = arg4;
	}

	@OriginalMember(owner = "client!ps", name = "<init>", descriptor = "(Lclient!fd;IIIZ[I)V")
	public OpenGl2DImageTexture(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) boolean arg4, @OriginalArg(5) int[] arg5) {
		super(arg0, arg1, 6408, arg2, arg3, arg4, arg5, true);
		this.singleWidth = arg2;
		this.singleLength = arg3;
		if (super.anInt5441 == 34037) {
			this.s = arg2;
			this.captureScreenRegion = arg3;
			this.tiled = false;
		} else {
			this.tiled = true;
			this.s = this.captureScreenRegion = 1.0F;
		}
	}

@OriginalMember(owner = "client!ps", name = "Q", descriptor = "I")
	public static int anInt5471;
@OriginalMember(owner = "client!ps", name = "W", descriptor = "I")
	public static int anInt5475;
@OriginalMember(owner = "client!ps", name = "T", descriptor = "I")
	public static int anInt5473 = 0;
@OriginalMember(owner = "client!ps", name = "Y", descriptor = "I")
	public static int anInt5476 = 0;
@OriginalMember(owner = "client!ps", name = "g", descriptor = "(I)V")
	public static void method4198() {
		Skybox.anInt6710 = 0;
		Class126.aClass183_28.method4138();
		Class126.aClass183_28.method4137(ParticleProducerType.aClass4_Sub39_1);
		Skybox.anInt6710++;
	}
}
