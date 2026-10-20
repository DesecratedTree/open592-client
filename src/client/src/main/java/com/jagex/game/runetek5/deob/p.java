package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.graphics.loginscreen.DrawableItem;

@OriginalClass("client!p")
public final class p extends Shadow implements DrawableItem {

	@OriginalMember(owner = "client!p", name = "nativeid", descriptor = "J")
	private long nativeid;

	@OriginalMember(owner = "client!p", name = "finalize", descriptor = "()V")
	@Override
	public void finalize() {
		Static404.method5220(this);
	}

	@OriginalMember(owner = "client!p", name = "EA", descriptor = "()V")
	public native void EA();
}
