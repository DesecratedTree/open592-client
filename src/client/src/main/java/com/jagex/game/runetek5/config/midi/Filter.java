package com.jagex.game.runetek5.config.midi;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;

import com.jagex.game.runetek5.config.midi.*;
import com.jagex.game.runetek5.config.midi.Filter;
@OriginalClass("client!bd")
public final class Filter {

	@OriginalMember(owner = "client!bd", name = "a", descriptor = "[[[I")
	private final int[][][] pairMagnitude = new int[2][2][4];

	@OriginalMember(owner = "client!bd", name = "e", descriptor = "[I")
	private final int[] unity = new int[2];

	@OriginalMember(owner = "client!bd", name = "f", descriptor = "[I")
	public final int[] numPairs = new int[2];

	@OriginalMember(owner = "client!bd", name = "d", descriptor = "[[[I")
	private final int[][][] pairPhase = new int[2][2][4];

	@OriginalMember(owner = "client!bd", name = "a", descriptor = "(IIF)F")
	private float adaptPhase(@OriginalArg(0) int direction, @OriginalArg(1) int phase, @OriginalArg(2) float off) {
		@Pc(30) float local30 = (float) this.pairPhase[direction][0][phase] + off * (float) (this.pairPhase[direction][1][phase] - this.pairPhase[direction][0][phase]);
		@Pc(34) float local34 = local30 * 1.2207031E-4F;
		return Filter.normalize(local34);
	}

	@OriginalMember(owner = "client!bd", name = "a", descriptor = "(Lclient!iv;Lclient!ej;)V")
	public void decode(@OriginalArg(0) Packet packet, @OriginalArg(1) Envelope envelope) {
		@Pc(3) int local3 = packet.method2490();
		this.numPairs[0] = local3 >> 4;
		this.numPairs[1] = local3 & 0xF;
		if (local3 == 0) {
			this.unity[0] = this.unity[1] = 0;
			return;
		}
		this.unity[0] = packet.method2536();
		this.unity[1] = packet.method2536();
		@Pc(37) int local37 = packet.method2490();
		@Pc(42) int local42;
		for (@Pc(39) int local39 = 0; local39 < 2; local39++) {
			for (local42 = 0; local42 < this.numPairs[local39]; local42++) {
				this.pairPhase[local39][0][local42] = packet.method2536();
				this.pairMagnitude[local39][0][local42] = packet.method2536();
			}
		}
		for (local42 = 0; local42 < 2; local42++) {
			for (@Pc(81) int local81 = 0; local81 < this.numPairs[local42]; local81++) {
				if ((local37 & 0x1 << local42 * 4 << local81) == 0) {
					this.pairPhase[local42][1][local81] = this.pairPhase[local42][0][local81];
					this.pairMagnitude[local42][1][local81] = this.pairMagnitude[local42][0][local81];
				} else {
					this.pairPhase[local42][1][local81] = packet.method2536();
					this.pairMagnitude[local42][1][local81] = packet.method2536();
				}
			}
		}
		if (local37 != 0 || this.unity[1] != this.unity[0]) {
			envelope.decodeShape(packet);
		}
	}

	@OriginalMember(owner = "client!bd", name = "a", descriptor = "(IF)I")
	public int compute(@OriginalArg(0) int direction, @OriginalArg(1) float off) {
		@Pc(20) float local20;
		if (direction == 0) {
			local20 = (float) this.unity[0] + (float) (this.unity[1] - this.unity[0]) * off;
			@Pc(24) float local24 = local20 * 0.0030517578F;
			Filter.invUnityFloat = (float) Math.pow(0.1D, (double) (local24 / 20.0F));
			Filter.invUnityInt = (int) (Filter.invUnityFloat * 65536.0F);
		}
		if (this.numPairs[direction] == 0) {
			return 0;
		}
		local20 = this.adaptMagnitude(direction, 0, off);
		Filter.COEFFICIENT_FLOAT[direction][0] = -2.0F * local20 * (float) Math.cos((double) this.adaptPhase(direction, 0, off));
		Filter.COEFFICIENT_FLOAT[direction][1] = local20 * local20;
		for (@Pc(77) int local77 = 1; local77 < this.numPairs[direction]; local77++) {
			local20 = this.adaptMagnitude(direction, local77, off);
			@Pc(97) float local97 = -2.0F * local20 * (float) Math.cos((double) this.adaptPhase(direction, local77, off));
			@Pc(101) float local101 = local20 * local20;
			Filter.COEFFICIENT_FLOAT[direction][local77 * 2 + 1] = Filter.COEFFICIENT_FLOAT[direction][local77 * 2 - 1] * local101;
			Filter.COEFFICIENT_FLOAT[direction][local77 * 2] = Filter.COEFFICIENT_FLOAT[direction][local77 * 2 - 1] * local97 + Filter.COEFFICIENT_FLOAT[direction][local77 * 2 - 2] * local101;
			for (@Pc(157) int local157 = local77 * 2 - 1; local157 >= 2; local157--) {
				Filter.COEFFICIENT_FLOAT[direction][local157] += Filter.COEFFICIENT_FLOAT[direction][local157 - 1] * local97 + Filter.COEFFICIENT_FLOAT[direction][local157 - 2] * local101;
			}
			Filter.COEFFICIENT_FLOAT[direction][1] += Filter.COEFFICIENT_FLOAT[direction][0] * local97 + local101;
			Filter.COEFFICIENT_FLOAT[direction][0] += local97;
		}
		@Pc(226) int local226;
		if (direction == 0) {
			for (local226 = 0; local226 < this.numPairs[0] * 2; local226++) {
				Filter.COEFFICIENT_FLOAT[0][local226] *= Filter.invUnityFloat;
			}
		}
		for (local226 = 0; local226 < this.numPairs[direction] * 2; local226++) {
			Filter.COEFFICIENT_INT[direction][local226] = (int) (Filter.COEFFICIENT_FLOAT[direction][local226] * 65536.0F);
		}
		return this.numPairs[direction] * 2;
	}

	@OriginalMember(owner = "client!bd", name = "b", descriptor = "(IIF)F")
	private float adaptMagnitude(@OriginalArg(0) int direction, @OriginalArg(1) int phase, @OriginalArg(2) float off) {
		@Pc(30) float local30 = (float) this.pairMagnitude[direction][0][phase] + off * (float) (this.pairMagnitude[direction][1][phase] - this.pairMagnitude[direction][0][phase]);
		@Pc(34) float local34 = local30 * 0.0015258789F;
		return 1.0F - (float) Math.pow(10.0D, (double) (-local34 / 20.0F));
	}

@OriginalMember(owner = "client!bd", name = "g", descriptor = "I")
	public static int invUnityInt;
@OriginalMember(owner = "client!bd", name = "h", descriptor = "F")
	public static float invUnityFloat;
@OriginalMember(owner = "client!bd", name = "b", descriptor = "[[I")
	public static final int[][] COEFFICIENT_INT = new int[2][8];
@OriginalMember(owner = "client!bd", name = "c", descriptor = "[[F")
	public static final float[][] COEFFICIENT_FLOAT = new float[2][8];
@OriginalMember(owner = "client!bd", name = "a", descriptor = "(F)F")
	public static float normalize(@OriginalArg(0) float octaves) {
		@Pc(7) float local7 = (float) Math.pow(2.0D, (double) octaves) * 32.703197F;
		return local7 * 3.1415927F / 11025.0F;
	}
}
