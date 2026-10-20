package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.deob.Class16_Sub5_Sub2;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.config.rsinterface.Component;
public final class Static102 {

	@OriginalMember(owner = "client!fb", name = "c", descriptor = "F")
	public static float aFloat56;

	@OriginalMember(owner = "client!fb", name = "e", descriptor = "I")
	public static int anInt2045;

	@OriginalMember(owner = "client!fb", name = "f", descriptor = "[Ljava/lang/String;")
	public static String[] aStringArray8;

	@OriginalMember(owner = "client!fb", name = "g", descriptor = "I")
	public static int anInt2046;

	@OriginalMember(owner = "client!fb", name = "a", descriptor = "I")
	public static int anInt2043 = 0;

	@OriginalMember(owner = "client!fb", name = "d", descriptor = "[Ljava/lang/String;")
	public static final String[] aStringArray7 = new String[100];

	@OriginalMember(owner = "client!fb", name = "a", descriptor = "(III)V")
	public static void method1794(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		if (Mesh.anInt3685 == 1) {
			GlFramebuffer.method3617(arg1, Component.aClass4_Sub39_2, arg0);
		} else if (Mesh.anInt3685 == 2) {
			Class16_Sub5_Sub2.method4658(arg1, arg0);
		}
		Mesh.anInt3685 = 0;
		Component.aClass4_Sub39_2 = null;
	}
}
