package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.graphics.map.worldmap.WorldMapElement;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;

public final class Static107 {

	@OriginalMember(owner = "client!fg", name = "c", descriptor = "[Lclient!l;")
	public static NativeSprite[] aClass57Array6;

	@OriginalMember(owner = "client!fg", name = "a", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_96 = new Class22(28, 3);

	@OriginalMember(owner = "client!fg", name = "d", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_40 = new Class83("Allocating memory", "Speicher wird zugewiesen.", "Mémoire en cours d'attribution", "Alocando memória");

	@OriginalMember(owner = "client!fg", name = "e", descriptor = "I")
	public static int anInt2335 = 1;

	@OriginalMember(owner = "client!fg", name = "a", descriptor = "(B)Lclient!cn;")
	public static WorldMapElement method1990() {
		return Static148.area;
	}
}
