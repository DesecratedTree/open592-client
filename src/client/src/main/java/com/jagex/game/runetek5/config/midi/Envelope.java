package com.jagex.game.runetek5.config.midi;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;

@OriginalClass("client!ej")
public final class Envelope {

	@OriginalMember(owner = "client!ej", name = "b", descriptor = "I")
	public int form;

	@OriginalMember(owner = "client!ej", name = "c", descriptor = "I")
	public int start;

	@OriginalMember(owner = "client!ej", name = "d", descriptor = "I")
	public int end;

	@OriginalMember(owner = "client!ej", name = "g", descriptor = "I")
	private int phaseIndex;

	@OriginalMember(owner = "client!ej", name = "h", descriptor = "I")
	private int critical;

	@OriginalMember(owner = "client!ej", name = "i", descriptor = "I")
	private int amplitude;

	@OriginalMember(owner = "client!ej", name = "j", descriptor = "I")
	private int step;

	@OriginalMember(owner = "client!ej", name = "k", descriptor = "I")
	private int ticks;

	@OriginalMember(owner = "client!ej", name = "e", descriptor = "I")
	private int numPhases = 2;

	@OriginalMember(owner = "client!ej", name = "a", descriptor = "[I")
	private int[] phaseDuration = new int[2];

	@OriginalMember(owner = "client!ej", name = "f", descriptor = "[I")
	private int[] phasePeak = new int[2];

	@OriginalMember(owner = "client!ej", name = "<init>", descriptor = "()V")
	public Envelope() {
		this.phaseDuration[0] = 0;
		this.phaseDuration[1] = 65535;
		this.phasePeak[0] = 0;
		this.phasePeak[1] = 65535;
	}

	@OriginalMember(owner = "client!ej", name = "a", descriptor = "()V")
	public void reset() {
		this.critical = 0;
		this.phaseIndex = 0;
		this.step = 0;
		this.amplitude = 0;
		this.ticks = 0;
	}

	@OriginalMember(owner = "client!ej", name = "a", descriptor = "(Lclient!iv;)V")
	public void decode(@OriginalArg(0) Packet packet) {
		this.form = packet.method2490();
		this.start = packet.method2529();
		this.end = packet.method2529();
		this.decodeShape(packet);
	}

	@OriginalMember(owner = "client!ej", name = "a", descriptor = "(I)I")
	public int step(@OriginalArg(0) int period) {
		if (this.ticks >= this.critical) {
			this.amplitude = this.phasePeak[this.phaseIndex++] << 15;
			if (this.phaseIndex >= this.numPhases) {
				this.phaseIndex = this.numPhases - 1;
			}
			this.critical = (int) ((double) this.phaseDuration[this.phaseIndex] / 65536.0D * (double) period);
			if (this.critical > this.ticks) {
				this.step = ((this.phasePeak[this.phaseIndex] << 15) - this.amplitude) / (this.critical - this.ticks);
			}
		}
		this.amplitude += this.step;
		this.ticks++;
		return this.amplitude - this.step >> 15;
	}

	@OriginalMember(owner = "client!ej", name = "b", descriptor = "(Lclient!iv;)V")
	public void decodeShape(@OriginalArg(0) Packet packet) {
		this.numPhases = packet.method2490();
		this.phaseDuration = new int[this.numPhases];
		this.phasePeak = new int[this.numPhases];
		for (@Pc(16) int local16 = 0; local16 < this.numPhases; local16++) {
			this.phaseDuration[local16] = packet.method2536();
			this.phasePeak[local16] = packet.method2536();
		}
	}
}
