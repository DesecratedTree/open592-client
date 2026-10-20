package com.jagex.game.runetek5.cache.node.lru;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.impl.SpriteRenderNode;
import com.jagex.game.runetek5.deob.Class229;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub8_Sub1;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import com.jagex.game.runetek5.deob.Class75;
import com.jagex.game.runetek5.deob.Static277;
import com.jagex.game.runetek5.entity.EntityNodeList;

import com.jagex.game.runetek5.cache.node.lru.*;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.cache.node.lru.StructType;
import com.jagex.game.runetek5.scene.ground.GlGround;
@OriginalClass("client!fa")
public final class LRUCache {

	@OriginalMember(owner = "client!fa", name = "k", descriptor = "Lclient!tn;")
	private final Class229 queue = new Class229();

	@OriginalMember(owner = "client!fa", name = "l", descriptor = "I")
	private int capacityLeft;

	@OriginalMember(owner = "client!fa", name = "f", descriptor = "I")
	private final int capacity;

	@OriginalMember(owner = "client!fa", name = "o", descriptor = "Lclient!ib;")
	private final CubeMapSpecularEffect aClass102_10;

	@OriginalMember(owner = "client!fa", name = "<init>", descriptor = "(I)V")
	public LRUCache(@OriginalArg(0) int arg0) {
		this.capacityLeft = arg0;
		this.capacity = arg0;
		@Pc(14) int local14;
		for (local14 = 1; arg0 > local14 + local14; local14 += local14) {
		}
		this.aClass102_10 = new CubeMapSpecularEffect(local14);
	}

	@OriginalMember(owner = "client!fa", name = "a", descriptor = "(Z)V")
	public void method1777() {
		this.queue.method5087();
		this.aClass102_10.method2699();
		this.capacityLeft = this.capacity;
	}

	@OriginalMember(owner = "client!fa", name = "a", descriptor = "(IJLjava/lang/Object;)V")
	public void method1779(@OriginalArg(1) long arg0, @OriginalArg(2) Object arg1) {
		this.method1790(arg1, arg0);
	}

	@OriginalMember(owner = "client!fa", name = "a", descriptor = "(B)I")
	public int method1781() {
		return this.capacityLeft;
	}

	@OriginalMember(owner = "client!fa", name = "b", descriptor = "(I)Ljava/lang/Object;")
	public Object method1782() {
		@Pc(11) SpriteRenderNode local11 = (SpriteRenderNode) this.aClass102_10.method2704();
		while (local11 != null) {
			@Pc(23) Object local23 = local11.get();
			if (local23 != null) {
				return local23;
			}
			@Pc(29) SpriteRenderNode local29 = local11;
			local11 = (SpriteRenderNode) this.aClass102_10.method2704();
			local29.method5684();
			local29.method4662();
			this.capacityLeft += local11.size;
		}
		return null;
	}

	@OriginalMember(owner = "client!fa", name = "a", descriptor = "(JI)V")
	private void method1783(@OriginalArg(0) long arg0) {
		@Pc(10) SpriteRenderNode local10 = (SpriteRenderNode) this.aClass102_10.method2700(arg0);
		this.method1792(local10);
	}

	@OriginalMember(owner = "client!fa", name = "b", descriptor = "(B)I")
	public int method1785() {
		return this.capacity;
	}

	@OriginalMember(owner = "client!fa", name = "c", descriptor = "(B)I")
	public int method1786() {
		@Pc(7) int local7 = 0;
		for (@Pc(13) SpriteRenderNode local13 = (SpriteRenderNode) this.queue.method5089(); local13 != null; local13 = (SpriteRenderNode) this.queue.method5092()) {
			if (!local13.isSoft()) {
				local7++;
			}
		}
		return local7;
	}

	@OriginalMember(owner = "client!fa", name = "a", descriptor = "(JB)Ljava/lang/Object;")
	public Object method1787(@OriginalArg(0) long arg0) {
		@Pc(10) SpriteRenderNode local10 = (SpriteRenderNode) this.aClass102_10.method2700(arg0);
		if (local10 == null) {
			return null;
		}
		@Pc(18) Object local18 = local10.get();
		if (local18 == null) {
			local10.method5684();
			local10.method4662();
			this.capacityLeft += local10.size;
			return null;
		}
		if (local10.isSoft()) {
			@Pc(51) Class4_Sub1_Sub8_Sub1 local51 = new Class4_Sub1_Sub8_Sub1(local18, local10.size);
			this.aClass102_10.method2703(local10.aLong224, local51);
			this.queue.method5088(local51);
			local51.indexReference = 0L;
			local10.method5684();
			local10.method4662();
		} else {
			this.queue.method5088(local10);
			local10.indexReference = 0L;
		}
		return local18;
	}

