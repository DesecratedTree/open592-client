package com.jagex.game.runetek5.sound.player;

import java.awt.Component;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.impl.AudioNode;
import com.jagex.game.runetek5.deob.Static459;
import com.jagex.game.runetek5.sound.jagtheora.PcmStream;

import java.io.IOException;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.JFS32;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.sign.SignedResource;

import com.jagex.game.runetek5.sound.player.*;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.JavaThreadResource;
import com.jagex.game.runetek5.deob.Class168;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.GameCanvas;
@OriginalClass("client!ln")
public class PcmPlayer {

	@OriginalMember(owner = "client!ln", name = "c", descriptor = "Lclient!ti;")
	private PcmStream currentPcmStream;

	@OriginalMember(owner = "client!ln", name = "q", descriptor = "[I")
	public int[] bitpackedSoundData;

	@OriginalMember(owner = "client!ln", name = "x", descriptor = "I")
	private int anInt2443;

	@OriginalMember(owner = "client!ln", name = "z", descriptor = "I")
	public int bufferCapacity;

	@OriginalMember(owner = "client!ln", name = "B", descriptor = "I")
	public int sampleRate2;

	@OriginalMember(owner = "client!ln", name = "d", descriptor = "I")
	private final int maxActiveVoices = 32;

	@OriginalMember(owner = "client!ln", name = "e", descriptor = "Z")
	private boolean aBoolean226 = false;

	@OriginalMember(owner = "client!ln", name = "h", descriptor = "J")
	private long aLong78 = Class168.method3962();

	@OriginalMember(owner = "client!ln", name = "w", descriptor = "I")
	private int anInt2442 = 0;

	@OriginalMember(owner = "client!ln", name = "A", descriptor = "[Lclient!ti;")
	private final PcmStream[] inactiveStreams = new PcmStream[8];

	@OriginalMember(owner = "client!ln", name = "C", descriptor = "J")
	private long aLong79 = 0L;

	@OriginalMember(owner = "client!ln", name = "D", descriptor = "Z")
	private boolean aBoolean227 = true;

	@OriginalMember(owner = "client!ln", name = "y", descriptor = "I")
	private int streamUpdateCountdown = 0;

	@OriginalMember(owner = "client!ln", name = "E", descriptor = "I")
	private int anInt2447 = 0;

	@OriginalMember(owner = "client!ln", name = "G", descriptor = "[Lclient!ti;")
	private final PcmStream[] activeStreams = new PcmStream[8];

	@OriginalMember(owner = "client!ln", name = "F", descriptor = "J")
	private long aLong80 = 0L;

	@OriginalMember(owner = "client!ln", name = "H", descriptor = "I")
	private int anInt2448 = 0;

	@OriginalMember(owner = "client!ln", name = "a", descriptor = "(II)V")
	private void method2062() {
		this.streamUpdateCountdown -= 256;
		if (this.streamUpdateCountdown < 0) {
			this.streamUpdateCountdown = 0;
		}
		if (this.currentPcmStream != null) {
			this.currentPcmStream.skip(256);
		}
	}

	@OriginalMember(owner = "client!ln", name = "a", descriptor = "(I)V")
	public final synchronized void method2064() {
		this.aBoolean227 = true;
		try {
			this.open();
		} catch (@Pc(10) Exception local10) {
			this.close();
			this.aLong80 = Class168.method3962() + 2000L;
		}
	}

	@OriginalMember(owner = "client!ln", name = "b", descriptor = "(I)V")
	public void play(@OriginalArg(0) int bufferSize) throws Exception {
	}

	@OriginalMember(owner = "client!ln", name = "a", descriptor = "(Ljava/awt/Component;)V")
	public void init(@OriginalArg(0) Component component) throws Exception {
	}

	@OriginalMember(owner = "client!ln", name = "a", descriptor = "()I")
	protected int dataLeft() throws Exception {
		return this.bufferCapacity;
	}

