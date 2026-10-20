package com.jagex.game.runetek5.graphics;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.cache.node.lru.OpenGlRenderBuffer;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.deob.s;
import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class83;

import com.jagex.game.runetek5.graphics.*;
@OriginalClass("client!wb")
public final class FramebufferCompositor {

	@OriginalMember(owner = "client!wb", name = "d", descriptor = "Z")
	private boolean isCompositionPending;

	@OriginalMember(owner = "client!wb", name = "w", descriptor = "Lclient!io;")
	private OpenGlRenderBuffer hdrRenderBuffer;

	@OriginalMember(owner = "client!wb", name = "z", descriptor = "Lclient!af;")
	private OpenGl2DTexture activeTexture;

	@OriginalMember(owner = "client!wb", name = "E", descriptor = "Lclient!io;")
	private OpenGlRenderBuffer renderBuffer;

	@OriginalMember(owner = "client!wb", name = "b", descriptor = "I")
	private int captureScreenRegion = 1;

	@OriginalMember(owner = "client!wb", name = "l", descriptor = "I")
	private int vertexCoordinateY = 0;

	@OriginalMember(owner = "client!wb", name = "i", descriptor = "I")
	private int vertexCoordinateX = 0;

	@OriginalMember(owner = "client!wb", name = "c", descriptor = "I")
	private int width = 1;

	@OriginalMember(owner = "client!wb", name = "f", descriptor = "Lclient!pk;")
	private final Class183 effectsList = new Class183();

	@OriginalMember(owner = "client!wb", name = "x", descriptor = "I")
	private int s = -1;

	@OriginalMember(owner = "client!wb", name = "s", descriptor = "Z")
	private boolean isDepthBufferDirty = true;

	@OriginalMember(owner = "client!wb", name = "u", descriptor = "Z")
	private boolean isFramebufferDirty = true;

	@OriginalMember(owner = "client!wb", name = "v", descriptor = "I")
	private int texturedFaceCount = 0;

	@OriginalMember(owner = "client!wb", name = "D", descriptor = "Z")
	private boolean pixelFormatSet = true;

	@OriginalMember(owner = "client!wb", name = "C", descriptor = "[Lclient!af;")
	private OpenGl2DTexture[] textures2d = new OpenGl2DTexture[2];

	@OriginalMember(owner = "client!wb", name = "t", descriptor = "Z")
	private boolean isFboAttachmentDirty = true;

	@OriginalMember(owner = "client!wb", name = "B", descriptor = "Z")
	private boolean loadTexture = false;

	@OriginalMember(owner = "client!wb", name = "j", descriptor = "Lclient!fd;")
	private final GlToolkit activeToolkit;

	@OriginalMember(owner = "client!wb", name = "q", descriptor = "Lclient!ml;")
	private GlFramebuffer resolveDisplay;

	@OriginalMember(owner = "client!wb", name = "g", descriptor = "Lclient!ml;")
	private GlFramebuffer activeDisplay;

	@OriginalMember(owner = "client!wb", name = "m", descriptor = "Lclient!ml;")
	private GlFramebuffer multisampleDisplay;

	@OriginalMember(owner = "client!wb", name = "<init>", descriptor = "(Lclient!fd;)V")
	public FramebufferCompositor(@OriginalArg(0) GlToolkit glToolkit) {
		this.activeToolkit = glToolkit;
		if (this.activeToolkit.extFramebufferObject && this.activeToolkit.arbRectangleTexturesSupported) {
			this.activeDisplay = this.resolveDisplay = new GlFramebuffer(this.activeToolkit);
			if (this.activeToolkit.multiSamplesPreference > 1 && this.activeToolkit.aBoolean204 && this.activeToolkit.aBoolean209) {
				this.activeDisplay = this.multisampleDisplay = new GlFramebuffer(this.activeToolkit);
				return;
			}
		}
	}

	@OriginalMember(owner = "client!wb", name = "a", descriptor = "(I)V")
	public void shutdown() {
		this.activeTexture = null;
		this.renderBuffer = null;
		this.activeDisplay = this.multisampleDisplay = this.resolveDisplay = null;
		this.textures2d = null;
		this.hdrRenderBuffer = null;
		if (!this.effectsList.method4147()) {
			for (@Pc(42) Country local42 = this.effectsList.method4140(); local42 != this.effectsList.aClass4_207; local42 = local42.aClass4_262) {
				((Class4_Sub10) local42).method2006();
			}
		}
		this.width = this.captureScreenRegion = 1;
	}

