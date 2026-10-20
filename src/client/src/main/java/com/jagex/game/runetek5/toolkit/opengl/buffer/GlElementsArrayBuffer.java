package com.jagex.game.runetek5.toolkit.opengl.buffer;

import jaclib.memory.NativeBuffer;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.buffer.ElementsBufferGL;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.deob.QuickChatValueType;

import com.jagex.game.runetek5.toolkit.opengl.buffer.*;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
@OriginalClass("client!om")
public final class GlElementsArrayBuffer extends GlBuffer implements ElementsBufferGL {

	@OriginalMember(owner = "client!om", name = "k", descriptor = "I")
	private int indexType;

	@OriginalMember(owner = "client!om", name = "<init>", descriptor = "(Lclient!fd;ILclient!jaggl/memory/NativeBuffer;)V")
	public GlElementsArrayBuffer(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) NativeBuffer arg2) {
		super(arg0, arg2);
		this.indexType = arg1;
	}

	@OriginalMember(owner = "client!om", name = "<init>", descriptor = "(Lclient!fd;I[BI)V")
	public GlElementsArrayBuffer(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) byte[] arg2, @OriginalArg(3) int arg3) {
		super(arg0, arg2, arg3);
		this.indexType = arg1;
	}

	@OriginalMember(owner = "client!om", name = "b", descriptor = "(I)J")
	@Override
	public long getAddress() {
		return super.aNativeBuffer4.c();
	}

	@OriginalMember(owner = "client!om", name = "a", descriptor = "(Z)I")
	@Override
	public int method5182() {
		return this.indexType;
	}

	@OriginalMember(owner = "client!om", name = "a", descriptor = "(I)I")
	@Override
	public int method5181() {
		return 0;
	}

	@OriginalMember(owner = "client!om", name = "a", descriptor = "(BII[B)V")
	@Override
	public void method5184(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) byte[] arg2) {
		this.put(arg2, arg0);
		this.indexType = arg1;
	}

@OriginalMember(owner = "client!om", name = "i", descriptor = "I")
	public static int anInt5213;
@OriginalMember(owner = "client!om", name = "g", descriptor = "Lclient!ho;")
	public static final QuickChatValueType aClass98_13 = new QuickChatValueType(0, 2, 2, 1);
@OriginalMember(owner = "client!om", name = "a", descriptor = "(IZ)V")
	public static void method3994(@OriginalArg(0) int arg0) {
		@Pc(8) Class4_Sub1_Sub11 local8 = NPCTypeList.method5222(arg0, 11);
		local8.method2608();
	}
@OriginalMember(owner = "client!om", name = "a", descriptor = "(IB)V")
	public static void method3995(@OriginalArg(0) int arg0) {
		@Pc(14) Class4_Sub1_Sub11 local14 = NPCTypeList.method5222(arg0, 16);
		local14.method2608();
	}
}
