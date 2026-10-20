package com.jagex.game.runetek5.toolkit.opengl.effect;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.graphics.texture.water.NativeWaterTexture;
import com.jagex.game.runetek5.deob.Class210;
import com.jagex.game.runetek5.deob.Class267;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.config.material.texture.TextureOpKaleidoscope;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorFill;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpConstant;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpCurve;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpDiagonalWaves;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpInterpolate;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpMandelbrot;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpMonochromeEdgeDetector;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpPerlinNoise;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSprite;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTexture;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTile;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTiledSprite;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpVoronoiNoise;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSquareWaveform;
import com.jagex.game.runetek5.config.material.texture.TextureOpFlip;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub12;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub16;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub19;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub23;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub26;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub29;
import com.jagex.game.runetek5.deob.TextureOpBoxBlur;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub33;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub35;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub37;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub38;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub5;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub8;
import com.jagex.game.runetek5.deob.TextureOpBlend;

import com.jagex.game.runetek5.toolkit.opengl.effect.*;
import com.jagex.game.runetek5.LocEntity;
import com.jagex.game.runetek5.deob.TileLighting;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.deob.Class4_Sub36;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.deob.PositionedGfx;
import com.jagex.game.runetek5.deob.Class4_Sub18;
@OriginalClass("client!aj")
public final class ReflectiveRipplingWaterEffect extends Class9 {

	@OriginalMember(owner = "client!aj", name = "u", descriptor = "Z")
	private boolean aBoolean16 = false;

	@OriginalMember(owner = "client!aj", name = "w", descriptor = "Z")
	private boolean aBoolean17 = false;

	@OriginalMember(owner = "client!aj", name = "q", descriptor = "Lclient!m;")
	private final NativeWaterTexture aClass144_1;

	@OriginalMember(owner = "client!aj", name = "o", descriptor = "Lclient!ru;")
	private Class210 aClass210_1;

