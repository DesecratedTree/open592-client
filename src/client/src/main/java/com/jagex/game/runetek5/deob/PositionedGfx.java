package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.cache.node.lru.CacheableNode;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.client.cycle.FrameRateStats;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.entity.pathingentity.player.PlayerAppearance;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.cache.node.GlMemoryPool;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.deob.Class4_Sub45;
import com.jagex.game.runetek5.graphics.map.worldmap.WorldMapElement;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class174;
import com.jagex.game.runetek5.deob.Class95_Sub3;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class249;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub2;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class269;
import com.jagex.game.runetek5.deob.Class16_Sub4;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub19;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpInterpolate;
import com.jagex.game.runetek5.graphics.texture.sampler.GlRectangleTexture;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.deob.Class180;
import com.jagex.game.runetek5.config.param.ParamTypeList;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchInfo;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub17;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.deob.TextureOpBoxBlur;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.graphics.gl.GlShadowMap;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
import com.jagex.game.runetek5.graphics.shader.Texture3DShaderGL;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.sprite.SpriteGL;
import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.graphics.texture.water.NativeWaterTexture;
import com.jagex.game.runetek5.config.sequence.PathingEntitySequence;
import com.jagex.game.runetek5.cache.node.StringNode;
import com.jagex.game.runetek5.cache.node.lru.GlShadow;
import com.jagex.game.runetek5.deob.Class28;
import com.jagex.game.runetek5.ByteArrayWrapper;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Class222;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.deob.Huffman;
import com.jagex.game.runetek5.toolkit.opengl.effect.ReflectiveRipplingWaterEffect;
import com.jagex.game.runetek5.deob.Class4_Sub25;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageType;
import com.jagex.game.runetek5.deob.Class48;
import com.jagex.game.runetek5.config.mec.MECTypeList;
@OriginalClass("client!hi")
public final class PositionedGfx extends CacheableNode {

	@OriginalMember(owner = "client!hi", name = "y", descriptor = "Lclient!dd;")
	public final SpotAnimation aClass16_Sub1_Sub2_1;

