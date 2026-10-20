package com.jagex.game.runetek5.deob;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.graphics.texture.water.NativeWaterTexture;
import com.jagex.game.runetek5.toolkit.opengl.GlRenderList;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationTypeList;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.graphics.JavaBillboardAttributes;
@OriginalClass("client!ei")
public final class Class9_Sub4 extends Class9 {

	@OriginalMember(owner = "client!ei", name = "q", descriptor = "I")
	private int anInt1825;

	@OriginalMember(owner = "client!ei", name = "t", descriptor = "Lclient!ct;")
	private GlRenderList aClass43_2;

	@OriginalMember(owner = "client!ei", name = "k", descriptor = "Lclient!m;")
	private final NativeWaterTexture aClass144_4;

	@OriginalMember(owner = "client!ei", name = "i", descriptor = "Lclient!td;")
	private Class225 aClass225_1;

	@OriginalMember(owner = "client!ei", name = "o", descriptor = "[F")
	private float[] aFloatArray13;

	@OriginalMember(owner = "client!ei", name = "<init>", descriptor = "(Lclient!fd;Lclient!m;)V")
	public Class9_Sub4(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) NativeWaterTexture arg1) {
		super(arg0);
		this.aClass144_4 = arg1;
		if (super.aClass19_Sub2_41.aBoolean198 && super.aClass19_Sub2_41.anInt2306 >= 2) {
			this.aClass225_1 = ParticleBinaryTree.method5125(super.aClass19_Sub2_41, "!!ARBvp1.0\nOPTION  ARB_position_invariant;\nATTRIB  iPos         = vertex.position;\nATTRIB  iColour      = vertex.color;\nOUTPUT  oColour      = result.color;\nOUTPUT  oTexCoord0   = result.texcoord[0];\nOUTPUT  oTexCoord1   = result.texcoord[1];\nOUTPUT  oFogCoord    = result.fogcoord;\nPARAM   time         = program.local[65];\nPARAM   turbulence   = program.local[64];\nPARAM   lightAmbient = program.local[66]; \nPARAM   pMatrix[4]   = { state.matrix.projection };\nPARAM   mvMatrix[4]  = { state.matrix.modelview };\nPARAM   ivMatrix[4]  = { state.matrix.texture[1] };\nPARAM   fNoise[64]   = { program.local[0..63] };\nTEMP    noise, clipPos, viewPos, worldPos;\nADDRESS noiseAddr;\nDP4   viewPos.x, mvMatrix[0], iPos;\nDP4   viewPos.y, mvMatrix[1], iPos;\nDP4   viewPos.z, mvMatrix[2], iPos;\nDP4   viewPos.w, mvMatrix[3], iPos;\nMOV   oFogCoord.x, -viewPos.z;\nDP4   worldPos.x, ivMatrix[0], viewPos;\nDP4   worldPos.y, ivMatrix[1], viewPos;\nDP4   worldPos.z, ivMatrix[2], viewPos;\nDP4   worldPos.w, ivMatrix[3], viewPos;\nADD   noise.x, worldPos.x, worldPos.z;SUB   noise.y, worldPos.z, worldPos.x;MUL   noise, noise, 0.0001220703125;\nFRC   noise, noise;\nMUL   noise, noise, 64;\nARL   noiseAddr.x, noise.x;\nMOV   noise.x, fNoise[noiseAddr.x].x;\nARL   noiseAddr.x, noise.y;\nMOV   noise.y, fNoise[noiseAddr.x].y;\nMUL   noise, noise, turbulence.x;\nMAD   oTexCoord0, worldPos.xzww, 0.0078125, noise;\nMOV   oTexCoord0.w, 1;\nMUL   oTexCoord1.xy, worldPos.xzww, 0.0009765625;\nMOV   oTexCoord1.zw, time.xxxw;\nMUL   oColour.xyz, iColour, lightAmbient;\nMOV   oColour.w, iColour.w;\nEND");
			if (this.aClass225_1 != null) {
				@Pc(39) int[][] local39 = Class215.method4927(0.4F);
				@Pc(50) int[][] local50 = Class215.method4927(0.4F);
				@Pc(52) int local52 = 0;
				this.aFloatArray13 = new float[32768];
				for (@Pc(58) int local58 = 0; local58 < 256; local58++) {
					@Pc(64) int[] local64 = local39[local58];
					@Pc(68) int[] local68 = local50[local58];
					for (@Pc(70) int local70 = 0; local70 < 64; local70++) {
						this.aFloatArray13[local52++] = (float) local64[local70] / 4096.0F;
						this.aFloatArray13[local52++] = (float) local68[local70] / 4096.0F;
					}
				}
				this.method1600();
			}
		}
	}

	@OriginalMember(owner = "client!ei", name = "a", descriptor = "(IZ)V")
	@Override
	public void enable(@OriginalArg(1) boolean arg0) {
		if (this.aClass43_2 == null) {
			return;
		}
		this.aClass43_2.method989('\u0000');
		super.aClass19_Sub2_41.method1936(1);
		OpenGL.glMatrixMode(5890);
		OpenGL.glLoadMatrixf(super.aClass19_Sub2_41.aClass31_Sub1_3.method5161(), 0);
		OpenGL.glRotatef(-180.0F, 1.0F, 0.0F, 0.0F);
		OpenGL.glMatrixMode(5888);
		super.aClass19_Sub2_41.method1936(0);
		if (this.anInt1825 == super.aClass19_Sub2_41.anInt2274) {
			return;
		}
		@Pc(56) int local56 = super.aClass19_Sub2_41.anInt2274 % 5120 * 128 / 5120;
		local56 *= 128;
		for (@Pc(62) int local62 = 0; local62 < 64; local62++) {
			OpenGL.glProgramLocalParameter4fvARB(34336, local62, this.aFloatArray13, local56);
			local56 += 2;
		}
		if (this.aClass144_4.hasDepth) {
			@Pc(97) float local97 = (float) (super.aClass19_Sub2_41.anInt2274 % 4000) / 4000.0F;
			OpenGL.glProgramLocalParameter4fARB(34336, 65, local97, 0.0F, 0.0F, 1.0F);
		} else {
			OpenGL.glProgramLocalParameter4fARB(34336, 65, 0.0F, 0.0F, 0.0F, 1.0F);
		}
		this.anInt1825 = super.aClass19_Sub2_41.anInt2274;
	}

	@OriginalMember(owner = "client!ei", name = "a", descriptor = "(Z)Z")
	@Override
	public boolean isSupported() {
		return true;
	}

	@OriginalMember(owner = "client!ei", name = "a", descriptor = "(ZB)V")
	@Override
	public void applyTextureCombine(@OriginalArg(0) boolean arg0) {
	}

	@OriginalMember(owner = "client!ei", name = "a", descriptor = "(III)V")
	@Override
	public void setEffectParams(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		if (this.aClass43_2 == null) {
			return;
		}
		super.aClass19_Sub2_41.method1936(1);
		@Pc(48) int local48;
		if ((arg0 & 0x80) != 0) {
			super.aClass19_Sub2_41.setActiveTexture(null);
		} else if (this.aClass144_4.hasDepth) {
			super.aClass19_Sub2_41.setActiveTexture(this.aClass144_4.aClass6_Sub3_3);
		} else {
			local48 = super.aClass19_Sub2_41.anInt2274 % 4000 * 16 / 4000;
			super.aClass19_Sub2_41.setActiveTexture(this.aClass144_4.waterTextureSamplers[local48]);
		}
		super.aClass19_Sub2_41.method1936(0);
		if ((arg0 & 0x40) == 0) {
			OpenGL.glGetFloatv(2899, Static2.aFloatArray1, 0);
			OpenGL.glProgramLocalParameter4fvARB(34336, 66, Static2.aFloatArray1, 0);
		} else {
			OpenGL.glProgramLocalParameter4fARB(34336, 66, 1.0F, 1.0F, 1.0F, 1.0F);
		}
		local48 = arg0 & 0x3;
		if (local48 == 2) {
			OpenGL.glProgramLocalParameter4fARB(34336, 64, 0.05F, 1.0F, 1.0F, 1.0F);
		} else if (local48 == 3) {
			OpenGL.glProgramLocalParameter4fARB(34336, 64, 0.1F, 1.0F, 1.0F, 1.0F);
		} else {
			OpenGL.glProgramLocalParameter4fARB(34336, 64, 0.025F, 1.0F, 1.0F, 1.0F);
		}
	}

	@OriginalMember(owner = "client!ei", name = "a", descriptor = "(I)V")
	@Override
	public void disable() {
		if (this.aClass43_2 != null) {
			this.aClass43_2.method989('\u0001');
			super.aClass19_Sub2_41.method1936(1);
			super.aClass19_Sub2_41.setActiveTexture(null);
			super.aClass19_Sub2_41.method1936(0);
		}
	}

	@OriginalMember(owner = "client!ei", name = "a", descriptor = "(ILclient!kj;Z)V")
	@Override
	public void method5376(@OriginalArg(0) int arg0, @OriginalArg(1) Class6 arg1) {
		super.aClass19_Sub2_41.setActiveTexture(arg1);
		super.aClass19_Sub2_41.method1900(arg0);
	}

	@OriginalMember(owner = "client!ei", name = "b", descriptor = "(B)V")
	private void method1600() {
		this.aClass43_2 = new GlRenderList(super.aClass19_Sub2_41, 2);
		this.aClass43_2.method990(0);
		super.aClass19_Sub2_41.method1936(1);
		super.aClass19_Sub2_41.method1901(-16777216);
		super.aClass19_Sub2_41.method1912(260, 7681);
		super.aClass19_Sub2_41.method1954(34166, 0);
		super.aClass19_Sub2_41.method1936(0);
		OpenGL.glBindProgramARB(34336, this.aClass225_1.anInt6405);
		OpenGL.glEnable(34336);
		this.aClass43_2.method991();
		this.aClass43_2.method990(1);
		super.aClass19_Sub2_41.method1936(1);
		OpenGL.glMatrixMode(5890);
		OpenGL.glLoadIdentity();
		OpenGL.glMatrixMode(5888);
		super.aClass19_Sub2_41.method1900(0);
		super.aClass19_Sub2_41.method1954(5890, 0);
		super.aClass19_Sub2_41.method1936(0);
		OpenGL.glBindProgramARB(34336, 0);
		OpenGL.glDisable(34336);
		OpenGL.glDisable(34820);
		this.aClass43_2.method991();
	}

@OriginalMember(owner = "client!ei", name = "a", descriptor = "(IIII)I")
	public static int method1599(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2) {
		if (arg2 > 243) {
			arg0 >>= 0x4;
		} else if (arg2 > 217) {
			arg0 >>= 0x3;
		} else if (arg2 > 192) {
			arg0 >>= 0x2;
		} else if (arg2 > 179) {
			arg0 >>= 0x1;
		}
		return (arg2 >> 1) + (((arg1 >> 2 & 0x3F) << 10) + (arg0 >> 5 << 7));
	}
@OriginalMember(owner = "client!ei", name = "a", descriptor = "(Ljava/lang/String;I)V")
	public static void method1601(@OriginalArg(0) String arg0) {
		if (!arg0.equals("")) {
			PingWorker.method5476(SpotAnimationTypeList.aClass215_76);
			ParticleNode.aClass4_Sub12_Sub1_5.method2551(JavaBillboardAttributes.method3856(arg0));
			ParticleNode.aClass4_Sub12_Sub1_5.method2518(arg0);
		}
	}
}
