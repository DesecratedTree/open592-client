package com.jagex.game.runetek5.cache.node;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.graphics.loginscreen.DrawableItem;

@OriginalClass("client!ks")
public final class SoftwareObjectNode extends Country {

	@OriginalMember(owner = "client!ks", name = "l", descriptor = "Lclient!d;")
	public DrawableItem anInterface4_1;
}
