package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;


import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.network.packet.ServerProt;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!ws")
public class Class95 {

@OriginalMember(owner = "client!ws", name = "a", descriptor = "Lclient!jc;")
	public static ServerProt aClass117_9;
@OriginalMember(owner = "client!ws", name = "b", descriptor = "(Z)V")
	public static void method5011() {
		OggOtherStream.aClass68_38.method1777();
	}
@OriginalMember(owner = "client!ws", name = "a", descriptor = "(IIBII)V")
	public static void method5012(@OriginalArg(1) int arg0, @OriginalArg(3) int arg1) {
		Class36.anInt2656 = arg0;
		GlCubeTexture.anInt3256 = arg1;
		ItemType.anInt6186 = 0;
		Model.anInt6287 = 0;
	}
}
