package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.deob.Class16_Sub1;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub1;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.deob.Class16_Sub2_Sub1;
import com.jagex.game.runetek5.deob.Class16_Sub2_Sub3;
import com.jagex.game.runetek5.deob.Class16_Sub4;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub1;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub2;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub3;
import com.jagex.game.runetek5.deob.Class16_Sub5;
import com.jagex.game.runetek5.deob.Class16_Sub5_Sub1;
import com.jagex.game.runetek5.deob.Class16_Sub5_Sub2;
import com.jagex.game.runetek5.entity.DynamicWall;
import com.jagex.game.runetek5.deob.Class190;
import com.jagex.game.runetek5.deob.Class191;
import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static308;
import com.jagex.game.runetek5.deob.Static73;
import com.jagex.game.runetek5.deob.Static99;
import com.jagex.game.runetek5.deob.TileLighting;
import com.jagex.game.runetek5.deob.i;
import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.scene.CollisionMap;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.deob.Static133;
import com.jagex.game.runetek5.deob.Static179;
import com.jagex.game.runetek5.deob.Static410;
import com.jagex.game.runetek5.*;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.deob.Class204;
import com.jagex.game.runetek5.deob.Class4_Sub5;
import com.jagex.game.runetek5.deob.SpotAnimation;
import com.jagex.game.runetek5.deob.Class4_Sub19;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.deob.Class186;
import com.jagex.game.runetek5.deob.Class26;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class193;
import com.jagex.game.runetek5.deob.Class111;
import com.jagex.game.runetek5.config.material.texture.MonochromeImageCache;
import com.jagex.game.runetek5.toolkit.opengl.GlParticleRenderer;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchNode;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class27_Sub4;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.deob.TextureOpBlend;
import com.jagex.game.runetek5.entity.ParticleEmitter;
import com.jagex.game.runetek5.cache.node.lru.OpenGlRenderBuffer;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.FixedFunctionSpecularPass;
import com.jagex.game.runetek5.deob.Class4_Sub3;
import com.jagex.game.runetek5.config.cursor.CursorType;
import com.jagex.game.runetek5.entity.pathingentity.EasedAngle;
import com.jagex.game.runetek5.deob.Class244;
import com.jagex.game.runetek5.config.sequence.SeqTypeList;
import com.jagex.game.runetek5.deob.Class25;
import com.jagex.game.runetek5.js5.request.Js5ResourceRequest;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.config.flu.FluTypeList;
import com.jagex.game.runetek5.ByteArrayWrapper;
import com.jagex.game.runetek5.deob.Class28;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.Huffman;
import com.jagex.game.runetek5.deob.QuickChatValueType;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
import com.jagex.game.runetek5.deob.Class225;
@OriginalClass("client!wn")
public final class MapRegion extends Terrain {

	@OriginalMember(owner = "client!wn", name = "P", descriptor = "I")
	public int minLevel = 99;

	@OriginalMember(owner = "client!wn", name = "<init>", descriptor = "(IIIZ)V")
	public MapRegion(@OriginalArg(0) int levels, @OriginalArg(1) int regionSizeX, @OriginalArg(2) int regionSizeZ, @OriginalArg(3) boolean underwater) {
		super(levels, regionSizeX, regionSizeZ, underwater, ByteArrayWrapper.aClass124_2, Class4_Sub6_Sub22.aClass192_2);
	}

	@OriginalMember(owner = "client!wn", name = "a", descriptor = "(I[IILclient!ya;ILclient!iv;)V")
	public void method5644(@OriginalArg(1) int[] arg0, @OriginalArg(2) int arg1, @OriginalArg(3) com.jagex.game.runetek5.graphics.Toolkit arg2, @OriginalArg(4) int arg3, @OriginalArg(5) Packet arg4) {
		if (super.underwater) {
			return;
		}
		@Pc(11) boolean local11 = false;
		if (arg0 != null) {
			arg0[0] = -1;
		}
		@Pc(19) TileLighting local19 = null;
		while (true) {
			@Pc(24) int local24;
			@Pc(80) int local80;
			@Pc(95) int local95;
			@Pc(99) int local99;
			@Pc(492) int local492;
			while (arg4.currentPosition < arg4.buffer.length) {
				local24 = arg4.method2490();
				if (local24 == 0) {
					local19 = new TileLighting(arg4);
				} else {
					@Pc(155) int local155;
					@Pc(380) int local380;
					@Pc(101) int local101;
					if (local24 == 1) {
						local80 = arg4.method2490();
						if (local80 > 0) {
							for (local492 = 0; local492 < local80; local492++) {
								@Pc(501) Class191 local501 = new Class191(arg2, arg4, 0);
								if (local501.anInt5562 == 31) {
									@Pc(514) Class190 local514 = NPCTypeList.aClass204_1.method4677(arg4.method2536());
									local501.method4334(local514.anInt5502, local514.anInt5506, local514.anInt5504, local514.anInt5500);
								}
								if (arg2.getMaxLights() > 0) {
									@Pc(531) PointLight local531 = local501.aClass4_Sub21_2;
									local101 = (arg3 << 7) + local531.method3160();
									local155 = local531.method3164() + (arg1 << 7);
									local380 = local101 >> 7;
									@Pc(556) int local556 = local155 >> 7;
									if (local380 >= 0 && local556 >= 0 && super.anInt7296 > local380 && super.anInt7290 > local556) {
										local531.setPosition(local101, super.tileHeights[local501.anInt5570][local380][local556] - local531.method3161(), local155);
										if (arg2.getMaxLights() > 0) {
											TextureOpBlend.method3111(local501);
										}
									}
								}
							}
						}
					} else if (local24 == 2) {
						if (local19 == null) {
							local19 = new TileLighting();
						}
						local19.method3692(arg4);
					} else if (local24 != 128) {
						if (local24 != 129) {
							throw new IllegalStateException("");
						}
						if (super.cameraHeights == null) {
							super.cameraHeights = new byte[4][][];
						}
						local11 = true;
						for (local80 = 0; local80 < 4; local80++) {
							@Pc(86) byte local86 = arg4.method2542();
							if (local86 == 0 && super.cameraHeights[local80] != null) {
								local95 = arg3;
								local99 = arg3 + 64;
								local101 = arg1;
								if (arg1 < 0) {
									local101 = 0;
								} else if (super.anInt7290 <= arg1) {
									local101 = super.anInt7290;
								}
								if (arg3 < 0) {
									local95 = 0;
								} else if (super.anInt7296 <= arg3) {
									local95 = super.anInt7296;
								}
								if (local99 < 0) {
									local99 = 0;
								} else if (super.anInt7296 <= local99) {
									local99 = super.anInt7296;
								}
								local155 = arg1 + 64;
								if (local155 < 0) {
									local155 = 0;
								} else if (local155 >= super.anInt7290) {
									local155 = super.anInt7290;
								}
								while (local99 > local95) {
									while (local155 > local101) {
										super.cameraHeights[local80][local95][local101] = 0;
										local101++;
									}
									local95++;
								}
							} else if (local86 == 1) {
								if (super.cameraHeights[local80] == null) {
									super.cameraHeights[local80] = new byte[super.anInt7296 + 1][super.anInt7290 + 1];
								}
								for (local95 = 0; local95 < 64; local95 += 4) {
									for (local99 = 0; local99 < 64; local99 += 4) {
										@Pc(369) byte local369 = arg4.method2542();
										for (local155 = local95 + arg3; local155 < arg3 + local95 + 4; local155++) {
											for (local380 = arg1 + local99; local380 < local99 + arg1 + 4; local380++) {
												if (local155 >= 0 && super.anInt7296 > local155 && local380 >= 0 && local380 < super.anInt7290) {
													super.cameraHeights[local80][local155][local380] = local369;
												}
											}
										}
									}
								}
							} else if (local86 == 2) {
								if (super.cameraHeights[local80] == null) {
									super.cameraHeights[local80] = new byte[super.anInt7296 + 1][super.anInt7290 + 1];
								}
								if (local80 > 0) {
									local95 = arg3;
									local99 = arg3 + 64;
									local101 = arg1;
									if (local99 < 0) {
										local99 = 0;
									} else if (super.anInt7296 <= local99) {
										local99 = super.anInt7296;
									}
									if (arg1 < 0) {
										local101 = 0;
									} else if (arg1 >= super.anInt7290) {
										local101 = super.anInt7290;
									}
									local155 = arg1 + 64;
									if (arg3 < 0) {
										local95 = 0;
									} else if (super.anInt7296 <= arg3) {
										local95 = super.anInt7296;
									}
									if (local155 < 0) {
										local155 = 0;
									} else if (local155 >= super.anInt7290) {
										local155 = super.anInt7290;
									}
									while (local95 < local99) {
										while (local155 > local101) {
											super.cameraHeights[local80][local95][local101] = super.cameraHeights[local80 - 1][local95][local101];
											local101++;
										}
										local95++;
									}
								}
							}
						}
					} else if (arg0 == null) {
						arg4.currentPosition += 10;
					} else {
						arg0[0] = arg4.method2536();
						arg0[1] = arg4.method2512();
						arg0[2] = arg4.method2512();
						arg0[3] = arg4.method2512();
						arg0[4] = arg4.method2536();
					}
				}
			}
			if (local19 != null) {
				for (local24 = 0; local24 < 8; local24++) {
					for (local80 = 0; local80 < 8; local80++) {
						local492 = (arg3 >> 3) + local24;
						local95 = local80 + (arg1 >> 3);
						if (local492 >= 0 && local492 < super.anInt7296 >> 3 && local95 >= 0 && local95 < super.anInt7290 >> 3) {
							CursorType.method2057(local19, local492, local95);
						}
					}
				}
			}
			if (!local11 && super.cameraHeights != null) {
				for (local24 = 0; local24 < 4; local24++) {
					if (super.cameraHeights[local24] != null) {
						for (local80 = 0; local80 < 16; local80++) {
							for (local492 = 0; local492 < 16; local492++) {
								local95 = local80 + (arg3 >> 2);
								local99 = local492 + (arg1 >> 2);
								if (local95 >= 0 && local95 < 26 && local99 >= 0 && local99 < 26) {
									super.cameraHeights[local24][local95][local99] = 0;
								}
							}
						}
					}
				}
				return;
			}
			return;
		}
	}

