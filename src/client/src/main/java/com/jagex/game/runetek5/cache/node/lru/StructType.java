package com.jagex.game.runetek5.cache.node.lru;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.cache.node.lru.*;
import com.jagex.game.runetek5.graphics.sprite.SpriteDrawQueue;
@OriginalClass("client!rk")
public final class StructType extends CacheableNode {

	@OriginalMember(owner = "client!rk", name = "y", descriptor = "Lclient!ib;")
	private CubeMapSpecularEffect aClass102_35;

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "(Lclient!iv;II)V")
	private void method4667(@OriginalArg(0) Packet arg0, @OriginalArg(1) int arg1) {
		if (arg1 != 249) {
			return;
		}
		@Pc(14) int local14 = arg0.method2490();
		@Pc(23) int local23;
		if (this.aClass102_35 == null) {
			local23 = SpriteDrawQueue.method4949(local14);
			this.aClass102_35 = new CubeMapSpecularEffect(local23);
		}
		for (local23 = 0; local23 < local14; local23++) {
			@Pc(42) boolean local42 = arg0.method2490() == 1;
			@Pc(46) int local46 = arg0.method2493();
			@Pc(55) Country local55;
			if (local42) {
				local55 = new Class4_Sub7(arg0.method2537());
			} else {
				local55 = new Class4_Sub37(arg0.method2529());
			}
			this.aClass102_35.method2703((long) local46, local55);
		}
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "(ILclient!iv;)V")
	public void method4668(@OriginalArg(1) Packet arg0) {
		while (true) {
			@Pc(12) int local12 = arg0.method2490();
			if (local12 == 0) {
				return;
			}
			this.method4667(arg0, local12);
		}
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "(III)I")
	public int getParam(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		if (this.aClass102_35 == null) {
			return arg0;
		} else {
			@Pc(23) Class4_Sub37 local23 = (Class4_Sub37) this.aClass102_35.method2700((long) arg1);
			return local23 == null ? arg0 : local23.anInt5426;
		}
	}

	@OriginalMember(owner = "client!rk", name = "a", descriptor = "(ILjava/lang/String;I)Ljava/lang/String;")
	public String getStringParam(@OriginalArg(1) String arg0, @OriginalArg(2) int string) {
		if (this.aClass102_35 == null) {
			return arg0;
		} else {
			@Pc(21) Class4_Sub7 local21 = (Class4_Sub7) this.aClass102_35.method2700((long) string);
			return local21 == null ? arg0 : local21.aString12;
		}
	}

@OriginalMember(owner = "client!rk", name = "z", descriptor = "[I")
	public static int[] anIntArray416;
@OriginalMember(owner = "client!rk", name = "F", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_75;
@OriginalMember(owner = "client!rk", name = "D", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_278 = new Class22(76, 7);
@OriginalMember(owner = "client!rk", name = "E", descriptor = "I")
	public static int anInt5940 = 0;
}
