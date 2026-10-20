package com.jagex.game.runetek5.config.material.texture.operation;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.deob.Static211;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Static99;
import com.jagex.game.runetek5.config.material.texture.operation.*;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Class222;
import com.jagex.game.runetek5.entity.ParticleEmitter;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.deob.Class78_Sub1;
@OriginalClass("client!vi")
public final class TextureOpMonochromeEdgeDetector extends TextureOperation {

	@OriginalMember(owner = "client!vi", name = "C", descriptor = "I")
	private int strength = 4096;

	@OriginalMember(owner = "client!vi", name = "<init>", descriptor = "()V")
	public TextureOpMonochromeEdgeDetector() {
		super(1, true);
	}

	@OriginalMember(owner = "client!vi", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.strength = arg1.method2536();
		}
	}

	@OriginalMember(owner = "client!vi", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[] local11 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			@Pc(33) int[] local33 = this.getChildMonochromeOutput(arg0 - 1 & Static211.anInt3929, 0);
			@Pc(39) int[] local39 = this.getChildMonochromeOutput(arg0, 0);
			@Pc(49) int[] local49 = this.getChildMonochromeOutput(Static211.anInt3929 & arg0 + 1, 0);
			for (@Pc(51) int local51 = 0; local51 < MidiPcmStream.anInt1616; local51++) {
				@Pc(65) int local65 = (local49[local51] - local33[local51]) * this.strength;
				@Pc(85) int local85 = this.strength * (local39[Class78_Sub1.anInt3883 & local51 + 1] - local39[local51 - 1 & Class78_Sub1.anInt3883]);
				@Pc(89) int local89 = local85 >> 12;
				@Pc(93) int local93 = local65 >> 12;
				@Pc(99) int local99 = local89 * local89 >> 12;
				@Pc(105) int local105 = local93 * local93 >> 12;
				@Pc(120) int local120 = (int) (Math.sqrt((double) ((float) (local99 + local105 + 4096) / 4096.0F)) * 4096.0D);
				@Pc(128) int local128 = local120 == 0 ? 0 : 16777216 / local120;
				local11[local51] = 4096 - local128;
			}
		}
		return local11;
	}

@OriginalMember(owner = "client!vi", name = "H", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_316 = new Class22(17, 0);
@OriginalMember(owner = "client!vi", name = "I", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_317 = new Class22(15, 8);
@OriginalMember(owner = "client!vi", name = "J", descriptor = "Z")
	public static boolean aBoolean653 = false;
@OriginalMember(owner = "client!vi", name = "K", descriptor = "[F")
	public static final float[] aFloatArray32 = new float[4];
@OriginalMember(owner = "client!vi", name = "g", descriptor = "(I)V")
	public static void method5405() {
		ReflectionCheck.aClass102_4.method2699();
		ParticleEmitter.OPNPC6.method5087();
		Static99.aClass229_1.method5087();
	}
@OriginalMember(owner = "client!vi", name = "b", descriptor = "(Z)Z")
	public static boolean method5406() {
		return Class222.aClass219_6 != GlToolkit.aClass219_2 || GlToolkit.activeChildCount >= 2;
	}
}
