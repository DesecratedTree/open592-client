package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.entity.ComparableGraphNode;
import com.jagex.game.runetek5.preference.Preference;

import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.cache.compression.GzipDecompressor;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.config.material.texture.MonochromeImageCache;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.graphics.model.particle.PointEntity;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.FixedFunctionSpecularPass;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.OpenGLArbTextureCubeMapGenerator;
import com.jagex.game.runetek5.deob.TextureOpBoxBlur;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub37;
import com.jagex.game.runetek5.deob.ObjStackEntity;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.deob.TileMessage;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.config.idktype.IDKTypeList;
import com.jagex.game.runetek5.deob.ReflectingTextureEffect;
import com.jagex.game.runetek5.buffer.Packet;
@OriginalClass("client!mt")
public final class Class154 implements ComparableGraphNode {

	@OriginalMember(owner = "client!mt", name = "c", descriptor = "I")
	public int anInt4758;

	@OriginalMember(owner = "client!mt", name = "d", descriptor = "I")
	public int anInt4759;

	@OriginalMember(owner = "client!mt", name = "f", descriptor = "I")
	public int anInt4761;

	@OriginalMember(owner = "client!mt", name = "g", descriptor = "I")
	public int anInt4762;

	@OriginalMember(owner = "client!mt", name = "i", descriptor = "Z")
	public boolean aBoolean438;

	@OriginalMember(owner = "client!mt", name = "k", descriptor = "I")
	public int anInt4765;

	@OriginalMember(owner = "client!mt", name = "p", descriptor = "I")
	public int anInt4769;

	static {
		new Class83("You are temporarily blocked from joining channels - please try again later!", "Du darfst derzeit keine Chaträume betreten - bitte versuch es später.", "Vous êtes temporairement exclu des canaux - veuillez réessayer ultérieurement.", "Você está temporariamente impedido de entrar em canais. Tente de novo depois!");
	}

	@OriginalMember(owner = "client!mt", name = "a", descriptor = "(B)J")
	@Override
	public long toLong() {
		@Pc(5) long[] local5 = Preference.aLongArray4;
		@Pc(7) long local7 = -1L;
		@Pc(22) long local22 = local7 >>> 8 ^ local5[(int) ((local7 ^ (long) this.anInt4762) & 0xFFL)];
		@Pc(45) long local45 = local5[(int) (((long) (this.anInt4759 >> 8) ^ local22) & 0xFFL)] ^ local22 >>> 8;
		@Pc(60) long local60 = local5[(int) (((long) this.anInt4759 ^ local45) & 0xFFL)] ^ local45 >>> 8;
		@Pc(77) long local77 = local60 >>> 8 ^ local5[(int) (((long) (this.anInt4758 >> 24) ^ local60) & 0xFFL)];
		@Pc(94) long local94 = local5[(int) ((local77 ^ (long) (this.anInt4758 >> 16)) & 0xFFL)] ^ local77 >>> 8;
		@Pc(111) long local111 = local94 >>> 8 ^ local5[(int) (((long) (this.anInt4758 >> 8) ^ local94) & 0xFFL)];
		@Pc(126) long local126 = local5[(int) (((long) this.anInt4758 ^ local111) & 0xFFL)] ^ local111 >>> 8;
		@Pc(141) long local141 = local126 >>> 8 ^ local5[(int) ((local126 ^ (long) this.anInt4761) & 0xFFL)];
		@Pc(158) long local158 = local141 >>> 8 ^ local5[(int) ((local141 ^ (long) (this.anInt4765 >> 24)) & 0xFFL)];
		@Pc(175) long local175 = local5[(int) ((local158 ^ (long) (this.anInt4765 >> 16)) & 0xFFL)] ^ local158 >>> 8;
		@Pc(192) long local192 = local5[(int) ((local175 ^ (long) (this.anInt4765 >> 8)) & 0xFFL)] ^ local175 >>> 8;
		@Pc(207) long local207 = local192 >>> 8 ^ local5[(int) (((long) this.anInt4765 ^ local192) & 0xFFL)];
		@Pc(222) long local222 = local5[(int) ((local207 ^ (long) this.anInt4769) & 0xFFL)] ^ local207 >>> 8;
		return local5[(int) (((long) (this.aBoolean438 ? 1 : 0) ^ local222) & 0xFFL)] ^ local222 >>> 8;
	}

