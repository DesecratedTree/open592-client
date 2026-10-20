package com.jagex.game.runetek5.config.midi;

import java.util.Random;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.Static459;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.config.midi.*;
import com.jagex.game.runetek5.config.midi.Instrument;
import com.jagex.game.runetek5.config.midi.Filter;
@OriginalClass("client!pl")
public final class Instrument {

	@OriginalMember(owner = "client!pl", name = "b", descriptor = "[I")
	private static final int[] NOISE = new int[32768];

	@OriginalMember(owner = "client!pl", name = "n", descriptor = "[I")
	private static final int[] SINE;

	@OriginalMember(owner = "client!pl", name = "c", descriptor = "Lclient!bd;")
	private Filter filter;

	@OriginalMember(owner = "client!pl", name = "e", descriptor = "Lclient!ej;")
	private Envelope volumeMultiplier;

	@OriginalMember(owner = "client!pl", name = "i", descriptor = "Lclient!ej;")
	private Envelope volumeEnvelope;

	@OriginalMember(owner = "client!pl", name = "j", descriptor = "Lclient!ej;")
	private Envelope attack;

	@OriginalMember(owner = "client!pl", name = "k", descriptor = "Lclient!ej;")
	private Envelope volumeAmplitude;

	@OriginalMember(owner = "client!pl", name = "l", descriptor = "Lclient!ej;")
	private Envelope filterEnvelope;

	@OriginalMember(owner = "client!pl", name = "q", descriptor = "Lclient!ej;")
	private Envelope pitchModifierAmplitude;

	@OriginalMember(owner = "client!pl", name = "r", descriptor = "Lclient!ej;")
	private Envelope pitchModifier;

	@OriginalMember(owner = "client!pl", name = "s", descriptor = "Lclient!ej;")
	private Envelope pitch;

	@OriginalMember(owner = "client!pl", name = "u", descriptor = "Lclient!ej;")
	private Envelope release;

	@OriginalMember(owner = "client!pl", name = "a", descriptor = "[I")
	private final int[] oscillatorPitch = new int[5];

	@OriginalMember(owner = "client!pl", name = "h", descriptor = "I")
	private int delayTime = 0;

	@OriginalMember(owner = "client!pl", name = "o", descriptor = "I")
	private int delayDecay = 100;

	@OriginalMember(owner = "client!pl", name = "g", descriptor = "[I")
	private final int[] oscillatorDelays = new int[5];

	@OriginalMember(owner = "client!pl", name = "f", descriptor = "[I")
	private final int[] oscillatorVolume = new int[5];

	@OriginalMember(owner = "client!pl", name = "d", descriptor = "I")
	public int duration = 500;

	@OriginalMember(owner = "client!pl", name = "m", descriptor = "I")
	public int loopBegin = 0;

	static {
		@Pc(7) Random local7 = new Random(0L);
		for (@Pc(9) int local9 = 0; local9 < 32768; local9++) {
			NOISE[local9] = (local7.nextInt() & 0x2) - 1;
		}
		SINE = new int[32768];
		for (@Pc(28) int local28 = 0; local28 < 32768; local28++) {
			SINE[local28] = (int) (Math.sin((double) local28 / 5215.1903D) * 16384.0D);
		}
	}

	@OriginalMember(owner = "client!pl", name = "a", descriptor = "(Lclient!iv;)V")
	public void decode(@OriginalArg(0) Packet packet) {
		this.pitch = new Envelope();
		this.pitch.decode(packet);
		this.volumeEnvelope = new Envelope();
		this.volumeEnvelope.decode(packet);
		@Pc(21) int local21 = packet.method2490();
		if (local21 != 0) {
			packet.currentPosition--;
			this.pitchModifier = new Envelope();
			this.pitchModifier.decode(packet);
			this.pitchModifierAmplitude = new Envelope();
			this.pitchModifierAmplitude.decode(packet);
		}
		local21 = packet.method2490();
		if (local21 != 0) {
			packet.currentPosition--;
			this.volumeMultiplier = new Envelope();
			this.volumeMultiplier.decode(packet);
			this.volumeAmplitude = new Envelope();
			this.volumeAmplitude.decode(packet);
		}
		local21 = packet.method2490();
		if (local21 != 0) {
			packet.currentPosition--;
			this.release = new Envelope();
			this.release.decode(packet);
			this.attack = new Envelope();
			this.attack.decode(packet);
		}
		for (@Pc(109) int local109 = 0; local109 < 10; local109++) {
			@Pc(114) int local114 = packet.method2506();
			if (local114 == 0) {
				break;
			}
			this.oscillatorVolume[local109] = local114;
			this.oscillatorPitch[local109] = packet.method2502();
			this.oscillatorDelays[local109] = packet.method2506();
		}
		this.delayTime = packet.method2506();
		this.delayDecay = packet.method2506();
		this.duration = packet.method2536();
		this.loopBegin = packet.method2536();
		this.filter = new Filter();
		this.filterEnvelope = new Envelope();
		this.filter.decode(packet, this.filterEnvelope);
	}

