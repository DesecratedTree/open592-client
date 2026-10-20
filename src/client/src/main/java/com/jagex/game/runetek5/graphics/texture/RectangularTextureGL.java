package com.jagex.game.runetek5.graphics.texture;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class210;
import com.jagex.game.runetek5.deob.Class267;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.HelveticaFont;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static99;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.entity.PlayerEntity;

import com.jagex.game.runetek5.graphics.texture.*;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchInfo;
import com.jagex.game.runetek5.deob.TileLighting;
import com.jagex.game.runetek5.deob.TextureOpBoxBlur;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.deob.Class10;
@OriginalClass("client!fh")
public final class RectangularTextureGL extends Class4_Sub10 {

	@OriginalMember(owner = "client!fh", name = "z", descriptor = "I")
	private int anInt2349;

	@OriginalMember(owner = "client!fh", name = "C", descriptor = "Lclient!ru;")
	private Class210 aClass210_4;

	@OriginalMember(owner = "client!fh", name = "H", descriptor = "[Lclient!af;")
	private OpenGl2DTexture[] mipmaps;

	@OriginalMember(owner = "client!fh", name = "I", descriptor = "I")
	private int anInt2354;

	@OriginalMember(owner = "client!fh", name = "K", descriptor = "Lclient!ru;")
	private Class210 aClass210_5;

	@OriginalMember(owner = "client!fh", name = "L", descriptor = "Lclient!af;")
	private OpenGl2DTexture aClass6_Sub1_4;

	@OriginalMember(owner = "client!fh", name = "N", descriptor = "Lclient!af;")
	private OpenGl2DTexture aClass6_Sub1_5;

	@OriginalMember(owner = "client!fh", name = "O", descriptor = "I")
	private int anInt2356;

	@OriginalMember(owner = "client!fh", name = "R", descriptor = "I")
	private int anInt2358;

	@OriginalMember(owner = "client!fh", name = "T", descriptor = "Lclient!ru;")
	private Class210 aClass210_6;

	@OriginalMember(owner = "client!fh", name = "U", descriptor = "Lclient!ru;")
	private Class210 aClass210_7;

	@OriginalMember(owner = "client!fh", name = "X", descriptor = "Lclient!ml;")
	private GlFramebuffer aClass150_3;

	@OriginalMember(owner = "client!fh", name = "Y", descriptor = "Lclient!ml;")
	private GlFramebuffer aClass150_4;

	@OriginalMember(owner = "client!fh", name = "<init>", descriptor = "(Lclient!fd;)V")
	public RectangularTextureGL(@OriginalArg(0) GlToolkit glToolkit) {
		super(glToolkit);
	}

