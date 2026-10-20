package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.sequence.SeqTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub36;
import com.jagex.game.runetek5.cache.node.CacheableNodeQueue;
import com.jagex.game.runetek5.GlVolumeTexture;
import com.jagex.game.runetek5.deob.Class4_Sub18;
@OriginalClass("client!po")
public final class Class4_Sub37 extends Country {

	@OriginalMember(owner = "client!po", name = "p", descriptor = "I")
	public int anInt5426;

	@OriginalMember(owner = "client!po", name = "<init>", descriptor = "()V")
	private Class4_Sub37() {
	}

	@OriginalMember(owner = "client!po", name = "<init>", descriptor = "(I)V")
	public Class4_Sub37(@OriginalArg(0) int arg0) {
		this.anInt5426 = arg0;
	}

@OriginalMember(owner = "client!po", name = "n", descriptor = "I")
	public static int anInt5424;
@OriginalMember(owner = "client!po", name = "m", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_251 = new Class22(55, 2);
@OriginalMember(owner = "client!po", name = "a", descriptor = "(IIBII)Z")
	public static boolean method4164(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		if ((SeqTypeList.aByteArrayArrayArray2[0][arg0][arg3] & 0x2) != 0) {
			return true;
		} else if ((SeqTypeList.aByteArrayArrayArray2[arg2][arg0][arg3] & 0x10) == 0) {
			return CacheableNodeQueue.method3479(arg3, arg2, arg0) == arg1;
		} else {
			return false;
		}
	}
@OriginalMember(owner = "client!po", name = "a", descriptor = "(III)V")
	public static void method4165(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		if (Class4_Sub18.method2034(arg0)) {
			GlVolumeTexture.method790(arg1, Class4_Sub36.aClass247ArrayArray2[arg0]);
		}
	}
}
