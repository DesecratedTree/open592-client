package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.graphics.gl.GlToolkit;

import java.awt.Frame;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.scene.CollisionMap;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.sign.SignedResource;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class48_Sub1_Sub1;
import com.jagex.game.runetek5.graphics.JavaBillboardFace;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.graphics.model.billboard.BillboardType;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.cache.node.lru.ProjectileAnimationNode;
@OriginalClass("client!wl")
public final class Class267 {

	@OriginalMember(owner = "client!wl", name = "e", descriptor = "Lclient!fd;")
	private final GlToolkit aClass19_Sub2_43;

	@OriginalMember(owner = "client!wl", name = "f", descriptor = "J")
	public final long aLong220;

	@OriginalMember(owner = "client!wl", name = "<init>", descriptor = "(Lclient!fd;JI)V")
	public Class267(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) long arg1, @OriginalArg(2) int arg2) {
		this.aClass19_Sub2_43 = arg0;
		this.aLong220 = arg1;
	}

	@OriginalMember(owner = "client!wl", name = "finalize", descriptor = "()V")
	@Override
	public void finalize() throws Throwable {
		this.aClass19_Sub2_43.method1973(this.aLong220);
		super.finalize();
	}

@OriginalMember(owner = "client!wl", name = "h", descriptor = "B")
	public static byte aByte103;
@OriginalMember(owner = "client!wl", name = "a", descriptor = "[I")
	public static final int[] anIntArray552 = new int[1000];
@OriginalMember(owner = "client!wl", name = "c", descriptor = "Lclient!ho;")
	public static final QuickChatValueType aClass98_18 = new QuickChatValueType(1, 2, 2, 0);
@OriginalMember(owner = "client!wl", name = "a", descriptor = "(Lclient!et;BLjava/awt/Frame;)V")
	public static void method5620(@OriginalArg(0) SignLink arg0, @OriginalArg(2) Frame arg1) {
		while (true) {
			@Pc(6) SignedResource local6 = arg0.exitFullscreen(arg1);
			while (local6.anInt5760 == 0) {
				Class259.method5503(10L);
			}
			if (local6.anInt5760 == 1) {
				arg1.setVisible(false);
				arg1.dispose();
				return;
			}
			Class259.method5503(100L);
		}
	}
@OriginalMember(owner = "client!wl", name = "a", descriptor = "(IIIIIII)V")
	public static void method5621(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5) {
		ProjectileAnimationNode.anInt1909 = arg3;
		Class21.anInt460 = arg0;
		BillboardType.anInt3534 = arg4;
		Class48_Sub1_Sub1.anInt4881 = arg1;
		Ground.anInt7321 = arg2;
		JavaBillboardFace.anInt3926 = arg5;
	}
@OriginalMember(owner = "client!wl", name = "a", descriptor = "(BII)Lclient!sd;")
	public static CollisionMap method5622(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		@Pc(7) CollisionMap local7 = new CollisionMap();
		local7.anInt6230 = -1;
		local7.anInt6225 = arg0 + 1 + 5;
		local7.anInt6231 = arg1 + 1 + 5;
		local7.anInt6235 = -1;
		local7.collisionFlags = new int[local7.anInt6231][local7.anInt6225];
		local7.method4851();
		return local7;
	}
}
