package com.jagex.game.runetek5.buffer;

import java.math.BigInteger;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Static179;
import com.jagex.game.runetek5.deob.Static277;
import com.jagex.game.runetek5.cache.node.impl.VariableRateSoundPacket;
import com.jagex.game.runetek5.deob.Class269;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static312;
import com.jagex.game.runetek5.deob.Static32;
import com.jagex.game.runetek5.sound.jagtheora.AreaSoundStream;
import com.jagex.game.runetek5.buffer.*;
import com.jagex.game.runetek5.graphics.sprite.SpriteGL;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.cache.node.VorbisSound;
import com.jagex.game.runetek5.config.sequence.PathingEntitySequence;
import com.jagex.game.runetek5.deob.TextureCombinerOp;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.JavaThreadResource;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.deob.Class186;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.deob.GameCanvas;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSquareWaveform;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.config.midi.SoundEffect;
@OriginalClass("client!iv")
public class Packet extends Country {

	@OriginalMember(owner = "client!iv", name = "z", descriptor = "[B")
	public byte[] buffer;

	@OriginalMember(owner = "client!iv", name = "rb", descriptor = "I")
	public int currentPosition;

	static {
		new Class83("Join your channel by clicking 'Join Chat' and typing: ", "Klick auf 'Betreten' und gib ein: ", "Pour rejoindre votre canal, cliquez sur « Participer » et entrez : ", "Para entrar no seu canal, clique em \"Acessar bate-papo\" e digite: ");
	}

	@OriginalMember(owner = "client!iv", name = "<init>", descriptor = "(I)V")
	public Packet(@OriginalArg(0) int size) {
		this.buffer = Static277.method3931(size);
		this.currentPosition = 0;
	}

	@OriginalMember(owner = "client!iv", name = "<init>", descriptor = "([B)V")
	public Packet(@OriginalArg(0) byte[] src) {
		this.buffer = src;
		this.currentPosition = 0;
	}

	@OriginalMember(owner = "client!iv", name = "c", descriptor = "(B)I")
	public final int method2489() {
		return 128 - this.buffer[this.currentPosition++] & 0xFF;
	}

	@OriginalMember(owner = "client!iv", name = "a", descriptor = "(I)I")
	public final int method2490() {
		return this.buffer[this.currentPosition++] & 0xFF;
	}

	@OriginalMember(owner = "client!iv", name = "c", descriptor = "(I)I")
	public final int method2491() {
		this.currentPosition += 4;
		return (this.buffer[this.currentPosition - 3] & 0xFF) + (((this.buffer[this.currentPosition - 2] & 0xFF) << 24) - (-((this.buffer[this.currentPosition - 1] & 0xFF) << 16) - ((this.buffer[this.currentPosition - 4] & 0xFF) << 8)));
	}

	@OriginalMember(owner = "client!iv", name = "a", descriptor = "(II)J")
	public final long method2492(@OriginalArg(1) int arg0) {
		@Pc(2) int local2 = arg0 - 1;
		if (local2 < 0 || local2 > 7) {
			throw new IllegalArgumentException();
		}
		@Pc(29) int local29 = local2 * 8;
		@Pc(31) long local31 = 0L;
		while (local29 >= 0) {
			local31 |= ((long) this.buffer[this.currentPosition++] & 0xFFL) << local29;
			local29 -= 8;
		}
		return local31;
	}

	@OriginalMember(owner = "client!iv", name = "d", descriptor = "(B)I")
	public final int method2493() {
		this.currentPosition += 3;
		return ((this.buffer[this.currentPosition - 2] & 0xFF) << 8) + (((this.buffer[this.currentPosition - 3] & 0xFF) << 16) + (this.buffer[this.currentPosition - 1] & 0xFF));
	}

