package com.jagex.game.runetek5.toolkit.vertexdeclaration;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import java.awt.Rectangle;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.deob.Class4_Sub3;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub23;
import com.jagex.game.runetek5.deob.Static148;
import com.jagex.game.runetek5.deob.Static308;

import com.jagex.game.runetek5.toolkit.vertexdeclaration.*;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.deob.Huffman;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub8_Sub1;
@OriginalClass("client!uh")
public final class VertexDeclarationComponent {

	@OriginalMember(owner = "client!uh", name = "l", descriptor = "C")
	public char aChar5;

	@OriginalMember(owner = "client!uh", name = "i", descriptor = "I")
	public int anInt6668 = 1;

	@OriginalMember(owner = "client!uh", name = "a", descriptor = "(Lclient!iv;B)V")
	public void method5214(@OriginalArg(0) Packet arg0) {
		while (true) {
			@Pc(5) int local5 = arg0.method2490();
			if (local5 == 0) {
				return;
			}
			this.method5218(local5, arg0);
		}
	}

	@OriginalMember(owner = "client!uh", name = "a", descriptor = "(BILclient!iv;)V")
	private void method5218(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 1) {
			this.aChar5 = Class4_Sub1_Sub8_Sub1.method3771(arg1.method2542());
		} else if (arg0 == 2) {
			this.anInt6668 = 0;
		}
	}

@OriginalMember(owner = "client!uh", name = "b", descriptor = "F")
	public static float aFloat211;
@OriginalMember(owner = "client!uh", name = "m", descriptor = "I")
	public static final int anInt6670 = 50;
@OriginalMember(owner = "client!uh", name = "a", descriptor = "[I")
	public static final int[] anIntArray507 = new int[anInt6670];
@OriginalMember(owner = "client!uh", name = "e", descriptor = "[I")
	public static final int[] anIntArray508 = new int[anInt6670];
@OriginalMember(owner = "client!uh", name = "f", descriptor = "[I")
	public static final int[] anIntArray509 = new int[anInt6670];
@OriginalMember(owner = "client!uh", name = "g", descriptor = "I")
	public static int anInt6667 = 0;
@OriginalMember(owner = "client!uh", name = "h", descriptor = "[I")
	public static final int[] anIntArray510 = new int[anInt6670];
@OriginalMember(owner = "client!uh", name = "j", descriptor = "[Ljava/lang/String;")
	public static final String[] aStringArray36 = new String[anInt6670];
@OriginalMember(owner = "client!uh", name = "n", descriptor = "[I")
	public static final int[] anIntArray511 = new int[anInt6670];
@OriginalMember(owner = "client!uh", name = "o", descriptor = "[I")
	public static final int[] anIntArray512 = new int[anInt6670];
@OriginalMember(owner = "client!uh", name = "a", descriptor = "(IIIII)V")
	public static void method5216(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(4) int arg3) {
		for (@Pc(3) int local3 = 0; local3 < SeqType.p12FullIndex; local3++) {
			@Pc(9) Rectangle local9 = Class4_Sub6_Sub23.aRectangleArray1[local3];
			if (arg3 < local9.width + local9.x && arg0 + arg3 > local9.x && local9.height + local9.y > arg2 && arg1 + arg2 > local9.y) {
				Class4_Sub6_Sub25.aBooleanArray15[local3] = true;
			}
		}
	}
@OriginalMember(owner = "client!uh", name = "a", descriptor = "(Z)Lclient!aq;")
	public static Class4_Sub3 method5217() {
		if (Static148.worldMapCategories == null || Static308.aClass63_1 == null) {
			return null;
		}
		Static308.aClass63_1.method1615(Static148.worldMapCategories);
		@Pc(18) Class4_Sub3 local18 = (Class4_Sub3) Static308.aClass63_1.method1611();
		if (local18 == null) {
			return null;
		} else {
			@Pc(28) MECType local28 = Static148.MECTYPE_LIST.method4124(local18.anInt234);
			return local28 != null && local28.aBoolean448 && local28.method3778(Static148.PLAYER_VAR_PROVIDER) ? local18 : Huffman.method2937();
		}
	}
}
