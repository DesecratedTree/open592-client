package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.entity.PathingEntity;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;

import com.jagex.game.runetek5.*;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
@OriginalClass("client!sp")
public final class ModeWhere {

	@OriginalMember(owner = "client!sp", name = "a", descriptor = "I")
	public final int id;

	@OriginalMember(owner = "client!sp", name = "<init>", descriptor = "(Ljava/lang/String;I)V")
	public ModeWhere(@OriginalArg(0) String string, @OriginalArg(1) int id) {
		this.id = id;
	}

	@OriginalMember(owner = "client!sp", name = "toString", descriptor = "()Ljava/lang/String;")
	@Override
	public String toString() {
		throw new IllegalStateException();
	}

@OriginalMember(owner = "client!sp", name = "d", descriptor = "Lclient!l;")
	public static NativeSprite aClass57_21;
@OriginalMember(owner = "client!sp", name = "c", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_287 = new Class22(66, 0);
@OriginalMember(owner = "client!sp", name = "h", descriptor = "[[I")
	public static final int[][] anIntArrayArray58 = new int[][] { { 0, 2 }, { 0, 2 }, { 0, 0, 2 }, { 2, 0, 0 }, { 0, 2, 0 }, { 0, 0, 2 }, { 0, 5, 1, 4 }, { 0, 4, 4, 4 }, { 4, 4, 4, 0 }, { 6, 6, 6, 2, 2, 2 }, { 2, 2, 2, 6, 6, 6 }, { 0, 11, 6, 6, 6, 4 }, { 0, 2 }, { 0, 4, 4, 4 }, { 0, 4, 4, 4 } };
@OriginalMember(owner = "client!sp", name = "a", descriptor = "(IIIIIILclient!r;)V")
	public static void method4939(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(5) int arg2, @OriginalArg(6) PathingEntity arg3) {
		EnvironmentSampler.method3226(arg3.anInt6893, arg3.anInt6892, 0, arg0, arg2, arg3.aByte82, arg1);
	}
@OriginalMember(owner = "client!sp", name = "a", descriptor = "(I)V")
	public static void method4940() {
		for (@Pc(11) int local11 = 0; local11 < 100; local11++) {
			PingRequest.aBooleanArray21[local11] = true;
		}
	}
@OriginalMember(owner = "client!sp", name = "a", descriptor = "(BI)V")
	public static void method4942(@OriginalArg(1) int arg0) {
		@Pc(13) Class4_Sub1_Sub11 local13 = NPCTypeList.method5222(arg0, 8);
		local13.method2608();
	}
}
