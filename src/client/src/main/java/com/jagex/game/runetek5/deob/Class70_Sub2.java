package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.js5.request.local.LocalRequester;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.deob.Class4_Sub14;
import com.jagex.game.runetek5.QuickChatPhrase;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class95_Sub3;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.client.loading.loader.Js5GroupLoader;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.client.input.keyboard.KeyRecordEntry;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.js5.request.Js5ResourceRequest;
import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub26;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.OpenGlArbElementArrayBuffer;
import com.jagex.game.runetek5.cache.node.StringNode;
import com.jagex.game.runetek5.ByteArrayWrapper;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Class4_Sub21_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
@OriginalClass("client!tl")
public final class Class70_Sub2 extends Class70 {

	@OriginalMember(owner = "client!tl", name = "k", descriptor = "I")
	private int anInt6476 = 0;

	@OriginalMember(owner = "client!tl", name = "i", descriptor = "Lclient!jj;")
	private final Class120 aClass120_1 = new Class120();

	static {
		new Class83("You are temporarily banned from this clan channel.", "Du wurdest temporär aus diesem Chatraum verbannt.", "Vous êtes temporairement exclu de ce canal de clan.", "Você está temporariamente vetado de entrar nesse canal de clã.");
	}

	@OriginalMember(owner = "client!tl", name = "b", descriptor = "(I)V")
	@Override
	public void method5074() {
		while (super.anInt6471 != super.anInt6472) {
			this.method5083();
		}
	}

	@OriginalMember(owner = "client!tl", name = "d", descriptor = "(I)V")
	private void method5083() {
		@Pc(8) int local8 = super.anInt6471++;
		if (super.anInt6471 >= 5000) {
			super.anInt6471 = 0;
		}
		this.anInt6476 = super.aByteArray91[local8];
		@Pc(34) Object local34 = super.anObjectArray5[local8];
		super.anObjectArray5[local8] = null;
		if (this.anInt6476 == 21) {
			EffectiveVertex.method4101(this.aClass120_1, (Class164) local34);
		} else if (this.anInt6476 == 20) {
			@Pc(161) Class164 local161 = (Class164) local34;
			if (local161.aClass16_Sub5_3 != null) {
				local161.aClass16_Sub5_3.method5335(Class95_Sub3.aClass19_37);
			}
			if (local161.aClass16_Sub5_2 != null) {
				local161.aClass16_Sub5_2.method5335(Class95_Sub3.aClass19_37);
			}
			if (local161.aClass16_Sub2_2 != null) {
				local161.aClass16_Sub2_2.method5335(Class95_Sub3.aClass19_37);
			}
			if (local161.aClass16_Sub2_3 != null) {
				local161.aClass16_Sub2_3.method5335(Class95_Sub3.aClass19_37);
			}
			if (local161.aClass16_Sub4_2 != null) {
				local161.aClass16_Sub4_2.method5335(Class95_Sub3.aClass19_37);
			}
			for (@Pc(204) Class7 local204 = local161.aClass7_3; local204 != null; local204 = local204.aClass7_1) {
				local204.aClass16_Sub1_1.method5335(Class95_Sub3.aClass19_37);
			}
		} else if (this.anInt6476 >= 30 && this.anInt6476 <= 33) {
			Class95_Sub3.aClass19_37.g(3000.0F, super.aFloatArray29[local8] * 1.5F);
			((Class65) local34).method4029(QuickChatPhrase.anInt7073, OpenGlArbElementArrayBuffer.anInt1887, FileOnDisk.anInt4431, GlVboArrayBuffer.aBooleanArrayArray4, this.anInt6476 - 30 == 0);
		} else if (this.anInt6476 >= 40 && this.anInt6476 <= 43) {
			Class95_Sub3.aClass19_37.g(3000.0F, super.aFloatArray29[local8] * 1.5F);
			((Class65) local34).method4029(QuickChatPhrase.anInt7073, OpenGlArbElementArrayBuffer.anInt1887, FileOnDisk.anInt4431, LocTypeList.aBooleanArrayArray6, this.anInt6476 - 40 == 0);
		} else if (this.anInt6476 == 22) {
			Class95_Sub3.aClass19_37.AA(-1, 1583160, 40, 127);
		} else if (this.anInt6476 == 23) {
			Class95_Sub3.aClass19_37.l();
			return;
		} else if (this.anInt6476 == 24) {
			Class95_Sub3.aClass19_37.copyLights(0, null);
			return;
		}
	}

	@OriginalMember(owner = "client!tl", name = "a", descriptor = "(IZLclient!ta;FB)V")
	@Override
	public void method5079(@OriginalArg(0) int arg0, @OriginalArg(1) boolean arg1, @OriginalArg(2) Class65 arg2, @OriginalArg(3) float arg3) {
		super.aByteArray91[super.anInt6472] = (byte) (arg1 ? arg0 + 40 : arg0 + 30);
		super.anObjectArray5[super.anInt6472] = arg2;
		super.aFloatArray29[super.anInt6472] = arg3;
		super.anInt6472++;
		if (super.anInt6472 >= 5000) {
			super.anInt6472 = 0;
		}
	}

