package com.jagex.game.runetek5.sound.jagtheora;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.cache.node.impl.AudioNode;

@OriginalClass("client!ti")
public abstract class PcmStream extends Country {

	@OriginalMember(owner = "client!ti", name = "l", descriptor = "I")
	public int streamPriority;

	@OriginalMember(owner = "client!ti", name = "m", descriptor = "Lclient!ti;")
	public PcmStream nextStream;

	@OriginalMember(owner = "client!ti", name = "o", descriptor = "Lclient!aw;")
	public AudioNode aClass4_Sub4_5;

	@OriginalMember(owner = "client!ti", name = "n", descriptor = "Z")
	public volatile boolean fill = true;

	@OriginalMember(owner = "client!ti", name = "a", descriptor = "([III)V")
	public abstract void fill(@OriginalArg(0) int[] buffer, @OriginalArg(1) int offset, @OriginalArg(2) int length);

	@OriginalMember(owner = "client!ti", name = "a", descriptor = "()I")
	public int getPriority() {
		return 255;
	}

	@OriginalMember(owner = "client!ti", name = "b", descriptor = "()Lclient!ti;")
	public abstract PcmStream method4857();

	@OriginalMember(owner = "client!ti", name = "b", descriptor = "([III)V")
	protected final void process(@OriginalArg(0) int[] buffer, @OriginalArg(1) int offset, @OriginalArg(2) int length) {
		if (this.fill) {
			this.fill(buffer, offset, length);
		} else {
			this.skip(length);
		}
	}

	@OriginalMember(owner = "client!ti", name = "c", descriptor = "()I")
	public abstract int getActiveVoiceCount();

	@OriginalMember(owner = "client!ti", name = "d", descriptor = "()Lclient!ti;")
	public abstract PcmStream method4860();

	@OriginalMember(owner = "client!ti", name = "a", descriptor = "(I)V")
	public abstract void skip(@OriginalArg(0) int length);
}
