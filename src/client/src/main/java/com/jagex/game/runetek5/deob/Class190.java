package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;

import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.JFS32;
import com.jagex.game.runetek5.entity.EntityNodeList;
import com.jagex.game.runetek5.graphics.model.normal.MeshBillboard;
import com.jagex.game.runetek5.sign.SignedResource;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub1;
import com.jagex.game.runetek5.deob.Class168;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.FloType;
import com.jagex.game.runetek5.config.material.texture.TextureOpKaleidoscope;
import com.jagex.game.runetek5.cache.node.lru.ProjectileAnimationNode;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationType;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
@OriginalClass("client!pu")
public final class Class190 {

	@OriginalMember(owner = "client!pu", name = "g", descriptor = "I")
	public int anInt5502 = 0;

	@OriginalMember(owner = "client!pu", name = "d", descriptor = "I")
	public int anInt5500 = 0;

	@OriginalMember(owner = "client!pu", name = "l", descriptor = "I")
	public int anInt5506 = 2048;

	@OriginalMember(owner = "client!pu", name = "j", descriptor = "I")
	public int anInt5504 = 2048;

	static {
		new Class83("Error sending message to clan chat - please try again later!", "Fehler beim Versenden der Nachricht - bitte versuch es später erneut.", "Erreur lors de l'envoi du message au canal de clan - veuillez réessayer ultérieurement.", "Erro ao enviar mensagem ao canal de clã. Tente de novo depois!");
	}

	@OriginalMember(owner = "client!pu", name = "a", descriptor = "(ZLclient!iv;)V")
	public void method4218(@OriginalArg(1) Packet arg0) {
		while (true) {
			@Pc(5) int local5 = arg0.method2490();
			if (local5 == 0) {
				return;
			}
			this.method4220(local5, arg0);
		}
	}

	@OriginalMember(owner = "client!pu", name = "a", descriptor = "(IBLclient!iv;)V")
	private void method4220(@OriginalArg(0) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 1) {
			this.anInt5502 = arg1.method2490();
		} else if (arg0 == 2) {
			this.anInt5506 = arg1.method2536();
		} else if (arg0 == 3) {
			this.anInt5504 = arg1.method2536();
		} else if (arg0 == 4) {
			this.anInt5500 = arg1.method2512();
		}
	}

@OriginalMember(owner = "client!pu", name = "e", descriptor = "Lclient!bg;")
	public static Class22 aClass22_257;
@OriginalMember(owner = "client!pu", name = "i", descriptor = "[I")
	public static int[] anIntArray376 = new int[2];
@OriginalMember(owner = "client!pu", name = "k", descriptor = "I")
	public static int anInt5505 = 0;
@OriginalMember(owner = "client!pu", name = "a", descriptor = "(I)V")
	public static void method4216() {
		@Pc(7) JFS32 local7 = null;
		try {
			@Pc(16) SignedResource local16 = Class126.aClass66_4.method1738("2");
			while (local16.anInt5760 == 0) {
				Class259.method5503(1L);
			}
			if (local16.anInt5760 == 1) {
				local7 = (JFS32) local16.anObject29;
				@Pc(41) Packet local41 = new Packet(SpotAnimationType.anInt4998 * 6 + 3);
				local41.method2551(1);
				local41.method2524(SpotAnimationType.anInt4998);
				for (@Pc(53) int local53 = 0; local53 < ParticleEffector.WALL_ROTATION_FLAGS.length; local53++) {
					if (Class16_Sub1_Sub1.aBooleanArray3[local53]) {
						local41.method2524(local53);
						local41.method2531(ParticleEffector.WALL_ROTATION_FLAGS[local53]);
					}
				}
				local7.method4714(0, local41.currentPosition, local41.buffer);
			}
		} catch (@Pc(84) Exception local84) {
		}
		try {
			if (local7 != null) {
				local7.method4713();
			}
		} catch (@Pc(93) Exception local93) {
		}
		FloType.aLong119 = Class168.method3962();
		TextureOpKaleidoscope.aBoolean256 = false;
	}
@OriginalMember(owner = "client!pu", name = "a", descriptor = "(Z)V")
	public static void method4217() {
		@Pc(3) EntityNodeList[] local3 = MeshBillboard.aClass84Array1;
		synchronized (MeshBillboard.aClass84Array1) {
			for (@Pc(12) int local12 = 0; local12 < MeshBillboard.aClass84Array1.length; local12++) {
				MeshBillboard.aClass84Array1[local12] = new EntityNodeList();
				ProjectileAnimationNode.anIntArray154[local12] = 0;
			}
		}
	}
@OriginalMember(owner = "client!pu", name = "a", descriptor = "(III)V")
	public static void method4221(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		@Pc(8) Class4_Sub1_Sub11 local8 = NPCTypeList.method5222(arg0, 1);
		local8.method2604();
		local8.anInt3083 = arg1;
	}
@OriginalMember(owner = "client!pu", name = "a", descriptor = "(IZ)Lclient!sp;")
	public static ModeWhere method4222(@OriginalArg(0) int arg0) {
		@Pc(8) ModeWhere[] local8 = Class4_Sub1_Sub2.method157();
		for (@Pc(10) int local10 = 0; local10 < local8.length; local10++) {
			@Pc(16) ModeWhere local16 = local8[local10];
			if (arg0 == local16.id) {
				return local16;
			}
		}
		return null;
	}
}
