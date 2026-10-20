package com.jagex.game.runetek5.graphics;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class186;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class83;

import com.jagex.game.runetek5.graphics.*;
@OriginalClass("client!j")
public final class NativeBillboardFace {

	@OriginalMember(owner = "client!j", name = "j", descriptor = "I")
	public final int anInt3420;

	@OriginalMember(owner = "client!j", name = "m", descriptor = "S")
	public final short aShort34;

	@OriginalMember(owner = "client!j", name = "b", descriptor = "I")
	public final int anInt3417;

	@OriginalMember(owner = "client!j", name = "c", descriptor = "I")
	public final int anInt3418;

	@OriginalMember(owner = "client!j", name = "n", descriptor = "S")
	public final short aShort35;

	@OriginalMember(owner = "client!j", name = "a", descriptor = "I")
	public final int anInt3416;

	@OriginalMember(owner = "client!j", name = "e", descriptor = "S")
	public final short aShort33;

	@OriginalMember(owner = "client!j", name = "l", descriptor = "I")
	public final int anInt3422;

	@OriginalMember(owner = "client!j", name = "g", descriptor = "B")
	public final byte aByte21;

	@OriginalMember(owner = "client!j", name = "h", descriptor = "Z")
	public final boolean aBoolean287;

	@OriginalMember(owner = "client!j", name = "f", descriptor = "B")
	public final byte aByte20;

	static {
		new Class186("", 73);
	}

	@OriginalMember(owner = "client!j", name = "<init>", descriptor = "(IIIIIIIIIZZI)V")
	public NativeBillboardFace(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int height, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int colourOp, @OriginalArg(8) int arg8, @OriginalArg(9) boolean hideFace, @OriginalArg(10) boolean hideWithBloom, @OriginalArg(11) int arg11) {
		this.anInt3420 = arg1;
		this.aShort34 = (short) height;
		this.anInt3417 = arg3;
		this.anInt3418 = arg11;
		this.aShort35 = (short) arg6;
		this.anInt3416 = arg0;
		this.aShort33 = (short) arg5;
		this.anInt3422 = arg2;
		this.aByte21 = (byte) colourOp;
		this.aBoolean287 = hideWithBloom;
		this.aByte20 = (byte) arg8;
	}

@OriginalMember(owner = "client!j", name = "q", descriptor = "[I")
	public static int[] anIntArray223;
@OriginalMember(owner = "client!j", name = "r", descriptor = "Lclient!va;")
	public static PingRequest aClass4_Sub42_2;
@OriginalMember(owner = "client!j", name = "i", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_152 = new Class22(48, 3);
@OriginalMember(owner = "client!j", name = "o", descriptor = "I")
	public static int anInt3423 = 0;
@OriginalMember(owner = "client!j", name = "p", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_72 = new Class83("M", "M", "M", "M");
@OriginalMember(owner = "client!j", name = "a", descriptor = "(Ljava/lang/String;IIII[B)I")
	public static int method2843(@OriginalArg(0) String arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(5) byte[] arg3) {
		@Pc(10) int local10 = arg1;
		for (@Pc(12) int local12 = 0; local12 < local10; local12++) {
			@Pc(20) char local20 = arg0.charAt(local12);
			if (local20 > '\u0000' && local20 < '\u0080' || !(local20 < ' ' || local20 > 'ÿ')) {
				arg3[local12 + arg2] = (byte) local20;
			} else if (local20 == '€') {
				arg3[arg2 + local12] = -128;
			} else if (local20 == '‚') {
				arg3[local12 + arg2] = -126;
			} else if (local20 == 'ƒ') {
				arg3[local12 + arg2] = -125;
			} else if (local20 == '„') {
				arg3[arg2 + local12] = -124;
			} else if (local20 == '…') {
				arg3[local12 + arg2] = -123;
			} else if (local20 == '†') {
				arg3[arg2 + local12] = -122;
			} else if (local20 == '‡') {
				arg3[arg2 + local12] = -121;
			} else if (local20 == 'ˆ') {
				arg3[arg2 + local12] = -120;
			} else if (local20 == '‰') {
				arg3[arg2 + local12] = -119;
			} else if (local20 == 'Š') {
				arg3[arg2 + local12] = -118;
			} else if (local20 == '‹') {
				arg3[arg2 + local12] = -117;
			} else if (local20 == 'Œ') {
				arg3[local12 + arg2] = -116;
			} else if (local20 == 'Ž') {
				arg3[local12 + arg2] = -114;
			} else if (local20 == '‘') {
				arg3[local12 + arg2] = -111;
			} else if (local20 == '’') {
				arg3[arg2 + local12] = -110;
			} else if (local20 == '“') {
				arg3[local12 + arg2] = -109;
			} else if (local20 == '”') {
				arg3[local12 + arg2] = -108;
			} else if (local20 == '•') {
				arg3[arg2 + local12] = -107;
			} else if (local20 == '–') {
				arg3[local12 + arg2] = -106;
			} else if (local20 == '—') {
				arg3[arg2 + local12] = -105;
			} else if (local20 == '˜') {
				arg3[local12 + arg2] = -104;
			} else if (local20 == '™') {
				arg3[local12 + arg2] = -103;
			} else if (local20 == 'š') {
				arg3[local12 + arg2] = -102;
			} else if (local20 == '›') {
				arg3[local12 + arg2] = -101;
			} else if (local20 == 'œ') {
				arg3[arg2 + local12] = -100;
			} else if (local20 == 'ž') {
				arg3[local12 + arg2] = -98;
			} else if (local20 == 'Ÿ') {
				arg3[local12 + arg2] = -97;
			} else {
				arg3[local12 + arg2] = 63;
			}
		}
		return local10;
	}
@OriginalMember(owner = "client!j", name = "a", descriptor = "(II)Z")
	public static boolean method2845(@OriginalArg(0) int arg0) {
		return arg0 == 2 || arg0 == 3;
	}
}
