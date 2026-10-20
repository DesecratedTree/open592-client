package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.HelveticaFont;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.config.param.ParamTypeList;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
public final class Static410 {

	@OriginalMember(owner = "client!uq", name = "b", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_95;

	@OriginalMember(owner = "client!uq", name = "c", descriptor = "I")
	public static int anInt6733;

	@OriginalMember(owner = "client!uq", name = "i", descriptor = "[Lclient!l;")
	public static NativeSprite[] aClass57Array17;

	@OriginalMember(owner = "client!uq", name = "d", descriptor = "I")
	public static int anInt6734 = -1;

	@OriginalMember(owner = "client!uq", name = "e", descriptor = "I")
	public static int anInt6735 = 0;

	@OriginalMember(owner = "client!uq", name = "f", descriptor = "Lclient!bf;")
	public static final Class21 aClass21_15 = new Class21(1, 7);

	@OriginalMember(owner = "client!uq", name = "a", descriptor = "(IBLclient!aq;IILclient!sb;Lclient!nf;Lclient!ya;)V")
	public static void method5269(@OriginalArg(0) int arg0, @OriginalArg(2) Class4_Sub3 arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) HelveticaFont arg4, @OriginalArg(6) MECType arg5, @OriginalArg(7) com.jagex.game.runetek5.graphics.Toolkit arg6) {
		@Pc(15) int local15 = arg1.anInt235 - arg2 / 2 - 5;
		@Pc(19) int local19 = arg3 + 2;
		if (arg5.anInt4918 != 0) {
			arg6.method4293(local15, arg2 + 10, local19, arg5.anInt4918, arg4.getLineHeight() * arg0 + arg3 + 1 - local19);
		}
		if (arg5.anInt4906 != 0) {
			arg6.method4295(local15, arg5.anInt4906, arg0 * arg4.getLineHeight() + arg3 + 1 - local19, arg2 + 10, local19);
		}
		@Pc(68) int local68 = arg5.anInt4926;
		if (arg1.aBoolean27 && arg5.anInt4907 != -1) {
			local68 = arg5.anInt4907;
		}
		for (@Pc(88) int local88 = 0; local88 < arg0; local88++) {
			@Pc(94) String local94 = ParamTypeList.aStringArray35[local88];
			if (local88 < arg0 - 1) {
				local94 = local94.substring(0, local94.length() - 4);
			}
			arg4.renderCenter(arg6, local94, arg1.anInt235, arg3, local68);
			arg3 += arg4.getLineHeight();
		}
	}

	@OriginalMember(owner = "client!uq", name = "a", descriptor = "([I[JI)V")
	public static void method5270(@OriginalArg(0) int[] arg0, @OriginalArg(1) long[] arg1) {
		Class230.method5098(0, arg1.length - 1, arg0, arg1);
	}

	@OriginalMember(owner = "client!uq", name = "a", descriptor = "(II)V")
	public static void method5271(@OriginalArg(1) int arg0) {
		@Pc(8) Class4_Sub1_Sub11 local8 = NPCTypeList.method5222(arg0, 6);
		local8.method2608();
	}
}