	@OriginalMember(owner = "client!wb", name = "a", descriptor = "(ILclient!cg;)Z")
	public boolean method5525(@OriginalArg(1) Class4_Sub10 arg0) {
		if (this.activeDisplay != null) {
			if (arg0.method2004() || arg0.method2005()) {
				this.effectsList.method4137(arg0);
				this.recalculateFormat();
				if (this.rebuildFramebuffers()) {
					if (this.width != -1 && this.captureScreenRegion != -1) {
						arg0.method2007(this.captureScreenRegion, this.width);
					}
					arg0.aBoolean220 = true;
					return true;
				}
			}
			this.method5529(arg0);
		}
		return false;
	}

	@OriginalMember(owner = "client!wb", name = "b", descriptor = "(I)V")
	private void recalculateFormat() {
		@Pc(7) boolean local7 = false;
		@Pc(9) int local9 = 0;
		@Pc(11) int local11 = 0;
		@Pc(25) int local25;
		for (@Pc(19) Class4_Sub10 local19 = (Class4_Sub10) this.effectsList.method4140(); local19 != null; local19 = (Class4_Sub10) this.effectsList.method4144()) {
			local25 = local19.method1999();
			local11 += local19.method2001();
			if (local25 > local9) {
				local9 = local25;
			}
			local7 |= local19.method1998();
		}
		if (local9 == 2) {
			local25 = 34836;
		} else if (local9 == 1) {
			local25 = 34842;
		} else {
			local25 = 6408;
		}
		if (local25 != this.s) {
			this.pixelFormatSet = true;
			this.s = local25;
		}
		@Pc(91) int local91 = this.texturedFaceCount > 2 ? 2 : this.texturedFaceCount;
		@Pc(100) int local100 = local11 > 2 ? 2 : local11;
		if (local7 != this.loadTexture) {
			this.loadTexture = local7;
			this.isDepthBufferDirty = true;
		}
		if (local91 != local100) {
			this.isFboAttachmentDirty = this.pixelFormatSet = true;
		}
		this.texturedFaceCount = local11;
	}

	@OriginalMember(owner = "client!wb", name = "a", descriptor = "(B)Z")
	private boolean rebuildFramebuffers() {
		if (this.isDepthBufferDirty) {
			if (this.renderBuffer != null) {
				this.renderBuffer.dispose();
				this.renderBuffer = null;
			}
			if (this.activeTexture != null) {
				this.activeTexture.method4183();
				this.activeTexture = null;
			}
			if (this.multisampleDisplay != null) {
				this.renderBuffer = new OpenGlRenderBuffer(this.activeToolkit, 6402, this.width, this.captureScreenRegion, this.activeToolkit.multiSamplesPreference);
			}
			if (this.loadTexture) {
				this.activeTexture = new OpenGl2DTexture(this.activeToolkit, 34037, 6402, this.width, this.captureScreenRegion);
			} else if (this.renderBuffer == null) {
				this.renderBuffer = new OpenGlRenderBuffer(this.activeToolkit, 6402, this.width, this.captureScreenRegion);
			}
			this.isFboAttachmentDirty = true;
			this.isDepthBufferDirty = false;
			this.isFramebufferDirty = true;
		}
		if (this.pixelFormatSet) {
			if (this.hdrRenderBuffer != null) {
				this.hdrRenderBuffer.dispose();
				this.hdrRenderBuffer = null;
			}
			if (this.textures2d[0] != null) {
				this.textures2d[0].method4183();
				this.textures2d[0] = null;
			}
			if (this.textures2d[1] != null) {
				this.textures2d[1].method4183();
				this.textures2d[1] = null;
			}
			if (this.multisampleDisplay != null) {
				this.hdrRenderBuffer = new OpenGlRenderBuffer(this.activeToolkit, this.s, this.width, this.captureScreenRegion, this.activeToolkit.multiSamplesPreference);
			}
			this.textures2d[0] = new OpenGl2DTexture(this.activeToolkit, 34037, this.s, this.width, this.captureScreenRegion);
			this.textures2d[1] = this.texturedFaceCount <= 1 ? null : new OpenGl2DTexture(this.activeToolkit, 34037, this.s, this.width, this.captureScreenRegion);
			this.pixelFormatSet = false;
			this.isFramebufferDirty = true;
			this.isFboAttachmentDirty = true;
		}
		if (this.isFboAttachmentDirty) {
			if (this.multisampleDisplay == null) {
				this.activeToolkit.method1922(this.resolveDisplay);
				this.resolveDisplay.method3611(0);
				this.resolveDisplay.method3611(1);
				this.resolveDisplay.method3611(8);
				this.resolveDisplay.method3621(this.textures2d[0], 0);
				if (this.texturedFaceCount > 1) {
					this.resolveDisplay.method3621(this.textures2d[1], 1);
				}
				if (this.loadTexture) {
					this.resolveDisplay.method3621(this.activeTexture, 8);
				} else {
					this.resolveDisplay.method3614(this.renderBuffer, 8);
				}
				this.activeToolkit.method1971(this.resolveDisplay);
			} else {
				this.activeToolkit.method1922(this.resolveDisplay);
				this.resolveDisplay.method3611(0);
				this.resolveDisplay.method3611(1);
				this.resolveDisplay.method3611(8);
				this.resolveDisplay.method3621(this.textures2d[0], 0);
				if (this.texturedFaceCount > 1) {
					this.resolveDisplay.method3621(this.textures2d[1], 1);
				}
				if (this.loadTexture) {
					this.resolveDisplay.method3621(this.activeTexture, 8);
				}
				this.activeToolkit.method1971(this.resolveDisplay);
				this.activeToolkit.method1922(this.multisampleDisplay);
				this.multisampleDisplay.method3611(0);
				this.multisampleDisplay.method3611(8);
				this.multisampleDisplay.method3614(this.hdrRenderBuffer, 0);
				this.multisampleDisplay.method3614(this.renderBuffer, 8);
				this.activeToolkit.method1971(this.multisampleDisplay);
			}
			this.isFramebufferDirty = true;
			this.isFboAttachmentDirty = false;
		}
		if (this.isFramebufferDirty) {
			this.activeToolkit.method1922(this.activeDisplay);
			this.isFramebufferDirty = !this.activeDisplay.method3616();
			this.activeToolkit.method1971(this.activeDisplay);
		}
		return !this.isFramebufferDirty;
	}