	@OriginalMember(owner = "client!pl", name = "a", descriptor = "(II)[I")
	public int[] synthesize(@OriginalArg(0) int arg0, @OriginalArg(1) int instrDuration) {
		Static459.clear(Instrument.OUTPUT, 0, arg0);
		if (instrDuration < 10) {
			return Instrument.OUTPUT;
		}
		@Pc(16) double local16 = (double) arg0 / ((double) instrDuration + 0.0D);
		this.pitch.reset();
		this.volumeEnvelope.reset();
		@Pc(24) int local24 = 0;
		@Pc(26) int local26 = 0;
		@Pc(28) int local28 = 0;
		if (this.pitchModifier != null) {
			this.pitchModifier.reset();
			this.pitchModifierAmplitude.reset();
			local24 = (int) ((double) (this.pitchModifier.end - this.pitchModifier.start) * 32.768D / local16);
			local26 = (int) ((double) this.pitchModifier.start * 32.768D / local16);
		}
		@Pc(63) int local63 = 0;
		@Pc(65) int local65 = 0;
		@Pc(67) int local67 = 0;
		if (this.volumeMultiplier != null) {
			this.volumeMultiplier.reset();
			this.volumeAmplitude.reset();
			local63 = (int) ((double) (this.volumeMultiplier.end - this.volumeMultiplier.start) * 32.768D / local16);
			local65 = (int) ((double) this.volumeMultiplier.start * 32.768D / local16);
		}
		for (@Pc(102) int local102 = 0; local102 < 5; local102++) {
			if (this.oscillatorVolume[local102] != 0) {
				Instrument.PHASES[local102] = 0;
				Instrument.DELAYS[local102] = (int) ((double) this.oscillatorDelays[local102] * local16);
				Instrument.VOLUME_STEP[local102] = (this.oscillatorVolume[local102] << 14) / 100;
				Instrument.PITCH_STEP[local102] = (int) ((double) (this.pitch.end - this.pitch.start) * 32.768D * Math.pow(1.0057929410678534D, (double) this.oscillatorPitch[local102]) / local16);
				Instrument.PITCH_BASE_STEP[local102] = (int) ((double) this.pitch.start * 32.768D / local16);
			}
		}
		@Pc(182) int local182;
		@Pc(187) int local187;
		@Pc(195) int local195;
		@Pc(200) int local200;
		for (@Pc(176) int local176 = 0; local176 < arg0; local176++) {
			local182 = this.pitch.step(arg0);
			local187 = this.volumeEnvelope.step(arg0);
			if (this.pitchModifier != null) {
				local195 = this.pitchModifier.step(arg0);
				local200 = this.pitchModifierAmplitude.step(arg0);
				local182 += this.evaluateWave(local28, local200, this.pitchModifier.form) >> 1;
				local28 += (local195 * local24 >> 16) + local26;
			}
			if (this.volumeMultiplier != null) {
				local195 = this.volumeMultiplier.step(arg0);
				local200 = this.volumeAmplitude.step(arg0);
				local187 = local187 * ((this.evaluateWave(local67, local200, this.volumeMultiplier.form) >> 1) + 32768) >> 15;
				local67 += (local195 * local63 >> 16) + local65;
			}
			for (local195 = 0; local195 < 5; local195++) {
				if (this.oscillatorVolume[local195] != 0) {
					local200 = local176 + Instrument.DELAYS[local195];
					if (local200 < arg0) {
						Instrument.OUTPUT[local200] += this.evaluateWave(Instrument.PHASES[local195], local187 * Instrument.VOLUME_STEP[local195] >> 15, this.pitch.form);
						Instrument.PHASES[local195] += (local182 * Instrument.PITCH_STEP[local195] >> 16) + Instrument.PITCH_BASE_STEP[local195];
					}
				}
			}
		}
		@Pc(347) int local347;
		@Pc(352) int local352;
		if (this.release != null) {
			this.release.reset();
			this.attack.reset();
			local182 = 0;
			@Pc(339) boolean local339 = true;
			for (local200 = 0; local200 < arg0; local200++) {
				local347 = this.release.step(arg0);
				local352 = this.attack.step(arg0);
				if (local339) {
					local187 = this.release.start + ((this.release.end - this.release.start) * local347 >> 8);
				} else {
					local187 = this.release.start + ((this.release.end - this.release.start) * local352 >> 8);
				}
				local182 += 256;
				if (local182 >= local187) {
					local182 = 0;
					local339 = !local339;
				}
				if (local339) {
					Instrument.OUTPUT[local200] = 0;
				}
			}
		}
		if (this.delayTime > 0 && this.delayDecay > 0) {
			local182 = (int) ((double) this.delayTime * local16);
			for (local187 = local182; local187 < arg0; local187++) {
				Instrument.OUTPUT[local187] += Instrument.OUTPUT[local187 - local182] * this.delayDecay / 100;
			}
		}
		if (this.filter.numPairs[0] > 0 || this.filter.numPairs[1] > 0) {
			this.filterEnvelope.reset();
			local182 = this.filterEnvelope.step(arg0 + 1);
			local187 = this.filter.compute(0, (float) local182 / 65536.0F);
			local195 = this.filter.compute(1, (float) local182 / 65536.0F);
			if (arg0 >= local187 + local195) {
				local200 = 0;
				local347 = local195;
				if (local195 > arg0 - local187) {
					local347 = arg0 - local187;
				}
				@Pc(519) int local519;
				@Pc(549) int local549;
				while (local200 < local347) {
					local352 = (int) ((long) Instrument.OUTPUT[local200 + local187] * (long) Filter.invUnityInt >> 16);
					for (local519 = 0; local519 < local187; local519++) {
						local352 += (int) ((long) Instrument.OUTPUT[local200 + local187 - local519 - 1] * (long) Filter.COEFFICIENT_INT[0][local519] >> 16);
					}
					for (local549 = 0; local549 < local200; local549++) {
						local352 -= (int) ((long) Instrument.OUTPUT[local200 - local549 - 1] * (long) Filter.COEFFICIENT_INT[1][local549] >> 16);
					}
					Instrument.OUTPUT[local200] = local352;
					local182 = this.filterEnvelope.step(arg0 + 1);
					local200++;
				}
				local347 = 128;
				while (true) {
					if (local347 > arg0 - local187) {
						local347 = arg0 - local187;
					}
					while (local200 < local347) {
						local352 = (int) ((long) Instrument.OUTPUT[local200 + local187] * (long) Filter.invUnityInt >> 16);
						for (local519 = 0; local519 < local187; local519++) {
							local352 += (int) ((long) Instrument.OUTPUT[local200 + local187 - local519 - 1] * (long) Filter.COEFFICIENT_INT[0][local519] >> 16);
						}
						for (local549 = 0; local549 < local195; local549++) {
							local352 -= (int) ((long) Instrument.OUTPUT[local200 - local549 - 1] * (long) Filter.COEFFICIENT_INT[1][local549] >> 16);
						}
						Instrument.OUTPUT[local200] = local352;
						local182 = this.filterEnvelope.step(arg0 + 1);
						local200++;
					}
					if (local200 >= arg0 - local187) {
						while (local200 < arg0) {
							local352 = 0;
							for (local519 = local200 + local187 - arg0; local519 < local187; local519++) {
								local352 += (int) ((long) Instrument.OUTPUT[local200 + local187 - local519 - 1] * (long) Filter.COEFFICIENT_INT[0][local519] >> 16);
							}
							for (local549 = 0; local549 < local195; local549++) {
								local352 -= (int) ((long) Instrument.OUTPUT[local200 - local549 - 1] * (long) Filter.COEFFICIENT_INT[1][local549] >> 16);
							}
							Instrument.OUTPUT[local200] = local352;
							this.filterEnvelope.step(arg0 + 1);
							local200++;
						}
						break;
					}
					local187 = this.filter.compute(0, (float) local182 / 65536.0F);
					local195 = this.filter.compute(1, (float) local182 / 65536.0F);
					local347 += 128;
				}
			}
		}
		for (local182 = 0; local182 < arg0; local182++) {
			if (Instrument.OUTPUT[local182] < -32768) {
				Instrument.OUTPUT[local182] = -32768;
			}
			if (Instrument.OUTPUT[local182] > 32767) {
				Instrument.OUTPUT[local182] = 32767;
			}
		}
		return Instrument.OUTPUT;
	}

