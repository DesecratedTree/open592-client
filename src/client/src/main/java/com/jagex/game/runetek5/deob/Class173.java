package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;


import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.network.packet.ServerProt;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationType;
@OriginalClass("client!ol")
public final class Class173 {

	static {
		new Class83("Close", "Bitte schließ die momentan geöffnete Benutzeroberfläche,", "Fermez l'interface que vous avez ouverte avant d'utiliser le bouton « Signaler un abus ».", "Feche a interface aberta antes de usar o recurso \"Denunciar abuso\".");
	}

@OriginalMember(owner = "client!ol", name = "a", descriptor = "(II)Lclient!jc;")
	public static ServerProt method3983(@OriginalArg(1) int arg0) {
		@Pc(8) ServerProt[] local8 = SpotAnimationType.method3834();
		for (@Pc(10) int local10 = 0; local10 < local8.length; local10++) {
			@Pc(16) ServerProt local16 = local8[local10];
			if (local16.anInt3461 == arg0) {
				return local16;
			}
		}
		return null;
	}
}