	@OriginalMember(owner = "client!fh", name = "a", descriptor = "(IBLclient!af;Lclient!af;)V")
	@Override
	public void method2000(@OriginalArg(0) int arg0, @OriginalArg(2) OpenGl2DTexture arg1, @OriginalArg(3) OpenGl2DTexture arg2) {
		OpenGL.glPushAttrib(2048);
		OpenGL.glMatrixMode(5889);
		OpenGL.glPushMatrix();
		OpenGL.glLoadIdentity();
		OpenGL.glOrtho(0.0D, 1.0D, 0.0D, 1.0D, -1.0D, 1.0D);
		@Pc(198) long local198;
		if (this.mipmaps == null) {
			super.aClass19_Sub2_16.setActiveTexture(arg1);
			super.aClass19_Sub2_16.method1922(this.aClass150_4);
			this.aClass150_4.method3610(0);
			OpenGL.glViewport(0, 0, 256, 256);
			@Pc(266) long local266 = this.aClass210_6.aLong202;
			OpenGL.glUseProgramObjectARB(local266);
			OpenGL.glUniform1iARB(OpenGL.glGetUniformLocationARB(local266, "sceneTex"), 0);
			OpenGL.glUniform3fARB(OpenGL.glGetUniformLocationARB(local266, "params"), Class4_Sub1_Sub2.aFloat7, 0.0F, 0.0F);
			OpenGL.glBegin(7);
			OpenGL.glTexCoord2f(0.0F, 0.0F);
			OpenGL.glVertex2i(0, 0);
			OpenGL.glTexCoord2f((float) this.anInt2349, 0.0F);
			OpenGL.glVertex2i(1, 0);
			OpenGL.glTexCoord2f((float) this.anInt2349, (float) this.anInt2354);
			OpenGL.glVertex2i(1, 1);
			OpenGL.glTexCoord2f(0.0F, (float) this.anInt2354);
			OpenGL.glVertex2i(0, 1);
			OpenGL.glEnd();
		} else {
			super.aClass19_Sub2_16.method1922(this.aClass150_3);
			@Pc(32) int local32 = CubeMapSpecularEffect.method2696(this.anInt2349);
			@Pc(37) int local37 = CubeMapSpecularEffect.method2696(this.anInt2354);
			@Pc(39) int local39 = 0;
			while (local32 > 256 || local37 > 256) {
				OpenGL.glViewport(0, 0, local32, local37);
				this.aClass150_3.method3621(this.mipmaps[local39], 0);
				if (local39 == 0) {
					super.aClass19_Sub2_16.setActiveTexture(arg1);
					OpenGL.glBegin(7);
					OpenGL.glTexCoord2f(0.0F, 0.0F);
					OpenGL.glVertex2i(0, 0);
					OpenGL.glTexCoord2f((float) this.anInt2349, 0.0F);
					OpenGL.glVertex2i(1, 0);
					OpenGL.glTexCoord2f((float) this.anInt2349, (float) this.anInt2354);
					OpenGL.glVertex2i(1, 1);
					OpenGL.glTexCoord2f(0.0F, (float) this.anInt2354);
					OpenGL.glVertex2i(0, 1);
					OpenGL.glEnd();
				} else {
					super.aClass19_Sub2_16.setActiveTexture(this.mipmaps[local39 - 1]);
					OpenGL.glBegin(7);
					OpenGL.glTexCoord2f(0.0F, 0.0F);
					OpenGL.glVertex2i(0, 0);
					OpenGL.glTexCoord2f(1.0F, 0.0F);
					OpenGL.glVertex2i(1, 0);
					OpenGL.glTexCoord2f(1.0F, 1.0F);
					OpenGL.glVertex2i(1, 1);
					OpenGL.glTexCoord2f(0.0F, 1.0F);
					OpenGL.glVertex2i(0, 1);
					OpenGL.glEnd();
				}
				if (local32 > 256) {
					local32 >>= 0x1;
				}
				if (local37 > 256) {
					local37 >>= 0x1;
				}
				local39++;
			}
			super.aClass19_Sub2_16.method1971(this.aClass150_3);
			super.aClass19_Sub2_16.setActiveTexture(this.mipmaps[local39 - 1]);
			super.aClass19_Sub2_16.method1922(this.aClass150_4);
			this.aClass150_4.method3610(0);
			OpenGL.glViewport(0, 0, 256, 256);
			local198 = this.aClass210_7.aLong202;
			OpenGL.glUseProgramObjectARB(local198);
			OpenGL.glUniform1iARB(OpenGL.glGetUniformLocationARB(local198, "sceneTex"), 0);
			OpenGL.glUniform3fARB(OpenGL.glGetUniformLocationARB(local198, "params"), Class4_Sub1_Sub2.aFloat7, 0.0F, 0.0F);
			OpenGL.glBegin(7);
			OpenGL.glTexCoord2f(0.0F, 0.0F);
			OpenGL.glVertex2i(0, 0);
			OpenGL.glTexCoord2f(1.0F, 0.0F);
			OpenGL.glVertex2i(1, 0);
			OpenGL.glTexCoord2f(1.0F, 1.0F);
			OpenGL.glVertex2i(1, 1);
			OpenGL.glTexCoord2f(0.0F, 1.0F);
			OpenGL.glVertex2i(0, 1);
			OpenGL.glEnd();
		}
		this.aClass150_4.method3610(1);
		super.aClass19_Sub2_16.setActiveTexture(this.aClass6_Sub1_5);
		@Pc(338) long local338 = this.aClass210_4.aLong202;
		OpenGL.glUseProgramObjectARB(local338);
		OpenGL.glUniform1iARB(OpenGL.glGetUniformLocationARB(local338, "baseTex"), 0);
		OpenGL.glUniform3fARB(OpenGL.glGetUniformLocationARB(local338, "step"), 0.00390625F, 0.0F, 0.0F);
		OpenGL.glBegin(7);
		OpenGL.glTexCoord2f(0.0F, 0.0F);
		OpenGL.glVertex2i(0, 0);
		OpenGL.glTexCoord2f(1.0F, 0.0F);
		OpenGL.glVertex2i(1, 0);
		OpenGL.glTexCoord2f(1.0F, 1.0F);
		OpenGL.glVertex2i(1, 1);
		OpenGL.glTexCoord2f(0.0F, 1.0F);
		OpenGL.glVertex2i(0, 1);
		OpenGL.glEnd();
		this.aClass150_4.method3610(0);
		super.aClass19_Sub2_16.setActiveTexture(this.aClass6_Sub1_4);
		OpenGL.glUniform3fARB(OpenGL.glGetUniformLocationARB(local338, "step"), 0.0F, 0.00390625F, 0.0F);
		OpenGL.glBegin(7);
		OpenGL.glTexCoord2f(0.0F, 0.0F);
		OpenGL.glVertex2i(0, 0);
		OpenGL.glTexCoord2f(1.0F, 0.0F);
		OpenGL.glVertex2i(1, 0);
		OpenGL.glTexCoord2f(1.0F, 1.0F);
		OpenGL.glVertex2i(1, 1);
		OpenGL.glTexCoord2f(0.0F, 1.0F);
		OpenGL.glVertex2i(0, 1);
		OpenGL.glEnd();
		OpenGL.glPopAttrib();
		OpenGL.glPopMatrix();
		OpenGL.glMatrixMode(5888);
		super.aClass19_Sub2_16.method1971(this.aClass150_4);
		local198 = this.aClass210_5.aLong202;
		OpenGL.glUseProgramObjectARB(local198);
		OpenGL.glUniform1iARB(OpenGL.glGetUniformLocationARB(local198, "sceneTex"), 0);
		OpenGL.glUniform1iARB(OpenGL.glGetUniformLocationARB(local198, "bloomTex"), 1);
		OpenGL.glUniform3fARB(OpenGL.glGetUniformLocationARB(local198, "params"), MusicPatchInfo.aFloat215, CubeMapSpecularEffect.aFloat108, 0.0F);
		super.aClass19_Sub2_16.method1936(1);
		super.aClass19_Sub2_16.setActiveTexture(this.aClass6_Sub1_5);
		super.aClass19_Sub2_16.method1936(0);
		super.aClass19_Sub2_16.setActiveTexture(arg1);
	}