	@OriginalMember(owner = "client!iv", name = "a", descriptor = "(IB)V")
	public final void method2494(@OriginalArg(0) int arg0) {
		this.buffer[this.currentPosition++] = (byte) arg0;
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 8);
	}

	@OriginalMember(owner = "client!iv", name = "a", descriptor = "(BI)V")
	public final void method2495(@OriginalArg(1) int arg0) {
		this.buffer[this.currentPosition++] = (byte) (arg0 + 128);
	}

	@OriginalMember(owner = "client!iv", name = "b", descriptor = "(BI)V")
	public final void method2496(@OriginalArg(1) int arg0) {
		this.buffer[this.currentPosition++] = (byte) (arg0 + 128);
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 8);
	}

	@OriginalMember(owner = "client!iv", name = "d", descriptor = "(I)Ljava/lang/String;")
	public final String method2497() {
		@Pc(22) byte local22 = this.buffer[this.currentPosition++];
		if (local22 != 0) {
			throw new IllegalStateException("Bad version number in gjstr2");
		}
		@Pc(35) int local35 = this.currentPosition;
		while (this.buffer[this.currentPosition++] != 0) {
		}
		@Pc(58) int local58 = this.currentPosition - local35 - 1;
		return local58 == 0 ? "" : SpriteGL.method5293(this.buffer, local58, local35);
	}

	@OriginalMember(owner = "client!iv", name = "f", descriptor = "(I)I")
	public final int method2499() {
		@Pc(7) int local7 = 0;
		@Pc(11) int local11 = this.method2506();
		while (local11 == 32767) {
			local11 = this.method2506();
			local7 += 32767;
		}
		return local7 + local11;
	}

	@OriginalMember(owner = "client!iv", name = "a", descriptor = "(Z)Z")
	public final boolean method2500() {
		this.currentPosition -= 4;
		@Pc(17) int local17 = Static179.method2846(0, this.buffer, this.currentPosition);
		@Pc(21) int local21 = this.method2529();
		return local21 == local17;
	}

	@OriginalMember(owner = "client!iv", name = "b", descriptor = "(Z)J")
	public final long method2501() {
		@Pc(10) long local10 = (long) this.method2529() & 0xFFFFFFFFL;
		@Pc(22) long local22 = (long) this.method2529() & 0xFFFFFFFFL;
		return (local10 << 32) + local22;
	}

	@OriginalMember(owner = "client!iv", name = "g", descriptor = "(I)I")
	public final int method2502() {
		@Pc(16) int local16 = this.buffer[this.currentPosition] & 0xFF;
		return local16 < 128 ? this.method2490() - 64 : this.method2536() + -49152;
	}

	@OriginalMember(owner = "client!iv", name = "e", descriptor = "(B)I")
	public final int method2503() {
		this.currentPosition += 2;
		@Pc(37) int local37 = (this.buffer[this.currentPosition - 2] & 0xFF) + ((this.buffer[this.currentPosition - 1] & 0xFF) << 8);
		if (local37 > 32767) {
			local37 -= 65536;
		}
		return local37;
	}

	@OriginalMember(owner = "client!iv", name = "a", descriptor = "(JB)V")
	public final void method2504(@OriginalArg(0) long arg0) {
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 56);
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 48);
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 40);
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 32);
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 24);
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 16);
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 8);
		this.buffer[this.currentPosition++] = (byte) arg0;
	}

	@OriginalMember(owner = "client!iv", name = "c", descriptor = "(BI)V")
	public final void method2505(@OriginalArg(1) int arg0) {
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 8);
		this.buffer[this.currentPosition++] = (byte) (arg0 + 128);
	}

	@OriginalMember(owner = "client!iv", name = "f", descriptor = "(B)I")
	public final int method2506() {
		@Pc(17) int local17 = this.buffer[this.currentPosition] & 0xFF;
		return local17 < 128 ? this.method2490() : this.method2536() - 32768;
	}

	@OriginalMember(owner = "client!iv", name = "h", descriptor = "(I)B")
	public final byte method2507() {
		return (byte) -this.buffer[this.currentPosition++];
	}

	@OriginalMember(owner = "client!iv", name = "a", descriptor = "(BII[B)V")
	public final void method2508(@OriginalArg(1) int arg0, @OriginalArg(3) byte[] arg1) {
		for (@Pc(3) int local3 = 0; local3 < arg0; local3++) {
			arg1[local3] = this.buffer[this.currentPosition++];
		}
	}

	@OriginalMember(owner = "client!iv", name = "g", descriptor = "(B)I")
	public final int method2509() {
		this.currentPosition += 2;
		@Pc(39) int local39 = (this.buffer[this.currentPosition - 1] - 128 & 0xFF) + ((this.buffer[this.currentPosition - 2] & 0xFF) << 8);
		if (local39 > 32767) {
			local39 -= 65536;
		}
		return local39;
	}

	@OriginalMember(owner = "client!iv", name = "i", descriptor = "(I)I")
	public final int method2510() {
		this.currentPosition += 4;
		return (this.buffer[this.currentPosition - 2] & 0xFF) + ((this.buffer[this.currentPosition - 4] & 0xFF) << 16) + ((this.buffer[this.currentPosition + -3] & 0xFF) << 24) + ((this.buffer[this.currentPosition + -1] & 0xFF) << 8);
	}

	@OriginalMember(owner = "client!iv", name = "j", descriptor = "(I)I")
	public final int method2511() {
		this.currentPosition += 4;
		return (this.buffer[this.currentPosition - 4] & 0xFF) + ((this.buffer[this.currentPosition - 3] & 0xFF) << 8) + ((this.buffer[this.currentPosition + -1] & 0xFF) << 24) + ((this.buffer[this.currentPosition + -2] & 0xFF) << 16);
	}

	@OriginalMember(owner = "client!iv", name = "h", descriptor = "(B)I")
	public final int method2512() {
		this.currentPosition += 2;
		@Pc(38) int local38 = ((this.buffer[this.currentPosition - 2] & 0xFF) << 8) + (this.buffer[this.currentPosition - 1] & 0xFF);
		if (local38 > 32767) {
			local38 -= 65536;
		}
		return local38;
	}

	@OriginalMember(owner = "client!iv", name = "b", descriptor = "(II)V")
	public final void method2513(@OriginalArg(1) int arg0) {
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 16);
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 24);
		this.buffer[this.currentPosition++] = (byte) arg0;
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 8);
	}

	@OriginalMember(owner = "client!iv", name = "c", descriptor = "(II)V")
	public final void method2514(@OriginalArg(1) int arg0) {
		this.buffer[this.currentPosition - arg0 - 4] = (byte) (arg0 >> 24);
		this.buffer[this.currentPosition - arg0 - 3] = (byte) (arg0 >> 16);
		this.buffer[this.currentPosition - arg0 - 2] = (byte) (arg0 >> 8);
		this.buffer[this.currentPosition - arg0 - 1] = (byte) arg0;
	}

	@OriginalMember(owner = "client!iv", name = "i", descriptor = "(B)I")
	public final int method2515() {
		this.currentPosition += 2;
		return ((this.buffer[this.currentPosition - 1] & 0xFF) << 8) + (this.buffer[this.currentPosition - 2] - 128 & 0xFF);
	}

	@OriginalMember(owner = "client!iv", name = "k", descriptor = "(I)Ljava/lang/String;")
	public final String method2516() {
		if (this.buffer[this.currentPosition] == 0) {
			this.currentPosition++;
			return null;
		} else {
			return this.method2537();
		}
	}

	@OriginalMember(owner = "client!iv", name = "d", descriptor = "(II)V")
	public final void method2517(@OriginalArg(1) int arg0) {
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 16);
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 8);
		this.buffer[this.currentPosition++] = (byte) arg0;
	}

	@OriginalMember(owner = "client!iv", name = "a", descriptor = "(ILjava/lang/String;)V")
	public final void method2518(@OriginalArg(1) String arg0) {
		@Pc(7) int local7 = arg0.indexOf(0);
		if (local7 >= 0) {
			throw new IllegalArgumentException("NUL character at " + local7 + " - cannot pjstr");
		}
		this.currentPosition += NativeBillboardFace.method2843(arg0, arg0.length(), this.currentPosition, this.buffer);
		this.buffer[this.currentPosition++] = 0;
	}

	@OriginalMember(owner = "client!iv", name = "a", descriptor = "(IZ[BI)V")
	public final void method2519(@OriginalArg(2) byte[] arg0, @OriginalArg(3) int arg1) {
		for (@Pc(5) int local5 = 0; local5 < arg1; local5++) {
			this.buffer[this.currentPosition++] = arg0[local5];
		}
	}

	@OriginalMember(owner = "client!iv", name = "l", descriptor = "(I)I")
	public final int method2520() {
		return this.buffer[this.currentPosition++] - 128 & 0xFF;
	}

	@OriginalMember(owner = "client!iv", name = "e", descriptor = "(II)V")
	public final void method2521(@OriginalArg(1) int arg0) {
		this.buffer[this.currentPosition++] = (byte) (128 - arg0);
	}

	@OriginalMember(owner = "client!iv", name = "a", descriptor = "(Ljava/math/BigInteger;BLjava/math/BigInteger;)V")
	public final void method2522(@OriginalArg(0) BigInteger arg0, @OriginalArg(2) BigInteger arg1) {
		@Pc(6) int local6 = this.currentPosition;
		this.currentPosition = 0;
		@Pc(12) byte[] local12 = new byte[local6];
		this.method2508(local6, local12);
		@Pc(23) BigInteger local23 = new BigInteger(local12);
		@Pc(28) BigInteger local28 = local23.modPow(arg0, arg1);
		@Pc(31) byte[] local31 = local28.toByteArray();
		this.currentPosition = 0;
		this.method2551(local31.length);
		this.method2519(local31, local31.length);
	}

	@OriginalMember(owner = "client!iv", name = "m", descriptor = "(I)I")
	public final int method2523() {
		return -this.buffer[this.currentPosition++] & 0xFF;
	}

	@OriginalMember(owner = "client!iv", name = "d", descriptor = "(BI)V")
	public final void method2524(@OriginalArg(1) int arg0) {
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 8);
		this.buffer[this.currentPosition++] = (byte) arg0;
	}

	@OriginalMember(owner = "client!iv", name = "f", descriptor = "(II)I")
	public final int method2525(@OriginalArg(0) int arg0) {
		@Pc(16) int local16 = Static179.method2846(arg0, this.buffer, this.currentPosition);
		this.method2531(local16);
		return local16;
	}

	@OriginalMember(owner = "client!iv", name = "a", descriptor = "(IJB)V")
	public final void method2526(@OriginalArg(0) int arg0, @OriginalArg(1) long arg1) {
		@Pc(6) int local6 = arg0 - 1;
		if (local6 < 0 || local6 > 7) {
			throw new IllegalArgumentException();
		}
		for (@Pc(24) int local24 = local6 * 8; local24 >= 0; local24 -= 8) {
			this.buffer[this.currentPosition++] = (byte) (arg1 >> local24);
		}
	}

	@OriginalMember(owner = "client!iv", name = "j", descriptor = "(B)I")
	public final int method2528() {
		@Pc(16) byte local16 = this.buffer[this.currentPosition++];
		@Pc(23) int local23 = 0;
		while (local16 < 0) {
			local23 = (local23 | local16 & 0x7F) << 7;
			local16 = this.buffer[this.currentPosition++];
		}
		return local23 | local16;
	}

	@OriginalMember(owner = "client!iv", name = "k", descriptor = "(B)I")
	public final int method2529() {
		this.currentPosition += 4;
		return ((this.buffer[this.currentPosition - 2] & 0xFF) << 8) + (this.buffer[this.currentPosition - 4] << 24 & 0xFF000000) + ((this.buffer[this.currentPosition + -3] & 0xFF) << 16) + (this.buffer[this.currentPosition + -1] & 0xFF);
	}

	@OriginalMember(owner = "client!iv", name = "c", descriptor = "(IB)V")
	public final void method2531(@OriginalArg(0) int arg0) {
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 24);
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 16);
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 8);
		this.buffer[this.currentPosition++] = (byte) arg0;
	}

	@OriginalMember(owner = "client!iv", name = "g", descriptor = "(II)V")
	public final void method2532(@OriginalArg(0) int arg0) {
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 8);
		this.buffer[this.currentPosition++] = (byte) arg0;
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 24);
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 16);
	}

	@OriginalMember(owner = "client!iv", name = "h", descriptor = "(II)V")
	public final void method2533(@OriginalArg(0) int arg0) {
		this.buffer[this.currentPosition++] = (byte) -arg0;
	}

	@OriginalMember(owner = "client!iv", name = "o", descriptor = "(I)B")
	public final byte method2534() {
		return (byte) (128 - this.buffer[this.currentPosition++]);
	}

	@OriginalMember(owner = "client!iv", name = "p", descriptor = "(I)I")
	public final int method2535() {
		this.currentPosition += 2;
		@Pc(39) int local39 = ((this.buffer[this.currentPosition - 1] & 0xFF) << 8) + (this.buffer[this.currentPosition - 2] - 128 & 0xFF);
		if (local39 > 32767) {
			local39 -= 65536;
		}
		return local39;
	}

	@OriginalMember(owner = "client!iv", name = "l", descriptor = "(B)I")
	public final int method2536() {
		this.currentPosition += 2;
		return (this.buffer[this.currentPosition - 1] & 0xFF) + ((this.buffer[this.currentPosition - 2] & 0xFF) << 8);
	}

	@OriginalMember(owner = "client!iv", name = "m", descriptor = "(B)Ljava/lang/String;")
	public final String method2537() {
		@Pc(6) int local6 = this.currentPosition;
		while (this.buffer[this.currentPosition++] != 0) {
		}
		@Pc(31) int local31 = this.currentPosition - local6 - 1;
		return local31 == 0 ? "" : SpriteGL.method5293(this.buffer, local31, local6);
	}

	@OriginalMember(owner = "client!iv", name = "q", descriptor = "(I)I")
	public final int method2538() {
		this.currentPosition += 2;
		return (this.buffer[this.currentPosition - 2] & 0xFF) + ((this.buffer[this.currentPosition - 1] & 0xFF) << 8);
	}

	@OriginalMember(owner = "client!iv", name = "a", descriptor = "([II)V")
	public final void method2540(@OriginalArg(0) int[] arg0) {
		@Pc(10) int local10 = this.currentPosition / 8;
		this.currentPosition = 0;
		for (@Pc(19) int local19 = 0; local19 < local10; local19++) {
			@Pc(25) int local25 = this.method2529();
			@Pc(29) int local29 = this.method2529();
			@Pc(31) int local31 = 0;
			@Pc(35) int local35 = 32;
			while (local35-- > 0) {
				local25 += arg0[local31 & 0x3] + local31 ^ local29 + (local29 << 4 ^ local29 >>> 5);
				local31 += -1640531527;
				local29 += arg0[local31 >>> 11 & 0xCB000003] + local31 ^ local25 + (local25 << 4 ^ local25 >>> 5);
			}
			this.currentPosition -= 8;
			this.method2531(local25);
			this.method2531(local29);
		}
	}

	@OriginalMember(owner = "client!iv", name = "e", descriptor = "(BI)V")
	public final void method2541(@OriginalArg(1) int arg0) {
		this.buffer[this.currentPosition++] = (byte) arg0;
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 8);
	}

	@OriginalMember(owner = "client!iv", name = "c", descriptor = "(Z)B")
	public final byte method2542() {
		return this.buffer[this.currentPosition++];
	}

	@OriginalMember(owner = "client!iv", name = "f", descriptor = "(BI)V")
	public final void method2543(@OriginalArg(1) int arg0) {
		if (arg0 >= 0 && arg0 < 128) {
			this.method2551(arg0);
		} else if (arg0 >= 0 && arg0 < 32768) {
			this.method2524(arg0 + 32768);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@OriginalMember(owner = "client!iv", name = "a", descriptor = "(III[I)V")
	public final void method2544(@OriginalArg(1) int arg0, @OriginalArg(3) int[] arg1) {
		@Pc(8) int local8 = this.currentPosition;
		this.currentPosition = 5;
		@Pc(18) int local18 = (arg0 - 5) / 8;
		for (@Pc(20) int local20 = 0; local20 < local18; local20++) {
			@Pc(32) int local32 = this.method2529();
			@Pc(36) int local36 = this.method2529();
			@Pc(38) int local38 = -957401312;
			@Pc(42) int local42 = 32;
			while (local42-- > 0) {
				local36 -= (local32 >>> 5 ^ local32 << 4) + local32 ^ local38 + arg1[local38 >>> 11 & 0x4C600003];
				local38 -= -1640531527;
				local32 -= local36 + (local36 << 4 ^ local36 >>> 5) ^ arg1[local38 & 0x3] + local38;
			}
			this.currentPosition -= 8;
			this.method2531(local32);
			this.method2531(local36);
		}
		this.currentPosition = local8;
	}

	@OriginalMember(owner = "client!iv", name = "i", descriptor = "(II)V")
	public final void method2545(@OriginalArg(1) int arg0) {
		this.buffer[this.currentPosition - arg0 - 1] = (byte) arg0;
	}

	@OriginalMember(owner = "client!iv", name = "a", descriptor = "(ZI)V")
	public final void method2546(@OriginalArg(1) int arg0) {
		if ((arg0 & 0xFFFFFF80) != 0) {
			if ((arg0 & 0xFFFFC000) != 0) {
				if ((arg0 & 0xFFE00000) != 0) {
					if ((arg0 & 0xF0000000) != 0) {
						this.method2551(arg0 >>> 28 | 0x80);
					}
					this.method2551(arg0 >>> 21 | 0x80);
				}
				this.method2551(arg0 >>> 14 | 0x80);
			}
			this.method2551(arg0 >>> 7 | 0x80);
		}
		this.method2551(arg0 & 0x7F);
	}

	@OriginalMember(owner = "client!iv", name = "j", descriptor = "(II)V")
	public final void method2547(@OriginalArg(1) int arg0) {
		this.buffer[this.currentPosition - arg0 - 2] = (byte) (arg0 >> 8);
		this.buffer[this.currentPosition - arg0 - 1] = (byte) arg0;
	}

	@OriginalMember(owner = "client!iv", name = "r", descriptor = "(I)I")
	public final int method2548() {
		this.currentPosition += 2;
		return ((this.buffer[this.currentPosition - 2] & 0xFF) << 8) + (this.buffer[this.currentPosition - 1] - 128 & 0xFF);
	}

	@OriginalMember(owner = "client!iv", name = "s", descriptor = "(I)B")
	public final byte method2549() {
		return (byte) (this.buffer[this.currentPosition++] - 128);
	}

	@OriginalMember(owner = "client!iv", name = "k", descriptor = "(II)V")
	public final void method2550(@OriginalArg(1) int arg0) {
		this.buffer[this.currentPosition++] = (byte) arg0;
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 8);
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 16);
		this.buffer[this.currentPosition++] = (byte) (arg0 >> 24);
	}

	@OriginalMember(owner = "client!iv", name = "l", descriptor = "(II)V")
	public final void method2551(@OriginalArg(1) int arg0) {
		this.buffer[this.currentPosition++] = (byte) arg0;
	}

