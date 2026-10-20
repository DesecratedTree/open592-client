package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.deob.SpotAnimation;
import com.jagex.game.runetek5.js5.request.Js5ResourceRequest;
import com.jagex.game.runetek5.sound.player.PcmPlayer;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.cache.node.impl.SpriteRenderNode;
public final class Static277 {

	@OriginalMember(owner = "client!oa", name = "e", descriptor = "[I")
	public static int[] anIntArray336;

	@OriginalMember(owner = "client!oa", name = "f", descriptor = "I")
	public static int anInt5150;

	@OriginalMember(owner = "client!oa", name = "d", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_231 = new Class22(98, -1);

	@OriginalMember(owner = "client!oa", name = "h", descriptor = "[I")
	public static final int[] anIntArray337 = new int[32];

	@OriginalMember(owner = "client!oa", name = "a", descriptor = "(II)[B")
	public static synchronized byte[] method3931(@OriginalArg(1) int arg0) {
		@Pc(21) byte[] local21;
		if (arg0 == 100 && NPCType.chatMessageHashIndex > 0) {
			local21 = Class99_Sub1.aByteArrayArray14[--NPCType.chatMessageHashIndex];
			Class99_Sub1.aByteArrayArray14[NPCType.chatMessageHashIndex] = null;
			return local21;
		} else if (arg0 == 5000 && Class230.anInt6496 > 0) {
			local21 = SpriteRenderNode.aByteArrayArray21[--Class230.anInt6496];
			SpriteRenderNode.aByteArrayArray21[Class230.anInt6496] = null;
			return local21;
		} else if (arg0 == 30000 && Js5MasterIndex.anInt6108 > 0) {
			local21 = BasType.aByteArrayArray17[--Js5MasterIndex.anInt6108];
			BasType.aByteArrayArray17[Js5MasterIndex.anInt6108] = null;
			return local21;
		} else {
			return new byte[arg0];
		}
	}

	@OriginalMember(owner = "client!oa", name = "a", descriptor = "(I)V")
	public static void method3932() {
		PcmPlayer.aClass57_11 = null;
		Class99_Sub1.anInt3967 = -1;
	}

	@OriginalMember(owner = "client!oa", name = "a", descriptor = "(ZI)V")
	public static void method3934(@OriginalArg(1) int arg0) {
		MECType.method3775();
		@Pc(15) int local15 = Js5ResourceRequest.aClass223_1.method5004(arg0).anInt3365;
		if (local15 == 0) {
			return;
		}
		@Pc(26) int local26 = Class4_Sub6_Sub24.aClass114_1.activeVars[arg0];
		if (local15 == 5) {
			SpotAnimation.anInt1361 = local26;
		}
		if (local15 == 6) {
			Game.anInt3572 = local26;
		}
	}
}
