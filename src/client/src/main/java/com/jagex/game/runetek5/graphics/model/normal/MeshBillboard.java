package com.jagex.game.runetek5.graphics.model.normal;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.entity.EntityNodeList;

import com.jagex.game.runetek5.entity.NPCEntity;

import com.jagex.game.runetek5.graphics.model.normal.*;
import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.config.material.texture.MonochromeImageCache;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.deob.Class48_Sub1_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.config.sequence.SeqTypeList;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub37;
import com.jagex.game.runetek5.deob.ObjStackEntity;
@OriginalClass("client!me")
public final class MeshBillboard {

	@OriginalMember(owner = "client!me", name = "a", descriptor = "[Lclient!gn;")
	public static final EntityNodeList[] aClass84Array1 = new EntityNodeList[5];

	@OriginalMember(owner = "client!me", name = "b", descriptor = "I")
	public final int anInt4521;

	@OriginalMember(owner = "client!me", name = "i", descriptor = "I")
	public final int anInt4528;

	@OriginalMember(owner = "client!me", name = "g", descriptor = "I")
	public final int anInt4526;

	@OriginalMember(owner = "client!me", name = "h", descriptor = "I")
	public final int anInt4527;

	static {
		for (@Pc(4) int local4 = 0; local4 < aClass84Array1.length; local4++) {
			aClass84Array1[local4] = new EntityNodeList();
		}
		new Class83("Unable to send message - system busy.", "Deine Nachricht konnte nicht verschickt werden, das System ist derzeit ausgelastet.", "Impossible d'envoyer un message - système occupé.", "Não foi possível enviar a mensagem. O sistema está ocupado.");
	}

	@OriginalMember(owner = "client!me", name = "<init>", descriptor = "(IIII)V")
	public MeshBillboard(@OriginalArg(0) int billboardId, @OriginalArg(1) int appliesOnFace, @OriginalArg(2) int depth, @OriginalArg(3) int distance) {
		this.anInt4521 = appliesOnFace;
		this.anInt4528 = distance;
		this.anInt4526 = billboardId;
		this.anInt4527 = depth;
	}

