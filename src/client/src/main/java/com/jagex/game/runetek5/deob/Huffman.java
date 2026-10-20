package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.graphics.map.worldmap.WorldMapElement;
import com.jagex.game.runetek5.tools.Isaac;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteTypeList;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub23;
import com.jagex.game.runetek5.deob.TextureCombinerOp;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.entity.pathingentity.player.PlayerAppearance;
import com.jagex.game.runetek5.config.param.ParamTypeList;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
import com.jagex.game.runetek5.client.input.keyboard.KeyRecordEntry;
import com.jagex.game.runetek5.toolkit.opengl.EffectManagerGL;
import com.jagex.game.runetek5.deob.Class243;
import com.jagex.game.runetek5.toolkit.vertexdeclaration.VertexDeclarationComponent;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.deob.Class145_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.config.sequence.PathingEntitySequence;
import com.jagex.game.runetek5.JavaThreadResource;
import com.jagex.game.runetek5.config.idktype.IDKTypeList;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.LegacyMouseManager;
import com.jagex.game.runetek5.deob.Class16_Sub3;
@OriginalClass("client!ji")
public final class Huffman {

	@OriginalMember(owner = "client!ji", name = "l", descriptor = "[B")
	private final byte[] aByteArray40;

	@OriginalMember(owner = "client!ji", name = "b", descriptor = "[I")
	private final int[] anIntArray231;

	@OriginalMember(owner = "client!ji", name = "d", descriptor = "[I")
	private int[] anIntArray232;

	@OriginalMember(owner = "client!ji", name = "<init>", descriptor = "([B)V")
	public Huffman(@OriginalArg(0) byte[] arg0) {
		@Pc(6) int local6 = arg0.length;
		this.aByteArray40 = arg0;
		this.anIntArray231 = new int[local6];
		this.anIntArray232 = new int[8];
		@Pc(20) int[] local20 = new int[33];
		@Pc(22) int local22 = 0;
		for (@Pc(24) int local24 = 0; local24 < local6; local24++) {
			@Pc(30) byte local30 = arg0[local24];
			if (local30 != 0) {
				@Pc(39) int local39 = 0x1 << 32 - local30;
				@Pc(43) int local43 = local20[local30];
				this.anIntArray231[local24] = local43;
				@Pc(61) int local61;
				@Pc(71) int local71;
				@Pc(77) int local77;
				@Pc(91) int local91;
				if ((local39 & local43) == 0) {
					local61 = local39 | local43;
					for (local71 = local30 - 1; local71 >= 1; local71--) {
						local77 = local20[local71];
						if (local77 != local43) {
							break;
						}
						local91 = 0x1 << 32 - local71;
						if ((local91 & local77) != 0) {
							local20[local71] = local20[local71 - 1];
							break;
						}
						local20[local71] = local91 | local77;
					}
				} else {
					local61 = local20[local30 - 1];
				}
				local20[local30] = local61;
				for (local71 = local30 + 1; local71 <= 32; local71++) {
					if (local43 == local20[local71]) {
						local20[local71] = local61;
					}
				}
				local77 = 0;
				for (local91 = 0; local91 < local30; local91++) {
					@Pc(157) int local157 = Integer.MIN_VALUE >>> local91;
					if ((local43 & local157) == 0) {
						local77++;
					} else {
						if (this.anIntArray232[local77] == 0) {
							this.anIntArray232[local77] = local22;
						}
						local77 = this.anIntArray232[local77];
					}
					if (this.anIntArray232.length <= local77) {
						@Pc(205) int[] local205 = new int[this.anIntArray232.length * 2];
						for (@Pc(207) int local207 = 0; local207 < this.anIntArray232.length; local207++) {
							local205[local207] = this.anIntArray232[local207];
						}
						this.anIntArray232 = local205;
					}
				}
				this.anIntArray232[local77] = ~local24;
				if (local22 <= local77) {
					local22 = local77 + 1;
				}
			}
		}
	}

