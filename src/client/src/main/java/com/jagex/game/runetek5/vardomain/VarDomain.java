package com.jagex.game.runetek5.vardomain;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


@OriginalClass("client!vs")
public interface VarDomain {

	@OriginalMember(owner = "client!vs", name = "a", descriptor = "(BI)I")
	int getVarBitValue(@OriginalArg(1) int arg0);

	@OriginalMember(owner = "client!vs", name = "a", descriptor = "(II)I")
	int getVarValueInt(@OriginalArg(0) int id);
}
