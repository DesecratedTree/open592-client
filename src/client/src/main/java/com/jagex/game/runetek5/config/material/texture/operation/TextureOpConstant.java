package com.jagex.game.runetek5.config.material.texture.operation;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.deob.Static459;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.QuickChatValueType;

import com.jagex.game.runetek5.config.material.texture.operation.*;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
@OriginalClass("client!lc")
public final class TextureOpConstant extends TextureOperation {

	@OriginalMember(owner = "client!lc", name = "H", descriptor = "I")
	private int intensity;

	@OriginalMember(owner = "client!lc", name = "<init>", descriptor = "(I)V")
	public TextureOpConstant(@OriginalArg(0) int intensity) {
		super(0, true);
		this.intensity = 4096;
		this.intensity = intensity;
	}

	@OriginalMember(owner = "client!lc", name = "<init>", descriptor = "()V")
	public TextureOpConstant() {
		this(4096);
	}

	@OriginalMember(owner = "client!lc", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.intensity = (arg1.method2490() << 12) / 255;
		}
	}

	@OriginalMember(owner = "client!lc", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(9) int[] local9 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			Static459.setVariables(local9, 0, MidiPcmStream.anInt1616, this.intensity);
		}
		return local9;
	}

@OriginalMember(owner = "client!lc", name = "G", descriptor = "I")
	public static int anInt4098;
@OriginalMember(owner = "client!lc", name = "E", descriptor = "Lclient!ho;")
	public static final QuickChatValueType aClass98_10 = new QuickChatValueType(15, 0, 1, 0);
@OriginalMember(owner = "client!lc", name = "F", descriptor = "I")
	public static int anInt4097 = -1;
}
