package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchNode;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.graphics.model.GlModel;
@OriginalClass("client!ph")
public final class Class180 {

	@OriginalMember(owner = "client!ph", name = "c", descriptor = "Lclient!fa;")
	private final LRUCache aClass68_32 = new LRUCache(64);

	@OriginalMember(owner = "client!ph", name = "h", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider aClass76_62;

	@OriginalMember(owner = "client!ph", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;)V")
	public Class180(@OriginalArg(0) Game arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Js5ResourceProvider arg2) {
		this.aClass76_62 = arg2;
		if (this.aClass76_62 != null) {
			this.aClass76_62.method2108(11);
		}
	}

	@OriginalMember(owner = "client!ph", name = "a", descriptor = "(B)V")
	public void method4116() {
		@Pc(14) LRUCache local14 = this.aClass68_32;
		synchronized (this.aClass68_32) {
			this.aClass68_32.method1777();
		}
	}

	@OriginalMember(owner = "client!ph", name = "a", descriptor = "(Z)V")
	public void method4118() {
		@Pc(2) LRUCache local2 = this.aClass68_32;
		synchronized (this.aClass68_32) {
			this.aClass68_32.method1791();
		}
	}

	@OriginalMember(owner = "client!ph", name = "a", descriptor = "(II)V")
	public void method4120() {
		@Pc(2) LRUCache local2 = this.aClass68_32;
		synchronized (this.aClass68_32) {
			this.aClass68_32.method1788(5);
		}
	}

	@OriginalMember(owner = "client!ph", name = "a", descriptor = "(BI)Lclient!lg;")
	public Class133 method4121(@OriginalArg(1) int arg0) {
		@Pc(6) LRUCache local6 = this.aClass68_32;
		@Pc(24) Class133 local24;
		synchronized (this.aClass68_32) {
			local24 = (Class133) this.aClass68_32.method1787((long) arg0);
		}
		if (local24 != null) {
			return local24;
		}
		@Pc(41) byte[] local41 = this.aClass76_62.method2104(arg0, 11);
		local24 = new Class133();
		if (local41 != null) {
			local24.method3354(new Packet(local41));
		}
		@Pc(57) LRUCache local57 = this.aClass68_32;
		synchronized (this.aClass68_32) {
			this.aClass68_32.method1779((long) arg0, local24);
			return local24;
		}
	}

@OriginalMember(owner = "client!ph", name = "b", descriptor = "Lclient!pk;")
	public static final Class183 aClass183_39 = new Class183();
@OriginalMember(owner = "client!ph", name = "e", descriptor = "I")
	public static int anInt5369 = 0;
@OriginalMember(owner = "client!ph", name = "i", descriptor = "Lclient!uu;")
	public static com.jagex.game.runetek5.config.rsinterface.Component aClass247_16 = null;
@OriginalMember(owner = "client!ph", name = "a", descriptor = "(Lclient!jj;IIII)V")
	public static void method4115(@OriginalArg(0) Class120 arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4) {
		if (arg1 == 1 && arg3 > 0) {
			MusicPatchNode.method3448(arg0, arg2, arg3 - 1, arg4);
		} else if (arg1 == 4 && arg3 <= GpuFaceNormal.menuOpenWidth) {
			MusicPatchNode.method3448(arg0, arg2, arg3 + 1, arg4);
		} else if (arg1 == 8 && arg4 > 0) {
			MusicPatchNode.method3448(arg0, arg2, arg3, arg4 - 1);
		} else if (arg1 == 2 && arg4 <= GlModel.anInt3361) {
			MusicPatchNode.method3448(arg0, arg2, arg3, arg4 + 1);
		} else if (arg1 == 16 && arg3 > 0 && arg4 <= GlModel.anInt3361) {
			MusicPatchNode.method3448(arg0, arg2, arg3 - 1, arg4 + 1);
		} else if (arg1 == 32 && arg3 <= GpuFaceNormal.menuOpenWidth && arg4 <= GlModel.anInt3361) {
			MusicPatchNode.method3448(arg0, arg2, arg3 + 1, arg4 + 1);
		} else if (arg1 == 128 && arg3 > 0 && arg4 > 0) {
			MusicPatchNode.method3448(arg0, arg2, arg3 - 1, arg4 - 1);
		} else if (arg1 == 64 && arg3 <= GpuFaceNormal.menuOpenWidth && arg4 > 0) {
			MusicPatchNode.method3448(arg0, arg2, arg3 + 1, arg4 - 1);
		} else {
			throw new RuntimeException("Unsupported wall shape " + arg1 + " in world2.setclosestlights_wall");
		}
	}
@OriginalMember(owner = "client!ph", name = "a", descriptor = "(IZ)V")
	public static void method4119() {
		@Pc(1) LRUCache local1 = NPCType.aClass68_58;
		synchronized (NPCType.aClass68_58) {
			NPCType.aClass68_58.method1788(5);
		}
		local1 = Class4_Sub6_Sub15.aClass68_20;
		synchronized (Class4_Sub6_Sub15.aClass68_20) {
			Class4_Sub6_Sub15.aClass68_20.method1788(5);
		}
	}
@OriginalMember(owner = "client!ph", name = "b", descriptor = "(II)I")
	public static int method4122(@OriginalArg(0) int arg0) {
		if (arg0 == 6407 || arg0 == 34843 || arg0 == 34837) {
			return 6407;
		} else if (arg0 == 6408 || arg0 == 34842 || arg0 == 34836) {
			return 6408;
		} else if (arg0 == 6406 || arg0 == 34844) {
			return 6406;
		} else if (arg0 == 6409 || arg0 == 34846) {
			return 6409;
		} else if (arg0 == 6410 || arg0 == 34847) {
			return 6410;
		} else if (arg0 == 6402) {
			return 6402;
		} else {
			throw new IllegalArgumentException("");
		}
	}
}
