package com.jagex.game.runetek5.config.quickchat;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.QuickChatValueType;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.core.io.BufferedSocket;

import com.jagex.game.runetek5.config.quickchat.*;
@OriginalClass("client!gh")
public final class QuickchatMessageTypeList {

	@OriginalMember(owner = "client!gh", name = "h", descriptor = "I")
	public int configClientSmallNum = 0;

	@OriginalMember(owner = "client!gh", name = "d", descriptor = "I")
	public int configClientLargeNum = 0;

	@OriginalMember(owner = "client!gh", name = "i", descriptor = "Lclient!fa;")
	private final LRUCache messageTypeCache = new LRUCache(64);

	@OriginalMember(owner = "client!gh", name = "j", descriptor = "Lclient!lo;")
	private QuickchatFillerList quickchatFillerlist = null;

	@OriginalMember(owner = "client!gh", name = "f", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider aClass76_30;

	@OriginalMember(owner = "client!gh", name = "g", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider menuResource;

	@OriginalMember(owner = "client!gh", name = "<init>", descriptor = "(ILclient!fs;Lclient!fs;Lclient!lo;)V")
	public QuickchatMessageTypeList(@OriginalArg(0) int languageId, @OriginalArg(1) Js5ResourceProvider messageResource, @OriginalArg(2) Js5ResourceProvider menuResource, @OriginalArg(3) QuickchatFillerList quickChatFillerDecoder) {
		this.quickchatFillerlist = quickChatFillerDecoder;
		this.aClass76_30 = messageResource;
		this.menuResource = menuResource;
		if (this.aClass76_30 != null) {
			this.configClientLargeNum = this.aClass76_30.method2108(1);
		}
		if (this.menuResource != null) {
			this.configClientSmallNum = this.menuResource.method2108(1);
		}
	}

	@OriginalMember(owner = "client!gh", name = "a", descriptor = "(Lclient!ho;B[IJ)Ljava/lang/String;")
	public String method2255(@OriginalArg(0) QuickChatValueType arg0, @OriginalArg(2) int[] arg1, @OriginalArg(3) long arg2) {
		if (this.quickchatFillerlist != null) {
			@Pc(22) String local22 = this.quickchatFillerlist.method3205(arg2, arg1, arg0);
			if (local22 != null) {
				return local22;
			}
		}
		return Long.toString(arg2);
	}

	@OriginalMember(owner = "client!gh", name = "a", descriptor = "(IB)Lclient!au;")
	public QuickchatMessageType method2258(@OriginalArg(0) int arg0) {
		@Pc(11) QuickchatMessageType local11 = (QuickchatMessageType) this.messageTypeCache.method1787((long) arg0);
		if (local11 != null) {
			return local11;
		}
		@Pc(36) byte[] local36;
		if (arg0 >= 32768) {
			local36 = this.menuResource.method2104(arg0 & 0x7FFF, 1);
		} else {
			local36 = this.aClass76_30.method2104(arg0, 1);
		}
		local11 = new QuickchatMessageType();
		local11.quickchatMessageTypelist = this;
		if (local36 != null) {
			local11.decode(new Packet(local36));
		}
		if (arg0 >= 32768) {
			local11.postDecode();
		}
		this.messageTypeCache.method1779((long) arg0, local11);
		return local11;
	}

@OriginalMember(owner = "client!gh", name = "c", descriptor = "Lclient!al;")
	public static BufferedSocket aClass11_5;
@OriginalMember(owner = "client!gh", name = "a", descriptor = "(ZII)B")
	public static byte method2257(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		if (arg1 == 9) {
			return (byte) ((arg0 & 0x1) == 0 ? 1 : 2);
		} else {
			return 0;
		}
	}
}
