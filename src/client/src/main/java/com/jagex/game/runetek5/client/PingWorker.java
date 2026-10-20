package com.jagex.game.runetek5.client;

import jagex3.jagmisc.jagmisc;
import java.net.InetAddress;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.deob.Class183;

import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;

import com.jagex.game.runetek5.client.*;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub6_Sub2;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
@OriginalClass("client!vo")
public final class PingWorker implements Runnable {

	@OriginalMember(owner = "client!vo", name = "a", descriptor = "Lclient!pk;")
	private final Class183 aClass183_49 = new Class183();

	@OriginalMember(owner = "client!vo", name = "d", descriptor = "Ljava/lang/Thread;")
	private Thread thread = new Thread(this);

	@OriginalMember(owner = "client!vo", name = "<init>", descriptor = "()V")
	public PingWorker() {
		this.thread.setDaemon(true);
		this.thread.start();
	}

	@OriginalMember(owner = "client!vo", name = "a", descriptor = "(I)V")
	public void close() {
		if (this.thread == null) {
			return;
		}
		this.method5477(new Country());
		try {
			this.thread.join();
		} catch (@Pc(20) InterruptedException local20) {
		}
		this.thread = null;
	}

	@OriginalMember(owner = "client!vo", name = "a", descriptor = "(ILclient!vu;)V")
	private void method5477(@OriginalArg(1) Country arg0) {
		@Pc(10) Class183 local10 = this.aClass183_49;
		synchronized (this.aClass183_49) {
			this.aClass183_49.method4137(arg0);
			this.aClass183_49.notify();
		}
	}

	@OriginalMember(owner = "client!vo", name = "a", descriptor = "(ILjava/lang/String;)Lclient!va;")
	public PingRequest requestPing(@OriginalArg(1) String arg0) {
		if (this.thread == null) {
			throw new IllegalStateException("");
		} else if (arg0 == null) {
			throw new IllegalArgumentException("");
		} else {
			@Pc(23) PingRequest local23 = new PingRequest(arg0);
			this.method5477(local23);
			return local23;
		}
	}

	@OriginalMember(owner = "client!vo", name = "run", descriptor = "()V")
	@Override
	public void run() {
		while (true) {
			@Pc(8) Class183 local8 = this.aClass183_49;
			@Pc(38) PingRequest local38;
			synchronized (this.aClass183_49) {
				@Pc(15) Country local15;
				for (local15 = this.aClass183_49.method4136(); local15 == null; local15 = this.aClass183_49.method4136()) {
					try {
						this.aClass183_49.wait();
					} catch (@Pc(22) InterruptedException local22) {
					}
				}
				if (!(local15 instanceof PingRequest)) {
					return;
				}
				local38 = (PingRequest) local15;
			}
			@Pc(64) int local64;
			try {
				@Pc(49) byte[] local49 = InetAddress.getByName(local38.address).getAddress();
				local64 = jagmisc.ping(local49[0], local49[1], local49[2], local49[3], 1000L);
			} catch (@Pc(66) Throwable local66) {
				local64 = 1000;
			}
			local38.ping = local64;
		}
	}

@OriginalMember(owner = "client!vo", name = "g", descriptor = "[J")
	public static long[] aLongArray9;
@OriginalMember(owner = "client!vo", name = "k", descriptor = "[Lclient!l;")
	public static NativeSprite[] aClass57Array18;
@OriginalMember(owner = "client!vo", name = "i", descriptor = "[I")
	public static final int[] anIntArray537 = new int[1000];
@OriginalMember(owner = "client!vo", name = "a", descriptor = "(ILclient!sl;)V")
	public static void method5476(@OriginalArg(1) Class215 arg0) {
		ParticleNode.aClass4_Sub12_Sub1_5.method1133(arg0.method4926());
	}
@OriginalMember(owner = "client!vo", name = "a", descriptor = "(II)V")
	public static void method5478() {
		@Pc(1) LRUCache local1 = Class4_Sub1_Sub6_Sub2.aClass68_24;
		synchronized (Class4_Sub1_Sub6_Sub2.aClass68_24) {
			Class4_Sub1_Sub6_Sub2.aClass68_24.method1788(5);
		}
	}
}
