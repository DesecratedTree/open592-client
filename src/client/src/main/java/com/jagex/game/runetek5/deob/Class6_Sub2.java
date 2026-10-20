package com.jagex.game.runetek5.deob;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.graphics.gl.GlToolkit;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.network.packet.ServerProt;
import com.jagex.game.runetek5.botdetection.ReflectionCheck;
@OriginalClass("client!ar")
public final class Class6_Sub2 extends Class6 {

	@OriginalMember(owner = "client!ar", name = "C", descriptor = "I")
	private final int anInt258;

	@OriginalMember(owner = "client!ar", name = "<init>", descriptor = "(Lclient!fd;II[BI)V")
	public Class6_Sub2(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) byte[] arg3, @OriginalArg(4) int arg4) {
		super(arg0, 3552, arg1, arg2, false);
		this.anInt258 = arg2;
		super.aClass19_Sub2_31.setActiveTexture(this);
		OpenGL.glPixelStorei(3317, 1);
		OpenGL.glTexImage1Dub(super.anInt5441, 0, super.anInt5443, this.anInt258, 0, arg4, 5121, arg3, 0);
		OpenGL.glPixelStorei(3317, 4);
		this.method4181(true);
	}

	@OriginalMember(owner = "client!ar", name = "a", descriptor = "(BZ)V")
	public void method199() {
		super.aClass19_Sub2_31.setActiveTexture(this);
		OpenGL.glTexParameteri(super.anInt5441, 10242, 33071);
	}

	@OriginalMember(owner = "client!ar", name = "a", descriptor = "(I)V")
	@Override
	public void detach() {
	}

@OriginalMember(owner = "client!ar", name = "x", descriptor = "I")
	public static int anInt255;
@OriginalMember(owner = "client!ar", name = "y", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_2;
@OriginalMember(owner = "client!ar", name = "A", descriptor = "Z")
	public static boolean aBoolean30;
@OriginalMember(owner = "client!ar", name = "f", descriptor = "(I)V")
	public static void method198() {
		ServerProt.anInt3464 = ReflectionCheck.aClass89_1.anInt2783 + ReflectionCheck.aClass89_1.anInt2778 + 2;
		Static363.anInt6275 = Js5ResourceProvider.aClass89_5.anInt2778 + Js5ResourceProvider.aClass89_5.anInt2783 + 2;
		Static102.aStringArray8 = new String[500];
		for (@Pc(26) int local26 = 0; local26 < Static102.aStringArray8.length; local26++) {
			Static102.aStringArray8[local26] = "";
		}
	}
}