	@OriginalMember(owner = "client!fh", name = "d", descriptor = "(Z)Z")
	public boolean method2008() {
		return super.aClass19_Sub2_16.extFramebufferObject && super.aClass19_Sub2_16.aBoolean214 && super.aClass19_Sub2_16.arbTextureFloat;
	}

	@OriginalMember(owner = "client!fh", name = "a", descriptor = "(II)V")
	@Override
	public void method1996(@OriginalArg(1) int arg0) {
		OpenGL.glUseProgramObjectARB(0L);
		super.aClass19_Sub2_16.method1936(1);
		super.aClass19_Sub2_16.setActiveTexture(null);
		super.aClass19_Sub2_16.method1936(0);
	}

	@OriginalMember(owner = "client!fh", name = "e", descriptor = "(B)Z")
	@Override
	public boolean method2005() {
		if (!super.aClass19_Sub2_16.extFramebufferObject || !super.aClass19_Sub2_16.aBoolean214 || !super.aClass19_Sub2_16.arbTextureFloat) {
			return false;
		}
		this.aClass150_4 = new GlFramebuffer(super.aClass19_Sub2_16);
		this.aClass6_Sub1_5 = new OpenGl2DTexture(super.aClass19_Sub2_16, 3553, 34842, 256, 256);
		this.aClass6_Sub1_5.method4196(false, false);
		this.aClass6_Sub1_4 = new OpenGl2DTexture(super.aClass19_Sub2_16, 3553, 34842, 256, 256);
		this.aClass6_Sub1_4.method4196(false, false);
		super.aClass19_Sub2_16.method1922(this.aClass150_4);
		this.aClass150_4.method3621(this.aClass6_Sub1_5, 0);
		this.aClass150_4.method3621(this.aClass6_Sub1_4, 1);
		this.aClass150_4.method3610(0);
		if (!this.aClass150_4.method3616()) {
			super.aClass19_Sub2_16.method1971(this.aClass150_4);
			return false;
		}
		super.aClass19_Sub2_16.method1971(this.aClass150_4);
		this.aClass210_6 = TileLighting.method3694(new Class267[] { MusicPatch.method4397(super.aClass19_Sub2_16, 35632, "#extension GL_ARB_texture_rectangle : enable\nuniform vec3 params;\nuniform sampler2DRect sceneTex;\nconst vec3 lumCoef = vec3(0.2126, 0.7152, 0.0722);\nvoid main() {\n    vec4 col = texture2DRect(sceneTex, gl_TexCoord[0].xy);\n    gl_FragColor = col*step(params.x, dot(lumCoef, col.rgb));\n}\n") }, super.aClass19_Sub2_16);
		this.aClass210_7 = TileLighting.method3694(new Class267[] { MusicPatch.method4397(super.aClass19_Sub2_16, 35632, "uniform vec3 params;\nuniform sampler2D sceneTex;\nconst vec3 lumCoef = vec3(0.2126, 0.7152, 0.0722);\nvoid main() {\n    vec4 col = texture2D(sceneTex, gl_TexCoord[0].xy);\n    gl_FragColor = col*step(params.x, dot(lumCoef, col.rgb));\n}\n") }, super.aClass19_Sub2_16);
		this.aClass210_5 = TileLighting.method3694(new Class267[] { MusicPatch.method4397(super.aClass19_Sub2_16, 35632, "#extension GL_ARB_texture_rectangle : enable\nuniform vec3 params;\nuniform vec3 dimScale;\nuniform sampler2D bloomTex;\nuniform sampler2DRect sceneTex;\nconst vec3 lumCoef = vec3(0.2126, 0.7152, 0.0722);\nvoid main() {\n\t vec4 bloomCol = texture2D(bloomTex, gl_TexCoord[1].xy);\n\t vec4 sceneCol = texture2DRect(sceneTex, gl_TexCoord[0].xy);\n\t float preLum = 0.99*dot(lumCoef, sceneCol.rgb)+0.01;\n    float postLum = preLum*(1.0+(preLum/params.y))/(preLum+1.0);\n\t gl_FragColor = sceneCol*(postLum/preLum)+bloomCol*params.x;\n}\n") }, super.aClass19_Sub2_16);
		this.aClass210_4 = TileLighting.method3694(new Class267[] { MusicPatch.method4397(super.aClass19_Sub2_16, 35632, "uniform vec3 step;\nuniform sampler2D baseTex;\nvoid main() {\n\tvec4 fragCol = texture2D(baseTex, gl_TexCoord[0].xy)*0.091396265;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+(-1.0*step.xy))*0.088584304;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+( 1.0*step.xy))*0.088584304;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+(-2.0*step.xy))*0.08065692;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+( 2.0*step.xy))*0.08065692;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+(-3.0*step.xy))*0.068989515;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+( 3.0*step.xy))*0.068989515;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+(-4.0*step.xy))*0.055434637;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+( 4.0*step.xy))*0.055434637;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+(-5.0*step.xy))*0.04184426;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+( 5.0*step.xy))*0.04184426;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+(-6.0*step.xy))*0.029672023;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+( 6.0*step.xy))*0.029672023;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+(-7.0*step.xy))*0.019765828;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+( 7.0*step.xy))*0.019765828;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+(-8.0*step.xy))*0.012369139;\n\tfragCol += texture2D(baseTex, gl_TexCoord[0].xy+( 8.0*step.xy))*0.012369139;\n\tgl_FragColor = fragCol;\n}\n") }, super.aClass19_Sub2_16);
		return this.aClass210_7 != null && this.aClass210_6 != null && this.aClass210_5 != null && this.aClass210_4 != null;
	}

