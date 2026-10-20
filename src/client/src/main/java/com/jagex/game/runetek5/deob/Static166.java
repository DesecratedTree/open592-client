package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.HashTable;
import com.jagex.game.runetek5.cache.node.lru.ClientScript;
import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.deob.Resampler;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class269;
import com.jagex.game.runetek5.deob.Class223;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.deob.Class53;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.toolkit.opengl.GlRenderList;
import com.jagex.game.runetek5.deob.Class208;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
public final class Static166 {

	@OriginalMember(owner = "client!ik", name = "b", descriptor = "[Lclient!uu;")
	public static com.jagex.game.runetek5.config.rsinterface.Component[] aClass247Array1;

	@OriginalMember(owner = "client!ik", name = "a", descriptor = "[B")
	public static final byte[] aByteArray37 = new byte[520];

	@OriginalMember(owner = "client!ik", name = "d", descriptor = "Lclient!hu;")
	public static HashTable aClass100_1 = new HashTable(8);

	@OriginalMember(owner = "client!ik", name = "f", descriptor = "[S")
	public static short[] aShortArray97 = new short[256];

	@OriginalMember(owner = "client!ik", name = "a", descriptor = "(IIIIZIIII)Z")
	public static boolean method2734(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) boolean arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7) {
		@Pc(8) int local8 = Static1.aClass16_Sub1_Sub5_Sub1_1.movementBaseX[0];
		@Pc(13) int local13 = Static1.aClass16_Sub1_Sub5_Sub1_1.movementBaseY[0];
		if (local8 < 0 || GLBillowTextureData.anInt5666 <= local8 || local13 < 0 || local13 >= Static283.anInt5187) {
			return false;
		} else if (arg2 >= 0 && arg2 < GLBillowTextureData.anInt5666 && arg1 >= 0 && arg1 < Static283.anInt5187) {
			@Pc(82) int local82 = ClientScript.method49(PlayerVarDomain.aClass213Array1[Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82], local8, arg2, arg5, arg1, Static1.aClass16_Sub1_Sub5_Sub1_1.getSize(), arg6, Class4_Sub7.anIntArray36, arg0, arg4, ParticleBinaryTree.anIntArray498, local13, arg3, arg7);
			if (local82 < 1) {
				return false;
			}
			Class30.anInt771 = ParticleBinaryTree.anIntArray498[local82 - 1];
			GlRenderList.anInt1094 = Class4_Sub7.anIntArray36[local82 - 1];
			Class208.aBoolean556 = false;
			Class53.method1365();
			return true;
		} else {
			return false;
		}
	}

	@OriginalMember(owner = "client!ik", name = "a", descriptor = "(IIIIBII)V")
	public static void method2735(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5) {
		if (arg4 >= ItemType.anInt6186 && Class36.anInt2656 >= arg0 && Model.anInt6287 <= arg5 && GlCubeTexture.anInt3256 >= arg3) {
			if (arg2 == 1) {
				Class269.method5663(arg3, arg0, arg5, arg1, arg4);
			} else {
				Class64_Sub1.method1653(arg3, arg4, arg1, arg0, arg2, arg5);
			}
		} else if (arg2 == 1) {
			Class223.method5001(arg0, arg3, arg1, arg5, arg4);
		} else {
			Resampler.method5548(arg5, arg1, arg4, arg0, arg2, arg3);
		}
	}
}
