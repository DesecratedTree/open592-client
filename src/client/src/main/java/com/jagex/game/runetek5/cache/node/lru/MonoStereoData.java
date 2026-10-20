package com.jagex.game.runetek5.cache.node.lru;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.cache.node.lru.*;
import com.jagex.game.runetek5.graphics.light.LightGL;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
@OriginalClass("client!gd")
public final class MonoStereoData extends Country {

	@OriginalMember(owner = "client!gd", name = "n", descriptor = "I")
	public final int anInt2616;

	@OriginalMember(owner = "client!gd", name = "l", descriptor = "I")
	public final int anInt2614;

	@OriginalMember(owner = "client!gd", name = "<init>", descriptor = "(II)V")
	public MonoStereoData(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		this.anInt2616 = arg1;
		this.anInt2614 = arg0;
	}

@OriginalMember(owner = "client!gd", name = "r", descriptor = "Lclient!ya;")
	public static com.jagex.game.runetek5.graphics.Toolkit aClass19_16;
@OriginalMember(owner = "client!gd", name = "o", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_32 = new Class215(76, 3);
@OriginalMember(owner = "client!gd", name = "p", descriptor = "[I")
	public static final int[] anIntArray176 = new int[] { 160, 192, 80, 96, 0, 144, 80, 48, 160 };
@OriginalMember(owner = "client!gd", name = "q", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_33 = new Class215(18, 6);
@OriginalMember(owner = "client!gd", name = "s", descriptor = "[Ljava/lang/String;")
	public static final String[] aStringArray9 = new String[100];
@OriginalMember(owner = "client!gd", name = "a", descriptor = "(ZI[Lclient!uu;)V")
	public static void method2212(@OriginalArg(1) int arg0, @OriginalArg(2) com.jagex.game.runetek5.config.rsinterface.Component[] arg1) {
		for (@Pc(12) int local12 = 0; local12 < arg1.length; local12++) {
			@Pc(18) com.jagex.game.runetek5.config.rsinterface.Component local18 = arg1[local12];
			if (local18 != null && arg0 == local18.anInt6850 && !client.isComponentHidden(local18)) {
				if (local18.anInt6840 == 0) {
					method2212(local18.anInt6809, arg1);
					if (local18.slotChildren != null) {
						method2212(local18.anInt6809, local18.slotChildren);
					}
					@Pc(56) Class4_Sub43 local56 = (Class4_Sub43) MusicPatch.aClass102_29.method2700((long) local18.anInt6809);
					if (local56 != null) {
						Class14.method208(local56.anInt6979);
					}
				}
				if (local18.anInt6840 == 6 && local18.anInt6788 != -1) {
					@Pc(77) SeqType local77 = SceneLocEntity.aClass55_1.method1397(local18.anInt6788);
					if (local77 != null) {
						local18.anInt6829 += LightGL.npcCount;
						while (local77.frames[local18.anInt6815] < local18.anInt6829) {
							local18.anInt6829 -= local77.frames[local18.anInt6815];
							local18.anInt6815++;
							if (local77.durations.length <= local18.anInt6815) {
								local18.anInt6815 -= local77.anInt4405;
								if (local18.anInt6815 < 0 || local18.anInt6815 >= local77.durations.length) {
									local18.anInt6815 = 0;
								}
							}
							local18.anInt6783 = local18.anInt6815 + 1;
							if (local77.durations.length <= local18.anInt6783) {
								local18.anInt6783 -= local77.anInt4405;
								if (local18.anInt6783 < 0 || local18.anInt6783 >= local77.durations.length) {
									local18.anInt6783 = -1;
								}
							}
							Class4_Sub12_Sub1.method1142(local18);
						}
					}
				}
			}
		}
	}
}
