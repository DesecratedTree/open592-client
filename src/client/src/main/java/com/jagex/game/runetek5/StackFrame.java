package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.cache.node.lru.ClientScript;

import java.awt.Canvas;
import org.openrs2.deob.annotation.OriginalArg;
import com.jagex.game.runetek5.deob.Static283;

import com.jagex.game.runetek5.*;
import com.jagex.game.runetek5.deob.Class7;
@OriginalClass("client!b")
public final class StackFrame {

	@OriginalMember(owner = "client!b", name = "a", descriptor = "Lclient!ae;")
	public ClientScript script;

	@OriginalMember(owner = "client!b", name = "d", descriptor = "[I")
	public int[] intVars;

	@OriginalMember(owner = "client!b", name = "e", descriptor = "[Ljava/lang/String;")
	public String[] stringVars;

	@OriginalMember(owner = "client!b", name = "f", descriptor = "I")
	public int pc = -1;

@OriginalMember(owner = "client!b", name = "b", descriptor = "I")
	public static int anInt350 = -1;
@OriginalMember(owner = "client!b", name = "a", descriptor = "(Ljava/awt/Canvas;IILclient!ga;)Lclient!ya;")
	public static com.jagex.game.runetek5.graphics.Toolkit method302(@OriginalArg(0) Canvas arg0, @OriginalArg(2) int arg1, @OriginalArg(3) unpack_Sub1 arg2) {
		return new JavaToolkit(arg1, arg0, arg2);
	}
@OriginalMember(owner = "client!b", name = "a", descriptor = "(IIB)Z")
	public static boolean method303(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		return Static283.method3965(arg0, arg1) & Class7.method87(arg0, arg1);
	}
}
