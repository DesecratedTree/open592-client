package com.jagex.game.runetek5.cache.node.impl;

import java.lang.ref.SoftReference;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


@OriginalClass("client!nq")
public final class KeyedSoftReferenceNode extends SpriteRenderNode {

	@OriginalMember(owner = "client!nq", name = "B", descriptor = "Ljava/lang/ref/SoftReference;")
	private final SoftReference softReference;

	@OriginalMember(owner = "client!nq", name = "<init>", descriptor = "(Ljava/lang/Object;I)V")
	public KeyedSoftReferenceNode(@OriginalArg(0) Object arg0, @OriginalArg(1) int arg1) {
		super(arg1);
		this.softReference = new SoftReference(arg0);
	}

	@OriginalMember(owner = "client!nq", name = "e", descriptor = "(B)Z")
	@Override
	public boolean isSoft() {
		return true;
	}

	@OriginalMember(owner = "client!nq", name = "d", descriptor = "(I)Ljava/lang/Object;")
	@Override
	public Object get() {
		return this.softReference.get();
	}
}
