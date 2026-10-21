package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.entity.EntityNode;
import com.jagex.game.runetek5.entity.PathingEntity;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.client.camera.CameraFollow;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.entity.pathingentity.EasedAngle;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.graphics.JavaBillboardAttributes;
@OriginalClass("client!ku")
public final class Class3_Sub4 extends EntityNode {

	@OriginalMember(owner = "client!ku", name = "j", descriptor = "I")
	public int anInt4019;

	@OriginalMember(owner = "client!ku", name = "l", descriptor = "I")
	public int anInt4021;

	@OriginalMember(owner = "client!ku", name = "n", descriptor = "I")
	public int anInt4022;

	@OriginalMember(owner = "client!ku", name = "p", descriptor = "I")
	public int anInt4023;

	@OriginalMember(owner = "client!ku", name = "q", descriptor = "Lclient!r;")
	public PathingEntity aClass16_Sub1_Sub5_1;

	static {
		new Class83("Now talking in clan channel of player: ", "Clanchat dieses Spieler beigetreten: ", "Vous participez actuellement au canal de clan du joueur : ", "Falando no canal do clã do jogador: ");
	}

@OriginalMember(owner = "client!ku", name = "m", descriptor = "Lclient!sp;")
	public static final ModeWhere aClass219_3 = new ModeWhere("WTQA", 2);
@OriginalMember(owner = "client!ku", name = "o", descriptor = "[Z")
	public static final boolean[] aBooleanArray13 = new boolean[8];
@OriginalMember(owner = "client!ku", name = "r", descriptor = "I")
	public static int anInt4024 = 0;
@OriginalMember(owner = "client!ku", name = "a", descriptor = "(IJ)V")
	public static void method3266(@OriginalArg(1) long arg0) {
		@Pc(10) int local10 = anInt4024 + Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893;
		@Pc(15) int local15 = TextureOpBrightness.anInt5620 + Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892;
		if (CameraFollow.shouldJump(JavaMatrix.anInt6578 - local10, MusicPatch.anInt5636 - local15)) {
			JavaMatrix.anInt6578 = local10;
			MusicPatch.anInt5636 = local15;
		}
		// Snappy follow: stock eased 20/320 of the gap per 20 ms frame, which
		// left the player a tile or more ahead of the camera while walking
		// and took the better part of a second to settle after stopping. The
		// response lives in CameraFollow; this stays a single call per axis.
		JavaMatrix.anInt6578 = CameraFollow.smoothStep(JavaMatrix.anInt6578, local10, arg0);
		if (!TextureOpBrightness.aClass50_Sub1_1.aBoolean303) {
			Class16_Sub2.aFloat142 += EasedAngle.aFloat28 * (float) arg0 / 6.0F;
			Static368.aFloat192 += (float) arg0 * Static380.aFloat195 / 6.0F;
		}
		MusicPatch.anInt5636 = CameraFollow.smoothStep(MusicPatch.anInt5636, local15, arg0);
		JavaBillboardAttributes.method3855();
	}
}
