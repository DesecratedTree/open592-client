package com.jagex.game.runetek5.graphics.model.billboard;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.graphics.model.billboard.*;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class38;
import com.jagex.game.runetek5.deob.Class190;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageType;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteTypeList;
@OriginalClass("client!jg")
public final class BillboardType {

	@OriginalMember(owner = "client!jg", name = "d", descriptor = "I")
	public int anInt3535 = 1;

	@OriginalMember(owner = "client!jg", name = "l", descriptor = "I")
	public int anInt3542 = 2;

	@OriginalMember(owner = "client!jg", name = "e", descriptor = "I")
	public int anInt3536 = -1;

	@OriginalMember(owner = "client!jg", name = "n", descriptor = "I")
	public int anInt3543 = 64;

	@OriginalMember(owner = "client!jg", name = "m", descriptor = "Z")
	public boolean hasUid = false;

	@OriginalMember(owner = "client!jg", name = "g", descriptor = "I")
	public int anInt3538 = 64;

	@OriginalMember(owner = "client!jg", name = "b", descriptor = "Z")
	public boolean stationary = false;

	@OriginalMember(owner = "client!jg", name = "a", descriptor = "(IILclient!iv;)V")
	public void method2922(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		while (true) {
			@Pc(9) int local9 = arg1.method2490();
			if (local9 == 0) {
				return;
			}
			this.method2923(arg1, local9, arg0);
		}
	}

	@OriginalMember(owner = "client!jg", name = "a", descriptor = "(Lclient!iv;III)V")
	private void method2923(@OriginalArg(0) Packet arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2) {
		if (arg1 == 1) {
			this.anInt3536 = arg0.method2536();
			if (this.anInt3536 == 65535) {
				this.anInt3536 = -1;
			}
		} else if (arg1 == 2) {
			this.anInt3538 = arg0.method2536() + 1;
			this.anInt3543 = arg0.method2536() + 1;
		} else if (arg1 == 3) {
			arg0.method2542();
		} else if (arg1 == 4) {
			this.anInt3542 = arg0.method2490();
		} else if (arg1 == 5) {
			this.anInt3535 = arg0.method2490();
		} else if (arg1 == 6) {
			this.stationary = true;
		} else if (arg1 == 7) {
			this.hasUid = true;
		}
	}

@OriginalMember(owner = "client!jg", name = "c", descriptor = "I")
	public static int anInt3534;
@OriginalMember(owner = "client!jg", name = "h", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_42;
@OriginalMember(owner = "client!jg", name = "k", descriptor = "I")
	public static int anInt3541;
@OriginalMember(owner = "client!jg", name = "o", descriptor = "[Lclient!l;")
	public static NativeSprite[] aClass57Array9;
@OriginalMember(owner = "client!jg", name = "a", descriptor = "(IZ)V")
	public static void method2924(@OriginalArg(0) int arg0) {
		Class190.anIntArray376 = new int[arg0];
		Class183.anIntArray360 = new int[arg0];
		Class38.anIntArray53 = new int[arg0];
		QuickchatMessageType.DYNAMIC_CORNER_WALL_SIDE_MASKS = new int[arg0];
		MapSpriteTypeList.anIntArray549 = new int[arg0];
	}
}
