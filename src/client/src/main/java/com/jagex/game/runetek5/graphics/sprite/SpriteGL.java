package com.jagex.game.runetek5.graphics.sprite;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.deob.Class145_Sub1;
import com.jagex.game.runetek5.deob.Static331;
import com.jagex.game.runetek5.deob.s;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.graphics.texture.OpenGl2DImageTexture;

import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.OpenGlArbElementArrayBuffer;
import com.jagex.game.runetek5.deob.Class16_Sub1;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub1;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.deob.Class16_Sub4;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub1;
import com.jagex.game.runetek5.deob.Class16_Sub5;
import com.jagex.game.runetek5.deob.Class16_Sub5_Sub1;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class269;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Static102;
import com.jagex.game.runetek5.deob.Static14;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static298;
import com.jagex.game.runetek5.deob.Static73;
import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.scene.CollisionMap;
import com.jagex.game.runetek5.graphics.sprite.*;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpPerlinNoise;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.deob.Class4_Sub19;
import com.jagex.game.runetek5.deob.Class193;
import com.jagex.game.runetek5.deob.Class26;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.toolkit.opengl.GlParticleRenderer;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.graphics.font.FontMetrics;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchNode;
import com.jagex.game.runetek5.config.param.ParamTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub3;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.deob.Class244;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.EnvironmentContext;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpVoronoiNoise;
import com.jagex.game.runetek5.deob.Class225;
@OriginalClass("client!ut")
public final class SpriteGL extends NativeSprite {

	@OriginalMember(owner = "client!ut", name = "h", descriptor = "Lclient!ps;")
	private OpenGl2DImageTexture channelMaskTexture;

	@OriginalMember(owner = "client!ut", name = "c", descriptor = "I")
	private int anInt6747 = 0;

	@OriginalMember(owner = "client!ut", name = "d", descriptor = "Z")
	private boolean stretched = false;

	@OriginalMember(owner = "client!ut", name = "f", descriptor = "I")
	private int channelMaskIndex = 0;

	@OriginalMember(owner = "client!ut", name = "i", descriptor = "I")
	private int anInt6751 = 0;

	@OriginalMember(owner = "client!ut", name = "g", descriptor = "I")
	private int anInt6750 = 0;

	@OriginalMember(owner = "client!ut", name = "w", descriptor = "I")
	private int anInt6763 = 0;

	@OriginalMember(owner = "client!ut", name = "v", descriptor = "Lclient!fd;")
	private final GlToolkit toolkit;

	@OriginalMember(owner = "client!ut", name = "o", descriptor = "Lclient!ps;")
	private final OpenGl2DImageTexture texture2d;

