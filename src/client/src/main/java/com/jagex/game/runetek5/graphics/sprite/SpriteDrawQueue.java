package com.jagex.game.runetek5.graphics.sprite;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class229;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub9;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub9_Sub2;
import com.jagex.game.runetek5.entity.ComparableGraphNode;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import com.jagex.game.runetek5.deob.QuickChatValueType;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlBitBuffer;

import com.jagex.game.runetek5.graphics.sprite.*;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.deob.Class135;
@OriginalClass("client!sq")
public final class SpriteDrawQueue {

	@OriginalMember(owner = "client!sq", name = "o", descriptor = "Lclient!tn;")
	private final Class229 spriteDrawQueue = new Class229();

	@OriginalMember(owner = "client!sq", name = "p", descriptor = "I")
	private int currentCapacity;

	@OriginalMember(owner = "client!sq", name = "k", descriptor = "I")
	private final int maxCapacity;

	@OriginalMember(owner = "client!sq", name = "d", descriptor = "Lclient!ib;")
	private final CubeMapSpecularEffect aClass102_37;

	@OriginalMember(owner = "client!sq", name = "<init>", descriptor = "(I)V")
	public SpriteDrawQueue(@OriginalArg(0) int arg0) {
		this.currentCapacity = arg0;
		this.maxCapacity = arg0;
		@Pc(16) int local16;
		for (local16 = 1; local16 + local16 < arg0; local16 += local16) {
		}
		this.aClass102_37 = new CubeMapSpecularEffect(local16);
	}

	@OriginalMember(owner = "client!sq", name = "a", descriptor = "(ILclient!bq;)V")
	private void discard(@OriginalArg(1) ComparableGraphNode arg0) {
		@Pc(9) long local9 = arg0.toLong();
		for (@Pc(16) Class4_Sub1_Sub9 local16 = (Class4_Sub1_Sub9) this.aClass102_37.method2700(local9); local16 != null; local16 = (Class4_Sub1_Sub9) this.aClass102_37.method2697()) {
			if (local16.anInterface2_3.method3684(arg0)) {
				this.method4945(local16);
				return;
			}
		}
	}

	@OriginalMember(owner = "client!sq", name = "a", descriptor = "(I)I")
	public int method4944() {
		return this.currentCapacity;
	}

	@OriginalMember(owner = "client!sq", name = "a", descriptor = "(Lclient!mh;I)V")
	private void method4945(@OriginalArg(0) Class4_Sub1_Sub9 arg0) {
		if (arg0 != null) {
			arg0.method5684();
			arg0.method4662();
			this.currentCapacity += arg0.anInt3025;
		}
	}

	@OriginalMember(owner = "client!sq", name = "a", descriptor = "(Lclient!bq;I)Ljava/lang/Object;")
	public Object method4946(@OriginalArg(0) ComparableGraphNode arg0) {
		@Pc(9) long local9 = arg0.toLong();
		for (@Pc(23) Class4_Sub1_Sub9 local23 = (Class4_Sub1_Sub9) this.aClass102_37.method2700(local9); local23 != null; local23 = (Class4_Sub1_Sub9) this.aClass102_37.method2697()) {
			if (local23.anInterface2_3.method3684(arg0)) {
				@Pc(37) Object local37 = local23.get();
				if (local37 != null) {
					if (local23.isSoft()) {
						@Pc(66) Class4_Sub1_Sub9_Sub2 local66 = new Class4_Sub1_Sub9_Sub2(arg0, local37, local23.anInt3025);
						this.aClass102_37.method2703(local23.aLong224, local66);
						this.spriteDrawQueue.method5088(local66);
						local66.indexReference = 0L;
						local23.method5684();
						local23.method4662();
					} else {
						this.spriteDrawQueue.method5088(local23);
						local23.indexReference = 0L;
					}
					return local37;
				}
				local23.method5684();
				local23.method4662();
				this.currentCapacity += local23.anInt3025;
			}
		}
		return null;
	}

