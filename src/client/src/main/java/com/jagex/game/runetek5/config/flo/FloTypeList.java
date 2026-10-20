package com.jagex.game.runetek5.config.flo;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.FloType;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.config.flo.*;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.toolkit.opengl.GlRenderList;
import com.jagex.game.runetek5.config.material.texture.TextureOpFlip;
@OriginalClass("client!kb")
public final class FloTypeList {

	@OriginalMember(owner = "client!kb", name = "a", descriptor = "Lclient!fa;")
	private final LRUCache overlayCache = new LRUCache(64);

	@OriginalMember(owner = "client!kb", name = "j", descriptor = "I")
	public int lastLoadedOverlayId = 0;

	@OriginalMember(owner = "client!kb", name = "f", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider floResource;

	@OriginalMember(owner = "client!kb", name = "d", descriptor = "I")
	public final int totalFileCount;

	static {
		new Class83("Unable to join clan chat at this time - please try again later!", "Chatraum kann nicht betreten werden - bitte versuch es später erneut.", "Impossible de participer à une discussion de clan pour le moment - veuillez réessayer ultérieurement.", "Não foi possível entrar no bate-papo do clã dessa vez. Tente de novo depois!");
	}

	@OriginalMember(owner = "client!kb", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;)V")
	public FloTypeList(@OriginalArg(0) Game modeGame, @OriginalArg(1) int languageId, @OriginalArg(2) Js5ResourceProvider floResource) {
		this.floResource = floResource;
		this.totalFileCount = this.floResource.method2108(4);
	}

	@OriginalMember(owner = "client!kb", name = "a", descriptor = "(B)V")
	public void method3149() {
		@Pc(2) LRUCache local2 = this.overlayCache;
		synchronized (this.overlayCache) {
			this.overlayCache.method1777();
		}
	}

	@OriginalMember(owner = "client!kb", name = "a", descriptor = "(II)Lclient!is;")
	public FloType method3150(@OriginalArg(1) int arg0) {
		@Pc(6) LRUCache local6 = this.overlayCache;
		@Pc(16) FloType local16;
		synchronized (this.overlayCache) {
			local16 = (FloType) this.overlayCache.method1787((long) arg0);
		}
		if (local16 != null) {
			return local16;
		}
		@Pc(33) byte[] local33 = this.floResource.method2104(arg0, 4);
		local16 = new FloType();
		local16.aClass124_4 = this;
		local16.anInt3385 = arg0;
		if (local33 != null) {
			local16.method2822(new Packet(local33));
		}
		local16.method2818();
		@Pc(60) LRUCache local60 = this.overlayCache;
		synchronized (this.overlayCache) {
			this.overlayCache.method1779((long) arg0, local16);
			return local16;
		}
	}

	@OriginalMember(owner = "client!kb", name = "b", descriptor = "(I)V")
	public void method3151() {
		@Pc(2) LRUCache local2 = this.overlayCache;
		synchronized (this.overlayCache) {
			this.overlayCache.method1791();
		}
	}

	@OriginalMember(owner = "client!kb", name = "a", descriptor = "(IZ)V")
	public void method3152() {
		@Pc(2) LRUCache local2 = this.overlayCache;
		synchronized (this.overlayCache) {
			this.overlayCache.method1788(5);
		}
	}

@OriginalMember(owner = "client!kb", name = "l", descriptor = "I")
	public static int anInt3850;
@OriginalMember(owner = "client!kb", name = "i", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_173 = new Class22(37, 8);
@OriginalMember(owner = "client!kb", name = "k", descriptor = "Z")
	public static boolean aBoolean362 = false;
@OriginalMember(owner = "client!kb", name = "a", descriptor = "(I)V")
	public static void method3148() {
		TextureOpFlip.anInt3532 = 0;
		GlRenderList.anInt1094 = -1;
		Class30.anInt771 = -1;
	}
}
