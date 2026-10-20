package com.jagex.game.runetek5.config.mapsprite;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class4_Sub18;
import com.jagex.game.runetek5.deob.Class4_Sub45;
import com.jagex.game.runetek5.deob.ObjStackEntity;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static166;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static411;
import com.jagex.game.runetek5.deob.Static99;
import com.jagex.game.runetek5.deob.p;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.config.mapsprite.*;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.deob.Class4_Sub14;
import com.jagex.game.runetek5.deob.Class249;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class217;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.deob.Class103;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.deob.Class58;
import com.jagex.game.runetek5.cache.node.lru.GlShadow;
import com.jagex.game.runetek5.deob.Class56;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
@OriginalClass("client!wi")
public final class MapSpriteTypeList {

	@OriginalMember(owner = "client!wi", name = "a", descriptor = "Lclient!fa;")
	private LRUCache mapspriteTypeCache = new LRUCache(64);

	@OriginalMember(owner = "client!wi", name = "q", descriptor = "Lclient!fa;")
	public LRUCache mapspriteCache = new LRUCache(64);

	@OriginalMember(owner = "client!wi", name = "g", descriptor = "Lclient!fs;")
	public final Js5ResourceProvider aClass76_103;

	@OriginalMember(owner = "client!wi", name = "c", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider mapspriteTypeResource;

	@OriginalMember(owner = "client!wi", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;Lclient!fs;)V")
	public MapSpriteTypeList(@OriginalArg(0) Game modeGame, @OriginalArg(1) int languageId, @OriginalArg(2) Js5ResourceProvider mapspriteTypeResource, @OriginalArg(3) Js5ResourceProvider mapspriteResource) {
		this.aClass76_103 = mapspriteResource;
		this.mapspriteTypeResource = mapspriteTypeResource;
		this.mapspriteTypeResource.method2108(34);
	}

	@OriginalMember(owner = "client!wi", name = "a", descriptor = "(I)V")
	public void method5587() {
		@Pc(2) LRUCache local2 = this.mapspriteTypeCache;
		synchronized (this.mapspriteTypeCache) {
			this.mapspriteTypeCache.method1777();
		}
		local2 = this.mapspriteCache;
		synchronized (this.mapspriteCache) {
			this.mapspriteCache.method1777();
		}
	}

	@OriginalMember(owner = "client!wi", name = "a", descriptor = "(II)Lclient!co;")
	public MapSpriteType method5588(@OriginalArg(1) int arg0) {
		@Pc(6) LRUCache local6 = this.mapspriteTypeCache;
		@Pc(16) MapSpriteType local16;
		synchronized (this.mapspriteTypeCache) {
			local16 = (MapSpriteType) this.mapspriteTypeCache.method1787((long) arg0);
		}
		if (local16 != null) {
			return local16;
		}
		@Pc(38) byte[] local38 = this.mapspriteTypeResource.method2104(arg0, 34);
		local16 = new MapSpriteType();
		local16.mapspriteTypeList = this;
		if (local38 != null) {
			local16.method938(new Packet(local38));
		}
		@Pc(57) LRUCache local57 = this.mapspriteTypeCache;
		synchronized (this.mapspriteTypeCache) {
			this.mapspriteTypeCache.method1779((long) arg0, local16);
			return local16;
		}
	}

	@OriginalMember(owner = "client!wi", name = "a", descriptor = "(III)V")
	public void method5591(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		this.mapspriteTypeCache = new LRUCache(arg1);
		this.mapspriteCache = new LRUCache(arg0);
	}

	@OriginalMember(owner = "client!wi", name = "b", descriptor = "(II)V")
	public void method5594() {
		@Pc(6) LRUCache local6 = this.mapspriteTypeCache;
		synchronized (this.mapspriteTypeCache) {
			this.mapspriteTypeCache.method1788(5);
		}
		local6 = this.mapspriteCache;
		synchronized (this.mapspriteCache) {
			this.mapspriteCache.method1788(5);
		}
	}

	@OriginalMember(owner = "client!wi", name = "a", descriptor = "(B)V")
	public void method5596() {
		@Pc(6) LRUCache local6 = this.mapspriteTypeCache;
		synchronized (this.mapspriteTypeCache) {
			this.mapspriteTypeCache.method1791();
		}
		local6 = this.mapspriteCache;
		synchronized (this.mapspriteCache) {
			this.mapspriteCache.method1791();
		}
	}

@OriginalMember(owner = "client!wi", name = "b", descriptor = "[I")
	public static int[] anIntArray548;
@OriginalMember(owner = "client!wi", name = "d", descriptor = "[I")
	public static int[] anIntArray549 = new int[2];
@OriginalMember(owner = "client!wi", name = "o", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_331 = new Class22(101, -1);
@OriginalMember(owner = "client!wi", name = "a", descriptor = "(IJII)V")
	public static void method5589(@OriginalArg(1) long arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		@Pc(10) int local10 = (int) arg0 >> 14 & 0x1F;
		@Pc(17) int local17 = (int) arg0 >> 20 & 0x3;
		@Pc(28) int local28 = Integer.MAX_VALUE & (int) (arg0 >>> 32);
		if (local10 != 10 && local10 != 11 && local10 != 22) {
			Static166.method2734(local17, arg2, arg1, 0, true, 0, 0, local10);
			return;
		}
		@Pc(46) LocType local46 = Static267.aClass262_2.method5560(local28);
		@Pc(62) int local62;
		@Pc(59) int local59;
		if (local17 == 0 || local17 == 2) {
			local59 = local46.anInt5801;
			local62 = local46.anInt5816;
		} else {
			local62 = local46.anInt5801;
			local59 = local46.anInt5816;
		}
		@Pc(73) int local73 = local46.anInt5834;
		if (local17 != 0) {
			local73 = (local73 << local17 & 0xF) + (local73 >> 4 - local17);
		}
		Static166.method2734(0, arg2, arg1, local59, true, local62, local73, 0);
	}
@OriginalMember(owner = "client!wi", name = "a", descriptor = "(Lclient!ya;BLclient!fs;)V")
	public static void method5592(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(2) Js5ResourceProvider arg1) {
		if (GLBillowTextureData.aBoolean509) {
			return;
		}
		arg0.p(0);
		GlVboElementsArrayBuffer.aClass57_23 = arg0.generateSprite(SpriteType.method328(arg1, LocType.anInt5846));
		GlVboElementsArrayBuffer.aClass57_23.render((GpuPointLight.anInt2881 - GlVboElementsArrayBuffer.aClass57_23.scaleWidth()) / 2, (EffectiveVertex.anInt5346 - GlVboElementsArrayBuffer.aClass57_23.scaleHeight()) / 2);
		Class58.aClass57_10 = arg0.generateSprite(SpriteType.method328(arg1, Class217.anInt6320));
		Class58.aClass57_10.render((GpuPointLight.anInt2881 - Class58.aClass57_10.scaleWidth()) / 2, 18);
		GLBillowTextureData.aBoolean509 = true;
	}
@OriginalMember(owner = "client!wi", name = "a", descriptor = "(Ljava/lang/String;IB)V")
	public static void method5593(@OriginalArg(0) String arg0, @OriginalArg(1) int arg1) {
		@Pc(15) int local15 = Class4_Sub7.anInt606;
		@Pc(17) int[] local17 = Static99.anIntArray157;
		@Pc(19) boolean local19 = false;
		for (@Pc(21) int local21 = 0; local21 < local15; local21++) {
			@Pc(29) PlayerEntity local29 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local17[local21]];
			if (local29 != null && Static1.aClass16_Sub1_Sub5_Sub1_1 != local29 && local29.aString44 != null && local29.aString44.equalsIgnoreCase(arg0)) {
				if (arg1 == 1) {
					PingWorker.method5476(Class3_Sub3.aClass215_28);
					ParticleNode.aClass4_Sub12_Sub1_5.method2505(local17[local21]);
					ParticleNode.aClass4_Sub12_Sub1_5.method2551(0);
				} else if (arg1 == 4) {
					PingWorker.method5476(GlShadow.aClass215_34);
					ParticleNode.aClass4_Sub12_Sub1_5.method2521(0);
					ParticleNode.aClass4_Sub12_Sub1_5.method2496(local17[local21]);
				} else if (arg1 == 5) {
					PingWorker.method5476(Class56.aClass215_18);
					ParticleNode.aClass4_Sub12_Sub1_5.method2496(local17[local21]);
					ParticleNode.aClass4_Sub12_Sub1_5.method2521(0);
				} else if (arg1 == 6) {
					PingWorker.method5476(MonoStereoData.aClass215_32);
					ParticleNode.aClass4_Sub12_Sub1_5.method2521(0);
					ParticleNode.aClass4_Sub12_Sub1_5.method2505(local17[local21]);
				} else if (arg1 == 7) {
					PingWorker.method5476(GlFramebuffer.aClass215_52);
					ParticleNode.aClass4_Sub12_Sub1_5.method2495(0);
					ParticleNode.aClass4_Sub12_Sub1_5.method2524(local17[local21]);
				}
				local19 = true;
				break;
			}
		}
		if (!local19) {
			Class249.method5323(Class4_Sub14.aClass83_32.method2267(NativeBillboardFace.anInt3423) + arg0);
		}
	}
@OriginalMember(owner = "client!wi", name = "a", descriptor = "(IIII)V")
	public static void method5595(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		if (Class70.aClass164ArrayArrayArray5 == null) {
			return;
		}
		@Pc(19) long local19 = (long) (arg2 << 14 | arg0 << 28 | arg1);
		@Pc(25) Class4_Sub18 local25 = (Class4_Sub18) LocTypeList.aClass102_40.method2700(local19);
		if (local25 == null) {
			Static411.method5275(arg0, arg1, arg2);
			return;
		}
		@Pc(41) Class4_Sub45 local41 = (Class4_Sub45) local25.aClass183_14.method4140();
		if (local41 == null) {
			Static411.method5275(arg0, arg1, arg2);
			return;
		}
		@Pc(63) ObjStackEntity local63 = (ObjStackEntity) Static411.method5275(arg0, arg1, arg2);
		if (local63 == null) {
			local63 = new ObjStackEntity();
		} else {
			local63.anInt758 = local63.anInt746 = -1;
		}
		local63.anInt755 = local41.anInt7355;
		local63.anInt756 = local41.anInt7356;
		label44: while (true) {
			@Pc(91) Class4_Sub45 local91 = (Class4_Sub45) local25.aClass183_14.method4144();
			if (local91 == null) {
				break;
			}
			if (local91.anInt7355 != local63.anInt755) {
				local63.anInt758 = local91.anInt7355;
				local63.anInt752 = local91.anInt7356;
				while (true) {
					@Pc(112) Class4_Sub45 local112 = (Class4_Sub45) local25.aClass183_14.method4144();
					if (local112 == null) {
						break label44;
					}
					if (local63.anInt755 != local112.anInt7355 && local63.anInt758 != local112.anInt7355) {
						local63.anInt746 = local112.anInt7355;
						local63.anInt749 = local112.anInt7356;
					}
				}
			}
		}
		@Pc(156) int local156 = BufferedSocket.method135((arg1 << 7) + 64, arg0, (arg2 << 7) + 64);
		Class103.method2710(arg0, arg1, arg2, local156, local63);
	}
}
