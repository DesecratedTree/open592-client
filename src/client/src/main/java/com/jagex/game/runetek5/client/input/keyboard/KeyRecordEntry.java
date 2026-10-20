package com.jagex.game.runetek5.client.input.keyboard;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class4_Sub5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class26;
import com.jagex.game.runetek5.client.input.keyboard.*;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
@OriginalClass("client!be")
public final class KeyRecordEntry extends Class4_Sub5 {

	@OriginalMember(owner = "client!be", name = "q", descriptor = "J")
	public long aLong23;

	@OriginalMember(owner = "client!be", name = "t", descriptor = "I")
	public int anInt449;

	@OriginalMember(owner = "client!be", name = "v", descriptor = "I")
	public int anInt451;

	@OriginalMember(owner = "client!be", name = "D", descriptor = "I")
	public int anInt455;

	@OriginalMember(owner = "client!be", name = "c", descriptor = "(B)I")
	@Override
	public int method515() {
		return this.anInt451;
	}

	@OriginalMember(owner = "client!be", name = "c", descriptor = "(I)I")
	@Override
	public int method518() {
		return this.anInt449;
	}

	@OriginalMember(owner = "client!be", name = "d", descriptor = "(B)J")
	@Override
	public long method516() {
		return this.aLong23;
	}

	@OriginalMember(owner = "client!be", name = "e", descriptor = "(B)I")
	@Override
	public int method519() {
		return this.anInt455;
	}

@OriginalMember(owner = "client!be", name = "x", descriptor = "Lclient!bp;")
	public static Class26 aClass26_1;
@OriginalMember(owner = "client!be", name = "E", descriptor = "[[S")
	public static short[][] aShortArrayArray2;
@OriginalMember(owner = "client!be", name = "s", descriptor = "I")
	public static int anInt448 = 1;
@OriginalMember(owner = "client!be", name = "z", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_32 = new Class22(87, -1);
@OriginalMember(owner = "client!be", name = "C", descriptor = "[I")
	public static final int[] anIntArray29 = new int[16];
@OriginalMember(owner = "client!be", name = "a", descriptor = "(ZC)Z")
	public static boolean method521(@OriginalArg(1) char arg0) {
		if (arg0 > '\u0000' && arg0 < '\u0080' || !(arg0 < ' ' || arg0 > 'ÿ')) {
			return true;
		}
		if (arg0 != '\u0000') {
			@Pc(32) char[] local32 = StillWaterEffect.aCharArray7;
			for (@Pc(34) int local34 = 0; local34 < local32.length; local34++) {
				@Pc(40) char local40 = local32[local34];
				if (arg0 == local40) {
					return true;
				}
			}
		}
		return false;
	}
}
