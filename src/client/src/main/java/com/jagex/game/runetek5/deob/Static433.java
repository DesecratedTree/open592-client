package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class48_Sub1_Sub1;
import com.jagex.game.runetek5.config.cursor.CursorType;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.OpenGlArbElementArrayBuffer;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.deob.Class50;
import com.jagex.game.runetek5.network.packet.ServerProt;
import com.jagex.game.runetek5.deob.Class4_Sub19;
import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
public final class Static433 {

	@OriginalMember(owner = "client!vt", name = "f", descriptor = "Lclient!ke;")
	public static Class70 aClass70_1;

	@OriginalMember(owner = "client!vt", name = "a", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_326 = new Class22(107, -1);

	@OriginalMember(owner = "client!vt", name = "e", descriptor = "[Ljava/lang/String;")
	public static final String[] aStringArray38 = new String[200];

	@OriginalMember(owner = "client!vt", name = "g", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_327 = new Class22(103, 6);

	@OriginalMember(owner = "client!vt", name = "a", descriptor = "(Lclient!ya;I)V")
	public static void method5498(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		arg0.OA(0, 0, GpuPointLight.anInt2881, 350);
		arg0.O(0, 0, GpuPointLight.anInt2881, 350, Class48_Sub1_Sub1.anInt4882 << 24 | 0x332277, 1);
		@Pc(33) int local33 = 350 / ServerProt.anInt3464;
		@Pc(45) int local45;
		if (OpenGlArbElementArrayBuffer.anInt1881 > 0) {
			local45 = 346 - ServerProt.anInt3464 - 4;
			@Pc(55) int local55 = local33 * local45 / (local33 + OpenGlArbElementArrayBuffer.anInt1881 - 1);
			@Pc(57) int local57 = 4;
			if (OpenGlArbElementArrayBuffer.anInt1881 > 1) {
				local57 = (OpenGlArbElementArrayBuffer.anInt1881 - Static167.chatScrollOffset - 1) * (-local55 + local45) / (OpenGlArbElementArrayBuffer.anInt1881 - 1) + 4;
			}
			arg0.O(GpuPointLight.anInt2881 - 16, local57, 12, local55, Class48_Sub1_Sub1.anInt4882 << 24 | 0x332277, 2);
			for (@Pc(96) int local96 = Static167.chatScrollOffset; local96 < Static167.chatScrollOffset + local33 && local96 < OpenGlArbElementArrayBuffer.anInt1881; local96++) {
				@Pc(105) String[] local105 = CursorType.method2058(Static102.aStringArray8[local96], '\b');
				@Pc(112) int local112 = (GpuPointLight.anInt2881 - 24) / local105.length;
				for (@Pc(114) int local114 = 0; local114 < local105.length; local114++) {
					@Pc(122) int local122 = local114 * local112 + 8;
					arg0.OA(local122, 0, local122 + local112 - 8, 350);
					Class6.aClass46_9.method5058(-16777216, -1, local105[local114], 350 - (local96 - Static167.chatScrollOffset) * ServerProt.anInt3464 - Static363.anInt6275 - ReflectionCheck.aClass89_1.anInt2778 - 2, local122);
				}
			}
		}
		arg0.OA(0, 0, GpuPointLight.anInt2881, 350);
		arg0.method4256(GpuPointLight.anInt2881, 350 - Static363.anInt6275, 0, -1);
		Static331.aClass46_10.method5058(-16777216, -1, "--> " + Class4_Sub19.aString29, 350 - Js5ResourceProvider.aClass89_5.anInt2778 - 1, 10);
		local45 = -1;
		if (Shadow.anInt5323 % 30 > 15) {
			local45 = 16777215;
		}
		arg0.method4240(350 - Js5ResourceProvider.aClass89_5.anInt2778 - 11, 12, Js5ResourceProvider.aClass89_5.getWidthNoSprites("--> " + Class4_Sub19.aString29.substring(0, CacheableNodeByteArray.anInt4511)) + 10, local45);
	}

	@OriginalMember(owner = "client!vt", name = "b", descriptor = "(I)V")
	public static void method5500() {
		Class4_Sub6_Sub31.aClipboard1 = null;
		Class50.aBoolean292 = false;
		ModeWhere.method4940();
	}
}