	@OriginalMember(owner = "client!ut", name = "<init>", descriptor = "(Lclient!fd;IIII)V")
	public SpriteGL(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) int width, @OriginalArg(2) int arg2, @OriginalArg(3) int pixels, @OriginalArg(4) int pixelOffset) {
		this.toolkit = glToolkit;
		this.texture2d = Static331.method4516(arg2, pixelOffset, width, pixels, glToolkit);
	}

	@OriginalMember(owner = "client!ut", name = "<init>", descriptor = "(Lclient!fd;II[III)V")
	public SpriteGL(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int[] arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5) {
		this.toolkit = arg0;
		this.texture2d = Class230.method5095(arg0, arg2, arg3, arg1);
	}

	@OriginalMember(owner = "client!ut", name = "b", descriptor = "(FFFFFFLclient!ma;II)V")
	@Override
	protected void renderParallelogramImpl(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2, @OriginalArg(3) float arg3, @OriginalArg(4) float arg4, @OriginalArg(5) float arg5, @OriginalArg(6) ComponentSurface arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8) {
		@Pc(7) OpenGl2DImageTexture local7 = ((Class145_Sub1) arg6).aClass6_Sub1_Sub1_5;
		@Pc(14) float local14;
		@Pc(18) float local18;
		@Pc(25) float local25;
		@Pc(32) float local32;
		if (this.stretched) {
			local14 = this.scaleWidth();
			local18 = this.scaleHeight();
			local25 = (arg2 - arg0) / local14;
			local32 = (arg3 - arg1) / local14;
			@Pc(39) float local39 = (arg4 - arg0) / local18;
			@Pc(46) float local46 = (arg5 - arg1) / local18;
			@Pc(52) float local52 = (float) this.anInt6750 * local39;
			@Pc(58) float local58 = local46 * (float) this.anInt6750;
			@Pc(64) float local64 = (float) this.anInt6763 * local25;
			@Pc(70) float local70 = (float) this.anInt6763 * local32;
			@Pc(77) float local77 = -local25 * (float) this.anInt6747;
			@Pc(84) float local84 = -local32 * (float) this.anInt6747;
			@Pc(91) float local91 = -local39 * (float) this.anInt6751;
			arg1 = local70 + arg1 + local58;
			arg4 = local64 + arg4 + local91;
			arg2 = local52 + arg2 + local77;
			arg3 = local84 + arg3 + local58;
			arg0 = local64 + arg0 + local52;
			@Pc(128) float local128 = -local46 * (float) this.anInt6751;
			arg5 = local128 + local70 + arg5;
		}
		local14 = arg2 + arg4 - arg0;
		local18 = arg3 + arg5 - arg1;
		this.texture2d.method4181(true);
		this.toolkit.method1930();
		this.toolkit.setActiveTexture(this.texture2d);
		this.toolkit.method1900(1);
		this.toolkit.method1936(1);
		this.toolkit.setActiveTexture(local7);
		this.toolkit.method1912(7681, 8448);
		this.toolkit.method1945(0, 768, 34168);
		this.toolkit.method1958(1);
		local25 = local7.s / (float) local7.singleWidth;
		local32 = local7.captureScreenRegion / (float) local7.singleLength;
		OpenGL.glBegin(7);
		OpenGL.glColor3f(1.0F, 1.0F, 1.0F);
		OpenGL.glMultiTexCoord2f(33984, 0.0F, this.texture2d.captureScreenRegion);
		OpenGL.glMultiTexCoord2f(33985, local25 * (arg0 - (float) arg7), local7.captureScreenRegion - local32 * ((float) -arg8 + arg1));
		OpenGL.glVertex2f(arg0, arg1);
		OpenGL.glMultiTexCoord2f(33984, 0.0F, 0.0F);
		OpenGL.glMultiTexCoord2f(33985, ((float) -arg7 + arg4) * local25, local7.captureScreenRegion - (arg5 - (float) arg8) * local32);
		OpenGL.glVertex2f(arg4, arg5);
		OpenGL.glMultiTexCoord2f(33984, this.texture2d.s, 0.0F);
		OpenGL.glMultiTexCoord2f(33985, ((float) -arg7 + local14) * local25, local7.captureScreenRegion - ((float) -arg8 + local18) * local32);
		OpenGL.glVertex2f(local14, local18);
		OpenGL.glMultiTexCoord2f(33984, this.texture2d.s, this.texture2d.captureScreenRegion);
		OpenGL.glMultiTexCoord2f(33985, local25 * (arg2 - (float) arg7), local7.captureScreenRegion - local32 * ((float) -arg8 + arg3));
		OpenGL.glVertex2f(arg2, arg3);
		OpenGL.glEnd();
		this.toolkit.method1945(0, 768, 5890);
		this.toolkit.method1900(0);
		this.toolkit.setActiveTexture(null);
		this.toolkit.method1936(0);
	}

	@OriginalMember(owner = "client!ut", name = "la", descriptor = "()I")
	@Override
	public int getWidth() {
		return this.texture2d.singleWidth;
	}

	@OriginalMember(owner = "client!ut", name = "a", descriptor = "(B)V")
	private void method5291() {
		this.toolkit.method1936(1);
		this.toolkit.setActiveTexture(null);
		this.toolkit.method1912(8448, 8448);
		this.toolkit.method1945(1, 768, 34168);
		this.toolkit.method1954(5890, 0);
		this.toolkit.method1936(0);
		this.toolkit.method1945(1, 768, 34168);
	}

	@OriginalMember(owner = "client!ut", name = "T", descriptor = "()I")
	@Override
	public int scaleHeight() {
		return this.anInt6751 + this.anInt6750 + this.texture2d.singleLength;
	}

	@OriginalMember(owner = "client!ut", name = "BA", descriptor = "(III)V")
	@Override
	public void copyAlpha(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int channel) {
		OpenGL.glPixelTransferf(3348, 0.5F);
		OpenGL.glPixelTransferf(3349, 0.499F);
		OpenGL.glPixelTransferf(3352, 0.5F);
		OpenGL.glPixelTransferf(3353, 0.499F);
		OpenGL.glPixelTransferf(3354, 0.5F);
		OpenGL.glPixelTransferf(3355, 0.499F);
		this.channelMaskTexture = Static331.method4516(arg1, this.texture2d.singleLength, arg0, this.texture2d.singleWidth, this.toolkit);
		this.channelMaskIndex = channel;
		OpenGL.glPixelTransferf(3348, 1.0F);
		OpenGL.glPixelTransferf(3349, 0.0F);
		OpenGL.glPixelTransferf(3352, 1.0F);
		OpenGL.glPixelTransferf(3353, 0.0F);
		OpenGL.glPixelTransferf(3354, 1.0F);
		OpenGL.glPixelTransferf(3355, 0.0F);
	}

	@OriginalMember(owner = "client!ut", name = "j", descriptor = "()I")
	@Override
	public int scaleWidth() {
		return this.anInt6747 + this.texture2d.singleWidth + this.anInt6763;
	}

	@OriginalMember(owner = "client!ut", name = "ma", descriptor = "()I")
	@Override
	public int getHeight() {
		return this.texture2d.singleLength;
	}

	@OriginalMember(owner = "client!ut", name = "a", descriptor = "(FFFFFFIII)V")
	@Override
	protected void a(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2, @OriginalArg(3) float arg3, @OriginalArg(4) float arg4, @OriginalArg(5) float arg5, @OriginalArg(6) int combineMode, @OriginalArg(7) int colorArgb, @OriginalArg(8) int arg8) {
		@Pc(10) float local10;
		@Pc(14) float local14;
		if (this.stretched) {
			local10 = this.scaleWidth();
			local14 = this.scaleHeight();
			@Pc(20) float local20 = (arg2 - arg0) / local10;
			@Pc(26) float local26 = (arg3 - arg1) / local10;
			@Pc(32) float local32 = (arg4 - arg0) / local14;
			@Pc(39) float local39 = (arg5 - arg1) / local14;
			@Pc(45) float local45 = local32 * (float) this.anInt6750;
			@Pc(51) float local51 = (float) this.anInt6750 * local39;
			@Pc(57) float local57 = local20 * (float) this.anInt6763;
			@Pc(63) float local63 = local26 * (float) this.anInt6763;
			@Pc(70) float local70 = -local20 * (float) this.anInt6747;
			@Pc(77) float local77 = -local26 * (float) this.anInt6747;
			@Pc(84) float local84 = (float) this.anInt6751 * -local32;
			arg0 = arg0 + local57 + local45;
			arg1 = local63 + arg1 + local51;
			@Pc(103) float local103 = -local39 * (float) this.anInt6751;
			arg2 = arg2 + local70 + local45;
			arg4 = local57 + arg4 + local84;
			arg3 = arg3 + local77 + local51;
			arg5 = local103 + local63 + arg5;
		}
		local10 = arg4 + arg2 - arg0;
		local14 = arg3 + arg5 - arg1;
		this.texture2d.method4181(true);
		this.toolkit.method1930();
		this.toolkit.setActiveTexture(this.texture2d);
		this.toolkit.method1958(arg8);
		this.toolkit.method1900(combineMode);
		OpenGL.glColor4ub((byte) (colorArgb >> 16), (byte) (colorArgb >> 8), (byte) colorArgb, (byte) (colorArgb >> 24));
		OpenGL.glBegin(7);
		OpenGL.glTexCoord2f(0.0F, this.texture2d.captureScreenRegion);
		OpenGL.glVertex2f(arg0, arg1);
		OpenGL.glTexCoord2f(0.0F, 0.0F);
		OpenGL.glVertex2f(arg4, arg5);
		OpenGL.glTexCoord2f(this.texture2d.s, 0.0F);
		OpenGL.glVertex2f(local10, local14);
		OpenGL.glTexCoord2f(this.texture2d.s, this.texture2d.captureScreenRegion);
		OpenGL.glVertex2f(arg2, arg3);
		OpenGL.glEnd();
	}

	@OriginalMember(owner = "client!ut", name = "a", descriptor = "(IILclient!ma;II)V")
	@Override
	public void drawWithSurface(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) ComponentSurface clippingMask, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4) {
		@Pc(6) Class145_Sub1 local6 = (Class145_Sub1) clippingMask;
		@Pc(9) OpenGl2DImageTexture local9 = local6.aClass6_Sub1_Sub1_5;
		this.texture2d.method4181(false);
		this.toolkit.method1930();
		this.toolkit.setActiveTexture(this.texture2d);
		this.toolkit.method1900(1);
		this.toolkit.method1936(1);
		this.toolkit.setActiveTexture(local9);
		this.toolkit.method1912(7681, 8448);
		this.toolkit.method1945(0, 768, 34168);
		this.toolkit.method1958(1);
		@Pc(62) int local62 = arg1 + this.anInt6750;
		@Pc(67) int local67 = arg0 + this.anInt6763;
		@Pc(74) int local74 = local67 + this.texture2d.singleWidth;
		@Pc(81) int local81 = local62 + this.texture2d.singleLength;
		@Pc(88) float local88 = local9.s / (float) local9.singleWidth;
		@Pc(95) float local95 = local9.captureScreenRegion / (float) local9.singleLength;
		@Pc(102) float local102 = local88 * (float) (local67 - arg3);
		@Pc(110) float local110 = local88 * (float) (local74 - arg3);
		@Pc(121) float local121 = local9.captureScreenRegion - local95 * (float) (local62 - arg4);
		@Pc(132) float local132 = local9.captureScreenRegion - local95 * (float) (local81 - arg4);
		OpenGL.glBegin(7);
		OpenGL.glColor3f(1.0F, 1.0F, 1.0F);
		OpenGL.glMultiTexCoord2f(33984, 0.0F, this.texture2d.captureScreenRegion);
		OpenGL.glMultiTexCoord2f(33985, local102, local121);
		OpenGL.glVertex2i(local67, local62);
		OpenGL.glMultiTexCoord2f(33984, 0.0F, 0.0F);
		OpenGL.glMultiTexCoord2f(33985, local102, local132);
		OpenGL.glVertex2i(local67, local62 + this.texture2d.singleLength);
		OpenGL.glMultiTexCoord2f(33984, this.texture2d.s, 0.0F);
		OpenGL.glMultiTexCoord2f(33985, local110, local132);
		OpenGL.glVertex2i(local67 + this.texture2d.singleWidth, local62 + this.texture2d.singleLength);
		OpenGL.glMultiTexCoord2f(33984, this.texture2d.s, this.texture2d.captureScreenRegion);
		OpenGL.glMultiTexCoord2f(33985, local110, local121);
		OpenGL.glVertex2i(local67 + this.texture2d.singleWidth, local62);
		OpenGL.glEnd();
		this.toolkit.method1945(0, 768, 5890);
		this.toolkit.method1900(0);
		this.toolkit.setActiveTexture(null);
		this.toolkit.method1936(0);
	}

	@OriginalMember(owner = "client!ut", name = "a", descriptor = "(BI)V")
	private void method5295(@OriginalArg(1) int arg0) {
		this.toolkit.method1936(1);
		this.toolkit.setActiveTexture(this.texture2d);
		this.toolkit.method1912(this.toolkit.method1962(arg0), 7681);
		this.toolkit.method1945(1, 768, 34167);
		this.toolkit.method1954(34168, 0);
		this.toolkit.method1936(0);
		this.toolkit.setActiveTexture(this.channelMaskTexture);
		this.toolkit.method1912(34479, 7681);
		this.toolkit.method1945(1, 768, 34166);
		if (this.channelMaskIndex == 0) {
			this.toolkit.method1913(0.5F, 0.0F, 0.5F, 1.0F);
		} else if (this.channelMaskIndex == 1) {
			this.toolkit.method1913(1.0F, 0.0F, 0.5F, 0.5F);
		} else if (this.channelMaskIndex == 2) {
			this.toolkit.method1913(0.5F, 0.0F, 1.0F, 0.5F);
			return;
		} else if (this.channelMaskIndex == 3) {
			this.toolkit.method1913(128.5F, 0.0F, 128.5F, 128.5F);
			return;
		}
	}

	@OriginalMember(owner = "client!ut", name = "r", descriptor = "(IIIII)V")
	@Override
	public void render(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int op, @OriginalArg(3) int color, @OriginalArg(4) int mode) {
		this.texture2d.method4181(false);
		this.toolkit.method1930();
		this.toolkit.method1958(mode);
		OpenGL.glColor4ub((byte) (color >> 16), (byte) (color >> 8), (byte) color, (byte) (color >> 24));
		@Pc(37) int local37 = y + this.anInt6750;
		@Pc(42) int local42 = x + this.anInt6763;
		if (this.channelMaskTexture == null) {
			this.toolkit.setActiveTexture(this.texture2d);
			this.toolkit.method1900(op);
			OpenGL.glBegin(7);
			OpenGL.glTexCoord2f(0.0F, this.texture2d.captureScreenRegion);
			OpenGL.glVertex2i(local42, local37);
			OpenGL.glTexCoord2f(0.0F, 0.0F);
			OpenGL.glVertex2i(local42, this.texture2d.singleLength + local37);
			OpenGL.glTexCoord2f(this.texture2d.s, 0.0F);
			OpenGL.glVertex2i(this.texture2d.singleWidth + local42, this.texture2d.singleLength + local37);
			OpenGL.glTexCoord2f(this.texture2d.s, this.texture2d.captureScreenRegion);
			OpenGL.glVertex2i(this.texture2d.singleWidth + local42, local37);
			OpenGL.glEnd();
			return;
		}
		this.method5295(op);
		this.channelMaskTexture.method4181(false);
		OpenGL.glBegin(7);
		OpenGL.glMultiTexCoord2f(33985, 0.0F, this.texture2d.captureScreenRegion);
		OpenGL.glTexCoord2f(0.0F, this.texture2d.captureScreenRegion);
		OpenGL.glVertex2i(local42, local37);
		OpenGL.glMultiTexCoord2f(33985, 0.0F, 0.0F);
		OpenGL.glTexCoord2f(0.0F, 0.0F);
		OpenGL.glVertex2i(local42, this.texture2d.singleLength + local37);
		OpenGL.glMultiTexCoord2f(33985, this.texture2d.s, 0.0F);
		OpenGL.glTexCoord2f(this.texture2d.s, 0.0F);
		OpenGL.glVertex2i(this.texture2d.singleWidth + local42, local37 - -this.texture2d.singleLength);
		OpenGL.glMultiTexCoord2f(33985, this.texture2d.s, this.texture2d.captureScreenRegion);
		OpenGL.glTexCoord2f(this.texture2d.s, this.texture2d.captureScreenRegion);
		OpenGL.glVertex2i(local42 + this.texture2d.singleWidth, local37);
		OpenGL.glEnd();
		this.method5291();
	}

	@OriginalMember(owner = "client!ut", name = "JA", descriptor = "(IIIIIII)V")
	@Override
	public void drawScaledColored(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int op, @OriginalArg(5) int colour, @OriginalArg(6) int mode) {
		@Pc(10) int local10 = x + width;
		this.texture2d.method4181(false);
		@Pc(19) int local19 = y + height;
		this.toolkit.method1930();
		this.toolkit.setActiveTexture(this.texture2d);
		this.toolkit.method1958(mode);
		this.toolkit.method1900(op);
		OpenGL.glColor4ub((byte) (colour >> 16), (byte) (colour >> 8), (byte) colour, (byte) (colour >> 24));
		if (this.texture2d.tiled && !this.stretched) {
			@Pc(73) float local73 = (float) height * this.texture2d.captureScreenRegion / (float) this.texture2d.singleLength;
			@Pc(85) float local85 = (float) width * this.texture2d.s / (float) this.texture2d.singleWidth;
			OpenGL.glBegin(7);
			OpenGL.glTexCoord2f(0.0F, local73);
			OpenGL.glVertex2i(x, y);
			OpenGL.glTexCoord2f(0.0F, 0.0F);
			OpenGL.glVertex2i(x, local19);
			OpenGL.glTexCoord2f(local85, 0.0F);
			OpenGL.glVertex2i(local10, local19);
			OpenGL.glTexCoord2f(local85, local73);
			OpenGL.glVertex2i(local10, y);
			OpenGL.glEnd();
			return;
		}
		OpenGL.glPushMatrix();
		OpenGL.glTranslatef((float) this.anInt6763, (float) this.anInt6750, 0.0F);
		@Pc(125) int local125 = this.scaleWidth();
		@Pc(128) int local128 = this.scaleHeight();
		@Pc(135) int local135 = y + this.texture2d.singleLength;
		OpenGL.glBegin(7);
		@Pc(139) int local139 = y;
		@Pc(147) int local147;
		while (local135 <= local19) {
			local147 = this.texture2d.singleWidth + x;
			@Pc(149) int local149 = x;
			while (local10 >= local147) {
				OpenGL.glTexCoord2f(0.0F, this.texture2d.captureScreenRegion);
				OpenGL.glVertex2i(local149, local139);
				OpenGL.glTexCoord2f(0.0F, 0.0F);
				OpenGL.glVertex2i(local149, local135);
				OpenGL.glTexCoord2f(this.texture2d.s, 0.0F);
				OpenGL.glVertex2i(local147, local135);
				OpenGL.glTexCoord2f(this.texture2d.s, this.texture2d.captureScreenRegion);
				OpenGL.glVertex2i(local147, local139);
				local149 += local125;
				local147 += local125;
			}
			if (local149 < local10) {
				@Pc(220) float local220 = this.texture2d.s * (float) (local10 - local149) / (float) this.texture2d.singleWidth;
				OpenGL.glTexCoord2f(0.0F, this.texture2d.captureScreenRegion);
				OpenGL.glVertex2i(local149, local139);
				OpenGL.glTexCoord2f(0.0F, 0.0F);
				OpenGL.glVertex2i(local149, local135);
				OpenGL.glTexCoord2f(local220, 0.0F);
				OpenGL.glVertex2i(local10, local135);
				OpenGL.glTexCoord2f(local220, this.texture2d.captureScreenRegion);
				OpenGL.glVertex2i(local10, local139);
			}
			local139 += local128;
			local135 += local128;
		}
		if (local19 > local139) {
			@Pc(281) float local281 = (float) (local139 + this.texture2d.singleLength - local19) * this.texture2d.captureScreenRegion / (float) this.texture2d.singleLength;
			@Pc(288) int local288 = x + this.texture2d.singleWidth;
			local147 = x;
			while (local288 <= local10) {
				OpenGL.glTexCoord2f(0.0F, this.texture2d.captureScreenRegion);
				OpenGL.glVertex2i(local147, local139);
				OpenGL.glTexCoord2f(0.0F, local281);
				OpenGL.glVertex2i(local147, local19);
				OpenGL.glTexCoord2f(this.texture2d.s, local281);
				OpenGL.glVertex2i(local288, local19);
				OpenGL.glTexCoord2f(this.texture2d.s, this.texture2d.captureScreenRegion);
				OpenGL.glVertex2i(local288, local139);
				local288 += local125;
				local147 += local125;
			}
			if (local10 > local147) {
				@Pc(357) float local357 = this.texture2d.s * (float) (local10 - local147) / (float) this.texture2d.singleWidth;
				OpenGL.glTexCoord2f(0.0F, this.texture2d.captureScreenRegion);
				OpenGL.glVertex2i(local147, local139);
				OpenGL.glTexCoord2f(0.0F, local281);
				OpenGL.glVertex2i(local147, local19);
				OpenGL.glTexCoord2f(local357, local281);
				OpenGL.glVertex2i(local10, local19);
				OpenGL.glTexCoord2f(local357, this.texture2d.captureScreenRegion);
				OpenGL.glVertex2i(local10, local139);
			}
		}
		OpenGL.glEnd();
		OpenGL.glPopMatrix();
	}

	@OriginalMember(owner = "client!ut", name = "t", descriptor = "(IIIIII)V")
	@Override
	public void captureScreenRegion(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int arg4, @OriginalArg(5) int colour) {
		this.texture2d.method4193(arg4, colour, arg0, arg1, height, width);
	}

	@OriginalMember(owner = "client!ut", name = "MA", descriptor = "(IIII)V")
	@Override
	public void setOffsets(@OriginalArg(0) int x1, @OriginalArg(1) int y1, @OriginalArg(2) int x2, @OriginalArg(3) int y2) {
		this.anInt6751 = y2;
		this.anInt6763 = x1;
		this.anInt6747 = x2;
		this.anInt6750 = y1;
		this.stretched = this.anInt6763 != 0 || this.anInt6750 != 0 || this.anInt6747 != 0 || this.anInt6751 != 0;
	}

	@OriginalMember(owner = "client!ut", name = "s", descriptor = "(IIIIIII)V")
	@Override
	public void renderImpl(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6) {
		this.texture2d.method4181(true);
		this.toolkit.method1930();
		this.toolkit.method1958(arg6);
		OpenGL.glColor4ub((byte) (arg5 >> 16), (byte) (arg5 >> 8), (byte) arg5, (byte) (arg5 >> 24));
		if (this.stretched) {
			@Pc(182) float local182 = (float) arg2 / (float) this.scaleWidth();
			@Pc(189) float local189 = (float) arg3 / (float) this.scaleHeight();
			@Pc(198) float local198 = local182 * (float) this.anInt6763 + (float) arg0;
			@Pc(207) float local207 = (float) arg1 + local189 * (float) this.anInt6750;
			@Pc(216) float local216 = (float) this.texture2d.singleWidth * local182 + local198;
			@Pc(225) float local225 = local207 + (float) this.texture2d.singleLength * local189;
			if (this.channelMaskTexture == null) {
				this.toolkit.setActiveTexture(this.texture2d);
				this.toolkit.method1900(arg4);
				OpenGL.glBegin(7);
				OpenGL.glTexCoord2f(0.0F, this.texture2d.captureScreenRegion);
				OpenGL.glVertex2f(local198, local207);
				OpenGL.glTexCoord2f(0.0F, 0.0F);
				OpenGL.glVertex2f(local198, local225);
				OpenGL.glTexCoord2f(this.texture2d.s, 0.0F);
				OpenGL.glVertex2f(local216, local225);
				OpenGL.glTexCoord2f(this.texture2d.s, this.texture2d.captureScreenRegion);
				OpenGL.glVertex2f(local216, local207);
				OpenGL.glEnd();
			} else {
				this.method5295(arg4);
				this.channelMaskTexture.method4181(true);
				OpenGL.glBegin(7);
				OpenGL.glMultiTexCoord2f(33985, 0.0F, this.texture2d.captureScreenRegion);
				OpenGL.glTexCoord2f(0.0F, this.texture2d.captureScreenRegion);
				OpenGL.glVertex2f(local198, local207);
				OpenGL.glMultiTexCoord2f(33985, 0.0F, 0.0F);
				OpenGL.glTexCoord2f(0.0F, 0.0F);
				OpenGL.glVertex2f(local198, local225);
				OpenGL.glMultiTexCoord2f(33985, this.texture2d.s, 0.0F);
				OpenGL.glTexCoord2f(this.texture2d.s, 0.0F);
				OpenGL.glVertex2f(local216, local225);
				OpenGL.glMultiTexCoord2f(33985, this.texture2d.s, this.texture2d.captureScreenRegion);
				OpenGL.glTexCoord2f(this.texture2d.s, this.texture2d.captureScreenRegion);
				OpenGL.glVertex2f(local216, local207);
				OpenGL.glEnd();
				this.method5291();
			}
		} else if (this.channelMaskTexture == null) {
			this.toolkit.setActiveTexture(this.texture2d);
			this.toolkit.method1900(arg4);
			OpenGL.glBegin(7);
			OpenGL.glTexCoord2f(0.0F, this.texture2d.captureScreenRegion);
			OpenGL.glVertex2i(arg0, arg1);
			OpenGL.glTexCoord2f(0.0F, 0.0F);
			OpenGL.glVertex2i(arg0, arg3 + arg1);
			OpenGL.glTexCoord2f(this.texture2d.s, 0.0F);
			OpenGL.glVertex2i(arg2 + arg0, arg3 + arg1);
			OpenGL.glTexCoord2f(this.texture2d.s, this.texture2d.captureScreenRegion);
			OpenGL.glVertex2i(arg0 + arg2, arg1);
			OpenGL.glEnd();
		} else {
			this.method5295(arg4);
			this.channelMaskTexture.method4181(true);
			OpenGL.glBegin(7);
			OpenGL.glMultiTexCoord2f(33985, 0.0F, this.texture2d.captureScreenRegion);
			OpenGL.glTexCoord2f(0.0F, this.texture2d.captureScreenRegion);
			OpenGL.glVertex2i(arg0, arg1);
			OpenGL.glMultiTexCoord2f(33985, 0.0F, 0.0F);
			OpenGL.glTexCoord2f(0.0F, 0.0F);
			OpenGL.glVertex2i(arg0, arg3 + arg1);
			OpenGL.glMultiTexCoord2f(33985, this.texture2d.s, 0.0F);
			OpenGL.glTexCoord2f(this.texture2d.s, 0.0F);
			OpenGL.glVertex2i(arg2 + arg0, arg3 + arg1);
			OpenGL.glMultiTexCoord2f(33985, this.texture2d.s, this.texture2d.captureScreenRegion);
			OpenGL.glTexCoord2f(this.texture2d.s, this.texture2d.captureScreenRegion);
			OpenGL.glVertex2i(arg0 + arg2, arg1);
			OpenGL.glEnd();
			this.method5291();
		}
	}

