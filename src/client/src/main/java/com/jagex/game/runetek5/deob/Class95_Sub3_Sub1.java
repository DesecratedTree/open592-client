package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;


import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.deob.Class4_Sub5;
import com.jagex.game.runetek5.config.material.texture.ColorImageCache;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.config.enumtype.EnumType;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class120;
import com.jagex.game.runetek5.cache.node.HashTable;
import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationTypeList;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.js5.request.Js5ResourceRequest;
import com.jagex.game.runetek5.deob.Class4_Sub36;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
import com.jagex.game.runetek5.EnvironmentContext;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.Class62;
import com.jagex.game.runetek5.deob.Huffman;
import com.jagex.game.runetek5.config.material.texture.TextureType;
import com.jagex.game.runetek5.deob.Class85;
@OriginalClass("client!su")
public final class Class95_Sub3_Sub1 extends Class95_Sub3 {

	static {
		new Class83("Your clan channel has now been enabled!", "Dein Chatraum ist jetzt eingeschaltet.", "Votre canal de clan est activé !", "Seu canal de clã já está ativado!");
		new Class83("This player is on a quick chat world and cannot receive your message.", "Der Spieler kann auf einer Direktchat-Welt keine Nachrichten empfangen.", "Ce joueur est sur un serveur à messagerie rapide et ne peut pas recevoir votre message.", "Este jogador não pode receber sua mensagem porque está em um mundo de papo rápido.");
	}

@OriginalMember(owner = "client!su", name = "n", descriptor = "F")
	public static float aFloat194;
@OriginalMember(owner = "client!su", name = "m", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_128 = new Class83("Drop", "Fallen lassen", "Poser", "Largar");
@OriginalMember(owner = "client!su", name = "o", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_129 = new Class83("Stellar Dawn is loading - please wait...", "Mechscape wird geladen - bitte warten...", "Chargement de Mechscape en cours - veuillez patienter...", "Mechscape carregando. Aguarde...");
@OriginalMember(owner = "client!su", name = "p", descriptor = "I")
	public static int anInt6394 = -1;
@OriginalMember(owner = "client!su", name = "a", descriptor = "(ZI)V")
	public static void method4994(@OriginalArg(0) boolean arg0) {
		if (arg0) {
			if (EnumType.anInt5766 != -1) {
				EnvironmentContext.method4602(EnumType.anInt5766);
			}
			for (@Pc(19) Class4_Sub43 local19 = (Class4_Sub43) MusicPatch.aClass102_29.method2705(); local19 != null; local19 = (Class4_Sub43) MusicPatch.aClass102_29.method2704()) {
				if (!local19.method5683()) {
					local19 = (Class4_Sub43) MusicPatch.aClass102_29.method2705();
					if (local19 == null) {
						break;
					}
				}
				Class62.method1606(true, local19, false);
			}
			EnumType.anInt5766 = -1;
			MusicPatch.aClass102_29 = new CubeMapSpecularEffect(8);
			Class22.method529();
			EnumType.anInt5766 = ColorImageCache.anInt4755;
			SpotAnimationTypeList.method4422(false);
			ModeWhere.method4940();
			Static271.runInterfaceOnLoadScripts(EnumType.anInt5766);
		}
		Class85.method2315();
		HashTable.anInt3147 = -1;
		Static368.method4938(Class30.anInt770);
		Static1.aClass16_Sub1_Sub5_Sub1_1 = new PlayerEntity();
		Static1.aClass16_Sub1_Sub5_Sub1_1.movementBaseX[0] = GLBillowTextureData.anInt5666 / 2;
		Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892 = Static283.anInt5187 * 128 / 2;
		Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893 = GLBillowTextureData.anInt5666 * 128 / 2;
		Static1.aClass16_Sub1_Sub5_Sub1_1.movementBaseY[0] = Static283.anInt5187 / 2;
		Static145.anInt2906 = 0;
		Class4_Sub23.anInt3197 = 0;
		if (Class3_Sub3.anInt2386 == 2) {
			Static145.anInt2906 = Class120.anInt5355 << 7;
			Class4_Sub23.anInt3197 = Class14.anInt266 << 7;
		} else {
			Class4_Sub5.method514();
		}
		Class133.method3355();
		if (Class4_Sub23.anInt3197 == 0 || Static145.anInt2906 == 0) {
			Huffman.method2932(10);
		} else {
			Class4_Sub12_Sub1.method1134();
			Huffman.method2932(28);
		}
	}
@OriginalMember(owner = "client!su", name = "a", descriptor = "(II)I")
	public static int method4995(@OriginalArg(1) int arg0) {
		return arg0 >> 11 & 0x7F;
	}
@OriginalMember(owner = "client!su", name = "b", descriptor = "(B)V")
	public static void method4996() {
		PingWorker.method5476(Js5MasterIndex.aClass215_82);
		ParticleNode.aClass4_Sub12_Sub1_5.method2524(Js5ResourceRequest.mediumPos);
	}
@OriginalMember(owner = "client!su", name = "a", descriptor = "(Lclient!uu;IZ)V")
	public static void method4998(@OriginalArg(0) com.jagex.game.runetek5.config.rsinterface.Component arg0, @OriginalArg(2) boolean arg1) {
		@Pc(15) int local15 = arg0.anInt6821 == 0 ? arg0.anInt6833 : arg0.anInt6821;
		@Pc(27) int local27 = arg0.anInt6834 == 0 ? arg0.anInt6805 : arg0.anInt6834;
		TextureType.method3278(local15, local27, Class4_Sub36.aClass247ArrayArray2[arg0.anInt6809 >> 16], arg0.anInt6809, arg1);
		if (arg0.slotChildren != null) {
			TextureType.method3278(local15, local27, arg0.slotChildren, arg0.anInt6809, arg1);
		}
		@Pc(62) Class4_Sub43 local62 = (Class4_Sub43) MusicPatch.aClass102_29.method2700((long) arg0.anInt6809);
		if (local62 != null) {
			Static395.method5164(arg1, local27, local62.anInt6979, local15);
		}
	}
}
