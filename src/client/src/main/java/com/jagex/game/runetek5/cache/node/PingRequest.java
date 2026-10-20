package com.jagex.game.runetek5.cache.node;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

import com.jagex.game.runetek5.config.enumtype.EnumTypeList;
import com.jagex.game.runetek5.deob.Class215;

import com.jagex.game.runetek5.cache.node.*;
@OriginalClass("client!va")
public final class PingRequest extends Country {

	@OriginalMember(owner = "client!va", name = "m", descriptor = "I")
	public volatile int ping = -1;

	@OriginalMember(owner = "client!va", name = "n", descriptor = "Ljava/lang/String;")
	public final String address;

	@OriginalMember(owner = "client!va", name = "<init>", descriptor = "(Ljava/lang/String;)V")
	public PingRequest(@OriginalArg(0) String string) {
		this.address = string;
	}

@OriginalMember(owner = "client!va", name = "o", descriptor = "Lclient!ng;")
	public static EnumTypeList aClass158_1;
@OriginalMember(owner = "client!va", name = "l", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_92 = new Class215(24, -1);
@OriginalMember(owner = "client!va", name = "p", descriptor = "[Z")
	public static final boolean[] aBooleanArray21 = new boolean[100];
@OriginalMember(owner = "client!va", name = "q", descriptor = "[I")
	public static final int[] recentVarcUpdates = new int[4096];
}
