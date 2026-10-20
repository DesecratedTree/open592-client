package com.jagex.game.runetek5.sound.jagtheora;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class183;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.sound.jagtheora.*;
import com.jagex.game.runetek5.sound.jagtheora.AreaSoundStream;
import com.jagex.game.runetek5.config.mec.MECType;
@OriginalClass("client!se")
public final class MusicPatchPcmStream extends PcmStream {

	@OriginalMember(owner = "client!se", name = "u", descriptor = "Lclient!pk;")
	public final Class183 queue = new Class183();

	@OriginalMember(owner = "client!se", name = "w", descriptor = "Lclient!jl;")
	public final PcmStreamMixer fadeOutScheduler = new PcmStreamMixer();

	@OriginalMember(owner = "client!se", name = "q", descriptor = "Lclient!dv;")
	private final MidiPcmStream midiPcmStream;

	@OriginalMember(owner = "client!se", name = "<init>", descriptor = "(Lclient!dv;)V")
	public MusicPatchPcmStream(@OriginalArg(0) MidiPcmStream mixBuss) {
		this.midiPcmStream = mixBuss;
	}

	@OriginalMember(owner = "client!se", name = "b", descriptor = "()Lclient!ti;")
	@Override
	public PcmStream method4857() {
		@Pc(9) MusicPatchNode local9;
		do {
			local9 = (MusicPatchNode) this.queue.method4144();
			if (local9 == null) {
				return null;
			}
		} while (local9.stream == null);
		return local9.stream;
	}

	@OriginalMember(owner = "client!se", name = "a", descriptor = "([III)V")
	@Override
	public void fill(@OriginalArg(0) int[] arg0, @OriginalArg(1) int offset, @OriginalArg(2) int length) {
		this.fadeOutScheduler.fill(arg0, offset, length);
		for (@Pc(17) MusicPatchNode local17 = (MusicPatchNode) this.queue.method4140(); local17 != null; local17 = (MusicPatchNode) this.queue.method4144()) {
			if (!this.midiPcmStream.method1430(local17)) {
				@Pc(27) int local27 = offset;
				@Pc(29) int local29 = length;
				do {
					if (local17.tickSampleLength >= local29) {
						this.method4863(arg0, local27, local29, local17, local27 + local29);
						local17.tickSampleLength -= local29;
						break;
					}
					this.method4863(arg0, local27, local17.tickSampleLength, local17, local29 + local27);
					local27 += local17.tickSampleLength;
					local29 -= local17.tickSampleLength;
				} while (!this.midiPcmStream.method1449(local17, arg0, local29, local27));
			}
		}
	}

	@OriginalMember(owner = "client!se", name = "a", descriptor = "(ILclient!lm;I)V")
	private void method4862(@OriginalArg(1) MusicPatchNode arg0, @OriginalArg(2) int arg1) {
		if ((this.midiPcmStream.anIntArray133[arg0.anInt4386] & 0x4) != 0 && arg0.anInt4394 < 0) {
			@Pc(30) int local30 = this.midiPcmStream.anIntArray129[arg0.anInt4386] / MECType.anInt4928;
			@Pc(39) int local39 = (local30 + 1048575 - arg0.retriggerPhase) / local30;
			arg0.retriggerPhase = arg0.retriggerPhase + local30 * arg1 & 0xFFFFF;
			if (local39 <= arg1) {
				if (this.midiPcmStream.anIntArray124[arg0.anInt4386] == 0) {
					arg0.stream = AreaSoundStream.create(arg0.audioFile, arg0.stream.getAbsolutePlaybackRate(), arg0.stream.getTargetVolume(), arg0.stream.getPanPosition());
				} else {
					arg0.stream = AreaSoundStream.create(arg0.audioFile, arg0.stream.getAbsolutePlaybackRate(), 0, arg0.stream.getPanPosition());
					this.midiPcmStream.method1436(arg0.patch.pitchOffset[arg0.anInt4384] < 0, arg0);
				}
				if (arg0.patch.pitchOffset[arg0.anInt4384] < 0) {
					arg0.stream.setNumLoops(-1);
				}
				arg1 = arg0.retriggerPhase / local30;
			}
		}
		arg0.stream.skip(arg1);
	}

