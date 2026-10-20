package com.jagex.game.runetek5.cache.node;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.impl.VariableRateSoundPacket;
import com.jagex.game.runetek5.config.midi.instrument.MusicSamepleMapping;
import com.jagex.game.runetek5.config.midi.instrument.MusicSampleResidue;
import com.jagex.game.runetek5.config.midi.instrument.MusicSampleCodebook;
import com.jagex.game.runetek5.config.midi.instrument.MusicSampleFloor;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.cache.node.*;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.cache.node.VorbisSound;
@OriginalClass("client!di")
public final class VorbisSound extends Country {

	@OriginalMember(owner = "client!di", name = "m", descriptor = "[[B")
	private byte[][] packets;

	@OriginalMember(owner = "client!di", name = "o", descriptor = "[F")
	private float[] previousWindow;

	@OriginalMember(owner = "client!di", name = "r", descriptor = "Z")
	private boolean previousNoDecodeFloor;

	@OriginalMember(owner = "client!di", name = "s", descriptor = "I")
	private int previousWindowEnd;

	@OriginalMember(owner = "client!di", name = "y", descriptor = "I")
	private int nominalBitRate;

	@OriginalMember(owner = "client!di", name = "z", descriptor = "I")
	private int minBitRate;

	@OriginalMember(owner = "client!di", name = "I", descriptor = "I")
	private int previousWindowSize;

	@OriginalMember(owner = "client!di", name = "K", descriptor = "I")
	private int maxBitRate;

	@OriginalMember(owner = "client!di", name = "N", descriptor = "I")
	private int sampleRate;

	@OriginalMember(owner = "client!di", name = "O", descriptor = "Z")
	private boolean negativeBitRate;

	@OriginalMember(owner = "client!di", name = "P", descriptor = "[B")
	private byte[] packetData;

	@OriginalMember(owner = "client!di", name = "Q", descriptor = "I")
	private int currentPacket;

	@OriginalMember(owner = "client!di", name = "R", descriptor = "I")
	private int currentOff;

	@OriginalMember(owner = "client!di", name = "<init>", descriptor = "([B)V")
	public VorbisSound(@OriginalArg(0) byte[] bytes) {
		this.decode(bytes);
	}

	@OriginalMember(owner = "client!di", name = "b", descriptor = "([B)V")
	private void decode(@OriginalArg(0) byte[] data) {
		@Pc(4) Packet local4 = new Packet(data);
		this.sampleRate = local4.method2529();
		this.maxBitRate = local4.method2529();
		this.nominalBitRate = local4.method2529();
		this.minBitRate = local4.method2529();
		if (this.minBitRate < 0) {
			this.minBitRate = ~this.minBitRate;
			this.negativeBitRate = true;
		}
		@Pc(40) int local40 = local4.method2529();
		this.packets = new byte[local40][];
		for (@Pc(46) int local46 = 0; local46 < local40; local46++) {
			@Pc(49) int local49 = 0;
			@Pc(53) int local53;
			do {
				local53 = local4.method2490();
				local49 += local53;
			} while (local53 >= 255);
			@Pc(64) byte[] local64 = new byte[local49];
			local4.method2508(local49, local64);
			this.packets[local46] = local64;
		}
	}

	@OriginalMember(owner = "client!di", name = "a", descriptor = "([I)Lclient!js;")
	public VariableRateSoundPacket toPcmSound(@OriginalArg(0) int[] sampleBudget) {
		if (sampleBudget != null && sampleBudget[0] <= 0) {
			return null;
		}
		if (this.packetData == null) {
			this.previousWindowSize = 0;
			this.previousWindow = new float[VorbisSound.blockSize1];
			this.packetData = new byte[this.maxBitRate];
			this.currentOff = 0;
			this.currentPacket = 0;
		}
		while (this.currentPacket < this.packets.length) {
			if (sampleBudget != null && sampleBudget[0] <= 0) {
				return null;
			}
			@Pc(42) float[] local42 = this.decode(this.currentPacket);
			if (local42 != null) {
				@Pc(47) int local47 = this.currentOff;
				@Pc(50) int local50 = local42.length;
				if (local50 > this.maxBitRate - local47) {
					local50 = this.maxBitRate - local47;
				}
				for (@Pc(63) int local63 = 0; local63 < local50; local63++) {
					@Pc(73) int local73 = (int) (local42[local63] * 128.0F + 128.0F);
					if ((local73 & 0xFFFFFF00) != 0) {
						local73 = ~local73 >> 31;
					}
					this.packetData[local47++] = (byte) (local73 - 128);
				}
				if (sampleBudget != null) {
					sampleBudget[0] -= local47 - this.currentOff;
				}
				this.currentOff = local47;
			}
			this.currentPacket++;
		}
		this.previousWindow = null;
		@Pc(129) byte[] local129 = this.packetData;
		this.packetData = null;
		return new VariableRateSoundPacket(this.sampleRate, local129, this.nominalBitRate, this.minBitRate, this.negativeBitRate);
	}

