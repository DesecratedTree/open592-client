package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.SoftwareObjectNode;
import com.jagex.game.runetek5.graphics.loginscreen.DrawableItem;

public final class Static404 {

	@OriginalMember(owner = "client!uj", name = "a", descriptor = "Lclient!pk;")
	private static final Class183 aClass183_46 = new Class183();

	@OriginalMember(owner = "client!uj", name = "a", descriptor = "(Z)V")
	public static void method5219() {
		while (true) {
			@Pc(8) SoftwareObjectNode local8 = (SoftwareObjectNode) aClass183_46.method4136();
			if (local8 == null) {
				return;
			}
			local8.anInterface4_1.EA();
			local8.method5684();
		}
	}

	@OriginalMember(owner = "client!uj", name = "a", descriptor = "(BLclient!d;)V")
	public static synchronized void method5220(@OriginalArg(1) DrawableItem arg0) {
		@Pc(3) SoftwareObjectNode local3 = new SoftwareObjectNode();
		local3.anInterface4_1 = arg0;
		aClass183_46.method4137(local3);
	}
}
