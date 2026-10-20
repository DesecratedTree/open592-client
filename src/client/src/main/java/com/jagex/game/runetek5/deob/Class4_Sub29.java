package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageType;
import com.jagex.game.runetek5.deob.Class85;
@OriginalClass("client!mk")
public final class Class4_Sub29 extends Country {

	@OriginalMember(owner = "client!mk", name = "q", descriptor = "J")
	public long aLong153;

	@OriginalMember(owner = "client!mk", name = "<init>", descriptor = "()V")
	private Class4_Sub29() {
	}

	@OriginalMember(owner = "client!mk", name = "<init>", descriptor = "(J)V")
	public Class4_Sub29(@OriginalArg(0) long arg0) {
		this.aLong153 = arg0;
	}

@OriginalMember(owner = "client!mk", name = "l", descriptor = "Z")
	public static boolean aBoolean423 = false;
@OriginalMember(owner = "client!mk", name = "o", descriptor = "I")
	public static int anInt4588 = 0;
@OriginalMember(owner = "client!mk", name = "p", descriptor = "I")
	public static int anInt4589 = 0;
@OriginalMember(owner = "client!mk", name = "c", descriptor = "(B)V")
	public static void method3601() {
		Class177.aClass57_20 = null;
		MidiPcmStream.aClass57_9 = null;
		QuickchatMessageType.aClass57_4 = null;
		Class85.aClass57_12 = null;
		ModeWhere.aClass57_21 = null;
		Class93.aClass57_15 = null;
		PointLight.aClass57_17 = null;
		Class4_Sub6_Sub24.aClass57_19 = null;
	}
}
