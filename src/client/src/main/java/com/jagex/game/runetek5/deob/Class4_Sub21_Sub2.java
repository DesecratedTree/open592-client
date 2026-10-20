package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.cache.node.PointLight;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.Class136;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!kc")
public final class Class4_Sub21_Sub2 extends PointLight {

	static {
		new Class83("You cannot send a quick chat message to a player on this world at this time.", "Einem Spieler auf dieser Welt können derzeit keine Direktchat-Nachrichten", "Impossible d'envoyer un message rapide à un joueur de ce serveur à l'heure actuelle.", "Você não pode enviar uma mensagem de papo rápido para um jogador neste mundo neste momento.");
	}

	@OriginalMember(owner = "client!kc", name = "<init>", descriptor = "(IIIIIF)V")
	public Class4_Sub21_Sub2(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) float arg5) {
		super(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@OriginalMember(owner = "client!kc", name = "a", descriptor = "(IIIB)V")
	@Override
	public void setPosition(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		super.anInt3866 = arg1;
		super.anInt3856 = arg2;
		super.anInt3860 = arg0;
	}

	@OriginalMember(owner = "client!kc", name = "a", descriptor = "(IF)V")
	@Override
	public void setIntensity(@OriginalArg(1) float arg0) {
		super.intensity = arg0;
	}

@OriginalMember(owner = "client!kc", name = "B", descriptor = "Lclient!fa;")
	public static final LRUCache aClass68_22 = new LRUCache(4);
@OriginalMember(owner = "client!kc", name = "G", descriptor = "[Ljava/lang/String;")
	public static final String[] aStringArray15 = new String[100];
@OriginalMember(owner = "client!kc", name = "J", descriptor = "I")
	public static int anInt3873 = 0;
@OriginalMember(owner = "client!kc", name = "a", descriptor = "(ILclient!qm;)Ljava/lang/String;")
	public static String method3166(@OriginalArg(1) OggOtherStream arg0) {
		return arg0.aString56 == null || arg0.aString56.length() <= 0 ? arg0.aString55 : arg0.aString55 + Class14.aClass83_10.method2267(NativeBillboardFace.anInt3423) + arg0.aString56;
	}
@OriginalMember(owner = "client!kc", name = "a", descriptor = "(IZIIII)V")
	public static void method3167(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4) {
		@Pc(10) int local10 = arg3 - arg4;
		@Pc(15) int local15 = arg1 - arg2;
		if (local10 == 0) {
			if (local15 != 0) {
				Class136.method3440(arg4, arg2, arg1, arg0);
			}
		} else if (local15 == 0) {
			Class16.method5340(arg2, arg4, arg0, arg3);
		} else {
			@Pc(55) int local55 = (local15 << 12) / local10;
			@Pc(63) int local63 = arg2 - (local55 * arg4 >> 12);
			@Pc(80) int local80;
			@Pc(78) int local78;
			if (arg4 < ItemType.anInt6186) {
				local78 = (ItemType.anInt6186 * local55 >> 12) + local63;
				local80 = ItemType.anInt6186;
			} else if (arg4 <= Class36.anInt2656) {
				local78 = arg2;
				local80 = arg4;
			} else {
				local80 = Class36.anInt2656;
				local78 = (Class36.anInt2656 * local55 >> 12) + local63;
			}
			@Pc(118) int local118;
			@Pc(116) int local116;
			if (arg3 < ItemType.anInt6186) {
				local116 = (local55 * ItemType.anInt6186 >> 12) + local63;
				local118 = ItemType.anInt6186;
			} else if (Class36.anInt2656 >= arg3) {
				local118 = arg3;
				local116 = arg1;
			} else {
				local118 = Class36.anInt2656;
				local116 = local63 + (local55 * Class36.anInt2656 >> 12);
			}
			if (Model.anInt6287 > local116) {
				local116 = Model.anInt6287;
				local118 = (Model.anInt6287 - local63 << 12) / local55;
			} else if (local116 > GlCubeTexture.anInt3256) {
				local116 = GlCubeTexture.anInt3256;
				local118 = (GlCubeTexture.anInt3256 - local63 << 12) / local55;
			}
			if (local78 < Model.anInt6287) {
				local80 = (Model.anInt6287 - local63 << 12) / local55;
				local78 = Model.anInt6287;
			} else if (GlCubeTexture.anInt3256 < local78) {
				local78 = GlCubeTexture.anInt3256;
				local80 = (GlCubeTexture.anInt3256 - local63 << 12) / local55;
			}
			Static167.method2738(local78, local80, arg0, local116, local118);
		}
	}
@OriginalMember(owner = "client!kc", name = "a", descriptor = "(IIII)V")
	public static void method3168(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		@Pc(35) String local35 = "tele " + arg0 + "," + (arg2 >> 6) + "," + (arg1 >> 6) + "," + (arg2 & 0x3F) + "," + (arg1 & 0x3F);
		System.out.println(local35);
		GlVboElementsArrayBuffer.method5186(local35, true);
	}
@OriginalMember(owner = "client!kc", name = "a", descriptor = "(Lclient!is;Lclient!ya;B)I")
	public static int method3169(@OriginalArg(0) FloType arg0, @OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg1) {
		if (arg0.anInt3372 != -1) {
			return arg0.anInt3372;
		}
		if (arg0.anInt3378 != -1) {
			@Pc(35) MaterialType local35 = arg1.materialProvider.getMetrics(arg1.method4243() ? arg0.anInt3378 : arg0.anInt3375);
			if (!local35.aBoolean639) {
				return local35.colorHsl;
			}
		}
		return arg0.anInt3381;
	}
}
