package com.jagex.game.runetek5.deob;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.graphics.texture.water.NativeWaterTexture;
import com.jagex.game.runetek5.toolkit.opengl.GlRenderList;

import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.particle.ParticleProducerType;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.material.texture.TextureOpFlip;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteType;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.LegacyMouseManager;
@OriginalClass("client!nu")
public final class WaterfallEffect extends Class9 {

	@OriginalMember(owner = "client!nu", name = "s", descriptor = "Lclient!m;")
	private final NativeWaterTexture aClass144_5;

	@OriginalMember(owner = "client!nu", name = "j", descriptor = "Lclient!ct;")
	private final GlRenderList aClass43_4;

	static {
		new Class83("For that rule you can only report players who have spoken or traded recently.", "Mit dieser Option können nur Spieler gemeldet werden,", "Cette règle n'est invocable que pour les discussions ou échanges récents.", "Para essa regra, você só pode denunciar jogadores com quem tenha falado ou negociado recentemente.");
		new Class83("You already sent an abuse report under 60 secs ago! Do not abuse this system!", "Du hast bereits vor weniger als 60 Sekunden einen Regelverstoß gemeldet!", "Vous avez déjà signalé un abus il y a moins d'une minute ! N'abusez pas du système !", "Você já enviou uma denúncia de abuso há menos de um minuto. Não abuse deste sistema!");
	}

	@OriginalMember(owner = "client!nu", name = "<init>", descriptor = "(Lclient!fd;Lclient!m;)V")
	public WaterfallEffect(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) NativeWaterTexture arg1) {
		super(arg0);
		this.aClass144_5 = arg1;
		this.aClass43_4 = new GlRenderList(arg0, 2);
		this.aClass43_4.method990(0);
		super.aClass19_Sub2_41.method1936(1);
		if (this.aClass144_5.hasDepth) {
			OpenGL.glTexGeni(8194, 9472, 9217);
			OpenGL.glEnable(3170);
		}
		OpenGL.glTexGeni(8192, 9472, 9216);
		OpenGL.glTexGeni(8193, 9472, 9216);
		OpenGL.glEnable(3168);
		OpenGL.glEnable(3169);
		super.aClass19_Sub2_41.method1936(0);
		this.aClass43_4.method991();
		this.aClass43_4.method990(1);
		super.aClass19_Sub2_41.method1936(1);
		if (this.aClass144_5.hasDepth) {
			OpenGL.glDisable(3170);
		}
		OpenGL.glDisable(3168);
		OpenGL.glDisable(3169);
		super.aClass19_Sub2_41.method1936(0);
		this.aClass43_4.method991();
	}

	@OriginalMember(owner = "client!nu", name = "a", descriptor = "(ILclient!kj;Z)V")
	@Override
	public void method5376(@OriginalArg(0) int arg0, @OriginalArg(1) Class6 arg1) {
		super.aClass19_Sub2_41.setActiveTexture(arg1);
		super.aClass19_Sub2_41.method1900(arg0);
	}

	@OriginalMember(owner = "client!nu", name = "a", descriptor = "(I)V")
	@Override
	public void disable() {
		this.aClass43_4.method989('\u0001');
		super.aClass19_Sub2_41.method1936(1);
		super.aClass19_Sub2_41.setActiveTexture(null);
		super.aClass19_Sub2_41.method1936(0);
	}

	@OriginalMember(owner = "client!nu", name = "a", descriptor = "(III)V")
	@Override
	public void setEffectParams(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		@Pc(14) float local14 = (float) ((arg0 & 0x3) + 1) * -5.0E-4F;
		@Pc(25) float local25 = (float) ((arg0 >> 3 & 0x3) + 1) * 5.0E-4F;
		@Pc(36) float local36 = (arg0 & 0x40) == 0 ? 4.8828125E-4F : 9.765625E-4F;
		super.aClass19_Sub2_41.method1936(1);
		@Pc(49) boolean local49 = (arg0 & 0x80) != 0;
		if (local49) {
			TextureOpFlip.aFloatArray22[3] = 0.0F;
			TextureOpFlip.aFloatArray22[2] = 0.0F;
			TextureOpFlip.aFloatArray22[1] = 0.0F;
			TextureOpFlip.aFloatArray22[0] = local36;
		} else {
			TextureOpFlip.aFloatArray22[2] = local36;
			TextureOpFlip.aFloatArray22[0] = 0.0F;
			TextureOpFlip.aFloatArray22[1] = 0.0F;
			TextureOpFlip.aFloatArray22[3] = 0.0F;
		}
		OpenGL.glTexGenfv(8192, 9474, TextureOpFlip.aFloatArray22, 0);
		TextureOpFlip.aFloatArray22[3] = local14 * (float) super.aClass19_Sub2_41.anInt2274 % 1.0F;
		TextureOpFlip.aFloatArray22[0] = 0.0F;
		TextureOpFlip.aFloatArray22[1] = local36;
		TextureOpFlip.aFloatArray22[2] = 0.0F;
		OpenGL.glTexGenfv(8193, 9474, TextureOpFlip.aFloatArray22, 0);
		if (this.aClass144_5.hasDepth) {
			TextureOpFlip.aFloatArray22[3] = (float) super.aClass19_Sub2_41.anInt2274 * local25 % 1.0F;
			TextureOpFlip.aFloatArray22[1] = 0.0F;
			TextureOpFlip.aFloatArray22[2] = 0.0F;
			TextureOpFlip.aFloatArray22[0] = 0.0F;
			OpenGL.glTexGenfv(8194, 9473, TextureOpFlip.aFloatArray22, 0);
		} else {
			@Pc(162) int local162 = (int) ((float) super.aClass19_Sub2_41.anInt2274 * 16.0F * local25);
			super.aClass19_Sub2_41.setActiveTexture(this.aClass144_5.textureSamplers[local162 % 16]);
		}
		super.aClass19_Sub2_41.method1936(0);
	}

	@OriginalMember(owner = "client!nu", name = "a", descriptor = "(ZB)V")
	@Override
	public void applyTextureCombine(@OriginalArg(0) boolean arg0) {
	}

	@OriginalMember(owner = "client!nu", name = "a", descriptor = "(Z)Z")
	@Override
	public boolean isSupported() {
		return true;
	}

	@OriginalMember(owner = "client!nu", name = "a", descriptor = "(IZ)V")
	@Override
	public void enable(@OriginalArg(1) boolean arg0) {
		this.aClass43_4.method989('\u0000');
		if (this.aClass144_5.hasDepth) {
			super.aClass19_Sub2_41.method1936(1);
			super.aClass19_Sub2_41.setActiveTexture(this.aClass144_5.aClass6_Sub3_1);
			super.aClass19_Sub2_41.method1936(0);
		}
	}

