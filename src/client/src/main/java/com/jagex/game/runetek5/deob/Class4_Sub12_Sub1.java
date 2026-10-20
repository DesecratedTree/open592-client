package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.tools.Isaac;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class204;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.cache.compression.GzipDecompressor;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.entity.ParticleEmitter;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub3;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.deob.Class136;
import com.jagex.game.runetek5.cache.node.lru.ProjectileAnimationNode;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.deob.Class15;
import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.graphics.model.Model;
@OriginalClass("client!cw")
public final class Class4_Sub12_Sub1 extends Packet {

	@OriginalMember(owner = "client!cw", name = "Cb", descriptor = "Lclient!dp;")
	private Isaac aClass52_1;

	@OriginalMember(owner = "client!cw", name = "Nb", descriptor = "I")
	private int anInt1270;

	@OriginalMember(owner = "client!cw", name = "<init>", descriptor = "(I)V")
	public Class4_Sub12_Sub1(@OriginalArg(0) int arg0) {
		super(arg0);
	}

	@OriginalMember(owner = "client!cw", name = "a", descriptor = "(III[B)V")
	public void method1132(@OriginalArg(1) int arg0, @OriginalArg(3) byte[] arg1) {
		for (@Pc(11) int local11 = 0; local11 < arg0; local11++) {
			arg1[local11] = (byte) (super.buffer[super.currentPosition++] - this.aClass52_1.method1362());
		}
	}

	@OriginalMember(owner = "client!cw", name = "m", descriptor = "(II)V")
	public void method1133(@OriginalArg(0) int arg0) {
		super.buffer[super.currentPosition++] = (byte) (arg0 + this.aClass52_1.method1362());
	}

	@OriginalMember(owner = "client!cw", name = "n", descriptor = "(B)V")
	public void method1135() {
		super.currentPosition = (this.anInt1270 + 7) / 8;
	}

	@OriginalMember(owner = "client!cw", name = "u", descriptor = "(I)V")
	public void method1136() {
		this.anInt1270 = super.currentPosition * 8;
	}

	@OriginalMember(owner = "client!cw", name = "n", descriptor = "(II)I")
	public int method1137(@OriginalArg(1) int arg0) {
		return arg0 * 8 - this.anInt1270;
	}

	@OriginalMember(owner = "client!cw", name = "b", descriptor = "([II)V")
	public void method1139(@OriginalArg(0) int[] arg0) {
		this.aClass52_1 = new Isaac(arg0);
	}

	@OriginalMember(owner = "client!cw", name = "v", descriptor = "(I)I")
	public int method1140() {
		@Pc(21) int local21 = super.buffer[super.currentPosition++] - this.aClass52_1.method1362() & 0xFF;
		return local21 < 128 ? local21 : (super.buffer[super.currentPosition++] - this.aClass52_1.method1362() & 0xFF) + (local21 - 128 << 8);
	}

	@OriginalMember(owner = "client!cw", name = "w", descriptor = "(I)Z")
	public boolean method1141() {
		@Pc(23) int local23 = super.buffer[super.currentPosition] - this.aClass52_1.method1364() & 0xFF;
		return local23 >= 128;
	}

