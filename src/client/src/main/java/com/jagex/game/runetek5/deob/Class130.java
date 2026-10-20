package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.enumtype.EnumType;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.config.quickchat.QuickchatFillerList;
import com.jagex.game.runetek5.HelveticaFont;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.client.GameShell;
import com.jagex.game.runetek5.deob.Class267;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlElementsArrayBuffer;
import com.jagex.game.runetek5.cache.node.GlGroundLayer;
import com.jagex.game.runetek5.deob.TextureCombinerOp;
import com.jagex.game.runetek5.deob.Class4_Sub19;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.cache.node.MonochromeImageCacheSlot;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.config.enumtype.EnumTypeList;
import com.jagex.game.runetek5.deob.Class222;
import com.jagex.game.runetek5.deob.ObjStackEntity;
import com.jagex.game.runetek5.graphics.sprite.SpriteDrawQueue;
import com.jagex.game.runetek5.graphics.texture.RectangularTextureGL;
@OriginalClass("client!ko")
public final class Class130 implements QuickchatFillerList {

	static {
		new Class83("You are not currently in a channel.", "Du befindest dich derzeit nicht in einem Chatraum.", "Vous n'êtes dans aucun canal à l'heure actuelle.", "No momento você não está em um canal.");
	}

	@OriginalMember(owner = "client!ko", name = "a", descriptor = "(JB[ILclient!ho;)Ljava/lang/String;")
	@Override
	public String method3205(@OriginalArg(0) long arg0, @OriginalArg(2) int[] arg1, @OriginalArg(3) QuickChatValueType arg2) {
		if (GlElementsArrayBuffer.aClass98_13 == arg2) {
			@Pc(15) EnumType local15 = PingRequest.aClass158_1.method3790(arg1[0]);
			return local15.getString((int) arg0);
		} else if (Class267.aClass98_18 == arg2 || arg2 == Static340.aClass98_15) {
			@Pc(40) ItemType local40 = Terrain.aClass206_3.method4703((int) arg0);
			return local40.name;
		} else if (arg2 == TextureCombinerOp.aClass98_11 || SpriteDrawQueue.OPPLAYER8 == arg2 || arg2 == ObjStackEntity.aClass98_3) {
			return PingRequest.aClass158_1.method3790(arg1[0]).getString((int) arg0);
		} else {
			return null;
		}
	}

@OriginalMember(owner = "client!ko", name = "c", descriptor = "I")
	public static int anInt3932 = 0;
@OriginalMember(owner = "client!ko", name = "a", descriptor = "(II)I")
	public static int method3206(@OriginalArg(0) int arg0) {
		return arg0 >>> 10;
	}
@OriginalMember(owner = "client!ko", name = "a", descriptor = "(IIIIZIILjava/lang/String;)V")
	public static void method3207(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) String arg6) {
		@Pc(7) TileMessage local7 = new TileMessage();
		local7.anInt7376 = arg0;
		local7.anInt7372 = arg2;
		local7.anInt7367 = Shadow.anInt5323 + arg1;
		local7.anInt7374 = arg4;
		local7.aString71 = arg6;
		local7.anInt7366 = arg3;
		local7.anInt7375 = arg5;
		GameShell.aClass84_4.offer(local7);
	}
@OriginalMember(owner = "client!ko", name = "a", descriptor = "(IB)Lclient!sb;")
	public static HelveticaFont method3208(@OriginalArg(0) int arg0) {
		if (arg0 == 0) {
			if ((double) Static148.mapZoom == 3.0D) {
				return Static258.aClass212_6;
			}
			if ((double) Static148.mapZoom == 4.0D) {
				return Class222.aClass212_8;
			}
			if ((double) Static148.mapZoom == 6.0D) {
				return ParticleList.aClass212_5;
			}
			if ((double) Static148.mapZoom >= 8.0D) {
				return RectangularTextureGL.aClass212_3;
			}
		} else if (arg0 == 1) {
			if ((double) Static148.mapZoom == 3.0D) {
				return ParticleList.aClass212_5;
			}
			if ((double) Static148.mapZoom == 4.0D) {
				return RectangularTextureGL.aClass212_3;
			}
			if ((double) Static148.mapZoom == 6.0D) {
				return EnumTypeList.aClass212_7;
			}
			if ((double) Static148.mapZoom >= 8.0D) {
				return GlGroundLayer.aClass212_2;
			}
		} else if (arg0 == 2) {
			if ((double) Static148.mapZoom == 3.0D) {
				return EnumTypeList.aClass212_7;
			}
			if ((double) Static148.mapZoom == 4.0D) {
				return GlGroundLayer.aClass212_2;
			}
			if ((double) Static148.mapZoom == 6.0D) {
				return MonochromeImageCacheSlot.aClass212_1;
			}
			if ((double) Static148.mapZoom >= 8.0D) {
				return Class4_Sub19.aClass212_4;
			}
		}
		return null;
	}
}