	@OriginalMember(owner = "client!ln", name = "c", descriptor = "(I)V")
	public final synchronized void method2069() {
		if (this.aBoolean226) {
			return;
		}
		@Pc(14) long local14 = Class168.method3962();
		try {
			if (local14 > this.aLong78 + 500000L) {
				this.aLong78 = local14 - 500000L;
			}
			while (this.aLong78 + 5000L < local14) {
				this.method2062();
				this.aLong78 += 256000 / MECType.anInt4928;
			}
		} catch (@Pc(58) Exception local58) {
			this.aLong78 = local14;
		}
		if (this.bitpackedSoundData == null) {
			return;
		}
		try {
			if ((long) 0 != this.aLong80) {
				if (local14 < this.aLong80) {
					return;
				}
				this.play(this.bufferCapacity);
				this.aLong80 = 0L;
				this.aBoolean227 = true;
			}
			@Pc(94) int local94 = this.dataLeft();
			if (this.anInt2442 < this.anInt2447 - local94) {
				this.anInt2442 = this.anInt2447 - local94;
			}
			@Pc(115) int local115 = this.sampleRate2 + this.anInt2443;
			if (local115 + 256 > 16384) {
				local115 = 16128;
			}
			if (this.bufferCapacity < local115 + 256) {
				this.bufferCapacity += 1024;
				if (this.bufferCapacity > 16384) {
					this.bufferCapacity = 16384;
				}
				this.close();
				this.play(this.bufferCapacity);
				local94 = 0;
				this.aBoolean227 = true;
				if (this.bufferCapacity < local115 + 256) {
					local115 = this.bufferCapacity - 256;
					this.anInt2443 = local115 - this.sampleRate2;
				}
			}
			while (local94 < local115) {
				this.fill(this.bitpackedSoundData);
				this.writeToBuffer();
				local94 += 256;
			}
			if (this.aLong79 < local14) {
				if (this.aBoolean227) {
					this.aBoolean227 = false;
				} else if (this.anInt2442 == 0 && this.anInt2448 == 0) {
					this.close();
					this.aLong80 = local14 + 2000L;
					return;
				} else {
					this.anInt2443 = Math.min(this.anInt2448, this.anInt2442);
					this.anInt2448 = this.anInt2442;
				}
				this.aLong79 = local14 + 2000L;
				this.anInt2442 = 0;
			}
			this.anInt2447 = local94;
		} catch (@Pc(245) Exception local245) {
			this.close();
			this.aLong80 = local14 + 2000L;
		}
	}

	@OriginalMember(owner = "client!ln", name = "a", descriptor = "(Lclient!ti;B)V")
	public final synchronized void method2070(@OriginalArg(0) PcmStream arg0) {
		this.currentPcmStream = arg0;
	}

	@OriginalMember(owner = "client!ln", name = "b", descriptor = "()V")
	protected void close() {
	}

	@OriginalMember(owner = "client!ln", name = "c", descriptor = "()V")
	protected void open() throws Exception {
	}

	@OriginalMember(owner = "client!ln", name = "d", descriptor = "()V")
	protected void writeToBuffer() throws Exception {
	}

	@OriginalMember(owner = "client!ln", name = "d", descriptor = "(I)V")
	public final synchronized void method2074() {
		if (JavaThreadResource.aClass227_1 != null) {
			@Pc(15) boolean local15 = true;
			for (@Pc(17) int local17 = 0; local17 < 2; local17++) {
				if (JavaThreadResource.aClass227_1.players[local17] == this) {
					JavaThreadResource.aClass227_1.players[local17] = null;
				}
				if (JavaThreadResource.aClass227_1.players[local17] != null) {
					local15 = false;
				}
			}
			if (local15) {
				JavaThreadResource.aClass227_1.stopping = true;
				while (JavaThreadResource.aClass227_1.running) {
					Class259.method5503(50L);
				}
				JavaThreadResource.aClass227_1 = null;
			}
		}
		this.close();
		this.bitpackedSoundData = null;
		this.aBoolean226 = true;
	}

	@OriginalMember(owner = "client!ln", name = "a", descriptor = "(IILclient!ti;)V")
	private void method2075(@OriginalArg(1) int arg0, @OriginalArg(2) PcmStream arg1) {
		@Pc(7) int local7 = arg0 >> 5;
		@Pc(12) PcmStream local12 = this.inactiveStreams[local7];
		if (local12 == null) {
			this.activeStreams[local7] = arg1;
		} else {
			local12.nextStream = arg1;
		}
		this.inactiveStreams[local7] = arg1;
		arg1.streamPriority = arg0;
	}

