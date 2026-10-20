package com.jagex.game.runetek5.config.particle;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.Class50_Sub1;

import com.jagex.game.runetek5.cache.node.HashTable;
import com.jagex.game.runetek5.deob.Static166;
import com.jagex.game.runetek5.deob.Static384;
import com.jagex.game.runetek5.entity.ParticleSystem;

import com.jagex.game.runetek5.config.particle.*;
import com.jagex.game.runetek5.deob.Class4_Sub29;
@OriginalClass("client!ah")
public final class ParticleType {

	@OriginalMember(owner = "client!ah", name = "a", descriptor = "I")
	public int anInt119;

	@OriginalMember(owner = "client!ah", name = "c", descriptor = "J")
	public long uid;

	@OriginalMember(owner = "client!ah", name = "d", descriptor = "I")
	public int particleHandlingType;

	@OriginalMember(owner = "client!ah", name = "e", descriptor = "I")
	private int anInt122;

	@OriginalMember(owner = "client!ah", name = "g", descriptor = "I")
	public int anInt124;

	@OriginalMember(owner = "client!ah", name = "h", descriptor = "I")
	public int anInt125;

	@OriginalMember(owner = "client!ah", name = "l", descriptor = "I")
	public int anInt128;

	@OriginalMember(owner = "client!ah", name = "n", descriptor = "I")
	public int partileFileId;

	@OriginalMember(owner = "client!ah", name = "o", descriptor = "I")
	public int anInt131;

	@OriginalMember(owner = "client!ah", name = "p", descriptor = "I")
	public int anInt132;

	@OriginalMember(owner = "client!ah", name = "q", descriptor = "I")
	public int anInt133;

	@OriginalMember(owner = "client!ah", name = "f", descriptor = "I")
	public int verticeCalculationType = 0;

	@OriginalMember(owner = "client!ah", name = "j", descriptor = "I")
	public int currentOffset = 0;

	@OriginalMember(owner = "client!ah", name = "k", descriptor = "Z")
	private boolean invert = false;

	@OriginalMember(owner = "client!ah", name = "a", descriptor = "(BLclient!iv;)V")
	public void method89(@OriginalArg(1) Packet arg0) {
		while (true) {
			@Pc(5) int local5 = arg0.method2490();
			if (local5 == 0) {
				return;
			}
			this.method93(local5, arg0);
		}
	}

	@OriginalMember(owner = "client!ah", name = "a", descriptor = "(Z)V")
	public void method91() {
		this.anInt124 = Class50_Sub1.anIntArray224[this.anInt122 << 3];
		this.anInt119 = (int) Math.sqrt((double) (this.anInt128 * this.anInt128 + this.anInt125 * this.anInt125 + this.anInt132 * this.anInt132));
		if (this.anInt131 == 0) {
			this.anInt131 = 1;
		}
		if (this.anInt133 == 0) {
			this.uid = 2147483647L;
		} else if (this.anInt133 == 1) {
			this.uid = this.anInt119 * 8 / this.anInt131;
			this.uid *= this.uid;
		} else if (this.anInt133 == 2) {
			this.uid = this.anInt119 * 8 / this.anInt131;
		}
		if (this.invert) {
			this.anInt119 *= -1;
		}
	}

	@OriginalMember(owner = "client!ah", name = "a", descriptor = "(ILclient!iv;Z)V")
	private void method93(@OriginalArg(0) int arg0, @OriginalArg(1) Packet arg1) {
		if (arg0 == 1) {
			this.anInt122 = arg1.method2536();
		} else if (arg0 == 2) {
			arg1.method2490();
		} else if (arg0 == 3) {
			this.anInt128 = arg1.method2529();
			this.anInt125 = arg1.method2529();
			this.anInt132 = arg1.method2529();
		} else if (arg0 == 4) {
			this.anInt133 = arg1.method2490();
			this.anInt131 = arg1.method2529();
		} else if (arg0 == 6) {
			this.particleHandlingType = arg1.method2490();
		} else if (arg0 == 8) {
			this.currentOffset = 1;
		} else if (arg0 == 9) {
			this.verticeCalculationType = 1;
		} else if (arg0 == 10) {
			this.invert = true;
		}
	}

@OriginalMember(owner = "client!ah", name = "t", descriptor = "Z")
	public static boolean aBoolean10 = false;
@OriginalMember(owner = "client!ah", name = "a", descriptor = "(I)V")
	public static void method90() {
		Static166.aClass100_1 = new HashTable(8);
		Class4_Sub29.anInt4588 = 0;
		for (@Pc(17) ParticleSystem local17 = (ParticleSystem) Static384.aClass84_8.method2312(); local17 != null; local17 = (ParticleSystem) Static384.aClass84_8.method2309()) {
			local17.remove();
		}
	}
@OriginalMember(owner = "client!ah", name = "a", descriptor = "(BI)I")
	public static int method92(@OriginalArg(1) int arg0) {
		if (arg0 == 6406) {
			return 1;
		} else if (arg0 == 6409) {
			return 1;
		} else if (arg0 == 32841) {
			return 1;
		} else if (arg0 == 6410) {
			return 2;
		} else if (arg0 == 6407) {
			return 3;
		} else if (arg0 == 6408) {
			return 4;
		} else {
			throw new IllegalArgumentException("");
		}
	}
}
