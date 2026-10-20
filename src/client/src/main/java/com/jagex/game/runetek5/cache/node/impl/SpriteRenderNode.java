package com.jagex.game.runetek5.cache.node.impl;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.cache.node.lru.CacheableNode;

import com.jagex.game.runetek5.cache.node.impl.*;
@OriginalClass("client!fq")
public abstract class SpriteRenderNode extends CacheableNode {

	@OriginalMember(owner = "client!fq", name = "A", descriptor = "I")
	public final int size;

	@OriginalMember(owner = "client!fq", name = "<init>", descriptor = "(I)V")
	protected SpriteRenderNode(@OriginalArg(0) int arg0) {
		this.size = arg0;
	}

	@OriginalMember(owner = "client!fq", name = "e", descriptor = "(B)Z")
	public abstract boolean isSoft();

	@OriginalMember(owner = "client!fq", name = "d", descriptor = "(I)Ljava/lang/Object;")
	public abstract Object get();

@OriginalMember(owner = "client!fq", name = "y", descriptor = "[I")
	public static final int[] anIntArray328 = new int[8];
@OriginalMember(owner = "client!fq", name = "z", descriptor = "[[B")
	public static final byte[][] aByteArrayArray21 = new byte[250][];
@OriginalMember(owner = "client!fq", name = "C", descriptor = "[B")
	public static final byte[] aByteArray63 = new byte[2048];
}
