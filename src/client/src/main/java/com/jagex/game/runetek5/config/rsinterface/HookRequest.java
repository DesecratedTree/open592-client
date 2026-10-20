package com.jagex.game.runetek5.config.rsinterface;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

import java.awt.Container;
import java.awt.Insets;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.staticelements.StaticElements;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static17;
import com.jagex.game.runetek5.sign.SignedResource;
import com.jagex.game.runetek5.config.rsinterface.*;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.config.objtype.ObjTypeList;
import com.jagex.game.runetek5.deob.Class120_Sub1;
import com.jagex.game.runetek5.config.enumtype.EnumType;
import com.jagex.game.runetek5.graphics.texture.water.BillowNoiseGenerator;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageTypeList;
import com.jagex.game.runetek5.toolkit.opengl.effect.BreakingWaterEffect;
import com.jagex.game.runetek5.cache.node.HashTable;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class7;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
import com.jagex.game.runetek5.deob.Class210;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.deob.Class267;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationTypeList;
import com.jagex.game.runetek5.toolkit.vertexdeclaration.VertexDeclarationComponent;
import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub37;
import com.jagex.game.runetek5.deob.ObjStackEntity;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.graphics.model.normal.ModelNormalsGL;
import com.jagex.game.runetek5.deob.Class222;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
@OriginalClass("client!op")
public final class HookRequest extends Country {

	@OriginalMember(owner = "client!op", name = "l", descriptor = "Z")
	public boolean hasMousePosition;

	@OriginalMember(owner = "client!op", name = "m", descriptor = "Ljava/lang/String;")
	public String aString50;

	@OriginalMember(owner = "client!op", name = "q", descriptor = "Lclient!uu;")
	public Component targetComponent;

	@OriginalMember(owner = "client!op", name = "s", descriptor = "I")
	public int id;

	@OriginalMember(owner = "client!op", name = "t", descriptor = "I")
	public int anInt5250;

	@OriginalMember(owner = "client!op", name = "u", descriptor = "I")
	public int anInt5251;

	@OriginalMember(owner = "client!op", name = "v", descriptor = "Lclient!uu;")
	public Component source;

	@OriginalMember(owner = "client!op", name = "w", descriptor = "I")
	public int anInt5252;

	@OriginalMember(owner = "client!op", name = "y", descriptor = "[Ljava/lang/Object;")
	public Object[] params;

	@OriginalMember(owner = "client!op", name = "z", descriptor = "I")
	public int typedKeyCode;

