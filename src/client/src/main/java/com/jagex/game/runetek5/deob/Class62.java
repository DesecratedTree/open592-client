package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.config.quickchat.QuickChatCategoryType;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.graphics.map.worldmap.WorldMapElement;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.SpotAnimation;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub8_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.config.enumtype.EnumType;
import com.jagex.game.runetek5.QuickChatPhrase;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.deob.Class95_Sub3;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.js5.request.FileWorker;
import com.jagex.game.runetek5.core.io.DummyInputStream;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTiledSprite;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.deob.Class145_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub3;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.deob.Class16_Sub1;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.deob.Class149;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub26;
import com.jagex.game.runetek5.deob.ObjStackEntity;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.OpenGlArbElementArrayBuffer;
import com.jagex.game.runetek5.EnvironmentContext;
import com.jagex.game.runetek5.deob.TileMessage;
import com.jagex.game.runetek5.deob.Class16_Sub2_Sub1;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpConstant;
@OriginalClass("client!ek")
public final class Class62 {

	@OriginalMember(owner = "client!ek", name = "g", descriptor = "Lclient!fa;")
	private final LRUCache aClass68_8 = new LRUCache(64);

	@OriginalMember(owner = "client!ek", name = "e", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider aClass76_25;

	@OriginalMember(owner = "client!ek", name = "d", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider aClass76_24;

	@OriginalMember(owner = "client!ek", name = "<init>", descriptor = "(ILclient!fs;Lclient!fs;)V")
	public Class62(@OriginalArg(0) int arg0, @OriginalArg(1) Js5ResourceProvider arg1, @OriginalArg(2) Js5ResourceProvider arg2) {
		this.aClass76_25 = arg1;
		this.aClass76_24 = arg2;
		if (this.aClass76_25 != null) {
			this.aClass76_25.method2108(0);
		}
		if (this.aClass76_24 != null) {
			this.aClass76_24.method2108(0);
		}
	}

	@OriginalMember(owner = "client!ek", name = "a", descriptor = "(IB)Lclient!np;")
	public QuickChatCategoryType method1607(@OriginalArg(0) int arg0) {
		@Pc(19) QuickChatCategoryType local19 = (QuickChatCategoryType) this.aClass68_8.method1787((long) arg0);
		if (local19 != null) {
			return local19;
		}
		@Pc(33) byte[] local33;
		if (arg0 < 32768) {
			local33 = this.aClass76_25.method2104(arg0, 0);
		} else {
			local33 = this.aClass76_24.method2104(arg0 & 0x7FFF, 0);
		}
		local19 = new QuickChatCategoryType();
		if (local33 != null) {
			local19.method3873(new Packet(local33));
		}
		if (arg0 >= 32768) {
			local19.method3870();
		}
		this.aClass68_8.method1779((long) arg0, local19);
		return local19;
	}

@OriginalMember(owner = "client!ek", name = "a", descriptor = "F")
	public static float aFloat49;
@OriginalMember(owner = "client!ek", name = "a", descriptor = "(ZLclient!vj;ZI)V")
	public static void method1606(@OriginalArg(0) boolean arg0, @OriginalArg(1) Class4_Sub43 arg1, @OriginalArg(2) boolean arg2) {
		@Pc(8) int local8 = arg1.anInt6979;
		@Pc(12) int local12 = (int) arg1.aLong224;
		arg1.method5684();
		if (arg0) {
			EnvironmentContext.method4602(local8);
		}
		Class145_Sub2.method5629(local8);
		@Pc(27) com.jagex.game.runetek5.config.rsinterface.Component local27 = ParticleBinaryTree.method5121(local12);
		if (local27 != null) {
			Class4_Sub12_Sub1.method1142(local27);
		}
		Static214.method3224();
		if (!arg2 && EnumType.anInt5766 != -1) {
			Class4_Sub37.method4165(EnumType.anInt5766, 1);
		}
		@Pc(58) Class25 local58 = new Class25(MusicPatch.aClass102_29);
		for (@Pc(63) Class4_Sub43 local63 = (Class4_Sub43) local58.method641(); local63 != null; local63 = (Class4_Sub43) local58.method640()) {
			if (!local63.method5683()) {
				local63 = (Class4_Sub43) local58.method641();
				if (local63 == null) {
					return;
				}
			}
			if (local63.anInt6981 == 3) {
				@Pc(85) int local85 = (int) local63.aLong224;
				if (local85 >>> 16 == local8) {
					method1606(true, local63, arg2);
				}
			}
		}
	}
@OriginalMember(owner = "client!ek", name = "a", descriptor = "(II)V")
	public static void method1608(@OriginalArg(0) int arg0) {
		@Pc(8) Class4_Sub1_Sub11 local8 = NPCTypeList.method5222(arg0, 3);
		local8.method2608();
	}
@OriginalMember(owner = "client!ek", name = "a", descriptor = "(IILclient!fs;I)Lclient!cn;")
	public static WorldMapElement method1609(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Js5ResourceProvider arg2) {
		@Pc(16) Packet local16 = new Packet(arg2.method2104(arg1, arg0));
		@Pc(49) WorldMapElement local49 = new WorldMapElement(arg1, local16.method2537(), local16.method2537(), local16.method2529(), local16.method2529(), local16.method2490() == 1, local16.method2490(), local16.method2490());
		@Pc(53) int local53 = local16.method2490();
		for (@Pc(55) int local55 = 0; local55 < local53; local55++) {
			local49.worldMapPlacements.method4137(new Class4_Sub41(local16.method2490(), local16.method2536(), local16.method2536(), local16.method2536(), local16.method2536(), local16.method2536(), local16.method2536(), local16.method2536(), local16.method2536()));
		}
		local49.method933();
		return local49;
	}
@OriginalMember(owner = "client!ek", name = "a", descriptor = "(Lclient!ya;IILclient!c;III[[[B[I[I[I[I[IIBIIZ)V")
	public static void method1610(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) Ground arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) byte[][][] arg7, @OriginalArg(8) int[] arg8, @OriginalArg(9) int[] arg9, @OriginalArg(10) int[] arg10, @OriginalArg(11) int[] arg11, @OriginalArg(12) int[] arg12, @OriginalArg(13) int arg13, @OriginalArg(14) byte arg14, @OriginalArg(15) int arg15, @OriginalArg(16) int arg16, @OriginalArg(17) boolean arg17) {
		Class95_Sub3.aClass19_37 = arg0;
		GLBillowTextureData.anInt5665 = arg1;
		MECType.aClass31_3 = arg3;
		Class4_Sub6_Sub26.aBoolean470 = Class95_Sub3.aClass19_37.getMaxLights() > 0;
		QuickChatPhrase.anInt7073 = arg4 >> Static231.anInt4434;
		OpenGlArbElementArrayBuffer.anInt1887 = arg6 >> Static231.anInt4434;
		Class4_Sub1_Sub11.anInt3073 = arg4;
		Static209.anInt3921 = arg6;
		TextureOpConstant.anInt4098 = arg5;
		Static105.anInt2325 = QuickChatPhrase.anInt7073 - FileOnDisk.anInt4431;
		if (Static105.anInt2325 < 0) {
			TileMessage.anInt7371 = -Static105.anInt2325;
			Static105.anInt2325 = 0;
		} else {
			TileMessage.anInt7371 = 0;
		}
		Class149.anInt4530 = OpenGlArbElementArrayBuffer.anInt1887 - FileOnDisk.anInt4431;
		if (Class149.anInt4530 < 0) {
			TextureOpTiledSprite.anInt1088 = -Class149.anInt4530;
			Class149.anInt4530 = 0;
		} else {
			TextureOpTiledSprite.anInt1088 = 0;
		}
		Class30.anInt775 = QuickChatPhrase.anInt7073 + FileOnDisk.anInt4431;
		if (Class30.anInt775 > GpuFaceNormal.menuOpenWidth) {
			Class30.anInt775 = GpuFaceNormal.menuOpenWidth;
		}
		Class4_Sub1_Sub11.anInt3087 = OpenGlArbElementArrayBuffer.anInt1887 + FileOnDisk.anInt4431;
		if (Class4_Sub1_Sub11.anInt3087 > GlModel.anInt3361) {
			Class4_Sub1_Sub11.anInt3087 = GlModel.anInt3361;
		}
		@Pc(77) int local77;
		@Pc(84) int local84;
		@Pc(90) int local90;
		for (@Pc(74) int local74 = 0; local74 < FileOnDisk.anInt4431 + FileOnDisk.anInt4431 + 2; local74++) {
			for (local77 = 0; local77 < FileOnDisk.anInt4431 + FileOnDisk.anInt4431 + 2; local77++) {
				local84 = QuickChatPhrase.anInt7073 + local74 - FileOnDisk.anInt4431;
				local90 = OpenGlArbElementArrayBuffer.anInt1887 + local77 - FileOnDisk.anInt4431;
				if (local84 >= 0 && local90 >= 0 && local84 < GpuFaceNormal.menuOpenWidth && local90 < GlModel.anInt3361) {
					@Pc(104) int local104 = local84 << Static231.anInt4434;
					@Pc(108) int local108 = local90 << Static231.anInt4434;
					@Pc(124) int local124 = Class4_Sub6_Sub4.aClass65Array1[Class4_Sub6_Sub4.aClass65Array1.length - 1].l(local84, local90) - (0x3E8 << Static231.anInt4434 - 7);
					@Pc(144) int local144 = FileWorker.aClass65Array3 == null ? Class4_Sub6_Sub4.aClass65Array1[0].l(local84, local90) + CacheableNode.anInt5935 : FileWorker.aClass65Array3[0].l(local84, local90) + CacheableNode.anInt5935;
					LocTypeList.aBooleanArrayArray6[local74][local77] = Class95_Sub3.aClass19_37.drawScaledColored(local104, local124, local108, local104, local144, local108);
				} else {
					LocTypeList.aBooleanArrayArray6[local74][local77] = false;
				}
			}
		}
		for (local77 = 0; local77 < FileOnDisk.anInt4431 + FileOnDisk.anInt4431 + 1; local77++) {
			for (local84 = 0; local84 < FileOnDisk.anInt4431 + FileOnDisk.anInt4431 + 1; local84++) {
				GlVboArrayBuffer.aBooleanArrayArray4[local77][local84] = LocTypeList.aBooleanArrayArray6[local77][local84] || LocTypeList.aBooleanArrayArray6[local77 + 1][local84] || LocTypeList.aBooleanArrayArray6[local77][local84 + 1] || LocTypeList.aBooleanArrayArray6[local77 + 1][local84 + 1];
			}
		}
		Class16_Sub2_Sub1.anIntArray34 = arg8;
		DummyInputStream.anIntArray303 = arg9;
		TextureOpColorGradient.anIntArray143 = arg10;
		SpotAnimation.anIntArray106 = arg11;
		PlayerEntity.anIntArray284 = arg12;
		Toolkit.method4292();
		Class4_Sub1_Sub8_Sub1.method3772();
		for (@Pc(260) Class3_Sub3 local260 = (Class3_Sub3) Class4_Sub3.aClass84_2.method2312(); local260 != null; local260 = (Class3_Sub3) Class4_Sub3.aClass84_2.method2309()) {
			local260.method5676();
			ObjStackEntity.method727(local260);
		}
		if (Class4_Sub6_Sub26.aBoolean470) {
			for (local90 = 0; local90 < Ground.anInt7322; local90++) {
				StructTypeList.aClass191Array1[local90].method4332(arg1, arg17);
			}
		}
		if (arg2 > 1) {
			Class95_Sub3.aClass19_37.method4303(0);
			if (Static433.aClass70_1 == null || Static433.aClass70_1 instanceof Class70_Sub2) {
				Static433.aClass70_1 = new Class70_Sub1();
			}
		} else if (Static433.aClass70_1 == null || Static433.aClass70_1 instanceof Class70_Sub1) {
			Static433.aClass70_1 = new Class70_Sub2();
		}
		Static433.aClass70_1.method5075(arg2);
		Static433.aClass70_1.method5074();
		if (Static2.aClass164ArrayArrayArray1 != null) {
			OpenGLTextureCache.method1547(true);
			Static433.aClass70_1.method5077(22);
			Class16_Sub1.method5344(arg2, null, 0, (byte) 0, arg15, arg16);
			Static433.aClass70_1.method5074();
			Static433.aClass70_1.method5077(23);
			OpenGLTextureCache.method1547(false);
		}
		Class16_Sub1.method5344(arg2, arg7, arg13, arg14, arg15, arg16);
		Static433.aClass70_1.method5074();
		Static433.aClass70_1.method5073();
		Static433.aClass70_1.method5074();
		if (arg2 > 1) {
			Class95_Sub3.aClass19_37.linkThreads(0);
		}
		Class95_Sub3.aClass19_37.copyLights(0, null);
	}
}
