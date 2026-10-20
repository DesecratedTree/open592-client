package com.jagex.game.runetek5.network.packet;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class183;

import com.jagex.game.runetek5.network.packet.*;
@OriginalClass("client!jc")
public final class ServerProt {

	@OriginalMember(owner = "client!jc", name = "b", descriptor = "I")
	public final int anInt3461;

	@OriginalMember(owner = "client!jc", name = "<init>", descriptor = "(Ljava/lang/String;I)V")
	public ServerProt(@OriginalArg(0) String arg0, @OriginalArg(1) int size) {
		this.anInt3461 = size;
	}

	@OriginalMember(owner = "client!jc", name = "toString", descriptor = "()Ljava/lang/String;")
	@Override
	public String toString() {
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!jc", name = "a", descriptor = "(Z)I")
	public int method2865() {
		return this.anInt3461;
	}

@OriginalMember(owner = "client!jc", name = "e", descriptor = "I")
	public static int anInt3464;
@OriginalMember(owner = "client!jc", name = "a", descriptor = "Lclient!pk;")
	public static final Class183 aClass183_25 = new Class183();
}
