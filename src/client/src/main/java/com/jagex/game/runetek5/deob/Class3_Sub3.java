package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.entity.BoundingBoxPolygon;
import com.jagex.game.runetek5.entity.EntityNode;

import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.cache.node.lru.ClientScript;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchInfo;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
@OriginalClass("client!fk")
public final class Class3_Sub3 extends EntityNode {

	@OriginalMember(owner = "client!fk", name = "l", descriptor = "I")
	public int anInt2381;

	@OriginalMember(owner = "client!fk", name = "n", descriptor = "I")
	public int anInt2383;

	@OriginalMember(owner = "client!fk", name = "o", descriptor = "I")
	public int anInt2384;

	@OriginalMember(owner = "client!fk", name = "r", descriptor = "Lclient!fi;")
	public Class16 aClass16_1;

	@OriginalMember(owner = "client!fk", name = "s", descriptor = "[Lclient!mi;")
	public BoundingBoxPolygon[] aClass3_Sub5Array1;

	@OriginalMember(owner = "client!fk", name = "a", descriptor = "(IIILclient!ya;)Z")
	public boolean method2024(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) com.jagex.game.runetek5.graphics.Toolkit arg2) {
		if (this.aClass3_Sub5Array1 != null) {
			for (@Pc(14) int local14 = 0; local14 < this.aClass3_Sub5Array1.length; local14++) {
				if (this.aClass3_Sub5Array1[local14].hovered(arg0, arg1) && this.aClass16_1.method5341(arg1, arg2, arg0)) {
					return true;
				}
			}
		}
		return false;
	}

@OriginalMember(owner = "client!fk", name = "j", descriptor = "I")
	public static int anInt2379;
@OriginalMember(owner = "client!fk", name = "q", descriptor = "Lclient!c;")
	public static Ground aClass31_1;
@OriginalMember(owner = "client!fk", name = "u", descriptor = "I")
	public static int anInt2386;
@OriginalMember(owner = "client!fk", name = "v", descriptor = "I")
	public static int anInt2387;
@OriginalMember(owner = "client!fk", name = "t", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_28 = new Class215(40, 3);
@OriginalMember(owner = "client!fk", name = "a", descriptor = "(Lclient!lh;IIIB)V")
	public static void method2022(@OriginalArg(0) PlayerEntity arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2) {
		@Pc(14) int local14 = arg0.movementBaseX[0];
		@Pc(19) int local19 = arg0.movementBaseY[0];
		if (local14 < 0 || GLBillowTextureData.anInt5666 <= local14 || local19 < 0 || Static283.anInt5187 <= local19 || (arg1 < 0 || arg1 >= GLBillowTextureData.anInt5666 || arg2 < 0 || Static283.anInt5187 <= arg2)) {
			return;
		}
		@Pc(77) int local77 = ClientScript.method49(PlayerVarDomain.aClass213Array1[arg0.aByte82], local14, arg1, 0, arg2, arg0.getSize(), 0, Class4_Sub7.anIntArray36, 0, true, ParticleBinaryTree.anIntArray498, local19, 0, -4);
		if (local77 >= 1 && local77 <= 3) {
			for (@Pc(87) int local87 = 0; local87 < local77 - 1; local87++) {
				arg0.method3427(Class4_Sub7.anIntArray36[local87], ParticleBinaryTree.anIntArray498[local87], (byte) 2);
			}
		}
	}
@OriginalMember(owner = "client!fk", name = "c", descriptor = "(I)V")
	public static void method2023() {
		MusicPatchInfo.aClass183_48 = new Class183();
	}
}
