package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTile;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.Class4_Sub21_Sub2;
import com.jagex.game.runetek5.FixedFunctionSpecularPass;
@OriginalClass("client!gu")
public final class Class27_Sub2 extends TextureCombinerOp {

	@OriginalMember(owner = "client!gu", name = "k", descriptor = "I")
	private final int anInt2799;

	@OriginalMember(owner = "client!gu", name = "j", descriptor = "I")
	private final int anInt2798;

	@OriginalMember(owner = "client!gu", name = "o", descriptor = "I")
	private final int anInt2803;

	@OriginalMember(owner = "client!gu", name = "l", descriptor = "I")
	private final int anInt2800;

	@OriginalMember(owner = "client!gu", name = "<init>", descriptor = "(IIIIII)V")
	public Class27_Sub2(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5) {
		super(-1, arg4, arg5);
		this.anInt2799 = arg1;
		this.anInt2798 = arg2;
		this.anInt2803 = arg3;
		this.anInt2800 = arg0;
	}

	@OriginalMember(owner = "client!gu", name = "c", descriptor = "(III)V")
	@Override
	public void method3939(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
	}

	@OriginalMember(owner = "client!gu", name = "b", descriptor = "(III)V")
	@Override
	public void method3936(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
	}

	@OriginalMember(owner = "client!gu", name = "a", descriptor = "(III)V")
	@Override
	public void method3935(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		@Pc(10) int local10 = this.anInt2800 * arg1 >> 12;
		@Pc(17) int local17 = this.anInt2798 * arg1 >> 12;
		@Pc(29) int local29 = arg0 * this.anInt2799 >> 12;
		@Pc(36) int local36 = this.anInt2803 * arg0 >> 12;
		Class4_Sub21_Sub2.method3167(super.anInt5152, local36, local29, local17, local10);
	}

@OriginalMember(owner = "client!gu", name = "i", descriptor = "I")
	public static int anInt2797 = -1;
@OriginalMember(owner = "client!gu", name = "r", descriptor = "[[[I")
	public static final int[][][] anIntArrayArrayArray3 = new int[2][][];
@OriginalMember(owner = "client!gu", name = "d", descriptor = "(III)I")
	public static int method2355(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		return arg1 == 1 || arg1 == 3 ? TextureOpTile.anIntArray484[arg0 & 0x3] : Class196.anIntArray398[arg0 & 0x3];
	}
@OriginalMember(owner = "client!gu", name = "a", descriptor = "(I)V")
	public static void method2357() {
		for (@Pc(10) Class3_Sub4 local10 = (Class3_Sub4) LRUCache.aClass84_5.method2306(); local10 != null; local10 = (Class3_Sub4) LRUCache.aClass84_5.method2306()) {
			JavaMatrix.method5157(local10);
		}
		@Pc(29) int local29;
		@Pc(31) int local31;
		if (TextureOpBrightness.aClass50_Sub1_1.method2861(Packet.anInt2973)) {
			local29 = 0;
			local31 = 3;
		} else {
			local31 = FixedFunctionSpecularPass.anInt5523;
			local29 = FixedFunctionSpecularPass.anInt5523;
		}
		client.calculateEntityPriorities();
		for (@Pc(40) int local40 = local29; local40 <= local31; local40++) {
			client.resetDrawPriorities();
			client.updateDrawPriority(local40);
			client.positionEntitiesOnPlane(local40);
		}
		client.method907();
		client.method904();
	}
}
