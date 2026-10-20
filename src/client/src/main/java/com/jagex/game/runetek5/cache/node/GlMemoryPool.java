package com.jagex.game.runetek5.cache.node;

import jaclib.memory.heap.NativeHeap;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.deob.Static231;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.cache.node.*;
import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.Class4_Sub18;
import com.jagex.game.runetek5.deob.Class105;
@OriginalClass("client!ai")
public final class GlMemoryPool extends MemoryPool {

	@OriginalMember(owner = "client!ai", name = "s", descriptor = "Lclient!jaggl/memory/NativeHeap;")
	public final NativeHeap aNativeHeap1;

	@OriginalMember(owner = "client!ai", name = "<init>", descriptor = "(I)V")
	public GlMemoryPool(@OriginalArg(0) int size) {
		this.aNativeHeap1 = new NativeHeap(size);
	}

	@OriginalMember(owner = "client!ai", name = "c", descriptor = "(I)V")
	public void deallocate() {
		this.aNativeHeap1.b();
	}

@OriginalMember(owner = "client!ai", name = "q", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_1;
@OriginalMember(owner = "client!ai", name = "r", descriptor = "Lclient!un;")
	public static Skybox aClass242_1;
@OriginalMember(owner = "client!ai", name = "t", descriptor = "Z")
	public static boolean aBoolean15 = false;
@OriginalMember(owner = "client!ai", name = "a", descriptor = "(IIIIII)Z")
	public static boolean method102(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5) {
		@Pc(16) int local16;
		@Pc(20) int local20;
		if (arg1 != arg2 || arg3 != arg4) {
			for (local16 = arg1; local16 <= arg2; local16++) {
				for (local20 = arg3; local20 <= arg4; local20++) {
					if (Class105.anIntArrayArrayArray13[arg0][local16][local20] == -Class93.anInt2891) {
						return false;
					}
				}
			}
			local20 = (arg1 << Static231.anInt4434) + 1;
			@Pc(142) int local142 = (arg3 << Static231.anInt4434) + 2;
			@Pc(151) int local151 = Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1, arg3) + arg5;
			if (!Class177.method4088(local20, local151, local142)) {
				return false;
			}
			@Pc(164) int local164 = (arg2 << Static231.anInt4434) - 1;
			if (!Class177.method4088(local164, local151, local142)) {
				return false;
			}
			@Pc(177) int local177 = (arg4 << Static231.anInt4434) - 1;
			if (!Class177.method4088(local20, local151, local177)) {
				return false;
			} else if (Class177.method4088(local164, local151, local177)) {
				return true;
			} else {
				return false;
			}
		} else if (Class4_Sub18.method2030(arg0, arg1, arg3)) {
			local16 = arg1 << Static231.anInt4434;
			local20 = arg3 << Static231.anInt4434;
			return Class177.method4088(local16 + 1, Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1, arg3) + arg5, local20 + 1) && Class177.method4088(local16 + CacheableNode.anInt5935 - 1, Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1 + 1, arg3) + arg5, local20 + 1) && Class177.method4088(local16 + CacheableNode.anInt5935 - 1, Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1 + 1, arg3 + 1) + arg5, local20 + CacheableNode.anInt5935 - 1) && Class177.method4088(local16 + 1, Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1, arg3 + 1) + arg5, local20 + CacheableNode.anInt5935 - 1);
		} else {
			return false;
		}
	}
}
