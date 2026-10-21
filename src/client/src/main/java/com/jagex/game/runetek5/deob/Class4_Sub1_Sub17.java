package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.config.sequence.frameset.AnimBase;
import com.jagex.game.runetek5.config.sequence.frameset.AnimFrame;
import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.unpack_Sub1;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteType;
import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.cache.node.GlMemoryPool;
import com.jagex.game.runetek5.deob.Class4_Sub14;
import com.jagex.game.runetek5.deob.SpotAnimation;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
import com.jagex.game.runetek5.deob.Class120;
import com.jagex.game.runetek5.deob.Class50;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub19;
import com.jagex.game.runetek5.client.cycle.FrameRateStats;
import com.jagex.game.runetek5.deob.TextureCombinerOp;
import com.jagex.game.runetek5.Js5TextureSource;
import com.jagex.game.runetek5.cache.node.lru.StructType;
import com.jagex.game.runetek5.toolkit.opengl.EffectManagerGL;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.WaterfallEffect;
import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.graphics.texture.OpenGl2DImageTexture;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub33;
import com.jagex.game.runetek5.toolkit.opengl.effect.ReflectiveRipplingWaterEffect;
@OriginalClass("client!pt")
public final class Class4_Sub1_Sub17 extends CacheableNode {

	@OriginalMember(owner = "client!pt", name = "D", descriptor = "[[B")
	private byte[][] aByteArrayArray24;

	@OriginalMember(owner = "client!pt", name = "F", descriptor = "[Lclient!gr;")
	public AnimFrame[] aClass88Array1;

	@OriginalMember(owner = "client!pt", name = "z", descriptor = "I")
	private final int anInt5488;

	@OriginalMember(owner = "client!pt", name = "<init>", descriptor = "(I)V")
	public Class4_Sub1_Sub17(@OriginalArg(0) int arg0) {
		this.anInt5488 = arg0;
	}

	@OriginalMember(owner = "client!pt", name = "a", descriptor = "(BI)Z")
	public boolean method4207(@OriginalArg(1) int arg0) {
		return this.aClass88Array1[arg0].aBoolean252;
	}

	@OriginalMember(owner = "client!pt", name = "b", descriptor = "(Z)Z")
	public boolean method4211() {
		if (this.aClass88Array1 != null) {
			return true;
		}
		@Pc(34) int local34;
		if (this.aByteArrayArray24 == null) {
			if (!GlMemoryPool.aClass76_1.method2101(this.anInt5488)) {
				return false;
			}
			@Pc(27) int[] local27 = GlMemoryPool.aClass76_1.method2124(this.anInt5488);
			this.aByteArrayArray24 = new byte[local27.length][];
			for (local34 = 0; local34 < local27.length; local34++) {
				this.aByteArrayArray24[local34] = GlMemoryPool.aClass76_1.method2104(local27[local34], this.anInt5488);
			}
		}
		@Pc(55) boolean local55 = true;
		@Pc(78) int local78;
		for (local34 = 0; local34 < this.aByteArrayArray24.length; local34++) {
			@Pc(64) byte[] local64 = this.aByteArrayArray24[local34];
			local78 = (local64[0] & 0xFF) << 8 | local64[1] & 0xFF;
			local55 &= EffectManagerGL.aClass76_96.method2125(local78);
		}
		if (!local55) {
			return false;
		}
		@Pc(103) Class183 local103 = new Class183();
		local78 = GlMemoryPool.aClass76_1.method2108(this.anInt5488);
		this.aClass88Array1 = new AnimFrame[local78];
		@Pc(119) int[] local119 = GlMemoryPool.aClass76_1.method2124(this.anInt5488);
		for (@Pc(121) int local121 = 0; local121 < local119.length; local121++) {
			@Pc(128) byte[] local128 = this.aByteArrayArray24[local121];
			@Pc(142) int local142 = local128[1] & 0xFF | (local128[0] & 0xFF) << 8;
			@Pc(144) AnimBase local144 = null;
			for (@Pc(149) AnimBase local149 = (AnimBase) local103.method4140(); local149 != null; local149 = (AnimBase) local103.method4144()) {
				if (local149.id == local142) {
					local144 = local149;
					break;
				}
			}
			if (local144 == null) {
				local144 = new AnimBase(local142, EffectManagerGL.aClass76_96.method2122(local142));
				local103.method4137(local144);
			}
			this.aClass88Array1[local119[local121]] = new AnimFrame(local128, local144);
		}
		this.aByteArrayArray24 = null;
		return true;
	}

	@OriginalMember(owner = "client!pt", name = "b", descriptor = "(II)Z")
	public boolean method4214(@OriginalArg(1) int arg0) {
		return this.aClass88Array1[arg0].modifiesAlpha;
	}

