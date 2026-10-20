package com.jagex.game.runetek5.js5.request.local;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class222;
import com.jagex.game.runetek5.deob.Class229;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub6_Sub2;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.sign.SignedResource;

import com.jagex.game.runetek5.deob.Class208;
import com.jagex.game.runetek5.deob.Static340;
import com.jagex.game.runetek5.js5.request.local.*;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.deob.Class50;
import com.jagex.game.runetek5.cache.node.StringNode;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchNode;
import com.jagex.game.runetek5.deob.Class4_Sub36;
import com.jagex.game.runetek5.graphics.gl.GlShadowMap;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.deob.Class4_Sub18;
import com.jagex.game.runetek5.deob.Class4_Sub21_Sub2;
import com.jagex.game.runetek5.deob.Class210;
@OriginalClass("client!vl")
public final class LocalRequester implements Runnable {

	@OriginalMember(owner = "client!vl", name = "g", descriptor = "Lclient!tn;")
	private final Class229 queue = new Class229();

	@OriginalMember(owner = "client!vl", name = "q", descriptor = "I")
	public int requestCount = 0;

	@OriginalMember(owner = "client!vl", name = "p", descriptor = "Z")
	private boolean shutdown = false;

	@OriginalMember(owner = "client!vl", name = "n", descriptor = "Ljava/lang/Thread;")
	private Thread executingThread;

	@OriginalMember(owner = "client!vl", name = "<init>", descriptor = "(Lclient!et;)V")
	public LocalRequester(@OriginalArg(0) SignLink signLink) {
		@Pc(20) SignedResource local20 = signLink.method1741(5, this);
		while (local20.anInt5760 == 0) {
			Class259.method5503(10L);
		}
		if (local20.anInt5760 == 2) {
			throw new RuntimeException();
		}
		this.executingThread = (Thread) local20.anObject29;
	}

	@OriginalMember(owner = "client!vl", name = "a", descriptor = "(ILclient!st;I)Lclient!lf;")
	public Class4_Sub1_Sub6_Sub2 method5431(@OriginalArg(1) Class222 arg0, @OriginalArg(2) int arg1) {
		@Pc(7) Class4_Sub1_Sub6_Sub2 local7 = new Class4_Sub1_Sub6_Sub2();
		local7.highPriority = false;
		local7.aClass222_1 = arg0;
		local7.anInt4117 = 3;
		local7.indexReference = arg1;
		this.method5434(local7);
		return local7;
	}

	@OriginalMember(owner = "client!vl", name = "a", descriptor = "(Lclient!st;II)Lclient!lf;")
	public Class4_Sub1_Sub6_Sub2 method5432(@OriginalArg(0) Class222 arg0, @OriginalArg(2) int arg1) {
		@Pc(9) Class4_Sub1_Sub6_Sub2 local9 = new Class4_Sub1_Sub6_Sub2();
		local9.anInt4117 = 1;
		@Pc(15) Class229 local15 = this.queue;
		synchronized (this.queue) {
			@Pc(23) Class4_Sub1_Sub6_Sub2 local23 = (Class4_Sub1_Sub6_Sub2) this.queue.method5089();
			while (true) {
				if (local23 == null) {
					break;
				}
				if ((long) arg1 == local23.indexReference && local23.aClass222_1 == arg0 && local23.anInt4117 == 2) {
					local9.waiting = false;
					local9.aByteArray50 = local23.aByteArray50;
					return local9;
				}
				local23 = (Class4_Sub1_Sub6_Sub2) this.queue.method5092();
			}
		}
		local9.aByteArray50 = arg0.method4981(arg1);
		local9.highPriority = true;
		local9.waiting = false;
		return local9;
	}

	@OriginalMember(owner = "client!vl", name = "a", descriptor = "(I)V")
	public void method5433() {
		this.shutdown = true;
		@Pc(9) Class229 local9 = this.queue;
		synchronized (this.queue) {
			this.queue.notifyAll();
		}
		try {
			this.executingThread.join();
		} catch (@Pc(30) InterruptedException local30) {
		}
		this.executingThread = null;
	}

	@OriginalMember(owner = "client!vl", name = "run", descriptor = "()V")
	@Override
	public void run() {
		while (!this.shutdown) {
			@Pc(10) Class229 local10 = this.queue;
			@Pc(18) Class4_Sub1_Sub6_Sub2 local18;
			synchronized (this.queue) {
				local18 = (Class4_Sub1_Sub6_Sub2) this.queue.method5091();
				if (local18 == null) {
					try {
						this.queue.wait();
					} catch (@Pc(25) InterruptedException local25) {
					}
					continue;
				}
				this.requestCount--;
			}
			try {
				if (local18.anInt4117 == 2) {
					local18.aClass222_1.method4982(local18.aByteArray50, (int) local18.indexReference, local18.aByteArray50.length);
				} else if (local18.anInt4117 == 3) {
					local18.aByteArray50 = local18.aClass222_1.method4981((int) local18.indexReference);
				}
			} catch (@Pc(74) Exception local74) {
				Class64_Sub1.method1654(local74, null);
			}
			local18.waiting = false;
		}
	}

