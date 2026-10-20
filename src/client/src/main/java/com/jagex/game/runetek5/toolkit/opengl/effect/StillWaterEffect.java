package com.jagex.game.runetek5.toolkit.opengl.effect;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class225;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.QuickChatPhrase;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.midi.SoundBankCache;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Static384;
import com.jagex.game.runetek5.deob.TileLighting;
import com.jagex.game.runetek5.toolkit.opengl.effect.*;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class60;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.deob.Class53;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.sound.player.PcmPlayer;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Class145_Sub1;
import com.jagex.game.runetek5.toolkit.opengl.GlRenderList;
import com.jagex.game.runetek5.deob.Class208;
import com.jagex.game.runetek5.core.io.DummyInputStream;
@OriginalClass("client!ve")
public final class StillWaterEffect extends Class9 {

	@OriginalMember(owner = "client!ve", name = "u", descriptor = "Z")
	private boolean aBoolean635;

	@OriginalMember(owner = "client!ve", name = "w", descriptor = "Z")
	private boolean aBoolean636;

	@OriginalMember(owner = "client!ve", name = "x", descriptor = "Lclient!td;")
	private Class225 aClass225_4;

	@OriginalMember(owner = "client!ve", name = "E", descriptor = "Lclient!td;")
	private Class225 aClass225_5;

	@OriginalMember(owner = "client!ve", name = "s", descriptor = "Lclient!td;")
	private Class225 aClass225_3;

	@OriginalMember(owner = "client!ve", name = "i", descriptor = "Lclient!td;")
	private Class225 aClass225_2;

	@OriginalMember(owner = "client!ve", name = "t", descriptor = "Z")
	private final boolean aBoolean634;