	@OriginalMember(owner = "client!aj", name = "<init>", descriptor = "(Lclient!fd;Lclient!m;)V")
	public ReflectiveRipplingWaterEffect(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) NativeWaterTexture arg1) {
		super(arg0);
		this.aClass144_1 = arg1;
		if (this.aClass144_1.aClass6_Sub3_2 != null && super.aClass19_Sub2_41.aBoolean217 && super.aClass19_Sub2_41.aBoolean214) {
			@Pc(30) Class267 local30 = MusicPatch.method4397(super.aClass19_Sub2_41, 35633, "uniform float time;\nuniform float scale;\nvarying vec3 wvVertex;\nvarying float waterDepth;\nvoid main() {\nwaterDepth = gl_MultiTexCoord0.z;\nvec4 ecVertex = gl_ModelViewMatrix*gl_Vertex;\nwvVertex.x = dot(gl_NormalMatrix[0], ecVertex.xyz);\nwvVertex.y = dot(gl_NormalMatrix[1], ecVertex.xyz);\nwvVertex.z = dot(gl_NormalMatrix[2], ecVertex.xyz);\ngl_TexCoord[0].x = dot(gl_TextureMatrix[0][0], gl_MultiTexCoord0)*scale;\ngl_TexCoord[0].y = dot(gl_TextureMatrix[0][1], gl_MultiTexCoord0)*scale;\ngl_TexCoord[0].z = time;\ngl_TexCoord[0].w = 1.0;\ngl_FogFragCoord = 1.0-clamp((gl_Fog.end+ecVertex.z)*gl_Fog.scale, 0.0, 1.0);\ngl_Position = ftransform();\n}\n");
			@Pc(37) Class267 local37 = MusicPatch.method4397(super.aClass19_Sub2_41, 35632, "varying vec3 wvVertex;\nvarying float waterDepth;\nuniform vec3 sunDir;\nuniform vec4 sunColour;\nuniform float sunExponent;\nuniform float breakWaterDepth;\nuniform float breakWaterOffset;\nuniform sampler3D normalSampler;\nuniform samplerCube envMapSampler;\nvoid main() {\nvec4 wnNormal = texture3D(normalSampler, gl_TexCoord[0].xyz).rbga;\nwnNormal.xyz = 2.0*wnNormal.xyz-1.0;\nvec3 wnVector = normalize(wvVertex);\nvec3 wnReflection = reflect(wnVector, wnNormal.xyz);\nvec3 envColour = textureCube(envMapSampler, wnReflection).rgb;\nvec4 specularColour = sunColour*pow(clamp(-dot(sunDir, wnReflection), 0.0, 1.0), sunExponent);\nfloat shoreFactor = clamp(waterDepth/breakWaterDepth-breakWaterOffset*wnNormal.w, 0.0, 1.0);\nfloat ndote = dot(wnVector, wnNormal.xyz);\nfloat fresnel = pow(1.0-abs(ndote), 1.0);\nvec4 surfaceColour = vec4(envColour, fresnel*shoreFactor)+specularColour*shoreFactor;\ngl_FragColor = mix(surfaceColour, vec4(gl_Fog.color.rgb, 1.0), gl_FogFragCoord);\n}\n");
			this.aClass210_1 = TileLighting.method3694(new Class267[] { local30, local37 }, super.aClass19_Sub2_41);
			this.aBoolean17 = this.aClass210_1 != null;
		}
	}

	@OriginalMember(owner = "client!aj", name = "a", descriptor = "(ZB)V")
	@Override
	public void applyTextureCombine(@OriginalArg(0) boolean arg0) {
	}

	@OriginalMember(owner = "client!aj", name = "a", descriptor = "(Z)Z")
	@Override
	public boolean isSupported() {
		return false;
	}

	@OriginalMember(owner = "client!aj", name = "a", descriptor = "(IZ)V")
	@Override
	public void enable(@OriginalArg(1) boolean arg0) {
		@Pc(8) GlCubeTexture local8 = super.aClass19_Sub2_41.method1946();
		if (!this.aBoolean17 || local8 == null) {
			return;
		}
		super.aClass19_Sub2_41.method1936(1);
		super.aClass19_Sub2_41.setActiveTexture(local8);
		super.aClass19_Sub2_41.method1936(0);
		super.aClass19_Sub2_41.setActiveTexture(this.aClass144_1.aClass6_Sub3_2);
		@Pc(51) long local51 = this.aClass210_1.aLong202;
		OpenGL.glUseProgramObjectARB(local51);
		OpenGL.glUniform1iARB(OpenGL.glGetUniformLocationARB(local51, "normalSampler"), 0);
		OpenGL.glUniform1iARB(OpenGL.glGetUniformLocationARB(local51, "envMapSampler"), 1);
		OpenGL.glUniform3fARB(OpenGL.glGetUniformLocationARB(local51, "sunDir"), -super.aClass19_Sub2_41.sunDirection[0], -super.aClass19_Sub2_41.sunDirection[1], -super.aClass19_Sub2_41.sunDirection[2]);
		OpenGL.glUniform4fARB(OpenGL.glGetUniformLocationARB(local51, "sunColour"), super.aClass19_Sub2_41.aFloat72, super.aClass19_Sub2_41.aFloat58, super.aClass19_Sub2_41.aFloat74, 1.0F);
		OpenGL.glUniform1fARB(OpenGL.glGetUniformLocationARB(local51, "sunExponent"), Math.abs(super.aClass19_Sub2_41.sunDirection[1]) * 928.0F + 96.0F);
		this.aBoolean16 = true;
	}

	@OriginalMember(owner = "client!aj", name = "a", descriptor = "(I)V")
	@Override
	public void disable() {
		if (!this.aBoolean16) {
			return;
		}
		super.aClass19_Sub2_41.method1936(1);
		super.aClass19_Sub2_41.setActiveTexture(null);
		super.aClass19_Sub2_41.method1936(0);
		super.aClass19_Sub2_41.setActiveTexture(null);
		OpenGL.glUseProgramObjectARB(0L);
		this.aBoolean16 = false;
	}

	@OriginalMember(owner = "client!aj", name = "a", descriptor = "(ILclient!kj;Z)V")
	@Override
	public void method5376(@OriginalArg(0) int arg0, @OriginalArg(1) Class6 arg1) {
		if (!this.aBoolean16) {
			super.aClass19_Sub2_41.setActiveTexture(arg1);
			super.aClass19_Sub2_41.method1900(arg0);
		}
	}

	@OriginalMember(owner = "client!aj", name = "a", descriptor = "(III)V")
	@Override
	public void setEffectParams(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		if (!this.aBoolean16) {
			return;
		}
		@Pc(12) int local12 = 0x1 << (arg0 & 0x3);
		@Pc(23) float local23 = (float) (0x1 << (arg0 >> 3 & 0x7)) / 32.0F;
		@Pc(27) int local27 = arg1 & 0xFFFF;
		@Pc(36) float local36 = (float) (arg1 >> 16 & 0x3) / 8.0F;
		@Pc(40) long local40 = this.aClass210_1.aLong202;
		OpenGL.glUniform1fARB(OpenGL.glGetUniformLocationARB(local40, "time"), (float) (super.aClass19_Sub2_41.anInt2274 * local12 % 40000) / 40000.0F);
		OpenGL.glUniform1fARB(OpenGL.glGetUniformLocationARB(local40, "scale"), local23);
		OpenGL.glUniform1fARB(OpenGL.glGetUniformLocationARB(local40, "breakWaterDepth"), (float) local27);
		OpenGL.glUniform1fARB(OpenGL.glGetUniformLocationARB(local40, "breakWaterOffset"), local36);
	}

