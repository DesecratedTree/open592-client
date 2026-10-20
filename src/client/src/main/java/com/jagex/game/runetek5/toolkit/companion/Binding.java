package com.jagex.game.runetek5.toolkit.companion;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


@OriginalClass("client!pa")
public interface Binding {

	@OriginalMember(owner = "client!pa", name = "a", descriptor = "(I)V")
	void detach();
}
