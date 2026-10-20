package com.jagex.game.runetek5.vardomain;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.quickchat.QuickchatMessageType;
import com.jagex.game.runetek5.deob.Class111;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.deob.Class4_Sub29;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import com.jagex.game.runetek5.deob.Class4_Sub25;
import com.jagex.game.runetek5.deob.QuickChatValueType;
import com.jagex.game.runetek5.deob.Static231;
import com.jagex.game.runetek5.deob.Static57;
import com.jagex.game.runetek5.scene.CollisionMap;
import com.jagex.game.runetek5.vardomain.*;
import com.jagex.game.runetek5.toolkit.opengl.GlParticleRenderer;
import com.jagex.game.runetek5.js5.request.Js5ResourceRequest;
import com.jagex.game.runetek5.deob.Class168;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.deob.Class95_Sub3_Sub1;
import com.jagex.game.runetek5.client.input.keyboard.KeyRecordEntry;
@OriginalClass("client!it")
public final class PlayerVarDomain implements VarDomain {

	@OriginalMember(owner = "client!it", name = "i", descriptor = "Lclient!ib;")
	private CubeMapSpecularEffect aClass102_16 = new CubeMapSpecularEffect(128);

	@OriginalMember(owner = "client!it", name = "h", descriptor = "[I")
	private final int[] pendingVars = new int[Js5ResourceRequest.aClass223_1.anInt6400];

	@OriginalMember(owner = "client!it", name = "f", descriptor = "[I")
	public final int[] activeVars = new int[Js5ResourceRequest.aClass223_1.anInt6400];

	@OriginalMember(owner = "client!it", name = "a", descriptor = "(III)V")
	public void method2828(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		@Pc(10) Class126 local10 = KeyRecordEntry.aClass26_1.method647(arg1);
		@Pc(13) int local13 = local10.anInt3914;
		@Pc(16) int local16 = local10.anInt3919;
		@Pc(19) int local19 = local10.anInt3915;
		@Pc(26) int local26 = QuickchatMessageType.anIntArray17[local19 - local16];
		if (arg0 < 0 || local26 < arg0) {
			arg0 = 0;
		}
		local26 <<= local16;
		this.updateVarp(local13, local26 & arg0 << local16 | ~local26 & this.pendingVars[local13]);
	}

	@OriginalMember(owner = "client!it", name = "a", descriptor = "(I)V")
	public void reset() {
		for (@Pc(7) int local7 = 0; local7 < Js5ResourceRequest.aClass223_1.anInt6400; local7++) {
			@Pc(14) Class111 local14 = Js5ResourceRequest.aClass223_1.method5004(local7);
			if (local14 != null && local14.anInt3365 == 0) {
				this.pendingVars[local7] = 0;
				this.activeVars[local7] = 0;
			}
		}
		this.aClass102_16 = new CubeMapSpecularEffect(128);
	}

	@OriginalMember(owner = "client!it", name = "b", descriptor = "(III)V")
	public void updateVarp(@OriginalArg(1) int arg0, @OriginalArg(2) int value) {
		this.pendingVars[arg0] = value;
		@Pc(24) Class4_Sub29 local24 = (Class4_Sub29) this.aClass102_16.method2700((long) arg0);
		if (local24 == null) {
			local24 = new Class4_Sub29(4611686018427387905L);
			this.aClass102_16.method2703((long) arg0, local24);
		} else if (local24.aLong153 != 4611686018427387905L) {
			local24.aLong153 = Class168.method3962() + 500L | 0x4000000000000000L;
			return;
		}
	}

	@OriginalMember(owner = "client!it", name = "c", descriptor = "(III)V")
	public void setVarValueInt(@OriginalArg(1) int arg0, @OriginalArg(2) int id) {
		this.activeVars[id] = arg0;
		@Pc(23) Class4_Sub29 local23 = (Class4_Sub29) this.aClass102_16.method2700((long) id);
		if (local23 == null) {
			local23 = new Class4_Sub29(Class168.method3962() + 500L);
			this.aClass102_16.method2703((long) id, local23);
		} else {
			local23.aLong153 = Class168.method3962() + 500L;
		}
	}

