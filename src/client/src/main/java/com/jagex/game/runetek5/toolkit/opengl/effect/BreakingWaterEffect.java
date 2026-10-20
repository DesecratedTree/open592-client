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
import com.jagex.game.runetek5.entity.ParticleSystem;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.LocEntity;
import com.jagex.game.runetek5.deob.TileLighting;
import com.jagex.game.runetek5.config.sequence.PathingEntitySequence;
import com.jagex.game.runetek5.deob.Class168;
import com.jagex.game.runetek5.graphics.texture.sampler.GlRectangleTexture;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.deob.Class259;
@OriginalClass("client!dk")
public final class BreakingWaterEffect extends Class9 {

	@OriginalMember(owner = "client!dk", name = "q", descriptor = "Z")
	private boolean aBoolean146 = false;

	@OriginalMember(owner = "client!dk", name = "u", descriptor = "Z")
	private boolean aBoolean147 = false;

	@OriginalMember(owner = "client!dk", name = "t", descriptor = "Lclient!m;")
	private final NativeWaterTexture aClass144_2;

	@OriginalMember(owner = "client!dk", name = "k", descriptor = "Lclient!ru;")
	private Class210 aClass210_3;

	@OriginalMember(owner = "client!dk", name = "<init>", descriptor = "(Lclient!fd;Lclient!m;)V")
	public BreakingWaterEffect(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) NativeWaterTexture arg1) {
		super(arg0);
		this.aClass144_2 = arg1;
		if (this.aClass144_2.aClass6_Sub3_2 != null && super.aClass19_Sub2_41.aBoolean217 && super.aClass19_Sub2_41.aBoolean214) {
			@Pc(30) Class267 local30 = MusicPatch.method4397(super.aClass19_Sub2_41, 35633, "uniform float time;\nuniform float scale;\nvarying vec3 wvVertex;\nvarying float waterDepth;\nvoid main() {\nwaterDepth = gl_MultiTexCoord0.z;\nvec4 ecVertex = gl_ModelViewMatrix*gl_Vertex;\nwvVertex.x = dot(gl_NormalMatrix[0], ecVertex.xyz);\nwvVertex.y = dot(gl_NormalMatrix[1], ecVertex.xyz);\nwvVertex.z = dot(gl_NormalMatrix[2], ecVertex.xyz);\ngl_TexCoord[0].x = dot(gl_TextureMatrix[0][0], gl_MultiTexCoord0)*scale;\ngl_TexCoord[0].y = dot(gl_TextureMatrix[0][1], gl_MultiTexCoord0)*scale;\ngl_TexCoord[0].z = time;\ngl_TexCoord[0].w = 1.0;\ngl_FogFragCoord = 1.0-clamp((gl_Fog.end+ecVertex.z)*gl_Fog.scale, 0.0, 1.0);\ngl_Position = ftransform();\n}\n");
			@Pc(37) Class267 local37 = MusicPatch.method4397(super.aClass19_Sub2_41, 35632, "varying vec3 wvVertex;\nvarying float waterDepth;\nuniform vec3 sunDir;\nuniform vec4 sunColour;\nuniform float sunExponent;\nuniform vec2 waveIntensity;\nuniform float waveExponent;\nuniform float breakWaterDepth;\nuniform float breakWaterOffset;\nuniform sampler3D normalSampler;\nuniform samplerCube envMapSampler;\nvoid main() {\nvec4 wnNormal = texture3D(normalSampler, gl_TexCoord[0].xyz).rbga;\nwnNormal.xyz = 2.0*wnNormal.xyz-1.0;\nvec3 wnVector = normalize(wvVertex);\nvec3 wnReflection = reflect(wnVector, wnNormal.xyz);\nvec3 envColour = textureCube(envMapSampler, wnReflection).rgb;\nvec4 specularColour = sunColour*pow(clamp(-dot(sunDir, wnReflection), 0.0, 1.0), sunExponent);\nfloat shoreFactor = clamp(waterDepth/breakWaterDepth-breakWaterOffset*wnNormal.w, 0.0, 1.0);\nfloat waveFactor = pow(1.0-shoreFactor, waveExponent)-0.5;\nwaveFactor = -4.0*waveFactor*waveFactor+1.0;\nfloat ndote = dot(wnVector, wnNormal.xyz);\nfloat fresnel = pow(1.0-abs(ndote), 1.0);\nvec4 surfaceColour = mix(vec4(envColour, fresnel*shoreFactor), (waveIntensity.x*wnNormal.wwww)+waveIntensity.y, waveFactor)+specularColour*shoreFactor;\ngl_FragColor = mix(surfaceColour, vec4(gl_Fog.color.rgb, 1.0), gl_FogFragCoord);\n}\n");
			this.aClass210_3 = TileLighting.method3694(new Class267[] { local30, local37 }, super.aClass19_Sub2_41);
			this.aBoolean146 = this.aClass210_3 != null;
		}
	}

	@OriginalMember(owner = "client!dk", name = "a", descriptor = "(IZ)V")
	@Override
	public void enable(@OriginalArg(1) boolean arg0) {
		@Pc(13) GlCubeTexture local13 = super.aClass19_Sub2_41.method1946();
		if (!this.aBoolean146 || local13 == null) {
			return;
		}
		@Pc(31) float local31 = (1.0F - Math.abs(super.aClass19_Sub2_41.sunDirection[1])) * 2.0F + 1.0F;
		super.aClass19_Sub2_41.method1936(1);
		super.aClass19_Sub2_41.setActiveTexture(local13);
		super.aClass19_Sub2_41.method1936(0);
		super.aClass19_Sub2_41.setActiveTexture(this.aClass144_2.aClass6_Sub3_2);
		@Pc(59) long local59 = this.aClass210_3.aLong202;
		OpenGL.glUseProgramObjectARB(local59);
		OpenGL.glUniform1iARB(OpenGL.glGetUniformLocationARB(local59, "normalSampler"), 0);
		OpenGL.glUniform1iARB(OpenGL.glGetUniformLocationARB(local59, "envMapSampler"), 1);
		OpenGL.glUniform3fARB(OpenGL.glGetUniformLocationARB(local59, "sunDir"), -super.aClass19_Sub2_41.sunDirection[0], -super.aClass19_Sub2_41.sunDirection[1], -super.aClass19_Sub2_41.sunDirection[2]);
		OpenGL.glUniform4fARB(OpenGL.glGetUniformLocationARB(local59, "sunColour"), local31 * super.aClass19_Sub2_41.aFloat72, super.aClass19_Sub2_41.aFloat58 * local31, super.aClass19_Sub2_41.aFloat74 * local31, 1.0F);
		OpenGL.glUniform1fARB(OpenGL.glGetUniformLocationARB(local59, "sunExponent"), Math.abs(super.aClass19_Sub2_41.sunDirection[1]) * 928.0F + 64.0F);
		this.aBoolean147 = true;
	}

	@OriginalMember(owner = "client!dk", name = "a", descriptor = "(Z)Z")
	@Override
	public boolean isSupported() {
		return this.aBoolean146;
	}

	@OriginalMember(owner = "client!dk", name = "a", descriptor = "(III)V")
	@Override
	public void setEffectParams(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		if (!this.aBoolean147) {
			return;
		}
		@Pc(8) int local8 = 0x1 << (arg0 & 0x3);
		@Pc(19) float local19 = (float) (0x1 << (arg0 >> 3 & 0x7)) / 32.0F;
		@Pc(23) int local23 = arg1 & 0xFFFF;
		@Pc(32) float local32 = (float) (arg1 >> 16 & 0x3) / 8.0F;
		@Pc(41) float local41 = (float) (arg1 >> 19 & 0xF) / 16.0F;
		@Pc(50) float local50 = (float) (arg1 >> 23 & 0xF) / 16.0F;
		@Pc(56) int local56 = arg1 >> 27 & 0xF;
		@Pc(60) long local60 = this.aClass210_3.aLong202;
		OpenGL.glUniform1fARB(OpenGL.glGetUniformLocationARB(local60, "time"), (float) (super.aClass19_Sub2_41.anInt2274 * local8 % 40000) / 40000.0F);
		OpenGL.glUniform1fARB(OpenGL.glGetUniformLocationARB(local60, "scale"), local19);
		OpenGL.glUniform1fARB(OpenGL.glGetUniformLocationARB(local60, "breakWaterDepth"), (float) local23);
		OpenGL.glUniform1fARB(OpenGL.glGetUniformLocationARB(local60, "breakWaterOffset"), local32);
		OpenGL.glUniform2fARB(OpenGL.glGetUniformLocationARB(local60, "waveIntensity"), local50, local41);
		OpenGL.glUniform1fARB(OpenGL.glGetUniformLocationARB(local60, "waveExponent"), (float) local56);
	}

	@OriginalMember(owner = "client!dk", name = "a", descriptor = "(ILclient!kj;Z)V")
	@Override
	public void method5376(@OriginalArg(0) int arg0, @OriginalArg(1) Class6 arg1) {
		if (!this.aBoolean147) {
			super.aClass19_Sub2_41.setActiveTexture(arg1);
			super.aClass19_Sub2_41.method1900(arg0);
		}
	}

	@OriginalMember(owner = "client!dk", name = "a", descriptor = "(I)V")
	@Override
	public void disable() {
		if (!this.aBoolean147) {
			return;
		}
		super.aClass19_Sub2_41.method1936(1);
		super.aClass19_Sub2_41.setActiveTexture(null);
		super.aClass19_Sub2_41.method1936(0);
		super.aClass19_Sub2_41.setActiveTexture(null);
		OpenGL.glUseProgramObjectARB(0L);
		this.aBoolean147 = false;
	}

	@OriginalMember(owner = "client!dk", name = "a", descriptor = "(ZB)V")
	@Override
	public void applyTextureCombine(@OriginalArg(0) boolean arg0) {
	}

