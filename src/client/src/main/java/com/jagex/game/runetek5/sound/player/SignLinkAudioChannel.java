package com.jagex.game.runetek5.sound.player;

import java.awt.Component;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.sign.SignLink;

import com.jagex.game.runetek5.PcmDevice;

import com.jagex.game.runetek5.sound.player.*;
import com.jagex.game.runetek5.sound.player.SignLinkAudioChannel;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.GpuFaceNormal;
@OriginalClass("client!fo")
public final class SignLinkAudioChannel extends PcmPlayer {

	@OriginalMember(owner = "client!fo", name = "I", descriptor = "I")
	private final int deviceIndex;

	@OriginalMember(owner = "client!fo", name = "<init>", descriptor = "(Lclient!et;I)V")
	public SignLinkAudioChannel(@OriginalArg(0) SignLink signLink, @OriginalArg(1) int deviceIndex) {
		SignLinkAudioChannel.pcmDevice = signLink.method1734();
		this.deviceIndex = deviceIndex;
	}

	@OriginalMember(owner = "client!fo", name = "b", descriptor = "()V")
	@Override
	protected void close() {
		SignLinkAudioChannel.pcmDevice.method4438(this.deviceIndex);
	}

	@OriginalMember(owner = "client!fo", name = "a", descriptor = "(Ljava/awt/Component;)V")
	@Override
	public void init(@OriginalArg(0) Component component) throws Exception {
		SignLinkAudioChannel.pcmDevice.method4436(component, GpuFaceNormal.aBoolean649, MECType.anInt4928);
	}

	@OriginalMember(owner = "client!fo", name = "c", descriptor = "()V")
	@Override
	protected void open() {
		SignLinkAudioChannel.pcmDevice.method4437(this.deviceIndex);
	}

	@OriginalMember(owner = "client!fo", name = "d", descriptor = "()V")
	@Override
	protected void writeToBuffer() {
		SignLinkAudioChannel.pcmDevice.method4441(this.deviceIndex, super.bitpackedSoundData);
	}

	@OriginalMember(owner = "client!fo", name = "a", descriptor = "()I")
	@Override
	protected int dataLeft() {
		return SignLinkAudioChannel.pcmDevice.method4439(this.deviceIndex);
	}

	@OriginalMember(owner = "client!fo", name = "b", descriptor = "(I)V")
	@Override
	public void play(@OriginalArg(0) int bufferCapacity) throws Exception {
		if (bufferCapacity > 32768) {
			throw new IllegalArgumentException();
		}
		SignLinkAudioChannel.pcmDevice.method4440(bufferCapacity, this.deviceIndex);
	}

@OriginalMember(owner = "client!fo", name = "J", descriptor = "Lclient!qn;")
	public static PcmDevice pcmDevice;
}
