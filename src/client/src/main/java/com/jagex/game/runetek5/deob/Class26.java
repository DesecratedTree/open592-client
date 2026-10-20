package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import java.util.Random;
import com.jagex.game.runetek5.sign.SignedResource;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class130;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.graphics.model.normal.ModelNormalsGL;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.graphics.JavaBillboardAttributes;
@OriginalClass("client!bp")
public final class Class26 {

	@OriginalMember(owner = "client!bp", name = "b", descriptor = "Lclient!fa;")
	private LRUCache aClass68_3 = new LRUCache(64);

	@OriginalMember(owner = "client!bp", name = "d", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider aClass76_6;

	@OriginalMember(owner = "client!bp", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;)V")
	public Class26(@OriginalArg(0) Game arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Js5ResourceProvider arg2) {
		this.aClass76_6 = arg2;
		if (this.aClass76_6 != null) {
			@Pc(20) int local20 = this.aClass76_6.method2100() - 1;
			this.aClass76_6.method2108(local20);
		}
	}

	@OriginalMember(owner = "client!bp", name = "a", descriptor = "(I)V")
	public void method644() {
		@Pc(6) LRUCache local6 = this.aClass68_3;
		synchronized (this.aClass68_3) {
			this.aClass68_3.method1777();
		}
	}

	@OriginalMember(owner = "client!bp", name = "a", descriptor = "(BI)V")
	public void method645() {
		@Pc(6) LRUCache local6 = this.aClass68_3;
		synchronized (this.aClass68_3) {
			this.aClass68_3.method1788(5);
		}
	}

	@OriginalMember(owner = "client!bp", name = "b", descriptor = "(II)V")
	public void method646(@OriginalArg(0) int arg0) {
		@Pc(6) LRUCache local6 = this.aClass68_3;
		synchronized (this.aClass68_3) {
			this.aClass68_3.method1777();
			this.aClass68_3 = new LRUCache(arg0);
		}
	}

	@OriginalMember(owner = "client!bp", name = "c", descriptor = "(II)Lclient!ki;")
	public Class126 method647(@OriginalArg(0) int arg0) {
		@Pc(6) LRUCache local6 = this.aClass68_3;
		@Pc(16) Class126 local16;
		synchronized (this.aClass68_3) {
			local16 = (Class126) this.aClass68_3.method1787((long) arg0);
		}
		if (local16 != null) {
			return local16;
		}
		@Pc(39) byte[] local39 = this.aClass76_6.method2104(JavaBillboardAttributes.method3851(arg0), Class130.method3206(arg0));
		local16 = new Class126();
		if (local39 != null) {
			local16.method3191(new Packet(local39));
		}
		@Pc(55) LRUCache local55 = this.aClass68_3;
		synchronized (this.aClass68_3) {
			this.aClass68_3.method1779((long) arg0, local16);
			return local16;
		}
	}

	@OriginalMember(owner = "client!bp", name = "a", descriptor = "(B)V")
	public void method649() {
		@Pc(6) LRUCache local6 = this.aClass68_3;
		synchronized (this.aClass68_3) {
			this.aClass68_3.method1791();
		}
	}

@OriginalMember(owner = "client!bp", name = "a", descriptor = "Lclient!qt;")
	public static SignedResource aClass199_3;
@OriginalMember(owner = "client!bp", name = "e", descriptor = "[Lclient!pt;")
	public static final Class4_Sub1_Sub17[] aClass4_Sub1_Sub17Array4 = new Class4_Sub1_Sub17[14];
@OriginalMember(owner = "client!bp", name = "a", descriptor = "(II)[B")
	public static byte[] method643(@OriginalArg(1) int arg0) {
		@Pc(12) CacheableNodeByteArray local12 = (CacheableNodeByteArray) ParticleBinaryTree.aClass142_3.method3483((long) arg0);
		if (local12 == null) {
			@Pc(25) byte[] local25 = new byte[512];
			@Pc(31) Random local31 = new Random((long) arg0);
			for (@Pc(33) int local33 = 0; local33 < 255; local33++) {
				local25[local33] = (byte) local33;
			}
			for (@Pc(48) int local48 = 0; local48 < 255; local48++) {
				@Pc(55) int local55 = 255 - local48;
				@Pc(60) int local60 = ModelNormalsGL.method3443(local31, local55);
				@Pc(64) byte local64 = local25[local60];
				local25[local60] = local25[local55];
				local25[local55] = local25[511 - local48] = local64;
			}
			local12 = new CacheableNodeByteArray(local25);
			ParticleBinaryTree.aClass142_3.method3480((long) arg0, local12);
		}
		return local12.aByteArray57;
	}
@OriginalMember(owner = "client!bp", name = "a", descriptor = "(IIILjava/lang/Class;)Lclient!pe;")
	public static Class16_Sub1 method648(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) Class arg3) {
		@Pc(7) Class164 local7 = Class70.aClass164ArrayArrayArray5[arg0][arg1][arg2];
		if (local7 == null) {
			return null;
		}
		for (@Pc(14) Class7 local14 = local7.aClass7_3; local14 != null; local14 = local14.aClass7_1) {
			@Pc(18) Class16_Sub1 local18 = local14.aClass16_Sub1_1;
			if (arg3.isAssignableFrom(local18.getClass()) && local18.aShort94 == arg1 && local18.aShort96 == arg2) {
				return local18;
			}
		}
		return null;
	}
}
