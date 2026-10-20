package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.config.quickchat.QuickchatMessageType;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class22;

import com.jagex.game.runetek5.*;
@OriginalClass("client!vq")
public final class QuickChatPhrase {

	@OriginalMember(owner = "client!vq", name = "a", descriptor = "I")
	public int id;

	@OriginalMember(owner = "client!vq", name = "b", descriptor = "Lclient!au;")
	public QuickchatMessageType type;

	@OriginalMember(owner = "client!vq", name = "d", descriptor = "[I")
	public int[] values;

@OriginalMember(owner = "client!vq", name = "f", descriptor = "I")
	public static int anInt7073;
@OriginalMember(owner = "client!vq", name = "h", descriptor = "I")
	public static int anInt7074;
@OriginalMember(owner = "client!vq", name = "g", descriptor = "Lclient!bg;")
	public static final Class22 OPNPC3 = new Class22(50, 3);
@OriginalMember(owner = "client!vq", name = "a", descriptor = "(I[Ljava/lang/String;BI)Ljava/lang/String;")
	public static String method5482(@OriginalArg(0) int arg0, @OriginalArg(1) String[] arg1, @OriginalArg(3) int arg2) {
		if (arg2 == 0) {
			return "";
		} else if (arg2 == 1) {
			@Pc(19) String local19 = arg1[arg0];
			return local19 == null ? "null" : local19.toString();
		} else {
			@Pc(30) int local30 = arg0 + arg2;
			@Pc(32) int local32 = 0;
			for (@Pc(34) int local34 = arg0; local34 < local30; local34++) {
				@Pc(40) String local40 = arg1[local34];
				if (local40 == null) {
					local32 += 4;
				} else {
					local32 += local40.length();
				}
			}
			@Pc(59) StringBuffer local59 = new StringBuffer(local32);
			for (@Pc(61) int local61 = arg0; local61 < local30; local61++) {
				@Pc(67) String local67 = arg1[local61];
				if (local67 == null) {
					local59.append("null");
				} else {
					local59.append(local67);
				}
			}
			return local59.toString();
		}
	}
}
