package com.jagex.game.runetek5.config.quickchat;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static283;
import com.jagex.game.runetek5.deob.Static308;
import com.jagex.game.runetek5.deob.p;
import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.scene.CollisionMap;
import com.jagex.game.runetek5.config.quickchat.*;
import com.jagex.game.runetek5.deob.Class4_Sub19;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub8_Sub1;
import com.jagex.game.runetek5.tools.Isaac;
import com.jagex.game.runetek5.deob.Class26;
import com.jagex.game.runetek5.sound.player.PcmPlayer;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteTypeList;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.toolkit.opengl.GlParticleRenderer;
import com.jagex.game.runetek5.network.packet.ServerProt;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchNode;
import com.jagex.game.runetek5.deob.Class154;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpDiagonalWaves;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class267;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub1;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.config.rsinterface.HookRequest;
import com.jagex.game.runetek5.deob.QuickChatValueType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
import com.jagex.game.runetek5.config.mec.MECTypeList;
@OriginalClass("client!np")
public final class QuickChatCategoryType extends CacheableNode {

	@OriginalMember(owner = "client!np", name = "z", descriptor = "[I")
	public int[] anIntArray325;

	@OriginalMember(owner = "client!np", name = "D", descriptor = "[C")
	public char[] aCharArray3;

	@OriginalMember(owner = "client!np", name = "I", descriptor = "Ljava/lang/String;")
	public String aString49;

	@OriginalMember(owner = "client!np", name = "L", descriptor = "[C")
	public char[] aCharArray4;

	@OriginalMember(owner = "client!np", name = "O", descriptor = "[I")
	public int[] anIntArray326;

	@OriginalMember(owner = "client!np", name = "a", descriptor = "(CB)I")
	public int method3867(@OriginalArg(0) char arg0) {
		if (this.anIntArray325 == null) {
			return -1;
		}
		for (@Pc(12) int local12 = 0; local12 < this.anIntArray325.length; local12++) {
			if (this.aCharArray4[local12] == arg0) {
				return this.anIntArray325[local12];
			}
		}
		return -1;
	}

	@OriginalMember(owner = "client!np", name = "b", descriptor = "(CB)I")
	public int method3869(@OriginalArg(0) char arg0) {
		if (this.anIntArray326 == null) {
			return -1;
		}
		for (@Pc(18) int local18 = 0; local18 < this.anIntArray326.length; local18++) {
			if (this.aCharArray3[local18] == arg0) {
				return this.anIntArray326[local18];
			}
		}
		return -1;
	}

	@OriginalMember(owner = "client!np", name = "f", descriptor = "(B)V")
	public void method3870() {
		@Pc(15) int local15;
		if (this.anIntArray325 != null) {
			for (local15 = 0; local15 < this.anIntArray325.length; local15++) {
				this.anIntArray325[local15] |= 0x8000;
			}
		}
		if (this.anIntArray326 != null) {
			for (local15 = 0; local15 < this.anIntArray326.length; local15++) {
				this.anIntArray326[local15] |= 0x8000;
			}
		}
	}

	@OriginalMember(owner = "client!np", name = "a", descriptor = "(Lclient!iv;II)V")
	private void method3872(@OriginalArg(0) Packet arg0, @OriginalArg(2) int arg1) {
		if (arg1 == 1) {
			this.aString49 = arg0.method2537();
			return;
		}
		@Pc(13) int local13;
		@Pc(23) int local23;
		@Pc(36) byte local36;
		if (arg1 == 2) {
			local13 = arg0.method2490();
			this.aCharArray3 = new char[local13];
			this.anIntArray326 = new int[local13];
			for (local23 = 0; local23 < local13; local23++) {
				this.anIntArray326[local23] = arg0.method2536();
				local36 = arg0.method2542();
				this.aCharArray3[local23] = local36 == 0 ? 0 : Class4_Sub1_Sub8_Sub1.method3771(local36);
			}
		} else if (arg1 == 3) {
			local13 = arg0.method2490();
			this.aCharArray4 = new char[local13];
			this.anIntArray325 = new int[local13];
			for (local23 = 0; local23 < local13; local23++) {
				this.anIntArray325[local23] = arg0.method2536();
				local36 = arg0.method2542();
				this.aCharArray4[local23] = local36 == 0 ? 0 : Class4_Sub1_Sub8_Sub1.method3771(local36);
			}
		}
	}

