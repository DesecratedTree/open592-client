package com.jagex.game.runetek5.config.midi.instrument;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.VorbisSound;
@OriginalClass("client!fu")
public final class MusicSamepleMapping {

	@OriginalMember(owner = "client!fu", name = "d", descriptor = "I")
	public final int submaps;

	@OriginalMember(owner = "client!fu", name = "b", descriptor = "I")
	public int mux;

	@OriginalMember(owner = "client!fu", name = "a", descriptor = "[I")
	public final int[] submapFloors;

	@OriginalMember(owner = "client!fu", name = "c", descriptor = "[I")
	public final int[] submapResidues;

	@OriginalMember(owner = "client!fu", name = "<init>", descriptor = "()V")
	public MusicSamepleMapping() {
		VorbisSound.getBits(16);
		this.submaps = VorbisSound.getBit() == 0 ? 1 : VorbisSound.getBits(4) + 1;
		if (VorbisSound.getBit() != 0) {
			VorbisSound.getBits(8);
		}
		VorbisSound.getBits(2);
		if (this.submaps > 1) {
			this.mux = VorbisSound.getBits(4);
		}
		this.submapFloors = new int[this.submaps];
		this.submapResidues = new int[this.submaps];
		for (@Pc(42) int local42 = 0; local42 < this.submaps; local42++) {
			VorbisSound.getBits(8);
			this.submapFloors[local42] = VorbisSound.getBits(8);
			this.submapResidues[local42] = VorbisSound.getBits(8);
		}
	}
}
