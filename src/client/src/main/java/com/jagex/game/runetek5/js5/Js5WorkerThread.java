package com.jagex.game.runetek5.js5;

import java.io.IOException;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.deob.Class229;
import com.jagex.game.runetek5.js5.request.Js5ResourceRequest;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Static148;

import com.jagex.game.runetek5.js5.*;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub12;
import com.jagex.game.runetek5.toolkit.opengl.effect.ReflectiveRipplingWaterEffect;
import com.jagex.game.runetek5.deob.Class168;
@OriginalClass("client!vn")
public final class Js5WorkerThread {

	@OriginalMember(owner = "client!vn", name = "v", descriptor = "Lclient!al;")
	private BufferedSocket socket;

	@OriginalMember(owner = "client!vn", name = "w", descriptor = "J")
	private long lastAttempt;

	@OriginalMember(owner = "client!vn", name = "y", descriptor = "I")
	private int latency;

	@OriginalMember(owner = "client!vn", name = "D", descriptor = "Lclient!je;")
	private Js5ResourceRequest aClass4_Sub1_Sub6_Sub1_2;

	@OriginalMember(owner = "client!vn", name = "j", descriptor = "Lclient!tn;")
	private final Class229 pendingUrgentRequests = new Class229();

	@OriginalMember(owner = "client!vn", name = "s", descriptor = "Lclient!tn;")
	private final Class229 inFlightUrgentRequests = new Class229();

	@OriginalMember(owner = "client!vn", name = "t", descriptor = "Lclient!tn;")
	private final Class229 prefetchRequests = new Class229();

	@OriginalMember(owner = "client!vn", name = "u", descriptor = "Lclient!tn;")
	private final Class229 pendingPrefetchRequests = new Class229();

	@OriginalMember(owner = "client!vn", name = "x", descriptor = "Lclient!iv;")
	private final Packet write = new Packet(4);

	@OriginalMember(owner = "client!vn", name = "A", descriptor = "I")
	public volatile int errors = 0;

	@OriginalMember(owner = "client!vn", name = "B", descriptor = "B")
	private byte xorCode = 0;

	@OriginalMember(owner = "client!vn", name = "C", descriptor = "I")
	public volatile int response = 0;

	@OriginalMember(owner = "client!vn", name = "z", descriptor = "Lclient!iv;")
	private final Packet read = new Packet(8);

	@OriginalMember(owner = "client!vn", name = "a", descriptor = "(B)I")
	private int getPrefetchRequestCount() {
		return this.prefetchRequests.method5093() + this.pendingPrefetchRequests.method5093();
	}

	@OriginalMember(owner = "client!vn", name = "a", descriptor = "(I)Z")
	public boolean isUrgentRequestQueueFull() {
		return this.getUrgentRequestCount() >= 20;
	}

	@OriginalMember(owner = "client!vn", name = "a", descriptor = "(ILclient!al;Z)V")
	public void method5461(@OriginalArg(1) BufferedSocket arg0, @OriginalArg(2) boolean arg1) {
		if (this.socket != null) {
			try {
				this.socket.method138();
			} catch (@Pc(14) Exception local14) {
			}
			this.socket = null;
		}
		this.socket = arg0;
		this.writeInitiating();
		this.writeLoggedIn(arg1);
		this.read.currentPosition = 0;
		this.aClass4_Sub1_Sub6_Sub1_2 = null;
		while (true) {
			@Pc(40) Js5ResourceRequest local40 = (Js5ResourceRequest) this.inFlightUrgentRequests.method5091();
			if (local40 == null) {
				while (true) {
					local40 = (Js5ResourceRequest) this.pendingPrefetchRequests.method5091();
					if (local40 == null) {
						if (this.xorCode != 0) {
							try {
								this.write.currentPosition = 0;
								this.write.method2551(4);
								this.write.method2551(this.xorCode);
								this.write.method2524(0);
								this.socket.method142(4, this.write.buffer);
							} catch (@Pc(102) IOException local102) {
								try {
									this.socket.method138();
								} catch (@Pc(108) Exception local108) {
								}
								this.response = -2;
								this.errors++;
								this.socket = null;
							}
						}
						this.latency = 0;
						this.lastAttempt = Class168.method3962();
						return;
					}
					this.prefetchRequests.method5088(local40);
				}
			}
			this.pendingUrgentRequests.method5088(local40);
		}
	}

