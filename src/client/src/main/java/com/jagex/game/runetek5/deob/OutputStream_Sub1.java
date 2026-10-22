package com.jagex.game.runetek5.deob;

import java.io.IOException;
import java.io.OutputStream;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.client.animation.Tweening;
@OriginalClass("client!gj")
public final class OutputStream_Sub1 extends OutputStream {

	@OriginalMember(owner = "client!gj", name = "write", descriptor = "(I)V")
	@Override
	public void write(@OriginalArg(0) int arg0) throws IOException {
		throw new IOException();
	}

@OriginalMember(owner = "client!gj", name = "c", descriptor = "I")
	public static int anInt2675;
@OriginalMember(owner = "client!gj", name = "a", descriptor = "Z")
	public static boolean aBoolean244 = Tweening.DEFAULT_ENABLED;
@OriginalMember(owner = "client!gj", name = "b", descriptor = "I")
	public static int anInt2674 = -1;
@OriginalMember(owner = "client!gj", name = "a", descriptor = "(II)I")
	public static int method2266(@OriginalArg(0) int arg0) {
		return arg0 & 0x7F;
	}
}
