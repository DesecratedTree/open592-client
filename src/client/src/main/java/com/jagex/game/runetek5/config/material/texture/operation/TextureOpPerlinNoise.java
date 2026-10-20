package com.jagex.game.runetek5.config.material.texture.operation;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.deob.Class75;
import com.jagex.game.runetek5.ByteArrayWrapper;
import com.jagex.game.runetek5.config.material.texture.operation.*;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteType;
import com.jagex.game.runetek5.deob.Class26;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
@OriginalClass("client!gl")
public final class TextureOpPerlinNoise extends TextureOperation {

	@OriginalMember(owner = "client!gl", name = "G", descriptor = "[S")
	private short[] stepMultipliers;

	@OriginalMember(owner = "client!gl", name = "I", descriptor = "[S")
	private short[] amplitudeMultipliers;

	@OriginalMember(owner = "client!gl", name = "E", descriptor = "I")
	public int anInt2698 = 4;

	@OriginalMember(owner = "client!gl", name = "M", descriptor = "I")
	public int anInt2702 = 1638;

	@OriginalMember(owner = "client!gl", name = "L", descriptor = "[B")
	private byte[] noiseArray = new byte[512];

	@OriginalMember(owner = "client!gl", name = "F", descriptor = "I")
	public int anInt2699 = 4;

	@OriginalMember(owner = "client!gl", name = "R", descriptor = "I")
	public int anInt2706 = 4;

	@OriginalMember(owner = "client!gl", name = "T", descriptor = "I")
	public int randomSeed = 0;

	@OriginalMember(owner = "client!gl", name = "S", descriptor = "Z")
	public boolean useFogEffect = true;

	@OriginalMember(owner = "client!gl", name = "<init>", descriptor = "()V")
	public TextureOpPerlinNoise() {
		super(0, true);
	}

