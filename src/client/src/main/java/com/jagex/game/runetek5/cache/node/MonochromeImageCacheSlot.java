package com.jagex.game.runetek5.cache.node;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

import com.jagex.game.runetek5.HelveticaFont;

import com.jagex.game.runetek5.cache.node.*;
@OriginalClass("client!cr")
public final class MonochromeImageCacheSlot extends Country {

	@OriginalMember(owner = "client!cr", name = "m", descriptor = "I")
	public final int anInt1036;

	@OriginalMember(owner = "client!cr", name = "o", descriptor = "I")
	public final int anInt1037;

	@OriginalMember(owner = "client!cr", name = "<init>", descriptor = "(II)V")
	public MonochromeImageCacheSlot(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		this.anInt1036 = arg0;
		this.anInt1037 = arg1;
	}

@OriginalMember(owner = "client!cr", name = "n", descriptor = "Lclient!sb;")
	public static HelveticaFont aClass212_1;
@OriginalMember(owner = "client!cr", name = "l", descriptor = "Z")
	public static boolean aBoolean98 = false;
}