	@OriginalMember(owner = "client!hi", name = "<init>", descriptor = "(Lclient!dd;)V")
	public PositionedGfx(@OriginalArg(0) SpotAnimation arg0) {
		this.aClass16_Sub1_Sub2_1 = arg0;
	}

@OriginalMember(owner = "client!hi", name = "C", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_56 = new Class83("flash3:", "blinken3:", "clignotant3:", "brilho3:");
@OriginalMember(owner = "client!hi", name = "D", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_57 = new Class83("Take", "Nehmen", "Prendre", "Pegar");
@OriginalMember(owner = "client!hi", name = "G", descriptor = "Z")
	public static boolean aBoolean263 = true;
@OriginalMember(owner = "client!hi", name = "a", descriptor = "(ILjava/lang/String;)Z")
	public static boolean method2458(@OriginalArg(1) String arg0) {
		if (arg0 == null) {
			return false;
		}
		for (@Pc(11) int local11 = 0; local11 < Class4_Sub10.anInt2346; local11++) {
			if (arg0.equalsIgnoreCase(Mesh.aStringArray14[local11])) {
				return true;
			}
		}
		return arg0.equalsIgnoreCase(Static1.aClass16_Sub1_Sub5_Sub1_1.aString44);
	}
@OriginalMember(owner = "client!hi", name = "a", descriptor = "(IC)C")
	public static char method2459(@OriginalArg(1) char arg0) {
		if (arg0 == ' ' || arg0 == ' ' || arg0 == '_' || arg0 == '-') {
			return '_';
		} else if (arg0 == '[' || arg0 == ']' || arg0 == '#') {
			return arg0;
		} else if (arg0 == 'à' || arg0 == 'á' || arg0 == 'â' || arg0 == 'ä' || arg0 == 'ã' || arg0 == 'À' || arg0 == 'Á' || arg0 == 'Â' || arg0 == 'Ä' || arg0 == 'Ã') {
			return 'a';
		} else if (arg0 == 'è' || arg0 == 'é' || arg0 == 'ê' || arg0 == 'ë' || arg0 == 'È' || arg0 == 'É' || arg0 == 'Ê' || arg0 == 'Ë') {
			return 'e';
		} else if (arg0 == 'í' || arg0 == 'î' || arg0 == 'ï' || arg0 == 'Í' || arg0 == 'Î' || arg0 == 'Ï') {
			return 'i';
		} else if (arg0 == 'ò' || arg0 == 'ó' || arg0 == 'ô' || arg0 == 'ö' || arg0 == 'õ' || arg0 == 'Ò' || arg0 == 'Ó' || arg0 == 'Ô' || arg0 == 'Ö' || arg0 == 'Õ') {
			return 'o';
		} else if (arg0 == 'ù' || arg0 == 'ú' || arg0 == 'û' || arg0 == 'ü' || arg0 == 'Ù' || arg0 == 'Ú' || arg0 == 'Û' || arg0 == 'Ü') {
			return 'u';
		} else if (arg0 == 'ç' || arg0 == 'Ç') {
			return 'c';
		} else if (arg0 == 'ÿ' || arg0 == 'Ÿ') {
			return 'y';
		} else if (arg0 == 'ñ' || arg0 == 'Ñ') {
			return 'n';
		} else if (arg0 == 'ß') {
			return 'b';
		} else {
			return Character.toLowerCase(arg0);
		}
	}
@OriginalMember(owner = "client!hi", name = "a", descriptor = "(IIII[Lclient!uu;IIIII)V")
	public static void method2460(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) com.jagex.game.runetek5.config.rsinterface.Component[] arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8) {
		MonoStereoData.aClass19_16.OA(arg5, arg0, arg1, arg7);
		for (@Pc(17) int local17 = 0; local17 < arg4.length; local17++) {
			@Pc(23) com.jagex.game.runetek5.config.rsinterface.Component local23 = arg4[local17];
			if (local23 != null && (arg6 == local23.anInt6850 || arg6 == -1412584499 && local23 == Static281.aClass247_12)) {
				@Pc(46) int local46;
				if (arg3 == -1) {
					Class4_Sub6_Sub23.aRectangleArray1[SeqType.p12FullIndex].setBounds(arg8 + local23.anInt6836, local23.anInt6859 + arg2, local23.anInt6833, local23.anInt6805);
					local46 = SeqType.p12FullIndex++;
				} else {
					local46 = arg3;
				}
				local23.anInt6798 = Shadow.anInt5323;
				local23.anInt6852 = local46;
				if (!client.isComponentHidden(local23)) {
					if (local23.anInt6842 != 0) {
						Class4_Sub6_Sub7.method1681(local23);
					}
					@Pc(90) int local90 = local23.anInt6836 + arg8;
					@Pc(95) int local95 = arg2 + local23.anInt6859;
					@Pc(98) int local98 = local23.anInt6851;
					if (Class249.aBoolean626 && (client.method900(local23).anInt5105 != 0 || local23.anInt6840 == 0) && local98 > 127) {
						local98 = 127;
					}
					@Pc(148) int local148;
					@Pc(154) int local154;
					if (local23 == Static281.aClass247_12) {
						if (arg6 != -1412584499 && (ParamTypeList.anInt6419 == local23.anInt6777 || local23.anInt6777 == GlRectangleTexture.anInt5106)) {
							Static166.aClass247Array1 = arg4;
							Static258.anInt4823 = arg2;
							TextureOpInterpolate.anInt3054 = arg8;
							continue;
						}
						if (Class4_Sub1_Sub2.aBoolean22 && Class95_Sub3.aBoolean584) {
							local148 = MaterialType.aClass80_1.method2234();
							local154 = MaterialType.aClass80_1.getDefaultValue();
							local148 -= Huffman.anInt3559;
							local154 -= Class4_Sub45.anInt7359;
							if (local148 < Toolkit.anInt5535) {
								local148 = Toolkit.anInt5535;
							}
							if (local23.anInt6833 + local148 > Toolkit.anInt5535 + Static57.aClass247_1.anInt6833) {
								local148 = Static57.aClass247_1.anInt6833 + Toolkit.anInt5535 - local23.anInt6833;
							}
							if (Class222.anInt6383 > local154) {
								local154 = Class222.anInt6383;
							}
							local90 = local148;
							if (Static57.aClass247_1.anInt6805 + Class222.anInt6383 < local154 - -local23.anInt6805) {
								local154 = Static57.aClass247_1.anInt6805 + Class222.anInt6383 - local23.anInt6805;
							}
							local95 = local154;
						}
						if (GlRectangleTexture.anInt5106 == local23.anInt6777) {
							local98 = 128;
						}
					}
					@Pc(234) int local234;
					@Pc(232) int local232;
					@Pc(244) int local244;
					@Pc(249) int local249;
					if (local23.anInt6840 == 2) {
						local154 = arg0;
						local232 = arg7;
						local234 = arg1;
						local148 = arg5;
					} else {
						local244 = local90 + local23.anInt6833;
						local249 = local23.anInt6805 + local95;
						if (local23.anInt6840 == 9) {
							local249++;
							local244++;
						}
						local148 = local90 <= arg5 ? arg5 : local90;
						local154 = local95 <= arg0 ? arg0 : local95;
						local234 = arg1 > local244 ? local244 : arg1;
						local232 = arg7 <= local249 ? arg7 : local249;
					}
					if (local234 > local148 && local154 < local232) {
						@Pc(555) int local555;
						@Pc(581) int local581;
						@Pc(583) int local583;
						@Pc(505) int local505;
						@Pc(507) int local507;
						if (local23.anInt6842 != 0) {
							if (QuickchatMessageType.anInt295 == local23.anInt6842 || Class28.anInt671 == local23.anInt6842) {
								Skybox.method5247(local90, local23.anInt6833, Class28.anInt671 == local23.anInt6842, local95, local23.anInt6805);
								PingRequest.aBooleanArray21[local46] = true;
								MonoStereoData.aClass19_16.OA(arg5, arg0, arg1, arg7);
								continue;
							}
							if (Class16_Sub4.anInt3645 == local23.anInt6842) {
								if (local23.method5312(MonoStereoData.aClass19_16) != null) {
									CubeMapSpecularEffect.method2701();
									Class269.method5661(local23, local90, local95, MonoStereoData.aClass19_16);
									Class4_Sub6_Sub25.aBooleanArray15[local46] = true;
									MonoStereoData.aClass19_16.OA(arg5, arg0, arg1, arg7);
								}
								continue;
							}
							if (local23.anInt6842 == Static283.anInt5185) {
								if (local23.method5312(MonoStereoData.aClass19_16) != null) {
									Static331.method4515(local90, local95, local23);
									Class4_Sub6_Sub25.aBooleanArray15[local46] = true;
									MonoStereoData.aClass19_16.OA(arg5, arg0, arg1, arg7);
								}
								continue;
							}
							if (Class196.anInt5689 == local23.anInt6842) {
								Class4_Sub1_Sub17.method4208(local90, local23.anInt6833, MidiPcmStream.anInterface7_3, MonoStereoData.aClass19_16, local23.anInt6805, local95);
								PingRequest.aBooleanArray21[local46] = true;
								MonoStereoData.aClass19_16.OA(arg5, arg0, arg1, arg7);
								continue;
							}
							if (Class4_Sub25.anInt3548 == local23.anInt6842) {
								Class48.method4399(local23.anInt6833, MonoStereoData.aClass19_16, local90, local95, local23.anInt6805);
								PingRequest.aBooleanArray21[local46] = true;
								MonoStereoData.aClass19_16.OA(arg5, arg0, arg1, arg7);
								continue;
							}
							if (Static281.anInt5183 == local23.anInt6842) {
								if (!MusicPatch.aBoolean507 && !Class174.aBoolean477) {
									continue;
								}
								local244 = local90 + local23.anInt6833;
								local249 = local95 + 15;
								if (MusicPatch.aBoolean507) {										Class6.aClass46_9.method5059(FrameRateStats.getOverlayText(), local249, -256, local244);
									local249 += 15;
									@Pc(495) Runtime local495 = Runtime.getRuntime();
									local505 = (int) ((local495.totalMemory() - local495.freeMemory()) / 1024L);
									local507 = -256;
									if (local505 > 65536) {
										local507 = -65536;
									}
									Class6.aClass46_9.method5059("Mem:" + local505 + "k", local249, local507, local244);
									local249 += 15;
									Class6.aClass46_9.method5059("In:" + WorldMapElement.anInt1020 + "B/s Out:" + Static449.anInt7316 + "B/s", local249, -256, local244);
									local249 += 15;
									local555 = MonoStereoData.aClass19_16.IA() / 1024;
									Class6.aClass46_9.method5059("Offheap:" + local555 + "k", local249, local555 <= 65536 ? -256 : -65536, local244);
									local249 += 15;
									local581 = 0;
									local583 = 0;
									@Pc(585) int local585 = 0;
									for (@Pc(587) int local587 = 0; local587 < 30; local587++) {
										local581 += Class16_Sub4_Sub2.aClass143_Sub1Array1[local587].method3517();
										local583 += Class16_Sub4_Sub2.aClass143_Sub1Array1[local587].method3527();
										local585 += Class16_Sub4_Sub2.aClass143_Sub1Array1[local587].method3521();
									}
									@Pc(625) int local625 = local585 * 100 / local581;
									@Pc(631) int local631 = local583 * 10000 / local581;
									@Pc(651) String local651 = "Cache:" + Class30.method748(true, (long) local631, 2, 0) + "% (" + local625 + "%)";
									Ground.aClass46_12.method5059(local651, local249, -256, local244);
									local249 += 12;
								}
								if (Class259.anInt7099 > 0) {
									Ground.aClass46_12.method5059("Particles: " + PathingEntitySequence.anInt5137 + " / " + Class259.anInt7099, local249, -256, local244);
								}
								local249 += 12;
								if (Class174.aBoolean477) {
									Ground.aClass46_12.method5059("Polys: " + MonoStereoData.aClass19_16.ZA() + " Models: " + MonoStereoData.aClass19_16.w(), local249, -256, local244);
									local249 += 12;
									Ground.aClass46_12.method5059("Ls: " + NativeWaterTexture.anInt4491 + " La: " + Static308.anInt5410 + " NPC: " + SpriteGL.anInt6770 + " Pl: " + Class22.anInt464, local249, -256, local244);
									local249 += 12;
									Static47.method768();
								}
								PingRequest.aBooleanArray21[local46] = true;
								continue;
							}
						}
						if (local23.anInt6840 == 0) {
							if (local23.anInt6842 == TextureOpBoxBlur.anInt5893 && MonoStereoData.aClass19_16.method4301()) {
								MonoStereoData.aClass19_16.method4294(local90, local95, local23.anInt6833, local23.anInt6805);
							}
							method2460(local154, local234, local95 - local23.anInt6849, local46, arg4, local148, local23.anInt6809, local232, local90 - local23.anInt6825);
							if (local23.slotChildren != null) {
								method2460(local154, local234, local95 - local23.anInt6849, local46, local23.slotChildren, local148, local23.anInt6809, local232, local90 - local23.anInt6825);
							}
							@Pc(815) Class4_Sub43 local815 = (Class4_Sub43) MusicPatch.aClass102_29.method2700((long) local23.anInt6809);
							if (local815 != null) {
								ReflectiveRipplingWaterEffect.method123(local148, local46, local154, local234, local815.anInt6979, local232, local90, local95);
							}
							if (TextureOpBoxBlur.anInt5893 == local23.anInt6842 && MonoStereoData.aClass19_16.method4301()) {
								MonoStereoData.aClass19_16.method4278();
								GlShadowMap.aBoolean600 = true;
							}
							MonoStereoData.aClass19_16.OA(arg5, arg0, arg1, arg7);
						}
						if (Texture3DShaderGL.aBooleanArray9[local46] || GlGround.lastSentMouseX > 1) {
							if (local23.anInt6840 == 3) {
								if (local98 == 0) {
									if (local23.aBoolean618) {
										MonoStereoData.aClass19_16.O(local90, local95, local23.anInt6833, local23.anInt6805, local23.anInt6772, 0);
									} else {
										MonoStereoData.aClass19_16.outlineRect(local90, local95, local23.anInt6833, local23.anInt6805, local23.anInt6772, 0);
									}
								} else if (local23.aBoolean618) {
									MonoStereoData.aClass19_16.O(local90, local95, local23.anInt6833, local23.anInt6805, local23.anInt6772 & 0xFFFFFF | 255 - (local98 & 0xFF) << 24, 1);
								} else {
									MonoStereoData.aClass19_16.outlineRect(local90, local95, local23.anInt6833, local23.anInt6805, 255 - (local98 & 0xFF) << 24 | local23.anInt6772 & 0xFFFFFF, 1);
								}
							} else if (local23.anInt6840 == 4) {
								@Pc(960) AbstractFontRenderer local960 = local23.method5310(MonoStereoData.aClass19_16);
								if (local960 != null) {
									local249 = local23.anInt6772;
									@Pc(975) String local975 = local23.aString67;
									if (local23.anInt6779 != -1) {
										@Pc(986) ItemType local986 = Terrain.aClass206_3.method4703(local23.anInt6779);
										local975 = local986.name;
										if (local975 == null) {
											local975 = "null";
										}
										if ((local986.anInt6164 == 1 || local23.anInt6832 != 1) && local23.anInt6832 != -1) {
											local975 = "<col=ff9040>" + local975 + "</col> x" + MusicPatchInfo.method5388(local23.anInt6832);
										}
									}
									if (Class180.aClass247_16 == local23) {
										local975 = ByteArrayWrapper.IF_ON_IF.method2267(NativeBillboardFace.anInt3423);
										local249 = local23.anInt6772;
									}
									if (Static449.aBoolean688) {
										MonoStereoData.aClass19_16.fa(local90, local95, local90 + local23.anInt6833, local23.anInt6805 + local95);
									}
									local960.method5048(local90, local23.anInt6805, 255 - (local98 & 0xFF) << 24 | local249, PingWorker.aClass57Array18, null, local23.aBoolean615 ? 255 - (local98 & 0xFF) << 24 : -1, local23.anInt6853, 0, 0, local23.anInt6803, local975, local95, local23.anInt6817, null, local23.anInt6838, local23.anInt6833);
									if (Static449.aBoolean688) {
										MonoStereoData.aClass19_16.OA(arg5, arg0, arg1, arg7);
									}
								} else if (GlMemoryPool.aBoolean15) {
									Class4_Sub12_Sub1.method1142(local23);
								}
							} else {
								@Pc(1158) int local1158;
								if (local23.anInt6840 == 5) {
									if (local23.anInt6780 < 0) {
										@Pc(1143) NativeSprite local1143;
										if (local23.anInt6779 == -1) {
											local1143 = local23.method5300(MonoStereoData.aClass19_16);
										} else {
											@Pc(1125) PlayerAppearance local1125 = local23.aBoolean613 ? Static1.aClass16_Sub1_Sub5_Sub1_1.playerModel : null;
											local1143 = Terrain.aClass206_3.method4700(MonoStereoData.aClass19_16, local23.anInt6779, local1125, local23.anInt6828, local23.anInt6832, local23.anInt6831, local23.anInt6778 | 0xFF000000);
										}
										if (local1143 != null) {
											local249 = local1143.scaleWidth();
											local1158 = local1143.scaleHeight();
											local505 = (local23.anInt6772 == 0 ? 16777215 : local23.anInt6772 & 0xFFFFFF) | 255 - (local98 & 0xFF) << 24;
											if (local23.aBoolean621) {
												MonoStereoData.aClass19_16.fa(local90, local95, local23.anInt6833 + local90, local95 + local23.anInt6805);
												if (local23.anInt6818 != 0) {
													local507 = (local249 + local23.anInt6833 - 1) / local249;
													local555 = (local1158 + local23.anInt6805 - 1) / local1158;
													for (local581 = 0; local581 < local507; local581++) {
														for (local583 = 0; local583 < local555; local583++) {
															if (local23.anInt6772 == 0) {
																local1143.drawRotatedCenter((float) local249 / 2.0F + (float) (local249 * local581 + local90), (float) local1158 / 2.0F + (float) (local95 - -(local583 * local1158)), 4096, local23.anInt6818);
															} else {
																local1143.drawRotatedCenterColored((float) (local90 + local581 * local249) + (float) local249 / 2.0F, (float) local1158 / 2.0F + (float) (local1158 * local583 + local95), 4096, local23.anInt6818, local505);
															}
														}
													}
												} else if (local23.anInt6772 == 0 && local98 == 0) {
													local1143.drawTiled(local90, local95, local23.anInt6833, local23.anInt6805);
												} else {
													local1143.drawScaledColored(local90, local95, local23.anInt6833, local23.anInt6805, 0, local505, 1);
												}
												MonoStereoData.aClass19_16.OA(arg5, arg0, arg1, arg7);
											} else if (local23.anInt6772 == 0 && local98 == 0) {
												if (local23.anInt6818 != 0) {
													local1143.drawRotatedCenter((float) local23.anInt6833 / 2.0F + (float) local90, (float) local23.anInt6805 / 2.0F + (float) local95, local23.anInt6833 * 4096 / local249, local23.anInt6818);
												} else if (local249 == local23.anInt6833 && local23.anInt6805 == local1158) {
													local1143.render(local90, local95);
												} else {
													local1143.drawTintedScaled(local90, local95, local23.anInt6833, local23.anInt6805);
												}
											} else if (local23.anInt6818 != 0) {
												local1143.drawRotatedCenterColored((float) local90 + (float) local23.anInt6833 / 2.0F, (float) local23.anInt6805 / 2.0F + (float) local95, local23.anInt6833 * 4096 / local249, local23.anInt6818, local505);
											} else if (local23.anInt6833 == local249 && local1158 == local23.anInt6805) {
												local1143.render(local90, local95, 0, local505, 1);
											} else {
												local1143.renderImpl(local90, local95, local23.anInt6833, local23.anInt6805, 0, local505, 1);
											}
										} else if (GlMemoryPool.aBoolean15) {
											Class4_Sub12_Sub1.method1142(local23);
										}
									} else {
										local23.method5311(CacheableNodeByteArray.aClass226_1, StringNode.aClass217_1).method5255(0, local90, local95, local23.sunRotationY << 3, local23.sunRotationX << 3, MonoStereoData.aClass19_16, local23.anInt6833, 0, local23.anInt6805);
									}
								} else if (local23.anInt6840 == 6) {
									MECTypeList.method4123();
									@Pc(1532) Model local1532 = null;
									local249 = 0;
									@Pc(1637) SeqType local1637;
									@Pc(1647) PlayerAppearance local1647;
									if (local23.anInt6779 != -1) {
										@Pc(1745) ItemType local1745 = Terrain.aClass206_3.method4703(local23.anInt6779);
										if (local1745 != null) {
											local1745 = local1745.method4788(local23.anInt6832);
											local1637 = local23.anInt6788 == -1 ? null : SceneLocEntity.aClass55_1.method1397(local23.anInt6788);
											local1647 = local23.aBoolean613 ? Static1.aClass16_Sub1_Sub5_Sub1_1.playerModel : null;
											local1532 = local1745.method4773(local23.anInt6829, 1, local1637, 2048, local23.anInt6783, local1647, local23.anInt6815, MonoStereoData.aClass19_16);
											if (local1532 == null) {
												Class4_Sub12_Sub1.method1142(local23);
											} else {
												local249 = -local1532.setOffsets() >> 1;
											}
										}
									} else if (local23.anInt6796 == 5) {
										local1158 = local23.anInt6781;
										if (local1158 >= 0 && local1158 < 2048) {
											@Pc(1685) PlayerEntity local1685 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local1158];
											@Pc(1698) SeqType local1698 = local23.anInt6788 == -1 ? null : SceneLocEntity.aClass55_1.method1397(local23.anInt6788);
											if (local1685 != null && (local1158 == Class6.anInt5452 || GlShadow.method2302(local1685.aString44) == local23.anInt6848)) {
												local1532 = local1685.playerModel.method833(Class133.aClass249_1, MaterialType.aClass109_2, null, 2048, null, Class196.aClass240_1, local23.anInt6783, local23.anInt6815, local1698, SceneLocEntity.aClass55_1, -1, 0, MonoStereoData.aClass19_16, Terrain.aClass206_3, 0, Class4_Sub6_Sub24.aClass114_1, local23.anInt6829);
											}
										}
									} else if (local23.anInt6796 == 8 || local23.anInt6796 == 9) {
										@Pc(1625) Class4_Sub25 local1625 = PlayerVarDomain.method2834(false, local23.anInt6781);
										local1637 = local23.anInt6788 == -1 ? null : SceneLocEntity.aClass55_1.method1397(local23.anInt6788);
										if (local1625 != null) {
											local1647 = local23.aBoolean613 ? Static1.aClass16_Sub1_Sub5_Sub1_1.playerModel : null;
											local1532 = local1625.method2929(local23.anInt6796 == 9, local23.anInt6783, local1637, local23.anInt6829, local23.anInt6848, local1647, local23.anInt6815, MonoStereoData.aClass19_16);
										}
									} else if (local23.anInt6788 == -1) {
										local1532 = local23.method5307(Terrain.aClass206_3, MonoStereoData.aClass19_16, Class196.aClass240_1, Static1.aClass16_Sub1_Sub5_Sub1_1.playerModel, SceneLocEntity.aClass55_1, 2048, null, -1, MaterialType.aClass109_2, -1, Class4_Sub6_Sub24.aClass114_1, 0, Class133.aClass249_1);
										if (local1532 == null && GlMemoryPool.aBoolean15) {
											Class4_Sub12_Sub1.method1142(local23);
										}
									} else {
										@Pc(1588) SeqType local1588 = SceneLocEntity.aClass55_1.method1397(local23.anInt6788);
										local1532 = local23.method5307(Terrain.aClass206_3, MonoStereoData.aClass19_16, Class196.aClass240_1, Static1.aClass16_Sub1_Sub5_Sub1_1.playerModel, SceneLocEntity.aClass55_1, 2048, local1588, local23.anInt6815, MaterialType.aClass109_2, local23.anInt6783, Class4_Sub6_Sub24.aClass114_1, local23.anInt6829, Class133.aClass249_1);
										if (local1532 == null && GlMemoryPool.aBoolean15) {
											Class4_Sub12_Sub1.method1142(local23);
										}
									}
									if (local1532 != null) {
										if (local23.anInt6822 <= 0) {
											local1158 = 512;
										} else {
											local1158 = (local23.anInt6833 << 9) / local23.anInt6822;
										}
										if (local23.anInt6792 > 0) {
											local505 = (local23.anInt6805 << 9) / local23.anInt6792;
										} else {
											local505 = 512;
										}
										local507 = local23.anInt6833 / 2 + local90 + (local23.anInt6846 * local1158 >> 9);
										local555 = local23.anInt6805 / 2 + local95 + (local505 * local23.anInt6793 >> 9);
										Class3_Sub3.aClass31_1.xa();
										MonoStereoData.aClass19_16.ea(Class3_Sub3.aClass31_1);
										MonoStereoData.aClass19_16.H(local507, local555, local1158, local505);
										MonoStereoData.aClass19_16.g((float) (local23.anInt6782 << 0), local23.aBoolean620 ? (float) (local23.anInt6862 << 0) : (float) (local23.anInt6862 << 0) * 1.5F);
										if (arg6 == -1412584499 || GlShadowMap.aBoolean600) {
											MonoStereoData.aClass19_16.e();
											MonoStereoData.aClass19_16.method4266();
											MonoStereoData.aClass19_16.OA(arg5, arg0, arg1, arg7);
											GlShadowMap.aBoolean600 = false;
										}
										if (local23.aBoolean619) {
											MonoStereoData.aClass19_16.method4309(false);
										}
										local581 = (local23.spriteScale << 0) * Class50_Sub1.anIntArray225[local23.anInt6844 << 3] >> 15;
										local583 = (local23.spriteScale << 0) * Class50_Sub1.anIntArray224[local23.anInt6844 << 3] >> 15;
										GlFramebuffer.aClass31_2.O(-local23.anInt6843 << 3);
										GlFramebuffer.aClass31_2.ja(local23.anInt6858 << 3);
										GlFramebuffer.aClass31_2.o(local23.anInt6814 << 0, local249 + local581 + (local23.anInt6826 << 0), local583 - -(local23.anInt6826 << 0));
										GlFramebuffer.aClass31_2.ka(local23.anInt6844 << 3);
										if (Static449.aBoolean688) {
											MonoStereoData.aClass19_16.fa(local90, local95, local23.anInt6833 + local90, local23.anInt6805 + local95);
										}
										if (local23.aBoolean620) {
											local1532.renderOrtho(GlFramebuffer.aClass31_2, null, local23.spriteScale << 0);
										} else {
											local1532.render(GlFramebuffer.aClass31_2, null, 1);
										}
										if (Static449.aBoolean688) {
											MonoStereoData.aClass19_16.OA(arg5, arg0, arg1, arg7);
										}
										if (local23.aBoolean619) {
											MonoStereoData.aClass19_16.method4309(true);
										}
										MonoStereoData.aClass19_16.g(0.0F, 0.0F);
									}
								} else if (local23.anInt6840 == 9) {
									if (local23.aBoolean610) {
										local1158 = local23.anInt6833 + local90;
										local505 = local95;
										local249 = local23.anInt6805 + local95;
									} else {
										local505 = local23.anInt6805 + local95;
										local1158 = local90 + local23.anInt6833;
										local249 = local95;
									}
									if (local23.anInt6794 == 1) {
										MonoStereoData.aClass19_16.line(local90, local249, local1158, local505, local23.anInt6772, 0);
									} else {
										MonoStereoData.aClass19_16.strongLine(local90, local249, local1158, local505, local23.anInt6772, local23.anInt6794);
									}
								}
							}
						}
					}
				}
			}
		}
	}
@OriginalMember(owner = "client!hi", name = "b", descriptor = "(Z)V")
	public static void method2462() {
		Class16.anInt6886 = 0;
		OpenGLTextureCache.aClass269Array1 = new Class269[50];
	}
}
