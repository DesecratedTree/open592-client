package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.deob.TileLighting;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.deob.Class60;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.deob.Class4_Sub14;
import com.jagex.game.runetek5.deob.SpotAnimation;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.graphics.map.worldmap.WorldMapElement;
import com.jagex.game.runetek5.tools.Isaac;
import com.jagex.game.runetek5.deob.Class170;
import com.jagex.game.runetek5.deob.Class186;
import com.jagex.game.runetek5.deob.Class4_Sub17;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSprite;
import com.jagex.game.runetek5.deob.Class193;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class208;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.deob.TextureCombinerOp;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.deob.Class6_Sub2;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.deob.TextureOpBlend;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.GlVolumeTexture;
import com.jagex.game.runetek5.config.param.ParamTypeList;
import com.jagex.game.runetek5.scene.CollisionMap;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.client.input.keyboard.KeyRecordEntry;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.js5.request.Js5ResourceRequest;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub26;
import com.jagex.game.runetek5.deob.FloType;
import com.jagex.game.runetek5.js5.request.local.LocalRequester;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.deob.Class95;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.cache.node.StringNode;
import com.jagex.game.runetek5.ByteArrayWrapper;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTexture;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.Class222;
import com.jagex.game.runetek5.deob.Class70_Sub1;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.deob.Class4_Sub21_Sub2;
import com.jagex.game.runetek5.deob.Class48;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
@OriginalClass("client!up")
public abstract class Class244 {

	@OriginalMember(owner = "client!up", name = "<init>", descriptor = "()V")
	protected Class244() {
	}

	@OriginalMember(owner = "client!up", name = "a", descriptor = "(I)Lclient!ri;")
	public abstract Class30 method5483();

	@OriginalMember(owner = "client!up", name = "b", descriptor = "(I)V")
	public abstract void method5486();

	@OriginalMember(owner = "client!up", name = "c", descriptor = "(I)V")
	public abstract void method5487();

