package com.jagex.game.runetek5.js5.request;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.RemoteFileWorker;
import com.jagex.game.runetek5.deob.Class222;
import com.jagex.game.runetek5.js5.Js5WorkerThread;
import com.jagex.game.runetek5.js5.request.local.LocalRequester;

import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static47;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.js5.request.*;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub2;
import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.deob.Class60;
import com.jagex.game.runetek5.deob.Class136;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
@OriginalClass("client!nj")
public final class RequestManager {

	@OriginalMember(owner = "client!nj", name = "g", descriptor = "[Lclient!lv;")
	private RemoteFileWorker[] aClass143_Sub1Array2;

	@OriginalMember(owner = "client!nj", name = "k", descriptor = "Lclient!iv;")
	private Packet aClass4_Sub12_5;

	@OriginalMember(owner = "client!nj", name = "d", descriptor = "Lclient!vl;")
	private final LocalRequester localRequester;

	@OriginalMember(owner = "client!nj", name = "h", descriptor = "Lclient!vn;")
	private final Js5WorkerThread remoteRequester;

	@OriginalMember(owner = "client!nj", name = "i", descriptor = "Lclient!je;")
	private Js5ResourceRequest aClass4_Sub1_Sub6_Sub1_1;

	@OriginalMember(owner = "client!nj", name = "<init>", descriptor = "(Lclient!vn;Lclient!vl;)V")
	public RequestManager(@OriginalArg(0) Js5WorkerThread arg0, @OriginalArg(1) LocalRequester arg1) {
		this.localRequester = arg1;
		this.remoteRequester = arg0;
		if (!this.remoteRequester.isUrgentRequestQueueFull()) {
			this.aClass4_Sub1_Sub6_Sub1_1 = this.remoteRequester.method5465(255, (byte) 0, true, 255);
		}
	}

	@OriginalMember(owner = "client!nj", name = "a", descriptor = "(Z)Z")
	public boolean method3814() {
		if (this.aClass4_Sub12_5 != null) {
			return true;
		}
		if (this.aClass4_Sub1_Sub6_Sub1_1 == null) {
			if (this.remoteRequester.isUrgentRequestQueueFull()) {
				return false;
			}
			this.aClass4_Sub1_Sub6_Sub1_1 = this.remoteRequester.method5465(255, (byte) 0, true, 255);
		}
		if (this.aClass4_Sub1_Sub6_Sub1_1.waiting) {
			return false;
		} else {
			this.aClass4_Sub12_5 = new Packet(this.aClass4_Sub1_Sub6_Sub1_1.getData());
			this.aClass143_Sub1Array2 = new RemoteFileWorker[(this.aClass4_Sub12_5.buffer.length - 5) / 8];
			return true;
		}
	}

	@OriginalMember(owner = "client!nj", name = "a", descriptor = "(Lclient!st;Lclient!st;II)Lclient!lv;")
	public RemoteFileWorker method3817(@OriginalArg(0) Class222 arg0, @OriginalArg(1) Class222 arg1, @OriginalArg(3) int arg2) {
		return this.method3821(arg1, arg2, arg0);
	}

	@OriginalMember(owner = "client!nj", name = "b", descriptor = "(I)V")
	public void method3819() {
		if (this.aClass143_Sub1Array2 == null) {
			return;
		}
		for (@Pc(15) int local15 = 0; local15 < this.aClass143_Sub1Array2.length; local15++) {
			if (this.aClass143_Sub1Array2[local15] != null) {
				this.aClass143_Sub1Array2[local15].method3522();
			}
		}
		for (@Pc(40) int local40 = 0; local40 < this.aClass143_Sub1Array2.length; local40++) {
			if (this.aClass143_Sub1Array2[local40] != null) {
				this.aClass143_Sub1Array2[local40].method3520();
			}
		}
	}