	@OriginalMember(owner = "client!fh", name = "d", descriptor = "(B)Z")
	@Override
	public boolean method2004() {
		return this.aClass150_4 != null;
	}

	@OriginalMember(owner = "client!fh", name = "b", descriptor = "(Z)I")
	@Override
	public int method1999() {
		return 1;
	}

	@OriginalMember(owner = "client!fh", name = "c", descriptor = "(III)V")
	@Override
	public void method2007(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		this.anInt2349 = arg1;
		this.anInt2354 = arg0;
		@Pc(20) int local20 = CubeMapSpecularEffect.method2696(this.anInt2349);
		@Pc(25) int local25 = CubeMapSpecularEffect.method2696(this.anInt2354);
		if (local20 == this.anInt2356 && this.anInt2358 == local25) {
			return;
		}
		@Pc(42) int local42;
		if (this.mipmaps != null) {
			for (local42 = 0; local42 < this.mipmaps.length; local42++) {
				this.mipmaps[local42].method4183();
			}
			this.mipmaps = null;
		}
		if (local20 <= 256 && local25 <= 256) {
			this.aClass150_3 = null;
		} else {
			local42 = local20;
			@Pc(82) int local82 = local25;
			@Pc(84) int local84 = 0;
			if (this.aClass150_3 == null) {
				this.aClass150_3 = new GlFramebuffer(super.aClass19_Sub2_16);
			}
			label56: while (true) {
				if (local42 <= 256 && local82 <= 256) {
					local42 = local20;
					this.mipmaps = new OpenGl2DTexture[local84];
					local82 = local25;
					local84 = 0;
					while (true) {
						if (local42 <= 256 && local82 <= 256) {
							break label56;
						}
						this.mipmaps[local84++] = new OpenGl2DTexture(super.aClass19_Sub2_16, 3553, 34842, local42, local82);
						if (local82 > 256) {
							local82 >>= 0x1;
						}
						if (local42 > 256) {
							local42 >>= 0x1;
						}
					}
				}
				if (local82 > 256) {
					local82 >>= 0x1;
				}
				if (local42 > 256) {
					local42 >>= 0x1;
				}
				local84++;
			}
		}
		this.anInt2356 = local20;
		this.anInt2358 = local25;
	}

