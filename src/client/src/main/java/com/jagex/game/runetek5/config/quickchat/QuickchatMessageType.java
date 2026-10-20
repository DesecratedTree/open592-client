package com.jagex.game.runetek5.config.quickchat;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.deob.QuickChatValueType;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;

import com.jagex.game.runetek5.config.quickchat.*;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.config.cursor.CursorType;
@OriginalClass("client!au")
public final class QuickchatMessageType extends CacheableNode {

	@OriginalMember(owner = "client!au", name = "B", descriptor = "[I")
	public static final int[] anIntArray17 = new int[32];

	@OriginalMember(owner = "client!au", name = "y", descriptor = "[I")
	public int[] responses;

	@OriginalMember(owner = "client!au", name = "I", descriptor = "[Ljava/lang/String;")
	private String[] message;

	@OriginalMember(owner = "client!au", name = "L", descriptor = "Lclient!gh;")
	public QuickchatMessageTypeList quickchatMessageTypelist;

	@OriginalMember(owner = "client!au", name = "O", descriptor = "[I")
	private int[] valueTypes;

	@OriginalMember(owner = "client!au", name = "R", descriptor = "[[I")
	private int[][] configs;

	@OriginalMember(owner = "client!au", name = "U", descriptor = "Z")
	public boolean searchable = true;

	static {
		@Pc(9) int local9 = 2;
		for (@Pc(11) int local11 = 0; local11 < 32; local11++) {
			anIntArray17[local11] = local9 - 1;
			local9 += local9;
		}
	}

	@OriginalMember(owner = "client!au", name = "b", descriptor = "(II)Lclient!ho;")
	public QuickChatValueType method226(@OriginalArg(0) int arg0) {
		return this.valueTypes == null || arg0 < 0 || this.valueTypes.length < arg0 ? null : Class10.method128(this.valueTypes[arg0]);
	}

	@OriginalMember(owner = "client!au", name = "a", descriptor = "(IZI)I")
	public int getDynamicCommandParam(@OriginalArg(0) int type, @OriginalArg(2) int arg1) {
		if (this.valueTypes == null || arg1 < 0 || this.valueTypes.length < arg1) {
			return -1;
		} else if (this.configs[arg1] == null || type < 0 || type > this.configs[arg1].length) {
			return -1;
		} else {
			return this.configs[arg1][type];
		}
	}

	@OriginalMember(owner = "client!au", name = "a", descriptor = "(I)V")
	public void postDecode() {
		if (this.responses != null) {
			for (@Pc(6) int local6 = 0; local6 < this.responses.length; local6++) {
				this.responses[local6] |= 0x8000;
			}
		}
	}

	@OriginalMember(owner = "client!au", name = "d", descriptor = "(I)Ljava/lang/String;")
	public String getText() {
		@Pc(10) StringBuffer local10 = new StringBuffer(80);
		if (this.message == null) {
			return "";
		}
		local10.append(this.message[0]);
		for (@Pc(24) int local24 = 1; local24 < this.message.length; local24++) {
			local10.append("...");
			local10.append(this.message[local24]);
		}
		return local10.toString();
	}

	@OriginalMember(owner = "client!au", name = "a", descriptor = "(B[ILclient!iv;)V")
	public void encodeValues(@OriginalArg(1) int[] arg0, @OriginalArg(2) Packet values) {
		if (this.valueTypes == null) {
			return;
		}
		for (@Pc(16) int local16 = 0; local16 < this.valueTypes.length; local16++) {
			if (local16 >= arg0.length) {
				return;
			}
			@Pc(24) int local24 = this.method226(local16).anInt3056;
			if (local24 > 0) {
				values.method2526(local24, (long) arg0[local16]);
			}
		}
	}

	@OriginalMember(owner = "client!au", name = "a", descriptor = "(Lclient!iv;I)V")
	public void decode(@OriginalArg(0) Packet packet) {
		while (true) {
			@Pc(9) int local9 = packet.method2490();
			if (local9 == 0) {
				return;
			}
			this.method235(packet, local9);
		}
	}

	@OriginalMember(owner = "client!au", name = "f", descriptor = "(I)I")
	public int getDynamicCommandCount() {
		return this.valueTypes == null ? 0 : this.valueTypes.length;
	}

	@OriginalMember(owner = "client!au", name = "b", descriptor = "(Lclient!iv;I)Ljava/lang/String;")
	public String method234(@OriginalArg(0) Packet arg0) {
		@Pc(17) StringBuffer local17 = new StringBuffer(80);
		if (this.valueTypes != null) {
			for (@Pc(22) int local22 = 0; local22 < this.valueTypes.length; local22++) {
				local17.append(this.message[local22]);
				local17.append(this.quickchatMessageTypelist.method2255(this.method226(local22), this.configs[local22], arg0.method2492(Class10.method128(this.valueTypes[local22]).anInt3058)));
			}
		}
		local17.append(this.message[this.message.length - 1]);
		return local17.toString();
	}

	@OriginalMember(owner = "client!au", name = "a", descriptor = "(Lclient!iv;IZ)V")
	private void method235(@OriginalArg(0) Packet arg0, @OriginalArg(1) int arg1) {
		if (arg1 == 1) {
			this.message = CursorType.method2058(arg0.method2537(), '<');
			return;
		}
		@Pc(22) int local22;
		@Pc(28) int local28;
		if (arg1 == 2) {
			local22 = arg0.method2490();
			this.responses = new int[local22];
			for (local28 = 0; local28 < local22; local28++) {
				this.responses[local28] = arg0.method2536();
			}
			return;
		}
		if (arg1 == 3) {
			local22 = arg0.method2490();
			this.configs = new int[local22][];
			this.valueTypes = new int[local22];
			for (local28 = 0; local28 < local22; local28++) {
				@Pc(67) int local67 = arg0.method2536();
				@Pc(71) QuickChatValueType local71 = Class10.method128(local67);
				if (local71 != null) {
					this.valueTypes[local28] = local67;
					this.configs[local28] = new int[local71.anInt3057];
					for (@Pc(87) int local87 = 0; local87 < local71.anInt3057; local87++) {
						this.configs[local28][local87] = arg0.method2536();
					}
				}
			}
			return;
		}
		if (arg1 == 4) {
			this.searchable = false;
			return;
		}
	}

@OriginalMember(owner = "client!au", name = "H", descriptor = "Lclient!l;")
	public static NativeSprite aClass57_4;
@OriginalMember(owner = "client!au", name = "K", descriptor = "I")
	public static int anInt290;
@OriginalMember(owner = "client!au", name = "P", descriptor = "[I")
	public static int[] DYNAMIC_CORNER_WALL_SIDE_MASKS = new int[2];
@OriginalMember(owner = "client!au", name = "T", descriptor = "I")
	public static final int anInt295 = 1337;
@OriginalMember(owner = "client!au", name = "V", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_12 = new Class83("Examine", "Untersuchen", "Examiner", "Examinar");
@OriginalMember(owner = "client!au", name = "a", descriptor = "(III)I")
	public static int method236(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		arg0 = (arg1 & 0x7F) * arg0 >> 7;
		if (arg0 < 2) {
			arg0 = 2;
		} else if (arg0 > 126) {
			arg0 = 126;
		}
		return (arg1 & 0xFF80) + arg0;
	}
}
