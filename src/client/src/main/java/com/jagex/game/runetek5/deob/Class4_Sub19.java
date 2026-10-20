package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.HelveticaFont;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class70;
@OriginalClass("client!fr")
public final class Class4_Sub19 extends Country {

	@OriginalMember(owner = "client!fr", name = "m", descriptor = "[B")
	public final byte[] aByteArray24;

	static {
		new Class83("Invalid channel name entered!", "Ungültiger Chatraum-Name angegeben.", "Nom de canal incorrect !", "Nome de canal inválido!");
	}

	@OriginalMember(owner = "client!fr", name = "<init>", descriptor = "([B)V")
	public Class4_Sub19(@OriginalArg(0) byte[] arg0) {
		this.aByteArray24 = arg0;
	}

@OriginalMember(owner = "client!fr", name = "n", descriptor = "Lclient!sb;")
	public static HelveticaFont aClass212_4;
@OriginalMember(owner = "client!fr", name = "l", descriptor = "[I")
	public static final int[] anIntArray167 = new int[] { 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99, 99 };
@OriginalMember(owner = "client!fr", name = "o", descriptor = "Ljava/lang/String;")
	public static String aString29 = "";
@OriginalMember(owner = "client!fr", name = "a", descriptor = "(III)Lclient!hr;")
	public static Class16_Sub4 method2096(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		@Pc(7) Class164 local7 = Class70.aClass164ArrayArrayArray5[arg0][arg1][arg2];
		return local7 == null || local7.aClass16_Sub4_2 == null ? null : local7.aClass16_Sub4_2;
	}
}