	@OriginalMember(owner = "client!it", name = "a", descriptor = "(BI)I")
	@Override
	public int getVarBitValue(@OriginalArg(1) int arg0) {
		@Pc(15) Class126 local15 = KeyRecordEntry.aClass26_1.method647(arg0);
		@Pc(18) int local18 = local15.anInt3914;
		@Pc(21) int local21 = local15.anInt3919;
		@Pc(24) int local24 = local15.anInt3915;
		@Pc(30) int local30 = QuickchatMessageType.anIntArray17[local24 - local21];
		return this.activeVars[local18] >> local21 & local30;
	}

	@OriginalMember(owner = "client!it", name = "a", descriptor = "(ZI)I")
	public int removeNext(@OriginalArg(0) boolean arg0) {
		@Pc(8) long local8 = Class168.method3962();
		for (@Pc(22) Class4_Sub29 local22 = arg0 ? (Class4_Sub29) this.aClass102_16.method2705() : (Class4_Sub29) this.aClass102_16.method2704(); local22 != null; local22 = (Class4_Sub29) this.aClass102_16.method2704()) {
			if ((local22.aLong153 & 0x3FFFFFFFFFFFFFFFL) < local8) {
				if ((local22.aLong153 & 0x4000000000000000L) != 0L) {
					@Pc(54) int local54 = (int) local22.aLong224;
					this.activeVars[local54] = this.pendingVars[local54];
					local22.method5684();
					return local54;
				}
				local22.method5684();
			}
		}
		return -1;
	}

	@OriginalMember(owner = "client!it", name = "a", descriptor = "(IBI)V")
	public void method2835(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		@Pc(16) Class126 local16 = KeyRecordEntry.aClass26_1.method647(arg0);
		@Pc(19) int local19 = local16.anInt3914;
		@Pc(22) int local22 = local16.anInt3919;
		@Pc(25) int local25 = local16.anInt3915;
		@Pc(32) int local32 = QuickchatMessageType.anIntArray17[local25 - local22];
		if (arg1 < 0 || arg1 > local32) {
			arg1 = 0;
		}
		local32 <<= local22;
		this.setVarValueInt(local32 & arg1 << local22 | ~local32 & this.activeVars[local19], local19);
	}

	@OriginalMember(owner = "client!it", name = "a", descriptor = "(II)I")
	@Override
	public int getVarValueInt(@OriginalArg(0) int id) {
		return this.activeVars[id];
	}

@OriginalMember(owner = "client!it", name = "l", descriptor = "I")
	public static int anInt3393;
@OriginalMember(owner = "client!it", name = "q", descriptor = "[I")
	public static int[] anIntArray222;
@OriginalMember(owner = "client!it", name = "a", descriptor = "[Lclient!sd;")
	public static final CollisionMap[] aClass213Array1 = new CollisionMap[4];
@OriginalMember(owner = "client!it", name = "g", descriptor = "Lclient!ho;")
	public static final QuickChatValueType aClass98_7 = new QuickChatValueType(4, 1, 1, 1);
@OriginalMember(owner = "client!it", name = "r", descriptor = "I")
	public static int anInt3398 = 0;
@OriginalMember(owner = "client!it", name = "s", descriptor = "I")
	public static int anInt3399 = 0;
@OriginalMember(owner = "client!it", name = "b", descriptor = "(I)V")
	public static void method2833() {
		Static57.aClass19_7.setBloomParams(Static231.aFloat138, GlParticleRenderer.aFloat31, Class95_Sub3_Sub1.aFloat194);
	}
@OriginalMember(owner = "client!it", name = "a", descriptor = "(IZI)Lclient!jh;")
	public static Class4_Sub25 method2834(@OriginalArg(1) boolean arg0, @OriginalArg(2) int arg1) {
		@Pc(17) long local17 = (long) (arg1 | (arg0 ? Integer.MIN_VALUE : 0));
		return (Class4_Sub25) Class36.aClass102_11.method2700(local17);
	}
}
