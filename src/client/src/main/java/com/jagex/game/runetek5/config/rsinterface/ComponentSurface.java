package com.jagex.game.runetek5.config.rsinterface;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Static459;
import com.jagex.game.runetek5.config.rsinterface.*;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.deob.Class149;
import com.jagex.game.runetek5.deob.Class70_Sub1;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!ma")
public abstract class ComponentSurface {

	@OriginalMember(owner = "client!ma", name = "<init>", descriptor = "()V")
	protected ComponentSurface() {
	}

@OriginalMember(owner = "client!ma", name = "a", descriptor = "Lclient!pk;")
	public static final Class183 aClass183_52 = new Class183();
@OriginalMember(owner = "client!ma", name = "a", descriptor = "(IIIIIIII)V")
	public static void method5623(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(7) int arg6) {
		if (arg5 == arg6) {
			Class70_Sub1.method1815(arg2, arg5, arg3, arg4, arg0, arg1);
		} else if (ItemType.anInt6186 <= arg2 - arg5 && arg5 + arg2 <= Class36.anInt2656 && arg4 - arg6 >= Model.anInt6287 && GlCubeTexture.anInt3256 >= arg6 + arg4) {
			Class149.method3558(arg4, arg6, arg5, arg0, arg1, arg2, arg3);
		} else {
			ParticleList.method3548(arg2, arg0, arg5, arg3, arg6, arg4, arg1);
		}
	}
@OriginalMember(owner = "client!ma", name = "a", descriptor = "(ILjava/lang/String;)J")
	public static long method5625(@OriginalArg(1) String arg0) {
		@Pc(8) int local8 = arg0.length();
		@Pc(11) long local11 = (long) 0;
		for (@Pc(13) int local13 = 0; local13 < local8; local13++) {
			local11 = (long) arg0.charAt(local13) + (local11 << 5) - local11;
		}
		return local11;
	}
@OriginalMember(owner = "client!ma", name = "a", descriptor = "(B[BI)[B")
	public static byte[] method5626(@OriginalArg(1) byte[] arg0, @OriginalArg(2) int arg1) {
		@Pc(11) byte[] local11 = new byte[arg1];
		Static459.copy(arg0, 0, local11, 0, arg1);
		return local11;
	}
}
