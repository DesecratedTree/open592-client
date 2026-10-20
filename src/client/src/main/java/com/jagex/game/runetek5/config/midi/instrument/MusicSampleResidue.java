package com.jagex.game.runetek5.config.midi.instrument;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.VorbisSound;
@OriginalClass("client!qs")
public final class MusicSampleResidue {

	@OriginalMember(owner = "client!qs", name = "c", descriptor = "I")
	private final int residueType = VorbisSound.getBits(16);

	@OriginalMember(owner = "client!qs", name = "d", descriptor = "I")
	private final int residueBegin = VorbisSound.getBits(24);

	@OriginalMember(owner = "client!qs", name = "g", descriptor = "I")
	private final int residueEnd = VorbisSound.getBits(24);

	@OriginalMember(owner = "client!qs", name = "e", descriptor = "I")
	private final int partitionSize = VorbisSound.getBits(24) + 1;

	@OriginalMember(owner = "client!qs", name = "b", descriptor = "I")
	private final int classifications = VorbisSound.getBits(6) + 1;

	@OriginalMember(owner = "client!qs", name = "a", descriptor = "I")
	private final int classbook = VorbisSound.getBits(8);

	@OriginalMember(owner = "client!qs", name = "f", descriptor = "[I")
	private final int[] residueBooks;

	@OriginalMember(owner = "client!qs", name = "<init>", descriptor = "()V")
	public MusicSampleResidue() {
		@Pc(33) int[] local33 = new int[this.classifications];
		@Pc(38) int local38;
		for (@Pc(35) int local35 = 0; local35 < this.classifications; local35++) {
			local38 = 0;
			@Pc(41) int local41 = VorbisSound.getBits(3);
			@Pc(47) boolean local47 = VorbisSound.getBit() != 0;
			if (local47) {
				local38 = VorbisSound.getBits(5);
			}
			local33[local35] = local38 << 3 | local41;
		}
		this.residueBooks = new int[this.classifications * 8];
		for (local38 = 0; local38 < this.classifications * 8; local38++) {
			this.residueBooks[local38] = (local33[local38 >> 3] & 0x1 << (local38 & 0x7)) == 0 ? -1 : VorbisSound.getBits(8);
		}
	}

	@OriginalMember(owner = "client!qs", name = "a", descriptor = "([FIZ)V")
	public void decode(@OriginalArg(0) float[] v, @OriginalArg(1) int arg1, @OriginalArg(2) boolean decode) {
		for (@Pc(1) int local1 = 0; local1 < arg1; local1++) {
			v[local1] = 0.0F;
		}
		if (decode) {
			return;
		}
		@Pc(19) int local19 = VorbisSound.codebooks[this.classbook].dimensions;
		@Pc(25) int local25 = this.residueEnd - this.residueBegin;
		@Pc(30) int local30 = local25 / this.partitionSize;
		@Pc(33) int[] local33 = new int[local30];
		for (@Pc(35) int local35 = 0; local35 < 8; local35++) {
			@Pc(38) int local38 = 0;
			while (local38 < local30) {
				@Pc(47) int local47;
				@Pc(51) int local51;
				if (local35 == 0) {
					local47 = VorbisSound.codebooks[this.classbook].decode();
					for (local51 = local19 - 1; local51 >= 0; local51--) {
						if (local38 + local51 < local30) {
							local33[local38 + local51] = local47 % this.classifications;
						}
						local47 /= this.classifications;
					}
				}
				for (local47 = 0; local47 < local19; local47++) {
					local51 = local33[local38];
					@Pc(90) int local90 = this.residueBooks[local51 * 8 + local35];
					if (local90 >= 0) {
						@Pc(100) int local100 = this.residueBegin + local38 * this.partitionSize;
						@Pc(104) MusicSampleCodebook local104 = VorbisSound.codebooks[local90];
						@Pc(113) int local113;
						if (this.residueType == 0) {
							local113 = this.partitionSize / local104.dimensions;
							for (@Pc(115) int local115 = 0; local115 < local113; local115++) {
								@Pc(119) float[] local119 = local104.decodeVector();
								for (@Pc(121) int local121 = 0; local121 < local104.dimensions; local121++) {
									v[local100 + local115 + local121 * local113] += local119[local121];
								}
							}
						} else {
							local113 = 0;
							while (local113 < this.partitionSize) {
								@Pc(153) float[] local153 = local104.decodeVector();
								for (@Pc(155) int local155 = 0; local155 < local104.dimensions; local155++) {
									v[local100 + local113] += local153[local155];
									local113++;
								}
							}
						}
					}
					local38++;
					if (local38 >= local30) {
						break;
					}
				}
			}
		}
	}
}
