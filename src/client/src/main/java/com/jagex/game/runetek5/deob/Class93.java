package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.toolkit.vertexdeclaration.VertexDeclarationComponent;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.MapRegion;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.sound.jagtheora.MusicTrack;

import com.jagex.game.runetek5.deob.*;
@OriginalClass("client!he")
public final class Class93 {

	@OriginalMember(owner = "client!he", name = "f", descriptor = "Lclient!fa;")
	private final LRUCache aClass68_13 = new LRUCache(64);

	@OriginalMember(owner = "client!he", name = "e", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider aClass76_36;

	@OriginalMember(owner = "client!he", name = "b", descriptor = "I")
	public final int anInt2890;

	@OriginalMember(owner = "client!he", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;)V")
	public Class93(@OriginalArg(0) Game arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Js5ResourceProvider arg2) {
		this.aClass76_36 = arg2;
		this.anInt2890 = this.aClass76_36.method2108(19);
	}

	@OriginalMember(owner = "client!he", name = "a", descriptor = "(II)Lclient!uh;")
	public VertexDeclarationComponent method2430(@OriginalArg(1) int arg0) {
		@Pc(6) LRUCache local6 = this.aClass68_13;
		@Pc(16) VertexDeclarationComponent local16;
		synchronized (this.aClass68_13) {
			local16 = (VertexDeclarationComponent) this.aClass68_13.method1787((long) arg0);
		}
		if (local16 != null) {
			return local16;
		}
		@Pc(33) byte[] local33 = this.aClass76_36.method2104(arg0, 19);
		local16 = new VertexDeclarationComponent();
		if (local33 != null) {
			local16.method5214(new Packet(local33));
		}
		@Pc(54) LRUCache local54 = this.aClass68_13;
		synchronized (this.aClass68_13) {
			this.aClass68_13.method1779((long) arg0, local16);
			return local16;
		}
	}

@OriginalMember(owner = "client!he", name = "d", descriptor = "Lclient!l;")
	public static NativeSprite aClass57_15;
@OriginalMember(owner = "client!he", name = "h", descriptor = "I")
	public static int anInt2891;
@OriginalMember(owner = "client!he", name = "j", descriptor = "Lclient!nb;")
	public static MusicTrack aClass4_Sub30_2;
@OriginalMember(owner = "client!he", name = "l", descriptor = "Lclient!wn;")
	public static MapRegion aClass266_Sub1_2;
@OriginalMember(owner = "client!he", name = "c", descriptor = "Lclient!pk;")
	public static final Class183 aClass183_20 = new Class183();
@OriginalMember(owner = "client!he", name = "g", descriptor = "[I")
	public static final int[] anIntArray184 = new int[32];
@OriginalMember(owner = "client!he", name = "k", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_125 = new Class22(91, -1);
@OriginalMember(owner = "client!he", name = "m", descriptor = "[Lclient!rs;")
	public static final NPCEntity[] aClass16_Sub1_Sub5_Sub2Array1 = new NPCEntity[32768];
}