	@OriginalMember(owner = "client!ji", name = "a", descriptor = "([BI[BIII)I")
	public int method2933(@OriginalArg(0) byte[] arg0, @OriginalArg(1) int arg1, @OriginalArg(2) byte[] arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4) {
		if (arg3 == 0) {
			return 0;
		}
		@Pc(13) int local13 = arg3;
		@Pc(15) int local15 = 0;
		@Pc(17) int local17 = arg4;
		while (true) {
			@Pc(30) byte local30 = arg0[local17];
			if (local30 >= 0) {
				local15++;
			} else {
				local15 = this.anIntArray232[local15];
			}
			@Pc(49) int local49;
			if ((local49 = this.anIntArray232[local15]) < 0) {
				arg2[arg1++] = (byte) ~local49;
				if (arg1 >= local13) {
					break;
				}
				local15 = 0;
			}
			if ((local30 & 0x40) == 0) {
				local15++;
			} else {
				local15 = this.anIntArray232[local15];
			}
			@Pc(88) int local88;
			if ((local88 = this.anIntArray232[local15]) < 0) {
				arg2[arg1++] = (byte) ~local88;
				if (local13 <= arg1) {
					break;
				}
				local15 = 0;
			}
			if ((local30 & 0x20) == 0) {
				local15++;
			} else {
				local15 = this.anIntArray232[local15];
			}
			@Pc(123) int local123;
			if ((local123 = this.anIntArray232[local15]) < 0) {
				arg2[arg1++] = (byte) ~local123;
				if (arg1 >= local13) {
					break;
				}
				local15 = 0;
			}
			if ((local30 & 0x10) == 0) {
				local15++;
			} else {
				local15 = this.anIntArray232[local15];
			}
			@Pc(162) int local162;
			if ((local162 = this.anIntArray232[local15]) < 0) {
				arg2[arg1++] = (byte) ~local162;
				if (local13 <= arg1) {
					break;
				}
				local15 = 0;
			}
			if ((local30 & 0x8) == 0) {
				local15++;
			} else {
				local15 = this.anIntArray232[local15];
			}
			@Pc(197) int local197;
			if ((local197 = this.anIntArray232[local15]) < 0) {
				arg2[arg1++] = (byte) ~local197;
				if (local13 <= arg1) {
					break;
				}
				local15 = 0;
			}
			if ((local30 & 0x4) == 0) {
				local15++;
			} else {
				local15 = this.anIntArray232[local15];
			}
			@Pc(236) int local236;
			if ((local236 = this.anIntArray232[local15]) < 0) {
				arg2[arg1++] = (byte) ~local236;
				if (arg1 >= local13) {
					break;
				}
				local15 = 0;
			}
			if ((local30 & 0x2) == 0) {
				local15++;
			} else {
				local15 = this.anIntArray232[local15];
			}
			@Pc(278) int local278;
			if ((local278 = this.anIntArray232[local15]) < 0) {
				arg2[arg1++] = (byte) ~local278;
				if (arg1 >= local13) {
					break;
				}
				local15 = 0;
			}
			if ((local30 & 0x1) == 0) {
				local15++;
			} else {
				local15 = this.anIntArray232[local15];
			}
			@Pc(317) int local317;
			if ((local317 = this.anIntArray232[local15]) < 0) {
				arg2[arg1++] = (byte) ~local317;
				if (arg1 >= local13) {
					break;
				}
				local15 = 0;
			}
			local17++;
		}
		return local17 + 1 - arg4;
	}

	@OriginalMember(owner = "client!ji", name = "a", descriptor = "([BIBI[BI)I")
	public int method2935(@OriginalArg(0) byte[] arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) byte[] arg3, @OriginalArg(5) int arg4) {
		@Pc(12) int local12 = 0;
		@Pc(16) int local16 = arg4;
		@Pc(20) int local20 = arg1 << 3;
		while (arg2 < local16) {
			@Pc(28) int local28 = arg0[arg2] & 0xFF;
			@Pc(33) int local33 = this.anIntArray231[local28];
			@Pc(38) byte local38 = this.aByteArray40[local28];
			if (local38 == 0) {
				throw new RuntimeException("No codeword for data value " + local28);
			}
			@Pc(58) int local58 = local20 >> 3;
			@Pc(62) int local62 = local20 & 0x7;
			local12 &= -local62 >> 31;
			@Pc(79) int local79 = (local38 + local62 - 1 >> 3) + local58;
			@Pc(80) int local80 = local62 + 24;
			arg3[local58] = (byte) (local12 |= local33 >>> local80);
			if (local58 < local79) {
				local62 = local80 - 8;
				local58++;
				arg3[local58] = (byte) (local12 = local33 >>> local62);
				if (local79 > local58) {
					local58++;
					local62 -= 8;
					arg3[local58] = (byte) (local12 = local33 >>> local62);
					if (local58 < local79) {
						local62 -= 8;
						local58++;
						arg3[local58] = (byte) (local12 = local33 >>> local62);
						if (local79 > local58) {
							local62 -= 8;
							local58++;
							arg3[local58] = (byte) (local12 = local33 << -local62);
						}
					}
				}
			}
			local20 += local38;
			arg2++;
		}
		return (local20 + 7 >> 3) - arg1;
	}

@OriginalMember(owner = "client!ji", name = "g", descriptor = "[I")
	public static final int[] anIntArray233 = new int[14];
