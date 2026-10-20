package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import java.net.URL;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.cache.node.lru.ClientScript;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteType;
import com.jagex.game.runetek5.deob.Class189;
import com.jagex.game.runetek5.deob.Class191;
import com.jagex.game.runetek5.deob.Class70_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub8;
import com.jagex.game.runetek5.deob.Class222;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!ie")
public abstract class Class105 {

	static {
		new Class83("Unable to add name - system busy.", "Der Name konnte nicht hinzugefügt werden, das System ist derzeit ausgelastet.", "Impossible d'ajouter un nom - système occupé.", "Não foi possível adicionar o nome. O sistema está ocupado.");
	}

	@OriginalMember(owner = "client!ie", name = "a", descriptor = "(Lclient!mh;I)Lclient!mh;")
	public abstract Class4_Sub1_Sub9 method5276(@OriginalArg(0) Class4_Sub1_Sub9 arg0);

@OriginalMember(owner = "client!ie", name = "d", descriptor = "[[[I")
	public static int[][][] anIntArrayArrayArray13;
@OriginalMember(owner = "client!ie", name = "a", descriptor = "J")
	public static long aLong210 = 0L;
@OriginalMember(owner = "client!ie", name = "e", descriptor = "Z")
	public static boolean aBoolean607 = false;
@OriginalMember(owner = "client!ie", name = "f", descriptor = "I")
	public static int anInt6744 = 0;
@OriginalMember(owner = "client!ie", name = "a", descriptor = "(II)I")
	public static int method5277(@OriginalArg(0) int arg0) {
		@Pc(14) int local14 = (arg0 & 0x55555555) + ((arg0 & 0xAAAAAAAB) >>> 1);
		@Pc(24) int local24 = (local14 & 0x33333333) + (local14 >>> 2 & 0x73333333);
		@Pc(32) int local32 = local24 + (local24 >>> 4) & 0xF0F0F0F;
		@Pc(38) int local38 = local32 + (local32 >>> 8);
		@Pc(44) int local44 = local38 + (local38 >>> 16);
		return local44 & 0xFF;
	}
@OriginalMember(owner = "client!ie", name = "a", descriptor = "(ZI)Z")
	public static boolean method5279(@OriginalArg(1) int arg0) {
		@Pc(7) Class78_Sub1 local7 = Class126.method3192(arg0);
		if (local7 == null) {
			return false;
		} else if (SignLink.anInt1987 == 3) {
			@Pc(39) String local39 = "";
			if (Class222.aClass219_6 != GlToolkit.aClass219_2) {
				local39 = ":" + (local7.anInt3881 + 7000);
			}
			@Pc(55) String local55 = "";
			if (ReflectionCheck.aString13 != null) {
				local55 = "/p=" + ReflectionCheck.aString13;
			}
			@Pc(104) String local104 = "http://" + local7.aString38 + local39 + "/l=" + NativeBillboardFace.anInt3423 + "/a=" + GlCubeTexture.anInt3259 + local55 + "/j" + (Class70_Sub2.aBoolean591 ? "1" : "0") + ",o" + (aBoolean607 ? "1" : "0") + ",a2";
			try {
				ClientScript.aClient1.getAppletContext().showDocument(new URL(local104), "_self");
				return true;
			} catch (@Pc(119) Exception local119) {
				return false;
			}
		} else {
			MapSpriteType.anInt1028 = local7.anInt3881;
			Class191.aString54 = local7.aString38;
			if (GlToolkit.aClass219_2 != Class222.aClass219_6) {
				Class4_Sub6_Sub8.anInt1949 = MapSpriteType.anInt1028 + 50000;
				Class189.anInt5436 = MapSpriteType.anInt1028 + 40000;
				Skybox.anInt6723 = Class189.anInt5436;
			}
			return true;
		}
	}
}
