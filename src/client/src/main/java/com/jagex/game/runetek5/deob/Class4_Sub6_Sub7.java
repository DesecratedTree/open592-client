package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.cache.node.lru.ProjectileAnimationNode;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.entity.ProjectileAnimation;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class4_Sub45;
import com.jagex.game.runetek5.deob.Class120_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.deob.Class193;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub17;
import com.jagex.game.runetek5.graphics.light.LightGL;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.cache.node.lru.GlShadow;
import com.jagex.game.runetek5.deob.Huffman;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
@OriginalClass("client!eq")
public final class Class4_Sub6_Sub7 extends TextureOperation {

	@OriginalMember(owner = "client!eq", name = "<init>", descriptor = "()V")
	public Class4_Sub6_Sub7() {
		super(0, true);
	}

	@OriginalMember(owner = "client!eq", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(9) int[] local9 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			Static459.setVariables(local9, 0, MidiPcmStream.anInt1616, ParticleEffector.anIntArray209[arg0]);
		}
		return local9;
	}

@OriginalMember(owner = "client!eq", name = "J", descriptor = "[[I")
	public static int[][] anIntArrayArray21;
@OriginalMember(owner = "client!eq", name = "M", descriptor = "I")
	public static int anInt1932;
@OriginalMember(owner = "client!eq", name = "N", descriptor = "I")
	public static int anInt1933;
@OriginalMember(owner = "client!eq", name = "H", descriptor = "Lclient!pn;")
	public static final Class186 aClass186_8 = new Class186("", 12);
@OriginalMember(owner = "client!eq", name = "I", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_37 = new Class83("Checking for updates - ", "Suche nach Updates - ", "Vérification des mises à jour - ", "Verificando atualizações - ");
@OriginalMember(owner = "client!eq", name = "K", descriptor = "[I")
	public static final int[] anIntArray156 = new int[1000];
@OriginalMember(owner = "client!eq", name = "L", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_38 = new Class83("Loading...", "Lade...", "Chargement en cours...", "Carregando...");
@OriginalMember(owner = "client!eq", name = "a", descriptor = "(IILclient!uu;I)V")
	public static void method1680(@OriginalArg(1) int arg0, @OriginalArg(2) com.jagex.game.runetek5.config.rsinterface.Component arg1, @OriginalArg(3) int arg2) {
		if (Static281.aClass247_12 != null || Class4_Sub6_Sub22.aBoolean411 || (arg1 == null || Class10.method126(arg1) == null)) {
			return;
		}
		Static281.aClass247_12 = arg1;
		Static57.aClass247_1 = Class10.method126(arg1);
		Class4_Sub45.anInt7359 = arg0;
		Class4_Sub1_Sub17.anInt5487 = 0;
		Huffman.anInt3559 = arg2;
		Class4_Sub1_Sub2.aBoolean22 = false;
	}
@OriginalMember(owner = "client!eq", name = "a", descriptor = "(Lclient!uu;I)V")
	public static void method1681(@OriginalArg(0) com.jagex.game.runetek5.config.rsinterface.Component arg0) {
		if (Class120_Sub1.anInt5364 != arg0.anInt6842) {
			return;
		}
		if (Static1.aClass16_Sub1_Sub5_Sub1_1.aString44 == null) {
			arg0.anInt6848 = 0;
			arg0.anInt6781 = 0;
			return;
		}
		arg0.anInt6844 = 150;
		arg0.anInt6858 = (int) (Math.sin((double) Shadow.anInt5323 / 40.0D) * 256.0D) & 0x7FF;
		arg0.anInt6796 = 5;
		arg0.anInt6781 = Class6.anInt5452;
		arg0.anInt6848 = GlShadow.method2302(Static1.aClass16_Sub1_Sub5_Sub1_1.aString44);
		arg0.anInt6783 = Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6036;
		arg0.anInt6815 = Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6016;
		arg0.anInt6829 = 0;
		arg0.anInt6788 = Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6076;
	}
@OriginalMember(owner = "client!eq", name = "f", descriptor = "(I)V")
	public static void method1683() {
		for (@Pc(10) ProjectileAnimationNode local10 = (ProjectileAnimationNode) Class21.aClass183_2.method4140(); local10 != null; local10 = (ProjectileAnimationNode) Class21.aClass183_2.method4144()) {
			@Pc(15) ProjectileAnimation local15 = local10.projectileAnimation;
			if (Class4_Sub6_Sub25.anInt4963 != local15.aByte82 || local15.anInt2581 < Shadow.anInt5323) {
				local10.method5684();
				local15.method2194();
			} else if (local15.anInt2599 <= Shadow.anInt5323) {
				if (local15.anInt2587 > 0) {
					@Pc(56) NPCEntity local56 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local15.anInt2587 - 1];
					if (local56 != null && local56.anInt6893 >= 0 && GLBillowTextureData.anInt5666 * 128 > local56.anInt6893 && local56.anInt6892 >= 0 && Static283.anInt5187 * 128 > local56.anInt6892) {
						local15.method2197(Shadow.anInt5323, local56.anInt6893, BufferedSocket.method135(local56.anInt6893, local15.aByte82, local56.anInt6892) - local15.anInt2586, local56.anInt6892);
					}
				}
				if (local15.anInt2587 < 0) {
					@Pc(118) int local118 = -local15.anInt2587 - 1;
					@Pc(127) PlayerEntity local127;
					if (Class6.anInt5452 == local118) {
						local127 = Static1.aClass16_Sub1_Sub5_Sub1_1;
					} else {
						local127 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local118];
					}
					if (local127 != null && local127.anInt6893 >= 0 && local127.anInt6893 < GLBillowTextureData.anInt5666 * 128 && local127.anInt6892 >= 0 && local127.anInt6892 < Static283.anInt5187 * 128) {
						local15.method2197(Shadow.anInt5323, local127.anInt6893, BufferedSocket.method135(local127.anInt6893, local15.aByte82, local127.anInt6892) - local15.anInt2586, local127.anInt6892);
					}
				}
				local15.method2198(LightGL.npcCount);
				Class193.method4385(local15, true);
			}
		}
	}
@OriginalMember(owner = "client!eq", name = "a", descriptor = "(ILjava/lang/String;)J")
	public static long method1684(@OriginalArg(1) String arg0) {
		@Pc(7) long local7 = 0L;
		@Pc(10) int local10 = arg0.length();
		for (@Pc(19) int local19 = 0; local19 < local10; local19++) {
			local7 *= 37L;
			@Pc(29) char local29 = arg0.charAt(local19);
			if (local29 >= 'A' && local29 <= 'Z') {
				local7 += local29 + 1 - 65;
			} else if (local29 >= 'a' && local29 <= 'z') {
				local7 += local29 + 1 - 97;
			} else if (local29 >= '0' && local29 <= '9') {
				local7 += local29 + 27 - 48;
			}
			if (local7 >= 177917621779460413L) {
				break;
			}
		}
		while (local7 % 37L == 0L && local7 != 0L) {
			local7 /= 37L;
		}
		return local7;
	}
}