	@OriginalMember(owner = "client!me", name = "a", descriptor = "(ZI)Lclient!me;")
	public MeshBillboard method3554(@OriginalArg(1) int arg0) {
		return new MeshBillboard(this.anInt4526, arg0, this.anInt4527, this.anInt4528);
	}

@OriginalMember(owner = "client!me", name = "a", descriptor = "(B)V")
	public static void method3552() {
		for (@Pc(7) int local7 = 0; local7 < ObjStackEntity.anInt759; local7++) {
			@Pc(13) int local13 = LocTypeList.mapElementLocIds[local7];
			@Pc(17) NPCEntity local17 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local13];
			@Pc(21) int local21 = Class14.aClass4_Sub12_Sub1_3.method2490();
			if ((local21 & 0x40) != 0) {
				local21 += Class14.aClass4_Sub12_Sub1_3.method2490() << 8;
			}
			// Custom pet flag. Must be read before the other blocks because the server writes it first.
			if ((local21 & 0x4000) != 0) {
				local17.isPet = Class14.aClass4_Sub12_Sub1_3.method2490() != 0;
			}
			@Pc(44) int local44;
			if ((local21 & 0x1000) != 0) {
				local44 = Class14.aClass4_Sub12_Sub1_3.method2523();
				@Pc(47) int[] local47 = new int[local44];
				@Pc(50) int[] local50 = new int[local44];
				@Pc(53) int[] local53 = new int[local44];
				for (@Pc(55) int local55 = 0; local55 < local44; local55++) {
					@Pc(61) int local61 = Class14.aClass4_Sub12_Sub1_3.method2536();
					if (local61 == 65535) {
						local61 = -1;
					}
					local47[local55] = local61;
					local50[local55] = Class14.aClass4_Sub12_Sub1_3.method2520();
					local53[local55] = Class14.aClass4_Sub12_Sub1_3.method2536();
				}
				Class48_Sub1_Sub1.method3758(local50, local17, local47, local53);
			}
			if ((local21 & 0x10) != 0) {
				if (local17.npcType.method5578()) {
					Class4_Sub6_Sub37.method5429(local17);
				}
				local17.method4766(Class196.aClass240_1.method5229(Class14.aClass4_Sub12_Sub1_3.method2536()));
				local17.setSize(local17.npcType.anInt7201);
				local17.anInt6080 = local17.npcType.anInt7221 << 3;
				if (local17.npcType.method5578()) {
					MonochromeImageCache.method2264(local17.movementBaseX[0], local17, null, local17.movementBaseY[0], 0, local17.aByte82, null);
				}
			}
			@Pc(162) int local162;
			if ((local21 & 0x2) != 0) {
				local44 = Class14.aClass4_Sub12_Sub1_3.method2506();
				local162 = Class14.aClass4_Sub12_Sub1_3.method2520();
				local17.method4746(local44, Shadow.anInt5323, local162);
			}
			if ((local21 & 0x400) != 0) {
				local17.anInt6089 = Class14.aClass4_Sub12_Sub1_3.method2548();
				local17.anInt6095 = Class14.aClass4_Sub12_Sub1_3.method2538();
			}
			if ((local21 & 0x100) != 0) {
				local17.anInt6043 = Class14.aClass4_Sub12_Sub1_3.method2534();
				local17.anInt6052 = Class14.aClass4_Sub12_Sub1_3.method2534();
				local17.anInt6044 = Class14.aClass4_Sub12_Sub1_3.method2507();
				local17.anInt6074 = Class14.aClass4_Sub12_Sub1_3.method2534();
				local17.anInt6018 = Class14.aClass4_Sub12_Sub1_3.method2536() + Shadow.anInt5323;
				local17.anInt6023 = Class14.aClass4_Sub12_Sub1_3.method2548() + Shadow.anInt5323;
				local17.lb = Class14.aClass4_Sub12_Sub1_3.method2489();
				local17.anInt6052 += local17.movementBaseY[0];
				local17.anInt6074 += local17.movementBaseY[0];
				local17.anInt6043 += local17.movementBaseX[0];
				local17.anInt6044 += local17.movementBaseX[0];
				local17.pathPointer = 1;
				local17.anInt6087 = 0;
			}
			@Pc(294) int local294;
			if ((local21 & 0x800) != 0) {
				local44 = Class14.aClass4_Sub12_Sub1_3.method2536();
				local162 = Class14.aClass4_Sub12_Sub1_3.method2491();
				if (local44 == 65535) {
					local44 = -1;
				}
				local294 = Class14.aClass4_Sub12_Sub1_3.method2489();
				local17.method4748(local294, local162, true, local44);
			}
			if ((local21 & 0x4) != 0) {
				local44 = Class14.aClass4_Sub12_Sub1_3.method2538();
				if (local44 == 65535) {
					local44 = -1;
				}
				local162 = Class14.aClass4_Sub12_Sub1_3.method2489();
				Class4_Sub7.method632(local44, local162, local17);
			}
			if ((local21 & 0x2000) != 0) {
				local17.aByte71 = Class14.aClass4_Sub12_Sub1_3.method2549();
				local17.aByte69 = Class14.aClass4_Sub12_Sub1_3.method2549();
				local17.aByte70 = Class14.aClass4_Sub12_Sub1_3.method2549();
				local17.aByte68 = (byte) Class14.aClass4_Sub12_Sub1_3.method2523();
				local17.anInt6020 = Shadow.anInt5323 + Class14.aClass4_Sub12_Sub1_3.method2515();
				local17.anInt6068 = Shadow.anInt5323 + Class14.aClass4_Sub12_Sub1_3.method2536();
			}
			if ((local21 & 0x1) != 0) {
				local44 = Class14.aClass4_Sub12_Sub1_3.method2548();
				if (local44 == 65535) {
					local44 = -1;
				}
				local162 = Class14.aClass4_Sub12_Sub1_3.method2510();
				local294 = Class14.aClass4_Sub12_Sub1_3.method2489();
				local17.method4748(local294, local162, false, local44);
			}
			if ((local21 & 0x200) != 0) {
				local44 = Class14.aClass4_Sub12_Sub1_3.method2515();
				local17.anInt6051 = Class14.aClass4_Sub12_Sub1_3.method2520();
				local17.anInt6054 = Class14.aClass4_Sub12_Sub1_3.method2490();
				local17.anInt6041 = local44 & 0x7FFF;
				local17.aBoolean559 = (local44 & 0x8000) != 0;
				local17.anInt6035 = local17.anInt6051 + Shadow.anInt5323 + local17.anInt6041;
			}
			if ((local21 & 0x8) != 0) {
				local17.anInt6034 = Class14.aClass4_Sub12_Sub1_3.method2548();
				if (local17.anInt6034 == 65535) {
					local17.anInt6034 = -1;
				}
			}
			if ((local21 & 0x80) != 0) {
				local17.aString60 = Class14.aClass4_Sub12_Sub1_3.method2537();
				local17.anInt6077 = 100;
			}
			if ((local21 & 0x20) != 0) {
				local44 = Class14.aClass4_Sub12_Sub1_3.method2506();
				local162 = Class14.aClass4_Sub12_Sub1_3.method2520();
				local17.method4746(local44, Shadow.anInt5323, local162);
				local17.anInt6072 = Shadow.anInt5323 + 300;
				local17.anInt6026 = Class14.aClass4_Sub12_Sub1_3.method2520();
			}
		}
	}
@OriginalMember(owner = "client!me", name = "b", descriptor = "(B)V")
	public static void method3555() {
		for (@Pc(3) int local3 = 0; local3 < SeqTypeList.aByteArrayArrayArray2.length; local3++) {
			for (@Pc(7) int local7 = 0; local7 < SeqTypeList.aByteArrayArrayArray2[0].length; local7++) {
				for (@Pc(11) int local11 = 0; local11 < SeqTypeList.aByteArrayArrayArray2[0][0].length; local11++) {
					SeqTypeList.aByteArrayArrayArray2[local3][local7][local11] = 0;
				}
			}
		}
	}
}
