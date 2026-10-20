package com.jagex.game.runetek5.cache.node;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

import com.jagex.game.runetek5.deob.Class217;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class83;

import com.jagex.game.runetek5.cache.node.*;
import com.jagex.game.runetek5.OpenGlArbElementArrayBuffer;
import com.jagex.game.runetek5.core.io.BufferedSocket;
@OriginalClass("client!ou")
public final class StringNode extends Country {

	@OriginalMember(owner = "client!ou", name = "o", descriptor = "Ljava/lang/String;")
	public String aString51;

	@OriginalMember(owner = "client!ou", name = "<init>", descriptor = "()V")
	private StringNode() {
	}

	@OriginalMember(owner = "client!ou", name = "<init>", descriptor = "(Ljava/lang/String;I)V")
	public StringNode(@OriginalArg(0) String arg0, @OriginalArg(1) int arg1) {
		this.aString51 = arg0;
	}

@OriginalMember(owner = "client!ou", name = "r", descriptor = "Lclient!sn;")
	public static Class217 aClass217_1;
@OriginalMember(owner = "client!ou", name = "l", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_109 = new Class83("Opened title screen", "Titelbild geöffnet.", "Écran-titre ouvert", "Tela título aberta");
@OriginalMember(owner = "client!ou", name = "n", descriptor = "I")
	public static int anInt5302 = -2;
@OriginalMember(owner = "client!ou", name = "p", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_241 = new Class22(34, 16);
@OriginalMember(owner = "client!ou", name = "q", descriptor = "I")
	public static int anInt5303 = 0;
@OriginalMember(owner = "client!ou", name = "a", descriptor = "(IIB)Z")
	public static boolean method4064(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		return OpenGlArbElementArrayBuffer.method1643(arg1, arg0) || BufferedSocket.method137(arg1, arg0);
	}
}
