package com.jagex.game.runetek5.cache.node.impl;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Resampler;

@OriginalClass("client!js")
public final class VariableRateSoundPacket extends AudioNode {

	@OriginalMember(owner = "client!js", name = "p", descriptor = "Z")
	public boolean pingPong;

	@OriginalMember(owner = "client!js", name = "q", descriptor = "I")
	public int sampleRate;

	@OriginalMember(owner = "client!js", name = "o", descriptor = "[B")
	public byte[] data;

	@OriginalMember(owner = "client!js", name = "n", descriptor = "I")
	public int nominalBitRate;

	@OriginalMember(owner = "client!js", name = "m", descriptor = "I")
	public int minBitRate;

	@OriginalMember(owner = "client!js", name = "<init>", descriptor = "(I[BII)V")
	public VariableRateSoundPacket(@OriginalArg(0) int sampleRate, @OriginalArg(1) byte[] data, @OriginalArg(2) int nominalBitRate, @OriginalArg(3) int minBitRate) {
		this.sampleRate = sampleRate;
		this.data = data;
		this.nominalBitRate = nominalBitRate;
		this.minBitRate = minBitRate;
	}

	@OriginalMember(owner = "client!js", name = "<init>", descriptor = "(I[BIIZ)V")
	public VariableRateSoundPacket(@OriginalArg(0) int arg0, @OriginalArg(1) byte[] arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) boolean arg4) {
		this.sampleRate = arg0;
		this.data = arg1;
		this.nominalBitRate = arg2;
		this.minBitRate = arg3;
		this.pingPong = arg4;
	}

	@OriginalMember(owner = "client!js", name = "a", descriptor = "(Lclient!wd;)Lclient!js;")
	public VariableRateSoundPacket resample(@OriginalArg(0) Resampler sampleRateConverter) {
		this.data = sampleRateConverter.method5551(this.data);
		this.sampleRate = sampleRateConverter.method5552(this.sampleRate);
		if (this.nominalBitRate == this.minBitRate) {
			this.nominalBitRate = this.minBitRate = sampleRateConverter.method5550(this.nominalBitRate);
		} else {
			this.nominalBitRate = sampleRateConverter.method5550(this.nominalBitRate);
			this.minBitRate = sampleRateConverter.method5550(this.minBitRate);
			if (this.nominalBitRate == this.minBitRate) {
				this.nominalBitRate--;
			}
		}
		return this;
	}
}
