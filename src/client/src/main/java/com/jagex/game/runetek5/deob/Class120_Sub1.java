package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.midi.SoundBankCache;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageType;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub16;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteType;
import com.jagex.game.runetek5.graphics.texture.water.NativeWaterTexture;
import com.jagex.game.runetek5.preference.Preference;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.sound.jagtheora.MusicTrack;
import com.jagex.game.runetek5.deob.FluType;
import com.jagex.game.runetek5.deob.Class6_Sub2;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.deob.Class4_Sub41;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.deob.Class16_Sub3;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.cache.node.lru.StructType;
import com.jagex.game.runetek5.deob.Class145_Sub1;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
@OriginalClass("client!pg")
public final class Class120_Sub1 extends Class120 implements Runnable {

	@OriginalMember(owner = "client!pg", name = "v", descriptor = "Z")
	private boolean aBoolean483 = true;

	@OriginalMember(owner = "client!pg", name = "A", descriptor = "I")
	public int anInt5366 = -1;

	@OriginalMember(owner = "client!pg", name = "y", descriptor = "Lclient!fc;")
	private final Class70_Sub1 aClass70_Sub1_1;

	@OriginalMember(owner = "client!pg", name = "<init>", descriptor = "(Lclient!fc;)V")
	public Class120_Sub1(@OriginalArg(0) Class70_Sub1 arg0) {
		this.aClass70_Sub1_1 = arg0;
	}

	@OriginalMember(owner = "client!pg", name = "b", descriptor = "(B)V")
	public void method4109() {
		this.aBoolean483 = false;
	}

	@OriginalMember(owner = "client!pg", name = "run", descriptor = "()V")
	@Override
	public void run() {
		while (this.aBoolean483) {
			this.aClass70_Sub1_1.method1818(this);
		}
	}

	@OriginalMember(owner = "client!pg", name = "d", descriptor = "(B)V")
	public void method4112() {
		(new Thread(this, "a")).start();
	}

@OriginalMember(owner = "client!pg", name = "r", descriptor = "Z")
	public static boolean aBoolean482 = false;
@OriginalMember(owner = "client!pg", name = "t", descriptor = "I")
	public static int anInt5361 = -1;
@OriginalMember(owner = "client!pg", name = "w", descriptor = "I")
	public static int anInt5363 = 0;
@OriginalMember(owner = "client!pg", name = "x", descriptor = "I")
	public static final int anInt5364 = 328;
@OriginalMember(owner = "client!pg", name = "a", descriptor = "(IIB)Z")
	public static boolean method4110(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		return (arg0 & 0x8000) != 0;
	}
@OriginalMember(owner = "client!pg", name = "c", descriptor = "(B)Z")
	public static boolean method4111() {
		try {
			if (Class21.anInt461 == 2) {
				if (Class93.aClass4_Sub30_2 == null) {
					Class93.aClass4_Sub30_2 = MusicTrack.loadMusictrack(NativeWaterTexture.aClass76_49, Static14.anInt190, Class16_Sub3.anInt742);
					if (Class93.aClass4_Sub30_2 == null) {
						return false;
					}
				}
				if (StillWaterEffect.aClass171_1 == null) {
					StillWaterEffect.aClass171_1 = new SoundBankCache(FluType.aClass76_85, StructType.aClass76_75);
				}
				if (MapSpriteType.aClass4_Sub15_Sub1_1.method1456(StillWaterEffect.aClass171_1, Class4_Sub6_Sub16.aClass76_43, Class93.aClass4_Sub30_2)) {
					MapSpriteType.aClass4_Sub15_Sub1_1.method1438();
					MapSpriteType.aClass4_Sub15_Sub1_1.method1428(Class126.anInt3920);
					MapSpriteType.aClass4_Sub15_Sub1_1.method1450(Class6_Sub2.aBoolean30, Class93.aClass4_Sub30_2);
					StillWaterEffect.aClass171_1 = null;
					NativeWaterTexture.aClass76_49 = null;
					Class21.anInt461 = 0;
					Class93.aClass4_Sub30_2 = null;
					return true;
				}
			}
		} catch (@Pc(63) Exception local63) {
			local63.printStackTrace();
			MapSpriteType.aClass4_Sub15_Sub1_1.method1440();
			StillWaterEffect.aClass171_1 = null;
			NativeWaterTexture.aClass76_49 = null;
			Class21.anInt461 = 0;
			Class93.aClass4_Sub30_2 = null;
		}
		return false;
	}
@OriginalMember(owner = "client!pg", name = "a", descriptor = "(ZILjava/lang/String;)V")
	public static void method4113(@OriginalArg(0) boolean arg0, @OriginalArg(2) String arg1) {
		@Pc(4) String local4 = arg1.toLowerCase();
		@Pc(11) short[] local11 = new short[16];
		@Pc(13) int local13 = 0;
		@Pc(19) int local19 = arg0 ? 32768 : 0;
		@Pc(35) int local35 = local19 + (arg0 ? Class145_Sub1.aClass81_2.configClientSmallNum : Class145_Sub1.aClass81_2.configClientLargeNum);
		for (@Pc(37) int local37 = local19; local37 < local35; local37++) {
			@Pc(44) QuickchatMessageType local44 = Class145_Sub1.aClass81_2.method2258(local37);
			if (local44.searchable && local44.getText().toLowerCase().indexOf(local4) != -1) {
				if (local13 >= 50) {
					Preference.aShortArray66 = null;
					Class4_Sub41.anInt6702 = -1;
					return;
				}
				if (local13 >= local11.length) {
					@Pc(80) short[] local80 = new short[local11.length * 2];
					for (@Pc(82) int local82 = 0; local82 < local13; local82++) {
						local80[local82] = local11[local82];
					}
					local11 = local80;
				}
				local11[local13++] = (short) local37;
			}
		}
		Class4_Sub41.anInt6702 = local13;
		Static47.anInt809 = 0;
		Preference.aShortArray66 = local11;
		@Pc(115) String[] local115 = new String[Class4_Sub41.anInt6702];
		for (@Pc(117) int local117 = 0; local117 < Class4_Sub41.anInt6702; local117++) {
			local115[local117] = Class145_Sub1.aClass81_2.method2258(local11[local117]).getText();
		}
		Class4_Sub6_Sub4.method1278(local115, Preference.aShortArray66);
	}
@OriginalMember(owner = "client!pg", name = "a", descriptor = "(III)V")
	public static void method4114(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		@Pc(15) Class4_Sub1_Sub11 local15 = NPCTypeList.method5222(arg0, 13);
		local15.method2604();
		local15.anInt3083 = arg1;
	}
}
