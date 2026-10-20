package com.jagex.game.runetek5.config.midi.instrument;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import org.openrs2.deob.annotation.OriginalArg;
import com.jagex.game.runetek5.config.midi.instrument.*;
import com.jagex.game.runetek5.deob.Class101;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.cache.node.VorbisSound;
import com.jagex.game.runetek5.config.midi.instrument.MusicSampleCodebook;
@OriginalClass("client!li")
public final class MusicSampleCodebook {

	@OriginalMember(owner = "client!li", name = "f", descriptor = "[I")
	private int[] huffmanTree;

	@OriginalMember(owner = "client!li", name = "c", descriptor = "I")
	public final int dimensions;

	@OriginalMember(owner = "client!li", name = "b", descriptor = "I")
	private final int entries;

	@OriginalMember(owner = "client!li", name = "d", descriptor = "[I")
	private final int[] lengths;

	@OriginalMember(owner = "client!li", name = "a", descriptor = "[I")
	private int[] mults;

	@OriginalMember(owner = "client!li", name = "e", descriptor = "[[F")
	private float[][] multiplicands;

	@OriginalMember(owner = "client!li", name = "<init>", descriptor = "()V")
	public MusicSampleCodebook() {
		VorbisSound.getBits(24);
		this.dimensions = VorbisSound.getBits(16);
		this.entries = VorbisSound.getBits(24);
		this.lengths = new int[this.entries];
		@Pc(23) boolean local23 = VorbisSound.getBit() != 0;
		@Pc(27) int local27;
		@Pc(32) int local32;
		@Pc(43) int local43;
		if (local23) {
			local27 = 0;
			local32 = VorbisSound.getBits(5) + 1;
			while (local27 < this.entries) {
				@Pc(41) int local41 = VorbisSound.getBits(Class196.method4426(this.entries - local27));
				for (local43 = 0; local43 < local41; local43++) {
					this.lengths[local27++] = local32;
				}
				local32++;
			}
		} else {
			@Pc(66) boolean local66 = VorbisSound.getBit() != 0;
			for (local32 = 0; local32 < this.entries; local32++) {
				if (local66 && VorbisSound.getBit() == 0) {
					this.lengths[local32] = 0;
				} else {
					this.lengths[local32] = VorbisSound.getBits(5) + 1;
				}
			}
		}
		this.computeAcceleratedHuffman();
		local27 = VorbisSound.getBits(4);
		if (local27 > 0) {
			@Pc(103) float local103 = VorbisSound.float32Unpack(VorbisSound.getBits(32));
			@Pc(107) float local107 = VorbisSound.float32Unpack(VorbisSound.getBits(32));
			local43 = VorbisSound.getBits(4) + 1;
			@Pc(118) boolean local118 = VorbisSound.getBit() != 0;
			@Pc(127) int local127;
			if (local27 == 1) {
				local127 = MusicSampleCodebook.lookup1Values(this.entries, this.dimensions);
			} else {
				local127 = this.entries * this.dimensions;
			}
			this.mults = new int[local127];
			for (@Pc(140) int local140 = 0; local140 < local127; local140++) {
				this.mults[local140] = VorbisSound.getBits(local43);
			}
			this.multiplicands = new float[this.entries][this.dimensions];
			@Pc(163) int local163;
			@Pc(166) float local166;
			@Pc(168) int local168;
			@Pc(170) int local170;
			if (local27 == 1) {
				for (local163 = 0; local163 < this.entries; local163++) {
					local166 = 0.0F;
					local168 = 1;
					for (local170 = 0; local170 < this.dimensions; local170++) {
						@Pc(177) int local177 = local163 / local168 % local127;
						@Pc(189) float local189 = (float) this.mults[local177] * local107 + local103 + local166;
						this.multiplicands[local163][local170] = local189;
						if (local118) {
							local166 = local189;
						}
						local168 *= local127;
					}
				}
				return;
			}
			for (local163 = 0; local163 < this.entries; local163++) {
				local166 = 0.0F;
				local168 = local163 * this.dimensions;
				for (local170 = 0; local170 < this.dimensions; local170++) {
					@Pc(240) float local240 = (float) this.mults[local168] * local107 + local103 + local166;
					this.multiplicands[local163][local170] = local240;
					if (local118) {
						local166 = local240;
					}
					local168++;
				}
			}
		}
	}

