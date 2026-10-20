package com.jagex.game.runetek5.graphics.shader;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.GlVolumeTexture;
import com.jagex.game.runetek5.cache.node.lru.OpenGlRenderBuffer;
import com.jagex.game.runetek5.deob.Class210;
import com.jagex.game.runetek5.deob.Class267;
import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class16_Sub1;
import com.jagex.game.runetek5.deob.Class7;
import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.network.packet.ServerProt;

import com.jagex.game.runetek5.graphics.shader.*;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.deob.TileLighting;
import com.jagex.game.runetek5.deob.Class14;
@OriginalClass("client!de")
public final class Texture3DShaderGL {

	@OriginalMember(owner = "client!de", name = "d", descriptor = "Lclient!ru;")
	private Class210 aClass210_2;

	@OriginalMember(owner = "client!de", name = "b", descriptor = "Lclient!fd;")
	private final GlToolkit openglToolkit;

	@OriginalMember(owner = "client!de", name = "<init>", descriptor = "(Lclient!fd;)V")
	public Texture3DShaderGL(@OriginalArg(0) GlToolkit glToolkit) {
		this.openglToolkit = glToolkit;
	}

	@OriginalMember(owner = "client!de", name = "b", descriptor = "(I)Z")
	public boolean createShader() {
		if (this.openglToolkit.extFramebufferObject && this.openglToolkit.aBoolean214 && this.aClass210_2 == null) {
			@Pc(26) Class267 local26 = MusicPatch.method4397(this.openglToolkit, 35632, "uniform float rcpRelief;\nuniform vec2 sampleSize;\nuniform sampler3D heightMap;\nvoid main() {\nfloat dx = texture3D(heightMap, vec3(-sampleSize.x, 0.0, 0.0)+gl_TexCoord[0].xyz).r - texture3D(heightMap, vec3(sampleSize.x, 0.0, 0.0)+gl_TexCoord[0].xyz).r;\nfloat dy = texture3D(heightMap, vec3(0.0, -sampleSize.y, 0.0)+gl_TexCoord[0].xyz).r - texture3D(heightMap, vec3(0.0, sampleSize.y, 0.0)+gl_TexCoord[0].xyz).r;\ngl_FragColor = vec4(0.5+normalize(vec3(dx, dy, rcpRelief))*0.5, texture3D(heightMap, gl_TexCoord[0].xyz).r);\n}\n");
			if (local26 != null) {
				this.aClass210_2 = TileLighting.method3694(new Class267[] { local26 }, this.openglToolkit);
			}
		}
		return this.aClass210_2 != null;
	}

