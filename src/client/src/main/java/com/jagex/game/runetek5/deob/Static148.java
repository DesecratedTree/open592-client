package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.unpack_Sub1;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.MultiLocEntry;
import com.jagex.game.runetek5.config.flo.FloTypeList;
import com.jagex.game.runetek5.config.flu.FluTypeList;
import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteType;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteTypeList;
import com.jagex.game.runetek5.config.mec.MECTypeList;
import com.jagex.game.runetek5.config.staticelements.StaticElements;
import com.jagex.game.runetek5.entity.EntityNodeList;
import com.jagex.game.runetek5.entity.WorldMapTile;
import com.jagex.game.runetek5.graphics.map.worldmap.WorldMapElement;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.vardomain.VarDomain;

import com.jagex.game.runetek5.deob.Class9_Sub4;
import com.jagex.game.runetek5.deob.Class62;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.deob.Class56;
import com.jagex.game.runetek5.entity.PathingEntity;
public final class Static148 {

	@OriginalMember(owner = "client!hj", name = "a", descriptor = "Lclient!cn;")
	public static WorldMapElement area;

	@OriginalMember(owner = "client!hj", name = "c", descriptor = "Lclient!pj;")
	public static MECTypeList MECTYPE_LIST;

	@OriginalMember(owner = "client!hj", name = "d", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider MAP_AREA_RESOURCE;

	@OriginalMember(owner = "client!hj", name = "e", descriptor = "Lclient!wi;")
	public static MapSpriteTypeList MAP_SPRITE_TYPE_LIST;

	@OriginalMember(owner = "client!hj", name = "f", descriptor = "Lclient!vs;")
	public static VarDomain PLAYER_VAR_PROVIDER;

	@OriginalMember(owner = "client!hj", name = "g", descriptor = "Lclient!qd;")
	private static FluTypeList FLUTYPE_LIST;

	@OriginalMember(owner = "client!hj", name = "h", descriptor = "Lclient!kb;")
	private static FloTypeList FLOTYPE_LIST;

	@OriginalMember(owner = "client!hj", name = "i", descriptor = "Lclient!wf;")
	public static LocTypeList LOCTYPE_LIST;

	@OriginalMember(owner = "client!hj", name = "k", descriptor = "I")
	public static int tileSize;

	@OriginalMember(owner = "client!hj", name = "l", descriptor = "Lclient!qk;")
	public static StaticElements staticElements;

	@OriginalMember(owner = "client!hj", name = "m", descriptor = "[[[B")
	public static byte[][][] tileShapePatterns;

	@OriginalMember(owner = "client!hj", name = "n", descriptor = "F")
	public static float mapZoom;

	@OriginalMember(owner = "client!hj", name = "q", descriptor = "F")
	public static float requestedMapZoom;

	@OriginalMember(owner = "client!hj", name = "t", descriptor = "I")
	public static int tileViewEndY;

	@OriginalMember(owner = "client!hj", name = "u", descriptor = "I")
	public static int pixelViewStartX;

	@OriginalMember(owner = "client!hj", name = "v", descriptor = "I")
	public static int anInt5724;

	@OriginalMember(owner = "client!hj", name = "w", descriptor = "[S")
	private static short[] tileLocIds;

	@OriginalMember(owner = "client!hj", name = "x", descriptor = "[I")
	private static int[] overlayFloorColors;

	@OriginalMember(owner = "client!hj", name = "y", descriptor = "[B")
	private static byte[] underlayIds;

	@OriginalMember(owner = "client!hj", name = "z", descriptor = "[B")
	private static byte[] overlayIds;

	@OriginalMember(owner = "client!hj", name = "A", descriptor = "Lclient!ib;")
	private static CubeMapSpecularEffect aClass102_31;

	@OriginalMember(owner = "client!hj", name = "B", descriptor = "I")
	public static int tileViewStartY;

	@OriginalMember(owner = "client!hj", name = "C", descriptor = "I")
	public static int tileViewStartX;

	@OriginalMember(owner = "client!hj", name = "D", descriptor = "I")
	public static int anInt5727;

	@OriginalMember(owner = "client!hj", name = "E", descriptor = "[B")
	private static byte[] overlayShapes;

	@OriginalMember(owner = "client!hj", name = "F", descriptor = "[B")
	private static byte[] underlayColoursHigh;

	@OriginalMember(owner = "client!hj", name = "G", descriptor = "I")
	public static int tileViewEndX;

	@OriginalMember(owner = "client!hj", name = "H", descriptor = "[B")
	private static byte[] tileLocShapes;

	@OriginalMember(owner = "client!hj", name = "I", descriptor = "I")
	public static int anInt5729;

	@OriginalMember(owner = "client!hj", name = "K", descriptor = "I")
	public static int pixelViewEndY;

	@OriginalMember(owner = "client!hj", name = "L", descriptor = "I")
	public static int anInt5731;

	@OriginalMember(owner = "client!hj", name = "M", descriptor = "I")
	public static int anInt5732;

	@OriginalMember(owner = "client!hj", name = "N", descriptor = "I")
	public static int anInt5733;

	@OriginalMember(owner = "client!hj", name = "O", descriptor = "[S")
	private static short[] underlayColoursLow;

	@OriginalMember(owner = "client!hj", name = "P", descriptor = "[[[Lclient!gn;")
	private static EntityNodeList[][][] tiles;

	@OriginalMember(owner = "client!hj", name = "b", descriptor = "Lclient!ib;")
	private static final CubeMapSpecularEffect aClass102_30 = new CubeMapSpecularEffect(16);

	@OriginalMember(owner = "client!hj", name = "j", descriptor = "I")
	public static int anInt5719 = (int) (Math.random() * 11.0D) - 5;

	@OriginalMember(owner = "client!hj", name = "o", descriptor = "Lclient!pk;")
	public static final Class183 worldMapCategories = new Class183();

	@OriginalMember(owner = "client!hj", name = "p", descriptor = "[S")
	private static final short[] singleLocIds = new short[1];

	@OriginalMember(owner = "client!hj", name = "r", descriptor = "[B")
	private static final byte[] singleLocShapes = new byte[1];

	@OriginalMember(owner = "client!hj", name = "s", descriptor = "I")
	public static int anInt5721 = (int) (Math.random() * 17.0D) - 8;

	@OriginalMember(owner = "client!hj", name = "a", descriptor = "(I)V")
	public static void method4492(@OriginalArg(0) int arg0) {
		area = (WorldMapElement) aClass102_30.method2700((long) arg0);
	}

	@OriginalMember(owner = "client!hj", name = "a", descriptor = "(Lclient!ya;IIII)V")
	private static void drawTiles(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		@Pc(3) int local3 = tileViewEndX - tileViewStartX;
		@Pc(7) int local7 = tileViewEndY - tileViewStartY;
		if (tileViewEndX < anInt5729) {
			local3++;
		}
		if (tileViewEndY < anInt5733) {
			local7++;
		}
		@Pc(28) int local28;
		@Pc(40) int local40;
		@Pc(44) int local44;
		@Pc(50) int local50;
		@Pc(57) int local57;
		@Pc(70) int local70;
		@Pc(80) int local80;
		@Pc(84) int local84;
		@Pc(93) int local93;
		@Pc(173) int local173;
		@Pc(175) int local175;
		@Pc(177) int local177;
		@Pc(179) int local179;
		for (@Pc(17) int local17 = 0; local17 < local3; local17++) {
			local28 = (arg1 * local17 >> 16) + pixelViewStartX;
			local40 = (arg1 * (local17 + 1) >> 16) + pixelViewStartX;
			local44 = local40 - local28;
			if (local44 > 0) {
				local50 = tileViewStartX + local17;
				if (local50 >= 0 && local50 < anInt5729) {
					for (local57 = 0; local57 < local7; local57++) {
						local70 = pixelViewEndY - (arg2 * (local57 + 1) >> 16);
						local80 = pixelViewEndY - (arg2 * local57 >> 16);
						local84 = local80 - local70;
						if (local84 > 0) {
							local93 = local57 + tileViewStartY;
							local173 = local50 + local93 * anInt5729;
							local175 = 0;
							local177 = 0;
							local179 = 0;
							if (local93 >= 0 && local93 < anInt5733) {
								local175 = (underlayColoursHigh[local173] & 0xFF) << 16 | underlayColoursLow[local173] & 0xFFFF;
								if (local175 != 0) {
									local175 |= 0xFF000000;
								}
								local177 = overlayIds[local173] & 0xFF;
								local179 = tileLocIds[local173] & 0xFFFF;
							}
							if (local175 == 0 && local177 == 0 && local179 == 0) {
								if (area.color != -1) {
									local175 = area.color | 0xFF000000;
								} else if ((local17 + tileViewStartX & 0x4) == (local57 + tileViewEndY & 0x4)) {
									local175 = overlayFloorColors[FLOTYPE_LIST.lastLoadedOverlayId + 1];
								} else {
									local175 = -11840664;
								}
								if (local175 == 0) {
									local175 = -16777216;
								}
								arg0.O(local28, local70, local44, local84, local175, 0);
							} else if (local179 <= 0) {
								drawTile(arg0, local28, local70, local44, local84, local175, local177, overlayShapes[local173], null, null, true);
							} else if (local179 == 65535) {
								@Pc(282) MultiLocEntry local282 = (MultiLocEntry) aClass102_31.method2700((long) (local50 << 16 | local93));
								if (local282 != null) {
									drawTile(arg0, local28, local70, local44, local84, local175, local177, overlayShapes[local173], local282.locIds, local282.locShapes, true);
								}
							} else {
								singleLocIds[0] = (short) (local179 - 1);
								singleLocShapes[0] = tileLocShapes[local173];
								drawTile(arg0, local28, local70, local44, local84, local175, local177, overlayShapes[local173], singleLocIds, singleLocShapes, true);
							}
						}
					}
				} else {
					for (local57 = 0; local57 < local7; local57++) {
						local70 = pixelViewEndY - (arg2 * (local57 + 1) >> 16);
						local80 = pixelViewEndY - (arg2 * local57 >> 16);
						local84 = local80 - local70;
						if (area.color != -1) {
							local93 = area.color | 0xFF000000;
						} else if ((local17 + tileViewStartX & 0x4) == (local57 + tileViewEndY & 0x4)) {
							local93 = overlayFloorColors[FLOTYPE_LIST.lastLoadedOverlayId + 1];
						} else {
							local93 = -11840664;
						}
						if (local93 == 0) {
							local93 = -16777216;
						}
						arg0.O(local28, local70, local44, local84, local93, 0);
					}
				}
			}
		}
		for (local28 = -16; local28 < local3 + 16; local28++) {
			local40 = (arg1 * local28 >> 16) + pixelViewStartX;
			local44 = (arg1 * (local28 + 1) >> 16) + pixelViewStartX;
			local50 = local44 - local40;
			if (local50 > 0) {
				local57 = local28 + tileViewStartX;
				if (local57 >= 0 && local57 < anInt5729) {
					for (local70 = -16; local70 < local7 + 16; local70++) {
						local80 = pixelViewEndY - (arg2 * (local70 + 1) >> 16);
						local84 = pixelViewEndY - (arg2 * local70 >> 16);
						local93 = local84 - local80;
						if (local93 > 0) {
							local173 = local70 + tileViewStartY;
							if (local173 >= 0 && local173 < anInt5733) {
								local175 = tileLocIds[local57 + local173 * anInt5729] & 0xFFFF;
								if (local175 <= 0) {
									drawMsiMultiple(arg0, local40, local80, local50, local93, null, null);
								} else if (local175 == 65535) {
									@Pc(459) MultiLocEntry local459 = (MultiLocEntry) aClass102_31.method2700((long) (local57 << 16 | local173));
									if (local459 != null) {
										drawMsiMultiple(arg0, local40, local80, local50, local93, local459.locIds, local459.locShapes);
									}
								} else {
									singleLocIds[0] = (short) (local175 - 1);
									singleLocShapes[0] = tileLocShapes[local57 + local173 * anInt5729];
									drawMsiMultiple(arg0, local40, local80, local50, local93, singleLocIds, singleLocShapes);
								}
							}
						}
					}
				}
			}
		}
		local40 = tileViewStartX >> 6;
		local44 = tileViewStartY >> 6;
		if (local40 < 0) {
			local40 = 0;
		}
		if (local44 < 0) {
			local44 = 0;
		}
		local50 = tileViewEndX >> 6;
		local57 = tileViewEndY >> 6;
		if (local50 >= tiles[0].length) {
			local50 = tiles[0].length - 1;
		}
		if (local57 >= tiles[0][0].length) {
			local57 = tiles[0][0].length - 1;
		}
		for (local70 = 0; local70 < 3; local70++) {
			@Pc(641) int local641;
			@Pc(653) int local653;
			@Pc(665) int local665;
			@Pc(675) int local675;
			@Pc(631) int local631;
			for (local80 = local40; local80 <= local50; local80++) {
				for (local84 = local44; local84 <= local57; local84++) {
					@Pc(589) EntityNodeList local589 = tiles[local70][local80][local84];
					if (local589 != null) {
						local173 = (local80 + (anInt5732 >> 6)) * 64;
						local175 = (local84 + (anInt5724 >> 6)) * 64;
						for (@Pc(612) WorldMapTile local612 = (WorldMapTile) local589.method2312(); local612 != null; local612 = (WorldMapTile) local589.method2309()) {
							local179 = local173 + local612.x - anInt5732 - tileViewStartX;
							local631 = local175 + local612.z - anInt5724 - tileViewStartY;
							local641 = (arg1 * local179 >> 16) + pixelViewStartX;
							local653 = (arg1 * (local179 + 1) >> 16) + pixelViewStartX;
							local665 = pixelViewEndY - (arg2 * (local631 + 1) >> 16);
							local675 = pixelViewEndY - (arg2 * local631 >> 16);
							drawTile(arg0, local641, local665, local653 - local641, local675 - local665, local612.underlayIdOrColour, local612.overlayId & 0xFF, local612.shape, local612.locIds, local612.locShapes, false);
						}
					}
				}
			}
			for (local84 = local40; local84 <= local50; local84++) {
				for (local93 = local44; local93 <= local57; local93++) {
					@Pc(727) EntityNodeList local727 = tiles[local70][local84][local93];
					if (local727 != null) {
						local175 = (local84 + (anInt5732 >> 6)) * 64;
						local177 = (local93 + (anInt5724 >> 6)) * 64;
						for (@Pc(750) WorldMapTile local750 = (WorldMapTile) local727.method2312(); local750 != null; local750 = (WorldMapTile) local727.method2309()) {
							local631 = local175 + local750.x - anInt5732 - tileViewStartX;
							local641 = local177 + local750.z - anInt5724 - tileViewStartY;
							local653 = (arg1 * local631 >> 16) + pixelViewStartX;
							local665 = (arg1 * (local631 + 1) >> 16) + pixelViewStartX;
							local675 = pixelViewEndY - (arg2 * (local641 + 1) >> 16);
							@Pc(813) int local813 = pixelViewEndY - (arg2 * local641 >> 16);
							drawMsiMultiple(arg0, local653, local675, local665 - local653, local813 - local675, local750.locIds, local750.locShapes);
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!hj", name = "a", descriptor = "(II)Lclient!cn;")
	public static WorldMapElement method4494(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		for (@Pc(4) WorldMapElement local4 = (WorldMapElement) aClass102_30.method2705(); local4 != null; local4 = (WorldMapElement) aClass102_30.method2704()) {
			if (local4.shouldRender && local4.method932(arg1, arg0)) {
				return local4;
			}
		}
		return null;
	}

	@OriginalMember(owner = "client!hj", name = "a", descriptor = "([B[B[SII)V")
	private static void decodeUnderlayColors(@OriginalArg(0) byte[] tileUnderlayIds, @OriginalArg(1) byte[] tileRed, @OriginalArg(2) short[] tileGreenBlue, @OriginalArg(3) int hueOffset, @OriginalArg(4) int lightnessOffset) {
		@Pc(2) int[] local2 = new int[anInt5733];
		@Pc(5) int[] local5 = new int[anInt5733];
		@Pc(8) int[] local8 = new int[anInt5733];
		@Pc(11) int[] local11 = new int[anInt5733];
		@Pc(14) int[] local14 = new int[anInt5733];
		for (@Pc(16) int local16 = -5; local16 < anInt5729; local16++) {
			@Pc(21) int local21 = local16 + 5;
			@Pc(25) int local25 = local16 - 5;
			@Pc(41) int local41;
			for (@Pc(27) int local27 = 0; local27 < anInt5733; local27++) {
				@Pc(50) FluType local50;
				@Pc(86) int local86;
				if (local21 < anInt5729) {
					local41 = tileUnderlayIds[local21 + local27 * anInt5729] & 0xFF;
					if (local41 > 0) {
						local50 = FLUTYPE_LIST.method4340(local41 - 1);
						local2[local27] += local50.anInt6348;
						local5[local27] += local50.anInt6341;
						local8[local27] += local50.anInt6343;
						local11[local27] += local50.anInt6342;
						local86 = local14[local27]++;
					}
				}
				if (local25 >= 0) {
					local41 = tileUnderlayIds[local25 + local27 * anInt5729] & 0xFF;
					if (local41 > 0) {
						local50 = FLUTYPE_LIST.method4340(local41 - 1);
						local2[local27] -= local50.anInt6348;
						local5[local27] -= local50.anInt6341;
						local8[local27] -= local50.anInt6343;
						local11[local27] -= local50.anInt6342;
						local86 = local14[local27]--;
					}
				}
			}
			if (local16 >= 0) {
				local41 = 0;
				@Pc(159) int local159 = 0;
				@Pc(161) int local161 = 0;
				@Pc(163) int local163 = 0;
				@Pc(165) int local165 = 0;
				for (@Pc(167) int local167 = -5; local167 < anInt5733; local167++) {
					@Pc(172) int local172 = local167 + 5;
					if (local172 < anInt5733) {
						local41 += local2[local172];
						local159 += local5[local172];
						local161 += local8[local172];
						local163 += local11[local172];
						local165 += local14[local172];
					}
					@Pc(209) int local209 = local167 - 5;
					if (local209 >= 0) {
						local41 -= local2[local209];
						local159 -= local5[local209];
						local161 -= local8[local209];
						local163 -= local11[local209];
						local165 -= local14[local209];
					}
					if (local167 >= 0 && local165 > 0) {
						@Pc(261) int local261;
						if ((tileUnderlayIds[local16 + local167 * anInt5729] & 0xFF) == 0) {
							local261 = local16 + local167 * anInt5729;
							tileRed[local261] = 0;
							tileGreenBlue[local261] = 0;
						} else {
							local261 = local163 == 0 ? 0 : Class9_Sub4.method1599(local159 / local165, local41 * 256 / local163, local161 / local165);
							@Pc(294) int local294 = (local261 & 0x7F) + lightnessOffset;
							if (local294 < 0) {
								local294 = 0;
							} else if (local294 > 127) {
								local294 = 127;
							}
							@Pc(316) int local316 = (local261 + hueOffset & 0xFC00) + (local261 & 0x380) + local294;
							@Pc(322) int local322 = local16 + local167 * anInt5729;
							@Pc(333) int local333 = PathingEntity.anIntArray423[Class56.method1470(Static395.method5162(local316)) & 0xFFFF];
							tileRed[local322] = (byte) (local333 >> 16 & 0xFF);
							tileGreenBlue[local322] = (short) (local333 & 0xFFFF);
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!hj", name = "a", descriptor = "(Lclient!ya;IIIIIII[S[BZ)V")
	private static void drawTile(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int width, @OriginalArg(4) int arg4, @OriginalArg(5) int underlayColour, @OriginalArg(6) int overlay, @OriginalArg(7) int tileShapeAndRotation, @OriginalArg(8) short[] locIds, @OriginalArg(9) byte[] locShapes, @OriginalArg(10) boolean opaque) {
		@Pc(20) int local20;
		@Pc(32) int local32;
		if (opaque || underlayColour != 0 || overlay > 0) {
			if (overlay == 0) {
				toolkit.O(x, y, width, arg4, underlayColour, 0);
			} else {
				local20 = tileShapeAndRotation & 0x3F;
				if (local20 == 0 || width <= 1 || arg4 <= 1) {
					local32 = overlayFloorColors[overlay];
					if (opaque || local32 != 0) {
						toolkit.O(x, y, width, arg4, local32, 0);
					}
				} else {
					local32 = opaque ? 0 : 1;
					LocTypeList.method5563(tileShapeAndRotation >> 6 & 0x3, tileSize, toolkit, underlayColour, width, arg4, local32, x, y, local20, overlayFloorColors[overlay], tileShapePatterns);
				}
			}
		}
		if (locIds == null) {
			return;
		}
		if (width == 1) {
			local20 = x;
		} else {
			local20 = x + width - 1;
		}
		if (arg4 == 1) {
			local32 = y;
		} else {
			local32 = y + arg4 - 1;
		}
		for (@Pc(100) int local100 = 0; local100 < locIds.length; local100++) {
			@Pc(107) int local107 = locShapes[local100] & 0x3F;
			if (local107 == 0 || local107 == 2 || local107 == 3 || local107 == 9) {
				@Pc(127) LocType local127 = LOCTYPE_LIST.method5560(locIds[local100] & 0xFFFF);
				if (local127.anInt5803 == -1) {
					@Pc(133) int local133 = -3355444;
					if (local127.anInt5813 == 1) {
						local133 = -3407872;
					}
					@Pc(147) int local147 = locShapes[local100] >> 6 & 0x3;
					if (local107 == 0) {
						if (local147 == 0) {
							toolkit.TA(x, y, arg4, local133, 0);
						} else if (local147 == 1) {
							toolkit.b(x, y, width, local133, 0);
						} else if (local147 == 2) {
							toolkit.TA(local20, y, arg4, local133, 0);
						} else {
							toolkit.b(x, local32, width, local133, 0);
						}
					} else if (local107 == 2) {
						if (local147 == 0) {
							toolkit.TA(x, y, arg4, -1, 0);
							toolkit.b(x, y, width, local133, 0);
						} else if (local147 == 1) {
							toolkit.TA(local20, y, arg4, -1, 0);
							toolkit.b(x, y, width, local133, 0);
						} else if (local147 == 2) {
							toolkit.TA(local20, y, arg4, -1, 0);
							toolkit.b(x, local32, width, local133, 0);
						} else {
							toolkit.TA(x, y, arg4, -1, 0);
							toolkit.b(x, local32, width, local133, 0);
						}
					} else if (local107 == 3) {
						if (local147 == 0) {
							toolkit.b(x, y, 1, local133, 0);
						} else if (local147 == 1) {
							toolkit.b(local20, y, 1, local133, 0);
						} else if (local147 == 2) {
							toolkit.b(local20, local32, 1, local133, 0);
						} else {
							toolkit.b(x, local32, 1, local133, 0);
						}
					} else if (local107 == 9) {
						@Pc(313) int local313;
						if (local147 == 0 || local147 == 2) {
							for (local313 = 0; local313 < arg4; local313++) {
								toolkit.b(x + local313, local32 - local313, 1, local133, 0);
							}
						} else {
							for (local313 = 0; local313 < arg4; local313++) {
								toolkit.b(x + local313, y + local313, 1, local133, 0);
							}
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!hj", name = "a", descriptor = "(Lclient!ya;II)V")
	public static void renderMap(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) int pixelsPerTileX, @OriginalArg(2) int pixelsPerTileY) {
		@Pc(9) Packet local9 = new Packet(MAP_AREA_RESOURCE.method2109(area.filenamePrefix, "area"));
		@Pc(13) int local13 = local9.method2490();
		@Pc(16) int[] local16 = new int[local13];
		for (@Pc(18) int local18 = 0; local18 < local13; local18++) {
			local16[local18] = local9.method2490();
		}
		@Pc(33) int local33 = local9.method2490();
		@Pc(36) int[] local36 = new int[local33];
		for (@Pc(38) int local38 = 0; local38 < local33; local38++) {
			local36[local38] = local9.method2490();
		}
		while (true) {
			@Pc(58) int local58;
			@Pc(64) int local64;
			@Pc(67) int local67;
			@Pc(76) int local76;
			@Pc(148) int local148;
			while (local9.currentPosition < local9.buffer.length) {
				@Pc(62) int local62;
				@Pc(84) int local84;
				if (local9.method2490() == 0) {
					local58 = local9.method2490();
					local62 = local9.method2490();
					for (local64 = 0; local64 < 64; local64++) {
						for (local67 = 0; local67 < 64; local67++) {
							local76 = local58 * 64 + local64 - anInt5732;
							local84 = local62 * 64 + local67 - anInt5724;
							decodeTile(toolkit, local9, local58, local62, local76, local84, local16, local36);
						}
					}
				} else {
					local58 = local9.method2490();
					local62 = local9.method2490();
					local64 = local9.method2490();
					local67 = local9.method2490();
					for (local76 = 0; local76 < 8; local76++) {
						for (local84 = 0; local84 < 8; local84++) {
							@Pc(136) int local136 = local58 * 64 + local64 * 8 + local76 - anInt5732;
							local148 = local62 * 64 + local67 * 8 + local84 - anInt5724;
							decodeTile(toolkit, local9, local58, local62, local136, local148, local16, local36);
						}
					}
				}
			}
			underlayColoursHigh = new byte[anInt5729 * anInt5733];
			underlayColoursLow = new short[anInt5729 * anInt5733];
			for (local58 = 0; local58 < 3; local58++) {
				@Pc(191) byte[] local191 = new byte[anInt5729 * anInt5733];
				for (local64 = 0; local64 < tiles[local58].length; local64++) {
					for (local67 = 0; local67 < tiles[local58][0].length; local67++) {
						@Pc(205) EntityNodeList local205 = tiles[local58][local64][local67];
						if (local205 != null) {
							for (@Pc(212) WorldMapTile local212 = (WorldMapTile) local205.method2312(); local212 != null; local212 = (WorldMapTile) local205.method2309()) {
								local191[local64 * 64 + local212.x + (local67 * 64 + local212.z) * anInt5729] = (byte) local212.underlayIdOrColour;
							}
						}
					}
				}
				decodeUnderlayColors(local191, underlayColoursHigh, underlayColoursLow, pixelsPerTileX, pixelsPerTileY);
				for (local67 = 0; local67 < tiles[local58].length; local67++) {
					for (local76 = 0; local76 < tiles[local58][0].length; local76++) {
						@Pc(276) EntityNodeList local276 = tiles[local58][local67][local76];
						if (local276 != null) {
							for (@Pc(283) WorldMapTile local283 = (WorldMapTile) local276.method2312(); local283 != null; local283 = (WorldMapTile) local276.method2309()) {
								local148 = local67 * 64 + local283.x + (local76 * 64 + local283.z) * anInt5729;
								local283.underlayIdOrColour = (underlayColoursHigh[local148] & 0xFF) << 16 | underlayColoursLow[local148] & 0xFFFF;
								if (local283.underlayIdOrColour != 0) {
									local283.underlayIdOrColour |= 0xFF000000;
								}
							}
						}
					}
				}
			}
			decodeUnderlayColors(underlayIds, underlayColoursHigh, underlayColoursLow, pixelsPerTileX, pixelsPerTileY);
			underlayIds = null;
			buildMapCategories();
			return;
		}
	}

	@OriginalMember(owner = "client!hj", name = "a", descriptor = "(Lclient!ya;Lclient!aq;IIII)V")
	private static void positionElement(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) Class4_Sub3 arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		arg1.anInt235 = pixelViewStartX + (arg2 * (arg1.anInt233 - tileViewStartX) >> 16);
		arg1.anInt231 = pixelViewEndY - (arg3 * (arg1.anInt228 - tileViewStartY) >> 16);
	}

	@OriginalMember(owner = "client!hj", name = "a", descriptor = "(Lclient!ya;Lclient!iv;IIII[I[I)V")
	private static void decodeTile(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) Packet packet, @OriginalArg(2) int chunkX, @OriginalArg(3) int chunkZ, @OriginalArg(4) int x, @OriginalArg(5) int z, @OriginalArg(6) int[] underlayPalette, @OriginalArg(7) int[] overlayPalette) {
		@Pc(3) int local3 = packet.method2490();
		if ((local3 & 0x1) == 0) {
			@Pc(15) boolean local15 = (local3 & 0x2) == 0;
			@Pc(21) int local21 = local3 >> 2 & 0x3F;
			if (local21 != 62) {
				if (local21 == 63) {
					local21 = packet.method2490();
				} else if (local15) {
					local21 = underlayPalette[local21];
				} else {
					local21 = overlayPalette[local21];
				}
				if (local15) {
					underlayIds[x + z * anInt5729] = (byte) local21;
					overlayIds[x + z * anInt5729] = 0;
				} else {
					overlayIds[x + z * anInt5729] = (byte) local21;
					overlayShapes[x + z * anInt5729] = 0;
					underlayIds[x + z * anInt5729] = packet.method2542();
				}
			}
			return;
		}
		@Pc(100) int local100 = (local3 >> 1 & 0x3) + 1;
		@Pc(108) boolean local108 = (local3 & 0x8) != 0;
		@Pc(116) boolean local116 = (local3 & 0x10) != 0;
		for (@Pc(118) int local118 = 0; local118 < local100; local118++) {
			@Pc(123) int local123 = packet.method2490();
			@Pc(125) int local125 = 0;
			@Pc(127) int local127 = 0;
			if (local108) {
				local125 = packet.method2490();
				local127 = packet.method2490();
			}
			@Pc(139) int local139 = 0;
			if (local116) {
				local139 = packet.method2490();
			}
			@Pc(215) short[] local215;
			@Pc(218) byte[] local218;
			@Pc(220) int local220;
			if (local118 == 0) {
				underlayIds[x + z * anInt5729] = (byte) local123;
				overlayIds[x + z * anInt5729] = (byte) local125;
				overlayShapes[x + z * anInt5729] = (byte) local127;
				if (local139 == 1) {
					tileLocIds[x + z * anInt5729] = (short) (packet.method2536() + 1);
					tileLocShapes[x + z * anInt5729] = packet.method2542();
				} else if (local139 > 1) {
					tileLocIds[x + z * anInt5729] = -1;
					local215 = new short[local139];
					local218 = new byte[local139];
					for (local220 = 0; local220 < local139; local220++) {
						local215[local220] = (short) packet.method2536();
						local218[local220] = packet.method2542();
					}
					aClass102_31.method2703((long) (x << 16 | z), new MultiLocEntry(local215, local218));
				}
			} else {
				local215 = null;
				local218 = null;
				if (local139 > 0) {
					local215 = new short[local139];
					local218 = new byte[local139];
					for (local220 = 0; local220 < local139; local220++) {
						local215[local220] = (short) packet.method2536();
						local218[local220] = packet.method2542();
					}
				}
				if (tiles[local118 - 1][chunkX - (anInt5732 >> 6)][chunkZ - (anInt5724 >> 6)] == null) {
					tiles[local118 - 1][chunkX - (anInt5732 >> 6)][chunkZ - (anInt5724 >> 6)] = new EntityNodeList();
				}
				@Pc(338) WorldMapTile local338 = new WorldMapTile(x & 0x3F, z & 0x3F, local123, local125, local127, local215, local218);
				tiles[local118 - 1][chunkX - (anInt5732 >> 6)][chunkZ - (anInt5724 >> 6)].offer(local338);
			}
		}
	}

	@OriginalMember(owner = "client!hj", name = "a", descriptor = "(Lclient!ya;)Lclient!pk;")
	public static Class183 positionElements(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit) {
		@Pc(3) int local3 = tileViewEndX - tileViewStartX;
		@Pc(7) int local7 = tileViewEndY - tileViewStartY;
		@Pc(15) int local15 = (anInt5731 - pixelViewStartX << 16) / local3;
		@Pc(23) int local23 = (pixelViewEndY - anInt5727 << 16) / local7;
		return positionElements(toolkit, local15, local23);
	}

	@OriginalMember(owner = "client!hj", name = "b", descriptor = "()V")
	public static void method4502() {
		underlayIds = new byte[anInt5729 * anInt5733];
		overlayIds = new byte[anInt5729 * anInt5733];
		overlayShapes = new byte[anInt5729 * anInt5733];
		tileLocIds = new short[anInt5729 * anInt5733];
		tileLocShapes = new byte[anInt5729 * anInt5733];
		aClass102_31 = new CubeMapSpecularEffect(1024);
		tiles = new EntityNodeList[3][anInt5729 >> 6][anInt5733 >> 6];
		overlayFloorColors = new int[FLOTYPE_LIST.totalFileCount + 1];
	}

	@OriginalMember(owner = "client!hj", name = "c", descriptor = "()V")
	public static void method4503() {
		@Pc(2) int[] local2 = new int[3];
		for (@Pc(4) int local4 = 0; local4 < staticElements.elementCount; local4++) {
			@Pc(32) boolean local32 = area.method935(local2, staticElements.anIntArray397[local4] >> 28 & 0x3, staticElements.anIntArray397[local4] & 0x3FFF, staticElements.anIntArray397[local4] >> 14 & 0x3FFF);
			if (local32) {
				@Pc(42) Class4_Sub3 local42 = new Class4_Sub3(staticElements.anIntArray396[local4]);
				local42.anInt233 = local2[1] - anInt5732;
				local42.anInt228 = local2[2] - anInt5724;
				worldMapCategories.method4137(local42);
			}
		}
	}

	@OriginalMember(owner = "client!hj", name = "a", descriptor = "(Lclient!ya;IIII[S[B)V")
	private static void drawMsiMultiple(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) int drawX, @OriginalArg(2) int drawY, @OriginalArg(3) int width, @OriginalArg(4) int height, @OriginalArg(5) short[] locIds, @OriginalArg(6) byte[] arg6) {
		if (locIds == null) {
			return;
		}
		for (@Pc(4) int local4 = 0; local4 < locIds.length; local4++) {
			@Pc(14) LocType local14 = LOCTYPE_LIST.method5560(locIds[local4] & 0xFFFF);
			@Pc(17) int local17 = local14.anInt5803;
			if (local17 != -1) {
				@Pc(25) MapSpriteType local25 = MAP_SPRITE_TYPE_LIST.method5588(local17);
				@Pc(49) NativeSprite local49 = local25.method937(local14.aBoolean530 ? local14.aBoolean536 : false, toolkit, local14.aBoolean533 ? arg6[local4] >> 6 & 0x3 : 0);
				if (local49 != null) {
					@Pc(58) int local58 = width * local49.scaleWidth() >> 2;
					@Pc(65) int local65 = height * local49.scaleHeight() >> 2;
					if (local25.requiresUpscaling) {
						@Pc(71) int local71 = local14.anInt5816;
						@Pc(74) int local74 = local14.anInt5801;
						if ((arg6[local4] >> 6 & 0x1) == 1) {
							@Pc(85) int local85 = local71;
							local71 = local74;
							local74 = local85;
						}
						local58 = local71 * width;
						local65 = local74 * height;
					}
					if (local58 != 0 && local65 != 0) {
						if (local25.backgroundColor == 0) {
							local49.drawTintedScaled(drawX, drawY + height - local65, local58, local65);
						} else {
							local49.renderImpl(drawX, drawY + height - local65, local58, local65, 0, local25.backgroundColor | 0xFF000000, 1);
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!hj", name = "d", descriptor = "()V")
	private static void buildMapCategories() {
		@Pc(4) int local4;
		@Pc(15) int local15;
		@Pc(49) int local49;
		for (@Pc(1) int local1 = 0; local1 < anInt5729; local1++) {
			for (local4 = 0; local4 < anInt5733; local4++) {
				local15 = tileLocIds[local1 + local4 * anInt5729] & 0xFFFF;
				if (local15 != 0) {
					@Pc(35) int local35;
					if (local15 == 65535) {
						@Pc(31) MultiLocEntry local31 = (MultiLocEntry) aClass102_31.method2700((long) (local1 << 16 | local4));
						if (local31 != null) {
							for (local35 = 0; local35 < local31.locIds.length; local35++) {
								@Pc(46) LocType local46 = LOCTYPE_LIST.method5560(local31.locIds[local35] & 0xFFFF);
								local49 = local46.anInt5807;
								if (local46.alternativeIds != null) {
									local46 = local46.method4584(PLAYER_VAR_PROVIDER);
									if (local46 != null) {
										local49 = local46.anInt5807;
									}
								}
								if (local49 != -1) {
									@Pc(70) Class4_Sub3 local70 = new Class4_Sub3(local49);
									local70.anInt233 = local1;
									local70.anInt228 = local4;
									worldMapCategories.method4137(local70);
								}
							}
						}
					} else {
						@Pc(94) LocType local94 = LOCTYPE_LIST.method5560(local15 - 1);
						local35 = local94.anInt5807;
						if (local94.alternativeIds != null) {
							local94 = local94.method4584(PLAYER_VAR_PROVIDER);
							if (local94 != null) {
								local35 = local94.anInt5807;
							}
						}
						if (local35 != -1) {
							@Pc(118) Class4_Sub3 local118 = new Class4_Sub3(local35);
							local118.anInt233 = local1;
							local118.anInt228 = local4;
							worldMapCategories.method4137(local118);
						}
					}
				}
			}
		}
		for (local4 = 0; local4 < 3; local4++) {
			for (local15 = 0; local15 < tiles[0].length; local15++) {
				for (@Pc(144) int local144 = 0; local144 < tiles[0][0].length; local144++) {
					@Pc(153) EntityNodeList local153 = tiles[local4][local15][local144];
					if (local153 != null) {
						for (@Pc(160) WorldMapTile local160 = (WorldMapTile) local153.method2312(); local160 != null; local160 = (WorldMapTile) local153.method2309()) {
							if (local160.locIds != null) {
								for (local49 = 0; local49 < local160.locIds.length; local49++) {
									@Pc(177) LocType local177 = LOCTYPE_LIST.method5560(local160.locIds[local49] & 0xFFFF);
									@Pc(180) int local180 = local177.anInt5807;
									if (local177.alternativeIds != null) {
										local177 = local177.method4584(PLAYER_VAR_PROVIDER);
										if (local177 != null) {
											local180 = local177.anInt5807;
										}
									}
									if (local180 != -1) {
										@Pc(201) Class4_Sub3 local201 = new Class4_Sub3(local180);
										local201.anInt233 = (local15 + (anInt5732 >> 6)) * 64 + local160.x - anInt5732;
										local201.anInt228 = (local144 + (anInt5724 >> 6)) * 64 + local160.z - anInt5724;
										worldMapCategories.method4137(local201);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!hj", name = "b", descriptor = "(II)Lclient!tn;")
	public static Class229 getRenderedElements(@OriginalArg(0) int coordX, @OriginalArg(1) int coordY) {
		@Pc(3) Class229 local3 = new Class229();
		for (@Pc(8) WorldMapElement local8 = (WorldMapElement) aClass102_30.method2705(); local8 != null; local8 = (WorldMapElement) aClass102_30.method2704()) {
			if (local8.shouldRender && local8.method932(coordY, coordX)) {
				local3.method5088(local8);
			}
		}
		return local3;
	}

	@OriginalMember(owner = "client!hj", name = "b", descriptor = "(Lclient!ya;IIII)Lclient!pk;")
	private static Class183 positionElements(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		for (@Pc(4) Class4_Sub3 local4 = (Class4_Sub3) worldMapCategories.method4140(); local4 != null; local4 = (Class4_Sub3) worldMapCategories.method4144()) {
			positionElement(arg0, local4, arg1, arg2);
		}
		return worldMapCategories;
	}

	@OriginalMember(owner = "client!hj", name = "b", descriptor = "(Lclient!ya;)V")
	public static void drawTiles(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit) {
		@Pc(3) int local3 = tileViewEndX - tileViewStartX;
		@Pc(7) int local7 = tileViewEndY - tileViewStartY;
		@Pc(15) int local15 = (anInt5731 - pixelViewStartX << 16) / local3;
		@Pc(23) int local23 = (pixelViewEndY - anInt5727 << 16) / local7;
		drawTiles(toolkit, local15, local23);
	}

	@OriginalMember(owner = "client!hj", name = "a", descriptor = "(Lclient!ga;III)I")
	private static int overlayColour(@OriginalArg(0) unpack_Sub1 textureSource, @OriginalArg(1) int id, @OriginalArg(2) int hueOffset, @OriginalArg(3) int lightnessOffset) {
		@Pc(4) FloType local4 = FLOTYPE_LIST.method3150(id);
		if (local4 == null) {
			return 0;
		}
		@Pc(11) int local11 = local4.anInt3378;
		if (local11 >= 0 && textureSource.getMetrics(local11).aBoolean639) {
			local11 = -1;
		}
		@Pc(68) int local68;
		@Pc(27) int local27;
		@Pc(33) int local33;
		@Pc(55) int local55;
		if (local4.anInt3372 >= 0) {
			local27 = local4.anInt3372;
			local33 = (local27 & 0x7F) + lightnessOffset;
			if (local33 < 0) {
				local33 = 0;
			} else if (local33 > 127) {
				local33 = 127;
			}
			local55 = (local27 + hueOffset & 0xFC00) + (local27 & 0x380) + local33;
			local68 = PathingEntity.anIntArray423[Class56.method1470(Class4_Sub1_Sub2.method158(local55)) & 0xFFFF] | 0xFF000000;
		} else if (local11 >= 0) {
			local68 = PathingEntity.anIntArray423[Class56.method1470(Class4_Sub1_Sub2.method158(textureSource.getMetrics(local11).colorHsl)) & 0xFFFF] | 0xFF000000;
		} else if (local4.anInt3381 == -1) {
			local68 = 0;
		} else {
			local27 = local4.anInt3381;
			local33 = (local27 & 0x7F) + lightnessOffset;
			if (local33 < 0) {
				local33 = 0;
			} else if (local33 > 127) {
				local33 = 127;
			}
			local55 = (local27 + hueOffset & 0xFC00) + (local27 & 0x380) + local33;
			local68 = PathingEntity.anIntArray423[Class56.method1470(Class4_Sub1_Sub2.method158(local55)) & 0xFFFF] | 0xFF000000;
		}
		return local68;
	}

	@OriginalMember(owner = "client!hj", name = "e", descriptor = "()V")
	public static void freeBuffers() {
		underlayIds = null;
		underlayColoursHigh = null;
		underlayColoursLow = null;
		overlayIds = null;
		overlayShapes = null;
		tileLocIds = null;
		tileLocShapes = null;
		aClass102_31 = null;
		tiles = null;
		overlayFloorColors = null;
	}

	@OriginalMember(owner = "client!hj", name = "b", descriptor = "(I)Lclient!cn;")
	public static WorldMapElement method4511(@OriginalArg(0) int arg0) {
		return (WorldMapElement) aClass102_30.method2700((long) arg0);
	}

	@OriginalMember(owner = "client!hj", name = "a", descriptor = "(Lclient!ga;II)V")
	public static void initOverlayColours(@OriginalArg(0) unpack_Sub1 textureSource, @OriginalArg(1) int hueOffset, @OriginalArg(2) int lightnessOffset) {
		for (@Pc(1) int local1 = 0; local1 < FLOTYPE_LIST.totalFileCount; local1++) {
			overlayFloorColors[local1 + 1] = overlayColour(textureSource, local1, hueOffset, lightnessOffset);
		}
	}

	@OriginalMember(owner = "client!hj", name = "a", descriptor = "(IIIIIIII)V")
	public static void setView(@OriginalArg(0) int x1, @OriginalArg(1) int z2, @OriginalArg(2) int x2, @OriginalArg(3) int z1, @OriginalArg(4) int left, @OriginalArg(5) int top, @OriginalArg(6) int right, @OriginalArg(7) int bottom) {
		tileViewStartX = x1 - anInt5732;
		tileViewEndY = z2 - anInt5724;
		tileViewEndX = x2 - anInt5732;
		tileViewStartY = z1 - anInt5724;
		pixelViewStartX = left;
		anInt5727 = top;
		anInt5731 = right;
		pixelViewEndY = bottom;
	}

	@OriginalMember(owner = "client!hj", name = "a", descriptor = "(Lclient!fs;Lclient!kb;Lclient!qd;Lclient!wf;Lclient!pj;Lclient!wi;Lclient!vs;)V")
	public static void method4514(@OriginalArg(0) Js5ResourceProvider arg0, @OriginalArg(1) FloTypeList arg1, @OriginalArg(2) FluTypeList arg2, @OriginalArg(3) LocTypeList arg3, @OriginalArg(4) MECTypeList arg4, @OriginalArg(5) MapSpriteTypeList arg5, @OriginalArg(6) VarDomain arg6) {
		MAP_AREA_RESOURCE = arg0;
		FLOTYPE_LIST = arg1;
		FLUTYPE_LIST = arg2;
		LOCTYPE_LIST = arg3;
		MECTYPE_LIST = arg4;
		MAP_SPRITE_TYPE_LIST = arg5;
		PLAYER_VAR_PROVIDER = arg6;
		aClass102_30.method2699();
		@Pc(21) int local21 = MAP_AREA_RESOURCE.method2099("details");
		@Pc(26) int[] local26 = MAP_AREA_RESOURCE.method2124(local21);
		for (@Pc(28) int local28 = 0; local28 < local26.length; local28++) {
			@Pc(37) WorldMapElement local37 = Class62.method1609(local21, local26[local28], MAP_AREA_RESOURCE);
			aClass102_30.method2703((long) local37.anInt1012, local37);
		}
		Static347.method4678(true, false);
	}
}
