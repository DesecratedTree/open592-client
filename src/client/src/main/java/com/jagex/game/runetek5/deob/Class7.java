package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import java.awt.Frame;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.sign.SignedResource;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class267;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.deob.Class27_Sub3;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
@OriginalClass("client!ag")
public final class Class7 {

	@OriginalMember(owner = "client!ag", name = "b", descriptor = "Lclient!pe;")
	public Class16_Sub1 aClass16_Sub1_1;

	@OriginalMember(owner = "client!ag", name = "f", descriptor = "Lclient!ag;")
	public Class7 aClass7_1;

	@OriginalMember(owner = "client!ag", name = "i", descriptor = "I")
	public int anInt118;

	@OriginalMember(owner = "client!ag", name = "a", descriptor = "(I)V")
	public void method85() {
		if (Class4_Sub1_Sub11.anInt3086 >= 500) {
			return;
		}
		this.aClass7_1 = Class4_Sub7.aClass7_2;
		this.anInt118 = 0;
		this.aClass16_Sub1_1 = null;
		Class4_Sub1_Sub11.anInt3086++;
		Class4_Sub7.aClass7_2 = this;
	}

@OriginalMember(owner = "client!ag", name = "g", descriptor = "I")
	public static int anInt116;
@OriginalMember(owner = "client!ag", name = "e", descriptor = "[I")
	public static final int[] anIntArray7 = new int[32];
@OriginalMember(owner = "client!ag", name = "j", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_11 = new Class22(82, 12);
@OriginalMember(owner = "client!ag", name = "a", descriptor = "(IILclient!et;III)Ljava/awt/Frame;")
	public static Frame method86(@OriginalArg(0) int arg0, @OriginalArg(2) SignLink arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		if (!arg1.method1754()) {
			return null;
		}
		@Pc(20) Class103[] local20 = Class27_Sub3.method3184(arg1);
		if (local20 == null) {
			return null;
		}
		@Pc(26) boolean local26 = false;
		for (@Pc(28) int local28 = 0; local28 < local20.length; local28++) {
			if (local20[local28].anInt3173 == arg0 && local20[local28].anInt3174 == arg2 && (!local26 || local20[local28].anInt3176 > arg3)) {
				local26 = true;
				arg3 = local20[local28].anInt3176;
			}
		}
		if (!local26) {
			return null;
		}
		@Pc(94) SignedResource local94 = arg1.method1726(arg2, arg3, arg0);
		while (local94.anInt5760 == 0) {
			Class259.method5503(10L);
		}
		@Pc(116) Frame local116 = (Frame) local94.anObject29;
		if (local116 == null) {
			return null;
		} else if (local94.anInt5760 == 2) {
			Class267.method5620(arg1, local116);
			return null;
		} else {
			return local116;
		}
	}
@OriginalMember(owner = "client!ag", name = "a", descriptor = "(III)Z")
	public static boolean method87(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		return (arg0 & 0x800) != 0;
	}
}