	@OriginalMember(owner = "client!pl", name = "a", descriptor = "(III)I")
	private int evaluateWave(@OriginalArg(0) int tphase, @OriginalArg(1) int amplitude, @OriginalArg(2) int table) {
		if (table == 1) {
			return (tphase & 0x7FFF) < 16384 ? amplitude : -amplitude;
		} else if (table == 2) {
			return SINE[tphase & 0x7FFF] * amplitude >> 14;
		} else if (table == 3) {
			return ((tphase & 0x7FFF) * amplitude >> 14) - amplitude;
		} else if (table == 4) {
			return NOISE[tphase / 2607 & 0x7FFF] * amplitude;
		} else {
			return 0;
		}
	}

@OriginalMember(owner = "client!pl", name = "p", descriptor = "[I")
	public static final int[] OUTPUT = new int[220500];
@OriginalMember(owner = "client!pl", name = "t", descriptor = "[I")
	public static final int[] PITCH_BASE_STEP = new int[5];
@OriginalMember(owner = "client!pl", name = "v", descriptor = "[I")
	public static final int[] VOLUME_STEP = new int[5];
@OriginalMember(owner = "client!pl", name = "w", descriptor = "[I")
	public static final int[] PHASES = new int[5];
@OriginalMember(owner = "client!pl", name = "x", descriptor = "[I")
	public static final int[] DELAYS = new int[5];
@OriginalMember(owner = "client!pl", name = "y", descriptor = "[I")
	public static final int[] PITCH_STEP = new int[5];
}
