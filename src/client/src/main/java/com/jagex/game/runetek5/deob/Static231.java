package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;

import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.deob.SpotAnimation;
import com.jagex.game.runetek5.cache.node.MonochromeImageCacheSlot;
import com.jagex.game.runetek5.deob.Class120_Sub1;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.graphics.texture.water.BillowNoiseGenerator;
import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.rsinterface.Component;
public final class Static231 {

	@OriginalMember(owner = "client!lr", name = "d", descriptor = "I")
	public static int anInt4434;

	@OriginalMember(owner = "client!lr", name = "e", descriptor = "F")
	public static float aFloat138;

	@OriginalMember(owner = "client!lr", name = "b", descriptor = "[I")
	public static int[] anIntArray298 = new int[1];

	@OriginalMember(owner = "client!lr", name = "a", descriptor = "(IIIIZ)V")
	public static void method3474(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) boolean arg3) {
		if (Packet.anInt2973 == 0) {
			Class4_Sub7.method633(false);
		} else {
			BillowNoiseGenerator.anInt5660 = Packet.anInt2973;
			ParticleList.method3551(0);
		}
		MidiPcmStream.anInt1620 = arg1;
		Class120_Sub1.anInt5361 = arg0;
		MonochromeImageCacheSlot.aBoolean98 = arg3;
		Static148.method4492(arg2);
	}

	@OriginalMember(owner = "client!lr", name = "a", descriptor = "(B)V")
	public static void method3475() {
		@Pc(17) Class4_Sub5 local17 = (Class4_Sub5) Component.aClass183_47.method4140();
		@Pc(28) boolean local28 = Static281.aClass247_12 != null || SpotAnimation.anInt1367 > 0;
		if (local28) {
			Mesh.anInt3685 = 1;
		}
		if (Country.aBoolean692 && Static384.aClass244_1.method5489(81) && Skybox.anInt6710 > 2) {
			if (local28) {
				Component.aClass4_Sub39_2 = (OggOtherStream) Class126.aClass183_28.aClass4_207.aClass4_261.aClass4_261;
			} else {
				GlFramebuffer.method3617(local17.method519(), (OggOtherStream) Class126.aClass183_28.aClass4_207.aClass4_261.aClass4_261, local17.method518());
			}
		} else if (local28) {
			Component.aClass4_Sub39_2 = (OggOtherStream) Class126.aClass183_28.aClass4_207.aClass4_261;
		} else {
			GlFramebuffer.method3617(local17.method519(), (OggOtherStream) Class126.aClass183_28.aClass4_207.aClass4_261, local17.method518());
		}
	}
}
