package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;


import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.graphics.model.billboard.BillboardType;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class70_Sub2;
import com.jagex.game.runetek5.js5.request.local.LocalRequester;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSquareWaveform;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpVoronoiNoise;
@OriginalClass("client!vv")
public final class Class259 {

	static {
		new Class83(null, "der Spieler ist momentan nicht verfügbar.", null, null);
	}

@OriginalMember(owner = "client!vv", name = "d", descriptor = "[[B")
	public static byte[][] aByteArrayArray30;
@OriginalMember(owner = "client!vv", name = "f", descriptor = "I")
	public static int anInt7099;
@OriginalMember(owner = "client!vv", name = "b", descriptor = "[B")
	public static final byte[] aByteArray103 = new byte[] { 1, 1, 0, 0, 0, 8, 0, 0, 8 };
@OriginalMember(owner = "client!vv", name = "e", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_328 = new Class22(4, -1);
@OriginalMember(owner = "client!vv", name = "h", descriptor = "Lclient!bf;")
	public static final Class21 aClass21_17 = new Class21(13, 3);
@OriginalMember(owner = "client!vv", name = "a", descriptor = "(ZI)Lclient!jg;")
	public static BillboardType method5501(@OriginalArg(1) int arg0) {
		@Pc(17) BillboardType local17 = (BillboardType) TextureOpSquareWaveform.aClass68_53.method1787((long) arg0);
		if (local17 != null) {
			return local17;
		}
		@Pc(27) byte[] local27 = Class183.aClass76_65.method2104(arg0, 0);
		local17 = new BillboardType();
		if (local27 != null) {
			local17.method2922(arg0, new Packet(local27));
		}
		TextureOpSquareWaveform.aClass68_53.method1779((long) arg0, local17);
		return local17;
	}
@OriginalMember(owner = "client!vv", name = "a", descriptor = "(JI)V")
	public static void method5503(@OriginalArg(0) long arg0) {
		if (arg0 <= 0L) {
			return;
		}
		if (arg0 % 10L == 0L) {
			TextureOpVoronoiNoise.method5115(arg0 - 1L);
			TextureOpVoronoiNoise.method5115(1L);
		} else {
			TextureOpVoronoiNoise.method5115(arg0);
		}
	}
@OriginalMember(owner = "client!vv", name = "a", descriptor = "([II)Ljava/lang/String;")
	public static String method5504(@OriginalArg(0) int[] arg0) {
		@Pc(14) StringBuffer local14 = new StringBuffer();
		@Pc(16) int local16 = Static277.anInt5150;
		for (@Pc(18) int local18 = 0; local18 < arg0.length; local18++) {
			@Pc(27) Class101 local27 = LocalRequester.aClass208_1.method4725(arg0[local18]);
			if (local27.anInt3153 != -1) {
				@Pc(39) NativeSprite local39 = (NativeSprite) Static105.aClass68_9.method1787((long) local27.anInt3153);
				if (local39 == null) {
					@Pc(47) SpriteType local47 = SpriteType.listSingle(GlGround.aClass76_60, local27.anInt3153, 0);
					if (local47 != null) {
						local39 = MonoStereoData.aClass19_16.generateSprite(local47);
						Static105.aClass68_9.method1779((long) local27.anInt3153, local39);
					}
				}
				if (local39 != null) {
					Class70_Sub2.aClass57Array16[local16] = local39;
					local14.append(" <img=").append(local16).append(">");
					local16++;
				}
			}
		}
		return local14.toString();
	}
}