	@OriginalMember(owner = "client!vl", name = "a", descriptor = "(ILclient!lf;)V")
	private void method5434(@OriginalArg(1) Class4_Sub1_Sub6_Sub2 arg0) {
		@Pc(2) Class229 local2 = this.queue;
		synchronized (this.queue) {
			this.queue.method5088(arg0);
			this.requestCount++;
			this.queue.notifyAll();
		}
	}

	@OriginalMember(owner = "client!vl", name = "a", descriptor = "(II[BLclient!st;)Lclient!lf;")
	public Class4_Sub1_Sub6_Sub2 method5435(@OriginalArg(1) int arg0, @OriginalArg(2) byte[] arg1, @OriginalArg(3) Class222 arg2) {
		@Pc(13) Class4_Sub1_Sub6_Sub2 local13 = new Class4_Sub1_Sub6_Sub2();
		local13.indexReference = arg0;
		local13.anInt4117 = 2;
		local13.aByteArray50 = arg1;
		local13.aClass222_1 = arg2;
		local13.highPriority = false;
		this.method5434(local13);
		return local13;
	}

@OriginalMember(owner = "client!vl", name = "i", descriptor = "Lclient!rq;")
	public static Class208 aClass208_1;
@OriginalMember(owner = "client!vl", name = "k", descriptor = "I")
	public static int anInt7011;
@OriginalMember(owner = "client!vl", name = "o", descriptor = "I")
	public static int anInt7014;
@OriginalMember(owner = "client!vl", name = "r", descriptor = "[B")
	public static final byte[] aByteArray102 = new byte[16384];
@OriginalMember(owner = "client!vl", name = "a", descriptor = "(IBI)Z")
	public static boolean method5437(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		return (arg1 & 0x800) != 0;
	}
@OriginalMember(owner = "client!vl", name = "a", descriptor = "(BILjava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;I)V")
	public static void method5438(@OriginalArg(1) int arg0, @OriginalArg(2) String arg1, @OriginalArg(3) String arg2, @OriginalArg(4) String arg3, @OriginalArg(5) int arg4, @OriginalArg(6) String arg5, @OriginalArg(7) int arg6) {
		for (@Pc(7) int local7 = 99; local7 > 0; local7--) {
			Static340.anIntArray411[local7] = Static340.anIntArray411[local7 - 1];
			ParticleNode.anIntArray267[local7] = ParticleNode.anIntArray267[local7 - 1];
			Class210.aStringArray32[local7] = Class210.aStringArray32[local7 - 1];
			MonoStereoData.aStringArray9[local7] = MonoStereoData.aStringArray9[local7 - 1];
			Class4_Sub1_Sub2.aStringArray2[local7] = Class4_Sub1_Sub2.aStringArray2[local7 - 1];
			Class4_Sub21_Sub2.aStringArray15[local7] = Class4_Sub21_Sub2.aStringArray15[local7 - 1];
			MusicPatchNode.anIntArray294[local7] = MusicPatchNode.anIntArray294[local7 - 1];
		}
		Static340.anIntArray411[0] = arg6;
		Class210.aStringArray32[0] = arg5;
		ParticleNode.anIntArray267[0] = arg4;
		MonoStereoData.aStringArray9[0] = arg1;
		Class4_Sub1_Sub2.aStringArray2[0] = arg2;
		GlShadowMap.anInt6639++;
		StringNode.anInt5303 = MusicPatch.anInt5640;
		Class4_Sub21_Sub2.aStringArray15[0] = arg3;
		MusicPatchNode.anIntArray294[0] = arg0;
	}
@OriginalMember(owner = "client!vl", name = "a", descriptor = "(II)V")
	public static void method5439(@OriginalArg(0) int arg0) {
		if (!Class4_Sub18.method2034(arg0)) {
			return;
		}
		@Pc(14) com.jagex.game.runetek5.config.rsinterface.Component[] local14 = Class4_Sub36.aClass247ArrayArray2[arg0];
		for (@Pc(16) int local16 = 0; local16 < local14.length; local16++) {
			@Pc(22) com.jagex.game.runetek5.config.rsinterface.Component local22 = local14[local16];
			if (local22 != null) {
				local22.anInt6783 = 1;
				local22.anInt6829 = 0;
				local22.anInt6815 = 0;
			}
		}
	}
@OriginalMember(owner = "client!vl", name = "c", descriptor = "(I)Z")
	public static boolean method5440() {
		return Class50.aBoolean292;
	}
}
