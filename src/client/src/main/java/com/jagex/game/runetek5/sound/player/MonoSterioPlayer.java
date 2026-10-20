package com.jagex.game.runetek5.sound.player;

import java.awt.Component;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.graphics.sprite.SpriteDrawQueue;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.deob.Class105;
import com.jagex.game.runetek5.GpuFaceNormal;
@OriginalClass("client!dh")
public final class MonoSterioPlayer extends PcmPlayer {

	@OriginalMember(owner = "client!dh", name = "J", descriptor = "Ljavax/sound/sampled/SourceDataLine;")
	private SourceDataLine aSourceDataLine1;

	@OriginalMember(owner = "client!dh", name = "K", descriptor = "Ljavax/sound/sampled/AudioFormat;")
	private AudioFormat audioFormat;

	@OriginalMember(owner = "client!dh", name = "L", descriptor = "[B")
	private byte[] buffer;

	@OriginalMember(owner = "client!dh", name = "M", descriptor = "I")
	private int bufferLength;

	@OriginalMember(owner = "client!dh", name = "I", descriptor = "Z")
	private boolean hasSoundmax = false;

	@OriginalMember(owner = "client!dh", name = "b", descriptor = "(I)V")
	@Override
	public void play(@OriginalArg(0) int bufferLength) throws LineUnavailableException {
		try {
			@Pc(20) Info local20 = new Info(SourceDataLine.class, this.audioFormat, bufferLength << (GpuFaceNormal.aBoolean649 ? 2 : 1));
			this.aSourceDataLine1 = (SourceDataLine) AudioSystem.getLine(local20);
			this.aSourceDataLine1.open();
			this.aSourceDataLine1.start();
			this.bufferLength = bufferLength;
		} catch (@Pc(36) LineUnavailableException local36) {
			if (Class105.method5277(bufferLength) == 1) {
				this.aSourceDataLine1 = null;
				throw local36;
			} else {
				this.play(SpriteDrawQueue.method4949(bufferLength));
			}
		}
	}

	@OriginalMember(owner = "client!dh", name = "d", descriptor = "()V")
	@Override
	protected void writeToBuffer() {
		@Pc(1) short local1 = 256;
		if (GpuFaceNormal.aBoolean649) {
			local1 = 512;
		}
		for (@Pc(9) int local9 = 0; local9 < local1; local9++) {
			@Pc(17) int local17 = this.bitpackedSoundData[local9];
			if ((local17 + 8388608 & 0xFF000000) != 0) {
				local17 = local17 >> 31 ^ 0x7FFFFF;
			}
			this.buffer[local9 * 2] = (byte) (local17 >> 8);
			this.buffer[local9 * 2 + 1] = (byte) (local17 >> 16);
		}
		this.aSourceDataLine1.write(this.buffer, 0, local1 << 1);
	}

	@OriginalMember(owner = "client!dh", name = "a", descriptor = "()I")
	@Override
	protected int dataLeft() {
		return this.bufferLength - (this.aSourceDataLine1.available() >> (GpuFaceNormal.aBoolean649 ? 2 : 1));
	}

	@OriginalMember(owner = "client!dh", name = "a", descriptor = "(Ljava/awt/Component;)V")
	@Override
	public void init(@OriginalArg(0) Component component) {
		@Pc(1) javax.sound.sampled.Mixer.Info[] local1 = AudioSystem.getMixerInfo();
		if (local1 != null) {
			for (@Pc(7) int local7 = 0; local7 < local1.length; local7++) {
				@Pc(19) javax.sound.sampled.Mixer.Info local19 = local1[local7];
				if (local19 != null) {
					@Pc(25) String local25 = local19.getName();
					if (local25 != null && local25.toLowerCase().indexOf("soundmax") >= 0) {
						this.hasSoundmax = true;
					}
				}
			}
		}
		this.audioFormat = new AudioFormat((float) MECType.anInt4928, 16, GpuFaceNormal.aBoolean649 ? 2 : 1, true, false);
		this.buffer = new byte[0x100 << (GpuFaceNormal.aBoolean649 ? 2 : 1)];
	}

	@OriginalMember(owner = "client!dh", name = "c", descriptor = "()V")
	@Override
	protected void open() throws LineUnavailableException {
		this.aSourceDataLine1.flush();
		if (!this.hasSoundmax) {
			return;
		}
		this.aSourceDataLine1.close();
		this.aSourceDataLine1 = null;
		@Pc(34) Info local34 = new Info(SourceDataLine.class, this.audioFormat, this.bufferLength << (GpuFaceNormal.aBoolean649 ? 2 : 1));
		this.aSourceDataLine1 = (SourceDataLine) AudioSystem.getLine(local34);
		this.aSourceDataLine1.open();
		this.aSourceDataLine1.start();
	}

	@OriginalMember(owner = "client!dh", name = "b", descriptor = "()V")
	@Override
	protected void close() {
		if (this.aSourceDataLine1 != null) {
			this.aSourceDataLine1.close();
			this.aSourceDataLine1 = null;
		}
	}
}