@OriginalMember(owner = "client!iv", name = "s", descriptor = "[I")
	public static int[] anIntArray188;
@OriginalMember(owner = "client!iv", name = "eb", descriptor = "I")
	public static int anInt2985;
@OriginalMember(owner = "client!iv", name = "S", descriptor = "I")
	public static int anInt2973 = -1;
@OriginalMember(owner = "client!iv", name = "n", descriptor = "(I)V")
	public static void method2527() {
		for (@Pc(7) int local7 = 0; local7 < Class16.anInt6886; local7++) {
			@Pc(13) Class269 local13 = OpenGLTextureCache.aClass269Array1[local7];
			@Pc(15) boolean local15 = false;
			@Pc(131) int local131;
			if (local13.aClass4_Sub15_Sub3_4 == null) {
				local13.anInt7336--;
				if (local13.anInt7336 < (local13.aByte104 == 2 ? -1500 : -10)) {
					local15 = true;
				} else {
					if (local13.aByte104 == 1 && local13.aClass241_1 == null) {
						local13.aClass241_1 = SoundEffect.getSoundEffect(Class4_Sub6_Sub4.aClass76_19, local13.anInt7340, 0);
						if (local13.aClass241_1 == null) {
							continue;
						}
						local13.anInt7336 += local13.aClass241_1.getDelay();
					} else if (local13.aByte104 == 2 && (local13.aClass4_Sub13_1 == null || local13.aClass4_Sub4_Sub1_2 == null)) {
						if (local13.aClass4_Sub13_1 == null) {
							local13.aClass4_Sub13_1 = VorbisSound.method1316(Static312.aClass76_66, local13.anInt7340);
						}
						if (local13.aClass4_Sub13_1 == null) {
							continue;
						}
						if (local13.aClass4_Sub4_Sub1_2 == null) {
							local13.aClass4_Sub4_Sub1_2 = local13.aClass4_Sub13_1.toPcmSound(new int[] { 22050 });
							if (local13.aClass4_Sub4_Sub1_2 == null) {
								continue;
							}
						}
					}
					if (local13.anInt7336 < 0) {
						if (local13.anInt7341 == 0) {
							local131 = local13.anInt7337 * TextureOpBrightness.aClass50_Sub1_1.anInt3448 >> 8;
						} else {
							@Pc(140) int local140 = local13.anInt7341 >> 24 & 0x3;
							if (local140 == Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82) {
								@Pc(159) int local159 = (local13.anInt7341 & 0xFF) << 7;
								@Pc(166) int local166 = local13.anInt7341 >> 16 & 0xFF;
								@Pc(176) int local176 = (local166 << 7) + 64 - Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893;
								if (local176 < 0) {
									local176 = -local176;
								}
								@Pc(188) int local188 = local13.anInt7341 >> 8 & 0xFF;
								@Pc(198) int local198 = (local188 << 7) + 64 - Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892;
								if (local198 < 0) {
									local198 = -local198;
								}
								@Pc(212) int local212 = local198 + local176 - 128;
								if (local212 > local159) {
									local13.anInt7336 = -99999;
									continue;
								}
								if (local212 < 0) {
									local212 = 0;
								}
								local131 = (local159 - local212) * TextureOpBrightness.aClass50_Sub1_1.anInt3449 * local13.anInt7337 / local159 >> 8;
							} else {
								local131 = 0;
							}
						}
						if (local131 > 0) {
							@Pc(246) VariableRateSoundPacket local246 = null;
							if (local13.aByte104 == 1) {
								local246 = local13.aClass241_1.getMixedAudio().resample(PathingEntitySequence.keyRecorder);
							} else if (local13.aByte104 == 2) {
								local246 = local13.aClass4_Sub4_Sub1_2;
							}
							@Pc(274) AreaSoundStream local274 = local13.aClass4_Sub15_Sub3_4 = AreaSoundStream.method4489(local246, local131);
							local274.setNumLoops(local13.anInt7342 - 1);
							GameCanvas.aClass4_Sub15_Sub2_2.addSubStream(local274);
						}
					}
				}
			} else if (!local13.aClass4_Sub15_Sub3_4.method5683()) {
				local15 = true;
			}
			if (local15) {
				Class16.anInt6886--;
				for (local131 = local7; local131 < Class16.anInt6886; local131++) {
					OpenGLTextureCache.aClass269Array1[local131] = OpenGLTextureCache.aClass269Array1[local131 + 1];
				}
				local7--;
			}
		}
		if (TextureOpSquareWaveform.aBoolean666 && !Class16.method5336()) {
			if (TextureOpBrightness.aClass50_Sub1_1.anInt3439 != 0 && JavaThreadResource.mapBuildLevel != -1) {
				Static32.method625(TextureOpBrightness.aClass50_Sub1_1.anInt3439, JavaThreadResource.mapBuildLevel, TextureCombinerOp.aClass76_54);
			}
			TextureOpSquareWaveform.aBoolean666 = false;
		} else if (TextureOpBrightness.aClass50_Sub1_1.anInt3439 != 0 && JavaThreadResource.mapBuildLevel != -1 && !Class16.method5336()) {
			PingWorker.method5476(Class186.aClass215_67);
			ParticleNode.aClass4_Sub12_Sub1_5.method2531(JavaThreadResource.mapBuildLevel);
			JavaThreadResource.mapBuildLevel = -1;
		}
	}
@OriginalMember(owner = "client!iv", name = "b", descriptor = "(IB)I")
	public static int method2530(@OriginalArg(0) int arg0) {
		return arg0 & 0xFF;
	}
@OriginalMember(owner = "client!iv", name = "a", descriptor = "(BII)I")
	public static int method2539(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		@Pc(12) int local12 = arg1 >>> 24;
		@Pc(16) int local16 = 255 - local12;
		@Pc(34) int local34 = ((arg1 & 0xFF00FF) * local12 & 0xFF00FF00 | local12 * (arg1 & 0xFF00) & 0xFF0000) >>> 8;
		return local34 + (((arg0 & 0xFF00) * local16 & 0xFF0000 | local16 * (arg0 & 0xFF00FF) & 0xFF00FF00) >>> 8);
	}
}
