package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class22;

import com.jagex.game.runetek5.*;
@OriginalClass("client!vu")
public class Country {

	@OriginalMember(owner = "client!vu", name = "b", descriptor = "Lclient!vu;")
	public Country aClass4_261;

	@OriginalMember(owner = "client!vu", name = "d", descriptor = "J")
	public long aLong224;

	@OriginalMember(owner = "client!vu", name = "h", descriptor = "Lclient!vu;")
	public Country aClass4_262;

	@OriginalMember(owner = "client!vu", name = "b", descriptor = "(I)Z")
	public final boolean method5683() {
		return this.aClass4_261 != null;
	}

	@OriginalMember(owner = "client!vu", name = "b", descriptor = "(B)V")
	public final void method5684() {
		if (this.aClass4_261 != null) {
			this.aClass4_261.aClass4_262 = this.aClass4_262;
			this.aClass4_262.aClass4_261 = this.aClass4_261;
			this.aClass4_262 = null;
			this.aClass4_261 = null;
		}
	}

@OriginalMember(owner = "client!vu", name = "c", descriptor = "[S")
	public static short[] aShortArray252;
@OriginalMember(owner = "client!vu", name = "f", descriptor = "I")
	public static int anInt7378;
@OriginalMember(owner = "client!vu", name = "g", descriptor = "F")
	public static float aFloat228;
@OriginalMember(owner = "client!vu", name = "e", descriptor = "Z")
	public static boolean aBoolean692 = false;
@OriginalMember(owner = "client!vu", name = "i", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_336 = new Class22(65, 2);
}