	@OriginalMember(owner = "client!gl", name = "a", descriptor = "([IBI)V")
	public void method2283(@OriginalArg(0) int[] arg0, @OriginalArg(2) int arg1) {
		@Pc(12) int local12 = ParticleEffector.anIntArray209[arg1] * this.anInt2706;
		@Pc(114) int local114;
		@Pc(128) int local128;
		@Pc(44) int local44;
		@Pc(27) short local27;
		@Pc(105) int local105;
		@Pc(50) int local50;
		@Pc(64) int local64;
		@Pc(57) int local57;
		@Pc(94) int local94;
		@Pc(68) int local68;
		@Pc(72) int local72;
		@Pc(90) int local90;
		@Pc(103) int local103;
		if (this.anInt2698 == 1) {
			local44 = this.amplitudeMultipliers[0] << 12;
			local27 = this.stepMultipliers[0];
			local50 = local44 * local12 >> 12;
			local57 = this.anInt2706 * local44 >> 12;
			local64 = this.anInt2699 * local44 >> 12;
			local68 = local50 >> 12;
			local72 = local68 + 1;
			@Pc(379) int local379 = local50 & 0xFFF;
			if (local72 >= local57) {
				local72 = 0;
			}
			local94 = Class75.anIntArray163[local379];
			local103 = this.noiseArray[local72 & 0xFF] & 0xFF;
			local90 = this.noiseArray[local68 & 0xFF] & 0xFF;
			if (this.useFogEffect) {
				for (local105 = 0; local105 < MidiPcmStream.anInt1616; local105++) {
					local114 = AbstractArbBufferGL.anIntArray504[local105] * this.anInt2699;
					local128 = this.method2284(local94, local90, local103, local64, local114 * local44 >> 12, local379);
					@Pc(478) int local478 = local128 * local27 >> 12;
					arg0[local105] = (local478 >> 1) + 2048;
				}
			} else {
				for (local105 = 0; local105 < MidiPcmStream.anInt1616; local105++) {
					local114 = this.anInt2699 * AbstractArbBufferGL.anIntArray504[local105];
					local128 = this.method2284(local94, local90, local103, local64, local114 * local44 >> 12, local379);
					arg0[local105] = local27 * local128 >> 12;
				}
			}
			return;
		}
		local27 = this.stepMultipliers[0];
		if (local27 > 8 || local27 < -8) {
			local44 = this.amplitudeMultipliers[0] << 12;
			local50 = local44 * local12 >> 12;
			local57 = this.anInt2706 * local44 >> 12;
			local64 = local44 * this.anInt2699 >> 12;
			local68 = local50 >> 12;
			local72 = local68 + 1;
			if (local72 >= local57) {
				local72 = 0;
			}
			local50 &= 0xFFF;
			local90 = this.noiseArray[local68 & 0xFF] & 0xFF;
			local94 = Class75.anIntArray163[local50];
			local103 = this.noiseArray[local72 & 0xFF] & 0xFF;
			for (local105 = 0; local105 < MidiPcmStream.anInt1616; local105++) {
				local114 = AbstractArbBufferGL.anIntArray504[local105] * this.anInt2699;
				local128 = this.method2284(local94, local90, local103, local64, local114 * local44 >> 12, local50);
				arg0[local105] = local27 * local128 >> 12;
			}
		}
		for (@Pc(146) int local146 = 1; local146 < this.anInt2698; local146++) {
			local27 = this.stepMultipliers[local146];
			if (local27 > 8 || local27 < -8) {
				local44 = this.amplitudeMultipliers[local146] << 12;
				local64 = this.anInt2699 * local44 >> 12;
				local50 = local44 * local12 >> 12;
				local57 = this.anInt2706 * local44 >> 12;
				local68 = local50 >> 12;
				local72 = local68 + 1;
				if (local57 <= local72) {
					local72 = 0;
				}
				local50 &= 0xFFF;
				local94 = Class75.anIntArray163[local50];
				local103 = this.noiseArray[local72 & 0xFF] & 0xFF;
				local90 = this.noiseArray[local68 & 0xFF] & 0xFF;
				if (this.useFogEffect && this.anInt2698 - 1 == local146) {
					for (local105 = 0; local105 < MidiPcmStream.anInt1616; local105++) {
						local114 = AbstractArbBufferGL.anIntArray504[local105] * this.anInt2699;
						local128 = this.method2284(local94, local90, local103, local64, local44 * local114 >> 12, local50);
						local128 = arg0[local105] + (local128 * local27 >> 12);
						arg0[local105] = (local128 >> 1) + 2048;
					}
				} else {
					for (local105 = 0; local105 < MidiPcmStream.anInt1616; local105++) {
						local114 = this.anInt2699 * AbstractArbBufferGL.anIntArray504[local105];
						local128 = this.method2284(local94, local90, local103, local64, local44 * local114 >> 12, local50);
						arg0[local105] += local128 * local27 >> 12;
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!gl", name = "a", descriptor = "(IIIIIII)I")
	private int method2284(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5) {
		@Pc(9) int local9 = arg4 >> 12;
		@Pc(13) int local13 = local9 + 1;
		@Pc(17) int local17 = arg4 & 0xFFF;
		@Pc(21) int local21 = local9 & 0xFF;
		if (local13 >= arg3) {
			local13 = 0;
		}
		local13 &= 0xFF;
		@Pc(38) int local38 = arg5 - 4096;
		@Pc(42) int local42 = local17 - 4096;
		@Pc(46) int local46 = Class75.anIntArray163[local17];
		@Pc(55) int local55 = this.noiseArray[arg1 + local21] & 0x3;
		@Pc(71) int local71;
		if (local55 <= 1) {
			local71 = local55 == 0 ? local17 + arg5 : -local17 + arg5;
		} else {
			local71 = local55 == 2 ? local17 - arg5 : -local17 + -arg5;
		}
		local55 = this.noiseArray[arg1 + local13] & 0x3;
		@Pc(114) int local114;
		if (local55 <= 1) {
			local114 = local55 == 0 ? arg5 + local42 : arg5 - local42;
		} else {
			local114 = local55 == 2 ? local42 - arg5 : -local42 - arg5;
		}
		local55 = this.noiseArray[local21 + arg2] & 0x3;
		@Pc(149) int local149 = ((local114 - local71) * local46 >> 12) + local71;
		if (local55 <= 1) {
			local71 = local55 == 0 ? local38 + local17 : -local17 + local38;
		} else {
			local71 = local55 == 2 ? local17 - local38 : -local38 + -local17;
		}
		local55 = this.noiseArray[arg2 + local13] & 0x3;
		if (local55 > 1) {
			local114 = local55 == 2 ? local42 - local38 : -local42 + -local38;
		} else {
			local114 = local55 == 0 ? local42 + local38 : -local42 + local38;
		}
		@Pc(234) int local234 = local71 + ((local114 - local71) * local46 >> 12);
		return ((local234 - local149) * arg0 >> 12) + local149;
	}

	@OriginalMember(owner = "client!gl", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.useFogEffect = arg1.method2490() == 1;
		} else if (arg0 == 1) {
			this.anInt2698 = arg1.method2490();
		} else if (arg0 == 2) {
			this.anInt2702 = arg1.method2512();
			if (this.anInt2702 < 0) {
				this.stepMultipliers = new short[this.anInt2698];
				for (@Pc(73) int local73 = 0; local73 < this.anInt2698; local73++) {
					this.stepMultipliers[local73] = (short) arg1.method2512();
				}
			}
		} else if (arg0 == 3) {
			this.anInt2699 = this.anInt2706 = arg1.method2490();
		} else if (arg0 == 4) {
			this.randomSeed = arg1.method2490();
		} else if (arg0 == 5) {
			this.anInt2699 = arg1.method2490();
		} else if (arg0 == 6) {
			this.anInt2706 = arg1.method2490();
		}
	}

	@OriginalMember(owner = "client!gl", name = "f", descriptor = "(I)V")
	private void method2286() {
		@Pc(16) int local16;
		if (this.anInt2702 > 0) {
			this.stepMultipliers = new short[this.anInt2698];
			this.amplitudeMultipliers = new short[this.anInt2698];
			for (local16 = 0; local16 < this.anInt2698; local16++) {
				this.stepMultipliers[local16] = (short) (Math.pow((double) ((float) this.anInt2702 / 4096.0F), (double) local16) * 4096.0D);
				this.amplitudeMultipliers[local16] = (short) Math.pow(2.0D, (double) local16);
			}
		} else if (this.stepMultipliers != null && this.stepMultipliers.length == this.anInt2698) {
			this.amplitudeMultipliers = new short[this.anInt2698];
			for (local16 = 0; local16 < this.anInt2698; local16++) {
				this.amplitudeMultipliers[local16] = (short) Math.pow(2.0D, (double) local16);
			}
		}
	}

	@OriginalMember(owner = "client!gl", name = "a", descriptor = "(Z)V")
	@Override
	public void postDecode() {
		this.noiseArray = Class26.method643(this.randomSeed);
		this.method2286();
		for (@Pc(23) int local23 = this.anInt2698 - 1; local23 >= 1; local23--) {
			@Pc(30) short local30 = this.stepMultipliers[local23];
			if (local30 > 8) {
				return;
			}
			if (local30 < -8) {
				return;
			}
			this.anInt2698--;
		}
	}

	@OriginalMember(owner = "client!gl", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(9) int[] local9 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			this.method2283(local9, arg0);
		}
		return local9;
	}

@OriginalMember(owner = "client!gl", name = "H", descriptor = "Ljava/lang/String;")
	public static String aString30 = null;
@OriginalMember(owner = "client!gl", name = "Q", descriptor = "[I")
	public static final int[] anIntArray182 = new int[4096];
@OriginalMember(owner = "client!gl", name = "a", descriptor = "(IBLjava/lang/Object;I)[B")
	public static byte[] method2285(@OriginalArg(2) Object arg0, @OriginalArg(3) int arg1) {
		if (arg0 == null) {
			return null;
		} else if (arg0 instanceof byte[]) {
			@Pc(13) byte[] local13 = (byte[]) arg0;
			return Class230.method5097(arg1, local13);
		} else if (arg0 instanceof ByteArrayWrapper) {
			@Pc(25) ByteArrayWrapper local25 = (ByteArrayWrapper) arg0;
			return local25.readBlock(arg1);
		} else {
			throw new IllegalArgumentException();
		}
	}
@OriginalMember(owner = "client!gl", name = "d", descriptor = "(B)V")
	public static void method2288() {
		if (SignLink.anInt1987 != 3) {
			MapSpriteType.anInt1028 = -1;
		}
	}
}
