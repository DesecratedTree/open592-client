package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.*;
@OriginalClass("client!mn")
public final class RuntimeException_Sub1 extends RuntimeException {

	@OriginalMember(owner = "client!mn", name = "e", descriptor = "Ljava/lang/Throwable;")
	public final Throwable aThrowable1;

	@OriginalMember(owner = "client!mn", name = "b", descriptor = "Ljava/lang/String;")
	public String aString46;

	@OriginalMember(owner = "client!mn", name = "<init>", descriptor = "(Ljava/lang/Throwable;Ljava/lang/String;)V")
	public RuntimeException_Sub1(@OriginalArg(0) Throwable arg0, @OriginalArg(1) String arg1) {
		this.aThrowable1 = arg0;
		this.aString46 = arg1;
	}

@OriginalMember(owner = "client!mn", name = "c", descriptor = "I")
	public static int anInt4620;
@OriginalMember(owner = "client!mn", name = "a", descriptor = "Z")
	public static boolean aBoolean425 = true;
@OriginalMember(owner = "client!mn", name = "f", descriptor = "I")
	public static int anInt4622 = 1;
@OriginalMember(owner = "client!mn", name = "g", descriptor = "I")
	public static int anInt4623 = -2;
@OriginalMember(owner = "client!mn", name = "a", descriptor = "([BI)[B")
	public static byte[] method3624(@OriginalArg(0) byte[] arg0) {
		@Pc(13) int local13 = arg0.length;
		@Pc(16) byte[] local16 = new byte[local13];
		Static459.copy(arg0, 0, local16, 0, local13);
		return local16;
	}
}