	@OriginalMember(owner = "client!vn", name = "b", descriptor = "(B)V")
	private void writeInitiating() {
		if (this.socket == null) {
			return;
		}
		try {
			this.write.currentPosition = 0;
			this.write.method2551(6);
			this.write.method2517(3);
			this.socket.method142(4, this.write.buffer);
		} catch (@Pc(38) IOException local38) {
			try {
				this.socket.method138();
			} catch (@Pc(44) Exception local44) {
			}
			this.socket = null;
			this.errors++;
			this.response = -2;
		}
	}

	@OriginalMember(owner = "client!vn", name = "a", descriptor = "(ZB)V")
	public void writeLoggedIn(@OriginalArg(0) boolean arg0) {
		if (this.socket == null) {
			return;
		}
		try {
			this.write.currentPosition = 0;
			this.write.method2551(arg0 ? 2 : 3);
			this.write.method2517(0);
			this.socket.method142(4, this.write.buffer);
		} catch (@Pc(41) IOException local41) {
			try {
				this.socket.method138();
			} catch (@Pc(47) Exception local47) {
			}
			this.response = -2;
			this.socket = null;
			this.errors++;
		}
	}

	@OriginalMember(owner = "client!vn", name = "c", descriptor = "(B)V")
	public void rekey() {
		try {
			this.socket.method138();
		} catch (@Pc(9) Exception local9) {
		}
		this.socket = null;
		this.response = -1;
		this.xorCode = (byte) (Math.random() * 255.0D + 1.0D);
		this.errors++;
	}

	@OriginalMember(owner = "client!vn", name = "a", descriptor = "(IBBZI)Lclient!je;")
	public Js5ResourceRequest method5465(@OriginalArg(0) int arg0, @OriginalArg(1) byte arg1, @OriginalArg(3) boolean arg2, @OriginalArg(4) int arg3) {
		@Pc(19) long local19 = (long) ((arg0 << 16) + arg3);
		@Pc(23) Js5ResourceRequest local23 = new Js5ResourceRequest();
		local23.indexReference = local19;
		local23.highPriority = arg2;
		local23.aByte24 = arg1;
		if (arg2) {
			if (this.getUrgentRequestCount() >= 20) {
				throw new RuntimeException();
			}
			this.pendingUrgentRequests.method5088(local23);
		} else if (this.getPrefetchRequestCount() < 20) {
			this.prefetchRequests.method5088(local23);
		} else {
			throw new RuntimeException();
		}
		return local23;
	}

	@OriginalMember(owner = "client!vn", name = "d", descriptor = "(B)V")
	public void method5466() {
		if (this.socket != null) {
			this.socket.method132();
		}
	}

	@OriginalMember(owner = "client!vn", name = "e", descriptor = "(B)V")
	public void writeTerminate() {
		if (this.socket == null) {
			return;
		}
		try {
			this.write.currentPosition = 0;
			this.write.method2551(7);
			this.write.method2517(0);
			this.socket.method142(4, this.write.buffer);
		} catch (@Pc(32) IOException local32) {
			try {
				this.socket.method138();
			} catch (@Pc(38) Exception local38) {
			}
			this.response = -2;
			this.socket = null;
			this.errors++;
		}
	}