	@OriginalMember(owner = "client!se", name = "d", descriptor = "()Lclient!ti;")
	@Override
	public PcmStream method4860() {
		@Pc(9) MusicPatchNode local9 = (MusicPatchNode) this.queue.method4140();
		if (local9 == null) {
			return null;
		} else if (local9.stream == null) {
			return this.method4857();
		} else {
			return local9.stream;
		}
	}

	@OriginalMember(owner = "client!se", name = "a", descriptor = "([IIILclient!lm;II)V")
	private void method4863(@OriginalArg(0) int[] arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) MusicPatchNode arg3, @OriginalArg(4) int arg4) {
		if ((this.midiPcmStream.anIntArray133[arg3.anInt4386] & 0x4) != 0 && arg3.anInt4394 < 0) {
			@Pc(31) int local31 = this.midiPcmStream.anIntArray129[arg3.anInt4386] / MECType.anInt4928;
			while (true) {
				@Pc(42) int local42 = (local31 + 1048575 - arg3.retriggerPhase) / local31;
				if (local42 > arg2) {
					arg3.retriggerPhase += arg2 * local31;
					break;
				}
				arg3.stream.fill(arg0, arg1, local42);
				arg2 -= local42;
				arg3.retriggerPhase += local31 * local42 - 1048576;
				arg1 += local42;
				@Pc(73) int local73 = MECType.anInt4928 / 100;
				@Pc(77) int local77 = 262144 / local31;
				if (local77 < local73) {
					local73 = local77;
				}
				@Pc(85) AreaSoundStream local85 = arg3.stream;
				if (this.midiPcmStream.anIntArray124[arg3.anInt4386] == 0) {
					arg3.stream = AreaSoundStream.create(arg3.audioFile, local85.getAbsolutePlaybackRate(), local85.getTargetVolume(), local85.getPanPosition());
				} else {
					arg3.stream = AreaSoundStream.create(arg3.audioFile, local85.getAbsolutePlaybackRate(), 0, local85.getPanPosition());
					this.midiPcmStream.method1436(arg3.patch.pitchOffset[arg3.anInt4384] < 0, arg3);
					arg3.stream.fadeToVolume(local73, local85.getTargetVolume());
				}
				if (arg3.patch.pitchOffset[arg3.anInt4384] < 0) {
					arg3.stream.setNumLoops(-1);
				}
				local85.fadeOutAndStop(local73);
				local85.fill(arg0, arg1, arg4 - arg1);
				if (local85.isFading()) {
					this.fadeOutScheduler.addSubStream(local85);
				}
			}
		}
		arg3.stream.fill(arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!se", name = "a", descriptor = "(I)V")
	@Override
	public void skip(@OriginalArg(0) int length) {
		this.fadeOutScheduler.skip(length);
		for (@Pc(15) MusicPatchNode local15 = (MusicPatchNode) this.queue.method4140(); local15 != null; local15 = (MusicPatchNode) this.queue.method4144()) {
			if (!this.midiPcmStream.method1430(local15)) {
				@Pc(25) int local25 = length;
				do {
					if (local25 <= local15.tickSampleLength) {
						this.method4862(local15, local25);
						local15.tickSampleLength -= local25;
						break;
					}
					this.method4862(local15, local15.tickSampleLength);
					local25 -= local15.tickSampleLength;
				} while (!this.midiPcmStream.method1449(local15, null, local25, 0));
			}
		}
	}

	@OriginalMember(owner = "client!se", name = "c", descriptor = "()I")
	@Override
	public int getActiveVoiceCount() {
		return 0;
	}

@OriginalMember(owner = "client!se", name = "s", descriptor = "I")
	public static int anInt6251 = 0;
}
