package com.jagex.game.runetek5.config.quickchat;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.QuickChatValueType;

@OriginalClass("client!lo")
public interface QuickchatFillerList {

	@OriginalMember(owner = "client!lo", name = "a", descriptor = "(JB[ILclient!ho;)Ljava/lang/String;")
	String method3205(@OriginalArg(0) long arg0, @OriginalArg(2) int[] arg1, @OriginalArg(3) QuickChatValueType arg2);
}
