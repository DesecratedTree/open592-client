package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.ByteArrayWrapper;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.deob.RuntimeException_Sub1;
public final class Static366 {

	@OriginalMember(owner = "client!sm", name = "d", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_83;

	// Index 30 (natives) provider: new holder for the 31-index table.
	// No @OriginalMember: vanilla 592 shipped 30 indexes; the server adds 30.
	public static Js5ResourceProvider aClass76_100;

	@OriginalMember(owner = "client!sm", name = "c", descriptor = "Lclient!gd;")
	public static final MonoStereoData aClass4_Sub20_1 = new MonoStereoData(0, 0);

	@OriginalMember(owner = "client!sm", name = "a", descriptor = "(ZBLjava/lang/Object;)[B")
	public static byte[] method4930(@OriginalArg(0) boolean arg0, @OriginalArg(2) Object arg1) {
		if (arg1 == null) {
			return null;
		} else if (arg1 instanceof byte[]) {
			@Pc(13) byte[] local13 = (byte[]) arg1;
			return arg0 ? RuntimeException_Sub1.method3624(local13) : local13;
		} else if (arg1 instanceof ByteArrayWrapper) {
			@Pc(27) ByteArrayWrapper local27 = (ByteArrayWrapper) arg1;
			return local27.get();
		} else {
			throw new IllegalArgumentException();
		}
	}
}