	@OriginalMember(owner = "client!wn", name = "a", descriptor = "(Lclient!ya;ILclient!sd;IIII)V")
	public void method5645(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(2) CollisionMap arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5) {
		@Pc(7) GameObject local7 = null;
		if (arg2 == 0) {
			local7 = (GameObject) MusicPatchNode.method3449(arg3, arg5, arg4);
		}
		if (arg2 == 1) {
			local7 = (GameObject) GlParticleRenderer.method918(arg3, arg5, arg4);
		}
		if (arg2 == 2) {
			local7 = (GameObject) Class26.method648(arg3, arg5, arg4, GameObject.class);
		}
		if (arg2 == 3) {
			local7 = (GameObject) Class4_Sub19.method2096(arg3, arg5, arg4);
		}
		if (local7 == null) {
			return;
		}
		@Pc(65) LocType local65 = Static267.aClass262_2.method5560(local7.method5350());
		@Pc(69) int local69 = local7.method5354();
		@Pc(73) int local73 = local7.method5347();
		if (local65.method4582()) {
			OpenGlRenderBuffer.method2773(arg4, arg5, arg3, local65);
		}
		if (local7.hardShadow()) {
			local7.method5349(arg0);
		}
		if (arg2 == 0) {
			@Pc(98) Class16_Sub5 local98 = MusicPatchNode.method3449(arg3, arg5, arg4);
			if (local98 instanceof Class16_Sub5_Sub1) {
				((Class16_Sub5_Sub1) local98).aClass16_Sub5_1 = null;
			} else {
				ItemType.method4777(arg3, arg5, arg4);
			}
			if (local65.anInt5851 != 0) {
				arg1.method4848(local73, local69, arg4, arg5, local65.aBoolean534, !local65.aBoolean525);
				return;
			}
		} else if (arg2 == 1) {
			@Pc(287) Class16_Sub2 local287 = GlParticleRenderer.method918(arg3, arg5, arg4);
			if (!(local287 instanceof OpenGlArbElementArrayBuffer)) {
				StillWaterEffect.method5382(arg3, arg5, arg4);
				return;
			}
			((OpenGlArbElementArrayBuffer) local287).aClass16_Sub2_1 = null;
		} else {
			if (arg2 == 2) {
				@Pc(153) Class16_Sub1 local153 = Class26.method648(arg3, arg5, arg4, GameObject.class);
				if (local153 instanceof Class16_Sub1_Sub1 && arg5 == local153.aShort94 && arg4 == local153.aShort96) {
					((Class16_Sub1_Sub1) local153).aClass16_Sub1_2 = null;
				} else {
					Mesh.method3046(arg3, arg5, arg4, GameObject.class);
				}
				if (local65.anInt5851 != 0 && super.anInt7296 > local65.anInt5816 + arg5 && super.anInt7290 > arg4 + local65.anInt5816 && super.anInt7296 > arg5 + local65.anInt5801 && super.anInt7290 > arg4 + local65.anInt5801) {
					arg1.method4837(arg4, local73, local65.aBoolean534, local65.anInt5816, arg5, !local65.aBoolean525, local65.anInt5801);
					return;
				}
			} else if (arg2 == 3) {
				@Pc(257) Class16_Sub4 local257 = Class4_Sub19.method2096(arg3, arg5, arg4);
				if (local257 instanceof Class16_Sub4_Sub1) {
					((Class16_Sub4_Sub1) local257).aClass16_Sub4_1 = null;
				} else {
					Class244.method5491(arg3, arg5, arg4);
				}
				if (local65.anInt5851 != 1) {
					return;
				}
				arg1.method4847(arg5, arg4);
			} else {
				return;
			}
			return;
		}
	}