	@OriginalMember(owner = "client!pt", name = "a", descriptor = "(IB)Z")
	public boolean method4215(@OriginalArg(0) int arg0) {
		return this.aClass88Array1[arg0].aBoolean250;
	}

@OriginalMember(owner = "client!pt", name = "y", descriptor = "I")
	public static int anInt5487;
@OriginalMember(owner = "client!pt", name = "J", descriptor = "[Ljava/lang/String;")
	public static final String[] aStringArray26 = new String[100];
@OriginalMember(owner = "client!pt", name = "a", descriptor = "(IILclient!ga;Lclient!ya;IBI)V")
	public static void method4208(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) unpack_Sub1 arg2, @OriginalArg(3) com.jagex.game.runetek5.graphics.Toolkit arg3, @OriginalArg(4) int arg4, @OriginalArg(6) int arg5) {
		if (Class50.anInt3429 < 100) {
			FileOnDisk.method3465(arg2, arg3);
		}
		arg3.OA(arg0, arg5, arg1 + arg0, arg4 + arg5);
		@Pc(33) int local33;
		@Pc(52) int local52;
		if (Class50.anInt3429 < 100) {
			local33 = arg1 / 2 + arg0;
			arg3.O(arg0, arg5, arg1, arg4, -16777216, 0);
			local52 = arg5 + arg4 / 2 - 18 - 20;
			arg3.outlineRect(local33 - 152, local52, 304, 34, Class16_Sub2.aColorArray6[StructType.anInt5940].getRGB(), 0);
			arg3.O(local33 - 150, local52 + 2, Class50.anInt3429 * 3, 30, SpotAnimation.aColorArray3[StructType.anInt5940].getRGB(), 0);
			Static331.aClass46_10.method5056(local33, GlModel.aColorArray5[StructType.anInt5940].getRGB(), local52 + 20, -1, Class4_Sub6_Sub7.aClass83_38.method2267(NativeBillboardFace.anInt3423));
			return;
		}
		@Pc(108) int local108 = OpenGl2DImageTexture.anInt5471 - (int) ((float) arg1 / Static148.mapZoom);
		local33 = TextureCombinerOp.anInt5156 + (int) ((float) arg4 / Static148.mapZoom);
		local52 = (int) ((float) arg1 / Static148.mapZoom) + OpenGl2DImageTexture.anInt5471;
		Static384.anInt6470 = (int) ((float) (arg4 * 2) / Static148.mapZoom);
		@Pc(146) int local146 = TextureCombinerOp.anInt5156 - (int) ((float) arg4 / Static148.mapZoom);
		Toolkit.mapCenterChunkX = TextureCombinerOp.anInt5156 - (int) ((float) arg4 / Static148.mapZoom);
		Static17.anInt220 = (int) ((float) (arg1 * 2) / Static148.mapZoom);
		Class120.anInt5352 = OpenGl2DImageTexture.anInt5471 - (int) ((float) arg1 / Static148.mapZoom);
		Static148.setView(local108 + Static148.anInt5732, local33 + Static148.anInt5724, Static148.anInt5732 + local52, Static148.anInt5724 + local146, arg0, arg5, arg0 + arg1, arg4 + arg5 - -1);
		Static148.drawTiles(arg3);
		@Pc(201) Class183 local201 = Static148.positionElements(arg3);
		Class4_Sub6_Sub33.method5042(local201, arg3);
		if (Js5TextureSource.anInt2835 > 0) {
			WaterfallEffect.anInt5136--;
			if (WaterfallEffect.anInt5136 == 0) {
				WaterfallEffect.anInt5136 = 20;
				Js5TextureSource.anInt2835--;
			}
		}
		if (!MusicPatch.aBoolean507) {
			return;
		}
		@Pc(232) int local232 = arg1 + arg0 - 5;
		@Pc(238) int local238 = arg4 + arg5 - 8;
		Class6.aClass46_9.method5059(FrameRateStats.getOverlayText(), local238, 16776960, local232);
		@Pc(253) int local253 = local238 - 15;
		@Pc(255) Runtime local255 = Runtime.getRuntime();
		@Pc(265) int local265 = (int) ((local255.totalMemory() - local255.freeMemory()) / 1024L);
		@Pc(267) int local267 = 16776960;
		if (local265 > 65536) {
			local267 = 16711680;
		}
		Class6.aClass46_9.method5059("Mem:" + local265 + "k", local253, local267, local232);
		local238 = local253 - 15;
	}
@OriginalMember(owner = "client!pt", name = "a", descriptor = "(ILclient!f;)Z")
	public static boolean method4210(@OriginalArg(1) GameObject arg0) {
		@Pc(15) LocType local15 = Static267.aClass262_2.method5560(arg0.method5350());
		if (local15.anInt5803 == -1) {
			return true;
		} else {
			@Pc(28) MapSpriteType local28 = Class4_Sub14.aClass265_2.method5588(local15.anInt5803);
			return local28.fileId == -1 ? true : local28.method941();
		}
	}
@OriginalMember(owner = "client!pt", name = "a", descriptor = "(ZII)I")
	public static int method4212(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		return arg0 == 1 || arg0 == 3 ? ReflectiveRipplingWaterEffect.anIntArray10[arg1 & 0x3] : Class183.anIntArray359[arg1 & 0x3];
	}
@OriginalMember(owner = "client!pt", name = "a", descriptor = "(ILclient!fs;I)Lclient!gt;")
	public static com.jagex.game.runetek5.graphics.font.FontMetrics method4213(@OriginalArg(0) int arg0, @OriginalArg(1) Js5ResourceProvider arg1) {
		@Pc(8) byte[] local8 = arg1.method2122(arg0);
		return local8 == null ? null : new com.jagex.game.runetek5.graphics.font.FontMetrics(local8);
	}
}