@OriginalMember(owner = "client!aj", name = "j", descriptor = "I")
	public static int anInt154;
@OriginalMember(owner = "client!aj", name = "k", descriptor = "[I")
	public static final int[] anIntArray10 = new int[] { 16, 32, 64, 128 };
@OriginalMember(owner = "client!aj", name = "s", descriptor = "Lclient!ib;")
	public static final CubeMapSpecularEffect aClass102_1 = new CubeMapSpecularEffect(8);
@OriginalMember(owner = "client!aj", name = "x", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_16 = new Class22(0, 28);
@OriginalMember(owner = "client!aj", name = "a", descriptor = "(II)Lclient!oc;")
	public static TextureOperation method122(@OriginalArg(0) int arg0) {
		if (arg0 == 0) {
			return new TextureOpConstant();
		} else if (arg0 == 1) {
			return new TextureOpColorFill();
		} else if (arg0 == 2) {
			return new Class4_Sub6_Sub26();
		} else if (arg0 == 3) {
			return new Class4_Sub6_Sub7();
		} else if (arg0 == 4) {
			return new Class4_Sub6_Sub24();
		} else if (arg0 == 5) {
			return new Class4_Sub6_Sub29();
		} else if (arg0 == 6) {
			return new TextureOpInterpolate();
		} else if (arg0 == 7) {
			return new TextureOpBlend();
		} else if (arg0 == 8) {
			return new TextureOpCurve();
		} else if (arg0 == 9) {
			return new TextureOpFlip();
		} else if (arg0 == 10) {
			return new TextureOpColorGradient();
		} else if (arg0 == 11) {
			return new Class4_Sub6_Sub19();
		} else if (arg0 == 12) {
			return new Class4_Sub6_Sub33();
		} else if (arg0 == 13) {
			return new Class4_Sub6_Sub15();
		} else if (arg0 == 14) {
			return new Class4_Sub6_Sub4();
		} else if (arg0 == 15) {
			return new TextureOpVoronoiNoise();
		} else if (arg0 == 16) {
			return new Class4_Sub6_Sub23();
		} else if (arg0 == 17) {
			return new Class4_Sub6_Sub38();
		} else if (arg0 == 18) {
			return new TextureOpTiledSprite();
		} else if (arg0 == 19) {
			return new Class4_Sub6_Sub22();
		} else if (arg0 == 20) {
			return new TextureOpTile();
		} else if (arg0 == 21) {
			return new TextureOpBoxBlur();
		} else if (arg0 == 22) {
			return new Class4_Sub6_Sub25();
		} else if (arg0 == 23) {
			return new TextureOpKaleidoscope();
		} else if (arg0 == 24) {
			return new Class4_Sub6_Sub5();
		} else if (arg0 == 25) {
			return new Class4_Sub6_Sub35();
		} else if (arg0 == 26) {
			return new Class4_Sub6_Sub12();
		} else if (arg0 == 27) {
			return new TextureOpSquareWaveform();
		} else if (arg0 == 28) {
			return new TextureOpIrregularBricks();
		} else if (arg0 == 29) {
			return new Class4_Sub6_Sub8();
		} else if (arg0 == 30) {
			return new Class4_Sub6_Sub31();
		} else if (arg0 == 31) {
			return new TextureOpMandelbrot();
		} else if (arg0 == 32) {
			return new Class4_Sub6_Sub16();
		} else if (arg0 == 33) {
			return new Class4_Sub6_Sub37();
		} else if (arg0 == 34) {
			return new TextureOpPerlinNoise();
		} else if (arg0 == 35) {
			return new TextureOpMonochromeEdgeDetector();
		} else if (arg0 == 36) {
			return new TextureOpTexture();
		} else if (arg0 == 37) {
			return new TextureOpDiagonalWaves();
		} else if (arg0 == 38) {
			return new TextureOpBrightness();
		} else if (arg0 == 39) {
			return new TextureOpSprite();
		} else {
			return null;
		}
	}
@OriginalMember(owner = "client!aj", name = "a", descriptor = "(IIIIIIIBI)V")
	public static void method123(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(8) int arg7) {
		if (Class4_Sub18.method2034(arg4)) {
			if (LocEntity.aClass247ArrayArray1[arg4] == null) {
				PositionedGfx.method2460(arg2, arg3, arg7, arg1, Class4_Sub36.aClass247ArrayArray2[arg4], arg0, -1, arg5, arg6);
			} else {
				PositionedGfx.method2460(arg2, arg3, arg7, arg1, LocEntity.aClass247ArrayArray1[arg4], arg0, -1, arg5, arg6);
			}
		} else if (arg1 == -1) {
			for (@Pc(18) int local18 = 0; local18 < 100; local18++) {
				PingRequest.aBooleanArray21[local18] = true;
			}
		} else {
			PingRequest.aBooleanArray21[arg1] = true;
		}
	}
}