@OriginalMember(owner = "client!dk", name = "w", descriptor = "I")
	public static int anInt1504;
@OriginalMember(owner = "client!dk", name = "i", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_31 = new Class83(" more options", " weitere Optionen", " autres options", " mais opções");
@OriginalMember(owner = "client!dk", name = "l", descriptor = "[S")
	public static final short[] aShortArray43 = new short[] { -4160, -4163, -8256, -8259, 22461 };
@OriginalMember(owner = "client!dk", name = "v", descriptor = "I")
	public static int anInt1503 = 64;
@OriginalMember(owner = "client!dk", name = "x", descriptor = "Lclient!ho;")
	public static final QuickChatValueType aClass98_4 = new QuickChatValueType(2, 4, 4, 0);
@OriginalMember(owner = "client!dk", name = "y", descriptor = "[[S")
	public static final short[][] aShortArrayArray3 = new short[][] { { 6798, 107, 10283, 16, 4797, 7744, 5799, 4634, -31839, 22433, 2983, -11343, 8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010 }, { 8741, 12, -1506, -22374, 7735, 8404, 1701, -27106, 24094, 10153, -8915, 4783, 1341, 16578, -30533, 25239, 8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010 }, { 25238, 8742, 12, -1506, -22374, 7735, 8404, 1701, -27106, 24094, 10153, -8915, 4783, 1341, 16578, -30533, 8, 5281, 10438, 3650, -27322, -21845, 200, 571, 908, 21830, 28946, -15701, -14010 }, { 4626, 11146, 6439, 12, 4758, 10270 }, { 4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574 } };
@OriginalMember(owner = "client!dk", name = "z", descriptor = "[I")
	public static final int[] anIntArray113 = new int[] { 2047, 16383, 65535 };
@OriginalMember(owner = "client!dk", name = "a", descriptor = "(JLclient!ya;I)V")
	public static void method1340(@OriginalArg(0) long arg0, @OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg1) {
		GlRectangleTexture.anInt5116 = Class259.anInt7099;
		PathingEntitySequence.anInt5137 = 0;
		Class259.anInt7099 = 0;
		@Pc(18) long local18 = Class168.method3962();
		for (@Pc(23) ParticleSystem local23 = (ParticleSystem) Static384.aClass84_8.method2312(); local23 != null; local23 = (ParticleSystem) Static384.aClass84_8.method2309()) {
			if (local23.performUpdate(arg1, arg0)) {
				PathingEntitySequence.anInt5137++;
			}
		}
		if (LocEntity.aBoolean53 && arg0 % 100L == 0L) {
			System.out.println("Particle system count: " + Static384.aClass84_8.method2307() + ", running: " + PathingEntitySequence.anInt5137 + ". Particles: " + Class259.anInt7099 + ". Time taken: " + (Class168.method3962() - local18) + "ms");
		}
	}
}
