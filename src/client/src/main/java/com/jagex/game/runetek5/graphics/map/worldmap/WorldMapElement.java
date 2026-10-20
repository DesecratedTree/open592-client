package com.jagex.game.runetek5.graphics.map.worldmap;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class4_Sub41;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.graphics.map.worldmap.*;
@OriginalClass("client!cn")
public final class WorldMapElement extends CacheableNode {

	@OriginalMember(owner = "client!cn", name = "K", descriptor = "I")
	public int anInt1018 = 12800;

	@OriginalMember(owner = "client!cn", name = "N", descriptor = "I")
	public int anInt1019 = 0;

	@OriginalMember(owner = "client!cn", name = "I", descriptor = "I")
	public int anInt1016 = -1;

	@OriginalMember(owner = "client!cn", name = "B", descriptor = "I")
	public int anInt1010 = 12800;

	@OriginalMember(owner = "client!cn", name = "G", descriptor = "I")
	public int anInt1014 = 0;

	@OriginalMember(owner = "client!cn", name = "J", descriptor = "I")
	public int color = -1;

	@OriginalMember(owner = "client!cn", name = "y", descriptor = "Z")
	public boolean shouldRender = true;

	@OriginalMember(owner = "client!cn", name = "E", descriptor = "I")
	public final int anInt1012;

	@OriginalMember(owner = "client!cn", name = "M", descriptor = "Ljava/lang/String;")
	public final String aString15;

	@OriginalMember(owner = "client!cn", name = "O", descriptor = "Ljava/lang/String;")
	public final String filenamePrefix;

	@OriginalMember(owner = "client!cn", name = "S", descriptor = "I")
	public final int anInt1023;

	@OriginalMember(owner = "client!cn", name = "L", descriptor = "Lclient!pk;")
	public final Class183 worldMapPlacements;

	@OriginalMember(owner = "client!cn", name = "<init>", descriptor = "(ILjava/lang/String;Ljava/lang/String;IIZII)V")
	public WorldMapElement(@OriginalArg(0) int arg0, @OriginalArg(1) String string, @OriginalArg(2) String string2, @OriginalArg(3) int arg3, @OriginalArg(4) int color, @OriginalArg(5) boolean shouldRender, @OriginalArg(6) int defaultZoomLevel, @OriginalArg(7) int mapsizeId) {
		this.color = color;
		this.anInt1016 = defaultZoomLevel;
		this.anInt1012 = arg0;
		this.shouldRender = shouldRender;
		this.aString15 = string2;
		this.filenamePrefix = string;
		this.anInt1023 = arg3;
		if (this.anInt1016 == 255) {
			this.anInt1016 = 0;
		}
		this.worldMapPlacements = new Class183();
	}

	@OriginalMember(owner = "client!cn", name = "a", descriptor = "(I[IBI)Z")
	public boolean method930(@OriginalArg(0) int arg0, @OriginalArg(1) int[] arg1, @OriginalArg(3) int arg2) {
		for (@Pc(11) Class4_Sub41 local11 = (Class4_Sub41) this.worldMapPlacements.method4140(); local11 != null; local11 = (Class4_Sub41) this.worldMapPlacements.method4144()) {
			if (local11.method5239(arg2, arg0)) {
				local11.method5237(arg2, arg0, arg1);
				return true;
			}
		}
		return false;
	}

	@OriginalMember(owner = "client!cn", name = "a", descriptor = "(I[III)Z")
	public boolean method931(@OriginalArg(1) int[] arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		for (@Pc(11) Class4_Sub41 local11 = (Class4_Sub41) this.worldMapPlacements.method4140(); local11 != null; local11 = (Class4_Sub41) this.worldMapPlacements.method4144()) {
			if (local11.method5238(arg1, arg2)) {
				local11.method5236(arg0, arg2, arg1);
				return true;
			}
		}
		return false;
	}

	@OriginalMember(owner = "client!cn", name = "a", descriptor = "(III)Z")
	public boolean method932(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		for (@Pc(22) Class4_Sub41 local22 = (Class4_Sub41) this.worldMapPlacements.method4140(); local22 != null; local22 = (Class4_Sub41) this.worldMapPlacements.method4144()) {
			if (local22.method5238(arg0, arg1)) {
				return true;
			}
		}
		return false;
	}

	@OriginalMember(owner = "client!cn", name = "e", descriptor = "(B)V")
	public void method933() {
		this.anInt1018 = 12800;
		this.anInt1014 = 0;
		this.anInt1019 = 0;
		this.anInt1010 = 12800;
		for (@Pc(32) Class4_Sub41 local32 = (Class4_Sub41) this.worldMapPlacements.method4140(); local32 != null; local32 = (Class4_Sub41) this.worldMapPlacements.method4144()) {
			if (local32.anInt6701 < this.anInt1010) {
				this.anInt1010 = local32.anInt6701;
			}
			if (local32.anInt6690 < this.anInt1018) {
				this.anInt1018 = local32.anInt6690;
			}
			if (this.anInt1019 < local32.anInt6693) {
				this.anInt1019 = local32.anInt6693;
			}
			if (this.anInt1014 < local32.anInt6688) {
				this.anInt1014 = local32.anInt6688;
			}
		}
	}

	@OriginalMember(owner = "client!cn", name = "a", descriptor = "(Z[IIII)Z")
	public boolean method935(@OriginalArg(1) int[] arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		for (@Pc(17) Class4_Sub41 local17 = (Class4_Sub41) this.worldMapPlacements.method4140(); local17 != null; local17 = (Class4_Sub41) this.worldMapPlacements.method4144()) {
			if (local17.method5240(arg2, arg3, arg1)) {
				local17.method5236(arg0, arg3, arg2);
				return true;
			}
		}
		return false;
	}

@OriginalMember(owner = "client!cn", name = "C", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_16;
@OriginalMember(owner = "client!cn", name = "P", descriptor = "I")
	public static int anInt1020;
@OriginalMember(owner = "client!cn", name = "z", descriptor = "[C")
	public static final char[] aCharArray2 = new char[] { ' ', ' ', '_', '-', 'à', 'á', 'â', 'ä', 'ã', 'À', 'Á', 'Â', 'Ä', 'Ã', 'è', 'é', 'ê', 'ë', 'È', 'É', 'Ê', 'Ë', 'í', 'î', 'ï', 'Í', 'Î', 'Ï', 'ò', 'ó', 'ô', 'ö', 'õ', 'Ò', 'Ó', 'Ô', 'Ö', 'Õ', 'ù', 'ú', 'û', 'ü', 'Ù', 'Ú', 'Û', 'Ü', 'ç', 'Ç', 'ÿ', 'Ÿ', 'ñ', 'Ñ', 'ß' };
@OriginalMember(owner = "client!cn", name = "a", descriptor = "(IIB)Z")
	public static boolean method934(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		return (arg0 & 0x100) != 0;
	}
}
