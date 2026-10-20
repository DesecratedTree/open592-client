package com.jagex.game.runetek5.cache.node;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.lru.CacheableNode;

import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.Class83;

import com.jagex.game.runetek5.cache.node.*;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpDiagonalWaves;
import com.jagex.game.runetek5.JavaMatrix;
@OriginalClass("client!hu")
public final class HashTable {

	@OriginalMember(owner = "client!hu", name = "b", descriptor = "J")
	private long searchKey;

	@OriginalMember(owner = "client!hu", name = "c", descriptor = "Lclient!ac;")
	private CacheableNode searchPointer;

	@OriginalMember(owner = "client!hu", name = "g", descriptor = "[Lclient!ac;")
	private final CacheableNode[] buckets;

	@OriginalMember(owner = "client!hu", name = "i", descriptor = "I")
	private final int bucketCount;

	@OriginalMember(owner = "client!hu", name = "<init>", descriptor = "(I)V")
	public HashTable(@OriginalArg(0) int arg0) {
		this.buckets = new CacheableNode[arg0];
		this.bucketCount = arg0;
		for (@Pc(10) int local10 = 0; local10 < arg0; local10++) {
			@Pc(20) CacheableNode local20 = this.buckets[local10] = new CacheableNode();
			local20.aClass4_Sub1_55 = local20;
			local20.aClass4_Sub1_56 = local20;
		}
	}

	@OriginalMember(owner = "client!hu", name = "a", descriptor = "(Z)Lclient!ac;")
	public CacheableNode method2670() {
		if (this.searchPointer == null) {
			return null;
		}
		@Pc(23) CacheableNode local23 = this.buckets[(int) ((long) (this.bucketCount - 1) & this.searchKey)];
		while (this.searchPointer != local23) {
			if (this.searchPointer.indexReference == this.searchKey) {
				@Pc(35) CacheableNode local35 = this.searchPointer;
				this.searchPointer = this.searchPointer.aClass4_Sub1_55;
				return local35;
			}
			this.searchPointer = this.searchPointer.aClass4_Sub1_55;
		}
		this.searchPointer = null;
		return null;
	}

	@OriginalMember(owner = "client!hu", name = "a", descriptor = "(JI)Lclient!ac;")
	public CacheableNode method2671(@OriginalArg(0) long arg0) {
		this.searchKey = arg0;
		@Pc(20) CacheableNode local20 = this.buckets[(int) (arg0 & (long) (this.bucketCount - 1))];
		for (this.searchPointer = local20.aClass4_Sub1_55; this.searchPointer != local20; this.searchPointer = this.searchPointer.aClass4_Sub1_55) {
			if (this.searchPointer.indexReference == arg0) {
				@Pc(39) CacheableNode local39 = this.searchPointer;
				this.searchPointer = this.searchPointer.aClass4_Sub1_55;
				return local39;
			}
		}
		this.searchPointer = null;
		return null;
	}

	@OriginalMember(owner = "client!hu", name = "a", descriptor = "(BLclient!ac;J)V")
	public void method2674(@OriginalArg(1) CacheableNode arg0, @OriginalArg(2) long arg1) {
		if (arg0.aClass4_Sub1_56 != null) {
			arg0.method4662();
		}
		@Pc(21) CacheableNode local21 = this.buckets[(int) (arg1 & (long) (this.bucketCount - 1))];
		arg0.aClass4_Sub1_55 = local21;
		arg0.aClass4_Sub1_56 = local21.aClass4_Sub1_56;
		arg0.aClass4_Sub1_56.aClass4_Sub1_55 = arg0;
		arg0.indexReference = arg1;
		arg0.aClass4_Sub1_55.aClass4_Sub1_56 = arg0;
	}

@OriginalMember(owner = "client!hu", name = "f", descriptor = "I")
	public static int anInt3142;
@OriginalMember(owner = "client!hu", name = "e", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_64 = new Class83("cyan:", "blaugrün:", "cyan:", "cyan:");
@OriginalMember(owner = "client!hu", name = "h", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_65 = new Class83("scroll:", "scrollen:", "déroulement:", "rolagem:");
@OriginalMember(owner = "client!hu", name = "m", descriptor = "I")
	public static int anInt3147 = -1;
@OriginalMember(owner = "client!hu", name = "n", descriptor = "I")
	public static int anInt3148 = 503;
@OriginalMember(owner = "client!hu", name = "a", descriptor = "(Lclient!iv;II)V")
	public static void method2669(@OriginalArg(0) Packet arg0, @OriginalArg(2) int arg1) {
		if (JavaMatrix.aClass139_4 == null) {
			return;
		}
		try {
			JavaMatrix.aClass139_4.method3466(0L);
			JavaMatrix.aClass139_4.method3464(arg1, arg0.buffer, 24);
		} catch (@Pc(18) Exception local18) {
		}
	}
@OriginalMember(owner = "client!hu", name = "a", descriptor = "(II)V")
	public static void method2673(@OriginalArg(0) int arg0) {
		if (arg0 < 0 || arg0 > 2) {
			arg0 = 0;
		}
		TextureOpDiagonalWaves.anInt4008 = arg0;
	}
}