	@OriginalMember(owner = "client!mt", name = "a", descriptor = "(Lclient!bq;I)Z")
	@Override
	public boolean method3684(@OriginalArg(0) ComparableGraphNode arg0) {
		if (!(arg0 instanceof Class154)) {
			return false;
		}
		@Pc(11) Class154 local11 = (Class154) arg0;
		if (this.anInt4762 != local11.anInt4762) {
			return false;
		} else if (this.anInt4759 != local11.anInt4759) {
			return false;
		} else if (this.anInt4758 != local11.anInt4758) {
			return false;
		} else if (local11.anInt4761 != this.anInt4761) {
			return false;
		} else if (this.anInt4765 != local11.anInt4765) {
			return false;
		} else if (local11.anInt4769 == this.anInt4769) {
			return this.aBoolean438 == local11.aBoolean438;
		} else {
			return false;
		}
	}

@OriginalMember(owner = "client!mt", name = "o", descriptor = "[I")
	public static int[] anIntArray312;
@OriginalMember(owner = "client!mt", name = "a", descriptor = "(IIB)V")
	public static void method3686(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		@Pc(13) Class4_Sub1_Sub11 local13 = NPCTypeList.method5222(arg1, 7);
		local13.method2604();
		local13.anInt3083 = arg0;
	}
@OriginalMember(owner = "client!mt", name = "a", descriptor = "(Z)V")
	public static void method3687() {
		if (TextureOpBrightness.aClass50_Sub1_1.method2861(Packet.anInt2973) || FixedFunctionSpecularPass.anInt5523 == Class4_Sub6_Sub25.anInt4963) {
			Class9.method5372(MonoStereoData.aClass19_16);
			if (Class4_Sub6_Sub25.anInt4963 != ReflectingTextureEffect.anInt5744) {
				OpenGLArbTextureCubeMapGenerator.method3238();
			}
		} else {
			GzipDecompressor.method4167(false, TileMessage.anInt7373, IDKTypeList.anInt3265, false);
		}
	}
@OriginalMember(owner = "client!mt", name = "a", descriptor = "(ZB)V")
	public static void method3689(@OriginalArg(0) boolean arg0) {
		while (true) {
			if (Class14.aClass4_Sub12_Sub1_3.method1137(PointEntity.anInt4075) >= 15) {
				@Pc(22) int local22 = Class14.aClass4_Sub12_Sub1_3.method1143(15);
				if (local22 != 32767) {
					@Pc(27) boolean local27 = false;
					if (Class93.aClass16_Sub1_Sub5_Sub2Array1[local22] == null) {
						Class93.aClass16_Sub1_Sub5_Sub2Array1[local22] = new NPCEntity();
						Class93.aClass16_Sub1_Sub5_Sub2Array1[local22].anInt6037 = local22;
						local27 = true;
					}
					@Pc(48) NPCEntity local48 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local22];
					TextureOpBoxBlur.anIntArray412[Class10.anInt163++] = local22;
					local48.anInt6015 = Shadow.anInt5323;
					if (local48.npcType != null && local48.npcType.method5578()) {
						Class4_Sub6_Sub37.method5429(local48);
					}
					@Pc(75) int local75 = Class14.aClass4_Sub12_Sub1_3.method1143(1);
					@Pc(80) int local80 = Class14.aClass4_Sub12_Sub1_3.method1143(2);
					@Pc(85) int local85 = Class14.aClass4_Sub12_Sub1_3.method1143(1);
					if (local85 == 1) {
						LocTypeList.mapElementLocIds[ObjStackEntity.anInt759++] = local22;
					}
					@Pc(105) int local105;
					if (arg0) {
						local105 = Class14.aClass4_Sub12_Sub1_3.method1143(8);
						if (local105 > 127) {
							local105 -= 256;
						}
					} else {
						local105 = Class14.aClass4_Sub12_Sub1_3.method1143(5);
						if (local105 > 15) {
							local105 -= 32;
						}
					}
					@Pc(131) int local131;
					if (arg0) {
						local131 = Class14.aClass4_Sub12_Sub1_3.method1143(8);
						if (local131 > 127) {
							local131 -= 256;
						}
					} else {
						local131 = Class14.aClass4_Sub12_Sub1_3.method1143(5);
						if (local131 > 15) {
							local131 -= 32;
						}
					}
					@Pc(161) int local161 = Class14.aClass4_Sub12_Sub1_3.method1143(3) + 4 << 11 & 0x391E;
					local48.method4766(Class196.aClass240_1.method5229(Class14.aClass4_Sub12_Sub1_3.method1143(14)));
					local48.setSize(local48.npcType.anInt7201);
					local48.anInt6080 = local48.npcType.anInt7221 << 3;
					if (local48.anInt6080 == 0) {
						local48.method4745(0);
					} else if (local27) {
						local48.method4745(local161);
					}
					local48.clearPath(local80, local131 + Static1.aClass16_Sub1_Sub5_Sub1_1.movementBaseX[0], local48.getSize(), local75 == 1, local105 + Static1.aClass16_Sub1_Sub5_Sub1_1.movementBaseY[0]);
					if (local48.npcType.method5578()) {
						MonochromeImageCache.method2264(local48.movementBaseX[0], local48, null, local48.movementBaseY[0], 0, local48.aByte82, null);
					}
					continue;
				}
			}
			Class14.aClass4_Sub12_Sub1_3.method1135();
			return;
		}
	}
@OriginalMember(owner = "client!mt", name = "a", descriptor = "(BLjava/lang/String;C)I")
	public static int method3690(@OriginalArg(1) String arg0, @OriginalArg(2) char arg1) {
		@Pc(5) int local5 = 0;
		@Pc(8) int local8 = arg0.length();
		for (@Pc(10) int local10 = 0; local10 < local8; local10++) {
			if (arg0.charAt(local10) == arg1) {
				local5++;
			}
		}
		return local5;
	}
@OriginalMember(owner = "client!mt", name = "a", descriptor = "(ZIB)Ljava/lang/String;")
	public static String method3691(@OriginalArg(1) int arg0) {
		return arg0 < 0 ? Integer.toString(arg0) : Class215.method4925(arg0);
	}
}
