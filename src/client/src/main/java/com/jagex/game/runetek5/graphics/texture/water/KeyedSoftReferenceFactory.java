package com.jagex.game.runetek5.graphics.texture.water;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.cache.node.impl.KeyedSoftReferenceNode;
import com.jagex.game.runetek5.cache.node.impl.SpriteRenderNode;
import com.jagex.game.runetek5.deob.Class174;

@OriginalClass("client!or")
public final class KeyedSoftReferenceFactory extends Class174 {

	@OriginalMember(owner = "client!or", name = "a", descriptor = "(ILclient!fq;)Lclient!fq;")
	@Override
	public SpriteRenderNode createSoftReference(@OriginalArg(1) SpriteRenderNode arg0) {
		return new KeyedSoftReferenceNode(arg0.get(), arg0.size);
	}
}
