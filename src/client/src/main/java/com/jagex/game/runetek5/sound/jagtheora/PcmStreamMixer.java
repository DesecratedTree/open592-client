package com.jagex.game.runetek5.sound.jagtheora;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.deob.Class183;

import com.jagex.game.runetek5.deob.Class16_Sub2_Sub3;
@OriginalClass("client!jl")
public final class PcmStreamMixer extends PcmStream {

	@OriginalMember(owner = "client!jl", name = "p", descriptor = "Lclient!pk;")
	private final Class183 subStreams = new Class183();

	@OriginalMember(owner = "client!jl", name = "q", descriptor = "Lclient!pk;")
	private final Class183 scheduledEvents = new Class183();

	@OriginalMember(owner = "client!jl", name = "r", descriptor = "I")
	private int nextEventTime = -1;

	@OriginalMember(owner = "client!jl", name = "s", descriptor = "I")
	private int currentPosition = 0;

	@OriginalMember(owner = "client!jl", name = "e", descriptor = "()I")
	public synchronized int count() {
		return this.subStreams.method4148();
	}

	@OriginalMember(owner = "client!jl", name = "a", descriptor = "(Lclient!ti;)V")
	public synchronized void addSubStream(@OriginalArg(0) PcmStream stream) {
		this.subStreams.method4133(stream);
	}

	@OriginalMember(owner = "client!jl", name = "b", descriptor = "(Lclient!ti;)V")
	public synchronized void method2955(@OriginalArg(0) PcmStream arg0) {
		arg0.method5684();
	}

	@OriginalMember(owner = "client!jl", name = "b", descriptor = "()Lclient!ti;")
	@Override
	public PcmStream method4857() {
		return (PcmStream) this.subStreams.method4144();
	}

	@OriginalMember(owner = "client!jl", name = "c", descriptor = "([III)V")
	private void fillFromStreams(@OriginalArg(0) int[] buffer, @OriginalArg(1) int offset, @OriginalArg(2) int length) {
		for (@Pc(5) PcmStream local5 = (PcmStream) this.subStreams.method4140(); local5 != null; local5 = (PcmStream) this.subStreams.method4144()) {
			local5.process(buffer, offset, length);
		}
	}

	@OriginalMember(owner = "client!jl", name = "d", descriptor = "()Lclient!ti;")
	@Override
	public PcmStream method4860() {
		return (PcmStream) this.subStreams.method4140();
	}

	@OriginalMember(owner = "client!jl", name = "a", descriptor = "(Lclient!wr;)V")
	private void removeEvent(@OriginalArg(0) ScheduledAudioEvent event) {
		event.method5684();
		event.onRemove();
		@Pc(9) Country local9 = this.scheduledEvents.aClass4_207.aClass4_262;
		if (local9 == this.scheduledEvents.aClass4_207) {
			this.nextEventTime = -1;
		} else {
			this.nextEventTime = ((ScheduledAudioEvent) local9).scheduledTime;
		}
	}

	@OriginalMember(owner = "client!jl", name = "a", descriptor = "(Lclient!vu;Lclient!wr;)V")
	private void method2958(@OriginalArg(0) Country arg0, @OriginalArg(1) ScheduledAudioEvent arg1) {
		while (arg0 != this.scheduledEvents.aClass4_207 && ((ScheduledAudioEvent) arg0).scheduledTime <= arg1.scheduledTime) {
			arg0 = arg0.aClass4_262;
		}
		Class16_Sub2_Sub3.method3597(arg0, arg1);
		this.nextEventTime = ((ScheduledAudioEvent) this.scheduledEvents.aClass4_207.aClass4_262).scheduledTime;
	}

	@OriginalMember(owner = "client!jl", name = "c", descriptor = "(I)V")
	private void skipAllStreams(@OriginalArg(0) int amount) {
		for (@Pc(5) PcmStream local5 = (PcmStream) this.subStreams.method4140(); local5 != null; local5 = (PcmStream) this.subStreams.method4144()) {
			local5.skip(amount);
		}
	}

	@OriginalMember(owner = "client!jl", name = "f", descriptor = "()V")
	private void updateEventTimings() {
		if (this.currentPosition <= 0) {
			return;
		}
		for (@Pc(8) ScheduledAudioEvent local8 = (ScheduledAudioEvent) this.scheduledEvents.method4140(); local8 != null; local8 = (ScheduledAudioEvent) this.scheduledEvents.method4144()) {
			local8.scheduledTime -= this.currentPosition;
		}
		this.nextEventTime -= this.currentPosition;
		this.currentPosition = 0;
	}

	@OriginalMember(owner = "client!jl", name = "c", descriptor = "()I")
	@Override
	public int getActiveVoiceCount() {
		return 0;
	}

	@OriginalMember(owner = "client!jl", name = "a", descriptor = "(I)V")
	@Override
	public synchronized void skip(@OriginalArg(0) int length) {
		do {
			if (this.nextEventTime < 0) {
				this.skipAllStreams(length);
				return;
			}
			if (this.currentPosition + length < this.nextEventTime) {
				this.currentPosition += length;
				this.skipAllStreams(length);
				return;
			}
			@Pc(29) int local29 = this.nextEventTime - this.currentPosition;
			this.skipAllStreams(local29);
			length -= local29;
			this.currentPosition += local29;
			this.updateEventTimings();
			@Pc(50) ScheduledAudioEvent local50 = (ScheduledAudioEvent) this.scheduledEvents.method4140();
			synchronized (local50) {
				@Pc(58) int local58 = local50.run(this);
				if (local58 < 0) {
					local50.scheduledTime = 0;
					this.removeEvent(local50);
				} else {
					local50.scheduledTime = local58;
					this.method2958(local50.aClass4_262, local50);
				}
			}
		} while (length != 0);
	}

	@OriginalMember(owner = "client!jl", name = "a", descriptor = "([III)V")
	@Override
	public synchronized void fill(@OriginalArg(0) int[] mix, @OriginalArg(1) int offset, @OriginalArg(2) int length) {
		do {
			if (this.nextEventTime < 0) {
				this.fillFromStreams(mix, offset, length);
				return;
			}
			if (this.currentPosition + length < this.nextEventTime) {
				this.currentPosition += length;
				this.fillFromStreams(mix, offset, length);
				return;
			}
			@Pc(33) int local33 = this.nextEventTime - this.currentPosition;
			this.fillFromStreams(mix, offset, local33);
			offset += local33;
			length -= local33;
			this.currentPosition += local33;
			this.updateEventTimings();
			@Pc(60) ScheduledAudioEvent local60 = (ScheduledAudioEvent) this.scheduledEvents.method4140();
			synchronized (local60) {
				@Pc(68) int local68 = local60.run(this);
				if (local68 < 0) {
					local60.scheduledTime = 0;
					this.removeEvent(local60);
				} else {
					local60.scheduledTime = local68;
					this.method2958(local60.aClass4_262, local60);
				}
			}
		} while (length != 0);
	}
}
