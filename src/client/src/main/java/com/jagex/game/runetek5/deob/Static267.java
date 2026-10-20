package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.graphics.shader.Texture3DShaderGL;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;

import com.jagex.game.runetek5.config.cursor.CursorType;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
public final class Static267 {

	@OriginalMember(owner = "client!nl", name = "c", descriptor = "Lclient!wf;")
	public static LocTypeList aClass262_2;

	@OriginalMember(owner = "client!nl", name = "a", descriptor = "[I")
	public static final int[] anIntArray321 = new int[1];

	@OriginalMember(owner = "client!nl", name = "e", descriptor = "[Lclient!lh;")
	public static final PlayerEntity[] aClass16_Sub1_Sub5_Sub1Array1 = new PlayerEntity[2048];

	@OriginalMember(owner = "client!nl", name = "a", descriptor = "(BLclient!fd;)V")
	public static void method3829(@OriginalArg(1) GlToolkit arg0) {
		@Pc(12) byte[] local12;
		if (Class16.anObject33 == null) {
			@Pc(5) Class48_Sub1_Sub1 local5 = new Class48_Sub1_Sub1();
			local12 = local5.method3759();
			Class16.anObject33 = SpotAnimationTypeList.method4416(local12);
		}
		if (CursorType.anObject13 == null) {
			@Pc(32) GLBillowTextureData local32 = new GLBillowTextureData();
			local12 = local32.method4412();
			CursorType.anObject13 = SpotAnimationTypeList.method4416(local12);
		}
		@Pc(47) Texture3DShaderGL local47 = arg0.texture3dShader;
		if (local47.createShader() && Class4_Sub6_Sub15.anObject19 == null) {
			local12 = Class4_Sub6_Sub24.method3710(0.6F, 4.0F, 16.0F, 0.5F, new Class79_Sub1(419684), 4.0F);
			Class4_Sub6_Sub15.anObject19 = SpotAnimationTypeList.method4416(local12);
		}
	}
}