	@OriginalMember(owner = "client!cw", name = "o", descriptor = "(II)I")
	public int method1143(@OriginalArg(1) int arg0) {
		@Pc(10) int local10 = this.anInt1270 >> 3;
		@Pc(18) int local18 = 8 - (this.anInt1270 & 0x7);
		@Pc(20) int local20 = 0;
		this.anInt1270 += arg0;
		while (local18 < arg0) {
			local20 += (super.buffer[local10++] & Class15.anIntArray14[local18]) << arg0 - local18;
			arg0 -= local18;
			local18 = 8;
		}
		if (arg0 == local18) {
			local20 += super.buffer[local10] & Class15.anIntArray14[local18];
		} else {
			local20 += super.buffer[local10] >> local18 - arg0 & Class15.anIntArray14[arg0];
		}
		return local20;
	}

@OriginalMember(owner = "client!cw", name = "Lb", descriptor = "Lclient!kk;")
	public static Class36 aClass36_1;
@OriginalMember(owner = "client!cw", name = "Eb", descriptor = "I")
	public static int anInt1262 = 0;
@OriginalMember(owner = "client!cw", name = "t", descriptor = "(I)V")
	public static void method1134() {
		Class4_Sub6_Sub25.method3809(TextureOpBrightness.aClass50_Sub1_1.anInt3434);
		@Pc(17) int local17 = (Class50_Sub1.anInt3453 >> 3) + (Class4_Sub23.anInt3197 >> 10);
		@Pc(25) int local25 = (OpenGLTextureCache.anInt1771 >> 3) + (Static145.anInt2906 >> 10);
		Class4_Sub6_Sub25.anInt4963 = Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82 = 0;
		Static1.aClass16_Sub1_Sub5_Sub1_1.move(8, 8);
		ReflectionCheck.aByteArrayArray1 = new byte[18][];
		Class136.anIntArray291 = new int[18];
		Class204.anIntArray418 = new int[18];
		Class16_Sub4_Sub3.aByteArrayArray13 = new byte[18][];
		Class259.aByteArrayArray30 = new byte[18][];
		Class99_Sub1.aByteArrayArray15 = new byte[18][];
		Model.anIntArrayArray50 = new int[18][4];
		PlayerVarDomain.anIntArray222 = new int[18];
		ProjectileAnimationNode.anIntArray155 = new int[18];
		Static308.anIntArray372 = new int[18];
		Game.anIntArray236 = new int[18];
		GlFramebuffer.aByteArrayArray16 = new byte[18][];
		@Pc(78) int local78 = 0;
		@Pc(98) int local98;
		for (@Pc(87) int local87 = (local17 - (GLBillowTextureData.anInt5666 >> 4)) / 8; local87 <= (local17 + (GLBillowTextureData.anInt5666 >> 4)) / 8; local87++) {
			for (local98 = (local25 - (Static283.anInt5187 >> 4)) / 8; local98 <= ((Static283.anInt5187 >> 4) + local25) / 8; local98++) {
				@Pc(106) int local106 = local98 + (local87 << 8);
				Static308.anIntArray372[local78] = local106;
				Class136.anIntArray291[local78] = Class4_Sub10.aClass76_27.method2099("m" + local87 + "_" + local98);
				Class204.anIntArray418[local78] = Class4_Sub10.aClass76_27.method2099("l" + local87 + "_" + local98);
				PlayerVarDomain.anIntArray222[local78] = Class4_Sub10.aClass76_27.method2099("n" + local87 + "_" + local98);
				ProjectileAnimationNode.anIntArray155[local78] = Class4_Sub10.aClass76_27.method2099("um" + local87 + "_" + local98);
				Game.anIntArray236[local78] = Class4_Sub10.aClass76_27.method2099("ul" + local87 + "_" + local98);
				if (PlayerVarDomain.anIntArray222[local78] == -1) {
					Class136.anIntArray291[local78] = -1;
					Class204.anIntArray418[local78] = -1;
					ProjectileAnimationNode.anIntArray155[local78] = -1;
					Game.anIntArray236[local78] = -1;
				}
				local78++;
			}
		}
		for (local98 = local78; local98 < PlayerVarDomain.anIntArray222.length; local98++) {
			PlayerVarDomain.anIntArray222[local98] = -1;
			Class136.anIntArray291[local98] = -1;
			Class204.anIntArray418[local98] = -1;
			ProjectileAnimationNode.anIntArray155[local98] = -1;
			Game.anIntArray236[local98] = -1;
		}
		GzipDecompressor.method4167(true, local25, local17, false);
	}
@OriginalMember(owner = "client!cw", name = "a", descriptor = "(Lclient!uu;B)V")
	public static void method1142(@OriginalArg(0) com.jagex.game.runetek5.config.rsinterface.Component arg0) {
		if (arg0.anInt6798 == ParticleEmitter.anInt4639) {
			PingRequest.aBooleanArray21[arg0.anInt6852] = true;
		}
	}
}