	@OriginalMember(owner = "client!fh", name = "f", descriptor = "(B)V")
	@Override
	public void method2006() {
		this.aClass210_6 = null;
		this.aClass6_Sub1_5 = null;
		this.mipmaps = null;
		this.aClass150_4 = null;
		this.aClass150_3 = null;
		this.aClass210_4 = null;
		this.aClass210_7 = null;
		this.aClass210_5 = null;
		this.aClass6_Sub1_4 = null;
	}

@OriginalMember(owner = "client!fh", name = "A", descriptor = "Lclient!sb;")
	public static HelveticaFont aClass212_3;
@OriginalMember(owner = "client!fh", name = "B", descriptor = "[[I")
	public static final int[][] anIntArrayArray23 = new int[][] { { 2, 4 }, { 2, 4 }, { 5, 2, 4 }, { 4, 5, 2 }, { 2, 4, 5 }, { 5, 2, 4 }, { 1, 6, 2, 5 }, { 1, 6, 7, 1 }, { 6, 7, 1, 1 }, { 0, 8, 9, 8, 9, 4 }, { 8, 9, 4, 0, 8, 9 }, { 2, 10, 0, 10, 11, 11 }, { 2, 4 }, { 1, 6, 7, 1 }, { 1, 6, 7, 1 } };
@OriginalMember(owner = "client!fh", name = "P", descriptor = "[[S")
	public static final short[][] aShortArrayArray6 = new short[][] { { 6554, 115, 10304, 28, 5702, 7756, 5681, 4510, -31835, 22437, 2859, -11339, 16, 5157, 10446, 3658, -27314, -21965, 472, 580, 784, 21966, 28950, -15697, -14002 }, { 9104, 10275, 7595, 3610, 7975, 8526, 918, -26734, 24466, 10145, -6882, 5027, 1457, 16565, -30545, 25486, 24, 5392, 10429, 3673, -27335, -21957, 192, 687, 412, 21821, 28835, -15460, -14019 }, new short[0], new short[0], new short[0] };
@OriginalMember(owner = "client!fh", name = "W", descriptor = "I")
	public static int anInt2361 = -50;
@OriginalMember(owner = "client!fh", name = "e", descriptor = "(Z)V")
	public static void method2009() {
		@Pc(7) int local7 = Class4_Sub7.anInt606;
		@Pc(9) int[] local9 = Static99.anIntArray157;
		for (@Pc(16) int local16 = 0; local16 < local7; local16++) {
			@Pc(24) PlayerEntity local24 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local9[local16]];
			if (local24 != null && local24.anInt6077 > 0) {
				local24.anInt6077--;
				if (local24.anInt6077 == 0) {
					local24.aString60 = null;
				}
			}
		}
		for (@Pc(50) int local50 = 0; local50 < Class10.anInt163; local50++) {
			@Pc(56) int local56 = TextureOpBoxBlur.anIntArray412[local50];
			@Pc(60) NPCEntity local60 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local56];
			if (local60 != null && local60.anInt6077 > 0) {
				local60.anInt6077--;
				if (local60.anInt6077 == 0) {
					local60.aString60 = null;
				}
			}
		}
	}
}