	@OriginalMember(owner = "client!wb", name = "b", descriptor = "(ILclient!cg;)V")
	public void method5529(@OriginalArg(1) Class4_Sub10 arg0) {
		arg0.aBoolean220 = false;
		arg0.method2006();
		arg0.method5684();
		this.recalculateFormat();
	}

	@OriginalMember(owner = "client!wb", name = "a", descriptor = "(IIIII)Z")
	public boolean prepareForRendering(@OriginalArg(0) int width, @OriginalArg(1) int x, @OriginalArg(3) int arg2, @OriginalArg(4) int y) {
		if (this.activeDisplay == null || this.effectsList.method4147()) {
			return false;
		}
		if (this.width != x || y != this.captureScreenRegion) {
			this.captureScreenRegion = y;
			this.width = x;
			for (@Pc(49) Country local49 = this.effectsList.method4140(); local49 != this.effectsList.aClass4_207; local49 = local49.aClass4_262) {
				((Class4_Sub10) local49).method2007(this.captureScreenRegion, this.width);
			}
			this.pixelFormatSet = true;
			this.isFboAttachmentDirty = true;
			this.isDepthBufferDirty = true;
		}
		if (!this.rebuildFramebuffers()) {
			return false;
		}
		this.vertexCoordinateY = width;
		this.vertexCoordinateX = arg2;
		this.isCompositionPending = true;
		this.activeToolkit.method1922(this.activeDisplay);
		this.activeDisplay.method3610(0);
		this.activeToolkit.method1931(this.captureScreenRegion - this.activeDisplay.method3615() - this.vertexCoordinateX, -this.vertexCoordinateY);
		return true;
	}

	@OriginalMember(owner = "client!wb", name = "d", descriptor = "(I)Z")
	public boolean isDisplayAvailable() {
		return this.activeDisplay != null;
	}

