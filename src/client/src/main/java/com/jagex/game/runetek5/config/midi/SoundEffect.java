package com.jagex.game.runetek5.config.midi;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.impl.VariableRateSoundPacket;

import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.config.midi.*;
@OriginalClass("client!um")
public final class SoundEffect {

	@OriginalMember(owner = "client!um", name = "c", descriptor = "[Lclient!pl;")
	private final Instrument[] instruments = new Instrument[10];

	@OriginalMember(owner = "client!um", name = "a", descriptor = "I")
	private int loopStart;

	@OriginalMember(owner = "client!um", name = "b", descriptor = "I")
	private int loopEnd;

	@OriginalMember(owner = "client!um", name = "<init>", descriptor = "(Lclient!iv;)V")
	public SoundEffect(@OriginalArg(0) Packet packet) {
		for (@Pc(7) int local7 = 0; local7 < 10; local7++) {
			@Pc(12) int local12 = packet.method2490();
			if (local12 != 0) {
				packet.currentPosition--;
				this.instruments[local7] = new Instrument();
				this.instruments[local7].decode(packet);
			}
		}
		this.loopStart = packet.method2536();
		this.loopEnd = packet.method2536();
	}

	@OriginalMember(owner = "client!um", name = "<init>", descriptor = "()V")
	private SoundEffect() {
	}

	@OriginalMember(owner = "client!um", name = "a", descriptor = "()[B")
	private byte[] mix() {
		@Pc(1) int local1 = 0;
		for (@Pc(3) int local3 = 0; local3 < 10; local3++) {
			if (this.instruments[local3] != null && this.instruments[local3].duration + this.instruments[local3].loopBegin > local1) {
				local1 = this.instruments[local3].duration + this.instruments[local3].loopBegin;
			}
		}
		if (local1 == 0) {
			return new byte[0];
		}
		@Pc(49) int local49 = local1 * 22050 / 1000;
		@Pc(52) byte[] local52 = new byte[local49];
		for (@Pc(54) int local54 = 0; local54 < 10; local54++) {
			if (this.instruments[local54] != null) {
				@Pc(70) int local70 = this.instruments[local54].duration * 22050 / 1000;
				@Pc(80) int local80 = this.instruments[local54].loopBegin * 22050 / 1000;
				@Pc(92) int[] local92 = this.instruments[local54].synthesize(local70, this.instruments[local54].duration);
				for (@Pc(94) int local94 = 0; local94 < local70; local94++) {
					@Pc(107) int local107 = local52[local94 + local80] + (local92[local94] >> 8);
					if ((local107 + 128 & 0xFFFFFF00) != 0) {
						local107 = local107 >> 31 ^ 0x7F;
					}
					local52[local94 + local80] = (byte) local107;
				}
			}
		}
		return local52;
	}

	@OriginalMember(owner = "client!um", name = "b", descriptor = "()Lclient!js;")
	public VariableRateSoundPacket getMixedAudio() {
		@Pc(2) byte[] local2 = this.mix();
		return new VariableRateSoundPacket(22050, local2, this.loopStart * 22050 / 1000, this.loopEnd * 22050 / 1000);
	}

	@OriginalMember(owner = "client!um", name = "c", descriptor = "()I")
	public int getDelay() {
		@Pc(1) int local1 = 9999999;
		for (@Pc(3) int local3 = 0; local3 < 10; local3++) {
			if (this.instruments[local3] != null && this.instruments[local3].loopBegin / 20 < local1) {
				local1 = this.instruments[local3].loopBegin / 20;
			}
		}
		if (this.loopStart < this.loopEnd && this.loopStart / 20 < local1) {
			local1 = this.loopStart / 20;
		}
		if (local1 == 9999999 || local1 == 0) {
			return 0;
		}
		for (@Pc(55) int local55 = 0; local55 < 10; local55++) {
			if (this.instruments[local55] != null) {
				this.instruments[local55].loopBegin -= local1 * 20;
			}
		}
		if (this.loopStart < this.loopEnd) {
			this.loopStart -= local1 * 20;
			this.loopEnd -= local1 * 20;
		}
		return local1;
	}

@OriginalMember(owner = "client!um", name = "a", descriptor = "(Lclient!fs;II)Lclient!um;")
	public static SoundEffect getSoundEffect(@OriginalArg(0) Js5ResourceProvider resource, @OriginalArg(1) int archiveId, @OriginalArg(2) int fileId) {
		@Pc(5) byte[] local5 = resource.method2104(fileId, archiveId);
		return local5 == null ? null : new SoundEffect(new Packet(local5));
	}
}
