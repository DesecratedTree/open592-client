package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.flo.FloTypeList;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.*;
@OriginalClass("client!hq")
public abstract class ByteArrayWrapper {

	@OriginalMember(owner = "client!hq", name = "a", descriptor = "(III)[B")
	public abstract byte[] readBlock(@OriginalArg(0) int arg0);

	@OriginalMember(owner = "client!hq", name = "a", descriptor = "(B)[B")
	public abstract byte[] get();

	@OriginalMember(owner = "client!hq", name = "a", descriptor = "(I[B)V")
	public abstract void set(@OriginalArg(1) byte[] arg0);

@OriginalMember(owner = "client!hq", name = "c", descriptor = "Lclient!kb;")
	public static FloTypeList aClass124_2;
@OriginalMember(owner = "client!hq", name = "e", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_11;
@OriginalMember(owner = "client!hq", name = "f", descriptor = "I")
	public static int cameraStepV;
@OriginalMember(owner = "client!hq", name = "a", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_51 = new Class22(78, 2);
@OriginalMember(owner = "client!hq", name = "b", descriptor = "Lclient!iv;")
	public static final Packet aClass4_Sub12_2 = new Packet(new byte[5000]);
@OriginalMember(owner = "client!hq", name = "d", descriptor = "Lclient!gk;")
	public static final Class83 IF_ON_IF = new Class83("Please wait...", "Bitte warte...", "Veuillez patienter...", "Aguarde...");
@OriginalMember(owner = "client!hq", name = "g", descriptor = "[I")
	public static final int[] anIntArray49 = new int[13];
}