	@OriginalMember(owner = "client!vn", name = "b", descriptor = "(I)Z")
	public boolean loop() {
		@Pc(18) int local18;
		if (this.socket != null) {
			@Pc(11) long local11 = Class168.method3962();
			local18 = (int) (local11 - this.lastAttempt);
			this.lastAttempt = local11;
			if (local18 > 200) {
				local18 = 200;
			}
			this.latency += local18;
			if (this.latency > 30000) {
				try {
					this.socket.method138();
				} catch (@Pc(42) Exception local42) {
				}
				this.socket = null;
			}
		}
		if (this.socket == null) {
			return this.getUrgentRequestCount() == 0 && this.getPrefetchRequestCount() == 0;
		}
		try {
			this.socket.method141();
			for (@Pc(76) Js5ResourceRequest local76 = (Js5ResourceRequest) this.pendingUrgentRequests.method5089(); local76 != null; local76 = (Js5ResourceRequest) this.pendingUrgentRequests.method5092()) {
				this.write.currentPosition = 0;
				this.write.method2551(1);
				this.write.method2517((int) local76.indexReference);
				this.socket.method142(4, this.write.buffer);
				this.inFlightUrgentRequests.method5088(local76);
			}
			for (@Pc(122) Js5ResourceRequest local122 = (Js5ResourceRequest) this.prefetchRequests.method5089(); local122 != null; local122 = (Js5ResourceRequest) this.prefetchRequests.method5092()) {
				this.write.currentPosition = 0;
				this.write.method2551(0);
				this.write.method2517((int) local122.indexReference);
				this.socket.method142(4, this.write.buffer);
				this.pendingPrefetchRequests.method5088(local122);
			}
			for (local18 = 0; local18 < 100; local18++) {
				@Pc(177) int local177 = this.socket.method133();
				if (local177 < 0) {
					throw new IOException();
				}
				if (local177 == 0) {
					break;
				}
				this.latency = 0;
				@Pc(190) byte local190 = 0;
				if (this.aClass4_Sub1_Sub6_Sub1_2 == null) {
					local190 = 8;
				} else if (this.aClass4_Sub1_Sub6_Sub1_2.anInt3510 == 0) {
					local190 = 1;
				}
				@Pc(219) int local219;
				@Pc(226) int local226;
				@Pc(275) int local275;
				if (local190 <= 0) {
					local219 = this.aClass4_Sub1_Sub6_Sub1_2.aClass4_Sub12_4.buffer.length - this.aClass4_Sub1_Sub6_Sub1_2.aByte24;
					local226 = 512 - this.aClass4_Sub1_Sub6_Sub1_2.anInt3510;
					if (local219 - this.aClass4_Sub1_Sub6_Sub1_2.aClass4_Sub12_4.currentPosition < local226) {
						local226 = local219 - this.aClass4_Sub1_Sub6_Sub1_2.aClass4_Sub12_4.currentPosition;
					}
					if (local226 > local177) {
						local226 = local177;
					}
					this.socket.method131(this.aClass4_Sub1_Sub6_Sub1_2.aClass4_Sub12_4.currentPosition, local226, this.aClass4_Sub1_Sub6_Sub1_2.aClass4_Sub12_4.buffer);
					if (this.xorCode != 0) {
						for (local275 = 0; local275 < local226; local275++) {
							this.aClass4_Sub1_Sub6_Sub1_2.aClass4_Sub12_4.buffer[local275 + this.aClass4_Sub1_Sub6_Sub1_2.aClass4_Sub12_4.currentPosition] ^= this.xorCode;
						}
					}
					this.aClass4_Sub1_Sub6_Sub1_2.aClass4_Sub12_4.currentPosition += local226;
					this.aClass4_Sub1_Sub6_Sub1_2.anInt3510 += local226;
					if (this.aClass4_Sub1_Sub6_Sub1_2.aClass4_Sub12_4.currentPosition == local219) {
						this.aClass4_Sub1_Sub6_Sub1_2.method4662();
						this.aClass4_Sub1_Sub6_Sub1_2.waiting = false;
						this.aClass4_Sub1_Sub6_Sub1_2 = null;
					} else if (this.aClass4_Sub1_Sub6_Sub1_2.anInt3510 == 512) {
						this.aClass4_Sub1_Sub6_Sub1_2.anInt3510 = 0;
					}
				} else {
					local219 = local190 - this.read.currentPosition;
					if (local219 > local177) {
						local219 = local177;
					}
					this.socket.method131(this.read.currentPosition, local219, this.read.buffer);
					if (this.xorCode != 0) {
						for (local226 = 0; local226 < local219; local226++) {
							this.read.buffer[this.read.currentPosition + local226] ^= this.xorCode;
						}
					}
					this.read.currentPosition += local219;
					if (local190 <= this.read.currentPosition) {
						if (this.aClass4_Sub1_Sub6_Sub1_2 == null) {
							this.read.currentPosition = 0;
							local226 = this.read.method2490();
							local275 = this.read.method2536();
							@Pc(459) int local459 = this.read.method2490();
							@Pc(464) int local464 = this.read.method2529();
							@Pc(468) int local468 = local459 & 0x7F;
							@Pc(479) boolean local479 = (local459 & 0x80) != 0;
							@Pc(486) long local486 = (long) ((local226 << 16) + local275);
							@Pc(496) Js5ResourceRequest local496;
							if (local479) {
								for (local496 = (Js5ResourceRequest) this.pendingPrefetchRequests.method5089(); local496 != null && local496.indexReference != local486; local496 = (Js5ResourceRequest) this.pendingPrefetchRequests.method5092()) {
								}
							} else {
								for (local496 = (Js5ResourceRequest) this.inFlightUrgentRequests.method5089(); local496 != null && local496.indexReference != local486; local496 = (Js5ResourceRequest) this.inFlightUrgentRequests.method5092()) {
								}
							}
							if (local496 == null) {
								throw new IOException();
							}
							@Pc(549) int local549 = local468 == 0 ? 5 : 9;
							this.aClass4_Sub1_Sub6_Sub1_2 = local496;
							this.aClass4_Sub1_Sub6_Sub1_2.aClass4_Sub12_4 = new Packet(local549 + local464 + this.aClass4_Sub1_Sub6_Sub1_2.aByte24);
							this.aClass4_Sub1_Sub6_Sub1_2.aClass4_Sub12_4.method2551(local468);
							this.aClass4_Sub1_Sub6_Sub1_2.aClass4_Sub12_4.method2531(local464);
							this.read.currentPosition = 0;
							this.aClass4_Sub1_Sub6_Sub1_2.anInt3510 = 8;
						} else if (this.aClass4_Sub1_Sub6_Sub1_2.anInt3510 != 0) {
							throw new IOException();
						} else if (this.read.buffer[0] == -1) {
							this.aClass4_Sub1_Sub6_Sub1_2.anInt3510 = 1;
							this.read.currentPosition = 0;
						} else {
							this.aClass4_Sub1_Sub6_Sub1_2 = null;
						}
					}
				}
			}
			return true;
		} catch (@Pc(627) IOException local627) {
			try {
				this.socket.method138();
			} catch (@Pc(633) Exception local633) {
			}
			this.errors++;
			this.response = -2;
			this.socket = null;
			return this.getUrgentRequestCount() == 0 && this.getPrefetchRequestCount() == 0;
		}
	}