	@OriginalMember(owner = "client!de", name = "a", descriptor = "(FLclient!cf;ILclient!cf;)Z")
	public boolean method1250(@OriginalArg(0) float arg0, @OriginalArg(1) GlVolumeTexture arg1, @OriginalArg(3) GlVolumeTexture arg2) {
		if (!this.createShader()) {
			return false;
		}
		@Pc(15) GlFramebuffer local15 = this.openglToolkit.aClass150_2;
		@Pc(26) OpenGlRenderBuffer local26 = new OpenGlRenderBuffer(this.openglToolkit, 6408, arg2.anInt833, arg2.anInt841);
		this.openglToolkit.method1922(local15);
		@Pc(38) boolean local38 = false;
		local15.method3614(local26, 0);
		if (local15.method3616()) {
			OpenGL.glPushMatrix();
			OpenGL.glLoadIdentity();
			OpenGL.glMatrixMode(5889);
			OpenGL.glPushMatrix();
			OpenGL.glLoadIdentity();
			OpenGL.glOrtho(0.0D, 1.0D, 0.0D, 1.0D, -1.0D, 1.0D);
			OpenGL.glPushAttrib(2048);
			OpenGL.glViewport(0, 0, arg2.anInt833, arg2.anInt841);
			OpenGL.glUseProgramObjectARB(this.aClass210_2.aLong202);
			OpenGL.glUniform1iARB(OpenGL.glGetUniformLocationARB(this.aClass210_2.aLong202, "heightMap"), 0);
			OpenGL.glUniform1fARB(OpenGL.glGetUniformLocationARB(this.aClass210_2.aLong202, "rcpRelief"), 1.0F / arg0);
			OpenGL.glUniform2fARB(OpenGL.glGetUniformLocationARB(this.aClass210_2.aLong202, "sampleSize"), 1.0F / (float) arg1.anInt833, 1.0F / (float) arg1.anInt841);
			for (@Pc(107) int local107 = 0; local107 < arg2.anInt835; local107++) {
				@Pc(116) float local116 = (float) local107 / (float) arg2.anInt835;
				this.openglToolkit.setActiveTexture(arg1);
				OpenGL.glBegin(7);
				OpenGL.glTexCoord3f(0.0F, 0.0F, local116);
				OpenGL.glVertex2f(0.0F, 0.0F);
				OpenGL.glTexCoord3f(1.0F, 0.0F, local116);
				OpenGL.glVertex2f(1.0F, 0.0F);
				OpenGL.glTexCoord3f(1.0F, 1.0F, local116);
				OpenGL.glVertex2f(1.0F, 1.0F);
				OpenGL.glTexCoord3f(0.0F, 1.0F, local116);
				OpenGL.glVertex2f(0.0F, 1.0F);
				OpenGL.glEnd();
				arg2.method793(arg2.anInt833, local107, arg2.anInt841);
			}
			OpenGL.glUseProgramObjectARB(0L);
			OpenGL.glPopAttrib();
			OpenGL.glPopMatrix();
			OpenGL.glMatrixMode(5888);
			local38 = true;
			OpenGL.glPopMatrix();
		}
		local15.method3611(0);
		this.openglToolkit.method1971(local15);
		return local38;
	}

@OriginalMember(owner = "client!de", name = "c", descriptor = "Lclient!jc;")
	public static final ServerProt aClass117_1 = new ServerProt("WIP", 2);
@OriginalMember(owner = "client!de", name = "e", descriptor = "[Z")
	public static final boolean[] aBooleanArray9 = new boolean[100];
@OriginalMember(owner = "client!de", name = "f", descriptor = "I")
	public static int anInt1369 = 16777215;
@OriginalMember(owner = "client!de", name = "j", descriptor = "I")
	public static int anInt1373 = 0;
@OriginalMember(owner = "client!de", name = "a", descriptor = "([[[Lclient!nt;I)V")
	public static void method1246(@OriginalArg(0) Class164[][][] arg0) {
		for (@Pc(11) int local11 = 0; local11 < arg0.length; local11++) {
			@Pc(17) Class164[][] local17 = arg0[local11];
			for (@Pc(19) int local19 = 0; local19 < local17.length; local19++) {
				for (@Pc(23) int local23 = 0; local23 < local17[local19].length; local23++) {
					@Pc(31) Class164 local31 = local17[local19][local23];
					if (local31 != null) {
						if (local31.aClass16_Sub4_2 instanceof GameObject) {
							((GameObject) local31.aClass16_Sub4_2).upload();
						}
						if (local31.aClass16_Sub2_2 instanceof GameObject) {
							((GameObject) local31.aClass16_Sub2_2).upload();
						}
						if (local31.aClass16_Sub2_3 instanceof GameObject) {
							((GameObject) local31.aClass16_Sub2_3).upload();
						}
						if (local31.aClass16_Sub5_3 instanceof GameObject) {
							((GameObject) local31.aClass16_Sub5_3).upload();
						}
						if (local31.aClass16_Sub5_2 instanceof GameObject) {
							((GameObject) local31.aClass16_Sub5_2).upload();
						}
						for (@Pc(81) Class7 local81 = local31.aClass7_3; local81 != null; local81 = local81.aClass7_1) {
							@Pc(86) Class16_Sub1 local86 = local81.aClass16_Sub1_1;
							if (local86 instanceof GameObject) {
								((GameObject) local86).upload();
							}
						}
					}
				}
			}
		}
	}
@OriginalMember(owner = "client!de", name = "a", descriptor = "(Lclient!fs;I)V")
	public static void method1249(@OriginalArg(0) Js5ResourceProvider arg0) {
		Class14.aClass76_4 = arg0;
	}
}
