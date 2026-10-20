package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.toolkit.opengl.GlParticleRenderer;
import com.jagex.game.runetek5.deob.Class4_Sub19;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchNode;
import com.jagex.game.runetek5.deob.OutputStream_Sub1;
import com.jagex.game.runetek5.deob.Class26;
@OriginalClass("client!at")
public final class Class15 {

	@OriginalMember(owner = "client!at", name = "c", descriptor = "Ljava/lang/String;")
	public String aString5;

	@OriginalMember(owner = "client!at", name = "f", descriptor = "Ljava/lang/String;")
	public String aString6;

	@OriginalMember(owner = "client!at", name = "g", descriptor = "Ljava/lang/String;")
	public String aString7;

	@OriginalMember(owner = "client!at", name = "h", descriptor = "Ljava/lang/String;")
	public String aString8;

	@OriginalMember(owner = "client!at", name = "i", descriptor = "B")
	public byte aByte1;

	@OriginalMember(owner = "client!at", name = "k", descriptor = "I")
	public int anInt272;

@OriginalMember(owner = "client!at", name = "a", descriptor = "[I")
	public static final int[] anIntArray13 = new int[8];
@OriginalMember(owner = "client!at", name = "d", descriptor = "[C")
	public static final char[] aCharArray1 = new char[] { '_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
@OriginalMember(owner = "client!at", name = "e", descriptor = "[I")
	public static final int[] anIntArray14 = new int[] { 0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 131071, 262143, 524287, 1048575, 2097151, 4194303, 8388607, 16777215, 33554431, 67108863, 134217727, 268435455, 536870911, 1073741823, Integer.MAX_VALUE, -1 };
@OriginalMember(owner = "client!at", name = "a", descriptor = "(IBI)V")
	public static void method215(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		Class9.anInt6920 = arg0 - Static148.anInt5724;
		OutputStream_Sub1.anInt2674 = arg1 - Static148.anInt5732;
	}
@OriginalMember(owner = "client!at", name = "a", descriptor = "(ILclient!ca;)V")
	public static void method216(@OriginalArg(1) Class4_Sub9 arg0) {
		if (Class70.aClass164ArrayArrayArray5 == null) {
			return;
		}
		@Pc(8) GameObject local8 = null;
		if (arg0.anInt792 == 0) {
			local8 = (GameObject) MusicPatchNode.method3449(arg0.anInt800, arg0.anInt793, arg0.anInt790);
		}
		if (arg0.anInt792 == 1) {
			local8 = (GameObject) GlParticleRenderer.method918(arg0.anInt800, arg0.anInt793, arg0.anInt790);
		}
		if (arg0.anInt792 == 2) {
			local8 = (GameObject) Class26.method648(arg0.anInt800, arg0.anInt793, arg0.anInt790, GameObject.class);
		}
		if (arg0.anInt792 == 3) {
			local8 = (GameObject) Class4_Sub19.method2096(arg0.anInt800, arg0.anInt793, arg0.anInt790);
		}
		if (local8 == null) {
			arg0.anInt797 = -1;
			arg0.anInt802 = 0;
			arg0.anInt803 = 0;
		} else {
			arg0.anInt797 = local8.method5350();
			arg0.anInt802 = local8.method5354();
			arg0.anInt803 = local8.method5347();
		}
	}
}
