package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
@OriginalClass("client!cg")
public abstract class Class4_Sub10 extends Country {

	@OriginalMember(owner = "client!cg", name = "t", descriptor = "Z")
	public boolean aBoolean220;

	@OriginalMember(owner = "client!cg", name = "u", descriptor = "Lclient!fd;")
	protected final GlToolkit aClass19_Sub2_16;

	static {
		new Class83("Sending request to leave channel...", "Chatraum wird verlassen...", "Envoi de la demande de sortie du canal...", "Enviando solicitação para deixar o canal...");
	}

	@OriginalMember(owner = "client!cg", name = "<init>", descriptor = "(Lclient!fd;)V")
	public Class4_Sub10(@OriginalArg(0) GlToolkit arg0) {
		this.aClass19_Sub2_16 = arg0;
	}

	@OriginalMember(owner = "client!cg", name = "a", descriptor = "(II)V")
	public abstract void method1996(@OriginalArg(1) int arg0);

	@OriginalMember(owner = "client!cg", name = "a", descriptor = "(Z)Z")
	public final boolean method1998() {
		return false;
	}

	@OriginalMember(owner = "client!cg", name = "b", descriptor = "(Z)I")
	public int method1999() {
		return 0;
	}

	@OriginalMember(owner = "client!cg", name = "a", descriptor = "(IBLclient!af;Lclient!af;)V")
	public abstract void method2000(@OriginalArg(0) int arg0, @OriginalArg(2) OpenGl2DTexture arg1, @OriginalArg(3) OpenGl2DTexture arg2);

	@OriginalMember(owner = "client!cg", name = "c", descriptor = "(Z)I")
	public final int method2001() {
		return 1;
	}

	@OriginalMember(owner = "client!cg", name = "c", descriptor = "(B)Z")
	public final boolean method2003() {
		return this.aBoolean220;
	}

	@OriginalMember(owner = "client!cg", name = "d", descriptor = "(B)Z")
	public abstract boolean method2004();

	@OriginalMember(owner = "client!cg", name = "e", descriptor = "(B)Z")
	public abstract boolean method2005();

	@OriginalMember(owner = "client!cg", name = "f", descriptor = "(B)V")
	public abstract void method2006();

	@OriginalMember(owner = "client!cg", name = "c", descriptor = "(III)V")
	public abstract void method2007(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1);

@OriginalMember(owner = "client!cg", name = "v", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_27;
@OriginalMember(owner = "client!cg", name = "n", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_98 = new Class22(33, 0);
@OriginalMember(owner = "client!cg", name = "r", descriptor = "Ljava/lang/String;")
	public static String aString28 = null;
@OriginalMember(owner = "client!cg", name = "w", descriptor = "I")
	public static int anInt2346 = 0;
@OriginalMember(owner = "client!cg", name = "a", descriptor = "(III)Z")
	public static boolean method1997(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		return Class4_Sub6_Sub15.method2975(arg0, arg1) | (arg0 & 0x70000) != 0 || Class164.method3914(arg1, arg0);
	}
@OriginalMember(owner = "client!cg", name = "b", descriptor = "(III)Z")
	public static boolean method2002(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		return (arg0 & 0x800) != 0 && (arg1 & 0x37) != 0;
	}
}
