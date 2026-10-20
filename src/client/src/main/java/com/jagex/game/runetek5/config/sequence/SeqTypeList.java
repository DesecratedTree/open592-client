package com.jagex.game.runetek5.config.sequence;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub17;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import java.awt.Canvas;
import com.jagex.game.runetek5.deob.Class4_Sub17;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static148;
import com.jagex.game.runetek5.deob.Static209;
import com.jagex.game.runetek5.config.sequence.*;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.deob.OutputStream_Sub1;
import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.deob.TextureCombinerOp;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.FixedFunctionSpecularPass;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub1;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import com.jagex.game.runetek5.graphics.texture.OpenGl2DImageTexture;
import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.deob.Class85;
import com.jagex.game.runetek5.deob.Class225;
@OriginalClass("client!du")
public final class SeqTypeList {

	@OriginalMember(owner = "client!du", name = "c", descriptor = "Lclient!fa;")
	private final LRUCache aClass68_5 = new LRUCache(64);

	@OriginalMember(owner = "client!du", name = "l", descriptor = "Lclient!fa;")
	private final LRUCache aClass68_6 = new LRUCache(100);

	@OriginalMember(owner = "client!du", name = "g", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider aClass76_21;

	@OriginalMember(owner = "client!du", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;Lclient!fs;Lclient!fs;)V")
	public SeqTypeList(@OriginalArg(0) Game arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Js5ResourceProvider arg2, @OriginalArg(3) Js5ResourceProvider arg3, @OriginalArg(4) Js5ResourceProvider arg4) {
		this.aClass76_21 = arg2;
		if (this.aClass76_21 != null) {
			@Pc(26) int local26 = this.aClass76_21.method2100() - 1;
			this.aClass76_21.method2108(local26);
		}
		FixedFunctionSpecularPass.method4234(arg3, arg4);
	}

	@OriginalMember(owner = "client!du", name = "a", descriptor = "(II)Lclient!pt;")
	public Class4_Sub1_Sub17 method1391(@OriginalArg(0) int arg0) {
		@Pc(6) LRUCache local6 = this.aClass68_6;
		@Pc(22) Class4_Sub1_Sub17 local22;
		synchronized (this.aClass68_6) {
			local22 = (Class4_Sub1_Sub17) this.aClass68_6.method1787((long) arg0);
			if (local22 == null) {
				local22 = new Class4_Sub1_Sub17(arg0);
				this.aClass68_6.method1779((long) arg0, local22);
			}
		}
		synchronized (local22) {
			return local22.method4211() ? local22 : null;
		}
	}

	@OriginalMember(owner = "client!du", name = "a", descriptor = "(I)V")
	public void method1392() {
		@Pc(2) LRUCache local2 = this.aClass68_5;
		synchronized (this.aClass68_5) {
			this.aClass68_5.method1791();
		}
		local2 = this.aClass68_6;
		synchronized (this.aClass68_6) {
			this.aClass68_6.method1791();
		}
	}

	@OriginalMember(owner = "client!du", name = "b", descriptor = "(II)V")
	public void method1394() {
		@Pc(10) LRUCache local10 = this.aClass68_5;
		synchronized (this.aClass68_5) {
			this.aClass68_5.method1788(5);
		}
		local10 = this.aClass68_6;
		synchronized (this.aClass68_6) {
			this.aClass68_6.method1788(5);
		}
	}

	@OriginalMember(owner = "client!du", name = "b", descriptor = "(I)V")
	public void method1396() {
		@Pc(6) LRUCache local6 = this.aClass68_5;
		synchronized (this.aClass68_5) {
			this.aClass68_5.method1777();
		}
		local6 = this.aClass68_6;
		synchronized (this.aClass68_6) {
			this.aClass68_6.method1777();
		}
	}

	@OriginalMember(owner = "client!du", name = "c", descriptor = "(II)Lclient!lp;")
	public SeqType method1397(@OriginalArg(1) int arg0) {
		@Pc(6) LRUCache local6 = this.aClass68_5;
		@Pc(16) SeqType local16;
		synchronized (this.aClass68_5) {
			local16 = (SeqType) this.aClass68_5.method1787((long) arg0);
		}
		if (local16 != null) {
			return local16;
		}
		@Pc(37) byte[] local37 = this.aClass76_21.method2104(OutputStream_Sub1.method2266(arg0), Class85.method2318(arg0));
		local16 = new SeqType();
		local16.id = arg0;
		local16.seqtypelist = this;
		if (local37 != null) {
			local16.method3455(new Packet(local37));
		}
		local16.postDecode();
		@Pc(68) LRUCache local68 = this.aClass68_5;
		synchronized (this.aClass68_5) {
			this.aClass68_5.method1779((long) arg0, local16);
			return local16;
		}
	}

@OriginalMember(owner = "client!du", name = "e", descriptor = "[[[B")
	public static byte[][][] aByteArrayArrayArray2;
@OriginalMember(owner = "client!du", name = "a", descriptor = "(IIIIB)V")
	public static void method1395(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		@Pc(9) float local9 = (float) Static148.anInt5733 / (float) Static148.anInt5729;
		@Pc(11) int local11 = arg0;
		@Pc(13) int local13 = arg2;
		if (local9 < 1.0F) {
			local13 = (int) ((float) arg0 * local9);
		} else {
			local11 = (int) ((float) arg2 / local9);
		}
		@Pc(40) int local40 = arg3 - (arg2 - local13) / 2;
		@Pc(49) int local49 = arg1 - (arg0 - local11) / 2;
		OutputStream_Sub1.anInt2674 = -1;
		TextureCombinerOp.anInt5156 = Static148.anInt5733 - local40 * Static148.anInt5733 / local13;
		OpenGl2DImageTexture.anInt5471 = local49 * Static148.anInt5729 / local11;
		Class9.anInt6920 = -1;
		JavaMatrix.method5160();
	}
@OriginalMember(owner = "client!du", name = "a", descriptor = "(Ljava/lang/String;II)V")
	public static void method1398(@OriginalArg(0) String arg0, @OriginalArg(1) int arg1) {
		@Pc(8) Class4_Sub1_Sub11 local8 = NPCTypeList.method5222(arg1, 3);
		local8.method2604();
		local8.aString32 = arg0;
	}
@OriginalMember(owner = "client!du", name = "a", descriptor = "(III)V")
	public static void method1399(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		@Pc(13) Class4_Sub1_Sub11 local13 = NPCTypeList.method5222(arg1, 16);
		local13.method2604();
		local13.anInt3083 = arg0;
	}
@OriginalMember(owner = "client!du", name = "a", descriptor = "(Lclient!ya;Z)V")
	public static void method1400(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		if (Class225.aClass183_45.method4148() == 0) {
			return;
		}
		@Pc(58) Class4_Sub17 local58;
		if (Packet.anInt2973 == 0) {
			for (local58 = (Class4_Sub17) Class225.aClass183_45.method4140(); local58 != null; local58 = (Class4_Sub17) Class225.aClass183_45.method4144()) {
				Terrain.aClass206_3.method4699(false, local58.aBoolean222 ? Static1.aClass16_Sub1_Sub5_Sub1_1.playerModel : null, Ground.aClass46_12, local58.anInt2372, local58.anInt2367, arg0, false, local58.anInt2368, local58.anInt2366, local58.anInt2373, arg0);
				local58.method5684();
			}
			ModeWhere.method4940();
			return;
		}
		if (CacheableNode.aClass19_34 == null) {
			@Pc(28) Canvas local28 = new Canvas();
			local28.setSize(36, 32);
			CacheableNode.aClass19_34 = Toolkit.method4241(MidiPcmStream.anInterface7_3, 0, local28, 0, Class126.aClass66_4);
			Class16_Sub1_Sub1.aClass46_4 = CacheableNode.aClass19_34.createFontRenderer(ParticleList.method3546(Packet.anInt2985, Static209.aClass76_48), SpriteType.decodeGroup(GlGround.aClass76_60, Packet.anInt2985));
		}
		for (local58 = (Class4_Sub17) Class225.aClass183_45.method4140(); local58 != null; local58 = (Class4_Sub17) Class225.aClass183_45.method4144()) {
			Terrain.aClass206_3.method4699(false, local58.aBoolean222 ? Static1.aClass16_Sub1_Sub5_Sub1_1.playerModel : null, Class16_Sub1_Sub1.aClass46_4, local58.anInt2372, local58.anInt2367, CacheableNode.aClass19_34, false, local58.anInt2368, local58.anInt2366, local58.anInt2373, arg0);
			local58.method5684();
		}
	}
}
