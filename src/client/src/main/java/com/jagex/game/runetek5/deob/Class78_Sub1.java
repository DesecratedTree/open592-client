package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.graphics.font.FontMetrics;
@OriginalClass("client!kf")
public final class Class78_Sub1 extends Class78 {

	@OriginalMember(owner = "client!kf", name = "h", descriptor = "I")
	public int anInt3881;

	@OriginalMember(owner = "client!kf", name = "i", descriptor = "Ljava/lang/String;")
	public String aString37;

	@OriginalMember(owner = "client!kf", name = "l", descriptor = "Ljava/lang/String;")
	public String aString38;

	@OriginalMember(owner = "client!kf", name = "r", descriptor = "I")
	public int anInt3887 = -1;

	@OriginalMember(owner = "client!kf", name = "c", descriptor = "(I)Lclient!ak;")
	public Class10 method3175() {
		return FontMetrics.aClass10Array1[super.anInt3877];
	}

@OriginalMember(owner = "client!kf", name = "m", descriptor = "F")
	public static float aFloat126;
@OriginalMember(owner = "client!kf", name = "n", descriptor = "I")
	public static int anInt3883;
@OriginalMember(owner = "client!kf", name = "j", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_40 = new Class215(35, 3);
@OriginalMember(owner = "client!kf", name = "d", descriptor = "(I)I")
	public static int method3176() {
		return 16;
	}
@OriginalMember(owner = "client!kf", name = "a", descriptor = "(III)V")
	public static void method3177(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		@Pc(10) CubeMapSpecularEffect local10 = new CubeMapSpecularEffect(16);
		for (@Pc(20) Class4_Sub18 local20 = (Class4_Sub18) LocTypeList.aClass102_40.method2705(); local20 != null; local20 = (Class4_Sub18) LocTypeList.aClass102_40.method2704()) {
			local20.method5684();
			@Pc(31) int local31 = (int) (local20.aLong224 >> 28);
			@Pc(41) int local41 = (int) (local20.aLong224 >> 14 & 0x3FFFL) - arg1;
			@Pc(49) int local49 = (int) (local20.aLong224 & 0x3FFFL) - arg0;
			if (local49 >= 0 && local41 >= 0 && local49 < GLBillowTextureData.anInt5666 && Static283.anInt5187 > local41) {
				local10.method2703((long) (local31 << 28 | local41 << 14 | local49), local20);
			}
		}
		LocTypeList.aClass102_40 = local10;
	}
}
