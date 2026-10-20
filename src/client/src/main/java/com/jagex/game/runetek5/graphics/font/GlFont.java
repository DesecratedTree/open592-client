package com.jagex.game.runetek5.graphics.font;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.deob.Class145_Sub1;
import com.jagex.game.runetek5.deob.s;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import com.jagex.game.runetek5.graphics.texture.OpenGl2DImageTexture;
import com.jagex.game.runetek5.toolkit.opengl.GlRenderList;

import com.jagex.game.runetek5.deob.Class28;
@OriginalClass("client!fm")
public final class GlFont extends AbstractFontRenderer {

	@OriginalMember(owner = "client!fm", name = "y", descriptor = "Lclient!fd;")
	private final GlToolkit glToolkit;

	@OriginalMember(owner = "client!fm", name = "x", descriptor = "Lclient!ps;")
	private final OpenGl2DImageTexture texture;

	@OriginalMember(owner = "client!fm", name = "w", descriptor = "Z")
	private final boolean isAlphaOnlyTexture;

	@OriginalMember(owner = "client!fm", name = "z", descriptor = "Lclient!ct;")
	private final GlRenderList glyphLists;

	@OriginalMember(owner = "client!fm", name = "<init>", descriptor = "(Lclient!fd;Lclient!gt;[Lclient!bb;Z)V")
	public GlFont(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) FontMetrics fontMetrics, @OriginalArg(2) SpriteType[] indexedImages, @OriginalArg(3) boolean isAlphaOnly) {
		super(arg0, fontMetrics);
		this.glToolkit = arg0;
		@Pc(8) int local8 = 0;
		for (@Pc(10) int local10 = 0; local10 < 256; local10++) {
			@Pc(15) SpriteType local15 = indexedImages[local10];
			if (local15.height > local8) {
				local8 = local15.height;
			}
			if (local15.width > local8) {
				local8 = local15.width;
			}
		}
		@Pc(37) int local37 = local8 * 16;
		@Pc(44) byte[] local44 = new byte[local37 * local37];
		@Pc(57) int local57;
		@Pc(60) int local60;
		@Pc(66) int local66;
		@Pc(72) int local72;
		for (@Pc(46) int local46 = 0; local46 < 256; local46++) {
			@Pc(51) SpriteType local51 = indexedImages[local46];
			@Pc(54) byte[] local54 = local51.pixels;
			local57 = local51.height;
			local60 = local51.width;
			local66 = local46 % 16 * local8;
			local72 = local46 / 16 * local8;
			@Pc(78) int local78 = local72 * local37 + local66;
			@Pc(80) int local80 = 0;
			for (@Pc(82) int local82 = 0; local82 < local57; local82++) {
				for (@Pc(85) int local85 = 0; local85 < local60; local85++) {
					local44[local78++] = (byte) (local54[local80++] == 0 ? 0 : -1);
				}
				local78 += local37 - local60;
			}
		}
		this.texture = Class28.method671(local44, arg0, local37, local37);
		this.isAlphaOnlyTexture = true;
		this.texture.method4181(false);
		this.glyphLists = new GlRenderList(arg0, 256);
		@Pc(290) float local290 = this.texture.s / (float) this.texture.singleWidth;
		@Pc(299) float local299 = this.texture.captureScreenRegion / (float) this.texture.singleLength;
		for (@Pc(301) int local301 = 0; local301 < 256; local301++) {
			@Pc(306) SpriteType local306 = indexedImages[local301];
			local57 = local306.height;
			local60 = local306.width;
			local66 = local306.minY;
			local72 = local306.minX;
			@Pc(325) float local325 = (float) (local301 % 16 * local8);
			@Pc(332) float local332 = (float) (local301 / 16 * local8);
			@Pc(336) float local336 = local325 * local290;
			@Pc(340) float local340 = local332 * local299;
			@Pc(347) float local347 = (local325 + (float) local60) * local290;
			@Pc(354) float local354 = (local332 + (float) local57) * local299;
			this.glyphLists.method990(local301);
			OpenGL.glBegin(7);
			OpenGL.glTexCoord2f(local336, this.texture.captureScreenRegion - local340);
			OpenGL.glVertex2i(local72, local66);
			OpenGL.glTexCoord2f(local336, this.texture.captureScreenRegion - local354);
			OpenGL.glVertex2i(local72, local66 + local57);
			OpenGL.glTexCoord2f(local347, this.texture.captureScreenRegion - local354);
			OpenGL.glVertex2i(local72 + local60, local66 + local57);
			OpenGL.glTexCoord2f(local347, this.texture.captureScreenRegion - local340);
			OpenGL.glVertex2i(local72 + local60, local66);
			OpenGL.glEnd();
			this.glyphLists.method991();
		}
	}

	@OriginalMember(owner = "client!fm", name = "a", descriptor = "(CIIIZLclient!ma;II)V")
	@Override
	protected void renderSymbol(@OriginalArg(0) char c, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int colour, @OriginalArg(4) boolean shadow, @OriginalArg(5) ComponentSurface mask, @OriginalArg(6) int offsetX, @OriginalArg(7) int offsetY) {
		@Pc(2) Class145_Sub1 local2 = (Class145_Sub1) mask;
		@Pc(5) OpenGl2DImageTexture local5 = local2.aClass6_Sub1_Sub1_5;
		this.glToolkit.method1929();
		this.glToolkit.setActiveTexture(this.texture);
		if (this.isAlphaOnlyTexture || shadow) {
			this.glToolkit.method1912(7681, 8448);
			this.glToolkit.method1945(0, 768, 34168);
		}
		this.glToolkit.method1936(1);
		this.glToolkit.setActiveTexture(local5);
		this.glToolkit.method1912(7681, 8448);
		this.glToolkit.method1945(0, 768, 34168);
		OpenGL.glTexGeni(8192, 9472, 9216);
		OpenGL.glTexGeni(8193, 9472, 9216);
		@Pc(71) float local71 = local5.s / (float) local5.singleWidth;
		@Pc(78) float local78 = local5.captureScreenRegion / (float) local5.singleLength;
		OpenGL.glTexGenfv(8192, 9474, new float[] { local71, 0.0F, 0.0F, (float) -offsetX * local71 }, 0);
		OpenGL.glTexGenfv(8193, 9474, new float[] { 0.0F, local78, 0.0F, (float) -offsetY * local78 }, 0);
		OpenGL.glEnable(3168);
		OpenGL.glEnable(3169);
		OpenGL.glColor4ub((byte) (colour >> 16), (byte) (colour >> 8), (byte) colour, (byte) (colour >> 24));
		OpenGL.glTranslatef((float) x, (float) y, 0.0F);
		this.glyphLists.method989(c);
		OpenGL.glLoadIdentity();
		OpenGL.glDisable(3168);
		OpenGL.glDisable(3169);
		this.glToolkit.method1945(0, 768, 5890);
		this.glToolkit.method1912(8448, 8448);
		this.glToolkit.setActiveTexture(null);
		this.glToolkit.method1936(0);
		if (this.isAlphaOnlyTexture || shadow) {
			this.glToolkit.method1945(0, 768, 5890);
		}
	}

	@OriginalMember(owner = "client!fm", name = "OA", descriptor = "(CIIIZ)V")
	@Override
	protected void OA(@OriginalArg(0) char c, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int colour, @OriginalArg(4) boolean shadow) {
		this.glToolkit.method1929();
		this.glToolkit.setActiveTexture(this.texture);
		if (this.isAlphaOnlyTexture || shadow) {
			this.glToolkit.method1912(7681, 8448);
			this.glToolkit.method1945(0, 768, 34168);
		}
		OpenGL.glColor4ub((byte) (colour >> 16), (byte) (colour >> 8), (byte) colour, (byte) (colour >> 24));
		OpenGL.glTranslatef((float) x, (float) y, 0.0F);
		this.glyphLists.method989(c);
		OpenGL.glLoadIdentity();
		if (this.isAlphaOnlyTexture || shadow) {
			this.glToolkit.method1945(0, 768, 5890);
		}
	}
}