	@OriginalMember(owner = "client!li", name = "a", descriptor = "()V")
	private void computeAcceleratedHuffman() {
		@Pc(3) int[] local3 = new int[this.entries];
		@Pc(6) int[] local6 = new int[33];
		@Pc(14) int local14;
		@Pc(22) int local22;
		@Pc(26) int local26;
		@Pc(40) int local40;
		@Pc(49) int local49;
		@Pc(54) int local54;
		@Pc(63) int local63;
		for (@Pc(8) int local8 = 0; local8 < this.entries; local8++) {
			local14 = this.lengths[local8];
			if (local14 != 0) {
				local22 = 0x1 << 32 - local14;
				local26 = local6[local14];
				local3[local8] = local26;
				if ((local26 & local22) == 0) {
					local40 = local26 | local22;
					for (local49 = local14 - 1; local49 >= 1; local49--) {
						local54 = local6[local49];
						if (local54 != local26) {
							break;
						}
						local63 = 0x1 << 32 - local49;
						if ((local54 & local63) != 0) {
							local6[local49] = local6[local49 - 1];
							break;
						}
						local6[local49] = local54 | local63;
					}
				} else {
					local40 = local6[local14 - 1];
				}
				local6[local14] = local40;
				for (local49 = local14 + 1; local49 <= 32; local49++) {
					local54 = local6[local49];
					if (local54 == local26) {
						local6[local49] = local40;
					}
				}
			}
		}
		this.huffmanTree = new int[8];
		local14 = 0;
		for (local22 = 0; local22 < this.entries; local22++) {
			local26 = this.lengths[local22];
			if (local26 != 0) {
				local40 = local3[local22];
				local49 = 0;
				for (local54 = 0; local54 < local26; local54++) {
					local63 = Integer.MIN_VALUE >>> local54;
					if ((local40 & local63) == 0) {
						local49++;
					} else {
						if (this.huffmanTree[local49] == 0) {
							this.huffmanTree[local49] = local14;
						}
						local49 = this.huffmanTree[local49];
					}
					if (local49 >= this.huffmanTree.length) {
						@Pc(178) int[] local178 = new int[this.huffmanTree.length * 2];
						for (@Pc(180) int local180 = 0; local180 < this.huffmanTree.length; local180++) {
							local178[local180] = this.huffmanTree[local180];
						}
						this.huffmanTree = local178;
					}
				}
				this.huffmanTree[local49] = ~local22;
				if (local49 >= local14) {
					local14 = local49 + 1;
				}
			}
		}
	}

	@OriginalMember(owner = "client!li", name = "b", descriptor = "()I")
	public int decode() {
		@Pc(1) int local1;
		for (local1 = 0; this.huffmanTree[local1] >= 0; local1 = VorbisSound.getBit() == 0 ? local1 + 1 : this.huffmanTree[local1]) {
		}
		return ~this.huffmanTree[local1];
	}

	@OriginalMember(owner = "client!li", name = "c", descriptor = "()[F")
	public float[] decodeVector() {
		return this.multiplicands[this.decode()];
	}

@OriginalMember(owner = "client!li", name = "a", descriptor = "(II)I")
	public static int lookup1Values(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		@Pc(10) int local10;
		for (local10 = (int) Math.pow((double) arg0, 1.0D / (double) arg1) + 1; Class101.method2678(local10, arg1) > arg0; local10--) {
		}
		return local10;
	}
}