	@OriginalMember(owner = "client!sq", name = "b", descriptor = "(II)V")
	public void method4950() {
		if (Class135.aClass105_1 == null) {
			return;
		}
		for (@Pc(21) Class4_Sub1_Sub9 local21 = (Class4_Sub1_Sub9) this.spriteDrawQueue.method5089(); local21 != null; local21 = (Class4_Sub1_Sub9) this.spriteDrawQueue.method5092()) {
			if (local21.isSoft()) {
				if (local21.get() == null) {
					local21.method5684();
					local21.method4662();
					this.currentCapacity += local21.anInt3025;
				}
			} else if (++local21.indexReference > (long) 5) {
				@Pc(47) Class4_Sub1_Sub9 local47 = Class135.aClass105_1.method5276(local21);
				this.aClass102_37.method2703(local21.aLong224, local47);
				Class133.method3353(local21, local47);
				local21.method5684();
				local21.method4662();
			}
		}
	}

	@OriginalMember(owner = "client!sq", name = "a", descriptor = "(Z)V")
	public void method4951() {
		this.spriteDrawQueue.method5087();
		this.aClass102_37.method2699();
		this.currentCapacity = this.maxCapacity;
	}

	@OriginalMember(owner = "client!sq", name = "c", descriptor = "(I)V")
	public void method4952() {
		for (@Pc(5) Class4_Sub1_Sub9 local5 = (Class4_Sub1_Sub9) this.spriteDrawQueue.method5089(); local5 != null; local5 = (Class4_Sub1_Sub9) this.spriteDrawQueue.method5092()) {
			if (local5.isSoft()) {
				local5.method5684();
				local5.method4662();
				this.currentCapacity += local5.anInt3025;
			}
		}
	}

	@OriginalMember(owner = "client!sq", name = "a", descriptor = "(ILjava/lang/Object;Lclient!bq;)V")
	public void put(@OriginalArg(1) Object arg0, @OriginalArg(2) ComparableGraphNode object) {
		this.method4955(object, arg0);
	}

	@OriginalMember(owner = "client!sq", name = "a", descriptor = "(B)I")
	public int method4954() {
		return this.maxCapacity;
	}

	@OriginalMember(owner = "client!sq", name = "a", descriptor = "(Lclient!bq;Ljava/lang/Object;ZI)V")
	private void method4955(@OriginalArg(0) ComparableGraphNode arg0, @OriginalArg(1) Object arg1) {
		if (this.maxCapacity < 1) {
			throw new IllegalStateException("s>cs");
		}
		this.discard(arg0);
		this.currentCapacity--;
		while (this.currentCapacity < 0) {
			@Pc(36) Class4_Sub1_Sub9 local36 = (Class4_Sub1_Sub9) this.spriteDrawQueue.method5091();
			this.method4945(local36);
		}
		@Pc(59) Class4_Sub1_Sub9_Sub2 local59 = new Class4_Sub1_Sub9_Sub2(arg0, arg1, 1);
		this.aClass102_37.method2703(arg0.toLong(), local59);
		this.spriteDrawQueue.method5088(local59);
		local59.indexReference = 0L;
	}

@OriginalMember(owner = "client!sq", name = "c", descriptor = "[F")
	public static final float[] aFloatArray28 = new float[16];
@OriginalMember(owner = "client!sq", name = "i", descriptor = "Lclient!ho;")
	public static final QuickChatValueType OPPLAYER8 = new QuickChatValueType(7, 0, 1, 1);
@OriginalMember(owner = "client!sq", name = "s", descriptor = "Lclient!hk;")
	public static GlBitBuffer aClass4_Sub12_Sub2_3 = new GlBitBuffer(8192);
@OriginalMember(owner = "client!sq", name = "a", descriptor = "(ILclient!fs;BI)Lclient!jq;")
	public static Mesh method4948(@OriginalArg(1) Js5ResourceProvider arg0, @OriginalArg(3) int arg1) {
		@Pc(11) byte[] local11 = arg0.method2104(0, arg1);
		return local11 == null ? null : new Mesh(local11);
	}
@OriginalMember(owner = "client!sq", name = "a", descriptor = "(II)I")
	public static int method4949(@OriginalArg(0) int arg0) {
		@Pc(0) int local0 = arg0 - 1;
		@Pc(15) int local15 = local0 | local0 >>> 1;
		@Pc(21) int local21 = local15 | local15 >>> 2;
		@Pc(27) int local27 = local21 | local21 >>> 4;
		@Pc(33) int local33 = local27 | local27 >>> 8;
		@Pc(39) int local39 = local33 | local33 >>> 16;
		return local39 + 1;
	}
}