	@OriginalMember(owner = "client!ve", name = "<init>", descriptor = "(Lclient!fd;)V")
	public StillWaterEffect(@OriginalArg(0) GlToolkit arg0) {
		super(arg0);
		if (super.aClass19_Sub2_41.aBoolean198) {
			this.aClass225_4 = ParticleBinaryTree.method5125(super.aClass19_Sub2_41, "!!ARBvp1.0\nATTRIB  iPos         = vertex.position;\nATTRIB  iColour      = vertex.color;\nATTRIB  iTexCoord    = vertex.texcoord[0];\nOUTPUT  oPos         = result.position;\nOUTPUT  oColour      = result.color;\nOUTPUT  oTexCoord0   = result.texcoord[0];\nOUTPUT  oFogCoord    = result.fogcoord;\nPARAM   fogParams    = program.local[0];\nPARAM   waterPlane   = state.texgen.eye.s;\nPARAM   tMatrix[4]   = { state.matrix.texture[0] };\nPARAM   pMatrix[4]   = { state.matrix.projection };\nPARAM   mvMatrix[4]  = { state.matrix.modelview };\nTEMP    viewPos, fogFactor;\nDP4   viewPos.x, mvMatrix[0], iPos;\nDP4   viewPos.y, mvMatrix[1], iPos;\nDP4   viewPos.z, mvMatrix[2], iPos;\nDP4   viewPos.w, mvMatrix[3], iPos;\nSUB   fogFactor.x, -viewPos.z, fogParams.x;\nMUL   fogFactor.x, fogFactor.x, 0.001953125;\nMAD   fogFactor.y, iTexCoord.z, fogParams.z, fogParams.w;\nSUB   fogFactor.z, -viewPos.z, fogParams.y;\nMUL   fogFactor.z, fogFactor.z, 0.00390625;\nMIN   fogFactor, fogFactor, 1;\nMAX   fogFactor, fogFactor, 0;\nMUL   fogFactor.z, fogFactor.z, iTexCoord.z;\nMAD   viewPos.xyz, waterPlane.xyzw, fogFactor.zzzz, viewPos.xyzw;\nMAX   oFogCoord.x, fogFactor.x, fogFactor.y;\nMOV   oColour, iColour;\nDP4   oPos.x, pMatrix[0], viewPos;\nDP4   oPos.y, pMatrix[1], viewPos;\nDP4   oPos.z, pMatrix[2], viewPos;\nDP4   oPos.w, pMatrix[3], viewPos;\nDP3   oTexCoord0.x, tMatrix[0], iTexCoord;\nDP3   oTexCoord0.y, tMatrix[1], iTexCoord;\nMOV   oTexCoord0.zw, iTexCoord;\nEND\n");
			this.aClass225_5 = ParticleBinaryTree.method5125(super.aClass19_Sub2_41, "!!ARBvp1.0\nATTRIB  iPos         = vertex.position;\nATTRIB  iNormal      = vertex.normal;\nATTRIB  iColour      = vertex.color;\nATTRIB  iTexCoord    = vertex.texcoord[0];\nOUTPUT  oPos         = result.position;\nOUTPUT  oColour      = result.color;\nOUTPUT  oTexCoord0   = result.texcoord[0];\nOUTPUT  oFogCoord    = result.fogcoord;\nPARAM   fogParams    = program.local[0];\nPARAM   waterPlane   = state.texgen.eye.s;\nPARAM   tMatrix[4]   = { state.matrix.texture[0] };\nPARAM   pMatrix[4]   = { state.matrix.projection };\nPARAM   mvMatrix[4]  = { state.matrix.modelview };\nTEMP    viewPos, viewNormal, fogFactor, colour, ndotl;\nDP4   viewPos.x, mvMatrix[0], iPos;\nDP4   viewPos.y, mvMatrix[1], iPos;\nDP4   viewPos.z, mvMatrix[2], iPos;\nDP4   viewPos.w, mvMatrix[3], iPos;\nSUB   fogFactor.x, -viewPos.z, fogParams.x;\nMUL   fogFactor.x, fogFactor.x, 0.001953125;\nMAD   fogFactor.y, iTexCoord.z, fogParams.z, fogParams.w;\nSUB   fogFactor.z, -viewPos.z, fogParams.y;\nMUL   fogFactor.z, fogFactor.z, 0.00390625;\nMIN   fogFactor, fogFactor, 1;\nMAX   fogFactor, fogFactor, 0;\nMUL   fogFactor.z, fogFactor.z, iTexCoord.z;\nMAD   viewPos.xyz, waterPlane.xyzw, fogFactor.zzzz, viewPos.xyzw;\nMAX   oFogCoord.x, fogFactor.x, fogFactor.y;\nDP3   viewNormal.x, mvMatrix[0], iNormal;\nDP3   viewNormal.y, mvMatrix[1], iNormal;\nDP3   viewNormal.z, mvMatrix[2], iNormal;\nDP3   ndotl.x, viewNormal, state.light[0].position;\nDP3   ndotl.y, viewNormal, state.light[1].position;\nMAX   ndotl, ndotl, 0;\nMOV   colour, state.lightmodel.ambient;\nMAD   colour, state.light[0].diffuse, ndotl.xxxx, colour;\nMAD   colour, state.light[1].diffuse, ndotl.yyyy, colour;\nMUL   oColour, iColour, colour;\nDP4   oPos.x, pMatrix[0], viewPos;\nDP4   oPos.y, pMatrix[1], viewPos;\nDP4   oPos.z, pMatrix[2], viewPos;\nDP4   oPos.w, pMatrix[3], viewPos;\nDP3   oTexCoord0.x, tMatrix[0], iTexCoord;\nDP3   oTexCoord0.y, tMatrix[1], iTexCoord;\nMOV   oTexCoord0.zw, iTexCoord;\nEND\n");
			this.aClass225_3 = ParticleBinaryTree.method5125(super.aClass19_Sub2_41, "!!ARBvp1.0\nATTRIB  iPos         = vertex.position;\nATTRIB  iColour      = vertex.color;\nATTRIB  iTexCoord    = vertex.texcoord[0];\nOUTPUT  oPos         = result.position;\nOUTPUT  oColour      = result.color;\nOUTPUT  oTexCoord0   = result.texcoord[0];\nOUTPUT  oFogCoord    = result.fogcoord;\nPARAM   fogParams    = program.local[0];\nPARAM   waterPlane   = state.texgen.eye.s;\nPARAM   pMatrix[4]   = { state.matrix.projection };\nPARAM   mvMatrix[4]  = { state.matrix.modelview };\nPARAM   texMatrix[4] = { state.matrix.texture[0] };\nTEMP    viewPos, fogFactor, depth;\nDP4   viewPos.x, mvMatrix[0], iPos;\nDP4   viewPos.y, mvMatrix[1], iPos;\nDP4   viewPos.z, mvMatrix[2], iPos;\nDP4   viewPos.w, mvMatrix[3], iPos;\nSUB   fogFactor.x, -viewPos.z, fogParams.x;\nMUL   fogFactor.x, fogFactor.x, 0.001953125;\nDP4   depth, waterPlane, viewPos;\nMAD   fogFactor.y, -depth, fogParams.z, fogParams.w;\nSUB   fogFactor.z, -viewPos.z, fogParams.y;\nMUL   fogFactor.z, fogFactor.z, 0.00390625;\nMIN   fogFactor, fogFactor, 1;\nMAX   fogFactor, fogFactor, 0;\nMUL   fogFactor.z, fogFactor.z, -depth;\nMAD   viewPos.xyz, waterPlane.xyzw, fogFactor.zzzz, viewPos.xyzw;\nMAX   oFogCoord.x, fogFactor.x, fogFactor.y;\nMOV   oColour, iColour;\nDP4   oPos.x, pMatrix[0], viewPos;\nDP4   oPos.y, pMatrix[1], viewPos;\nDP4   oPos.z, pMatrix[2], viewPos;\nDP4   oPos.w, pMatrix[3], viewPos;\nDP4   oTexCoord0.x, texMatrix[0], iTexCoord;\nDP4   oTexCoord0.y, texMatrix[1], iTexCoord;\nDP4   oTexCoord0.z, texMatrix[2], iTexCoord;\nMOV   oTexCoord0.w, 1;\nEND\n");
			this.aClass225_2 = ParticleBinaryTree.method5125(super.aClass19_Sub2_41, "!!ARBvp1.0\nATTRIB  iPos         = vertex.position;\nATTRIB  iNormal      = vertex.normal;\nATTRIB  iColour      = vertex.color;\nATTRIB  iTexCoord    = vertex.texcoord[0];\nOUTPUT  oPos         = result.position;\nOUTPUT  oColour      = result.color;\nOUTPUT  oTexCoord0   = result.texcoord[0];\nOUTPUT  oFogCoord    = result.fogcoord;\nPARAM   fogParams    = program.local[0];\nPARAM   waterPlane   = state.texgen.eye.s;\nPARAM   pMatrix[4]   = { state.matrix.projection };\nPARAM   mvMatrix[4]  = { state.matrix.modelview };\nPARAM   texMatrix[4] = { state.matrix.texture[0] };\nTEMP    viewPos, viewNormal, fogFactor, depth, colour, ndotl;\nDP4   viewPos.x, mvMatrix[0], iPos;\nDP4   viewPos.y, mvMatrix[1], iPos;\nDP4   viewPos.z, mvMatrix[2], iPos;\nDP4   viewPos.w, mvMatrix[3], iPos;\nSUB   fogFactor.x, -viewPos.z, fogParams.x;\nMUL   fogFactor.x, fogFactor.x, 0.001953125;\nDP4   depth, waterPlane, viewPos;\nMAD   fogFactor.y, -depth, fogParams.z, fogParams.w;\nSUB   fogFactor.z, -viewPos.z, fogParams.y;\nMUL   fogFactor.z, fogFactor.z, 0.00390625;\nMIN   fogFactor, fogFactor, 1;\nMAX   fogFactor, fogFactor, 0;\nMUL   fogFactor.z, fogFactor.z, -depth;\nMAD   viewPos.xyz, waterPlane.xyzw, fogFactor.zzzz, viewPos.xyzw;\nMAX   oFogCoord.x, fogFactor.x, fogFactor.y;\nDP3   viewNormal.x, mvMatrix[0], iNormal;\nDP3   viewNormal.y, mvMatrix[1], iNormal;\nDP3   viewNormal.z, mvMatrix[2], iNormal;\nDP3   ndotl.x, viewNormal, state.light[0].position;\nDP3   ndotl.y, viewNormal, state.light[1].position;\nMAX   ndotl, ndotl, 0;\nMOV   colour, state.lightmodel.ambient;\nMAD   colour, state.light[0].diffuse, ndotl.xxxx, colour;\nMAD   colour, state.light[1].diffuse, ndotl.yyyy, colour;\nMUL   oColour, iColour, colour;\nDP4   oPos.x, pMatrix[0], viewPos;\nDP4   oPos.y, pMatrix[1], viewPos;\nDP4   oPos.z, pMatrix[2], viewPos;\nDP4   oPos.w, pMatrix[3], viewPos;\nDP4   oTexCoord0.x, texMatrix[0], iTexCoord;\nDP4   oTexCoord0.y, texMatrix[1], iTexCoord;\nDP4   oTexCoord0.z, texMatrix[2], iTexCoord;\nMOV   oTexCoord0.w, 1;\nEND\n");
			this.aBoolean634 = this.aClass225_4 != null & this.aClass225_5 != null & this.aClass225_3 != null & this.aClass225_2 != null;
		} else {
			this.aBoolean634 = false;
		}
	}

