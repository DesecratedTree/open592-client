package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.ParticleList;

import java.awt.Canvas;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.unpack_Sub1;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class16_Sub2_Sub3;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub35;
import com.jagex.game.runetek5.deob.Class58;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpInterpolate;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.js5.request.local.LocalRequester;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
@OriginalClass("client!nt")
public final class Class164 {

	@OriginalMember(owner = "client!nt", name = "j", descriptor = "Lclient!rb;")
	public Class16_Sub5 aClass16_Sub5_2;

	@OriginalMember(owner = "client!nt", name = "k", descriptor = "Lclient!ii;")
	public Class16_Sub2 aClass16_Sub2_2;

	@OriginalMember(owner = "client!nt", name = "l", descriptor = "B")
	public byte aByte48;

	@OriginalMember(owner = "client!nt", name = "n", descriptor = "Lclient!hr;")
	public Class16_Sub4 aClass16_Sub4_2;

	@OriginalMember(owner = "client!nt", name = "p", descriptor = "Lclient!ua;")
	public Class16_Sub3 aClass16_Sub3_1;

	@OriginalMember(owner = "client!nt", name = "q", descriptor = "Lclient!ii;")
	public Class16_Sub2 aClass16_Sub2_3;

	@OriginalMember(owner = "client!nt", name = "r", descriptor = "Lclient!ag;")
	public Class7 aClass7_3;

	@OriginalMember(owner = "client!nt", name = "t", descriptor = "B")
	public byte aByte49;

	@OriginalMember(owner = "client!nt", name = "v", descriptor = "S")
	public short aShort65;

	@OriginalMember(owner = "client!nt", name = "w", descriptor = "Lclient!nt;")
	public Class164 aClass164_1;

	@OriginalMember(owner = "client!nt", name = "x", descriptor = "Z")
	public boolean aBoolean464;

	@OriginalMember(owner = "client!nt", name = "y", descriptor = "Z")
	public boolean aBoolean465;

	@OriginalMember(owner = "client!nt", name = "A", descriptor = "B")
	public byte aByte51;

	@OriginalMember(owner = "client!nt", name = "B", descriptor = "B")
	public byte aByte52;

	@OriginalMember(owner = "client!nt", name = "C", descriptor = "Z")
	public boolean aBoolean466;

	@OriginalMember(owner = "client!nt", name = "D", descriptor = "Lclient!rb;")
	public Class16_Sub5 aClass16_Sub5_3;

	@OriginalMember(owner = "client!nt", name = "E", descriptor = "Lclient!mc;")
	public ParticleList aClass146_2;

	@OriginalMember(owner = "client!nt", name = "z", descriptor = "B")
	public byte aByte50 = 0;

	@OriginalMember(owner = "client!nt", name = "o", descriptor = "S")
	public final short aShort63;

	@OriginalMember(owner = "client!nt", name = "s", descriptor = "S")
	public final short aShort64;

	@OriginalMember(owner = "client!nt", name = "G", descriptor = "B")
	public byte aByte54;

	@OriginalMember(owner = "client!nt", name = "F", descriptor = "B")
	public byte aByte53;

	@OriginalMember(owner = "client!nt", name = "<init>", descriptor = "(III)V")
	public Class164(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		this.aShort63 = (short) arg2;
		this.aShort64 = (short) arg1;
		this.aByte53 = this.aByte54 = (byte) arg0;
	}

	@OriginalMember(owner = "client!nt", name = "a", descriptor = "(B)V")
	public void method3915() {
		while (this.aClass7_3 != null) {
			@Pc(11) Class7 local11 = this.aClass7_3.aClass7_1;
			this.aClass7_3.method85();
			this.aClass7_3 = local11;
		}
		this.aByte50 = 0;
	}

@OriginalMember(owner = "client!nt", name = "d", descriptor = "[B")
	public static byte[] aByteArray64;
@OriginalMember(owner = "client!nt", name = "f", descriptor = "I")
	public static int anInt5120;
@OriginalMember(owner = "client!nt", name = "m", descriptor = "Ljava/awt/Canvas;")
	public static Canvas aCanvas5;
@OriginalMember(owner = "client!nt", name = "a", descriptor = "[I")
	public static final int[] anIntArray334 = new int[8];
@OriginalMember(owner = "client!nt", name = "e", descriptor = "I")
	public static int anInt5119 = 0;
@OriginalMember(owner = "client!nt", name = "g", descriptor = "Z")
	public static boolean aBoolean463 = false;
@OriginalMember(owner = "client!nt", name = "a", descriptor = "(IIZII)V")
	public static void method3912(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		if (TextureOpBrightness.aClass50_Sub1_1.anInt3448 != 0 && arg0 != 0 && Class16.anInt6886 < 50 && arg2 != -1) {
			OpenGLTextureCache.aClass269Array1[Class16.anInt6886++] = new Class269((byte) 2, arg2, arg0, arg1, arg3, 0);
		}
	}
@OriginalMember(owner = "client!nt", name = "a", descriptor = "(IB)Ljava/lang/String;")
	public static String method3913(@OriginalArg(0) int arg0) {
		return (arg0 >> 24 & 0xFF) + "." + (arg0 >> 16 & 0xFF) + "." + (arg0 >> 8 & 0xFF) + "." + (arg0 & 0xFF);
	}
@OriginalMember(owner = "client!nt", name = "a", descriptor = "(III)Z")
	public static boolean method3914(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		return (Class16_Sub2_Sub3.method3594(arg0, arg1) | Class4_Sub6_Sub15.method2975(arg1, arg0) | Class58.method1510(arg1, arg0)) & LocalRequester.method5437(arg0, arg1);
	}
@OriginalMember(owner = "client!nt", name = "a", descriptor = "(Lclient!fs;Lclient!ga;B)V")
	public static void method3916(@OriginalArg(0) Js5ResourceProvider arg0, @OriginalArg(1) unpack_Sub1 arg1) {
		TextureOpInterpolate.aClass76_38 = arg0;
		Class4_Sub6_Sub35.anInterface7_8 = arg1;
	}
}
