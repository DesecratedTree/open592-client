package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;


import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.ChatFilter;
import com.jagex.game.runetek5.graphics.texture.sampler.GlRectangleTexture;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
@OriginalClass("client!dq")
public final class Class53 {

	static {
		new Class83("The channel you tried to join does not exist.", "Der von dir gewünschte Chatraum existiert nicht.", "Le canal que vous essayez de rejoindre n'existe pas.", "O canal que você tentou acessar não existe.");
	}

@OriginalMember(owner = "client!dq", name = "b", descriptor = "I")
	public static int anInt1532;
@OriginalMember(owner = "client!dq", name = "a", descriptor = "Lclient!ns;")
	public static final GlRectangleTexture aClass4_Sub33_1 = new GlRectangleTexture(0, -1);
@OriginalMember(owner = "client!dq", name = "c", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_73 = new Class22(71, 6);
@OriginalMember(owner = "client!dq", name = "d", descriptor = "Lclient!id;")
	public static final ChatFilter aClass104_1 = new ChatFilter(1);
@OriginalMember(owner = "client!dq", name = "a", descriptor = "(B)V")
	public static void method1365() {
		@Pc(8) Class4_Sub1_Sub11 local8 = NPCTypeList.method5222(0, 15);
		local8.method2608();
	}
}
