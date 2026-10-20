package com.jagex.game.runetek5.core.datastruct.ref;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.cache.node.lru.LRUSoftReference;
import com.jagex.game.runetek5.deob.Class105;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub9;

@OriginalClass("client!us")
public final class SoftReferenceFactory extends Class105 {

	@OriginalMember(owner = "client!us", name = "a", descriptor = "(Lclient!mh;I)Lclient!mh;")
	@Override
	public Class4_Sub1_Sub9 method5276(@OriginalArg(0) Class4_Sub1_Sub9 arg0) {
		return new LRUSoftReference(arg0.anInterface2_3, arg0.get(), arg0.anInt3025);
	}
}