	@OriginalMember(owner = "client!np", name = "a", descriptor = "(Lclient!iv;I)V")
	public void method3873(@OriginalArg(0) Packet arg0) {
		while (true) {
			@Pc(5) int local5 = arg0.method2490();
			if (local5 == 0) {
				return;
			}
			this.method3872(arg0, local5);
		}
	}

@OriginalMember(owner = "client!np", name = "M", descriptor = "I")
	public static int anInt5073;
@OriginalMember(owner = "client!np", name = "H", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_106 = new Class83("Loading world map - ", "Lade Weltkarte - ", "Chargement de la mappemonde - ", "Carregando mapa-múndi - ");
@OriginalMember(owner = "client!np", name = "J", descriptor = "[J")
	public static final long[] aLongArray7 = new long[100];
@OriginalMember(owner = "client!np", name = "e", descriptor = "(B)V")
	public static void method3866() {
		Packet.anIntArray188 = null;
		Class154.anIntArray312 = null;
		Static308.aBoolean485 = false;
		MapSpriteTypeList.anIntArray548 = null;
		NativeBillboardFace.anIntArray223 = null;
		GlVboArrayBuffer.anIntArray310 = null;
	}
@OriginalMember(owner = "client!np", name = "a", descriptor = "(IILclient!ya;)Z")
	public static boolean method3868(@OriginalArg(1) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1) {
		@Pc(11) int local11 = (GLBillowTextureData.anInt5666 - 104) / 2;
		@Pc(17) int local17 = (Static283.anInt5187 - 104) / 2;
		@Pc(19) boolean local19 = true;
		@Pc(29) int local29;
		@Pc(40) int local40;
		for (@Pc(21) int local21 = local11; local21 < local11 + 104; local21++) {
			for (@Pc(25) int local25 = local17; local25 < local17 + 104; local25++) {
				for (local29 = arg0; local29 <= 3; local29++) {
					if (Class4_Sub37.method4164(local21, arg0, local29, local25)) {
						local40 = local29;
						if (EnvironmentSampler.method3229(local21, local25)) {
							local40 = local29 - 1;
						}
						if (local40 >= 0) {
							local19 &= TextureOpDiagonalWaves.method3257(local25, local40, local21);
						}
					}
				}
			}
		}
		if (!local19) {
			return false;
		}
		@Pc(91) int[] local91 = new int[262144];
		for (local29 = 0; local29 < local91.length; local29++) {
			local91[local29] = -16777216;
		}
		PcmPlayer.aClass57_11 = arg1.method4265(local91, 512, 512, 512);
		MECTypeList.method4123();
		local40 = ((int) (Math.random() * 20.0D) + 238 - 10 << 16) + ((int) (Math.random() * 20.0D) + 238 - 10 << 8) + (int) (Math.random() * 20.0D) + 238 - 10 | 0xFF000000;
		@Pc(164) int local164 = ((int) (Math.random() * 20.0D) + 238 - 10 | 0xFF00) << 16;
		@Pc(183) int local183 = (int) (Math.random() * 8.0D) << 16 | (int) (Math.random() * 8.0D) << 8 | (int) (Math.random() * 8.0D);
		@Pc(187) boolean[][] local187 = new boolean[QuickChatValueType.anInt3061][QuickChatValueType.anInt3061];
		@Pc(193) int local193;
		@Pc(210) int local210;
		@Pc(214) int local214;
		@Pc(218) int local218;
		@Pc(274) int local274;
		@Pc(280) int local280;
		@Pc(285) int local285;
		@Pc(304) int local304;
		for (@Pc(189) int local189 = local11; local189 < local11 + 104; local189 += QuickChatValueType.anInt3061) {
			for (local193 = local17; local193 < local17 + 104; local193 += QuickChatValueType.anInt3061) {
				arg1.OA(0, 0, QuickChatValueType.anInt3061 * 4, QuickChatValueType.anInt3061 * 4);
				arg1.p(-16777216);
				for (local210 = arg0; local210 <= 3; local210++) {
					for (local214 = 0; local214 < QuickChatValueType.anInt3061; local214++) {
						for (local218 = 0; local218 < QuickChatValueType.anInt3061; local218++) {
							local187[local214][local218] = Class4_Sub37.method4164(local189 + local214, arg0, local210, local193 + local218);
						}
					}
					// 592HD: this bake reuses the world's ground pass from a top-down
					// projection of its own, so the scene's per-pixel lighting must stand
					// aside for it - it would light the map with the camera's fog and
					// sun and flatten the whole terrain to one tone. The stock path
					// draws this pass with its own lights either way.
					arg1.hdMapBakeBegin();
					try {
						Class4_Sub6_Sub4.aClass65Array1[local210].method4025(local189, local193,
							local189 + QuickChatValueType.anInt3061,
							local193 - -QuickChatValueType.anInt3061, local187);
					} finally {
						arg1.hdMapBakeEnd();
					}
					if (!GlFramebuffer.aBoolean424) {
						for (local218 = -4; local218 < QuickChatValueType.anInt3061; local218++) {
							for (local274 = -4; local274 < QuickChatValueType.anInt3061; local274++) {
								local280 = local189 + local218;
								local285 = local193 + local274;
								if (local280 >= local11 && local17 <= local285 && Class4_Sub37.method4164(local280, arg0, local210, local285)) {
									local304 = local210;
									if (EnvironmentSampler.method3229(local280, local285)) {
										local304 = local210 - 1;
									}
									if (local304 >= 0) {
										OpenGLTextureCache.method1548(local304, local40, local285, (QuickChatValueType.anInt3061 - local274) * 4 - 4, arg1, local280, local218 * 4, local164);
									}
								}
							}
						}
					}
				}
				if (GlFramebuffer.aBoolean424) {
					@Pc(357) CollisionMap local357 = PlayerVarDomain.aClass213Array1[arg0];
					for (local218 = 0; local218 < QuickChatValueType.anInt3061; local218++) {
						for (local274 = 0; local274 < QuickChatValueType.anInt3061; local274++) {
							local280 = local189 + local218;
							local285 = local193 + local274;
							local304 = local357.collisionFlags[local280 - local357.anInt6230][local285 - local357.anInt6235];
							if ((local304 & 0x40240000) != 0) {
								arg1.method4293(local218 * 4, 4, (QuickChatValueType.anInt3061 - local274) * 4 - 4, -1713569622, 4);
							} else if ((local304 & 0x800000) != 0) {
								arg1.method4256(4, (QuickChatValueType.anInt3061 - local274) * 4 - 4, local218 * 4, -1713569622);
							} else if ((local304 & 0x2000000) != 0) {
								arg1.method4240((QuickChatValueType.anInt3061 - local274) * 4 - 4, 4, local218 * 4 + 3, -1713569622);
							} else if ((local304 & 0x8000000) != 0) {
								arg1.method4256(4, (QuickChatValueType.anInt3061 - local274) * 4 + 3 - 4, local218 * 4, -1713569622);
							} else if ((local304 & 0x20000000) != 0) {
								arg1.method4240((QuickChatValueType.anInt3061 - local274) * 4 - 4, 4, local218 * 4, -1713569622);
							}
						}
					}
				}
				arg1.O(0, 0, QuickChatValueType.anInt3061 * 4, QuickChatValueType.anInt3061 * 4, local183, 2);
				PcmPlayer.aClass57_11.captureScreenRegion((local189 - local11) * 4 + 48, -(QuickChatValueType.anInt3061 * 4) + -((local193 + -local17) * 4) + 464, QuickChatValueType.anInt3061 * 4, QuickChatValueType.anInt3061 * 4, 0, 0);
			}
		}
		arg1.e();
		arg1.p(-16777215);
		ModeWhere.method4940();
		Class50_Sub1.anInt3460 = 0;
		ServerProt.aClass183_25.method4138();
		if (!GlFramebuffer.aBoolean424) {
			for (local193 = local11; local193 < local11 + 104; local193++) {
				for (local210 = local17; local210 < local17 + 104; local210++) {
					for (local214 = arg0; local214 <= arg0 + 1 && local214 <= 3; local214++) {
						if (Class4_Sub37.method4164(local193, arg0, local214, local210)) {
							@Pc(641) GameObject local641 = (GameObject) Class4_Sub19.method2096(local214, local193, local210);
							if (local641 == null) {
								local641 = (GameObject) Class26.method648(local214, local193, local210, GameObject.class);
							}
							if (local641 == null) {
								local641 = (GameObject) MusicPatchNode.method3449(local214, local193, local210);
							}
							if (local641 == null) {
								local641 = (GameObject) GlParticleRenderer.method918(local214, local193, local210);
							}
							if (local641 != null) {
								@Pc(682) LocType local682 = Static267.aClass262_2.method5560(local641.method5350());
								if (!local682.aBoolean532 || MusicPatch.aBoolean506) {
									local280 = local682.anInt5807;
									if (local682.alternativeIds != null) {
										for (local285 = 0; local285 < local682.alternativeIds.length; local285++) {
											if (local682.alternativeIds[local285] != -1) {
												@Pc(711) LocType local711 = Static267.aClass262_2.method5560(local682.alternativeIds[local285]);
												if (local711.anInt5807 >= 0) {
													local280 = local711.anInt5807;
												}
											}
										}
									}
									if (local280 >= 0) {
										@Pc(731) boolean local731 = false;
										if (local280 >= 0) {
											@Pc(741) MECType local741 = Class4_Sub6_Sub31.aClass182_4.method4124(local280);
											if (local741 != null && local741.aBoolean451) {
												local731 = true;
											}
										}
										local304 = local193;
										@Pc(752) int local752 = local210;
										if (local731) {
											@Pc(759) int[][] local759 = PlayerVarDomain.aClass213Array1[local214].collisionFlags;
											@Pc(764) int local764 = PlayerVarDomain.aClass213Array1[local214].anInt6230;
											@Pc(769) int local769 = PlayerVarDomain.aClass213Array1[local214].anInt6235;
											for (@Pc(771) int local771 = 0; local771 < 10; local771++) {
												@Pc(778) int local778 = (int) (Math.random() * 4.0D);
												if (local778 == 0 && local11 < local304 && local193 - 3 < local304 && (local759[local304 - local764 - 1][local752 - local769] & 0x2C0108) == 0) {
													local304--;
												}
												if (local778 == 1 && local304 < local11 + 104 - 1 && local193 + 3 > local304 && (local759[local304 + 1 - local764][local752 - local769] & 0x2C0180) == 0) {
													local304++;
												}
												if (local778 == 2 && local752 > local17 && local752 > local210 - 3 && (local759[local304 - local764][local752 - local769 - 1] & 0x2C0102) == 0) {
													local752--;
												}
												if (local778 == 3 && local752 < local17 + 103 && local210 + 3 > local752 && (local759[local304 - local764][local752 + 1 - local769] & 0x2C0120) == 0) {
													local752++;
												}
											}
										}
										Class267.anIntArray552[Class50_Sub1.anInt3460] = local682.id;
										Class4_Sub6_Sub7.anIntArray156[Class50_Sub1.anInt3460] = local304;
										Class16_Sub4_Sub1.anIntArray146[Class50_Sub1.anInt3460] = local752;
										Class50_Sub1.anInt3460++;
									}
								}
							}
						}
					}
				}
			}
			if (HookRequest.aClass195_2 != null) {
				Isaac.aClass76_20.loadType = 1;
				Class4_Sub6_Sub31.aClass182_4.method4131(64, 1024);
				for (local210 = 0; local210 < HookRequest.aClass195_2.elementCount; local210++) {
					local214 = HookRequest.aClass195_2.anIntArray397[local210];
					if (Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82 == local214 >> 28) {
						local218 = (local214 >> 14 & 0x3FFF) - Class50_Sub1.anInt3453;
						local274 = (local214 & 0x3FFF) - OpenGLTextureCache.anInt1771;
						if (local218 >= 0 && local218 < GLBillowTextureData.anInt5666 && local274 >= 0 && local274 < Static283.anInt5187) {
							ServerProt.aClass183_25.method4137(new Class4_Sub37(local210));
						} else {
							@Pc(1041) MECType local1041 = Class4_Sub6_Sub31.aClass182_4.method4124(HookRequest.aClass195_2.anIntArray396[local210]);
							if (local1041.offsets != null && local1041.anInt4933 + local218 >= 0 && GLBillowTextureData.anInt5666 > local1041.anInt4936 + local218 && local1041.anInt4925 + local274 >= 0 && Static283.anInt5187 > local1041.anInt4915 + local274) {
								ServerProt.aClass183_25.method4137(new Class4_Sub37(local210));
							}
						}
					}
				}
				Class4_Sub6_Sub31.aClass182_4.method4131(64, 128);
				Isaac.aClass76_20.loadType = 2;
				Isaac.aClass76_20.method2118();
			}
		}
		return true;
	}
@OriginalMember(owner = "client!np", name = "a", descriptor = "(IIIIIIBII)V")
	public static void method3871(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(7) int arg5) {
		EnvironmentSampler.method3226(arg0, arg1, 0, arg5, arg3, arg2, arg4);
	}
}
