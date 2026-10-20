package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.ChatFilter;
import com.jagex.game.runetek5.preference.Preference;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub8_Sub1;
import com.jagex.game.runetek5.deob.Class70_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.toolkit.opengl.effect.BreakingWaterEffect;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class27_Sub4;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.Class95_Sub2;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationTypeList;
import com.jagex.game.runetek5.entity.DynamicWall;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTexture;
import com.jagex.game.runetek5.deob.Class4_Sub21_Sub2;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.config.rsinterface.Component;
public final class Static17 {

	@OriginalMember(owner = "client!ap", name = "a", descriptor = "I")
	public static int anInt220;

	@OriginalMember(owner = "client!ap", name = "c", descriptor = "I")
	public static int anInt222;

	@OriginalMember(owner = "client!ap", name = "d", descriptor = "I")
	public static int anInt223 = 0;

	@OriginalMember(owner = "client!ap", name = "a", descriptor = "(BLclient!ya;)V")
	public static void method177(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		if (Skybox.anInt6710 < 2 && !AbstractArbBufferGL.aBoolean599 || Static281.aClass247_12 != null) {
			return;
		}
		@Pc(38) String local38;
		if (AbstractArbBufferGL.aBoolean599 && Skybox.anInt6710 < 2) {
			local38 = Class230.aString62 + Class14.aClass83_10.method2267(NativeBillboardFace.anInt3423) + Class4_Sub10.aString28 + " ->";
		} else if (Country.aBoolean692 && Static384.aClass244_1.method5489(81) && Skybox.anInt6710 > 2) {
			local38 = Class4_Sub21_Sub2.method3166((OggOtherStream) Class126.aClass183_28.aClass4_207.aClass4_261.aClass4_261);
		} else {
			@Pc(55) OggOtherStream local55 = (OggOtherStream) Class126.aClass183_28.aClass4_207.aClass4_261;
			local38 = Class4_Sub21_Sub2.method3166(local55);
			@Pc(61) int[] local61 = null;
			if (Static441.method5569(local55.anInt5699)) {
				local61 = Terrain.aClass206_3.method4703((int) local55.aLong192).quests;
			} else if (local55.anInt5695 != -1) {
				local61 = Terrain.aClass206_3.method4703(local55.anInt5695).quests;
			} else if (TextureOpBrightness.method4382(local55.anInt5699)) {
				@Pc(89) NPCEntity local89 = Class93.aClass16_Sub1_Sub5_Sub2Array1[(int) local55.aLong192];
				if (local89 != null) {
					@Pc(94) NPCType local94 = local89.npcType;
					if (local94.anIntArray544 != null) {
						local94 = local94.method5574(Class4_Sub6_Sub24.aClass114_1);
					}
					if (local94 != null) {
						local61 = local94.anIntArray547;
					}
				}
			} else if (StructTypeList.method1982(local55.anInt5699)) {
				@Pc(132) LocType local132;
				if (local55.anInt5699 == 1010) {
					local132 = Static267.aClass262_2.method5560((int) local55.aLong192);
				} else {
					local132 = Static267.aClass262_2.method5560((int) (local55.aLong192 >>> 32 & 0x7FFFFFFFL));
				}
				if (local132.alternativeIds != null) {
					local132 = local132.method4584(Class4_Sub6_Sub24.aClass114_1);
				}
				if (local132 != null) {
					local61 = local132.quests;
				}
			}
			if (local61 != null) {
				local38 = local38 + Class259.method5504(local61);
			}
		}
		if (Skybox.anInt6710 > 2) {
			local38 = local38 + "<col=ffffff> / " + (Skybox.anInt6710 - 2) + BreakingWaterEffect.aClass83_31.method2267(NativeBillboardFace.anInt3423);
		}
		if (Static283.aClass247_13 != null) {
			@Pc(252) AbstractFontRenderer local252 = Static283.aClass247_13.method5310(arg0);
			if (local252 == null) {
				local252 = Static331.aClass46_10;
			}
			local252.method5047(Static283.aClass247_13.anInt6838, Static283.aClass247_13.anInt6805, ParticleList.anInt4519, Class27_Sub4.anIntArray338, Class70_Sub2.aClass57Array16, SpotAnimationTypeList.anInt5675, MusicPatch.aRandom1, local38, Static283.aClass247_13.anInt6772, Static283.aClass247_13.anInt6833, Static283.aClass247_13.anInt6778, Static283.aClass247_13.anInt6803, Preference.anIntArray180, Component.anInt6861);
			EffectiveVertex.method4103(Preference.anIntArray180[0], Preference.anIntArray180[3], Preference.anIntArray180[2], Preference.anIntArray180[1]);
		} else if (ChatFilter.aClass247_6 != null && DynamicWall.aClass121_3 == TextureOpTexture.aClass121_1) {
			@Pc(232) int local232 = Static331.aClass46_10.method5053(MusicPatch.aRandom1, Class70_Sub2.aClass57Array16, Class27_Sub4.anIntArray338, ParticleList.anInt4519, local38, Class95_Sub2.anInt6409 + 4, Class4_Sub1_Sub8_Sub1.anInt4896 - -16);
			EffectiveVertex.method4103(Class95_Sub2.anInt6409 + 4, 16, Js5ResourceProvider.aClass89_5.getWidthNoSprites(local38) + local232, Class4_Sub1_Sub8_Sub1.anInt4896);
			return;
		}
	}
}
