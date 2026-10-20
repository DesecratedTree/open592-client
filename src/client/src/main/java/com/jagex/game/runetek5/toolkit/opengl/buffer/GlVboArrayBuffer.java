package com.jagex.game.runetek5.toolkit.opengl.buffer;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.buffer.ArrayBufferGL;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Static347;
import com.jagex.game.runetek5.deob.Static411;

import com.jagex.game.runetek5.toolkit.opengl.buffer.*;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
import com.jagex.game.runetek5.deob.Class92;
@OriginalClass("client!mq")
public final class GlVboArrayBuffer extends AbstractArbBufferGL implements ArrayBufferGL {

	@OriginalMember(owner = "client!mq", name = "s", descriptor = "I")
	private int _stride;

	@OriginalMember(owner = "client!mq", name = "<init>", descriptor = "(Lclient!fd;I[BIZ)V")
	public GlVboArrayBuffer(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) int _stride, @OriginalArg(2) byte[] arg2, @OriginalArg(3) int bufferSize, @OriginalArg(4) boolean streamDraw) {
		super(glToolkit, 34963, arg2, bufferSize, streamDraw);
		this._stride = _stride;
	}

	@OriginalMember(owner = "client!mq", name = "a", descriptor = "(B)J")
	@Override
	public long getAddress() {
		return 0L;
	}

	@OriginalMember(owner = "client!mq", name = "a", descriptor = "(I)I")
	@Override
	public int getId() {
		return super.id;
	}

	@OriginalMember(owner = "client!mq", name = "b", descriptor = "(B)I")
	@Override
	public int getStride() {
		return this._stride;
	}

	@OriginalMember(owner = "client!mq", name = "a", descriptor = "([BZII)V")
	@Override
	public void method3657(@OriginalArg(0) byte[] arg0, @OriginalArg(3) int arg1) {
		this.method5177(arg1, arg0);
		this._stride = 5123;
	}

	@OriginalMember(owner = "client!mq", name = "d", descriptor = "(B)V")
	@Override
	protected void setAsActiveBuffer() {
		super.toolkit.method1970(this);
	}

@OriginalMember(owner = "client!mq", name = "q", descriptor = "[[Z")
	public static boolean[][] aBooleanArrayArray4;
@OriginalMember(owner = "client!mq", name = "t", descriptor = "[I")
	public static int[] anIntArray310;
@OriginalMember(owner = "client!mq", name = "w", descriptor = "[S")
	public static short[] aShortArray151;
@OriginalMember(owner = "client!mq", name = "e", descriptor = "(B)V")
	public static void method3660() {
		Class4_Sub12_Sub1.aClass36_1.method2248();
		for (@Pc(10) int local10 = 0; local10 < 32; local10++) {
			Static347.aLongArray8[local10] = 0L;
		}
		for (@Pc(22) int local22 = 0; local22 < 32; local22++) {
			Class92.aLongArray6[local22] = 0L;
		}
		Static411.anInt6741 = 0;
	}
}
