package com.jagex.game.runetek5.config.cursor;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.TileLighting;

import com.jagex.game.runetek5.config.cursor.*;
import com.jagex.game.runetek5.deob.Class154;
@OriginalClass("client!fn")
public final class CursorType {

	@OriginalMember(owner = "client!fn", name = "b", descriptor = "I")
	public int anInt2419;

	@OriginalMember(owner = "client!fn", name = "c", descriptor = "I")
	public int anInt2420;

	@OriginalMember(owner = "client!fn", name = "d", descriptor = "Lclient!ok;")
	public CursorTypeList cursorTypeList;

	@OriginalMember(owner = "client!fn", name = "j", descriptor = "I")
	private int spriteId;

	@OriginalMember(owner = "client!fn", name = "a", descriptor = "(Lclient!iv;I)V")
	public void method2056(@OriginalArg(0) Packet arg0) {
		while (true) {
			@Pc(5) int local5 = arg0.method2490();
			if (local5 == 0) {
				return;
			}
			this.method2060(local5, arg0);
		}
	}

	@OriginalMember(owner = "client!fn", name = "a", descriptor = "(IILclient!iv;)V")
	private void method2060(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 1) {
			this.spriteId = arg1.method2536();
		} else if (arg0 == 2) {
			this.anInt2420 = arg1.method2490();
			this.anInt2419 = arg1.method2490();
		}
	}

	@OriginalMember(owner = "client!fn", name = "b", descriptor = "(I)Lclient!bb;")
	public synchronized SpriteType method2061() {
		@Pc(13) SpriteType local13 = (SpriteType) this.cursorTypeList.aClass68_31.method1787((long) this.spriteId);
		if (local13 != null) {
			return local13;
		}
		local13 = SpriteType.listSingle(this.cursorTypeList.spriteResource, this.spriteId, 0);
		if (local13 != null) {
			this.cursorTypeList.aClass68_31.method1779((long) this.spriteId, local13);
		}
		return local13;
	}

@OriginalMember(owner = "client!fn", name = "i", descriptor = "Ljava/lang/Object;")
	public static Object anObject13;
@OriginalMember(owner = "client!fn", name = "m", descriptor = "I")
	public static int anInt2426;
@OriginalMember(owner = "client!fn", name = "g", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_101 = new Class22(104, 6);
@OriginalMember(owner = "client!fn", name = "h", descriptor = "S")
	public static short aShort19 = 1;
@OriginalMember(owner = "client!fn", name = "a", descriptor = "(Lclient!mu;ZII)V")
	public static void method2057(@OriginalArg(0) TileLighting arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		Static1.aClass155ArrayArray1[arg1][arg2] = arg0;
	}
@OriginalMember(owner = "client!fn", name = "a", descriptor = "(BLjava/lang/String;C)[Ljava/lang/String;")
	public static String[] method2058(@OriginalArg(1) String arg0, @OriginalArg(2) char arg1) {
		@Pc(10) int local10 = Class154.method3690(arg0, arg1);
		@Pc(23) String[] local23 = new String[local10 + 1];
		@Pc(25) int local25 = 0;
		@Pc(27) int local27 = 0;
		for (@Pc(29) int local29 = 0; local29 < local10; local29++) {
			@Pc(33) int local33;
			for (local33 = local27; arg0.charAt(local33) != arg1; local33++) {
			}
			local23[local25++] = arg0.substring(local27, local33);
			local27 = local33 + 1;
		}
		local23[local10] = arg0.substring(local27);
		return local23;
	}
}
