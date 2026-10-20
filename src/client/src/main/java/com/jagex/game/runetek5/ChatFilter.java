package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.*;
@OriginalClass("client!id")
public final class ChatFilter {

	@OriginalMember(owner = "client!id", name = "e", descriptor = "I")
	public final int id;

	@OriginalMember(owner = "client!id", name = "<init>", descriptor = "(I)V")
	public ChatFilter(@OriginalArg(0) int id) {
		this.id = id;
	}

	@OriginalMember(owner = "client!id", name = "toString", descriptor = "()Ljava/lang/String;")
	@Override
	public String toString() {
		throw new IllegalStateException();
	}

@OriginalMember(owner = "client!id", name = "a", descriptor = "Lclient!uu;")
	public static com.jagex.game.runetek5.config.rsinterface.Component aClass247_6 = null;
@OriginalMember(owner = "client!id", name = "b", descriptor = "I")
	public static int anInt3177 = -1;
@OriginalMember(owner = "client!id", name = "d", descriptor = "I")
	public static int anInt3179 = -1;
}
