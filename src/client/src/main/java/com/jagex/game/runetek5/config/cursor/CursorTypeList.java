package com.jagex.game.runetek5.config.cursor;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.deob.Class103;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.config.cursor.*;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
@OriginalClass("client!ok")
public final class CursorTypeList {

	@OriginalMember(owner = "client!ok", name = "d", descriptor = "Lclient!fa;")
	private final LRUCache aClass68_30 = new LRUCache(64);

	@OriginalMember(owner = "client!ok", name = "l", descriptor = "Lclient!fa;")
	public final LRUCache aClass68_31 = new LRUCache(2);

	@OriginalMember(owner = "client!ok", name = "i", descriptor = "Lclient!fs;")
	public final Js5ResourceProvider spriteResource;

	@OriginalMember(owner = "client!ok", name = "c", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider cursorResource;

	static {
		new Class83("Changes will take effect on your clan in the next 60 seconds.", "Die Änderungen am Chatraum werden innerhalb von 60 Sekunden gültig.", "Les modifications seront apportées à votre clan dans les prochaines 60 secondes.", "As alterações passarão a valer no seu clã nos próximos 60 segundos.");
	}

	@OriginalMember(owner = "client!ok", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;Lclient!fs;)V")
	public CursorTypeList(@OriginalArg(0) Game modeGame, @OriginalArg(1) int languageId, @OriginalArg(2) Js5ResourceProvider arg2, @OriginalArg(3) Js5ResourceProvider arg3) {
		this.spriteResource = arg3;
		this.cursorResource = arg2;
		this.cursorResource.method2108(33);
	}

	@OriginalMember(owner = "client!ok", name = "a", descriptor = "(II)Lclient!fn;")
	public CursorType method3977(@OriginalArg(0) int arg0) {
		@Pc(6) LRUCache local6 = this.aClass68_30;
		@Pc(16) CursorType local16;
		synchronized (this.aClass68_30) {
			local16 = (CursorType) this.aClass68_30.method1787((long) arg0);
		}
		if (local16 != null) {
			return local16;
		}
		@Pc(41) byte[] local41 = this.cursorResource.method2104(arg0, 33);
		local16 = new CursorType();
		local16.cursorTypeList = this;
		if (local41 != null) {
			local16.method2056(new Packet(local41));
		}
		@Pc(60) LRUCache local60 = this.aClass68_30;
		synchronized (this.aClass68_30) {
			this.aClass68_30.method1779((long) arg0, local16);
			return local16;
		}
	}

	@OriginalMember(owner = "client!ok", name = "a", descriptor = "(Z)V")
	public void method3978() {
		@Pc(2) LRUCache local2 = this.aClass68_30;
		synchronized (this.aClass68_30) {
			this.aClass68_30.method1777();
		}
		local2 = this.aClass68_31;
		synchronized (this.aClass68_31) {
			this.aClass68_31.method1777();
		}
	}

	@OriginalMember(owner = "client!ok", name = "a", descriptor = "(I)V")
	public void method3979() {
		@Pc(2) LRUCache local2 = this.aClass68_30;
		synchronized (this.aClass68_30) {
			this.aClass68_30.method1791();
		}
		local2 = this.aClass68_31;
		synchronized (this.aClass68_31) {
			this.aClass68_31.method1791();
		}
	}

	@OriginalMember(owner = "client!ok", name = "b", descriptor = "(II)V")
	public void method3981() {
		@Pc(2) LRUCache local2 = this.aClass68_30;
		synchronized (this.aClass68_30) {
			this.aClass68_30.method1788(5);
		}
		local2 = this.aClass68_31;
		synchronized (this.aClass68_31) {
			this.aClass68_31.method1788(5);
		}
	}

@OriginalMember(owner = "client!ok", name = "f", descriptor = "[Lclient!ic;")
	public static Class103[] aClass103Array1;
@OriginalMember(owner = "client!ok", name = "n", descriptor = "F")
	public static float aFloat163;
@OriginalMember(owner = "client!ok", name = "h", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_235 = new Class22(56, 4);
@OriginalMember(owner = "client!ok", name = "m", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_236 = new Class22(51, 14);
@OriginalMember(owner = "client!ok", name = "a", descriptor = "(BIIII)V")
	public static void method3980(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		@Pc(6) int local6;
		if (arg0 < arg1) {
			for (local6 = arg0; local6 < arg1; local6++) {
				Class4_Sub6_Sub7.anIntArrayArray21[local6][arg2] = arg3;
			}
		} else {
			for (local6 = arg1; local6 < arg0; local6++) {
				Class4_Sub6_Sub7.anIntArrayArray21[local6][arg2] = arg3;
			}
		}
	}
}
