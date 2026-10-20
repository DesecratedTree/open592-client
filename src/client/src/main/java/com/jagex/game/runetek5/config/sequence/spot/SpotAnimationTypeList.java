package com.jagex.game.runetek5.config.sequence.spot;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import java.util.zip.CRC32;
import com.jagex.game.runetek5.ByteArrayWrapper;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Static395;
import com.jagex.game.runetek5.config.sequence.spot.*;
import com.jagex.game.runetek5.graphics.texture.water.NativeWaterTexture;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.config.enumtype.EnumType;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
@OriginalClass("client!qj")
public final class SpotAnimationTypeList {

	@OriginalMember(owner = "client!qj", name = "o", descriptor = "I")
	public int featureMask;

	@OriginalMember(owner = "client!qj", name = "j", descriptor = "Lclient!fa;")
	private final LRUCache aClass68_36 = new LRUCache(64);

	@OriginalMember(owner = "client!qj", name = "l", descriptor = "Lclient!fa;")
	public final LRUCache aClass68_37 = new LRUCache(30);

	@OriginalMember(owner = "client!qj", name = "c", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider gfxResource;

	@OriginalMember(owner = "client!qj", name = "b", descriptor = "Lclient!fs;")
	public final Js5ResourceProvider aClass76_71;

	@OriginalMember(owner = "client!qj", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;Lclient!fs;)V")
	public SpotAnimationTypeList(@OriginalArg(0) Game modeGame, @OriginalArg(1) int languageId, @OriginalArg(2) Js5ResourceProvider arg2, @OriginalArg(3) Js5ResourceProvider arg3) {
		this.gfxResource = arg2;
		this.aClass76_71 = arg3;
		@Pc(26) int local26 = this.gfxResource.method2100() - 1;
		this.gfxResource.method2108(local26);
	}

	@OriginalMember(owner = "client!qj", name = "a", descriptor = "(IZ)V")
	public void method4417(@OriginalArg(0) int arg0) {
		this.featureMask = arg0;
		@Pc(9) LRUCache local9 = this.aClass68_37;
		synchronized (this.aClass68_37) {
			this.aClass68_37.method1777();
		}
	}

	@OriginalMember(owner = "client!qj", name = "a", descriptor = "(II)V")
	public void method4418() {
		@Pc(6) LRUCache local6 = this.aClass68_36;
		synchronized (this.aClass68_36) {
			this.aClass68_36.method1788(5);
		}
		local6 = this.aClass68_37;
		synchronized (this.aClass68_37) {
			this.aClass68_37.method1788(5);
		}
	}

	@OriginalMember(owner = "client!qj", name = "a", descriptor = "(I)V")
	public void method4419() {
		@Pc(2) LRUCache local2 = this.aClass68_36;
		synchronized (this.aClass68_36) {
			this.aClass68_36.method1791();
		}
		local2 = this.aClass68_37;
		synchronized (this.aClass68_37) {
			this.aClass68_37.method1791();
		}
	}

	@OriginalMember(owner = "client!qj", name = "b", descriptor = "(II)Lclient!nm;")
	public SpotAnimationType method4421(@OriginalArg(1) int arg0) {
		@Pc(12) LRUCache local12 = this.aClass68_36;
		@Pc(22) SpotAnimationType local22;
		synchronized (this.aClass68_36) {
			local22 = (SpotAnimationType) this.aClass68_36.method1787((long) arg0);
		}
		if (local22 != null) {
			return local22;
		}
		@Pc(43) byte[] local43 = this.gfxResource.method2104(Packet.method2530(arg0), NativeWaterTexture.method3528(arg0));
		local22 = new SpotAnimationType();
		local22.currentId = arg0;
		local22.gfxTypeList = this;
		if (local43 != null) {
			local22.method3831(new Packet(local43));
		}
		@Pc(65) LRUCache local65 = this.aClass68_36;
		synchronized (this.aClass68_36) {
			this.aClass68_36.method1779((long) arg0, local22);
			return local22;
		}
	}

	@OriginalMember(owner = "client!qj", name = "a", descriptor = "(Z)V")
	public void method4423() {
		@Pc(6) LRUCache local6 = this.aClass68_36;
		synchronized (this.aClass68_36) {
			this.aClass68_36.method1777();
		}
		local6 = this.aClass68_37;
		synchronized (this.aClass68_37) {
			this.aClass68_37.method1777();
		}
	}

@OriginalMember(owner = "client!qj", name = "m", descriptor = "J")
	public static long aLong189;
@OriginalMember(owner = "client!qj", name = "k", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_76 = new Class215(15, -1);
@OriginalMember(owner = "client!qj", name = "n", descriptor = "Ljava/util/zip/CRC32;")
	public static final CRC32 aCRC32_1 = new CRC32();
@OriginalMember(owner = "client!qj", name = "p", descriptor = "I")
	public static int anInt5675 = -1;
@OriginalMember(owner = "client!qj", name = "a", descriptor = "(BZ[B)Ljava/lang/Object;")
	public static Object method4416(@OriginalArg(2) byte[] arg0) {
		if (arg0 == null) {
			return null;
		}
		if (arg0.length > 136 && !TextureOperation.aBoolean664) {
			try {
				@Pc(25) ByteArrayWrapper local25 = (ByteArrayWrapper) Class.forName("com.jagex.game.runetek5.core.io.ByteBufferArrayWrapper").getDeclaredConstructor().newInstance();
				local25.set(arg0);
				return local25;
			} catch (@Pc(34) Throwable local34) {
				TextureOperation.aBoolean664 = true;
			}
		}
		return arg0;
	}
@OriginalMember(owner = "client!qj", name = "a", descriptor = "(BZ)V")
	public static void method4422(@OriginalArg(1) boolean arg0) {
		Static395.method5164(arg0, EffectiveVertex.anInt5346, EnumType.anInt5766, GpuPointLight.anInt2881);
	}
}
