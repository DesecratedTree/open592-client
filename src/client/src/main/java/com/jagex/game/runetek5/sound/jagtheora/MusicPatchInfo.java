package com.jagex.game.runetek5.sound.jagtheora;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Static214;
import com.jagex.game.runetek5.sound.jagtheora.*;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class4_Sub45;
@OriginalClass("client!vg")
public final class MusicPatchInfo {

	@OriginalMember(owner = "client!vg", name = "a", descriptor = "I")
	public int volumeEnvelopeDecay;

	@OriginalMember(owner = "client!vg", name = "b", descriptor = "[B")
	public byte[] volumeEnvelopeData;

	@OriginalMember(owner = "client!vg", name = "c", descriptor = "I")
	public int anInt6938;

	@OriginalMember(owner = "client!vg", name = "e", descriptor = "[B")
	public byte[] releaseEnvelopeData;

	@OriginalMember(owner = "client!vg", name = "f", descriptor = "I")
	public int volumeEnvelopeRelease;

	@OriginalMember(owner = "client!vg", name = "g", descriptor = "I")
	public int vibratoLfoPitch;

	@OriginalMember(owner = "client!vg", name = "h", descriptor = "I")
	public int releaseEnvelopeKeyScaling;

	@OriginalMember(owner = "client!vg", name = "l", descriptor = "I")
	public int anInt6943;

	@OriginalMember(owner = "client!vg", name = "m", descriptor = "I")
	public int anInt6944;

@OriginalMember(owner = "client!vg", name = "d", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_310 = new Class22(25, -1);
@OriginalMember(owner = "client!vg", name = "j", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_93 = new Class215(31, 3);
@OriginalMember(owner = "client!vg", name = "k", descriptor = "Lclient!pk;")
	public static Class183 aClass183_48 = new Class183();
@OriginalMember(owner = "client!vg", name = "n", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_311 = new Class22(75, 14);
@OriginalMember(owner = "client!vg", name = "o", descriptor = "F")
	public static float aFloat215 = 0.25F;
@OriginalMember(owner = "client!vg", name = "a", descriptor = "(II)Ljava/lang/String;")
	public static String method5388(@OriginalArg(1) int arg0) {
		@Pc(13) String local13 = Integer.toString(arg0);
		for (@Pc(18) int local18 = local13.length() - 3; local18 > 0; local18 -= 3) {
			local13 = local13.substring(0, local18) + "," + local13.substring(local18);
		}
		if (local13.length() > 9) {
			return " <col=00ff80>" + local13.substring(0, local13.length() - 8) + Class4_Sub45.aClass83_152.method2267(NativeBillboardFace.anInt3423) + " (" + local13 + ")</col>";
		} else if (local13.length() > 6) {
			return " <col=ffffff>" + local13.substring(0, local13.length() - 4) + Static214.aClass83_79.method2267(NativeBillboardFace.anInt3423) + " (" + local13 + ")</col>";
		} else {
			return " <col=ffff00>" + local13 + "</col>";
		}
	}
}