	@OriginalMember(owner = "client!ve", name = "a", descriptor = "(I)V")
	@Override
	public void disable() {
		if (this.aBoolean636) {
			OpenGL.glBindProgramARB(34336, 0);
			OpenGL.glDisable(34820);
			OpenGL.glDisable(34336);
			this.aBoolean636 = false;
		}
	}

	@OriginalMember(owner = "client!ve", name = "a", descriptor = "(ZB)V")
	@Override
	public void applyTextureCombine(@OriginalArg(0) boolean arg0) {
	}

	@OriginalMember(owner = "client!ve", name = "a", descriptor = "(IZ)V")
	@Override
	public void enable(@OriginalArg(1) boolean arg0) {
		this.aBoolean635 = arg0;
	}

	@OriginalMember(owner = "client!ve", name = "b", descriptor = "(I)V")
	public void method5379() {
		if (this.aBoolean636) {
			OpenGL.glProgramLocalParameter4fARB(34336, 0, (float) (super.aClass19_Sub2_41.anInt2312 - 1280), (float) (super.aClass19_Sub2_41.anInt2312 - 768), 1.0F / (float) super.aClass19_Sub2_41.anInt2317, (float) super.aClass19_Sub2_41.anInt2293 / 255.0F);
		}
	}

	@OriginalMember(owner = "client!ve", name = "a", descriptor = "(ILclient!kj;Z)V")
	@Override
	public void method5376(@OriginalArg(0) int arg0, @OriginalArg(1) Class6 arg1) {
	}

