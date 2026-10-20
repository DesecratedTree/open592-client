package com.jagex.game.runetek5.cache.node.lru;

import java.lang.ref.SoftReference;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class4_Sub1_Sub9;
import com.jagex.game.runetek5.entity.ComparableGraphNode;

@OriginalClass("client!fv")
public final class LRUSoftReference extends Class4_Sub1_Sub9 {

	@OriginalMember(owner = "client!fv", name = "G", descriptor = "Ljava/lang/ref/SoftReference;")
	private final SoftReference softReference;

	@OriginalMember(owner = "client!fv", name = "<init>", descriptor = "(Lclient!bq;Ljava/lang/Object;I)V")
	public LRUSoftReference(@OriginalArg(0) ComparableGraphNode arg0, @OriginalArg(1) Object arg1, @OriginalArg(2) int arg2) {
		super(arg0, arg2);
		this.softReference = new SoftReference(arg1);
	}

	@OriginalMember(owner = "client!fv", name = "f", descriptor = "(B)Ljava/lang/Object;")
	@Override
	public Object get() {
		return this.softReference.get();
	}

	@OriginalMember(owner = "client!fv", name = "e", descriptor = "(B)Z")
	@Override
	public boolean isSoft() {
		return true;
	}
}