	@OriginalMember(owner = "client!fa", name = "a", descriptor = "(II)V")
	public void method1788(@OriginalArg(0) int arg0) {
		if (GlGround.aClass174_1 == null) {
			return;
		}
		for (@Pc(13) SpriteRenderNode local13 = (SpriteRenderNode) this.queue.method5089(); local13 != null; local13 = (SpriteRenderNode) this.queue.method5092()) {
			if (local13.isSoft()) {
				if (local13.get() == null) {
					local13.method5684();
					local13.method4662();
					this.capacityLeft++;
				}
			} else if (++local13.indexReference > (long) arg0) {
				@Pc(39) SpriteRenderNode local39 = GlGround.aClass174_1.createSoftReference(local13);
				this.aClass102_10.method2703(local13.aLong224, local39);
				Class133.method3353(local13, local39);
				local13.method5684();
				local13.method4662();
			}
		}
	}

	@OriginalMember(owner = "client!fa", name = "d", descriptor = "(B)Ljava/lang/Object;")
	public Object method1789() {
		@Pc(16) SpriteRenderNode local16 = (SpriteRenderNode) this.aClass102_10.method2705();
		while (local16 != null) {
			@Pc(22) Object local22 = local16.get();
			if (local22 != null) {
				return local22;
			}
			@Pc(26) SpriteRenderNode local26 = local16;
			local16 = (SpriteRenderNode) this.aClass102_10.method2704();
			local26.method5684();
			local26.method4662();
			this.capacityLeft += local16.size;
		}
		return null;
	}

	@OriginalMember(owner = "client!fa", name = "a", descriptor = "(Ljava/lang/Object;IJI)V")
	private void method1790(@OriginalArg(0) Object arg0, @OriginalArg(2) long arg1) {
		if (this.capacity < 1) {
			throw new IllegalStateException("s>cs");
		}
		this.method1783(arg1);
		this.capacityLeft--;
		while (this.capacityLeft < 0) {
			@Pc(32) SpriteRenderNode local32 = (SpriteRenderNode) this.queue.method5091();
			this.method1792(local32);
		}
		@Pc(45) Class4_Sub1_Sub8_Sub1 local45 = new Class4_Sub1_Sub8_Sub1(arg0, 1);
		this.aClass102_10.method2703(arg1, local45);
		this.queue.method5088(local45);
		local45.indexReference = 0L;
	}

	@OriginalMember(owner = "client!fa", name = "c", descriptor = "(I)V")
	public void method1791() {
		for (@Pc(7) SpriteRenderNode local7 = (SpriteRenderNode) this.queue.method5089(); local7 != null; local7 = (SpriteRenderNode) this.queue.method5092()) {
			if (local7.isSoft()) {
				local7.method5684();
				local7.method4662();
				this.capacityLeft += local7.size;
			}
		}
	}

	@OriginalMember(owner = "client!fa", name = "a", descriptor = "(Lclient!fq;I)V")
	private void method1792(@OriginalArg(0) SpriteRenderNode arg0) {
		if (arg0 != null) {
			arg0.method5684();
			arg0.method4662();
			this.capacityLeft += arg0.size;
		}
	}

@OriginalMember(owner = "client!fa", name = "q", descriptor = "Lclient!fp;")
	public static Class75 aClass75_1;
@OriginalMember(owner = "client!fa", name = "e", descriptor = "Lclient!gn;")
	public static final EntityNodeList aClass84_5 = new EntityNodeList();
@OriginalMember(owner = "client!fa", name = "n", descriptor = "I")
	public static int anInt2036 = -50;
@OriginalMember(owner = "client!fa", name = "v", descriptor = "[I")
	public static final int[] anIntArray158 = new int[] { 0, 1, 2, 3, 4, 5, 6, 14 };
@OriginalMember(owner = "client!fa", name = "a", descriptor = "(IB)Z")
	public static boolean method1778(@OriginalArg(0) int arg0) {
		return arg0 >= 0 && arg0 <= 3 || arg0 == 9;
	}
@OriginalMember(owner = "client!fa", name = "b", descriptor = "(Z)V")
	public static void method1784() {
		if (Static277.anIntArray336 != null && StructType.anIntArray416 != null) {
			return;
		}
		StructType.anIntArray416 = new int[256];
		Static277.anIntArray336 = new int[256];
		for (@Pc(18) int local18 = 0; local18 < 256; local18++) {
			@Pc(27) double local27 = (double) local18 / 255.0D * 6.283185307179586D;
			Static277.anIntArray336[local18] = (int) (Math.sin(local27) * 4096.0D);
			StructType.anIntArray416[local18] = (int) (Math.cos(local27) * 4096.0D);
		}
	}
}