	@OriginalMember(owner = "client!di", name = "d", descriptor = "(I)[F")
	private float[] decode(@OriginalArg(0) int packet) {
		VorbisSound.setData(this.packets[packet]);
		VorbisSound.getBit();
		@Pc(15) int local15 = VorbisSound.getBits(Class196.method4426(VorbisSound.modes.length - 1));
		@Pc(19) boolean local19 = VorbisSound.noResidues[local15];
		@Pc(25) int local25 = local19 ? VorbisSound.blockSize1 : VorbisSound.blockSize0;
		@Pc(27) boolean local27 = false;
		@Pc(29) boolean local29 = false;
		if (local19) {
			local27 = VorbisSound.getBit() != 0;
			local29 = VorbisSound.getBit() != 0;
		}
		@Pc(47) int local47 = local25 >> 1;
		@Pc(59) int local59;
		@Pc(67) int local67;
		@Pc(71) int local71;
		if (local19 && !local27) {
			local59 = (local25 >> 2) - (VorbisSound.blockSize0 >> 2);
			local67 = (local25 >> 2) + (VorbisSound.blockSize0 >> 2);
			local71 = VorbisSound.blockSize0 >> 1;
		} else {
			local59 = 0;
			local67 = local47;
			local71 = local25 >> 1;
		}
		@Pc(94) int local94;
		@Pc(104) int local104;
		@Pc(108) int local108;
		if (local19 && !local29) {
			local94 = local25 - (local25 >> 2) - (VorbisSound.blockSize0 >> 2);
			local104 = local25 + (VorbisSound.blockSize0 >> 2) - (local25 >> 2);
			local108 = VorbisSound.blockSize0 >> 1;
		} else {
			local94 = local47;
			local104 = local25;
			local108 = local25 >> 1;
		}
		@Pc(123) MusicSamepleMapping local123 = VorbisSound.mappings[VorbisSound.modes[local15]];
		@Pc(126) int local126 = local123.mux;
		@Pc(131) int local131 = local123.submapFloors[local126];
		@Pc(140) boolean local140 = !VorbisSound.floors[local131].decode();
		@Pc(154) float[] local154;
		for (@Pc(144) int local144 = 0; local144 < local123.submaps; local144++) {
			@Pc(152) MusicSampleResidue local152 = VorbisSound.residues[local123.submapResidues[local144]];
			local154 = VorbisSound.window;
			local152.decode(local154, local25 >> 1, local140);
		}
		@Pc(171) int local171;
		@Pc(176) int local176;
		if (!local140) {
			local171 = local123.mux;
			local176 = local123.submapFloors[local171];
			VorbisSound.floors[local176].computeFloor(VorbisSound.window, local25 >> 1);
		}
		@Pc(212) int local212;
		if (local140) {
			for (local171 = local25 >> 1; local171 < local25; local171++) {
				VorbisSound.window[local171] = 0.0F;
			}
		} else {
			local171 = local25 >> 1;
			local176 = local25 >> 2;
			local212 = local25 >> 3;
			@Pc(214) float[] local214 = VorbisSound.window;
			for (@Pc(216) int local216 = 0; local216 < local171; local216++) {
				local214[local216] *= 0.5F;
			}
			for (@Pc(230) int local230 = local171; local230 < local25; local230++) {
				local214[local230] = -local214[local25 - local230 - 1];
			}
			@Pc(252) float[] local252 = local19 ? VorbisSound.A_1 : VorbisSound.A_0;
			@Pc(258) float[] local258 = local19 ? VorbisSound.B_1 : VorbisSound.B_0;
			@Pc(264) float[] local264 = local19 ? VorbisSound.C_1 : VorbisSound.C_0;
			@Pc(270) int[] local270 = local19 ? VorbisSound.BITREVERSE_1 : VorbisSound.BITREVERSE_0;
			@Pc(307) float local307;
			@Pc(313) float local313;
			@Pc(321) float local321;
			for (@Pc(272) int local272 = 0; local272 < local176; local272++) {
				@Pc(289) float local289 = local214[local272 * 4] - local214[local25 - local272 * 4 - 1];
				local307 = local214[local272 * 4 + 2] - local214[local25 - local272 * 4 - 3];
				local313 = local252[local272 * 2];
				local321 = local252[local272 * 2 + 1];
				local214[local25 - local272 * 4 - 1] = local289 * local313 - local307 * local321;
				local214[local25 - local272 * 4 - 3] = local289 * local321 + local307 * local313;
			}
			for (@Pc(359) int local359 = 0; local359 < local212; local359++) {
				local307 = local214[local171 + local359 * 4 + 3];
				local313 = local214[local171 + local359 * 4 + 1];
				local321 = local214[local359 * 4 + 3];
				@Pc(396) float local396 = local214[local359 * 4 + 1];
				local214[local171 + local359 * 4 + 3] = local307 + local321;
				local214[local171 + local359 * 4 + 1] = local313 + local396;
				@Pc(430) float local430 = local252[local171 - local359 * 4 - 4];
				@Pc(440) float local440 = local252[local171 - local359 * 4 - 3];
				local214[local359 * 4 + 3] = (local307 - local321) * local430 - (local313 - local396) * local440;
				local214[local359 * 4 + 1] = (local313 - local396) * local430 + (local307 - local321) * local440;
			}
			@Pc(486) int local486 = Class196.method4426(local25 - 1);
			@Pc(520) int local520;
			@Pc(522) int local522;
			@Pc(527) int local527;
			@Pc(535) float local535;
			@Pc(543) float local543;
			@Pc(551) float local551;
			@Pc(559) float local559;
			@Pc(585) float local585;
			@Pc(495) int local495;
			@Pc(499) int local499;
			@Pc(501) int local501;
			@Pc(510) int local510;
			for (@Pc(488) int local488 = 0; local488 < local486 - 3; local488++) {
				local495 = local25 >> local488 + 2;
				local499 = 0x8 << local488;
				for (local501 = 0; local501 < 0x2 << local488; local501++) {
					local510 = local25 - local495 * 2 * local501;
					local520 = local25 - local495 * (local501 * 2 + 1);
					for (local522 = 0; local522 < local25 >> local488 + 4; local522++) {
						local527 = local522 * 4;
						local535 = local214[local510 - local527 - 1];
						local543 = local214[local510 - local527 - 3];
						local551 = local214[local520 - local527 - 1];
						local559 = local214[local520 - local527 - 3];
						local214[local510 - local527 - 1] = local535 + local551;
						local214[local510 - local527 - 3] = local543 + local559;
						local585 = local252[local522 * local499];
						@Pc(593) float local593 = local252[local522 * local499 + 1];
						local214[local520 - local527 - 1] = (local535 - local551) * local585 - (local543 - local559) * local593;
						local214[local520 - local527 - 3] = (local543 - local559) * local585 + (local535 - local551) * local593;
					}
				}
			}
			@Pc(673) float local673;
			for (local495 = 1; local495 < local212 - 1; local495++) {
				local499 = local270[local495];
				if (local495 < local499) {
					local501 = local495 * 8;
					local510 = local499 * 8;
					local673 = local214[local501 + 1];
					local214[local501 + 1] = local214[local510 + 1];
					local214[local510 + 1] = local673;
					@Pc(695) float local695 = local214[local501 + 3];
					local214[local501 + 3] = local214[local510 + 3];
					local214[local510 + 3] = local695;
					@Pc(717) float local717 = local214[local501 + 5];
					local214[local501 + 5] = local214[local510 + 5];
					local214[local510 + 5] = local717;
					@Pc(739) float local739 = local214[local501 + 7];
					local214[local501 + 7] = local214[local510 + 7];
					local214[local510 + 7] = local739;
				}
			}
			for (local499 = 0; local499 < local171; local499++) {
				local214[local499] = local214[local499 * 2 + 1];
			}
			for (local501 = 0; local501 < local212; local501++) {
				local214[local25 - local501 * 2 - 1] = local214[local501 * 4];
				local214[local25 - local501 * 2 - 2] = local214[local501 * 4 + 1];
				local214[local25 - local176 - local501 * 2 - 1] = local214[local501 * 4 + 2];
				local214[local25 - local176 - local501 * 2 - 2] = local214[local501 * 4 + 3];
			}
			for (local510 = 0; local510 < local212; local510++) {
				local673 = local264[local510 * 2];
				@Pc(868) float local868 = local264[local510 * 2 + 1];
				@Pc(876) float local876 = local214[local171 + local510 * 2];
				local535 = local214[local171 + local510 * 2 + 1];
				local543 = local214[local25 - local510 * 2 - 2];
				local551 = local214[local25 - local510 * 2 - 1];
				local559 = local868 * (local876 - local543) + local673 * (local535 + local551);
				local214[local171 + local510 * 2] = (local876 + local543 + local559) * 0.5F;
				local214[local25 - local510 * 2 - 2] = (local876 + local543 - local559) * 0.5F;
				local559 = local868 * (local535 + local551) - local673 * (local876 - local543);
				local214[local171 + local510 * 2 + 1] = (local535 + local559 - local551) * 0.5F;
				local214[local25 - local510 * 2 - 1] = (local551 + local559 - local535) * 0.5F;
			}
			for (local520 = 0; local520 < local176; local520++) {
				local214[local520] = local214[local520 * 2 + local171] * local258[local520 * 2] + local214[local520 * 2 + local171 + 1] * local258[local520 * 2 + 1];
				local214[local171 - local520 - 1] = local214[local520 * 2 + local171] * local258[local520 * 2 + 1] - local214[local520 * 2 + local171 + 1] * local258[local520 * 2];
			}
			for (local522 = 0; local522 < local176; local522++) {
				local214[local25 + local522 - local176] = -local214[local522];
			}
			for (local527 = 0; local527 < local176; local527++) {
				local214[local527] = local214[local176 + local527];
			}
			for (@Pc(1111) int local1111 = 0; local1111 < local176; local1111++) {
				local214[local176 + local1111] = -local214[local176 - local1111 - 1];
			}
			for (@Pc(1131) int local1131 = 0; local1131 < local176; local1131++) {
				local214[local171 + local1131] = local214[local25 - local1131 - 1];
			}
			for (@Pc(1150) int local1150 = local59; local1150 < local67; local1150++) {
				local559 = (float) Math.sin(((double) (local1150 - local59) + 0.5D) / (double) local71 * 0.5D * 3.141592653589793D);
				VorbisSound.window[local1150] *= (float) Math.sin((double) local559 * 1.5707963267948966D * (double) local559);
			}
			for (@Pc(1188) int local1188 = local94; local1188 < local104; local1188++) {
				local585 = (float) Math.sin(((double) (local1188 - local94) + 0.5D) / (double) local108 * 0.5D * 3.141592653589793D + 1.5707963267948966D);
				VorbisSound.window[local1188] *= (float) Math.sin((double) local585 * 1.5707963267948966D * (double) local585);
			}
		}
		@Pc(1228) float[] local1228 = null;
		if (this.previousWindowSize > 0) {
			local176 = this.previousWindowSize + local25 >> 2;
			local1228 = new float[local176];
			@Pc(1254) int local1254;
			if (!this.previousNoDecodeFloor) {
				for (local212 = 0; local212 < this.previousWindowEnd; local212++) {
					local1254 = (this.previousWindowSize >> 1) + local212;
					local1228[local212] += this.previousWindow[local1254];
				}
			}
			if (!local140) {
				for (local212 = local59; local212 < local25 >> 1; local212++) {
					local1254 = local1228.length + local212 - (local25 >> 1);
					local1228[local1254] += VorbisSound.window[local212];
				}
			}
		}
		local154 = this.previousWindow;
		this.previousWindow = VorbisSound.window;
		VorbisSound.window = local154;
		this.previousWindowSize = local25;
		this.previousWindowEnd = local104 - (local25 >> 1);
		this.previousNoDecodeFloor = local140;
		return local1228;
	}

@OriginalMember(owner = "client!di", name = "l", descriptor = "[Lclient!li;")
	public static MusicSampleCodebook[] codebooks;
@OriginalMember(owner = "client!di", name = "p", descriptor = "[F")
	public static float[] C_0;
@OriginalMember(owner = "client!di", name = "q", descriptor = "I")
	public static int blockSize1;
@OriginalMember(owner = "client!di", name = "t", descriptor = "[B")
	private static byte[] instrumentData;
@OriginalMember(owner = "client!di", name = "u", descriptor = "[I")
	public static int[] BITREVERSE_0;
@OriginalMember(owner = "client!di", name = "v", descriptor = "I")
	private static int bitPos;
@OriginalMember(owner = "client!di", name = "w", descriptor = "I")
	private static int pos;
@OriginalMember(owner = "client!di", name = "x", descriptor = "[F")
	public static float[] B_1;
@OriginalMember(owner = "client!di", name = "A", descriptor = "[F")
	public static float[] window;
@OriginalMember(owner = "client!di", name = "B", descriptor = "[Z")
	public static boolean[] noResidues;
@OriginalMember(owner = "client!di", name = "C", descriptor = "[F")
	public static float[] A_0;
@OriginalMember(owner = "client!di", name = "D", descriptor = "[F")
	public static float[] B_0;
@OriginalMember(owner = "client!di", name = "E", descriptor = "[I")
	public static int[] BITREVERSE_1;
@OriginalMember(owner = "client!di", name = "F", descriptor = "[I")
	public static int[] modes;
@OriginalMember(owner = "client!di", name = "G", descriptor = "[Lclient!fu;")
	public static MusicSamepleMapping[] mappings;
@OriginalMember(owner = "client!di", name = "H", descriptor = "[F")
	public static float[] A_1;
@OriginalMember(owner = "client!di", name = "J", descriptor = "[F")
	public static float[] C_1;
@OriginalMember(owner = "client!di", name = "L", descriptor = "I")
	public static int blockSize0;
@OriginalMember(owner = "client!di", name = "M", descriptor = "[Lclient!vc;")
	public static MusicSampleFloor[] floors;
@OriginalMember(owner = "client!di", name = "S", descriptor = "[Lclient!qs;")
	public static MusicSampleResidue[] residues;
@OriginalMember(owner = "client!di", name = "n", descriptor = "Z")
	private static boolean fullyCached = false;
@OriginalMember(owner = "client!di", name = "a", descriptor = "(I)I")
	public static int getBits(@OriginalArg(0) int bits) {
		@Pc(1) int local1 = 0;
		@Pc(3) int local3 = 0;
		@Pc(8) int local8;
		while (bits >= 8 - bitPos) {
			local8 = 8 - bitPos;
			@Pc(14) int local14 = (0x1 << local8) - 1;
			local1 += (instrumentData[pos] >> bitPos & local14) << local3;
			bitPos = 0;
			pos++;
			local3 += local8;
			bits -= local8;
		}
		if (bits > 0) {
			local8 = (0x1 << bits) - 1;
			local1 += (instrumentData[pos] >> bitPos & local8) << local3;
			bitPos += bits;
		}
		return local1;
	}
@OriginalMember(owner = "client!di", name = "a", descriptor = "([BI)V")
	public static void setData(@OriginalArg(0) byte[] data) {
		instrumentData = data;
		pos = 0;
		bitPos = 0;
	}
@OriginalMember(owner = "client!di", name = "a", descriptor = "(Lclient!fs;I)Lclient!di;")
	public static VorbisSound method1316(@OriginalArg(0) Js5ResourceProvider arg0, @OriginalArg(1) int arg1) {
		if (method1320(arg0)) {
			@Pc(14) byte[] local14 = arg0.method2122(arg1);
			return local14 == null ? null : new VorbisSound(local14);
		} else {
			arg0.method2125(arg1);
			return null;
		}
	}
@OriginalMember(owner = "client!di", name = "a", descriptor = "()I")
	public static int getBit() {
		@Pc(7) int local7 = instrumentData[pos] >> bitPos & 0x1;
		bitPos++;
		pos += bitPos >> 3;
		bitPos &= 0x7;
		return local7;
	}
@OriginalMember(owner = "client!di", name = "a", descriptor = "(Lclient!fs;II)Lclient!di;")
	public static VorbisSound create(@OriginalArg(0) Js5ResourceProvider vorbis, @OriginalArg(1) int archiveId, @OriginalArg(2) int fileId) {
		if (method1320(vorbis)) {
			@Pc(16) byte[] local16 = vorbis.method2104(fileId, archiveId);
			return local16 == null ? null : new VorbisSound(local16);
		} else {
			vorbis.method2098(fileId, archiveId);
			return null;
		}
	}
@OriginalMember(owner = "client!di", name = "a", descriptor = "([B)V")
	private static void decodeHeader(@OriginalArg(0) byte[] header) {
		setData(header);
		blockSize0 = 0x1 << getBits(4);
		blockSize1 = 0x1 << getBits(4);
		window = new float[blockSize1];
		@Pc(24) int local24;
		@Pc(28) int local28;
		@Pc(32) int local32;
		@Pc(36) int local36;
		@Pc(41) int local41;
		@Pc(86) int local86;
		@Pc(138) int local138;
		@Pc(191) int local191;
		for (@Pc(17) int local17 = 0; local17 < 2; local17++) {
			local24 = local17 == 0 ? blockSize0 : blockSize1;
			local28 = local24 >> 1;
			local32 = local24 >> 2;
			local36 = local24 >> 3;
			@Pc(39) float[] local39 = new float[local28];
			for (local41 = 0; local41 < local32; local41++) {
				local39[local41 * 2] = (float) Math.cos((double) (local41 * 4) * 3.141592653589793D / (double) local24);
				local39[local41 * 2 + 1] = -((float) Math.sin((double) (local41 * 4) * 3.141592653589793D / (double) local24));
			}
			@Pc(84) float[] local84 = new float[local28];
			for (local86 = 0; local86 < local32; local86++) {
				local84[local86 * 2] = (float) Math.cos((double) (local86 * 2 + 1) * 3.141592653589793D / (double) (local24 * 2));
				local84[local86 * 2 + 1] = (float) Math.sin((double) (local86 * 2 + 1) * 3.141592653589793D / (double) (local24 * 2));
			}
			@Pc(136) float[] local136 = new float[local32];
			for (local138 = 0; local138 < local36; local138++) {
				local136[local138 * 2] = (float) Math.cos((double) (local138 * 4 + 2) * 3.141592653589793D / (double) local24);
				local136[local138 * 2 + 1] = -((float) Math.sin((double) (local138 * 4 + 2) * 3.141592653589793D / (double) local24));
			}
			@Pc(185) int[] local185 = new int[local36];
			local191 = Class196.method4426(local36 - 1);
			for (@Pc(193) int local193 = 0; local193 < local36; local193++) {
				local185[local193] = OpenGLTextureCache.method1546(local193, local191);
			}
			if (local17 == 0) {
				A_0 = local39;
				B_0 = local84;
				C_0 = local136;
				BITREVERSE_0 = local185;
			} else {
				A_1 = local39;
				B_1 = local84;
				C_1 = local136;
				BITREVERSE_1 = local185;
			}
		}
		local24 = getBits(8) + 1;
		codebooks = new MusicSampleCodebook[local24];
		for (local28 = 0; local28 < local24; local28++) {
			codebooks[local28] = new MusicSampleCodebook();
		}
		local32 = getBits(6) + 1;
		for (local36 = 0; local36 < local32; local36++) {
			getBits(16);
		}
		@Pc(269) int local269 = getBits(6) + 1;
		floors = new MusicSampleFloor[local269];
		for (local41 = 0; local41 < local269; local41++) {
			floors[local41] = new MusicSampleFloor();
		}
		@Pc(290) int local290 = getBits(6) + 1;
		residues = new MusicSampleResidue[local290];
		for (local86 = 0; local86 < local290; local86++) {
			residues[local86] = new MusicSampleResidue();
		}
		@Pc(311) int local311 = getBits(6) + 1;
		mappings = new MusicSamepleMapping[local311];
		for (local138 = 0; local138 < local311; local138++) {
			mappings[local138] = new MusicSamepleMapping();
		}
		@Pc(332) int local332 = getBits(6) + 1;
		noResidues = new boolean[local332];
		modes = new int[local332];
		for (local191 = 0; local191 < local332; local191++) {
			noResidues[local191] = getBit() != 0;
			getBits(16);
			getBits(16);
			modes[local191] = getBits(8);
		}
	}
@OriginalMember(owner = "client!di", name = "a", descriptor = "(Lclient!fs;)Z")
	private static boolean method1320(@OriginalArg(0) Js5ResourceProvider arg0) {
		if (!fullyCached) {
			@Pc(7) byte[] local7 = arg0.method2104(0, 0);
			if (local7 == null) {
				return false;
			}
			decodeHeader(local7);
			fullyCached = true;
		}
		return true;
	}
@OriginalMember(owner = "client!di", name = "c", descriptor = "(I)F")
	public static float float32Unpack(@OriginalArg(0) int v) {
		@Pc(3) int local3 = v & 0x1FFFFF;
		@Pc(7) int local7 = v & Integer.MIN_VALUE;
		@Pc(13) int local13 = v >> 21 & 0x3FF;
		if (local7 != 0) {
			local3 = -local3;
		}
		return (float) ((double) local3 * Math.pow(2.0D, (double) (local13 - 788)));
	}
}
