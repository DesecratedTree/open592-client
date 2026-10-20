package com.jagex.game.runetek5.sound.jagtheora;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.cache.node.impl.VariableRateSoundPacket;
import com.jagex.game.runetek5.deob.Class83;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class120;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class16_Sub5;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Static368;
import com.jagex.game.runetek5.sound.jagtheora.*;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.graphics.font.FontMetrics;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.QuickChatPhrase;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.deob.Class70_Sub1;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.graphics.JavaBillboardAttributes;
@OriginalClass("client!lm")
public final class MusicPatchNode extends Country {

	@OriginalMember(owner = "client!lm", name = "l", descriptor = "I")
	public int tickSampleLength;

	@OriginalMember(owner = "client!lm", name = "n", descriptor = "I")
	public int anInt4382;

	@OriginalMember(owner = "client!lm", name = "p", descriptor = "I")
	public int anInt4384;

	@OriginalMember(owner = "client!lm", name = "q", descriptor = "I")
	public int anInt4385;

	@OriginalMember(owner = "client!lm", name = "r", descriptor = "I")
	public int anInt4386;

	@OriginalMember(owner = "client!lm", name = "t", descriptor = "Lclient!qo;")
	public AreaSoundStream stream;

	@OriginalMember(owner = "client!lm", name = "u", descriptor = "I")
	public int anInt4388;

	@OriginalMember(owner = "client!lm", name = "v", descriptor = "I")
	public int anInt4389;

	@OriginalMember(owner = "client!lm", name = "w", descriptor = "I")
	public int anInt4390;

	@OriginalMember(owner = "client!lm", name = "x", descriptor = "Lclient!vg;")
	public MusicPatchInfo musicPatchInfo;

	@OriginalMember(owner = "client!lm", name = "y", descriptor = "I")
	public int retriggerPhase;

	@OriginalMember(owner = "client!lm", name = "z", descriptor = "I")
	public int anInt4392;

	@OriginalMember(owner = "client!lm", name = "A", descriptor = "I")
	public int anInt4393;

	@OriginalMember(owner = "client!lm", name = "B", descriptor = "Lclient!js;")
	public VariableRateSoundPacket audioFile;

	@OriginalMember(owner = "client!lm", name = "C", descriptor = "Lclient!qh;")
	public MusicPatch patch;

	@OriginalMember(owner = "client!lm", name = "D", descriptor = "I")
	public int anInt4394;

	@OriginalMember(owner = "client!lm", name = "E", descriptor = "I")
	public int anInt4395;

	@OriginalMember(owner = "client!lm", name = "F", descriptor = "I")
	public int anInt4396;

	@OriginalMember(owner = "client!lm", name = "G", descriptor = "I")
	public int anInt4397;

	@OriginalMember(owner = "client!lm", name = "H", descriptor = "I")
	public int anInt4398;

	@OriginalMember(owner = "client!lm", name = "L", descriptor = "I")
	public int anInt4399;

	static {
		new Class83("You have been kicked from the channel.", "Du wurdest aus dem Chatraum rausgeworfen.", "Vous avez été expulsé du canal.", "Você foi expulso do canal.");
	}

	@OriginalMember(owner = "client!lm", name = "a", descriptor = "(I)V")
	public void method3453() {
		this.patch = null;
		this.audioFile = null;
		this.stream = null;
		this.musicPatchInfo = null;
	}

@OriginalMember(owner = "client!lm", name = "I", descriptor = "[I")
	public static final int[] anIntArray292 = new int[14];
@OriginalMember(owner = "client!lm", name = "J", descriptor = "[I")
	public static int[] anIntArray293 = new int[1];
@OriginalMember(owner = "client!lm", name = "K", descriptor = "[I")
	public static final int[] anIntArray294 = new int[100];
@OriginalMember(owner = "client!lm", name = "M", descriptor = "[Ljava/lang/String;")
	public static final String[] aStringArray17 = new String[100];
@OriginalMember(owner = "client!lm", name = "a", descriptor = "(Lclient!jj;III)V")
	public static void method3448(@OriginalArg(0) Class120 arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		@Pc(7) long local7 = Terrain.aLongArrayArrayArray2[arg1][arg2][arg3];
		@Pc(9) long local9 = 0L;
		arg0.anInt5351 = 0;
		@Pc(21) int local21;
		while (local9 <= 48L) {
			local21 = (int) (local7 >> (int) local9 & 0xFFFFL);
			if (local21 <= 0) {
				break;
			}
			arg0.aClass4_Sub21Array3[arg0.anInt5351++] = StructTypeList.aClass191Array1[local21 - 1].aClass4_Sub21_2;
			local9 += 16L;
		}
		for (local21 = arg0.anInt5351; local21 < 4; local21++) {
			arg0.aClass4_Sub21Array3[local21] = null;
		}
	}
@OriginalMember(owner = "client!lm", name = "a", descriptor = "(III)Lclient!rb;")
	public static Class16_Sub5 method3449(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		@Pc(7) Class164 local7 = Class70.aClass164ArrayArrayArray5[arg0][arg1][arg2];
		return local7 == null ? null : local7.aClass16_Sub5_3;
	}
@OriginalMember(owner = "client!lm", name = "a", descriptor = "(IIIIB)I")
	public static int method3450(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		@Pc(21) int local21 = 65536 - Class50_Sub1.anIntArray224[arg0 * 8192 / arg1] >> 1;
		return (arg3 * local21 >> 16) + ((65536 - local21) * arg2 >> 16);
	}
@OriginalMember(owner = "client!lm", name = "a", descriptor = "(IBII)V")
	public static void method3451(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		@Pc(7) int local7 = arg1 << 3;
		@Pc(11) int local11 = arg0 << 3;
		if (Class3_Sub3.anInt2386 == 2) {
			FontMetrics.anInt2790 = local7;
			QuickChatPhrase.anInt7074 = local11;
			Class70_Sub1.anInt2070 = 0;
		}
		Static368.aFloat192 = local7;
		Class16_Sub2.aFloat142 = local11;
		JavaBillboardAttributes.method3855();
		BasType.aBoolean429 = true;
	}
}