	@OriginalMember(owner = "client!vn", name = "c", descriptor = "(I)I")
	public int getUrgentRequestCount() {
		return this.pendingUrgentRequests.method5093() + this.inFlightUrgentRequests.method5093();
	}

	@OriginalMember(owner = "client!vn", name = "f", descriptor = "(B)Z")
	public boolean isPrefetchRequestQueueFull() {
		return this.getPrefetchRequestCount() >= 20;
	}

	@OriginalMember(owner = "client!vn", name = "e", descriptor = "(I)V")
	public void method5474() {
		if (this.socket != null) {
			this.socket.method138();
		}
	}

@OriginalMember(owner = "client!vn", name = "i", descriptor = "Lclient!mp;")
	public static final BasType aClass151_1 = new BasType();
@OriginalMember(owner = "client!vn", name = "q", descriptor = "I")
	public static int anInt7059 = 0;
@OriginalMember(owner = "client!vn", name = "a", descriptor = "(II)Z")
	public static boolean method5470(@OriginalArg(1) int arg0) {
		return arg0 >= 4 && arg0 <= 8;
	}
@OriginalMember(owner = "client!vn", name = "a", descriptor = "(Lclient!nf;I)Z")
	public static boolean method5473(@OriginalArg(0) MECType arg0) {
		if (arg0 == null) {
			return false;
		} else if (!arg0.aBoolean449) {
			return false;
		} else if (!arg0.method3778(Static148.PLAYER_VAR_PROVIDER)) {
			return false;
		} else if (Class4_Sub6_Sub12.aClass102_12.method2700((long) arg0.anInt4914) == null) {
			return ReflectiveRipplingWaterEffect.aClass102_1.method2700((long) arg0.anInt4919) == null;
		} else {
			return false;
		}
	}
}