	@OriginalMember(owner = "client!tl", name = "a", descriptor = "(BI)V")
	@Override
	public void method5077(@OriginalArg(1) int arg0) {
		super.aByteArray91[super.anInt6472] = (byte) arg0;
		super.anInt6472++;
		if (super.anInt6472 >= 5000) {
			super.anInt6472 = 0;
		}
	}

	@OriginalMember(owner = "client!tl", name = "a", descriptor = "(Lclient!nt;B)V")
	@Override
	public void method5076(@OriginalArg(0) Class164 arg0) {
		super.aByteArray91[super.anInt6472] = 20;
		super.anObjectArray5[super.anInt6472] = arg0;
		super.anInt6472++;
		if (super.anInt6472 >= 5000) {
			super.anInt6472 = 0;
		}
	}

	@OriginalMember(owner = "client!tl", name = "a", descriptor = "(BLclient!nt;)V")
	@Override
	public void method5078(@OriginalArg(1) Class164 arg0) {
		super.anInt6471--;
		if (super.anInt6471 < 0) {
			super.anInt6471 = 4999;
		}
		super.aByteArray91[super.anInt6471] = 21;
		super.anObjectArray5[super.anInt6471] = arg0;
	}

	@OriginalMember(owner = "client!tl", name = "a", descriptor = "(Lclient!nt;I)V")
	@Override
	public void method5080(@OriginalArg(0) Class164 arg0) {
		super.aByteArray91[super.anInt6472] = 21;
		super.anObjectArray5[super.anInt6472] = arg0;
		super.anInt6472++;
		if (super.anInt6472 >= 5000) {
			super.anInt6472 = 0;
		}
	}

	@OriginalMember(owner = "client!tl", name = "a", descriptor = "(II)V")
	@Override
	public void method5075(@OriginalArg(0) int arg0) {
	}

	@OriginalMember(owner = "client!tl", name = "a", descriptor = "(I)V")
	@Override
	public void method5073() {
	}

@OriginalMember(owner = "client!tl", name = "w", descriptor = "Lclient!vl;")
	public static LocalRequester aClass254_3;
@OriginalMember(owner = "client!tl", name = "x", descriptor = "[Lclient!l;")
	public static NativeSprite[] aClass57Array16;
@OriginalMember(owner = "client!tl", name = "z", descriptor = "I")
	public static int anInt6487;
@OriginalMember(owner = "client!tl", name = "l", descriptor = "[S")
	public static final short[] aShortArray232 = new short[] { 58, 13, 10, 12, 5, 57, 17, 20 };
@OriginalMember(owner = "client!tl", name = "q", descriptor = "Z")
	public static boolean aBoolean591 = false;
@OriginalMember(owner = "client!tl", name = "c", descriptor = "(I)V")
	public static void method5082() {
		ByteArrayWrapper.aClass124_2.method3151();
		Class4_Sub6_Sub22.aClass192_2.method4343();
		MaterialType.aClass109_2.method2760();
		Static267.aClass262_2.method5557();
		Class196.aClass240_1.method5221();
		Terrain.aClass206_3.method4702();
		SceneLocEntity.aClass55_1.method1392();
		Js5MasterIndex.aClass194_2.method4419();
		KeyRecordEntry.aClass26_1.method649();
		Js5ResourceRequest.aClass223_1.method5003();
		Class133.aClass249_1.method5324();
		Class4_Sub14.aClass265_2.method5596();
		Class4_Sub6_Sub31.aClass182_4.method4132();
		LocalRequester.aClass208_1.method4722();
		Class4_Sub1_Sub11.aClass180_1.method4118();
		StringNode.aClass217_1.method4931();
		CacheableNodeByteArray.aClass226_1.method5022();
		NPCTypeList.aClass204_1.method4673();
		Js5ResourceProvider.aClass172_2.method3979();
		Class4_Sub6_Sub26.aClass72_1.method1987();
		CacheableNode.method4664();
		Class4_Sub6_Sub22.method3501();
		Js5GroupLoader.method4520();
		AbstractArbBufferGL.method5178();
		MECType.aClass68_27.method1791();
		Class4_Sub21_Sub2.aClass68_22.method1791();
		Class99_Sub1.aClass68_23.method1791();
		Static105.aClass68_9.method1791();
		Static271.aClass68_29.method1791();
	}
@OriginalMember(owner = "client!tl", name = "e", descriptor = "(I)V")
	public static void method5084() {
		@Pc(7) int local7 = 0;
		for (@Pc(14) int local14 = 0; local14 < GLBillowTextureData.anInt5666; local14++) {
			for (@Pc(18) int local18 = 0; local18 < Static283.anInt5187; local18++) {
				if (Class183.method4135(local14, true, local18, local7, Class70.aClass164ArrayArrayArray5)) {
					local7++;
				}
				if (local7 >= 512) {
					return;
				}
			}
		}
	}
}