	@OriginalMember(owner = "client!wn", name = "a", descriptor = "([Lclient!sd;I[BIILclient!ya;)V")
	public void method5646(@OriginalArg(0) CollisionMap[] arg0, @OriginalArg(1) int arg1, @OriginalArg(2) byte[] arg2, @OriginalArg(3) int arg3, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg4) {
		@Pc(14) Packet local14 = new Packet(arg2);
		@Pc(16) int local16 = -1;
		while (true) {
			@Pc(20) int local20 = local14.method2499();
			if (local20 == 0) {
				return;
			}
			local16 += local20;
			@Pc(28) int local28 = 0;
			while (true) {
				@Pc(32) int local32 = local14.method2506();
				if (local32 == 0) {
					break;
				}
				local28 += local32 - 1;
				@Pc(44) int local44 = local28 & 0x3F;
				@Pc(50) int local50 = local28 >> 6 & 0x3F;
				@Pc(54) int local54 = local28 >> 12;
				@Pc(58) int local58 = local14.method2490();
				@Pc(62) int local62 = local58 >> 2;
				@Pc(66) int local66 = local58 & 0x3;
				@Pc(70) int local70 = local50 + arg3;
				@Pc(74) int local74 = arg1 + local44;
				if (local70 > 0 && local74 > 0 && super.anInt7296 - 1 > local70 && local74 < super.anInt7290 - 1) {
					@Pc(102) CollisionMap local102 = null;
					if (!super.underwater) {
						@Pc(107) int local107 = local54;
						if ((SeqTypeList.aByteArrayArrayArray2[1][local70][local74] & 0x2) == 2) {
							local107 = local54 - 1;
						}
						if (local107 >= 0) {
							local102 = arg0[local107];
						}
					}
					this.method5649(local70, local54, local66, -1, local16, arg4, local62, local74, local54, local102);
				}
			}
		}
	}

