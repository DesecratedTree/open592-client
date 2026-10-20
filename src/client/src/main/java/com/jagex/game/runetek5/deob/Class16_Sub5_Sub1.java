package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.graphics.model.Model;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class27_Sub2;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
@OriginalClass("client!jo")
public final class Class16_Sub5_Sub1 extends Class16_Sub5 implements GameObject {

	@OriginalMember(owner = "client!jo", name = "y", descriptor = "Lclient!rb;")
	public Class16_Sub5 aClass16_Sub5_1;

	static {
		new Class83("Ok", "Okay", "OK", "Ok");
	}

	@OriginalMember(owner = "client!jo", name = "<init>", descriptor = "(IIIIILclient!rb;)V")
	public Class16_Sub5_Sub1(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) Class16_Sub5 arg5) {
		super(arg2, arg3, arg4, Class27_Sub2.method2355(arg1, arg0));
		this.aClass16_Sub5_1 = arg5;
	}

	@OriginalMember(owner = "client!jo", name = "c", descriptor = "(Lclient!ya;I)Lclient!fk;")
	@Override
	public Class3_Sub3 method5339(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		return null;
	}

	@OriginalMember(owner = "client!jo", name = "a", descriptor = "(IILclient!ya;I)Z")
	@Override
	public boolean method5341(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) int arg2) {
		return false;
	}

	@OriginalMember(owner = "client!jo", name = "d", descriptor = "(I)V")
	@Override
	public void removeTileShadow() {
	}

	@OriginalMember(owner = "client!jo", name = "a", descriptor = "(Lclient!ya;II)Lclient!t;")
	@Override
	public Model method5352(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) int arg1) {
		return null;
	}

	@OriginalMember(owner = "client!jo", name = "a", descriptor = "(B)I")
	@Override
	public int method5347() {
		return 0;
	}

	@OriginalMember(owner = "client!jo", name = "a", descriptor = "(Z)I")
	@Override
	public int method5350() {
		return 0;
	}

	@OriginalMember(owner = "client!jo", name = "b", descriptor = "(Lclient!ya;I)V")
	@Override
	public void method5335(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
	}

	@OriginalMember(owner = "client!jo", name = "a", descriptor = "(IILclient!fi;BZLclient!ya;I)V")
	@Override
	public void method5337(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Class16 arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg4, @OriginalArg(6) int arg5) {
	}

	@OriginalMember(owner = "client!jo", name = "b", descriptor = "(I)V")
	@Override
	public void upload() {
	}

	@OriginalMember(owner = "client!jo", name = "c", descriptor = "(I)I")
	@Override
	public int method5354() {
		return 0;
	}

	@OriginalMember(owner = "client!jo", name = "a", descriptor = "(Lclient!ya;I)V")
	@Override
	public void removeShadow(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
	}

	@OriginalMember(owner = "client!jo", name = "a", descriptor = "(I)Z")
	@Override
	public boolean hardShadow() {
		return false;
	}

	@OriginalMember(owner = "client!jo", name = "a", descriptor = "(ZLclient!ya;)V")
	@Override
	public void method5349(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
	}

	@OriginalMember(owner = "client!jo", name = "d", descriptor = "(B)Z")
	@Override
	public boolean hasTileShadow() {
		return false;
	}

@OriginalMember(owner = "client!jo", name = "q", descriptor = "[I")
	public static int[] anIntArray239 = new int[1];
@OriginalMember(owner = "client!jo", name = "t", descriptor = "[[Z")
	public static final boolean[][] aBooleanArrayArray3 = new boolean[][] { { true, true, true, true, true, true, true, true, true, true, true, true, true }, { true, true, true, false, false, false, true, true, false, false, false, false, true }, { true, false, false, false, false, true, true, true, false, false, false, false, false }, { false, false, true, true, true, true, false, false, false, false, false, false, false }, { true, true, true, true, true, true, false, false, false, false, false, false, false }, { true, true, true, false, false, true, true, true, false, false, false, false, false }, { true, true, false, false, false, true, true, true, false, false, false, false, true }, { true, true, false, false, false, false, false, true, false, false, false, false, false }, { false, true, true, true, true, true, true, true, false, false, false, false, false }, { true, false, false, false, true, true, true, true, true, true, false, false, false }, { true, true, true, true, true, false, false, false, true, true, false, false, false }, { true, true, true, false, false, false, false, false, false, false, true, true, false }, new boolean[13], { true, true, true, true, true, true, true, true, true, true, true, true, true }, new boolean[13] };
@OriginalMember(owner = "client!jo", name = "u", descriptor = "[I")
	public static final int[] anIntArray240 = new int[13];
@OriginalMember(owner = "client!jo", name = "w", descriptor = "[[I")
	public static final int[][] anIntArrayArray35 = new int[][] { { 12, 12, 12, 12 }, { 12, 12, 12, 12, 12, 5 }, { 5, 5, 1, 1 }, { 5, 1, 1, 5 }, { 5, 5, 5 }, { 5, 5, 5 }, { 12, 12, 12, 12, 12, 12 }, { 1, 12, 12, 12, 12, 12 }, { 1, 1, 7, 1 }, { 8, 9, 9, 8, 8, 3, 1, 9 }, { 8, 8, 9, 8, 9, 9 }, { 10, 10, 11, 11, 11, 7, 3, 7 }, { 12, 12, 12, 12 } };
@OriginalMember(owner = "client!jo", name = "a", descriptor = "(II)I")
	public static int method3012(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		return AbstractArbBufferGL.aByteArrayArray29 == null ? 0 : AbstractArbBufferGL.aByteArrayArray29[arg0][arg1] & 0xFF;
	}
}