@OriginalMember(owner = "client!nu", name = "t", descriptor = "I")
	public static int anInt5136;
@OriginalMember(owner = "client!nu", name = "o", descriptor = "I")
	public static int anInt5132 = 0;
@OriginalMember(owner = "client!nu", name = "b", descriptor = "(I)V")
	public static void method3926() {
		try {
			if (Class21.anInt461 == 1) {
				@Pc(12) int local12 = MapSpriteType.aClass4_Sub15_Sub1_1.method1437();
				if (local12 > 0 && MapSpriteType.aClass4_Sub15_Sub1_1.method1434()) {
					local12 -= Static47.anInt811;
					if (local12 < 0) {
						local12 = 0;
					}
					MapSpriteType.aClass4_Sub15_Sub1_1.method1428(local12);
					return;
				}
				MapSpriteType.aClass4_Sub15_Sub1_1.method1440();
				MapSpriteType.aClass4_Sub15_Sub1_1.method1441();
				StillWaterEffect.aClass171_1 = null;
				if (NativeWaterTexture.aClass76_49 == null) {
					Class21.anInt461 = 0;
				} else {
					Class21.anInt461 = 2;
				}
				Class93.aClass4_Sub30_2 = null;
			}
		} catch (@Pc(61) Exception local61) {
			local61.printStackTrace();
			MapSpriteType.aClass4_Sub15_Sub1_1.method1440();
			NativeWaterTexture.aClass76_49 = null;
			Class21.anInt461 = 0;
			StillWaterEffect.aClass171_1 = null;
			Class93.aClass4_Sub30_2 = null;
		}
	}
@OriginalMember(owner = "client!nu", name = "a", descriptor = "(II)Lclient!jv;")
	public static ParticleProducerType method3927(@OriginalArg(1) int arg0) {
		@Pc(10) ParticleProducerType local10 = (ParticleProducerType) LegacyMouseManager.aClass68_43.method1787((long) arg0);
		if (local10 != null) {
			return local10;
		}
		@Pc(20) byte[] local20 = Class14.aClass76_4.method2104(arg0, 0);
		local10 = new ParticleProducerType();
		if (local20 != null) {
			local10.method3114(new Packet(local20));
		}
		local10.postDecode();
		LegacyMouseManager.aClass68_43.method1779((long) arg0, local10);
		return local10;
	}
}
