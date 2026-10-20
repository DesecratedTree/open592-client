package com.jagex.game.runetek5.js5.request;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.buffer.Packet;

import org.openrs2.deob.annotation.OriginalArg;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.deob.Class223;
import com.jagex.game.runetek5.network.packet.ServerProt;

import com.jagex.game.runetek5.js5.request.*;
@OriginalClass("client!je")
public final class Js5ResourceRequest extends Js5WorkerRequestMessage {

	@OriginalMember(owner = "client!je", name = "I", descriptor = "Lclient!iv;")
	public Packet aClass4_Sub12_4;

	@OriginalMember(owner = "client!je", name = "J", descriptor = "I")
	public int anInt3510;

	@OriginalMember(owner = "client!je", name = "L", descriptor = "B")
	public byte aByte24;

	@OriginalMember(owner = "client!je", name = "a", descriptor = "(I)I")
	@Override
	public int completePercentage() {
		return this.aClass4_Sub12_4 == null ? 0 : this.aClass4_Sub12_4.currentPosition * 100 / (this.aClass4_Sub12_4.buffer.length - this.aByte24);
	}

	@OriginalMember(owner = "client!je", name = "b", descriptor = "(Z)[B")
	@Override
	public byte[] getData() {
		if (super.waiting || this.aClass4_Sub12_4.currentPosition < this.aClass4_Sub12_4.buffer.length - this.aByte24) {
			throw new RuntimeException();
		}
		return this.aClass4_Sub12_4.buffer;
	}

@OriginalMember(owner = "client!je", name = "E", descriptor = "Lclient!sv;")
	public static Class223 aClass223_1;
@OriginalMember(owner = "client!je", name = "H", descriptor = "Lclient!jc;")
	public static final ServerProt aClass117_4 = new ServerProt("LIVE", 0);
@OriginalMember(owner = "client!je", name = "M", descriptor = "Lclient!bf;")
	public static final Class21 aClass21_11 = new Class21(5, 14);
@OriginalMember(owner = "client!je", name = "N", descriptor = "Lclient!bf;")
	public static final Class21 aClass21_12 = new Class21(14, 7);
@OriginalMember(owner = "client!je", name = "O", descriptor = "I")
	public static int mediumPos = 0;
@OriginalMember(owner = "client!je", name = "a", descriptor = "(IBI)Z")
	public static boolean method2904(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		return (arg1 & 0x10000) != 0;
	}
}
