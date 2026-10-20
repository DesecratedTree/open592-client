package com.jagex.game.runetek5.config.material.texture.operation;

import java.util.Random;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.deob.Static102;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;

import com.jagex.game.runetek5.config.material.texture.operation.*;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.graphics.model.normal.ModelNormalsGL;
import com.jagex.game.runetek5.deob.Class26;
import com.jagex.game.runetek5.js5.request.local.LocalRequester;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
@OriginalClass("client!tp")
public final class TextureOpVoronoiNoise extends TextureOperation {

	@OriginalMember(owner = "client!tp", name = "E", descriptor = "I")
	private int anInt6520 = 5;

	@OriginalMember(owner = "client!tp", name = "D", descriptor = "[S")
	private short[] pointPositions = new short[512];

	@OriginalMember(owner = "client!tp", name = "I", descriptor = "[B")
	private byte[] permutationTable = new byte[512];

	@OriginalMember(owner = "client!tp", name = "G", descriptor = "I")
	private int pointJitter = 2048;

	@OriginalMember(owner = "client!tp", name = "K", descriptor = "I")
	private int distanceOutputMode = 2;

	@OriginalMember(owner = "client!tp", name = "L", descriptor = "I")
	private int anInt6524 = 1;

	@OriginalMember(owner = "client!tp", name = "M", descriptor = "I")
	private int cellCountY = 5;

	@OriginalMember(owner = "client!tp", name = "C", descriptor = "I")
	private int randomSeed = 0;

	@OriginalMember(owner = "client!tp", name = "<init>", descriptor = "()V")
	public TextureOpVoronoiNoise() {
		super(0, true);
	}

