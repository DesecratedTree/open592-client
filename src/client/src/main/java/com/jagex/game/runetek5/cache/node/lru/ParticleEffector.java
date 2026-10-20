package com.jagex.game.runetek5.cache.node.lru;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.config.particle.ParticleType;
import com.jagex.game.runetek5.deob.p;
import com.jagex.game.runetek5.entity.ParticleSystem;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;

import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.cache.node.lru.*;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageTypeList;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class16_Sub5_Sub2;
import com.jagex.game.runetek5.cache.node.lru.ProjectileAnimationNode;
import com.jagex.game.runetek5.config.idktype.IDKTypeList;
import com.jagex.game.runetek5.deob.Huffman;
@OriginalClass("client!ij")
public final class ParticleEffector extends CacheableNode {

	@OriginalMember(owner = "client!ij", name = "A", descriptor = "I")
	public int anInt3218;

	@OriginalMember(owner = "client!ij", name = "C", descriptor = "I")
	public int anInt3220;

	@OriginalMember(owner = "client!ij", name = "D", descriptor = "I")
	public int anInt3221;

	@OriginalMember(owner = "client!ij", name = "E", descriptor = "I")
	public int verticeX;

	@OriginalMember(owner = "client!ij", name = "J", descriptor = "I")
	public int anInt3225;

	@OriginalMember(owner = "client!ij", name = "y", descriptor = "Lclient!pf;")
	public final EffectiveVertex model;

	@OriginalMember(owner = "client!ij", name = "z", descriptor = "Lclient!ah;")
	public final ParticleType type;

	@OriginalMember(owner = "client!ij", name = "<init>", descriptor = "(Lclient!pf;Lclient!ao;)V")
	public ParticleEffector(@OriginalArg(0) EffectiveVertex modelParticleEffector, @OriginalArg(1) ParticleSystem particleSystem) {
		this.model = modelParticleEffector;
		this.type = this.model.method4106();
		this.update();
	}

	@OriginalMember(owner = "client!ij", name = "a", descriptor = "(I)V")
	public void update() {
		this.verticeX = this.model.anInt5340;
		this.anInt3220 = this.model.anInt5342;
		this.anInt3225 = this.model.anInt5347;
		if (this.model.aClass31_4 != null) {
			this.model.aClass31_4.p(this.type.anInt128, this.type.anInt125, this.type.anInt132, Class16_Sub5_Sub2.anIntArray414);
		}
		this.anInt3221 = Class16_Sub5_Sub2.anIntArray414[2];
		this.anInt3218 = Class16_Sub5_Sub2.anIntArray414[0];
	}

@OriginalMember(owner = "client!ij", name = "H", descriptor = "[I")
	public static int[] anIntArray209;
@OriginalMember(owner = "client!ij", name = "I", descriptor = "[I")
	public static int[] WALL_ROTATION_FLAGS;
@OriginalMember(owner = "client!ij", name = "a", descriptor = "(Lclient!fs;I)V")
	public static void method2730(@OriginalArg(0) Js5ResourceProvider arg0) {
		Class183.aClass76_65 = arg0;
	}
@OriginalMember(owner = "client!ij", name = "e", descriptor = "(B)V")
	public static void method2731() {
		if (ProjectileAnimationNode.anInt1910 > 0) {
			BasType.method3639();
		} else {
			IDKTypeList.aClass11_6 = QuickchatMessageTypeList.aClass11_5;
			QuickchatMessageTypeList.aClass11_5 = null;
			Huffman.method2932(40);
		}
	}
}
