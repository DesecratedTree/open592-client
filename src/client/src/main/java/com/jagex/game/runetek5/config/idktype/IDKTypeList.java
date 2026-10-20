package com.jagex.game.runetek5.config.idktype;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.config.identitykit.IdkType;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.config.idktype.*;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
@OriginalClass("client!in")
public final class IDKTypeList {

	@OriginalMember(owner = "client!in", name = "h", descriptor = "Lclient!fa;")
	private final LRUCache recentUse = new LRUCache(64);

	@OriginalMember(owner = "client!in", name = "e", descriptor = "Lclient!fs;")
	public final Js5ResourceProvider aClass76_41;

	@OriginalMember(owner = "client!in", name = "a", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider configClient;

	@OriginalMember(owner = "client!in", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;Lclient!fs;)V")
	public IDKTypeList(@OriginalArg(0) Game modeGame, @OriginalArg(1) int languageId, @OriginalArg(2) Js5ResourceProvider configClient, @OriginalArg(3) Js5ResourceProvider meshResource) {
		this.aClass76_41 = meshResource;
		this.configClient = configClient;
		this.configClient.method2108(3);
	}

	@OriginalMember(owner = "client!in", name = "a", descriptor = "(B)V")
	public void method2756() {
		@Pc(6) LRUCache local6 = this.recentUse;
		synchronized (this.recentUse) {
			this.recentUse.method1777();
		}
	}

	@OriginalMember(owner = "client!in", name = "a", descriptor = "(II)Lclient!pd;")
	public IdkType method2758(@OriginalArg(0) int arg0) {
		@Pc(6) LRUCache local6 = this.recentUse;
		@Pc(16) IdkType local16;
		synchronized (this.recentUse) {
			local16 = (IdkType) this.recentUse.method1787((long) arg0);
		}
		if (local16 != null) {
			return local16;
		}
		@Pc(33) byte[] local33 = this.configClient.method2104(arg0, 3);
		local16 = new IdkType();
		local16.identityTypeList = this;
		if (local33 != null) {
			local16.decode(new Packet(local33));
		}
		@Pc(52) LRUCache local52 = this.recentUse;
		synchronized (this.recentUse) {
			this.recentUse.method1779((long) arg0, local16);
			return local16;
		}
	}

	@OriginalMember(owner = "client!in", name = "a", descriptor = "(ZI)V")
	public void method2759() {
		@Pc(13) LRUCache local13 = this.recentUse;
		synchronized (this.recentUse) {
			this.recentUse.method1788(5);
		}
	}

	@OriginalMember(owner = "client!in", name = "a", descriptor = "(Z)V")
	public void method2760() {
		@Pc(6) LRUCache local6 = this.recentUse;
		synchronized (this.recentUse) {
			this.recentUse.method1791();
		}
	}

@OriginalMember(owner = "client!in", name = "j", descriptor = "I")
	public static int anInt3265;
@OriginalMember(owner = "client!in", name = "l", descriptor = "Lclient!al;")
	public static BufferedSocket aClass11_6;
@OriginalMember(owner = "client!in", name = "i", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_146 = new Class22(54, -2);
@OriginalMember(owner = "client!in", name = "k", descriptor = "[[Z")
	public static final boolean[][] aBooleanArrayArray2 = new boolean[][] { new boolean[4], new boolean[4], { false, false, true, false }, { false, false, true, false }, { false, false, true, false }, { false, false, true, false }, { true, false, true, false }, { true, false, false, true }, { true, false, false, true }, new boolean[4], new boolean[4], new boolean[4], new boolean[4] };
@OriginalMember(owner = "client!in", name = "a", descriptor = "(IIIBI)V")
	public static void method2755(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(4) int arg3) {
		@Pc(7) int local7 = 0;
		@Pc(14) int local14 = arg1;
		@Pc(17) int local17 = -arg1;
		BasType.method3640(arg0 - arg1, arg3, arg0 + arg1, Class4_Sub6_Sub7.anIntArrayArray21[arg2]);
		@Pc(33) int local33 = -1;
		while (local14 > local7) {
			local33 += 2;
			local17 += local33;
			local7++;
			if (local17 >= 0) {
				local14--;
				local17 -= local14 << 1;
				@Pc(59) int[] local59 = Class4_Sub6_Sub7.anIntArrayArray21[local14 + arg2];
				@Pc(65) int[] local65 = Class4_Sub6_Sub7.anIntArrayArray21[arg2 - local14];
				@Pc(69) int local69 = arg0 + local7;
				@Pc(74) int local74 = arg0 - local7;
				BasType.method3640(local74, arg3, local69, local59);
				BasType.method3640(local74, arg3, local69, local65);
			}
			@Pc(91) int local91 = arg0 + local14;
			@Pc(96) int local96 = arg0 - local14;
			@Pc(103) int[] local103 = Class4_Sub6_Sub7.anIntArrayArray21[arg2 + local7];
			@Pc(110) int[] local110 = Class4_Sub6_Sub7.anIntArrayArray21[arg2 - local7];
			BasType.method3640(local96, arg3, local91, local103);
			BasType.method3640(local96, arg3, local91, local110);
		}
	}
}
