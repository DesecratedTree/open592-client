package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.entity.PathingEntity;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub2;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.RuntimeException_Sub1;
import com.jagex.game.runetek5.graphics.JavaBillboardAttributes;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Class186;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
public final class Static179 {

	@OriginalMember(owner = "client!ja", name = "f", descriptor = "I")
	public static int anInt3427;

	@OriginalMember(owner = "client!ja", name = "c", descriptor = "Lclient!bf;")
	public static final Class21 aClass21_10 = new Class21(4, -1);

	@OriginalMember(owner = "client!ja", name = "e", descriptor = "Ljava/lang/Boolean;")
	public static Boolean aBoolean288 = Boolean.FALSE;

	@OriginalMember(owner = "client!ja", name = "a", descriptor = "(IB[BI)I")
	public static int method2846(@OriginalArg(0) int arg0, @OriginalArg(2) byte[] arg1, @OriginalArg(3) int arg2) {
		@Pc(7) int local7 = -1;
		for (@Pc(9) int local9 = arg0; local9 < arg2; local9++) {
			local7 = GlVboElementsArrayBuffer.anIntArray505[(local7 ^ arg1[local9]) & 0xFF] ^ local7 >>> 8;
		}
		return ~local7;
	}

	@OriginalMember(owner = "client!ja", name = "a", descriptor = "(IIZLjava/lang/String;ZZIIJLjava/lang/String;I)V")
	public static void method2848(@OriginalArg(1) int arg0, @OriginalArg(2) boolean arg1, @OriginalArg(3) String arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) boolean arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) long arg7, @OriginalArg(9) String arg8, @OriginalArg(10) int arg9) {
		@Pc(8) int[] local8 = new int[4];
		for (@Pc(10) int local10 = 0; local10 < 3; local10++) {
			local8[local10] = (int) (Math.random() * 9.9999999E7D);
		}
		@Pc(30) Packet local30 = new Packet(128);
		local30.method2551(10);
		local30.method2524((arg3 ? 4 : 0) | (arg1 ? 2 : 0) | (arg4 ? 1 : 0));
		local30.method2504(arg7);
		local30.method2531(local8[0]);
		local30.method2518(arg2);
		local30.method2531(local8[1]);
		local30.method2524(GlCubeTexture.anInt3259);
		local30.method2551(arg9);
		local30.method2551(arg0);
		local30.method2531(local8[2]);
		local30.method2524(arg5);
		local30.method2524(arg6);
		local30.method2531(local8[3]);
		local30.method2522(TextureOpColorGradient.aBigInteger1, Class186.aBigInteger2);
		@Pc(120) Packet local120 = new Packet(350);
		local120.method2518(arg8);
		@Pc(134) int local134 = 8 - JavaBillboardAttributes.method3856(arg8) % 8;
		for (@Pc(136) int local136 = 0; local136 < local134; local136++) {
			local120.method2551((int) (Math.random() * 255.0D));
		}
		local120.method2540(local8);
		ParticleNode.aClass4_Sub12_Sub1_5.currentPosition = 0;
		ParticleNode.aClass4_Sub12_Sub1_5.method2551(Class4_Sub1_Sub11.aClass60_8.anInt1812);
		ParticleNode.aClass4_Sub12_Sub1_5.method2524(local30.currentPosition + local120.currentPosition + 2);
		ParticleNode.aClass4_Sub12_Sub1_5.method2524(592);
		ParticleNode.aClass4_Sub12_Sub1_5.method2519(local30.buffer, local30.currentPosition);
		ParticleNode.aClass4_Sub12_Sub1_5.method2519(local120.buffer, local120.currentPosition);
		Class4_Sub6_Sub31.anInt5976 = 0;
		ItemType.anInt6183 = 1;
		Class16_Sub4_Sub2.anInt2524 = 0;
		RuntimeException_Sub1.anInt4623 = -3;
	}

	@OriginalMember(owner = "client!ja", name = "a", descriptor = "(Lclient!r;I)V")
	public static void method2849(@OriginalArg(0) PathingEntity arg0) {
		@Pc(5) boolean local5 = false;
		if (arg0.anInt6023 == Shadow.anInt5323 || arg0.anInt6021 == -1 || arg0.anInt6046 != 0) {
			local5 = true;
		} else {
			@Pc(26) SeqType local26 = SceneLocEntity.aClass55_1.method1397(arg0.anInt6021);
			if (local26.tweened || local26.frames[arg0.anInt6060] < arg0.anInt6073 + 1) {
				local5 = true;
			}
		}
		if (local5) {
			@Pc(54) int local54 = arg0.anInt6023 - arg0.anInt6018;
			@Pc(60) int local60 = Shadow.anInt5323 - arg0.anInt6018;
			@Pc(71) int local71 = arg0.anInt6043 * 128 + arg0.getSize() * 64;
			@Pc(83) int local83 = arg0.anInt6052 * 128 + arg0.getSize() * 64;
			@Pc(95) int local95 = arg0.anInt6044 * 128 + arg0.getSize() * 64;
			@Pc(107) int local107 = arg0.anInt6074 * 128 + arg0.getSize() * 64;
			arg0.anInt6893 = (local60 * local95 + (local54 - local60) * local71) / local54;
			arg0.anInt6892 = ((local54 - local60) * local83 + local107 * local60) / local54;
		}
		arg0.anInt6085 = 0;
		if (arg0.lb == 0) {
			arg0.method4745(8192);
		}
		if (arg0.lb == 1) {
			arg0.method4745(12288);
		}
		if (arg0.lb == 2) {
			arg0.method4745(0);
		}
		if (arg0.lb == 3) {
			arg0.method4745(4096);
		}
	}
}
