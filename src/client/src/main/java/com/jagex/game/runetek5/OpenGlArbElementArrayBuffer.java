package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.*;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
@OriginalClass("client!en")
public final class OpenGlArbElementArrayBuffer extends Class16_Sub2 implements GameObject {

	@OriginalMember(owner = "client!en", name = "I", descriptor = "Lclient!ii;")
	public Class16_Sub2 aClass16_Sub2_1;

	@OriginalMember(owner = "client!en", name = "<init>", descriptor = "(IIIIILclient!ii;)V")
	public OpenGlArbElementArrayBuffer(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) Class16_Sub2 arg5) {
		super(arg2, arg3, arg4, 0, 0, 0, MusicPatch.method4394(arg1, arg0));
		this.aClass16_Sub2_1 = arg5;
	}

	@OriginalMember(owner = "client!en", name = "c", descriptor = "(I)I")
	@Override
	public int method5354() {
		return 0;
	}

	@OriginalMember(owner = "client!en", name = "a", descriptor = "(ZLclient!ya;)V")
	@Override
	public void method5349(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
	}

	@OriginalMember(owner = "client!en", name = "a", descriptor = "(Lclient!ya;I)V")
	@Override
	public void removeShadow(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
	}

	@OriginalMember(owner = "client!en", name = "a", descriptor = "(Z)I")
	@Override
	public int method5350() {
		return 0;
	}

	@OriginalMember(owner = "client!en", name = "a", descriptor = "(B)I")
	@Override
	public int method5347() {
		return 0;
	}

	@OriginalMember(owner = "client!en", name = "b", descriptor = "(Z)I")
	@Override
	public int method3583() {
		return 0;
	}

	@OriginalMember(owner = "client!en", name = "b", descriptor = "(Lclient!ya;I)V")
	@Override
	public void method5335(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
	}

	@OriginalMember(owner = "client!en", name = "b", descriptor = "(I)V")
	@Override
	public void upload() {
	}

	@OriginalMember(owner = "client!en", name = "a", descriptor = "(Lclient!ya;II)Lclient!t;")
	@Override
	public Model method5352(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) int arg1) {
		return null;
	}

	@OriginalMember(owner = "client!en", name = "a", descriptor = "(I)Z")
	@Override
	public boolean hardShadow() {
		return false;
	}

	@OriginalMember(owner = "client!en", name = "c", descriptor = "(Lclient!ya;I)Lclient!fk;")
	@Override
	public Class3_Sub3 method5339(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		return null;
	}

	@OriginalMember(owner = "client!en", name = "a", descriptor = "(IILclient!ya;I)Z")
	@Override
	public boolean method5341(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) int arg2) {
		return false;
	}

@OriginalMember(owner = "client!en", name = "J", descriptor = "I")
	public static int anInt1873;
@OriginalMember(owner = "client!en", name = "O", descriptor = "I")
	public static int anInt1878;
@OriginalMember(owner = "client!en", name = "Y", descriptor = "I")
	public static int anInt1887;
@OriginalMember(owner = "client!en", name = "R", descriptor = "I")
	public static int anInt1881 = 0;
@OriginalMember(owner = "client!en", name = "W", descriptor = "[I")
	public static final int[] anIntArray152 = new int[] { 0, 4, 3, 3, 1, 1, 3, 5, 1, 5, 3, 6, 4 };
@OriginalMember(owner = "client!en", name = "a", descriptor = "(IIB)Z")
	public static boolean method1643(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		return (arg1 & 0x84080) != 0;
	}
}
