package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;

import java.awt.Canvas;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.unpack_Sub1;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub2;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.RuntimeException_Sub1;
import com.jagex.game.runetek5.client.loading.loader.Js5GroupLoader;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!tm")
public abstract class Class99_Sub1 extends EnvironmentSampler {

	@OriginalMember(owner = "client!tm", name = "<init>", descriptor = "()V")
	protected Class99_Sub1() {
	}

	@OriginalMember(owner = "client!tm", name = "a", descriptor = "(I)Lclient!im;")
	public abstract GlCubeTexture method3232();

@OriginalMember(owner = "client!tm", name = "n", descriptor = "Lclient!et;")
	public static SignLink aClass66_5;
@OriginalMember(owner = "client!tm", name = "s", descriptor = "[[B")
	public static byte[][] aByteArrayArray15;
@OriginalMember(owner = "client!tm", name = "i", descriptor = "I")
	public static int anInt3967 = -1;
@OriginalMember(owner = "client!tm", name = "k", descriptor = "[[B")
	public static final byte[][] aByteArrayArray14 = new byte[1000][];
@OriginalMember(owner = "client!tm", name = "p", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_80 = new Class83("Your friend list is full. Max of 100 for free users, and 200 for members.", "Deine Freunde-Liste ist voll! Maximale Einträge: Mitglieder 200/freie Spieler 100", "Votre liste d'amis est pleine (100 noms maximum pour la version gratuite et 200 pour les abonnés).", "Sua lista de amigos está cheia. O limite é 100 para os usuários não pagantes e 200 para os membros.");
@OriginalMember(owner = "client!tm", name = "q", descriptor = "Lclient!fa;")
	public static final LRUCache aClass68_23 = new LRUCache(8);
@OriginalMember(owner = "client!tm", name = "r", descriptor = "[I")
	public static final int[] anIntArray262 = new int[] { 1, 2, 4, 8 };
@OriginalMember(owner = "client!tm", name = "a", descriptor = "(IIIII)V")
	public static void method3233(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(4) int arg3) {
		ParticleNode.aClass4_Sub12_Sub1_5.currentPosition = 0;
		ParticleNode.aClass4_Sub12_Sub1_5.method2551(Class4_Sub1_Sub11.aClass60_6.anInt1812);
		ParticleNode.aClass4_Sub12_Sub1_5.method2551(arg1);
		ParticleNode.aClass4_Sub12_Sub1_5.method2551(arg0);
		ParticleNode.aClass4_Sub12_Sub1_5.method2524(arg2);
		ParticleNode.aClass4_Sub12_Sub1_5.method2524(arg3);
		Class16_Sub4_Sub2.anInt2524 = 0;
		Class4_Sub6_Sub31.anInt5976 = 0;
		RuntimeException_Sub1.anInt4623 = -3;
		ItemType.anInt6183 = 1;
	}
@OriginalMember(owner = "client!tm", name = "a", descriptor = "(BLjava/awt/Canvas;ILclient!et;Lclient!ga;)Lclient!ya;")
	public static com.jagex.game.runetek5.graphics.Toolkit method3234(@OriginalArg(1) Canvas arg0, @OriginalArg(2) int arg1, @OriginalArg(3) SignLink arg2, @OriginalArg(4) unpack_Sub1 arg3) {
		return new qa(arg1, arg0, arg3, arg2);
	}
@OriginalMember(owner = "client!tm", name = "a", descriptor = "(ILclient!iv;)Lclient!ob;")
	public static Class27_Sub4 method3235(@OriginalArg(1) Packet arg0) {
		return new Class27_Sub4(arg0.method2512(), arg0.method2512(), arg0.method2512(), arg0.method2512(), arg0.method2493(), arg0.method2493(), arg0.method2490());
	}
@OriginalMember(owner = "client!tm", name = "a", descriptor = "(IIBIIIII)V")
	public static void method3236(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6) {
		@Pc(19) int local19 = Js5GroupLoader.method4519(GlCubeTexture.anInt3256, arg3, Model.anInt6287);
		@Pc(25) int local25 = Js5GroupLoader.method4519(GlCubeTexture.anInt3256, arg6, Model.anInt6287);
		@Pc(31) int local31 = Js5GroupLoader.method4519(Class36.anInt2656, arg0, ItemType.anInt6186);
		@Pc(37) int local37 = Js5GroupLoader.method4519(Class36.anInt2656, arg5, ItemType.anInt6186);
		@Pc(46) int local46 = Js5GroupLoader.method4519(GlCubeTexture.anInt3256, arg3 + arg2, Model.anInt6287);
		@Pc(55) int local55 = Js5GroupLoader.method4519(GlCubeTexture.anInt3256, arg6 - arg2, Model.anInt6287);
		for (@Pc(57) int local57 = local19; local57 < local46; local57++) {
			BasType.method3640(local31, arg1, local37, Class4_Sub6_Sub7.anIntArrayArray21[local57]);
		}
		for (@Pc(77) int local77 = local25; local77 > local55; local77--) {
			BasType.method3640(local31, arg1, local37, Class4_Sub6_Sub7.anIntArrayArray21[local77]);
		}
		@Pc(99) int local99 = Js5GroupLoader.method4519(Class36.anInt2656, arg0 + arg2, ItemType.anInt6186);
		@Pc(107) int local107 = Js5GroupLoader.method4519(Class36.anInt2656, arg5 - arg2, ItemType.anInt6186);
		for (@Pc(109) int local109 = local46; local109 <= local55; local109++) {
			@Pc(115) int[] local115 = Class4_Sub6_Sub7.anIntArrayArray21[local109];
			BasType.method3640(local31, arg1, local99, local115);
			BasType.method3640(local99, arg4, local107, local115);
			BasType.method3640(local107, arg1, local37, local115);
		}
	}
}