	@OriginalMember(owner = "client!nj", name = "a", descriptor = "(Lclient!st;ZBILclient!st;)Lclient!lv;")
	private RemoteFileWorker method3821(@OriginalArg(0) Class222 arg0, @OriginalArg(3) int arg1, @OriginalArg(4) Class222 arg2) {
		if (this.aClass4_Sub12_5 == null) {
			throw new RuntimeException();
		}
		this.aClass4_Sub12_5.currentPosition = arg1 * 8 + 5;
		if (this.aClass4_Sub12_5.buffer.length <= this.aClass4_Sub12_5.currentPosition) {
			throw new RuntimeException();
		} else if (this.aClass143_Sub1Array2[arg1] == null) {
			@Pc(45) int local45 = this.aClass4_Sub12_5.method2529();
			@Pc(50) int local50 = this.aClass4_Sub12_5.method2529();
			@Pc(64) RemoteFileWorker local64 = new RemoteFileWorker(arg1, arg0, arg2, this.remoteRequester, this.localRequester, local45, local50, true);
			this.aClass143_Sub1Array2[arg1] = local64;
			return local64;
		} else {
			return this.aClass143_Sub1Array2[arg1];
		}
	}

@OriginalMember(owner = "client!nj", name = "b", descriptor = "Z")
	public static boolean aBoolean457;
@OriginalMember(owner = "client!nj", name = "o", descriptor = "[Ljava/lang/String;")
	public static String[] aStringArray20;
@OriginalMember(owner = "client!nj", name = "q", descriptor = "Lclient!tb;")
	public static EnvironmentSampler aClass99_5;
@OriginalMember(owner = "client!nj", name = "m", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_60 = new Class215(47, 3);
@OriginalMember(owner = "client!nj", name = "p", descriptor = "Lclient!bg;")
	public static final Class22 MAP_BUILD_COMPLETE = new Class22(86, 8);
@OriginalMember(owner = "client!nj", name = "a", descriptor = "(B)V")
	public static void method3816() {
		for (@Pc(7) int local7 = 0; local7 < Static1.aClass155ArrayArray1.length; local7++) {
			for (@Pc(11) int local11 = 0; local11 < Static1.aClass155ArrayArray1[local7].length; local11++) {
				Static1.aClass155ArrayArray1[local7][local11] = Static47.aClass155_1;
			}
		}
	}
@OriginalMember(owner = "client!nj", name = "a", descriptor = "(II)I")
	public static int method3818(@OriginalArg(0) int arg0) {
		@Pc(12) int local12 = arg0 & 0x3F;
		@Pc(18) int local18 = arg0 >> 6 & 0x3;
		if (local12 == 18) {
			if (local18 == 0) {
				return 1;
			}
			if (local18 == 1) {
				return 2;
			}
			if (local18 == 2) {
				return 4;
			}
			if (local18 == 3) {
				return 8;
			}
		} else if (local12 == 19 || local12 == 21) {
			if (local18 == 0) {
				return 16;
			}
			if (local18 == 1) {
				return 32;
			}
			if (local18 == 2) {
				return 64;
			}
			if (local18 == 3) {
				return 128;
			}
		}
		return 0;
	}
@OriginalMember(owner = "client!nj", name = "a", descriptor = "(ZIZII)Lclient!fs;")
	public static Js5ResourceProvider method3820(@OriginalArg(0) boolean arg0, @OriginalArg(1) int arg1, @OriginalArg(2) boolean arg2) {
		@Pc(5) Class222 local5 = null;
		if (Class60.aClass139_1 != null) {
			local5 = new Class222(arg1, Class60.aClass139_1, OpenGLTextureCache.aClass139Array1[arg1], 1000000);
		}
		Class16_Sub4_Sub2.aClass143_Sub1Array1[arg1] = Class64_Sub1.aClass159_1.method3817(Class136.aClass222_2, local5, arg1);
		if (arg2) {
			Class16_Sub4_Sub2.aClass143_Sub1Array1[arg1].method3526();
		}
		return new Js5ResourceProvider(Class16_Sub4_Sub2.aClass143_Sub1Array1[arg1], arg0, 1);
	}
}