	@OriginalMember(owner = "client!op", name = "A", descriptor = "I")
	public int anInt5254;

@OriginalMember(owner = "client!op", name = "n", descriptor = "Lclient!qk;")
	public static StaticElements aClass195_2;
@OriginalMember(owner = "client!op", name = "p", descriptor = "Lclient!pk;")
	public static final Class183 aClass183_36 = new Class183();
@OriginalMember(owner = "client!op", name = "r", descriptor = "J")
	public static long aLong177 = -1L;
@OriginalMember(owner = "client!op", name = "x", descriptor = "Lclient!qt;")
	public static SignedResource aClass199_8 = null;
@OriginalMember(owner = "client!op", name = "B", descriptor = "[I")
	public static final int[] anIntArray343 = new int[] { 1, 2, 4, 8 };
@OriginalMember(owner = "client!op", name = "C", descriptor = "I")
	public static int anInt5255 = 0;
@OriginalMember(owner = "client!op", name = "D", descriptor = "[I")
	public static final int[] anIntArray344 = new int[] { 19, 55, 38, 155, 255, 110, 137, 205, 76 };
@OriginalMember(owner = "client!op", name = "a", descriptor = "(IIBIZI)V")
	public static void method4020(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) int arg4) {
		if (BillowNoiseGenerator.aFrame3 != null && (arg0 != 3 || arg2 != TextureOpBrightness.aClass50_Sub1_1.anInt3431 || TextureOpBrightness.aClass50_Sub1_1.anInt3450 != arg4)) {
			Class267.method5620(Class126.aClass66_4, BillowNoiseGenerator.aFrame3);
			BillowNoiseGenerator.aFrame3 = null;
		}
		if (arg0 == 3 && BillowNoiseGenerator.aFrame3 == null) {
			BillowNoiseGenerator.aFrame3 = Class7.method86(arg2, Class126.aClass66_4, arg4, 0);
			if (BillowNoiseGenerator.aFrame3 != null) {
				TextureOpBrightness.aClass50_Sub1_1.anInt3431 = arg2;
				TextureOpBrightness.aClass50_Sub1_1.anInt3450 = arg4;
				TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
			}
		}
		if (arg0 == 3 && BillowNoiseGenerator.aFrame3 == null) {
			method4020(TextureOpBrightness.aClass50_Sub1_1.anInt3447, arg1, -1, true, -1);
			return;
		}
		@Pc(78) Container local78;
		@Pc(91) Insets local91;
		if (BillowNoiseGenerator.aFrame3 != null) {
			Static17.anInt222 = arg4;
			local78 = BillowNoiseGenerator.aFrame3;
			Class4_Sub6_Sub37.anInt7000 = arg2;
		} else if (ModelNormalsGL.aFrame1 == null) {
			local78 = Class126.aClass66_4.anApplet1;
			Class4_Sub6_Sub37.anInt7000 = local78.getSize().width;
			Static17.anInt222 = local78.getSize().height;
		} else {
			local91 = ModelNormalsGL.aFrame1.getInsets();
			Class4_Sub6_Sub37.anInt7000 = ModelNormalsGL.aFrame1.getSize().width - local91.left - local91.right;
			@Pc(108) int local108 = -local91.bottom;
			Static17.anInt222 = ModelNormalsGL.aFrame1.getSize().height + local108 - local91.top;
			local78 = ModelNormalsGL.aFrame1;
		}
		@Pc(166) int local166;
		if (arg0 == 1) {
			Class120_Sub1.anInt5363 = 0;
			EffectiveVertex.anInt5346 = HashTable.anInt3148;
			FileOnDisk.anInt4424 = (Class4_Sub6_Sub37.anInt7000 - Class210.anInt6121) / 2;
			GpuPointLight.anInt2881 = Class210.anInt6121;
		} else if (BreakingWaterEffect.anInt1503 < 96 && Packet.anInt2973 == 0) {
			local166 = Class4_Sub6_Sub37.anInt7000 > 1024 ? 1024 : Class4_Sub6_Sub37.anInt7000;
			@Pc(173) int local173 = Static17.anInt222 > 768 ? 768 : Static17.anInt222;
			FileOnDisk.anInt4424 = (Class4_Sub6_Sub37.anInt7000 - local166) / 2;
			GpuPointLight.anInt2881 = local166;
			EffectiveVertex.anInt5346 = local173;
			Class120_Sub1.anInt5363 = 0;
		} else {
			GpuPointLight.anInt2881 = Class4_Sub6_Sub37.anInt7000;
			Class120_Sub1.anInt5363 = 0;
			EffectiveVertex.anInt5346 = Static17.anInt222;
			FileOnDisk.anInt4424 = 0;
		}
		if (GlToolkit.aClass219_2 != Class222.aClass219_6) {
			@Pc(196) boolean local196;
			if (GpuPointLight.anInt2881 < 1024 && EffectiveVertex.anInt5346 < 768) {
				local196 = true;
			} else {
				local196 = false;
			}
		}
		if (arg3) {
			ObjTypeList.method4711();
		} else {
			Class164.aCanvas5.setSize(GpuPointLight.anInt2881, EffectiveVertex.anInt5346);
			MonoStereoData.aClass19_16.releaseSurface(Class164.aCanvas5);
			if (local78 == ModelNormalsGL.aFrame1) {
				local91 = ModelNormalsGL.aFrame1.getInsets();
				Class164.aCanvas5.setLocation(local91.left + FileOnDisk.anInt4424, local91.top - -Class120_Sub1.anInt5363);
			} else {
				Class164.aCanvas5.setLocation(FileOnDisk.anInt4424, Class120_Sub1.anInt5363);
			}
		}
		if (arg0 >= 2) {
			ObjStackEntity.aBoolean76 = true;
		} else {
			ObjStackEntity.aBoolean76 = false;
		}
		if (EnumType.anInt5766 != -1) {
			SpotAnimationTypeList.method4422(true);
		}
		if (QuickchatMessageTypeList.aClass11_5 != null && (VertexDeclarationComponent.anInt6667 == 30 || VertexDeclarationComponent.anInt6667 == 25)) {
			Static1.method5();
		}
		for (local166 = 0; local166 < 100; local166++) {
			PingRequest.aBooleanArray21[local166] = true;
		}
		TextureOpIrregularBricks.aBoolean189 = true;
	}
}
