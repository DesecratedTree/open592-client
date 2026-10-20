package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.lru.ClientScript;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.deob.Class204;
import com.jagex.game.runetek5.cache.compression.GzipDecompressor;
import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.deob.TextureOpBlend;
import com.jagex.game.runetek5.GlVolumeTexture;
import com.jagex.game.runetek5.graphics.model.particle.PointEntity;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub3;
import com.jagex.game.runetek5.MapRegion;
import com.jagex.game.runetek5.cache.node.lru.ProjectileAnimationNode;
import com.jagex.game.runetek5.deob.Class136;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.deob.Class189;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.graphics.model.normal.ModelNormalsGL;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.graphics.model.Model;
public final class Static211 {

	@OriginalMember(owner = "client!kn", name = "d", descriptor = "I")
	public static int anInt3929;

	@OriginalMember(owner = "client!kn", name = "c", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_41 = new Class215(50, -1);

	@OriginalMember(owner = "client!kn", name = "e", descriptor = "Lclient!ib;")
	public static final CubeMapSpecularEffect aClass102_18 = new CubeMapSpecularEffect(512);

	@OriginalMember(owner = "client!kn", name = "a", descriptor = "(II)Lclient!ae;")
	public static ClientScript method3200(@OriginalArg(0) int arg0) {
		@Pc(10) ClientScript local10 = (ClientScript) GlVolumeTexture.aClass142_1.method3483((long) arg0);
		if (local10 != null) {
			return local10;
		}
		@Pc(25) byte[] local25 = TextureOpBlend.aClass76_45.method2104(0, arg0);
		if (local25 == null || local25.length <= 1) {
			return null;
		} else {
			local10 = ModelNormalsGL.method3441(local25);
			GlVolumeTexture.aClass142_1.method3480((long) arg0, local10);
			return local10;
		}
	}

	@OriginalMember(owner = "client!kn", name = "a", descriptor = "(ZZ)Z")
	public static boolean method3201(@OriginalArg(0) boolean arg0, @OriginalArg(1) boolean arg1) {
		return arg0 | arg1;
	}

	@OriginalMember(owner = "client!kn", name = "a", descriptor = "(I)V")
	public static void method3203() {
		MapRegion.anInt7307 = Class14.aClass4_Sub12_Sub1_3.method2490();
		@Pc(20) int local20 = Class14.aClass4_Sub12_Sub1_3.method2515();
		@Pc(29) boolean local29 = Class14.aClass4_Sub12_Sub1_3.method2489() == 1;
		@Pc(35) int local35 = Class14.aClass4_Sub12_Sub1_3.method2548();
		@Pc(39) int local39 = Class14.aClass4_Sub12_Sub1_3.method2489();
		Class4_Sub6_Sub25.method3809(local39);
		Class14.aClass4_Sub12_Sub1_3.method1136();
		@Pc(51) int local51;
		@Pc(55) int local55;
		@Pc(62) int local62;
		for (@Pc(47) int local47 = 0; local47 < 4; local47++) {
			for (local51 = 0; local51 < GLBillowTextureData.anInt5666 >> 3; local51++) {
				for (local55 = 0; local55 < Static283.anInt5187 >> 3; local55++) {
					local62 = Class14.aClass4_Sub12_Sub1_3.method1143(1);
					if (local62 == 1) {
						Class189.anIntArrayArrayArray12[local47][local51][local55] = Class14.aClass4_Sub12_Sub1_3.method1143(26);
					} else {
						Class189.anIntArrayArrayArray12[local47][local51][local55] = -1;
					}
				}
			}
		}
		Class14.aClass4_Sub12_Sub1_3.method1135();
		local51 = (PointEntity.anInt4075 - Class14.aClass4_Sub12_Sub1_3.currentPosition) / 16;
		Model.anIntArrayArray50 = new int[local51][4];
		for (local55 = 0; local55 < local51; local55++) {
			for (local62 = 0; local62 < 4; local62++) {
				Model.anIntArrayArray50[local55][local62] = Class14.aClass4_Sub12_Sub1_3.method2529();
			}
		}
		PlayerVarDomain.anIntArray222 = null;
		Static308.anIntArray372 = new int[local51];
		Class99_Sub1.aByteArrayArray15 = new byte[local51][];
		ReflectionCheck.aByteArrayArray1 = null;
		Class136.anIntArray291 = new int[local51];
		ProjectileAnimationNode.anIntArray155 = new int[local51];
		GlFramebuffer.aByteArrayArray16 = new byte[local51][];
		Game.anIntArray236 = new int[local51];
		Class259.aByteArrayArray30 = new byte[local51][];
		Class16_Sub4_Sub3.aByteArrayArray13 = new byte[local51][];
		Class204.anIntArray418 = new int[local51];
		local51 = 0;
		for (local62 = 0; local62 < 4; local62++) {
			for (@Pc(185) int local185 = 0; local185 < GLBillowTextureData.anInt5666 >> 3; local185++) {
				for (@Pc(189) int local189 = 0; local189 < Static283.anInt5187 >> 3; local189++) {
					@Pc(199) int local199 = Class189.anIntArrayArrayArray12[local62][local185][local189];
					if (local199 != -1) {
						@Pc(209) int local209 = local199 >> 14 & 0x3FF;
						@Pc(215) int local215 = local199 >> 3 & 0x7FF;
						@Pc(225) int local225 = local215 / 8 + (local209 / 8 << 8);
						for (@Pc(227) int local227 = 0; local227 < local51; local227++) {
							if (Static308.anIntArray372[local227] == local225) {
								local225 = -1;
								break;
							}
						}
						if (local225 != -1) {
							Static308.anIntArray372[local51] = local225;
							@Pc(256) int local256 = local225 >> 8 & 0xFF;
							@Pc(260) int local260 = local225 & 0xFF;
							Class136.anIntArray291[local51] = Class4_Sub10.aClass76_27.method2099("m" + local256 + "_" + local260);
							Class204.anIntArray418[local51] = Class4_Sub10.aClass76_27.method2099("l" + local256 + "_" + local260);
							ProjectileAnimationNode.anIntArray155[local51] = Class4_Sub10.aClass76_27.method2099("um" + local256 + "_" + local260);
							Game.anIntArray236[local51] = Class4_Sub10.aClass76_27.method2099("ul" + local256 + "_" + local260);
							local51++;
						}
					}
				}
			}
		}
		GzipDecompressor.method4167(false, local20, local35, local29);
	}
}
