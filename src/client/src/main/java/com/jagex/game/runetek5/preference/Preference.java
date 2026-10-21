package com.jagex.game.runetek5.preference;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class4_Sub5;

import org.openrs2.deob.annotation.OriginalArg;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.Class83;

import com.jagex.game.runetek5.preference.*;
import com.jagex.game.runetek5.cache.node.lru.ClientScript;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
@OriginalClass("client!jn")
public abstract class Preference {

	@OriginalMember(owner = "client!jn", name = "e", descriptor = "[J")
	public static final long[] aLongArray4 = new long[256];

	static {
		for (@Pc(250) int local250 = 0; local250 < 256; local250++) {
			@Pc(254) long local254 = (long) local250;
			for (@Pc(256) int local256 = 0; local256 < 8; local256++) {
				if ((local254 & 0x1L) == 1L) {
					local254 = local254 >>> 1 ^ 0xC96C5795D7870F42L;
				} else {
					local254 >>>= 0x1;
				}
			}
			aLongArray4[local250] = local254;
		}
	}

	@OriginalMember(owner = "client!jn", name = "<init>", descriptor = "()V")
	protected Preference() {
	}

	@OriginalMember(owner = "client!jn", name = "a", descriptor = "(Z)V")
	public abstract void validate();

	@OriginalMember(owner = "client!jn", name = "a", descriptor = "(I)Z")
	public final boolean method2228() {
		return this.method2235() || this.method2237() || this.method2229();
	}

	@OriginalMember(owner = "client!jn", name = "b", descriptor = "(I)Z")
	protected abstract boolean method2229();

	@OriginalMember(owner = "client!jn", name = "c", descriptor = "(I)I")
	public abstract int getDefaultValue();

	/**
	 * Latest pointer x recorded by the input thread.
	 *
	 * <p>The stock path latches the pointer once per tick
	 * ({@link #method2236()}); per-frame consumers such as the camera need
	 * the freshest sample instead. Implementations without a separate input
	 * thread fall back to the latched value.
	 */
	public int getLatestX() {
		return this.method2234();
	}

	/** Latest pointer y recorded by the input thread; see {@link #getLatestX()}. */
	public int getLatestY() {
		return this.getDefaultValue();
	}

	@OriginalMember(owner = "client!jn", name = "a", descriptor = "(B)Lclient!os;")
	public abstract Class4_Sub5 method2232();

	@OriginalMember(owner = "client!jn", name = "b", descriptor = "(B)I")
	public abstract int method2234();

	@OriginalMember(owner = "client!jn", name = "e", descriptor = "(I)Z")
	public abstract boolean method2235();

	@OriginalMember(owner = "client!jn", name = "f", descriptor = "(I)V")
	public abstract void method2236();

	@OriginalMember(owner = "client!jn", name = "c", descriptor = "(B)Z")
	public abstract boolean method2237();

@OriginalMember(owner = "client!jn", name = "f", descriptor = "[S")
	public static short[] aShortArray66;
@OriginalMember(owner = "client!jn", name = "c", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_48 = new Class83("glow1:", "leuchten1:", "brillant1:", "brilho1:");
@OriginalMember(owner = "client!jn", name = "d", descriptor = "[I")
	public static final int[] anIntArray180 = new int[4];
@OriginalMember(owner = "client!jn", name = "g", descriptor = "[I")
	public static final int[] anIntArray181 = new int[64];
@OriginalMember(owner = "client!jn", name = "h", descriptor = "[[I")
	public static final int[][] anIntArrayArray25 = new int[][] { { 0, 1, 2, 3 }, { 1, 2, 3, 0 }, { 1, 2, -1, 0 }, { 2, 0, -1, 1 }, { 0, 1, -1, 2 }, { 1, 2, -1, 0 }, { -1, 4, -1, 1 }, { -1, 1, 3, -1 }, { -1, 0, 2, -1 }, { 3, 5, 2, 0 }, { 0, 2, 5, 3 }, { 0, 2, 3, 5 }, { 0, 1, 2, 3 } };
@OriginalMember(owner = "client!jn", name = "a", descriptor = "(II)V")
	public static void method2233(@OriginalArg(1) int arg0) {
		ClientScript.anInt62 = arg0;
		@Pc(12) LRUCache local12 = Class4_Sub6_Sub15.aClass68_20;
		synchronized (Class4_Sub6_Sub15.aClass68_20) {
			Class4_Sub6_Sub15.aClass68_20.method1777();
		}
		local12 = NPCType.aClass68_58;
		synchronized (NPCType.aClass68_58) {
			NPCType.aClass68_58.method1777();
		}
	}
}
