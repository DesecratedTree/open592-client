package com.jagex.game.runetek5.cache.node;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

@OriginalClass("client!ld")
public final class MultiLocEntry extends Country {

	@OriginalMember(owner = "client!ld", name = "m", descriptor = "[S")
	public final short[] locIds;

	@OriginalMember(owner = "client!ld", name = "l", descriptor = "[B")
	public final byte[] locShapes;

	@OriginalMember(owner = "client!ld", name = "<init>", descriptor = "([S[B)V")
	public MultiLocEntry(@OriginalArg(0) short[] locIds, @OriginalArg(1) byte[] locShapes) {
		this.locIds = locIds;
		this.locShapes = locShapes;
	}
}