@OriginalMember(owner = "client!ji", name = "h", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_74 = new Class83("K", "T", "K", "K");
@OriginalMember(owner = "client!ji", name = "m", descriptor = "I")
	public static int anInt3559 = 0;
@OriginalMember(owner = "client!ji", name = "n", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_159 = new Class22(16, -1);
@OriginalMember(owner = "client!ji", name = "o", descriptor = "I")
	public static int anInt3560 = 0;
@OriginalMember(owner = "client!ji", name = "a", descriptor = "(BI)V")
	public static void method2932(@OriginalArg(1) int arg0) {
		if (arg0 == VertexDeclarationComponent.anInt6667) {
			return;
		}
		if (VertexDeclarationComponent.anInt6667 == 0) {
			Class4_Sub6_Sub23.method3676();
		}
		if (arg0 == 40) {
			Class145_Sub1.method5617();
		}
		if (arg0 != 40 && IDKTypeList.aClass11_6 != null) {
			IDKTypeList.aClass11_6.method138();
			IDKTypeList.aClass11_6 = null;
		}
		if (VertexDeclarationComponent.anInt6667 == 25 || VertexDeclarationComponent.anInt6667 == 28) {
			Isaac.aClass76_20.loadType = 2;
			Class36.aClass76_29.loadType = 2;
			Class4_Sub43.aClass76_99.loadType = 2;
			ParamTypeList.aClass76_87.loadType = 2;
			Static391.aClass76_91.loadType = 2;
			Class230.aClass76_90.loadType = 2;
			WorldMapElement.aClass76_16.loadType = 2;
		}
		if (arg0 == 25 || arg0 == 28) {
			PathingEntitySequence.anInt5144 = 0;
			KeyRecordEntry.anInt448 = 1;
			PlayerAppearance.anInt883 = 0;
			TextureOpIrregularBricks.lastMiscUpdateTick = 0;
			Terrain.anInt7298 = 1;
			Class4_Sub7.method633(true);
			Isaac.aClass76_20.loadType = 1;
			Class36.aClass76_29.loadType = 1;
			Class4_Sub43.aClass76_99.loadType = 1;
			ParamTypeList.aClass76_87.loadType = 1;
			Static391.aClass76_91.loadType = 1;
			Class230.aClass76_90.loadType = 1;
			WorldMapElement.aClass76_16.loadType = 1;
		}
		if (arg0 == 25 || arg0 == 10) {
			Class30.method744();
		}
		if (arg0 == 5) {
			MapSpriteTypeList.method5592(MonoStereoData.aClass19_16, GlGround.aClass76_60);
		} else {
			LegacyMouseManager.method4834();
		}
		@Pc(135) boolean local135 = arg0 == 5 || arg0 == 10 || arg0 == 28;
		@Pc(148) boolean local148 = VertexDeclarationComponent.anInt6667 == 5 || VertexDeclarationComponent.anInt6667 == 10 || VertexDeclarationComponent.anInt6667 == 28;
		if (local135 != local148) {
			if (local135) {
				JavaThreadResource.mapBuildLevel = EffectManagerGL.anInt6866;
				if (TextureOpBrightness.aClass50_Sub1_1.anInt3439 == 0) {
					Class16_Sub3.method724();
				} else {
					Class243.method5258(TextureOpBrightness.aClass50_Sub1_1.anInt3439, TextureCombinerOp.aClass76_54, EffectManagerGL.anInt6866);
				}
				MECType.aClass255_2.writeLoggedIn(false);
			} else {
				Class16_Sub3.method724();
				MECType.aClass255_2.writeLoggedIn(true);
			}
		}
		if (arg0 == 25 || arg0 == 28 || arg0 == 40) {
			MonoStereoData.aClass19_16.method4281();
		}
		VertexDeclarationComponent.anInt6667 = arg0;
	}
@OriginalMember(owner = "client!ji", name = "a", descriptor = "(IIIII)V")
	public static void method2934(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		if (TextureOpBrightness.aClass50_Sub1_1.anInt3448 != 0 && arg1 != 0 && Class16.anInt6886 < 50 && arg0 != -1) {
			OpenGLTextureCache.aClass269Array1[Class16.anInt6886++] = new Class269((byte) 1, arg0, arg1, arg3, arg2, 0);
		}
	}
@OriginalMember(owner = "client!ji", name = "b", descriptor = "(I)Lclient!aq;")
	public static Class4_Sub3 method2937() {
		if (Static148.worldMapCategories == null || Static308.aClass63_1 == null) {
			return null;
		}
		for (@Pc(16) Class4_Sub3 local16 = (Class4_Sub3) Static308.aClass63_1.method1612(); local16 != null; local16 = (Class4_Sub3) Static308.aClass63_1.method1612()) {
			@Pc(24) MECType local24 = Static148.MECTYPE_LIST.method4124(local16.anInt234);
			if (local24 != null && local24.aBoolean448 && local24.method3778(Static148.PLAYER_VAR_PROVIDER)) {
				return local16;
			}
		}
		return null;
	}
@OriginalMember(owner = "client!ji", name = "a", descriptor = "(IIIIIII)I")
	public static int method2938(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5) {
		@Pc(3) int local3 = arg1 & 0x3;
		if ((arg4 & 0x1) == 1) {
			@Pc(16) int local16 = arg5;
			arg5 = arg2;
			arg2 = local16;
		}
		if (local3 == 0) {
			return arg0;
		} else if (local3 == 1) {
			return arg3;
		} else if (local3 == 2) {
			return 1 + 7 - arg5 - arg0;
		} else {
			return 1 + 7 - arg2 - arg3;
		}
	}
}