	@OriginalMember(owner = "client!ve", name = "b", descriptor = "(B)V")
	public void method5380() {
		DummyInputStream.aFloatArray24[3] = super.aClass19_Sub2_41.anInt2288;
		OpenGL.glTexGenfv(8192, 9474, DummyInputStream.aFloatArray24, 0);
		if (this.aBoolean635) {
			OpenGL.glBindProgramARB(34336, ~super.aClass19_Sub2_41.anInt2288 == Integer.MIN_VALUE ? this.aClass225_5.anInt6405 : this.aClass225_2.anInt6405);
		} else {
			OpenGL.glBindProgramARB(34336, ~super.aClass19_Sub2_41.anInt2288 == Integer.MIN_VALUE ? this.aClass225_4.anInt6405 : this.aClass225_3.anInt6405);
		}
		OpenGL.glEnable(34336);
		this.aBoolean636 = true;
		this.method5379();
	}

	@OriginalMember(owner = "client!ve", name = "a", descriptor = "(III)V")
	@Override
	public void setEffectParams(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
	}

	@OriginalMember(owner = "client!ve", name = "a", descriptor = "(Z)Z")
	@Override
	public boolean isSupported() {
		return this.aBoolean634;
	}

@OriginalMember(owner = "client!ve", name = "n", descriptor = "Lclient!mu;")
	public static TileLighting aClass155_2;
@OriginalMember(owner = "client!ve", name = "p", descriptor = "I")
	public static int anInt6924;
@OriginalMember(owner = "client!ve", name = "y", descriptor = "Lclient!oj;")
	public static SoundBankCache aClass171_1;
@OriginalMember(owner = "client!ve", name = "z", descriptor = "J")
	public static long aLong212;
@OriginalMember(owner = "client!ve", name = "D", descriptor = "I")
	public static int anInt6931;
@OriginalMember(owner = "client!ve", name = "m", descriptor = "[B")
	public static final byte[] aByteArray99 = new byte[] { 2, 0, 0, 2, 0, 0, 0, 4, 4 };
@OriginalMember(owner = "client!ve", name = "o", descriptor = "[C")
	public static final char[] aCharArray7 = new char[] { '€', '\u0000', '‚', 'ƒ', '„', '…', '†', '‡', 'ˆ', '‰', 'Š', '‹', 'Œ', '\u0000', 'Ž', '\u0000', '\u0000', '‘', '’', '“', '”', '•', '–', '—', '˜', '™', 'š', '›', 'œ', '\u0000', 'ž', 'Ÿ' };
@OriginalMember(owner = "client!ve", name = "a", descriptor = "(Lclient!iv;B)Lclient!vq;")
	public static QuickChatPhrase method5378(@OriginalArg(0) Packet arg0) {
		@Pc(15) QuickChatPhrase local15 = new QuickChatPhrase();
		local15.id = arg0.method2536();
		local15.type = Class145_Sub1.aClass81_2.method2258(local15.id);
		return local15;
	}
@OriginalMember(owner = "client!ve", name = "b", descriptor = "(III)V")
	public static void method5382(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		@Pc(7) Class164 local7 = Class70.aClass164ArrayArrayArray5[arg0][arg1][arg2];
		if (local7 == null) {
			return;
		}
		if (local7.aClass16_Sub2_2 != null) {
			local7.aClass16_Sub2_2 = null;
		}
		if (local7.aClass16_Sub2_3 != null) {
			local7.aClass16_Sub2_3 = null;
		}
	}
@OriginalMember(owner = "client!ve", name = "a", descriptor = "(IIII)V")
	public static void method5383(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		if (arg1 == 0) {
			PingWorker.method5476(PcmPlayer.aClass215_30);
		}
		if (arg1 == 1) {
			PingWorker.method5476(Class60.aClass215_20);
		}
		ParticleNode.aClass4_Sub12_Sub1_5.method2551(Static384.aClass244_1.method5489(82) ? 1 : 0);
		ParticleNode.aClass4_Sub12_Sub1_5.method2524(arg0 + Class50_Sub1.anInt3453);
		ParticleNode.aClass4_Sub12_Sub1_5.method2524(arg2 + OpenGLTextureCache.anInt1771);
		GlRenderList.anInt1094 = arg2;
		Class30.anInt771 = arg0;
		Class208.aBoolean556 = false;
		Class53.method1365();
	}
}
