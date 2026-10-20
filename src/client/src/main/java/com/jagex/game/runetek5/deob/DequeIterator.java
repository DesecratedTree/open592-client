package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.deob.Class168;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.entity.ParticleEmitter;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
@OriginalClass("client!em")
public final class DequeIterator {

	@OriginalMember(owner = "client!em", name = "e", descriptor = "Lclient!vu;")
	private Country aClass4_70;

	@OriginalMember(owner = "client!em", name = "f", descriptor = "Lclient!pk;")
	private Class183 aClass183_5;

	@OriginalMember(owner = "client!em", name = "<init>", descriptor = "()V")
	public DequeIterator() {
	}

	@OriginalMember(owner = "client!em", name = "<init>", descriptor = "(Lclient!pk;)V")
	public DequeIterator(@OriginalArg(0) Class183 arg0) {
		this.aClass183_5 = arg0;
	}

	@OriginalMember(owner = "client!em", name = "a", descriptor = "(Z)Lclient!vu;")
	public Country method1611() {
		@Pc(13) Country local13 = this.aClass183_5.aClass4_207.aClass4_262;
		if (local13 == this.aClass183_5.aClass4_207) {
			this.aClass4_70 = null;
			return null;
		} else {
			this.aClass4_70 = local13.aClass4_262;
			return local13;
		}
	}

	@OriginalMember(owner = "client!em", name = "a", descriptor = "(B)Lclient!vu;")
	public Country method1612() {
		@Pc(12) Country local12 = this.aClass4_70;
		if (this.aClass183_5.aClass4_207 == local12) {
			this.aClass4_70 = null;
			return null;
		} else {
			this.aClass4_70 = local12.aClass4_262;
			return local12;
		}
	}

	@OriginalMember(owner = "client!em", name = "a", descriptor = "(Lclient!pk;I)V")
	public void method1615(@OriginalArg(0) Class183 arg0) {
		this.aClass183_5 = arg0;
	}

@OriginalMember(owner = "client!em", name = "c", descriptor = "Lclient!pn;")
	public static final Class186 aClass186_7 = new Class186("", 17);
@OriginalMember(owner = "client!em", name = "h", descriptor = "I")
	public static int anInt1847 = 0;
@OriginalMember(owner = "client!em", name = "a", descriptor = "(IBI)Z")
	public static boolean method1613(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		return (arg0 & 0x10) != 0;
	}
@OriginalMember(owner = "client!em", name = "a", descriptor = "(I)Lclient!hp;")
	public static Class4_Sub1_Sub11 method1614() {
		@Pc(12) Class4_Sub1_Sub11 local12 = (Class4_Sub1_Sub11) Static99.aClass229_1.method5089();
		if (local12 != null) {
			local12.method5684();
			local12.method4662();
			return local12;
		}
		do {
			local12 = (Class4_Sub1_Sub11) ParticleEmitter.OPNPC6.method5089();
			if (local12 == null) {
				return null;
			}
			if (local12.method2611() > Class168.method3962()) {
				return null;
			}
			local12.method5684();
			local12.method4662();
		} while ((local12.indexReference & Long.MIN_VALUE) == 0L);
		return local12;
	}
@OriginalMember(owner = "client!em", name = "a", descriptor = "(III)V")
	public static void method1617(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		PingWorker.method5476(MonoStereoData.aClass215_33);
		ParticleNode.aClass4_Sub12_Sub1_5.method2505(arg1);
		ParticleNode.aClass4_Sub12_Sub1_5.method2531(arg0);
	}
}