@OriginalMember(owner = "client!ut", name = "y", descriptor = "I")
	public static int anInt6765 = 0;
@OriginalMember(owner = "client!ut", name = "B", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_142 = new Class83("Continue", "Weiter", "Continuer", "Continuar");
@OriginalMember(owner = "client!ut", name = "E", descriptor = "I")
	public static int anInt6770 = 0;
@OriginalMember(owner = "client!ut", name = "F", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_90 = new Class215(17, 0);
@OriginalMember(owner = "client!ut", name = "a", descriptor = "(IIIIII)V")
	public static void method5292(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4) {
		if (TextureOpBrightness.aClass50_Sub1_1.anInt3448 != 0 && arg3 != 0 && Class16.anInt6886 < 50 && arg1 != -1) {
			OpenGLTextureCache.aClass269Array1[Class16.anInt6886++] = new Class269((byte) 1, arg1, arg3, arg2, arg0, arg4);
		}
	}
@OriginalMember(owner = "client!ut", name = "a", descriptor = "(I[BII)Ljava/lang/String;")
	public static String method5293(@OriginalArg(1) byte[] arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		@Pc(8) char[] local8 = new char[arg1];
		@Pc(10) int local10 = 0;
		for (@Pc(12) int local12 = 0; local12 < arg1; local12++) {
			@Pc(22) int local22 = arg0[local12 + arg2] & 0xFF;
			if (local22 != 0) {
				if (local22 >= 128 && local22 < 160) {
					@Pc(38) char local38 = StillWaterEffect.aCharArray7[local22 - 128];
					if (local38 == '\u0000') {
						local38 = '?';
					}
					local22 = local38;
				}
				local8[local10++] = (char) local22;
			}
		}
		return new String(local8, 0, local10);
	}
@OriginalMember(owner = "client!ut", name = "b", descriptor = "(B)V")
	public static void method5294() {
		for (@Pc(18) Class4_Sub23 local18 = (Class4_Sub23) Static298.aClass183_38.method4140(); local18 != null; local18 = (Class4_Sub23) Static298.aClass183_38.method4144()) {
			if (Class70.aClass164ArrayArrayArray5 == null) {
				local18.method5684();
			} else {
				@Pc(44) int local44;
				@Pc(236) Class16_Sub4 local236;
				@Pc(255) Class16_Sub4_Sub1 local255;
				@Pc(128) Class16_Sub1 local128;
				@Pc(62) Class16_Sub2 local62;
				@Pc(273) Class16_Sub5 local273;
				@Pc(98) OpenGlArbElementArrayBuffer local98;
				@Pc(309) Class16_Sub5_Sub1 local309;
				if (Shadow.anInt5323 >= local18.anInt3202) {
					local44 = EnvironmentContext.anIntArray410[local18.anInt3195];
					if (local44 == 0) {
						local273 = MusicPatchNode.method3449(local18.anInt3196, local18.anInt3189, local18.anInt3193);
						if (local273 instanceof Class16_Sub5_Sub1) {
							ItemType.method4777(local18.anInt3196, local18.anInt3189, local18.anInt3193);
							local309 = (Class16_Sub5_Sub1) local273;
							if (local309.aClass16_Sub5_1 != null) {
								Class4_Sub3.method181(local18.anInt3196, local18.anInt3189, local18.anInt3193, local309.aClass16_Sub5_1, null);
							}
						}
					} else if (local44 == 1) {
						local62 = GlParticleRenderer.method918(local18.anInt3196, local18.anInt3189, local18.anInt3193);
						if (local62 instanceof OpenGlArbElementArrayBuffer) {
							StillWaterEffect.method5382(local18.anInt3196, local18.anInt3189, local18.anInt3193);
							local98 = (OpenGlArbElementArrayBuffer) local62;
							if (local98.aClass16_Sub2_1 != null) {
								Static73.method1356(local18.anInt3196, local18.anInt3189, local18.anInt3193, local98.aClass16_Sub2_1, null);
							}
						}
					} else if (local44 == 2) {
						local128 = Class26.method648(local18.anInt3196, local18.anInt3189, local18.anInt3193, GameObject.class);
						if (local128 instanceof Class16_Sub1_Sub1) {
							Mesh.method3046(local18.anInt3196, local18.anInt3189, local18.anInt3193, GameObject.class);
							@Pc(417) Class16_Sub1_Sub1 local417 = (Class16_Sub1_Sub1) local128;
							if (local417.aClass16_Sub1_2 != null) {
								Class193.method4385(local417.aClass16_Sub1_2, false);
							}
						}
					} else if (local44 == 3) {
						local236 = Class4_Sub19.method2096(local18.anInt3196, local18.anInt3189, local18.anInt3193);
						if (local236 instanceof Class16_Sub4_Sub1) {
							Class244.method5491(local18.anInt3196, local18.anInt3189, local18.anInt3193);
							local255 = (Class16_Sub4_Sub1) local236;
							if (local255.aClass16_Sub4_1 != null) {
								Class225.method5008(local18.anInt3196, local18.anInt3189, local18.anInt3193, local255.aClass16_Sub4_1);
							}
						}
					}
					local18.method5684();
				} else if (local18.anInt3191 == Shadow.anInt5323) {
					local44 = EnvironmentContext.anIntArray410[local18.anInt3195];
					if (local44 == 0) {
						local273 = MusicPatchNode.method3449(local18.anInt3196, local18.anInt3189, local18.anInt3193);
						if (local273 instanceof Class16_Sub5_Sub1) {
							local18.method5684();
						} else if (FontMetrics.method2344(local18.anInt3196, local18.anInt3189, local18.anInt3193) == null) {
							local309 = new Class16_Sub5_Sub1(local18.anInt3195, local18.anInt3190, local18.anInt3198, local18.anInt3188, local18.anInt3203, local273);
							Class4_Sub3.method181(local18.anInt3196, local18.anInt3189, local18.anInt3193, local309, null);
						} else {
							local18.method5684();
						}
					} else if (local44 == 1) {
						local62 = GlParticleRenderer.method918(local18.anInt3196, local18.anInt3189, local18.anInt3193);
						if (local62 instanceof OpenGlArbElementArrayBuffer) {
							local18.method5684();
						} else if (ParamTypeList.method5023(local18.anInt3196, local18.anInt3189, local18.anInt3193) == null) {
							local98 = new OpenGlArbElementArrayBuffer(local18.anInt3195, local18.anInt3190, local18.anInt3198, local18.anInt3188, local18.anInt3203, local62);
							Static73.method1356(local18.anInt3196, local18.anInt3189, local18.anInt3193, local98, null);
						} else {
							local18.method5684();
						}
					} else if (local44 == 2) {
						local128 = Class26.method648(local18.anInt3196, local18.anInt3189, local18.anInt3193, GameObject.class);
						if (local128 instanceof Class16_Sub1_Sub1) {
							local18.method5684();
						} else {
							Mesh.method3046(local18.anInt3196, local18.anInt3189, local18.anInt3193, GameObject.class);
							@Pc(157) LocType local157 = Static267.aClass262_2.method5560(local18.anInt3194);
							@Pc(175) int local175;
							@Pc(172) int local172;
							if (local18.anInt3190 == 1 || local18.anInt3190 == 3) {
								local172 = local157.anInt5816;
								local175 = local157.anInt5801;
							} else {
								local172 = local157.anInt5801;
								local175 = local157.anInt5816;
							}
							@Pc(217) Class16_Sub1_Sub1 local217 = new Class16_Sub1_Sub1(local18.anInt3195, local18.anInt3190, local18.anInt3196, local18.anInt3198, local18.anInt3188, local18.anInt3203, local18.anInt3189, local175 + local18.anInt3189 - 1, local18.anInt3193, local18.anInt3193 + local172 - 1, local128);
							Class193.method4385(local217, false);
						}
					} else if (local44 == 3) {
						local236 = Class4_Sub19.method2096(local18.anInt3196, local18.anInt3189, local18.anInt3193);
						if (local236 instanceof Class16_Sub4_Sub1) {
							local18.method5684();
						} else {
							local255 = new Class16_Sub4_Sub1(local18.anInt3198, local18.anInt3188, local18.anInt3203, local236);
							Class225.method5008(local18.anInt3196, local18.anInt3189, local18.anInt3193, local255);
						}
					}
				}
			}
		}
	}
@OriginalMember(owner = "client!ut", name = "a", descriptor = "(IIIIILclient!sd;IIIII)Z")
	public static boolean method5296(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) CollisionMap arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) int arg7, @OriginalArg(9) int arg8, @OriginalArg(10) int arg9) {
		@Pc(7) int local7 = arg9;
		@Pc(9) int local9 = arg8;
		@Pc(18) int local18 = arg9 - 64;
		CubeMapSpecularEffect.anIntArrayArray30[64][64] = 99;
		@Pc(28) int local28 = arg8 - 64;
		Static14.anIntArrayArray1[64][64] = 0;
		@Pc(36) byte local36 = 0;
		TextureOpPerlinNoise.anIntArray182[0] = arg9;
		@Pc(42) int local42 = 0;
		@Pc(45) int local45 = local36 + 1;
		PingRequest.recentVarcUpdates[0] = arg8;
		@Pc(50) int[][] local50 = arg4.collisionFlags;
		while (local45 != local42) {
			local9 = PingRequest.recentVarcUpdates[local42];
			local7 = TextureOpPerlinNoise.anIntArray182[local42];
			@Pc(65) int local65 = local9 - local28;
			local42 = local42 + 1 & 0xFFF;
			@Pc(77) int local77 = local7 - arg4.anInt6230;
			@Pc(82) int local82 = local7 - local18;
			@Pc(88) int local88 = local9 - arg4.anInt6235;
			if (arg2 == -4) {
				if (arg3 == local7 && arg5 == local9) {
					Static102.anInt2045 = local9;
					TextureOpVoronoiNoise.anInt6521 = local7;
					return true;
				}
			} else if (arg2 == -3) {
				if (Toolkit.method4305(1, arg5, 1, arg7, arg0, local9, local7, arg3)) {
					TextureOpVoronoiNoise.anInt6521 = local7;
					Static102.anInt2045 = local9;
					return true;
				}
			} else if (arg2 == -2) {
				if (arg4.method4844(arg3, 1, arg7, local7, arg1, arg5, local9, arg0, 1)) {
					TextureOpVoronoiNoise.anInt6521 = local7;
					Static102.anInt2045 = local9;
					return true;
				}
			} else if (arg2 == -1) {
				if (arg4.method4840(arg0, 1, arg3, arg1, arg7, arg5, local7, local9)) {
					Static102.anInt2045 = local9;
					TextureOpVoronoiNoise.anInt6521 = local7;
					return true;
				}
			} else if (arg2 == 0 || arg2 == 1 || arg2 == 2 || arg2 == 3 || arg2 == 9) {
				if (arg4.method4842(local9, arg6, arg5, 1, local7, arg3, arg2)) {
					TextureOpVoronoiNoise.anInt6521 = local7;
					Static102.anInt2045 = local9;
					return true;
				}
			} else if (arg4.method4849(arg6, local7, arg2, arg3, local9, arg5, 1)) {
				Static102.anInt2045 = local9;
				TextureOpVoronoiNoise.anInt6521 = local7;
				return true;
			}
			@Pc(245) int local245 = Static14.anIntArrayArray1[local82][local65] + 1;
			if (local82 > 0 && CubeMapSpecularEffect.anIntArrayArray30[local82 - 1][local65] == 0 && (local50[local77 - 1][local88] & 0x42240000) == 0) {
				TextureOpPerlinNoise.anIntArray182[local45] = local7 - 1;
				PingRequest.recentVarcUpdates[local45] = local9;
				CubeMapSpecularEffect.anIntArrayArray30[local82 - 1][local65] = 2;
				local45 = local45 + 1 & 0xFFF;
				Static14.anIntArrayArray1[local82 - 1][local65] = local245;
			}
			if (local82 < 127 && CubeMapSpecularEffect.anIntArrayArray30[local82 + 1][local65] == 0 && (local50[local77 + 1][local88] & 0x60240000) == 0) {
				TextureOpPerlinNoise.anIntArray182[local45] = local7 + 1;
				PingRequest.recentVarcUpdates[local45] = local9;
				local45 = local45 + 1 & 0xFFF;
				CubeMapSpecularEffect.anIntArrayArray30[local82 + 1][local65] = 8;
				Static14.anIntArrayArray1[local82 + 1][local65] = local245;
			}
			if (local65 > 0 && CubeMapSpecularEffect.anIntArrayArray30[local82][local65 - 1] == 0 && (local50[local77][local88 - 1] & 0x40A40000) == 0) {
				TextureOpPerlinNoise.anIntArray182[local45] = local7;
				PingRequest.recentVarcUpdates[local45] = local9 - 1;
				local45 = local45 + 1 & 0xFFF;
				CubeMapSpecularEffect.anIntArrayArray30[local82][local65 - 1] = 1;
				Static14.anIntArrayArray1[local82][local65 - 1] = local245;
			}
			if (local65 < 127 && CubeMapSpecularEffect.anIntArrayArray30[local82][local65 + 1] == 0 && (local50[local77][local88 + 1] & 0x48240000) == 0) {
				TextureOpPerlinNoise.anIntArray182[local45] = local7;
				PingRequest.recentVarcUpdates[local45] = local9 + 1;
				CubeMapSpecularEffect.anIntArrayArray30[local82][local65 + 1] = 4;
				local45 = local45 + 1 & 0xFFF;
				Static14.anIntArrayArray1[local82][local65 + 1] = local245;
			}
			if (local82 > 0 && local65 > 0 && CubeMapSpecularEffect.anIntArrayArray30[local82 - 1][local65 - 1] == 0 && (local50[local77 - 1][local88 - 1] & 0x43A40000) == 0 && (local50[local77 - 1][local88] & 0x42240000) == 0 && (local50[local77][local88 - 1] & 0x40A40000) == 0) {
				TextureOpPerlinNoise.anIntArray182[local45] = local7 - 1;
				PingRequest.recentVarcUpdates[local45] = local9 - 1;
				local45 = local45 + 1 & 0xFFF;
				CubeMapSpecularEffect.anIntArrayArray30[local82 - 1][local65 - 1] = 3;
				Static14.anIntArrayArray1[local82 - 1][local65 - 1] = local245;
			}
			if (local82 < 127 && local65 > 0 && CubeMapSpecularEffect.anIntArrayArray30[local82 + 1][local65 - 1] == 0 && (local50[local77 + 1][local88 - 1] & 0x60E40000) == 0 && (local50[local77 + 1][local88] & 0x60240000) == 0 && (local50[local77][local88 - 1] & 0x40A40000) == 0) {
				TextureOpPerlinNoise.anIntArray182[local45] = local7 + 1;
				PingRequest.recentVarcUpdates[local45] = local9 - 1;
				local45 = local45 + 1 & 0xFFF;
				CubeMapSpecularEffect.anIntArrayArray30[local82 + 1][local65 - 1] = 9;
				Static14.anIntArrayArray1[local82 + 1][local65 - 1] = local245;
			}
			if (local82 > 0 && local65 < 127 && CubeMapSpecularEffect.anIntArrayArray30[local82 - 1][local65 + 1] == 0 && (local50[local77 - 1][local88 + 1] & 0x4E240000) == 0 && (local50[local77 - 1][local88] & 0x42240000) == 0 && (local50[local77][local88 + 1] & 0x48240000) == 0) {
				TextureOpPerlinNoise.anIntArray182[local45] = local7 - 1;
				PingRequest.recentVarcUpdates[local45] = local9 + 1;
				local45 = local45 + 1 & 0xFFF;
				CubeMapSpecularEffect.anIntArrayArray30[local82 - 1][local65 + 1] = 6;
				Static14.anIntArrayArray1[local82 - 1][local65 + 1] = local245;
			}
			if (local82 < 127 && local65 < 127 && CubeMapSpecularEffect.anIntArrayArray30[local82 + 1][local65 + 1] == 0 && (local50[local77 + 1][local88 + 1] & 0x78240000) == 0 && (local50[local77 + 1][local88] & 0x60240000) == 0 && (local50[local77][local88 + 1] & 0x48240000) == 0) {
				TextureOpPerlinNoise.anIntArray182[local45] = local7 + 1;
				PingRequest.recentVarcUpdates[local45] = local9 + 1;
				CubeMapSpecularEffect.anIntArrayArray30[local82 + 1][local65 + 1] = 12;
				local45 = local45 + 1 & 0xFFF;
				Static14.anIntArrayArray1[local82 + 1][local65 + 1] = local245;
			}
		}
		TextureOpVoronoiNoise.anInt6521 = local7;
		Static102.anInt2045 = local9;
		return false;
	}
}
