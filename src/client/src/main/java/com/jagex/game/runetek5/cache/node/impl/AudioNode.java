package com.jagex.game.runetek5.cache.node.impl;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

@OriginalClass("client!aw")
public abstract class AudioNode extends Country {

	@OriginalMember(owner = "client!aw", name = "l", descriptor = "I")
	public int activeVoiceCount;

	@OriginalMember(owner = "client!aw", name = "<init>", descriptor = "()V")
	protected AudioNode() {
	}
}
