package com.jagex.game.runetek5.config.material.texture.operation;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.deob.Static459;

import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.deob.Huffman;
import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
@OriginalClass("client!w")
public final class TextureOpSquareWaveform extends TextureOperation {

	@OriginalMember(owner = "client!w", name = "C", descriptor = "[I")
	private int[] anIntArray539;

	@OriginalMember(owner = "client!w", name = "M", descriptor = "[I")
	private int[] anIntArray540;

	@OriginalMember(owner = "client!w", name = "D", descriptor = "I")
	private int anInt7116 = 0;

	@OriginalMember(owner = "client!w", name = "H", descriptor = "I")
	private int anInt7120 = 10;

	@OriginalMember(owner = "client!w", name = "I", descriptor = "I")
	private int anInt7121 = 2048;

	@OriginalMember(owner = "client!w", name = "<init>", descriptor = "()V")
	public TextureOpSquareWaveform() {
		super(0, true);
	}

	@OriginalMember(owner = "client!w", name = "a", descriptor = "(Z)V")
	@Override
	public void postDecode() {
		this.method5522();
	}

	@OriginalMember(owner = "client!w", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.anInt7120 = arg1.method2490();
		} else if (arg0 == 1) {
			this.anInt7121 = arg1.method2536();
		} else if (arg0 == 2) {
			this.anInt7116 = arg1.method2490();
		}
	}

	@OriginalMember(owner = "client!w", name = "g", descriptor = "(I)V")
	private void method5522() {
		this.anIntArray539 = new int[this.anInt7120 + 1];
		this.anIntArray540 = new int[this.anInt7120 + 1];
		@Pc(21) int local21 = 0;
		@Pc(26) int local26 = 4096 / this.anInt7120;
		@Pc(33) int local33 = this.anInt7121 * local26 >> 12;
		for (@Pc(43) int local43 = 0; local43 < this.anInt7120; local43++) {
			this.anIntArray539[local43] = local21;
			this.anIntArray540[local43] = local33 + local21;
			local21 += local26;
		}
		this.anIntArray539[this.anInt7120] = 4096;
		this.anIntArray540[this.anInt7120] = this.anIntArray540[0] + 4096;
	}

	@OriginalMember(owner = "client!w", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[] local11 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			@Pc(27) int local27 = ParticleEffector.anIntArray209[arg0];
			@Pc(36) int local36;
			if (this.anInt7116 == 0) {
				@Pc(146) short local146 = 0;
				for (local36 = 0; local36 < this.anInt7120; local36++) {
					if (local27 >= this.anIntArray539[local36] && local27 < this.anIntArray539[local36 + 1]) {
						if (local27 < this.anIntArray540[local36]) {
							local146 = 4096;
						}
						break;
					}
				}
				Static459.setVariables(local11, 0, MidiPcmStream.anInt1616, local146);
			} else {
				for (@Pc(32) int local32 = 0; local32 < MidiPcmStream.anInt1616; local32++) {
					local36 = 0;
					@Pc(38) short local38 = 0;
					@Pc(42) int local42 = AbstractArbBufferGL.anIntArray504[local32];
					@Pc(45) int local45 = this.anInt7116;
					if (local45 == 1) {
						local36 = local42;
					} else if (local45 == 2) {
						local36 = (local42 + local27 - 4096 >> 1) + 2048;
					} else if (local45 == 3) {
						local36 = (local42 - local27 >> 1) + 2048;
					}
					for (local45 = 0; local45 < this.anInt7120; local45++) {
						if (local36 >= this.anIntArray539[local45] && local36 < this.anIntArray539[local45 + 1]) {
							if (this.anIntArray540[local45] > local36) {
								local38 = 4096;
							}
							break;
						}
					}
					local11[local32] = local38;
				}
			}
		}
		return local11;
	}

@OriginalMember(owner = "client!w", name = "J", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_95 = new Class215(46, 8);
@OriginalMember(owner = "client!w", name = "N", descriptor = "Z")
	public static boolean aBoolean666 = false;
@OriginalMember(owner = "client!w", name = "Q", descriptor = "Lclient!fa;")
	public static final LRUCache aClass68_53 = new LRUCache(64);
@OriginalMember(owner = "client!w", name = "R", descriptor = "Z")
	public static boolean aBoolean667 = false;
@OriginalMember(owner = "client!w", name = "S", descriptor = "Z")
	public static boolean aBoolean668 = false;
@OriginalMember(owner = "client!w", name = "a", descriptor = "(ZLjava/lang/String;Lclient!la;I)V")
	public static void method5519(@OriginalArg(0) boolean arg0, @OriginalArg(1) String arg1, @OriginalArg(2) AbstractFontRenderer arg2) {
		@Pc(21) int local21 = ReflectionCheck.aClass89_1.method2349(arg1, 250, null);
		@Pc(30) int local30 = ReflectionCheck.aClass89_1.method2347(null, 250, arg1) * 13;
		MonoStereoData.aClass19_16.O(6, 6, local21 + 8, local30 + 4 + 4, -16777216, 0);
		MonoStereoData.aClass19_16.outlineRect(6, 6, local21 + 8, 4 + 4 + local30, -1, 0);
		arg2.method5063(-1, 0, null, 0, null, 10, 10, local30, local21, -1, 1, arg1, null);
		EffectiveVertex.method4103(6, local30 + 4 + 4, local21 + 4 + 4, 6);
		if (arg0) {
			MonoStereoData.aClass19_16.method4247();
		}
	}
@OriginalMember(owner = "client!w", name = "f", descriptor = "(I)V")
	public static void method5520() {
		Huffman.method2932(25);
		Class4_Sub6_Sub4.method1277();
		System.gc();
	}
@OriginalMember(owner = "client!w", name = "a", descriptor = "(CZ)Z")
	public static boolean method5521(@OriginalArg(0) char arg0) {
		return arg0 >= '0' && arg0 <= '9';
	}
}
