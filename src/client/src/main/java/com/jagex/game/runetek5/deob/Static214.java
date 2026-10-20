package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;

import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.graphics.JavaBillboardFace;
import com.jagex.game.runetek5.graphics.light.LightGL;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.Class111;
import com.jagex.game.runetek5.deob.Class4_Sub18;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
public final class Static214 {

	@OriginalMember(owner = "client!kq", name = "e", descriptor = "[[[Lclient!nt;")
	public static Class164[][][] aClass164ArrayArrayArray4;

	@OriginalMember(owner = "client!kq", name = "b", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_178 = new Class22(36, 12);

	@OriginalMember(owner = "client!kq", name = "f", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_79 = new Class83("K", "T", "K", "K");

	@OriginalMember(owner = "client!kq", name = "a", descriptor = "(I)V")
	public static void method3224() {
		for (@Pc(14) OggOtherStream local14 = (OggOtherStream) Class126.aClass183_28.method4140(); local14 != null; local14 = (OggOtherStream) Class126.aClass183_28.method4144()) {
			if (Terrain.method5641(local14.anInt5699)) {
				LightGL.method2841(local14);
			}
		}
		if (Skybox.anInt6710 == 1) {
			Class4_Sub6_Sub22.aBoolean411 = false;
			EffectiveVertex.method4103(Class4_Sub37.anInt5424, Class4_Sub18.anInt2392, Static173.anInt3370, Class111.anInt3367);
			return;
		}
		EffectiveVertex.method4103(Class4_Sub37.anInt5424, Class4_Sub18.anInt2392, Static173.anInt3370, Class111.anInt3367);
		@Pc(57) int local57 = Js5ResourceProvider.aClass89_5.getWidthNoSprites(Static391.aClass83_133.method2267(NativeBillboardFace.anInt3423));
		for (@Pc(62) OggOtherStream local62 = (OggOtherStream) Class126.aClass183_28.method4140(); local62 != null; local62 = (OggOtherStream) Class126.aClass183_28.method4144()) {
			@Pc(68) int local68 = JavaBillboardFace.method3197(local62);
			if (local57 < local68) {
				local57 = local68;
			}
		}
		Static173.anInt3370 = local57 + 8;
		Class4_Sub18.anInt2392 = (Class177.aBoolean481 ? 26 : 22) + Skybox.anInt6710 * 16;
	}
}
