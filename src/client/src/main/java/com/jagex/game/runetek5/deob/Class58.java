package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;

import com.jagex.game.runetek5.graphics.sprite.NativeSprite;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
@OriginalClass("client!eb")
public final class Class58 {

	@OriginalMember(owner = "client!eb", name = "a", descriptor = "I")
	public int anInt1719;

	@OriginalMember(owner = "client!eb", name = "e", descriptor = "I")
	public int anInt1723;

	@OriginalMember(owner = "client!eb", name = "i", descriptor = "I")
	public int anInt1726;

	@OriginalMember(owner = "client!eb", name = "l", descriptor = "I")
	public int anInt1728;

	@OriginalMember(owner = "client!eb", name = "n", descriptor = "I")
	public int anInt1729;

	@OriginalMember(owner = "client!eb", name = "q", descriptor = "Z")
	public boolean aBoolean160;

	@OriginalMember(owner = "client!eb", name = "o", descriptor = "I")
	public int anInt1730 = 8;

	@OriginalMember(owner = "client!eb", name = "p", descriptor = "I")
	public int anInt1731 = 16777215;

	@OriginalMember(owner = "client!eb", name = "a", descriptor = "(ILclient!iv;B)V")
	private void method1508(@OriginalArg(0) int arg0, @OriginalArg(1) Packet arg1) {
		if (arg0 == 1) {
			this.anInt1730 = arg1.method2536();
		} else if (arg0 == 2) {
			this.aBoolean160 = true;
		} else if (arg0 == 3) {
			this.anInt1729 = arg1.method2512();
			this.anInt1726 = arg1.method2512();
			this.anInt1728 = arg1.method2512();
		} else if (arg0 == 4) {
			this.anInt1719 = arg1.method2490();
		} else if (arg0 == 5) {
			this.anInt1723 = arg1.method2536();
		} else if (arg0 == 6) {
			this.anInt1731 = arg1.method2493();
		}
	}

	@OriginalMember(owner = "client!eb", name = "a", descriptor = "(Lclient!iv;B)V")
	public void method1509(@OriginalArg(0) Packet arg0) {
		while (true) {
			@Pc(5) int local5 = arg0.method2490();
			if (local5 == 0) {
				return;
			}
			this.method1508(local5, arg0);
		}
	}

@OriginalMember(owner = "client!eb", name = "j", descriptor = "Lclient!l;")
	public static NativeSprite aClass57_10;
@OriginalMember(owner = "client!eb", name = "g", descriptor = "J")
	public static volatile long aLong59 = 0L;
@OriginalMember(owner = "client!eb", name = "m", descriptor = "Z")
	public static boolean aBoolean159 = false;
@OriginalMember(owner = "client!eb", name = "a", descriptor = "(BII)Z")
	public static boolean method1510(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		return (arg0 & 0x34) != 0;
	}
@OriginalMember(owner = "client!eb", name = "a", descriptor = "(Lclient!uu;ZLclient!uu;)V")
	public static void method1512(@OriginalArg(0) com.jagex.game.runetek5.config.rsinterface.Component arg0, @OriginalArg(2) com.jagex.game.runetek5.config.rsinterface.Component arg1) {
		PingWorker.method5476(MECType.aClass215_59);
		ParticleNode.aClass4_Sub12_Sub1_5.method2531(arg0.anInt6809);
		ParticleNode.aClass4_Sub12_Sub1_5.method2496(arg0.anInt6865);
		ParticleNode.aClass4_Sub12_Sub1_5.method2532(arg1.anInt6809);
		ParticleNode.aClass4_Sub12_Sub1_5.method2496(arg1.anInt6865);
		ParticleNode.aClass4_Sub12_Sub1_5.method2494(arg1.anInt6779);
		ParticleNode.aClass4_Sub12_Sub1_5.method2494(arg0.anInt6779);
	}
@OriginalMember(owner = "client!eb", name = "a", descriptor = "(BILclient!pe;)Lclient!ag;")
	public static Class7 method1513(@OriginalArg(1) int arg0, @OriginalArg(2) Class16_Sub1 arg1) {
		@Pc(7) Class7 local7;
		if (Class4_Sub7.aClass7_2 == null) {
			local7 = new Class7();
		} else {
			local7 = Class4_Sub7.aClass7_2;
			Class4_Sub7.aClass7_2 = Class4_Sub7.aClass7_2.aClass7_1;
			local7.aClass7_1 = null;
			Class4_Sub1_Sub11.anInt3086--;
		}
		local7.aClass16_Sub1_1 = arg1;
		local7.anInt118 = arg0;
		return local7;
	}
}