	@OriginalMember(owner = "client!up", name = "a", descriptor = "(II)Z")
	public abstract boolean method5489(@OriginalArg(1) int arg0);

@OriginalMember(owner = "client!up", name = "b", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_325 = new Class22(3, -1);
@OriginalMember(owner = "client!up", name = "e", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_145 = new Class83("Loaded defaults", "Standardeinstellungen geladen", "Paramètres par défaut chargés", "Padrões carregados");
@OriginalMember(owner = "client!up", name = "f", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_146 = new Class83("red:", "rot:", "rouge:", "vermelho:");
@OriginalMember(owner = "client!up", name = "g", descriptor = "S")
	public static short aShort100 = 32767;
@OriginalMember(owner = "client!up", name = "a", descriptor = "(Lclient!uu;Lclient!ma;IIIIIIJ)V")
	public static void method5484(@OriginalArg(0) com.jagex.game.runetek5.config.rsinterface.Component arg0, @OriginalArg(1) ComponentSurface arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) long arg7) {
		@Pc(11) int local11 = arg6 * arg6 + arg2 * arg2;
		if ((long) local11 > arg7) {
			return;
		}
		@Pc(31) int local31 = Math.min(arg0.anInt6833 / 2, arg0.anInt6805 / 2);
		if (local31 * local31 >= local11) {
			NPCType.method5585(arg3, Toolkit.aClass57Array11[arg4], arg0, arg1, arg6, arg2, arg5);
			return;
		}
		local31 -= 10;
		@Pc(47) int local47;
		if (Class3_Sub3.anInt2386 == 4) {
			local47 = (int) Static368.aFloat192 & 0x3FFF;
		} else {
			local47 = PlayerVarDomain.anInt3399 + (int) Static368.aFloat192 & 0x3FFF;
		}
		@Pc(60) int local60 = Class50_Sub1.anIntArray225[local47];
		@Pc(64) int local64 = Class50_Sub1.anIntArray224[local47];
		if (Class3_Sub3.anInt2386 != 4) {
			local64 = local64 * 256 / (Class4_Sub17.anInt2374 + 256);
			local60 = local60 * 256 / (Class4_Sub17.anInt2374 + 256);
		}
		@Pc(95) int local95 = arg2 * local64 + local60 * arg6 >> 15;
		@Pc(106) int local106 = arg6 * local64 - arg2 * local60 >> 15;
		@Pc(112) double local112 = Math.atan2((double) local95, (double) local106);
		@Pc(119) int local119 = (int) (Math.sin(local112) * (double) local31);
		@Pc(126) int local126 = (int) (Math.cos(local112) * (double) local31);
		Class186.aClass57Array10[arg4].drawRotatedCenter((float) arg0.anInt6833 / 2.0F + (float) arg3 + (float) local119, (float) -local126 + (float) arg0.anInt6805 / 2.0F + (float) arg5, 4096, (int) (-local112 / 6.283185307179586D * 65535.0D));
	}
@OriginalMember(owner = "client!up", name = "a", descriptor = "(B)V")
	public static void method5485() {
		ByteArrayWrapper.aClass124_2.method3149();
		Class4_Sub6_Sub22.aClass192_2.method4342();
		MaterialType.aClass109_2.method2756();
		Static267.aClass262_2.method5555();
		Class196.aClass240_1.method5226();
		Terrain.aClass206_3.method4707();
		SceneLocEntity.aClass55_1.method1396();
		Js5MasterIndex.aClass194_2.method4423();
		KeyRecordEntry.aClass26_1.method644();
		Js5ResourceRequest.aClass223_1.method5002();
		Class133.aClass249_1.method5327();
		Class4_Sub14.aClass265_2.method5587();
		Class4_Sub6_Sub31.aClass182_4.method4127();
		LocalRequester.aClass208_1.method4721();
		Class4_Sub1_Sub11.aClass180_1.method4116();
		StringNode.aClass217_1.method4932();
		CacheableNodeByteArray.aClass226_1.method5020();
		NPCTypeList.aClass204_1.method4672();
		Js5ResourceProvider.aClass172_2.method3978();
		Class4_Sub6_Sub26.aClass72_1.method1984();
		CollisionMap.method4846();
		Static441.method5571();
		GlVolumeTexture.method789();
		if (Class222.aClass219_6 != GlToolkit.aClass219_2) {
			for (@Pc(76) int local76 = 0; local76 < ParticleBinaryTree.aByteArrayArray28.length; local76++) {
				ParticleBinaryTree.aByteArrayArray28[local76] = null;
			}
			Static102.anInt2043 = 0;
		}
		Class14.method210();
		GlFramebuffer.method3609();
		TextureOpSprite.method985();
		FloType.method2817();
		Class95.method5011();
		Static271.aClass68_29.method1777();
		MonoStereoData.aClass19_16.cacheReset();
		GlVolumeTexture.aClass142_1.method3484();
		Class70_Sub1.method1813();
		Static395.aClass76_92.method2118();
		Class193.aClass76_69.method2118();
		Isaac.aClass76_20.method2118();
		TileLighting.aClass76_50.method2118();
		Class4_Sub6_Sub4.aClass76_19.method2118();
		Class4_Sub10.aClass76_27.method2118();
		TextureCombinerOp.aClass76_54.method2118();
		CollisionMap.aClass76_82.method2118();
		GlGround.aClass76_60.method2118();
		TextureOpTexture.aClass76_44.method2118();
		Class60.aClass76_23.method2118();
		Class170.aClass76_55.method2118();
		TextureOpBlend.aClass76_45.method2118();
		Static209.aClass76_48.method2118();
		Static312.aClass76_66.method2118();
		Class208.aClass76_79.method2118();
		Class4_Sub43.aClass76_99.method2118();
		Class36.aClass76_29.method2118();
		ParamTypeList.aClass76_87.method2118();
		Static391.aClass76_91.method2118();
		Class230.aClass76_90.method2118();
		WorldMapElement.aClass76_16.method2118();
		SpotAnimation.aClass76_17.method2118();
		Class4_Sub23.aClass76_39.method2118();
		Class48.aClass76_70.method2118();
		ByteArrayWrapper.aClass76_11.method2118();
		Shadow.aClass76_61.method2118();
		Class16_Sub1_Sub6.aClass76_98.method2118();
		Class6_Sub2.aClass76_2.method2118();
		Static366.aClass76_83.method2118();
		MECType.aClass68_27.method1777();
		Class4_Sub21_Sub2.aClass68_22.method1777();
		Class99_Sub1.aClass68_23.method1777();
		Static105.aClass68_9.method1777();
	}
@OriginalMember(owner = "client!up", name = "a", descriptor = "(Lclient!cw;II)Z")
	public static boolean method5490(@OriginalArg(0) Class4_Sub12_Sub1 arg0, @OriginalArg(2) int arg1) {
		@Pc(13) int local13 = arg0.method1143(2);
		@Pc(33) int local33;
		@Pc(38) int local38;
		@Pc(106) int local106;
		@Pc(110) int local110;
		@Pc(116) int local116;
		if (local13 == 0) {
			if (arg0.method1143(1) != 0) {
				method5490(arg0, arg1);
			}
			local33 = arg0.method1143(6);
			local38 = arg0.method1143(6);
			@Pc(50) boolean local50 = arg0.method1143(1) == 1;
			if (local50) {
				Class230.anIntArray497[Static441.anInt7199++] = arg1;
			}
			if (Static267.aClass16_Sub1_Sub5_Sub1Array1[arg1] != null) {
				throw new RuntimeException("hr:lr");
			}
			@Pc(73) GpuFaceNormal local73 = Class183.aClass253Array1[arg1];
			@Pc(81) PlayerEntity local81 = Static267.aClass16_Sub1_Sub5_Sub1Array1[arg1] = new PlayerEntity();
			local81.anInt6037 = arg1;
			if (ItemType.aClass4_Sub12Array1[arg1] != null) {
				local81.method3422(ItemType.aClass4_Sub12Array1[arg1]);
			}
			local81.method4745(local73.anInt6947);
			local81.anInt6034 = local73.anInt6946;
			local106 = local73.anInt6949;
			local110 = local106 >> 28;
			local116 = local106 >> 14 & 0xFF;
			local81.pMapdot = local73.aBoolean648;
			@Pc(124) int local124 = local106 & 0xFF;
			local81.movementQueueSpeed[0] = Static411.aByteArray95[arg1];
			local81.aByte82 = (byte) local110;
			local81.move(local38 + (local124 << 6) - OpenGLTextureCache.anInt1771, -Class50_Sub1.anInt3453 + ((local116 << 6) - -local33));
			local81.hasPendingPositionUpdate = false;
			Class183.aClass253Array1[arg1] = null;
			return true;
		} else if (local13 == 1) {
			local33 = arg0.method1143(2);
			local38 = Class183.aClass253Array1[arg1].anInt6949;
			Class183.aClass253Array1[arg1].anInt6949 = (local38 & 0xFFFFFFF) + (((local38 >> 28) + local33 & 0x3) << 28);
			return false;
		} else {
			@Pc(216) int local216;
			@Pc(221) int local221;
			@Pc(229) int local229;
			if (local13 != 2) {
				local33 = arg0.method1143(18);
				local38 = local33 >> 16;
				local216 = local33 >> 8 & 0xFF;
				local221 = local33 & 0xFF;
				local229 = Class183.aClass253Array1[arg1].anInt6949;
				local106 = local38 + (local229 >> 28) & 0x3;
				local110 = (local229 >> 14) + local216 & 0xFF;
				local116 = local229 + local221 & 0xFF;
				Class183.aClass253Array1[arg1].anInt6949 = local116 + (local106 << 28) + (local110 << 14);
				return false;
			}
			local33 = arg0.method1143(5);
			local38 = local33 >> 3;
			local216 = local33 & 0x7;
			local221 = Class183.aClass253Array1[arg1].anInt6949;
			local229 = local38 + (local221 >> 28) & 0x3;
			local106 = local221 >> 14 & 0xFF;
			local110 = local221 & 0xFF;
			if (local216 == 0) {
				local106--;
				local110--;
			}
			if (local216 == 1) {
				local110--;
			}
			if (local216 == 2) {
				local110--;
				local106++;
			}
			if (local216 == 3) {
				local106--;
			}
			if (local216 == 4) {
				local106++;
			}
			if (local216 == 5) {
				local110++;
				local106--;
			}
			if (local216 == 6) {
				local110++;
			}
			if (local216 == 7) {
				local106++;
				local110++;
			}
			Class183.aClass253Array1[arg1].anInt6949 = local110 + (local229 << 28) + (local106 << 14);
			return false;
		}
	}
@OriginalMember(owner = "client!up", name = "a", descriptor = "(III)V")
	public static void method5491(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		@Pc(7) Class164 local7 = Class70.aClass164ArrayArrayArray5[arg0][arg1][arg2];
		if (local7 != null && local7.aClass16_Sub4_2 != null) {
			local7.aClass16_Sub4_2 = null;
		}
	}
}