	@OriginalMember(owner = "client!wb", name = "b", descriptor = "(B)V")
	public void compositeEffects() {
		if (!this.isCompositionPending) {
			return;
		}
		if (this.multisampleDisplay != null) {
			this.activeToolkit.method1949(this.multisampleDisplay);
			@Pc(20) short local20 = 16384;
			this.activeToolkit.method1956(this.resolveDisplay);
			this.multisampleDisplay.method3618();
			this.resolveDisplay.method3610(0);
			if (this.loadTexture) {
				local20 = 16640;
			}
			OpenGL.glBlitFramebufferEXT(0, 0, this.width, this.captureScreenRegion, 0, 0, this.width, this.captureScreenRegion, local20, 9728);
			this.activeToolkit.method1935(this.multisampleDisplay);
			this.activeToolkit.method1940(this.resolveDisplay);
		}
		this.activeToolkit.method1930();
		this.activeToolkit.method1958(0);
		this.activeToolkit.method1900(1);
		this.activeToolkit.e();
		@Pc(101) int local101 = 0;
		@Pc(103) int local103 = 1;
		@Pc(117) Class4_Sub10 local117;
		for (@Pc(109) Class4_Sub10 local109 = (Class4_Sub10) this.effectsList.method4140(); local109 != null; local109 = local117) {
			local117 = (Class4_Sub10) this.effectsList.method4144();
			@Pc(121) int local121 = local109.method2001();
			for (@Pc(123) int local123 = 0; local123 < local121; local123++) {
				local109.method2000(local123, this.textures2d[local101], this.activeTexture);
				if (local117 == null && local123 == local121 - 1) {
					this.activeToolkit.method1971(this.resolveDisplay);
					this.activeToolkit.method1931(0, 0);
					OpenGL.glBegin(7);
					OpenGL.glTexCoord2f(0.0F, (float) this.captureScreenRegion);
					OpenGL.glMultiTexCoord2f(33985, 0.0F, 1.0F);
					OpenGL.glVertex2i(this.vertexCoordinateY, this.vertexCoordinateX);
					OpenGL.glTexCoord2f(0.0F, 0.0F);
					OpenGL.glMultiTexCoord2f(33985, 0.0F, 0.0F);
					OpenGL.glVertex2i(this.vertexCoordinateY, this.captureScreenRegion + this.vertexCoordinateX);
					OpenGL.glTexCoord2f((float) this.width, 0.0F);
					OpenGL.glMultiTexCoord2f(33985, 1.0F, 0.0F);
					OpenGL.glVertex2i(this.vertexCoordinateY + this.width, this.captureScreenRegion + this.vertexCoordinateX);
					OpenGL.glTexCoord2f((float) this.width, (float) this.captureScreenRegion);
					OpenGL.glMultiTexCoord2f(33985, 1.0F, 1.0F);
					OpenGL.glVertex2i(this.width + this.vertexCoordinateY, this.vertexCoordinateX);
					OpenGL.glEnd();
				} else {
					this.resolveDisplay.method3610(local103);
					OpenGL.glBegin(7);
					OpenGL.glTexCoord2f(0.0F, 0.0F);
					OpenGL.glMultiTexCoord2f(33985, 0.0F, 0.0F);
					OpenGL.glVertex2i(0, 0);
					OpenGL.glTexCoord2f(0.0F, (float) this.captureScreenRegion);
					OpenGL.glMultiTexCoord2f(33985, 0.0F, 1.0F);
					OpenGL.glVertex2i(0, this.captureScreenRegion);
					OpenGL.glTexCoord2f((float) this.width, (float) this.captureScreenRegion);
					OpenGL.glMultiTexCoord2f(33985, 1.0F, 1.0F);
					OpenGL.glVertex2i(this.width, this.captureScreenRegion);
					OpenGL.glTexCoord2f((float) this.width, 0.0F);
					OpenGL.glMultiTexCoord2f(33985, 1.0F, 0.0F);
					OpenGL.glVertex2i(this.width, 0);
					OpenGL.glEnd();
				}
				local101 = local101 + 1 & 0x1;
				local103 = local103 + 1 & 0x1;
				local109.method1996(local123);
			}
		}
		this.isCompositionPending = false;
	}

@OriginalMember(owner = "client!wb", name = "y", descriptor = "[Ljava/lang/String;")
	public static String[] aStringArray39;
@OriginalMember(owner = "client!wb", name = "h", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_147 = new Class83("Your ignore list is full, max of 100.", "Deine Ignorieren-Liste ist voll, du kannst nur 100 Spieler darauf eintragen.", "Votre liste noire est pleine (100 noms maximum).", "Sua lista de ignorados está cheia. O máx. é 100.");
@OriginalMember(owner = "client!wb", name = "G", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_96 = new Class215(63, -1);
@OriginalMember(owner = "client!wb", name = "H", descriptor = "Z")
	public static boolean aBoolean675 = false;
}
