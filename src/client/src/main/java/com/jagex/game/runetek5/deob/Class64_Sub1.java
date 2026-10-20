package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.buffer.ArrayBufferGL;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlBuffer;

import java.io.DataInputStream;
import java.net.URL;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.js5.request.RequestManager;
import com.jagex.game.runetek5.sign.SignedResource;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.config.midi.SoundBankCache;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.Class174;
import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
@OriginalClass("client!eo")
public final class Class64_Sub1 extends GlBuffer implements ArrayBufferGL {

	@OriginalMember(owner = "client!eo", name = "c", descriptor = "I")
	private int anInt1890;

	@OriginalMember(owner = "client!eo", name = "<init>", descriptor = "(Lclient!fd;I[BI)V")
	public Class64_Sub1(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) byte[] arg2, @OriginalArg(3) int arg3) {
		super(arg0, arg2, arg3);
		this.anInt1890 = arg1;
	}

	@OriginalMember(owner = "client!eo", name = "a", descriptor = "(I)I")
	@Override
	public int getId() {
		return 0;
	}

	@OriginalMember(owner = "client!eo", name = "b", descriptor = "(B)I")
	@Override
	public int getStride() {
		return this.anInt1890;
	}

	@OriginalMember(owner = "client!eo", name = "a", descriptor = "([BZII)V")
	@Override
	public void method3657(@OriginalArg(0) byte[] arg0, @OriginalArg(3) int arg1) {
		this.put(arg0, arg1);
		this.anInt1890 = 5123;
	}

	@OriginalMember(owner = "client!eo", name = "a", descriptor = "(B)J")
	@Override
	public long getAddress() {
		return super.aNativeBuffer4.c();
	}

@OriginalMember(owner = "client!eo", name = "n", descriptor = "Lclient!nj;")
	public static RequestManager aClass159_1;
@OriginalMember(owner = "client!eo", name = "j", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_35 = new Class83("Loading interface scripts - ", "Lade Interface-Skripte - ", "Chargement des interfaces - ", "Carregando interfaces - ");
@OriginalMember(owner = "client!eo", name = "l", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_21 = new Class215(80, 7);
@OriginalMember(owner = "client!eo", name = "m", descriptor = "Z")
	public static boolean aBoolean176 = false;
@OriginalMember(owner = "client!eo", name = "a", descriptor = "(IBIII)V")
	public static void method1652(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		@Pc(8) Class4_Sub1_Sub11 local8 = NPCTypeList.method5222(arg0, 8);
		local8.method2604();
		local8.anInt3082 = arg1;
		local8.anInt3083 = arg2;
		local8.anInt3079 = arg3;
	}
@OriginalMember(owner = "client!eo", name = "a", descriptor = "(IIIIIII)V")
	public static void method1653(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5) {
		@Pc(10) int local10 = arg0 - arg4;
		@Pc(14) int local14 = arg4 + arg5;
		for (@Pc(24) int local24 = arg5; local24 < local14; local24++) {
			BasType.method3640(arg1, arg2, arg3, Class4_Sub6_Sub7.anIntArrayArray21[local24]);
		}
		for (@Pc(40) int local40 = arg0; local40 > local10; local40--) {
			BasType.method3640(arg1, arg2, arg3, Class4_Sub6_Sub7.anIntArrayArray21[local40]);
		}
		@Pc(63) int local63 = arg3 - arg4;
		@Pc(67) int local67 = arg1 + arg4;
		for (@Pc(69) int local69 = local14; local69 <= local10; local69++) {
			@Pc(75) int[] local75 = Class4_Sub6_Sub7.anIntArrayArray21[local69];
			BasType.method3640(arg1, arg2, local67, local75);
			BasType.method3640(local63, arg2, arg3, local75);
		}
	}
@OriginalMember(owner = "client!eo", name = "a", descriptor = "(Ljava/lang/Throwable;Ljava/lang/String;I)V")
	public static void method1654(@OriginalArg(0) Throwable arg0, @OriginalArg(1) String arg1) {
		try {
			@Pc(7) String local7 = "";
			if (arg0 != null) {
				local7 = Class196.method4429(arg0);
			}
			if (arg1 != null) {
				if (arg0 != null) {
					local7 = local7 + " | ";
				}
				local7 = local7 + arg1;
			}
			BufferedSocket.method140(local7);
			local7 = Class174.method4044("%3a", ":", local7);
			local7 = Class174.method4044("%40", "@", local7);
			local7 = Class174.method4044("%26", "&", local7);
			local7 = Class174.method4044("%23", "#", local7);
			if (Class99_Sub1.aClass66_5.anApplet1 != null) {
				@Pc(99) SignedResource local99 = Class99_Sub1.aClass66_5.openStream(new URL(Class99_Sub1.aClass66_5.anApplet1.getCodeBase(), "clienterror.ws?c=" + BufferedSocket.anInt178 + "&u=" + SoundBankCache.aLong174 + "&v1=" + SignLink.aString23 + "&v2=" + SignLink.aString24 + "&e=" + local7));
				while (local99.anInt5760 == 0) {
					Class259.method5503(1L);
				}
				if (local99.anInt5760 == 1) {
					@Pc(125) DataInputStream local125 = (DataInputStream) local99.anObject29;
					local125.read();
					local125.close();
				}
			}
		} catch (@Pc(132) Exception local132) {
		}
	}
}
