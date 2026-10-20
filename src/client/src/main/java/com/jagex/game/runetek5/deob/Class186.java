package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.trigger.TriggerType;

import java.math.BigInteger;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.TextureOpBoxBlur;
import com.jagex.game.runetek5.graphics.shadow.ShadowTileGL;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpDiagonalWaves;
import com.jagex.game.runetek5.deob.Class249;
@OriginalClass("client!pn")
public final class Class186 implements TriggerType {

	@OriginalMember(owner = "client!pn", name = "f", descriptor = "I")
	public final int anInt5418;

	@OriginalMember(owner = "client!pn", name = "<init>", descriptor = "(Ljava/lang/String;I)V")
	public Class186(@OriginalArg(0) String arg0, @OriginalArg(1) int arg1) {
		this.anInt5418 = arg1;
	}

	@OriginalMember(owner = "client!pn", name = "toString", descriptor = "()Ljava/lang/String;")
	@Override
	public String toString() {
		throw new IllegalStateException();
	}

@OriginalMember(owner = "client!pn", name = "j", descriptor = "[Lclient!l;")
	public static NativeSprite[] aClass57Array10;
@OriginalMember(owner = "client!pn", name = "d", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_67 = new Class215(25, 4);
@OriginalMember(owner = "client!pn", name = "g", descriptor = "Ljava/math/BigInteger;")
	public static final BigInteger aBigInteger2 = new BigInteger("150760707550173036278772945946438567090506433713559524284882578712501882671153563393908075995202185676644780813553398041300178964417276453064154818204487233225067371665309152801785075695390556272661160726188792517860341413663154923673919152081958143212280562847593468338985450560012495634444598508070964823741");
@OriginalMember(owner = "client!pn", name = "h", descriptor = "[I")
	public static final int[] anIntArray373 = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 85, 80, 84, 0, 91, 0, 0, 0, 81, 82, 86, 0, 0, 0, 0, 0, 0, 0, 0, 13, 0, 0, 0, 0, 83, 104, 105, 103, 102, 96, 98, 97, 99, 0, 0, 0, 0, 0, 0, 0, 25, 16, 17, 18, 19, 20, 21, 22, 23, 24, 0, 0, 0, 0, 0, 0, 0, 48, 68, 66, 50, 34, 51, 52, 53, 39, 54, 55, 56, 70, 69, 40, 41, 32, 35, 49, 36, 38, 67, 33, 65, 37, 64, 0, 0, 0, 0, 0, 228, 231, 227, 233, 224, 219, 225, 230, 226, 232, 89, 87, 0, 88, 229, 90, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 0, 0, 0, 101, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
@OriginalMember(owner = "client!pn", name = "i", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_114 = new Class83("Prepared sound engine", "Musik-Engine vorbereitet.", "Moteur son préparé", "Mecanismo de som preparado");
@OriginalMember(owner = "client!pn", name = "k", descriptor = "J")
	public static long aLong183 = 0L;
@OriginalMember(owner = "client!pn", name = "l", descriptor = "Lclient!bf;")
	public static final Class21 aClass21_13 = new Class21(7, 16);
@OriginalMember(owner = "client!pn", name = "m", descriptor = "[F")
	public static final float[] aFloatArray25 = new float[] { 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F };
@OriginalMember(owner = "client!pn", name = "n", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_68 = new Class215(14, 7);
@OriginalMember(owner = "client!pn", name = "a", descriptor = "(IIIIB)V")
	public static void method4157(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		if (TextureOpColorGradient.anInt1764 == 1) {
			TextureOpBoxBlur.aClass57Array13[TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS / 100].render(Static14.anInt191 - 8, ShadowTileGL.anInt3911 - 8);
		}
		if (TextureOpColorGradient.anInt1764 == 2) {
			TextureOpBoxBlur.aClass57Array13[TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS / 100 + 4].render(Static14.anInt191 - 8, ShadowTileGL.anInt3911 + -8);
		}
		Static391.method5118();
	}
@OriginalMember(owner = "client!pn", name = "a", descriptor = "(Ljava/lang/String;B)V")
	public static void method4159(@OriginalArg(0) String arg0) {
		System.out.println("Bad " + arg0 + ", Usage: worldid, <live/rc/wip>, <english/german>, <game0/game1>");
		System.exit(1);
	}
@OriginalMember(owner = "client!pn", name = "a", descriptor = "(Lclient!uu;III)V")
	public static void method4160(@OriginalArg(0) com.jagex.game.runetek5.config.rsinterface.Component arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		if (arg0.aByte81 == 0) {
			arg0.anInt6859 = arg0.anInt6808;
		} else if (arg0.aByte81 == 1) {
			arg0.anInt6859 = (arg2 - arg0.anInt6805) / 2 + arg0.anInt6808;
		} else if (arg0.aByte81 == 2) {
			arg0.anInt6859 = arg2 - arg0.anInt6805 - arg0.anInt6808;
		} else if (arg0.aByte81 == 3) {
			arg0.anInt6859 = arg0.anInt6808 * arg2 >> 14;
		} else if (arg0.aByte81 == 4) {
			arg0.anInt6859 = (arg2 * arg0.anInt6808 >> 14) + (arg2 - arg0.anInt6805) / 2;
		} else {
			arg0.anInt6859 = arg2 - (arg2 * arg0.anInt6808 >> 14) - arg0.anInt6805;
		}
		if (arg0.aByte79 == 0) {
			arg0.anInt6836 = arg0.anInt6804;
		} else if (arg0.aByte79 == 1) {
			arg0.anInt6836 = (arg1 - arg0.anInt6833) / 2 + arg0.anInt6804;
		} else if (arg0.aByte79 == 2) {
			arg0.anInt6836 = arg1 - arg0.anInt6804 - arg0.anInt6833;
		} else if (arg0.aByte79 == 3) {
			arg0.anInt6836 = arg1 * arg0.anInt6804 >> 14;
		} else if (arg0.aByte79 == 4) {
			arg0.anInt6836 = (arg0.anInt6804 * arg1 >> 14) + (arg1 - arg0.anInt6833) / 2;
		} else {
			arg0.anInt6836 = arg1 - (arg1 * arg0.anInt6804 >> 14) - arg0.anInt6833;
		}
		if (!Class249.aBoolean626) {
			return;
		}
		if (client.method900(arg0).anInt5105 == 0 && arg0.anInt6840 != 0) {
			return;
		}
		if (arg0.anInt6859 < 0) {
			arg0.anInt6859 = 0;
		} else if (arg0.anInt6859 + arg0.anInt6805 > arg2) {
			arg0.anInt6859 = arg2 - arg0.anInt6805;
		}
		if (arg0.anInt6836 < 0) {
			arg0.anInt6836 = 0;
			return;
		}
		if (arg0.anInt6833 + arg0.anInt6836 > arg1) {
			arg0.anInt6836 = arg1 - arg0.anInt6833;
			return;
		}
	}
}
