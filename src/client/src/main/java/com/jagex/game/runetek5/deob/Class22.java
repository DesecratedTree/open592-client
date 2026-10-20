package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.deob.Class16_Sub2_Sub3;
import com.jagex.game.runetek5.LocEntity;
import com.jagex.game.runetek5.deob.Class189;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageTypeList;
import com.jagex.game.runetek5.entity.pathingentity.EasedAngle;
import com.jagex.game.runetek5.deob.Class4_Sub36;
import com.jagex.game.runetek5.deob.WaterfallEffect;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.TileMessage;
import com.jagex.game.runetek5.toolkit.opengl.effect.ReflectiveRipplingWaterEffect;
import com.jagex.game.runetek5.LegacyMouseManager;
@OriginalClass("client!bg")
public final class Class22 {

	@OriginalMember(owner = "client!bg", name = "g", descriptor = "I")
	public final int anInt468;

	@OriginalMember(owner = "client!bg", name = "f", descriptor = "I")
	private final int anInt467;

	@OriginalMember(owner = "client!bg", name = "<init>", descriptor = "(II)V")
	public Class22(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		this.anInt468 = arg1;
		this.anInt467 = arg0;
	}

	@OriginalMember(owner = "client!bg", name = "toString", descriptor = "()Ljava/lang/String;")
	@Override
	public String toString() {
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!bg", name = "a", descriptor = "(I)I")
	public int method527() {
		return this.anInt467;
	}

@OriginalMember(owner = "client!bg", name = "c", descriptor = "I")
	public static int anInt464 = 0;
@OriginalMember(owner = "client!bg", name = "a", descriptor = "(ZI)I")
	public static int method526(@OriginalArg(1) int arg0) {
		if (QuickchatMessageTypeList.aClass11_5 != null) {
			QuickchatMessageTypeList.aClass11_5.method138();
			QuickchatMessageTypeList.aClass11_5 = null;
		}
		WaterfallEffect.anInt5132++;
		if (WaterfallEffect.anInt5132 > 4) {
			WaterfallEffect.anInt5132 = 0;
			EasedAngle.anInt862 = 0;
			return arg0;
		}
		if (Static133.anInt2755 == Class189.anInt5435) {
			Class189.anInt5435 = ReflectiveRipplingWaterEffect.anInt154;
		} else {
			Class189.anInt5435 = Static133.anInt2755;
		}
		EasedAngle.anInt862 = 0;
		return -1;
	}
@OriginalMember(owner = "client!bg", name = "a", descriptor = "(Lclient!wt;IIBI)V")
	public static void method528(@OriginalArg(0) Class4_Sub45 arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(4) int arg3) {
		@Pc(20) long local20 = (long) (arg1 | arg3 << 28 | arg2 << 14);
		@Pc(26) Class4_Sub18 local26 = (Class4_Sub18) LocTypeList.aClass102_40.method2700(local20);
		if (local26 == null) {
			local26 = new Class4_Sub18();
			LocTypeList.aClass102_40.method2703(local20, local26);
			local26.aClass183_14.method4137(arg0);
			return;
		}
		@Pc(49) ItemType local49 = Terrain.aClass206_3.method4703(arg0.anInt7355);
		@Pc(52) int local52 = local49.anInt6165;
		if (local49.anInt6164 == 1) {
			local52 *= arg0.anInt7356 + 1;
		}
		for (@Pc(71) Class4_Sub45 local71 = (Class4_Sub45) local26.aClass183_14.method4140(); local71 != null; local71 = (Class4_Sub45) local26.aClass183_14.method4144()) {
			local49 = Terrain.aClass206_3.method4703(local71.anInt7355);
			@Pc(82) int local82 = local49.anInt6165;
			if (local49.anInt6164 == 1) {
				local82 *= local71.anInt7356 + 1;
			}
			if (local52 > local82) {
				Class16_Sub2_Sub3.method3597(local71, arg0);
				return;
			}
		}
		local26.aClass183_14.method4137(arg0);
	}
@OriginalMember(owner = "client!bg", name = "b", descriptor = "(I)V")
	public static void method529() {
		Class4_Sub36.aClass247ArrayArray2 = new com.jagex.game.runetek5.config.rsinterface.Component[LegacyMouseManager.aClass76_81.method2100()][];
		LocEntity.aClass247ArrayArray1 = new com.jagex.game.runetek5.config.rsinterface.Component[LegacyMouseManager.aClass76_81.method2100()][];
		TileMessage.aBooleanArray25 = new boolean[LegacyMouseManager.aClass76_81.method2100()];
	}
}