	@OriginalMember(owner = "client!wn", name = "a", descriptor = "(IILclient!iv;[ILclient!ya;IIIIII)V")
	public void method5647(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Packet arg2, @OriginalArg(3) int[] arg3, @OriginalArg(4) com.jagex.game.runetek5.graphics.Toolkit arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(9) int arg8, @OriginalArg(10) int arg9) {
		if (super.underwater) {
			return;
		}
		@Pc(13) TileLighting local13 = null;
		if (arg3 != null) {
			arg3[0] = -1;
		}
		@Pc(25) int local25 = (arg6 & 0x7) * 8;
		@Pc(31) int local31 = (arg5 & 0x7) * 8;
		while (true) {
			while (true) {
				while (true) {
					while (true) {
						@Pc(38) int local38;
						@Pc(73) int local73;
						@Pc(407) int local407;
						@Pc(109) int local109;
						while (arg2.currentPosition < arg2.buffer.length) {
							local38 = arg2.method2490();
							if (local38 == 0) {
								local13 = new TileLighting(arg2);
							} else {
								@Pc(242) int local242;
								@Pc(124) int local124;
								@Pc(128) int local128;
								@Pc(163) int local163;
								if (local38 == 1) {
									local73 = arg2.method2490();
									if (local73 > 0) {
										for (local407 = 0; local407 < local73; local407++) {
											@Pc(416) Class191 local416 = new Class191(arg4, arg2, 0);
											if (local416.anInt5562 == 31) {
												@Pc(429) Class190 local429 = NPCTypeList.aClass204_1.method4677(arg2.method2536());
												local416.method4334(local429.anInt5502, local429.anInt5506, local429.anInt5504, local429.anInt5500);
											}
											if (arg4.getMaxLights() > 0) {
												@Pc(449) PointLight local449 = local416.aClass4_Sub21_2;
												local242 = local449.method3160() >> 7;
												local124 = local449.method3164() >> 7;
												if (local416.anInt5570 == arg8 && local242 >= local25 && local242 < local25 + 8 && local124 >= local31 && local31 + 8 > local124) {
													local128 = (arg9 << 7) + GlVboElementsArrayBuffer.method5188(arg7, local449.method3164() & 0x3FF, local449.method3160() & 0x3FF);
													local163 = (arg1 << 7) + Static99.method1755(local449.method3164() & 0x3FF, local449.method3160() & 0x3FF, arg7);
													local242 = local128 >> 7;
													local124 = local163 >> 7;
													if (local242 >= 0 && local124 >= 0 && super.anInt7296 > local242 && super.anInt7290 > local124) {
														local449.setPosition(local128, super.tileHeights[arg8][local242][local124] - local449.method3161(), local163);
														if (arg4.getMaxLights() > 0) {
															TextureOpBlend.method3111(local416);
														}
													}
												}
											}
										}
									}
								} else if (local38 == 2) {
									if (local13 == null) {
										local13 = new TileLighting();
									}
									local13.method3692(arg2);
								} else if (local38 != 128) {
									if (local38 != 129) {
										throw new IllegalStateException("");
									}
									if (super.cameraHeights == null) {
										super.cameraHeights = new byte[4][][];
									}
									for (local73 = 0; local73 < 4; local73++) {
										@Pc(79) byte local79 = arg2.method2542();
										@Pc(113) int local113;
										if (local79 == 0 && super.cameraHeights[arg0] != null) {
											if (local73 <= arg8) {
												local109 = arg9;
												local113 = arg9 + 7;
												local242 = arg1;
												if (arg1 < 0) {
													local242 = 0;
												} else if (super.anInt7290 <= arg1) {
													local242 = super.anInt7290;
												}
												if (local113 < 0) {
													local113 = 0;
												} else if (local113 >= super.anInt7296) {
													local113 = super.anInt7296;
												}
												local124 = arg1 + 7;
												if (arg9 < 0) {
													local109 = 0;
												} else if (super.anInt7296 <= arg9) {
													local109 = super.anInt7296;
												}
												if (local124 < 0) {
													local124 = 0;
												} else if (super.anInt7290 <= local124) {
													local124 = super.anInt7290;
												}
												while (local109 < local113) {
													while (local124 > local242) {
														super.cameraHeights[arg0][local109][local242] = 0;
														local242++;
													}
													local109++;
												}
											}
										} else if (local79 == 1) {
											if (super.cameraHeights[arg0] == null) {
												super.cameraHeights[arg0] = new byte[super.anInt7296 + 1][super.anInt7290 + 1];
											}
											for (local109 = 0; local109 < 64; local109 += 4) {
												for (local113 = 0; local113 < 64; local113 += 4) {
													@Pc(119) byte local119 = arg2.method2542();
													if (local73 <= arg8) {
														for (local124 = local109; local124 < local109 + 4; local124++) {
															for (local128 = local113; local128 < local113 + 4; local128++) {
																if (local124 >= local25 && local25 + 8 > local124 && local128 >= local31 && local31 + 8 > local31) {
																	local163 = arg9 + Skybox.method5252(local128 & 0x7, arg7, local124 & 0x7);
																	@Pc(175) int local175 = BasType.method3636(arg7, local124 & 0x7, local128 & 0x7) + arg1;
																	if (local163 >= 0 && super.anInt7296 > local163 && local175 >= 0 && local175 < super.anInt7290) {
																		super.cameraHeights[arg0][local163][local175] = local119;
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								} else if (arg3 == null) {
									arg2.currentPosition += 10;
								} else {
									arg3[0] = arg2.method2536();
									arg3[1] = arg2.method2512();
									arg3[2] = arg2.method2512();
									arg3[3] = arg2.method2512();
									arg3[4] = arg2.method2536();
								}
							}
						}
						if (local13 != null) {
							CursorType.method2057(local13, arg9 >> 3, arg1 >> 3);
						}
						if (super.cameraHeights != null && super.cameraHeights[arg0] != null) {
							local38 = arg9 + 7;
							local73 = arg1 + 7;
							for (local407 = arg9; local407 < local38; local407++) {
								for (local109 = arg1; local109 < local73; local109++) {
									super.cameraHeights[arg0][local407][local109] = 0;
								}
							}
							return;
						}
						return;
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!wn", name = "a", descriptor = "([BIIIIILclient!ya;I[Lclient!sd;IB)V")
	public void method5648(@OriginalArg(0) byte[] arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) com.jagex.game.runetek5.graphics.Toolkit arg6, @OriginalArg(7) int arg7, @OriginalArg(8) CollisionMap[] arg8, @OriginalArg(9) int arg9) {
		@Pc(10) Packet local10 = new Packet(arg0);
		@Pc(16) int local16 = -1;
		while (true) {
			@Pc(20) int local20 = local10.method2499();
			if (local20 == 0) {
				return;
			}
			local16 += local20;
			@Pc(31) int local31 = 0;
			while (true) {
				@Pc(35) int local35 = local10.method2506();
				if (local35 == 0) {
					break;
				}
				local31 += local35 - 1;
				@Pc(47) int local47 = local31 & 0x3F;
				@Pc(53) int local53 = local31 >> 6 & 0x3F;
				@Pc(57) int local57 = local31 >> 12;
				@Pc(61) int local61 = local10.method2490();
				@Pc(65) int local65 = local61 >> 2;
				@Pc(69) int local69 = local61 & 0x3;
				if (local57 == arg9 && arg2 <= local53 && arg2 + 8 > local53 && local47 >= arg7 && local47 < arg7 + 8) {
					@Pc(97) LocType local97 = Static267.aClass262_2.method5560(local16);
					@Pc(114) int local114 = Huffman.method2938(local53 & 0x7, arg3, local97.anInt5801, local47 & 0x7, local69, local97.anInt5816) + arg1;
					@Pc(131) int local131 = arg4 + Class4_Sub3.method182(local97.anInt5816, local69, arg3, local97.anInt5801, local47 & 0x7, local53 & 0x7);
					if (local114 > 0 && local131 > 0 && super.anInt7296 - 1 > local114 && local131 < super.anInt7290 - 1) {
						@Pc(155) CollisionMap local155 = null;
						if (!super.underwater) {
							@Pc(160) int local160 = arg5;
							if ((SeqTypeList.aByteArrayArrayArray2[1][local114][local131] & 0x2) == 2) {
								local160 = arg5 - 1;
							}
							if (local160 >= 0) {
								local155 = arg8[local160];
							}
						}
						this.method5649(local114, arg5, arg3 + local69 & 0x3, -1, local16, arg6, local65, local131, arg5, local155);
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!wn", name = "a", descriptor = "(IIIIILclient!ya;IIIBLclient!sd;)V")
	public void method5649(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8, @OriginalArg(10) CollisionMap arg9) {
		if (!TextureOpBrightness.aClass50_Sub1_1.method2861(Packet.anInt2973) && !Class4_Sub37.method4164(arg0, FixedFunctionSpecularPass.anInt5523, arg8, arg7)) {
			return;
		}
		if (this.minLevel > arg1) {
			this.minLevel = arg1;
		}
		@Pc(30) LocType local30 = Static267.aClass262_2.method5560(arg4);
		if (arg5.method4251() && TextureOpBrightness.aClass50_Sub1_1.aBoolean307 && local30.aBoolean531) {
			return;
		}
		@Pc(49) int local49;
		@Pc(52) int local52;
		if (arg2 == 1 || arg2 == 3) {
			local49 = local30.anInt5801;
			local52 = local30.anInt5816;
		} else {
			local49 = local30.anInt5816;
			local52 = local30.anInt5801;
		}
		@Pc(72) int local72;
		@Pc(80) int local80;
		if (super.anInt7296 >= arg0 + local49) {
			local72 = arg0 + (local49 >> 1);
			local80 = (local49 + 1 >> 1) + arg0;
		} else {
			local72 = arg0;
			local80 = arg0 + 1;
		}
		@Pc(121) int local121;
		@Pc(119) int local119;
		if (arg7 + local52 > super.anInt7290) {
			local119 = arg7 + 1;
			local121 = arg7;
		} else {
			local119 = (local52 + 1 >> 1) + arg7;
			local121 = (local52 >> 1) + arg7;
		}
		@Pc(141) Class65 local141 = Class16_Sub1_Sub6.aClass65Array4[arg8];
		@Pc(164) int local164 = local141.l(local72, local121) + local141.l(local80, local121) + local141.l(local72, local119) + local141.l(local80, local119) >> 2;
		@Pc(173) int local173 = (arg0 << 7) + (local49 << 6);
		@Pc(182) int local182 = (arg7 << 7) + (local52 << 6);
		@Pc(194) boolean local194 = Static308.aBoolean485 && !super.underwater && local30.aBoolean528;
		if (local30.method4582()) {
			MonochromeImageCache.method2264(arg0, null, null, arg7, arg2, arg1, local30);
		}
		@Pc(228) boolean local228 = arg3 == -1 && local30.anInt5840 == -1 && local30.anIntArray405 == null && local30.alternativeIds == null && !local30.aBoolean537;
		if (MaterialType.aBoolean647 && (LRUCache.method1778(arg6) && local30.anInt5828 != 1 || !(!ParticleEmitter.method3631(arg6) || local30.anInt5828 != 0))) {
			return;
		}
		if (arg6 != 22) {
			@Pc(385) Class16_Sub1 local385;
			@Pc(353) Class16_Sub1_Sub6 local353;
			@Pc(391) int local391;
			if (arg6 == 10 || arg6 == 11) {
				local353 = null;
				if (local228) {
					@Pc(383) Class16_Sub1_Sub6 local383 = new Class16_Sub1_Sub6(arg5, local30, arg1, arg8, local173, local164, local182, super.underwater, arg0, local49 + arg0 - 1, arg7, arg7 + local52 - 1, arg6, arg2, local194);
					local385 = local383;
					local353 = local383;
					local391 = local383.method5358();
				} else {
					local385 = new SceneLocEntity(arg5, local30, arg1, arg8, local173, local164, local182, super.underwater, arg0, arg0 + local49 - 1, arg7, arg7 + local52 - 1, arg6, arg2, arg3);
					local391 = 15;
				}
				@Pc(435) Class16_Sub1 local435 = Class26.method648(arg1, arg0, arg7, GameObject.class);
				@Pc(437) boolean local437 = false;
				if (local435 instanceof Class16_Sub1_Sub1 && arg0 == local435.aShort94 && arg7 == local435.aShort96) {
					local437 = true;
					((Class16_Sub1_Sub1) local435).aClass16_Sub1_2 = local385;
				}
				if (local437 || Class193.method4385(local385, false)) {
					if (local353 != null && local353.hardShadow()) {
						local353.removeShadow(arg5);
					}
					if (local30.aBoolean538 && Static308.aBoolean485) {
						if (local391 > 30) {
							local391 = 30;
						}
						for (@Pc(492) int local492 = 0; local492 <= local49; local492++) {
							for (@Pc(496) int local496 = 0; local496 <= local52; local496++) {
								local141.i(arg0 + local492, arg7 - -local496, local391);
							}
						}
					}
				}
				if (local30.anInt5851 != 0 && arg9 != null) {
					arg9.method4841(local49, local30.aBoolean534, local52, !local30.aBoolean525, arg0, arg7);
				}
			} else {
				@Pc(649) Class16_Sub1 local649;
				if (arg6 >= 12 && arg6 <= 17 || arg6 >= 18 && arg6 <= 21) {
					if (local228) {
						local353 = new Class16_Sub1_Sub6(arg5, local30, arg1, arg8, local173, local164, local182, super.underwater, arg0, arg0 + local49 - 1, arg7, arg7 + local52 - 1, arg6, arg2, local194);
						local385 = local353;
						if (local353.hardShadow()) {
							local353.removeShadow(arg5);
						}
					} else {
						local385 = new SceneLocEntity(arg5, local30, arg1, arg8, local173, local164, local182, super.underwater, arg0, local49 + arg0 - 1, arg7, arg7 + local52 - 1, arg6, arg2, arg3);
					}
					local649 = Class26.method648(arg1, arg0, arg7, GameObject.class);
					if (local649 instanceof Class16_Sub1_Sub1 && local649.aShort94 == arg0 && arg7 == local649.aShort96) {
						((Class16_Sub1_Sub1) local649).aClass16_Sub1_2 = local385;
					} else {
						Class193.method4385(local385, false);
					}
					if (Static308.aBoolean485 && !super.underwater && arg6 >= 12 && arg6 <= 17 && arg6 != 13 && arg1 > 0 && local30.anInt5828 != 0) {
						super.occluderFlags[arg1][arg0][arg7] = (byte) (super.occluderFlags[arg1][arg0][arg7] | 0x4);
					}
					if (local30.anInt5851 != 0 && arg9 != null) {
						arg9.method4841(local49, local30.aBoolean534, local52, !local30.aBoolean525, arg0, arg7);
					}
				} else {
					@Pc(779) Class16_Sub5 local779;
					if (arg6 == 0) {
						@Pc(752) int local752 = local30.anInt5828;
						if (Class111.aBoolean282 && local30.anInt5828 == -1) {
							local752 = 1;
						}
						if (local228) {
							@Pc(796) Class16_Sub5_Sub2 local796 = new Class16_Sub5_Sub2(arg5, local30, arg8, local173, local164, local182, super.underwater, arg6, arg2, local194);
							local779 = local796;
							if (local796.hardShadow()) {
								local796.removeShadow(arg5);
							}
						} else {
							local779 = new DynamicWall(arg5, local30, arg1, arg8, local173, local164, local182, super.underwater, arg6, arg2, arg3);
						}
						@Pc(811) Class16_Sub5 local811 = MusicPatchNode.method3449(arg1, arg0, arg7);
						if (local811 instanceof Class16_Sub5_Sub1) {
							((Class16_Sub5_Sub1) local811).aClass16_Sub5_1 = local779;
						} else {
							Class4_Sub3.method181(arg1, arg0, arg7, local779, null);
						}
						if (Static308.aBoolean485) {
							if (arg2 == 0) {
								if (local30.aBoolean538) {
									local141.i(arg0, arg7, 50);
									local141.i(arg0, arg7 + 1, 50);
								}
								if (local752 == 1 && !super.underwater) {
									super.occluderFlags[arg1][arg0][arg7] = (byte) (super.occluderFlags[arg1][arg0][arg7] | 0x1);
								}
							} else if (arg2 == 1) {
								if (local30.aBoolean538) {
									local141.i(arg0, arg7 + 1, 50);
									local141.i(arg0 + 1, arg7 + 1, 50);
								}
								if (local752 == 1 && !super.underwater) {
									super.occluderFlags[arg1][arg0][arg7 + 1] = (byte) (super.occluderFlags[arg1][arg0][arg7 + 1] | 0x2);
								}
							} else if (arg2 == 2) {
								if (local30.aBoolean538) {
									local141.i(arg0 + 1, arg7, 50);
									local141.i(arg0 + 1, arg7 + 1, 50);
								}
								if (local752 == 1 && !super.underwater) {
									super.occluderFlags[arg1][arg0 + 1][arg7] = (byte) (super.occluderFlags[arg1][arg0 + 1][arg7] | 0x1);
								}
							} else if (arg2 == 3) {
								if (local30.aBoolean538) {
									local141.i(arg0, arg7, 50);
									local141.i(arg0 + 1, arg7, 50);
								}
								if (local752 == 1 && !super.underwater) {
									super.occluderFlags[arg1][arg0][arg7] = (byte) (super.occluderFlags[arg1][arg0][arg7] | 0x2);
								}
							}
						}
						if (local30.anInt5851 != 0 && arg9 != null) {
							arg9.method4845(arg7, local30.aBoolean534, arg0, arg2, !local30.aBoolean525, arg6);
						}
						if (local30.anInt5815 != 16) {
							FluTypeList.method4341(arg1, arg0, arg7, local30.anInt5815);
						}
					} else {
						@Pc(1086) Class16_Sub5_Sub2 local1086;
						@Pc(1096) Class16_Sub5 local1096;
						if (arg6 == 1) {
							if (local228) {
								local1086 = new Class16_Sub5_Sub2(arg5, local30, arg8, local173, local164, local182, super.underwater, arg6, arg2, local194);
								if (local1086.hardShadow()) {
									local1086.removeShadow(arg5);
								}
								local1096 = local1086;
							} else {
								local1096 = new DynamicWall(arg5, local30, arg1, arg8, local173, local164, local182, super.underwater, arg6, arg2, arg3);
							}
							local779 = MusicPatchNode.method3449(arg1, arg0, arg7);
							if (local779 instanceof Class16_Sub5_Sub1) {
								((Class16_Sub5_Sub1) local779).aClass16_Sub5_1 = local1096;
							} else {
								Class4_Sub3.method181(arg1, arg0, arg7, local1096, null);
							}
							if (local30.aBoolean538 && Static308.aBoolean485) {
								if (arg2 == 0) {
									local141.i(arg0, arg7 + 1, 50);
								} else if (arg2 == 1) {
									local141.i(arg0 + 1, arg7 + 1, 50);
								} else if (arg2 == 2) {
									local141.i(arg0 + 1, arg7, 50);
								} else if (arg2 == 3) {
									local141.i(arg0, arg7, 50);
								}
							}
							if (local30.anInt5851 != 0 && arg9 != null) {
								arg9.method4845(arg7, local30.aBoolean534, arg0, arg2, !local30.aBoolean525, arg6);
							}
						} else if (arg6 == 2) {
							local391 = arg2 + 1 & 0x3;
							if (local228) {
								@Pc(1242) Class16_Sub5_Sub2 local1242 = new Class16_Sub5_Sub2(arg5, local30, arg8, local173, local164, local182, super.underwater, arg6, arg2 + 4, local194);
								@Pc(1257) Class16_Sub5_Sub2 local1257 = new Class16_Sub5_Sub2(arg5, local30, arg8, local173, local164, local182, super.underwater, arg6, local391, local194);
								if (local1242.hardShadow()) {
									local1242.removeShadow(arg5);
								}
								local779 = local1257;
								local1096 = local1242;
								if (local1257.hardShadow()) {
									local1257.removeShadow(arg5);
								}
							} else {
								local1096 = new DynamicWall(arg5, local30, arg1, arg8, local173, local164, local182, super.underwater, arg6, arg2 + 4, arg3);
								local779 = new DynamicWall(arg5, local30, arg1, arg8, local173, local164, local182, super.underwater, arg6, local391, arg3);
							}
							Class4_Sub3.method181(arg1, arg0, arg7, local1096, local779);
							if (local30.anInt5828 == 1 && Static308.aBoolean485 && !super.underwater) {
								if (arg2 == 0) {
									super.occluderFlags[arg1][arg0][arg7] = (byte) (super.occluderFlags[arg1][arg0][arg7] | 0x1);
									super.occluderFlags[arg1][arg0][arg7 + 1] = (byte) (super.occluderFlags[arg1][arg0][arg7 + 1] | 0x2);
								} else if (arg2 == 1) {
									super.occluderFlags[arg1][arg0][arg7 + 1] = (byte) (super.occluderFlags[arg1][arg0][arg7 + 1] | 0x2);
									super.occluderFlags[arg1][arg0 + 1][arg7] = (byte) (super.occluderFlags[arg1][arg0 + 1][arg7] | 0x1);
								} else if (arg2 == 2) {
									super.occluderFlags[arg1][arg0 + 1][arg7] = (byte) (super.occluderFlags[arg1][arg0 + 1][arg7] | 0x1);
									super.occluderFlags[arg1][arg0][arg7] = (byte) (super.occluderFlags[arg1][arg0][arg7] | 0x2);
								} else if (arg2 == 3) {
									super.occluderFlags[arg1][arg0][arg7] = (byte) (super.occluderFlags[arg1][arg0][arg7] | 0x2);
									super.occluderFlags[arg1][arg0][arg7] = (byte) (super.occluderFlags[arg1][arg0][arg7] | 0x1);
								}
							}
							if (local30.anInt5851 != 0 && arg9 != null) {
								arg9.method4845(arg7, local30.aBoolean534, arg0, arg2, !local30.aBoolean525, arg6);
							}
							if (local30.anInt5815 != 16) {
								FluTypeList.method4341(arg1, arg0, arg7, local30.anInt5815);
							}
						} else if (arg6 == 3) {
							if (local228) {
								local1086 = new Class16_Sub5_Sub2(arg5, local30, arg8, local173, local164, local182, super.underwater, arg6, arg2, local194);
								if (local1086.hardShadow()) {
									local1086.removeShadow(arg5);
								}
								local1096 = local1086;
							} else {
								local1096 = new DynamicWall(arg5, local30, arg1, arg8, local173, local164, local182, super.underwater, arg6, arg2, arg3);
							}
							local779 = MusicPatchNode.method3449(arg1, arg0, arg7);
							if (local779 instanceof Class16_Sub5_Sub1) {
								((Class16_Sub5_Sub1) local779).aClass16_Sub5_1 = local1096;
							} else {
								Class4_Sub3.method181(arg1, arg0, arg7, local1096, null);
							}
							if (local30.aBoolean538 && Static308.aBoolean485) {
								if (arg2 == 0) {
									local141.i(arg0, arg7 + 1, 50);
								} else if (arg2 == 1) {
									local141.i(arg0 + 1, arg7 + 1, 50);
								} else if (arg2 == 2) {
									local141.i(arg0 + 1, arg7, 50);
								} else if (arg2 == 3) {
									local141.i(arg0, arg7, 50);
								}
							}
							if (local30.anInt5851 != 0 && arg9 != null) {
								arg9.method4845(arg7, local30.aBoolean534, arg0, arg2, !local30.aBoolean525, arg6);
							}
						} else if (arg6 == 9) {
							if (local228) {
								local353 = new Class16_Sub1_Sub6(arg5, local30, arg1, arg8, local173, local164, local182, super.underwater, arg0, arg0, arg7, arg7, arg6, arg2, local194);
								local385 = local353;
								if (local353.hardShadow()) {
									local353.removeShadow(arg5);
								}
							} else {
								local385 = new SceneLocEntity(arg5, local30, arg1, arg8, local173, local164, local182, super.underwater, arg0, arg0 + local49 - 1, arg7, arg7 + local52 - 1, arg6, arg2, arg3);
							}
							local649 = Class26.method648(arg1, arg0, arg7, GameObject.class);
							if (local649 instanceof Class16_Sub1_Sub1 && local649.aShort94 == arg0 && local649.aShort96 == arg7) {
								((Class16_Sub1_Sub1) local649).aClass16_Sub1_2 = local385;
							} else {
								Class193.method4385(local385, false);
							}
							if (local30.anInt5851 != 0 && arg9 != null) {
								arg9.method4841(local49, local30.aBoolean534, local52, !local30.aBoolean525, arg0, arg7);
							}
							if (local30.anInt5815 != 16) {
								FluTypeList.method4341(arg1, arg0, arg7, local30.anInt5815);
							}
						} else {
							@Pc(1875) Class16_Sub2 local1875;
							if (arg6 == 4) {
								if (local228) {
									@Pc(1865) Class16_Sub2_Sub1 local1865 = new Class16_Sub2_Sub1(arg5, local30, arg8, local173, local164, local182, super.underwater, 0, 0, 0, arg6, arg2);
									if (local1865.hardShadow()) {
										local1865.removeShadow(arg5);
									}
									local1875 = local1865;
								} else {
									local1875 = new Class16_Sub2_Sub3(arg5, local30, arg1, arg8, local173, local164, local182, super.underwater, 0, 0, 0, arg6, arg2, arg3);
								}
								@Pc(1901) Class16_Sub2 local1901 = GlParticleRenderer.method918(arg1, arg0, arg7);
								if (local1901 instanceof OpenGlArbElementArrayBuffer) {
									((OpenGlArbElementArrayBuffer) local1901).aClass16_Sub2_1 = local1875;
								} else {
									Static73.method1356(arg1, arg0, arg7, local1875, null);
								}
							} else {
								@Pc(1921) int local1921;
								@Pc(1927) GameObject local1927;
								@Pc(1966) Class16_Sub2_Sub1 local1966;
								@Pc(2010) Class16_Sub2 local2010;
								if (arg6 == 5) {
									local1921 = 17;
									local1927 = (GameObject) MusicPatchNode.method3449(arg1, arg0, arg7);
									if (local1927 != null) {
										local1921 = Static267.aClass262_2.method5560(local1927.method5350()).anInt5815 + 1;
									}
									if (local228) {
										local1966 = new Class16_Sub2_Sub1(arg5, local30, arg8, local173, local164, local182, super.underwater, 0, SpotAnimation.anIntArray105[arg2] * local1921, local1921 * Class204.anIntArray417[arg2], arg6, arg2);
										if (local1966.hardShadow()) {
											local1966.removeShadow(arg5);
										}
										local1875 = local1966;
									} else {
										local1875 = new Class16_Sub2_Sub3(arg5, local30, arg1, arg8, local173, local164, local182, super.underwater, 0, SpotAnimation.anIntArray105[arg2] * local1921, local1921 * Class204.anIntArray417[arg2], arg6, arg2, arg3);
									}
									local2010 = GlParticleRenderer.method918(arg1, arg0, arg7);
									if (local2010 instanceof OpenGlArbElementArrayBuffer) {
										((OpenGlArbElementArrayBuffer) local2010).aClass16_Sub2_1 = local1875;
									} else {
										Static73.method1356(arg1, arg0, arg7, local1875, null);
									}
								} else if (arg6 == 6) {
									local1921 = 9;
									local1927 = (GameObject) MusicPatchNode.method3449(arg1, arg0, arg7);
									if (local1927 != null) {
										local1921 = Static267.aClass262_2.method5560(local1927.method5350()).anInt5815 / 2 + 1;
									}
									if (local228) {
										local1966 = new Class16_Sub2_Sub1(arg5, local30, arg8, local173, local164, local182, super.underwater, arg2, SpotAnimation.anIntArray105[arg2] * local1921, Class204.anIntArray417[arg2] * local1921, arg6, arg2 + 4);
										local1875 = local1966;
										if (local1966.hardShadow()) {
											local1966.removeShadow(arg5);
										}
									} else {
										local1875 = new Class16_Sub2_Sub3(arg5, local30, arg1, arg8, local173, local164, local182, super.underwater, arg2, local1921 * MidiPcmStream.anIntArray127[arg2], local1921 * Class16.anIntArray527[arg2], arg6, arg2 + 4, arg3);
									}
									local2010 = GlParticleRenderer.method918(arg1, arg0, arg7);
									if (local2010 instanceof OpenGlArbElementArrayBuffer) {
										((OpenGlArbElementArrayBuffer) local2010).aClass16_Sub2_1 = local1875;
									} else {
										Static73.method1356(arg1, arg0, arg7, local1875, null);
									}
								} else if (arg6 == 7) {
									local1921 = arg2 + 2 & 0x3;
									if (local228) {
										@Pc(2195) Class16_Sub2_Sub1 local2195 = new Class16_Sub2_Sub1(arg5, local30, arg8, local173, local164, local182, super.underwater, local1921, 0, 0, arg6, local1921 + 4);
										local1875 = local2195;
										if (local2195.hardShadow()) {
											local2195.removeShadow(arg5);
										}
									} else {
										local1875 = new Class16_Sub2_Sub3(arg5, local30, arg1, arg8, local173, local164, local182, super.underwater, local1921, 0, 0, arg6, local1921 + 4, arg3);
									}
									@Pc(2210) Class16_Sub2 local2210 = GlParticleRenderer.method918(arg1, arg0, arg7);
									if (local2210 instanceof OpenGlArbElementArrayBuffer) {
										((OpenGlArbElementArrayBuffer) local2210).aClass16_Sub2_1 = local1875;
									} else {
										Static73.method1356(arg1, arg0, arg7, local1875, null);
									}
								} else if (arg6 == 8) {
									local391 = arg2 + 2 & 0x3;
									@Pc(2236) int local2236 = 9;
									@Pc(2242) GameObject local2242 = (GameObject) MusicPatchNode.method3449(arg1, arg0, arg7);
									if (local2242 != null) {
										local2236 = Static267.aClass262_2.method5560(local2242.method5350()).anInt5815 / 2 + 1;
									}
									@Pc(2287) Class16_Sub2 local2287;
									@Pc(2308) Class16_Sub2 local2308;
									if (local228) {
										local2287 = new Class16_Sub2_Sub1(arg5, local30, arg8, local173, local164, local182, super.underwater, arg2, local2236 * MidiPcmStream.anIntArray127[arg2], Class16.anIntArray527[arg2] * local2236, arg6, arg2 + 4);
										local2308 = new Class16_Sub2_Sub1(arg5, local30, arg8, local173, local164, local182, super.underwater, arg2, 0, 0, arg6, local391 + 4);
										if (local2287.hardShadow()) {
											local2287.removeShadow(arg5);
										}
										if (local2308.hardShadow()) {
											local2308.removeShadow(arg5);
										}
									} else {
										local2287 = new Class16_Sub2_Sub3(arg5, local30, arg1, arg8, local173, local164, local182, super.underwater, arg2, MidiPcmStream.anIntArray127[arg2] * local2236, Class16.anIntArray527[arg2] * local2236, arg6, arg2 + 4, arg3);
										local2308 = new Class16_Sub2_Sub3(arg5, local30, arg1, arg8, local173, local164, local182, super.underwater, arg2, 0, 0, arg6, local391 + 4, arg3);
									}
									Static73.method1356(arg1, arg0, arg7, local2287, local2308);
								}
							}
						}
					}
				}
			}
		} else if (TextureOpBrightness.aClass50_Sub1_1.aBoolean305 || local30.anInt5813 != 0 || local30.anInt5851 == 1 || local30.aBoolean539) {
			@Pc(291) Class16_Sub4 local291;
			if (local228) {
				@Pc(281) Class16_Sub4_Sub3 local281 = new Class16_Sub4_Sub3(arg5, local30, arg8, local173, local164, local182, super.underwater, arg2, local194);
				if (local281.hardShadow()) {
					local281.removeShadow(arg5);
				}
				local291 = local281;
			} else {
				local291 = new Class16_Sub4_Sub2(arg5, local30, arg1, arg8, local173, local164, local182, super.underwater, arg2, arg3);
			}
			@Pc(313) Class16_Sub4 local313 = Class4_Sub19.method2096(arg1, arg0, arg7);
			if (local313 instanceof Class16_Sub4_Sub1) {
				((Class16_Sub4_Sub1) local313).aClass16_Sub4_1 = local291;
			} else {
				Class225.method5008(arg1, arg0, arg7, local291);
			}
			if (local30.anInt5851 == 1 && arg9 != null) {
				arg9.method4835(arg7, arg0);
			}
		}
	}

	@OriginalMember(owner = "client!wn", name = "a", descriptor = "(ILclient!ya;)V")
	public void method5651(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		Class27_Sub4.method3943(arg0);
		@Pc(19) int local19;
		if (super.anInt7302 > 1) {
			for (@Pc(15) int local15 = 0; super.anInt7296 > local15; local15++) {
				for (local19 = 0; super.anInt7290 > local19; local19++) {
					if ((SeqTypeList.aByteArrayArrayArray2[1][local15][local19] & 0x2) == 2) {
						NPCTypeList.method5224(local15, local19);
					}
				}
			}
		}
		for (local19 = 0; local19 < super.anInt7302; local19++) {
			for (@Pc(67) int local67 = 0; local67 <= super.anInt7290; local67++) {
				for (@Pc(71) int local71 = 0; super.anInt7296 >= local71; local71++) {
					@Pc(89) int local89;
					@Pc(91) int local91;
					@Pc(93) int local93;
					@Pc(140) int local140;
					@Pc(144) int local144;
					@Pc(231) int local231;
					@Pc(240) int local240;
					@Pc(260) int local260;
					@Pc(264) int local264;
					if ((super.occluderFlags[local19][local71][local67] & 0x1) != 0) {
						local89 = local67;
						local91 = local67;
						local93 = local19;
						while (super.anInt7290 > local91 && (super.occluderFlags[local19][local71][local91 + 1] & 0x1) != 0) {
							local91++;
						}
						while (local89 > 0 && (super.occluderFlags[local19][local71][local89 - 1] & 0x1) != 0) {
							local89--;
						}
						local140 = local19;
						label164: while (local93 > 0) {
							for (local144 = local89; local144 <= local91; local144++) {
								if ((super.occluderFlags[local93 - 1][local71][local144] & 0x1) == 0) {
									break label164;
								}
							}
							local93--;
						}
						label153: while (local140 < 3) {
							for (local144 = local89; local144 <= local91; local144++) {
								if ((super.occluderFlags[local140 + 1][local71][local144] & 0x1) == 0) {
									break label153;
								}
							}
							local140++;
						}
						local144 = (local91 + 1 - local89) * (local140 + 1 - local93);
						if (local144 >= 2) {
							local231 = super.tileHeights[local140][local71][local89] - 240;
							local240 = super.tileHeights[local93][local71][local89];
							EasedAngle.method814(1, local71 << 7, local71 << 7, local89 << 7, (local91 << 7) + 128, local231, local240);
							for (local260 = local93; local260 <= local140; local260++) {
								for (local264 = local89; local264 <= local91; local264++) {
									super.occluderFlags[local260][local71][local264] &= 0xFFFFFFFE;
								}
							}
						}
					}
					if ((super.occluderFlags[local19][local71][local67] & 0x2) != 0) {
						local89 = local71;
						local91 = local71;
						local93 = local19;
						while (local91 < super.anInt7296 && (super.occluderFlags[local19][local91 + 1][local67] & 0x2) != 0) {
							local91++;
						}
						while (local89 > 0 && (super.occluderFlags[local19][local89 - 1][local67] & 0x2) != 0) {
							local89--;
						}
						local140 = local19;
						label218: while (local93 > 0) {
							for (local144 = local89; local144 <= local91; local144++) {
								if ((super.occluderFlags[local93 - 1][local144][local67] & 0x2) == 0) {
									break label218;
								}
							}
							local93--;
						}
						label207: while (local140 < 3) {
							for (local144 = local89; local144 <= local91; local144++) {
								if ((super.occluderFlags[local140 + 1][local144][local67] & 0x2) == 0) {
									break label207;
								}
							}
							local140++;
						}
						local144 = (local140 + 1 - local93) * (local91 + 1 - local89);
						if (local144 >= 2) {
							local231 = super.tileHeights[local140][local89][local67] - 240;
							local240 = super.tileHeights[local93][local89][local67];
							EasedAngle.method814(2, local89 << 7, (local91 << 7) + 128, local67 << 7, local67 << 7, local231, local240);
							for (local260 = local93; local260 <= local140; local260++) {
								for (local264 = local89; local264 <= local91; local264++) {
									super.occluderFlags[local260][local264][local67] &= 0xFFFFFFFD;
								}
							}
						}
					}
					if ((super.occluderFlags[local19][local71][local67] & 0x4) != 0) {
						local89 = local71;
						local91 = local71;
						for (local93 = local67; local93 > 0 && (super.occluderFlags[local19][local71][local93 - 1] & 0x4) != 0; local93--) {
						}
						for (local140 = local67; local140 < super.anInt7290 && (super.occluderFlags[local19][local71][local140 + 1] & 0x4) != 0; local140++) {
						}
						label272: while (local89 > 0) {
							for (local144 = local93; local144 <= local140; local144++) {
								if ((super.occluderFlags[local19][local89 - 1][local144] & 0x4) == 0) {
									break label272;
								}
							}
							local89--;
						}
						label261: while (local91 < super.anInt7296) {
							for (local144 = local93; local144 <= local140; local144++) {
								if ((super.occluderFlags[local19][local91 + 1][local144] & 0x4) == 0) {
									break label261;
								}
							}
							local91++;
						}
						if ((local140 + 1 - local93) * (-local89 + (local91 - -1)) >= 4) {
							local144 = super.tileHeights[local19][local89][local93];
							EasedAngle.method814(4, local89 << 7, (local91 << 7) + 128, local93 << 7, (local140 << 7) + 128, local144, local144);
							for (@Pc(699) int local699 = local89; local699 <= local91; local699++) {
								for (local231 = local93; local231 <= local140; local231++) {
									super.occluderFlags[local19][local699][local231] &= 0xFFFFFFFB;
								}
							}
						}
					}
				}
			}
		}
		super.occluderFlags = null;
	}

@OriginalMember(owner = "client!wn", name = "M", descriptor = "F")
	public static float aFloat224;
@OriginalMember(owner = "client!wn", name = "K", descriptor = "I")
	public static int anInt7307 = 0;
@OriginalMember(owner = "client!wn", name = "a", descriptor = "(Z)[Lclient!bf;")
	public static Class21[] method5650() {
		return new Class21[] { Class4_Sub5.aClass21_4, Static410.aClass21_15, QuickChatValueType.aClass21_9, StructTypeList.aClass21_7, Static179.aClass21_10, Js5ResourceRequest.aClass21_11, Class25.aClass21_5, Class186.aClass21_13, MaterialType.aClass21_16, NPCTypeList.aClass21_14, Static133.aClass21_8, Class28.aClass21_6, Class14.aClass21_2, Class259.aClass21_17, Js5ResourceRequest.aClass21_12 };
	}
}