	@OriginalMember(owner = "client!tp", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[] local11 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			@Pc(32) int local32 = this.cellCountY * ParticleEffector.anIntArray209[arg0] + 2048;
			@Pc(36) int local36 = local32 >> 12;
			@Pc(40) int local40 = local36 + 1;
			for (@Pc(42) int local42 = 0; local42 < MidiPcmStream.anInt1616; local42++) {
				Static102.anInt2046 = Integer.MAX_VALUE;
				ParticleNode.anInt4067 = Integer.MAX_VALUE;
				Class230.anInt6499 = Integer.MAX_VALUE;
				LocalRequester.anInt7014 = Integer.MAX_VALUE;
				@Pc(61) int local61 = AbstractArbBufferGL.anIntArray504[local42] * this.anInt6520 + 2048;
				@Pc(65) int local65 = local61 >> 12;
				@Pc(69) int local69 = local65 + 1;
				@Pc(158) int local158;
				for (@Pc(73) int local73 = local36 - 1; local73 <= local40; local73++) {
					@Pc(98) int local98 = this.permutationTable[(local73 >= this.cellCountY ? local73 - this.cellCountY : local73) & 0xFF] & 0xFF;
					for (@Pc(102) int local102 = local65 - 1; local102 <= local69; local102++) {
						@Pc(130) int local130 = (this.permutationTable[(local102 >= this.anInt6520 ? local102 - this.anInt6520 : local102) + local98 & 0xFF] & 0xFF) * 2;
						@Pc(135) int local135 = local61 - (local102 << 12);
						@Pc(139) int local139 = local130 + 1;
						@Pc(142) int local142 = local135 - this.pointPositions[local130];
						@Pc(155) int local155 = local32 - (local73 << 12) - this.pointPositions[local139];
						local158 = this.anInt6524;
						@Pc(190) int local190;
						if (local158 == 1) {
							local190 = local142 * local142 + local155 * local155 >> 12;
						} else if (local158 == 3) {
							local155 = local155 >= 0 ? local155 : -local155;
							local142 = local142 < 0 ? -local142 : local142;
							local190 = local155 >= local142 ? local155 : local142;
						} else if (local158 == 4) {
							local142 = (int) (Math.sqrt((double) ((float) (local142 < 0 ? -local142 : local142) / 4096.0F)) * 4096.0D);
							local155 = (int) (Math.sqrt((double) ((float) (local155 >= 0 ? local155 : -local155) / 4096.0F)) * 4096.0D);
							local190 = local155 + local142;
							local190 = local190 * local190 >> 12;
						} else if (local158 == 5) {
							local155 *= local155;
							local142 *= local142;
							local190 = (int) (Math.sqrt(Math.sqrt((double) ((float) (local155 + local142) / 1.6777216E7F))) * 4096.0D);
						} else if (local158 == 2) {
							local190 = (local142 >= 0 ? local142 : -local142) + (local155 >= 0 ? local155 : -local155);
						} else {
							local190 = (int) (Math.sqrt((double) ((float) (local155 * local155 + local142 * local142) / 1.6777216E7F)) * 4096.0D);
						}
						if (local190 < LocalRequester.anInt7014) {
							Static102.anInt2046 = ParticleNode.anInt4067;
							ParticleNode.anInt4067 = Class230.anInt6499;
							Class230.anInt6499 = LocalRequester.anInt7014;
							LocalRequester.anInt7014 = local190;
						} else if (Class230.anInt6499 > local190) {
							Static102.anInt2046 = ParticleNode.anInt4067;
							ParticleNode.anInt4067 = Class230.anInt6499;
							Class230.anInt6499 = local190;
						} else if (ParticleNode.anInt4067 > local190) {
							Static102.anInt2046 = ParticleNode.anInt4067;
							ParticleNode.anInt4067 = local190;
						} else if (local190 < Static102.anInt2046) {
							Static102.anInt2046 = local190;
						}
					}
				}
				local158 = this.distanceOutputMode;
				if (local158 == 0) {
					local11[local42] = LocalRequester.anInt7014;
				} else if (local158 == 1) {
					local11[local42] = Class230.anInt6499;
				} else if (local158 == 3) {
					local11[local42] = ParticleNode.anInt4067;
				} else if (local158 == 4) {
					local11[local42] = Static102.anInt2046;
				} else if (local158 == 2) {
					local11[local42] = Class230.anInt6499 - LocalRequester.anInt7014;
				}
			}
		}
		return local11;
	}

	@OriginalMember(owner = "client!tp", name = "f", descriptor = "(I)V")
	private void initializePointPositions() {
		@Pc(18) Random local18 = new Random((long) this.randomSeed);
		this.pointPositions = new short[512];
		if (this.pointJitter > 0) {
			for (@Pc(27) int local27 = 0; local27 < 512; local27++) {
				this.pointPositions[local27] = (short) ModelNormalsGL.method3443(local18, this.pointJitter);
			}
		}
	}

	@OriginalMember(owner = "client!tp", name = "a", descriptor = "(Z)V")
	@Override
	public void postDecode() {
		this.permutationTable = Class26.method643(this.randomSeed);
		this.initializePointPositions();
	}

	@OriginalMember(owner = "client!tp", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.anInt6520 = this.cellCountY = arg1.method2490();
		} else if (arg0 == 1) {
			this.randomSeed = arg1.method2490();
		} else if (arg0 == 2) {
			this.pointJitter = arg1.method2536();
		} else if (arg0 == 3) {
			this.distanceOutputMode = arg1.method2490();
		} else if (arg0 == 4) {
			this.anInt6524 = arg1.method2490();
		} else if (arg0 == 5) {
			this.anInt6520 = arg1.method2490();
		} else if (arg0 == 6) {
			this.cellCountY = arg1.method2490();
		}
	}

@OriginalMember(owner = "client!tp", name = "F", descriptor = "I")
	public static int anInt6521;
@OriginalMember(owner = "client!tp", name = "J", descriptor = "Lclient!l;")
	public static NativeSprite aClass57_22;
@OriginalMember(owner = "client!tp", name = "N", descriptor = "I")
	public static int anInt6526 = 0;
@OriginalMember(owner = "client!tp", name = "a", descriptor = "(IJ)V")
	public static void method5115(@OriginalArg(1) long arg0) {
		try {
			Thread.sleep(arg0);
		} catch (@Pc(11) InterruptedException local11) {
		}
	}
}
