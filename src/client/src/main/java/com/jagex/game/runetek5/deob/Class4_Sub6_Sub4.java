package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.MapRegion;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub5;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.graphics.texture.water.BillowNoiseGenerator;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
@OriginalClass("client!dg")
public final class Class4_Sub6_Sub4 extends TextureOperation {

	@OriginalMember(owner = "client!dg", name = "C", descriptor = "I")
	private int anInt1400 = 585;

	static {
		new Class83(null, "Spieler nicht auf deiner Freunde-Liste.", null, null);
	}

	@OriginalMember(owner = "client!dg", name = "<init>", descriptor = "()V")
	public Class4_Sub6_Sub4() {
		super(0, true);
	}

	@OriginalMember(owner = "client!dg", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.anInt1400 = arg1.method2536();
		}
	}

	@OriginalMember(owner = "client!dg", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[] local11 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			@Pc(19) int local19 = ParticleEffector.anIntArray209[arg0];
			for (@Pc(21) int local21 = 0; local21 < MidiPcmStream.anInt1616; local21++) {
				@Pc(27) int local27 = AbstractArbBufferGL.anIntArray504[local21];
				@Pc(68) int local68;
				if (local27 > this.anInt1400 && 4096 - this.anInt1400 > local27 && 2048 - this.anInt1400 < local19 && this.anInt1400 + 2048 > local19) {
					local68 = 2048 - local27;
					local68 = local68 >= 0 ? local68 : -local68;
					local68 <<= 0xC;
					local68 /= 2048 - this.anInt1400;
					local11[local21] = 4096 - local68;
				} else if (local27 > 2048 - this.anInt1400 && this.anInt1400 + 2048 > local27) {
					local68 = local19 - 2048;
					local68 = local68 >= 0 ? local68 : -local68;
					local68 -= this.anInt1400;
					local68 <<= 0xC;
					local11[local21] = local68 / (2048 - this.anInt1400);
				} else if (local19 < this.anInt1400 || local19 > 4096 - this.anInt1400) {
					local68 = local27 - 2048;
					@Pc(179) int local179 = local68 < 0 ? -local68 : local68;
					@Pc(184) int local184 = local179 - this.anInt1400;
					@Pc(188) int local188 = local184 << 12;
					local11[local21] = local188 / (2048 - this.anInt1400);
				} else if (local27 >= this.anInt1400 && 4096 - this.anInt1400 >= local27) {
					local11[local21] = 0;
				} else {
					local68 = 2048 - local19;
					local68 = local68 < 0 ? -local68 : local68;
					local68 <<= 0xC;
					local68 /= 2048 - this.anInt1400;
					local11[local21] = 4096 - local68;
				}
			}
		}
		return local11;
	}

@OriginalMember(owner = "client!dg", name = "I", descriptor = "[Lclient!ta;")
	public static Class65[] aClass65Array1;
@OriginalMember(owner = "client!dg", name = "J", descriptor = "Lclient!wn;")
	public static MapRegion aClass266_Sub1_1;
@OriginalMember(owner = "client!dg", name = "K", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_19;
@OriginalMember(owner = "client!dg", name = "O", descriptor = "I")
	public static int anInt1408;
@OriginalMember(owner = "client!dg", name = "a", descriptor = "(IIBII)V")
	public static void method1274(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		@Pc(15) Class4_Sub1_Sub11 local15 = NPCTypeList.method5222(arg2, 10);
		local15.method2604();
		local15.anInt3082 = arg1;
		local15.anInt3079 = arg3;
		local15.anInt3083 = arg0;
	}
@OriginalMember(owner = "client!dg", name = "g", descriptor = "(I)V")
	public static void method1276() {
		if (Static308.aBoolean487) {
			return;
		}
		if (TextureOpBrightness.aClass50_Sub1_1.aBoolean303) {
			Static368.aFloat192 = (int) Static368.aFloat192 + 191 & 0xFFFFFF80;
		} else {
			Static380.aFloat195 += (24.0F - Static380.aFloat195) / 2.0F;
		}
		BasType.aBoolean429 = true;
		Static308.aBoolean487 = true;
	}
@OriginalMember(owner = "client!dg", name = "h", descriptor = "(I)V")
	public static void method1277() {
		Class4_Sub7.method633(false);
		if (BillowNoiseGenerator.anInt5660 >= 0 && BillowNoiseGenerator.anInt5660 != 0) {
			ParticleList.method3551(BillowNoiseGenerator.anInt5660);
			BillowNoiseGenerator.anInt5660 = -1;
		}
	}
@OriginalMember(owner = "client!dg", name = "a", descriptor = "(I[Ljava/lang/String;[S)V")
	public static void method1278(@OriginalArg(1) String[] arg0, @OriginalArg(2) short[] arg1) {
		Class4_Sub6_Sub5.method1388(arg1, arg0, 0, arg0.length - 1);
	}
}
