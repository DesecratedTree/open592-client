package com.jagex.game.runetek5.graphics.model.particle;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.entity.EntityNode;

import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
import com.jagex.game.runetek5.deob.Class83;

import com.jagex.game.runetek5.graphics.model.particle.*;
@OriginalClass("client!ab")
public class ParticleNode extends EntityNode {

	@OriginalMember(owner = "client!ab", name = "m", descriptor = "Lclient!ab;")
	public ParticleNode prev2;

	@OriginalMember(owner = "client!ab", name = "n", descriptor = "Lclient!ab;")
	public ParticleNode next2;

	@OriginalMember(owner = "client!ab", name = "c", descriptor = "(I)V")
	public final void unlink2() {
		if (this.prev2 != null) {
			this.prev2.next2 = this.next2;
			this.next2.prev2 = this.prev2;
			this.next2 = null;
			this.prev2 = null;
		}
	}

@OriginalMember(owner = "client!ab", name = "q", descriptor = "I")
	public static int anInt4067;
@OriginalMember(owner = "client!ab", name = "k", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_185 = new Class22(5, 8);
@OriginalMember(owner = "client!ab", name = "l", descriptor = "[I")
	public static final int[] anIntArray267 = new int[100];
@OriginalMember(owner = "client!ab", name = "o", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_88 = new Class83("Loaded wordpack", "Wordpack geladen.", "Module texte chargé", "Pacote de palavras carregado");
@OriginalMember(owner = "client!ab", name = "p", descriptor = "Lclient!cw;")
	public static final Class4_Sub12_Sub1 aClass4_Sub12_Sub1_5 = new Class4_Sub12_Sub1(5000);
}
