package com.jagex.game.runetek5.config.material.texture.operation;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.sound.player.PcmPlayer;

import com.jagex.game.runetek5.config.material.texture.operation.*;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
@OriginalClass("client!oo")
public final class TextureOpMandelbrot extends TextureOperation {

	@OriginalMember(owner = "client!oo", name = "F", descriptor = "I")
	private int centerY = 0;

	@OriginalMember(owner = "client!oo", name = "H", descriptor = "I")
	private int centerX = 20;

	@OriginalMember(owner = "client!oo", name = "E", descriptor = "I")
	private int zoom = 1365;

	@OriginalMember(owner = "client!oo", name = "K", descriptor = "I")
	private int maxIterations = 0;

	@OriginalMember(owner = "client!oo", name = "<init>", descriptor = "()V")
	public TextureOpMandelbrot() {
		super(0, true);
	}

	@OriginalMember(owner = "client!oo", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[] local11 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			for (@Pc(17) int local17 = 0; local17 < MidiPcmStream.anInt1616; local17++) {
				@Pc(31) int local31 = (AbstractArbBufferGL.anIntArray504[local17] << 12) / this.zoom + this.maxIterations;
				@Pc(43) int local43 = (ParticleEffector.anIntArray209[arg0] << 12) / this.zoom + this.centerY;
				@Pc(49) int local49 = local31;
				@Pc(51) int local51 = local43;
				@Pc(57) int local57 = local31 * local31 >> 12;
				@Pc(63) int local63 = local43 * local43 >> 12;
				@Pc(65) int local65 = 0;
				while (local57 + local63 < 16384 && local65 < this.centerX) {
					local51 = (local49 * local51 >> 12) * 2 + local43;
					local49 = local31 + local57 - local63;
					local65++;
					local57 = local49 * local49 >> 12;
					local63 = local51 * local51 >> 12;
				}
				local11[local17] = local65 < this.centerX - 1 ? (local65 << 12) / this.centerX : 0;
			}
		}
		return local11;
	}

	@OriginalMember(owner = "client!oo", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.zoom = arg1.method2536();
		} else if (arg0 == 1) {
			this.centerX = arg1.method2536();
		} else if (arg0 == 2) {
			this.maxIterations = arg1.method2536();
		} else if (arg0 == 3) {
			this.centerY = arg1.method2536();
		}
	}

@OriginalMember(owner = "client!oo", name = "L", descriptor = "Lclient!ln;")
	public static PcmPlayer aClass49_1;
@OriginalMember(owner = "client!oo", name = "D", descriptor = "Lclient!pk;")
	public static final Class183 aClass183_35 = new Class183();
@OriginalMember(owner = "client!oo", name = "G", descriptor = "S")
	public static short aShort66 = 1;
}
