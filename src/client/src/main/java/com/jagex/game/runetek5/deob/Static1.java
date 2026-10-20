package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.rsinterface.HookRequest;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.deob.SpotAnimation;
import com.jagex.game.runetek5.deob.Class4_Sub45;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class79_Sub1;
import com.jagex.game.runetek5.deob.Class95_Sub3;
import com.jagex.game.runetek5.deob.Class75;
import com.jagex.game.runetek5.deob.Class269;
import com.jagex.game.runetek5.deob.Class101;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class95_Sub3_Sub1;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub17;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
import com.jagex.game.runetek5.deob.Class16_Sub5;
import com.jagex.game.runetek5.deob.Class15;
import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.deob.Class58;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Class222;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.deob.Huffman;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
public final class Static1 {

	@OriginalMember(owner = "client!a", name = "c", descriptor = "[[Lclient!mu;")
	public static TileLighting[][] aClass155ArrayArray1;

	@OriginalMember(owner = "client!a", name = "h", descriptor = "Lclient!lh;")
	public static PlayerEntity aClass16_Sub1_Sub5_Sub1_1;

	@OriginalMember(owner = "client!a", name = "f", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_1 = new Class22(45, 2);

	@OriginalMember(owner = "client!a", name = "g", descriptor = "I")
	public static int anInt5 = 0;

	@OriginalMember(owner = "client!a", name = "i", descriptor = "I")
	public static int anInt6 = 0;

	@OriginalMember(owner = "client!a", name = "j", descriptor = "[[Z")
	public static final boolean[][] aBooleanArrayArray1 = new boolean[][] { new boolean[4], { false, true, true, false }, { true, false, true, false }, { true, false, true, false }, { false, false, true, false }, { false, false, true, false }, { true, false, true, false }, { true, false, false, true }, { true, false, false, true }, { true, true, false, false }, new boolean[4], { false, true, false, true }, new boolean[4] };

	@OriginalMember(owner = "client!a", name = "a", descriptor = "(I)V")
	public static void method1() {
		for (@Pc(14) Class4_Sub9 local14 = (Class4_Sub9) Class75.aClass183_15.method4140(); local14 != null; local14 = (Class4_Sub9) Class75.aClass183_15.method4144()) {
			if (local14.anInt796 == -1) {
				local14.anInt799 = 0;
				if (local14.anInt793 >= 0 && local14.anInt790 >= 0 && GLBillowTextureData.anInt5666 > local14.anInt793 && Static283.anInt5187 > local14.anInt790) {
					Class15.method216(local14);
				}
			} else {
				local14.method5684();
			}
		}
	}

	@OriginalMember(owner = "client!a", name = "a", descriptor = "(ZI)V")
	public static void method2() {
		SceneLocEntity.aClass68_19.method1788(5);
	}

	@OriginalMember(owner = "client!a", name = "b", descriptor = "(I)V")
	public static void method3() {
		Class4_Sub12_Sub1.method1142(Static281.aClass247_12);
		Class4_Sub1_Sub17.anInt5487++;
		if (Class79_Sub1.aBoolean570 && Class95_Sub3.aBoolean584) {
			@Pc(32) int local32 = MaterialType.aClass80_1.method2234();
			@Pc(36) int local36 = MaterialType.aClass80_1.getDefaultValue();
			local32 -= Huffman.anInt3559;
			local36 -= Class4_Sub45.anInt7359;
			if (local32 < Toolkit.anInt5535) {
				local32 = Toolkit.anInt5535;
			}
			if (Static57.aClass247_1.anInt6833 + Toolkit.anInt5535 < local32 + Static281.aClass247_12.anInt6833) {
				local32 = Toolkit.anInt5535 + Static57.aClass247_1.anInt6833 - Static281.aClass247_12.anInt6833;
			}
			if (Class222.anInt6383 > local36) {
				local36 = Class222.anInt6383;
			}
			if (local36 + Static281.aClass247_12.anInt6805 > Static57.aClass247_1.anInt6805 + Class222.anInt6383) {
				local36 = Class222.anInt6383 + Static57.aClass247_1.anInt6805 - Static281.aClass247_12.anInt6805;
			}
			@Pc(110) int local110 = local32 + Static57.aClass247_1.anInt6825 - Toolkit.anInt5535;
			@Pc(118) int local118 = Static57.aClass247_1.anInt6849 + local36 - Class222.anInt6383;
			@Pc(172) HookRequest local172;
			if (MaterialType.aClass80_1.method2228()) {
				if (Class4_Sub1_Sub17.anInt5487 > Static281.aClass247_12.anInt6786) {
					@Pc(130) int local130 = local32 - GlFramebuffer.totalTransitionDuration;
					@Pc(135) int local135 = local36 - Class95_Sub3_Sub1.anInt6394;
					if (Static281.aClass247_12.anInt6795 < local130 || -Static281.aClass247_12.anInt6795 > local130 || Static281.aClass247_12.anInt6795 < local135 || local135 < -Static281.aClass247_12.anInt6795) {
						Class4_Sub1_Sub2.aBoolean22 = true;
					}
				}
				if (Static281.aClass247_12.anObjectArray26 != null && Class4_Sub1_Sub2.aBoolean22) {
					local172 = new HookRequest();
					local172.params = Static281.aClass247_12.anObjectArray26;
					local172.anInt5254 = local110;
					local172.anInt5252 = local118;
					local172.source = Static281.aClass247_12;
					Static271.executeHookInner(local172);
					return;
				}
			} else {
				if (Class4_Sub1_Sub2.aBoolean22) {
					Class16_Sub5.method5139();
					if (Static281.aClass247_12.anObjectArray16 != null) {
						local172 = new HookRequest();
						local172.params = Static281.aClass247_12.anObjectArray16;
						local172.anInt5252 = local118;
						local172.targetComponent = Static232.aClass247_9;
						local172.source = Static281.aClass247_12;
						local172.anInt5254 = local110;
						Static271.executeHookInner(local172);
					}
					if (Static232.aClass247_9 != null && client.method906(Static281.aClass247_12) != null) {
						Class58.method1512(Static281.aClass247_12, Static232.aClass247_9);
					}
				} else if ((SpotAnimation.anInt1361 == 1 || Class101.method2677()) && Skybox.anInt6710 > 2) {
					Static102.method1794(Huffman.anInt3559 + GlFramebuffer.totalTransitionDuration, Class95_Sub3_Sub1.anInt6394 + Class4_Sub45.anInt7359);
				} else if (Static14.method143()) {
					Static102.method1794(Huffman.anInt3559 + GlFramebuffer.totalTransitionDuration, Class95_Sub3_Sub1.anInt6394 + Class4_Sub45.anInt7359);
				}
				Static281.aClass247_12 = null;
			}
		} else if (Class4_Sub1_Sub17.anInt5487 > 1) {
			Static281.aClass247_12 = null;
		}
	}

	@OriginalMember(owner = "client!a", name = "a", descriptor = "(B)V")
	public static void method5() {
		PingWorker.method5476(NPCEntity.aClass215_81);
		ParticleNode.aClass4_Sub12_Sub1_5.method2551(Class269.method5664());
		ParticleNode.aClass4_Sub12_Sub1_5.method2524(GpuPointLight.anInt2881);
		ParticleNode.aClass4_Sub12_Sub1_5.method2524(EffectiveVertex.anInt5346);
		ParticleNode.aClass4_Sub12_Sub1_5.method2551(TextureOpBrightness.aClass50_Sub1_1.anInt3440);
	}
}