	@OriginalMember(owner = "client!ln", name = "a", descriptor = "([II)V")
	private void fill(@OriginalArg(0) int[] samples) {
		@Pc(1) short local1 = 256;
		if (GpuFaceNormal.aBoolean649) {
			local1 = 512;
		}
		Static459.clear(samples, 0, local1);
		this.streamUpdateCountdown -= 256;
		if (this.currentPcmStream != null && this.streamUpdateCountdown <= 0) {
			this.streamUpdateCountdown += MECType.anInt4928 >> 4;
			ParticleList.method3547(this.currentPcmStream);
			this.method2075(this.currentPcmStream.getPriority(), this.currentPcmStream);
			@Pc(45) int local45 = 0;
			@Pc(47) int local47 = 255;
			@Pc(49) int local49 = 7;
			@Pc(56) int local56;
			label103: while (local47 != 0) {
				@Pc(61) int local61;
				if (local49 < 0) {
					local56 = local49 & 0x3;
					local61 = -(local49 >> 2);
				} else {
					local56 = local49;
					local61 = 0;
				}
				for (@Pc(72) int local72 = local47 >>> local56 & 0x11111111; local72 != 0; local72 >>>= 0x4) {
					if ((local72 & 0x1) != 0) {
						local47 &= ~(0x1 << local56);
						@Pc(88) PcmStream local88 = null;
						@Pc(93) PcmStream local93 = this.activeStreams[local56];
						label97: while (true) {
							while (true) {
								if (local93 == null) {
									break label97;
								}
								@Pc(97) AudioNode local97 = local93.aClass4_Sub4_5;
								if (local97 == null || local97.activeVoiceCount <= local61) {
									local93.fill = true;
									@Pc(121) int local121 = local93.getActiveVoiceCount();
									local45 += local121;
									if (local97 != null) {
										local97.activeVoiceCount += local121;
									}
									if (local45 >= this.maxActiveVoices) {
										break label103;
									}
									@Pc(140) PcmStream local140 = local93.method4860();
									if (local140 != null) {
										@Pc(145) int local145 = local93.streamPriority;
										while (local140 != null) {
											this.method2075(local145 * local140.getPriority() >> 8, local140);
											local140 = local93.method4857();
										}
									}
									@Pc(164) PcmStream local164 = local93.nextStream;
									local93.nextStream = null;
									if (local88 == null) {
										this.activeStreams[local56] = local164;
									} else {
										local88.nextStream = local164;
									}
									if (local164 == null) {
										this.inactiveStreams[local56] = local88;
									}
									local93 = local164;
								} else {
									local47 |= 0x1 << local56;
									local88 = local93;
									local93 = local93.nextStream;
								}
							}
						}
					}
					local56 += 4;
					local61++;
				}
				local49--;
			}
			for (local56 = 0; local56 < 8; local56++) {
				@Pc(207) PcmStream local207 = this.activeStreams[local56];
				this.activeStreams[local56] = this.inactiveStreams[local56] = null;
				while (local207 != null) {
					@Pc(221) PcmStream local221 = local207.nextStream;
					local207.nextStream = null;
					local207 = local221;
				}
			}
		}
		if (this.streamUpdateCountdown < 0) {
			this.streamUpdateCountdown = 0;
		}
		if (this.currentPcmStream != null) {
			this.currentPcmStream.fill(samples, 0, 256);
		}
		this.aLong78 = Class168.method3962();
	}

	@OriginalMember(owner = "client!ln", name = "c", descriptor = "(B)V")
	public final void method2077() {
		this.aBoolean227 = true;
	}

@OriginalMember(owner = "client!ln", name = "u", descriptor = "Lclient!l;")
	public static NativeSprite aClass57_11;
@OriginalMember(owner = "client!ln", name = "m", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_29 = new Class215(54, 12);
@OriginalMember(owner = "client!ln", name = "r", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_30 = new Class215(5, 5);
@OriginalMember(owner = "client!ln", name = "b", descriptor = "(B)V")
	public static void method2068() {
		@Pc(11) JFS32 local11 = null;
		try {
			@Pc(16) SignedResource local16 = Class126.aClass66_4.method1738("2");
			while (local16.anInt5760 == 0) {
				Class259.method5503(1L);
			}
			if (local16.anInt5760 == 1) {
				local11 = (JFS32) local16.anObject29;
				@Pc(38) byte[] local38 = new byte[(int) local11.method4717()];
				@Pc(53) int local53;
				for (@Pc(40) int local40 = 0; local40 < local38.length; local40 += local53) {
					local53 = local11.method4715(local40, local38.length - local40, local38);
					if (local53 == -1) {
						throw new IOException("EOF");
					}
				}
				GameCanvas.method4864(new Packet(local38));
			}
		} catch (@Pc(82) Exception local82) {
		}
		try {
			if (local11 != null) {
				local11.method4713();
			}
		} catch (@Pc(89) Exception local89) {
		}
	}
}
