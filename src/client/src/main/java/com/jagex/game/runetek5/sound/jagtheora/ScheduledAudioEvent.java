package com.jagex.game.runetek5.sound.jagtheora;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

@OriginalClass("client!wr")
public abstract class ScheduledAudioEvent extends Country {

	@OriginalMember(owner = "client!wr", name = "l", descriptor = "I")
	public int scheduledTime;

	@OriginalMember(owner = "client!wr", name = "<init>", descriptor = "()V")
	private ScheduledAudioEvent() {
	}

	@OriginalMember(owner = "client!wr", name = "a", descriptor = "()V")
	public abstract void onRemove();

	@OriginalMember(owner = "client!wr", name = "a", descriptor = "(Lclient!jl;)I")
	public abstract int run(@OriginalArg(0) PcmStreamMixer mixer);
}
